package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.context.WebApplicationContextHolder;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillDepositReceivedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IFossToFinsRemitCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountDService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.server.service.IzfbStatementManageService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillDepositReceivedEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PdaStatementManageEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.RequestGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.ReturnGreenHandWrapEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementConfReceiptEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VerificationEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CommonQueryParamDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PdaStatementManageDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPdaStatementManageDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPdaCommonService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementReceiptService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PdaSoaRepaymentEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @ClassName：ZfbStatementManageService
 * @Description:支付宝条码支付对账单管理实现类,直接调用pda服务
 * @author:378391|foss结算zhanglipeng
 * @date:2016年10月9日 下午5:36:21
 * @version
 */
public class ZfbStatementManageService implements IzfbStatementManageService {
    /**
     * 限制前台查询条数
     */
    private static int start = 0;

    private static int limit = 100;

    /**
     * 注入Service
     */
    private IPdaStatementManageDao pdaStatementManageDao;

    /**
     * Logger
     */
    private static final Logger LOGGER = LogManager.getLogger(ZfbStatementManageService.class);

    /**
     * 还款单公共service
     */
    private IBillRepaymentService billRepaymentService;

    /**
     * 核销/反核销公共service
     */
    private IBillWriteoffService billWriteoffService;

    /**
     * 对账单service
     */
    private IStatementOfAccountService statementOfAccountService;

    /**
     * 结算通用服务service
     */
    private ISettlementCommonService settlementCommonService;

    /**
     * 对账单明细service
     */
    private IStatementOfAccountDService statementOfAccountDService;

    /**
     * 应收单service
     */
    private IBillReceivableService billReceivableService;

    /**
     * 应付单service
     */
    private IBillPayableService billPayableService;

    /**
     * 预收单service
     */
    private IBillDepositReceivedService billDepositReceivedService;

    /**
     * 预付单service
     */
    private IBillAdvancedPaymentService billAdvancedPaymentService;

    /**
     * 对账单回执service
     */
    private IStatementReceiptService statementReceiptService;

    /**
     * 对账单还款数据
     */
    /* private ISoaRepaymentManageService soaRepaymentManageService; */

    /**
     * 修改对账单Service
     */
    private IStatementModifyService statementModifyService;

    /**
     * FOSS到财务第三方支付
     */
    private IFossToFinsRemitCommonService fossToFinsRemitCommonService;

    /**
     * @Description:供PDA条码支付核销方法
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:39:17
     * @param dto
     * @return
     */

    @Override
    public ReturnGreenHandWrapEntity statementRepaymentwriteoff(VerificationEntity verificationEntity) {
        ReturnGreenHandWrapEntity returnGreenHandWrapEntity = new ReturnGreenHandWrapEntity();
        // 调用查询方法查询对账单信息
        LOGGER.info("调用查询方法查询对账单信息");
        try {
            List<PdaStatementManageDto> queryStatementByNo = queryStatementByNu(verificationEntity);
            LOGGER.info("调用查询方法查询对账单信息" + queryStatementByNo);
            List<PdaStatementManageDto> statementRepayment = statementRepayment(queryStatementByNo,
                    verificationEntity);
            LOGGER.info("调用核销方法将对账单进行还款，返回参数集合" + statementRepayment.size());
            returnGreenHandWrapEntity.setIfSuccess(true);

        } catch (SettlementException e) {
            // TODO: handle exception
            returnGreenHandWrapEntity.setIfSuccess(false);
            LOGGER.info("未查询到有效的对账单信息对账单还款失败。。。。。");
        }
        LOGGER.info("返回信息" + returnGreenHandWrapEntity.isIfSuccess());
        return returnGreenHandWrapEntity;

    }

