package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.ows.inteface.domain.relation.SyncPartnerRelationInfoResponse;
/**
 * 网点映射关系接口响应转换类
 * @author 308861
 *
 */
public class SyncPartnerRelationResponseTrans implements IMessageTransform<SyncPartnerRelationInfoResponse> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public SyncPartnerRelationInfoResponse  toMessage(String string)
			throws ESBConvertException {
		SyncPartnerRelationInfoResponse response =null;
		try {
			response =mapper.readValue(string, SyncPartnerRelationInfoResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化response时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化response时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化response时失败！", e);
		}
		return response;
	}

	@Override
	public String fromMessage(SyncPartnerRelationInfoResponse message)
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
