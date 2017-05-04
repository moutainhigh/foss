package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.fssc.inteface.domain.payment.DealComplainChangeFossRequest;

/**
 * DealComplainChangeFossRequest、DealComplainChangeFossResponse
 * 请求响应转换类
 * @author 231438-chenjunying 2015-03-18
 */
public class DealComplainChangeFossRequestTrans implements IMessageTransform<DealComplainChangeFossRequest>{
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public DealComplainChangeFossRequest toMessage(String string)
			throws ESBConvertException {
		DealComplainChangeFossRequest result = null;
		try {
			result = mapper.readValue(string, DealComplainChangeFossRequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化SmsResult时失败！", e);
		}
		return result;
	}

	@Override
	public String fromMessage(DealComplainChangeFossRequest message)
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
