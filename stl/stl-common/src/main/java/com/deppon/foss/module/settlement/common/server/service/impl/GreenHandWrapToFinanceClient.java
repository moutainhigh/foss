package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.settlement.common.api.server.service.IFossConfigEntityService;
import com.deppon.foss.module.settlement.common.api.server.service.IGreenHandWrapToFinanceClient;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.ResponseFinsWrapDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WriteoffInformationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 218392 张永雪
 * @date 2016-02-15 10:15:12
 * 裹裹项目：FOSS结算核销后把核销信息传给财务自助，传的字段有：DOP传过单的金额、
 * 			 FOSS核销金额、应收部门、DOP时间、运单号
 */
public class GreenHandWrapToFinanceClient implements IGreenHandWrapToFinanceClient{
	/**
	 * 注入日志
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(GreenHandWrapToFinanceClient.class);
	
	/**
	 * ESB提供的服务端编码（ESB同事提供的）
	 */
	public static String ESB_SERVICE_CODE = "/FINS_ESB2FINS_PUSH_RESULT_INF2FINS";
	
	/**
	 * ESB提供的客户端编码（ESB同事提供的）
	 */
	public static String ESB_CLIENT_CODE = "/ESB_FOSS2ESB_PUSH_RESULT_INF2FINS";
	
	/**
	 * FINS服务端地址(本地联调用)
	 */
	//public static String FINS_SERVICE_CODE = "http://10.224.64.129:8080/financManager/webservice/GGOrderServiceImpl/SaveGGOrderRecord";
	
	/**
	 * 查询esb地址信息的接口
	 */
	private IFossConfigEntityService fossConfigEntityService;
	
	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}

	@Override
	public void sendWriteoffWrapToFinance(WriteoffInformationDto writeoffInformationDto) {
		logger.info("裹裹从FOSS到FINS请求：补贴公布数据开始...");
		if(writeoffInformationDto != null){
			try{
				//Map<String,Object> map = new HashMap<String,Object>();
				//map.put("requestKey", writeoffInformationDto);//定义传给财务自助请求实体的key，那边获取就可以用get("requestKey")获取
				JSONObject jsonObject =  JSONObject.fromObject(writeoffInformationDto);
				String js = jsonObject.toString();
				RequestEntity entity = new StringRequestEntity(js,"application/json", "UTF-8");
				
				// 根据服务端的ESB_CODE查到esb地址
				FossConfigEntity fossConfig = fossConfigEntityService.queryFossConfigEntityByServerCode(ESB_SERVICE_CODE);
				String  esbTPSAddr = fossConfig.getEsbAddr();
				logger.info("查询到的ESB地址为：" + esbTPSAddr);
				logger.info("裹裹从FOSS到FINS请求的ESB地址为："+esbTPSAddr + ESB_CLIENT_CODE);
				// 构造PostMethod的实例
				PostMethod postMethod = new PostMethod(esbTPSAddr+ ESB_CLIENT_CODE);
				
				//PostMethod postMethod = new PostMethod(FINS_SERVICE_CODE);//和FINS本地联调用的
				
				postMethod.setRequestEntity(entity);
				postMethod.addRequestHeader("Content-Type","application/json;charset=UTF-8");
				HttpClient httpClient = new HttpClient();
				// 设置编码格式
				httpClient.getParams().setContentCharset("UTF-8");
				// 执行postMethod
				int statusCode = httpClient.executeMethod(postMethod);
				logger.info("客户端方法执行的结果状态:" + statusCode);
				String responseBody = "";
				// 获取返回值
				responseBody = postMethod.getResponseBodyAsString();
				logger.info("返回值信息为："+responseBody);
				
				// 将返回值转换成实体类ReceiveTPSResponseEntity
				JSONObject jsonOb = JSONObject.fromObject(responseBody);
				if(jsonOb != null){
					Object object = JSONObject.toBean(jsonOb, ResponseFinsWrapDto.class);
					ResponseFinsWrapDto responseFinsWrapDto = (ResponseFinsWrapDto) object;
					logger.info("调用裹裹FINS服务端接口后响应的结果...");
					logger.info("是否成功："+responseFinsWrapDto.getIsSuccess());
					logger.info("失败原因为: "+responseFinsWrapDto.getFailReason());					
				}
			}catch(Exception e){
				
				throw new SettlementException("裹裹从FOSS同步到FINS财务自助失败,异常信息为: "+ e.getMessage());
				
			}
		}else{
			logger.info("裹裹从FOSS到FINS同步信息为空...");
		}
	}
}
