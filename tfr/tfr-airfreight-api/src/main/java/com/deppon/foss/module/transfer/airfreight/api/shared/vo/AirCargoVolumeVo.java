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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/airfreight/api/shared/vo/AirCargoVolumeVo.java
 *  
 *  FILE NAME          :AirCargoVolumeVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirCargoVolumeQueryEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirSpaceDetailVolumeEntity;

/**
 *  空运货量统计Vo
 * 
 * @author dp-liming
 * @date 2012-10-18 下午17:39:30
 */
public class AirCargoVolumeVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924120709731049929L;
	/**
	 * 空运货量实体
	 */
	private AirCargoVolumeQueryEntity airCargoVolumeEntity;	
	/**
	 * 线路舱位实体
	 */
	private AirSpaceDetailVolumeEntity airSpaceDetaiEntity;	
	/**
	 * 开始时间
	 */
	private String beginCreateTime;
	/**
	 * 结束时间
	 */
	private String endCreateTime;
	
	/**
	 * 配载开始时间
	 */
	private String handoverTimeBegin;
	
	/**
	 * 配载结束时间
	 */
	private String handoverTimeEnd;
	
	/**
	 * 空调总调
	 */
	private String assembleOrgName;
	/**
	 * 空运货量明细
	 */
	List<AirCargoVolumeQueryEntity> airCargoVolumeList;
	/**
	 * 线路舱位明细
	 */
	List<AirSpaceDetailVolumeEntity> airSpaceDetaiList;

	/**
	 * 获取 配载开始时间.
	 *
	 * @return the 配载开始时间
	 */
	public String getHandoverTimeBegin() {
		return handoverTimeBegin;
	}

	/**
	 * 设置 配载开始时间.
	 *
	 * @param handoverTimeBegin the new 配载开始时间
	 */
	public void setHandoverTimeBegin(String handoverTimeBegin) {
		this.handoverTimeBegin = handoverTimeBegin;
	}

	/**
	 * 获取 配载结束时间.
	 *
	 * @return the 配载结束时间
	 */
	public String getHandoverTimeEnd() {
		return handoverTimeEnd;
	}

	/**
	 * 设置 配载结束时间.
	 *
	 * @param handoverTimeEnd the new 配载结束时间
	 */
	public void setHandoverTimeEnd(String handoverTimeEnd) {
		this.handoverTimeEnd = handoverTimeEnd;
	}

	/**
	 * 获取 空运货量实体.
	 *
	 * @return the 空运货量实体
	 */
	public AirCargoVolumeQueryEntity getAirCargoVolumeEntity() {
		return airCargoVolumeEntity;
	}
	
	/**
	 * 设置 空运货量实体.
	 *
	 * @param airCargoVolumeEntity the new 空运货量实体
	 */
	public void setAirCargoVolumeEntity(
			AirCargoVolumeQueryEntity airCargoVolumeEntity) {
		this.airCargoVolumeEntity = airCargoVolumeEntity;
	}
	
	/**
	 * 获取 线路舱位实体.
	 *
	 * @return the 线路舱位实体
	 */
	public AirSpaceDetailVolumeEntity getAirSpaceDetaiEntity() {
		return airSpaceDetaiEntity;
	}
	
	/**
	 * 设置 线路舱位实体.
	 *
	 * @param airSpaceDetaiEntity the new 线路舱位实体
	 */
	public void setAirSpaceDetaiEntity(
			AirSpaceDetailVolumeEntity airSpaceDetaiEntity) {
		this.airSpaceDetaiEntity = airSpaceDetaiEntity;
	}
	
	/**
	 * 获取 开始时间.
	 *
	 * @return the 开始时间
	 */
	public String getBeginCreateTime() {
		return beginCreateTime;
	}
	
	/**
	 * 设置 开始时间.
	 *
	 * @param beginCreateTime the new 开始时间
	 */
	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	
	/**
	 * 获取 结束时间.
	 *
	 * @return the 结束时间
	 */
	public String getEndCreateTime() {
		return endCreateTime;
	}
	
	/**
	 * 设置 结束时间.
	 *
	 * @param endCreateTime the new 结束时间
	 */
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	
	/**
	 * 获取 空运货量明细.
	 *
	 * @return the 空运货量明细
	 */
	public List<AirCargoVolumeQueryEntity> getAirCargoVolumeList() {
		return airCargoVolumeList;
	}
	
	/**
	 * 设置 空运货量明细.
	 *
	 * @param airCargoVolumeList the new 空运货量明细
	 */
	public void setAirCargoVolumeList(
			List<AirCargoVolumeQueryEntity> airCargoVolumeList) {
		this.airCargoVolumeList = airCargoVolumeList;
	}
	
	/**
	 * 获取 线路舱位明细.
	 *
	 * @return the 线路舱位明细
	 */
	public List<AirSpaceDetailVolumeEntity> getAirSpaceDetaiList() {
		return airSpaceDetaiList;
	}
	
	/**
	 * 设置 线路舱位明细.
	 *
	 * @param airSpaceDetaiList the new 线路舱位明细
	 */
	public void setAirSpaceDetaiList(
			List<AirSpaceDetailVolumeEntity> airSpaceDetaiList) {
		this.airSpaceDetaiList = airSpaceDetaiList;
	}
	
	/**
	 * 获取 空调总调.
	 *
	 * @return the 空调总调
	 */
	public String getAssembleOrgName() {
		return assembleOrgName;
	}
	
	/**
	 * 设置 空调总调.
	 *
	 * @param assembleOrgName the new 空调总调
	 */
	public void setAssembleOrgName(String assembleOrgName) {
		this.assembleOrgName = assembleOrgName;
	}

	

}