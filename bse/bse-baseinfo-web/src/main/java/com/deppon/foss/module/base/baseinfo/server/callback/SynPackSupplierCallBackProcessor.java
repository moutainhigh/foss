package com.deppon.foss.module.base.baseinfo.server.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.cubc.inteface.domain.SyncPackSupplierResponse;
import com.deppon.dpap.esb.mqc.core.exception.ESBException;
import com.deppon.dpap.esb.mqc.core.process.ICallBackProcess;
import com.deppon.dpap.esb.mqc.core.process.ErrorResponse;
import com.deppon.esb.pojo.transformer.jaxb.SyncPackSupplierResponseTrans;

public class SynPackSupplierCallBackProcessor implements ICallBackProcess {

	private static final Logger LOGGER = LoggerFactory.getLogger(SynPackSupplierCallBackProcessor.class);

	@Override
	public void callback(Object response) throws ESBException {
		// TODO Auto-generated method stub
		if (null != response) {
			SyncPackSupplierResponse syncPackSupplierResponse = (SyncPackSupplierResponse) response;
			try {
				LOGGER.info("send AccSalesDept info,then receive callback info.同步接驳点与营业部映射关系到CUBC，收到回调信息：\n"+ 
				   new SyncPackSupplierResponseTrans().fromMessage(syncPackSupplierResponse));
			} catch (ESBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void errorHandler(Object errorResponse) throws ESBException {

	ErrorResponse info = (ErrorResponse) errorResponse;
	StringBuilder nb = new StringBuilder();
	nb.append(info.getBusinessId()).append(",描述1：")
	.append(info.getBusinessDesc1()).append(",描述2：")
	.append(info.getBusinessDesc2()).append(",描述3：")
	.append(info.getBusinessDesc3());

	LOGGER.error("ESB处理错误", nb.toString());
	}


}
