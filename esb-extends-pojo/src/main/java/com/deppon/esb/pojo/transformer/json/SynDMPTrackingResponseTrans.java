package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.pojo.domain.dmp2foss.DMPSynTrackingToFOSSResponseEntity;

public class SynDMPTrackingResponseTrans implements IMessageTransform<DMPSynTrackingToFOSSResponseEntity>{
	/** The mapper. */
	private static ObjectMapper mapper = new ObjectMapper();
	@Override
	public DMPSynTrackingToFOSSResponseEntity toMessage(String arg0)throws ESBConvertException {
		DMPSynTrackingToFOSSResponseEntity result = null;
		try {
			result = mapper.readValue(arg0, DMPSynTrackingToFOSSResponseEntity.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化DMPSynTrackingToFOSSResponseEntity时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化DMPSynTrackingToFOSSResponseEntity时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化DMPSynTrackingToFOSSResponseEntity时失败！", e);
		}
		return result;
	}
	@Override
	public String fromMessage(DMPSynTrackingToFOSSResponseEntity message)throws ESBConvertException {

		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化DMPSynTrackingToFOSSResponseEntity时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化DMPSynTrackingToFOSSResponseEntity时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化DMPSynTrackingToFOSSResponseEntity时失败！", e);
		}
	
	}

	

}
