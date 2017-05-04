package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.deppon.foss.framework.service.IService;

/**
 * 
* @description 获取发车计划信息Service
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:17:51
 */
@Produces(MediaType.APPLICATION_JSON)
public interface IDepartVehiclePlanService extends IService {

	/**
	 * 
	* @description 获取发车计划信息接口定义
	* @param reqMsg 请求参数
	* @return 获取计划发车信息实体
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:34:08
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/getDepartVehiclePlanInfo")
	// http://localhost:8080/foss/services/departVehicleplan/getDepartVehiclePlanInfo
	public Object getDepartVehiclePlanInfo(String reqMsg);
	
}
