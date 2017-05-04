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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.shared.util.PropertiesUtil;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ExternalInterfaceException;
import com.deppon.pda.bdm.module.foss.accept.server.IPDAToOMSService;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderOmsDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.PdaOrderWorkerOmsDto;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryAcctOrderEntity;


/**
 * OMS
 * PDA对接OMS同步接口走ESB 
 */
public class OmsToPdaService implements IPDAToOMSService {
	private static Logger LOGGER = LogManager.getLogger(OmsToPdaService.class);
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
	 * 拉取订单接货数据
	 * @param request
	 * @return
	 * @param JSON
	 */
	public String queryAcctOrder( QryAcctOrderEntity entity ) {
		LOGGER.error("----PDA查询OMS订单数据开始----");
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//接入地址
		String url = PropertiesUtil.getKeyValue("oms.queryacctorder.address");
		PostMethod  postMethod=new PostMethod(url);
		String requestStr = JSONObject.toJSONString(entity);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----PDA查询OMS订单数据开始，返回参数：" + responseStr);
			return responseStr;
			
		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			LOGGER.info("调用OMS接口异常"+e.getMessage());
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}
	}
	
		/**         
		 * 开启/暂停接口
		 * @Date  2016-4-10     
		 */
		public String pdaSendWorkerToOMS(PdaOrderWorkerOmsDto pdaOrderWorkerOmsDto) {
			LOGGER.error("----PDA上传OMS开启暂停接口数据开始----");
			HttpClient httpClient = new HttpClient(connectionManager);
			httpClient.getParams().setContentCharset("UTF-8");
			//接入地址
			String url = PropertiesUtil.getKeyValue("oms.sendworkerstatus.address");
			PostMethod  postMethod=new PostMethod(url);
			String requestStr = JSONObject.toJSONString(pdaOrderWorkerOmsDto);
			try {
				RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
				postMethod.setRequestEntity(requestEntity);
				httpClient.executeMethod(postMethod);
				String responseStr = postMethod.getResponseBodyAsString();
				LOGGER.error("----PDA上传OMS开启暂停接口数据，返回参数：" + responseStr);
				return responseStr;
				
			} catch (UnsupportedEncodingException e) {
				throw new ExternalInterfaceException(e.getCause(),e.getMessage());
			} catch (HttpException e) {
				LOGGER.info("调用OMS接口异常"+e.getMessage());
				throw new ExternalInterfaceException(e.getCause(),e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new ExternalInterfaceException(e.getCause(),e.getMessage());
			}finally{
				postMethod.releaseConnection();
			}
		}


	/**
	 * 订单退回
	 * @param request
	 * @return
     */
	@Override
	public String pdaBackOrderOMS(PdaOrderOmsDto backOrder) {
		LOGGER.error("----PDA上传OMS开启暂停接口数据开始----");
		//	String code = "ESB_PDA2ESB_PUSH_FORM_TRAJECTORY2DOP";
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//接入地址
		String url = PropertiesUtil.getKeyValue("oms.backorder.address");
		PostMethod  postMethod=new PostMethod(url);
		String requestStr = JSONObject.toJSONString(backOrder);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			LOGGER.error("----PDA上传OMS开启暂停接口数据，返回参数：" + responseStr);
			return responseStr;
		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			LOGGER.info("调用OMS接口异常"+e.getMessage());
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}
	}

	/**
	 * 订单读取
	 * @param request
     * @return
	 */
	@Override
	public String pdaReadOrderOMS(PdaOrderOmsDto readOrder) {
		//	String code = "ESB_PDA2ESB_PUSH_FORM_TRAJECTORY2DOP";
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//鎺ュ叆鍦板潃
		String url = PropertiesUtil.getKeyValue("oms.readorder.address");
		PostMethod  postMethod=new PostMethod(url);
		String requestStr = JSONObject.toJSONString(readOrder);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;
			
		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}
	}
	/**
	 * 订单受理
	 * @param request
     * @return
	 */
	@Override
	public String pdaRvcOrderOMS(PdaOrderOmsDto rvcOrder) {
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		String url = PropertiesUtil.getKeyValue("oms.rvcorder.address");
		PostMethod  postMethod=new PostMethod(url);
		String requestStr = JSONObject.toJSONString(rvcOrder);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;
			
		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}
	}

	 /** PDA转发人员集合
	 * @param request
     * @return
     */
	@Override
	public String pdaKdQueryForwardListByDriverCodeOMS(String userCode) {
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//鎺ュ叆鍦板潃
		String url = PropertiesUtil.getKeyValue("oms.forwardlist.address");
		PostMethod  postMethod=new PostMethod(url);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("driverCode",userCode);
		String requestStr = jsonObj.toString();
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;
			
		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}
	}

	/**
	 * 查询快递员状态
	 * @param request
     * @return
     */
	@Override
	public String pdaKdQueryWorkerStatusByDriverCodeOMS(String userCode) {
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		String url = PropertiesUtil.getKeyValue("oms.queryworkerstatus.address");
		PostMethod  postMethod=new PostMethod(url);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("empCode",userCode);
		String requestStr = jsonObj.toString();
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,"application/json","utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;
			
		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (HttpException e) {
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(),e.getMessage());
		}finally{
			postMethod.releaseConnection();
		}
	}
}
