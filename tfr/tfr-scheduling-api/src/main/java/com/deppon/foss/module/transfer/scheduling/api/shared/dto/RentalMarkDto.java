package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @title: RentalMarkDto
 * @description： 租车标记Dto
 * @author： zenghaibin
 * @date： 2014-5-28 下午3:02:34
 */
public class RentalMarkDto implements Serializable {

	/**
	 * 序列号
	 */
	
	private static final long serialVersionUID = -2675622414307981170L;

	private Date  billGenerationBeginTime ;//单据生成起始查询时间
	
	private Date  billGenerationEndTime;//单据生成结束查询时间
	
	private String vehicleNo;//车牌号
	
	private String borrowNo;//租车编号
	
	private String isWayBill;//是否运单
	
	private String isHandoverBill;//是否交接单
	
	private String isDeliverBill;//是否派送单
	
	private String isStowageBill;//是否配载单
	// 313352  gouyangyang
	private String isHandoverEirBill; // 是否快递交接单
	
	private String isAirPortBillList;//是否快递航空单
	
	private String orgCode;//部门编码
	
	private String createDept;//创建部门编码
	
	private List<String> handoverBillNoList;//交接单号
	
	private List<String> deliverbillNoList;//派送单号
	
	private List<String> stowageBillNoList;//配载单号
	
	private String departmentSignle;//部门标识

	private String queryType;//查询类型
	
	private String billsType;//检查小票号时的单据类型
	
	private boolean smallTicketVisbile;//小票单号控件是否可见
	
	private List<String> smallTicketList;//小票单号List
	
	private String smallTickets;//错误的小票单号
	
	private String waybillNos;//校验小票号时的错误运单
	
	private  String incomeCategories;//收入类别
	
	private String rentalUse;//租车用途
	/**车队的顶级车队所服务营业部的编码集合**/
	private List<String> orgCodeList;
	/**下面4个字段用于更新工作流信息*/
	/**预提状态**/
	private String accruedState;
	/**预提工作流号**/
	private String accruedWorkNo;
	/**预提工作流处理结果**/
	private String accruedWorkResult;
	/**租车编号**/
	private String temprentalMarkNo;
	//269701-lln-2015-08-31 begin
	/**零担运单号*/
	private List<String> wayBillNoList;
	/**快递运单号*/
	private List<String> expressWaybillNoList;
	/**是否快递运单*/
	private String isExpressWayBill;
	
	//判断零担还是快递的     type=1为零担    2为快递
	private String type;
	
	// 313352  gouyangyang
	private List<String> expressBillNoList; // 快递交接单号
	//269701-lln-2015-08-31 end
	
	/**快递机场扫面单号*/
	private List<String> expressAirportNoList;  
	
	public List<String> getExpressAirportNoList() {
		return expressAirportNoList;
	}

	public void setExpressAirportNoList(List<String> expressAirportNoList) {
		this.expressAirportNoList = expressAirportNoList;
	}

	public String getSmallTickets() {
		return smallTickets;
	}

	public String getCreateDept() {
		return createDept;
	}

	public String getIsAirPortBillList() {
		return isAirPortBillList;
	}

