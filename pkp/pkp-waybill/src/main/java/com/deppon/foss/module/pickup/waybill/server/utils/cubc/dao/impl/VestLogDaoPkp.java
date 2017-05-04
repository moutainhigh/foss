package com.deppon.foss.module.pickup.waybill.server.utils.cubc.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.dao.IVestLogDaoPkp;
import com.deppon.foss.module.pickup.waybill.server.utils.cubc.model.VestLogDo;

public class VestLogDaoPkp extends iBatis3DaoImpl implements IVestLogDaoPkp {
	private static final String NAMESPACE = "foss.pkp.cubc.VestLogDao.";
	@Override
	public int insert(VestLogDo vestLogDo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().insert(NAMESPACE + "insert", vestLogDo);
	}

}
