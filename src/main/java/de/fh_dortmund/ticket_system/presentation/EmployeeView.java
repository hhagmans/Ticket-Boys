package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.entity.Employee;

@ManagedBean
@SessionScoped
public class EmployeeView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@ManagedProperty("#{employeeData}")
	private EmployeeData employeeData;

	private Employee selectedEmployee;

	public void onEdit(RowEditEvent event)
	{
		FacesMessage msg = new FacesMessage("Mitarbeiter bearbeitet", ((Employee) event.getObject()).getKonzernID());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event)
	{
		FacesMessage msg = new FacesMessage("Abgebrochen", ((Employee) event.getObject()).getKonzernID());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public EmployeeData getEmployeeData()
	{
		return employeeData;
	}

	public void deleteEmployee()
	{
		employeeData.deleteEmployee(selectedEmployee);
	}

	public void setEmployeeData(EmployeeData employeeData)
	{
		this.employeeData = employeeData;
	}

	public Employee getSelectedEmployee()
	{
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee)
	{
		this.selectedEmployee = selectedEmployee;
	}
}
