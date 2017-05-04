package com.deppon.pda.bdm.module.push.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.push.server.dao.IPushDao;
import com.deppon.pda.bdm.module.push.shared.domain.PushMessage;

public class PushDao extends iBatis3DaoImpl implements IPushDao{

	@Override
	public void savePushMessage(PushMessage pushMessage) {
		getSqlSession().insert(getClass().getName() + ".savePushMessage", pushMessage);
	}

}
