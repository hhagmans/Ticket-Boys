package de.fh_dortmund.ticket_system.util;

import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedProperty;

import org.apache.commons.mail.EmailException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import de.fh_dortmund.ticket_system.business.EmployeeData;
import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.entity.Shift;
import de.fh_dortmund.ticket_system.util.*;

public class DailyChecker
{	
	@ManagedProperty("#{EmailUtil}")
	static EmailUtil emailUtil;
	
	static GregorianCalendar cal = new GregorianCalendar();
	static int currentweek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
	static int currentyear = cal.get(GregorianCalendar.YEAR);
	
	
	public static Employee getLatestEmployee(ShiftData shiftData) {
		Employee latestEmployee = null;
		int calcweek = currentweek;
		int calcyear = currentyear;
		if (calcweek > 50) {
			calcweek = calcweek - 50;
			calcyear++;
		}
		else {
			calcweek = calcweek + 2;
		}
		String currentRowKey = calcweek + "-" + calcweek;
		latestEmployee = shiftData.findByID(currentRowKey).getDispatcher();
		return latestEmployee;
	}
	
	public static int getLatestKW(ShiftData shiftData) {
		int latestKW = 0;
		int calcweek = currentweek;
		int calcyear = currentyear;
		if (calcweek > 50) {
			calcweek = calcweek - 50;
			calcyear++;
		}
		else {
			calcweek = calcweek + 2;
		}
		String currentRowKey = calcweek + "-" + calcweek;
		latestKW = shiftData.findByID(currentRowKey).getWeekNumber();
		return latestKW;
	}
	
	public static void check(ShiftData shiftData)
	{
		/**
		try
		{

			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			// and start it off
			scheduler.start();
			*/
			try {
				String msg = "Hallo " + getLatestEmployee(shiftData).getFullName() + ",\n\n"
						+ "Sie sind in KW " + getLatestKW(shiftData) + " als Dispatcher eingetragen. Ihr Einsatz beginnt in Kürze.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem";
				EmailUtil.sendEmail(msg, "ticketboys1337@gmail.com");
			} catch (EmailException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/**
			try
			{
				Thread.sleep(1000000);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			scheduler.shutdown();
		
		}
		catch (SchedulerException se)
		{
			se.printStackTrace();
		} */
	}
}
