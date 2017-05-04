/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.vo
 * FILE    NAME: BillRecWriteoffBillPayVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillRecWriteoffBillPayDto;

/**
 * 应收冲应付界面输入
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:59:20
 */
public class BillRecWriteoffBillPayVo implements Serializable {

	/**
	 * 应收单参数类序列号
	 */
	private static final long serialVersionUID = 6944428115413754846L;

	/**
	 * 应收冲应付单Dto
	 */
	private BillRecWriteoffBillPayDto billRecWriteoffBillPayDto;

	/**
	 * @return billRecWriteoffBillPayDto
	 */
	public BillRecWriteoffBillPayDto getBillRecWriteoffBillPayDto() {
		return billRecWriteoffBillPayDto;
	}

	/**
	 * @param billRecWriteoffBillPayDto
	 */
	public void setBillRecWriteoffBillPayDto(
			BillRecWriteoffBillPayDto billRecWriteoffBillPayDto) {
		this.billRecWriteoffBillPayDto = billRecWriteoffBillPayDto;
	}

}
