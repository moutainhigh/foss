package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.ISaveEcsBillLogDao;
import com.deppon.foss.util.UUIDUtils;

public class SaveEcsBillLogDao extends iBatis3DaoImpl implements ISaveEcsBillLogDao {
	
	private Logger logger = LoggerFactory.getLogger(SaveEcsBillLogDao.class);
	
	private static final String NAMESPACE = "foss.pkp.SaveEcsBillLogMapper.";
	
	@Override
	public void saveEcsBillLog(Map ecsBillLog) {
		logger.info("插入ECS收派计算运费日志 ");
		// 设置UUID
		ecsBillLog.put("id", UUIDUtils.getUUID());		
		this.getSqlSession().insert(NAMESPACE+"insert", ecsBillLog);
	}
}
