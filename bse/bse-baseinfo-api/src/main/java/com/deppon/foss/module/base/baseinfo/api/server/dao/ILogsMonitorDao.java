/**
 * @Title: ILogsMonitorDao.java
 * @Package com.deppon.foss.module.base.baseinfo.api.server.dao
 * @Description: 日志监控服务
 * Copyright: Copyright (c) 2011 
 * Company:德邦物流
 * 
 * @author Comsys-129903-阮正华
 * @date 2013-3-22 上午11:07:37
 * @version V1.0
 */
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import com.deppon.foss.framework.server.components.logger.entity.LogInfo;


/**
 * @author 129903-阮正华
 *
 */
public interface ILogsMonitorDao {
	/**
	 * 根据传入对象查询符合条件所有日志信息
	 * @param logMonitorEntity 日志对象查询实体
	 * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
	 */
	public List<LogInfo> queryLogsMonitor(LogInfo logMonitorEntity,int limit,int start);
	 /**
     * 统计总记录数 
     * @param logMonitorEntity 日志对象查询实体
     * @return 符合条件的总记录数
     * @see
     */
     Long queryRecordCount(LogInfo logMonitorEntity);
}
