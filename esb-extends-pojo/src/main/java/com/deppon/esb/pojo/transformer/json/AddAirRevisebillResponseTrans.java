package com.deppon.esb.pojo.transformer.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.cubc.revisebill.AirRevisebillToCubcResponse;
import com.deppon.esb.inteface.domain.cubc.waybill.AirWaybillToCubcResponse;

public class AddAirRevisebillResponseTrans implements IMessageTransform<AirRevisebillToCubcResponse>{
	/** The Constant CLZZ. */
	private static final Class<AirRevisebillToCubcResponse> CLZZ =  AirRevisebillToCubcResponse.class;

	/** The log. */
	private static Log log = LogFactory.getLog(AirRevisebillToCubcResponse.class);
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public AirRevisebillToCubcResponse toMessage(String string) throws ESBConvertException {
		try {
			return mapper.readValue(string, AirRevisebillToCubcResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化AirWaybillToCubcResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化AirWaybillToCubcResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化AirWaybillToCubcResponse时失败！", e);
		}
	}

	@Override
	public String fromMessage(AirRevisebillToCubcResponse value)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化AirWaybillToCubcResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化AirWaybillToCubcResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化AirWaybillToCubcResponse时失败！", e);
		}
	}
}
