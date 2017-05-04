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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/CodCommonService.java
 * 
 * FILE NAME        	: CodCommonService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-24 	创建版 	毛建强	V0.1
2012-07-13 	修订版	毛建强	V0.5
2012-07-25 	版本升级	毛建强	V0.9
  			

1.	SUC-310-冻结代收货款
1.1	相关业务用例
BUC_FOSS_4.7.50.30_040  资金部冻结代收货款
1.2	用例描述
资金部出纳查询代收货款状态为“未退款”并且其应付单支付状态为“可支付”的代收货款单据，进行资金部冻结操作，使营业部不能再操作该代收货款单据。资金部冻结操作会修改代收货款状态为“资金部冻结”状态。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		
后置条件	1、	修改代收货款状态为“资金部冻结”，使营业部不能再操作该单据。	
1.4	操作用户角色
操作用户	描述
资金部出纳	
1.5	界面要求
1.5.1	表现方式
Web
1.5.2	界面原型

查询冻结取消冻结界面：
  
1.5.3	界面描述
页面初始化组件描述：
查询页面：
1、	初始化退款类型数据字典到退款类型下拉列表中，默认显示“即日退”
2、	截止签收日期默认为当前日期的下一天日期00:00:00秒 
页面输入组件描述
单号：运单号集合，最多输入10个， 以“逗号”隔开 
截止签收日期：运单签收录入日期。不能大于当前日期
退款类型:代收货款退款类型
	三日退（审核退）
	即日退
账户性质：代收货款收款账号的性质
	对公
	对私
开户行:银行账号所属的开户银行。该控件为公共组件，可以选择多个开户行，显示开户行名称，以逗号隔开。也可手动输入开户行名称，以逗号隔开。
页面表格组件描述：
1、	该页面默认一页显示，不能进行分页
2、	表格最下面显示合计的总条数，总金额，冻结条数和冻结总金额
页面提供的钮控件描述：
1、	查询：查询符合退款条件的代收货款列表
2、	重置：将查询条件设置到初始化情况
3、 冻结：冻结代收货款信息，使营业部不能进行操作。 
4、 取消冻结：取消代收货款的资金部冻结状态，营业部能够查询到该条代收货款信息，从而进行操作。
5、 退款申请：选中冻结单据，点击退款申请，会调用金蝶EAS7.0接口，将数据传递过去进行退款。与汇款导出互斥，同一批数据，点击了退款申请，则不能进行汇款导出。
6、汇款导出：将冻结单据导出Excel进行汇款，与退款申请互斥。点击汇款导出，则不能再进行汇款申请。
7、全选冻结单据：勾选全部的冻结状态的单据，从而方便其进行退款申请或汇款导出。
1.6	操作步骤
1.6.1	查询
序号	基本步骤	相关数据	补充步骤
1	页面初始化		1、	退款默认显示“即日退”
2、	截止签时间默认为当前日期的下一天的00:00:00
2	如果输入单号查询 	单号集合	1、输入单号进行查询，则忽略界面其他查询条件。只按照单号进行查询。
2、规则-业务规则参见SR2
3、扩展事件参考2a
3	如果单号为空 	截止签收日期，退款类型，开户行信息	1、如果输入单号为空，则查询条件按照界面其它四个控件所选条件继续进行查询。
2、 规则-业务规则参见SR3
3、 扩展事件参考2a

1.6.2	冻结
序号	基本步骤	相关数据	补充步骤
4	勾选查询出来的代收货款数据（可批量）	代收货款信息	
5	点击冻结按钮	代收货款信息	1、	校验是否选中单据
扩展事件参见5a
2、	如果所选代收货款状态为“资金部冻结”，则抛出异常提示
扩展事件参见5b
3、	批量更新代收货款状态为“资金部冻结”。对于未冻结成功的单据，需要将界面冻结状态改为：“冻结失败原因：XXX”
规则-业务规则参见SR4
4、	如果为“审核退”，则更新代收货款对应的应付单的冻结状态为“冻结中”
5、	添加日志，用户可以通过统一的日志查询界面进行查询
1.6.3	取消冻结
7	勾选查询出来的代收货款数据（可批量）	代收货款信息	
8	点击取消冻结按钮	代收货款信息	1、	校验是否选中单据
扩展事件参见8a
2、	如果所选代收货款状态不为“资金部冻结”，则抛出异常提示
扩展事件参见8b
3、	批量更新代收货款状态为“未退款”
规则-业务规则参见SR5
4、	如果为审核退，则更新其对应的代收货款应付单的冻结状态为“未冻结”
5、	添加日志，用户可以通过统一的日志查询界面进行查询


序号	扩展事件	相关数据	备注
2a	当点击“查询”按钮，没有查询到数据时，抛出异常	提示信息	弹出框提示“没有查询到数据！”
2b	当点击“重置”按钮，系统进行重置操作		将查询条件恢复到初始化状态
5a	如果没有选中数据，进行冻结操作，弹出框提示	提示信息	请至少选择一条数据，再进行冻结操作
5b	如果所选代收货款状态为“资金部冻结”，则弹出框提示	提示信息	“已经冻结的单据不能再次冻结”
8a	如果没有选中数据，进行取消冻结操作，弹出框提示	提示信息	请至少选择一条数据，再进行取消冻结操作
8b	如果所选单据代收货款状态不是“资金部冻结”，则弹出提示框提示	提示信息	“代收货款必须是资金部冻结才能进行取消冻结操作”

1.7	业务规则
序号	描述
SR1	1、	代收货款状态为“未退款”或“资金部冻结”
2、	代收货款对应的应付单支付状态为“可支付”
3、	不存在未受理的始发更改单和到达更改单
SR2	1、	查询条件等于SR1
2、	单号等于输入运单号集合中一个单号
SR3	1、	查询条件等于SR1
2、	代收货款签收日期必须小于等于界面截止签收日期
3、	退款类型等于界面所选择退款类型
4、	开户行等于界面开户行中的一个开户行
5、	所选账号的对公对私标志等于所选账户性质
SR4	批量更新冻结状态为“资金部冻结”，更新条件：
1、	代收货款状态为“未退款”
2、	代收货款对应的应付单支付状态为“可支付
3、	不存在未受理的始发跟改单和到达更改单
SR5	批量更新代收货款状态为“未退款”
1、代收货款状态为“资金部冻结”
1.8	数据元素
1.8.1	页面查询条件（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

  单号	运单号集合	文本	请输入正确单号：8-10位数字	10	否	最多10个，逗号/换行分隔
截止签收日期	运单签收日期	日期	运单签收日期	30	否	无
退款类型	退款类型	下拉框	无	10	否	无
开户行	开户银行	公共组件可多选	无	30	否	无
账户性质	汇款账号的性质：“对公”还是“对私”	下拉框	无	10	否	无

1.8.2	查询显示代收货款信息（输出）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
代收货款类型	代收货款退款类型	无	无	20	是	代收货款类型
运单号	运单单号	无	无	10	是	运单单号
应退金额	代收货款退款金额	无	无	20	是	代收货款要退款的金额
应付部门	代收货款所属部门	无	无	参照统一标准	是	等于当前登录部门
冻结状态	资金部冻结状态	无	无	20	是	无
收款人	代收货款收款人姓名	无	无	20	是	无
账号	收款账号	无	无	50	是	无
开户行	收款账号所属银行	无	无	20	是	无
省份	收款账号所属银行所在的省份	无	无	20	是	无
城市	收款账号所属银行所在的城市	无	无	20	是	无
支行	收款账号所属银行的详细地址	无	无	50	是	无
对公对私标志	账号的对公对私标志	无	无	50	是	无
签收时间	运单签收时间	无	无	50	是	无
手机号	收款人手机号	无	无	50	是	无
所属子公司	该应付单的应付部门对应的子公司	无	无	50	是	无
银行行号	开户银行的行号	无	无	50	是	无

1.9	非功能性需求
使用量	3次/天
2012年全网估计用户数	1人
响应要求（如果与全系统要求 不一致的话）	10分钟以内
使用时间段	资金部上班时间
高峰使用时间段	9:00~10:00，13:30~16:00

1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
判断是否存在未受理的到达更改单	接送货	判断该单号是否存在未受理的更改单


修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-06-16 	创建版 	毛建强	V0.1
2012-07-13 	修订版	毛建强	V0.5
2012-07-25 	版本升级	毛建强	V0.9
2012-10-24
增加代收货款整批退回功能	保轶	V1.2

1.	SUC-714-接收代收货款汇款状态（费控接口）
1.1	相关业务用例
BUC_FOSS_4.7.30.10_080  给客户汇款代收货款
1.2	用例描述
资金部审核代收货款后，通过本接口更新代收货款批次状态为“审核通过”或“退回”。审核通过时，生成付款单（不核销）；退回时，更新代收货款状态为“已冻结”。参见SUC-361-汇代收货款给客户。
费控系统在汇代收货款之后，返回汇款成功信息，也调用该接口，核销对应的应付单，且更新代收货款状态为“退款成功”。如果汇款失败，则红冲对应的付款单，且更新代收货款状态为“退款失败”
1.3	用例条件
条件类型	描述	引用系统用例
前置条件		费控调用
后置条件	1.	如果汇款成功，核销应付单
2.	如果汇款失败，红冲付款单
3.	更新代收货款状态	
1.4	操作用户角色
操作用户	描述
	
1.5	界面要求
1.5.1	表现方式
接口
1.5.2	界面原型
无
1.5.3	界面描述
无
1.6	操作步骤
1.6.1	接受代收货款汇款状态（费控接口）
序号	基本步骤	相关数据	补充步骤
1	校验传入退款信息集合	退款信息	1、	扩展事件参见1a
2、	业务规则参见SR1
2	检查传入退款信息的代收货款编号	代收货款编号	1、	扩展事件参见2a
2、	业务规则参见SR2
3	检查传入退款信息的支付状态	退款信息	1、	扩展事件参见3a
2、 业务规则参见SR3
4	检查传入的批次号 	退款信息	1、	扩展事件参见4a
2、	业务规则参见SR4
5	根据批次号和代收货款编号，查找对应的代收货款单据	代收货款信息	1、	扩展时间参见5a
2、	业务规则参见SR5
6	如果支付状态为“成功”，则核销应付单	应付单、付款单、核销单、代收货款单据、代收货款日志	1、	调用核销接口进行付款冲应付，生成核销单为“自动核销”
2、	更新代收货款状态为“已退款”
3、 增加代收货款日志
4、规则-规则参见SR6

