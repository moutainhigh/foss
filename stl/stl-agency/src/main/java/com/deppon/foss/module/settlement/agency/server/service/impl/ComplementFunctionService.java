package com.deppon.foss.module.settlement.agency.server.service.impl;

import java.math.BigDecimal;
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
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressPartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ExpressPartSalesDeptResultDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IActualFreightService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillExpressService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillExpressEntity;
import com.deppon.foss.module.settlement.agency.api.server.service.IComplementFunctionService;
import com.deppon.foss.module.settlement.agency.api.shared.dto.SettlementComplementBillDto;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IFinanceOnlinePayWSProxy;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillClaimEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillClaimService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IWaybillPickupService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillPickupInfoDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentComplementAutoDisableAndCreateService;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 补码功能财务接口调用
 * @author foss-guxinhua
 * @date 2013-7-29 下午2:39:20
 */
public class ComplementFunctionService implements IComplementFunctionService{

	/**
	 * 日志 logger
	 */
	private static final Logger logger = LogManager.getLogger(ComplementFunctionService.class);

	/**
	 * 应收单Service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 应付单Service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 现金收款单公共服务实现类
	 */
	private IBillCashCollectionService billCashCollectionService;

	/**
	 * 对账单服务
	 */
	private IStatementOfAccountService statementOfAccountService;

	/**
	 * 结算通用Common Service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 快递代理点部映射
	 */
	private IExpressPartSalesDeptService expressPartSalesDeptService;

	/**
	 * 运单服务
	 */
	private IActualFreightService actualFreightService;
	/**
	 * 组织服务
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	private IWaybillExpressService waybillExpressService;

	private IWaybillPickupService waybillPickupService;

	private ISaleDepartmentService saleDepartmentService;

	private IConfigurationParamsService configurationParamsService;

	private IWaybillManagerService waybillManagerService;
	
	/**
	 * @author 268217
	 * 注入理赔单服务Service
	 */
	private IBillClaimService billClaimService;
	
	/**
	 * 还款单
	 * 2016-10-03 231434
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 悟空补码时，到达应收网上支付还款单处理
	 */
	private IBillRepaymentComplementAutoDisableAndCreateService billRepaymentComplementAutoDisableAndCreateService;
	
	/**
	 * 业务互斥锁服务
	 */
	private IBusinessLockService businessLockService;
	
	/**
     * 网上支付处理代理
     */
    private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;

	/**
	 * 校验是否可以补码
	 * 1、存在多条相同类型应收、应付单，不允许补码
	 * 2、应收、应付已核销金额大于0，不允许补码（始发应收除外）
	 * 3、应收、应付已确认对账单不允许补码（始发应收除外）
	 * 4、应付单已审核，不允许补码
	 * 5、应收单已锁定，不允许补码
	 * 6、现金收款单记账日期超过30天，不允许补码
	 * @param foss-231434-bieyexiong
	 * @date 2016-6-01 下午3:27
	 */
	@Override
	public String checkComplementFunction(SettlementComplementBillDto dto) {
		logger.info("补码校验财务接口 开始.");
		if(dto == null){
			throw new SettlementException("校验参数为空！"); 
		}
		//配置参数-合伙人结清4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人结清流程
		String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
		long intTime = 0;
		if (StringUtils.isNotBlank(configString)) {
			intTime = DateUtils.convert(configString.trim(), DateUtils.DATE_TIME_FORMAT).getTime();
		}
		if(dto.getBillTime() == null){
			throw new SettlementException("开单时间为空！"); 
		}
		//开单时间
		long destTime = dto.getBillTime().getTime();
		//始发营业部
		SaleDepartmentEntity orgEntity = saleDepartmentService.querySaleDepartmentInfoByCode(dto.getReceiveOrgCode());
		//到达营业部
        SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(dto.getDestOrgCode());
        
        Date now = new Date();
        
        //应收单单据子类型
        String[] receiveBillTypes = null;
        
		//根据不同补码场景做不同校验
        if (null != entity && null != orgEntity  
        		&& ((!FossConstants.YES.equals(entity.getIsLeagueSaleDept()) 
        				&& !FossConstants.YES.equals(orgEntity.getIsLeagueSaleDept()))
        				|| (destTime < intTime))) {
        	logger.info("调用补码校验财务接口 补码后部门不是合伙人.");
        	receiveBillTypes = new String[SettlementReportNumber.THREE];
        	//到付应收、始发应收、代收货款应收
        	receiveBillTypes[0] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
        	receiveBillTypes[1] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE;
        	receiveBillTypes[2] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE;
        	
        }else if(null != entity && FossConstants.YES.equals(entity.getIsLeagueSaleDept()) 
        		&& null != orgEntity && !FossConstants.YES.equals(orgEntity.getIsLeagueSaleDept())
        		&& destTime > intTime){
        	logger.info("调用补码校验财务接口 补码后部门是合伙人，收货部门不是合伙人.");
            receiveBillTypes = new String[1];
            //始发应收
            receiveBillTypes[0] = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE;
            
        }
        //根据下面补码场景，不满足上述2种场景时，不用红冲任何单据
        if(receiveBillTypes != null){
        	validaType(dto, now, receiveBillTypes);
        }
        
        logger.info("补码校验财务接口 结束.");

        return null;
	}

	private void validaType(SettlementComplementBillDto dto, Date now,
			String[] receiveBillTypes) {
		//校验应收单
		//应收单查询实体
		BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(dto.getWaybillNo());
		receiConDto.setBillTypes(receiveBillTypes);
		
		// 根据传入的运单号和单据类型等参数，查询到付运费应收单信息可公用
		List<BillReceivableEntity> receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);
		
		if(!CollectionUtils.isEmpty(receList)){
			// 验证同一个外发单和代理编码是否存在相同类型的多个应收单
			this.billReceivableService.validateWaybillForBillReceivable(receList);
			for(BillReceivableEntity receEntity : receList){
				// 验证应收单(应收单锁定、应收单核销、对账单确认)
				this.checkBillReceivable(receEntity,now);
			}
		}
		
		//校验应付单
		//应付单查询实体
		BillPayableConditionDto payConDto = new BillPayableConditionDto(dto.getWaybillNo());
		String[] payBillTypes = new String[2];
		//代收货款应付、装卸费应付
		payBillTypes[0] = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD;
		payBillTypes[1] = SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE;
		
