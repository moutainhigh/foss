package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 快递代理代收货款审核Dto
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-29 下午5:24:29
 */
public class LandBillPaidCODQueryDto {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 快递代理签收组织编码
	 */
	private String destOrgCode;
	
	/**
	 * 快递代理签收组织编码集合
	 */
	private List<String> destOrgCodeList;

	/**
	 * 签收起始日期
	 */
	private Date inceptSignDate;

	/**
	 * 签收结束日期
	 */
	private Date endSignDate;

	/**
	 * 起始审核时间
	 */
	private Date inceptAuditDate;

	/**
	 * 结束审核时间
	 */
	private Date endAuditDate;

	/**
	 * 产品类型
	 */
	private List<String> productCodeList;

	/**
	 * 代收货款状态
	 */
	private String status;

	/**
	 * 新快递代理代收货款状态
	 */
	private String newLandStatus;

	/**
	 * 旧快递代理代收货款状态
	 */
	private String oldLandStatus;

	/**
	 * 用户编码
	 */
	private String userCode;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 快递代理代收货款审核时间
	 */
	private Date landAuditDate;

	/**
	 * 运单集合(用户查询)
	 */
	private List<String> waybillNoSet;

	/**
	 * 快递代理代收货款状态
	 */
	private String landStatus;
	
	
	/**
	 * 快递代理代收货款审核人编码
	 */
	private String landOrgAuditUserCode;

	/**
	 * 快递代理代收货款审核名称
	 */
	private String landOrgAuditUserName;

	/**
	 * 代收货款类型
	 */
	private String codType;

	/**
	 * 应付单据类型
	 */
	private String billPayableType;

	/**
	 * 应收单单据子类型
	 */
	private String billReceivableType;

	/**
	 * 已核销金额
	 */
	private BigDecimal verifyAmount;
	
	/**
	 * 因为做完合大票之后，还可以进行更改，增加代收货款应收单
	 * 而经过合大票之后，代收货款应收单变为快递代理应收单
	 * 查询时，需要传入两种类型
	 */
	private List<String> billReceivableTypes;

	/**
	 * 外部网点类型 KY:快递代理网点 PX：偏线代理网点
	 */
	private String externalNodeType;
	
	/**
	 * 有效性
	 */
	private String active;
	

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	/**
	 * @param destOrgCode
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
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
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return newLandStatus
	 */
	public String getNewLandStatus() {
		return newLandStatus;
	}

	/**
	 * @param newLandStatus
	 */
	public void setNewLandStatus(String newLandStatus) {
		this.newLandStatus = newLandStatus;
	}

	/**
	 * @return oldLandStatus
	 */
	public String getOldLandStatus() {
		return oldLandStatus;
	}

	/**
	 * @param oldLandStatus
	 */
	public void setOldLandStatus(String oldLandStatus) {
		this.oldLandStatus = oldLandStatus;
	}

	/**
	 * @return userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return landAuditDate
	 */
	public Date getLandAuditDate() {
		return landAuditDate;
	}

	/**
	 * @param landAuditDate
	 */
	public void setLandAuditDate(Date landAuditDate) {
		this.landAuditDate = landAuditDate;
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
	 * @return billPayableType
	 */
	public String getBillPayableType() {
		return billPayableType;
	}

	/**
	 * @param billPayableType
	 */
	public void setBillPayableType(String billPayableType) {
		this.billPayableType = billPayableType;
	}

	/**
	 * @return billReceivableType
	 */
	public String getBillReceivableType() {
		return billReceivableType;
	}

	/**
	 * @param billReceivableType
	 */
	public void setBillReceivableType(String billReceivableType) {
		this.billReceivableType = billReceivableType;
	}

	/**
	 * @return verifyAmount
	 */
	public BigDecimal getVerifyAmount() {
		return verifyAmount;
	}

	/**
	 * @param verifyAmount
	 */
	public void setVerifyAmount(BigDecimal verifyAmount) {
		this.verifyAmount = verifyAmount;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return  the landOrgAuditUserCode
	 */
	public String getLandOrgAuditUserCode() {
		return landOrgAuditUserCode;
	}

	
	/**
	 * @param landOrgAuditUserCode the landOrgAuditUserCode to set
	 */
	public void setLandOrgAuditUserCode(String landOrgAuditUserCode) {
		this.landOrgAuditUserCode = landOrgAuditUserCode;
	}

	
	/**
	 * @return  the landOrgAuditUserName
	 */
	public String getLandOrgAuditUserName() {
		return landOrgAuditUserName;
	}

	
	/**
	 * @param landOrgAuditUserName the landOrgAuditUserName to set
	 */
	public void setLandOrgAuditUserName(String landOrgAuditUserName) {
		this.landOrgAuditUserName = landOrgAuditUserName;
	}

	/**
	 * @return destOrgCodeList
	 */
	public List<String> getDestOrgCodeList() {
		return destOrgCodeList;
	}

	/**
	 * @param  destOrgCodeList  
	 */
	public void setDestOrgCodeList(List<String> destOrgCodeList) {
		this.destOrgCodeList = destOrgCodeList;
	}

	
	/**
	 * @return  the billReceivableTypes
	 */
	public List<String> getBillReceivableTypes() {
		return billReceivableTypes;
	}

	
	/**
	 * @param billReceivableTypes the billReceivableTypes to set
	 */
	public void setBillReceivableTypes(List<String> billReceivableTypes) {
		this.billReceivableTypes = billReceivableTypes;
	}

	/**
	 * @return externalNodeType
	 */
	public String getExternalNodeType() {
		return externalNodeType;
	}

	/**
	 * @param externalNodeType
	 */
	public void setExternalNodeType(String externalNodeType) {
		this.externalNodeType = externalNodeType;
	}

	/**
	 * @return the productCodeList
	 */
	public List<String> getProductCodeList() {
		return productCodeList;
	}

	/**
	 * @param productCodeList the productCodeList to set
	 */
	public void setProductCodeList(List<String> productCodeList) {
		this.productCodeList = productCodeList;
	}
 
}
