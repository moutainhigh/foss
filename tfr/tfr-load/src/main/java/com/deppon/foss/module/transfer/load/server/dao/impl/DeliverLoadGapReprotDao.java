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
 *  PROJECT NAME  : tfr-load
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/server/dao/impl/DeliverLoadGapReprotDao.java
 *  
 *  FILE NAME          :DeliverLoadGapReprotDao.java
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
 * PROJECT NAME: tfr-load
 * PACKAGE NAME: com.deppon.foss.module.transfer.load.server.dao.impl
 * FILE    NAME: DeliverLoadGapReprotDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportSerialEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.DiffReportReturnNumEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.QueryDiffReportByWayBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeliverLoadGapReportWayBillDto;


/**
 * DeliverLoadGapReprotDao
 * @author dp-duyi
 * @date 2012-10-25 下午3:38:28
 */
@SuppressWarnings("unchecked")
public class DeliverLoadGapReprotDao extends iBatis3DaoImpl implements IDeliverLoadGapReportDao{
	private static final String NAMESPACE = "tfr-load.";
	/** 
	 * 生成派送装车差异报告
	 * @author 042795-foss-duyi
	 * @date 2012-10-25 下午3:39:36
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#createDeliverLoadGapReort()
	 */
	@Override
	public void createDeliverLoadGapReort() {
		this.getSqlSession().selectOne(NAMESPACE+"selectByProcCreateLoadGapRep");
		  
	}

	/** 
	 * 查询派送装车差异报告
	 * @author dp-duyi
	 * @date 2012-10-26 下午3:22:36
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryDeliverLoadGapReport(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity, java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<DeliverLoadGapReportEntity> queryDeliverLoadGapReport(Map<String,Object> condition, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverLoadGapReport", condition, rowBounds);
	}

	/** 
	 * 查询派送装车差异报告条数
	 * @author dp-duyi
	 * @date 2012-10-29 上午8:18:35
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryDeliverLoadGapReportCount(java.util.Map)
	 */
	@Override
	public Long queryDeliverLoadGapReportCount(
			Map<String,Object> condition) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE+"queryDeliverLoadGapReportCount", condition);
	}

	/** 
	 * 查询运单明细
	 * @author dp-duyi
	 * @date 2012-10-29 上午11:48:10
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryDeliverLoadGapReportWayBills(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity)
	 */
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBills(
			DeliverLoadGapReportEntity report) {
		 return this.getSqlSession().selectList(NAMESPACE+"queryDeliverLoadGapReportWayBills", report);
	}

	/** 
	 * 查询流水号明细
	 * @author dp-duyi
	 * @date 2012-10-29 上午11:48:10
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryDeliverLoadGapReportSerials(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportWayBillEntity)
	 */
	@Override
	public List<DeliverLoadGapReportSerialEntity> queryDeliverLoadGapReportSerials(
			DeliverLoadGapReportWayBillEntity reportWayBill) {
		return null;
	}

	/** 
	 * 修改派送装车差异报告状态
	 * @author dp-duyi
	 * @date 2012-10-30 上午11:13:26
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#updateDeliverLoadGapReportState(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity)
	 */
	@Override
	public int updateDeliverLoadGapReportState(DeliverLoadGapReportEntity report) {
		return this.getSqlSession().update(NAMESPACE+"updateReportState", report);
	}
	/** 
	 * 根据派送单号、装车任务id查询全部卸车差异报告（有效，无效）
	 * @author dp-duyi
	 * @date 2012-12-6 上午9:44:16
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadTaskDao#queryDeliverLoadGapRep(java.util.Map)
	 */
	@Override
	public List<DeliverLoadGapReportEntity> queryAllDeliverLoadGapRepByDeliverNo(
			Map<String, String> condition) {
		return this.getSqlSession().selectList(NAMESPACE+"queryAllDeliverLoadGapRepByDeliverNo", condition);
	}

	/** 
	 * @author dp-duyi
	 * @date 2013-6-24 上午9:46:38
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryDeliverLoadGapReportWayBillsById(com.deppon.foss.module.transfer.load.api.shared.domain.DeliverLoadGapReportEntity)
	 */
	@Override
	public List<DeliverLoadGapReportWayBillEntity> queryDeliverLoadGapReportWayBillsById(
			DeliverLoadGapReportEntity report) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDeliverLoadGapReportWayBillsById", report);
	}

	/** 
	 * 根据车队编码查询外场
	 * @author dp-duyi
	 * @date 2013-7-2 下午2:27:36
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryTransferCenterByMotorcade(java.lang.String)
	 */
	@Override
	public List<String> queryTransferCenterByMotorcade(String motorCadeCode) {
		return this.getSqlSession().selectList(NAMESPACE+"queryTransferCenterByMotorcade", motorCadeCode);
	}

	/** 
	 * 导出派送装车差异报告明细
	 * @author dp-duyi
	 * @date 2013-7-12 下午1:43:14
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IDeliverLoadGapReportDao#queryExportDeliverLoadGapDetail(java.lang.String)
	 */
	@Override
	public List<DeliverLoadGapReportWayBillDto> queryExportDeliverLoadGapDetail(
			String taskNo) {
		return  this.getSqlSession().selectList(NAMESPACE+"queryExportDeliverLoadGapDetail", taskNo);
	}
	@Override
	//查询派送装车运单退回件数
	public Integer querySumWaybillReturn(String taskNo) {
		return  (Integer)this.getSqlSession().selectOne(NAMESPACE+"querySumWaybillReturn", taskNo);
	}
	
	/**
	 * @author 269701--lln
	 * @date 2015-11-11上午10:39:43
	 * @function 根据运单查派送装车差异报告，显示出其中运单差异报告类型为“少货”、“退回”和预计装车件数的记录
	 * @param waybillNo
	 * @return 预计装车件数、 差异报告类型
	 */
	@Override
	public List<QueryDiffReportByWayBillEntity> queryDiffReportByWayBill(String waybillNo) {
		return this.getSqlSession().selectList(NAMESPACE+"queryDiffReportByWayBill",waybillNo);
	}
	
	/**
	 * @author 283250--liuyi
	 * @date 2016-02-25上午10:39:43
	 * @function 根据运单号以及交单开始时间，查询装车“少货”、“退回”次数
	 * @param waybillNo 运单号  surrenderTime 交单开始时间
	 * @return DiffReportReturnNumEntity “少货”、“退回”次数
	 */
	@Override
	public DiffReportReturnNumEntity queryDiffReportReturnNum(Map<String,Object> queryEntity) {
		return (DiffReportReturnNumEntity)this.getSqlSession().selectOne(NAMESPACE+"queryDiffReportReturnNum",queryEntity);
	}
}