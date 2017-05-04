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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/CustomerCreditRestoreService.java
 * 
 * FILE NAME        	: CustomerCreditRestoreService.java
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
 * 修订记录
 日期 	修订内容 	修订人员 	版本号 
 2012-05-02 	创建版本	李琴	V0.1
 2012-07-09	编辑版本 根据调研情况，添加用例描叙前置后置条件，业务规则、操作步骤、扩展事件、数据元素、非功能性需求	李琴	V0.1
 2012-07-14	编辑版本 完成增加或者还原客户可用额度	李琴	V0.5
 2012-07-23	编辑版本 修改用例描叙	李琴	V0.9
 2012-10-26	SR1 业务规则，将"数据字段"改为从基础资料的"数据字典"	保轶	V1.2
 2012-11-21	增加业务规则，临欠的应收单还款时实时还原已用额度	黄小波	V1.3
 2013-1-21	增加业务规则，定时将客户的已用额度、最大欠款天数同步给CRM系统	黄小波	V1.4
 2013-1-21	增加业务规则描述，实现准实时还原已用额度信息	黄小波	V1.5
 2013-2-18	增加每月初统计部门上月收入规则描述；
 增加获取部门最大临欠额度信息描述。	武江涛	V1.6
 2013-2-28	删除对客户超期欠款判断的业务规则	黄小波	V1.7

 1.	SUC-74查询_管理客户应收账款
 1.1	相关业务用例
 BUC_FOSS_4.7.10.20_010出发运费应收单
 BUC_FOSS_4.7.50.40_040预收冲应收-反核销
 BUC_FOSS_4.7.50.40_030 （预收冲应收）
 1.2	用例描述

 1.	在对生成、反核销、红冲、核销付款方式为临欠的应收单时，通过准实时调用此接口，60秒以内扣减或者还原部门可用额度。
 2.	红冲、反核销付款方式为月结的应收单，通过准实时的方式还原客户已用额度；
 3.	接送货开单时，如果是月结或者临欠客户需要先校验是否满足月结和临欠的资格，校验通过扣减客户可用额度，校验不通过返回失败原因。
 1.3	用例条件
 条件类型	描述	引用系统用例
 前置条件	1.	准备生成应收单单，判断客户的可用额度、是否存在超期欠款、是否可以欠款；
 2.	进行反核销、生成应收，扣减增加客户可用额度
 3.	进行核销、红冲应收，增加客户可用额度	1.	SUC-85-修改_查询行政组织业务属性
 2.	SUC-105-查询客户

 后置条件	1、	准备生成应收单，判断客户的可用额度、是否存在超期欠款、是否可以欠款，返回处理结果；
 2、	进行反核销、生成应收，扣减增加客户可用额度
 3、	进行核销、红冲应收，增加客户可用额度	
 1.4	操作用户角色
 操作用户	描述
 无	无
 1.5	界面要求
 无
 1.5.1	表现方式
 接口
 1.5.2	界面原型
 无
 1.5.3	界面描述
 无
 1.6	操作步骤
 1.6.1	开单时传入参数校验
 序号	基本步骤	相关数据	补充步骤
 1.		获取客户编码	参数校验	1.	根据传入的客户编码查询是否真实，是否存在参见业务扩展步骤1a
 2.		获取客户的支付方式（只能是月结和临欠），月结获取合同客户的可欠款天数，临欠获取临欠客户最大欠款天数	参数校验	1.	如果是月结客户，根据客户编号，从综合数据字典基础数据表中取得月结（月结、半月结、双月结）合同里的欠款天数。
 2.	临欠客户（非月结默认为临欠）的最大欠款天数。
 3.		如果是月结客户，判断月结资格	参数校验	1.	合同号是否真实有效，根据客户编号查找对应的合同编号是否存在有效 2a
 2.	判断是否合同失效，取得客户编号对应的合同号的生效日期、失效日期，判断合同号当前日期是否在有效期内，如果不在有效期内，参见扩展步骤 3a
 3.		如果是临欠客户，判断部门的临欠资格	参数校验	1.	根据部门编号，调用综合管理接口查询部门最大欠款额度，查询结算内部记录的已用额度，如果部门已用额度大于部门最大可临欠额度。参见扩展步骤6a
 是否存在超期欠款	参数校验	1.	临欠用户是否超期，根据客户编号查找对应最早运单编号的时间，如果最早交易业务时间距离当前时间，大于临欠可欠款天数，参见扩展步骤5a
 2.		月结和临欠客户是否能够继续欠款	参数校验	1.	如果是月结客户，根据客户编号，获取客户的信用额度，和已欠款额度。如果用户的已欠款金额大于客户信用额度，参见扩展步骤6a
 2.	如果是临欠客户，根据部门编号和客户编码，获取部门的信用额度，和已欠款额度。如果用户的已欠款金额大于部门信用额度，参见扩展步骤7a

 1.6.2	扣减或者增加客户可用额度
 序号	基本步骤	相关数据	补充步骤
 1.		获取应收单操作方式	获取参数	判断用户操作，红冲、反核销、开单、核销
 2.		获取传入金额	获取参数	获取传入金额
 3.		获取支付方式	获取参数	临欠和月结
 4.		如果是红冲和核销	参数校验	1.	如果客户支付方式是月结参见SR2
 2.	如果客户支付方式是临欠参见SR3
 5.		如果是生成和反核销	参数校验	3.	如果客户支付方式是月结参见SR4
 4.	如果客户支付方式是临欠参见SR5
 6.	返回处理状态	处理返回	返回处理成功或者失败

 1.6.3	将已用额度、最大欠款日期同步给CRM
 序号	基本步骤	相关数据	补充步骤
 1、	将结算系统欠款方式为月结的应收对应的已用额度信息同步、最前欠款日期同步CRM	已用额度信息	

 1.6.4	每月初计算部门上月收入，存入到“部门月收入表”中
 序号	基本步骤	相关数据	补充步骤
 1、	1、每月一号定时统计部门上月的收入：按照记账日期进行统计，按现金收款单和应收单的收入部门进行分组统计，统计结果存入“部门月收入表”	1、现金收款单；
 2、应收单；	


 序号	扩展事件	相关数据	备注
 1a		如果传入的客户编号不存在	异常处理	提示，“编号为“12345678”客户编码错误，不允许操作。操作中止
 2a		如果客户编号，对应合同编号不存在	异常处理	提示,“客户对应的合同编号不存在，不允许月结” 
 3a		月结客户，当前日期欠款日期大于生效日期和失效日期	异常处理	提示：“客户编码为123456,合同以已失效，请续签或者选择其它方式付款” 
 4a		月结客户，当前日期，小于生效日期，和失效日期。计算生效日期和当前日期相距天数	异常处理	提示“客户编码为12345678”合同需要在（计算出来的天数）天后生效。
 5a		月结客户，最大欠款天数已超期	异常处理	提示“客户编号为：“123456789”已存在欠款超期，请速还款或者选择其它付款方式”
 6a		客户编码对应的信用额度，小于已用额度	异常处理	提示“客户编码为“123456”信用额度已透支，请速还款或选择其它支付方式！”
 7a		如果是临欠，查找当前登录营业部，最大欠款金额，和已用金额。	异常处理	提示“部门已用额度金额已透支，不能选择临欠”
 8a		如果存在临欠已超期	异常处理	提示“客户编号，用户存在临欠超期“

 1.7	业务规则
 序号	描述
 SR1		1.	从数据字典中获取月结和临欠最大天数。如果为空返回0
 SR2		5.	月结客户查询客户信用额度和已用额度 
 6.	可用额度=信用额度-(已用额度+金额)
 7.	同步行政组织业务属性信息表已用金额
 SR3		8.	临欠客户查询部门最大欠款额度和已经额度
 9.	部门最大欠款额度=（已用额度+金额）
 10.	同步行政组织业务属性信息表已用金额
 SR4		11.	月结客户查询客户信用额度和已用额度，已用额度必须小于客户信用额度。
 12.	可用额度=信用额度-已用额度-金额必须大于0
 13.	同步行政组织业务属性信息表已用金额
 SR5		14.	临欠客户查询部门最大欠款额度和已经额度，已用额度必须小于部门最大欠款额度
 15.	临欠客户信用 可用额度=信用额度-已用额度-金额必须>0
 16.	同步行政组织业务属性信息表已用金额

 1.8	数据元素
 1.8.1	输入客户信息
 字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
 客户编码	调用接口参数传入	系统传入	无	20	是	
 部门编码	调用接口参数传入	系统传入	无	20	是	
 支付方式	调用接口参数传入	系统传入	无	1	是	临欠、月结
 金额	调用接口参数传入	系统传入	无	10,2	是	
 应收单操作方式	红冲、反核销、生成、核销	系统传入	无	10	是	操作方式，应收单是开单生成、或者红冲、或者核销、反核销

 1.8.2	输出客户信息
 字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
 客户编码	系统传出	无	无	44	是	
 业务日期	业务日期	无	无	10	是	
 可用额度	系统传出	无	无	10,2	否	客户的可用额度
 已用金额	系统传出	无	无	10，2	是	
 处理状态	扣减或者红冲状态	无	无	1	是	

 1.8.3	部门月收入表
 字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
 ID	主键	无	无	30	是	
 部门编码				50	是	
 收入金额				18	是	
 创建时间					是	


 1.9	非功能性需求
 使用量	100000笔/天，每年保持60%的增长率
 2012年全网估计用户数	30000
 响应要求（如果与全系统要求 不一致的话）	属于高并发要求，要求1s完成响应
 使用时间段	00：00-24：00
 高峰使用时间段	14：00-18：00



 1.10	接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 查询部门的最大临欠额度
 综合管理
 传入部门编码，查询部门的最大临欠额度。


 修订记录
 日期 	修订内容 	修订人员 	版本号 
 2012-05-02 	创建版本	李琴	V0.1
 2012-07-09	编辑版本 根据调研情况，添加用例描叙前置后置条件，业务规则、操作步骤、扩展事件、数据元素、非功能性需求	李琴	V0.1
 2012-07-14	编辑版本 完成增加或者还原客户可用额度	李琴	V0.5
 2012-07-23	编辑版本 修改用例描叙	李琴	V0.9
 2012-10-26	SR1 业务规则，将"数据字段"改为从基础资料的"数据字典"	保轶	V1.2
 2012-11-21	增加业务规则，临欠的应收单还款时实时还原已用额度	黄小波	V1.3
 2013-1-21	增加业务规则，定时将客户的已用额度、最大欠款天数同步给CRM系统	黄小波	V1.4
 2013-1-21	增加业务规则描述，实现准实时还原已用额度信息	黄小波	V1.5
 2013-2-18	增加每月初统计部门上月收入规则描述；
 增加获取部门最大临欠额度信息描述。	武江涛	V1.6
 2013-2-28	删除对客户超期欠款判断的业务规则	黄小波	V1.7

 1.	SUC-74查询_管理客户应收账款
 1.1	相关业务用例
 BUC_FOSS_4.7.10.20_010出发运费应收单
 BUC_FOSS_4.7.50.40_040预收冲应收-反核销
 BUC_FOSS_4.7.50.40_030 （预收冲应收）
 1.2	用例描述

 1.	在对生成、反核销、红冲、核销付款方式为临欠的应收单时，通过准实时调用此接口，60秒以内扣减或者还原部门可用额度。
 2.	红冲、反核销付款方式为月结的应收单，通过准实时的方式还原客户已用额度；
 3.	接送货开单时，如果是月结或者临欠客户需要先校验是否满足月结和临欠的资格，校验通过扣减客户可用额度，校验不通过返回失败原因。
 1.3	用例条件
 条件类型	描述	引用系统用例
 前置条件	1.	准备生成应收单单，判断客户的可用额度、是否存在超期欠款、是否可以欠款；
 2.	进行反核销、生成应收，扣减增加客户可用额度
 3.	进行核销、红冲应收，增加客户可用额度	1.	SUC-85-修改_查询行政组织业务属性
 2.	SUC-105-查询客户

 后置条件	1、	准备生成应收单，判断客户的可用额度、是否存在超期欠款、是否可以欠款，返回处理结果；
 2、	进行反核销、生成应收，扣减增加客户可用额度
 3、	进行核销、红冲应收，增加客户可用额度	
 1.4	操作用户角色
 操作用户	描述
 无	无
 1.5	界面要求
 无
 1.5.1	表现方式
 接口
 1.5.2	界面原型
 无
 1.5.3	界面描述
 无
 1.6	操作步骤
 1.6.1	开单时传入参数校验
 序号	基本步骤	相关数据	补充步骤
 1.		获取客户编码	参数校验	1.	根据传入的客户编码查询是否真实，是否存在参见业务扩展步骤1a
 2.		获取客户的支付方式（只能是月结和临欠），月结获取合同客户的可欠款天数，临欠获取临欠客户最大欠款天数	参数校验	1.	如果是月结客户，根据客户编号，从综合数据字典基础数据表中取得月结（月结、半月结、双月结）合同里的欠款天数。
 2.	临欠客户（非月结默认为临欠）的最大欠款天数。
 3.		如果是月结客户，判断月结资格	参数校验	1.	合同号是否真实有效，根据客户编号查找对应的合同编号是否存在有效 2a
 2.	判断是否合同失效，取得客户编号对应的合同号的生效日期、失效日期，判断合同号当前日期是否在有效期内，如果不在有效期内，参见扩展步骤 3a
 3.		如果是临欠客户，判断部门的临欠资格	参数校验	1.	根据部门编号，调用综合管理接口查询部门最大欠款额度，查询结算内部记录的已用额度，如果部门已用额度大于部门最大可临欠额度。参见扩展步骤6a
 是否存在超期欠款	参数校验	1.	临欠用户是否超期，根据客户编号查找对应最早运单编号的时间，如果最早交易业务时间距离当前时间，大于临欠可欠款天数，参见扩展步骤5a
 2.		月结和临欠客户是否能够继续欠款	参数校验	1.	如果是月结客户，根据客户编号，获取客户的信用额度，和已欠款额度。如果用户的已欠款金额大于客户信用额度，参见扩展步骤6a
 2.	如果是临欠客户，根据部门编号和客户编码，获取部门的信用额度，和已欠款额度。如果用户的已欠款金额大于部门信用额度，参见扩展步骤7a

 1.6.2	扣减或者增加客户可用额度
 序号	基本步骤	相关数据	补充步骤
 1.		获取应收单操作方式	获取参数	判断用户操作，红冲、反核销、开单、核销
 2.		获取传入金额	获取参数	获取传入金额
 3.		获取支付方式	获取参数	临欠和月结
 4.		如果是红冲和核销	参数校验	1.	如果客户支付方式是月结参见SR2
 2.	如果客户支付方式是临欠参见SR3
 5.		如果是生成和反核销	参数校验	3.	如果客户支付方式是月结参见SR4
 4.	如果客户支付方式是临欠参见SR5
 6.	返回处理状态	处理返回	返回处理成功或者失败

 1.6.3	将已用额度、最大欠款日期同步给CRM
 序号	基本步骤	相关数据	补充步骤
 1、	将结算系统欠款方式为月结的应收对应的已用额度信息同步、最前欠款日期同步CRM	已用额度信息	

 1.6.4	每月初计算部门上月收入，存入到“部门月收入表”中
 序号	基本步骤	相关数据	补充步骤
 1、	1、每月一号定时统计部门上月的收入：按照记账日期进行统计，按现金收款单和应收单的收入部门进行分组统计，统计结果存入“部门月收入表”	1、现金收款单；
 2、应收单；	


 序号	扩展事件	相关数据	备注
 1a		如果传入的客户编号不存在	异常处理	提示，“编号为“12345678”客户编码错误，不允许操作。操作中止
 2a		如果客户编号，对应合同编号不存在	异常处理	提示,“客户对应的合同编号不存在，不允许月结” 
 3a		月结客户，当前日期欠款日期大于生效日期和失效日期	异常处理	提示：“客户编码为123456,合同以已失效，请续签或者选择其它方式付款” 
 4a		月结客户，当前日期，小于生效日期，和失效日期。计算生效日期和当前日期相距天数	异常处理	提示“客户编码为12345678”合同需要在（计算出来的天数）天后生效。
 5a		月结客户，最大欠款天数已超期	异常处理	提示“客户编号为：“123456789”已存在欠款超期，请速还款或者选择其它付款方式”
 6a		客户编码对应的信用额度，小于已用额度	异常处理	提示“客户编码为“123456”信用额度已透支，请速还款或选择其它支付方式！”
 7a		如果是临欠，查找当前登录营业部，最大欠款金额，和已用金额。	异常处理	提示“部门已用额度金额已透支，不能选择临欠”
 8a		如果存在临欠已超期	异常处理	提示“客户编号，用户存在临欠超期“

 1.7	业务规则
 序号	描述
 SR1		1.	从数据字典中获取月结和临欠最大天数。如果为空返回0
 SR2		5.	月结客户查询客户信用额度和已用额度 
 6.	可用额度=信用额度-(已用额度+金额)
 7.	同步行政组织业务属性信息表已用金额
 SR3		8.	临欠客户查询部门最大欠款额度和已经额度
 9.	部门最大欠款额度=（已用额度+金额）
 10.	同步行政组织业务属性信息表已用金额
 SR4		11.	月结客户查询客户信用额度和已用额度，已用额度必须小于客户信用额度。
 12.	可用额度=信用额度-已用额度-金额必须大于0
 13.	同步行政组织业务属性信息表已用金额
 SR5		14.	临欠客户查询部门最大欠款额度和已经额度，已用额度必须小于部门最大欠款额度
 15.	临欠客户信用 可用额度=信用额度-已用额度-金额必须>0
 16.	同步行政组织业务属性信息表已用金额

 1.8	数据元素
 1.8.1	输入客户信息
 字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
 客户编码	调用接口参数传入	系统传入	无	20	是	
 部门编码	调用接口参数传入	系统传入	无	20	是	
 支付方式	调用接口参数传入	系统传入	无	1	是	临欠、月结
 金额	调用接口参数传入	系统传入	无	10,2	是	
 应收单操作方式	红冲、反核销、生成、核销	系统传入	无	10	是	操作方式，应收单是开单生成、或者红冲、或者核销、反核销

 1.8.2	输出客户信息
 字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
 客户编码	系统传出	无	无	44	是	
 业务日期	业务日期	无	无	10	是	
 可用额度	系统传出	无	无	10,2	否	客户的可用额度
 已用金额	系统传出	无	无	10，2	是	
 处理状态	扣减或者红冲状态	无	无	1	是	

 1.8.3	部门月收入表
 字段名称 	说明 (来源)	输入限制	提示信息	长度	是否必填	备注
 ID	主键	无	无	30	是	
 部门编码				50	是	
 收入金额				18	是	
 创建时间					是	


 1.9	非功能性需求
 使用量	100000笔/天，每年保持60%的增长率
 2012年全网估计用户数	30000
 响应要求（如果与全系统要求 不一致的话）	属于高并发要求，要求1s完成响应
 使用时间段	00：00-24：00
 高峰使用时间段	14：00-18：00



 1.10	接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 查询部门的最大临欠额度
 综合管理
 传入部门编码，查询部门的最大临欠额度。

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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditCustomerService;
import com.deppon.foss.module.settlement.common.api.server.service.ICreditOrgService;
import com.deppon.foss.module.settlement.common.api.server.service.IJOBTimestampService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditCustomerEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.CreditOrgEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CreditOrgDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICustomerCreditQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRestoreService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerCreditGridDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户信用额度还原
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-23 上午11:17:41
 */
