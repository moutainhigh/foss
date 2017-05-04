package com.deppon.esb.pojo.transformer.jaxb;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.vtsbill.VtsPayableFinanceBillRequest;

/**
 * VtsPayableFinanceBillRequest的转换类
 * 
 * @author  FOSS结算开发组 310970  caopeng
 * 
 */
public class VtsPayableFinanceBillRequestTrans implements IMessageTransform<VtsPayableFinanceBillRequest> {

	private static final Class<VtsPayableFinanceBillRequest> CLZZ =  VtsPayableFinanceBillRequest.class;

	private static Log log = LogFactory.getLog(VtsPayableFinanceBillRequest.class);
	
	private static JAXBContext context = initContext();
	
	public VtsPayableFinanceBillRequest toMessage(String string) throws ESBConvertException {
		System.out.println("JMS异步接口调试成功！入口1...@310970 caoepng....string字符串为："+string);
		if(context == null){
			System.out.println("JMS异步接口调试成功！入口循环初始化..");
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		System.out.println("JMS异步接口调试成功！入口2..");
		try {
			System.out.println("JMS异步接口调试成功！入口3..");
			//在这里如果客户端不加上命名空间地址，那么我服务端就统一加上
/*			string = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
					"<VtsPayableFinanceBillRequest xmlns=\"http://www.deppon.com/esb/inteface/domain/vtsbill\">"+
						"<waybillNo>e164a2f2-92de-4147-ac3a-94350dff4e4d</waybillNo>"+
					"</VtsPayableFinanceBillRequest>";*/
			ByteArrayInputStream stream=null;
			try {
				stream = new ByteArrayInputStream(string.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Unmarshaller unmarshaller = context.createUnmarshaller();
			System.out.println("JMS异步接口调试成功！入口3.1..");
			JAXBElement<VtsPayableFinanceBillRequest> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			System.out.println("JMS异步接口调试成功！入口4.."); 
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	public String fromMessage(VtsPayableFinanceBillRequest value) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		if (value == null) {
			return null;
		}
		try {
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
			JAXBElement<VtsPayableFinanceBillRequest> element = new com.deppon.esb.inteface.domain.vtsbill.ObjectFactory().createVtsPayableFinanceBillRequest(value);
			marshaller.marshal(element, stringWriter);
			stringWriter.flush();
			return stringWriter.toString();
		} catch (PropertyException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		} catch (FactoryConfigurationError e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		} catch (JAXBException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		}
	}
	

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(CLZZ);
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
