package com.deppon.foss.module.settlement.pay.api.shared.dto;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询应付单Action
 * 
 * @author 045738-foss-maojianqiang
 * @date 2012-11-16 下午4:24:03
 */
public class BillPayableManageDto extends BillPayableEntity {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 6250425463486513629L;
	
	/**
	 * 应付单的核销状态 --未核销
	 */
	private static final String WRITEOFF_NOT = SettlementConstants.BILL_PAYABLE__WRITEOFF_STATUS__NOT_WRITEOFF;

	/**
	 * 应付单的核销状态 --部分核销
	 */
	private static final String WRITEOFF_PART = SettlementConstants.BILL_PAYABLE__WRITEOFF_STATUS__PART_WRITEOFF;

	/**
	 * 应付单的核销状态 --已核销
	 */
	private static final String WRITEOFF_DONE = SettlementConstants.BILL_PAYABLE__WRITEOFF_STATUS__WRITEOFF_DONE;
	
	/**
	 * 是否签收
	 */
	private static final String BILL_PAYABLE_IS_SIGN_NO = FossConstants.NO;

	/**
	 * 客户名称@author 340403
	 */
	private String truckCustomerName;
	/**
	 * 客户编码@author 340403
	 */
	private String truckCustomerCode;
	
	/**
	 * 核销状态
	 */
	private String writeoffStatus;

	/**
	 * 业务开始日期
	 */
	private Date businessBeginDate;

	/**
	 * 业务结束日期
	 */
	private Date businessEndDate;

	/**
	 * 大区
	 */
	private String largeRegion;

	/**
	 * 小区
	 */
	private String smallRegion;

	/**
	 * 按单号制作查询条件
	 */
	private String[] billNos;
	
	/**
	 * 应付单号列表
	 */
	private List<String> payableBillNosList;

	/**
	 * 运单号了列表
	 */
	private List<String> sourceBillNosList;

	/**
	 * 可查询部门列表
	 */
	private List<String> orgCodeList;

	/**
	 * 查询tab
	 */
	private String queryTab;

	/**
	 *自定义导出列头 
	 */
	private String[] arrayColumns;
	
	/**
	 *自定义导出列中文名称
	 */
	private String[] arrayColumnNames;
	
	/**
	 * 当前登录人编码
	 */
	private String empCode;
	
	
	/**
	 * 付款单号
	 */
	String paymentNo;
	
	/**
	 * 付款单明细之来源单据类型
	 */
	String sourceBillTypeFkd;
		
	/**
	 * 是否红单
	 */
	String isRedBack;
	
	/**
	 * 是否初始化
	 */
	String isInit;
	
	/**
	 *  记账开始日期
	 */
	private Date accountBeginDate;

	/**
	 * 记账结束日期
	 */
	private Date accountEndDate;
	
	/**
	 * 查询日期类型
	 */
	private String queryDateFlag;
	
	/**
	 * 是否签收
	 */
	String isSign;

	/**
	 * 产品类型
	 */
	private List<String> productCodesList;

	

	/**
	 * 单据子类型
	 */
	private List<String> payableBillType;
	
	/**
	 * 是否包含临时租车数据
	 */
	private String containRentCar;
	
	/**
	 * 预提费用承担部门 
	 */
	private String witholdingCostDept;
	
	private String isPartner;
	
	/**
	 * 查询客户编码列表---此处为临时租车按多个司机查询做的 
	 */
	private String[] customerCodeList;
	public List<String> getPayableBillType() {
		return payableBillType;
	}

