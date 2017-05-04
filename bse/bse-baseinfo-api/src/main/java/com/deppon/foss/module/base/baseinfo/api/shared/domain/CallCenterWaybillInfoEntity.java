package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 催运单信息实体
 * @author 132599-shenweihua 2014-7.15
 *
 */
public class CallCenterWaybillInfoEntity extends BaseEntity{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 催单凭证号
	 */
	private String pressWaybillNo;
	
	/**
	 * 催单信息
	 */
	private String pressMsg;
	/**
	 * 处理时间
	 */
	private Date dealTime;
	/**
	 * 催单时间
	 */
	private Date pressTime;
	/**
	 *催单人
	 */
	private String pressUser;
	
	/**
	 * 处理部门
	 */
	private String dealDept;
	
	/**
	 * 处理部门名称
	 */
	private String dealDeptName;
	
	/**
	 * 处理人
	 */
	private String dealUser;
	
	/**
	 * 处理人姓名
	 */
	private String dealUserName;
	
	
	/**
	 * 是否已解决（默认统一为未解决）
	 * Y  解决
	 * N  未解决
	 */
	private String hasDone;
	/**
	 * 催单反馈信息
	 */
	private String callBackMsg;
	
	/**
	 * 单号
	 */
	private String waybillNo;

	public String getPressWaybillNo() {
		return pressWaybillNo;
	}

	public void setPressWaybillNo(String pressWaybillNo) {
		this.pressWaybillNo = pressWaybillNo;
	}

	public String getPressMsg() {
		return pressMsg;
	}

	public void setPressMsg(String pressMsg) {
		this.pressMsg = pressMsg;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Date getPressTime() {
		return pressTime;
	}

	public void setPressTime(Date pressTime) {
		this.pressTime = pressTime;
	}

	public String getPressUser() {
		return pressUser;
	}

	public void setPressUser(String pressUser) {
		this.pressUser = pressUser;
	}

	public String getDealDept() {
		return dealDept;
	}

	public void setDealDept(String dealDept) {
		this.dealDept = dealDept;
	}

	public String getDealUser() {
		return dealUser;
	}

	public void setDealUser(String dealUser) {
		this.dealUser = dealUser;
	}

	public String getHasDone() {
		return hasDone;
	}

	public void setHasDone(String hasDone) {
		this.hasDone = hasDone;
	}

	public String getCallBackMsg() {
		return callBackMsg;
	}

	public void setCallBackMsg(String callBackMsg) {
		this.callBackMsg = callBackMsg;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getDealDeptName() {
		return dealDeptName;
	}

	public void setDealDeptName(String dealDeptName) {
		this.dealDeptName = dealDeptName;
	}

	public String getDealUserName() {
		return dealUserName;
	}

	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}
	
}
