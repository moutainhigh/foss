package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.pay.api.server.service.IGatewayPaymentService;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 网上支付应收单、对账单公共SERVICE接口实现类
 * 
 * @author 306698-foss-youkun
 * @date 2016-05-09 下午2:29:17
 */
public class GatewayPaymentService implements IGatewayPaymentService {
    // 支付宝条码支付
    private static final String alipayWithScanCode = "支付宝条码支付";

    /**
     * 注入更改单SERVICE
     */
    private IWaybillRfcService waybillRfcService;

    /**
     * 注入代收货款ICodCommonService
     */
    private ICodCommonService codCommonService;

    /**
     * FOSS到财务自助公共接口类
     */
    // private IFossToFinsRemitCommonService fossToFinsRemitCommonService;

    /**
     * 注入应收单IBillReceivableService
     */
    private IBillReceivableService billReceivableService;

    /**
     * 注入对账单IStatementOfAccountService
     */
    private IStatementOfAccountService statementOfAccountService;

    /**
     * 注入核销单IBillWriteoffService
     */
    private IBillWriteoffService billWriteoffService;

    /**
     * 注入生成序列Service
     */
    private ISettlementCommonService settlementCommonService;

    /**
     * 业务互斥锁服务
     */
    private IBusinessLockService businessLockService;

    /**
     * 注入子公司SERVICE
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 注入还款单IBillRepaymentService
     */
    private IBillRepaymentService billRepaymentService;

    /**
     * ESB提供的服务端编码（ESB同事提供的）
     */
    public static String ESB_SERVICE_CODE = "/FINS_ESB2FINS_INCOME_REPORTED";

    /**
     * ESB提供的客户端编码（ESB同事提供的）
     */
    public static String ESB_CLIENT_CODE = "/ESB_FOSS2ESB_INCOME_REPORTED";

    /**
     * FINS服务端地址(本地联调用)
     */
    // public static String FINS_SERVICE_CODE =
    // "http://10.224.64.35:8080/financManager/webservice/ThirdMainBillService/SaveThirdOrderRecord";

    /**
     * 查询esb地址信息的接口
     */
    // private IFossConfigEntityService fossConfigEntityService;

    // 日志
    private static final Logger logger = org.apache.log4j.LogManager.getLogger(GatewayPaymentService.class);

