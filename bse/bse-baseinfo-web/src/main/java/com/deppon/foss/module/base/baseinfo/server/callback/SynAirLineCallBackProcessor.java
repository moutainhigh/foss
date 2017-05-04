package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.SyncAirlineResponse;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncAirlineResponseTrans;

public class SynAirLineCallBackProcessor implements ICallBackProcess {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SynAirLineCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
			SyncAirlineResponse airlineResponse = (SyncAirlineResponse) response;
			LOGGER.info("send airline info,then receive callback info.同步FOSS航空公司，收到回调信息：\n"
					+ new SyncAirlineResponseTrans()
							.fromMessage(airlineResponse));
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
