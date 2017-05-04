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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/LandBillPaidCODService.java
 * 
 * FILE NAME        	: LandBillPaidCODService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 用例描述
 * 快递代理总调对账员对已经签收的快递代理代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	快递代理运单已经签收
 * 2、	运单含有代收货款
 * 反审核：
 * 1、	代收货款不能被资金部冻结或退款中或已退款
 * 2、	代收货款对应的应付单支付状态必须是“可支付”状态
 * 
 * 后置条件
 * 1.	审核成功后，生效代收货款应付单
 * 2.	反审核成功后，失效代收货款应付单
 * 
 * 查询
 * 调用后台接口查询代收货款信息
 * 1、	按日期查询时签收起止日期、审核起止日期二者至少选择一个
 * 2、	按签收日期查询时签收起止日期都不能为空
 * 3、	按审核日期查询时审核起止日期都不能为空
 * 4、	按签收日期查询时签收起止日期不能超过7天
 * 5、	按审核日期查询时审核起止日期不能超过7天
 * 6、	按单号查询时单号信息不能为空
 * 7、	按单号查询时单号最多不能超过10个
 * 8、	查询代收货款信息时，默认审核和反审核的部门为当前登陆人所在部门
 * 9、	查询代收货款信息时，默认运单的产品类型为精准快递代理
 * 10、	代收货款状态不为“资金部冻结”、“退款中”、“退款成功”等状态
 * 11、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当按日期查询时，签收起止日期、审核起止日期都为空时，终止操作并抛出异常，异常信息为“签收起止日期、审核起止日期二者至少选择一个”
 * 2、	当按日期查询时，签收起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“签收起止日期都不允许为空”
 * 3、	当按日期查询时，审核起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“审核起止日期都不允许为空”
 * 4、	当按日期查询时，且签收起止日期超过7天时，终止操作并抛出异常，异常信息为“签收起止日期不能超过7天”
 * 5、	当按日期查询时，且审核起止日期超过7天时，终止操作并抛出异常，异常信息为“审核起止日期不能超过7天”
 * 6、	当按单号查询时，单号为空时，终止操作并抛出异常，异常信息为“单号信息不能为空”
 * 7、	当按单号查询时，单号超过10个时，终止操作并抛出异常，异常信息为“单号不能超过10个”
 * 8、	如果没查询到数据，则终止操作，弹出提示信息：“没有查询到对应的代收货款单据”
 * 
 * 审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的应付单的支付状态为“不可支付”
 * 3、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当代收货款信息退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号代收货款信息退款状态为资金部冻结、退款中、退款成功，不能审核”
 * 2、	当代收货款信息对应的代收货款应付单的支付状态为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态为可支付，不能审核”
 * 
 * 反审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的代收货款应付单的支付状态为“可支付”
 * 3、	代收货款应付单的已核销金额必须等于0
 * 扩展事件
 * 1、	当代收货款信息的退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号的退款状态为资金部冻结、退款中、退款成功，不能反审核”
 * 2、	当代收货款信息对应的代收到货款应付单的支付状态不为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态不为可支付，不能反审核”
 * 3、	当代收货款应付单的已核销金额大于0时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单已核销或部分核销，不能反审核”
 * 
 *  * 用例描述
 * 快递代理总调对账员对已经签收的快递代理代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	快递代理运单已经签收
 * 2、	运单含有代收货款
 * 反审核：
 * 1、	代收货款不能被资金部冻结或退款中或已退款
 * 2、	代收货款对应的应付单支付状态必须是“可支付”状态
 * 
 * 后置条件
 * 1.	审核成功后，生效代收货款应付单
 * 2.	反审核成功后，失效代收货款应付单
 * 
 * 查询
 * 调用后台接口查询代收货款信息
 * 1、	按日期查询时签收起止日期、审核起止日期二者至少选择一个
 * 2、	按签收日期查询时签收起止日期都不能为空
 * 3、	按审核日期查询时审核起止日期都不能为空
 * 4、	按签收日期查询时签收起止日期不能超过7天
 * 5、	按审核日期查询时审核起止日期不能超过7天
 * 6、	按单号查询时单号信息不能为空
 * 7、	按单号查询时单号最多不能超过10个
 * 8、	查询代收货款信息时，默认审核和反审核的部门为当前登陆人所在部门
 * 9、	查询代收货款信息时，默认运单的产品类型为精准快递代理
 * 10、	代收货款状态不为“资金部冻结”、“退款中”、“退款成功”等状态
 * 11、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当按日期查询时，签收起止日期、审核起止日期都为空时，终止操作并抛出异常，异常信息为“签收起止日期、审核起止日期二者至少选择一个”
 * 2、	当按日期查询时，签收起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“签收起止日期都不允许为空”
 * 3、	当按日期查询时，审核起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“审核起止日期都不允许为空”
 * 4、	当按日期查询时，且签收起止日期超过7天时，终止操作并抛出异常，异常信息为“签收起止日期不能超过7天”
 * 5、	当按日期查询时，且审核起止日期超过7天时，终止操作并抛出异常，异常信息为“审核起止日期不能超过7天”
 * 6、	当按单号查询时，单号为空时，终止操作并抛出异常，异常信息为“单号信息不能为空”
 * 7、	当按单号查询时，单号超过10个时，终止操作并抛出异常，异常信息为“单号不能超过10个”
 * 8、	如果没查询到数据，则终止操作，弹出提示信息：“没有查询到对应的代收货款单据”
 * 
 * 审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的应付单的支付状态为“不可支付”
 * 3、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当代收货款信息退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号代收货款信息退款状态为资金部冻结、退款中、退款成功，不能审核”
 * 2、	当代收货款信息对应的代收货款应付单的支付状态为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态为可支付，不能审核”
 * 
 * 反审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的代收货款应付单的支付状态为“可支付”
 * 3、	代收货款应付单的已核销金额必须等于0
 * 扩展事件
 * 1、	当代收货款信息的退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号的退款状态为资金部冻结、退款中、退款成功，不能反审核”
 * 2、	当代收货款信息对应的代收到货款应付单的支付状态不为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态不为可支付，不能反审核”
 * 3、	当代收货款应付单的已核销金额大于0时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单已核销或部分核销，不能反审核”
 * 
 *  * 用例描述
 * 快递代理总调对账员对已经签收的快递代理代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	快递代理运单已经签收
 * 2、	运单含有代收货款
 * 反审核：
 * 1、	代收货款不能被资金部冻结或退款中或已退款
 * 2、	代收货款对应的应付单支付状态必须是“可支付”状态
 * 
 * 后置条件
 * 1.	审核成功后，生效代收货款应付单
 * 2.	反审核成功后，失效代收货款应付单
 * 
 * 查询
 * 调用后台接口查询代收货款信息
 * 1、	按日期查询时签收起止日期、审核起止日期二者至少选择一个
 * 2、	按签收日期查询时签收起止日期都不能为空
 * 3、	按审核日期查询时审核起止日期都不能为空
 * 4、	按签收日期查询时签收起止日期不能超过7天
 * 5、	按审核日期查询时审核起止日期不能超过7天
 * 6、	按单号查询时单号信息不能为空
 * 7、	按单号查询时单号最多不能超过10个
 * 8、	查询代收货款信息时，默认审核和反审核的部门为当前登陆人所在部门
 * 9、	查询代收货款信息时，默认运单的产品类型为精准快递代理
 * 10、	代收货款状态不为“资金部冻结”、“退款中”、“退款成功”等状态
 * 11、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当按日期查询时，签收起止日期、审核起止日期都为空时，终止操作并抛出异常，异常信息为“签收起止日期、审核起止日期二者至少选择一个”
 * 2、	当按日期查询时，签收起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“签收起止日期都不允许为空”
 * 3、	当按日期查询时，审核起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“审核起止日期都不允许为空”
 * 4、	当按日期查询时，且签收起止日期超过7天时，终止操作并抛出异常，异常信息为“签收起止日期不能超过7天”
 * 5、	当按日期查询时，且审核起止日期超过7天时，终止操作并抛出异常，异常信息为“审核起止日期不能超过7天”
 * 6、	当按单号查询时，单号为空时，终止操作并抛出异常，异常信息为“单号信息不能为空”
 * 7、	当按单号查询时，单号超过10个时，终止操作并抛出异常，异常信息为“单号不能超过10个”
 * 8、	如果没查询到数据，则终止操作，弹出提示信息：“没有查询到对应的代收货款单据”
 * 
 * 审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的应付单的支付状态为“不可支付”
 * 3、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当代收货款信息退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号代收货款信息退款状态为资金部冻结、退款中、退款成功，不能审核”
 * 2、	当代收货款信息对应的代收货款应付单的支付状态为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态为可支付，不能审核”
 * 
 * 反审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的代收货款应付单的支付状态为“可支付”
 * 3、	代收货款应付单的已核销金额必须等于0
 * 扩展事件
 * 1、	当代收货款信息的退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号的退款状态为资金部冻结、退款中、退款成功，不能反审核”
 * 2、	当代收货款信息对应的代收到货款应付单的支付状态不为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态不为可支付，不能反审核”
 * 3、	当代收货款应付单的已核销金额大于0时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单已核销或部分核销，不能反审核”
 * 
 *  * 用例描述
 * 快递代理总调对账员对已经签收的快递代理代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	快递代理运单已经签收
 * 2、	运单含有代收货款
 * 反审核：
 * 1、	代收货款不能被资金部冻结或退款中或已退款
 * 2、	代收货款对应的应付单支付状态必须是“可支付”状态
 * 
 * 后置条件
 * 1.	审核成功后，生效代收货款应付单
 * 2.	反审核成功后，失效代收货款应付单
 * 
 * 查询
 * 调用后台接口查询代收货款信息
 * 1、	按日期查询时签收起止日期、审核起止日期二者至少选择一个
 * 2、	按签收日期查询时签收起止日期都不能为空
 * 3、	按审核日期查询时审核起止日期都不能为空
 * 4、	按签收日期查询时签收起止日期不能超过7天
 * 5、	按审核日期查询时审核起止日期不能超过7天
 * 6、	按单号查询时单号信息不能为空
 * 7、	按单号查询时单号最多不能超过10个
 * 8、	查询代收货款信息时，默认审核和反审核的部门为当前登陆人所在部门
 * 9、	查询代收货款信息时，默认运单的产品类型为精准快递代理
 * 10、	代收货款状态不为“资金部冻结”、“退款中”、“退款成功”等状态
 * 11、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当按日期查询时，签收起止日期、审核起止日期都为空时，终止操作并抛出异常，异常信息为“签收起止日期、审核起止日期二者至少选择一个”
 * 2、	当按日期查询时，签收起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“签收起止日期都不允许为空”
 * 3、	当按日期查询时，审核起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“审核起止日期都不允许为空”
 * 4、	当按日期查询时，且签收起止日期超过7天时，终止操作并抛出异常，异常信息为“签收起止日期不能超过7天”
 * 5、	当按日期查询时，且审核起止日期超过7天时，终止操作并抛出异常，异常信息为“审核起止日期不能超过7天”
 * 6、	当按单号查询时，单号为空时，终止操作并抛出异常，异常信息为“单号信息不能为空”
 * 7、	当按单号查询时，单号超过10个时，终止操作并抛出异常，异常信息为“单号不能超过10个”
 * 8、	如果没查询到数据，则终止操作，弹出提示信息：“没有查询到对应的代收货款单据”
 * 
 * 审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的应付单的支付状态为“不可支付”
 * 3、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当代收货款信息退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号代收货款信息退款状态为资金部冻结、退款中、退款成功，不能审核”
 * 2、	当代收货款信息对应的代收货款应付单的支付状态为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态为可支付，不能审核”
 * 
 * 反审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的代收货款应付单的支付状态为“可支付”
 * 3、	代收货款应付单的已核销金额必须等于0
 * 扩展事件
 * 1、	当代收货款信息的退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号的退款状态为资金部冻结、退款中、退款成功，不能反审核”
 * 2、	当代收货款信息对应的代收到货款应付单的支付状态不为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态不为可支付，不能反审核”
 * 3、	当代收货款应付单的已核销金额大于0时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单已核销或部分核销，不能反审核”
 * 
 *  * 用例描述
 * 快递代理总调对账员对已经签收的快递代理代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	快递代理运单已经签收
 * 2、	运单含有代收货款
 * 反审核：
 * 1、	代收货款不能被资金部冻结或退款中或已退款
 * 2、	代收货款对应的应付单支付状态必须是“可支付”状态
 * 
 * 后置条件
 * 1.	审核成功后，生效代收货款应付单
 * 2.	反审核成功后，失效代收货款应付单
 * 
 * 查询
 * 调用后台接口查询代收货款信息
 * 1、	按日期查询时签收起止日期、审核起止日期二者至少选择一个
 * 2、	按签收日期查询时签收起止日期都不能为空
 * 3、	按审核日期查询时审核起止日期都不能为空
 * 4、	按签收日期查询时签收起止日期不能超过7天
 * 5、	按审核日期查询时审核起止日期不能超过7天
 * 6、	按单号查询时单号信息不能为空
 * 7、	按单号查询时单号最多不能超过10个
 * 8、	查询代收货款信息时，默认审核和反审核的部门为当前登陆人所在部门
 * 9、	查询代收货款信息时，默认运单的产品类型为精准快递代理
 * 10、	代收货款状态不为“资金部冻结”、“退款中”、“退款成功”等状态
 * 11、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当按日期查询时，签收起止日期、审核起止日期都为空时，终止操作并抛出异常，异常信息为“签收起止日期、审核起止日期二者至少选择一个”
 * 2、	当按日期查询时，签收起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“签收起止日期都不允许为空”
 * 3、	当按日期查询时，审核起止日期中有一个不为空，另一个为空时，终止操作并抛出异常，异常信息为“审核起止日期都不允许为空”
 * 4、	当按日期查询时，且签收起止日期超过7天时，终止操作并抛出异常，异常信息为“签收起止日期不能超过7天”
 * 5、	当按日期查询时，且审核起止日期超过7天时，终止操作并抛出异常，异常信息为“审核起止日期不能超过7天”
 * 6、	当按单号查询时，单号为空时，终止操作并抛出异常，异常信息为“单号信息不能为空”
 * 7、	当按单号查询时，单号超过10个时，终止操作并抛出异常，异常信息为“单号不能超过10个”
 * 8、	如果没查询到数据，则终止操作，弹出提示信息：“没有查询到对应的代收货款单据”
 * 
 * 审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的应付单的支付状态为“不可支付”
 * 3、	代收货款对应的运单已经签收
 * 扩展事件
 * 1、	当代收货款信息退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号代收货款信息退款状态为资金部冻结、退款中、退款成功，不能审核”
 * 2、	当代收货款信息对应的代收货款应付单的支付状态为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态为可支付，不能审核”
 * 
 * 反审核
 * 1、	代收货款信息状态不为“资金部冻结”、“退款中”、“退款成功”
 * 2、	代收货款信息对应的代收货款应付单的支付状态为“可支付”
 * 3、	代收货款应付单的已核销金额必须等于0
 * 扩展事件
 * 1、	当代收货款信息的退款状态为“资金部冻结”、“退款中”、“退款成功”时，终止操作并抛出异常，异常信息为“XX单号的退款状态为资金部冻结、退款中、退款成功，不能反审核”
 * 2、	当代收货款信息对应的代收到货款应付单的支付状态不为“可支付”时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单支付状态不为可支付，不能反审核”
 * 3、	当代收货款应付单的已核销金额大于0时，终止操作并抛出异常，异常信息为“XX单号代收货款应付单已核销或部分核销，不能反审核”
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
 * 
 * 
 * 
 * 
 * 
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ILandBillPaidCODQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODGridDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.LandBillPaidCODQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理代收货款审核服务
 * 
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-30 上午11:39:22
 */
