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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/AbandonGoodsApplicationDao.java
 * 
 * FILE NAME        	: AbandonGoodsApplicationDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IAbandonGoodsApplicationDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonGoodsResultDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsOaDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.AbandonedGoodsSearchDto;

/**
* @ClassName: 	AbandonGoodsApplicationDao
* @Description: 弃货dao
* @author 
* @date 		2012-10-25 上午10:40:12
*
*/
public class AbandonGoodsApplicationDao extends iBatis3DaoImpl implements IAbandonGoodsApplicationDao {

	//弃货功能模块命名空间
	private static final String ABANDONGOODSAPPLICATIONNAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.shared.domain.AbandonGoodsApplicationEntity.";
	//转弃货申请
	private static final String APPLYABANDONGOODS = "insertAbandonGoodsApplication";
	//编辑预弃货信息
	private static final String EDITAPPLYABANDONGOODS="editAbandonGoodsApplication";
	//弃货申请查询
	private static final String SEARCHABANDONGOODSLIST = "searchAbandonGoodsList";
	/** 无标签弃货查询*/
	private static final String SEARCH_NOTAG_ABANDON_GOODS_LIST = "searchNoTagAbandonGoodsList"; 
	//弃货申请查询
	private static final String SEARCHABANDONGOODSLISTWITHNAMEAMBUOIUS = "searchAbandonGoodsListWithAmbuiousName";
		
	//弃货申请COUNT
	private static final String GETTOTALCOUNT = "getTotalCount";
	//弃货详细信息
	private static final String ABANDONGOODDETAILL = "getAbandonGoodsDetailById";
	//弃货详细信息 by waybill no
	private static final String ABANDONGOODDETAILLWAYBILLNO = "getAbandonGoodsDetailByWaybillNo";
	//货物入库时间
	private static final String INSTOCKTIME = "getInStockTimeByWaybillNoAndOrgCode";
	//弃货申请查询by time
	private static final String SEARCHABANDONGOODSBYTIMELIST = "searchAbandonGoodsByTimeList";
	//update by waybill no 
	private static final String UPDATEBYWAYBILLNO = "updateByWaybillNo";
	private static final String UPDATEBYKEY= "updateByPrimaryKeySelective";
	//费用弃货工作流 query
	private static final String QUERYABANDONEDGOODSOADTO = "queryAbandonedGoodsOaDto";
	//unchecked
	private static final String UNCHECKED="unchecked";
	/** 根据id查询弃货类型 */
	private static final String GET_ABANDON_GOODS_TYPE_BY_ID = "getAbandonGoodsTypeById";
	/** 根据id查询弃货详细信息 */
	private static final String GET_NOTAG_ABANDON_GOODS_DETAIL_BY_ID = "getNoTagAbandonGoodsDetailById";
	
	/**
	 * 插入数据
	 */
	public void insertAbandonGoodsApplication(
			AbandonGoodsApplicationEntity entity) {
		getSqlSession().insert(ABANDONGOODSAPPLICATIONNAMESPACE + APPLYABANDONGOODS, entity);

	}

	/**
	 * 编辑预弃货信息
	 */
	public void editAbandonGoodsApplication(AbandonGoodsApplicationEntity entity){
		getSqlSession().update(ABANDONGOODSAPPLICATIONNAMESPACE+EDITAPPLYABANDONGOODS, entity);
		
	}
	
