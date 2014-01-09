package de.fh_dortmund.ticket_system.util;

import java.util.Calendar;
import java.util.Date;

import de.fh_dortmund.ticket_system.entity.Week;

public class WeekUtil
{

	public static Date getEndDateForWeek(Week week)
	{
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.YEAR, week.getYear());
		cal.set(Calendar.WEEK_OF_YEAR, week.getWeekNumber());
		cal.set(Calendar.DAY_OF_WEEK, 1);
		return cal.getTime();
	}

	public static Date getStartDateForWeek(Week week)
	{
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.WEEK_OF_YEAR, week.getWeekNumber());
		cal.set(Calendar.YEAR, week.getYear());
		cal.set(Calendar.DAY_OF_WEEK, 2);
		return cal.getTime();
	}

}
