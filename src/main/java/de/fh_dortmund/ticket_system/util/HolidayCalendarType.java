package de.fh_dortmund.ticket_system.util;

import de.jollyday.HolidayCalendar;

public enum HolidayCalendarType {

	germanyNRW("Deutschland, NRW", HolidayCalendar.GERMANY, "nw"), germanyHessen("Deutschland, Hessen",
		HolidayCalendar.GERMANY, "he"), bulgariaAll("Bulgarien, Überall", HolidayCalendar.BULGARIA, "");

	private String label;
	private HolidayCalendar country;
	private String state;

	private HolidayCalendarType(String label, HolidayCalendar country, String state)
	{
		this.label = label;
		this.country = country;
		this.state = state;
	}

}
