package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.foss.BusinessDeptres;
import com.deppon.esb.pojo.transformer.jaxb.BusinessDeptresTrans;
/**
 * 用来存储营业部数据更新后调用FIN的"发票项目"的结果回调同步信息到数据库
 * 
 * @author 130566-ZengJunfan
 * @date 2014-6-19 下午6:31:40
 */
public class SalesInfoToFinResultCallBackProcessor implements ICallBackProcess {
	private static final Logger LOGGER = LoggerFactory
		    .getLogger(SalesInfoToFinResultCallBackProcessor.class);
	@Override
	public void callback(Object response) throws ESBException {
		if(null !=response){
			BusinessDeptres res =(BusinessDeptres)response;
			LOGGER.info("send SaleDepartment info,then receive callback info.同步营业部信息，收到回调信息：\n"
			+new BusinessDeptresTrans().fromMessage(res));
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
