package com.deppon.foss.module.settlement.dubbo.api.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.module.settlement.dubbo.api.dao.ISettlementSequenceDao4dubbo;


/**
 * 查询结算序列号DAO
 * 
 * @author ibm-zhuwei
 * @date 2012-10-15 上午11:02:45
 */
public class SettlementSequenceDao4dubbo extends BaseDao implements ISettlementSequenceDao4dubbo {

//	private static final String NAMESPACE = "foss.stl.StlSequenceDao.";

	/**
	 * 获取下一序列号
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:38:09
	 * @param sequenceName
	 * @return
	 * @see com.deppon.foss.module.settlement.ISettlementSequenceDao4dubbo.api.server.dao.ISettlementSequenceDao#getNextVal(java.lang.String)
	 */
	@Override
	public long getNextVal(String sequenceName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sequenceName", sequenceName);
		this.getSqlSession().clearCache();
		return (Long) this.getSqlSession().selectOne("selectNextVal_4_dubbo", map);
	}

}
