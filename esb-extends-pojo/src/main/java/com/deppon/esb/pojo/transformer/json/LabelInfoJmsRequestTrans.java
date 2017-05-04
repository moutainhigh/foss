package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.foss2dop.LabelInfoJmsRequest;

public class LabelInfoJmsRequestTrans implements IMessageTransform<LabelInfoJmsRequest>{

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String fromMessage(LabelInfoJmsRequest request)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(request);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化LabelInfoJmsRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化LabelInfoJmsRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化LabelInfoJmsRequest时失败！", e);
		}
	}

	@Override
	public LabelInfoJmsRequest toMessage(String s)
			throws ESBConvertException {
		LabelInfoJmsRequest request = null;
		try {
			request = mapper.readValue(s, LabelInfoJmsRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化LabelInfoJmsRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化LabelInfoJmsRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化LabelInfoJmsRequest时失败！", e);
		}
		return request;
	}
}

