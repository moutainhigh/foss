package com.deppon.foss.module.pickup.order.server.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*mport com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ErrorResponse;
import com.deppon.esb.core.process.ICallBackProcess;*/



import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.esb.inteface.domain.oms.CourierSend2OmsResponse;
import com.deppon.esb.pojo.transformer.jaxb.CourierSendResponseTrans;
 
/**
 * 发送pda签到给oms
 * @author 276935-foss-shuaishuang
 * @date 2016-06-14 上午11:03
 */
public class SendModifySignInfoResultCallBackProcessor implements  ICallBackProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendModifySignInfoResultCallBackProcessor.class);
    
    @Override
    public void callback(Object response) throws ESBException {
    	if (null != response) {
    		CourierSend2OmsResponse courierSend2OmsResponse = (CourierSend2OmsResponse) response;
			LOGGER.info("发送PDA签到信息给oms，收到回调信息：\n"+ new CourierSendResponseTrans().fromMessage(courierSend2OmsResponse));
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
