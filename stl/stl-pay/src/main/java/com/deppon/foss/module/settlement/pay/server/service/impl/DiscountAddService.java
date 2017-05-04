package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.settlement.common.api.server.dao.IProductEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.module.settlement.pay.api.server.dao.IDiscountManagementDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountAddService;
import com.deppon.foss.module.settlement.pay.api.server.service.IDiscountRateService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementDEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountManagementEntity;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DiscountRateEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountAddDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountManagementDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DiscountRateDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.ReceivableBillDto;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 073615 on 2015/2/6.
 */
public class DiscountAddService implements IDiscountAddService {

    private static Logger logger = Logger.getLogger(DiscountAddService.class);
    /**
     * 应收单查询服务
     */
    private IBillReceivableQueryService billReceivableQueryService;
    /**
     * 折扣率服务
     */
    private IDiscountRateService discountRateService;
    /**
     * 应付单服务
     */
    private IBillPayableService billPayableService;
    /**
     * 折扣单管理Dao
     */
    private IDiscountManagementDao discountManagementDao;
    /**
     * 基础单据管理服务
     */
    private ISettlementCommonService settlementCommonService;
    /**
     * 实际承运信息服务
     */
    private IActualFreightService actualFreightService;

    /**
     * 组织信息
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 产品类型Dao
     */
    private IProductEntityDao productEntityDao;


    public List<BillReceivableEntity> queryReceiableByCondition(DiscountAddDto dto,int start,int limit) {
        BillReceivableDto queryDto = new BillReceivableDto();
        queryDto.setCustomerCode(dto.getCustomerCode());
        queryDto.setSelectDateType(SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS);
        queryDto.setActive(FossConstants.ACTIVE);
        queryDto.setEndDate(dto.getEndDate());
        queryDto.setStartDate(dto.getStartDate());
        //标准快递、360特惠件、电商尊享件和商务专递件
        List<String> productCodes = new ArrayList<String>();
        productCodes.addAll(productEntityDao.queryPackageProductCode());
        
        queryDto.setProductCode(productCodes);
        //未打折
        queryDto.setIsDiscount(FossConstants.NO);
        //始发应收、到达应收、空运到达代理应收、到达快递代理应收,偏线到达代理应收
        String[] billTypes = {SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
        };
        queryDto.setBillTypes(billTypes);
        //付款方式为临时欠款、月结
        String[] paymetnTypes = {SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
                SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
        };
        queryDto.setPaymentTypes(paymetnTypes);
        //查询应收单信息
        List<BillReceivableEntity> list =  billReceivableQueryService.
                queryBillReceivableByBusinessDate(start,limit,queryDto, null);

        return list;
    }

    @Override
    public ReceivableBillDto queryReceiableAmountByCondition(DiscountAddDto dto) {
        ReceivableBillDto queryDto = new ReceivableBillDto();
        queryDto.setCustomerCode(dto.getCustomerCode());
        queryDto.setSelectDateType(SettlementConstants.TAB_DATE_TYPE_FOR_BUSINESS);
        queryDto.setActive(FossConstants.ACTIVE);
        queryDto.setEndDate(dto.getEndDate());
        queryDto.setStartDate(dto.getStartDate());
        //标准快递、360特惠件、电商尊享件和商务专递
        List<String> productCodes = new ArrayList<String>();
        productCodes.addAll(productEntityDao.queryPackageProductCode());
        
        queryDto.setProductCode(productCodes);
        //始发应收、到达应收、空运到达代理应收、到达快递代理应收,偏线到达代理应收
        String[] billTypes = {SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
        };
        queryDto.setBillTypes(billTypes);
        //付款方式为临时欠款、月结
        String[] paymetnTypes = {SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
                SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
        };
        queryDto.setPaymentTypes(paymetnTypes);
        //未打折
        queryDto.setIsDiscount(FossConstants.NO);
        ReceivableBillDto receivableDto = discountManagementDao.queryReceiableAmountByCondition(queryDto);        
        return receivableDto;
    }

