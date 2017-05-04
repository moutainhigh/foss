package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IWaybillEntityForEcsDao;
import com.deppon.foss.util.define.FossConstants;

public class WaybillEntityForEcsDao extends iBatis3DaoImpl implements IWaybillEntityForEcsDao{
	
	private static final String NAMESPACE = "foss.stl.WaybillEntityForEcsMapper.";

	/**
	 * 通过运单号查询运单表判断是否是悟空运单
	 * 
	 * @author 325369
	 * @date 2016-09-06
	 * @param waybillNo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer queryWaybillIsEcsByWaybillNo(String waybillNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("waybillNo", waybillNo);
		params.put("active", FossConstants.YES);
		params.put("isEcs", FossConstants.YES);
		return (Integer) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillIsEcsByWaybillNo", params);
	}

}
