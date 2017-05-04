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
 *  PROJECT NAME  : tfr-load-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IOutsideVehicleChargeDao.java
 *  
 *  FILE NAME          :IOutsideVehicleChargeDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.TruckTaskDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeLogDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.TruckTaskBillInfoDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;

/**
 * 外请车费用调整 dao
 * 
 * @author dp-liming
 * @date 2012-11-7 下午 15:16:57
 */
public interface IOutsideVehicleChargeDao {
	/**
	 * 查询外请车费用调整信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:45:32
	 * */
	 List<AdjustOutVehicleFeeEntity> queryOutsideVehicleChargeList(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto, int limit, int start);

	/**
	 * 查询外请车费用调整 总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:36:52
	 * */
	 Long queryCount(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto);

	/**
	 * 保存外请车费用调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	 int addOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);
	 
	 /**
	   * 保存外请车费用调整记录
	   * 
	   * @author foss-中转-lln
	   * @date 2015-07-13 下午 15:40:34
	*/
	 int addOutsideVehicleUpdateLog(AdjustOutVehicleFeeLogEntity adjustOutVehicleFeeLogEntity);	
	 
	 /**
	   * 查询外请车费用调整修改日志
	   * 
	   * @author foss-中转-lln
	   * @date 2015-07-13 下午 6:40:34
	*/
	 List<AdjustOutVehicleFeeLogEntity> queryOutsideVehicleUpdateLogs(AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto, int limit,int start);
	 
	 /**
	   * 未审核记录条数以及审批中记录条数的统计
	   * 
	   * @author foss-中转-lln
	   * @date 2015-07-16 下午 6:40:34
	*/
	 List<AdjustOutVehicleFeeEntity> noAuditAndAuditInCount(AdjustOutVehicleFeeDto assignLoadTaskDto);
	
	 /**
	   * 查询外请车费用调整修改日志总条数
	   * 
	   * @author foss-中转-lln
	   * @date 2015-07-13 下午 6:40:34
	*/
	 Long queryOutsideVehicleUpdateLogsTotalCount(AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto);
	
	 /**
	 * 更新外请车费用调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:46:12
	 */
	 int updateOutsideVehicleCharge(AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);

	/**
	 * 返回外请车费用调整页面数据
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:49:59
	 */
	 AdjustOutVehicleFeeEntity getOutsideVehicleChargeById(String id);
	
	/**
	 * 保存外请车费用调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	 int deleteOutsideVehicleCharge(String  id);
	
	/**
	 * 根据配载车次号找配载单信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	AdjustOutVehicleFeeDto queryByVehicleassembleNo(String vehicleassembleNo);
	
	/**
	 * 根据id找外请车信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	AdjustOutVehicleFeeDto queryById(String id);
	
	/**
	 * 根据vehicleassembleNo查找配载单信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeBy(String vehicleassembleNo);
	
	
	/**
	 * 根据vehicleassembleNo查找调整费用金额
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeByVehicleassembleNo(String vehicleassembleNo);

	/**
	 * 根据单号查询可结算的记录数量
	 * 
	 * @param waybillNo
	 * @author liangfuxiang
	 * @date 2013-03-15 
	 */
	Long getBillPayableCountByWaybillNo(String waybillNo);

	/**
	 * 根据配载单查询是否存在费用调整
	 * @param waybillNo
	 * @author liangfuxiang
	 * @date  2013-03-15 
	 * @return
	 */
	Long getAdjustOutVehicleFeeRecordCount(String waybillNo);

	/** 
	 * @Title: checkOutsideVehicleCharge 
	 * @Description: 审核调整外请车费用 
	 * 只更新审核状态, 审核意见
	 * @param adjustOutVehicleFeeEntity
	 * @return    
	 * @return int    返回类型 
	 * checkOutsideVehicleCharge
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-17下午3:16:13
	 */ 
	int checkOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);

	/** 
	* @Title: queryOutVehicleAssembleBillAndFeeVoList 
	* @Description: 提供一个接口给结算:根据传入配载车次号集合查询出对应的配载单信息及调整金额信息。
	* @param vehicleAssembleNoList
	* @return  设定文件 
	* @return List<OutVehicleAssembleBillAndFeeVo>    返回类型 
	* @see queryOutVehicleAssembleBillAndFeeVoList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-19 下午2:24:48   
	* @throws 
	*/ 
	List<OutVehicleAssembleBillAndFeeVo> queryOutVehicleAssembleBillAndFeeVoList(List<String> vehicleAssembleNoList);

	/**
	 * 当为不可结算时，查询出发部门和达到部门用于抛出异常
	 * @author foss-liuxue(for IBM)
	 * @date 2013-6-14 下午4:22:10
	 */
	List<AdjustOutVehicleFeeDto> getUnValidationDept(String waybillNo);

	/**
	 * 整车是否已到达
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-5 下午4:35:42
	 */
	Long queryIsArriveCount(String waybillNo);

	/**
	 * 根据车辆任务明细ID查询该车辆下单据信息, 只找配载单
	 * @author 163580
	 * @date 2013-12-6 上午11:02:54
	 * @param truckTaskDetailId
	 * @return
	 * @see
	 */
	List<TruckTaskBillInfoDto> queryTruckTaskBillInfoByTruckTaskDetailId(
			String truckTaskDetailId);

	/**
	 * //根据主任务ID获取子任务(根据到达时间排序, 取最早到达的那一条数据)
	 * @author 163580
	 * @date 2013-12-12 上午9:27:16
	 * @param truckTaskId
	 * @return
	 * @see
	 */
	TruckTaskDetailEntity getFirstTruckTaskDetailById(String truckTaskId);

	/**
	 * 根据配载单号查询有时效协议的数据
	 * @author 163580
	 * @date 2014-1-3 上午11:16:42
	 * @param vehicleassembleNo
	 * @return
	 * @see
	 */
	List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeCauseByVehicleassembleNo(
			String vehicleassembleNo);

	
	/**
	* @description 查询快递长途外请交接单信息
	* @param vehicleassembleNo
	* @return
	* @version 1.0
	* @author 283250-foss-liuyi
	* @update 2016年6月3日 下午4:22:55
	*/
	AdjustOutVehicleFeeDto queryByVehicleassembleNoFromWk(
			String vehicleassembleNo);

	AdjustOutVehicleFeeDto queryByIdFromWk(String id);

	List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeByFromWk(
			String vehicleassembleNo);

	List<TruckTaskBillInfoDto> queryTruckTaskBillInfoByTruckTaskDetailIdFromWkBill(
			String truckTaskDetailId);

}