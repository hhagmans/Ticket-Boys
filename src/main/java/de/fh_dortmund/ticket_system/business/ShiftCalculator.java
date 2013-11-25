package de.fh_dortmund.ticket_system.business;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;

import org.codehaus.plexus.util.StringUtils;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;

/**
 * Meant to be the Dispatcher-List generating Class. Later with score and shit.
 * 
 * @author Ticket-Boys
 * 
 */

@ManagedBean
public class ShiftCalculator
{
	public List<Shift> generateDispatcherList(List<Employee> empList)
	{
		List<Shift> shifts = new ArrayList<Shift>();
		int usercount = empList.size();
		int actUsercounter = 0;
		Employee actEmp = null;
		Employee actEmp2 = null;

		for (int i = 0; i < 52; i++)
		{
			if (actUsercounter >= usercount)
			{
				actUsercounter = 0;
			}
			actEmp = empList.get(actUsercounter);

			if ((actUsercounter + 1) < empList.size())
			{
				actEmp2 = empList.get(actUsercounter + 1);
			}
			else
			{
				actEmp2 = empList.get(0);
			}

			shifts.add(new Shift(2013, i + 1, actEmp, actEmp2));
			actUsercounter++;
		}
		return shifts;
	}

	public static List<Shift> getDispatcherShifts()
	{
		List<Shift> shifts = new ArrayList<Shift>();

		return shifts;

	}

	public ShiftCalculator()
	{

	}
	
	
}
