package com.deppon.foss.pojo.transformer.jaxb;


import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.gis.HisSignDataTeamRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * HisSignDataRequest的转换类.
 *
 * @author daolin
 */
public class HisSignDataTeamRequestTrans implements IMessageTransform<HisSignDataTeamRequest> {
	private static final Log logger = LogFactory
			.getLog(HisSignDataTeamRequestTrans.class);
	private static ObjectMapper mapper = new ObjectMapper();

	@Override
	public HisSignDataTeamRequest toMessage(String string)
			throws ESBConvertException {
		HisSignDataTeamRequest request = null;
		try {
			request = mapper.readValue(string,
					HisSignDataTeamRequest.class);

		} catch (Exception e) {
			logger.info(e.getMessage()
					+ ":反序列化PartnerUpdateTakeEffectTimeRequest时失败！");
			throw new ESBConvertException(
					"反序列化PartnerUpdateTakeEffectTimeRequest时失败！", e);
		}

		return request;
	}

	@Override
	public String fromMessage(HisSignDataTeamRequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (Exception e) {
			throw new ESBConvertException("序列化PartnerAccountInfoRequest时失败！", e);

		}
	}

}