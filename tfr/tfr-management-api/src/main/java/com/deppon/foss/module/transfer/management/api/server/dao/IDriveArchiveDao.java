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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/dao/IDriveArchiveDao.java
 *  
 *  FILE NAME          :IDriveArchiveDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.DriveArchiveEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.DriveArchiveVehicleInfoDto;


public interface IDriveArchiveDao {
	
	/**
	 * 根据ID删除行驶档案信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-11-8 上午10:44:41
	 */
    int deleteByPrimaryKey(String id);

    /**
     * 根据ID查询详细信息
     * @author foss-liuxue(for IBM)
     * @date 2012-11-8 上午10:45:06
     */
    List<DriveArchiveEntity> displayDriveArchiveDetail(String id);

    /**
     * 查询所有行驶档案信息
     * @author foss-liuxue(for IBM)
     * @date 2012-11-8 上午10:45:19
     */
    List<DriveArchiveEntity> queryDriveArchive(DriveArchiveDto driveArchiveDto,int start,int limit);
    
    /**
     * 获取总记录条数
     * @author foss-liuxue(for IBM)
     * @date 2012-11-8 上午10:45:41
     */
    Long getCount(DriveArchiveDto driveArchiveDto);
    
    /**
     * 新增行驶档案
     * @author foss-liuxue(for IBM)
     * @date 2012-11-8 上午10:45:54
     */
    int addDriveArchive(DriveArchiveEntity driveArchiveEntity);
    
    /**
     * 修改行驶档案
     * @author foss-liuxue(for IBM)
     * @date 2012-11-8 上午10:47:08
     */
    int updateDriveArchive(DriveArchiveEntity driveArchiveEntity);
    
    /**
	 * 根据配载号查询车辆信息
	 * @author foss-liuxue(for IBM)
	 * @date 2012-12-29 下午4:19:49
	 */
	List<DriveArchiveVehicleInfoDto> queryVehicleNoByVehicleAssembleNo(DriveArchiveDto driveArchiveDto);
    
}