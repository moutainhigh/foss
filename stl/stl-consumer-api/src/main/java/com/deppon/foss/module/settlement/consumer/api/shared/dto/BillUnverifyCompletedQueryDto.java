package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.util.define.FossConstants;


/**
 * 未完全核销单据查询参数dto
 * @author foss-qiaolifeng
 * @date 2013-5-14 下午2:04:43
 */
public class BillUnverifyCompletedQueryDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6371822084926278174L;
	
	/**
	 * 有效
	 */
	@SuppressWarnings("unused")
	private static final String BILL_UNVERIFY_ACTIVE_Y = FossConstants.ACTIVE;
	
	/**
	 * 预收单
	 */
	@SuppressWarnings("unused")
	private static final String BILL_UNVERIFY_ACTIVE_US = SettlementConstants.BILL_PREFIX_US;
	
	/**
	 * 应收单
	 */
	@SuppressWarnings("unused")
	private static final String BILL_UNVERIFY_ACTIVE_YS = SettlementConstants.BILL_PREFIX_YS;
	
	/**
	 * 应付单
	 */
	@SuppressWarnings("unused")
	private static final String BILL_UNVERIFY_ACTIVE_YF = SettlementConstants.BILL_PREFIX_YF;
	
	/**
	 * 预付单
	 */
	@SuppressWarnings("unused")
	private static final String BILL_UNVERIFY_ACTIVE_UF = SettlementConstants.BILL_PREFIX_UF;
	
	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 总条数
	 */
	private long billNums;
	
	/**
	 * @get
	 * @return billNums
	 */
	public long getBillNums() {
		/*
		 * @get
		 * @return billNums
		 */
		return billNums;
	}


	
	/**
	 * @set
	 * @param billNums
	 */
	public void setBillNums(long billNums) {
		/*
		 *@set
		 *@this.billNums = billNums
		 */
		this.billNums = billNums;
	}


	/**
	 * @get
	 * @return customerCode
	 */
	public String getCustomerCode() {
		/*
		 * @get
		 * @return customerCode
		 */
		return customerCode;
	}

	
	/**
	 * @set
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		/*
		 *@set
		 *@this.customerCode = customerCode
		 */
		this.customerCode = customerCode;
	}
	
	

}
