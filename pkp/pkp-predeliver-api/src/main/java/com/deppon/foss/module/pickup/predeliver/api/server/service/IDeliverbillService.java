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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IDeliverbillService.java
 * 
 * FILE NAME        	: IDeliverbillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.ArriveSheetEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoaderDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;

/**
 * 
 * 派送排单Service接口
 * 
 * @author ibm-wangxiexu
 * @date 2012-10-18 下午4:48:43
 */ 
public interface IDeliverbillService {
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
	List<DeliverbillDto> queryDeliverbillList(DeliverbillDto deliverbillDto,
			int start, int limit);
	
	/**
	 * 
	 * 根据条件查询派送单
	 * @author 043258-foss-zhaobin
	 * @date 2013-5-4 上午9:15:23
	 */
	InputStream queryDeliverbillList(DeliverbillDto deliverbillDto);

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
	Long queryDeliverbillCountByCondition(DeliverbillDto deliverbillDto);

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 */
	List<DeliverbillDetailEntity> queryDeliverbillDetailList(
			String deliverbillId);

	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 */
	List<DeliverbillDetailEntity> queryDeliverbillDetailList(
			String deliverbillId, int start, int limit);

	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 上午10:05:28
	 */
	List<DeliverbillDetailEntity> queryDeliverbillArrivesheetList(
			String deliverbillId);

	/**
	 * 
	 * 根据派送单ID查找派送单信息
	 * 
	 * @author ibm-wangxiexu
	 * @date 2012-10-24 下午4:55:30
	 */
	DeliverbillEntity queryDeliverbill(String id);

	/**
	 * 从派送单中批量移除派送单明细
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param deliverbillDetailIdArray
	 *            待删除的派送单明细ID数组
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午7:10:25
	 */
	DeliverbillEntity deleteDeliverbillDetails(String deliverbillId,
			String[] deliverbillDetailIdArray);

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
	Long queryDeliverbillDetailCountByDeliverbillId(String deliverbillId);

	/**
	 * 
	 * 提高派送单明细优先级
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param deliverbillDetailId
	 *            派送单明细ID
	 * @param serialNo
	 *            派送单明细序列号
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午2:34:52
	 */
	void upgradeDeliverbillDetail(String deliverbillId,
			String deliverbillDetailId, Integer serialNo);

	/**
	 * 
	 * 降低派送单明细优先级
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param deliverbillDetailId
	 *            派送单明细ID
	 * @param serialNo
	 *            派送单明细序列号
	 * @author ibm-wangxiexu
	 * @date 2012-10-30 下午2:34:52
	 */
	void downgradeDeliverbillDetail(String deliverbillId,
			String deliverbillDetailId, Integer serialNo);

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
	 * 添加运单至派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @param waybillToArrangeDtoList
	 *            添加的运单列表
	 * @exception 派送单异常
	 * @return 添加失败的运单列表
	 * @author ibm-wangxiexu
	 * @date 2012-11-13 下午2:21:41
	 */
	List<WaybillToArrangeDto> addWaybillToArrange(
			DeliverbillEntity deliverbill,
			List<WaybillToArrangeDto> waybillToArrangeDtoList,
			CurrentInfo currentInfo);

