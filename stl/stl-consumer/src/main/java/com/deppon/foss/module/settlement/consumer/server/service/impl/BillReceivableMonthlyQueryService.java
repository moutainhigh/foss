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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/AirBillPaidCODService.java
 * 
 * FILE NAME        	: AirBillPaidCODService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 用例描述
 * 空运总调对账员对已经签收的空运代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	空运运单已经签收
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
 * 9、	查询代收货款信息时，默认运单的产品类型为精准空运
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
 * 空运总调对账员对已经签收的空运代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	空运运单已经签收
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
 * 9、	查询代收货款信息时，默认运单的产品类型为精准空运
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
 * 空运总调对账员对已经签收的空运代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	空运运单已经签收
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
 * 9、	查询代收货款信息时，默认运单的产品类型为精准空运
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
 * 空运总调对账员对已经签收的空运代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	空运运单已经签收
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
 * 9、	查询代收货款信息时，默认运单的产品类型为精准空运
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
 * 空运总调对账员对已经签收的空运代收货款运单进行审核和反审核，以便资金部退款。
 * 
 * 前置条件
 * 审核：
 * 1、	空运运单已经签收
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
 * 9、	查询代收货款信息时，默认运单的产品类型为精准空运
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.IBillReceivableMonthlyQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto;
import com.deppon.foss.util.UUIDUtils;

/**
 * 应收单月结查询服务
 * @author foss-youkun
 * @date 2016/1/8
 */
public class BillReceivableMonthlyQueryService implements IBillReceivableMonthlyQueryService {

	private static final Logger LOGGER = LogManager.getLogger(BillReceivableMonthlyQueryService.class);

    //查询月结应收单dao
    private IBillReceivableMonthlyQueryDao billReceivableMonthlyQueryDao;

    /**
     * 根据条件查询月结应收单
     * @param offset
     * @param start
     * @param dto
     * @return
     */
    public List<BillReceivableEntity> queryBillReceivableByData(int offset, int start, BillReceivableDto dto)throws SettlementException {
    	validateDate(dto);
    	//将期间转化为时间
    	//如果期间为空返回原来的dto
    	BillReceivableDto billReceivableDto = periodToDate(dto);
        //声明返回结果
        List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
        try{
            list=(List<BillReceivableEntity>)billReceivableMonthlyQueryDao.queryBillReceivableByData(offset,start,billReceivableDto);
        }catch(SettlementException e){
            LOGGER.error("查询失败:"+e.getMessage());
            throw new SettlementException("查询失败");
        }

        return list;
    }

    /**
	 * <p>将实体中的期间变了转换为当月1号0点0分0秒到下月的1号0点0分0秒</p> 
	 * @author 273272 唐俊
	 * @date 2016-1-29 下午1:44:34
	 * @param dto
	 * @return
	 * @see
	 */
	private BillReceivableDto periodToDate(BillReceivableDto dto) {
		BillReceivableDto billReceivableDto = dto;
		//分离期间的年份和月份
		String  period = billReceivableDto.getPeriod();
		if("".equals(period) || null == period ){
			return dto;
		}
		String year = period.substring(0, NumberConstants.NUMBER_4);
		String month = period.substring(NumberConstants.NUMBER_4, NumberConstants.NUMBER_6);
		//格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR,Integer.parseInt(year));
		//设置时间为1号
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //设置时分秒为0时0分0秒
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
		try {
			//设置当月的1号0点0分0秒
			cal.set(Calendar.MONTH, Integer.parseInt(month)-1);
			billReceivableDto.setStartDate(sdf.parse(sdf.format(cal.getTime())));
			//设置下月的1号0点0分0秒
			cal.set(Calendar.MONTH, Integer.parseInt(month));
			billReceivableDto.setEndDate(sdf.parse(sdf.format(cal.getTime())));
		} catch (ParseException e) {
			 LOGGER.error("期间转化时间失败:"+e.getMessage());
			 throw new SettlementException("期间转化时间失败:"+e.getMessage());
		}
		return billReceivableDto;
	}

