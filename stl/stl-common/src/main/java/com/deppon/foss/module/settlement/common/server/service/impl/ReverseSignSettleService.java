package com.deppon.foss.module.settlement.common.server.service.impl;

import com.deppon.esb.header.ESBHeader;
import com.deppon.fin.module.claimpay.service.CommonException;
import com.deppon.fin.module.claimpay.service.IFinClaimNumService;
import com.deppon.fin.module.claimpay.shared.domain.ReleaseClaimNumRequest;
import com.deppon.fin.module.claimpay.shared.domain.ReleaseClaimNumResponse;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.dao.IBillPayableEntityDao;
import com.deppon.foss.module.settlement.common.api.server.dao.IReverseSignSettleDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillRepaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ICustomerBargainService;
import com.deppon.foss.module.settlement.common.api.server.service.IFinanceOnlinePayWSProxy;
import com.deppon.foss.module.settlement.common.api.server.service.IPODService;
import com.deppon.foss.module.settlement.common.api.server.service.IReverseSignSettleService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.AutoReverseSignSettleRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.PosCardDetailEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCODEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSCodAuditEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSResverSettleRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSReverseSettlRequest;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillSignResultEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.VTSWaybillTransactionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.WaybillEntityForVTS;
import com.deppon.foss.module.settlement.common.api.shared.dto.ActualFreightVTSEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.PaymentSettlementDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.VTSCodAuditDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.WaybillFRcQueryByWaybillNoVTSDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.api.shared.util.BigDecimalOperationUtil;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.Holder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 
 * @author 218392 张永雪 FOSS结算开发组
 * @date 2016-06-12 15:52:20
 * vts自动反签收反结清service
 *
 */
