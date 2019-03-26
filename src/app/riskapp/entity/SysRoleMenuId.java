package app.riskapp.entity;

/**
 * SysRoleMenuId entity. @author MyEclipse Persistence Tools
 */

public class SysRoleMenuId implements java.io.Serializable {

	// Fields

	private String id;
	private String roleNo;
	private String menuNo;

	// Constructors

	/** default constructor */
	public SysRoleMenuId() {
	}

	/** full constructor */
	public SysRoleMenuId(String id, String roleNo, String menuNo) {
		this.id = id;
		this.roleNo = roleNo;
		this.menuNo = menuNo;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysRoleMenuId))
			return false;
		SysRoleMenuId castOther = (SysRoleMenuId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getRoleNo() == castOther.getRoleNo()) || (this
						.getRoleNo() != null
						&& castOther.getRoleNo() != null && this.getRoleNo()
						.equals(castOther.getRoleNo())))
				&& ((this.getMenuNo() == castOther.getMenuNo()) || (this
						.getMenuNo() != null
						&& castOther.getMenuNo() != null && this.getMenuNo()
						.equals(castOther.getMenuNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getRoleNo() == null ? 0 : this.getRoleNo().hashCode());
		result = 37 * result
				+ (getMenuNo() == null ? 0 : this.getMenuNo().hashCode());
		return result;
	}

}