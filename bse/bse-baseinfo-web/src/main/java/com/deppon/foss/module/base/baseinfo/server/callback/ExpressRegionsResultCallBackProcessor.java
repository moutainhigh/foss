package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.ExpressScopeReponseTrans;
import com.deppon.ows.inteface.domain.express.ExpressScopeReponse;

/**
 * 快递行政区域
 * @author 130134
 *
 */
public class ExpressRegionsResultCallBackProcessor implements
		ICallBackProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressRegionsResultCallBackProcessor.class);


	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
    		ExpressScopeReponse reponse= (ExpressScopeReponse) response;
			LOGGER.info("send ExpressPartSalesDeptRelation to Crm System ：发送营业部和快递点部的消息到Crm,回调信息反馈  \n"+ new ExpressScopeReponseTrans().fromMessage(reponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {

		ErrorResponse info = (ErrorResponse)errorResponse;
		StringBuilder sb =new StringBuilder();
		sb.append(info.getBusinessId()).append(",描述1：")
			.append(info.getBusinessDesc1()).append(",描述2：")
			.append(info.getBusinessDesc2()).append(",描述3：")
			.append(info.getBusinessDesc3());

		LOGGER.error("ESB处理错误", sb.toString());
	}
}
