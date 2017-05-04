package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GGCheckNumberEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.GGReturnTypeEntity;

/**
 * PDA-OMS 回传验证码到OMS
 * 
 * @author LiuLiPeng
 * @update 2016-07-10 14:16:38
 */
public class AcctPDAToOMSverificationCodeService implements IBusinessService<GGReturnTypeEntity, GGCheckNumberEntity> {
	private Logger log = Logger.getLogger(getClass());
	
	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	private static final int TIMEOUT = 5 * 1000;
	private static final int MAX_HTTP_CONNECTION = 200;
	private static final int MAX_CONNECTION_PER_HOST = 50;
	static {
	//HttpClient 连接池属性设置，HOST并发数默认为50, 客户端总并发数为200, TimeOut时间为5s.
	HttpConnectionManagerParams httpConnectionManagerParams = new HttpConnectionManagerParams();
	httpConnectionManagerParams.setMaxTotalConnections(MAX_HTTP_CONNECTION);
	httpConnectionManagerParams.setDefaultMaxConnectionsPerHost(MAX_CONNECTION_PER_HOST);
	httpConnectionManagerParams.setSoTimeout(TIMEOUT);
	httpConnectionManagerParams.setConnectionTimeout(TIMEOUT);
	connectionManager.setParams(httpConnectionManagerParams);
	}
	/**
	 * 包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 */
	@Override
	public GGCheckNumberEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		GGCheckNumberEntity result = JSON.parseObject(asyncMsg.getContent(), GGCheckNumberEntity.class);
		return result;
	}
	/**
	 * 服务方法 至 DOP
	 * @param asyncMsg
	 * @param param
	 * @return
	 * @throws PdaBusiException
	 */
	@Override
	public GGReturnTypeEntity service(AsyncMsg asyncMsg, GGCheckNumberEntity param) throws PdaBusiException {
		GGReturnTypeEntity result = null;
		
		this.validata(param);
		
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//设置地址
		String url = PropertiesUtil.getKeyValue("dop.query.validation");
		PostMethod postMethod = new PostMethod(url);
		JSONObject jsonObj = new JSONObject();
		//渠道单号
		jsonObj.put("channelNumber", param.getChannelNumber());
		String requestStr = jsonObj.toJSONString();
		//请求实体
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr, "application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			//结果
			String responseStr = postMethod.getResponseBodyAsString();
			result = JSON.parseObject(responseStr, GGReturnTypeEntity.class);
		} catch (UnsupportedEncodingException e) {
			log.info("ERROR 输入参数：“渠道单号”转码失败！",e);
			throw new RuntimeException("ERROR 输入参数：“渠道单号”转码失败！");
		} catch (HttpException e) {
			log.info("ERROR HTTP请求异常：网络不通！",e);
			throw new RuntimeException("ERROR 请求OMS异常：网络不通！");
		} catch (IOException e) {
			log.info("ERROR IO异常！",e);
			throw new RuntimeException("ERROR IO异常：服务方服务异常");
		}
		return result;
	}
	
	public void validata(GGCheckNumberEntity data){
		Argument.notNull(data, "传入参数为空目标：GGCheckNumberEntity");
		Argument.hasText(data.getChannelNumber(), "参数为空：GGCheckNumberEntity.getChannelNumber()");
	}

	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_VALIDATION_CODE.VERSION;
	}

	@Override
	public boolean isAsync() {
		return false;
	}

}
