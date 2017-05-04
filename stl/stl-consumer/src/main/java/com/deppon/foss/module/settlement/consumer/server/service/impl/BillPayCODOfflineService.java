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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODOfflineService.java
 * 
 * FILE NAME        	: BillPayCODOfflineService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 *  @﻿修订记录 
 * 日期   修订内容   修订人员   版本号 
 * 2012-05-25   创建版   毛建强  V0.1
 * 2012-07-11   修订版  毛建强  V0.5
 * 2012-07-23   修订版
 * 1、  增加代收货款线下导出EXCEL后，资金部确认汇款成功及失败的操作流程
 * 2、 同时增加审核汇款失败和反汇款成功单据的操作流程  毛建强  V0.5
 * 2012-07-25   版本升级  毛建强  V0.9
 * 2012-10-12  1、  增加扩展规则："如果反汇款成功，且反汇款成功原因为空，则终止程序，弹出提示"
 * 2、  增加扩展事件：” 当选择代收货款的状态不是"反汇款成功"或"退款失败申请"，则终止程序，弹出提示”
 * 3、  修改扩展事件2b—代收货款的状态为冻结状态
 * 修改引用错误  毛建强  2012-10-12
 * 2012-10-24  资金部审核后批量退回代收货款，增加批次状态“提交”  保轶  V1.2
 * 2012-11-09  查询显示代收货款信息（输出）增加“代收货款批次号”
 *   保轶  V1.21
 * 2012-12-29  付款失败审核通过后，增加付款冲应付反核销  曾斌文  V1.22
 * 
 * 
 * 1.  SUC-361-汇代收货款给客户
 * 1.1  相关业务用例
 * BUC_FOSS_4.7.50.30_080给客户汇款代收货款
 * 
 * 1.2  用例描述
 * 线上：
 * 资金部出纳选择资金部冻结的单据进行“退款申请”，
 * 会调用银企接口将代收货款信息传递过去，但不生成付款单（也不生成核销单），
 * 并且将代收货款状态修改为“退款中”，代收货款批次状态为“提交”；
 * 资金部整批审核通过后，代收货款批次状态修改为“审核通过”,
 * 代收货款单状态不变；审核发现问题整批退回，
 * 代收货款批次状态修改为“退回”，代收货款单状态回到申请前状态“已冻结”。
 * 参见SUC-714-接收代收货款汇款状态、SUC-76-生成应付单附件dp-foss-结算业务用例-代收货款综合查询。
 * 
 * 线下：
 * 1、资金部出纳选择资金部冻结的单据进行“汇款导出”操作，
 * 会自动导出Excel记录，生成付款单、核销单同时核销导出的代收货款应付单，
 * 将代收货款单据更新为“退款中”
 * 
 * 2、资金部可以将“汇款导出”的单据根据汇款情况进行对应操作。
 * 如果是成功，则点“汇款成功”按钮，更新代收货款状态为“已退款”，
 * 反之如果汇款失败，则点击“汇款失败”按钮，更新代收货款状态为“退款失败申请”。
 * 如果为成功转失败，则点击“反汇款成功”按钮，则更新代收货款状态为“反汇款成功”。
 * 3、针对汇款失败和反汇款成功单据，资金部审核人员审核其退款情况真实性，
 * 
 * 点击“通过”和“退回”按钮。如果点击“通过”，则更新代收货款状态为“退款失败”，
 * 同时红冲核销单、红冲付款单、更新代收货款应付单。如果点击“退回”，
 * 则更新代收货款状态为“退款中”（退款失败申请）或“已退款”（反汇款成功）。
 * 
 * 1.3  用例条件
 * 条件类型  描述  引用系统用例
 * 前置条件  冻结代收货款  
 * 后置条件  线上：
 * 1、调用银企接口，将代收货
 *    款数据传递过去
 * 2、修改代收货款状态为“退款中”，批次状态为“提交”
 * 
 * 线下：
 * 1、修改代收货款状态为“退款中”，同时生成付款单核销对应的代收货款应付单
 * 
 * 2、 汇款成功，更新代收货款应付单为“已退款”，
 * 汇款失败或反汇款成功更新代收货款状态为“退款失败申请”或“反汇款成功”
 * 
 * 3、 审核通过，则更新代收货款单据未“退款失败”，
 * 红冲核销单、红冲付款单、更新代收货款应付单，审核不通过，
 * 则更新代收货款状态为“退款中”（退款失败申请）或“已退款”（反汇款成功）  
 * 
 * 
 * 1.4  操作用户角色
 * 操作用户  描述
 * 资金部出纳  进行代收货款冻结、取消冻结、汇款申请和汇款导出、汇款成功、汇款失败、反汇款成功等操作
 * 财务部会计  进行审核和退回汇款失败的资金部单据
 * 
 * 1.5  界面要求
 * 
 * 1.5.1  表现方式
 * 
 * Web
 * 
 * 
 * 1.5.2  界面原型
 * 
 * 资金部汇款导出界面：
 *  
 * “批次查询”链接到代收货款批次查询界面（SUC-76-生成应付单附件dp-foss-结算业务用例-代收货款综合查询）。
 * 
 * 汇款确认：
 *  
 * 汇款失败或反汇款成功原因
 *  
 * 汇款失败审核：
 *  
 * 1.5.3  界面描述
 * 页面初始化组件描述：
 * 1、资金部汇款导出页面：
 *   初始化退款类型数据字典到退款类型下拉列表中，默认显示“即日退”
 *   截止签收日期默认为当前日期的下一天日期00:00:00秒 
 * 2、汇款确认页面：
 *   代收货款状态默认为退款中
 *   起始时间为当前服务器时间的前一天00:00:00,结束时间为当前服务器时间。起止时间间隔不超过7天
 *   反汇款成功按钮默认为灰色不可编辑
 * 2、  汇款失败审核界面：
 *   默认查询条件选择查询所有
 *   输入文本域默认为灰色不可编辑
 * 
 * 
 * 页面输入组件描述
 * 1、资金部汇款导出页面：
 * 单号：运单号集合，最多输入10个， 以“逗号”隔开 
 * 截止签收日期：运单签收录入日期。不能大于当前日期
 * 退款类型:代收货款退款类型
 *   三日退（审核退）
 *   即日退
 * 账户性质：代收货款收款账号的性质
 *   对公
 *   对私
 * 开户行:银行账号所属的开户银行。该控件为公共组件，
 * 可以选择多个开户行，显示开户行名称，以逗号隔开。
 * 也可手动输入开户行名称，以逗号隔开。
 * 
 * 3、  汇款确认页面：
 * 单号：运单号集合，最多输入10个， 以“逗号”隔开 
 * 批次号：资金部汇款导出的批次号
 * 代收货款状态：当前代收货款的状态
 *   退款中
 *   已退款
 * 付款人：代收货款汇款导出操作人
 * 起始时间：代收货款汇款导出查询的起始时间
 * 结束时间：代收货款汇款导出查询的结束时间
 * 
 * 
 * 4、  汇款失败审核界面：
 * 单号：运单号集合，最多输入10个， 以“逗号”隔开 
 * 
 * 页面表格组件描述：
 * 1、资金部汇款导出页面：
 *   该页面默认一页显示，不能进行分页
 *   表格最下面显示合计的总条数，总金额，冻结条数和冻结总金额
 * 2、汇款确认页面：
 *     该页面默认进行分页显示，每页显示默认为 20,30,50,100,500
 * 3、汇款失败审核界面
 * 该页面默认进行分页显示，每页显示默认为 20,30,50,100,500
 * 
 * 页面提供的钮控件描述：
 * 1、资金部汇款导出页面
 *   查询：查询符合退款条件的代收货款列表
 *   重置：将查询条件设置到初始化情况
 *   冻结：冻结代收货款信息，使营业部不能进行操作。 
 *   取消冻结：取消代收货款的资金部冻结状态，营业部能够查询到该条代收货款信息，从而进行操作。
 *   退款申请：选中冻结单据，点击退款申请，会调用银企接口，将数据传递过去进行退款。与汇款导出互斥，同一批数据，点击了退款申请，则不能进行汇款导出。
 *   汇款导出：将冻结单据导出Excel进行汇款，与退款申请互斥。点击汇款导出，则不能再进行退款申请。
 *   全选冻结单据：勾选全部的冻结状态的单据，从而方便其进行退款申请或汇款导出。
 * 
 * 2、汇款确认页面
 *   汇款成功：资金部汇款成功，则点击按钮，修改代收货款状态为“已退款”
 *   汇款失败：资金部汇款失败，则点击按钮，修改代收货款状态为“退款失败申请”
 *   反汇款成功：资金部汇款成功转失败，则点击按钮，修改代收货款状态为“反汇款成功”
 * 
 * 3、汇款失败审核界面
 *   审核通过：资金部对于汇款失败和汇款成功转失败的单据，如果确认失败，则点击该 
 * 按钮，修改代收货款状态为“退款失败”
 *   退回：资金部对于汇款失败和汇款成功转失败的单据，如果确认其没有失败，则点击
 * 该按钮，修改代收货款状态为“退款中”或“已退款”
 * 
 * 1.6  操作步骤
 * 1.6.1  退款申请
 * 
 * 1  查询代收货款    具体规则参见《冻结代收货款》用例中查询操作
 * 2  勾选查询出来的代收货款数据（可批量）  代收货款信息  
 * 3  如果点击退款申请按钮，则传递数据到银企进行退款  代收货款信息  1、  校验是否选中单据
 * 
 * 扩展事件参见2a
 * 
 * 2、  判断当前选中的代收货款冻结状态是否是“已冻结”，若非已冻结，则弹出异常提示
 * 扩展事件参见2b
 * 
 * 3、  根据银行划分汇款基础资料，将所选的明细根据银行范围划分成不同集合传递给银企
 * 4、  调用银企接口，将分好的不同银行的汇款明细集合数据传递给银企如果返回接受状态为false，
 * 		则终止程序，弹出异常提示
 * 
 * 扩展事件参见3a
 * 业务规则参见SR1
 * 
 * 5、  更新代收货款状态为“退款中”，同时更新批次号为最新批次编号，批次状态为“已提交”
 * 业务规则参见SR2
 * 
 * 6、  批量生成代收货款付款单
 * 业务规则参见SR3
 * 
 * 7、  添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 4  如果点击汇款导出按钮，则导出Excel人工去进行退款，不经过银企系统    1、  校验是否选中单据
 * 扩展事件参见2a
 * 
 * 2、  判断当前选中代收货款冻结状态是否是“已冻结”，若非资金部冻结，则弹出异常提示
 * 扩展事件参见2b
 * 
 * 3、  更新代收货款状态为“退款中”，同时更新批次号为最新批次编号
 * 业务规则参见SR2
 * 
 * 4、  批量生成代收货款付款单，并核销应付单，生成核销单（自动核销）
 * 业务规则参见SR3
 * 
 * 5、  导出Excel
 * Excel格式参见规则SR1
 * 
 * 6、  添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 1.6.2  汇款确认
 * 
 * 5  如果输入运单号查询  运单号集合  
 * 1、  业务规则参见SR4
 * 2、  扩展事件参见5a，5b
 * 
 * 6  如果没有输入单号，按照其他所选条件进行查询  批次号，代收货款状态，付款人、起始时间、结束时间  
 * 1、业务规则参见SR5
 * 2、扩展事件参见6a,5b
 * 
 * 7  点击汇款成功按钮  无  
 * 1、 校验是否选中单据
 *     扩展事件参见2a
 * 2、 校验所选单据的代收货款状态  
 * 是否都为“退款中”
 * 扩展事件参见7a
 * 
 * 3、  更新选中单据的代收货款状态为“已退款”
 * 
 * 4、 添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 8  点击汇款失败按钮,弹出界面，并输入汇款失败原因，点击确认按钮  无  
 * 1、  校验是否选中单据
 * 扩展事件参见2a
 * 
 * 2、  校验所选单据的代收货款状态  
 * 是否都为“退款中”
 * 扩展事件参见8a
 * 
 * 3、  校验汇款失败原因是否为空，如果为空，则弹出异常提示
 * 扩展事件参见8b
 * 
 * 4、更新选中单据的代收货款状态
 * 为“退款失败申请”，汇款失
 * 败原因为传入汇款失败原因
 * 
 * 5、添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 9  点击反汇款成功按钮  无  
 * 1、  校验是否选中单据
 * 扩展事件参见2a
 * 
 * 2、  校验所选单据的代收货款状态  是否都为“已退款”
 * 扩展事件参见9a
 * 
 * 3、  校验汇款失败原因是否为空，如果为空，则弹出异常提示
 * 扩展事件参见8b
 * 
 * 4、更新选中单据的代收货款状态
 * 为“反汇款成功”，汇款失
 * 败原因为传入汇款失败原因
 * 
 * 5、 添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 1.6.3  汇款失败审核
 * 
 * 10  当选择“查询所有”进行查询  无  查询所有符合审核条件单据
 * 1、  业务规则参见SR6
 * 2、 扩展事件参见5b
 * 
 * 11  当选择单号，并输入单号进行查询  运单号集合  
 * 1、  业务规则参见SR7
 * 2、  扩展事件参见5b
 * 
 * 12  点击审核通过按钮  无  
 * 1、 校验是否选中单据
 * 扩展事件参见2a
 * 
 * 2、 校验所选单据的代收货款状态  
 * 是否都为“反汇款成功”或“退款失败申请”
 * 
 * 扩展事件参见12a
 * 
 * 3、  更新选中单据的代收货款状态
 * 为“退款失败”
 * 
 * 4、  添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 5、  红冲付款单、红冲核销单，更新代收货款应付单
 * 
 * 13  点击退回按钮  无  
 * 1、 校验是否选中单据
 * 扩展事件参见2a
 * 2、 校验所选单据的代收货款状态  
 * 是否都为“反汇款成功”或“退款失败申请”
 * 扩展事件参见12a
 * 
 * 4、 更新选中单据的代收货款状态为“退款中”（退款失败申请）或“已退款”（反汇款成功）
 * 
 * 5、  添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 
 * 扩展事件
 * 序号  扩展事件  相关数据  备注
 * 2a  当没有选中数据时，弹出提示  提示信息  
 * 请至少选中一条数据，进行操作！
 * 
 * 2b  当选择代收货款的冻结状态不是“已冻结”，则终止程序，弹出提示  提示信息  
 * “代收货款冻结状态必须为已冻结，才能进行退款申请”
 * 
 * 3a  将数据传输给银企系统，如果返回接受状态为“false”，则终止程序，弹出提示  提示信息  
 * 返回错误信息:“数据未成功传递给银企系统，可能是对方系统出了问题，请联系IT服务中心进行处理！”
 * 
 * 5a  校验输入运单号有误，则直接返回，并弹出提示  提示信息  
 * 1、  如果输入运单号超过10个，则弹出提示：“运单号不能超过10个”
 * 
 * 2、  如果输入运单号校验有误，
 * 则弹出提示：“输入运单号必须为8-10为数字”
 * 
 * 5b  如果没查询到数据，则弹出提示  提示信息  
 * 1、弹出提示信息：“没有查询到 符合条件的数据”
 * 
 * 6a  校验传入起始日期和结束日期有误，则直接返回并弹出提示  提示信息  
 * 
 * 1、如果传入的起始日期或结束日期为空，
 * 则直接弹出提示：   “起始日期和结束日期不能为空”
 * 
 * 2 、如果传入的起始日期和结束日期的间隔大于7天，
 * 则直接弹出提示：“起始日期和结束日期的间隔不能大于7天”
 * 
 * 7a  当选择代收货款的状态不是“退款中”，则终止程序，弹出提示  提示信息  
 * 弹出错误信息：“代收货款状态必须为退款中，才能进行汇款成功操作”
 * 
 * 8a  当选择代收货款的状态不是“退款中”，则终止程序，弹出提示  
 * 提示信息  弹出错误信息：“代收货款状态必须为退款中，才能进行退款失败操作”
 * 
 * 8b  如果汇款失败或反汇款成功，且原因为空，则终止程序，弹出提示  
 * 提示信息  弹出错误信息：“请填写汇款失败或反汇款成功的原因”
 * 
 * 9a  当选择代收货款的状态不是“已退款”，则终止程序，弹出提示  
 * 提示信息  弹出错误信息：“代收货款状态必须为已退款，才能进行反汇款成功操作”
 * 
 * 12a 如果代收货款状态不为：“反汇款成功”或“退款失败申请”，
 * 则终止程序直接弹出提示  提示信息  弹出错误信息：“代收货款状态必须为退款失败申请或反汇款成功状态，
 * 才能进行操作！”
 * 
 * 1.7  业务规则
 * 序号  描述
 * 
 * SR1  数据传输格式：
 * 类别：代收货款类别
 * 
 * 所属子公司：代收货款应付单部门所对应的子公司
 * 出发部门：代收货款应付单的应付部门
 * 运单号：代收货款的运单号
 * 收款人：代收货款账号的开户人
 * 金额：应退代收货款金额
 * 账号：退款账号
 * 开户行：账号所属银行
 * 省：开户行所在省份
 * 市：开户行所在城市
 * 支行：开户行的详细名称
 * 对公对私标志：账号的类别
 * 手机号码：收款人的手机号码
 * 签收日期：有效版本财务单据对应的签收日期
 * 银行行号：开户行的行号
 * 
 * 汇款申请时间：点击“退款申请”或“汇款导出”操作时对应的服务器时间
 * 批次号：当前时间截取到日期01/02八位流水号（此处01代表即日退，02为三日退或审核退） 如：201207030100000001
 * 
 * SR2  更新代收货款状态为“退款中”，同时更新批次号为最新批次编号，批次状态为“已提交”，更新条件：
 * 1、代收货款状态为“资金部冻结”
 * SR3  付款单转换规则：
 * 编码：自动生成，编码规则： FK2+8位流水号（不足补0）
 * 付款部门：资金部
 * 应付部门: 应付单的应付部门
 * 客户编码：应付单挂账客户编码
 * 付款类型：退代收货款
 * 付款金额：应付单的未核销金额
 * 来源单据：应付单编号
 * 审核状态：未审核
 * 付款方式：电汇
 * 业务日期：当前服务器时间
 * 记账日期：当前服务器时间
 * 审核日期：无
 * 是否有效：是
 * 是否红单：否
 * 创建人：当前登录人
 * 
 * 创建时间:当前时间
 * SR4  1、  按单号查询，则忽略其他查询条件
 * 2、  查询代收货款状态为“退款中”和“已退款”单据
 * SR5  1、 起始时间和结束时间不能为空，且起始时间不能大于结束时间
 * 2、 起始时间和结束时间间隔不能大于7天
 * 3、 代收货款汇款导出时间必须在起始时间和结束时间范围之内
 * SR6  1、代收货款状态为“退款失败申请”或“反汇款成功”的代收货款单据
 * SR7  1、  运单号不能为空且不能超过10个
 * 2、  代收货款状态为“退款失败申请”或“反汇款成功”的代收货款单据
 * 
 * 1.8  数据元素
 * 1.8.1  汇款导出页面查询条件（输入）
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填  
 * 备注
 * 
 *   单号  运单号集合  文本  请输入正确单号：8-10位数字  10  否  最多10个，逗号/换行分隔
 * 截止签收日期  运单签收日期  日期  运单签收日期  30  否  无
 * 退款类型  退款类型  下拉框  无  10  否  无
 * 开户行  开户银行  公共组件可多选  无  30  否  无
 * 账户性质  汇款账号的性质：“对公”还是“对私”  下拉框  无  10  否  无
 * 
 * 1.8.2  汇款确认界面查询条件（输入）
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填  
 * 备注
 * 
 *   运单号  运单号集合  文本  请输入正确单号：8-10位数字  10  否  最多10个，逗号隔开
 * 批次号  代收货款的批次号  文本  无  30  否  无
 * 代收货款状态  代收货款的状态  文本  无  10  否  退款中或已退款
 * 付款人  代收货款汇款导出操作人编码  公共组件  无  参见统一标准  否  无
 * 起始时间  查询起始时间  时间  无  参见统一标准  否  无
 * 结束时间  查询的结束时间  时间  无  参见统一标准  否  
 * 
 * 1.8.3  汇款失败审核界面查询条件（输入）
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填  
 * 备注
 * 
 *   运单号  运单号集合  文本  请输入正确单号：8-10位数字  10  否  最多10个，逗号隔开
 * 
 * 1.8.4  查询显示代收货款信息（输出）
 * 字段名称   说明   输出限制  输入项提示文本  长度  是否必填  备注
 * 代收货款类型  代收货款退款类型  无  无  20  是  代收货款类型
 * 运单号  运单单号  无  无  10  是  运单单号
 * 金额  代收货款退款金额  无  无  20  是  代收货款要退款的金额
 * 应付部门  代收货款所属部门  无  无  参照统一标准  是  等于当前登录部门
 * 冻结状态  资金部冻结状态  无  无  20  是  无
 * 收款人  代收货款收款人姓名  无  无  20  是  无
 * 账号  收款账号  无  无  50  是  无
 * 开户行  收款账号所属银行  无  无  20  是  无
 * 省份  收款账号所属银行所在的省份  无  无  20  是  无
 * 城市  收款账号所属银行所在的城市  无  无  20  是  无
 * 支行  收款账号所属银行的详细地址  无  无  50  是  无
 * 对公对私标志  账号的对公对私标志  无  无  50  是  无
 * 签收时间  运单签收时间  无  无  50  是  无
 * 手机号  收款人手机号  无  无  50  是  无
 * 银行行号  开户银行的行号  无  无  50  是  无
 * 所属子公司  该运单所属子公司  无  无  50  是  无
 * 汇款导出时间  代收货款退款申请或汇款导出的时间  无  无  参见统一标准  是  无
 * 汇款导出人  代收货款退款申请或汇款导出的操作人  无  无  参见统一标准  是  无
 * 代收货款批次号  代收货款批次申请退款的编号  无  无  30  否  系统自动获取
 * 
 * 1.8.5  更新代收货款数据（汇款申请或汇款导出输出）
 * 字段名称   说明   输出限制  输出项提示文本  长度  是否必填  备注
 * 代收货款状态  代收货款的退款状态  枚举  无  20  是  退款中
 * 代收货款批次号  代收货款批次申请退款的编号  文本  无  30  是  系统自动获取
 * 退款申请时间  当前服务器时间  文本  无  参照统一标准  是  系统自动获取
 * 退款申请人  当前登录人  文本  无  50  是  系统自动获取
 * 
 * 1.8.6  生成付款单（汇款导出输出）
 * 字段名称   说明   输出限制  长度  是否必填  备注
 * 单据编号  自动生成  无  10  无  FK2+8位流水号（不足补0）
 * 审核状态  付款单审核状态  无  1  无  可选值为：已审核、未审核
 * 版本号  付款单版本号  无  1  无  
 * 是否有效版本  有效标志  无  1  无  可选值为：是、否
 * 是否红单  红单标志  无  1  无  可选值为：是、否
 * 付款类型  付款类型  无  20  无  可选值为：退预收、退代收货款，此处为退代收货款
 * 付款部门  实际付款部门  无  40  无  资金部
 * 应付部门  付款单部门  无  40  无  取应付单部门
 * 客户名称  付款客户名称  无  40  无  取应付单挂账客户名称
 * 客户编码  付款客户编码  无  40  无  取应付单挂账客户名称
 * 业务日期  首次付款日期，红冲时不变  无  6  无  首次付款日期，红冲时不变
 * 记账日期  付款单生成日期  无  6  无  当前日期
 * 付款状态  付款单的付款状态  无  10  无  可选值为：未付款、付款中、已付款、付款失败，此处应为付款中
 * 付款金额  付款单金额  无  30  无  应付单的未核销金额
 * 来源类型  单据来源  无  10  无  可选值为：手动、自动，此处为自动
 * 来源单号  应付单的来源单号  无  10  无  
 * 创建时间  创建时间  无  6  无  当前时间
 * 创建人  单据创建时间  无  10  无  当前登录人
 * 付款方式  付款单的付款方式  无  10  无  电汇
 * 备注  备注  无  200  无  无
 * 
 * 
 * 
 * 1.8.7  更新应付单（审核通过输出）
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填  备注
 * 
 * 未核销金额  应付单的未核销金额  数字  无  10  是  无
 * 
 * 已核销金额  应付单的已核销金额  数字  无  10  是  无
 * 
 * 1.8.8  红冲付款单（审核通过输出）
 * 
 * 参见《DP-FOSS-结算系统用例-录入付款单》付款单信息数据元素
 * 
 * 1.8.9  红冲付款单明细（审核通过输出输出）
 * 
 * 参见《DP-FOSS-结算系统用例-录入付款单》付款单明细信息数据元素
 * 
 * 1.8.10  代收货款更新后单据（审核通过输出输出）
 * 
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填   备注
 * 
 * 代收货款状态  代收货款的状态  文本  无  10  是  无
 * 汇款失败原因  汇款失败的原因  文本  无  255  是  无
 * 
 * 1.8.11  核销单信息（审核通过输出）
 * 参见《DP-FOSS-结算系统用例-应收冲应付（后台）》核销单信息数据元素
 * 
 * 1.8.12  核销单明细信息（审核通过输出）
 * 参见《DP-FOSS-结算系统用例-应收冲应付（后台）》核销单明细信息数据元素
 * 
 * 1.9  非功能性需求
 * 使用量  每天汇款15,000单左右，大概分3批汇款
 * 
 * 2012年全网估计用户数  1人
 * 
 * 响应要求（如果与全系统要求 不一致的话）  15分钟以内
 * 
 * 使用时间段  资金部上班时间
 * 
 * 高峰使用时间段  每天9:00~10:00，13:30~16:00
 * 
 * 
 * 1.10  接口描述：
 * 接口名称   对方系统（外部系统或内部其他模块）  接口描述
 * 银企退代收货款接口  银企  退代收货款
 * 
 *
 *
 * 页面提供的钮控件描述：
 * 1、资金部汇款导出页面
 *   查询：查询符合退款条件的代收货款列表
 *   重置：将查询条件设置到初始化情况
 *   冻结：冻结代收货款信息，使营业部不能进行操作。 
 *   取消冻结：取消代收货款的资金部冻结状态，营业部能够查询到该条代收货款信息，从而进行操作。
 *   退款申请：选中冻结单据，点击退款申请，会调用银企接口，将数据传递过去进行退款。与汇款导出互斥，同一批数据，点击了退款申请，则不能进行汇款导出。
 *   汇款导出：将冻结单据导出Excel进行汇款，与退款申请互斥。点击汇款导出，则不能再进行退款申请。
 *   全选冻结单据：勾选全部的冻结状态的单据，从而方便其进行退款申请或汇款导出。
 * 
 * 2、汇款确认页面
 *   汇款成功：资金部汇款成功，则点击按钮，修改代收货款状态为“已退款”
 *   汇款失败：资金部汇款失败，则点击按钮，修改代收货款状态为“退款失败申请”
 *   反汇款成功：资金部汇款成功转失败，则点击按钮，修改代收货款状态为“反汇款成功”
 * 
 * 3、汇款失败审核界面
 *   审核通过：资金部对于汇款失败和汇款成功转失败的单据，如果确认失败，则点击该 
 * 按钮，修改代收货款状态为“退款失败”
 * 
 *   退回：资金部对于汇款失败和汇款成功转失败的单据，如果确认其没有失败，则点击
 * 该按钮，修改代收货款状态为“退款中”或“已退款”
 *
 *
 *
 1.7  业务规则
 * 序号  描述
 * SR1  数据传输格式：
 * 类别：代收货款类别
 * 
 * 所属子公司：代收货款应付单部门所对应的子公司
 * 出发部门：代收货款应付单的应付部门
 * 运单号：代收货款的运单号
 * 收款人：代收货款账号的开户人
 * 金额：应退代收货款金额
 * 账号：退款账号
 * 开户行：账号所属银行
 * 省：开户行所在省份
 * 市：开户行所在城市
 * 支行：开户行的详细名称
 * 对公对私标志：账号的类别
 * 手机号码：收款人的手机号码
 * 签收日期：有效版本财务单据对应的签收日期
 * 银行行号：开户行的行号
 * 
 * 汇款申请时间：点击“退款申请”或“汇款导出”操作时对应的服务器时间
 * 批次号：当前时间截取到日期01/02八位流水号（此处01代表即日退，02为三日退或审核退） 如：201207030100000001
 * 
 * SR2  更新代收货款状态为“退款中”，同时更新批次号为最新批次编号，批次状态为“已提交”，更新条件：
 * 1、代收货款状态为“资金部冻结”
 * SR3  付款单转换规则：
 * 编码：自动生成，编码规则： FK2+8位流水号（不足补0）
 * 付款部门：资金部
 * 应付部门: 应付单的应付部门
 * 客户编码：应付单挂账客户编码
 * 付款类型：退代收货款
 * 付款金额：应付单的未核销金额
 * 来源单据：应付单编号
 * 审核状态：未审核
 * 付款方式：电汇
 * 业务日期：当前服务器时间
 * 记账日期：当前服务器时间
 * 审核日期：无
 * 是否有效：是
 * 是否红单：否
 * 创建人：当前登录人
 * 
 * 创建时间:当前时间
 * SR4  1、  按单号查询，则忽略其他查询条件
 * 2、  查询代收货款状态为“退款中”和“已退款”单据
 * SR5  1、 起始时间和结束时间不能为空，且起始时间不能大于结束时间
 * 2、 起始时间和结束时间间隔不能大于7天
 * 3、 代收货款汇款导出时间必须在起始时间和结束时间范围之内
 * SR6  1、代收货款状态为“退款失败申请”或“反汇款成功”的代收货款单据
 * SR7  1、  运单号不能为空且不能超过10个
 * 2、  代收货款状态为“退款失败申请”或“反汇款成功”的代收货款单据
 * 
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOfflineService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICODBatchService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 线下汇代收货款给客户服务.
 * 
 * @author dp-zengbinwen
 * @date 2012-10-20 下午1:56:00
 */
