package com.example.UserService.Service;

import com.example.UserService.DTO.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
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
    private String clientId;
    @Value("${keycloak.admin.client}")
    private String myClientId;
    @Value("${keycloak.admin.client-uuid}")
    private String clientUuid;


    private final RestTemplate restTemplate = new RestTemplate();

    public String getAdminAccessToken(){

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("client_id",clientId);
        params.add("username", adminUsername);
        params.add("password",adminPassword);
        params.add("grant_type","password");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String,String>> entity = new HttpEntity<>(params,headers);

        String url = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        ResponseEntity<Map> response = restTemplate.postForEntity
                (
                url,
                entity,
                Map.class
                );
        return response.getBody().get("access_token").toString();
    }

    public String createUser(String token, UserRequest userRequest){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        Map<String,Object> userPayload = new HashMap<>();
        userPayload.put("username", userRequest.getUsername());
        userPayload.put("email",userRequest.getEmail());
        userPayload.put("enabled",true);
        userPayload.put("firstName",userRequest.getFirstName());
        userPayload.put("lastName",userRequest.getLastName());


        Map<String,Object> credential = new HashMap<>();
        credential.put("type","password");
        credential.put("value",userRequest.getPassword());
        credential.put("temporary",false);


        userPayload.put("credentials", List.of(credential));

        HttpEntity<Map<String,Object>> entity = new HttpEntity<>(userPayload,headers);

        String url = keycloakServerUrl + "/admin/realms/" + realm + "/users";


        ResponseEntity<String> response = restTemplate.postForEntity
                (
                        url,
                        entity,
                        String.class
                );

        if(!HttpStatus.CREATED.equals(response.getStatusCode())){
            throw new RuntimeException("Failed to create user in keycloak"+ response.getBody());
        }

        URI location = response.getHeaders().getLocation();
        if(location == null){
            throw new RuntimeException("Keycloak did not return Location Header "+ response.getBody());
        }

        String path = location.getPath();
        return path.substring(path.lastIndexOf("/") + 1);

    }

    private Map<String, Object> getRealmRoleRepresentation(String token,String roleName){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String url = keycloakServerUrl + "/admin/realms/"+ realm +"/clients/"+ clientUuid +"/roles/"+roleName;
        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        return response.getBody();
    }

    public void assignRealmRoleTUser(String username, String roleName,String userId){

        String token = getAdminAccessToken();
        Map<String, Object> roleRep = getRealmRoleRepresentation(token,roleName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<List<Map<String,Object>>> entity = new HttpEntity<>(List.of(roleRep),headers);

        String url = keycloakServerUrl + "/admin/realms/"+realm+"/users/"+userId+"/role-mappings/clients/"+clientUuid;

        ResponseEntity<Void> response = restTemplate.postForEntity(url,entity, Void.class);

        if(!response.getStatusCode().is2xxSuccessful()){
            throw new RuntimeException("Failed to assing role "+ roleName + "to user "+ username+" HTTP "+ response.getStatusCode());
        }

    }

    public String login(String username, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", myClientId);
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        String url = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> body = response.getBody();
                return body != null ? body.get("access_token").toString() : null;
            } else {
                throw new RuntimeException("Login failed: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Login failed: " + e.getMessage(), e);
        }
    }




}
