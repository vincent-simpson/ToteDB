package com.luv2code.springboot.thymeleafdemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	private EmployeeService employeeService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Autowired
	public EmployeeController(EmployeeService theEmployeeService) {
		this.employeeService = theEmployeeService;
	}
	
	@RequestMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, @RequestParam("employeeId2") int id) {
		
		logger.warn("employee controller employee id: " + employee);
		logger.warn("id: " + id);
		
		employee.setId(id);
		
		employeeService.save(employee);
		
		return "redirect:/employees/list";
	}
	
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int id) {
				
		employeeService.deleteById(id);
		
		return "redirect:/employees/list";
	}
	
	@GetMapping("/list")
	public String employeeList(Model theModel) {
		
		logger.warn("In list mapping");
		
		List<Employee> employees = employeeService.findAll();
		Employee employee = new Employee();
		
		theModel.addAttribute("employees", employees);
		theModel.addAttribute("employee", employee);
		
		return "employeeList";
	}
	
	@PostMapping("/edit")
	public String editEmployee(@RequestParam("employeeId") int id, Model model) {
		
		if(id == -1) {
			logger.warn("The employee id is negative 1: " + id);
			
			model.addAttribute("employee", new Employee());
			
			return "employeeList :: modalEditEmployee";
		} else {
			logger.warn("The employee id is not negative 1: " + id);
			
			Employee employee = employeeService.findById(id);
			logger.warn("employee service employee id: " + employee.getId());
			
			model.addAttribute("employee", employee);
			
			return "employeeList :: modalEditEmployee";
		}	
		
		
	}

	

}
