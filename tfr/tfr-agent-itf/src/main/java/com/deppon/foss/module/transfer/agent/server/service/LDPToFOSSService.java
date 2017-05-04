package com.deppon.foss.module.transfer.agent.server.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILdpAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerExpressEntity;
import com.deppon.foss.module.transfer.common.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IAirAgencySignService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.transfer.common.api.shared.dto.LdpExceptionDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.LdpSignUpExceptionDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.LdpTrackExceptionDto;
import com.deppon.foss.module.transfer.common.server.utils.BidBeanSerializerFactory;
import com.deppon.foss.module.transfer.common.server.utils.JSONUtils;
import com.deppon.foss.module.transfer.load.api.server.service.IExpressHandOverBillService;
import com.deppon.foss.module.transfer.load.api.shared.dto.LdpHandOverDto;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.server.service.ILdpExternalBillTrackService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpExternalBillTrackDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpSignUpResultDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 快递代理公司与FOSS系统服务端 Service
 *
 * @author ibm-liuzhaowei
 * @date 2013-08-02 上午9:18:36
 */
public class LDPToFOSSService {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LDPToFOSSService.class);
	
	/**
	 * 快递交接单服务 Service
	 */
	private IExpressHandOverBillService expressHandOverBillService;
	/**
	 * 快递代理服务 Service
	 */
	private ILdpExternalBillService ldpExternalBillService;
	/**
	 * 快递代理外发单轨迹 Service
	 */
	private ILdpExternalBillTrackService ldpExternalBillTrackService;
	/**
	 * 快递代理签收 Service
	 */
	private IAirAgencySignService airAgencySignService;
	
	private IWaybillManagerService waybillManagerService;
	
	private ILdpAgencyCompanyService ldpAgencyCompanyService;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/getJsonForTest")
	public String getJsonForTest() {
		//messageBundle.getMessage("test");
		System.out.println("get the Request");
		return "{\"handoverNo\": \"," +
				"\"agentCompanyCode\": \"AgentCompanyCode\"," +
				"\"handoverTime\": \"2013-07-31\"}";
	}

	/**
	 * 查询快递代理交接单
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-02 上午11:01:32
	 */
	@POST
	@Path("/queryLdpHandOverBillList")
	@Transactional
	public String queryLdpHandOverBillListWithJson(@Context HttpServletRequest httpServletRequest,
			                                       @Context HttpServletResponse httpServletResponse, 
			                                       String jsonStr) {
		LOGGER.info("查询交接单：Header-----> " + "ESB-UserName: " + httpServletRequest.getHeader("ESB-UserName") + 
				  "ESB-ESBServiceCode: " + httpServletRequest.getHeader("ESB-ESBServiceCode") + 
				  "ESB-Version: " + httpServletRequest.getHeader("ESB-Version") +
				  "JSON: " + jsonStr);
		
		httpServletResponse.setHeader("ESB-UserName", httpServletRequest.getHeader("ESB-UserName"));
		httpServletResponse.setHeader("ESB-ESBServiceCode",	httpServletRequest.getHeader("ESB-ESBServiceCode"));
		httpServletResponse.setHeader("ESB-Password", httpServletRequest.getHeader("ESB-Password"));
		httpServletResponse.setHeader("ESB-Version", httpServletRequest.getHeader("ESB-Version"));
		httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_SUCCESS);
		
		//执行正常返回的结果信息
		String resultJsonStr = "";
	    String handoverNo = "";
	    String agentCompanyCode = "";
	    String handoverTimeStr = "";
	    Date handoverTime = null;
		LdpExceptionDto ldpExceptionDto = new LdpExceptionDto();
		ObjectMapper objectMapper = null;
		try {
			// 获取objectMapper
			objectMapper = obtainJSONObjectMapper();
			JsonFactory jfactory = new JsonFactory();
			JsonParser jParser = jfactory.createJsonParser(jsonStr);
			while (jParser.nextToken() != JsonToken.END_OBJECT) {
				String fieldname = jParser.getCurrentName();
				if ("handoverNo".equals(fieldname)) {
				  jParser.nextToken();
				  handoverNo = jParser.getText();
				}else if ("agentCompanyCode".equals(fieldname)) {
				  jParser.nextToken();
				  agentCompanyCode = jParser.getText();
				}else if ("handoverTime".equals(fieldname)) {
				  jParser.nextToken();
				  handoverTimeStr = jParser.getText();
				}
			}
			jParser.close();
		}catch (Exception e) {
			LOGGER.info("快递代理公司查询交接单解析参数出错！参数为：" + jsonStr + " 出错信息：" + e.toString());
			ldpExceptionDto.setMessage("查询快递代理交接单解析传入参数出错！");
			ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
			ldpExceptionDto.setMessage(ExceptionTypeEnum.S000004.getMessage());
			ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000004.getTypeCode());
			ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000004.getCode());
			
			httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
			//返回异常信息
			return getJsonForException(ldpExceptionDto,objectMapper);
		}
		
		try{
			/**
			 * 查询条件：
			 * 1）	交接单号: 此时返回该交接单号对应的信息；或者
			 * 2）	代理公司编码 + 交接日期（yyyy-mm-dd）： 此时返回属于该代理公司的，某天的所有交接单.
			 * 如果所有字段都有，则只适用交接单号来查询。
			 */
			if(StringUtils.isNotBlank(agentCompanyCode)){
				//验证传入参数的和header中的代理公司是否一致
				if(!StringUtils.equals(httpServletRequest.getHeader("ESB-UserName"), agentCompanyCode)){
					String s1 = ExceptionTypeEnum.A000101.getMessage().replace("%A", httpServletRequest.getHeader("ESB-UserName"));
					String s2 = s1.replace("%B", agentCompanyCode);
					LOGGER.info(s2);
					
					ldpExceptionDto.setDetailedInfo(s2);
					ldpExceptionDto.setMessage(s2);
					ldpExceptionDto.setExceptionType(ExceptionTypeEnum.A000101.getTypeCode());
					ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.A000101.getCode());
					
					httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
					//返回异常信息
					return getJsonForException(ldpExceptionDto, objectMapper);
				}
				if(StringUtils.isBlank(handoverTimeStr)){
					handoverTime = DateUtils.convert(DateUtils.convert(Calendar.getInstance().getTime(), 
							DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
				}else{
					handoverTime = DateUtils.convert(handoverTimeStr, DateUtils.DATE_FORMAT);
				}
			}else if(StringUtils.isNotBlank(handoverNo)){
				handoverTime = null;
			}else{
				LOGGER.info(ExceptionTypeEnum.A000102.getMessage());
				ldpExceptionDto.setMessage(ExceptionTypeEnum.A000102.getMessage());
				ldpExceptionDto.setExceptionType(ExceptionTypeEnum.A000102.getTypeCode());
				ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.A000102.getCode());
				
				httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
				//返回异常信息
				return getJsonForException(ldpExceptionDto,objectMapper);
			}
			
			List<LdpHandOverDto> resultList = expressHandOverBillService.
					queryLDPHandOverBill(handoverNo, agentCompanyCode, handoverTime);
			if(CollectionUtils.isEmpty(resultList)){
				resultList = new ArrayList<LdpHandOverDto>();
			}
				
			try{
				//把结果list转换成json数据
				resultJsonStr = objectMapper.writeValueAsString(resultList);
			} catch (Exception e) {
				LOGGER.info("快递代理公司查询交接单查询结果转换json数据出错！" + e.toString());
				ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
				ldpExceptionDto.setMessage(ExceptionTypeEnum.S000005.getMessage());
				ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000005.getTypeCode());
				ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000005.getCode());
				
				httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
				//返回异常信息
				return getJsonForException(ldpExceptionDto,objectMapper);
			}
		}catch(Exception e){
			LOGGER.info("查询快递代理交接单数据出错", e); 
			ldpExceptionDto.setMessage("查询快递代理交接单数据出错！");

			ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
			ldpExceptionDto.setMessage(ExceptionTypeEnum.S000099.getMessage());
			ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000099.getTypeCode());
			ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000099.getCode());
			
			httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
			//返回异常信息
			return getJsonForException(ldpExceptionDto, objectMapper);
		}
		LOGGER.info("返回结果json：" + resultJsonStr);
		
		return resultJsonStr;
	}
	/**
	 * 快递代理外发单接口（外围系统）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午9:01:30
	 */
	@POST
	@Path("/queryExternalBillList")
	public String queryLdpExternalBillListWithJson(@Context HttpServletRequest httpServletRequest,
												   @Context HttpServletResponse httpServletResponse, 
												   String jsonStr) {
		LOGGER.info("查询外发单：Header-----> " + "ESB-UserName: " + httpServletRequest.getHeader("ESB-UserName") + 
									  "ESB-ESBServiceCode: " + httpServletRequest.getHeader("ESB-ESBServiceCode") + 
									  "ESB-Version: " + httpServletRequest.getHeader("ESB-Version") +
									  "JSON: " + jsonStr);
		
		httpServletResponse.setHeader("ESB-UserName", httpServletRequest.getHeader("ESB-UserName"));
		httpServletResponse.setHeader("ESB-ESBServiceCode",	httpServletRequest.getHeader("ESB-ESBServiceCode"));
		httpServletResponse.setHeader("ESB-Version", httpServletRequest.getHeader("ESB-Version"));
		httpServletResponse.setHeader("ESB-Password", httpServletRequest.getHeader("ESB-Password"));
		httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_SUCCESS);

		
		//执行正常返回的结果信息
		String resultJsonStr = "";
		LdpExceptionDto ldpExceptionDto = new LdpExceptionDto();
		LdpExternalBillDto queryDto = new LdpExternalBillDto();
		ObjectMapper objectMapper = null;
		String handOverNo = "";
		String waybillNo = "";
		try{
			// 获取objectMapper
			objectMapper =	obtainJSONObjectMapper();
			
			//设置过滤器，转换成json格式时转换配置属性PartiallineConstants.LDP_JSON_RESULT_HEADS
			SimpleFilterProvider fileter = new SimpleFilterProvider();
			fileter.addFilter("includeFilter", SimpleBeanPropertyFilter.filterOutAllExcept(PartiallineConstants.LDP_JSON_RESULT_HEADS));
			objectMapper.setFilters(fileter);
			BidBeanSerializerFactory bidBeanFactory = BidBeanSerializerFactory.instance;
			bidBeanFactory.setFilterId("includeFilter"); 
			objectMapper.setSerializerFactory(bidBeanFactory);
			
			try{
				LOGGER.info("查询快递代理外发单传入参数："+jsonStr);
				//解析传入的json参数
				JsonFactory jfactory = new JsonFactory();
				JsonParser jParser = jfactory.createJsonParser(jsonStr);
				while (jParser.nextToken() != JsonToken.END_OBJECT) {
					String fieldname = jParser.getCurrentName();
					if ("handOverNo".equals(fieldname)) {
					  jParser.nextToken();
					  handOverNo = jParser.getText();
					}else if ("waybillNo".equals(fieldname)) {
					  jParser.nextToken();
					  waybillNo = jParser.getText();
					}
				}
				jParser.close();
			} catch (Exception e) {
				LOGGER.info("快递代理公司查询外发单解析参数出错！参数为：" + jsonStr + " 出错信息：" + ExceptionUtils.getFullStackTrace(e));
				ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
				ldpExceptionDto.setMessage(ExceptionTypeEnum.S000004.getMessage());
				ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000004.getTypeCode());
				ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000004.getCode());
				
				httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
				//返回异常信息
				return getJsonForException(ldpExceptionDto, objectMapper);
			}
			/**
			 * 查询条件：
			 * 1）	交接单号: 此时返回该交接单号对应的所有外发单信息；或者
			 * 2）	运单号：此时返回所查询的运单号对应的外发单，正常情况如果两个字段都有，则只使用交接单号来查询。
			 */
			if(StringUtils.isNotBlank(handOverNo)){
				queryDto.setHandoverNo(handOverNo);
			}else if(StringUtils.isNotBlank(waybillNo)){
				queryDto.setWaybillNo(waybillNo);
			}else{
				LOGGER.info("查询快递代理外发单参数不能为空！传入参数为:" + jsonStr);
				
				ldpExceptionDto.setMessage(ExceptionTypeEnum.A000202.getMessage());
				ldpExceptionDto.setExceptionType(ExceptionTypeEnum.A000202.getTypeCode());
				ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.A000202.getCode());
				
				httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
				//返回异常信息
				return getJsonForException(ldpExceptionDto, objectMapper);
			}
			//有效运单
			queryDto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_CHAR_VALUE);
			queryDto.setAgentCompanyCode(httpServletRequest.getHeader("ESB-UserName")); //只查询传入的代理公司编号对应的外发单信息
			//外发单待审核、已审核
			List<String> auditStatusList = new ArrayList<String>();
			auditStatusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			auditStatusList.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			queryDto.setList(auditStatusList);
			List<LdpExternalBillDto> ldpExternalBillDtoList = ldpExternalBillService.queryExternalBillInfoList(queryDto, false);
			if(CollectionUtils.isEmpty(ldpExternalBillDtoList)){
				ldpExternalBillDtoList = new ArrayList<LdpExternalBillDto>();
			}
			try{
				for(LdpExternalBillDto dto: ldpExternalBillDtoList){
					if(dto != null && StringUtils.equals(dto.getAuditStatus(), PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT)){
						dto.setFreightFee(null); //外发运费
						dto.setExternalInsuranceFee(null); //外发保价费
						dto.setInsuranceFee(null);
						dto.setCodAgencyFee(null); //代收货款手续费
						dto.setPaymentCollectionFee(null);
					}
					
					ldpExternalBillFeeDataConverting(dto);
				}
				//把结果list转换成json数据
				resultJsonStr = objectMapper.writeValueAsString(ldpExternalBillDtoList);
			} catch (Exception e) {
				LOGGER.info("快递代理公司查询外发单查询结果转换json数据出错！" + ExceptionUtils.getFullStackTrace(e));

				ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
				ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000005.getTypeCode());
				ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000005.getCode());
				
				httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
				//返回异常信息
				return getJsonForException(ldpExceptionDto,objectMapper);
			}
		}catch(Exception e){
			LOGGER.info("查询快递代理外发单数据出错", e);
			ldpExceptionDto.setMessage("查询快递代理外发单数据出错！");
		
			ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
			ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000099.getTypeCode());
			ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000099.getCode());
			
			httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
			//返回异常信息
			return getJsonForException(ldpExceptionDto, objectMapper);
		}
		
		LOGGER.info("返回结果json：" + resultJsonStr);
		
		return resultJsonStr;
	}
	
	/**
	 * 上传快递代理外发单签收结果
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-05 上午11:01:32
	 */
	@POST
	@Path("/addSignUpResultList")
	@Transactional
	public String addSignUpResultListFromEsb(@Context HttpServletRequest httpServletRequest,
						                     @Context HttpServletResponse httpServletResponse, 
						                     String jsonStr) {
		LOGGER.info("上传签收结果：Header-----> " + "ESB-UserName: " + httpServletRequest.getHeader("ESB-UserName") + 
				  "ESB-ESBServiceCode: " + httpServletRequest.getHeader("ESB-ESBServiceCode") + 
				  "ESB-Version: " + httpServletRequest.getHeader("ESB-Version") +
				  "JSON: " + jsonStr);
		Date startTime = Calendar.getInstance().getTime();
		LOGGER.info("开始执行addSignUpResultListFromEsb快递代理签收 -------->" + DateUtils.convert(startTime, DateUtils.DATE_TIME_FORMAT));
		String agentCompanyCode = StringUtils.trimToEmpty(httpServletRequest.getHeader("ESB-UserName"));
		httpServletResponse.setHeader("ESB-UserName", agentCompanyCode);
		httpServletResponse.setHeader("ESB-ESBServiceCode",	httpServletRequest.getHeader("ESB-ESBServiceCode"));
		httpServletResponse.setHeader("ESB-Version", httpServletRequest.getHeader("ESB-Version"));
		httpServletResponse.setHeader("ESB-Password", httpServletRequest.getHeader("ESB-Password"));
		httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_SUCCESS);
		
		StringBuffer resultJsonStr = new StringBuffer("["); 
		ObjectMapper objectMapper =	null;
		List<LdpSignUpResultDto> signUpList = null;
		LdpExceptionDto ldpExceptionDto = new LdpExceptionDto();
		LdpSignUpExceptionDto ldpSignUpExceptionDto = new LdpSignUpExceptionDto();
		try{
			objectMapper = obtainJSONObjectMapper();
			LdpSignUpResultDto[] dtoArr = objectMapper.readValue(jsonStr, LdpSignUpResultDto[].class);
			signUpList = new ArrayList<LdpSignUpResultDto>();
			Collections.addAll(signUpList, dtoArr); 
		} catch (Exception e) {
			LOGGER.info("快递代理公司上传外发单签收结果解析参数出错！参数为：" + jsonStr + ", 错误信息：" + ExceptionUtils.getFullStackTrace(e));
			ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
			ldpExceptionDto.setMessage(ExceptionTypeEnum.S000004.getMessage());
			ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000004.getTypeCode());
			ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000004.getCode());
			
			httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
			//返回异常信息
			return getJsonForException(ldpExceptionDto,objectMapper);
		}	
			
		//循环插入签收记录，某条记录出错不影响其他，返回错误记录
		for(LdpSignUpResultDto signUpDto : signUpList){
			try{
				WaybillSignResultEntity expressSignEntity = new WaybillSignResultEntity();
				
				validateLdpSignUp(signUpDto, agentCompanyCode, expressSignEntity);
				
				expressSignEntity.setWaybillNo(signUpDto.getWaybillNo());
				//代理编码
				expressSignEntity.setAgentCode(signUpDto.getAgentOrgCode());
				//到达时间
				expressSignEntity.setArriveTime(signUpDto.getReceiveTime());
				//送货时间
				expressSignEntity.setDeliverDate(signUpDto.getDeliveryTime());
				//快递员名称 = 送货人(代理的员工)
				expressSignEntity.setExpressEmpName(signUpDto.getDeliveryUserName());
				//签收时间
				expressSignEntity.setSignTime(signUpDto.getSignUpTime());
				//签收人(客户)
				expressSignEntity.setDeliverymanName(signUpDto.getSignUpUserName());
				//签收件数
				expressSignEntity.setSignGoodsQty(signUpDto.getGoodsNum());
				//签收备注 = 异常描述
				expressSignEntity.setSignNote(signUpDto.getExceptionDescribe());
				
				if(StringUtils.equals(Constants.SIGN_STATUS_REFUSE, signUpDto.getSignUpType())){
					LdpExternalBillTrackDto trackDto = new LdpExternalBillTrackDto();
					
					trackDto.setTraceId(signUpDto.getSignUpId());
					trackDto.setWaybillNo(signUpDto.getWaybillNo());
					trackDto.setAgentCompanyCode(signUpDto.getAgentCompanyCode());
					trackDto.setAgentCompanyName(signUpDto.getAgentCompanyName());
					trackDto.setAgentOrgCode(signUpDto.getAgentOrgCode());
					trackDto.setAgentOrgName(signUpDto.getAgentOrgName());
					trackDto.setOperationcity(signUpDto.getAgentCompanyName());
					trackDto.setOperationtype(Constants.LDP_TRACK_OPERATION_TYPE_DEFUSE_SIGN);
					trackDto.setDispatchname(signUpDto.getDeliveryUserName());
					trackDto.setOperationTime(signUpDto.getSignUpTime());
					trackDto.setOperationUserName(signUpDto.getDeliveryUserName());
					trackDto.setOperationDescribe(signUpDto.getExceptionDescribe());
					
					ldpExternalBillTrackService.addLdpExternalBillTrack(trackDto);
				}else{
					airAgencySignService.addExpressAgentSignResultForTfr(expressSignEntity);
				}
			}catch(AgentException e){
				LOGGER.info("快递代理公司上传外发单签收失败！" + ExceptionUtils.getFullStackTrace(e));
				
				ldpSignUpExceptionDto.setSignUpId(signUpDto.getSignUpId());
				ldpSignUpExceptionDto.setSuccess(Constants.RESULT_FAILURE);
				ldpSignUpExceptionDto.setMessage(e.getBizMessage());
				ldpSignUpExceptionDto.setExceptionType(e.getExceptionTypeEnum().getTypeCode());
				ldpSignUpExceptionDto.setExceptionCode(e.getExceptionTypeEnum().getCode());
				
				String returnJsonStr = getJsonForSignUpException(ldpSignUpExceptionDto,objectMapper);
				LOGGER.info("此异常信息对应的JSON：" + resultJsonStr.toString());
				
				resultJsonStr.append(returnJsonStr).append(",");
			}catch(BusinessException e){
				LOGGER.info("快递代理公司上传外发单签收失败！运单号为：" + signUpDto.getWaybillNo() + ", 错误信息：" + ExceptionUtils.getFullStackTrace(e));
				
				ldpSignUpExceptionDto.setSignUpId(signUpDto.getSignUpId());
				
				ldpSignUpExceptionDto.setSuccess(Constants.RESULT_FAILURE);
				
				if(StringUtils.equals("pkp.sign.airAgencySignException.waybillIsSigned", e.getErrorCode())){
					ldpSignUpExceptionDto.setMessage("此运单号 "+ signUpDto.getWaybillNo() +" 已签收");
					ldpSignUpExceptionDto.setExceptionType(ExceptionTypeEnum.A000307.getTypeCode());
					ldpSignUpExceptionDto.setExceptionCode(ExceptionTypeEnum.A000307.getCode());
				}else{
					ldpSignUpExceptionDto.setMessage(e.getErrorCode());
					ldpSignUpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
					ldpSignUpExceptionDto.setExceptionType(ExceptionTypeEnum.S000099.getTypeCode());
					ldpSignUpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000099.getCode());
				}
				
				String returnJsonStr = getJsonForSignUpException(ldpSignUpExceptionDto, objectMapper);
				LOGGER.info("此异常信息对应的JSON：" + resultJsonStr.toString());
				
				resultJsonStr.append(returnJsonStr).append(",");
			}catch(Exception e){
				LOGGER.info("快递代理公司上传外发单签收失败！运单号为：" + signUpDto.getWaybillNo() + ", 错误信息：" + ExceptionUtils.getFullStackTrace(e));
				
				ldpSignUpExceptionDto.setSignUpId(signUpDto.getSignUpId());
				ldpSignUpExceptionDto.setSuccess(Constants.RESULT_FAILURE);
				ldpSignUpExceptionDto.setMessage(e.getMessage());
				ldpSignUpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
				ldpSignUpExceptionDto.setExceptionType(ExceptionTypeEnum.S000099.getTypeCode());
				ldpSignUpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000099.getCode());
				
				String returnJsonStr = getJsonForSignUpException(ldpSignUpExceptionDto, objectMapper);
				LOGGER.info("此异常信息对应的JSON：" + resultJsonStr.toString());
				
				resultJsonStr.append(returnJsonStr).append(",");
			}
		}
		/**
		 * 只返回失败的签收单流水号，成功的不必返回；如果全部成功，可以各字段可以返回空。
		 */
		
		Date endTime = Calendar.getInstance().getTime();
		LOGGER.info("完成执行addSignUpResultListFromEsb快递代理签收 -------->" + DateUtils.convert(endTime, DateUtils.DATE_TIME_FORMAT) + "用时 ---->" + DateUtils.getSecondDiff(endTime, startTime) + " 秒");
		LOGGER.info("返回的JSON：" + resultJsonStr.toString());
		//如果全部成功，可以各字段可以返回空。
		if(resultJsonStr.length() < 2){
			return "[]";
		}else{
			//返回失败的签收单流水号
			return resultJsonStr.substring(0, resultJsonStr.length() - 1) + "]";
		}
			
	}

	private void validateLdpSignUp(LdpSignUpResultDto signUpDto, String agentCompanyCode, WaybillSignResultEntity expressSignEntity) {
		if(signUpDto == null){
			throw new AgentException("输入的signUpDto对象为空", ExceptionTypeEnum.A000303);
		}
		
		if(StringUtils.isBlank(signUpDto.getSignUpId())){
			throw new AgentException("输入的轨迹流水号signUpId为空", ExceptionTypeEnum.A000303);
		}
		
		if(StringUtils.isBlank(signUpDto.getWaybillNo())){
			throw new AgentException("输入的运单号waybillNo为空", ExceptionTypeEnum.A000303);
		}
		
		boolean waybillNoExist = waybillManagerService.isWayBillExsits(signUpDto.getWaybillNo());
		if(!waybillNoExist){
			String message = ExceptionTypeEnum.A000302.getMessage()
			        .replace("%S", signUpDto.getWaybillNo());
			throw new AgentException(message, ExceptionTypeEnum.A000302);
		}
		
		if(StringUtils.isBlank(signUpDto.getAgentCompanyCode())){
			throw new AgentException("输入的代理公司编号agentCompanyCode为空", ExceptionTypeEnum.A000303);
		}
		
		if(!StringUtils.equals(agentCompanyCode, signUpDto.getAgentCompanyCode())){
			String message = ExceptionTypeEnum.A000301.getMessage()
					        .replace("%A", agentCompanyCode)
					        .replace("%B", signUpDto.getAgentCompanyCode());
			throw new AgentException(message, ExceptionTypeEnum.A000301);
		}
		
		if(StringUtils.isBlank(signUpDto.getAgentOrgCode())){
			throw new AgentException("输入的代理网点编码agentOrgCode为空", ExceptionTypeEnum.A000303);
		}
		
		BusinessPartnerExpressEntity businessPartnerExpressEntity = ldpAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(signUpDto.getAgentOrgCode(), FossConstants.ACTIVE);
		if(businessPartnerExpressEntity == null){
			throw new AgentException(signUpDto.getAgentOrgCode() + "未找到对应的代理公司，请检查传入的代理网点编号", ExceptionTypeEnum.A000308);
		}else{
			if(!StringUtils.equals(businessPartnerExpressEntity.getAgentCompanyCode(), signUpDto.getAgentCompanyCode())){
				String message = ExceptionTypeEnum.A000308.getMessage()
				        .replace("%A", signUpDto.getAgentOrgCode())
				        .replace("%B", signUpDto.getAgentCompanyCode());
				throw new AgentException(message, ExceptionTypeEnum.A000308);
			}
		}
		
		if(StringUtils.isBlank(signUpDto.getDeliveryUserName())){
			throw new AgentException("输入的快递员名称(送货人-代理的员工)deliveryUserName为空", ExceptionTypeEnum.A000303);
		}
		
		if(null == signUpDto.getSignUpTime()){
			throw new AgentException("输入的签收时间signUpTime为空", ExceptionTypeEnum.A000303);
		}
		
		if(StringUtils.isBlank(signUpDto.getSignUpUserName())){
			throw new AgentException("输入的签收人(客户)姓名signUpUserName为空", ExceptionTypeEnum.A000303);
		}
		
		if(!StringUtils.equals(Constants.SIGN_STATUS_REFUSE, signUpDto.getSignUpType()) && signUpDto.getGoodsNum() == 0){
			throw new AgentException("输入的签收件数goodsNum为0", ExceptionTypeEnum.A000303);
		}
		
		if(StringUtils.isBlank(signUpDto.getSignUpType())){
			throw new AgentException("输入的签收类型signUpType为空", ExceptionTypeEnum.A000303);
		}
		if(StringUtils.equals(Constants.SIGN_STATUS_ALL, signUpDto.getSignUpType()) ||
		   StringUtils.equals(Constants.SIGN_STATUS_REFUSE, signUpDto.getSignUpType())){
			if(StringUtils.equals(Constants.SIGN_STATUS_ALL, signUpDto.getSignUpType())){
				expressSignEntity.setSignSituation(SignConstants.NORMAL_SIGN);
				expressSignEntity.setSignStatus(SignConstants.SIGN_STATUS_ALL);
			}
		}else{
			String message = ExceptionTypeEnum.A000305.getMessage().replace("%S", signUpDto.getSignUpType());
			throw new AgentException(message, ExceptionTypeEnum.A000305);
		}
		//通过快递代理网点传来的运单号、快递代理网点编码，查询快递代理外发单信息，获取外发外场名称、编号及外发员名称及编号，此4项作为签收的操作人、操作部门，此方式今后不支持分配配载的情况，必须运单下所有件一次性放在一个交接单中处理
		List<LdpExternalBillDto> list = ldpExternalBillService.queryExternalBillListForLdpSign(signUpDto.getWaybillNo());
		if(CollectionUtils.isEmpty(list)){
			LOGGER.info("没找到对应的外发单，运单号：" + signUpDto.getWaybillNo() + ", 快递代理网点：" + signUpDto.getAgentOrgCode());
			throw new AgentException(ExceptionTypeEnum.S000099.getMessage(), ExceptionTypeEnum.S000099);
		}else if(list.size() > 1){
			LOGGER.info("找到多条对应的外发单，运单号：" + signUpDto.getWaybillNo() + ", 快递代理网点：" + signUpDto.getAgentOrgCode());
			throw new AgentException(ExceptionTypeEnum.S000099.getMessage(), ExceptionTypeEnum.S000099);
		}else{
			LdpExternalBillDto dto = list.get(0);
			//操作人姓名
			expressSignEntity.setCreator(dto.getExternalUserName());
			//操作人编号
			expressSignEntity.setCreatorCode(dto.getExternalUserCode());
			//操作网点名称
			expressSignEntity.setCreateOrgName(dto.getExternalOrgName());
			//操作网点编码
			expressSignEntity.setCreateOrgCode(dto.getExternalOrgCode());
		}
		
//		转换签收异常信息描述,如果传过来的异常信息不是按照文档编码的，不做转换，直接保存
		String changeStr = Constants.signExceptionDescribeMap.get(signUpDto.getExceptionDescribe());
		String descStr = "";
		if(StringUtils.isBlank(changeStr)){
			descStr = signUpDto.getExceptionDescribe();
		}else{
			descStr = changeStr;
		}
		signUpDto.setExceptionDescribe(descStr);
	}
	
	/**
	 * 上传快递代理外发单轨迹记录
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-05 上午11:01:32
	 */
	@POST
	@Path("/addExternalBillTrackList")
	@Transactional
	public String addExternalBillTrackFromEsb(@Context HttpServletRequest httpServletRequest,
										      @Context HttpServletResponse httpServletResponse, 
										      String jsonStr) {
		LOGGER.info("上传轨迹：Header-----> " + "ESB-UserName: " + httpServletRequest.getHeader("ESB-UserName") + 
				  "ESB-ESBServiceCode: " + httpServletRequest.getHeader("ESB-ESBServiceCode") + 
				  "ESB-Version: " + httpServletRequest.getHeader("ESB-Version") +
				  "JSON: " + jsonStr);
		String agentCompanyCode = StringUtils.trimToEmpty(httpServletRequest.getHeader("ESB-UserName"));
		httpServletResponse.setHeader("ESB-UserName", agentCompanyCode);
		httpServletResponse.setHeader("ESB-ESBServiceCode",	httpServletRequest.getHeader("ESB-ESBServiceCode"));
		httpServletResponse.setHeader("ESB-Version", httpServletRequest.getHeader("ESB-Version"));
		httpServletResponse.setHeader("ESB-Password", httpServletRequest.getHeader("ESB-Password"));
		httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_SUCCESS);
		
		StringBuffer resultJsonStr = new StringBuffer("["); 
		ObjectMapper objectMapper =	null;
		List<LdpExternalBillTrackDto> trackList = null;
		LdpExceptionDto ldpExceptionDto = new LdpExceptionDto();
		LdpTrackExceptionDto ldpTrackExceptionDto = new LdpTrackExceptionDto();
		try{
			objectMapper = obtainJSONObjectMapper();
			LdpExternalBillTrackDto[] dtoArr = objectMapper.readValue(jsonStr, LdpExternalBillTrackDto[].class);
			trackList = new ArrayList<LdpExternalBillTrackDto>();
			Collections.addAll(trackList, dtoArr); 
		} catch (Exception e) {
			LOGGER.info("上传快递代理外发单轨迹解析参数出错！参数为：" + jsonStr + ", 错误信息：" + e.toString());
			ldpExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
			ldpExceptionDto.setMessage(ExceptionTypeEnum.S000004.getMessage());
			ldpExceptionDto.setExceptionType(ExceptionTypeEnum.S000004.getTypeCode());
			ldpExceptionDto.setExceptionCode(ExceptionTypeEnum.S000004.getCode());
			
			httpServletResponse.setHeader("ESB-ResultCode", Constants.RESULT_FAILURE);
			//返回异常信息
			return getJsonForException(ldpExceptionDto,objectMapper);
		}	
			
		//循环插入轨迹记录，某条记录出错不影响其他，返回错误记录
		for(LdpExternalBillTrackDto trackDto : trackList){
			try{
				validateLdpExternalBillTrack(trackDto, agentCompanyCode);
				
				ldpExternalBillTrackService.addLdpExternalBillTrack(trackDto);
			} catch(AgentException e){
				LOGGER.info("上传快递代理外发单轨迹出错!" + ExceptionUtils.getFullStackTrace(e));
				
				ldpTrackExceptionDto.setTraceId(trackDto.getTraceId());
				ldpTrackExceptionDto.setSuccess(Constants.RESULT_FAILURE);
				ldpTrackExceptionDto.setMessage(e.getBizMessage());
				ldpTrackExceptionDto.setExceptionType(e.getExceptionTypeEnum().getTypeCode());
				ldpTrackExceptionDto.setExceptionCode(e.getExceptionTypeEnum().getCode());
				
				String returnJsonStr = getJsonForTrackException(ldpTrackExceptionDto,objectMapper);
				LOGGER.info("此异常信息对应的JSON：" + resultJsonStr.toString());
				
				resultJsonStr.append(returnJsonStr).append(",");
			} catch(BusinessException e){
				LOGGER.info("上传快递代理外发单轨迹出错!" + ExceptionUtils.getFullStackTrace(e));
				
				ldpTrackExceptionDto.setTraceId(trackDto.getTraceId());
				
				ldpTrackExceptionDto.setSuccess(Constants.RESULT_FAILURE);
				ldpTrackExceptionDto.setMessage(e.getErrorCode());
				ldpTrackExceptionDto.setExceptionType(ExceptionTypeEnum.A000099.getTypeCode());
				ldpTrackExceptionDto.setExceptionCode(ExceptionTypeEnum.A000099.getCode());
				
				String returnJsonStr = getJsonForTrackException(ldpTrackExceptionDto,objectMapper);
				LOGGER.info("此异常信息对应的JSON：" + resultJsonStr.toString());
				
				resultJsonStr.append(returnJsonStr).append(",");
			}catch(Exception e){
				LOGGER.info("上传快递代理外发单轨迹出错!运单号为：" + trackDto.getWaybillNo(), e);
				
				ldpTrackExceptionDto.setTraceId(trackDto.getTraceId());
				ldpTrackExceptionDto.setSuccess(Constants.RESULT_FAILURE);
				ldpTrackExceptionDto.setMessage(ExceptionTypeEnum.S000099.getMessage());
				ldpTrackExceptionDto.setDetailedInfo(ExceptionUtils.getFullStackTrace(e));
				ldpTrackExceptionDto.setExceptionType(ExceptionTypeEnum.S000099.getTypeCode());
				ldpTrackExceptionDto.setExceptionCode(ExceptionTypeEnum.S000099.getCode());
				
				String returnJsonStr = getJsonForTrackException(ldpTrackExceptionDto,objectMapper);
				LOGGER.info("此异常信息对应的JSON：" + resultJsonStr.toString());
				
				resultJsonStr.append(returnJsonStr).append(",");
			}
		}
		/**
		 * 只返回失败的轨迹流水号，成功的不必返回；如果全部成功，可以各字段可以返回空。
		 */
		LOGGER.info("返回的JSON：" + resultJsonStr.toString());
		//如果全部成功，可以各字段可以返回空。
		if(resultJsonStr.length() < 2){
			return "[]";
		}else{
			//返回失败的轨迹流水号
			return resultJsonStr.substring(0, resultJsonStr.length()-1)+"]";
		}
	}
	
	private void validateLdpExternalBillTrack(LdpExternalBillTrackDto trackDto, String agentCompanyCode) {
		if(trackDto == null){
			throw new AgentException("输入的trackDto对象为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getTraceId())){
			throw new AgentException("输入的轨迹流水号traceID为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getWaybillNo())){
			throw new AgentException("输入的运单号waybillNo为空", ExceptionTypeEnum.A000403);
		}
		
		boolean waybillNoExist = waybillManagerService.isWayBillExsits(trackDto.getWaybillNo());
		if(!waybillNoExist){
			String message = ExceptionTypeEnum.A000402.getMessage()
			        .replace("%S", trackDto.getWaybillNo());
			throw new AgentException(message, ExceptionTypeEnum.A000402);
		}
		
		if(StringUtils.isBlank(trackDto.getAgentCompanyCode())){
			throw new AgentException("输入的代理公司编号agentCompanyCode为空", ExceptionTypeEnum.A000403);
		}
		
		if(!StringUtils.equals(agentCompanyCode, trackDto.getAgentCompanyCode())){
			String message = ExceptionTypeEnum.A000401.getMessage()
					        .replace("%A", agentCompanyCode)
					        .replace("%B", trackDto.getAgentCompanyCode());
			throw new AgentException(message, ExceptionTypeEnum.A000401);
		}
		
		if(StringUtils.isBlank(trackDto.getAgentCompanyName())){
			throw new AgentException("输入的代理公司名称agentCompancyName为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getAgentOrgCode())){
			throw new AgentException("输入的代理网点编码agentOrgCode为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getAgentOrgName())){
			throw new AgentException("输入的代理网点名称agentOrgName为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getOperationcity())){
			throw new AgentException("输入的操作所在城市operationcity为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getOperationtype())){
			throw new AgentException("输入的操作类型operationtype为空", ExceptionTypeEnum.A000403);
		}
		
		if(!PartiallineConstants.operationTypeMap.containsKey(trackDto.getOperationtype())){
			String message = ExceptionTypeEnum.A000405.getMessage()
			        .replace("%S", trackDto.getOperationtype());
			throw new AgentException(message, ExceptionTypeEnum.A000405);
		}
		
		if(StringUtils.isBlank(trackDto.getOperationUserName())){
			throw new AgentException("输入的操作人姓名operationUserName为空", ExceptionTypeEnum.A000403);
		}
		
		if(StringUtils.isBlank(trackDto.getOperationDescribe())){
			throw new AgentException("输入的操作描述operationDescribe为空", ExceptionTypeEnum.A000403);
		}
		
	}

	/**
	 * 设置 json 数据格式
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-01 上午9:21:30
	 */
	public static ObjectMapper obtainJSONObjectMapper() {
		// 获取objectMapper
		ObjectMapper objectMapper = JSONUtils.obtainObjectMapper();
		// 设置时间转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtils.DATE_TIME_FORMAT);
		// 设置到objectMapper
		objectMapper.setDateFormat(dateFormat);
		
		return objectMapper;
	}
	
	/**
	 * 发生异常时返回  json格式异常信息
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-02 下午4:21:30
	 */
	public String getJsonForException(LdpExceptionDto ldpExceptionDto, ObjectMapper objectMapper) {
		String returnStr = "";
		try{
			ldpExceptionDto.setCreatedTime(DateUtils.convert(DateUtils.convert(Calendar.getInstance().getTime())));
			
			returnStr = objectMapper.writeValueAsString(ldpExceptionDto);
		}catch(Exception e){
			LOGGER.info("拼接异常信息字符串时发生异常" + e);
			
			returnStr = "{\"createdTime\": \"" + DateUtils.convert(Calendar.getInstance().getTime())
					   + "\",\"detailedInfo\": \"" + ExceptionUtils.getFullStackTrace(e)
					   + "\",\"message\": \"" + ExceptionTypeEnum.S000099.getMessage()
					   + "\",\"exceptionType\": \"" + ExceptionTypeEnum.S000099.getTypeCode()
					   + "\",\"exceptionCode\": \"" + ExceptionTypeEnum.S000099.getCode() + "\"}";
		}
		
		return returnStr;
	}
	
	/**
	 * 发生异常时返回  json格式异常信息(上传签收信息)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-02 下午4:21:30
	 */
	public String getJsonForSignUpException(LdpSignUpExceptionDto ldpSignUpExceptionDto, ObjectMapper objectMapper) {
		String returnStr = "";
		if(ldpSignUpExceptionDto == null || objectMapper == null){
			return returnStr;
		}
		try{
			ldpSignUpExceptionDto.setCreatedTime(DateUtils.convert(DateUtils.convert(Calendar.getInstance().getTime())));
			returnStr = objectMapper.writeValueAsString(ldpSignUpExceptionDto);
		}catch(Exception e){
			LOGGER.info("拼接异常信息字符串时发生异常" + e);
			
			returnStr = "{\"signUpId\": \"" + ldpSignUpExceptionDto.getSignUpId()
					  + "\",\"success\": \"" + ldpSignUpExceptionDto.getSuccess()
					  + "\",\"createdTime\": \"" + DateUtils.convert(Calendar.getInstance().getTime())
					  + "\",\"detailedInfo\": \"" + ExceptionUtils.getFullStackTrace(e)
					  + "\",\"message\": \"" + ExceptionTypeEnum.S000099.getMessage()
					  + "\",\"exceptionType\": \"" + ExceptionTypeEnum.S000099.getTypeCode()
					  + "\",\"exceptionCode\": \"" + ExceptionTypeEnum.S000099.getCode() + "\"}";
		}
		return returnStr;
	}
	
	/**
	 * 发生异常时返回  json格式异常信息(轨迹)
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-08-02 下午4:21:30
	 */
	public String getJsonForTrackException(LdpTrackExceptionDto ldpTrackExceptionDto, ObjectMapper objectMapper) {
		String returnStr = "";
		if(ldpTrackExceptionDto == null || objectMapper == null){
			return returnStr;
		}
		try{
			ldpTrackExceptionDto.setCreatedTime(DateUtils.convert(DateUtils.convert(Calendar.getInstance().getTime())));
			returnStr = objectMapper.writeValueAsString(ldpTrackExceptionDto);
		}catch(Exception e){
			LOGGER.info("拼接异常信息字符串时发生异常" + e);

			returnStr = "{\"traceId\": \"" + ldpTrackExceptionDto.getTraceId()
					  + "\",\"success\": \"" + ldpTrackExceptionDto.getSuccess()
					  + "\",\"createdTime\": \"" + DateUtils.convert(Calendar.getInstance().getTime())
					  + "\",\"detailedInfo\": \"" + ExceptionUtils.getFullStackTrace(e)
					  + "\",\"message\": \"" + ExceptionTypeEnum.S000099.getMessage()
					  + "\",\"exceptionType\": \"" + ExceptionTypeEnum.S000099.getTypeCode()
					  + "\",\"exceptionCode\": \"" + ExceptionTypeEnum.S000099.getCode() + "\"}";
		}
		return returnStr;
	}
	
	public void setExpressHandOverBillService(IExpressHandOverBillService expressHandOverBillService) {
		this.expressHandOverBillService = expressHandOverBillService;
	}

	public void setLdpExternalBillService(ILdpExternalBillService ldpExternalBillService) {
		this.ldpExternalBillService = ldpExternalBillService;
	}

	public void setLdpExternalBillTrackService(ILdpExternalBillTrackService ldpExternalBillTrackService) {
		this.ldpExternalBillTrackService = ldpExternalBillTrackService;
	}

	public void setAirAgencySignService(IAirAgencySignService airAgencySignService) {
		this.airAgencySignService = airAgencySignService;
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
	public void setLdpAgencyCompanyService(ILdpAgencyCompanyService ldpAgencyCompanyService) {
		this.ldpAgencyCompanyService = ldpAgencyCompanyService;
	}
	
	private void ldpExternalBillFeeDataConverting(LdpExternalBillDto ldpExternalBillDto) {
		BigDecimal payDpFee = ldpExternalBillDto.getPayDpFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getPayDpFee();
		BigDecimal codAmount = ldpExternalBillDto.getCodAmount() == null ? BigDecimal.ZERO : ldpExternalBillDto.getCodAmount();
		BigDecimal freightFee = ldpExternalBillDto.getFreightFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getFreightFee();
		BigDecimal externalInsuranceFee = ldpExternalBillDto.getExternalInsuranceFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getExternalInsuranceFee();
		BigDecimal codAgencyFee = ldpExternalBillDto.getCodAgencyFee() == null ? BigDecimal.ZERO : ldpExternalBillDto.getCodAgencyFee();
		BigDecimal billPayable = ldpExternalBillDto.getBillPayable() == null ? BigDecimal.ZERO : ldpExternalBillDto.getBillPayable();
		BigDecimal billReceiveable = ldpExternalBillDto.getBillReceiveable() == null ? BigDecimal.ZERO : ldpExternalBillDto.getBillReceiveable();
		
		ldpExternalBillDto.setPayDpFee(payDpFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 		// 到付金额
		ldpExternalBillDto.setCodAmount(codAmount.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 代收货款
		ldpExternalBillDto.setFreightFee(freightFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 外发运费（德邦付给代理）
		ldpExternalBillDto.setExternalInsuranceFee(externalInsuranceFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 保价费 (外发)
		ldpExternalBillDto.setInsuranceFee(externalInsuranceFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 保价费 (外发)
		ldpExternalBillDto.setCodAgencyFee(codAgencyFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 代收货款手续费
		ldpExternalBillDto.setPaymentCollectionFee(codAgencyFee.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP));
		ldpExternalBillDto.setBillPayable(billPayable.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 应付费用
		ldpExternalBillDto.setBillReceiveable(billReceiveable.divide(new BigDecimal(PricingConstants.YUTOFEN), 2, BigDecimal.ROUND_UP)); 	// 应收费用
	}
}