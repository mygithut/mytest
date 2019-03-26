package app.riskapp.entity;

/**
 * AlmCurId entity. @author MyEclipse Persistence Tools
 */

public class AlmCurId implements java.io.Serializable {

	// Fields

	private String curno;
	private String curname;
	private String curcode;
	private Long curstate;

	// Constructors

	/** default constructor */
	public AlmCurId() {
	}

	/** full constructor */
	public AlmCurId(String curno, String curname, String curcode, Long curstate) {
		this.curno = curno;
		this.curname = curname;
		this.curcode = curcode;
		this.curstate = curstate;
	}

	// Property accessors

	public String getCurno() {
		return this.curno;
	}

	public void setCurno(String curno) {
		this.curno = curno;
	}

	public String getCurname() {
		return this.curname;
	}

	public void setCurname(String curname) {
		this.curname = curname;
	}

	public String getCurcode() {
		return this.curcode;
	}

	public void setCurcode(String curcode) {
		this.curcode = curcode;
	}

	public Long getCurstate() {
		return this.curstate;
	}

	public void setCurstate(Long curstate) {
		this.curstate = curstate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AlmCurId))
			return false;
		AlmCurId castOther = (AlmCurId) other;

		return ((this.getCurno() == castOther.getCurno()) || (this.getCurno() != null
				&& castOther.getCurno() != null && this.getCurno().equals(
				castOther.getCurno())))
				&& ((this.getCurname() == castOther.getCurname()) || (this
						.getCurname() != null
						&& castOther.getCurname() != null && this.getCurname()
						.equals(castOther.getCurname())))
				&& ((this.getCurcode() == castOther.getCurcode()) || (this
						.getCurcode() != null
						&& castOther.getCurcode() != null && this.getCurcode()
						.equals(castOther.getCurcode())))
				&& ((this.getCurstate() == castOther.getCurstate()) || (this
						.getCurstate() != null
						&& castOther.getCurstate() != null && this
						.getCurstate().equals(castOther.getCurstate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCurno() == null ? 0 : this.getCurno().hashCode());
		result = 37 * result
				+ (getCurname() == null ? 0 : this.getCurname().hashCode());
		result = 37 * result
				+ (getCurcode() == null ? 0 : this.getCurcode().hashCode());
		result = 37 * result
				+ (getCurstate() == null ? 0 : this.getCurstate().hashCode());
		return result;
	}

}