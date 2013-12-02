package de.fh_dortmund.ticket_system.util;

import it.sauronsoftware.cron4j.Scheduler;

import java.util.GregorianCalendar;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.commons.mail.EmailException;

import de.fh_dortmund.ticket_system.business.ShiftData;
import de.fh_dortmund.ticket_system.entity.Employee;

@ManagedBean
@ApplicationScoped
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
		String currentRowKey = calcyear + "-" + calcweek;
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
		String currentRowKey = calcyear + "-" + calcweek;
		latestKW = shiftData.findByID(currentRowKey).getWeek().getWeekNumber();
		return latestKW;
	}
	
	public static void check(ShiftData shiftData)
	{
		final ShiftData newShiftData = shiftData;
		// Creates a Scheduler instance.
				Scheduler s = new Scheduler();
				//schedule pattern for once a day at 8 AM
				// "0 8 * * *"
				// Schedule a once-a-minute task.
				s.schedule("* * * * *", new Runnable() {
					public void run() {
						trigger(newShiftData);
					}
				});
				// Starts the scheduler.
				s.start();
				// Will run for ten minutes.
				try {
					while (true) {
						Thread.sleep(1000L * 60L * 10L);
					}
				} catch (InterruptedException e) {
					;
				}
				// Stops the scheduler.
				s.stop();
	} 
	
	public static void trigger(ShiftData shiftData) {
		try {
			String msg = "Hallo " + getLatestEmployee(shiftData).getFullName() + ",\n\n" + "Sie sind in KW " + getLatestKW(shiftData) + " als Dispatcher eingetragen. Ihr Einsatz beginnt in Kürze.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem";
			EmailUtil.sendEmail(msg, "ticketboys1337@gmail.com");
		} catch (EmailException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
