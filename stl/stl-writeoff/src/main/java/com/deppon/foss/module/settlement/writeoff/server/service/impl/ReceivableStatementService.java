package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.*;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.*;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IFossToFinanceRemittanceService;
import com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.BillStatementToPaymentQueryDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IReceivableStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IReceivableStatementQueryDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IReceivableStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.StatementRecivableDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author foss-youkun
 * @date 2016/1/19
 */
public class ReceivableStatementService implements IReceivableStatementService{

    private IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService;

    private IPublicBankAccountService publicBankAccountService;

    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    private IReceivableStatementQueryDao receivableStatementQueryDao;

    private IReceivableStatementDao receivableStatementDao;

    private IBillReceivableDService billReceivableDService;

    private IBillReceivableService billReceivableService;

    private IBillReceivablePartnerService billReceivablePartnerService;

    /**
     * 还款单service
     */
    private IBillRepaymentService billRepaymentService;

    /**
     * 结算通用服务service
     */
    private ISettlementCommonService settlementCommonService;

    /**
     * 汇款服务接口service
     */
    private IFossToFinanceRemittanceService fossToFinanceRemittanceService;

    /**
     * 注入组织服务
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;

    /**
     * 对账单还款单关系service
     */
    private ISoaRepaymentManageService soaRepaymentManageService;

    /**
     * 网上支付记录接口调用代理
     */
    private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;

