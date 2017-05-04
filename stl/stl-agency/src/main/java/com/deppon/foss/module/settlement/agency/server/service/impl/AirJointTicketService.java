package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.functors.OrPredicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.IAirAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirJointTicketService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirPickupbillEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 空运合大票服务
 * 
 * @author ibm-zhuwei
 * @date 2012-11-21 上午11:34:15
 */
public class AirJointTicketService implements IAirJointTicketService {

	private static final Logger LOGGER = LogManager.getLogger(AirJointTicketService.class);

	private static final String PACKAGE = "B";
	
	/**
	 * 结算通用服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 应收单服务
	 */
	private IBillReceivableService billReceivableService;
	
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
	
	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;
	
	/**
	 * 新增空运合大票
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:04
	 */
	@Override
	public void addAirJointTicket(AirPickupbillEntity master,
			List<AirPickupbillDetailEntity> details, CurrentInfo currentInfo) {

		LOGGER.info("开始新增空运合大票：" + master.getAirLineCode());

		// 验证空运合大票输入参数合法性
		this.validAirJointTicketParam(master);

		// 校验明细信息
		if (CollectionUtils.isEmpty(details)) {
			throw new SettlementException("运单明细不能为空");
		}
		
		// 处理新增空运合大票
		this.handleAddedAirJointTicket(master, details, currentInfo);
		
		LOGGER.info("结束新增空运合大票：" + master.getAirLineCode());
	}

