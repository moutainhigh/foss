package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.oms.inteface.domain.SynContrabandErrorsResponse;

public class SynContrabandErrorsResponseTrans implements IMessageTransform<SynContrabandErrorsResponse> {
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public SynContrabandErrorsResponse toMessage(String string) throws ESBConvertException {
		SynContrabandErrorsResponse result = null;
		try {
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			result = mapper.readValue(string, SynContrabandErrorsResponse.class);
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
	public String fromMessage(SynContrabandErrorsResponse message) throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化Result时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化Result时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化Result时失败！", e);
		}
	}

}
