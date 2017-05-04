package com.deppon.foss.module.settlement.common.api.shared.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @author 218392 张永雪
 * @date 2016-05-27 20:58:00
 * VTS外请车费用调整同意之后调用结算重生成整车尾款应付单restful接口:请求实体
 */
public class RequestAdjustFeeEntity implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 1.增减类型:increase-增加； decrease-减少
	 */
	private String adjustType;
	
	/**
	 * 2.调整费用（单位元）往数据库中插入的时候，要乘以100
	 */
	private BigDecimal adjustAmount;
	
	/**
	 * 3.运单号
	 */
	private String waybillNo;
	
	/**
	 * 5.员工工号
	 * @return
	 */
	private String empCode;
	
	/**
	 * 6.员工工号
	 * @return
	 */
	private String empName;
	
	/**
	 * 7.修改部门code
	 * @return
	 */
	private String modifyDeptCode;
	
	/**
	 * 8.修改部门名称
	 * @return
	 */
	private String modifyDeptName;
	
	/**
	 * 合同编号
	 */
	private String contractCode;
	
	/**
	 * 开单日期
	 */
	private Date billTime;
	
	/**
	 * 合同打印日期
	 */
	private Date contractDate;
	
	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public BigDecimal getAdjustAmount() {
		return adjustAmount;
	}

	public void setAdjustAmount(BigDecimal adjustAmount) {
		this.adjustAmount = adjustAmount;
	}

	public String getWaybillNo() {
		return waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getModifyDeptCode() {
		return modifyDeptCode;
	}

	public void setModifyDeptCode(String modifyDeptCode) {
		this.modifyDeptCode = modifyDeptCode;
	}

	public String getModifyDeptName() {
		return modifyDeptName;
	}

	public void setModifyDeptName(String modifyDeptName) {
		this.modifyDeptName = modifyDeptName;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	
}
