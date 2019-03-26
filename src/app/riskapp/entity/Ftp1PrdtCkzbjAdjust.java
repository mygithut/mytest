package app.riskapp.entity;

/**
 * Ftp1PrdtCkzbjAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1PrdtCkzbjAdjust implements java.io.Serializable {

	// Fields

	private String adjustId;
	private Integer termType;
	private String brNo;
	private String adjustDate;
	private Double adjustValue;

	// Constructors

	/** default constructor */
	public Ftp1PrdtCkzbjAdjust() {
	}

	/** minimal constructor */
	public Ftp1PrdtCkzbjAdjust(String adjustId) {
		this.adjustId = adjustId;
	}

	/** full constructor */
	public Ftp1PrdtCkzbjAdjust(String adjustId, Integer termType, String brNo,
			String adjustDate, Double adjustValue) {
		this.adjustId = adjustId;
		this.termType = termType;
		this.brNo = brNo;
		this.adjustDate = adjustDate;
		this.adjustValue = adjustValue;
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

	public String getAdjustDate() {
		return this.adjustDate;
	}

	public void setAdjustDate(String adjustDate) {
		this.adjustDate = adjustDate;
	}

	public Double getAdjustValue() {
		return this.adjustValue;
	}

	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}

}