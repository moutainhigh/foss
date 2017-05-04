package com.deppon.foss.module.settlement.consumer.server.service.impl;


import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustInvoiceMarkQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CustInvoiceMarkEntity;
import com.deppon.foss.util.define.FossConstants;

public class CustInvoiceMarkQueryService implements ICustInvoiceMarkQueryService {

	/**
	 * 日志
	 */
	public static final Logger logger = LogManager.getLogger(CustInvoiceMarkQueryService.class);

	/**
	 * 综合接口 查询发票标记
	 */
	/* 此接口已经作废 */
//	private ICusContractTaxService cusContractTaxService;
	private ICustomerService customerService;
	/** 
	 * 注入组织管理服务.
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/** 
	 * 注入客户合同信息服务
	 */
	private ICusBargainService cusBargainService;
	
	/**
	 * <p>
	 * 返回客户发票标记实体
	 * </p>
	 * 
	 * @author Yang Shushuo
	 * @date 2013-11-19 下午4:32:50
	 * @param custNumber
	 * @return CustInvoiceMarkEntity
	 */
	@Override
	public CustInvoiceMarkEntity queryCustInvoiceMarkByCustNum(String custNumber) {
		// 调用综合接口须传递的参数
		//CustomerQueryConditionDto dto = new CustomerQueryConditionDto();
		// 创建返回实体
		CustInvoiceMarkEntity en = new CustInvoiceMarkEntity();
		//合同部门
		OrgAdministrativeInfoEntity contractOrgEntity = null;
		//催款部门
		OrgAdministrativeInfoEntity dunningOrgEntity = null;
		// 设置参数
		//dto.setCustCode(custNumber);
		// 查询发票标记
		CustomerDto entity = customerService.queryCustInfoByCode(custNumber);
		//查询统一结算客户
		CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainByCustCode(custNumber);
		if(null != cusBargainEntity){
			//统一结算
			if(StringUtils.isNotBlank(cusBargainEntity.getAsyntakegoodsCode())){
				en.setUnifiedSettlement(cusBargainEntity.getAsyntakegoodsCode());
				if(FossConstants.YES.equals(cusBargainEntity.getAsyntakegoodsCode()))
				{
					//判断合同编码是否为空
					if(StringUtils.isNotBlank(cusBargainEntity.getUnifiedCode())){
						//通过编码查询部门信息
						contractOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(cusBargainEntity.getUnifiedCode());
					}
					//判断催款编码是否为空
					if(StringUtils.isNotBlank(cusBargainEntity.getHastenfunddeptCode())){
						//通过编码查询部门信息
						dunningOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCodeNoCache(cusBargainEntity.getHastenfunddeptCode());
					}
					//合同部门
					if(null != contractOrgEntity && StringUtils.isNotBlank(contractOrgEntity.getCode())){
						en.setContractOrgCode(contractOrgEntity.getCode());
						en.setContractOrgName(contractOrgEntity.getName());
					}
					//催款部门
					if(null != dunningOrgEntity && StringUtils.isNotBlank(dunningOrgEntity.getCode())){
						en.setDunningOrgCode(dunningOrgEntity.getCode());
						en.setDunningOrgName(dunningOrgEntity.getName());
					}
				}else{
					en.setContractOrgCode(null);
					en.setContractOrgName(null);
					en.setDunningOrgCode(null);
					en.setDunningOrgName(null);
				}
			}
		}else{
			en.setUnifiedSettlement(FossConstants.NO);
			en.setContractOrgCode(null);
			en.setContractOrgName(null);
			en.setDunningOrgCode(null);
			en.setDunningOrgName(null);
		}
		
		// 设置返回客户编码
		en.setCustNumber(custNumber);
		// 设置发票标记，为空则设置为 02-非运输专票
		if (null == entity || null == entity.getInvoiceType() || "".equals(entity.getInvoiceType())) {
			en.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		// 发票标记非01 02 则设置为02-非运输专票
		} else if (!SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO.equals(entity.getInvoiceType())
				&& !SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE.equals(entity.getInvoiceType())) {
			en.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		} else {
			en.setInvoiceMark(entity.getInvoiceType());
		}
		return en;
	}

	/* setters & getters */
//	public void setCusContractTaxService(ICusContractTaxService cusContractTaxService) {
//		this.cusContractTaxService = cusContractTaxService;
//	}

	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
