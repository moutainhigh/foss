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
import com.deppon.esb.inteface.domain.vtsbill.VtsWaybillFinanceBillRequest;

/**
 * VtsWaybillFinanceBillRequest的转换类
 * 
 * @author  FOSS结算开发组 218392 张永雪
 * 
 */
public class VtsWaybillFinanceBillRequestTrans implements IMessageTransform<VtsWaybillFinanceBillRequest> {

	private static final Class<VtsWaybillFinanceBillRequest> CLZZ =  VtsWaybillFinanceBillRequest.class;

	private static Log log = LogFactory.getLog(VtsWaybillFinanceBillRequest.class);
	
	private static JAXBContext context = initContext();
	
	public VtsWaybillFinanceBillRequest toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			//在这里如果客户端不加上命名空间地址，那么我服务端就统一加上
/*			string = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"+
					"<VtsWaybillFinanceBillRequest xmlns=\"http://www.deppon.com/esb/inteface/domain/vtsbill\">"+
						"<waybillNo>e164a2f2-92de-4147-ac3a-94350dff4e4d</waybillNo>"+
					"</VtsWaybillFinanceBillRequest>";*/
			ByteArrayInputStream stream = null;//这个是为了VTS那边使用的DPAP和FOSS的不匹配
			try {
				stream = new ByteArrayInputStream(string.getBytes("UTF-8"));//括号中我自己加上的UTF-8，目的是为了VTS传输过来的时候，汉字解析不了报错
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<VtsWaybillFinanceBillRequest> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	public String fromMessage(VtsWaybillFinanceBillRequest value) throws ESBConvertException {
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
			JAXBElement<VtsWaybillFinanceBillRequest> element = new com.deppon.esb.inteface.domain.vtsbill.ObjectFactory().createVtsWaybillFinanceBillRequest(value);
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
