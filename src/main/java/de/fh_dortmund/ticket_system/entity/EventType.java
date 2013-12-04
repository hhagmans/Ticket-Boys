package de.fh_dortmund.ticket_system.entity;

public enum EventType {
	vacation("Urlaub"), other("Sonstiges"), dispatcher("Dispatcher-Dienst");

	private String name;

	private EventType(String name) {
		this.setName(name);

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
