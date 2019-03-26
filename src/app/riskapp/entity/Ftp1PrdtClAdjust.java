package app.riskapp.entity;

/**
 * Ftp1PrdtClAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1PrdtClAdjust implements java.io.Serializable {

	// Fields

	private String adjustId;
	private String productName;
	private String productNo;
	private String brNo;
	private Double adjustValue;
	private String modifyTime;
	private String modifyTelno;
	private String businessNo;

	// Constructors

	/** default constructor */
	public Ftp1PrdtClAdjust() {
	}

	/** minimal constructor */
	public Ftp1PrdtClAdjust(String adjustId) {
		this.adjustId = adjustId;
	}

	/** full constructor */
	public Ftp1PrdtClAdjust(String adjustId, String productName,
			String productNo, String brNo, Double adjustValue,
			String modifyTime, String modifyTelno) {
		this.adjustId = adjustId;
		this.productName = productName;
		this.productNo = productNo;
		this.brNo = brNo;
		this.adjustValue = adjustValue;
		this.modifyTime = modifyTime;
		this.modifyTelno = modifyTelno;
	}
	

	// Property accessors

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getAdjustId() {
		return this.adjustId;
	}

	public void setAdjustId(String adjustId) {
		this.adjustId = adjustId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNo() {
		return this.productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
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

	public String getModifyTime() {
		return this.modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyTelno() {
		return this.modifyTelno;
	}

	public void setModifyTelno(String modifyTelno) {
		this.modifyTelno = modifyTelno;
	}

}