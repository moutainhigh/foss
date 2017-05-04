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
 *  FILE PATH          :/IAirSpaceDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.dao;

import java.util.List;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.AirSpaceDto;

/**
 * 航空舱位信息管理DAO接口
 * @author dp-pengzhen
 * @date 2012-10-13 上午11:36:40
 */
public interface IAirSpaceDao {
	/**
	 * 查询航空舱位信息列表DAO接口
	 * @author dp-pengzhen
	 * @date 2012-10-16 上午9:15:33
	 */
	List<AirSpaceDto> queryAirSpace(AirSpaceDto dto,int limit,int start);
	
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
	 * 录入航空舱位明细
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	void addAirSpaceDetail(AirSpaceDetailEntity entity);
	
	/**
	 * 判断是否存在同一日期到同一目的地的舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-1 下午8:12:28
	 */
	boolean isExistSpace(AirSpaceDto dto);
	
	/**
	 *  删除某条舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-1 下午8:15:54
	 */
	void deleteAirSpace(AirSpaceDto dto);
	
	/**
	 * 根据舱位id删除某条舱位明细信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-6 下午1:50:32
	 */
	void deleteAirSpaceDetail(String airSpaceId);
	
	/**
	 * 修改航空舱位信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	void updateAirSpace(AirSpaceDto dto);
	
	/**
	 * 修改航空舱位明细
	 * @author 038300-foss-pengzhen
	 * @date 2012-10-31 上午10:24:58
	 */
	void updateAirSpaceDetail(AirSpaceDetailEntity entity);
	
	/**
	 * 获取舱位ID
	 * @author 038300-foss-pengzhen
	 * @date 2012-12-6 下午7:44:25
	 */
	String getAriSpaceId(AirSpaceDto dto);

	/**
	 * 根据ID查询舱位类型总数
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-27 下午2:25:34
	 */
	Long queryFlightCountById(AirSpaceDto dto);

	/**
	 * "删除"单条舱位信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-4-27 下午2:29:00
	 */
	void deleteSingleSpace(AirSpaceDto dto);
}