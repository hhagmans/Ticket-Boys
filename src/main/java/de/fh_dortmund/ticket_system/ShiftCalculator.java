package de.fh_dortmund.ticket_system;

import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Dieses Objekt berechnet und verwaltet den Dispatcher-Schichtplan (Liste von Shift-Objekten) 
 * 
 * @author Ticket-Boys
 *
 */

@ManagedBean
public class ShiftCalculator implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Shift> shifts;
	
	public ShiftCalculator() {
	setShifts(new ArrayList<Shift>());
	fillDatShit();
	}

	private void fillDatShit() {		
		
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
		
		if (empList != null) {
			
			int usercount = empList.size();
			int actUsercounter = 0;
			Employee actEmp = null;
			
			for (int i = 0; i < 52;i++) {
				if (actUsercounter >= usercount) {
					actUsercounter = 0;
				}
				actEmp = empList.get(actUsercounter);
				getShifts().add(new Shift(i + 1,actEmp.getFirstName(), actEmp.getLastName()));
				actUsercounter++;
			}
		}

		
	 
		
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}
	
	


}