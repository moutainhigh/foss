package com.deppon.foss.module.pickup.order.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncExpressCityResponseTrans;
import com.deppon.ows.inteface.domain.SyncExpressCityResponse;
 
/**
 * 发送订单【已派车】状态给官网
 * @author 219396-foss-daolin
 * @date 2015-01-07 上午09:03
 */
public class SendOrderStateToAppResultCallBackProcessor implements  ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendOrderStateToAppResultCallBackProcessor.class);
    
    @Override
    public void callback(Object response) throws ESBException {
    	if (null != response) {
    		SyncExpressCityResponse syncExpressCityResponse = (SyncExpressCityResponse) response;
			LOGGER.info("发送订单【已派车】状态给官网，收到回调信息：\n"+ new SyncExpressCityResponseTrans().fromMessage(syncExpressCityResponse));
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
