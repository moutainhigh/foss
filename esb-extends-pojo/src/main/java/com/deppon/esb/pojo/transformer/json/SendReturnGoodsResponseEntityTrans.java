package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums2foss.inteface.domian.usermanagementNew.ReturnGoodsRequestEntity;

@XmlRootElement(name = "responseMsg")
public class SendReturnGoodsResponseEntityTrans implements IMessageTransform<SendReturnGoodsResponseEntityTrans>{

	/**
	 * 
	 */
	private static ObjectMapper mapper =new ObjectMapper();
	
	@Override
	public SendReturnGoodsResponseEntityTrans toMessage(String string)
			throws ESBConvertException {
		SendReturnGoodsResponseEntityTrans request =null;
		try {
			request =mapper.readValue(string, SendReturnGoodsResponseEntityTrans.class);
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
	public String fromMessage(SendReturnGoodsResponseEntityTrans message)
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
