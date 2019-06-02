package com.luv2code.springboot.thymeleafdemo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
public class DispatchController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public DispatchController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
		
	@GetMapping("/")
	public String index() {
		return "index";
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
