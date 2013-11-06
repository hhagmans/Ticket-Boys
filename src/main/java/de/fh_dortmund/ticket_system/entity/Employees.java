package de.fh_dortmund.ticket_system.entity;

import java.io.IOException;
import java.io.InputStream;
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

		List<Employee> empList = null;

		InputStream is = getClass().getResourceAsStream("/test/UserList.json");
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		String json = s.hasNext() ? s.next() : "";
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Type type = new TypeToken<List<Employee>>(){}.getType();
		empList = new Gson().fromJson(json, type);

		setEmployees(empList);
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
	
	private Employee getEmployeeByKonzernID(String konzernID)
	{
		for(Employee e : employees)
		{
			if(e.getKonzernID().equals(konzernID))
			{
				return e;
			}
		}
		return null;
	}
	
	public void saveChanges()
	{
		;
		if(getEmployeeByKonzernID(selectedEmployee.getKonzernID()) == null)
			return;
		getEmployeeByKonzernID(selectedEmployee.getKonzernID()).setCity(selectedEmployee.getCity());
		getEmployeeByKonzernID(selectedEmployee.getKonzernID()).setFirstName(selectedEmployee.getFirstName());
		getEmployeeByKonzernID(selectedEmployee.getKonzernID()).setLastName(selectedEmployee.getLastName());
		getEmployeeByKonzernID(selectedEmployee.getKonzernID()).setRole(selectedEmployee.getRole());
		getEmployeeByKonzernID(selectedEmployee.getKonzernID()).setZipcode(selectedEmployee.getZipcode());
	}

}
