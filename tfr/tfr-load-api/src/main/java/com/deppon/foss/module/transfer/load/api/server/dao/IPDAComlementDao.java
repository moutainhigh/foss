/*
 * PROJECT NAME: tfr-load-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.api.server.dao
 * FILE    NAME: IPDAComlementDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;

/**
 * IPDAComlementDao
 * @author dp-duyi
 * @date 2013-7-23 下午5:13:37
 */
public interface IPDAComlementDao {
	public List<PDAComplementDto> queryComplement(Date queryTimeBegin,
			String wayBillNo, boolean beLocal,String orgCode);
	
	/**
	* @description 根据运单号查询走货路径的最后一个外场部门的部门名称简称
	* @param wayBillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年8月20日 下午2:59:26
	*/
	public String queryLastOrgNameByWaybillNo(String wayBillNo);
}
