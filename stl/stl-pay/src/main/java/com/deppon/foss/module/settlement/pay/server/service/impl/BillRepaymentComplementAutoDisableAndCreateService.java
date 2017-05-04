package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.IPdaPosManageService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableOnlineQueryDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillRepaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.StatementOfAccountUpdateDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillRepaymentManageDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillRepaymentComplementAutoDisableAndCreateService;
import com.deppon.foss.module.settlement.pay.api.server.service.ISoaRepaymentManageService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.SoaRepaymentEntity;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IWoodenStatementService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.WoodenStatementEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 悟空补码时，到达应收网上支付还款单处理
 * @author 231434-foss-bieyexiong
 * @date 2016-10-3
 */
public class BillRepaymentComplementAutoDisableAndCreateService implements IBillRepaymentComplementAutoDisableAndCreateService{
	
	/**
	 * Logger
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillRepaymentComplementAutoDisableAndCreateService.class);

	// 还款单dao
	private IBillRepaymentManageDao billRepaymentManageDao;
	
	/**
	 * 系统配置参数 Service接口
	 */
	private IConfigurationParamsService configurationParamsService;
	
	/**
	 * 核销/反核销公共service
	 */
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 应付单service
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 还款单公共service
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 对账单还款单关系service
	 */
	private ISoaRepaymentManageService soaRepaymentManageService;
	
	/**
	 * 对账单service
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	private IWoodenStatementService woodenStatementService;
	
	/**
     * 注入POS服务service
     * @author 309603 yangqiang
     * @date 2016-02-23 
     */
    private IPdaPosManageService pdaPosManageService;

    /**
	 * 操作日志service
	 */
	private IOperatingLogService operatingLogService;
	
	/**
	 * 注入生成序列Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 注入子公司SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 注入代收货款ICodCommonService
	 */
	private ICodCommonService codCommonService;
	
	/**
	 * 注入更改单SERVICE
	 */
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 补码时自动作废 到达应收网上支付 还款单
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-3 
	 */
	@Override
	public void complementAutoDisableBillRepayment(BillRepaymentEntity entity,CurrentInfo cInfo) {
			if(entity == null){
				throw new SettlementException("还款单实体为空，无法在补码时自动作废还款单");
			}
			LOGGER.info("补码时自动作废还款单开始..."+entity.getWaybillNo());	
			
			// 校验是否可以作废还款单
			this.validateBillRepaymentStatus(entity, cInfo);
			
			// 作废还款单
			this.disableBillRepaymentList(entity, cInfo);
			
			//update by 2016-10-04 231434  只给悟空补码 到付应收单网上支付 使用，不会是月结，不用管灰名单
			//start add by 269044-zhurongrong--灰名单需求 2016-05-09 15:19
			//发生反核销时，先判断该客户是否在灰名单中，不在的话需判断是否添加进去，在的话，需要更新最久欠款日期
			//存放客户编码集合
//			List<String> customerCodeList = new ArrayList<String>();
//			//获取所有的客户编码集合
//			if(list.size() > 0) {
//				for(BillRepaymentEntity billRepaymentEntity : list) {
//					//如果核销单的客户编码不为空
//					if(StringUtils.isNotEmpty(billRepaymentEntity.getCustomerCode())) {
//						//添加到集合中
//						customerCodeList.add(billRepaymentEntity.getCustomerCode());
//					} else {
//						continue;
//					}
//				}
//			}
//			//此时已经去掉重复的数据保存在hashset中
//			Set<String> hs = new HashSet<String>(customerCodeList); 
//			//将hashSet数据转化为ArrayList中
//			List<String> newList = new ArrayList<String>(hs);
//			try{
//				//调用判断时候修改灰名单接口
//				grayCustomerService.updateGrayCustomerToECS(newList);
//			} catch (Exception e) {
//				//打印异常
//				LOGGER.info("调用悟空更改灰名单接口异常" + e.getMessage());
//			}		
			//end

			LOGGER.info("补码时自动作废还款单结束..."+entity.getWaybillNo());
	}

