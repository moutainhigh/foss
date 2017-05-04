package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MsgOnlineInfoSyncResponse;

public class MsgOnlineResponseTrans implements IMessageTransform<MsgOnlineInfoSyncResponse> {
	/** The mapper. */
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public MsgOnlineInfoSyncResponse toMessage(String string) throws ESBConvertException {
		MsgOnlineInfoSyncResponse result =null;
		try {
			result =mapper.readValue(string, MsgOnlineInfoSyncResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化respResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化respResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化respResult时失败！", e);
		}
		return result;
	}

	@Override
	public String fromMessage(MsgOnlineInfoSyncResponse message) throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化respResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化respResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化respResult时失败！", e);
		}
	}
}
