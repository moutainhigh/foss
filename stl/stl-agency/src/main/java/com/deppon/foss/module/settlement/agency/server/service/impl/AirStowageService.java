package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirStowageService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.transfer.airfreight.api.define.AirfreightConstants;
import com.deppon.foss.module.transfer.airfreight.api.server.dao.IAirWaybillDetailDao;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirWaybillEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 空运配载单服务
 * @author ibm-zhuwei
 * @date 2012-11-21 上午11:33:04
 */
public class AirStowageService implements IAirStowageService {

	private static final Logger LOGGER = LogManager.getLogger(AirStowageService.class);

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
	 * 空运代理公司服务
	 */
	private IAirAgencyCompanyService airAgencyCompanyService;
	
	/**
	 * 接送货运单服务
	 */
	private IWaybillManagerService WaybillManagerService;
	
	/** 查询航空正单dao. */
	private IAirWaybillDetailDao pointsSingleJointTicketDao;
	
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;

	/**
	 * 新增空运配载单
	 * 
	 * 注意：
	 * ISSUE-3305 中转在新增空运配载单，金额为0的时候，不调用此接口
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:04
	 */
	@Override
	public void addAirStowage(AirWaybillEntity airWaybill, CurrentInfo currentInfo) {
		
		LOGGER.info("开始新增空运配载单：" + airWaybill.getAirWaybillNo());
		
		// 验证空运配载单输入参数合法性
		this.validAirWaybillParam(airWaybill);
		
		// 处理新增空运配载单
		this.handleAddedAirWaybill(airWaybill, currentInfo);
		
		LOGGER.info("结束新增空运配载单：" + airWaybill.getAirWaybillNo());
	}

