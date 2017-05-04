package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.deppon.cubc.module.foss.shared.domain.ResponseExternalBillDto;
import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;


/**
 * 
 * @author 310248
 * 
 */
public class ResponseExternalBillDtoTrans  implements IMessageTransform<ResponseExternalBillDto>  {
	private static ObjectMapper mapper = new ObjectMapper();

@Override
public String fromMessage(ResponseExternalBillDto response)
		throws ESBConvertException {
	try {
		return mapper.writeValueAsString(response);
	} catch (JsonGenerationException e) {
		throw new ESBConvertException("序列化ResponseExternalBillDto时失败！", e);
	} catch (JsonMappingException e) {
		throw new ESBConvertException("序列化ResponseExternalBillDto时失败！", e);
	} catch (IOException e) {
		throw new ESBConvertException("序列化ResponseExternalBillDto时失败！", e);
	}
}

@Override
public ResponseExternalBillDto toMessage(String s)
		throws ESBConvertException {
	ResponseExternalBillDto response = null;
	try {
		response = mapper.readValue(s, ResponseExternalBillDto.class);
	} catch (JsonParseException e) {
		throw new ESBConvertException("反序列化ResponseExternalBillDto时失败！", e);
	} catch (JsonMappingException e) {
		throw new ESBConvertException("反序列化ResponseExternalBillDto时失败！", e);
	} catch (IOException e) {
		throw new ESBConvertException("反序列化ResponseExternalBillDto时失败！", e);
	}
	return response;
}}
