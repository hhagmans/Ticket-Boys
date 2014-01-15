package de.fh_dortmund.ticket_system.entity;

/**
 * Eventtype used by events to determine type of event.
 * 
 * @author Ticket-Boys
 * 
 */
public enum EventType {
	vacation("Urlaub", "vacation-event"), other("Sonstiges", "other-event"), dispatcher(
			"Dispatcher-Dienst", "dispatcher-event"), holiday("Feiertag",
			"holiday-event");

	private String label;
	private String styleClass;

	private EventType(String label, String styleClass) {
		this.setLabel(label);
		this.setStyleClass(styleClass);
	}

	@Override
	public String toString() {
		return this.getLabel();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
