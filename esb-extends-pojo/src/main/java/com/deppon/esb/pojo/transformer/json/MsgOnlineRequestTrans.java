package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.MsgOnlineInfoSyncRequest;

/**
 * 接口转换类
 * @author 273241
 *
 */
public class MsgOnlineRequestTrans implements IMessageTransform<MsgOnlineInfoSyncRequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	@Override
	public MsgOnlineInfoSyncRequest toMessage(String string)
			throws ESBConvertException {
		MsgOnlineInfoSyncRequest request =null;
		try {
			 request= mapper.readValue(string, MsgOnlineInfoSyncRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化MsgOnlineRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化MsgOnlineRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化MsgOnlineRequest时失败！", e);
		}
		return request;
	}

	@Override
	public String fromMessage(MsgOnlineInfoSyncRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化MsgOnlineRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化MsgOnlineRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化MsgOnlineRequest时失败！", e);
		}
	}
}
