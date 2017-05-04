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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/CashIncomeStatementsService.java
 * 
 * FILE NAME        	: CashIncomeStatementsService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 报表名称：	现金收入报表										
    表头说明	会计日期	收款部门	收入部门	单号	运单号	单据类别	单据状态	付款方式	金额	收银员	收银时间
    示例	2012-6-28	上海开单查询组	上海虹桥营业部	YS00001125	40004010	现金收款单	提交	现金	58	陈小康	2012-6-28
    数据定义	单据的会计日期	单据的收款部门	单据的收入部门	单据的单号	单据的运单号	单据类别包括：现金收款单、预收单、还款单	单据状态：提交/收银确认	单据付款方式：现金、银行卡、支票、电汇	单据的金额	单据的收银确认人，无则为空	单据的收银确认时间，无则为空
    											
    报表说明：											
    1	报表概述	查询、导出、打印一定期间内营业部以现金、POS机、支票、汇款方式收到款项的数据，用于明确业务部门一定期间应交款项的报表。									
    2	所属业务模块	结算									
    3	需求详细描述	"界面要求：
    1.界面包括查询、导出、打印汇总、打印明细按钮；  
    2.每页显示默认显示1000条数据；
    3.显示合计金额和每种付款方式的总金额"									
    		"功能：
    查询：选择查询条件（记账日期、单据类别、单据状态、收款部门、收入部门、收银员）后，查询满足条件的有效版本的数据。
    导出：导出查询出的结果数据。
    打印汇总：根据特定的模版打印汇总报表；
    打印明细：根据特定的模版打印明细报表"									
    		报表权限：所有用户可查询本部门现金收入报表									
    		报表生成时间：实时									
    4	业务规则	"R1.进入现金收入报表的条件：
         a.单据类别为现金收款单、还款单、预收单
         b.单据状态为提交或收银确认状态"									
    		R2.不区分付款现金的方式，满足条件单据的所有付款方式都进入现金收入报表									
    		R3.只查询有效版本的数据									
    
    修订记录 
    日期 	修订内容 	修订人员 	版本号 
    2012-04-21 	创建版本	黄小波	V0.1
    2012-06-19	修改用例描叙，增加引用接送货系统用例条件，添加操作步骤，扩充步骤，数据元素，非功能性需求等	李琴	V0.5
    2012-07-11	修改  添加小票生成规则，数据元素等	李琴	V0.6
    2012-07-23	0.6版本改为0.9版本	李琴	V0.9
    2012-09-14	0.9版本存在文字描叙的错误	李琴	V1.0
    2012-10-12	1、SR3中提到"小票的预付金额大于0"，但小票界面的付款方式是选择的，没有预付金额这个字段，需要修改规则为："小票的付款方式为预付"
    2、设计上没有交账部门，那个收款就由那个部门缴款，故删除	李琴	V1.0
    
    1.	SUC-10生成现金收款单（接货开单）
    1.1	相关业务用例
    BUC_FOSS_4.7.10.10_010  现金与刷卡-接货开单
    BUC_FOSS_4.7.10.30_010  小票收入
    1.2	用例描述
    1.司机接到客户货物后或者客户直接在营业部、派送部（送货给客户时，客户要求发货，目前货量比较少）、接货开单查询组进行系统开单，并收取客户运输服务费，如果客户付款方式是现金（现金或者银行卡），在运单提交时调用此接口，生成财务单据现金收款单，以便后续统计进入财务报表；
    2.作为运单的补充的其它营业外收入，主要是营业部、派送部开的以现金（银行卡和现金）的小票单据,调用 此接口生成财务单据小票应收单。以便后续统计进入财务报表；
    3.查询现金收银报表，参见系统用例报表《DP-FOSS-结算系统用例-现金收入报表-V0.1.xls》
    1.3	用例条件
    条件类型	描述	引用系统用例
    前置条件	1	传入运单号，付款方式为现金或银行卡并且预付金额大于0。
    2	传入小票单号，付款方式为现金或者银行卡，并且金额大于0
    1.SUC-439-提交运单（接送货小组）
    2.SUC-17 生成应收单（接送货）
    3.SUC-2 -新增小票信息
    4.SUC-741查询/受理变更申请
    5.SUC-542 查询/审核变更申请
    后置条件	1.	生成现金收款单	
    1.4	操作用户角色
    操作用户	描述
    无	无
    1.5	界面要求
    1.5.1	表现方式
    接口
    1.5.2	界面原型
    无
    1.5.3	界面描述
    无
    1.6	操作步骤
    1.6.1	校验参数
    序号	基本步骤	相关数据	补充步骤
    1		校验单号是否正确	运单或小票	1.	检验传入单号，根据单据类型和长度，判读是运单还是小票号
    2.	如果是运单，校验运单单号是否真实存在有效， 扩展事件1a
    3.	如果是小票，校验小票，校验小票是否真实存在,扩展事件请参考1a
    2		校验是否重复调用接口	运单或小票	1.根据传入是运单还是小票，校验运单号和小票号对应是否重复生成现金收款单，参见扩展步骤2a。
    3		获取运单或者小票付款方式	运单或小票	1.先验证运单或者小票是否作废，参见-扩展步骤3a。
    2.校验运单付款方式是否正确，参考-扩展步骤4a。
    4		校验金额类字段是否正确	运单或小票	1.	如果是运单校验运金额单个字段有效性，参见扩展步骤5a。
    2.	如果是运单校验运单金额等内部数据有效性，参见扩展步骤6a。
    3.	如果是小票，校验小票的金额字段是否有效5a。
    5		校验发货客户编号和姓名	运单或小票	1.用户编码和姓名不能为空 7a
    6		校验产品类型有效性	运单或小票	1.	校验产品运输类型不能为空，如果不是，参见扩展步骤8a
    2.	产品类型为精准汽运（长途）、精准卡航、精准汽运（短途）、精准城运、 汽运偏线 、精准空运中一中。如果不是参见扩展步骤9a
    
    1.6.2	生成现金收款单
    
    序号	基本步骤	相关数据	补充步骤
    7		如果客户选择全额现金现付	输入：开单后，调用接口时传入参数，信息来源运单或小票
    输出：现金收款单	如果客户全额现付，参考业务规则SR1
    
    8		如果客户部分现付（到付大于0）	输入：开单后，调用接口时传入参数，信息来源运单
    输出：现金收款单	如果客户部分现付，业务规则参见SR2
    9		生成现金收款单		1.	根据单据类型来源运单或者来源小票生成对应的现金收款单
    
    
    
    序号	扩展事件	相关数据	备注
    1a		根据输入的运单和小票单号，调用运单校验接口，校验运单号是否存在。如果不存在。	异常消息	1．	如果传入是运单号，提示：“输入运单号不存在，不能继续执行操作！”操作终止。参考业务规则SR4
    2．	如果传入的是小票单据，提示“输入小票单号不存在，不能继续执行操作！” 操作终止。参考业务规则SR4
    2a		如果出入运单和小票编号对应的现金收款单编号，存在重复调用	异常消息	提示：“现金收款单，已经存在不能重复生成！操作终止“参考业务规则SR4
    3a		运单和小票记录是不是作废。	异常消息	1．	如果运单已经作废，提示：“该运单已经被作废，不能继续执行操作！” 参考业务规则SR4
    2．	如果小票已经作废，提示:“该运单已经被作废，不能继续执行操作！” 参考业务规则SR4
    4a		运单始发付款方式不是现金和银行卡（非临欠或者月结）。	异常消息	提示:“运单和小票的付款方式不正确，付款方式必须为现金或银行卡，不能继续执行相关操作” 参考业务规则SR4
    5a		小票的金额有效性	异常消息	判断小票金额是否为大于0等，不合法，提示:“小票的金额不正确”
    6a		校验运单金额，预付、到付、公布价运费、送货费、包装费、代收货款手续费、保价费，其它费用，优惠费用单个字段的有效性，其中任意一项为空或者小于0.	异常消息	提示“运单部分字段金额不正确，不能继续操作”操作终止。参考业务规则SR4
    7a		校验运单内部金额是否正确。如果运单的（预付+到付-代收货款）不等于（公布价运费、送货费、包装费、代收货款手续费、保价费、（其他费用-优惠费用）之和时	异常消息	提示“明细之和不等于总运费，不能继续操作”，该操作结束；参考业务规则SR4
    8a		产品运输类型不能为空	异常信息	提示：“产品运输类型不能为空！”
    9a		产品类型是否有效	异常信息	提示：“产品类型不正确”，生成操作，提示异常！
    
    1.7	业务规则
    序号	描述
    SR1		a)	如果运单的到付金额等于0，执行下列转换规则
    b)	现金收款单 ： 运单
    c)	现金总额：预付金额
    d)	公布价运费： 公布价运费；
    e)	送货费：送货费
    f)	包装手续费：包装手续费
    g)	代收货款手续费：代收货款手续费
    h)	保价费：保价费
    i)	其他费用：其它费用之和
    j)	收入部门：收货部门
    k)	收款客户：运单的发货客户
    SR2		a)	如果运单的到付金额大于0，并且预付金额大于0 ，执行下列转换规则
    b)	现金收款单 运单 
    c)	现金总额：运单预付金额
    d)	公布价运费：运单公布价运费 × (现付/（总运费））；
    e)	送货费：运单送货费  ×  (现付/（总运费））；
    f)	包装手续费：运单包装手续费  ×(现付/（总运费））；
    g)	代收货款手续费：运单代收货款手续费  × (现付/（总运费））；
    h)	保价费：运单保价费  ×  (现付/（总运费））；
    i)	其他费用：运单预付运费金额-公布价运费-送货费-运单包装手续费-代收货款手续费-保价费；
    j)	收入部门：运单的收货部门
    m)收款客户：运单的发货客户
    
    SR3		a)	如果小票的付款方式为预付，小票的支付现金金额大于0，执行以下转换规则
    b)	现金收款单：小票
    c)	现金总额：收入金额
    d)	收入公司：小票收入公司
    e)	收入部门：收入部门
    f)	收入人：收银员名称
    g)	收银时间：收银时间
    h)	收款类别：收银员、小票操作员 
    i)	收款事项：进仓费、放空费
    SR4	如果接口在执行过程中遇到异常信息，将异常信息抛出，然后对操作进行回滚。
    
    1.8	数据元素
    1.8.1	运单信息（输入）
    字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
    收入部门	收货部门	外键	无	44	是	
    收入公司	收货部门所属公司	外键	无	44	是	
    运单单号	开单运单单号	数字型字符	无	10	是	目前为8位
    收款方式	运单的付款方式	枚举	无	44	是	现金、银行卡
    派送方式	运单的派送方式	枚举	无	44	是	自提\送货上门
    客户编码	开单发货的客户编码	字符串	无	20	是	
    客户名称	开单发货的客户名称	字符串	无	200	是	
    产品类型	产品类型	枚举	无	44	是	精准汽运（长途）\精准卡航\ 精准汽运（短途）\精准城运\ 汽运偏线 \精准空运
    总运费	运单总运费	数字	无	10，2	是	
    公布价运费	运费	数字	无	10，2	是	
    送货费	送货费	数字	无	10，2	是	
    包装费	运单包装手续费	数字	无	10，2	是	
    代收货款手续费	运单代收收款手续费	数字	无	10，2	是	
    保价费	运单保价费	数字	无	10，2	是	
    其他费用	运单其它费用之和	数字	无	10，2	是	
    增值服务费	送货费+包装费+保价费+代收手续费+其他费用和	数字	无	10,2	是	
    优惠费率	优惠总合计，包括折扣、优惠券之类					
    预付金额	运单的预付金额	数字	无	10,2	是	
    到付金额	运单的到付金额	数字	无	10,2	是	
    业务日期	运单的开单时间	日期	无	8	是	
    到达部门编号	到达部门编号	文本	无	20	是	
    到达部门名称	到达部门名称	文本	无	50	是	
    录入部门编码	运单开单部门	外键	无	44	是	
    录入部门名称	运单开单名称	字符	无	200	是	
    录入人	运单录入人	外键	无	44	是	
    来源单据类型	现金收款单	字符	无	20	是	1．来源运单、2。来源小票
    记账日期	记账日期	日期	无	10	是	
    收银日期	收银日期	日期	无	10	是	
    确定收入日期	确定收入日期	日期	无	10	否	
    
    1.8.2	小票信息（输入）
    字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
    小票单号	小票单号	数字型字符		8	是	无
    收入部门名称	部门名称	字符		20	是	无
    收入部门编码	部门所属子公司名称	字符		20	是	无
    运单单号	运单号	数字型字符		8	是	无
    客户编号	客户编号	数字型字符		20	是	无
    客户名称	客户名称	字符		50	是	无
    客户类型	客户，代理	数字		10		1.代表客户
    2.代表代理
    收款方式	付款方式	字符		20	是	无
    收款部门编码	收款部门编码	字符		20		无
    收款部门	收款部门	字符		50		无
    收入类别	收入类型	字符		15	是	无
    金额	应付金额	数字		10	是	金额为大于0 的整数
    录入人员	录入人姓名	字符		10	是	无
    录入时间	录入时间	日期		10	是	无
    录入部门	录入部门	字符		20	是	无
    创建时间	创建时间	日期		10	是	无
    更新时间	更新时间	日期		10	是	无
    记账日期	记账日期	日期		10	是	无
    来源单据类型	来源小票	数字		1	是	1.	代表运单
    2.	代表运单
    
    1.8.3	现金收款单信息（运单输出）
    字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
    现金收款单号	系统生成	字符	无	20	是	
    收入部门编码	收入部门编码	外键	无	44	是	
    收入部门名称	收入部门名称	字符	无	100	是	
    运单单号	运单单号	数字型字符	无	10	是	
    收款方式	运单的始发付款方式	枚举	无	44	是	现金、银行卡
    客户编码	运单客户编号	字符	无	200	是	
    客户名称	运单客户名称	字符	无	200	是	
    产品类型	运单的产品类型	枚举	无	44	是	精准汽运（长途）\精准卡航\ 精准汽运（短途）\精准城运\ 汽运偏线 \精准空运
    总运费	运单的预付金额	数字	无	10，2	是	
    公布价运费	运费	数字	无	10，2	是	
    送货费	送货费	数字	无	10，2	是	
    包装手续费	包装手续费	数字	无	10，2	是	
    代收货款手续费	代收货款手续费	数字	无	10，2	是	
    保价费	保价费	数字	无	10，2	是	
    其他费用	其他费用	数字	无	10，2	是	
    优惠费用	优惠费用	数字	无	10,2	是	
    业务日期	运单的开单时间	日期	无	8	是	
    单据状态	提交	枚举	无	44	是	
    版本号	版本号	整数	无	4	是	版本为1
    是否有效	是否有效	布尔	无	1	是	版本标记，默认为true
    是否作废	现金收款单是否作废	布尔	无	1	是	默认为false
    是否红冲	是否做过红冲	布尔	无	1	是	是否为红冲单，默认为false
    是否初始化	是否初始化	布尔	无	1	是	
    到达部门编号	到达部门编号	文本	无	20	是	
    到达部门名称	到达部门名称	文本	无	40	是	
    录入部门编号	运单录入部门编号	外键	无	44	是	
    录入部门名称	运单录入部门名称	字符	无	200	是	
    录入人名称	运单开单人名称	字符	无	44	是	
    录入日期	当前服务器时间	日期	无	8	是	
    来源单据编码	来源单据编码	数字	无	44	是	
    收银日期	收银确认操作日期	日期	无	11	否	
    来源单据类别	来源运单，网上订单	枚举	无	44	是	
    确定收入日期	运单签收日期	日期	无	10	是	
    来源单据类型	1.来源运单	数字	无	44	是	
    创建日期	创建日期	日期	无	10	是	
    更新日期	更新日期	日期	无	10	是	
    记账日期	记账日期	日期	无	10	是	
    
    1.8.4	现金收款单（小票输出）
    字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
    部门名称	部门名称	字符		20	是	无
    所属子公司名称	部门所属子公司名称	字符		20	是	无
    客户名称	客户名称	字符		50	是	无
    客户编号	客户编号	编码		20	是	无
    小票编号	小票单号	编码		20	是	无
    金额	应收金额	数字		10	是	小票应收金额
    付款方式	付款方式	字符		  15	是	无
    版本号	小票对应的版本号	数字		10	是	无
    是否有效	标记该应收单是否有效	字符		10	是	无
    是否红单	标记该条应收单是否是红冲单据	字符		10	是	无
    是否初始化	标志该条应收单记录是否初始化	字符		10	是	无
    录入人员	制单人名称	字符		20	是	无
    录入时间	录入时间	日期		6	是	无
    单据子类型	单据对应子类型	字符		30	是	无
    业务日期	应收单发生的业务日期	日期		6	是	无
    记账日期	小票应收单的会计日期	日期		6	是	无
    录入人	单据制作人	字符		30	是	无
    录入日期	制单日期	日期		6	是	无
    产品类型	单据所对应货物的产品类型	字符		30	是	无
    创建时间	创建时间	日期		10	是	无
    更新时间	更新时间	日期		10	是	无
    来源单据类型	1.来源运单2.来源小票	数字		1	是	无
    
    1.9	非功能性需求
    使用量	现在处理的运单约为80000单/天
    2012年全网估计用户数	10000人
    响应要求（如果与全系统要求 不一致的话）	异步请求生成，可以再5秒之内响应
    使用时间段	7×24小时
    高峰使用时间段	全天
    
    1.10	接口描述：
    接口名称 	对方系统（外部系统或内部其他模块）	接口描述
    无	无	无
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
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICashIncomeStatementsEntityDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICashIncomeStatementsService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CashIncomeStatementsResultDto;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 现金收入明细报表Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-11-12 上午9:28:57
 * @since
 * @version
 */
