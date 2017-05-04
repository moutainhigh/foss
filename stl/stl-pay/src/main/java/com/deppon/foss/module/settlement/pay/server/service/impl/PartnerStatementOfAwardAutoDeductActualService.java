package com.deppon.foss.module.settlement.pay.server.service.impl;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.*;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.FossConfigEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PartnerStatementOfAwardDeductDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PartnerStatementOfAwardDeductResultDto;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IBillDepWriteoffBillRecService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepWriteoffBillRecDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.BillDepositReceivedDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;
import com.deppon.foss.util.define.FossConstants;
import net.sf.json.JSONArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

public class PartnerStatementOfAwardAutoDeductActualService {
	//声明日志
	private static final Logger logger = LogManager.getLogger(PartnerStatementOfAwardAutoDeductActualService.class);

	/**
	 * 合伙人奖罚扣款类型
	 */
	private final static String DEDUCT_TYPE_PARTNER_AWARD = "JFKK";

	/**
	 * 合伙人奖罚扣款接口编码
	 */
	private final static String PTP_WITHDRAW_SERVER_CODE = "/PTP_ESB2PTP_DEDUCT_BY_WAYBILL_LIST";
	
	/**
	 * 合伙人奖罚对账单Service类
	 */
	private IPartnerStatementOfAwardService partnerStatementOfAwardService;
		
	/**
	 * 注入结算单据编号生成service
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 注入应付单服务接口
	 */
	private IBillPayableService billPayableService;
	
	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivableService billReceivableService;
	
	/**
	 * 注入应收单SERVICE
	 */
	private IBillReceivablePartnerService billReceivablePartnerService;
	
	/**
	 * 注入核销单SERVICE
	 */
	private IBillWriteoffService billWriteoffService;
	
	/**
	 * 待核销预收单、应收单进行查询、核销等操作service
	 */
	private IBillDepWriteoffBillRecService billDepWriteoffBillRecService;
	
	/**
	 * 注入合伙人奖罚对账单管理SERVICE
	 */
	private IPartnerStatementOfAwardMService partnerStatementOfAwardMService;

	/**
	 * 合伙人奖罚对账单DAO
	 */
	private IPartnerStatementOfAwardDao partnerStatementOfAwardDao;
	
	/**
	 * 部门 service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 子公司银行信息
	 */
	private IPublicBankAccountService publicBankAccountService;
	
	/**
	 * esb地址配置Service
	 */
	private IFossConfigEntityService fossConfigEntityService;

	/**
	 *  自动生成合伙人奖罚对账单
	 *  @author 269044
	 *  @date 3016-09-02
	 */
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public PartnerStatementOfAwardDto partnerStatementOfAwardSaveAuto(PartnerStatementOfAwardDto dto,CurrentInfo currentInfo) {

		//生成对账单
		createStatementOfAward(dto,currentInfo);
		
		//应收金额大于等于应付金额，并且未核销金额大于0 ，才进入自动扣款。
		if(dto.getPeriodUnverifyRecAmount().compareTo(dto.getPeriodUnverifyPayAmount())>=0 && dto.getUnpaidAmount().compareTo(BigDecimal.ZERO)!=0){			
			// 调用自动扣款接口(ps：同一个合伙人和部门，当期生成一张对账单)
			partnerStatementOfAwardToDeductAuto(dto.getStatementBillNo(),currentInfo);
		}else if(dto.getPeriodUnverifyRecAmount().compareTo(dto.getPeriodUnverifyPayAmount())<0 && dto.getUnpaidAmount().compareTo(BigDecimal.ZERO)!=0){
			//当应付大于应收时，自动应收冲应付核销掉应收单，以便奖励自动返充值
			partnerStatementOfAwardToDeductRPAuto(dto.getStatementBillNo(),currentInfo);
		}
		//返回参数
		return dto;
	}

