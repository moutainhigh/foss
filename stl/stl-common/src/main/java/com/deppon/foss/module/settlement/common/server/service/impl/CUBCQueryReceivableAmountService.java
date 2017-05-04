package com.deppon.foss.module.settlement.common.server.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.deppon.foss.util.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCQueryReceivableAmountService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossBillReceivableResponseDO;
import com.deppon.foss.module.settlement.common.api.shared.dto.FossQueryRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.QueryReceivableAmountException;
import com.deppon.foss.util.CollectionUtils;

/**
 * CUBC结清查询(财务单据)接口实现
 * @author 353654
 *
 */

public class CUBCQueryReceivableAmountService extends CUBCCommonService implements ICUBCQueryReceivableAmountService {
	
	private final static String PARAMETERS_ERROR;
	private final static String CONNECTIONS_ERROR;
	private final static String METHOD_START;
	static{
		PARAMETERS_ERROR = "调用CUBC结清财务查询失败，参数异常...";
		CONNECTIONS_ERROR = "调用CUBC结清财务查询失败，连接异常...";
		METHOD_START = "调用CUBC财务查询接口---start";
	}
	/**
	 * FOSS调用CUBC查询财务单据服务
	 * @throws Exception 
	 */
	@Override
	public List<BillReceivableEntity> queryReceivableAmount(String sourceNo){
		logger.info(METHOD_START);
		if(StringUtils.isBlank(sourceNo)){
			throw new QueryReceivableAmountException(PARAMETERS_ERROR);
		}
		logger.info("CUBC查询财务单据请求地址信息:"+esbAddress);
		PostMethod postMethod  = null;
		try {
			String requestJson = JSONObject.toJSONString(new FossQueryRequestDto(sourceNo));
			logger.info("调用CUBC查询财务单据服务请求JSON信息："+requestJson);
			RequestEntity reqEntity = new StringRequestEntity(requestJson,APPLICATION_JSON,CODE_FORMAT);
			postMethod  = new PostMethod(esbAddress);
			postMethod.setRequestEntity(reqEntity);
			postMethod.addRequestHeader(CONTENT_TYPE,REQUEST_HEADER);
			HttpClient httpClient = new HttpClient();
			HttpConnectionManagerParams params = httpClient.getHttpConnectionManager().getParams();
			params.setConnectionTimeout(NumberConstants.NUMBER_15000);
			params.setSoTimeout(NumberConstants.NUMBER_2999);
			httpClient.executeMethod(postMethod);
//			String resultJson = postMethod.getResponseBodyAsString();
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,CODE_FORMAT));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			try {
				while ((line = br.readLine()) != null){
					sbf.append(line);
				}
			} catch (Exception e) {
				throw new IOException("读取响应数据失败！");
			} finally{
				if(br != null){
					br.close();
				}
			}
			String resultJson = sbf.toString();
			logger.info("调用CUBC查询财务单据服务响应JSON信息："+resultJson);
			FossBillReceivableResponseDO resultDto = JSONObject.parseObject(resultJson,FossBillReceivableResponseDO.class);
			//把客户编码和客户名称从list外面set到list里面的第一条数据里面   add by 378375
			if(CollectionUtils.isNotEmpty(resultDto.getList())) {
				resultDto.getList().get(0).setSettleCustCode(resultDto.getCustomerCode());
				resultDto.getList().get(0).setSettleCustName(resultDto.getCustomerName());
			}
			return resultDto.getList();
		} catch (Exception e) {
			logger.error(CONNECTIONS_ERROR + e.getMessage());
			throw new QueryReceivableAmountException(CONNECTIONS_ERROR);
		}finally {
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
		}
	}
	
}