public class ReverseSignSettleService implements IReverseSignSettleService{
	
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReverseSignSettleService.class);
	
	/**
	 * 注入dao reverseSignSettleDao
	 */
	private IReverseSignSettleDao reverseSignSettleDao;
	
	/** 
	 * 注入应付单公共Service. 
	 */
	private IBillPayableService billPayableService;
	
	/** 
	 * 注入对账单Service
	 */
	private IStatementOfAccountService statementOfAccountService;
	
	/**
	 * 现金收款单公共Service
	 */
	private IBillCashCollectionService billCashCollectionService;
	
	/**
	 * 注入应收单公用Service
	 */
	private IBillReceivableService billReceivableService;
	
	/** 
	 * 注入财务签收记录Service
	 */
	private IPODService podService;
	
	/**
	 * 结算公用Service
	 */
	private ISettlementCommonService settlementCommonService;
	
	/**
	 * 扣减客户信用额度Service
	 */
	private ICustomerBargainService customerBargainService;
	
	/**
	 * 还款单公用 Service
	 */
	private IBillRepaymentService billRepaymentService;
	
	/**
	 * 核销service
	 */
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 注入费控接口service
	 */
	private IFinClaimNumService claimNumService;
	
	/**
	 * 网上支付的款项处理
	 */
	private IFinanceOnlinePayWSProxy financeOnlinePayWSProxy;
	
	/**
	 * 应付单Dao
	 */
	private IBillPayableEntityDao billPayableEntityDao;
	
	/**
	 * 反签收的校验
	 */
	@Override
	@Transactional
	public String checkReverseSign(AutoReverseSignSettleRequestEntity reverseSignSettleDto) {
		
		if(reverseSignSettleDto == null){
			throw new SettlementException("校验反签收参数为空！");
		}
		
		//表示校验不成功
		String msg = "N";
		
		//1.新建UserEntiy实体
		String empCode = reverseSignSettleDto.getEmpCode();
		String empName = reverseSignSettleDto.getEmpName();
		
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode(empCode);
		employeeEntity.setEmpName(empName);
		
		UserEntity user = new UserEntity();
		user.setEmployee(employeeEntity);
		user.setEmpCode(empCode);
		user.setEmpName(empName);
		
		//2.新建OrgAdministrativeInfoEntity实体
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(reverseSignSettleDto.getCurrentDeptCode());
		dept.setName(reverseSignSettleDto.getCurrentDeptName());
		//3.对CurrentInfo执行构造函数，将值设置到CurrentInfo中
		CurrentInfo currentInfo = new CurrentInfo(user,dept);
		/**
		 * 1.校验资金部锁定的
		 * @author 218392 张永雪
		 * 配合代收货款支付审核 需求校验：
		 * （1）对于资金安全控制组锁定后的代收单据：
		 * 到达部门所有人员无法对此单据进行反签收申请、更改签收结果申请操作。
		 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁”
		 * （2）资金复核小组锁定后的代收单据：
		 * 到达部门所有人员无法对此单据进行反签收申请、更改签收结果申请操作。
		 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
		 */

		//获取运单号
		String waybillNo = reverseSignSettleDto.getWaybillNo();
		if(StringUtils.isEmpty(waybillNo)){
			throw new SettlementException("vts反签收运单号为空！");
		}
		if(StringUtils.isNotEmpty(waybillNo)){
			VTSCodAuditDto dto = new VTSCodAuditDto();
			List<String> waybillNoList = new ArrayList<String>();
			waybillNoList.add(waybillNo);
			dto.setWaybillNos(waybillNoList);
			List<VTSCodAuditEntity> codAuditEntityList = this.queryCodAuditEntityByCondition(dto);//根据单号查询
			if(codAuditEntityList != null && codAuditEntityList.size() > 0){
				VTSCodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
				if(codAuditEntity != null){
					//如果为资金部锁定
					if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
						throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
					}
					if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
						throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
					}
				}
			}else{
				LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
			}
		}
		
		//查询代收货款信息
		VTSCODEntity vTSCODEntity = new VTSCODEntity();
		vTSCODEntity.setWaybillNo(waybillNo);
		vTSCODEntity.setActive("Y");
		VTSCODEntity cod = reverseSignSettleDao.queryByWaybillNo(vTSCODEntity);
		if (cod != null) {
			if (SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE.equals(cod.getStatus())
					|| SettlementDictionaryConstants.COD__STATUS__RETURNING.equals(cod.getStatus())
					|| SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(cod.getStatus())) {
				throw new SettlementException("资金部冻结或存在退款中或退款成功的代收货款，不能反签收操作!");
			}
		}
		
		/**
		 * 2.根据单号+active=Y 查询签收结果表信息，有的话置成无效N
		 * (1)先查询
		 * (2)根据ID修改
		 */
		VTSWaybillSignResultEntity vTSWaybillSignResultEntity = new VTSWaybillSignResultEntity();
		vTSWaybillSignResultEntity.setWaybillNo(waybillNo);
		vTSWaybillSignResultEntity.setActive("Y");
		LOGGER.info("vts反签收校查询签收结果表开始!单号为:" + waybillNo);
		vTSWaybillSignResultEntity = reverseSignSettleDao.queryWaybillSignResult(vTSWaybillSignResultEntity);
		
		if (vTSWaybillSignResultEntity != null) {
			// 作废运单签收结果里当前运单号
			Date modifyTime = new Date();//修改时间
			String id = vTSWaybillSignResultEntity.getId();//获取ID
			VTSWaybillSignResultEntity updateSignResultEntity = new VTSWaybillSignResultEntity();
			updateSignResultEntity.setModifyTime(modifyTime);//set修改时间
			updateSignResultEntity.setActive("N");//set有效状态为N
			updateSignResultEntity.setId(id);//set主键ID，根据这个ID来修改签收结果表为N
			reverseSignSettleDao.updateWaybillSignResult(updateSignResultEntity);
		} else {
			LOGGER.error("运单号" + waybillNo + "在运单签收结果里不存在");//记录日志
			throw new SettlementException("FOSS签收结果表中不存在有效的单号为" + waybillNo + "的记录信息！");
		}
		LOGGER.info("vts反签收校查询签收结果表结束!单号为:" + waybillNo);
		
		/**
		 * 3.校验应付单:断不能存在服务补救应付单和理赔应付单
		 */
		LOGGER.info("vts反签收校验应付单开始!单号为:" + waybillNo);
		BillPayableConditionDto billPayableConDto = new BillPayableConditionDto();
		billPayableConDto.setWaybillNo(waybillNo);//设置运单号
		//设置应付单类型
		billPayableConDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM,// 理赔应付单
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION,// 服务补救应付单
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,// 代收货款应付单
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST,// 整车尾款应付单
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
				});
		//查询应付单
		List<BillPayableEntity> billPayables = this.billPayableService.queryBillPayableByCondition(billPayableConDto);
		if (CollectionUtils.isNotEmpty(billPayables)) {
			// 需要判断对应的对账单是否已经确认，已确认不能进行反签收操作
			List<String> sourcesStatementNos = new ArrayList<String>();
			// 反签收时，验证应付单信息
			checkBillPayableByReverseConfirmIncome(billPayables, sourcesStatementNos);
			if (CollectionUtils.isNotEmpty(sourcesStatementNos)) {
				List<String> soaIds = statementOfAccountService.queryConfirmStatmentOfAccount(sourcesStatementNos);
				// 对账单已被确认，不能进行反签收操作
				if (CollectionUtils.isNotEmpty(soaIds)) {
					throw new SettlementException("应付单对应的对账单已被客户/代理确认，不能进行反签收操作");
				}
			}
		}
		//失效应付单
		LOGGER.info("反签收-开始失效应付单操作，运单号为:" + waybillNo);
		if (CollectionUtils.isNotEmpty(billPayables)) {
			//1.失效代收货款应付单和装卸费应付单，并且清楚日期
			for (int i = 0; i < billPayables.size(); i++) {
				BillPayableEntity entity = billPayables.get(i);
				if ((SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD.equals(entity.getBillType()) ||
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE.equals(entity.getBillType()))) {
					// 失效应付单
					entity.setSignDate(null);
					this.billPayableService.disableBillPayable(entity, currentInfo);
				}
			}
			
			//2.清空整车首尾款应付单的签收日期
			for(int i = 0; i < billPayables.size(); i++){
				BillPayableEntity entity = billPayables.get(i);
				if ((SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.equals(entity.getBillType()) ||
						SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.equals(entity.getBillType()))) {
					// 失效应付单
					entity.setSignDate(null);
					//获取一个格式化到秒的时间对象
					Date now = Calendar.getInstance().getTime();
					//修改时间
					entity.setModifyTime(now);
					// 操作者信息
					entity.setModifyUserCode(currentInfo.getEmpCode());
					//修改人名称
					entity.setModifyUserName(currentInfo.getEmpName());
					
					int billNum = billPayableEntityDao.updateByTakeEffect(entity);
					if (billNum != 1) {
						throw new SettlementException("清空整车首尾款应付单签收日期出错!");
					}
					LOGGER.info("successfully exit service, id: " + entity.getId());
					
				}
			}
			
			LOGGER.info("反签收-失效应付单成功单号为:" + waybillNo);
		}
		
		/**
		 * 4.校验现金收款单
		 */
		LOGGER.info("vts反签收校验现金收款单开始!单号为:" + waybillNo);
		BillCashCollectionEntity cashCollectionEntity = null;
		List<String> sourceBillNos = new ArrayList<String>();
		sourceBillNos.add(waybillNo);//设置来源单号
		//查询现金收款单
		List<BillCashCollectionEntity> cashCollections = billCashCollectionService.queryBySourceBillNOs(
			sourceBillNos,SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,FossConstants.ACTIVE);
		if (CollectionUtils.isNotEmpty(cashCollections)){
			if (cashCollections.size() > 1){
				throw new SettlementException("存在多条现金收款单");
			}
			cashCollectionEntity = cashCollections.get(0);
			// 现金收款单的确认收入日期为空，说明运单并未签收，直接抛出异常信息
			if (cashCollectionEntity.getConrevenDate() == null){
				throw new SettlementException("运单还未签收，不能进行反签收操作");
			}
		}
		LOGGER.info("vts反签收校验现金收款结束!单号为:" + waybillNo);
		
		/**
		 * 5.校验应收单
		 */
		LOGGER.info("vts反签收校验应收单开始!单号为:" + waybillNo);
		List<BillReceivableEntity> billReceivables = null;
		// 反签收-反确认收入：始发应收单/到达
		BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto();
		billReceivableConDto.setWaybillNo(waybillNo);
		// 到达的应收单类型，默认为专线：查询始发应收单和到达运费应收单
		String billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;
		billReceivableConDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
				billType,
				SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE});
		//查询应收单
		billReceivables = this.billReceivableService.queryBillReceivableByCondition(billReceivableConDto);
		if (CollectionUtils.isNotEmpty(billReceivables)){
			// 验证一个运单是否存在相同类型的多条应收单
			if (CollectionUtils.isNotEmpty(billReceivables)) {
				int orig = 0;// 始发标记
				int dest = 0;// 到达标记
				int codNum = 0;// 代收货款标记
				for (BillReceivableEntity entity : billReceivables) {
					if (entity != null) {
						if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
								.equals(entity.getBillType())) {
							orig += 1;
							if (orig > 1) {
								throw new SettlementException("同一个运单，存在多条始发应收单");
							}
						} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
								.equals(entity.getBillType())// 专线到达运费应收单
								|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
										.equals(entity.getBillType())// 偏线到达运费应收单
								|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
										.equals(entity.getBillType())// 空运到达运费应收单
								|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
								.equals(entity.getBillType()) // 到达快递代理应收 ISSUE-3389 小件业务
						          ) {
							dest += 1;
							if (dest > 1) {
								throw new SettlementException("同一个运单，存在多条到达运费应收单");
							}
						} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
								.equals(entity.getBillType())
								|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
										.equals(entity.getBillType())
								|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
										.equals(entity.getBillType()) // 快递代理代收货款应收单 ISSUE-3389 小件业务
								  ) {
							codNum += 1;
							if (codNum > 1) {
								throw new SettlementException("同一个运单，存在多条代收货款应收单");
							}
						}
					}
				}
			}
			// 反签收时，校验应收单签收日期
			for (BillReceivableEntity billReceivableEntity : billReceivables) {
				if (billReceivableEntity.getConrevenDate() == null) {
					throw new SettlementException("运单还未签收，不能进行反签收操作!应收单没有确定收入日期值!");
				}
			}
		}
		LOGGER.info("vts反签收校验应收单结束!单号为:" + waybillNo);
		
		/**
		 * 6.反确认收入日期：反确认现金收款单+应收单收入日期为空
		 */
		LOGGER.info("vts反签收反确认收入日期开始!单号为:" + waybillNo);
		if (CollectionUtils.isNotEmpty(billReceivables))
		{
			// 反应收单确认收入日期
			for (BillReceivableEntity entity : billReceivables)
			{
				// 设置确认收入日期为空
				entity.setConrevenDate(null);
				// 调用公用应收单反签收时置空确认收入日期
				this.billReceivableService.updateBillReceivableByReverseConfirmIncome(entity,currentInfo);
			}
		}
		// 反签收，反确认现金收款单
		if (cashCollectionEntity != null){
			// 设置确认收入日期为空
			cashCollectionEntity.setConrevenDate(null);
			this.billCashCollectionService.reverseConfirmIncomeBillCashCollection(cashCollectionEntity, currentInfo);
		}
		LOGGER.info("vts反签收反确认收入日期结束!单号为:" + waybillNo);
		
		/**
		 * 7.POD表校验
		 */
		LOGGER.info("vts反签收插入POD表UPD开始,单号为:" + waybillNo);//记录日志
		PODEntity p = podService.queryNewestPOD(waybillNo);
		if (p == null) {
			throw new SettlementException("不存在签收记录，不能反签收");
		} else if (!p.getPodType().equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
			throw new SettlementException("运单未签收，不能反签收");
		}
		// 新增签收记录表服务
		PODEntity podEntity = new PODEntity();
		podEntity.setId(UUIDUtils.getUUID());// ID
		podEntity.setWaybillNo(waybillNo);// 运单号
		podEntity.setPodDate(new Date());// 签收/反签收日期
		podEntity.setPodType("UPD");// 签收/反签收类型
		podService.addPOD(podEntity, currentInfo);
		LOGGER.info("vts反签收插入POD表UPD结束,单号为:" + waybillNo);//记录日志
		
		/**
		 * 8.反业务完结
		 */
		LOGGER.info("vts反签收反业务完结开始,单号为:" + waybillNo);//记录日志
		VTSWaybillTransactionEntity waybillTransactionEntity = new VTSWaybillTransactionEntity();
		waybillTransactionEntity.setBusinessOver("N");
		waybillTransactionEntity.setWaybillNo(waybillNo);
		waybillTransactionEntity.setModifyDate(new Date());
		reverseSignSettleDao.updateReverseBusinessOver(waybillTransactionEntity);
		LOGGER.info("vts反签收反业务完结结束,单号为:" + waybillNo);//记录日志
		
		msg = "Y";
		return msg;
	}
	
	
	/**
	 * 反结清
	 */
	@Override
	@Transactional
	public String checkReverseSettle(AutoReverseSignSettleRequestEntity reverseSettleDto) {
		
		if(reverseSettleDto == null){
			throw new SettlementException("vts反结清开始传入参数空！");
		}
		
		//定义反结清返回的消息字符串
		String msgSettle = "N";
		
		//1.新建UserEntiy实体
		String empCode = reverseSettleDto.getEmpCode();
		String empName = reverseSettleDto.getEmpName();
		
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode(empCode);
		employeeEntity.setEmpName(empName);
		
		UserEntity user = new UserEntity();
		user.setEmployee(employeeEntity);
		user.setEmpCode(empCode);
		user.setEmpName(empName);
		
		//2.新建OrgAdministrativeInfoEntity实体
		OrgAdministrativeInfoEntity dept = new OrgAdministrativeInfoEntity();
		dept.setCode(reverseSettleDto.getCurrentDeptCode());
		dept.setName(reverseSettleDto.getCurrentDeptName());
		//3.对CurrentInfo执行构造函数，将值设置到CurrentInfo中
		CurrentInfo currentInfo = new CurrentInfo(user,dept);
		
		/**
		 * 1.代收货款资金部锁定校验
		 * @author 218392 张永雪
		 * @date 2016-06-14 23:21:30
		 * 配合代收货款支付审核 需求校验：
		 * (1)对于资金安全控制组锁定后的代收单据：
		 * 到达部门所有人员无法对此单据进行反签收申请、更改签收结果申请操作。
		 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁”
		 * (2)资金复核小组锁定后的代收单据：
		 * 到达部门所有人员无法对此单据进行反签收申请、更改签收结果申请操作。
		 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
		 */
		//获取运单号
		String waybillNo = reverseSettleDto.getWaybillNo();
		if(StringUtils.isEmpty(waybillNo)){
			throw new SettlementException("vts反结清运单号为空！");
		}
		LOGGER.info("反结清校验开始，单号为："+waybillNo+"代收货款资金部锁定校验");
		if(StringUtils.isNotEmpty(waybillNo)){
			VTSCodAuditDto dto = new VTSCodAuditDto();
			List<String> waybillNoList = new ArrayList<String>();
			waybillNoList.add(waybillNo);
			dto.setWaybillNos(waybillNoList);
			List<VTSCodAuditEntity> codAuditEntityList = this.queryCodAuditEntityByCondition(dto);//根据单号查询
			if(codAuditEntityList != null && codAuditEntityList.size() > 0){
				VTSCodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
				if(codAuditEntity != null){
					//如果为资金部锁定
					if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
						throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
					}
					if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
						throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
					}
				}
			}else{
				LOGGER.info("单号：" + waybillNo + "没有进入代收货款支付审核！");
			}
		}
		
		
		//获取到达付款信息的ID
		List<String> idsList = reverseSettleDto.getRepaymentIds();
		if(CollectionUtils.isEmpty(idsList)){
			throw new SettlementException("VTS没有传没有到达付款信息ID！");
		}
		//根据ID集合，批量查询到达付款信息记录
		List<VTSRepaymentEntity> vTSRepaymentEntityList = reverseSignSettleDao.searchRepaymentById(idsList);
		
		/**
		 * 循环对到达付款信息实体判断付款方式，然后反结清
		 */
		if(CollectionUtils.isNotEmpty(vTSRepaymentEntityList)){
			for(VTSRepaymentEntity repaymentEntity : vTSRepaymentEntityList){
				//运单号
				String repaymentWaybillNo = repaymentEntity.getWaybillNo();
				if(StringUtils.isEmpty(repaymentWaybillNo)){
					throw new SettlementException("到达付款信息表中运单表为空！");
				}
				
				/**
				 * 2.作废到达付款信息无效：到达付款信息表  PKP.T_SRV_REPAYMENT  active=’N’
				 */
				reverseSignSettleDao.updateRepaymentById(repaymentEntity);
				// 代收货款
				BigDecimal receiveableAmount = repaymentEntity.getCodAmount();
				// 实付运费
				BigDecimal receiveablePayAmoout = repaymentEntity.getActualFreight();

				// 获取原始付款方式
				String paymentType = repaymentEntity.getPaymentType();
				// 封装结算结清货款dto
				PaymentSettlementDto dto = new PaymentSettlementDto();
				// 运单号
				dto.setWaybillNo(repaymentWaybillNo);
				// 付款类型
				dto.setPaymentType(repaymentEntity.getPaymentType());
				// 部门code
				dto.setDestOrgCode(repaymentEntity.getOperateOrgCode());
				// 部门名称
				dto.setDestOrgName(repaymentEntity.getOperateOrgName());
				// 客户code
				dto.setCustomerCode(repaymentEntity.getConsigneeCode());
				// 客户名称
				dto.setCustomerName(repaymentEntity.getConsigneeName());
				// 时间
				dto.setBusinessDate(repaymentEntity.getPaymentTime());
				// 实收代收货款费用
				dto.setCodFee(repaymentEntity.getCodAmount());
				// 实收到付运费
				dto.setToPayFee(repaymentEntity.getActualFreight());
				// 币种
				dto.setCurrencyCode(repaymentEntity.getCurrencyCode());
				// 付款编号
				dto.setSourceBillNo(repaymentEntity.getRepaymentNo());
				
				if ((SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT).equals(paymentType) // 如果原始付款方式为临欠或月结时 调用结算接口-反到付运费结转临欠/月结
						|| (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT).equals(paymentType)) {
					
					LOGGER.info("VTS自动反结清开始-付款方式为临欠或月结" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
					/**
					 * 4. ISSUE-3281 查询最新的签收记录，如果签收类型为POD(已签收)，则不允许继续操作
					 */
					PODEntity podEntity = podService.queryNewestPOD(repaymentEntity.getWaybillNo());
					if (podEntity != null
							&& podEntity.getPodType()
									.equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
						throw new SettlementException("运单已经签收，不能结清货款");
					}
					LOGGER.info(" 开始反到付转临欠/月结操作，运单号为：" + dto.getWaybillNo());
					if (StringUtils.isEmpty(dto.getDestOrgCode())) {
						throw new SettlementException("到达部门编码不能为空！");
					}
					
					/**
					 * 5. 验证运单信息
					 * （1）验证运单是否存在
					 * （2）验证是否存在未受理的更改单
					 */
					WaybillEntityForVTS  waybillEntityForVTS= reverseSignSettleDao.queryWaybillByNo(repaymentEntity.getWaybillNo());
					if(waybillEntityForVTS == null){
						throw new SettlementException("不存在有效的运单，不允许反结清！");
					}
					/**
					 * 6.判断是否存在未受理的更改单
					 */
					WaybillFRcQueryByWaybillNoVTSDto waybillFRcQueryByWaybillNoVTSDto = new WaybillFRcQueryByWaybillNoVTSDto();
					waybillFRcQueryByWaybillNoVTSDto.setWaybillNo(repaymentWaybillNo);
					String isWaybillNo = reverseSignSettleDao.queryWaybillRfcByWaybillNo(waybillFRcQueryByWaybillNoVTSDto);
					if (StringUtils.isEmpty(isWaybillNo)) {
						throw new SettlementException("运单" + dto.getWaybillNo() + "存在未受理的更改单");
					}
					LOGGER.info("调用接送货校验规则成功！单号：" + isWaybillNo);
					
					/**
					 * 7.校验应收单
					 */
					// 根据传入的运单号和单据类型等参数，查询[到付运费]有效应收单信息
					BillReceivableConditionDto receivableDto = new BillReceivableConditionDto(dto.getWaybillNo());
					receivableDto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE});// 到达应收单
					List<BillReceivableEntity> list = billReceivableService.queryBillReceivableByCondition(receivableDto);
					// 不存在到付运费应收单，不能进行反到付运费转临欠月结操作
					if (CollectionUtils.isEmpty(list)) {
						throw new SettlementException("不存在到达运费应收单，不能进行反到付运费转临欠月结操作");
					}
					//校验重复性：系统中存在多个到达运费应收单，不能进行反到付运费转临欠月结操作
					if (CollectionUtils.isNotEmpty(list)) {
						int dest = 0;// 到达标记
						int cod = 0;// 代收货款标记
						for (BillReceivableEntity entity : list) {
							if (entity != null) {
							   if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
										.equals(entity.getBillType())// 专线到达运费应收单
										|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE
												.equals(entity.getBillType())// 偏线到达运费应收单
										|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY
												.equals(entity.getBillType())// 空运到达运费应收单
										|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE
										.equals(entity.getBillType()) // 到达快递代理应收 ISSUE-3389 小件业务
								          ) {
									dest += 1;
									if (dest > 1) {
										throw new SettlementException("同一个运单，存在多条到达运费应收单");
									}
								} else if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
										.equals(entity.getBillType())
										|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
												.equals(entity.getBillType())
										|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
												.equals(entity.getBillType()) // 快递代理代收货款应收单 ISSUE-3389 小件业务
										  ) {
									cod += 1;
									if (cod > 1) {
										throw new SettlementException("同一个运单，存在多条代收货款应收单");
									}
								}
							}
						}
					}
					BillReceivableEntity entity = (BillReceivableEntity) list.get(0);
					// 当前操作时间
					Date date = dto.getBusinessDate() != null ? dto.getBusinessDate() : new Date();
					if (entity.getUnlockDateTime() != null && date.before(entity.getUnlockDateTime())) {
						throw new SettlementException("应收单已经被锁定，不能进行反到付运费转临欠月结操作");
					}
					if (entity.getVerifyAmount() != null
							&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
						throw new SettlementException("应收单已核销，不能反到付运费结转临欠月结操作");
					}
					// 对账单号，不为空且不为N/A，验证对账单是否已经被确认
					if (StringUtils.isNotEmpty(entity.getStatementBillNo())
							&& !SettlementConstants.DEFAULT_BILL_NO.equals(entity.getStatementBillNo())) {
						List<String> souIds = statementOfAccountService.queryConfirmStatmentOfAccount(Arrays
								.asList(entity.getStatementBillNo()));
						if (CollectionUtils.isNotEmpty(souIds)) {
							throw new SettlementException("到达应收单对应的对账单已被客户/代理确认，不能进行反签收操作");
						}
					}
					// 应收单已确认收入，付款方式没有发生改变，不能进行反到付运费结转临欠月结操作
					if (entity.getConrevenDate() != null
							&& !SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
									.equals(dto.getPaymentType())) {
						// 签收以后，如果接口传入的付款方式不为到付，不能进行反到付运费结转临欠月结操作
						throw new SettlementException("应收单已确认收入，不能进行反到付运费转临欠月结操作");
					}
					// 查询到的应收单的应收部门，和传入的到达部门不一致的情况下
					if (StringUtils.isNotEmpty(dto.getDestOrgCode()) && !FossConstants.YES.equals(entity.getUnifiedSettlement())
							&& !dto.getDestOrgCode().equals(entity.getReceivableOrgCode()) ) {
						throw new SettlementException("传入的到达部门和和应收单的应收部门不一致");
					}
					// 判断应收单的付款方式为到付时，返回提示信息
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(entity
							.getPaymentType())) {
						throw new SettlementException("已存在付款方式为到付的应收单");
					}
					dto.setRevers(true);// 反操作方法
					// 调用红冲应收单方法，生成蓝单
					writeBackBillReceivableByPayable(entity, dto, currentInfo);
					
					/**
					 * 8.更新结清状态为N：更新实际承运表的状态为N
					 */
					updateSettleStatus(repaymentEntity, FossConstants.NO);
					
					LOGGER.info("VTS自动反结清结束-付款方式为临欠或月结" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
				}else{
					/**
					 * 反结清付款方式为银行卡的时候，释放T+0交易流水号金额
					 */
					if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD.equals(paymentType)){
						PosCardDetailEntity query = new PosCardDetailEntity();
						query.setInvoiceNo(repaymentWaybillNo);
						query.setInvoiceType("W2");
						List<PosCardDetailEntity> lists = reverseSignSettleDao.queryDetailsByNo(query);	
						BigDecimal	total = repaymentEntity.getActualFreight()==null?BigDecimal.ZERO:repaymentEntity.getActualFreight()
								.add(repaymentEntity.getCodAmount()==null?BigDecimal.ZERO:repaymentEntity.getCodAmount());
						if (lists!=null&&lists.size()>0)
							for (PosCardDetailEntity posCardDetailEntity : lists) {
								if(total.compareTo(BigDecimal.ZERO)==1)
									if(posCardDetailEntity.getTradeSerialNo().equals(repaymentEntity.getClaimNo())){
										posCardDetailEntity.setModifyUser(currentInfo.getEmpName());
										posCardDetailEntity.setModifyUserCode(currentInfo.getEmpName());
										//如果反结清金额等于该明细的金额，更新T+0，同时删除明细
										if (total.compareTo(posCardDetailEntity.getOccupateAmount())==0) {
											// 根据交易流水号更新已使用流水号金额和未使用金额
											reverseSignSettleDao.updatePosByNoForMoney(posCardDetailEntity);
											// 更新一个，释放一个 删除明细，将明细isDelete 置为Y
											reverseSignSettleDao.deleteDetailByNo(posCardDetailEntity);
											break;
										}else{//如果反结清金额小于该明细金额，更新T+0金额，同时更新明细中金额
											posCardDetailEntity.setOccupateAmount(total);
											// 根据交易流水号更新已使用流水号金额和未使用金额
											reverseSignSettleDao.updatePosByNoForMoney(posCardDetailEntity);
											//插入新的POS刷卡明细
											//准备数据
											posCardDetailEntity.setOccupateAmount(total.multiply(new BigDecimal("-1")));
											//验证 posCardDetailEntity
											// 交易流水号
											if (StringUtil.isBlank(posCardDetailEntity.getTradeSerialNo())) {
												throw new SettlementException("VTS反结清数据错误：交易流水号不能为空！");
											}
											// 单据号
											if (StringUtil.isBlank(posCardDetailEntity.getInvoiceNo())) {
												throw new SettlementException("VTS反结清数据错误：单据号不能为空！");
											}
											// 金额必须大于零
											if (posCardDetailEntity.getAmount().compareTo(BigDecimal.ZERO) > 1) {
												throw new SettlementException("VTS反结清数据错误：金额必须大于零！");
											}
											// 插入或更新数据
											reverseSignSettleDao.insertOrUpdateDetail(posCardDetailEntity);	
										}
									}
							}
						/**
						 * 更新结清状态为N：更新实际承运表的状态为N
						 */
						updateSettleStatus(repaymentEntity, FossConstants.NO);
						
					}
					
					/**
					 * 9.如果反结清的付款方式不为临欠、月结：当实收货款和到付运费都不为0时
				     *（1）红冲代收还款单；红冲运费还款单
			         *（2）释放财务自助汇款编号，释放网上金额如果为网上支付OL
			         *（3）反核销：红冲核销单
			         *（4）更新结清状态为未结清 --避免出现异常同步接口操作问题
					 */
					// 当实收货款和到付运费不都为0时
					if (!BigDecimalOperationUtil.compare(receiveableAmount, BigDecimal.ZERO)
							|| !BigDecimalOperationUtil.compare(receiveablePayAmoout, BigDecimal.ZERO)) {
						// 记录日志
						LOGGER.info("调用结算接口开始" + "运单号：" + repaymentEntity.getWaybillNo());// 记录日志
						// 调用反确认付款 (客户付给公司的费用)接口
						
						/**
						 * . 验证运单信息
						 * （1）验证运单是否存在
						 * （2）验证是否存在未受理的更改单
						 */
						WaybillEntityForVTS  WaybillEntityForVTS= reverseSignSettleDao.queryWaybillByNo(repaymentEntity.getWaybillNo());
						if(WaybillEntityForVTS == null){
							throw new SettlementException("不存在有效的运单，不允许反结清！");
						}
						/**
						 * .判断是否存在未受理的更改单
						 */
						WaybillFRcQueryByWaybillNoVTSDto waybillFRcQueryByWaybillNoVTSDto = new WaybillFRcQueryByWaybillNoVTSDto();
						waybillFRcQueryByWaybillNoVTSDto.setWaybillNo(repaymentWaybillNo);
						String isWaybillNo = reverseSignSettleDao.queryWaybillRfcByWaybillNo(waybillFRcQueryByWaybillNoVTSDto);
						if (StringUtils.isEmpty(isWaybillNo)) {
							throw new SettlementException("运单" + dto.getWaybillNo() + "存在未受理的更改单");
						}
						/**
						 * 关联的应付单没有支付，或已核销金额等于0 跟运单关联：
						 *  (1)代收货款应付单 
						 *  (2)装卸费应付单
						 */
						BillPayableConditionDto payableConditionDto = new BillPayableConditionDto();
						payableConditionDto.setWaybillNo(dto.getWaybillNo());
						payableConditionDto.setBillTypes(new String[] {
								SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,
								SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE });
						List<BillPayableEntity> payables = billPayableService
								.queryBillPayableByCondition(payableConditionDto);
						if (CollectionUtils.isNotEmpty(payables)) {
							for (int i = 0; i < payables.size(); i++) {
								BillPayableEntity entity = payables.get(i);
								if (entity.getVerifyAmount() != null
										&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
									throw new SettlementException("运单对应应付单已核销，不能进行反实收货款操作");
								}
								if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(entity
										.getPayStatus())) {
									throw new SettlementException("运单对应的应付单正在支付中或已支付，不能进行反实收货款操作");
								}
							}
						}
						
						/**
						 * 校验POD表中是否
						 */
						PODEntity podEntity = podService.queryNewestPOD(dto.getWaybillNo());
						if (podEntity != null
								&& podEntity.getPodType()
										.equals(SettlementDictionaryConstants.POD_ENTITY__POD_TYPE__PROOF_OF_DELIVERY)) {
							throw new SettlementException("运单已经签收，不能结清货款");
						}
						
						/**
						 *  代收货款还款单或到达运费还款单：根据传入的来源单号【到达实收单号】，查询是否存在
						 */
						List<String> sourceBillNos = new ArrayList<String>();
						sourceBillNos.add(dto.getSourceBillNo());
						List<BillRepaymentEntity> repayments = this.billRepaymentService.queryBySourceBillNOs(
								sourceBillNos, FossConstants.ACTIVE);
						if (CollectionUtils.isEmpty(repayments)) {
							throw new SettlementException("不存在还款单记录，不能进行反实收货款操作");
						}
						
						/**
						 * 运单的到达运费应收单和代收货款应收单信息 查询所有的应收单信息：根据到达实收单号，查询到还款单时，需要根据还款单信息查询对应
						 */
						BillReceivableConditionDto reDto = new BillReceivableConditionDto(dto.getWaybillNo());
						reDto.setBillTypes(new String[] {
								SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
								SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
								SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE});
						List<BillReceivableEntity> billReceivables = billReceivableService
								.queryBillReceivableByCondition(reDto);
						if (CollectionUtils.isEmpty(billReceivables)) {
							throw new SettlementException("不存在应收单信息，不能进行反实收货款操作");
						} else {
							// 验证同一个运单号是否存在相同类型的多条应收单
							this.billReceivableService.validateWaybillForBillReceivable(billReceivables);
						}
						// 代收货款还款单
						BillRepaymentEntity codBillRepayment = null;// 默认为空
						// 到达运费还款单
						BillRepaymentEntity destBillRepayment = null;// 默认为空
						for (int i = 0; i < repayments.size(); i++) {
							BillRepaymentEntity billVTSRepaymentEntity = repayments.get(i);
							// 来源单据类型：代收货款还款单时，需要判断是否存在代收货款应收单 不存在时，抛出异常
							if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__COD
									.equals(billVTSRepaymentEntity.getSourceBillType())) {
								// 验证代收货款应收单
								validateBillReceivableByReversConfirmPayment(billReceivables,
										SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
								codBillRepayment = billVTSRepaymentEntity;
							}
							// 来源单据类型：到达运费还款单时，需要判断是否存在到达运费应收单 不存在时，抛出异常
							else if (SettlementDictionaryConstants.BILL_REPAYMENT__SOURCE_BILL_TYPE__FREIGHT_COLLECT
									.equals(billVTSRepaymentEntity.getSourceBillType())) {
								for(BillReceivableEntity receEntity : billReceivables){
									if(receEntity.getBillType().equals(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE)){
										// 验证到达运费应收单信息
										validateBillReceivableByReversConfirmPayment(
												billReceivables,
												SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE);
									}
								}
								destBillRepayment = billVTSRepaymentEntity;
							}
						}
						
						// 代收货款还款单
						this.writeBackBillRepayment(codBillRepayment, currentInfo, reverseSettleDto.getUnifiedCodeFromVTS());
						// 到付运费还款单
						this.writeBackBillRepayment(destBillRepayment, currentInfo, reverseSettleDto.getUnifiedCodeFromVTS());
						// 更新结清状态为未结清 --避免出现异常同步接口操作问题。
						updateSettleStatus(repaymentEntity, FossConstants.NO);
					}else{
						/**
						 * 10.如果反结清的付款方式不为临欠、月结：当实收货款和到付运费都为0时
						 *	实际承运表结清状态更新为未结清，更新结清状态为未结清
						 */
						updateSettleStatus(repaymentEntity, FossConstants.NO);
						
					}
				}
			}
		}
		if(CollectionUtils.isEmpty(vTSRepaymentEntityList)){
			throw new SettlementException("根据付款ID查询付款信息为空!");
		}
		
		msgSettle = "Y";
		return msgSettle;
		
	}
	
	/**
	 * 根据条件查询代收货款支付审核
	 */
	@Override
	public List<VTSCodAuditEntity> queryCodAuditEntityByCondition(
			VTSCodAuditDto dto) {
		if(dto == null){
			throw new SettlementException("vts根据条件查询代收货款支付审核参数为空！");
		}
		
		return reverseSignSettleDao.selectByCondition(dto);
		
	}
	
	/**
	 * 反签收时，验证应付单
	 * 专线反签收校验---不能存在理赔/服务补救应付单,专线反签收代收货款/整车尾款/装卸费都需要验证 
	 * 偏线和空运只需要验证装卸费应付单.
	 * @author 218392-foss结算开发组-张永雪
	 * @date 2016-06-12 19:16:00
	 */
	@Override
	public List<String> checkBillPayableByReverseConfirmIncome(List<BillPayableEntity> billPayables,
			List<String> sourcesStatementNos) {
		//验证存在多条应付单
		if (CollectionUtils.isNotEmpty(billPayables)) {
			//cod代表代收货款应付单个数
			int cod = 0;
			//sic代表装卸费应付单个数
			int sic = 0;
			//整车首款应付单个数
			int firstPay = 0;
			//整车尾款应付单个数
			int lastPay = 0;
			for (BillPayableEntity entity : billPayables) {
				//应付单实体不为空时
				if (entity != null) {
					if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
							.equals(entity.getBillType())) {//代收货款应付单
						cod += 1;
						if (cod > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条代收货款应付单");
						}
					} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE
							.equals(entity.getBillType())) {//装卸费
						sic += 1;
						if (sic > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条装卸费应付单");
						}
					} else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_FIRST.
							equals(entity.getBillType())){ //整车首款
						firstPay += 1;
						if (firstPay > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条VTS整车首款应付单！");
						}
					} else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST.
							equals(entity.getBillType())){ //整车尾款
						lastPay += 1;
						if (lastPay > 1) {
							//大于1，说明同一个运单号的超过两条记录
							throw new SettlementException("同一个运单存在多条VTS整车尾款应付单！");
						}
					}
				}
			}
		}
		//应付单名称
		String billName = null;
		for (int i = 0; i < billPayables.size(); i++) {
			BillPayableEntity billPayableEntity = billPayables.get(i);

			//不能存在理赔/服务补救应付单
			if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(billPayableEntity.getBillType())) {
				throw new SettlementException("存在理赔类型应付单，不能进行反签收操作！");
			} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(billPayableEntity.getBillType())) {
				throw new SettlementException("存在服务补救应付单，不能进行反签收操作！");
			}
			// 收集对账单号码集合
			if (StringUtils.isNotEmpty(billPayableEntity.getStatementBillNo())
					&& !SettlementConstants.DEFAULT_BILL_NO.equals(billPayableEntity.getStatementBillNo())) {
				sourcesStatementNos.add(billPayableEntity.getStatementBillNo());
			}
			// 专线反签收代收货款/整车尾款/装卸费都需要验证；偏线和空运只需要验证装卸费应付单
			billName = DictUtil.rendererSubmitToDisplay(billPayableEntity.getBillType(),DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
			
			// 已核销金额大于0
			if (billPayableEntity.getVerifyAmount() != null
					&& billPayableEntity.getVerifyAmount().compareTo(BigDecimal.ZERO) > 0) {
				throw new SettlementException(billName + "应付单已核销，不能进行反签收操作！");
			}
			// 应付单正在付款中或已付款
			if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES.equals(billPayableEntity.getPayStatus())) {
				throw new SettlementException(billName + "应付单付款中或已付款，不能进行反签收操作");
			}
			// 应付单在已冻结
			if (SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__FROZEN.equals(billPayableEntity.getFrozenStatus())) {
				throw new SettlementException(billName + "应付单已冻结，不能进行反签收操作");
			}
		}
		//对账单号集合
		return sourcesStatementNos;
		
	}
	
	/**
	 * (到付运费结转临欠/月结)根据红应收单动作，作废原应收单信息 生成新红单应收单信息、生成蓝单应收单信息
	 * 
	 * @author 218392 zhangyongxue
	 * @date 2016-06-16 22:53:10
	 * @param entity
	 * @param dto
	 * @param currentInfo
	 * @see
	 */
	@Override
	public void writeBackBillReceivableByPayable(BillReceivableEntity entity,
			PaymentSettlementDto dto, CurrentInfo currentInfo) {

		// 输入参数校验
		if (entity == null || dto == null) {
			throw new SettlementException("到付运费结转临欠/月结参数不能为空！");
		}
		LOGGER.info("entering service, sourceBillNo: " + entity.getSourceBillNo());
		// 调用公共 红冲应收单方法
		this.billReceivableService.writeBackBillReceivable(entity, currentInfo);
		// 红冲应收单成功以后
		BillReceivableEntity blueEntity = new BillReceivableEntity();
		BeanUtils.copyProperties(entity, blueEntity);
		// ID
		blueEntity.setId(UUIDUtils.getUUID());
		// 生成新单据
		if (SettlementConstants.BLUE_NEW_BILL_NO) {
			SettlementNoRuleEnum rule = SettlementNoRuleEnum.getByCode(entity.getReceivableNo());
			blueEntity.setReceivableNo(settlementCommonService.getSettlementNoRule(rule));
		}
		// 设置付款方式
		blueEntity.setPaymentType(dto.getPaymentType());
		// 非反操作时，到付转临欠/月结时，设置客户编码信息
		if (!dto.isRevers()) {
			// 设置客户编码
			blueEntity.setCustomerCode(dto.getCustomerCode());
			// 设置客户名称
			blueEntity.setCustomerName(dto.getCustomerName());
		} else {
			// 反到付转临欠/月结时，设置客户编码信息为空
			// 设置客户编码为空
			blueEntity.setCustomerCode("");
			// 设置客户名称为空
			blueEntity.setCustomerName("");
			// 设置到达客户编码为空
			//blueEntity.setReceiveCustomerCode("");
			// 设置到达客户名称为空
			//blueEntity.setReceiveCustomerName("");
			// 反之后，设置付款方式为到付
			blueEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT);
			// BUG-23123：反转临欠/月结时，催款部门运单上的到达部门
			//blueEntity.setDunningOrgCode(blueEntity.getDestOrgCode());
			//blueEntity.setDunningOrgName(blueEntity.getDestOrgName());
		}
		// 日期初始化到秒
		Date now = Calendar.getInstance().getTime();

		// 设置记账日期
		blueEntity.setAccountDate(now);

		// ISSUE-2397:结转时，设置业务日期为当前日期
		blueEntity.setBusinessDate(now);

		// 是否有效
		blueEntity.setActive(FossConstants.ACTIVE);

		// 是否红单--非红单
		blueEntity.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

		// 版本号
		blueEntity.setVersionNo(FossConstants.INIT_VERSION_NO);

		// 红冲生成蓝单后设置对账单号，为默认单号
		blueEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);

		// 保存应收单信息
		this.billReceivableService.addBillReceivable(blueEntity, currentInfo);
		if (!dto.isRevers()) {
			// 挂具体客户，生成蓝单以后，调用结算内部接口，增加客户的已用信用额度信息
			customerBargainService.updateUsedAmount(
					// 此方法不报错误，表示修改成功
					blueEntity.getCustomerCode(), blueEntity.getReceivableOrgCode(),
					blueEntity.getPaymentType(), blueEntity.getUnverifyAmount(), currentInfo);
		}
	}

	/**
	 * 更新实际承运表结清状态
	 */
	@Override
	public void updateSettleStatus(VTSRepaymentEntity repayment, String state) {
		ActualFreightVTSEntity actualFreightVTSEntity = new ActualFreightVTSEntity();
		actualFreightVTSEntity.setWaybillNo(repayment.getWaybillNo());
		actualFreightVTSEntity.setSettleStatus("N");
		actualFreightVTSEntity.setSettleTime(new Date());
		//调用
		reverseSignSettleDao.updateActualFreightSettleStatusByNo(actualFreightVTSEntity);
	}
	
	/**
	 * 反实收货款时，验证还款单对应的应收单信息
	 */
	private void validateBillReceivableByReversConfirmPayment(List<BillReceivableEntity> list,
			String receivableType) {
		String receivableName = this.getBillNameByType(receivableType);

		// 根据应收单类型获取应收单实体信息
		BillReceivableEntity billReceivableEntity = this.getBillReceivableEntityByBillTypeForList(
				list, receivableType);
		if (billReceivableEntity == null) {
			throw new SettlementException("不存在" + receivableName + "应收单，不能进行反实收货款操作！");
		}

		// 应收单类型为到付运费应收单，并且运单已签收（运单签收后，应收单的确认收入日期不为空）
		if (billReceivableEntity.getConrevenDate() != null
				&& SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(billReceivableEntity.getBillType())
				&&
				// 到付运费应收单的付款方式为：临欠或月结，时抛出异常信息
				(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__DEBT// 临欠
						.equals(billReceivableEntity.getPaymentType()) || SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CREDIT// 月结
						.equals(billReceivableEntity.getPaymentType()))) {
			throw new SettlementException("运单已签收，且该运单的付款方式非到付，不能调用此接口！");
		}
	}
	
	/**
	 * 根据单据类型获取单据名称
	 */
	private String getBillNameByType(String billType) {
		// 调用综合管理DictUtil工具类接口
		return DictUtil.rendererSubmitToDisplay(billType,
				DictionaryConstants.BILL_RECEIVABLE__BILL_TYPE);
	}
	
	/**
	 * 根据单据类型从应收单集合中获取一条应收单数据
	 */
	@SuppressWarnings("unchecked")
	private BillReceivableEntity getBillReceivableEntityByBillTypeForList(
			List<BillReceivableEntity> list, String billType) {
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		Predicate predicate = new BeanPropertyValueEqualsPredicate(SettlementConstants.BILL_TYPE,
				billType);
		List<BillReceivableEntity> lists = (List<BillReceivableEntity>) CollectionUtils.select(
				list, predicate);
		if (CollectionUtils.isNotEmpty(lists)) {
			return lists.get(0);
		}
		return null;
	}
	
	/**
	 * 红冲还款单
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 上午10:53:48
	 * @param entity
	 * @param currentInfo
	 */
	private void writeBackBillRepayment(BillRepaymentEntity entity, CurrentInfo currentInfo, String unifiedCodeFromVTS) {
		if (entity != null) {
			// 红冲代收货款还款单
			this.billRepaymentService.writeBackBillRepayment(entity, currentInfo);

			// 释放财务自助汇款编号
			// 是否初始化为否，是foss生成的还款单，为“是”是原来ERP导入的不释放已占用的汇款编号
			// 2013、03、25日需求变更 ERP迁入FOSS的电汇和支票类还款单/预收单，作废时不要找财务自助释放已占用的汇款
			if (StringUtils.isNotEmpty(entity.getOaPaymentNo())
					&& FossConstants.NO.equals(entity.getIsInit())) {
				BigDecimal claimAmount = entity.getAmount();
				String claimBillNum = entity.getOaPaymentNo();
//				String claimDeptCode = currentInfo.getCurrentDeptCode();
				String debtNumber = entity.getRepaymentNo();
				
				try {
					ReleaseClaimNumRequest request = new ReleaseClaimNumRequest();
					request.setClaimAmount(claimAmount);
					request.setClaimBillNum(claimBillNum);
					// 获取组织的的实体信息
					//OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
					//		.queryOrgAdministrativeInfoByCode(claimDeptCode);
					String unifiedCode = unifiedCodeFromVTS;
					// 部门标杆编码
					request.setClaimDeptCode(unifiedCode);
					
					//增加单号  --毛建强 2012-1-26
					request.setDebtNumber(debtNumber);

					// 实例化ESBHeader，并设置到Holder对象中
					ESBHeader header = new ESBHeader();
					header.setEsbServiceCode(SettlementESBDictionaryConstants.ESB_FOSS2ESB_RELEASE_NUMBER);
					//与业务相关的字段
					header.setBusinessId(claimBillNum);
					header.setExchangePattern(SettlementESBDictionaryConstants.ESB_HEADER__EXCHANGE_PATTERN);
					header.setVersion(SettlementESBDictionaryConstants.ESB_HEADER__VERSION);
					//消息格式
					header.setMessageFormat(SettlementESBDictionaryConstants.ESB_HEADER__MESSAGE_FORMAT);
					header.setRequestId(UUIDUtils.getUUID());
				    //请求系统
					header.setSourceSystem(SettlementESBDictionaryConstants.ESB_HEADER__SOURCE_SYSTEM);
					Holder<ESBHeader> holder = new Holder<ESBHeader>(header);

					ReleaseClaimNumResponse response = claimNumService.releaseClaimNum(holder, request);
					if (!response.isResult()) {
						throw new SettlementException("释放汇款接口异常!" + response.getReason());
					}

				} catch (CommonException e) {
					throw new SettlementException("释放汇款接口异常!" + e.getMessage());
				}
			}

			/**
			 * 释放网上支付金额
			 */
			if (StringUtils.isNotEmpty(entity.getOnlinePaymentNo()) && FossConstants.NO.equals(entity.getIsInit())
					&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(entity.getPaymentType())) {
				financeOnlinePayWSProxy.release(entity.getOnlinePaymentNo(),entity.getAmount());
			}

			// 反核销（还款冲应收操作）
			this.billWriteoffService.writeBackBillWriteoffByRepayment(entity.getRepaymentNo(),
					currentInfo);
		}
	}
	
	
	
	/**
	 * 反签收查询业务处理service
	 */
	@Override
	public List<VTSResverSettleRepaymentEntity> handleReverseSign(VTSReverseSettlRequest request) {
		//1.校验登陆部门是否等于运单表中最终到达部门
		WaybillEntityForVTS  waybillEntityForVTS= reverseSignSettleDao.queryWaybillByNo(request.getWaybillNo());
		if(waybillEntityForVTS == null){
			throw new SettlementException("FOSS中不存在有效的运单！");
		}
		//2.校验当前登陆部门是否登陆最终到达部门
//		String currentOrgCode = request.getCurrentDeptCode();//当前登陆部门
//		if(StringUtils.isEmpty(currentOrgCode)){
//			throw new SettlementException("反签收查询传入的当前登陆部门编码为空！");
//		}
//		String lastOrgCode = WaybillEntityForVTS.getCustomerPickupOrgCode();//最终到达部门
//		if(!currentOrgCode.equals(lastOrgCode)){
//			throw new SettlementException("反签收查询当前登陆部门不是到达部门！");
//		}
		/**
		 *3.校验：代收货款资金部锁定
		 * @author 218392 张永雪
		 * @date 2016-06-14 23:21:30
		 * 配合代收货款支付审核 需求校验：
		 * (1)对于资金安全控制组锁定后的代收单据：
		 * 到达部门所有人员无法对此单据进行反签收申请、更改签收结果申请操作。
		 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁”
		 * (2)资金复核小组锁定后的代收单据：
		 * 到达部门所有人员无法对此单据进行反签收申请、更改签收结果申请操作。
		 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
		 */
		//获取运单号
		String waybillNo = request.getWaybillNo();
		if(StringUtils.isEmpty(waybillNo)){
			throw new SettlementException("vts反结清运单号为空！");
		}
		LOGGER.info("反结清校验开始，单号为："+waybillNo+"代收货款资金部锁定校验");
		if(StringUtils.isNotEmpty(waybillNo)){
			VTSCodAuditDto dto = new VTSCodAuditDto();
			List<String> waybillNoList = new ArrayList<String>();
			waybillNoList.add(waybillNo);
			dto.setWaybillNos(waybillNoList);
			List<VTSCodAuditEntity> codAuditEntityList = this.queryCodAuditEntityByCondition(dto);//根据单号查询
			if(codAuditEntityList != null && codAuditEntityList.size() > 0){
				VTSCodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
				if(codAuditEntity != null){
					//如果为资金部锁定
					if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
						throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金安全控制组进行解锁");
					}
					if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
						throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
					}
				}
			}else{
				LOGGER.info("单号：" + waybillNo + "没有进入代收货款支付审核！");
			}
		}
		
		/**
		 * 4.查询到达付款信息表：查询条件运单号、有效、财务单据已生成、财务单据无需生成
		 */
		// 获得付款LIST,通过运单号
		VTSResverSettleRepaymentEntity re = new VTSResverSettleRepaymentEntity();
		// 运单号
		re.setWaybillNo(waybillNo);
		// 状态
		re.setActive(FossConstants.ACTIVE);
		// 已生成
		re.setStlbillGeneratedStatus("STLBILL_GENERATED");
		//获得付款的签收数据
		List<VTSResverSettleRepaymentEntity> repaymentList = reverseSignSettleDao.queryRepaymentList(re);
		//追加无需生成的数据
		re.setStlbillGeneratedStatus("STLBILL_NOTREQUIRE");
		//没有付款的签收数据
		List<VTSResverSettleRepaymentEntity> notrequireRepaymentList = reverseSignSettleDao.queryRepaymentList(re);
		//判断是否有无需生成的数据
		if(CollectionUtils.isNotEmpty(notrequireRepaymentList)){
			//遍历没有付款的签收数据数据
			for (VTSResverSettleRepaymentEntity repaymentEntity : notrequireRepaymentList) {
				//把无需生成的结清货款信息放入返回LIST中
				repaymentList.add(repaymentEntity);
			}
		}
		
		return repaymentList;
		
	}
	
	public void setReverseSignSettleDao(IReverseSignSettleDao reverseSignSettleDao) {
		this.reverseSignSettleDao = reverseSignSettleDao;
	}
	
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	
	public void setStatementOfAccountService(
			IStatementOfAccountService statementOfAccountService) {
		this.statementOfAccountService = statementOfAccountService;
	}
	
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}
	
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}
	public void setPodService(IPODService podService) {
		this.podService = podService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setCustomerBargainService(
			ICustomerBargainService customerBargainService) {
		this.customerBargainService = customerBargainService;
	}

	public void setBillRepaymentService(IBillRepaymentService billRepaymentService) {
		this.billRepaymentService = billRepaymentService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setClaimNumService(IFinClaimNumService claimNumService) {
		this.claimNumService = claimNumService;
	}


	public void setFinanceOnlinePayWSProxy(
			IFinanceOnlinePayWSProxy financeOnlinePayWSProxy) {
		this.financeOnlinePayWSProxy = financeOnlinePayWSProxy;
	}


	public void setBillPayableEntityDao(IBillPayableEntityDao billPayableEntityDao) {
		this.billPayableEntityDao = billPayableEntityDao;
	}

}
