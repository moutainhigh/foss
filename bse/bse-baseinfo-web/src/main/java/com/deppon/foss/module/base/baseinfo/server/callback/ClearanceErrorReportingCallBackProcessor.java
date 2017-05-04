package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.domain.foss2qms.ErrResponseParam;
import com.deppon.esb.pojo.transformer.json.ClearanceErrorReportingResponseTrans;

/**
 *净空差错上报
 * @author 307196
 *
 */
public class ClearanceErrorReportingCallBackProcessor implements ICallBackProcess{
	private static final Logger logger = LoggerFactory.getLogger(ClearanceErrorReportingCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {
		if (null != response) {
			ErrResponseParam errResponseParam = (ErrResponseParam) response;
		    logger.info("send Information info,then receive callback info.净空差错上报：\n"
			    + new ClearanceErrorReportingResponseTrans()
				    .fromMessage(errResponseParam));
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
