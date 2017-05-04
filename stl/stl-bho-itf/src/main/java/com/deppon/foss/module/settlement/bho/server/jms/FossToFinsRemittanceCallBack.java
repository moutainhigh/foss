package com.deppon.foss.module.settlement.bho.server.jms;

import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.exception.CommonExceptionInfo;
import com.deppon.esb.inteface.domain.fins.FossToFinsRemitCommonResponse;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonLogService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossToFinsRemitCommonLogEntity;
import com.deppon.foss.util.UUIDUtils;


/**
 * @Description: 推送异常第三方异常付款数据到到财务自助的回调接口
 * @author 321603
 * 2016-10-14
 */
public class FossToFinsRemittanceCallBack implements ICallBackProcess {
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(FossToFinsRemittanceCallBack.class);
	
	private IFossToFinsRemitCommonLogService fossToFinsRemitCommonLogService;

	
	public IFossToFinsRemitCommonLogService getFossToFinsRemitCommonLogService() {
		return fossToFinsRemitCommonLogService;
	}


	public void setFossToFinsRemitCommonLogService(
			IFossToFinsRemitCommonLogService fossToFinsRemitCommonLogService) {
		this.fossToFinsRemitCommonLogService = fossToFinsRemitCommonLogService;
	}


	@Override
	public void callback(Object response) throws ESBException {
		//转换消息对象
		LOGGER.info("推送异常第三方异常付款数据到到财务自助的回调开始");
		FossToFinsRemitCommonResponse res = (FossToFinsRemitCommonResponse)response;
		FossToFinsRemitCommonLogEntity entity = new FossToFinsRemitCommonLogEntity();
		entity.setBillNum(res.getBillNum());
		entity.setFaileReason(res.getFaileReason());
		entity.setIsSuccess(res.getIsSuccess());
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateDate(new Date());
		fossToFinsRemitCommonLogService.insertFossToFinsRemitCommonLog(entity);
		LOGGER.info("推送异常第三方异常付款数据到到财务自助的回调结束");
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
		LOGGER.error("接收推送异常第三方异常付款数据到到财务自助响应消息出错：" + errorResponse.toString());
		
	}

}
