package com.deppon.foss.module.trackings.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class DMPSynTrackingsToWQSEntity implements Serializable{

	/**
	 * 接收DMP的大件家装轨迹数据到轨迹表实体
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	/**运单号**/
	private String wayBillNo;
	//发生城市;
	private String operateCity;
	//部门编码
	private String orgCode;
	// 部门名称
	private String orgName;
	// 事件类型
	private String eventType;
	// 操作人
	private String operateName;
	//操作时间(发生时间)
	private Date operateTime;
	//创建时间
	private Date createTime;
	//签收人
	private String signer;
	//轨迹信息
	private String trackInfo;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWayBillNo() {
		return wayBillNo;
	}
	public void setWayBillNo(String wayBillNo) {
		this.wayBillNo = wayBillNo;
	}
	public String getOperateCity() {
		return operateCity;
	}
	public void setOperateCity(String operateCity) {
		this.operateCity = operateCity;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSigner() {
		return signer;
	}
	public void setSigner(String signer) {
		this.signer = signer;
	}
	public String getTrackInfo() {
		return trackInfo;
	}
	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}
	
}
