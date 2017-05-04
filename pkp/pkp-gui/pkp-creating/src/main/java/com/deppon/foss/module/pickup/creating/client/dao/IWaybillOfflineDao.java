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
 * PROJECT NAME	: pkp-creating
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/IWaybillOfflineDao.java
 * 
 * FILE NAME        	: IWaybillOfflineDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOfflineDto;

/**
 * 
 * 离线运单数据持久层接口
 * 
 * 定义借口不要写public修饰字了sornar报错  重复的修饰符   
 * @author 105089-foss-yangtong
 * @date 2012-10-16 下午5:28:47
 * @since
 * @version
 */
public interface IWaybillOfflineDao {
	
	//--定义借口不要写public修饰字了sornar报错  重复的修饰符
	
	/**
	 * 
	 * <p>
	 * 通过主键获取本地运单
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 下午5:29:19
	 * @param id
	 * @return
	 * @see
	 */
	WaybillOfflineEntity queryByPrimaryKey(String id);

	/**
	 * 
	 * <p>
	 * 通过运单号查本地运单
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 下午5:29:24
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillOfflineEntity queryByWaybillNo(String waybillNo);

	/**
	 * 
	 * <p>
	 * 获取最新本地运单
	 * </p>
	 * 
	 * @author Bobby
	 * @date 2012-10-16 下午5:29:27
	 * @return
	 * @see
	 */
	WaybillOfflineEntity queryLastWaybill();
	
    /**
     * 更新离线运单打印次数
     * 
     * @author foss-jiangfei
     * @date 2012-11-15 下午11:48:25
     * @param waybillNo
     * @return
     * @see
     */
    int updateWaybillPrintTimes(String waybillNo);
    
    /**
	 * 
	 * 获取离线运单
	 * @date 2012-10-16 下午5:29:27
	 * @return
	 * @see
	 */
	List<WaybillOfflineEntity> queryWaybillOffline(WaybillOfflineDto waybillOfflineDto);
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在OFFLINE
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	WaybillOfflineEntity queryOfflineByNo(String mixNo);
	
	/**
	 * 
	 * <p>
	 * 查询未提交的OFFLINE运单数量
	 * </p>
	 * @author niujian
	 * @return
	 * @see 
	 */
	Integer countOfflineActiveWayBill();

}