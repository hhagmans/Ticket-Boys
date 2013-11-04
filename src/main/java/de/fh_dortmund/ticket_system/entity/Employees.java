package de.fh_dortmund.ticket_system.entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Diese Klasse speichert und verwaltet alle bekannten Benutzer
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
public class Employees implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Employee> employees;
	private Employee selectedEmployee;

	public Employees() {
		super();
		this.setEmployees(new ArrayList<Employee>());
		fillEmployees();
	}

	private void fillEmployees() {

		Gson gson = new Gson();

		List<Employee> empList = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"./../../ticket-system/src/test/fixtures/UserList.json"));

			Type type = new TypeToken<List<Employee>>() {
			}.getType();
			empList = new Gson().fromJson(br, type);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < empList.size(); i++) {
			getEmployees().add(empList.get(i));
		}
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

}