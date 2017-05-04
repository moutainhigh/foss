package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.IFirstTransferCenterHandoverTimeDao;

public class FirstTransferCenterHandoverTimeDao extends iBatis3DaoImpl implements IFirstTransferCenterHandoverTimeDao {
	private static final String nameSpace="foss.tfr.handovertime.";

	@Override
	public Date getFirstTransferCenterHandoverTime(String wayBillNo) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("wayBillNo",wayBillNo);
		return (Date) this.getSqlSession().selectOne(nameSpace + "firstTransferCenterHandoverTime", map);
		
	}
}
