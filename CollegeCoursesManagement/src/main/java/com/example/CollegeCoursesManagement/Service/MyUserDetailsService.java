package com.example.CollegeCoursesManagement.Service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.CollegeCoursesManagement.DAO.UserRepo;
import com.example.CollegeCoursesManagement.Model.User;
import com.example.CollegeCoursesManagement.Model.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepo.findByUsername(username).get();
		if (user == null) {
			throw new UsernameNotFoundException("User is not found in the Database");
		}
		return new UserPrincipal(user);
	}
}