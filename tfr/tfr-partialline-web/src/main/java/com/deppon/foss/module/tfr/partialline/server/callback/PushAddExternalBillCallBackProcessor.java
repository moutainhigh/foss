package com.deppon.foss.module.tfr.partialline.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.module.foss.shared.domain.ResponseExternalBillDto;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;

public class PushAddExternalBillCallBackProcessor implements ICallBackProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushAddExternalBillCallBackProcessor.class);
	// 错误标志，用于查找日志
	private static final String EXTERNAL_ADD_CALL_BACK = "EXTERNAL_ADD_CALL_BACK:";

	@Override
	public void callback(Object response) throws ESBException {
		if(response!=null){
			ResponseExternalBillDto externalBillResponse = (ResponseExternalBillDto)response;
			if(externalBillResponse != null){
				LOGGER.info("原因："+externalBillResponse.getReason());
				LOGGER.info("是否成功："+externalBillResponse.getResult());
			}else{
				LOGGER.info("response: null");
			}
		}else{
			//无操作
		}

	}
    @Override
	public void errorHandler(Object errorResponse) throws ESBException {
		
		LOGGER.error(EXTERNAL_ADD_CALL_BACK,errorResponse.toString());
	}

}