	@Transactional
	public void createStatementOfAward(PartnerStatementOfAwardDto dto,CurrentInfo currentInfo){
		//记录日志
		logger.info("生成合伙人奖罚对账单，enter service...");
		// 调用综合管理接口获取公司的对公银行账号信息
		PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
		// 部门标杆编码信息
		publicBankAccountEntity.setDeptCd(dto.getUnifiedCode());
		//查询部门所属银行信息
		List<PublicBankAccountEntity> bankList = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
		//如果银行账号信息为空则抛出异常
		if (com.deppon.foss.util.CollectionUtils.isEmpty(bankList)) {
			//提示当前操作用户所属部门没有银行账号信息
//			throw new SettlementException("当前操作用户所属部门没有银行账号信息！", "");
			//20160424需求变更：取消新增奖罚对账单对公账户校验
			// 获取支行名称
			dto.setBankBranchName("");
			// 获取开户名
			dto.setAccountUserName("");
			// 获取子公司账号
			dto.setCompanyAccountBankNo("");
		}else{
			//获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
			PublicBankAccountEntity bankAccountEntity = bankList.get(0);
			// 获取支行名称
			dto.setBankBranchName(bankAccountEntity.getSubbranchName());
			// 获取开户名
			dto.setAccountUserName(bankAccountEntity.getBankAccName());
			// 获取子公司账号
			dto.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
		}
		//插入行数
		int insertDRows = 0;
		//判断客户编码是否为空
		if (null == dto.getCustomerCode() || "".equals(dto.getCustomerCode())) {
			throw new SettlementException("","客户编码为空不可以生成对账单！");
		}
		// 按时间客户自动生成对账单明细
		insertDRows = partnerStatementOfAwardDao.partnerStatementOfAwardDSaveByCustomerAuto(dto);

		//获取所有生成的对账单明细-根据对账单号查询
		List<PartnerStatementOfAwardDEntity> list = partnerStatementOfAwardDao.querypartnerDByStatementBillNo(dto);
		//设置对账单金额
		countPeriodAmount(list,dto);
		//生成对账单
		int insertRows = partnerStatementOfAwardDao.partnerStatementOfAwardSaveByStatementBillNoAuto(dto);
		//判断是否生成一条对账单
		if(insertRows != 1){
			throw new SettlementException("","生成对账单失败，插入条数大于1，请确认应收单客户是否一致");
		}
		//更新应付单
		int updatePayRows = partnerStatementOfAwardDao.partnerPayUpdateByStatementBillNo(dto);
		//更新应收单
		int updateRecRows = partnerStatementOfAwardDao.partnerRecUpdateByStatementBillNo(dto);

		logger.info("对账单明细： "+insertDRows+"，应付单： "+updatePayRows+"，应收单："+updateRecRows);
		//判断更新的应收应付单据总条数是否和生成的对账单明细条数是否相等
		if((updatePayRows + updateRecRows) != insertDRows){
			throw new SettlementException("","对账单明细生成条数和应付单应收单生成条数不一致，请重新查询！");
		}
	}