	/**
	 * 修改空运合大票
	 * 修改时，中转开发人员需要注意：
	 *（1）addedDetails 新增的清单明细  （2） modifiedDetails 修改的清单明细 （3） removedDeltails 删除的清单明细
	 * 新增和删除时，对应收单操作；
	 * 新增（当送货费大于0时），修改，删除时，对应付单进行操作
	 * 注意：只提供费用变更的运单明细信息
	 * @author 092036-foss-bochenlong
	 * @date 修改于2013-07-01 下午17:55:00
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void modifyAirJointTicketDetail(AirPickupbillEntity oldMaster, AirPickupbillEntity newMaster, 
			List<AirPickupbillDetailEntity> addedDetails, List<AirPickupbillDetailEntity> modifiedDetails,
			List<String> removedDetails, CurrentInfo currentInfo) {

		LOGGER.info("开始修改空运合大票");

		// 验证空运合大票输入参数合法性
		this.validAirJointTicketParam(oldMaster);
		
		// 验证空运合大票输入参数合法性
		this.validAirJointTicketParam(newMaster);
		
		// 092036-foss-bochenlong 2013-07-01 逻辑重写
		// 单号 
		List<String> waybillNos = new ArrayList<String>();
		// 新增的单号
		if(CollectionUtils.isNotEmpty(addedDetails)) {
			BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer("waybillNo");
			waybillNos.addAll(CollectionUtils.collect(addedDetails, transformer));
		}
		
		// 删除的单号
		if(CollectionUtils.isNotEmpty(removedDetails))	{
			waybillNos.addAll(removedDetails);
		}
		
		
//-------------------------------------以下对应收单进行操作（新增和删除明细时）------------------------------------------------------
		//update by 231434-bieyexiong ECS空运有可能有包(包一定是B开头,并且一定是商务专递),没有运单号
		validaCurrentInfo(newMaster, removedDetails, currentInfo, waybillNos);
//-----------------------------以下对应付单进行操作（新增（送货费大于0）修改删除明细时）---------------------------------------------------		
		// 修改单号
		validaAirPickup(newMaster, addedDetails, modifiedDetails, currentInfo,
				waybillNos);
		
		LOGGER.info("结束修改空运合大票");
	}

	private void validaCurrentInfo(AirPickupbillEntity newMaster,
			List<String> removedDetails, CurrentInfo currentInfo,
			List<String> waybillNos) {
		String productCode = "";
		if(CollectionUtils.isNotEmpty(waybillNos)){
			if(PACKAGE.equals(waybillNos.get(0).substring(0,1))){
				productCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT;
			}else{
				productCode = WaybillManagerService.queryWaybillBasicByNo(waybillNos.get(0)).getProductCode();
			}
		}
		
		// 如果新增和删除单号集不为空，且运单对应的运输性质不为商务专递，则需要对应收单进行操作
		if (CollectionUtils.isNotEmpty(waybillNos) && !PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.
				equals(productCode)
				) {
			validaEntity(newMaster, removedDetails, currentInfo, waybillNos);
		}
	}

	private void validaEntity(AirPickupbillEntity newMaster,
			List<String> removedDetails, CurrentInfo currentInfo,
			List<String> waybillNos) {
		// 存放应收单的单据类型 --只查询到付运费应收单，代收货款应收单，空运到付运费应收单，空运代理代收货款应收单
		List<String> billTypes = new ArrayList<String>();
		// 到付运费应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		// 代收货款应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		// 空运到付运费应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		// 空运代理代收货款应收单
		billTypes
				.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
		// 根据运单号集合或来源单号结合，单据类型集合查询应收单信息
		List<BillReceivableEntity> receivableLists = this.billReceivableService
				.queryByWaybillNosOrSourceBillNosAndBillTypes(
						waybillNos,
						null,
						SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,
						billTypes,
						FossConstants.ACTIVE,
						SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 存放新增的应收单实体集合
		List<BillReceivableEntity> addList = new ArrayList<BillReceivableEntity>();
		// 存放原合票单据删除的的应收单实体集合
		List<BillReceivableEntity> deleteList = new ArrayList<BillReceivableEntity>();
		if (CollectionUtils.isNotEmpty(receivableLists)) {
			for (BillReceivableEntity entity : receivableLists) {
				validaDetails(removedDetails, currentInfo, addList,
						deleteList, entity);
			}
		}

		// 新增的明细信息，生成挂代理的空运到付运费应收单和空运代理代收货款应收单
		if (CollectionUtils.isNotEmpty(addList)) {
			addBlueBillReceivablesToAgency(addList, newMaster, currentInfo);
		}

		// 删除的明细信息，生成不挂客户的到付运费应收单和代收货款应收单
		if (CollectionUtils.isNotEmpty(deleteList)) {
			addBlueBillReceivablesToDr(deleteList, currentInfo);
		}
	}

	private void validaAirPickup(AirPickupbillEntity newMaster,
			List<AirPickupbillDetailEntity> addedDetails,
			List<AirPickupbillDetailEntity> modifiedDetails,
			CurrentInfo currentInfo, List<String> waybillNos) {
		if(CollectionUtils.isNotEmpty(modifiedDetails)) {
			BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer("waybillNo");
			waybillNos.addAll(CollectionUtils.collect(modifiedDetails, transformer));
		}
		
		// 运单号不为空时
		if (CollectionUtils.isNotEmpty(waybillNos)) {
			// 查询应付单
			List<BillPayableEntity> list = billPayableService.queryBySourceBillNOs(waybillNos, 
					SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP, 
					FossConstants.ACTIVE);
			
			// 应付单不为空
			if(CollectionUtils.isNotEmpty(list)) {
				// 循环红冲该合票清单中运单号对应的应付代理送货费
				for (BillPayableEntity entity : list) {
					// 验证应付单，是否可以进行红冲操作
					validatePayable(entity);
					// BUG-41547 092036-foss-bochenlong
					// 增加是否签收校验，如果签收，则不允许做合大票
					if(entity.getSignDate() != null) {
						throw new SettlementException("运单已经签收，不能修改合大票");
					}
					// 红冲应付单
					billPayableService.writeBackBillPayable(entity, currentInfo);
				} 
			}
		}
		
		// 生成新单据 
		// 新增明细，如果送货费大于0，则生成新单据
		if (CollectionUtils.isNotEmpty(addedDetails)) {
			for (AirPickupbillDetailEntity entity : addedDetails) {
    			if (entity.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
    				// 生成合票送货费应付单
    				BillPayableEntity blueEntity = this.getBillPayable(newMaster, entity, currentInfo);
    				this.billPayableService.addBillPayable(blueEntity, currentInfo);
    			}
    		}
		}
		
		// 修改明细
		// 如果修改明细的送货费大于0，则新增应付单；如果等于0，则只做上面的红冲操作
		if (CollectionUtils.isNotEmpty(modifiedDetails)) {
			for (AirPickupbillDetailEntity entity : modifiedDetails) {
				if (entity.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
					// 生成合票送货费应付单
    				BillPayableEntity blueEntity = this.getBillPayable(newMaster, entity, currentInfo);
    				this.billPayableService.addBillPayable(blueEntity, currentInfo);
				}
			}
		}
	}

	private void validaDetails(List<String> removedDetails,
			CurrentInfo currentInfo, List<BillReceivableEntity> addList,
			List<BillReceivableEntity> deleteList, BillReceivableEntity entity) {
		// BUG-41547 092036-foss-bochenlong
		// 增加是否签收校验，如果签收，则不允许做合大票
		if(entity.getConrevenDate() != null) {
			throw new SettlementException("运单已经签收，不能修改合大票");
		}
		// 删除标识，不为true说明是新增加的明细或修改的数据
		boolean dBl = false;
		if (CollectionUtils.isNotEmpty(removedDetails)) {
			dBl = validaBill(removedDetails, entity, dBl);
		}
		// 针对合票上面删除的应收单存放到一个list集合里面，便于后面
		if (dBl) {
			deleteList.add(entity);
		} else {
			// 新增
			addList.add(entity);
		}
		// 红冲应收单
			this.billReceivableService.writeBackBillReceivable(entity,
					currentInfo);
	}

	private boolean validaBill(List<String> removedDetails,
			BillReceivableEntity entity, boolean dBl) {
		for (String waybillNo : removedDetails) {
			if (entity.getWaybillNo().equals(waybillNo)) {
				dBl = true;
			}
		}
		return dBl;
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
			//空运成本应付单已审核，不能进行修改空运中转提货清单操作
			if(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())){
				throw new SettlementException("空运成本应付单已审核，不能进行修改空运合大票服务清单操作！");
			}
			//空运成本应付单已付款，不能进行修改空运中转提货清单操作！
			if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity.getPayStatus())){
				throw new SettlementException("空运成本应付单已付款，不能进行修改空运合大票服务清单操作！");
			}
		}
	}
	
	/**
	 * 根据历史应收单,生成新的空运代理到付运费应收单/代收货款应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午5:11:07
	 * @param list
	 * @return
	 */
	private void addBlueBillReceivablesToAgency(List<BillReceivableEntity> list,AirPickupbillEntity master,CurrentInfo currentInfo){
		for(BillReceivableEntity entity:list){
			BillReceivableEntity blueEntity=new BillReceivableEntity();
			BeanUtils.copyProperties(entity, blueEntity);
			
			// 设置单据子类型，到付运费应收单经过合大票之后，转变成空运到达代理应收单
			if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(entity.getBillType())){
				blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
			}
			// 设置单据子类型，代收货款应收单经过合大票之后，转变成空运代理代收货款应收单
			else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(entity.getBillType())){
				blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
			}
			
