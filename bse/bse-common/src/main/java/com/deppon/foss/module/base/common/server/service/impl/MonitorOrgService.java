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
 * PROJECT NAME	: bse-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/common/server/service/impl/MonitorOrgService.java
 * 
 * FILE NAME        	: MonitorOrgService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-common
 * PACKAGE NAME: com.deppon.foss.module.base.common.server.service.impl
 * FILE    NAME: MonitorOrgService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.common.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.common.api.server.dao.IMonitorOrgDao;
import com.deppon.foss.module.base.common.api.server.service.IMonitorOrgService;
import com.deppon.foss.module.base.common.api.shared.vo.MonitorVo;

/**
 * 监控树Service
 * 
 * @author 096598-foss-zhongyubing
 * @date 2013-2-27 上午11:42:18
 */
public class MonitorOrgService implements IMonitorOrgService {

	@Autowired
	private IMonitorOrgDao monitorOrgDao;

	/**
	 * 查询监控结构树
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2013-2-27 上午11:42:55
	 * @see com.deppon.foss.module.base.common.api.server.service.IMonitorOrgService#queryOrgList(com.deppon.foss.module.base.common.api.shared.vo.MonitorVo)
	 */
	@Override
	public List<OrgAdministrativeInfoEntity> queryOrgList(MonitorVo vo) {
		return monitorOrgDao.queryOrgList(vo);
	}

	public void setMonitorOrgDao(IMonitorOrgDao monitorOrgDao) {
		this.monitorOrgDao = monitorOrgDao;
	}

}
