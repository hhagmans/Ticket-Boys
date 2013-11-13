package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import de.fh_dortmund.ticket_system.entity.Employee;

public class EmployeeDAOsqlLite implements EmployeeDAO {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlite");
    EntityManager entityManager = emf.createEntityManager();

	@Override
	public List<Employee> findAllEmployees() {

			List<Employee> resultList = entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
		return resultList;
	}

	@Override
	public void updateEmployee(Employee employee) {
		
		entityManager.merge(employee);
		
		entityManager.persist(employee);
	}

	@Override
	public void deleteEmployee(Employee employee) {

		entityManager.remove(employee);
	}

	@Override
	public void addEmployee(Employee employee) {
		
		entityManager.persist(employee);
	}

	@Override
	public Employee findEmployeeById(String id) {

		return entityManager.find(Employee.class, id);
	}
	
	

}
