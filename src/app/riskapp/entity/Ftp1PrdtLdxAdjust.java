package app.riskapp.entity;

/**
 * Ftp1PrdtLdxAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1PrdtLdxAdjust implements java.io.Serializable {

	// Fields

	private String adjustId;
	private Integer termType;
	private String brNo;
	private Double adjustValue;
	private String lastModifyTime;
	private String lastModifyTelno;

	// Constructors

	/** default constructor */
	public Ftp1PrdtLdxAdjust() {
	}

	/** minimal constructor */
	public Ftp1PrdtLdxAdjust(String adjustId) {
		this.adjustId = adjustId;
	}

	/** full constructor */
	public Ftp1PrdtLdxAdjust(String adjustId, Integer termType, String brNo,
			Double adjustValue, String lastModifyTime, String lastModifyTelno) {
		this.adjustId = adjustId;
		this.termType = termType;
		this.brNo = brNo;
		this.adjustValue = adjustValue;
		this.lastModifyTime = lastModifyTime;
		this.lastModifyTelno = lastModifyTelno;
	}

	// Property accessors

	public String getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	public Integer getTermType() {
		return this.termType;
	}

	public void setTermType(Integer termType) {
		this.termType = termType;
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

	public String getLastModifyTelno() {
		return this.lastModifyTelno;
	}

	public void setLastModifyTelno(String lastModifyTelno) {
		this.lastModifyTelno = lastModifyTelno;
	}

}