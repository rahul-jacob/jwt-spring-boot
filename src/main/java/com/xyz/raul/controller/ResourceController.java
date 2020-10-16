package com.xyz.raul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
	@RequestMapping({"/hello"})
	public String sayHello(){
		return "Hello World!";
	}
}
