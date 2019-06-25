package com.vince.springboot.app.dao.employee;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vince.springboot.app.entity.Employee;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
	
	// define field for entitymanager
	private EntityManager entityManager;
	
	// set up constructor injection
	@Autowired
	public EmployeeDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Employee> getAll() {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
//		//comment these two lines in/out depending on if local or heroku
//		Query setUpSequences = currentSession.createNativeQuery("SET search_path TO monmouth, public");
//		setUpSequences.executeUpdate();
//		
//		// create a query
		Query<Employee> theQuery = 
				currentSession.createQuery("from Employee", Employee.class);
		
		// execute query and get result list
		List<Employee> employees = theQuery.list();
		
		
		
		
		// return the results
		
		return employees;
	}

	@Override
	public void save(Employee employee) {
		
		// get the current session
		Session currentSession = entityManager.unwrap(Session.class);
		
		Logger logger = LoggerFactory.getLogger(this.getClass());
		
		logger.warn(employee.getId() + "");
		
		Query theQuery = currentSession.createQuery("from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", employee.getId());
		
		logger.warn("query employee id: " + employee.getId());
		
		Employee employee2 = (Employee) theQuery.uniqueResult();
				
		if(employee2 == null) {
			currentSession.save(employee);
			logger.warn("employee is null. Saving it now");
		} else {
			currentSession.evict(employee2); //remove employee2 because it also references the same object as the employee parameter
			currentSession.update(employee);
			logger.warn("employee is not null. updating...");
		}
		
	
		
	}

	@Override
	public Employee findById(int theId) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get the employee
		Employee theEmployee = 
				currentSession.get(Employee.class, theId);
		
		// return the employee
		
		return theEmployee;
	}

	@Override
	public void deleteById(int theId) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		//delete object with primary key
		Query<Employee> theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", theId);
		
		theQuery.executeUpdate();
		
		Query emptyTableQuery = currentSession.createQuery("from Employee");
		List<Employee> temp = emptyTableQuery.getResultList();
		
		if(temp.isEmpty()) {
			//comment these two lines in/out depending on if local or heroku
			Query autoIncrementZero = currentSession.createNativeQuery("ALTER SEQUENCE employee_auto_increment RESTART WITH 1");
			autoIncrementZero.executeUpdate();
		}
		
	}

	@Override
	public void save(List<Employee> employees) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		for(Employee temp : employees) {
			currentSession.saveOrUpdate(temp);
		}
		
	}

}
