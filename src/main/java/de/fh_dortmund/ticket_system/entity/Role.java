package de.fh_dortmund.ticket_system.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public enum Role { admin("Admin"), guest("Gast"), dispatcher("Dispatcher"), vacationer("Urlauber");
	
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
