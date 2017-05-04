package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.server.dao.IVtsCodAuditDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.CodAuditForVtsSignEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditForVtsSignDto;

public class VtsCodAuditDao extends iBatis3DaoImpl implements IVtsCodAuditDao {
	 private static final String NAMESPACE="foss.stl.vtsCodAuditMapper.";

	@SuppressWarnings("unchecked")
	@Override
	public List<CodAuditForVtsSignEntity> queryCodChangeTime(CodAuditForVtsSignDto codAuditDto) {
		if(StringUtil.isEmpty(codAuditDto.getWaybillNo())){
    		return null;
    	}  
		return getSqlSession().selectList(NAMESPACE+"vtsqueryCodChangeTime",codAuditDto);
	}

	@Override
	public int insert(CodAuditForVtsSignEntity record) {
		
	   return getSqlSession().insert(NAMESPACE+"vtsinsert",record);
	}

}
