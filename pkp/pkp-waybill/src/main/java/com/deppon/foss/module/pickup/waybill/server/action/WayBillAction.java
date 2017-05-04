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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/action/WayBillAction.java
 * 
 * FILE NAME        	: WayBillAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillQueryService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.OrgAdministrativeInfoDto;
import com.deppon.foss.module.pickup.waybill.api.shared.exception.AdjustPlanException;
import com.deppon.foss.module.pickup.waybill.api.shared.vo.AdjustPlanVo;
import com.deppon.foss.module.pickup.waybill.server.utils.StringHandlerUtil;

/**
 * Action开发规范 1.必须继承com.deppon.foss.framework.server.web.action.AbstractAction
 * 2.必须生成serialVersionUID 3.类名必须以Action结尾
 * 4.前台传参必须封装到Vo中,不能直接使用Entity,Vo必须添加getter、setter方法
 * 5.方法上必须添加com.deppon.foss.framework.server.web.result.json.annotation.JSON @JSON
 * 注解 6.方法中必须try catch异常,成功调用returnSuccess系列重载函数,异常调用returnError系列重载函数
 * 7.禁止添加Service的getter方法 8.禁止注入Dao、只允许注入Service
 */
public class WayBillAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 用于运单和更改单查询
	 */
	private IWaybillQueryService waybillQueryService;

	/**
	 * 更改单服务接口
	 */
	private IWaybillRfcService waybillRfcService;

	/**
	 * 执行计划vo
	 */
	private AdjustPlanVo vo = new AdjustPlanVo();

	/**
	 * 设置 用于运单和更改单查询.
	 * 
	 * @param waybillQueryService the new 用于运单和更改单查询
	 */
	public void setWaybillQueryService(IWaybillQueryService waybillQueryService) {
		this.waybillQueryService = waybillQueryService;
	}

	/**
	 * 设置 更改单服务接口.
	 * 
	 * @param waybillRfcService the new 更改单服务接口
	 */
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	/**
	 * 查询执行计划列表.
	 * 
	 * @return the string
	 * @author 105089-foss-yangtong
	 * @date 2012-11-20 下午4:31:29
	 */
	@JSON
	public String queryAdjustPlanDtoList() {
		try {
			// 查询符合条件的记录数
			Long totalCount = this.waybillQueryService.queryAdjustPlanCount(vo.getAdjustPlanSearcherDto());
			if (totalCount != null && totalCount.intValue() > 0) {
				//根据查询条件返回待处理列表
				vo.setAdjustPlanResultDtoList(waybillQueryService.queryAdjustPlan(vo.getAdjustPlanSearcherDto(), this.getStart(), this.getLimit()));
			} else {
				vo.setAdjustPlanResultDtoList(null);
			}
			this.setTotalCount(totalCount);

		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 查询变更信息列表.
	 * 
	 * @return the string
	 * @author 105089-foss-yangtong
	 * @date 2012-11-20 下午4:31:29
	 */
	@JSON
	public String queryWaybillRfcList() {
		try {
			// 根据查询条件返回待处理列表
			String waybillRfcId = vo.getAdjustPlanResultDto().getWaybillRfcId();
			// 设置 更改单明细(变更信息明细).
			vo.setWaybillRfcChangeDetailList(waybillRfcService.queryRfcChangeDetail(waybillRfcId));
			// 设置 变更节点List.
			vo.setChangeNodeDtoList(waybillRfcService.queryTodoByWaybillRfcInfo(waybillRfcId, vo.getAdjustPlanResultDto()));
		} catch (BusinessException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 处理变更节点下拉框.
	 * 
	 * @return the string
	 * @author 105089-foss-yangtong
	 * @date 2012-11-20 下午4:31:29
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@JSON
	public String queryExeNode() {
		if (vo != null) {
			String temp = vo.getChangeNodeDto().getActuatingNode();
			List list = new ArrayList();
			if (temp != null && !temp.equals("")) {
				String actuatingNodes[] = temp.split(SymbolConstants.EN_COLON);
				StringHandlerUtil handler = new StringHandlerUtil();
				for (int i = 0; i < actuatingNodes.length; i++) {
					String actuatingNode[] = actuatingNodes[i].split(SymbolConstants.EN_COMMA);
					for (int j = 0; j < actuatingNode.length; j++) {
						handler.hashInsert(actuatingNode[j]);
					}
				}
				if (handler != null) {
					Map<String, Integer> map = handler.getHashMap();
					for (Entry<String, Integer> entry : map.entrySet()) {
						if (entry.getValue() == actuatingNodes.length) {
							list.add(entry.getKey());
						}
					}
				}
			}
			List<OrgAdministrativeInfoDto> orgList = waybillRfcService.getExeNodes(list);
			vo = new AdjustPlanVo();
			vo.setOrgList(orgList);
		}
		return returnSuccess();
	}

	/**
	 * 更新执行节点.
	 * 
	 * @return the string
	 * @author 105089-foss-yangtong
	 * @date 2012-11-20 下午4:31:29
	 */
	@JSON
	public String updateExeNode() {
		try {
			if (vo != null && CollectionUtils.isNotEmpty(vo.getChangeNodeDtoList())) {
				waybillRfcService.updateBatchNodes(vo.getChangeNodeDtoList());
			}
		} catch (AdjustPlanException e) {
			return returnError(e);
		}
		return returnSuccess();
	}

	/**
	 * 获取 执行计划vo.
	 * 
	 * @return the 执行计划vo
	 */
	public AdjustPlanVo getVo() {
		return vo;
	}

	/**
	 * 设置 执行计划vo.
	 * 
	 * @param vo the new 执行计划vo
	 */
	public void setVo(AdjustPlanVo vo) {
		this.vo = vo;
	}

}