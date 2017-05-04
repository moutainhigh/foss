package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.server.dao.IVtsCodAuditLogDao;
import com.deppon.foss.module.settlement.common.api.server.service.IVtsCodAuditLogService;
import com.deppon.foss.module.settlement.common.api.shared.domain.VtsCodAuditLogEntity;

public class VtsCodAuditLogService implements IVtsCodAuditLogService{
    private IVtsCodAuditLogDao vtsCodAuditLogDao;
	public void setVtsCodAuditLogDao(IVtsCodAuditLogDao vtsCodAuditLogDao) {
		this.vtsCodAuditLogDao = vtsCodAuditLogDao;
	}
	@Override
	public int insertBath(List<VtsCodAuditLogEntity> list) {
		
		return vtsCodAuditLogDao.insertBath(list);
	}

}
