package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.SendMdmOrgRequest;
/**
 * 接口转换类
 * @author 130566
 *
 */
public class SendOrgInfoRequestTrans implements IMessageTransform<SendMdmOrgRequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	@Override
	public SendMdmOrgRequest toMessage(String string)
			throws ESBConvertException {
		SendMdmOrgRequest request =null;
		try {
			 request= mapper.readValue(string, SendMdmOrgRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化sendMdmOrgRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化sendMdmOrgRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化sendMdmOrgRequest时失败！", e);
		}
		return request;
	}

	@Override
	public String fromMessage(SendMdmOrgRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化sendMdmOrgRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化sendMdmOrgRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化sendMdmOrgRequest时失败！", e);
		}
		
	}
	
}
