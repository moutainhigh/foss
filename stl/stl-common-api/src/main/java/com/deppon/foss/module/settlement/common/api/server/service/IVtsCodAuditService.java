package com.deppon.foss.module.settlement.common.api.server.service;

import java.util.List;
import com.deppon.foss.module.settlement.common.api.shared.domain.CodAuditForVtsSignEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditForVtsSignDto;


public interface IVtsCodAuditService {
	/**
     * 
     * @author 310970
     * @param waybillNo
     * @return the Date
     * */
    List<CodAuditForVtsSignEntity> queryCodChangeTime(CodAuditForVtsSignDto codAuditDto);
    
	int addCodAudit(CodAuditForVtsSignEntity record);
}
