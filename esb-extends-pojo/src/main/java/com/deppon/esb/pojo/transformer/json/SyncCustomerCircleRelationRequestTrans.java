package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.crm2foss.CustCircleRelationRequest;
/**
 * 
 * 客户圈信息接口请求转换类
 * @author 308861 
 * @date 2016-12-21 下午2:18:26
 * @since
 * @version
 */
public class SyncCustomerCircleRelationRequestTrans implements IMessageTransform<CustCircleRelationRequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public CustCircleRelationRequest toMessage(String string)
			throws ESBConvertException {
		CustCircleRelationRequest request =null;
		try {
			request =mapper.readValue(string, CustCircleRelationRequest.class);
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
	public String fromMessage(CustCircleRelationRequest message)
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

