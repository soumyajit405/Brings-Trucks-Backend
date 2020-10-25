package com.gvn.brings.dto;

import com.gvn.brings.model.BrngLkpIsAccepted;
import com.gvn.brings.model.BrngLkpIsPicked;
import com.gvn.brings.model.BrngLkpOrderDelStatus;

public class OrderDeliveryDto extends AbstractBaseDto{
	private static final long serialVersionUID = 1L;
	

private String isPicked;
private String description;
private String isAccepted;
private String delStatus;

public OrderDeliveryDto(BrngLkpIsAccepted brngLkpIsAccepted)
{
	this.isAccepted=brngLkpIsAccepted.getIsAccepted();
	this.description=brngLkpIsAccepted.getDescription();
}

public OrderDeliveryDto(BrngLkpIsPicked brngLkpIsPicked)
{
	this.isPicked=brngLkpIsPicked.getIsPicked();
	this.description=brngLkpIsPicked.getDescription();
}

public OrderDeliveryDto(BrngLkpOrderDelStatus brngLkpOrderDelStatus)
{
	this.delStatus=brngLkpOrderDelStatus.getDelStatus();
	this.description=brngLkpOrderDelStatus.getDescription();
}
public String getIsPicked() {
	return isPicked;
}
public void setIsPicked(String isPicked) {
	this.isPicked = isPicked;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getIsAccepted() {
	return isAccepted;
}
public void setIsAccepted(String isAccepted) {
	this.isAccepted = isAccepted;
}
public String getDelStatus() {
	return delStatus;
}
public void setDelStatus(String delStatus) {
	this.delStatus = delStatus;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}

}
