package com.deppon.foss.module.base.baseinfo.server.callback;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.foss.inteface.domain.lineinfo.SyncLineInfoResponse;
import com.deppon.foss.pojo.transformer.jaxb.SyncLineInfoResponseTrans;
/**
 * 接收CRM接收车队信息后的返回结果
 * 
 * @author 130346-foss-lifanghong
 * @date 2014-03-25 下午3:39:55
 */
public class LineinfoResultCallBackProcessor implements
ICallBackProcess {
	private static final Logger LOGGER = LoggerFactory
		    .getLogger(LineinfoResultCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
			SyncLineInfoResponse responseObj = (SyncLineInfoResponse) response;
		    LOGGER.info("send SyncLineInfo info,then receive callback info.同步线路信息，收到回调信息：\n"
			    + new SyncLineInfoResponseTrans()
				    .fromMessage(responseObj));
		   
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
	    
		// TODO Auto-generated method stub
		
	}

}
