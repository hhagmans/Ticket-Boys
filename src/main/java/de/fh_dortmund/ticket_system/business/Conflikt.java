package de.fh_dortmund.ticket_system.business;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.entity.VacationEvent;

@ManagedBean
@ApplicationScoped
public class Conflikt
{
	@ManagedProperty("#shiftData")
	ShiftData		shiftData;

	@ManagedProperty("#employeeData")
	EmployeeData	employeeData;

	@ManagedProperty("#vacationData")
	VacationData	vacationData;

	public Conflikt()
	{
	}

	public boolean sameKW(Date date1, Date date2)
	{
		if (getYear(date1).equals(getYear(date2)))
			if (getKW(date1).equals(getKW(date2)))
				return true;

		return false;
	}

	public Integer getKW(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		Integer kw1 = calendar.get(Calendar.WEEK_OF_YEAR);

		return kw1;
	}

	public Integer getYear(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		Integer year = calendar.get(Calendar.YEAR);

		return year;
	}

	public static class KW
	{
		int	year;
		int	kw;

		public KW(int year, int kw)
		{
			this.year = year;
			this.kw = kw;
		}

		@Override
		public boolean equals(Object obj)
		{
			KW kw = (KW) obj;
			if (this.year == kw.year && this.kw == kw.kw)
				return true;

			return false;
		}
	}

	public boolean checkEmployee(Employee employee)
	{
		// Get all KWs from employee
		Set<KW> employeesKWs = getEmployeesKW(employee);

		// Get all KWs from employees shifts
		List<Shift> shifts = shiftData.findShiftByEmployee(employee);
		Set<KW> shiftKWs = getShiftKW(shifts);

		return checkForNoConflicts(employeesKWs, shiftKWs);
	}

	public boolean checkForNoConflicts(Set<KW> set1, Set<KW> set2)
	{
		// Check for conflikts
		boolean ok = true;
		for (KW kw : set1)
		{
			if (set2.contains(kw))
			{
				ok = false;
				break;
			}
		}

		return ok;
	}

	public Set<KW> getShiftKW(List<Shift> shifts)
	{
		Set<KW> kws = new HashSet<Conflikt.KW>();
		for (Shift shift : shifts)
		{
			kws.add(new KW(shift.getYear(), shift.getWeekNumber()));
		}
		return kws;
	}

	public Set<KW> getEmployeesKW(Employee employee)
	{
		Set<KW> employeesKWs = new HashSet<Conflikt.KW>();
		Set<VacationEvent> vacs = employee.getMyEvents();
		for (VacationEvent vacationEvent : vacs)
		{
			Date startDate = vacationEvent.getStartDate();
			Date endDate = vacationEvent.getEndDate();
			employeesKWs.addAll(getKwFromDates(startDate, endDate));
		}

		return employeesKWs;
	}

	public Set<KW> getKwFromDates(Date startDate, Date endDate)
	{
		Set<KW> employeesKWs = new HashSet<Conflikt.KW>();

		Integer startKW = getKW(startDate);
		Integer startYear = getYear(startDate);
		Integer endKW = getKW(endDate);
		Integer endYear = getYear(endDate);

		int startZaehler = startKW;
		for (int year = startYear; year <= endYear; year++)
		{
			int endZaehler = 52;
			if (year == endYear)
				endZaehler = endKW;

			for (int kw = startZaehler; kw <= endZaehler; kw++)
			{
				employeesKWs.add(new KW(year, kw));
			}
			startZaehler = 1;
		}

		return employeesKWs;
	}

	public boolean checkAllEmployees()
	{
		return true;
	}

	public boolean checkVacation(VacationEvent vacationEvent)
	{
		return true;
	}

	public boolean checkAllVacations()
	{
		return true;
	}

	public boolean checkShift(Shift shift)
	{
		return true;
	}

	public boolean checkAllShifts()
	{
		return true;
	}
}
