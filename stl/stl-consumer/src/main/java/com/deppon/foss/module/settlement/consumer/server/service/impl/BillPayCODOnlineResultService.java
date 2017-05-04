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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODOnlineResultService.java
 * 
 * FILE NAME        	: BillPayCODOnlineResultService.java
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
 * 2、 校验所选单据的代收货款状态  
 * 是否都为“反汇款成功”或“退款失败申请”
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
 * 2a  当没有选中数据时，弹出提示  提示信息  请至少选中一条数据，进行操作！
 * 2b  当选择代收货款的冻结状态不是“已冻结”，则终止程序，弹出提示  提示信息  “代收货款冻结状态必须为已冻结，才能进行退款申请”
 * 3a  将数据传输给银企系统，如果返回接受状态为“false”，则终止程序，弹出提示  提示信息  返回错误信息:“数据未成功传递给银企系统，可能是对方系统出了问题，请联系IT服务中心进行处理！”
 * 5a  校验输入运单号有误，则直接返回，并弹出提示  提示信息  1、  如果输入运单号超过10个，则弹出提示：“运单号不能超过10个”
 * 2、  如果输入运单号校验有误，则弹出提示：“输入运单号必须为8-10为数字”
 * 5b  如果没查询到数据，则弹出提示  提示信息  1、弹出提示信息：“没有查询到 符合条件的数据”
 * 6a  校验传入起始日期和结束日期有误，则直接返回并弹出提示  提示信息  
 * 1、如果传入的起始日期或结束日期为空，则直接弹出提示：   “起始日期和结束日期不能为空”
 * 2 、如果传入的起始日期和结束日期的间隔大于7天，则直接弹出提示：“起始日期和结束日期的间隔不能大于7天”
 * 7a  当选择代收货款的状态不是“退款中”，则终止程序，弹出提示  提示信息  弹出错误信息：“代收货款状态必须为退款中，才能进行汇款成功操作”
 * 8a  当选择代收货款的状态不是“退款中”，则终止程序，弹出提示  提示信息  弹出错误信息：“代收货款状态必须为退款中，才能进行退款失败操作”
 * 8b  如果汇款失败或反汇款成功，且原因为空，则终止程序，弹出提示  提示信息  弹出错误信息：“请填写汇款失败或反汇款成功的原因”
 * 9a  当选择代收货款的状态不是“已退款”，则终止程序，弹出提示  提示信息  弹出错误信息：“代收货款状态必须为已退款，才能进行反汇款成功操作”
 * 12a 如果代收货款状态不为：“反汇款成功”或“退款失败申请”，则终止程序直接弹出提示  提示信息  弹出错误信息：“代收货款状态必须为退款失败申请或反汇款成功状态，才能进行操作！”
 * 
 * 1.7  业务规则
 * 序号  描述
 * SR1  数据传输格式：
 * 类别：代收货款类别
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
 * 汇款申请时间：点击“退款申请”或“汇款导出”操作时对应的服务器时间
 * 批次号：当前时间截取到日期01/02八位流水号（此处01代表即日退，02为三日退或审核退） 如：201207030100000001
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
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填  
 * 备注
 * 
 * 未核销金额  应付单的未核销金额  数字  无  10  是  无
 * 已核销金额  应付单的已核销金额  数字  无  10  是  无
 * 1.8.8  红冲付款单（审核通过输出）
 * 参见《DP-FOSS-结算系统用例-录入付款单》付款单信息数据元素
 * 1.8.9  红冲付款单明细（审核通过输出输出）
 * 参见《DP-FOSS-结算系统用例-录入付款单》付款单明细信息数据元素
 * 1.8.10  代收货款更新后单据（审核通过输出输出）
 * 字段名称   说明   输入限制  输入项提示文本  长度  是否必填  
 * 备注
 * 
 * 代收货款状态  代收货款的状态  文本  无  10  是  无
 * 汇款失败原因  汇款失败的原因  文本  无  255  是  无
 * 
 * 1.8.11  核销单信息（审核通过输出）
 * 参见《DP-FOSS-结算系统用例-应收冲应付（后台）》核销单信息数据元素
 * 1.8.12  核销单明细信息（审核通过输出）
 * 参见《DP-FOSS-结算系统用例-应收冲应付（后台）》核销单明细信息数据元素
 * 
 * 1.9  非功能性需求
 * 使用量  每天汇款15,000单左右，大概分3批汇款
 * 2012年全网估计用户数  1人
 * 响应要求（如果与全系统要求 不一致的话）  15分钟以内
 * 使用时间段  资金部上班时间
 * 高峰使用时间段  每天9:00~10:00，13:30~16:00
 * 
 * 
 * 1.10  接口描述：
 * 接口名称   对方系统（外部系统或内部其他模块）  接口描述
 * 银企退代收货款接口  银企  退代收货款
 * 
 *
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.base.common.api.shared.domain.InstationJobMsgEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillWriteoffService;
import com.deppon.foss.module.settlement.common.api.server.service.ISettlementCommonService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementNoRuleEnum;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillWriteoffOperationDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODOnlineResultService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODOnlineResultEnum;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.pay.api.server.service.IPaymentQueryService;
import com.deppon.foss.module.settlement.pay.api.shared.dto.BillPaymentQueryDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 代收货款线上汇款处理结果服务
 * 
 * @author 046644-foss-zengbinwen
 * @date 2012-11-8 上午10:01:10
 */
