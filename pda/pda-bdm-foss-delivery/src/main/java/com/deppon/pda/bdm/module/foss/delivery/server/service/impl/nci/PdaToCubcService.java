package com.deppon.pda.bdm.module.foss.delivery.server.service.impl.nci;

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
import com.deppon.foss.module.settlement.common.api.shared.domain.WSCWayBillParamEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.util.PropertiesUtil;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ExternalInterfaceException;
import com.deppon.pda.bdm.module.foss.delivery.server.IPdaToCubcService;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.PayInfoDO;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.RequestDO;


public class PdaToCubcService implements IPdaToCubcService{
	private static Logger LOGGER = LogManager.getLogger(PdaToCubcService.class);
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
	 * 通过单号查询对账单
	 */
	@Override
	public String pdaToCubcAccountStatement(CommonQueryParamDto dto) {
		String code = "ESB_FOSS2ESB_PDA_CUBC_QUERY_BILL_UNWRITEOFF_DETAIL";
		HttpClient httpClient = new HttpClient(connectionManager);
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		httpClient.getParams().setContentCharset("UTF-8");
		//String url = "http://10.230.28.80:8084/bill-web/official/queryUnwriteOffForPdaOrPos";
		//PropertiesUtil.getKeyValue("cubc.pdaToCubcAccountStatement.address");
		PostMethod postMethod = new PostMethod(url);
		String requestStr = JSONObject.toJSONString(dto);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,
					"application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;

		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (HttpException e) {
			LOGGER.error("调用CUBC接口异常" + e.getMessage());
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 刷卡信息上传
	 */
	@Override
	public String getAccountStatementSuccess(PayInfoDO dto) {
		String code = "ESB_PDA2ESB_OWS_BUILDPAYINFO";
		HttpClient httpClient = new HttpClient(connectionManager);
		String url = PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		httpClient.getParams().setContentCharset("UTF-8");
		//String url = "http://10.230.28.79:18080/asset-web/ws/v1/asset/payInfoManagerService/buildPayInfoMessage";
		//PropertiesUtil.getKeyValue("cubc.pdaToCubcUploadCard.address");
		PostMethod postMethod = new PostMethod(url);
		String requestStr = JSONObject.toJSONString(dto);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,
					"application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;

		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (HttpException e) {
			LOGGER.error("调用CUBC接口异常" + e.getMessage());
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}


	/**
	 * 查询登录人对应部门的所有待刷卡数据
	 */
	@Override
	public String setNotBushCard(WSCWayBillParamEntity entity) {
		String code = "ESB_PDA2ESB_CUBC_PRE_CARD";
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		String url=PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		//String url ="http://10.230.28.75:8081/trade-web/webservice/queryStaySwipingInfoService/queryStaySwipingInfo"; 
		//PropertiesUtil.getKeyValue("cubc.pdaToCubcNotBush.address");
		PostMethod postMethod = new PostMethod(url);
		String requestStr = JSONObject.toJSONString(entity);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,
					"application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;

		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (HttpException e) {
			LOGGER.error("调用CUBC接口异常" + e.getMessage());
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}

	//查询运单号是否核销
	@Override
	public String queryWaybill(String waybill) {
		String code = "ESB_PDA2ESB_CUBC_PDANOT_CANCEL";
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		String url=PropertiesUtil.getKeyValue("esb.rs")+"/"+code;
		//String url ="http://10.230.28.75:8081/trade-web/webservice/settleQueryUnverifyService/queryUnverifyTrade"; 
		//PropertiesUtil.getKeyValue("cubc.pdaToCubcSettlementOfPayment.address");
		PostMethod postMethod = new PostMethod(url);
		String requestStr = JSONObject.toJSONString(waybill);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,
					"application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;

		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (HttpException e) {
			LOGGER.error("调用CUBC接口异常" + e.getMessage());
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}

	/**
	 * 调用灰度接口,查询走哪个系统--按对账单号
	 */
	@Override
	public String cubcHuiduQuery(RequestDO requestDo) {
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//String url ="http://10.230.28.87:8145/ashy-web/webservice/v1/ashy/vestService/vestAscription"; 
		String url = PropertiesUtil.getKeyValue("cubc.gray.vestSourceBillNo");
		PostMethod postMethod = new PostMethod(url);
		String requestStr = JSONObject.toJSONString(requestDo);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,
					"application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;

		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (HttpException e) {
			LOGGER.error("调用CUBC接口异常" + e.getMessage());
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}
	
	/**
	 * 调用灰度接口,查询走哪个系统--按方法名称
	 */
	@Override
	public String cubcHuiduQueryMethod(RequestDO requestDo) {
		HttpClient httpClient = new HttpClient(connectionManager);
		httpClient.getParams().setContentCharset("UTF-8");
		//String url ="http://10.230.28.87:8145/ashy-web/webservice/v1/ashy/vestService/vestMethod"; 
		String url = PropertiesUtil.getKeyValue("cubc.gray.vestMethod");
		PostMethod postMethod = new PostMethod(url);
		String requestStr = JSONObject.toJSONString(requestDo);
		try {
			RequestEntity requestEntity = new StringRequestEntity(requestStr,
					"application/json", "utf-8");
			postMethod.setRequestEntity(requestEntity);
			httpClient.executeMethod(postMethod);
			String responseStr = postMethod.getResponseBodyAsString();
			return responseStr;

		} catch (UnsupportedEncodingException e) {
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (HttpException e) {
			LOGGER.error("调用CUBC接口异常" + e.getMessage());
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExternalInterfaceException(e.getCause(), e.getMessage());
		} finally {
			postMethod.releaseConnection();
		}
	}
	
}
