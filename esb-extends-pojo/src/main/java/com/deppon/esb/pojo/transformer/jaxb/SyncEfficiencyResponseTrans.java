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

import com.deppon.ecs.inteface.domain.SyncEfficiencyResponse;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;

/**
 * SyncEfficiencyResponse的转换类.
 * @author 310854
 * @date 2016-4-6
 */
public class SyncEfficiencyResponseTrans implements IMessageTransform<SyncEfficiencyResponse> {

	/** The Constant CLZZ. */
	private static final Class<SyncEfficiencyResponse> CLZZ =  SyncEfficiencyResponse.class;

	/** The log. */
	private static Log log = LogFactory.getLog(SyncEfficiencyResponseTrans.class);
	
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
	public SyncEfficiencyResponse toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<SyncEfficiencyResponse> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
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
	public String fromMessage(SyncEfficiencyResponse value) throws ESBConvertException {
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
			JAXBElement<SyncEfficiencyResponse> element = new com.deppon.ecs.inteface.domain.ObjectFactory().createSyncEfficiencyResponse(value);
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
