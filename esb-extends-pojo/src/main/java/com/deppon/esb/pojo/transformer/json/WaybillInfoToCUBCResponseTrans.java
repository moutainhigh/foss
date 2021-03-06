package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.foss2cubc.WaybillInfoToCUBCResponse;


/**
 * 
 * WaybillInfoToCUBCRequestTrans
 * @author 198771-zhangwei
 * 2016-10-12 下午2:54:07
 */
public class WaybillInfoToCUBCResponseTrans implements IMessageTransform<WaybillInfoToCUBCResponse> {	
	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-12 下午2:49:46
	 */
	public WaybillInfoToCUBCResponse toMessage(String value) throws ESBConvertException {
		WaybillInfoToCUBCResponse response = null;
		try {
			response = mapper.readValue(value, WaybillInfoToCUBCResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化WaybillInfoToCUBCResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化WaybillInfoToCUBCResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化WaybillInfoToCUBCResponse时失败！", e);
		}
		return response;
	}
	
	/**
	 * 
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-12 下午2:49:52
	 */
	public String fromMessage(WaybillInfoToCUBCResponse value) throws ESBConvertException {
		return null;
	}
}
