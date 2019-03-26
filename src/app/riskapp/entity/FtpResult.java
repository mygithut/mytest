package app.riskapp.entity;

/**
 * FtpResult entity. @author MyEclipse Persistence Tools
 */

public class FtpResult implements java.io.Serializable {

	// Fields

	private Integer resultId;
	private String brNo;
	private String prcMode;
	private String prcMethod;
	private Integer poolId;
	private Double resValue;
	private String curNo;
	private Double afresValue;
	private String memo;
	private String resDate;

	// Constructors

	/** default constructor */
	public FtpResult() {
	}

	/** minimal constructor */
	public FtpResult(Integer resultId) {
		this.resultId = resultId;
	}

	/** full constructor */
	public FtpResult(Integer resultId, String brNo, String prcMode,
			String prcMethod, Integer poolId, Double resValue, String curNo,
			Double afresValue, String memo, String resDate) {
		this.resultId = resultId;
		this.brNo = brNo;
		this.prcMode = prcMode;
		this.prcMethod = prcMethod;
		this.poolId = poolId;
		this.resValue = resValue;
		this.curNo = curNo;
		this.afresValue = afresValue;
		this.memo = memo;
		this.resDate = resDate;
	}

	// Property accessors

	public Integer getResultId() {
		return this.resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getPrcMode() {
		return this.prcMode;
	}

	public void setPrcMode(String prcMode) {
		this.prcMode = prcMode;
	}

	public String getPrcMethod() {
		return this.prcMethod;
	}

	public void setPrcMethod(String prcMethod) {
		this.prcMethod = prcMethod;
	}

	public Integer getPoolId() {
		return this.poolId;
	}

	public void setPoolId(Integer poolId) {
		this.poolId = poolId;
	}

	public Double getResValue() {
		return this.resValue;
	}

	public void setResValue(Double resValue) {
		this.resValue = resValue;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public Double getAfresValue() {
		return this.afresValue;
	}

	public void setAfresValue(Double afresValue) {
		this.afresValue = afresValue;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getResDate() {
		return this.resDate;
	}

	public void setResDate(String resDate) {
		this.resDate = resDate;
	}

}