	public void setIsAirPortBillList(String isAirPortBillList) {
		this.isAirPortBillList = isAirPortBillList;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public void setSmallTickets(String smallTickets) {
		this.smallTickets = smallTickets;
	}

	public String getWaybillNos() {
		return waybillNos;
	}

	public void setWaybillNos(String waybillNos) {
		this.waybillNos = waybillNos;
	}

	public String getIsHandoverEirBill() {
		return isHandoverEirBill;
	}

	public void setIsHandoverEirBill(String isHandoverEirBill) {
		this.isHandoverEirBill = isHandoverEirBill;
	}

	public String getIsStowageBill() {
		return isStowageBill;
	}

	public void setIsStowageBill(String isStowageBill) {
		this.isStowageBill = isStowageBill;
	}

	public List<String> getStowageBillNoList() {
		return stowageBillNoList;
	}

	public void setStowageBillNoList(List<String> stowageBillNoList) {
		this.stowageBillNoList = stowageBillNoList;
	}

	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	/**
	 * 租车编号集合
	 */
	private List<String> rentCarNos;
	
	public String getRentalUse() {
		return rentalUse;
	}

	public void setRentalUse(String rentalUse) {
		this.rentalUse = rentalUse;
	}

	public String getIncomeCategories() {
		return incomeCategories;
	}

	public void setIncomeCategories(String incomeCategories) {
		this.incomeCategories = incomeCategories;
	}

	public List<String> getSmallTicketList() {
		return smallTicketList;
	}

	public void setSmallTicketList(List<String> smallTicketList) {
		this.smallTicketList = smallTicketList;
	}

	public boolean getSmallTicketVisbile() {
		return smallTicketVisbile;
	}

	public void setSmallTicketVisbile(boolean smallTicketVisbile) {
		this.smallTicketVisbile = smallTicketVisbile;
	}

	public String getBillsType() {
		return billsType;
	}

	public void setBillsType(String billsType) {
		this.billsType = billsType;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getTemprentalMarkNo() {
		return temprentalMarkNo;
	}

	public void setTemprentalMarkNo(String temprentalMarkNo) {
		this.temprentalMarkNo = temprentalMarkNo;
	}

	public List<String> getHandoverBillNoList() {
		return handoverBillNoList;
	}

	public List<String> getExpressBillNoList() {
		return expressBillNoList;
	}

	public void setExpressBillNoList(List<String> expressBillNoList) {
		this.expressBillNoList = expressBillNoList;
	}

	public void setHandoverBillNoList(List<String> handoverBillNoList) {
		this.handoverBillNoList = handoverBillNoList;
	}

	public List<String> getDeliverbillNoList() {
		return deliverbillNoList;
	}

	public void setDeliverbillNoList(List<String> deliverbillNoList) {
		this.deliverbillNoList = deliverbillNoList;
	}

	public String getDepartmentSignle() {
		return departmentSignle;
	}

	public void setDepartmentSignle(String departmentSignle) {
		this.departmentSignle = departmentSignle;
	}

	public Date getBillGenerationBeginTime() {
		return billGenerationBeginTime;
	}

	public void setBillGenerationBeginTime(Date billGenerationBeginTime) {
		this.billGenerationBeginTime = billGenerationBeginTime;
	}

	public Date getBillGenerationEndTime() {
		return billGenerationEndTime;
	}

	public void setBillGenerationEndTime(Date billGenerationEndTime) {
		this.billGenerationEndTime = billGenerationEndTime;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public String getBorrowNo() {
		return borrowNo;
	}

	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}

	public String getIsWayBill() {
		return isWayBill;
	}

	public void setIsWayBill(String isWayBill) {
		this.isWayBill = isWayBill;
	}

	public String getIsHandoverBill() {
		return isHandoverBill;
	}

	public void setIsHandoverBill(String isHandoverBill) {
		this.isHandoverBill = isHandoverBill;
	}

	public String getIsDeliverBill() {
		return isDeliverBill;
	}

	public void setIsDeliverBill(String isDeliverBill) {
		this.isDeliverBill = isDeliverBill;
	}

	public String getAccruedState() {
		return accruedState;
	}

	public void setAccruedState(String accruedState) {
		this.accruedState = accruedState;
	}

	public String getAccruedWorkNo() {
		return accruedWorkNo;
	}

	public void setAccruedWorkNo(String accruedWorkNo) {
		this.accruedWorkNo = accruedWorkNo;
	}

	public String getAccruedWorkResult() {
		return accruedWorkResult;
	}

	public void setAccruedWorkResult(String accruedWorkResult) {
		this.accruedWorkResult = accruedWorkResult;
	}

	public List<String> getRentCarNos() {
		return rentCarNos;
	}

	public void setRentCarNos(List<String> rentCarNos) {
		this.rentCarNos = rentCarNos;
	}

	
	/**
	 * @return the wayBillNoList
	 */
	public List<String> getWayBillNoList() {
		return wayBillNoList;
	}

	/**
	 * @param wayBillNoList the wayBillNoList to set
	 */
	public void setWayBillNoList(List<String> wayBillNoList) {
		this.wayBillNoList = wayBillNoList;
	}

	/**
	 * 快递运单号
	 * @return the expressWaybillNoList
	 */
	public List<String> getExpressWaybillNoList() {
		return expressWaybillNoList;
	}

	/**
	 * 快递运单号
	 * @param expressWaybillNoList the expressWaybillNoList to set
	 */
	public void setExpressWaybillNoList(List<String> expressWaybillNoList) {
		this.expressWaybillNoList = expressWaybillNoList;
	}

	/**
	 * 是否快递运单
	 * @return the isExpressWayBill
	 */
	public String getIsExpressWayBill() {
		return isExpressWayBill;
	}

	/**
	 * 是否快递运单
	 * @param isExpressWayBill the isExpressWayBill to set
	 */
	public void setIsExpressWayBill(String isExpressWayBill) {
		this.isExpressWayBill = isExpressWayBill;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
}
