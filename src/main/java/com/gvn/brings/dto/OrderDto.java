package com.gvn.brings.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.gvn.brings.model.BrngLkpIsPaid;
import com.gvn.brings.model.BrngLkpPayPercent;
import com.gvn.brings.model.BrngOrder;

public class OrderDto extends AbstractBaseDto{
	private static final long serialVersionUID = 1L;
	
	private BigDecimal payPercent;
	private Date effectiveDate;
	private String description;
	private String isPaid;
	
	//
	
	private BrngOrder brngOrder;
	
	//Order Details
	private BigDecimal companyPrice;
	private BigDecimal totalPrice;
	private String fromAddress;
	private String fromLatitude;
	private String fromLongitude;
	private int orderSeq;
	//private Date orderTime;
	private String orderTime;
	private int id;
	private BigDecimal servicePrice;
	private String toAddress;
	private String toLatitude;
	private String toLongitude;
	private double estimatedWeight;
	private BigDecimal totalDistance;
	private String nameOfDelPerson;
	private String completeDelAddress;
	private String contactDelPerson;
	private String contactPckupPerson;
	private String nameOfPckupPerson;
	private String completePckupAddress;
	private String isPicked;
	private String payType;
	private String deliveryCode;
	private String deliveryStatus;
	private String pickUpCode;
	
	private BigDecimal gst;
	private BigDecimal payuTxtXCharges;
	
	private int tripRating;
	
	private String driverCurrentLat;
	private String driverCurrentLng;
	private String driverName;
	private String driverNumber;
	private String driverImage;
	
	private String isAccepted;
	private String orderStatus;
	private double pickDistance;
	//Same as Retry
	private String customerOrderStatus;
	
	private String orderPic;
	
	private String orderDropPic;
	
	private String offer;
	
	private String scheduleTime;
	
	private String scheduleDate;
	
	private String scheduleStatus ;
	
