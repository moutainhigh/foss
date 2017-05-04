/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-agency
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/agency/test/service/TruckStowageServiceTest.java
 * 
 * FILE NAME        	: TruckStowageServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.agency.test.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.deppon.foss.module.settlement.agency.api.server.service.ITruckStowageService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.agency.test.BaseTestCase;
import com.deppon.foss.module.settlement.agency.test.util.AgencyTestUtil;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.load.api.shared.define.OutsideVehicleChargeConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.util.define.FossConstants;

/**
 * 配载单服务测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-1 下午2:15:11
 * @since
 * @version
 */
public class TruckStowageServiceTest extends BaseTestCase {

	@Autowired
	private ITruckStowageService truckStowageService;

	@Autowired
	private IBillPayableService billPayableService;

	public void setUp() {

	}

	/**
	 * 初始化配载单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午2:16:37
	 * @param 配载类型
	 * @param 付款类型
	 * @return
	 */
	private StlVehicleAssembleBillDto getStlVehicleAssembleBillDtoToAdd(
			String assembleType, String paymentType, String beReturnReceipt) {
		StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();
		dto.setVehicleAssembleNo(AgencyTestUtil.getVehicleAssembleNO());// 配载车次号
		dto.setWaybillNo(AgencyTestUtil.getWaybillNO());// 获取运单号
		dto.setAssembleType(assembleType);// 配载类型
		dto.setOrigOrgCode("CFBUMEN");// 出发部门编码
		dto.setOrigOrgName("出发部门");
		dto.setDestOrgCode("DDBUMEN");// 到达部门编码
		dto.setDestOrgName("到达部门");// 到达部门编码
		dto.setVehicleNo("沪.QQ8888");// 车牌号
		dto.setVehicleOwnerShip("WQC");// 车辆所有权类别

		dto.setDriverName("王虎");// 司机名称
		dto.setDriverCode("D0001");// 司机编码

		dto.setPaymentType(paymentType);// 付款方式
		dto.setFeeTotal(new BigDecimal("10000"));// 总运费
		dto.setPrePaidFeeTotal(new BigDecimal("6000"));// 预付运费总额
		dto.setArriveFeeTotal(new BigDecimal("4000"));// 到付运费总额

		dto.setBeReturnReceipt(beReturnReceipt);// 是否押回单

		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种

		return dto;
	}

	/**
	 * 录入配载单测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午2:15:28
	 */
	@Test
	@Rollback(true)
	public void testAddTruckStowage() {
		try {
			// 整车付款方式为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());

			// 再次保存提示异常信息
			// this.truckStowageService.addTruckStowage(dto,
			// AgencyTestUtil.getCurrentInfo());

			List<BillPayableEntity> list = this
					.queryBillPyableBySourceBillNOs(dto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)
					&& list.size() == 2);

			// 专线非整车外请车为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto wqcZcDto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(wqcZcDto,
					AgencyTestUtil.getCurrentInfo());

