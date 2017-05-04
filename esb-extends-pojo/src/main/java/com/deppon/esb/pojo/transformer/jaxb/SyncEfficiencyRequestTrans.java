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

import com.deppon.ecs.inteface.domain.SyncEfficiencyRequest;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;

/**
 * SyncEfficiencyRequest的转换类.
 * @author 310854
 * @date 2016-4-6
 */
public class SyncEfficiencyRequestTrans implements IMessageTransform<SyncEfficiencyRequest>{

	/** 常量定义 CLZZ. */
	private static final Class<SyncEfficiencyRequest> CLZZ =  SyncEfficiencyRequest.class;

	/** The log. */
	private static Log log = LogFactory.getLog(SyncEfficiencyRequestTrans.class);
	
	/** The context. */
	private static JAXBContext context = initContext();
	
	@Override
	public String fromMessage(SyncEfficiencyRequest value)
			throws ESBConvertException {
		// TODO Auto-generated method stub
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
			JAXBElement<SyncEfficiencyRequest> element = new com.deppon.ecs.inteface.domain.ObjectFactory().createSyncEfficiencyRequest(value);
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

	@Override
	public SyncEfficiencyRequest toMessage(String string)
			throws ESBConvertException {
		// TODO Auto-generated method stub
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncEfficiencyRequest> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}

	/**
	 * 初始化
	 * @author 310854
	 * @date 2016-3-23上午11:12:46
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
