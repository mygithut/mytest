package app.riskapp.entity;

/**
 * ComSysParm entity. @author MyEclipse Persistence Tools
 */

public class ComSysParm implements java.io.Serializable {

	// Fields

	private Integer lstDate;
	private Integer sysSts;
	private Integer sysDate;
	private String realTimeInd;
	private String sysInd;

	// Constructors

	/** default constructor */
	public ComSysParm() {
	}

	/** minimal constructor */
	public ComSysParm(Integer lstDate) {
		this.lstDate = lstDate;
	}

	/** full constructor */
	public ComSysParm(Integer lstDate, Integer sysSts, Integer sysDate,
			String realTimeInd, String sysInd) {
		this.lstDate = lstDate;
		this.sysSts = sysSts;
		this.sysDate = sysDate;
		this.realTimeInd = realTimeInd;
		this.sysInd = sysInd;
	}

	// Property accessors

	public Integer getLstDate() {
		return this.lstDate;
	}

	public void setLstDate(Integer lstDate) {
		this.lstDate = lstDate;
	}

	public Integer getSysSts() {
		return this.sysSts;
	}

	public void setSysSts(Integer sysSts) {
		this.sysSts = sysSts;
	}

	public Integer getSysDate() {
		return this.sysDate;
	}

	public void setSysDate(Integer sysDate) {
		this.sysDate = sysDate;
	}

	public String getRealTimeInd() {
		return this.realTimeInd;
	}

	public void setRealTimeInd(String realTimeInd) {
		this.realTimeInd = realTimeInd;
	}

	public String getSysInd() {
		return this.sysInd;
	}

	public void setSysInd(String sysInd) {
		this.sysInd = sysInd;
	}

}