			// 通过空运代理网点，查找空运代理公司
			BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(master.getDestOrgCode());
			if (partner == null) {
				throw new SettlementException("空运网点编码" + master.getDestOrgCode() + "找不到空运公司信息");
			}
			
			// 应收客户为到达代理编号
			blueEntity.setCustomerCode(partner.getAgentCompanyCode());
			blueEntity.setCustomerName(partner.getAgentCompanyName());
			if (SettlementConstants.BLUE_NEW_BILL_NO) {	// 生成新单据
				SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
				blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
			}
			//设置id
			blueEntity.setId(UUIDUtils.getUUID());
			//获取当前的时间
			Date now = Calendar.getInstance().getTime();
			// 设置记账日期
			blueEntity.setAccountDate(now);	
			//设置有效
			blueEntity.setActive(FossConstants.ACTIVE);
			//设置非红单
			blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			//设置版本号
			blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
			//新增应收单
			this.billReceivableService.addBillReceivable(blueEntity, currentInfo);
		}
	}
	
	/**
	 * 把原来挂空运代理的到付运费应收单和空运代理代收货款应收单红冲后，
	 * 生成新的不挂代理的到付运费应收单和空运代理代收货款应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午6:37:33
	 * @param list
	 * @param now
	 * @param currentInfo
	 */
	private void addBlueBillReceivablesToDr(List<BillReceivableEntity> list,CurrentInfo currentInfo){
		for(BillReceivableEntity entity:list){
			BillReceivableEntity blueEntity=new BillReceivableEntity();
			BeanUtils.copyProperties(entity, blueEntity);
			
			// 设置单据子类型，有空运到付运费设置为不挂客户的到付运费应收单
			if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY.equals(entity.getBillType())){
				blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
			}
			
			// 设置单据子类型，有空运代理代收货款应收单设置为不挂客户的代收货款应收单
			else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD.equals(entity.getBillType())){
				blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
			}
			
			// 应收客户为到达代理编号
			blueEntity.setCustomerCode("");
			// 应收客户为到达代理名称
			blueEntity.setCustomerName("");
			
			if (SettlementConstants.BLUE_NEW_BILL_NO) {	// 生成新单据
				SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
				blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
			}
			blueEntity.setId(UUIDUtils.getUUID());
			//获取当前系统时间
			Date now = Calendar.getInstance().getTime();
			// 设置记账日期
			blueEntity.setAccountDate(now);	
			//设置有效
			blueEntity.setActive(FossConstants.ACTIVE);
			//设置红单
			blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			//设置版本号
			blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
			this.billReceivableService.addBillReceivable(blueEntity, currentInfo);			
		}
	}

	
	/**
	 * 验证新增空运合大票
	 * @author ibm-zhuwei
	 * @date 2012-11-5 上午10:58:44
	 */
	private void validAirJointTicketParam(AirPickupbillEntity master) {

		// 合票信息为空
		if (master == null) {
			throw new SettlementException("合票信息不能为空");
		}
		
		// 费用为空
		if (master.getDeliverFeeTotal() == null) {
			throw new SettlementException("合票费用不能为空");
		}
		
		// 空运正单号为空
		if (StringUtils.isEmpty(master.getAirWaybillNo())) {
			throw new SettlementException("航空正单号不能为空");
		}

		// 制单部门为空
		if (StringUtils.isEmpty(master.getOrigOrgCode())) {
			throw new SettlementException("制单部门不能为空");
		}
		
		// 空运代理
		if (StringUtils.isEmpty(master.getDestOrgCode())) {
			throw new SettlementException("空运代理不能为空");
		}
		
		// 业务日期为空
		if (master.getCreateTime() == null) {
			throw new SettlementException("创建时间不能为空");
		}
		
	}
	
	/**
	 * 处理增空运合大票
	 * @author ibm-zhuwei
	 * @date 2012-11-30 下午3:28:33
	 */
	@SuppressWarnings("unchecked")
	private void handleAddedAirJointTicket(AirPickupbillEntity master, List<AirPickupbillDetailEntity> details,
			CurrentInfo currentInfo) {
		
		// 获取运单明细信息
		BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer("waybillNo");
		List<String> waybillNos = (List<String>) CollectionUtils.collect(details, transformer);
		
		// 查询合票应付单,根据传入的一到多个来源单号，获取一到多条应付单信息
		List<BillPayableEntity> billPayables =  billPayableService.queryBySourceBillNOs(waybillNos, 
				SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP, 
				FossConstants.ACTIVE);
		
		if (CollectionUtils.isNotEmpty(billPayables)) {
			throw new SettlementException("已经存在该合票运单费用");
		}
		
		// 运单生成送货费
		for (AirPickupbillDetailEntity detail : details) {
			if (detail.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
				// 生成合票送货费应付单
				BillPayableEntity payableEntity = this.getBillPayable(master, detail, currentInfo);
				//新增应付单
				this.billPayableService.addBillPayable(payableEntity, currentInfo);
			}
		}
		
		String productCode = details.get(0).getTransportType();
		//如果产品类型为商务专递,则不红冲应收单 author 272005 huanglewei
		//悟空系统会传运输性质过来，不需要查运单表 --author 231434 bieyexiong
		if (PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(productCode)
				|| PricingConstants.ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.
				equals(WaybillManagerService.queryWaybillBasicByNo(waybillNos.get(0)).getProductCode())) {
			return;
		}
		
		// 查询运单关联应收单
		//根据传入的一到多个来源单号，获取一到多条应收单信息
		List<BillReceivableEntity> billReceivables = billReceivableService.queryBySourceBillNOs(waybillNos, 
				SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL, 
				FossConstants.ACTIVE);
		
		if (CollectionUtils.isEmpty(billReceivables)) {
			return;
		}
		
		// 到付应收和代收货款应收
		Predicate p1 = new BeanPropertyValueEqualsPredicate("billType", 
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
		Predicate p2 = new BeanPropertyValueEqualsPredicate("billType", 
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		Predicate predicate = new OrPredicate(p1, p2);
		List<BillReceivableEntity> list = (List<BillReceivableEntity>) CollectionUtils.
				select(billReceivables, predicate);
		
		// 循环红冲该合票清单中的运单号对应的应收到付运费和应收代收货款单据，并生成蓝单
		if (CollectionUtils.isNotEmpty(list)) {
			for (BillReceivableEntity entity : list) {
				// BUG-41547 092036-foss-bochenlong
				// 增加是否签收校验，如果签收，则不允许做合大票
				if(entity.getConrevenDate() != null) {
					throw new SettlementException("运单已经签收，不能合大票");
				}
				
				
				// 红冲到达应收单
				this.billReceivableService.writeBackBillReceivable(entity, currentInfo);			
				//获取系统当前时间
				Date now = Calendar.getInstance().getTime();
				
				// 生成新到达应收单
				BillReceivableEntity blueEntity = new BillReceivableEntity();
				BeanUtils.copyProperties(entity, blueEntity);
				
				// 设置单据子类型，到付运费应收单经过合大票之后，转变成空运到达代理应收单
				if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(entity.getBillType())){
					blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
				}
				// 设置单据子类型，代收货款应收单经过合大票之后，转变成空运代理代收货款应收单
				else if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(entity.getBillType())){
					blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
				}

				// 通过空运代理网点，查找空运代理公司
				BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(master.getDestOrgCode());
				if (partner == null) {
					throw new SettlementException("空运网点编码" + master.getDestOrgCode() + "找不到空运公司信息");
				}
				
				// 应收客户为到达代理编号
				blueEntity.setCustomerCode(partner.getAgentCompanyCode());
				// 应收客户为到达代理名称
				blueEntity.setCustomerName(partner.getAgentCompanyName());
				
				//设置应收部门编码
				blueEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
				//设置应收部门名称
				blueEntity.setReceivableOrgName(currentInfo.getCurrentDeptName());
				
				//设置催款部门编码
				blueEntity.setDunningOrgCode(currentInfo.getCurrentDeptCode());
				//设置催款部门名称
				blueEntity.setDunningOrgName(currentInfo.getCurrentDeptName());
				
				//设置到达部门编码
				blueEntity.setDestOrgCode(currentInfo.getCurrentDeptCode());
				//设置到达部门名称
				blueEntity.setDestOrgName(currentInfo.getCurrentDeptName());
				

				if (SettlementConstants.BLUE_NEW_BILL_NO) {	// 生成新单据
					//通过CODE获取枚举值
					SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
					blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
				}
				
				//设置默认获取的id
				blueEntity.setId(UUIDUtils.getUUID());
				blueEntity.setAccountDate(now);	// 设置记账日期
				blueEntity.setBusinessDate(now);// 设置业务日期
				//设置有效
				blueEntity.setActive(FossConstants.ACTIVE);
				blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
				//设置默认版本号
				blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
				
				this.billReceivableService.addBillReceivable(blueEntity, currentInfo);
			}
		}
		
	}
	
	/**
	 * 生成应付单
	 * @author ibm-zhuwei
	 * @date 2013-1-10 上午11:54:56
	 * @param master
	 * @param detail
	 * @param currentInfo
	 * @return
	 */
	private BillPayableEntity getBillPayable(AirPickupbillEntity master, 
			AirPickupbillDetailEntity detail, CurrentInfo currentInfo) {
		//获取系统当前时间
		Date now = Calendar.getInstance().getTime();
		
		BillPayableEntity entity = new BillPayableEntity();

		// ID,应付单号,运单号,运单ID,生成方式,付款方
		entity.setId(UUIDUtils.getUUID());
		//设置应付单号
		entity.setPayableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF92));
		
		//update  by FOSS-231434 2016-06-24 快递包没有运单号
		String productCode = detail.getTransportType();
		if(StringUtils.isBlank(productCode)){
			//根据合大票清单明细单的运单号查询运输性质 
			productCode = WaybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getProductCode();
		}
		//设置运输性质 
		entity.setProductCode(productCode);
		
		//设置运单号
		entity.setWaybillNo(detail.getWaybillNo());
		//设置运单id
		entity.setWaybillId(null);
		//设置创建类型
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__DESTINATION);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		//设置审批状态
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		//设置应该类型
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
		//设置来源单据单号
		entity.setSourceBillNo(detail.getWaybillNo());	// 运单号
		//来源单据类型,
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP);
		//设置付款状态
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		//设置应付部门编码
		entity.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		//设置应付部门名称
		entity.setPayableOrgName(currentInfo.getCurrentDeptName());
		//根据编码查询获取相应的信息
		OrgAdministrativeInfoEntity originalOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());	
		if(originalOrgEntity!=null){
			//应付子公司编码
			entity.setPayableComCode(originalOrgEntity.getSubsidiaryCode());
			//应付子公司名称
			entity.setPayableComName(originalOrgEntity.getSubsidiaryName());
		}
		
		// 设置出发部门编码
		entity.setOrigOrgCode(WaybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getReceiveOrgCode());
		// 设置出发部门名称
		entity.setOrigOrgName(WaybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getReceiveOrgName());

		// 到达部门编码
		entity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		// 到达部门名称
		entity.setDestOrgName(currentInfo.getCurrentDeptName()); 
		
		//如果为快递，则需要设置出发部门和到达部门对应的快递代理
		if(SettlementUtil.isPackageProductCode(productCode)){
			//获取始发部门的组织信息
			OrgAdministrativeInfoEntity origOrgEntity = orgAdministrativeInfoService.
					queryOrgAdministrativeInfoByCode(entity.getOrigOrgCode());                
			//如果始发部门是营业部则设置快递点部为对应点部，否则设置为本身
			if(origOrgEntity.getSalesDepartment().equals(FossConstants.YES)){
				//调用综合接口去查询始发部门快递代理点部
				ExpressPartSalesDeptResultDto expressOrigOrg = expressPartSalesDeptService.
						queryExpressPartSalesDeptBySalesCodeAndTime(entity.getOrigOrgCode(),null);
				//判断是否为空
				if(expressOrigOrg==null){
					throw new SettlementException("新增空运其它应付的业务类型为快递代理，其始发部门对应的快递代理点部为空，不能进行录入操作！");
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
					throw new SettlementException("新增空运其它应付的业务类型为快递代理，其到达部门对应的快递代理点部为空，不能进行录入操作！");
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

		// 通过空运代理网点，查找空运代理公司
		BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(master.getDestOrgCode());
		if (partner == null) {
			throw new SettlementException("空运网点编码" + master.getDestOrgCode() + "找不到空运公司信息");
		}
		
		// 设置应付客户编码
		entity.setCustomerCode(partner.getAgentCompanyCode());
		// 设置应付客户名称
		entity.setCustomerName(partner.getAgentCompanyName());

		// 设置金额
		entity.setAmount(detail.getDeliverFee());
		// 设置已核销金额
		entity.setVerifyAmount(BigDecimal.ZERO);
		// 设置未核销金额
		entity.setUnverifyAmount(entity.getAmount());

		// 设置币种、会计日期、业务日期
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		// 设置会计日期
		entity.setAccountDate(now);
		// 设置业务日期
		entity.setBusinessDate(master.getCreateTime());

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

		// 生效状态、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		//设置创建时间
		entity.setCreateTime(now);
		//设置修改时间、
		entity.setModifyTime(now);
		
		/*
		 * 税务改造需求
		 * 
		 * 外请车收尾款应付单发票标记为 02-非运输专票
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
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
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
	 * @param airAgencyCompanyService
	 */
	public void setAirAgencyCompanyService(
			IAirAgencyCompanyService airAgencyCompanyService) {
		this.airAgencyCompanyService = airAgencyCompanyService;
	}

	/**
	 * @param waybillManagerService the waybillManagerService to set
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		WaybillManagerService = waybillManagerService;
	}

	public IExpressPartSalesDeptService getExpressPartSalesDeptService() {
		return expressPartSalesDeptService;
	}

	public void setExpressPartSalesDeptService(
			IExpressPartSalesDeptService expressPartSalesDeptService) {
		this.expressPartSalesDeptService = expressPartSalesDeptService;
	}

	@Override
	public void validateAirJointTicketDetail(String waybillNo) {
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add(waybillNo);
		// 查询原有空运到达应付单
		List<BillPayableEntity> list = billPayableService.queryBySourceBillNOs(waybillNos, 
				SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP, 
				FossConstants.ACTIVE);
		
		if(CollectionUtils.isNotEmpty(list)){
			
			// 循环红冲该合票清单中的运单号对应的应付代理送货费
			for (BillPayableEntity entity : list) {			
				validatePayable(entity);//验证应付单，是否可以进行红冲操作
		}
	}
	}

}
