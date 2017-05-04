package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncLeasedTruckTeamResponseTrans;
import com.deppon.oms.inteface.domain.SyncLeasedTruckTeamResponse;
/**
 * 同步外请车服务资料
 * 
 * @author 310854
 * 
 */
public class SyncLeasedTruckTeamtCallBackProcessor implements
		ICallBackProcess {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncLeasedTruckTeamtCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncLeasedTruckTeamResponse reponse = (SyncLeasedTruckTeamResponse) response;
			LOGGER.info("send SalesDescExpand to omsand System ：同步外请车服务资料给周边系统,回调信息反馈  \n"
					+ new SyncLeasedTruckTeamResponseTrans()
							.fromMessage(reponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {

		ErrorResponse info = (ErrorResponse) errorResponse;
		StringBuilder sb = new StringBuilder();
		sb.append(info.getBusinessId()).append(",描述1：")
				.append(info.getBusinessDesc1()).append(",描述2：")
				.append(info.getBusinessDesc2()).append(",描述3：")
				.append(info.getBusinessDesc3());

		LOGGER.error("ESB处理错误", sb.toString());
	}
}
