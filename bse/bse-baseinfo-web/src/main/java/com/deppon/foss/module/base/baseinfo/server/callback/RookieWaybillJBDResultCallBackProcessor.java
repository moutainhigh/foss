package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.dop.WayBillDistributeResponseEntity;
import com.deppon.esb.pojo.transformer.jaxb.WayBillDistributeResponseEntityTrans;

/**
 * 集包地基础资料
 * @author 273311
 *
 */
public class RookieWaybillJBDResultCallBackProcessor implements
		ICallBackProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(RookieWaybillJBDResultCallBackProcessor.class);


	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
			WayBillDistributeResponseEntity reponse= (WayBillDistributeResponseEntity) response;
			LOGGER.info("send WayBillDistributeRequestEntity to omsanddop System ：发送集包地信息给oms和dop,回调信息反馈  \n"+ new WayBillDistributeResponseEntityTrans().fromMessage(reponse));
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
