package com.deppon.pda.bdm.module.ocb.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.ocb.server.dao.IFlowCountDao;
import com.deppon.pda.bdm.module.ocb.shared.domain.FlowCountBean;

public class FlowCountDao extends iBatis3DaoImpl implements IFlowCountDao{

	@Override
	public void saveFlowCount(FlowCountBean param) {
		if(param != null){
			param.setId(UUIDUtils.getUUID());
			getSqlSession().insert(getClass().getName() + ".saveFlowCount", param);
		}
	}
}
