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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/dao/impl/DeliverbillDao.java
 * 
 * FILE NAME        	: DeliverbillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * 派送单DAO实现
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-18 下午5:26:22
 */
@SuppressWarnings("unchecked")
public class DeliverbillDao extends iBatis3DaoImpl implements IDeliverbillDao {
	// 派送单 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao";

	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillDto
	 *            查询条件
	 * @return 派送单列表
	 * @author ibm-wangxiexu
	 * @date 2012-10-18 下午4:52:00
	 */
	@Override
	public List<DeliverbillDto> queryByCondition(DeliverbillDto deliverbillDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByCondition", deliverbillDto);
	}

	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillDto
	 *            查询条件
	 * @return 派送单列表
	 * @author ibm-wangxiexu
	 * @date 2012-10-18 下午4:52:00
	 */
	@Override
	public List<DeliverbillDto> queryByCondition(DeliverbillDto deliverbillDto,
			int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectByCondition", deliverbillDto, rowBounds);
	}

	/**
	 * 
	 * 根据ID查找派送单
	 * 
	 * @param id
	 *            派送单ID
	 * @return 派送单
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 下午4:22:17
	 */
	@Override
	public DeliverbillEntity queryById(String id) {
		return (DeliverbillEntity) this.getSqlSession().selectOne(
				NAMESPACE + ".selectByPrimaryKey", id);
	}

	/**
	 * 
	 * 添加派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 若成功，则返回派送单；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-28 下午5:48:16
	 */
	@Override
	public DeliverbillEntity add(DeliverbillEntity deliverbill) {
		deliverbill.setId(UUIDUtils.getUUID());
		//添加时间字段，派送单修改时更新该时间字段
		deliverbill.setModifyTime(new Date());
		int result = this.getSqlSession().insert(
				NAMESPACE + ".insertSelective", deliverbill);

		return result > 0 ? deliverbill : null;
	}

	/**
	 * 
	 * 更新派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 若成功，返回更新后的派送单；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午2:54:18
	 */
	@Override
	public DeliverbillEntity update(DeliverbillEntity deliverbill) {
		//添加时间字段，派送单修改时更新该时间字段
		deliverbill.setModifyTime(new Date());
		int result = this.getSqlSession().update(
				NAMESPACE + ".updateByPrimaryKeySelective", deliverbill);

		return result > 0 ? deliverbill : null;
	}

	/**
	 * 
	 * 根据输入条件，查询符合条件的派送单数量
	 * 
	 * @param deliverbillDto
	 *            查询条件
	 * 
	 * @return 符合条件的派送单数量
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午5:01:01
	 */
	@Override
	public Long queryCountByCondition(DeliverbillDto deliverbillDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectCountByCondition", deliverbillDto);
	}

	/**
	 * 
	 * 根据派送单编号查询装车任务
	 * 
	 * @param loadTaskDto
	 *            包含派送单编号和装车任务状态的DTO
	 * @return 装车任务(包括装车任务ID/编号，差异报告ID)
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午6:09:51
	 */
	@Override
	public LoadTaskDto queryLoadTaskByDeliverbillNo(LoadTaskDto loadTaskDto) {
		return (LoadTaskDto) this.getSqlSession().selectOne(
				NAMESPACE + ".selectLoadTaskByDeliverbillNo", loadTaskDto);
	}

	/**
	 * 根据派送单ID更新派送单状态
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-22 下午8:43:16
	 */
	@Override
	public int updateStatusByDeliverbillNo(DeliverbillEntity entity) {
		entity.setOperateTime(new Date());
		//添加时间字段，派送单修改时更新该时间字段		
		entity.setModifyTime(new Date());
		return (Integer) this.getSqlSession().update(
				NAMESPACE + ".updateStatusByDeliverbillNo", entity);
	}

	/**
	 * 
	 * 根据派送单编号/签收状态查询签收票数
	 * 
	 * @param arriveSheetDto
	 *            包含派送单编号和到达联签收状态的查询条件
	 * @return 签收票数
	 * @author ibm-wangxiexu
	 * @date 2012-12-26 下午7:15:43
	 */
	@Override
	public Long querySignCountByArrivesheetDto(ArriveSheetDto arriveSheetDto) {
		return (Long) this.getSqlSession().selectOne(
				NAMESPACE + ".selectSignCountByArrivesheetDto", arriveSheetDto);
	}

	/**
	 * 
	 * 根据派送单编号查找派送单
	 * 
	 * @param deliverbillDto
	 *            包含派送单编号和派送单状态的查询条件
	 * @return 派送单
	 * @author ibm-wangxiexu
	 * @date 2012-12-26 下午9:01:38
	 */
	@Override
	public DeliverbillEntity queryByDeliverbillDto(DeliverbillDto deliverbillDto) {
		return (DeliverbillEntity) this.getSqlSession().selectOne(
				NAMESPACE + ".selectByDeliverbillDto", deliverbillDto);
	}

