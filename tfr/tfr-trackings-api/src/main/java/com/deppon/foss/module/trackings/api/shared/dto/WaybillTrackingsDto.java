package com.deppon.foss.module.trackings.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

public class WaybillTrackingsDto extends BaseEntity {

	private static final long serialVersionUID = 1L;
	//路由ID
	private String routeId;
	//运单号
	private String waybillNo;
	//操作类型
	private String operateType;
	//操作部门code
	private String operateDeptCode;
	//操作部门名称
	private String operateDeptName;
	//下一部门code(出发时必传)
	private String nextDeptCode;
	//下一部门名称
	private String nextDeptName;
	//操作时间
	private Date operateTime;
	//操作人名称
	private String operatorName;
	//操作人电话
	private String operatorPhone;
	//操作描述
	private String operateDesc;
	//货物当前位置
	private String operateLocation;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;
	//是否可推送
	//private String isCanPush;
	//是否推送
	private String isPush;
	//路由IDlist
	private List<String> routeList;
	//货物到达时间
	private Date inStockTime;
	
	
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getOperateDeptCode() {
		return operateDeptCode;
	}
	public void setOperateDeptCode(String operateDeptCode) {
		this.operateDeptCode = operateDeptCode;
	}
	public String getOperateDeptName() {
		return operateDeptName;
	}
	public void setOperateDeptName(String operateDeptName) {
		this.operateDeptName = operateDeptName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorPhone() {
		return operatorPhone;
	}
	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
	}
	public String getOperateDesc() {
		return operateDesc;
	}
	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}
	public String getOperateLocation() {
		return operateLocation;
	}
	public void setOperateLocation(String operateLocation) {
		this.operateLocation = operateLocation;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getNextDeptCode() {
		return nextDeptCode;
	}
	public void setNextDeptCode(String nextDeptCode) {
		this.nextDeptCode = nextDeptCode;
	}
	public String getNextDeptName() {
		return nextDeptName;
	}
	public void setNextDeptName(String nextDeptName) {
		this.nextDeptName = nextDeptName;
	}
//	public String getIsCanPush() {
//		return isCanPush;
//	}
//	public void setIsCanPush(String isCanPush) {
//		this.isCanPush = isCanPush;
//	}
	public String getIsPush() {
		return isPush;
	}
	public void setIsPush(String isPush) {
		this.isPush = isPush;
	}
	public List<String> getRouteList() {
		return routeList;
	}
	public void setRouteList(List<String> routeList) {
		this.routeList = routeList;
	}
	public Date getInStockTime() {
		return inStockTime;
	}
	public void setInStockTime(Date inStockTime) {
		this.inStockTime = inStockTime;
	}
}