		// 根据传入的运单号和单据类型等参数，查询到付运费应付单信息可公用
		List<BillPayableEntity> payList = this.billPayableService.queryBillPayableByCondition(payConDto);
		
		if(!CollectionUtils.isEmpty(payList)){
			// 验证同一个外发单和代理编码是否存在相同类型的多个应付单
			this.billPayableService.validateWaybillForBillPayable(payList);
			for(BillPayableEntity payEntity : payList){
				this.checkBillPayable(payEntity,now);
			}
		}
		
		//校验现金收款单
		// 根据传入的运单号和单据类型等参数，查询现金收款单
		List<BillCashCollectionEntity> cashCollectionlist = billCashCollectionService.queryBySourceBillNOs(Arrays.asList(dto.getWaybillNo()),
				SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL, FossConstants.ACTIVE);
		if (CollectionUtils.isNotEmpty(cashCollectionlist)) {
			// 验证验证一个运单是否存在多条现金收款单
			this.billCashCollectionService.validateWaybillForBillCashCollection(cashCollectionlist);
			BillCashCollectionEntity cashEntity = cashCollectionlist.get(0);
			
			//调用综合管理接口，查询结算业务最大红冲天数
			String span = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_CASH_COLLECTION_WRITEBACK_DAY);
			//根据财务的记账日期和配置参数相加
			Date d = DateUtils.addDayToDate(cashEntity.getAccountDate(), Integer.valueOf(span));
			
