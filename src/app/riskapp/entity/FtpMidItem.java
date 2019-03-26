package app.riskapp.entity;

/**
 * FtpMidItem entity. @author MyEclipse Persistence Tools
 */

public class FtpMidItem implements java.io.Serializable {

	// Fields

	private String itemId;
	private String itemNo;
	private String itemName;
	private String brNo;
	private String itemDate;
	private String prcMode;
	private String prcMethod;
	private Double itemAmount;
	private String curNo;
	private String ftpTerm;

	// Constructors

	/** default constructor */
	public FtpMidItem() {
	}

	/** minimal constructor */
	public FtpMidItem(String itemId) {
		this.itemId = itemId;
	}

	/** full constructor */
	public FtpMidItem(String itemId, String itemNo, String itemName,
			String brNo, String itemDate, String prcMode, String prcMethod,
			Double itemAmount, String curNo, String ftpTerm) {
		this.itemId = itemId;
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.brNo = brNo;
		this.itemDate = itemDate;
		this.prcMode = prcMode;
		this.prcMethod = prcMethod;
		this.itemAmount = itemAmount;
		this.curNo = curNo;
		this.ftpTerm = ftpTerm;
	}

	// Property accessors

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getItemDate() {
		return this.itemDate;
	}

	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
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

	public Double getItemAmount() {
		return this.itemAmount;
	}

	public void setItemAmount(Double itemAmount) {
		this.itemAmount = itemAmount;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getFtpTerm() {
		return this.ftpTerm;
	}

	public void setFtpTerm(String ftpTerm) {
		this.ftpTerm = ftpTerm;
	}

}