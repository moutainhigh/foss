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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/InvoiceService.java
 * 
 * FILE NAME        	: InvoiceService.java
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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-07 	创建版 	贲佳佳	V0.1
2012-07-03	修订版（操作步骤、业务规则）	贲佳佳	V0.1
 2012-7-16	修订版本	贲佳佳	V0.5
 2012-7-25	修订版本由0.5改为0.9	贲佳佳	V0.9
2012-09-14	增加小票的单据类型，增加两条规则	李琴	V1.1

1.	SUC-111 -标记发票已开
1.1	相关业务用例
BUC_FOSS_4.7.70.20_010 (标记开发票运单)
1.2	用例描述
财务自助在审核、作废发票时，调用此接口标记更新Foss已开票金额。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1.	发票管理员已申请开发票
2.	发票管理员需作废和审核发票

后置条件	从发票系统，获取到最新的已开发票的金额，并将结果返回到财务自助系统
DP-FOSS-综合查询系统用例-查询运单综合信息（SUC-687）
1.4	操作用户
1.5	界面要求
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	系统校验参数	操作人名称及工号，运单单号、金额、部门名称	1.  参见扩展事件1a；
2.  参见业务规则SR1。
2	获取运单详细信息	运单详细信息	1、	调用部门查询接口校验部门信息是否有效；
2、	根据运单单号、小票号查询是否为存在；
3、	根据运单、小票单号查询部门是否有应收的金额。
3	根据部门名称、运单号或者小票，从接口获取已开金额
	1.获取运单或者小票的的已开金额，并返回至财务自助系统 。


1.7	扩展事件
序号	扩展事件	相关数据	备注
1a	系统校验参数不通过，Foss系统，日志记录语言		1.	操作人为非发票管理员时，系统校验参数不通过，系统返回：“操作人错误”；
2.	运单单号为空时，系统校验参数不通过，系统返回：“运单单号为空，请提供有效单号”；
3.	发票申请状态为“未审批”、“审批中”、“已退回”状态，系统校验参数不通过，系统返回：“发票申请未通过”；
4.	金额小于等于0时，系统校验参数不通过，系统返回：“金额小于等于0”。
5.	调用部门查询接口校验部门信息为无效，系统返回：部门信息不存在，请重试；


1.8	业务规则
序号	描述
SR1	1.	运单单号为非空；
2.	小票单号不为空；
3.	操作人为发票管理员；
4.	传入部门不是财务自助时，提示错误！
5.	金额为大于0的整数
6.	开票金额是指除代收货款外的运费及小票费用；
7.	作废的小票不允许开票。财务自助开票信息来源运单、小票及对账单中的应收单
8.	财务自助审核通过，Foss异步调用将发票管理的已用金额，同步至Foss系统，并记录下日志
1.9		1.9	

1.9	数据元素
1.9.1	标记发票已开（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
单号
需要开发票的运单单号或者小票单号	数字		10	是	
操作人名称	操作人姓名	文本		10	是	
类型	小票或者运单	文本		2	是	



	



部门名称	开单部门名称	文本		20	是	
1.9.2	标记发票已开（输出）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
部门名称	申请开发票部门	文本		100	是	
部门编码		数字		100	是	界面不显示
单号
需要开发票的运单单号或者小票单号	数字		10	是	
类型	小票或运单	字符		2	是	
客户编码	开发票客户对象	数字		100	是	
客户名称		文本		100	是	
已开金额
运单和小票单号对应交易明细的已开发票金额	数字		20	是	


				
业务时间		数字		20	是	
申请人	申请开发票的人员	文本		10	是	界面不显示
申请人编号		数字		10	是	界面不显示
1.10	非功能性需求
使用量	目前据统计，截止2012年7月全月发票的使用量为接近55000票/月。
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	标记在3秒内响应 
使用时间段	正常白天上班时间（8:00-17：30）
高峰使用时间段	特殊业务，无高峰使用时间
1.11	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述


