package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressDeptRelationResponseTrans;
import com.deppon.ows.inteface.domain.SyncExpressDeptRelationResponse;

/**
 * 接收营业部和快递点部传递消息返回结果
 * 
 * @author WangPeng
 * @date   2013-08-08 5:45 PM
 *
 */
public class ExpressAndSalesDeptResultCallBackProcessor implements
		ICallBackProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressAndSalesDeptResultCallBackProcessor.class);


	@Override
	public void callback(Object response) throws ESBException {

		if (null != response) {
    		SyncExpressDeptRelationResponse syncExpressAndSalesDeptResponse = (SyncExpressDeptRelationResponse) response;
			LOGGER.info("send ExpressPartSalesDeptRelation to Crm System ：发送营业部和快递点部的消息到Crm,回调信息反馈  \n"+ new SyncExpressDeptRelationResponseTrans().fromMessage(syncExpressAndSalesDeptResponse));
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

		LOGGER.error("ESB处理错误", sb.toString());
	}
}
