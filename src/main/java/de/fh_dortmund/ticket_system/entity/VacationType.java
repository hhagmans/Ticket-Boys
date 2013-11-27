package de.fh_dortmund.ticket_system.entity;

public enum VacationType { 
	vacation("Urlaub"), other ("Sonstiges");
	
	private String name;
	
	private VacationType(String name) {
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
