package de.fh_dortmund.ticket_system.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import de.fh_dortmund.ticket_system.entity.Employee;
import de.jollyday.Holiday;
import de.jollyday.HolidayManager;

/**
 * Helperclass for Holiday calender.
 * 
 * @author Hendrik Hagmans
 * 
 */
public class HolidayUtil {

	/**
	 * Returns a set of holidays for a given user.
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	public static Set<Holiday> getHolidaysforUser(Employee emp) {
		HolidayManager manager;
		Set<Holiday> holidays = null;

		HolidayCalendarType usersHolidayCalendarType = emp
				.getHolidayCalendarType();
		manager = HolidayManager.getInstance(usersHolidayCalendarType
				.getCountry());

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);

		if (!usersHolidayCalendarType.getState().isEmpty()) {
			holidays = manager.getHolidays(year,
					usersHolidayCalendarType.getState());
			holidays.addAll(manager.getHolidays(year + 1,
					usersHolidayCalendarType.getState()));

		} else {
			holidays = manager.getHolidays(year);
			holidays.addAll(manager.getHolidays(year + 1));
		}

		return holidays;
	}

	/**
	 * Returns the number of holidays between two dates for a given user.
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	public static int getNumberofHolidaysBetweenTwoDates(Employee emp,
			Date startDate, Date endDate) {
		Set<Holiday> holidays = getHolidaysforUser(emp);

		if (holidays == null) {
			return 0;
		}

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