	/**
     * 根据条件查询月结应收单总数量
     * @param dto
     * @return
     */
    public Long countBillReceivableByData(BillReceivableDto dto)throws SettlementException {
    	validateDate(dto);
    	//将期间转化为时间
    	//如果期间为空返回原来的dto
    	BillReceivableDto billReceivableDto = periodToDate(dto);
        //定义返回的结果
        Long count =0L;
        try{
            count = billReceivableMonthlyQueryDao.countBillReceivableByData(billReceivableDto);
        }catch(SettlementException e){
            LOGGER.error("查询总记录失败:"+e.getMessage());
            throw new SettlementException("查询总记录失败:"+e.getMessage());
        }
        return count;
    }

    /**
     * 查询总月结应收单的总金额
     * @param dto
     * @return
     * @throws SettlementException
     */
    public String amountBillReceivableByParam(BillReceivableDto dto) throws SettlementException {
        String amount = null;
        try{
            amount=billReceivableMonthlyQueryDao.amountBillReceivableByParam(dto);
        }catch(SettlementException e){
            LOGGER.error("查询月结总金额失败:"+e.getMessage());
            throw  new SettlementException("查询月结总金额失败:"+e.getMessage());
        }
        return amount;
    }

    /**
     *查询月结为始发提成,委托派费的应收单
     * @param dto
     * @return
     * @throws SettlementException
     */
    public List<BillReceivableEntity> queryBillRecivableByList(BillReceivableDto dto,int start,int limit) throws SettlementException {
        //声明返回结果
        List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
        /**
         * 判断是根据单号查询还是合伙人制作查询
         * 如果是合伙人制作时间不为空按照合伙人制作来查询
         */
        if(dto.getStartDate()!=null ){
            validateDate2(dto);
        }else{
            if(dto.getWaybillNo()!=null) {
                dto.setWaybillNoList(conventList(dto.getWaybillNo()));
            }
        }
        try{
            list=billReceivableMonthlyQueryDao.queryBillRecivableByList(dto, start, limit);
        }catch(SettlementException e){
            LOGGER.error("查询失败:"+e.getMessage());
            throw new SettlementException("查询失败:"+e.getMessage());
        }
        if(list==null){
            list = new ArrayList<BillReceivableEntity>();
        }
        return list;
    }

    /**
     * 查询月结为始发提成,委托派费的应收单(不分页的)
     * @param dto
     * @return
     * @throws SettlementException
     */
    public List<BillReceivableEntity> queryNotPageBillRecivableByList(BillReceivableDto dto) throws SettlementException {
        //声明返回结果
        List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
        /**
         * 判断是根据单号查询还是合伙人制作查询
         * 如果是合伙人制作时间不为空按照合伙人制作来查询
         */
        if(dto.getStartDate()!=null &&!("").equals(dto.getStartDate())){
            validateDate2(dto);
        }else{
            if(dto.getWaybillNo()!=null) {
                dto.setWaybillNoList(conventList(dto.getWaybillNo()));
            }
        }
        try{
            list=billReceivableMonthlyQueryDao.queryNotPageBillRecivableByList(dto);
        }catch(SettlementException e){
            LOGGER.error("查询失败:"+e.getMessage());
            throw new SettlementException("查询失败:"+e.getMessage());
        }
        if(list==null){
            list = new ArrayList<BillReceivableEntity>();
        }
        return list;
    }

    /**
     * 查询月结为始发提成,委托派费的应收单总条数
     * @param dto
     * @return
     * @throws SettlementException
     */
    public Long countBillRecivableByList(BillReceivableDto dto) throws SettlementException {
        /**
         * 判断是根据单号查询还是合伙人制作查询
         * 如果是合伙人制作时间不为空按照合伙人制作来查询
         */
        if(dto.getWaybillNo()!=null) {
            dto.setWaybillNoList(conventList(dto.getWaybillNo()));
        }
        //定义返回的结果
        Long count =0L;
        try{
            count = billReceivableMonthlyQueryDao.countBillRecivableByList(dto);
        }catch(SettlementException e){
            LOGGER.error("查询总记录失败:"+e.getMessage());
            throw new SettlementException("查询总记录失败:"+e.getMessage());
        }
        return count;
    }
    public void setBillReceivableMonthlyQueryDao(IBillReceivableMonthlyQueryDao billReceivableMonthlyQueryDao) {
        this.billReceivableMonthlyQueryDao = billReceivableMonthlyQueryDao;
    }

    //数据校验
    private void validateDate(BillReceivableDto dto){
        // 查询单号的参数不能为空
        if (null == dto) {
            // Dto对象为空则抛出异常
            throw new SettlementException("查询条件不能为空");
        }
        if("".equals(dto.getPeriod()) || null == dto.getPeriod()){
        	 // Dto对象为空则抛出异常
            throw new SettlementException("查询期间为空!");
		}
    }
    
