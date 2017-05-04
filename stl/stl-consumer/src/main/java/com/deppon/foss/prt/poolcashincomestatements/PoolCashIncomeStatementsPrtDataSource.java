/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-consumer
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/poolcashincomestatements/PoolCashIncomeStatementsPrtDataSource.java
 * 
 * FILE NAME        	: PoolCashIncomeStatementsPrtDataSource.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 
 * SUC-12 查询现金收款单
 * 
 * 1.1	相关业务用例
 * 
 *	BUC_FOSS_4.7.10.10_010  现金与刷卡-接货开单
 *  BUC_FOSS_4.7.10.30_010  小票收入
 *
 *  1.2	用例描述
 *  
 * 	收银员根据记账日期、
 * 
 *   业务日期、
 *   
 *   运单号、
 *   
 *   客户名称（编码）、
 *   
 *   单据状态查询出现金收款单信息，
 *   
 *   查询出的现金收款单可以导出。
 *   
 *   1.3	用例条件
 *   
 *	条件类型	描述	引用系统用例
 *
 *	前置条件		
 *
 *	后置条件	1.	在界面显示查询的现金收款单	
 *   
 *   
 *  1.4	操作用户角色
 *  
 * 	操作用户	描述
 * 
 * 	收银员	查询本部门的现金收款单数据
 * 
 * 	会计 	查询管辖范围内部门的现金收款单数据
 * 
 * 
 * 1.5.3	界面描述
 * 
 * 查询条件界面
 * 	页面初始化组件描述：
 * 
 * 	1.	当选择按业务日期查询时，
 * 
 * 		开始日期和结束日期是指运单业务时间；
 * 
 * 		当选择按记账日期查询时，
 * 
 * 		开始时间和结束时间是指现金收款单的记账日期；
 * 
 * 		系统默认选择按业务日期查询，
 * 
 * 		页面初始化时系统自动提供默认值为当前用户登录日期，
 * 
 * 		用户也可以通过选择、
 * 
 * 		直接修改的方式修改日期数据；
 * 
 * 	2.	有效版本：初始化时默认为“全部”；
 *  
 * 	3.	付款方式：初始化时默认为“全部”；
 * 
 * 	4.	单据状态：初始化时默认为“全部”；
 * 
