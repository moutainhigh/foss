package com.deppon.foss.module.settlement.ptpitf.server.inter;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.esb.inteface.domain.ptp.SendWithholdStatusResponse;

/**
 * D到H客户网上支付的需求优化:请求回调接口，需消费调服务端的响应消息
 * 
 * @author 269044-zhurongrong
 * @date 2017-01-06
 */
public class SendStatusToPtpCallBack implements ICallBackProcess {
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(SendStatusToPtpCallBack.class);

	/**
	 * 接收返回结果
	 * 
	 * @author 269044-zhurongrong
	 * @date 2017-01-06
	 * 
	 */
	@Override
	public void callback(Object response) throws ESBException {
		//处理开始
		LOGGER.info("接收合伙人扣款响应消息开始。");
		//转换消息对象
		SendWithholdStatusResponse req = (SendWithholdStatusResponse)response;
		//将消息结果进行打印
		LOGGER.info("扣款处理的结果：" + req.getResult());
		//异常信息进行打印
		LOGGER.info("扣款处理的异常：" + req.getReason());
		//处理结束
		LOGGER.info("接收合伙人扣款响应响应消息结束。");
		
	}

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
		LOGGER.error("接收合伙人系统扣款响应消息出错：" + errorResponse.toString());
		
	}

}
