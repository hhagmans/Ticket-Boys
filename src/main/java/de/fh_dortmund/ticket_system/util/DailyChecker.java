package de.fh_dortmund.ticket_system.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;

import de.fh_dortmund.ticket_system.entity.Conflict;
import de.fh_dortmund.ticket_system.entity.Employee;
import de.fh_dortmund.ticket_system.persistence.ConflictDao;
import de.fh_dortmund.ticket_system.persistence.ShiftDao;

/**
 * Checks daily for conflicts and upcoming shifts and reminds employees.
 * 
 * @author Ticket-Boys
 * 
 */
@ManagedBean
@ApplicationScoped
public class DailyChecker implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DailyChecker.class);

	public static Employee getLatestEmployee(ShiftDao shiftDao) {
		GregorianCalendar cal = new GregorianCalendar();

		Employee latestEmployee = null;
		int calcweek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
		int calcyear = cal.get(GregorianCalendar.YEAR);
		if (calcweek > 50) {
			calcweek = calcweek - 50;
			calcyear++;
		} else {
			calcweek = calcweek + 2;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		latestEmployee = shiftDao.findById(currentRowKey).getDispatcher();
		return latestEmployee;
	}

	public static Employee getSoonestEmployee(ShiftDao shiftDao) {
		GregorianCalendar cal = new GregorianCalendar();

		Employee soonestEmployee = null;
		int calcweek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
		int calcyear = cal.get(GregorianCalendar.YEAR);
		if (calcweek > 51) {
			calcweek = calcweek - 50;
			calcyear++;
		} else {
			calcweek = calcweek + 1;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		soonestEmployee = shiftDao.findById(currentRowKey).getDispatcher();
		return soonestEmployee;
	}

	public static int getLatestKW(ShiftDao shiftDao) {
		GregorianCalendar cal = new GregorianCalendar();

		int latestKW = 0;
		int calcweek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
		int calcyear = cal.get(GregorianCalendar.YEAR);
		if (calcweek > 50) {
			calcweek = calcweek - 50;
			calcyear++;
		} else {
			calcweek = calcweek + 2;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		latestKW = shiftDao.findById(currentRowKey).getWeek().getWeekNumber();

		return latestKW;
	}

	public static int getSoonestKW(ShiftDao shiftDao) {
		GregorianCalendar cal = new GregorianCalendar();

		int soonestKW = 0;
		int calcweek = cal.get(GregorianCalendar.WEEK_OF_YEAR);
		int calcyear = cal.get(GregorianCalendar.YEAR);
		if (calcweek > 51) {
			calcweek = calcweek - 50;
			calcyear++;
		} else {
			calcweek = calcweek + 1;
		}
		String currentRowKey = calcyear + "-" + calcweek;
		soonestKW = shiftDao.findById(currentRowKey).getWeek().getWeekNumber();

		return soonestKW;
	}

	/**
	 * Trigger checks and sent emails to employees for the next shift
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	public static void trigger(ShiftDao shiftDao) {

		try {
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
			log.info("ShiftReminder: Email send to : "
					+ latestEmployee.getEmail());
			log.info("ShiftReminder: Email send to : "
					+ soonestEmployee.getEmail());
		} catch (Exception e1) {
			log.error(e1);
		}
	}

	/**
	 * Trigger checks for conflicts and send emails to employees who have
	 * unsolved conflicts
	 * 
	 * @author Ticket-Boys
	 * 
	 */
	public static void conflictTrigger(ConflictDao conflictDao) {

		GregorianCalendar cal = new GregorianCalendar();
		Date currentTime = cal.getTime();
		int dom = cal.get(GregorianCalendar.DATE);
		cal.set(GregorianCalendar.DATE, 10 + dom);
		Date inTenDays = cal.getTime();

		String subject = "Konflikt mit Dispatcher-Schicht";

		List<Employee> employees = new ArrayList<Employee>();

		List<Conflict> findAll = conflictDao.findAll();

		log.debug("ConflictReminder: Found " + findAll.size()
				+ " conflicts. Now checking for unsolved ones");

		for (Conflict conflict : findAll) {
			if (conflict.getSolved()) {
				continue;
			}

			Date startDateofConflict = conflict.getWeek().getStartDate();

			if (startDateofConflict.after(currentTime)
					&& startDateofConflict.before(inTenDays)) {
				employees.add(conflict.getEmployee());
			}
		}

		log.debug("ConflictReminder: Going to send " + employees.size()
				+ " emails.");

		for (Employee employee : employees) {
			String msg = "Hallo "
					+ employee.getFullName()
					+ ",\n\n"
					+ " Es besteht noch ein Konflikt mit Ihrer kommenden Dispatcher-Schicht.\n Bitte markieren Sie ihn als gelöst, falls Sie die Überschneidung manuell gelöst haben.\n\nWeitere Details finden Sie unter http://localhost:8080/TicketSystem";

			try {
				log.info("ConflictReminder: Sending Email to: "
						+ employee.getEmail());
				EmailUtil.sendEmailWithSubject(subject, msg,
						employee.getEmail());
			} catch (Exception e) {
				log.error(e);
			}
		}

	}
}
