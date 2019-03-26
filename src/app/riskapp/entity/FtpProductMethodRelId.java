package app.riskapp.entity;

/**
 * FtpProductMethodRelId entity. @author MyEclipse Persistence Tools
 */

public class FtpProductMethodRelId implements java.io.Serializable {

	// Fields

	private String curNo;
	private String customerType;
	private String termType;
	private String rateType;
	private String amtType;
	private String businessName;
	private String businessNo;
	private String productName;
	private String productNo;
	private String methodName;
	private String methodNo;
	private Integer assignTerm;
	private Double adjustRate;
	private String curveNo;
	private String methodTypeNo;

	// Constructors

	/** default constructor */
	public FtpProductMethodRelId() {
	}

	/** full constructor */
	public FtpProductMethodRelId(String curNo, String customerType,
			String termType, String rateType, String amtType,
			String businessName, String businessNo, String productName,
			String productNo, String methodName, String methodNo,
			Integer assignTerm, Double adjustRate, String curveNo,
			String methodTypeNo) {
		this.curNo = curNo;
		this.customerType = customerType;
		this.termType = termType;
		this.rateType = rateType;
		this.amtType = amtType;
		this.businessName = businessName;
		this.businessNo = businessNo;
		this.productName = productName;
		this.productNo = productNo;
		this.methodName = methodName;
		this.methodNo = methodNo;
		this.assignTerm = assignTerm;
		this.adjustRate = adjustRate;
		this.curveNo = curveNo;
		this.methodTypeNo = methodTypeNo;
	}

	// Property accessors

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCustomerType() {
		return this.customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getTermType() {
		return this.termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getRateType() {
		return this.rateType;
	}

	public void setRateType(String rateType) {
		this.rateType = rateType;
	}

	public String getAmtType() {
		return this.amtType;
	}

	public void setAmtType(String amtType) {
		this.amtType = amtType;
	}

	public String getBusinessName() {
		return this.businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
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

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodNo() {
		return this.methodNo;
	}

	public void setMethodNo(String methodNo) {
		this.methodNo = methodNo;
	}

	public Integer getAssignTerm() {
		return this.assignTerm;
	}

	public void setAssignTerm(Integer assignTerm) {
		this.assignTerm = assignTerm;
	}

	public Double getAdjustRate() {
		return this.adjustRate;
	}

	public void setAdjustRate(Double adjustRate) {
		this.adjustRate = adjustRate;
	}

	public String getCurveNo() {
		return this.curveNo;
	}

	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}

	public String getMethodTypeNo() {
		return this.methodTypeNo;
	}

	public void setMethodTypeNo(String methodTypeNo) {
		this.methodTypeNo = methodTypeNo;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FtpProductMethodRelId))
			return false;
		FtpProductMethodRelId castOther = (FtpProductMethodRelId) other;

		return ((this.getCurNo() == castOther.getCurNo()) || (this.getCurNo() != null
				&& castOther.getCurNo() != null && this.getCurNo().equals(
				castOther.getCurNo())))
				&& ((this.getCustomerType() == castOther.getCustomerType()) || (this
						.getCustomerType() != null
						&& castOther.getCustomerType() != null && this
						.getCustomerType().equals(castOther.getCustomerType())))
				&& ((this.getTermType() == castOther.getTermType()) || (this
						.getTermType() != null
						&& castOther.getTermType() != null && this
						.getTermType().equals(castOther.getTermType())))
				&& ((this.getRateType() == castOther.getRateType()) || (this
						.getRateType() != null
						&& castOther.getRateType() != null && this
						.getRateType().equals(castOther.getRateType())))
				&& ((this.getAmtType() == castOther.getAmtType()) || (this
						.getAmtType() != null
						&& castOther.getAmtType() != null && this.getAmtType()
						.equals(castOther.getAmtType())))
				&& ((this.getBusinessName() == castOther.getBusinessName()) || (this
						.getBusinessName() != null
						&& castOther.getBusinessName() != null && this
						.getBusinessName().equals(castOther.getBusinessName())))
				&& ((this.getBusinessNo() == castOther.getBusinessNo()) || (this
						.getBusinessNo() != null
						&& castOther.getBusinessNo() != null && this
						.getBusinessNo().equals(castOther.getBusinessNo())))
				&& ((this.getProductName() == castOther.getProductName()) || (this
						.getProductName() != null
						&& castOther.getProductName() != null && this
						.getProductName().equals(castOther.getProductName())))
				&& ((this.getProductNo() == castOther.getProductNo()) || (this
						.getProductNo() != null
						&& castOther.getProductNo() != null && this
						.getProductNo().equals(castOther.getProductNo())))
				&& ((this.getMethodName() == castOther.getMethodName()) || (this
						.getMethodName() != null
						&& castOther.getMethodName() != null && this
						.getMethodName().equals(castOther.getMethodName())))
				&& ((this.getMethodNo() == castOther.getMethodNo()) || (this
						.getMethodNo() != null
						&& castOther.getMethodNo() != null && this
						.getMethodNo().equals(castOther.getMethodNo())))
				&& ((this.getAssignTerm() == castOther.getAssignTerm()) || (this
						.getAssignTerm() != null
						&& castOther.getAssignTerm() != null && this
						.getAssignTerm().equals(castOther.getAssignTerm())))
				&& ((this.getAdjustRate() == castOther.getAdjustRate()) || (this
						.getAdjustRate() != null
						&& castOther.getAdjustRate() != null && this
						.getAdjustRate().equals(castOther.getAdjustRate())))
				&& ((this.getCurveNo() == castOther.getCurveNo()) || (this
						.getCurveNo() != null
						&& castOther.getCurveNo() != null && this.getCurveNo()
						.equals(castOther.getCurveNo())))
				&& ((this.getMethodTypeNo() == castOther.getMethodTypeNo()) || (this
						.getMethodTypeNo() != null
						&& castOther.getMethodTypeNo() != null && this
						.getMethodTypeNo().equals(castOther.getMethodTypeNo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCurNo() == null ? 0 : this.getCurNo().hashCode());
		result = 37
				* result
				+ (getCustomerType() == null ? 0 : this.getCustomerType()
						.hashCode());
		result = 37 * result
				+ (getTermType() == null ? 0 : this.getTermType().hashCode());
		result = 37 * result
				+ (getRateType() == null ? 0 : this.getRateType().hashCode());
		result = 37 * result
				+ (getAmtType() == null ? 0 : this.getAmtType().hashCode());
		result = 37
				* result
				+ (getBusinessName() == null ? 0 : this.getBusinessName()
						.hashCode());
		result = 37
				* result
				+ (getBusinessNo() == null ? 0 : this.getBusinessNo()
						.hashCode());
		result = 37
				* result
				+ (getProductName() == null ? 0 : this.getProductName()
						.hashCode());
		result = 37 * result
				+ (getProductNo() == null ? 0 : this.getProductNo().hashCode());
		result = 37
				* result
				+ (getMethodName() == null ? 0 : this.getMethodName()
						.hashCode());
		result = 37 * result
				+ (getMethodNo() == null ? 0 : this.getMethodNo().hashCode());
		result = 37
				* result
				+ (getAssignTerm() == null ? 0 : this.getAssignTerm()
						.hashCode());
		result = 37
				* result
				+ (getAdjustRate() == null ? 0 : this.getAdjustRate()
						.hashCode());
		result = 37 * result
				+ (getCurveNo() == null ? 0 : this.getCurveNo().hashCode());
		result = 37
				* result
				+ (getMethodTypeNo() == null ? 0 : this.getMethodTypeNo()
						.hashCode());
		return result;
	}

}