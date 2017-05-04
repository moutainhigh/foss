package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.foss2dop.LabelInfoJmsResponse;

public class LabelInfoJmsResponseTrans implements IMessageTransform<LabelInfoJmsResponse>{

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String fromMessage(LabelInfoJmsResponse response)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(response);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化OwsDopLabelInfoJmsRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化OwsDopLabelInfoJmsRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化OwsDopLabelInfoJmsRequest时失败！", e);
		}
	}

	@Override
	public LabelInfoJmsResponse toMessage(String s)
			throws ESBConvertException {
		LabelInfoJmsResponse response = null;
		try {
			response = mapper.readValue(s, LabelInfoJmsResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化OwsDopLabelInfoJmsRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化OwsDopLabelInfoJmsRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化OwsDopLabelInfoJmsRequest时失败！", e);
		}
		return response;
	}

}
