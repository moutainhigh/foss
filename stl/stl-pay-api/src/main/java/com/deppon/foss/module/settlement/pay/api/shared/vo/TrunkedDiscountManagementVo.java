package com.deppon.foss.module.settlement.pay.api.shared.vo;

import com.deppon.foss.module.settlement.pay.api.shared.dto.TrunkedDiscountManagementDto;

/**
 * 零担事后折折扣管理vo
 * @ClassName: TrunkedDiscountManagementVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author &269052 |zhouyuan008@deppon.com
 * @date 2015-9-22 上午10:50:26
 * 
 */
public class TrunkedDiscountManagementVo {
	/**
	 * 零担事后折折扣dto
	 */
	private TrunkedDiscountManagementDto trunkedDiscountManagementDto;

	public TrunkedDiscountManagementDto getTrunkedDiscountManagementDto() {
		return trunkedDiscountManagementDto;
	}

	public void setTrunkedDiscountManagementDto(
			TrunkedDiscountManagementDto trunkedDiscountManagementDto) {
		this.trunkedDiscountManagementDto = trunkedDiscountManagementDto;
	}

}