	/**
	 * 作废空运配载单
	 * @author ibm-zhuwei
	 * @date 2012-11-22 下午3:25:05
	 */
	@Override
	public void cancelAirStowage(String airWaybillNo, CurrentInfo currentInfo) {
		
		LOGGER.info("开始更改空运配载单：" + airWaybillNo);
		
		// 查询应付单
		List<BillPayableEntity> billPayables = billPayableService.queryBySourceBillNOs(
				Arrays.asList(airWaybillNo), 
				SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL, 
				FossConstants.ACTIVE);

		// 校验应付单合法性
		billPayableService.validateWaybillForBillPayable(billPayables);

		// 校验应付单
		if (CollectionUtils.isNotEmpty(billPayables)) {
			
			for (BillPayableEntity e : billPayables) {
				//对应的应付单已经审核，不能进行后续操作
				if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(e.getApproveStatus())) {
					throw new SettlementException("对应的应付单已经审核，不能进行后续操作");
				}
				//对应的应付单已经冻结，不能进行后续操作
				if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN.equals(e.getFrozenStatus())) {
					throw new SettlementException("对应的应付单已经冻结，不能进行后续操作");
				}
				//航空成本应付单已支付，不能进行后续操作
				if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(e.getPayStatus())){
					throw new SettlementException("航空成本应付单已支付，不能进行后续操作");
				}
				//对应的应付单已经核销，不能进行后续操作
				if (e.getUnverifyAmount().compareTo(e.getAmount()) < 0) {
					throw new SettlementException("对应的应付单已经核销，不能进行后续操作");
				}
			}
		}
		
		// 红冲应付单
		if (CollectionUtils.isNotEmpty(billPayables)) {
			for (BillPayableEntity entity : billPayables) {
				billPayableService.writeBackBillPayable(entity, currentInfo);
			}
		}
		
		LOGGER.info("结束更改空运配载单：" + airWaybillNo);
	}

	/**
	 * 修改空运配载单
	 * 
	 * ① 原配载单总金额为0，改为大于0；新增空运成本应付单
	 * ② 原配在单总金额大于0，改为大于0,作废空运配载单再新增；改为0，作废空运配载单
	 * 
	 * @author 092036-foss-bochenlong
	 * @date 修改于2013-07-10 上午18:01:00
	 */
	@Override
	public void modifyAirStowage(AirWaybillEntity oldAirWaybill, AirWaybillEntity newAirWaybill, CurrentInfo currentInfo) {

		LOGGER.info("开始更改空运配载单：" + oldAirWaybill.getAirWaybillNo());
		
		// 作废空运配载单
		this.cancelAirStowage(oldAirWaybill.getAirWaybillNo(), currentInfo);
		
		// ISSUE-3305 092036-foss-bochenlong
		
		if(newAirWaybill.getFeeTotal().compareTo(BigDecimal.ZERO) > 0) {
			// 新增空运配载单
			this.validAirWaybillParam(newAirWaybill);
			// 处理空运配载单
			this.handleAddedAirWaybill(newAirWaybill, currentInfo);
		}
		

		LOGGER.info("结束更改空运配载单：" + oldAirWaybill.getAirWaybillNo());
		
	}


	/**
	 * 验证新增空运配载单合法性
	 * @author ibm-zhuwei
	 * @date 2012-11-5 上午10:58:44
	 */
	private void validAirWaybillParam(AirWaybillEntity airWaybill) {

		// 空运正单号为空
		if (StringUtils.isEmpty(airWaybill.getAirWaybillNo())) {
			throw new SettlementException("航空正单号不能为空");
		}
		
		// 制单部门为空
		if (StringUtils.isEmpty(airWaybill.getCreateOrgCode())) {
			throw new SettlementException("制单部门不能为空");
		}
		
		// 承运人为空
		if (StringUtils.isEmpty(airWaybill.getAgenctCode())) {
			throw new SettlementException("承运人不能为空");
		}
		
		// 总金额
		BigDecimal totalAmount = airWaybill.getFeeTotal();
		if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("总金额为空或者小于等于0");
		}
		
		// 制单时间为空
		if (airWaybill.getCreateTime() == null) {
			throw new SettlementException("制单时间为空");
		}
		
		// 配载类型
		if (StringUtils.isEmpty(airWaybill.getAirAssembleType())) {
			throw new SettlementException("配载类型为空");
		}
		
		//验证同一个航空正单号，是否已经存在空运成本应付单
	    List<BillPayableEntity> list=this.billPayableService.queryBySourceBillNOs
	    		(Arrays.asList(airWaybill.getAirWaybillNo()), 
	    				SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL, FossConstants.ACTIVE);
	    if(CollectionUtils.isNotEmpty(list)){
	    	throw new SettlementException("航空正单号为："+airWaybill.getAirWaybillNo()+"已经生成了空运成本应付单！");
	    }
	}
	
	private void handleAddedAirWaybill(AirWaybillEntity airWaybill,
			CurrentInfo currentInfo) {
		
		// 会计时间为分区键，开单时保证各个单据的时间保持一致
		Date now = new Date();
		
		BillPayableEntity entity = new BillPayableEntity();
		
		// ID,应付单号,单据子类型,运单号,运单ID,生成方式,付款方
		entity.setId(UUIDUtils.getUUID());
		if (airWaybill.getAirAssembleType().equals(AirfreightConstants.AIRFREIGHT_INDEPENDENTBILLING)
			|| airWaybill.getAirAssembleType().equals(AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNO)) {
			//设置应付单号
			entity.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF81));
			//设置应付类型
			entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR);
		} else if (airWaybill.getAirAssembleType().equals(AirfreightConstants.AIRFREIGHT_INDEPENDENTBILLINGOUTSOURCE)
			|| airWaybill.getAirAssembleType().equals(AirfreightConstants.AIRFREIGHT_TOTALBIGBILLNOOUTSOURCE)) {
			//设置应付单号
			entity.setPayableNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YF82));
			//设置单据类型
			entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_ORIGINAL);
		} else {
			throw new SettlementException("配载类型不合法");
		}
		//设置运单号
		validaPayableEntity(airWaybill, now, entity);
		
		billPayableService.addBillPayable(entity, currentInfo);
	}

	private void validaPayableEntity(AirWaybillEntity airWaybill, Date now,
			BillPayableEntity entity) {
		entity.setWaybillNo(null);
		//设置运单id
		entity.setWaybillId(null);
		//设置创建类型
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		//设置付款方类型
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__ORIGIN);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		//设置审批状态
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		//设置应付类型
		entity.setPayableType(null);

		// 来源单据编码，应付部门编码，应付部门名称
		entity.setSourceBillNo(airWaybill.getAirWaybillNo());
		//来源单据类型,
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_WAYBILL);
		//支付状态
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		// 应付部门：配载单的制单部门code
		entity.setPayableOrgCode(airWaybill.getCreateOrgCode());	
		// 应付部门：配载单的制单部门name
		entity.setPayableOrgName(airWaybill.getCreateOrgName());
		
		//根据编码查询相关信息
		OrgAdministrativeInfoEntity origOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(airWaybill.getCreateOrgCode());	
		if(origOrgEntity!=null){
			//应付子公司编码
			entity.setPayableComCode(origOrgEntity.getSubsidiaryCode());
			//应付子公司名称
			entity.setPayableComName(origOrgEntity.getSubsidiaryName());
		}
		// 设置出发部门编码
		entity.setOrigOrgCode(airWaybill.getCreateOrgCode());
		// 设置出发部门名称
		entity.setOrigOrgName(airWaybill.getCreateOrgName());

		// 到达部门编码
		entity.setDestOrgCode(airWaybill.getCreateOrgCode());	// 制单部门编码
		// 到达部门名称
		entity.setDestOrgName(airWaybill.getCreateOrgName());
		
		// 设置应付客户编码
		entity.setCustomerCode(airWaybill.getAgenctCode());
		// 设置应付客户名称
		entity.setCustomerName(airWaybill.getAgencyName());


		// 设置金额
		validaWaybill(airWaybill, now, entity);
	}

	private void validaWaybill(AirWaybillEntity airWaybill, Date now,
			BillPayableEntity entity) {
		entity.setAmount(airWaybill.getFeeTotal());
		// 设置已核销金额
		entity.setVerifyAmount(BigDecimal.ZERO);
		// 设置未核销金额
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 设置会计日期
		entity.setAccountDate(now);
		// 设置业务日期
		entity.setBusinessDate(airWaybill.getCreateTime());

		// 设置生效日期
		entity.setEffectiveDate(null);
		// 设置创建人code
		entity.setCreateUserCode(airWaybill.getCreateUserCode());
		// 设置创建人name
		entity.setCreateUserName(airWaybill.getCreateUserName());
		// 制单部门编码
		entity.setCreateOrgCode(airWaybill.getCreateOrgCode());
		// 制单部门名称
		entity.setCreateOrgName(airWaybill.getCreateOrgName());

		// 设置是否有效
		entity.setActive(FossConstants.YES);
		// 设置是否红单
		entity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 设置是否初始化
		entity.setIsInit(FossConstants.NO);
		// 设置版本号
		entity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 生效状态、付款状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);

        //处理空运的产品类型，如果存在没有运单的正单也可以保存 解决问题DEAP-144
		String productCode = null;
		//若foss中转传运输性质(快递系统只有包的情况下)，则不查运单表，若不传，则根据运单号查运单表的运输性质
		if(StringUtils.isNotBlank(airWaybill.getProductCode())){
			productCode = airWaybill.getProductCode();
		}else{
			productCode = validaAirWay(airWaybill);
		}
		//设置产品类型
		entity.setProductCode(productCode);
		
		//如果为快递，则需要设置出发部门和到达部门对应的快递代理
		if(SettlementUtil.isPackageProductCode(productCode)){
			validaBill(entity);
		}
	
		//设置、创建时间
		entity.setCreateTime(now);
		//设置、修改时间
		entity.setModifyTime(now);
		
		/*
		 * 税务改造需求
		 * 
		 * 外请车收尾款应付单发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
	}

	private void validaBill(BillPayableEntity entity) {
		//获取始发部门的组织信息
		OrgAdministrativeInfoEntity originalOrgEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(entity.getOrigOrgCode());                
		//如果始发部门是营业部则设置快递点部为对应点部，否则设置为本身
		if(originalOrgEntity.getSalesDepartment().equals(FossConstants.YES)){
			//调用综合接口去查询始发部门快递代理点部
			ExpressPartSalesDeptResultDto expressOrigOrg = expressPartSalesDeptService.
					queryExpressPartSalesDeptBySalesCodeAndTime(entity.getOrigOrgCode(),null);
			//判断是否为空
			if(expressOrigOrg==null){
				throw new SettlementException("航空正单的业务类型属于快递，其始发部门对应的快递代理点部为空，不能进行录入操作！");
			}else if(StringUtils.isNotBlank(expressOrigOrg.getUnifiedCode())){
				//设置快递代理点部
				entity.setExpressOrigOrgCode(expressOrigOrg.getPartCode());
				entity.setExpressOrigOrgName(expressOrigOrg.getPartName());
			}else{
				//设置快递代理点部为本身
				entity.setExpressOrigOrgCode(entity.getOrigOrgCode());
				entity.setExpressOrigOrgName(entity.getOrigOrgName());
			}
		}else{
			//设置快递代理点部为本身
			entity.setExpressOrigOrgCode(entity.getOrigOrgCode());
			entity.setExpressOrigOrgName(entity.getOrigOrgName());
		}
		            
		//获取到达部门的组织信息
		OrgAdministrativeInfoEntity destOrgEntity = orgAdministrativeInfoService.
				queryOrgAdministrativeInfoByCode(entity.getDestOrgCode());                
		//如果到达部门是营业部则设置快递点部为对应点部，否则设置为本身
		if(destOrgEntity.getSalesDepartment().equals(FossConstants.YES)){
			//调用综合接口去查询到达部门快递代理点部
			ExpressPartSalesDeptResultDto expressDestOrg = expressPartSalesDeptService.
					queryExpressPartSalesDeptBySalesCodeAndTime(entity.getDestOrgCode(),null);
			//判断是否为空
			if(expressDestOrg==null){
				throw new SettlementException("航空正单的业务类型属于快递，其到达部门对应的快递代理点部为空，不能进行录入操作！");
			}else if(StringUtils.isNotBlank(expressDestOrg.getUnifiedCode())){
				//设置快递代理点部
				entity.setExpressDestOrgCode(expressDestOrg.getPartCode());
				entity.setExpressDestOrgName(expressDestOrg.getPartName());
			}else{
				//设置快递代理点部为本身
				entity.setExpressDestOrgCode(entity.getDestOrgCode());
				entity.setExpressDestOrgName(entity.getDestOrgName());
			}
		}else{
			//设置快递代理点部为本身
			entity.setExpressDestOrgCode(entity.getDestOrgCode());
			entity.setExpressDestOrgName(entity.getDestOrgName());
		}
	}

	private String validaAirWay(AirWaybillEntity airWaybill) {
		String productCode;
		//查询航空正单对应的一个明细运单号
		List<String> waybillNos = pointsSingleJointTicketDao.queryWaybillNoList(airWaybill.getAirWaybillNo());
		if(CollectionUtils.isNotEmpty(waybillNos)){
			String waybillNo = waybillNos.get(0);
			if(null == waybillNo){
				throw new SettlementException("处理新增空运配载单，有航空正单对应的运单为空");
			}
			WaybillEntity wayDto = WaybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(null == wayDto){
				throw new SettlementException("处理新增空运配载单，有航空正单对应的运单信息(或运输性质)为空");
			}
			productCode = wayDto.getProductCode();
			productCode = productCode==null?PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT:productCode;
		}else {
			productCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT;
		}
		return productCode;
	}

	
	
	
	/**
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	
	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	
	/**
	 * @return billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		return billPayableService;
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
	 * @return  the airAgencyCompanyService
	 */
	public IAirAgencyCompanyService getAirAgencyCompanyService() {
		return airAgencyCompanyService;
	}

	/**
	 * @param airAgencyCompanyService the airAgencyCompanyService to set
	 */
	public void setAirAgencyCompanyService(
			IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}

	public IWaybillManagerService getWaybillManagerService() {
		return WaybillManagerService;
	}

	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		WaybillManagerService = waybillManagerService;
	}

	public IAirWaybillDetailDao getPointsSingleJointTicketDao() {
		return pointsSingleJointTicketDao;
	}

	public void setPointsSingleJointTicketDao(
			IAirWaybillDetailDao pointsSingleJointTicketDao) {
		this.pointsSingleJointTicketDao = pointsSingleJointTicketDao;
	}

	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

}