7	如果支付状态为“失败”,则更新代收货款单据状态	应付单、付款单、代收货款单据、代收货款日志	1、	红冲付款单
2、	更新代收货款状态为“退款失败”,更新退款失败原因为传入的退款失败的原因
3、	更新代收货款应付单的冻结状态为“未冻结”
4、	增加代收货款日志
5、	规则-规则参见SR6
8	如果支付状态为 “退款成功转失败”,则更新代收货款单据状态	代收货款单、应付单、付款单、核销单、代收货款日志	1、	红冲核销单
2、	红冲付款单
3、	更新代收货款应付单的金额为付代收货款之前金额，代收货款应付单的冻结状态为“未冻结”
4、	更新代收货款状态为“退款失败”，更新退款失败原因为传入的退款失败原因
5、	增加代收货款日志
6、	规则-规则参见SR6
9	如果支付状态为“审核通过”，则更新代收货款批次状态，生成付款单	代收货款批次
付款单
代收货款日志	1、	更新代收货款批次状态为“审核通过”
2、	代收货款单状态不变
3、	生成付款单，不核销应付
4、	增加代收货款日志
10	如果支付状态为“退回”，则更新代收货款批次状态、代收货款状态	代收货款批次
代收货款单
代收货款日志	1、	更新代收货款批次状态为“退回”
2、	更新代收货款单状态为“已冻结”
3、	增加代收货款日志

扩展事件
序号	扩展事件	相关数据	备注
1a	如果传入的退款信息集合为空，则直接返回错误信息	信息内容	返回错误信息：“传入退款信息不能为空”
2a	如果传入的代收货款编号为空，则直接返回错误信息	信息内容	返回错误信息：“传入的代收货款编号不能为空”
3a	校验支付状态失败，返回错误信息	信息内容	1、如果支付状态为空，则返回错误信息：“传入的支付状态不能为空”
4a	如果传入的批次号为空，则直接返回错误信息	信息内容	返回错误信息：“传入的批次号不能为空”
5a	如果没有查询到代收货款单据，则直接返回错误	信息内容	返回错误信息：“该批次号和代收货款编号没有对应的代收货款信息”

1.7	业务规则
序号	描述
SR1	1、	退款信息集合不能为空
SR2	1、代收货款编号信息不能为空
SR3	1、	支付状态不能为空
2、	支付状态只能是“成功”或“失败”或“退款成功转失败”
SR4	1、	批次号不能为空
SR5	必须存在代收货款单据
SR6	代收货款更新条件：
1、	代收货款编号等于传入代收货款编号
2、	代收货款批次号等于传入批次号

1.8	数据元素
1.8.1	接口输入参数
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

运单号	运单号	文本	无	40	是	无
支付状态	成功或失败或退款成功转失败	文本	无	6	是	无
批次号	汇款的批次号	文本	无	40	是	无
失败原因	退款失败或成功转失败的原因	文本	无	255	是	无

1.8.2	更新应付单（输出）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

未核销金额	应付单的未核销金额	数字	无	10	是	无
已核销金额	应付单的已核销金额	数字	无	10	是	无

1.8.3	红冲付款单（输出）
参见《DP-FOSS-结算系统用例-录入付款单》付款单信息数据元素
1.8.4	红冲付款单明细（输出）
参见《DP-FOSS-结算系统用例-录入付款单》付款单明细信息数据元素
1.8.5	更新代收货款单据（输出）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

代收货款状态	代收货款的状态	文本	无	10	是	无
退款失败原因	退款失败或成功转失败的原因	文本	无	255	是	无

1.8.6	核销单信息（输出）
参见《DP-FOSS-结算系统用例-应收冲应付（后台）》核销单信息数据元素
1.8.7	核销单明细信息（输出）
参见《DP-FOSS-结算系统用例-应收冲应付（后台）》核销单明细信息数据元素

1.9	非功能性需求
使用量	每天不超过10次
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	每条单据的反写完成时间为5s内
使用时间段	金蝶7.0系统汇款成功，调用此接口时间
高峰使用时间段	金蝶7.0系统汇款成功，调用此接口时间

1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		
修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-11 	创建版 	毛建强	V0.1
2012-07-16 	修订版 	毛建强	V0.5
2012-07-25 	版本升级	毛建强	V0.9
2012-10-12
增加冻结和修改账号的颜色变化描述	毛建强	V1.0

1.	SUC-312-锁定代收货款
1.1	相关业务用例
BUC_FOSS_4.7.50.30_050  修改客户银行帐号
1.2	用例描述
营业部收银员对代收货款记录进行营业部冻结，会将代收货款状态修改为“营业部冻结”状态,收银员通过该功能修改该条代收货款信息中的银行账号信息。 
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、营业部冻结：
1）代收货款应付单还没有进行退款或没被资金部冻结或没有在退款中
2）存在对应的代收货款单据
2、修改账号
  1）该代收货款已经被营业部冻结
  2）该代收货款存在对应的要修改的银行账号	《确认代收货款退款申请》系统用例中查询
后置条件	1.	修改代收货款状态为“营业部冻结”
2.	修改代收货款银行账号信息为所选择新银行账号信息	
1.4	操作用户角色
操作用户	描述
收银员	收银员进行营业部冻结、修改账号和审核操作
1.5	界面要求
1.5.1	表现方式
Web
1.5.2	界面原型
查询冻结界面：
 
修改账号界面：
 
1.5.3	界面描述
页面初始化组件描述：
查询页面：
1、	退款状态默认显示“待审核或退款失败”
修改账号界面：
1、	界面输入框除了银行账号外，都为不可编辑状态
2、	默认将所选代收货款记录的银行账号信息初始化到修改银行账号界面对应组件中
页面输入组件描述
查询页面：
1、	代收货款状态：代收货款操作状态
	待审核或退款失败
	未退款
2、	单号查询：输入多个单号，以逗号隔开，最多不能超过10个。查询对应单号代收货款信息。单号默认为8-10位数字
修改账号界面：
1、银行账号控件默认为公共查询组件，且不可手动输入，只能选择。不能为空。选择银行账号，默认会更新界面上对应控件的信息为新账号对应的值。
页面表格组件描述：
1、	默认为分页显示，每页显示数据可选择20,30,50,100
页面提供的钮控件描述：
1、查询：查询代收货款列表功能
2、营业部冻结：冻结所选的代收货款信息，使资金部不能操作冻结的代收货款
3、修改账号：弹出客户账号信息，可以进行银行账号修改
4、审核：进行审核操作。参见系统用例《DP-FOSS-结算系统用例-确认代收货款退款申请》

1.6	操作步骤
1.6.1	营业部冻结
序号	基本步骤	相关数据	补充步骤
1	查询数据	代收货款信息	参见《确认代收货款退款申请》系统用例中查询 
2	勾选查询出来的代收货款数据	代收货款信息	
3	点击营业部冻结按钮	代收货款信息	1、	判断是否选中行，如果没选中，则弹出提示
扩展事件参见3a
2、	判断如果所选记录代收货款状态为“营业部冻结”,则弹出提示
扩展事件参见3b
3、	更新代收货款的操作状态为“营业部冻结”，同时更新冻结单据颜色为橘黄色
业务规则请参见SR1
4、	添加日志，用户可以通过统一的日志查询界面进行查询

1.6.2	修改账号
序号	基本步骤	相关数据	补充步骤
4	勾选查询出来的代收货款数据（单选）	代收货款信息	
5	点击修改账号按钮	代收货款信息	1、	判断所选记录数不等于1，则弹出提示
参见扩展事件5a
2、	判断所选中记录代收货款状态是否为“营业部冻结”，如果不是则弹出提示
参见扩展事件5b
3、	弹出修改账号界面
4、	将所选代收货款信息填充到界面对应控件中
6	点击客户银行账号公共组件	银行账号信息	1、调用综合管理系统接口，查询该客户银行账号信息，显示在基础控件中
2、选择对应账号信息，判断如果当前代收货款为即日退且所选账号的银行不属于即日退开单银行限制基础资料中的银行，则弹出提示。反之将该账号信息重新填充到界面对应的控件
3、 判断如果当前代收货款为即日退且所选银行账号的对公对私标志如果为“对公”，则弹出提示
参见扩展事件6a
7	点击提交按钮		1、	关闭修改账号按钮界面
2、	修改对应代收货款对应行的银行账号信息、银行、收款人、客户收款人关系等信息
3、	同时更新界面上该条代收货款信息，且修改该条记录颜色为红色
扩展事件参见7a
业务规则参见SR2
4、	添加日志，用户可以通过统一的日志查询界面进行查询

序号	扩展事件	相关数据	备注
3a	如果没选中行，点击营业部冻结按钮，则弹出提示	提示信息	提示信息：“请至少选中1条记录进行冻结操作！”
3b	所选记录代收货款状态为“营业部冻结”,则弹出提示	提示信息	“该记录已经被营业部冻结，不能重复冻结”
5a	若果所选记录数不等于1，则弹出异常提示	提示信息	“请选中一条数据进行修改账号操作，只能选择一条哦！”
5b	如果所选记录代收货款状态不是“营业部冻结”，则弹出异常提示	提示信息	“必须先进行营业部冻结，才能修改账号！”
6a	如果校验账号信息错误，则直接返回，弹出错误信息	提示信息	1、	如果代收货款是即日退，并且所选银行不在即日退限制银行范围内，则弹出提示 “所选账号的银行暂不支持即日退。”
2、	如果代收货款是即日退且所选账号为“对公”，则弹出异常提示：“即日退不能选择对公账号！”

7a	点击“取消”按钮		关闭修改账号界面，不修改

1.7	业务规则
序号	描述
SR1	更新选中数据的代收货款状态为“营业部冻结”状态。更新条件：
1、	代收货款状态必须是“未退款”或“待审核”或“退款失败”
SR2	更新对应代收货款中“收款人”、“银行账号”、“开户行”、“客户收款人关系”、“省份”、“城市”、“收款人手机号”，“开户行支行”，“对公对私标志”信息
更新条件：
代收货款状态为“营业部冻结”
转化规则：
收款人 ：弹出银行账号界面的开户姓名
银行账号：弹出银行账号界面的银行账号
开户行：弹出银行账号界面的开户银行编码
客户收款人关系：弹出银行账号界面的客户收款人关系
开户行支行：弹出银行账号界面的银行地址
省份：弹出银行账号界面的开户行省份
城市：弹出银行账号界面的开户行城市
收款人手机号：弹出银行账号界面的手机号码
对公对私标志：弹出界面银行账号对公私标志
银行行号：银行的行号

1.8	数据元素
1.8.1	页面查询条件（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

代收货款状态	代收货款状态	枚举	代收货款退款状态	10	是	无
  单号	运单号	文本	请输入正确单号：8-10位数字	10	否	无

