package com.deppon.esb.pojo.transformer.jaxb;

import java.io.ByteArrayInputStream;
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
import com.deppon.ows.inteface.domain.SyncExpressCityResponse;

/**
 * SyncExpressCityResponse的转换类
 * 
 * @author HuangHua
 * 
 */
public class SyncExpressCityResponseTrans implements IMessageTransform<SyncExpressCityResponse> {

	private static final Class<SyncExpressCityResponse> CLZZ =  SyncExpressCityResponse.class;

	private static Log log = LogFactory.getLog(SyncExpressCityResponse.class);
	
	private static JAXBContext context = initContext();
	
	public SyncExpressCityResponse toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncExpressCityResponse> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	public String fromMessage(SyncExpressCityResponse value) throws ESBConvertException {
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
			JAXBElement<SyncExpressCityResponse> element = new com.deppon.ows.inteface.domain.ObjectFactory().createSyncExpressCityResponse(value);
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
