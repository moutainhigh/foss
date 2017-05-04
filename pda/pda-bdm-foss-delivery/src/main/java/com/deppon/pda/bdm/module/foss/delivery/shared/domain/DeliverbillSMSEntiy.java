package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;
/**
 * 
  * @ClassName DeliverbillSMSDto 
  * @Description 接送货异常短信推送 
  * @author 354682
  * @date 2016-09-13
 */

public class DeliverbillSMSEntiy implements Serializable{

	private static final long serialVersionUID = 1L;
	//司机编号
	private String driverCode;
	//运单号
	private String waybillNo;
	//短信类型  MsgType 1：接货短信，发给发货人 MsgType 2：送货短信，发给收货人，必传
	private String notifyType;
	//司机电话号
	private String driverPhone;
	//客户手机号
	private String mobile;
  
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	
	 

}
