package com.deppon.foss.module.settlement.agency.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.server.service.ITruckStowageService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.StlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.ITruckArriveConfirmService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.TruckArriveConfirmEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.TruckArriveConfirmDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.load.api.shared.define.LoadConstants;
import com.deppon.foss.module.transfer.load.api.shared.define.OutsideVehicleChargeConstants;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 整车/外请车配载服务
 * 
 * 配载类型常量和车辆所有权类别 、奖罚类型、部门经理审核状态 是引用中转（transfer.load.api）中定义常量
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-28 下午2:37:07
 * @since
 * @version
 */
public class TruckStowageService implements ITruckStowageService {

	private static final Logger LOGGER = LogManager
			.getLogger(TruckStowageService.class);

	/**
	 * 应付单通用Service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 结算通用Common Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入组织信息接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 注入外请车到达反到达实例
	 */
	private ITruckArriveConfirmService  truckArriveConfirmService;

	/**
	 * 公共验证参数方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-21 下午4:45:10
	 * @param dto
	 */
	private void validatePubParams(StlVehicleAssembleBillDto dto) {
		//配载车次号不能为空
		if (StringUtils.isEmpty(dto.getVehicleAssembleNo())) {
			throw new SettlementException("配载车次号不能为空");
		}
		//配载类型不能为空
		if (StringUtils.isEmpty(dto.getAssembleType())) {//整车暂时定义也可以调整尾款费用
			throw new SettlementException("配载类型不能为空");
		}
		//出发部门编码不能为空
		if (StringUtils.isEmpty(dto.getOrigOrgCode())) {
			throw new SettlementException("出发部门编码不能为空");
		}
		//到达部门编码不能为空
		if (StringUtils.isEmpty(dto.getDestOrgCode())) {
			throw new SettlementException("到达部门编码不能为空");
		}
	}
	
	/**
	 * 验证接口传入的参数信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午3:44:16
	 * @param dto
	 */
	private void validateParams(StlVehicleAssembleBillDto dto) {
		validatePubParams(dto);
		
		// 整车运单号不能为空
		if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD.equals(dto
				.getAssembleType())&&StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("整车配载单，运单号不能为空为空");
		}
		//车牌号不能为空
		if (StringUtils.isEmpty(dto.getVehicleNo())) {
			throw new SettlementException("车牌号不能为空");
		}
		//车辆所有权类别不能为空
		if (StringUtils.isEmpty(dto.getVehicleOwnerShip())) {
			throw new SettlementException("车辆所有权类别不能为空");
		}else{
			//LoadConstants.VEHICLE_OWNERSHIP_OWNER_VEHICLE 为公司自有车，为公司内部车辆抛出异常信息
			if(LoadConstants.VEHICLE_OWNERSHIP_OWNER_VEHICLE.equals(dto.getVehicleOwnerShip())){
				throw new SettlementException("车辆所有权类别不能为公司自有车！");
			}
		}
		//付款方式不能为空
		if (StringUtils.isEmpty(dto.getPaymentType())) {
			throw new SettlementException("付款方式不能为空");
		}
		//总费用不能为空
		if (dto.getFeeTotal() == null) {
			throw new SettlementException("总费用不能为空");
		}
		//输入的总运费必须大于0
		if (dto.getFeeTotal().compareTo(BigDecimal.ZERO) <=0) {
			throw new SettlementException("输入的总运费必须大于0");
		}
		//是否有押回单不能为空
		if (StringUtils.isEmpty(dto.getBeReturnReceipt())) {
			throw new SettlementException("是否有押回单不能为空");
		}
		
