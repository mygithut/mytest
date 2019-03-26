package app.riskapp.entity;

/**
 * BrMst entity. @author MyEclipse Persistence Tools
 */

public class BrMst implements java.io.Serializable {

	// Fields

	private String brNo;
	private String brName;
	private String chargePersonName;
	private String manageLvl;
	private String fbrCode;
	private String legalPersonFlag;
	private String conPersonName;
	private String conPhone;
	private String conAddr;
	private String postNo;
	private String brClFlag;
	private String superBrNo;
	private String fax;
	private String brf;

	// Constructors

	/** default constructor */
	public BrMst() {
	}

	/** minimal constructor */
	public BrMst(String brNo) {
		this.brNo = brNo;
	}

	/** full constructor */
	public BrMst(String brNo, String brName, String chargePersonName,
			String manageLvl, String fbrCode, String legalPersonFlag,
			String conPersonName, String conPhone, String conAddr,
			String postNo, String brClFlag, String superBrNo, String fax,
			String brf) {
		this.brNo = brNo;
		this.brName = brName;
		this.chargePersonName = chargePersonName;
		this.manageLvl = manageLvl;
		this.fbrCode = fbrCode;
		this.legalPersonFlag = legalPersonFlag;
		this.conPersonName = conPersonName;
		this.conPhone = conPhone;
		this.conAddr = conAddr;
		this.postNo = postNo;
		this.brClFlag = brClFlag;
		this.superBrNo = superBrNo;
		this.fax = fax;
		this.brf = brf;
	}

	// Property accessors

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrName() {
		return this.brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getChargePersonName() {
		return this.chargePersonName;
	}

	public void setChargePersonName(String chargePersonName) {
		this.chargePersonName = chargePersonName;
	}

	public String getManageLvl() {
		return this.manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public String getFbrCode() {
		return this.fbrCode;
	}

	public void setFbrCode(String fbrCode) {
		this.fbrCode = fbrCode;
	}

	public String getLegalPersonFlag() {
		return this.legalPersonFlag;
	}

	public void setLegalPersonFlag(String legalPersonFlag) {
		this.legalPersonFlag = legalPersonFlag;
	}

	public String getConPersonName() {
		return this.conPersonName;
	}

	public void setConPersonName(String conPersonName) {
		this.conPersonName = conPersonName;
	}

	public String getConPhone() {
		return this.conPhone;
	}

	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}

	public String getConAddr() {
		return this.conAddr;
	}

	public void setConAddr(String conAddr) {
		this.conAddr = conAddr;
	}

	public String getPostNo() {
		return this.postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getBrClFlag() {
		return this.brClFlag;
	}

	public void setBrClFlag(String brClFlag) {
		this.brClFlag = brClFlag;
	}

	public String getSuperBrNo() {
		return this.superBrNo;
	}

	public void setSuperBrNo(String superBrNo) {
		this.superBrNo = superBrNo;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBrf() {
		return this.brf;
	}

	public void setBrf(String brf) {
		this.brf = brf;
	}

}