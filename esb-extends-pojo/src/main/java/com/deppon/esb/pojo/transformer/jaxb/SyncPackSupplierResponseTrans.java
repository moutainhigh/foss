package com.deppon.esb.pojo.transformer.jaxb;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.cubc.inteface.domain.SyncPackSupplierResponse;
import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;

public class SyncPackSupplierResponseTrans implements IMessageTransform<SyncPackSupplierResponse>{


	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author HuangHua
	 * @date 2012-12-25 下午5:54:49
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	@Override
	public SyncPackSupplierResponse toMessage(String string)
			throws ESBConvertException {
		SyncPackSupplierResponse result = null;
		try {
			result = mapper.readValue(string,
					SyncPackSupplierResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		}
		return result;
	}

	/**
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * 
	 * @author HuangHua
	 * @date 2012-12-25 下午5:54:49
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	@Override
	public String fromMessage(SyncPackSupplierResponse message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化SmsResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化SmsResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化SmsResult时失败！", e);
		}
	}
}
