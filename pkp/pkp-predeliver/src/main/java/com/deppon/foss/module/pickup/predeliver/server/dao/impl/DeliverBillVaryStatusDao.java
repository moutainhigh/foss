package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverBillVaryStatusDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;

/** 
 * @ClassName: DeliverBillVaryStatusDao 
 * @Description: 派送单状态更新记录表Dao 实现
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-27 下午3:44:14 
 *  
 */
public class DeliverBillVaryStatusDao extends iBatis3DaoImpl implements IDeliverBillVaryStatusDao {
	
	/**
	 * 派送单状态更新命名空间
	 */
	private final static String VARYSTATUS_NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntityMapper.";

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverBillVaryStatusDao#insertOne(com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity)
	 */
	@Override
	public int insertOne(DeliverBillVaryStatusEntity deliverBillVaryStatusEntity) {
		return super.getSqlSession().insert(VARYSTATUS_NAMESPACE + "insertOne", deliverBillVaryStatusEntity);
	}

}