		validaExtracted(dto);
	}

	private void validaExtracted(StlVehicleAssembleBillDto dto) {
		//付款方式为现金，判断总运费=预付运费总额信息和到付运费总额
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
				.equals(dto.getPaymentType())
				||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(dto.getPaymentType())) {
			if(dto.getFeeTotal().compareTo(NumberUtils.add(dto.getPrePaidFeeTotal(),
					dto.getArriveFeeTotal())) != 0){
				throw new SettlementException("输入的总运费和预付运费总额信息和到付运费总额不相同");
			}
			
			//针对现金支付的，预付总额和到付总额不能同时为空
			if (dto.getPrePaidFeeTotal() == null && dto.getArriveFeeTotal() == null) {
				throw new SettlementException("预付运费总额信息和到付运费总额不能同时为空");
			}
		}
		
		//因为中转，付款方式为月结时，预付总额和到付总额默认为0，这里设置小于0的情况进行提示
		if (dto.getPrePaidFeeTotal() != null
				&& dto.getPrePaidFeeTotal().compareTo(BigDecimal.ZERO) <0) {
			throw new SettlementException("输入的预付运费总额必须大于0");
		}
		if (dto.getPrePaidFeeTotal() != null
				&& dto.getPrePaidFeeTotal().compareTo(dto.getFeeTotal()) > 0) {
			throw new SettlementException("输入的预付运费总额："
					+ dto.getPrePaidFeeTotal() + "大于总运费：" + dto.getFeeTotal());
		}
		//输入的到付运费总额必须大于0
		if (dto.getArriveFeeTotal() != null
				&& dto.getArriveFeeTotal().compareTo(BigDecimal.ZERO) <0) {
			throw new SettlementException("输入的到付运费总额必须大于0");
		}
		if (dto.getArriveFeeTotal() != null
				&& dto.getArriveFeeTotal().compareTo(dto.getFeeTotal()) > 0) {
			throw new SettlementException("输入的到付运费总额："
					+ dto.getArriveFeeTotal() + "大于总运费：" + dto.getFeeTotal());
		}
	}

	/**
	 * 录入配载单
	 * 【
	 * 配载车次号：vehicleAssembleNo
	 * 运单号：waybillNo(整车时需要)，
	 * 配载类型：assembleType；
	 * 出发部门编码:origOrgCode；
	 * 出发部门名称：origOrgName；
	 * 到达部门编码：destOrgCode；
	 * 到达部门名称：destOrgName；
	 * 车牌号:vehicleNo；
	 * 车辆所有权类别：vehicleOwnerShip；
	 * 司机姓名：driverName；
	 * 司机编码：driverCode
	 * 付款方式 ：paymentType
	 * 总运费：feeTotal
	 * 预付运费总额：prePaidFeeTotal
	 * 到付运费总额：arriveFeeTotal；
	 * 是否押回单：beReturnReceipt；
	 * 币种：currencyCode；
	 * 】
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午2:37:21
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void addTruckStowage(StlVehicleAssembleBillDto dto,
			CurrentInfo currentInfo) {
		// 配载单车辆性质---车辆所有权类别
		if (dto == null) {
			throw new SettlementException("新增配载单传入参数不能为空");
		}
		
		LOGGER.info("STL 开始录入配载单服务，配载车次号为："
				+ dto.getVehicleAssembleNo()
				+ (StringUtils.isNotEmpty(dto.getWaybillNo()) ? ("    运单号为：" + dto
						.getWaybillNo()) : "") + "  司机编码为： "
				+ dto.getDriverCode());
		
		this.validateParams(dto);

		// 校验是否存在相同记录的应付单
		List<BillPayableEntity> list = this.queryBillPyableBySourceBillNOs(dto);
		if (CollectionUtils.isNotEmpty(list)) {
			throw new SettlementException("系统中已存在汽运配载应付单");
		}

		// 代表首款类型
		String firstBillType = "";
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
				.equals(dto.getPaymentType())
				||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(dto.getPaymentType())) {// 为现金时才会有首款应付单
			firstBillType = this.getPayableBillType(dto,
							SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST,null);
		}
		// 代表尾款类型
		String lastBillType = this.getPayableBillType(dto,
				SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST,firstBillType);

		//配载单输入的数据不完整
		if (StringUtils.isEmpty(firstBillType)
				&& StringUtils.isEmpty(lastBillType)) {
			throw new SettlementException("配载单输入的数据不完整");
		}
		Date date = new Date();
		if (StringUtils.isNotEmpty(firstBillType)) {
			BillPayableEntity firstEntity = this.getBillPayableEntity(dto,
					firstBillType, date, currentInfo);
			// 保存首款应付单
			this.billPayableService.addBillPayable(firstEntity, currentInfo);
		}
		if (StringUtils.isNotEmpty(lastBillType)) {
			BillPayableEntity lastEntity = this.getBillPayableEntity(dto,
					lastBillType, date, currentInfo);
			// 保存尾款应付单
			this.billPayableService.addBillPayable(lastEntity, currentInfo);
		}
		
		LOGGER.info(" STL 录入配载单服务成功！ ");
	}

	/**
	 * 获取整车/外请车首款应付单类型
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-29 下午7:49:20
	 * @param dto
	 * @param payableType
	 * @param firstBillType
	 * @return
	 */
	private String getPayableBillType(StlVehicleAssembleBillDto dto,
			String payableType, String firstBillType) {
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
				.equals(dto.getPaymentType())||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(dto.getPaymentType())) {

			// 获取方式为首款和预付金额大于0的
			if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST
					.equals(payableType)
					&& dto.getPrePaidFeeTotal() != null
					&& dto.getPrePaidFeeTotal().compareTo(BigDecimal.ZERO) > 0) {
				
				// assembleType配载类型
				if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD.equals(dto
						.getAssembleType())) {
					return SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST;// 整车首款
				} else if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE.equals(dto
						.getAssembleType())) {
					return SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST;// 外请车首款
				}
				
			}else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST
					.equals(payableType)
					&& dto.getArriveFeeTotal() != null
					&& dto.getArriveFeeTotal().compareTo(BigDecimal.ZERO) > 0) {
				// 获取应付方式为尾款和到付运费金额大于0的
				//获取整车/外请车尾款应付单类型
				return getLastBillPayableType(dto);
			}
		}
		else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())
				) {
			if (StringUtils.isNotEmpty(firstBillType)) {
				throw new SettlementException("付款方式为月结时，只能存在一个应付单");
			}
			//获取整车/外请车尾款应付单类型
			return this.getLastBillPayableType(dto);
		}
		return "";
	}
	
	/**
	 * 获取整车/外请车尾款应付单类型
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-3 下午2:45:04
	 * @param dto
	 * @return
	 */
	private String getLastBillPayableType(StlVehicleAssembleBillDto dto){
		// assembleType配载类型
		if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD.equals(dto
				.getAssembleType())) {
			// 整车尾款
			return SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
		}
		else if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE.equals(dto
				.getAssembleType())) {
			// 外请车尾款
			return SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST;
		}
		return "";
	}

	/**
	 * 根据传入的参数和单据类型设置应付单属性值
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-29 下午3:38:20
	 * @param dto
	 * @param billType
	 * @param date
	 * @param currentInfo
	 */
	private BillPayableEntity getBillPayableEntity(
			StlVehicleAssembleBillDto dto, String billType, Date date,
			CurrentInfo currentInfo) {
		BillPayableEntity entity = new BillPayableEntity();

		// ID
		entity.setId(UUIDUtils.getUUID());

		// 配置类型为：整车时，设置运单号属性值
		if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD.equals(dto.getAssembleType())) {
			entity.setWaybillNo(dto.getWaybillNo());
		}

		// 付款单号N/A
		entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

		// 系统生成方式
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		// 配载车次号
		entity.setSourceBillNo(dto.getVehicleAssembleNo());

		// 来源单据类型
		entity.setSourceBillType(SettlementDictionaryConstants.
				BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE);

		// 出发部门编码
		entity.setOrigOrgCode(dto.getOrigOrgCode());

		// 出发部门名称
		entity.setOrigOrgName(dto.getOrigOrgName());

		// 到达部门编码
		entity.setDestOrgCode(dto.getDestOrgCode());

		// 到达部门编码
		entity.setDestOrgName(dto.getDestOrgName());
		
		
        //add by 309603 yangqiang
        if (dto.getOrgCode()!=null){//如果为组织或者
            // 司机编码
            entity.setCustomerCode(dto.getOrgCode());
            // 司机名称
            entity.setCustomerName(dto.getOrgName());
            
    		/**
    		 * 客户联系人姓名@author 340403
    		 */
    		entity.setCustomerContactName(dto.getDriverName());
    		
    		/**
    		 * 客户联系电话@author 340403
    		 */
    		entity.setCustomerPhone(dto.getDriverPhone());
        }else{
        	
		// 司机编码
		entity.setCustomerCode(dto.getDriverCode());

		// 司机名称
		entity.setCustomerName(dto.getDriverName());
		/**
		 * 客户联系人姓名@author 340403
		 */
		entity.setCustomerContactName(dto.getDriverName());
		
		/**
		 * 客户联系电话@author 340403
		 */
		entity.setCustomerPhone(dto.getDriverPhone());
        }

		// 单据类型
		entity.setBillType(billType);
		
		//2013-02-21日，需求变更配载类型为长途外请车时(非整车)，生成的应付单的生效状态默认为：已生效
		//整车首款默认为：已生效，只有整车尾款不生效
		if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE.equals(dto
				.getAssembleType()) ||
		// 整车首款默认生效
				(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD.equals(dto
						.getAssembleType()) 
						&& SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST
						.equals(billType))) {

			// 生效时间
			entity.setEffectiveDate(date);
			
			// 生效状态-已生效
			entity.setEffectiveStatus(SettlementDictionaryConstants.
					BILL_PAYABLE__EFFECTIVE_STATUS__YES);
			
			// 生效人编码
			entity.setEffectiveUserCode(currentInfo.getEmpCode());

			// 生效人名称
			entity.setEffectiveUserName(currentInfo.getEmpName());
		}else{
			
			// 整车尾款，默认不生效。生效状态-默认设置为未生效
			entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		}
		
		// 首款
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST
						.equals(billType)) {
			// 应付单号
			entity.setPayableNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF61));

			// 金额
			entity.setAmount(dto.getPrePaidFeeTotal());

			// 应付类型-首款
			if(FossConstants.YES.equals(dto.getBeReturnReceipt())){
				entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK);
			}else{
				entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST);
			}
			

			// 首款出发部门支付 应付部门编码-出发部门编码
			entity.setPayableOrgCode(dto.getOrigOrgCode());

			// 应付部门名称-出发部门名称
			entity.setPayableOrgName(dto.getOrigOrgName());
		} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST
						.equals(billType)) {
			
			// 尾款
			// 应付单号
			entity.setPayableNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF62));

			// 配载单-到付运费总额
			entity.setAmount(dto.getArriveFeeTotal());

			
			// 应付类型-尾款
						if(FossConstants.YES.equals(dto.getBeReturnReceipt())){
							entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK);
						}else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())){
							entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH);
						}else{
							entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST);
						}
			

			// 付款方式为月结的，应付部门为出发部门
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())	
					//有押回单的，尾款也挂出发部门
					||FossConstants.YES.equals(dto.getBeReturnReceipt())
					) {
				
				// 应付部门编码
				entity.setPayableOrgCode(dto.getOrigOrgCode());//-出发部门编码

				// 应付部门名称
				entity.setPayableOrgName(dto.getOrigOrgName());//-出发部门名称
			} else {
				// 应付部门编码
				entity.setPayableOrgCode(dto.getDestOrgCode());//-到达部门编码

				// 应付部门名称
				entity.setPayableOrgName(dto.getDestOrgName());//-到达部门名称
			}
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())
					){
				// 总费用
				entity.setAmount(dto.getFeeTotal());
			}
		}
		
		//设置应付公司  --毛建强 2013-4-8
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPayableOrgCode());
		//如果部门为空，则抛出异常
		if(orgEntity!=null){
			//设置部门编码和名称
			entity.setPayableComCode(orgEntity.getSubsidiaryCode());
			//设置部门名称
			entity.setPayableComName(orgEntity.getSubsidiaryName());
		}
		
		// 已核销金额
		entity.setVerifyAmount(BigDecimal.ZERO);
		
		// 未核销金额
		entity.setUnverifyAmount(entity.getAmount());
		
		// 币种
		entity.setCurrencyCode(dto.getCurrencyCode());
		
		// 记账日期
		entity.setAccountDate(date);
		
		// 业务日期
		entity.setBusinessDate(date);
		
		// 版本号
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		// 是否有效
		entity.setActive(FossConstants.ACTIVE);
		
		// 是否红单
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		// 是否初始化
		entity.setIsInit(FossConstants.NO);
		
		// 冻结状态
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		
		// 支付状态
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);

		// 对账单号,默认为N/A
		entity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 创建时间
		entity.setCreateTime(date);
		
		// 修改时间
		entity.setModifyDate(date);

			// 司机编码
		entity.setLgdriverCode(dto.getDriverCode());
		
		// 司机名称
		entity.setLgdriverName(dto.getDriverName());
		
		// 审批状态-已审核
		entity.setApproveStatus(SettlementDictionaryConstants.
				BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		
		/*
		 * 税务改造需求
		 * 
		 * 外请车首尾款应付单发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		
		return entity;
	}

	/**
	 * 修改配载单 
	 * 【
	 * 配载车次号：vehicleAssembleNo
	 * 运单号：waybillNo(整车时需要)，
	 * 配载类型：assembleType；
	 * 出发部门编码:origOrgCode；
	 * 出发部门名称：origOrgName；
	 * 到达部门编码：destOrgCode；
	 * 到达部门名称：destOrgName；
	 * 车牌号:vehicleNo；
	 * 车辆所有权类别：vehicleOwnerShip；
	 * 司机姓名：driverName；
	 * 司机编码：driverCode
	 * 付款方式 ：paymentType
	 * 总运费：feeTotal
	 * 预付运费总额：prePaidFeeTotal
	 * 到付运费总额：arriveFeeTotal；
	 * 是否押回单：beReturnReceipt；
	 * 币种：currencyCode；
	 * 车辆出发日期：leaveTime；
	 * 】
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午2:37:42
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void modifyTruckStowage(StlVehicleAssembleBillDto dto,
			CurrentInfo currentInfo) {
		//验证传入参数是否为空
		if (dto == null) {
			throw new SettlementException("修改配载单传入的参数为空");
		}
		
		LOGGER.info("STL 开始修改配载单服务，配载车次号为："
				+ dto.getVehicleAssembleNo()
				+ (StringUtils.isNotEmpty(dto.getWaybillNo()) ? ("   运单号为：" + dto
						.getWaybillNo()) : "")+ "  司机编码为： "
								+ dto.getDriverCode());
		
		//验证修改参数
		this.validateParams(dto);

		// 验证通过，先把原有的应付查询出来
		List<BillPayableEntity> payableList = null;
		
		// 首款实体信息
		BillPayableEntity firstPayableEntity = null;
		
		// 尾款实体信息
		BillPayableEntity lastPayableEntity = null;
		//根据来源单号进行查询
		payableList = this.queryBillPyableBySourceBillNOs(dto);
		if (CollectionUtils.isEmpty(payableList)) {
			throw new SettlementException("不存在汽运配载应付单信息，不能进行修改操作");
		}
		
		 //验证同一个汽运单号是否存在多条有效的首款应付单或多条有效的尾款应付单信息
		int fcount=0;//代表首款应付单个数
		int lcount=0;//代表尾款应付单个数
		//循环验证同一个汽运单号是否存在多条有效的首款应付单或多条有效的尾款应付单信息
		for (BillPayableEntity entity : payableList) {
			 //验证同一个汽运单号是否存在多条有效的首款应付单或多条有效的尾款应付单信息
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST
					.equals(entity.getBillType())
					|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST
							.equals(entity.getBillType())) {
				firstPayableEntity = entity;
				fcount+=1;
				 //验证同一个汽运单号是否存在多条有效的首款应付单或多条有效的尾款应付单信息
			} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST
					.equals(entity.getBillType())
					|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
							.equals(entity.getBillType())) {
				lastPayableEntity = entity;
				lcount+=1;
			}
		}
		//同一个汽运配载单号存在多条首款应付单，不能进行修改操作
		if(fcount>1){
			throw new SettlementException("同一个汽运配载单号存在"+fcount+"条首款应付单，不能进行修改操作");
		}
		//同一个汽运配载单号存在多条尾款应付单，不能进行修改操作
		if(lcount>1){
			throw new SettlementException("同一个汽运配载单号存在"+lcount+"条尾款应付单，不能进行修改操作");
		}
		
		//声明一个字符串
		String firstBillType = "";
		if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH
				.equals(dto.getPaymentType())||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(dto.getPaymentType())) {// 支付方式为现金时，才会有首款应付单
			// 代表首款类型
			firstBillType = this.getPayableBillType(dto,
							SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST,null);
		}

		// 代表尾款类型 --支付方式为（月结的只有尾款应付单）
		String lastBillType = this.getPayableBillType(dto,
				SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST,
				firstBillType);
		Date date = new Date();

		// 首款
		this.modifyBillPayable(dto, firstPayableEntity, firstBillType, date,
				currentInfo);

		// 尾款
		this.modifyBillPayable(dto, lastPayableEntity, lastBillType, date,
				currentInfo);
		
		LOGGER.info("STL 修改配载单服务成功！");
	}

	/**
	 * 修改应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 上午8:03:43
	 * @param dto
	 * @param entity
	 * @param billType
	 * @param date
	 * @param currentInfo
	 */
	private void modifyBillPayable(StlVehicleAssembleBillDto dto,
			BillPayableEntity payableEntity, String billType, Date date,
			CurrentInfo currentInfo) {
		
		// 系统中已经存在应付单信息
		if (payableEntity != null
				&& StringUtils.isNotEmpty(payableEntity.getId())) {
			this.validateBillPayableEntity(payableEntity);// 验证应付单信息

			// 如果存在预付运费总额或到付运费总额
			if (StringUtils.isNotEmpty(billType)) {
				//红冲应付单信息，根据新的修改信息生成新的应付单信息
				this.writeBackBillPayableAndAddPayable(dto, payableEntity,billType, date, currentInfo);
			} else {
				// 没有预付运费总额或到付运费总额，只红冲应付单
				//红冲应付单
				this.billPayableService.writeBackBillPayable(payableEntity,
						currentInfo);
			}
		} else {
			// 如果存在预付运费总额或到付运费总额
			if (StringUtils.isNotEmpty(billType)) {
				//根据传入的参数和单据类型设置应付单属性值
				BillPayableEntity entity = this.getBillPayableEntity(dto,
						billType, date, currentInfo);
				
				// 新增一条应付单Service
				this.billPayableService.addBillPayable(entity, currentInfo);
			}
		}
	}

	/**
	 * 红冲应付单信息，根据新的修改信息生成新的应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-30 下午7:35:01
	 * @param dto
	 * @param entity
	 * @param date
	 * @param currentInfo
	 */
	private void writeBackBillPayableAndAddPayable(
			StlVehicleAssembleBillDto dto, BillPayableEntity entity,
			String billType, Date date, CurrentInfo currentInfo) {
		if (entity != null&&StringUtils.isNotEmpty(entity.getId())) {
			// 红冲应付单信息
			this.billPayableService.writeBackBillPayable(entity, currentInfo);
			//根据传入的参数和单据类型设置应付单属性值 出发部门和到达不会改变
			BillPayableEntity blueEntity = this.getBillPayableEntityByUpdate(
					dto, entity, billType, date, currentInfo);
			
			// 保存新的应付单据信息
			this.billPayableService.addBillPayable(blueEntity, currentInfo);
		}
	}

	/**
	 * 根据传入的参数和单据类型设置应付单属性值 出发部门和到达不会改变
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-29 下午5:01:08
	 * @param dto          修改传入的dto参数
	 * @param entity       应付单实体
	 * @param billType     应付单单据类型
	 * @param date         当前系统日期
	 * @param currentInfo  操作者
	 * @return
	 */
	private BillPayableEntity getBillPayableEntityByUpdate(
			StlVehicleAssembleBillDto dto, BillPayableEntity entity,
			String billType, Date date, CurrentInfo currentInfo) {
		//声明实例应付单据对象
		BillPayableEntity newEntity = new BillPayableEntity();
		BeanUtils.copyProperties(entity, newEntity);
		
		// ID
		newEntity.setId(UUIDUtils.getUUID());
		
		// 付款单号N/A
		newEntity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
		
		// 系统生成方式
		newEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		
		// 配载车次号
		newEntity.setSourceBillNo(dto.getVehicleAssembleNo());
		
		// 司机编码
		newEntity.setCustomerCode(dto.getDriverCode());
		
		// 司机名称
		newEntity.setCustomerName(dto.getDriverName());
		
		//单据类型
		newEntity.setBillType(billType);
		
		//2013-02-21日，需求变更配载类型为长途外请车时(非整车)，生成的应付单的生效状态默认为：已生效
		if (LoadConstants.VEHICLE_ASSEMBLE_TYPE_OWNER_LINE.equals(dto
				.getAssembleType()) ||
				// 整车首款默认生效
				(LoadConstants.VEHICLE_ASSEMBLE_TYPE_CAR_LOAD.equals(dto.getAssembleType()) 
						&& SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST
						.equals(billType))) {

			// 生效时间
			newEntity.setEffectiveDate(date);
			
			// 生效状态-已生效
			newEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
			
			// 生效人编码
			newEntity.setEffectiveUserCode(currentInfo.getEmpCode());

			// 生效人名称
			newEntity.setEffectiveUserName(currentInfo.getEmpName());
			
		}else{
			// 生效状态-默认设置为未生效
			entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
		}		
		
		// 整车/外请车-首款
		if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(billType)
				|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST.equals(billType)) {
			
			// 应付单号
			newEntity.setPayableNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF61));
			
			// 金额
			newEntity.setAmount(dto.getPrePaidFeeTotal());
			
			
			// 应付类型-首款
			if(FossConstants.YES.equals(dto.getBeReturnReceipt())){
				newEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK);
			}else{
				newEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST);
			}
			
			
			
			
		}else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
				.equals(billType)
				|| SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST
						.equals(billType)) {
			
			// 尾款应付单号
			newEntity.setPayableNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YF62));
			newEntity.setAmount(dto.getArriveFeeTotal());// 配载单-到付运费总额

			// 应付类型-尾款
			if(FossConstants.YES.equals(dto.getBeReturnReceipt())){
				newEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK);
			}else if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())){
				entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH);
			}else{
				newEntity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST);
			}
			
			
			// 付款方式为月结的
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())
					) {
				// 配载单-总运费
				newEntity.setAmount(dto.getFeeTotal());
			}
			
			//付款方式为月结的或有押回单的，尾款有出发部门支付
			if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto.getPaymentType())// 付款方式为月结的
					||FossConstants.YES.equals(dto.getBeReturnReceipt())//或者存在押回单的
					){
				//设置应付单部门编码
				newEntity.setPayableOrgCode(dto.getOrigOrgCode());
				//设置应付单部门名称
				newEntity.setPayableOrgName(dto.getOrigOrgName());
			}else{
				//设置应付单部门编码
				newEntity.setPayableOrgCode(dto.getDestOrgCode());
				//设置应付单部门名称
				newEntity.setPayableOrgName(dto.getDestOrgName());
			}
		}
		
		//已核销金额
		newEntity.setVerifyAmount(BigDecimal.ZERO);
		
		// 未核销金额
		newEntity.setUnverifyAmount(newEntity.getAmount());
		
		// 币种
		newEntity.setCurrencyCode(dto.getCurrencyCode());
		
		// 运输性质
		newEntity.setProductCode("");
		
		// 产品ID
		newEntity.setProductId("");
		
		// 记账日期
		newEntity.setAccountDate(date);

		// 是否有效
		newEntity.setActive(FossConstants.ACTIVE);
		
		// 是否红单
		newEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		// 是否初始化
		newEntity.setIsInit(FossConstants.NO);
		
		// 版本号
		newEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		// 冻结状态
		newEntity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		
		// 支付状态-未支付
		newEntity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		
		// 审批状态-已审核
		newEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		
		// 对账单号 N/A
		newEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);	
		
		// 创建时间
		newEntity.setCreateTime(date);
		
		// 修改时间
		newEntity.setModifyDate(date);
		
		// 司机编码
		newEntity.setLgdriverCode(dto.getDriverCode());
		
		// 司机名称
		newEntity.setLgdriverName(dto.getDriverName());
		
		/*
		 * 税务改造需求
		 * 
		 * 外请车收尾款应付单发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		
		return newEntity;
	}

	/**
	 * 验证应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 上午8:05:46
	 * @param payableEntity
	 */
	private void validateBillPayableEntity(BillPayableEntity payableEntity) {
		//判断传入应付单参数是否为空
		if (payableEntity == null) {
			throw new SettlementException("应付单信息为空");
		}
		//判断传入应付单参数是否核销
		if (payableEntity.getVerifyAmount() != null
				&& payableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			throw new SettlementException("应付单已核销");
		}
		//判断传入应付单参数是否冻结
		if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
				.equals(payableEntity.getFrozenStatus())) {
			throw new SettlementException("应付单已冻结");
		}
		//判断传入应付单参数是否付款
		if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
				.equals(payableEntity.getPayStatus())) {
			throw new SettlementException("应付单已付款");
		}
		
