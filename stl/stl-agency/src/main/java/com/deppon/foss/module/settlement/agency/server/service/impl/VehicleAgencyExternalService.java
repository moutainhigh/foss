package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IVehicleAgencyExternalService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementExternalBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.transfer.partialline.api.server.service.IExternalBillService;
import com.deppon.foss.module.transfer.partialline.api.shared.define.PartiallineConstants;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 偏线外发单录入、修改、审核、反审核、作废服务
 * 
 * 
 * 2013-03-05日需求变更后，外发单录入不存在：实收货款金额和实付代理费用金额
 * @author 099995-foss-wujiangtao
 * @date 2012-10-23 下午6:35:29
 * @since
 * @version
 * @param <IReverseBillWriteoffService>
 * @param <ReverseBillWriteoffDto>
 */
public class VehicleAgencyExternalService<IReverseBillWriteoffService, ReverseBillWriteoffDto> implements
		IVehicleAgencyExternalService {

	/**
	 * 注入日志接口
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(VehicleAgencyExternalService.class);


	/**
	 * 结算通用Common Service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 应收单Service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 应付单Service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 核销单Service
	 */
	private IBillWriteoffService billWriteoffService;


	/**
	 * 综合管理-客户信息Service
	 */
	private ICustomerService customerService;

	/**
	 * 综合管理-部门信息Service
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 操作日志Service
	 */
	private IOperatingLogService operatingLogService;
	
	/**
	 * 查询配置参数表 
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 查询运单签收结果Service 
	 */
    private IWaybillSignResultService waybillSignResultService;
    
    
    
    /**
	 * 对账单公共service 
	 * modify 095793 -liqin   2013-07-30
	 *  
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	
	/**
	 * 反核消service
	 */
	private IReverseBillWriteoffService reverseBillWriteoffService;
	
	/**
	 * 外发单接口
	 */
	private IExternalBillService externalBillService;
	
	/**
	 * 新增外发单服务
	 * 
	 * 【 运单号：waybillNo 付款方式：paidMethod 制单人部门：waifabumen 制单部门名称：waifabumenName
	 * 外发单号：externalBillNo 外发代理费：externalAgencyFee 送货费：deliveryFee
	 * 外发成本总额：costAmount 
	 * 偏线代理编码：agentCompanyCode 偏线代理名称：agentCompanyName 是否中转外发：transferExternal
	 * 币种：currencyCode 业务日期：businessDate 】
	 * 
	 * 	//因为中转也会查询运单信息，故下列这些运单中存在的属性有中转传给结算系统
	 * 【
	 * 		运单Id：waybillId
	 * 		出发部门编码：receiveOrgCode
	 * 		到达部门编码：lastLoadOrgCode
	 * 		送货费： deliveryGoodsFee  没有可为空
	 * 		总费用：totalFee
	 * 		代收货款费用：codAmount  没有可为空
	 * 】
	 * 
	 * 2013-03-05日需求变更后，外发单录入不存在：实收货款金额和实付代理费用金额
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:34:37
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void addExternalBill(SettlementExternalBillDto dto,
			CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if (dto == null) {
			throw new SettlementException("新增外发单参数为空！");
		}

		LOGGER.info(" stl 新增外发单服务的外发单号为：" + dto.getExternalBillNo()
				+ " 外发代理编码为： " + dto.getAgentCompanyCode() + " 运单号为： "
				+ dto.getWaybillNo());

		// 验证各种参数信息
		this.validateParams(dto);

		// 外发单传入的费用信息和运单的费用信息作比较
		this.validateExternalBillFeeByWaybill(dto);

		//验证是否存在相同财务记录的开关，待后续程序稳定后可以去掉
		if(SettlementConstants.EXTEND_VALIDATE_SETTLEMENT_BILL_IS_EXISTS){
			this.validateSameStlBill(dto);
		}
		
		/***************************公共验证数据完毕，进行保存操作 ***********************/
		
		//定义一个日期，保证本次新增财务单据的记账日期一致
		Date date = new Date();

		// 生成偏线到付运费应收单信息
		addPartialLineBillReceivable(dto, date, currentInfo);
		
		// 生成偏线成本应付单
		if (dto.getCostAmount() != null
				&& dto.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {
			
			// 添加应付单和付款单的关联
			this.addPartialLineBillPayable(dto, date, currentInfo); 
		}

		LOGGER.info("stl 新增外发单成功 -------------");
	}

	/**
	 * 验证根据外发单是否已生成相同的财务单据
	 * （防止同一个外发单号出现重复提交现象,非必须）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-20 下午3:23:12
	 * @param dto
	 */
	private void validateSameStlBill(SettlementExternalBillDto dto) {

		// 根据运单号和外发单号、客户编码、判断是否已存在有效的偏线到达运费应收单
		BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
				dto.getWaybillNo());
		
		//设置查询需要的外发单号
		billReceivableConDto.setExternalBillNo(dto.getExternalBillNo());
		
		//设置查询需要的外发代理编码
		billReceivableConDto.setPartailLineAgentCode(dto.getAgentCompanyCode());
		
		//设置查询的应收单类型
		billReceivableConDto.setBillTypes(new String[] {
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE });
		
		//查询是否已经存在相同的偏线到达运费应收单
		int k = billReceivableService
				.queryReceivableByExternalBillNo(billReceivableConDto);
		if (k > 0) {
			throw new SettlementException("已存在相同的外发单号和代理编号相同的偏线到达运费应收单");
		}

		// 根据运单号和外发单号、代理编码、判断是否已存在有效的偏线成本应付单
		BillPayableConditionDto payableConDto = new BillPayableConditionDto(
				dto.getWaybillNo());
		// 根据外发单号
		payableConDto.setExternalBillNo(dto.getExternalBillNo());
		// 根据代理编码
		payableConDto.setPartailLineAgentCode(dto.getAgentCompanyCode());
		payableConDto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE });
		
		//根据运单号和外发单号、代理编码、判断是否已存在有效的偏线成本应付单
		k = this.billPayableService
				.queryBillPayableByExternalBillNo(payableConDto);
		if (k > 0) {
			//已存在相同的外发单号和代理编号相同的偏线成本应付单
			throw new SettlementException("已存在相同的外发单号和代理编号相同的偏线成本应付单");
		}
	}

	/**
	 * 新增外发单时，红冲原有应收单，生成新的偏线到达运费应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 上午11:01:56
	 * @param dto
	 * @param date
	 * @param currentInfo
	 */
	private void addPartialLineBillReceivable(SettlementExternalBillDto dto,
			Date date, CurrentInfo currentInfo) {

		// 查询到达运费应收单
		BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
				dto.getWaybillNo());
		//设置单据类型
		billReceivableConDto.setBillTypes(new String[] {
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE });
		// 根据传入的运单号和单据类型等参数，查询[到付运费/始发/代收货款]有效应收单信息可公用
		List<BillReceivableEntity> list = billReceivableService
				.queryBillReceivableByCondition(billReceivableConDto);
		BillReceivableEntity entity = null;// 到付运费应收单/偏线到达运费应收单
		//判断单据是否存在
		if (CollectionUtils.isNotEmpty(list)) {
			if (list.size() > 1) {
				//同一个运单，存在多条到达运费应收单异常
				throw new SettlementException("同一个运单，存在多条到达运费应收单");
			}
			entity = list.get(0);
			//验证应收单
			this.validateBillReceivable(entity, date);

			// 校验成功后，红冲应收单，生成偏线到付运费应收单
			this.billReceivableService.writeBackBillReceivable(entity,currentInfo);
			
			// BUG-41009 重新获取当前系统时间，使红单记账日期早于蓝单时间
			date = new Date();
			
			//填充新的应收单方法
			BillReceivableEntity blueEntity = this.getBlueBillReceivableEntity(
					entity, date, dto);

			// 生成偏线到付运费应收单，调用公共新增应收单方法
			this.billReceivableService.addBillReceivable(blueEntity,currentInfo);
		}
	}

	/**
	 * 设置应收单蓝单数据
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午10:13:49
	 * @param entity
	 *            原始单据实体
	 * @param date
	 *            系统时间
	 * @param dto 接口传入参数dto
	 * @return
	 */
	private BillReceivableEntity getBlueBillReceivableEntity(
			BillReceivableEntity entity, Date date,
			SettlementExternalBillDto dto) {
		//声明实例应收单
		BillReceivableEntity blueEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(entity, blueEntity);
		
		// ID
		blueEntity.setId(UUIDUtils.getUUID());
		
		// 生成新单据
		if (SettlementConstants.BLUE_NEW_BILL_NO) { 
			SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity
					.getReceivableNo());
			//设置应收单号
			blueEntity.setReceivableNo(settlementCommonService
					.getSettlementNoRule(rule));
		}
		
		// 设置来源单号 -外发单
		blueEntity.setSourceBillNo(dto.getExternalBillNo());
		
		// 单据类型
		blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE);
		
		// 设置客户信息
		blueEntity.setCustomerCode(dto.getAgentCompanyCode());
		
		// 设置客户名称
		blueEntity.setCustomerName(dto.getAgentCompanyName());
		
		// 设置通用参数
		setReceivableCommonParam(blueEntity);
		
		//记账日期
		blueEntity.setAccountDate(date);
		
		//业务日期
		blueEntity.setBusinessDate(date);
		
		//如果付款方式为空--默认设置为到付
		if(StringUtils.isEmpty(blueEntity.getPaymentType())){
			blueEntity.setPaymentType(
					SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);//到付
		}
		
		return blueEntity;
	}

	/**
	 * 新增外发单，添加偏线成本应付单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 下午12:23:54
	 * @param dto
	 * @param date
	 * @param currentInfo
	 */
	public void addPartialLineBillPayable(SettlementExternalBillDto dto,
			Date date,  CurrentInfo currentInfo) {
		//声明实例应付单对象
		BillPayableEntity entity = new BillPayableEntity();
		
		//ID
		entity.setId(UUIDUtils.getUUID());
		
		//应付单号
		entity.setPayableNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YF7));
		
		//运单号
		entity.setWaybillNo(dto.getWaybillNo());
		
		//运单ID
		entity.setWaybillId(dto.getWaybillId());
		
		//系统自动生成
		entity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		
		//单据类型
		entity.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE);
		
		//来源单号：外发单号
		entity.setSourceBillNo(dto.getExternalBillNo());
		
		// 来源单据类型：外发单
		entity.setSourceBillType(SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__PARTIAL_LINE);

		// 应付部门编码：外发部门编码
		entity.setPayableOrgCode(dto.getWaifabumen());
		
		//应付部门名称：外发部门名称
		entity.setPayableOrgName(dto.getWaifabumenName());
		
		
		//设置应付公司  --chenmingchun 2013-4-16
		OrgAdministrativeInfoEntity orgItem = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getPayableOrgCode());
		//如果部门为空，则抛出异常
		if(orgItem!=null){
			//设置公司编码和名称
			entity.setPayableComCode(orgItem.getSubsidiaryCode());
			//设置公司名称
			entity.setPayableComName(orgItem.getSubsidiaryName());
		}

		// 出发部门编码：对应运单的始发部门
		entity.setOrigOrgCode(dto.getReceiveOrgCode());

		// 获取出发部门名称-调用综合管理Service
		OrgAdministrativeInfoEntity orgEntity = this.orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(dto.getReceiveOrgCode());
		if (orgEntity != null) {
			//设置出发部门名称
			entity.setOrigOrgName(orgEntity.getName());
		}

		// 到达部门编码和到达部门名称
		entity.setDestOrgCode(dto.getLastLoadOrgCode());
		// 获取出发部门名称-调用综合管理Service
		orgEntity = this.orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCode(dto.getLastLoadOrgCode());
		if (orgEntity != null) {
			// 对应运单的到达部门
			entity.setDestOrgName(orgEntity.getName());
		}

		//客户编码： 外发代理编码
		entity.setCustomerCode(dto.getAgentCompanyCode());
		
		//客户名称：外发代码名称
		entity.setCustomerName(dto.getAgentCompanyName());
		
		// 应付金额
		entity.setAmount(dto.getCostAmount());
		//新增产品类型 --汽运偏线
		entity.setProductCode(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE);
		
		// 已核销金额默认设置为0
		entity.setVerifyAmount(BigDecimal.ZERO);
		
		// 未核销金额
		entity.setUnverifyAmount(dto.getCostAmount());
		
		// 默认设置为人民币
		entity.setCurrencyCode(dto.getCurrencyCode() != null ? dto
				.getCurrencyCode() : FossConstants.CURRENCY_CODE_RMB);
		
		// 记账日期
		entity.setAccountDate(date);
		
		// 业务日期
		entity.setBusinessDate(dto.getBusinessDate());
		
		// 设置通用参数
		setPayableCommonParam(entity);
						
		// 送货费
		entity.setDeliverFee(dto.getDeliveryFee());

		// 外发运费
		entity.setOutgoingFee(dto.getExternalAgencyFee());// 参数中的- 外发代理费
				
		/*
		 * 税务改造需求
		 * 
		 * 偏线成本应付单发票标记为 02-非运输专票
		 * 
		 * 杨书硕 2013-11-5 上午9:58:56
		 */
		
		entity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
		
		// 保存偏线成本应付单，公共保存应付单方法
		billPayableService.addBillPayable(entity, currentInfo);
	}


	/**
	 * 验证修改外发单传入的数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-20 上午9:39:05
	 * @param dto
	 */
	private void validateModifyExternalBillParams(SettlementExternalBillDto dto,Date date){
		
		// 公共验证各种参数信息
		this.validateParams(dto);
		
		//外发单已审核，不能进行修改操作
		if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED.equals(dto
				.getAuditStatus())) {
			throw new SettlementException("外发单已审核，不能进行修改操作");
		}

		// 外发单的创建时间与当前系统时间相差超过过M天（M天为财务红冲超期天数）时，
		//异常信息为“外发单的创建时间与当前系统时间相差超过过M天，不能修改
		int maxWriteBackDays=0;
		
		//调用综合管理接口，查询财务可红冲最大时间，单位为天
		ConfigurationParamsEntity conParam = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
						ConfigurationParamsConstants.STL_PL_WRITEBACK_DAY,
						FossConstants.ROOT_ORG_CODE);
		//校验参数
		if(conParam!=null&&StringUtils.isNotEmpty(conParam.getConfValue())
				&&StringUtils.isNumeric(conParam.getConfValue())){
			//必须为整数
			maxWriteBackDays=Integer.parseInt(conParam.getConfValue());
		}
		
		//计算外发单业务日期和当前系统时间相差的天数
		Long l=DateUtils.getTimeDiff(dto.getCreateTime(), date);
		if(l!=null&&l.intValue()>maxWriteBackDays){
			throw new SettlementException("外发单的创建时间与当前系统时间相差超过"+maxWriteBackDays+"天，不能进行修改操作");
		}		
	}
	
	/**
	 * 修改外发单服务 
	 * 
	 * @author 092036-foss-bochenlong
	 * @date 修改于2013-07-24 下午3:33:00
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void modifyExternalBill(SettlementExternalBillDto dto,
			CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if (dto == null) {
			throw new SettlementException("修改外发单参数为空！");
		}
		LOGGER.info(" 修改外发单服务的外发单号为：" + dto.getExternalBillNo() + " 外发代理编码为： "
				+ dto.getAgentCompanyCode() + " 运单号为： " + dto.getWaybillNo());
		Date date = new Date();

		//验证修改外发单传入的数据
		validateModifyExternalBillParams(dto,date);
		
		// 外发单传入的费用信息和运单的费用信息作比较
		this.validateExternalBillFeeByWaybill(dto);

//------------------------------------------------对应付单进行操作------------------------------------------------------
		// 如果修改了外发成本，则对应付单操作
		if(dto.getIsModifyCostAmount().equals(FossConstants.YES)) {
			// 如果存在偏线成本应付单,红冲原单据生成蓝单应付单
			List<BillPayableEntity> payableList = this.queryBillPayableByCondition(dto);
			//查询系统中如果不存在偏线成本应付单，不能进行后续操作
			if (CollectionUtils.isEmpty(payableList)) {
				throw new SettlementException("不存在偏线外发单财务单据，不能进行修改操作");
			} 	
			
			//应付单实体
			BillPayableEntity payableEntity = payableList.get(0);
			this.validateBillEntity(payableEntity, true);
			
			// 红冲原偏线成本应付单单据，生成新的单据
			this.writeBackBillPayableByModifyExternalBill(payableEntity, dto,
							date, currentInfo);
		}

//------------------------------------------------对应收单进行操作------------------------------------------------------
		// 如果修改了是否中转，则对应收单操作
		if(dto.getIsModifyTransferExternal().equals(FossConstants.YES)) {
			// 查询应收单
			List<BillReceivableEntity> receList = null;
			
			// 如果是中转外发，则原应收单为偏线代理应收单
			if(PartiallineConstants.IS_TRANSFER_EXTERNAL_YES.equals(dto.getTransferExternal())) {
				receList = this.queryBillReceivableByCondition(dto);
			} 
			
			// 如果否中转外发，则原应收单为到付运费应收单
			if(PartiallineConstants.IS_TRANSFER_EXTERNAL_NO.equals(dto.getTransferExternal())) {
				receList = this.queryBillReceivableByWayBillNo(dto);
			}
			
			if (CollectionUtils.isNotEmpty(receList)) {
				
				// 验证同一个外发单和代理编码是否存在相同类型的多个应收单
				this.billReceivableService.validateWaybillForBillReceivable(receList);
				
				BillReceivableEntity receEntity = receList.get(0);

				// 验证应收单
				this.validateBillReceivable(receEntity, date);
				
				// 如果：是中转外发--红冲应收单，生成新的不挂代理的到付运费应收单（客户编码、客户名称为空，单据类型为：到付运费应收单）
				if (PartiallineConstants.IS_TRANSFER_EXTERNAL_YES.equals(dto.getTransferExternal())) {
					this.writebackBillReceivableByTransferExternalBill(receEntity, dto.getWaybillNo(), date, currentInfo);
				} else {
					// 如果：否中转外发--红冲应收单生成新的蓝单，挂到达代理的应收单（单据类型：偏线到达运费应收单）
					this.writeBackBillReceivableByModifyExternalBill(receEntity, date, dto, currentInfo);
				}
			}
		}
		
		LOGGER.info(" stl  修改外发单服务成功！");
	}
	
	/**
	 * 修改外发单选择的是中转外发时，红冲偏线到付运费应收单，生成新的不挂客户的到付运费应收单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-26 下午3:59:06
	 * @param dto
	 * @param currentInfo
	 */
	private void writebackBillReceivableByTransferExternalBill(
			BillReceivableEntity receEntity,String waybillNo, 
			Date date,CurrentInfo currentInfo) {

		// 红冲应收单
		this.billReceivableService.writeBackBillReceivable(receEntity,
				currentInfo);

		// 生成到付运费应收单
		BillReceivableEntity blueEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(receEntity, blueEntity);
		// 设置Id
		blueEntity.setId(UUIDUtils.getUUID());
		//设置单号
		blueEntity.setReceivableNo(this.settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.YS2));
		//设置来源单号
		blueEntity.setSourceBillNo(waybillNo);
		
		//设置来源单据类型为：运单
		blueEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		
		//设置单据类型为：到达运费应收单
		blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);

		// 设置客户信息为空
		blueEntity.setCustomerCode("");
		// 设置客户信息为空
		blueEntity.setCustomerName("");
		
		// 设置通用参数
		setReceivableCommonParam(blueEntity);
		
		// BUG-49384 红冲之后重新生成Date，使红单时间早于蓝单
		date = new Date();
		
		//设置创建时间
		blueEntity.setCreateDate(date);
		
		//记账日期
		blueEntity.setAccountDate(date);
		
		//调用应收单接口
		this.billReceivableService.addBillReceivable(blueEntity,
				currentInfo);
	}

	/**
	 * 修改外发单、红冲原有的应收单，生成新的蓝单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午8:36:10
	 * @param entity
	 * @param date
	 * @param dto
	 * @param currentInfo
	 */
	private void writeBackBillReceivableByModifyExternalBill(
			BillReceivableEntity entity, Date date,
			SettlementExternalBillDto dto, CurrentInfo currentInfo) {
		//存在数据执行下面的方法
		if (entity != null&&StringUtils.isNotEmpty(entity.getId())) {
			
			// 调红冲应收单方法
			this.billReceivableService.writeBackBillReceivable(entity,currentInfo);
			
			// BUG-49384 红冲之后重新生成Date，使红单时间早于蓝单
			date = new Date();
			
			// 获取蓝单实体 
			BillReceivableEntity blueEntity = this.getBlueBillReceivableEntity(entity, date, dto);

			// 新增蓝单
			this.billReceivableService.addBillReceivable(blueEntity,currentInfo);
		}
	}

	/**
	 * 修改外发单、红冲原有的偏线成本应付单，生成新的蓝单数据
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午10:39:30
	 * @param entity
	 * @param dto
	 * @param date
	 * @param currentInfo
	 */
	private void writeBackBillPayableByModifyExternalBill(
			BillPayableEntity entity, SettlementExternalBillDto dto, Date date,
			CurrentInfo currentInfo) {
		//存在数据执行下面的方法
		if (entity != null&&StringUtils.isNotEmpty(entity.getId())) {
			// 红冲应付单
			this.billPayableService.writeBackBillPayable(entity, currentInfo);

			BillPayableEntity blueEntity = new BillPayableEntity();
			BeanUtils.copyProperties(entity, blueEntity);
			
			// ID
			blueEntity.setId(UUIDUtils.getUUID());
			
			//来源单号
			blueEntity.setSourceBillNo(dto.getExternalBillNo());

			// 应付单号
			if (SettlementConstants.BLUE_NEW_BILL_NO) {
				SettlementNoRuleEnum rule = SettlementNoRuleEnum
						.getByCode(entity.getPayableNo());
				blueEntity.setPayableNo(settlementCommonService
						.getSettlementNoRule(rule));
			}
			
			// 应付金额
			blueEntity.setAmount(dto.getCostAmount());
			
			// 已核销金额默认设置为0
			blueEntity.setVerifyAmount(BigDecimal.ZERO);
			
			// 未核销金额
			blueEntity.setUnverifyAmount(dto.getCostAmount());
			
			// 默认设置为人民币
			blueEntity.setCurrencyCode(dto.getCurrencyCode() != null ? dto
					.getCurrencyCode() : FossConstants.CURRENCY_CODE_RMB);
			
			// BUG-49384 红冲之后重新生成Date，使红单时间早于蓝单
			date = new Date();
			
			// 记账日期
			blueEntity.setAccountDate(date);
			
			// 设置通用参数
			setPayableCommonParam(blueEntity);
			
			// 送货费
			blueEntity.setDeliverFee(dto.getDeliveryFee());

			// 外发运费
			blueEntity.setOutgoingFee(dto.getExternalAgencyFee());// 参数中的- 外发代理费
			
			// 添加偏线成本应付单
			this.billPayableService.addBillPayable(blueEntity, currentInfo);
		}
	}

	/**
	 * 审核外发单前，进行校验规则
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-23 上午10:40:48
	 * @param dto
	 */
	private void validateParamByAuditExternalBill(SettlementExternalBillDto dto) {
		if(PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED.equals(dto.getAuditStatus())){
			//外发单已审核
			throw new SettlementException("外发单已审核");
		}
		//审核反审核验证信息
		this.validateParamsByExternalBill(dto);
	}

	/**
	 * 批量审核外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:36:14
	 * @param externalBills
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void auditExternalBill(List<SettlementExternalBillDto> externalBills,
			CurrentInfo currentInfo) {
		//判断传入参数是否为空
		if (CollectionUtils.isEmpty(externalBills)) {
			throw new SettlementException("审核外发单传入参数为空");
		}
		
		LOGGER.info("STL ---开始批量审核外发单服务");
		
		//操作日志集合
		List<OperatingLogEntity> operList=new ArrayList<OperatingLogEntity>();
		
		//循环验证外发单信息
		for (SettlementExternalBillDto dto : externalBills) {
			//外发单信息为空
			if (dto == null) {
				throw new SettlementException("外发单信息为空！");
			}
			
			LOGGER.info(" 审核单条外发单号为：" + dto.getExternalBillNo()
					+ " 外发代理编码为： " + dto.getAgentCompanyCode() + " 运单号为： "
					+ dto.getWaybillNo());
			
			// 验证外发单数据-公用验证
			this.validateParamByAuditExternalBill(dto);

			/********************************* 执行审核操作 *******************************************/

			// 查询应付单信息（应收冲应付使用、生效应付单使用）
			List<BillPayableEntity> payableList = this.queryBillPayableByCondition(dto);
			//不存在偏线外发成本应付单，不能进行审核外发单操作
			if (CollectionUtils.isEmpty(payableList)) {
				throw new SettlementException("不存在偏线外发成本应付单，不能进行审核外发单操作");
			} 
			//同一个外发单号和外发代理存在多条偏线成本应付单
			if (payableList.size() > 1) {
				throw new SettlementException("同一个外发单号和外发代理存在多条偏线成本应付单");
			}
			//取集合中的第一条数据
			BillPayableEntity billPayableEntity = payableList.get(0);
			
			//外发成本应付单如果已生效，代表外发单已经审核过，提示异常
			if(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES.equals(billPayableEntity.getEffectiveStatus())){
				//外发单已审核，不能调用审核操作
				throw new SettlementException("外发单已审核，不能调用审核操作");
			}
			
			//根据新的需求变更，应付单核销要先审核通过之后才能进行核销操作
			billPayableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE);//审核状态设置为已审核
			
			// 生效（带修改应付单的审核状态操作）应付单
			this.billPayableService.enableBillPayable(
					billPayableEntity, billPayableEntity.getSignDate(), currentInfo);

			// 查询偏线到达运费应收单信息,不为空进行（还款冲应收、应收冲应付）
			List<BillReceivableEntity> receList = this.queryBillReceivableByCondition(dto);
			
			//判断信息集合是否为空
			if (CollectionUtils.isNotEmpty(receList)) {
				if (receList.size() > 1) {
					//存在多条偏线到达运费应收单不能操作
					throw new SettlementException("存在多条偏线到达运费应收单");
				}
				BillReceivableEntity billReceivableEntity = receList.get(0);
                
				//更改了应付单的状态和版本号码，在这里查询查询应付单数据
				payableList=this.queryBillPayableByCondition(dto);
				
		        //调用应收冲应付操作
				this.writeoffBillReceivableAndBillPayable(billReceivableEntity, dto, payableList, currentInfo);
				
				/*	已生效应付单已审核应收单才能做进对账单
				 *  DEFECT-943
				 *  审核前，应收单可能会做进对账单；应付单不会。
				 *  因此审核操作，如果有应收冲应付，会影响对账单。
				 */
				modifyStatement(payableList,receList,currentInfo,"audit");
				
			}
			
			
			//审核外发单日志
			OperatingLogEntity operatingLogEntity=this.getOperatingLogEntity(dto.getExternalBillNo(), 
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT,//---审核
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PARTIAL_LINE,//偏线外发单
					currentInfo);
			operList.add(operatingLogEntity);
			
			//审核应付单日志
			OperatingLogEntity operatingLogEntityPayable=this.getOperatingLogEntity(billPayableEntity.getPayableNo(), //应付单号
					//---审核
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__AUDIT,
					//应付单
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYABLE,
					currentInfo);
			operList.add(operatingLogEntityPayable);
			
			LOGGER.info("STL 审核单条外发单服务成功！");
		}
		
		//添加日志信息
		if(CollectionUtils.isNotEmpty(operList)){
			//批量添加操作日志信息
			this.operatingLogService.addBatchOperatingLog(operList);
		}
		
		LOGGER.info("STL ---批量审核外发单服务成功！");
	}

	/**
	 * 审核外发时，应收充应付操作
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-23 下午2:42:21
	 * @param receEntity
	 * @param dto
	 * @param payableList
	 * @param currentInfo
	 */
	private void writeoffBillReceivableAndBillPayable(
			BillReceivableEntity receEntity, SettlementExternalBillDto dto,
			List<BillPayableEntity> payableList, CurrentInfo currentInfo) {
			
		    //应收单列表 
		    List<BillReceivableEntity> billReceivableEntitys=null;
		    // 当应收单的未核销金额大于0时，进行应收冲应付操作
		    if (receEntity != null
				&& receEntity.getUnverifyAmount() != null
				&& receEntity.getUnverifyAmount().compareTo(
						BigDecimal.ZERO) > 0) {
			//声明实例对象
			BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();
			billReceivableEntitys=Arrays.asList(receEntity);
			// 应收单集合
			writeoffDto.setBillReceivableEntitys(billReceivableEntitys);

			// 应付单集合
			writeoffDto.setBillPayableEntitys(payableList);
			
			// 设置核销批次号
			writeoffDto.setWriteoffBatchNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));
			
			//核销单生成方式
			writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
			
			// 调用应收冲应付服务 生成核销单方法
			this.billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
		}
	}

	/**
	 * 反审核外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:36:44
	 * @param externalBills
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void reverseAuditExternalBill(
			List<SettlementExternalBillDto> externalBills,
			CurrentInfo currentInfo) {	
		//反审核偏线外发单传入参数为空
		if (CollectionUtils.isEmpty(externalBills)) {
			throw new SettlementException("反审核偏线外发单传入参数为空");
		}
		
		LOGGER.info("开始批量反审核偏线外发单");
		
		//操作日志list
		List<OperatingLogEntity> operList=new ArrayList<OperatingLogEntity>();
		
		//循环验证单据集合
		for (SettlementExternalBillDto dto : externalBills) {
			LOGGER.info(" 反审核单条外发单号为：" + dto.getExternalBillNo()
					+ " 外发代理编码为： " + dto.getAgentCompanyCode() + " 运单号为： "
					+ dto.getWaybillNo());

			//获取当前时间
			Date date=new Date();
			
			// 验证反审核外发单参数
			this.validateParamByReverseAuditExternalBill(dto,date);
		
			//反审核单条外发单
			BillPayableEntity payableEntity =reverseAuditOneExternalBill(dto,currentInfo);

			//审核外发单日志
			OperatingLogEntity operatingLogEntity=this.getOperatingLogEntity(
					//外发单号
					dto.getExternalBillNo(), 
					//---反审核
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT,
					//偏线外发单
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PARTIAL_LINE,
					currentInfo);
			operList.add(operatingLogEntity);
			
			//审核应付单日志
			OperatingLogEntity operatingLogEntityPayable=this.getOperatingLogEntity(
					 //应付单号
					payableEntity.getPayableNo(),
					//---反审核
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__REVERSE_AUDIT,
					//应付单
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYABLE,
					currentInfo);
			operList.add(operatingLogEntityPayable);
			
			LOGGER.info("STL 反审核单条外发单服务成功！");
		}
		if(CollectionUtils.isNotEmpty(operList)){
			//批量添加操作日志信息
			this.operatingLogService.addBatchOperatingLog(operList);
		}
		
		LOGGER.info("STL 批量反审核外发单服务成功！");
	}
	
	/**
	 * 提取方法反审核单条外发单记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-20 下午1:58:12
	 * @param dto
	 * @param currentInfo
	 */
	private BillPayableEntity reverseAuditOneExternalBill(SettlementExternalBillDto dto,
			CurrentInfo currentInfo){
		
		// 查询应付单信息
		List<BillPayableEntity> payableList = this.queryBillPayableByCondition(dto);
		//不存在偏线外发成本应付单，不能进行反审核外发单操作
		if (CollectionUtils.isEmpty(payableList)) {
			throw new SettlementException("不存在偏线外发成本应付单，不能进行反审核外发单操作");
		} 
		//获取集合中的首条数据
		BillPayableEntity payableEntity = payableList.get(0);
		
		//外发成本应付单已失效，代表外发单已反审核过或者没有被审核过，提示异常
		if (SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO
				.equals(payableEntity.getEffectiveStatus())) {
			//外发单未审核，不能调用反审核操作
			throw new SettlementException("外发单未审核，不能调用反审核操作");
		}
		// 判断应付单信息
		this.validateBillEntity(payableEntity,false);
				
		
		// 判断偏线外发成本应付单是否被手工核销过
		List<BillWriteoffEntity> writeOffs = this.billWriteoffService
				.queryBillWriteoffByBeginOrEndNo(
						//应付单号
						payableEntity.getPayableNo(),
						//有效
						FossConstants.ACTIVE,
						SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		//存在手工核销偏线外发成本应付单，不能进行反审核操作！
		if (CollectionUtils.isNotEmpty(writeOffs)) {
			throw new SettlementException(
					"存在手工核销偏线外发成本应付单，不能进行反审核操作！");
		}
		
		// 反核销还款冲应收
		List<BillReceivableEntity> receivableList = this.queryBillReceivableByCondition(dto);// 应收单信息
		
		// 代表是否调用反核销应收冲应付操作
        int j = 0;
        
		// 存在偏线到达运费应收单数据
		if (CollectionUtils.isNotEmpty(receivableList)) {
			BillReceivableEntity receivableEntity = receivableList.get(0);

			// 判断偏线到达运费应收单是否被手工核销过
			writeOffs = this.billWriteoffService
					.queryBillWriteoffByBeginOrEndNo(
							//应收单号
							receivableEntity.getReceivableNo(),
							//有效
							FossConstants.ACTIVE,
							SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
			//判断集合是否为空
			if (CollectionUtils.isNotEmpty(writeOffs)) {
				//存在手工核销偏线到达运费应收单，不能进行反审核操作
				throw new SettlementException(
						"存在手工核销偏线到达运费应收单，不能进行反审核操作！");
			}
			//获取集合中的首条数据
			receivableEntity = receivableList.get(0);
			if (receivableEntity != null) {
				// 反核销(应收冲应付接口)
				this.billWriteoffService.writeBackByRecNoAndPayNo(
						receivableEntity.getReceivableNo(),
						payableEntity.getPayableNo(), currentInfo);
				j += 1;
			}
			
			
		}//应收单部分代码结束
		
		
		//j大于0，上面执行了（反应收冲应付接口，应付单数据发生变化，需要重新查询应付单数据）
		if(j>0){
			payableList = this.queryBillPayableByCondition(dto);
			//判断集合是否为空
			if (CollectionUtils.isEmpty(payableList)) {
				//不存在偏线外发成本应付单，不能进行反审核外发单操作
				throw new SettlementException("不存在偏线外发成本应付单，不能进行反审核外发单操作");
			} 
			//重新获取应付单信息
			payableEntity=payableList.get(0);
		}
		
		//审核状态-未审核
		payableEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		
		//调用失效应付单接口
		this.billPayableService.disableBillPayable(payableEntity, currentInfo);
		
		/*
		 *  DEFECT-943
		 *  反审核完偏线外发单 ，修改对账单信息；
		 *  	同时，如果已经做了对账单，不能反审核
		 *  反审核 1 无效应付单 2 反核销 （应收冲应付） 两者都不允许
		 *  
		 */
		modifyStatement(payableList,receivableList,currentInfo,"reverse");
		
		return payableEntity;
	}

	/**
	 * 反审核时，验证外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 下午3:24:39
	 * @param dto
	 */
	private void validateParamByReverseAuditExternalBill(
			SettlementExternalBillDto dto,Date date) {
		//审核反审核验证信息
		this.validateParamsByExternalBill(dto);
		
		//已反审核不能进行反审核操作
		if(PartiallineConstants.PARTIALLINE_AUDITSTATUS_BACKAUDIT.equals(dto.getAuditStatus())){
			throw new SettlementException("外发单已反审核");
		}
		
		//未审核，不能进行反审核服务
		if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_WAITINGAUDIT 
				//审核类型：待审核
				.equals(dto.getAuditStatus())
				|| PartiallineConstants.PARTIALLINE_AUDITSTATUS_UNKNOWN 
				//审核类型：未知
						.equals(dto.getAuditStatus())) {
			throw new SettlementException("外发单未审核");
		}
		
		//若外发单已被“审核”超过一个财务反操作阀值X（由基础资料设置）天
		//最大反操作时间，单位为天数
		int maxInverseDays=0;
		ConfigurationParamsEntity conParam = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
						ConfigurationParamsConstants.STL_PL_REVERSE_AUDIT_DAY,
						FossConstants.ROOT_ORG_CODE);
		
		if(conParam!=null&&StringUtils.isNotEmpty(conParam.getConfValue())
				&&StringUtils.isNumeric(conParam.getConfValue())){
			maxInverseDays=Integer.parseInt(conParam.getConfValue());
		}
		Long l=DateUtils.getTimeDiff(dto.getUpdateTime()!=null?dto.getUpdateTime():new Date(), date);
		//反审核日期超过财务最大反操作时间，系统返回
		if(l!=null&&l.intValue()>maxInverseDays){
			throw new SettlementException("反审核日期超过财务最大反操作时间，系统返回");
		}
		
		// 需求变更，对已经签收的偏线运单，不能对外发单进行反审核操作
		if(SettlementConstants.EXTEND_PARTIAL_LINE_VEHICLE_WAYBILL_IS_SING){
			WaybillSignResultEntity signEntity=new WaybillSignResultEntity();
			//运单号
			signEntity.setWaybillNo(dto.getWaybillNo());
			//是否有效-有效的签收记录只有一条
			signEntity.setActive(FossConstants.ACTIVE);
			//根据运单号查询运单签结果里的运单信息
			WaybillSignResultEntity sign=waybillSignResultService.queryWaybillSignResultByWaybillNo(signEntity);
			//运单已签收，不能进行反审核外发单操作
			if(sign!=null&&StringUtils.isNotEmpty(sign.getId())){
				throw new SettlementException("运单已签收，不能进行反审核外发单操作");
			}
		}
	}

	/**
	 * 审核反审核验证信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 下午7:40:50
	 * @param dto
	 */
	private void validateParamsByExternalBill(SettlementExternalBillDto dto) {
		//运单号不能为空
		if (StringUtils.isEmpty(dto.getWaybillNo())) {
			throw new SettlementException("运单号不能为空！");
		}
		//外发单号不能为空
		if (StringUtils.isEmpty(dto.getExternalBillNo())) {
			throw new SettlementException("外发单号不能为空！");
		}
		//外发代理编码不能为空
		if (StringUtils.isEmpty(dto.getAgentCompanyCode())) {
			throw new SettlementException("外发代理编码不能为空！");
		}
	}

	/**
	 * 作废外发单服务
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午6:36:33
	 * @param externalBills
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void disableExternalBill(
			List<SettlementExternalBillDto> externalBills,
			CurrentInfo currentInfo) {
		//验证作废偏线外发单传入参数是否为空
		if (CollectionUtils.isEmpty(externalBills)) {
			throw new SettlementException("作废偏线外发单传入参数为空");
		} 
		
		LOGGER.info(" STL ----开始批量作废外发单服务 ");
		
		//操作日志list
		List<OperatingLogEntity> operList=new ArrayList<OperatingLogEntity>();
		//循环验证外发单信息
		for (SettlementExternalBillDto dto : externalBills) {
			//外发单信息为空
			if (dto == null) {
				throw new SettlementException("外发单信息为空！");
			}
			
			LOGGER.info(" 作废单条外发单号为：" + dto.getExternalBillNo()
					+ " 外发代理编码为： " + dto.getAgentCompanyCode() + " 运单号为： "
					+ dto.getWaybillNo());
			//审核反审核验证信息
			this.validateParamsByExternalBill(dto);
			//"已审核，不能进行作废操作"
			if (PartiallineConstants.PARTIALLINE_AUDITSTATUS_HASAUDITED
					.equals(dto.getAuditStatus())) {
				throw new SettlementException("外发单号为"
						+ dto.getExternalBillNo() + "已审核，不能进行作废操作");
			}
			Date date=new Date();
			// 外发单的创建时间与当前系统时间相差超过过M天（M天为财务红冲超期天数）时，异常信息为“外发单的创建时间与当前系统时间相差超过过M天，不能作废
			int maxWriteBackDays=0;
			
			//调用综合管理接口，查询财务可红冲最大时间，单位为天
			ConfigurationParamsEntity conParam = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
							ConfigurationParamsConstants.STL_PL_WRITEBACK_DAY,
							FossConstants.ROOT_ORG_CODE);
	
			if(conParam!=null&&StringUtils.isNotEmpty(conParam.getConfValue())&&StringUtils.isNumeric(conParam.getConfValue())){
				maxWriteBackDays=Integer.parseInt(conParam.getConfValue());
			}
			//计算外发单业务日期和当前系统时间相差的天数
			Long l=DateUtils.getTimeDiff(dto.getCreateTime(), date);
			//验证时间差，过期不能操作
			if(l!=null&&l.intValue()>maxWriteBackDays){
				throw new SettlementException("外发单的创建时间与当前系统时间相差超过"+maxWriteBackDays+"天，" +
						"不能进行作废操作");
			}
			//根据运单号、外单号和外发代理编码查询应付单信息
			List<BillPayableEntity> payableList = this.queryBillPayableByCondition(dto);
			//验证是否为空
			if (CollectionUtils.isEmpty(payableList)) {
				//不存在外发成本应付单信息，不能进行作废外发单服务操作
				throw new SettlementException("不存在外发成本应付单信息，不能进行作废外发单服务操作");
			} 
			//获取集合中的首条数据
			BillPayableEntity payableEntity = payableList.get(0);
			
			// 验证应付单信息---后续需要查询付款单信息，故第三个参数默认为true
			this.validateBillEntity(payableEntity, true);

			// 作废红冲应付单信息
			this.billPayableService.writeBackBillPayable(payableEntity,currentInfo);

			// 红冲偏线到达运费应收单，生成到付运费应收单信息
			List<BillReceivableEntity> resList=this.writebackBillReceivableByDisableExternalBill(dto,currentInfo);
			
			
			/*
			 *  DEFECT-943
			 *  作废完偏线外发单 ，修改对账单信息；
			 *  	同时，如果已经做了对账单，不能作废
			 */
			modifyStatement(payableList,resList,currentInfo,"disable");

			
			//作废外发单日志
			OperatingLogEntity operatingLogEntity=this.getOperatingLogEntity(dto.getExternalBillNo(), 
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE,//---作废
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__PARTIAL_LINE,//偏线外发单
					currentInfo);
			operList.add(operatingLogEntity);
			
			//作废应付单日志
			OperatingLogEntity operatingLogEntityPayable=this.getOperatingLogEntity(
					 //应付单号
					payableEntity.getPayableNo(),
					//---作废
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE,
					//应付单
					SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_PAYABLE,
					currentInfo);
			operList.add(operatingLogEntityPayable);
			
			LOGGER.info(" STL------------------作废单条外发单成功！ ");
		}
		
		if(CollectionUtils.isNotEmpty(operList)){
			//批量添加操作日志信息
			this.operatingLogService.addBatchOperatingLog(operList);
		}
		
		LOGGER.info(" STL------------------作废批量外发单服务成功！ ");
	}

	/**
	 * 红冲偏线到达运费应收单，生成到付运费应收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-26 下午8:50:31
	 * @param dto
	 * @param currentInfo
	 */
	private List<BillReceivableEntity> writebackBillReceivableByDisableExternalBill(
			SettlementExternalBillDto dto, CurrentInfo currentInfo) {
		// 查询应收单信息
		List<BillReceivableEntity> receList = this.queryBillReceivableByCondition(dto);
		//验证集合是否为空
		if (CollectionUtils.isNotEmpty(receList)) {
			//获取集合中的首条数据
			BillReceivableEntity receEntity = receList.get(0);
			Date date = new Date();
			//验证应收单
			this.validateBillReceivable(receEntity, date);

			// 红冲应收单
			this.billReceivableService.writeBackBillReceivable(receEntity,
					currentInfo);

			// 生成到付运费应收单
			BillReceivableEntity blueEntity = new BillReceivableEntity();
			BeanUtils.copyProperties(receEntity, blueEntity);
			// 设置Id
			blueEntity.setId(UUIDUtils.getUUID());
			//设置应收单号
			blueEntity.setReceivableNo(this.settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.YS2));
			//设置航空正单号
			blueEntity.setSourceBillNo(dto.getWaybillNo());
			//设置正单类型
			blueEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
			//设置单据类型
			blueEntity.setBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);

			// 设置客户信息为空
			blueEntity.setCustomerCode("");
			// 设置客户信息为空
			blueEntity.setCustomerName("");
			
			// 设置通用参数
			this.setReceivableCommonParam(blueEntity);

			//生成日期
			blueEntity.setCreateDate(date);
			//业务日期
			blueEntity.setAccountDate(date);
			
			// BUG-40150 092036-foss-bochenlong 
			// 因为迁移数据是月结付款方式，影响弃货处理
			// 付款方式 设置为到付
			blueEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
			
			//调用新增应收单接口
			this.billReceivableService.addBillReceivable(blueEntity,currentInfo);
		}
		return receList;
	}

	/******************************************************* 验证各种单据公用代码 ***************************************/
	/**
	 * 验证应收单
	 * 有void方法改为存在返回值的，为了简化if嵌套，提高程序的可读性
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-20 下午6:42:18
	 * @param entity
	 * @param date
	 */
	private boolean validateBillReceivable(BillReceivableEntity entity, Date date) {
		//不存在应收单信息，直接返回true
		if (entity == null) {
			return true;
		}
		
		// 当应收单类型为：到付运费应收单时，判断应收单的客户必须为到付客户，也就是客户编码为空
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
				.equals(entity.getBillType())) {
			if (StringUtils.isNotEmpty(entity.getCustomerCode())) {
				
				// 判断是否为具体客户，是具体客户抛出异常信息
				CustomerDto custDto = customerService.queryCustInfoByCode(entity.getCustomerCode());
				if (custDto != null) {
					//到付应收单的挂账客户不是到付客户
					throw new SettlementException("到付应收单的挂账客户不是到付客户");
				}
			}
		}

		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null
				&& date.before(entity.getUnlockDateTime())) {
			//应收单已被锁定
			throw new SettlementException("应收单已被锁定");
		}
		if (entity.getVerifyAmount() != null
				&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			//应收单已经被核销
			throw new SettlementException("应收单已经被核销");
		}	
		return true;
	}

	/**
	 * 验证应付单（修改和作废、反审核外发单使用）
	 * 
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-27 上午8:05:46
	 * @param payableEntity
	 * @param bl 为true代表需要验证已核销金额信息
	 */
	private void validateBillEntity(BillPayableEntity payableEntity, boolean bl) {
		if (payableEntity != null) {
			
			// 在反审核时bl传入值：false不需要进行下面if中的判断
			if (bl) {
				//偏线外发成本应付单已核销
				if (payableEntity.getVerifyAmount() != null
						&& payableEntity.getVerifyAmount().compareTo(
								BigDecimal.ZERO) > 0) {
					throw new SettlementException("偏线外发成本应付单已核销");
				}
				//偏线外发成本应付单已审核
				if (SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__AUDIT_AGREE.
						equals(payableEntity.getApproveStatus())){
					throw new SettlementException("偏线外发成本应付单已审核");
				}
			}
			
			//判断偏线成本应付单是否已经冻结
			if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN
					.equals(payableEntity.getFrozenStatus())) {
				throw new SettlementException("偏线外发成本应付单已冻结");
			}
			//偏线外发成本应付单正在付款中或已付款
			if(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.
					equals(payableEntity.getPayStatus())){
				throw new SettlementException("偏线外发成本应付单正在付款中或已付款");
			}
		}
	}
	
	/**
	 * 根据来源单号（外发单号），查询对应的偏线代理应收单
	 * 
	 * @author 092036-foss-bochenlong
	 * @date 修改于2013-07-24 下午3:39:00
	 * @param dto
	 * @return List<BillReceivableEntity>
	 */
	private List<BillReceivableEntity> queryBillReceivableByCondition(SettlementExternalBillDto dto) {
		// 声明实例dto对象
		BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(dto.getWaybillNo());
		
		// 092036-foss-bochenlong 增加对是否迁移数据判断
		if(dto.getIsInit().equals(FossConstants.YES)) {
			// 如果是迁移数据，来源单号为waybillNo运单号
			receiConDto.setSourceBillNo(dto.getWaybillNo());
		} else {
			// 不是，则来源单号为externalBillNo外发单号
			receiConDto.setSourceBillNo(dto.getExternalBillNo());
		}
		// 设置代理
		receiConDto.setPartailLineAgentCode(dto.getAgentCompanyCode());
		// 设置单据类型
		receiConDto.setBillTypes(new String[] { 
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE // 偏线代理应收单
				});
		//根据传入的运单号和单据类型等参数，查询[偏线代理运费应收单]有效应收单信息可公用
		List<BillReceivableEntity> receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);
		return receList;
	}
	
	/**
	 * 根据来源单号（运单），查询对应的到达应收单
	 * 
	 * @author 092036-foss-bochenlong
	 * @date 修改于2013-07-24 下午3:39:00
	 * @param dto
	 * @return List<BillReceivableEntity>
	 */
	private List<BillReceivableEntity> queryBillReceivableByWayBillNo(SettlementExternalBillDto dto) {
		// 声明实例dto对象
		BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(dto.getWaybillNo());
		
		// 设置来源单号
		receiConDto.setSourceBillNo(dto.getWaybillNo());
		
		// 设置单据类型
		receiConDto.setBillTypes(new String[] { 
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE // 到付运费应收单
				});
		//根据传入的运单号和单据类型等参数，查询[到付运费应收单]有效应收单信息可公用
		List<BillReceivableEntity> receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);
		return receList;
	}

	/**
	 * 根据运单号、外单号和外发代理编码查询应付单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-23 上午11:20:19
	 * @param dto
	 * @return
	 */
	private List<BillPayableEntity> queryBillPayableByCondition(
			SettlementExternalBillDto dto) {
		//声明实例对象
		BillPayableConditionDto payableConDto = new BillPayableConditionDto();
		//是否是迁移数据
		if(dto.getIsInit().equals("Y")){
			//运单号
			payableConDto.setWaybillNo(dto.getWaybillNo());
		}
		// 外发单号
		payableConDto.setSourceBillNo(dto.getExternalBillNo());
		payableConDto.setPartailLineAgentCode(dto.getAgentCompanyCode());
		// 偏线成本应付单
		payableConDto.setBillTypes(new String[] { 
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__PARTIAL_LINE 
				});
		//根据运单号和应付单类型集合，获取一到多条应付单信息
		List<BillPayableEntity> payableList = this.billPayableService
				.queryBillPayableByCondition(payableConDto);
		//同一个外发代理和外发单号，存在多条外发成本应付单
		if(CollectionUtils.isNotEmpty(payableList)&&payableList.size()>1){
			throw new SettlementException("同一个外发代理和外发单号，存在"+payableList.size()+"条外发成本应付单");
		}
		return payableList;
	}

	/**
	 * 获取操作日志信息
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-25 下午3:09:42
	 * @param operateBillNo
	 * @param operateType
	 * @param operateBillType
	 * @param currentInfo
	 * @return
	 */
	private OperatingLogEntity getOperatingLogEntity(String operateBillNo, String operateType,
			String operateBillType,CurrentInfo currentInfo){
		// 记录审核日志信息
		OperatingLogEntity item = new OperatingLogEntity();
		// ID
		item.setId(UUIDUtils.getUUID());
		// 外发单
		item.setOperateBillNo(operateBillNo);
		// 操作单据类型
		item.setOperateBillType(operateBillType);
		// 操作部门编码
		item.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		// 操作部门名称
		item.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 操作人编码
		item.setOperatorCode(currentInfo.getEmpCode());
		// 操作人名称
		item.setOperatorName(currentInfo.getEmpName());
		item.setOperateTime(new Date());
		item.setOperateType(operateType);
		item.setNotes("操作偏线外发单");
		return item;
	}
	
	/**
	 * 验证参数
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-19 上午9:40:38
	 * @param dto
	 */
	private void validateParams(SettlementExternalBillDto dto) {
		this.validateParamsByExternalBill(dto);
		
		//因为中转也会查询运单信息，故下列这些运单中存在的属性有中转传给结算系统
		//运单Id不能为空
		validaSettlement(dto);
		
		//由于中转业务外发代理费为必填项，无外发代理费时默认为0
		//输入的外发代理费不能小于0
		if(dto.getExternalAgencyFee()!=null&&dto.getExternalAgencyFee().
				compareTo(BigDecimal.ZERO)<0){
			throw new SettlementException("输入的外发代理费不能小于0");
		}
		if(dto.getExternalAgencyFee()!=null&&dto.getExternalAgencyFee().
				compareTo(dto.getCostAmount())>0){
			throw new SettlementException("输入的外发代理费："+dto.getExternalAgencyFee()+"大于"+
				"外发成本总额："+dto.getCostAmount()
					);
		}
		
		//由于中转业务代理送货费为必填项，无代理送货费时默认为0
		//输入的代理送货费不能小于0
		if(dto.getExternalAgencyFee()!=null&&dto.getDeliveryFee().
				compareTo(BigDecimal.ZERO)<0){
			throw new SettlementException("输入的代理送货费不能小于0");
		}
		if(dto.getDeliveryFee()!=null&&dto.getDeliveryFee().
				compareTo(dto.getCostAmount())>0){
			throw new SettlementException("输入的代理送货费："+dto.getDeliveryFee()+"大于"+
				"外发成本总额："+dto.getCostAmount()
					);
		}
		//外发成本总额必须等于（外发代理费和代理送货费相加之和）
		if (dto.getCostAmount().compareTo(
				// 外发代理费
				NumberUtils.add(dto.getExternalAgencyFee(),
						// 代理送货费
						dto.getDeliveryFee())) != 0) {
			throw new SettlementException("外发成本总额必须等于（外发代理费和代理送货费相加之和）");
		}
	}

	private void validaSettlement(SettlementExternalBillDto dto) {
		if(StringUtils.isEmpty(dto.getWaybillId())){
			throw new SettlementException("运单Id不能为空");
		}
		//出发部门不能为空
		if(StringUtils.isEmpty(dto.getReceiveOrgCode())){
			throw new SettlementException("出发部门不能为空");
		}
		//外发部门不能为空
		if(StringUtils.isEmpty(dto.getWaifabumen())){
			throw new SettlementException("外发部门不能为空");
		}
		//外发部门不能为空
		if(StringUtils.isEmpty(dto.getWaifabumenName())){
			throw new SettlementException("外发部门不能为空");
		}
		//运单的最终配载部门不能为空
		if(StringUtils.isEmpty(dto.getLastLoadOrgCode())){
			throw new SettlementException("运单的最终配载部门不能为空");
		}
		//运单的总费用不能为空!
		if(dto.getTotalFee()==null){
			throw new SettlementException("运单的总费用不能为空!");
		}
		//外发代理编码不能为空！
		if (StringUtils.isEmpty(dto.getAgentCompanyCode())) {
			throw new SettlementException("外发代理编码不能为空！");
		}
		//外发代理名称不能为空！
		if (StringUtils.isEmpty(dto.getAgentCompanyName())) {
			throw new SettlementException("外发代理名称不能为空！");
		}
		
		//外发单中的金额信息校验
		if (dto.getCostAmount() == null) {
			throw new SettlementException("外发成本总额不能为空！");
		}
		//输入的外发成本总额必须大于0
		if (dto.getCostAmount().compareTo(BigDecimal.ZERO) <= 0) {
			throw new SettlementException("输入的外发成本总额必须大于0");
		}
	}

	/**
	 * 根据传入的外发单的费用信息和运单的费用信息进行比较 【录入外发单和修改外发单都需要】
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-21 上午11:01:22
	 * @param dto
	 */
	private void validateExternalBillFeeByWaybill(
			SettlementExternalBillDto dto) {
		// 当外发单中包含有送货费大于0时，需要查看对应的运单是否有送货费
		if (dto.getDeliveryFee() != null&&dto.getDeliveryFee().compareTo(BigDecimal.ZERO)>0&&
				// 运单的送货费为空时
				dto.getDeliveryGoodsFee() == null
				) {
			throw new SettlementException("开单不送货，不允许录入送货费");
		}
		if (dto.getCostAmount() != null
				&& dto.getCostAmount().compareTo(BigDecimal.ZERO) > 0) {

			// 开单费用：总费用减去代收货款费用
			BigDecimal waybillFee = dto.getTotalFee().subtract(
					dto.getCodAmount() != null ? dto.getCodAmount()
							: BigDecimal.ZERO);
			BigDecimal n = null;//外发成本倍数变量
			
			//综合管理配置的：外发成本总额小于等于开单金额的倍数
			ConfigurationParamsEntity conParam = configurationParamsService
					.queryConfigurationParamsByOrgCode(
							DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
							ConfigurationParamsConstants.STL_PL_FEE_RATIO,
							FossConstants.ROOT_ORG_CODE);
	
			if(conParam!=null&&StringUtils.isNotEmpty(conParam.getConfValue())){
				n=new BigDecimal(conParam.getConfValue());
				
				//偏线外发费用不能大于运单开单金额的*倍
				if (dto.getCostAmount().compareTo(waybillFee.multiply(n)) > 0) {
					throw new SettlementException("提示“外发代理成本比开单金额大" + n
							+ "倍，不允许录入外发单”");
				}
			}else{
				//请在综合管理配置偏线运费比率
				throw new SettlementException("请在综合管理配置偏线运费比率");
			}
		}
	}
	
	private void setReceivableCommonParam(BillReceivableEntity recEntity){
		// 设置对账单号N/A
		recEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 是否有效
		recEntity.setActive(FossConstants.ACTIVE);
		
		// 是否红单
		recEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		// 是否初始化
		recEntity.setIsInit(FossConstants.NO);
		
		//版本号默认为1
		recEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		// 生成方式：系统生成
		recEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
	}
	
	private void setPayableCommonParam(BillPayableEntity payEntity){
		// 生效状态-未生效
		payEntity.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__NO);

		// 冻结状态-未冻结
		payEntity.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		
		//审核状态-默认为未审核
		payEntity.setApproveStatus(SettlementDictionaryConstants.BILL_PAYABLE__APPROVE_STATUS__NOT_AUDIT);
		
		// 支付状态-未支付
		payEntity.setPayStatus(SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO);
		
		//付款金额为空
		payEntity.setPaymentAmount(null);

		// 对账单号设置为N/A
		payEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		//是否有效
		payEntity.setActive(FossConstants.ACTIVE);
		
		// 是否红单
		payEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		
		// 是否初始化
		payEntity.setIsInit(FossConstants.NO);
		
		// 版本号
		payEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		
		//付款单号-设置为N/A
		payEntity.setPaymentNo(SettlementConstants.DEFAULT_BILL_NO);
	}

	
	
	/**
	 * 根据应付单、应收单参数修改对账单
	 * 	1 作废偏线外发单时，修改对账单
	 *  2 审核偏线外发单时，修改对账单
	 *  3 反审核偏线外发单时，修改做账单
	 * @author 095793-foss-LiQin
	 * @date 2013-7-31 下午4:41:29
	 * @param payableList
	 * @param receList
	 * @param cinfo
	 */
	private void modifyStatement(List<BillPayableEntity> payableEntities,
			List<BillReceivableEntity> receivableEntities, CurrentInfo cInfo ,String type) {
		// 生成未确认对账单的应收单列表
		List<BillReceivableEntity> noConfirmReceivableEntityList = new ArrayList<BillReceivableEntity>();
		// 生成未确认对账单的应付单列表
		List<BillPayableEntity> noConfirmPayableEntityList = new ArrayList<BillPayableEntity>();
		
		if (CollectionUtils.isNotEmpty(payableEntities)) {
			for (BillPayableEntity payableEntity : payableEntities) {
				// 根据对账单号查询对账单
				StatementOfAccountEntity stateEntity = statementOfAccountService
						.queryByStatementNo(payableEntity.getStatementBillNo());
				// 如果查询不到对账单，抛出业务异常
				if (stateEntity != null) {
					// 如果对账单是未确认状态
					if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
							.equals(stateEntity.getConfirmStatus())) {
						// 加入到未确认对账单应收单列表
						noConfirmPayableEntityList.add(payableEntity);
					} else {
						throw new SettlementException(
								"对账单已经确认，不能进行审核、反审核或作废操作！对账单:"+ stateEntity.getStatementBillNo()
										+ "应付单:" + payableEntity.getPayableNo()+ "外发单号:"+ payableEntity.getSourceBillNo());
					}
				}
			}
		}

		if (CollectionUtils.isNotEmpty(receivableEntities)) {
			for (BillReceivableEntity receivableEntity : receivableEntities) {
				// 根据对账单号查询对账单
				StatementOfAccountEntity stateEntity = statementOfAccountService
						.queryByStatementNo(receivableEntity.getStatementBillNo());
				// 如果查询不到对账单，抛出业务异常
				if (stateEntity != null) {
					// 如果对账单是未确认状态
					if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM
							.equals(stateEntity.getConfirmStatus())) {
						// 加入到未确认对账单应收单列表
						noConfirmReceivableEntityList.add(receivableEntity);
					} else {
						throw new SettlementException(
								"对账单已经确认，不能进行审核、反审核或作废操作！对账单:"+ stateEntity.getStatementBillNo()
										+ "应收单:"+ receivableEntity.getReceivableNo()+ "外发单号:"+ receivableEntity.getSourceBillNo());
					}
				}
			}

			// 反核销完成，对于未确认的子单据，通知修改对账单及对账单明细信息
			StatementOfAccountUpdateDto statementOfAccountUpdateDto = new StatementOfAccountUpdateDto();
			statementOfAccountUpdateDto
					.setReceivableEntityList(noConfirmReceivableEntityList);
			statementOfAccountUpdateDto
					.setPayableEntityList(noConfirmPayableEntityList);

			// 如果作废，则调用红冲或作废时，修改对账单及对账单明细信息
			if (type.equals("disable")) {
				statementOfAccountService.updateStatementAndDetailForRedBack(
						statementOfAccountUpdateDto, cInfo);
			} else if (type.equals("audit")) {
				statementOfAccountService.updateStatementAndDetailForWriteOff(
						statementOfAccountUpdateDto, cInfo);
			} else if (type.equals("reverse")) {
				statementOfAccountService
						.updateStatementAndDetailForBackWriteOff(
								statementOfAccountUpdateDto, cInfo);
			}

		}
	}
	
	/**
	 * <p>查询运单号对应有效偏线外发单代理编号 处理为键值对的形式</p> 
	 * @author 105762
	 * @date 2014-3-4 上午9:38:23
	 * @param waybillNos
	 * @return
	 * @see
	 */
	public Map<String,List<ExternalBillDto>> getWaybillPartialAgencyCode(List<String> waybillNos){
		if(CollectionUtils.isEmpty(waybillNos)){
			throw new SettlementException("内部错误，运单号参数为空");
		}
		//按单号查询偏线外发单
		List<ExternalBillDto> list = externalBillService.queryExternalBillByWaybillNos(waybillNos);
		if(CollectionUtils.isEmpty(list)){
			throw new SettlementException("未查询到运单号对应有效外发单");
		}
		//返回值 运单 代理 键值对
		Map<String,List<ExternalBillDto>> map = new HashMap<String,List<ExternalBillDto>>();
		for(ExternalBillDto dto:list){
			List<ExternalBillDto> l = new ArrayList<ExternalBillDto>();
			if(map.containsKey(dto.getWaybillNo())){
				l = map.get(dto.getWaybillNo());
				l.add(dto);
				map.put(dto.getWaybillNo(),l);
			} else {
				l.add(dto);
				map.put(dto.getWaybillNo(),l);
			}
		}
		return map;
	}
	
	/**
	 * @param settlementCommonService the settlementCommonService to set
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	
	/**
	 * @param billReceivableService the billReceivableService to set
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	
	/**
	 * @param billPayableService the billPayableService to set
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	
	/**
	 * @param billWriteoffService the billWriteoffService to set
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	
	/**
	 * @param customerService the customerService to set
	 */
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	
	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	
	/**
	 * @param operatingLogService the operatingLogService to set
	 */
	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	
	/**
	 * @param configurationParamsService the configurationParamsService to set
	 */
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	
	/**
	 * @param waybillSignResultService the waybillSignResultService to set
	 */
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}

	/**
	 * @return statementOfAccountService
	 */
	public IStatementOfAccountService getStatementOfAccountService() {
		return statementOfAccountService;
	}

	/**
	 * @param statementOfAccountService
	 */
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	/**
	 * @return reverseBillWriteoffService
	 */
	public IReverseBillWriteoffService getReverseBillWriteoffService() {
		return reverseBillWriteoffService;
	}

	/**
	 * @param reverseBillWriteoffService
	 */
	public void setReverseBillWriteoffService(
			IReverseBillWriteoffService reverseBillWriteoffService) {
		this.reverseBillWriteoffService = reverseBillWriteoffService;
	}

	/**
	 * @param externalBillService the externalBillService to set
	 */
	public void setExternalBillService(IExternalBillService externalBillService) {
		this.externalBillService = externalBillService;
	}
	
}
