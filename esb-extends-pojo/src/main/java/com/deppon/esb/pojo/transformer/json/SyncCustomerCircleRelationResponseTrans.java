package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.crm2foss.CustCircleRelationResponse;
/**
 * 
 * 客户圈信息接口请求转换类
 * @author 308861 
 * @date 2016-12-26 下午2:56:42
 * @since
 * @version
 */
public class SyncCustomerCircleRelationResponseTrans implements IMessageTransform<CustCircleRelationResponse> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public CustCircleRelationResponse toMessage(String string)
			throws ESBConvertException {
		CustCircleRelationResponse response =null;
		try {
			response =mapper.readValue(string, CustCircleRelationResponse.class);
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
	public String fromMessage(CustCircleRelationResponse message)
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
