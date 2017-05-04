package com.deppon.foss.module.transfer.common.server.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.inteface.domain.vehlinetask.SyncVehLineTaskResponse;

public class SynTaskVehicleInfoToGPSCallBackProcess implements ICallBackProcess {

	/**
	 * 日志
	 */
	protected final static Logger logger = LoggerFactory
			.getLogger(SynTaskVehicleInfoToGPSCallBackProcess.class.getName());
	/**
	 * 
	 * <p>GPS端JMS服务正常回调处理</p> 
	 * @author alfred
	 * @date 2014-3-21 下午3:11:45
	 * @param response
	 * @throws ESBException 
	 * @see com.deppon.esb.core.process.ICallBackProcess#callback(java.lang.Object)
	 */
	@Override
	public void callback(Object response) throws ESBException {
		logger.error("==========================================");
		SyncVehLineTaskResponse res = (SyncVehLineTaskResponse) response;
		logger.error("同步车辆任务正常回调"+res.getSuccessCount());

	}

	/**
	 * 
	 * <p>GPS端JMS服务异常回调处理</p> 
	 * @author alfred
	 * @date 2014-3-21 下午3:11:49
	 * @param errorResponse
	 * @throws ESBException 
	 * @see com.deppon.esb.core.process.ICallBackProcess#errorHandler(java.lang.Object)
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		SyncVehLineTaskResponse res = (SyncVehLineTaskResponse) errorResponse;
		logger.error("同步车辆任务异常回调",res.getSuccessCount());
	}

}
