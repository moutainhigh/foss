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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonBigZoneAction.java
 * 
 * FILE NAME        	: CommonBigZoneAction.java
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
 * FILE    NAME: CommonBigZoneActino.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.BigZoneVo;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SmallZoneVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector.CommonBigZoneService;

/**
 * 公共组件--集中接送货大区Action.
 *
 * @author panGuangJun
 * @date 2012-12-3 上午9:28:00
 */
public class CommonBigZoneAction extends AbstractAction implements IQueryAction{
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 4716395916351397991L;
	//vo
	/** The big zone vo. */
	private BigZoneVo bigZoneVo = new BigZoneVo();
	/** The small zone vo. */
	private SmallZoneVo smallZoneVo = new SmallZoneVo();
	//sevice
	/** The common big zone service. */
	private CommonBigZoneService commonBigZoneService;
	
	/**
	 * 查询大区信息.
	 *
	 * @return the string
	 * @author panGuangJun
	 * @date 2012-12-3 上午9:30:36
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		bigZoneVo.setBigZoneEntities(commonBigZoneService.queryBigZones(bigZoneVo.getEntity(), limit, start));
		setTotalCount(commonBigZoneService.queryRecordCount(bigZoneVo.getEntity()));
		return returnSuccess();
	}
	
	/**
	 * Gets the big zone vo.
	 *
	 * @return the big zone vo
	 */
	public BigZoneVo getBigZoneVo() {
		return bigZoneVo;
	}
	
	/**
	 * Sets the big zone vo.
	 *
	 * @param bigZoneVo the new big zone vo
	 */
	public void setBigZoneVo(BigZoneVo bigZoneVo) {
		this.bigZoneVo = bigZoneVo;
	}
	
	/**
	 * Gets the small zone vo.
	 *
	 * @return the small zone vo
	 */
	public SmallZoneVo getSmallZoneVo() {
		return smallZoneVo;
	}
    
	/**
	 * Sets the small zone vo.
	 *
	 * @param smallZoneVo the new small zone vo
	 */
	public void setSmallZoneVo(SmallZoneVo smallZoneVo) {
		this.smallZoneVo = smallZoneVo;
	}

	/**
	 * Sets the common big zone service.
	 *
	 * @param commonBigZoneService the new common big zone service
	 */
	public void setCommonBigZoneService(CommonBigZoneService commonBigZoneService) {
		this.commonBigZoneService = commonBigZoneService;
	}

	/**
	 * 根据管理部门查询大区信息.
	 *
	 * @return the string
	 * @author zhuweixing
	 * @date 2015-01-06 上午9:30:36
	 * @see 
	 */
	@JSON
	public String search() {
		bigZoneVo.setBigZoneEntities(commonBigZoneService.searchBigZones(bigZoneVo.getEntity(), limit, start));
		setTotalCount(commonBigZoneService.searchRecordCount(bigZoneVo.getEntity()));
		return returnSuccess();
	}
	/**
	 * 根据管理部门查询送货大区信息.
	 *
	 * @return the string
	 * @author wusuhua
	 * @date 2015-06-11 上午9:30:36
	 * @see 
	 */
	@JSON
	public String searchDE() {
		bigZoneVo.setBigZoneEntities(commonBigZoneService.searchBigZonesDE(bigZoneVo.getEntity(), limit, start));
		setTotalCount(commonBigZoneService.searchDERecordCount(bigZoneVo.getEntity()));
		return returnSuccess();
	}
	
	
	/**
	 * 根据管理部门查询小区信息.
	 *
	 * @return the string
	 * @author zhuweixing
	 * @date 2015-01-06  上午9:30:36
	 * @see 
	 */
	@JSON
	public String find() {
		smallZoneVo.setSmallZoneEntities(commonBigZoneService.findSmallZones(smallZoneVo.getEntity(), limit, start));
		setTotalCount(commonBigZoneService.findRecordCount(smallZoneVo.getEntity()));
		return returnSuccess();
	}
}
