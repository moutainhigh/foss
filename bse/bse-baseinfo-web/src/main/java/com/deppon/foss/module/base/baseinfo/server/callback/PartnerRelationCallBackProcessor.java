package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.json.SyncPartnerRelationResponseTrans;
import com.deppon.ows.inteface.domain.relation.SyncPartnerRelationInfoResponse;
/**
 * 
 * 同步网点映射关系信息新接口
 * @author 308861 
 * @date 2016-9-7 上午9:16:13
 * @since
 * @version
 */
public class PartnerRelationCallBackProcessor implements ICallBackProcess{

	 private static final Logger LOGGER = LoggerFactory
			    .getLogger(PartnerRelationCallBackProcessor.class);
	 
	@Override
	public void callback(Object response) throws ESBException {
		if (null != response) {
		    SyncPartnerRelationInfoResponse syncPartnerRelationresponse = (SyncPartnerRelationInfoResponse) response;
		    LOGGER.info("send PartnerRelation info,then receive callback info.同步网点映射关系，收到回调信息：\n"
			    + new SyncPartnerRelationResponseTrans()
				    .fromMessage(syncPartnerRelationresponse));
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