1.8.2	代收货款信息（输出）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
运单号	运单号	无	无	10	无	来源于运单
开单金额	代收货款金额	无	无	10	无	运单开单确认的代收货款
冲应收金额	冲销的金额	无	无	10	无	运单应收冲应付的金额
应退金额	应付代收货款金额。取应付单未核销金额	无	无	10	无	开单金额=冲应收金额+应退金额
退款类型	代收货款退款类型	无	无	20	无	无
退款状态	代收货款退款状态	无	无	20	无	无
客户名称	发货客户名称	无	无	20	无	来源于运单
收款人	代收货款收款人姓名	无	无	20	无	来源于crm
账号	收款账号	无	无	40	无	来源于crm
开户行	收款账号所属银行	无	无	20	无	来源于crm
客户收款人关系	发货客户与收款人关系	无	无	50	无	来源于crm
对公对私标志	账号类型	无	无	20	无	隐藏
省份	账号所属银行所在省份	无	无	20	无	隐藏
城市	账号所属银行所在城市	无	无	20	无	隐藏
银行地址（支行）	账号所属银行具体地址	无	无	50	无	隐藏
收款人手机号	账号开户人的手机号	无	无	11	无	隐藏
银行行号	银行行号	无	无	50	无	无
开单日期	运单开单日期	无	无	参照统一标准	无	无
部门	代收货款所属部门	无	无	一标准	无	等于当前登录部门
备注	备注信息	无	无	255	无	显示代收货款备注信息：如资金部汇款失败，会填写失败原因到备注中
1.8.3	代收货款更新的字段（输出）
字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
代收货款状态	代收货款的状态	枚举	无	50	是	无
收款人	代收货款银行账号对应的收款人	文本	无	50	是	无
账号	代收货款银行账号	文本	无	50	是	无
开户行	银行账号所属开户行	文本	无	20	是	无
银行地址（支行）	开户银行详细地址	文本	无	100	是	无
客户收款人关系	客户收款人关系	文本	无	100	是	无
省份	开户银行所属省份	文本	无	20	是	无
城市	开户银行所属城市	文本	无	20	是	无
收款人手机号	收款人手机号	文本	无	11	是	无
对公对私标志	银行账号属性表明是对公还是对私	文本	无	20	是	无

1.9	非功能性需求
使用量	每天收银员审核约为700票
2012年全网估计用户数	收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
响应要求（如果与全系统要求 不一致的话）	响应时间2-5s以内 
使用时间段	营业部正常上班时间
高峰使用时间段	8:00-17:30

1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
获取客户账号信息接口	综合管理系统	通过客户获取客户账号信息


修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-11 	创建版 	毛建强	V0.1
2012-07-16 	修订版 	毛建强	V0.5
2012-07-25 	版本升级	毛建强	V0.9
  			

1.	SUC-139-确认代收货款退款申请
1.1	相关业务用例
BUC_FOSS_4.7.50.30_020  出纳审核代收货款
1.2	用例描述
营业部收银员确认代收货款信息后，进行审核代收货款操作,将代收货款更细状态为“收银员审核”状态。 
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	进行营业部冻结或修改过账号的有效的代收货款单据
2、	审核退进行应收冲应付后还有未核销金额的代收货款单据
3、	代收货款状态为退款失败	
后置条件	更新代收货款状态为“收银员审核”	
1.4	操作用户角色
操作用户	描述
收银员	收银员可以进行营业部冻结、修改账号和审核等操作
1.5	界面要求
1.5.1	表现方式
Web
1.5.2	界面原型
 
1.5.3	界面描述
页面初始化组件描述：
1、	代收货款状态默认显示“待审核或退款失败”
2、	单号文本域默认为空
页面输入组件描述
1、	代收货款状态：代收货款的操作状态
	待审核或退款失败
	未退款
2、	单号查询：输入多个单号，以逗号隔开，最多不能超过10个。
页面表格组件描述：
1、	默认为分页显示，每页显示数据可选择20,30,50,100
页面提供的按钮控件描述：
1、	查询：查询代收货款列表功能
2、	重置：恢复查询条件到界面初始化状态
3、	审核：进行审核操作
4、	营业部冻结：将代收货款单据进行冻结，使其不能在资金部退款。参见系统用例《DP-FOSS-结算系统用例-锁定代收货款》
5、修改账号：营业部收银员点击该按钮，弹出修改账号界面，从而修改代收货款的退款账号信息。参见系统用例《DP-FOSS-结算系统用例-锁定代收货款》
1.6	操作步骤
1.6.1	查询
序号	基本步骤	相关数据	补充步骤
1	页面初始化		1、	代收汇款状态下拉框默显示“待审核或退款失败”
2、	页面表格不显示
2	如果选择了代收货款状态，但是没有填写单号，点击“查询”按钮	代收货款状态	1、查询出该部门符合所选退款条件的代收货款单据
2、规则-业务规则参见SR1
3、扩展事件参考2b、2c
3	如果输入单号，点击“查询”按钮	输入单号	1、	判断单号是否超过10个，如果超过10个，则弹出提示
扩展事件参见2a
2、	查询该单号的代收货款信息
3、	规则-业务规则参见SR2
4、	扩展事件参考2b、2c

1.6.2	审核
序号	基本步骤	相关数据	补充步骤
4	点击查询按钮，查询到数据后。勾选查询出来的代收货款数据（可多选）	代收货款信息	
5	点击“审核”按钮	代收货款信息	1、	判断是否选中数据，如果没选中数据，则弹出提示
扩展事件参见5a
2、	调用综合管理接口，判断所选账号是否存在对应的账号信息，如果不存在则抛出异常提示
扩展事件参见5b
3、	更新代收货款状态为“收银员审核”。
业务规则请参加SR3
4、	添加日志，用户可以通过统一的日志查询界面进行查询

序号	扩展事件	相关数据	备注
2a	如果输入单号超过10个，则在输入框旁边提示	提示信息	输入单号不能超过10个
2b	当点击“查询”按钮，没有查询到数据时	提示信息	弹出框提示“没有查询到数据！”
2c	重置	提示信息	恢复界面控件到初始化状态
5a	如果没选中数据，则弹出提示	提示信息	请至少选中一条数据进行审核！
5b	如果代收货款账号不存在于crm中的客户银行账号信息，则弹出提示信息	提示信息	该账号不存在对应的账号信息，请选择重新修改账号

1.7	业务规则
序号	描述
SR1	1、	代收货款对应的应付单的应付部门为当前登录部门
2、	若代收货款状态为“待审核或退款失败”，则代收货款状态等于“退款失败”或“待审核”或“营业部冻结”
3、	若代收货款状态为“未退款”，则查询出代收货款状态等于“未退款”
SR2	1、	代收货款的运单号等于所输入运单号
2、	代收货款状态为“退款失败”或“待审核”或“未退款”或“营业部冻结”
SR3	更新选中数据的代收货款状态为“收银员审核”状态。
1、	代收货款状态等于“退款失败”或“待审核”或“未退款”或“营业部冻结”
2、	代收货款对应的应付单的挂账部门为当前登录部门

1.8	数据元素
1.8.1	页面查询条件（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

代收货款状态	代收货款状态	枚举	代收货款退款状态	10	是	无
  单号	运单号	文本	无	30	否	无

1.8.2	代收货款列表信息（输出）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
运单号	运单号	无	无	10	无	来源于运单
应付单编号	应付单对应的编号	无	无	30	无	来源于应付单
开单金额	代收货款金额	无	无	10	无	运单开单确认的代收货款
冲应收金额	冲销的金额	无	无	10	无	运单应收冲应付的金额
应退金额	应付代收货款金额。取应付单未核销金额	无	无	10	无	开单金额=冲应收金额+应退金额
退款类型	代收货款退款类型	无	无	20	无	无
代收货款状态	代收货款状态	无	无	20	无	无
客户名称	发货客户名称	无	无	20	无	来源于运单
收款人	代收货款收款人姓名	无	无	20	无	
账号	收款账号	无	无	40	无	
开户行	收款账号所属银行	无	无	20	无	
客户收款人关系	发货客户与收款人关系	无	无	50	无	
对公对私标志	账号类型	无	无	20	无	隐藏
省份	账号所属银行所在省份	无	无	20	无	隐藏
城市	账号所属银行所在城市	无	无	20	无	隐藏
银行地址（支行）	账号所属银行具体地址	无	无	50	无	隐藏
收款人手机号	账号开户人的手机号			11		隐藏
业务日期	应付单的业务日期	无	无	参照统一标准	无	无
部门	应付单的应付部门	无	无	一标准	无	等于当前登录部门
备注	备注信息	无	无	255	无	显示代收货款备注信息：如经理退回或资金部退回等填写退回原因 

1.8.3	更新代收货款状态（输出）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
代收货款状态	代收货款的操作状态	无	无	30	是	“收银员审核”

1.9	非功能性需求
使用量	每天审核数据约为700票
2012年全网估计用户数	收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
响应要求（如果与全系统要求 不一致的话）	响应时间2-5s以内 
使用时间段	全天
高峰使用时间段	8:00-17:30

1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
调用查询客户账号信息接口	综合管理系统 	获取对应客户的开户行信息

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-24 	创建版 	毛建强	V0.1
2012-07-16 	修订版 	毛建强	V0.5
2012-07-25 	版本升级 	毛建强	V0.9
2012-10-12
1、	统一修改出纳审核为收银员审核
2、修改经理审核中，更新代收货款状态SR3描述，增加区分签收前和签收后	毛建强	V1.0

1.	SUC-315-审核支付申请
1.1	相关业务用例
BUC_FOSS_4.7.50.30_060  审核代收货款支付申请
1.2	用例描述
营业部经理将被收银员审核通过的代收货款记录，并可以选择需要审核的记录进行审核，也可对有问题的记录进行退回。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、营业部收银员已审核代收货款。	
后置条件	1、	修改代收货款支付申请的退款状态为“未退款”，使其在资金部可以查询到	
1.4	操作用户角色
操作用户	描述
营业部经理	可以进行审核和退款操作
1.5	界面要求
1.5.1	表现方式
Web
1.5.2	界面原型

查询冻结界面：
 

经理审核填写密码界面：
 
填写退回理由界面：
 

1.5.3	界面描述
页面初始化组件描述：
查询页面：
1、	单选框默认勾选“查询所有”按钮
2、	输入文本框默认为灰色不可编辑
页面输入组件描述
查询页面：
1、	查询所有：查询出本部门所有符合经理审核条件的代收货款单据
2、	查询单号：输入多个单号（可以为运单号），以逗号隔开进行查询。当勾选“单号查询”时，输入文本框为可编辑状态。最多为10个单号
填写密码界面：
1、	该文本框填写密码显示为“密码”格式且不能为空
2、	点击确认和取消界面自动关闭该界面
退回原因界面：
1、	该文本域提交时不可以为空
2、	点击确认和取消界面自动关闭该界面
页面表格组件描述：
1、	默认为分页显示，每页显示数据可选择20,30,50,100
页面提供的钮控件描述：
1、查询：查询代收货款列表功能
2、审核：审核收银员确认之后的代收货款单据。审核通过后，资金部可以查询到该单进行退款。点击审核按钮，弹出填写密码界面，填写登陆密码进行审核
3、退回：经理退回收银员确认之后有问题的单据。使收银员重新对该单进行操作。点击退回按钮，会弹出填写退回原因界面，填写原因，退回该信息。 
1.6	操作步骤
1.6.1	查询
序号	基本步骤	相关数据	补充步骤
1	页面初始化		1、默认勾选“查询所有”单选框2、输入文本框为灰色不可编辑
2	如果查询所有		1、查询出当前登录部门所有符合经理审核条件的代收货款信息
2、业务规则参见SR1
3、扩展事件参考2a
3	如果按单号查询	单号集合	1、查询出该部门该单号的代收货款信息
2、业务规则参见SR1
3、扩展事件参考2a

