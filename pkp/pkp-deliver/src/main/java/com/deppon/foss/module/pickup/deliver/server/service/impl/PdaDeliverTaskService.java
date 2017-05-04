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
 * PROJECT NAME	: pkp-deliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/deliver/server/service/impl/PdaDeliverTaskService.java
 * 
 * FILE NAME        	: PdaDeliverTaskService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.deliver.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressVehiclesService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressVehiclesEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IPdaSignEntityDao;
import com.deppon.foss.module.pickup.order.api.shared.define.PdaSignStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.PdaSignEntity;
import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService;
import com.deppon.foss.module.pickup.pda.api.shared.define.PdaConstants;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverHandTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskConditionDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDeliverTaskDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialParamDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDao;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IDeliverbillDetailDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.transfer.load.api.server.service.IDeliverLoadTaskService;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.pda.api.shared.define.TransferPDADictConstants;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IDeliverBillVaryStatusService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverBillVaryStatusEntity;
import com.deppon.foss.module.pickup.sign.api.server.dao.IDeliverHandlerDao;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
/**
 * PDA查询送货任务接口、完成下拉任务接口
 * 
 * @author 097972-foss-dengtingting
 * @date 2012-12-12 下午7:26:01
 */
public class PdaDeliverTaskService implements IPdaDeliverTaskService {
	/**
	 * 记录
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PdaDeliverTaskService.class);
	// 派送单明细dao
	private IDeliverbillDetailDao deliverbillDetailDao;

	// PDA签到DAO
	private IPdaSignEntityDao pdaSignEntityDao;

	// 派送单DAO
	private IDeliverbillDao deliverbillDao;
	
	private IDeliverHandlerDao deliverHandlerDao;

	public void setDeliverHandlerDao(IDeliverHandlerDao deliverHandlerDao) {
		this.deliverHandlerDao = deliverHandlerDao;
	}

	// 查询流水号service
	IDeliverLoadTaskService deliverLoadTaskService;
	private IExpressVehiclesService expressVehiclesService;
	//派送单状态更新记录表Service 
	private IDeliverBillVaryStatusService deliverBillVaryStatusService;
	
	/**
	 * 货款结清Service
	 */
	private IRepaymentService repaymentService;
	
