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
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/dao/impl/QueryPackedDao.java
 *  
 *  FILE NAME          :QueryPackedDao.java
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
 * PROJECT NAME: tfr-package
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.server.dao.impl
 * FILE    NAME: QueryPackedDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackedPersonEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.SerialRelationEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoAreaDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 包装录入、查询和修改的DAO层
 * @author 046130-foss-xuduowei
 * @date 2012-10-17 上午8:14:35
 */
public class QueryPackedDao extends iBatis3DaoImpl implements IQueryPackedDao {
	
	/**
	 * 根据查询条件查询包装录入信息
	 * @param queryPackedConditionEntity 查询条件
	 * @param limit 限制数
	 * @param start 开始数
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:51:08
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#queryPackedAll(com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryPackedResultEntity> queryPackedAll(
			QueryPackedConditionEntity queryPackedConditionEntity, int limit,
			int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectPacked", 
				queryPackedConditionEntity,rowBounds);
	}
	
	/**
	 * 
	 * 查询已包装的需要包装的货物信息，不分页，用于excel
	 * @param queryUnpackConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 上午8:09:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryPackedResultEntity> queryPackedAll(
			QueryPackedConditionEntity queryPackedConditionEntity) {
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectPackedWithExecl", 
				queryPackedConditionEntity);
	}
	
	/**
	 * 查询已包装信息总数
	 * @param queryPackedConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:51:08
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#queryPackedAll(com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity, int, int)
	 */
	@Override
	public Long queryTotalCount(
			QueryPackedConditionEntity queryPackedConditionEntity) {

		return (Long) getSqlSession().selectOne(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectPackedCount", 
				queryPackedConditionEntity);
	}

	/**
	 * 根据运单号查询包装信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:51:45
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#queryWaybillPack(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPackEntity> queryWaybillPack(String waybillno,String packageType) {
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("waybillno", waybillno);
		map.put("packageType", packageType);
		
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectWaybillPack", 
				map);
	}
	
	/**
	 * 根据运单号查询需要包装的运单信息，前提是根据运单号查询包装信息返回结果为空
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-24 下午3:37:57
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#queryUnWaybillPack(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPackEntity> queryUnWaybillPack(String waybillno) {
		
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectUnWaybillPack", 
				waybillno);
	}
	
	
	/**
	 * 获取当前运单号的最大标签
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:53:18
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#queryMaxSerialNo(java.lang.String)
	 */
	@Override
	public String queryMaxSerialNo(String waybillno) {

		return getSqlSession().selectOne(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectMaxSerialNo", 
				waybillno).toString();
	}
	
	/**
	 * 根据运单号获取新旧流水号信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:53:47
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#querySerialRelation(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialRelationEntity> querySerialRelation(String waybillno,String packageType) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("waybillno", waybillno);
		map.put("packageType", packageType);
		map.put("active", "Y");

		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectSerialRelation", 
				map);
	}
	
	/**
	 * 根据运单号获取新旧流水号信息
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:53:47
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#querySerialRelation(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialRelationEntity> querySerialRelation(String waybillno,String packageType,String active) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("waybillno", waybillno);
		map.put("packageType", packageType);
		map.put("active", active);
		
		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectSerialRelation", 
				map);
	}
	
	/**
	 * 根据关联id，得到包装人员信息
	 * @param id id
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:54:40
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#queryPackedPerson(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PackedPersonEntity> queryPackedPerson(String id) {

		return getSqlSession().selectList(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectPackedPersonList", 
				id);
	}
	
	/** 
	 * 保存包装录入主信息
	 * @param waybillPackEntity 包装录入主信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:55:14
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#addWaybillPackEntity(com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity)
	 */
	@Override
	public int insertWaybillPackEntity(WaybillPackEntity waybillPackEntity) {

		return getSqlSession().insert(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"insertWaybillPackEntity", 
				waybillPackEntity);
	}
	
	
	/**
	 * 修改包装录入主信息
	 * @param waybillPackEntity 包装录入主信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-24 下午4:31:52
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#updateWaybillPackEntity(com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity)
	 */
	@Override
	public int updateWaybillPackEntity(WaybillPackEntity waybillPackEntity) {
		
		return getSqlSession().update(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updateWaybillPackEntity", 
				waybillPackEntity);
	}
	