	/**
	 * 校验待作废的还款单是否满足作废条件
	 * @author foss-231434-bieyexiong
	 * @date 2016-10-04 
	 */
	private void validateBillRepaymentStatus(BillRepaymentEntity entity,CurrentInfo cInfo){
		LOGGER.info("作废还款单，还款单号：" + entity.getRepaymentNo()+"校验开始");

		// 系统自动生成的还款单不能作废
		if (SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO
				.equals(entity.getCreateType())) {
			throw new SettlementException("系统自动生成的还款单: "
					+ entity.getRepaymentNo() + " 不能作废!");
		}

		// 已审核的还款单不能作废
		if (SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE
				.equals(entity.getAuditStatus())) {
			throw new SettlementException("已审核的还款单: "
					+ entity.getRepaymentNo() + " 不能作废!");
		}

		// 自己录入的还款单不能自己作废 系统自动做，所以不需要这个校验
//		if (entity.getCreateUserCode().equals(userCode)) {
//			throw new SettlementException("自己录入的还款单: "
//					+ entity.getRepaymentNo() + " 不能自己作废!");
//		}

		// 无效或红单还款单不能作废
		if (FossConstants.INACTIVE.equals(entity.getActive())
				|| SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES
						.equals(entity.getIsRedBack())) {
			throw new SettlementException("无效或红单还款单: "
					+ entity.getRepaymentNo() + " 不能作废!");
		}

		// 根据还款单号查询是否存在反核销的还款单
		BillRepaymentEntity redBackEntity = billRepaymentManageDao
				.queryRebackBillByNo(entity.getRepaymentNo(),
						FossConstants.ACTIVE);

		// 如果还款单已做过反核销，则不能作废
		if (redBackEntity != null) {
			throw new SettlementException("已经反核销的还款单: "
					+ entity.getRepaymentNo() + " 不能作废!");
		}

		if(entity.getAmount().compareTo(BigDecimal.ZERO)<0||entity.getBverifyAmount().compareTo(BigDecimal.ZERO)>0){
			throw new SettlementException("执行过反核消的还款单: " + entity.getRepaymentNo()
					+ " 不能被审核!");
		}
		
		// 还款单的可作废时间必须为当前时间30天之内，天数从基础资料读取
		// 当前系统时间、还款单作废时间
		Date nowDate = new Date();
		String sysLimitDateStr = configurationParamsService.queryConfValueByCode(ConfigurationParamsConstants.STL_REPAYMENT_CANCELABLE_DAY);
		
		if(StringUtils.isEmpty(sysLimitDateStr)){
			throw new SettlementException("还款单作废期限未配置 ，请在系统管理的配置参数中设置!");
		}
		
		Date sysLimitDate = DateUtils.addDays(nowDate,
				-Integer.parseInt(sysLimitDateStr));
		if (entity.getBusinessDate().before(sysLimitDate)) {
			throw new SettlementException("选择的还款单:"
					+ entity.getRepaymentNo() + "业务时间超过了还款单作废限制时间,请重新选择!");
		}
		/*作废时检查还款单冲销的应收单对应的运单是否存在劳务费应付单
		 *         7.1 不存在，直接反核消
		 *         7.2 存在，判断劳务费应付单是否已支付
		 *         		7.2.1 已支付，提示不允许反核消
		 *              7.2.1 未支付，失效劳务费应付单，进而反核消
		 */
		//如果核销单开始或结束单号为应收单,获取其对应的运单号
		BillPayableConditionDto billPayableConditionDto = new BillPayableConditionDto();
		//根据还款单号查询有效核销单
		List<BillWriteoffEntity> billWriteoffEntitys = billWriteoffService.queryByRepayment(entity.getRepaymentNo(), FossConstants.ACTIVE);
	    //如果核销单存在，循环处理核销单
		if(!CollectionUtils.isEmpty(billWriteoffEntitys)){
			for(BillWriteoffEntity billWriteoffEntity:billWriteoffEntitys){
				if(SettlementUtil.isReceiveable(billWriteoffEntity.getEndNo())){
					//将该还款单冲销的应收单的运单放入查询条件Dto中
					billPayableConditionDto.setWaybillNo(billWriteoffEntity.getEndWaybillNo());
				}
				//如果运单号存在
				if(StringUtils.isNotEmpty(billPayableConditionDto.getWaybillNo())){
					//根据运单号，查询该运单对应的应付单 
					List<BillPayableEntity> payableList = billPayableService.queryBillPayableByCondition(billPayableConditionDto);
					//如果应付单存在
					if(!CollectionUtils.isEmpty(payableList)){
						//循环应付单列表
						for(BillPayableEntity pay:payableList){
							//如果应付单为装卸费费类型，且为已支付状态，不允许反核消
							if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(pay.getBillType())&&SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(pay.getPayStatus())){
								// 提示：选择的核销单:XXX其相关运单XX的装卸费应付单已支付,不可反核消,请重新选择
								throw new SettlementException("选择的还款单:"+ entity.getRepaymentNo()+ "其相关运单:"+billPayableConditionDto.getWaybillNo()+"的装卸费应付单已支付,不可作废,请重新选择!");
							}
							//如果应付单为装卸费类型，且为未支付状态，将装卸费应付单失效
							if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(pay.getBillType())&&SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__NO.equals(pay.getPayStatus())){
								//失效装卸费应付单
								billPayableService.disableBillPayable(pay, cInfo);
							}
						}
					}
				}
			}
		}
		LOGGER.info("作废还款单，还款单号：" + entity.getRepaymentNo()+"校验结束");
	}
	
	/**
	 * 作废还款操作
	 * @author foss-231434-bieyexiong
	 * @date 2016-10-04 
	 */
	private void disableBillRepaymentList(BillRepaymentEntity entity,CurrentInfo cInfo){

		// 生成操作时间
		Date operateDate = new Date();


		LOGGER.info("作废还款单,还款单号：" + entity.getRepaymentNo());

		// 1、调用反核销服务，红冲核销单及其关联的应收单
		billWriteoffService.writeBackBillWriteoffByRepayment(
				entity.getRepaymentNo(), cInfo);
		LOGGER.info("作废还款单时红冲核销单及关联应收单成功,还款单号：" + entity.getRepaymentNo());

		// 2、调用还款单服务，红冲还款单
		BillRepaymentEntity billRepaymentEntity = billRepaymentService.queryByRepaymentNO(entity.getRepaymentNo(),FossConstants.ACTIVE);
		billRepaymentEntity.setActive(FossConstants.INACTIVE);
		billRepaymentService.writeBackBillRepayment(billRepaymentEntity, cInfo);
		LOGGER.info("作废还款单时红冲还款单成功,还款单号：" + entity.getRepaymentNo());

		// 生成该还款单相关的所有对账单号
		List<String> statementNoList = new ArrayList<String>();
		//生成待反确认对账单号列表
		List<String> unConfirmStatementNoList = new ArrayList<String>();

		// 3、通过查询对账还款表(还款单号)，获取对账单，并修改对账单上的未还款金额为：未还款金额+还款单金额
		// 3.1 新增逻辑：
		//	     根据还款单号获取该还款对应所有对账单号，然后循环对账单号根据每个对账单号获取其生成的有效的核销单列表；
		//		1、如果不为空，即存在有效的核销单，此时不处理对账单确认状态
		//		2、如果为空，即不存在有效的核销单，将该对账单进行反确认

		// 根据还款单号查询对账单还款单关系信息
		List<SoaRepaymentEntity> soaRepaymentList = soaRepaymentManageService
				.queryListByRepaymentNo(entity.getRepaymentNo());

		//对账单还款单关系信息不为空
		if (CollectionUtils.isNotEmpty(soaRepaymentList)) {
			//循环处理还款单对应的对账单号
			for (SoaRepaymentEntity soaEntity : soaRepaymentList) {
				//获取该还款单相关的所有对账单号
				statementNoList.add(soaEntity.getStatementBillNo());
				//根据对账单号获取该对账单生成的全部有效的的核销单 
				List<BillWriteoffEntity> entityList = billWriteoffService.queryByStatementBillNo(soaEntity.getStatementBillNo(), null, null, null, FossConstants.ACTIVE);
				//如果核销单列表为空
				if(CollectionUtils.isEmpty(entityList)){
					//将对账单号加入到待反确认对账单号列表
					unConfirmStatementNoList.add(soaEntity.getStatementBillNo());
				}
			}

			// 根据对账单号集合查询出该还款单对应的所有对账单集合
			List<StatementOfAccountEntity> statementList = statementOfAccountService.queryByStatementNos(statementNoList);
			// 根据对账单号集合查询出该还款单对应的所有代打木架对账单集合
			List<WoodenStatementEntity> woodenList = woodenStatementService.queryWoodenStatementByNumber(statementNoList);
			//该还款单对应的所有对账单集合不为空
			if (CollectionUtils.isNotEmpty(statementList)) {
				// 循环修改对账单
				for (StatementOfAccountEntity stateEntity : statementList) {
					for (SoaRepaymentEntity soaEntity : soaRepaymentList) {

						// 如果该对账单号和对账单还款单关系数据中的对账单相等，则修改未还款金额并保存
						if (soaEntity.getStatementBillNo().equals(stateEntity.getStatementBillNo())) {
							//未还款金额+还款对账单关系表的还款金额
							stateEntity.setUnpaidAmount(stateEntity.getUnpaidAmount().add(soaEntity.getRepaymentAmount()));
							//修改未还款金额
							statementOfAccountService.updateStatementForWriteOff(stateEntity, cInfo);
							LOGGER.info("作废还款单时循环修改对账单的未还款金额,对账单号："+ stateEntity.getStatementBillNo());

							/**
							 * 判断数据是否来自于NCI项目
							 * @author 269052 zhouyuan008@deppon.com
							 */
							/**
							 * 1、判断支付方式是否为银行卡 
							 */
							if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(entity.getPaymentType())){
								/**
								 * 2、获取交易流水号
								 */
								String tradeSerialNo = entity.getBatchNo();

								/**
								 * 获取对账单还款单关系表总的还款金额
								 */
								BigDecimal amount = soaEntity.getRepaymentAmount();//entity.getAmount();soaEntity.

								/**
								 * 3、根据交易流水号和单号去查询明细
								 */
								//获取刷卡明细实体集合
								PosCardDetailEntity detail = new PosCardDetailEntity();
								detail.setInvoiceNo(stateEntity.getStatementBillNo());
								detail.setTradeSerialNo(tradeSerialNo);
								detail.setAmount(amount);
								/**
								 * 直接根据单据号和交易流水号去查询是否为NCI中数据
								 */
								List<PosCardDetailEntity> lists = pdaPosManageService.queryPosDetailsByNo(detail);
								if(CollectionUtils.isNotEmpty(lists)){
									/**
									 * 4 、释放T+0报表的金额和删除明细数据
									 */
									pdaPosManageService.disableRepayment(detail);
								}
							}

						}
					}
				}
				//ddw
			}else if (CollectionUtils.isNotEmpty(woodenList)) {
				// 循环修改对账单
				for (WoodenStatementEntity stateEntity : woodenList) {
					for (SoaRepaymentEntity soaEntity : soaRepaymentList) {

						// 如果该对账单号和对账单还款单关系数据中的对账单相等，则修改未还款金额并保存
						if (soaEntity.getStatementBillNo().equals(stateEntity.getStatementBillNo())) {
							//未还款金额+还款对账单关系表的还款金额
							stateEntity.setUnpaidAmount(stateEntity.getUnpaidAmount().add(soaEntity.getRepaymentAmount()));
							//修改未还款金额
							woodenStatementService.updateStatementForWriteOff(stateEntity, cInfo);
							LOGGER.info("作废还款单时循环修改对账单的未还款金额,对账单号："+ stateEntity.getStatementBillNo());
						}
					}
				}
			}

			//如果待反确认对账单号不为空
			if(CollectionUtils.isNotEmpty(unConfirmStatementNoList)){
				List<StatementOfAccountEntity> unConfirmStatementList = new ArrayList<StatementOfAccountEntity>() ;

				// 根据待反确认对账单号查询所有待反确认的对账单集合
				for(int i=0;i<unConfirmStatementNoList.size();i++){
					StatementOfAccountEntity statementOfAccountEntityTemp = statementOfAccountService.queryByStatementNo(unConfirmStatementNoList.get(i));
					if(null != statementOfAccountEntityTemp){
						if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__CONFIRM.equals(statementOfAccountEntityTemp.getConfirmStatus())){
							unConfirmStatementList.add(statementOfAccountEntityTemp);
						}
					}
				}
				//反确认对账单
				statementOfAccountService.unConfirmStatement(unConfirmStatementList, cInfo);
				LOGGER.info("作废还款单时反确认还款单对应的对账单完成,还款单号："+   entity.getRepaymentNo());
			}

		}

		// 4、记录操作日志（单据信息、作废核人、作废时间）
		OperatingLogEntity logEntity = new OperatingLogEntity();
		logEntity.setOperateBillNo(entity.getRepaymentNo());// 操作单据编号
		logEntity.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_REPAYMENT);// 操作单据类型
		logEntity.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__DISABLE);
		logEntity.setOperateOrgCode(cInfo.getCurrentDeptCode());// 操作部门编码
		logEntity.setOperateOrgName(cInfo.getCurrentDeptName());// 操作部门名称
		logEntity.setOperatorName(cInfo.getEmpName());// 操作人名称
		logEntity.setOperatorCode(cInfo.getEmpCode());// 操作人编码
		logEntity.setOperateTime(operateDate);// 操作时间
		operatingLogService.addOperatingLog(logEntity, cInfo);

		//update by 2016-10-04 231434  只给悟空补码 到付应收单网上支付 的作废还款单使用，不涉及电汇
