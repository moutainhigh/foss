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
 *  
 *  PROJECT NAME  : tfr-partialline
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/dao/impl/ExternalBillDao.java
 * 
 *  FILE NAME     :ExternalBillDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExternalBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillWayBillInfoDto;

/**
 * 偏线Dao
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:16:57
 */
public class ExternalBillDao extends iBatis3DaoImpl implements IExternalBillDao {

	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.ExternalBillMapper.";
 
	/**
	 * 查询偏线列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:02:27
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#selectByParams(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> selectByParams(ExternalBillDto dto, int limit, int start) {
		// 运单可用
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_Y);
		// 分页条件
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(prefix + "selectByExternalBill", dto, rowBounds);
	}

	/**
	 * 插入偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午11:52:20
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#insert(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public int insert(ExternalBillDto record) {
		// 插入
		this.getSqlSession().insert(prefix + "insert", record);
		return 0;
	}

	/**
	 * 根据主键查询外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午11:52:20
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#selectByPrimaryKey(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExternalBillEntity queryByPrimaryKey(String id) {
		// 执行查询
		List<ExternalBillEntity> tmpLit = this.getSqlSession().selectList(prefix + "selectByPrimaryKey", id);
		if (tmpLit != null && tmpLit.size() > 0) {
			return tmpLit.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 更新偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午11:52:20
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#updateByPrimaryKey(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public int updateByPrimaryKey(ExternalBillDto record) {
		// 更新
		this.getSqlSession().update(prefix + "updateExternalBill", record);
		return PartiallineConstants.SERVICE_RESULT_INT_VALUE;
	}

	/**
	 * 根据参数查询偏线外发单的条数，用于分页查询
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 下午3:04:11
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#getCount(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public Long queryCount(ExternalBillDto dto) {
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "getCount", dto);
	}

	/**
	 * 查询已交接但未做录入的运单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-16 上午11:31:50
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryHandedUninputWaybill(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public Long queryHandedUninputWaybill(ExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
		dto.setList(availableStatus);
		// 偏线
		dto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
		// 运单状态
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_Y);
		// 非已预配、非作废状态
		List<Integer> billStatuslist = new ArrayList<Integer>();
		// 20：已交接，
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_FORMAL_HANDOVER);
		// (集配交接单专属状态，21：已配载)
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ASSEMBLE);
		// 30：已出发，
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_DEPART);
		// 40：已到达，
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_ARRIVE);
		// 50：已入库
		billStatuslist.add(LoadConstants.HANDOVERBILL_STATE_ALREADY_INSTOCK);

		dto.setBillStatuslist(billStatuslist);
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "selectHandedUninputWaybill", dto);
	}

	/**
	 * 根据对应的SQL和参数查询总的结果数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-16 下午2:59:29
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryCount(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto,
	 *      java.lang.String)
	 */
	@Override
	public Long queryCount(ExternalBillDto dto, String mapperSqlId) {
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + mapperSqlId, dto);
	}

	/**
	 * 根据ID 更新审核状态
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 下午12:18:47
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#updateAuditStatusByPrimaryKey(java.util.List,
	 *      java.lang.String)
	 */
	@Override
	public void updateAuditStatusByPrimaryKey(List<String> auditIds, String auditStatus) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		// 状态
		parameter.put("auditStatus", auditStatus);
		// 更改列表
		parameter.put("list", auditIds);
		// 执行更新
		this.getSqlSession().update(prefix + "updateAuditStatusByPrimaryKey", parameter);
	}

	/**
	 * 根据运单号查询，被录入的外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-23 上午11:36:42
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryByWaybillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> queryByWaybillNo(ExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);

		dto.setList(availableStatus);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "queryByWaybillNo", dto);
	}

	/**
	 * 根据外发单号及偏线代理编号 查询,非作废 的外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-23 下午1:48:51
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryByExternalBillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> queryByExternalBillNo(ExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);

		dto.setList(availableStatus);
		// 执行查询
		return this.getSqlSession().selectList(prefix + "queryByExternalBillNo", dto);
	}

	/**
	 * 根据运单号查询,特定时间之后的被录入的外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-24 上午10:26:10
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryByWaybillNoAndRegisterTime(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public Long queryByWaybillNoAndRegisterTime(ExternalBillDto dto) {
		// 可用状态
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);

		dto.setList(availableStatus);
		// 执行查询
		return (Long) this.getSqlSession().selectOne(prefix + "queryByWaybillNoAndRegisterTime", dto);
	}

	/**
	 * 根据主键列表查询偏线外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-25 下午3:09:14
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#selectExternalBillByPrimaryKeys(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> selectExternalBillByPrimaryKeys(List<String> externalBillIds) {
		// 执行查询
		return this.getSqlSession().selectList(prefix + "selectExternalBillByPrimaryKeys", externalBillIds);

	}

	/**
	 * 根据运单号查询运单信息
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-25 下午5:26:49
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#selectWayBillByWaybillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ExternalBillWayBillInfoDto selectWayBillByWaybillNo(ExternalBillDto dto) {
		// 查询当前有效的运单
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_CHAR_VALUE);
		// 执行查询
		List<ExternalBillWayBillInfoDto> tempList = this.getSqlSession().selectList(
				prefix + "selectWayBillByWaybillNo", dto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * 根据运单号查询偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-12 下午3:42:45
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryExternalBillByWaybillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public ExternalBillDto queryExternalBillByWaybillNo(ExternalBillDto tempDto) {
		// 已审核
		tempDto.setAuditStatus(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 非中转外发
		tempDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		@SuppressWarnings("unchecked")
		// 执行查询
		List<ExternalBillDto> tempList = this.getSqlSession().selectList(prefix + "queryExternalBillByWaybillNo",
				tempDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据运单号查询偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-12-12 下午3:42:45
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryExternalBillByWaybillNo(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@Override
	public ExternalBillDto queryExternalBillByWaybillNoForChange(ExternalBillDto tempDto) {
		// 状态列表
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
		tempDto.setList(availableStatus);
		// 非中转外发
		tempDto.setTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);
		@SuppressWarnings("unchecked")
		// 执行查询
		List<ExternalBillDto> tempList = this.getSqlSession().selectList(
				prefix + "queryExternalBillByWaybillNoForChange", tempDto);
		if (CollectionUtils.isNotEmpty(tempList)) {
			return tempList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 提供给结算的接口，校验是否存在外发单
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-9 上午11:27:24
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> validateIsExistExternalBill(String waybillNo){
		return this.getSqlSession().selectList(prefix + "validateIsExistExternalBill", waybillNo);
	}

	/**
	 * @author niuly
	 * @date 2014-3-3下午3:59:14
	 * @function 根据运单号list查询有效的外发单信息
	 * @param waybillNoList
	 * @return List<ExternalBillDto>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> queryExternalBillByWaybillNos(List<String> waybillNoList) {
		return this.getSqlSession().selectList(prefix + "queryExternalBillByWaybillNos", waybillNoList);
	}

	/**
	 * @author zwd 200968
	 * @date 2015-04-24 
	 * @function 根据运单号查询偏线外发信息
	 * @param dto
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto) {
		// 运单可用
		dto.setActive(PartiallineConstants.WAY_BILL_ACTIVE_Y);
		return this.getSqlSession().selectList(prefix + "selectExternalByWayBillNo", dto);
	}

}