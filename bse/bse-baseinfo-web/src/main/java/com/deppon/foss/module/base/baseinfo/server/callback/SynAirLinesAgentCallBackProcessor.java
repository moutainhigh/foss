package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.SyncAirlineAgentResponse;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncAirlineAgentResponseTrans;

public class SynAirLinesAgentCallBackProcessor implements ICallBackProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SynAirLinesAgentCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
			SyncAirlineAgentResponse airlineAgentResponse = (SyncAirlineAgentResponse) response;
			LOGGER.info("send airLinesAgent info,then receive callback info.同步FOSS航空公司代理人，收到回调信息：\n"
					+ new SyncAirlineAgentResponseTrans()
							.fromMessage(airlineAgentResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {

		ErrorResponse info = (ErrorResponse) errorResponse;
		StringBuilder nb = new StringBuilder();
		nb.append(info.getBusinessId()).append(",描述1：")
				.append(info.getBusinessDesc1()).append(",描述2：")
				.append(info.getBusinessDesc2()).append(",描述3：")
				.append(info.getBusinessDesc3());

		LOGGER.error("ESB处理错误", nb.toString());
	}

}
