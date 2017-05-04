package com.deppon.foss.module.transfer.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * 类描述：	同步TPS车辆交易信息到FOSS系统
 * @date 2014-10-23
 * @author 203906 熊佳冰
 *
 */
@XmlRootElement(name="VehicleRequestEntity")
public class VehicleRequestEntity implements Serializable{
	
	private static final long serialVersionUID = 1111218372889625010L;

	private String vehicleNo; //车牌号
	
	private BigDecimal inviteCost;//请车价格
	
	private Date planArriveTime;//预计到达时间
	
	private String inviteNo;//约车编号
	
	private String appvoeUserName;//审批人姓名

	private String appvoeUserCode;//审批人编码
	
	private String appvoeDeptName;//审批人部门
	
	private String appvoeDeptCode;//审批部门编码
	
	//269701--lln--2015-08-25 begin
	/**信息部名称*/
	private String ministryinformation;
	//269701--lln--2015-08-25 end

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public BigDecimal getInviteCost() {
		return inviteCost;
	}

	public void setInviteCost(BigDecimal inviteCost) {
		this.inviteCost = inviteCost;
	}

	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getInviteNo() {
		return inviteNo;
	}

	public void setInviteNo(String inviteNo) {
		this.inviteNo = inviteNo;
	}

	/**
	 * @return the appvoeUserName
	 */
	public String getAppvoeUserName() {
		return appvoeUserName;
	}

	/**
	 * @param appvoeUserName the appvoeUserName to set
	 */
	public void setAppvoeUserName(String appvoeUserName) {
		this.appvoeUserName = appvoeUserName;
	}

	/**
	 * @return the appvoeUserCode
	 */
	public String getAppvoeUserCode() {
		return appvoeUserCode;
	}

	/**
	 * @param appvoeUserCode the appvoeUserCode to set
	 */
	public void setAppvoeUserCode(String appvoeUserCode) {
		this.appvoeUserCode = appvoeUserCode;
	}

	/**
	 * @return the appvoeDeptName
	 */
	public String getAppvoeDeptName() {
		return appvoeDeptName;
	}

	/**
	 * @param appvoeDeptName the appvoeDeptName to set
	 */
	public void setAppvoeDeptName(String appvoeDeptName) {
		this.appvoeDeptName = appvoeDeptName;
	}

	/**
	 * @return the appvoeDeptCode
	 */
	public String getAppvoeDeptCode() {
		return appvoeDeptCode;
	}

	/**
	 * @param appvoeDeptCode the appvoeDeptCode to set
	 */
	public void setAppvoeDeptCode(String appvoeDeptCode) {
		this.appvoeDeptCode = appvoeDeptCode;
	}

	/**
	 * 信息部名称
	 * @return the ministryinformation
	 */
	public String getMinistryinformation() {
		return ministryinformation;
	}

	/**
	 * 信息部名称
	 * @param ministryinformation the ministryinformation to set
	 */
	public void setMinistryinformation(String ministryinformation) {
		this.ministryinformation = ministryinformation;
	}
	
}
