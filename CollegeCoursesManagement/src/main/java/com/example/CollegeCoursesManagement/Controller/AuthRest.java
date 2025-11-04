package com.example.CollegeCoursesManagement.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CollegeCoursesManagement.DAO.UserRepo;
import com.example.CollegeCoursesManagement.Model.User;
import com.example.CollegeCoursesManagement.Service.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthRest {

	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	private final UserRepo userRepo;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authentication.isAuthenticated()) {

			User dbUser = userRepo.findByUsername(user.getUsername())
					.orElseThrow(() -> new RuntimeException("User not found"));

			String token = jwtService.generateToken(dbUser);

			return ResponseEntity.ok(Map.of("token", token));
		} else {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}
}
