package de.fh_dortmund.ticket_system.presentation;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.EmailException;

import de.fh_dortmund.ticket_system.authentication.Authentication;
import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.business.ShiftModel;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.util.DailyChecker;
import de.fh_dortmund.ticket_system.util.EmailUtil;
import de.fh_dortmund.ticket_system.util.RightsManager;

/**
 * Die View zur Dispatcherliste
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ViewScoped
public class DispatcherView implements Serializable
{
	private static final long	serialVersionUID	= 1L;

	@ManagedProperty("#{shiftData}")
	ShiftData					shiftData;

	@ManagedProperty("#{employeeData}")
	EmployeeData				employeeData;

	@ManagedProperty("#{rightsManager}")
	private
	RightsManager				rightsManager;

	private ShiftModel			shiftModel;

	private List<Shift>			selectedShifts;

	public void switchShifts()
	{
		if (getSelectedShifts().size() > 2)
		{
			showMessage("Verweigert", "Sie haben mehr als 2 Schichten ausgewählt!");
			return;
		}
		else if (getSelectedShifts().size() < 2)
		{
			showMessage("Verweigert", "Sie haben weniger als 2 Schichten ausgewählt!");
			return;
		}

		Shift shift0 = getSelectedShifts().get(0);
		Shift shift1 = getSelectedShifts().get(1);

		if ((shift0 == null) || (shift1 == null))
		{
			showMessage("Verweigert", "Sie haben nicht (mehr) vorhandene Schichten zum tauschen gewählt!");
			return;
		}

		if (!getRightsManager().userIsAllowedToSwitchShifts(shift0, shift1))
		{
			showMessage("Verweigert", "Sie haben nicht die Berechtigung diese Schichten zu tauschen!");
			return;
		}

		Shift tempShift0 = new Shift(shift0.getYear(), shift0.getWeekNumber(), shift0.getDispatcher(),
				shift0.getSubstitutioner());

		shift0.setDispatcher(shift1.getDispatcher());
		shift1.setDispatcher(tempShift0.getDispatcher());

		updateShifts(shift0);
		updateShifts(shift1);

		showMessage("Erfolg!", "Die Dispatcher der KW " + shift1.getWeekNumber() + " & " + shift0.getWeekNumber()
				+ " wurden getauscht!");
		
//		test email sending
		try {
			DailyChecker.check();
			showMessage("Email versendet","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			showMessage(e.getMessage(), "fehler");
		}
	}

	private void updateShifts(Shift shift0)
	{
		shiftData.updateShift(shift0);
	}

	public List<Shift> getSelectedShifts()
	{
		return selectedShifts;
	}

	public void setSelectedShifts(List<Shift> selectedShifts)
	{
		this.selectedShifts = selectedShifts;
	}

	public ShiftData getShiftData()
	{
		return shiftData;
	}

	public void setShiftData(ShiftData shiftData)
	{
		this.shiftData = shiftData;
	}

	public EmployeeData getEmployeeData()
	{
		return employeeData;
	}

	public void setEmployeeData(EmployeeData employeeData)
	{
		this.employeeData = employeeData;
	}

	public void showMessage(String summary, String detail)
	{
		FacesMessage msg = new FacesMessage(summary, detail);

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public ShiftModel getShiftModel()
	{
		if (shiftModel == null)
			setShiftModel(new ShiftModel(shiftData.findAllShifts()));

		return shiftModel;
	}

	public void setShiftModel(ShiftModel shiftModel)
	{
		this.shiftModel = shiftModel;
	}

	public RightsManager getRightsManager()
	{
		return rightsManager;
	}

	public void setRightsManager(RightsManager rightsManager)
	{
		this.rightsManager = rightsManager;
	}

}
