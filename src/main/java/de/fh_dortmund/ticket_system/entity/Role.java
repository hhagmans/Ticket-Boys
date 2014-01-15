package de.fh_dortmund.ticket_system.entity;

import javax.persistence.Id;

/**
 * Determines the role of a user. Gives different rights to users.
 * 
 * @author Ticket-Boys
 * 
 */
public enum Role {
	admin("Admin"), guest("Gast"), dispatcher("Dispatcher"), vacationer(
			"Urlaubsplannutzer");

	private String roleName;

	private Role(String roleName) {
		this.setRoleName(roleName);
	}

	@Override
	public String toString() {
		return this.getRoleName();
	}

	@Id
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
