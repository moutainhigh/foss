package com.deppon.esb.pojo.transformer.jaxb;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.foss.inteface.domain.usermanagement.SendLineRequest;
/**
 * SendLineRequest 的转换类
 * @author 130566
 *
 */
public class SendLineRequestTrans implements IMessageTransform<SendLineRequest> {
	/** The Constant CLZZ. */
	private static final Class<SendLineRequest> CLZZ =SendLineRequest.class;
	/** The log. */
	private static Log log = LogFactory.getLog(SendLineRequest.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	
	/**
	 *<p></p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-11下午1:50:27
	 *@see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)	
	 *@param string
	 *@return
	 *@throws ESBConvertException
	 */
	@Override
	public SendLineRequest toMessage(String string) throws ESBConvertException {
		if(null ==context){
			//再初始化一遍
			initContext();
			if(null ==context){
				throw new ESBConvertException("初始化JAXBContext失败");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller =context.createUnmarshaller();
			JAXBElement<SendLineRequest> element = unmarshaller.unmarshal(new StreamSource(stream),CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			log.debug(e.getMessage(),e);
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	/**
	 *<p></p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-11下午1:50:33
	 *@see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)	
	 *@param message
	 *@return
	 *@throws ESBConvertException
	 */
	@Override
	public String fromMessage(SendLineRequest message)
			throws ESBConvertException {
		if(null ==context){
			//再初始化一遍
			initContext();
			if(null ==context){
				throw new ESBConvertException("初始化JAXBContext失败");
			}
		}
		if(null ==message){
			return null;
		}
		try {
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
			JAXBElement<SendLineRequest> element = new com.deppon.foss.inteface.domain.usermanagement.ObjectFactory().createSendLineRequest(message);
			marshaller.marshal(element, stringWriter);
			stringWriter.flush();
			return stringWriter.toString();
		} catch (JAXBException e) {
			throw new ESBConvertException("序列化" + message.getClass().getName() + "时失败！", e);
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
