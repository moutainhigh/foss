package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.deppon.ecs.inteface.domain.SyncSendWaybillRequest;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;


public class SyncSendWaybillRequestTrans implements IMessageTransform<SyncSendWaybillRequest> {

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();


	@Override
	public SyncSendWaybillRequest toMessage(String string) throws ESBConvertException {
		SyncSendWaybillRequest result = null;
		try {
			result = mapper.readValue(string, SyncSendWaybillRequest.class);
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
	public String fromMessage(SyncSendWaybillRequest message) throws ESBConvertException {
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