package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmEmpRequest;
/**
 * 主数据项目接口转换类
 * @author 130566
 *
 */
public class SendEmpInfoRequestTrans implements IMessageTransform<SendMdmEmpRequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public SendMdmEmpRequest toMessage(String string)
			throws ESBConvertException {
		SendMdmEmpRequest request =null;
		try {
			request =mapper.readValue(string, SendMdmEmpRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化request时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化request时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化request时失败！", e);
		}
		return request;
	}
	
	
	@Override
	public String fromMessage(SendMdmEmpRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化request时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化request时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化request时失败！", e);
		}
		
	}
}