public class LandBillPaidCODService implements ILandBillPaidCODService {

	private static final Logger LOGGER = LogManager
			.getLogger(LandBillPaidCODService.class);
	
	/**
	 * 代收货款快递代理审核Dao
	 */
	private ILandBillPaidCODQueryDao landBillPaidCodQueryDao;

	/**
	 * 代收货款记录管理Dao
	 */
	private ICodCommonService codCommonService;

	/**
	 * 代收货款记录管理服务
	 */
	private IBillPayCODService billPayCODService;

	/**
	 * 按运单号查询代收货款
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-30 上午11:40:13
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#queryCODByWaybillNos(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public List<LandBillPaidCODGridDto> queryByWaybillNos(
			CurrentInfo currentInfo, List<String> waybillNos) {
		// 检查参数
		if (currentInfo != null
				&& StringUtils.isNotEmpty(currentInfo.getCurrentDeptCode())
				&& CollectionUtils.isNotEmpty(waybillNos)) {

			LandBillPaidCODQueryDto dto = getDefaultQueryDto();

			// 查询条件
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());

			//运单号
			dto.setWaybillNoSet(waybillNos);

			List<LandBillPaidCODGridDto> dtoSet = this.landBillPaidCodQueryDao
					.queryByWaybillNos(dto);
			// 转化后，返回
			return dtoSet;
		} else {
			throw new SettlementException("内部错误，参数不正确");
		}
	}


	/**
	 * 
	 * 按审核日期查询总行数,合计金额
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 上午9:28:03
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#queryTotalRowsByAuditDate(com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo)
	 */
	@Override
	public LandBillPaidCODGridDto queryTotalRowsByAuditDate(CurrentInfo currentInfo,
			LandBillPaidCODConditionVo conditionVo) {
		// 判断查询条件
		if (conditionVo != null) {
			// 检查参数
			this.checkByAuditDateParams(conditionVo);

			LandBillPaidCODQueryDto dto = getDefaultQueryDto();

			// 查询条件
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());
						
			dto.setInceptAuditDate(conditionVo.getInceptAuditDate());
			dto.setEndAuditDate(conditionVo.getEndAuditDate());
			dto.setCodType(conditionVo.getCodType());
			// 默认为已经审核
			dto.setLandStatus(SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE);

			// 执行查询
			return this.landBillPaidCodQueryDao.queryTotalRowsByAuditDate(dto);
		} else {
			throw new SettlementException("内部对象为空!");
		}
	}

	/**
	 * 按审核日期分页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 上午10:14:24
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#queryByAuditDate(com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo,
	 *      int, int)
	 */
	@Override
	public List<LandBillPaidCODGridDto> queryByAuditDate(CurrentInfo currentInfo,
			LandBillPaidCODConditionVo conditionVo, int offset, int limit) {
		// 判断查询条件
		if (conditionVo != null) {
			// 检查参数
			this.checkByAuditDateParams(conditionVo);

			LandBillPaidCODQueryDto dto = getDefaultQueryDto();

			// 查询条件
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());			
			
			dto.setInceptAuditDate(conditionVo.getInceptAuditDate());
			dto.setEndAuditDate(conditionVo.getEndAuditDate());
			dto.setCodType(conditionVo.getCodType());
			// 默认为已经审核
			dto.setLandStatus(SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE);

			// 执行查询
			List<LandBillPaidCODGridDto> result = this.landBillPaidCodQueryDao
					.queryByAuditDate(dto, offset, limit);
			return result;
		} else {
			throw new SettlementException("内部对象为空!");
		}
	}

	/**
	 * 检查按审核日期的参数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 上午10:12:50
	 */
	private void checkByAuditDateParams(LandBillPaidCODConditionVo conditionVo) {
		if (conditionVo != null) {
			// 审核日期
			if (conditionVo.getInceptAuditDate() == null) {
				throw new SettlementException("起始审核日期为空");
			}
			if (conditionVo.getEndAuditDate() == null) {
				throw new SettlementException("结束审核日期为空");
			}
			// 判断时间范围
			// 当前日期
			Date sysDate = new Date();
			if (conditionVo.getInceptAuditDate().after(sysDate)) {
				throw new SettlementException("起始审核日期晚于当前日期!");
			}
			// 当前日期
			if (conditionVo.getInceptAuditDate().after(
					conditionVo.getEndAuditDate())) {
				throw new SettlementException("起始审核日期晚于结束日期!");
			}
		}
	}

	/**
	 * 检查按签收日期的参数
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 上午10:12:50
	 */
	private void checkBySignDateParams(LandBillPaidCODConditionVo conditionVo) {
		if (conditionVo != null) {
			// 签收日期
			if (conditionVo.getInceptSignDate() == null) {
				throw new SettlementException("起始审核日期为空");
			}
			if (conditionVo.getEndSignDate() == null) {
				throw new SettlementException("结束审核日期为空");
			}
			// 判断时间范围
			// 当前日期
			Date sysDate = new Date();
			if (conditionVo.getInceptSignDate().after(sysDate)) {
				throw new SettlementException("起始审核日期晚于当前日期!");
			}
			// 当前日期
			if (conditionVo.getInceptSignDate().after(
					conditionVo.getEndSignDate())) {
				throw new SettlementException("起始审核日期晚于结束日期!");
			}
		}
	}

	/**
	 * 按签收时间查询总行数，合计金额 
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 上午10:27:52
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#queryTotalRowsBySignDate(com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo)
	 */
	@Override
	public LandBillPaidCODGridDto queryTotalRowsBySignDate(CurrentInfo currentInfo,
			LandBillPaidCODConditionVo conditionVo) {
		// 判断查询条件
		if (conditionVo != null) {
			// 检查参数
			this.checkBySignDateParams(conditionVo);

			// 生成查询条件
			LandBillPaidCODQueryDto dto = getDefaultQueryDto();

			// 查询条件
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());
			
			dto.setInceptSignDate(conditionVo.getInceptSignDate());
			dto.setEndSignDate(conditionVo.getEndSignDate());
			dto.setCodType(conditionVo.getCodType());
			dto.setLandStatus(conditionVo.getLandStatus());

			// 执行查询
			return this.landBillPaidCodQueryDao.queryTotalRowsBySignDate(dto);
		} else {
			throw new SettlementException("内部对象为空!");
		}
	}

	/**
	 * 按签收时间分页查询
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-9 上午10:28:06
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#queryBySignDate(com.deppon.foss.module.settlement.consumer.api.shared.vo.LandBillPaidCODConditionVo,
	 *      int, int)
	 */
	@Override
	public List<LandBillPaidCODGridDto> queryBySignDate(CurrentInfo currentInfo,
			LandBillPaidCODConditionVo conditionVo, int offset, int limit) {
		// 判断查询条件
		if (conditionVo != null) {
			// 检查参数
			this.checkBySignDateParams(conditionVo);

			// 生成查询条件
			LandBillPaidCODQueryDto dto = getDefaultQueryDto();

			// 查询条件
			// 设置用户数据查询权限
			dto.setUserCode(currentInfo.getEmpCode());			
			
			dto.setInceptSignDate(conditionVo.getInceptSignDate());
			dto.setEndSignDate(conditionVo.getEndSignDate());
			dto.setCodType(conditionVo.getCodType());
			dto.setLandStatus(conditionVo.getLandStatus());
			
			// 执行查询
			List<LandBillPaidCODGridDto> result = this.landBillPaidCodQueryDao
					.queryBySignDate(dto, offset, limit);
			return result;
		} else {
			throw new SettlementException("内部对象为空!");
		}
	}

	/**
	 * 生效快递代理代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午6:38:22
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#makeEffectiveBillCOD(java.lang.String)
	 */
	@Transactional
	@Override
	public void makeEffectiveBillCOD(List<String> codIds,
			CurrentInfo currentInfo) {
		LOGGER.info("审核快递代理代收货款开始");
		// 检查参数
		if (CollectionUtils.isNotEmpty(codIds)) {
			// 根据CodId查询对应的
			List<CODEntity> codEntitySet = this.codCommonService
					.queryByIds(codIds);
			// 如果代收货款的状态为未审核，则继续，否则抛出异常
			for (CODEntity item : codEntitySet) {
				if (item.getLandStatus()
						.equals(SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE)) {
					throw new SettlementException(item.getWaybillNo()
							+ "已经审核，不能再次进行审核");
				}
			}
			
			LOGGER.info("审核快递代理代收货款开始!");
			
			// 执行审核
			this.billPayCODService.auditLandBillCOD(codIds, currentInfo);
			
			LOGGER.info("审核快递代理代收货款结束");
		} else {
			throw new SettlementException("内部参数为空");
		}

	}

	/**
	 * 失效快递代理代收货款记录
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 下午8:23:06
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ILandBillPaidCODService#reverseEffectiveBillCOD(java.lang.String,
	 *      com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Transactional
	@Override
	public void reverseEffectiveBillCOD(List<String> codIds,
			CurrentInfo currentInfo) {

		// 检查参数
		if (CollectionUtils.isNotEmpty(codIds)) {
			// 根据CodId查询对应的
			List<CODEntity> codEntitySet = this.codCommonService
					.queryByIds(codIds);
			// 如果代收货款的状态为未审核，则继续，否则抛出异常
			for (CODEntity item : codEntitySet) {
				if (item.getLandStatus()
						.equals(SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT)) {
					throw new SettlementException(item.getWaybillNo()
							+ "已经反审核，不能再次进行反审核");
				}
			}

			LOGGER.info("反审核快递代理代收货款开始!");
			
			// 执行审核
			this.billPayCODService.antiAuditLandBillCOD(codIds, currentInfo);
			
			LOGGER.info("反审核快递代理代收货款结束!");
		} else {
			throw new SettlementException("内部参数为空");
		}

	}

	/**
	 * 设置默认查询条件
	 * 
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-7 上午10:55:31
	 */
	private LandBillPaidCODQueryDto getDefaultQueryDto() {

		LandBillPaidCODQueryDto dto = new LandBillPaidCODQueryDto();
		// 默认值
		// 未核销的应付单
		dto.setVerifyAmount(BigDecimal.ZERO);
		// 状态为有效
		dto.setActive(FossConstants.ACTIVE);
		
		/*
		 * 3.60特惠件 快递代理新增加的一种三级产品
		 * 
		 * 10562 2014年8月6日 上午11:27:49
		 */
		dto.setProductCodeList(SettlementUtil.createPackageProductCodeList());
		
		// 应付单 等于 代收货款应付单BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
		dto.setBillPayableType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		List<String> billTypes=new ArrayList<String>();
		//billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE);
		billTypes.add(SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__LAND_STOWAGE_AGENCY_COD);
		dto.setBillReceivableTypes(billTypes);//设置代收货款类型
		dto.setExternalNodeType(SettlementConstants.EXTERNAL_NODE_TYPE_LDP); // 快递代理网点

		return dto;
	}
	
	/**
	 * @param landBillPaidCodQueryDao
	 */
	public void setLandBillPaidCodQueryDao(
			ILandBillPaidCODQueryDao landBillPaidCodQueryDao) {
		this.landBillPaidCodQueryDao = landBillPaidCodQueryDao;
	}


	
	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * @param billPayCODService
	 */
	public void setBillPayCODService(IBillPayCODService billPayCODService) {
		this.billPayCODService = billPayCODService;
	}

}
