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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/TakingService.java
 * 
 * FILE NAME        	: TakingService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 修订记录 
 日期 	修订内容 	修订人员 	版本号 
 2012-06-01 	创建版 	曾斌文	V0.1
 2012-06-07	根据小组系统用例评审，修改系统用例	曾斌文	V0.1
 2012-06-20	将实收货款、转临欠月结和签收分开	曾斌文	V0.1
 2012-07-25	升级到0.9版本	曾斌文	V0.9
 2012-12-11	签收时在财务签收记录表新增一条记录	曾斌文	V1.1

 1.	SUC-597-专线正常签收
 1.1	相关业务用例
 BUC_FOSS_4.7.20.10_130 正常签收
 1.2	用例描述
 客户在签收界面录入签收以后，标志这个业务已经完结，需要在应收单、现金收款单上增加一个签收日期，根据签收日期来确定公司的实际收入。
 用例条件
 条件类型	描述	引用系统用例
 前置条件	1、	在签收界面录入签收信息
 2、	运单已经转临欠月结或已经货款结清	1、	接送货系统：SUC-789-签收出库
 2、	接送货系统：SUC-485-签收出库（PDA和中转接口）
 3、	接送货系统：SUC-466-签收接口
 后置条件	1、	如果存在现金收款单，更新现金收款单的签收日期
 2、	如果存在始发应收单，更新始发应收单的签收日期
 3、	如果存在到付运费应收单，更新到付运费应收单的签收日期
 4、	生效整车尾款应付单
 5、	生效代收货款应付单
 6、	生效装卸费应付单（如果货款已结清）
 7、	在财务签收记录表中新增一条记录	SUC-137-生效整车运费应付单
 SUC-78-生效应付单（代收货款类）
 SUC-134-生效应付单（装卸费）
 1.3	操作用户角色
 操作用户	描述



 1.4	界面要求
 1.4.1	表现方式
 后台接口，无相关界面
 1.4.2	界面原型
 后台接口，无界面原型
 1.4.3	界面描述
 后台接口，无界面描述
 1.5	操作步骤
 1.5.1	参数校验
 序号	基本步骤	相关数据	补充步骤
 1	接口参数校验	参数信息	1、	参见业务规则SR1
 2、	参见扩展事件1a
 2	查询专线到付运费应收单	参数信息	1、	查询该单对应的专线到付运费应收单
 2、	查询规则参见SR2
 3、	扩展事件参见2a
 3	查询代收货款应收单	参数信息	1、	查询该单对应的代收货款应收单
 2、	查询规则参见SR3
 3、	扩展事件参见3a
 4	如果不是PDA签收，校验代收货款是否还款，到付运费是否还款或挂具体客户	参数信息	1、	校验代收货款是否还款，到付运费是否还款或挂具体客户
 2、	如果存在专线到付运费应收单，校验专线到付运费应收单必须完全还款或挂具体客户，参见业务规则SR4
 3、	如果存在代收货款应收单，校验代收货款应收单必须完全还款，参见业务规则SR5
 4、	扩展事件参见4a
 5	查询现金收款单	参数信息	1、	查询该单对应的现金收款单
 2、	查询规则参见SR6
 3、	扩展事件参见6a
 6	更新现金收款单签收日期	参数信息	1、	如果存在现金收款单，更新现金收款单的签收日期为接口参数中的签收日期
 4	查询始发应收单	参数信息	1、	查询该单对应的始发应收单
 2、	查询规则参见SR7
 3、	扩展事件参见7a
 5	更新始发应收单签收日期	参数信息	1、	如果存在始发应收单，更新始发应收单的签收日期为接口参数中的签收日期
 7	更新专线到付运费应收单签收日期	参数信息	1、	如果存在专线到付运费应收单，更新专线到付运费应收单的签收日期为接口参数中的签收日期
 8	如果是整车，调用接口生效整车尾款应付单	参数信息	1、	如果该单是整车单，调用《生效整车运费应付单》接口，生效整车尾款应付单
 9	生效代收货款应付单	参数信息	1、	如果是PDA签收，且代收货款金额大于M(基础资料中PDA代收货款阀值金额)元，则不生效代收货款应付单
 2、	否则，如果存在代收货款应付单，调用《生效应付单（代收货款类）》接口，使代收货款应付单生效
 10	生效装卸费应付单	参数信息	1、	调用《生效整车运费应付单》接口生成装卸费应付单
 11	新增财务签收记录	参数信息	1、	在财务签收记录表新增一条签收记录

 1.5.2	扩展事件
 序号	扩展事件	相关数据	备注
 1a	当接口参数校验不合法时，终止操作，并给用户异常信息提示	参数信息	1、	当运单号为空时，异常信息为“运单号为空，不能签收”
 2、	当运单号在系统中不存在时，异常信息为“运单号XX在系统中不存在，不能签收”
 3、	当运单的运输方式不为专线时，异常信息为“运单的运输方式不为专线，不能调用此接口”
 4、	当签收部门编码为空时，异常信息为“签收部门编码为空，不能签收”
 5、	当签收日期时，异常信息为“签收日期为空，不能签收”
 6、	签收日期与系统当前时间相差分钟数超过某一阀值M（阀值M由基础资料提供）时，异常信息为“签收日期与当前系统时间相差不能超过M分钟”
 2a	专线到付运费应收单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的专线到付运费应收单时，异常信息为“该单存在多个有效的专线到付运费应收单”
 3a	代收货款应收单校验不合法，终止操作，并给客户异常信息提示	参数信息	2、	当存在2个及以上的代收货款应收单时，异常信息为“该单存在多个有效的代收货款应收单”
 4a	校验代收货款、到付运费未还款或未挂具体客户时，终止操作，并给客户异常信息提示	参数信息	1、	当专线到付运费应收单未还款并且未挂具体客户时，异常信息为“专线到付运费未还款且未挂具体客户，不能签收”
 2、	当代收货款应收单未还款时，异常信息为“代收货款未还款，不能签收”
 6a	现金收款单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的现金收款单时，异常信息为“该单存在多个有效的现金收款单”
 7a	始发应收单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的始发应收单时，异常信息为“该单存在多个有效的始发应收单”
 2、	如果存在支付方式为“网上支付”的应收单，且已核销小于应收金额，则抛出异常信息“存在未支付的网上支付的应收单”

 1.6	业务规则
 序号	描述
 SR1	1、	运单号不能为空
 2、	运单号在系统中必须存在（调用接送货子系统接口）
 3、	运单的运输方式必须为专线
 4、	签收部门编码不能为空
 5、	签收日期不能为空
 6、	签收日期与系统当前时间相差分钟数不能超过某一阀值M（阀值M由基础资料提供）
 SR2	1、	专线到付运费应收单查询条件为：
 1）	应收单的单据类型为专线到付运费
 2）	应收单的有效标志为是
 3）	应收单的来源单据编号为参数中的运单号
 4）	应收单的来源单据类型为运单
 5）	应收单的应收部门编码为参数中的签收部门编码
 2、	不能存在2个及以上的专线到付运费应收单
 SR3	1、	代收货款应收单查询条件为：
 1）	应收单的单据类型为代收货款
 2）	应收单的有效标志为是
 3）	应收单的来源单据编号为参数中的运单号
 4）	应收单的来源单据类型为运单
 5）	应收单的应收部门编码为参数中的签收部门编码
 2、	不能存在2个及以上的代收货款应收单
 SR4	1、	到付运费应收单必须符合以下两个条件之一：
 a)	专线到付运费应收单的未核销金额等于0
 b)	专线到付运费应收单的客户必须是具体客户
 SR5	1、	代收货款应收单必须满足以下条件：
 a)	代收货款应收单的未核销金额等于0
 SR6	1、	现金收款单查询条件为：
 1）	现金收款单的运单号为参数中的运单号
 2）	现金收款单的有效标志为是
 3）	现金收款单的到达部门编码为参数中部门编码
 2、	不能存在2个及以上的现金收款单
 SR7	1、	始发应收单查询条件为：
 1）	应收单的来源单据编号为参数中的运单号
 2）	应收单的来源单据类型为运单
 3）	应收单的有效标志为是
 4）	应收单的单据类型为始发
 5）	应收单的到达部门编码为参数中部门编码
 2、	不能存在2个及以上的始发应收单

 1.7	数据元素
 1.7.1	参数信息（输入）
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 运单号	运单单号	文本	无	8~10	是	
 签收部门编码	签收部门编码	文本	无	20	是	
 签收日期	签收日期	日期	无	10	是	
 是否整车	是否整车	布尔	无	1	是	
 是否PDA签收		布尔	无	1	是 	

 1.7.2	应收单信息（输出）
 字段名称 	说明 (来源)	输出限制（类型，限制）	长度	是否必填	备注
 运单单号	运单单号	数字型字符	10	是	目前为8位
 签收日期	系统当前日期	日期	10	否	
 是否有效		布尔值	1	是	
 单据类型		文本	10	否	
 到达部门		文本	40	是	
 应收部门		文本	40	是	
 来源单据类型	运单	枚举	10	是	
 来源单据编号	运单号	文本	10	是	

 1.7.3	现金收款单（输出）
 字段名称 	说明 (来源)	输出限制（类型，限制）	长度	是否必填	备注
 运单单号	运单单号	数字型字符	10	是	目前为8位
 签收日期	系统当前日期	日期	10	否	
 是否有效		布尔值	1	是	
 到达部门		文本	40	是	

 1.8	非功能性需求
 使用量	100,000票/天，每年保持60%的增长率
 2012年全网估计用户数	截至到2012年5月，营业员或司机大概6,000人
 响应要求（如果与全系统要求 不一致的话）	属于高并发要求，要求3s完成响应
 使用时间段	0：00-24：00
 高峰使用时间段	13：00-20：00



 1.9	接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 运单号校验接口	接送货子系统	判断运单号是否存在
 获取PDA代收货款阀值金额		从基础资料中获取PDA代收货款阀值金额
 获取专线签收延迟分钟数阀值		从基础资料中获取专线签收延迟分钟数阀值



 修订记录 
 日期 	修订内容 	修订人员 	版本号 
 2012-06-01 	创建版 	曾斌文	V0.1
 2012-07-25 	升级到0.9版本	曾斌文	V0.9
 2012-12-11	签收时在财务签收表中新增一条记录	曾斌文	V1.1


 1.	SUC-651-偏线空运正常签收
 1.1	相关业务用例
 BUC_FOSS_4.7.20.10_130 正常签收
 1.2	用例描述
 确定运输类型为偏线或空运的业务的确认收入日期。运单的提货网点为非自有网点且运输方式为汽运的，运输类型为偏线。运单的运输方式为空运的，运输类型为空运。
 1.3	用例条件
 条件类型	描述	引用系统用例
 前置条件	在偏线空运签收界面录入签收信息	接送货系统用例：SUC-123-签收偏线空运货物
 后置条件	1、	存在现金收款单，更新现金收款单的确认收入日期
 2、	存在始发应收单,更新始发应收单的确认收入日期
 3、	存在偏线到付运费应收单，更新偏线到付运费应收单的确认收入日期
 4、	存在空运到付运费应收单，更新空运到付运费应收单的确认收入日期
 5、	在财务签收记录表中新增一条记录	
 1.4	操作用户角色
 操作用户	描述

 1.5	界面要求
 1.5.1	表现方式
 后台接口，无相关界面
 1.5.2	界面原型
 后台接口，无界面原型
 1.5.3	界面描述
 后台接口，无界面描述
 1.6	操作步骤
 1.6.1	偏线空运正常签收步骤
 序号	基本步骤	相关数据	补充步骤
 1	接口参数校验	参数信息	1、	对输入参数作校验
 2、	业务规则参见SR1
 3、	扩展事件参见1a
 2	查询现金收款单	参数信息	1、	查询该单对应的现金收款单
 2、	查询规则参见SR2
 3、	扩展事件参见2a
 3	更新现金收款单确认收入日期	参数信息	1、	如果存在现金收款单，更新现金收款单的确认收入日期为当前系统日期
 4	查询始发应收单	参数信息	1、	查询该单对应的始发应收单
 2、	查询规则参见SR3
 3、	扩展事件参见4a
 5	更新始发应收单确认收入日期	参数信息	1、	如果存在始发应收单，更新始发应收单的确认收入日期为当前系统日期
 6	查询偏线到付运费应收单	参数信息	1、	如果运输方式为偏线，查询该单对应的偏线到付运费应收单
 2、	查询规则参见SR4
 3、	扩展事件参见6a
 7	更新偏线到付运费应收单确认收入日期	参数信息	1、	如果存在偏线到付运费应收单，更新偏线到付运费应收单的确认收入日期为当前系统日期
 8	查询空运到付运费应收单	参数信息	1、	如果运输方式为空运，查询该单对应的空运到付运费应收单
 2、	查询规则参见SR5
 3、	扩展事件参见8a
 9	更新空运到付运费应收单确认收入日期	参数信息	1、	如果存在空运到付运费应收单，更新空运到付运费应收单的确认收入日期为当前系统日期
 10	新增财务签收记录	参数信息	1、	在财务签收记录表中新增一条财务签收记录

 1.6.2	扩展事件
 序号	扩展事件	相关数据	备注
 1a	参数校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当运单号为空时，异常信息为“运单号为空，不能签收”
 2、	当签收部门为空时，异常信息为“签收部门为空，不能签收”
 3、	当运单号在系统中不存在时，异常信息为“运单号在系统中不存在，不能签收”
 4、	当运单的运输方式不为空运或偏线，异常信息为“运单的运输方式不为空运或偏线，不能调用此接口”
 2a	现金收款单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的现金收款单时，异常信息为“该单存在多个有效的现金收款单”
 4a	始发应收单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的始发应收单时，异常信息为“该单存在多个有效的始发应收单”
 6a	偏线到付运费应收单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的偏线到付运费应收单时，异常信息为“该单存在多个有效的偏线到付运费应收单”
 8a	空运到付运费应收单校验不合法，终止操作，并给客户异常信息提示	参数信息	1、	当存在2个及以上的空运到付运费应收单时，异常信息为“该单存在多个有效的空运到付运费应收单”

 1.7	业务规则
 序号	描述
 SR1	1、	运单号不能为空。
 2、	签收部门不能为空
 3、	运单号在系统中必须存在。
 4、	运单的开单的运输方式必须为空运或偏线。
 SR2	1、	现金收款单查询条件为：
 1）	现金收款单的运单号为参数中的运单号
 2）	现金收款单有效标志为是
 3）	现金收款单的到达部门为接口参数中的签收部门
 2、	不能存在2个及以上的现金收款单
 SR3	1、	始发应收单查询条件为：
 1）	应收单的来源单据编号为参数中的运单号
 2）	应收单的来源单据类型为运单
 3）	应收单有效标志为是
 4）	应收单的单据类型为始发
 5）	应收单的到达部门为接口参数中的签收部门
 2、	不能存在2个及以上的始发应收单
 SR4	1、	偏线到付运费应收单查询条件为：
 1）	应收单的单据类型为偏线到付运费
 2）	应收单有效标志为是
 3）	应收单的来源单据编号为参数中的运单号
 4）	应收单的来源单据类型为运单
 5）	应收单的收款部门为参数中的签收部门
 2、	不能存在2个及以上的偏线到付运费应收单
 SR5	1、	空运到付运费应收单查询条件为：
 1）	应收单的单据类型为空运到付运费
 2）	应收单有效标志为是
 3）	应收单的来源单据编号为参数中的运单号
 4）	应收单的来源单据类型为运单
 5）	应收单的收款部门为参数中的签收部门
 2、	不能存在2个及以上的空运到付运费应收单

 1.8	数据元素
 1.8.1	参数信息（输入）
 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 运单号	运单单号	文本	无	8~10	是	
 签收部门	签收部门	文本	无	40	是	

 1.8.2	应收单信息（输出）
 字段名称 	说明 (来源)	输出限制（类型，限制）	长度	是否必填	备注
 运单单号	运单单号	数字型字符	10	是	目前为8位
 收入部门		文本	40	是	
 确认收入日期	系统当前日期	日期	10	否	
 是否有效		布尔值	1	是	
 单据类型		文本	10	否	

 1.8.3	现金收款单（输出）
 字段名称 	说明 (来源)	输出限制（类型，限制）	长度	是否必填	备注
 运单单号	运单单号	数字型字符	10	是	目前为8位
 确认收入日期	系统当前日期	日期	10	否	
 到达部门	对应运单业务的到达部门	文本	40	是	
 是否有效		布尔值	1	是	

 1.9	非功能性需求
 使用量	10000票/天，每年保持60%的增长率
 2012年全网估计用户数	7000人
 响应要求（如果与全系统要求 不一致的话）	属于高并发要求，要求3s完成响应
 使用时间段	8：00-24：00
 高峰使用时间段	13：00-20：00



 1.10	接口描述：
 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 运单号校验接口	综合管理子系统	判断运单号是否存在





 *
 *
 *
 *
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.server.service.IBillCashCollectionService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillReceivableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementExceptionType;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.service.ITakingService;
import com.deppon.foss.util.define.FossConstants;