    public List<PdaStatementManageDto> queryStatementByNu(VerificationEntity dto) {
        LOGGER.info("********支付宝前台查询对账单开始************");
        // 校验参数
        if (dto == null) {
            throw new SettlementException("数据错误：传递参数为空！");
        }

        /**
         * 单号和部门不能同时为空
         */
        if (dto.getOutTradeNo() == null && dto.getOrgCode() == null) {
            throw new SettlementException("数据错误：传递单号或对账单号和登录人部门不能同时为空");
        }
        /**
         * 员工工号不能为空
         */
        if (dto.getEmpCode() == null) {
            throw new SettlementException("数据错误：员工工号不能为空！");
        }
        // 创建查询对账单DTO
        CommonQueryParamDto commonQueryParamDto = new CommonQueryParamDto();
        // 查询
        // 创建返回值
        List<String> numbers = new ArrayList<String>();
        numbers.add(dto.getOutTradeNo());
        LOGGER.info("对账单号：" + dto.getOutTradeNo());
        commonQueryParamDto.setNumbers(numbers);
        // 封装当前登录员工和部门的编码
        commonQueryParamDto.setEmpCode(dto.getEmpCode());
        LOGGER.info("当前登录员工编码：" + commonQueryParamDto.getEmpCode());
        PdaStatementManageDto statementDto = new PdaStatementManageDto();
        LOGGER.info("查询对账单数据。。。。。。。。。");
        List<PdaStatementManageEntity> list = pdaStatementManageDao.queryStatementByNo(commonQueryParamDto,
                start, limit);
        // 过滤list集合
        List<PdaStatementManageEntity> slist = new ArrayList<PdaStatementManageEntity>();
        for (PdaStatementManageEntity pdaStatementManageEntity : list) {
            if (dto.getOutTradeNo().equals(pdaStatementManageEntity.getStatementNo())) {
                slist.add(pdaStatementManageEntity);
            }
        }
        LOGGER.info("对账单数据" + list.size());
        LOGGER.info("过滤后的集合：" + slist.size());
        List<PdaStatementManageDto> lists = new ArrayList<PdaStatementManageDto>();
        statementDto.setStatementEntitys(slist);
        // 对账单号信息
        List<String> stillPaymentNusList = new ArrayList<String>();
        // 版本号集合
        List<String> versions = new ArrayList<String>();
        if (list != null && list.size() != 0) {

            // 支付方式
            statementDto.setRepaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
            LOGGER.info("付款方式为" + SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
            // 将查询到的对账单信息中的对账单号和版本号遍历出来
            for (PdaStatementManageEntity pdaStatementManageEntity : slist) {
                stillPaymentNusList.add(pdaStatementManageEntity.getStatementNo());
                versions.add(pdaStatementManageEntity.getVersion());
            }
            // 将对账单号和版本号进行封装 创建对账单号数组
            String[] arrayStrings = new String[stillPaymentNusList.size()];
            for (int i = 0; i < stillPaymentNusList.size(); i++) {
                arrayStrings[i] = stillPaymentNusList.get(i);
            }
            // 创建版本账号数组
            String[] arrayString = new String[versions.size()];
            for (int i = 0; i < versions.size(); i++) {
                arrayString[i] = versions.get(i);
            }
            statementDto.setStatementBillNos(arrayStrings);
            statementDto.setVersionNos(arrayString);
            //设置交易流水号与推送财务自助编码一致
            statementDto.setRemittanceNumber("ZFBCODE" +dto.getOutTradeNo());
            // 刷卡金额
            statementDto.setRepaymentAmount(dto.getTotalAmount());
            // 客户编码 交易号 刷卡金额
            for (PdaStatementManageEntity pdaStatementManageEntity : list) {
                statementDto.setCustomerCode(pdaStatementManageEntity.getCustomeCode());
            }
            // 员工编号 员工名称 当前登录部门编号 当前登录部门名称
            statementDto.setEmpCode(dto.getEmpCode());
            statementDto.setEmpName(dto.getEmpName());
            statementDto.setCurrentDeptCode(dto.getOrgCode());
            statementDto.setCurrentDeptName(dto.getOrgName());
            lists.add(statementDto);
        } else {
            LOGGER.info("未查询到有效的对账单信息，传入对账单号有误。。。。。");
            throw new SettlementException("未查询到有效的对账单信息，传入对账单号有误。。。。。");
        }
        // 设置DTO的值
        // 将结果放入list集合

        LOGGER.info("前台查询对账单结束。。。。。。");
        LOGGER.info("查询出的对账单号信息：" + lists.size());
        return lists;
    }

    /**
     * @Description: 还款(核销操作)
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:42:16
     * @param pdaStatementManageDtos
     * @return
     */
    @Override
    public List<PdaStatementManageDto> statementRepayment(List<PdaStatementManageDto> pdaStatementManageDtos,
            VerificationEntity verificationEntity) {
        LOGGER.info("******支付宝对账单还款开始********");
        // 返回对账单的List
        List<PdaStatementManageDto> dtos = new ArrayList<PdaStatementManageDto>();
        // 对账单数据
        PdaStatementManageDto statementDto = null;

        // 返回未核销完的对账单数据
        List<PdaStatementManageEntity> pdaStatementManageEntitys = null;

        // 如果系统无异常，则推送数据到财务的时候为已使用，否则为未使用用。默认为N
        RequestGreenHandWrapEntity paramEntity = new RequestGreenHandWrapEntity();
        // 设置错误的List
        List<String> lists = null;
        // 返回错误信息 单号+信息
        Map<String, String> msgMap = null;

        //
        String isSuccess = "Y";
        // 批量处理
        for (PdaStatementManageDto dto : pdaStatementManageDtos) {
            /**
             * 初始化集合数据
             */
            statementDto = new PdaStatementManageDto();
            pdaStatementManageEntitys = new ArrayList<PdaStatementManageEntity>();
            lists = new ArrayList<String>();
            msgMap = new HashMap<String, String>();

            try {
                // 2、校验参数 交易号 客户编码 刷卡金额
                validateParam(dto);
                // 4、还款---->核销
                // 封装PDA登录人信息
                CurrentInfo cInfo = getCurrentUserMessage(dto);

                IzfbStatementManageService zfbStatementManageService = (IzfbStatementManageService) WebApplicationContextHolder
                        .getWebApplicationContext().getBean("zfbStatementManageService");
                LOGGER.info("调用核销方法核销。。。。。。");
                zfbStatementManageService.repayment(dto, cInfo);
            } catch (BusinessException e) {
                // 获取到所有的异常单号
                isSuccess = "N";
                // 错误的运单号 + 信息
                lists = Arrays.asList(dto.getStatementBillNos());
                for (int i = 0; i < lists.size(); i++) {
                    if (i == 0) {
                        msgMap.put(lists.get(i), e.getErrorCode());
                    } else {
                        msgMap.put(lists.get(i), "");
                    }
                }

                // 设置交易流水号
                statementDto.setRemittanceNumber(dto.getRemittanceNumber());
            } finally {
                /**
                 * 不管是否核销成功数据都推送信息到财务自助
                 */
                if ("N".equals(isSuccess)) {
                    // 核销失败封装财务自助数据
                    paramEntity.setIsPush("true");// 是否需要推送
                    paramEntity.setIsException("Y");// 是否异常推送
                    paramEntity.setResource("ZFBCODE");// 来源
                    paramEntity.setCostType("4");// 设置费用类型支付宝条码支付
                    // paramEntity.setWaybillNo(dto.getStatementBillNos()[0]);
                    paramEntity.setStatementBillNos(Arrays.asList(dto.getStatementBillNos())); // 设置对账单号
                    paramEntity.setDopAmount(dto.getRepaymentAmount());
                    paramEntity.setDoptime(new Date());
                    paramEntity.setReceivableNo(verificationEntity.getCollectionAccount());// 封装汇款账号
                    paramEntity.setZfbBillNum("ZFBCODE" + verificationEntity.getOutTradeNo());// 推送财务自助编号

                } else if ("Y".equals(isSuccess)) {
                    // 判断应收单号、在线支付编号、还款金额、记账日期是否为空
                    // 核销成功封装财务自助数据
                    paramEntity.setIsPush("true");// 是否需要推送
                    paramEntity.setIsException("N");// 是否异常推送
                    paramEntity.setResource("ZFBCODE");// 来源
                    paramEntity.setCostType("4");// 设置费用类型支付宝条码支付
                    // paramEntity.setWaybillNo(dto.getStatementBillNos()[0]);//
                    paramEntity.setStatementBillNos(Arrays.asList(dto.getStatementBillNos())); // 设置对账单号
                    paramEntity.setDopAmount(dto.getRepaymentAmount());
                    paramEntity.setDoptime(new Date());
                    paramEntity.setReceivableNo(verificationEntity.getCollectionAccount());// 封装汇款账号
                    paramEntity.setZfbBillNum("ZFBCODE" + verificationEntity.getOutTradeNo());// 推送财务自助编号

                }
                // 调用财务自助接口
                if ("true".equals(paramEntity.getIsPush())) {
                    fossToFinsRemitCommonService.pushRemittanceMessToFins(paramEntity);
                    LOGGER.info("推送数据成功！");
                } else {
                    throw new SettlementException("推送到财务自助数据异常！");
                }

            }

            // 设置每个交易流水号是成功还是失败
            statementDto.setIsSuccess(isSuccess);

            // 将未核销玩的对账单数据返回到PDA
            statementDto.setStatementEntitys(pdaStatementManageEntitys);

            // 设置错误的对账单号
            statementDto.setStatementNos(lists);

            // 设置错误信息的Map
            statementDto.setMsgMap(msgMap);

            // 设置集合
            dtos.add(statementDto);
        }
        // 进行推送财务自助 TODO
        LOGGER.info("********支付宝条码支付对账单还款结束********");
        // 返回数据
        return dtos;
    }

    /**
     * @Description:校验参数
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:44:36
     * @param dto
     */
    private void validateParam(PdaStatementManageDto dto) {
        if (dto == null) {
            throw new SettlementException("数据错误：对账单还款参数为空！");
        }
        // 客户名称
        /*
         * if (StringUtil.isBlank(dto.getCustomerName())) { //throw new
         * SettlementException("数据错误：客户名称不能为空！"); }
         */
        // 客户编码
        if (StringUtil.isBlank(dto.getCustomerCode())) {
            throw new SettlementException("数据错误：客户编码不能为空！");
        }
        // 银联交易流水号
        if (StringUtil.isBlank(dto.getRemittanceNumber())) {
            throw new SettlementException("数据错误：交易号不能为空！");
        }
        // 刷卡金额
        if (BigDecimal.ZERO == dto.getRepaymentAmount()) {
            throw new SettlementException("数据错误：刷卡金额不能为0！");
        }
        /*
         * // 汇款人不能为空 if (StringUtil.isBlank(dto.getRemittanceName())) { throw new
         * SettlementException("数据错误：汇款人不能为空不能为空！"); }
         */
    }

    /**
     * @Description:通过员工工号拿到当前用户信息
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:45:11
     * @param dto
     * @return
     */
    public CurrentInfo getCurrentUserMessage(PdaStatementManageDto dto) {
        if (dto.getEmpCode() == null) {
            throw new SettlementException("数据错误:员工编号为空！");
        }

        if (dto.getEmpName() == null) {
            throw new SettlementException("数据错误：员工名称为空！");
        }

        if (dto.getCurrentDeptName() == null) {
            throw new SettlementException("数据错误：当前部门名称为空！");
        }

        if (dto.getCurrentDeptCode() == null) {
            throw new SettlementException("数据错误：当前部门编码为空！");
        }
        // 用户信息
        UserEntity ue = new UserEntity();
        EmployeeEntity ee = new EmployeeEntity();
        ee.setEmpCode(dto.getEmpCode());
        ee.setEmpName(dto.getEmpName());
        ue.setEmployee(ee);
        // 部门信息
        OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
        dept.setCode(dto.getCurrentDeptCode());
        dept.setName(dto.getCurrentDeptName());

        CurrentInfo cInfo = new CurrentInfo(ue, dept);
        return cInfo;
    }

    /**
     * @Description:还款操作 事务处理
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:45:31
     * @param statementManageDto
     * @param cInfo
     */
    @Transactional
    public void repayment(PdaStatementManageDto statementManageDto, CurrentInfo cInfo) {
        LOGGER.info("还款/批量还款单开始..." + statementManageDto.getRepaymentAmount());

        // 还款金额必须>0,否则提示还款金额小于0异常
        if (statementManageDto.getRepaymentAmount().compareTo(BigDecimal.ZERO) != 1) {
            throw new SettlementException("还款/批量还款单时还款金额必须大于0!");
        }
        LOGGER.info("对账单号信息:" + statementManageDto.toString());
        // 获取对账单号集合
        List<String> statementBillNoList = Arrays.asList(statementManageDto.getStatementBillNos());
        LOGGER.info("对账单号集合" + statementBillNoList.size());
        // 对账单号不能为空
        if (CollectionUtils.isEmpty(statementBillNoList)) {
            LOGGER.info("还款/批量还款单时,传入的对账单号为空不能正常还款");
            throw new SettlementException("传入的对账单号为空不能正常还款!");
        }

        // 根据对账单号集合获取对账单列表
        List<StatementOfAccountEntity> statementList = new ArrayList<StatementOfAccountEntity>();

        // 如果版本号数组有值
        if (statementManageDto.getVersionNos() != null && statementManageDto.getVersionNos().length > 0) {

            // 获取对账单version版本号集合
            List<Short> versionNoList = new ArrayList<Short>();
            for (String versionNo : statementManageDto.getVersionNos()) {
                versionNoList.add(Short.parseShort(versionNo.trim()));
            }

            // 对账单号和版本号集合数量不匹配，还款失败
            if (statementBillNoList.size() != versionNoList.size()) {
                LOGGER.info("还款/批量还款单时,对账单号和版本号数量不匹配,请重新查询对账单并还款");
                throw new SettlementException("对账单号和版本号数量不匹配,请重新查询对账单并还款");
            }

            // 循环调用对账单接口，按对账单号和版本号查询对账单
            for (int i = 0; i < statementBillNoList.size(); i++) {
                // 根据对账单号和版本号查询对账单
                StatementOfAccountEntity entity = statementOfAccountService.queryByStatementNoAndVersion(
                        statementBillNoList.get(i), (short) versionNoList.get(i));
                if (entity == null) {
                    LOGGER.info("还款/批量还款单时,对账单数据已更新,请重新查询对账单并还款");
                    throw new SettlementException("对账单数据已更新,请重新查询对账单并还款");
                }
                statementList.add(entity);
            }
            // 如果版本号数组没有值，直接根据对账单号查询
        } else {
            StatementOfAccountEntity entity = statementOfAccountService
                    .queryByStatementNo(statementBillNoList.get(0));
            if (entity == null) {
                LOGGER.info("POS还款时输入的对账单号有误，无法查询到该对账单信息!");
                throw new SettlementException("POS还款时没有该对账单信息，请确认对账单号是否有误!");
            }
            statementList.add(entity);
        }

        // 1、新增还款单实体，单位保存
        BillRepaymentEntity billRepaymentEntity = addBillRepayment(statementManageDto, cInfo);

        // 排序类，所有对账单按照业务日期排序
        ListComparator orderComparator = new ListComparator("businessDate");
        // 按时间排序，按业务时间先后排序
        Collections.sort(statementList, orderComparator);

        // 2、调用核销操作
        // 还款单执行每次按对账单还款后剩余金额
        BigDecimal unpaidAmountLeft = billRepaymentEntity.getAmount();

        // 获取核销批次号
        String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

        // 生成其中待核销的一个应收单，永远设置还款单的收入部门
        BillReceivableEntity rtnBillReceivableEntity = null;

        for (StatementOfAccountEntity entity : statementList) {
            if (entity == null)
                return;
            LOGGER.info("还款/批量还款单时,循环核销对账单开始,对账单号：" + entity.getStatementBillNo());

            // 如果还款单金额小于等于单个对账单的未还款金额，按还款单金额核销还款，并break
            if (unpaidAmountLeft.compareTo(entity.getUnpaidAmount()) <= 0) {
                // 设置还款金额
                billRepaymentEntity.setAmount(unpaidAmountLeft);

                // 调用核销操作
                if (rtnBillReceivableEntity == null) {
                    rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),
                            billRepaymentEntity, cInfo, writeoffBatchNo);
                } else {
                    billRepayWriteoff(entity.getStatementBillNo(), billRepaymentEntity, cInfo,
                            writeoffBatchNo);
                }

                // 修改对账单未还款金额
                entity.setUnpaidAmount(entity.getUnpaidAmount().subtract(unpaidAmountLeft));
                // 修改对账单备注信息
                if (StringUtils.isNotEmpty(entity.getNotes())) {
                    if (StringUtils.isNotBlank(statementManageDto.getNotes())) {
                        if (statementManageDto.getNotes().length() > 150) {
                            entity.setNotes(statementManageDto.getNotes().substring(0, 149));
                        } else {
                            entity.setNotes(statementManageDto.getNotes());
                        }
                    }
                    if (entity.getNotes().length() > 1000) {
                        entity.getNotes().substring(0, 999);
                    }
                    statementOfAccountService.updateStatementForWriteOff(entity, cInfo);
                } else {
                    if (StringUtils.isNotBlank(statementManageDto.getNotes())) {
                        if (statementManageDto.getNotes().length() > 150) {
                            entity.setNotes(statementManageDto.getNotes().substring(0, 149));
                        } else {
                            entity.setNotes(statementManageDto.getNotes());
                        }
                    }

                    statementOfAccountService.updateStatementForWriteOff(entity, cInfo);
                }
                // 新增对账单还款数据(账单号、还款单号、还款金额)
                addSoaRepayment(entity.getStatementBillNo(), billRepaymentEntity.getRepaymentNo(),
                        billRepaymentEntity.getAmount());

                /**
                 * 更具单据号和交易流水号去更新T+0报表的数据和明细数据
                 */
                /*
                 * updatePosCardData(entity.getStatementBillNo(),
                 * statementManageDto.getRemittanceNumber(), billRepaymentEntity.getAmount());//还款金额
                 */break;
                // 反之按对账单的未还款金额去核销
            } else if (unpaidAmountLeft.compareTo(entity.getUnpaidAmount()) > 0) {
                billRepaymentEntity.setAmount(entity.getUnpaidAmount());

                // 调用核销操作
                rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(), billRepaymentEntity,
                        cInfo, writeoffBatchNo);
                unpaidAmountLeft = unpaidAmountLeft.subtract(entity.getUnpaidAmount());

                // 修改对账单未还款金额
                entity.setUnpaidAmount(BigDecimal.ZERO);
                // 修改对账单备注信息
                if (StringUtils.isNotEmpty(entity.getNotes())) {
                    if (StringUtils.isNotBlank(statementManageDto.getNotes())) {
                        if (statementManageDto.getNotes().length() > 150) {
                            entity.setNotes(statementManageDto.getNotes().substring(0, 149));
                        } else {
                            entity.setNotes(statementManageDto.getNotes());
                        }
                    }
                    if (entity.getNotes().length() > 1000) {
                        entity.getNotes().substring(0, 999);
                    }
                    statementOfAccountService.updateStatementForWriteOff(entity, cInfo);
                } else {
                    if (StringUtils.isNotBlank(statementManageDto.getNotes())) {
                        if (statementManageDto.getNotes().length() > 150) {
                            entity.setNotes(statementManageDto.getNotes().substring(0, 149));
                        } else {
                            entity.setNotes(statementManageDto.getNotes());
                        }
                    }
                    statementOfAccountService.updateStatementForWriteOff(entity, cInfo);
                }
                // 新增对账单还款数据(账单号、还款单号、还款金额)

                addSoaRepayment(entity.getStatementBillNo(), billRepaymentEntity.getRepaymentNo(),
                        billRepaymentEntity.getAmount());

                /**
                 * 更新T+0报表的数据和明细数据
                 */
                /*
                 * updatePosCardData(entity.getStatementBillNo(),
                 * statementManageDto.getRemittanceNumber(), billRepaymentEntity.getAmount());//还款金额
                 */}
        }

        // 根据Id更新站内消息的读取状态
        for (StatementOfAccountEntity entity : statementList) {
            statementModifyService.updateInstationMsgByIds(entity, cInfo);
        }
        // 根据核销的应收单修改收款、收入部门，且重设全额还款金额
        billRepaymentEntity.setAmount(statementManageDto.getRepaymentAmount());// 重设还款金额
        billRepaymentEntity.setCollectionOrgCode(rtnBillReceivableEntity.getDunningOrgCode());// 收款部门编码
        billRepaymentEntity.setCollectionOrgName(rtnBillReceivableEntity.getDunningOrgName());// 收款部门名称
        // 保存还款单
        billRepaymentEntity.setCreateOrgCode(cInfo.getCurrentDeptCode());
        billRepaymentEntity.setCreateOrgName(cInfo.getCurrentDeptName());
        billRepaymentService.addBillRepayment(billRepaymentEntity, cInfo);

        LOGGER.info("还款/批量还款单时,新增还款单成功,还款单号：" + billRepaymentEntity.getRepaymentNo());

        // 3、如果不是网上支付的，查询并修改对账单回执
        updateStatementReceipt(statementBillNoList, statementManageDto, cInfo);

        LOGGER.debug("还款/批量还款单结束...");
    }

    /**
     * 更新T+0报表的数据
     * 
     * @author:378391|foss结算zhanglipeng
     * @date 2016年10月9日 下午7:45:24
     */
    /*
     * public void updatePosCardData(String statementNo, String tradeSerialNo, BigDecimal amount) {
     *//**
     * 根据对账单号去更新明细
     */
    /*
     * // 封装明细实体 PosCardDetailEntity detail = new PosCardDetailEntity(); // 设置单据号
     * detail.setInvoiceNo(statementNo); // 设置交易流水号 detail.setTradeSerialNo(tradeSerialNo); //
     * 设置已使用流水号金额 detail.setOccupateAmount(amount);
     *//**
     * 更新明细
     */
    /*
     * pdaCommonServiceImpl.updateSinglePosCardDetail(detail);
     *//**
     * 更新T+0
     */
    /*
     * pdaCommonServiceImpl.updatePosCardByNumber(detail); }
     */

    /**
     * 根据对账单号修改对账单 回执
     * 
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:46:24
     * @param statementList
     * @param dto
     * @param cInfo
     */
    private void updateStatementReceipt(List<String> statementList, PdaStatementManageDto dto,
            CurrentInfo cInfo) {

        // 循环处理对账单
        for (String statementBillNo : statementList) {

            LOGGER.info("还款/批量还款单时,修改对账单回执开始,对账单号：" + statementBillNo);

            // 根据对账单号，查询对最后打印的对账单回执
            StatementConfReceiptEntity entity = statementReceiptService
                    .queryLastPrintReceipt(statementBillNo);
            if (entity != null) {

                // 设置还款方式、还款金额、还款日期、客户意见、收款人
                entity.setPaymentType(dto.getRepaymentType());
                entity.setReceivedAmount(dto.getRepaymentAmount());
                entity.setPaymentDate(new Date());
                entity.setCustomerIdea(dto.getDescription());
                entity.setReceiveEmpName(cInfo.getEmpName());

                // 修改对账单回执
                statementReceiptService.updateStatementConfReceipt(entity);

            }
        }

    }

    /**
     * 生成对账单还款单关系数据
     * 
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:47:12
     * @param statementNo
     * @param repaymentNo
     * @param repaymentAmount
     */
    private void addSoaRepayment(String statementNo, String repaymentNo, BigDecimal repaymentAmount) {

        // 生成对账单还款单关系数据
        PdaSoaRepaymentEntity entity = new PdaSoaRepaymentEntity();
        entity.setId(UUIDUtils.getUUID());
        entity.setStatementBillNo(statementNo);
        entity.setRepaymentNo(repaymentNo);
        entity.setRepaymentAmount(repaymentAmount);
        entity.setPaymentDate(new Date());

        // 保存对账单还款单关系数据
        pdaStatementManageDao.add(entity);

        LOGGER.info("还款/批量还款单时,生成对账单还款单关系数据成功,对账单号：" + entity.getStatementBillNo() + ",还款单号："
                + entity.getRepaymentNo());
    }

    /**
     * 新增还款单
     * 
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:50:37
     * @param dto
     * @param cInfo
     * @return
     */
    private BillRepaymentEntity addBillRepayment(PdaStatementManageDto dto, CurrentInfo cInfo) {

        LOGGER.info("还款/批量还款单时,新增还款单开始...");
        // 生成还款单
        BillRepaymentEntity entity = new BillRepaymentEntity();
        Date now = new Date();

        entity.setId(UUIDUtils.getUUID());// ID
        entity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1));// 还款单号
        entity.setCustomerCode(dto.getCustomerCode());// 客户编码
        entity.setCustomerName(dto.getCustomerName());// 客户名称
        entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
        entity.setAmount(dto.getRepaymentAmount());// 金额
        entity.setTrueAmount(dto.getRepaymentAmount());// 实际还款金额
        entity.setBverifyAmount(BigDecimal.ZERO);// 反核销金额
        entity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);// 审核状态
        entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);// 生成方式

        /* 录入部门为当前POS登录人编码和名称 */
        entity.setCreateOrgName(cInfo.getCurrentDeptCode());// 录入部门名称
        entity.setCreateOrgCode(cInfo.getCurrentDeptCode());// 录入部门编码
        entity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);// 还款单类型
        entity.setStatus(SettlementDictionaryConstants.BILL_REPAYMENT__STATUS__SUBMIT);// 状态默认为提交
        entity.setActive(FossConstants.ACTIVE);// 有效
        entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);// 非红单
        entity.setPaymentType(dto.getRepaymentType());// 支付方式
        entity.setCreateUserCode(cInfo.getCurrentDeptCode());// 制单人编码
        entity.setCreateUserName(cInfo.getCurrentDeptName());// 制单人名称
        entity.setBusinessDate(now);// 业务时间
        entity.setAccountDate(now);// 记账时间
        entity.setCreateTime(now);// 创建时间
        entity.setIsInit(FossConstants.NO);// 是否初始化
        entity.setNotes(dto.getRepaymentNotes());// 备注
        entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号

        entity.setOnlinePaymentNo(dto.getRemittanceNumber());// 在线支付编号(与推送财务自助编码 一致)
        LOGGER.info("在线支付编号为："+dto.getRemittanceNumber());
        /**
         * 银行卡填写银联交易流水号前台传入的是汇款编号的字段，故在此处处理
         */
        /*
         * if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD
         * .equals(entity.getPaymentType())) {
         */
        entity.setBatchNo(dto.getRemittanceNumber());
        /*
         * } else { entity.setOaPaymentNo(dto.getRemittanceNumber());// OA汇款编号 }
         */
        entity.setOaPayee(dto.getRemittanceName());// OA汇款人

        // 保存还款单
        return entity;
    }

    /**
     * 按对账单进行核销
     * 
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:51:50
     * @param statementNo
     * @param entity
     * @param cInfo
     * @param writeoffBatchNo
     * @return
     */
    private BillReceivableEntity billRepayWriteoff(String statementNo, BillRepaymentEntity entity,
            CurrentInfo cInfo, String writeoffBatchNo) {

        // 循环按每个对账单处理

        // 根据对账单号查询对账单明细
        List<StatementOfAccountDEntity> statementDetailEntitys = statementOfAccountDService
                .queryByStatementBillNo(statementNo);
        if (CollectionUtils.isEmpty(statementDetailEntitys)) {
            LOGGER.info("还款/批量还款按对账单核销时,对账单明细记录为空，无法核销");
            throw new SettlementException("对账单明细记录为空，无法核销");
        }

        // 从对账单明细中获取应收单号、应付单号、预收单号、预付单号
        List<String> recNos = new ArrayList<String>();
        List<String> payNos = new ArrayList<String>();
        List<String> depNos = new ArrayList<String>();
        List<String> advNos = new ArrayList<String>();
        for (StatementOfAccountDEntity statmentDetailEntity : statementDetailEntitys) {
            // 应收单号
            if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE
                    .equals(statmentDetailEntity.getBillParentType())) {
                recNos.add(statmentDetailEntity.getSourceBillNo());
            }
            // 应付单号
            else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE
                    .equals(statmentDetailEntity.getBillParentType())) {
                payNos.add(statmentDetailEntity.getSourceBillNo());
            }
            // 预收单号
            else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__DEPOSIT_RECEIVED
                    .equals(statmentDetailEntity.getBillParentType())) {
                depNos.add(statmentDetailEntity.getSourceBillNo());
            }
            // 预付单号
            else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__ADVANCED_PAYMENT
                    .equals(statmentDetailEntity.getBillParentType())) {
                advNos.add(statmentDetailEntity.getSourceBillNo());
            }
            // 其他
            else {
                LOGGER.info("还款/批量还款按对账单核销时,对账单明细单据大类型异常，无法核销");
                throw new SettlementException("对账单明细单据大类型异常，无法核销");
            }
        }

        List<BillReceivableEntity> recs = new ArrayList<BillReceivableEntity>();
        List<BillPayableEntity> pays = new ArrayList<BillPayableEntity>();
        List<BillDepositReceivedEntity> deps = new ArrayList<BillDepositReceivedEntity>();
        List<BillAdvancedPaymentEntity> advs = new ArrayList<BillAdvancedPaymentEntity>();

        // 根据来源单号查询应收单
        if (CollectionUtils.isNotEmpty(recNos)) {
            recs = billReceivableService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
            // 校应收单原始单据数量和对账单明细数量是否匹配
            if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recNos.size())
                    || (CollectionUtils.isEmpty(recs) && recNos.size() > 0)) {
                LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和应收单原始单据数量不相等，无法核销");
                throw new SettlementException("对账单明细数量和应收单原始单据数量不相等，无法核销");
            }
        }

        // 根据来源单号查询应付单
        if (CollectionUtils.isNotEmpty(payNos)) {
            pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
            // 校应付单原始单据数量和对账单明细数量是否匹配
            if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size())
                    || (CollectionUtils.isEmpty(pays) && payNos.size() > 0)) {
                LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和应付单原始单据数量不相等，无法核销");
                throw new SettlementException("对账单明细数量和应付单原始单据数量不相等，无法核销");
            }
        }

        // 根据来源单号查询预收单
        if (CollectionUtils.isNotEmpty(depNos)) {
            deps = billDepositReceivedService.queryByDepositReceivedNOs(depNos, FossConstants.ACTIVE);
            // 校预收单原始单据数量和对账单明细数量是否匹配
            if ((CollectionUtils.isNotEmpty(deps) && deps.size() != depNos.size())
                    || (CollectionUtils.isEmpty(deps) && depNos.size() > 0)) {
                LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和预收单原始单据数量不相等，无法核销");
                throw new SettlementException("对账单明细数量和预收单原始单据数量不相等，无法核销");
            }
        }

        // 根据来源单号查询预付单
        if (CollectionUtils.isNotEmpty(advNos)) {
            advs = billAdvancedPaymentService.queryBillAdvancedPaymentNos(advNos, FossConstants.ACTIVE);
            // 校预付单原始单据数量和对账单明细数量是否匹配
            if ((CollectionUtils.isNotEmpty(advs) && advs.size() != advNos.size())
                    || (CollectionUtils.isEmpty(advs) && advNos.size() > 0)) {
                LOGGER.info("还款/批量还款按对账单核销时,对账单明细数量和预付单原始单据数量不相等，无法核销");
                throw new SettlementException("对账单明细数量和预付单原始单据数量不相等，无法核销");
            }
        }

        BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

        // 获取其中待核销的一个应收单
        BillReceivableEntity rtnBillReceivableEntity = recs.get(0);

        // 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
        writeoffDto.setBillReceivableEntitys(recs);
        writeoffDto.setBillPayableEntitys(pays);
        writeoffDto.setBillDepositReceivedEntitys(deps);
        writeoffDto.setBillAdvancedPaymentEntitys(advs);

        // 设置还款单
        writeoffDto.setBillRepaymentEntity(entity);

        writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

        // 核销类型为手工核销
        writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

        // 核销对账单编号取对账单上的单据编号
        writeoffDto.setStatementBillNo(statementNo);

        // 核销对账单明细
        writeoffStatementDetailForRepayment(writeoffDto, cInfo);

        // 释放预付单
        releaseFromStatement(writeoffDto, cInfo);

        return rtnBillReceivableEntity;
    }

    /**
     * 释放预付单
     * 
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:52:29
     * @param writeoffResultDto
     * @param currentInfo
     */
    private void releaseFromStatement(BillWriteoffOperationDto writeoffResultDto, CurrentInfo currentInfo) {

        // 预付单未核销金额大于零时，从对账单中释放
        if (CollectionUtils.isNotEmpty(writeoffResultDto.getBillAdvancedPaymentEntitys())) {
            for (BillAdvancedPaymentEntity adv : writeoffResultDto.getBillAdvancedPaymentEntitys()) {

                LOGGER.info("还款/批量还款按对账单核销完成,释放对账单中的预付单,对预付号：" + adv.getAdvancesNo());
                // 修改预收单的对账单号字段
                adv.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
                int row = billAdvancedPaymentService.updateBillAdvancedPaymentByMakeStatement(adv,
                        currentInfo);
                if (row != 1) {
                    throw new SettlementException("更新条数错误，更新异常！");
                }
            }
        }
    }

    /**
     * 对账单明细核销，包括：预收冲应收、应收冲应付、还款冲应收 、预付冲应付
     * 
     * @author:378391|foss结算zhanglipeng
     * @date:2016年10月9日 下午7:53:26
     */
    private BillWriteoffOperationDto writeoffStatementDetailForRepayment(
            BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
        // 初始化返回结果
        BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
        writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());
        writeoffResultDto.setBillDepositReceivedEntitys(writeoffDto.getBillDepositReceivedEntitys());
        writeoffResultDto.setBillAdvancedPaymentEntitys(writeoffDto.getBillAdvancedPaymentEntitys());

        // 预收冲应收
        if (CollectionUtils.isNotEmpty(writeoffDto.getBillDepositReceivedEntitys())
                && CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())) {
            LOGGER.info("还款/批量还款按对账单核销时,核销预收冲应收...");
            writeoffResultDto = billWriteoffService.writeoffDepositAndReceivable(writeoffDto, currentInfo);
            writeoffDto.setBillDepositReceivedEntitys(writeoffResultDto.getBillDepositReceivedEntitys());
            writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
        }

        // 应收冲应付
        if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
                && CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
            LOGGER.info("还款/批量还款按对账单核销时,核销应收冲应付...");
            writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
            writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
            writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
        }

        // 还款冲应收
        if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
                && writeoffDto.getBillRepaymentEntity() != null) {
            LOGGER.info("还款/批量还款按对账单核销时,核销还款冲应收...");
            billWriteoffService.writeoffRepaymentAndReceibable(writeoffDto, currentInfo);
            writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
            writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
        }

        // 预付冲应付
        if (CollectionUtils.isNotEmpty(writeoffDto.getBillAdvancedPaymentEntitys())
                && CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
            LOGGER.info("还款/批量还款按对账单核销时,核销预付冲应付...");
            writeoffResultDto = billWriteoffService.writeoffAdvancedAndPayable(writeoffDto, currentInfo);
            writeoffDto.setBillAdvancedPaymentEntitys(writeoffResultDto.getBillAdvancedPaymentEntitys());
            writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
        }
        return writeoffResultDto;
    }

    /****** setter/getter **********/
    public void setPdaStatementManageDao(IPdaStatementManageDao pdaStatementManageDao) {
        this.pdaStatementManageDao = pdaStatementManageDao;
    }

    public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
        this.billRepaymentService = billRepaymentService;
    }

    public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
        this.billWriteoffService = billWriteoffService;
    }

    public void setStatementOfAccountService(IStatementOfAccountService statementOfAccountService) {
        this.statementOfAccountService = statementOfAccountService;
    }

    public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }

    public void setStatementOfAccountDService(IStatementOfAccountDService statementOfAccountDService) {
        this.statementOfAccountDService = statementOfAccountDService;
    }

    public void setBillReceivableService(IBillReceivableService billReceivableService) {
        this.billReceivableService = billReceivableService;
    }

    public void setBillPayableService(IBillPayableService billPayableService) {
        this.billPayableService = billPayableService;
    }

    public void setBillDepositReceivedService(IBillDepositReceivedService billDepositReceivedService) {
        this.billDepositReceivedService = billDepositReceivedService;
    }

    public void setBillAdvancedPaymentService(IBillAdvancedPaymentService billAdvancedPaymentService) {
        this.billAdvancedPaymentService = billAdvancedPaymentService;
    }

    public void setStatementReceiptService(IStatementReceiptService statementReceiptService) {
        this.statementReceiptService = statementReceiptService;
    }

    public void setStatementModifyService(IStatementModifyService statementModifyService) {
        this.statementModifyService = statementModifyService;
    }

    public void setPdaCommonServiceImpl(IPdaCommonService pdaCommonServiceImpl) {
    }

    public IFossToFinsRemitCommonService getFossToFinsRemitCommonService() {
        return fossToFinsRemitCommonService;
    }

    public void setFossToFinsRemitCommonService(IFossToFinsRemitCommonService fossToFinsRemitCommonService) {
        this.fossToFinsRemitCommonService = fossToFinsRemitCommonService;
    }

}
