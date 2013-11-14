package de.fh_dortmund.ticket_system.persistence;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import de.fh_dortmund.ticket_system.entity.Employee;

@ManagedBean
@SessionScoped
public class EmployeeDAOsqlLite implements EmployeeDAO, Serializable
{
	private static final long serialVersionUID = 1L;

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("sqlite");
	EntityManager entityManager = emf.createEntityManager();

	@Override
	public List<Employee> findAllEmployees()
	{

		List<Employee> resultList = entityManager.createNamedQuery("Employee.findAll", Employee.class).getResultList();
		return resultList;
	}

	@Override
	public void updateEmployee(Employee employee)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.merge(employee);

		entityManager.persist(employee);
		tx.commit();
	}

	@Override
	public void deleteEmployee(Employee employee)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.remove(employee);
		tx.commit();
	}

	@Override
	public void addEmployee(Employee employee)
	{
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		entityManager.persist(employee);
		tx.commit();
	}

	@Override
	public Employee findEmployeeById(String id)
	{

		return entityManager.find(Employee.class, id);
	}

}
