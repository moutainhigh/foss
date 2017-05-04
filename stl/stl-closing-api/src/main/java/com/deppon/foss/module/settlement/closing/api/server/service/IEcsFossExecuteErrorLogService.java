package com.deppon.foss.module.settlement.closing.api.server.service;

import com.deppon.foss.module.settlement.closing.api.shared.domain.MqLogEntity;

/**
 * 异常任务查询表接口
 * @author 326181
 *
 */
public interface IEcsFossExecuteErrorLogService {

	/**
	 * 重新执行异常请求参数，执行成功之后删除失败日志，新增成功日志
	 * @param log
	 */
	void doExecuteLogReqMsg(MqLogEntity log);
}
