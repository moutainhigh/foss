/*
 * 党章，是一个政党为保证全党在政治上，思想上的一致和组织上，行动上的统一所制定的章程。
 * 一个党的党章的主要内容应该包括该党的性质、指导思想、纲领任务、组织结构、组织制度，党员的条件、权利、义务和纪律等项。
 * 通常衡量一个政党是否成熟党章也是关键因素之一。党章是政党的宗旨和行为规范。
 * 中国共产党现行党章于中国共产党第十八次全国代表大会部分修改，于2012年11月14日通过。
 * 除总纲外共十一章五十三条。规定了党的纲领、组织机构、组织制度、党员的条件、党员的义务和权利、党的纪律等项.
 */
package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerStatementOfAwardDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;
import com.deppon.foss.util.CollectionUtils;

/**
 * 合伙人奖罚对账单service
 * @author 269044
 * @date 2016-01-27 
 */
public class PartnerStatementOfAwardService implements IPartnerStatementOfAwardService{
	/**
	 * 日志 
	 */
	private static final Logger logger = LoggerFactory.getLogger(PartnerStatementOfAwardService.class);
	
	/**
	 * 合伙人奖罚对账单DAO 
	 */
	private IPartnerStatementOfAwardDao partnerStatementOfAwardDao;
	
	/**
	 * 注入组织SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 子公司银行信息
	 */
	private IPublicBankAccountService publicBankAccountService;
	
	/**
	 * 获取结算单据编号服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 查询应收应付单
	 * @author 269044
	 * @date 2016-01-27 
	 */
	@Override
	public PartnerStatementOfAwardDto queryPartnerStatementOfAwardD(
			PartnerStatementOfAwardDto dto, int start, int limit) {
		//参数dto不能为空
		if (dto == null) {
			//提示查询DTO为空
			throw new SettlementException("查询DTO为空！", "");
		}		
		//记录日志
		logger.info("制作对账单，enter service ,客户编码：" + dto.getCustomerCode());		
		//对账单明细List
		List<PartnerStatementOfAwardDEntity> partnerStatementDEntityList = new ArrayList<PartnerStatementOfAwardDEntity>();
		//奖罚对账单Dto
		PartnerStatementOfAwardDto partnerStatementOfAwardDto = new PartnerStatementOfAwardDto();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户所在部门
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//获取当前用户所在部门名称
		dto.setOrgName(currentInfo.getCurrentDeptName());
		//设置总行数
		int count = 0;
		//判断查询页签，按客户查询，否则按来源单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//判断客户编码是否为空
			if(dto.getCustomerCode() == null) {
				throw new SettlementException("客户编码为空，不能查询对账单明细！");
			}
			//根据时间客户查询应收应付单明细
			partnerStatementDEntityList = partnerStatementOfAwardDao.queryPartnerStatementOfAwardDByCustomer(dto, start, limit);
			//根据时间客户查询应收应付总行数
			count = partnerStatementOfAwardDao.countPartnerStatementOfAwardDByCustomer(dto);
		} else {
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getNumbers().size();
			//按应收应付单号查询应收应付单
			partnerStatementDEntityList = partnerStatementOfAwardDao.queryPartnerStatementOfAwardDByNumber(dto);		
		}
		//获取客户编码和名称，写入dto中
		if(CollectionUtils.isNotEmpty(partnerStatementDEntityList)){
			PartnerStatementOfAwardDEntity partner = partnerStatementDEntityList.get(0);
			if(partner.getCustomerName() != null && partner.getCustomerCode() != null) {
				//设置合伙人客户编码
				partnerStatementOfAwardDto.setCustomerCode(partner.getCustomerCode());
				//设置合伙人客户名称
				partnerStatementOfAwardDto.setCustomerName(partner.getCustomerName());
			}
			
            //设置账期开始时间和账期结束时间
	        Date periodBeginDate = null;
	        Date periodEndDate = null;
	        //循环明细数据，获取最大的账期时间和最小的账期时间
	        for (PartnerStatementOfAwardDEntity dEntity : partnerStatementDEntityList) {
	            if (null == periodBeginDate) {
	                periodBeginDate = dEntity.getBusinessDate();
	            } else if (periodBeginDate.after(dEntity.getBusinessDate())) {
	                periodBeginDate = dEntity.getBusinessDate();

	            }
	            if (null == periodEndDate) {
	                periodEndDate = dEntity.getBusinessDate();
	            } else if (periodEndDate.before(dEntity.getBusinessDate())) {
	                periodEndDate = dEntity.getBusinessDate();
	            }
	        }
	        //设置账期开始时间为最小的账期时间
	        partnerStatementOfAwardDto.setPeriodBeginDate(periodBeginDate);
	        //设置账期结束时间为最大的账期时间
	        partnerStatementOfAwardDto.setPeriodEndDate(periodEndDate);
		}
		
