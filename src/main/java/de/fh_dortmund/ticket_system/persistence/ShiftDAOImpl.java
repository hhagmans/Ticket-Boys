package de.fh_dortmund.ticket_system.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.ShiftModel;

public class ShiftDAOImpl implements ShiftDAO{

	
	
	public List<Shift> getAllShifts()
	{
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
		
		List<Shift> shifts = new ArrayList<Shift>();
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
				
				
				shifts.add(new Shift("2013-" + i + 1,i + 1,actEmp.getFirstName(), actEmp.getLastName()));
				actUsercounter++;
			}
		}
		
		return shifts;
	 
	}

	@Override
	public void updateShift(Shift shift) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteShift(Shift shift) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addShift(Shift newShift) {
		// TODO Auto-generated method stub
		
	}
}
