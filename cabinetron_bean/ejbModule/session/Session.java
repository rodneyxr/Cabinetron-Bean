package session;

import java.io.Serializable;

public class Session implements Serializable {
	private static final long serialVersionUID = 7648688658648829803L;

	enum Role {
		Admin, ProdManager, InvManager
	}

	private Role role;
	private User user;

	public Session(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public boolean canViewProductTemplates() {
		return role.equals(Role.ProdManager) || role.equals(Role.Admin);
	}

	public boolean canAddProductTemplates() {
		return role.equals(Role.ProdManager) || role.equals(Role.Admin);
	}

	public boolean canDeleteProductTemplates() {
		return role.equals(Role.ProdManager) || role.equals(Role.Admin);
	}

	public boolean canCreateProducts() {
		return role.equals(Role.ProdManager) || role.equals(Role.Admin);
	}

	public boolean canViewInventory() {
		return role.equals(Role.ProdManager) || role.equals(Role.InvManager) || role.equals(Role.Admin);
	}

	public boolean canAddInventory() {
		return role.equals(Role.InvManager) || role.equals(Role.Admin);
	}

	public boolean canDeleteInventory() {
		return role.equals(Role.Admin);
	}

	public boolean canViewParts() {
		return role.equals(Role.ProdManager) || role.equals(Role.InvManager) || role.equals(Role.Admin);
	}

	public boolean canAddParts() {
		return role.equals(Role.InvManager) || role.equals(Role.Admin);
	}

	public boolean canDeleteParts() {
		return role.equals(Role.Admin);
	}

	public User getUser() {
		return user;
	}

}