* 	5.	收款部门：若登录用户角色是收银员，
* 
* 		收款部门默认为登录人所在部门；
* 
* 		若登录用户角色是会计，
* 
* 		收款部门默认为空；
* 
* 	6.	运单号：初始化时组件中显示“请输入一个或10个内的多个运单号，
* 
* 		单号之间用半角逗号隔开”；
* 	7.	来源单号：
* 		同上
* 		页面输入组件描述：
* 	1.	收款部门：公共选择框；
* 	2.	收入部门：公共选择框；
* 	3.	客户信息：公共选择框；
* 	4.	运单号：输入框，
* 		用户可输入一个或多个运单号进行查询，
* 		单号之间用输入法半角状态逗号隔开；
* 	是否有效版本以下拉列表形式显示，
* 		内容为：
* 	是
* 	否
* 		付款方式以下拉列表形式显示，
* 		在数据字典维护，
* 		内容为：
* 	现金
* 	银行卡
* 	单据状态以下拉列表形式显示，
* 		在数据字典维护，
* 		内容为：
* 	提交
* 	收银确认
* 	查询条件界面提供以下按钮：
* 
* 	查询：点此按钮，
* 			根据查询条件查询出结果，
* 			现金收款单列表界面显示查询结果
* 
* 	重置：点此按钮将查询条件还原为初始化状态
* 
* 	按来源单号查询：
* 
* 	           需要能够支持按照运单号和小票单号来查询。
* 
* 	          现金收款单列表界面
* 
* 		页面初始化组件描述：
* 	1.	金额：查询结果金额合计，
* 
*       系统自动对查询出的数据进行金额合计计算，
*       
*       并显示在每一页的左下角；
*       
* 	2.	条数：查询结果条数合计；
*  
* 		每页最大显示条数以下拉列表形式显示，
*       内容为：
* 		500
* 		1000
* 		5000
* 		页面表格组件功能描述：
* 	1.	页面初始化时，
* 		不显示查询结果集；
* 	2.	用户可以对查询出的结果通过多选框选择一条或者多条记录，
* 		可以通过点击结果集列表左上角第一个多选框，
* 		实现对结果集信息的全选和清空操作；
* 	3.	用户可以分别通过业务日期、
* 		记账日期、
* 		付款方式、
* 		单据状态、
* 		收款部门、
* 		收入部门升序或者降序排列；
* 	4.	用户可以自定义显示结果集中的数据列；
* 	5.	结果集分页，
* 		且用户可选择每页最多显示条数，
* 		结果集的宽和高长度固定，
* 		横向和竖向以滚动条的形式显示
* 		列表界面提供以下按钮：
* 		导出：点此按钮可以导出选中的服务补救申请，
* 			未选择时默认为导出界面中所有记录
* 		查看明细 ：点此按钮可弹出查看该条记录的详细信息对话框
* 		现金收款单明细界面
* 
* 	界面中的组件与数据元素现金收款单列表信息（输出数据）一致
* 
*   序号	基本步骤	相关数据	补充步骤
*   
*	1	页面初始化	
*		付款方式、
*		单据状态、
*		收款部门信息
*		1.	系统默认选择按业务日期查询界面
*
*	2.	调用综合管理子系统查询出现金和银行卡两种付款类型,
*		填充付款类型下拉列表
*	3.	调用综合管理子系统查询出提交和收银确认两种单据状态,
*		填充单据状态下拉列表
*
*	参见业务规则SR1
*
*	2	选择按业务日期/记账日期查询	现金收款单信息	
*	3	选择是否有效版本	现金收款单信息	
*	4	选择开始日期及结束日期		参见业务规则SR2
*	5	选择收货部门	公共选择框（部门）	参见业务规则SR3
*	6	选择收入部门和客户信息	公共选择框（部门、客户）	
*	7	选择付款方式和单据状态
*		
*	8	输入运单号或来源单号
*		1.	系统自动忽略运单号前后的空格；
*		2.	检查输入运单号格式合法性
*
*	参见业务规则SR4
*
*	9	点击“查询”按钮	现金收款单信息
	*	1.	系统检查查询条件必填项是否已填
	*	2.	系统根据选择的查询条件查询数据，并显示在现金收款单列表界面；
	*	3.	查询出记录后，原查询条件不清空，可用于下一次查询；
*
*	参见业务规则SR5
*	10	点击“重置”按钮		将查询界面所有已填入和选择的查询条件变为初始化状态
*
 * 
 * 
 * 
 * 1.6.2	查看明细
*	序号	基本步骤	相关数据	补充步骤
*	
*	10	点击 “查看明细”按钮	现金收款单信息
*			
*		1.弹出该条记录的详细信息界面（
*									参见现金收款单明细界面
*								），
*								该界面包含了该条记录所有信息
*
*	1.6.3	导出
*	序号	基本步骤	相关数据	补充步骤
*		
*	11	选中一条或多条记录	现金收款单信息	
*		选中的记录首行的选择框 打钩，
*		同时该条记录标记颜色
*
*	12	点击“导出”按钮	
*		现金收款单信息	
*
*		1.	调用导出数据接口，
*		导出已选择的现金收款单信息，
*		未选择默认为导出查询出的所有信息
*
*	参见业务规则SR6
*	1.6.4	扩展事件
*	序号	扩展事件	相关数据	备注
*	4a	日期为空		
*					失去焦点时输入框附近显示红色字体提示“请选择查询日期”
*	4b	开始日期大于结束日期		
*					提示“开始日期不能大于结束日期!”
*	4c	开始日期与结束日期相差大于31天		
*					提示“开始日期与结束日期之间不能大于31天，请从新选择日期！”
*	8a	运单号为空		
*					失去焦点时输入框附近显示红色字体提示“请至少输入一个运单号”
*
*	8b	当输入的运单号个数超过10个时，
*		不能再进行输入		
*		提示“你好，输入的预收单号数量不能超过10个！”
*
*	9b	查询结果为空		弹出提示“无符合条件的数据”
	
