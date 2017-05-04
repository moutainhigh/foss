package com.deppon.esb.pojo.transformer.jaxb;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.vtsbill.ObjectFactory;
import com.deppon.esb.inteface.domain.vtsbill.VtsPayableFinanceBillResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * VtsPayableFinanceBillResponse的转换类.
 * 
 * @author  310970 
 */
public class VtsPayableFinanceBillResponseTrans implements IMessageTransform<VtsPayableFinanceBillResponse> {

	/** The Constant CLZZ. */
	private static final Class<VtsPayableFinanceBillResponse> CLZZ =  VtsPayableFinanceBillResponse.class;

	/** The log. */
	private static Log log = LogFactory.getLog(VtsPayableFinanceBillResponse.class);

	/** The context. */
	private static JAXBContext context = initContext();

	/**
	 * @author 310970  caopeng
	 * @date 2016-5-15
	 */
	public VtsPayableFinanceBillResponse toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<VtsPayableFinanceBillResponse> element = unmarshaller.unmarshal(new StreamSource(stream), CLZZ);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + CLZZ.getName() + "时失败！", e);
		}
	}

	/**
	 *@author 310970  caopeng
	 *@date 2016-5-15
	 */
	public String fromMessage(VtsPayableFinanceBillResponse value) throws ESBConvertException {
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
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			JAXBElement<VtsPayableFinanceBillResponse> element = new com.deppon.esb.inteface.domain.vtsbill.ObjectFactory().createVtsPayableFinanceBillResponse(value);
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
