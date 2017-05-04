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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/QueryGoodsAction.java
 * 
 * FILE NAME        	: QueryGoodsAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import java.util.List;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IQueryGoodsService;
import com.deppon.foss.module.pickup.predeliver.api.shared.context.FossUserContextHelper;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.GoodsInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.exception.QueryGoodsException;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.GoodsInfoConditionVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 查询货量Action.
 *
 * @author 043258-foss-zhaobin
 * @date 2012-10-16 下午2:59:16
 * @since
 * @version
 */
public class QueryGoodsAction extends AbstractAction 
{
	
	/** 序列化. */
	private static final long serialVersionUID = 1118011519921068369L;
	
	/** 查询货量VO. */
	private GoodsInfoConditionVo vo;
	
	/** 查询货量Service. */
	private IQueryGoodsService queryGoodsService;
	
	/** 获取部门. */
	private ISaleDepartmentService saleDepartmentService;
	
	/** 是否派送部. */
	private String isDeliverDepartment;
	
	
	/**
	 * Gets the vo.
	 *
	 * @return the vo
	 */
	public GoodsInfoConditionVo getVo() {
		return vo;
	}
	
	/**
	 * Sets the vo.
	 *
	 * @param vo the new vo
	 */
	public void setVo(GoodsInfoConditionVo vo) {
		this.vo = vo;
	}
	
	/**
	 * Sets the query goods service.
	 *
	 * @param queryGoodsService the new query goods service
	 */
	public void setQueryGoodsService(IQueryGoodsService queryGoodsService) {
		this.queryGoodsService = queryGoodsService;
	}
	
	
	
	/**
	 * Sets the sale department service.
	 *
	 * @param saleDepartmentService the new sale department service
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	
	/**
	 * Gets the is deliver department.
	 *
	 * @return the is deliver department
	 */
	public String getIsDeliverDepartment() {
		return isDeliverDepartment;
	}

	/**
	 * Sets the is deliver department.
	 *
	 * @param isDeliverDepartment the new is deliver department
	 */
	public void setIsDeliverDepartment(String isDeliverDepartment) {
		this.isDeliverDepartment = isDeliverDepartment;
	}

	/**
	 * 查询货量.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2012-10-16 下午2:59:16
	 * @since
	 * @version
	 */
	@JSON
	public String queryGoods(){
		try {
			//查询出总条数，用于分页
			Long totalCount = queryGoodsService.getGoodsInfoCount(vo.getGoodsInfoConditionDto());
			
			if(totalCount != null && totalCount.intValue() > 0)
			{
				//根据getGoodsInfoConditionDto获取货量信息Dto，包含分页信息
	 			List<GoodsInfoDto> list = queryGoodsService.queryGoods(vo.getGoodsInfoConditionDto(),this.getStart(),this.getLimit());
				vo.setGoodsInfoDtoList(list);
				if(this.getStart()== 0)
				{
					//查询总计
					GoodsInfoConditionDto goodsInfoConditionDto = queryGoodsService.queryGoodsTotal(vo.getGoodsInfoConditionDto());
					goodsInfoConditionDto.setGoodsWaybillTotal(totalCount.toString());
					vo.setGoodsInfoConditionDto(goodsInfoConditionDto);
				}
			}else
			{
				vo.setGoodsInfoDtoList(null);
			}
			//设置总条数
			this.setTotalCount(totalCount);
		}
		//捕获异常
		catch (QueryGoodsException e) {
			//返回异常信息
			returnError(e);
		}
		//成功
		return returnSuccess();
	}
	
	/**
	 * 根据当前部门编码查询出营业部实体.
	 *
	 * @return the string
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-6 上午11:47:26
	 * @since
	 * @version
	 */
	@JSON
	public String queryDepartment(){
		//查询营业部
		SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySimpleSaleDepartmentByCode(FossUserContextHelper.getOrgCode());
		//如果不为空
		if(saleDepartmentEntity != null)
		{
			//是否到达
			String isArrive = saleDepartmentEntity.getArrive();
			//是否驻地部门
			String station = saleDepartmentEntity.getStation();
			//可做到达并且为驻地部门
			if(FossConstants.YES.equals(isArrive) && FossConstants.YES.equals(station))
			{
				//派送部属性置为Y 
				isDeliverDepartment = "Y";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			}
			//成功
			return returnSuccess();
		}
		//成功
		return returnSuccess();
	}
}