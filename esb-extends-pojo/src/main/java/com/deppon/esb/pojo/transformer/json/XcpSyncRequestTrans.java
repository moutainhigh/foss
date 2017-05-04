package com.deppon.esb.pojo.transformer.json;

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
import com.deppon.esb.inteface.domain.crm.XcpSyncRequest;

/**
 * 新产品客户协议请求转换类
 * @author 198771
 *
 */
public class XcpSyncRequestTrans implements IMessageTransform<XcpSyncRequest>{
	/** The Constant CLZZ. */
	private static final Class<XcpSyncRequest> CLZZ =  XcpSyncRequest.class;

	/** The log. */
	private static Log log = LogFactory.getLog(XcpSyncRequestTrans.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	@Override
	public XcpSyncRequest toMessage(String string) throws ESBConvertException {
		if(context!=null){
			context = initContext();
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<XcpSyncRequest> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}

	@Override
	public String fromMessage(XcpSyncRequest value)
			throws ESBConvertException {
		if(context!=null){
			context = initContext();
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		if(value==null){
			return null;
		}
		try {
			StringWriter stringWriter = new StringWriter();
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
			JAXBElement<XcpSyncRequest> element = new com.deppon.esb.inteface.domain.crm.ObjectFactory().createXcpSyncRequest(value);
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
