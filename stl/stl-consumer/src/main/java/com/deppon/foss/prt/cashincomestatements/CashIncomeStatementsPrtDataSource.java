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
 * FILE PATH        	: src/main/java/com/deppon/foss/prt/cashincomestatements/CashIncomeStatementsPrtDataSource.java
 * 
 * FILE NAME        	: CashIncomeStatementsPrtDataSource.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-06-28 	创建版	曾斌文	V0.1
2012-07-25	升级到0.9版本	曾斌文	V0.9
2012-10-12
现金收入报表增加收入部门字段	曾斌文	V1.0
 	 	  	 

1.	SUC-761 –生成现金收入报表
1.1	相关业务用例
BUC_FOSS_4.7.70.30_010  款项上缴
1.2	用例描述
	根据现金收款单、还款单、预收单计算出应缴款信息，同时根据财务自助传递过来的已缴款信息组合生成现金收入报表
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	生成现金收入报表	
1.4	操作用户角色
操作用户	描述
	
1.5	界面要求
1.5.1	表现方式
	后台接口，无界面
1.5.2	界面原型
	无
1.5.3	界面描述
无
1.6	操作步骤
1.6.1	生成应缴款信息
序号	基本步骤	相关数据	补充步骤
1	参数校验	参数信息	1、	对接口参数做校验
2、	校验规则参见SR1
3、	扩展事件参见1a
2	校验报表是否重复生成	参数信息	1、	查询现金收入报表，并校验是否存在
2、	参考业务规则SR2
3、	扩展事件参见2a
3	生成现金收入报表明细	参数信息	1、	生成现金收款单明细,来源单据为现金收款单、预收单、还款单
2、	从现金收款单中生成明细，参见业务规则SR3
3、	从预收单中生成明细，参见业务规则SR4
4、	从还款单中生成明细，参见业务规则SR5
4	生成现金收入报表	参数信息	1、	生成现金收款单，参见业务规则SR6

1.6.2	上传应缴款信息
序号	基本步骤	相关数据	补充步骤
5	将生成的现金收入报表数据传送给财务自助系统	参数信息	1、	调用接口将生成的现金收入报表数据传送给财务自助系统做处理,传数据包括部门、日期、营业款金额、非营业款金额、预收款金额

1.6.3	内部核销
序号	基本步骤	相关数据	补充步骤
6	现金收入报表内部核销	参数信息	1、	如果报表明细中存在金额小于0的报表明细，更新报表明细金额为0，并核销客户、运单号相同的现金收入报表明细的金额，核销顺序为业务日期小的先核销
2、	更新相应现金收入报表应缴款信息、已缴款信息。

1.6.4	扩展事件
序号	扩展事件	相关数据	备注
1a	当校验接口参数不正确时，终止操作并给客户异常信息提示		1)	当部门编码为空时，异常信息为“部门编码为空，不能生成现金收入报表”
2)	当报表日期为空时，异常信息为“报表日期为空，不能生成现金收入报表”
2a	当校验现金收入报表已经生成时，终止操作并给客户异常信息提示		1)	当现金收入报表已经存在时，异常信息为“现金收入报表已经生成，不能重复生成”

