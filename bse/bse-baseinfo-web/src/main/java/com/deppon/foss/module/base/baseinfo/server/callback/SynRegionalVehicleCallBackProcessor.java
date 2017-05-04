package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncRegionVehicleResponseTrans;
import com.deppon.oms.inteface.domain.SyncRegionVehicleResponse;

/**
 * 定人定区接口回调
 * @author 310854
 * @date 2016-4-25
 */
public class SynRegionalVehicleCallBackProcessor implements ICallBackProcess{

	private static final Logger LOGGER = LoggerFactory.getLogger(SynRegionalVehicleCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncRegionVehicleResponse createScatterResponse = (SyncRegionVehicleResponse) response;
			LOGGER.info("send RegionalVehicle info,then receive callback info.同步定人定区信息，收到回调信息：\n"+ 
			   new SyncRegionVehicleResponseTrans().fromMessage(createScatterResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		// TODO Auto-generated method stub
		if(null != errorResponse){
			SyncRegionVehicleResponse info = (SyncRegionVehicleResponse)errorResponse;
			StringBuilder nb =new StringBuilder();
			nb.append("是否成功：").append(info.getIsSuccess()).append(",异常内容：")
				.append(info.getExceptionMsg());

			LOGGER.error("ESB处理错误", nb.toString());
		}
	}

}
