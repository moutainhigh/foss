package com.deppon.foss.module.base.baseinfo.server.dao.impl;


import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IEsbErrorLoggingDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EsbErrorLogging;

@SuppressWarnings("unchecked")
public class EsbErrorLoggingDao extends SqlSessionDaoSupport implements IEsbErrorLoggingDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.esbErrorMessage.";
	@Transactional(propagation=Propagation.REQUIRES_NEW) 
	@Override
	public void addErrorMessage(EsbErrorLogging entity) {
		if(entity==null){
			return;
		}else
		this.getSqlSession().insert(NAMESPACE+"addEsbErrorMessage",entity);
		}
	
}
