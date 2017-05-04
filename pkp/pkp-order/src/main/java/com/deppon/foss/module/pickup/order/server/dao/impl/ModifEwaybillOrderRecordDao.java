package com.deppon.foss.module.pickup.order.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IModifEwaybillOrderRecordDao;
import com.deppon.foss.module.pickup.order.api.shared.domain.ModifyEwaybillOrderLogEntity;

public class ModifEwaybillOrderRecordDao extends iBatis3DaoImpl implements
		IModifEwaybillOrderRecordDao {

	private static final String NAMESPACE = "com.deppon.foss.module.pickup.order.api.shared.domain.ModifyEwaybillOrderLogEntity.";
	
	@Override
	public int insertSelective(ModifyEwaybillOrderLogEntity record) {
		return getSqlSession().insert(NAMESPACE+"insertSelective", record);
	}

}
