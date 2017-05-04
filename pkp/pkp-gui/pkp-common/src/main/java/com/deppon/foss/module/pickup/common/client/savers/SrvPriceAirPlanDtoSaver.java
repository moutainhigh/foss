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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/dto/PickupToDoMsgDto.java
 * 
 * FILE NAME        	: PickupToDoMsgDto.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.savers;

import java.util.Date;
import java.util.List;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.framework.client.component.sync.AbstractSyncDataSaver;
import com.deppon.foss.module.pickup.common.client.service.DownLoadDataServiceFactory;
import com.deppon.foss.module.pickup.common.client.service.impl.PricePlanService;
import com.deppon.foss.module.pickup.waybill.shared.dto.PriceAirPlanDto;


/**
 * 获取空运价格详细信息
 * \\\
 *  客户端在离线使用时，需要开单计算价格。
 * 所以价格信息会提前下载到客户端。每次用户在登录时，
 * 会根据用户所在组织，查询下载和自己组织相关的信息。
 * 这些信息包括：基础数据，价格，折扣等。
 * 以下主要描述与价格，折扣相关的数据的下载。
 * 目前框架主要支持的是单表下载，
 * 根据最后更新时间来增量更新数据。
 * 但是价格里面关系复杂，根据最后更新时间，
 * 不能满足下载到正确的价格的需求。所以，在此方案中，
 * 会描述如何下载到准确的价格。并且是做增量下载。
 * 
 * @author 105089-foss-yangtong
 * @date 2012-11-6 下午2:57:02
 */
public class SrvPriceAirPlanDtoSaver  extends AbstractSyncDataSaver {
	//区域id
	private String regionID;
	/**
	 * 
	 * 功能：映射实体类
	 * 该方法主要用于返回实体类型  用于给客户端判断
	 * 下载的是何种类型的数据实体
	 * 和对应实体的是那一张票
	 * 该实体的类名会保存在水位表的的类信息中
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public Class<?> getSaveType() {
		return PriceAirPlanDto.class;
	}
	
	
	/**
	 * 
	 * 功能：保存数据
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public Date saveData(List<?> data) {
		Date date = null;
		List<PriceAirPlanDto> list = (List<PriceAirPlanDto>)data;
		long mxVersion = 0;
		if(list != null && !list.isEmpty()){
			PricePlanService pricePlanService = DownLoadDataServiceFactory
					.getPricePlanService();
			for(PriceAirPlanDto priceRegionAir : list){
				pricePlanService.saveOrUpdate(priceRegionAir);
				Long version = priceRegionAir.getVersionNo();
				regionID = priceRegionAir.getPriceRegionId();
				//这个是region code
				//获得最大版本号
				if(version != null && version>mxVersion){
					mxVersion = version;
				}
			}
		} 
		//创建水位日期
		date = new Date();
		//设置水位时间戳
		//该时间戳标记了上次下载的位置
		//返回以后保存于水位表中
		//下次下载会从该时间戳起进行增量下载
		date.setTime(mxVersion);
		//返回水位信息
		//由于hqlsql 和oracle精度不同， 
		//因此我们在下次下载的时候 可能会对该时间戳做1秒钟的提前量
		//但是这些多余的数据会进行update操作  
		//所以不会有很多影像
		return date;
	}
	
	/**
	 * 
	 * 功能：取区域ID
	 * @param:
	 * @return:
	 * @since:1.6
	 */
	public String getRegionID() {
		return regionID;
	}

}
