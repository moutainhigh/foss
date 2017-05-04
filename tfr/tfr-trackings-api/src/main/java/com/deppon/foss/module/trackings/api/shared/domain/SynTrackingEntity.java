package com.deppon.foss.module.trackings.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 待同步轨迹实体 
 * @author Alfred
 * @date 2015-07-08 16:35
 *
 */
public class SynTrackingEntity extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**运单号**/
	private String wayBillNo;
	
	/**订单号**/
	private String orderNo;
	
	/**发生时间**/
	private Date operateTime;
	
	/**发生城市**/
	private String operateCity;
	
	/**站点类型**/
	private String orgType;
	
	/**部门编码**/
	private String orgCode;
	
	/**部门名称**/
	private String orgName;

	/**时间类型**/
	private String eventType;

	/**操作人姓名**/
	private String operatorName;

	/**操作人联系方式**/
	private String operatorPhone;
	
	/**操作人工号**/
	private String operatorCode;

	/**同步jobid**/
	private String jobId;

	/**订单渠道来源**/
	private String orderChannel;
	
	/**到达部门Code**/
	private String nextOrgCode;
	
	/**到达部门Name**/
	private String nextOrgName;
	
	/**下一部门所在城市**/
	private String nextCity;
	
	/**产品类型**/
	private String productCode;
	
	/**跟踪信息描述**/
	private String trackInfo;
	
	
	/**GPS信息**/
	private String gpsMessage;
	
	/**操作内容：提货通知内容、派送拉回原因等**/
	private String operateContent;
	
	/**签收人**/
	private String signer;
	
	/**驻地部门名称**/
	private String stationDeptName;
	
	/**驻地部门编码**/
	private String stationDeptCode;
	
	/**目的部门所在城市名称**/
	private String destinationCityName;
	
	/**目的部门名称**/
	private String destinationDeptName;
	
	/**预计到达下一部门时间**/
	private Date planArriveTime;
	
	/**外发单号**/
	private String nextMailNos;
	
	/**外发商的编码**/
	private String nextTpCode;
	
	/**代理公司名称*/
	private String agentCompanyName;
	
	/**目的国*/
	private String destCountryName;
	
	public String getNextMailNos() {
		return nextMailNos;
	}

	public void setNextMailNos(String nextMailNos) {
		this.nextMailNos = nextMailNos;
	}

	public String getNextTpCode() {
		return nextTpCode;
	}

	public void setNextTpCode(String nextTpCode) {
		this.nextTpCode = nextTpCode;
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

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
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

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getTrackInfo() {
		return trackInfo;
	}

	public void setTrackInfo(String trackInfo) {
		this.trackInfo = trackInfo;
	}

	public String getNextOrgCode() {
		return nextOrgCode;
	}

	public void setNextOrgCode(String nextOrgCode) {
		this.nextOrgCode = nextOrgCode;
	}

	public String getNextOrgName() {
		return nextOrgName;
	}

	public void setNextOrgName(String nextOrgName) {
		this.nextOrgName = nextOrgName;
	}

	public String getNextCity() {
		return nextCity;
	}

	public void setNextCity(String nextCity) {
		this.nextCity = nextCity;
	}

	public String getGpsMessage() {
		return gpsMessage;
	}

	public void setGpsMessage(String gpsMessage) {
		this.gpsMessage = gpsMessage;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getSigner() {
		return signer;
	}

	public void setSigner(String signer) {
		this.signer = signer;
	}

	public String getStationDeptName() {
		return stationDeptName;
	}

	public void setStationDeptName(String stationDeptName) {
		this.stationDeptName = stationDeptName;
	}

	public String getStationDeptCode() {
		return stationDeptCode;
	}

	public void setStationDeptCode(String stationDeptCode) {
		this.stationDeptCode = stationDeptCode;
	}

	public String getDestinationCityName() {
		return destinationCityName;
	}

	public void setDestinationCityName(String destinationCityName) {
		this.destinationCityName = destinationCityName;
	}

	public String getDestinationDeptName() {
		return destinationDeptName;
	}

	public void setDestinationDeptName(String destinationDeptName) {
		this.destinationDeptName = destinationDeptName;
	}

	public Date getPlanArriveTime() {
		return planArriveTime;
	}

	public void setPlanArriveTime(Date planArriveTime) {
		this.planArriveTime = planArriveTime;
	}

	public String getAgentCompanyName() {
		return agentCompanyName;
	}

	public void setAgentCompanyName(String agentCompanyName) {
		this.agentCompanyName = agentCompanyName;
	}

	public String getDestCountryName() {
		return destCountryName;
	}

	public void setDestCountryName(String destCountryName) {
		this.destCountryName = destCountryName;
	}

}
