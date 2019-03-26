package app.riskapp.entity;

/**
 * SysMenu entity. @author MyEclipse Persistence Tools
 */

public class SysMenu implements java.io.Serializable {

	// Fields

	private SysMenuId id;

	// Constructors

	/** default constructor */
	public SysMenu() {
	}

	/** full constructor */
	public SysMenu(SysMenuId id) {
		this.id = id;
	}

	// Property accessors

	public SysMenuId getId() {
		return this.id;
	}

	public void setId(SysMenuId id) {
		this.id = id;
	}

}