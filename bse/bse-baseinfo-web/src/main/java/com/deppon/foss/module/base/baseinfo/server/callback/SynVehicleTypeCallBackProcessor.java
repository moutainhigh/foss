package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncVehicleTypeResponseTrans;
import com.deppon.oms.inteface.domain.SyncVehicleTypeResponse;

/**
 * 车型每公里费用表基础资料回调
 * @author 310854
 * @date 2016-4-25
 */
public class SynVehicleTypeCallBackProcessor implements ICallBackProcess{

	private static final Logger LOGGER = LoggerFactory.getLogger(SynVehicleTypeCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncVehicleTypeResponse syncVehicleTypeResponse = (SyncVehicleTypeResponse) response;
			LOGGER.info("send VehicleType info,then receive callback info.同步车型每公里费用表基础资料信息，收到回调信息：\n"+ 
			   new SyncVehicleTypeResponseTrans().fromMessage(syncVehicleTypeResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		// TODO Auto-generated method stub
		if(null != errorResponse){
			SyncVehicleTypeResponse info = (SyncVehicleTypeResponse)errorResponse;
			StringBuilder nb =new StringBuilder();
			nb.append("是否成功：").append(info.getIsSuccess()).append(",异常内容：")
				.append(info.getExceptionMsg());

			LOGGER.error("ESB处理错误", nb.toString());
		}
	}

}
