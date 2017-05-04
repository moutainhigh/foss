package com.deppon.esb.pojo.domain.foss2dop;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.dop.SignMessageResponse;

public class SignMessageResponseTrans implements IMessageTransform<SignMessageResponse> {

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public SignMessageResponse toMessage(String string)
			throws ESBConvertException {
		SignMessageResponse result = null;
		try {
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			result = mapper.readValue(string, SignMessageResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		}
		return result;
	}

	@Override
	public String fromMessage(SignMessageResponse message)
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
