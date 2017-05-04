package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;
import java.util.regex.Pattern;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.foss.esb.inteface.domain.dopsigninfo.DealDopSignInfoResponse;
import com.deppon.fssc.inteface.domain.payment.DealComplainChangeFossResponse;

public class DealDopSignInfoResponseTrans implements IMessageTransform<DealDopSignInfoResponse> {
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();
	@Override
	public DealDopSignInfoResponse toMessage(String string)
			throws ESBConvertException {
		// TODO Auto-generated method stub
		DealDopSignInfoResponse result=null;
		try {
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			result = mapper.readValue(string, DealDopSignInfoResponse.class);
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
	public String fromMessage(DealDopSignInfoResponse message)
			throws ESBConvertException {
		// TODO Auto-generated method stub
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
