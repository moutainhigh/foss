package com.deppon.foss.module.settlement.writeoff.api.shared.define;

/**
 * 对账核销模块常量类
 * @author dp-zhangjiheng
 * @date 2012-10-15 下午7:58:48
 */
public class SettlementWriteoffConstants {
    
	/**
	 * 对账单期次
	 */
	public static final String STATEMENT_PERIOD_TYPE="PERIOD";            //本期
	public static final String STATEMENT_BEGIN_PERIOD_TYPE="BEGIN_PERIOD";//期初
	
	/**
	 * 对账单结账状态
	 */
	public static final String STATEMENT_SETTLEMENTSTATE_SETTLETYPE="Y"; //已结清
	public static final String STATEMENT_SETTLEMENTSTATE_UNSETTLEEDTYPE="N";//未结清
	
	/**
	 * 定义map KEY值字符串
	 */
	public static final String periodList="periodList";  //本期明细集合
	public static final String beginPeriodList="beginPeriodList";//期初明细集合
	public static final String queryDto="queryDto";      //查询dto
	public static final String customerCode="customerCode";//客户编码
	public static final String customerName="customerName";//客户名称
	public static final String soaEntity="soaEntity";     //对账单实体
	public static final String soadEntity="soadEntity";     //对账单实体
    

	/**
	 * 收银员核销时间限制
	 * */
	public static final int WRITEOFF_LIMIT_DATE_CASHIER=2; //收银员核销时时间限制为2年之内
		
}
