package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.OneticketornotSyncRequest;

/**
 * 接口转换类
 * @author 273311
 *
 */
public class OneticketornotRequestTrans implements IMessageTransform<OneticketornotSyncRequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	@Override
	public OneticketornotSyncRequest toMessage(String string)
			throws ESBConvertException {
		OneticketornotSyncRequest request =null;
		try {
			 request= mapper.readValue(string, OneticketornotSyncRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化OneticketornotSyncRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化OneticketornotSyncRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化OneticketornotSyncRequest时失败！", e);
		}
		return request;
	}

	@Override
	public String fromMessage(OneticketornotSyncRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化OneticketornotSyncRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化OneticketornotSyncRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化OneticketornotSyncRequest时失败！", e);
		}
	}
}
