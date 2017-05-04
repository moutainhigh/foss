package com.deppon.foss.module.settlement.ecsitf.server.jms;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.ecs.inteface.domain.graycustomer.SyncUpdateGrayCustomerResponse;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.exception.CommonExceptionInfo;

/**
 * 灰名单相应结果：灰名单接口为请求回调接口，为了消费掉悟空系统的消息，防止堵塞
 * 
 * @author 269044-zhurongrong
 * @date 2016-05-31
 *
 */
public class GrayCustomerToECSCallBack implements ICallBackProcess {
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(GrayCustomerToECSCallBack.class);

	/**
	 * 接收返回结果
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-05-31
	 * 
	 */
	@Override
	public void callback(Object response) throws ESBException {
		//处理开始
		LOGGER.info("接收悟空系统灰名单响应消息开始。");
		//转换消息对象
		SyncUpdateGrayCustomerResponse req = (SyncUpdateGrayCustomerResponse)response;
		//将消息结果进行打印
		LOGGER.info("灰名单处理的结果：" + req.getResult());
		//异常信息进行打印
		LOGGER.info("灰名单处理的异常：" + req.getReason());
		//处理结束
		LOGGER.info("接收悟空系统灰名单响应消息结束。");
		
	}

	/**
	 * 处理异常
	 * 
	 * @author 269044-zhurongrong
	 * @date 2016-05-31
	 * 
	 */
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		//记录日志
		LOGGER.info("错误处理结果开始");
		//获取异常参数
		ErrorResponse response = (ErrorResponse) errorResponse;
		//获取esb异常信息
		CommonExceptionInfo info = response.getCommonExceptionInfo();
		//记录日志
		LOGGER.info("业务编号:" + info.getExceptioncode());
		//记录日志
		LOGGER.info("错误信息：" + info.getMessage());
		//记录日志
		LOGGER.info("错误信息：" + info.getDetailedInfo());
		//记录日志
		LOGGER.error("接收悟空系统灰名单响应消息出错：" + errorResponse.toString());
		
	}

}
