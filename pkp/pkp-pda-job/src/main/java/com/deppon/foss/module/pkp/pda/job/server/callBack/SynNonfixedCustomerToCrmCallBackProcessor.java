package com.deppon.foss.module.pkp.pda.job.server.callBack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.crm.CreateScatterResponse;
import com.deppon.esb.pojo.transformer.jaxb.CreateScatterResponseTrans;

public class SynNonfixedCustomerToCrmCallBackProcessor implements
		ICallBackProcess {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SynNonfixedCustomerToCrmCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		if (null != response) {
			CreateScatterResponse createScatterResponse = (CreateScatterResponse) response;
			LOGGER.info("send create scatter info,then receive callback info.同步创建的散客信息，收到回调信息：\n"+ 
			   new CreateScatterResponseTrans().fromMessage(createScatterResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		ErrorResponse info = (ErrorResponse)errorResponse;
		StringBuilder nb =new StringBuilder();
		nb.append(info.getBusinessId()).append(",描述1：")
			.append(info.getBusinessDesc1()).append(",描述2：")
			.append(info.getBusinessDesc2()).append(",描述3：")
			.append(info.getBusinessDesc3());

		LOGGER.error("ESB处理错误", nb.toString());
	}

}
