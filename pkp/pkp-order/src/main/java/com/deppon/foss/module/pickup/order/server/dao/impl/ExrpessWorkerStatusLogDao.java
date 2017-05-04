package com.deppon.foss.module.pickup.order.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusLogDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusLogEntity;

public class ExrpessWorkerStatusLogDao extends iBatis3DaoImpl implements IExpressWorkerStatusLogDao {

	private static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusLogEntity.";
	

    /**
     * 
     * @author:lianghaisheng
     * @Description：插入日志
     * @date:2014-4-24 下午2:33:04
     */
	@Override
	public int insertSelective(ExpressWorkerStatusLogEntity record) {
		return getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

	

}
