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

import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;

/**
 * 预收单参数dto
 * 
 * @author ibm-qiaolifeng
 * @date 2012-10-12 下午3:42:05
 */
public class BillDepositReceivedDto implements Serializable {

	/**
	 * 预应收单参数类序列号
	 */
	private static final long serialVersionUID = -3288769059561929026L;

	/**
	 * 预收单列表
	 */
	private List<BillDepositReceivedEntity> billDepositreceivedEntityList = new ArrayList<BillDepositReceivedEntity>();

	/**
	 * 付款单号
	 */
	private String paymentNo;

	/*
	 * 界面参数
	 */
	/**
	 * 预收单业务开始时间
	 */
	private Date startBusinessDate;

	/**
	 * 预收单业务结束时间
	 */
	private Date endBusinessDate;

	/**
	 * 预收单记账开始时间
	 */
	private Date startAccountDate;

	/**
	 * 预收单记账结束时间
	 */
	private Date endAccountDate;

	/**
	 * 预收单页面最大显示条数
	 */
	private int maxShowNum;

	/**
	 * 预收单客户编码
	 */
	private String customerCode;

	/**
	 * 预收单部门编码
	 */
	private String collectionOrgCode;

	/**
	 * 预收单号集
	 */
	private String precollectedNumbers;

	/*
	 * 隐含或整合后参数
	 */
	/**
	 * 预收单号集
	 */
	private List<String> precollectedNos;

	/**
	 * 预收单是否有效
	 */
	private String active;

	/**
	 * 预收单是否红单
	 */
	private String isRedBack;

	/**
	 * 预收单退款状态
	 */
	private String refundStatus;

	/**
	 * 预收单状态
	 */
	private List<String> statusList;

	/**
	 * 查询方式
	 */
	private String queryType;

	/**
	 * 当前登录用户的员工编码
	 */
	private String empCode;

	/**
	 * 付款单的汇款状态
	 */
	private String remitStatus;

	
	/**
	 * @return 
		billDepositreceivedEntityList
	 */
	public List<BillDepositReceivedEntity> getBillDepositreceivedEntityList() {
		return billDepositreceivedEntityList;
	}

	
	/**
	 * @param 
		billDepositreceivedEntityList
	 */
	public void setBillDepositreceivedEntityList(List<BillDepositReceivedEntity> billDepositreceivedEntityList) {
		this.billDepositreceivedEntityList = billDepositreceivedEntityList;
	}

	
	/**
	 * @return 
		paymentNo
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	
	/**
	 * @param 
		paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
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
		collectionOrgCode
	 */
	public String getCollectionOrgCode() {
		return collectionOrgCode;
	}

	
	/**
	 * @param 
		collectionOrgCode
	 */
	public void setCollectionOrgCode(String collectionOrgCode) {
		this.collectionOrgCode = collectionOrgCode;
	}

	
	/**
	 * @return 
		precollectedNumbers
	 */
	public String getPrecollectedNumbers() {
		return precollectedNumbers;
	}

	
	/**
	 * @param 
		precollectedNumbers
	 */
	public void setPrecollectedNumbers(String precollectedNumbers) {
		this.precollectedNumbers = precollectedNumbers;
	}

	
	/**
	 * @return 
		precollectedNos
	 */
	public List<String> getPrecollectedNos() {
		return precollectedNos;
	}

	
	/**
	 * @param 
		precollectedNos
	 */
	public void setPrecollectedNos(List<String> precollectedNos) {
		this.precollectedNos = precollectedNos;
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
		refundStatus
	 */
	public String getRefundStatus() {
		return refundStatus;
	}

	
	/**
	 * @param 
		refundStatus
	 */
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	
	/**
	 * @return 
		statusList
	 */
	public List<String> getStatusList() {
		return statusList;
	}

	
	/**
	 * @param 
		statusList
	 */
	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
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

	
	/**
	 * @return 
		remitStatus
	 */
	public String getRemitStatus() {
		return remitStatus;
	}

	
	/**
	 * @param 
		remitStatus
	 */
	public void setRemitStatus(String remitStatus) {
		this.remitStatus = remitStatus;
	}



}