			// 如果时间差超过30天
			if (now.after(d)) {
				throw new SettlementException("超出结算业务红冲的最大时间范围，不允许进行红冲现金收款单操作");
			}
		}
	}

	/**
	 * 快递补码
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。
	 * @author foss-231434-bieyexiong
	 * @date 2016-6-4 下午2:48
	 */
	@Transactional
	@Override
	public void ecsComplementFunctionWriteBackAndCreateReceivable(
			SettlementComplementBillDto dto, CurrentInfo currentInfo) {
		//对外部系统接口，加事物控制，所以加个方法
		this.complementFunctionWriteBackAndCreateReceivable(dto, currentInfo);
	}
	
	/**
	 * 补码功能财务接口调用：如果运单存在应收虚拟网点的到付运费或者代收货款费用，且最终网点信息为自有网点，则需要调用结算接口，
	 * 红冲虚拟网点的应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。.
	 * ISSUE-4333
	 * 1.当运单补码为快递代理，则需要调用结算接口，红冲代收货款应付单、现金收款单、始发应收单生成最新代收货款应付单、现金收款单、始发应收单的到达部门。
	 * 2.若在补码之前，始发应收已经还款核销，则在补码时提示：请联系出发部门反核销后，再进行补码；补码完成后，请联系出发部门再做还款和核销操作。
	 * 始发应收单据补码时，需要判断对账单是否已确认。对账单已确认不能进行补码操作。
	 * @param dto the dto
	 * @param currentInfo the current info
	 * @author foss-guxinhua
	 * @date 2013-7-29 下午2:40:03
	 */
	@Override
	public void complementFunctionWriteBackAndCreateReceivable(
			SettlementComplementBillDto dto, CurrentInfo currentInfo) {

		logger.info("调用补码财务接口 开始.");
		//配置参数-合伙人结清4.10上线前运单走以前逻辑;  读取配置参数日期开关;  如果开关日期不为空，则日期之前走原反签收流程；日期之后走合伙人结清流程
		//这个是老接口
		String configString = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
		WaybillEntity waybillEntity = waybillManagerService.queryPartWaybillByNo(dto.getWaybillNo());
		long intTime = 0;
		if (StringUtils.isNotBlank(configString)) {
			/*SimpleDateFormat sdf = new SimpleDateFormat(SignConstants.PTP_INIT_DATE_410);
			long intTime = 0;
			try {
				intTime = sdf.parse(configString.trim()).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}*/
			intTime = DateUtils.convert(configString.trim(), DateUtils.DATE_TIME_FORMAT).getTime();
		}
		long destTime = 0;
		String receiveOrgCode = "";
		if(waybillEntity != null){
			destTime = waybillEntity.getBillTime().getTime();
			receiveOrgCode = waybillEntity.getReceiveOrgCode();
		}else{
			destTime = dto.getBillTime().getTime();
			receiveOrgCode = dto.getReceiveOrgCode();
		}
		//查询理赔单中是否有数据268217
		BillClaimEntity claim=billClaimService.queryBillClaimEntity(dto.getWaybillNo());
		if(claim != null && SettlementDictionaryConstants.CLAIMSWAY_ONLINE.equals(claim.getClaimType())){
			throw new SettlementException("该单存在理赔应付单，不能进行补码！");
		}
		
        if (dto != null && StringUtil.isNotBlank(dto.getDestOrgCode())) {
			SaleDepartmentEntity orgEntity = saleDepartmentService.querySaleDepartmentInfoByCode(receiveOrgCode);
            SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(dto.getDestOrgCode());
            //update by 231434-bieyexiong 当部门到达部门不为营业部时，肯定不是加盟网点，即非合伙人
            if ((entity == null && !FossConstants.YES.equals(dto.getIsFreeSite()))
            		|| (null != entity && null != orgEntity  && ((!FossConstants.YES.equals(entity.getIsLeagueSaleDept()) && !FossConstants.YES.equals(orgEntity.getIsLeagueSaleDept()))
            				|| (destTime < intTime)))) {
                validaSettle(dto, currentInfo);

            }else if(null != entity && FossConstants.YES.equals(entity.getIsLeagueSaleDept()) 
            			&& null != orgEntity && !FossConstants.YES.equals(orgEntity.getIsLeagueSaleDept())
            			&& destTime > intTime){
                validaComplement(dto, currentInfo);
            }
        }else{
        	logger.info("补码信息有误！");
        }
		logger.info("调用补码财务接口 结束.");
	}

	private void validaComplement(SettlementComplementBillDto dto,
			CurrentInfo currentInfo) {
		logger.info("调用补码财务接口 补码后部门是合伙人，收货部门不是合伙人.");
		this.validateSettlementComplementBillDto(dto);

		//ddw，通过新单查询原单
		WaybillExpressEntity waybills = waybillExpressService.queryWaybillByWaybillNo(dto.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		if (null != waybills && StringUtil.isNotBlank(waybills.getOriginalWaybillNo())
		        && StringUtil.isNotBlank(dto.getDestOrgCode())
		        && StringUtil.isNotBlank(dto.getDestOrgName())) {
		    logger.info("调用补码财务接口 红冲返货应收单开始.");
		    //运单对象
		    WaybillPickupInfoDto waybillPickupInfoDto = new WaybillPickupInfoDto();
		    //原单单号
		    waybillPickupInfoDto.setWaybillNo(waybills.getOriginalWaybillNo());
		    //到达部门编码
		    waybillPickupInfoDto.setLastLoadOrgCode(dto.getDestOrgCode());
		    //到达部门名称
		    waybillPickupInfoDto.setLastLoadOrgName(dto.getDestOrgName());
		    //红冲原单
		    waybillPickupService.returnedGoodsWriteoffReceivable(waybillPickupInfoDto, currentInfo);
		    logger.info("调用补码财务接口 红冲返货应收单结束.");
		}
		
		BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(dto.getWaybillNo());
		
		/**
		 * 代收货款应付单
		 * 红冲虚拟网点的代收货款费用，生成应付最新提货网点代收货款费用
		 */
		BillPayableConditionDto payConDto = new BillPayableConditionDto(dto.getWaybillNo());
		payConDto.setWaybillNo(dto.getWaybillNo());
		payConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD});

		this.writeBackVirtualNetworkPayableAndCreateNewBill(payConDto, dto, currentInfo);

		/**
		 * 装卸费应付单
		 * 红冲虚拟网点的装卸费应付费用，生成应付最新提货网点装卸费应付费用
		 */
		BillPayableConditionDto sfConDto = new BillPayableConditionDto(dto.getWaybillNo());
		sfConDto.setWaybillNo(dto.getWaybillNo());
		sfConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE});
		//校验装卸费应付
		this.writeBackVirtualNetworkPayableAndCreateNewBill(sfConDto, dto, currentInfo);

		/**
		 * 当运单补码为快递代理
		 * 红冲始发应收单生成最新始发应收单
		 */
		receiConDto.setWaybillNo(dto.getWaybillNo());
		receiConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE}); //始发应收

		this.writeBackVirtualNetworkReceivableAndCreateNewBill(receiConDto, dto, currentInfo);

		/**
		 * 红冲现金收款单生成最新单据补码到付部门
		 */
		this.writeBackVirtualNetworkCashCollectionAndCreateNewBill(dto, currentInfo);
		logger.info("调用补码财务接口 补码后部门是合伙人，收货部门不是合伙人结束.");
	}

	private void validaSettle(SettlementComplementBillDto dto,
			CurrentInfo currentInfo) {
		logger.info("调用补码财务接口 补码后部门不是合伙人.");
		this.validateSettlementComplementBillDto(dto);

		//ddw，通过新单查询原单
		WaybillExpressEntity waybills = waybillExpressService.queryWaybillByWaybillNo(dto.getWaybillNo(), WaybillConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO);
		if (null != waybills && StringUtil.isNotBlank(waybills.getOriginalWaybillNo())
		        && StringUtil.isNotBlank(dto.getDestOrgCode())
		        && StringUtil.isNotBlank(dto.getDestOrgName())) {
		    logger.info("调用补码财务接口 红冲返货应收单开始.");
		    //运单对象
		    WaybillPickupInfoDto waybillPickupInfoDto = new WaybillPickupInfoDto();
		    //原单单号
		    waybillPickupInfoDto.setWaybillNo(waybills.getOriginalWaybillNo());
		    //到达部门编码
		    waybillPickupInfoDto.setLastLoadOrgCode(dto.getDestOrgCode());
		    //到达部门名称
		    waybillPickupInfoDto.setLastLoadOrgName(dto.getDestOrgName());
		    //红冲原单
		    waybillPickupService.returnedGoodsWriteoffReceivable(waybillPickupInfoDto, currentInfo);
		    logger.info("调用补码财务接口 红冲返货应收单结束.");
		}

		/**
		 * 红冲虚拟网点的应收到付运费，生成应收最新提货网点的到付运费
		 */
		BillReceivableConditionDto receiConDto = new BillReceivableConditionDto(dto.getWaybillNo());
		receiConDto.setWaybillNo(dto.getWaybillNo());
		//2016-10-2 update by 231434-bieyexiong-foss 到付单独处理,如果网上支付已核销，做反核销，再红冲，在核销
//                receiConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE}); //到付运费应收单
		
//                this.writeBackVirtualNetworkReceivableAndCreateNewBill(receiConDto, dto, currentInfo);

		/**
		 * 红冲虚拟网点的代收货款费用，生成应收最新提货网点代收货款费用。
		 */
		receiConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE}); // 代收货款应收

		this.writeBackVirtualNetworkReceivableAndCreateNewBill(receiConDto, dto, currentInfo);

		/**
		 * 代收货款应付单
		 * 红冲虚拟网点的代收货款费用，生成应付最新提货网点代收货款费用
		 */
		BillPayableConditionDto payConDto = new BillPayableConditionDto(dto.getWaybillNo());
		payConDto.setWaybillNo(dto.getWaybillNo());
		payConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD});

		this.writeBackVirtualNetworkPayableAndCreateNewBill(payConDto, dto, currentInfo);

		/**
		 * 装卸费应付单
		 * 红冲虚拟网点的装卸费应付费用，生成应付最新提货网点装卸费应付费用
		 */
		BillPayableConditionDto sfConDto = new BillPayableConditionDto(dto.getWaybillNo());
		sfConDto.setWaybillNo(dto.getWaybillNo());
		sfConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE});

		this.writeBackVirtualNetworkPayableAndCreateNewBill(sfConDto, dto, currentInfo);


		/**
		 * 当运单补码为快递代理
		 * 红冲始发应收单生成最新始发应收单
		 */
		receiConDto.setWaybillNo(dto.getWaybillNo());
		receiConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE}); //始发应收

		this.writeBackVirtualNetworkReceivableAndCreateNewBill(receiConDto, dto, currentInfo);

		/**
		 * 红冲现金收款单生成最新单据补码到付部门
		 */
		this.writeBackVirtualNetworkCashCollectionAndCreateNewBill(dto, currentInfo);
		
		/**
		 * 2016-10-2 update by 231434-bieyexiong-foss 到付单独处理,如果网上支付已核销，做反核销，再红冲，在核销
		 * 到付运费应收单
		 */
		receiConDto.setBillTypes(new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE}); 
		//如果来源系统是悟空
		if(SettlementDictionaryConstants.SOURCE_SYSTEM_ECS.equals(dto.getSourceSystem())){
			//走新逻辑，反核销、红冲、核销
			this.writeBackVirtualNetworkReceivableAndCreateNewBillNew(receiConDto, dto, currentInfo);
		}else{
			//否则走原逻辑
			this.writeBackVirtualNetworkReceivableAndCreateNewBill(receiConDto, dto, currentInfo);
		}
	}

	/**
	 * 红冲始发应收或应收到付运费或代收货款费用，生成应收最新提货网点的到付运费或代收货款费用。
	 * @author foss-guxinhua
	 * @date 2013-7-29 下午4:20:10
	 */
	private void writeBackVirtualNetworkReceivableAndCreateNewBill(BillReceivableConditionDto receiConDto,SettlementComplementBillDto dto, CurrentInfo currentInfo){
		// 根据传入的运单号和单据类型等参数，查询到付运费应收单信息可公用
		List<BillReceivableEntity> receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);

		if (CollectionUtils.isNotEmpty(receList)) {
			Date date = new Date();

			// 验证同一个外发单和代理编码是否存在相同类型的多个应收单
			this.billReceivableService.validateWaybillForBillReceivable(receList);
			BillReceivableEntity receEntity = receList.get(0);

			// 验证应收单
			if(this.validateBillReceivable(receEntity, date)){
				// 红冲虚拟网点的应收到付运费或代收货款费用
				this.billReceivableService.writeBackBillReceivable(receEntity,currentInfo);

				date = new Date();
				// 生成应收最新提货网点的到付运费或代收货款费用
				this.createLatestDeliveryOutletsBillReceivable( receEntity,  dto,   date,  currentInfo);

				logger.info(String.format("调用补码财务接口:类型%s应收单补码成功 ", receiConDto.getBillTypes()[0]));
			}

		}
	}

	/**
	 * 生成应收最新提货网点的始发应收或到付运费或代收货款费用
	 * @author guxinhua
	 * @date 2012-12-26 下午3:59:06
	 * @param dto
	 * @param currentInfo
	 */
	private void createLatestDeliveryOutletsBillReceivable(
			BillReceivableEntity receEntity,SettlementComplementBillDto dto,  Date date,CurrentInfo currentInfo) {

		// 生成到付运费应收单
		BillReceivableEntity blueEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(receEntity, blueEntity);
		// 设置Id
		blueEntity.setId(UUIDUtils.getUUID());
		// 设置单号
		blueEntity.setReceivableNo(this.settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.YS2));
		// 设置来源单号
		blueEntity.setSourceBillNo(receEntity.getWaybillNo());
		// 设置来源单据类型为：运单
		blueEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		// 设置通用参数
		this.setReceivableCommonParam(blueEntity);
		// 设置创建时间
		blueEntity.setCreateDate(date);
		// 记账日期
		blueEntity.setAccountDate(date);

		// 如果始发应收，只需要修改到达部门，（应收部门、催款部门、提货网点）不变
		if(StringUtils.equals(blueEntity.getBillType(),
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)){
			// 最新网点的到达部门
			blueEntity.setDestOrgCode(dto.getDestOrgCode());
			blueEntity.setDestOrgName(dto.getDestOrgName());
		}else{
			// 到付运费或代收货款费用
			// 最新网点的到达部门、应收部门、催款部门、提货网点
			blueEntity.setDestOrgCode(dto.getDestOrgCode());
			blueEntity.setDestOrgName(dto.getDestOrgName());
			blueEntity.setCustomerPickupOrgCode(dto.getDestOrgCode());
			if(!FossConstants.YES.equals(blueEntity.getUnifiedSettlement())){
				ActualFreightEntity wayBill =  actualFreightService.queryByWaybillNo(dto.getWaybillNo());
				if(wayBill == null){
					throw new SettlementException("承运信息为空,无法获取到达统一结算信息!");
				}
				if(FossConstants.YES.equals(wayBill.getArriveCentralizedSettlement())
						&&StringUtils.equals(dto.getIsFreeSite(), FossConstants.YES)){
					//获取催款部门和合同部门
					OrgAdministrativeInfoEntity duningOrg = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByUnifiedCodeNoCache(wayBill.getArriveReminderOrgCode());
					OrgAdministrativeInfoEntity contractOrg = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByUnifiedCodeNoCache(wayBill.getArriveContractOrgCode());
					blueEntity.setDunningOrgCode(duningOrg.getCode());
					blueEntity.setDunningOrgName(duningOrg.getName());
					blueEntity.setContractOrgCode(contractOrg.getCode());
					blueEntity.setContractOrgName(contractOrg.getName());
					blueEntity.setReceivableOrgCode(contractOrg.getCode());
					blueEntity.setReceivableOrgName(contractOrg.getName());
					blueEntity.setUnifiedSettlement(FossConstants.YES);

				}else{
				blueEntity.setReceivableOrgCode(dto.getDestOrgCode());
				blueEntity.setReceivableOrgName(dto.getDestOrgName());
				blueEntity.setDunningOrgCode(dto.getDestOrgCode());
				blueEntity.setDunningOrgName(dto.getDestOrgName());
				}
			}else if(FossConstants.YES.equals(blueEntity.getUnifiedSettlement())
					&&!StringUtils.equals(dto.getIsFreeSite(), FossConstants.YES)){

				blueEntity.setReceivableOrgCode(dto.getDestOrgCode());
				blueEntity.setReceivableOrgName(dto.getDestOrgName());
				blueEntity.setDunningOrgCode(dto.getDestOrgCode());
				blueEntity.setDunningOrgName(dto.getDestOrgName());
				blueEntity.setUnifiedSettlement(FossConstants.NO);
				blueEntity.setContractOrgCode(null);
				blueEntity.setContractOrgName(null);
				}
		}

		this.setReceivableExpressOrigDestOrg(blueEntity,dto);

		// 调用应收单接口
		this.billReceivableService.addBillReceivable(blueEntity, currentInfo);
	}

	/**
	 * 红冲虚拟网点的应应付代收货款。
	 * @author 邓大伟
	 * @date 2013-09-22
	 */
	private void writeBackVirtualNetworkPayableAndCreateNewBill(BillPayableConditionDto payConDto,SettlementComplementBillDto dto, CurrentInfo currentInfo){
		// 根据传入的运单号和单据类型等参数，查询到付运费应付单信息可公用
		List<BillPayableEntity> payList = this.billPayableService.queryBillPayableByCondition(payConDto);

		if (CollectionUtils.isNotEmpty(payList)) {
			Date date = new Date();

			// 验证同一个外发单和代理编码是否存在相同类型的多个应付单
			this.billPayableService.validateWaybillForBillPayable(payList);
			BillPayableEntity payEntity = payList.get(0);

			// 验证应付单
			if(this.validateBillPayable(payEntity, date)){
				// 红冲虚拟网点的应付代收货款费用
				this.billPayableService.writeBackBillPayable(payEntity,currentInfo);

				date = new Date();
				// 生成应付最新提货网点的代收货款费用
				this.createLatestDeliveryOutletsBillPayable( payEntity,  dto,   date,  currentInfo);
				logger.info("调用补码财务接口:"+payEntity.getBillType()+"应付单补码成功 ");
			}

		}
	}

	/**
	 * 生成应付最新提货网点的代收货款费用
	 * @author 邓大伟
	 * @date 2013-09-22
	 * @param dto
	 * @param currentInfo
	 */
	private void createLatestDeliveryOutletsBillPayable(
			BillPayableEntity payEntity,SettlementComplementBillDto dto,  Date date,CurrentInfo currentInfo) {

		Map<String, SettlementNoRuleEnum> stlSqMap = new HashMap<String, SettlementNoRuleEnum>(2);
		stlSqMap.put(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD, SettlementNoRuleEnum.YF1);
		stlSqMap.put(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE, SettlementNoRuleEnum.YF2);

		// 生成代收货款应付单
		BillPayableEntity blueEntity = new BillPayableEntity();
		BeanUtils.copyProperties(payEntity, blueEntity);
		// 设置Id
		blueEntity.setId(UUIDUtils.getUUID());
		// 设置单号
		blueEntity.setPayableNo(this.settlementCommonService.getSettlementNoRule(stlSqMap.get(payEntity.getBillType())));
		// 设置来源单号
		blueEntity.setSourceBillNo(payEntity.getWaybillNo());
		// 设置来源单据类型为：运单
		blueEntity.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);
		// 设置通用参数
		this.setPayableCommonParam(blueEntity);
		// 设置创建时间
		blueEntity.setCreateDate(date);
		// 记账日期
		blueEntity.setAccountDate(date);
		// 最新网点的到达部门、应付部门
		blueEntity.setDestOrgCode(dto.getDestOrgCode());
		blueEntity.setDestOrgName(dto.getDestOrgName());

		this.setPayableExpressOrigDestOrg(blueEntity,dto);

		// 调用应付单接口
		this.billPayableService.addBillPayable(blueEntity, currentInfo);
	}

	/**
	 * 红冲现金收款单
	 * @author 163576-foss-guxinhua
	 * @date 2013-10-18 下午4:20:10
	 * @param dto
	 * @param currentInfo
	 */
	private void writeBackVirtualNetworkCashCollectionAndCreateNewBill(
			SettlementComplementBillDto dto, CurrentInfo currentInfo) {
		// 根据传入的运单号和单据类型等参数，查询到付运费应收单信息可公用
		List<BillCashCollectionEntity> cashCollectionlist = billCashCollectionService.queryBySourceBillNOs(Arrays.asList(dto.getWaybillNo()),
					SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL, FossConstants.ACTIVE);

		if (CollectionUtils.isNotEmpty(cashCollectionlist)) {
			Date date = new Date();

			// 验证验证一个运单是否存在多条现金收款单
			this.billCashCollectionService.validateWaybillForBillCashCollection(cashCollectionlist);
			BillCashCollectionEntity cashEntity = cashCollectionlist.get(0);

			// 红冲现金收款单
			this.billCashCollectionService.writeBackBillCashCollection(cashEntity, currentInfo);

			// 生成补码后现金收款单
			this.createLatestDeliveryOutletsCashCollection( cashEntity,  dto,   date,  currentInfo);

			logger.info("调用补码财务接口:现金收款单补码成功 ");
		}
	}

	/**
	 * 生成补码后现金收款单
	 * @author guxinhua
	 * @date 2012-12-26 下午3:59:06
	 * @param dto
	 * @param currentInfo
	 */
	private void createLatestDeliveryOutletsCashCollection(
			BillCashCollectionEntity cashEntity,
			SettlementComplementBillDto dto, Date date, CurrentInfo currentInfo) {
		// 生成现金收款单
		BillCashCollectionEntity blueEntity = new BillCashCollectionEntity();
		BeanUtils.copyProperties(cashEntity, blueEntity);
		// 设置Id
		blueEntity.setId(UUIDUtils.getUUID());
		blueEntity.setCashCollectionNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.XS1));

		// 设置来源单号
		blueEntity.setSourceBillNo(cashEntity.getWaybillNo());
		// 设置来源单据类型为：运单
		blueEntity.setSourceBillType(SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL);

		blueEntity.setActive(FossConstants.ACTIVE); // 是否有效
		blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO); // 是否红冲
		blueEntity.setIsInit(FossConstants.NO); // 是否初始化
		blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO); // 初始化版本号
		blueEntity.setCreateTime(date); // 创建时间
		blueEntity.setModifyTime(date); // 修改时间
		blueEntity.setAccountDate(date); // 记账日期

		blueEntity.setDestOrgCode(dto.getDestOrgCode());
		blueEntity.setDestOrgName(dto.getDestOrgName());

		//收银状态
		blueEntity.setStatus(SettlementDictionaryConstants.BILL_CASH_COLLECTION__STATUS__SUBMIT);

		blueEntity.setCashConfirmTime(null);
		blueEntity.setCashConfirmUserCode(null);
		blueEntity.setCashConfirmUserName(null);

		//如果是自由网点
		if(StringUtils.equals(dto.getIsFreeSite(), FossConstants.YES)){
			ExpressPartSalesDeptResultDto destExpressDept = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(blueEntity.getDestOrgCode(),blueEntity.getAccountDate());
			if(destExpressDept==null ){
				throw new SettlementException("该提货网点对应的快递点部不存在，不能进行补码！");
			//如果快递代理点部存在，则获取快递代理点部门
			}else if(StringUtils.isNotBlank(destExpressDept.getPartCode())){
				blueEntity.setExpressDestOrgCode(destExpressDept.getPartCode());
				blueEntity.setExpressDestOrgName(destExpressDept.getPartName());
			//如果快递代理点部不存在，且不是试点城市，则获取对应到达部门
			}else{
				blueEntity.setExpressDestOrgCode(blueEntity.getDestOrgCode());
				blueEntity.setExpressDestOrgName(blueEntity.getDestOrgName());
			}

		//如果是快递代理
		}else if(StringUtils.equals(dto.getIsFreeSite(), FossConstants.NO)){
			blueEntity.setExpressDestOrgCode(dto.getDestOrgCode());
			blueEntity.setExpressDestOrgName(dto.getDestOrgName());
		}

		// 调用应收单接口
		this.billCashCollectionService.addBillCashCollection(blueEntity, currentInfo);
	}

	/**
	 * 验证应付单
	 * @author 邓大伟
	 * @date 2013-09-22
	 * @param entity
	 * @param date
	 */
	private boolean validateBillPayable(BillPayableEntity entity, Date date) {
		//不存在应收单信息，直接返回true
		if (entity == null) {
			return true;
		}
		if (entity.getVerifyAmount() != null && entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			//应收单已经被核销
			throw new SettlementException("应付单已经被核销");
		}
		return true;
	}
	
	/**
	 *  验证应付单始发核销、始发确认对账单
	 * @author foss-231434-bieyexiong
	 * @date 2016-06-02
	 */
	private boolean checkBillPayable(BillPayableEntity entity, Date date){
		//不存在应付单信息，直接返回true
		if (entity == null) {
			return true;
		}
		if (entity.getVerifyAmount() != null && entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			//应收单已经被核销
			throw new SettlementException("应付单已经被核销");
		}
		// 校验对账单
		if (StringUtils.isEmpty(entity.getStatementBillNo())
				|| SettlementConstants.DEFAULT_BILL_NO.equals(entity
						.getStatementBillNo())) {
			return true;
		} else {
			//对账单号不为空时，需要查询对账单记录，
			//看对账单记录是否已经被确认了。
			List<String> list = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity
							.getStatementBillNo()));
			if (!CollectionUtils.isEmpty(list)) {
				// 对账单已确认则不允许红冲单据
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行红冲");
			}
		}
		return true;
	}

	/**
	 * 验证应收单
	 * @author guxinhua
	 * @date 2012-11-20 下午6:42:18
	 * @param entity
	 * @param date
	 */
	private boolean validateBillReceivable(BillReceivableEntity entity, Date date) {
		//不存在应收单信息，直接返回true
		if (entity == null) {
			return true;
		}
		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null && date.before(entity.getUnlockDateTime())) {
			//应收单已被锁定
			throw new SettlementException("应收单已被锁定");
		}
		
		//---update by foss-231434-bieyexiong 2016-10-05 start
		if(StringUtils.equals(entity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)
				|| StringUtils.equals(entity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE)){
			
			//到付或代收，如果到达部门为加盟网点，则不红冲生成，交由PTP系统处理
			SaleDepartmentEntity destEntity = saleDepartmentService.querySaleDepartmentInfoByCode(entity.getDestOrgCode());
			if(destEntity != null && FossConstants.YES.equals(destEntity.getIsLeagueSaleDept())){
				return false;
			}
		}
		//---update by foss-231434-bieyexiong 2016-10-05  end
		
		if (entity.getUnverifyAmount() != null
				&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
			//应收单已经被核销
			if(StringUtils.equals(entity.getBillType(),
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)){
//				throw new SettlementException("始发应收已经还款核销，请联系出发部门反核销后，再进行补码；补码完成后，请联系出发部门再做还款和核销操作。");
				return false;
			}else{
				throw new SettlementException("应收单已经被核销");
			}
		}

		// 始发应收单据补码时，需要判断对账单是否已确认
		if(StringUtils.equals(entity.getBillType(),
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)){

			// 运单对应的客户对账单已确认、核销、付款、还款
			List<String> statementNos = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(entity.getStatementBillNo()));
			if (CollectionUtils.isNotEmpty(statementNos)) {
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行补码操作");
			}else if(entity.getVerifyAmount()!= null
					&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO)>0){
				return false;
			}
		}

		return true;
	}

	/**
	 * 验证应收单是否核销、是否确认对账单
	 * @author foss-231434-bieyexiong
	 * @date 2016-06-02 
	 */
	private boolean checkBillReceivable(BillReceivableEntity entity, Date date) {
		//不存在应收单信息，直接返回true
		if (entity == null) {
			return true;
		}
		// 当前系统时间，小于应收单的解锁时间时不能被红冲
		if (entity.getUnlockDateTime() != null && date.before(entity.getUnlockDateTime())) {
			//应收单已被锁定
			throw new SettlementException("应收单已被锁定");
		}
		if (entity.getUnverifyAmount() != null
				&& entity.getUnverifyAmount().compareTo(BigDecimal.ZERO) == 0) {
			//应收单已经被核销
			if(StringUtils.equals(entity.getBillType(),
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)){
//				throw new SettlementException("始发应收已经还款核销，请联系出发部门反核销后，再进行补码；补码完成后，请联系出发部门再做还款和核销操作。");
				return false;
			}else{
				throw new SettlementException("应收单已经被核销");
			}
		}
		// 运单对应的客户对账单已确认、核销、付款、还款
		List<String> statementNos = statementOfAccountService
				.queryConfirmStatmentOfAccount(Arrays.asList(entity.getStatementBillNo()));
		if (CollectionUtils.isNotEmpty(statementNos)) {
			throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行补码操作");
		}else if(StringUtils.equals(entity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE)
				&& entity.getVerifyAmount()!= null
				&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO)>0){
			return false;
		}

		return true;
	}

	/**
	 * 验证补码功能DTO
	 * @author foss-guxinhua
	 * @date 2013-7-29 下午4:29:25
	 * @param dto
	 */
	private void validateSettlementComplementBillDto(
			SettlementComplementBillDto dto) {

		if(StringUtils.isBlank(dto.getWaybillNo())){
			throw new SettlementException("运单号不能为空！");
		}

		if(StringUtils.isBlank(dto.getIsFreeSite())){
			throw new SettlementException("区分自由网点、快递代理标示不能为空！");
		}

		if(StringUtils.isBlank(dto.getDestOrgCode())){
			throw new SettlementException("最新提货网点的到达部门编码不能为空！");
		}

		if(StringUtils.isBlank(dto.getDestOrgName())){
			throw new SettlementException("最新提货网点的到达部门名称不能为空！");
		}
	}

	/**
	 * 设置应收单默认初始化属性
	 * @author foss-guxinhua
	 * @date 2013-7-29 下午4:01:47
	 * @param recEntity
	 */
	private void setReceivableCommonParam(BillReceivableEntity recEntity){
		// 设置对账单号N/A
		recEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		// 是否有效
		recEntity.setActive(FossConstants.ACTIVE);
		// 是否红单
		recEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 是否初始化
		recEntity.setIsInit(FossConstants.NO);
		// 版本号默认为1
		recEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 生成方式：系统生成
		recEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
	}

	/**
	 * 设置应付单默认初始化属性
	 * @author 邓大伟
	 * @date 2013-09-22
	 * @param payEntity
	 */
	private void setPayableCommonParam(BillPayableEntity payEntity){
		// 设置对账单号N/A
		payEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
		// 是否有效
		payEntity.setActive(FossConstants.ACTIVE);
		// 是否红单
		payEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		// 是否初始化
		payEntity.setIsInit(FossConstants.NO);
		// 版本号默认为1
		payEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 生成方式：系统生成
		payEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
	}

	/**
	 * 设置快递代理网点部门
	 * @author foss-guxinhua
	 * @date 2013-8-13 上午10:41:13
	 * @param entity
	 */
	private void setReceivableExpressOrigDestOrg(BillReceivableEntity entity, SettlementComplementBillDto dto){

		//如果是自由网点
		if(StringUtils.equals(dto.getIsFreeSite(), FossConstants.YES)){
			ExpressPartSalesDeptResultDto destExpressDept = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(entity.getDestOrgCode(),entity.getAccountDate());
			if(destExpressDept==null ){
				throw new SettlementException("该提货网点对应的快递点部不存在，不能进行补码！");
			//如果快递代理点部存在，则获取快递代理点部门
			}else if(StringUtils.isNotBlank(destExpressDept.getPartCode())){
				entity.setExpressDestOrgCode(destExpressDept.getPartCode());
				entity.setExpressDestOrgName(destExpressDept.getPartName());
			//如果快递代理点部不存在，且不是试点城市，则获取对应到达部门
			}else{
				entity.setExpressDestOrgCode(entity.getDestOrgCode());
				entity.setExpressDestOrgName(entity.getDestOrgName());
			}

		//如果是快递代理
		}else if(StringUtils.equals(dto.getIsFreeSite(), FossConstants.NO)){
			entity.setExpressDestOrgCode(dto.getDestOrgCode());
			entity.setExpressDestOrgName(dto.getDestOrgName());
		}else{
			throw new SettlementException("区分自由网点、快递代理标示错误：" + dto.getIsFreeSite());
		}

	}

	/**
	 * 设置快递代理网点部门
	 * @author 邓大伟
	 * @date  2013-09-22
	 * @param entity
	 */
	private void setPayableExpressOrigDestOrg(BillPayableEntity entity, SettlementComplementBillDto dto){

		//如果是自由网点
		if(StringUtils.equals(dto.getIsFreeSite(), FossConstants.YES)){
			ExpressPartSalesDeptResultDto destExpressDept = expressPartSalesDeptService.queryExpressPartSalesDeptBySalesCodeAndTime(entity.getDestOrgCode(),entity.getAccountDate());
			if(destExpressDept==null ){
				throw new SettlementException("该提货网点对应的快递点部不存在，不能进行补码！");
			//如果快递代理点部存在，则获取快递代理点部门
			}else if(StringUtils.isNotBlank(destExpressDept.getPartCode())){
				entity.setExpressDestOrgCode(destExpressDept.getPartCode());
				entity.setExpressDestOrgName(destExpressDept.getPartName());
			//如果快递代理点部不存在，且不是试点城市，则获取对应到达部门
			}else{
				entity.setExpressDestOrgCode(entity.getDestOrgCode());
				entity.setExpressDestOrgName(entity.getDestOrgName());
			}
		//如果是快递代理
		}else if(StringUtils.equals(dto.getIsFreeSite(), FossConstants.NO)){
			entity.setExpressDestOrgCode(dto.getDestOrgCode());
			entity.setExpressDestOrgName(dto.getDestOrgName());
		}else{
			throw new SettlementException("区分自由网点、快递代理标示错误：" + dto.getIsFreeSite());
		}
	}

	/**
	 * 到达应收单红冲，生成应收最新提货网点的到付运费或代收货款费用。
	 * 2016-10-02 update by 231434-bieyexiong-foss
	 */
	private void writeBackVirtualNetworkReceivableAndCreateNewBillNew(BillReceivableConditionDto receiConDto,SettlementComplementBillDto dto, CurrentInfo currentInfo){
		// 根据传入的运单号和单据类型等参数，查询到付运费应收单信息可公用
		List<BillReceivableEntity> receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);

		if (CollectionUtils.isNotEmpty(receList)) {
			Date date = new Date();

			// 验证同运单是否存在相同类型的多个应收单
			this.billReceivableService.validateWaybillForBillReceivable(receList);
			BillReceivableEntity receEntity = receList.get(0);
			
			//不存在应收单信息，直接返回
			if (receEntity == null) {
				return ;
			}
			
			logger.info("悟空补码到达应收网上支付红冲开始：" + receEntity.getWaybillNo());
			
			if(StringUtils.equals(receEntity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)
					|| StringUtils.equals(receEntity.getBillType(),SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE)){
				
				//到付或代收，如果到达部门为加盟网点，则不红冲生成，交由PTP系统处理
				SaleDepartmentEntity destEntity = saleDepartmentService.querySaleDepartmentInfoByCode(receEntity.getDestOrgCode());
				if(destEntity != null && FossConstants.YES.equals(destEntity.getIsLeagueSaleDept())){
					return ;
				}
			}
			
			// 当前系统时间，小于应收单的解锁时间时不能被红冲
			if (receEntity.getUnlockDateTime() != null && date.before(receEntity.getUnlockDateTime())) {
				//应收单已被锁定
				throw new SettlementException("应收单已被锁定");
			}

			// 运单对应的客户对账单已确认、核销、付款、还款
			List<String> statementNos = statementOfAccountService
					.queryConfirmStatmentOfAccount(Arrays.asList(receEntity.getStatementBillNo()));
			if (CollectionUtils.isNotEmpty(statementNos)) {
				throw new SettlementException("该单存在相应的客户对账单已确认、核销、付款、还款，不能进行补码操作");
			}
			
			//---------自动反核销---------
			
			// 如果应收单存在运单号，生成互斥对象
			MutexElement mutex = null;
			//还款单实体
			BillRepaymentEntity repayment = null;

			try{
				mutex = validaCondition(receiConDto, dto, currentInfo,
						receEntity, mutex);
			}catch(BusinessException e){
				
				logger.info("悟空补码到达应收网上支付红冲结束：" + receEntity.getWaybillNo());
				throw new SettlementException("悟空补码到达应收网上支付自动处理还款单异常："+e.getErrorCode());
			}finally{
				// 如果互斥对象不为空,解锁该应收单对应的运单
				if (mutex != null) {
					// 解锁运单
					businessLockService.unlock(mutex);
				}
			}
			logger.info(String.format("调用补码财务接口:类型%s应收单补码成功 ", receiConDto.getBillTypes()[0]));
		}
	}

	private MutexElement validaCondition(
			BillReceivableConditionDto receiConDto,
			SettlementComplementBillDto dto, CurrentInfo currentInfo,
			BillReceivableEntity receEntity, MutexElement mutex) {
		List<BillReceivableEntity> receList;
		Date date;
		BillRepaymentEntity repayment;
		//int count = NumberConstants.NUMERAL_ZORE;
		//应收单已经被核销
		if (receEntity.getVerifyAmount() != null
				&& receEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
			//查询还款单
			List<BillRepaymentEntity> repayments = billRepaymentService.queryRepaymentByReceivableNo
					(receEntity.getReceivableNo(),FossConstants.YES,SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
			//TODO 已核销金额 大于 0 ，查询不到还款单，如何处理？
			if(CollectionUtils.isEmpty(repayments)){
				throw new SettlementException("该运单到达应收已核销，且非还款冲应收，无法自动反核销！");
			}
			//TODO 查询出的还款单条数 大于 1，如何处理 ？
			repayment = repayments.get(0);
			
			//到达应收单不会多次网上支付还款
			if(repayment != null && SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(repayment.getPaymentType())){

				//判空
				if (StringUtils.isNotEmpty(receEntity.getWaybillNo())&&!SettlementConstants.DEFAULT_BILL_NO.equals(receEntity.getWaybillNo())) {
					//互斥锁
					mutex = new MutexElement(receEntity.getWaybillNo(),"悟空补码到达应收网上支付反核销", MutexElementType.WAYBILL_NO);
				}
				// 如果互斥对象不为空,锁定该应收单对应的运单
				if (mutex != null) {
					// 锁定运单
					businessLockService.lock(mutex,SettlementConstants.BUSINESS_LOCK_BATCH);
				}

				//如果是到达应收网上支付 还款，自动反核销
				billRepaymentComplementAutoDisableAndCreateService.complementAutoDisableBillRepayment(repayment, currentInfo);

				
				//--------红冲操作--------
				//重新查询 新生成的到达应收单
				receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);
				
				if(CollectionUtils.isNotEmpty(receList)){
					receEntity = receList.get(0);
					
					// 红冲虚拟网点的应收到付运费或代收货款费用
					this.billReceivableService.writeBackBillReceivable(receEntity,currentInfo);
					
					date = new Date();
					// 生成应收最新提货网点的到付运费或代收货款费用
					this.createLatestDeliveryOutletsBillReceivable( receEntity,  dto,   date,  currentInfo);
				}
				

				//---------自动核销---------
				//如果是网上支付的还款单，做自动核销
				//重新查询 新生成的到达应收单
				receList = this.billReceivableService.queryBillReceivableByCondition(receiConDto);
				if(CollectionUtils.isNotEmpty(receList)){
					receEntity = validaBill(receList, mutex, repayment);
				}

			}else{
				throw new SettlementException("应收单已经被核销");
			}
		}else{
			//--------红冲操作--------
			// 红冲虚拟网点的应收到付运费或代收货款费用
			this.billReceivableService.writeBackBillReceivable(receEntity,currentInfo);
			
			date = new Date();
			// 生成应收最新提货网点的到付运费或代收货款费用
			this.createLatestDeliveryOutletsBillReceivable( receEntity,  dto,   date,  currentInfo);
		}
		
		logger.info("悟空补码到达应收网上支付红冲结束：" + receEntity.getWaybillNo());
		return mutex;
	}

	private BillReceivableEntity validaBill(
			List<BillReceivableEntity> receList, MutexElement mutex,
			BillRepaymentEntity repayment) {
		BillReceivableEntity receEntity;
		receEntity = receList.get(0);

		//补码时自动生成 到达应收网上支付 还款单(还款冲应收)
		BillReceivableOnlineQueryDto queryDto = new BillReceivableOnlineQueryDto();
		queryDto.setReceivableNo(receEntity.getReceivableNo());
		queryDto.setAmount(repayment.getAmount());
		queryDto.setOnlineNo(repayment.getOnlinePaymentNo());
		billRepaymentComplementAutoDisableAndCreateService.complementAutoCreateBillRepayment(queryDto,receEntity);

		// 如果互斥对象不为空,解锁该应收单对应的运单
		if (mutex != null) {
			// 解锁运单
			businessLockService.unlock(mutex);
		}

		//调用财务自助接口，修改财务自助公布的占用部门
		OrgAdministrativeInfoEntity org= 
				orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(receEntity.getReceivableOrgCode());
		if(org == null){
			throw new SettlementException("悟空补码到达应收网上支付自动处理还款单异常:补码到达部门不存在！");
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("onlinePayCode", repayment.getOnlinePaymentNo());
		map.put("complementDeptCode",org.getUnifiedCode());
		//调用财务自助接口
		financeOnlinePayWSProxy.updateObtainDept(map);
		return receEntity;
	}
	
	/**
	 * @return billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
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

	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	public void setActualFreightService(IActualFreightService actualFreightService) {
		this.actualFreightService = actualFreightService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setWaybillExpressService(
			IWaybillExpressService waybillExpressService) {
		this.waybillExpressService = waybillExpressService;
	}

	public void setWaybillPickupService(IWaybillPickupService waybillPickupService) {
		this.waybillPickupService = waybillPickupService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	//268217
	public void setBillClaimService(IBillClaimService billClaimService) {
		this.billClaimService = billClaimService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setBillRepaymentComplementAutoDisableAndCreateService(
			IBillRepaymentComplementAutoDisableAndCreateService billRepaymentComplementAutoDisableAndCreateService) {
		this.billRepaymentComplementAutoDisableAndCreateService = billRepaymentComplementAutoDisableAndCreateService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	public void setFinanceOnlinePayWSProxy(
			IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
		this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
	}
	
}
