package de.fh_dortmund.ticket_system.entity;

public enum EventType {
	vacation("Urlaub"), other("Sonstiges"), dispatcher("Dispatcher-Dienst");

	private String name;

	private EventType(String name) {
		this.setName(name);

	}

	public EventType getEventTypeForString(String eventType) {
		if (eventType.equals(EventType.vacation)) {
			return vacation;
		}
		if (eventType.equals(EventType.other)) {
			return other;
		}

		if (eventType.equals(EventType.dispatcher)) {
			return dispatcher;
		}

		return other;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