修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-07 	创建版 	贲佳佳	V0.1
2012-07-03	修订版（操作步骤、业务规则）	贲佳佳	V0.1
 2012-7-16	修订版本	贲佳佳	V0.5
 2012-7-25	修订版本由0.5改为0.9	贲佳佳	V0.9
2012-09-14	增加小票的单据类型，增加两条规则	李琴	V1.1

1.	SUC-111 -标记发票已开
1.1	相关业务用例
BUC_FOSS_4.7.70.20_010 (标记开发票运单)
1.2	用例描述
财务自助在审核、作废发票时，调用此接口标记更新Foss已开票金额。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1.	发票管理员已申请开发票
2.	发票管理员需作废和审核发票

后置条件	从发票系统，获取到最新的已开发票的金额，并将结果返回到财务自助系统
DP-FOSS-综合查询系统用例-查询运单综合信息（SUC-687）
1.4	操作用户
1.5	界面要求
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	系统校验参数	操作人名称及工号，运单单号、金额、部门名称	1.  参见扩展事件1a；
2.  参见业务规则SR1。
2	获取运单详细信息	运单详细信息	1、	调用部门查询接口校验部门信息是否有效；
2、	根据运单单号、小票号查询是否为存在；
3、	根据运单、小票单号查询部门是否有应收的金额。
3	根据部门名称、运单号或者小票，从接口获取已开金额
	1.获取运单或者小票的的已开金额，并返回至财务自助系统 。


1.7	扩展事件
序号	扩展事件	相关数据	备注
1a	系统校验参数不通过，Foss系统，日志记录语言		1.	操作人为非发票管理员时，系统校验参数不通过，系统返回：“操作人错误”；
2.	运单单号为空时，系统校验参数不通过，系统返回：“运单单号为空，请提供有效单号”；
3.	发票申请状态为“未审批”、“审批中”、“已退回”状态，系统校验参数不通过，系统返回：“发票申请未通过”；
4.	金额小于等于0时，系统校验参数不通过，系统返回：“金额小于等于0”。
5.	调用部门查询接口校验部门信息为无效，系统返回：部门信息不存在，请重试；


1.8	业务规则
序号	描述
SR1	1.	运单单号为非空；
2.	小票单号不为空；
3.	操作人为发票管理员；
4.	传入部门不是财务自助时，提示错误！
5.	金额为大于0的整数
6.	开票金额是指除代收货款外的运费及小票费用；
7.	作废的小票不允许开票。财务自助开票信息来源运单、小票及对账单中的应收单
8.	财务自助审核通过，Foss异步调用将发票管理的已用金额，同步至Foss系统，并记录下日志
1.9		1.9	

1.9	数据元素
1.9.1	标记发票已开（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
单号
需要开发票的运单单号或者小票单号	数字		10	是	
操作人名称	操作人姓名	文本		10	是	
类型	小票或者运单	文本		2	是	



	



部门名称	开单部门名称	文本		20	是	
1.9.2	标记发票已开（输出）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
部门名称	申请开发票部门	文本		100	是	
部门编码		数字		100	是	界面不显示
单号
需要开发票的运单单号或者小票单号	数字		10	是	
类型	小票或运单	字符		2	是	
客户编码	开发票客户对象	数字		100	是	
客户名称		文本		100	是	
已开金额
运单和小票单号对应交易明细的已开发票金额	数字		20	是	


				
业务时间		数字		20	是	
申请人	申请开发票的人员	文本		10	是	界面不显示
申请人编号		数字		10	是	界面不显示
1.10	非功能性需求
使用量	目前据统计，截止2012年7月全月发票的使用量为接近55000票/月。
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	标记在3秒内响应 
使用时间段	正常白天上班时间（8:00-17：30）
高峰使用时间段	特殊业务，无高峰使用时间
1.11	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-07 	创建版 	贲佳佳	V0.1
2012-07-03	修订版（操作步骤、业务规则）	贲佳佳	V0.1
 2012-7-16	修订版本	贲佳佳	V0.5
 2012-7-25	修订版本由0.5改为0.9	贲佳佳	V0.9
