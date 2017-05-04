package com.deppon.foss.module.settlement.closing.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPartnerRelationService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.closing.api.server.service.ICRMPayableBillService;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.BankPayInfo;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ClaimsPayBillGenerateRes;
import com.deppon.foss.module.settlement.closing.api.shared.inteface.domain.ResponsibilityInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IOrgShareService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OrgShareEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.ListComparator;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPayableQueryManageservice;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillPaymentPayService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPayableManageResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentAddDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * CRM到FOSS应付单服务（理赔、服务补救、退运费）.
 *
 * @author 046644-foss-zengbinwen
 * @date 2012-12-4 下午4:09:32
 */
public class CRMPayableBillService implements ICRMPayableBillService {
	
	/**
	 * 记录日志 
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CRMPayableBillService.class);

    /** 应付单服务. */
    private IBillPayableService billPayableService;

    /** 结算综合服务. */
    private ISettlementCommonService settlementCommonService;

    /** 理赔成本划分服务. */
    private IOrgShareService orgShareService;

    /** 运单管理服务. */
    private IWaybillManagerService waybillManagerService;

    /** 客户服务. */
    private ICustomerService customerService;

    /** 组织信息服务. */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /** 理赔service. */
    private IBillClaimService billClaimService;

    /** 散客服务. */
    private INonfixedCustomerService nonfixedCustomerService;

    /**
     * 快递点部映射
     */
    private IExpressPartSalesDeptService expressPartSalesDeptService;
    
    /**
	 *注入service 
	 */
	private IBillPayableQueryManageservice billPayableQueryManageservice;
	
	/**
	 * 注入录入付款单服务
	 */
	private IBillPaymentPayService billPaymentPayService;
	
	/**
	 * 营业部
	 */
	private ISaleDepartmentService saleDepartmentService;
	
	/**
	 * 合伙人网点查询
	 */
	private IPartnerRelationService partnerRelationService;

    /**
     * Sets the nonfixed customer service.
     *
     * @param nonfixedCustomerService the new nonfixed customer service
     */
    public void setNonfixedCustomerService(
            INonfixedCustomerService nonfixedCustomerService) {
        this.nonfixedCustomerService = nonfixedCustomerService;
    }

    /**
     * Sets the bill payable service.
     *
     * @param billPayableService the new bill payable service
     */
    public void setBillPayableService(IBillPayableService billPayableService) {
        this.billPayableService = billPayableService;
    }

    /**
     * Sets the settlement common service.
     *
     * @param settlementCommonService the new settlement common service
     */
    public void setSettlementCommonService(
            ISettlementCommonService settlementCommonService) {
        this.settlementCommonService = settlementCommonService;
    }

    /**
     * Sets the org share service.
     *
     * @param orgShareService the new org share service
     */
    public void setOrgShareService(IOrgShareService orgShareService) {
        this.orgShareService = orgShareService;
    }

    /**
     * Sets the waybill manager service.
     *
     * @param waybillManagerService the new waybill manager service
     */
    public void setWaybillManagerService(
            IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }

    /**
     * Sets the customer service.
     *
     * @param customerService the new customer service
     */
    public void setCustomerService(ICustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Sets the org administrative info service.
     *
     * @param orgAdministrativeInfoService the new org administrative info service
     */
    public void setOrgAdministrativeInfoService(
            IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * Sets the bill claim service.
     *
     * @param billClaimService the new bill claim service
     */
    public void setBillClaimService(IBillClaimService billClaimService) {
        this.billClaimService = billClaimService;
    }

    /**
     * 新增CRM发送到FOSS的应付单.
     *
     * @param request the request
     * @param currentInfo the current info
     * @author 046644-foss-zengbinwen
     * @date 2013-1-25 下午2:24:12
     */
    @Transactional
    public void addCRMBillPayable(ClaimsPayBillGenerateRes request,
                                  CurrentInfo currentInfo) {

        // 判断参数不为空
        if (request == null) {
            //ESB传递参数为空
            throw new SettlementException("ESB传递参数为空");
        }

        // 如果类型不能为空
        if (StringUtils.isEmpty(request.getBusinessType())) {
            //ESB传递理赔类型为空
            throw new SettlementException("ESB传递理赔类型为空");
        }
        
        //理赔方式不能为空
        if(StringUtils.isEmpty(request.getClaimWay())) {
        	//ESB传递的理赔方式为空
        	throw new SettlementException("ESB传递理赔方式为空");
        }
        
        // 如果类型为理赔，
        // 必须有支付类别
        if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__CLAIM.equals(request.getBusinessType())
                && StringUtils.isEmpty(request.getPaymentCategory())) {
            //生成理赔类应付单时，ESB传递支付类别为空
            throw new SettlementException("生成理赔类应付单时，ESB传递支付类别为空");
        }

        // 校验存在是否重复的应付单（理赔、退运费、服务补救）
        List<String> billTypeList = new ArrayList<String>();
        // 理赔
        billTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
        // 退运费
        billTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);
        // 服务补救
        billTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
        // 根据运单号集合和单据类型集合查询应付单信息
        List<BillPayableEntity> list = billPayableService.queryByWaybillNosAndByBillTypes(Arrays.asList(request.getBillNo()), billTypeList);
        if (list.size() > 0) {
            throw new SettlementException("运单已经存在理赔/服务补救/退运费应付单申请");
        }
        // 根据运单号查询运单
        WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(request.getBillNo());
        if (waybill == null) {
            throw new SettlementException("运单号为:" + request.getBillNo() + "的运单在系统中不存在");
        }

        // 新增理赔类型应付单
        BillPayableEntity billPayable = buildBillPayableEntity(request,waybill);

		/*
		 * ISSUE-3585 CRM传递给FOSS的部门标杆编码必须是对应运单上的出发部门或者到达部门， 且一个运单只能存在一条服务补救应付单。
		 * 
		 * 杨书硕 2013-8-15 上午11:14:24
		 */
        //ISSUE-1898 服务补救快递无申请部门限制 2014-03-11
        if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(billPayable.getBillType())
                && !SettlementUtil.isPackageProductCode(billPayable.getProductCode())
                && !billPayable.getPayableOrgCode().equals(billPayable.getOrigOrgCode())
                && !billPayable.getPayableOrgCode().equals(billPayable.getDestOrgCode())) {
            throw new SettlementException("服务补救发起部门必须是对应运单上的出发部门或者到达部门");
        }

