package com.deppon.foss.module.transfer.load.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

public class ThroughExpressPathDetailEntity extends BaseEntity{

	/**
	 * 快递直达包走货路径明细
	 */
	private static final long serialVersionUID = -8664361939000817191L;

	private String packageNo;//包号
	
	private String packagePathId;//package_path_id

	private String createOrgCode;//建包部门code

	private String createOrgName;//建包部门名称

	private String origOrgCode;//当前部门code

	private String origOrgName;//当前部门名称

	private String objectiveOrgCode;//目的部门code

	private String objectiveOrgName;//目的部门名称
	
	private String status;//状态

	private int routeNo;//路段号

	private String vehicleNo;//车牌号

	private Date arriveTime;//到达时间
	
	private Date  departTime;//出发时间

	private String createUserCode;//创建人code

	private String modifyUserCode;//修改人code

	//set and get
	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getPackagePathId() {
		return packagePathId;
	}

	public void setPackagePathId(String packagePathId) {
		this.packagePathId = packagePathId;
	}

	public String getCreateOrgCode() {
		return createOrgCode;
	}

	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	public String getCreateOrgName() {
		return createOrgName;
	}

	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}

	public String getOrigOrgCode() {
		return origOrgCode;
	}

	public void setOrigOrgCode(String origOrgCode) {
		this.origOrgCode = origOrgCode;
	}

	public String getOrigOrgName() {
		return origOrgName;
	}

	public void setOrigOrgName(String origOrgName) {
		this.origOrgName = origOrgName;
	}

	public String getObjectiveOrgCode() {
		return objectiveOrgCode;
	}

	public void setObjectiveOrgCode(String objectiveOrgCode) {
		this.objectiveOrgCode = objectiveOrgCode;
	}

	public String getObjectiveOrgName() {
		return objectiveOrgName;
	}

	public void setObjectiveOrgName(String objectiveOrgName) {
		this.objectiveOrgName = objectiveOrgName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(int routeNo) {
		this.routeNo = routeNo;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public Date getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public String getModifyUserCode() {
		return modifyUserCode;
	}

	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

}
