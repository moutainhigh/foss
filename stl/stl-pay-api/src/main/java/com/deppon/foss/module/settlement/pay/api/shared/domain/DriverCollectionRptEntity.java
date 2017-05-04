package com.deppon.foss.module.settlement.pay.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/*
 * @author ibm-pengzhen
 * @date 2012-10-10 下午12:33:53
 * @since
 * @version
 */
public class DriverCollectionRptEntity extends BaseEntity {
	
	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = -6731492088844930143L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 报表编号
	 */
	private String reportNo;
	/**
	 * 司机工号
	 */
	private String driverCode;
	/**
	 * 司机名称
	 */
	private String driverName;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 总票数
	 */
	private Integer waybillQtyTotal;
	/**
	 * 总件数
	 */
	private Integer piecesTotal;
	/**
	 * 应收总额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal receiveAmountTotal;

	/**
	 * 实收总额 BigDecimal存储，单位（元）；数据库以Long存储，单位（分）
	 */
	private BigDecimal receivedAmountTotal;
	/**
	 * 总体积
	 */
	private BigDecimal volumeTotal;
	/**
	 * 总重量
	 */
	private BigDecimal weightTotal;
	/**
	 * 签收单总数
	 */
	private Integer signwaybillTotal;
	/**
	 * 返单总数
	 */
	private Integer returnTicketTotal;
	/**
	 * 币种
	 */
	private String currencyCode;
	/**
	 * 业务日期
	 */
	private Date businessDate;
	/**
	 * 创建人工号
	 */
	private String createUserCode;
	/**
	 * 创建人名称
	 */
	private String createUserName;
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	/**
	 * 创建部门名称
	 */
	private String createOrgName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改人工号
	 */
	private String modifyUserCode;
	/**
	 * 修改人名称
	 */
	private String modifyUserName;
	/**
	 * 修改时间
	 */
	private Date modifyTime;
	/**
	 * 备注
	 */
	private String notes;
	/**
	 * 报表起始日期 (控制重复录入)
	 */
	private Date reportBeginDate;
	/**
	 * 报表结束日期(控制重复录入)
	 */
	private Date reportEndDate;
	
	/**
	 * 报表类型  接货/送货
 	 */
	private String rptType;
	
	/**
	 * 现金收入
	 */
	private BigDecimal cardTotalAmount;
	
	/**
	 * 刷卡收入
	 */
	private BigDecimal cashTotalAmount;
	
	/**
	 * @author 218392 zhangyongxue 
	 * @date 2015-07-08 16:57:09
	 * @return 实收返单数
	 */
	private Integer returnSingular;
	

	public Integer getReturnSingular() {
		return returnSingular;
	}

	public void setReturnSingular(Integer returnSingular) {
		this.returnSingular = returnSingular;
	}

	public BigDecimal getCardTotalAmount() {
		return cardTotalAmount;
	}

	public void setCardTotalAmount(BigDecimal cardTotalAmount) {
		this.cardTotalAmount = cardTotalAmount;
	}

	public BigDecimal getCashTotalAmount() {
		return cashTotalAmount;
	}