	@Override
	public List<PdaDeliverTaskDto> getDeliverTaskList(String driverCode,
			String vehicleNo) {
		int deviceNoNum = pdaSignEntityDao.querySignCountByDV(new PdaSignEntity(null,
				driverCode, vehicleNo, PdaSignStatusConstants.BUNDLE));
		if (deviceNoNum <= 0) {
			throw new PdaProcessException("还未登录，或者登录已失效，请重新登录");
		}
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity ();
		expressVehiclesEntity.setEmpCode(driverCode);
		// 根据司机、车牌号、派送单状态查询派送单号
		PdaDeliverTaskConditionDto dto = new PdaDeliverTaskConditionDto();
		List<ExpressVehiclesEntity> list = expressVehiclesService.queryExpressVehiclesList(expressVehiclesEntity, 0, 2);
		if(list.size()>0){
			dto.setUserType(PdaConstants.OPERATE_TYPE_EXPRESS);
		}else{
			dto.setUserType(PdaConstants.OPERATE_TYPE_TYPE_LTL);
		}
		
		dto.setDriverCode(driverCode);
		dto.setVehicleNo(vehicleNo);
		dto.setDeliverbillStatus(DeliverbillConstants.STATUS_CONFIRMED);
		dto.setArriveSheetActive(FossConstants.YES);
		dto.setArriveSheetStatus(ArriveSheetConstants.STATUS_DELIVER);
		dto.setArriveSheetDestroyed(FossConstants.NO);//到达联未作废
		List<String> goodsStates = new ArrayList<String>();
		goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);//装车货物类型-取消
		goodsStates.add(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);//装车货物类型-未装车
		dto.setGoodsStates(goodsStates);
		dto.setTaskState(LoadConstants.LOAD_TASK_STATE_CANCELED);
		dto.setTaskState(LoadConstants.LOAD_TASK_STATE_CANCELED);
		//添加运单 收获客户电话	RECEIVE_CUSTOMER_PHONE	收货客户电话  add 309603 
		dto.setShowPhoneNo("Y");
		//add by 329757 判断是否是外请车
		if("000000".equals(driverCode)){
			//如果司机是外请车  就不用工号查询派送任务
			dto.setDriverCode(null);
		}
		return deliverbillDetailDao.queryPdaDeliverTaskDtoByCondition(dto);
	}
	
	/**
	 * 根据司机工号、车牌号查询快递送货任务接口
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-14 上午11:01:34
	 * @param driverCode 司机工号
	 * @param vehicleNo 车牌号
	 * @return
	 */
	@Override
	public List<PdaDeliverTaskDto> getExpressDeliverTaskList(String driverCode,
			String vehicleNo) {
		int deviceNoNum = pdaSignEntityDao.querySignCountByDV(new PdaSignEntity(null,
				driverCode, vehicleNo, PdaSignStatusConstants.BUNDLE));
		if (deviceNoNum <= 0) {
			throw new PdaProcessException("还未登录，或者登录已失效，请重新登录");
		}
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity ();
		expressVehiclesEntity.setEmpCode(driverCode);
		// 根据司机、车牌号、派送单状态查询派送单号
		PdaDeliverTaskConditionDto dto = new PdaDeliverTaskConditionDto();
		List<ExpressVehiclesEntity> list = expressVehiclesService.queryExpressVehiclesList(expressVehiclesEntity, 0, 2);
		if(list.size()>0){
			dto.setDriverCode(driverCode);
			dto.setVehicleNo(vehicleNo);
			List<String> statusList = new ArrayList<String>();
			statusList.add(DeliverbillConstants.STATUS_CONFIRMED);//派送单状态-已确认
			statusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);//派送单状态-已下拉
			dto.setDeliverbillStatusList(statusList);
			dto.setArriveSheetActive(FossConstants.YES);
			dto.setArriveSheetStatus(ArriveSheetConstants.STATUS_DELIVER);
			dto.setArriveSheetDestroyed(FossConstants.NO);//到达联未作废
			List<String> goodsStates = new ArrayList<String>();
			goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);//装车货物类型-取消
			goodsStates.add(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);//装车货物类型-未装车
			dto.setGoodsStates(goodsStates);
			dto.setTaskState(LoadConstants.LOAD_TASK_STATE_CANCELED);
			List<PdaDeliverTaskDto> pdaTaskDto = deliverbillDetailDao.queryPdaDeliverTaskDtoByCondition(dto);
			if(CollectionUtils.isNotEmpty(pdaTaskDto)){
				//添加下拉送货任务返回时的日志，输出返回信息
				LOGGER.info("-- 下拉送货任务结束：----pdaTaskDto:"+JSON.toJSONString(pdaTaskDto));
			}
			return pdaTaskDto;
			
		}else{
			throw new PdaProcessException("无此快递车辆");
		}
	}

	/**
	 * 根据任务号、司机工号、车牌号查询快递派件交接明细接口
	 * @author 243921-foss-zhangtingting
	 * @date 2015-04-14 上午11:01:34
	 * @param driverCode 司机工号
	 * @param vehicleNo 车牌号
	 * @return
	 */
	@Override
	public List<PdaDeliverHandTaskDto> getDeliverHandTaskList(String taskNo,
			String driverCode, String vehicleNo) {
		ExpressVehiclesEntity expressVehiclesEntity = new ExpressVehiclesEntity ();
		expressVehiclesEntity.setEmpCode(driverCode);
		// 根据司机、车牌号、派送单状态查询派送单号
		PdaDeliverHandTaskConditionDto dto = new PdaDeliverHandTaskConditionDto();
		List<ExpressVehiclesEntity> list = expressVehiclesService.queryExpressVehiclesList(expressVehiclesEntity, 0, 2);
		if(list.size()>0){
			dto.setDriverCode(driverCode);
			dto.setVehicleNo(vehicleNo);
			List<String> statusList = new ArrayList<String>();
			statusList.add(DeliverbillConstants.STATUS_CONFIRMED);//派送单状态-已确认
			statusList.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);//派送单状态-已下拉
			dto.setDeliverbillStatus(statusList);
			dto.setArriveSheetActive(FossConstants.YES);
			dto.setArriveSheetStatus(ArriveSheetConstants.STATUS_DELIVER);
			dto.setArriveSheetDestroyed(FossConstants.NO);//到达联未作废
			List<String> goodsStates = new ArrayList<String>();
			goodsStates.add(TransferPDADictConstants.LOAD_GOODS_STATE_CANCELED);//装车货物类型-取消
			goodsStates.add(LoadConstants.LOAD_GOODS_STATE_NOT_LOADING);//装车货物类型-未装车
			dto.setGoodsStates(goodsStates);
			dto.setTaskNo(taskNo);
			return deliverbillDetailDao.queryPdaDeliverHandTaskDtoByCondition(dto);
		}else{
			throw new PdaProcessException("无此快递车辆");
		}
	}
	
	@Override
	public int updateDeliverbillStatus(String deliverbillNo) {
		
		//根据派送单号查询派送单的状态 ---by 243921
		DeliverbillDto deliverbillDto = new DeliverbillDto();
		deliverbillDto.setDeliverbillNo(deliverbillNo);
		DeliverbillEntity deliverbillEntity = deliverbillDao.queryByDeliverbillDto(deliverbillDto);
		//派送单之前的状态为已取消，不进行任何操作
		if(null != deliverbillEntity && DeliverbillConstants.STATUS_CONFIRMED.equals(deliverbillEntity.getStatus())){
			DeliverbillDetailDto dto = new DeliverbillDetailDto();
			dto.setDeliverbillNo(deliverbillEntity.getDeliverbillNo());
			dto.setId(deliverbillEntity.getId());
			// 将状态改为 "PDA已下拉"
			dto.setDeliverStatus(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
			//如果派送单号和派送单状态不为空，添加-派送单状态更新记录
			if (StringUtils.isNotBlank(dto.getDeliverbillNo()) && StringUtils.isNotBlank(dto.getDeliverStatus()) ) {
				DeliverBillVaryStatusEntity deliverBillVary = new DeliverBillVaryStatusEntity();
				deliverBillVary.setDeliverBillNo(dto.getDeliverbillNo());//派送单号
				deliverBillVary.setDeliverBillStatus(dto.getDeliverStatus());//派送单状态
				//添加纪录
				deliverBillVaryStatusService.insertDBVaryStatus(deliverBillVary);
			}
			return deliverHandlerDao.updateDeliverbill(dto);
		}else{
			return 0;
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.pda.api.server.service.IPdaDeliverTaskService#selectFinancial(com.deppon.foss.module.pickup.pda.api.shared.dto.PdaFinancialParamDto)
	 */
	@Override
	public PdaFinancialDto selectFinancial(PdaFinancialParamDto pdaFinancialParamDto) {
		
		if (pdaFinancialParamDto == null) {
			throw new PdaProcessException("传入参数对象不能为空");
		}
		
		if (StringUtils.isBlank(pdaFinancialParamDto.getWaybillNo())) {
			throw new PdaProcessException("传入运单号不能为空");
		}
		
		PdaFinancialDto pdaFinancialDto = new PdaFinancialDto();
		
		FinancialDto finance = repaymentService.queryFinanceSign(pdaFinancialParamDto.getWaybillNo()); //单号
		//应收代收款
		pdaFinancialDto.setCodAmount(finance.getReceiveableAmount()==null ? BigDecimal.ZERO : finance.getReceiveableAmount().multiply(BigDecimal.valueOf(SettlementReportNumber.ONE_HUNDRED)));
		//应收到付款
		pdaFinancialDto.setToPayAmount( finance.getReceiveablePayAmoout() ==null ? BigDecimal.ZERO : finance.getReceiveablePayAmoout().multiply(BigDecimal.valueOf(SettlementReportNumber.ONE_HUNDRED)));
		
		return pdaFinancialDto;
	}
	

	public void setDeliverbillDetailDao(
			IDeliverbillDetailDao deliverbillDetailDao) {
		this.deliverbillDetailDao = deliverbillDetailDao;
	}

	public void setPdaSignEntityDao(IPdaSignEntityDao pdaSignEntityDao) {
		this.pdaSignEntityDao = pdaSignEntityDao;
	}

	public void setDeliverbillDao(IDeliverbillDao deliverbillDao) {
		this.deliverbillDao = deliverbillDao;
	}

	public void setDeliverLoadTaskService(
			IDeliverLoadTaskService deliverLoadTaskService) {
		this.deliverLoadTaskService = deliverLoadTaskService;
	}

	public void setExpressVehiclesService(
			IExpressVehiclesService expressVehiclesService) {
		this.expressVehiclesService = expressVehiclesService;
	}

	public void setDeliverBillVaryStatusService(
			IDeliverBillVaryStatusService deliverBillVaryStatusService) {
		this.deliverBillVaryStatusService = deliverBillVaryStatusService;
	}

	/**
	 * 设置repaymentService
	 * @param repaymentService 要设置的repaymentService
	 */
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	
}