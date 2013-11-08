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
		updateEmployee((Employee) event.getObject());
		showMessage("Mitarbeiter bearbeitet", ((Employee) event.getObject()).getKonzernID());
	}

	public void onCancel(RowEditEvent event)
	{
		showMessage("Abgebrochen", ((Employee) event.getObject()).getKonzernID());
	}

	private void showMessage(String summary, String detail)
	{
		FacesMessage msg = new FacesMessage(summary, detail);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public EmployeeData getEmployeeData()
	{
		return employeeData;
	}

	public void updateEmployee(Employee employee)
	{
		employeeData.updateEmployee(employee);
	}

	public void addEmployee(Employee employee)
	{
		employeeData.addEmployee(employee);
	}

	public void deleteEmployee(Employee employee)
	{
		employeeData.deleteEmployee(employee);
		showMessage("Erfolg!", "Der Mitarbeiter " + employee.getFullName() + " wurde gelöscht");
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
