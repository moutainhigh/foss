/**
 * Copyright 2013 STL TEAM
 */
/*
 * PROJECT NAME: stl-writeoff-api
 * PACKAGE NAME: com.deppon.foss.module.settlement.writeoff.api.shared.vo
 * FILE    NAME: ModifyBillWriteoffResultVo.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.settlement.writeoff.api.shared.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillWriteoffResultDto;

/**
 * 查询—核销-反核销更改单返回结果到界面
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-26 上午8:41:23
 */
public class ModifyBillWriteoffResultVo implements Serializable {

	/**
	 * VO类 序列号
	 */
	private static final long serialVersionUID = 7192805696234480275L;

	/**
	 * 核销反核销更改单
	 */
	private BillWriteoffResultDto modifyBillWriteoffResultDto;

	/**
	 * 查询财务类运单更改的清单
	 */
	private List<BillWriteoffResultDto> waybillRfcList = new ArrayList<BillWriteoffResultDto>();

	
	/**
	 * @return 
		modifyBillWriteoffResultDto
	 */
	public BillWriteoffResultDto getModifyBillWriteoffResultDto() {
		return modifyBillWriteoffResultDto;
	}

	
	/**
	 * @param 
		modifyBillWriteoffResultDto
	 */
	public void setModifyBillWriteoffResultDto(BillWriteoffResultDto modifyBillWriteoffResultDto) {
		this.modifyBillWriteoffResultDto = modifyBillWriteoffResultDto;
	}

	
	/**
	 * @return 
		waybillRfcList
	 */
	public List<BillWriteoffResultDto> getWaybillRfcList() {
		return waybillRfcList;
	}

	
	/**
	 * @param 
		waybillRfcList
	 */
	public void setWaybillRfcList(List<BillWriteoffResultDto> waybillRfcList) {
		this.waybillRfcList = waybillRfcList;
	}

	

}
