package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPartnerStatementOfAwardAutoDeductService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerStatementOfAwardService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerStatementOfAwardDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerStatementOfAwardDto;

public class PartnerStatementOfAwardAutoDeductService implements
		IPartnerStatementOfAwardAutoDeductService {

	//声明日志
	private static final Logger logger = LogManager.getLogger(PartnerStatementOfAwardAutoDeductService.class);
	
	/**
	 * 合伙人奖罚对账单Service类
	 */
	private IPartnerStatementOfAwardService partnerStatementOfAwardService;
		
	/**
	 * 注入结算单据编号生成service
	 */
	private ISettlementCommonService settlementCommonService;

	
	/**
	 * 部门 service接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * 合伙人奖罚自动扣款事务类
	 */
	private PartnerStatementOfAwardAutoDeductActualService partnerStatementOfAwardAutoDeductActualService;

	private static String DEADLINE="2016-12-03 23:59:59";
	
	private static String STARTDATE="2016-12-01 00:00:00";	
		
	/**
	 * 合伙人奖罚对账单自动扣款
	 * @author 367752 
	 * @date 2016-09-01 
	 */
	@Override
	public void autoDeductPartnerStatementOfAward() {
		//根据系统时间，查询本期（前三天00:00~23:59期间）能够生成对账单的信息的部门编码和合伙人编码
		Calendar calendar = Calendar.getInstance();
		Date nowTime = calendar.getTime();
		Date deadline = null;
		Date  deadlineStart= null;
		try {
			deadline = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(DEADLINE);
			deadlineStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(STARTDATE);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Date startDate = null;
		Date endDate = null;
		//判空
		if(deadline == null || deadlineStart == null){
			throw new SettlementException("时间转换错误！");
		}
		//在截止日期之前
		if(deadlineStart.getTime() < nowTime.getTime() && nowTime.getTime() < deadline.getTime()){
			// 查找之前合伙人上线开始的数据
		try {
		    //开始时间设置为合伙人项目开始的时间
		    startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-04-01 00:00:00");
		    endDate = deadline;
	        } catch (ParseException e1) {
		        e1.printStackTrace();
	        }
		}else{
			startDate = getTimeAuto(calendar,"start");
			endDate = getTimeAuto(calendar,"end");
		}
		
		PartnerStatementOfAwardDto dto = new PartnerStatementOfAwardDto();
		dto.setPeriodBeginDate(startDate);
		dto.setPeriodEndDate(endDate);
		//默认工号和名称
		dto.setEmpCode(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE);
		dto.setEmpName(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPNAME);

		//调用Dao类，查询能够生成对账单的信息的部门编码和合伙人编码
		List<PartnerStatementOfAwardDEntity> list = partnerStatementOfAwardService.queryOrgCodeAndCustomerCode(dto);

		if(list != null && list.size() > 0){
			for (PartnerStatementOfAwardDEntity entity : list){
				logger.info("本次的部门编码："+entity.getOrgCode()+",客户部门编码："+entity.getCustomerCode());
				//生成系统默认的CurrentInfo
				CurrentInfo currentInfo= createSysInfo(entity.getOrgCode());
				//生成对账单单号
				String statementNo = settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.DZ);
				//设置对账单单号
				dto.setStatementBillNo(statementNo);
				dto.setOrgCode(entity.getOrgCode());
				dto.setOrgName(currentInfo.getCurrentDeptName());				
				dto.setCustomerCode(entity.getCustomerCode());
				// 用户操作公司
				dto.setCompanyCode(currentInfo.getDept().getSubsidiaryCode());
				// 用户操作公司
				dto.setCompanyName(currentInfo.getDept().getSubsidiaryName());
				//部门标杆编码
				dto.setUnifiedCode(currentInfo.getDept().getUnifiedCode());
				try{
					//生成合伙人奖罚对账单。
					partnerStatementOfAwardAutoDeductActualService.partnerStatementOfAwardSaveAuto(dto,currentInfo);
				}catch(Exception e){
					logger.info("本次对账单操作失败，statementNo：" + statementNo + "，失败信息是：" + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * @param calendar 时间
	 * @param type 类型
	 * @return
	 */
	private Date getTimeAuto(Calendar calendar,String type){		
		if("start".equals(type)){
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			calendar.set(Calendar.MILLISECOND,0);
			calendar.add(Calendar.DAY_OF_MONTH,-3);
			return calendar.getTime();			
		}else{
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			calendar.set(Calendar.MILLISECOND,999);
			calendar.add(Calendar.DAY_OF_MONTH,2);
			return calendar.getTime();
		}			
	}
	
	/**
	 * 创建系统当前环境
	 * @return
	 */
	private CurrentInfo createSysInfo(String deptCode){
		EmployeeEntity employeeEntity = new EmployeeEntity();
		employeeEntity.setEmpCode(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPCODE);
		employeeEntity.setEmpName(SettlementDictionaryConstants.PARTNER_STATEMENT_OF_AWARD_EMPNAME);
		
		UserEntity userEntity = new UserEntity();
		userEntity.setEmployee(employeeEntity);

		//调用组织管理的service
		OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode);
		
		return new CurrentInfo(userEntity,orgAdministrativeInfoEntity);
	}

	public void setPartnerStatementOfAwardService(
			IPartnerStatementOfAwardService partnerStatementOfAwardService) {
		this.partnerStatementOfAwardService = partnerStatementOfAwardService;
	}

	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}
	
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setPartnerStatementOfAwardAutoDeductActualService(PartnerStatementOfAwardAutoDeductActualService partnerStatementOfAwardAutoDeductActualService) {
		this.partnerStatementOfAwardAutoDeductActualService = partnerStatementOfAwardAutoDeductActualService;
	}
}
