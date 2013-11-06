package de.fh_dortmund.ticket_system.entity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Diese Klasse speichert und verwaltet alle bekannten Benutzer
 * 
 * @author Ticket-Boys
 * 
 */



@ManagedBean
@SessionScoped
public class Employees 
{

	private static final long serialVersionUID = 1L;
	private List<Employee> employees;
	private Employee selectedEmployee;

	public Employees() {
	}
	
	@PostConstruct
	private void fillEmployees() {
		this.setEmployees(new ArrayList<Employee>());

		List<Employee> empList = null;

		// Auslesen der json File
		InputStream is = getClass().getResourceAsStream("/test/UserList.json");
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		String json = s.hasNext() ? s.next() : "";
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Serialisieren in eine Liste von Employees
		Type type = new TypeToken<List<Employee>>(){}.getType();
		empList = new Gson().fromJson(json, type);

		setEmployees(empList);
		
		setSelectedEmployee(getEmployees().get(0));
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
	
	private int getIndexOfEmployeeByKonzernID(String konzernID)
	{
		for(Employee e : employees)
		{
			if(e.getKonzernID().equals(konzernID))
			{
				return employees.indexOf(e);
			}
		}
		return -1;
	}
	
	public void saveChanges()
	{
		;
		int index = getIndexOfEmployeeByKonzernID(selectedEmployee.getKonzernID());
		if(index < 0)
			return;
		employees.get(index).setCity(selectedEmployee.getCity());
		employees.get(index).setFirstName(selectedEmployee.getFirstName());
		employees.get(index).setLastName(selectedEmployee.getLastName());
		employees.get(index).setRole(selectedEmployee.getRole());
		employees.get(index).setZipcode(selectedEmployee.getZipcode());
	}
	
	public void onEdit(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Mitarbeiter bearbeitet", ((Employee) event.getObject()).getKonzernID());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  
      
    public void onCancel(RowEditEvent event) {  
        FacesMessage msg = new FacesMessage("Abgebrochen", ((Employee) event.getObject()).getKonzernID());  
  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
    }  


}
