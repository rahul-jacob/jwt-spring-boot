package com.xyz.raul.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new User("raul","61624528",new ArrayList<>());
	}

}
