package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;

import de.fh_dortmund.ticket_system.base.BaseView;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.EmployeeModel;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Role;
import de.fh_dortmund.ticket_system.util.HolidayCalendarType;

/**
 * View for employeelist
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ViewScoped
public class EmployeeView extends BaseView implements Serializable {
	private static final long serialVersionUID = 1L;

	private Employee selectedEmployee;

	private Employee newRec = new Employee("new", "first name", "last name",
			"city", Role.admin, 0, 0, HolidayCalendarType.germanyNRW);

	@ManagedProperty("#{employeeData}")
	EmployeeData employeeData;

	@ManagedProperty("#{shiftData}")
	ShiftData shiftData;

	private EmployeeModel employeeModel;

	public EmployeeView() {

	}

	public void onEdit(RowEditEvent event) {
		updateEmployee((Employee) event.getObject());
		addMessage("Mitarbeiter bearbeitet",
				((Employee) event.getObject()).getKonzernID());
	}

	public void onCancel(RowEditEvent event) {
		addMessage("Abgebrochen", ((Employee) event.getObject()).getKonzernID());
	}

	public void updateEmployee(Employee employee) {
		getEmployeeData().update(employee);
	}

	public void addEmployee() {
		getEmployeeData().add(getNewRec());
		addMessage("Mitarbeiter hinzugefügt", "Der Mitarbeiter "
				+ getNewRec().getFullName() + " wurde erfolgreich hinzugefügt");
	}

	public void deleteEmployee(Employee employee) {
		getEmployeeData().delete(employee);
		getShiftData().deleteEmployeeFromShifts(employee);
		addMessage("Erfolg!", "Der Mitarbeiter " + employee.getFullName()
				+ " wurde gelöscht");
	}

	public Employee getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Employee selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public EmployeeData getEmployeeData() {
		return employeeData;
	}

	public void setEmployeeData(EmployeeData employeeData) {
		this.employeeData = employeeData;
	}

	public EmployeeModel getEmployeeModel() {
		if (employeeModel == null) {
			List<Employee> findAllEmployees = getEmployeeData().findAll();
			System.out.println("all employees: " + findAllEmployees.size());
			EmployeeModel employeeModel2 = new EmployeeModel(findAllEmployees);
			employeeModel = employeeModel2;
		}

		return employeeModel;
	}

	public void setEmployeeModel(EmployeeModel employeeModel) {
		this.employeeModel = employeeModel;
	}

	public Employee getNewRec() {
		return newRec;
	}

	public void setNewRec(Employee newRec) {
		this.newRec = newRec;
	}

	public ShiftData getShiftData() {
		return shiftData;
	}

	public void setShiftData(ShiftData shiftData) {
		this.shiftData = shiftData;
	}

	public Role[] getEmployeeRoles() {
		return Role.values();
	}

	public HolidayCalendarType[] getEmployeeHolidayCalendarTypes() {
		return HolidayCalendarType.values();
	}
}