public class CustomerCreditRestoreService implements ICustomerCreditRestoreService {

	/**
	 * 客户信用额度还原 Logger
	 */
	private static final Logger logger = LogManager.getLogger(CustomerCreditRestoreService.class);

	/**
	 * 还原客户信用额度编码
	 */
	private static final String JOB_CODE = "CUSTOMER_CREDIT";

	/**
	 * 查询配置参数表
	 */
	private IConfigurationParamsService configurationParamsService;

	/**
	 * 每次处理的条数
	 */
	private static final int PAGE_ROWS = 1000;

	/**
	 * 信用额度管理相关查询dao
	 */
	private ICustomerCreditQueryDao customerCreditQueryDao;

	/**
	 * 客户信用额度服务
	 */
	private ICreditCustomerService creditCustomerService;

	/**
	 * 临欠额度服务
	 */
	private ICreditOrgService creditOrgService;

	/**
	 * 定时任务时间戳服务
	 */
	private IJOBTimestampService jobTimestampService;

	/**
	 * 
	 * 查询上次JOB执行时间
	 * 
	 * @Title: getLastExecuteTime
	 * @author 046644-foss-zengbinwen
	 * @date 2013-4-20 上午9:18:22
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public Date getLastExecuteTime(Date current) {

		Calendar cal = Calendar.getInstance();

		// JOB上次执行时间
		Date lastDate = jobTimestampService.getJOBTimestamp(JOB_CODE);

		// 如果上次执行时间为空，则新增一条;
		// 如果上次执行时间不为空，则更新
		if (lastDate == null) {
			jobTimestampService.addJOBTimestamp(JOB_CODE, current, "客户信用额度还原时间戳");

			// 如果时间为空，默认上次执行为一个月前
			cal.add(Calendar.DATE, -31);
			lastDate = cal.getTime();
		} else {
			jobTimestampService.updateJOBTimestamp(JOB_CODE, current);
		}

		return lastDate;
	}

	/**
	 * 
	 * 同步客户信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午11:18:04
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRestoreService#syncCustomer()
	 */
	@Override
	public void syncCustomer(Date lastDate, Date current) {

		logger.info("开始同步客户!");

		// 当前页
		int currentPage = 0;

		// 读取综合管理的客户信息
		List<CustomerCreditGridDto> customerList = this.customerCreditQueryDao
				.queryCustomerFromBse(lastDate, current, (currentPage++) * PAGE_ROWS, PAGE_ROWS);

		while (CollectionUtils.isNotEmpty(customerList)) {

			// 依次遍历，读取客户信息，对客户的信息进行初始化
			for (CustomerCreditGridDto item : customerList) {

				// 根据编码获取Customer兑现
				CreditCustomerEntity customerEntity = this.creditCustomerService
						.queryByCustomerCode(item.getCode());

				// 根据编码判断客户是否存在历史记录,不存在
				if (customerEntity == null) {
					// 如果不存在则进行加入
					CreditCustomerEntity newCustomer = new CreditCustomerEntity();
					// 设置相应的参数
					newCustomer.setId(item.getId()); // 使用相同的Id
					// 使用有效
					newCustomer.setActive(FossConstants.ACTIVE);

					// 已经用额度
					newCustomer.setUsedAmount(BigDecimal.ZERO);
					// 客户名称
					newCustomer.setCustomerCode(item.getCode());
					newCustomer.setCustomerName(item.getName());

					// 写入服务器
					this.creditCustomerService.addCreditCustomer(newCustomer);
				}

			}

			// 如果还有数据，则继续查询
			if (customerList != null && customerList.size() == PAGE_ROWS) {
				customerList = this.customerCreditQueryDao.queryCustomerFromBse(lastDate, current,
						(currentPage++) * PAGE_ROWS, PAGE_ROWS);
			} else {
				customerList = null;
			}
		}

		logger.info("同步客户结束!");

	}

