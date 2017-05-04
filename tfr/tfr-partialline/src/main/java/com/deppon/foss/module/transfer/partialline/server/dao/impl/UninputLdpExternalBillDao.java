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
 *  DESCRIPTION   : 落地配外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/server/dao/impl/UninputLdpExternalBillDao.java
 * 
 *  FILE NAME     :UninputLdpExternalBillDao.java
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
import com.deppon.foss.module.transfer.partialline.api.server.dao.IUninputLdpExternalBillDao;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.LdpHandoverBillDetailDto;

/**
 * 未录入外发单Dao
 * @author ibm-liuzhaowei
 * @date 2013-07-16 上午9:18:36
 */
public class UninputLdpExternalBillDao extends iBatis3DaoImpl implements IUninputLdpExternalBillDao {

	/**
	 * 前缀
	 */
	private static String prefix = "foss.partialline.uninputLdpExternalBillMapper.";

	/**
	 * 查询未录入落地配外发单列表（查询交接类型为落地配，且未录入外发单的交接单明细）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:08:28
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<LdpHandoverBillDetailDto> queryUninputLdpExternalBill(LdpHandoverBillDetailDto detailDto, int limit, int start) {
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
			// 落地配交接
			detailDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
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
		return this.getSqlSession().selectList(prefix + "queryUninputLdpExternalBill", detailDto, rowBounds);
	}

	/**
	 * 查询未录入落地配外发单列表（查询交接类型为落地配，且未录入外发单的交接单明细）总条数
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 上午11:08:28
	 */
	@Override
	public Long queryUninputLdpExternalBillCount(LdpHandoverBillDetailDto detailDto) {
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
			// 落地配交接
			detailDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
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
	 * 查询未录入落地配外发单模块 根据运单号取最新的交接单明细信息（按交接时间降序查询(1.最新生成的 2.未产生交接单)交接单明细）
	 * 
	 * @author ibm-liuzhaowei
	 * @date 2013-07-17 下午2:59:20
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LdpHandoverBillDetailDto queryLastHandoverBillDetail(LdpHandoverBillDetailDto detailDto) {
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

		// 落地配交接
		detailDto.setHandoverType(PartiallineConstants.HANDOVER_TYPE_LDP_HANDOVER);
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
		List<LdpHandoverBillDetailDto> tempList = this.getSqlSession().selectList(prefix + "queryLastHandoverBillDetial",
				detailDto);
		// 获取最近的交接明细记录
		if (tempList != null && tempList.size() > 0) {
			return tempList.get(0);
		} else {
			return null;
		}
	}

}