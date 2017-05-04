package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 租车信息
 * @author 045738
 *
 */
public class RentCarReportDto implements Serializable {

	/**
	 * 序列话ID
	 */
	private static final long serialVersionUID = 8715119889200649243L;
	
	/**
	 * 应付单号
	 */
	private String payableNo;
	/**
	 * 租车编号
	 */
	private String rentCarNo;
	/**
	 * 付款状态
	 */
	private String payStatus;
	/**
	 * 报销/付款工作流号
	 */
	private String payWorkFlowNo;
	/**
	 * 是否已报销/转报销
	 */
	private String reimbursement;
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	/**
	 * 租车用途
	 */
	private String rentCarUseType;
	/**
	 * 租车金额
	 */
	private BigDecimal rentCarAmount;
	/**
	 * 费用承担部门
	 */
	private String costDept;
	
	/**
	 * 费用承担部门名称
	 */
	private String costDeptName;
	/**
	 * 费用类型
	 */
	private String costType;
	/**
	 * 业务发生日期
	 */
	private Date businessDate;
	/**
	 * 预提状态	
	 */
	private String withholdingStatus;
	/**
	 * 预提工作流号
	 */
	private String withholdingWorkFlowNo;
	/**
	 * 预提工作流处理结果
	 */
	private String withholdingResult;
	/**
	 * 费用所属月份
	 */
	private Date costDate;
	/**
	 * 事业部
	 */
	private String division;
	/**
	 * 大区
	 */
	private String bigArea;
	/**
	 * 小区
	 */
	private String smallArea;
	/**
	 * 总重量
	 */
	private double totalWeight;
	/**
	 * 总体积
	 */
	private double totalVolume;
	/**
	 * 公里数
	 */
	private double kilometre;
	/**
	 * 目的地
	 */
	private String destination;
	/**
	 * 出发地
	 */
	private String departure;
	/**
	 * 用车日期
	 */
	private Date useCarDate;
	/**
	 * 用车原因
	 */
	private String useCarReasion;
	/**
	 * 应走货票数
	 */
	private double shallTakegoodsQyt;
	/**
	 * 实际走货票数
	 */
	private double actualTakegoodsQyt;
	/**
	 * 车型
	 */
	private String carType;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机姓名
	 */
	private String driverCode;
	
	/**
	 * 联系电话
	 */
	private String  driverPhone;
	
	/**
	 * 租车标记时间
	 */
	private Date rentCarTime;
	/**
	 * 租车标记部门
	 */
	private String rentCarDeptName;
	/**
	 * 租车标记部门
	 */
	private String rentCarDeptCode;
	/**
	 * 租车标记操作人
	 */
	private String rentMarkUserInfo;
	
	/**
	 * 开始日期
	 */
	private Date startDate;
	
	/**
	 * 结束日期
	 */
	private Date endDate;
	
	/**
	 * 查询单据编号
	 */
	private List<String> billNos;
	
	/**
	 * 查询类型
	 */
	private String queryType;
	
	/**
	 * 按用车/租车日期查询
	 */
	private String queryDateType;
	
	/**
	 * 部门
	 */
	private List<String> orgCodeList;
	
	/**
	 * 总条数
	 */
	private int totalCount;
	
	/**
	 * 总金额
	 */
	private BigDecimal totalAmount;
	
	/**
	 * 总体积
	 */
	private int totalTakeGoodsQyt;
	
	/**
	 * 是否多次标记
	 */
	private String isRepeateMark;
	
	/**
	 * 应付单的支付状态
	 */
	private String payablePayStatus;
	
	/**
	 * 是否有效租车记录
	 */
	private String active;
	
	/**
	 * 版本号
	 */
	private short version;
	
	/**
	 * 应付单部门编码
	 */
	private String payableOrgCode;
	
	/**
	 * 当前登录人
	 */
	private String empCode;
	
	/**
	 * 当前登录部门编码
	 */
	private String currentDeptCode;
	
	/**
	 * 临时租车备注
	 */
	private String notes;
 	
	/**
	 * 预提费用承担部门
	 */
	private String withholdingCostDeptName;
	