1.7	业务规则
序号	描述
SR1	1、	部门编码不能为空
2、	报表日期不能为空
SR2	1、	现金收入报表查询条件：
1）	现金收入报表部门编码为接口参数中的部门编码
2）	现金收入报表报表日期为接口数据中的报表日期
2、	不能存在现上述查询条件的金收入报表
SR3	1、	现金收款单查询条件为：
1）	现金收款单的记账日期大于等于报表日期开始时间（例如2012-6-28 00：00：00）
2）	现金收款单的记账日期小于（报表日期+1）开始时间（例如2012-6-29 00：00：00）
3）	现金收款单的缴款部门为接口参数中的部门
4）	现金收款单的付款方式为现金
5）	按运单号、业务日期、客户分组统计
2、	现金收入报表明细字段为：
a)	现金收入报表编码：对应现金收入报表编码
b)	运单号：现金收款单上运单号
c)	收入部门：现金收款单上的收入部门
d)	业务日期：运单业务日期
e)	单据类型：现金收款单
f)	客户编码：现金收款单客户编码
g)	金额：现金收款单金额
h)	未缴款：现金收款单金额
i)	已缴款：0
j)	营业款金额：如果是运单业务现金收款单，则为现金收款单金额，如果为非运单业务现金收款单，则为0
k)	非营业款金额：如果是运单业务现金收款单，则为0，如果为非运单业务现金收款单，则为现金收款单金额
l)	预收款金额：0
SR4	1、	预收单查询条件为：
1）	预收单的记账日期大于等于报表日期开始时间
2）	预收单的记账日期小于（报表日期+1）的开始时间
3）	预收单的预收部门为接口参数中的部门
4）	预收单的付款方式为现金
5）	按业务日期、客户分组统计
2、	现金收入报表明细字段为：
a)	现金收入报表编码：对应现金收入报表编码
b)	运单号：空
c)	收入部门：预收单上的收入部门
d)	业务日期：预收单业务日期
e)	单据类型：预收单
f)	客户编码：预收单客户编码
g)	金额：预收单预收金额
h)	未缴款：预收单预收金额
i)	已缴款：0
j)	营业款金额：0
k)	非营业款金额：0
l)	预收款金额：预收单预收金额
SR5	1、	还款单查询条件为：
1）	还款单的记账日期大于等于报表日期开始时间
2）	还款单的记账日期小于（报表日期+1）的开始时间
3）	还款单的还款部门为接口参数中的部门
4）	还款单的付款方式为现金
5）	按运单号、客户、业务日期
2、	现金收入报表明细字段为：
a)	现金收入报表编码：对应现金收入报表编码
b)	运单号：还款单对应的运单号
c)	收入部门：还款单上的收入部门
d)	业务日期：还款单业务日期
e)	单据类型：还款单
f)	客户编码：还款单客户编码
g)	金额：还款单还款金额
h)	未缴款：还款单还款金额
i)	已缴款：0
m)	营业款金额：如果是运单业务还款单，则为还款单还款金额，如果为非运单业务还款单，则为0
n)	非营业款金额：如果是运单业务还款单，则为0，如果为非运单业务还款单，则为还款单还款金额
j)	预收款金额：0
SR6	1、	生成现金收入报表，其字段如下：
a)	部门编码：接口参数中的部门编码
b)	报表编码：系统自动生成
c)	报表日期：接口参数中的报表日期
d)	应缴款：报表明细中所有金额之和
e)	已缴款：0
f)	未缴款：0
g)	营业款金额：报表明细中所有营业款金额之和
h)	非营业款金额：报表明细中所有非营业款金额之和
i)	预收款金额：报表明细中所有预收款金额之和
j)	创建时间：系统当前时间
k)	创建人：系统当前操作人

1.8	数据元素
1.8.1	接口参数
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
部门编码	需要缴款的部门编码	文本	无	40	是	
报表日期	报表日期	日期	无	10	是	

1.8.2	现金收入报表
字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
部门编码	部门编码	文本	无	40	是	
部门名称	部门名称	文本	无	40	是	
报表编码	系统自动生成	文本	无	40	是	
报表日期	报表日期	日期	无	10	是	
应缴款	部门应缴款	金额	无	38	是	
已缴款	部门已缴款	金额	无	38	是	
未缴款	部门未缴款	金额	无	38	是	
创建时间	报表创建时间	日期	无	10	是	
创建人	报表创建人	文本	无	40	是	
营业款金额	营业款	金额	无	38	是	
非营业款金额	非营业款	金额	无	38	是	
预收款金额	预收款	金额	无	38	是	

