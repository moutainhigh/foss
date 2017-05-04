package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.foss2qms.ErrResponseParam;


public class ClearanceErrorReportingResponseTrans implements IMessageTransform<ErrResponseParam>{
	
private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public ErrResponseParam toMessage(String string)
			throws ESBConvertException {
		ErrResponseParam response =null;
		try {
			response =mapper.readValue(string, ErrResponseParam.class);
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
	public String fromMessage(ErrResponseParam message)
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
