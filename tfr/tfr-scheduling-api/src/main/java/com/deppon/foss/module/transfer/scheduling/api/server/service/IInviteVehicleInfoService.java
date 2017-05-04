package com.deppon.foss.module.transfer.scheduling.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;

/**
 * 
* @description 获取外请约车信息和修改外请约车信息Service
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:49:37
 */
public interface IInviteVehicleInfoService extends IService {
	
	/**
	 * 
	* @description 根据车牌号和用车部门编码获取外请约车信息接口定义
	* @param inviteVehicleQueryParmEntity 获取外请约车信息查询实体
	* @return 获取外请约车信息Dto
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:50:00
	 */
	InviteVehicleInfoDto queryInviteVehicleInfo(InviteVehicleQueryParmEntity inviteVehicleQueryParmEntity);
	
	/**
	 * 
	* @description 根据车牌号和约车编号更新外请约车使用状态
	* @param entity 修改外请约车信息参数实体
	* @return 执行记录
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:50:50
	 */
	int updateInviteVehicleStatus(InviteVehicleEditParmEntity entity);
}