//		//需求变更后，应付单审核后，不能进行修改和作废操作
//		if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE
//				.equals(payableEntity.getApproveStatus())) {
//			throw new SettlementException("应付单已审核");
//		}
	}

	/**
	 * 验证调整费用信息的需要传入的参数信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 上午9:43:46
	 * @param dto
	 */
	private void validateAdjustOutVehicleFeeParams(StlVehicleAssembleBillDto dto) {
		
		validatePubParams(dto);
		//调整配载单的奖罚类型不能为空
		if (StringUtils.isEmpty(dto.getAwardType())) {
			throw new SettlementException("调整配载单的奖罚类型不能为空");
		}
		//调整费用金额不能为空
		if (dto.getAdjustFee() == null) {
			throw new SettlementException("调整费用金额不能为空");
		}
		//调整费用金额不能小于等于0
		if (dto.getAdjustFee().compareTo(BigDecimal.ZERO) <=0) {
			throw new SettlementException("调整费用金额不能小于等于0");
		} 
		//总费用为空
		if (dto.getFeeTotal() == null) {
			throw new SettlementException("总费用为空");
		}
		//总运费不能小于等于0
		if (dto.getFeeTotal().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("总运费不能小于等于0");
		}
		//审核状态不能为空
		if (StringUtils.isEmpty(dto.getAuditState())) {
			throw new SettlementException("审核状态不能为空");
		} else {
			//审批未通过时，不能调用这个接口
			if (!OutsideVehicleChargeConstants.AUDITPASS.equals(dto
					.getAuditState())) {
				throw new SettlementException("还未进行审核或审核未通过不能进行调整费用操作！");
			}
		}
	}

	/**
	 * 调整外请车（尾款）费用信息
	 * 【配载车次号 vehicleAssembleNo， 配载类型：assembleType， 出发部门编码：origOrgCode
	 * 到达部门编码：destOrgCode ，总费用：feeTotal
	 * 奖罚类型：awardType 调整费用：adjustFee
	 *  部门经理审核状态：auditState 】
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-30 下午5:02:29
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void adjustOutVehicleFee(StlVehicleAssembleBillDto dto,
			CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if (dto == null) {
			throw new SettlementException("调整外请车费用信息为空");
		}
		
		LOGGER.info("---STL 开始调用调整外请车费用服务，配载车次号为："+ dto.getVehicleAssembleNo());
		// 验证参数
		this.validateAdjustOutVehicleFeeParams(dto);
		Date date = new Date();
		//只查询整车或外请车的尾款应付单信息
		List<BillPayableEntity> list = this.queryBillPyableByCon(dto);
		//不存在外请车应付单信息，不能进行调整费用操作"
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("不存在外请车应付单信息，不能进行调整费用操作");
		}
		//存在多条外请尾款成本应付单，不能进行调整费用操作
		if(list.size()>1){
			throw new SettlementException("存在"+list.size()+"条外请尾款成本应付单，不能进行调整费用操作");
		}
		//获取集合中的第一条数据
		BillPayableEntity entity = list.get(0);

		// 验证应付单信息
		this.validateBillPayableEntity(entity);

		// 红冲原单据信息
		this.billPayableService.writeBackBillPayable(entity, currentInfo);

		//声明实例一个应付单对象
		BillPayableEntity blueEntity = new BillPayableEntity();
		BeanUtils.copyProperties(entity, blueEntity);
		blueEntity.setId(UUIDUtils.getUUID());// Id
		if (SettlementConstants.BLUE_NEW_BILL_NO) { // 生成新单据
			//通过CODE获取枚举值
			SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getPayableNo());
			blueEntity.setPayableNo(settlementCommonService.getSettlementNoRule(rule));
		}
		if (OutsideVehicleChargeConstants.REWARD.equals(dto.getAwardType())) {
			// 奖励 应付金额+调整金额
			blueEntity.setAmount(NumberUtils.add(blueEntity.getAmount(),dto.getAdjustFee()));
		} else if (OutsideVehicleChargeConstants.FINE
				.equals(dto.getAwardType())) {
			// 惩罚用应付金额-调整金额
			//调整惩罚金额大于或等于应付金额，不能进行调整费用操作
			if (blueEntity.getAmount() != null
					&& blueEntity.getAmount().compareTo(dto.getAdjustFee()) <= 0) {
				throw new SettlementException("调整惩罚金额大于或等于应付金额，不能进行调整费用操作");
			}
			
			// 惩罚用应付金额-调整金额
			blueEntity.setAmount(blueEntity.getAmount().subtract(
					dto.getAdjustFee()));//
		}else{
			//系统传入的奖罚类型有误，不能进行调整费用操作
			throw new SettlementException("系统传入的奖罚类型有误，不能进行调整费用操作");
		}
		
		//未核销金额
		blueEntity.setUnverifyAmount(blueEntity.getAmount());
		
		//已核销金额默认为0
		blueEntity.setVerifyAmount(BigDecimal.ZERO);
		
		// 记账日期
		blueEntity.setAccountDate(date);
		
		// 版本号
		blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		// 是否有效
		blueEntity.setActive(FossConstants.ACTIVE);
		
		// 是否红单
		blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		// 是否初始化
		blueEntity.setIsInit(FossConstants.NO);
		
		//审核状态-已审核
		blueEntity.setApproveStatus(SettlementDictionaryConstants.
				BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
		
		// 保存应付单信息
		this.billPayableService.addBillPayable(blueEntity, currentInfo);
		
		//2014-05-29 添加费用调整后做反到达确认，并重新到达确认
		if(FossConstants.YES.equals(dto.getIsArrive())&&dto.getArriveTime() != null){
			//查询收款信息
			BillPayableConditionDto conDto = new BillPayableConditionDto();
			if (StringUtils.isNotEmpty(dto.getWaybillNo())) {
				conDto.setWaybillNo(dto.getWaybillNo());
			}
			// 来源单号-配载车次号
			conDto.setSourceBillNo(dto.getVehicleAssembleNo());
			conDto.setBillTypes(new String[] {
					// 外请车尾款
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_FIRST,
					// 整车尾款
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST 
			});// 单据类型
			//根据运单号和应付单类型等参数，查询有效应付单信息
			List<BillPayableEntity> fistEntiys =this.billPayableService.queryBillPayableByCondition(conDto);
			BillPayableEntity firstEnity = new BillPayableEntity();
			if(CollectionUtils.isEmpty(fistEntiys)&&fistEntiys.size()>0){
			 firstEnity = fistEntiys.get(0);
			}
			//做反到达操作
			this.truckArriveConfirm(firstEnity,entity,SettlementDictionaryConstants.TRUCK_ARRIVE_UNCONFIRM);
			//重新到达确认
			this.truckArriveConfirm(firstEnity,blueEntity,SettlementDictionaryConstants.TRUCK_ARRIVE_CONFIRM);
		}
		
		LOGGER.info("-----STL 调用调整外请车费用服务成功！");
	}
	
	/**
	 * 
	 * 费用调整后生成车辆到达确认，反到达确认数据
	 * @author 073615
	 * @date 2014-05-29
	 * @param entity first 首款 tail 尾款 arrvie_type 到达类型  到达/反到达
	 */
	private void truckArriveConfirm(BillPayableEntity firstPayableEntity
			,BillPayableEntity tailPayableEntity,String arrvieType){
		TruckArriveConfirmDto dto = new TruckArriveConfirmDto();
		TruckArriveConfirmEntity entity = new TruckArriveConfirmEntity();
		entity.setId(UUIDUtils.getUUID());
		entity.setConertType(arrvieType);
		//设置配载单号
		entity.setHandleNo(tailPayableEntity.getSourceBillNo());
		//到达反到达时间为当前时间
		entity.setConvertDate(new Date());
		entity.setCreateTime(new Date());
		//设置首款、尾款的id
		entity.setPayablefirstId(firstPayableEntity.getId());
		entity.setPayablelastId(tailPayableEntity.getId());
		dto.setTruckArriveConfirmEntity(entity);
		truckArriveConfirmService.truckConfirmByEntity(dto);
		
	}

	/**
	 * 作废汽运配载单时，验证中转传入的参数信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-1 上午11:21:45
	 * @param dto
	 */
	private void validateByDisableTruckStowageParams(
			StlVehicleAssembleBillDto dto) {
		//配载车次号不能为空
		if (StringUtils.isEmpty(dto.getVehicleAssembleNo())) {
			throw new SettlementException("配载车次号不能为空");
		}
		//配载类型不能为空
		if (StringUtils.isEmpty(dto.getAssembleType())) {
			throw new SettlementException("配载类型不能为空");
		} 
		//车辆所有权类别不能为空
		if (StringUtils.isEmpty(dto.getVehicleOwnerShip())) {
			throw new SettlementException("车辆所有权类别不能为空");
		} else {
			//为公司内部车辆抛出异常信息
			if(LoadConstants.VEHICLE_OWNERSHIP_OWNER_VEHICLE.equals(dto.getVehicleOwnerShip())){
				throw new SettlementException("车辆所有权类别必须为外请车");
			}
		}
	}

	/**
	 * 作废配载单
	 * 
	 * 【配载车次号 vehicleAssembleNo 运单号 waybillNo 配载类型 assembleType 出发部门编码
	 * origOrgCode 到达部门编码 destOrgCode 车辆所有权类别 vehicleOwnerShip 】
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-28 下午2:38:00
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void disableTruckStowage(StlVehicleAssembleBillDto dto,
			CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if (dto == null) {
			throw new SettlementException("作废配载单服务传入的参数为空");
		}
		
		LOGGER.info("STL 开始调用作废配载单服务,配载车次号为："+dto.getVehicleAssembleNo());
		
		// 验证传入的参数信息
		this.validateByDisableTruckStowageParams(dto);
		//根据来源单号进行查询
		List<BillPayableEntity> list = this.queryBillPyableBySourceBillNOs(dto);
		//判断查询是否为空
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException("不存在汽运配载应付单信息");
		}
		//循环验证应付单信息
		for (BillPayableEntity entity : list) {
			this.validateBillPayableEntity(entity);// 验证应付单信息

			// 调用红冲应付单服务接口
			this.billPayableService.writeBackBillPayable(entity, currentInfo);
		}
		
		LOGGER.info("STL调用作废配载单服务成功！");
		
	}

	/**
	 * 只查询整车或外请车的尾款应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-30 下午7:19:05
	 * @param dto
	 * @return
	 */
	private List<BillPayableEntity> queryBillPyableByCon(
			StlVehicleAssembleBillDto dto) {
		//声明实例dto
		BillPayableConditionDto conDto = new BillPayableConditionDto();
		if (StringUtils.isNotEmpty(dto.getWaybillNo())) {
			conDto.setWaybillNo(dto.getWaybillNo());
		}
		// 来源单号-配载车次号
		conDto.setSourceBillNo(dto.getVehicleAssembleNo());
		conDto.setBillTypes(new String[] {
				// 外请车尾款
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST,
				// 整车尾款
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST 
		});// 单据类型
		//根据运单号和应付单类型等参数，查询有效应付单信息
		return this.billPayableService.queryBillPayableByCondition(conDto);
	}

	/**
	 * 根据来源单号进行查询
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-29 下午2:32:05
	 * @param dto
	 * @return
	 */
	private List<BillPayableEntity> queryBillPyableBySourceBillNOs(
			StlVehicleAssembleBillDto dto) {
		//根据传入的一到多个来源单号，获取一到多条应付单信息
		return this.billPayableService.queryBySourceBillNOs(
						Arrays.asList(dto.getVehicleAssembleNo()),
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE,
						FossConstants.ACTIVE);
	}

	
	/**
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	
	/**
	 * @param settlementCommonService the settlementCommonService to set
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @GET
	 * @return orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		/*
		 *@get
		 *@ return orgAdministrativeInfoService
		 */
		return orgAdministrativeInfoService;
	}

	/**
	 * @SET
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoService = orgAdministrativeInfoService
		 */
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setTruckArriveConfirmService(
			ITruckArriveConfirmService truckArriveConfirmService) {
		this.truckArriveConfirmService = truckArriveConfirmService;
	}



}