	/**
	 * 
	 * 根据查询条件(工号/姓名/电话号码)查询公司司机
	 * 
	 * @param driverDto
	 *            查询条件
	 * @return 符合条件的公司司机列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-4 下午9:55:30
	 */
	@Override
	public List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".selectDriverListByDriverDto", driverDto);
	}

	/**
	 * 
	 * 更新派送单
	 * @author 043258-foss-zhaobin
	 * @date 2013-2-2 下午3:35:30
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao#updateDeliverBill(com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity)
	 */
	@Override
	public int updateDeliverBill(DeliverbillEntity deliverbill)
	{
		//添加时间字段，派送单修改时更新该时间字段
		deliverbill.setModifyTime(new Date());
		int result = this.getSqlSession().update(
				NAMESPACE + ".updateByPrimaryKeySelective", deliverbill);
		return result > 0 ? result : 0;
	}

	/**
	 * 
	 * 查询派送单序列
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-8 上午10:57:16
	 */
	@Override
	public String querySequence() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("sequenceName", "DELIVERBILL_SEQ");
		return (String) this.getSqlSession().selectOne(NAMESPACE + ".getNextSequenceValue", params);

	}

	@Override
	public boolean isExistDeliverbill(String waybill) {
		DeliverbillDto deliverbillDto = new DeliverbillDto();
		deliverbillDto.setWaybillNo(waybill);
		deliverbillDto.setStatus(DeliverbillConstants.STATUS_CANCELED);
		return getSqlSession().selectList(NAMESPACE + ".queryisExistDeliverbill",deliverbillDto).size()>0 ? true :false;
	}
	/**
	 * 
	 * 根据运单号查询派送情况
	 * @author foss-meiying
	 * @date 2013-7-2 上午10:57:16
	 */
	public List<DeliverbillDto> queryPartDeliverbillbyWaybill(DeliverbillDto deliverbillDto){
		return getSqlSession().selectList(NAMESPACE + ".selectPartDeliverbillbyWaybill",deliverbillDto);
	}

	 /**
	  * 
	  * 是否存在 已绑定到达联的 派送单明细
	  * @author 043258-foss-zhaobin
	  * @date 2013-7-4 上午8:42:52
	  */
	@Override
	public boolean isExistValidDeliverbill(String deliverbillid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("deliverbillid", deliverbillid);
		params.put("arrivesta", "N/A");
		Long flag = (Long)getSqlSession().selectOne(NAMESPACE + ".queryisExistValidDeliverbill",params);
		return flag>0?true:false;
	}

	 /**
	  * 
	  * 是否存在非取消状态的派送单
	  * @author 043258-foss-zhaobin
	  * @date 2013-8-1 下午5:11:55
	  */
	@Override
	public boolean isNotCancelDeliverbill(String waybillNo) {
		Long flag = (Long)getSqlSession().selectOne(NAMESPACE + ".queryisNotCancelDeliverbill",waybillNo);
		return flag>0?true:false;
	}

	@Override
	public List<DeliverbillDto> queryStayHandoverByPda(DeliverbillDto deliverbillDto) {
		return this.getSqlSession().selectList(
				NAMESPACE + ".queryStayHandoverByPda", deliverbillDto);
	}
	/**
	 * PDA派送签收时根据到达联编号查询返回 派送单号、运单号、总体积、总重量
	 *  @author 159231-foss-meiying
	 * @date 2014-3-8 上午8:37:37
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao#queryDeliverBillByArrivesheetNo(com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto)
	 */
	@Override
	public DeliverbillDto queryDeliverBillByArrivesheetNo(DeliverbillDetailDto deliverbillDto) {
		return (DeliverbillDto)getSqlSession().selectOne(NAMESPACE + ".selectDeliverbillbyArrivesheetNo",deliverbillDto);
	}

	@Override
	public DeliverbillEntity queryDeliverbillDetailEntityByWaybillNo(
			String waybillNo, List<String> statusList) {
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("waybillNo", waybillNo);
		params.put("statusList", statusList);
		List<DeliverbillEntity> list = this.getSqlSession().selectList(NAMESPACE+".queryDeliverbillDetailEntityByWaybillNo", params);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询最新的派送单状态
	 * @author 159231 meiying
	 * 2015-5-6  上午10:27:31
	 * @param deliverbillDto
	 * @return
	 */
	@Override
	public DeliverbillDto queryLastDeliverbillbyWaybill(DeliverbillDto deliverbillDto){
		return (DeliverbillDto)getSqlSession().selectOne(NAMESPACE + ".selectLastDeliverbillbyWaybill",deliverbillDto);
	}
	
	/**
	 * 根据运单号，查询司机信息集合 
	 * @author 302346	DN201606250013
	 * @param waybillNo 运单号
	 * @param status	派送单状态
	 * @return List<DeliverbillEntity>	包含司机编号、司机姓名和车牌号等信息的派送单集合
	 */
	public List<DeliverbillEntity> queryDeliverbillDetailListByWaybillNo(
			String waybillNo, String status) {
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("waybillNo", waybillNo);
		params.put("status", status);
		List<DeliverbillEntity> list = this.getSqlSession().selectList(NAMESPACE+".queryDeliverbillDetailListByWaybillNo", params);
		if(list != null && list.size() > 0){
			return list;
		}
		return null;
	}
}