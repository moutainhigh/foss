package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IActualFreightBIDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightBIEntity;
import com.deppon.foss.util.UUIDUtils;

public class ActualFreightBIDao extends iBatis3DaoImpl implements IActualFreightBIDao {
	
	private static final String NAMESPACE = "foss.pkp.ActualFreightBIEntityMapper.";

	@Override
	public int insert(ActualFreightBIEntity actualFreightBIEntity) {
		actualFreightBIEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective", actualFreightBIEntity);
	}

}
