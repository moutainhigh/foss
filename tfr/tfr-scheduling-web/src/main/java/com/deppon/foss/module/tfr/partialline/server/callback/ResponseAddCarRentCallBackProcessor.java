package com.deppon.foss.module.tfr.partialline.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.module.foss.shared.domain.RentCarCubcResponse;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;

public class ResponseAddCarRentCallBackProcessor implements
		ICallBackProcess {
	 private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAddCarRentCallBackProcessor.class);
	   //错误标志，用于查找日志
	 private static final String RENT_CAR_ADD_CALL_BACK = "RENT_CAR_ADD_CALL_BACK:";
	 @Override
		public void callback(Object response) throws ESBException {
			if(response!=null){
				RentCarCubcResponse rentCarResponse = (RentCarCubcResponse)response;
				LOGGER.info("原因："+rentCarResponse.getReason());
				LOGGER.info("是否成功："+rentCarResponse.getResult());
			}else{
				//无操作
			}

		}

	 @Override
		public void errorHandler(Object errorResponse) throws ESBException {
			
			LOGGER.error(RENT_CAR_ADD_CALL_BACK,errorResponse.toString());
		}


}
