package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsCodAuditLogDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.VtsCodAuditLogEntity;


public class VtsCodAuditLogDao extends iBatis3DaoImpl implements IVtsCodAuditLogDao{
	 private static final String NAMESPACE ="foss.stl.VtsCodAuditLogMapper.";
	/**
     * 批量插入日志
     * @param list
     * @return
     */
	@Override
	public int insertBath(List<VtsCodAuditLogEntity> list) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(NAMESPACE+"vtsinsertLogBath",list);
	}
}
