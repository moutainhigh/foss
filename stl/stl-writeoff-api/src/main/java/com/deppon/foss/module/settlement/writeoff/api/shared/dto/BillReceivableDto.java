/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;

/**
 * 应收单参数dto
 * 
 * @author ibm-qiaolifeng
 * @date 2012-10-12 下午3:42:05
 */
public class BillReceivableDto implements Serializable {

	/**
	 * 应收单参数类序列号
	 */
	private static final long serialVersionUID = 501822517414376782L;

	/**
	 * 应收单列表
	 */
	private List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();

	/*
	 * 界面参数
	 */
	/**
	 * 应收单业务开始时间
	 */
	private Date startBusinessDate;

	/**
	 * 应收单业务结束时间
	 */
	private Date endBusinessDate;

	/**
	 * 应收单记账开始时间
	 */
	private Date startAccountDate;

	/**
	 * 应收单记账结束时间
	 */
	private Date endAccountDate;

	/**
	 * 应收单页面最大显示条数
	 */
	private int maxShowNum;

	/**
	 * 应收单客户编码
	 */
	private String customerCode;

	/**
	 * 应收单号集
	 */
	private String receivableNumbers;

	/*
	 * 隐含或整合后参数
	 */
	/**
	 * 登录用户所属部门
	 */
	private String generatingOrgCode;

	/**
	 * 应收单号集
	 */
	private List<String> receivableNos;

	/**
	 * 应收单是否有效
	 */
	private String active;

	/**
	 * 应收单是否红单
	 */
	private String isRedBack;

	/**
	 * 应收单是否已经制作对账单
	 */
	private String isGrenerateStatement;

	/**
	 * 应收单的类型集合
	 */
	private List<String> billTypeList;

	/**
	 * 查询方式
	 */
	private String queryType;

	/**
	 * 当前登录用户的员工编码
	 */
	private String empCode;
	
	/**
	 * 审核状态
	 */
	private String approveStatus;

	/**
	 * 应收单对应运单单号集
	 */
	private List<String> waybillNoList;
	
	/**
	 * @get
	 * @return waybillNoList
	 */
	public List<String> getWaybillNoList() {
		return waybillNoList;
	}


	/**
	 * @set
	 * @param waybillNoList
	 */
	public void setWaybillNoList(List<String> waybillNoList) {
		this.waybillNoList = waybillNoList;
	}



	/**
	 * @get
	 * @return approveStatus
	 */
	public String getApproveStatus() {
		/*
		 * @get
		 * @return approveStatus
		 */
		return approveStatus;
	}


	
	/**
	 * @set
	 * @param approveStatus
	 */
	public void setApproveStatus(String approveStatus) {
		/*
		 *@set
		 *@this.approveStatus = approveStatus
		 */
		this.approveStatus = approveStatus;
	}


	/**
	 * @return 
		billReceivableEntityList
	 */
	public List<BillReceivableEntity> getBillReceivableEntityList() {
		return billReceivableEntityList;
	}

	
	/**
	 * @param 
		billReceivableEntityList
	 */
	public void setBillReceivableEntityList(List<BillReceivableEntity> billReceivableEntityList) {
		this.billReceivableEntityList = billReceivableEntityList;
	}

	
	/**
	 * @return 
		startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	
	/**
	 * @param 
		startBusinessDate
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	
	/**
	 * @return 
		endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	
	/**
	 * @param 
		endBusinessDate
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	
	/**
	 * @return 
		startAccountDate
	 */
	public Date getStartAccountDate() {
		return startAccountDate;
	}

	
	/**
	 * @param 
		startAccountDate
	 */
	public void setStartAccountDate(Date startAccountDate) {
		this.startAccountDate = startAccountDate;
	}

	
	/**
	 * @return 
		endAccountDate
	 */
	public Date getEndAccountDate() {
		return endAccountDate;
	}

	
	/**
	 * @param 
		endAccountDate
	 */
	public void setEndAccountDate(Date endAccountDate) {
		this.endAccountDate = endAccountDate;
	}

	
	/**
	 * @return 
		maxShowNum
	 */
	public int getMaxShowNum() {
		return maxShowNum;
	}

	
	/**
	 * @param 
		maxShowNum
	 */
	public void setMaxShowNum(int maxShowNum) {
		this.maxShowNum = maxShowNum;
	}

	
	/**
	 * @return 
		customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	
	/**
	 * @param 
		customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	
	/**
	 * @return 
		receivableNumbers
	 */
	public String getReceivableNumbers() {
		return receivableNumbers;
	}

	
	/**
	 * @param 
		receivableNumbers
	 */
	public void setReceivableNumbers(String receivableNumbers) {
		this.receivableNumbers = receivableNumbers;
	}

	
	/**
	 * @return 
		generatingOrgCode
	 */
	public String getGeneratingOrgCode() {
		return generatingOrgCode;
	}

	
	/**
	 * @param 
		generatingOrgCode
	 */
	public void setGeneratingOrgCode(String generatingOrgCode) {
		this.generatingOrgCode = generatingOrgCode;
	}

	
	/**
	 * @return 
		receivableNos
	 */
	public List<String> getReceivableNos() {
		return receivableNos;
	}

	
	/**
	 * @param 
		receivableNos
	 */
	public void setReceivableNos(List<String> receivableNos) {
		this.receivableNos = receivableNos;
	}

	
	/**
	 * @return 
		active
	 */
	public String getActive() {
		return active;
	}

	
	/**
	 * @param 
		active
	 */
	public void setActive(String active) {
		this.active = active;
	}

	
	/**
	 * @return 
		isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	
	/**
	 * @param 
		isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	
	/**
	 * @return 
		isGrenerateStatement
	 */
	public String getIsGrenerateStatement() {
		return isGrenerateStatement;
	}

	
	/**
	 * @param 
		isGrenerateStatement
	 */
	public void setIsGrenerateStatement(String isGrenerateStatement) {
		this.isGrenerateStatement = isGrenerateStatement;
	}

	
	/**
	 * @return 
		billTypeList
	 */
	public List<String> getBillTypeList() {
		return billTypeList;
	}

	
	/**
	 * @param 
		billTypeList
	 */
	public void setBillTypeList(List<String> billTypeList) {
		this.billTypeList = billTypeList;
	}

	
	/**
	 * @return 
		queryType
	 */
	public String getQueryType() {
		return queryType;
	}

	
	/**
	 * @param 
		queryType
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	
	/**
	 * @return 
		empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	
	/**
	 * @param 
		empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	
}
