package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;



/**
 * 
 * 查询预收单 Action传入到界面
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:44:23
 */
public class BillAdvanceVo implements Serializable {

	/**
	 * VO类序列号
	 */
	private static final long serialVersionUID = -211398096533537623L;

	

	//预付单Action传入service
	private BillAdvanceDto billAdvanDto;



	
	/**
	 * @return billAdvanDto
	 */
	public BillAdvanceDto getBillAdvanDto() {
		return billAdvanDto;
	}



	
	/**
	 * @param billAdvanDto
	 */
	public void setBillAdvanDto(BillAdvanceDto billAdvanDto) {
		this.billAdvanDto = billAdvanDto;
	}


}
