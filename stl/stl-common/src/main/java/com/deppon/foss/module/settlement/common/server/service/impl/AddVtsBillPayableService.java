package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IAddVtsBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.EffectiveSaveDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VtsStlVehicleAssembleBillDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * VTS打印运输合同,触发财务单据
 *
 * @author 310970 caopeng
 */
public class AddVtsBillPayableService implements IAddVtsBillPayableService {

    /**
     * 结算通用Common Service
     */
    private ISettlementCommonService settlementCommonService;
    /**
     * 注入组织信息接口
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 应付单通用Service
     */
    private IBillPayableService billPayableService;
    private static final int NUMBER_OF_THREE = 3;

    @Override
    public void addvtsBillPayable(VtsStlVehicleAssembleBillDto dto, CurrentInfo currentInfo) {
        // 校验传入信息
        this.validateParams(dto);

		/*
         * 1.使用businessId字段判断是否为更改出发外场调用此接口：更改出发外场businessId以"update_"开头；
		 * 1.1非更改出发外场调用，走原有逻辑； 1.2为更改出发外场调用：查询是否已生成active=Y首尾款应付单：
		 * 1.2.1未生成：抛异常“更改出发外场时未查询到首尾款应付单”
		 * 1.2.2已生成：查询尾款应付单的生效状态字段effectiveStatus是否已生效（Y/N），
		 * 1.2.2.1如果尾款应付单为未生效，则红冲原有首尾款应付单；红冲后走以下逻辑，重新生成首尾款应付单；
		 * 1.2.2.2如果尾款应付单已生效，查询尾款应付单生效状态及生效时间返回；红冲原有首尾款应付单；红冲后走以下逻辑，重新生成首尾款应付单；
		 */
        EffectiveSaveDto effectiveSaveDto = new EffectiveSaveDto();
        // 判断是否含有 update_
        if (dto.getBusinessId().contains("update_")) {

            // 初始化dto
            BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto(
                    dto.getWaybillNo());
            // 设置查询的应付单类型
            billPayableConditionDto.setBillTypes(new String[]{
                    SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST,
                    SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST});
            // 查询是否已生成active=Y首尾款应付单：
            List<BillPayableEntity> billPayables = this.billPayableService
                    .queryBillPayableByCondition(billPayableConditionDto);
            // 未生成：抛异常“更改出发外场时未查询到首尾款应付单”
            if (CollectionUtils.isEmpty(billPayables)) {
                throw new SettlementException("更改出发外场时未查询到首尾款应付单");
            }
            for (BillPayableEntity entity : billPayables) {
                // 如果为尾款且生效 保存生效状态及时间
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
                        .equals(entity.getBillType())
                        && SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES
                        .equals(entity.getEffectiveStatus())) {
                    // 已生效，查询尾款应付单生效状态及生效时间返回
                    // 保存生效状态及生效时间
                    effectiveSaveDto.setEffectiveDate(entity.getEffectiveDate());
                    effectiveSaveDto.setEffectiveStatus(entity.getEffectiveStatus());
                    // //红冲应付单
                    billPayableService.writeBackBillPayable(entity, currentInfo);
                } else {
                    // 红冲应付单
                    effectiveSaveDto
                            .setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
                    billPayableService.writeBackBillPayable(entity, currentInfo);
                }
            }
        }

