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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/load/api/server/dao/IVehicleAssembleBillDao.java
 *  
 *  FILE NAME          :IVehicleAssembleBillDao.java
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

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.common.api.shared.dto.WayBillAssembleDto;
import com.deppon.foss.module.transfer.load.api.shared.domain.MotorstowagebillrecordEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.RewardOrPunishAgreementEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.VehicleAssembleBillOptLogEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.DeleteHandOverBillFromVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintCarriageContractDataDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillDetailDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.PrintVehicleAssembleBillHeaderDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillConditionDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryVehicleAssembleBillDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.UpdateVehicleAssembleBillStateDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.VehicleAssembleBillVo;

/** 
 * @className: IVehicleAssembleBillDao
 * @author: ShiWei shiwei@outlook.com
 * @description: 配载单dao接口
 * @date: 2012-11-8 下午2:32:15
 * 
 */
public interface IVehicleAssembleBillDao {
	
	/**
	 * 传入配载单基本信息、交接单list，保存配载单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-8 下午2:38:43
	 */
	boolean saveVehicleAssembleBill(VehicleAssembleBillEntity baseEntity,List<VehicleAssembleBillDetailEntity> billDetailEntityList);
	
	/**
	 * 查询配载单
	 * @author 045923-foss-shiwei
	 * @date 2012-11-13 下午5:10:10
	 */
	List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillList(QueryVehicleAssembleBillConditionDto conditionDto,int limit,int start);
	
	/**
	 * 无分页查询配载单，用于导出
	 * @author 045923-foss-shiwei
	 * @date 2013-5-29 下午5:24:49
	 */
	List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillListNoPaging(QueryVehicleAssembleBillConditionDto conditionDto);
	
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
	 * @date 2012-11-16 上午10:22:10
	 */
	PrintCarriageContractDataDto queryPrintCarriageContractData(
			String vehicleAssembleNo);
	
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
	 * 更新配载单信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-19 下午8:14:07
	 */
	boolean updateVehicleAssembleBill(VehicleAssembleBillEntity baseEntity,
			List<VehicleAssembleBillDetailEntity> addedHandOverBillList,
			DeleteHandOverBillFromVehicleAssembleBillDto deletedHandOverBillDto,
			List<VehicleAssembleBillOptLogEntity> billOptLogList);
	
	/**
	 * 插入配载单修改日志
	 * @author 045923-foss-shiwei
	 * @date 2013-6-8 下午9:54:32
	 */
	int addOptLogList(List<VehicleAssembleBillOptLogEntity> billOptLogList);
	
	/**
	 * 更新配载单状态
	 * @author 045923-foss-shiwei
	 * @date 2012-11-26 下午8:38:55
	 */
	boolean updateVehicleAssembleBillState(List<UpdateVehicleAssembleBillStateDto> updateVehicleAssembleBillStateDtoList);
	
	/**
	 * 根据配载车次号获取该车次下的所有运单号list
	 * @author 045923-foss-shiwei
	 * @date 2012-12-23 上午9:40:45
	 */
	List<String> queryWaybillNoListByVehicleAssembleNo(String vehicleAssembleNo);
	
	/**
	 * 根据运单号查询该运单号的所有汽运配载记录，包括每次的出发时间和预计到达时间
	 * @author 045923-foss-shiwei
	 * @date 2013-1-10 上午11:26:06
	 */
	List<WayBillAssembleDto> queryVehicleAssembleRecordsByWaybillNo(String waybillNo);
	
	/**
	 * 查询出打印配置单中的一部分数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	PrintVehicleAssembleBillHeaderDto queryVehicleAssembleBillPrtDataSource(
			String vehicleAssembleNo);
	
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
	PrintVehicleAssembleBillHeaderDto querySummaryHandOverBillByVehicleassemblebill(
			String vehicleassemblebillId);

	/**
	 * 查询出打印配置单中的一部分数据_查询出该配置单下运单明细数据
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:07:50
	 */
	List<PrintVehicleAssembleBillDetailDto> queryVehicleAssembleBillDetailPrtDataSource(
			String vehicleassemblebillId);

	/**
	 * 插入打印次数
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:29:33
	 */
	int insertMotorstowagebillrecord(
			MotorstowagebillrecordEntity motorstowagebillrecordEntity);

	/**
	 * 根据交接单号, 运单号, 查询出交接明细
	 * @author ibm-zhangyixin
	 * @date 2012-11-20 下午2:35:35
	 */
	String getHandOverBillSerialNoDetailsByWayBillNo(
			String wayBillNo, String handOverBillNo);

	String querySealNosByVehicleAssembleNo(String vehicleAssembleNo,
			String sealType);

	/**
	 * 用于验证选择的配载单在该车牌下是否还有其他的没选择
	 * @author ibm-zhangyixin
	 * @date 2012-10-31 下午4:00:01
	 */
	Long checkPrintVehicleAssembleBill(
			VehicleAssembleBillVo vehicleAssembleBillVo);

