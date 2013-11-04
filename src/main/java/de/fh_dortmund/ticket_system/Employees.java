package de.fh_dortmund.ticket_system;

import java.util.ArrayList;
import java.util.List;

public class Employees {
	
	private List<Employee> employees;

	public Employees() {
		super();
		this.employees = new ArrayList<Employee>();
		fillEmployees();
	}

	private void fillEmployees() {
		Employee employee = new Employee("Kartoffel1337", "Karl-Heinz", "Toffelhaus", "Dortmund", 44135, Role.admin);
		Employee employee2 = new Employee("Mettwurst1337", "Mette", "Wurstowitz", "Stadt ohne Namen", 45883, Role.dispatcher);
		employees.add(employee );
	}

	
	
	
}