	public String getScheduleStatus() {
		return scheduleStatus;
	}


	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}


	public String getScheduleDate() {
		return scheduleDate;
	}


	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}


	private BigDecimal offerDiscount;
	
	private BigDecimal discountPrice;
	
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}


	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}


	public BrngOrder getBrngOrder() {
		return brngOrder;
	}


	public void setBrngOrder(BrngOrder brngOrder) {
		this.brngOrder = brngOrder;
	}


	public OrderDto(BrngOrder brngOrder)
	{
		this.id=brngOrder.getId();
		this.fromAddress=brngOrder.getFromAddress();
		this.toAddress=brngOrder.getToAddress();
		this.description=brngOrder.getDescription();
		//this.totalPrice=brngOrder.getTotalPrice();
		this.totalPrice=brngOrder.getTotalPrice().add(brngOrder.getGst()).add(brngOrder.getPayuTxtCharges());
		this.gst=brngOrder.getGst();
		this.isPicked=brngOrder.getBrngLkpIsPicked().getIsPicked();
		this.isPaid=Integer.toString(brngOrder.getBrngLkpIsPaid().getId());
		this.isAccepted=brngOrder.getIsAccepted().getIsAccepted();
		this.companyPrice=brngOrder.getCompanyPrice();
		this.servicePrice=brngOrder.getServicePrice();
		this.orderTime=brngOrder.getOrderTime().toString();
		this.fromLatitude=brngOrder.getFromLatitude();
		this.fromLongitude=brngOrder.getFromLongitude();
		this.toLatitude=brngOrder.getToLatitude();
		this.toLongitude=brngOrder.getToLongitude();
		this.completePckupAddress=brngOrder.getCompletePckupAddress();
		this.completeDelAddress=brngOrder.getCompleteDelAddress();
		this.nameOfPckupPerson=brngOrder.getNameOfPckupPerson();
		this.nameOfDelPerson=brngOrder.getNameOfDelPerson();
		this.contactPckupPerson=brngOrder.getContactPckupPerson();
		this.contactDelPerson=brngOrder.getContactDelPerson();
		this.totalDistance=brngOrder.getTotalDistance();
		this.estimatedWeight=brngOrder.getEstimatedWeight();
		this.isPaid=brngOrder.getBrngLkpIsPaid().getIsPaid();
		this.payType=brngOrder.getBrnglkppaytype().getDescription();
		this.deliveryCode=brngOrder.getDeliveryCode();
		this.tripRating=brngOrder.getTripRating();
		this.scheduleTime=brngOrder.getScheduleTime();
		this.scheduleDate=brngOrder.getScheduleDate();
		if(brngOrder.getIsAccepted().getIsAccepted().equalsIgnoreCase("Y"))
		{
		this.pickUpCode=brngOrder.getPickupCode();
		}
		if(brngOrder.getBrnglkpisretry().getIsRetry().equalsIgnoreCase("Y"))
		{
			this.customerOrderStatus="N";
		}
		else
		{
			this.customerOrderStatus="Y";
		}
		if(brngOrder.getBrngLkpIsPicked().getIsPicked().equalsIgnoreCase("Y"))
		{
			this.orderStatus="Picked";
			this.orderPic=brngOrder.getOrderPic();
		}
		else if(brngOrder.getBrngLkpIsPicked().getIsPicked().equalsIgnoreCase("Y"))
		{
			this.orderStatus="Picked";
		}
		/*else if(brngOrder.getBrngLkpIsPaid().getIsPaid().equalsIgnoreCase("Y"))
		{
			this.orderStatus="Accepted";
		}*/
		else if(brngOrder.getIsAccepted().getIsAccepted().equalsIgnoreCase("Y"))
		{
			
			this.orderStatus="Accepted";
		}
		else if(brngOrder.getBrnglkpisretry().getIsRetry().equalsIgnoreCase("Y"))
		{
			this.orderStatus="Open";
		}
		
		this.discountPrice=brngOrder.getDiscountPrice();
		//this.customerOrderStatus=brngOrder.getBrnglkpisretry().getIsRetry();
		//this.deliveryStatus=brngOrder.get
	}
	
	
	public OrderDto(BrngLkpPayPercent brngLkpPayPercent)
	{
		this.payPercent=brngLkpPayPercent.getPayPercent();
		this.effectiveDate=brngLkpPayPercent.getEffectiveDate();
		this.description=brngLkpPayPercent.getDescription();
	}
	
	
	
	public OrderDto(BrngLkpIsPaid brngLkpIsPaid)
	{
		this.isPaid=brngLkpIsPaid.getIsPaid();
		this.description=brngLkpIsPaid.getDescription();
	}
	public BigDecimal getPayPercent() {
		return payPercent;
	}
	public void setPayPercent(BigDecimal payPercent) {
		this.payPercent = payPercent;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(String isPaid) {
		this.isPaid = isPaid;
	}


	public BigDecimal getCompanyPrice() {
		return companyPrice;
	}


	public void setCompanyPrice(BigDecimal companyPrice) {
		this.companyPrice = companyPrice;
	}


	public BigDecimal getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}


	public String getFromAddress() {
		return fromAddress;
	}


	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}


	public String getFromLatitude() {
		return fromLatitude;
	}


	public void setFromLatitude(String fromLatitude) {
		this.fromLatitude = fromLatitude;
	}


	public String getFromLongitude() {
		return fromLongitude;
	}


	public void setFromLongitude(String fromLongitude) {
		this.fromLongitude = fromLongitude;
	}


	public int getOrderSeq() {
		return orderSeq;
	}


	public void setOrderSeq(int orderSeq) {
		this.orderSeq = orderSeq;
	}


	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public BigDecimal getServicePrice() {
		return servicePrice;
	}


	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}


	public String getToAddress() {
		return toAddress;
	}


	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}


	public String getToLatitude() {
		return toLatitude;
	}


	public void setToLatitude(String toLatitude) {
		this.toLatitude = toLatitude;
	}


	public String getToLongitude() {
		return toLongitude;
	}


	public void setToLongitude(String toLongitude) {
		this.toLongitude = toLongitude;
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



	



	public String getContactDelPerson() {
		return contactDelPerson;
	}


	public void setContactDelPerson(String contactDelPerson) {
		this.contactDelPerson = contactDelPerson;
	}


	public void setIsPicked(String isPicked) {
		this.isPicked = isPicked;
	}


	public String getIsPicked() {
		return isPicked;
	}


	public String getIsAccepted() {
		return isAccepted;
	}


	public void setIsAccepted(String isAccepted) {
		this.isAccepted = isAccepted;
	}


	public String getPayType() {
		return payType;
	}


	public void setPayType(String payType) {
		this.payType = payType;
	}


	public String getDriverCurrentLat() {
		return driverCurrentLat;
	}


	public void setDriverCurrentLat(String driverCurrentLat) {
		this.driverCurrentLat = driverCurrentLat;
	}


	public String getDriverCurrentLng() {
		return driverCurrentLng;
	}


	public void setDriverCurrentLng(String driverCurrentLng) {
		this.driverCurrentLng = driverCurrentLng;
	}


	public String getDriverName() {
		return driverName;
	}


	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}


	public String getDriverNumber() {
		return driverNumber;
	}


	public void setDriverNumber(String driverNumber) {
		this.driverNumber = driverNumber;
	}


	public String getDeliveryCode() {
		return deliveryCode;
	}


	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}


	public String getDeliveryStatus() {
		return deliveryStatus;
	}


	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}


	public String getDriverImage() {
		return driverImage;
	}


	public void setDriverImage(String driverImage) {
		this.driverImage = driverImage;
	}


	public String getCustomerOrderStatus() {
		return customerOrderStatus;
	}


	public void setCustomerOrderStatus(String customerOrderStatus) {
		this.customerOrderStatus = customerOrderStatus;
	}


	public String getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}


	public String getPickUpCode() {
		return pickUpCode;
	}


	public void setPickUpCode(String pickUpCode) {
		this.pickUpCode = pickUpCode;
	}


	

	public double getPickDistance() {
		return pickDistance;
	}


	public void setPickDistance(double pickDistance) {
		this.pickDistance = pickDistance;
	}


	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}


	public String getOrderTime() {
		return orderTime;
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


	public BigDecimal getPayuTxtXCharges() {
		return payuTxtXCharges;
	}


	public void setPayuTxtXCharges(BigDecimal payuTxtXCharges) {
		this.payuTxtXCharges = payuTxtXCharges;
	}


	public String getOffer() {
		return offer;
	}


	public void setOffer(String offer) {
		this.offer = offer;
	}


	public String getScheduleTime() {
		return scheduleTime;
	}


	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}


	public BigDecimal getOfferDiscount() {
		return offerDiscount;
	}


	public void setOfferDiscount(BigDecimal offerDiscount) {
		this.offerDiscount = offerDiscount;
	}
	
}
