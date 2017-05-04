/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IPickupAndDeliveryBigZoneService.java
 * 
 * FILE NAME        	: IPickupAndDeliveryBigZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;

/**
 * 集中接送货大区service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-xieyantao,date:2012-10-11 下午5:51:34
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-11 下午5:51:34
 * @since
 * @version
 */
public interface IPickupAndDeliveryBigZoneService extends IService {

	/**
	 * 新增集中接送货大区
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:51:34
	 * @param entity
	 *            集中接送货大区实体
	 * @param addList
	 *            新增的小区虚拟编码集合
	 * @param deleteList
	 *            删除的小区虚拟编码集合
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addPickupAndDeliveryBigZone(BigZoneEntity entity, List<String> addList,
			List<String> deleteList);

	/**
	 * 根据code作废集中接送货大区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:51:34
	 * @param codeStr
	 *            虚拟编码拼接字符串
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deletePickupAndDeliveryBigZoneByCode(String codeStr, String modifyUser);

	/**
	 * 修改集中接送货大区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:51:34
	 * @param entity
	 *            集中接送货大区实体
	 * @param addList
	 *            新增的小区虚拟编码集合
	 * @param deleteList
	 *            删除的小区虚拟编码集合
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updatePickupAndDeliveryBigZone(BigZoneEntity entity,
			List<String> addList, List<String> deleteList);

	/**
	 * 根据传入对象查询符合条件所有集中接送货大区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:51:34
	 * @param entity
	 *            集中接送货大区实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<BigZoneEntity> queryPickupAndDeliveryBigZones(BigZoneEntity entity,
			int limit, int start);

	/**
	 * 根据传入对象查询符合条件所有集中接送货大区信息 同事 模糊查询 按照 大区 code和name
	 * 
	 * @author DP-LIXUEXING
	 * @date 2012-10-11 下午5:51:34
	 * @param entity
	 *            集中接送货大区实体
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<BigZoneEntity> queryBigZonesByNameOrCode(BigZoneEntity entity);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:51:34
	 * @param entity
	 *            集中接送货大区实体
	 * @return
	 * @see
	 */
	Long queryRecordCount(BigZoneEntity entity);

	/**
	 * 根据区域编码查询集中接送货大区详细信息
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-29 下午1:39:44
	 * @param regionCode
	 *            区域编码
	 * @return
	 * @see
	 */
	BigZoneEntity queryBigzoneByCode(String regionCode);

	/**
	 * <p>
	 * 根据大区虚拟编码查询集中接送货大区详细信息
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-12-11 下午7:34:29
	 * @param virtualCode
	 *            虚拟编码
	 * @return
	 * @see
	 */
	BigZoneEntity queryBigzoneByVirtualCode(String virtualCode);

	/**
	 * <p>
	 * 根据传入的车队调度组编码、区域类型生成集中接送货大区编码
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-11 上午9:13:14
	 * @param orgCode
	 *            车队调度组编码
	 * @param regionType
	 *            区域类型：接货区：PK 送货区：DE
	 * @return 集中接送货大区编码
	 * @see
	 */
	String generateCode(String orgCode, String regionType);

}
