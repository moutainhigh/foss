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
 *  PROJECT NAME  : tfr-scheduling-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 调度、发车计划、排班、月台、车辆管理等
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/scheduling/api/shared/dto/TfrLoadTruckTaskDetailDto.java
 * 
 *  FILE NAME     :TfrLoadTruckTaskDetailDto.java
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
/*
 * PROJECT NAME: tfr-scheduling-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.scheduling.api.shared.dto
 * FILE    NAME: TfrLoadTruckTaskDetailDto.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.TfrLoadTruckTaskDetailEntity;

/**
 * 车辆任务Dto
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-12-20 下午3:45:03
 */
public class TfrLoadTruckTaskDetailDto extends TfrLoadTruckTaskDetailEntity {

	private static final long serialVersionUID = -6833932104082266362L;
	/**
	 * 状态列表
	 */
	private List<String> list;

	/**
	 * 获取 状态列表.
	 * 
	 * @return the 状态列表
	 */
	public List<String> getList() {
		return list;
	}

	/**
	 * 设置 状态列表.
	 * 
	 * @param list
	 *            the new 状态列表
	 */
	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * 司机1编码
	 */
	private String driverCode1;
	/**
	 * 司机1姓名
	 */
	private String driverName1;
	/**
	 * 司机2编码
	 */
	private String driverCode2;
	/**
	 * 司机2姓名
	 */
	private String driverName2;

	/**
	 * 获取 司机1编码.
	 * 
	 * @return the 司机1编码
	 */
	public String getDriverCode1() {
		return driverCode1;
	}

	/**
	 * 设置 司机1编码.
	 * 
	 * @param driverCode1
	 *            the new 司机1编码
	 */
	public void setDriverCode1(String driverCode1) {
		this.driverCode1 = driverCode1;
	}

	/**
	 * 获取 司机1姓名.
	 * 
	 * @return the 司机1姓名
	 */
	public String getDriverName1() {
		return driverName1;
	}

	/**
	 * 设置 司机1姓名.
	 * 
	 * @param driverName1
	 *            the new 司机1姓名
	 */
	public void setDriverName1(String driverName1) {
		this.driverName1 = driverName1;
	}

	/**
	 * 获取 司机2编码.
	 * 
	 * @return the 司机2编码
	 */
	public String getDriverCode2() {
		return driverCode2;
	}

	/**
	 * 设置 司机2编码.
	 * 
	 * @param driverCode2
	 *            the new 司机2编码
	 */
	public void setDriverCode2(String driverCode2) {
		this.driverCode2 = driverCode2;
	}

	/**
	 * 获取 司机2姓名.
	 * 
	 * @return the 司机2姓名
	 */
	public String getDriverName2() {
		return driverName2;
	}

	/**
	 * 设置 司机2姓名.
	 * 
	 * @param driverName2
	 *            the new 司机2姓名
	 */
	public void setDriverName2(String driverName2) {
		this.driverName2 = driverName2;
	}

}