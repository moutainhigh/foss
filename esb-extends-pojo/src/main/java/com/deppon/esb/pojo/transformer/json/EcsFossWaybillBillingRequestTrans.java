package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.ecs.EcsFossWaybillBillingRequest;

/**
 * @author foss-231434
 * @date 2016-05-30
 */
public class EcsFossWaybillBillingRequestTrans implements IMessageTransform<EcsFossWaybillBillingRequest> {

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public EcsFossWaybillBillingRequest toMessage(String string)
			throws ESBConvertException {
		EcsFossWaybillBillingRequest result = null;
		try{
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			String jsonStr = mapper.readValue(string, String.class);
			result = mapper.readValue(jsonStr, EcsFossWaybillBillingRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化Result时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化Result时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化Result时失败！", e);
		}
		return result;
	}

	@Override
	public String fromMessage(EcsFossWaybillBillingRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化SmsResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化SmsResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化SmsResult时失败！", e);
		}
	}

	
}
