package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.settlement.agency.api.server.service.IAirTransferService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupDetailEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.domain.AirTransPickupbillEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * 中转提货服务
 * @author ibm-zhuwei
 * @date 2012-11-30 下午3:17:14
 */
public class AirTransferService implements IAirTransferService {

	private static final Logger LOGGER = LogManager.getLogger(AirTransferService.class);

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
	 * 新增空运中转提货
	 * @author ibm-zhuwei
	 * @date 2012-11-21 上午11:36:04
	 */
	@Override
	public void addAirTransfer(AirTransPickupbillEntity master,
			List<AirTransPickupDetailEntity> details, CurrentInfo currentInfo) {

		LOGGER.info("开始新增空运中转提货：" + master.getAirLineCode());

		// 验证空运中转提货输入参数合法性
		this.validAirTransferParam(master);

		// 校验明细信息
		if (CollectionUtils.isEmpty(details)) {
			throw new SettlementException("运单明细不能为空");
		}
		
		// 处理新增空运中转提货
		this.handleAddedAirTransfer(master, details, currentInfo);
		
		LOGGER.info("结束新增空运中转提货：" + master.getAirLineCode());
		
	}

	/**
	 * 修改空运中转提货明细
	 * 修改时，中转开发人员需要注意：
	 *（1）addedDetails 新增的清单明细  （2） modifiedDetails 修改的清单明细 （3） removedDeltails 删除的清单明细
	 * 新增和删除时，对应收单操作；
	 * 新增（当送货费大于0时），修改，删除时，对应付单进行操作
	 * 注意：只提供费用变更的运单明细信息
	 * @author 092036-foss-bochenlong
	 * @date 修改于2013-07-09 下午15:37:00
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void modifyAirTransferDetail(AirTransPickupbillEntity oldMaster, AirTransPickupbillEntity newMaster,
			List<AirTransPickupDetailEntity> addedDetails,
			List<AirTransPickupDetailEntity> modifiedDetails,
			List<String> removedDetails, CurrentInfo currentInfo) {

		LOGGER.info("开始修改空运中转提货");

		// 验证空运中转提货输入参数合法性
		this.validAirTransferParam(oldMaster);

		// 验证空运中转提货输入参数合法性
		this.validAirTransferParam(newMaster);
		
		if(CollectionUtils.isEmpty(addedDetails)&&CollectionUtils.isEmpty(modifiedDetails)&&CollectionUtils.isEmpty(removedDetails)) {
			throw new SettlementException("传参不正确，缺少修改参数");
		}
		
		// 单号
		List<String> waybillNos = new ArrayList<String>();
		
		// 新增运单
		if (CollectionUtils.isNotEmpty(addedDetails)) {
			BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer("waybillNo");
			waybillNos.addAll(CollectionUtils.collect(addedDetails, transformer));
		}

		// 删除运单
		if (CollectionUtils.isNotEmpty(removedDetails)) {
			waybillNos.addAll(removedDetails);
		}

//-------------------------------------以下对应收单进行操作（新增和删除明细时）------------------------------------------------------
		// 如果运单集合不为空，则需要进行应收单操作
		if(CollectionUtils.isNotEmpty(waybillNos)) {
			validaTransPickup(newMaster, removedDetails, currentInfo,
					waybillNos);
		}
//-----------------------------以下对应付单进行操作（新增（送货费大于0）修改删除明细时）---------------------------------------------------		
		// 修改单号
		if (CollectionUtils.isNotEmpty(modifiedDetails)) {
			BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer("waybillNo");
			waybillNos.addAll(CollectionUtils.collect(modifiedDetails,transformer));
		}
		
		validaAirTrans(oldMaster, newMaster, addedDetails, currentInfo,
				waybillNos);

		// 生成蓝单
		if (CollectionUtils.isNotEmpty(modifiedDetails)) {
			// 循环明细集合
			for (AirTransPickupDetailEntity entity : modifiedDetails) {
				if (entity.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
					// 生成合票送货费应付单
					BillPayableEntity blueEntity = this.getBillPayable(newMaster, entity, currentInfo);
					this.billPayableService.addBillPayable(blueEntity,currentInfo);
				}
			}
		}

		LOGGER.info("结束修改空运中转提货");
	}

	private void validaTransPickup(AirTransPickupbillEntity newMaster,
			List<String> removedDetails, CurrentInfo currentInfo,
			List<String> waybillNos) {
		// 存放应收单的单据类型 --只查询空运到付运费应收单，空运代理代收货款应收单（因为单据都已经做过合票）
		List<String> billTypes=new ArrayList<String>();
		// 空运到付运费应收单
		billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		// 空运代理代收货款应收单
		billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
		// 根据运单号集合或来源单号结合，单据类型集合查询应收单信息
		List<BillReceivableEntity> receivableLists=this.billReceivableService.queryByWaybillNosOrSourceBillNosAndBillTypes(waybillNos, null, 
				SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL, 
				billTypes,FossConstants.ACTIVE,SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 存放新增单号对应应收单
		List<BillReceivableEntity> addList = new ArrayList<BillReceivableEntity>();
		// 存放删除中转提货明细中运单号的红单数据，用于还原应收单客户
		List<BillReceivableEntity> redList=new ArrayList<BillReceivableEntity>();
		if (CollectionUtils.isNotEmpty(removedDetails)){
			//查询删除红单数据
			//根据运单号集合或来源单号结合，单据类型集合查询应收单信息
			redList = this.billReceivableService.queryByWaybillNosOrSourceBillNosAndBillTypes
					(removedDetails, null, SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL, 
					billTypes,FossConstants.INACTIVE,SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		}
		if (CollectionUtils.isNotEmpty(receivableLists)) {
			validaDetails(removedDetails, currentInfo, receivableLists,
					addList);
		}

		//集合不为空
		if (CollectionUtils.isNotEmpty(addList)) {
			//新增或者修改的运单信息，生成挂代理的空运到付运费应收单和空运代理代收货款应收单
			addBlueBillReceivablesToAgency(addList,newMaster,currentInfo);
		}
		
		//集合不为空
		if (CollectionUtils.isNotEmpty(redList)) {
			//删除掉的明细信息，生成挂上一个（红单中）代理的空运到付运费应收单和空运代理代收货款应收单
			addBlueBillReceivablesToUpAgency(removedDetails,redList,currentInfo);
		}
	}

	private void validaAirTrans(AirTransPickupbillEntity oldMaster,
			AirTransPickupbillEntity newMaster,
			List<AirTransPickupDetailEntity> addedDetails,
			CurrentInfo currentInfo, List<String> waybillNos) {
		if (CollectionUtils.isNotEmpty(waybillNos)) {
			// 通过空运代理网点，查找空运代理公司
			BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(oldMaster.getDestOrgCode());
			if (partner == null) {
				throw new SettlementException("空运网点编码" + oldMaster.getDestOrgCode() + "找不到空运公司信息");
			}
			//根据运单号集合和空运代理编码， 查询原有空运到达应付单
			List<BillPayableEntity> list = billPayableService.queryBySourceBillNOsAndCustomerCode(waybillNos, 
					partner.getAgentCompanyCode(),SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP, 
					FossConstants.ACTIVE);
			//原来的数据有可能为空
			if(CollectionUtils.isNotEmpty(list)){
				// 循环红冲该合票清单中的运单号对应的应付代理送货费
				for (BillPayableEntity entity : list) {
					//验证应付单信息
					validatePayable(entity);
					// 红冲应付单
					billPayableService.writeBackBillPayable(entity, currentInfo);
				}
			}
		}
		
		// 生成新单据
		if (CollectionUtils.isNotEmpty(addedDetails)) {
			// 循环明细集合
			for (AirTransPickupDetailEntity entity : addedDetails) {
				if (entity.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
					// 生成合票送货费应付单
					BillPayableEntity blueEntity = this.getBillPayable(newMaster, entity, currentInfo);
					// 新增应付单
					this.billPayableService.addBillPayable(blueEntity,currentInfo);
				}
			}
		}
	}

	private void validaDetails(List<String> removedDetails,
			CurrentInfo currentInfo,
			List<BillReceivableEntity> receivableLists,
			List<BillReceivableEntity> addList) {
		//删除标识，不为true说明是新增的数据
		boolean dBl = false;
		for (BillReceivableEntity entity : receivableLists) {
			dBl = false;
			if (CollectionUtils.isNotEmpty(removedDetails)) {
				dBl = validaRemoved(removedDetails, dBl, entity);
			}
			// 不等于true存放非删除集合运单号对应的应收单数据
			if (!dBl) {
				addList.add(entity);
			}
			// 红冲应收单
			this.billReceivableService.writeBackBillReceivable(entity,
					currentInfo);
		}
	}

	private boolean validaRemoved(List<String> removedDetails, boolean dBl,
			BillReceivableEntity entity) {
		for (String waybillNo : removedDetails) {
			if (entity.getWaybillNo().equals(waybillNo)) {
				dBl = true;// 在删除(removedDetails)的明细集合中
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
			//空运成本应付单已审核，不能进行修改空运中转提货清单操作！
			if(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.equals(entity.getApproveStatus())){
				throw new SettlementException("空运成本应付单已审核，不能进行修改空运中转提货清单操作！");
			}
			//空运成本应付单已付款，不能进行修改空运中转提货清单操作！
			if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity.getPayStatus())){
				throw new SettlementException("空运成本应付单已付款，不能进行修改空运中转提货清单操作！");
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
	private void addBlueBillReceivablesToAgency(List<BillReceivableEntity> list,AirTransPickupbillEntity master,CurrentInfo currentInfo){
		for(BillReceivableEntity entity:list){
			//声明实例应收单对象
			BillReceivableEntity blueEntity = new BillReceivableEntity();
			BeanUtils.copyProperties(entity, blueEntity);

			// 通过空运代理网点，查找空运代理公司
			BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(master.getDestOrgCode());
			if (partner == null) {
				throw new SettlementException("空运网点编码" + master.getDestOrgCode() + "找不到空运公司信息");
			}
			
			// 应收客户为到达代理编号
			blueEntity.setCustomerCode(partner.getAgentCompanyCode());
			// 应收客户为到达代理名称
			blueEntity.setCustomerName(partner.getAgentCompanyName());
			if (SettlementConstants.BLUE_NEW_BILL_NO) {	// 生成新单据
				//通过CODE获取枚举值
				SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
				blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
			}
			//设置id
			blueEntity.setId(UUIDUtils.getUUID());
			//获取系统当前时间
			Date now = Calendar.getInstance().getTime();
			blueEntity.setAccountDate(now);	// 设置记账日期
			//设置有效
			blueEntity.setActive(FossConstants.ACTIVE);
			//设置非红单
			blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			//设置版本号
			blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
			this.billReceivableService.addBillReceivable(blueEntity, currentInfo);
		}
	}
	
	/**
	 * 把原来挂空运代理的到付运费应收单和空运代理代收货款应收单红冲后，
	 * 根据删除明细中的运单号，获取removedDetails集合中最近红冲的应收单记录，
	 * 生成新的空运到付运费应收单和空运代理代收货款应收单 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-16 下午6:37:33
	 * @param removedDetails
	 * @param redList
	 * @param currentInfo
	 */
	private void addBlueBillReceivablesToUpAgency(List<String> removedDetails,List<BillReceivableEntity> 
	redList,CurrentInfo currentInfo){
		for(String waybillNo:removedDetails){
			//空运到达运费应收单
			//根据运单号和应收单单据类型，获取同一个运单号，相同单据类型
			BillReceivableEntity entity=this.getReceivableEntity(redList, waybillNo, 
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
			// 这次如果不判断 是否为空则会copybean时空指针 情景：现付代收货款 到付不含代收货款
			if(entity != null) {
				//（根据历史红单数据，生成新的有效的应收单数据）新增应收单调用
				this.addBillReceivable(entity, currentInfo);
			}
			
			
			//空运代理代收货款应收单
			//根据运单号和应收单单据类型，获取同一个运单号，相同单据类型
			BillReceivableEntity codEntity=this.getReceivableEntity(redList, waybillNo,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
			if(codEntity != null) {
				//（根据历史红单数据，生成新的有效的应收单数据）新增应收单 
				this.addBillReceivable(codEntity, currentInfo);
			}
			
		}
	}
	
	/**
	 * 根据运单号和应收单单据类型，获取同一个运单号，相同单据类型
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午8:28:07
	 * @param redList
	 * @param waybillNo
	 * @param billType
	 * @return
	 */
	private BillReceivableEntity getReceivableEntity(List<BillReceivableEntity> redList,
			String waybillNo,String billType){
		//声明应收单对象
		BillReceivableEntity receEntity=null;
		//声明实例应收单集合对象
		List<BillReceivableEntity> list=new ArrayList<BillReceivableEntity>();
		//循环把有当前运单号的对象放入list对象
		for(BillReceivableEntity entity:redList){
			//不为空的运单号，并且与当前运单号相同的对象
			//不为空的类型，并且与当前的类型相同的对象
			if(entity!=null&&StringUtils.isNotEmpty(waybillNo)&&
					waybillNo.equals(entity.getWaybillNo())
					&&StringUtils.isNotEmpty(billType)&&
					billType.equals(entity.getBillType())){
				//把过滤出来的对象加入list集合对象
				list.add(entity);
			}
		}
		//当list对象集合不为空
		if(CollectionUtils.isNotEmpty(list)){
			//声明一个日期对象
			Date date=null;
			for(int i=0;i<list.size();i++){
				//循环获取list对象中的数据
				BillReceivableEntity entity=list.get(i);
				if(i==0){
					//把数据中的记账日期交给日期对象
					date=entity.getAccountDate();
					receEntity=entity;
				}else{
					//在记账日期之后
					if(date != null && date.before(entity.getAccountDate())){
						//把数据中的记账日期交给日期对象
						date=entity.getAccountDate();
						receEntity=entity;
					}
				}
			}
		}
		return receEntity;
	}
	
	/**
	 *  （根据历史红单数据，生成新的有效的应收单数据）新增应收单
	 *  addBlueBillReceivablesToUpAgency调用
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-17 上午8:23:24
	 * @param entity  历史红单数据
	 * @param currentInfo
	 */
	private void addBillReceivable(BillReceivableEntity entity ,CurrentInfo currentInfo){
		//声明实例应收单对象
		BillReceivableEntity blueEntity=new BillReceivableEntity();
		BeanUtils.copyProperties(entity, blueEntity);
		
		// 应收客户为到达代理编号
		blueEntity.setCustomerCode(entity.getCustomerCode());
		// 应收客户为到达代理名称
		blueEntity.setCustomerName(entity.getCustomerName());
		
		if (SettlementConstants.BLUE_NEW_BILL_NO) {	// 生成新单据
			//通过CODE获取枚举值
			SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
			//获取结算单据编号
			blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
		}
		//设置id
		blueEntity.setId(UUIDUtils.getUUID());
		//获取系统当前时间
		Date now = Calendar.getInstance().getTime();
		blueEntity.setAccountDate(now);	// 设置记账日期
		blueEntity.setBusinessDate(now);	// 设置业务日期
		//设置有效
		blueEntity.setActive(FossConstants.ACTIVE);
		//设置非红单
		blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		//设置版本号
		blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		//红冲金额为负数，故在这里乘以-1变成正数
		blueEntity.setAmount(entity.getAmount().multiply(new BigDecimal(-1)));
		//设置已核销金额
		blueEntity.setUnverifyAmount(entity.getUnverifyAmount().multiply(new BigDecimal(-1)));
		//设置未核销金额
		blueEntity.setVerifyAmount(entity.getVerifyAmount().multiply(new BigDecimal(-1)));
		//新增应收单
		this.billReceivableService.addBillReceivable(blueEntity, currentInfo);			
	}
	

	/**
	 * 验证新增空运中转提货
	 * @author ibm-zhuwei
	 * @date 2012-11-5 上午10:58:44
	 */
	private void validAirTransferParam(AirTransPickupbillEntity master) {

		// 中转提货信息为空
		if (master == null) {
			//中转提货信息不能为空异常
			throw new SettlementException("中转提货信息不能为空");
		}
		
		// 费用为空
		if (master.getDeliverFeeTotal() == null) {
			//中转提货费用不能为空异常
			throw new SettlementException("中转提货费用不能为空");
		}

		// 中转单号为空
		if (StringUtils.isEmpty(master.getAirTransferPickupbillNo())) {
			//中转单号不能为空异常
			throw new SettlementException("中转单号不能为空");
		}

		// 空运正单号为空
		if (StringUtils.isEmpty(master.getAirWaybillNo())) {
			//航空正单号不能为空异常
			throw new SettlementException("航空正单号不能为空");
		}

		// 制单部门为空
		if (StringUtils.isEmpty(master.getCreateOrgCode())) {
			//制单部门不能为空异常
			throw new SettlementException("制单部门不能为空");
		}

		// 空运代理
		if (StringUtils.isEmpty(master.getDestOrgCode())) {
			//空运代理不能为空异常
			throw new SettlementException("空运代理不能为空");
		}
		
		// 业务日期为空
		if (master.getCreateTime() == null) {
			//创建时间不能为空异常
			throw new SettlementException("创建时间不能为空");
		}
		
	}
	
	/**
	 * 处理新增空运中转提货
	 * 
	 * @author ibm-zhuwei
	 * @date 2012-11-30 下午3:30:26
	 */
	@SuppressWarnings("unchecked")
	private void handleAddedAirTransfer(AirTransPickupbillEntity master,
			List<AirTransPickupDetailEntity> details, CurrentInfo currentInfo) {

		// 通过空运代理网点，查找空运代理公司
		BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(master.getDestOrgCode());
		if (partner == null) {
			throw new SettlementException("空运网点编码" + master.getDestOrgCode() + "找不到空运公司信息");
		}
		
		// 获取运单明细信息
		BeanToPropertyValueTransformer transformer = new BeanToPropertyValueTransformer("waybillNo");
		List<String> waybillNos = (List<String>) CollectionUtils.collect(details, transformer);
		
		// 根据中转提货明细中的运单号信息，查询送货费应付单信息
		// BUG-51571
		List<String> sourceBillTypes = Arrays.asList(new String[] {
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_PICKUP,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP,
						SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__AIR_CHANGE });
		List<BillPayableEntity> billPayables = billPayableService
				.selectBySourceBillNosTypes(waybillNos, sourceBillTypes,
						FossConstants.ACTIVE);

		if (CollectionUtils.isNotEmpty(billPayables)) {
			// 新增时，验证明细中同一个运单，同一个代理已经生成应付单数据
			for (BillPayableEntity entity : billPayables) {
				for (AirTransPickupDetailEntity air : details) {
					// 运单号相同、代理编码相同，提示异常信息
					// 同一运单同一代理不能给两笔费用
					if (entity.getWaybillNo().equals(air.getWaybillNo())
							&& entity.getCustomerCode().equals(
									partner.getAgentCompanyCode())) {
						throw new SettlementException("同一运单同一代理给了两笔费用");
					}
				}
			}
		}
		
		// 运单生成送货费
		for (AirTransPickupDetailEntity detail : details) {
			if (detail.getDeliverFee().compareTo(BigDecimal.ZERO) > 0) {
				//运单中的送货费大于0，生成中转提货送货费应付单
				BillPayableEntity payableEntity = this.getBillPayable(master, detail, currentInfo);
				//新增应付单
				this.billPayableService.addBillPayable(payableEntity, currentInfo);
			}
		}

		// 查询运单关联应收单
		List<BillReceivableEntity> billReceivables = billReceivableService.queryBySourceBillNOs(waybillNos, 
				SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL, 
				FossConstants.ACTIVE);
		if (CollectionUtils.isEmpty(billReceivables)) {
			return;
		}
		
		// 到付应收和代收货款应收
		//"billType"
		//合大票之后，类型已经变为：空运到达代理应收单
		Predicate p1 = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE, 
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY);
		//合大票之后，转变成空运代理代收货款应收单
		Predicate p2 = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE, //"billType"
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD);
		Predicate predicate = new OrPredicate(p1, p2);
		List<BillReceivableEntity> list = (List<BillReceivableEntity>) CollectionUtils.
				select(billReceivables, predicate);
		
		// 循环红冲该合票清单中的运单号对应的应收到付运费和应收代收货款单据，并生成蓝单
		if (CollectionUtils.isNotEmpty(list)) {
			for (BillReceivableEntity entity : list) {
				// 红冲到达应收单
				this.billReceivableService.writeBackBillReceivable(entity, currentInfo);
				Date now = Calendar.getInstance().getTime();
				
				// 生成新到达应收单
				BillReceivableEntity blueEntity = new BillReceivableEntity();
				BeanUtils.copyProperties(entity, blueEntity);

				// 应收客户为到达代理编号
				blueEntity.setCustomerCode(partner.getAgentCompanyCode());
				// 应收客户为到达代理名称
				blueEntity.setCustomerName(partner.getAgentCompanyName());
				
				//设置应收部门编码
				blueEntity.setReceivableOrgCode(currentInfo.getCurrentDeptCode());
				//设置应收部门名称
				blueEntity.setReceivableOrgName(currentInfo.getCurrentDeptName());
				
				// 生成新单据
				if (SettlementConstants.BLUE_NEW_BILL_NO) {	
					//通过CODE获取枚举值
					SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
					blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
				}
				//设置id
				blueEntity.setId(UUIDUtils.getUUID());
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
		
	}
	
	/**
	 * 根据空运中转清单生成应付单
	 * @author 00123-foss-huangxiaobo
	 * @date 2012-12-24 下午8:32:46
	 * @param master 中转清单
	 * @param detail 中转清单明细
	 * @param currentInfo 当前用户信息
	 * @return 应付单信息
	 */
	private BillPayableEntity getBillPayable(AirTransPickupbillEntity master, 
			AirTransPickupDetailEntity detail, CurrentInfo currentInfo) {
		//获取系统当前时间
		Date now = Calendar.getInstance().getTime();
		//声明实例应付单
		BillPayableEntity entity = new BillPayableEntity();

		// ID,应付单号,运单号,运单ID,生成方式,付款方
		entity.setId(UUIDUtils.getUUID());
		//设置应付单号
		entity.setPayableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF91));
		
		//设置产品类型
		entity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT);
		//设置运单号
		entity.setWaybillNo(detail.getWaybillNo());
		//设置运单id
		entity.setWaybillId(null);
		//设置生成方式
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		entity.setPayerType(SettlementDictionaryConstants.BILL_PAYABLE__PAYER_TYPE__DESTINATION);
		entity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		//设置审批状态
		entity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		//设置应付类型
		entity.setPayableType(null);

