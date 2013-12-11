package de.fh_dortmund.ticket_system.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

/**
 * Helperclass for Holiday calender.
 * 
 * @author Hendrik Hagmans
 * 
 */
public class HolidayUtil {

	public static Set<Holiday> getHolidaysforUser(Employee emp) {
		HolidayManager manager;
		Set<Holiday> holidays = null;
		if (String.valueOf(emp.getZipcode()).length() == 5) {
			manager = HolidayManager.getInstance(HolidayCalendar.GERMANY);
			if (emp.getCity().trim().toLowerCase().startsWith("marl")) {
				holidays = manager.getHolidays(
						Calendar.getInstance().get(Calendar.YEAR), "nw");
			} else if (emp.getCity().trim().toLowerCase()
					.startsWith("frankfurt")) {
				holidays = manager.getHolidays(
						Calendar.getInstance().get(Calendar.YEAR), "he");
			} else {
				holidays = manager.getHolidays(
						Calendar.getInstance().get(Calendar.YEAR), "de");
			}
		} else if (String.valueOf(emp.getZipcode()).length() == 4) {
			manager = HolidayManager.getInstance(HolidayCalendar.BULGARIA);
			holidays = manager.getHolidays(Calendar.getInstance().get(
					Calendar.YEAR));
		}
		return holidays;
	}

	public static int getNumberofHolidaysBetweenTwoDates(Employee emp,
			Date startDate, Date endDate) {
		Set<Holiday> holidays = getHolidaysforUser(emp);

		if (holidays == null)
			return 0;

		int count = 0;
		Date actHoliday;
		for (Holiday h : holidays) {
			actHoliday = h.getDate().toDateTimeAtStartOfDay().toDate();
			if (actHoliday.after(startDate) && actHoliday.before(endDate)) {
				count++;
			} else if (actHoliday.equals(startDate)
					|| actHoliday.equals(endDate)) {
				count++;
			}
		}
		return count;
	}
}
