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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/creating/client/dao/impl/WaybillDao.java
 * 
 * FILE NAME        	: WaybillDao.java
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

import com.deppon.foss.module.pickup.creating.client.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsCondition;
import com.deppon.foss.module.pickup.waybill.shared.dto.AbandonedGoodsDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AccountingQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.AccountingResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CrmWaybillServiceDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillQueryArgsDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 
 ******************************************* 
 * <b style="font-family:微软雅黑"><small>Description:WayBillDao</small></b> </br>
 * <b style="font-family:微软雅黑"><small>HISTORY</small></b></br> <b
 * style="font-family:微软雅黑"><small> ID DATE PERSON REASON</small></b><br>
 ******************************************** 
 * <div style="font-family:微软雅黑,font-size:70%"> 2012-10-10 sunrui 新增 </div>
 ******************************************** 
 */
public class WaybillDao implements IWaybillDao {

	private static final String NAMESPACE = "foss.pkp.WaybillEntityMapper.";

	
	private SqlSession sqlSession;

	@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	
	/**
	 * 创建运单
	 * 
	 * @param waybill
	 */
	@Override
	public int addWaybillEntity(WaybillEntity waybill) {
		// 设置UUID
		waybill.setId(UUIDUtils.getUUID());

		return this.sqlSession.insert(NAMESPACE + "insert", waybill);
	}

	/**
	 * 修改运单
	 * 
	 * @param waybill
	 */
	@Override
	public int updateWaybill(WaybillEntity waybill) {
		return this.sqlSession.update(
				NAMESPACE + "updateByPrimaryKeySelective", waybill);
	}

	/**
	 * 通过订单编号查询运单
	 * 
	 * @param orderNo
	 */
	@Override
	public WaybillEntity queryWaybillByOrderNo(String orderNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setOrderNo(orderNo);
		argsDto.setActive(FossConstants.YES);

		return (WaybillEntity) this.sqlSession.selectOne(
				NAMESPACE + "selectByOrderNo", argsDto);
	}

	/**
	 * 通过运单编号查询运单
	 * 
	 * @param waybill
	 */
	@Override
	public WaybillEntity queryWaybillByNo(String waybillNo) {
		// 封装查询条件
		WaybillQueryArgsDto argsDto = new WaybillQueryArgsDto();
		argsDto.setWaybillNo(waybillNo);
		argsDto.setActive(FossConstants.YES);

		return (WaybillEntity) this.sqlSession.selectOne(
				NAMESPACE + "selectByWaybillNo", argsDto);
	}

	/**
	 * 通过运单编号集合查询运单
	 * 
	 * @param waybillNoList
	 */
	@Override
	public List<WaybillEntity> queryWaybillByNoList(List<String> waybillNoList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", waybillNoList);
		map.put("active", FossConstants.ACTIVE);
		return this.sqlSession.selectList(
				NAMESPACE + "selectByWaybillNoList", map);
	}

	/**
	 * 通过运单系统编号查询运单
	 * 
	 * @param waybill
	 */
	@Override
	public WaybillEntity queryWaybillById(String id) {
		return (WaybillEntity) this.sqlSession.selectOne(
				NAMESPACE + "selectByPrimaryKey", id);
	}

	/**
	 * 查询最近一次建立的运单
	 * 
	 * @param orgCode
	 */
	@Override
	public WaybillEntity queryLastWaybill(String orgCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active", FossConstants.ACTIVE);
		map.put("orgCode", orgCode);
		List<WaybillEntity> list = this.sqlSession.selectList(
				NAMESPACE + "selectLastWaybill", map);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 更新在线运单打印次数
	 * 
	 * @author foss-jiangfei
	 * @date 2012-11-15 下午11:48:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public int updateWaybillPrintTimes(String waybillNo) {
		WaybillEntity waybillEntity = queryWaybillByNo(waybillNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("printTimes", waybillEntity.getPrintTimes() + 1);
		map.put("waybillNo", waybillNo);
		return this.sqlSession.update(
				NAMESPACE + "updateWaybillPrintTime", map);
	}

	/**
	 * 
	 * <p>
	 * 查询运单<br />
	 * </p>
	 * 
	 * @author suyujun
	 * @version 0.1 2012-11-30
	 * @param waybillId
	 * @return WaybillEntity
	 */
	@Override
	public WaybillEntity getWaybillEntityById(String waybillId) {
		return (WaybillEntity) this.sqlSession.selectOne(
				NAMESPACE + "selectByPrimaryKey", waybillId);
	}

	/**
	 * 删除运单
	 * 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao#deleteWaybillEntityById(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity)
	 */
	@Override
	public int deleteWaybillEntityById(String id) {
		return this.sqlSession
				.delete(NAMESPACE + "deleteByPrimaryKey", id);
	}

	@Override
	public List<AccountingResultDto> queryWaybillInfosByAccount(
			AccountingQueryDto accountingQueryDto) {

		List<AccountingResultDto> accountingResultDtos = this.sqlSession
				.selectList(NAMESPACE + "queryWaybillInfosByAccount",
						accountingQueryDto);

		return accountingResultDtos;
	}
	
	/**
     * 
     * 查询运单
     * @author 105089-FOSS-yangtong
     * @date 2012-11-26 下午08:37:46
	 */
	@Override
	public List<WaybillEntity> queryWaybill(WaybillDto waybillDto) {
		return this.sqlSession.selectList(NAMESPACE + "queryWaybills",waybillDto);
	}
	
	/**
	 * 根据运单号查询运单部分数据（查询付款方式，到付金额，外发代理和币种）
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-13 下午2:14:48
	 */
	@Override
	public WaybillEntity queryPartWaybillByNo(String waybillNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("waybillNo", waybillNo);
		map.put("active", FossConstants.ACTIVE);
		return (WaybillEntity) this.sqlSession.selectOne(NAMESPACE + "queryPartWaybillByNo",map);
	}

	@Override
	public List<AbandonedGoodsDto> queryAbandonedGoodsDtoList(
			AbandonedGoodsCondition condition) {
		return this.sqlSession.selectList(NAMESPACE + "queryAbandonedGoodsDtoList", condition);
	}

	/**
	 * 更新弃货
	 * @author 043260-foss-suyujun
	 * @date 2012-12-17
	 * @param args
	 * @see com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao#updateAbandonedGoods(java.util.Map)
	 */
	@Override
	public void updateAbandonedGoods(Map<String, Object> args) {
		this.sqlSession.update(NAMESPACE+"updateAbandonedGoods", args);
	}

	@Override
	public List<CrmWaybillServiceDto> queryWaybillDetail(
			List<String> waybillNoList) {
		return this.sqlSession.selectList(NAMESPACE+"queryWaybillDetail", waybillNoList);
	}
}