    //数据校验
    private void validateDate2(BillReceivableDto dto) {
        if(dto ==null) {
            throw new SettlementException("查询条件不能为空");
        } else if(dto.getStartDate() == null) {
            throw new SettlementException("开始日期不能为空");
        } else if(dto.getEndDate()==null) {
            throw new SettlementException("结束日期不能为空");
        } else {
            if(dto.getStartDate() != null && dto.getEndDate() != null) {
                Date startDate = DateUtils.truncate(dto.getStartDate(), SettlementReportNumber.THIRTEEN);
                Date endDate = DateUtils.truncate(dto.getEndDate(), SettlementReportNumber.THIRTEEN);
                if(startDate.after(endDate)) {
                    throw new SettlementException("开始日期大于结束日期！");
                }
            }

            dto.setEndDate(com.deppon.foss.util.DateUtils.convert(com.deppon.foss.util.DateUtils.addDay(dto.getEndDate(), 1), "yyyy-MM-dd"));
        }
    }

    //String转list
    public List<String> conventList(String str){
        List<String> list = new ArrayList<String>();
        if(str.length()>0){
            String s[] =str.split(",");
            for(int i=0;i<s.length;i++){
                list.add(s[i]);
            }
        }
        return list;
    }

	/** 
	 * <p>查询所有的月结应收单</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:20:01
	 * @param dto
	 * @return
	 * @throws SettlementException 
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService#queryBillReceivableByData(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public List<BillReceivableEntity> queryBillReceivableByData(
			BillReceivableDto dto) throws SettlementException {
		 //声明返回结果
        List<BillReceivableEntity> list = new ArrayList<BillReceivableEntity>();
        try{
            list=(List<BillReceivableEntity>)billReceivableMonthlyQueryDao.queryBillReceivableByData(dto);
        }catch(SettlementException e){
            LOGGER.error("查询失败:"+e.getMessage());
            throw new SettlementException("查询失败");
        }
        return list;
	}

	/** 
	 * <p>批量插入合伙人月结应收账单数据</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:50:29
	 * @param tempList 
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService#insertBillReceivable(java.util.List)
	 */
	@Override
	public void insertBillReceivable(List<BillReceivableEntity> list) {
		//批量插入数据库
		int count = 0;
		List<BillReceivableEntity>  tempList = new ArrayList<BillReceivableEntity>();
		for (int i = 0; i < list.size(); i ++) {
			count ++;
			list.get(i).setWaybillId(UUIDUtils.getUUID());
			tempList.add(list.get(i));
			if(count == SettlementReportNumber.THREE_HUNDRED || i == list.size() - 1){
				insert(tempList);
				tempList.clear();
				count = 0;
			}
		}
	}
	/**
	 * 
	 * <p>插入合伙人月结应收账单数据</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-17 上午10:53:02
	 * @param tempList
	 * @see
	 */
	public void insert(List<BillReceivableEntity> list) {
		billReceivableMonthlyQueryDao.insertBillReceivable(list);
	}

	/** 
	 * <p>判断用户是否能查询合伙人营业部月结金额</p> 
	 * @author 273272 唐俊
	 * @date 2016-2-22 上午8:33:53
	 * @param dto          
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivableMonthlyQueryService#judgeDepartmentPermission(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillReceivableDto)
	 */
	@Override
	public void judgeDepartmentPermission(BillReceivableDto dto) {
		
	}
	/**
	 * 查询所有的月结应收单用于导出
	 */
	@Override
	public List<BillReceivableEntity> queryBillReceivableEntityListForExport(
			BillReceivableDto dto) throws SettlementException {
		List<BillReceivableEntity> billReceivableEntityList = new ArrayList<BillReceivableEntity>();
		BillReceivableDto billReceivableDto = periodToDate(dto);
	    try {
	      billReceivableEntityList = this.billReceivableMonthlyQueryDao.queryBillReceivableEntityListForExport(billReceivableDto);
	    }
	    catch (SettlementException e) {
	      LOGGER.error("查询失败" + e.getMessage());
	      throw new SettlementException("查询失败" + e.getMessage());
	    }
	    return billReceivableEntityList;
	}
}
