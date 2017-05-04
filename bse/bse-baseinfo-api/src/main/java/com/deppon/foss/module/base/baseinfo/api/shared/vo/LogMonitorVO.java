package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.framework.server.components.logger.entity.LogInfo;

/**
 * 系统日志信息
 * @author 阮正华
 * @date 2012-9-5 下午3:42:17
 * @since
 * @version
 */
public class LogMonitorVO  implements Serializable{
    

	private static final long serialVersionUID = 2445189876254341302L;

	private LogInfo logMonitorEntity;
	
	private List<LogInfo> logMonitorEntityList;
	
	public LogInfo getLogMonitorEntity() {
		return logMonitorEntity;
	}

	public void setLogMonitorEntity(LogInfo logMonitorEntity) {
		this.logMonitorEntity = logMonitorEntity;
	}

	public List<LogInfo> getLogMonitorEntityList() {
		return logMonitorEntityList;
	}

	public void setLogMonitorEntityList(List<LogInfo> logMonitorEntityList) {
		this.logMonitorEntityList = logMonitorEntityList;
	}

}