package com.deppon.pda.bdm.module.foss.login.server.dao.impl;


import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.pda.bdm.module.foss.login.server.dao.ILoginOutDao;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PdaLoginOutInfo;
import com.deppon.pda.bdm.module.foss.login.shared.domain.TaskUserRelation;


public class LoginOutDao extends iBatis3DaoImpl implements ILoginOutDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskUserRelation> getTaskRelation(
			PdaLoginOutInfo pdaLoginOutInfo) {
		return getSqlSession().selectList(getClass().getName()+".getTaskRelationNoFnsh", pdaLoginOutInfo);
	}

	@Override
	public void deleteLogin(PdaLoginOutInfo pdaLoginOutInfo) {
		getSqlSession().delete(getClass().getName()+".deleteLogin", pdaLoginOutInfo);
	}
	
}
