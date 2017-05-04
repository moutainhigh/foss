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
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/shared/vo/AdjustOutVehicleFeeVo.java
 *  
 *  FILE NAME          :AdjustOutVehicleFeeVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.RewardOrPunishAgreementDto;

/**
 * 外请车费用调整 Vo
 * 
 * @author dp-liming
 * @date 2012-11-19 下午 16:30:52
 */
public class AdjustOutVehicleFeeVo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8346585426447133456L;

	/**
	 * 外请车费用调整entity
	 */
	private AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity;

	/**
	 * 外请车费用调整Dto
	 */
	private AdjustOutVehicleFeeDto adjustOutVehicleFeeDto;

	/**
	 * 外请车费用调整Dto List
	 */
	List<AdjustOutVehicleFeeDto> adjustOutVehicleFeeDtoList;

	/**
	 * 外请车费用调整List
	 */
	List<AdjustOutVehicleFeeEntity> adjustOutVehicleFeeList;

	/**
	 * 外请车费用调整 费用范围
	 */
	List<ConfigurationParamsEntity> ConfigurationParamsList;

	/**
	 * 外请车时效条款信息
	 */
	private RewardOrPunishAgreementDto rewardOrPunishAgreementDto;

	/**
	 * 外请车费用修改日志Dto
	 * @author 269701--lln--2015-07-15
	 */
	private AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto;

	/**
	 * 外请车费用修改日志Entity
	 * @author 269701--lln--2015-07-15
	 */
	private AdjustOutVehicleFeeLogEntity adjustOutVehicleFeeLogEntity;

	/**
	 * 外请车费用修改日志List
	 * 
	 * @author 269701--foss--lln
	 * @date 2015--07--12
	 */
	List<AdjustOutVehicleFeeLogEntity> logList;

	/**
	 * 未审核记录条数
	 * 
	 * @author 269701--foss--lln
	 * @date 2015--07--16
	 */
	private Integer noAuditCount;
	/**
	 * 审批中记录条数
	 * 
	 * @author 269701--foss--lln
	 * @date 2015--07--16
	 */
	private Integer auditInCount;

	/**
	 * 审核效验
	 */
	private Integer isNum;

	/**
	 * 是否调整
	 */
	private Boolean isAdjust;

	/**
	 * 获取 外请车费用调整entity.
	 * 
	 * @return the 外请车费用调整entity
	 */
	public AdjustOutVehicleFeeEntity getAdjustOutVehicleFeeEntity() {
		return adjustOutVehicleFeeEntity;
	}

	/**
	 * 设置 外请车费用调整entity.
	 * 
	 * @param adjustOutVehicleFeeEntity
	 *            the new 外请车费用调整entity
	 */
	public void setAdjustOutVehicleFeeEntity(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity) {
		this.adjustOutVehicleFeeEntity = adjustOutVehicleFeeEntity;
	}

	/**
	 * 获取 外请车费用调整Dto.
	 * 
	 * @return the 外请车费用调整Dto
	 */
	public AdjustOutVehicleFeeDto getAdjustOutVehicleFeeDto() {
		return adjustOutVehicleFeeDto;
	}

	/**
	 * 设置 外请车费用调整Dto.
	 * 
	 * @param adjustOutVehicleFeeDto
	 *            the new 外请车费用调整Dto
	 */
	public void setAdjustOutVehicleFeeDto(
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto) {
		this.adjustOutVehicleFeeDto = adjustOutVehicleFeeDto;
	}

	/**
	 * 获取 外请车费用调整List.
	 * 
	 * @return the 外请车费用调整List
	 */
	public List<AdjustOutVehicleFeeEntity> getAdjustOutVehicleFeeList() {
		return adjustOutVehicleFeeList;
	}

	/**
	 * 设置 外请车费用调整List.
	 * 
	 * @param adjustOutVehicleFeeList
	 *            the new 外请车费用调整List
	 */
	public void setAdjustOutVehicleFeeList(
			List<AdjustOutVehicleFeeEntity> adjustOutVehicleFeeList) {
		this.adjustOutVehicleFeeList = adjustOutVehicleFeeList;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public List<ConfigurationParamsEntity> getConfigurationParamsList() {
		return ConfigurationParamsList;
	}

	/**
	 * 
	 * 
	 * @param configurationParamsList
	 */
	public void setConfigurationParamsList(
			List<ConfigurationParamsEntity> configurationParamsList) {
		ConfigurationParamsList = configurationParamsList;
	}

	/**
	 * 获取 审核效验.
	 * 
	 * @return the 审核效验
	 */
	public Integer getIsNum() {
		return isNum;
	}

	/**
	 * 设置 审核效验.
	 * 
	 * @param isNum
	 *            the new 审核效验
	 */
	public void setIsNum(Integer isNum) {
		this.isNum = isNum;
	}

	/**
	 * 获取 是否调整.
	 * 
	 * @return the 是否调整
	 */
	public Boolean getIsAdjust() {
		return isAdjust;
	}

	/**
	 * 设置 是否调整.
	 * 
	 * @param isAdjust the new 是否调整
	 */
	public void setIsAdjust(Boolean isAdjust) {
		this.isAdjust = isAdjust;
	}

	/**
	 * 获取 外请车费用调整Dto List.
	 * 
	 * @return the 外请车费用调整Dto List
	 */
	public List<AdjustOutVehicleFeeDto> getAdjustOutVehicleFeeDtoList() {
		return adjustOutVehicleFeeDtoList;
	}

	/**
	 * 设置 外请车费用调整Dto List.
	 * 
	 * @param adjustOutVehicleFeeDtoList
	 *            the new 外请车费用调整Dto List
	 */
	public void setAdjustOutVehicleFeeDtoList(
			List<AdjustOutVehicleFeeDto> adjustOutVehicleFeeDtoList) {
		this.adjustOutVehicleFeeDtoList = adjustOutVehicleFeeDtoList;
	}

	/**
	 * @return set the rewardOrPunishAgreementDto
	 */
	public RewardOrPunishAgreementDto getRewardOrPunishAgreementDto() {
		return rewardOrPunishAgreementDto;
	}

	/**
	 * @param rewardOrPunishAgreementDto
	 *            the rewardOrPunishAgreementDto to set
	 */
	public void setRewardOrPunishAgreementDto(
			RewardOrPunishAgreementDto rewardOrPunishAgreementDto) {
		this.rewardOrPunishAgreementDto = rewardOrPunishAgreementDto;
	}

	/**
	 * 获取 外请车费用修改日志Dto
	 * @author 269701--lln-2015-07-16
	 * @return the adjustOutVehicleFeeLogDto
	 */
	public AdjustOutVehicleFeeLogDto getAdjustOutVehicleFeeLogDto() {
		return adjustOutVehicleFeeLogDto;
	}

	/**
	 * 设置 外请车费用修改日志Dto
	 * @author 269701--lln-2015-07-16
	 * @param adjustOutVehicleFeeLogDto
	 *            the adjustOutVehicleFeeLogDto to set
	 */
	public void setAdjustOutVehicleFeeLogDto(
			AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto) {
		this.adjustOutVehicleFeeLogDto = adjustOutVehicleFeeLogDto;
	}

	/**
	 * 获取 外请车费用修改日志Entity
	 * @author 269701--lln-2015-07-16
	 * @return the adjustOutVehicleFeeLogEntity
	 */
	public AdjustOutVehicleFeeLogEntity getAdjustOutVehicleFeeLogEntity() {
		return adjustOutVehicleFeeLogEntity;
	}

	/**
	 * 设置 外请车费用修改日志Entity
	 * @author 269701--lln-2015-07-16
	 * @param adjustOutVehicleFeeLogEntity
	 *            the adjustOutVehicleFeeLogEntity to set
	 */
	public void setAdjustOutVehicleFeeLogEntity(
			AdjustOutVehicleFeeLogEntity adjustOutVehicleFeeLogEntity) {
		this.adjustOutVehicleFeeLogEntity = adjustOutVehicleFeeLogEntity;
	}

	/**
	 * 获取 外请车费用修改日志List
	 * @author 269701--lln-2015-07-16
	 * @return the logList
	 */
	public List<AdjustOutVehicleFeeLogEntity> getLogList() {
		return logList;
	}

	/**
	 * 设置 外请车费用修改日志List
	 * @author 269701--lln-2015-07-16
	 * @param logList
	 *            the logList to set
	 */
	public void setLogList(List<AdjustOutVehicleFeeLogEntity> logList) {
		this.logList = logList;
	}

	/**
	 * 获取 未审核记录条数
	 * @author 269701--lln-2015-07-16
	 * @return the noAuditCount
	 */
	public Integer getNoAuditCount() {
		return noAuditCount;
	}

	/**
	 * 设置 未审核记录条数
	 * @author 269701--lln-2015-07-16
	 * @param noAuditCount
	 *            the noAuditCount to set
	 */
	public void setNoAuditCount(Integer noAuditCount) {
		this.noAuditCount = noAuditCount;
	}

	/**
	 * 获取 审批中记录条数
	 * @author 269701--lln-2015-07-16
	 * @return the auditInCount
	 */
	public Integer getAuditInCount() {
		return auditInCount;
	}

	/**
	 * 设置 审批中记录条数
	 * @author 269701--lln-2015-07-16
	 * @param auditInCount
	 *            the auditInCount to set
	 */
	public void setAuditInCount(Integer auditInCount) {
		this.auditInCount = auditInCount;
	}

}