package com.deppon.foss.module.base.querying.shared.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *  交货确认状态查询接口的响应实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-9-10 下午2:26:03,content:TODO </p>
 * @author 232607 
 * @date 2015-9-10 下午2:26:03
 * @since
 * @version
 */
@XmlRootElement(name="DeliveryConfirmResponse")
public class DeliveryConfirmResponse {
	/**
	 * 交货确认状态（Y为交货确认，N为未确认或取消交货确认）
	 */
	private String deliveryConfirmStatus;
	
	/**
	 * 交货确认时间
	 */
	private Date confirmTime;
	
	/**
	 * 查询是否成功
	 */
	private boolean isSuccess;
	
	/**
	 * 信息
	 */
	private String message;
	

	public String getDeliveryConfirmStatus() {
		return deliveryConfirmStatus;
	}
	public void setDeliveryConfirmStatus(String deliveryConfirmStatus) {
		this.deliveryConfirmStatus = deliveryConfirmStatus;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	
}
