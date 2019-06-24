package com.vince.springboot.app.service;

import java.util.List;

import com.vince.springboot.app.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee employee);
	
	public void save(List<Employee> employees);
	
	public void deleteById(int id);

}