	/**
	 * 计算对账单剩余未还金额
	 *
	 * @author 269044
	 * @date 2016-01-28
	 */
	private void countPeriodAmount(List<PartnerStatementOfAwardDEntity> periodList,
								  PartnerStatementOfAwardDto dto) {
		if (com.deppon.foss.util.CollectionUtils.isNotEmpty(periodList)) {
			// 本期发生金额
			BigDecimal periodAmount = BigDecimal.ZERO;
			// 本期应收金额
			BigDecimal periodRecAmount = BigDecimal.ZERO;
			// 本期应付金额
			BigDecimal periodPayAmount = BigDecimal.ZERO;
			// 本期剩余应收金额
			BigDecimal periodUnRecAmount = BigDecimal.ZERO;
			// 本期剩余应付金额
			BigDecimal periodUnPayAmount = BigDecimal.ZERO;
			// 未核销金额
			BigDecimal unpaidAmount = BigDecimal.ZERO;
			//循环取出本期明细信息
			for (int i = 0; i < periodList.size(); i++) {
				//获取当前对账单明细
				PartnerStatementOfAwardDEntity partnerD = periodList.get(i);
				logger.info("对账单明细信息 ： "+ partnerD.getSourceBillNo());
				// 如果为应收单
				if (partnerD.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE)) {
					//设置本期应收发生金额
					periodRecAmount = periodRecAmount.add(partnerD.getAmount());
					//设置于本期剩余应收金额
					periodUnRecAmount = periodUnRecAmount.add(partnerD.getUnverifyAmount());
					// 如果为应付单
				} else if (partnerD.getBillParentType().equals(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE)) {
					//设置本期应付发生金额
					periodPayAmount = periodPayAmount.add(partnerD.getAmount());
					//设置于本期剩余应付金额
					periodUnPayAmount = periodUnPayAmount.add(partnerD.getUnverifyAmount());
				}
			}
			//判断应收应付金额大小
			if(periodPayAmount.compareTo(periodRecAmount) > 0){
				//计算本期发生金额，应付金额减去应收金额
				periodAmount = periodPayAmount.subtract(periodRecAmount);
				//计算本期未核销金额，剩余应付金额减去剩余应收金额
				unpaidAmount = periodUnPayAmount.subtract(periodUnRecAmount);
			}else{
				//计算本期发生金额，应收金额减去应付金额
				periodAmount = periodRecAmount.subtract(periodPayAmount);
				//计算未核销金额，剩余应收金额减去剩余应付金额
				unpaidAmount = periodUnRecAmount.subtract(periodUnPayAmount);
			}
			//设置对账单类型--合伙人奖罚对账单
			dto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__PARTNER__BONUS_PENALTY_ACCOUNT);
			//设置本期应收金额
			dto.setPeriodRecAmount(periodRecAmount);
			//设置本期应付金额
			dto.setPeriodPayAmount(periodPayAmount);
			//设置本期发生金额
			dto.setPeriodAmount(periodAmount);
			//设置本期剩余应收金额
			dto.setPeriodUnverifyRecAmount(periodUnRecAmount);
			//设置本期剩余应付金额
			dto.setPeriodUnverifyPayAmount(periodUnPayAmount);
			//设置未还金额
			dto.setUnpaidAmount(unpaidAmount);
			logger.info("本期发生金额periodAmount:" + periodAmount);
			logger.info("本期未还金额unpaidAmount:" + unpaidAmount);
		} else {
			dto.setPeriodAmount(BigDecimal.ZERO);//设置金额默认为0
			dto.setPaidAmount(BigDecimal.ZERO);//设置金额默认为0
			dto.setUnpaidAmount(BigDecimal.ZERO);//设置金额默认为0
			dto.setPeriodRecAmount(BigDecimal.ZERO);//设置金额默认为0
			dto.setPeriodPayAmount(BigDecimal.ZERO);//设置金额默认为0
			dto.setPeriodUnverifyRecAmount(BigDecimal.ZERO);//设置金额默认为0
			dto.setPeriodUnverifyPayAmount(BigDecimal.ZERO);//设置金额默认为0
		}
	}
	
	/**
	 * 对账单自动扣款  
	 * @author foss结算-367752
     * @date 2016-09-02 
	 */
	@Transactional
	public void partnerStatementOfAwardToDeductAuto(String statementNo,CurrentInfo currentInfo){
		logger.info("进入合伙人奖罚对账单自动扣款service ");

		if(StringUtil.isEmpty(statementNo)){
			logger.info("对账单号为空，无法核销！");
			throw new SettlementException("","对账单号为空，无法核销！");
		}
		//把对账单状态设为已确认。
		partnerStatementOfAwardMService.updateStatusByStatementNoAuto(statementNo);
		
		//根据对账单号查询对账单,生成对账单时已经判断一个单号只生成一个对账单
		PartnerStatementOfAwardEntity pAwardEntity = partnerStatementOfAwardMService.queryPStatementsByStaNo(statementNo);
		if(null == pAwardEntity){
			logger.info("根据对账单号:"+ statementNo +",查询对账单为空！");
			throw new SettlementException("","根据对账单号查询对账单为空！");
		}else{
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(pAwardEntity.getConfirmStatus())){
				logger.info("对账单状态未确认，不能扣款！对账单号为："+ statementNo +"。");
				throw new SettlementException("","对账单状态未确认，不能扣款！");
			}
		}
		//根据对账单号查询对账单明细
		List<PartnerStatementOfAwardDEntity> pAwardDEntityList = partnerStatementOfAwardMService.queryPStatementDByStaNo(statementNo);
		if(CollectionUtils.isEmpty(pAwardDEntityList)){
			logger.info("根据对账单号查询对账单明细集合为空！！对账单号为："+ statementNo +"。");
			throw new SettlementException("","根据对账单号查询对账单明细集合为空！");
		}
		//从对账单及对账单明细获取数据封装扣款实体集合
		PartnerStatementOfAwardDeductDto dto = new PartnerStatementOfAwardDeductDto();
		dto.setSourceBillNo(pAwardEntity.getStatementBillNo());

		//定义核销dto
		BillWriteoffOperationDto writeoffResultDto;
		
		//定义应收单集合
		List<BillReceivableEntity> recList = new ArrayList<BillReceivableEntity>();
		//对账单核销操作--应收冲应付
		writeoffResultDto = writeoffStatementAuto(pAwardEntity, currentInfo);
		if(null!=writeoffResultDto.getBillReceivableEntitys()&&writeoffResultDto.getBillReceivableEntitys().size()>0 ){
			//ddw--begin
			for(BillReceivableEntity entity : writeoffResultDto.getBillReceivableEntitys()){
				recList.add(entity);
			}
		}else{
			throw new SettlementException("","应收单为空！");
		}
		try {
			//封装实体，调用PTP接口，通知合伙人扣款
			notifyPtpToDeductAuto(dto,currentInfo);
		} catch (SettlementException e) {
			logger.error("合伙人奖罚对账单自动扣款发送至PTP失败！"+e.getErrorCode(), e);
			throw new SettlementException(e.getErrorCode(),"合伙人奖罚对账单自动扣款发送至PTP失败！"+e.getErrorCode());
		}
		//通知PTP扣款成功后
		try{
			//预收冲应收(新建一个事务)
			billWriteOff(pAwardEntity,currentInfo,recList);		
			//预收冲应收，没有报错，默认成功，成功后更新对账单明细金额				
			Map<String, Object> bmap = new HashMap<String, Object>();
			bmap.put("statementNo", statementNo);
			this.partnerStatementOfAwardService.updateAmountByStatemnetNo(bmap);
		}catch(Exception e){
			logger.info("预收冲应收失败！失败信息是：" + e.getMessage());
		}
		logger.info("合伙人奖罚自动扣款end");
	}
	
	/**
	 * 对账单自动扣款  
	 * @author foss结算-367752
     * @date 2016-09-02 
	 */
	@Transactional
	public void partnerStatementOfAwardToDeductRPAuto(String statementNo,CurrentInfo currentInfo){
		logger.info("进入合伙人奖罚对账单自动应收冲应付核销service ");

		if(StringUtil.isEmpty(statementNo)){
			logger.info("对账单号为空，无法核销！");
			throw new SettlementException("","对账单号为空，无法核销！");
		}
		//把对账单状态设为已确认。
		partnerStatementOfAwardMService.updateStatusByStatementNoAuto(statementNo);
		
		//根据对账单号查询对账单,生成对账单时已经判断一个单号只生成一个对账单
		PartnerStatementOfAwardEntity pAwardEntity = partnerStatementOfAwardMService.queryPStatementsByStaNo(statementNo);
		if(null == pAwardEntity){
			logger.info("根据对账单号:"+ statementNo +",查询对账单为空！");
			throw new SettlementException("","根据对账单号查询对账单为空！");
		}else{
			if(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM.equals(pAwardEntity.getConfirmStatus())){
				logger.info("对账单状态未确认，不能扣款！对账单号为："+ statementNo +"。");
				throw new SettlementException("","对账单状态未确认，不能扣款！");
			}
		}
		//根据对账单号查询对账单明细
		List<PartnerStatementOfAwardDEntity> pAwardDEntityList = partnerStatementOfAwardMService.queryPStatementDByStaNo(statementNo);
		if(CollectionUtils.isEmpty(pAwardDEntityList)){
			logger.info("根据对账单号查询对账单明细集合为空！！对账单号为："+ statementNo +"。");
			throw new SettlementException("","根据对账单号查询对账单明细集合为空！");
		}
		//从对账单及对账单明细获取数据封装扣款实体集合
		PartnerStatementOfAwardDeductDto dto = new PartnerStatementOfAwardDeductDto();
		dto.setSourceBillNo(pAwardEntity.getStatementBillNo());

//		//定义核销dto
//		BillWriteoffOperationDto writeoffResultDto;
		
		//对账单核销操作--应收冲应付
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementAuto(pAwardEntity, currentInfo);
		if(writeoffResultDto==null)
			logger.info("没有更新成功的核销单信息");
		else {
			//在核销成功后，修改对账单应收应付明细
			this.statementDetailWriteOff(pAwardEntity);
		}
		logger.info("合伙人奖罚自动应收冲应付核销end");
	}
	
	/**
	 * 根据对账单，去核销明细
	 * @param partnerStatementOfAwardEntity
	 */
	@Transactional
	public void statementDetailWriteOff(PartnerStatementOfAwardEntity partnerStatementOfAwardEntity){
		//封装查询DTO
				PartnerStatementOfAwardDto queryDto = new PartnerStatementOfAwardDto();
				queryDto.setStatementBillNo(partnerStatementOfAwardEntity.getStatementBillNo());
				//根据对账单号查询对账单明细信息
				List<PartnerStatementOfAwardDEntity> statementDetailEntitys = partnerStatementOfAwardService.querypartnerDByStatementBillNo(queryDto);
				//如果对账单明细列表为空
				if (CollectionUtils.isEmpty(statementDetailEntitys)) {
					//提示对账单明细记录为空，无法核销
					throw new SettlementException("","对账单明细记录为空，无法核销");
				}

				// 核销操作Dto
				BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

				// 从对账单明细中获取应收单号、应付单号
				List<String> recNos = new ArrayList<String>();//应收单号
				List<String> payNos = new ArrayList<String>();//应付单号
				//循环处理对账单明细
				for (PartnerStatementOfAwardDEntity statmentDetailEntity : statementDetailEntitys) {
					// 应收单号
					if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(statmentDetailEntity.getBillParentType())) {
						//加入到应收单号列表
						recNos.add(statmentDetailEntity.getSourceBillNo());
					}
					// 应付单号
					else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(statmentDetailEntity.getBillParentType())) {
						//加入到应付单号列表
						payNos.add(statmentDetailEntity.getSourceBillNo());
					}
				}
				
				// 根据对账单明细来源单号查询应收应付
				List<BillReceivableEntity> recs = new ArrayList<BillReceivableEntity>();//应收单号
				List<BillPayableEntity> pays = new ArrayList<BillPayableEntity>();//应付单号
				// 根据来源单号查询应收单
				//如果应收单号不为空
				if (CollectionUtils.isNotEmpty(recNos)) {
					//查询应收单
					recs = billReceivablePartnerService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
					
				}
				// 根据来源单号查询应付单
				//如果应付单号不为空
				if (CollectionUtils.isNotEmpty(payNos)) {
					//查询应付单
					pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
					
				}
				//根据对账单号，来源单号，核销金额，核销明细
				if (CollectionUtils.isNotEmpty(recs)) {
					for(BillReceivableEntity receivableEntity : recs){
						Map<String, Object> bmap = new HashMap<String, Object>();
						bmap.put("statementNo", queryDto.getStatementBillNo());
						bmap.put("sourceBillNo", receivableEntity.getReceivableNo());
						bmap.put("verifyAmount", receivableEntity.getVerifyAmount());
						this.partnerStatementOfAwardService.updateDetailAmountByStatemnetNo(bmap);
					}
				}
				//根据对账单号，来源单号，核销金额，核销明细
				if (CollectionUtils.isNotEmpty(pays)) {
					for(BillPayableEntity payableEntity : pays){
						Map<String, Object> bmap = new HashMap<String, Object>();
						bmap.put("statementNo", queryDto.getStatementBillNo());
						bmap.put("sourceBillNo", payableEntity.getPayableNo());
						bmap.put("verifyAmount", payableEntity.getVerifyAmount());
						this.partnerStatementOfAwardService.updateDetailAmountByStatemnetNo(bmap);
					}
				}
	}
	
	/**
	 * 预收冲应收
	 * @param pAwardEntity
	 * @param currentInfo
	 * @param recList
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void billWriteOff(PartnerStatementOfAwardEntity pAwardEntity,CurrentInfo currentInfo,List<BillReceivableEntity> recList){
		//预收冲应收
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();
		BillDepositReceivedDto billDepositReceivedDto = new BillDepositReceivedDto();
		//设置客户编码
		billDepositReceivedDto.setCustomerCode(pAwardEntity.getCustomerCode());
		//设置部门编码
		billDepositReceivedDto.setCollectionOrgCode(pAwardEntity.getCreateOrgCode());
		//按时间与客户查询
		billDepositReceivedDto.setQueryType(SettlementConstants.TAB_QUERY_BY_DATE);
		//设置预收单最多显示条数
//		billDepositReceivedDto.setMaxShowNum(100);
		//按照客户编码和部门编码查询预收单
		BillDepWriteoffBillRecDto billDepWriteoffBillRecDto = billDepWriteoffBillRecService.queryBillDep(billDepositReceivedDto);			
		if(null==billDepWriteoffBillRecDto.getBillDepositreceivedEntityList() || billDepWriteoffBillRecDto.getBillDepositreceivedEntityList().size()==0){
			throw new SettlementException("","该合伙人预收单为空！");
		}
		
		// 核销操作Dto加入预收单List
		writeoffDto.setBillDepositReceivedEntitys(billDepWriteoffBillRecDto.getBillDepositreceivedEntityList());
		writeoffDto.setBillReceivableEntitys(recList);

		// 获取预收冲应收核销批次号
		String writeoffBatchNo = settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
		writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

		// 设置核销方式--系统生成
		writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		// 预收冲应收操作
		billWriteoffService.writeoffDepositAndReceivable(writeoffDto,currentInfo);
		// ddw--end

		// 设置对账单未核销金额
		pAwardEntity.setUnpaidAmount(BigDecimal.ZERO);
		// 设置对账单核销金额--发生金额
		pAwardEntity.setPaidAmount(pAwardEntity.getPeriodAmount());
		// 修改对账单金额
		this.partnerStatementOfAwardMService.updateStatementForWriteOff(pAwardEntity, currentInfo);
		
//		try{
//			//更新对账单明细中的已核销和未核销金额  TODO
//			Map<String,Object> bmap = new HashMap<String, Object>();
//			bmap.put("billReceivableList",writeoffDto.getBillReceivableEntitys());
//			this.partnerStatementOfAwardService.updateAmountByReceivableNo(bmap);
//		}catch(Exception e){
//			logger.error("同步更新对账单明细失败：" + e.getMessage());
//		}
	}
	
	/**
	 * 封装实体，调用PTP接口，通知合伙人扣款
	 * @author 367752
	 * @date 2016-09-06
	 */
	@SuppressWarnings("unchecked")
	private void notifyPtpToDeductAuto(PartnerStatementOfAwardDeductDto deductDto,CurrentInfo currentInfo) {
		logger.info("合伙人奖罚扣款通知PTP开始...");
		//循环扣款dto
		logger.info("合伙人奖罚扣款金额校验通过，开始扣款...");
		String url = "";
		//校验地址是否在ESB注册
		FossConfigEntity configEntity = fossConfigEntityService.queryFossConfigEntityByServerCode(PTP_WITHDRAW_SERVER_CODE);
		if (null != configEntity && !StringUtil.isEmpty(configEntity.getEsbAddr())) {
			url = configEntity.getEsbAddr();
		} else {
			logger.error("合伙人奖罚扣款-读取esb地址有误");
			throw new SettlementException("","读取esb地址有误!");
		}
		//查询调用crm接口地址---------灰度----------end---------------356354
//		url = "http://10.224.70.121:8081/ptp-syncfoss-itf/v1/ptp/saleflow/saleFlowDeductService/deductByList";
//		url = "http://192.168.67.70:8180/esb2/rs/ESB_FOSS2ESB_DEDUCT_BY_WAYBILL_LIST";
		//发送请求
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		PostMethod postMethod = new PostMethod(url);
		//请求参数
		List<PartnerStatementOfAwardDeductDto> req = new ArrayList<PartnerStatementOfAwardDeductDto>();
		//响应参数
		List<PartnerStatementOfAwardDeductResultDto> res = new ArrayList<PartnerStatementOfAwardDeductResultDto>();
		//组装请求实体
		req = buildPtpDeductReqEntityList(deductDto,currentInfo);
		//将请求list转为json数组
		String json = JSONArray.fromObject(req).toString();
		try {
			logger.info("合伙人奖罚扣款-请求参数:" + json);
			//测试回滚
//			String s= "234a";
//			Integer.parseInt(s);
			RequestEntity requestEntity = new StringRequestEntity(json, "application/json", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			postMethod.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			String responseBody = "";
			// 执行postMethod
			httpClient.executeMethod(postMethod);
			// 获取返回值
			responseBody = postMethod.getResponseBodyAsString();
			if(null==responseBody||"".equals(responseBody)){
				logger.error("合伙人奖罚扣款响应为空");
				throw new Exception("合伙人奖罚扣款响应为空");
			}
			logger.info("合伙人奖罚扣款结果！" + responseBody);
			// 将返回值转换成list
			JSONArray ja = JSONArray.fromObject(responseBody);
			Collection<PartnerStatementOfAwardDeductResultDto> coll = JSONArray.toCollection(ja,PartnerStatementOfAwardDeductResultDto.class);
			res = new ArrayList<PartnerStatementOfAwardDeductResultDto>(coll);
			//遍历返回list，校验返回值
//			for (PartnerStatementOfAwardDeductResultDto resultDto : res) {
//				//异常信息
//				if (!resultDto.getResult()) {
//					logger.error("合伙人奖罚扣款返回标识校验失败:" +resultDto.getMessage() );
//					throw new Exception("合伙人系统校验失败，"+resultDto.getMessage());
//				}
//			}
			//返回结果只有一个值
			if(!res.get(0).getResult()){
				logger.error("合伙人奖罚扣款返回标识校验失败:" +res.get(0).getMessage() );
				throw new Exception("合伙人系统校验失败，"+res.get(0).getMessage());
			}
			if(res.get(0).getResult()){				
				//更新应收单扣款状态
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("deductStatus","WHS");
				map.put("reqList",req); //TODO
				int updateNo = this.partnerStatementOfAwardService.updateDeductStatus(map);
				if(updateNo==0){
					logger.error("更新应收单扣款状态失败!");
//					throw new SettlementException("","更新应收单扣款状态失败！");
				}
			}
		}catch (Exception e) {
			throw new SettlementException("",e.getMessage());
		}  finally {
			if (null != postMethod) {
				postMethod.releaseConnection();
			}
		}
	logger.info("合伙人奖罚扣款通知PTP-end....");
}
	/**
	 * 组装请求实体List
	 * @author guoxinru--306579
	 * @date 2016-03-05
	 */
	private List<PartnerStatementOfAwardDeductDto> buildPtpDeductReqEntityList(PartnerStatementOfAwardDeductDto deductDto,CurrentInfo currentInfo) {

		List<PartnerStatementOfAwardDeductDto> dtoList = new ArrayList<PartnerStatementOfAwardDeductDto>();

		List<BillReceivableEntity> receivableList = billReceivableService.queryReceivableByStatementBillNoAuto(deductDto.getSourceBillNo());
		for(BillReceivableEntity entity : receivableList){
			PartnerStatementOfAwardDeductDto dto = new PartnerStatementOfAwardDeductDto();
			//根据合伙人部门编码，查询部门的标杆编码
			OrgAdministrativeInfoEntity origEntity = orgAdministrativeInfoService
					.querySimpleOrgAdministrativeInfoByCodeCache(entity.getCustomerCode());//合伙人部门编码
			if(null == origEntity){
				logger.error("查询合伙人部门标杆编码失败，合伙人部门信息为空！");
				throw new SettlementException("","查询合伙人部门标杆编码失败，合伙人部门信息为空！");
			}
			logger.info("合伙人部门标杆编码是："+origEntity.getUnifiedCode());
			//封装扣款金额--应收未核销
			dto.setAmount(entity.getUnverifyAmount());
			//封装流水类型--应收子类型
			dto.setFlowType(entity.getBillType());
			//封装来扣款类型--奖罚扣款
			dto.setSourceBillType(DEDUCT_TYPE_PARTNER_AWARD);
			//封装合伙人部门编码--标杆编码
			dto.setPartnerOrgCode(origEntity.getUnifiedCode());
//			dto.setPartnerOrgCode("DP10856");//测试数据
			//封装合伙人名称
			dto.setPartnerOrgName(entity.getCustomerName());
			//封装来源单号--应收单号
			dto.setSourceBillNo(entity.getSourceBillNo());
			//封装对接营业部编码 
			dto.setContractOrgCode(entity.getCreateOrgCode());
			//封装对接营业部名称 
			dto.setContractOrgName(entity.getCreateOrgName());
			//封装创建用户编码
			dto.setOperatorCode(currentInfo.getEmpCode());
			//封装创建用户名称
			dto.setOperatorName(currentInfo.getEmpName());
			//封装业务日期
			dto.setBizDate(new Date());
			dtoList.add(dto);
		}
		
		return dtoList;
	}		
	
	/**
	 * 对账单核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatementAuto(PartnerStatementOfAwardEntity partnerStatementOfAwardEntity,CurrentInfo currentInfo) {

		// 获取核销批次号
		String writeoffBatchNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);

		/*
		 * 3 获取应收单、应付单列表记录
		 */
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetailAuto(partnerStatementOfAwardEntity, writeoffBatchNo, currentInfo);
		
		//返回核销结果dto
		return writeoffResultDto;
	}
	
	/**
	 * 对账单核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatementDetailAuto(PartnerStatementOfAwardEntity partnerStatementOfAwardEntity, String writeoffBatchNo,CurrentInfo currentInfo) {

		//封装查询DTO
		PartnerStatementOfAwardDto queryDto = new PartnerStatementOfAwardDto();
		queryDto.setStatementBillNo(partnerStatementOfAwardEntity.getStatementBillNo());
		//根据对账单号查询对账单明细信息
		List<PartnerStatementOfAwardDEntity> statementDetailEntitys = partnerStatementOfAwardService.querypartnerDByStatementBillNo(queryDto);
		//如果对账单明细列表为空
		if (CollectionUtils.isEmpty(statementDetailEntitys)) {
			//提示对账单明细记录为空，无法核销
			throw new SettlementException("","对账单明细记录为空，无法核销");
		}

		// 核销操作Dto
		BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

		// 从对账单明细中获取应收单号、应付单号
		List<String> recNos = new ArrayList<String>();//应收单号
		List<String> payNos = new ArrayList<String>();//应付单号
		//循环处理对账单明细
		for (PartnerStatementOfAwardDEntity statmentDetailEntity : statementDetailEntitys) {
			// 应收单号
			if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__RECEIVABLE.equals(statmentDetailEntity.getBillParentType())) {
				//加入到应收单号列表
				recNos.add(statmentDetailEntity.getSourceBillNo());
			}
			// 应付单号
			else if (SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT_D__BILL_PARENT_TYPE__PAYABLE.equals(statmentDetailEntity.getBillParentType())) {
				//加入到应付单号列表
				payNos.add(statmentDetailEntity.getSourceBillNo());
			}
		}

		// 根据来源单号查询应收单
		//如果应收单号不为空
		if (CollectionUtils.isNotEmpty(recNos)) {
			//查询应收单
			//ddw
			List<BillReceivableEntity> recs = billReceivablePartnerService.queryByReceivableNOs(recNos, FossConstants.ACTIVE);
			// 校应收单原始单据数量和对账单明细数量是否匹配
			if (CollectionUtils.isEmpty(recs)||recs.size() != recNos.size()) {
				//提示对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员
				logger.error("对账单明细应收单数量:"+recNos.size()+"");
				logger.error("原始应收单数量:"+recs.size()+"");
				throw new SettlementException("","对账单明细记录中的应收单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应收单列表
			writeoffDto.setBillReceivableEntitys(recs);
		}
		// 根据来源单号查询应付单
		//如果应付单号不为空
		if (CollectionUtils.isNotEmpty(payNos)) {
			//查询应付单
			List<BillPayableEntity> pays = billPayableService.queryByPayableNOs(payNos, FossConstants.ACTIVE);
			// 校应付单原始单据数量和对账单明细数量是否匹配
			if ((CollectionUtils.isNotEmpty(pays) && pays.size() != payNos.size()) || CollectionUtils.isEmpty(pays)) {
				//提示对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员
				throw new SettlementException("","对账单明细记录中的应付单和原始记录信息不一致，无法核销，请联系管理员");
			}
			// 设置核销参数中的核销方列表：应付单列表
			writeoffDto.setBillPayableEntitys(pays);
		}

		// 设置核销批次号
		writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

		// 核销类型为系统生成
		writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		// 核销对账单编号取对账单上的单据编号
		writeoffDto.setStatementBillNo(partnerStatementOfAwardEntity.getStatementBillNo());

		// 核销对账单明细
		BillWriteoffOperationDto writeoffResultDto = writeoffStatementDetailByBillListAuto(writeoffDto, currentInfo);

		//返回核销结果dto
		return writeoffResultDto;
	}
	
	/**
	 * 对账单核销
	 * @author ddw
	 * @date 2014-06-17
	 */
	private BillWriteoffOperationDto writeoffStatementDetailByBillListAuto(BillWriteoffOperationDto writeoffDto, CurrentInfo currentInfo) {
		// 初始化返回结果
		BillWriteoffOperationDto writeoffResultDto = new BillWriteoffOperationDto();
		//设置应付单
		writeoffResultDto.setBillPayableEntitys(writeoffDto.getBillPayableEntitys());
		
		writeoffResultDto.setBillReceivableEntitys(writeoffDto.getBillReceivableEntitys());

		// 应收冲应付
		//如果应收单和应付单不为空
		if (CollectionUtils.isNotEmpty(writeoffDto.getBillReceivableEntitys())
				&& CollectionUtils.isNotEmpty(writeoffDto.getBillPayableEntitys())) {
			//调用统一核销方法
			writeoffResultDto = billWriteoffService.writeoffReceibableAndPayable(writeoffDto, currentInfo);
			//设置应收单核销结果
			writeoffDto.setBillReceivableEntitys(writeoffResultDto.getBillReceivableEntitys());
			//设置应付单核销结果
			writeoffDto.setBillPayableEntitys(writeoffResultDto.getBillPayableEntitys());
			
//			try{
//				//更新对账单明细中的已核销和未核销金额		TODO		
//				Map<String,Object> bmap = new HashMap<String, Object>();
//				bmap.put("billReceivableList",writeoffDto.getBillReceivableEntitys());
//				this.partnerStatementOfAwardService.updateAmountByReceivableNo(bmap);				
//				
//				Map<String,Object> pmap = new HashMap<String, Object>();
//				pmap.put("billPayableList",writeoffDto.getBillPayableEntitys());
//				this.partnerStatementOfAwardService.updateAmountByPayableNo(pmap);
//			}catch(Exception e){
//				logger.error("同步更新对账单明细失败：" + e.getMessage());
//			}
		}

//		//更新应收单扣款状态
//		Map<String,Object> map = new HashMap<String, Object>();
//		map.put("deductStatus","WHS");
//		map.put("billReceivableList",writeoffDto.getBillReceivableEntitys());
//		int updateNo = this.partnerStatementOfAwardService.updateDeductStatus(map);
//		if(updateNo==0){
//			throw new SettlementException("","更新应收单扣款状态失败！");
//		}
		//返回核销结果
		return writeoffResultDto;
	}
	
	public void setPartnerStatementOfAwardService(
			IPartnerStatementOfAwardService partnerStatementOfAwardService) {
		this.partnerStatementOfAwardService = partnerStatementOfAwardService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	public void setPartnerStatementOfAwardMService(
			IPartnerStatementOfAwardMService partnerStatementOfAwardMService) {
		this.partnerStatementOfAwardMService = partnerStatementOfAwardMService;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setBillReceivablePartnerService(
			IBillReceivablePartnerService billReceivablePartnerService) {
		this.billReceivablePartnerService = billReceivablePartnerService;
	}

	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setBillDepWriteoffBillRecService(
			IBillDepWriteoffBillRecService billDepWriteoffBillRecService) {
		this.billDepWriteoffBillRecService = billDepWriteoffBillRecService;
	}

	public void setFossConfigEntityService(
			IFossConfigEntityService fossConfigEntityService) {
		this.fossConfigEntityService = fossConfigEntityService;
	}
	public void setPartnerStatementOfAwardDao(
			IPartnerStatementOfAwardDao partnerStatementOfAwardDao) {
		this.partnerStatementOfAwardDao = partnerStatementOfAwardDao;
	}
	public void setPublicBankAccountService(
			IPublicBankAccountService publicBankAccountService) {
		this.publicBankAccountService = publicBankAccountService;
	}	

}
