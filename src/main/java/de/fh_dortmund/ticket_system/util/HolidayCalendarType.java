package de.fh_dortmund.ticket_system.util;

import de.jollyday.HolidayCalendar;

public enum HolidayCalendarType {

	germanyNRW("Deutschland, NRW", HolidayCalendar.GERMANY, "nw"), germanyHessen("Deutschland, Hessen",
		HolidayCalendar.GERMANY, "he"), bulgariaAll("Bulgarien, Überall", HolidayCalendar.BULGARIA, "");

	private String label;
	private HolidayCalendar country;

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public HolidayCalendar getCountry()
	{
		return country;
	}

	public void setCountry(HolidayCalendar country)
	{
		this.country = country;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	private String state;

	private HolidayCalendarType(String label, HolidayCalendar country, String state)
	{
		this.label = label;
		this.country = country;
		this.state = state;
	}

}
