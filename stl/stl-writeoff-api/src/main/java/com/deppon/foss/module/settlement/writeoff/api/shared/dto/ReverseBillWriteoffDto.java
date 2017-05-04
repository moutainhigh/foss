package com.deppon.foss.module.settlement.writeoff.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;

/**
 * 反核消时，传递参数Dto类
 * 
 * @author foss-qiaolifeng
 * @date 2012-10-24 上午11:13:45
 */
public class ReverseBillWriteoffDto implements Serializable {

	/**
	 * 反核消时，传递参数Dto类序列号
	 */
	private static final long serialVersionUID = -828441647830677624L;

	/**
	 * 界面参数,业务开始时间
	 */
	private Date startBusinessDate;

	/**
	 * 界面参数,业务结束时间
	 */
	private Date endBusinessDate;

	/**
	 * 界面参数,部门编码
	 */
	private String orgCode;

	/**
	 * 界面参数,客户编码
	 */
	private String customerCode;

	/**
	 * 界面参数,是否红单
	 */
	private String isRedBack;

	/**
	 * 界面参数,核销类型
	 */
	private String writeoffType;

	/**
	 * 界面参数,核销方式
	 */
	private String createType;

	/**
	 * 界面参数,核销单号
	 */
	private String writeoffBillNo;

	/**
	 * 界面参数,运单号
	 */
	private String waybillNo;

	/**
	 * 界面参数,选择的反核销单号字符串
	 */
	private String writeoffBillNos;

	/**
	 * 界面参数,应付单号
	 */
	private String payableBillNo;

	/**
	 * 界面参数,预付单号
	 */
	private String advancePayBillNo;

	/**
	 * 选择的反核销单号字符串整合list
	 */
	private List<String> writeoffBillNoList;

	/**
	 * 隐含参数,当前登录用户所属部门及下属部门列表
	 */
	private List<String> loginOrgCodeList;

	/**
	 * 隐含参数,是否有效
	 */
	private String active;

	/**
	 * 核销单明细实体列表
	 */
	private List<BillWriteoffEntity> billWriteoffEntityList = new ArrayList<BillWriteoffEntity>();

	/**
	 * 反核销总条数
	 */
	private long writeoffTotalRows;

	/**
	 * 反核销总金额
	 */
	private BigDecimal writeoffTotalAmout;

	/**
	 * 每页显示条数
	 */
	private int onePageSize;

	/**
	 * 当前登录用户员工变那么
	 */
	private String empCode;

	/**
	 * 是否初始化
	 */
	private String isInit;
	
	/**
	 * 界面参数,预收单号
	 */
	private String depositNo;

	/**
	 * @get
	 * @return isInit
	 */
	public String getIsInit() {
		/*
		 * @get
		 * 
		 * @return isInit
		 */
		return isInit;
	}

	/**
	 * @set
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		/*
		 * @set
		 * 
		 * @this.isInit = isInit
		 */
		this.isInit = isInit;
	}

	/**
	 * @return startBusinessDate
	 */
	public Date getStartBusinessDate() {
		return startBusinessDate;
	}

	/**
	 * @param startBusinessDate
	 */
	public void setStartBusinessDate(Date startBusinessDate) {
		this.startBusinessDate = startBusinessDate;
	}

	/**
	 * @return endBusinessDate
	 */
	public Date getEndBusinessDate() {
		return endBusinessDate;
	}

	/**
	 * @param endBusinessDate
	 */
	public void setEndBusinessDate(Date endBusinessDate) {
		this.endBusinessDate = endBusinessDate;
	}

	/**
	 * @return orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		return isRedBack;
	}

	/**
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		this.isRedBack = isRedBack;
	}

	/**
	 * @return writeoffType
	 */
	public String getWriteoffType() {
		return writeoffType;
	}

	/**
	 * @param writeoffType
	 */
	public void setWriteoffType(String writeoffType) {
		this.writeoffType = writeoffType;
	}

	/**
	 * @return createType
	 */
	public String getCreateType() {
		return createType;
	}

	/**
	 * @param createType
	 */
	public void setCreateType(String createType) {
		this.createType = createType;
	}

	/**
	 * @return writeoffBillNo
	 */
	public String getWriteoffBillNo() {
		return writeoffBillNo;
	}

	/**
	 * @param writeoffBillNo
	 */
	public void setWriteoffBillNo(String writeoffBillNo) {
		this.writeoffBillNo = writeoffBillNo;
	}

	/**
	 * @return waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	/**
	 * @param waybillNo
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	/**
	 * @return writeoffBillNos
	 */
	public String getWriteoffBillNos() {
		return writeoffBillNos;
	}

	/**
	 * @param writeoffBillNos
	 */
	public void setWriteoffBillNos(String writeoffBillNos) {
		this.writeoffBillNos = writeoffBillNos;
	}

	/**
	 * @return writeoffBillNoList
	 */
	public List<String> getWriteoffBillNoList() {
		return writeoffBillNoList;
	}

	/**
	 * @param writeoffBillNoList
	 */
	public void setWriteoffBillNoList(List<String> writeoffBillNoList) {
		this.writeoffBillNoList = writeoffBillNoList;
	}

	/**
	 * @return loginOrgCodeList
	 */
	public List<String> getLoginOrgCodeList() {
		return loginOrgCodeList;
	}

	/**
	 * @param loginOrgCodeList
	 */
	public void setLoginOrgCodeList(List<String> loginOrgCodeList) {
		this.loginOrgCodeList = loginOrgCodeList;
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
	 * @return billWriteoffEntityList
	 */
	public List<BillWriteoffEntity> getBillWriteoffEntityList() {
		return billWriteoffEntityList;
	}

	/**
	 * @param billWriteoffEntityList
	 */
	public void setBillWriteoffEntityList(
			List<BillWriteoffEntity> billWriteoffEntityList) {
		this.billWriteoffEntityList = billWriteoffEntityList;
	}

	/**
	 * @return writeoffTotalRows
	 */
	public long getWriteoffTotalRows() {
		return writeoffTotalRows;
	}

	/**
	 * @param writeoffTotalRows
	 */
	public void setWriteoffTotalRows(long writeoffTotalRows) {
		this.writeoffTotalRows = writeoffTotalRows;
	}

	/**
	 * @return writeoffTotalAmout
	 */
	public BigDecimal getWriteoffTotalAmout() {
		return writeoffTotalAmout;
	}

	/**
	 * @param writeoffTotalAmout
	 */
	public void setWriteoffTotalAmout(BigDecimal writeoffTotalAmout) {
		this.writeoffTotalAmout = writeoffTotalAmout;
	}

	/**
	 * @return onePageSize
	 */
	public int getOnePageSize() {
		return onePageSize;
	}

	/**
	 * @param onePageSize
	 */
	public void setOnePageSize(int onePageSize) {
		this.onePageSize = onePageSize;
	}

	/**
	 * @return empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * @param empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getPayableBillNo() {
		return payableBillNo;
	}

	public void setPayableBillNo(String payableBillNo) {
		this.payableBillNo = payableBillNo;
	}

	public String getAdvancePayBillNo() {
		return advancePayBillNo;
	}

	public void setAdvancePayBillNo(String advancePayBillNo) {
		this.advancePayBillNo = advancePayBillNo;
	}

	public String getDepositNo() {
		return depositNo;
	}

	public void setDepositNo(String depositNo) {
		this.depositNo = depositNo;
	}
}
