package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmOrgResponse;

public class SendOrgInfoResponseTrans implements IMessageTransform<SendMdmOrgResponse> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	@Override
	public SendMdmOrgResponse toMessage(String string)
			throws ESBConvertException {
		SendMdmOrgResponse respResult =null;
		try {
			respResult =mapper.readValue(string, SendMdmOrgResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化respResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化respResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化respResult时失败！", e);
		}
		return respResult;
	}

	@Override
	public String fromMessage(SendMdmOrgResponse message)
			throws ESBConvertException {
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
