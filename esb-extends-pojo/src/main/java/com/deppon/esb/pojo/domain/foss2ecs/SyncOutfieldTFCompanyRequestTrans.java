package com.deppon.esb.pojo.domain.foss2ecs;

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

import com.deppon.ecs.inteface.domain.SyncOutfieldTFCompanyRequest;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;

/**
 * OutFieldInfoRequest转换类.
 * @author 187862-dujunhui
 * @date 2015-1-5 下午5:21:14
 */
public class SyncOutfieldTFCompanyRequestTrans implements IMessageTransform<SyncOutfieldTFCompanyRequest> {

	/** The Constant CLZZ. */
	private static final Class<SyncOutfieldTFCompanyRequest> CLZZ =  SyncOutfieldTFCompanyRequest.class;

	/** The log. */
	private static Log log = LogFactory.getLog(SyncOutfieldTFCompanyRequest.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	
	/** 
	 * 请求消息转换
	 * @author 187862-dujunhui
	 * @date 2015-1-5 下午5:21:14
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	public SyncOutfieldTFCompanyRequest toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncOutfieldTFCompanyRequest> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	/** 
	 * 消息转换
	 * @author 187862-dujunhui
	 * @date 2015-1-5 下午5:24:35
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	public String fromMessage(SyncOutfieldTFCompanyRequest value) throws ESBConvertException {
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
			JAXBElement<SyncOutfieldTFCompanyRequest> element = new com.deppon.ecs.inteface.domain.ObjectFactory().createSyncOutfieldTFCompanyRequest(value);
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

	/**
	 * Inits the context.
	 * 
	 * @return the jAXB context
	 */
	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(CLZZ);
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}