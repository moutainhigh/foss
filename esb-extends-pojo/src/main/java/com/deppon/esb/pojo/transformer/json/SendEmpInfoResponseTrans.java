package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmEmpResponse;
/**
 * 
 * @author 130566
 *
 */
public class SendEmpInfoResponseTrans implements IMessageTransform<SendMdmEmpResponse> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public SendMdmEmpResponse toMessage(String string)
			throws ESBConvertException {
		SendMdmEmpResponse response =null;
		try {
			response =mapper.readValue(string, SendMdmEmpResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化response时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化response时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化response时失败！", e);
		}
		return null;
	}

	@Override
	public String fromMessage(SendMdmEmpResponse message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化response时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化response时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化response时失败！", e);
		}
	}
	

}
