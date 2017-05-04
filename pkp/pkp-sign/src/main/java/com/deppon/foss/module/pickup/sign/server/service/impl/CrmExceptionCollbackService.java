package com.deppon.foss.module.pickup.sign.server.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.crm.WarnMessageResponse;
/**
 * 菜鸟项目--物流服务预警产品 货物拉回 异常破损签收回调CRM
 * @author 159231
 * @date 2015-3-26 下午7:33:30
 */
public class CrmExceptionCollbackService  implements ICallBackProcess
{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CrmExceptionCollbackService.class.getName());
	@Override
	public void callback(Object response) throws ESBException {
		WarnMessageResponse warnMessageResponse =(WarnMessageResponse)response;
		if(warnMessageResponse!= null){
			if(warnMessageResponse.isIsSuccess()){
				LOGGER.info("菜鸟项目--物流服务预警产品 货物拉回 异常破损签收 返回结果成功 返回内容:"+
						warnMessageResponse.getBillNumber()
						+warnMessageResponse.isIsSuccess());
			}else {
				LOGGER.error("菜鸟项目--物流服务预警产品 货物拉回 异常破损签收 返回结果失败 返回内容:"+
						warnMessageResponse.getBillNumber()+warnMessageResponse.isIsSuccess()
						);
			}
		}else {
			LOGGER.error("菜鸟项目--物流服务预警产品 货物拉回 异常破损签收  返回空");
		}
	}
	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		WarnMessageResponse warnMessageResponse =(WarnMessageResponse)errorResponse;
		LOGGER.error("菜鸟项目--物流服务预警产品 货物拉回 异常破损签收  调用接口失败"+warnMessageResponse.toString());
	}

}
