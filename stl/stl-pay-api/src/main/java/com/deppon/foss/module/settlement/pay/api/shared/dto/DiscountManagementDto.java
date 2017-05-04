package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementEntity;

public class DiscountManagementDto {
	/**
	 * 折扣单明细
	 */
	private List<DiscountManagementDEntity> discountManagementDList;
	/**
	 * 折扣单
	 */
	private List<DiscountManagementEntity> discountManagementList;
	/**
	 * 单号集合
	 */
	private List<String> numbers;
	/**
	 * 折扣单单号
	 */
	private String discountNo;
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	
	/**
	 * 期间开始日期
	 */
	private Date periodBeginDate;
	/**
	 * 期间结束日期
	 */
	private Date periodEndDate;
	/**
	 * 分页数
	 */
	private Integer pageNum;
	/**
	 * 客户编码
	 */
	private String customerNo;
	/**
	 * 用户工号
	 */
	private String empCode;
	/**
	 * 用户姓名
	 */
	private String empName;
	
	/**
	 * 总行数
	 */
	private long count;
	/**
	 * 部门编码
	 */
	private String orgCode;

	/**
	 *折扣单状态
	 */
	private String[] discountStatus;
	/**
	 * 创建方式
	 */
	private String createType;

	public String getCreateType() {
		return createType;
	}

	public void setCreateType(String createType) {
		this.createType = createType;
	}

	public String[] getDiscountStatus() {
		return discountStatus;
	}

	public void setDiscountStatus(String[] discountStatus) {
		this.discountStatus = discountStatus;
	}

	public List<String> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}
	
	public String getDiscountNo() {
		return discountNo;
	}
	public void setDiscountNo(String discountNo) {
		this.discountNo = discountNo;
	}
	public Date getPeriodBeginDate() {
		return periodBeginDate;
	}
	public void setPeriodBeginDate(Date periodBeginDate) {
		this.periodBeginDate = periodBeginDate;
	}
	public Date getPeriodEndDate() {
		return periodEndDate;
	}
	public void setPeriodEndDate(Date periodEndDate) {
		this.periodEndDate = periodEndDate;
	}
	
	public String getCustomerNo() {
		return customerNo;
	}
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
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
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public List<DiscountManagementDEntity> getDiscountManagementDList() {
		return discountManagementDList;
	}
	public void setDiscountManagementDList(
			List<DiscountManagementDEntity> discountManagementDList) {
		this.discountManagementDList = discountManagementDList;
	}
	public List<DiscountManagementEntity> getDiscountManagementList() {
		return discountManagementList;
	}
	public void setDiscountManagementList(
			List<DiscountManagementEntity> discountManagementList) {
		this.discountManagementList = discountManagementList;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
	
}
