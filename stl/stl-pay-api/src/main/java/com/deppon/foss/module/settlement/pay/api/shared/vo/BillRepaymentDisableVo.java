package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableDto;

/**
 *申请作废VO
 *
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-19 下午6:47:35
 */
public class BillRepaymentDisableVo implements Serializable{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5814788954817448175L;
	private BillRepaymentDisableDto billRepaymentDisableDto;

	/**
	 * @return the billRepaymentDisableDto
	 */
	public BillRepaymentDisableDto getBillRepaymentDisableDto() {
		return billRepaymentDisableDto;
	}

	/**
	 * @param billRepaymentDisableDto the billRepaymentDisableDto to set
	 */
	public void setBillRepaymentDisableDto(
			BillRepaymentDisableDto billRepaymentDisableDto) {
		this.billRepaymentDisableDto = billRepaymentDisableDto;
	}
	
}