*	1.7	业务规则
*	序号	描述
*	SR1	1.系统初始化的开始时间和结束时间默认相差0天；
*	SR2	1.	日期范围最大不超过31天
*	SR3	1.	收银员不能查询除本部门以外部门的现金收款单数据；
*			会计可查询所管辖范围内部门的数据；
*	2.	会计选择部门时可多选；
*	SR4	1.	输入的运单号的个数不能超过10个且不能为空；
*	2.	查询增加数据权限控制，
*
*		只能查询出（
*					应收部门/收入部门/始发部门为
*				）
*				当前登录用户客户可操作的营业部数据
*	SR5	1.	用户可自定义显示字段和隐藏字段；
*	SR6	1.	导出的数据以EXCEL表格的形式显示
*	2.	若用户已选择了一条或多条记录（
*									在记录的复选框中打钩
*									），
*									则导出的是用户所选择的记录；
*									若用户未选择任何记录，
*									则无论是否分页，
*									导出的是查询出的所有记录；
*
 * 
 * 1.8	数据元素
 *	1.8.1	查询条件（输入信息）
 *  字段名称
 * 	按业务日期查询
 * 	按记账日期查询
 * 	有效版本
 * 	开始日期
 * 	结束日期
 *  收款部门
 * 	收入部门
 * 	客户信息
 * 	付款方式
 * 	单据状态
 * 	运单号
 * 
 * 
 *现金收款单信息（输出元素）
 * 
 * 单据编号	单据编号
*	运单号	 运单号
*	客户名称	 客户名称
*	客户编码	 客户编码
*	收入部门 	收入部门
*	收入部门 编码	收入部门编码
*	收款部门	 开单部门
*	收款部门编码	 开单部门编码
*	收入部门所属子公司	收入部门所属子公司
*	收入部门所属子公司编码	收入部门所属子公司编码
*	收款部门所属子公司	收款部门所属子公司
*	收款部门所属子公司编码	收款部门所属子公司编码
*	单据状态	现金收款单的单据状态
*	金额	开单现金/银行卡金额
*	单据子类型	“现金收款单”
*	付款方式	付款方式
*	运费	货物运费
*	接货费收入	接货费
*	送货费	送货费
*	*包装收入	包装费
*	代收货款手续费收入	代收货款手续费
*	保价收入	保险费
*	其他	其他费用
*	小票	小票收入
*	产品类型	运单运输性质
*	版本号	现金收款单的版本号
*	是否有效版本	版本标记
*	是否红单	是否为红冲单
*	业务日期	运单业务日期
*	记账日期	现金收款单最新变动日期
*	制单人	运单开单人
*	制单人工号	
*	收银员	收银确认人姓名
*	收银员工号	
*	收银日期	收银确认日期
*	是否初始化数据	数据是否来源于初始化
*	备注	备注说明
*
 * 
 * 
 * 
 * 说明 
*	单据编号
*	运单号
*	客户名称
*	客户编码
*	收入部门
*	收入部门编码
*	开单部门
*	开单部门编码
*	收入部门所属子公司
*	收入部门所属子公司编码
*	收款部门所属子公司
*	收款部门所属子公司编码
*	现金收款单的单据状态
*	开单现金/银行卡金额
*	“现金收款单”
*	付款方式
*	货物运费
*	接货费
*	送货费
*	包装费
*	代收货款手续费
*	保险费
*	其他费用
*	小票收入
*	运单运输性质
*	现金收款单的版本号
*	版本标记
*	是否为红冲单
*	运单业务日期
*	现金收款单最新变动日期
*	运单开单人
*	
*	收银确认人姓名
*	
*	收银确认日期
*	数据是否来源于初始化
*	备注说明
*
 * 
 * 
 * 
 * 
 * 1.9	非功能性需求
