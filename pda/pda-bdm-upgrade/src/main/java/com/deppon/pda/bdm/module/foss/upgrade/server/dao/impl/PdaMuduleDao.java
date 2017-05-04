package com.deppon.pda.bdm.module.foss.upgrade.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.upgrade.server.dao.IPdaMuduleDao;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.PdaModuleInfoEntity;

/**
 * 
  * @ClassName PdaMuduleDao 
  * @Description TODO 
  * @author mt 
  * @date 2013-8-22 下午4:12:01
 */
public class PdaMuduleDao extends SqlSessionDaoSupport implements IPdaMuduleDao {
	@Override
	public void updatePdaModuleInfo(PdaModuleInfoEntity parameter) {
		getSqlSession().update(getClass().getName() + ".updatePdaModuleInfo",
				parameter);
	}
}
