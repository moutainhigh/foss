package com.deppon.foss.module.settlement.common.server.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.settlement.common.api.server.dao.IFossToFinsRemitCommonLogDao;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonLogService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossToFinsRemitCommonLogEntity;

public class FossToFinsRemitCommonLogService implements
		IFossToFinsRemitCommonLogService {
	/**
	 * 日志
	 */
	public final Logger logger = LogManager.getLogger(FossToFinsRemitCommonLogService.class);
	
	/**
	 *  记录推送第三方付款数据到财务自助响应Dao
	 * 
	 */
	private IFossToFinsRemitCommonLogDao fossToFinsRemitCommonLogDao;
	
	
	public IFossToFinsRemitCommonLogDao getFossToFinsRemitCommonLogDao() {
		return fossToFinsRemitCommonLogDao;
	}


	public void setFossToFinsRemitCommonLogDao(
			IFossToFinsRemitCommonLogDao fossToFinsRemitCommonLogDao) {
		this.fossToFinsRemitCommonLogDao = fossToFinsRemitCommonLogDao;
	}


	@Override
	@Transactional
	public void insertFossToFinsRemitCommonLog(FossToFinsRemitCommonLogEntity entity) {
		this.fossToFinsRemitCommonLogDao.insertFossToFinsRemitCommonLog(entity);
	}




}
