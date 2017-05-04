package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.cubc.module.foss.shared.domain.CubcVehicleAssembleBillResponse;
import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;


/**
 * 
 * @author 310248
 * 
 */
public class CubcVehicleAssembleBillResponseTrans  implements IMessageTransform<CubcVehicleAssembleBillResponse>  {
	private static ObjectMapper mapper = new ObjectMapper();

@Override
public String fromMessage(CubcVehicleAssembleBillResponse response)
		throws ESBConvertException {
	try {
		return mapper.writeValueAsString(response);
	} catch (JsonGenerationException e) {
		throw new ESBConvertException("序列化CubcVehicleAssembleBillResponse时失败！", e);
	} catch (JsonMappingException e) {
		throw new ESBConvertException("序列化CubcVehicleAssembleBillResponse时失败！", e);
	} catch (IOException e) {
		throw new ESBConvertException("序列化CubcVehicleAssembleBillResponse时失败！", e);
	}
}

@Override
public CubcVehicleAssembleBillResponse toMessage(String s)
		throws ESBConvertException {
	CubcVehicleAssembleBillResponse response = null;
	try {
		response = mapper.readValue(s, CubcVehicleAssembleBillResponse.class);
	} catch (JsonParseException e) {
		throw new ESBConvertException("反序列化CubcVehicleAssembleBillResponse时失败！", e);
	} catch (JsonMappingException e) {
		throw new ESBConvertException("反序列化CubcVehicleAssembleBillResponse时失败！", e);
	} catch (IOException e) {
		throw new ESBConvertException("反序列化CubcVehicleAssembleBillResponse时失败！", e);
	}
	return response;
}}
