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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/server/dao/IQueryPackedDao.java
 *  
 *  FILE NAME          :IQueryPackedDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.server.dao
 * FILE    NAME: IQueryPackedDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackedPersonEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.SerialRelationEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoAreaDto;

/**
 * 包装模块的DAO接口
 * @author 046130-foss-xuduowei
 * @date 2012-10-17 上午8:13:25
 */
public interface IQueryPackedDao {
	/**
	 * 查询营业部代打包装
	 * @param queryPackedConditionEntity 查询条件
	 * @param limit 限制数
	 * @param start 开始数
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-18 上午10:35:08
	 */
	List<QueryPackedResultEntity> queryPackedAll(QueryPackedConditionEntity queryPackedConditionEntity,int limit,int start);
	/**
	 * 
	 * 查询已包装的需要包装的货物信息，不分页，用于excel
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 上午8:09:54
	 */
	List<QueryPackedResultEntity> 
	queryPackedAll(QueryPackedConditionEntity queryPackedConditionEntity);
	/**
	 * 查询包装录入主信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	List<WaybillPackEntity> queryWaybillPack(String waybillno,String packageType);
	/**
	 * 查询未进行包装的需要包装的运单信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:29
	 */
	List<WaybillPackEntity> queryUnWaybillPack(String waybillno);
	/**
	 * 查询新旧流水号关系
	 * @param waybillno 运单号
	 * @param packageType 包装类型
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:36
	 */
	List<SerialRelationEntity> querySerialRelation(String waybillno,String packageType);
	/**
	 * 查询新旧流水号关系
	 * @param waybillno 运单号
	 * @param packageType 包装类型
	 * @param active 是否有效
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年11月29日 11:59:16
	 */
	List<SerialRelationEntity> querySerialRelation(String waybillno,String packageType,String active) ;
	/**
	 * 查询包装人员
	 * @param id id
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	List<PackedPersonEntity> queryPackedPerson(String id);
	/**
	 * 查询最大流水号
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	String 	queryMaxSerialNo(String waybillno);
	/**
	 * 新增包装录入主信息
	 * @param waybillPackEntity 包装录入主信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int insertWaybillPackEntity(WaybillPackEntity waybillPackEntity);
	/**
	 * 更新包装录入主信息
	 * @param waybillPackEntity 包装录入主信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int updateWaybillPackEntity(WaybillPackEntity waybillPackEntity);
	/**
	 * 更新PDA包装录入主信息
	 * @param waybillPackEntity 包装录入主信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int updatePDAWaybillPackEntity(WaybillPackEntity waybillPackEntity);
	/**
	 * 新增包装人员信息
	 * @param packedPersonList 包装人员列表
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int insertPackedPersonList(List<PackedPersonEntity> packedPersonList);
	/**
	 * 更新包装需求名细表
	 * @param map ->
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年11月30日 09:58:11
	 */
	int updateSerialdetail(Map<String, Object> map);
	/**
	 * 删除包装人员信息
	 * @param packedId 包装录入id
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int deletePackedPersonList(String packedId);
	/**
	 * 新增流水号关系
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int insertSerialRelation(List<SerialRelationEntity> serialRelationList);
	/**
	 * 更新流水号关系
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int updateSerialRelation(List<SerialRelationEntity> serialRelationList);
	/**
	 * 删除流水号关系
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int deleteSerialRelation(List<SerialRelationEntity> serialRelationList);
	/**
	 * 变更流水号状态为已包装
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int addSerialStatus(List<SerialRelationEntity> serialRelationList);
	/**
	 * 变更流水号状态为未包装
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int updateSerialStatus(List<SerialRelationEntity> serialRelationList);
	/**
	 * 更新包装需求的已包装件数
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午10:35:08
	 */
	int updatePackedNum(String waybillno);
	/**
	 * 
	 * 查询已包装货物总数
	 * @param queryPackedConditionEntity 查询条
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 上午9:31:02
	 */
	Long queryTotalCount(QueryPackedConditionEntity queryPackedConditionEntity);
	/**
	 * 
	 * 查询运单件数
	 * @param waybillNo 运单号
	 * @return 流水号个数
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-5 上午10:10:35
	 */
	int querySerialNoCount(String waybillNo);
	
	/**
	 * 
	 * 修改包装需求件数和包装录入的开单件数
	 * @param waybillno 运单号
	 * @param num 开单件数
	 * @author 046130-foss-xuduowei
	 * @date 2013-1-14 下午4:16:26
	 */
	int updateWaybillNum(Map<String,Object> map);
	
	/**
	 * 根据运单号查询货物库存部门
	 * @author 046130-foss-xuduowei
	 * @date 2013-4-17 下午4:16:26
	 * @param map
	 * @return
	 */
	List<SerialNoAreaDto> querySerialNoStockByWaybill(Map<String,Object> map);
	/**
	 * 修改包装需求的包装状态
	 * @author 046130-foss-xuduowei
	 * @date 2013-4-26 下午4:16:26
	 * @param map
	 * @return
	 */
	int updatePacakageRequirePackedStatus(String waybillNo);
	/**
	<delete id="deleteActualPackageById" parameterType="String">
		delete from tfr.t_opt_actual_package
				where ID=#{item.id}
	</delete>
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年12月4日 13:16:11
	 * @param map
	 * @return
	 */
	int deleteActualPackageById(String id);
	/**
	<delete id="deleteActualPackageDetailByActualPackageId" parameterType="String">
		delete from tfr.t_opt_actual_package_detail
				where actual_package_id=#{item.id}
	</delete>
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年12月4日 13:16:14
	 * @param map
	 * @return
	 */
	int deleteActualPackageDetailByActualPackageId(String id);
	/**
	<delete id="deletePackageRequestDetailByActualPackageId" parameterType="String">
		delete from tfr.t_opt_package_request_detail
				where actual_package_id=#{item.id}
	</delete>
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年12月4日 13:16:17
	 * @param map
	 * @return
	 */
	int deletePackageRequestDetailByActualPackageId(String id);
	/**
	 * @author niuly
	 * @date 2014-1-20上午8:56:16
	 * @function 根据查询条件查询包装的货物信息（为接送货提供的接口）
	 * @param queryPackedConditionEntity
	 * @return
	 */
	List<QueryPackedResultEntity> queryPackedAllInfo(QueryPackedConditionEntity queryPackedConditionEntity);
	
}