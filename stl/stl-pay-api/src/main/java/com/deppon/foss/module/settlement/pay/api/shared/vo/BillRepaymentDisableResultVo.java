package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentDisableResultDto;

/**
 * 
 *申请作废dto
 *
 * @author 092036-foss-bochenlong
 * @date 2013-11-20 下午2:35:34
 */
public class BillRepaymentDisableResultVo implements Serializable{

	private static final long serialVersionUID = 2877155765257730711L;
	
	private BillRepaymentDisableResultDto billRepaymentDisableResultDto;

	/**
	 * @return the billRepaymentDisableResultDto
	 */
	public BillRepaymentDisableResultDto getBillRepaymentDisableResultDto() {
		return billRepaymentDisableResultDto;
	}

	/**
	 * @param billRepaymentDisableResultDto the billRepaymentDisableResultDto to set
	 */
	public void setBillRepaymentDisableResultDto(
			BillRepaymentDisableResultDto billRepaymentDisableResultDto) {
		this.billRepaymentDisableResultDto = billRepaymentDisableResultDto;
	}
	
}
