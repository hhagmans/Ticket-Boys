package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;
import java.util.GregorianCalendar;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.ShiftDao;

@ManagedBean
@ApplicationScoped
public class DailyChecker implements Serializable
{

	private static final long serialVersionUID = 1L;

	static GregorianCalendar cal = new GregorianCalendar();

	static int currentweek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
	static int currentyear = cal.get(GregorianCalendar.YEAR);
	static int currentday = cal.get(GregorianCalendar.DAY_OF_MONTH);
	static int currentmonth = cal.get(GregorianCalendar.MONTH);

	public static Employee getLatestEmployee(ShiftDao shiftDao)
	{
		Employee latestEmployee = null;
		int calcweek = currentweek;
		int calcyear = currentyear;
		if (calcweek > 50)
		{
			calcweek = calcweek - 50;
			calcyear++;
		}
		else
		{
			calcweek = calcweek + 2;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		latestEmployee = shiftDao.findById(currentRowKey).getDispatcher();
		return latestEmployee;
	}

	public static Employee getSoonestEmployee(ShiftDao shiftDao)
	{
		Employee soonestEmployee = null;
		int calcweek = currentweek;
		int calcyear = currentyear;
		if (calcweek > 51)
		{
			calcweek = calcweek - 50;
			calcyear++;
		}
		else
		{
			calcweek = calcweek + 1;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		soonestEmployee = shiftDao.findById(currentRowKey).getDispatcher();
		return soonestEmployee;
	}

	public static int getLatestKW(ShiftDao shiftDao)
	{
		int latestKW = 0;
		int calcweek = currentweek;
		int calcyear = currentyear;
		if (calcweek > 50)
		{
			calcweek = calcweek - 50;
			calcyear++;
		}
		else
		{
			calcweek = calcweek + 2;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		latestKW = shiftDao.findById(currentRowKey).getWeek().getWeekNumber();

		return latestKW;
	}

	public static int getSoonestKW(ShiftDao shiftDao)
	{
		int soonestKW = 0;
		int calcweek = currentweek;
		int calcyear = currentyear;
		if (calcweek > 51)
		{
			calcweek = calcweek - 50;
			calcyear++;
		}
		else
		{
			calcweek = calcweek + 1;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		soonestKW = shiftDao.findById(currentRowKey).getWeek().getWeekNumber();

		return soonestKW;
	}

	public int getCurrentDay()
	{
		return currentday;
	}

	public int getCurrentWeek()
	{
		return currentweek;
	}

	private static Logger log = Logger.getLogger(DailyChecker.class);

	public static void trigger(ShiftDao shiftDao)
	{

		System.out.println("Hello. This is DailyChecker talking!");
		System.out.println("This is my shiftData: " + shiftDao);
		try
		{
			Employee latestEmployee = getLatestEmployee(shiftDao);
			Employee soonestEmployee = getSoonestEmployee(shiftDao);
			String msg_first = "Hallo "
				+ soonestEmployee.getFullName()
				+ ",\n\n"
				+ "Sie sind in KW "
				+ getSoonestKW(shiftDao)
				+ " als Dispatcher eingetragen. Ihr Einsatz beginnt in Kürze.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem";
			String msg_last = "Hallo "
				+ latestEmployee.getFullName()
				+ ",\n\n"
				+ "Sie sind in KW "
				+ getLatestKW(shiftDao)
				+ " als Dispatcher eingetragen. Ihr Einsatz beginnt in Kürze.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem";
			// email address for soonest employee: String address =
			// getSoonestEmployee(shiftData).getKonzernID() + "@evonik.com"
			EmailUtil.sendEmail(msg_first, soonestEmployee.getEmail());
			// email address for latest employee: String address =
			// getLatestEmployee(shiftData).getKonzernID() + "@evonik.com"
			EmailUtil.sendEmail(msg_last, latestEmployee.getEmail());
			log.info("Email send");
		}
		catch (Exception e1)
		{
			log.error(e1);
			e1.printStackTrace();
		}
	}

	public static void conflictTrigger()
	{

	}
}