public class BillPayCODOfflineService implements IBillPayCODOfflineService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODOfflineService.class);

	/** 代收货款服务. */
	private ICodCommonService codCommonService;

	/** 付款单服务. */
	private IBillPaymentService billPaymentService;

	/** 应付单服务. */
	private IBillPayableService billPayableService;

	/** 核销单服务. */
	private IBillWriteoffService billWriteoffService;

	/** 结算公共服务. */
	private ISettlementCommonService settlementCommonService;

	/** 代收货款批次号服务. */
	private ICODBatchService codBatchService;

	/**
	 * 资金部汇款界面查询合计总条数总金额，冻结总条数总金额
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:47:07
	 * @param endSignDate
	 * @param codTypes
	 * @param banks
	 * @param publicPrivateFlag
	 * @return
	 * @throws SettlementException
	 */
	@Override
	public CODDto queryBillCODPayableSum(Date endSignDate,
			List<String> codTypes, List<String> banks, String publicPrivateFlag)
			throws SettlementException {

		LOGGER.trace("资金部汇款界面查询合计Service start,codTypes:"
				+ (codTypes == null ? null : codTypes.toString()));

		// 代收货款类型不能为空
		if (CollectionUtils.isEmpty(codTypes)) {
			throw new SettlementException("代收货款类型为空，不能进行代收货款汇款导出查询");
		}

		// 构造查询参数
		BillCODPayableQueryDto queryDto = new BillCODPayableQueryDto();

		// 截止签收日期、代收货款类型、银行、对公对私标志
		queryDto.setEndSignDate(endSignDate);
		queryDto.setCodTypes(codTypes);
		queryDto.setBanks(banks);
		queryDto.setPublicPrivateFlag(publicPrivateFlag);
		
		/**
		 * @author 218392  zhangyongxue 2015-08-07 14:26:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		queryDto.setIsPackage("");//默认不含有打包退,默认是空
		for(String codType : codTypes){
			if(StringUtils.equals("PACK", codType)){
				queryDto.setIsPackage("Y");//如果含有打包退的时候，就将 N 变成 Y
			}
		}

		// 代收货款状态:未退款、营业部冻结
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		statuses.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
		queryDto.setStatuses(statuses);

		// 是否有效、应付单是否有效、应付单生效状态、应付单单据类型
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		queryDto.setStatue(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE); // 用于统计冻结状态的总金额总条数

		// 查询结果
		CODDto queryResult = codCommonService.queryBillCODPayableSum(queryDto);

		LOGGER.trace("资金部汇款界面查询Service end");

		return queryResult;
	}

	/**
	 * 资金部汇款界面查询【代收货款状态不能为空，截止签收日期、银行、对公对私标志可为空】.
	 * 
	 * @param endSignDate
	 *            the end sign date
	 * @param codTypes
	 *            the cod types
	 * @param banks
	 *            the banks
	 * @param publicPrivateFlag
	 *            the public private flag
	 * @return the list
	 * @throws SettlementException
	 *             the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:46:21
	 */
	public List<CODDto> queryBillCODPayable(Date endSignDate,
			List<String> codTypes, List<String> banks,
			String publicPrivateFlag, int offset, int limit,
			String sortProperty,String sortDirection)
			throws SettlementException {

		LOGGER.trace("资金部汇款界面查询Service start,codTypes:"
				+ (codTypes == null ? null : codTypes.toString()));

		// 代收货款类型不能为空
		if (CollectionUtils.isEmpty(codTypes)) {
			throw new SettlementException("代收货款类型为空，不能进行代收货款汇款导出查询");
		}

		// 构造查询参数
		BillCODPayableQueryDto queryDto = new BillCODPayableQueryDto();

		// 截止签收日期、代收货款类型、银行、对公对私标志
		queryDto.setEndSignDate(endSignDate);
		queryDto.setCodTypes(codTypes);
		queryDto.setBanks(banks);
		queryDto.setPublicPrivateFlag(publicPrivateFlag);
		
		/**
		 * @author 218392  zhangyongxue 2015-08-07 14:26:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		queryDto.setIsPackage("");//默认不含有打包退,默认是空
		for(String codType : codTypes){
			if(StringUtils.equals("PACK", codType)){
				queryDto.setIsPackage("Y");//如果含有打包退的时候，就将 N 变成 Y
			}
		}

		// 代收货款状态:未退款、营业部冻结
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		statuses.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
		queryDto.setStatuses(statuses);

		// 是否有效、应付单是否有效、应付单生效状态、应付单单据类型
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		queryDto.setLimit(limit);
		queryDto.setOffset(offset);
		
		// 设置排序
		queryDto.setSortProperty(sortProperty);
		queryDto.setSortDirection(sortDirection);

		// 查询结果
		List<CODDto> queryResult = codCommonService
				.queryBillCODPayable(queryDto);

		LOGGER.trace("资金部汇款界面查询Service end");

		// 查询并返回结果
		return queryResult;
	}

	/**
	 * 批量处理导出代收货款数据.
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午6:15:10
	 * @param entityIds
	 * @param currentInfo
	 * @return
	 * @throws SettlementException
	 */
	@Transactional
	public CODDto doWithExportBillCODAll(Date endSignDate,
			List<String> codTypes, List<String> banks,
			String publicPrivateFlag, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("批量处理导出代收货款数据Service start");

		// 代收货款类型不能为空
		if (CollectionUtils.isEmpty(codTypes)) {
			throw new SettlementException("代收货款类型为空，不能进行代收货款汇款导出查询");
		}

		// 构造查询参数
		BillCODPayableQueryDto queryDto = new BillCODPayableQueryDto();
		
		/**
		 * @author 218392  zhangyongxue 2015-08-29 16:26:39
		 * 这里加上个判断给isPackage根据是否打包退设置值
		 */
		queryDto.setIsPackage("");//默认不含有打包退,默认是空
		for(String codType : codTypes){
			if(StringUtils.equals("PACK", codType)){
				queryDto.setIsPackage("Y");//如果含有打包退的时候，就将 N 变成 Y
			}
		}

		// 截止签收日期、代收货款类型、银行、对公对私标志
		queryDto.setEndSignDate(endSignDate);
		queryDto.setCodTypes(codTypes);
		queryDto.setBanks(banks);
		queryDto.setPublicPrivateFlag(publicPrivateFlag);

		// 代收货款状态:冻结
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
		queryDto.setStatuses(statuses);

		// 是否有效、应付单是否有效、应付单生效状态、应付单单据类型
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setEffectiveStatus(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		// 查询结果
		List<CODPayableDto> codPayableDtoList = codCommonService
				.queryAllCODAndBillPayable(queryDto);

		String status = null;

		// 循环对代收货款数据处理
		for (CODEntity entity : codPayableDtoList) {

			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			status = entity.getStatus();

			// 如果不是经营部冻结，则抛出异常
			if (!SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
					.equals(status)) {
				throw new SettlementException("代收货款非资金部冻结");
			}

		}

		// 判断codEntity集合属于什么类型(R3,RA;R1)
		//String codType = codCommonService.codEntityListInstanceofType(codPayableDtoList);
		// 根据条件付款导出时，判断类型(R3,RA;R1)优化，只要根据查询条件获得类型
		String codType = null;
		if (StringUtils.equals(codTypes.get(0), SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY) ) { // 全部是即日退类型
			codType = SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY;
		} else {
			codType = SettlementConstants.COD__COD_TYPE__RETURN_3_A_DAY_CODE;
		}
		
		// 生成代收货款批次号
		String codBatchNumberStr = codBatchService.generateCODBatchNo(codType);
		// 创建批次号实体,线下批次状态给null
		codBatchService.createCODBatchEntity(currentInfo, codBatchNumberStr,null);

		CODDto codDto = null;
		if(CollectionUtils.isNotEmpty(codPayableDtoList)){
			// 批量更新，大于1000条，拆分多组执行
			List<List<CODPayableDto>> splitPayableList = com.deppon.foss.util.CollectionUtils
					.splitListBySize(codPayableDtoList,
							FossConstants.ORACLE_MAX_IN_SIZE);
			codDto = new CODDto();
			// 退款中、汇款导出时间、汇款导出人、批次号、退款路径
			codDto.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNING);
			codDto.setCodExportTime(new Date());
			codDto.setCodExportName(currentInfo.getEmpName());
			codDto.setCodExportCode(currentInfo.getEmpCode());
			codDto.setBatchNumber(codBatchNumberStr);
			codDto.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__OFFLINE);
			for (List<CODPayableDto> list : splitPayableList) {
				codDto.setCodList(list); // 批量更新
				// 更新代收货款状态
				codCommonService.updatePayCODOfflineStatusByBatch(codDto,
						currentInfo);
			}
			
			// 循环对代收货款数据处理
			for (CODPayableDto entity : codPayableDtoList) {
				//上面批量更新后，把汇款导出信息设置到CODPayableDto上。
				entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNING);
				entity.setCodExportTime(new Date());
				entity.setCodExportName(currentInfo.getEmpName());
				entity.setCodExportCode(currentInfo.getEmpCode());
				entity.setBatchNumber(codBatchNumberStr);
				entity.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__OFFLINE);

				// 把CODPayableDto 中的应付单信息构建到BillPayableEntity上。
				BillPayableEntity billPayable = new BillPayableEntity();
				billPayable.setId(entity.getPayableId());
				billPayable.setAccountDate(entity.getPayableAccountDate());
				billPayable.setVersionNo(entity.getPayableVersionNo());
				billPayable.setEffectiveStatus(entity.getEffectiveStatus());
				billPayable.setSignDate(entity.getSignDate());
				billPayable.setPayableComName(entity.getPayableComName());
				billPayable.setWaybillNo(entity.getPayableWaybillNo());
				billPayable.setUnverifyAmount(entity.getUnverifyAmount());
				billPayable.setPayableOrgName(entity.getPayablePayableOrgName());
				billPayable.setAmount(entity.getAmount());
				billPayable.setPayableComCode(entity.getPayableComCode());
				billPayable.setPayableOrgCode(entity.getPayablePayableOrgCode());
				billPayable.setPayableNo(entity.getPayableNo());
				billPayable.setSourceBillNo(entity.getSourceBillNo());
				billPayable.setCustomerCode(entity.getPayableCustomerCode());
				billPayable.setCustomerName(entity.getPayableCustomerName());
				billPayable.setBusinessDate(entity.getPayableBusinessDate());
				billPayable.setVerifyAmount(entity.getVerifyAmount());
				billPayable.setApproveStatus(entity.getApproveStatus());

				// 代收货款线下汇款导出数据验证
				this.validBillPayableCODEntity(entity, billPayable);

				// 新增付款单
				BillPaymentEntity billPayment = buildBillPaymentEntity(entity,
						billPayable, currentInfo.getEmpName(),
						currentInfo.getEmpCode());
				// 创建付款单明细记录
				BillPaymentDEntity paymentDetail = new BillPaymentDEntity();
				paymentDetail.setSourceBillNo(billPayable.getPayableNo()); // 来源单号
				paymentDetail.setWaybillNo(billPayable.getWaybillNo()); // 运单号
				paymentDetail
						.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE); // 来源单据类型
				paymentDetail.setSrcSourceBillNo(billPayable.getSourceBillNo()); // 应付单来源单据号
				paymentDetail.setSourceAccountDate(billPayable.getAccountDate()); // 来源单据的记账日期
				// 保存付款单和付款单明细记录
				billPaymentService.addBillPaymentAndDetails(billPayment,
						Arrays.asList(paymentDetail), currentInfo);

				// 将付款单单号更新到应付单
				BillPayableDto dto = new BillPayableDto();
				dto.setId(billPayable.getId());
				dto.setAccountDate(billPayable.getAccountDate());
				dto.setVersionNo(billPayable.getVersionNo());
				dto.setPaymentNo(billPayment.getPaymentNo());
				dto.setPaymentAmount(billPayment.getAmount());
				billPayableService.payForBillPayable(dto, currentInfo);
				// 为了保持后面应付单核销，java方法中的版本号和数据库中的版本号统一，在这里+1
				billPayable.setVersionNo((short) (billPayable.getVersionNo() + 1));

				// 构造传入核销服务的参数
				BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

				// 应付单
				List<BillPayableEntity> billPayableEntitys = new ArrayList<BillPayableEntity>();
				billPayableEntitys.add(billPayable);
				writeoffDto.setBillPayableEntitys(billPayableEntitys);

				// 付款单
				writeoffDto.setBillPaymentEntity(billPayment);

				// 核销批次号
				String writeoffBatchNo = settlementCommonService
						.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
				writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

				// 生成方式，自动生成
				writeoffDto
						.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

				// 调用核销接口，付款冲应付操作
				billWriteoffService.writeoffCODPaymentAndPayable(writeoffDto,
						currentInfo);

			}
		}

		// 把导出的集合返回出去用于导出execl
		if(codDto != null){
			codDto.setCodList(codPayableDtoList);
		}

		LOGGER.info("批量处理导出代收货款数据Service end.");
		return codDto;
	}

	/**
	 * 把CODEntity，BillPayableEntity中的部分数据构建到CodPayableDto实体
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-9 上午10:43:04
	 * @param entity
	 * @param billPayable
	 */
	private CODPayableDto createCODPayableDtoByCODEntityAndBillPayableEntity(
			CODEntity entity, BillPayableEntity billPayable) {

		// 把CODEntity，BillPayableEntity中的部分数据构建到CodPayableDto实体上，用于返回
		CODPayableDto codPayableDto = new CODPayableDto();
		try {
			BeanUtils.copyProperties(codPayableDto, entity);
		} catch (IllegalAccessException e) {
			throw new SettlementException("BeanUtils.copyProperties方法异常");
		} catch (InvocationTargetException e) {
			throw new SettlementException("BeanUtils.copyProperties方法异常");
		}
		codPayableDto.setPayableId(billPayable.getId());
		codPayableDto.setPayableAccountDate(billPayable.getAccountDate());
		codPayableDto.setPayableVersionNo(billPayable.getVersionNo());
		codPayableDto.setEffectiveStatus(billPayable.getEffectiveStatus());
		codPayableDto.setSignDate(billPayable.getSignDate());
		codPayableDto.setPayableComName(billPayable.getPayableComName());
		codPayableDto.setPayableWaybillNo(billPayable.getWaybillNo());
		codPayableDto.setUnverifyAmount(billPayable.getUnverifyAmount());
		codPayableDto.setPayablePayableOrgName(billPayable.getPayableOrgName());
		codPayableDto.setAmount(billPayable.getAmount());
		codPayableDto.setPayableComCode(billPayable.getPayableComCode());
		codPayableDto.setPayablePayableOrgCode(billPayable.getPayableOrgCode());
		codPayableDto.setPayableNo(billPayable.getPayableNo());
		codPayableDto.setSourceBillNo(billPayable.getSourceBillNo());
		codPayableDto.setPayableCustomerCode(billPayable.getCustomerCode());
		codPayableDto.setPayableCustomerName(billPayable.getCustomerName());
		codPayableDto.setPayableBusinessDate(billPayable.getBusinessDate());
		codPayableDto.setVerifyAmount(billPayable.getVerifyAmount());
		codPayableDto.setApproveStatus(billPayable.getApproveStatus());

		return codPayableDto;

	}

	/**
	 * 处理导出代收货款数据.
	 * 
	 * @param entityIds
	 *            the entity ids
	 * @param currentInfo
	 *            the current info
	 * @return the string
	 * @throws SettlementException
	 *             the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-20 下午1:59:34
	 */
	@Override
	@Transactional
	public CODDto doWithExportBillCOD(List<String> entityIds,
			CurrentInfo currentInfo) throws SettlementException {

		LOGGER.info("Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		List<CODEntity> codEntityList = codCommonService.queryByIds(entityIds);// 获得CODEntity集合

		// 判断codEntity集合属于什么类型(R3,RA;R1)
		String codType = codCommonService
				.codEntityListInstanceofType(codEntityList);
		// 生成代收货款批次号
		String codBatchNumberStr = codBatchService.generateCODBatchNo(codType);
		// 创建批次号实体,线下批次状态默认null
		codBatchService.createCODBatchEntity(currentInfo, codBatchNumberStr,null);

		// 把codEntityList 转换为 单号list
		@SuppressWarnings("unchecked")
		List<String> codWayBillNoList = (List<String>) CollectionUtils.collect(
				codEntityList, new Transformer() {
					@Override
					public Object transform(Object input) {
						if (input == null) {
							throw new SettlementException("代收货款实体为空");
						}
						CODEntity entity = (CODEntity) input;
						String status = entity.getStatus();
						// 如果不是经营部冻结，则抛出异常
						if (!SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE
								.equals(status)) {
							throw new SettlementException("代收货款非资金部冻结");
						}
						return entity.getWaybillNo();
					}
				});

		
		List<BillPayableEntity> billPayableList = billPayableService
				.queryByWaybillNosAndByBillTypes(
						codWayBillNoList,
						Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD));// 获得代收货款应付单集合

		List<CODPayableDto> codPayableDtoList = new ArrayList<CODPayableDto>(
				billPayableList.size()); // 返回的codPayableDto集合

		// 循环对代收货款数据处理
		for (final CODEntity entity : codEntityList) {

			// 查询并设置应付单
			BillPayableEntity billPayable = (BillPayableEntity) CollectionUtils
					.find(billPayableList, new Predicate() {
						@Override
						public boolean evaluate(Object object) {
							if (StringUtils.equals(
									((BillPayableEntity) object).getWaybillNo(),
									entity.getWaybillNo()))
								return true;
							return false;
						}
					});

			// 退款中、汇款导出时间、汇款导出人、批次号、退款路径
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNING);
			entity.setCodExportTime(new Date());
			entity.setCodExportName(currentInfo.getEmpName());
			entity.setCodExportCode(currentInfo.getEmpCode());
			entity.setBatchNumber(codBatchNumberStr);
			entity.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__OFFLINE);
			
			// 代收货款线下汇款导出数据验证
			this.validBillPayableCODEntity(entity, billPayable);

			// 更新代收货款状态
			codCommonService.updatePayCODOfflineStatus(entity, currentInfo);

			// 把CODEntity，BillPayableEntity中的部分数据构建到CodPayableDto实体,并 添加
			// 到codPayableDtoList
			codPayableDtoList.add(this
					.createCODPayableDtoByCODEntityAndBillPayableEntity(entity,
							billPayable));

			// 新增付款单
			BillPaymentEntity billPayment = buildBillPaymentEntity(entity,
					billPayable, currentInfo.getEmpName(),
					currentInfo.getEmpCode());
			// 创建付款单明细记录
			BillPaymentDEntity paymentDetail = new BillPaymentDEntity();
			paymentDetail.setSourceBillNo(billPayable.getPayableNo()); // 来源单号
			paymentDetail.setWaybillNo(billPayable.getWaybillNo()); // 运单号
			paymentDetail.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT_D_SOURCE_BILL_TYPE_PAYABLE); // 来源单据类型
			paymentDetail.setSrcSourceBillNo(billPayable.getSourceBillNo()); // 应付单来源单据号
			paymentDetail.setSourceAccountDate(billPayable.getAccountDate()); // 来源单据的记账日期
			paymentDetail.setPayAmount(billPayable.getUnverifyAmount());//支付金额
			
			
			// 保存付款单和付款单明细记录
			billPaymentService.addBillPaymentAndDetails(billPayment,Arrays.asList(paymentDetail), currentInfo);
					

			// 将付款单单号更新到应付单
			BillPayableDto dto = new BillPayableDto();
			dto.setId(billPayable.getId());
			dto.setAccountDate(billPayable.getAccountDate());
			dto.setVersionNo(billPayable.getVersionNo());
			dto.setPaymentNo(billPayment.getPaymentNo());
			dto.setPaymentAmount(billPayment.getAmount());
			billPayableService.payForBillPayable(dto, currentInfo);
			// 为了保持后面应付单核销，java方法中的版本号和数据库中的版本号统一，在这里+1
			billPayable.setVersionNo((short) (billPayable.getVersionNo() + 1));

			// 构造传入核销服务的参数
			BillWriteoffOperationDto writeoffDto = new BillWriteoffOperationDto();

			// 应付单
			List<BillPayableEntity> billPayableEntitys = new ArrayList<BillPayableEntity>();
			billPayableEntitys.add(billPayable);
			writeoffDto.setBillPayableEntitys(billPayableEntitys);

			// 付款单
			writeoffDto.setBillPaymentEntity(billPayment);

			// 核销批次号
			String writeoffBatchNo = settlementCommonService
					.getSettlementNoRule(SettlementNoRuleEnum.HX_BN);
			writeoffDto.setWriteoffBatchNo(writeoffBatchNo);

			// 生成方式，自动生成
			writeoffDto
					.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

			// 调用核销接口，付款冲应付操作
			billWriteoffService.writeoffCODPaymentAndPayable(writeoffDto,
					currentInfo);

		}

		// 把导出的集合返回出去用于导出execl
		CODDto codDto = new CODDto();
		codDto.setBatchNumber(codBatchNumberStr);
		codDto.setCodList(codPayableDtoList);
		LOGGER.info("Service end.");
		return codDto;
	}

	/**
	 * 构建付款单实体.
	 * 
	 * @param entity
	 *            the entity
	 * @param billPayable
	 *            the bill payable
	 * @param exportUserName
	 *            the export user name
	 * @param exportUserCode
	 *            the export user code
	 * @return the bill payment entity
	 * @author dp-zengbinwen
	 * @date 2012-10-20 下午4:57:00
	 */
	private BillPaymentEntity buildBillPaymentEntity(CODEntity entity,
			BillPayableEntity billPayable, String exportUserName,
			String exportUserCode) {

		BillPaymentEntity bill = new BillPaymentEntity();

		// ID,付款单编号，币种，付款金额，客户
		bill.setId(UUIDUtils.getUUID());
		bill.setPaymentNo(settlementCommonService
				.getSettlementNoRule(SettlementNoRuleEnum.FK2));
		bill.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		bill.setAmount(billPayable.getUnverifyAmount());
		bill.setCustomerCode(billPayable.getCustomerCode());
		bill.setCustomerName(billPayable.getCustomerName());

		// 汇款状态，审核状态，来源单据类型，来源单据编号
		bill.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__NOT_TRANSFER);
		bill.setAuditStatus(SettlementDictionaryConstants.BILL_REPAYMENT__AUDIT_STATUS__NOT_AUDIT);
		bill.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		bill.setSourceBillNo(billPayable.getPayableNo());

		// 付款部门、付款子公司
		bill.setPaymentOrgCode(billPayable.getPayableOrgCode());
		bill.setPaymentOrgName(billPayable.getPayableOrgName());
		// 付款子公司处理
		bill.setPaymentCompanyCode(billPayable.getPayableComCode());
		bill.setPaymentCompanyName(billPayable.getPayableComName());

		// 是否有效、是否红单、创建人、业务日期、会计日期
		bill.setActive(FossConstants.YES);
		bill.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
		bill.setCreateUserCode(exportUserCode);
		bill.setCreateUserName(exportUserName);
		bill.setBusinessDate(billPayable.getBusinessDate());
		bill.setAccountDate(new Date());

		// 创建时间、是否初始化、付款方式、收款人电话、开户名
		bill.setCreateTime(new Date());
		bill.setIsInit(FossConstants.NO);
		bill.setPaymentType(SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__TELEGRAPH_TRANSFER);
		bill.setMobilePhone(entity.getPayeePhone());
		bill.setPayeeName(entity.getPayeeName());

		// 银行账号、对公对私标志、省、市、银行、支行、行号、版本号
		bill.setAccountNo(entity.getAccountNo());
		bill.setAccountType(entity.getPublicPrivateFlag());
		bill.setProvinceCode(entity.getProvinceCode());
		bill.setProvinceName(entity.getProvinceName());
		bill.setCityCode(entity.getCityCode());
		bill.setCityName(entity.getCityName());
		bill.setBankHqName(entity.getBankHQName());
		bill.setBankBranchName(entity.getBankBranchName());
		bill.setBankBranchCode(entity.getBankBranchCode());
		bill.setVersionNo(FossConstants.INIT_VERSION_NO);
		bill.setBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);
		// 生成方式，自动生成
		bill.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);
		return bill;
	}

	/**
	 * 代收货款线下汇款导出数据验证.
	 * 
	 * @param entity
	 *            the entity
	 * @param billPayable
	 *            the bill payable
	 * @author guxinhua
	 * @date 2013-1-14 下午2:12:41
	 */
	private void validBillPayableCODEntity(CODEntity entity,
			BillPayableEntity billPayable) {
		String waybillNo = entity.getWaybillNo();
		if (StringUtils.isBlank(waybillNo)) {
			throw new SettlementException("代收货款运单号不能为空");
		}
		String headMsg = "运单：" + waybillNo + "，";

		if (StringUtils.isBlank(entity.getCodType())) {
			throw new SettlementException(headMsg + "代收货款类别不能为空");
		}
		if (StringUtils.isBlank(billPayable.getPayableComName())) {
			throw new SettlementException(headMsg + "代收货款所属子公司不能为空");
		}
		if (StringUtils.isBlank(billPayable.getPayableOrgName())) {
			throw new SettlementException(headMsg + "代收货款出发部门不能为空");
		}
		if (StringUtils.isBlank(entity.getPayeeName())) {
			throw new SettlementException(headMsg + "代收货款收款人不能为空");
		}
		if (null == billPayable.getAmount()) {
			throw new SettlementException(headMsg + "代收货款金额不能为空");
		}
		if (StringUtils.isBlank(entity.getAccountNo())) {
			throw new SettlementException(headMsg + "账号不能为空");
		}
		if (StringUtils.isBlank(entity.getBankHQName())) {
			throw new SettlementException(headMsg + "开户行不能为空");
		}
		if (StringUtils.isBlank(entity.getProvinceName())) {
			throw new SettlementException(headMsg + "省不能为空");
		}
		if (StringUtils.isBlank(entity.getCityName())) {
			throw new SettlementException(headMsg + "市不能为空");
		}
		if (StringUtils.isBlank(entity.getBankBranchName())) {
			throw new SettlementException(headMsg + "支行不能为空");
		}
		if (StringUtils.isBlank(entity.getPublicPrivateFlag())) {
			throw new SettlementException(headMsg + "对公对私标志不能为空");
		}
		/*
		 * if(StringUtils.isBlank(entity.getPayeePhone())){ throw new
		 * SettlementException(headMsg + "手机号码不能为空"); }
		 */
		if (null == billPayable.getSignDate()) {
			throw new SettlementException(headMsg + "签收日期不能为空");
		}
		if (StringUtils.isBlank(entity.getBankBranchCode())) {
			throw new SettlementException(headMsg + "银行行号不能为空");
		}
		if (null == entity.getCodExportTime()) {
			throw new SettlementException(headMsg + "汇款导出时间不能为空");
		}
		if (StringUtils.isBlank(entity.getCodExportName())) {
			throw new SettlementException(headMsg + "汇款导出人不能为空");
		}
		if (StringUtils.isBlank(entity.getBatchNumber())) {
			throw new SettlementException(headMsg + "批次号不能为空");
		}
	}

	/**
	 * Sets the cod common service.
	 * 
	 * @param codCommonService
	 *            the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Sets the bill payment service.
	 * 
	 * @param billPaymentService
	 *            the new bill payment service
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	/**
	 * Sets the bill writeoff service.
	 * 
	 * @param billWriteoffService
	 *            the new bill writeoff service
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}

	/**
	 * Sets the settlement common service.
	 * 
	 * @param settlementCommonService
	 *            the new settlement common service
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * Sets the bill payable service.
	 * 
	 * @param billPayableService
	 *            the new bill payable service
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}

	/**
	 * Sets the cod batch service.
	 * 
	 * @param codBatchService
	 *            the new cod batch service
	 */
	public void setCodBatchService(ICODBatchService codBatchService) {
		this.codBatchService = codBatchService;
	}

}
