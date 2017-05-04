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
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/OutWarehouseExceptionService.java
 * 
 * FILE NAME        	: OutWarehouseExceptionService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOutWarehouseExceptionDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOutWarehouseExceptionService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OutStockExceptionEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 异常出库接口
 * 
 * @author foss-qiaolifeng
 * @date 2012-11-8 上午9:51:39
 */
public class OutWarehouseExceptionService implements
		IOutWarehouseExceptionService {

	/**
	 * Logger
	 */
	private static final Logger logger = LogManager
			.getLogger(OutWarehouseExceptionService.class);

	/**
	 * 应付单服务service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 应收单服务service
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 现金收款单service
	 */
	private IBillCashCollectionService billCashCollectionService;

	/**
	 * 结算通用服务service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 异常出库Dao
	 */
	private IOutWarehouseExceptionDao outWarehouseExceptionDao;
	
	/**
	 * 代收货款service
	 */
	private ICodCommonService codCommonService;
	/**
	 *  运单状态服务接口
	 */
	//private IActualFreightService actualFreightService;

	/**
	 * 异常出库红冲操作
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 上午9:51:35
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IOutWarehouseExceptionService#outWarehouseException(java.lang.String)
	 */
	@Override
	public void outWarehouseException(String waybillNo, String expType,
			CurrentInfo cInfo) {

		logger.info("异常出库开始,运单号:" + waybillNo + " ,异常类型编码:" + expType);

		// 1、校验运单号及异常类型
		validateWaybillNo(waybillNo, expType);

		// 2、新增异常出库信息
		OutStockExceptionEntity outStockExceptionEntity = addWarehouseException(
				waybillNo, expType, cInfo);

		// 3、根据运单号查询应收单
		List<BillReceivableEntity> billReceivableList = queryBillReceivable(waybillNo);
		
		// 7.1、根据运单号查询现金收款单-结算优化需求
		List<BillCashCollectionEntity> billCashCollectionList = billCashCollectionService.queryBySourceBillNOs(Arrays.asList(waybillNo), 
				SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL, 
				FossConstants.ACTIVE);
		// 7.2、现金收款单反写确认时间-结算优化需求
		if (CollectionUtils.isNotEmpty(billCashCollectionList)){
			for (BillCashCollectionEntity entity : billCashCollectionList) {
				entity.setConrevenDate(new Date());
				billCashCollectionService.confirmIncomeBillCashCollection(entity, cInfo);
			}
		}

		// 4、根据运单号查询应付单
		List<BillPayableEntity> billPayableList = queryBillPayable(waybillNo);
		
		// 5、如果该运单有对应的应收单信息,调用红冲应收单接口，红冲应收单
		if (CollectionUtils.isNotEmpty(billReceivableList)) {
			for (BillReceivableEntity entity : billReceivableList) {

				// 反写确认时间
				entity.setConrevenDate(new Date());
				billReceivableService.updateBillReceivableByConfirmIncome(entity, cInfo);
				// 其他地方还需要使用，为了保持java方法中的版本号和数据库中的版本号统一，在这里+1
				entity.setVersionNo((short) (entity.getVersionNo() + 1));
				
				/*// 如果应收单是临欠、月结方式，不执行红冲
				if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
						.equals(entity.getPaymentType())
						|| SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT
								.endsWith(entity.getPaymentType())) {
					continue;
				}*/
				//BUG-47516 将原来根据付款方式判断修改为根据单据子类型判断只要是
				//到达应收、代收货款应收、到达偏线代理应收单、空运到达代理应收、空运代理代收货款应收在这种情况下都要进行红冲
				//Yang Shushuo 弃货添加快递代理代收货款和快递代理应收单据子类型
				if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(entity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.
						equals(entity.getBillType())
						||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
						.equals(entity.getBillType())
						||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
						.equals(entity.getBillType())
						||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
						.equals(entity.getBillType())
						||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
						.equals(entity.getBillType())
						||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
						.equals(entity.getBillType())
						){
					entity.setSourceBillNo(outStockExceptionEntity
							.getOutStockBillNo()); // 来源单据编号
					entity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__EXCEPTION);// 来源单据类型
					entity.setBusinessDate(outStockExceptionEntity.getCreateTime());// 业务时间

					// 红冲应收单
					billReceivableService.writeBackBillReceivable(entity, cInfo);
				}	
				
			}
		}
		
		// 6、如果该运单有对应的应付单信息，调用红冲应付单接口，红冲应付单
		if (CollectionUtils.isNotEmpty(billPayableList)) {
			
			//获取当前时间
			Date date = new Date();
			
			//循环处理应付单
			for (BillPayableEntity entity : billPayableList) {
				
				//如果应付单的类型为代收货款应付单，查询有效的代后货款，并作废该代收货款
				if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(entity.getBillType())){
					//如果运单号存在
					if(StringUtils.isNotEmpty(entity.getWaybillNo())){
						//根据运单号查询有效的代收货款
						CODEntity cod = codCommonService.queryByWaybill(entity.getWaybillNo());
						//如果代收货款存在
						if(cod!=null){
							cod.setActive(FossConstants.INACTIVE);//作废状态
							cod.setModifyTime(date);//修改时间
							cod.setModifyUserCode(cInfo.getEmpCode());//修改人编码
							cod.setModifyUserName(cInfo.getEmpName());//修改人名称
							codCommonService.updateCODCancelStatus(cod, cInfo);//作废代收货款
						}
					}
				}
				
				// 反写确认时间
				entity.setSignDate(date);
				billPayableService.updateBillPayableSignDateByConfirmIncome(entity, cInfo);
				// 其他地方还需要使用，为了保持java方法中的版本号和数据库中的版本号统一，在这里+1
				entity.setVersionNo((short) (entity.getVersionNo() + 1));
				
				entity.setSourceBillNo(outStockExceptionEntity
						.getOutStockBillNo()); // 来源单据编号
				entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__EXCEPTION);// 来源单据类型
				entity.setBusinessDate(outStockExceptionEntity.getCreateTime());// 业务时间

				// 红冲应付单
				billPayableService.writeBackBillPayable(entity, cInfo);
			}
		}
	}

	/**
	 * 校验运单号
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 上午9:56:40
	 */
	private void validateWaybillNo(String waybillNo, String expType) {

		// 晕单号为空，提示晕单号为空异常
		if (StringUtils.isEmpty(waybillNo)) {
			throw new SettlementException("运单编号为空");
		}

		// 校验异常类型不能为空
		if (StringUtils.isEmpty(expType)) {
			throw new SettlementException("异常类型为空");
		}

		// 如果标记状态不是丢货或弃货或违禁品时，提示异常
		if (!(SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__LOST_GOODS
				.equals(expType)
				|| SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__GIVE_UP_GOODS
						.equals(expType) || SettlementDictionaryConstants.OUT_STOCK_EXCEPTION__EXCEPTION_TYPE__CONTRABAND_GOODS
					.equals(expType))) {
			throw new SettlementException("运单的标记状态不是丢货或弃货或违禁品，不能进行异常出库操作");
		}
	}

	/**
	 * 查询应付单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 上午11:00:01
	 */
	private List<BillPayableEntity> queryBillPayable(String waybillNo) {

		// 创建应付单Dto
		BillPayableConditionDto billPayableDto = new BillPayableConditionDto();

		// 应付单必须为有效的
		billPayableDto.setActive(FossConstants.ACTIVE);

		// 应付单必须为非红单
		billPayableDto
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		// 应付单的运单号为传入运单号
		billPayableDto.setWaybillNo(waybillNo);

		// 应付单来源单号类型为为运单
		billPayableDto
				.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 异常出库红冲支持的应付单类型：装卸费应付、代收货款应付
		String[] billTypes = new String[2];
		billTypes[0] = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE;
		billTypes[1] = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD;
		billPayableDto.setBillTypes(billTypes);

		// 查询，并返回应付单列表
		return billPayableService.queryBillPayableByCondition(billPayableDto);
	}

	/**
	 * 查询应收单
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-11-8 上午11:04:30
	 */
	private List<BillReceivableEntity> queryBillReceivable(String waybillNo) {

		// 创建应收单Dto
		BillReceivableConditionDto billReceivableDto = new BillReceivableConditionDto();

		// 应收单必须为有效的
		billReceivableDto.setActive(FossConstants.ACTIVE);

		// 应收单必须为非红单
		billReceivableDto
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		// 应收单的运单号为传入运单号
		billReceivableDto.setWaybillNo(waybillNo);

		// 应收单来源单号类型为为运单
		billReceivableDto
				.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 异常出库红冲支持的应收单类型：到达应收、代收货款应收、空运代理代收货款应收、到达偏线代理应收单、空运到达代理应收
		String[] billTypes = new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE // 始发应收反写确认时间
		};
		billReceivableDto.setBillTypes(billTypes);

		// 查询，并返回应收单列表
		return billReceivableService
				.queryBillReceivableByCondition(billReceivableDto);
	}

	/**
	 * 新增异常出库信息
	 * 
	 * @author foss-qiaolifeng
	 * @date 2012-12-17 下午4:24:37
	 */
	private OutStockExceptionEntity addWarehouseException(String waybillNo,
			String expType, CurrentInfo cInfo) {

		Date now = new Date();

		// 生产异常出库实体
		OutStockExceptionEntity entity = new OutStockExceptionEntity();

		// 异常出库实体赋值
		entity.setId(UUIDUtils.getUUID());
		entity.setOutStockBillNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YCCK));
		entity.setWaybillNo(waybillNo);
		entity.setExceptionType(expType);
		entity.setCreateUserCode(cInfo.getEmpCode());
		entity.setCreateUserName(cInfo.getEmpName());
		entity.setCreateTime(now);

		// 保存异常出库实体
		int i = outWarehouseExceptionDao.addOutWarehouseException(entity);
		if (i != 1) {
			throw new SettlementException("保存异常出库信息失败");
		}

		return entity;
	}

	/**
	 * @return billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @return billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @return outWarehouseExceptionDao
	 */
	public IOutWarehouseExceptionDao getOutWarehouseExceptionDao() {
		return outWarehouseExceptionDao;
	}

	/**
	 * @param outWarehouseExceptionDao
	 */
	public void setOutWarehouseExceptionDao(
			IOutWarehouseExceptionDao outWarehouseExceptionDao) {
		this.outWarehouseExceptionDao = outWarehouseExceptionDao;
	}

	
	/**
	 * @get
	 * @return codCommonService
	 */
	public ICodCommonService getCodCommonService() {
		/*
		 * @get
		 * @return codCommonService
		 */
		return codCommonService;
	}

	
	/**
	 * @set
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		/*
		 *@set
		 *@this.codCommonService = codCommonService
		 */
		this.codCommonService = codCommonService;
	}

	/**
	 * @param billCashCollectionService the billCashCollectionService to set
	 */
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	/*public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}*/

}
