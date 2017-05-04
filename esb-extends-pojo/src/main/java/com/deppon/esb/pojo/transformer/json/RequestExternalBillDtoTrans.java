package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.cubc.module.foss.shared.domain.RequestExternalBillDto;
import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;


/**
 * RequestExternalBillDtoTrans的转换类.
 * 
 */
public class RequestExternalBillDtoTrans  implements IMessageTransform<RequestExternalBillDto>  {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public RequestExternalBillDto toMessage(String s)
			throws ESBConvertException {
		RequestExternalBillDto request = null;
		try {
			request = mapper.readValue(s, RequestExternalBillDto.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化RequestExternalBillDto时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化RequestExternalBillDto时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化RequestExternalBillDto时失败！", e);
		}
		return request;
	}

	@Override
	public String fromMessage(RequestExternalBillDto request)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(request);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化RequestExternalBillDto时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化RequestExternalBillDto时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化RequestExternalBillDto时失败！", e);
		}
	}
}
