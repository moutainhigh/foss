package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.AdjustEntity;

public class AdjustExpressPathDao extends iBatis3DaoImpl implements IAdjustExpressPathDao {

	private static final String NAMESPACE = "Foss.adjustExpress.";

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao#queryExpressByParamMap(java.util.Map, int, int)
	 * @desc 查询快递修改的走货路径集合
	 * @wqh
	 * @date 2014-12-08
	 */

	public List<AdjustEntity> queryExpressByParamMap(Map<String, Object> paramMap, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		@SuppressWarnings("unchecked")
		List<AdjustEntity> adjustList = this.getSqlSession().selectList(NAMESPACE + "queryExpressByParamMap", paramMap, rowBounds);
		return adjustList;
	}

	

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao#queryExpressByParamMap(java.util.Map, int, int)
	 * @desc 查询快递修改的走货路径集合
	 * @wqh
	 * @date 2014-12-08
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AdjustEntity> queryExpressWaybillListByParamMap(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressWaybillListByParamMap", paramMap);

	}

	//查询总条数
	public long getCount(Map<String, Object> paramMap)
	{
		@SuppressWarnings("unchecked")
		List<AdjustEntity> adjustList = this.getSqlSession().selectList(NAMESPACE + "queryExpressByParamMap", paramMap);
        return adjustList.size();
		
	}
	
	
	 /* (non-Javadoc)
	 * @see com.deppon.foss.module.transfer.scheduling.api.server.dao.IAdjustExpressPathDao#queryExpressByParamMap(java.util.Map, int, int)
	 * @desc 查询快递运单
	 * @wqh
	 * @date 2014-12-08
	 */
	@SuppressWarnings("unchecked")
	public List<AdjustEntity> queryExpressWaybillList(Map<String, Object> paramMap)
	{
		return this.getSqlSession().selectList(NAMESPACE + "queryExpressWaybillListByParamMap", paramMap);
	}
	
}
