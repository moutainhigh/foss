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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/dao/IDeliverbillDetailDao.java
 * 
 * FILE NAME        	: IDeliverbillDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDetailsDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;

/**
 * 
 * 派送单明细DAO接口
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-24 上午10:03:35
 */
public interface IDeliverbillDetailDao {
	/**
	 * 
	 * 根据ID查找派送单明细
	 * 
	 * @param id
	 *            派送单明细ID
	 * @return 派送单明细
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 下午4:22:17
	 */
	DeliverbillDetailEntity queryById(String id);

	/**
	 * 
	 * 根据查询条件查询派送单明细
	 * 
	 * @param waybillToArrangeDto
	 *            查询条件，查询条件包括运单号和派送单ID
	 * @return 符合条件的派送单明细
	 * @author ibm-wangxiexu
	 * @date 2012-11-13 下午4:13:19
	 */
	DeliverbillDetailEntity queryByCondition(
			WaybillToArrangeDto waybillToArrangeDto);

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 */
//	List<DeliverbillDetailEntity> queryByDeliverbillId(String deliverbillId,
//			int start, int limit);
	
	
	List<DeliverbillDetailEntity> queryByDeliverbillId(Map<Object,Object> map,int start, int limit);
	
	/**
	 * 查询派送单明细
	 * 根据返单类型排序
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	List<DeliverbillDetailEntity> selectByDeliverbillReturn_bill_type_Sort(Map<Object,Object> map,int start, int limit);
	
	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 * update by 231438 2015-04-16 用于快递100轨迹推送 
	 */
	List<DeliverbillDetailEntity> queryByDeliverbillId(String deliverbillId);
	
	List<DeliverbillDetailEntity> queryByDeliverbillId(Map<Object,Object> map);
	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID的查询条件
	 * @return 已生成到达联的派送明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-12-9 下午10:04:39
	 */
	List<DeliverbillDetailEntity> queryArrivesheetListByDeliverbillId(
			DeliverbillDetailDto deliverbillDetailDto);

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表大小
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表大小
	 * 
	 * @return 派送单明细列表大小
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午5:01:01
	 */
	Long queryCountByDeliverbillId(String deliverbillId);

	/**
	 * 
	 * 添加派送单明细
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 若成功，则返回派送单明细；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-28 下午5:30:01
	 */
	DeliverbillDetailEntity add(DeliverbillDetailEntity deliverbillDetail);

	/**
	 * 
	 * 删除派送单明细
	 * 
	 * @param deliverbillDetailId
	 *            派送单明细ID
	 * @return 删除的行数
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 上午11:13:15
	 */
	int delete(String deliverbillDetailId);

	/**
	 * 批量删除派送单明细
	 * 
	 * @param deliverbillDetailIdArray
	 *            派送单明细ID数组
	 * @return 批量删除的行数
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午6:53:51
	 */
	int deleteBatch(String[] deliverbillDetailIdArray);

	/**
	 * 
	 * 更新派送单明细
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 若成功，返回更新后的派送单明细；否则返回null
	 * @author ibm-wangxiexu
	 * @date 2012-10-29 下午2:54:18
	 */
	DeliverbillDetailEntity update(DeliverbillDetailEntity deliverbillDetail);

	/**
	 * 
	 * 查询派送单下指定序号的派送单明细ID
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param serialNo
	 *            派送单明细序号
	 * @return 指定序号的派送单明细ID
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午3:00:19
	 */
	String queryIdBySerialNo(String deliverbillId, Integer serialNo);

	/**
	 * 
	 * 更新序号大于指定派送单明细序号的的序号
	 * 
	 * @param deliverbillDetail
	 *            派送单明细
	 * @return 更新的行数
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 上午10:39:38
	 */
	int updateSerialNos(DeliverbillDetailEntity deliverbillDetail);

	/**
	 * 
	 * 根据查询条件查询待排运单
	 * 
	 * @param waybillToArrangeDto
	 *            查询条件
	 * @author ibm-wangxiexu
	 * @date 2012-11-4 下午4:32:27
	 */
	List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(
			WaybillToArrangeDto waybillToArrangeDto, int start, int limit);
	
	/**
	 * 
	 * 查询在库位中运单
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-13 下午2:11:15
	 */
	List<WaybillToArrangeDto> queryWaybillToArrangeByStoringCondition(
			WaybillToArrangeDto waybillToArrangeDto, int start, int limit);

	/**
	 * 
	 * 根据查询条件查询待排运单数量
	 * 
	 * @param waybillToArrangeDto
	 *            查询条件
	 * @return 满足查询条件的查询待排运单数量
	 * @author ibm-wangxiexu
	 * @date 2012-11-12 上午10:33:08
	 */
	Long queryWaybillToArrangeCountByCondition(
			WaybillToArrangeDto waybillToArrangeDto);
	
	/**
	 * 
	 * 查询库位中数据
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-13 下午1:59:02
	 */
	Long queryWaybillToArrangeCountByStoringCondition(
			WaybillToArrangeDto waybillToArrangeDto);

	/**
	 * 
	 * 根据派送单ID查询最大的派送单明细编号
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 最大的派送单明细编号
	 * @author ibm-wangxiexu
	 * @date 2012-11-17 下午6:15:10
	 */
	Integer queryMaxSerialNoByDeliverbillId(String deliverbillId);

	/**
	 * 根据条件查询派送单明细内 到达联编号
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-11-27 下午5:17:20
	 */
	List<DeliverbillDetailDto> queryDeliverbillDetailBy(DeliverbillDetailDto dto);

