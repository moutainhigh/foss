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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/commonselector/CommonPositionAction.java
 * 
 * FILE NAME        	: CommonPositionAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.action.commonselector
 * FILE    NAME: CommonPositionAction.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.action.commonselector;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPositionService;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PositionVo;

/**
 * 职位查询ACTION.
 *
 * @author 078823-foss-panGuangJun
 * @date 2012-12-12 下午5:41:25
 */
public class CommonPositionAction extends AbstractAction implements
		IQueryAction {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1364059337185677858L;
	// 用于注入人员业务服务实现类
		private ICommonPositionService commonPositionService;

		private PositionVo positionVo;




	public void setCommonPositionService(
				ICommonPositionService commonPositionService) {
			this.commonPositionService = commonPositionService;
		}

		public PositionVo getPositionVo() {
		return positionVo;
	}
		public void setPositionVo(PositionVo positionVo) {
			this.positionVo = positionVo;
		}




	/**
	 * 查询职位
	 *
	 * @return the string
	 * @author 130346-foss-lifanghong
	 * @date 2014-4-29 上午11:53:25
	 * @see com.deppon.foss.module.base.baseinfo.api.server.action.IQueryAction#query()
	 */
	@JSON
	@Override
	public String query() {
		// 返回的结果显示在表格中：
		positionVo.setPositionList(commonPositionService.queryPositionByEntitySelector(
				positionVo.getPositionEntity(), start, limit));
		totalCount = commonPositionService.countPositionByEntitySelector(positionVo.getPositionEntity());
		return returnSuccess();
	}

}
