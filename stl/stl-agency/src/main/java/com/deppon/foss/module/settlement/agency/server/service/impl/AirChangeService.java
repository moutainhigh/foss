package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirChangeService;
import com.deppon.foss.module.settlement.agency.api.shared.domain.AirChangeEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 变更清单服务
 * @author Foss-chenmingchun
 * @date 2013-4-11 下午3:17:14
 */
public class AirChangeService implements IAirChangeService {

	private static final Logger LOGGER = LogManager.getLogger(AirChangeService.class);

	/**
	 * 结算通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 组织信息服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 接送货运单服务
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 * 修改变更清单明细
	 * 修改时，中转开发人员需要注意：
	 * 修改送货费时，修改明细放在modifiedDetails
	 * @author 092036-Foss-bochenlong
	 * @date 修改于2013-07-02  下午15:28:00
	 * @param addedDetails 新增明细
	 * @param modifiedDetails 修改明细（不使用）
	 * @param removedDetails 删除明细（运单集合）（不使用）
	 */
	@Override
	public void modifyAirChangeDetail(List<AirChangeEntity> addedDetails,
			List<AirChangeEntity> modifiedDetails,
			List<AirChangeEntity> removedDetails, CurrentInfo currentInfo) {

		LOGGER.info("开始修改变更清单服务");

		// 修改明细不能为空
		if (CollectionUtils.isEmpty(modifiedDetails)) {
			throw new SettlementException("修改变更清单明细，输入的参数不能为空！");
		}

		List<BillPayableEntity> billPayableEntityList = new ArrayList<BillPayableEntity>();
		for (int i = 0; i < modifiedDetails.size(); i++) {
			BillPayableEntity billPayableEntity = new BillPayableEntity();
			billPayableEntity.setSourceBillNo(modifiedDetails.get(i).getWaybillNo());
			billPayableEntity.setCustomerCode(modifiedDetails.get(i).getAgentCompanyCode());
			billPayableEntity.setSourceBillType(modifiedDetails.get(i).getType());
			billPayableEntityList.add(billPayableEntity);
		}

		// 根据运单号集合和空运代理编码， 查询原有空运到达应付单
		if (CollectionUtils.isNotEmpty(billPayableEntityList)) {
			List<BillPayableEntity> list = billPayableService
					.selectBySourceBillNOsAndCustomerCodeAndBillType(
							billPayableEntityList, FossConstants.ACTIVE);
			// 原来的数据有可能为空
			if (CollectionUtils.isNotEmpty(list)) {

				// 循环红冲该合票清单中的运单号对应的应付代理送货费
				for (BillPayableEntity entity : list) {
					// 验证应付单信息
					validatePayable(entity);
					// BUG-41547 092036-foss-bochenlong
					// 增加是否签收校验，如果签收，则不允许做合大票
					if(entity.getSignDate() != null) {
						throw new SettlementException("运单已经签收，不能修改合大票");
					}
					// 红冲应付单
					billPayableService
							.writeBackBillPayable(entity, currentInfo);
				}
			}
		}

		// 生成蓝单
		if (CollectionUtils.isNotEmpty(modifiedDetails)) {
			// 循环明细集合
			for (AirChangeEntity entity : modifiedDetails) {
				if (entity.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
					// 生成合票送货费应付单
					BillPayableEntity blueEntity = this.getBillPayable(entity,
							currentInfo);
					this.billPayableService.addBillPayable(blueEntity,
							currentInfo);
				}
			}
		}

		LOGGER.info("结束修改空运变更清单");

	}
	
	
	
	/**
	 * 增加红冲应付单验证
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午6:58:18
	 * @param entity
	 */
	private void  validatePayable(BillPayableEntity entity){
		if(entity!=null&&StringUtils.isNotEmpty(entity.getId())){
			//空运成本应付单已审核，不能进行修改空运变更清单操作！
			if(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())){
				throw new SettlementException("空运成本应付单已审核，不能进行修改空运变更清单操作！");
			}
			//空运成本应付单已付款，不能进行修改空运变更清单操作！
			if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity.getPayStatus())){
				throw new SettlementException("空运成本应付单已付款，不能进行修改空运变更清单操作！");
			}
		}
	}
	
	
	

	
	
	/**
	 * 根据空运变更清单生成应付单
	 * @author 110485-foss-chenmingchun
	 * @date 2013-4-16 下午8:32:46
	 * @param detail 变更清单明细
	 * @param currentInfo 当前用户信息
	 * @return 应付单信息
	 */
	private BillPayableEntity getBillPayable(AirChangeEntity detail, CurrentInfo currentInfo) {
		//获取系统当前时间
		Date now = Calendar.getInstance().getTime();
		//声明实例应付单
		BillPayableEntity entity = new BillPayableEntity();

		// ID,应付单号,运单号,运单ID,生成方式,付款方
		entity.setId(UUIDUtils.getUUID());
		//设置应付单号
		entity.setPayableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF91));
		//设置运单号
		entity.setWaybillNo(detail.getWaybillNo());
		//设置运单id
		entity.setWaybillId(null);
		//设置生成方式
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__DESTINATION);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		//设置审批状态
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		//设置应付类型
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
		//设置来源单据编码
		entity.setSourceBillNo(detail.getWaybillNo());	// 运单号
		//设置来源单据类型
		entity.setSourceBillType(detail.getType());
		//设置付款状态
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		//设置应付部门编码
		entity.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		//设置应付部门名称
		entity.setPayableOrgName(currentInfo.getCurrentDeptName());
		//根据编码查询获取组织详细信息
		OrgAdministrativeInfoEntity origOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());	
		if (origOrgEntity != null) {
			//应付子公司编码
			entity.setPayableComCode(origOrgEntity.getSubsidiaryCode());
			//应付子公司名称
			entity.setPayableComName(origOrgEntity.getSubsidiaryName());
		}

		// 设置出发部门编码
		entity.setOrigOrgCode(waybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getReceiveOrgCode());
		// 设置出发部门名称
		entity.setOrigOrgName(waybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getReceiveOrgName());

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		entity.setDestOrgName(currentInfo.getCurrentDeptName());

		
		// 设置应付客户编码
		entity.setCustomerCode(detail.getAgentCompanyCode());
		// 设置应付客户名称
		entity.setCustomerName(detail.getAgentCompanyName());

		// 设置金额
		entity.setAmount(detail.getDeliverFee());
		// 设置已核销金额
		entity.setVerifyAmount(BigDecimal.ZERO);
		// 设置未核销金额
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 设置会计日期
		entity.setAccountDate(now);
		// 设置业务日期
		entity.setBusinessDate(detail.getCreateTime());

		// 设置生效日期
		entity.setEffectiveDate(null);

		// 是否有效
		entity.setActive(FossConstants.YES);
		// 是否红单
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 是否初始化
		entity.setIsInit(FossConstants.NO);
		// 版本号
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		//创建时间
		entity.setCreateTime(now);
		//修改时间
		entity.setModifyTime(now);
		
		/*
		 * 税务改造需求
		 * 
		 * 合票送货费应付单 为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		
		return entity;
	}
	
	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}
	
	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	
}
