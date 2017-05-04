package com.deppon.foss.module.transfer.dubbo.api.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.deppon.foss.module.transfer.dubbo.api.dao.IMatchTaskOrgDao4dubbo;
import com.deppon.foss.module.transfer.dubbo.api.define.PathDetailEntity;

public class MatchTaskOrgDao4dubbo extends BaseDao implements IMatchTaskOrgDao4dubbo {

	@Override
	public String queryFirstOrgByStockOrgSet(Set<String> stockSet, String waybillNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("stockSet", stockSet);
		return (String) this.getSqlSession().selectOne("queryFirstOrgByStockOrgSet4dubbo", map);
	}

	/**
	 * 根据运单号 查询该运单是否已经全部签收
	 * 
	 * @Author: 200978 xiaobingcheng 2014-10-22
	 * @param waybillNo
	 * @return
	 */
	@Override
	public boolean checkWaybillIsSigned(String waybillNo) {
		return (Boolean) this.getSqlSession().selectOne("checkWaybillIsSigned4dubbo", waybillNo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PathDetailEntity> queryPathDetailList(PathDetailEntity pathDetailEntity) {
		return getSqlSession().selectList("pathDetailQuery4dubbo", pathDetailEntity);
	}

}
