package app.riskapp.entity;

/**
 * TelMst entity. @author MyEclipse Persistence Tools
 */

public class TelMst implements java.io.Serializable {

	// Fields

	private String telNo;
	private String telName;
	private String idNo;
	private String phone;
	private String passwd;
	private String roleNo;
	private String brNo;
	private String flag;
	private String holdDate;
	private String endDate;
	private String lstChgPwdDate;
	private String purvNo;
	private String lstLoginDate;
	private Byte pwdErrorCnt;
	private Short perpgShwLine;
	private String txBrNo;
	private String txTelNo;
	private String txDate;
	private String lastModDate;
	private String brf;

	// Constructors

	/** default constructor */
	public TelMst() {
	}

	/** minimal constructor */
	public TelMst(String telNo) {
		this.telNo = telNo;
	}

	/** full constructor */
	public TelMst(String telNo, String telName, String idNo, String phone,
			String passwd, String roleNo, String brNo, String flag,
			String holdDate, String endDate, String lstChgPwdDate,
			String purvNo, String lstLoginDate, Byte pwdErrorCnt,
			Short perpgShwLine, String txBrNo, String txTelNo, String txDate,
			String lastModDate, String brf) {
		this.telNo = telNo;
		this.telName = telName;
		this.idNo = idNo;
		this.phone = phone;
		this.passwd = passwd;
		this.roleNo = roleNo;
		this.brNo = brNo;
		this.flag = flag;
		this.holdDate = holdDate;
		this.endDate = endDate;
		this.lstChgPwdDate = lstChgPwdDate;
		this.purvNo = purvNo;
		this.lstLoginDate = lstLoginDate;
		this.pwdErrorCnt = pwdErrorCnt;
		this.perpgShwLine = perpgShwLine;
		this.txBrNo = txBrNo;
		this.txTelNo = txTelNo;
		this.txDate = txDate;
		this.lastModDate = lastModDate;
		this.brf = brf;
	}

	// Property accessors

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getTelName() {
		return this.telName;
	}

	public void setTelName(String telName) {
		this.telName = telName;
	}

	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRoleNo() {
		return this.roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getHoldDate() {
		return this.holdDate;
	}

	public void setHoldDate(String holdDate) {
		this.holdDate = holdDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getLstChgPwdDate() {
		return this.lstChgPwdDate;
	}

	public void setLstChgPwdDate(String lstChgPwdDate) {
		this.lstChgPwdDate = lstChgPwdDate;
	}

	public String getPurvNo() {
		return this.purvNo;
	}

	public void setPurvNo(String purvNo) {
		this.purvNo = purvNo;
	}

	public String getLstLoginDate() {
		return this.lstLoginDate;
	}

	public void setLstLoginDate(String lstLoginDate) {
		this.lstLoginDate = lstLoginDate;
	}

	public Byte getPwdErrorCnt() {
		return this.pwdErrorCnt;
	}

	public void setPwdErrorCnt(Byte pwdErrorCnt) {
		this.pwdErrorCnt = pwdErrorCnt;
	}

	public Short getPerpgShwLine() {
		return this.perpgShwLine;
	}

	public void setPerpgShwLine(Short perpgShwLine) {
		this.perpgShwLine = perpgShwLine;
	}

	public String getTxBrNo() {
		return this.txBrNo;
	}

	public void setTxBrNo(String txBrNo) {
		this.txBrNo = txBrNo;
	}

	public String getTxTelNo() {
		return this.txTelNo;
	}

	public void setTxTelNo(String txTelNo) {
		this.txTelNo = txTelNo;
	}

	public String getTxDate() {
		return this.txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public String getLastModDate() {
		return this.lastModDate;
	}

	public void setLastModDate(String lastModDate) {
		this.lastModDate = lastModDate;
	}

	public String getBrf() {
		return this.brf;
	}

	public void setBrf(String brf) {
		this.brf = brf;
	}

}