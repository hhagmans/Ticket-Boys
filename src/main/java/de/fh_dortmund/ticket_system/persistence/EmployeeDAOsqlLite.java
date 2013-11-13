package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.ticket_system.entity.Employee;

public class EmployeeDAOsqlLite implements EmployeeDAO {


    @PersistenceContext
    private EntityManager entityManager;
	

	@Override
	public List<Employee> findAllEmployees() {
		
			List<Employee> resultList = entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
		return resultList;
	}

	@Override
	public void updateEmployee(Employee employee) {

		entityManager.persist(employee);
		entityManager.flush();
	}

	@Override
	public void deleteEmployee(Employee employee) {
		
		entityManager.remove(employee);
		entityManager.flush();
	}

	@Override
	public void addEmployee(Employee employee) {
		
		entityManager.persist(employee);
		entityManager.flush();
		
	}

	@Override
	public Employee findEmployeeById(String id) {
		
		return entityManager.find(Employee.class, id);
	}
	
	

}
