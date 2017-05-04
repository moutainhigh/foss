package com.deppon.foss.module.settlement.common.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ptp监控查询结算单据返回结果dto
 * 
 * @author gpz
 * @date 2016年8月6日
 */
public class StlBillQueryResultDto implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -711818687063946458L;

	/**
	 * 预收单总笔数
	 */
	private Long advanceTotalCount;
	
	/**
	 * 预收单总金额
	 */
	private BigDecimal advanceTotalAmount;
	
	/**
	 * 单据明细
	 */
	private List<StlBillDetailDto> receivableBillDetail;
	
	/**
	 * 响应时间
	 */
	private Date resTime;
	
	/**
	 * 是否发生错误
	 */
	private String isError;
	
	/**
	 * 错误消息
	 */
	private String errorMsg;

	/**
	 * @return the advanceTotalCount
	 */
	public Long getAdvanceTotalCount() {
		return advanceTotalCount;
	}

	/**
	 * @param advanceTotalCount the advanceTotalCount to set
	 */
	public void setAdvanceTotalCount(Long advanceTotalCount) {
		this.advanceTotalCount = advanceTotalCount;
	}

	/**
	 * @return the advanceTotalAmount
	 */
	public BigDecimal getAdvanceTotalAmount() {
		return advanceTotalAmount;
	}

	/**
	 * @param advanceTotalAmount the advanceTotalAmount to set
	 */
	public void setAdvanceTotalAmount(BigDecimal advanceTotalAmount) {
		this.advanceTotalAmount = advanceTotalAmount;
	}

	/**
	 * @return the receivableBillDetail
	 */
	public List<StlBillDetailDto> getReceivableBillDetail() {
		return receivableBillDetail;
	}

	/**
	 * @param receivableBillDetail the receivableBillDetail to set
	 */
	public void setReceivableBillDetail(List<StlBillDetailDto> receivableBillDetail) {
		this.receivableBillDetail = receivableBillDetail;
	}

	/**
	 * @return the resTime
	 */
	public Date getResTime() {
		return resTime;
	}

	/**
	 * @param resTime the resTime to set
	 */
	public void setResTime(Date resTime) {
		this.resTime = resTime;
	}

	/**
	 * @return the isError
	 */
	public String getIsError() {
		return isError;
	}

	/**
	 * @param isError the isError to set
	 */
	public void setIsError(String isError) {
		this.isError = isError;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
