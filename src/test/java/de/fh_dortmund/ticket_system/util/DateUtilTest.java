package de.fh_dortmund.ticket_system.util;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.junit.Test;

import de.fh_dortmund.ticket_system.entity.Week;

public class DateUtilTest {

	@Test
	public void testCreateDate() {
		Date startDate = DateUtil.createDate(2013, 11, 25);

		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("CET"),
				Locale.GERMANY);
		cal.setTime(startDate);

		assertEquals(2013, cal.get(Calendar.YEAR));
		assertEquals(11, cal.get(Calendar.MONTH) + 1);
		assertEquals(25, cal.get(Calendar.DATE));
	}

	@Test
	public void testGetWeekNumber() {
		Date startDate = DateUtil.createDate(2013, 11, 25);

		int kw = DateUtil.getWeekNumber(startDate);

		assertEquals(48, kw);
	}

	@Test
	public void testSameWeek() {
		Date date1 = new Date(123456);
		Date date2 = new Date(123456);

		DateUtil.sameWeek(date1, date2);
	}

	@Test
	public void testGetYear() {
		Date startDate = DateUtil.createDate(2013, 11, 25);

		int kw = DateUtil.getYear(startDate);

		assertEquals(2013, kw);
	}

	@Test
	public void checkGetKwFromDateWithCorrectDates() {
		Date startDate = DateUtil.createDate(2011, 8, 12);
		Date endDate = DateUtil.createDate(2012, 4, 13);

		Set<Week> count = DateUtil.getWeeksFromDates(startDate, endDate);

		// 12 August 2011 -> Week 32
		// 13 April 2012 -> Week 15
		// (52 - 32 +1 ) + 15 = 35
		assertEquals(36, count.size());
	}

	@Test
	public void checkGetKwFromDateWithNotCorrectDates() {
		Date startDate = DateUtil.createDate(2012, 8, 12);
		Date endDate = DateUtil.createDate(2011, 4, 13);

		Set<Week> count = DateUtil.getWeeksFromDates(startDate, endDate);

		assertEquals(0, count.size());
	}

	@Test
	public void checkGetKwFromDateSameDates() {
		Date startDate = DateUtil.createDate(2012, 8, 12);
		Date endDate = DateUtil.createDate(2012, 8, 12);

		Set<Week> count = DateUtil.getWeeksFromDates(startDate, endDate);

		assertEquals(1, count.size());
	}
}
