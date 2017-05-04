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
import com.deppon.foss.inteface.domain.usermanagements.SendLinesResponse;

public class SendLinesResponseTrans implements IMessageTransform<SendLinesResponse> {
	/** The Constant CLZZ. */
	private static final Class<SendLinesResponse> CLZZ =SendLinesResponse.class;
	/** The log. */
	private static Log log = LogFactory.getLog(SendLinesResponse.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	/**
	 * 
	 *<p></p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-11下午3:56:28
	 *@see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)	
	 *@param string
	 *@return
	 *@throws ESBConvertException
	 */
	@Override
	public SendLinesResponse toMessage(String string) throws ESBConvertException {
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
			JAXBElement<SendLinesResponse> element = unmarshaller.unmarshal(new StreamSource(stream),CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			log.debug(e.getMessage(),e);
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
		
	}
	/**
	 * 
	 *<p></p>
	 *@author 130566 -ZengJunfan
	 *@date 2014-12-12上午9:38:40
	 *@see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)	
	 *@param message
	 *@return
	 *@throws ESBConvertException
	 */
	@Override
	public String fromMessage(SendLinesResponse message)
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
			JAXBElement<SendLinesResponse> element = new com.deppon.foss.inteface.domain.usermanagements.ObjectFactory().createSendLinesResponse(message);
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
