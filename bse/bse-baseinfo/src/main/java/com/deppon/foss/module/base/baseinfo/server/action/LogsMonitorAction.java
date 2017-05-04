/**
 * @Title: LogsMonitorAction.java
 * @Package com.deppon.foss.module.base.baseinfo.server.action
 * @Description: 日志监控查询
 * Copyright: Copyright (c) 2013 
 * Company:德邦物流
 * 
 * @author Comsys-129903-阮正华
 * @date 2013-3-22 上午11:07:37
 * @version V1.0
 */

package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.List;
import com.deppon.foss.framework.server.components.logger.entity.LogInfo;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILogsMonitorService;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LogsMonitorException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.LogMonitorVO;

/**
 * @ClassName: LogsMonitorAction
 * @Description: 日志监控查询
 * @author 129903-阮正华
 * @date 2013-3-22 上午11:07:37
 *
 */

public class LogsMonitorAction extends AbstractAction {

	
	private static final long serialVersionUID = -5474591450558757432L;

	private ILogsMonitorService logMonitorService;
	
	private LogMonitorVO logMonitorVO;

	public void setLogMonitorService(ILogsMonitorService logMonitorService) {
		this.logMonitorService = logMonitorService;
	}

	public LogMonitorVO getLogMonitorVO() {
		return logMonitorVO;
	}

	public void setLogMonitorVO(LogMonitorVO logMonitorVO) {
		this.logMonitorVO = logMonitorVO;
	}
	@JSON
	public String queryLogsMonitorList(){
		LogInfo logMonitorEntity =  logMonitorVO.getLogMonitorEntity();
		try{
			List<LogInfo> logMonitorEntityList = logMonitorService.queryLogsMonitor(logMonitorEntity, limit, start);
			getLogMonitorVO().setLogMonitorEntityList(logMonitorEntityList);
			setTotalCount(logMonitorService.queryRecordCount(logMonitorEntity));
		}catch(LogsMonitorException e){
			returnError(e);
		}
		return returnSuccess();
	}
}
