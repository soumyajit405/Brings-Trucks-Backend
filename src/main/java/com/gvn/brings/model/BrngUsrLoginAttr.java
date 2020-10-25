package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRNG_USR_LOGIN_ATTR database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_LOGIN_ATTR")
@NamedQuery(name="BrngUsrLoginAttr.findAll", query="SELECT b FROM BrngUsrLoginAttr b")
public class BrngUsrLoginAttr extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="effective_date", nullable=false)
	private Date effectiveDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_time", nullable=false)
	private Date loginTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="logout_time")
	private Date logoutTime;

	//bi-directional many-to-one association to BrngUsrLogin
	@ManyToOne
	@JoinColumn(name="usr_login_id", nullable=false)
	private BrngUsrLogin brngUsrLogin;

	public BrngUsrLoginAttr() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public BrngUsrLogin getBrngUsrLogin() {
		return this.brngUsrLogin;
	}

	public void setBrngUsrLogin(BrngUsrLogin brngUsrLogin) {
		this.brngUsrLogin = brngUsrLogin;
	}

}