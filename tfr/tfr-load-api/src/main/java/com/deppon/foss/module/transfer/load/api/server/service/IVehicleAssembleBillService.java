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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/service/IVehicleAssembleBillService.java
 *  
 *  FILE NAME          :IVehicleAssembleBillService.java
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OwnTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.transfer.common.api.shared.domain.TotalNumberEntityEntity;
import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.MakeUpWaybillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.GetInfoWhenVehicleNoLostFocusDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintCarriageContractDataDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillHeaderDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.RewardOrPunishAgreementDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.VehicleAssembleBillVo;

/** 
 * @className: IVehicleAssembleBillService
 * @author: ShiWei shiwei@outlook.com
 * @description: 配载单service类
 * @date: 2012-11-8 下午2:42:52
 * 
 */
public interface IVehicleAssembleBillService extends IService {

	/**
	 * 调用交接单模块接口，获取本部门符合条件的待配载交接单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:39:37
	 */
	List<QueryHandOverBillDto> queryUnAssembledHandOverBillList(QueryHandOverBillConditionDto queryHandOverBillConditionDto);
	
	/**
	 * 根据出发部门到达部门判断是快递还是零担
	 * @author 045923-foss-shiwei
	 * @date 2012-11-9 下午4:39:37
	 */
	List<QueryHandOverBillDto> queryUnAssembledHandOverBillList1(QueryHandOverBillConditionDto queryHandOverBillConditionDto);
	
	/**
	 * 传入配载单基本信息、交接单list，保存配载单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-8 下午2:38:43
	 */
	String saveVehicleAssembleBill(VehicleAssembleBillEntity baseEntity,List<VehicleAssembleBillDetailEntity> billDetailEntityList,RewardOrPunishAgreementDto rewardOrPunishDto);
	
	/**
	 * 传入查询条件，返回配载单查询结果列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-13 下午5:11:20
	 */
	List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillList(QueryVehicleAssembleBillConditionDto conditionDto,int limit,int start);
	
	/**
	 * 获取查询到的配载单的总条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-14 下午4:47:59
	 */
	Long getVehicleAssembleBillCount(QueryVehicleAssembleBillConditionDto conditionDto);
	
	/**
	 * 根据配载车次号获取配载单基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:34:31
	 */
	List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNo(String vehicleAssembleNo);
	
	/**
	 * 根据配载车次号获取其下交接单列表
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午6:30:27
	 */
	List<QueryHandOverBillDto> queryHandOverBillListByVNo(String vehicleAssembleNo);
	
	/**
	 * 获取打印运输合同需要的数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-16 上午10:20:55
	 */
	PrintCarriageContractDataDto queryPrintCarriageContractData(String vehicleAssembleNo);
	
	/**
	 * 根据配载车次号获取修改日志
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 上午10:43:13
	 */
	List<VehicleAssembleBillOptLogEntity> queryVehicleAssembleBillOptLogByNo(String vehicleAssembleNo,int limit,int start);
	
	/**
	 * 根据配载车次号获取修改日志的条数
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 下午2:23:53
	 */
	Long queryVehicleAssembleBillOptLogCountByNo(String vehicleAssembleNo);
	
	/**
	 * 根据配载车次号找出当前配载单下所有的交接单号, 打印时用到
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 下午2:23:53
	 */
	List<String> queryHandOverBillNosByVehicleAssembleNo(List<String> vehicleAssembleNo);
	
	/**
	 * 根据配置车次号找出当前配载单下所有的交接单号, 打印时用到
	 * @author 045923-foss-shiwei
	 * @date 2012-11-16 下午2:23:53
	 */
	List<String> queryHandOverBillNosByVehicleAssembleNo(String vehicleAssembleNo);
	/**
	 * 更新配载单信息
	 * @author 045923-foss-shiwei
	 * @param rewardDto 
	 * @date 2012-11-19 下午8:14:07
	 */
	boolean updateVehicleAssembleBill(UpdateVehicleAssembleBillDto updateVehicleAssembleBillDto, RewardOrPunishAgreementDto rewardDto);
	
