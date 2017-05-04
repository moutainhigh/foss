package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDResultDto;



/**
 * 现金收入缴款报表返回Vo
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午5:00:57
 */
public class BillCashRecPayInDResultVo implements Serializable {
	
	/**
	 * 现金收入缴款报表明细返回界面Vo
	 */
	private static final long serialVersionUID = -1109118991276969195L;
	/**
	 * 现金收入报表返回界面resultDto
	 */
	private BillCashRecPayInDResultDto billCashRecPayInDResultDto;
	
	/**
	 * @return billCashRecPayInDResultDto
	 */
	public BillCashRecPayInDResultDto getBillCashRecPayInDResultDto() {
		return billCashRecPayInDResultDto;
	}
	
	/**
	 * @param billCashRecPayInDResultDto
	 */
	public void setBillCashRecPayInDResultDto(
			BillCashRecPayInDResultDto billCashRecPayInDResultDto) {
		this.billCashRecPayInDResultDto = billCashRecPayInDResultDto;
	}
	
	
	
}