			list = this.queryBillPyableBySourceBillNOs(wqcZcDto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)
					&& list.size() == 2);

			// 整车付款方式为月结的---会生成一条应付单记录
			StlVehicleAssembleBillDto dtoYueJieDto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dtoYueJieDto,
					AgencyTestUtil.getCurrentInfo());

			list = this.queryBillPyableBySourceBillNOs(dtoYueJieDto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)
					&& list.size() == 1);

			// 专线非整车外请车付款方式为月结的 ---会生成一条应付单记录
			StlVehicleAssembleBillDto wqcDto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(wqcDto,
					AgencyTestUtil.getCurrentInfo());

			list = this.queryBillPyableBySourceBillNOs(wqcDto);
			Assert.assertTrue(CollectionUtils.isNotEmpty(list)
					&& list.size() == 1);
			for(BillPayableEntity entity:list){
				if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST.
						equals(entity.getBillType())){
					Assert.assertTrue(entity.getAmount().compareTo(dto.getFeeTotal())==0);
				}
			}
			} catch (SettlementException e) {
			logger.info(e.getErrorCode());
		}
	}
	
	
	
	/**
	 * 包含有押回单的信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-3 上午9:10:07
	 */
	@Test
	@Rollback(true)
	public void testAddTruckStowageHaveBeReturnReceipt(){
		try{
			// 整车付款方式为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.YES);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());
			
			
			//修改成无押回单的
			dto.setBeReturnReceipt(FossConstants.YES);
			dto.setPrePaidFeeTotal(new BigDecimal("2000"));
			dto.setArriveFeeTotal(new BigDecimal("8000"));
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 新增配载类型为整车，付款方式为月结的配载单，修改为
	 * 
	 * 现金无押回单
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-23 下午1:53:54
	 */
	@Test
	@Rollback(true)
	public void testAddTruckStowageTwo(){
		try{
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());
			
			//设置为现金
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
			
			
			//付款方式为现金
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			dto.setBeReturnReceipt(FossConstants.YES);
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
			
			//付款方式为月结
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}

	
	/**
	 * 配载类型为：长途外请车
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-23 下午2:08:55
	 */
	@Test
	@Rollback(true)
	public void testAddTruckStowageThree(){
		try{
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,//付款方式月结
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());
			
			//设置为现金
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
			
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			dto.setBeReturnReceipt(FossConstants.YES);
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
			
			
			//设置为月结
			dto.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT);
			this.truckStowageService.modifyTruckStowage(dto, AgencyTestUtil.getCurrentInfo());
		}catch(SettlementException e){
			logger.error(e.getMessage(),e);
		}
	}

	private List<BillPayableEntity> queryBillPyableBySourceBillNOs(
			StlVehicleAssembleBillDto dto) {
		List<String> sourceBillNos = new ArrayList<String>();
		sourceBillNos.add(dto.getVehicleAssembleNo());
		return this.billPayableService
				.queryBySourceBillNOs(
						sourceBillNos,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE,
						FossConstants.ACTIVE);
	}

	/**
	 * 修改方法 首款和尾款都有金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午5:30:30
	 * @param waybillNo
	 * @param vehicleAssembleNo
	 * @param assembleType
	 * @param paymentType
	 * @param beReturnReceipt
	 * @return
	 */
	private StlVehicleAssembleBillDto getStlVehicleAssembleBillDtoToUpdate(
			String waybillNo, String vehicleAssembleNo, String assembleType,
			String paymentType, String beReturnReceipt) {
		StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();
		dto.setVehicleAssembleNo(vehicleAssembleNo);// 配载车次号
		dto.setWaybillNo(waybillNo);// 运单号
		dto.setAssembleType(assembleType);// 配载类型
		dto.setOrigOrgCode("CFBUMEN");// 出发部门编码
		dto.setOrigOrgName("出发部门");
		dto.setDestOrgCode("DDBUMEN");// 到达部门编码
		dto.setDestOrgName("到达部门");// 到达部门编码
		dto.setVehicleNo("沪.QQ8888");// 车牌号
		dto.setVehicleOwnerShip("WQC");// 车辆所有权类别

		dto.setDriverName("王虎");// 司机名称
		dto.setDriverCode("D0001");// 司机编码

		dto.setPaymentType(paymentType);// 付款方式
		dto.setFeeTotal(new BigDecimal("10000"));// 总运费
		dto.setPrePaidFeeTotal(new BigDecimal("1000"));// 预付运费总额
		dto.setArriveFeeTotal(new BigDecimal("9000"));// 到付运费总额

		dto.setBeReturnReceipt(beReturnReceipt);// 是否押回单

		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种

		return dto;
	}

	/**
	 * 修改为只有一方来付款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午5:31:03
	 * @param waybillNo
	 * @param vehicleAssembleNo
	 * @param assembleType
	 * @param paymentType
	 * @param beReturnReceipt
	 * @param payableType 应付类型
	 * @return
	 */
	private StlVehicleAssembleBillDto getStlVehicleAssembleBillDtoToUpdateTwo(
			String waybillNo, String vehicleAssembleNo, String assembleType,
			String paymentType, String beReturnReceipt,String payableType) {
		StlVehicleAssembleBillDto dto = new StlVehicleAssembleBillDto();
		dto.setVehicleAssembleNo(vehicleAssembleNo);// 配载车次号
		dto.setWaybillNo(waybillNo);// 运单号
		dto.setAssembleType(assembleType);// 配载类型
		dto.setOrigOrgCode("CFBUMEN");// 出发部门编码
		dto.setOrigOrgName("出发部门");
		dto.setDestOrgCode("DDBUMEN");// 到达部门编码
		dto.setDestOrgName("到达部门");// 到达部门编码
		dto.setVehicleNo("沪.QQ8888");// 车牌号
		dto.setVehicleOwnerShip("WQC");// 车辆所有权类别

		dto.setDriverName("王虎");// 司机名称
		dto.setDriverCode("D0001");// 司机编码
		dto.setPaymentType(paymentType);// 付款方式
		dto.setFeeTotal(new BigDecimal("10000"));// 总运费
		if(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN.equals(payableType)){
			dto.setPrePaidFeeTotal(new BigDecimal("10000"));// 预付运费总额
		}else if(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__DESTINATION.equals(payableType)){
			dto.setArriveFeeTotal(new BigDecimal("10000"));//到付运费总额
		}
		dto.setBeReturnReceipt(beReturnReceipt);// 是否押回单
		dto.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种

		return dto;
	}

	/**
	 * 修改配载单设置
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午5:33:27
	 */
	@Test
	@Rollback(true)
	public void testModifyTruckStowage() {
		try {
			// 整车付款方式为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());
			
			dto = this
					.getStlVehicleAssembleBillDtoToUpdate(
							dto.getWaybillNo(),
							dto.getVehicleAssembleNo(),
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			
			
			this.truckStowageService.modifyTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());
			
			List<BillPayableEntity> list=this.queryBillPyableBySourceBillNOs(dto);
			if(CollectionUtils.isNotEmpty(list)){
				for(BillPayableEntity entity:list){
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(entity.getBillType())){
						Assert.assertTrue(entity.getAmount().compareTo(dto.getPrePaidFeeTotal())==0);
					}
					if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(entity.getBillType())){
						Assert.assertTrue(entity.getAmount().compareTo(dto.getArriveFeeTotal())==0);
					}
				}
			}
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 修改外发单后，只有首款
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-3 上午8:12:50
	 */
	@Test
	@Rollback(true)
	public void testModifyTruckStowageOnlyHavePrePaidFeeTotal() {
		try {
			// 整车付款方式为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());

			dto = this
					.getStlVehicleAssembleBillDtoToUpdateTwo(
							dto.getWaybillNo(),
							dto.getVehicleAssembleNo(),
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO,SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);

			this.truckStowageService.modifyTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}
	
	/**
	 * 修改外发单后，只有尾款金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-3 上午8:33:57
	 */
	@Test
	@Rollback(true)
	public void testModifyTruckStowageOnlyHaveArriveFeeTotal(){
		try{
			// 整车付款方式为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto dto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());

			dto = this
					.getStlVehicleAssembleBillDtoToUpdateTwo(
							dto.getWaybillNo(),
							dto.getVehicleAssembleNo(),
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO,SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__DESTINATION);

			this.truckStowageService.modifyTruckStowage(dto,
					AgencyTestUtil.getCurrentInfo());			
		}catch(SettlementException e){
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 调整外请车费用
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午5:41:05
	 */
	@Test
	@Rollback(true)
	public void testAdjustOutVehicleFee() {
		try {
			// 专线非整车外请车为现金的 --首款和尾款都有会生成两条应付单记录
			StlVehicleAssembleBillDto wqcZcDto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(wqcZcDto,
					AgencyTestUtil.getCurrentInfo());

			StlVehicleAssembleBillDto tzDto = new StlVehicleAssembleBillDto();
			tzDto.setVehicleAssembleNo(wqcZcDto.getVehicleAssembleNo());// 配载车次号
			tzDto.setAssembleType(LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE);//
			tzDto.setAwardType(OutsideVehicleChargeConstants.REWARD);// 奖励类型
			tzDto.setAdjustFee(new BigDecimal("1000"));// 调整费用
			tzDto.setFeeTotal(new BigDecimal("10000"));// 总运费
			tzDto.setAuditState(OutsideVehicleChargeConstants.AUDITPASS);// 已审核
			tzDto.setOrigOrgCode("CFBUMEN");// 出发部门编码
			tzDto.setOrigOrgName("出发部门");
			tzDto.setDestOrgCode("DDBUMEN");// 到达部门编码
			tzDto.setDestOrgName("到达部门");// 到达部门编码
			this.truckStowageService.adjustOutVehicleFee(tzDto,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

	/**
	 * 作废汽运配载单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 下午5:52:32
	 */
	@Test
	@Rollback(true)
	public void testDisableTruckStowage() {
		try {
			StlVehicleAssembleBillDto wqcZcDto = this
					.getStlVehicleAssembleBillDtoToAdd(
							LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE,
							SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH,
							FossConstants.NO);
			this.truckStowageService.addTruckStowage(wqcZcDto,
					AgencyTestUtil.getCurrentInfo());
			StlVehicleAssembleBillDto zfDto = new StlVehicleAssembleBillDto();
			zfDto.setVehicleAssembleNo(wqcZcDto.getVehicleAssembleNo());// 配载车次号
			zfDto.setAssembleType(LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE);//
			zfDto.setAwardType(OutsideVehicleChargeConstants.REWARD);// 奖励类型
			zfDto.setAdjustFee(new BigDecimal("1000"));// 调整费用
			zfDto.setFeeTotal(new BigDecimal("10000"));// 总运费
			zfDto.setAuditState(OutsideVehicleChargeConstants.AUDITPASS);// 已审核
			zfDto.setOrigOrgCode("CFBUMEN");// 出发部门编码
			zfDto.setOrigOrgName("出发部门");
			zfDto.setDestOrgCode("DDBUMEN");// 到达部门编码
			zfDto.setDestOrgName("到达部门");// 到达部门编码
			zfDto.setVehicleOwnerShip("WQC");// 车辆类型
			this.truckStowageService.disableTruckStowage(zfDto,
					AgencyTestUtil.getCurrentInfo());
		} catch (SettlementException e) {
			logger.error(e.getErrorCode());
		}
	}

}