	/**
	 * 查询出打印配置单中的一部分数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	PrintVehicleAssembleBillHeaderDto queryVehicleAssembleBillPrtDataSource(String vehicleAssembleNo);

	/**
	 * 查询出打印配置单中的一部分数据_根据配载单号查询出装车人
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	String queryLoaderNameByVehicleassemblebillId(String vehicleassemblebillId);
	
	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下所有交接单的汇总
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	PrintVehicleAssembleBillHeaderDto querySummaryHandOverBillByVehicleassemblebill(String vehicleassemblebillId);
	
	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下运单明细数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	List<PrintVehicleAssembleBillDetailDto> queryVehicleAssembleBillDetailPrtDataSource(String vehicleassemblebillId);
	
	/**
	 * 插入打印次数
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:28:41
	 */
	int insertMotorstowagebillrecord(MotorstowagebillrecordEntity motorstowagebillrecordEntity);
	
	/**
	 * 根据交接单号,运单号,查询出交接明细 结果为 7110522/7110523/XXXXX  类型
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:35:35
	 */
	String getHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);

	/**
	 * 根据车次号查询出装车封签号, 结果为 7110522/7110523/XXXXX  类型
	 * @author ibm-zhangyixin
	 * @date 2012-11-21 上午10:32:59
	 */
	String querySealNosByVehicleAssembleNo(String vehicleAssembleNo,
			String sealTypeDetailSide);
	
	/**
	 * 根据车牌号获取车辆信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-22 下午2:28:46
	 */
	VehicleAssociationDto queryVehicleInfoByVehicleNo(String vehicleNo);
	
	/**
	 * 根据货柜编号，获取货柜长宽高载重净空等情况
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 上午10:21:27
	 */
	OwnTruckEntity queryContainerInfoByContainerNo(String containerNo);
	
	/**
	 * 根据公司车车牌号，获取车辆类型
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 上午10:21:27
	 */
	String queryOwnerVehicleTypeByVehicleNo(String vehicleNo);
	
	/**
	 * 调用综合接口，根据到达部门编码，获取该部门是否支持自动分拣
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 下午3:58:16
	 */
	OutfieldEntity queryOutfieldIsHasSortingMachine(String outfieldCode);
	
	/**
	 * 为前台生成配载车次号
	 * @author 045923-foss-shiwei
	 * @date 2012-11-23 下午4:34:33
	 */
	String showVehicleAssembleNo(String destDeptCode,String isCarLoad,Date leaveTime);
	
	/**
	 * 根据运单号查询该运单号的所有汽运配载记录，包括每次的出发时间和预计到达时间
	 * @author 045923-foss-shiwei
	 * @date 2013-1-10 上午11:26:06
	 */
	List<WayBillAssembleDto> queryVehicleAssembleRecordsByWaybillNo(String waybillNo);
	
	/**
	 * 批量更新配载单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-26 下午8:31:29
	 */
	boolean updateVehicleAssembleBillState(List<UpdateVehicleAssembleBillStateDto> updateVehicleAssembleBillStateDtoList);
	
	/**
	 * 根据配载单号更新配载单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-26 下午8:31:29
	 */
	boolean updateVehicleAssembleBillStateByVNo(String vehicleAssembleNo,int targetState);
	
	/**
	 * 根据配载单号作废配载单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-30 下午6:24:48
	 */
	boolean cancelVehicleAssembleBill(String vehicleAssembleNo);
	
	/**
	 * 调用外请车约车模块接口，传入外请车车牌号，获取外请车总费用
	 * @author 045923-foss-shiwei
	 * @date 2012-12-1 下午2:35:55
	 */
	Object getTotalFeeFromInviteVehicleModule(String vehicleNo,String assembleType);
	
	/**
	 * 传入到达部门code，返回外场实体，如果不是外场，则返回null
	 * @author 045923-foss-shiwei
	 * @date 2012-12-5 下午9:18:50
	 */
	OutfieldEntity queryOutfieldByOrgCode(String arriveDeptCode);
	
	/**
	 * 传入配载车次号，调用结算接口，判断是否已做出发付款确认
	 * @author 045923-foss-shiwei
	 * @date 2012-12-6 下午5:06:52
	 */
	boolean  isDepartPaymentConfirm(String vehicleAssembleNo);
	
	/**
	 * 提供给综合的接口，根据配载车次号获取该车次下的所有运单号list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午9:41:56
	 */
	List<String> queryWaybillNoListByVehicleAssembleNo(String vehicleAssembleNo);
	
	/**
	 * 校验到达部门是否在该日发车计划中
	 * @author 045923-foss-shiwei
	 * @date 2013-1-7 上午9:52:19
	 */
	int validateArriveDeptFromTruckPlan(String destOrgCode,Date leaveTime);
	
	/**
	 * 校验车辆是否在该日发车计划中
	 * @author 045923-foss-shiwei
	 * @date 2013-1-7 上午10:17:11
	 */
	GetInfoWhenVehicleNoLostFocusDto validateVehicleNoFromTruckPlan(String destOrgCode, Date leaveTime, String vehicleNo,String transProperty);
	
	/**
	 * 调用结算接口，看是否已做出发付款
	 * @author 045923-foss-shiwei
	 * @date 2013-1-9 上午10:26:46
	 */
	boolean queryBillPayableIsWriteOff(String vehicleAssembleNo);
	
	/**
	 * 获取两部门之间的公里数，乘以车辆每公里费用，计算运费
	 * @author 045923-foss-shiwei
	 * @date 2013-1-22 下午2:24:54
	 */
	BigDecimal calculateFeeTotalForOwnTruck(String destOrgCode,String lineVirtualCode,BigDecimal eachKilometersFees);

	/**
	 * 根据运输性质的不同来获取运行时数
	 * @author 045923-foss-shiwei
	 * @date 2013-2-25 下午3:30:27
	 */
	BigDecimal queryRunHoursByTransProperty(String destOrgCode, Date leaveTime, String vehicleNo,String transProperty);
	
	/**
	 * 用于验证选择的配载单在该车牌下是否还有其他的没选择
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午4:00:01
	 */
	Long checkPrintVehicleAssembleBill(
			VehicleAssembleBillVo vehicleAssembleBillVo);

	/**
	 * @Title: checkPrintTruckTask 
	 * @Description: 校验选择了多少任务车辆
	 * @param vehicleAssembleBillVo
	 * @return    
	 * @return Long    返回类型 
	 * checkPrintTruckTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-1下午11:51:37
	 */
	Long checkPrintTruckTask(
			VehicleAssembleBillVo vehicleAssembleBillVo);
	
	/**
	 * 根据配载车次号导出所有运单
	 * @author 045923-foss-shiwei
	 * @date 2013-3-15 下午3:38:13
	 */
	@SuppressWarnings("rawtypes")
	List getWayBillExcelInputStream(String vehicleAssembleNo);
	
	/**
	 * 导出配载单
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午5:31:02
	 */
	@SuppressWarnings("rawtypes")
	List getVehicleAssembleBillExcelInputStream(QueryVehicleAssembleBillConditionDto conditionDto);
	
	/**
	 * 获取上级组织code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-3 下午4:34:15
	 */
	String querySuperiorOrgCode();

	/** 
	 * @Title: checkCarriageContractPrinted 
	 * @Description: 判断运输合同是否重复打印 
	 * @param vehicleAssembleNo
	 * @return    
	 * @return int    返回类型 
	 * checkCarriageContractPrinted
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午3:26:26
	 */ 
	int checkCarriageContractPrinted(String vehicleAssembleNo);
	
	/**
	 * 修改配载单时根据运输性质的不同来获取运行时数
	 * @author 105869-foss-heyongdong
	 * @date 2013-7-26 下午3:40:27
	 */
	BigDecimal queryModifyRunHoursByTransProperty(String vehicleNo,String vehicleAssembleNo,String transProperty);
	/**
	 * 通过运单查询配载单
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-6 15:49:52
	 */
	boolean queryVehicleAssemblyBillByWaybillNo(String waybillNo);

	
	/**
	 * 通过配载单号查询配载单信息
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-6 15:49:52
	 */
	QueryVehicleAssembleBillDto queryVehicleAssembleBillInfoByBillNo(String vehicleAssembillNo);
	/**
	 * 判断是否时效线路
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-6 15:49:52
	 */
	RewardOrPunishAgreementDto checkBeAgingLine(String destDeptCode);

	//
	RewardOrPunishAgreementDto queryRewardOrPunishAgreement(
			String vehicleAssembleNo);
	/**
	 * 传入配载单号，获取奖罚信息
	 * @author foss-heyongdong
	 * @date 2013年12月24日 18:46:33
	 * */
	 RewardOrPunishAgreementDto queryRewardOrPunishAgreementDetail(String vehicleAssembleNo);
	 
		/**
		 * 传入运行时差和配载单号
		 * @author foss-heyongdong
		 * @date 2013年12月24日 18:47:36
		 * **/
		BigDecimal qureyRewardOrPunishMoney(String vehicleAssembleNo,double runHours);
	/**
	 * 运单补录更新配载单装载率	
	 * @param makeUpWaybillEntity
	 * @return
	 */      
	 boolean updateVehAssemForMakeUpWaybill(MakeUpWaybillEntity makeUpWaybillEntity);
	 
	 /**
		 * 根据交接单号 查询交接单中第一条运单的运输性质
		 * @Author: 200978  xiaobingcheng
		 * 2014-8-26
		 * @param handoverNo
		 * @return
		 */
	 String queryProductTypeByHandoverNo(String handoverNo);
	 
	 /**
	  * 查配载单在租车标记表的预提状态：为预提中、已预提
	  * 
	  * */
	String queryHoldingState(String vehicleAssembleNo);
	 /**
	  * 校验是否存在有到达部门是当前部门的在途车辆任务
	  * @param vehicleNo
	  */
	void validateOntheWayTruckTask(String vehicleNo);

	/**通过车牌号查询在途只装不卸且未出发的车辆
	 *@author 105869
	 *@date 2014年11月26日 14:07:08
	 * @param vehicleNo
	 * @return
	 */
	String queryMidleUnloadVehicleAssemByVehicleNo(String vehicleNo);
	
	/**
	 ** 通过车辆任务id和单据号查询该车辆任务下 所有有运费的配载单号
	 * @author 105869
	 * @date 2015年1月29日 11:30:51
	 * @param  truckTaskDetailId,billNo 
	 * */
	List<String> queryTruckBillByDetailId(String truckTaskDetailId,
			String billNo);
	/**
	 * 当年1月1号到当日长途外请车和合同车累计发车趟数 
	 * 当月1号到当日累计长途外请车和合同车累计发车趟数
	 * @author 105869
	 * @date 2015年1月5日 16:19:29
	 * @return TotalNumberEntityEntity
	 */
	TotalNumberEntityEntity cumulativeDepartureTimes();
	/**
	 * 给结算接口
	 * <p> 根据运单号   查询配载单数</p> 
	 * @author 189284 
	 * @date 2015-12-17 上午11:41:36
	 * @param waybillNo 
	 * @return
	 * @see
	 */
	String queryIsVehicleassembleByNo(String waybillNo);

	List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNoFromWk(
			String vehicleassembleNo);
	
	/**
	 ** 通过车辆任务id和单据号查询该车辆任务下 所有有运费单据号
	 * @author 332209
	 * @date 2015年1月29日 11:30:51
	 * @param  truckTaskDetailId,billNo 
	 * */
	public List<String> queryTruckBillByDetailIdAndBillNo(String truckTaskDetailId,String billNo);
	
	/**
	 * 根据交接单号获取交接单基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:36:38
	 * @see com.deppon.foss.module.transfer.load.api.server.service.IVehicleAssembleBillService#queryVehicleAssembleBillByNo(java.lang.String)
	 */
	public List<VehicleAssembleBillEntity> queryWkHandoverBillByNo(String vehicleAssembleNo);

}