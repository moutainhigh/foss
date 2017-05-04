package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.pda.bdm.module.core.server.service.IPDAToVASService;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ExternalInterfaceException;
/**         
 * pda发送数据到vas系统  
 * @author 268974 wangzhili      
 * @created 2015-12-25     
 */
public class PDAToVASService implements IPDAToVASService{
	private Logger log = Logger.getLogger(getClass());

	@Override
	public String pdaSendDataToVAS(String customsCode ) {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		//String url = "http://192.168.67.239:8080/vas-web/rest/customs/customsprint";
		//String url = "http://192.168.67.12:8580/esb2/rs/ESB_PDA2ESB_SEND_PRINT";
		//接入地址
		String url = PropertiesUtil.getKeyValue("vas.home.address");
		PostMethod  postMethod=new PostMethod(url);
		String requestStr = JSONObject.toJSONString(customsCode);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			log.info("调用VAS接口异常"+e.getMessage());
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}
	}

}
