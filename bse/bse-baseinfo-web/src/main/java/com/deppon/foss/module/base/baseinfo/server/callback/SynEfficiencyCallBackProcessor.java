package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.ecs.inteface.domain.SyncEfficiencyResponse;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncEfficiencyResponseTrans;

public class SynEfficiencyCallBackProcessor implements ICallBackProcess{

	private static final Logger LOGGER = LoggerFactory.getLogger(SynEfficiencyCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncEfficiencyResponse efficiencyResponse = (SyncEfficiencyResponse) response;
			LOGGER.info("send Efficiency info,then receive callback info.同步FOSS装卸车标准信息，收到回调信息：\n"+ 
			   new SyncEfficiencyResponseTrans().fromMessage(efficiencyResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		// TODO Auto-generated method stub
		ErrorResponse info = (ErrorResponse)errorResponse;
		StringBuilder nb =new StringBuilder();
		nb.append(info.getBusinessId()).append(",描述1：")
			.append(info.getBusinessDesc1()).append(",描述2：")
			.append(info.getBusinessDesc2()).append(",描述3：")
			.append(info.getBusinessDesc3());

		LOGGER.error("ESB处理错误", nb.toString());
	}

}