	/**
	 * 查询弃货
	 */
	@SuppressWarnings(UNCHECKED)
	public List<AbandonGoodsResultDto> searchAbandonGoodsList(
			AbandonedGoodsSearchDto dto) {
		return getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE + SEARCHABANDONGOODSLIST, dto);
	}
	
	/**
	 * 查询无标签转弃货
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AbandonGoodsResultDto> searchNoTagAbandonGoodsList(
			AbandonedGoodsSearchDto dto) {
		return getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE + SEARCH_NOTAG_ABANDON_GOODS_LIST, dto);
	}

	/**
	 * 查询弃货 名字可以模糊匹配
	 */
	@SuppressWarnings(UNCHECKED)
	public List<AbandonGoodsResultDto> searchAbandonGoodsListWithAmbuiousName(AbandonedGoodsSearchDto dto){
		return getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE 
				+ SEARCHABANDONGOODSLISTWITHNAMEAMBUOIUS, dto);
		
	}

	/**
	 * 查询弃货 总数量
	 */
	public Long getTotalCount(AbandonedGoodsSearchDto dto) {
		return (Long)this.getSqlSession().selectOne(ABANDONGOODSAPPLICATIONNAMESPACE + GETTOTALCOUNT,dto);
	}
	
	/**
	 * 查询弃货 总数量
	 */
	@SuppressWarnings(UNCHECKED)
	@Override
	public List<AbandonGoodsResultDto> queryAbandonedsForStatus(AbandonedGoodsSearchDto dto) {
		return this.getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE + "queryAbandonedsForStatus",dto);
	}
	
	/**
	 * 通过弃货ID，获得弃货的详细信息
	 */
	public AbandonedGoodsDetailDto getAbandonGoodsDetailById(String id) {
		return (AbandonedGoodsDetailDto)this.getSqlSession().selectOne(
				ABANDONGOODSAPPLICATIONNAMESPACE + ABANDONGOODDETAILL,id);
	}
	
	/**
	 * 通过弃货waybill no，获得弃货的详细信息
	 */
	@SuppressWarnings("unchecked")
	public AbandonedGoodsDetailDto getAbandonGoodsDetailByWaybillNo(String waybillNo) {
		List<AbandonedGoodsDetailDto> list =this.getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE +
				ABANDONGOODDETAILLWAYBILLNO,waybillNo);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 更具运单号和部门编码，获得货物在该部门的入库时间
	 */
	public Date getInStockTimeByWaybillNoAndOrgCode(Map<String,String> argMap) {
		return (Date)this.getSqlSession().selectOne(ABANDONGOODSAPPLICATIONNAMESPACE + INSTOCKTIME,argMap);
	}
	
	/**
	 * UPDATE AbandonGoodsApplicationEntity By Waybill No
	 */
	public int updateByWaybillNo(AbandonGoodsApplicationEntity entity){
		return this.getSqlSession().update(ABANDONGOODSAPPLICATIONNAMESPACE + UPDATEBYWAYBILLNO, entity);
	}
	@Override
	public int updateByKey(AbandonGoodsApplicationEntity entity){
		return this.getSqlSession().update(ABANDONGOODSAPPLICATIONNAMESPACE + UPDATEBYKEY, entity);
	}
	
	/**
	 * 通过弃货time，获得弃货List
	 */
	@SuppressWarnings(UNCHECKED)
	public List<AbandonedGoodsDetailDto> searchAbandonGoodsByTimeList(
			AbandonedGoodsSearchDto dto){
		return getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE + 
				SEARCHABANDONGOODSBYTIMELIST, dto);
	}
	
	/**
	 * 费用弃货工作流 query
	 * @date 2012-11-28 下午3:32:10
	 */
	@SuppressWarnings(UNCHECKED)
	public List<AbandonedGoodsOaDto> queryAbandonedGoodsOaDto(String waybillNo){
		return getSqlSession().selectList(ABANDONGOODSAPPLICATIONNAMESPACE + 
				QUERYABANDONEDGOODSOADTO, waybillNo);
	}

	/**
	 * 通过id查询弃货类型
	 */
	@Override
	public String getAbandonGoodsTypeById(String id) {
		return (String)this.getSqlSession().selectOne(
				ABANDONGOODSAPPLICATIONNAMESPACE + GET_ABANDON_GOODS_TYPE_BY_ID, id);
	}

	/**
	 * 通过弃货id，获得无标签转弃货的详细信息
	 * @param id
	 * @return
	 */
	@Override
	public AbandonedGoodsDetailDto getNoTagAbandonGoodsDetailById(String id) {		
		 return (AbandonedGoodsDetailDto)this.getSqlSession().selectOne(
				ABANDONGOODSAPPLICATIONNAMESPACE + GET_NOTAG_ABANDON_GOODS_DETAIL_BY_ID,id);		
	}
	
	
}