//		// 5、如果作废的是电汇还款单，且校验通过，且是非初始化数据（Foss生成非迁移），则调用OA反占用汇款接口(开关为true时，调用费控)
//		if (StringUtils.isNotEmpty(entity.getOaPaymentNo())
//			&& FossConstants.NO.equals(entity.getIsInit())
//			&& (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.endsWith(entity.getPaymentType())
//				||SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE.endsWith(entity.getPaymentType()))) {
//			LOGGER.info("作废还款单时调用费控接口释放汇款,汇款单号号：" + entity.getOaPaymentNo());
//			fossToFinanceRemittanceService.releaseClaim(entity.getAmount(),
//					entity.getOaPaymentNo(), entity.getCollectionOrgCode(),entity.getRepaymentNo());
//		}

        //网上支付安全收款
		//Author 105762
		//update by 2016-10-04 231434  悟空补码 到达应收网上支付 对接财务自助在 自动核销之后，反核销时不调用财务自助
        // 6、如果作废的是网上支付还款单，且校验通过，且是非初始化数据（Foss生成非迁移），则调用OA反占用汇款接口(开关为true时，调用费控)
//        if (StringUtils.isNotEmpty(entity.getOnlinePaymentNo()) && FossConstants.NO.equals(entity.getIsInit())
//                && (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(entity.getPaymentType()))) {
//            /*
//            调用财务自助释放网上支付金额
//            */
//            financeOnlinePayWSProxy.release(entity.getOnlinePaymentNo(),entity.getAmount());
//        }
	}

	/**
	 * 补码时自动生成 到达应收网上支付 还款单(还款冲应收)
	 * @author 231434-foss-bieyexiong
	 * @date 2016-10-3 
	 */
	@Override
	public void complementAutoCreateBillRepayment(BillReceivableOnlineQueryDto queryDto,BillReceivableEntity receivableEntity) {
		LOGGER.info("网上支付还款sucessful service,应收单号："+ queryDto.getReceivableNo()+"开始");
		// 校验查询参数是否合法
		// 判断应收单号、在线支付编号、还款金额、记账日期是否为空
		if (StringUtil.isEmpty(queryDto.getReceivableNo())) {
			LOGGER.error("应收单号为空");
			throw new SettlementException("应收单号为空！", "");
		}
		//记录日志
		LOGGER.info("网上支付还款entering service,应收单号：" + queryDto.getReceivableNo());
		//判空
		if (StringUtil.isEmpty(queryDto.getOnlineNo())) {
			//记录日志
			LOGGER.error("在线支付编码为空");
			throw new SettlementException("在线支付编码为空！", "");
		}
		//判空 update by foss-231434-bieyexiong 不需要记账日期
//		if (queryDto.getAccountDate() == null) {
//			//记录日志
//			LOGGER.error("记账日期为空");
//			throw new SettlementException("记账日期为空！", "");
//		}
		//判空
		if (queryDto.getAmount() == null) {
			//记录日志
			LOGGER.error("还款金额为空");
			throw new SettlementException("还款金额为空！", "");
		}
		//判空
		if (queryDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
			//记录日志
			LOGGER.error("还款金额小于等于0");
			throw new SettlementException("还款金额小于等于0！", "");
		}
		// 判断在线支付编号是否已经使用
		BillRepaymentConditionDto dto = new BillRepaymentConditionDto();
		//设置属性值
		dto.setOnlinePaymentNo(queryDto.getOnlineNo());
		dto.setActive(FossConstants.YES);
		//调用接口进行查询
		List<BillRepaymentEntity> list = billRepaymentService.queryBillRepaymentByCondition(dto);
		//判断列表集合
		if (list.size() > 0) {
			//记录日志
			LOGGER.error("在线支付编号已被使用过，请确认是否重复付款");
			throw new SettlementException("在线支付编号已被使用过，请确认是否重复付款！", "");
		}
		// 根据应收单能否被操作
		this.validateReceivableEntity(receivableEntity);
		// 判断还款金额和应收单未核销金额
		if (queryDto.getAmount().compareTo(receivableEntity.getUnverifyAmount()) != 0) {
			//记录日志
			LOGGER.error("还款金额与应收单的未核销金额不等");
			throw new SettlementException("还款金额与应收单的未核销金额不等！", "");
		}

		// 生成还款单信息
		BillRepaymentEntity repaymentEntity = new BillRepaymentEntity();
		//设置属性值
		repaymentEntity.setOnlinePaymentNo(queryDto.getOnlineNo());
		//设置属性值
		repaymentEntity.setId(UUIDUtils.getUUID());
		// 还款单号
		repaymentEntity.setRepaymentNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HK1));
		//设置属性值
		repaymentEntity.setCustomerCode(receivableEntity.getCustomerCode());
		//设置运单号属性值
		repaymentEntity.setWaybillNo(receivableEntity.getWaybillNo());
		//设置属性值
		repaymentEntity.setCustomerName(receivableEntity.getCustomerName());
		// 还款金额、实际还款金额、反核销金额
		repaymentEntity.setAmount(receivableEntity.getUnverifyAmount());
		//设置属性值
		repaymentEntity.setTrueAmount(receivableEntity.getUnverifyAmount());
		//设置属性值
		repaymentEntity.setBverifyAmount(BigDecimal.ZERO);
		// 审核状态、生成方式
		repaymentEntity.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		//设置属性值
		repaymentEntity.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		// 收款部门编码、名称
		repaymentEntity.setCollectionOrgCode(receivableEntity.getReceivableOrgCode());
		repaymentEntity.setCollectionOrgName(receivableEntity.getReceivableOrgName());
		// 根据部门编码获取子公司信息
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(receivableEntity.getReceivableOrgCode());
		//判空
		if (orgEntity != null) {
			// 收款部门公司编码、名称
			repaymentEntity.setCollectionCompanyCode(orgEntity.getSubsidiaryCode());
			//设置属性值
			repaymentEntity.setCollectionCompanyName(orgEntity.getSubsidiaryName());
		}
		//BUG-20628 还款单不需要收入部门及收入部门公司
		// 收入部门编码、名称
		//repaymentEntity.setGeneratingOrgCode(receivableEntity.getGeneratingOrgCode());
		//设置属性值
		//repaymentEntity.setGeneratingOrgName(receivableEntity.getGeneratingOrgName());
		// 收入部门公司编码、名称
		//repaymentEntity.setGeneratingCompanyCode(receivableEntity.getGeneratingComCode());
		//设置属性值
		//repaymentEntity.setGeneratingCompanyName(receivableEntity.getGeneratingComName());
		// 单据类型、是否有效、是否红单、支付方式
		repaymentEntity.setBillType(SettlementDictionaryConstants.BILL_REPAYMENT__BILL_TYPE__REPAYMENT);
		//设置属性值
		repaymentEntity.setActive(FossConstants.ACTIVE);
		//设置属性值
		repaymentEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		//设置属性值
		repaymentEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		// 业务日期、记账日期、是否初始化、版本号
		repaymentEntity.setBusinessDate(new Date());
		//设置属性值
		repaymentEntity.setAccountDate(new Date());
		//设置属性值
		repaymentEntity.setStatus(SettlementDictionaryConstants.BILL_DEPOSIT_RECEIVED__STATUS__SUBMIT);
		//设置属性值
		repaymentEntity.setIsInit(FossConstants.NO);
		//设置属性值
		repaymentEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 创建当前登录信息
		EmployeeEntity employee = new EmployeeEntity();
		//设置属性值
		employee.setEmpCode(SettlementConstants.BHO_CODE);
		//设置属性值
		employee.setEmpName(SettlementConstants.BHO_NAME);
		//实例化
		UserEntity user = new UserEntity();
		//设置属性值
		user.setEmployee(employee);
		//设置属性值
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		//设置属性值
		dept.setCode(receivableEntity.getReceivableOrgCode());
		//设置属性值
		dept.setName(receivableEntity.getReceivableOrgName());
		//获取当前登录人
		CurrentInfo currentInfo = new CurrentInfo(user, dept);
		// 新增还款单信息
		repaymentEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		repaymentEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
		billRepaymentService.addBillRepayment(repaymentEntity, currentInfo);
		// 调用还款冲应收服务
		BillWriteoffOperationDto billWriteoffOperationDto = new BillWriteoffOperationDto();
		//设置属性值
		billWriteoffOperationDto.setBillRepaymentEntity(repaymentEntity);
		//声明
		List<BillReceivableEntity> billReceivableEntitys = new ArrayList<BillReceivableEntity>();
		//设置属性值
		billReceivableEntitys.add(receivableEntity);
		//设置属性值
		billWriteoffOperationDto.setBillReceivableEntitys(billReceivableEntitys);
		// 核销批次号
		billWriteoffOperationDto.setWriteoffBatchNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN));
		// 核销單生成方式（手工輸入）
		billWriteoffOperationDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__MANUAL);
		// 核销网点编码和名称
		BillWriteoffOperationDto resultDto = billWriteoffService.writeoffRepaymentAndReceibable(billWriteoffOperationDto,currentInfo);
		// 核销完毕调用通知对账单接口
		StatementOfAccountUpdateDto updateDto = new StatementOfAccountUpdateDto();
		//设置属性值
		updateDto.setReceivableEntityList(resultDto.getBillReceivableEntitys());
		//调用接口更更新
		statementOfAccountService.updateStatementAndDetailForWriteOff(updateDto, currentInfo);

		//记录日志
		LOGGER.info("网上支付还款sucessful service,应收单号："+ queryDto.getReceivableNo()+"结束");

	}
	
	/**
	 * 校验应收单信息能否被网厅操作
	 * @author foss231434--bieyexiong
	 * @date 2016-10-04
	 */
	private BillReceivableEntity validateReceivableEntity(BillReceivableEntity receivableEntity) {
		// 校验产品类型为空运、偏线的到付应收单不能进行网上支付
		if ((SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(receivableEntity.getBillType()) 
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE.equals(receivableEntity.getBillType())
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY.equals(receivableEntity.getBillType()))
				&&(PricingConstants.ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(receivableEntity.getProductCode())
						||PricingConstants.ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(receivableEntity.getProductCode())
						)) {
			//记录日志
			LOGGER.error("运输性质为空运、偏线的到付应收单不能进行网上支付！");
			throw new SettlementException("运输性质为空运、偏线的到付应收单不能进行网上支付！","");
		}



		// 校验应收单信息是否合法
		// 校验应收单的未核销金额是否大于0；
		if (receivableEntity.getUnverifyAmount().compareTo(BigDecimal.ZERO) <= 0) {
			//记录日志
			LOGGER.error("该应收单未核销金额为0！不能操作");
			throw new SettlementException("该应收单未核销金额为0！不能操作", "");
		}
		// 校验应收单的类型只能始发应收、专线到付运费
		if (!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.equals(receivableEntity.getBillType()) 
				|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(receivableEntity.getBillType()))) {
			//记录日志
			LOGGER.error("该应收单类型只能为始发应收、到付应收，其余类型不能进行网上支付！");
			throw new SettlementException("该应收单类型只能为始发应收、到付应收，其余类型不能进行网上支付！","");
		}
		// 不能操作有代收货款的专线到付运费应收单
		if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE.equals(receivableEntity.getBillType())) {
			//调用接口查询
			CODEntity codEntity = codCommonService.queryByWaybill(receivableEntity.getWaybillNo());
			//判空
			if (codEntity != null) {
				//记录日志
				LOGGER.error("该专线到付应收单存在代收货款记录！不能操作");
				throw new SettlementException("该专线到付应收单存在代收货款记录！不能操作", "");
			}
		}
		// 只能操作没有进入对账单或者进入对账单但对账单未确认
		// 对账单号集合
		List<String> statementNos = new ArrayList<String>();
		//校验是否存在对账单号
		if (SettlementConstants.DEFAULT_BILL_NO.equals(receivableEntity.getStatementBillNo())) {
			//添加对账单号
			statementNos.add(receivableEntity.getStatementBillNo());
		}
		//查询
		List<String> confirmList = statementOfAccountService.queryConfirmStatmentOfAccount(statementNos);
		//判空
		if (CollectionUtils.isNotEmpty(confirmList)) {
			//记录日志
			LOGGER.error("该应收单已经入确认的对账单！不能操作");
			throw new SettlementException("该应收单已经入确认的对账单！不能操作", "");
		}
		// 与应收单相关联的运单不存在有未受理的更改单信息
		// 运单号集合
		if (StringUtil.isNotEmpty(receivableEntity.getWaybillNo())) {
			//实例化实体
			List<String> waybillNos = new ArrayList<String>();
			//添加运单号
			waybillNos.add(receivableEntity.getWaybillNo());
			//判断是否存在更改单
			List<String> changeWaybillNoList = waybillRfcService.isExsitsWayBillRfcs(waybillNos);
			//判空
			if (CollectionUtils.isNotEmpty(changeWaybillNoList)) {
				//记录日志
				LOGGER.error("该应收单存在未受理的更改单！不能操作");
				throw new SettlementException("该应收单存在未受理的更改单！不能操作", "");
			}
		}
		return receivableEntity;
	}

	public void setBillRepaymentManageDao(
			IBillRepaymentManageDao billRepaymentManageDao) {
		this.billRepaymentManageDao = billRepaymentManageDao;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setSoaRepaymentManageService(
			ISoaRepaymentManageService soaRepaymentManageService) {
		this.soaRepaymentManageService = soaRepaymentManageService;
	}

	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}

	public void setWoodenStatementService(
			IWoodenStatementService woodenStatementService) {
		this.woodenStatementService = woodenStatementService;
	}

	public void setPdaPosManageService(IPdaPosManageService pdaPosManageService) {
		this.pdaPosManageService = pdaPosManageService;
	}

	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		this.operatingLogService = operatingLogService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}
	
}
