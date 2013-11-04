package de.fh_dortmund.ticket_system;

import java.util.ArrayList;
import java.util.List;

public class Employees {
	
	private List<Employee> employees;

	public Employees() {
		super();
		this.setEmployees(new ArrayList<Employee>());
		fillEmployees();
	}

	private void fillEmployees() {
		Employee employee = new Employee("Kartoffel1337", "Karl-Heinz", "Toffelhaus", "Dortmund", 44135, Role.admin);
		Employee employee2 = new Employee("Mettwurst1337", "Mette", "Wurstowitz", "Stadt ohne Namen", 45883, Role.dispatcher);
		getEmployees().add(employee );
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	
	
	
}
