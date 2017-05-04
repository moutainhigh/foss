package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.foss2oms.SyncTransitGoodsResponse;

public class SyncTransitGoodsResponseTrans implements IMessageTransform<SyncTransitGoodsResponse>{
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 332153
	 * @date 2016年11月22日13:57:56
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	@Override
	public SyncTransitGoodsResponse toMessage(String string)
			throws ESBConvertException {
		 SyncTransitGoodsResponse result = null;
		try {
			result = mapper.readValue(string, SyncTransitGoodsResponse.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化SyncTransitGoodsResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化SyncTransitGoodsResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化SyncTransitGoodsResponse时失败！", e);
		}
		return result;
	}

	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 332153
	 * @date  2016年11月22日13:57:56
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	@Override
	public String fromMessage(SyncTransitGoodsResponse message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化SyncTransitGoodsResponse时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化SyncTransitGoodsResponse时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化SyncTransitGoodsResponse时失败！", e);
		}
	}
}
