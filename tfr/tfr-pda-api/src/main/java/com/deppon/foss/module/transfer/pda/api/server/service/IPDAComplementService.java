/*
 * PROJECT NAME: tfr-pda-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.pda.api.server.service
 * FILE    NAME: IPDAComplementService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SortingScanDto;

/**
 * PDA补码接口
 * @author dp-duyi
 * @date 2013-7-23 下午4:27:47
 */
public interface IPDAComplementService {
	/**查询补码结果*/
	List<PDAComplementDto> queryComplement(Date queryTimeBegin,String wayBillNo,boolean beLocal,String orgCode);
	
	
	/**
	* @description 查询补码结果+上分拣扫描
	* @param queryTimeBegin
	* @param wayBillNo
	* @param beLocal
	* @param orgCode
	* @param isPackage
	* @param record
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年8月20日 上午10:11:47
	*/
	Map<String,Object> queryComplement(Date queryTimeBegin,String wayBillNo,boolean beLocal,String orgCode,String isPackage,SortingScanDto record);
}
