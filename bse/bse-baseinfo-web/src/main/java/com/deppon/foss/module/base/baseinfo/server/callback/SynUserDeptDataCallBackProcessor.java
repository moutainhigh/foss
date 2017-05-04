package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.ecs.inteface.domain.SyncUserDeptDataResponse;
import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncUserDeptDataResponseTrans;

public class SynUserDeptDataCallBackProcessor implements ICallBackProcess{

	private static final Logger LOGGER = LoggerFactory.getLogger(SynUserDeptDataCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncUserDeptDataResponse userDeptDataResponse = (SyncUserDeptDataResponse) response;
			LOGGER.info("send UserDeptData info,then receive callback info.同步FOSS数据权限信息，收到回调信息：\n"+ 
			   new SyncUserDeptDataResponseTrans().fromMessage(userDeptDataResponse));
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
