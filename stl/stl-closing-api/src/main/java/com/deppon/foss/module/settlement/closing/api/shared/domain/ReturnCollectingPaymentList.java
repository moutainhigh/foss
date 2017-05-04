package com.deppon.foss.module.settlement.closing.api.shared.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author 218392 zhangyongxue
 * @date 2015-09-07 08:54:40
 * CRM请求查询过来，不管成功失败返回的信息
 */
 //@XmlRootElement(name = "responseMsg")
public class ReturnCollectingPaymentList implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 是否成功
	 */
	private boolean ifSuccess;
	
	/**
	 * 错误信息
	 */
	private String errorMsg;
	
	/**
	 * 代收货款清单查询结果list
	 */
	private List<ReturnCollectingPaymentEntity> returnCollectingPaymentEntity;
	
	
	/**
	 * @return  费用统计合计 ，对查询返回的结果List集合部分费用汇总
	 */
	
	/**
	 * 代收金额总额---(代收货款金额的总和)
	 */
	private double codAmountTotal;
	
	/**
	 * 手续费总额
	 */
	private double codFeeTotal;
	
	/**
	 * 应付款总额--(ReturnCollectingPaymentEntity实体中应付金额总额)
	 */
	private double payableAmountTotal;
	
	/**
	 * 已付金额合计---(当前查询条件，状态为'已支付'运单，代收货款金额总和)
	 */
	private double payCodAmountTotal;
	
	/**
	 * 总条数（供CRM分页使用）
	 * @return
	 */
	private Long totalItems;
	
	

	public boolean isIfSuccess() {
		return ifSuccess;
	}

	public void setIfSuccess(boolean ifSuccess) {
		this.ifSuccess = ifSuccess;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<ReturnCollectingPaymentEntity> getReturnCollectingPaymentEntity() {
		return returnCollectingPaymentEntity;
	}

	public void setReturnCollectingPaymentEntity(
			List<ReturnCollectingPaymentEntity> returnCollectingPaymentEntity) {
		this.returnCollectingPaymentEntity = returnCollectingPaymentEntity;
	}

	public double getCodAmountTotal() {
		return codAmountTotal;
	}

	public void setCodAmountTotal(double codAmountTotal) {
		this.codAmountTotal = codAmountTotal;
	}

	public double getCodFeeTotal() {
		return codFeeTotal;
	}

	public void setCodFeeTotal(double codFeeTotal) {
		this.codFeeTotal = codFeeTotal;
	}

	public double getPayableAmountTotal() {
		return payableAmountTotal;
	}
 
	public void setPayableAmountTotal(double payableAmountTotal) {
		this.payableAmountTotal = payableAmountTotal;
	}

	public double getPayCodAmountTotal() {
		return payCodAmountTotal;
	}

	public void setPayCodAmountTotal(double payCodAmountTotal) {
		this.payCodAmountTotal = payCodAmountTotal;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	
}
