package com.deppon.foss.module.settlement.pay.server.service.impl;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import com.deppon.esb.inteface.domain.payment.ExpenseDetail;
import com.deppon.foss.framework.server.components.export.excel.ExcelExport;
import com.deppon.foss.framework.server.components.export.excel.SheetData;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillAdvancedPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IOperatingLogService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementESBDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillAdvancedPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.OperatingLogEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.server.dao.IBillAdvanceApplysManageDao;
import com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToCostcontrolSendService;
import com.deppon.foss.module.settlement.pay.api.server.service.IPayToFSSCSendService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceResultDto;
import com.deppon.foss.module.settlement.pay.api.shared.dto.PayToCostcontrolDto;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 预付单服务
 * 
 * @author 095793-foss-LiQin
 * @date 2012-10-16 下午2:13:56
 */
public class BillAdvanceApplysManageService implements
		IBillAdvanceApplysManageService {
	/**
	 * Logger 预付单服务
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillAdvanceApplysManageService.class);
	/**
	 * 返回费控常量
	 */
	private static final String APPLYDESC = "空运预付款申请";
	/**
	 * 注入组织SERVICE
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	// 预付单dao
	private IBillAdvanceApplysManageDao billAdvanceApplysManageDao;
	// 调用预付款申请，公共方法
	private IBillAdvancedPaymentService billAdvancedPaymentService;
	// 结算通用自动产生预付单编号
	private ISettlementCommonService settlementCommonService;
	// 操作审核日志service
	private IOperatingLogService operatingLogService;
	
	/**
	 * 注入对费控接口
	 */
	private IPayToCostcontrolSendService payToCostcontrolSendService;
	
	/**
	 * 注入财务共享接口
	 */
	private IPayToFSSCSendService payToFSSCSendService;
	
	

	/**
	 * 查询预付单
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-19 下午7:57:53
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService#queryBillAdvancePayApply(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto,
	 *      int, int)
	 */
	@Override
	public BillAdvanceResultDto queryBillAdvancePayApply(
			BillAdvanceDto billAdvanDto, int start, int limit,CurrentInfo currentInfo) {

		// 校验查询参数
		checkParm(billAdvanDto);
		// 设置查询条件
		setParm(billAdvanDto,currentInfo);
		// 分页符合条件的预付单
		List<BillAdvancedPaymentEntity> billAdvanceQListByPage = null;
		// 返回action dto
		BillAdvanceResultDto billAaDto;

		// 执行分页查询
		billAaDto = billAdvanceApplysManageDao
				.queryCountBillAdvancePayApplys(billAdvanDto);
		// 获取总条数
		if (billAaDto.getCountNum() > 0) {
			// 页面查询到列表
			billAdvanceQListByPage = billAdvanceApplysManageDao
					.queryBillAdvancePayApplys(billAdvanDto, start, limit);
			// 将分页的结果集，设置到返回对象中
			billAaDto.setQueryBillAdvancedPayList(billAdvanceQListByPage);
		}
		return billAaDto;
	}

	/**
	 * 导出预付单查询
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-19 下午7:57:53
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService#queryExportBillAdvancePayApply(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto
	 *      )
	 */
	public BillAdvanceResultDto queryExportBillAdvancePayApply(
			BillAdvanceDto billAdvanDto,CurrentInfo currentInfo) {

		// 校验查询参数
		checkParm(billAdvanDto);
		// 设置查询条件
		setParm(billAdvanDto,currentInfo);
		// 分页符合条件的预付单
		List<BillAdvancedPaymentEntity> billAdvanceQList = null;
		// 返回action dto
		BillAdvanceResultDto billAaDto = new BillAdvanceResultDto();
		// 页面查询到列表
		billAdvanceQList = billAdvanceApplysManageDao
				.queryExportBillAdvancePayApplys(billAdvanDto);
		// 将分页的结果集，设置到返回对象中
		billAaDto.setQueryBillAdvancedPayList(billAdvanceQList);

		return billAaDto;
	}

	/**
	 * 检查和设置查询预付款查询参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 上午9:26:48
	 */
	private void checkParm(BillAdvanceDto billADto) {

		// 校验结束日期和开始日期相差不超过90天
		if (DateUtils.getTimeDiff(billADto.getBusinessStartDate(),
				billADto.getBusinessEndDate()) > SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH) {
			LOGGER.error("申请日期和结束日期不能超过"
					+ SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH + "天");
			throw new SettlementException("申请日期和结束日期不能超过"
					+ SettlementConstants.DATE_LIMIT_DAYS_MAX_MONTH + "天", "");
		}

		// 如果页面条数为空或者小于0，设置每页条数
		if (null == billADto.getPageSize() || billADto.getPageSize() < 0) {
			billADto.setPageSize(0);
		}
	}

	/**
	 * 设置查询传入参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午6:18:52
	 */
	private BillAdvanceDto setParm(BillAdvanceDto billADto,CurrentInfo currentInfo) {
		billADto.setEmpCode(currentInfo.getEmpCode());
		// 结束日期+1
		Date endDate = DateUtils.addDayToDate(billADto.getBusinessEndDate(), 1);
		billADto.setBusinessEndDate(endDate);
		return billADto;
	}

	/**
	 * 新增预付款申请
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-20 下午3:39:15
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService#addAdvancePayApply(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	@Transactional
	public void addAdvancePayApply(BillAdvanceDto billAdDto, CurrentInfo cInfo) {
		
		//付款对接系统  Y--财务共享，N--代表费控 
		String payToSystem = BillPaymentPayService.getPayToSystem();
		// 获取当前登录组织的的实体信息（调用综合管理接口）
	    OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService.
	    		queryOrgAdministrativeInfoByCode(cInfo.getCurrentDeptCode());
	    
		if(FossConstants.YES.equals(orgEntity.getAirDispatch())){
				// 初始 创建日期、更新日期、申请日期、
			Date nowTime = getNowTime();
			// 初始化金额
			BigDecimal amount = BigDecimal.ZERO;
			// 预付单实体
			BillAdvancedPaymentEntity baEntity = new BillAdvancedPaymentEntity();
			// 自动生成uid
			baEntity.setId(UUIDUtils.getUUID());
			//生成税务标记为02非运输专票
//			baEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
			
			// 预付单号
			baEntity.setAdvancesNo(settlementCommonService.getSettlementNoRule(SettlementNoRuleEnum.UF));
			
			//设置发票标记为02非运输专票
			baEntity.setInvoiceMark(SettlementDictionaryConstants.SETTLEMENT_INVOICE_MARK_TWO);
			
			
			// 费用申请部门（费用承担部门编码）
			baEntity.setPaymentOrgCode(billAdDto.getPaymentOrgCode());
			// 费用付款部门名称（费用承担部门名称）
			baEntity.setPaymentOrgName(billAdDto.getPaymentOrgName());
			
			
			
			//查询部门对象
			OrgAdministrativeInfoEntity deptEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(billAdDto.getPaymentOrgCode());
			if(deptEntity!=null){
				// 费用承担部门名称==付款部门编码和名称
				baEntity.setPaymentCompanyCode(deptEntity.getSubsidiaryCode());
				baEntity.setPaymentCompanyName(deptEntity.getSubsidiaryName());
				billAdDto.setPaymentCompanyCode(deptEntity.getSubsidiaryCode());
				billAdDto.setPaymentCompanyName(deptEntity.getSubsidiaryName());
			}
			
			// 申请金额(付款金额)
			baEntity.setAmount(billAdDto.getAmount());
			// 核销金额
			baEntity.setVerifyAmount(amount);
			// 未核销金额就是总金额
			baEntity.setUnverifyAmount(billAdDto.getAmount());
			// 发票抬头
			baEntity.setInvoiceTitle(billAdDto.getInvoiceTitle());
			// 省份编码
			baEntity.setProvinceCode(billAdDto.getProvinceCode());
			// 省份名称
			baEntity.setProvinceName(billAdDto.getProvinceName());
			// 城市名称
			baEntity.setCityName(billAdDto.getCityName());
			// 城市编码
			baEntity.setCityCode(billAdDto.getCityCode());
			// 申请日期
			baEntity.setBusinessDate(nowTime);
			// 是否有效
			baEntity.setActive(FossConstants.ACTIVE);
			// 审核状态，新增时，未审核
			baEntity.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__NOT_AUDIT);
			// 开户支行银行编码
			baEntity.setBankBranchCode(billAdDto.getBankBranchCode());
			// 开户支行银行名称
			baEntity.setBankBranchName(billAdDto.getBankBranchName());
			// 开户银行编码
			baEntity.setBankHqCode(billAdDto.getBankHqCode());
			// 开户银行名称
			baEntity.setBankHqName(billAdDto.getBankHqName());
			// 开户支行
			baEntity.setBankBranchName(billAdDto.getBankBranchName());
			// 开户名
			baEntity.setPayeeName(billAdDto.getPayeeName());
			// 手机号码
			baEntity.setMobilePhone(billAdDto.getMobilePhone());
			// 最迟汇款日期
			baEntity.setLastpaymentTime(billAdDto.getLastpaymentTime());
			
			/***
			 * 费用承担部门，就是付款部门 申请部门 ，申请部门，就是当前登录部门
			 */
			
			
			// 申请部门部门编码
			baEntity.setApplyOrgCode(cInfo.getCurrentDeptCode());
			// 申请部门部门名称
			baEntity.setApplyOrgName(cInfo.getCurrentDeptName());
			// 申请部门所属子公司编码
			baEntity.setApplyCompanyCode(cInfo.getDept().getSubsidiaryCode());
			// 申请部门所属子公司名称
			baEntity.setApplyCompanyName(cInfo.getDept().getSubsidiaryName());
			
			
			
			// 修改时间
			baEntity.setModifyTime(nowTime);
			// 修改人编码
			baEntity.setModifyUserCode(cInfo.getEmpCode());
			// 修改人名称
			baEntity.setModifyUserName(cInfo.getEmpName());
			// 申请人编号和名称
			baEntity.setCreateUserCode(cInfo.getEmpCode());
			baEntity.setCreateUserName(cInfo.getEmpName());
			// 预付单时间
			baEntity.setCreateTime(nowTime);
			// 客户编码和名称
			baEntity.setCustomerCode(billAdDto.getCustomerCode());
			baEntity.setCustomerName(billAdDto.getCustomerName());
			// 预付款付款方式
			baEntity.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
			// 记账日期
			baEntity.setAccountDate(nowTime);
			// 是否初始化
			baEntity.setIsInit(FossConstants.NO);
			// 银行账号
			baEntity.setAccountNo(billAdDto.getAccountNo());
			// 账户类型，对公或者对私
			baEntity.setPublicPrivateFlag(billAdDto.getAccountType());
			// 对账单号
			baEntity.setStatementBillNo(SettlementConstants.DEFAULT_BILL_NO);
			// 版本信息
			baEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
			// 币种
			baEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			// 是否红单
			baEntity.setIsRedBack(FossConstants.NO);
			// 单据类型
			baEntity.setBillType(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__BILL_TYPE__AIR);
	
			int rownum = billAdvancedPaymentService.addAdvancedPaymentEntity(
					baEntity, cInfo);
			if (1 != rownum) {
				throw new SettlementException("申请失败!");
			}
	
			if (SettlementConstants.EXTEND_SYSTEM_COSTCONTROL_SWITCH) {
				//对接费控
				if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)){
					try {
						billAdDto.setAdvancesNo(baEntity.getAdvancesNo());
						billAdDto.setBusinessDate(nowTime);
						PayToCostcontrolDto dto=setPayToCost(billAdDto,cInfo,payToSystem);
						payToCostcontrolSendService.payToCostcontrol(dto);
					} catch (Exception e) {
						LOGGER.error("新增空运预付款工作流申请失败："+e.getMessage(),e);
						throw new SettlementException("新增空运预付款工作流失败:"+e.getMessage());
					}
				//对接财务共享	
				}else if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_FSSC.equals(payToSystem)){
					try {
						// 申请备用金工作流
						billAdDto.setAdvancesNo(baEntity.getAdvancesNo());
						billAdDto.setBusinessDate(nowTime);
						PayToCostcontrolDto dto=setPayToCost(billAdDto,cInfo,payToSystem);
						payToFSSCSendService.payToCostcontrol(dto);
					} catch (Exception e) {
						LOGGER.error("新增空运预付款工作流申请失败："+e.getMessage(),e);
						throw new SettlementException("新增空运预付款工作流失败:"+e.getMessage());
					}
				}else{
					throw new SettlementException("付款对接系统配置有误！");
				}
			}
		}else{
			throw new SettlementException("只能空运总调才能新增预付单");
		}
		
	}
	
	
	
	
	/**
	 * 新增预付款申请参数设置，传递到费控
	 * @author 045738-foss-liqin
	 * @date 2013-1-14 下午6:41:27
	 * @param billAdDto
	 * @param cInfo
	 * @return
	 */
	private  PayToCostcontrolDto setPayToCost(BillAdvanceDto billAdDto,CurrentInfo cInfo,String payToSystem){
		
		if(null==billAdDto){
			throw new SettlementException("提交预付单到费控发生错误，预付单dto不能为空！", "");
		} 
		//给费控传递参数
		PayToCostcontrolDto dto=new PayToCostcontrolDto();
		dto.setPayBillDeptNo(cInfo.getDept().getUnifiedCode());//部门标杆编码
		dto.setPayBillDeptName(cInfo.getDept().getName());//部门名称
		dto.setPaymentBillNo(billAdDto.getAdvancesNo());//付款编号
		dto.setPayBillCelephone(billAdDto.getMobilePhone());
		dto.setPayBillAmount(billAdDto.getAmount());
		dto.setPayBillAppNo(cInfo.getEmpCode());//申请员工编号
		dto.setPayBillBankNo(billAdDto.getAccountNo());//银行账号
		
		//发票抬头，后续需要从界面传入code、开户行编码、开户支行编号、省份、城市、收款人编码、收款人名称
		dto.setPayBillComNo(billAdDto.getInvoiceTitle());//申请部门所属子公司名称?
		
		dto.setBankHqCode(billAdDto.getBankHqCode());
		dto.setBankBranchCode(billAdDto.getBankBranchCode());
		dto.setProvinceCode(billAdDto.getProvinceCode());
		dto.setCityCode(billAdDto.getCityCode());
		dto.setPayBillPayeeName(billAdDto.getPayeeName());
		dto.setPayBillPayeeCode(billAdDto.getPayeeCode());
		dto.setAccountType(billAdDto.getAccountType());
		
		// 付款部门标杆编码 、付款方最后付款日期、付款方名称、 付款工作流类型(预付款申请)、付款描叙、付款工作流类型
		dto.setPayBillLastTime(billAdDto.getLastpaymentTime());
		dto.setPayBillAppType(SettlementESBDictionaryConstants.COST_CONTROL_WORKFLOW_COST_PAY_ADV);//工作流类型
		dto.setPayType(SettlementESBDictionaryConstants.COST_CONTROL_PAYMENT_PAY);//支付类型  --付款
		dto.setBatchNo(dto.getPaymentBillNo());
		dto.setCurrency(SettlementESBDictionaryConstants.CURRENCY_CODE_RMB);//币种
		dto.setExchangeRate(null);//汇率
		List<ExpenseDetail> detail = new ArrayList<ExpenseDetail>();
		
		//对接费控
		if(SettlementDictionaryConstants.SETTLEMENT__PAYTOSYSTEM_TYPE_CONSCONTROL.equals(payToSystem)){
			//获取付款明细
			ExpenseDetail d = new ExpenseDetail();
			d.setBillNumber(billAdDto.getAdvancesNo());//明细单号
			d.setExpenseExplain(APPLYDESC);//费用说明
			d.setExpensesMoney(billAdDto.getAmount());//明细金额
			d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_ADVANCE);
			d.setPayBillExpDate(billAdDto.getBusinessDate());//业务发生日期

			OrgAdministrativeInfoEntity deptEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(billAdDto.getPaymentOrgCode());
			if(deptEntity==null){
				throw new SettlementException("新增预付款申请参数设置:传递到费控,预付单号："+billAdDto.getAccountNo()+"费用承担部门对应标杆编码  "+billAdDto.getApplyOrgCode()+"不存在!");
			}
			d.setExpenseCostCenter(deptEntity.getUnifiedCode());
			detail.add(d);
			dto.setExpenseDetail(detail);
		}else{
			List<com.deppon.fssc.inteface.domain.payment.ExpenseDetail> detailList = new ArrayList<com.deppon.fssc.inteface.domain.payment.ExpenseDetail>();
			//获取付款明细
			com.deppon.fssc.inteface.domain.payment.ExpenseDetail d = new com.deppon.fssc.inteface.domain.payment.ExpenseDetail();
			d.setBillNumber(billAdDto.getAdvancesNo());//明细单号
			d.setExpenseExplain(APPLYDESC);//费用说明
			d.setExpensesMoney(billAdDto.getAmount());//明细金额
			d.setExpensesType(SettlementESBDictionaryConstants.COST_CONTROL_PAY_DETAIL_ADVANCE);
			d.setPayBillExpDate(billAdDto.getBusinessDate());//业务发生日期

			OrgAdministrativeInfoEntity deptEntity = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(billAdDto.getPaymentOrgCode());
			if(deptEntity==null){
				throw new SettlementException("新增预付款申请参数设置:传递到费控,预付单号："+billAdDto.getAccountNo()+"费用承担部门对应标杆编码  "+billAdDto.getApplyOrgCode()+"不存在!");
			}
			d.setExpenseCostCenter(deptEntity.getUnifiedCode());
			detailList.add(d);
			dto.setExpenseDetailToFSSC(detailList);
			dto.setIsAutoAbatementLoan(FossConstants.NO);
		}

		return  dto;
	}

	

	
	/**
	 * 更新预付单的工作流号
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-26 下午4:50:52
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService#updateBillPayWorkFlow(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto)
	 */
	@Override
	public void updateBillPayWorkFlow(BillAdvanceDto billAdDto) {
		
		// 设置预付单工作流写入到系统的时间
		
		BillAdvancedPaymentEntity entity = billAdvancedPaymentService.queryBillAdvancedPaymentNo(billAdDto.getAdvancesNo(),FossConstants.YES);
		if(null==entity){
			throw new SettlementException("更新预付单工作流号时错误，根据预付单号:"+billAdDto.getAdvancesNo()+"查询预付单,信息未找到!");
		}
		
		//修改时间、工作流号、审批状态为审批中、预付单号
		entity.setModifyTime(getNowTime());
		entity.setWorkflowNo(billAdDto.getWorkflowNo());
		entity.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDITING);
		entity.setAdvancesNo(billAdDto.getAdvancesNo());
		
		CurrentInfo currentInfo = getCostControlCurrentInfo();
		int num = billAdvancedPaymentService.updatePaymentBillWorkFlow(entity,currentInfo);
		if (num != 1) {
			throw new SettlementException("更新空运预付款工作流失败");
		}
	}

	/**
	 * 费控返回预付款审批结果后，进行处理
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-29 上午11:16:52
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService#dealBillAdvancePayResultProcessor(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto)
	 */
	@Transactional
	@Override
	public void dealBillAdvancePayResultProcessor(BillAdvanceDto billAdDto) {
		// 获取费控系统用户信息
		CurrentInfo cInfo = SettlementUtil.getFKCurrentInfo();
		String notes = "";
		// 预付单号不能为空
		if (CollectionUtils.isEmpty(billAdDto.getAdvanPayNoList())) {
			throw new SettlementException("预付单号为空，参数传递发生错误！", "");
		}
		// 遍历预付款号集合,更新预付款状态
		for (String advanceBillNo : billAdDto.getAdvanPayNoList()) {
			// 根据预付单号查询预付单信息
			BillAdvancedPaymentEntity advanEntity = billAdvancedPaymentService.queryBillAdvancedPaymentNo(advanceBillNo,FossConstants.YES);
			// 校验预付单信息是否为空
			if (null==advanEntity)  {
				throw new SettlementException("预付单,实体为空！", "");
			}
			
			//取预付单的工作流号，如果没有工作流号，属于异常情况不允许操作			
			if(StringUtils.isEmpty(advanEntity.getWorkflowNo())){
				throw new SettlementException("预付单,工作流号为空，不能参与审批！", "");
			}
			
			
			// 校验，费控传入预付单金额和Foss查询出来金额是否相等
			if (billAdDto.getAmount().compareTo(advanEntity.getAmount()) > 0|| billAdDto.getAmount().compareTo(advanEntity.getAmount()) < 0) {
				throw new SettlementException("预付单号：" + advanceBillNo+ "付款金额和申请金额不相等！", "");
			}
			// 根据费控返回的付款状态修改预付单信息
			// 如果付款状态为审核通过
			if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__SUCESS.equals(billAdDto.getAuditStatus())) {
				// 申请状态为
				if (SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE.equals(advanEntity.getAuditStatus())|| SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_DISAGREE.equals(advanEntity.getAuditStatus())) {
					throw new SettlementException("预付单号：" + advanceBillNo+ "已经审批过或审批不通过的预付单，不能再次进行审批通过操作!");
				}
				// 设置审批条件
				advanEntity.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE);
				advanEntity.setAuditTime(new Date());//审批通过时间
				advanEntity.setModifyTime(new Date());
				advanEntity.setModifyUserCode(cInfo.getEmpCode());
				advanEntity.setModifyUserName(cInfo.getEmpName());

				// 调用公共修改预付单为审批通过
				billAdvancedPaymentService.updatePaymentBillResult(advanEntity,cInfo);
				// 写入操作日志，后续可根据预付单号进行查询
				notes = "预付单号：" + advanceBillNo + "申请，审批成功！";
				LOGGER.info(notes);
				// 如果为审批不通过
			} else if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__FAIL.equals(billAdDto
					.getAuditStatus())) {
				//如果为审批通过，再失败，则表示成功转失败
				if(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE.equals(advanEntity.getAuditStatus())){

					// 判断预付单状态，只有成功的，才能做成功转失败操作,否则，提示异常
					if (SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__NOT_AUDIT
							.equals(advanEntity.getAuditStatus())
							|| SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_DISAGREE
									.equals(advanEntity.getAuditStatus())) {
						throw new SettlementException("预付单单号 :" + advanceBillNo
								+ "申请审批状态为:" + advanEntity.getAuditStatus()
								+ "只有审批状态成功的,才能做审批成功转转失败操作!");
					}

					// 判断预付单审批通过的是否进入对账单或者参与核销，如果是不允许成功转失败
					if ((!SettlementConstants.DEFAULT_BILL_NO.equals(advanEntity
							.getStatementBillNo()))
							|| advanEntity.getVerifyAmount().compareTo(
									BigDecimal.ZERO) > 0) {
						throw new SettlementException("预付单号" + advanceBillNo
								+ "已经核销或已经进入对账单，不允许操作！");
					} else {
						// 需要定义在公共包下，作废单据
						advanEntity.setActive(FossConstants.INACTIVE);
						advanEntity.setAuditTime(new Date());//审批通过时间
						advanEntity.setModifyTime(new Date());
						advanEntity.setModifyUserCode(cInfo.getEmpCode());
						advanEntity.setModifyUserName(cInfo.getEmpName());
						int num = billAdvancedPaymentService.writeBackAdvancePay(
								advanEntity, cInfo);
						if (num != 1) {
							throw new SettlementException("预付单号" + advanceBillNo
									+ "数据库更新发生异常！", "");
						}
						// 生成预付单红冲单
						BillAdvancedPaymentEntity redBackEntity = setParamForAddRedBack(advanEntity);
						int addNum = billAdvancedPaymentService
								.addAdvancedPaymentEntity(redBackEntity, cInfo);

						if (addNum != 1) {
							throw new SettlementException("预付单号" + advanceBillNo
									+ "生成红单出错！");
						}
						// 写入日志
						notes = "预付单" + advanceBillNo + "成功转失败！";
						LOGGER.info(notes);
					}
				
				}else{
					if (SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_AGREE.equals(advanEntity.getAuditStatus())
							|| SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_DISAGREE.equals(advanEntity.getAuditStatus())) {
						throw new SettlementException("预付单号：" + advanceBillNo+ "已经审批过的预付单，不能再次进行审批不通过操作!", "");
					}
					
					// 审核不通过通过
					/**
					 *  设置审批条件为不通过、有效状态为无效、修改时间和修改人
					 */
					advanEntity.setAuditStatus(SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_DISAGREE);
					advanEntity.setModifyTime(new Date());
					advanEntity.setModifyUserCode(cInfo.getEmpCode());
					advanEntity.setModifyUserName(cInfo.getEmpName());
					advanEntity.setActive(FossConstants.INACTIVE);
					
					// 调用公共修改预付单为审批不通过
					int num=billAdvancedPaymentService.updatePaymentBillResultFail(advanEntity,cInfo);
					
					if (num != 1) {
						throw new SettlementException("预付单号" + advanceBillNo+ "数据库更新发生异常！", "");
					}
					
					// 生成预付单红冲单
					BillAdvancedPaymentEntity redBackEntity = setParamForAddRedBack(advanEntity);
					
					
					int addNum = billAdvancedPaymentService.addAdvancedPaymentEntity(redBackEntity, cInfo);

					if (addNum != 1) {
						throw new SettlementException("预付单号" + advanceBillNo+ "生成红单出错！");
					}
					
					// 写入日志
					notes = "预付单" + advanceBillNo + "审批失败！";
					LOGGER.info(notes);
				}
			} else if (SettlementESBDictionaryConstants.COST_CONTROL__RESULT__SUCCESS_TO_FAIL.equals(billAdDto
					.getAuditStatus())) {
				// 判断预付单状态，只有成功的，才能做成功转失败操作,否则，提示异常
				if (SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__NOT_AUDIT
						.equals(advanEntity.getAuditStatus())
						|| SettlementDictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS__AUDIT_DISAGREE
								.equals(advanEntity.getAuditStatus())) {
					throw new SettlementException("预付单单号 :" + advanceBillNo
							+ "申请审批状态为:" + advanEntity.getAuditStatus()
							+ "只有审批状态成功的,才能做审批成功转转失败操作!");
				}

				// 判断预付单审批通过的是否进入对账单或者参与核销，如果是不允许成功转失败
				if ((!SettlementConstants.DEFAULT_BILL_NO.equals(advanEntity
						.getStatementBillNo()))
						|| advanEntity.getVerifyAmount().compareTo(
								BigDecimal.ZERO) > 0) {
					throw new SettlementException("预付单号" + advanceBillNo
							+ "已经核销或已经进入对账单，不允许操作！");
				} else {
					// 需要定义在公共包下，作废单据
					advanEntity.setActive(FossConstants.INACTIVE);
					advanEntity.setModifyTime(new Date());
					advanEntity.setAuditTime(new Date());//审批通过时间
					advanEntity.setModifyUserCode(cInfo.getEmpCode());
					advanEntity.setModifyUserName(cInfo.getEmpName());
					int num = billAdvancedPaymentService.writeBackAdvancePay(
							advanEntity, cInfo);
					if (num != 1) {
						throw new SettlementException("预付单号" + advanceBillNo
								+ "数据库更新发生异常！", "");
					}
					// 生成预付单红冲单
					BillAdvancedPaymentEntity redBackEntity = setParamForAddRedBack(advanEntity);
					int addNum = billAdvancedPaymentService
							.addAdvancedPaymentEntity(redBackEntity, cInfo);

					if (addNum != 1) {
						throw new SettlementException("预付单号" + advanceBillNo
								+ "生成红单出错！");
					}
					// 写入日志
					notes = "预付单" + advanceBillNo + "成功转失败！";
					LOGGER.info(notes);
				}
			}
			// 写入日志
			savaOperatingLog(cInfo, notes, advanceBillNo,
					billAdDto.getApplyOrgCode());

		}
	}

	/**
	 * 写入日志
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-30 上午11:54:01
	 */
	private void savaOperatingLog(CurrentInfo cInfo, String notes,
			String paybillNo, String applyOrgCode) {

		// 写入操作日志，后续可根据预付单号进行查询
		OperatingLogEntity operLog = new OperatingLogEntity();
		// 操作单据编号
		operLog.setOperateBillNo(paybillNo);
		// 操作详情
		operLog.setNotes(notes);
		// 操作类型预付款审批工作流
		operLog.setOperateBillType(SettlementConstants.COST_CONTROL__WORK_FLOW_TYPE__ADVANCED_AUDTI_RESULT);
		// 付款部门标杆编码
		operLog.setOperateOrgCode(applyOrgCode);
		// 操作单据类型，预付单
		operLog.setOperateBillType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_BILL_TYPE__BILL_ADVANCED_PAYMENT);
		// 操作类型：预付款审批处理
		operLog.setOperateType(SettlementDictionaryConstants.OPERATING_LOG__OPERATE_TYPE__APPROVE);
		// 添加到日志
		operatingLogService.addOperatingLog(operLog, cInfo);
	}

	/**
	 * 红冲预付单时，设置参数
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-30 下午2:36:45
	 */
	private BillAdvancedPaymentEntity setParamForAddRedBack(
			BillAdvancedPaymentEntity advanQueryEntity) {
		BillAdvancedPaymentEntity redBackEntity = new BillAdvancedPaymentEntity();
		BeanUtils.copyProperties(advanQueryEntity, redBackEntity);
		// 当前时间
		Date now = getNowTime();
		// 获取，审批预付单时用户信息
		CurrentInfo cInfo = SettlementUtil.getFKCurrentInfo();
		// 预付款金额
		redBackEntity.setAmount(advanQueryEntity.getAmount().negate());
		// 已核销金额，取反
		redBackEntity.setVerifyAmount(advanQueryEntity.getVerifyAmount()
				.negate());
		// 未核销金额，取反
		redBackEntity.setUnverifyAmount(advanQueryEntity.getUnverifyAmount()
				.negate());
		// 是否红单
		redBackEntity
				.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__YES);
		// 设置默认版本信息
		redBackEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		// 有效
		redBackEntity.setActive(FossConstants.INACTIVE);
		// 分区键
		redBackEntity.setAccountDate(now);
		// 修改时间
		redBackEntity.setModifyTime(now);
		// 预付单id
		redBackEntity.setId(UUIDUtils.getUUID());
		// 创建时间
		redBackEntity.setCreateTime(now);
		// 修改用户code
		redBackEntity.setModifyUserCode(cInfo.getEmpCode());
		// 修改用户名称
		redBackEntity.setModifyUserName(cInfo.getEmpName());
		return redBackEntity;
	}

	/**
	 * 获取当前时间
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-30 下午3:51:16
	 */
	private Date getNowTime() {
		// 获取当前精确时间
		return org.apache.commons.lang.time.DateUtils.truncate(Calendar
				.getInstance().getTime(), Calendar.SECOND);
	}
	

	/**
	 * 获取，费控的登录信息
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-11-30 下午3:53:11
	 */
	private CurrentInfo getCostControlCurrentInfo() {
		CurrentInfo cInfo = SettlementUtil.getFKCurrentInfo();
		LOGGER.info("费控登录信息"+cInfo.getEmpCode()+"费控名称:"+cInfo.getEmpName());
		return cInfo;
	}



	/**
	 * 导出空运预付款
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午7:45:18
	 * @see com.deppon.foss.module.settlement.pay.api.server.service.IBillAdvanceApplysManageService#exportBillPayable(com.deppon.foss.module.settlement.pay.api.shared.dto.BillAdvanceDto,
	 *      com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public HSSFWorkbook exportBillAdvancePay(BillAdvanceDto billAdDto,
			CurrentInfo currentInfo) {
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (billAdDto.getArrayColumnNames() == null
				|| billAdDto.getArrayColumnNames().length == 0) {
			throw new SettlementException("导出Excel的列头名称不存在，请至少存在一列!");
		}
		/**
		 * 如果导出列头名称不存在，则抛出异常提示
		 */
		if (billAdDto.getArrayColumns() == null
				|| billAdDto.getArrayColumns().length == 0) {
			throw new SettlementException("导出Excel的列头不存在，请至少存在一列!");
		}
		// 调用执行方法，获取结果集
		BillAdvanceResultDto resultDto = queryBillAdvancePayApply(billAdDto, 0,
				Integer.MAX_VALUE, currentInfo);
		// 判断要导出数据是否存在，若不存在，则抛出异常提示
		if (resultDto == null
				|| resultDto.getQueryBillAdvancedPayList() == null
				|| resultDto.getQueryBillAdvancedPayList().size() == 0) {
			throw new SettlementException("没有要导出的数据!");
		}
		// 将要导出东西封装转化成Excel导出要用的List<List<String>> 格式
		List<List<String>> rowList = convertListFormEntity(
				resultDto.getQueryBillAdvancedPayList(),
				billAdDto.getArrayColumns());

		// 获取导出数据
		SheetData data = new SheetData();
		// 设置导出列头
		data.setRowHeads(billAdDto.getArrayColumnNames());
		data.setRowList(rowList);
		// 获取平台提供导出函数
		ExcelExport export = new ExcelExport();
		// 返回wookbook
		return export.exportExcel(data, "sheet",SettlementConstants.EXPORT_MAX_COUNT);
	}

	/**
	 * 预付单转化的实体
	 * 
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午8:01:16
	 */
	private List<List<String>> convertListFormEntity(
			List<BillAdvancedPaymentEntity> list, String[] header) {
		// 声明sheetList
		List<List<String>> sheetList = new ArrayList<List<String>>();
		//声明字典集合
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS);
		termCodes.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		termCodes.add(DictionaryConstants.FOSS_ACTIVE);
		termCodes.add(DictionaryConstants.SETTLEMENT__IS_RED_BACK);
		//后台获取数据字典对应的数据
		Map<String,Map<String,Object>> map = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		
		// 循环进行封装
		for (BillAdvancedPaymentEntity entity : list) {
			// 声明一行的rowList
			List<String> rowList = new ArrayList<String>();
			for (String columnName : header) {
				// 通过名称获取field
				Field field = ReflectionUtils.findField(
						BillAdvancedPaymentEntity.class, columnName);
				if (field != null) {
					// 通过传入字段获取值
					ReflectionUtils.makeAccessible(field);
					Object fieldValue = ReflectionUtils.getField(field, entity);
					// 如果为日期，需要进行格式化
					if (Date.class.equals(field.getType())
							&& fieldValue != null) {
						fieldValue = DateUtils.convert((Date) fieldValue,
								"yyyy-MM-dd HH:mm:ss");
					}
					// 将字段封装到list中
					if (fieldValue != null) {
						// 如果为预付单审核状态，则需要转化
						if (columnName.equals("auditStatus")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.BILL_ADVANCED_PAYMENT__AUDIT_STATUS,
									fieldValue.toString());
						}
						
						// 如果为付款方式，则需要装换
						if (columnName.equals("paymentType")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__PAYMENT_TYPE,
									fieldValue.toString());
						}
						
						// 是否红单
						if (columnName.equals("isRedBack")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.SETTLEMENT__IS_RED_BACK,
									fieldValue.toString());
						}
						
						// 是否有效
						if (columnName.equals("active")) {
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(map,
									DictionaryConstants.FOSS_ACTIVE,
									fieldValue.toString());
						}
						
						rowList.add(fieldValue.toString());
					} else {
						rowList.add(null);
					}
				}
			}
			sheetList.add(rowList);
		}
		return sheetList;
	}
	
	/**
	 * 预付单提交到费控返回工作流的异常时处理，预付单红冲记录失败
	 * @author 095793-foss-LiQin
	 * @date 2012-12-5 下午8:01:16
	 */
	@Override
	public void applyAdvancePayWorkflowFail(BillAdvanceDto billAdDto) {
		
		// 获取费控系统用户信息
		CurrentInfo cInfo = SettlementUtil.getFKCurrentInfo();
		String notes = "";
		
		String advanceBillNo=billAdDto.getAdvancesNo();
		// 预付单号不能为空
		if (StringUtils.isEmpty(advanceBillNo)) {
			throw new SettlementException("预付单号为空，参数传递发生错误！", "");
		}

		// 根据预付单号查询预付单信息
		BillAdvancedPaymentEntity advanEntity = billAdvancedPaymentService.queryBillAdvancedPaymentNo(advanceBillNo,FossConstants.YES);

		// 校验预付单信息是否为空
		if (advanEntity == null) {
			throw new SettlementException("预付单提交到费控返回工作流的异常时处理,按预付单号:"+advanceBillNo+"Foss未找到相关记录！", "");
		}

		advanEntity.setActive(FossConstants.INACTIVE);
		advanEntity.setModifyTime(new Date());
		advanEntity.setModifyUserCode(cInfo.getEmpCode());
		advanEntity.setModifyUserName(cInfo.getEmpName());
		advanEntity.setNotes(billAdDto.getNotes());
		
		int num = billAdvancedPaymentService.writeBackAdvancePay(advanEntity, cInfo);
		
		if (num != 1) {
			throw new SettlementException("预付单号" + advanceBillNo+ "数据库更新发生异常！", "");
		}
		
		// 生成预付单红冲单
		BillAdvancedPaymentEntity redBackEntity = setParamForAddRedBack(advanEntity);
		
		int addNum = billAdvancedPaymentService.addAdvancedPaymentEntity(redBackEntity, cInfo);

		if (addNum != 1) {
			throw new SettlementException("预付单号" + advanceBillNo+ "生成红单出错！");
		}
		
		// 写入日志
		notes = "预付单" + advanceBillNo + "工作流申请失败！";
		
		LOGGER.info(notes);
		savaOperatingLog(cInfo, notes, advanceBillNo,billAdDto.getApplyOrgCode());
	}

	/**
	 * @GET
	 * @return orgAdministrativeInfoService
	 */
	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		/*
		 *@get
		 *@ return orgAdministrativeInfoService
		 */
		return orgAdministrativeInfoService;
	}

	/**
	 * @SET
	 * @param orgAdministrativeInfoService
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		/*
		 *@set
		 *@this.orgAdministrativeInfoService = orgAdministrativeInfoService
		 */
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @GET
	 * @return billAdvanceApplysManageDao
	 */
	public IBillAdvanceApplysManageDao getBillAdvanceApplysManageDao() {
		/*
		 *@get
		 *@ return billAdvanceApplysManageDao
		 */
		return billAdvanceApplysManageDao;
	}

	/**
	 * @SET
	 * @param billAdvanceApplysManageDao
	 */
	public void setBillAdvanceApplysManageDao(
			IBillAdvanceApplysManageDao billAdvanceApplysManageDao) {
		/*
		 *@set
		 *@this.billAdvanceApplysManageDao = billAdvanceApplysManageDao
		 */
		this.billAdvanceApplysManageDao = billAdvanceApplysManageDao;
	}

	/**
	 * @GET
	 * @return billAdvancedPaymentService
	 */
	public IBillAdvancedPaymentService getBillAdvancedPaymentService() {
		/*
		 *@get
		 *@ return billAdvancedPaymentService
		 */
		return billAdvancedPaymentService;
	}

	/**
	 * @SET
	 * @param billAdvancedPaymentService
	 */
	public void setBillAdvancedPaymentService(
			IBillAdvancedPaymentService billAdvancedPaymentService) {
		/*
		 *@set
		 *@this.billAdvancedPaymentService = billAdvancedPaymentService
		 */
		this.billAdvancedPaymentService = billAdvancedPaymentService;
	}

	/**
	 * @GET
	 * @return settlementCommonService
	 */
	public ISettlementCommonService getSettlementCommonService() {
		/*
		 *@get
		 *@ return settlementCommonService
		 */
		return settlementCommonService;
	}

	/**
	 * @SET
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		/*
		 *@set
		 *@this.settlementCommonService = settlementCommonService
		 */
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @GET
	 * @return operatingLogService
	 */
	public IOperatingLogService getOperatingLogService() {
		/*
		 *@get
		 *@ return operatingLogService
		 */
		return operatingLogService;
	}

	/**
	 * @SET
	 * @param operatingLogService
	 */
	public void setOperatingLogService(IOperatingLogService operatingLogService) {
		/*
		 *@set
		 *@this.operatingLogService = operatingLogService
		 */
		this.operatingLogService = operatingLogService;
	}

	/**
	 * @GET
	 * @return payToCostcontrolSendService
	 */
	public IPayToCostcontrolSendService getPayToCostcontrolSendService() {
		/*
		 *@get
		 *@ return payToCostcontrolSendService
		 */
		return payToCostcontrolSendService;
	}

	/**
	 * @SET
	 * @param payToCostcontrolSendService
	 */
	public void setPayToCostcontrolSendService(
			IPayToCostcontrolSendService payToCostcontrolSendService) {
		/*
		 *@set
		 *@this.payToCostcontrolSendService = payToCostcontrolSendService
		 */
		this.payToCostcontrolSendService = payToCostcontrolSendService;
	}

	/**
	 * @SET
	 * @param payToFSSCSendService
	 */
	public void setPayToFSSCSendService(IPayToFSSCSendService payToFSSCSendService) {
		/*
		 *@set
		 *@this.payToFSSCSendService = payToFSSCSendService
		 */
		this.payToFSSCSendService = payToFSSCSendService;
	}

	

}