	/**
	 * 修改PDA包装录入主信息
	 * @param waybillPackEntity 包装录入主信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-24 下午4:31:52
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#updatePDAWaybillPackEntity(com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity)
	 */
	@Override
	public int updatePDAWaybillPackEntity(WaybillPackEntity waybillPackEntity) {
		return getSqlSession().update(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updatePDAWaybillPackEntity", 
				waybillPackEntity);
	}

	/** 
	 * 保存包装录入的包装人员信息
	 * @param packedPersonList 包装人员列表
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:55:44
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#addPackedPersonList(java.util.List)
	 */
	@Override
	public int insertPackedPersonList(List<PackedPersonEntity> packedPersonList) {
		
		//循环插入包装人员
		//肯定有性能问题，真服了这些架构师
		for(PackedPersonEntity packedPersonEntity : packedPersonList){
			getSqlSession().insert(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"insertPackedPersonEntity", packedPersonEntity);
		}
		/*return getSqlSession().insert(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"insertPackedPersonList", 
				packedPersonList);*/
		return FossConstants.SUCCESS;
	}
	
	
	/**
	 * 修改包装人员信息前，先清空原包装人员，只做新增操作
	 * @param packedId 包装录入id
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-24 下午5:03:26
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#deletePackedPersonList(java.lang.String)
	 */
	@Override
	public int deletePackedPersonList(String packedId) {

		return getSqlSession().delete(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"deletePackedPersonList", 
				packedId);
	}