    /**
     * 创建折扣单服务
     * @param dto
     */
    @Override
    @Transactional
    public void createDiscount(DiscountAddDto dto) {
        if(StringUtils.isBlank(dto.getCustomerCode())){
            throw new SettlementException("客户编码不能为空");
        }
        DiscountManagementDto queryDiscountDto = new DiscountManagementDto();
        queryDiscountDto.setCustomerNo(dto.getCustomerCode());
        queryDiscountDto.setPeriodBeginDate(dto.getStartDate());
        queryDiscountDto.setPeriodEndDate(dto.getEndDate());
        String[] discountStatus = {SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_CONFIRM,
                SettlementDictionaryConstants.DISCOUNT_BILL_STATUS_NOT_CONFIRM};
        queryDiscountDto.setDiscountStatus(discountStatus);
        queryDiscountDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
        int isDiscount = discountManagementDao.queryPeroidCountByCustomer(queryDiscountDto);
        if(isDiscount >0){
            throw new SettlementException("该客户的折扣单已经生成，不能重复生成！");
        }
        DiscountRateDto rateDto = new DiscountRateDto();
        rateDto.setCustomerCode(dto.getCustomerCode());
        rateDto.setEndDate(dto.getEndDate());
        rateDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
        rateDto.setActive(FossConstants.YES);
        String[] billTypes = {SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
        };
        //付款方式为临时欠款、月结
        String[] paymetnTypes = {SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
                SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT};
        String[] productCode =  productEntityDao.queryPackageProductCode().toArray(new String[0]);
        
        rateDto.setPaymentTypes(paymetnTypes);
        rateDto.setBillTypes(billTypes);
        rateDto.setProductCodes(productCode);
        //生成折扣率
        DiscountRateEntity rateEntity = discountRateService.makeDiscountRate(rateDto);
        if(rateEntity.getCodCrmId() == null
                &&rateEntity.getInsuranceCrmId() == null
                &&rateEntity.getTransportCrmId() == null){
            throw new SettlementException("该客户在对应期间没没有折扣区间信息！");
        }
        dto.setCodDiscountRate(rateEntity.getCodRate());
        dto.setInsuranceDiscountRate(rateEntity.getInsuranceRate());
        dto.setTransportDiscountRate(rateEntity.getTransportRate());
        //生成折扣单号
        String discountNo = this.settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.ZK);
        dto.setDiscountNo(discountNo);
        CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
        //当前操作人
        dto.setCurrentUser(currentInfo);
        //生成折扣明细单
        discountDetailBathAdd(dto);
        //生成折扣单
        discountBillAddByDetail(dto);
        //生成折应付单
        List<DiscountManagementEntity> discountList = discountManagementDao.queryDiscountBillByNo(dto);
        if(CollectionUtils.isNotEmpty(discountList)){
            for(DiscountManagementEntity entity :discountList){
                //封装应付单
                BillPayableEntity payableEntity = new BillPayableEntity();
                buildDiscountPayable(payableEntity,entity,currentInfo);
                if(BigDecimal.ZERO.compareTo(payableEntity.getAmount())==0){
                    continue;
                }
                //新增应付单
                billPayableService.addBillPayable(payableEntity,FossUserContext.getCurrentInfo());
            }
        }
        //更新应收单折扣状态
        dto.setIsDiscount(FossConstants.YES);
        int rows = discountManagementDao.updateReceivableDiscountStatusLock(dto);
        if(rows == 0){
            throw new SettlementException("该客户在对应期间，有多人在进行生成折扣单操作，请稍后重试！");
        }
        //更新运单信息
        //折扣单明细查询
        List<DiscountManagementDEntity> details =
                discountManagementDao.queryDiscountDetailByDiscountNo(dto);
        List<ActualFreightEntity> actEntitys = new ArrayList<ActualFreightEntity>() ;
        //封装实际承运信息中的实体
        if(CollectionUtils.isNotEmpty(details)){
            for(DiscountManagementDEntity detail :details){
                ActualFreightEntity act = new ActualFreightEntity();
                act.setWaybillNo(detail.getWaybillNo());
                //计算折扣金额
                BigDecimal discountAmount = BigDecimal.ZERO;
                if(detail.getCodDiscount() != null){
                discountAmount = discountAmount.add(detail.getCodDiscount());
                }
                if(detail.getTransportDiscount() != null){
                discountAmount = discountAmount.add(detail.getTransportDiscount());
                }
                if(detail.getInsuranceDiscount() != null){
                discountAmount = discountAmount.add(detail.getInsuranceDiscount());
                }
                //如果为非预付即为到付
                if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.equals(detail.getReceiveBillType())){
                    act.setPrePayAmountDiscount(discountAmount);
                }else{
                    act.setToPayAmountDiscount(discountAmount);
                }
                actEntitys.add(act);
            }
        }
        actualFreightService.updateByActualFreightEntitys(actEntitys);

    }

    /**
     * 通过折扣单封装应付单
     * @param payableEntity
     * @param entity
     */
    private void buildDiscountPayable(BillPayableEntity payableEntity,
                                      DiscountManagementEntity entity,
                                      CurrentInfo userEntity){
        payableEntity.setId(UUIDUtils.getUUID());
        //折扣应付单单号
        payableEntity.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF10));
        payableEntity.setCustomerCode(entity.getCustomerNo());
        payableEntity.setCustomerName(entity.getCustomerName());
        payableEntity.setActive(FossConstants.YES);
        //总费用 = 代收货款折扣+运费折扣+保价费折扣
        BigDecimal amount = BigDecimal.ZERO;
        amount = (entity.getCodDiscount()== null?amount:amount.add(entity.getCodDiscount()));
        amount = (entity.getInsuranceDiscount()== null?amount:amount.add(entity.getInsuranceDiscount()));
        amount = (entity.getTransportDiscount()== null?amount:amount.add(entity.getTransportDiscount()));
        //除以100
        amount = amount.divide(new BigDecimal("100"),2,BigDecimal.ROUND_FLOOR);
        payableEntity.setAmount(amount);
        payableEntity.setUnverifyAmount(amount);
        payableEntity.setVerifyAmount(BigDecimal.ZERO);
        payableEntity.setAccountDate(new Date());
        payableEntity.setBusinessDate(new Date());
        payableEntity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__DISCOUNT);
        //单据子类型为折扣单
        payableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__DISCOUNT);
        payableEntity.setSourceBillNo(entity.getDiscountNo());
        payableEntity.setPayableOrgCode(entity.getDiscountOrgCode());
        payableEntity.setPayableOrgName(entity.getDiscountOrgName());
        //子公司
        OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.
                queryOrgAdministrativeInfoByCodeClean(entity.getDiscountOrgCode());
        if(orgEntity != null){
            payableEntity.setPayableComCode(orgEntity.getSubsidiaryCode());
            payableEntity.setPayableOrgName(orgEntity.getSubsidiaryName());
        }
        //手工生成
        payableEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
        //生效状态
        payableEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
        payableEntity.setCreateDate(new Date());
        payableEntity.setCreateUserCode(userEntity.getEmpCode());
        payableEntity.setCreateUserName(userEntity.getEmpName());
        payableEntity.setIsRedBack(FossConstants.NO);
        payableEntity.setIsInit(FossConstants.NO);
        //审核状态为已审核
        payableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);
        payableEntity.setPayStatus(FossConstants.NO);
        payableEntity.setFrozenStatus(FossConstants.NO);
        payableEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
        payableEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__DISCOUNT);
    }

    /**
     * 根据条件生成折扣单明细
     * @param dto
     */
    @Override
    public void discountDetailBathAdd(DiscountAddDto dto) {
        if(StringUtils.isBlank(dto.getCustomerCode())){
            throw new SettlementException("客户编码不能为空");
        }
        //始发应收、到达应收、空运到达代理应收、到达快递代理应收,偏线到达代理应收
        String[] billTypes = {SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
                SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
        };
        dto.setBillTypes(billTypes);
        //付款方式为临时欠款、月结
        String[] paymetnTypes = {SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT,
        SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT
        };
        dto.setPaymentTypes(paymetnTypes);
        //产品类型为 360特惠件、标准快递、电商尊享件和商务专递
        String[] productCodes =  productEntityDao.queryPackageProductCode().toArray(new String[0]);
        dto.setProductCodes(productCodes);
        dto.setActive(FossConstants.YES);
        discountManagementDao.discountDetailBathAdd(dto);
    }

    /**
     * 根据折扣单明细生成折扣单
     * @param dto
     */
    @Override
    public void discountBillAddByDetail(DiscountAddDto dto) {
        if(StringUtils.isBlank(dto.getDiscountNo())){
            throw new SettlementException("折扣单编码为空");
        }
        discountManagementDao.discountBillAddByDetail(dto);

    }


    public void setBillReceivableQueryService(IBillReceivableQueryService billReceivableQueryService) {
        this.billReceivableQueryService = billReceivableQueryService;
    }

    public void setDiscountRateService(IDiscountRateService discountRateService) {
        this.discountRateService = discountRateService;
    }

    public void setBillPayableService(IBillPayableService billPayableService) {
        this.billPayableService = billPayableService;
    }

    public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }

    public void setDiscountManagementDao(IDiscountManagementDao discountManagementDao) {
        this.discountManagementDao = discountManagementDao;
    }

    public void setActualFreightService(IActualFreightService actualFreightService) {
        this.actualFreightService = actualFreightService;
    }

    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

	public IProductEntityDao getProductEntityDao() {
		return productEntityDao;
	}

	public void setProductEntityDao(IProductEntityDao productEntityDao) {
		this.productEntityDao = productEntityDao;
	}
}