	/**
	 * 同步组织信息
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午11:18:23
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRestoreService#syncOrgBusiness()
	 */
	@Override
	public void syncOrgBusiness() {

		logger.info("开始同步组织!");

		// 读取总组织数量行数
		int orgRows = this.customerCreditQueryDao.queryTotalOrgsFromBse();

		// 计算出总页数
		int pageSize = 0;
		if (orgRows % PAGE_ROWS == 0) {
			pageSize = orgRows / PAGE_ROWS;
		} else {
			pageSize = orgRows / PAGE_ROWS + 1;
		}

		// 通过分页的方式进行处理
		for (int i = 0; i < pageSize; i++) {

			// 读取综合管理的客户信息
			List<CustomerCreditGridDto> customerSet = this.customerCreditQueryDao.queryOrgFromBse(
					i * PAGE_ROWS , PAGE_ROWS);
			// 依次遍历，读取客户信息，对客户的信息进行初始化
			Iterator<CustomerCreditGridDto> iterator = customerSet.iterator();
			while (iterator.hasNext()) {
				// 获取组织信息
				CustomerCreditGridDto item = iterator.next();

				CreditOrgEntity orgEntity = this.creditOrgService.queryByOrgCode(item.getCode());
				// 根据编码判断组织是否存在，为空时表示不存在
				if (orgEntity == null) {
					// 如果不存在则进行加入
					CreditOrgEntity newOrgEntity = new CreditOrgEntity();
					// 设置相应的参数
					newOrgEntity.setId(item.getId()); // 使用相同的Id
					// 使用有效
					newOrgEntity.setActive(FossConstants.ACTIVE);

					// 已经用额度
					newOrgEntity.setUsedAmount(BigDecimal.ZERO);
					// 编码信息
					newOrgEntity.setOrgCode(item.getCode());
					newOrgEntity.setOrgName(item.getName());
					// 超期欠款标记
					newOrgEntity.setIsOverdue(FossConstants.NO);

					// 写入服务器
					this.creditOrgService.addCreditOrg(newOrgEntity);
				}
			}
		}

		logger.info("同步组织结束!");

	}

