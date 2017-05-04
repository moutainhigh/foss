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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IOutsideVehicleChargeService.java
 *  
 *  FILE NAME          :IOutsideVehicleChargeService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.AdjustOutVehicleFeeLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.AdjustOutVehicleFeeLogDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;

/**
 * 外请车费用调整 service
 * 
 * @author dp-liming
 * @date 2012-11-19 上午10:30:52
 */
public interface IOutsideVehicleChargeService extends IService {
	/**
	 * 查询外请车费用调整信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 上午10:45:32
	 * */
	List<AdjustOutVehicleFeeEntity> queryOutsideVehicleChargeList(
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto, int limit, int start);

	/**
	 * 查询外请车费用调整 总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 上午10:36:52
	 * */
	Long queryCount(AdjustOutVehicleFeeDto adjustOutVehicleFeeDto);

	/**
	 * 保存外请车费用调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 上午10:40:34
	 */
	int addOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);

	/**
	 * 更新外请车费用调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 上午10:46:12
	 */
	int updateOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);

	/**
	 * 返回外请车费用调整页面数据
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 上午10:49:59
	 */
	AdjustOutVehicleFeeEntity getOutsideVehicleChargeById(String id);

	/**
	 * 删除外请车费用调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 上午10:50:12
	 */
	int deleteOutsideVehicleCharge(String id);

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
	 * 审核外请车信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 16:40:34
	 */
	int auditOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);

	/**
	 * 审批外请车信息
	 * 
	 * @author foss-lln
	 * @date 2015-07-09 下午 16:40:34
	 */
	int approvalOutsideVehicleCharge(
			AdjustOutVehicleFeeEntity adjustOutVehicleFeeEntity);

	/**
	 * 外请车费用调整审核后,调整费用
	 * 
	 * @param vehicleassembleNo
	 * @param isAdjust
	 * @return
	 */
	int adjustOutVehicleFee(AdjustOutVehicleFeeDto dto);

	/**
	 * 效验该车辆能否调整
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 16:40:34
	 */
	Boolean queryBillPayableIsWriteOff(String vehicleassembleNo, String isAdjust);

	/**
	 *奖罚范围效验
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午16:40:34
	 * 
	 */
	List<ConfigurationParamsEntity>  queryConfigurationParamsExactByEntity( int limit, int start);
	
	/**
	 * 根据vehicleassembleNo查找调整费用金额
	 * 
	 * @author dp-liming
	 * @date 2012-11-19 下午 15:40:34
	 */
	List<AdjustOutVehicleFeeDto> queryOutsideVehicleChargeByVehicleassembleNo(String vehicleassembleNo);
	
	/**
	 * 提供一个接口给结算，判断相关配载单对应的车辆是否卸车，没有费用调整或者调整完毕,以此作为能否结算的依据
	 * 
	 * @author liangfuxiang
	 * @date 2013-03-14
	 */
	Boolean isBillPayable(String waybillNo) throws TfrBusinessException;
	
	/**
	* @Title: queryOutVehicleAssembleBillAndFeeVoList 
	* @Description: 提供一个接口给结算:根据传入配载车次号集合查询出对应的配载单信息及调整金额信息。
	* @param vehicleAssembleNoList
	* @return
	* @throws TfrBusinessException  设定文件 
	* @return List<OutVehicleAssembleBillAndFeeVo>    返回类型 
	* @see queryOutVehicleAssembleBillAndFeeVoList
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-19 下午2:17:55   
	* @throws
	 */
	List<OutVehicleAssembleBillAndFeeVo> queryOutVehicleAssembleBillAndFeeVoList(List<String> vehicleAssembleNoList ) throws TfrBusinessException;

	/**
	 * 导出外请车费用信息
	 * @author Administrator
	 * @date 2013-11-28 下午4:40:56
	 * @param adjustOutVehicleFeeDto
	 * @return
	 * @see
	 */
	InputStream exportOutsideVehicleCharge(
			AdjustOutVehicleFeeDto adjustOutVehicleFeeDto);

	/**
	 * 新增时效费用接口
	 * 外请车到达确认之后，自动生成一条待审核数据（不可删除），
	 * 其中如果配载单录入了时效条款，则自动填写“是“，如果没有，则填写“否“；
	 * 只有时效条款外请车车辆“到达确认”以后，系统自动计算出本次运行的结果，
	 * 不足一分钟按一分钟计，最终计算以小时为单位，保存两位小数。
	 * 变化时长=实际运行时长-预计运行时长；
	 * 2、实际运行时长里，没有出发时间的配载单，出发时间按配载时间计算；
	 * <p>提供给到达</p> 
	 * @author 163580
	 * @date 2013-12-5 上午9:46:55
	 * @param truckTaskDetailId  车辆任务明细id
	 * @see
	 */
	void addOutsideVehicleChargeForArrival(String truckTaskDetailId);

	/**
	 * 
	 * 当前系统是否有配置奖励
	 * @author 163580
	 * @date 2013-12-18 下午2:23:47
	 * @return
	 * @see
	 */
	Boolean beReward();

	/**
	 * 
	 * 查询外请车费用修改记录
	 * 
	 * @author 269701--foss--lln
	 * @date 2015-07-14 上午10:07:50
	 */
	List<AdjustOutVehicleFeeLogEntity> queryOutsideVehicleUpdateLogs(
			AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto, int limit,
			int start);

	/**
	 * 
	 * 查询外请车费用修改记录的总数
	 * 
	 * @author 269701--foss--lln
	 * @date 2015-07-14 上午10:07:50
	 */
	Long queryOutsideVehicleUpdateLogsTotalCount(
			AdjustOutVehicleFeeLogDto adjustOutVehicleFeeLogDto);

	/**
	 * 
	 * 未审核记录条数以及审批中记录条数的统计
	 * 
	 * @author 269701--foss--lln
	 * @date 2015-07-16上午10:07:50
	 */

	AdjustOutVehicleFeeEntity noAuditAndAuditInCount(AdjustOutVehicleFeeDto assignLoadTaskDto);
	
	/**
	 * 调整外请车费用申请时候，审批界面，点击通过，调用结算接口
	 * @author 269701---foss--lln
	 * @date 2015--07--28
	 */
	public int approvalAdjustOutVehicleFee(AdjustOutVehicleFeeDto dto);
}