2012-09-14	增加小票的单据类型，增加两条规则	李琴	V1.1

1.	SUC-111 -标记发票已开
1.1	相关业务用例
BUC_FOSS_4.7.70.20_010 (标记开发票运单)
1.2	用例描述
财务自助在审核、作废发票时，调用此接口标记更新Foss已开票金额。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1.	发票管理员已申请开发票
2.	发票管理员需作废和审核发票

后置条件	从发票系统，获取到最新的已开发票的金额，并将结果返回到财务自助系统
DP-FOSS-综合查询系统用例-查询运单综合信息（SUC-687）
1.4	操作用户
1.5	界面要求
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	系统校验参数	操作人名称及工号，运单单号、金额、部门名称	1.  参见扩展事件1a；
2.  参见业务规则SR1。
2	获取运单详细信息	运单详细信息	1、	调用部门查询接口校验部门信息是否有效；
2、	根据运单单号、小票号查询是否为存在；
3、	根据运单、小票单号查询部门是否有应收的金额。
3	根据部门名称、运单号或者小票，从接口获取已开金额
	1.获取运单或者小票的的已开金额，并返回至财务自助系统 。


1.7	扩展事件
序号	扩展事件	相关数据	备注
1a	系统校验参数不通过，Foss系统，日志记录语言		1.	操作人为非发票管理员时，系统校验参数不通过，系统返回：“操作人错误”；
2.	运单单号为空时，系统校验参数不通过，系统返回：“运单单号为空，请提供有效单号”；
3.	发票申请状态为“未审批”、“审批中”、“已退回”状态，系统校验参数不通过，系统返回：“发票申请未通过”；
4.	金额小于等于0时，系统校验参数不通过，系统返回：“金额小于等于0”。
5.	调用部门查询接口校验部门信息为无效，系统返回：部门信息不存在，请重试；


1.8	业务规则
序号	描述
SR1	1.	运单单号为非空；
2.	小票单号不为空；
3.	操作人为发票管理员；
4.	传入部门不是财务自助时，提示错误！
5.	金额为大于0的整数
6.	开票金额是指除代收货款外的运费及小票费用；
7.	作废的小票不允许开票。财务自助开票信息来源运单、小票及对账单中的应收单
8.	财务自助审核通过，Foss异步调用将发票管理的已用金额，同步至Foss系统，并记录下日志
1.9		1.9	

1.9	数据元素
1.9.1	标记发票已开（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
单号
需要开发票的运单单号或者小票单号	数字		10	是	
操作人名称	操作人姓名	文本		10	是	
类型	小票或者运单	文本		2	是	



	



部门名称	开单部门名称	文本		20	是	
1.9.2	标记发票已开（输出）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
部门名称	申请开发票部门	文本		100	是	
部门编码		数字		100	是	界面不显示
单号
需要开发票的运单单号或者小票单号	数字		10	是	
类型	小票或运单	字符		2	是	
客户编码	开发票客户对象	数字		100	是	
客户名称		文本		100	是	
已开金额
运单和小票单号对应交易明细的已开发票金额	数字		20	是	


				
业务时间		数字		20	是	
申请人	申请开发票的人员	文本		10	是	界面不显示
申请人编号		数字		10	是	界面不显示
1.10	非功能性需求
使用量	目前据统计，截止2012年7月全月发票的使用量为接近55000票/月。
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	标记在3秒内响应 
使用时间段	正常白天上班时间（8:00-17：30）
高峰使用时间段	特殊业务，无高峰使用时间
1.11	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述


修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-07 	创建版 	贲佳佳	V0.1
2012-07-03	修订版（操作步骤、业务规则）	贲佳佳	V0.1
 2012-7-16	修订版本	贲佳佳	V0.5
 2012-7-25	修订版本由0.5改为0.9	贲佳佳	V0.9
2012-09-14	增加小票的单据类型，增加两条规则	李琴	V1.1

