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
 *  PROJECT NAME  : tfr-ontheway-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/ontheway/api/shared/domain/VehicleStatistics.java
 *  
 *  FILE NAME          :VehicleStatistics.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.ontheway.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 
 * 在途界面显示车辆的其他信息
 * @author foss-liubinbin(for IBM)
 * @date 2012-12-25 上午9:12:49
 */
public class VehicleStatistics extends BaseEntity
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/********** 长途车辆数量 ************/
	private long longVehicleQuantity;  //长途车辆数量
    
	/********** 短途GPS离线车辆数量 ************/
    private long shortVehicleQuantity;  //短途GPS离线车辆数量
    
    /********** 接送货GPS离线车辆数量 ************/
    private long pkpVehicleQuantity;  //接送货GPS离线车辆数量

	/**
	 * 获取 ******** 长途车辆数量 ***********.
	 *
	 * @return the ******** 长途车辆数量 ***********
	 */
	public long getLongVehicleQuantity()
	{
		return longVehicleQuantity;
	}

	/**
	 * 设置 ******** 长途车辆数量 ***********.
	 *
	 * @param longVehicleQuantity the new ******** 长途车辆数量 ***********
	 */
	public void setLongVehicleQuantity(long longVehicleQuantity)
	{
		this.longVehicleQuantity = longVehicleQuantity;
	}

	/**
	 * 获取 ******** 短途GPS离线车辆数量 ***********.
	 *
	 * @return the ******** 短途GPS离线车辆数量 ***********
	 */
	public long getShortVehicleQuantity()
	{
		return shortVehicleQuantity;
	}

	/**
	 * 设置 ******** 短途GPS离线车辆数量 ***********.
	 *
	 * @param shortVehicleQuantity the new ******** 短途GPS离线车辆数量 ***********
	 */
	public void setShortVehicleQuantity(long shortVehicleQuantity)
	{
		this.shortVehicleQuantity = shortVehicleQuantity;
	}

	/**
	 * 获取 ******** 接送货GPS离线车辆数量 ***********.
	 *
	 * @return the ******** 接送货GPS离线车辆数量 ***********
	 */
	public long getPkpVehicleQuantity()
	{
		return pkpVehicleQuantity;
	}

	/**
	 * 设置 ******** 接送货GPS离线车辆数量 ***********.
	 *
	 * @param pkpVehicleQuantity the new ******** 接送货GPS离线车辆数量 ***********
	 */
	public void setPkpVehicleQuantity(long pkpVehicleQuantity)
	{
		this.pkpVehicleQuantity = pkpVehicleQuantity;
	}
	
	
	
}