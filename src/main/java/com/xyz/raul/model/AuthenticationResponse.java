package com.xyz.raul.model;

public class AuthenticationResponse {
	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + "]";
	}
}