* 	使用量	
* 			目前每天产生的现金收款单总量约为40000笔，
* 			每年以60%的速度增长
* 	2012年全网估计用户数	
* 			收银员总数量约2236名(
* 									截止2012.4.12，
* 									其增长速度与网点增长速度成正比
* 							)
* 	目前检查会计数量为约为300人
* 	响应要求（
* 				如果与全系统要求 不一致的话
* 			）	查询在3秒内响应；
* 	使用时间段	
* 				正常上班时间（9:30-21:30）
* 	高峰使用时间段	
* 			收银员每天盘点期间（16:00-21:30）
* 
 * 
 * 1.10	接口描述：
 *	接口名称 	对方系统（
 *						外部系统或内部其他模块
 *					）	接口描述
 *	查询部门信息接口	
 *				FOSS-综合管理子系统	
 *
 *				根据当前登录用户查询出部门信息
 *	查询客户信息接口
 *				FOSS-综合管理子系统	
 *				根据输入的字符模糊查询出客户名称和编码
 *
* 
 * 
 ******************************************************************************/

package com.deppon.foss.prt.poolcashincomestatements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashIncomeStatementsService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsResultDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperDataSource;
import com.deppon.foss.util.DateUtils;

/**
 * 打印现金收入明细报表数据源类.
 *
 * @author 099995-foss-wujiangtao
 * @date 2012-11-12 下午5:10:01
 * @since
 * @version
 */
public class PoolCashIncomeStatementsPrtDataSource implements JasperDataSource{

	// 注入日志
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(PoolCashIncomeStatementsPrtDataSource.class);
		
	/** 查询返回值Dto,考虑到两个方法里面同时调用一个查询方法，故顶一个resultDto对象. */	
	private CashIncomeStatementsResultDto resultDto;
		
