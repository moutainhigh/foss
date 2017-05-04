/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IGisQueryService.java
 * 
 * FILE NAME        	: IGisQueryService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.service
 * FILE    NAME: IGisService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionKeywordEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisDepartmentDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.GisPickupOrgDto;
import com.deppon.foss.module.pickup.waybill.shared.request.HisSegMatchRequest;
import com.deppon.foss.module.pickup.waybill.shared.response.HisSegMatchResponse;


/**
 * GIS查询服务接口
 * @author 026123-foss-lifengteng
 * @date 2012-12-27 上午10:33:37
 */
public interface IGisQueryService extends IService {

	/**
	 * 通过调用GIS的匹配到达网点接口实现收货人地址自动匹提货网点功能
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-27 上午8:35:53
	 */
	List<GisDepartmentDto> queryPickupOrgCodeByGis(GisPickupOrgDto dto);

	/**
	 * 通过调用GIS的特殊地址查询接口获取地址类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-1-4 下午4:51:33
	 */
	List<String> querySpecialAddressByGis(GisPickupOrgDto dto);
	
	/**
	 * 通过调用GIS的详细地址匹配接口查询是否展会货
	 * @param exhibitionKeyword
	 * @return
	 */
	String isExhibitCargo(ExhibitionKeywordEntity exhibitionKeyword);
	
	/**
	 * 通过调用GIS的历史经验分词匹配接口查询目的站信息
	 * @author 321993 zhangdianhao
	 * @date 2017-03-16 下午13:53:50
	 * @version 1.0
	 */
	HisSegMatchResponse queryStationInfo(HisSegMatchRequest request);

}