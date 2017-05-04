package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IQueryOrgAttitudeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OrgAttributeInfoDto;
import com.deppon.foss.util.define.ESBHeaderConstant;
/**
 * 
 * @author leo-zeng
 *
 */
public class QueryOrgAttributeService  implements IQueryOrgAttitudeService{
	
	private static final long serialVersionUID = 1L;
	//日志记录
	private static final Logger LOGGER = LoggerFactory.getLogger(QueryOrgAttributeService.class);
	@Context
	HttpServletRequest request;
	@Context
	HttpServletResponse response;
	/**
	 *组织架构接口 
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	
	@Override
	public OrgAttributeInfoDto queryOrgAttribute(String rertunString) {
		LOGGER.info("------------------------部门信息查询接口begin----------------------------");
		//设置消息头
		response.setHeader(ESBHeaderConstant.VERSION, request.getHeader(ESBHeaderConstant.VERSION));
		response.setHeader(ESBHeaderConstant.ESBSERVICECODE, "ESB_APP2ESB_QUERYDEPT_INFO");
		response.setHeader(ESBHeaderConstant.REQUESTID, request.getHeader(ESBHeaderConstant.REQUESTID));
		response.setHeader(ESBHeaderConstant.BUSINESSID, request.getHeader(ESBHeaderConstant.BUSINESSID));
		response.setHeader(ESBHeaderConstant.MESSAGEFORMAT, request.getHeader(ESBHeaderConstant.MESSAGEFORMAT));
		response.setHeader(ESBHeaderConstant.EXCHANGEPATTERN, request.getHeader(ESBHeaderConstant.EXCHANGEPATTERN));
		response.setHeader(ESBHeaderConstant.BACKSERVICECODE, request.getHeader(ESBHeaderConstant.BACKSERVICECODE));
		response.setHeader(ESBHeaderConstant.RESPONSEID, UUID.randomUUID().toString());
		response.setHeader(ESBHeaderConstant.SOURCESYSTEM, "FOSS");
		//转换成json obj
		JSONObject obj = JSONObject.fromObject(rertunString);
		OrgAttributeInfoDto dto = new OrgAttributeInfoDto();
		try {
			if(null!=obj){
				//得到部门标杆编码
				String orgCode =obj.getString("arrival_branch_code");
				//传过来要是个空的集合
				if(StringUtils.isBlank(orgCode)){
					dto.setMessage("部门标杆编码为空");
					dto.setSuccess(false);
					response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
					return dto;
				}
				//查询行政组织属性
				OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(orgCode);
				//查询是组织在foss 中不存在，
				if(null ==org){
					dto.setSuccess(false);
					dto.setMessage("部门标杆编码为:"+orgCode+"在foss 找不到对应的部门信息");
					response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
					return dto;
				}
				dto.setSuccess(true);
				dto.setOrgCode(orgCode);
				//判断是否为空的情况
				dto.setIsSalesDepartment(StringUtils.isBlank(org.getSalesDepartment())?"N":org.getSalesDepartment());
				//校验为空的情况
				dto.setIsExpVitrualSales(StringUtils.isBlank(org.getExpressSalesDepartment())?"N":org.getExpressSalesDepartment());
				response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
				LOGGER.info("------------------------部门信息查询接口end----------------------------");
			}else{
				dto.setSuccess(false);
				dto.setMessage("请求参数为空");
				response.setHeader("ESB-ResultCode", "0");
			}
			
		} catch (Exception e) {
			dto.setSuccess(false);
			dto.setMessage("数据异常....");
			response.setHeader("ESB-ResultCode", "0");
		}
		return dto;
	}

}
