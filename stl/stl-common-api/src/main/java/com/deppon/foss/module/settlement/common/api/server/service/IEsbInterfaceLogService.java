package com.deppon.foss.module.settlement.common.api.server.service;

import com.deppon.foss.module.settlement.common.api.shared.domain.InterfaceLogEntity;

/** 
 * 异步接口日志表service
 * @author foss结算-306579-guoxinru 
 * @date 2016-6-17 上午11:29:57    
 */
public interface IEsbInterfaceLogService {
	/**
	 * 接口日志表新增记录
	 */
	boolean addInterfaceLog(InterfaceLogEntity entity); 

	
	/**
	 * 保存合伙人异步接口抛异常的错误日志
	 * @author gpz
	 * @date 2016年9月2日
	 * @param obj 接口请求参数
	 * @param exception 异常信息
	 */
	void savePtpInterfaceLog(Object obj,String exception);
}
