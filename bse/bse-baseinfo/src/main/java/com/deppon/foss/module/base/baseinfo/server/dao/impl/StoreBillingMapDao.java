package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IStoreBillingMapDao;

public class StoreBillingMapDao extends SqlSessionDaoSupport implements IStoreBillingMapDao{
	private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".storeBillingMap.";
	/**
	 * 通过仓管组 查询对应开单营业部
	 * @author wusuhua
	 * @date   2015-06-1 2:31PM
	 *
	 */
	@Override
	public String queryBillingByStore(String storeCode) {
		if(null==storeCode){
			return null;
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("storeCode", storeCode);
		return (String)getSqlSession().selectOne(NAMESPACE + "queryBillingByStore", map);
	}
}
