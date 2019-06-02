package com.luv2code.springboot.thymeleafdemo.dao.employee;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;

public interface EmployeeDAO {

	public List<Employee> getAll();
	
	public void save(Employee employee);
	
	public void save(List<Employee> employees);
	
	public Employee findById(int theId);
	
	public void deleteById(int theId);
	
}
