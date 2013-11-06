package de.fh_dortmund.ticket_system.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten) 
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
@SessionScoped
public class ShiftCalculator implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Shift> shifts;
	private List<Shift> selectedShifts;
	
	private ShiftModel shiftModel;
	
	public ShiftCalculator() {
	setShifts(new ArrayList<Shift>());
	fillDatShit();
	}

	private void fillDatShit() {		
		
		
		
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
		
		// Verteilen der Employees auf die 52 "Shifts"
		if (empList != null) {
			
			int usercount = empList.size();
			int actUsercounter = 0;
			Employee actEmp = null;
			
			for (int i = 0; i < 52;i++) {
				if (actUsercounter >= usercount) {
					actUsercounter = 0;
				}
				actEmp = empList.get(actUsercounter);
				getShifts().add(new Shift("2013-" + i + 1,i + 1,actEmp.getFirstName(), actEmp.getLastName()));
				actUsercounter++;
			}
		}
		
		shiftModel = new ShiftModel(shifts);
	 
		
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	public List<Shift> getSelectedShifts() {
		return selectedShifts;
	}

	public void setSelectedShifts(List<Shift> selectedShifts) {
		this.selectedShifts = selectedShifts;
	}

	public ShiftModel getShiftModel() {
		return shiftModel;
	}
	
	public void swapShifts()
	{
		if(selectedShifts.size()!=2)
			//TODO Exceptionhandling
			return;
		
		Shift shift0 = selectedShifts.get(0);
		Shift shift1 = selectedShifts.get(1);
		
		if(shift0 ==null || shift1==null)
			//TODO Exceptionhandling
			return;
		
	}
}