package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.VtsCodAuditLogEntity;


public interface IVtsCodAuditLogDao {
	 /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBath(List<VtsCodAuditLogEntity> list);
}
