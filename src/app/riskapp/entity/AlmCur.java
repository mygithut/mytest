package app.riskapp.entity;

/**
 * AlmCur entity. @author MyEclipse Persistence Tools
 */

public class AlmCur implements java.io.Serializable {

	// Fields

	private AlmCurId id;

	// Constructors

	/** default constructor */
	public AlmCur() {
	}

	/** full constructor */
	public AlmCur(AlmCurId id) {
		this.id = id;
	}

	// Property accessors

	public AlmCurId getId() {
		return this.id;
	}

	public void setId(AlmCurId id) {
		this.id = id;
	}

}