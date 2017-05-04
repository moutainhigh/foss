package com.deppon.foss.prt.statementconfreceipt;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.common.api.server.service.IStatementOfAccountService;
import com.deppon.foss.module.settlement.common.api.shared.domain.StatementOfAccountEntity;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;


/**
 * 对账单回执打印
 * @author foss-qiaolifeng
 * @date 2012-11-7 下午2:01:35
 */
public class StatementConfReceiptPrtDateSource implements JasperDataSource {

	// 注入日志
	private static final Logger logger = LoggerFactory.getLogger(StatementConfReceiptPrtDateSource.class);
	
	// 日期类型
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");// 此处需要多出一个空格，不然打印日期最后一位出不来。
	

	
	/** 
	 * 继承空方法
	 * @author foss-qiaolifeng
	 * @date 2013-3-17 下午6:01:29
	 * @param jasperContext
	 * @return
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map createParameterDataSource(JasperContext jasperContext) {

		//返回null
		return null;
	}

	/** 
	 * 打印回执
	 * @author foss-qiaolifeng
	 * @date 2012-11-7 下午6:05:13
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List createDetailDataSource(JasperContext jasperContext) {

		//记录日志
		logger.info("------------进入打印回执单开始-------------");
		
		// 声明打印值map
		List<Map> parameterList = new ArrayList<Map>();
		
		//获取对账单servcie
		IStatementOfAccountService statementOfAccountService = (IStatementOfAccountService)jasperContext.getApplicationContext().getBean("statementOfAccountService");
		 
		//根据对账单ID获取对账单信息
		String statementBillId = (String)jasperContext.get("statementBillNo");
		String createUserName = (String)jasperContext.get("createUserName");
		//获取对账单
		StatementOfAccountEntity entity = statementOfAccountService.queryByPrimaryKey(statementBillId);
		
		//对账单不为空
		if(entity!=null){
			
			//生成参数列表
			final Map<String, Object> parameter = new HashMap<String, Object>();
			
			//设置打印显示值
			parameter.put("STATEMENT_BILL_NO", entity.getStatementBillNo());//对账单号
			parameter.put("CUSTOMER_NAME", entity.getCustomerName());//客户名称
			parameter.put("CUSTOMER_CODE", entity.getCustomerCode());//客户编码
			parameter.put("CREATE_ORG_NAME", entity.getCreateOrgName());//创建网点名称
			parameter.put("CREATE_USER_NAME", createUserName);//创建用户名称
			parameter.put("UNIFIED_CODE", entity.getUnifiedCode());//组织标杆表明
			parameter.put("COMPANY_ACCOUNT_BANK_NO", entity.getCompanyAccountBankNo());//银行帐号
			parameter.put("ACCOUNT_USER_NAME", entity.getAccountUserName());//开户名
			parameter.put("BANK_BRANCH_NAME", entity.getBankBranchName());//支行名称
			
			//结算期间
			String beginDate =  dateFormat.format(entity.getPeriodBeginDate());//开始时间
			String endDate =  dateFormat.format(entity.getPeriodEndDate());//结束时间
			parameter.put("PERIOD_BEGIN_DATE", beginDate);//设置开始时间
			parameter.put("PERIOD_END_DATE", endDate);//设置结束时间
			
			//设置未还款金额、已还款金额
			BigDecimal unPaidAmount = BigDecimal.ZERO;//未还款金额
			BigDecimal paidAmount = BigDecimal.ZERO;//还款金额
			
			//设置以还款金额,如果未还款金额为null或者0，以还款金额为本期发生发生金额
			if(entity.getUnpaidAmount()==null ||entity.getUnpaidAmount().compareTo(BigDecimal.ZERO)==0){
				//计算金额
				paidAmount = entity.getPeriodAmount();
			
			//如果未还款金额>0，以还款金额为本期发生发生金额减去未还款金额,未还款金额为当前未还款金额
			}else{
				unPaidAmount = entity.getUnpaidAmount();//未还款金额
				paidAmount = entity.getPeriodAmount().subtract(entity.getUnpaidAmount());//已还款金额
			}
			parameter.put("PAID_AMOUNT", paidAmount);//设置未还款金额
			parameter.put("UNPAID_AMOUNT", unPaidAmount);//设置已还款金额
			
			parameter.put("PERIOD_REC_AMOUNT", entity.getPeriodRecAmount());//设置本期应收单金额
			parameter.put("PERIOD_PAY_AMOUNT", entity.getPeriodPayAmount());//设置本期应付单金额
			parameter.put("PERIOD_PRE_AMOUNT", entity.getPeriodPreAmount());//设置本期预收单金额
			parameter.put("PERIOD_ADV_AMOUNT", entity.getPeriodAdvAmount());//设置本期预付单金额
			parameter.put("PERIOD_AMOUNT", entity.getPeriodAmount());//设置本期发生单金额
			
			//加入列表
			parameterList.add(parameter);
		}
		
		//记录日志
		logger.info("------------进入打印回执单结束-------------");
		//返回数据
		return parameterList;
	}

}
