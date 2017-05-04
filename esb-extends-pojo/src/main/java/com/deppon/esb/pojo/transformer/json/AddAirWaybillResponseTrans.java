package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.cubc.waybill.AirWaybillToCubcResponse;

public class AddAirWaybillResponseTrans implements IMessageTransform<AirWaybillToCubcResponse>{

	/** The log. */
	private static Log log = LogFactory.getLog(AirWaybillToCubcResponse.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public AirWaybillToCubcResponse toMessage(String string) throws ESBConvertException {
		try {
			return mapper.readValue(string, AirWaybillToCubcResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化AirWaybillToCubcResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化AirWaybillToCubcResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化AirWaybillToCubcResponse时失败！", e);
		}
	}
	
	@Override
	public String fromMessage(AirWaybillToCubcResponse value)
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