		//设置对账单类型--奖罚对账单		
		partnerStatementOfAwardDto.setBillType(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE__PARTNER__BONUS_PENALTY_ACCOUNT);
		// 当前登录用户操作部门
		partnerStatementOfAwardDto.setOrgCode(currentInfo.getCurrentDeptCode());
		// 当前登录用户操作部门
		partnerStatementOfAwardDto.setOrgName(currentInfo.getCurrentDeptName());
		// 获取当前登录组织的的实体信息（调用综合管理接口）
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
		// 当前登录用户操作公司
		partnerStatementOfAwardDto.setCompanyCode(orgEntity.getSubsidiaryCode());
		// 当前登录用户操作公司
		partnerStatementOfAwardDto.setCompanyName(orgEntity.getSubsidiaryName());
		// 当前登录用户所属部门标杆编码
		partnerStatementOfAwardDto.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
		
		//设置对账单明细返回列表
		partnerStatementOfAwardDto.setPartnerStatementOfAwardDList(partnerStatementDEntityList);
		//设置对账单明细返回总行数
		partnerStatementOfAwardDto.setCount(count);
		// 调用综合管理接口获取公司的对公银行账号信息
		PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
		// 部门标杆编码信息
		publicBankAccountEntity.setDeptCd(currentInfo.getDept().getUnifiedCode());
		//查询部门所属银行信息
		List<PublicBankAccountEntity> list = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
		//如果银行账号信息为空则抛出异常
		if (CollectionUtils.isEmpty(list)) {
			//提示当前操作用户所属部门没有银行账号信息
			//throw new SettlementException("当前操作用户所属部门没有银行账号信息！", "");
			partnerStatementOfAwardDto.setAccountException("当前操作用户所属部门没有银行账号信息！");
		}else if (list.size() > 1) {
			//提示当前操作用户所属部门存在多条银行账号信息
			//throw new SettlementException("当前操作用户所属部门存在多条银行账号信息！", "");
			partnerStatementOfAwardDto.setAccountException("当前操作用户所属部门存在多条银行账号信息！");
		}else{
			//获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
			PublicBankAccountEntity bankAccountEntity = list.get(0);
			// 获取支行名称
			partnerStatementOfAwardDto.setBankBranchName(bankAccountEntity.getSubbranchName()); 
			// 获取开户名
			partnerStatementOfAwardDto.setAccountUserName(bankAccountEntity.getBankAccName());
			// 获取子公司账号
			partnerStatementOfAwardDto.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
			}
		//
		countPeriodAmount(partnerStatementDEntityList,partnerStatementOfAwardDto);
		// 获取对账单的本期未还金额
//		partnerStatementOfAwardDto = updateUnpaidAmount(partnerStatementOfAwardDto);
		//返回参数
		return partnerStatementOfAwardDto;
	}
	
	/**
	 * 计算对账单剩余未还金额
	 * 
	 * @author 269044
	 * @date 2016-01-28
	 */
	public void countPeriodAmount(List<PartnerStatementOfAwardDEntity> periodList,
			PartnerStatementOfAwardDto dto) {
		if (CollectionUtils.isNotEmpty(periodList)) {
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
			// 本期发生金额=本期应收+本期预付-本期应付-本期预收
//			periodAmount = periodRecAmount.subtract(periodPayAmount);
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
	 * 计算对账单的本期未还金额
	 * 
	 * @author 269044
	 * @date 2016-01-28
	 */
	@Override
	public PartnerStatementOfAwardDto updateUnpaidAmount(PartnerStatementOfAwardDto dto) {
		// 如果本期发生金额大于0，则本期剩余未还金额等于本期剩余应收金额
		if (dto.getPeriodAmount().compareTo(BigDecimal.ZERO) > 0) {
			//设置未还款金额
			dto.setUnpaidAmount(dto.getPeriodUnverifyRecAmount());
		} else {
			// 如果本期发生金额小于等于0，则本期剩余未还金额等于本期发生金额
			dto.setUnpaidAmount(dto.getPeriodAmount());
		}
		//返回对账单
		return dto;
	}
	
	/**
	 *  生成合伙人奖罚对账单
	 *  @author 269044
	 *  @date 3016-01-29
	 */
	@Transactional
	@Override
	public PartnerStatementOfAwardDto partnerStatementOfAwardSave(PartnerStatementOfAwardDto dto) {
		//记录日志
		logger.info("生成合伙人奖罚对账单，enter service...");	
		// 获取当前登录信息
		CurrentInfo info = FossUserContext.getCurrentInfo();
		//生成对账单单号
		String statementNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZ);
		//设置对账单单号
		dto.setStatementBillNo(statementNo);
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		//获取当前用户工号
		dto.setEmpCode(currentInfo.getEmpCode());
		//获取当前用户姓名
		dto.setEmpName(currentInfo.getEmpName());
		//获取当前用户所在部门编码
		dto.setOrgCode(currentInfo.getCurrentDeptCode());
		//获取当前用户所在部门名称
		dto.setOrgName(currentInfo.getCurrentDeptName());
		// 获取当前登录组织的的实体信息（调用综合管理接口）
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
		// 当前登录用户操作公司
		dto.setCompanyCode(orgEntity.getSubsidiaryCode());
		// 当前登录用户操作公司
		dto.setCompanyName(orgEntity.getSubsidiaryName());
		//获取当前用户所在部门标杆编码
		dto.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
		// 调用综合管理接口获取公司的对公银行账号信息
		PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
		// 部门标杆编码信息
		publicBankAccountEntity.setDeptCd(info.getDept().getUnifiedCode());
		//查询部门所属银行信息
		List<PublicBankAccountEntity> bankList = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
		//如果银行账号信息为空则抛出异常
		if (CollectionUtils.isEmpty(bankList)) {
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
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//判断客户编码是否为空
			if(null == dto.getCustomerCode() || "".equals(dto.getCustomerCode())){
				throw new SettlementException("客户编码为空不可以生成对账单！");
			}
			//按时间客户生成对账单明细
			insertDRows = partnerStatementOfAwardDao.partnerStatementOfAwardDSaveByCustomer(dto);
		}else{
			//按单号生成对账单明细
			insertDRows = partnerStatementOfAwardDao.partnerStatementOfAwardDSaveByNumber(dto);
		}
		//获取所有生成的对账单明细-根据对账单号查询
		List<PartnerStatementOfAwardDEntity> list = partnerStatementOfAwardDao.querypartnerDByStatementBillNo(dto);
		//设置对账单金额
		countPeriodAmount(list,dto);
		//生成对账单
		int insertRows = partnerStatementOfAwardDao.partnerStatementOfAwardSaveByStatementBillNo(dto);
		//判断是否生成一条对账单
		if(insertRows != 1){
			throw new SettlementException("生成对账单失败，插入条数大于1，请确认应收单客户是否一致");
		}
		//更新应付单
		int updatePayRows = partnerStatementOfAwardDao.partnerPayUpdateByStatementBillNo(dto);
		//更新应收单
		int updateRecRows = partnerStatementOfAwardDao.partnerRecUpdateByStatementBillNo(dto);
		//判断更新的应收应付单据总条数是否和生成的对账单明细条数是否相等
		if((updatePayRows + updateRecRows) != insertDRows){
			throw new SettlementException("对账单明细生成条数和应付单应收单生成条数不一致，请重新查询！");
		}
		//返回参数
		return dto;
	}

	public void setPartnerStatementOfAwardDao(
			IPartnerStatementOfAwardDao partnerStatementOfAwardDao) {
		this.partnerStatementOfAwardDao = partnerStatementOfAwardDao;
	}
	/**
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}
	/**
	 * @param publicBankAccountService
	 */
	public void setPublicBankAccountService(IPublicBankAccountService publicBankAccountService) {
		this.publicBankAccountService = publicBankAccountService;
	}
	public void setSettlementCommonService(ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}
	
	/**
	 * 按对账单号查询对账单明细
	 * @author 367752
	 * @date 2016-09-08
	 */
	@Override
	public List<PartnerStatementOfAwardDEntity> querypartnerDByStatementBillNo(PartnerStatementOfAwardDto queryDto) {
		return partnerStatementOfAwardDao.querypartnerDByStatementBillNo(queryDto);
	}
	/**
	 * 更新应收单扣款状态 
	 * @author 367752
	 * @date 2016-09-08
	 */
	@Override
	public int updateDeductStatus(Map<String, Object> map) {
		return partnerStatementOfAwardDao.updateDeductStatusAuto(map);
	}
	
	/**
	 * 查询本期能够生成对账单的部门编码和合伙人编码
	 * @author 367752
	 * @date 2016-09-08
	 */
	@Override
	public List<PartnerStatementOfAwardDEntity> queryOrgCodeAndCustomerCode(
			PartnerStatementOfAwardDto dto) {
		return partnerStatementOfAwardDao.queryOrgCodeAndCustomerCode(dto);
	}

	/**
	 * 按照应收单ID更新对账单明细的已核销和未核销金额
	 * @param nos
	 */
	@Override
	public int updateAmountByReceivableNo(Map<String,Object> map) {		
		return partnerStatementOfAwardDao.updateAmountByReceivableNo(map);		
	}
	
	/**
	 * 按照应付单ID更新对账单明细的已核销和未核销金额
	 * @param nos
	 */
	@Override
	public int updateAmountByPayableNo(Map<String,Object> map) {
		return partnerStatementOfAwardDao.updateAmountByPayableNo(map);
	}
	
	/**
	 * 根据应付单号设置对账金额
	 * @param billPayableEntity
	 */
	public int updateAmountInStatementByPayableNo(BillPayableEntity billPayableEntity) {
		return partnerStatementOfAwardDao.updateAmountInStatementByPayableNo(billPayableEntity);
	}
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param nos
	 */
	@Override
	public int updateAmountByStatemnetNo(Map<String, Object> map){
		return partnerStatementOfAwardDao.updateAmountByStatemnetNo(map);
	}
	
	/**
	 * 按照对账单号更新对账单明细的已核销和未核销金额
	 * @param Map<String, Object>
	 */
	public int updateDetailAmountByStatemnetNo(Map<String, Object> bmap){
		return partnerStatementOfAwardDao.updateDetailAmountByStatemnetNo(bmap);
	}
}
