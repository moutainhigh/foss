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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/AbandonGoodsApplicationAction.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IArriveDeliverManagerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveDeliverQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.ArriveDeliverManagerException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.ArriveDeliverVo;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
/**
 * 到达派送管理查询系统 Action
 * 
 * @author foss-meiying
 * @date 2013-06-24 下午5:17:11
 */
public class ArriveDeliverManagerAction extends AbstractAction {

	/**
	 * 序列化版本号
	 */
	private static final long serialVersionUID = 1L;
	private ArriveDeliverVo arriveDeliverVo;
	private IArriveDeliverManagerService arriveDeliverManagerService;

	/**
	 * 
	 * 根据条件查询到达派送信息
	 * 
	 *@author foss-meiying
	 * @date 2013-06-24 下午5:18:11
	 */
	@JSON
	public String queryArriveDeliverByParams() {
		try {
			ArriveDeliverQueryDto arriveDeliverQueryDto = arriveDeliverVo.getArriveDeliverQueryDto();
			
			arriveDeliverQueryDto = this.arriveDeliverManagerService
					.refreshArriveDeliverQueryDto(arriveDeliverQueryDto);
			if(arriveDeliverQueryDto == null){
				throw new  ArriveDeliverManagerException(ArriveDeliverManagerException.LOGER_ORG_CODE_NULL);
			}
			this.totalCount = arriveDeliverManagerService.queryArriveDeliverCountByParams(arriveDeliverQueryDto);
			// 如果存在信息
			if (this.totalCount != null && this.totalCount >SignConstants.ZERO ) {
				arriveDeliverVo=arriveDeliverManagerService.queryArriveDeliverByParams(arriveDeliverQueryDto,this.getStart(), this.getLimit());
			}else {
				arriveDeliverVo.setArriveDeliverDtoList(null);
			}
		} catch (BusinessException e) {
			// 返回error
			return returnError(e);
		}
		// 返回成功
		return returnSuccess();
	}

	public ArriveDeliverVo getArriveDeliverVo() {
		return arriveDeliverVo;
	}

	public void setArriveDeliverVo(ArriveDeliverVo arriveDeliverVo) {
		this.arriveDeliverVo = arriveDeliverVo;
	}

	public void setArriveDeliverManagerService(
			IArriveDeliverManagerService arriveDeliverManagerService) {
		this.arriveDeliverManagerService = arriveDeliverManagerService;
	}




	
}