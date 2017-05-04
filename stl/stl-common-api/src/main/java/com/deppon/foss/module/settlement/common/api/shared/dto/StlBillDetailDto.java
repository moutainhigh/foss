package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * ptp监控查询预收单总记录数和总金额
 * 
 * @author gpz
 * @date 2016年8月5日
 */
public class StlBillDetailDto implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8611563556784771415L;

	/**
	 * 总记录数
	 */
	private Long flowTotalNum;
	
	/**
	 * 总金额
	 */
	private BigDecimal flowTotalAmount;
	
	
	/**
	 * 单据类型
	 */
	private String flowType;


	public Long getFlowTotalNum() {
		return flowTotalNum;
	}


	public void setFlowTotalNum(Long flowTotalNum) {
		this.flowTotalNum = flowTotalNum;
	}


	public BigDecimal getFlowTotalAmount() {
		return flowTotalAmount;
	}


	public void setFlowTotalAmount(BigDecimal flowTotalAmount) {
		this.flowTotalAmount = flowTotalAmount;
	}


	public String getFlowType() {
		return flowType;
	}


	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
}
