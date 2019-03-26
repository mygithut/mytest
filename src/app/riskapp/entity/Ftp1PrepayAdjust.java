package app.riskapp.entity;

/**
 * Ftp1PrepayAdjust entity. @author MyEclipse Persistence Tools
 */

public class Ftp1PrepayAdjust implements java.io.Serializable {

	// Fields

	private String id;
	private String assetsType;
	private Integer minTermType;
	private Integer maxTermType;
	private Double adjustValue;
	private String brNo;
	private String lastModifyTime;
	private String lastModifyTelNo;

	// Constructors

	/** default constructor */
	public Ftp1PrepayAdjust() {
	}

	/** minimal constructor */
	public Ftp1PrepayAdjust(String id) {
		this.id = id;
	}

	/** full constructor */
	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public Ftp1PrepayAdjust(String id, String assetsType, Integer minTermType,
			Integer maxTermType, Double adjustValue, String brNo,
			String lastModifyTime, String lastModifyTelNo) {
		this.id = id;
		this.assetsType = assetsType;
		this.minTermType = minTermType;
		this.maxTermType = maxTermType;
		this.adjustValue = adjustValue;
		this.brNo = brNo;
		this.lastModifyTime = lastModifyTime;
		this.lastModifyTelNo = lastModifyTelNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAssetsType() {
		return this.assetsType;
	}

	public void setAssetsType(String assetsType) {
		this.assetsType = assetsType;
	}

	public Integer getMinTermType() {
		return minTermType;
	}

	public void setMinTermType(Integer minTermType) {
		this.minTermType = minTermType;
	}

	public Integer getMaxTermType() {
		return maxTermType;
	}

	public void setMaxTermType(Integer maxTermType) {
		this.maxTermType = maxTermType;
	}

	public Double getAdjustValue() {
		return this.adjustValue;
	}

	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
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