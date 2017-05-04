package com.deppon.esb.pojo.transformer.json;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.esb.core.data.IMessageTransform;
import com.deppon.esb.core.exception.ESBConvertException;
import com.deppon.uums.inteface.domain.SyncOrganizationToUURequest;
/**
 * 配合主数据项目推送FOSS组织信息给UUMS系统接口转换类
 * @author 187862-dujunhui
 * @date 2015-4-10 下午4:23:43
 *
 */
public class SendOrgInfoToUURequestTrans implements IMessageTransform<SyncOrganizationToUURequest> {
	
	private static ObjectMapper mapper =new ObjectMapper();
	@Override
	public SyncOrganizationToUURequest toMessage(String string)
			throws ESBConvertException {
		SyncOrganizationToUURequest request =null;
		try {
			 request= mapper.readValue(string, SyncOrganizationToUURequest.class);
		} catch (JsonParseException e) {
			throw new ESBConvertException("反序列化syncOrganizationToUURequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("反序列化syncOrganizationToUURequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("反序列化syncOrganizationToUURequest时失败！", e);
		}
		return request;
	}

	@Override
	public String fromMessage(SyncOrganizationToUURequest message)
			throws ESBConvertException {
		try {
			return mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			throw new ESBConvertException("序列化syncOrganizationToUURequest时失败！", e);
		} catch (JsonMappingException e) {
			throw new ESBConvertException("序列化syncOrganizationToUURequest时失败！", e);
		} catch (IOException e) {
			throw new ESBConvertException("序列化syncOrganizationToUURequest时失败！", e);
		}
		
	}
	
}
