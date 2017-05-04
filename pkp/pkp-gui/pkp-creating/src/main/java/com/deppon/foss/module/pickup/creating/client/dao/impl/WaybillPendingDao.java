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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillPendingDao.java
 * 
 * FILE NAME        	: WaybillPendingDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.creating.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillPendingDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillOfflineEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaReceiveGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * @author 025000-foss-helong
 * @date 2012-10-23 上午9:13:00
 */
public class WaybillPendingDao implements IWaybillPendingDao {

	private static final String NAMESPACE = "foss.pkp.WaybillPendingEntityMapper.";

	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午9:19:59
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#deleteByPrimaryKey(java.lang.String)
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return this.sqlSession.delete(NAMESPACE + "deleteByPrimaryKey", id);
	}

	/**
	 * 
	 * <p>
	 * 通过运单编号删除一条记录
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-10-31 下午3:40:57
	 * @param waybillNo
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#deleteByWaybillNo(java.lang.String)
	 */
	@Override
	public int deleteByWaybillNo(String waybillNo) {
		return this.sqlSession.delete(NAMESPACE + "deleteByWaybillNo",
				waybillNo);
	}

	/**
	 * 
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午9:21:30
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insert(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int insert(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		if(waybillPendingEntity.getId()==null || "".equals(waybillPendingEntity.getId())){
			waybillPendingEntity.setId(UUIDUtils.getUUID());
		}
		return this.sqlSession.insert(NAMESPACE + "insert",
				waybillPendingEntity);
	}

	/**
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午10:08:31
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insertSelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int insertSelective(WaybillPendingEntity waybillPendingEntity) {
		// 设置UUID
		if(waybillPendingEntity.getId()==null || "".equals(waybillPendingEntity.getId())){
			waybillPendingEntity.setId(UUIDUtils.getUUID());
		}
		return this.sqlSession.insert(NAMESPACE + "insertSelective",
				waybillPendingEntity);
	}

	/**
	 * 
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午9:24:01
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public WaybillPendingEntity queryByPrimaryKey(String id) {
		return (WaybillPendingEntity) this.sqlSession.selectOne(NAMESPACE
				+ "selectByPrimaryKey", id);
	}

	/**
	 * 批量插入WaybillPendingEntity对象
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-11-3 下午4:45:40
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#insertBatch(java.util.List)
	 */
	@Override
	public int addWaybillPendingEntityBatch(
			List<WaybillPendingEntity> pendingList) {
		return this.sqlSession.insert("insertBatch", pendingList);
	}

	/**
	 * 
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午10:09:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int updateByPrimaryKeySelective(
			WaybillPendingEntity waybillPendingEntity) {
		return this.sqlSession
				.update(NAMESPACE + "updateByPrimaryKeySelective",
						waybillPendingEntity);
	}
	
	/**
	 * 
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午10:09:12
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updateByPrimaryKeySelective(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int updateByWaybillNo(
			WaybillPendingEntity waybillPendingEntity) {
		return this.sqlSession
				.update(NAMESPACE + "updateByWaybillNo",
						waybillPendingEntity);
	}

	/**
	 * 
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-23 上午10:11:35
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updateByPrimaryKey(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity)
	 */
	@Override
	public int updateByPrimaryKey(WaybillPendingEntity waybillPendingEntity) {
		return this.sqlSession.update(NAMESPACE + "updateByPrimaryKey",
				waybillPendingEntity);
	}

	/**
	 * 
	 * <p>
	 * 根据运单号查询
	 * </p>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-26 下午4:03:23
	 * @param number
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryByWaybillNumber(java.lang.String)
	 */
	@Override
	public WaybillPendingEntity queryByWaybillNumber(String number) {
		return (WaybillPendingEntity) this.sqlSession.selectOne(NAMESPACE
				+ "selectByWaybillNumber", number);
	}

	/**
	 * <p>
	 * (查询运单待处理表)
	 * </p>
	 * 
	 * @author 105089-025000-foss-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 */
	@Override
	public List<WaybillPendingEntity> queryPending(
			WaybillPendingDto waybillPendingDto) {
		return this.sqlSession.selectList(NAMESPACE + "getPendings",
				waybillPendingDto);
	}
	
	@Override
	public List<WaybillPendingEntity> queryPendingExpress(
			WaybillPendingDto waybillPendingDto) {
		return this.sqlSession.selectList(NAMESPACE + "getPendingsExpress",
				waybillPendingDto);
	}
	
	
	/**
	 * 与queryPending调用的方法是一样的，只是为了返回不一样的实体类型
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-11 上午10:55:18
	 */
	@Override
	public List<WaybillOfflineEntity> queryOfflinePending(WaybillPendingDto waybillPendingDto) {
		return this.sqlSession.selectList(NAMESPACE + "getPendings", waybillPendingDto);
	}

	/**
	 * <p>
	 * 更改运单状态PENDING
	 * </p>
	 * 
	 * @author 105089-025000-foss-helong
	 * @date 2012-10-16 下午04:22:42
	 * @return
	 * @see
	 */
	@Override
	public int updatePendingActive(String id) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("id", id);
		parms.put("active", FossConstants.INACTIVE);
		return this.sqlSession.update(NAMESPACE + "updatePendingActive", parms);
	}

	/**
	 * 根据运单号更新待处理运单状态为失效
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-22 下午5:04:04
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#updatePendingActiveByNo(java.lang.String)
	 */
	@Override
	public int updatePendingActiveByNo(String waybillNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("active", FossConstants.INACTIVE);
		return this.sqlSession.update(NAMESPACE + "updatePendingActiveByNo",
				parms);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see
	 */
	@Override
	public WaybillPendingEntity queryPendingByNo(String mixNo) {
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", mixNo);
		parms.put("active", FossConstants.ACTIVE);
		return (WaybillPendingEntity) this.sqlSession.selectOne(NAMESPACE
				+ "queryPending", parms);
	}
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在
	 * </p>
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see
	 */
	public WaybillPendingEntity queryPendingByWaybillNoAndOrderNo(String waybillNo, String orderNo){
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("waybillNo", waybillNo);
		parms.put("orderNo", orderNo);
		parms.put("active", FossConstants.ACTIVE);
		return (WaybillPendingEntity) this.sqlSession.selectOne(NAMESPACE
				+ "queryPendingByWaybillNoAndOrderNo", parms);
	}

	/**
	 * 从ｐｅｎｄｉｎｇ中查询PDA接货记录
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-12
	 * @param args
	 * @return
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao#queryPdaReceiveGoodsDto(java.util.Map)
	 */
	@Override
	public List<PdaReceiveGoodsDto> queryPdaReceiveGoodsDto(
			Map<String, Object> args) {
		return this.sqlSession.selectList(
				NAMESPACE + "queryPdaReceiveGoodsDto", args);
	}

	/**
	 * 从运单表中查询ＰＤＡ接货记录
	 * 
	 * @author 025000-foss-helong
	 * @date 2012-12-12
	 * @param args
	 * @return
	 * @@see 
	 *       com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillPendingDao
	 *       #queryPdaWaybillReceiveGoodsDto(java.util.Map)
	 */
	@Override
	public List<PdaReceiveGoodsDto> queryPdaWaybillReceiveGoodsDto(
			Map<String, Object> args) {
		return this.sqlSession.selectList(NAMESPACE
				+ "queryPdaWaybillReceiveGoodsDto", args);
	}

	/**
	 * 从运单表中查询ＰＤＡ接货记录
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-12
	 * @param args
	 * @return
	 */
	@Override
	public Integer countOfflineActiveWayBill() {
		return sqlSession.selectOne(NAMESPACE + "countOffineActiveWaybill");
	}
	/**
	 * 从运单表中查询ＰＤＡ接货记录
	 * 
	 * @author 026113-foss-linwensheng
	 * @date 2012-12-12
	 * @param args
	 * @return
	 */
	@Override
	public Integer countOfflineActiveWayBill(String orgCode) {
		return sqlSession.selectOne(NAMESPACE + "countOffineActiveWaybillByOrgCode", orgCode);
	}
	 
}