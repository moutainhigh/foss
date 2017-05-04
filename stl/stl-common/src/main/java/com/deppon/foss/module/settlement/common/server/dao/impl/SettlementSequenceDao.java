package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.ISettlementSequenceDao;

/**
 * 查询结算序列号DAO
 * @author ibm-zhuwei
 * @date 2012-10-15 上午11:02:45
 */
public class SettlementSequenceDao extends iBatis3DaoImpl implements ISettlementSequenceDao {

	private static final String NAMESPACE = "foss.stl.StlSequenceDao.";
	
	/** 
	 * 获取下一序列号
	 * @author ibm-zhuwei
	 * @date 2012-12-24 下午8:38:09
	 * @param sequenceName
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.dao.ISettlementSequenceDao#getNextVal(java.lang.String)
	 */
	@Override
	public long getNextVal(String sequenceName) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("sequenceName", sequenceName);
		
		// sqlSqlSession未关闭之前，如果对于同样条件进行重复查询，采用的是local session cache
		// 参见 http://blog.csdn.net/x43816138/article/details/7868990
		// 用mybatis 3.1 以后可以使用 <setting name="localCacheScope" value="STATEMENT"/> 来禁止缓存
		// 参见http://code.google.com/p/mybatis/issues/detail?id=482
		this.getSqlSession().clearCache();
		
		return  (Long)this.getSqlSession().selectOne(
				NAMESPACE + "selectNextVal", map);
	}

}
