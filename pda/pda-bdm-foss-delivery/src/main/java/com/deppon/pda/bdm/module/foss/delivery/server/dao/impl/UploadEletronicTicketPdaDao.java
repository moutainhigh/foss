package com.deppon.pda.bdm.module.foss.delivery.server.dao.impl;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.pda.bdm.module.foss.delivery.server.dao.IUploadEletronicTicketPdaDao;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PDAElectronicTicketEntity;

public class UploadEletronicTicketPdaDao extends SqlSessionDaoSupport implements  IUploadEletronicTicketPdaDao{

	@Override
	public void saveUploadResult(
			PDAElectronicTicketEntity pdaElectronicTicketEntity) {
		getSqlSession().insert(getClass().getName()+".saveUploadResult", pdaElectronicTicketEntity);
	}

}
