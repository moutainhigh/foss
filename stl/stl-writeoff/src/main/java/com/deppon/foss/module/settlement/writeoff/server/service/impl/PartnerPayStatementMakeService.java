package com.deppon.foss.module.settlement.writeoff.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDDao;
import com.deppon.foss.module.settlement.writeoff.api.server.dao.IPartnerPayStatementDao;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementMakeService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;
/**
 * 制作合伙人付款对账单服务实现类
 * @author 黄乐为
 * @date 2016-1-29 下午2:04:09
 */
public class PartnerPayStatementMakeService implements
		IPartnerPayStatementMakeService {
	
	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PartnerPayStatementMakeService.class);
	
	/**
	 * 注入制作合伙人付款对账单DAO
	 */
	private IPartnerPayStatementDao partnerPayStatementDao;
	
	/**
	 * 注入制作合伙人付款对账单明细DAO
	 */
	private IPartnerPayStatementDDao partnerPayStatementDDao;
	
	/**
	 * 子公司银行信息
	 */
	private IPublicBankAccountService publicBankAccountService;

	/**
	 * 注入组织SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
	 * 获取结算单据编号服务
	 */
	private ISettlementCommonService settlementCommonService;

	/**
	 * 合伙人付款对账单新增查询应付单
	 * @author 黄乐为
	 * @date 2016-1-29 下午5:36:16
	 * @param dto 查询dto
	 * @param start
	 * @param limit
	 * @return PartnerPayStatementDto
	 */
	@Override
	public PartnerPayStatementDto queryPartnerPayStatementD(PartnerPayStatementDto dto,int start,int limit) {
		//参数dto不能为空
		if (dto == null) {
			//提示查询DTO为空
			throw new SettlementException("查询DTO为空！", "");
		}
		//记录日志
		logger.info("制作合伙人付款对账单，enter service ");
		//对账单明细列表
		List<PartnerPayStatementDEntity> partnerPayStatementDList = new ArrayList<PartnerPayStatementDEntity>();
		//获取当前用户
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		/*
		 * modify by 269044-zhurongrong 2016-11-23
		 * DP-SPBP-合伙人收银员可做到达提成与D-H委托派费付款的需求:合伙人收银员可操作合伙人付款对账单新增生成对账单、合伙人付款对账单管理进行付款功能
		 */
		//客户编码即为当前登录部门
		dto.setCustomerCode(currentInfo.getCurrentDeptCode());
		//将orgCode重置为null
		dto.setOrgCode(null);
		//设置合伙人付款对账单实体
		dto.setPartnerPayStatementEntity(queryPPSEntity(dto,currentInfo));
		//设置总行数
		int count = 0;
		//判断查询页签，按合伙人查询，否则按单号查询
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			// 将结束日期减1天（由于前台将日期增加了一天，所以保存到数据库时必须减去一天）
			//dto.getPartnerPayStatementEntity().setPeriodEndDate(DateUtils.addDays(dto.getPeriodEndDate(), -1));
			//根据时间和客户查询应付单据
			partnerPayStatementDList = partnerPayStatementDDao.queryStatementDByCustomer(dto,start,limit);
			//根据客户查询条件，查询对账单明细总数和总金额
			PartnerPayStatementDto stateDto = partnerPayStatementDDao.countStatementAmountByCustomer(dto);
			if(null != stateDto){
				//总数据量
				count = new Long(stateDto.getTotalCount()).intValue();
				//设置总金额
				dto.getPartnerPayStatementEntity().setPeriodUnverifyPayAmount(stateDto.getTotalAmount());
				//设置对账单的账单开始日期和结束日期
				dto.getPartnerPayStatementEntity().setPeriodBeginDate(stateDto.getPeriodBeginDate());
				dto.getPartnerPayStatementEntity().setPeriodEndDate(stateDto.getPeriodEndDate());
			}
		}else{
			//按单号查询不需要分页，设置总行数为单号个数
			count = dto.getBillDetailNos().length;
			// 调用单号公共处理方法，返回map集合
			Map<String, List<String>> resultMap = SettlementUtil.convertBillNos(dto.getBillDetailNos());
			
			// 判断map中是否有应付单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YF))) {
				//设置应付单号
				dto.setPayableBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_YF));
				//按应付单号查询应付单
				partnerPayStatementDList.addAll(partnerPayStatementDDao.queryStatementDByPayableNos(dto));
			}
			// 判断map中是否有运单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_LY))) {
				//设置来源单号
				dto.setSourceBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_LY));
				//按来源单号查询应付单
				partnerPayStatementDList.addAll(partnerPayStatementDDao.queryStatementDByWaybillNos(dto));
			}
		
			// 获取账期起始时间和结束时间,起始时间取对账单明细中最早的签收日期,结束时间取对账单明细中最晚的签收日期
			// 计算本期剩余未还金额
			if (CollectionUtils.isNotEmpty(partnerPayStatementDList)) {
				Date periodBeginDate = null;
				Date periodEndDate = null;
				//本期剩余应付金额
				BigDecimal sumAmount = new BigDecimal(0);
				
				for (PartnerPayStatementDEntity dEntity : partnerPayStatementDList) {
					if (null == periodBeginDate) {
						periodBeginDate = dEntity.getSignDate();
					//取最小业务日期
					} else if (periodBeginDate.after(dEntity.getBusinessDate())) {
						periodBeginDate = dEntity.getBusinessDate();
					}
					if (null == periodEndDate) {
						periodEndDate = dEntity.getSignDate();
					//取最大业务日期
					} else if (periodEndDate.before(dEntity.getBusinessDate())) {
						periodEndDate = dEntity.getBusinessDate();
					}
					//本期剩余未还金额
					sumAmount = sumAmount.add(dEntity.getUnverifyAmount());
				}
				//设置dto的账单开始日期和结束日期
				//dto.setPeriodBeginDate(periodBeginDate);
				//查询日期加1
				//dto.setPeriodEndDate(DateUtils.addDays(periodEndDate, 1));
				//设置对账单的账单开始日期和结束日期
				dto.getPartnerPayStatementEntity().setPeriodBeginDate(periodBeginDate);
				dto.getPartnerPayStatementEntity().setPeriodEndDate(periodEndDate);
				//设置对账单的本期剩余未还金额
				dto.getPartnerPayStatementEntity().setPeriodUnverifyPayAmount(sumAmount);
			}
		}
		
		if(CollectionUtils.isNotEmpty(partnerPayStatementDList)){
			//设置合伙人编码，同一单号的合伙人信息应该相同，故只取第一条
			dto.getPartnerPayStatementEntity().setCustomerCode(
					partnerPayStatementDList.get(0).getCustomerCode());
			//设置合伙人名称
			dto.getPartnerPayStatementEntity().setCustomerName(
					partnerPayStatementDList.get(0).getCustomerName());
		}
		
		//设置对账单明细返回列表
		dto.setPartnerPayStatementDList(partnerPayStatementDList);
		//设置对账单明细返回总行数
		dto.setCount(count);
		//返回参数
		return dto;
	}
	
	/**
	 * 根据前台传递的参数和当前用户信息准备对账单信息
	 * @author 黄乐为
	 * @date 2016-2-25 上午10:12:50
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	public PartnerPayStatementEntity queryPPSEntity(PartnerPayStatementDto dto,CurrentInfo currentInfo){
		//付款对账单实体
		PartnerPayStatementEntity ppsEntity = new PartnerPayStatementEntity();
		//对账单类型
		ppsEntity.setBillType(dto.getBillType());
		//合伙人编码
		ppsEntity.setCustomerCode(dto.getCustomerCode());
		//合伙人名称
		ppsEntity.setCustomerName(dto.getCustomerName());
		// 当前登录用户操作部门
		ppsEntity.setCreateOrgCode(currentInfo.getCurrentDeptCode());
		// 当前登录用户操作部门
		ppsEntity.setCreateOrgName(currentInfo.getCurrentDeptName());
		// 获取当前登录组织的的实体信息（调用综合管理接口）
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(currentInfo.getCurrentDeptCode());
		// 当前登录用户操作公司
		ppsEntity.setCompanyCode(orgEntity.getSubsidiaryCode());
		// 当前登录用户操作公司
		ppsEntity.setCompanyName(orgEntity.getSubsidiaryName());
		// 当前登录用户所属部门标杆编码
		ppsEntity.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
		//设置参数：结账次数
		ppsEntity.setSettleNum((short) 0);
		// 调用综合管理接口获取公司的对公银行账号信息
		PublicBankAccountEntity publicBankAccountEntity = new PublicBankAccountEntity();
		// 部门标杆编码信息
		publicBankAccountEntity.setDeptCd(currentInfo.getDept().getUnifiedCode());
		//查询部门所属银行信息
		List<PublicBankAccountEntity> list = publicBankAccountService.queryPublicBankAccountExactByEntity(publicBankAccountEntity,0, Integer.MAX_VALUE);
		//如果银行账号信息为空则抛出异常
		if (CollectionUtils.isEmpty(list)) {
			// 获取支行名称
			ppsEntity.setBankBranchName(""); 
			// 获取开户名
			ppsEntity.setAccountUserName("");
			// 获取子公司账号
			ppsEntity.setCompanyAccountBankNo("");
			//提示当前操作用户所属部门没有银行账号信息
//			throw new SettlementException("当前操作用户所属部门没有银行账号信息！");
		}else if (list.size() > 1) {
			//提示当前操作用户所属部门存在多条银行账号信息
			throw new SettlementException("当前操作用户所属部门存在多条银行账号信息！");
		}else{
			//获取第一条银行账号信息（业务上只允许一条，但综合提供的方法返回参数是集合）
			PublicBankAccountEntity bankAccountEntity = list.get(0);
			// 获取支行名称
			ppsEntity.setBankBranchName(bankAccountEntity.getSubbranchName()); 
			// 获取开户名
			ppsEntity.setAccountUserName(bankAccountEntity.getBankAccName());
			// 获取子公司账号
			ppsEntity.setCompanyAccountBankNo(bankAccountEntity.getBankAcc());
		}
		//返回参数
		return ppsEntity;
	}

	/**
	 * 制作合伙人付款对账单时，保存合伙人付款对账单及合伙人付款对账单明细单据
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:09:25
	 * @param dto 制作合伙人付款对账单参数Dto
	 * @param info 当前登录用户
	 * @return PartnerPayStatementMakeQueryResultDto 
	 * 			合伙人付款对账单及合伙人付款对账单明细Dto
	 */
	@Override
	public PartnerPayStatementDto addStatement(
			PartnerPayStatementDto dto,
			CurrentInfo info) {
		return null;
	}
	
	/**
	 * 制作合伙人付款对账单时，保存合伙人付款对账单及合伙人付款对账单明细单据
	 * @author 黄乐为
	 * @date 2016-1-29 下午2:09:25
	 * @param dto 制作合伙人付款对账单参数Dto
	 * @param info 当前登录用户
	 * @return PartnerPayStatementMakeQueryResultDto 
	 * 			合伙人付款对账单及合伙人付款对账单明细Dto
	 */
	@Override
	@Transactional
	public PartnerPayStatementDto addStatement(PartnerPayStatementDto dto) {
		if(dto.getPartnerPayStatementEntity() == null){
			//提示制作合伙人付款对账单时,传入的对账单实体为空
			throw new SettlementException("制作合伙人付款对账单时,传入的对账单实体为空！", "");
		}
		if(dto.getPartnerPayStatementDList() == null){
			//提示制作合伙人付款对账单时,传入的对账单明细为空
			throw new SettlementException("制作合伙人付款对账单时,传入的对账单明细为空！", "");
		}
		//生成对账单单号
		String statementNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZ);
		//将对账单号传入dto
		dto.setStatementBillNo(statementNo);
		//获取传入的对账单实体
		PartnerPayStatementEntity entity = dto.getPartnerPayStatementEntity();
		//设置对账单单号
		entity.setStatementBillNo(statementNo);
		//设置对账单状态为未确认
		entity.setStatementStatus(SettlementDictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS__NOT_CONFIRM);
		//设置默认对账单金额为0
		entity.setPeriodAmount(BigDecimal.ZERO);
		entity.setPeriodNpayAmount(BigDecimal.ZERO);
		entity.setPeriodPayAmount(BigDecimal.ZERO);
		entity.setPeriodRrpayAmount(BigDecimal.ZERO);
		entity.setPeriodUnverifyPayAmount(BigDecimal.ZERO);
		//设置对账单创建时间，即运单开单时间
		entity.setBillBeginTime(Calendar.getInstance().getTime());

		//生成对账单明细
		if(SettlementConstants.TAB_QUERY_BY_DATE.equals(dto.getQueryTabType())){
			//判断客户编码是否为空
			if(StringUtils.isBlank(dto.getCustomerCode())){
				throw new SettlementException("客户编码为空不可以生成对账单！");
			}
			//将前台页面传过来的orgCode重置为null
			dto.setOrgCode(null);
			int cRows = partnerPayStatementDDao.saveDetailByCustomer(dto);
			//判断插入行数
			if(cRows == 0){
				throw new SettlementException("对账单明细插入失败，新增条数为零！");
			}
			//限制对账单明细数量不能超过3000
			if(cRows > SettlementReportNumber.THREE_THOUSAND){
				throw new SettlementException("制作对账单失败：对账单明细不能超过"
						+ SettlementReportNumber.THREE_THOUSAND
						+ ",请调整账期日期重新制作对账单");
			}
		}else {
			// 调用单号公共处理方法，返回map集合
			Map<String, List<String>> resultMap = SettlementUtil.convertBillNos(dto.getBillDetailNos());			
			// 判断map中是否有应付单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_YF))) {
				//设置应付单号
				dto.setPayableBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_YF));
				//按应付单号新增对账单明细
				int pRows = partnerPayStatementDDao.saveDetailByPayableNos(dto);
				//判断插入行数
				if(pRows == 0){
					throw new SettlementException("对账单明细插入失败，新增条数为零！");
				}
			}
			// 判断map中是否有运单号集合
			if (CollectionUtils.isNotEmpty(resultMap.get(SettlementConstants.BILL_PREFIX_LY))) {
				//设置来源单号
				dto.setSourceBillNosList(resultMap.get(SettlementConstants.BILL_PREFIX_LY));
				//按运单号新增对账单明细
				int wRows = partnerPayStatementDDao.saveDetailByWaybillNos(dto);
				//判断插入行数
				if(wRows == 0){
					throw new SettlementException("对账单明细插入失败，新增条数为零！");
				}
			}
			//根据明细获取合伙人信息，同一单号的合伙人信息应该相同，故只取第一条
			//设置对账单的合伙人编码
			entity.setCustomerCode(dto.getPartnerPayStatementDList().get(0).getCustomerCode());
			//设置对账单的合伙人名称
			entity.setCustomerName(dto.getPartnerPayStatementDList().get(0).getCustomerName());
		}
		
		//生成对账单,返回插入行数
		int insertRows = partnerPayStatementDao.SaveStatement(entity);
		//判断插入行数
		if(insertRows == 0){
			throw new SettlementException("对账单明细插入失败，新增条数为零！");
		}
		//判断是否生成一条对账单
		if(insertRows != 1){
			throw new SettlementException("生成对账单失败，插入条数大于1，请确认应付单客户是否一致");
		}
		//更新应付单
		int updatePayRows = partnerPayStatementDDao.updatePayableByStatementBillNo(dto);
		//判断更新行数
		if(updatePayRows == 0){
			throw new SettlementException("对账单明细插入失败，应付单据更新条数同时为零！");
		}
		//返回参数
		return dto;
	}


	public IPartnerPayStatementDao getPartnerPayStatementDao() {
		return partnerPayStatementDao;
	}

	public void setPartnerPayStatementDao(
			IPartnerPayStatementDao partnerPayStatementDao) {
		this.partnerPayStatementDao = partnerPayStatementDao;
	}

	public IPartnerPayStatementDDao getPartnerPayStatementDDao() {
		return partnerPayStatementDDao;
	}

	public void setPartnerPayStatementDDao(
			IPartnerPayStatementDDao partnerPayStatementDDao) {
		this.partnerPayStatementDDao = partnerPayStatementDDao;
	}

	public IPublicBankAccountService getPublicBankAccountService() {
		return publicBankAccountService;
	}

	public void setPublicBankAccountService(
			IPublicBankAccountService publicBankAccountService) {
		this.publicBankAccountService = publicBankAccountService;
	}

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public ISettlementCommonService getSettlementCommonService() {
		return settlementCommonService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}
	
}
