package app.riskapp.entity;

/**
 * Ftp1PrdtIrAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1PrdtIrAdjust implements java.io.Serializable {

	// Fields

	private String ajustId;
	private String custCreditLvl;
	private String brNo;
	private Double adjustValue;
	private String lastModifyTime;
	private String lastModifyTelNo;

	// Constructors

	/** default constructor */
	public Ftp1PrdtIrAdjust() {
	}

	/** minimal constructor */
	public Ftp1PrdtIrAdjust(String ajustId) {
		this.ajustId = ajustId;
	}

	/** full constructor */
	public Ftp1PrdtIrAdjust(String ajustId, String custCreditLvl, String brNo,
			Double adjustValue, String lastModifyTime, String lastModifyTelNo) {
		this.ajustId = ajustId;
		this.custCreditLvl = custCreditLvl;
		this.brNo = brNo;
		this.adjustValue = adjustValue;
		this.lastModifyTime = lastModifyTime;
		this.lastModifyTelNo = lastModifyTelNo;
	}

	// Property accessors

	public String getAjustId() {
		return this.ajustId;
	}

	public void setAjustId(String ajustId) {
		this.ajustId = ajustId;
	}

	public String getCustCreditLvl() {
		return this.custCreditLvl;
	}

	public void setCustCreditLvl(String custCreditLvl) {
		this.custCreditLvl = custCreditLvl;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public Double getAdjustValue() {
		return this.adjustValue;
	}

	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}

	public String getLastModifyTime() {
		return this.lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getLastModifyTelNo() {
		return this.lastModifyTelNo;
	}

	public void setLastModifyTelNo(String lastModifyTelNo) {
		this.lastModifyTelNo = lastModifyTelNo;
	}

}