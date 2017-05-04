package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInResultDto;


/**
 * 现金收入缴款报表返回Vo
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午5:00:57
 */
public class BillCashRecPayInResultVo implements Serializable {
	
	/**
	 * 现金收入报表返回界面Vo
	 */
	private static final long serialVersionUID = 8741938982047124234L;
	
	
	/**
	 * 现金收入报表返回界面resultDto
	 */
	private BillCashRecPayInResultDto billCashRecPayInResultDto;


	
	/**
	 * @return billCashRecPayInResultDto
	 */
	public BillCashRecPayInResultDto getBillCashRecPayInResultDto() {
		return billCashRecPayInResultDto;
	}


	
	/**
	 * @param billCashRecPayInResultDto
	 */
	public void setBillCashRecPayInResultDto(
			BillCashRecPayInResultDto billCashRecPayInResultDto) {
		this.billCashRecPayInResultDto = billCashRecPayInResultDto;
	}
	

}
