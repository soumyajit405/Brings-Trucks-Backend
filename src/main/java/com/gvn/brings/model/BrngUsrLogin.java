package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the BRNG_USR_LOGIN database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_LOGIN")
@NamedQuery(name="BrngUsrLogin.findAll", query="SELECT b FROM BrngUsrLogin b")
public class BrngUsrLogin extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="player_id" , length=45)
	private String playerId;
	
	@Column(name="token" , length=45)
	private String token;
	
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="login_time", nullable=false)
	private Date loginTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="logout_time")
	private Date logoutTime;

	//bi-directional many-to-one association to BrngOrder
	@OneToMany(mappedBy="brngUsrLogin")
	private List<BrngOrder> brngOrders;

	//bi-directional many-to-one association to BrngOrderDelivery
	@OneToMany(mappedBy="brngUsrLogin")
	private List<BrngOrderDelivery> brngOrderDeliveries;

	//bi-directional many-to-one association to BrngUsrReg
	@ManyToOne
	@JoinColumn(name="usr_reg_id", nullable=false)
	private BrngUsrReg brngUsrReg;
	
	
	
	@ManyToOne
	@JoinColumn(name="usr_type_id")
	private BrngLkpUsrType brngLkpUsrType;

	public BrngLkpUsrType getBrngLkpUsrType() {
		return brngLkpUsrType;
	}

	public void setBrngLkpUsrType(BrngLkpUsrType brngLkpUsrType) {
		this.brngLkpUsrType = brngLkpUsrType;
	}

	//bi-directional many-to-one association to BrngUsrLoginAttr
	@OneToMany(mappedBy="brngUsrLogin")
	private List<BrngUsrLoginAttr> brngUsrLoginAttrs;

	public BrngUsrLogin() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public List<BrngOrder> getBrngOrders() {
		return this.brngOrders;
	}

	public void setBrngOrders(List<BrngOrder> brngOrders) {
		this.brngOrders = brngOrders;
	}

	public BrngOrder addBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().add(brngOrder);
		brngOrder.setBrngUsrLogin(this);

		return brngOrder;
	}

	public BrngOrder removeBrngOrder(BrngOrder brngOrder) {
		getBrngOrders().remove(brngOrder);
		brngOrder.setBrngUsrLogin(null);

		return brngOrder;
	}

	public List<BrngOrderDelivery> getBrngOrderDeliveries() {
		return this.brngOrderDeliveries;
	}

	public void setBrngOrderDeliveries(List<BrngOrderDelivery> brngOrderDeliveries) {
		this.brngOrderDeliveries = brngOrderDeliveries;
	}

	public BrngOrderDelivery addBrngOrderDelivery(BrngOrderDelivery brngOrderDelivery) {
		getBrngOrderDeliveries().add(brngOrderDelivery);
		brngOrderDelivery.setBrngUsrLogin(this);

		return brngOrderDelivery;
	}

	public BrngOrderDelivery removeBrngOrderDelivery(BrngOrderDelivery brngOrderDelivery) {
		getBrngOrderDeliveries().remove(brngOrderDelivery);
		brngOrderDelivery.setBrngUsrLogin(null);

		return brngOrderDelivery;
	}

	public BrngUsrReg getBrngUsrReg() {
		return this.brngUsrReg;
	}

	public void setBrngUsrReg(BrngUsrReg brngUsrReg) {
		this.brngUsrReg = brngUsrReg;
	}

	public List<BrngUsrLoginAttr> getBrngUsrLoginAttrs() {
		return this.brngUsrLoginAttrs;
	}

	public void setBrngUsrLoginAttrs(List<BrngUsrLoginAttr> brngUsrLoginAttrs) {
		this.brngUsrLoginAttrs = brngUsrLoginAttrs;
	}

	public BrngUsrLoginAttr addBrngUsrLoginAttr(BrngUsrLoginAttr brngUsrLoginAttr) {
		getBrngUsrLoginAttrs().add(brngUsrLoginAttr);
		brngUsrLoginAttr.setBrngUsrLogin(this);

		return brngUsrLoginAttr;
	}

	public BrngUsrLoginAttr removeBrngUsrLoginAttr(BrngUsrLoginAttr brngUsrLoginAttr) {
		getBrngUsrLoginAttrs().remove(brngUsrLoginAttr);
		brngUsrLoginAttr.setBrngUsrLogin(null);

		return brngUsrLoginAttr;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}