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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/dao/impl/UninputPartiallineDao.java
 * 
 *  FILE NAME     :UninputPartiallineDao.java
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
/*
 * PROJECT NAME: tfr-partialline
 * PACKAGE NAME: com.deppon.foss.module.transfer.partialline.server.dao.impl
 * FILE    NAME: UninputPartiallineDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.transfer.partialline.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputPartiallineDao;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto;

/**
 * 未录入外发单Dao
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-22 下午6:47:35
 */
public class UninputPartiallineDao extends iBatis3DaoImpl implements IUninputPartiallineDao {

	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.UninputedPartillineMapper.";

	/**
	 * 查询未录入偏线外发单列表（查询交接类型为偏线，且未录入外发单的交接单明细）
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 上午11:08:28
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryUninputedpartial(com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto,
	 *      int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HandoverBillDetailDto> queryUninputedpartial(HandoverBillDetailDto detailDto, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		// 设置基础查询条件
		if (detailDto != null) {
			// 可用状态
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			detailDto.setList(availableStatus);
			// 偏线交接
			detailDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
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

			detailDto.setBillStatuslist(billStatuslist);
		}
		// 查询
		return this.getSqlSession()
				.selectList(prefix + "queryHandedUninputedHandoverbillDetails", detailDto, rowBounds);
	}

	/**
	 * 查询未录入偏线外发单列表（查询交接类型为偏线，且未录入外发单的交接单明细）总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 上午11:08:28
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryUninputedpartialCount(com.deppon.foss.module.transfer.partialline.api.shared.dto.HandoverBillDetailDto)
	 */
	@Override
	public Long queryUninputedpartialCount(HandoverBillDetailDto detailDto) {
		// 设置基础查询条件
		if (detailDto != null) {
			// 可用状态
			List<String> availableStatus = new ArrayList<String>();
			// 待审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
			// 已审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
			// 反审核
			availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
			detailDto.setList(availableStatus);
			// 偏线交接
			detailDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
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

			detailDto.setBillStatuslist(billStatuslist);
		}
		// 查询
		return (Long) this.getSqlSession().selectOne(prefix + "queryHandedUninputedHandoverbillCount", detailDto);
	}

	/**
	 * 查询未录入偏线外发单模块 根据运单号取最新的交接单明细信息（按交接时间降序查询(1.最新生成的 2.未产生交接单)交接单明细）
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-22 下午2:59:20
	 * @see com.deppon.foss.module.transfer.partialline.api.server.dao.IExternalBillDao#queryLastHandoverBillDetial(com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HandoverBillDetailDto queryLastHandoverBillDetial(HandoverBillDetailDto detailDto) {
		// 按交接时间降序查询最新生成的交接单明细
		List<String> availableStatus = new ArrayList<String>();
		// 待审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT);
		// 已审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED);
		// 反审核
		availableStatus.add(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT);
		detailDto.setList(availableStatus);
		// 非中转外发
		detailDto.setIsTransferExternal(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO);

		// 偏线交接
		detailDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_PARTIALLINE_HANDOVER);
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

		detailDto.setBillStatuslist(billStatuslist);
		// 执行查询
		List<HandoverBillDetailDto> tempList = this.getSqlSession().selectList(prefix + "queryLastHandoverBillDetial",
				detailDto);
		// 获取最近的交接明细记录
		if (tempList != null && tempList.size() > 0) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

}