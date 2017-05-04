package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAgActionHistoryDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AgActionHistoryEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 弃货处理历史记录实现类
 * 
 * @author ibm-wangfei
 * @date Apr 24, 2013 5:48:39 PM
 */
public class AgActionHistoryDao extends iBatis3DaoImpl implements IAgActionHistoryDao {

	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.AgActionHistoryEntity.";

	@Override
	public int insertSelective(AgActionHistoryEntity record) {
		record.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", record);
	}
}
