/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.settlement.costcontrolitf.server.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.adapter.jms.convert.ConvertException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.costcontrolitf.server.inter.IBillReceivableMonthlyServiceImpl;
import com.deppon.foss.util.define.ESBHeaderConstant;

/**
 * 月结提成接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:273272,date:2016-2-17 下午5:10:17, </p>
 * @author 273272 唐俊
 * @date 2016-2-17 下午5:10:17
 * @since
 * @version
 */
@Path("/")
public class BillReceivableMonthlyServiceImpl implements
		IBillReceivableMonthlyServiceImpl {
	@Context
	protected HttpServletRequest req;
	@Context
	protected HttpServletResponse resp;

	//日志
	private static final Logger LOG = LogManager.getLogger(BillReceivableMonthlyServiceImpl.class);
	//消息转换工具
	private static final ObjectMapper mapper = new ObjectMapper();
	
	private IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService;
	
	/**
	 * 组织架构Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/** 
	 * <p>月结提成总金额查询</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 下午5:10:17
	 * @param request
	 * @return 
	 * @see com.deppon.foss.service.IBillReceivableMonthlyServiceImpl#queryBillReceivableAmount(java.lang.String)
	 */
	@Override
	@POST
	@Path("/billReceivableAmount")
	public String queryBillReceivableAmount(String requestJSON) {
		LOG.info("\n\n FOSS对接财务月结报表开始");
		
		// ESB 表头
		resp.setHeader(ESBHeaderConstant.VERSION,
				req.getHeader(ESBHeaderConstant.VERSION));
		//ESB客户端编码
		resp.setHeader(ESBHeaderConstant.ESBSERVICECODE,
				"ESB_FSSC2ESB_PARTNER_MONTHLY_QUERY");
		//ESB服务端编码
		resp.setHeader(ESBHeaderConstant.BACKSERVICECODE,
				"FOSS_ESB2FOSS_PARTNER_MONTHLY_QUERY");
		resp.setHeader(ESBHeaderConstant.REQUESTID,
				req.getHeader(ESBHeaderConstant.REQUESTID));
		resp.setHeader(ESBHeaderConstant.BUSINESSID,
				req.getHeader(ESBHeaderConstant.BUSINESSID));
		resp.setHeader(ESBHeaderConstant.MESSAGEFORMAT,
				req.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		resp.setHeader(ESBHeaderConstant.EXCHANGEPATTERN,
				req.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		resp.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID()
				.toString());
		resp.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		
		Map<String, Object> request = new LinkedHashMap<String, Object>();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		String responseJson = "";
		try {
			request = toMessage(requestJSON); 
			//验证参数合法性
			validation(request);
			//获取期间和合伙人部门编号
			String period = (String) request.get("loanDate");
			String contractOrgCode = (String) request.get("loanDepartmentDp");
			String empCode = (String) request.get("loanEmp");
			//查询foss内部，部门编码
			OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByUnifiedCode(contractOrgCode);
			if(null == orgEntity){
				LOG.error("财务请求FOSS月结接口出错，合伙人部门信息为空！");
				throw new SettlementException("财务请求FOSS月结接口出错，合伙人部门信息为空！");
			}
			//查询月结提成金额
			BillReceivableDto dto = new BillReceivableDto();
			dto.setPeriod(period);
			dto.setContractOrgCode(orgEntity.getCode());
			dto.setEmpCode(empCode);
			LOG.info("\n\n FOSS对接财务月结报表。请求参数，peroid是："+dto.getPeriod()+"部门是："+dto.getContractOrgCode()+"empCode是："+dto.getEmpCode()+"\n");
			String amount = billReceivableMonthlyQueryService.amountBillReceivableByParam(
					periodToDate(dto));
			LOG.info("\n\n FOSS对接财务月结报表。查询期间从："+dto.getStartDate()+",到："+dto.getEndDate()+"\n");
			//将结果封装成map
			response.put("amount", amount);
			response.put("contractOrgCode", contractOrgCode);
			response.put("isSuccess", true);
		} catch (SettlementException e) {
			response.put("isSuccess", "false");
			response.put("message", "异常"+e.getErrorCode());
			LOG.error("FOSS对接财务月结报表。异常:" + e.getErrorCode());
		}catch (Exception e) {
			response.put("isSuccess", "false");
			response.put("message", "异常"+e.getMessage());
			LOG.error("转换异常:" + e.getMessage());
		} finally {
			LOG.info("\n\n对接财务月结报表，响应结果。是否成功:"+
					response.get("isSuccess")+",金额："+response.get("amount")+",申请部门编码："+response.get("contractOrgCode")+
					",错误消息："+response.get("message")+"\n\n");
			try {
				responseJson = fromMessage(response);
			} catch (ConvertException e) {
				LOG.error("消息转换失败:" + e.getMessage());
			}
		}
		resp.setHeader(ESBHeaderConstant.RESULTCODE, "1");
		LOG.info("\n\n FOSS对接财务月结报表结束");
		return responseJson;
	}
	
	/**
	 * <p>验证请求参数合法性</p> 
	 * @author 302346 蒋迅
	 * @date 2016-3-16 上午08:48:34
	 * @param Map
	 * @return N/A
	 * @see
	 */
	private void validation(Map<String, Object> request) {
		if(request.isEmpty()){
			LOG.error("财务请求FOSS月结接口出错，期间转化时间失败。");
			throw new SettlementException("财务请求FOSS月结接口出错，请求参数为空！");
		}
		if(!request.containsKey("loanDate")){
			LOG.error("财务请求FOSS月结接口出错，查询日期为空！");
			throw new SettlementException("财务请求FOSS月结接口出错，查询日期为空！");
		}
		if(!request.containsKey("loanDepartmentDp")){
			LOG.error("财务请求FOSS月结接口出错，查询部门为空！");
			throw new SettlementException("财务请求FOSS月结接口出错，查询部门为空！");
		}
		if(!request.containsKey("loanEmp")){
			LOG.error("财务请求FOSS月结接口出错，操作人为空！");
			throw new SettlementException("财务请求FOSS月结接口出错，操作人为空！");
		}
	}

	/**
	 * <p>将实体中的期间变了转换为上月1号0点0分0秒到当月的1号0点0分0秒</p> 
	 * @author 273272 唐俊
	 * @date 2016-1-29 下午1:44:34
	 * @param dto
	 * @return
	 * @see
	 */
	private BillReceivableDto periodToDate(BillReceivableDto dto) {
		BillReceivableDto billReceivableDto = dto;
		//分离期间的年份和月份
		String  period = billReceivableDto.getPeriod();
		if("".equals(period) || null == period ){
			return dto;
		}
		String year = period.substring(0, NumberConstants.NUMBER_4);
		String month = period.substring(NumberConstants.NUMBER_4, NumberConstants.NUMBER_6);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		//设置时间为1号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //设置时分秒为0时0分0秒
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
		try {
			//设置上月的1号0点0分0秒
			cal.set(Calendar.MONTH, Integer.parseInt(month)-2);
			billReceivableDto.setStartDate(sdf.parse(sdf.format(cal.getTime())));
			//设置当月的1号0点0分0秒
			cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
			billReceivableDto.setEndDate(sdf.parse(sdf.format(cal.getTime())));
		} catch (ParseException e) {
			 LOG.error("财务请求FOSS月结接口出错，期间转化时间失败:"+e.getMessage());
			 throw new SettlementException("财务请求FOSS月结接口出错，期间转化时间失败:"+e.getMessage());
		}
		return billReceivableDto;
	}
	/**
	 * 
	 * <p>将参数转化为json</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 下午5:40:11
	 * @param string
	 * @return
	 * @throws ConvertException
	 * @throws UnsupportedEncodingException
	 * @see
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> toMessage(String string)
			throws ConvertException, UnsupportedEncodingException {
		Map<String, Object> response = null;
		try {
			response = mapper.readValue(string, Map.class);
		} catch (JsonParseException e) {
			LOG.error("消息转换异常:".concat(e.getMessage()));
		} catch (JsonMappingException e) {
			LOG.error("消息转换异常:".concat(e.getMessage()));
		} catch (IOException e) {
			LOG.error("消息转换异常:".concat(e.getMessage()));
		}
		return response;
	}
	/**
	 * 
	 * <p>将结果转换为json</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 下午5:40:07
	 * @param message
	 * @return
	 * @throws ConvertException
	 * @see
	 */
	public String fromMessage(Map<String, Object> message)
			throws ConvertException {
		String json=null;
		try {
			json=mapper.writeValueAsString(message);
		} catch (JsonGenerationException e) {
			LOG.error("消息转换异常:".concat(e.getMessage()));
		} catch (JsonMappingException e) {
			LOG.error("消息转换异常:".concat(e.getMessage()));
		} catch (IOException e) {
			LOG.error("消息转换异常:".concat(e.getMessage()));
		}
		return json;
	}

	/**
	 * @return  the billReceivableMonthlyQueryService
	 */
	public IBillReceivableMonthlyQueryService getBillReceivableMonthlyQueryService() {
		return billReceivableMonthlyQueryService;
	}

	/**
	 * @param billReceivableMonthlyQueryService the billReceivableMonthlyQueryService to set
	 */
	public void setBillReceivableMonthlyQueryService(
			IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService) {
		this.billReceivableMonthlyQueryService = billReceivableMonthlyQueryService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	

}
