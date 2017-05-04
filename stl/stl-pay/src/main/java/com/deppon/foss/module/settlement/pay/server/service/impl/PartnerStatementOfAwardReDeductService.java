package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardReDeductService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardMService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

public class PartnerStatementOfAwardReDeductService implements
		IPartnerStatementOfAwardReDeductService {

	//声明日志
	private static final Logger logger = LogManager.getLogger(PartnerStatementOfAwardReDeductService.class);
	
	/**
	 * 合伙人奖罚对账单Service类
	 */
	private IPartnerStatementOfAwardMService partnerStatementOfAwardMService;

	/**
	 * 合伙人奖罚自动扣款事务类
	 */
	private PartnerStatementOfAwardAutoDeductActualService partnerStatementOfAwardAutoDeductActualService;

		
	/**
	 * 合伙人奖罚对账单自动扣款
	 * @author 367752 
	 * @date 2016-10-14 
	 */
	@Override
	public void reDeductPartnerStatementOfAward() {
		PartnerStatementOfAwardDto dto = new PartnerStatementOfAwardDto();

		//默认工号和名称
		dto.setEmpCode(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE);
		dto.setEmpName(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPNAME);

		//查询需要重推的合伙人奖罚对账单列表
		List<PartnerStatementOfAwardEntity> list = partnerStatementOfAwardMService.querypartnerDNeedReDecduct(dto);

		if(list != null && list.size() > 0){
			for (PartnerStatementOfAwardEntity entity : list){
				logger.info("本次重推的部门对账单号为 ："+entity.getStatementBillNo());
				//生成系统默认的CurrentInfo
				CurrentInfo currentInfo= createSysInfo(entity.getCreateOrgCode(),entity.getCreateOrgName());
				
				//应收金额大于等于应付金额，并且未核销金额大于0 ，才进入自动扣款。
				if(entity.getPeriodUnverifyRecAmount().compareTo(entity.getPeriodUnverifyPayAmount())>=0 && entity.getUnpaidAmount().compareTo(BigDecimal.ZERO)!=0){			
					try{
						// 调用自动扣款接口
						partnerStatementOfAwardAutoDeductActualService.partnerStatementOfAwardToDeductAuto(entity.getStatementBillNo(),currentInfo);
					}catch(BusinessException e){
						logger.error("本次对账单重推失败，statementNo：" + entity.getStatementBillNo() + "，失败信息是：" + e.getMessage());
					}catch(Exception e){
						logger.error("本次对账单重推失败，statementNo：" + entity.getStatementBillNo() + "，失败信息是：" + e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 创建系统当前环境
	 * @return
	 */
	private CurrentInfo createSysInfo(String deptCode,String deptName){
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE);
		employeeEntity.setEmpName(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPNAME);
		
		UserEntity userEntity = new UserEntity();
		userEntity.setEmployee(employeeEntity);

		//调用组织管理的service
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = new OrgAdministrativeInfoEntity();
		orgAdministrativeInfoEntity.setCode(deptCode);
		orgAdministrativeInfoEntity.setName(deptName);
		
		return new CurrentInfo(userEntity,orgAdministrativeInfoEntity);
	}

	public void setPartnerStatementOfAwardMService(
			IPartnerStatementOfAwardMService partnerStatementOfAwardMService) {
		this.partnerStatementOfAwardMService = partnerStatementOfAwardMService;
	}

	public void setPartnerStatementOfAwardAutoDeductActualService(PartnerStatementOfAwardAutoDeductActualService partnerStatementOfAwardAutoDeductActualService) {
		this.partnerStatementOfAwardAutoDeductActualService = partnerStatementOfAwardAutoDeductActualService;
	}
}
