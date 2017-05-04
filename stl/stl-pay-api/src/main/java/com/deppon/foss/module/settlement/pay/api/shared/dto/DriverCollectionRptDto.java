package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DriverCollectionRptEntity;

/**
 * pda司机缴款dto
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-12-18 下午3:08:59
 */
public class DriverCollectionRptDto implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7729639337871786033L;

	/**
	 * 接货列表
	 */
	private List<DriverCollectionRptDEntity> receivedList;

	/**
	 * 送货列表
	 */
	private List<DriverCollectionRptDEntity> deliverList;

	/**
	 * pda表头信息
	 */
	private DriverCollectionRptEntity entity;
	
	/**
	 * 报表列表
	 */
	private List<DriverCollectionRptEntity> rptList;

	/**
	 * 报表编号
	 */
	private String reportNo;

	/**
	 * 开始日期
	 */
	private Date reportBeginDate;

	/**
	 * 结束日期
	 */
	private Date reportEndDate;

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
	 * 接货总金额
	 */
	private BigDecimal receivedTotalAmount;

	/**
	 * 接货总票数
	 */
	private Short receivedTotalCount;

	/**
	 * 签收单数
	 */
	private Short signwaybillTotal;

	/**
	 * 送货总金额
	 */
	private BigDecimal deliverTotalAmount;

	/**
	 * 送货总条数
	 */
	private Short deliverTotalCount;

	/**
	 * 返单数
	 */
	private Short returnTicketTotal;
	
	/**
	 * 创建部门编码
	 */
	private String createOrgCode;
	
	/**
	 *缴款单总条数 
	 */
	private int totalCount;
	
	/**
	 * 报表类型  接货/送货
 	 */
	private String rptType;
	
	/**
	 * 报表查询日期
	 */
	private Date queryReportDate;
	/**
	 * 接货现金收入和刷卡收入
	 */
	private BigDecimal receiveCashIncome;
	/**
	 * 接货现金收入和刷卡收入
	 */
	private BigDecimal receiveCardIncome ;
	/**
	 * 送货现金收入和刷卡收入
	 */
	private BigDecimal deliverCashIncome;
	/**
	 * 送货现金收入和刷卡收入
	 */
	private BigDecimal deliverCardIncome ;

	/**
	 * @return receivedList
	 */
	public List<DriverCollectionRptDEntity> getReceivedList() {
		return receivedList;
	}

	/**
	 * @param receivedList
	 */
	public void setReceivedList(List<DriverCollectionRptDEntity> receivedList) {
		this.receivedList = receivedList;
	}

	/**
	 * @return deliverList
	 */
	public List<DriverCollectionRptDEntity> getDeliverList() {
		return deliverList;
	}

	/**
	 * @param deliverList
	 */
	public void setDeliverList(List<DriverCollectionRptDEntity> deliverList) {
		this.deliverList = deliverList;
	}

	/**
	 * @return entity
	 */
	public DriverCollectionRptEntity getEntity() {
		return entity;
	}

	/**
	 * @param entity
	 */
	public void setEntity(DriverCollectionRptEntity entity) {
		this.entity = entity;
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
	 * @return receivedTotalAmount
	 */
	public BigDecimal getReceivedTotalAmount() {
		return receivedTotalAmount;
	}

	/**
	 * @param receivedTotalAmount
	 */
	public void setReceivedTotalAmount(BigDecimal receivedTotalAmount) {
		this.receivedTotalAmount = receivedTotalAmount;
	}

	/**
	 * @return receivedTotalCount
	 */
	public Short getReceivedTotalCount() {
		return receivedTotalCount;
	}

	/**
	 * @param receivedTotalCount
	 */
	public void setReceivedTotalCount(Short receivedTotalCount) {
		this.receivedTotalCount = receivedTotalCount;
	}

	/**
	 * @return signwaybillTotal
	 */
	public Short getSignwaybillTotal() {
		return signwaybillTotal;
	}

	/**
	 * @param signwaybillTotal
	 */
	public void setSignwaybillTotal(Short signwaybillTotal) {
		this.signwaybillTotal = signwaybillTotal;
	}

	/**
	 * @return deliverTotalAmount
	 */
	public BigDecimal getDeliverTotalAmount() {
		return deliverTotalAmount;
	}

	/**
	 * @param deliverTotalAmount
	 */
	public void setDeliverTotalAmount(BigDecimal deliverTotalAmount) {
		this.deliverTotalAmount = deliverTotalAmount;
	}

	/**
	 * @return deliverTotalCount
	 */
	public Short getDeliverTotalCount() {
		return deliverTotalCount;
	}

	/**
	 * @param deliverTotalCount
	 */
	public void setDeliverTotalCount(Short deliverTotalCount) {
		this.deliverTotalCount = deliverTotalCount;
	}

	/**
	 * @return returnTicketTotal
	 */
	public Short getReturnTicketTotal() {
		return returnTicketTotal;
	}

	/**
	 * @param returnTicketTotal
	 */
	public void setReturnTicketTotal(Short returnTicketTotal) {
		this.returnTicketTotal = returnTicketTotal;
	}

	/**
	 * @return rptList
	 */
	public List<DriverCollectionRptEntity> getRptList() {
		return rptList;
	}

	/**
	 * @param  rptList  
	 */
	public void setRptList(List<DriverCollectionRptEntity> rptList) {
		this.rptList = rptList;
	}

	/**
	 * @return createOrgCode
	 */
	public String getCreateOrgCode() {
		return createOrgCode;
	}

	/**
	 * @param  createOrgCode  
	 */
	public void setCreateOrgCode(String createOrgCode) {
		this.createOrgCode = createOrgCode;
	}

	/**
	 * @return totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param  totalCount  
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @param  rptType  
	 */
	public String getRptType() {
		return rptType;
	}
	/**
	 * @param  rptType  
	 */
	public void setRptType(String rptType) {
		this.rptType = rptType;
	}
	/**
	 * @param  queryReportDate  
	 */
	public Date getQueryReportDate() {
		return queryReportDate;
	}
	/**
	 * @param  queryReportDate  
	 */
	public void setQueryReportDate(Date queryReportDate) {
		this.queryReportDate = queryReportDate;
	}

	public BigDecimal getReceiveCashIncome() {
		return receiveCashIncome;
	}

	public void setReceiveCashIncome(BigDecimal receiveCashIncome) {
		this.receiveCashIncome = receiveCashIncome;
	}

	public BigDecimal getReceiveCardIncome() {
		return receiveCardIncome;
	}

	public void setReceiveCardIncome(BigDecimal receiveCardIncome) {
		this.receiveCardIncome = receiveCardIncome;
	}

	public BigDecimal getDeliverCashIncome() {
		return deliverCashIncome;
	}

	public void setDeliverCashIncome(BigDecimal deliverCashIncome) {
		this.deliverCashIncome = deliverCashIncome;
	}

	public BigDecimal getDeliverCardIncome() {
		return deliverCardIncome;
	}

	public void setDeliverCardIncome(BigDecimal deliverCardIncome) {
		this.deliverCardIncome = deliverCardIncome;
	}

	
}