1.	SUC-111 -标记发票已开
1.1	相关业务用例
BUC_FOSS_4.7.70.20_010 (标记开发票运单)
1.2	用例描述
财务自助在审核、作废发票时，调用此接口标记更新Foss已开票金额。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1.	发票管理员已申请开发票
2.	发票管理员需作废和审核发票

后置条件	从发票系统，获取到最新的已开发票的金额，并将结果返回到财务自助系统
DP-FOSS-综合查询系统用例-查询运单综合信息（SUC-687）
1.4	操作用户
1.5	界面要求
1.6	操作步骤
序号	基本步骤	相关数据	补充步骤
1	系统校验参数	操作人名称及工号，运单单号、金额、部门名称	1.  参见扩展事件1a；
2.  参见业务规则SR1。
2	获取运单详细信息	运单详细信息	1、	调用部门查询接口校验部门信息是否有效；
2、	根据运单单号、小票号查询是否为存在；
3、	根据运单、小票单号查询部门是否有应收的金额。
3	根据部门名称、运单号或者小票，从接口获取已开金额
	1.获取运单或者小票的的已开金额，并返回至财务自助系统 。


1.7	扩展事件
序号	扩展事件	相关数据	备注
1a	系统校验参数不通过，Foss系统，日志记录语言		1.	操作人为非发票管理员时，系统校验参数不通过，系统返回：“操作人错误”；
2.	运单单号为空时，系统校验参数不通过，系统返回：“运单单号为空，请提供有效单号”；
3.	发票申请状态为“未审批”、“审批中”、“已退回”状态，系统校验参数不通过，系统返回：“发票申请未通过”；
4.	金额小于等于0时，系统校验参数不通过，系统返回：“金额小于等于0”。
5.	调用部门查询接口校验部门信息为无效，系统返回：部门信息不存在，请重试；


1.8	业务规则
序号	描述
SR1	1.	运单单号为非空；
2.	小票单号不为空；
3.	操作人为发票管理员；
4.	传入部门不是财务自助时，提示错误！
5.	金额为大于0的整数
6.	开票金额是指除代收货款外的运费及小票费用；
7.	作废的小票不允许开票。财务自助开票信息来源运单、小票及对账单中的应收单
8.	财务自助审核通过，Foss异步调用将发票管理的已用金额，同步至Foss系统，并记录下日志
1.9		1.9	

1.9	数据元素
1.9.1	标记发票已开（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
单号
需要开发票的运单单号或者小票单号	数字		10	是	
操作人名称	操作人姓名	文本		10	是	
类型	小票或者运单	文本		2	是	



	



部门名称	开单部门名称	文本		20	是	
1.9.2	标记发票已开（输出）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
部门名称	申请开发票部门	文本		100	是	
部门编码		数字		100	是	界面不显示
单号
需要开发票的运单单号或者小票单号	数字		10	是	
类型	小票或运单	字符		2	是	
客户编码	开发票客户对象	数字		100	是	
客户名称		文本		100	是	
已开金额
运单和小票单号对应交易明细的已开发票金额	数字		20	是	


				
业务时间		数字		20	是	
申请人	申请开发票的人员	文本		10	是	界面不显示
申请人编号		数字		10	是	界面不显示
1.10	非功能性需求
使用量	目前据统计，截止2012年7月全月发票的使用量为接近55000票/月。
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	标记在3秒内响应 
使用时间段	正常白天上班时间（8:00-17：30）
高峰使用时间段	特殊业务，无高峰使用时间
1.11	接口描述：
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.ActualFreightEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IInvoiceDao;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IOtherRevenueDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IInvoiceService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.InvoiceEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 开发发票服务实现.
 * 
 * @author ibm-guxinhua
 * @date 2012-11-19 下午4:10:38
 */
public class InvoiceService implements IInvoiceService {

	/** 日志. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

	/** 开发票Dao. */
	private IInvoiceDao invoiceDao;

	/** 小票DAO. */
	private IOtherRevenueDao otherRevenueDao;

	/** 运单管理服务. */
	private IWaybillManagerService waybillManagerService;