	/**
	 * 
	 * 保存(新增/更新)派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 保存后的派送单
	 * @author ibm-wangxiexu
	 * @date 2012-11-22 下午4:56:35
	 */
	DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill);

	/**
	 * 
	 * 根据派送单编号查询装车任务
	 * 
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 装车任务
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午6:08:04
	 */
	LoadTaskDto queryLoadTaskByDeliverbillNo(String deliverbillNo);

	/**
	 * 
	 * 根据装车任务编号查询装车明细
	 * 
	 * @param taskNo
	 *            装车任务编号
	 * @return 装车明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午5:21:46
	 */
	List<LoadTaskDetailDto> queryLoadTaskDetailListByTaskNo(String taskNo);

	/**
	 * 
	 * 根据差异报告ID查询装车差异报告明细
	 * 
	 * @param loadGaprepId
	 *            差异报告ID
	 * @return 装车差异报告明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午5:21:46
	 */
	List<LoadGaprepWaybillDto> queryLoadGaprepWaybillListByRepId(
			String loadGaprepId,String deliverbillNo);

	/**
	 * 
	 * 确认派送装车报告
	 * 
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 若成功，返回大于0；否则返回0
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午5:21:46
	 */
	int comfirmLoadTask(String deliverbillNo);

	/**
	 * 
	 * 退回派送装车报告
	 * 
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 若成功，返回大于0；否则返回0
	 * @author ibm-wangxiexu
	 * @date 2012-11-30 下午5:21:46
	 */
	int rejectLoadTask(String deliverbillNo);

	/**
	 * 
	 * 确认预派送单
	 * 
	 * @param deliverbillDto
	 *            派送单，包含了派送单ID,派送单司机(编码和姓名)信息
	 * @return 若成功，返回大于0；否则返回0
	 * @author ibm-wangxiexu
	 * @date 2012-12-3 上午11:52:10
	 */
	int confirmDeliverbill(DeliverbillDto deliverbillDto,String taskNo);

	/**
	 * 
	 * 取消预派送单
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 若成功，返回大于0；否则返回0
	 * @author ibm-wangxiexu
	 * @date 2012-12-3 上午11:52:10
	 */
	int cancelDeliverbill(String deliverbillId, String deliverbillNo);
	
	/**
	 * 
	 * 取消已保存的派送单
	 * @author 043258-foss-zhaobin
	 * @date 2013-2-2 下午3:30:18
	 */
	int cancelDeliverbillForSaved(String deliverbillId, String deliverbillNo);

	/**
	 * 
	 * 根据派送装车明细ID查询扫描明细列表
	 * 
	 * @param loadDetailId
	 *            装车明细ID
	 * @return 扫描明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-12-4 下午9:20:43
	 */
	List<ScanDetailDto> queryScanDetailListByLoadDetailId(String loadDetailId);

	/**
	 * 
	 * 根据差异报告ID和运单号查询少货明细
	 * 
	 * @param loadGaprepId
	 *            差异报告ID
	 * @param waybillNo
	 *            运单号
	 * @return 少货明细列表
	 * @author ibm-wangxiexu
	 * @date 2012-12-5 下午7:47:49
	 */
	List<LoadGaprepDetailDto> queryLoadGaprepDetailListByRepIdAndWaybillNo(
			String loadGaprepId, String waybillNo);

	/**
	 * 
	 * 根据任务ID查找装车人
	 * 
	 * @param taskId
	 *            装车任务ID
	 * @return 装车人列表
	 * @author ibm-wangxiexu
	 * @date 2012-12-5 下午11:53:24
	 */
	List<LoaderDto> queryLoaderListByTaskId(String taskId);

	/**
	 * 
	 * 根据装车任务ID和派送单号查询历史装车差异报告
	 * 
	 * @param taskId
	 *            装车任务ID
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 历史装车差异报告
	 * @author ibm-wangxiexu
	 * @date 2012-12-6 下午2:28:31
	 */
	List<LoadGaprepDto> queryLoadGaprepListByTaskIdAndDeliverbillNo(
			String taskId, String deliverbillNo);

	/**
	 * 
	 * 为中转提供的接口，检验运单是否在指定部门安排过派送
	 * 
	 * @param waybillNo
	 *            运单号
	 * @param deptCode
	 *            派送部门
	 * @return 运单是否在指定部门安排过派送
	 * @author ibm-wangxiexu
	 * @date 2012-12-14 下午4:20:53
	 */
	boolean queryWaybillArrangedFlag(String waybillNo, String deptCode);

	/**
	 * 
	 * 根据车牌号和司机工号查询PDA已下拉但未生成交接单的派送单列表
	 * @author ibm-wangxiexu
	 * @date 2012-12-22 下午7:45:58
	 */
	List<DeliverbillDto> queryPdaDownloadedDeliverbillList(DeliverbillDto deliverbillDto);
	/**
	 * 
	 * 根据派送单编号查询派送信息（要求派送单必须确认签收）
	 * 
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 派送信息
	 * @author ibm-wangxiexu
	 * @date 2012-12-26 下午7:15:43
	 */
	DeliverbillDto queryDeliveryInfoByDeliverbillNo(String deliverbillNo);

	/**
	 * 
	 * 更新派送单的装车状态
	 * 
	 * @param deliverbillNo
	 *            派送单号
	 * @param status
	 *            派送单状态
	 * @return 更新派送单的条目数
	 * @author ibm-wangxiexu
	 * @date 2012-12-27 下午4:42:20
	 */
	int updateDeliverbillLoadStatus(String deliverbillNo, String status);

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
	 * 分配派送任务
	 * 
	 * @param deliverbillId
	 *            派送单id
	 * @param driver
	 *            分配的司机
	 * @return 分配成功标志。若成功，则返回1；否则不成功
	 * @author ibm-wangxiexu
	 * @date 2013-1-6 上午10:46:16
	 */
	int assignDriver(String deliverbillId, DriverDto driver);

	/**
	 * 
	 * 根据运单号查询运单派送信息列表，用于运单轨迹查询
	 * 
	 * @param waybillNo
	 *            运单号
	 * @return 运单派送信息列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-8 下午8:21:35
	 */
	List<WaybillDeliveryDto> queryWaybillDeliveryListByWaybillNo(
			String waybillNo);
	
	/**
	 * 
	 * 根据运单号查询运单派送信息列表，用于运单轨迹查询
	 * 
	 * @param waybillNo
	 *               运单号
	 * @return 运单派送信息列表
	 * @author 043258-foss-zhaobin
	 * @date 2013-7-3 下午2:21:45
	 */
	List<WaybillDeliveryDto> queryWaybillDeliveryListByWaybillNoForBse(
			String waybillNo);

	/**
	 * 
	 * 根据接送货车辆车牌号查询接送货司机(SUC-447 创建派送单 SR9 1.
	 * 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 2.
	 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 3. 当排班和PDA绑定同时存在时，以PDA绑定为准)
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @return 接送货司机
	 * @author ibm-wangxiexu
	 * @date 2013-1-10 下午9:52:47
	 */
	DriverDto queryDriverByVehicleNo(String vehicleNo);

	/**
	 * 
	 * 查询未通知客户的派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 未通知客户的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-14 下午1:40:15
	 */
	List<DeliverbillDetailDto> queryUnnotifiedDeliverbillDetailList(
			String deliverbillId);

	/**
	 * 
	 * 通知客户
	 * 
	 * @param deliverbillDetailDtoList
	 *            待通知客户的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-16 下午2:27:01
	 */
	void notifyCustomer(List<DeliverbillDetailDto> deliverbillDetailDtoList);
	
	/**
	 * 
	 * 查询排单件数与到达联件数不一致的派送单明细列表
	 * @param deliverbillId 派送单ID
	 * @return 排单件数与到达联件数不一致的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午12:03:23
	 */
	List<DeliverbillDetailDto> queryArrivesheetQtyInconsistentDeliverbillDetailList(String deliverbillId);
	
	/**
	 * 
	 * 查询排单件数与库存件数不一致的派送单明细列表
	 * @param deliverbillId 派送单ID
	 * @return 排单件数与库存件数不一致的派送单明细列表
	 * @author ibm-wangxiexu
	 * @date 2013-1-17 下午12:03:23
	 */
	List<DeliverbillDetailDto> queryInStockQtyInconsistentDeliverbillDetailList(String deliverbillId);

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
	 * 
	 * 查询派送单序列
	 * @author 043258-foss-zhaobin
	 * @date 2013-3-8 上午10:57:16
	 */
	String querySequence();
	
	/**
	 * 
	 * 运单排单
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-22 
	 *				上午10:58:04
	 * @param deliverbill
	 * @param waybill
	 * @return
	 * @see
	 */
	WaybillToArrangeDto arrangeWaybill(DeliverbillEntity deliverbill, WaybillToArrangeDto waybill);
	/**
	 * 
	 * 根据条件查询派送单明细
	 * @author 043258-
	 *				foss-zhaobin
	 * @date 2013-4-22 
	 *				上午10:58:04
	 * @param deliverbill
	 * @param waybill
	 * @return
	 * @see
	 */
    InputStream queryDeliverbillDetailLists(DeliverbillDto deliverbillDto);
    
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
     * 查询排单统计信息
     * @author 043258-foss-zhaobin
     * @date 2013-6-25 下午4:31:52
     */
    DeliverbillDto queryDeliverbillTotal(WaybillToArrangeDto waybillToArrangeDto);    
    /**
	 * 
	 * 根据运单号查询派送情况
	 * @author foss-meiying
	 * @date 2013-7-2 上午10:57:16
	 */
    List<DeliverbillDto> queryPartDeliverbillbyWaybill(DeliverbillDto deliverbillDto);
    
    /**
     * 
     * 打印派送单调用的查询派送单明细列表接口
     * @author ibm-wangxiexu
     * @date 2013-7-11 下午10:52:24
     */
    List<DeliverbillDetailEntity> queryDeliverbillDetailListForPrint(String deliverbillId, int start, int limit);
    
	/**
	 * 
	 * 更新可排单件数
	 * @author 043258-foss-zhaobin
	 * @date 2013-8-1 下午5:04:19
	 */
	void updateWaybillToArrangeCountByCondition(WaybillToArrangeDto waybillToArrangeDto);
	/**
	 * 
	 *传入运单号或者运单号数组集合，判断传入的运单号是否存在未处理的更改单
	 *再判断该运单集合中是否有付款方式为网上支付未支付完成的。
	 * @author meiying
	 * @date 2013-10-26 下午4:18:59
	 */
	 DeliverbillVo checkWaybillNos(List<String> waybillNos);
	 
	 /**
	  * 
	  * 更新派送明细
	  * @author 043258-foss-zhaobin
	  * @date 2014-3-11 下午3:03:39
	  */
	 void updateDeliverbillDetail(DeliverbillDetailEntity deliverbillDetailEntity);
	/**
	 * 校验传入的整车运单是否做配载和到达
	 *  @author 159231-foss-meiying
	 * @date 2014-5-6 下午7:32:40
	 */
	 void checkWayBillNosWVH(List<String> waybillNos);
	DeliverbillEntity queryDeliverbillDetailEntityByWaybillNo(
			String waybillNo, List<String> statusList);

	/**
	 * 根据拖动后更新派送单明细运单的序号
	 * @author 239284
	 * @param ids 派送单明细id集合
	 */
	int updateDeliverDetailSeriNoByDrag(List<String> ids);
	
	/**
	 * 根据运单号，查询司机信息集合 
	 * @author 302346	DN201606250013
	 * @param waybillNo 运单号
	 * @param status	派送单状态
	 * @return List<DeliverbillEntity>	包含司机编号、司机姓名和车牌号等信息的派送单集合
	 */
	public List<DeliverbillEntity> queryDeliverbillDetailListByWaybillNo(
			String waybillNo, String status);
	
	/**
	 * 根据运单号，查询送货中的司机信息
	 * @author 302346	DN201606250013
	 * @param ArriveSheetEntity 到达联实体
	 * @return DeliverbillDto	派送单实体
	 * 如果没有送货中的有效地到达联，返回null；否则返回包含司机编号、司机姓名和车牌号等信息的派送单集合DTO。
	 */
	public List<DeliverbillDto> queryDriverByWaybillNo(ArriveSheetEntity entity);
	

}