	/**
	 * 定时处理部门临时欠款是否超期
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-23 上午11:18:36
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICustomerCreditRestoreService#restoreCredit()
	 */
	@Override
	public void restoreCredit(Date lastDate, Date current) {

		// 更新月结的已用额度及超期欠款状态 updateCreditState() ;

		logger.info("开始 更新临欠已用额度及超期欠款状态");

		// 更新临欠已用额度及超期欠款状态
		updateDebtState(lastDate, current);

		logger.info("更新临欠已用额度及超期欠款状态结束");

	}

	/**
	 * 更新临欠信息 包括超期欠款的运单、已经红冲的应收金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午3:03:18
	 */
	private void updateDebtState(Date lastDate, Date sysDate) {

		// 结束日期 等于当前日期，不含时分秒
		///Date endDate = (Date) sysDate.clone();

		// 过期时间
		Date debtOverdueDate = null;

		// 调用综合管理接口，查询部門最大临欠天数
		String confValue = configurationParamsService
				.queryConfValueByCode(ConfigurationParamsConstants.STL_DEBT_LIMIT_DAY);

		// 校验参数
		if (StringUtils.isNotEmpty(confValue) && StringUtils.isNumeric(confValue)) {
			// 必须为整数
			debtOverdueDate = DateUtils.addDays(sysDate,
			// 當前日期減去配置最大临欠天数=最大欠款天数
					-Integer.parseInt(confValue));
			// 当临欠过期天数为空时,抛出异常进行处理!
		} else {
			throw new SettlementException("请在综合配置 临欠过期天数!");
		}

		// 读取所有组织条数
		int orgRows = this.creditOrgService.queryTotalRows();

		// 计算页数
		int pageSize = 0;
		if (orgRows % PAGE_ROWS == 0) {
			pageSize = orgRows / PAGE_ROWS;
		} else {
			pageSize = orgRows / PAGE_ROWS + 1;
		}

		// 读取组织信息
		for (int i = 0; i < pageSize; i++) {
			// 按页读取组织信息
			List<CreditOrgDto> orgDebitDtoSet = this.creditOrgService.queryByPage(i * PAGE_ROWS , PAGE_ROWS);

			// 依次迭代
			Iterator<CreditOrgDto> iterator = orgDebitDtoSet.iterator();
			while (iterator.hasNext()) {

				CreditOrgDto item = iterator.next();

				/* defect-669
				// 还原组织的还款信息(包括红冲、核销部分)
				// 临欠运单红冲的
				// 读取已经红冲且作废的应收单金额
				BigDecimal debtWriteBackAmount = this.customerCreditQueryDao
						.queryDebtWriteBackAmount(item.getOrgCode(), lastDate, endDate);

				// 红冲金额不等于0，则进行更新
				if (debtWriteBackAmount != null
						&& debtWriteBackAmount.compareTo(BigDecimal.ZERO) != 0) {
					// 更新已经使用的状态
					this.creditOrgService.updateUsedAmount(item.getOrgCode(), debtWriteBackAmount);
				}*/

				// 标记组织的超期欠款（包括应收部分）
				// 判断是否存在超期欠款
				BillReceivableEntity entity = this.customerCreditQueryDao
						.queryDebtOverdueReceivable(item.getOrgCode(), sysDate);

				// 超期欠款判断结果,默认为否
				String isOverdue = FossConstants.NO;
				String notes = null;
				int maxAccountDays = 0;
				if (entity != null) {

					// 如果超期单号不为空
					if (StringUtils.isNotEmpty(entity.getWaybillNo())
							&& entity.getBusinessDate() != null) {

						// 判断组织是否存在超期欠款
						if (entity.getBusinessDate().before(debtOverdueDate)) {
							isOverdue = FossConstants.YES;
							notes = entity.getWaybillId();
						}

						// 计算账期
						Date truncatedBusinessDate = DateUtils.truncate(entity.getBusinessDate(),
								Calendar.DATE);
						Date truncatedSysDate = DateUtils.truncate(sysDate, Calendar.DATE);
						maxAccountDays = com.deppon.foss.util.DateUtils.getTimeDiff(
								truncatedBusinessDate, truncatedSysDate).intValue();
					}
				}

				// 判断组织当前超前欠款状态是否一致，不一致则进行更新
				if (!isOverdue.equals(item.getIsOverdue())
						|| maxAccountDays != item.getMaxAccountDays()) {
					// 调用后台服务更新状态
					this.creditOrgService.updateOverdueState(item.getOrgCode(), isOverdue,
							maxAccountDays, notes);
				}

			}
		}
	}

	/**
	 * @param customerCreditQueryDao
	 */
	public void setCustomerCreditQueryDao(ICustomerCreditQueryDao customerCreditQueryDao) {
		this.customerCreditQueryDao = customerCreditQueryDao;
	}

	/**
	 * @param creditCustomerService
	 */
	public void setCreditCustomerService(ICreditCustomerService creditCustomerService) {
		this.creditCustomerService = creditCustomerService;
	}

	/**
	 * @param creditOrgService
	 */
	public void setCreditOrgService(ICreditOrgService creditOrgService) {
		this.creditOrgService = creditOrgService;
	}

	/**
	 * @param jobTimestampService
	 */
	public void setJobTimestampService(IJOBTimestampService jobTimestampService) {
		this.jobTimestampService = jobTimestampService;
	}

	/**
	 * @param configurationParamsService
	 */
	public void setConfigurationParamsService(IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

}
