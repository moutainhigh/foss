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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/IAirSpaceService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-airfreight-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.airfreight.api.server.service
 * FILE    NAME: IAirSpaceService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto;

/**
 * 舱位管理Service接口
 * @author dp-pengzhen
 * @date 2012-10-13 上午11:37:22
 */
public interface IAirSpaceService extends IService{
	
	/**
	 * 查询航空舱位信息列表Service接口
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-16 下午2:04:26
	 */
	List<AirSpaceDto> queryAirSpace(AirSpaceDto dto, int limit, int start);
	
	/**
	 * 统计根据条件查询出的结果数目
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-14 上午9:32:18
	 */
	Long queryAirSpaceCount(AirSpaceDto dto);
	
	/**
	 * 根据id查询航空公司舱位及明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-6 下午7:39:06
	 */
	AirSpaceDto queryAirSpaceById(String airSpaceId);
	
	/**
	 * 录入航空舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	void addAirSpace(AirSpaceDto dto);
	
	/**
	 * 删除某条舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-1 下午8:15:54
	 */
	void deleteAirSpace(AirSpaceDto dto);
	
	/**
	 * 修改航空舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	void updateAirSpace(AirSpaceDto dto);

	/**
	 * 获取上级空运总调组织
	 */
	OrgAdministrativeInfoEntity getOrgAdministrative();
}