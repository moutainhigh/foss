package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * 客户圈信息
 * @author 308861 
 * @date 2016-12-21 上午10:25:23
 * @since
 * @version
 */
public class CustomerCircleEntity extends BaseEntity{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 客户圈关系FID
	 */
	private String crmId;
	/**
	 * 客户圈编码
	 */
	private String custCircleCode;
	/**
	 * 客户圈名称
	 */
	private String custCircleName;
	/**
	 * 客户编码
	 */
	private String custCode;
	/**
	 * 客户名称
	 */
	private String custName;
	/**
	 * 是否主客户（N：从客户、Y：主客户）
	 */
	private String isMainCust;
	/**
	 * 是否统一结算
	 */
	private String isFocusPay;
	/**
	 * 催款部门编码
	 */
	private String hastenfunddeptCode;
	/**
	 * 是否启用（N：未启用、Y：启用）
	 */
	private String active;
	/**
	 * 开始时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	public String getCrmId() {
		return crmId;
	}
	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}
	public String getCustCircleCode() {
		return custCircleCode;
	}
	public void setCustCircleCode(String custCircleCode) {
		this.custCircleCode = custCircleCode;
	}
	public String getCustCircleName() {
		return custCircleName;
	}
	public void setCustCircleName(String custCircleName) {
		this.custCircleName = custCircleName;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIsMainCust() {
		return isMainCust;
	}
	public void setIsMainCust(String isMainCust) {
		this.isMainCust = isMainCust;
	}
	public String getIsFocusPay() {
		return isFocusPay;
	}
	public void setIsFocusPay(String isFocusPay) {
		this.isFocusPay = isFocusPay;
	}
	public String getHastenfunddeptCode() {
		return hastenfunddeptCode;
	}
	public void setHastenfunddeptCode(String hastenfunddeptCode) {
		this.hastenfunddeptCode = hastenfunddeptCode;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
