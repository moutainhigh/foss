package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.oa.module.errors.webservice.bean.objectwithinshort.ObjectWithinShortRequest;

public class ObjectWithinShortRequestTrans  implements IMessageTransform<ObjectWithinShortRequest> {

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();


	@Override
	public ObjectWithinShortRequest toMessage(String string) throws ESBConvertException {
		ObjectWithinShortRequest result = null;
		try {
			result = mapper.readValue(string, ObjectWithinShortRequest.class);
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
	public String fromMessage(ObjectWithinShortRequest message) throws ESBConvertException {
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