1.8.3	现金收入报表明细
字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
现金收入报表编码	对应现金收入报表编码	文本	无	40	是	
运单号	运单号	文本	无	10	否	
收入部门编码	收入部门编码	文本	无	40	是	
收入部门名称	收入部门名称	文本	无	40	是	
业务日期	运单业务日期	日期	无	10	是	
单据类型	现金收款单、预收单、还款单	枚举	无	10	是	
客户编码	单据对应客户编码	文本	无	40	否	
客户名称	单据对应客户名称	文本	无	40	否	
金额	单据对应金额	金额	无	38	是	
未缴款	未缴款	金额	无	38	是	
已缴款	已缴款	金额	无	38	是	
营业款金额	营业款	金额	无	38	是	
非营业款金额	非营业款	金额	无	38	是	
预收款金额	预收款	金额	无	38	是	

1.9	非功能性需求
使用量	2500个报表/每天，每年以60%速度增长
2012年全网估计用户数	1个
响应要求（如果与全系统要求不一致的话）	全公司各个部门报表在2小时内生成
使用时间段	8：00~20：00
高峰使用时间段	14：00~18：00



1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
上传现金收入报表数据接口		将生成的现金收入报表数据传送给财务自助系统做处理

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-06-28 	创建版	曾斌文	V0.1
2012-07-25 	升级到0.9版本	曾斌文	V0.9
 			
 	 	  	 

1.	SUC-762 –查询现金收入报表
1.1	相关业务用例
BUC_FOSS_4.7.70.30_010  款项上缴
1.2	用例描述
	查询现金收入报表和现金收入报表明细
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	现金收入报表已经生成	
后置条件	查询出现金收入报表数据	
1.4	操作用户角色
操作用户	描述
收银员、会计	查询现金收入报表
1.5	界面要求
1.5.1	表现方式
	Web
1.5.2	界面原型
现金收入报表查询界面：
 
现金收入报表明细界面：
 
1.5.3	界面描述
	页面初始化组件描述：
1、	报表日期（起）：日期输入控件，默认为当前系统日期的前7天
2、	报表日期（止）：日期输入控件，默认为当前系统日期
3、	现金收入报表默认不显示

	页面输入组件描述：
1、	报表日期（止）大于等于报表日期（起），当报表日期（止）小于报表日期（起）时，在页面上提示“报表日期（止）不能大于报表日期（起）”
2、	报表日期起止日期相差不能超过60天，如果超过60天，在页面上提示“报表起止日期相差不能超过60天”

	现金收入报表界面提供如按钮：
	查询
	重置
1.6	操作步骤
1.6.1	查询现金收入报表
序号	基本步骤	相关数据	补充步骤
1	页面初始化		1、	初始化现金收入报表查询界面
2	输入报表起止日期		
3	点击“查询”按钮		1、	根据报表日期查询现金收入报表
2、	校验参见业务规则SR1
3、	查询条件参见业务规则SR2

1.6.2	查看现金收入报表明细
序号	基本步骤	相关数据	补充步骤
4	点击操作区中的“详细”，弹出明细查看界面	参数信息	1、	通过查询点击行现金收入报表的报表编码查询出现金收入报表明细，并将数据填充到页面中

1.6.3	扩展事件
序号	扩展事件	相关数据	备注
3a	点击“重置”		页面恢复界面初始化状态
3b	查询条件不正确时，终止查询操作，并给客户异常信息提示		1)	当报表起止日期为空时，异常信息为“报表起止日期不能为空”
2)	当报表起止日期相差超过60天时，异常信息为“报表起止日期相差不能超过60天”

1.7	业务规则
序号	描述
SR1	1、	报表起止日期不能为空
2、	报表起止日期相差不能超过60天
SR2	1、	现金收入报表的部门为当前登陆人所在部门

