/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.vo
 * FILE    NAME: ModifyBillWriteoffVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffDto;

/**
 * 
 * 查询-核销-反核销更改单界面传入用于 Action
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:44:23
 */
public class ModifyBillWriteoffVo implements Serializable {

	/**
	 * VO类序列号
	 */
	private static final long serialVersionUID = 7379912636078020208L;

	/**
	 * 更改单service层和dao dto
	 */
	BillWriteoffDto modifyBillWriteOffDto;

	/**
	 * @return modifyBillWriteOffDto
	 */
	public BillWriteoffDto getModifyBillWriteOffDto() {
		return modifyBillWriteOffDto;
	}

	/**
	 * @param modifyBillWriteOffDto
	 */
	public void setModifyBillWriteOffDto(BillWriteoffDto modifyBillWriteOffDto) {
		this.modifyBillWriteOffDto = modifyBillWriteOffDto;
	}

}
