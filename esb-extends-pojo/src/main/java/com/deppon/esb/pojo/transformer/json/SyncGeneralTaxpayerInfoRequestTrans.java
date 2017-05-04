package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.crm.GeneralTaxpayerInfo;
/**
 * 一般纳税人信息接口请求转换类
 * @author 308861
 *
 */
public class SyncGeneralTaxpayerInfoRequestTrans implements IMessageTransform<GeneralTaxpayerInfo> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public GeneralTaxpayerInfo toMessage(String string)
			throws ESBConvertException {
		GeneralTaxpayerInfo request =null;
		try {
			request =mapper.readValue(string, GeneralTaxpayerInfo.class);
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
	public String fromMessage(GeneralTaxpayerInfo message)
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

