package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.workflow.GoodsDiscardRequest;

/**
 * The Class GoodsDiscardRequestTrans.
 */
public class GoodsDiscardRequestTrans implements IMessageTransform<GoodsDiscardRequest> {
	
	
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author HuangHua
	 * @date 2012-12-25 下午5:54:42
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	@Override
	public GoodsDiscardRequest toMessage(String string) throws ESBConvertException {
		GoodsDiscardRequest result = null;
		try {
			result = mapper.readValue(string, GoodsDiscardRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化ThrowfreightRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化ThrowfreightRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化ThrowfreightRequest时失败！", e);
		}
		return result;
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author HuangHua
	 * @date 2012-12-25 下午5:54:42
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	@Override
	public String fromMessage(GoodsDiscardRequest message) throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化ThrowfreightRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化ThrowfreightRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化ThrowfreightRequest时失败！", e);
		}
	}
	
	

	/*private static Log log = LogFactory.getLog(GoodsDiscardRequest.class);
	
	private static final Class<GoodsDiscardRequest> clzz =  GoodsDiscardRequest.class;
	private static JAXBContext context = initContext();
	
	public GoodsDiscardRequest toMessage(String string) throws ESBConvertException {
		if(context == null){
			initContext();//再次尝试一次
			if (context == null) {
				throw new ESBConvertException("初始化JAXBContext失败！");
			}
		}
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(string.getBytes());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<GoodsDiscardRequest> element = unmarshaller.unmarshal(new StreamSource(stream), clzz);
			return element.getValue();
		} catch (JAXBException e) {
			throw new ESBConvertException("反序列化" + clzz.getName() + "时失败！", e);
		}
	}
	
	public String fromMessage(GoodsDiscardRequest value) throws ESBConvertException {
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
			JAXBElement<GoodsDiscardRequest> element = new com.deppon.esb.inteface.domain.workflow.ObjectFactory().createGoodsDiscardRequest(value);
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
		JAXBContext context = null;
		try {
			context = JAXBContext.newInstance(clzz);
		} catch (JAXBException e) {
			log.error(e.getMessage(), e);
		}
		return context;
	}
*/
}
