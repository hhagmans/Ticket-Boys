package de.fh_dortmund.ticket_system.base;


/**
 * Represents a unique week in a year.
 * 
 * @author Alex Hofmann
 * 
 */
public class Week {
	private int year;
	private int weekNumber;

	/**
	 * Creates a Week object with the given parameters.
	 * 
	 * @param year
	 * @param weekNumber
	 */
	public Week(int year, int weekNumber) {
		this.setYear(year);
		this.setWeeknumber(weekNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Week kw = (Week) obj;
		if (this.getYear() == kw.getYear() && this.getWeekNumber() == kw.getWeekNumber())
			return true;

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + year;
		result = (prime * result) + weekNumber;
		
		return result;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return new Week(year, weekNumber);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeeknumber(int kw) {
		this.weekNumber = kw;
	}
}
