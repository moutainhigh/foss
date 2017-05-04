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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IPickupAndDeliverySmallZoneService.java
 * 
 * FILE NAME        	: IPickupAndDeliverySmallZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PickupAndDeliverySmallZoneException;

/**
 * 集中接送货小区service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:dp-xieyantao,date:2012-10-11 下午5:45:30
 * </p>
 * 
 * @author dp-xieyantao
 * @date 2012-10-11 下午5:45:30
 * @since
 * @version
 */
public interface IPickupAndDeliverySmallZoneService extends IService {

	/**
	 * 新增集中接送货小区
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:45:30
	 * @param entity
	 *            集中接送货小区实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int addPickupAndDeliverySmallZone(SmallZoneEntity entity);

	/**
	 * 根据code作废集中接送货小区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:45:30
	 * @param codeStr
	 *            虚拟编码拼接字符串
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int deletePickupAndDeliverySmallZoneByCode(String codeStr, String modifyUser);

	/**
	 * 修改集中接送货小区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:45:30
	 * @param entity
	 *            集中接送货小区实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	int updatePickupAndDeliverySmallZone(SmallZoneEntity entity);

	/**
	 * <p>
	 * 根据小区虚拟编码修改小区的区域编码、所属大区
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-12 上午8:12:26
	 * @param entity
	 * @return
	 * @see
	 */
	int updateSmallZoneByVirtualCode(SmallZoneEntity entity);
	
	/**
	 * <p>根据所属大区编码修改小区编码、所属大区编码为空</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-5-18 上午9:31:36
	 * @param entity 小区信息
	 * @return
	 * @see
	 */
	int updateSmallZoneByBigCode(SmallZoneEntity entity);

	/**
	 * 根据传入对象查询符合条件所有集中接送货小区信息
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:45:30
	 * @param entity
	 *            集中接送货小区实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<SmallZoneEntity> queryPickupAndDeliverySmallZones(
			SmallZoneEntity entity, int limit, int start);

	/**
	 * 根据条件查询接送货小区信息
	 * 
	 * @author 073586-LIXUEXING
	 * @date 2012-12-3 上午9:36:14
	 * @RETRUN List<SmallZoneEntity>
	 * @param
	 */
	List<SmallZoneEntity> querySmallZonesByNameOrCode(SmallZoneEntity entity);

	/**
	 * 统计总记录数
	 * 
	 * @author dp-xieyantao
	 * @date 2012-10-11 下午5:45:30
	 * @param entity
	 *            集中接送货小区实体
	 * @return
	 * @see
	 */
	Long queryRecordCount(SmallZoneEntity entity);

	/**
	 * 根据传入的管理部门Code、集中接送货大区的区域类型（接货区、送货区）查询符合条件 的集中接送货小区
	 * 
	 * @author 094463-foss-senate
	 * @date 2012-10-25 下午3:04:56
	 * @param deptCode
	 *            管理部门Code 必填
	 * @param type
	 *            区域类型 必填
	 * @param bigZoneVirtualCode
	 *            所属大区虚拟编码 选填 若为：null 查询所有没有所属大区的小区 否则:查询属于该大区的小区
	 * @return 集中接送货小区列表
	 * @see
	 */
	List<SmallZoneEntity> querySmallZonesByDeptCode(String deptCode,
			String type, String bigZoneVirtualCode);

	/**
	 * 根据区域编码查询集中接送货小区详细信息
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2012-10-29 下午2:27:08
	 * @param regionCode
	 *            区域编码
	 * @return
	 * @see
	 */
	SmallZoneEntity querySmallZoneByCode(String regionCode);

	/**
	 * <p>
	 * 根据大区的区域编码动态生成小区区域编码
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-11 下午4:14:42
	 * @param bigZoneRegionCode
	 *            大区的区域编码
	 * @return
	 * @see
	 */
	String generateRegionCode(String bigZoneRegionCode);

	/**
	 * <p>
	 * 验证小区名称是否唯一
	 * </p>
	 * 
	 * @author 094463-foss-xieyantao
	 * @date 2013-1-7 上午9:20:06
	 * @param regionName
	 *            集中接送货小区名称
	 * @return
	 * @see
	 */
	SmallZoneEntity querySmallZoneByName(String regionName);

	/**
	 * 根据传入条件 导出查询结果
	 * 
	 * @author 073586-FOSS-LIXUEXING
	 * @date 2012-10-29 下午2:27:08
	 * @param entity
	 *            小区实体
	 * @return
	 * @see
	 */
	ExportResource exportSmallZoneList(SmallZoneEntity entity);
	
	/**
	 * 根据传入对象查询符合条件所有集中接送货小区信息 （供导出用）
	 * 
	 * @author 132599-shenweihua
	 * @date 2013-11-15 上午8:43:30
	 * @param entity
	 *            集中接送货小区实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	List<SmallZoneEntity> queryPickupAndDeliverySmallZonesExport(
			SmallZoneEntity entity, int limit, int start)
			throws PickupAndDeliverySmallZoneException;
	/**
     * <p>
     * 根据所属大区编码修改小区编码、所属大区编码为空
     * </p>
     * 
     * @author 130346-foss-lifanghong
     * @date 2014-03-05 上午9:31:36
     * @param entity
     *            小区信息
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#updateSmallZoneByBigCode(java.lang.String)
     */
	SmallZoneEntity querySmallZoneByVirtualCode(String virtualCode);

}
