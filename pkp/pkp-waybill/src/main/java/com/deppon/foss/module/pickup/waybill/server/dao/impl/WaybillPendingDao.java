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
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/dao/impl/WaybillPendingDao.java
 * 
 * FILE NAME        	: WaybillPendingDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillCustomerDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupDetailDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ExpressFeederPickupQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026113-foss-linwensheng
 * @date 2012-10-23 上午9:13:00
 */
public class WaybillPendingDao extends iBatis3DaoImpl implements
IWaybillPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillPendingEntityMapper.";

	/**
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午9:19:59
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>通过运单编号删除一条记录</p> 
	 * @author foss-sunrui
	 * @date 2012-10-31 下午3:40:57
	 * @param waybillNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#deleteByWaybillNo(java.lang.String)
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNo", waybillNo);
	}
	@Override
	public int deleteByWaybillNos(List<String> waybillNos) {
		return this.getSqlSession().delete(NAMESPACE + "deleteByWaybillNos", waybillNos);
	}
	/**
	 * 
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午9:21:30
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int insert(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		waybillPendingEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insert",waybillPendingEntity);
	}
	
	/**
	 * 
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午9:21:30
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int updateByWaybillNo(WaybillPendingEntity waybillPendingEntity) {
		return this.getSqlSession().update(NAMESPACE + "updateByWaybillNoSelective",waybillPendingEntity);
	}

	/**
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午10:08:31
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int insertSelective(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		waybillPendingEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertSelective",
				waybillPendingEntity);
	}

	/**
	 * 
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午9:24:01
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public WaybillPendingEntity queryByPrimaryKey(String id) {
		return (WaybillPendingEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectByPrimaryKey", id);
	}

	/** 
	 * 批量插入WaybillPendingEntity对象
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-3 下午4:45:40
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insertBatch(java.util.List)
	 */
	@Override
	public int addWaybillPendingEntityBatch(List<WaybillPendingEntity> pendingList){
		return this.getSqlSession().insert(NAMESPACE +"insertBatch",pendingList);
	}

	/**
	 * 
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午10:09:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(
			WaybillPendingEntity waybillPendingEntity) {
		return this.getSqlSession()
				.update(NAMESPACE + "updateByPrimaryKeySelective",
						waybillPendingEntity);
	}

	/**
	 * 
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-10-23 上午10:11:35
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updateByPrimaryKey(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int updateByPrimaryKey(WaybillPendingEntity waybillPendingEntity) {
		return this.getSqlSession().update(NAMESPACE + "updateByPrimaryKey",
				waybillPendingEntity);
	}

	/**
	 * 
	 * <p>根据运单号查询</p> 
	 * @author 102246-foss-shaohongliang
	 * @date 2012-10-26 下午4:03:23
	 * @param number
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryByWaybillNumber(java.lang.String)
	 */
	@Override
	public WaybillPendingEntity queryByWaybillNumber(String number) {
		return (WaybillPendingEntity) this.getSqlSession().selectOne(
				NAMESPACE + "selectByWaybillNumber", number);
	}


	/**
	 * <p>
	 * (查询运单待处理表)
	 * </p>
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPendingEntity> queryPending(
			WaybillPendingDto waybillPendingDto) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("active", FossConstants.ACTIVE);
		parms.put("pendingDto", waybillPendingDto);
		return this.getSqlSession().selectList( NAMESPACE + "getPendings", parms);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPendingEntity> queryPendingNoExpress(
			WaybillPendingDto waybillPendingDto) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("active", FossConstants.ACTIVE);
		parms.put("pendingDto", waybillPendingDto);
//		List<WaybillPendingEntity> pendingEntytyList = this.getSqlSession().selectList( NAMESPACE + "getPendingsNoLTLEwaybill", parms);
//		pendingEntytyList.addAll(this.getSqlSession().selectList( NAMESPACE + "getPendingsNoExpress", parms));
//		return pendingEntytyList;
		return this.getSqlSession().selectList( NAMESPACE + "getPendingsNoExpress", parms);
	}
	

	/**
	 * <p>
	 * 更改运单状态PENDING
	 * </p>
	 * @author 105089-FOSS-yangtong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	@Override
	public int updatePendingActive(String id) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("id", id);
		parms.put("active", FossConstants.INACTIVE);
		return this.getSqlSession().update(NAMESPACE + "updatePendingActive",parms);
	}

	/** 
	 * 根据运单号更新待处理运单状态为失效
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-22 下午5:04:04
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updatePendingActiveByNo(java.lang.String)
	 */
	@Override
	public int updatePendingActiveByNo(String waybillNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.INACTIVE);
		return this.getSqlSession().update(NAMESPACE + "updatePendingActiveByNo",parms);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public WaybillPendingEntity queryPendingByNo(String mixNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", mixNo);
		parms.put("active", FossConstants.ACTIVE);
		List<WaybillPendingEntity> list =  this.getSqlSession().selectList(NAMESPACE + "queryPending", parms);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	
	@Override
	public WaybillPendingEntity queryEWaybillPendingByNo(String orderNo,
			String active) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", orderNo);
		parms.put("active", active);
		List<WaybillPendingEntity> list =  this.getSqlSession().selectList(NAMESPACE + "queryEWaybillPendingByNo", parms);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	public WaybillPendingEntity queryPendingByNoAndOrderNo
	(String mixNo, String orderno) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", mixNo);
		parms.put("orderNo", orderno);
		parms.put("active", FossConstants.ACTIVE);
		return  (WaybillPendingEntity) this.getSqlSession().selectOne(NAMESPACE +
				"queryPendingByNoAndOrderNo", parms);
	}
	
	
	/**
	 * 从ｐｅｎｄｉｎｇ中查询PDA接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param args
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryPdaReceiveGoodsDto(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDto(
			Map<String, Object> args) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPdaReceiveGoodsDto", args);
	}

	/**
	 * 从运单表中查询ＰＤＡ接货记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-12
	 * @param args
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryPdaWaybillReceiveGoodsDto(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaReceiveGoodsDto> queryPdaWaybillReceiveGoodsDto(
			Map<String, Object> args) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPdaWaybillReceiveGoodsDto", args);
	}

	@Override
	public WaybillPendingEntity queryWaybillPendingEntityNo(String waybillNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		String   createcode =  (String) this.getSqlSession().selectOne(NAMESPACE + "queryWaybillPendingEntityNo", parms);
		WaybillPendingEntity waybillPendingEntity = new WaybillPendingEntity();
		waybillPendingEntity.setCreateOrgCode(createcode);
	  return waybillPendingEntity ;
		 
	}
	
	/**
	 * 根据运单号获取待补录信息
	 * @author WangQianJin
	 * @date 2013-7-26 下午8:33:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillPendingEntity getWaybillPendingEntityByWaybillNo(String waybillNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		List<WaybillPendingEntity> list=this.getSqlSession().selectList(NAMESPACE + "getWaybillPendingEntityByWaybillNo", parms);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public WaybillPendingEntity queryIsExpressBill(String waybillNo,
			String productCode) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("isExpress", FossConstants.YES);
		List<WaybillPendingEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryIsExpressBill", parms);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 根据运单号删除待补录信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-20 下午2:13:08
	 */
	@Override
	public int deletePendingByWaybillNo(String waybillNo){
		Map<String, Object> map = new HashMap<String, Object>();		
		map.put("active", FossConstants.INACTIVE);
		map.put("waybillNo", waybillNo);
		map.put("conActive", FossConstants.ACTIVE);
		map.put("pendingType", WaybillConstants.WAYBILL_STATUS_PDA_PENDING);
		return this.getSqlSession().update(NAMESPACE + "deletePendingByWaybillNo", map);
	}
	
	/**
	 * 根据运单号和状态获取待补录信息
	 * @author WangQianJin
	 * @date 2014-01-22 下午13:47:31
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillPendingEntity getPendingByWaybillNoAndType(String waybillNo,String pendingType){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.ACTIVE);
		parms.put("pendingType", pendingType);
		List<WaybillPendingEntity> list=this.getSqlSession().selectList(NAMESPACE + "getPendingByWaybillNoAndType", parms);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 从运单表中查询ＰＤＡ接货记录
	 * @author 045738-maojianqiang
	 * @date 2014-05-05
	 * @param args
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryPdaWaybillReceiveGoodsDto(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaReceiveGoodsDto> queryPdaWaybillReceiveGoodsDtoByDate(Map<String, Object> args) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPdaWaybillReceiveGoodsDtoByDate", args);
	}

	
	/**
	 * 从运单表中查询ＰＤＡ接货记录（快递）
	 * @author WangQianJin
	 * @date 2014-06-06
	 * @param args
	 * @return
	 * @@see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryPdaWaybillReceiveGoodsDto(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PdaReceiveGoodsDto> queryPdaExpressReceiveGoodsDtoByDate(Map<String, Object> args) {
		return this.getSqlSession().selectList(NAMESPACE + "queryPdaExpressReceiveGoodsDtoByDate", args);
	}
	/**
	 * 通过客户编码查询对应的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 19:54:47
	 * @param customerCodeList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DispatchEWaybillDto> queryEWaybillByCust(EWaybillConditionDto eWaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillByCust", eWaybillConditionDto);
	}

	/**
	 * 通过司机工号和车牌号进行相关电子运单数据的扫描，主要统计每个客户对应的电子运单数量
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 19:31:35
	 * @param eWaybillConditionDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EWaybillCustomerDto> queryEWaybillBigCust(EWaybillConditionDto eWaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillBigCust", eWaybillConditionDto);
	}

	/**
	 * 通过联系人编码查询对应联系方式
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-7-25 19:51:19
	 * @param eWaybillConditionDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EWaybillCustomerDto queryBigCustByCustContact(EWaybillConditionDto eWaybillConditionDto) {
		List<EWaybillCustomerDto> list = this.getSqlSession().selectList(NAMESPACE+"queryBigCustByCustContact", eWaybillConditionDto);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 查询逾期为受理的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-9-2 09:15:49
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPendingEntity> queryOverDaysEWaybillData(int overDays, Date startDate, int start, int limited) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("overDays", overDays);
		parms.put("startDate", startDate);
		RowBounds row = new RowBounds(start, limited);
		return this.getSqlSession().selectList(NAMESPACE+"queryOverDaysEWaybillData", parms, row);
	}
	
	/*
	 * 给官网提供的查询待激活电子运单详情方法
	 * @author FOSS-136334-BaiLei
	 */
	@SuppressWarnings("unchecked")
	public List<WaybillPendingEntity> queryUnactiveEWaybillInfoByCondition(Map<String, Object> args, boolean isRowBounds){
		if(args!=null){
			if(isRowBounds){
				if(args.get("pageNum")==null ||  args.get("pageSize") ==null){
					return this.getSqlSession().selectList(NAMESPACE + "queryUnactiveEWaybillInfoByCondition", args);
				}
				int start = (Integer) args.get("pageNum");
				int limit = (Integer) args.get("pageSize");
				if (start>1){ 
					start= (start - 1) * limit;
				}else{
					start=0;
				}
				RowBounds rb = new RowBounds(start, limit);
				return this.getSqlSession().selectList(NAMESPACE + "queryUnactiveEWaybillInfoByCondition", args, rb);
			}else{
				return this.getSqlSession().selectList(NAMESPACE + "queryUnactiveEWaybillInfoByCondition", args);	
			}
		}else{
			return null;
		}
		
	}
	
	/*
	 * 给官网提供的查询待激活电子运单方法
	 * @author FOSS-136334-BaiLei
	 */
	public int countQueryUnactiveEWaybillInfoByCondition(Map<String, Object> args){
	
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "countUnactiveQueryEWaybillInfoByCondition", args);
	}

	@Override
	public int countQueryWaybillPendingByWaybillNO(String waybillNO) {
		return (Integer)this.getSqlSession().selectOne(NAMESPACE + "countUnactiveQueryWaybillPendingByWaybillNO", waybillNO);
	}

	@Override
	public String getNextEWaybillNo() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sequenceName", "EWAYBILLNO_SEQ");
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getNextEWaybillNo", params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DispatchEWaybillDto> queryIndividualCustEwaybill(EWaybillConditionDto eWaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryIndividualCustEwaybill", eWaybillConditionDto);
	}

	/**
	 * 根据条件查询待激活的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-13 10:05:58
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillPendingEntity queryUnActiveEWaybillPendingByWaybilllNo(Map<String, Object> args) {
		List<WaybillPendingEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryUnActiveEWaybillPendingByWaybilllNo", args);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
	/**
	 * 根据条件查询待激活的电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-13 10:05:58
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillPendingEntity queryUnActiveEWaybillPendingByOrderNo(Map<String, Object> args) {
		List<WaybillPendingEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryUnActiveEWaybillPendingByOrderNo", args);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * <p>根据条件进行电子面单数据的查询</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 11:15:50
	 * @param ewaybillConditionDto
	 * @param type 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto) {
		return this.getSqlSession().selectList(NAMESPACE+"queryEWaybillSalesDepart", ewaybillConditionDto);
	}

	/**
	 * 查询暂存运单信息表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-22 15:45:34
	 * @param maps
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WaybillPendingEntity queryBasicWaybillPendingData(WaybillPendingDto waybillPendingDto) {
		List<WaybillPendingEntity> list = this.getSqlSession().selectList(NAMESPACE+"queryBasicWaybillPendingData", waybillPendingDto);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 返货开单页面，根据原单号在暂存表查询运单号
	 */
	@Override
	public String getWaybillNo(String originalWaybillNo) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("originalWaybillNo", originalWaybillNo);
		return (String) this.getSqlSession().selectOne(NAMESPACE + "getWaybillNo", params);
	}
	@Override
	/**
	 * 查询WAYBILL表中的运单号
	 */
	public String selectWaybillNo(String waybillNo) {
		return (String) this.getSqlSession().selectOne(NAMESPACE+"selectWaybillNo",waybillNo);
	}
	@Override
	public WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo){
		return (WaybillPendingEntity) this.getSqlSession().selectOne(NAMESPACE+"getNowaybillEntity",waybillNo);
	}

	/**
	 * 根据条件删除待补录运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-6-10 15:39:07
	 */
	@Override
	public int deleteWaybillPendingEntityByOrderNo(Map<String, Object> params) {
		return this.getSqlSession().delete(NAMESPACE+"deleteWaybillPendingEntityByOrderNo", params);
	}
	
	/**
	 * 二程接驳项目-接送货-根据快递员工号查询当前未接货的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-4-29 09:02:46
	 * @param queryDto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressFeederPickupDetailDto> getExpressFeederPickupDetail(ExpressFeederPickupQueryDto queryDto) {
		return this.getSqlSession().selectList(NAMESPACE+"getExpressFeederPickupDetail", queryDto);
	}

	@Override
	public int updateWaybillHandOverStatus(ExpressFeederPickupQueryDto queryDto) {
		return this.getSqlSession().update(NAMESPACE+"updateWaybillHandOverStatus", queryDto);
	}

	@Override
	public Long isExistPickUpDoneByWaybillNoList(ExpressFeederPickupQueryDto queryDto) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "isExistPickUpDoneByWaybillNoList", queryDto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPendingEntity> queryEWaybillPendingByParams(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE + "queryEWaybillPendingByParams", params);
	}

	/**
	 * 根据运单号集合查询对应的待补录数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-9-1 10:07:59
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WaybillPendingEntity> queryWaybillPendingEntityByWaybillNoList(Map<String, Object> params) {
		return this.getSqlSession().selectList(NAMESPACE+"queryWaybillPendingEntityByWaybillNoList", params);
	}
	
	/**
	 * 添加对应的运单表数据
	 * @author Foss-265475-zoushengli
	 * @date 2015-9-1 10:07:59
	 */
	@Override
	public String addWaybillEntity(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		waybillPendingEntity.setId(UUIDUtils.getUUID());
				
		this.getSqlSession().insert(NAMESPACE + "insertWaybill", waybillPendingEntity);

		return waybillPendingEntity.getId();
	}
	
	/**
	 * 添加对应的快递运单表数据
	 * @author Foss-265475-zoushengli
	 * @date 2015-9-1 10:07:59
	 */
	@Override
	public void addWaybillExpressEntity(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		waybillPendingEntity.setId(UUIDUtils.getUUID());
				
		this.getSqlSession().insert(NAMESPACE + "insertWaybillExpress", waybillPendingEntity);
	}
	/**
	 * 插入数据，包含waybillType字段
	 * @author Foss-323098  王鹏涛
	 * @date 2016年7月30日13:54:15
	 */
	public int insertForWaybillType(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		waybillPendingEntity.setId(UUIDUtils.getUUID());
		return this.getSqlSession().insert(NAMESPACE + "insertForWaybillType",waybillPendingEntity);
	}
}