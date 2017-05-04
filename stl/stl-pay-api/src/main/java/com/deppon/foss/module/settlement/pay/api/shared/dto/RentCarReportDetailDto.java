package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * 租车信息
 * @author FOSS-maojianqiang
 * @date 2014-8-7 下午2:55:26
 */
public class RentCarReportDetailDto implements Serializable {

	/**
	 * 序列话ID
	 */
	private static final long serialVersionUID = 8715119889200649243L;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 单号
	 */
	private String billNo;
	
	/**
	 * 租车编号
	 */
	private String rentCarNo;
	
	/**
	 * 应付单号
	 */
	private String payableNo;
	
	/**
	 * 报销/付款工作流号
	 */
	private String payWorkFlowNo;
	
	/**
	 * 重量
	 */
	private double weight;
	
	/**
	 * 体积
	 */
	private double volume;
	
	/**
	 * 单据生成日期
	 */
	private Date createTime;
	
	/**
	 * 单据类型
	 */
	private String billType;	
	/**
	 * 车牌号
	 */
	private String vehicleNo;
	
	/**
	 * 司机姓名
	 */
	private String driverName;
	
	/**
	 * 用车日期
	 */
	private Date useCarDate;
	
	/**
	 * 租车用途
	 */
	private String rentCarUseType;
	
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
	 * 租车标记部门
	 */
	private String rentCarDeptName;
	/**
	 * 租车标记部门集合
	 */
	private List<String> rentCarDeptCodeList;
	/**
	 * 租车标记部门
	 */
	private String rentCarDeptCode;
	
	/**
	 * 查询日期
	 */
	private Date queryDate;
	
	/**
	 * 查询终止日期
	 */
	private Date queryEndDate;
	/**
	 * 查询单据编号
	 */
	private String[] billNos;
	
	/**
	 * 查询类型
	 */
	private String queryType;
	
	/**
	 * 按用车/租车日期查询
	 */
	private String queryDateType;
	
	/**
	 * 返回的总条数
	 */
    private int totalCount=0;
    
   /**
    * 所有数据的总金额
    */
    private  BigDecimal totalAmount=BigDecimal.ZERO;


public BigDecimal getTotalAmount() {
	return totalAmount;
}

public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getRentCarNo() {
		return rentCarNo;
	}

	public void setRentCarNo(String rentCarNo) {
		this.rentCarNo = rentCarNo;
	}

	public String getPayableNo() {
		return payableNo;
	}

	public void setPayableNo(String payableNo) {
		this.payableNo = payableNo;
	}

	public String getPayWorkFlowNo() {
		return payWorkFlowNo;
	}

	public void setPayWorkFlowNo(String payWorkFlowNo) {
		this.payWorkFlowNo = payWorkFlowNo;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
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


	public String[] getBillNos() {
		return billNos;
	}

	public void setBillNos(String[] billNos) {
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

	public Date getUseCarDate() {
		return useCarDate;
	}

	public void setUseCarDate(Date useCarDate) {
		this.useCarDate = useCarDate;
	}

	public Date getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	public Date getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(Date queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public List<String> getRentCarDeptCodeList() {
		return rentCarDeptCodeList;
	}

	public void setRentCarDeptCodeList(List<String> rentCarDeptCodeList) {
		this.rentCarDeptCodeList = rentCarDeptCodeList;
	}
}