public class CashIncomeStatementsService implements
		ICashIncomeStatementsService {

	/**
	 * 现金收入明细报表Dao
	 */
	private ICashIncomeStatementsEntityDao cashIncomeStatementsEntityDao;

	/**
	 * 根据不同的查询条件查询所有的（所有）现金收入明细记录
	 * 打印不需要分页 加入isPaging参数，为false不分页
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-8 下午3:21:20
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		// 加入查询条件不能为空，分页参数不能为空
		if (dto == null || !validatePageParam(dto, start, limit)) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		initCashIncomeStatementsQueryDto(dto);
		
		
		// 需要分页的话，统计总行数--为false打印不需要分页
		if (dto.isPaging()) {
			Long count = this.cashIncomeStatementsEntityDao
					.queryTotalCountByConition(dto);

			// 设置总行数
			resultDto.setTotalCount(count);
			if (count == null || count.longValue() == 0) {
				return resultDto;
			}

		}// 为false不分页，不统计总行数

		// 查询记录数
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryByCondition(dto, start, limit);
		resultDto.setList(list);

		// 设置汇总金额
		List<CashIncomeStatementsDto> lists = this.cashIncomeStatementsEntityDao
				.queryTotalAmountByCondition(dto);
		return this.sumTotalAmount(lists, resultDto);
	}

	/**
	 * 统计各种汇总金额 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午4:59:10
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || !validateParam(dto)) {
			throw new SettlementException("查询条件不能为空");
		}
		this.initCashIncomeStatementsQueryDto(dto);
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		// 设置汇总金额
		List<CashIncomeStatementsDto> lists = this.cashIncomeStatementsEntityDao
				.queryTotalAmountByCondition(dto);
		return this.sumTotalAmount(lists, resultDto);
	}

	/**
	 * 当选择单据类型为现金收款单时,查询符合条件的现金收款单记录
	 * 打印不需要分页 加入isPaging参数，为false不分页
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:05:22
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * 
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillCashByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		if (dto == null || !validatePageParam(dto, start, limit)) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashQueryDto(dto);

		// dto.isPaging() 为true需要分页的话，统计总行数
		if (dto.isPaging()) {
			Long count = this.cashIncomeStatementsEntityDao
					.queryBillCashTotalCountByCondition(dto);

			// 设置总行数
			resultDto.setTotalCount(count);
			if (count == null || count.longValue() == 0) {
				return resultDto;
			}

		}// dto.isPaging() 为false不分页，不统计总行数

		// 设置记录信息
		resultDto.setList(this.cashIncomeStatementsEntityDao
				.queryBillCashByCondition(dto, start, limit));

		// 设置汇总金额
		List<CashIncomeStatementsDto> lists = this.cashIncomeStatementsEntityDao
				.queryBillCashTotalAmountByCondition(dto);
		return this.sumTotalAmount(lists, resultDto);
	}

	/**
	 * 当选择单据类型为现金收款单时,统计符合条件的现金收款单的总金额 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:05:16
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillCashTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || !validateParam(dto)) {
			throw new SettlementException("查询条件不能为空");
		}
		this.initCashQueryDto(dto);
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		// 设置汇总金额
		List<CashIncomeStatementsDto> lists = this.cashIncomeStatementsEntityDao
				.queryBillCashTotalAmountByCondition(dto);
		return this.sumTotalAmount(lists, resultDto);
	}

	/**
	 * 根据运单号查询所有的现金收款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:09:42
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryByWaybillNos(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getWaybillNos())) {// 运单号集合不能为空
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashQueryDto(dto);

		// 查询记录数信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryByWaybillNos(dto);
		resultDto.setList(list);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 根据运单号集合，统计所有现金收款单的金额信息 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:07:41
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryTotalAmountByWaybillNos(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getWaybillNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashQueryDto(dto);
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryTotalAmountByWaybillNos(dto);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 选择的单据类型为还款单时，查询符合条件的还款单信息
	 * 打印不需要分页 加入isPaging参数，为false不分页
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:09:57
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillRepaymentByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		if (dto == null || !validatePageParam(dto, start, limit)) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);

		// 需要分页的话，统计总行数
		if (dto.isPaging()) {
			Long count = this.cashIncomeStatementsEntityDao
					.queryBillRepaymentTotalCountByCondition(dto);

			// 设置总行数
			resultDto.setTotalCount(count);
			if (count == null || count.longValue() == 0) {
				return resultDto;
			}
		}// dto.isPaging() 为false不分页，不统计总行数

		// 查询还款单信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentByCondition(dto, start, limit);
		resultDto.setList(list);

		// 查询总金额
		List<CashIncomeStatementsDto> lists = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentTotalAmountByCondition(dto);
		return this.sumTotalAmount(lists, resultDto);
	}

	/**
	 * 选择的单据类型为还款单时，查询符合条件的还款单总金额信息 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:10:49
	 * @param dto
	 * @return
	 */
	public CashIncomeStatementsResultDto queryBillRepaymentTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || !validateParam(dto)) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);
		// 查询总金额
		List<CashIncomeStatementsDto> lists = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentTotalAmountByCondition(dto);
		return this.sumTotalAmount(lists, resultDto);
	}

	/**
	 * 根据对账单号查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:12:16
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillRepaymentByStatementBillNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getStatementBillNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);

		// 查询还款单信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentByStatementBillNOs(dto);
		resultDto.setList(list);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 根据对账单号集合，统计还款单金额信息 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:16:32
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillRepaymentTotalAmountByStatementBillNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getStatementBillNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentTotalAmountByStatementBillNOs(dto);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 根据还款单号集合查询还款单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:13:43
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillRepaymentByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getRepaymentNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);

		// 查询还款单信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentByNOs(dto);
		resultDto.setList(list);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 根据还款单号集合，查询还款单的金额信息 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:19:04
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillRepaymentTotalAmountByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getRepaymentNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillRepaymentTotalAmountByNOs(dto);
		resultDto.setList(list);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单信息
	 * 打印不需要分页 加入isPaging参数，为false不分页
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:14:01
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillDepositReceivedByCondition(
			CashIncomeStatementsQueryDto dto, Integer start, Integer limit) {
		if (dto == null || !validatePageParam(dto, start, limit)) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);

		// 分页统计总行数
		if (dto.isPaging()) {
			Long count = this.cashIncomeStatementsEntityDao
					.queryBillDepositReceivedTotalCountByCondition(dto);
			resultDto.setTotalCount(count);
			if (count == null || count.longValue() == 0) {
				return resultDto;
			}
		}// dto.isPaging() 为false不分页，不统计总行数

		// 查询预收单信息
		resultDto.setList(this.cashIncomeStatementsEntityDao
				.queryBillDepositReceivedByCondition(dto, start, limit));

		// 设置汇总金额信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillDepositReceivedTotalAmountByCondition(dto);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 选择的单据类型为预收单时，统计符合条件的预收单金额信息 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:22:45
	 * @param dto
	 * @return
	 */
	public CashIncomeStatementsResultDto queryBillDepositReceivedTotalAmountByCondition(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || !validateParam(dto)) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);

		// 设置汇总金额信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillDepositReceivedTotalAmountByCondition(dto);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 根据预收单号集合，查询预收单信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:14:41
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillDepositReceivedByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getDepositReceivedNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);

		// 查询预收单信息
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillDepositReceivedByNOs(dto);
		resultDto.setList(list);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 根据预收单号集合，统计预收单金额信息 [只需要打印总金额信息]
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:24:03
	 * @param dto
	 * @return
	 */
	@Override
	public CashIncomeStatementsResultDto queryBillDepositReceivedTotalAmountByNOs(
			CashIncomeStatementsQueryDto dto) {
		if (dto == null || CollectionUtils.isEmpty(dto.getDepositReceivedNos())) {
			throw new SettlementException("查询条件不能为空");
		}
		CashIncomeStatementsResultDto resultDto = new CashIncomeStatementsResultDto();
		this.initCashIncomeStatementsQueryDto(dto);
		List<CashIncomeStatementsDto> list = this.cashIncomeStatementsEntityDao
				.queryBillDepositReceivedTotalAmountByNOs(dto);
		return this.sumTotalAmount(list, resultDto);
	}

	/**
	 * 计算汇总金额
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 下午2:36:47
	 * @param listTotalAmount
	 * @return
	 */
	private CashIncomeStatementsResultDto sumTotalAmount(
			List<CashIncomeStatementsDto> list,
			CashIncomeStatementsResultDto dto) {
		if (CollectionUtils.isEmpty(list)) {
			return dto;
		}
		// 设置总金额
		dto.setTotalAmount(BigDecimal.ZERO);
		// 现金（现钞）总金额
		dto.setChTotalAmount(BigDecimal.ZERO);
		// 设置银行卡总金额
		dto.setCdTotalAmount(BigDecimal.ZERO);
		// 设置电汇总金额
		dto.setTtTotalAmount(BigDecimal.ZERO);
		//设置支票总金额
		dto.setNtTotalAmount(BigDecimal.ZERO);
		//设置网上总金额
		dto.setOlTotalAmount(BigDecimal.ZERO);
		for (CashIncomeStatementsDto amountDto : list) {
			if (amountDto.getAmount() == null) {
				continue;
			}
			// 设置总金额
			dto.setTotalAmount(NumberUtils.add(dto.getTotalAmount(),
					amountDto.getAmount()));

			// 现金（现钞）总金额
			if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH // 现金
					.equals(amountDto.getPaymentType())) {
				// 设置现金总金额
				dto.setChTotalAmount(NumberUtils.add(dto.getChTotalAmount(),
						amountDto.getAmount()));

			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD// 银行卡
					.equals(amountDto.getPaymentType())) {
				// 设置银行卡总金额
				dto.setCdTotalAmount(NumberUtils.add(dto.getCdTotalAmount(),
						amountDto.getAmount()));
			//针对与预收单 的电汇和网上支付 统一为电汇，也只有预收单的网上支付能被查询到
			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER.equals(amountDto.getPaymentType())
					) {
				// 设置电汇总金额
				dto.setTtTotalAmount(NumberUtils.add(dto.getTtTotalAmount(),
						amountDto.getAmount()));
			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE// 支票
					.equals(amountDto.getPaymentType())) {
				//设置支票总金额
				dto.setNtTotalAmount(NumberUtils.add(dto.getNtTotalAmount(),
						amountDto.getAmount()));
			} else if (SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE// 网上支付
					.equals(amountDto.getPaymentType())) {
				//设置网上总金额
				dto.setOlTotalAmount(NumberUtils.add(dto.getOlTotalAmount(),
						amountDto.getAmount()));
			}
		}
		return dto;
	}

	/**
	 * 初始化查询参数数据 很多地方都需要初始化这些参数故提取出来
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:35:55
	 * @param dto
	 * @return
	 */
	private CashIncomeStatementsQueryDto initCashIncomeStatementsQueryDto(
			CashIncomeStatementsQueryDto dto) {
		
		// 现金收款单
		dto.setCashBillType(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION);

		// 还款单
		dto.setRepaymentBillType(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT);

		// 预收单
		dto.setDepositReceivedBillType(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__DEPOSIT_RECEIVED);
		
		// 设置支付方式类别集合（现金、银行卡、支票、电汇）     、默认查全部否则查界面选择的付款方式
		List<String> paymentTypes = new ArrayList<String>();
		if(StringUtils.isNotEmpty(dto.getPaymentType())){
			paymentTypes.add(dto.getPaymentType());
		}else{
			// 现金
			paymentTypes.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CASH);
			
			// 银行卡
			paymentTypes.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__CARD);
			
			// 支票
			paymentTypes.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__NOTE);
			
			// 电汇
			paymentTypes.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
			
			//网上支付
			paymentTypes.add(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE);
		}

		dto.setPaymentTypes(paymentTypes);
		
		//是否有效
		dto.setActive(FossConstants.YES);
		
		//营业状态 
		dto.setOrgActive(FossConstants.YES);
		
		return dto;
	}

	/**
	 * 验证带分页数据记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:00:47
	 * @param dto
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	private boolean validatePageParam(CashIncomeStatementsQueryDto dto,
			Integer start, Integer limit) {
		
		// 查询日期必须存在其中的一种
		if (((dto.getAccountStartDate() != null && dto.getAccountEndDate() != null) || 
		(dto.getCashConfirmStartDate() != null && dto.getCashConfirmEndDate() != null))) {
			
			// 需要分页,起始条数进行验证
			if (dto.isPaging()) {
				if (start != null && limit != null && limit.intValue() > 0) {
					return true;
				}
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验不分页的参数方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-13 下午5:01:23
	 * @param dto
	 * @return
	 */
	private boolean validateParam(CashIncomeStatementsQueryDto dto) {
		// 查询日期必须存在其中的一种
		if (((dto.getAccountStartDate() != null && dto.getAccountEndDate() != null) || 
		(dto.getCashConfirmStartDate() != null && dto.getCashConfirmEndDate() != null))) {
			return true;
		}
		return false;
	}

	/**
	 * 查询现金收款单的数据，不需要存放支付方式
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-9 上午9:59:17
	 * @param dto
	 * @return
	 */
	private CashIncomeStatementsQueryDto initCashQueryDto(
			CashIncomeStatementsQueryDto dto) {
		
		// 现金收款单
		dto.setCashBillType(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__CASH_COLLECTION);
		//还款
		dto.setRepaymentBillType(SettlementDictionaryConstants.CASH_COLLECTION_RPT_D__SOURCE_BILL_TYPE__REPAYMENT);
		
		//设置核销单类型：还款冲应收
		List<String> writeoffType = new ArrayList<String>();
		writeoffType.add(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__REPAYMENT_RECEIVABLE);
		dto.setWriteoffType(writeoffType);
		return dto;
	}

	/**
	 * @param cashIncomeStatementsEntityDao
	 */
	public void setCashIncomeStatementsEntityDao(
			ICashIncomeStatementsEntityDao cashIncomeStatementsEntityDao) {
		this.cashIncomeStatementsEntityDao = cashIncomeStatementsEntityDao;
	}

}