	/** 组织信息 Service实现. */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/** 人员 Service实现. */
	private IEmployeeService employeeService;

	/**
	 * 查询运单的可开票金额.
	 *
	 * @param waybillNoList the waybill no list
	 * @return the list
	 * @throws SettlementException the settlement exception
	 * @author ibm-guxinhua
	 * @date 2012-11-19 下午4:10:38
	 */
	@Override
	public List<InvoiceDto> queryWaybillAmount(List<String> waybillNoList) throws SettlementException{
		LOGGER.info("Service start,waybillNoList:"
				+ (null == waybillNoList ? null : waybillNoList.toString()));

		if (CollectionUtils.isEmpty(waybillNoList)) {
			throw new SettlementException("单号为空，无法查询单号信息");
		}

		List<InvoiceDto> invoiceDtoList = null;
		// 通过运单号码集合获取运单信息
		List<WaybillEntity> waybillEntityList = waybillManagerService
				.queryWaybillBasicByNoList(waybillNoList);

		if (!CollectionUtils.isEmpty(waybillEntityList)) {
			invoiceDtoList = new ArrayList<InvoiceDto>();
			InvoiceDto dto = null;
			// 把运单单号可开票金额封装到Dto中
			for (WaybillEntity entity : waybillEntityList) {
				dto = new InvoiceDto();
				dto.setWaybillNo(entity.getWaybillNo());
				dto.setTotalAmount(entity.getTotalFee().subtract(entity.getCodAmount()));
				dto.setProductCode(entity.getProductCode());
				//运单收货部门
				dto.setReceviceOrgCode(entity.getReceiveOrgCode());
				//发票标记
				ActualFreightEntity actualFreightEntity = waybillManagerService.queryActualFreightByNo(entity.getWaybillNo());
				if(null == actualFreightEntity.getInvoice()) {
					throw new SettlementException("获取发票标记为空！");
				}
				dto.setInvoiceMark(actualFreightEntity.getInvoice());
				invoiceDtoList.add(dto);
			}
		}
		
		LOGGER.info("Service end");
		return invoiceDtoList;
	}

	/**
	 * 查询小票开票金额.
	 *
	 * @param otherRevenueNoList the other revenue no list
	 * @return the list
	 * @throws SettlementException the settlement exception
	 * @author ibm-guxinhua
	 * @date 2012-11-19 下午4:10:38
	 */
	@Override
	public List<InvoiceDto> queryOtherRevenueAmount(
			List<String> otherRevenueNoList)throws SettlementException {
		LOGGER.info("Service start,otherRevenueNoList:"
				+ (null == otherRevenueNoList ? null : otherRevenueNoList
						.toString()));
		if (CollectionUtils.isEmpty(otherRevenueNoList)) {
			throw new SettlementException("单号为空，无法查询单号信息");
		}

		List<InvoiceDto> invoiceDtoList = null;
		// 按小票号查询小票记录
		List<OtherRevenueEntity> otherRevenueEntitieList = otherRevenueDao
				.queryOtherRevenueByOtherRevenueNos(otherRevenueNoList,
						FossConstants.ACTIVE);
		if (!CollectionUtils.isEmpty(otherRevenueEntitieList)) {
			invoiceDtoList = new ArrayList<InvoiceDto>();
			InvoiceDto dto = null;
			// 把小票单号开票金额封装到Dto中
			for (OtherRevenueEntity entity : otherRevenueEntitieList) {
				dto = new InvoiceDto();
				dto.setOtherRevenueNo(entity.getOtherRevenueNo());
				dto.setTotalAmount(entity.getAmount());
				dto.setProductCode(entity.getProductCode());
				// 发票标记 和部门
				dto.setReceviceOrgCode(entity.getGeneratingOrgCode());
				dto.setInvoiceMark(entity.getInvoiceMark());
				invoiceDtoList.add(dto);
			}
		} 
		LOGGER.info("Service end");
		return invoiceDtoList;
	}


