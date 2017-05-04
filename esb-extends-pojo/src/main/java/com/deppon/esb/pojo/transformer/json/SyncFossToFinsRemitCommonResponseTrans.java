package com.deppon.esb.pojo.transformer.json;


import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.fins.FossToFinsRemitCommonResponse;


public class SyncFossToFinsRemitCommonResponseTrans  implements IMessageTransform<FossToFinsRemitCommonResponse>  {
	
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public FossToFinsRemitCommonResponse toMessage(String string)
			throws ESBConvertException {
		FossToFinsRemitCommonResponse result = null;
		try {
			result = mapper.readValue(string, FossToFinsRemitCommonResponse.class);
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
	public String fromMessage(FossToFinsRemitCommonResponse message)
			throws ESBConvertException {
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

