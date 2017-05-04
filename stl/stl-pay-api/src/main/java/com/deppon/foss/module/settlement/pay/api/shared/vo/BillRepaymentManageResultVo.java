package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillRepaymentManageResultDto;

/**
 * 
 * 查询 审核、作废、还款单用于 Action返回结果到界面
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:44:23
 */
public class BillRepaymentManageResultVo implements Serializable {

	/**
	 * VO类序列号
	 */
	private static final long serialVersionUID = 5638944806774140588L;

	// 还款单返回Dto
	private BillRepaymentManageResultDto billRepaymentManageResultDto;

	
	/**
	 * @return billRepaymentManageResultDto
	 */
	public BillRepaymentManageResultDto getBillRepaymentManageResultDto() {
		return billRepaymentManageResultDto;
	}

	
	/**
	 * @param billRepaymentManageResultDto
	 */
	public void setBillRepaymentManageResultDto(
			BillRepaymentManageResultDto billRepaymentManageResultDto) {
		this.billRepaymentManageResultDto = billRepaymentManageResultDto;
	}

	

}
