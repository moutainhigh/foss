/*
package com.deppon.foss.module.transfer.scheduling.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.esb.pojo.domain.foss2oms.SyncTransitGoodsResponse;
import com.deppon.esb.pojo.transformer.json.SyncTransitGoodsResponseTrans;
 
*//**
 * 同步转货订单到OMS回调
 * @author 332153-foss-zm
 * @date 2016年11月22日14:00:33
 *//*
public class SyncTransitGoodsStatusCallBack implements  ICallBackProcess {
    private static final Logger logger = LoggerFactory.getLogger(SyncTransitGoodsStatusCallBack.class);
    
    @Override
    public void callback(Object response) throws ESBException {
    	logger.info("同步转货订单到OMS回调 start");
    	if (null != response) {
    		SyncTransitGoodsResponse syncTransitGoodsResponse = (SyncTransitGoodsResponse) response;
    		logger.info("send dictirct info,then receive callback info.同步转货订单到OMS，收到回调信息：\n"+ new SyncTransitGoodsResponseTrans().fromMessage(syncTransitGoodsResponse));
		}
    	logger.info("同步转货订单到OMS回调 end");
    }

    @Override
    public void errorHandler(Object errorResponse) throws ESBException {
		logger.error("同步转货订单到OMS回调ESB处理错误");
		ErrorResponse info = (ErrorResponse)errorResponse;
		StringBuilder sb =new StringBuilder();
		sb.append(info.getBusinessId()).append(",描述1：")
			.append(info.getBusinessDesc1()).append(",描述2：")
			.append(info.getBusinessDesc2()).append(",描述3：")
			.append(info.getBusinessDesc3());
		logger.error("同步转货订单到OMS回调ESB处理错误", sb.toString());
    }
}
*/