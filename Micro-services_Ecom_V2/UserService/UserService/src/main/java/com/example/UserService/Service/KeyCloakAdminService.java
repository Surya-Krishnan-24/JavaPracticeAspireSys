package com.example.UserService.Service;

import com.example.UserService.DTO.UserRequest;
import com.example.UserService.GlobalExceptionHandler.KeycloakAuthenticationException;
import com.example.UserService.GlobalExceptionHandler.KeycloakUserCreationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeyCloakAdminService {

    @Value("${keycloak.admin.username}")
    private String adminUsername;

    @Value("${keycloak.admin.password}")
    private String adminPassword;

    @Value("${keycloak.admin.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.admin.client}")
    private String clientId;

    @Value("${keycloak.admin.client-uuid}")
    private String userUuid;

    private final RestClient restClient;



    public String getAdminAccessToken() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", adminClientId);
        params.add("username", adminUsername);
        params.add("password", adminPassword);
        params.add("grant_type", "password");

        try {
            Map<String, Object> response = restClient.post()
                    .uri(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(params)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return response.get("access_token").toString();
        } catch (HttpStatusCodeException e) {
            throw new KeycloakAuthenticationException("Failed to get admin token: " + e.getResponseBodyAsString());
        }
    }

    public String createUser(String token, UserRequest userRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", userRequest.getUsername());
        userPayload.put("email", userRequest.getEmail());
        userPayload.put("enabled", true);
        userPayload.put("firstName", userRequest.getFirstName());
        userPayload.put("lastName", userRequest.getLastName());

        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", userRequest.getPassword());
        credential.put("temporary", false);

        userPayload.put("credentials", List.of(credential));
        try {
            ResponseEntity<Void> response = restClient.post()
                    .uri(keycloakServerUrl + "/admin/realms/" + realm + "/users")
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(userPayload)
                    .retrieve()
                    .toEntity(Void.class);

            if (!response.getStatusCode().equals(HttpStatus.CREATED)) {
                throw new KeycloakUserCreationException("Failed to create user in keycloak. Status: " + response.getStatusCode());
            }

            URI location = response.getHeaders().getLocation();
            if (location == null) {
                throw new KeycloakUserCreationException("Keycloak did not return Location header.");
            }

            String path = location.getPath();
            return path.substring(path.lastIndexOf("/") + 1);
        }
        catch (HttpStatusCodeException e){
            throw new KeycloakUserCreationException("Keycloak error: " + e.getResponseBodyAsString());
        }
    }

    public void updateUserInKeycloak(String token, String keycloakUserId, UserRequest userRequest) {
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("email", userRequest.getEmail());
        userPayload.put("firstName", userRequest.getFirstName());
        userPayload.put("lastName", userRequest.getLastName());

        restClient.put()
                .uri(keycloakServerUrl + "/admin/realms/" + realm + "/users/" + keycloakUserId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userPayload)
                .retrieve()
                .toBodilessEntity();
    }


    public void updateUserPasswordInKeycloak(String token, String keycloakUserId, String newPassword) {
        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", newPassword);
        credential.put("temporary", false);

        restClient.put()
                .uri(keycloakServerUrl + "/admin/realms/" + realm + "/users/" + keycloakUserId + "/reset-password")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(credential)
                .retrieve()
                .toBodilessEntity();
    }



    public void assignRoleToUser(String roleName, String userId) {
        String token = getAdminAccessToken();
        Map<String, Object> roleRep = getRealmRoleRepresentation(token, roleName);

        String url = keycloakServerUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/clients/" + userUuid;

        restClient.post()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .body(List.of(roleRep))
                .retrieve()
                .toBodilessEntity();
    }

    private Map<String, Object> getRealmRoleRepresentation(String token, String roleName) {
        return restClient.get()
                .uri(keycloakServerUrl + "/admin/realms/" + realm + "/clients/" + userUuid + "/roles/" + roleName)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public String login(String username, String password) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        try {
            Map<String, Object> response = restClient.post()
                    .uri(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(params)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return response != null ? response.get("access_token").toString() : null;
        } catch (Exception e) {
            throw new KeycloakAuthenticationException("Login failed: " + e.getMessage());
        }
    }
}
