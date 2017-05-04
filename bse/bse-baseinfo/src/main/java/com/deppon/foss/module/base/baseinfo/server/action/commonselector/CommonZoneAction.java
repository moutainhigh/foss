/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonZoneAction.java
 * 
 * FILE NAME        	: CommonZoneAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
 /*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonAllZoneDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonZoneVo;

/**
 * 公共组件--查询集中接送货大小区.
 * @author 101911-foss-zhouChunlai
 * @date 2013-1-12 上午9:42:10
 */
public class CommonZoneAction extends AbstractAction implements IQueryAction {
 
	private static final long serialVersionUID = 1753080455518422617L;
	  
	/**
	 * 接送货大小区Vo
	 */
	private CommonZoneVo commonZoneVo=new CommonZoneVo();
	 
	/**
	 * 接送货大小区Service
	 */
	private ICommonZoneService commonZoneService;

 
	/**
	 * 查询接送货小区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午9:46:31
	 * @return 
	 */
	@JSON
	public String querySmallZone() {
		long totalCount=commonZoneService.countSmallZoneByCodition(commonZoneVo.getCommonSmallZoneDto());
		if(totalCount > 0){
			List<SmallZoneEntity> smallZoneList=commonZoneService.querySmallZoneByCondition(commonZoneVo.getCommonSmallZoneDto(), limit, start);
			commonZoneVo.setCommonSmallZoneList(smallZoneList);
		} 
		setTotalCount(totalCount);
		return returnSuccess();
	}
 
	/**
	 * 查询接送货大区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午9:46:40
	 * @return 
	 */
	@JSON
	public String queryBigZone() {
		long totalCount=commonZoneService.countBigZoneByCodition(commonZoneVo.getCommonBigZoneDto());
		if(totalCount > 0){
			List<BigZoneEntity> bigZoneList=commonZoneService.queryBigZoneByCondition(commonZoneVo.getCommonBigZoneDto(), limit, start);
			commonZoneVo.setCommonBigZoneList(bigZoneList);
		} 
		setTotalCount(totalCount);
		return returnSuccess();
	}

	/**
	 * 查询接送货大小区.
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-12 上午9:46:40
	 * @return 
	 */
	@JSON
	public String queryAllZone() {
		long totalCount=commonZoneService.countAllZoneByCodition(commonZoneVo.getCommonAllZoneDto());
		if(totalCount > 0){
			List<CommonAllZoneDto> allZoneList=commonZoneService.queryAllZoneByCondition(commonZoneVo.getCommonAllZoneDto(), limit, start);
			commonZoneVo.setCommonAllZoneList(allZoneList);
		} 
		setTotalCount(totalCount);
		return returnSuccess();
	}

	
	public CommonZoneVo getCommonZoneVo() {
		return commonZoneVo;
	}

	
	public void setCommonZoneVo(CommonZoneVo commonZoneVo) {
		this.commonZoneVo = commonZoneVo;
	}

	
	public void setCommonZoneService(ICommonZoneService commonZoneService) {
		this.commonZoneService = commonZoneService;
	}

	@Override
	public String query() { 
		return null;
	}
	 

}
