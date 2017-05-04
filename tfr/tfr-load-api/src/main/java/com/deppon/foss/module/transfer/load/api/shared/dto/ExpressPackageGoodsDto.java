/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.shared.dto
 * FILE    NAME: ExpressPackageGoodsDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.shared.dto;

import com.deppon.foss.module.transfer.pda.api.shared.dto.ExpressPackageDetailDto;

/**
 * @author dp-duyi
 * @date 2013-7-22 下午4:34:33
 */
public class ExpressPackageGoodsDto extends ExpressPackageDetailDto{
	private static final long serialVersionUID = 1041026112315691104L;
	private String seiralNo;
	public String getSeiralNo() {
		return seiralNo;
	}
	public void setSeiralNo(String seiralNo) {
		this.seiralNo = seiralNo;
	}
}