1.6.2	经理审核
序号	基本步骤	相关数据	补充步骤
4	勾选查询出来的代收货款数据（可批量）	代收货款信息	
5	如果点击审核按钮	代收货款信息	1、	如果没有勾选数据，则弹出提示
扩展事件参见5a
2、	弹出填写密码界面
6	输入登录密码点击确定	登录密码	1、	调用综合管理接口，校验密码
业务规则参见SR2
参见扩展事件6a,6c
2、	更新代收货款状态
业务规则参见SR3
3、	添加日志，用户可以通过统一的日志查询界面进行查询
1.6.3	退回
7	如果点击退回按钮	代收货款信息	1、如果没有勾选数据，则弹出提示
扩展事件参见7a
2、弹出填写退回意见界面
8	填写退回意见，点击确定按钮	退回意见	1、	判断填写的退回意见是否为空，若为空，则弹出提示
扩展事件参见8a，8b
2、	更新代收货款状态为“待审核”，更新代收货款备注为退回原因
业务规则参见SR4
3、	添加日志，用户可以通过统一的日志查询界面进行查询


序号	扩展事件	相关数据	备注
2a	当点击“查询”按钮，没有查询到数据时	提示信息	弹出框提示“没有查询到数据！”
5a	如果没有选择数据，点击“经理审核”，则直接返回异常提示	提示信息	弹出框提示“请至少选择1条数据进行经理审核！”
6a	校验密码失败，则直接返回并弹出提示	提示信息	1、	如果密码为空，弹出框提示:“密码不能为空！”
2、	如果密码不等于当前登录用户的登录密码，弹出框提示：“输入密码有误，请重新输入！”
6c	点击取消按钮		自动关闭填写密码界面
7a	如果没有选择数据，点击“退回”，则直接返回异常提示	提示信息	弹出框提示“请至少选择1条数据进行退回操作！”
8a	输入框旁边提示	提示信息	退回意见不能为空！
8b	点击取消按钮		自动关闭填写意见界面

1.7	业务规则
序号	描述
SR1	1、	代收货款应付单的应付部门编码等于当前登录部门编码
2、	代收货款状态为“收银员审核”
SR2	1、	输入的密码不能为空
2、	密码等于当前用户的登录密码
SR3	如果为运单签收之后，则更新代收货款状态为“未退款”，如果为运单签收之前，代收货款如果是三日退或即日退，则更新代收货款状态为“未退款”，审核退则更新为“待审核”。更新条件：
1、	代收货款状态为“收银员审核”
2、代收货款对应的应付单的应付部门编码等于当前登录部门编码

SR4	更新代收货款状态为“待审核”，同时更新代收货款备注为退回原因，更新条件：
1、	代收货款状态为“收银员审核”
2、代收货款对应的应付单的应付部门编码等于当前登录部门编码

1.8	数据元素
1.8.1	页面查询条件（输入）
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
备注

  单号	运单号	文本	8-10位数字	30	否	无
1.8.2	查询显示代收货款信息（输出）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
运单号	运单号	无	无	10	无	来源于运单
应付单编号	应付单对应的编号	无	无	30	无	来源于应付单
开单金额	代收货款金额	无	无	10	无	运单开单确认的代收货款
冲应收金额	冲销的金额	无	无	10	无	运单应收冲应付的金额
应退金额	应付代收货款金额。取应付单未核销金额	无	无	10	无	开单金额=冲应收金额+应退金额
退款类型	代收货款退款类型	无	无	20	无	无
代收货款状态	代收货款状态	无	无	20	无	无
客户名称	发货客户名称	无	无	20	无	来源于运单
收款人	代收货款收款人姓名	无	无	20	无	
账号	收款账号	无	无	40	无	
开户行	收款账号所属银行	无	无	20	无	
客户收款人关系	发货客户与收款人关系	无	无	50	无	
对公对私标志	账号类型	无	无	20	无	隐藏
省份	账号所属银行所在省份	无	无	20	无	隐藏
城市	账号所属银行所在城市	无	无	20	无	隐藏
银行地址（支行）	账号所属银行具体地址	无	无	50	无	隐藏
收款人手机号	账号开户人的手机号			11		隐藏
业务日期	应付单的业务日期	无	无	参照统一标准	无	无
部门	应付单的应付部门	无	无	一标准	无	等于当前登录部门
备注	备注信息	无	无	255	无	显示代收货款备注信息：如经理退回或资金部退回等填写退回原因 

1.8.3	更新字段（输出）
字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
代收货款状态	代收货款的操作状态	无	无	10	无	未退款或待审核
代收货款备注	代收货款备注	无	无	10	无	审核退回时填写的原因

1.9	非功能性需求
使用量	经理审核操作全国每天约为1200次
2012年全网估计用户数	经理数量约2500名，其增长速度与网点增长速度成正比
响应要求（如果与全系统要求 不一致的话）	响应时间2-5s以内 
使用时间段	营业部正常上班时间（8:00-17：30）
高峰使用时间段	8:00-17:30

1.10	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
校验输入密码是否与当前登录用户的密码一致	综合管理系统	校验输入密码是否与当前登录用户的密码一致


 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
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

import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPayableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementReportNumber;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPayableConditionDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ICODEntityDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodLogService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODLogEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODWaybillNoQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODMergeDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODPayableDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODQueryByWaybillDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代收货款记录管理服务.
 *
 * @author 000123-foss-huangxiaobo
 * @date 2012-10-25 下午6:08:47
 */
public class CodCommonService implements ICodCommonService {

	/** The Constant logger. */
	private static final Logger logger = LogManager
			.getLogger(CodCommonService.class);

	/** 代收货款实体Dao. */
	private ICODEntityDao codEntityDao;

	/** 代收货款日志Dao. */
	private ICodLogService codLogService;

	/** 应付单服务. */
	private IBillPayableService billPayableService;
	
	/** 坏账Service. */
	//private IBillBadAccountService  billBadAccountService;

