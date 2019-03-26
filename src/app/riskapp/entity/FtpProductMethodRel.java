package app.riskapp.entity;

/**
 * FtpProductMethodRel entity. @author MyEclipse Persistence Tools
 */

public class FtpProductMethodRel implements java.io.Serializable {

	// Fields

	private FtpProductMethodRelId id;

	// Constructors

	/** default constructor */
	public FtpProductMethodRel() {
	}

	/** full constructor */
	public FtpProductMethodRel(FtpProductMethodRelId id) {
		this.id = id;
	}

	// Property accessors

	public FtpProductMethodRelId getId() {
		return this.id;
	}

	public void setId(FtpProductMethodRelId id) {
		this.id = id;
	}

}