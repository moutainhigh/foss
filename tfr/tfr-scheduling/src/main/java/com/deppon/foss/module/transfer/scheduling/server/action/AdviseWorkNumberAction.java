/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  
 *  PROJECT NAME  : tfr-scheduling
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/server/action/AdviseWorkNumberAction.java
 * 
 *  FILE NAME     :AdviseWorkNumberAction.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.scheduling.server.action;

import java.math.BigDecimal;
import java.math.RoundingMode;



import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILoadAndUnloadEfficiencyManService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LoadAndUnloadEfficiencyManEntity;



import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.scheduling.api.define.ForecastConstants;
import com.deppon.foss.module.transfer.scheduling.api.server.service.IAdviseWorkNumberService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.AdviseWorkNumberDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.vo.AdviseWorkNumberVO;

/**
 * 外场上班人员action类
 * 
 * @author huyue
 * @date 2012-10-31 下午5:23:12
 */
public class AdviseWorkNumberAction extends AbstractAction {

	private static final long serialVersionUID = -5182857592065055743L;
	/**
	 * 外场上班人员VO
	 */
	private AdviseWorkNumberVO adviseWorkNumberVO = new AdviseWorkNumberVO();

	/**
	 * 外场上班人员Service
	 */
	private transient IAdviseWorkNumberService adviseWorkNumberService;

	/**
	 * 综合配置参数相关Service
	 */
	//没有使用-352203
//	private transient IConfigurationParamsService configurationParamsService;
	
	private transient ILoadAndUnloadEfficiencyManService loadAndUnloadEfficiencyManService;

	private static final int three = 3;

	/**
	 * 获取 外场上班人员VO.
	 *
	 * @return the 外场上班人员VO
	 */
	public AdviseWorkNumberVO getAdviseWorkNumberVO() {
		return adviseWorkNumberVO;
	}

	/**
	 * 设置 外场上班人员VO.
	 *
	 * @param adviseWorkNumberVO the new 外场上班人员VO
	 */
	public void setAdviseWorkNumberVO(AdviseWorkNumberVO adviseWorkNumberVO) {
		this.adviseWorkNumberVO = adviseWorkNumberVO;
	}

	/**
	 * 设置 综合配置参数相关Service.
	 *
	 * @param configurationParamsService the new 综合配置参数相关Service
	 */
/*	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}*/

	/**
	 * 设置 外场上班人员Service.
	 *
	 * @param adviseWorkNumberService the new 外场上班人员Service
	 */
	public void setAdviseWorkNumberService(IAdviseWorkNumberService adviseWorkNumberService) {
		this.adviseWorkNumberService = adviseWorkNumberService;
	}

	public void setLoadAndUnloadEfficiencyManService(ILoadAndUnloadEfficiencyManService loadAndUnloadEfficiencyManService) {
		this.loadAndUnloadEfficiencyManService = loadAndUnloadEfficiencyManService;
	}

	/**
	 * 卸车人数
	 * 
	 * @author huyue
	 * @date 2012-12-11 下午5:26:23
	 */
	@JSON
	public String queryArrive() {
		try {
			/**
			 * 获取卸车相关信息, 到达货量预测,车辆统计等
			 */
			AdviseWorkNumberDto adviseWorkNumberDto = adviseWorkNumberService.getForecastData(adviseWorkNumberVO.getOrgCode(), ForecastConstants.FORECAST_ARRIVE);
			adviseWorkNumberVO.setAdviseWorkNumberDto(adviseWorkNumberDto);
			/**
			 * 获取本外场卸车效率
			 */
			/**
			 * 先设置为0
			 */
			BigDecimal efficiencyPerPeople = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			/**
			 * 再查询综合人天装卸效率是否有值
			 */
			LoadAndUnloadEfficiencyManEntity entity = loadAndUnloadEfficiencyManService.queryLoadAndUnloadEfficiencyManUpByOrgCode(adviseWorkNumberVO.getOrgCode());
			/**
			 * 如果有值则赋值
			 */
			if (null != entity && null != entity.getLoadVolumeStd()) {
				efficiencyPerPeople = entity.getLoadVolumeStd();
			}
			adviseWorkNumberVO.setEfficiencyPerPeople(efficiencyPerPeople);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 装车人数
	 * 
	 * @author huyue
	 * @date 2012-12-11 下午5:27:43
	 */
	@JSON
	public String queryDepart() {
		try {
			/**
			 * 获取装车相关信息, 出发货量预测,车辆统计等
			 */
			AdviseWorkNumberDto adviseWorkNumberDto = adviseWorkNumberService.getForecastData(adviseWorkNumberVO.getOrgCode(), ForecastConstants.FORECAST_DEPART);
			adviseWorkNumberVO.setAdviseWorkNumberDto(adviseWorkNumberDto);
			/**
			 * 获取本外场装车效率
			 */
			/**
			 * 先设置为0
			 */
			BigDecimal efficiencyPerPeople = BigDecimal.ZERO.setScale(three, RoundingMode.HALF_DOWN);
			/**
			 * 再查询综合人天装卸效率是否有值
			 */
			LoadAndUnloadEfficiencyManEntity entity = loadAndUnloadEfficiencyManService.queryLoadAndUnloadEfficiencyManUpByOrgCode(adviseWorkNumberVO.getOrgCode());
			/**
			 * 如果有值则赋值
			 */
			if (null != entity && null != entity.getLoadWeightStd()) {
				efficiencyPerPeople = entity.getLoadWeightStd();
			}
			adviseWorkNumberVO.setEfficiencyPerPeople(efficiencyPerPeople);
			return returnSuccess();
			/**
			 * 捕获中转异常
			 */
		} catch (TfrBusinessException e) {
			return returnError(e);
		}
	}
}
