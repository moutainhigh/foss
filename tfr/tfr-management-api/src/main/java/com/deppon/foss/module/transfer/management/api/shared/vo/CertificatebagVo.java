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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/CertificatebagVo.java
 *  
 *  FILE NAME          :CertificatebagVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagQueryEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagReturnEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagTakeEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagReturnDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagTakeDto;
import com.deppon.foss.module.transfer.departure.api.shared.dto.VehiclePrintDTO;

/**
 * 证件包Vo
 * 
 * @author dp-liming
 * @date 2012-11-29 上午10:50:47
 */
public class CertificatebagVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7848844002239423966L;

	/**
	 * 证件包entity
	 */
	private CertificatebagEntity certificatebagEntity;

	/**
	 * 证件包dto
	 */
	private CertificatebagDto certificatebagDto;

	/**
	 * 证件包列表
	 */
	private List<CertificatebagEntity> certificatebagList;

	/**
	 * 证件包领取entity
	 */
	private CertificatebagTakeEntity takeEntity;

	/**
	 * 证件包归还Dto
	 */
	private CertificatebagReturnDto returnDto;

	/**
	 * 证件包归还entity
	 */
	private CertificatebagReturnEntity returnEntity;

	/**
	 * 证件包领取Dto
	 */
	private CertificatebagTakeDto takeDto;

	/**
	 * 证件包列表
	 */
	private List<CertificatebagQueryEntity> certificatebagQueryList;

	/**
	 * 开始 领取时间
	 */
	private String beginActualTakeTime;

	/**
	 * 结束领取时间
	 */
	private String endActualTakeTime;

	/**
	 * 开始 归还时间
	 */
	private String beginActualReturnTime;

	/**
	 * 结束 归还时间
	 */
	private String endActualReturnTime;

	// add by liangfuxiang 2013-4-13上午11:18:19 begin:ISSUE-2093
	/**
	 * 配置项与组织对应关系实体列表
	 */
	private List<ConfigOrgRelationEntity> configOrgRelationEntityList;

	/**
	 * configOrgRelationEntityList
	 * 
	 * @return the configOrgRelationEntityList
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public List<ConfigOrgRelationEntity> getConfigOrgRelationEntityList() {
		return configOrgRelationEntityList;
	}

	/**
	 * @param configOrgRelationEntityList the configOrgRelationEntityList to set Date:2013-4-13上午11:18:07
	 */

	public void setConfigOrgRelationEntityList(List<ConfigOrgRelationEntity> configOrgRelationEntityList) {
		this.configOrgRelationEntityList = configOrgRelationEntityList;
	}

	// add by liangfuxiang 2013-4-13上午11:18:51 end;

	// modify by liangfuxiang 2013-4-9下午2:36:10 begin:BUG-6732
	/**
	 * 车牌号
	 */
	private String vehicleNo;

	/**
	 * vehicleNo
	 * 
	 * @return the vehicleNo
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public String getVehicleNo() {
		return vehicleNo;
	}

	/**
	 * @param vehicleNo the vehicleNo to set Date:2013-4-9下午2:37:13
	 */

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	// modify by liangfuxiang 2013-4-9下午2:36:25 end;

	// add by liangfuxiang 2013-4-25下午3:38:55 begin: ISSUE-2673 ISSUE-2440
	/**
	 * 打印车牌
	 */
	private VehiclePrintDTO vehiclePrintDTO;

	// add by liangfuxiang 2013-4-25下午3:39:01 end;

	/**
	 * vehiclePrintDTO
	 * 
	 * @return the vehiclePrintDTO
	 * @since CodingExample Ver(编码范例查看) 1.0
	 */

	public VehiclePrintDTO getVehiclePrintDTO() {
		return vehiclePrintDTO;
	}

	/**
	 * @param vehiclePrintDTO the vehiclePrintDTO to set Date:2013-4-25下午3:39:42
	 */

	public void setVehiclePrintDTO(VehiclePrintDTO vehiclePrintDTO) {
		this.vehiclePrintDTO = vehiclePrintDTO;
	}

	/**
	 * 获取 证件包entity.
	 * 
	 * @return the 证件包entity
	 */
	public CertificatebagEntity getCertificatebagEntity() {
		return certificatebagEntity;
	}

	/**
	 * 设置 证件包entity.
	 * 
	 * @param certificatebagEntity the new 证件包entity
	 */
	public void setCertificatebagEntity(CertificatebagEntity certificatebagEntity) {
		this.certificatebagEntity = certificatebagEntity;
	}

	/**
	 * 获取 证件包dto.
	 * 
	 * @return the 证件包dto
	 */
	public CertificatebagDto getCertificatebagDto() {
		return certificatebagDto;
	}

	/**
	 * 设置 证件包dto.
	 * 
	 * @param certificatebagDto the new 证件包dto
	 */
	public void setCertificatebagDto(CertificatebagDto certificatebagDto) {
		this.certificatebagDto = certificatebagDto;
	}

	/**
	 * 获取 证件包列表.
	 * 
	 * @return the 证件包列表
	 */
	public List<CertificatebagEntity> getCertificatebagList() {
		return certificatebagList;
	}

	/**
	 * 设置 证件包列表.
	 * 
	 * @param certificatebagList the new 证件包列表
	 */
	public void setCertificatebagList(List<CertificatebagEntity> certificatebagList) {
		this.certificatebagList = certificatebagList;
	}

	/**
	 * 获取 证件包领取entity.
	 * 
	 * @return the 证件包领取entity
	 */
	public CertificatebagTakeEntity getTakeEntity() {
		return takeEntity;
	}

	/**
	 * 设置 证件包领取entity.
	 * 
	 * @param takeEntity the new 证件包领取entity
	 */
	public void setTakeEntity(CertificatebagTakeEntity takeEntity) {
		this.takeEntity = takeEntity;
	}

	/**
	 * 获取 证件包归还Dto.
	 * 
	 * @return the 证件包归还Dto
	 */
	public CertificatebagReturnDto getReturnDto() {
		return returnDto;
	}

	/**
	 * 设置 证件包归还Dto.
	 * 
	 * @param returnDto the new 证件包归还Dto
	 */
	public void setReturnDto(CertificatebagReturnDto returnDto) {
		this.returnDto = returnDto;
	}

	/**
	 * 获取 证件包归还entity.
	 * 
	 * @return the 证件包归还entity
	 */
	public CertificatebagReturnEntity getReturnEntity() {
		return returnEntity;
	}

	/**
	 * 设置 证件包归还entity.
	 * 
	 * @param returnEntity the new 证件包归还entity
	 */
	public void setReturnEntity(CertificatebagReturnEntity returnEntity) {
		this.returnEntity = returnEntity;
	}

	/**
	 * 获取 证件包领取Dto.
	 * 
	 * @return the 证件包领取Dto
	 */
	public CertificatebagTakeDto getTakeDto() {
		return takeDto;
	}

	/**
	 * 设置 证件包领取Dto.
	 * 
	 * @param takeDto the new 证件包领取Dto
	 */
	public void setTakeDto(CertificatebagTakeDto takeDto) {
		this.takeDto = takeDto;
	}

	/**
	 * 获取 证件包列表.
	 * 
	 * @return the 证件包列表
	 */
	public List<CertificatebagQueryEntity> getCertificatebagQueryList() {
		return certificatebagQueryList;
	}

	/**
	 * 设置 证件包列表.
	 * 
	 * @param certificatebagQueryList the new 证件包列表
	 */
	public void setCertificatebagQueryList(List<CertificatebagQueryEntity> certificatebagQueryList) {
		this.certificatebagQueryList = certificatebagQueryList;
	}

	/**
	 * 获取 开始 领取时间.
	 * 
	 * @return the 开始 领取时间
	 */
	public String getBeginActualTakeTime() {
		return beginActualTakeTime;
	}

	/**
	 * 设置 开始 领取时间.
	 * 
	 * @param beginActualTakeTime the new 开始 领取时间
	 */
	public void setBeginActualTakeTime(String beginActualTakeTime) {
		this.beginActualTakeTime = beginActualTakeTime;
	}

	/**
	 * 获取 结束领取时间.
	 * 
	 * @return the 结束领取时间
	 */
	public String getEndActualTakeTime() {
		return endActualTakeTime;
	}

	/**
	 * 设置 结束领取时间.
	 * 
	 * @param endActualTakeTime the new 结束领取时间
	 */
	public void setEndActualTakeTime(String endActualTakeTime) {
		this.endActualTakeTime = endActualTakeTime;
	}

	/**
	 * 获取 开始 归还时间.
	 * 
	 * @return the 开始 归还时间
	 */
	public String getBeginActualReturnTime() {
		return beginActualReturnTime;
	}

	/**
	 * 设置 开始 归还时间.
	 * 
	 * @param beginActualReturnTime the new 开始 归还时间
	 */
	public void setBeginActualReturnTime(String beginActualReturnTime) {
		this.beginActualReturnTime = beginActualReturnTime;
	}

	/**
	 * 获取 结束 归还时间.
	 * 
	 * @return the 结束 归还时间
	 */
	public String getEndActualReturnTime() {
		return endActualReturnTime;
	}

	/**
	 * 设置 结束 归还时间.
	 * 
	 * @param endActualReturnTime the new 结束 归还时间
	 */
	public void setEndActualReturnTime(String endActualReturnTime) {
		this.endActualReturnTime = endActualReturnTime;
	}

}