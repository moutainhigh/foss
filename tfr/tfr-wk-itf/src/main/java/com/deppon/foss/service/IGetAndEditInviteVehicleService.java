package com.deppon.foss.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.deppon.foss.framework.service.IService;

/**
* @description 获取约车信息和修改外请约车信息接口定义
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016年4月29日 上午7:07:21
*/

@Produces(MediaType.APPLICATION_JSON)
public interface IGetAndEditInviteVehicleService extends IService {

	/**
	 * 
	* @description 获取约车信息接口定义
	* @param reqMsg 请求参数
	* @return 获取约车信息实体
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:31:37
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/getInviteVehicleInfo")
	// http://localhost:8080/foss/services/synchronizedinvitevehicle/getInviteVehicleInfo
	public Object getInviteVehicleInfo(String reqMsg);
	
	/**
	 * 
	* @description 修改约车信息接口定义
	* @param reqMsg 请求参数
	* @return 修改约车信息实体
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:33:01
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({MediaType.APPLICATION_JSON})
	@Path(value = "/editInviteVehicleInfo")
	// http://localhost:8080/foss/services/synchronizedinvitevehicle/editInviteVehicleInfo
	public Object editInviteVehicleInfo(String reqMsg);
	
}
