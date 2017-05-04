/*
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: PDAComlementDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IPDAComlementDao;
import com.deppon.foss.module.transfer.pda.api.shared.dto.PDAComplementDto;

/**
 * PDAComlementDao
 * @author dp-duyi
 * @date 2013-7-23 下午5:16:39
 */
@SuppressWarnings("unchecked")
public class PDAComlementDao extends iBatis3DaoImpl implements IPDAComlementDao{

	/** 
	 * queryComplement
	 * @author dp-duyi
	 * @date 2013-7-23 下午5:17:19
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IPDAComlementDao#queryComplement(java.util.Date, java.lang.String, boolean)
	 */
	@Override
	public List<PDAComplementDto> queryComplement(Date queryTimeBegin,
			String wayBillNo, boolean beLocal,String orgCode) {
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("queryTimeBegin", queryTimeBegin);
		condition.put("wayBillNo", wayBillNo);
		condition.put("beLocal", beLocal);
		condition.put("orgCode", orgCode);
		return this.getSqlSession().selectList("foss.load.express.complement.queryComplement",condition);
	}
	
	/**
	* @description 根据运单号查询走货路径的最后一个外场部门的部门名称简称
	* @param wayBillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年8月20日 下午2:59:26
	*/
	@Override
	public String queryLastOrgNameByWaybillNo(String wayBillNo) {
		return (String) this.getSqlSession().selectOne("foss.load.express.complement.queryLastOrgNameByWaybillNo",wayBillNo);
	}
	
	
}
