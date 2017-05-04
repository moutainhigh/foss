package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.ecs.inteface.domain.SyncOutfieldSignForRequest;
/*import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;*/

public class SyncOutfieldSignForRequestTrans implements IMessageTransform<SyncOutfieldSignForRequest>{

	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 327090
	 * @date 2016-5-30 
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	@Override
	public SyncOutfieldSignForRequest toMessage(String string)
			throws ESBConvertException {
		SyncOutfieldSignForRequest result = null;
		try {
			result = mapper.readValue(string, SyncOutfieldSignForRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		}
		return result;
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 327090
	 * @date 2016-5-30
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	@Override
	public String fromMessage(SyncOutfieldSignForRequest message)
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
