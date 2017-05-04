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

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.msg.SyncMsgResponse;

/**
 * SyncBigZoneRequestTrans的转换类.
 * @author 310854
 * @date 2016-4-6
 */
public class SyncMsgResponseTrans implements IMessageTransform<SyncMsgResponse> {

	/** The Constant CLZZ. */
	private static final Class<SyncMsgResponse> CLZZ =  SyncMsgResponse.class;

	/** The log. */
	private static Log log = LogFactory.getLog(SyncMsgResponseTrans.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	
	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）.
	 * 
	 * @param string
	 *            the string
	 * @return the sync pub bank acc response type
	 * @throws ESBConvertException
	 *             the eSB convert exception
	 * @author HuangHua
	 * @date 2012-12-25 下午5:54:52
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	public SyncMsgResponse toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncMsgResponse> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）.
	 * 
	 * @param value
	 *            the value
	 * @return the string
	 * @throws ESBConvertException
	 *             the eSB convert exception
	 * @author HuangHua
	 * @date 2012-12-25 下午5:54:52
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	public String fromMessage(SyncMsgResponse value) throws ESBConvertException {
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
			JAXBElement<SyncMsgResponse> element = new com.deppon.esb.inteface.domain.msg.ObjectFactory().createSyncMsgResponse(value);
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