	/** 
	 * @Title: getMotorstowagebillrecordByVehicleAssembleNo 
	 * @Description: 判断运输合同是否重复打印 
	 * @param vehicleAssembleNo
	 * @return    
	 * @return MotorstowagebillrecordEntity    返回类型 
	 * getMotorstowagebillrecordByVehicleAssembleNo
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-4-19下午3:28:43
	 */ 
	MotorstowagebillrecordEntity getMotorstowagebillrecordByVehicleAssembleNo(
			String vehicleAssembleNo);

	/** 
	 * @Title: checkPrintTruckTask 
	 * @Description: 校验选择了多少任务车辆 
	 * @param vehicleAssembleBillVo
	 * @return    
	 * @return Long    返回类型 
	 * checkPrintTruckTask
	 * @author: ibm-zhangyixin
	 * @throws 
	 * Date:2013-6-1下午11:52:48
	 */ 
	Long checkPrintTruckTask(VehicleAssembleBillVo vehicleAssembleBillVo);
	
	/**
	 * 获取两部门间最大配载车次号，用于生成配载单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-7-2 上午9:09:26
	 */
	String queryLatestVehicleAssembleNo(String oriOrgCode,	List<String> destOrgCodeList, String leaveTime);
	
	
	/**
	 * 通过运单号查询配载单
	 * @author 105869-foss-heyongdong
	 * @date 2013-9-7 10:29:02
	 */
	List<QueryVehicleAssembleBillDto> queryVehicleAssemblyBillByWaybillNo(
			String waybillNo);

	int saveVehicleRewardProt(List<RewardOrPunishAgreementEntity> rewardOrPunishAgreementEntity);
	

	/**
	 * 通过配载单号查询配载信息以及任务信息
	 * @author 105869-foss-heyongdong
	 * @date 2013年11月27日 14:16:34
	 */
	List<QueryVehicleAssembleBillDto> queryVehicleAssembleBillInfoByBillNo(
			String vehicleAssembillNo);
	
	/**
	 * 通过配载单号查询奖罚信息
	 * @author 105869-foss-heyongdong
	 * @date 2013年12月19日 09:29:09
	 * */
	List<RewardOrPunishAgreementEntity> queryRewardOrPunishDetail(
			String vehicleAssembleNo);

	/**
	 * 通过配载车次号删除奖罚信息
	 * @author foss-heyongdong
	 * @date  2013年12月23日 14:05:09
	 * */
	int deletReWardOrPunishAgreement(String vehicleAssembleNo);

	/**
	 * 运单补录跟新配载单装载率
	 * @param vehicleAssembleNo
	 * @param vehicleAssembleNo2 
	 * @return
	 */
	int updateVehAssemForMakeUpWaybill(String loadingRate, String vehicleAssembleNo2);
	
	/**
	 * 根据交接单号 查询交接单中第一条运单的运输性质
	 * @Author: 200978  xiaobingcheng
	 * 2014-8-26
	 * @param handoverNo
	 * @return
	 */
	 String queryProductTypeByHandoverNo(String handoverNo);
	 

		/**通过车牌号查询在途只装不卸且未出发的车辆
		 *@author 105869
		 *@date 2014年11月26日 14:07:08
		 * @param vehicleNo
		 * @return
		 */
	String queryMidleUnloadVehicleAssemByVehicleNo(String vehicleNo);

	/**
	 * 通过车辆任务id和单据号查询该车辆任务下 所有有运费的配载单号
	 * @author 105869
	 * @date 2015年1月28日 08:56:10
	 * @param  truckTaskDetailId,billNo
	 * 
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
	long leasedVehicleDepartureTimes(Date startDate,Date endDate);
	/**
	 * 给结算接口
	 * <p> 根据运单号   查询配载单数</p> 
	 * @author 189284 
	 * @date 2015-12-17 上午11:41:36
	 * @param waybillNo 
	 * @return
	 * @see
	 */
	Long queryIsVehicleassembleByNo(String waybillNo);

	List<VehicleAssembleBillEntity> queryVehicleAssembleBillByNoFromWk(
			String vehicleAssembleNo);
	
	/**
	 ** 通过车辆任务id和单据号查询该车辆任务下 所有有运费的单据号
	 * @author 105869
	 * @date 2015年1月28日 08:56:10
	 * @param  truckTaskDetailId,billNo 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryTruckBillByDetailId(java.lang.String, java.lang.String)
	 */
	public List<String> queryTruckBillByDetailIdAndBillNo(String truckTaskDetailId,
			String billNo);
	
	/**
	 * 根据交接单号获取交接单基本信息
	 * @author 045923-foss-shiwei
	 * @date 2012-11-15 下午4:35:26
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IVehicleAssembleBillDao#queryVehicleAssembleBillByNo(java.lang.String)
	 */
	public List<VehicleAssembleBillEntity> queryWkHandoverBillByNo(
			String vehicleAssembleNo);
}