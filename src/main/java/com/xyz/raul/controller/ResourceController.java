package com.xyz.raul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.raul.model.AuthenticationRequest;
import com.xyz.raul.model.AuthenticationResponse;
import com.xyz.raul.service.MyUserDetailsService;
import com.xyz.raul.util.JwtUtil;

@RestController
public class ResourceController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private MyUserDetailsService userDetailService;

	@Autowired
	private JwtUtil jwtUtil;

	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello World!";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthentionToken(@RequestBody AuthenticationRequest authReq) throws Exception {
		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
		} catch (Exception e) {
			throw new Exception("Incorrect username and password");
		}
		final UserDetails userDetail = userDetailService.loadUserByUsername(authReq.getUsername());
		String token = jwtUtil.generateToken(userDetail);
		//return ResponseEntity.ok(new AuthenticationResponse(token));
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(token),HttpStatus.OK);
	}
}
