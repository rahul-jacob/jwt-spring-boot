package com.xyz.raul.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xyz.raul.service.MyUserDetailsService;
import com.xyz.raul.util.JwtUtil;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String username = null;
		String jwt = null;
		final String authorizationHeader = req.getHeader("Authorization");
		if(!StringUtils.isEmpty(authorizationHeader) && authorizationHeader.startsWith("Bearer ")){
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		if(!StringUtils.isEmpty(username) && SecurityContextHolder.getContext().getAuthentication()==null){
			UserDetails userDetails = userDetailService.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)){
				UsernamePasswordAuthenticationToken usernamePasswordAuthToken = 
						new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
				usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
			}
		}
		chain.doFilter(req, res);
	}

}