/**
 * 收入确认、反确认服务（确认现金收款单、应收单的确认收入日期，更新应付单的签收日期，反确认反之操作）
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-10-16 上午8:43:31
 * @since
 * @version
 */
public class TakingService implements ITakingService {

	private static final Logger LOGGER = LogManager
			.getLogger(TakingService.class);

	/**
	 * 应收单公用Service
	 */
	private IBillReceivableService billReceivableService;

	/**
	 * 现金收款单公共Service
	 */
	private IBillCashCollectionService billCashCollectionService;

	/**
	 * 应付单公共Service
	 */
	private IBillPayableService billPayableService;

	/**
	 * 核销Service
	 */
	private IBillWriteoffService billWriteoffService;
	
	/** 
	 * 接送货运单Service. 
	 */
	private IWaybillManagerService waybillManagerService;
	
	/**
	 *  营业部 Service
	 */
	private ISaleDepartmentService saleDepartmentService;

	/**
	 * 确认收入 （修改现金收款单、应收单的确认收入日期为签收日期）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午8:43:42
	 * @param dto
	 *            【 waybillNo 运单号 productType 运输性质 signOrgCode 签收部门编码
	 *            signOrgName 签收部门名称 businessDate 签收日期 isWholeVehicle 是否整车运单
	 *            isPdaSign 是否PDA签收】
	 * @param currentInfo
	 */
	@Override
	public void confirmIncome(LineSignDto dto, CurrentInfo currentInfo) {
		if (dto == null) {
			throw new SettlementException(
					SettlementExceptionType.INTERFACE_AFFERFENT_DATA_IS_EMPTY);
		}

		LOGGER.info(" 签收操作调用：确认收入方法 ，运单号：" + dto.getWaybillNo());

		BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto(
				dto.getWaybillNo());
		billReceivableConDto
				.setSourceBillType(SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL);

		// 专线签收：查询始发应收单、到达运费应收单、代收货款应收单
		if (SettlementConstants.LINE_SIGN.equals(dto.getSignType())) {
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE });
		}
		// 偏线签收：查询始发应收单和偏线到付运费应收单
		else if (SettlementConstants.PARTIAL_LINE_SIGN
				.equals(dto.getSignType())) {
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE });
		}
		// 空运签收：查询始发应收单和空运到付运费应收单、空运代理代收货款应收单
		else if (SettlementConstants.AIR_SIGN.equals(dto.getSignType())) {
			billReceivableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,

							// 空运运单，到达客户提货方式为机场自提的，不会进入空运合大票，故代收货款应收单类型不变
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
							// 需要将到达应收单查询出来，判断其是否合票，如未合票，则抛出异常
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
							SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE });
		//快递代理签收
		}else if(SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())){
			billReceivableConDto
			.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
					SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE
					 });
			
		}
		List<BillReceivableEntity> receivables = this.billReceivableService
				.queryBillReceivableByCondition(billReceivableConDto);

		if (CollectionUtils.isNotEmpty(receivables)) {
			// 设置存在结算单据标记
			dto.setStlBillCounts(1);

			// 运单签收，验证应收单信息
			checkBillReceivable(dto, receivables);

			// 对所有查询的应收单，进行收入确认操作，包含（代收货款应收单，便于后续统计使用）确认收入日期
			for (BillReceivableEntity receEntity : receivables) {
				// 设置签收日期
				receEntity.setConrevenDate(dto.getSignDate());

				// 签收时，确认应收单收入
				this.billReceivableService.updateBillReceivableByConfirmIncome(
						receEntity, currentInfo);

				// 为代收货款应收单时，设置dto的CodReceivableEntity属性值，便于后续使用
				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(receEntity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
								.equals(receEntity.getBillType())
						|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD
								.equals(receEntity.getBillType())) {
					// 设置代收货款实体
					dto.setCodReceivableEntity(receEntity);
				}
			}
		}

		// 查询现金收款单---更新现金收款单的确认收入日期
		List<String> sourceBillNos = new ArrayList<String>();
		sourceBillNos.add(dto.getWaybillNo());
		List<BillCashCollectionEntity> cashCollections = billCashCollectionService
				.queryBySourceBillNOs(
						sourceBillNos,
						SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.ACTIVE);
		if (CollectionUtils.isNotEmpty(cashCollections)) {
			// 设置存在结算单据标记
			dto.setStlBillCounts(1);
			if (cashCollections.size() > 1) {
				throw new SettlementException("存在多条现金收款单");
			}
			BillCashCollectionEntity cashCollectionEntity = cashCollections
					.get(0);
			if (cashCollectionEntity != null) {
				if (cashCollectionEntity.getConrevenDate() != null) {
					throw new SettlementException("现金收款单已确认收入，不能进行签收操作");
				}
				cashCollectionEntity.setConrevenDate(dto.getSignDate());

				// 签收时 确认现金收款单收入
				this.billCashCollectionService.confirmIncomeBillCashCollection(
						cashCollectionEntity, currentInfo);
			}
		}

		LOGGER.info(" 确认收入end------");
	}

	/**
	 * 验证应收单 1、签收时始发应收单的支付方式为：网上支付的，应收单的未核销金额必须等于0
	 * 2、应收单的确认收入日期不为空时，说明运单已经签收了，不能再次签收 3、签收部门编码必须和应收单的到达部门编码保持一致
	 * 4、专线签收时，代收货款应收单必须全部还款， 到达运费应收单付款方式为到付的，应收单的未核销金额必须等于0
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-23 下午3:48:05
	 * @param dto
	 * @param receivables
	 */
	private void checkBillReceivable(LineSignDto dto,
			List<BillReceivableEntity> receivables) {
		if (dto == null) {
			throw new SettlementException(
					SettlementExceptionType.INTERFACE_AFFERFENT_DATA_IS_EMPTY);
		}

		// 验证同一个运单是否存在多条相同类型的应收单
		this.billReceivableService
				.validateWaybillForBillReceivable(receivables);
		for (int i = 0; i < receivables.size(); i++) {
			BillReceivableEntity receivableEntity = receivables.get(i);

			// 应收单的签收日期不为空和签收状态为已签收时
			if (receivableEntity.getConrevenDate() != null) {
				throw new SettlementException("应收单已确认收入，不能重复调用签收接口");
			}

			//ISSUE-2877
			//BUG-29804 去掉偏线签收时到达部门校验限制
			//BUG-33066 去年空运签收时到达部门校验限制
			//BUG-56275  去掉整车的签收时到达部门的限制
			if (FossConstants.NO.equals(dto.getIsPdaSign())
					&& !SettlementConstants.PARTIAL_LINE_SIGN.equals(dto.getSignType())
					&& !SettlementConstants.AIR_SIGN.equals(dto.getSignType())
					&& !SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())
					&&!ProductEntityConstants.PRICING_PRODUCT_FULL_VEHICLE.equals(receivableEntity.getProductCode())) {
				// 签收部门编码必须和应收单的到达部门编码一致
                //去掉快递始发应收到达部门校验限制
				if (StringUtils.isNotEmpty(receivableEntity.getDestOrgCode())
						&& !receivableEntity.getDestOrgCode().equals(dto.getSignOrgCode())
                        &&!(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.equals(receivableEntity.getBillType())
                        		|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE.equals(receivableEntity.getBillType())
            					|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE.equals(receivableEntity.getBillType()))
						 //判断产品类型是否属于快递
						&& !SettlementUtil.isPackageProductCode(receivableEntity.getBillType())
						) {
					// 因为二级网点的签收和到达部门已经校验过，所以如果是二级网点则不抛异常
					// 查询到达部门是否是合伙人二级网点H2
					SaleDepartmentEntity signDept = saleDepartmentService
							.querySaleDepartmentInfoByCode(dto.getSignOrgCode());
					if (null != signDept) {
						LOGGER.info("签收部门：" + dto.getSignOrgCode() + "，到达部门："
								+ receivableEntity.getDestOrgCode()+",是否二级网点："+signDept.getIsTwoLevelNetwork());
						// 如果签收部门不是二级网点，则抛出异常
						if (!FossConstants.YES.equals(signDept.getIsTwoLevelNetwork())){
							throw new SettlementException("签收部门和应收单的到达部门不一致，不能进行签收操作");
						}
					}
					
				}
			}
			
			// BUG-35170 空运未做合票，不允许签收
			if (SettlementConstants.AIR_SIGN.equals(dto.getSignType())) {
				
				//调用接送货接口查询运单
				WaybillEntity waybillBasic = waybillManagerService.queryWaybillBasicByNo(dto.getWaybillNo());
				if(waybillBasic == null){
					throw new SettlementException(String.format("不存在单号为%s的运单", dto.getWaybillNo()));
				}
				
				//是否单独开单
				boolean isDDKD = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_FREIGNT_DDKD
						.equals(waybillBasic.getFreightMethod());
				
				//是否机场自提
				boolean isAirportPickup = WaybillConstants.AIRPORT_PICKUP.equals(waybillBasic.getReceiveMethod());
				
				if ((SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
						.equals(receivableEntity.getBillType())||
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
						.equals(receivableEntity.getBillType())
						) && !isDDKD && !isAirportPickup) {
					throw new SettlementException(String.format("%s对应的空运到达应收单未合票,不允许签收",
							dto.getWaybillNo()));
				}
			}

			// 应收单未核销金额大于0的
			if (receivableEntity.getUnverifyAmount() != null
					&& receivableEntity.getUnverifyAmount().compareTo(
							BigDecimal.ZERO) > 0) {

				// 2012-11-21 日增加需求： 签收时类型为始发应收单且支付方式为：网上支付的，应收单的未核销金额必须等于0
				
				//根据ISSUE-2486:网上支付当临时欠款处理
//				if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE
//						.equals(receivableEntity.getBillType())
//						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE
//								.equals(receivableEntity.getPaymentType())) {
//					throw new SettlementException(
//							"付款方式为网上支付的始发运费应收单存在未核销金额，不能进行签收操作");
//				}
				
				// 到达应收单或到达运费应收单 到付，且未核销金额大于0，抛出异常
				if ((SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE
							.equals(receivableEntity.getBillType())
						||SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE
							.equals(receivableEntity.getBillType()))
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT
								.equals(receivableEntity.getPaymentType())) {
					throw new SettlementException("到付运费应收单存在未核销金额，不能进行签收操作");
				}
				// 专线签收，需要判断代收货款的未核销金额必须等于0，未核销金额大于0，抛出异常
				else if (SettlementConstants.LINE_SIGN
						.equals(dto.getSignType())
						&& SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE
								.equals(receivableEntity.getBillType())) {
					throw new SettlementException("代收货款应收单存在未核销金额，不能进行签收操作");
				}
			}
		}
	}

	/**
	 * 校验反签收服务
	 * @param dto
	 * @param currentInfo
	 */
	public void validReverseConfirmIncome(LineSignDto dto,
			CurrentInfo currentInfo)
	{
		BillCashCollectionEntity cashCollectionEntity = null;
		List<BillReceivableEntity> billReceivables = null;

		if (dto == null)
		{
			throw new SettlementException(
					SettlementExceptionType.INTERFACE_AFFERFENT_DATA_IS_EMPTY);
		}

		LOGGER.info("校验反签收操作调用：反确认收入方法，运单号：" + dto.getWaybillNo());

		// 查询现金收款单
		List<String> sourceBillNos = new ArrayList<String>();
		sourceBillNos.add(dto.getWaybillNo());
		List<BillCashCollectionEntity> cashCollections = billCashCollectionService
				.queryBySourceBillNOs(
						sourceBillNos,
						SettlementDictionaryConstants.BILL_CASH_COLLECTION__SOURCE_BILL_TYPE__WAYBILL,
						FossConstants.ACTIVE);
		if (CollectionUtils.isNotEmpty(cashCollections))
		{
			// 设置存在财务单据标记，存在设置为1
			dto.setStlBillCounts(1);
			if (cashCollections.size() > 1)
			{
				throw new SettlementException("存在多条现金收款单");
			}
			cashCollectionEntity = cashCollections.get(0);

			// 现金收款单的确认收入日期为空，说明运单并未签收，直接抛出异常信息
			if (cashCollectionEntity.getConrevenDate() == null)
			{
				throw new SettlementException("运单还未签收，不能进行反签收操作");
			}
			
			dto.setBillCashCollectionEntity(cashCollectionEntity);
		}

		// 反签收-反确认收入：始发应收单/到达
		BillReceivableConditionDto billReceivableConDto = new BillReceivableConditionDto();
		billReceivableConDto.setWaybillNo(dto.getWaybillNo());

		// 到达的应收单类型，默认为专线：查询始发应收单和到达运费应收单
		String billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE;

		// 偏线：查询始发应收单和到达偏线代理应收单
		if (SettlementConstants.PARTIAL_LINE_SIGN.equals(dto.getSignType()))
		{
			billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_PARTIAL_LINE;
		}
		// 空运：查询始发应收单和空运中转代理应收单
		else if (SettlementConstants.AIR_SIGN.equals(dto.getSignType()))
		{
			billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY;
		//快递代理：
		}else if(SettlementConstants.LAND_STOWAGE_SIGN.equals(dto.getSignType())){
			billType = SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_LAND_STOWAGE;
		}

		billReceivableConDto
				.setBillTypes(new String[] {
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE,
						billType,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__ORIGINAL_RECEIVABLE,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__FREIGHT_COLLECT_RECEIVABLE,
						SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__PARTNER__DELIVERY_FEE_RECEIVABLE});
		billReceivables = this.billReceivableService
				.queryBillReceivableByCondition(billReceivableConDto);

		if (CollectionUtils.isNotEmpty(billReceivables))
		{

			// 设置存在财务单据标记，存在设置为1
			dto.setStlBillCounts(1);

			// 验证一个运单是否存在相同类型的多条应收单
			this.billReceivableService
					.validateWaybillForBillReceivable(billReceivables);

			/*// 反签收时，校验应收单信息
			checkBillReceivableByReverseConfirmIncome(billReceivables,
					new Date(), dto);*/ // 353654 TODO
			
			dto.setBillReceivableEntityList(billReceivables);

		}

		LOGGER.error("校验反确认收入方法结束------");
		
	}
	
	/**
	 * 反确认收入 （修改现金收款单和 应收单的确认收入日期为空）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-16 上午8:43:55
	 * @param dto
	 * @param currentInfo
	 * @return
	 */
	@Override
	public void reverseConfirmIncome(LineSignDto dto, CurrentInfo currentInfo)
	{

		LOGGER.info(" 反签收操作调用：反确认收入方法，运单号：" + dto.getWaybillNo());

		this.validReverseConfirmIncome(dto, currentInfo);

		if (CollectionUtils.isNotEmpty(dto.getBillReceivableEntityList()))
		{
			// 反应收单确认收入日期
			for (BillReceivableEntity entity : dto
					.getBillReceivableEntityList())
			{
				// 设置确认收入日期为空
				entity.setConrevenDate(null);

				// 调用公用应收单反签收时置空确认收入日期
				this.billReceivableService
						.updateBillReceivableByReverseConfirmIncome(entity,
								currentInfo);
			}
		}

		// 反签收，反确认现金收款单
		if (dto.getBillCashCollectionEntity() != null)
		{
			// 设置确认收入日期为空
			dto.getBillCashCollectionEntity().setConrevenDate(null);
			this.billCashCollectionService
					.reverseConfirmIncomeBillCashCollection(
							dto.getBillCashCollectionEntity(), currentInfo);
		}

		LOGGER.error(" 反确认收入方法结束------");
	}

	/**
	 * 反签收时，验证应收单 （应收单不能被网上锁定，应收单的确认收入日期不能为空， 操作部门和应收单的到达部门编码必须一致）
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-10-30 上午9:11:33
	 * @param billReceivables
	 *            应收单集合
	 * @param date
	 *            服务系统时间
	 * 
	 * @param dto
	 *            接口调用传入的dto
	 */
	private void checkBillReceivableByReverseConfirmIncome(
			List<BillReceivableEntity> billReceivables, Date date,
			LineSignDto dto) {
		for (BillReceivableEntity billReceivableEntity : billReceivables) {
			
			// 2013-05-20修改: ISSUE-2874
//			if (billReceivableEntity.getUnlockDateTime() != null
//					&& date.before(billReceivableEntity.getUnlockDateTime())) {
//				throw new SettlementException("存在网上支付锁定的应收单，不能进行反签收操作");
//			}
			if (billReceivableEntity.getConrevenDate() == null) {
				throw new SettlementException("运单还未签收，不能进行反签收操作");
			}
		}
	}

	 /**
	  * 开单网上支付，但是尚未支付的单据
	  * @author lianghaisheng
	  * @param waybillNos
	  * @return 尚未支付的单据
	  */
	@Override
	public List<String> unpaidOnlinePay(List<String> waybillNos) {
		
		//DN201607150014 裹裹订单开单网上支付不需要检验是否网上支付完成  by 243921
		//update by 231434 2016-12-8 裹裹运单重新加上校验 DN201612060015、DN201612060014 
//		Iterator<String> it = waybillNos.iterator();
//		while (it.hasNext()) {
//			String waybillNO = it.next();
//			//调用接送货接口查询运单
//			WaybillEntity waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillNO);
//			if(null != waybillEntity){
//				if(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.equals(waybillEntity.getPaidMethod()) && 
//						StringUtils.isNotBlank(waybillEntity.getOrderNo()) && waybillEntity.getOrderNo().contains("GG")){
//					//将开单网上支付的裹裹运单从列表中删除
//					it.remove();
//				}
//			}else{
//				throw new SettlementException("该运单信息不存在！运单号：" + waybillNO);
//			}
//		}

        //判断传入的是否为空
        if(CollectionUtils.isEmpty(waybillNos)){
        	return null;
        }
		List<String> unpaidWaybills =null;
		List<BillReceivableEntity>  billReceivables =billReceivableService.queryBySourceBillNOs(waybillNos, SettlementDictionaryConstants.
				BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL, FossConstants.ACTIVE);
		//循环判断是否是存在未核销网上支付的运单
       if(CollectionUtils.isNotEmpty(billReceivables)){
    	   for( BillReceivableEntity billReceivable:billReceivables){    		
    		   //判断是否未核销，如果未核销则返回
    		   if(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__ORIGIN_RECEIVABLE.
    				   equals(billReceivable.getBillType())&&
    				   SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE.
    				   equals(billReceivable.getPaymentType())
    				   &&BigDecimal.ZERO.compareTo(billReceivable.getUnverifyAmount())<0){
    			   //如果为空则需要创建对应的对象
    			   if(unpaidWaybills == null){
    				   unpaidWaybills = new ArrayList<String>();
    			   }
    			   unpaidWaybills.add(billReceivable.getWaybillNo());
    		   }
    	   }
       }
	return unpaidWaybills;
	}
	
	/**
	 * @return billReceivableService
	 */
	public IBillReceivableService getBillReceivableService() {
		return billReceivableService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @return billCashCollectionService
	 */
	public IBillCashCollectionService getBillCashCollectionService() {
		return billCashCollectionService;
	}

	/**
	 * @param billCashCollectionService
	 */
	public void setBillCashCollectionService(
			IBillCashCollectionService billCashCollectionService) {
		this.billCashCollectionService = billCashCollectionService;
	}

	/**
	 * @return billPayableService
	 */
	public IBillPayableService getBillPayableService() {
		return billPayableService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * @return billWriteoffService
	 */
	public IBillWriteoffService getBillWriteoffService() {
		return billWriteoffService;
	}

	/**
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}

	/**
	 * @return the saleDepartmentService
	 */
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	/**
	 * @param saleDepartmentService the saleDepartmentService to set
	 */
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

}
