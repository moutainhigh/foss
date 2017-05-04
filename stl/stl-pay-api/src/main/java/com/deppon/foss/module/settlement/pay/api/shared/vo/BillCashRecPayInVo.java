package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDto;

/**
 * 现金收入缴款服务VO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午4:57:12
 */

public class BillCashRecPayInVo implements Serializable {

	/**
	 * 现金收入缴款vo 序列化
	 */
	private static final long serialVersionUID = -3483316295121344373L;

	/**
	 * 现金收入缴款报表dto
	 */
	private BillCashRecPayInDto billCashRecPayInDto;

	/**
	 * @return billCashRecPayInDto
	 */
	public BillCashRecPayInDto getBillCashRecPayInDto() {
		return billCashRecPayInDto;
	}

	/**
	 * @param billCashRecPayInDto
	 */
	public void setBillCashRecPayInDto(BillCashRecPayInDto billCashRecPayInDto) {
		this.billCashRecPayInDto = billCashRecPayInDto;
	}
}
