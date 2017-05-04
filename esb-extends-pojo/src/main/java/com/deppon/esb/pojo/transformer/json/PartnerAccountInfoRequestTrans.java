package com.deppon.esb.pojo.transformer.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.fins.PartnerAccountInfoRequest;

/**
 * 合伙人账户信息接口转换类
 * 
 * @author 302302
 * 
 */
public class PartnerAccountInfoRequestTrans implements
		IMessageTransform<PartnerAccountInfoRequest> {
	private static final Log logger = LogFactory
			.getLog(PartnerAccountInfoRequestTrans.class);
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public PartnerAccountInfoRequest toMessage(String string)
			throws ESBConvertException {
		PartnerAccountInfoRequest request = null;
		try {
			request = mapper.readValue(string, PartnerAccountInfoRequest.class);
		
		} catch (Exception e) {
			logger.info(e.getMessage() + ":反序列化PartnerAccountInfoRequest时失败！");
			throw new ESBConvertException("反序列化PartnerAccountInfoRequest时失败！",
					e);
		}
	
		return request;
	}

	@Override
	public String fromMessage(PartnerAccountInfoRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (Exception e) {
			throw new ESBConvertException("序列化PartnerAccountInfoRequest时失败！", e);
		
	}
	}

}
