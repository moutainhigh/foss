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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/deliver/server/service/impl/ValidateArriveSheetService.java
 * 
 * FILE NAME        	: ValidateArriveSheetService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.deliver.server.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IValidateArriveSheetService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.ValidateArriveSheetDto;
import com.deppon.foss.module.pickup.pda.api.shared.exception.PdaProcessException;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IArrivesheetDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.NotifyCustomerConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ArriveSheetDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.SignDto;
import com.deppon.foss.module.pickup.sign.api.server.service.IExpSignService;
import com.deppon.foss.module.pickup.sign.api.server.service.IRepaymentService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignChangeService;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.RepaymentEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.SignException;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.shared.define.ExpWaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 校验到达联接口
 * 
 * @author 097972-foss-dengtingting
 */
public class ValidateArriveSheetService implements IValidateArriveSheetService {
	// LOGGER
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValidateArriveSheetService.class);
	// 货款结清Service
	private IRepaymentService repaymentService;
	// 到达联DAO
	private IArrivesheetDao arrivesheetDao;
	private ISignService signService;
	/**
	 * 营业部 Service实现
	 */
	private ISaleDepartmentService saleDepartmentService;
	/**
	 * 部门复杂service
	 */
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	/**
	 * 签收变更结果接口
	 */
	private ISignChangeService signChangeService;
	/**
	 * 快递运单service
	 */
	private IWaybillExpressService waybillExpressService;
	/**
	 * 快递签收接口
	 */
	private IExpSignService expSignService;
	
	@Override
	public ValidateArriveSheetDto validateArriveSheet(String arriveSheetNo,String currentOrgCode){
		
		if(null ==arriveSheetNo || arriveSheetNo.trim().equals("") ){
			throw new PdaProcessException("请输入到达联编号！");
		}
		ArriveSheetDto condition = new ArriveSheetDto();
		condition.setArrivesheetNo(arriveSheetNo);
		condition.setActive(FossConstants.ACTIVE);
		condition.setStatus(ArriveSheetConstants.STATUS_GENERATE);
		condition.setDestroyed(FossConstants.NO);
		// 查询到达联信息
		ValidateArriveSheetDto result = arrivesheetDao.queryWaybillByArriveSheetNo(condition);
		if (result == null){
			// 返回到达联无效
			throw new PdaProcessException("无效的到达联！");
		}
		//家装类运单限制进行PDA签收   add by 243921
		if(StringUtils.isNotBlank(result.getSpecialValueAddedService()) &&
				result.getSpecialValueAddedService().contains(NotifyCustomerConstants.DELIVER_EQUIP)){
			//属于家装类运单
			throw new PdaProcessException("德邦家装类运单，不允许使用PDA签收!");
		}
		//add by chenjunying 231438 2015-02-05 ----
		//判断运单为快递 ，限制返货新单的原单反签收
		if(ExpWaybillConstants.directDetermineIsExpressByProductCode(result.getProductCode())){
			LOGGER.info("验证运单是否有返货有效新单开始......");
			WaybillExpressEntity expEntity = waybillExpressService.queryWaybillByOriginalWaybillNo(
					result.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
			if(expEntity != null){ //有返货新单
				//原单查出的返货新单 有未作废的
				LOGGER.info("运单存在有效返货新单，不允许原单签收");
				throw new PdaProcessException("该运单存在有效返货新单，不允许原单签收");
			}
			//为新单，或原单无有效返货新单 允许当前单号反签收
			LOGGER.info("验证运单是否有返货有效新单结束......");
		}//----

		try {
			signChangeService.checkWayBillRfcStatus(result.getWaybillNo());//验证运单签收变更、反签收申请情况
		} catch (SignException e) {
			throw new PdaProcessException("该运单有到达更改单正在审核中，不能签收！");
		}
		SaleDepartmentEntity saleDepartment = saleDepartmentService.querySaleDepartmentByCode(currentOrgCode);
		if (saleDepartment == null ) {
			// 判断 最终配载部门 是否驻地部门
			SaleDepartmentEntity saleDepar = saleDepartmentService.querySaleDepartmentByCode(result.getCustomerPickupOrgCode());
			// 是否驻地部门
			if (saleDepar != null && saleDepar.checkStation()) {
				String endStockOrgCode = saleDepar.getTransferCenter();//得到驻地部门外场
				//非营业部找到它上级所属外场的编码
				List<String> bizTypes = new ArrayList<String>();
				bizTypes.add(BizTypeConstants.ORG_TRANSFER_CENTER);
				OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoIncludeSelfByCode(currentOrgCode, bizTypes);
				if(orgAdministrativeInfoEntity != null){
					if(!StringUtils.equals(endStockOrgCode, orgAdministrativeInfoEntity.getCode())){
						throw new PdaProcessException("操作部门与运单的最终库存部门不一致,不能进行签收操作");
					}
				}else{
					throw new PdaProcessException("操作部门与运单的最终库存部门不一致,不能进行签收操作");
				}
			} else {
				throw new PdaProcessException("操作部门与运单的最终配载部门不一致,不能进行签收操作");
			}
		} else {//如果不为空，为营业部
			if(!currentOrgCode.equals(result.getCustomerPickupOrgCode())){//如果签收部门与运单的最终配载部门不一致
				throw new PdaProcessException("操作部门与运单的最终配载部门不一致,不能进行签收操作");
			}
		}
		RepaymentEntity repayment = new RepaymentEntity();
		repayment.setWaybillNo(result.getWaybillNo());
		//PDA结清     243921
		repayment.setSettleApproach(SettlementDictionaryConstants.SETTLE_APPROACH_MOBILE);
		// 校验该运单是否结清货款
		Boolean ispay = repaymentService.paymentOperate(repayment);
		if (!ispay){
			// 返回货款未结清
			throw new PdaProcessException("该到达联未结清货款，不能出库！");
		}
		SignDto dto = new SignDto();
		dto.setWaybillNo(result.getWaybillNo()); //运单号
		dto.setLastLoadOrgCode(result.getCustomerPickupOrgCode());//提货网点CODE
		//零担、快递 DMANA-9499 PDA自提签收库区显示在快递驻地库区
		//快递
		if(WaybillConstants.directDetermineIsExpressByProductCode(result.getProductCode())){
			List<StockDto> stockDto =expSignService.queryStock(dto);
			List<String> serialNos =new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(stockDto)){
				for (StockDto stockDto2 : stockDto) {
					serialNos.add(stockDto2.getSerialNo());
				}
				result.setSerialNos(serialNos);
			}
		}else{
		//零担 
			List<StockDto> stockDto =signService.queryStock(dto);
			List<String> serialNos =new ArrayList<String>();
			if(CollectionUtils.isNotEmpty(stockDto)){
				for (StockDto stockDto2 : stockDto) {
					serialNos.add(stockDto2.getSerialNo());
				}
				result.setSerialNos(serialNos);
			}
		}

		return result;
	}
	public void setRepaymentService(IRepaymentService repaymentService) {
		this.repaymentService = repaymentService;
	}

	public void setArrivesheetDao(IArrivesheetDao arrivesheetDao) {
		this.arrivesheetDao = arrivesheetDao;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setSignService(ISignService signService) {
		this.signService = signService;
	}
	public void setSignChangeService(ISignChangeService signChangeService) {
		this.signChangeService = signChangeService;
	}
	/**
	 * <p>注入<br />
	 * @author chenjunying
	 * @version 0.1 2015-02-03
	 * @param waybillExpressService
	 * void
	 */
	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}
	public void setExpSignService(IExpSignService expSignService) {
		this.expSignService = expSignService;
	}

}