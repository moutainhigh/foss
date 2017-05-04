package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillCashRecPayInDDto;

/**
 * 现金收入缴款服务VO
 * 
 * @author 095793-foss-LiQin
 * @date 2012-12-3 下午4:57:12
 */

public class BillCashRecPayInDVo implements Serializable {
	/**
	 * 现金收入缴款缴款明细vo 序列化
	 */
	private static final long serialVersionUID = -512583166974365956L;
	
	/**
	 * 现金收入缴款报表dto
	 */
	private BillCashRecPayInDDto billCashRecPayInDDto;

	
	/**
	 * @return billCashRecPayInDDto
	 */
	public BillCashRecPayInDDto getBillCashRecPayInDDto() {
		return billCashRecPayInDDto;
	}

	
	/**
	 * @param billCashRecPayInDDto
	 */
	public void setBillCashRecPayInDDto(BillCashRecPayInDDto billCashRecPayInDDto) {
		this.billCashRecPayInDDto = billCashRecPayInDDto;
	}

	
	
}