1.8	数据元素
1.8.1	现金收入报表(输出)
字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
部门编码	部门编码	文本	无	40	是	
部门名称	部门名称	文本	无	40	是	
报表编码	系统自动生成	文本	无	40	是	
报表日期	报表日期	日期	无	10	是	
应缴款	部门应缴款	金额	无	38	是	
已缴款	部门已缴款	金额	无	38	是	
未缴款	部门未缴款	金额	无	38	是	
创建时间	报表创建时间	日期	无	10	是	
创建人	报表创建人	文本	无	40	是	
营业款金额	营业款	金额	无	38	是	
非营业款金额	非营业款	金额	无	38	是	
预收款金额	预收款	金额	无	38	是	

1.8.2	现金收入报表明细（输出）
字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
现金收入报表编码	对应现金收入报表编码	文本	无	40	是	
运单号	运单号	文本	无	10	否	
业务日期	运单业务日期	日期	无	10	是	
单据类型	现金收款单、预收单、还款单	枚举	无	10	是	
客户编码	单据对应客户编码	文本	无	40	否	
客户名称	单据对应客户名称	文本	无	40	否	
金额	单据对应金额	金额	无	38	是	
未缴款	未缴款	金额	无	38	是	
已缴款	已缴款	金额	无	38	是	
营业款金额	营业款	金额	无	38	是	
非营业款金额	非营业款	金额	无	38	是	
预收款金额	预收款	金额	无	38	是	

1.9	非功能性需求
使用量	2500个报表/每天，每年以60%速度增长
2012年全网估计用户数	5000个
响应要求（如果与全系统要求不一致的话）	查询在5S内响应
使用时间段	8：00~20：00
高峰使用时间段	14：00~18：00



1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		
		

 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 ******************************************************************************/

package com.deppon.foss.prt.cashincomestatements;

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
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashIncomeStatementsService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsDto;
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
public class CashIncomeStatementsPrtDataSource implements JasperDataSource {

	// 注入日志
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(CashIncomeStatementsPrtDataSource.class);

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
	public Map<String, Object> createParameterDataSource(
			JasperContext jasperContext) throws Exception {
		logger.info("开始填充打印数据源");

		// 声明打印值map
		Map<String, Object> parameter = new HashMap<String, Object>();
		
		//创建日期
		//String createDate = DateUtils.convert(new Date(),DateUtils.DATE_TIME_FORMAT);
		//parameter.put("createDate",createDate+" ");
		parameter.put("createDate",new Date());
		if (jasperContext != null && jasperContext.getParamMap() != null) {
			
			//现金收入报表明细
			CashIncomeStatementsQueryDto dto = new CashIncomeStatementsQueryDto();

			//查询类型
			String queryType = null;
			
			//设置查询类型
			if (jasperContext.get("queryType") != null) {
				queryType = (String) jasperContext.get("queryType");
			}

			// 因为Map直接转换成dto对象类型要求一致，因此在这里对日期类型的字段先进行转换
			if (jasperContext.get("startDate") != null
					&& jasperContext.get("endDate") != null) {
				
				//获取开始日期和结束日期
				String startDate = (String) jasperContext.get("startDate");
				String endDate = (String) jasperContext.get("endDate");
				
				// 按照日期进行查询
				if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryType)) {
					//设置开始日期
					parameter.put("startDate",startDate+ SettlementConstants.PRINT_DATE_TIME_MERGE_OPERATOR+ endDate);
					
					//转换开始日期并放入MAP中
					jasperContext.getParamMap().put("startDate",DateUtils.convert(startDate,DateUtils.DATE_FORMAT));
					
					//转换结束日期并放入MAP中
					jasperContext.getParamMap().put("endDate",DateUtils.convert(endDate, DateUtils.DATE_FORMAT));
				} else {
					// 日期为空，下面转换报错故，设置为new Date();
					Date date = new Date();
					
					//设置开始日期和结束日期
					jasperContext.getParamMap().put("startDate", date);
					jasperContext.getParamMap().put("endDate", date);
				}
			}

			// 从前台传入的Map值，直接转换成dto对象。
			BeanUtils.populate(dto, jasperContext.getParamMap());
			
