package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncSalesDescExpandResponseTrans;
import com.deppon.oms.inteface.domain.SyncSalesDescExpandResponse;

/**
 * 同步营业部自提派送扩展资料给周边系统，订单，快递
 * 
 * @author 273311
 * 
 */
public class SalesDescExpandServiceResultCallBackProcessor implements
		ICallBackProcess {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SalesDescExpandServiceResultCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
			SyncSalesDescExpandResponse reponse = (SyncSalesDescExpandResponse) response;
			LOGGER.info("send SalesDescExpand to omsand System ：发送营业部自提派送扩展资料给周边系统,回调信息反馈  \n"
					+ new SyncSalesDescExpandResponseTrans()
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
