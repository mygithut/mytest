package app.riskapp.entity;

/**
 * RoleMst entity. @author MyEclipse Persistence Tools
 */

public class RoleMst implements java.io.Serializable {

	// Fields

	private String roleNo;
	private String roleName;
	private String roleLvl;
	private String purvFlag;
	private String roleMenu;
	private String flag;
	private String brf;

	// Constructors

	/** default constructor */
	public RoleMst() {
	}

	/** minimal constructor */
	public RoleMst(String roleNo) {
		this.roleNo = roleNo;
	}

	/** full constructor */
	public RoleMst(String roleNo, String roleName, String roleLvl,
			String purvFlag, String roleMenu, String flag, String brf) {
		this.roleNo = roleNo;
		this.roleName = roleName;
		this.roleLvl = roleLvl;
		this.purvFlag = purvFlag;
		this.roleMenu = roleMenu;
		this.flag = flag;
		this.brf = brf;
	}

	// Property accessors

	public String getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleLvl() {
		return this.roleLvl;
	}

	public void setRoleLvl(String roleLvl) {
		this.roleLvl = roleLvl;
	}

	public String getPurvFlag() {
		return this.purvFlag;
	}

	public void setPurvFlag(String purvFlag) {
		this.purvFlag = purvFlag;
	}

	public String getRoleMenu() {
		return this.roleMenu;
	}

	public void setRoleMenu(String roleMenu) {
		this.roleMenu = roleMenu;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBrf() {
		return this.brf;
	}

	public void setBrf(String brf) {
		this.brf = brf;
	}

}