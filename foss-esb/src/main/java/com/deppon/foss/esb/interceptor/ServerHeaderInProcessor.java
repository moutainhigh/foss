package com.deppon.foss.esb.interceptor;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;

/**
 *  请求拦截器
 * @author qiancheng
 * 
 */
public  class ServerHeaderInProcessor extends AbstractSoapInterceptor {
	private Logger log = Logger.getLogger(getClass());
	private  static SAAJInInterceptor saa = new SAAJInInterceptor();
	public ServerHeaderInProcessor(){
		super(Phase.PRE_INVOKE);
	}	
	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		try {
				inMessageProcessor(message);
		} catch (Exception e) {
			log.error("error occur when process in soap header", e);
		}
	}
	/**
	 * 从soap 消息中获取esbHeader
	 * @param message
	 */
	protected void inMessageProcessor(SoapMessage message){
		  SOAPMessage mess = message.getContent(SOAPMessage.class);
	        if (mess == null) {
	            saa.handleMessage(message);
	            mess = message.getContent(SOAPMessage.class);
	        }
	        SOAPHeader head = null;
	        try {
	            head = mess.getSOAPHeader();
	        } catch (SOAPException e) {
	            e.printStackTrace();
	        }
	        if (head == null) {
	            return;
	        }
	        //处理接受到的soapHeader
	        WSHeaderHelper.processServerInHeader(head);
	}
}
