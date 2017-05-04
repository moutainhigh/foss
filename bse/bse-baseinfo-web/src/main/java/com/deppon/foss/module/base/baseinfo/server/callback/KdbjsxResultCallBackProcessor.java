package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.crm.ExpressInsuredLimitResponse;
import com.deppon.esb.pojo.transformer.jaxb.KdbjsxResponseTrans;


/**
 *  快递保价上限的结果回调过程
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2015-6-23 下午2:01:27,content:TODO </p>
 * @author 232607 
 * @date 2015-6-23 下午2:01:27
 * @since
 * @version
 */
public class KdbjsxResultCallBackProcessor implements ICallBackProcess{
	private static final Logger logger = LoggerFactory.getLogger(KdbjsxResultCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {
		if (null != response) {
			ExpressInsuredLimitResponse kdbjsxResponse = (ExpressInsuredLimitResponse) response;
		    logger.info("send Kdps info,then receive callback info.同步快递派送区域信息到crm，收到回调信息：\n"
			    + new KdbjsxResponseTrans()
				    .fromMessage(kdbjsxResponse));
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
