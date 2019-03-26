package app.riskapp.entity;

/**
 * FtpItemToAcc entity. @author MyEclipse Persistence Tools
 */

public class FtpItemToAcc implements java.io.Serializable {

	// Fields

	private String itemNo;
	private String itemName;
	private String toAcc;
	private String superItemNo;
	private String brf;
	private String itemId;

	// Constructors

	/** default constructor */
	public FtpItemToAcc() {
	}

	/** minimal constructor */
	public FtpItemToAcc(String itemNo) {
		this.itemNo = itemNo;
	}

	/** full constructor */
	public FtpItemToAcc(String itemNo, String itemName, String toAcc,
			String superItemNo, String brf, String itemId) {
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.toAcc = toAcc;
		this.superItemNo = superItemNo;
		this.brf = brf;
		this.itemId = itemId;
	}

	// Property accessors

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

	public String getToAcc() {
		return this.toAcc;
	}

	public void setToAcc(String toAcc) {
		this.toAcc = toAcc;
	}

	public String getSuperItemNo() {
		return this.superItemNo;
	}

	public void setSuperItemNo(String superItemNo) {
		this.superItemNo = superItemNo;
	}

	public String getBrf() {
		return this.brf;
	}

	public void setBrf(String brf) {
		this.brf = brf;
	}

	public String getItemId() {
		return this.itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}