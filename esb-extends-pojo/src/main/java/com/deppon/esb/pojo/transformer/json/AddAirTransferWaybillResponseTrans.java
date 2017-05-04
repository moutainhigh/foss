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
import com.deppon.esb.inteface.domain.cubc.transferwaybill.AirTransferWaybillToCubcResponse;

public class AddAirTransferWaybillResponseTrans implements IMessageTransform<AirTransferWaybillToCubcResponse>{

	/** The log. */
	private static Log log = LogFactory.getLog(AirTransferWaybillToCubcResponse.class);
	
	


	
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public AirTransferWaybillToCubcResponse toMessage(String string) throws ESBConvertException {
		try {
			return mapper.readValue(string, AirTransferWaybillToCubcResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化AirTransferWaybillToCubcResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化AirTransferWaybillToCubcResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化AirTransferWaybillToCubcResponse时失败！", e);
		}
	}

	@Override
	public String fromMessage(AirTransferWaybillToCubcResponse value)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化AirTransferWaybillToCubcResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化AirTransferWaybillToCubcResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化AirTransferWaybillToCubcResponse时失败！", e);
		}
	}
}
