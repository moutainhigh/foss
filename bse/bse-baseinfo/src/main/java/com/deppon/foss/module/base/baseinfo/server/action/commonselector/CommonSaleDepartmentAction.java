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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonSaleDepartmentAction.java
 * 
 * FILE NAME        	: CommonSaleDepartmentAction.java
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
 * FILE    NAME: CommonSaleDepartmentAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonSaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.SaleDepartmentVo;

/**
 * 公共选择器--营业部查询Action实现.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-5 下午3:22:01
 */
public class CommonSaleDepartmentAction extends AbstractAction implements
		IQueryAction {
	// vo
	/** The sale department vo. */
	private SaleDepartmentVo saleDepartmentVo = new SaleDepartmentVo();
	// service
	/** The common sale department service. */
	private ICommonSaleDepartmentService commonSaleDepartmentService;

	/** serialVersionUID. */
	private static final long serialVersionUID = 7487990534380046342L;

	/**
	 * 查询营业部信息.
	 *
	 * @return the string
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-5 下午3:22:20
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@Override
	public String query() {
		saleDepartmentVo.setDepartmentEntities(commonSaleDepartmentService
				.querySaleDepartmentExactByEntity(
						saleDepartmentVo.getDepartmentEntity(), start, limit));
		this.setTotalCount(commonSaleDepartmentService
				.querySaleDepartmentExactByEntityCount(saleDepartmentVo
						.getDepartmentEntity()));
		return returnSuccess();
	}

	/**
	 * setter.
	 *
	 * @param commonSaleDepartmentService the new common sale department service
	 */
	public void setCommonSaleDepartmentService(
			ICommonSaleDepartmentService commonSaleDepartmentService) {
		this.commonSaleDepartmentService = commonSaleDepartmentService;
	}

	/**
	 * getter.
	 *
	 * @return the sale department vo
	 */
	public SaleDepartmentVo getSaleDepartmentVo() {
		return saleDepartmentVo;
	}

	/**
	 * setter.
	 *
	 * @param saleDepartmentVo the new sale department vo
	 */
	public void setSaleDepartmentVo(SaleDepartmentVo saleDepartmentVo) {
		this.saleDepartmentVo = saleDepartmentVo;
	}

}
