package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPushFoss2EcsWaybillNoLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.PushFoss2EcsWaybillNoLogEntity;
import com.deppon.foss.util.UUIDUtils;
/*
 * FOSS推送运单号给ECS记录日志
 * 2016年7月23日 08:29:30 葛亮亮
 */
public class PushFoss2EcsWaybillNoLogDao extends iBatis3DaoImpl implements
		IPushFoss2EcsWaybillNoLogDao {
	private static final String NAMESPACE = "foss.pkp.PushFoss2EcsWaybillNoLogMapper.";
	
	public void insertWaybillNoLog(PushFoss2EcsWaybillNoLogEntity logEntity) {
		// 设置UUID
		logEntity.setId(UUIDUtils.getUUID());
		//添加日志
		this.getSqlSession().insert(NAMESPACE+"insert", logEntity);
	}

}
