package app.riskapp.entity;

/**
 * SysRoleMenu entity. @author MyEclipse Persistence Tools
 */

public class SysRoleMenu implements java.io.Serializable {

	// Fields

	private SysRoleMenuId id;

	// Constructors

	/** default constructor */
	public SysRoleMenu() {
	}

	/** full constructor */
	public SysRoleMenu(SysRoleMenuId id) {
		this.id = id;
	}

	// Property accessors

	public SysRoleMenuId getId() {
		return this.id;
	}

	public void setId(SysRoleMenuId id) {
		this.id = id;
	}

}