	public void setPayableBillType(List<String> payableBillType) {
		this.payableBillType = payableBillType;
	}

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}


	
	public String getTruckCustomerName() {
		return truckCustomerName;
	}

	public void setTruckCustomerName(String truckCustomerName) {
		this.truckCustomerName = truckCustomerName;
	}

	public String getTruckCustomerCode() {
		return truckCustomerCode;
	}

	public void setTruckCustomerCode(String truckCustomerCode) {
		this.truckCustomerCode = truckCustomerCode;
	}

	/**
	 * @get
	 * @return queryDateFlag
	 */
	public String getQueryDateFlag() {
		/*
		 * @get
		 * @return queryDateFlag
		 */
		return queryDateFlag;
	}



	
	/**
	 * @set
	 * @param queryDateFlag
	 */
	public void setQueryDateFlag(String queryDateFlag) {
		/*
		 *@set
		 *@this.queryDateFlag = queryDateFlag
		 */
		this.queryDateFlag = queryDateFlag;
	}



	/**
	 * @get
	 * @return accountBeginDate
	 */
	public Date getAccountBeginDate() {
		/*
		 * @get
		 * @return accountBeginDate
		 */
		return accountBeginDate;
	}


	
	/**
	 * @set
	 * @param accountBeginDate
	 */
	public void setAccountBeginDate(Date accountBeginDate) {
		/*
		 *@set
		 *@this.accountBeginDate = accountBeginDate
		 */
		this.accountBeginDate = accountBeginDate;
	}


	
	/**
	 * @get
	 * @return accountEndDate
	 */
	public Date getAccountEndDate() {
		/*
		 * @get
		 * @return accountEndDate
		 */
		return accountEndDate;
	}


	
	/**
	 * @set
	 * @param accountEndDate
	 */
	public void setAccountEndDate(Date accountEndDate) {
		/*
		 *@set
		 *@this.accountEndDate = accountEndDate
		 */
		this.accountEndDate = accountEndDate;
	}


	/**
	 * @return writeoffStatus
	 */
	public String getWriteoffStatus() {
		return writeoffStatus;
	}

	
	/**
	 * @param writeoffStatus
	 */
	public void setWriteoffStatus(String writeoffStatus) {
		this.writeoffStatus = writeoffStatus;
	}

	
	/**
	 * @return businessBeginDate
	 */
	public Date getBusinessBeginDate() {
		return businessBeginDate;
	}

	
	/**
	 * @param businessBeginDate
	 */
	public void setBusinessBeginDate(Date businessBeginDate) {
		this.businessBeginDate = businessBeginDate;
	}

	
	/**
	 * @return businessEndDate
	 */
	public Date getBusinessEndDate() {
		return businessEndDate;
	}

	
	/**
	 * @param businessEndDate
	 */
	public void setBusinessEndDate(Date businessEndDate) {
		this.businessEndDate = businessEndDate;
	}

	
	/**
	 * @return largeRegion
	 */
	public String getLargeRegion() {
		return largeRegion;
	}

	
	/**
	 * @param largeRegion
	 */
	public void setLargeRegion(String largeRegion) {
		this.largeRegion = largeRegion;
	}

	
	/**
	 * @return smallRegion
	 */
	public String getSmallRegion() {
		return smallRegion;
	}

	
	/**
	 * @param smallRegion
	 */
	public void setSmallRegion(String smallRegion) {
		this.smallRegion = smallRegion;
	}

	
	/**
	 * @return billNos
	 */
	public String[] getBillNos() {
		return billNos;
	}

	
	/**
	 * @param billNos
	 */
	public void setBillNos(String[] billNos) {
		this.billNos = billNos;
	}

	
	/**
	 * @return payableBillNosList
	 */
	public List<String> getPayableBillNosList() {
		return payableBillNosList;
	}

	
	/**
	 * @param payableBillNosList
	 */
	public void setPayableBillNosList(List<String> payableBillNosList) {
		this.payableBillNosList = payableBillNosList;
	}

	
	/**
	 * @return sourceBillNosList
	 */
	public List<String> getSourceBillNosList() {
		return sourceBillNosList;
	}

	
	/**
	 * @param sourceBillNosList
	 */
	public void setSourceBillNosList(List<String> sourceBillNosList) {
		this.sourceBillNosList = sourceBillNosList;
	}

	
	/**
	 * @return orgCodeList
	 */
	public List<String> getOrgCodeList() {
		return orgCodeList;
	}

	
	/**
	 * @param orgCodeList
	 */
	public void setOrgCodeList(List<String> orgCodeList) {
		this.orgCodeList = orgCodeList;
	}

	
	/**
	 * @return queryTab
	 */
	public String getQueryTab() {
		return queryTab;
	}

	
	/**
	 * @param queryTab
	 */
	public void setQueryTab(String queryTab) {
		this.queryTab = queryTab;
	}

	
	/**
	 * @return arrayColumns
	 */
	public String[] getArrayColumns() {
		return arrayColumns;
	}

	
	/**
	 * @param arrayColumns
	 */
	public void setArrayColumns(String[] arrayColumns) {
		this.arrayColumns = arrayColumns;
	}

	
	/**
	 * @return arrayColumnNames
	 */
	public String[] getArrayColumnNames() {
		return arrayColumnNames;
	}

	
	/**
	 * @param arrayColumnNames
	 */
	public void setArrayColumnNames(String[] arrayColumnNames) {
		this.arrayColumnNames = arrayColumnNames;
	}


	public String getEmpCode() {
		return empCode;
	}


	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * @get
	 * @return paymentNo
	 */
	public String getPaymentNo() {
		/*
		 * @get
		 * 
		 * @return paymentNo
		 */
		return paymentNo;
	}

	/**
	 * @set
	 * @param paymentNo
	 */
	public void setPaymentNo(String paymentNo) {
		/*
		 * @set
		 * 
		 * @this.paymentNo = paymentNo
		 */
		this.paymentNo = paymentNo;
	}

	/**
	 * @get
	 * @return sourceBillTypeFkd
	 */
	public String getSourceBillTypeFkd() {
		/*
		 * @get
		 * 
		 * @return sourceBillTypeFkd
		 */
		return sourceBillTypeFkd;
	}

	/**
	 * @set
	 * @param sourceBillTypeFkd
	 */
	public void setSourceBillTypeFkd(String sourceBillTypeFkd) {
		/*
		 * @set
		 * 
		 * @this.sourceBillTypeFkd = sourceBillTypeFkd
		 */
		this.sourceBillTypeFkd = sourceBillTypeFkd;
	}

	/**
	 * @get
	 * @return isRedBack
	 */
	public String getIsRedBack() {
		/*
		 * @get
		 * 
		 * @return isRedBack
		 */
		return isRedBack;
	}

	/**
	 * @set
	 * @param isRedBack
	 */
	public void setIsRedBack(String isRedBack) {
		/*
		 * @set
		 * 
		 * @this.isRedBack = isRedBack
		 */
		this.isRedBack = isRedBack;
	}


	/**
	 * @GET
	 * @return isInit
	 */
	public String getIsInit() {
		/*
		 *@get
		 *@ return isInit
		 */
		return isInit;
	}


	/**
	 * @SET
	 * @param isInit
	 */
	public void setIsInit(String isInit) {
		/*
		 *@set
		 *@this.isInit = isInit
		 */
		this.isInit = isInit;
	}


	/**
	 * @GET
	 * @return productCodesList
	 */
	public List<String> getProductCodesList() {
		/*
		 *@get
		 *@ return productCodesList
		 */
		return productCodesList;
	}

	public String getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}

	/**
	 * @SET
	 * @param productCodesList
	 */
	public void setProductCodesList(List<String> productCodesList) {
		/*
		 *@set
		 *@this.productCodesList = productCodesList
		 */
		this.productCodesList = productCodesList;
	}

	public String getContainRentCar() {
		return containRentCar;
	}

	public void setContainRentCar(String containRentCar) {
		this.containRentCar = containRentCar;
	}

	public String getWitholdingCostDept() {
		return witholdingCostDept;
	}

	public void setWitholdingCostDept(String witholdingCostDept) {
		this.witholdingCostDept = witholdingCostDept;
	}

	public String[] getCustomerCodeList() {
		return customerCodeList;
	}

	public void setCustomerCodeList(String[] customerCodeList) {
		this.customerCodeList = customerCodeList;
	}
}
