/**
 * 
 */
package com.deppon.foss.module.pickup.order.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IOrderThreadPoollogDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity;

/**
 * @author 073615
 *
 */
public class OrderThreadPoollogDao extends iBatis3DaoImpl implements IOrderThreadPoollogDao {
    
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.OrderThreadPoollogEntity.";
	/**
	 * @author:lianghaisheng
	 * @Description：插入日志信息
	 * @date:2014-4-30 下午4:04:27
	 */
	@Override
	public int insert(OrderThreadPoollogEntity record) {
		return getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

}
