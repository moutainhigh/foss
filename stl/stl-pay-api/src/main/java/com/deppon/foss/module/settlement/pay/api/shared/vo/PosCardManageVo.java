package com.deppon.foss.module.settlement.pay.api.shared.vo;

import com.deppon.foss.module.settlement.common.api.shared.dto.PosCardManageDto;


/**
 * POS刷卡管理VO
 * @author 269052
 *
 */
public class PosCardManageVo {
	private PosCardManageDto posCardManageDto;

	public PosCardManageDto getPosCardManageDto() {
		return posCardManageDto;
	}

	public void setPosCardManageDto(PosCardManageDto posCardManageDto) {
		this.posCardManageDto = posCardManageDto;
	}
}