	/**
	 * 填充其他模块的值.
	 *
	 * @param jasperContext the jasper context
	 * @return the map
	 * @throws Exception the exception
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 下午5:09:55
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createParameterDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public Map<String,Object> createParameterDataSource(JasperContext jasperContext)
			throws Exception {
		logger.info("开始填充打印数据源");
		
		// 声明打印值map
		Map<String, Object> parameter = new HashMap<String, Object>();
		if(jasperContext!=null&&jasperContext.getParamMap()!=null){
			//现金收入明细报表查询传入参数：DTO
			CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();
			String queryType = "";
			if (jasperContext.get("queryType") != null) {
				queryType = (String) jasperContext.get("queryType");
			}

			// 因为Map直接转换成dto对象类型要求一直，因此在这里对日期类型的字段先进行转换
			if (jasperContext.get("startDate") != null
					&& jasperContext.get("endDate") != null) {
				String startDate = (String) jasperContext.get("startDate");
				String endDate = (String) jasperContext.get("endDate");
				if(SettlementConstants.TAB_QUERY_BY_DATE.equals(queryType)){// 按照日期进行查询 
					//日期拼接符号2
					parameter.put("startDate", startDate + SettlementConstants.PRINT_DATE_TIME_MERGE_OPERATOR + endDate);
					jasperContext.getParamMap().put("startDate",DateUtils.convert(startDate, DateUtils.DATE_FORMAT));
					jasperContext.getParamMap().put("endDate",DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
				}else{
					//日期为空，下面转换报错故，设置为new一个新的date对象
					Date date=new  Date();
					jasperContext.getParamMap().put("startDate",date);
					jasperContext.getParamMap().put("endDate",date);
				}
			}
			
			// 从前台传入的Map值，直接转换成dto对象。
			BeanUtils.populate(dto, jasperContext.getParamMap());
			for (String key : jasperContext.getParamkeys()) {
				/**
				 * startDate，
				 * endDate 
				 * 判断
				 */
				if (jasperContext.get(key) != null
						&& !"startDate".equals(key)
						&& !"endDate".equals(key)
						&& StringUtil.isNotEmpty((String) jasperContext
								.get(key))) {
					
					if("createUserCode".equals(key)){//当前操作者编码
						String createUserCode=(String )jasperContext.get(key);
						dto.setEmpCode(createUserCode);//当前操作者编码
					}

					// 按照日期查询时,日期名称类型
					if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryType)&& "dateType".equals(key)) {
						String dateType = dto.getDateType();
						// 按照记账日期进行查询
						if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dateType)) {
							//记账日期
							parameter.put("startDate",
									SettlementConstants.ACCOUNT_DATE_DESCRIPTION_NAME+SettlementConstants.CHINESE_COLON + parameter.get("startDate"));
						} else if (SettlementConstants.TAB_DATE_TYPE_FOR_CONFIRM
								.equals(dateType)) {
							//收银确认日期
							parameter.put("startDate",
									SettlementConstants.CASH_CONFIRM_TIME_DESCRIPTION_NAME+SettlementConstants.CHINESE_COLON + parameter.get("startDate"));
						}
						if(StringUtils.isNotEmpty((String)jasperContext.get("paymentType"))){
							List<String> paymentTypes = new ArrayList<String>();
							paymentTypes.add((String)jasperContext.get("paymentType"));
							dto.setPaymentTypes(paymentTypes);
						}
					}
					//按对账单号
					else if(SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO
							.equals(queryType)&&"statementBillNumbers".equals(key)){
						//对账单号不能为空
						String number=(String) jasperContext.get(key);
						dto.setStatementBillNos(getNOs(number,"对账单号不能为空！"));
					}
					//运单号
					else if(SettlementConstants.TAB_QUERY_BY_WAYBILL_NO
							.equals(queryType)&&"waybillNumbers".equals(key)){
						//运单号不能为空
						String number=(String) jasperContext.get(key);
						dto.setWaybillNos(getNOs(number,"运单号不能为空！"));
					}
					//按还款单号
					else if(SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO
							.equals(queryType)&&"repaymentNumbers".equals(key)){
						//还款单号不能为空
						String number=(String) jasperContext.get(key);
						dto.setRepaymentNos(getNOs(number,"还款单号不能为空！"));
					} //按预收单号
					else if(SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO
							.equals(queryType)&&"depositReceivedNumbers".equals(key)){
						//预收单号不能为空
						String number=(String) jasperContext.get(key);
						dto.setDepositReceivedNos(getNOs(number,"预收单号不能为空！"));
					}
					else {
						// 保证key(xml定义的属性名称)和Jrxml中定义的参数名称一致
						if (StringUtils.isNotEmpty((String) jasperContext.get(key))) {
							parameter.put(key, (String) jasperContext.get(key));
						}
					}
				}
			}
			parameter.put("createDate", new Date());
			
			//打印参数为空
			if(StringUtils.isEmpty(queryType)){
				throw new SettlementException("打印参数为空！");
			}
			
			//进行查询
			//后台获取数据
			dto.setPaging(false);
			ICashIncomeStatementsService cashIncomeStatementsService=
					(ICashIncomeStatementsService)jasperContext.getApplicationContext()
					.getBean("cashIncomeStatementsService");
			
			// 按照日期查询
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryType)) {
				if(dto.getStartDate()==null||dto.getEndDate()==null){
					throw new SettlementException("日期参数不能为空！");
				}
				
				/**
				 * 返回两个时间的相差天数
						Parameters:
						startTime 对比的开始时间
						endTime 对比的结束时间
				 */
				Long i = DateUtils.getTimeDiff(DateUtils.convert(dto.getStartDate(), DateUtils.DATE_FORMAT),DateUtils.convert(dto.getEndDate(),DateUtils.DATE_FORMAT));
				
				// 天数 默认为7天
				int maxDays=SettlementConstants.DATE_LIMIT_DAYS_MONTH;
				if (i != null && i.longValue() > maxDays) {
					throw new SettlementException("查询开始日期和结束日期不能超过"+SettlementConstants.DATE_LIMIT_DAYS_MONTH+"天！");
				}
				
				// 处理结束日期，在原结束日期上加1
				dto.setEndDate(DateUtils.convert(DateUtils.addDay(dto.getEndDate(), 1),DateUtils.DATE_FORMAT));

				// 按照记账日期查询
				if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dto.getDateType())) {
					 dto.setAccountStartDate(dto.getStartDate());
					 dto.setAccountEndDate(dto.getEndDate());
				}
				// 按照查询日期
				else if (SettlementConstants.TAB_DATE_TYPE_FOR_CONFIRM.equals(dto.getDateType())) {
					//Date startDate=dto.getStartDate();
					dto.setCashConfirmStartDate(dto.getStartDate());
					//dto.setCashConfirmEndDate(dto.getEndDate());
					//Date endDate=dto.getEndDate();
					dto.setCashConfirmEndDate(dto.getEndDate());
				}
				
				if (StringUtils.isNotEmpty(dto.getBillType())) {// 按照单据类型进行查询时
					// 选择单据类型为现金收款单时
					if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION.equals(dto.getBillType())) {
						resultDto = cashIncomeStatementsService.queryBillCashTotalAmountByCondition(dto);
					}
					// 选择单据类型为预收单时
					else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED.equals(dto.getBillType())) {
						resultDto = cashIncomeStatementsService.queryBillDepositReceivedTotalAmountByCondition(dto);
					}
					// 选择单据类型为还款时
					else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT.equals(dto.getBillType())) {
						resultDto = cashIncomeStatementsService.queryBillRepaymentTotalAmountByCondition(dto);
					}
				} else {// 未选择单据类型，默认查询三种单据（现金收款单/还款单/预收单）
					resultDto = cashIncomeStatementsService.queryTotalAmountByCondition(dto);
					
				}
			}
			// 按照对账单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryType)) {
				resultDto = cashIncomeStatementsService.queryBillRepaymentTotalAmountByStatementBillNOs(dto);
			}
			// 按照运单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryType)) {
				resultDto = cashIncomeStatementsService.queryTotalAmountByWaybillNos(dto);
			}
			// 按照预收单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO.equals(queryType)) {
				resultDto = cashIncomeStatementsService.queryBillDepositReceivedTotalAmountByNOs(dto);
			}
			
			//设置打印页面的数据汇总信息
			if(resultDto!=null){
				//现金总金额
				parameter.put("chTotalAmount", resultDto.getChTotalAmount()!=null?resultDto.getChTotalAmount():"");
				//银行卡总金额
				parameter.put("cdTotalAmount", resultDto.getCdTotalAmount()!=null?resultDto.getCdTotalAmount():"");
				//电汇总金额
				parameter.put("ttTotalAmount", resultDto.getTtTotalAmount()!=null?resultDto.getTtTotalAmount():"");
				//支票总金额
				parameter.put("ntTotalAmount", resultDto.getNtTotalAmount()!=null?resultDto.getNtTotalAmount():"");
				//网上支付
				parameter.put("olTotalAmount", resultDto.getOlTotalAmount()!=null?resultDto.getOlTotalAmount():"");

			}					
		}
		//返回参数集合		
		return parameter;
	}
	
	/**
	 * 解析字符串，处理合并成集合.
	 *
	 * @param number the number
	 * @param errCode the err code
	 * @return the n os
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-14 下午8:31:28
	 */
	private List<String> getNOs(String number,String errCode){
		//判断界面的字符串是否为空		
		if(StringUtils.isEmpty(number)){
			return null;
		}
		/**
		 * 根据公共配置文件，
		 * 自动匹配并转化成对应的，
		 * 应收单、
		 * 应付单、
		 * 还款单号等
		 * 
		 */
		String[] numbers=number.split(SettlementConstants.ENGLISH_COMMA);
		/**
		 * 将界面的单号，
		 * 转成List集合		
		 */
		List<String> list=new ArrayList<String>(Arrays.asList(numbers));
		
		/**
		 * 判断传入是否为空
		 * 为空，进行异常处理		
		 */
		if(CollectionUtils.isEmpty(list)){
			throw new SettlementException(errCode);
		}
		//返回单号集合		
		return list;
	}

	/**
	 * 设置打印jrxml中Detail模块中的内容.
	 *
	 * @param jasperContext the jasper context
	 * @return the list
	 * @throws Exception the exception
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-12 下午5:10:25
	 * @see com.deppon.foss.print.jasper.JasperDataSource#createDetailDataSource(com.deppon.foss.print.jasper.JasperContext)
	 */
	@Override
	public List<Map<String,Object>> createDetailDataSource(JasperContext jasperContext)
			throws Exception {
		//打印时，转化设置
		return null;
	}
	
	/**
	 * Gets the result dto.
	 *
	 * @return the result dto
	 */
	public CashIncomeStatementsResultDto getResultDto() {
		return resultDto;
	}

	
	/**
	 * Sets the result dto.
	 *
	 * @param resultDto the new result dto
	 */
	public void setResultDto(CashIncomeStatementsResultDto resultDto) {
		this.resultDto = resultDto;
	}

}
