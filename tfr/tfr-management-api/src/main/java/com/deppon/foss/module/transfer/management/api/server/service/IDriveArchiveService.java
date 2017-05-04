/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/IDriveArchiveService.java
 *  
 *  FILE NAME          :IDriveArchiveService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.management.api.shared.domain.DriveArchiveEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveLineInfoDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveVehicleInfoDto;

public interface IDriveArchiveService extends IService {
	/**
	 * 根据查询条件查询行驶档案
	 * @author LiuXue
	 * @date 2012-10-29 15:55:17
	 */
	List<DriveArchiveEntity> queryDriveArchive(DriveArchiveDto driveArchiveDto,int start,int limit);
	
	/**
	 * 获取当前数据库中记录的总条数
	 * @author LiuXue
	 * @date 2012-10-29 15:56:08
	 */
	Long getCount(DriveArchiveDto driveArchiveDto);
	
	/**
	 * 执行sava/update操作
	 * @author LiuXue
	 * @date 2012-10-29 15:56:12
	 */
	int saveOrUpdate(DriveArchiveDto driveArchiveDto);
	
	/**
	 * 删除选中的记录
	 * @author LiuXue
	 * @date 2012-10-29 15:56:14
	 */
	int deleteByPrimaryKey(String driveArchiveId);
	
	/**
	 * 根据ID查询行驶档案
	 * @author LiuXue
	 * @date 2012-10-29 15:56:17
	 */
	DriveArchiveEntity displayDriveArchiveDetail(String id);
	
	/**
	 * 根据车牌号获取车辆信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-21 下午4:12:26
	 */
	DriveArchiveEntity queryVehicleInfo(DriveArchiveDto driveArchiveDto);
	
	/**
	 * 根据配载号查询车辆信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-29 下午4:19:49
	 */
	DriveArchiveVehicleInfoDto queryVehicleNoByVehicleAssembleNo(DriveArchiveDto driveArchiveDto);

	/**
	 * 根据班次号和线路带出预计发车日期，预计到达日期，标准时效
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-4 下午3:59:44
	 */
	DriveArchiveLineInfoDto queryLineInfoByLineSequence(DriveArchiveDto driveArchiveDto);
	
}