	public String getPayableNo() {
		return payableNo;
	}
	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}
	public String getRentCarNo() {
		return rentCarNo;
	}
	public void setRentCarNo(String rentCarNo) {
		this.rentCarNo = rentCarNo;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayWorkFlowNo() {
		return payWorkFlowNo;
	}
	public void setPayWorkFlowNo(String payWorkFlowNo) {
		this.payWorkFlowNo = payWorkFlowNo;
	}
	public String getReimbursement() {
		return reimbursement;
	}
	public void setReimbursement(String reimbursement) {
		this.reimbursement = reimbursement;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public String getRentCarUseType() {
		return rentCarUseType;
	}
	public void setRentCarUseType(String rentCarUseType) {
		this.rentCarUseType = rentCarUseType;
	}
	public BigDecimal getRentCarAmount() {
		return rentCarAmount;
	}
	public void setRentCarAmount(BigDecimal rentCarAmount) {
		this.rentCarAmount = rentCarAmount;
	}
	public String getCostDept() {
		return costDept;
	}
	public void setCostDept(String costDept) {
		this.costDept = costDept;
	}
	public String getCostType() {
		return costType;
	}
	public void setCostType(String costType) {
		this.costType = costType;
	}
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
	}
	public String getWithholdingStatus() {
		return withholdingStatus;
	}
	public void setWithholdingStatus(String withholdingStatus) {
		this.withholdingStatus = withholdingStatus;
	}
	public String getWithholdingWorkFlowNo() {
		return withholdingWorkFlowNo;
	}
	public void setWithholdingWorkFlowNo(String withholdingWorkFlowNo) {
		this.withholdingWorkFlowNo = withholdingWorkFlowNo;
	}
	public String getWithholdingResult() {
		return withholdingResult;
	}
	public void setWithholdingResult(String withholdingResult) {
		this.withholdingResult = withholdingResult;
	}
	public Date getCostDate() {
		return costDate;
	}
	public void setCostDate(Date costDate) {
		this.costDate = costDate;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getBigArea() {
		return bigArea;
	}
	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}
	public String getSmallArea() {
		return smallArea;
	}
	public void setSmallArea(String smallArea) {
		this.smallArea = smallArea;
	}
	public double getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}
	public double getTotalVolume() {
		return totalVolume;
	}
	public void setTotalVolume(double totalVolume) {
		this.totalVolume = totalVolume;
	}
	public double getKilometre() {
		return kilometre;
	}
	public void setKilometre(double kilometre) {
		this.kilometre = kilometre;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public Date getUseCarDate() {
		return useCarDate;
	}
	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}
	public String getUseCarReasion() {
		return useCarReasion;
	}
	public void setUseCarReasion(String useCarReasion) {
		this.useCarReasion = useCarReasion;
	}
	public double getShallTakegoodsQyt() {
		return shallTakegoodsQyt;
	}
	public void setShallTakegoodsQyt(double shallTakegoodsQyt) {
		this.shallTakegoodsQyt = shallTakegoodsQyt;
	}
	public double getActualTakegoodsQyt() {
		return actualTakegoodsQyt;
	}
	public void setActualTakegoodsQyt(double actualTakegoodsQyt) {
		this.actualTakegoodsQyt = actualTakegoodsQyt;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public Date getRentCarTime() {
		return rentCarTime;
	}
	public void setRentCarTime(Date rentCarTime) {
		this.rentCarTime = rentCarTime;
	}
	public String getRentCarDeptName() {
		return rentCarDeptName;
	}
	public void setRentCarDeptName(String rentCarDeptName) {
		this.rentCarDeptName = rentCarDeptName;
	}
	public String getRentCarDeptCode() {
		return rentCarDeptCode;
	}
	public void setRentCarDeptCode(String rentCarDeptCode) {
		this.rentCarDeptCode = rentCarDeptCode;
	}
	public String getRentMarkUserInfo() {
		return rentMarkUserInfo;
	}
	public void setRentMarkUserInfo(String rentMarkUserInfo) {
		this.rentMarkUserInfo = rentMarkUserInfo;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<String> getBillNos() {
		return billNos;
	}
	public void setBillNos(List<String> billNos) {
		this.billNos = billNos;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryDateType() {
		return queryDateType;
	}
	public void setQueryDateType(String queryDateType) {
		this.queryDateType = queryDateType;
	}
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getTotalTakeGoodsQyt() {
		return totalTakeGoodsQyt;
	}
	public void setTotalTakeGoodsQyt(int totalTakeGoodsQyt) {
		this.totalTakeGoodsQyt = totalTakeGoodsQyt;
	}
	public String getIsRepeateMark() {
		return isRepeateMark;
	}
	public void setIsRepeateMark(String isRepeateMark) {
		this.isRepeateMark = isRepeateMark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getPayablePayStatus() {
		return payablePayStatus;
	}
	public void setPayablePayStatus(String payablePayStatus) {
		this.payablePayStatus = payablePayStatus;
	}
	public String getCostDeptName() {
		return costDeptName;
	}
	public void setCostDeptName(String costDeptName) {
		this.costDeptName = costDeptName;
	}
	public short getVersion() {
		return version;
	}
	public void setVersion(short version) {
		this.version = version;
	}
	public String getPayableOrgCode() {
		return payableOrgCode;
	}
	public void setPayableOrgCode(String payableOrgCode) {
		this.payableOrgCode = payableOrgCode;
	}
	public String getDriverCode() {
		return driverCode;
	}
	public void setDriverCode(String driverCode) {
		this.driverCode = driverCode;
	}
	public String getDriverPhone() {
		return driverPhone;
	}
	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getCurrentDeptCode() {
		return currentDeptCode;
	}
	public void setCurrentDeptCode(String currentDeptCode) {
		this.currentDeptCode = currentDeptCode;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getWithholdingCostDeptName() {
		return withholdingCostDeptName;
	}
	public void setWithholdingCostDeptName(String withholdingCostDeptName) {
		this.withholdingCostDeptName = withholdingCostDeptName;
	}
	
}
