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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IDeliverbillDao.java
 * 
 * FILE NAME        	: IDeliverbillDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;

/**
 * 
 * 派送单DAO接口
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-18 下午5:21:14
 */
public interface IDeliverbillDao {
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
	List<DeliverbillDto> queryByCondition(DeliverbillDto deliverbillDto);

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
	List<DeliverbillDto> queryByCondition(DeliverbillDto deliverbillDto,
			int start, int limit);

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
	Long queryCountByCondition(DeliverbillDto deliverbillDto);

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
	DeliverbillEntity queryById(String id);

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
	DeliverbillEntity queryByDeliverbillDto(DeliverbillDto deliverbillDto);

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
	DeliverbillEntity add(DeliverbillEntity deliverbill);

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
	DeliverbillEntity update(DeliverbillEntity deliverbill);
	
	/**
	 * 
	 * 更新派送单
	 * @author 043258-foss-zhaobin
	 * @date 2013-2-2 下午3:34:59
	 */
	int updateDeliverBill(DeliverbillEntity deliverbill);

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
	LoadTaskDto queryLoadTaskByDeliverbillNo(LoadTaskDto loadTaskDto);

	/**
	 * 根据派送单ID更新派送单状态
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-22 下午8:43:16
	 */
	int updateStatusByDeliverbillNo(DeliverbillEntity entity);

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
	Long querySignCountByArrivesheetDto(ArriveSheetDto arriveSheetDto);

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
	List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto);
	
	/**
	 * 
	 * 查询派送单序列
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-8 上午10:57:16
	 */
	String querySequence();
	
	/**
	 * 
	 * 是否存在派送单
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-22 
	 *				上午10:58:04
	 * @param deliverbill
	 * @param waybill
	 * @return
	 * @see
	 */
    boolean isExistDeliverbill(String waybill);
    /**
	 * 
	 * 根据运单号查询派送情况
	 * @author foss-meiying
	 * @date 2013-7-2 上午10:57:16
	 */
	 List<DeliverbillDto> queryPartDeliverbillbyWaybill(DeliverbillDto deliverbillDto);
	 
	 /**
	  * 
	  * 是否存在 已绑定到达联的 派送单明细
	  * @author 043258-foss-zhaobin
	  * @date 2013-7-4 上午8:42:52
	  */
	 boolean isExistValidDeliverbill(String deliverbillid);
	 
	 /**
	  * 
	  * 是否存在非取消状态的派送单
	  * @author 043258-foss-zhaobin
	  * @date 2013-8-1 下午5:11:55
	  */
	 boolean isNotCancelDeliverbill(String waybillNo);
	 
	 /**
	  * 
	  * 根据司机工号和车牌号查询待生成的派送单
	  * 
	  * @author 038590-foss-wanghui
	  * @date 2013-8-21 下午3:06:24
	  */
	 List<DeliverbillDto> queryStayHandoverByPda(DeliverbillDto deliverbillDto);
	 /**
	  * PDA派送签收时根据到达联编号查询返回 派送单号、运单号、总体积、总重量
	  *  @author 159231-foss-meiying
	  * @date 2014-3-8 上午8:36:18
	  */
	 DeliverbillDto queryDeliverBillByArrivesheetNo(DeliverbillDetailDto deliverbillDto);

	DeliverbillEntity queryDeliverbillDetailEntityByWaybillNo(
			String waybillNo, List<String> statusList);
	/**
	 * 查询最新的派送单状态
	 * @author 159231 meiying
	 * 2015-5-6  上午10:28:20
	 * @param deliverbillDto
	 * @return
	 */
	DeliverbillDto queryLastDeliverbillbyWaybill(DeliverbillDto deliverbillDto);
	
	/**
	 * 根据运单号，查询司机信息集合 
	 * @author 302346	DN201606250013
	 * @param waybillNo 运单号
	 * @param status	派送单状态
	 * @return List<DeliverbillEntity>	包含司机编号、司机姓名和车牌号等信息的派送单集合
	 */
	public List<DeliverbillEntity> queryDeliverbillDetailListByWaybillNo(
			String waybillNo, String status);
}