package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 快递代理代收货款审核查询条件VO
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-29 下午5:24:29
 */
public class LandBillPaidCODConditionVo implements Serializable {

	private static final long serialVersionUID = 84994581159046744L;

	/**
	 * 查询类别
	 */
	private String queryType;

	/**
	 * 快递代理签收组织编码
	 */
	private String orgCode;

	/**
	 * 签收起始日期
	 */
	private Date inceptSignDate;

	/**
	 * 签收结束日期
	 */
	private Date endSignDate;

	/**
	 * 签收起始日期
	 */
	private String inceptSignDateStr;

	/**
	 * 签收结束日期
	 */
	private String endSignDateStr;

	/**
	 * 签收起始日期
	 */
	private Date inceptAuditDate;

	/**
	 * 签收结束日期
	 */
	private Date endAuditDate;

	/**
	 * 签收起始日期
	 */
	private String inceptAuditDateStr;

	/**
	 * 签收结束日期
	 */
	private String endAuditDateStr;

	/**
	 * 运单单号
	 */
	private String waybillNo;

	/**
	 * 运单集合(用户查询)
	 */
	private List<String> waybillNoSet;

	/**
	 * 快递代理代收货款状态
	 */
	private String landStatus;

	/**
	 * 代收货款类型
	 */
	private String codType;

	/**
	 * 日期查询类别
	 */
	private String dateType;

	/**
	 * @return queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	/**
	 * @param queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return inceptSignDate
	 */
	public Date getInceptSignDate() {
		return inceptSignDate;
	}

	/**
	 * @param inceptSignDate
	 */
	public void setInceptSignDate(Date inceptSignDate) {
		this.inceptSignDate = inceptSignDate;
	}

	/**
	 * @return endSignDate
	 */
	public Date getEndSignDate() {
		return endSignDate;
	}

	/**
	 * @param endSignDate
	 */
	public void setEndSignDate(Date endSignDate) {
		this.endSignDate = endSignDate;
	}

	/**
	 * @return inceptSignDateStr
	 */
	public String getInceptSignDateStr() {
		return inceptSignDateStr;
	}

	/**
	 * @param inceptSignDateStr
	 */
	public void setInceptSignDateStr(String inceptSignDateStr) {
		this.inceptSignDateStr = inceptSignDateStr;
	}

	/**
	 * @return endSignDateStr
	 */
	public String getEndSignDateStr() {
		return endSignDateStr;
	}

	/**
	 * @param endSignDateStr
	 */
	public void setEndSignDateStr(String endSignDateStr) {
		this.endSignDateStr = endSignDateStr;
	}

	/**
	 * @return inceptAuditDate
	 */
	public Date getInceptAuditDate() {
		return inceptAuditDate;
	}

	/**
	 * @param inceptAuditDate
	 */
	public void setInceptAuditDate(Date inceptAuditDate) {
		this.inceptAuditDate = inceptAuditDate;
	}

	/**
	 * @return endAuditDate
	 */
	public Date getEndAuditDate() {
		return endAuditDate;
	}

	/**
	 * @param endAuditDate
	 */
	public void setEndAuditDate(Date endAuditDate) {
		this.endAuditDate = endAuditDate;
	}

	/**
	 * @return inceptAuditDateStr
	 */
	public String getInceptAuditDateStr() {
		return inceptAuditDateStr;
	}

	/**
	 * @param inceptAuditDateStr
	 */
	public void setInceptAuditDateStr(String inceptAuditDateStr) {
		this.inceptAuditDateStr = inceptAuditDateStr;
	}

	/**
	 * @return endAuditDateStr
	 */
	public String getEndAuditDateStr() {
		return endAuditDateStr;
	}

	/**
	 * @param endAuditDateStr
	 */
	public void setEndAuditDateStr(String endAuditDateStr) {
		this.endAuditDateStr = endAuditDateStr;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return waybillNoSet
	 */
	public List<String> getWaybillNoSet() {
		return waybillNoSet;
	}

	/**
	 * @param waybillNoSet
	 */
	public void setWaybillNoSet(List<String> waybillNoSet) {
		this.waybillNoSet = waybillNoSet;
	}

	/**
	 * @return landStatus
	 */
	public String getLandStatus() {
		return landStatus;
	}

	/**
	 * @param landStatus
	 */
	public void setLandStatus(String landStatus) {
		this.landStatus = landStatus;
	}

	/**
	 * @return codType
	 */
	public String getCodType() {
		return codType;
	}

	/**
	 * @param codType
	 */
	public void setCodType(String codType) {
		this.codType = codType;
	}

	/**
	 * @return dateType
	 */
	public String getDateType() {
		return dateType;
	}

	/**
	 * @param dateType
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

}
