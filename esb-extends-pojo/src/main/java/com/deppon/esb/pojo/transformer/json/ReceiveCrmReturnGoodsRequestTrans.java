package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.ReturnGoodsRequestEntity;

/** 
 * @author 何世贵 
 * @E-mail: heshigui@deppon.com
 * @date 创建时间：2016-5-20 下午3:59:24 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public class ReceiveCrmReturnGoodsRequestTrans  implements IMessageTransform<ReturnGoodsRequestEntity> {

private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public ReturnGoodsRequestEntity toMessage(String string)
			throws ESBConvertException {
		ReturnGoodsRequestEntity request =null;
		try {
			request =mapper.readValue(string, ReturnGoodsRequestEntity.class);
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
	public String fromMessage(ReturnGoodsRequestEntity message)
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
