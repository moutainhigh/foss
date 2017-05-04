package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.esb.core.exception.ESBException;
import com.deppon.esb.core.process.ICallBackProcess;
import com.deppon.esb.pojo.transformer.jaxb.SyncBigcusDeliveryAddressResponseTrans;
import com.deppon.oms.inteface.domain.SyncBigcusDeliveryAddressResponse;

/**
 * 零担大客户派送地址库回调
 * @author 232607
 * @date 2016-6-20
 */
public class SynBigcusCallBackProcessor implements ICallBackProcess{

	private static final Logger LOGGER = LoggerFactory.getLogger(SynBigcusCallBackProcessor.class);
	
	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncBigcusDeliveryAddressResponse syncBigcusDeliveryAddressResponse = (SyncBigcusDeliveryAddressResponse) response;
			LOGGER.info("send BigcusDeliveryAddress info,then receive callback info.同步零担大客户派送地址库，收到回调信息：\n"+ 
			   new SyncBigcusDeliveryAddressResponseTrans().fromMessage(syncBigcusDeliveryAddressResponse));
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {
		// TODO Auto-generated method stub
		if(null != errorResponse){
			SyncBigcusDeliveryAddressResponse info = (SyncBigcusDeliveryAddressResponse)errorResponse;
			StringBuilder nb =new StringBuilder();
			nb.append("是否成功：").append(info.getIfSuccess()).append(",异常内容：")
				.append(info.getFailReason());

			LOGGER.error("ESB处理错误", nb.toString());
		}
	}

}
