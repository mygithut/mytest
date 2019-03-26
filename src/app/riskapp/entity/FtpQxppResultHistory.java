package app.riskapp.entity;

import java.math.BigDecimal;

/**
 * FtpQxppResultHistory entity. @author MyEclipse Persistence Tools
 */

public class FtpQxppResultHistory implements java.io.Serializable {

	// Fields

	private Long resultId;
	private String curNo;
	private String brNo;
	private String acId;
	private String businessName;
	private String businessNo;
	private String productName;
	private String productNo;
	private String wrkTime;
	private Double ftpPrice;
	private String methodNo;
	private String curveNo;
	private Short wrkNum;
	private Double adjustAmt;
	private String telNo;
	private String ftpTelNo;
	private String acSqen;
	private String isZq;
	private String cusName;
	private String opnDate;
	private Double amt;
	private Double bal;
	private Double rate;
	private Integer term;
	private String mtrDate;
	private String wrkSysDate;
	private String kmh;
	private String khjl;
	private Double sjsflx;

	// Constructors

	/** default constructor */
	public FtpQxppResultHistory() {
	}

	/** minimal constructor */
	public FtpQxppResultHistory(Long resultId) {
		this.resultId = resultId;
	}

	/** full constructor */
	public FtpQxppResultHistory(Long resultId, String curNo, String brNo,
			String acId, String businessName, String businessNo,
			String productName, String productNo, String wrkTime,
			Double ftpPrice, String methodNo, String curveNo, Short wrkNum,
			Double adjustAmt, String telNo, String ftpTelNo, String acSqen,
			String isZq, String cusName, String opnDate, Double amt,
			Double bal, Double rate, Integer term, String mtrDate,
			String wrkSysDate, String kmh, String khjl, Double sjsflx) {
		this.resultId = resultId;
		this.curNo = curNo;
		this.brNo = brNo;
		this.acId = acId;
		this.businessName = businessName;
		this.businessNo = businessNo;
		this.productName = productName;
		this.productNo = productNo;
		this.wrkTime = wrkTime;
		this.ftpPrice = ftpPrice;
		this.methodNo = methodNo;
		this.curveNo = curveNo;
		this.wrkNum = wrkNum;
		this.adjustAmt = adjustAmt;
		this.telNo = telNo;
		this.ftpTelNo = ftpTelNo;
		this.acSqen = acSqen;
		this.isZq = isZq;
		this.cusName = cusName;
		this.opnDate = opnDate;
		this.amt = amt;
		this.bal = bal;
		this.rate = rate;
		this.term = term;
		this.mtrDate = mtrDate;
		this.wrkSysDate = wrkSysDate;
		this.kmh = kmh;
		this.khjl = khjl;
		this.sjsflx = sjsflx;
	}

	// Property accessors

	public Long getResultId() {
		return this.resultId;
	}

	public void setResultId(Long resultId) {
		this.resultId = resultId;
	}

	public String getCurNo() {
		return this.curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getBrNo() {
		return this.brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getAcId() {
		return this.acId;
	}

	public void setAcId(String acId) {
		this.acId = acId;
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

	public String getWrkTime() {
		return this.wrkTime;
	}

	public void setWrkTime(String wrkTime) {
		this.wrkTime = wrkTime;
	}

	public Double getFtpPrice() {
		return this.ftpPrice;
	}

	public void setFtpPrice(Double ftpPrice) {
		this.ftpPrice = ftpPrice;
	}

	public String getMethodNo() {
		return this.methodNo;
	}

	public void setMethodNo(String methodNo) {
		this.methodNo = methodNo;
	}

	public String getCurveNo() {
		return this.curveNo;
	}

	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}

	public Short getWrkNum() {
		return this.wrkNum;
	}

	public void setWrkNum(Short wrkNum) {
		this.wrkNum = wrkNum;
	}

	public Double getAdjustAmt() {
		return this.adjustAmt;
	}

	public void setAdjustAmt(Double adjustAmt) {
		this.adjustAmt = adjustAmt;
	}

	public String getTelNo() {
		return this.telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getFtpTelNo() {
		return this.ftpTelNo;
	}

	public void setFtpTelNo(String ftpTelNo) {
		this.ftpTelNo = ftpTelNo;
	}

	public String getAcSqen() {
		return this.acSqen;
	}

	public void setAcSqen(String acSqen) {
		this.acSqen = acSqen;
	}

	public String getIsZq() {
		return this.isZq;
	}

	public void setIsZq(String isZq) {
		this.isZq = isZq;
	}

	public String getCusName() {
		return this.cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public String getOpnDate() {
		return this.opnDate;
	}

	public void setOpnDate(String opnDate) {
		this.opnDate = opnDate;
	}

	public Double getAmt() {
		return this.amt;
	}

	public void setAmt(Double amt) {
		this.amt = amt;
	}

	public Double getBal() {
		return this.bal;
	}

	public void setBal(Double bal) {
		this.bal = bal;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Integer getTerm() {
		return this.term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getMtrDate() {
		return this.mtrDate;
	}

	public void setMtrDate(String mtrDate) {
		this.mtrDate = mtrDate;
	}

	public String getWrkSysDate() {
		return this.wrkSysDate;
	}

	public void setWrkSysDate(String wrkSysDate) {
		this.wrkSysDate = wrkSysDate;
	}

	public String getKmh() {
		return this.kmh;
	}

	public void setKmh(String kmh) {
		this.kmh = kmh;
	}

	public String getKhjl() {
		return this.khjl;
	}

	public void setKhjl(String khjl) {
		this.khjl = khjl;
	}

	public Double getSjsflx() {
		return this.sjsflx;
	}

	public void setSjsflx(Double sjsflx) {
		this.sjsflx = sjsflx;
	}

}