        billPayableService.addBillPayable(billPayable, currentInfo);
        //声明当前服务器时间

        // 新增理赔成本划分数据
        if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__CLAIM.equals(request.getBusinessType())) {
            // 获得responsibilityList
            List<ResponsibilityInfo> responsibilityList = request.getResponsibilityInfos();
            if (CollectionUtils.isNotEmpty(responsibilityList)) {
                // 初始化成本总金额为0
                BigDecimal responsibilityMoney = validaExtracted(billPayable,
						responsibilityList);

                // 成本总金额不等于理赔应付单应付金额，不允许生成理赔应付单
                if (responsibilityMoney.compareTo(billPayable.getAmount()) != 0) {
                    // 理赔成本总金额和理赔应付金额不一致
                    throw new SettlementException("理赔成本总金额和理赔应付金额不一致");
                }

            }
        }

        // 新增理赔、服务补救、退运费单据实体
        BillClaimEntity billClaimEntity = buildBillClaimEntity(request,billPayable,waybill);

        // 根据运单号查询有效的理赔单据
        BillClaimEntity billClaimEntityExist = billClaimService.queryBillClaimEntity(request.getBillNo());
        // 校验是否存在重复的有效理赔单据
        if (billClaimEntityExist != null) {
            // 已经存该运单的有效理赔/服务补救/退运费单据
            throw new SettlementException("已经存该运单的有效理赔/服务补救/退运费单据");
        }

        // 新增理赔单据
        billClaimService.addBillClaimEntity(billClaimEntity, currentInfo);
    }

	private BigDecimal validaExtracted(BillPayableEntity billPayable,
			List<ResponsibilityInfo> responsibilityList) {
		// 循环新增成本
        OrgShareEntity orgShare = null;
		BigDecimal responsibilityMoney = BigDecimal.ZERO;
		for (ResponsibilityInfo info : responsibilityList) {
		    // 创建理赔成本划分实体
		    orgShare = new OrgShareEntity();

		    // ID、是否有效、金额、部门编码、应付单号、来源单据类型
		    orgShare.setId(UUIDUtils.getUUID());
		    //是否有效
		    orgShare.setActive(FossConstants.ACTIVE);
		    // 金额
		    orgShare.setAmount(info.getResponsibilityMoney());

		    // 通过 组织标杆编码unifiedCode 查询组织;
		    OrgAdministrativeInfoEntity orgAdmin = orgAdministrativeInfoService
		            .queryOrgAdministrativeInfoByUnifiedCode(info.getResponsibilityDeptCode());
		    if (orgAdmin == null) {
		        throw new SettlementException("标杆编码为:" + info.getResponsibilityDeptCode() + "的组织信息在系统中不存在");
		    }
		    // 部门编码
		    orgShare.setOrgCode(orgAdmin.getCode());
		    // 部门名称
		    orgShare.setOrgName(orgAdmin.getName());
		    //应付单号
		    orgShare.setSourceBillNo(billPayable.getPayableNo());
		    //来源单据类型
		    orgShare.setSourceBillType(billPayable.getBillType());
		    //设置快递部门和编码
		    orgShare.setExpressOrgCode(orgAdmin.getCode());
		    orgShare.setExpressOrgName(orgAdmin.getName());
//					//获取快递点部
//					if(!StringUtils.isEmpty(billPayable.getProductCode()) 
//							&& ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE.equals(billPayable.getProductCode())){
//						//获取出发部门快递点部
//						ExpressPartSalesDeptResultDto shareExpressOrg = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(orgShare.getOrgCode(),nowDate);
//						if(shareExpressOrg==null || StringUtils.isEmpty(shareExpressOrg.getUnifiedCode())){
//							throw new SettlementException("理赔分摊部门："+orgAdmin.getName()+"没有对应的快递点部！");
//						}
//						orgShare.setExpressOrgCode(orgAdmin.getCode());
//						orgShare.setExpressOrgName(orgAdmin.getName());
//					}


		    // 加到成本总金额
		    responsibilityMoney = responsibilityMoney.add(info
		            .getResponsibilityMoney());

		    // 新增成本划分数据
		    orgShareService.addOrgShare(orgShare);
		}
		return responsibilityMoney;
	}

    /**
     * 构建理赔、服务补救、退运费单据实体.
     *
     * @param request the request
     * @return the bill claim entity
     * @author 046644-foss-zengbinwen
     * @date 2012-11-20 下午2:38:16
     */
    private BillClaimEntity buildBillClaimEntity(
            ClaimsPayBillGenerateRes request,BillPayableEntity billPayable,WaybillEntity waybill ) {

        // 初始化理赔、服务补救、退运费单据实体
        BillClaimEntity entity = new BillClaimEntity();

        // ID
        entity.setId(UUIDUtils.getUUID());
        // 运单号
        entity.setWaybillNo(request.getBillNo());
        // 金额
        entity.setAmount(request.getClaimMoney());

        // 客户编码、客户名称、
        String customerCode = request.getCustNo();
        entity.setCustomerCode(customerCode);
        
        //理赔类型不能为空 268217
        if(StringUtils.isEmpty(request.getClaimType())) {
     	   //ESB传递的理赔方式为空
     	   throw new SettlementException("理赔类型不能为空");
        }
        //理赔类型
        entity.setClaimType(request.getClaimType());
        // 根据客户编码查询月结等正式客户
        CustomerDto customerDtoTemp = new CustomerDto();
        customerDtoTemp.setCusCode(customerCode);
        List<CustomerEntity> customerEntityList = customerService.queryCustomers(customerDtoTemp, Integer.MAX_VALUE,0);

        // 客户按修改时间排序，并获取最新的时间的数据
        ListComparator orderComparator = new ListComparator("createDate");
        CustomerEntity customerDto = null;
        if (!CollectionUtils.isEmpty(customerEntityList)) {
            customerDto = validaCustomer(customerEntityList, orderComparator,
					customerDto);

        }

        // 根据客户编码查询散客信息
        CustomerQueryConditionDto queryCondition = new CustomerQueryConditionDto();
        queryCondition.setCustCode(customerCode);// 设置客户编码查询条件
        queryCondition.setExactQuery(true);// 设置是否查询全公司客户信息

        // 获得散客信息
        NonfixedCustomerEntity nonfixedCustDto = null;
        List<NonfixedCustomerEntity> rtnNonfixedCustList = nonfixedCustomerService
                .queryCustomerByConditionAll(queryCondition);
        if (!CollectionUtils.isEmpty(rtnNonfixedCustList)) {
            nonfixedCustDto = validaNonFixed(orderComparator, nonfixedCustDto,
					rtnNonfixedCustList);

        }

        // 如果客户和散客结果均为空，抛出异常
        if (customerDto == null && nonfixedCustDto == null) {
            throw new SettlementException("编码为:" + customerCode
                    + "的客户信息在系统中不存在");
        }
        if (customerDto != null) {
            entity.setCustomerName(customerDto.getName());
        } else if (nonfixedCustDto != null) {

            entity.setCustomerName(nonfixedCustDto.getCustName());
        }

        // 理赔应付部门编码
        entity.setPayableOrgCode(billPayable.getPayableOrgCode());
        // 理赔应付部门名称
        entity.setPayableOrgName(billPayable.getPayableOrgName());
        //ddw
        validaGenerate(request, billPayable, waybill, entity);

        return entity;

    }

	private NonfixedCustomerEntity validaNonFixed(
			ListComparator orderComparator,
			NonfixedCustomerEntity nonfixedCustDto,
			List<NonfixedCustomerEntity> rtnNonfixedCustList) {
		if (rtnNonfixedCustList.size() > 1) {
		    Collections.sort(rtnNonfixedCustList, orderComparator);
		    NonfixedCustomerEntity nonfixedCustTemp = rtnNonfixedCustList
		            .get(rtnNonfixedCustList.size() - 1);
		    if (!FossConstants.ACTIVE
		            .equals(nonfixedCustTemp.getIsDelete())) {
		        nonfixedCustDto = nonfixedCustTemp;
		    }
		} else {
		    if (!FossConstants.ACTIVE.equals(rtnNonfixedCustList.get(0)
		            .getIsDelete())) {
		        nonfixedCustDto = rtnNonfixedCustList.get(0);
		    }
		}
		return nonfixedCustDto;
	}

	private void validaGenerate(ClaimsPayBillGenerateRes request,
			BillPayableEntity billPayable, WaybillEntity waybill,
			BillClaimEntity entity) {
		OrgAdministrativeInfoEntity orgAdmin = orgAdministrativeInfoService.queryOrgAdministrativeInfoByUnifiedCode(request.getDeptNo());
        if(null != orgAdmin){
            //设置理赔申请部门类型
            if(StringUtils.equals(orgAdmin.getCode(),waybill.getReceiveOrgCode())){
                entity.setApplyOrgType(SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_ORIG);
            } else if(StringUtils.equals(orgAdmin.getCode(),waybill.getLastLoadOrgCode())) {
                entity.setApplyOrgType(SettlementDictionaryConstants.CLAIM_APPLY_ORG_TYPE_DEST);
            }
        }else{
            throw new SettlementException("标杆编码为:" + request.getDeptNo() + "的组织信息在系统中不存在");
        }

        // 支付类别
        String paymentCategory = request.getPaymentCategory();
        // 现金
        if (SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__CASH
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__CASH);
            // 电汇
        } else if (SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER);
            // 核销
        } else if (SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF);
            // 核销后现金
        } else if (SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF_CASH
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF_CASH);
            // 核销后电汇
        } else if (SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_CLAIM__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER);
        }

        // 类型（理赔、服务补救、退运费）
        String businessType = request.getBusinessType();
        String billType = null;
        // 理赔
        if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__CLAIM
                .equals(businessType)) {
            // 理赔
            billType = SettlementDictionaryConstants.BILL_CLAIM__TYPE__CLAIM;
        }

        // 退运费
        else if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__REFUND
                .equals(businessType)) {
            // 退运费
            billType = SettlementDictionaryConstants.BILL_CLAIM__TYPE__REFUND;
        }

        // 服务补救
        else if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__COMPENSATION
                .equals(businessType)) {
            // 服务补救
            billType = SettlementDictionaryConstants.BILL_CLAIM__TYPE__COMPENSATION;
        }
        // 赔付类型（理赔、服务补救、退运费）
        entity.setType(billType);


        // 设置创建时间,修改时间 同理赔应付单的业务日期
        entity.setCreateTime(billPayable.getBusinessDate());
        // 修改时间
        entity.setModifyTime(billPayable.getBusinessDate());

        // 有效
        entity.setActive(FossConstants.ACTIVE);

        // 发送状态
        entity.setStatus(SettlementDictionaryConstants.BILL_CLAIM__RETURN__STATUS__NOT_RETURN);

        /*
         * 最晚付款时间（CRM理赔审批工作流审批同意时间 + 24h）
         */
        entity.setPaymentTimeLimit(request.getPayBillLastTime());
	}

	private CustomerEntity validaCustomer(
			List<CustomerEntity> customerEntityList,
			ListComparator orderComparator, CustomerEntity customerDto) {
		if (customerEntityList.size() > 1) {
		    Collections.sort(customerEntityList, orderComparator);
		    CustomerEntity customerEntityTemp = customerEntityList
		            .get(customerEntityList.size() - 1);
		    if (!FossConstants.ACTIVE.equals(customerEntityTemp
		            .getIsDelete())) {
		        customerDto = customerEntityTemp;
		    }
		} else {
		    if (!FossConstants.ACTIVE.equals(customerEntityList.get(0)
		            .getIsDelete())) {
		        customerDto = customerEntityList.get(0);
		    }
		}
		return customerDto;
	}

    /**
     * 构建理赔应付单实体.
     *
     * @param request the request
     * @return the bill payable entity
     * @author 046644-foss-zengbinwen
     * @date 2012-11-20 下午2:38:16
     */
    private BillPayableEntity buildBillPayableEntity(
            ClaimsPayBillGenerateRes request,WaybillEntity waybill) {

        BillPayableEntity entity = new BillPayableEntity();
        // 获取businessType属性的值。
        String businessType = request.getBusinessType();

        // ID、应付单号、运单号、运单ID、生成方式
        entity.setId(UUIDUtils.getUUID());

        SettlementNoRuleEnum ruleEnum = null;
        String billType = null;

        // 支付类别
        // 设置支付类别和是否可核销
        String paymentCategory = request.getPaymentCategory();

        // 现金
        if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__CASH
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__CASH);
            entity.setIsWriteoff(FossConstants.NO);
            // 电汇
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER);
            entity.setIsWriteoff(FossConstants.NO);
            // 核销
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF);
            entity.setIsWriteoff(FossConstants.YES);
            // 核销后电汇
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_TELEGRAPH_TRANSFER);
            entity.setIsWriteoff(FossConstants.YES);
            // 核销后现金
        } else if (SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_CASH
                .equals(paymentCategory)) {
            entity.setPaymentCategories(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__WRITEOFF_CASH);
            entity.setIsWriteoff(FossConstants.YES);
        }

        // 理赔
        if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__CLAIM
                .equals(businessType)) {
            ruleEnum = SettlementNoRuleEnum.YF3;
            billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM;
        }

        // 退运费
        else if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__REFUND
                .equals(businessType)) {
            ruleEnum = SettlementNoRuleEnum.YF4;
            billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND;
        }

        // 服务补救
        else if (SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__COMPENSATION
                .equals(businessType)) {
            ruleEnum = SettlementNoRuleEnum.YF5;
            billType = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION;
        }

        Date now = new Date();
        // 获取结算单据编号
        String payableNo = settlementCommonService.getSettlementNoRule(ruleEnum);
        // 设置payableNo
        entity.setPayableNo(payableNo);
        // 设置WaybillNo
        entity.setWaybillNo(request.getBillNo());
        
        if(StringUtil.isBlank(request.getClaimWay())){
        	throw new SettlementException("理赔方式不能为空！");
        }
        //理赔类型
        entity.setClaimWay(request.getClaimWay());
        
      //理赔类型不能为空 268217
       if(StringUtils.isEmpty(request.getClaimType())) {
    	   //ESB传递的理赔方式为空
    	   throw new SettlementException("理赔类型不能为空");
       }

        // 运单ID、始发部门CODE、到达部门CODE、产品类型、创建方式
        entity.setWaybillId(waybill.getId());
        // 始发部门CODE
        entity.setOrigOrgCode(waybill.getReceiveOrgCode());
        // 到达部门CODE
        entity.setDestOrgCode(waybill.getLastLoadOrgCode());

        //如果始发部门编码存在
        if(!StringUtils.isEmpty(waybill.getReceiveOrgCode())){
            String origOrgName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(waybill.getReceiveOrgCode());
            entity.setOrigOrgName(origOrgName);
        }

        OrgAdministrativeInfoEntity destOrgEntity = null;
        //如果到达部门编码存在
        if(!StringUtils.isEmpty(waybill.getLastLoadOrgCode())){
            destOrgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(waybill.getLastLoadOrgCode());
            if(null != destOrgEntity){
                entity.setDestOrgName(destOrgEntity.getName());
            }else{
                throw new SettlementException("部门编码为:" + waybill.getLastLoadOrgCode() + "的组织信息在系统中不存在");
            }
        }
        
        /*
         * 合伙人二级网点理赔结算需求:理赔应付单中出发部门（ORIG_ORG_NAME）及编码（ORIG_ORG_CODE）
         * /到达部门（DEST_ORG_NAME）及编码（DEST_ORG_CODE）/应付部门PAYABLE_ORG_CODE）及编码（PAYABLE_ORG_CODE）
         * 映射成对应的一级网点部门名称和编码，若出发部门/到达部门/应付部门中有非二级网点部门，则该字段不用映射
         * 
         * modify by 269044-zhurongrong 2016-11-29
         */
        //根据始发部门编码查询营业部实体
        SaleDepartmentEntity pOrigOrgEntity = saleDepartmentService
        		.querySimpleSaleDepartmentByCode(waybill.getReceiveOrgCode());
        //是二级网点
        if(null != pOrigOrgEntity && FossConstants.YES.equals(pOrigOrgEntity.getIsTwoLevelNetwork())){
        	//根据二级网点查询一级网点
        	SaleDepartmentEntity origSaleDept = partnerRelationService.oneEntityByTwoCode(waybill.getReceiveOrgCode());
        	//不为空
        	if(origSaleDept != null) {
        		//设置出发部门编码
        		entity.setOrigOrgCode(origSaleDept.getCode());
        		//设置出发部门名称
        		entity.setOrigOrgName(origSaleDept.getName());
        	}
        }
        
        //根据到达部门编码查询营业部实体
        SaleDepartmentEntity pDestOrgEntity = saleDepartmentService
        		.querySimpleSaleDepartmentByCode(waybill.getLastLoadOrgCode());
        //是二级网点
        if(null != pDestOrgEntity && FossConstants.YES.equals(pDestOrgEntity.getIsTwoLevelNetwork())){
        	//根据二级网点查询一级网点
        	SaleDepartmentEntity destSaleDept = partnerRelationService.oneEntityByTwoCode(waybill.getLastLoadOrgCode());
        	//不为空
        	if(destSaleDept != null) {
        		//设置到达部门编码
        		entity.setDestOrgCode(destSaleDept.getCode());
        		//设置到达部门名称
        		entity.setDestOrgName(destSaleDept.getName());
        	}
        }

        // 产品类型
        entity.setProductCode(waybill.getProductCode());
        // 创建方式
        entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

        // 单据类型、源单据编号、源单据类型、应付部门编码、应付部门名称
        entity.setBillType(billType);
        //源单据编号
        entity.setSourceBillNo(request.getBillNo());
        //源单据类型
        entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL);

		/*
		 * 设置发票标记与理赔/服务补救/退运费对应运单保持一致
		 * 
		 * 杨书硕 2013-11-8 下午3:10:26
		 */
        ActualFreightEntity actualFreightEntity = null;
        try{
            actualFreightEntity =  waybillManagerService.queryWaybillByNo(waybill.getWaybillNo()).getActualFreightEntity();
            if(null == actualFreightEntity){
                throw new SettlementException("获取运单信息异常！");
            }
            String invoiceMark = actualFreightEntity.getInvoice().toString();
            if(StringUtil.isBlank(invoiceMark)){
                throw new SettlementException("发票标记字段不能为空");
            } else if(!SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE.equals(invoiceMark)
                    &&!SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO.equals(invoiceMark)){
                throw new SettlementException("发票标记字段值非法，只能为 "
                        + SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_ONE
                        + "或者" + SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
            } else{
                entity.setInvoiceMark(invoiceMark);
            }
        } catch(Exception e){
            throw new SettlementException("查询运单发票标记接口（接送货模块）发生异常，信息："+e.getMessage());
        }

        String payableOrgUnifiedCode = request.getDeptNo();
        /**
         * 统一结算需求改动
         * 根据运单上的部门判断始发还是到达部门申请，确定统一结算标记
         * 非服务补救
         */
        if(!SettlementESBDictionaryConstants.CRM__BUSINESS_TYPE__COMPENSATION.equals(businessType)) {
            //应付部门标杆编码
            //获取传入的理赔部门信息
            OrgAdministrativeInfoEntity claimOrg = orgAdministrativeInfoService
                    .queryOrgAdministrativeInfoByUnifiedCode(request.getDeptNo());
            if (claimOrg == null) {
                throw new SettlementException("标杆编码为:" + request.getDeptNo() + "的组织信息在系统中不存在");
            }

            //判断是否始发统一结算
            if (StringUtil.equals(waybill.getReceiveOrgCode(), claimOrg.getCode())
                    && StringUtils.equals(actualFreightEntity.getStartCentralizedSettlement(), FossConstants.YES)) {
                payableOrgUnifiedCode = actualFreightEntity.getStartContractOrgCode();
                entity.setUnifiedSettlement(FossConstants.YES);
            } else if (StringUtils.equals(actualFreightEntity.getArriveCentralizedSettlement(), FossConstants.YES)) {
                payableOrgUnifiedCode = actualFreightEntity.getArriveContractOrgCode();
                entity.setUnifiedSettlement(FossConstants.YES);
            }
        } else {
            entity.setUnifiedSettlement(FossConstants.NO);
        }


        OrgAdministrativeInfoEntity orgAdmin = orgAdministrativeInfoService
                .queryOrgAdministrativeInfoByUnifiedCode(payableOrgUnifiedCode);
        if (orgAdmin == null) {
            throw new SettlementException("标杆编码为:" + payableOrgUnifiedCode + "的组织信息在系统中不存在");
        }
        entity.setPayableOrgCode(orgAdmin.getCode());
        entity.setPayableOrgName(orgAdmin.getName());

        // 公司编码、公司名称
        entity.setPayableComCode(orgAdmin.getSubsidiaryCode());
        entity.setPayableComName(orgAdmin.getSubsidiaryName());
        //ddw
        if(FossConstants.YES.equals(entity.getUnifiedSettlement())){
            entity.setContractOrgCode(orgAdmin.getCode());
            entity.setContractOrgName(orgAdmin.getName());
        }
        
      //根据应付部门编码查询营业部实体
        SaleDepartmentEntity payableOrgEntity = saleDepartmentService
        		.querySimpleSaleDepartmentByCode(entity.getPayableOrgCode());
        //是二级网点
        if(null != payableOrgEntity && FossConstants.YES.equals(payableOrgEntity.getIsTwoLevelNetwork())) {
        	//根据二级网点查询一级网点
        	SaleDepartmentEntity paySaleDept = partnerRelationService.oneEntityByTwoCode(entity.getPayableOrgCode());
        	//不为空
        	if(paySaleDept != null) {
        		//设置到达部门编码
        		entity.setPayableOrgCode(paySaleDept.getCode());
        		//设置到达部门名称
        		entity.setPayableOrgName(paySaleDept.getName());
        	}
        	if(FossConstants.YES.equals(entity.getUnifiedSettlement())) {
                entity.setContractOrgCode(paySaleDept.getCode());
                entity.setContractOrgName(paySaleDept.getName());
            }
        }

        // 客户编码、客户名称、
        String customerCode = request.getCustNo();
        entity.setCustomerCode(customerCode);
        //客户联系电话
        entity.setCustomerPhone(request.getCustomerPhone());

        // 根据客户编码查询月结等正式客户
        CustomerDto customerDtoTemp = new CustomerDto();
        customerDtoTemp.setCusCode(customerCode);
        List<CustomerEntity> customerEntityList = customerService.queryCustomers(customerDtoTemp, Integer.MAX_VALUE,0);

        //客户按修改时间排序，并获取最新的时间的数据
        ListComparator orderComparator = new ListComparator("createDate");
        CustomerEntity customerDto = null;
        if(!CollectionUtils.isEmpty(customerEntityList)){
            customerDto = validaCustomer(customerEntityList, orderComparator,
					customerDto);

        }

        // 根据客户编码查询散客信息
        CustomerQueryConditionDto queryCondition = new CustomerQueryConditionDto();
        queryCondition.setCustCode(customerCode);// 设置客户编码查询条件
        queryCondition.setExactQuery(true);// 设置是否查询全公司客户信息

        // 获得散客信息
        NonfixedCustomerEntity nonfixedCustDto = null;
        List<NonfixedCustomerEntity> rtnNonfixedCustList = nonfixedCustomerService.queryCustomerByConditionAll(queryCondition);
        if(!CollectionUtils.isEmpty(rtnNonfixedCustList)){
            nonfixedCustDto = validaNonFixed(orderComparator, nonfixedCustDto,
					rtnNonfixedCustList);

        }

        // 如果客户和散客结果均为空，抛出异常
        if (customerDto == null && nonfixedCustDto == null) {
            throw new SettlementException("编码为:" + customerCode
                    + "的客户信息在系统中不存在");
        }
        if(customerDto!=null){
            entity.setCustomerName(customerDto.getName());
        }else if(nonfixedCustDto!=null){

            entity.setCustomerName(nonfixedCustDto.getCustName());
        }


        //金额、已核销金额、未核销金额
        entity.setAmount(request.getClaimMoney());
        entity.setVerifyAmount(BigDecimal.ZERO);
        entity.setUnverifyAmount(entity.getAmount());

        // 币种、会计日期、创建人、创建部门
        entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
        // 会计日期
        entity.setAccountDate(now);
        entity.setBusinessDate(entity.getAccountDate());
        // 创建人、创建部门
        entity.setCreateUserCode(request.getCreatorNo());

        // 是否有效、是否红单、是否初始化、版本号、创建时间、生效状态、支付状态、冻结状态、审核状态
        entity.setActive(FossConstants.ACTIVE);
        // 是否红单
        entity.setIsRedBack(FossConstants.NO);
        // 是否初始化
        entity.setIsInit(FossConstants.NO);
        // 版本号
        entity.setVersionNo(FossConstants.INIT_VERSION_NO);
        // 创建时间
        entity.setCreateTime(now);
        // 生效状态  268217
        if(StringUtils.isNotEmpty(request.getClaimType()) && "3".equals(request.getClaimType())){
        	entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);
        }else{
        	entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
        }
        // 支付状态
        entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
        // 冻结状态
        entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
        // 审核状态
        entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);

        // 银行账号信息，可以为空
        BankPayInfo bank = request.getBankPayInfo();
        if (bank != null) {

            // 开户名、银行名称、银行编码、银行账号
            entity.setPayeeName(bank.getAccountName());
            entity.setBankHqName(bank.getBankName());
            entity.setBankHqCode(bank.getBankCode());
            entity.setAccountNo(bank.getAccountNumber());

            // 省份编码、省份名称、城市编码、城市名称
            entity.setProvinceCode(bank.getBankProvinceCode());
            entity.setProvinceName(bank.getBankProvinceName());
            entity.setCityCode(bank.getBankCityCode());
            entity.setCityName(bank.getBankCityName());

            // 支行编码、支行名称
            entity.setBankBranchCode(bank.getSubbranchCode());
            entity.setBankBranchName(bank.getSubbranchName());

            //账户类型
            entity.setAccountType(bank.getAccountProperty());

        }
        //判断如果为包裹，需要将快递点部映射过来
        if(SettlementUtil.isPackageProductCode(entity.getProductCode())){
            //获取出发部门快递点部
            ExpressPartSalesDeptResultDto origOrg = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(entity.getOrigOrgCode(),now);
            if (destOrgEntity.getExpressSalesDepartment() != null
                    && destOrgEntity.getTransferCenter() != null) {
                if (!destOrgEntity.getExpressSalesDepartment().equals(FossConstants.YES)
                        && !destOrgEntity.getTransferCenter().equals(FossConstants.YES)) {
                    // 获取到达部门快递点部
                    ExpressPartSalesDeptResultDto destOrg = expressPartSalesDeptService
                            .queryExpressPartSalesDeptBySalesCodeAndTime(
                                    entity.getDestOrgCode(), now);
                    // 判断是否为空
                    if (destOrg == null) {
                        throw new SettlementException(
                                "开单为德邦快递，其最终配载部门对应的快递点部不存在！");
                        // 如果快递点部存在，则获取快递点部门
                    } else if (!StringUtils.isEmpty(destOrg.getPartCode())) {
                        entity.setExpressDestOrgCode(destOrg.getPartCode());
                        entity.setExpressDestOrgName(destOrg.getPartName());
                        // 如果快递点部不存在，且不是试点城市，则获取对应到达部门
                    } else {
                        entity.setExpressDestOrgCode(entity.getDestOrgCode());
                        entity.setExpressDestOrgName(entity.getDestOrgName());
                    }
                } else {
                    entity.setExpressDestOrgCode(entity.getDestOrgCode());
                    entity.setExpressDestOrgName(entity.getDestOrgName());
                }
            } else if (destOrgEntity.getExpressSalesDepartment() == null
                    && destOrgEntity.getTransferCenter() == null) {
                // 获取到达部门快递点部
                ExpressPartSalesDeptResultDto destOrg = expressPartSalesDeptService
                        .queryExpressPartSalesDeptBySalesCodeAndTime(
                                entity.getDestOrgCode(), now);
                // 判断是否为空
                if (destOrg == null) {
                    throw new SettlementException("开单为德邦快递，其最终配载部门对应的快递点部不存在！");
                    // 如果快递点部存在，则获取快递点部门
                } else if (!StringUtils.isEmpty(destOrg.getPartCode())) {
                    entity.setExpressDestOrgCode(destOrg.getPartCode());
                    entity.setExpressDestOrgName(destOrg.getPartName());
                    // 如果快递点部不存在，且不是试点城市，则获取对应到达部门
                } else {
                    entity.setExpressDestOrgCode(entity.getDestOrgCode());
                    entity.setExpressDestOrgName(entity.getDestOrgName());
                }
            } else {
                entity.setExpressDestOrgCode(entity.getDestOrgCode());
                entity.setExpressDestOrgName(entity.getDestOrgName());
            }
            //判断是否为空
            if(origOrg==null){
                throw new SettlementException("运单出发部门映射的快递点部为空！");
                //如果快递点部存在，则获取快递点部门
            }else if(!StringUtils.isEmpty(origOrg.getPartCode())){
                entity.setExpressOrigOrgCode(origOrg.getPartCode());
                entity.setExpressOrigOrgName(origOrg.getPartName());
                //如果快递点部不存在，且不是试点城市，则获取对应出发部门
            }else{
                entity.setExpressOrigOrgCode(entity.getOrigOrgCode());
                entity.setExpressOrigOrgName(entity.getOrigOrgName());
            }
        }

        return entity;
    }
    
    /**
     * DN201511200014-理赔付款优化（零担在线理赔流程优化）
     * @author 269044
     * @date 2016-01-19
     * @description 当应付单管理中存在由CRM对接过来的在线理赔的应付单时系统自动发起付款，对接报账平台生成FOSS理赔—付款工作流
     */
    @Transactional
    public void crmBillPayableToFSSC(ClaimsPayBillGenerateRes request,CurrentInfo currentInfo) {
    	LOGGER.info("在线理赔应付单付款开始,理赔类型是：" + request.getClaimWay());
    	//获取对接系统参数
		DataDictionaryEntity dictEntity = DictUtil.getDataByTermsCode("SETTLEMENT__PAYTOSYSTEM_TYPE");
		LOGGER.info("获取对接系统参数成功");
		List<DataDictionaryValueEntity> dataList = dictEntity.getDataDictionaryValueEntityList();
		//如果对接系统数据字典没配置，则抛出异常
		if(CollectionUtils.isEmpty(dataList)){
			throw new SettlementException("FOSS付款的付款工作流对接系统类型数据字典没配置，请去数据字典进行配置！");
		}
		//对接系统必须配置，且必须是1条 Y--财务共享，N--代表费控
		if(dataList.size()!=1){
			throw new SettlementException("付款工作流对接系统类型数据字典配置有误，必须只有1条。其中值为Y代表财务共享，N代表费控！");
		}
		
		//付款对接系统  Y--财务共享，N--代表费控 
		String payToSystem = dataList.get(0).getValueCode();
		LOGGER.info("付款对接系统" + payToSystem);
		//判空
		if(StringUtils.isEmpty(payToSystem)){
			throw new SettlementException("付款工作流对接系统类型数据字典配置有误，不能为空！值必须为Y或N。其中值为Y代表财务共享，N代表费控！");
		}
		
		// 校验存在是否重复的应付单（理赔、退运费、服务补救）
        BillPayableManageResultDto resultDto = validaPayBill(request,
				currentInfo);
		
		//进行付款的操作
		List<BillPaymentAddDto> addDtoList = resultDto.getAddDtoList();
		BillPaymentEntity paymentEntity = resultDto.getPaymentEntity();
		//设置付款单的来源单据类型为YF
		paymentEntity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		//设置付款单的是否增值税专用发票为否
		paymentEntity.setIsTaxinvoice(false);
		if(CollectionUtils.isEmpty(addDtoList)) {
			throw new SettlementException("在线理赔付款失败，录入的付款单Dto为空");
		}
		LOGGER.info("CRM在线理赔直接付款对接到FSSC生成工作流操作开始");
		CurrentInfo cInfo = getCurrentInfo(currentInfo,request);
		String paymentNo = billPaymentPayService.addBillPaymentInfo(paymentEntity, addDtoList, cInfo);
		LOGGER.info("CRM在线理赔直接付款对接到FSSC生成工作流操作结束" + paymentNo);	
    }

	private BillPayableManageResultDto validaPayBill(
			ClaimsPayBillGenerateRes request, CurrentInfo currentInfo) {
		List<String> billTypeList = new ArrayList<String>();
        // 理赔
        billTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM);
        // 退运费
        billTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__REFUND);
        // 服务补救
        billTypeList.add(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION);
		// 根据运单号集合和单据类型集合查询应付单信息
        List<BillPayableEntity> list = billPayableService.queryByWaybillNosAndByBillTypes(Arrays.asList(request.getBillNo()), billTypeList);
        LOGGER.info("应付单集合的长度为：" + list.size());
        if (CollectionUtils.isEmpty(list)) {
            throw new SettlementException("在线理赔付款失败，不存在有效的理赔应付单");
        }
        BillPayableEntity billPayable= list.get(0);
		
		//获取应付单号集合
		if(StringUtils.isEmpty(billPayable.getPayableNo())){
			throw new SettlementException("在线理赔付款失败，应付单号为空，不能进行在线付款操作！");
		}
		//String[] payableNos = null;
		String payableNos[] = {billPayable.getPayableNo()};
		//创建应付单Action实体
		BillPayableManageDto billPayableManageDto = new BillPayableManageDto();
		//写入应付单单号数据
		billPayableManageDto.setBillNos(payableNos);
		//设置是否包含外请车为否
		billPayableManageDto.setContainRentCar("N");
		LOGGER.info("录入付款单操作开始，应付单号长度为：" + payableNos.length);
		//调用service进行付款caozuo
		BillPayableManageResultDto resultDto = billPayableQueryManageservice.payForBillPayable(billPayableManageDto);
		LOGGER.info("录入付款单操作结束");
		//获取付款批次号
		String batchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.FK_BN);
		resultDto.getPaymentEntity().setBatchNo(batchNo);
		//操作者编码--CRM传来的参数中有创建人工号的字段，需要传给FSSC生成工作流用
		if(request.getCreatorNo() == null) {
			throw new SettlementException("在线理赔付款失败，CRM传来的参数中创建人为空，无法付款");
		}
		resultDto.getPaymentEntity().setCreateUserCode(request.getCreatorNo());
		//操作者名称--CRM传来的参数中没有此字段，使用currentInfo中的empName
		resultDto.getPaymentEntity().setCreateUserName(currentInfo.getEmpName());
		
		//bug--crm传来的电话号码中只有零担的有电话号码，快递的没有，因此电话号码为空的情况下需要从运单里重新获取电话号码
		//设置付款单的客户联系电汇
		if(StringUtils.isEmpty(billPayable.getCustomerPhone())) {
			validaClaims(request, resultDto);				
		} else {
			//零担运单的电话直接设置
			resultDto.getPaymentEntity().setMobilePhone(billPayable.getCustomerPhone());
		}
		//设置付款单的付款方式为电汇
		resultDto.getPaymentEntity().setPaymentType(SettlementDictionaryConstants.BILL_PAYABLE__PAYMENT_CATEGORIES__TELEGRAPH_TRANSFER);
		//设置发票抬头为应付单的子公司
		if(StringUtils.isEmpty(billPayable.getPayableComCode()) || StringUtils.isEmpty(billPayable.getPayableComName())) {
			throw new SettlementException("在线理赔付款失败，生成的理赔应付单的子公司编码或者子公司名称为空");
		}
		resultDto.getPaymentEntity().setInvoiceHeadCode(billPayable.getPayableComCode());
		resultDto.getPaymentEntity().setInvoiceHeadName(billPayable.getPayableComName());
		return resultDto;
	}

	private void validaClaims(ClaimsPayBillGenerateRes request,
			BillPayableManageResultDto resultDto) {
		// 根据运单号查询运单
		WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(request.getBillNo());
		// 获取crm参数中的客户编码
		String crmCustomerCode = request.getCustNo();
		// 根据request中客户编码，如果是收货客户
		if(StringUtils.isNotEmpty(waybill.getReceiveCustomerCode()) && crmCustomerCode.equals(waybill.getReceiveCustomerCode())) {	        	
			// 判断收货客户编码不为空
			if(StringUtils.isEmpty(waybill.getReceiveCustomerMobilephone())) {
				// 抛出异常
				throw new SettlementException("在线理赔付款失败，客户没有对应的联系方式，请到FOSS系统手动点击付款");
			} else {
				// 则设置收货客户手机
		    	resultDto.getPaymentEntity().setMobilePhone(waybill.getReceiveCustomerMobilephone());
			}
		} // 若与发货客户编码一样
		else if (StringUtils.isNotEmpty(waybill.getDeliveryCustomerCode()) && crmCustomerCode.equals(waybill.getDeliveryCustomerCode())){        		        	
			// 判断发货客户编码不为空
			if(StringUtils.isEmpty(waybill.getDeliveryCustomerMobilephone())) {
				// 抛出发货客户编码为空的异常
				throw new SettlementException("在线理赔付款失败，客户没有对应的联系方式，请到FOSS系统手动点击付款");
			} else {
				// 则设置发货客户手机
		    	resultDto.getPaymentEntity().setMobilePhone(waybill.getDeliveryCustomerMobilephone());
			}
		} else {
			// 客户编码跟运单中都没有匹配，则抛出异常
			throw new SettlementException("在线理赔付款失败，客户没有对应的联系方式，请到FOSS系统手动点击付款");
		}
	}
    	
    public CurrentInfo getCurrentInfo(CurrentInfo currentInfo,ClaimsPayBillGenerateRes request) {
    	// 用户信息
    	UserEntity user = new UserEntity();
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmpCode(request.getCreatorNo());
		employee.setEmpName(SettlementConstants.CRM_NAME);
		user.setEmployee(employee);
		user.setUserName(SettlementConstants.CRM_NAME);

		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(null);
		dept.setName(null);

		CurrentInfo cf = new CurrentInfo(user, dept);
		return cf;
	}

	
    /**
     * @GET
     * @return expressPartSalesDeptService
     */
    public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		/*
		 *@get
		 *@ return expressPartSalesDeptService
		 */
        return expressPartSalesDeptService;
    }

    /**
     * @SET
     * @param expressPartSalesDeptService
     */
    public void setExpressPartSalesDeptService(
            IExpressPartSalesDeptService expressPartSalesDeptService) {
		/*
		 *@set
		 *@this.expressPartSalesDeptService = expressPartSalesDeptService
		 */
        this.expressPartSalesDeptService = expressPartSalesDeptService;
    }
    
    /**
	 * @SET
	 * @param billPayableQueryManageservice
	 */
	public void setBillPayableQueryManageservice(
			IBillPayableQueryManageservice billPayableQueryManageservice) {
		/*
		 *@set
		 *@this.billPayableQueryManageservice = billPayableQueryManageservice
		 */
		this.billPayableQueryManageservice = billPayableQueryManageservice;
	}
	
	public void setBillPaymentPayService(IBillPaymentPayService billPaymentPayService) {
		this.billPaymentPayService = billPaymentPayService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setPartnerRelationService(
			IPartnerRelationService partnerRelationService) {
		this.partnerRelationService = partnerRelationService;
	}
	
}
