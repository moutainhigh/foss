package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCostCenterDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.*;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OverdueSFPaymentApplyDto;
import com.deppon.foss.module.settlement.consumer.server.service.impl.OverdueSFPaymentApplyService;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillPayableQueryManageDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITemprentalMarkService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * 应付单service
 *
 * @author 045738-foss-maojianqiang
 * @date 2012-11-17 下午5:58:13
 */
public class BillPayableQueryManageservice implements
        IBillPayableQueryManageservice {

    /**
     * 获取日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BillPayableQueryManageservice.class);


    /**
     * 注入应付单dao
     */
    private IBillPayableQueryManageDao billPayableQueryManageDao;

    /**
     * 获取应付单service
     */
    private IBillPayableService billPayableService;

    /**
     * 注入更改单SERVICE
     */
    private IWaybillRfcService waybillRfcService;

    /**
     * 注入操作日志服务
     */
    private IOperatingLogService operatingLogService;
    /**
     * 注入组织服务
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

    /**
     * 注入部门service
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 注入应收单服务
     */
    private IBillReceivableService billReceivableService;

    /**
     * 注入中转接口，判断外请车尾款是否可以支付
     */
    private IOutsideVehicleChargeService outsideVehicleChargeService;

    /**
     * 根据产品CODE找对应的名称
     */
    IProductService productService;

    /**
     * 注入获取配置参数接口
     */
    private IConfigurationParamsService configurationParamsService;

    /**
     * 装卸费超时付款申请service
     */
    private OverdueSFPaymentApplyService overdueSFPaymentApplyService;

    /**
     * 调用成本中心service
     */
    private ICommonCostCenterDeptService commonCostCenterDeptService;
    /**
     * @author 268217
     * 注入理赔单服务Service
     */
    private IBillClaimService billClaimService;

    /**
     * 租车标记service
     */
    private ITemprentalMarkService temprentalMarkService;

    /**
     * 查询应付单
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-11-19 上午8:26:30
     * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice#queryBillPayable(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto)
     */
    @Override
    public BillPayableManageResultDto queryBillPayable(BillPayableManageDto dto, int start, int limit, CurrentInfo currentInfo) {
        LOGGER.info("查询应付单开始，开始日期和结束日期：" + dto.getBusinessBeginDate() + " ," + dto.getBusinessEndDate());
        // 校验dto参数，并封装部门信息进去
        validateDto(dto, currentInfo);
        //实例化dto
        BillPayableManageResultDto resultDto = new BillPayableManageResultDto();
        // 按日期查询
        if (dto.getQueryTab().equals(SettlementConstants.TAB_QUERY_BY_DATE)) {
            // 封装分页参数
            RowBounds rb = new RowBounds(start, limit);
            List<String> billList = new ArrayList<String>();
            //ddw
            if (null != dto.getBillType()) {
                String billType[] = dto.getBillType().split(",");
                for (int i = 0; i < billType.length; i++) {
                    if (null != billType[i] && !"".equals(billType[i].trim())) {
                        billList.add(billType[i].trim());
                    }
                }
                dto.setPayableBillType(billList);
            }
            // 调用dao层进行查询
            List<BillPayableEntity> list = billPayableQueryManageDao.queryBillPayableByDate(rb, dto);
            //调用接口计算总条数和总金额
            resultDto = billPayableQueryManageDao.countBillPayableByDate(dto);
            //判空
            if (resultDto != null) {
                resultDto.setList(list);
            }
        } else if (SettlementConstants.TAB_QUERY_BY_BILL_NO.equals(dto.getQueryTab())) {
            // 按运单号或应付单号查询
            if (dto.getBillNos() != null && dto.getBillNos().length > 0) {
                //账号转化
                Map<String, List<String>> resultMap = SettlementUtil.convertBillNos(dto.getBillNos());
                // 判断map中是否有应付单号集合
                if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YF))) {
                    //获取应付单号集合
                    dto.setPayableBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_YF));
                }
                //判空
                if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_LY))) {
                    //设置来源单号
                    dto.setSourceBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_LY));
                }
                //实例化应付单列表
                List<BillPayableEntity> list = new ArrayList<BillPayableEntity>();
                // 按 应付单号查询
                if (CollectionUtils.isNotEmpty(dto.getPayableBillNosList()) && dto.getPayableBillNosList().size() > 0) {
                    //调用接口进行查询
                    List<BillPayableEntity> payableList = billPayableService.queryByPayableNosAndDeptCodes(dto.getPayableBillNosList(),
                            null, null, currentInfo, dto.getIsPartner());
                    //将结果放到list中
                    list.addAll(payableList);
                }
                // 按来源单号查询
                if (CollectionUtils.isNotEmpty(dto.getSourceBillNosList()) && dto.getSourceBillNosList().size() > 0) {
                    //调用接口进行查询
                    List<BillPayableEntity> sourceList = billPayableService.queryByWaybillNosAndOrgCodes(dto.getSourceBillNosList(), null, null, currentInfo, dto.getIsPartner());
                    //将结果放到list中
                    list.addAll(sourceList);
                }
                //计算总金额
                countBillPayableByNumbers(resultDto, list);
            }
        } else {
            //根据来源单号集合查询应付单
            List<BillPayableEntity> payableList = billPayableService.queryBySourceBillNosAndOrgCodes(
                    Arrays.asList(dto.getBillNos()), dto.getOrgCodeList(), null, null, currentInfo, dto.getIsPartner());
            //计算总金额
            countBillPayableByNumbers(resultDto, payableList);
        }
        //记录日志
        LOGGER.info("查询应付结束");
        return resultDto;
    }

    /**
     * 按单号查询计算总条数和总金额
     */
    public void countBillPayableByNumbers(BillPayableManageResultDto resultDto, List<BillPayableEntity> list) {
        //申请总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        //总的未核销金额
        BigDecimal totalUnVerifyAmount = BigDecimal.ZERO;
        //总的已核销金额
        BigDecimal totalVerifyAmount = BigDecimal.ZERO;
        /**
         * 计算总金额
         */
        for (BillPayableEntity en : list) {
            //累加总金额
            totalAmount = totalAmount.add(en.getAmount());
            //累加总已核销金额
            totalVerifyAmount = totalVerifyAmount.add(en.getVerifyAmount());
            //累加总未核销金额
            totalUnVerifyAmount = totalUnVerifyAmount.add(en.getUnverifyAmount());
        }
        // 将查询结果集封装到dto中，回传到界面
        resultDto.setList(list);
        //设置结果到dto中
        resultDto.setTotalAmount(totalAmount);
        //设置结果到dto中
        resultDto.setTotalVerifyAmount(totalVerifyAmount);
        //设置结果到dto中
        resultDto.setTotalUnVerifyAmount(totalUnVerifyAmount);
        //设置结果到dto中
        resultDto.setTotalCount(list.size());
    }

    /**
     * 计算日期查询的总条数和总金额
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-11-19 下午7:37:36
     */
    public BillPayableManageResultDto countBillPayableByDate(BillPayableManageDto dto) {
        //调用接口计算总条数和总金额
        return billPayableQueryManageDao.countBillPayableByDate(dto);
    }

    /**
     * 付款操作
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-11-21 下午8:08:03
     * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice#payForBillPayable(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto)
     */
    @Transactional(readOnly = true)
    public BillPayableManageResultDto payForBillPayable(BillPayableManageDto dto) {
        LOGGER.info("应付单付款操作开始：应付单数量为" + dto.getBillNos().length);
        List<BillPayableEntity> payableList = null;

        // 调用应付单接口查询应付单
        if (StringUtils.isNotBlank(dto.getContainRentCar()) && FossConstants.ACTIVE.equals(dto.getContainRentCar())) {
            payableList = billPayableService.queryByPayableNOsForRentCar(Arrays.asList(dto.getBillNos()));
        } else {
            payableList = billPayableService.queryByPayableNOs(Arrays.asList(dto.getBillNos()), FossConstants.ACTIVE);
        }

        // 判断是否查询到应付单集合
        if (CollectionUtils.isEmpty(payableList)) {
            throw new SettlementException("没有查询到要付款的应付单！");
        }
        // 声明返回结果集
        BillPayableManageResultDto resultDto = new BillPayableManageResultDto();
        /**
         * 校验付款信息
         */
        // 校验是否通过
        validateCanPay(payableList, null);

        // 声明要返回的付款单明细列表
        List<BillPaymentAddDto> addDtoList = new ArrayList<BillPaymentAddDto>();
        //声明总金额
        BigDecimal totalAmount = BigDecimal.ZERO;

        //临时租车使用，存放费用承担部门集合
        Set<String> costDeptNames = new HashSet<String>();

        /**
         * 将数据封装到要返回的付款单dto实体列表中
         */
        for (BillPayableEntity pay : payableList) {

            //临时租车使用
            if (FossConstants.ACTIVE.equals(dto.getContainRentCar())) {
                //根据来源单号获取对应的费用承担部门名称
                costDeptNames.add(temprentalMarkService.queryBearFeesDeptName(pay.getSourceBillNo()));
            }
            // 将属性copy到付款单明细dto中
            BillPaymentAddDto addDto = new BillPaymentAddDto();
            //属性copy
            BeanUtils.copyProperties(pay, addDto);
            //设置属性值
            addDto.setCurrentPaymentAmount(pay.getUnverifyAmount());
            addDto.setTaxAmount(pay.getUnverifyAmount());//默认等于应付单未核销金额
            addDto.setTax(BigDecimal.ZERO);//默认等于0
            //设置业务发生日期--临时租车
            if (pay.getCostDate() != null) {
                addDto.setBusinessOfDate(pay.getCostDate());
            } else {
                addDto.setBusinessOfDate(new Date());
            }
            addDtoList.add(addDto);
            // 计算总金额
            totalAmount = totalAmount.add(pay.getUnverifyAmount());
        }

        //临时租车批量付款时费用承担部门必须相同,如果set集合中有多个费用承担部门则说明不相同
        if (CollectionUtils.isNotEmpty(costDeptNames) && costDeptNames.size() > 1) {
            throw new SettlementException("所选的应付单费用承担部门不同，所以不能批量付款！");
        }

        // 将数据封装到dto中
        resultDto.setAddDtoList(addDtoList);
        // 将应付单部门、公司、客户等信息封装到付款单实体中，带到付款单界面
        if (payableList.size() > 0) {
            // 获取应付单实体
            BillPayableEntity entity = payableList.get(0);
            // 声明付款单实体
            BillPaymentEntity paymentEntity = new BillPaymentEntity();
            // 进行数据转化
            paymentEntity.setCustomerCode(entity.getCustomerCode());
            //设置属性值
            paymentEntity.setCustomerName(entity.getCustomerName());
            //设置属性值
            paymentEntity.setPaymentOrgCode(entity.getPayableOrgCode());
            //设置属性值
            paymentEntity.setPaymentOrgName(entity.getPayableOrgName());
            //设置保理的值
            if (null == entity.getFactoring() || StringUtils.isEmpty(entity.getFactoring())) {
                paymentEntity.setFactoring(FossConstants.NO);
            } else {
                paymentEntity.setFactoring(entity.getFactoring());
                if (entity.getFactoring().equals(FossConstants.YES)) {
                    //设置属性值
                    paymentEntity.setFactorBeginTime(entity.getFactorBeginTime());
                    //设置属性值
                    paymentEntity.setFactorEndTime(entity.getFactorEndTime());
                    //设置属性值
                    paymentEntity.setFactorAccount(entity.getFactorAccount());
                    //设置属性值
                    paymentEntity.setCusCode(entity.getCusCode());
                }
            }
            //调用综合接口查询部门信息
            OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPayableOrgCode());
            //如果部门为空，则抛出异常
            if (orgEntity == null) {
                throw new SettlementException("没有查询到应付单部门所对应的部门组织！");
            }
            //设置部门编码和名称
            paymentEntity.setPaymentCompanyCode(orgEntity.getSubsidiaryCode());
            //设置部门名称
            paymentEntity.setPaymentCompanyName(orgEntity.getSubsidiaryName());
            //设置金额
            paymentEntity.setAmount(totalAmount);
            // 默认为现金
            paymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
            // 封装银行账号信息
            if (StringUtils.isNotBlank(entity.getAccountNo()) && StringUtils.isNotBlank(entity.getPaymentCategories())
                    && (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER.equals(entity.getPaymentCategories())
                    || SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER.equals(entity.getPaymentCategories()))) {
                paymentEntity.setAccountType(convertCRMAccountType(entity.getAccountType()));//账户类型
                paymentEntity.setAccountNo(entity.getAccountNo());//账号
                paymentEntity.setPayeeName(entity.getPayeeName());//收款人
                paymentEntity.setProvinceCode(entity.getProvinceCode());//开户行省份
                paymentEntity.setProvinceName(entity.getProvinceName());//省份编码
                paymentEntity.setCityCode(entity.getCityCode());//开户行城市
                paymentEntity.setCityName(entity.getCityName());//城市编码
                paymentEntity.setBankHqCode(entity.getBankHqCode());//开户行编码
                paymentEntity.setBankHqName(entity.getBankHqName());//开户行名称
                paymentEntity.setBankBranchCode(entity.getBankBranchCode());//支行编码
                paymentEntity.setBankBranchName(entity.getBankBranchName());//支行名称
            }

            //临时租车 单独使用 --已预提为预提部门
            if (StringUtils.isNotBlank(entity.getCostDeptCode())) {
                paymentEntity.setCostDeptCode(entity.getCostDeptCode());//费用承担部门
                paymentEntity.setCostDeptName(entity.getCostDeptName());//费用承担部门
            } else {
                if (CollectionUtils.isNotEmpty(costDeptNames)) {
                    String[] temps = costDeptNames.toArray(new String[0]);
                    paymentEntity.setCostDeptName(temps[0]);//费用承担部门名称

                    if (StringUtils.isNotBlank(temps[0])) {
                        //根据费用承担部门名称查询code
                        CostCenterDeptEntity deptEntity = new CostCenterDeptEntity();
                        deptEntity.setDeptName(temps[0]);
                        deptEntity.setActive(FossConstants.ACTIVE);
                        deptEntity.setStatus("0");//正常0，已封存1，已撤销2，待撤销3，待清理4
                        deptEntity.setIsFreeze("0");// 是否冻结 1是0否
                        deptEntity.setIsCostOrgUnit("1");//是否成本中心 1是 0否
                        List<CostCenterDeptEntity> costDeptList = commonCostCenterDeptService.queryCostDeptByCondition(deptEntity, Integer.MAX_VALUE, 0);
                        if (CollectionUtils.isNotEmpty(costDeptList)) {
                            for (CostCenterDeptEntity costDeptEntity : costDeptList) {
                                if (temps[0].equals(costDeptEntity.getDeptName())) {
                                    paymentEntity.setCostDeptCode(costDeptEntity.getDeptCode());//费用承担部门编码
                                    break;
                                }
                            }
                        } else {
                            paymentEntity.setCostDeptCode(null);//费用承担部门编码
                            paymentEntity.setCostDeptName(null);//费用承担部门名称
                        }
                    }
                }
            }
            //如果不为空，则表示已经预提。则需要从成本中心获取信息
            if (!StringUtils.isEmpty(entity.getCostDeptCode())) {
                CostCenterDeptEntity costEntity = new CostCenterDeptEntity();
                costEntity.setDeptCode(entity.getCostDeptCode());
                costEntity.setStatus("0");//正常0，已封存1，已撤销2，待撤销3，待清理4
                costEntity.setIsFreeze("0");// 是否冻结 1是0否
                costEntity.setIsCostOrgUnit("1");//是否成本中心 1是 0否
                //需要判断当前登陆部门是否为成本中心
                List<CostCenterDeptEntity> costCenterList = commonCostCenterDeptService.queryCostDeptByCondition(costEntity, Integer.MAX_VALUE, 0);
                //判断费用承担部门
                if (!CollectionUtils.isEmpty(costCenterList)) {
                    //货物费用承担部门类型
                    CostCenterDeptEntity costCenter = costCenterList.get(0);
                    resultDto.setCostDeptType(costCenter.getTypeCode());
                }
            }

            // 将付款单实体封装到dto中
            resultDto.setPaymentEntity(paymentEntity);
        }
        //记录日志
        LOGGER.info("应付单付款操作结束!");
        // 封装返回数据
        return resultDto;
    }

    /**
     * 审核应付单
     *
     * @param 前台选择需要审核单据 ，当前登录信息
     * @return
     * @author 045738-foss-maojianqiang
     * @date 2012-12-20 下午1:53:24
     */
    @Override
    @Transactional
    public void auditPayable(BillPayableManageDto dto, CurrentInfo currentInfo) {
        LOGGER.info("应付单审核操作开始：应付单数量为" + dto.getBillNos().length);
        // 判断需要审核单据
        if (dto.getBillNos() == null || dto.getBillNos().length == 0) {
            throw new SettlementException("没有传入需要审核应付单号");
        }
        // 调用应付单接口查询应付单
        List<String> payableNos = Arrays.asList(dto.getBillNos());
        //调用接口查询
        List<BillPayableEntity> payableList = billPayableService.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
        // 校验是否可以审核
        validateCanAutid(payableList, payableNos, currentInfo);

        //声明审核接口要用的参数
        BillPayableDto auditDto = new BillPayableDto();
        //设置要审核单据
        auditDto.setBillPayables(payableList);
        // 调用接口，记性审核操作
        billPayableService.batchAuditBillPayable(auditDto, currentInfo);
        //插入日志
        addOperateLog(payableList, SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT, null, currentInfo);
        LOGGER.info("应付单审核操作结束");
    }

    /**
     * 校验是否可以审核
     *
     * @param 应付单列表 ，审核应付单号，当前登录信息
     * @return
     * @author 045738-foss-maojianqiang
     * @date 2012-12-20 下午2:04:23
     */
    private void validateCanAutid(List<BillPayableEntity> payableList,
                                  List<String> payableNos, CurrentInfo currentInfo) {
        // 判断当前用户是否登录
        if (currentInfo == null || currentInfo.getEmpCode() == null) {
            throw new SettlementException("当前用户没有进行登录操作，不能进行操作！");
        }
        // 校验查询结果是否与传入应付单号一致
        compareResultToQueryParams(payableNos, payableList);
        // 校验应付单是否在目标单据内,将定义好的可以审核单据类型数组转化为list
        List<String> targetTypeList = Arrays.asList(SettlementConstants.AUDIT_OR_UNAUDIT_TYPES);
        // 循环判断
        for (BillPayableEntity entity : payableList) {
            //判断是否有效
            if (!FossConstants.ACTIVE.equals(entity.getActive())) {
                throw new SettlementException("应付单号：" + entity.getPayableNo() + "为无效应付单，不能进行审核操作！");
            }
            // 如果应付单不是规定单据类型，不能进行审核
            if (!targetTypeList.contains(entity.getBillType())) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的单据类型不是空运代理运费等类型，不能进行审核操作！");
            }
            // 判断审核状态
            if (entity.getApproveStatus() == null) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的审核状态为空，不能进行审核操作！");
            }
            // 判断审核状态为"未审核"
            if (!SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT.equals(entity.getApproveStatus())) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的审核状态不为未审核，不能进行审核操作！");
            }
        }
    }

    /**
     * 反审核应付单
     *
     * @param 前台选择需要反审核单据 ，当前登录信息
     * @return
     * @author 045738-foss-maojianqiang
     * @date 2012-12-20 下午1:53:24
     */
    @Override
    @Transactional
    public void unAuditPayable(BillPayableManageDto dto, CurrentInfo currentInfo) {
        LOGGER.info("应付单反审核操作：应付单数量为" + dto.getBillNos().length);
        // 判断需要审核单据
        if (dto.getBillNos() == null || dto.getBillNos().length == 0) {
            throw new SettlementException("没有传入需要反审核应付单号");
        }
        // 调用应付单接口查询应付单
        List<String> payableNos = Arrays.asList(dto.getBillNos());
        //查询
        List<BillPayableEntity> payableList = billPayableService.queryByPayableNOs(payableNos, FossConstants.ACTIVE);
        // 校验是否可以反审核
        validateCanUnAutid(payableList, payableNos, currentInfo);
        //声明审核接口要用的参数
        BillPayableDto unAuditDto = new BillPayableDto();
        //设置要审核单据
        unAuditDto.setBillPayables(payableList);
        // 调用接口，记性审核操作
        billPayableService.batchReverseAuditBillPayable(unAuditDto, currentInfo);
        //插入日志
        addOperateLog(payableList, SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT, null, currentInfo);
        //记录日志
        LOGGER.info("应付单反审核操作结束");
    }

    /**
     * 校验是否可以反审核
     *
     * @param 应付单列表 ，审核应付单号，当前登录信息
     * @return
     * @author 045738-foss-maojianqiang
     * @date 2012-12-20 下午3:06:24
     */
    private void validateCanUnAutid(List<BillPayableEntity> payableList,
                                    List<String> payableNos, CurrentInfo currentInfo) {
        // 判断当前用户是否登录
        if (currentInfo == null || currentInfo.getEmpCode() == null) {
            throw new SettlementException("当前用户没有进行登录操作，不能进行操作！");
        }
        // 校验查询结果是否与传入应付单号一致
        compareResultToQueryParams(payableNos, payableList);
        // 校验应付单是否在目标单据内,将定义好的可以审核单据类型数组转化为list
        List<String> targetTypeList = Arrays.asList(SettlementConstants.AUDIT_OR_UNAUDIT_TYPES);
        // 循环判断
        for (BillPayableEntity entity : payableList) {
            //判断是否有效
            if (!FossConstants.ACTIVE.equals(entity.getActive())) {
                throw new SettlementException("应付单号：" + entity.getPayableNo() + "为无效应付单，不能进行反审核操作！");
            }
            // 如果应付单不是规定单据类型，不能进行审核
            if (!targetTypeList.contains(entity.getBillType())) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的单据类型不是空运代理运费等类型，不能进行反审核操作！");
            }
            // 判断审核状态
            if (entity.getApproveStatus() == null) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的审核状态为空，不能进行反审核操作！");
            }
            // 判断审核状态为"未审核"
            if (!SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的审核状态不为已审核，不能进行反审核操作！");
            }
            // 判断支付状态为"未付款"
            if (!SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO.equals(entity.getPayStatus())) {
                throw new SettlementException("应付单" + entity.getPayableNo() + "的支付状态不是未支付，则表示该应付单已经付款，不能进行反审核操作！");
            }
        }
    }

    /**
     * 封装excel文件
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-11-21 上午10:06:12
     * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice#exportBillPayable(com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto)
     */
    public HSSFWorkbook exportBillPayable(BillPayableManageDto dto,
                                          CurrentInfo currentInfo) {
        //记录日志
        LOGGER.info("应付单导出操作开始");
        /**
         * 如果导出列头名称不存在，则抛出异常提示
         */
        if (dto.getArrayColumnNames() == null || dto.getArrayColumnNames().length == 0) {
            throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
        }
        /**
         * 如果导出列头名称不存在，则抛出异常提示
         */
        if (dto.getArrayColumns() == null || dto.getArrayColumns().length == 0) {
            throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
        }
        // 调用执行方法，获取结果集
        BillPayableManageResultDto resultDto = queryBillPayable(dto, 0,
                Integer.MAX_VALUE, currentInfo);
        // 判断要导出数据是否存在，若不存在，则抛出异常提示
        if (resultDto == null || resultDto.getList() == null || resultDto.getList().size() == 0) {
            throw new SettlementException("没有要导出的数据!");
        }
        // 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
        List<List<String>> rowList = convertListFormEntity(resultDto.getList(), dto.getArrayColumns());

        // 获取导出数据
        SheetData data = new SheetData();
        // 设置导出列头
        data.setRowHeads(dto.getArrayColumnNames());
        data.setRowList(rowList);
        // 获取平台提供导出函数
        ExcelExport export = new ExcelExport();
        // 返回wookbook
        HSSFWorkbook wookbook = export.exportExcel(data, SettlementConstants.EXCEL_SHEET_NAME,
                SettlementConstants.EXPORT_EXCEL_MAX_COUNTS);
        LOGGER.info("应付单导出操作结束");
        return wookbook;
    }

    /**
     * list的实体转化成list<list<String>
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-11-21 下午2:25:57
     */
    private List<List<String>> convertListFormEntity(
            List<BillPayableEntity> list, String[] header) {
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();

        //声明字典集合
        List<String> termCodes = new ArrayList<String>();
        termCodes.add(DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
        termCodes.add(DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS);
        termCodes.add(DictionaryConstants.BILL_PAYABLE__PAY_STATUS);
        termCodes.add(DictionaryConstants.FOSS_BOOLEAN);
        //后台获取数据字典对应的数据
        Map<String, Map<String, Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);

        // 循环进行封装
        for (BillPayableEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(
                        BillPayableEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    // 如果为日期，需要进行格式化
                    if (Date.class.equals(field.getType()) && fieldValue != null) {
                        //日期转化
                        fieldValue = DateUtils.convert((Date) fieldValue, DateUtils.DATE_TIME_FORMAT);
                    }
                    // 将字段封装到list中
                    if (fieldValue != null) {
                        // 如果为单据子类型，则需要转化
                        if (columnName.equals("billType")) {
                            //数据字典转化
                            fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
                                    DictionaryConstants.BILL_PAYABLE__BILL_TYPE,
                                    fieldValue.toString());
                            //如果为审核状态，则需要转化
                        } else if (columnName.equals("approveStatus")) {
                            //数据字典转化
                            fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
                                    DictionaryConstants.BILL_PAYABLE__APPROVE_STATUS,
                                    fieldValue.toString());
                        } else if (columnName.equals("productCode")) {
                            fieldValue = productService.getProductByCache(fieldValue.toString(), new Date()).getName();
                        } else if (columnName.equals("payStatus")) {
                            //数据字典转化
                            fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
                                    DictionaryConstants.BILL_PAYABLE__PAY_STATUS,
                                    fieldValue.toString());
                        } else if (columnName.equals("active") || columnName.equals("isRedBack")
                                || columnName.equals("isInit")) {
                            //数据字典转化
                            fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
                                    DictionaryConstants.FOSS_BOOLEAN,
                                    fieldValue.toString());
                        } else if (columnName.equals("invoiceMark")) {
                            if (fieldValue.toString().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE)) {
                                fieldValue = "01-运输专票11%";
                            } else if (fieldValue.toString().equals(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO)) {
                                fieldValue = "02-非运输专票";
                            }
                        }
                        //设置属性值    --BAMP空指针异常处理 --243921
                        if (fieldValue != null && fieldValue != "") {
                            rowList.add(fieldValue.toString());
                        } else {
                            rowList.add(null);
                        }
                    } else {
                        rowList.add(null);
                    }
                }
            }
            sheetList.add(rowList);
        }
        return sheetList;
    }

    /**
     * 进行dto校验及一些信息的添加
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-11-21 下午2:00:46
     */
    private void validateDto(BillPayableManageDto dto, CurrentInfo currentInfo) {
        //声明目标部门集合
        List<String> targetLsit = new ArrayList<String>();

        //如果部门存在，则获取当前部门与用户所管辖部门的交集
        if (StringUtils.isNotBlank(dto.getPayableOrgCode())) {
            //则需要查看小区是否选对 或者大区是否选对
            if (StringUtils.isNotBlank(dto.getSmallRegion())) {
                List<String> smallRegionDepts = new ArrayList<String>();
                //调用综合方法查询
                List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto.getSmallRegion());
                //循环部门，获取用户所管辖部门与查询到部门的交集
                for (OrgAdministrativeInfoEntity en : orgList) {
                    smallRegionDepts.add(en.getCode());
                }
                //如果所选部门与所选小区不符合，则查询不到
                if (!smallRegionDepts.contains(dto.getPayableOrgCode())) {
                    throw new SettlementException("所选小区与所选部门不相符，查询不到数据！");
                }
            } else if (StringUtils.isNotBlank(dto.getLargeRegion())) {
                List<String> largeRegionDepts = new ArrayList<String>();
                //调用综合方法查询
                List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto.getLargeRegion());
                //循环部门，获取用户所管辖部门与查询到部门的交集
                for (OrgAdministrativeInfoEntity en : orgList) {
                    largeRegionDepts.add(en.getCode());
                }
                //如果所选部门与所选大区不符合，则查询不到
                if (!largeRegionDepts.contains(dto.getPayableOrgCode())) {
                    throw new SettlementException("所选大区与所选部门不相符，查询不到数据！");
                }
            }
            targetLsit.add(dto.getPayableOrgCode());
            //如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集
        } else if (StringUtils.isNotBlank(dto.getSmallRegion())) {
            //调用综合方法查询
            List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto.getSmallRegion());
            //循环部门，获取用户所管辖部门与查询到部门的交集
            for (OrgAdministrativeInfoEntity en : orgList) {
                targetLsit.add(en.getCode());
            }
            //如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集
        } else if (StringUtils.isNotBlank(dto.getLargeRegion())) {
            //调用综合方法查询
            List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(dto.getLargeRegion());
            //循环部门，获取用户所管辖部门与查询到部门的交集
            for (OrgAdministrativeInfoEntity en : orgList) {
                targetLsit.add(en.getCode());
            }
            //如果都不存在，则获取默认用户所管辖部门集合
        }
        // 设置可查询部门结果集
        dto.setOrgCodeList(targetLsit);
        //包装当前登录人编码 为后数据权限传参数
        dto.setEmpCode(currentInfo.getEmpCode());
    }

    /**
     * 校验是否可以支付
     *
     * @param payableList--应付单列表 ，payType--付款单的付款类型（此参数是在录入付款单调用该方法时传入）
     * @author 045738-foss-maojianqiang
     * @date 2012-11-22 下午4:26:38
     */
    public void validateCanPay(List<BillPayableEntity> payableList, String payType) {
        //声明付款的应付单中存在装卸费
        boolean existServiceFee = false;
        //声明付款的应付单中存在不是装卸费的类型
        boolean existNotServiceFee = false;
        //存在支付类型为电汇或核销后电汇的理赔应付单
        boolean existNotTTCategories = false;
        //存在支付类型为现金或核销后现金的理赔应付单
        boolean existNotCHCategories = false;
        //是否押回单到达
        String isReturnBackBalance = "";
        // 声明客户编码
        String customerCode = "";
        //声明单据所属的单据类型
        String workFlowType = null;
        //声明应付单银行账号
        String accountNo = null;
        //声明循环次数
        int cycleTimes = 0;
        //理赔单的应付单号
        String calimNo = null;
        //声明装卸费联系人
        String customerContactName = null;
        //声明装卸费联系人手机
        String customerPhone = null;
        //----临时租车  校验  单据子类型,预提状态,费用承担部门
        String billType = null;
        String witholdingStatus = null;
        String costDeptCode = null;
        //预提应付单列表---后面用来查询费用承担部门和 预提状态
        List<String> witholdingStatusList = new ArrayList<String>();

        // 声明运单号集合
        List<String> waybillNos = new ArrayList<String>();
        //付款对接系统  Y--财务共享，N--代表费控
        String payToSystem = BillPaymentPayService.getPayToSystem();

        //是否外请车--外请车是单独付款故而只需要判断一条是即可
        List<String> driverList = Arrays.asList(SettlementESBDictionaryConstants.WORKFLOW_DETAIL_DRIVER);

        //超时装卸费付款限制
        int overdueSfPayableTimeoutLimit = -1;

        BigDecimal partnerFreightAmount = BigDecimal.valueOf(0);

        // 循环校验
        for (BillPayableEntity bill : payableList) {
            //判断临时租车应付单不能和别的类型应付单一起付款
            if (StringUtils.isBlank(billType)) {
                billType = bill.getBillType();
            } else {
                //如果包含临时租车，则判断必须单独付款
                if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(bill.getBillType())) {
                    if (!billType.equals(bill.getBillType())) {
                        throw new SettlementException("临时租车应付单不能与其他类型的应付单合并付款");
                    }
                }
            }

            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__DISCOUNT.equals(bill.getBillType())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "为折扣应付单不允许付款");
            }

            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(bill.getBillType())) {
                partnerFreightAmount = partnerFreightAmount.add(bill.getUnverifyAmount());
            }
            //ddw
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_PAYABLE.equals(bill.getBillType())
                    && (StringUtils.isEmpty(bill.getStatementBillNo()) || SettlementConstants.DEFAULT_BILL_NO.equals(bill.getStatementBillNo()))) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "为合伙人到达提成应付单不允许付款");
            }

            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__DELEGATION_DELIVERY_FEE.equals(bill.getBillType())
                    && (StringUtils.isEmpty(bill.getStatementBillNo()) || SettlementConstants.DEFAULT_BILL_NO.equals(bill.getStatementBillNo()))) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "为合伙人委托派费应付单不允许付款");
            }

            //临时租车判断预提状态，费用承担部门
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__RENT_CAR.equals(bill.getBillType())) {
                //获取预提状态
                String nextStatus = (bill.getWorkflowNo() == null ?
                        SettlementDictionaryConstants.WITHHOLDING_STATUS_NOT_TRANSFER :
                        SettlementDictionaryConstants.WITHHOLDING_STATUS_TRANSFERED);
                costDeptCode = costDeptCode == null ? bill.getCostDeptCode() : costDeptCode;
                //已预提和未预提要分开
                if (StringUtils.isBlank(witholdingStatus)) {
                    //设置预提状态
                    witholdingStatus = nextStatus;
                } else {
                    if (!witholdingStatus.equals(nextStatus)) {
                        throw new SettlementException(bill.getPayableNo() + "：未预提的临时租车费和已预提的临时租车费不能合并付款!");
                    }
                }
                //判断费用承担部门是否一致
                if (StringUtils.isNotBlank(costDeptCode) && StringUtils.isNotBlank(bill.getCostDeptCode())) {
                    if (!costDeptCode.equals(bill.getCostDeptCode())) {
                        throw new SettlementException(bill.getPayableNo() + "：的费用承担部门和其它应付单的费用承担部门不一致，不能合并付款！");
                    }
                }
                witholdingStatusList.add(bill.getPayableNo());
            }

            //循环次数
            cycleTimes = cycleTimes + 1;
            //代收货款类型不能支付
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(bill.getBillType())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo()
                        + "为应付代收货款，不能在此处进行支付，需要统一走代收货款支付流程！");
            }

            //判断是否有效
            if (!FossConstants.ACTIVE.equals(bill.getActive())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "为无效应付单，不能进行付款操作！");
            }

            // 审核状态必须是已审核
            if (bill.getApproveStatus() == null || !SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(bill.getApproveStatus())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "还未审核，不能进行付款！");
            }
            // 完全核销的应付单不能付款
            if (bill.getUnverifyAmount() == null || BigDecimal.ZERO.compareTo(bill.getUnverifyAmount()) == 0) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "已经核销完毕，不能付款！");
            }
            // 如果没有生效，则不能付款
            if (SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO.equals(bill.getEffectiveStatus())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "还未生效，不能进行付款操作！");
            }
            // 如果存在付款单号，则表示已经付过款，不能再次付款
            if (!SettlementConstants.DEFAULT_BILL_NO.equals(bill.getPaymentNo()) && StringUtils.isNotBlank(bill.getPaymentNo())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "已经付过一次款，一个付款单只能付款一次！");
            }
            //非装卸费校验
            if (!SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(bill.getBillType())) {
                //声明不存在装卸费类型
                existNotServiceFee = true;
                // 判断所选客户编码是否一致
                if (StringUtils.isBlank(customerCode)) {
                    customerCode = bill.getCustomerCode();
                } else {
                    //判断是否同一客户进行支付
                    if (!customerCode.equals(bill.getCustomerCode())) {
                        throw new SettlementException("应付单号：" + bill.getPayableNo() + "的客户编码与上面所勾选客户编码的不一致！");
                    }
                }
                //增加装卸费判断，装卸费可以没有客户编码，但联系人和联系人手机一定要有，且必须相等才能同时
            } else {
                //声明存在装卸费类型
                existServiceFee = true;
                //判断联系人编码和联系人电话是否都为空
                if ((StringUtils.isBlank(bill.getCustomerContactName()) || StringUtils.isBlank(bill.getCustomerPhone()))) {
                    throw new SettlementException("应付单号：" + bill.getPayableNo() + "为装卸费类型，对应的联系人和联系人电话不能为空！");
                }
                //初始化联系人和联系人手机
                if (StringUtils.isBlank(customerContactName) && StringUtils.isBlank(customerPhone)) {
                    //客户联系人
                    customerContactName = bill.getCustomerContactName();
                    //联系人手机
                    customerPhone = bill.getCustomerPhone();
                    //装卸费支付，联系人和联系人手机必须相等
                } else if (!StringUtils.equals(customerContactName, bill.getCustomerContactName())
                        || !StringUtils.equals(customerPhone, bill.getCustomerPhone())) {
                    throw new SettlementException("应付单号：" + bill.getPayableNo() + "为装卸费类型，与本次所勾选的其它装卸费的联系人或联系人电话不相等，不能进行付款！");
                }
                /*
				 * 装卸费超时付款申请需求添加限制  装卸费超过系统配置天数须在"超时装卸费管理"界面审批同意后后付款
				 * Yang Shushuo 105762 2014-5-13 下午4:26:00
				 */
                if (overdueSfPayableTimeoutLimit == -1) {
                    String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_OVERDUE_SF_PAYABLE_TIMEOUT_LIMIT);
                    if (StringUtils.isBlank(configString)) {
                        throw new SettlementException("请联系系统管理员在系统中结算模块配置参数:装卸费超时天数限制");
                    } else {
                        overdueSfPayableTimeoutLimit = Integer.parseInt(configString);
                    }
                }
                //超时时间点
                Date overdueSfPayableTime = DateUtils.addDayToDate(DateUtils.getStartDatetime(new Date()), -overdueSfPayableTimeoutLimit);

                //是否超期
                if (bill.getBusinessDate().before(overdueSfPayableTime)) {
                    OverdueSFPaymentApplyDto dto = overdueSFPaymentApplyService.queryOneByPayableNo(bill.getPayableNo());
                    if (dto == null || !(dto.getAuditStatus().equals(SettlementDictionaryConstants.OVERDUE_SERVICE_FEE_AUDIT_STATUS_PASSED))) {
                        throw new SettlementException("该装卸费应付单" + bill.getPayableNo() + "已超出付款时间，请在装卸费付款审批界面发起申请，审批同意后再进行付款操作。");
                    }
                }
            }
            //装卸费必须单独支付
            if (existServiceFee && existNotServiceFee) {
                throw new SettlementException("付款明细中存在装卸费，装卸费必须单独支付 ！");
            }

            //如果支付类别为核销，则抛出异常
            if (StringUtils.isNotEmpty(bill.getStatementBillNo()) && !SettlementConstants.DEFAULT_BILL_NO.equals(bill.getStatementBillNo())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo()
                        + "已经进入对账单，不能进行付款操作！");
            }
            /**
             * 2013-3-5 增加银行账号判断
             */
            // 判断所选银行账号是否一致
            //初始化银行账号字段
            if (cycleTimes == 1) {
                //初始化应付单号
                calimNo = bill.getPayableNo();
                accountNo = bill.getAccountNo();
                //如果银行账号不相等，则抛出提示
            } else if (!StringUtils.equals(accountNo, bill.getAccountNo())) {
                throw new SettlementException("应付单号：" + bill.getPayableNo() + "的银行账号与" + calimNo + "的银行账号不相同，不能一起支付");
            }
            /**
             * 增加外请车尾款判断
             */
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK1_LAST.equals(bill.getBillType())) {
                //调用中转接口，判断尾款是否可以支付
                outsideVehicleChargeService.isBillPayable(bill.getSourceBillNo());
            }
            //如果为整车或外请车，则需要增加判断 押回单到达必须单独付款
            if (driverList.contains(bill.getBillType())) {
                //如果对接的是费控则，需要判断押回单到达。押回单到达类型不能与费押回单到达类型一块付款
                if (SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)) {
                    //判断付款类型
                    if (StringUtils.isEmpty(bill.getPayableType())) {
                        throw new SettlementException("应付单：" + bill.getPayableNo() + "的付款类型为空！没法判断是否为押回单到达！");
                    }
                    //获取外请车应付单支付类型
                    if (StringUtils.isEmpty(isReturnBackBalance)) {
                        isReturnBackBalance = getReturnBackBalance(bill.getPayableType());
                    } else {
                        //获取押回单到达类型
                        String isReturnBackBalanceNew = null;
                        isReturnBackBalanceNew = getReturnBackBalance(bill.getPayableType());
                        //判断是否都为押回单到达
                        if (!isReturnBackBalance.equals(isReturnBackBalanceNew)) {
                            throw new SettlementException("应付单：" + bill.getPayableNo() + "与所选应付单不是同一种外请车类型。月结与非月结不能一起付款！押回单到达与非押回单到达不能一起付款！");
                        }
                    }
                }
            }

            /**
             * 2012-2-3 增加理赔支付方式校验
             */
            if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(bill.getBillType())) {
                //支付类别
                String paymentType = bill.getPaymentCategories();
                //如果支付类别为核销，则抛出异常
                if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF.equals(paymentType)) {
                    throw new SettlementException("应付单号：" + bill.getPayableNo() + "为理赔应付单，其支付类型为核销类型，不能进行付款！");
                }
                //电汇或核销后电汇
                if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER.equals(paymentType)
                        || SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER.equals(paymentType)) {
                    existNotTTCategories = true;
                    //现金或核销后现金
                } else {
                    existNotCHCategories = true;
                }
                //同时存在现金/核销后现金和电汇/核销后电汇支付类型的理赔应付单付款
                if (existNotTTCategories && existNotCHCategories) {
                    throw new SettlementException("应付单号：" + bill.getPayableNo() + "为理赔应付单，其支付类别与前面所选单据支付类别不能同时进行现金或电汇付款！");
                }
                //如果付款类型不为空，则表示进行录入付款单操作，此处需要校验
                if (StringUtils.isNotEmpty(payType)) {
                    //如果为电汇，则应付单支付类别必须为电汇或核销后电汇
                    if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(payType)) {
                        //判断是否电汇或核销后电汇
                        if (!SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER.equals(paymentType)
                                && !SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER.equals(paymentType)) {
                            throw new SettlementException("应付单号：" + bill.getPayableNo()
                                    + "为理赔应付单，其支付状态为现金或核销后现金，只支持现金付款");

                        }
                    } else {
                        //判断是否现金或核销后现金
                        if (!SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__CASH.equals(paymentType)
                                && !SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_CASH.equals(paymentType)) {
                            throw new SettlementException("应付单号：" + bill.getPayableNo()
                                    + "为理赔应付单，其支付状态为电汇或核销后电汇，只支持电汇付款");
                        }
                    }
                }
                //如果为理赔应付单，且其支付状态为核销后现金或核销后电汇，则必须判断其是否核销完毕应收单
                if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER.equals(paymentType)
                        || SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_CASH.equals(paymentType)) {
                    //判断应付单是否进行核销,如果应付单未核销过，则抛出异常
                    if (bill.getAmount().compareTo(bill.getUnverifyAmount()) == 0) {
                        throw new SettlementException("应付单号：" + bill.getPayableNo()
                                + "为理赔应付单，其支付状态为核销后现金或核销后电汇，必须先进行核销，才能付款!");
                    }
                    //判断该客户是否还存在未完全核销的应收单
                    boolean isExist = billReceivableService.queryIsExistsReceivableAmountByCustomerCode(bill.getCustomerCode());
                    //如果存在未核销的应收单，则抛出异常
                    if (isExist) {
                        throw new SettlementException("应付单号：" + bill.getPayableNo()
                                + "为理赔应付单，其支付状态为核销后现金或核销后电汇，但其存在未核销的应收单，不能进行付款操作！");
                    }
                }

            }
            //进行单据类型进行判断  --此处只需要判断是日常或成本那种工作流，具体是付款还是报销无所谓
            //此处因为临时租车费用类型是在录入付款单地方选择，所以此处默认为临时租车
            if (StringUtils.isBlank(workFlowType)) {
                workFlowType = BillPaymentPayService.getWorkFlowTypeByBillType(bill.getPayableNo(), bill.getBillType(), SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY, SettlementDictionaryConstants.RENTCAR_COST_TYPE_RENT_CAR_FEE);
            } else {
                //获取下一次的类型与第一次工作流类型进行比较  --此处只需要判断是日常或成本那种工作流，具体是付款还是报销无所谓
                String workFlowTypeNew = BillPaymentPayService.getWorkFlowTypeByBillType(bill.getPayableNo(), bill.getBillType(), SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY, SettlementDictionaryConstants.RENTCAR_COST_TYPE_RENT_CAR_FEE);
                //如果两次工作流类型不等，则抛出异常
                if (!StringUtils.equals(workFlowType, workFlowTypeNew)) {
                    throw new SettlementException("应付单" + bill.getPayableNo() + "和勾选的别的应付单属于不同付款工作流类型，不能进行付款！");
                }
            }
            //添加运单号
            if (StringUtils.isNotEmpty(bill.getWaybillNo()) && !SettlementConstants.DEFAULT_BILL_NO.equals(bill.getWaybillNo())) {
                // 添加运单号
                waybillNos.add(bill.getWaybillNo());
            }

        }

        //ddw
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_PAYABLE.equals(payableList.get(0).getBillType())) {
            BigDecimal num = new BigDecimal("0");
            String[] codes = new String[1];
            codes[0] = ConfigurationParamsConstants.STL_PAYABLE_PAY_AMOUNT;
            List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
            if (null != configurationParamsEntitys && configurationParamsEntitys.size() > 0) {
                String value = configurationParamsEntitys.get(0).getConfValue();
                if (value != null) {
                    num = new BigDecimal(value);
                }
            }
            if (num.compareTo(partnerFreightAmount) > 0) {
                throw new SettlementException("应付单：" + payableList.get(0).getPayableNo() + "为到付运费应付单，付款金额必须大于" + num);
            }
        }

        // 存在未受理更改单则抛出异常提示
        List<String> waybillList = null;
        if (waybillNos.size() > SettlementConstants.MAX_BILL_NO_SIZE) {
            // 算出总共要多少也放数据
            int pages = (waybillNos.size()
                    + SettlementConstants.MAX_BILL_NO_SIZE - 1)
                    / SettlementConstants.MAX_BILL_NO_SIZE;
            // 如果对账单号不为空，表示已经做了对账单，则需要判断是否对账单确认
            for (int i = 0; i < pages; i++) {
                //获取起始条数
                int start = i * SettlementConstants.MAX_BILL_NO_SIZE;
                int end = 0;
                //获取结束条数
                if (i == pages - 1) {
                    //设置结束条数
                    end = waybillNos.size();
                } else {
                    //设置结束条数
                    end = (i + 1) * SettlementConstants.MAX_BILL_NO_SIZE;
                }
                //调用接口继续查询是否存在更改单
                waybillList = waybillRfcService.isExsitsWayBillRfcs(waybillNos.subList(start, end));
            }
        } else {
            //判断集合是否为空
            if (waybillNos.size() > 0) {
                // 判断是否有未受理的更改单
                waybillList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
            }
        }
        // 判断 该集合是否存在数据，存在则抛出异常
        if (CollectionUtils.isNotEmpty(waybillList)) {
            //拼接字符串
            StringBuffer sb = new StringBuffer("应付单号：");
            //循环获取异常数据
            for (int j = 0; j < waybillList.size(); j++) {
                //循环处理
                if (j == waybillList.size() - 1) {
                    sb.append(waybillList.get(j));
                } else {
                    sb.append(waybillList.get(j));
                    sb.append(",");
                }
            }
            sb.append("存在未受理的更改单，不能进行付款操作！");
            throw new SettlementException(sb.toString());
        }
    }

    /**
     * 比较传入单号集合与查询结果条数是否一致
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-12-3 上午10:27:57
     */
    private void compareResultToQueryParams(List<String> payables,
                                            List<BillPayableEntity> payableList) {
        // 判断传入审核条数和查询条数是否一致
        if (payableList.size() != payables.size()) {
            // 声明查询结果的单号集合
            List<String> resultPayableNos = new ArrayList<String>();
            // 循环获取应付单单号集合
            for (BillPayableEntity entity : payableList) {
                resultPayableNos.add(entity.getPayableNo());
            }
            // 循环判断哪个单号没有查询到
            StringBuffer sb = new StringBuffer("应付单：");
            for (String s : payables) {
                // 如果该单号没查询到，则将其返回给用户提示
                if (!resultPayableNos.contains(s)) {
                    sb.append(s);
                    sb.append(" ");
                }
            }
            sb.append("已经发生变化，请刷新后再试！");
            throw new SettlementException(sb.toString());
        }
    }

    /**
     * 循环插入日志
     *
     * @author 045738-foss-maojianqiang
     * @date 2012-12-3 上午11:41:02
     */
    private void addOperateLog(List<BillPayableEntity> payableList,
                               String operateType, String notes, CurrentInfo currentInfo) {
        LOGGER.info("应付单插入日志开始：条数" + payableList.size());
        //声明日志集合
        List<OperatingLogEntity> list = new ArrayList<OperatingLogEntity>();
        //获取当前时间
        Date nowDate = new Date();
        // 循环插入日志
        for (BillPayableEntity en : payableList) {
            // 插入日志
            OperatingLogEntity log = new OperatingLogEntity();

            //设置
            log.setId(UUIDUtils.getUUID());
            // 设置类型
            log.setOperateBillNo(en.getPayableNo());
            // 设置操作单据类型--应付单
            log.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYABLE);
            // 设置操作类型--审核
            log.setOperateType(operateType);
            //操作时间 --
            log.setOperateTime(nowDate);
            // 设置操作备注
            log.setNotes(notes);

            // 操作组织编码
            log.setOperateOrgCode(currentInfo.getCurrentDeptCode());
            // 操作组织名称
            log.setOperateOrgName(currentInfo.getCurrentDeptName());
            // 操作用户名称
            log.setOperatorName(currentInfo.getEmpName());
            // 操作用户编码
            log.setOperatorCode(currentInfo.getEmpCode());
            //添加到日志集合中
            list.add(log);
        }

        // 调用接口进行插入日志
        operatingLogService.addBatchOperatingLog(list);
        LOGGER.info("应付单插入日志结束");
    }

    /**
     * CRM传递过来账户类型转化为费控要用的账户类型
     *
     * @param accountType
     * @author 045738-foss-maojianqiang
     * @date 2013-3-18 下午3:22:11
     */
    private String convertCRMAccountType(String accountType) {
        //非空判断
        if (StringUtils.isBlank(accountType)) {
            throw new SettlementException("账户性质不能为空");
        }
        //声明费控账户类型
        String finAccountType = null;
        //进行转化
        if (SettlementESBDictionaryConstants.CRM_ACCOUNT_TYPE_PUBLIC.equals(accountType)) {
            //返回费控外部对公账户
            finAccountType = DictionaryValueConstants.FIN_ACCOUNT_TYPE_PUBLIC;
            //CRM对私账户
        } else if (SettlementESBDictionaryConstants.CRM_ACCOUNT_TYPE_PRIVATE.equals(accountType)) {
            finAccountType = DictionaryValueConstants.FIN_ACCOUNT_TYPE_PRIVATE;
            //CRM收银员账户
        } else if (SettlementESBDictionaryConstants.CRM_ACCOUNT_TYPE_CASHIER.equals(accountType)) {
            finAccountType = DictionaryValueConstants.FIN_ACCOUNT_TYPE_CASHIER;
        } else {
            throw new SettlementException("CRM传入理赔账户性质有误！" + accountType);
        }
        return finAccountType;
    }


    /**
     * 根据付款单号和付款单明细来源单号查询应付单
     *
     * @param paymentNo
     * @param sourceBillType
     * @param active
     * @param isRedBack
     * @return
     * @author foss-qiaolifeng
     * @date 2013-3-20 下午6:03:01
     * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice#queryListByPaymentNo(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<BillPayableEntity> queryListByPaymentNo(BillPayableManageDto dto) {

        // 传入参数dto不能为空
        if (dto == null) {
            //提示查看付款单应付明细传入参数为空
            throw new SettlementException("查看付款单应付明细传入参数为空");
        }
        //付款单号、付款单明细来源单号类型不能为空
        if (StringUtils.isEmpty(dto.getPaymentNo()) || StringUtils.isEmpty(dto.getSourceBillTypeFkd())) {
            //提示查看付款单应付明细传入付款单号、来源单号类型为空
            throw new SettlementException("查看付款单应付明细传入付款单号、来源单号类型为空");
        }
        //获取应付单信息
        return billPayableQueryManageDao.queryListByPaymentNo(dto);
    }

    /**
     * 判断应付单是否为押回单到达  --费控付款用
     *
     * @param billType
     * @return
     * @author 045738-foss-maojianqiang
     * @date 2013-5-15 上午8:30:21
     */
    public static String getReturnBackBalance(String billType) {
        //声明押回单到达值
        String isReturnBackBalance = null;
        //判断月结
        if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH.equals(billType)) {
            isReturnBackBalance = SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__MONTH;
            //判断如果为应付单的支付类型为尾款押回单，则表示押回单到达
        }
        if (SettlementDictionaryConstants.BILL_PAYABLE__PAYABLE_TYPE__LAST_BACK.equals(billType)) {
            isReturnBackBalance = FossConstants.YES;
        } else {
            isReturnBackBalance = FossConstants.NO;
        }
        return isReturnBackBalance;
    }

    /**
     * 根据传的运单号和客户编码查询应收单是否已经核销
     *
     * @author 268217
     * @date 2016-04-14
     */
    @Override
    public String queryReceivableBill(BillPayableManageDto dto) {
        //查询理赔单中是否有数据
        BillClaimEntity claim = billClaimService.queryBillClaimEntity(dto.getWaybillNo());
        //判断是否为未签收理赔
        if (claim != null && SettlementDictionaryConstants.CLAIMSWAY_ONLINE.equals(claim.getClaimType())) {
            String active = SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES;
            List<String> waybillNos = new ArrayList<String>();
            waybillNos.add(dto.getWaybillNo());
            String isRedBack = SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO;
            String sourceBillType = "";
            List<String> sourceBillNos = new ArrayList<String>();
            List<String> billTypes = new ArrayList<String>();
            billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
            //查询应收单是否已核销
            List<BillReceivableEntity> receivablelist = billReceivableService.
                    queryByWaybillNosOrSourceBillNosAndBillTypes(waybillNos, sourceBillNos, sourceBillType, billTypes, active, isRedBack);
            if (CollectionUtils.isNotEmpty(receivablelist)) {
                BillReceivableEntity entity = receivablelist.get(0);
                //如果存在未核销的应收单，则抛出异常
                if (entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) > 0) {
                    return "false";
                    //throw new SettlementException("收银员未完成该单号的理赔冲应收，无法付款！");
                }
            }
        }
        return "success";
    }

    /**
     * @param billPayableQueryManageDao
     */
    public void setBillPayableQueryManageDao(
            IBillPayableQueryManageDao billPayableQueryManageDao) {
        this.billPayableQueryManageDao = billPayableQueryManageDao;
    }

    /**
     * @param billPayableService
     */
    public void setBillPayableService(IBillPayableService billPayableService) {
        this.billPayableService = billPayableService;
    }

    /**
     * @param waybillRfcService
     */
    public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
        this.waybillRfcService = waybillRfcService;
    }

    /**
     * @param operatingLogService
     */
    public void setOperatingLogService(IOperatingLogService operatingLogService) {
        this.operatingLogService = operatingLogService;
    }

    /**
     * @param orgAdministrativeInfoComplexService
     */
    public void setOrgAdministrativeInfoComplexService(
            IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    /**
     * @param orgAdministrativeInfoService
     */
    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * @param billReceivableService
     */
    public void setBillReceivableService(IBillReceivableService billReceivableService) {
        this.billReceivableService = billReceivableService;
    }

    /**
     * @param outsideVehicleChargeService
     */
    public void setOutsideVehicleChargeService(
            IOutsideVehicleChargeService outsideVehicleChargeService) {
        this.outsideVehicleChargeService = outsideVehicleChargeService;
    }

    /**
     * @return productService
     * @GET
     */
    public IProductService getProductService() {
		/*
		 *@get
		 *@ return productService
		 */
        return productService;
    }

    /**
     * @param productService
     * @SET
     */
    public void setProductService(IProductService productService) {
		/*
		 *@set
		 *@this.productService = productService
		 */
        this.productService = productService;
    }

    /**
     * @param configurationParamsService the configurationParamsService to set
     */
    public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
        this.configurationParamsService = configurationParamsService;
    }

    /**
     * @param overdueSFPaymentApplyService the overdueSFPaymentApplyService to set
     */
    public void setOverdueSFPaymentApplyService(OverdueSFPaymentApplyService overdueSFPaymentApplyService) {
        this.overdueSFPaymentApplyService = overdueSFPaymentApplyService;
    }

    public void setCommonCostCenterDeptService(
            ICommonCostCenterDeptService commonCostCenterDeptService) {
        this.commonCostCenterDeptService = commonCostCenterDeptService;
    }

    /**
     * @param temprentalMarkService the temprentalMarkService to set
     */
    public void setTemprentalMarkService(
            ITemprentalMarkService temprentalMarkService) {
        this.temprentalMarkService = temprentalMarkService;
    }

    public void setBillClaimService(IBillClaimService billClaimService) {
        this.billClaimService = billClaimService;
    }

}
