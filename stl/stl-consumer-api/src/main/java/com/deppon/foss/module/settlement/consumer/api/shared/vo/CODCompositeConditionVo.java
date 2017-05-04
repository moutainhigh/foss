package com.deppon.foss.module.settlement.consumer.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODCompositeQueryDto;

/**
 * 代收货款查询Vo
 * 
 * @author 000123-foss-huangxiaobo
 * @String 2012-11-9 下午4:17:05
 */
public class CODCompositeConditionVo extends CODCompositeQueryDto implements
		Serializable {

	private static final long serialVersionUID = -766921013840088065L;

	/**
	 * 起始开单时间
	 */
	private String inceptBizDateStr;

	/**
	 * 结束开单时间
	 */
	private String endBizDateStr;

	/**
	 * 起始签收日期
	 */
	private String inceptSignDateStr;

	/**
	 * 结束签收日期
	 */
	private String endSignDateStr;

	/**
	 * 起始付款日期
	 */
	private String inceptPaymentDateStr;

	/**
	 * 结束签收日期
	 */
	private String endPaymentDateStr;
	
	/**
	 * 汇款成功日期
	 */
	private String inceptRefundSuccessDateStr;

	/**
	 * 汇款成功日期
	 */
	private String endRefundSuccessDateStr;
	
	/**
	 * @return inceptBizDateStr
	 */
	public String getInceptBizDateStr() {
		return inceptBizDateStr;
	}

	/**
	 * @param inceptBizDateStr
	 */
	public void setInceptBizDateStr(String inceptBizDateStr) {
		this.inceptBizDateStr = inceptBizDateStr;
	}

	/**
	 * @return endBizDateStr
	 */
	public String getEndBizDateStr() {
		return endBizDateStr;
	}

	/**
	 * @param endBizDateStr
	 */
	public void setEndBizDateStr(String endBizDateStr) {
		this.endBizDateStr = endBizDateStr;
	}

	/**
	 * @return inceptSignDateStr
	 */
	public String getInceptSignDateStr() {
		return inceptSignDateStr;
	}

	/**
	 * @param inceptSignDateStr
	 */
	public void setInceptSignDateStr(String inceptSignDateStr) {
		this.inceptSignDateStr = inceptSignDateStr;
	}

	/**
	 * @return endSignDateStr
	 */
	public String getEndSignDateStr() {
		return endSignDateStr;
	}

	/**
	 * @param endSignDateStr
	 */
	public void setEndSignDateStr(String endSignDateStr) {
		this.endSignDateStr = endSignDateStr;
	}

	/**
	 * @return inceptPaymentDateStr
	 */
	public String getInceptPaymentDateStr() {
		return inceptPaymentDateStr;
	}

	/**
	 * @param inceptPaymentDateStr
	 */
	public void setInceptPaymentDateStr(String inceptPaymentDateStr) {
		this.inceptPaymentDateStr = inceptPaymentDateStr;
	}

	/**
	 * @return endPaymentDateStr
	 */
	public String getEndPaymentDateStr() {
		return endPaymentDateStr;
	}

	/**
	 * @param endPaymentDateStr
	 */
	public void setEndPaymentDateStr(String endPaymentDateStr) {
		this.endPaymentDateStr = endPaymentDateStr;
	}

	/**
	 * @return the inceptRefundSuccessDateStr
	 */
	public String getInceptRefundSuccessDateStr() {
		return inceptRefundSuccessDateStr;
	}

	/**
	 * @param inceptRefundSuccessDateStr the inceptRefundSuccessDateStr to set
	 */
	public void setInceptRefundSuccessDateStr(String inceptRefundSuccessDateStr) {
		this.inceptRefundSuccessDateStr = inceptRefundSuccessDateStr;
	}

	/**
	 * @return the endRefundSuccessDateStr
	 */
	public String getEndRefundSuccessDateStr() {
		return endRefundSuccessDateStr;
	}

	/**
	 * @param endRefundSuccessDateStr the endRefundSuccessDateStr to set
	 */
	public void setEndRefundSuccessDateStr(String endRefundSuccessDateStr) {
		this.endRefundSuccessDateStr = endRefundSuccessDateStr;
	}
	
}
