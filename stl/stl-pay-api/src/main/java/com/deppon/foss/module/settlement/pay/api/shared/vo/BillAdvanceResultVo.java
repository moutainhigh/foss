package com.deppon.foss.module.settlement.pay.api.shared.vo;

import java.io.Serializable;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;

/**
 * 
 *预付单管理 Action 返回结果到界面
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:44:23
 */
public class BillAdvanceResultVo implements Serializable {

	/**
	 * 预付单VO类序列号
	 */
	private static final long serialVersionUID = -6812654084726665940L;


	// 预付单返回Dto
	private BillAdvanceResultDto billAdvanResultDto;


	
	/**
	 * @return billAdvanResultDto
	 */
	public BillAdvanceResultDto getBillAdvanResultDto() {
		return billAdvanResultDto;
	}


	
	/**
	 * @param billAdvanResultDto
	 */
	public void setBillAdvanResultDto(BillAdvanceResultDto billAdvanResultDto) {
		this.billAdvanResultDto = billAdvanResultDto;
	}


	
	

}
