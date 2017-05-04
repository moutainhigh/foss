package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.VtsCodAuditLogEntity;


public interface IVtsCodAuditLogService {
	  /**
     * 批量插入
     * @param list
     * @return
     */
    int insertBath(List<VtsCodAuditLogEntity> list);

}
