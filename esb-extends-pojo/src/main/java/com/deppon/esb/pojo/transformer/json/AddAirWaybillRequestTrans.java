package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.dpap.esb.mqc.core.data.IMessageTransform;
import com.deppon.dpap.esb.mqc.core.exception.ESBConvertException;
import com.deppon.esb.inteface.domain.cubc.waybill.AirWaybillToCubcRequest;

/**
 * AddWaybillRequest的转换类.
 * 
 * @author 316759-foss-RuipengWang
 */
public class AddAirWaybillRequestTrans implements IMessageTransform<AirWaybillToCubcRequest> {

	private static ObjectMapper obj = new ObjectMapper();
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 316759-foss-RuipengWang
	 * @date 2016-10-19 下午18:59:32
	 * @see com.deppon.esb.core.data.IMessageTransform#toMessage(java.lang.String)
	 */
	public AirWaybillToCubcRequest toMessage(String string) throws ESBConvertException {
		return null;
	}
	
	/** 
	 * TODO（方法详细描述说明、方法参数的具体涵义）
	 * @author 316759-foss-RuipengWang
	 * @date 2016-10-19 下午18:59:32
	 * @see com.deppon.esb.core.data.IMessageTransform#fromMessage(java.lang.Object)
	 */
	public String fromMessage(AirWaybillToCubcRequest value) throws ESBConvertException {
		String jsonString = null;
		if (value == null) {
			return jsonString;
		}
		try {
			jsonString = obj.writeValueAsString(value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonString;
	}

}
