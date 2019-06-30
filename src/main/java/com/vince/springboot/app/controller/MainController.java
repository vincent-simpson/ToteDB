package com.vince.springboot.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.vince.springboot.app.service.EmployeeService;

/**
 * Controller responsible for handling the main page request mappings.
 */
@Controller
public class MainController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public MainController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
		
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/buttons")
	public String getButtons() {
		return "buttons";
	}
	
	@GetMapping("/cards")
	public String getCards() {
		return "cards";
	}
	
	@GetMapping("/utilities-machines")
	public String getUtilitiesOther() {
		return "utilities-machines";
	}

}
