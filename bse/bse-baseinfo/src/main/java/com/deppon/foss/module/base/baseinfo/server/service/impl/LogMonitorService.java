/**
 * @Title: LogMonitorService.java
 * @Package com.deppon.foss.module.base.baseinfo.server.service.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2013 
 * Company:德邦物流
 * 
 * @author Comsys-129903-阮正华
 * @date 2013-3-23 上午11:29:54
 * @version V1.0
 */

package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.logger.entity.LogInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILogsMonitorDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILogsMonitorService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LogsMonitorException;

/**
 * @ClassName: LogMonitorService
 * @Description: TODO
 * @author 129903-阮正华
 * @date 2013-3-23 上午11:29:54
 *
 */

public class LogMonitorService implements ILogsMonitorService {
	
	private ILogsMonitorDao logMonitorDao;

	/*
	 * <p>Title: queryLogsMonitor</p>
	 * <p>Description: </p>
	 * @param logMonitorEntity
	 * @param limit
	 * @param start
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILogsMonitorService#queryLogsMonitor(com.deppon.foss.module.base.baseinfo.api.shared.domain.LogMonitorEntity, int, int)
	 */

	public ILogsMonitorDao getLogMonitorDao() {
		return logMonitorDao;
	}

	public void setLogMonitorDao(ILogsMonitorDao logMonitorDao) {
		this.logMonitorDao = logMonitorDao;
	}

	@Override
	public List<LogInfo> queryLogsMonitor (LogInfo logMonitorEntity, int limit, int start)  throws LogsMonitorException{
		return logMonitorDao.queryLogsMonitor(logMonitorEntity, limit, start);
	}

	/*
	 * <p>Title: queryRecordCount</p>
	 * <p>Description: </p>
	 * @param logMonitorEntity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILogsMonitorService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LogMonitorEntity)
	 */

	@Override
	public Long queryRecordCount(LogInfo logMonitorEntity)  throws LogsMonitorException{
		return logMonitorDao.queryRecordCount(logMonitorEntity);
	}

}
