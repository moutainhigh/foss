package com.deppon.foss.module.tfr.execution.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.module.foss.shared.domain.CubcVehicleAssembleBillResponse;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;

public class ResponseAddDstowageCallBackProcessor implements
		ICallBackProcess {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAddDstowageCallBackProcessor.class);
    //错误标志，用于查找日志
  	private static final String STOWAGE_ADD_CALL_BACK = "STOWAGE_ADD_CALL_BACK:";
    @Override
	public void callback(Object response) throws ESBException {
		if(response!=null){
			CubcVehicleAssembleBillResponse cubcVehicleAssembleBillResponse = (CubcVehicleAssembleBillResponse)response;
			if(cubcVehicleAssembleBillResponse != null){
				LOGGER.info("原因："+cubcVehicleAssembleBillResponse.getReason());
				LOGGER.info("是否成功："+cubcVehicleAssembleBillResponse.getResult());
			}else{
				LOGGER.info("response: null");
			}
		}else{
			//无操作
		}

	}
    @Override
	public void errorHandler(Object errorResponse) throws ESBException {
		
		LOGGER.error(STOWAGE_ADD_CALL_BACK,errorResponse.toString());
	}

}
