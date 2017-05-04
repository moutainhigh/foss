package com.deppon.foss.esb.interceptor;

import java.util.List;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;

/**
 *  响应拦截器
 * @author qiancheng
 * 
 */
public  class ServerHeaderOutProcessor extends AbstractSoapInterceptor {
	public ServerHeaderOutProcessor(){
		super(Phase.WRITE);
	}
	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		try {
			List<Header> headers = message.getHeaders();
			WSHeaderHelper.setResultCode(WSHeaderHelper.RESULT_CODE_STATUS_SUCCESS);
			Header header = WSHeaderHelper.createServerOutHeader();
			headers.add(header);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handleFault(SoapMessage message) {
		try {
			List<Header> headers = message.getHeaders();
			WSHeaderHelper.setResultCode(WSHeaderHelper.RESULT_CODE_STATUS_FAIL);
			Header header = WSHeaderHelper.createServerOutHeader();
			headers.add(header);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