	/**
	 * 按运单号进行查询.
	 *
	 * @param waybillNumber the waybill number
	 * @return the cOD entity
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-26 下午5:37:16
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryByWaybill(java.lang.String)
	 */
	@Override
	public CODEntity queryByWaybill(String waybillNumber) {
		if (StringUtils.isNotEmpty(waybillNumber)) {

			CODQueryByWaybillDto dto = new CODQueryByWaybillDto();
			dto.setWaybillNo(waybillNumber);
			dto.setActive(FossConstants.ACTIVE);

			return this.codEntityDao.queryByWaybill(dto);

		} else {
			throw new SettlementException("内部错误，运单号为空");
		}
	}
	
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-短期
	 */
	@Override
	public List<BillPayableEntity> queryShortNonRefundCod(String billType) {
		
		if(StringUtils.isBlank(billType)){
			throw new SettlementException("参数为空！");
		}
		logger.info("process 在CodCmommonService queryShortNonRefundCod长期未退款代收货款冻结job开始!");
		return codEntityDao.queryShortNonRefundCodLock(billType);
		
	}
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2016-07-08 09:21:00
	 * 查询已签收但长期未支付代收货款-长期
	 */
	@Override
	public List<BillPayableEntity> queryLongNonRefundCod(String billType) {
		
		if(StringUtils.isBlank(billType)){
			throw new SettlementException("参数为空！");
		}
		logger.info("process 在CodCmommonService queryLongNonRefundCod长期未退款代收货款冻结job开始!");
		return codEntityDao.queryLongNonRefundCodLock(billType);
		
	}
	
	
	/**
	 * 检查运单对应的代收货款是否已付款（冻结之后）.
	 *
	 * @param waybillNo the waybill no
	 * @param signType String . 签收类型: 专线正常签收("LS")、偏线正常签收("PLS")、空运正常签收("AS")
	 * @return true:检查通过;false:检查不通过
	 * @author guxinhua
	 * @date 2013-3-11 下午6:10:54
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#checkCodHasPaymentByWaybillNo(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkCodHasPaymentByWaybillNo(String waybillNo,String signType) {
		
		logger.info("开始检查运单对应的代收货款是否已付款（冻结之后）");
		
		if (StringUtils.isBlank(waybillNo)) {
			throw new SettlementException("内部错误，运单号为空");
		}
		
		// 签收类型list
		List<String> signTypeList = new ArrayList<String>(SettlementReportNumber.THREE);
		signTypeList.add(SettlementConstants.LINE_SIGN);
		signTypeList.add(SettlementConstants.AIR_SIGN);
		signTypeList.add(SettlementConstants.PARTIAL_LINE_SIGN);
		if (StringUtils.isBlank(signType) || !signTypeList.contains(signType)) {
			throw new SettlementException("内部错误，签收类型异常：" + signType);
		}

//		int i = billBadAccountService.queryByWaybillNO(waybillNo);
//		if( i > 0 ){
//			throw new SettlementException("该运单存在坏账信息，不能进行后续操作！");
//		}

		// 只有专线和空运有代收货款 不能存在资金部冻结、退款中、退款成功的代收货款信息
		if (StringUtils.equals(signType, SettlementConstants.LINE_SIGN)
				|| StringUtils.equals(signType,SettlementConstants.AIR_SIGN)) {
			
			CODQueryByWaybillDto dto = new CODQueryByWaybillDto();
			dto.setWaybillNo(waybillNo);
			dto.setActive(FossConstants.ACTIVE);
			CODEntity cod = codEntityDao.queryByWaybill(dto);
			if(null != cod){
				//空运反签收时
				if(StringUtils.equals(signType, SettlementConstants.AIR_SIGN)
						&&StringUtils.equals(cod.getAirStatus(), SettlementDictionaryConstants.COD__AIR_STATUS__AUDIT_AGREE)){
					throw new SettlementException("空运代收货款已审核，不能进行后续操作！");
				}
				
				// 冻结之后状态
				List<String> statusList = new ArrayList<String>(5);
				statusList.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);
				statusList.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
				statusList.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
				statusList.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
				statusList.add(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);
				if(StringUtils.isNotBlank(cod.getStatus())){
					if(statusList.contains(cod.getStatus())){
						throw new SettlementException("存在退款中或退款成功的代收货款，不能进行后续操作");
					}
				}else{
					throw new SettlementException("代收货款状态异常：" + cod.getStatus());
				}
			}
		}
		
		// 判断不能存在服务补救应付单和理赔应付单
		BillPayableConditionDto billPayableConDto = new BillPayableConditionDto();
		billPayableConDto.setWaybillNo(waybillNo);
		// 专线反签收
		if (SettlementConstants.LINE_SIGN.equals(signType)) {
			billPayableConDto
					.setBillTypes(new String[] {
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM,// 理赔应付单
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION,// 服务补救应付单
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD,// 代收货款应付单
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__TRUCK2_LAST,// 整车尾款应付单
							SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
					});
		} else if(SettlementConstants.AIR_SIGN.equals(signType)) {
			// 空运失效装卸费应付单、后续去掉代收货款应付单的签收日期
			billPayableConDto.setBillTypes(new String[] {
					SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
					,SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD //代收货款应付单
				});
		}else if(SettlementConstants.PARTIAL_LINE_SIGN.equals(signType)){
			// 偏线只需要失效装卸费应付单
			billPayableConDto.setBillTypes(new String[] {
				SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__SERVICE_FEE // 装卸费应付单
			});
		}
		
		//前面验证通过，如果应付单数据为空，直接返回
		List<BillPayableEntity> billPayables = billPayableService.queryBillPayableByCondition(billPayableConDto);
		if (CollectionUtils.isEmpty(billPayables)) {
			return true;
		}
		// 存在服务补救应付单和理赔应付单
		
		for(BillPayableEntity entity:billPayables){
			if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__CLAIM.equals(entity.getBillType())){
				throw new SettlementException("运单存在理赔应付单，不能进行后续操作！");
			}else if(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__COMPENSATION.equals(entity.getBillType())){
				throw new SettlementException("运单存在服务补救应付单，不能进行后续操作！");
			}
			
			// 已核销金额大于0
			if (entity.getVerifyAmount() != null
					&& entity.getVerifyAmount().compareTo(BigDecimal.ZERO) >0) {
				throw new SettlementException("应付单已核销，不能进行反签收操作！");
			}

			// 应付单正在付款中或已付款
			if (SettlementDictionaryConstants.BILL_PAYABLE__PAY_STATUS__YES
					.equals(entity.getPayStatus())) {
				throw new SettlementException("应付单付款中或已付款，不能进行反签收操作");
			}
		}
		logger.info("结束检查运单对应的代收货款是否已付款（冻结之后），检查通过。");
		// 检查通过
		return true ;
	}

	/**
	 * 创建代收货款记录.
	 *
	 * @param item the item
	 * @param currentInfo the current info
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午6:13:37
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#createCOD(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity)
	 */
	@Override
	public void createCOD(CODEntity item, CurrentInfo currentInfo) {
		if (item != null) {
			// 校验关键信息
			if (StringUtils.isEmpty(item.getWaybillNo())) {
				throw new SettlementException("运单单号为空");
			}

			logger.info("开始新加代收货款记录");

			// 添加代收货款记录
			logger.info("开始记录代收货款记录日志!");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(item, currentInfo);
			codLogEntity.setOperateContent("新加代收货款记录!");
			codLogEntity.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__ADD);

			this.codLogService.addCodLog(codLogEntity);

			logger.info("记录代收货款记录日志结束");

			// FOSS生成的所有单据的初始化状态都为"N"
			item.setIsInit(FossConstants.NO);
			// 插入代收货款记录
			this.codEntityDao.addCod(item);

			logger.info("新加代收货款记录结束");

		} else {
			throw new SettlementException("内部错误，参数为空！");
		}

	}

	/**
	 * 根据ID查询代收货款.
	 *
	 * @param entityId the entity id
	 * @return the cOD entity
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-9 上午9:11:44
	 */
	@Override
	public CODEntity queryById(String entityId) {

		if (StringUtils.isNotEmpty(entityId)) {
			return codEntityDao.queryById(entityId);
		} else {
			throw new SettlementException("代收货款ID不能为空");
		}

	}

	/**
	 * 按Id集合批量进行查询.
	 *
	 * @param ids the ids
	 * @return the list
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-15 下午4:23:48
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryByIds(java.util.List)
	 */
	@Override
	public List<CODEntity> queryByIds(List<String> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			return codEntityDao.queryByIds(ids);
		} else {
			throw new SettlementException("代收货款ID不能为空");
		}

	}
	
	/**
	 * 按合并编号进行查询代收货款.
	 *
	 * @param 
	 * @return the list
	 * @date 2014-11-15 下午4:23:48
	 */
	@Override
	public List<CODEntity> queryCODEntitiListByMergeCode(String mergeCode) {
		if (StringUtils.isNotBlank(mergeCode)) {
			CODEntity cod = new CODEntity();
			cod.setMergeCode(mergeCode);
			cod.setActive(FossConstants.ACTIVE);
			return codEntityDao.queryListByMergeCode(cod);
		} else {
			throw new SettlementException("代收货款合并编号不能为空");
		}

	}
	
	/**
	 * 按Id集合批量进行查询可合并代收货款.
	 *
	 * @param ids the ids
	 * @return the list
	 * @date 2014-11-15 11:23:48
	 */
	@Override
	public List<CODMergeDto> queryMergeCodByIds(List<String> ids) {
		if (CollectionUtils.isNotEmpty(ids)) {
			return codEntityDao.queryMergeCodByIds(ids);
		} else {
			throw new SettlementException("代收货款ID不能为空");
		}

	}
	
	/**
	 * 更新代收货款合并编号状态
	 * 
	 * @author foss-guxinhua
	 * @date 2014-11-16 下午3:32:22
	 * @param 
	 * @param currentInfo
	 */
	@Override
	public void updateCodMergeCode(CODMergeDto mergeDto)throws SettlementException{
		if (mergeDto != null) {
			logger.info("开始更新代收货款合并编号状态,运单单号：" + mergeDto.getWaybillNo());
			
			int updateRows = codEntityDao.updateCodMergeCode(mergeDto);
			if (updateRows != 1) {
				logger.error("更新失败，更新行数不唯一！");
				throw new SettlementException("更新失败，更新行数不唯一！");
			}

			logger.info("更新代收货款合并编号状态");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 生成代收货款记录.
	 *
	 * @param item the item
	 * @param currentInfo the current info
	 * @return the cOD log entity
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-25 下午6:17:15
	 */
	private CODLogEntity getCODLogEntity(CODEntity item, CurrentInfo currentInfo) {

		CODLogEntity result = new CODLogEntity();

		// Id
		result.setId(UUIDUtils.getUUID());
		// 操作组织
		result.setOperateOrgCode(currentInfo.getCurrentDeptCode());
		result.setOperateOrgName(currentInfo.getCurrentDeptName());
		// 设置运单号
		result.setWaybillNo(item.getWaybillNo());
		result.setCodId(item.getId());
		// 操作人
		result.setOperatorCode(currentInfo.getEmpCode());
		result.setOperatorName(currentInfo.getEmpName());

		// 获取当前时间
		Date sysDate = new Date();
		result.setOperateTime(sysDate);
		result.setBusinessDate(sysDate);
		result.setActive(FossConstants.ACTIVE);

		return result;
	}

	/**
	 * 更新银行帐号.
	 *
	 * @param item the item
	 * @param currentInfo the current info
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-10-29 下午4:05:24
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateBankAccounts(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity)
	 */
	@Override
	public void updateBankAccounts(CODEntity item, CurrentInfo currentInfo) throws SettlementException{

		if (item != null) {
			logger.info("开始修改代收货款银行帐号,运单单号：" + item.getWaybillNo());

			logger.info("开始记录代收货款记录日志!");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(item, currentInfo);
			
			CODEntity olditem=this.queryById(item.getId());
			String accountno=olditem.getAccountNo();
			String publicPrivateFlag=DictUtil.rendererSubmitToDisplay(olditem.getPublicPrivateFlag(),
							DictionaryConstants.COD__PUBLIC_PRIVATE_FLAG);
			String bankHQCode=olditem.getBankHQCode();
			String bankHQName=olditem.getBankHQName();

			String bankBranchCode=olditem.getBankBranchCode();
			String bankBranchName=olditem.getBankBranchName();
			
			String provinceCode=olditem.getProvinceCode();
			String provinceName=olditem.getProvinceName();
			
			String cityCode=olditem.getCityCode();
			String cityName=olditem.getCityName();
			
			codLogEntity.setOperateContent("修改前银行帐号："+accountno
					+"  收款人："+olditem.getPayeeName()+" 号码："+olditem.getPayeePhone()
					+"  类型："+publicPrivateFlag + "<br/>"
					+"  开户行："+bankHQCode+","+bankHQName
					+"  支行："+bankBranchCode+","+bankBranchName+ "<br/>"
					+"  省份："+provinceCode+","+provinceName
					+"  城市："+cityCode+","+cityName);
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__CHANGE_ACCOUNT);

			this.codLogService.addCodLog(codLogEntity);

			logger.info("记录代收货款记录日志结束");

			// 更新银行帐号信息
			item.setAccountModifyTime(new Date());
			item.setAccountModifyUserCode(currentInfo.getEmpCode());
			item.setAccountModifyUserName(currentInfo.getEmpName());
			
			int updateRows = this.codEntityDao.updateBankAccounts(item);
			if (updateRows != 1) {

				logger.error("更新失败，更新行数不唯一!");

				throw new SettlementException("更新失败，更新行数不唯一!");

			}

			logger.info("修改代收货款银行帐号结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 根据运单号查询代收货款.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 上午9:04:25
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryByWaybillNo(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODWaybillNoQueryDto)
	 */
	@Override
	public List<CODDto> queryByWaybillNo(BillCODWaybillNoQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryByWaybillNo(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查询始发申请代收货款.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 上午9:21:29
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryStartApplyBillCOD(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto)
	 */
	@Override
	public List<CODDto> queryStartApplyBillCOD(
			BillCODStartApplyQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryStartApplyBillCOD(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查询始发申请代收货款大小.
	 *
	 * @param queryDto the query dto
	 * @return the int
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:43:05
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryStartApplyBillCODSize(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto)
	 */
	@Override
	public int queryStartApplyBillCODSize(BillCODStartApplyQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryStartApplyBillCODSize(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查询代收货款汇款失败审核.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:43:31
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryBillCODPayFailed(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto)
	 */
	@Override
	public List<CODDto> queryBillCODPayFailed(BillCODPayFailedQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryBillCODPayFailed(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查询代收货款汇款失败审核大小，合计金额 
	 *
	 * @param queryDto the query dto
	 * @return the int
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:43:59
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryBillCODPayFailedSize(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayFailedQueryDto)
	 */
	@Override
	public CODDto queryBillCODPayFailedSize(BillCODPayFailedQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryBillCODPayFailedSize(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 代收货款汇款查询,合计总条数总金额，冻结总条数总金额
	 *
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:51:20
	 * @param queryDto
	 * @return
	 */
	@Override
	public CODDto queryBillCODPayableSum(BillCODPayableQueryDto queryDto)throws SettlementException{
		if (queryDto != null) {
			return (CODDto) codEntityDao.queryBillCODPayableSum(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 查询可支付的代收货款.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:44:22
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryBillCODPayable(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayableQueryDto)
	 */
	@Override
	public List<CODDto> queryBillCODPayable(BillCODPayableQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryBillCODPayable(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 查询可支付的代收货款及应付单
	 * @author foss-guxinhua
	 * @date 2013-5-8 上午11:04:24
	 * @param queryDto
	 * @return
	 */
	public List<CODPayableDto> queryAllCODAndBillPayable(BillCODPayableQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryAllCODAndBillPayable(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 查询可退款的并可合并付款的代收货款及应付单明细
	 * @author 163576
	 * @date 2014-10-13 11:04:24
	 * @param queryDto
	 * @return
	 */
	public List<CODMergeDto> queryAllMergeCODAndBillPayable(BillCODPayableQueryDto queryDto) throws SettlementException{
		if (queryDto != null) {
			return codEntityDao.queryAllMergeCODAndBillPayable(queryDto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查询代收货款汇款确认.
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param offset the offset
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:44:47
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryPayConfirm(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto)
	 */
	@Override
	public List<CODDto> queryPayConfirm(BillCODPayConfirmDto dto, int start,
			int offset) throws SettlementException{
		if (dto != null) {
			return codEntityDao.queryPayConfirm(dto, start, offset);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 查询代收货款汇款确认行数大小,总金额 
	 *
	 * @param dto the dto
	 * @return the int
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:45:11
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#queryPayConfirmSize(com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto)
	 */
	@Override
	public CODDto queryPayConfirmSize(BillCODPayConfirmDto dto) throws SettlementException{
		if (dto != null) {
			return codEntityDao.queryPayConfirmSize(dto);
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 作废代收货款记录.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:45:34
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateCODCancelStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateCODCancelStatus(CODEntity entity, CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("开始作废代收货款记录,运单单号:" + entity.getWaybillNo());

			logger.info("记录操作日志");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("作废代收货款记录");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__DISABLE);

			this.codLogService.addCodLog(codLogEntity);

			logger.info("记录操作日志结束");

			int updateRows = codEntityDao.updateCODCancelStatus(entity);
			if (updateRows != 1) {
				logger.error("更新失败，更新行数不唯一！");
				throw new SettlementException("更新失败，更新行数不唯一！");
			}

			logger.info("作废代收货款结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新冻结代收货款状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:45:53
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateOrgFreezeStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateOrgFreezeStatus(CODEntity entity, CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("开始更新代收货款冻结状态，运单单号：" + entity.getWaybillNo());

			logger.info("记录代收货款操作日志");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新营业部冻结代收货款状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FREEZE);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("记录代收货款操作日志结束");

			// 更新状态
			int updateRows = codEntityDao.updateOrgFreezeStatus(entity);
			if (updateRows != 1) {
				logger.info("更新行数不唯一!");
				throw new SettlementException("更新行数不唯一!");
			}

			logger.info("更新代收货款冻结状态结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新营业部审核状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:46:13
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateOrgAuditStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateOrgAuditStatus(CODEntity entity, CurrentInfo currentInfo) throws SettlementException{

		if (null != entity) {
			logger.info("开始更新代收货款营业部审核状态，运单单号：" + entity.getWaybillNo());

			logger.info("新加操作日志记录");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新营业部审核代收货款状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__APPROVE);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("新加操作日志记录结束");

			// 更新审核状态
			int updateRows = codEntityDao.updateOrgAuditStatus(entity);
			if (updateRows != 1) {
				logger.error("更新失败,更新行数唯一");
			}

			logger.info("更新营业部审核状态结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新营业部经理审核状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:46:44
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateManagerAuditStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateManagerAuditStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (null != entity) {
			logger.info("更新营业部经理审核状态，运单单号:" + entity.getWaybillNo());

			logger.info("添加代收货款日志.");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);

			// 经理审核同意时，如果该单未签收，提示代收货款为待审核。
			codLogEntity.setOperateContent("更新营业部经理审核代收货款状态"
							+ (StringUtils.equals(entity.getStatus(),
									SettlementDictionaryConstants.COD__STATUS__APPROVING) ? "<br/>(运单未签收，代收货款状态为待审核)"
									: ""));
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__APPROVE);

			this.codLogService.addCodLog(codLogEntity);

			logger.info("添加代收货款日志结束!");

			int updateRows = codEntityDao.updateManagerAuditStatus(entity);
			if (updateRows != 1) {
				logger.info("更新失败，更新行数不唯一！");
				throw new SettlementException("更新失败，更新行数不唯一！");
			}

			logger.info("更新营业部经理审核状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}
	
	/**
	 * 更新营业部经理退回状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 163576-foss-guxinhua
	 * @date 2013-10-19 上午10:46:44
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateManagerAuditStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateManagerBackStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (null != entity) {
			logger.info("更新营业部经理退回状态，运单单号:" + entity.getWaybillNo());

			logger.info("添加代收货款日志.");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);

			codLogEntity.setOperateContent("营业部经理退回更新代收货款待审核状态<br/>退回原因："+ entity.getRefundNotes());
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__BACK);

			this.codLogService.addCodLog(codLogEntity);

			logger.info("添加代收货款日志结束!");

			int updateRows = codEntityDao.updateManagerAuditStatus(entity);
			if (updateRows != 1) {
				logger.info("更新失败，更新行数不唯一！");
				throw new SettlementException("更新失败，更新行数不唯一！");
			}

			logger.info("更新营业部经理退回状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}

	}

	/**
	 * 更新资金部冻结状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:47:02
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateTusyorgFreezeStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateTusyorgFreezeStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {

			logger.info("更新资金部冻结状态,运单单号:" + entity.getWaybillNo());

			logger.info("添加代收货款日志");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新资金部冻结代收货款状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FUND_FREEZE);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("添加代收货款日志结束!");
			int updateRows = codEntityDao.updateTusyorgFreezeStatus(entity);
			if (updateRows != 1) {
				logger.info("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}

			logger.info("更新资金部冻结状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 批量更新代收货款资金部冻结状态
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-3 下午3:32:22
	 * @param entity
	 * @param currentInfo
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateTusyorgFreezeStatusByBatch(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateTusyorgFreezeStatusByBatch(CODDto dto, CurrentInfo currentInfo)throws SettlementException{
		if (dto != null&&CollectionUtils.isNotEmpty(dto.getCodList())) {

			logger.info("批量更新资金部冻结状态,运单单号...:" + dto.getCodList().get(0).getWaybillNo());
			
			if (dto.getCodList().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量更新资金部冻结状态,批量更新数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}

			int updateRows = codEntityDao.updateTusyorgFreezeStatusByBatch(dto);
			
			if (updateRows != dto.getCodList().size()) {
				logger.info("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}

			logger.info("添加代收货款日志");
			for (CODPayableDto entity : dto.getCodList()) {
				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity((CODEntity)entity,
						currentInfo);
				codLogEntity.setOperateContent("更新资金部冻结代收货款状态");
				codLogEntity
						.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FUND_FREEZE);
				this.codLogService.addCodLog(codLogEntity);
			}
			
			logger.info("添加代收货款日志结束!");
			
			logger.info("更新资金部冻结状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 更新资金部取消冻结状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:47:22
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateTusyorgClfreezeStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updateTusyorgClfreezeStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新资金部取消冻结状态,运单单号:" + entity.getWaybillNo());

			logger.info("添加操作记录!");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新资金部反冻结代收货款状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FUND_RELEASE_FREEZE);
			this.codLogService.addCodLog(codLogEntity);
			logger.info("添加操作记录结束!");

			int updateRows = codEntityDao.updateTusyorgClfreezeStatus(entity);
			if (updateRows != 1) {
				logger.error("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}

			logger.info("更新资金部取消冻结状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 批量更新资金部取消冻结状态.
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午4:44:38
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void updateTusyorgClfreezeStatusByBatch(CODDto dto,CurrentInfo currentInfo) throws SettlementException{

		if (dto != null&&CollectionUtils.isNotEmpty(dto.getCodList())) {
			logger.info("批量更新资金部取消冻结状态,运单单号...:" + dto.getCodList().get(0).getWaybillNo());
			
			if (dto.getCodList().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量更新资金部冻结状态,批量更新数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}

			int updateRows = codEntityDao.updateTusyorgClfreezeStatusByBatch(dto);
			if (updateRows != dto.getCodList().size()) {
				logger.error("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}
			
			logger.info("添加操作记录!");
			for (CODPayableDto entity : dto.getCodList()) {
				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity((CODEntity)entity,
						currentInfo);
				codLogEntity.setOperateContent("更新资金部反冻结代收货款状态");
				codLogEntity
						.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FUND_RELEASE_FREEZE);
				this.codLogService.addCodLog(codLogEntity);
			}
			logger.info("添加操作记录结束!");

			logger.info("批量更新资金部取消冻结状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新付款失败审核状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:47:48
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePaymentFailedAuditStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePaymentFailedAuditStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新代收货款付款失败状态，运单单号:" + entity.getWaybillNo());

			logger.info("添加更新日志");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款付款失败审核状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FAIL_APPLY_PASSED);

			this.codLogService.addCodLog(codLogEntity);
			logger.info("添加更新日志结束");

			int updateRows = codEntityDao
					.updatePaymentFailedAuditStatus(entity);
			if (updateRows != 1) {

				logger.info("更新代收货款付款状态失败,更新行数不唯一!");

				throw new SettlementException("更新代收货款付款状态失败,更新行数不唯一!");
			}

			logger.info("更新代收货款付款失败状态结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 更新汇款失败审核退回状态.
	 * @author foss-guxinhua
	 * @date 2013-5-20 上午10:58:41
	 * @param entity
	 * @param currentInfo
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePaymentFailedReturnedStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity, com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePaymentFailedReturnedStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新汇款失败审核退回状态，运单单号:" + entity.getWaybillNo());

			logger.info("添加更新日志");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款付款失败审核退回状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__FAIL_APPLY_RETURNED);

			this.codLogService.addCodLog(codLogEntity);
			logger.info("添加更新日志结束");

			int updateRows = codEntityDao
					.updatePaymentFailedAuditStatus(entity);
			if (updateRows != 1) {

				logger.info("更新汇款失败审核退回状态失败,更新行数不唯一!");

				throw new SettlementException("更新汇款失败审核退回状态失败,更新行数不唯一!");
			}

			logger.info("更新汇款失败审核退回状态结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 批量更新线下汇款状态.
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午4:41:56
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void updatePayCODOfflineStatusByBatch(CODDto dto,CurrentInfo currentInfo) throws SettlementException{

		if (dto != null && CollectionUtils.isNotEmpty(dto.getCodList())) {
			logger.info("批量更新代收货款线下付款状态,运单单号...:" + dto.getCodList().get(0).getWaybillNo());

			if (dto.getCodList().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量更新资金部冻结状态,批量更新数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}
			
			int updateRows = codEntityDao.updatePayCODOfflineStatusByBatch(dto);
			if (updateRows != dto.getCodList().size()) {
				logger.error("更新失败，更新行数不唯一");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}
			
			logger.info("新加更新代收货款状态日志!");
				for (CODPayableDto entity : dto.getCodList()) {
				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity((CODEntity)entity,
						currentInfo);
				codLogEntity.setOperateContent("更新代收货款线下汇款状态");
				codLogEntity
						.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__EXPORT_TRANSFER);
				this.codLogService.addCodLog(codLogEntity);
				}
			logger.info("新加更新代收货款状态日志结束!");
			
			logger.info("批量更新代收货款线下付款状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新线下汇款状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:48:07
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePayCODOfflineStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePayCODOfflineStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新代收货款线下付款状态,运单单号:" + entity.getWaybillNo());

			logger.info("新加更新代收货款状态日志!");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款线下汇款状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__EXPORT_TRANSFER);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("新加更新代收货款状态日志结束!");
			int updateRows = codEntityDao.updatePayCODOfflineStatus(entity);
			if (updateRows != 1) {

				logger.error("更新失败，更新行数不唯一");

				throw new SettlementException("更新失败，更新行数不唯一!");
			}

			logger.info("更新代收货款线下付款状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 批量更新线上汇款状态.
	 * @author foss-guxinhua
	 * @date 2013-5-8 下午4:37:59
	 * @param dto
	 * @param currentInfo
	 */
	@Override
	public void updatePayCODOnlineStatusByBatch(CODDto dto,CurrentInfo currentInfo) throws SettlementException{

		if (dto != null && CollectionUtils.isNotEmpty(dto.getCodList())) {
			logger.info("批量更新代收货款线上汇款状态,运单单号...:" + dto.getCodList().get(0).getWaybillNo());
			
			if (dto.getCodList().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量更新代收货款线上汇款状态,批量更新数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}

			int updateRows = codEntityDao.updatePayCODOnlineStatusByBatch(dto);
			if (updateRows != dto.getCodList().size()) {
				logger.error("更新线上代收货款状态失败,更新行数不唯一!");
				throw new SettlementException("更新线上代收货款状态失败,更新行数不唯一!");
			}

			logger.info("添加代收货款操作日志");
			for (CODPayableDto entity : dto.getCodList()) {
				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity((CODEntity)entity,
						currentInfo);
				codLogEntity.setOperateContent("更新代收货款线上汇款状态");
				codLogEntity
						.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__RETURN_APPLICATION);

				this.codLogService.addCodLog(codLogEntity);
			}
			logger.info("添加操作日志结束");
			
			logger.info("批量更新代收货款线上汇款状态结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 更新线上汇款状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:48:26
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePayCODOnlineStatus(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePayCODOnlineStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新代收货款线上汇款状态,运单单号:" + entity.getWaybillNo());

			logger.info("添加代收货款操作日志");
			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款线上汇款状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__RETURN_APPLICATION);

			this.codLogService.addCodLog(codLogEntity);

			logger.info("添加操作日志结束");

			int updateRows = codEntityDao.updatePayCODOnlineStatus(entity);
			if (updateRows != 1) {
				logger.error("更新线上代收货款状态失败,更新行数不唯一!");
				throw new SettlementException("更新线上代收货款状态失败,更新行数不唯一!");
			}

			logger.info("更新代收货款线上汇款状态结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新汇款成功状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:48:42
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePayCODSuccess(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePayCODSuccess(CODEntity entity, CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新付款成功状态,运单单号:" + entity.getWaybillNo());

			logger.info("开始记录操作日志");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款汇款成功状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__RETURN_SUCCESS);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("操作日志记录结束");

			int updateRows = codEntityDao.updatePayCODSuccess(entity);
			if (updateRows != 1) {
				logger.info("更新付款成功状态失败，更新行数不唯一!");
				throw new SettlementException("更新付款成功状态失败，更新行数不唯一!");
			}
			logger.info("更新付款成功状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}
	
	/**
	 * 批量更新汇款成功状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:48:42
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePayCODSuccess(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePayCODSuccessByBatch(CODDto dto, CurrentInfo currentInfo) throws SettlementException{
		
		if (dto != null && CollectionUtils.isNotEmpty(dto.getCodEntityList())) {
			logger.info("批量更新汇款成功状态,运单单号...:" + dto.getCodEntityList().get(0).getWaybillNo());
			
			if (dto.getCodEntityList().size() > SettlementConstants.MAX_LIST_SIZE) {
				throw new SettlementException("批量更新汇款成功状态,批量更新数量大于"
						+ SettlementConstants.MAX_LIST_SIZE + "，不能进行操作");
			}

			int updateRows = codEntityDao.updatePayCODSuccessByBatch(dto);
			if (updateRows != dto.getCodEntityList().size()) {
				logger.error("更新失败，更新行数不唯一!");
				throw new SettlementException("更新失败，更新行数不唯一!");
			}
			
			logger.info("添加操作记录!");
			for (CODEntity entity : dto.getCodEntityList()) {
				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity(entity,currentInfo);
				codLogEntity.setOperateContent("更新代收货款汇款成功状态");
				codLogEntity
						.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__RETURN_SUCCESS);
				this.codLogService.addCodLog(codLogEntity);
			}
			logger.info("添加操作记录结束!");

			logger.info("批量更新汇款成功状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
		
	}

	/**
	 * 更新汇款失败状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:49:03
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePayCODFailure(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePayCODFailure(CODEntity entity, CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新付款失败状态,运单单号:" + entity.getWaybillNo());

			logger.info("添加操作日志！");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款汇款失败状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__RETURN_FAILURE);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("添加操作日志结束！");

			int updateRows = codEntityDao.updatePayCODFailure(entity);
			if (updateRows != 1) {
				logger.error("更新付款失败状态不成功,更新行数不唯一!");
				throw new SettlementException("更新付款失败状态不成功,更新行数不唯一!");
			}

			logger.info("更新付款失败状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新汇款成功转失败状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-19 下午6:49:20
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updatePayCODAntiRemittance(com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity,
	 * com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo)
	 */
	@Override
	public void updatePayCODAntiRemittance(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新代收货款付款成功能够转失败,运单单号:" + entity.getWaybillNo());

			logger.info("添加操作日志");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			codLogEntity.setOperateContent("更新代收货款汇款成功转失败状态");
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__NEGATIVE_REFUND_SUCCESS);
			this.codLogService.addCodLog(codLogEntity);

			logger.info("添加操作日志结束");

			// 执行更新
			int updateRows = codEntityDao.updatePayCODAntiRemittance(entity);
			if (updateRows != 1) {
				logger.error("更新付款成功转始发不成功，更新行数不唯一!");
				throw new SettlementException("更新付款成功转始发不成功，更新行数不唯一!");
			}

			logger.info("更新代收货款付款成功能够转失败结束!");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 更新空运代收货款审核记录.
	 *
	 * @param codEntity the cod entity
	 * @param newAirStatus the new air status
	 * @param oldAirStatus the old air status
	 * @param currentInfo the current info
	 * @author 000123-foss-huangxiaobo
	 * @date 2012-11-8 上午10:38:30
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateAirPaidCodStatus(java.lang.String,
	 * java.lang.String, java.lang.String,
	 * com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public void updateAirPaidCodStatus(CODEntity codEntity,
			String newAirStatus, String oldAirStatus, CurrentInfo currentInfo) throws SettlementException{
		if (codEntity != null && StringUtils.isNotEmpty(newAirStatus)
				&& StringUtils.isNotEmpty(oldAirStatus)) {

			logger.info("开始空运代收货款状态审核,运单单号：" + codEntity.getWaybillNo());

			// 判断是否新单据状态是否与旧单据状态是否一致
			if (!newAirStatus.equals(oldAirStatus)) {

				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity(codEntity,
						currentInfo);
				
				String mark = "";
				// 反审核标志
				if(StringUtils.equals(SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT, newAirStatus)){
					mark = "反";
				}
				codLogEntity.setOperateContent(mark + "审核空运代收货款");
				codLogEntity.setOperateActiontype(mark + "审核空运代收货款");
				
				this.codLogService.addCodLog(codLogEntity);

				// 执行更新
				int updateRows = this.codEntityDao.updateAirPaidCodStatus(
						codEntity, newAirStatus, oldAirStatus);
				if (updateRows != 1) {
					logger.error("更新空运代收货款状态失败，更新行数不唯一!");
					throw new SettlementException("更新空运代收货款状态失败，更新行数不唯一!");
				}

				logger.info("开始空运代收货款状态"+ mark +"审核结束");

			} else {

				throw new SettlementException("内部错误，新旧空运审核状态是一样");
			}
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}

	/**
	 * 更新快递代理代收货款审核记录.
	 * ISSUE-3389 小件业务
	 * @param codEntity the cod entity
	 * @param newAirStatus the new air status
	 * @param oldAirStatus the old air status
	 * @param currentInfo the current info
	 * @author guxinhua
	 * @date 2012-11-8 上午10:38:30
	 * @see com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService#updateAirPaidCodStatus(java.lang.String,
	 * java.lang.String, java.lang.String,
	 * com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity)
	 */
	@Override
	public void updateLandPaidCodStatus(CODEntity codEntity,
			String newLandStatus, String oldLandStatus, CurrentInfo currentInfo) throws SettlementException{
		if (codEntity != null && StringUtils.isNotEmpty(newLandStatus)
				&& StringUtils.isNotEmpty(oldLandStatus)) {

			logger.info("开始快递代理代收货款状态审核,运单单号：" + codEntity.getWaybillNo());

			// 判断是否新单据状态是否与旧单据状态是否一致
			if (!newLandStatus.equals(oldLandStatus)) {

				// 添加操作日志
				CODLogEntity codLogEntity = this.getCODLogEntity(codEntity,
						currentInfo);
				
				String mark = "";
				// 反审核标志
				if(StringUtils.equals(SettlementDictionaryConstants.COD__AIR_STATUS__NOT_AUDIT, newLandStatus)){
					mark = "反";
				}
				codLogEntity.setOperateContent(mark + "审核快递代理代收货款");
				codLogEntity.setOperateActiontype(mark + "审核快递代理代收货款");
				
				this.codLogService.addCodLog(codLogEntity);

				// 执行更新
				int updateRows = this.codEntityDao.updateLandPaidCodStatus(
						codEntity, newLandStatus, oldLandStatus);
				if (updateRows != 1) {
					logger.error("更新快递代理代收货款状态失败，更新行数不唯一!");
					throw new SettlementException("更新快递代理代收货款状态失败，更新行数不唯一!");
				}

				logger.info("开始快递代理代收货款状态"+ mark +"审核结束");

			} else {

				throw new SettlementException("内部错误，新旧快递代理审核状态是一样");
			}
		} else {
			throw new SettlementException("内部错误，参数为空");
		}
	}	
	
	/**
	 * 获取代收货款对应的应付单.
	 *
	 * @param entity the entity
	 * @return the bill payable entity
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-20 下午5:13:25
	 */
	public BillPayableEntity getBillPayableEntity(CODEntity entity)
			throws SettlementException {

		if (entity != null && StringUtils.isNotEmpty(entity.getWaybillNo())) {
			// 查询出对应的代收货款应付单
			String waybillNo = entity.getWaybillNo();
			BillPayableConditionDto dto = new BillPayableConditionDto();
			dto.setWaybillNo(waybillNo);
			dto.setBillTypes(new String[] { SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD });
			dto.setActive(FossConstants.ACTIVE);

			// 查询代收货款应付单
			List<BillPayableEntity> bills = billPayableService
					.queryBillPayableByCondition(dto);

			// 如果找不到对应的代收货款应付单，则抛出异常
			if (bills == null) {
				throw new SettlementException("根据运单号找不到对应的代收货款应付单");
			}

			// 如果代收货款应付单不唯一，则抛出异常
			if (bills.size() != 1) {
				throw new SettlementException("根据运单号找到的代收货款应付单不唯一");
			}

			BillPayableEntity bill = bills.get(0);

			return bill;
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 按运单号、代收货款状态、应付部门查询代收货款【运单号不能为空 代收货款状态、应付部门可为空】.
	 *
	 * @param waybillNos the waybill nos
	 * @param statuses the statuses
	 * @param currentInfo the current info
	 * @return the list
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:33:19
	 */
	public List<CODDto> queryBillCODByWaybillNo(List<String> waybillNos,
			List<String> statuses, CurrentInfo currentInfo) throws SettlementException{

		// 如果运单号为空，则抛出异常
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("运单号集合为空，不能按运单号查询");
		}

		// 构造查询DTO
		BillCODWaybillNoQueryDto queryDto = new BillCODWaybillNoQueryDto();

		// 设置查询DTO的值
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		// 设置用户数据查询权限
		queryDto.setUserCode(currentInfo==null?null:currentInfo.getEmpCode());//避免没有权限的设置
		queryDto.setStatuses(statuses);
		queryDto.setWaybillNos(waybillNos);
		
		return codEntityDao.queryByWaybillNo(queryDto);
	}

	/**
	 * 根据批次号查询代收货款.
	 *
	 * @param batchNumber the batch number
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-23 上午9:50:01
	 */
	public List<CODEntity> queryByBatchNumber(String batchNumber) {

		if (StringUtils.isEmpty(batchNumber)) {
			throw new SettlementException("批次号为空");
		}

		return codEntityDao.queryByBatchNumber(batchNumber);
	}

	/**
	 * 更新银企整批退回代收货款状态.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-26 上午9:53:30
	 */
	public void updateCODBankBatchBackStatus(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException{

		if (entity != null) {
			logger.info("更新银企整批退回代收货款状态");

			// 添加操作日志
			CODLogEntity codLogEntity = this.getCODLogEntity(entity,
					currentInfo);
			// 用PayeeRelationship表示 退回异常信息
			codLogEntity.setOperateContent("更新银企整批退回代收货款状态，"+entity.getPayeeRelationship());
			codLogEntity
					.setOperateActiontype(SettlementDictionaryConstants.COD_LOG__OPERATE_TYPE__BATCH_BACK);
			this.codLogService.addCodLog(codLogEntity);

			// 执行更新
			int updateRows = codEntityDao.updateCODBankBatchBackStatus(entity);

			if (updateRows != 1) {
				logger.info("更新银企整批退回代收货款状态失败，更新行数不唯一！");
				throw new SettlementException("更新银企整批退回代收货款状态失败，更新行数不唯一！");
			}

			logger.info("更新银企整批退回代收货款状态结束");
		} else {
			throw new SettlementException("内部错误，参数为空!");
		}
	}

	/**
	 * 按运单号集合查询（官网代收货款查询）.
	 *
	 * @param waybillNos the waybill nos
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:04:11
	 */
	public List<CODDto> queryCODByWaybillNums(List<String> waybillNos,
			int offset, int limit) {

		// 构建查询DTO
		BillCODWaybillNoQueryDto dto = buildBillCODWaybillNoQueryDto(waybillNos);

		// 查询并返回结果
		return codEntityDao.queryCODByWaybillNums(dto, offset, limit);

	}
	
	/**
	 * 按运单号集合查询（CC代收货款查询）.
	 *
	 * @param waybillNos the waybill nos
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:04:11
	 */
	public List<CODDto> queryCODByWaybillNumsForCC(List<String> waybillNos,
			int offset, int limit) {

		// 构建查询DTO
		BillCODWaybillNoQueryDto dto = buildBillCODWaybillNoQueryDto(waybillNos);

		// 查询并返回结果
		return codEntityDao.queryCODByWaybillNumsForCC(dto, offset, limit);

	}

	/**
	 * 构建按运单号集合查询条件（官网代收货款查询）.
	 *
	 * @param waybillNos the waybill nos
	 * @return the bill cod waybill no query dto
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:06:44
	 */
	private BillCODWaybillNoQueryDto buildBillCODWaybillNoQueryDto(
			List<String> waybillNos) {

		// 如果运单号为空，则抛出异常
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("运单号集合为空.");
		}

		// 运单号、是否有效、应付单是否有效、应付单单据子类型
		BillCODWaybillNoQueryDto dto = new BillCODWaybillNoQueryDto();
		dto.setWaybillNos(waybillNos);
		dto.setActive(FossConstants.ACTIVE);
		dto.setPayableActive(FossConstants.ACTIVE);
		dto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		return dto;

	}

	/**
	 * 按运单号、代收货款状态、应付部门查询线下导出代收货款【运单号不能为空 代收货款状态】.
	 *
	 * @param waybillNos the waybill nos
	 * @param statuses the statuses
	 * @return the list
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:33:19
	 */
	public List<CODDto> queryBillOfflineCODByWaybillNo(List<String> waybillNos,
			List<String> statuses) throws SettlementException{

		// 如果运单号为空，则抛出异常
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("运单号集合为空，不能按运单号查询");
		}

		// 构造查询DTO
		BillCODWaybillNoQueryDto queryDto = new BillCODWaybillNoQueryDto();

		// 设置查询DTO的值
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		queryDto.setStatuses(statuses);
		queryDto.setWaybillNos(waybillNos);
		queryDto.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__OFFLINE);
		//核销类型
		queryDto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);
		
		queryDto.setIsFilterUnverifyAmount("true"); // 用于过滤按应付单未核销金额>0条件

		return codEntityDao.queryByWaybillNo(queryDto);
	}

	/**
	 * 按运单号集合合计总条数总金额，冻结总条数总金额查询
	 * 
	 * @author foss-guxinhua
	 * @date 2013-5-6 下午3:38:51
	 * @return
	 */
	@Override
	public CODDto queryBillCODPayableByWaybillNosSum(List<String> waybillNos)throws SettlementException{
		// 如果运单号为空，则抛出异常
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("运单号集合为空，不能按运单号查询");
		}

		// 构造查询DTO
		BillCODWaybillNoQueryDto queryDto = new BillCODWaybillNoQueryDto();

		// 设置查询DTO的值
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		// 代收货款状态:未退款、资金部冻结
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		statuses.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
		queryDto.setStatuses(statuses);
		queryDto.setWaybillNos(waybillNos);
		// 应付单有效
		queryDto.setEffective(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
				
		queryDto.setStatue(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE); // 用于统计冻结状态的总金额总条数
		return codEntityDao.queryByWaybillNoListSum( queryDto);
	}
	
	/**
	 * 按运单号查询可支付的代收货款.
	 *
	 * @param waybillNos the waybill nos
	 * @return the list
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-21 上午11:29:41
	 */
	public List<CODDto> queryBillCODPayableByWaybillNos(List<String> waybillNos,String sortProperty,String sortDirection)
			throws SettlementException {

		// 如果运单号为空，则抛出异常
		if (CollectionUtils.isEmpty(waybillNos)) {
			throw new SettlementException("运单号集合为空，不能按运单号查询");
		}

		// 构造查询DTO
		BillCODWaybillNoQueryDto queryDto = new BillCODWaybillNoQueryDto();

		// 设置查询DTO的值
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		// 代收货款状态:未退款、资金部冻结
		List<String> statuses = new ArrayList<String>();
		statuses.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		statuses.add(SettlementDictionaryConstants.COD__STATUS__FUND_FREEZE);
		queryDto.setStatuses(statuses);
		queryDto.setWaybillNos(waybillNos);
		// 应付单有效
		queryDto.setEffective(SettlementDictionaryConstants.BILL_PAYABLE__EFFECTIVE_STATUS__YES);
		
		// 设置排序
		queryDto.setSortProperty(sortProperty);
		queryDto.setSortDirection(sortDirection);

		/**
		 * @author 218392 张永雪 专为代收货款支付界面按单号查询：因为新的需求增加了一个代收货款资金部审核界面环节，
		 * 那么在按单号查询中，只能查询复合组审核通过的数据，但是在后台还不能使用 状态=“复合组审核通过”，因为有些代收货款是
		 * 不需要审核的 用not exists
		 */
		return codEntityDao.queryCodListByWaybillNo(queryDto);

	}

	/**
	 * 按运单号集合查询大小（官网代收货款查询）.
	 *
	 * @param waybillNos the waybill nos
	 * @return the int
	 * @author 046644-foss-zengbinwen
	 * @date 2012-12-5 下午2:04:23
	 */
	public int queryCODByWaybillNumsSize(List<String> waybillNos) {

		// 构建查询DTO
		BillCODWaybillNoQueryDto dto = buildBillCODWaybillNoQueryDto(waybillNos);

		// 查询并返回结果
		return codEntityDao.queryCODByWaybillNumsSize(dto);

	}
	
	/**
	 * 判断集合中codEntity属于三日退（审核退）类型:R3,RA，还是即日退:R1 。返回 R3,RA 或 R1.
	 *
	 * @param codEntityList the cod entity list
	 * @return the string
	 * @author ibm-guxinhua
	 * @date 2012-11-30 上午10:05:12
	 */
	public String codEntityListInstanceofType(List<?> codEntityList) throws SettlementException{

		int r1Num = 0; // 即日退数量
		int r3raNum = 0; // 三日退（审核退）数量
		for (Object codEntity : codEntityList) {
			// 即日退
			if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
					.equals(((CODEntity)codEntity).getCodType())) {
				r1Num++;
			} else {
				r3raNum++;
			}
		}

		int codEntityListNum = codEntityList.size();
		if (r1Num == codEntityListNum && r3raNum == 0) { // 全部是即日退类型
			return SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY;
		} else if (r3raNum == codEntityListNum && r1Num == 0) { // 全部是三日退（审核退）类型
			return SettlementConstants.COD__COD_TYPE__RETURN_3_A_DAY_CODE;
		} else {
			throw new SettlementException("代收货款集合必须全部是即日退类型或三日退（审核退）类型");
		}

	}
	
	/**
	 * 根据代收货款批次号查询应付单集合
	 * @author foss-guxinhua
	 * @date 2013-5-29 上午9:53:26
	 * @param batchNumber
	 * @return
	 */
	public List<BillPayableEntity> queryBillPayableByCodBatchNumber(String batchNumber)throws SettlementException {
		
		if (StringUtils.isNotBlank(batchNumber)) {
			
			CODPayableDto dto = new CODPayableDto();
			
			dto.setBatchNumber(batchNumber);
			
			dto.setBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD );
			
			// 有效单据
			dto.setActive(FossConstants.ACTIVE);

			// 非红单
			dto.setIsRedBack(SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);

			return codEntityDao.queryBillPayableByCodBatchNumber(dto);
		} else {
			throw new SettlementException("根据代收货款批次号查询应付单集合，输入的批次不能为空！");
		}
				
	}
	
	/**
	 * Sets the cod entity dao.
	 *
	 * @param codEntityDao the new cod entity dao
	 */
	public void setCodEntityDao(ICODEntityDao codEntityDao) {
		this.codEntityDao = codEntityDao;
	}

	/**
	 * Sets the cod log service.
	 *
	 * @param codLogService the new cod log service
	 */
	public void setCodLogService(ICodLogService codLogService) {
		this.codLogService = codLogService;
	}

	/**
	 * Sets the bill payable service.
	 *
	 * @param billPayableService the new bill payable service
	 */
	public void setBillPayableService(IBillPayableService billPayableService) {
		this.billPayableService = billPayableService;
	}


	/**
	 * Sets the bill bad account service.
	 *
	 * @param billBadAccountService the new bill bad account service
	 */
	/*public void setBillBadAccountService(
			IBillBadAccountService billBadAccountService) {
		this.billBadAccountService = billBadAccountService;
	}*/

}