public class BillPayCODOnlineResultService implements
		IBillPayCODOnlineResultService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager.getLogger(BillPayCODOnlineResultService.class);

	/**
	 * 代收货款服务
	 */
	private ICodCommonService codCommonService;

	/**
	 * 付款单服务
	 */
	private IBillPaymentService billPaymentService;

	/**
	 * 应付单服务
	 */
	private IBillPayableService billPayableService;

	/**
	 * 核销单服务
	 */
	private IBillWriteoffService billWriteoffService;

	/**
	 * 结算公共服务
	 */
	private ISettlementCommonService settlementCommonService;
	/**在线通知*/
    private IMessageService messageService;
	
    /**
	 * 付款单查询service接口实现
	 */
	private IPaymentQueryService paymentQueryService;
	
	
	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 
	 * 处理代收货款线上汇款结果
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 下午5:21:51
	 */
	@Transactional
	public void processPayResultService(List<BillCODOnlineResultDto> results,CurrentInfo currentInfo) throws SettlementException { 

		LOGGER.info("处理代收货款线上汇款结果Service start,result size:" + (results == null ? null : results.size()));

		// 判断汇款结果不能为空
		if (CollectionUtils.isEmpty(results)) {
			throw new SettlementException("代收货款汇款结果不能为空.");
		}

		List<CODEntity> successList = new ArrayList<CODEntity>();//成功集合 添加初始化创建
		
		
		// 循环对代收货款结果做处理
		for (BillCODOnlineResultDto resultDto : results) {
			
			// 判断运单号不能为空
			String waybillNoOrhbCode = resultDto.getWaybillNo();
			if (StringUtils.isEmpty(waybillNoOrhbCode)) {
				throw new SettlementException("运单号或合并编号不能为空");
			}
			// 判断汇款结果不能为空
			if (resultDto.getResult() == null) {
				throw new SettlementException(waybillNoOrhbCode+"代收货款汇款结果不能为空.");
			}
			
			if(waybillNoOrhbCode.startsWith("h")){
				// 根据合并编号获得代收货款集合
				List<CODEntity> codList = codCommonService.queryCODEntitiListByMergeCode(waybillNoOrhbCode);
				for (CODEntity cod : codList) {
					this.checkAndProcessPayResult(resultDto,cod,successList,currentInfo);
				}
			}else{
				// 查询代收货款
				CODEntity entity = codCommonService.queryByWaybill(resultDto.getWaybillNo());
				this.checkAndProcessPayResult(resultDto,entity,successList,currentInfo);
			}

		}

		// 处理退代收货款成功后，核销及设置代收状态退款成功
		if(CollectionUtils.isNotEmpty(successList)){
			this.processSuccessResultByBatch(results, successList, currentInfo);
		}
		
		LOGGER.info("处理代收货款线上汇款结果Service end.");
	}
	
	/**
	 * 校验并处理代收货款
	 * @param resultDto
	 * @param currentInfo
	 */
	private void checkAndProcessPayResult(BillCODOnlineResultDto resultDto,CODEntity entity,
									List<CODEntity> successList,CurrentInfo currentInfo){
		if (entity == null) {
			//throw new SettlementException("代收货款实体为空");
			LOGGER.error("代收货款实体为空");
			return;
		}
		// 判断是否线上汇款
		if (!SettlementDictionaryConstants.COD__COD_REFUND_PATH__ONLINE.equals(entity.getRefundPath())) {
			//throw new SettlementException(entity.getWaybillNo()+"代收货款非线上汇款");
			LOGGER.error(entity.getWaybillNo()+"代收货款非线上汇款");
			return;
		}
		
		// 代收货款批次号不能为空
		if (StringUtils.isEmpty(entity.getBatchNumber())) {
			//throw new SettlementException(entity.getWaybillNo()+"代收货款批次号为空.");
			LOGGER.error(entity.getWaybillNo()+"代收货款批次号为空.");
			return;
		}

		// 批次号必须与银企数据一致
		if (!entity.getBatchNumber().equals(resultDto.getBatchNo())) {
			//throw new SettlementException(entity.getWaybillNo()+"代收货款批次号与银企批次号不一致");
			LOGGER.error("银企返回状态"+resultDto.getResult()+";运单"+entity.getWaybillNo()+"代收货款批次号与银企批次号不一致");
			return;
		}
		
		// 校验现有代收货款状态
		if (SettlementDictionaryConstants.COD__STATUS__RETURNING.equals(entity.getStatus())) {
			// 成功或者失败，属于正常消息
			if(BillCODOnlineResultEnum.SUCCESS.equals(resultDto.getResult())){
				entity.setComAccount(resultDto.getBankAccount()); // 把公司付款账号复制到实体上
				successList.add(entity); // 汇款成功汇总并批量处理
			}else if(BillCODOnlineResultEnum.FAILURE.equals(resultDto.getResult())){
				
				// 如果失败原因为空，则抛出异常
				if (StringUtils.isEmpty(resultDto.getFailNotes())) {
					throw new SettlementException(entity.getWaybillNo()+"代收货款汇款失败原因为空");
				}
				BillPayableEntity billPayable = codCommonService.getBillPayableEntity(entity);
				BillPaymentEntity billPayment = getBillPayment(billPayable);
				doWithPayCODFailure(entity, billPayable, billPayment,currentInfo, resultDto.getFailNotes());
				
			}
			
		} else if (SettlementDictionaryConstants.COD__STATUS__RETURNED.equals(entity.getStatus())
				&& BillCODOnlineResultEnum.FAILURE.equals(resultDto.getResult())) { // 成功转失败
			
			// 如果失败原因为空，则抛出异常
			if (StringUtils.isEmpty(resultDto.getFailNotes())) {
				throw new SettlementException(entity.getWaybillNo()+"代收货款汇款成功转失败原因为空");
			}
			BillPayableEntity billPayable = codCommonService.getBillPayableEntity(entity);
			BillPaymentEntity billPayment = getBillPayment(billPayable);
			doWithPayCODSuccessToFailure(entity, billPayable, billPayment, currentInfo,resultDto.getFailNotes());
			
		} else {	// 其他状态，属于异常或者重发消息
			LOGGER.error("银企返回状态"+resultDto.getResult()+";运单"+entity.getWaybillNo()+"代收货款线状态异常！");
			//throw new SettlementException("代收货款状态有异常");
			return;
		}
		
	}
	
	/**
	 * 根据运单处理代收货款退款状态
	 * @param resultDto
	 * @param currentInfo
	 */
	private void processSuccessResultByBatch(List<BillCODOnlineResultDto> results,
			List<CODEntity> successList,CurrentInfo currentInfo){

		// 查询代收货款对应的应付单
		@SuppressWarnings("unchecked")
		List<String> waybillNOs =  (List<String>)CollectionUtils.collect(successList, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((CODEntity)input).getWaybillNo();
			}
		});
		
		//批量查询应付单
		List<BillPayableEntity> billPayableList = new  ArrayList<BillPayableEntity>(waybillNOs.size());
		
		List<List<String>> splitWaybillNoList = com.deppon.foss.util.CollectionUtils
									.splitListBySize(waybillNOs,FossConstants.ORACLE_MAX_IN_SIZE);
		List<String> billTypes = Arrays.asList(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		for (List<String> list : splitWaybillNoList) {
			billPayableList.addAll(billPayableService.queryByWaybillNosAndByBillTypes(list,billTypes));
		}
		
		if(CollectionUtils.isEmpty(billPayableList)){
			throw new SettlementException(Arrays.toString(waybillNOs.toArray()) +";查询代收货款对应的应付单为空");
		}
		
		//批量查询付款单
		// 查询应付单对应的付款单
		List<BillPaymentEntity> billPaymentList = new ArrayList<BillPaymentEntity>(waybillNOs.size());
		
		@SuppressWarnings("unchecked")
		List<String> payableNoList = (List<String>)CollectionUtils.collect(billPayableList, new Transformer() {
			@Override
			public Object transform(Object input) {
				return ((BillPayableEntity)input).getPayableNo();
			}
		});
		BillPaymentQueryDto queryDto = new BillPaymentQueryDto();
		queryDto.setActive(FossConstants.ACTIVE);
		List<List<String>> splitPayableNoList = com.deppon.foss.util.CollectionUtils
									.splitListBySize(payableNoList,FossConstants.ORACLE_MAX_IN_SIZE);
		for (List<String> list : splitPayableNoList) {
			queryDto.setSourceBillNos(list);
			billPaymentList.addAll(paymentQueryService.queryBillPaymentListBySourceBillNo(queryDto));
		}
		
		// 成功
		for (final CODEntity codEntity : successList) {
			final BillPayableEntity billPayable = (BillPayableEntity) CollectionUtils.find(billPayableList,new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					if(((BillPayableEntity)object).getWaybillNo().equals(codEntity.getWaybillNo())){
						return true;
					}
					return false;
				}
			});
			
			BillPaymentEntity billPayment = (BillPaymentEntity) CollectionUtils.find(billPaymentList,new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					if(((BillPaymentEntity)object).getSourceBillNo().equals(billPayable.getPayableNo())){
						return true;
					}
					return false;
				}
			});
			
			//TODO 
			this.doWithPayCODSuccess(codEntity,billPayable , billPayment,currentInfo);
		}
		
		// 按银企返回的银行账号分组更新退款成功
		Map<String,List<CODEntity>> accountMap = new HashMap<String,List<CODEntity>>();
		for (CODEntity cod : successList) {
				String accountStr = cod.getComAccount();
				if(accountMap.containsKey(accountStr)){
					//如果map key中存在
					List<CODEntity> haveList = accountMap.get(accountStr);
					haveList.add(cod);
				}else{
					//如果map中key不存在
					List<CODEntity> newList = new ArrayList<CODEntity>();
					newList.add(cod);
					accountMap.put(accountStr,newList);
				}
		}
		
		
		CODDto dto = new CODDto();
		Date now = new Date();
		dto.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNED);
		dto.setModifyTime(now);
		dto.setRefundSuccessTime(now);
		dto.setRemittanceFailNotes(null);
		dto.setModifyUserName(currentInfo.getEmpName());
		dto.setModifyUserCode(currentInfo.getEmpCode());
		
		Iterator<Entry<String, List<CODEntity>>> entryKeyIterator = accountMap.entrySet().iterator();  
	       while (entryKeyIterator.hasNext()) {  
	            Entry<String, List<CODEntity>> e = entryKeyIterator.next();  
	            String key = e.getKey();
	            List<CODEntity> value=e.getValue();  
	            
	            dto.setComAccount(key); //
	            // 批量更新，大于1000条，拆分多组successList执行
	    		// 更新代收货款状态为已退款
	    		List<List<CODEntity>> splitCodList = com.deppon.foss.util.CollectionUtils
	    				.splitListBySize(value,FossConstants.ORACLE_MAX_IN_SIZE);
	    		for (List<CODEntity> list : splitCodList) {
	    			dto.setCodEntityList(list); // 批量更新
	    			// 更新代收货款状态为已退款
	    			codCommonService.updatePayCODSuccessByBatch(dto, currentInfo);
	    		}
	    }  
		
	}
	
	/**
	 * 根据运单号处理代收货款退款状态
	 * @param resultDto
	 * @param currentInfo
	 * @Deprecated 使用checkAndProcessPayResult改造方法后，成功状态批量处理
	 */
	@Deprecated
	private void processPayResultByWaybill(BillCODOnlineResultDto resultDto,CODEntity entity,CurrentInfo currentInfo){

		// 查询代收货款应付单和对应的付款单
		BillPayableEntity billPayable = codCommonService.getBillPayableEntity(entity);
		BillPaymentEntity billPayment = getBillPayment(billPayable);

		// 汇款成功
		if (BillCODOnlineResultEnum.SUCCESS.equals(resultDto.getResult())) {
			entity.setComAccount(resultDto.getBankAccount()); // 把公司付款账号复制到实体上
			doWithPayCODSuccess(entity, billPayable, billPayment,currentInfo);
		}
		// 汇款失败
		else if (BillCODOnlineResultEnum.FAILURE.equals(resultDto.getResult())) {
			// 如果失败原因为空，则抛出异常
			if (StringUtils.isEmpty(resultDto.getFailNotes())) {
				throw new SettlementException(entity+"代收货款汇款失败原因为空");
			}
			doWithPayCODFailure(entity, billPayable, billPayment,currentInfo, resultDto.getFailNotes());
		}

		// 反汇款成功
		else if (BillCODOnlineResultEnum.REMITTANCE_SUCCESS_TO_FAIL.equals(resultDto.getResult())) {
			// 如果失败原因为空，则抛出异常
			if (StringUtils.isEmpty(resultDto.getFailNotes())) {
				throw new SettlementException(entity.getWaybillNo()+"代收货款汇款成功转失败原因为空");
			}
			doWithPayCODSuccessToFailure(entity, billPayable, billPayment, currentInfo,resultDto.getFailNotes());
		}
		
	}

	/**
	 * 
	 * 根据应付单查询付款单
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-8 下午5:07:55
	 */
	private BillPaymentEntity getBillPayment(BillPayableEntity billPayable)
			throws SettlementException {

		// 构建查询条件DTO
		BillPaymentConditionDto dto = new BillPaymentConditionDto();
		dto.setActive(FossConstants.ACTIVE);
		dto.setSourceBillNo(billPayable.getPayableNo());
		dto.setSourceBillType(SettlementDictionaryConstants.BILL_PAYMENT__SOURCE_BILL_TYPE__ADVANCED_PAYMENT);

		// 查询付款单
		List<BillPaymentEntity> billPayments = billPaymentService
				.queryBillPaymentByCondition(dto);

		// 如果为空，则抛出异常
		if (billPayments == null || billPayments.size() == 0) {
			throw new SettlementException("找不到代收货款应付单对应的付款单");
		}

		// 如果存在多个付款单，则抛出异常
		if (billPayments.size() > 1) {
			throw new SettlementException("代收货款应付单对应的付款单存在多个");
		}

		return billPayments.get(0);
	}

	/**
	 * 
	 * 处理代收货款汇款成功
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-8 上午11:44:39
	 */
	private void doWithPayCODSuccess(CODEntity entity,
			BillPayableEntity billPayable, BillPaymentEntity billPayment,
			CurrentInfo currentInfo) throws SettlementException {

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
		writeoffDto.setCreateType(SettlementDictionaryConstants.SETTLEMENT__CREATE_TYPE__AUTO);

		// 调用核销接口，付款冲应付操作
		billWriteoffService.writeoffCODPaymentAndPayable(writeoffDto,
				currentInfo);
		
		//付款单状态变为--已汇款
		BillPaymentDto billPaymentDto = new BillPaymentDto();
		billPaymentDto.setBillPayments(Arrays.asList(new BillPaymentEntity[]{billPayment}));// 设置更新付款单集合
		billPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);// 设置更新汇款状态为"未汇款"
		// 批量更新付款单为付款中
		billPaymentService.batchReverseRemitStatusBillPayment(billPaymentDto,currentInfo);

		// 设置代收货款退款成功状态改造成批量处理
	}

	/**
	 * 
	 * 处理代收货款线上汇款失败
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-8 下午4:50:50
	 */
	private void doWithPayCODFailure(CODEntity entity,
			BillPayableEntity billPayable, BillPaymentEntity billPayment,
			CurrentInfo currentInfo, String failNotes) {
		//记录日志

		// 调用接口红冲付款单
		billPaymentService.writeBackBillPayment(billPayment, currentInfo);

		// 更新代收货款状态为退款失败
		entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE);
		Date now = new Date();
		entity.setModifyTime(now);
		entity.setRefundSuccessTime(null);
		entity.setRemittanceFailNotes(failNotes);
		entity.setModifyUserName(currentInfo.getEmpName());
		entity.setModifyUserCode(currentInfo.getEmpCode());
		entity.setComAccount(entity.getComAccount()); // 退款失败或成功转失败赋值NULL
		entity.setMergeCode(null); // 退款失败合并编号置空
		codCommonService.updatePayCODFailure(entity, currentInfo);

		// 冻结状态、冻结时间、冻结人
		billPayable.setFrozenStatus(SettlementDictionaryConstants.BILL_PAYABLE__FROZEN_STATUS__NOT_FROZEN);
		billPayable.setFrozenTime(null);
		billPayable.setFrozenUserCode(null);
		billPayable.setFrozenUserName(null);

		// 取消冻结代收货款
		billPayableService.cancelFrozenBillPayable(billPayable, currentInfo);
	
		//ISSUE-3250 如果代收货款汇款失败，通知相关部门及时处理
		if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(billPayable.getCodType())
				||SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(billPayable.getCodType())
				||SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(billPayable.getCodType())){
			InstationJobMsgEntity entiy = new InstationJobMsgEntity();
			//发送人和发送部门信息
			entiy.setSenderCode(currentInfo.getEmpCode());
			entiy.setSenderName(currentInfo.getEmpName());
			entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
			entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
			//设置为代收货款消息
			entiy.setMsgType(MessageConstants.MSG_TYPE__CODPAYFAILD);
			//接受方式为组织
			entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
			//设置接收部门信息
			entiy.setReceiveOrgCode(billPayable.getPayableOrgCode());
			entiy.setReceiveOrgName(billPayable.getPayableOrgName());
			//设置
			entiy.setContext("你部门含有代收货款的单据："+billPayable.getWaybillNo()+"汇款失败，请及时处理！");
			messageService.createBatchInstationMsg(entiy);
		}

	}

	/**
	 * 
	 * 处理代收货款汇款成功转失败
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-8 下午5:52:00
	 */
	private void doWithPayCODSuccessToFailure(CODEntity entity,
			BillPayableEntity billPayable, BillPaymentEntity billPayment,
			CurrentInfo currentInfo, String failNotes) {
		// 调用核销单接口反核销付款冲应付
		billWriteoffService.writeBackBillWriteoffByPayment(
				billPayment.getPaymentNo(), currentInfo);

		// 上面反核销 变动应付单，为了保持后面应付单的版本号和数据库中的版本号统一，在这里+1
		billPayable.setVersionNo((short)(billPayable.getVersionNo() + 1));
		// 调用失败接口
		doWithPayCODFailure(entity, billPayable, billPayment, currentInfo, failNotes);

	}
	
	/**
	 * @param codCommonService
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}
	
	/**
	 * @param billPaymentService
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}

	/**
	 * @param billPayableService
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}
	
	/**
	 * @param billWriteoffService
	 */
	public void setBillWriteoffService(IBillWriteoffService billWriteoffService) {
		this.billWriteoffService = billWriteoffService;
	}
	
	/**
	 * @param settlementCommonService
	 */
	public void setSettlementCommonService(
			ISettlementCommonService settlementCommonService) {
		this.settlementCommonService = settlementCommonService;
	}

	/**
	 * @param paymentQueryService the paymentQueryService to set
	 */
	public void setPaymentQueryService(IPaymentQueryService paymentQueryService) {
		this.paymentQueryService = paymentQueryService;
	}
	
}

