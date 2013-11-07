package de.fh_dortmund.ticket_system.presentation;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

/**
 * Die View zur Dispatcherliste
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@SessionScoped
public class DispatcherView
{
	@ManagedProperty("#{auth}")
	Authentication authentication;
	
	@ManagedProperty("#{shiftData}")
	ShiftData	shiftData;

	@ManagedProperty("#{employeeData}")
	EmployeeData			employeeData;

	private List<Shift>	selectedShifts;

	public void switchShifts()
	{
		Shift shift0 = getSelectedShifts().get(0);
		Shift shift1 = getSelectedShifts().get(1);
		if(!userIsAllowedToSwitchShifts(shift0, shift1))
			return;
		
		if (getSelectedShifts().size() >= 2){
			FacesMessage msg = new FacesMessage("You selected more than 2 shifts");  
		  
        	FacesContext.getCurrentInstance().addMessage(null, msg);   
			return;
		}
		
		if (getSelectedShifts().size() >= 2){
			FacesMessage msg = new FacesMessage("You selected less than 2 shifts");  
		  
        	FacesContext.getCurrentInstance().addMessage(null, msg);   
			return;
		}

		if (shift0 == null || shift1 == null){
			FacesMessage msg = new FacesMessage("You selected invalid shifts");  
		  
    		FacesContext.getCurrentInstance().addMessage(null, msg);   
			return;
		}

		Shift tempShift0 = new Shift(shift0.getYear(), shift0.getWeekNumber(), shift0.getDispatcher(),
				shift0.getSubstitutioner());

		shift0.setDispatcher(shift1.getDispatcher());
		shift1.setDispatcher(tempShift0.getDispatcher());
	}
	
	public boolean userIsAllowedToSwitchShifts(Shift shift1, Shift shift2)
	{
		Employee currentUser = authentication.getEmployee();
		
		if(currentUser.equals(shift1.getDispatcher()) || currentUser.equals(shift2.getDispatcher()))
		return true;
		
		return false;
	}


	public List<Shift> getSelectedShifts()
	{
		return selectedShifts;
	}

	public void setSelectedShifts(List<Shift> selectedShifts)
	{
		this.selectedShifts = selectedShifts;
	}


	public ShiftData getShiftData() {
		return shiftData;
	}


	public void setShiftData(ShiftData shiftData) {
		this.shiftData = shiftData;
	}


	public EmployeeData getEmployeeData() {
		return employeeData;
	}


	public void setEmployeeData(EmployeeData employeeData) {
		this.employeeData = employeeData;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}
	
	
	
	

}
