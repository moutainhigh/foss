package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOutWarehouseExceptionDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OutStockExceptionEntity;


/**
 * 异常出库Dao实现类
 * @author foss-qiaolifeng
 * @date 2012-12-17 下午4:20:15
 */
public class OutWarehouseExceptionDao extends iBatis3DaoImpl implements
		IOutWarehouseExceptionDao {

	private static final String NAMESPACE = "foss.stl.OutStockExceptionEntityDao.";// 异常出库命名空间
	
	/** 
	 * 新增异常出库信息
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午4:20:18
	 * @see com.deppon.foss.module.settlement.consumer.api.server.dao.IOutWarehouseExceptionDao#addOutWarehouseException(com.deppon.foss.module.settlement.consumer.api.shared.domain.OutStockExceptionEntity)
	 */
	@Override
	public int addOutWarehouseException(OutStockExceptionEntity entity) {
		
		return getSqlSession().insert(NAMESPACE+"insert", entity);
	}

}