			for (String key : jasperContext.getParamkeys()) {
				if (jasperContext.get(key) != null && !"startDate".equals(key) && !"endDate".equals(key) 
						&& StringUtil.isNotEmpty((String) jasperContext.get(key))) {
					
					if ("createUserCode".equals(key)) {// 当前操作者编码
						String createUserCode = (String) jasperContext.get(key);
						dto.setEmpCode(createUserCode);// 当前操作者编码
					}

					// 按照日期查询时,日期名称类型
					if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryType)
							&& "dateType".equals(key)) {
						String dateType = dto.getDateType();
						// 按照记账日期进行查询
						if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT
								.equals(dateType)) {
							//设置记账日期
							parameter.put("startDate",SettlementConstants.ACCOUNT_DATE_DESCRIPTION_NAME
													+ SettlementConstants.CHINESE_COLON
													+ parameter.get("startDate"));
							
						} else if (SettlementConstants.TAB_DATE_TYPE_FOR_CONFIRM
								.equals(dateType)) {
							//设置收银确认日期
							parameter.put("startDate",SettlementConstants.CASH_CONFIRM_TIME_DESCRIPTION_NAME
													+ SettlementConstants.CHINESE_COLON
													+ parameter.get("startDate"));
						}
						if(StringUtils.isNotEmpty((String)jasperContext.get("paymentType"))){
							List<String> paymentTypes = new ArrayList<String>();
							paymentTypes.add((String)jasperContext.get("paymentType"));
							dto.setPaymentTypes(paymentTypes);
						}
					}
					// 按对账单号
					else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryType)&& "statementBillNumbers".equals(key)) {
						//对账单号不能为空
						String number = (String) jasperContext.get(key);
						dto.setStatementBillNos(getNOs(number, "对账单号不能为空！"));
					}
					// 运单号
					else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryType) && "waybillNumbers".equals(key)) {
						String number = (String) jasperContext.get(key);
						//运单号不能为空
						dto.setWaybillNos(getNOs(number, "运单号不能为空！"));
					}
					// 按还款单号
					else if (SettlementConstants.TAB_QUERY_BY_REPAYMENT_NO.equals(queryType)&& "repaymentNumbers".equals(key)) {
						String number = (String) jasperContext.get(key);
						//还款单号不能为空
						dto.setRepaymentNos(getNOs(number, "还款单号不能为空！"));
					} // 按预收单号
					else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO.equals(queryType)&& "depositReceivedNumbers".equals(key)) {
						String number = (String) jasperContext.get(key);
						//预收单号不能为空
						dto.setDepositReceivedNos(getNOs(number, "预收单号不能为空！"));
					} else {
						// 保证key(xml定义的属性名称)
						//和Jrxml中定义的参数名称一致
						if (StringUtils.isNotEmpty((String) jasperContext.get(key))) {
							parameter.put(key, (String) jasperContext.get(key));
						}
					}
				}
			}
			if (StringUtils.isEmpty(queryType)) {
				throw new SettlementException("打印参数为空！");
			}

			// 设置当前操作者编码
			// dto.setEmpCode(dto.get);

			// 进行查询后台获取数据
			dto.setPaging(false);
			// Return an instance, which may be shared or independent, of the specified bean
			ICashIncomeStatementsService cashIncomeStatementsService = (ICashIncomeStatementsService) jasperContext
					.getApplicationContext().getBean(
							"cashIncomeStatementsService");

			// 按照日期进行查询打印的
			if (SettlementConstants.TAB_QUERY_BY_DATE.equals(queryType)) {
				// 判断日期参数不能为空
				if (dto.getStartDate() == null || dto.getEndDate() == null) {
					throw new SettlementException("日期参数不能为空！");
				}
				// 返回两个时间的相差天数
				Long i = DateUtils.getTimeDiff(DateUtils.convert(
						dto.getStartDate(), DateUtils.DATE_FORMAT), DateUtils
						.convert(dto.getEndDate(), DateUtils.DATE_FORMAT));

				// 天数 默认为7天
				int maxDays = SettlementConstants.DATE_LIMIT_DAYS_MONTH;
				// 查询开始日期和结束日期不能超过7天
				if (i != null && i.longValue() > maxDays) {
					throw new SettlementException("查询开始日期和结束日期不能超过"
							+ SettlementConstants.DATE_LIMIT_DAYS_MONTH + "天！");
				}

				// 处理结束日期，在原结束日期上加1
				dto.setEndDate(DateUtils.convert(
						DateUtils.addDay(dto.getEndDate(), 1),
						DateUtils.DATE_FORMAT));

				// 按照记账日期查询
				if (SettlementConstants.TAB_DATE_TYPE_FOR_ACCOUNT.equals(dto
						.getDateType())) {
					dto.setAccountStartDate(dto.getStartDate());
					dto.setAccountEndDate(dto.getEndDate());
				}
				// 按照确认收银日期
				else if (SettlementConstants.TAB_DATE_TYPE_FOR_CONFIRM
						.equals(dto.getDateType())) {
					dto.setCashConfirmStartDate(dto.getStartDate());
					dto.setCashConfirmEndDate(dto.getEndDate());
				}
				if (StringUtils.isNotEmpty(dto.getBillType())) {// 按照单据类型进行查询时

					// 选择单据类型为现金收款单时
					if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION
							.equals(dto.getBillType())) {
						// 当选择单据类型为现金收款单时,查询符合条件的现金收款单记录
						resultDto = cashIncomeStatementsService.queryBillCashByCondition(dto, null, null);
					}
					// 选择单据类型为预收单时
					else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED
							.equals(dto.getBillType())) {
						//选择的单据类型为预收单时，统计符合条件的预收单信息
						resultDto = cashIncomeStatementsService
								.queryBillDepositReceivedByCondition(dto, null,null);
					}
					// 选择单据类型为还款时
					else if (SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT
							.equals(dto.getBillType())) {
						//选择的单据类型为还款单时，查询符合条件的还款单信息
						resultDto = cashIncomeStatementsService.queryBillRepaymentByCondition(dto, null, null);
					}
				} else {// 未选择单据类型，默认查询三种单据（现金收款单/还款单/预收单）
					resultDto = cashIncomeStatementsService.queryByCondition(dto, null, null);
				}
			}
			// 按照对账单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_DZ_BILL_NO.equals(queryType)) {
				//根据对账单号集合，查询还款单信息
				resultDto = cashIncomeStatementsService.queryBillRepaymentByStatementBillNOs(dto);
			}
			// 按照运单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_WAYBILL_NO.equals(queryType)) {
				// 根据运单号查询所有的现金收款单信息
				resultDto = cashIncomeStatementsService.queryByWaybillNos(dto);
			}
			// 按照预收单号进行查询
			else if (SettlementConstants.TAB_QUERY_BY_DEPOIST_RECEIVED_NO.equals(queryType)) {
				//根据预收单号集合，查询预收单信息
				resultDto = cashIncomeStatementsService.queryBillDepositReceivedByNOs(dto);
			}

			// 设置打印页面的数据汇总信息
			if (resultDto != null) {
				parameter.put(
						"chTotalAmount",resultDto.getChTotalAmount() != null ? (resultDto
								.getChTotalAmount()) : "");// 现金总金额
				parameter.put(
						"cdTotalAmount",resultDto.getCdTotalAmount() != null ? resultDto
								.getCdTotalAmount() : "");// 银行卡总金额
				parameter.put(
						"ttTotalAmount",resultDto.getTtTotalAmount() != null ? resultDto
								.getTtTotalAmount() : "");// 电汇总金额
				parameter.put(
						"ntTotalAmount",resultDto.getNtTotalAmount() != null ? resultDto
								.getNtTotalAmount() : "");// 支票总金额
				parameter.put("olTotalAmount", resultDto.getOlTotalAmount() != null ? resultDto
								.getOlTotalAmount() : "");// 网上银行总金额
			}
		}
		return parameter;
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
	public List<Map<String, Object>> createDetailDataSource(
			JasperContext jasperContext) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 查询到的明细数据不为空时
		if (resultDto != null
				&& CollectionUtils.isNotEmpty(resultDto.getList())) {

			// 因原来在循环里面调用速度比较慢，一次性把所有的缓存获取到
			List<String> types = new ArrayList<String>();
			types.add(DictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE);// 单据类型
			types.add(DictionaryConstants.BILL_REPAYMENT__STATUS);// 单据状态
			types.add(DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);// 付款方式
			List<DataDictionaryEntity> listData = DictUtil
					.getDataByTermsCodes(types);

			for (int i = 0; i < resultDto.getList().size(); i++) {
				CashIncomeStatementsDto dto = resultDto.getList().get(i);
				Map<String, Object> map = new HashMap<String, Object>();
				if (dto != null) {
					//记帐日期
					map.put("accountDate", dto.getAccountDate());
					//单据编号
					map.put("billNo", dto.getBillNo());
					// 收款部门名称
					map.put("collectionOrgName", dto.getCollectionOrgName());
					// 收入部门名称
					map.put("generatingOrgName", dto.getGeneratingOrgName());
					//运单号
					map.put("waybillNo", dto.getWaybillNo());

					// 调用综合管理基础数据字典工具类
					map.put("billType",
							//根据数据valueCode和termsCode获取数据字典list集合中的ValueName描述内容.
							getDataDicByType(
									listData,
									dto.getBillType(),
									DictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE));// 单机类型
					map.put("status",
							//根据数据valueCode和termsCode获取数据字典list集合中的ValueName描述内容.
							getDataDicByType(listData, dto.getStatus(),
									DictionaryConstants.BILL_REPAYMENT__STATUS));// 状态
					map.put("paymentType",
							//根据数据valueCode和termsCode获取数据字典list集合中的ValueName描述内容.
							getDataDicByType(
									listData,
									dto.getPaymentType(),
									DictionaryConstants.SETTLEMENT__PAYMENT_TYPE));// 付款方式

					map.put("amount", dto.getAmount() != null ? dto.getAmount(): "");
					map.put("cashConfirmUserName", dto.getCashConfirmUserName());
					map.put("cashConfirmTime", dto.getCashConfirmTime());
					list.add(map);
				}
			}
		}
		return list;
	}

	/**
	 * 根据数据valueCode和termsCode获取数据字典list集合中的ValueName描述内容.
	 *
	 * @param listData the list data
	 * @param valueCode the value code
	 * @param termsCode the terms code
	 * @return the data dic by type
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-27 下午8:31:33
	 */
	private String getDataDicByType(List<DataDictionaryEntity> listData,
			String valueCode, String termsCode) {
		if (CollectionUtils.isEmpty(listData) || StringUtils.isEmpty(termsCode)
				|| StringUtils.isEmpty(valueCode)) {
			return "";
		}
		// 循环
		for (DataDictionaryEntity entity : listData) {
			// 判断
			if (termsCode.equals(entity.getTermsCode())&& 
					CollectionUtils.isNotEmpty(entity.getDataDictionaryValueEntityList())) {
				for (DataDictionaryValueEntity value : entity.getDataDictionaryValueEntityList()) {
					if (valueCode.equals(value.getValueCode())) {
						return value.getValueName();
					}
				}
			}
		}
		return "";
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
	private List<String> getNOs(String number, String errCode) {
		// 非空判断
		if (StringUtils.isEmpty(number)) {
			return null;
		}
		//数组转化集合
		String[] numbers = number.split(SettlementConstants.ENGLISH_COMMA);
		List<String> list = new ArrayList<String>(Arrays.asList(numbers));
		//集合非空判断
		if (CollectionUtils.isEmpty(list)) {
			throw new SettlementException(errCode);
		}
		return list;
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
