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
import com.deppon.oms.inteface.domain.SyncSalesDescExpandResponse;


/**
 * SyncSalesDescExpandResponse的转换类.
 * @author 273311
 * @date 2016-3-24 上午11:27:14
 */
public class SyncSalesDescExpandResponseTrans implements IMessageTransform<SyncSalesDescExpandResponse> {

	/** The Constant CLZZ. */
	private static final Class<SyncSalesDescExpandResponse> CLZZ =  SyncSalesDescExpandResponse.class;

	/** The log. */
	private static Log log = LogFactory.getLog(SyncSalesDescExpandResponse.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	
	/** 
	 * （响应转换方法）
	 * @author 273311
	 * @date 2016-3-24 上午11:29:37
	 */
	public SyncSalesDescExpandResponse toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		ByteArrayInputStream stream=null;
		try {
			try {
				stream = new ByteArrayInputStream(string.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncSalesDescExpandResponse> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}
	
	/** 
	 * （消息转换方法）
	 * @author 273311
	 * @date 2016-3-24 上午11:31:26
	 */
	public String fromMessage(SyncSalesDescExpandResponse value) throws ESBConvertException {
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
			JAXBElement<SyncSalesDescExpandResponse> element = new com.deppon.oms.inteface.domain.ObjectFactory().createSyncSalesDescExpandResponse(value);
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