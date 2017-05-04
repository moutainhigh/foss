package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncSmallZoneResponseTrans;
import com.deppon.oms.inteface.domain.SyncSmallZoneResponse;

/**
 * 接送货小区接口回调
 * @author 310854
 * @date 2016-4-25
 */
public class SynSmallZoneCallBackProcessor implements ICallBackProcess{

	private static final Logger LOGGER = LoggerFactory.getLogger(SynSmallZoneCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncSmallZoneResponse syncSmallZoneResponse = (SyncSmallZoneResponse) response;
			LOGGER.info("send SmallZone info,then receive callback info.同步接送货小区信息，收到回调信息：\n"+ 
			   new SyncSmallZoneResponseTrans().fromMessage(syncSmallZoneResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		// TODO Auto-generated method stub
		if(null != errorResponse){
			SyncSmallZoneResponse info = (SyncSmallZoneResponse)errorResponse;
			StringBuilder nb =new StringBuilder();
			nb.append("是否成功：").append(info.getIsSuccess()).append(",异常内容：")
				.append(info.getExceptionMsg());

			LOGGER.error("ESB处理错误", nb.toString());
		}
	}

}