	/**
	 * 根据司机工号、车牌号 查询司机送货任务明细
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-12 下午8:56:18
	 */
	List<PdaDeliverTaskDetailsDto> queryDeliverDetailByDriverCode(
			PdaDeliverTaskConditionDto dto);

	/**
	 * 根据司机工号、车牌号 查询司机送货任务ID
	 * 
	 * @author 097972-foss-dengtingting
	 * @date 2012-12-12 下午8:56:18
	 */
	List<String> queryDeliverNoByDriverCode(PdaDeliverTaskConditionDto dto);

	/**
	 * 
	 * 为中转提供的接口DAO，检验运单是否在指定部门安排过派送
	 * 
	 * @param deliverbillDetailDto
	 *            包含运单号，派送部门，派送单状态的查询条件
	 * @return 运单是否在指定部门安排过派送
	 * @author ibm-wangxiexu
	 * @date 2012-12-14 下午4:20:53
	 */
	Long queryWaybillArrangedFlag(DeliverbillDetailDto deliverbillDetailDto);

	/**
	 * 
	 * 根据运单号查询运单派送信息列表，用于运单轨迹查询
	 * 
	 * @param waybillDeliveryDto
	 *            包含运单号的查询条件
	 * @return 运单派送信息列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-8 下午8:21:35
	 */
	List<WaybillDeliveryDto> queryWaybillDeliveryListByWaybillNo(
			WaybillDeliveryDto waybillDeliveryDto);

	/**
	 * 
	 * 查询未通知客户的派送单明细列表
	 * 
	 * @param deliverbillDto
	 *            派送单明细查询条件，包含了派送单ID和通知状态查询条件
	 * @return 未通知客户的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-14 下午1:40:15
	 */
	List<DeliverbillDetailDto> queryUnnotifiedDeliverbillDetailList(
			DeliverbillDetailDto deliverbillDetailDto);

	/**
	 * 
	 * 查询排单件数与到达联件数不一致的派送单明细列表
	 * 
	 * @param deliverbillDetailDto
	 *            包含派送单ID和到达联状态(生成)的查询条件
	 * @return 排单件数与到达联件数不一致的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午12:03:23
	 */
	List<DeliverbillDetailDto> queryArrivesheetQtyInconsistentListByDeliverbillId(
			DeliverbillDetailDto deliverbillDetailDto);

	/**
	 * 
	 * 查询排单件数与库存件数不一致的派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 排单件数与库存件数不一致的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午12:03:23
	 */
	List<DeliverbillDetailDto> queryInStockQtyInconsistentDeliverbillDetailList(
			String deliverbillId);
	/**
	 * 根据到达联编号，派送单状态查询派送单编号
	 * @author foss-meiying
	 * @date 2013-1-30 下午4:12:25
	 * @param dto
	 * @return
	 * @see
	 */
	String queryDeliverNoByArriveSheetNo(DeliverbillDetailDto dto);
	/**
	 * 根据司机、车牌号、派送单状态查询派送单号
	 * @author foss-meiying
	 * @date 2013-3-27 下午5:52:19
	 * @param pdaDeliverTaskConditionDto
	 * @return
	 * @see
	 */
	List<PdaDeliverTaskDto> queryPdaDeliverTaskDtoByCondition(PdaDeliverTaskConditionDto pdaDeliverTaskConditionDto);
	/**
	 * 根据司机、车牌号、派送单状态查询派送交接单明细
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-15 上午11:30:01
	 * @param pdaDeliverHandTaskConditionDto
	 * @return
	 */
	List<PdaDeliverHandTaskDto> queryPdaDeliverHandTaskDtoByCondition(PdaDeliverHandTaskConditionDto pdaDeliverHandTaskConditionDto);
	/**
	 * 根据派送单集合查询派送单明细
	 * @author foss-meiying
	 * @date 2013-5-7 下午5:52:19
	 * @param 
	 * @return
	 * @see
	 */
	List<DeliverbillDetailDto> queryByDeliverbillNos(DeliverbillDto deliverbillDto);
	
	/**
	 * 
	 * 根据查询条件查询待排运单
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-25 下午4:50:56
	 */
	DeliverbillDto queryWaybillToArrangeTotal(WaybillToArrangeDto waybillToArrangeDto);
	
	/**
	 * 
	 *  统计库位中信息
	 * @author 043258-foss-zhaobin
	 * @date 2013-6-25 下午4:50:56
	 */
	DeliverbillDto queryWaybillToStoringArrangeTotal(WaybillToArrangeDto waybillToArrangeDto);
	
	List<DeliverbillDetailEntity> queryByDeliverbillIdForPrint(Map<Object,Object> map,int start, int limit);
	
	/**
	 * 
	 * 根据运单号修改派送单明细
	 * @author 043258-foss-zhaobin
	 * @date 2014-3-20 下午6:30:29
	 */
	void updateDetailByWaybillNo(DeliverbillDetailEntity deliverbillDetail);
	
	/**
	 * 根据运单号查询派送明细
	 * @author 269871-foss-zhuliangzhi
	 * @param waybillId
	 */
	List<DeliverbillDetailEntity> queryByWaybillNo(String waybillId) ;
 
	/**
	 * 根据拖动后更新派送单明细运单的序号
	 * @author 239284
	 * @param detailId
	 * @param serialNo
	 */
	int updateDeliverDetailSeriNoByDrag(String detailId, int serialNo);
}