        // 校验是否存在相同记录的应付单
        List<BillPayableEntity> list = this.queryBillPyableBySourceBillNOs(dto);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new SettlementException("系统中已存整车应付单");
        }
        // 代表首款类型
        String firstBillType = "";
        // 为现金,电汇时才会有首款应付单
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(dto
                .getPaymentType())
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                .equals(dto.getPaymentType())) {// 为现金时才会有首款应付单
            firstBillType = this.getPayableBillType(dto,
                    SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST, null);
        }
        // 代表尾款类型
        String lastBillType = this.getPayableBillType(dto,
                SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST, firstBillType);
        // 输入的数据不完整
        if (StringUtils.isEmpty(firstBillType) && StringUtils.isEmpty(lastBillType)) {
            throw new SettlementException("传入的数据不完整");
        }
        Date date = new Date();
        if (StringUtils.isNotEmpty(firstBillType)) {
            BillPayableEntity firstEntity = this.getBillPayableEntity(dto, firstBillType, date,
                    currentInfo);
            // 保存首款应付单
            this.billPayableService.addBillPayable(firstEntity, currentInfo);
        }
        if (StringUtils.isNotEmpty(lastBillType)) {
            BillPayableEntity lastEntity = this.getBillPayableEntity(dto, lastBillType, date,
                    currentInfo);
            // 保存尾款应付单
            this.billPayableService.addBillPayable(lastEntity, currentInfo);

			/*
             * 2.使用businessId字段判断是否为更改出发外场调用此接口：更改出发外场businessId以"update_"开头；
			 * 2.1非更改出发外场调用，不做任何处理；
			 * 2.2为更改出发外场调用：将1.2.2.2查询的生效状态及生效时间更新至保存的尾款应付单中；
			 */
            if (dto.getBusinessId().contains("update_")) {
                // 更新应付单
                this.billPayableService.updateBillPayableEffectiveStatus(lastEntity,
                        effectiveSaveDto.getEffectiveDate(), effectiveSaveDto.getEffectiveStatus(),
                        currentInfo);
            }
        }

    }

    // 校验传入信息
    private void validateParams(VtsStlVehicleAssembleBillDto dto) {
        // 判空
        if (dto == null) {
            throw new SettlementException("传入参数不能为空");
        }
        // 车牌号不能为空
        if (StringUtils.isEmpty(dto.getVehicleNo())) {
            throw new SettlementException("车牌号不能为空");
        }
        // 付款方式不能为空
        if (StringUtils.isEmpty(dto.getPaymentType())) {
            throw new SettlementException("付款方式不能为空");
        }
        // 总费用不能为空
        if (dto.getFeeTotal() == null) {
            throw new SettlementException("总费用不能为空");
        }
        // 输入的总运费必须大于0
        if (dto.getFeeTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new SettlementException("输入的总运费必须大于0");
        }
        // 付款方式为现金，判断总运费=预付运费总额信息和到付运费总额
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(dto
                .getPaymentType())
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                .equals(dto.getPaymentType())) {
            if (dto.getFeeTotal().compareTo(
                    NumberUtils.add(dto.getPrePaidFeeTotal(), dto.getArriveFeeTotal())) != 0) {
                throw new SettlementException("输入的总运费和预付运费总额信息和到付运费总额不相同");
            }
            // 针对现金支付的，预付总额和到付总额不能同时为空
            if (dto.getPrePaidFeeTotal() == null && dto.getArriveFeeTotal() == null) {
                throw new SettlementException("预付运费总额信息和到付运费总额不能同时为空");
            }
            // 计算整车预付车费最大值，最大值为总车费的2/3
            double preMaxfee = Math.floor(dto.getFeeTotal().doubleValue() * 2 / NUMBER_OF_THREE);
            BigDecimal bigPreMaxfee = new BigDecimal(preMaxfee);
            if (dto.getPrePaidFeeTotal().compareTo(bigPreMaxfee) == 1) {
                throw new TfrBusinessException("整车首款不能大于总车费的2/3，请重新输入预付运费！");
            }
        }
        // 付款方式为月结时，预付总额和到付总额默认为0，这里设置小于0的情况进行提示
        if (dto.getPrePaidFeeTotal() != null
                && dto.getPrePaidFeeTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new SettlementException("输入的预付运费总额必须大于0");
        }
        if (dto.getPrePaidFeeTotal() != null
                && dto.getPrePaidFeeTotal().compareTo(dto.getFeeTotal()) > 0) {
            throw new SettlementException("输入的预付运费总额：" + dto.getPrePaidFeeTotal() + "大于总运费："
                    + dto.getFeeTotal());
        }
        // 输入的到付运费总额必须大于0
        if (dto.getArriveFeeTotal() != null
                && dto.getArriveFeeTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new SettlementException("输入的到付运费总额必须大于0");
        }
        if (dto.getArriveFeeTotal() != null
                && dto.getArriveFeeTotal().compareTo(dto.getFeeTotal()) > 0) {
            throw new SettlementException("输入的到付运费总额：" + dto.getArriveFeeTotal() + "大于总运费："
                    + dto.getFeeTotal());
        }

    }

    /**
     * 根据来源单号进行查询
     */
    private List<BillPayableEntity> queryBillPyableBySourceBillNOs(VtsStlVehicleAssembleBillDto dto) {
        // 根据传入的一到多个来源单号，获取一到多条应付单信息
        return this.billPayableService.queryBySourceBillNOs(Arrays.asList(dto.getSourceBillNo()),
                dto.getSourceBillType(), FossConstants.ACTIVE);
    }

    /**
     * 获取整车应付单类型
     *
     * @param dto
     * @param payableType
     * @param firstBillType /lastBillType
     * @return
     * @author 310970 caopeng
     * @date 2016-5-24
     */
    private String getPayableBillType(VtsStlVehicleAssembleBillDto dto, String payableType,
                                      String firstBillType) {
        if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH.equals(dto
                .getPaymentType())
                || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER
                .equals(dto.getPaymentType())) {

            // 获取方式为首款和预付金额大于0的
            if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST.equals(payableType)
                    && dto.getPrePaidFeeTotal() != null
                    && dto.getPrePaidFeeTotal().compareTo(BigDecimal.ZERO) > 0) {
                // 整车首款
                return SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST;
            } else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST
                    .equals(payableType)
                    && dto.getArriveFeeTotal() != null
                    && dto.getArriveFeeTotal().compareTo(BigDecimal.ZERO) > 0) {
                // 获取应付方式为尾款和到付运费金额大于0的
                // 获取整车尾款应付单类型
                return getLastBillPayableType(dto);
            }
        } else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
                .getPaymentType())) {
            if (StringUtils.isNotEmpty(firstBillType)) {
                throw new SettlementException("付款方式为月结时，只能存在一个应付单");
            }
            // 获取整车尾款应付单类型
            return this.getLastBillPayableType(dto);
        }
        return "";
    }

    /**
     * 获取整车尾款应付单类型
     *
     * @param dto
     * @return
     * @author 310970 caopeng
     * @date 2016-5-24
     */
    private String getLastBillPayableType(VtsStlVehicleAssembleBillDto dto) {
        // 整车尾款
        return SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST;
    }

    /**
     * 根据传入的参数和单据类型设置应付单属性值
     *
     * @param dto
     * @param billType
     * @param date
     * @param currentInfo
     * @author 310970-caopeng
     * @date 2016-5-24
     */
    private BillPayableEntity getBillPayableEntity(VtsStlVehicleAssembleBillDto dto,
                                                   String billType, Date date, CurrentInfo currentInfo) {
        BillPayableEntity entity = new BillPayableEntity();

        // ID
        entity.setId(UUIDUtils.getUUID());

        // 设置运单号属性值

        entity.setWaybillNo(dto.getWaybillNo());

        // 付款单号N/A
        entity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);

        // 系统生成方式
        entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

        // 设置来源单号
        entity.setSourceBillNo(dto.getSourceBillNo());

        // 来源单据类型
        entity.setSourceBillType(dto.getSourceBillType());

        // 出发部门编码
        entity.setOrigOrgCode(dto.getOrigOrgCode());

        // 出发部门名称
        entity.setOrigOrgName(dto.getOrigOrgName());

        // 到达部门编码
        entity.setDestOrgCode(dto.getDestOrgCode());

        // 到达部门名称
        entity.setDestOrgName(dto.getDestOrgName());

        // 司机编码
        entity.setCustomerCode(dto.getCustomerCode());

        // 司机名称
        entity.setCustomerName(dto.getCustomerName());

        // 单据类型
        entity.setBillType(billType);

        entity.setCustomerContactName(dto.getLgdriverName());

        entity.setCustomerPhone(dto.getLgdriverCode());
        // 外请车司机编码
        if (StringUtils.isNotEmpty(dto.getLgdriverCode())) {
            entity.setLgdriverCode(dto.getLgdriverCode());
        }
        // 外请车司机名称
        if (StringUtils.isNotEmpty(dto.getLgdriverName())) {
            entity.setLgdriverName(dto.getLgdriverName());
        }
        // 保理
        if (null != dto.getFactoring() && StringUtils.isNotEmpty(dto.getFactoring())) {
                entity.setFactoring(dto.getFactoring());
            if (dto.getFactoring().equals(FossConstants.YES)) {
                entity.setFactorBeginTime(dto.getFactorBeginTime());
                entity.setFactorEndTime(dto.getFactorEndTime());
                entity.setFactorAccount(dto.getFactorAccount());
                entity.setCusCode(dto.getCusCode());
            }
        } else {
            entity.setFactoring(FossConstants.NO);
        }
        // 整车首款默认为：已生效，只有整车尾款不生效
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(billType)) {

            // 生效时间
            entity.setEffectiveDate(date);

            // 生效状态-已生效
            entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

            // 生效人编码
            entity.setEffectiveUserCode(currentInfo.getEmpCode());

            // 生效人名称
            entity.setEffectiveUserName(currentInfo.getEmpName());
        } else {

            // 整车尾款，默认不生效。生效状态-默认设置为未生效
            entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
        }

        // 首款
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(billType)) {
            // 应付单号
            entity.setPayableNo(this.settlementCommonService
                    .getSettlementNoRule(SettlementNoRuleEnum.YF61));

            // 金额
            entity.setAmount(dto.getPrePaidFeeTotal());

            // 应付类型-首款
            entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__FIRST_BACK);

            // 首款出发部门支付 应付部门编码-出发部门编码
            entity.setPayableOrgCode(dto.getOrigOrgCode());

            // 应付部门名称-出发部门名称
            entity.setPayableOrgName(dto.getOrigOrgName());
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST
                .equals(billType)) {

            // 尾款
            // 应付单号
            entity.setPayableNo(this.settlementCommonService
                    .getSettlementNoRule(SettlementNoRuleEnum.YF62));

            // 到付运费总额
            entity.setAmount(dto.getArriveFeeTotal());

            // 应付类型-尾款
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
                    .getPaymentType())) {
                entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH);
            } else {
                entity.setPayableType(SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK);
            }

            // 付款方式为月结的，应付部门为出发部门
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
                    .getPaymentType())) {
                // 应付部门编码
                entity.setPayableOrgCode(dto.getOrigOrgCode());// -出发部门编码

                // 应付部门名称
                entity.setPayableOrgName(dto.getOrigOrgName());// -出发部门名称
            } else {
                // 应付部门编码
                entity.setPayableOrgCode(dto.getOrigOrgCode());// -到达部门编码

                // 应付部门名称
                entity.setPayableOrgName(dto.getOrigOrgName());// -到达部门名称
            }
            if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT.equals(dto
                    .getPaymentType())) {
                // 总费用
                entity.setAmount(dto.getFeeTotal());
            }
        }

        // 设置应付公司
        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(entity.getPayableOrgCode());
        // 如果部门为空，则抛出异常
        if (orgEntity != null) {
            // 设置部门编码和名称
            entity.setPayableComCode(orgEntity.getSubsidiaryCode());
            // 设置部门名称
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

        // 产品类型
        entity.setProductCode("WVH");

        // 产品类型
        entity.setProductId("C30005");

        // 审批状态-已审核
        entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

		/*
		 * 外请车首尾款应付单发票标记为 02-非运输专票
		 */

        entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);

        return entity;
    }

    public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }

    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    public void setBillPayableService(IBillPayableService billPayableService) {
        this.billPayableService = billPayableService;
    }
}