		// 单据子类型，来源单据编码，来源单据类型,应付部门编码，应付部门名称
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__AIR_AGENCY_DELIVERY);
		//设置来源单据编码
		entity.setSourceBillNo(detail.getWaybillNo());	// 运单号
		//设置来源单据类型
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP);
		//设置付款状态
		entity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		//设置应付部门编码
		entity.setPayableOrgCode(currentInfo.getCurrentDeptCode());
		//设置应付部门名称
		entity.setPayableOrgName(currentInfo.getCurrentDeptName());
		//根据编码查询获取组织详细信息
		OrgAdministrativeInfoEntity origOrgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());	
		if (origOrgEntity != null) {
			//应付子公司编码
			entity.setPayableComCode(origOrgEntity.getSubsidiaryCode());
			//应付子公司名称
			entity.setPayableComName(origOrgEntity.getSubsidiaryName());
		}

		// 设置出发部门编码
				entity.setOrigOrgCode(WaybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getReceiveOrgCode());
				// 设置出发部门名称
				entity.setOrigOrgName(WaybillManagerService.queryWaybillBasicByNo(detail.getWaybillNo()).getReceiveOrgName());

		// 到达部门名称、到达部门编码
		entity.setDestOrgCode(currentInfo.getCurrentDeptCode());
		entity.setDestOrgName(currentInfo.getCurrentDeptName());

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

		// 设置币种
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

		// 生效状态、创建时间、修改时间、对方部门
		entity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		//创建时间
		entity.setCreateTime(now);
		//修改时间
		entity.setModifyTime(now);
		
		/*
		 * 税务改造需求
		 * 
		 * 空运代理应付单发票标记为 02-非运输专票
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

	@Override
	public void validateAirTransferDetail(String destOrgCode,String waybillNo) {
		
		List<String> waybillNos=new ArrayList<String>();
		waybillNos.add(waybillNo);	
				//修改时，修改集合和删除集合不能同时为空
				if(CollectionUtils.isEmpty(waybillNos)){
					throw new SettlementException("修改空运中转提货明细，输入的参数不能为空！");
				}
				
		if (CollectionUtils.isNotEmpty(waybillNos)) {
		// 通过空运代理网点，查找空运代理公司
		BusinessPartnerEntity partner = airAgencyCompanyService.queryBusinessPartnerByAgencyDeptCode(destOrgCode);
		if (partner == null) {
			throw new SettlementException("空运网点编码" + destOrgCode + "找不到空运公司信息");
		}
		
		//根据运单号集合和空运代理编码， 查询原有空运到达应付单
		List<BillPayableEntity> list = billPayableService.queryBySourceBillNOsAndCustomerCode(waybillNos, 
				partner.getAgentCompanyCode(),SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__TRANSFER_PICKUP, 
				FossConstants.ACTIVE);

		//原来的数据有可能为空
		if(CollectionUtils.isNotEmpty(list)){
			
			// 循环红冲该合票清单中的运单号对应的应付代理送货费
			for (BillPayableEntity entity : list) {
				BillPayableEntity blueEntity = new BillPayableEntity();	// 蓝单
				BeanUtils.copyProperties(entity, blueEntity);
				
				//验证应付单信息
				validatePayable(entity);
				
				// 红冲应付单
			}
		}
	}
		
	}
		
	


}
