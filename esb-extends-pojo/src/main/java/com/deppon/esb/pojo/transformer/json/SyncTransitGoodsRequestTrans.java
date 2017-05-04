package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.foss2oms.SyncTransitGoodsRequest;


public class SyncTransitGoodsRequestTrans  implements IMessageTransform<SyncTransitGoodsRequest>{
		/** The mapper. */							  
		private static ObjectMapper mapper = new ObjectMapper();
		
		@Override
		public SyncTransitGoodsRequest toMessage(String string) throws ESBConvertException {
			SyncTransitGoodsRequest result = null;
			try {
				result = mapper.readValue(string, SyncTransitGoodsRequest.class);
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
		public String fromMessage(SyncTransitGoodsRequest message) throws ESBConvertException {
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
