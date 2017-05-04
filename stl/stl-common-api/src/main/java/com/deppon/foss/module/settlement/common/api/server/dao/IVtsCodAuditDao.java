package com.deppon.foss.module.settlement.common.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.common.api.shared.domain.CodAuditForVtsSignEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditForVtsSignDto;


public interface IVtsCodAuditDao {
	/**
     * 根据单号  查询
     * @param  waybillNo
     * @return the Date 
     * */
	List<CodAuditForVtsSignEntity> queryCodChangeTime(CodAuditForVtsSignDto codAuditDto);

	int insert(CodAuditForVtsSignEntity record);
}
