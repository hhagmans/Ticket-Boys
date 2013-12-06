package de.fh_dortmund.ticket_system.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import de.fh_dortmund.ticket_system.entity.Week;

/**
 * Helperclass for the Date and Week classes.
 * 
 * @author Alex Hofmann
 * 
 */
public class DateUtil {
	private static Calendar cal = new GregorianCalendar(
			TimeZone.getTimeZone("CET"), Locale.GERMANY);

	/**
	 * Checks if two dates have the same weeknumber.
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return true if same year and week, else false
	 */
	public static boolean sameWeek(Date firstDate, Date secondDate) {
		if (getYear(firstDate) == getYear(secondDate))
			if (getWeekNumber(firstDate) == getWeekNumber(secondDate))
				return true;

		return false;
	}

	/**
	 * Returns the weeknumber of a given date.
	 * 
	 * @param date
	 * @return weeknumber
	 */
	public static int getWeekNumber(Date date) {
		int weeknumber = Integer
				.valueOf(new SimpleDateFormat("w").format(date));

		return weeknumber;
	}

	/**
	 * Return the year of a given date.
	 * 
	 * @param date
	 * @return year
	 */
	public static int getYear(Date date) {
		int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(date));

		return year;
	}

	/**
	 * Creates a date object with the given parameters. Uses a
	 * GregorianCalendar.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return date object with the parameters set
	 */
	public static Date createDate(int year, int month, int day) {
		cal.clear();
		cal.set(year, month - 1, day);

		return new Date(cal.getTime().getTime());
	}

	/**
	 * Counts the weeks between two dates and return a Set of week object.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return Set of week object
	 */
	public static Set<Week> getWeeksFromDates(Date startDate, Date endDate) {
		Set<Week> weeks = new HashSet<Week>();

		Integer startWeek = getWeekNumber(startDate);
		Integer startYear = getYear(startDate);
		Integer endWeek = getWeekNumber(endDate);
		Integer endYear = getYear(endDate);

		int startZaehler = startWeek;
		for (int year = startYear; year <= endYear; year++) {
			int endZaehler = 52;
			if (year == endYear)
				endZaehler = endWeek;

			for (int kw = startZaehler; kw <= endZaehler; kw++) {
				weeks.add(new Week(year, kw));
			}
			startZaehler = 1;
		}

		return weeks;
	}
}
