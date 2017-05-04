package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.ows.inteface.domain.relation.SyncPartnerRelationRequest;
/**
 * 网点映射关系信息接口请求转换类
 * @author 308861
 *
 */
public class SyncPartnerRelationRequestTrans implements IMessageTransform<SyncPartnerRelationRequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public SyncPartnerRelationRequest toMessage(String string)
			throws ESBConvertException {
		SyncPartnerRelationRequest request =null;
		try {
			request =mapper.readValue(string, SyncPartnerRelationRequest.class);
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
	public String fromMessage(SyncPartnerRelationRequest message)
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