    /**
     * 核销/反核销公共service
     */
    private IBillWriteoffService billWriteoffService;


    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ReceivableStatementService.class);

    /**
     * 查询出应收单需要生成对账单的数据
     * @param dto
     * @param currentInfo
     * @return
     * @throws Exception
     */
    public StatementRecivableDto queryBillRecivableByList(StatementRecivableDto dto, CurrentInfo currentInfo,int start,int limit) throws SettlementException {
        //定义返回结果集
        StatementRecivableDto statementRecivableDto = new StatementRecivableDto();
        //客户的信息
        StatementOfAccountEntity soaEntity = new StatementOfAccountEntity();
        //查询应收单的结果集
        List<BillReceivableEntity> billReceivableEntityList;
        //总记录数
        Long total = 0L;
        //参数dto不能为空
        if (dto == null) {
            //提示查询DTO为空
            throw new SettlementException("查询DTO为空！", "");
        }
        //记录日志
        logger.info("制作对账单，enter service ,合伙人编码：" + dto.getContractOrgCode());

        // 当前登录用户操作部门
        soaEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
        // 当前登录用户操作部门（所属部门）
        soaEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
        // 获取当前登录组织的的实体信息（调用综合管理接口）
        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
        // 当前登录用户操作公司（所属公司）
        soaEntity.setCompanyCode(orgEntity.getSubsidiaryCode());
        // 当前登录用户操作公司
        soaEntity.setCompanyName(orgEntity.getSubsidiaryName());
        // 当前登录用户所属部门标杆编码
        soaEntity.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
        //设置合伙人编码
        statementRecivableDto.setContractOrgCode(dto.getContractOrgCode());
        //设置合伙人名称
        statementRecivableDto.setContractOrgName(dto.getContractOrgName());

        //当前登录用户不能为空
        if (currentInfo == null) {
            //提示当前登录用户信息为空
            throw new SettlementException("当前登录用户信息为空！", "");
        }

        // 调用综合管理接口获取公司的对公银行账号信息
        PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
        // 部门标杆编码信息
        publicBankAccountEntity.setDeptCd(currentInfo.getDept().getUnifiedCode());

        //查询部门所属银行信息
        List<PublicBankAccountEntity> bankList = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
        //如果银行账号信息为空则抛出异常
        if (CollectionUtils.isEmpty(bankList)) {
            //提示当前操作用户所属部门没有银行账号信息
            //throw new SettlementException("当前操作用户所属部门没有银行账号信息！");
        	// 获取支行名称
            soaEntity.setBankBranchName("");
            // 获取开户名
            soaEntity.setAccountUserName("");
            // 获取子公司账号
            soaEntity.setCompanyAccountBankNo("");
        }else if (bankList.size() > 1) {
            //提示当前操作用户所属部门存在多条银行账号信息
            throw new SettlementException("当前操作用户所属部门存在多条银行账号信息！");
        }else{
            //获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
            PublicBankAccountEntity bankAccountEntity = bankList.get(0);
            // 获取支行名称
            soaEntity.setBankBranchName(bankAccountEntity.getSubbranchName());
            // 获取开户名
            soaEntity.setAccountUserName(bankAccountEntity.getBankAccName());
            // 获取子公司账号
            soaEntity.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
        }
        // 获取结账次数
        soaEntity.setSettleNum((short) 0);
        /**
         * 定义需要传递参数的实体类
         */
        BillReceivableDto billReceivableDto = new BillReceivableDto();
        /**
         * 如果日期则是按照合伙人制作查询否则就是单号
         */
        if(dto.getBusinessStartDate()!=null && !("").equals(dto.getBusinessStartDate())){
            billReceivableDto.setStartDate(dto.getBusinessStartDate());
            billReceivableDto.setEndDate(dto.getBusinessEndDate());
            billReceivableDto.setContractOrgCode(dto.getContractOrgCode());
            //设置账期开始日期
            statementRecivableDto.setBusinessStartDate(dto.getBusinessStartDate());
            //设置账期结束日期
            statementRecivableDto.setBusinessEndDate(dto.getBusinessEndDate());
        }else{
            if(dto.getWaybillNoList().size()>0){
                billReceivableDto.setWaybillNoList(dto.getWaybillNoList());
            }
        }
        try{
            billReceivableEntityList = billReceivableMonthlyQueryService.queryBillRecivableByList(billReceivableDto,start,limit);
        }catch (SettlementException e){
            throw  new SettlementException("查询失败："+e.getMessage());
        }
        try{
            total = billReceivableMonthlyQueryService.countBillRecivableByList(billReceivableDto);
        }catch (SettlementException e){
            logger.error(e.getMessage());
            throw  new SettlementException("查询失败："+e.getMessage());
        }

        if(billReceivableEntityList.size()<=0){
            billReceivableEntityList = new ArrayList<BillReceivableEntity>();
        }else{
            //判断是否是一个合伙人的应收单
            String contractOrgCode = null;
            for(BillReceivableEntity billReceivableEntity:billReceivableEntityList){
                if(billReceivableEntity.getCustomerCode()==null){
                    break;
                }
                if(contractOrgCode==null){
                    //获取合伙人的名称
                    contractOrgCode = billReceivableEntity.getCustomerCode();
                    //设置合伙人的公司名称
                    statementRecivableDto.setContractOrgName(billReceivableEntity.getCustomerName());
                    //设置合伙人的公司code
                    statementRecivableDto.setContractOrgCode(billReceivableEntity.getCustomerCode());
                }
                if(!contractOrgCode.equals(billReceivableEntity.getCustomerCode())){
                    throw new SettlementException("查询结果包含多个合伙人信息");
                }
            }
        }
        // 获取账期起始时间和结束时间,起始时间取对账单明细中最早的业务日期,结束时间取对账单明细中最晚的业务日期
        //如果按单号时间就去查询出来的
        if(dto.getWaybillNoList()!=null) {
            if (CollectionUtils.isNotEmpty(billReceivableEntityList)) {
                Date periodBeginDate = null;
                Date periodEndDate = null;
                for (BillReceivableEntity dEntity : billReceivableEntityList) {
                    if (null == periodBeginDate) {
                        periodBeginDate = dEntity.getBusinessDate();
                    } else if (periodBeginDate.after(dEntity.getBusinessDate())) {
                        periodBeginDate = dEntity.getBusinessDate();

                    }
                    if (null == periodEndDate) {
                        periodEndDate = dEntity.getBusinessDate();
                    } else if (periodEndDate.before(dEntity.getBusinessDate())) {
                        periodEndDate = dEntity.getBusinessDate();
                    }
                }
                statementRecivableDto.setBusinessStartDate(periodBeginDate);
                statementRecivableDto.setBusinessEndDate(periodEndDate);
            }
        }
        dto.setContractOrgCode(currentInfo.getCurrentDeptCode());
        statementRecivableDto.setTotalCount(total);
        statementRecivableDto.setBillReceivableEntityList(billReceivableEntityList);
        statementRecivableDto.setStatementOfAccountEntity(soaEntity);
        return statementRecivableDto;
    }

    /**
     * 新增收款对账单
     * @param dto
     * @return
     * @throws SettlementException
     */
    @Transactional
    public String addReceivableStatement(StatementRecivableDto dto,CurrentInfo currentInfo) throws SettlementException {
        //生成对账单号
        String  statementBillNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZHY);
        //当前登录用户不能为空
        if (currentInfo == null) {
            //提示当前登录用户信息为空
            throw new SettlementException("当前登录用户信息为空！", "");
        }
        //获得应收单信息
        List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();
        //定义对账单明细实体
        List<Object> statementRecivableDEntityList = new ArrayList<Object>();
        //定义一个收款对账单的实体
        StatementRecivableEntity statementRecivableEntity = new StatementRecivableEntity();

        //定义一个应收单号的集合
        List<String> receivableNoList = new ArrayList<String>();
        Map<String,Object> map = new HashMap<String,Object>();
        // 获取当前登录组织的的实体信息（调用综合管理接口）
        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
        // 调用综合管理接口获取公司的对公银行账号信息
        PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
        // 部门标杆编码信息
        publicBankAccountEntity.setDeptCd(currentInfo.getDept().getUnifiedCode());

        //查询部门所属银行信息
        List<PublicBankAccountEntity> bankList = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
        //如果银行账号信息为空则抛出异常
        if (CollectionUtils.isEmpty(bankList)) {
            //提示当前操作用户所属部门没有银行账号信息
            //throw new SettlementException("当前操作用户所属部门没有银行账号信息！");
            // 获取支行名称
            statementRecivableEntity.setBankBranchName("");
            // 获取开户名
            statementRecivableEntity.setAccountUserName("");
            // 获取子公司账号
            statementRecivableEntity.setCompanyAccountBankNo("");
        }else if (bankList.size() > 1) {
            //提示当前操作用户所属部门存在多条银行账号信息
            throw new SettlementException("当前操作用户所属部门存在多条银行账号信息！");
        }else{
            //获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
            PublicBankAccountEntity bankAccountEntity = bankList.get(0);
            // 获取支行名称
            statementRecivableEntity.setBankBranchName(bankAccountEntity.getSubbranchName());
            // 获取开户名
            statementRecivableEntity.setAccountUserName(bankAccountEntity.getBankAccName());
            // 获取子公司账号
            statementRecivableEntity.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
        }

        //设置Id
        statementRecivableEntity.setId(UUIDUtils.getUUID());
        //对账单号
        statementRecivableEntity.setStatementBillNo(statementBillNo);
        //部门编码
        statementRecivableEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
        //部门名称
        statementRecivableEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
        //公司编码
        statementRecivableEntity.setCustomerCode(orgEntity.getSubsidiaryCode());
        //公司名称
        statementRecivableEntity.setCustomerName(orgEntity.getSubsidiaryName());
        //部门标杆编码
        statementRecivableEntity.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
        if(dto.getBusinessStartDate()!=null && !("").equals(dto.getBusinessStartDate())) {
            //账期开始日期
            statementRecivableEntity.setPeriodBeginDate(dto.getBusinessStartDate());
            //账期结束日期
            statementRecivableEntity.setPeriodEndDate(dto.getBusinessEndDate());
        }

        //结账次数
        statementRecivableEntity.setSettleNum((short) 0);
        //对账单状态
        statementRecivableEntity.setStatementStatus(SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_NO);
        //创建时间
        statementRecivableEntity.setCreateTime(new Date());


        /**
         * 定义需要传递参数的实体类
         */
        BillReceivableDto billReceivableDto = new BillReceivableDto();
        /**
         * 如果日期则是按照合伙人制作查询否则就是单号
         */
        if(dto.getBusinessStartDate()!=null && !("").equals(dto.getBusinessStartDate())){
            billReceivableDto.setStartDate(dto.getBusinessStartDate());
            billReceivableDto.setEndDate(dto.getBusinessEndDate());
            billReceivableDto.setContractOrgCode(dto.getContractOrgCode());
            //设置账期开始日期
            dto.getBusinessStartDate();
            //设置账期结束日期
            dto.getBusinessEndDate();
        }else{
            if(dto.getWaybillNoList().size()>0){
                billReceivableDto.setWaybillNoList(dto.getWaybillNoList());
            }
        }
        try{
            billReceivableEntityList = billReceivableMonthlyQueryService.queryNotPageBillRecivableByList(billReceivableDto);
        }catch (SettlementException e){
            throw  new SettlementException("查询失败："+e.getMessage());
        }
        // 获取账期起始时间和结束时间,起始时间取对账单明细中最早的业务日期,结束时间取对账单明细中最晚的业务日期
        //如果按单号时间就去查询出来的
        if(dto.getWaybillNoList()!=null) {
            if (CollectionUtils.isNotEmpty(billReceivableEntityList)) {
                Date periodBeginDate = null;
                Date periodEndDate = null;
                for (BillReceivableEntity dEntity : billReceivableEntityList) {
                    if (null == periodBeginDate) {
                        periodBeginDate = dEntity.getBusinessDate();
                    } else if (periodBeginDate.after(dEntity.getBusinessDate())) {
                        periodBeginDate = dEntity.getBusinessDate();

                    }
                    if (null == periodEndDate) {
                        periodEndDate = dEntity.getBusinessDate();
                    } else if (periodEndDate.before(dEntity.getBusinessDate())) {
                        periodEndDate = dEntity.getBusinessDate();
                    }
                }
                statementRecivableEntity.setPeriodBeginDate(periodBeginDate);
                statementRecivableEntity.setPeriodEndDate(periodEndDate);
            }
        }
        //本期应收金额（总金额）本期应收
        BigDecimal bigDecimalAmount = BigDecimal.ZERO;
        //本期未还（未核销）本期剩余应收
        BigDecimal bigDecimalUnverifyAmount = BigDecimal.ZERO;
        //本期已还(已核销)
        BigDecimal bigDecimalVerifyAmount = BigDecimal.ZERO;
        String customerName = null;
        String customerCode = null;
        for(int i=0;i<billReceivableEntityList.size();i++){
            if(null==billReceivableEntityList.get(i).getCustomerCode()){
                throw new SettlementException("合伙人信息为空！");
            }
            if(customerName==null){
                customerName = billReceivableEntityList.get(i).getCustomerName();
            }
            if(customerCode==null){
                customerCode = billReceivableEntityList.get(i).getCustomerCode();
            }
            if(!customerCode.equals(billReceivableEntityList.get(i).getCustomerCode())){
                throw new SettlementException("对账单包含多个合伙人信息！");
            }
            //计算所以得费用生成对账单需要的数据
            //本期应收金额（总金额）本期应收
            bigDecimalAmount = bigDecimalAmount.add(billReceivableEntityList.get(i).getAmount());
            //本期未还（未核销）本期生剩余应收
            bigDecimalUnverifyAmount = bigDecimalUnverifyAmount.add(billReceivableEntityList.get(i).getUnverifyAmount());
            //本期已还(已核销)
            bigDecimalVerifyAmount = bigDecimalVerifyAmount.add(billReceivableEntityList.get(i).getVerifyAmount());
            //应收单号放入应收单号集合中
            receivableNoList.add(billReceivableEntityList.get(i).getReceivableNo());

            //应收单明细不为空则生成对账单明细
            Map<String,Object> statementRecivableDEntityMap = new HashMap<String, Object>();
            //ID
            statementRecivableDEntityMap.put("id", UUIDUtils.getUUID());
            //对账单单号
            statementRecivableDEntityMap.put("billStatementNo", statementBillNo);
            //单号（应收单单号）
            statementRecivableDEntityMap.put("sourceBillNo", billReceivableEntityList.get(i).getReceivableNo());
            //业务日期
            statementRecivableDEntityMap.put("businessDate", billReceivableEntityList.get(i).getBusinessDate());
            //运单号
            statementRecivableDEntityMap.put("waybillNo", billReceivableEntityList.get(i).getWaybillNo());
            //目的站
            statementRecivableDEntityMap.put("arrvRegionCode", billReceivableEntityList.get(i).getTargetOrgCode());
            //产品类型
            statementRecivableDEntityMap.put("proType", billReceivableEntityList.get(i).getProductCode());
            //件数
            statementRecivableDEntityMap.put("qty", billReceivableEntityList.get(i).getGoodsQtyTotal());
            //计费体积
            statementRecivableDEntityMap.put("billingVolume", billReceivableEntityList.get(i).getGoodsVolumeTotal());
            //计费重量
            statementRecivableDEntityMap.put("billWeight", billReceivableEntityList.get(i).getBillWeight());
            //付款方式
            statementRecivableDEntityMap.put("paymentType", billReceivableEntityList.get(i).getPaymentType());
            //提货方式
            statementRecivableDEntityMap.put("receiveMethod", billReceivableEntityList.get(i).getReceiveMethod());
            //原始未核销金额
            statementRecivableDEntityMap.put("unverifyAmount", billReceivableEntityList.get(i).getUnverifyAmount());
            //总金额
            statementRecivableDEntityMap.put("amount", billReceivableEntityList.get(i).getAmount());
            //单号
            /*statementRecivableDEntityMap.put("sourceBillNo", billReceivableEntityList.get(i).getSourceBillNo());*/
            //单据子类型
            statementRecivableDEntityMap.put("billType", billReceivableEntityList.get(i).getBillType());
            //客户\代理编码
            statementRecivableDEntityMap.put("customerCode", billReceivableEntityList.get(i).getCustomerCode());
            //客户名称
            statementRecivableDEntityMap.put("customerName", billReceivableEntityList.get(i).getCustomerName());
            //已核销金额
            statementRecivableDEntityMap.put("verifyAmount", billReceivableEntityList.get(i).getVerifyAmount());
            //部门编码
            statementRecivableDEntityMap.put("orgCode", currentInfo.getCurrentDeptCode());
            //部门名称
            statementRecivableDEntityMap.put("orgName", currentInfo.getCurrentDeptName());
            //记账日期
            statementRecivableDEntityMap.put("accountDate", billReceivableEntityList.get(i).getAccountDate());
            //提货网点
            statementRecivableDEntityMap.put("customerPickupOrgName", billReceivableEntityList.get(i).getCustomerPickupOrgCode());
            //货物名称
            statementRecivableDEntityMap.put("goodsName", billReceivableEntityList.get(i).getGoodsName());
            //始发网点编码
            statementRecivableDEntityMap.put("origOrgCode", billReceivableEntityList.get(i).getOrigOrgCode());
            //始发网点名称
            statementRecivableDEntityMap.put("origOrgName", billReceivableEntityList.get(i).getOrigOrgName());
            //到达部门编码
            statementRecivableDEntityMap.put("destOrgCode", billReceivableEntityList.get(i).getDestOrgCode());
            //到达部门名称
            statementRecivableDEntityMap.put("destOrgName", billReceivableEntityList.get(i).getDestOrgName());
            //发货客户编码
            statementRecivableDEntityMap.put("deliveryCustomerCode", billReceivableEntityList.get(i).getDeliveryCustomerCode());
            //签收日期
            statementRecivableDEntityMap.put("signDate", billReceivableEntityList.get(i).getConrevenDate());
            //审核状态
            statementRecivableDEntityMap.put("auditStatus", SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_NO);
            //备注
            statementRecivableDEntityMap.put("notes", billReceivableEntityList.get(i).getNotes());
            //运单开单时间
            statementRecivableDEntityMap.put("billBeginTime", billReceivableEntityList.get(i).getCreateTime());
            //创建时间
            statementRecivableDEntityMap.put("createTime",new Date());
            //是否删除
            statementRecivableDEntityMap.put("isDelete","N");
            //根据应收单号查询应收单明细
            List<BillReceivableDEntity> billReceivableDEntities;
            try {
                billReceivableDEntities = billReceivableDService.queryByReceivableNo(billReceivableEntityList.get(i).getReceivableNo());
            }catch (Exception e){
                logger.error(e.getMessage());
                throw  new SettlementException(e.getMessage());
            }
            if(billReceivableDEntities!=null && billReceivableDEntities.size()>0){
                for(BillReceivableDEntity bList :billReceivableDEntities ){
                    //根据应收单明信息生成对账单所需的数据根据应收类型判断收费类型
                    statementRecivableDEntityMap.put("singleOperationFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("packageFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("insuranceFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("disbursementFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("deliveryFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("baseDeliveryFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("mattressOperationFee",BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("agentDeclarationFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("removePackingFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("registrationFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("otherFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("upstairsFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("warehouseDeliveryFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("largeUpstairsFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("superBigDecimalFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("singleReturnFee", BigDecimal.ZERO);
                    //FEE_TYPE__BILLING_OPERATE = "BO";// 开单操作费
                    if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__BILLING_OPERATE)){
                        statementRecivableDEntityMap.put("singleOperationFee", bList.getAmount());
                        //FEE_TYPE__PACKAGING_EXTRA = "PE";// 包装费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__PACKAGING_EXTRA)){
                        statementRecivableDEntityMap.put("packageFee", bList.getAmount());
                        //FEE_TYPE__SUPPORT_VALUE_EXTRA = "SVE";// 保价费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__SUPPORT_VALUE_EXTRA)){
                        statementRecivableDEntityMap.put("insuranceFee", bList.getAmount());
                        // FEE_TYPE__COD_HANDING_EXTRA = "CHE";// 代收货款手续费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__COD_HANDING_EXTRA)){
                        statementRecivableDEntityMap.put("disbursementFee", bList.getAmount());
                        // FEE_TYPE__DELIVER_EXTRA_NO_UPSTAIRS = "DENU";// 送货费提成（不含上楼）
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__DELIVER_EXTRA_NO_UPSTAIRS)){
                        statementRecivableDEntityMap.put("deliveryFee", bList.getAmount());
                        //FEE_TYPE__BASIC_DELIVER_EXTRA = "BDE";// 基础送货费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__BASIC_DELIVER_EXTRA)){
                        statementRecivableDEntityMap.put("baseDeliveryFee", bList.getAmount());
                        //FEE_TYPE__MATTRESS_OPRATE_EXTRA = "MOE";// 床垫操作费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__MATTRESS_OPRATE_EXTRA)){
                        statementRecivableDEntityMap.put("mattressOperationFee",bList.getAmount());
                        //FEE_TYPE__AGENCY_CUSTOMERS_CHARGES_EXTRA = "ACCE";// 代理报关费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__AGENCY_CUSTOMERS_CHARGES_EXTRA)){
                        statementRecivableDEntityMap.put("agentDeclarationFee", bList.getAmount());
                        //FEE_TYPE__DISMANTLE_PACKAGE_EXTRA = "DPE";// 拆包装费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__DISMANTLE_PACKAGE_EXTRA)){
                        statementRecivableDEntityMap.put("removePackingFee", bList.getAmount());
                        // FEE_TYPE__REGISTER_PARK_EXTRA = "RPE";// 登记费停车费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__REGISTER_PARK_EXTRA)){
                        statementRecivableDEntityMap.put("registrationFee", bList.getAmount());
                        //FEE_TYPE__OTHERS_EXTRA = "OE";// 其他费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__OTHERS_EXTRA)){
                        statementRecivableDEntityMap.put("otherFee", bList.getAmount());
                        //FEE_TYPE__GOODS_UPSTAIRS_EXTRA= "GU";// 送货上楼费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__GOODS_UPSTAIRS_EXTRA)){
                        statementRecivableDEntityMap.put("upstairsFee", bList.getAmount());
                        //FEE_TYPE__GOODS_ENTRY_EXTRA = "GE";// 送货进仓费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__GOODS_ENTRY_EXTRA)){
                        statementRecivableDEntityMap.put("warehouseDeliveryFee", bList.getAmount());
                        // FEE_TYPE__GOODS_BIG_UPSTAIRS_EXTRA = "GBU";// 大件上楼费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__GOODS_BIG_UPSTAIRS_EXTRA)){
                        statementRecivableDEntityMap.put("largeUpstairsFee", bList.getAmount());
                        //FEE_TYPE__LONG_SEND_EXTRA = "LSE";// 超远派送费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__LONG_SEND_EXTRA)){
                        statementRecivableDEntityMap.put("superBigDecimalFee", bList.getAmount());
                        //BILL_RECEIVABLE__RECEIVABLE_TYPE__PUBLISH_FREIGHT_EXTRA // 签收单返回提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__PROOF_DELIVERY_EXTRA)){
                        statementRecivableDEntityMap.put("singleReturnFee", bList.getAmount());
                    }
                }
            }
            //封装对账的明细
            statementRecivableDEntityList.add(statementRecivableDEntityMap);
        }
        if(customerCode==null){
            throw new SettlementException("合伙人信息不全");
        }
        //客户名称
        statementRecivableEntity.setCustomerName(customerName);
        //客户编码
        statementRecivableEntity.setCustomerCode(customerCode);
        //设置本期应收金额
        statementRecivableEntity.setPeriodRecAmount(bigDecimalAmount);
        //设置本期已还金额
        statementRecivableEntity.setPeriodRrpayAmount(bigDecimalVerifyAmount);
        //设置本期未还金额
        statementRecivableEntity.setPeriodNpayAmount(bigDecimalUnverifyAmount);
        //设置本期发生额
        statementRecivableEntity.setPeriodAmount(bigDecimalAmount);
        //设置本期剩余应收金额
        statementRecivableEntity.setPeriodUnverifyRecAmount(bigDecimalUnverifyAmount);

        try {
            receivableStatementDao.AddRecivalbeStatement(statementRecivableEntity);
        }catch (SettlementException e){
            logger.error(e.getMessage());
            throw  new SettlementException("生成对账单失败！"+e.getMessage());
        }

        try {
            if(null!=statementRecivableDEntityList&&statementRecivableDEntityList.size()>0) {
                receivableStatementQueryDao.AddRecivalbeStatementQuery(statementRecivableDEntityList);
            }
        }catch (SettlementException e){
            logger.error("生成对账单明细失败！"+e.getMessage());
            throw  new SettlementException("生成对账单明细失败！"+e.getMessage());
        }
        //更新应收单中的对账单单号
        try{
            map.put("statementBillNo",statementBillNo);
            map.put("receivableNoList",receivableNoList);
            billReceivableService.updateByReceivableNo(map);
        }catch (SettlementException e){
            logger.error("更新应收单的对账单单号失败！"+e.getMessage());
            throw new SettlementException(e.getErrorCode());
        }

        //更新应收单的对账单号

        return statementBillNo;
    }

    /**
     * 更新收款单的状态
     * @param statementStatus
     * @return
     * @throws SettlementException
     */
    public void updateStatementByStatementStatus(String statementStatus,List<String> statementBillNo) throws SettlementException {

        //判断是否已还款如果还款不能反确认
        List<StatementRecivableEntity> statementRecivableEntitiesList = new ArrayList<StatementRecivableEntity>();
        try {
            statementRecivableEntitiesList =  receivableStatementDao.queryPartnerStatementList(statementBillNo);
        }catch (SettlementException e){
            logger.error("查询对账单信息失败"+e.getMessage());
            throw new SettlementException("查询对账单信息失败！");
        }
        if(null==statementRecivableEntitiesList|| statementRecivableEntitiesList.size()<=0){
            throw new SettlementException("对账单不存在！");
        }
        for(StatementRecivableEntity s : statementRecivableEntitiesList){
            int r=s.getPeriodUnverifyRecAmount().compareTo(BigDecimal.ZERO);
            if(r==0){
                throw new SettlementException("对账单已还款,不能重复操作！");
            }
        }

        try{
           receivableStatementDao.updateStatementByStatementStatus(statementStatus,statementBillNo);
        }catch(SettlementException e){
            logger.error(e.getMessage());
            throw new SettlementException("更新对账单状态失败！");
        }

    }

    /**
     * 查询合伙人收款对账
     * @param dto
     * @return
     * @throws SettlementException
     */
    public List<StatementRecivableEntity> queryPartnerReceivalbeStatement(StatementRecivableDto dto,int start ,int limit) throws SettlementException {
        //校验
        validateDto(dto);
        //如果结束日期不为空则加一天
        if(dto.getBusinessEndDate()!=null && !"".equals(dto.getBusinessEndDate())){
            dto.setBusinessEndDate(DateUtils.convert(DateUtils.addDay(dto.getBusinessEndDate(), 1), DateUtils.DATE_FORMAT));
        }
        List<StatementRecivableEntity> list = null;
        try{
            list = receivableStatementDao.queryPartnerReceivalbeStatement(dto, start, limit);
        }catch(SettlementException e){
            logger.error(e.getMessage());
            throw  new SettlementException("查询收款对账失败:"+e.getMessage());
        }
        if(list == null){
            list = new ArrayList<StatementRecivableEntity>();
        }
        return list;
    }

    /**
     * 查询合伙人收款对账单的总记录数
     * @param dto
     * @return
     * @throws SettlementException
     */
    public Long queryPartnerReceivalbeStatementCount(StatementRecivableDto dto) throws SettlementException {
        Long total =0L;
        //校验
        validateDto(dto);
        //如果结束日期不为空则加一天
        if(dto.getBusinessEndDate()!=null && !"".equals(dto.getBusinessEndDate())){
            dto.setBusinessEndDate(DateUtils.convert(DateUtils.addDay(dto.getBusinessEndDate(), 1), DateUtils.DATE_FORMAT));
        }
        try{
            total = receivableStatementDao.queryPartnerReceivalbeStatementCount(dto);
        }catch (SettlementException e){
            logger.error(e.getMessage());
            new  SettlementException("查询合伙人对账单总数量失败："+e.getMessage());
        }
        return total;
    }

    /**
     * 根据对账单号查询对账单明细
     * @param billStatementNo
     * @return
     * @throws SettlementException
     */
    public List<StatementRecivableDEntity> queryReceivalbeStatementByBillNo(String billStatementNo) throws SettlementException {
        List<StatementRecivableDEntity> list = new ArrayList<StatementRecivableDEntity>();
        try{
            list = receivableStatementQueryDao.queryReceivalbeStatementByBillNo(billStatementNo);
        }catch (SettlementException e){
            logger.error(e.getMessage());
            throw  new SettlementException("查询对账单明细失败："+e.getMessage());
        }
        return list;
    }

    /**
     * 删除对账单明细
     * @param id
     * @throws SettlementException
     */
    @Transactional
    public void deleteReceivableStatementById(List<String> id) throws SettlementException {
    	//1.判断传入参数不能为空
    	if(CollectionUtils.isEmpty(id)){
    		throw new SettlementException("请选择要删除的对账单明细");
    	}
    	
    	//2.根据对账单明细id查询明细信息
    	StatementRecivableDEntity statementRecivableDEntity = receivableStatementQueryDao.queryReceivalbeStatementById(id.get(0));
    	if(statementRecivableDEntity == null){
    		throw new SettlementException("该对账单明细已删除！");
    	}
    	
		// 3.根据对账单明细id查询对账单信息
		StatementRecivableEntity statementRecivableEntity = receivableStatementDao
				.queryPartnerReceivableByStatemenNo(statementRecivableDEntity
						.getBillStatementNo());
		if (statementRecivableEntity == null) {
			throw new SettlementException("选择的对账单不存在，请稍后再试");
		}
    	//判断对账单状态
    	if(FossConstants.YES.equals(statementRecivableEntity.getStatementStatus())){
            throw  new SettlementException("对账单状态已确认不能删除！");
        }
    	
    	//4.根据明细id删除对账单明细
        receivableStatementQueryDao.deleteReceivableStatementById(id);
    	
    	/**
         * 5.更新对账单表中的金额
         * 本期发生金额 本期应收金额 本期剩余应收金额
         * 本期已还本期未还
         */
    	StatementRecivableEntity entity = receivableStatementQueryDao.queryAmountByAllDetail(statementRecivableEntity.getStatementBillNo());
        //设置对账单单号
    	entity.setStatementBillNo(statementRecivableDEntity.getBillStatementNo());
        receivableStatementDao.updateByStatementNo(entity);
        
        //6.删除对账单明细同时也需要清除应收单中的对账单号
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("statementBillNo", SettlementConstants.DEFAULT_BILL_NO);
        //要删除的应收单号
        List<String> receivableNoList = new ArrayList<String>();
        receivableNoList.add(statementRecivableDEntity.getSourceBillNo());
        map.put("receivableNoList",receivableNoList);
        billReceivableService.updateByReceivableNo(map);
    }

    /**
     * 收款对账单还款
     * @param billStatementToPaymentQueryDto
     * @param cInfo
     * @return
     * @throws SettlementException
     */
    @Transactional
    public String partnerRepayment(BillStatementToPaymentQueryDto billStatementToPaymentQueryDto, CurrentInfo cInfo) throws SettlementException {
        logger.info("还款/批量还款单开始...");

        //验证还款dto
        if (billStatementToPaymentQueryDto == null||billStatementToPaymentQueryDto.getRepaymentAmount()==null) {
            logger.info("还款/批量还款单时,传入的还款信息为空");
            throw new SettlementException("传入的还款信息为空!");
        }

        //还款金额必须>0,否则提示还款金额小于0异常
        if(billStatementToPaymentQueryDto.getRepaymentAmount().compareTo(BigDecimal.ZERO)!=1){
            throw new SettlementException("还款/批量还款单时还款金额必须大于0!");
        }

        // 获取对账单号集合
        List<String> statementBillNoList = Arrays.asList(billStatementToPaymentQueryDto.getStatementBillNos());

        // 对账单号不能为空
        if (CollectionUtils.isEmpty(statementBillNoList)) {
            logger.info("还款/批量还款单时,传入的对账单号为空不能正常还款");
            throw new SettlementException("传入的对账单号为空不能正常还款!");
        }

        // 根据对账单号集合获取对账单列表
        List<StatementRecivableEntity> statementList = new ArrayList<StatementRecivableEntity>();

        //根据对账单单号查询对账单信息
        StatementRecivableEntity woodenStatementEntity = receivableStatementDao.queryPartnerReceivableByStatemenNo(statementBillNoList.get(0));
        statementList.add(woodenStatementEntity);
        //对账单确认状态判断
        if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(
                woodenStatementEntity.getStatementStatus())) {
            throw new SettlementException("对账单确认状态为未确认不可以进行还款!");
        }
        // 1、新增还款单实体，单位保存
        BillRepaymentEntity billRepaymentEntity = addBillRepayment(billStatementToPaymentQueryDto, cInfo);
        //还款单单号
        String repaymentNo = billRepaymentEntity.getRepaymentNo();
        // 排序类，所有对账单按照业务日期排序
        ListComparator orderComparator = new ListComparator("businessDate");
        // 按时间排序，预收收单核销时按业务时间先后排序
        Collections.sort(statementList, orderComparator);

        // 2、调用核销操作
        // 还款单执行每次按对账单还款后剩余金额
        BigDecimal unpaidAmountLeft = billRepaymentEntity.getAmount();

        // 获取核销批次号
        String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

        //生成其中待核销的一个应收单，永远设置还款单的收入部门
        BillReceivableEntity rtnBillReceivableEntity = null;

        for (StatementRecivableEntity entity : statementList) {

            logger.info("还款/批量还款单时,循环核销对账单开始,对账单号：" + entity.getStatementBillNo());

            // 如果还款单金额小于等于单个对账单的未还款金额，按还款单金额核销还款，并break
            if (unpaidAmountLeft.compareTo(entity.getPeriodUnverifyRecAmount()) <= 0) {
                billRepaymentEntity.setAmount(unpaidAmountLeft);

                // 调用核销操作
                if(rtnBillReceivableEntity==null){
                    rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),billRepaymentEntity, cInfo, writeoffBatchNo);
                }else{
                    billRepayWriteoff(entity.getStatementBillNo(),billRepaymentEntity, cInfo, writeoffBatchNo);
                }



                // 修改对账单未还款金额
                entity.setPeriodUnverifyRecAmount(entity.getPeriodUnverifyRecAmount().subtract(unpaidAmountLeft));
                //本期已还金额
                entity.setPeriodRrpayAmount(unpaidAmountLeft);

                try {
                    receivableStatementDao.updateStatementRecivableEntity(entity);
                }catch(SettlementException e){
                    logger.error(e.getMessage());
                    throw  new SettlementException("更新对账单失败："+e.getMessage());
                }

                // 新增对账单还款数据(账单号、还款单号、还款金额)
                addSoaRepayment(entity.getStatementBillNo(),
                        billRepaymentEntity.getRepaymentNo(),
                        billRepaymentEntity.getAmount());
                break;

                // 反之按对账单的未还款金额去核销
            } else if (unpaidAmountLeft.compareTo(entity.getPeriodUnverifyRecAmount()) > 0) {
                billRepaymentEntity.setAmount(entity.getPeriodUnverifyRecAmount());

                // 调用核销操作
                rtnBillReceivableEntity = billRepayWriteoff(entity.getStatementBillNo(),billRepaymentEntity, cInfo, writeoffBatchNo);
                unpaidAmountLeft = unpaidAmountLeft.subtract(entity.getPeriodUnverifyRecAmount());


                // 修改对账单未还款金额
                entity.setPeriodUnverifyRecAmount(entity.getPeriodUnverifyRecAmount().subtract(unpaidAmountLeft));
                //本期已还金额
                entity.setPeriodRrpayAmount(unpaidAmountLeft);
                try {
                    receivableStatementDao.updateStatementRecivableEntity(entity);
                }catch(SettlementException e){
                    logger.error(e.getMessage());
                    throw new SettlementException("更新对账单失败："+e.getMessage());
                }

                // 新增对账单还款数据(账单号、还款单号、还款金额)
                addSoaRepayment(entity.getStatementBillNo(),
                        billRepaymentEntity.getRepaymentNo(),
                        billRepaymentEntity.getAmount());
            }
        }
        //根据核销的应收单修改收款、收入部门，且重设全额还款金额
        billRepaymentEntity.setAmount(billStatementToPaymentQueryDto.getRepaymentAmount());//重设还款金额
        if(rtnBillReceivableEntity != null){
        billRepaymentEntity.setCollectionOrgCode(rtnBillReceivableEntity.getDunningOrgCode());// 收款部门编码
        billRepaymentEntity.setCollectionOrgName(rtnBillReceivableEntity.getDunningOrgName());// 收款部门名称
        }
        // 保存还款单
        billRepaymentEntity.setCreateOrgCode(cInfo.getCurrentDeptCode());
        billRepaymentEntity.setCreateOrgName(cInfo.getCurrentDeptName());
        billRepaymentService.addBillRepayment(billRepaymentEntity, cInfo);
        logger.info("还款/批量还款单时,新增还款单成功,还款单号：" + billRepaymentEntity.getRepaymentNo());

        // 4、调用费控接口处理数据
        // 如果是电汇或支票还款，且校验通过，则调用费控占用汇款接口
        if (StringUtils.isNotEmpty(billStatementToPaymentQueryDto.getRemittanceNumber())
                && (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(billStatementToPaymentQueryDto.getRepaymentType())
                ||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.equals(billStatementToPaymentQueryDto.getRepaymentType()))) {

            logger.info("还款/批量还款单时,调用费控接口占用汇款开始,汇款单号："
                    + billStatementToPaymentQueryDto.getRemittanceNumber());
            fossToFinanceRemittanceService.obtainClaim(
                    billStatementToPaymentQueryDto.getRepaymentAmount(),
                    billStatementToPaymentQueryDto.getRemittanceNumber(),
                    cInfo.getCurrentDeptCode(),billRepaymentEntity.getRepaymentNo());
        }

        //5.网上支付则调用财务接口占用
        //DMANA-6876 FOSS-网上支付发更改重新还款
        if (StringUtils.isNotEmpty(billStatementToPaymentQueryDto.getOnlinePaymentNo())
                && SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(
                billStatementToPaymentQueryDto.getRepaymentType())) {

            logger.info("还款/批量还款单时,调用费控接口占用网上支付款开始,网上支付编号："
                    + billStatementToPaymentQueryDto.getOnlinePaymentNo());
            financeOnlinePayWSProxy.obtain(billStatementToPaymentQueryDto.getOnlinePaymentNo()
                    , billStatementToPaymentQueryDto.getRepaymentAmount());

        }
        logger.debug("还款/批量还款单结束...");
        //返回还款单号
        return repaymentNo;
    }

    /**
     * 添加对账单明细
     * @param dto
     * @throws SettlementException
     */
    @Transactional
    public void addReceivableStatementDetail(StatementRecivableDto dto) throws SettlementException {

        // 获取当前登录信息
        CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
        /**
         * 定义需要传递参数的实体类
         */
        BillReceivableDto billReceivableDto = new BillReceivableDto();

        //获取需要添加的应收单号和需要修改对账单单号
        if(null==dto.getStatementBillNo()){
            throw new SettlementException("对账单号不能为空！");
        }
        //获取要添加的应收单单号
        if(null==dto.getReceivableNo()){
            throw new SettlementException("应收单号不能空！");
        }else{
            List<String> list = new ArrayList<String>();
            list.add(dto.getReceivableNo());
            billReceivableDto.setWaybillNoList(list);
        }
        //根据对对账单号查询出对账单的信息
        //创建一个对象
        StatementRecivableEntity entity;
        try {
            entity = receivableStatementDao.queryPartnerReceivableByStatemenNo(dto.getStatementBillNo());
        }catch (SettlementException e){
            logger.error(e.getMessage());
            throw new  SettlementException("查询对账单信息失败！");
        }
        if(null==entity){
            throw new  SettlementException("对账单信息不存在！");
        }
        if(entity.getStatementStatus().equals("Y")){
            throw new  SettlementException("对账单已确认不能再添加！");
        }
        //根据应收单号查询应收单信息
        //查询应收单的结果集
        List<BillReceivableEntity> billReceivableEntityList;
        //申明对账单明细的对象
       List<Object> statementRecivableDEntityList = new ArrayList<Object>();
        //声明一个对账单的对象
        StatementRecivableEntity statementRecivableEntity = new StatementRecivableEntity();
        //定义一个应收单号的集合
        List<String> receivableNoList = new ArrayList<String>();
        Map<String,Object> map = new HashMap<String,Object>();
        /**
         * 如果日期则是按照合伙人制作查询否则就是单号
         */
        try{
            billReceivableEntityList = billReceivableMonthlyQueryService.queryNotPageBillRecivableByList(billReceivableDto);
        }catch (SettlementException e){
            throw  new SettlementException("查询失败："+e.getMessage());
        }

        //本期应收金额（总金额）本期应收
        BigDecimal bigDecimalAmount = BigDecimal.ZERO;
        //本期未还（未核销）本期剩余应收
        BigDecimal bigDecimalUnverifyAmount = BigDecimal.ZERO;
        //本期已还(已核销)
        BigDecimal bigDecimalVerifyAmount = BigDecimal.ZERO;
        //设置本期发生额
        BigDecimal periodAmount = BigDecimal.ZERO;
        //设置本期剩余应收金额
        BigDecimal periodUnverifyRecAmount = BigDecimal.ZERO;
        //String customerCode = null;
        for(int i=0;i<billReceivableEntityList.size();i++){
            if(null==billReceivableEntityList.get(i).getCustomerCode()){
                throw new SettlementException("合伙人信息为空！");
            }
            if(!entity.getCustomerCode().equals(billReceivableEntityList.get(i).getCustomerCode())){
                throw new SettlementException("合伙人信息与当前对账单中的信息不一致！");
            }
            //本期应收金额（总金额）本期应收
            bigDecimalAmount = entity.getPeriodRecAmount().add(billReceivableEntityList.get(i).getAmount());
            //本期未还（未核销）本期生剩余应收
            bigDecimalUnverifyAmount =  entity.getPeriodNpayAmount().add(billReceivableEntityList.get(i).getUnverifyAmount());
            //本期已还(已核销)
            bigDecimalVerifyAmount = entity.getPeriodRrpayAmount().add(billReceivableEntityList.get(i).getVerifyAmount());
            //应收单号放入应收单号集合中
            receivableNoList.add(billReceivableEntityList.get(i).getReceivableNo());
            //设置本期发生额
            periodAmount =  entity.getPeriodAmount().add(billReceivableEntityList.get(i).getAmount());
            entity.getPeriodAmount().add(bigDecimalAmount);
            //设置本期剩余应收金额
            periodUnverifyRecAmount=entity.getPeriodUnverifyRecAmount().add(billReceivableEntityList.get(i).getUnverifyAmount());

            //应收单明细不为空则生成对账单明细
            Map<String,Object> statementRecivableDEntityMap = new HashMap<String, Object>();
            //ID
            statementRecivableDEntityMap.put("id", UUIDUtils.getUUID());
            //对账单单号
            statementRecivableDEntityMap.put("billStatementNo", dto.getStatementBillNo());
            //单号（应收单单号）
            statementRecivableDEntityMap.put("sourceBillNo", billReceivableEntityList.get(i).getReceivableNo());
            //业务日期
            statementRecivableDEntityMap.put("businessDate", billReceivableEntityList.get(i).getBusinessDate());
            //运单号
            statementRecivableDEntityMap.put("waybillNo", billReceivableEntityList.get(i).getWaybillNo());
            //目的站
            statementRecivableDEntityMap.put("arrvRegionCode", billReceivableEntityList.get(i).getTargetOrgCode());
            //产品类型
            statementRecivableDEntityMap.put("proType", billReceivableEntityList.get(i).getProductCode());
            //件数
            statementRecivableDEntityMap.put("qty", billReceivableEntityList.get(i).getGoodsQtyTotal());
            //计费体积
            statementRecivableDEntityMap.put("billingVolume", billReceivableEntityList.get(i).getGoodsVolumeTotal());
            //计费重量
            statementRecivableDEntityMap.put("billWeight", billReceivableEntityList.get(i).getBillWeight());
            //付款方式
            statementRecivableDEntityMap.put("paymentType", billReceivableEntityList.get(i).getPaymentType());
            //提货方式
            statementRecivableDEntityMap.put("receiveMethod", billReceivableEntityList.get(i).getReceiveMethod());
            //原始未核销金额
            statementRecivableDEntityMap.put("unverifyAmount", billReceivableEntityList.get(i).getUnverifyAmount());
            //总金额
            statementRecivableDEntityMap.put("amount", billReceivableEntityList.get(i).getAmount());
            //单号
            /*statementRecivableDEntityMap.put("sourceBillNo", billReceivableEntityList.get(i).getSourceBillNo());*/
            //单据子类型
            statementRecivableDEntityMap.put("billType", billReceivableEntityList.get(i).getBillType());
            //客户\代理编码
            statementRecivableDEntityMap.put("customerCode", billReceivableEntityList.get(i).getCustomerCode());
            //客户名称
            statementRecivableDEntityMap.put("customerName", billReceivableEntityList.get(i).getCustomerName());
            //已核销金额
            statementRecivableDEntityMap.put("verifyAmount", billReceivableEntityList.get(i).getVerifyAmount());
            //部门编码
            statementRecivableDEntityMap.put("orgCode", currentInfo.getCurrentDeptCode());
            //部门名称
            statementRecivableDEntityMap.put("orgName", currentInfo.getCurrentDeptName());
            //记账日期
            statementRecivableDEntityMap.put("accountDate", billReceivableEntityList.get(i).getAccountDate());
            //提货网点
            statementRecivableDEntityMap.put("customerPickupOrgName", billReceivableEntityList.get(i).getCustomerPickupOrgCode());
            //货物名称
            statementRecivableDEntityMap.put("goodsName", billReceivableEntityList.get(i).getGoodsName());
            //始发网点编码
            statementRecivableDEntityMap.put("origOrgCode", billReceivableEntityList.get(i).getOrigOrgCode());
            //始发网点名称
            statementRecivableDEntityMap.put("origOrgName", billReceivableEntityList.get(i).getOrigOrgName());
            //到达部门编码
            statementRecivableDEntityMap.put("destOrgCode", billReceivableEntityList.get(i).getDestOrgCode());
            //到达部门名称
            statementRecivableDEntityMap.put("destOrgName", billReceivableEntityList.get(i).getDestOrgName());
            //发货客户编码
            statementRecivableDEntityMap.put("deliveryCustomerCode", billReceivableEntityList.get(i).getDeliveryCustomerCode());
            //签收日期
            statementRecivableDEntityMap.put("signDate", billReceivableEntityList.get(i).getConrevenDate());
            //审核状态
            statementRecivableDEntityMap.put("auditStatus", SettlementDictionaryConstants.SETTLEMENT_INVOICE_APPLY_INVOICE_NO);
            //备注
            statementRecivableDEntityMap.put("notes", billReceivableEntityList.get(i).getNotes());
            //运单开单时间
            statementRecivableDEntityMap.put("billBeginTime", billReceivableEntityList.get(i).getCreateTime());
            //创建时间
            statementRecivableDEntityMap.put("createTime",new Date());
            //是否删除
            statementRecivableDEntityMap.put("isDelete","N");
            //根据应收单号查询应收单明细
            List<BillReceivableDEntity> billReceivableDEntities;
            try {
                billReceivableDEntities = billReceivableDService.queryByReceivableNo(billReceivableEntityList.get(i).getReceivableNo());
            }catch (Exception e){
                logger.error(e.getMessage());
                throw  new SettlementException(e.getMessage());
            }
            if(billReceivableDEntities!=null && billReceivableDEntities.size()>0){
                for(BillReceivableDEntity bList :billReceivableDEntities ){
                    //根据应收单明信息生成对账单所需的数据根据应收类型判断收费类型
                    statementRecivableDEntityMap.put("singleOperationFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("packageFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("insuranceFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("disbursementFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("deliveryFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("baseDeliveryFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("mattressOperationFee",BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("agentDeclarationFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("removePackingFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("registrationFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("otherFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("upstairsFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("warehouseDeliveryFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("largeUpstairsFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("superBigDecimalFee", BigDecimal.ZERO);
                    statementRecivableDEntityMap.put("singleReturnFee", BigDecimal.ZERO);
                    //FEE_TYPE__BILLING_OPERATE = "BO";// 开单操作费
                    if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__BILLING_OPERATE)){
                        statementRecivableDEntityMap.put("singleOperationFee", bList.getAmount());
                        //FEE_TYPE__PACKAGING_EXTRA = "PE";// 包装费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__PACKAGING_EXTRA)){
                        statementRecivableDEntityMap.put("packageFee", bList.getAmount());
                        //FEE_TYPE__SUPPORT_VALUE_EXTRA = "SVE";// 保价费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__SUPPORT_VALUE_EXTRA)){
                        statementRecivableDEntityMap.put("insuranceFee", bList.getAmount());
                        // FEE_TYPE__COD_HANDING_EXTRA = "CHE";// 代收货款手续费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__COD_HANDING_EXTRA)){
                        statementRecivableDEntityMap.put("disbursementFee", bList.getAmount());
                        // FEE_TYPE__DELIVER_EXTRA_NO_UPSTAIRS = "DENU";// 送货费提成（不含上楼）
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__DELIVER_EXTRA_NO_UPSTAIRS)){
                        statementRecivableDEntityMap.put("deliveryFee", bList.getAmount());
                        //FEE_TYPE__BASIC_DELIVER_EXTRA = "BDE";// 基础送货费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__BASIC_DELIVER_EXTRA)){
                        statementRecivableDEntityMap.put("baseDeliveryFee", bList.getAmount());
                        //FEE_TYPE__MATTRESS_OPRATE_EXTRA = "MOE";// 床垫操作费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__MATTRESS_OPRATE_EXTRA)){
                        statementRecivableDEntityMap.put("mattressOperationFee",bList.getAmount());
                        //FEE_TYPE__AGENCY_CUSTOMERS_CHARGES_EXTRA = "ACCE";// 代理报关费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__AGENCY_CUSTOMERS_CHARGES_EXTRA)){
                        statementRecivableDEntityMap.put("agentDeclarationFee", bList.getAmount());
                        //FEE_TYPE__DISMANTLE_PACKAGE_EXTRA = "DPE";// 拆包装费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__DISMANTLE_PACKAGE_EXTRA)){
                        statementRecivableDEntityMap.put("removePackingFee", bList.getAmount());
                        // FEE_TYPE__REGISTER_PARK_EXTRA = "RPE";// 登记费停车费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__REGISTER_PARK_EXTRA)){
                        statementRecivableDEntityMap.put("registrationFee", bList.getAmount());
                        //FEE_TYPE__OTHERS_EXTRA = "OE";// 其他费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__OTHERS_EXTRA)){
                        statementRecivableDEntityMap.put("otherFee", bList.getAmount());
                        //FEE_TYPE__GOODS_UPSTAIRS_EXTRA= "GU";// 送货上楼费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__GOODS_UPSTAIRS_EXTRA)){
                        statementRecivableDEntityMap.put("upstairsFee", bList.getAmount());
                        //FEE_TYPE__GOODS_ENTRY_EXTRA = "GE";// 送货进仓费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__GOODS_ENTRY_EXTRA)){
                        statementRecivableDEntityMap.put("warehouseDeliveryFee", bList.getAmount());
                        // FEE_TYPE__GOODS_BIG_UPSTAIRS_EXTRA = "GBU";// 大件上楼费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__GOODS_BIG_UPSTAIRS_EXTRA)){
                        statementRecivableDEntityMap.put("largeUpstairsFee", bList.getAmount());
                        //FEE_TYPE__LONG_SEND_EXTRA = "LSE";// 超远派送费提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__LONG_SEND_EXTRA)){
                        statementRecivableDEntityMap.put("superBigDecimalFee", bList.getAmount());
                        //BILL_RECEIVABLE__RECEIVABLE_TYPE__PUBLISH_FREIGHT_EXTRA // 签收单返回提成
                    }else if(bList.getReceivableType().equals(SettlementDictionaryConstants.RECEIVABLE_FEE_TYPE__PROOF_DELIVERY_EXTRA)){
                        statementRecivableDEntityMap.put("singleReturnFee", bList.getAmount());
                    }
                }
            }
            //封装对账的明细
            statementRecivableDEntityList.add(statementRecivableDEntityMap);
        }
        //设置本期应收金额
        statementRecivableEntity.setPeriodRecAmount(bigDecimalAmount);
        //设置本期已还金额
        statementRecivableEntity.setPeriodRrpayAmount(bigDecimalVerifyAmount);
        //设置本期未还金额
        statementRecivableEntity.setPeriodNpayAmount(bigDecimalUnverifyAmount);
        //设置本期发生额
        statementRecivableEntity.setPeriodAmount(periodAmount);
        //设置本期剩余应收金额
        statementRecivableEntity.setPeriodUnverifyRecAmount(periodUnverifyRecAmount);
        //设置对账单单号
        statementRecivableEntity.setStatementBillNo(dto.getStatementBillNo());

        try {
            receivableStatementDao.updateByStatementNo(statementRecivableEntity);
        }catch (SettlementException e){
            logger.error(e.getMessage());
            throw  new SettlementException("更新对账信息失败！"+e.getMessage());
        }

        try {
            if(null!=statementRecivableDEntityList&&statementRecivableDEntityList.size()>0) {
                receivableStatementQueryDao.AddRecivalbeStatementQuery(statementRecivableDEntityList);
            }
        }catch (SettlementException e){
            throw  new SettlementException("生成对账单明细失败！"+e.getMessage());
        }
        //更新应收单中的对账单单号
        try{
            map.put("statementBillNo",dto.getStatementBillNo());
            map.put("receivableNoList",receivableNoList);
            billReceivableService.updateByReceivableNo(map);
        }catch (SettlementException e){
            throw new SettlementException(e.getErrorCode());
        }

    }

    /**
     * 根据对账单号查询对账单信息
     * @param list
     * @return
     * @throws SettlementException
     */
    public List<StatementRecivableEntity> queryPartnerStatementList(List<String> list) throws SettlementException {
        List<StatementRecivableEntity> statementRecivableEntities;
        try{
            statementRecivableEntities = receivableStatementDao.queryPartnerStatementList(list);
        }catch (SettlementException e){
            logger.error(e.getMessage());
            throw new  SettlementException ("查询异常");
        }
        if(null==statementRecivableEntities){
            throw new SettlementException("对账信息不存在！");
        }
        return statementRecivableEntities;
    }

    /**
     * 根据对账单号查询对账单信息和校验对账单是否可以还款
     * @param statementNoList
     * @return
     * @throws SettlementException
     */
    public StatementRecivableEntity repaymentBillofStatementNo(List<String> statementNoList) throws SettlementException {
        //创建一个对象
        StatementRecivableEntity entity = new StatementRecivableEntity();
        //客户编码
        String customerCode =null;
        //对账单号
        StringBuffer statement = new StringBuffer();
        //本期发生额
        BigDecimal periodRecAmount = BigDecimal.ZERO;
       //本期剩余应还金额
        BigDecimal periodUnverifyRecAmount = BigDecimal.ZERO;

        //计算多个对账的总金额
        for(int i=0;i<statementNoList.size();i++){
            try {
                entity = receivableStatementDao.queryPartnerReceivableByStatemenNo(statementNoList.get(i).toString());
            }catch (SettlementException e){
                logger.error(e.getMessage());
                throw new SettlementException("查询对账信息失败："+e.getMessage());
            }
            //判断是否时一个客户的
            if(null!=entity){
                if(null==customerCode){
                    customerCode = entity.getCustomerCode();
                }
                if(!customerCode.equals(entity.getCustomerCode())){
                    throw  new SettlementException("不能包含多个客户信息！");
                }
                statement.append(entity.getStatementBillNo()+",");
                //本期发生额相加
                periodRecAmount.add(entity.getPeriodRecAmount());
                //本期剩余金额相加
                periodUnverifyRecAmount.add(entity.getPeriodUnverifyRecAmount());
            }
        }
        //设置对账单号
        String statementNo = statement.substring(0,statement.length()-1).toString();
        entity.setStatementBillNo(statementNo);
        entity.setPeriodUnverifyRecAmount(periodUnverifyRecAmount);
        entity.setPeriodRecAmount(periodRecAmount);
        return entity;
    }


    /**
     * 生成对账单还款单关系数据
     * @author ddw
     * @date 2014-06-17
     */
    private void addSoaRepayment(String statementNo, String repaymentNo,BigDecimal repaymentAmount) {
        // 生成对账单还款单关系数据
        SoaRepaymentEntity entity = new SoaRepaymentEntity();
        entity.setId(UUIDUtils.getUUID());
        entity.setStatementBillNo(statementNo);
        entity.setRepaymentNo(repaymentNo);
        entity.setRepaymentAmount(repaymentAmount);
        entity.setPaymentDate(new Date());

        // 保存对账单还款单关系数据
        soaRepaymentManageService.add(entity);

        logger.info("还款/批量还款单时,生成对账单还款单关系数据成功,对账单号："
                + entity.getStatementBillNo() + ",还款单号："
                + entity.getRepaymentNo());
    }

    /**
     * 按对账单进行核销
     * @author ddw
     * @date 2014-06-17
     */
    private BillReceivableEntity billRepayWriteoff(String statementNo,
                                                   BillRepaymentEntity entity, CurrentInfo cInfo,
                                                   String writeoffBatchNo) {

        // 循环按每个对账单处理

        // 根据对账单号查询对账单明细
        List<StatementRecivableDEntity> statementDetailEntitys = receivableStatementQueryDao.queryReceivalbeStatementByBillNo(statementNo);
        if (CollectionUtils.isEmpty(statementDetailEntitys)) {

            logger.info("还款/批量还款按对账单核销时,对账单明细记录为空，无法核销");
            throw new SettlementException("对账单明细记录为空，无法核销");
        }

        // 从对账单明细中获取应收单号、应付单号、预收单号、预付单号
        List<String> recNos = new ArrayList<String>();
        List<String> payNos = new ArrayList<String>();
        for (StatementRecivableDEntity statmentDetailEntity : statementDetailEntitys) {
            // 始发提成应收单单号
           /* if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE
                    .equals(statmentDetailEntity.getBillType())) {*/
                recNos.add(statmentDetailEntity.getSourceBillNo());
           /* }*/
            // 委托派费应收单单号
           /* else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE
                    .equals(statmentDetailEntity.getBillType())) {
                payNos.add(statmentDetailEntity.getWaybillNo());
            }*/
         /*   else {
                logger.info("还款/批量还款按对账单核销时,对账单明细单据大类型异常，无法核销");
                throw new SettlementException("对账单明细单据大类型异常，无法核销");
            }*/
        }

        List<BillReceivableEntity> recs = new ArrayList<BillReceivableEntity>();

        // 根据来源单号查询始发提成应收单
        if (CollectionUtils.isNotEmpty(recNos)) {
            recs = billReceivablePartnerService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
            // 校应收单原始单据数量和对账单明细数量是否匹配
            if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recNos.size())
                    || (CollectionUtils.isEmpty(recs) && recNos.size() > 0)) {
                logger.info("还款/批量还款按对账单核销时,对账单明细数量和应收单原始单据数量不相等，无法核销");
                throw new SettlementException("对账单明细数量和应收单原始单据数量不相等，无法核销");
            }
        }

        // 根据来源单号查询委托
        if (CollectionUtils.isNotEmpty(payNos)) {
            recs = billReceivablePartnerService.queryByReceivableNOs(payNos, FossConstants.ACTIVE);
            // 校应付单原始单据数量和对账单明细数量是否匹配
            if ((CollectionUtils.isNotEmpty(recs) && recs.size() != recs.size())
                    || (CollectionUtils.isEmpty(recs) && recs.size() > 0)) {
                logger.info("还款/批量还款按对账单核销时,对账单明细数量和应付单原始单据数量不相等，无法核销");
                throw new SettlementException("对账单明细数量和应付单原始单据数量不相等，无法核销");
            }
        }

        BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

        //获取其中待核销的一个应收单
        BillReceivableEntity rtnBillReceivableEntity = recs.get(0);

        // 设置核销参数中的核销方列表：应收单列表、应付单列表、预收单列表、预付单列表
        writeoffDto.setBillReceivableEntitys(recs);
       // writeoffDto.setBillPayableEntitys(pays);

        // 设置还款单
        writeoffDto.setBillRepaymentEntity(entity);

        writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

        // 核销类型为手工核销
        writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);

        // 核销对账单编号取对账单上的单据编号
        writeoffDto.setStatementBillNo(statementNo);

        // 核销对账单明细
        writeoffStatementDetailForRepayment(writeoffDto, cInfo);

        return rtnBillReceivableEntity;
    }

        /**
         * 对账单明细核销，包括：应收冲应付、还款冲应收
         * @author ddw
         * @date 2014-06-17
         */
        private BillWriteoffOperationDto writeoffStatementDetailForRepayment(
                BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
            // 初始化返回结果
            BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
            writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());

            // 应收冲应付
          /*  if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
                    && CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
                logger.info("还款/批量还款按对账单核销时,核销应收冲应付...");
                writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
                writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
                writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
            }
            */
            // 还款冲应收
            if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
                    && writeoffDto.getBillRepaymentEntity() != null) {
                logger.info("还款/批量还款按对账单核销时,核销还款冲应收...");
                billWriteoffService.writeoffRepaymentAndReceibable(writeoffDto,currentInfo);
                writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
                writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
            }

            return writeoffResultDto;
        }



    /**
     * 新增还款单
     * @author ddw
     * @date 2014-06-17
     */
    private BillRepaymentEntity addBillRepayment(BillStatementToPaymentQueryDto dto, CurrentInfo cInfo) {

        logger.info("还款/批量还款单时,新增还款单开始...");
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
        entity.setOaPaymentNo(dto.getRemittanceNumber());// OA汇款编号
        entity.setCreateOrgCode(cInfo.getCurrentDeptCode());// 录入部门编码
        entity.setCreateOrgName(cInfo.getCurrentDeptCode());// 录入部门名称
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
        entity.setNotes(dto.getDescription());// 备注
        entity.setVersionNo(FossConstants.INIT_VERSION_NO);// 版本号
        entity.setOnlinePaymentNo(dto.getOnlinePaymentNo());// 在线支付编号
        entity.setOaPayee(dto.getRemittanceName());//OA汇款人

        // 保存还款单
        return entity;
    }


    private void validateDto(StatementRecivableDto updateDto) {
        //声明目标部门集合
        List<String> targetLsit = new ArrayList<String>();

        //如果部门存在，则获取当前部门与用户所管辖部门的交集
        if (StringUtils.isNotBlank(updateDto.getCreateOrgCode())) {
            targetLsit.add(updateDto.getCreateOrgCode());
        } else{
            //如果部门不存在，小区存在，则获取小区地下所有部门与该用户所管辖部门交集
            if(StringUtils.isNotBlank(updateDto.getSmallRegion())){
                //调用综合方法查询
                List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(updateDto.getSmallRegion());
                //循环部门，获取用户所管辖部门与查询到部门的交集
                for(OrgAdministrativeInfoEntity en: orgList){
                    targetLsit.add(en.getCode());
                }
            }else{
                //如果部门、小区都不存在，而大区存在，	则获取大区底下所有部门与该用户所管辖部门交集
                if(StringUtils.isNotBlank(updateDto.getLargeRegion())){
                    //调用综合方法查询
                    List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(updateDto.getLargeRegion());
                    //循环部门，获取用户所管辖部门与查询到部门的交集
                    for(OrgAdministrativeInfoEntity en: orgList){
                        targetLsit.add(en.getCode());
                    }
                }
            }
        }
        // 设置可查询部门结果集
        updateDto.setOrgCodeList(targetLsit);
    }
    
	/* 
	 * 作废还款单还原合伙人收款对账单中的金额
	 * @see com.deppon.foss.module.settlement.writeoff.api.server.service.IReceivableStatementService#updateStatementForDisableRepayment(com.deppon.foss.module.settlement.writeoff.api.shared.domain.StatementRecivableEntity)
	 */
	@Override
	public void updateStatementForDisableRepayment(
			StatementRecivableEntity stateReceivableEntity) {
		receivableStatementDao.updateStatementRecivableEntity(stateReceivableEntity);
	}


    public void setOrgAdministrativeInfoComplexService(IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }

    public void setPublicBankAccountService(IPublicBankAccountService publicBankAccountService) {
        this.publicBankAccountService = publicBankAccountService;
    }

    public void setBillReceivableMonthlyQueryService(IBillReceivableMonthlyQueryService billReceivableMonthlyQueryService) {
        this.billReceivableMonthlyQueryService = billReceivableMonthlyQueryService;
    }

    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    public void setReceivableStatementDao(IReceivableStatementDao receivableStatementDao) {
        this.receivableStatementDao = receivableStatementDao;
    }

    public void setReceivableStatementQueryDao(IReceivableStatementQueryDao receivableStatementQueryDao) {
        this.receivableStatementQueryDao = receivableStatementQueryDao;
    }

    public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }

    public void setBillReceivableDService(IBillReceivableDService billReceivableDService) {
        this.billReceivableDService = billReceivableDService;
    }

    public void setBillReceivableService(IBillReceivableService billReceivableService) {
        this.billReceivableService = billReceivableService;
    }

    public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
        this.billRepaymentService = billRepaymentService;
    }

    public void setFossToFinanceRemittanceService(IFossToFinanceRemittanceService fossToFinanceRemittanceService) {
        this.fossToFinanceRemittanceService = fossToFinanceRemittanceService;
    }

    public void setSoaRepaymentManageService(ISoaRepaymentManageService soaRepaymentManageService) {
        this.soaRepaymentManageService = soaRepaymentManageService;
    }

    public void setFinanceOnlinePayWSProxy(IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
        this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
    }

    public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
        this.billWriteoffService = billWriteoffService;
    }

    public void setBillReceivablePartnerService(IBillReceivablePartnerService billReceivablePartnerService) {
        this.billReceivablePartnerService = billReceivablePartnerService;
    }
}
