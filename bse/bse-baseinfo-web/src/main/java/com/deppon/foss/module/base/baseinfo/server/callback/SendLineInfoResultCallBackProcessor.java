package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SendLineResponseTrans;
import com.deppon.foss.inteface.domain.usermanagement.SendLineResponse;
/**
 * 同步给网点规划系统的回调函数
 * @author 130566
 *
 */
public class SendLineInfoResultCallBackProcessor implements ICallBackProcess {
	private static final Logger LOG = LoggerFactory.getLogger(SendLineInfoResultCallBackProcessor.class);
	@Override
	public void callback(Object response) throws ESBException {
		if(null !=response){
			SendLineResponse resp = new SendLineResponse();
			LOG.info("send lineinfo info,then receive callback info.同步线路信息给wdgh，收到回调信息：\n"
					+new SendLineResponseTrans().fromMessage(resp));
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

		LOG.error("ESB处理错误", sb.toString());

	}

}
