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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonMotorcadeDistrictAction.java
 * 
 * FILE NAME        	: CommonMotorcadeDistrictAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action
 * FILE    NAME: CommonMotorcadeServeDistrictAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonMotorcadeDistrictService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.MotorcadeDistrictVo;

/**
 * 公共查询组件--车队对应行政区域查询ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:28:16
 */
public class CommonMotorcadeDistrictAction extends AbstractAction
		implements IQueryAction {
	// vo
	/** The motorcade district vo. */
	private MotorcadeDistrictVo motorcadeDistrictVo = new MotorcadeDistrictVo();
	// service
	/** The common motorcade district service. */
	private ICommonMotorcadeDistrictService commonMotorcadeDistrictService;
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 3537348651250439484L;

	/**
	 * 车队对应的行政区域查询方法.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:28:16
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		motorcadeDistrictVo
				.setMotorcadeServeDistrictEntities(commonMotorcadeDistrictService
						.queryMotorcadeServeDistrictByCondition(
								motorcadeDistrictVo
										.getMotorcadeServeDistrictEntity(),
								start, limit));
		setTotalCount(commonMotorcadeDistrictService
				.queryMotorcadeServeDistricByCondition(motorcadeDistrictVo
						.getMotorcadeServeDistrictEntity()));
		return returnSuccess();
	}


	/**
	 * setter.
	 *
	 * @param commonMotorcadeDistrictService the new common motorcade district service
	 */
	public void setCommonMotorcadeDistrictService(
			ICommonMotorcadeDistrictService commonMotorcadeDistrictService) {
		this.commonMotorcadeDistrictService = commonMotorcadeDistrictService;
	}


	/**
	 * getter.
	 *
	 * @return the motorcade district vo
	 */
	public MotorcadeDistrictVo getMotorcadeDistrictVo() {
		return motorcadeDistrictVo;
	}


	/**
	 * setter.
	 *
	 * @param motorcadeDistrictVo the new motorcade district vo
	 */
	public void setMotorcadeDistrictVo(MotorcadeDistrictVo motorcadeDistrictVo) {
		this.motorcadeDistrictVo = motorcadeDistrictVo;
	}

}
