package com.gvn.brings.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the BRNG_ORDER database table.
 * 
 */
@Entity
@Table(name="BRNG_ORDER")
@NamedQuery(name="BrngOrder.findAll", query="SELECT b FROM BrngOrder b")
public class BrngOrder extends AbstractBaseModel  {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name="company_price", nullable=false, precision=10, scale=2)
	private BigDecimal companyPrice;
	
	@Column(name="total_price", nullable=false, precision=10, scale=2)
	private BigDecimal totalPrice;

	
	@Column(name="discount_price", nullable=false, precision=10, scale=2)
	private BigDecimal discountPrice;

	@Column(length=1000)
	private String description;

	@Column(name="schedule_time",length=1000)
	private String scheduleTime;
	
	@Column(name="schedule_date",length=1000)
	private String scheduleDate;

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	@Column(name="from_address", nullable=false, length=1000)
	private String fromAddress;

	@Column(name="from_latitude", length=45)
	private String fromLatitude;

	@Column(name="from_longitude", length=45)
	private String fromLongitude;

	@ManyToOne
	@JoinColumn(name="is_accepted", nullable=false)
	private BrngLkpIsAccepted isAccepted;

	@Column(name="order_seq", nullable=false)
	private int orderSeq;

	
	@Column(name="order_time", nullable=false)
	private Timestamp orderTime;

	@Column(name="service_price", nullable=false, precision=10, scale=2)
	private BigDecimal servicePrice;

	@Column(name="to_address", nullable=false, length=1000)
	private String toAddress;

	@Column(name="to_latitude", length=45)
	private String toLatitude;

	@Column(name="to_longitude", length=45)
	private String toLongitude;

	@Column(name="estimated_weight",length=11)
	private double estimatedWeight;
	
	@Column(name="total_distance", precision=10, scale=2)
	private BigDecimal totalDistance;
	
	@Column(name="order_pic", length=500)
	private String orderPic;
	
	@Column(name="order_drop_pic", length=500)
	private String orderDropPic;
	
	@Column(name="name_of_del_person", length=45)
	private String nameOfDelPerson;
	
	@Column(name="contact_del_person", length=20)
	private String contactDelPerson;
	
	@Column(name="complete_del_address",length=500)
	private String completeDelAddress;
	
	@Column(name="contact_pckup_person",length=20)
	private String contactPckupPerson;
	
	@Column(name="name_of_pickup_person",length=45)
	private String nameOfPckupPerson;
	
	@Column(name="complete_pckup_address",length=500)
	private String completePckupAddress;

	@Column(name="pay_tx_id",length=45)
	private String payTxId;
	
	@Column(name="delivery_code",length=45)
	private String deliveryCode;
	
	@Column(name="pickup_code",length=45)
	private String pickupCode;
	
	@Column(name="trip_rating",length=2)
	private int tripRating;
	
	@Column(name="gst",precision=10, scale=2)
	private BigDecimal gst;
	
	@Column(name="payu_txt_charges",precision=10, scale=2)
	private BigDecimal payuTxtCharges;
	
	//bi-directional many-to-one association to BrngLkpIsPaid
	@ManyToOne
	@JoinColumn(name="is_paid_id", nullable=false)
	private BrngLkpIsPaid brngLkpIsPaid;

	//bi-directional many-to-one association to BrngLkpIsPicked
	@ManyToOne
	@JoinColumn(name="is_picked_id", nullable=false)
	private BrngLkpIsPicked brngLkpIsPicked;

	//bi-directional many-to-one association to BrngLkpIsInsurance
		@ManyToOne
		@JoinColumn(name="is_insurance_id", nullable=false)
		private BrngLkpIsInsurance brngLkpIsInsurance;

		@ManyToOne
		@JoinColumn(name="offer_code", nullable=false)
		private BrngOffer brngOffer;

	//bi-directional many-to-one association to BrngUsrLogin
	@ManyToOne
	@JoinColumn(name="usr_login_id", nullable=false)
	private BrngUsrLogin brngUsrLogin;

	//bi-directional many-to-one association to BrngOrderDelivery
	@OneToMany(mappedBy="brngOrder")
	private List<BrngOrderDelivery> brngOrderDeliveries;
	
	@ManyToOne
	@JoinColumn(name="pay_type", nullable=false)
	private BrngLkpPayType brnglkppaytype;

	@ManyToOne
	@JoinColumn(name="is_cancelled_id", nullable=false)
	private BrngLkpIsCancelled brnglkpiscancelled;
	
	@ManyToOne
	@JoinColumn(name="is_retry_id", nullable=false)
	private BrngLkpIsRetry brnglkpisretry;
	
	public BrngOrder() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getCompanyPrice() {
		return this.companyPrice;
	}

	public void setCompanyPrice(BigDecimal companyPrice) {
		this.companyPrice = companyPrice;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getFromLatitude() {
		return this.fromLatitude;
	}

	public void setFromLatitude(String fromLatitude) {
		this.fromLatitude = fromLatitude;
	}

	public String getFromLongitude() {
		return this.fromLongitude;
	}

	public void setFromLongitude(String fromLongitude) {
		this.fromLongitude = fromLongitude;
	}

	

	public int getOrderSeq() {
		return this.orderSeq;
	}

	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}

	public Timestamp getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getServicePrice() {
		return this.servicePrice;
	}

	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getToLatitude() {
		return this.toLatitude;
	}

	public void setToLatitude(String toLatitude) {
		this.toLatitude = toLatitude;
	}

	public String getToLongitude() {
		return this.toLongitude;
	}

	public void setToLongitude(String toLongitude) {
		this.toLongitude = toLongitude;
	}

	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getEstimatedWeight() {
		return estimatedWeight;
	}

	public void setEstimatedWeight(double estimatedWeight) {
		this.estimatedWeight = estimatedWeight;
	}

	public BigDecimal getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(BigDecimal totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getNameOfDelPerson() {
		return nameOfDelPerson;
	}

	public void setNameOfDelPerson(String nameOfDelPerson) {
		this.nameOfDelPerson = nameOfDelPerson;
	}

	public String getContactDelPerson() {
		return contactDelPerson;
	}

	public void setContactDelPerson(String contactDelPerson) {
		this.contactDelPerson = contactDelPerson;
	}

	public String getCompleteDelAddress() {
		return completeDelAddress;
	}

	public void setCompleteDelAddress(String completeDelAddress) {
		this.completeDelAddress = completeDelAddress;
	}

	public String getContactPckupPerson() {
		return contactPckupPerson;
	}

	public void setContactPckupPerson(String contactPckupPerson) {
		this.contactPckupPerson = contactPckupPerson;
	}

	public String getNameOfPckupPerson() {
		return nameOfPckupPerson;
	}

	public void setNameOfPckupPerson(String nameOfPckupPerson) {
		this.nameOfPckupPerson = nameOfPckupPerson;
	}

	public String getCompletePckupAddress() {
		return completePckupAddress;
	}

	public void setCompletePckupAddress(String completePckupAddress) {
		this.completePckupAddress = completePckupAddress;
	}

	public BrngLkpIsPaid getBrngLkpIsPaid() {
		return this.brngLkpIsPaid;
	}

	public void setBrngLkpIsPaid(BrngLkpIsPaid brngLkpIsPaid) {
		this.brngLkpIsPaid = brngLkpIsPaid;
	}

	public BrngLkpIsPicked getBrngLkpIsPicked() {
		return this.brngLkpIsPicked;
	}

	public void setBrngLkpIsPicked(BrngLkpIsPicked brngLkpIsPicked) {
		this.brngLkpIsPicked = brngLkpIsPicked;
	}

	public BrngUsrLogin getBrngUsrLogin() {
		return this.brngUsrLogin;
	}

	public void setBrngUsrLogin(BrngUsrLogin brngUsrLogin) {
		this.brngUsrLogin = brngUsrLogin;
	}

	public List<BrngOrderDelivery> getBrngOrderDeliveries() {
		return this.brngOrderDeliveries;
	}

	public void setBrngOrderDeliveries(List<BrngOrderDelivery> brngOrderDeliveries) {
		this.brngOrderDeliveries = brngOrderDeliveries;
	}

	public BrngOrderDelivery addBrngOrderDelivery(BrngOrderDelivery brngOrderDelivery) {
		getBrngOrderDeliveries().add(brngOrderDelivery);
		brngOrderDelivery.setBrngOrder(this);

		return brngOrderDelivery;
	}

	public BrngOrderDelivery removeBrngOrderDelivery(BrngOrderDelivery brngOrderDelivery) {
		getBrngOrderDeliveries().remove(brngOrderDelivery);
		brngOrderDelivery.setBrngOrder(null);

		return brngOrderDelivery;
	}

	public String getPayTxId() {
		return payTxId;
	}

	public void setPayTxId(String payTxId) {
		this.payTxId = payTxId;
	}

	public BrngLkpPayType getBrnglkppaytype() {
		return brnglkppaytype;
	}

	public void setBrnglkppaytype(BrngLkpPayType brnglkppaytype) {
		this.brnglkppaytype = brnglkppaytype;
	}

	public BrngLkpIsCancelled getBrnglkpiscancelled() {
		return brnglkpiscancelled;
	}

	public void setBrnglkpiscancelled(BrngLkpIsCancelled brnglkpiscancelled) {
		this.brnglkpiscancelled = brnglkpiscancelled;
	}

	public void setIsAccepted(BrngLkpIsAccepted isAccepted) {
		this.isAccepted = isAccepted;
	}

	public BrngLkpIsAccepted getIsAccepted() {
		return isAccepted;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public BrngLkpIsRetry getBrnglkpisretry() {
		return brnglkpisretry;
	}

	public void setBrnglkpisretry(BrngLkpIsRetry brnglkpisretry) {
		this.brnglkpisretry = brnglkpisretry;
	}

	public String getPickupCode() {
		return pickupCode;
	}

	public void setPickupCode(String pickupCode) {
		this.pickupCode = pickupCode;
	}

	public int getTripRating() {
		return tripRating;
	}

	public void setTripRating(int tripRating) {
		this.tripRating = tripRating;
	}

	public String getOrderPic() {
		return orderPic;
	}

	public void setOrderPic(String orderPic) {
		this.orderPic = orderPic;
	}

	public String getOrderDropPic() {
		return orderDropPic;
	}

	public void setOrderDropPic(String orderDropPic) {
		this.orderDropPic = orderDropPic;
	}

	public BigDecimal getGst() {
		return gst;
	}

	public void setGst(BigDecimal gst) {
		this.gst = gst;
	}

	public BigDecimal getPayuTxtCharges() {
		return payuTxtCharges;
	}

	public void setPayuTxtCharges(BigDecimal payuTxtCharges) {
		this.payuTxtCharges = payuTxtCharges;
	}

	public BrngLkpIsInsurance getBrngLkpIsInsurance() {
		return brngLkpIsInsurance;
	}

	public void setBrngLkpIsInsurance(BrngLkpIsInsurance brngLkpIsInsurance) {
		this.brngLkpIsInsurance = brngLkpIsInsurance;
	}

	public BrngOffer getBrngOffer() {
		return brngOffer;
	}

	public void setBrngOffer(BrngOffer brngOffer) {
		this.brngOffer = brngOffer;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	

	

}