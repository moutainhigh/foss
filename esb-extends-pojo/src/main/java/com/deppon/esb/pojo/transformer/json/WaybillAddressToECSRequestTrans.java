package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.foss2ecs.CustomerAddressFossRequest;


/**
 * 
 * Trans
 * @author 198771-zhangwei
 * 2016-10-12 下午2:54:07
 */
public class WaybillAddressToECSRequestTrans implements IMessageTransform<CustomerAddressFossRequest> {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-12 下午2:49:46
	 */
	public CustomerAddressFossRequest toMessage(String string) throws ESBConvertException {
		return null;
	}
	
	/**
	 * 
	 * 
	 * @author 198771-zhangwei
	 * 2016-10-12 下午2:49:52
	 */
	public String fromMessage(CustomerAddressFossRequest value) throws ESBConvertException {
		try {
			return mapper.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化CustomerAddressFossRequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化CustomerAddressFossRequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化CustomerAddressFossRequest时失败！", e);
		}
	}
}
