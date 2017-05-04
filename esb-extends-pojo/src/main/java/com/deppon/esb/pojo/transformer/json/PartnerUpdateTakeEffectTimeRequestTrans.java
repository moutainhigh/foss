package com.deppon.esb.pojo.transformer.json;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.foss2ptp.PartnerUpdateTakeEffectTimeListRequest;

/**
 * 合伙人作废/生效派送提成相关流水转换类
 * 
 * @author 239284
 * 
 */
public class PartnerUpdateTakeEffectTimeRequestTrans implements
		IMessageTransform<PartnerUpdateTakeEffectTimeListRequest> {
	private static final Log logger = LogFactory
			.getLog(PartnerUpdateTakeEffectTimeRequestTrans.class);
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public PartnerUpdateTakeEffectTimeListRequest toMessage(String string)
			throws ESBConvertException {
		PartnerUpdateTakeEffectTimeListRequest request = null;
		try {
			request = mapper.readValue(string,
					PartnerUpdateTakeEffectTimeListRequest.class);

		} catch (Exception e) {
			logger.info(e.getMessage()
					+ ":反序列化PartnerUpdateTakeEffectTimeRequest时失败！");
			throw new ESBConvertException(
					"反序列化PartnerUpdateTakeEffectTimeRequest时失败！", e);
		}

		return request;
	}

	@Override
	public String fromMessage(PartnerUpdateTakeEffectTimeListRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (Exception e) {
			throw new ESBConvertException("序列化PartnerAccountInfoRequest时失败！", e);

		}
	}

}
