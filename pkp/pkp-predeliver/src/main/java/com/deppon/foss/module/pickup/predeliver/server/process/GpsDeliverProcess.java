package com.deppon.foss.module.pickup.predeliver.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;

public class GpsDeliverProcess implements ICallBackProcess {
	/**
	 * 日志
	 */
	protected final static Logger LOGGER = LoggerFactory.getLogger(GpsDeliverProcess.class);

	/**
	 * Gps正常返回参数，目前Foss这边不需要多做处理
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月28日 11:39:21
	 */
	@Override
	public void callback(Object response) throws ESBException {
		//强制转换返回参数
		LOGGER.info("Gps返回了参数,详情:");

	}	

	/**
	 * Gps返回了错误参数，目前Foss这边不需要多做处理,等需要做处理的时候再加吧
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月28日 11:39:21
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		//强制转换返回参数
		LOGGER.info("Gps返回了错误参数,详情:");
	}	

}
