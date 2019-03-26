package app.riskapp.entity;

/**
 * SysMenuId entity. @author MyEclipse Persistence Tools
 */

public class SysMenuId implements java.io.Serializable {

	// Fields

	private String menuNo;
	private String menuName;
	private String menuUrl;
	private String menuParent;
	private String menuLvl;
	private String menuStats;

	// Constructors

	/** default constructor */
	public SysMenuId() {
	}

	/** full constructor */
	public SysMenuId(String menuNo, String menuName, String menuUrl,
			String menuParent, String menuLvl, String menuStats) {
		this.menuNo = menuNo;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.menuParent = menuParent;
		this.menuLvl = menuLvl;
		this.menuStats = menuStats;
	}

	// Property accessors

	public String getMenuNo() {
		return this.menuNo;
	}

	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuParent() {
		return this.menuParent;
	}

	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}

	public String getMenuLvl() {
		return this.menuLvl;
	}

	public void setMenuLvl(String menuLvl) {
		this.menuLvl = menuLvl;
	}

	public String getMenuStats() {
		return this.menuStats;
	}

	public void setMenuStats(String menuStats) {
		this.menuStats = menuStats;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SysMenuId))
			return false;
		SysMenuId castOther = (SysMenuId) other;

		return ((this.getMenuNo() == castOther.getMenuNo()) || (this
				.getMenuNo() != null
				&& castOther.getMenuNo() != null && this.getMenuNo().equals(
				castOther.getMenuNo())))
				&& ((this.getMenuName() == castOther.getMenuName()) || (this
						.getMenuName() != null
						&& castOther.getMenuName() != null && this
						.getMenuName().equals(castOther.getMenuName())))
				&& ((this.getMenuUrl() == castOther.getMenuUrl()) || (this
						.getMenuUrl() != null
						&& castOther.getMenuUrl() != null && this.getMenuUrl()
						.equals(castOther.getMenuUrl())))
				&& ((this.getMenuParent() == castOther.getMenuParent()) || (this
						.getMenuParent() != null
						&& castOther.getMenuParent() != null && this
						.getMenuParent().equals(castOther.getMenuParent())))
				&& ((this.getMenuLvl() == castOther.getMenuLvl()) || (this
						.getMenuLvl() != null
						&& castOther.getMenuLvl() != null && this.getMenuLvl()
						.equals(castOther.getMenuLvl())))
				&& ((this.getMenuStats() == castOther.getMenuStats()) || (this
						.getMenuStats() != null
						&& castOther.getMenuStats() != null && this
						.getMenuStats().equals(castOther.getMenuStats())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMenuNo() == null ? 0 : this.getMenuNo().hashCode());
		result = 37 * result
				+ (getMenuName() == null ? 0 : this.getMenuName().hashCode());
		result = 37 * result
				+ (getMenuUrl() == null ? 0 : this.getMenuUrl().hashCode());
		result = 37
				* result
				+ (getMenuParent() == null ? 0 : this.getMenuParent()
						.hashCode());
		result = 37 * result
				+ (getMenuLvl() == null ? 0 : this.getMenuLvl().hashCode());
		result = 37 * result
				+ (getMenuStats() == null ? 0 : this.getMenuStats().hashCode());
		return result;
	}

}