	/** 
	 * 保存新旧流水号关系
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:56:20
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#addSerialRelationList(java.util.List)
	 */
	@Override
	public int insertSerialRelation(
			List<SerialRelationEntity> serialRelationList) {
		
		//循环插入
		for(SerialRelationEntity serialRelationEntity : serialRelationList){
			getSqlSession().insert(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"insertSerialRelationEntity", serialRelationEntity);
		}	
		/*return getSqlSession().insert(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"insertSerialRelationList", 
				serialRelationList);*/
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 修改新旧流水号关系
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:56:41
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#updateSerialRelationList(java.util.List)
	 */
	@Override
	public int updateSerialRelation(
			List<SerialRelationEntity> serialRelationList) {
		//循环插入
		for(SerialRelationEntity serialRelationEntity : serialRelationList){
			getSqlSession().update(
					PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updateSerialRelationEntity", 
					serialRelationEntity);
		}
		/*return getSqlSession().update(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updateSerialRelationList", 
				serialRelationList);*/
		return FossConstants.SUCCESS;
	}
	/**
	 * 删除新旧流水号关系
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-22 下午2:57:00
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#deleteSerialRelationList(java.util.List)
	 */
	@Override
	public int deleteSerialRelation(
			List<SerialRelationEntity> serialRelationList) {

		return getSqlSession().delete(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"deleteSerialRelationList", 
				serialRelationList);
	}
	/**
	 * 更改需要包装的流水号状态为未包装
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-23 下午1:40:34
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#updateSerialStatus(java.util.List)
	 */
	@Override
	public int updateSerialStatus(List<SerialRelationEntity> serialRelationList) {
		
		
		//循环更新
		for(SerialRelationEntity serialRelationEntity : serialRelationList){
			//为每一个实例追加修改时间
			serialRelationEntity.setModifyDate(new Date());
			getSqlSession().update(
					PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updateSerialEntityStatus", 
					serialRelationEntity);
		}	
		/*return getSqlSession().update(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updateSerialStatus", 
				serialRelationList);*/
		
		return FossConstants.SUCCESS;
	}
	/** 
	 * 修改需要包装的流水号状态为已包装
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-23 下午1:46:50
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#addSerialStatus(java.util.List)
	 */
	@Override
	public int addSerialStatus(List<SerialRelationEntity> serialRelationList) {
		//循环新增
		for(SerialRelationEntity serialRelationEntity : serialRelationList){
			//为每一个实例追加修改时间
			serialRelationEntity.setModifyDate(new Date());
			getSqlSession().update(
					PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"addSerialEntityStatus", 
					serialRelationEntity);
		}
		/*return getSqlSession().update(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"addSerialStatus", 
				serialRelationList);*/
		return FossConstants.SUCCESS;
	}
	/** 
	 * 修改需要包装的流水号状态为已包装
	 * @param serialRelationList 新旧流水号关系
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-23 下午1:46:50
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#updateSerialdetail(java.util.List)
	 */
	@Override
	public int updateSerialdetail(Map<String, Object> map) {
		map .put("modifyDate",new Date());//增加修改时间
		//循环新增
			getSqlSession().update(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updateSerialdetail",map);
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 完成包装时更新包装需求表中已包装件数
	 * @param waybillno 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午10:15:59
	 * @see com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao#updatePackedNum(java.lang.String)
	 */
	@Override
	public int updatePackedNum(String waybillno) {
		
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		//增加修改时间
		paramMap.put("modifyDate",new Date());
		paramMap.put("waybillNo",waybillno);
		return getSqlSession().update(
				PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"updatePackedNum", 
				paramMap);
	}
	
	/**
	 * 
	 * 查询流水号个数
	 * @param waybillNo 运单号
	 * @return 流水号个数
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-5 上午10:10:35
	 */
	@Override
	public int querySerialNoCount(String waybillNo) {
		
		return Integer.parseInt(
				getSqlSession().selectOne(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectSerialCount", 
						waybillNo).toString());
	}
	
	/**
	 * 
	 * 修改包装需求件数和包装录入的开单件数
	 * @param waybillno 运单号
	 * @param num 开单件数
	 * @author 046130-foss-xuduowei
	 * @date 2013-1-14 下午4:16:26
	 */
	@Override
	public int updateWaybillNum(Map<String,Object> map) {
		map.put("modifyDate", new Date());//增加修改时间
		int suOne = getSqlSession().update(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + "updateUnpackWaybillNum", map);
		//int suTwo = getSqlSession().update(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + "updatePackedWaybillNum", map);
		return suOne;
	}
	
	/**
	 * 根据运单号查询货物库存部门
	 * @author 046130-foss-xuduowei
	 * @date 2013-4-17 下午4:16:26
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerialNoAreaDto> querySerialNoStockByWaybill(
			Map<String, Object> map) {
		
		return getSqlSession().selectList(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + "querySerialNoStockByWaybill", map);
	}
	/**
	 * 修改包装需求的包装状态
	 * @author 046130-foss-xuduowei
	 * @date 2013-4-26 下午4:16:26
	 * @param map
	 * @return
	 */
	@Override
	public int updatePacakageRequirePackedStatus(String waybillNo) {
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		//增加修改时间
		paramMap.put("modifyDate",new Date());
		paramMap.put("waybillNo",waybillNo);
		//修改包装状态
		getSqlSession().update(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + 
				"updatePacakageRequirePackedStatus", paramMap);
		return FossConstants.SUCCESS;
	}
	
	/**
	<delete id="deleteActualPackageById" parameterType="String">
		delete from tfr.t_opt_actual_package
				where ID=#{item.id}
	</delete>
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年12月4日 13:17:05
	 * @param map
	 * @return
	 */
	@Override
	public int deleteActualPackageById(String id) {
		//删除
		getSqlSession().delete(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + 
				"deleteActualPackageById", id);
		return FossConstants.SUCCESS;
	}
	/**
	<delete id="deleteActualPackageDetailByActualPackageId" parameterType="String">
		delete from tfr.t_opt_actual_package_detail
				where actual_package_id=#{item.id}
	</delete>
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年12月4日 13:17:02
	 * @param map
	 * @return
	 */
	@Override
	public int deleteActualPackageDetailByActualPackageId(String id) {
		//删除
		getSqlSession().delete(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + 
				"deleteActualPackageDetailByActualPackageId", id);
		return FossConstants.SUCCESS;
	}
	/**
	<delete id="deletePackageRequestDetailByActualPackageId" parameterType="String">
		delete from tfr.t_opt_package_request_detail
				where actual_package_id=#{item.id}
	</delete>
	 * @author 134019-foss-yuyongxiang
	 * @date 2013年12月4日 13:16:57
	 * @param map
	 * @return
	 */
	@Override
	public int deletePackageRequestDetailByActualPackageId(String id) {
		//删除
		getSqlSession().delete(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE + 
				"deletePackageRequestDetailByActualPackageId", id);
		return FossConstants.SUCCESS;
	}

	/**
	 * @author niuly
	 * @date 2014-1-20上午8:56:16
	 * @function 根据查询条件查询包装的货物信息（为接送货提供的接口）
	 * @param queryPackedConditionEntity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QueryPackedResultEntity> queryPackedAllInfo(
			QueryPackedConditionEntity queryPackedConditionEntity) {
		return getSqlSession().selectList(PackagingConstants.PACKAGING_IBATIS_NAMESAPCE+"selectPacked", queryPackedConditionEntity);
	}
	
	
	
}