    /**
     * 网上支付按应收单还款
     * 
     * @author 306698-foss-youkun
     * @date 2016-05-09 上午8:07
     */
    @Transactional
    @Override
    public void paymentReceiveBillInfo(BillReceivableOnlineQueryDto queryDto) {
        // 校验查询参数是否合法
        // 判断应收单号、在线支付编号、还款金额、记账日期是否为空
        if (StringUtil.isEmpty(queryDto.getReceivableNo())) {
            logger.error("应收单号为空");
            throw new SettlementException("应收单号为空！", "");
        }
        // 记录日志
        logger.info("网上支付还款entering service,应收单号：" + queryDto.getReceivableNo());
        // 判空
        if (StringUtil.isEmpty(queryDto.getOnlineNo())) {
            // 记录日志
            logger.error("在线支付编码为空");
            throw new SettlementException("在线支付编码为空！", "");
        }
        // 判空
        if (StringUtil.isEmpty(queryDto.getRemitAccount())) {
            // 记录日志
            logger.error("在线支付汇款账号不能为空");
            throw new SettlementException("在线支付汇款账号不能为空！", "");
        }
        // 判空
        if (queryDto.getAccountDate() == null) {
            // 记录日志
            logger.error("记账日期为空");
            throw new SettlementException("记账日期为空！", "");
        }
        // 判空
        if (queryDto.getAmount() == null) {
            // 记录日志
            logger.error("还款金额为空");
            throw new SettlementException("还款金额为空！", "");
        }
        // 判空
        if (queryDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            // 记录日志
            logger.error("还款金额小于等于0");
            throw new SettlementException("还款金额小于等于0！", "");
        }
        // 判断在线支付编号是否已经使用
        BillRepaymentConditionDto dto = new BillRepaymentConditionDto();
        // 设置属性值
        dto.setOnlinePaymentNo(queryDto.getOnlineNo());
        // 调用接口进行查询
        List<BillRepaymentEntity> list = billRepaymentService.queryBillRepaymentByCondition(dto);
        // 判断列表集合
        if (list.size() > 0) {
            // 记录日志
            logger.error("在线支付编号已被使用过，请确认是否重复付款");
            throw new SettlementException("在线支付编号已被使用过，请确认是否重复付款！", "");
        }
        // 根据应收单能否被操作
        BillReceivableEntity receivableEntity = validateReceivableEntity(queryDto);
        // 判断还款金额和应收单未核销金额
        if (queryDto.getAmount().compareTo(receivableEntity.getUnverifyAmount()) != 0) {
            // 记录日志
            logger.error("还款金额与应收单的未核销金额不等");
            throw new SettlementException("还款金额与应收单的未核销金额不等！", "");
        }

        // 如果应收单存在运单号，生成互斥对象
        MutexElement mutex = null;
        // 判空
        if (StringUtils.isNotEmpty(receivableEntity.getWaybillNo())
                && !SettlementConstants.DEFAULT_BILL_NO.equals(receivableEntity.getWaybillNo())) {
            // 互斥锁
            mutex = new MutexElement(receivableEntity.getWaybillNo(), "网上支付按应收单还款",
                    MutexElementType.WAYBILL_NO);
        }
        // 如果互斥对象不为空,锁定该应收单对应的运单
        if (mutex != null) {
            // 锁定运单
            businessLockService.lock(mutex, SettlementConstants.BUSINESS_LOCK_BATCH);
        }

        // 生成还款单信息
        BillRepaymentEntity repaymentEntity = new BillRepaymentEntity();
        // 设置属性值
        repaymentEntity.setOnlinePaymentNo(queryDto.getOnlineNo());
        // 设置属性值
        repaymentEntity.setId(UUIDUtils.getUUID());
        // 还款单号
        repaymentEntity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1));
        // 设置属性值
        repaymentEntity.setCustomerCode(receivableEntity.getCustomerCode());
        // 设置运单号属性值
        repaymentEntity.setWaybillNo(receivableEntity.getWaybillNo());
        // 设置属性值
        repaymentEntity.setCustomerName(receivableEntity.getCustomerName());
        // 还款金额、实际还款金额、反核销金额
        repaymentEntity.setAmount(receivableEntity.getUnverifyAmount());
        // 设置属性值
        repaymentEntity.setTrueAmount(receivableEntity.getUnverifyAmount());
        // 设置属性值
        repaymentEntity.setBverifyAmount(BigDecimal.ZERO);
        // 审核状态、生成方式
        repaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
        // 设置属性值
        repaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
        // 收款部门编码、名称
        repaymentEntity.setCollectionOrgCode(receivableEntity.getReceivableOrgCode());
        repaymentEntity.setCollectionOrgName(receivableEntity.getReceivableOrgName());
        // 根据部门编码获取子公司信息
        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByCode(receivableEntity.getReceivableOrgCode());
        // 判空
        if (orgEntity != null) {
            // 收款部门公司编码、名称
            repaymentEntity.setCollectionCompanyCode(orgEntity.getSubsidiaryCode());
            // 设置属性值
            repaymentEntity.setCollectionCompanyName(orgEntity.getSubsidiaryName());
        }
        // BUG-20628 还款单不需要收入部门及收入部门公司
        // 收入部门编码、名称
        // repaymentEntity.setGeneratingOrgCode(receivableEntity.getGeneratingOrgCode());
        // 设置属性值
        // repaymentEntity.setGeneratingOrgName(receivableEntity.getGeneratingOrgName());
        // 收入部门公司编码、名称
        // repaymentEntity.setGeneratingCompanyCode(receivableEntity.getGeneratingComCode());
        // 设置属性值
        // repaymentEntity.setGeneratingCompanyName(receivableEntity.getGeneratingComName());
        // 单据类型、是否有效、是否红单、支付方式
        repaymentEntity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);
        // 设置属性值
        repaymentEntity.setActive(FossConstants.ACTIVE);
        // 设置属性值
        repaymentEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
        // 设置属性值
        repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
        // 业务日期、记账日期、是否初始化、版本号
        repaymentEntity.setBusinessDate(new Date());
        // 设置属性值
        repaymentEntity.setAccountDate(new Date());
        // 设置属性值
        repaymentEntity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
        // 设置属性值
        repaymentEntity.setIsInit(FossConstants.NO);
        // 设置属性值
        repaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
        // 创建当前登录信息
        EmployeeEntity employee = new EmployeeEntity();
        // 设置属性值
        employee.setEmpCode(SettlementConstants.BHO_CODE);
        // 设置属性值
        employee.setEmpName(SettlementConstants.BHO_NAME);
        // 实例化
        UserEntity user = new UserEntity();
        // 设置属性值
        user.setEmployee(employee);
        // 设置属性值
        OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
        // 设置属性值
        dept.setCode(receivableEntity.getReceivableOrgCode());
        // 设置属性值
        dept.setName(receivableEntity.getReceivableOrgName());
        // 获取当前登录人
        CurrentInfo currentInfo = new CurrentInfo(user, dept);
        // 新增还款单信息
        repaymentEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
        repaymentEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
        billRepaymentService.addBillRepayment(repaymentEntity, currentInfo);
        // 调用还款冲应收服务
        BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
        // 设置属性值
        billWriteoffOperationDto.setBillRepaymentEntity(repaymentEntity);
        // 声明
        List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
        // 设置属性值
        billReceivableEntitys.add(receivableEntity);
        // 设置属性值
        billWriteoffOperationDto.setBillReceivableEntitys(billReceivableEntitys);
        // 核销批次号
        billWriteoffOperationDto.setWriteoffBatchNo(settlementCommonService
                .getSettlementNoRule(SettlementNoRuleEnum.HX_BN));
        // 核销單生成方式（手工輸入）
        billWriteoffOperationDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
        // 核销网点编码和名称
        BillWriteoffOperationDto resultDto = billWriteoffService.writeoffRepaymentAndReceibable(
                billWriteoffOperationDto, currentInfo);
        // 核销完毕调用通知对账单接口
        StatementOfAccountUpdateDto updateDto = new StatementOfAccountUpdateDto();
        // 设置属性值
        updateDto.setReceivableEntityList(resultDto.getBillReceivableEntitys());
        // 调用接口更更新
        statementOfAccountService.updateStatementAndDetailForWriteOff(updateDto, currentInfo);

        // 如果互斥对象不为空,解锁该应收单对应的运单
        if (mutex != null) {
            // 解锁运单
            businessLockService.unlock(mutex);
        }
        // 记录日志
        logger.info("网上支付还款sucessful service,应收单号：" + queryDto.getReceivableNo());
    }

    /**
     * 校验应收单信息能否被网厅操作
     * 
     * @author 306698-foss-youkun
     * @date 2016-05-09 上午8:03
     */
    private BillReceivableEntity validateReceivableEntity(BillReceivableOnlineQueryDto queryDto) {
        // 按应收单号和记账日期查询有效应收单信息
        BillReceivableDto dtos = new BillReceivableDto();
        // 实例化
        List<BillReceivableEntity> billReceivables = new ArrayList<BillReceivableEntity>();
        // 声明应收单
        BillReceivableEntity queryEntity = new BillReceivableEntity();
        // 设置属性值
        queryEntity.setReceivableNo(queryDto.getReceivableNo());
        // 设置属性值
        queryEntity.setAccountDate(queryDto.getAccountDate());
        // 添加应收单
        billReceivables.add(queryEntity);
        // 设置属性值
        dtos.setBillReceivables(billReceivables);
        // 设置属性值
        dtos.setActive(FossConstants.YES);
        // 查询
        List<BillReceivableEntity> receivableEntityList = billReceivableService
                .queryPartitionsByConditions(dtos);
        // 判空
        if (CollectionUtils.isEmpty(receivableEntityList)) {
            logger.error("该应收单不存在或已经处于无效状态！不能操作");
            throw new SettlementException("该应收单不存在或已经处于无效状态！不能操作", "");
        }
        BillReceivableEntity receivableEntity = receivableEntityList.get(0);

        // 校验产品类型为空运、偏线的到付应收单不能进行网上支付
        if ((SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
                .equals(receivableEntity.getBillType())
                || SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
                        .equals(receivableEntity.getBillType()) || SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
                    .equals(receivableEntity.getBillType()))
                && (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT
                        .equals(receivableEntity.getProductCode()) || PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE
                        .equals(receivableEntity.getProductCode()))) {
            // 记录日志
            logger.error("运输性质为空运、偏线的到付应收单不能进行网上支付！");
            throw new SettlementException("运输性质为空运、偏线的到付应收单不能进行网上支付！", "");
        }

        // 校验应收单信息是否合法
        // 校验应收单的未核销金额是否大于0；
        if (receivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) <= 0) {
            // 记录日志
            logger.error("该应收单未核销金额为0！不能操作");
            throw new SettlementException("该应收单未核销金额为0！不能操作", "");
        }
        // 校验应收单的类型只能始发应收、专线到付运费 支付宝条码支付存在代收货款可以走网上支付接口进行核销
        if (!alipayWithScanCode.equals(queryDto.getPayWay())) {
            if (!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
                    .equals(receivableEntity.getBillType()) || SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
                    .equals(receivableEntity.getBillType()))) {
                // 记录日志
                logger.error("该应收单类型只能为始发应收、到付应收，其余类型不能进行网上支付！");
                throw new SettlementException("该应收单类型只能为始发应收、到付应收，其余类型不能进行网上支付！", "");
            }
            // 不能操作有代收货款的专线到付运费应收单
            if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
                    .equals(receivableEntity.getBillType())) {
                // 调用接口查询
                CODEntity codEntity = codCommonService.queryByWaybill(receivableEntity.getWaybillNo());
                // 判空
                if (codEntity != null) {
                    // 记录日志
                    logger.error("该专线到付应收单存在代收货款记录！不能操作");
                    throw new SettlementException("该专线到付应收单存在代收货款记录！不能操作", "");
                }
            }
        }

        // 只能操作没有进入对账单或者进入对账单但对账单未确认
        // 对账单号集合
        List<String> statementNos = new ArrayList<String>();
        // 校验是否存在对账单号
        if (SettlementConstants.DEFAULT_BILL_NO.equals(receivableEntity.getStatementBillNo())) {
            // 添加对账单号
            statementNos.add(receivableEntity.getStatementBillNo());
        }
        // 查询
        List<String> confirmList = statementOfAccountService.queryConfirmStatmentOfAccount(statementNos);
        // 判空
        if (CollectionUtils.isNotEmpty(confirmList)) {
            // 记录日志
            logger.error("该应收单已经入确认的对账单！不能操作");
            throw new SettlementException("该应收单已经入确认的对账单！不能操作", "");
        }
        // 与应收单相关联的运单不存在有未受理的更改单信息
        // 运单号集合
        if (StringUtil.isNotEmpty(receivableEntity.getWaybillNo())) {
            // 实例化实体
            List<String> waybillNos = new ArrayList<String>();
            // 添加运单号
            waybillNos.add(receivableEntity.getWaybillNo());
            // 判断是否存在更改单
            List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
            // 判空
            if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
                // 记录日志
                logger.error("该应收单存在未受理的更改单！不能操作");
                throw new SettlementException("该应收单存在未受理的更改单！不能操作", "");
            }
        }
        return receivableEntity;
    }

    /******** setter-getter **********/
    public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
        this.waybillRfcService = waybillRfcService;
    }

    public void setCodCommonService(ICodCommonService codCommonService) {
        this.codCommonService = codCommonService;
    }

    public void setBillReceivableService(IBillReceivableService billReceivableService) {
        this.billReceivableService = billReceivableService;
    }

    public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
        this.statementOfAccountService = statementOfAccountService;
    }

    public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
        this.billWriteoffService = billWriteoffService;
    }

    public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }

    public void setBusinessLockService(IBusinessLockService businessLockService) {
        this.businessLockService = businessLockService;
    }

    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
        this.billRepaymentService = billRepaymentService;
    }

}
