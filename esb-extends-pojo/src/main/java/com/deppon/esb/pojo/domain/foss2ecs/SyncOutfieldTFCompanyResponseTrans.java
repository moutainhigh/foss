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

import com.deppon.ecs.inteface.domain.SyncOutfieldTFCompanyResponse;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;

/**
 * OutfieldTFCompanyResponse转换类.
 * 
 * @author 313353-qiupeng
 * @date 2016-4-6 上午11:18:25
 */
public class SyncOutfieldTFCompanyResponseTrans implements
		IMessageTransform<SyncOutfieldTFCompanyResponse> {

	/** The Constant CLZZ. */
	private static final Class<SyncOutfieldTFCompanyResponse> CLZZ = SyncOutfieldTFCompanyResponse.class;

	/** The log. */
	private static Log log = LogFactory
			.getLog(SyncOutfieldTFCompanyResponse.class);

	/** The context. */
	private static JAXBContext context = initContext();

	/**
	 * 响应转换方法 OutfieldTFCompanyResponse转换类.
	 * 
	 * @author 313353-qiupeng
	 * @date 2016-4-6 上午11:18:25
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	public SyncOutfieldTFCompanyResponse toMessage(String string)
			throws ESBConvertException {
		if (context == null) {
			initContext();// 再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(
					string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncOutfieldTFCompanyResponse> element = unmarshaller
					.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}

	/**
	 * 消息转换方法
	 * 
	 * @author 313353-qiupeng
	 * @date 2016-4-6 上午11:18:25
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	public String fromMessage(SyncOutfieldTFCompanyResponse value)
			throws ESBConvertException {
		if (context == null) {
			initContext();// 再次尝试一次
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
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING,
					"UTF-8");
			JAXBElement<SyncOutfieldTFCompanyResponse> element = new com.deppon.ecs.inteface.domain.ObjectFactory()
					.createSyncOutfieldTFCompanyResponse(value);
			marshaller.marshal(element, stringWriter);
			stringWriter.flush();
			return stringWriter.toString();
		} catch (PropertyException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName()
					+ "时失败！", e);
		} catch (FactoryConfigurationError e) {
			throw new ESBConvertException("序列化" + value.getClass().getName()
					+ "时失败！", e);
		} catch (JAXBException e) {
			throw new ESBConvertException("序列化" + value.getClass().getName()
					+ "时失败！", e);
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