package com.deppon.foss.module.transfer.airfreight.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/*
 * 
 * @author foss-105795-wqh
 * @desc 记录合票修改，删除的实体
 * @date 2014-01-21
 * 
 * */
public class AirPickupbillLogEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 111313313332121213L;

	//合票清单Id
	String airPickupbillId;
	
	//正单号
	String airWaybillNo;
	
	//操作类型
	String operateType;
	
	//操作人工号
	String operateCode;
	
	//操作人
	String operateName;
	
	//操作时间
	Date operateTime;
	
	//操作内容
	String operateContent;
	
	//操作部门
	String opterateOrgCode;
	
	//操作部门名称
	String opterateOrgName;
	
	//创建人工号
	String createrCode;
	
	//创建人
	String createrName;
	
	//创建时间
	Date createTime;  
	
	//修改人工号
	String modifyCode;
	
	//修改人
	String modifyName;
	
	//修改时间
	Date modifyTime;

	public String getAirPickupbillId() {
		return airPickupbillId;
	}

	public void setAirPickupbillId(String airPickupbillId) {
		this.airPickupbillId = airPickupbillId;
	}

	public String getAirWaybillNo() {
		return airWaybillNo;
	}

	public void setAirWaybillNo(String airWaybillNo) {
		this.airWaybillNo = airWaybillNo;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getOperateCode() {
		return operateCode;
	}

	public void setOperateCode(String operateCode) {
		this.operateCode = operateCode;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getOpterateOrgCode() {
		return opterateOrgCode;
	}

	public void setOpterateOrgCode(String opterateOrgCode) {
		this.opterateOrgCode = opterateOrgCode;
	}

	public String getOpterateOrgName() {
		return opterateOrgName;
	}

	public void setOpterateOrgName(String opterateOrgName) {
		this.opterateOrgName = opterateOrgName;
	}

	public String getCreaterCode() {
		return createrCode;
	}

	public void setCreaterCode(String createrCode) {
		this.createrCode = createrCode;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyCode() {
		return modifyCode;
	}

	public void setModifyCode(String modifyCode) {
		this.modifyCode = modifyCode;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
