package de.fh_dortmund.ticket_system.entity;

public enum Role { admin("Admin"), guest("Gast"), dispatcher("Dispatcher"), vacationer("Urlauber");
	
	private String roleName;

	private Role(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return this.roleName;
	}
}
