package com.vince.springboot.app.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vince.springboot.app.entity.Employee;
import com.vince.springboot.app.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeRestController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}
		
	@GetMapping("/employees/{employeeId}")
	public ResponseEntity<?> getEmployee(@PathVariable int employeeId) {
		
		Employee theEmployee = employeeService.findById(employeeId);
		
		if(theEmployee == null) {
			return new ResponseEntity<String>("No employee matching that ID", HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Employee>(theEmployee, HttpStatus.OK);
		}
		
	}
	
	// add mapping for POST /employees - add new employee
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	// add mapping for PUT /employees - update existing
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		employeeService.save(theEmployee);
		return theEmployee;
	}
	
	// add mapping for DELETE /employees/{employeeId} - delete
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable int employeeId) {
		
		Employee temp = employeeService.findById(employeeId);
		
		// throw exception if null
		if (temp == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		return "Deleted employee id - " + employeeId;
	}

}