	public void setCashTotalAmount(BigDecimal cashTotalAmount) {
		this.cashTotalAmount = cashTotalAmount;
	}
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
	 * @return reportNo
	 */
	public String getReportNo() {
		return reportNo;
	}
	/**
	 * @param reportNo
	 */
	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	/**
	 * @return driverCode
	 */
	public String getDriverCode() {
		return driverCode;
	}
	/**
	 * @param driverCode
	 */
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	/**
	 * @return driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * @param driverName
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * @return vehicleNo
	 */
	public String getVehicleNo() {
		return vehicleNo;
	}
	/**
	 * @param vehicleNo
	 */
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	/**
	 * @return waybillQtyTotal
	 */
	public Integer getWaybillQtyTotal() {
		return waybillQtyTotal;
	}
	/**
	 * @param waybillQtyTotal
	 */
	public void setWaybillQtyTotal(Integer waybillQtyTotal) {
		this.waybillQtyTotal = waybillQtyTotal;
	}
	/**
	 * @return receiveAmountTotal
	 */
	public BigDecimal getReceiveAmountTotal() {
		return receiveAmountTotal;
	}
	/**
	 * @param receiveAmountTotal
	 */
	public void setReceiveAmountTotal(BigDecimal receiveAmountTotal) {
		this.receiveAmountTotal = receiveAmountTotal;
	}
	/**
	 * @return receivedAmountTotal
	 */
	public BigDecimal getReceivedAmountTotal() {
		return receivedAmountTotal;
	}
	/**
	 * @param receivedAmountTotal
	 */
	public void setReceivedAmountTotal(BigDecimal receivedAmountTotal) {
		this.receivedAmountTotal = receivedAmountTotal;
	}
	/**
	 * @return volumeTotal
	 */
	public BigDecimal getVolumeTotal() {
		return volumeTotal;
	}
	/**
	 * @param volumeTotal
	 */
	public void setVolumeTotal(BigDecimal volumeTotal) {
		this.volumeTotal = volumeTotal;
	}
	/**
	 * @return weightTotal
	 */
	public BigDecimal getWeightTotal() {
		return weightTotal;
	}
	/**
	 * @param weightTotal
	 */
	public void setWeightTotal(BigDecimal weightTotal) {
		this.weightTotal = weightTotal;
	}
	/**
	 * @return signwaybillTotal
	 */
	public Integer getSignwaybillTotal() {
		return signwaybillTotal;
	}
	/**
	 * @param signwaybillTotal
	 */
	public void setSignwaybillTotal(Integer signwaybillTotal) {
		this.signwaybillTotal = signwaybillTotal;
	}
	/**
	 * @return returnTicketTotal
	 */
	public Integer getReturnTicketTotal() {
		return returnTicketTotal;
	}
	/**
	 * @param returnTicketTotal
	 */
	public void setReturnTicketTotal(Integer returnTicketTotal) {
		this.returnTicketTotal = returnTicketTotal;
	}
	/**
	 * @return businessDate
	 */
	public Date getBusinessDate() {
		return businessDate;
	}
	/**
	 * @param businessDate
	 */
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	/**
	 * @return createUserCode
	 */
	public String getCreateUserCode() {
		return createUserCode;
	}
	/**
	 * @param createUserCode
	 */
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	/**
	 * @return createUserName
	 */
	public String getCreateUserName() {
		return createUserName;
	}
	/**
	 * @param createUserName
	 */
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}
	/**
	 * @param createOrgCode
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}
	/**
	 * @return createOrgName
	 */
	public String getCreateOrgName() {
		return createOrgName;
	}
	/**
	 * @param createOrgName
	 */
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * @return modifyUserCode
	 */
	public String getModifyUserCode() {
		return modifyUserCode;
	}

	/**
	 * @param modifyUserCode
	 */
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}

	/**
	 * @return modifyUserName
	 */
	public String getModifyUserName() {
		return modifyUserName;
	}

	/**
	 * @param modifyUserName
	 */
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	/**
	 * @return modifyTime
	 */
	public Date getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 */
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/**
	 * @return notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return reportBeginDate
	 */
	public Date getReportBeginDate() {
		return reportBeginDate;
	}

	/**
	 * @param reportBeginDate
	 */
	public void setReportBeginDate(Date reportBeginDate) {
		this.reportBeginDate = reportBeginDate;
	}
	/**
	 * @return reportEndDate
	 */
	public Date getReportEndDate() {
		return reportEndDate;
	}

	/**
	 * @param reportEndDate
	 */
	public void setReportEndDate(Date reportEndDate) {
		this.reportEndDate = reportEndDate;
	}
	/**
	 * @return currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * @param  currencyCode  
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return piecesTotal
	 */
	public Integer getPiecesTotal() {
		return piecesTotal;
	}

	/**
	 * @param piecesTotal
	 */
	public void setPiecesTotal(Integer piecesTotal) {
		this.piecesTotal = piecesTotal;
	}
	public String getRptType() {
		return rptType;
	}
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}
	
	
}
