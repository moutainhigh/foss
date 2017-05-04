package com.deppon.foss.pojo.transformer.jaxb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

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
import com.deppon.esb.inteface.domain.crm.ObjectFactory;
import com.deppon.esb.inteface.domain.crm.WxdbSyncResponse;

/**
 * WxdbSyncResponse转换类
 * @author 198771
 * @date 2014-11-24 
 */

public class WxdbSyncResponseTrans implements IMessageTransform<WxdbSyncResponse>{
	
	private static final Class<WxdbSyncResponse> CLZZ = WxdbSyncResponse.class;
	
	private static Log log = LogFactory.getLog(WxdbSyncResponse.class);
	
	private JAXBContext context = initContext();

	@Override
	public String fromMessage(WxdbSyncResponse value) throws ESBConvertException {
		if(context==null){
			context = initContext();
			if(context == null){
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		if(value==null){
			return null;
		}
		try {
			StringWriter writer = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
			ObjectFactory factory = new ObjectFactory();
			JAXBElement<WxdbSyncResponse> element = factory.createWxdbSyncResponse(value);
			marshaller.marshal(element, writer);
			writer.flush();
			String str = writer.toString();
			writer.close();
			return str;
		} catch (PropertyException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		} catch (FactoryConfigurationError e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		} catch (JAXBException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName() + "时失败！", e);
		}
	}


	@Override
	public WxdbSyncResponse toMessage(String message) throws ESBConvertException {
		if(context==null){
			context = initContext();
			if(context==null){
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(message.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<WxdbSyncResponse> element = unmarshaller.unmarshal(new StreamSource(inputStream),CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}

	private JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(CLZZ);
		} catch (JAXBException e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
}
