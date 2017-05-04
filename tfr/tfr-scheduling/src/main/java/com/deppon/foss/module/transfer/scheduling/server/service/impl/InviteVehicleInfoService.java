package com.deppon.foss.module.transfer.scheduling.server.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.transfer.scheduling.api.server.dao.IInviteVehicleInfoDao;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleInfoService;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.InviteVehicleInfoDto;

/**
 * 
* @description 获取外请约车信息和更新外请约车信息Service
* @version 1.0
* @author 332209-foss-ruilibao
* @update 2016-5-6 下午2:40:30
 */
public class InviteVehicleInfoService implements IInviteVehicleInfoService {
	
	// 日志
	private static final Logger LOGGER = LoggerFactory.getLogger(InviteVehicleInfoService.class);
	
	//外请约车信息查询和更新DAO
	private IInviteVehicleInfoDao inviteVehicleInfoDao;
	
	public void setInviteVehicleInfoDao(IInviteVehicleInfoDao inviteVehicleInfoDao) {
		this.inviteVehicleInfoDao = inviteVehicleInfoDao;
	}

	/**
	 * 
	* @description 获取外请约车信息
	* @see com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleInfoService#queryInviteVehicleInfo(com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleQueryParmEntity)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:40:55
	* @version V1.0
	 */
	@Override
	public InviteVehicleInfoDto queryInviteVehicleInfo(
			InviteVehicleQueryParmEntity entity) {
		LOGGER.debug("start service");
		if(entity != null && StringUtils.isNotBlank(entity.getVehicleNo())) {
			return inviteVehicleInfoDao.queryInviteVehicleInfo(entity);	
		} else {
			return null;
		}
	}

	/**
	 * 
	 */
	/**
	 * 
	* @description 更新外请约车状态
	* @see com.deppon.foss.module.transfer.scheduling.api.server.service.IInviteVehicleInfoService#updateInviteVehicleStatus(com.deppon.foss.module.transfer.scheduling.api.shared.domain.InviteVehicleEditParmEntity)
	* @author 332209-foss-ruilibao
	* @update 2016-5-6 下午2:41:27
	* @version V1.0
	 */
	@Override
	public int updateInviteVehicleStatus(InviteVehicleEditParmEntity entity) {
		LOGGER.debug("start service");
		if (entity != null && StringUtils.isNotBlank(entity.getVehicleNo())
				&& StringUtils.isNotBlank(entity.getInviteNo())
				&& StringUtils.isNotBlank(entity.getUseStatus())) {
			return inviteVehicleInfoDao.updateInviteVehicleStatus(entity);
		} else {
			return 0;
		}
	}
}