package com.mcvalls.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloWorld")
public class HelloWorldController {

	@GetMapping("/sayHello")
	public String sayHello() {
		return "Hello World!";
	}
	
	@GetMapping("/sayHelloAsAdmin")
	public String sayHelloAsAdmin() {
		return "Hello World! \nI'm an Admin!";
	}
	
}
