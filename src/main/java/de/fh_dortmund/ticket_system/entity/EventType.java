package de.fh_dortmund.ticket_system.entity;

public enum EventType {
	vacation("Urlaub"), other("Sonstiges"), dispatcher("Dispatcher-Dienst"), holiday("Feiertag");

	private String label;

	private EventType(String label)
	{
		this.setLabel(label);
	}

	public EventType getEventTypeForString(String eventType)
	{
		if (eventType.equals(EventType.vacation))
		{
			return vacation;
		}
		if (eventType.equals(EventType.other))
		{
			return other;
		}

		if (eventType.equals(EventType.dispatcher))
		{
			return dispatcher;
		}

		return other;
	}

	@Override
	public String toString()
	{
		return this.getLabel();
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

}
