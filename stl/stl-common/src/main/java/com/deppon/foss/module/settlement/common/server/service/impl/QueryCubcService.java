package com.deppon.foss.module.settlement.common.server.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IQueryCubcService;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CodQueryToCubcResponse;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;

/**
 * 对接cubc服务
 * @author 269044
 * @date 2017-04-07
 */
public class QueryCubcService implements IQueryCubcService {
	
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(QueryCubcService.class);
	
	/**
	 * esb编码
	 */
	public static final String ESB_CODE = "FOSS_CUBC_QUERY_COD";
	
	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;

	/**
	 * 查询代收集合
	 * @author 269044
	 * @date 2017-04-07
	 */
	@Override
	public CodQueryToCubcResponse queryCubcCodList(CodQueryToCubcDto dto) {
		// 打印日志
		logger.info("调用cubc接口查询开始");
		// 参数判空
		if(null == dto || (StringUtils.isEmpty(dto.getCustomerCode()) && CollectionUtils.isEmpty(dto.getWayBillNo()))) {
			// 抛异常
			throw new SettlementException ("参数为空，或者客户编码和运单号同时为空");
		}
		try{
			// 根据服务端的ESB_CODE查到esb地址
			FossConfigEntity fossConfig = fossConfigEntityService.queryFossConfigEntityByServerCode(ESB_CODE);
			// 获取地址
			String esbTPSAddr = fossConfig.getEsbAddr();
			logger.info("请求cubc的地址为：" + esbTPSAddr);
			
			String params = JSON.toJSONString(dto);
			logger.info("调用CUBC查询代收货款请求参数："+params);
			RequestEntity entity = new StringRequestEntity(params, "application/json", "UTF-8");
			
			// 构造PostMethod的实例
			PostMethod postMethod = new PostMethod(esbTPSAddr);
			postMethod.setRequestEntity(entity);
			// 设置格式
			postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
			HttpClient httpClient = new HttpClient();
			// 设置编码格式
			httpClient.getParams().setContentCharset("UTF-8");
			// 执行postMethod
			int statusCode = httpClient.executeMethod(postMethod);
			logger.info("客户端方法执行的结果值" + statusCode);
			
			// 获取返回值
			String responseBody = IOUtils.toString(postMethod.getResponseBodyAsStream());
			logger.info("调用CUBC查询代收货款返回数据："+responseBody);
			
			// 转换响应参数
			return JSON.parseObject(responseBody, CodQueryToCubcResponse.class);
			
		} catch (Exception e) {
			logger.info("调用CUBC查询代收货款接口异常",e);
			// 抛异常
			throw new SettlementException("调用Cubc接口异常" + e.getMessage());
		}
		//return null;
	}

	/**
	 * @param fossConfigEntityService the fossConfigEntityService to set
	 */
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}
	
}
