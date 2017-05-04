package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;

/**
 * 
* @description 获取外请约车信息和更新外请约车信息状态DAO
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:55:16
 */
public interface IInviteVehicleInfoDao {

	/**
	 * 
	* @description 据车牌号和用车部门查询外请约车信息接口定义
	* @param inviteVehicleQueryParmEntity 获取外请约车信息查询实体
	* @return 获取外请约车信息Dto
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:54:42
	 */
	InviteVehicleInfoDto queryInviteVehicleInfo(InviteVehicleQueryParmEntity inviteVehicleQueryParmEntity);

	/**
	 * 
	* @description 根据车牌号和约车编号更新外请约车使用状态接口定义
	* @param entity 修改外请约车信息参数实体
	* @return 更新记录数
	* @version 1.0
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:56:08
	 */
	int updateInviteVehicleStatus(InviteVehicleEditParmEntity entity);
}