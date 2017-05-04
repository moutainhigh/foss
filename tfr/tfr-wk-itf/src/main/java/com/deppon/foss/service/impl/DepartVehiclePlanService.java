package com.deppon.foss.service.impl;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IDepartVehiclePlanInfoService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.DepartVehiclePlanInfoQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.DepartPlanInfoDetailDto;
import com.deppon.foss.service.IDepartVehiclePlanService;
import com.deppon.foss.shared.request.DepartVehiclePlanReqParEntity;
import com.deppon.foss.shared.response.DepartVehiclePlanRespParEntity;

/**
 * 
* @description 获取计划发车信息Service (悟空系统调用接口)
* @version 1.0
* @author 283250-foss-ruilibao
* @update 2016-5-6 下午2:12:18
 */
public class DepartVehiclePlanService implements IDepartVehiclePlanService{

	// 日志
	private static final Logger logger = LogManager.getLogger(DepartVehiclePlanService.class);
	
	@Context
	private HttpServletResponse response;
	
	// 获取计划发车信息Service
	private IDepartVehiclePlanInfoService departVehiclePlanInfoService;
	
	public void setDepartVehiclePlanInfoService(
			IDepartVehiclePlanInfoService departVehiclePlanInfoService) {
		this.departVehiclePlanInfoService = departVehiclePlanInfoService;
	}

	/**
	 * 
	* @description 获取计划发车信息
	* @see com.deppon.foss.service.IDepartVehiclePlanService#getDepartVehiclePlanInfo(java.lang.String)
	* @author 283250-foss-ruilibao
	* @update 2016-5-6 下午2:12:40
	* @version V1.0
	 */
	@Override
	public Object getDepartVehiclePlanInfo(String reqMsg) {
		//返回结果定义
		DepartVehiclePlanRespParEntity result = new DepartVehiclePlanRespParEntity();
		DepartVehiclePlanReqParEntity reqParEntity = null;
		try {
			logger.info("WK获取计划发车信息开始"+reqMsg);
			reqParEntity = JSON.parseObject(reqMsg, DepartVehiclePlanReqParEntity.class);
			
			//车牌号不能为空
			if (StringUtils.isBlank(reqParEntity.getVehicleNo())) {
				result.setResultFlag(false);
				result.setFailureReason("车牌号不能为空");
				return JSONObject.fromObject(result).toString();
			}
			//交接时间不能为空
			if (reqParEntity.getHandoverTime() == null) {
				result.setResultFlag(false);
				result.setFailureReason("交接时间不能为空");
				return JSONObject.fromObject(result).toString();
			}
			// 出发部门编码不能为空
			if (StringUtils.isBlank(reqParEntity.getOrigOrgCode())) {
				result.setResultFlag(false);
				result.setFailureReason("出发部门编码不能为空");
				return JSONObject.fromObject(result).toString();
			}
			// 到达部门编码不能为空
			if (StringUtils.isBlank(reqParEntity.getArrivalDeptNo())) {
				result.setResultFlag(false);
				result.setFailureReason("到达部门编码不能为空");
				return JSONObject.fromObject(result).toString();
			}
			// 运输类型不能为空
			if (StringUtils.isBlank(reqParEntity.getTransportType())) {
				result.setResultFlag(false);
				result.setFailureReason("运输类型不能为空");
				return JSONObject.fromObject(result).toString();
			}
			// 发车计划类型
			if (StringUtils.isBlank(reqParEntity.getPlanType())) {
				result.setResultFlag(false);
				result.setFailureReason("发车计划不能为空");
				return JSONObject.fromObject(result).toString();
			}
			
			DepartVehiclePlanInfoQueryParmEntity queryParmEntity = new DepartVehiclePlanInfoQueryParmEntity();
			queryParmEntity.setVehicleNo(reqParEntity.getVehicleNo());
			queryParmEntity.setDepartDate(reqParEntity.getHandoverTime());
			queryParmEntity.setOrigOrgCode(reqParEntity.getOrigOrgCode());
			queryParmEntity.setDestOrgCode(reqParEntity.getArrivalDeptNo());
			queryParmEntity.setTransProperty(reqParEntity.getTransportType());
			queryParmEntity.setPlanType(reqParEntity.getPlanType());
			logger.info("查询开始");
			DepartPlanInfoDetailDto infoDto = departVehiclePlanInfoService.queryDepartVehiclePlanInfo(queryParmEntity);
			logger.info("查询结束");
			if (infoDto == null || "".equals(infoDto)) {
				result.setResultFlag(false);
				result.setFailureReason("没有发车计划信息");
				return JSONObject.fromObject(result).toString();
			}
					
			result.setResultFlag(true);
			result.setData(infoDto);
			return JSONObject.fromObject(result).toString();
		} catch (Exception e) {
			result.setResultFlag(false);
			result.setFailureReason("系统异常"+e.getStackTrace());
			logger.error("WK获取计划发车信息异常",e);
			return JSONObject.fromObject(result).toString();
		}
	}
}