	/**
	 * 添加新发票记录
	 *
	 * @param dto the dto 
	 * @param returnDto the return dto 前置条件返回Dto
	 * @author ibm-guxinhua
	 * @return the int
	 */
	private int addInvoiceEntity(InvoiceDto dto){
		int result = 0;
		
		// 添加新发票记录
		BigDecimal totalAmount = null;
		String customerCode = null;
		String customerName = null;
		
		// 来源单据类型
		String sourceBillType = dto.getSourceBillType();
		if (StringUtils
				.equals(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL,
						sourceBillType)) {
			// 获得运单金额等信息
			WaybillEntity entity = (WaybillEntity) dto.getEntity();
			// 总费用
			totalAmount = entity.getTotalFee();
			// 发货客户编码
			customerCode = entity.getDeliveryCustomerCode();
			// 发货客户名称
			customerName = entity.getDeliveryCustomerName();
		} else {
			// 获得小票金额等信息
			OtherRevenueEntity entity = (OtherRevenueEntity) dto
					.getEntity();
			// 金额
			totalAmount = entity.getAmount();
			// 客户编码
			customerCode = entity.getCustomerCode();
			// 客户名称
			customerName = entity.getCustomerName();
		}
		InvoiceEntity haveEntity = new InvoiceEntity();
		haveEntity.setId(UUIDUtils.getUUID());
		haveEntity.setSourceBillNo(dto.getSourceBillNo());
		haveEntity.setSourceBillType(dto.getSourceBillType());
		// 发票给客户
		haveEntity
				.setInvoiceType(SettlementDictionaryConstants.INVOICE__INVOICE_TYPE__CUSTOMER);
		haveEntity.setCustomerCode(customerCode);
		haveEntity.setCustomerName(customerName);
		haveEntity.setOrgCode(dto.getOrgCode());
		haveEntity.setOrgName(dto.getOrgName());
		haveEntity.setTotalAmount(totalAmount);
		haveEntity.setAlreadyOpenAmount(dto.getAlreadyOpenAmount());
		haveEntity.setBusinessDate(new Date());
		haveEntity.setApplyUserCode(dto.getApplyUserCode());
		haveEntity.setApplyUserName(dto.getApplyUserName());
		haveEntity.setActive(FossConstants.ACTIVE);
		haveEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		haveEntity.setVersionNo(FossConstants.INIT_VERSION_NO);
		result = invoiceDao.addInvoice(haveEntity);
					
		return result;
	}
	
