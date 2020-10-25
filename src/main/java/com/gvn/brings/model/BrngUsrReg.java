package com.gvn.brings.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the BRNG_USR_REG database table.
 * 
 */
@Entity
@Table(name="BRNG_USR_REG")
@NamedQuery(name="BrngUsrReg.findAll", query="SELECT b FROM BrngUsrReg b")
public class BrngUsrReg extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private int id;

	

	@Column(name="email_id", nullable=false, length=45)
	private String emailId;

	
	
	@Column(nullable=false, length=100)
	private String password;

	
	
	
	@Column(name="registered_date")
	private Timestamp registeredDate;
	
	@Column(name="player_id" , length=45)
	private String playerId;



	

	
	
	public Timestamp getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Timestamp registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	//bi-directional many-to-one association to BrngUsrCode
	@OneToMany(mappedBy="brngUsrReg")
	private List<BrngUsrCode> brngUsrCodes;

	//bi-directional many-to-one association to BrngUsrLogin
	@OneToMany(mappedBy="brngUsrReg")
	private List<BrngUsrLogin> brngUsrLogins;

	//bi-directional many-to-one association to BrngUsrOtp
	@OneToMany(mappedBy="brngUsrReg")
	private List<BrngUsrOtp> brngUsrOtps;

	//bi-directional many-to-one association to BrngUsrPassChange
	@OneToMany(mappedBy="brngUsrReg")
	private List<BrngUsrPassChange> brngUsrPassChanges;

	//bi-directional many-to-one association to BrngLkpUsrRegStatus
	@ManyToOne
	@JoinColumn(name="usr_reg_status_id", nullable=false)
	private BrngLkpUsrRegStatus brngLkpUsrRegStatus;

	//bi-directional many-to-one association to BrngLkpUsrRegType
	@ManyToOne
	@JoinColumn(name="usr_reg_type_id", nullable=false)
	private BrngLkpUsrRegType brngLkpUsrRegType;
	
	@ManyToOne
	@JoinColumn(name="method_id", nullable=false)
	private BrngRegisterMethodLookup brngRegMethod;
	
	@ManyToOne
	@JoinColumn(name="version_id", nullable=false)
	private BrngRegisterDeviceVersionLookup brngRegDevVersion;
	
	
	@ManyToOne
	@JoinColumn(name="otp_validated_id", nullable=false)
	private BrngLkpOtpValidated brnglkpotpvalidated;
	
	@ManyToOne
	@JoinColumn(name="validated_id", nullable=false)
	private BrngLkpServicemanValidated brnglkpservicemanvalidated;
	
	
	@ManyToOne
	@JoinColumn(name="vehicle_type", nullable=false)
	private BrngLkpVehicleType brnglkpvehicleType;
	
	

	public BrngLkpVehicleType getBrnglkpvehicleType() {
		return brnglkpvehicleType;
	}

	public void setBrnglkpvehicleType(BrngLkpVehicleType brnglkpvehicleType) {
		this.brnglkpvehicleType = brnglkpvehicleType;
	}

	//bi-directional many-to-one association to BrngUsrRegAttr
	@OneToMany(mappedBy="brngUsrReg")
	private List<BrngUsrRegAttr> brngUsrRegAttrs;

	public BrngUsrReg() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getEmailId() {
		return this.emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<BrngUsrCode> getBrngUsrCodes() {
		return this.brngUsrCodes;
	}

	public void setBrngUsrCodes(List<BrngUsrCode> brngUsrCodes) {
		this.brngUsrCodes = brngUsrCodes;
	}

	public BrngUsrCode addBrngUsrCode(BrngUsrCode brngUsrCode) {
		getBrngUsrCodes().add(brngUsrCode);
		brngUsrCode.setBrngUsrReg(this);

		return brngUsrCode;
	}

	public BrngUsrCode removeBrngUsrCode(BrngUsrCode brngUsrCode) {
		getBrngUsrCodes().remove(brngUsrCode);
		brngUsrCode.setBrngUsrReg(null);

		return brngUsrCode;
	}

	public List<BrngUsrLogin> getBrngUsrLogins() {
		return this.brngUsrLogins;
	}

	public void setBrngUsrLogins(List<BrngUsrLogin> brngUsrLogins) {
		this.brngUsrLogins = brngUsrLogins;
	}

	public BrngUsrLogin addBrngUsrLogin(BrngUsrLogin brngUsrLogin) {
		getBrngUsrLogins().add(brngUsrLogin);
		brngUsrLogin.setBrngUsrReg(this);

		return brngUsrLogin;
	}

	public BrngUsrLogin removeBrngUsrLogin(BrngUsrLogin brngUsrLogin) {
		getBrngUsrLogins().remove(brngUsrLogin);
		brngUsrLogin.setBrngUsrReg(null);

		return brngUsrLogin;
	}

	public List<BrngUsrOtp> getBrngUsrOtps() {
		return this.brngUsrOtps;
	}

	public void setBrngUsrOtps(List<BrngUsrOtp> brngUsrOtps) {
		this.brngUsrOtps = brngUsrOtps;
	}

	public BrngUsrOtp addBrngUsrOtp(BrngUsrOtp brngUsrOtp) {
		getBrngUsrOtps().add(brngUsrOtp);
		brngUsrOtp.setBrngUsrReg(this);

		return brngUsrOtp;
	}

	public BrngUsrOtp removeBrngUsrOtp(BrngUsrOtp brngUsrOtp) {
		getBrngUsrOtps().remove(brngUsrOtp);
		brngUsrOtp.setBrngUsrReg(null);

		return brngUsrOtp;
	}

	public List<BrngUsrPassChange> getBrngUsrPassChanges() {
		return this.brngUsrPassChanges;
	}

	public void setBrngUsrPassChanges(List<BrngUsrPassChange> brngUsrPassChanges) {
		this.brngUsrPassChanges = brngUsrPassChanges;
	}

	public BrngUsrPassChange addBrngUsrPassChange(BrngUsrPassChange brngUsrPassChange) {
		getBrngUsrPassChanges().add(brngUsrPassChange);
		brngUsrPassChange.setBrngUsrReg(this);

		return brngUsrPassChange;
	}

	public BrngUsrPassChange removeBrngUsrPassChange(BrngUsrPassChange brngUsrPassChange) {
		getBrngUsrPassChanges().remove(brngUsrPassChange);
		brngUsrPassChange.setBrngUsrReg(null);

		return brngUsrPassChange;
	}

	public BrngLkpUsrRegStatus getBrngLkpUsrRegStatus() {
		return this.brngLkpUsrRegStatus;
	}

	public void setBrngLkpUsrRegStatus(BrngLkpUsrRegStatus brngLkpUsrRegStatus) {
		this.brngLkpUsrRegStatus = brngLkpUsrRegStatus;
	}

	public BrngLkpUsrRegType getBrngLkpUsrRegType() {
		return this.brngLkpUsrRegType;
	}

	public void setBrngLkpUsrRegType(BrngLkpUsrRegType brngLkpUsrRegType) {
		this.brngLkpUsrRegType = brngLkpUsrRegType;
	}

	public List<BrngUsrRegAttr> getBrngUsrRegAttrs() {
		return this.brngUsrRegAttrs;
	}

	public void setBrngUsrRegAttrs(List<BrngUsrRegAttr> brngUsrRegAttrs) {
		this.brngUsrRegAttrs = brngUsrRegAttrs;
	}

	public BrngUsrRegAttr addBrngUsrRegAttr(BrngUsrRegAttr brngUsrRegAttr) {
		getBrngUsrRegAttrs().add(brngUsrRegAttr);
		brngUsrRegAttr.setBrngUsrReg(this);

		return brngUsrRegAttr;
	}

	public BrngUsrRegAttr removeBrngUsrRegAttr(BrngUsrRegAttr brngUsrRegAttr) {
		getBrngUsrRegAttrs().remove(brngUsrRegAttr);
		brngUsrRegAttr.setBrngUsrReg(null);

		return brngUsrRegAttr;
	}

	public BrngLkpOtpValidated getBrnglkpotpvalidated() {
		return brnglkpotpvalidated;
	}

	public void setBrnglkpotpvalidated(BrngLkpOtpValidated brnglkpotpvalidated) {
		this.brnglkpotpvalidated = brnglkpotpvalidated;
	}

	public BrngLkpServicemanValidated getBrnglkpservicemanvalidated() {
		return brnglkpservicemanvalidated;
	}

	public void setBrnglkpservicemanvalidated(BrngLkpServicemanValidated brnglkpservicemanvalidated) {
		this.brnglkpservicemanvalidated = brnglkpservicemanvalidated;
	}

	public BrngRegisterMethodLookup getBrngRegMethod() {
		return brngRegMethod;
	}

	public void setBrngRegMethod(BrngRegisterMethodLookup brngRegMethod) {
		this.brngRegMethod = brngRegMethod;
	}

	public BrngRegisterDeviceVersionLookup getBrngRegDevVersion() {
		return brngRegDevVersion;
	}

	public void setBrngRegDevVersion(BrngRegisterDeviceVersionLookup brngRegDevVersion) {
		this.brngRegDevVersion = brngRegDevVersion;
	}

}