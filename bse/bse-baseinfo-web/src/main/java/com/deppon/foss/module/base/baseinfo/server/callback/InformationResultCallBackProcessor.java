package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.oms.SyncMotorcadeResponse;
import com.deppon.esb.pojo.transformer.jaxb.InformationSyncResponseTrans;

/**
 *车队推送OMS
 * @author 198771
 *
 */
public class InformationResultCallBackProcessor implements ICallBackProcess{
	private static final Logger logger = LoggerFactory.getLogger(InformationResultCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {
		if (null != response) {
			SyncMotorcadeResponse syncMotorcadeResponse = (SyncMotorcadeResponse) response;
		    logger.info("send Information info,then receive callback info.同步车队信息到OMS，收到回调信息：\n"
			    + new InformationSyncResponseTrans()
				    .fromMessage(syncMotorcadeResponse));
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

		logger.error("ESB处理错误", sb.toString());
	}

}