	/**
	 * 标记发票已开，返回InvoiceEntity标记开票成功，否则开票失败.
	 *
	 * @param dto the dto(sourceBillNo,alreadyOpenAmount,sourceBillType,applyUserCode,billingDeptCode)
	 * @return the invoice entity
	 * @throws SettlementException the settlement exception
	 * @author ibm-guxinhua
	 * @date 2012-11-19 下午4:10:38
	 */
	@Transactional
	@Override
	public InvoiceEntity markInvoice(InvoiceDto dto) throws SettlementException {
		LOGGER.info("Service start,dto.getSourceBillNo():"
				+ (null == dto ? null : dto.getSourceBillNo()));
		if (null == dto) {
			throw new SettlementException("内部错误，参数为空");
		}

		// 前置条件判断
		this.markInvoicePrecondition(dto);

		// 来源单据单号
		String sourceBillNo = dto.getSourceBillNo();
		// 已开金额
		BigDecimal alreadyOpenAmount = dto.getAlreadyOpenAmount();
		// 执行sql成功状态
		int result = 0;
		// 获取运单或者小票的的已开金额，Foss异步调用将发票管理的已用金额，同步至Foss系统，并记录下日志
		
		/* BUG-56554 092036-bochenlong
		 * 
		 * 对始发和到达部门进行区分  
		 */
		// 开票部门
		String code = dto.getOrgCode();
		// 根据来源单号和部门查询，便于区分始发和到达
		InvoiceEntity haveEntity = queryInvoiceState(sourceBillNo ,code); // 判断是否存在开票记录
		if (haveEntity != null) {
			// 已存在则更新发票记录
			haveEntity.setAlreadyOpenAmount(alreadyOpenAmount);
			haveEntity.setApplyUserCode(dto.getApplyUserCode());
			haveEntity.setApplyUserName(dto.getApplyUserName());
			haveEntity.setOrgCode(dto.getOrgCode());
			haveEntity.setOrgName(dto.getOrgName());
			result = invoiceDao.updateInvoice(haveEntity);
		} else {
			// 添加新发票记录
			result = this.addInvoiceEntity(dto);
		}

		LOGGER.info("Service end");
		// 判断是否标记成功
		if (result == 1) {
			return haveEntity;
		} else if (result > 1) {
			throw new SettlementException("标记发票已开失败，标记发票已开行数不唯一!");
		} else {
			throw new SettlementException("内部错误，标记失败");
		}
	}

	
	/**
	 * 查询开票状态-如果存在，返回开发票记录实体，否则返回NULL.
	 *
	 * @param sourceBillNo the source bill no
	 * @return the invoice entity
	 * @throws SettlementException the settlement exception
	 * @author ibm-guxinhua
	 * @date 2012-11-22 下午5:12:56
	 */
	@Override
	public InvoiceEntity queryInvoiceState(String sourceBillNo ,String code) throws SettlementException {
		LOGGER.info("Service start,sourceBillNo:" + (null == sourceBillNo ? null : sourceBillNo));
		if (StringUtils.isBlank(sourceBillNo)) {
			throw new SettlementException("单号为空，无法查询单号信息");
		}
		
		if (StringUtils.isBlank(code)) {
			throw new SettlementException("部门为空，无法查询发票信息");
		}
		InvoiceEntity entity = new InvoiceEntity();
		entity.setSourceBillNo(sourceBillNo);
		entity.setOrgCode(code);
		entity.setActive(FossConstants.ACTIVE);
		InvoiceEntity haveEntity = invoiceDao.existsInvoice(entity);

		LOGGER.info("Service end");
		return haveEntity;
	}

