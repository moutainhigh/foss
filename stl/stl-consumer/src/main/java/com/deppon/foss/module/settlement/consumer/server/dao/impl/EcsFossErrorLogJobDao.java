package com.deppon.foss.module.settlement.consumer.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IEcsFossErrorLogJobDao;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.EcsFossErrorLogJobEntity;

/**
 * 快递对接FOSS,JOB定时执行该表异常日志Dao
 * @author 326181
 * @date 2016-9-20
 */
public class EcsFossErrorLogJobDao extends iBatis3DaoImpl implements IEcsFossErrorLogJobDao {

	private static final String NAME_SPACE = "foss.stl.EcsFossErrorLogJobEntityMapper.";
	
	@Override
	public void insertEcsFossErrorLogJob(
			EcsFossErrorLogJobEntity errorLogJobEntity) {
		this.getSqlSession().insert(NAME_SPACE + "insertEcsFossErrorLogJob", errorLogJobEntity);
	}

	@Override
	public void updateEcsFossErrorLogJob(Map<String, Object> params) {
		this.getSqlSession().insert(NAME_SPACE + "updateEcsFossErrorLogJob", params);
	}

	@Override
	public List<EcsFossErrorLogJobEntity> findEcsFossErrorLogJob(String codeType) {
		return this.getSqlSession().selectList(NAME_SPACE + "findEcsFossErrorLogJob", codeType);
	}

}
