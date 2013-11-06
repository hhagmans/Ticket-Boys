package de.fh_dortmund.ticket_system.persistence;

import java.util.List;

import de.fh_dortmund.ticket_system.entity.Employee;

public interface EmployeeDAO {

	public List<Employee> getAllEmployees();
	public void updateEmployee(Employee Employee);
	public void deleteEmployee(Employee Employee);
	public void addEmployee(Employee newEmployee);
	
	
}