	/**
	 * 标记发票已开前提条件，条件都满足，则返回对应的运单或小票.
	 *
	 * @param dto the dto(sourceBillNo,alreadyOpenAmount,sourceBillType,applyUserCode,billingDeptCode)
	 * 方法执行后 the invoice dto(sourceBillNo,alreadyOpenAmount,sourceBillType,billingDeptCode,
	 * 			ApplyUserCode,ApplyUserName,OrgCode,OrgName,Entity(运单、小票))
	 * @throws SettlementException the settlement exception
	 * @author ibm-guxinhua
	 * @date 2012-11-26 下午3:58:04
	 */
	private void markInvoicePrecondition(InvoiceDto dto) throws SettlementException {

		if (dto == null) {
			throw new SettlementException("内部错误，发票记录对象为空！");
		}

		String sourceBillNo = null; // 来源单据单号
		String sourceBillType = null; // 来源单据类型
		BigDecimal alreadyOpenAmount = null; // 金额
		sourceBillType = dto.getSourceBillType();
		if (StringUtils.isBlank(sourceBillType)) {
			throw new SettlementException("来源单据类型为空");
		} else {
			// 判断来源单号必须是运单或小票类型
			if (!(StringUtils
					.equals(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL,
							sourceBillType))
					&& !(StringUtils
							.equals(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__OTHER_REVENUE,
									sourceBillType))) {
				throw new SettlementException("来源单据类型错误");
			}
		}

		// 操作人为非发票管理员时
		String userCode = dto.getApplyUserCode();
		EmployeeEntity employeeEntity = null;
		if (StringUtils.isBlank(userCode)) {
			throw new SettlementException("操作人编码不能为空");
		} else {
			employeeEntity = employeeService.queryEmployeeByEmpCode(userCode);
			if (null == employeeEntity) {
				throw new SettlementException("操作人信息为无效");
			} else {
				// 设置 操作人信息
				dto.setApplyUserCode(employeeEntity.getEmpCode());
				dto.setApplyUserName(employeeEntity.getEmpName());
			}
		}

		// 调用部门查询接口校验部门信息为无效
		String billingDeptCd = dto.getBillingDeptCode(); // 开票人部门标杆编码
		if (StringUtils.isBlank(billingDeptCd)) {
			// 传入部门标杆编码为空，用操作人的标杆编码。
			LOGGER.debug("传入部门标杆编码为空，用操作人的标杆编码。");
			billingDeptCd = employeeEntity.getUnifieldCode();
			
		}
		// 查询部门信息
		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByUnifiedCode(billingDeptCd);
		if (null == orgEntity) {
			throw new SettlementException("开票人部门信息为无效");
		} else {
			// 设置 部门信息
			dto.setOrgCode(orgEntity.getCode());
			dto.setOrgName(orgEntity.getName());
		}

		// 运单单号为空时
		sourceBillNo = dto.getSourceBillNo();
		if (StringUtils.isBlank(sourceBillNo)) {
			if (StringUtils
					.equals(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL,
							sourceBillType)) {
				throw new SettlementException("运单单号为空，请提供有效单号");
			} else {
				throw new SettlementException("小票单号为空，请提供有效单号");
			}
		}
		// 发票申请状态为“未审批”、“审批中”、“已退回”状态

		// 金额小于0时不能做发票的作废和更改
		
		alreadyOpenAmount = dto.getAlreadyOpenAmount();
		if (null == alreadyOpenAmount
				|| alreadyOpenAmount.compareTo(BigDecimal.ZERO) <  0) {
			throw new SettlementException("金额小于等于0");
		}

		// 根据运单单号、小票号查询是否为存在；作废的小票不允许开票
		if (StringUtils
				.equals(SettlementDictionaryConstants.INVOICE__SOURCE_BILL_TYPE__WAYBILL,
						sourceBillType)) {
			WaybillEntity waybillEntity = waybillManagerService
					.queryWaybillBasicByNo(sourceBillNo);
			if (waybillEntity == null) {// 判断是否存在运单
				throw new SettlementException("根据运单单号查询，运单不存在");
			} else {
				dto.setEntity(waybillEntity);
			}
		} else {
			OtherRevenueEntity otherRevenueEntity = null;
			List<OtherRevenueEntity> otherRevenueList = otherRevenueDao
					.queryOtherRevenueByOtherRevenueNos(
							Arrays.asList(sourceBillNo), FossConstants.ACTIVE);
			if (CollectionUtils.isEmpty(otherRevenueList)) {// 判断是否存在小票，是否作废
				throw new SettlementException("根据小票单号查询，小票不存在");
			} else {
				if (otherRevenueList.size() != 1) {
					throw new SettlementException("根据小票单号查询，一个单号对应多个小票");
				} else {
					otherRevenueEntity = (OtherRevenueEntity) otherRevenueList.get(0);
					if (!StringUtils.equals(FossConstants.NO,
							otherRevenueEntity.getIsDisable())) {
						throw new SettlementException("根据小票单号查询，作废的小票不允许开票");
					} else {
						dto.setEntity(otherRevenueEntity);
					}
				}
			}

		}

	}

	/**
	 * Sets the invoice dao.
	 * 
	 * @param invoiceDao
	 *            the new invoice dao
	 */
	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	/**
	 * Sets the other revenue dao.
	 * 
	 * @param otherRevenueDao
	 *            the new other revenue dao
	 */
	public void setOtherRevenueDao(IOtherRevenueDao otherRevenueDao) {
		this.otherRevenueDao = otherRevenueDao;
	}

	/**
	 * Sets the waybill manager service.
	 * 
	 * @param waybillManagerService
	 *            the new waybill manager service
	 */
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * Sets the org administrative info service.
	 * 
	 * @param orgAdministrativeInfoService
	 *            the new org administrative info service
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * Sets the employee service.
	 * 
	 * @param employeeService
	 *            the new employee service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

}
