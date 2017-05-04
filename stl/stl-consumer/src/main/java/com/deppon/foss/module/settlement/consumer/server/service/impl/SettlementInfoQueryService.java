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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/SettlementInfoQueryService.java
 * 
 * FILE NAME        	: SettlementInfoQueryService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
 * 修订记录 
 * 日期 	修订内容 	修订人员 	版本号 
 * 2012-05-11 	创建版 	毛建强	V0.1
 * 2012-07-16 	修订版 	毛建强	V0.5
 * 2012-07-25 	版本升级	毛建强	V0.9
 * 2012-10-12
 * 增加冻结和修改账号的颜色变化描述	毛建强	V1.0
 * 
 * 1.	SUC-312-锁定代收货款
 * 1.1	相关业务用例
 * BUC_FOSS_4.7.50.30_050  修改客户银行帐号
 * 1.2	用例描述
 * 营业部收银员对代收货款记录进行营业部冻结，会将代收货款状态修改为“营业部冻结”状态,收银员通过该功能修改该条代收货款信息中的银行账号信息。 
 * 1.3	用例条件
 * 条件类型	描述	引用系统用例
 * 前置条件	1、营业部冻结：
 * 1）代收货款应付单还没有进行退款或没被资金部冻结或没有在退款中
 * 2）存在对应的代收货款单据
 * 2、修改账号
 *   1）该代收货款已经被营业部冻结
 *   2）该代收货款存在对应的要修改的银行账号	《确认代收货款退款申请》系统用例中查询
 * 后置条件	1.	修改代收货款状态为“营业部冻结”
 * 2.	修改代收货款银行账号信息为所选择新银行账号信息	
 * 1.4	操作用户角色
 * 操作用户	描述
 * 收银员	收银员进行营业部冻结、修改账号和审核操作
 * 1.5	界面要求
 * 1.5.1	表现方式
 * Web
 * 1.5.2	界面原型
 * 查询冻结界面：
 *  
 * 修改账号界面：
 *  
 * 1.5.3	界面描述
 * 页面初始化组件描述：
 * 查询页面：
 * 1、	退款状态默认显示“待审核或退款失败”
 * 修改账号界面：
 * 1、	界面输入框除了银行账号外，都为不可编辑状态
 * 2、	默认将所选代收货款记录的银行账号信息初始化到修改银行账号界面对应组件中
 * 页面输入组件描述
 * 查询页面：
 * 1、	代收货款状态：代收货款操作状态
 * 	待审核或退款失败
 * 	未退款
 * 2、	单号查询：输入多个单号，以逗号隔开，最多不能超过10个。查询对应单号代收货款信息。单号默认为8-10位数字
 * 修改账号界面：
 * 1、银行账号控件默认为公共查询组件，且不可手动输入，只能选择。不能为空。选择银行账号，默认会更新界面上对应控件的信息为新账号对应的值。
 * 页面表格组件描述：
 * 1、	默认为分页显示，每页显示数据可选择20,30,50,100
 * 页面提供的钮控件描述：
 * 1、查询：查询代收货款列表功能
 * 2、营业部冻结：冻结所选的代收货款信息，使资金部不能操作冻结的代收货款
 * 3、修改账号：弹出客户账号信息，可以进行银行账号修改
 * 4、审核：进行审核操作。参见系统用例《DP-FOSS-结算系统用例-确认代收货款退款申请》
 * 
 * 1.6	操作步骤
 * 1.6.1	营业部冻结
 * 序号	基本步骤	相关数据	补充步骤
 * 1	查询数据	代收货款信息	参见《确认代收货款退款申请》系统用例中查询 
 * 2	勾选查询出来的代收货款数据	代收货款信息	
 * 3	点击营业部冻结按钮	代收货款信息	1、	判断是否选中行，如果没选中，则弹出提示
 * 扩展事件参见3a
 * 2、	判断如果所选记录代收货款状态为“营业部冻结”,则弹出提示
 * 扩展事件参见3b
 * 3、	更新代收货款的操作状态为“营业部冻结”，同时更新冻结单据颜色为橘黄色
 * 业务规则请参见SR1
 * 4、	添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 1.6.2	修改账号
 * 序号	基本步骤	相关数据	补充步骤
 * 4	勾选查询出来的代收货款数据（单选）	代收货款信息	
 * 5	点击修改账号按钮	代收货款信息	1、	判断所选记录数不等于1，则弹出提示
 * 参见扩展事件5a
 * 2、	判断所选中记录代收货款状态是否为“营业部冻结”，如果不是则弹出提示
 * 参见扩展事件5b
 * 3、	弹出修改账号界面
 * 4、	将所选代收货款信息填充到界面对应控件中
 * 6	点击客户银行账号公共组件	银行账号信息	1、调用综合管理系统接口，查询该客户银行账号信息，显示在基础控件中
 * 2、选择对应账号信息，判断如果当前代收货款为即日退且所选账号的银行不属于即日退开单银行限制基础资料中的银行，则弹出提示。反之将该账号信息重新填充到界面对应的控件
 * 3、 判断如果当前代收货款为即日退且所选银行账号的对公对私标志如果为“对公”，则弹出提示
 * 参见扩展事件6a
 * 7	点击提交按钮		1、	关闭修改账号按钮界面
 * 2、	修改对应代收货款对应行的银行账号信息、银行、收款人、客户收款人关系等信息
 * 3、	同时更新界面上该条代收货款信息，且修改该条记录颜色为红色
 * 扩展事件参见7a
 * 业务规则参见SR2
 * 4、	添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 序号	扩展事件	相关数据	备注
 * 3a	如果没选中行，点击营业部冻结按钮，则弹出提示	提示信息	提示信息：“请至少选中1条记录进行冻结操作！”
 * 3b	所选记录代收货款状态为“营业部冻结”,则弹出提示	提示信息	“该记录已经被营业部冻结，不能重复冻结”
 * 5a	若果所选记录数不等于1，则弹出异常提示	提示信息	“请选中一条数据进行修改账号操作，只能选择一条哦！”
 * 5b	如果所选记录代收货款状态不是“营业部冻结”，则弹出异常提示	提示信息	“必须先进行营业部冻结，才能修改账号！”
 * 6a	如果校验账号信息错误，则直接返回，弹出错误信息	提示信息	1、	如果代收货款是即日退，并且所选银行不在即日退限制银行范围内，则弹出提示 “所选账号的银行暂不支
 * 持即日退。”
 * 2、	如果代收货款是即日退且所选账号为“对公”，则弹出异常提示：“即日退不能选择对公账号！”
 * 
 * 7a	点击“取消”按钮		关闭修改账号界面，不修改
 * 
 * 1.7	业务规则
 * 序号	描述
 * SR1	更新选中数据的代收货款状态为“营业部冻结”状态。更新条件：
 * 1、	代收货款状态必须是“未退款”或“待审核”或“退款失败”
 * SR2	更新对应代收货款中“收款人”、“银行账号”、“开户行”、“客户收款人关系”、“省份”、“城市”、“收款人手机号”，“开户行支行”，“对公对私标志”信息
 * 更新条件：
 * 代收货款状态为“营业部冻结”
 * 转化规则：
 * 收款人 ：弹出银行账号界面的开户姓名
 * 银行账号：弹出银行账号界面的银行账号
 * 开户行：弹出银行账号界面的开户银行编码
 * 客户收款人关系：弹出银行账号界面的客户收款人关系
 * 开户行支行：弹出银行账号界面的银行地址
 * 省份：弹出银行账号界面的开户行省份
 * 城市：弹出银行账号界面的开户行城市
 * 收款人手机号：弹出银行账号界面的手机号码
 * 对公对私标志：弹出界面银行账号对公私标志
 * 银行行号：银行的行号
 * 
 * 1.8	数据元素
 * 1.8.1	页面查询条件（输入）
 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
 * 备注
 * 
 * 代收货款状态	代收货款状态	枚举	代收货款退款状态	10	是	无
 *   单号	运单号	文本	请输入正确单号：8-10位数字	10	否	无
 * 
 * 1.8.2	代收货款信息（输出）
 * 字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
 * 运单号	运单号	无	无	10	无	来源于运单
 * 开单金额	代收货款金额	无	无	10	无	运单开单确认的代收货款
 * 冲应收金额	冲销的金额	无	无	10	无	运单应收冲应付的金额
 * 应退金额	应付代收货款金额。取应付单未核销金额	无	无	10	无	开单金额=冲应收金额+应退金额
 * 退款类型	代收货款退款类型	无	无	20	无	无
 * 退款状态	代收货款退款状态	无	无	20	无	无
 * 客户名称	发货客户名称	无	无	20	无	来源于运单
 * 收款人	代收货款收款人姓名	无	无	20	无	来源于crm
 * 账号	收款账号	无	无	40	无	来源于crm
 * 开户行	收款账号所属银行	无	无	20	无	来源于crm
 * 客户收款人关系	发货客户与收款人关系	无	无	50	无	来源于crm
 * 对公对私标志	账号类型	无	无	20	无	隐藏
 * 省份	账号所属银行所在省份	无	无	20	无	隐藏
 * 城市	账号所属银行所在城市	无	无	20	无	隐藏
 * 银行地址（支行）	账号所属银行具体地址	无	无	50	无	隐藏
 * 收款人手机号	账号开户人的手机号	无	无	11	无	隐藏
 * 银行行号	银行行号	无	无	50	无	无
 * 开单日期	运单开单日期	无	无	参照统一标准	无	无
 * 部门	代收货款所属部门	无	无	一标准	无	等于当前登录部门
 * 备注	备注信息	无	无	255	无	显示代收货款备注信息：如资金部汇款失败，会填写失败原因到备注中
 * 1.8.3	代收货款更新的字段（输出）
 * 字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
 * 代收货款状态	代收货款的状态	枚举	无	50	是	无
 * 收款人	代收货款银行账号对应的收款人	文本	无	50	是	无
 * 账号	代收货款银行账号	文本	无	50	是	无
 * 开户行	银行账号所属开户行	文本	无	20	是	无
 * 银行地址（支行）	开户银行详细地址	文本	无	100	是	无
 * 客户收款人关系	客户收款人关系	文本	无	100	是	无
 * 省份	开户银行所属省份	文本	无	20	是	无
 * 城市	开户银行所属城市	文本	无	20	是	无
 * 收款人手机号	收款人手机号	文本	无	11	是	无
 * 对公对私标志	银行账号属性表明是对公还是对私	文本	无	20	是	无
 * 
 * 1.9	非功能性需求
 * 使用量	每天收银员审核约为700票
 * 2012年全网估计用户数	收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
 * 响应要求（如果与全系统要求 不一致的话）	响应时间2-5s以内 
 * 使用时间段	营业部正常上班时间
 * 高峰使用时间段	8:00-17:30
 * 
 * 1.10	接口描述：
 * 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 * 获取客户账号信息接口	综合管理系统	通过客户获取客户账号信息
 * 修订记录 
 * 日期 	修订内容 	修订人员 	版本号 
 * 2012-05-11 	创建版 	毛建强	V0.1
 * 2012-07-16 	修订版 	毛建强	V0.5
 * 2012-07-25 	版本升级	毛建强	V0.9
 * 2012-10-12
 * 增加冻结和修改账号的颜色变化描述	毛建强	V1.0
 * 
 * 1.	SUC-312-锁定代收货款
 * 1.1	相关业务用例
 * BUC_FOSS_4.7.50.30_050  修改客户银行帐号
 * 1.2	用例描述
 * 营业部收银员对代收货款记录进行营业部冻结，会将代收货款状态修改为“营业部冻结”状态,收银员通过该功能修改该条代收货款信息中的银行账号信息。 
 * 1.3	用例条件
 * 条件类型	描述	引用系统用例
 * 前置条件	1、营业部冻结：
 * 1）代收货款应付单还没有进行退款或没被资金部冻结或没有在退款中
 * 2）存在对应的代收货款单据
 * 2、修改账号
 *   1）该代收货款已经被营业部冻结
 *   2）该代收货款存在对应的要修改的银行账号	《确认代收货款退款申请》系统用例中查询
 * 后置条件	1.	修改代收货款状态为“营业部冻结”
 * 2.	修改代收货款银行账号信息为所选择新银行账号信息	
 * 1.4	操作用户角色
 * 操作用户	描述
 * 收银员	收银员进行营业部冻结、修改账号和审核操作
 * 1.5	界面要求
 * 1.5.1	表现方式
 * Web
 * 1.5.2	界面原型
 * 查询冻结界面：
 *  
 * 修改账号界面：
 *  
 * 1.5.3	界面描述
 * 页面初始化组件描述：
 * 查询页面：
 * 1、	退款状态默认显示“待审核或退款失败”
 * 修改账号界面：
 * 1、	界面输入框除了银行账号外，都为不可编辑状态
 * 2、	默认将所选代收货款记录的银行账号信息初始化到修改银行账号界面对应组件中
 * 页面输入组件描述
 * 查询页面：
 * 1、	代收货款状态：代收货款操作状态
 * 	待审核或退款失败
 * 	未退款
 * 2、	单号查询：输入多个单号，以逗号隔开，最多不能超过10个。查询对应单号代收货款信息。单号默认为8-10位数字
 * 修改账号界面：
 * 1、银行账号控件默认为公共查询组件，且不可手动输入，只能选择。不能为空。选择银行账号，默认会更新界面上对应控件的信息为新账号对应的值。
 * 页面表格组件描述：
 * 1、	默认为分页显示，每页显示数据可选择20,30,50,100
 * 页面提供的钮控件描述：
 * 1、查询：查询代收货款列表功能
 * 2、营业部冻结：冻结所选的代收货款信息，使资金部不能操作冻结的代收货款
 * 3、修改账号：弹出客户账号信息，可以进行银行账号修改
 * 4、审核：进行审核操作。参见系统用例《DP-FOSS-结算系统用例-确认代收货款退款申请》
 * 
 * 1.6	操作步骤
 * 1.6.1	营业部冻结
 * 序号	基本步骤	相关数据	补充步骤
 * 1	查询数据	代收货款信息	参见《确认代收货款退款申请》系统用例中查询 
 * 2	勾选查询出来的代收货款数据	代收货款信息	
 * 3	点击营业部冻结按钮	代收货款信息	1、	判断是否选中行，如果没选中，则弹出提示
 * 扩展事件参见3a
 * 2、	判断如果所选记录代收货款状态为“营业部冻结”,则弹出提示
 * 扩展事件参见3b
 * 3、	更新代收货款的操作状态为“营业部冻结”，同时更新冻结单据颜色为橘黄色
 * 业务规则请参见SR1
 * 4、	添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 1.6.2	修改账号
 * 序号	基本步骤	相关数据	补充步骤
 * 4	勾选查询出来的代收货款数据（单选）	代收货款信息	
 * 5	点击修改账号按钮	代收货款信息	1、	判断所选记录数不等于1，则弹出提示
 * 参见扩展事件5a
 * 2、	判断所选中记录代收货款状态是否为“营业部冻结”，如果不是则弹出提示
 * 参见扩展事件5b
 * 3、	弹出修改账号界面
 * 4、	将所选代收货款信息填充到界面对应控件中
 * 6	点击客户银行账号公共组件	银行账号信息	1、调用综合管理系统接口，查询该客户银行账号信息，显示在基础控件中
 * 2、选择对应账号信息，判断如果当前代收货款为即日退且所选账号的银行不属于即日退开单银行限制基础资料中的银行，则弹出提示。反之将该账号信息重新填充到界面对应的控件
 * 3、 判断如果当前代收货款为即日退且所选银行账号的对公对私标志如果为“对公”，则弹出提示
 * 参见扩展事件6a
 * 7	点击提交按钮		1、	关闭修改账号按钮界面
 * 2、	修改对应代收货款对应行的银行账号信息、银行、收款人、客户收款人关系等信息
 * 3、	同时更新界面上该条代收货款信息，且修改该条记录颜色为红色
 * 扩展事件参见7a
 * 业务规则参见SR2
 * 4、	添加日志，用户可以通过统一的日志查询界面进行查询
 * 
 * 序号	扩展事件	相关数据	备注
 * 3a	如果没选中行，点击营业部冻结按钮，则弹出提示	提示信息	提示信息：“请至少选中1条记录进行冻结操作！”
 * 3b	所选记录代收货款状态为“营业部冻结”,则弹出提示	提示信息	“该记录已经被营业部冻结，不能重复冻结”
 * 5a	若果所选记录数不等于1，则弹出异常提示	提示信息	“请选中一条数据进行修改账号操作，只能选择一条哦！”
 * 5b	如果所选记录代收货款状态不是“营业部冻结”，则弹出异常提示	提示信息	“必须先进行营业部冻结，才能修改账号！”
 * 6a	如果校验账号信息错误，则直接返回，弹出错误信息	提示信息	1、	如果代收货款是即日退，并且所选银行不在即日退限制银行范围内，则弹出提示 “所选账号的银行暂不支
 * 持即日退。”
 * 2、	如果代收货款是即日退且所选账号为“对公”，则弹出异常提示：“即日退不能选择对公账号！”
 * 
 * 7a	点击“取消”按钮		关闭修改账号界面，不修改
 * 
 * 1.7	业务规则
 * 序号	描述
 * SR1	更新选中数据的代收货款状态为“营业部冻结”状态。更新条件：
 * 1、	代收货款状态必须是“未退款”或“待审核”或“退款失败”
 * SR2	更新对应代收货款中“收款人”、“银行账号”、“开户行”、“客户收款人关系”、“省份”、“城市”、“收款人手机号”，“开户行支行”，“对公对私标志”信息
 * 更新条件：
 * 代收货款状态为“营业部冻结”
 * 转化规则：
 * 收款人 ：弹出银行账号界面的开户姓名
 * 银行账号：弹出银行账号界面的银行账号
 * 开户行：弹出银行账号界面的开户银行编码
 * 客户收款人关系：弹出银行账号界面的客户收款人关系
 * 开户行支行：弹出银行账号界面的银行地址
 * 省份：弹出银行账号界面的开户行省份
 * 城市：弹出银行账号界面的开户行城市
 * 收款人手机号：弹出银行账号界面的手机号码
 * 对公对私标志：弹出界面银行账号对公私标志
 * 银行行号：银行的行号
 * 
 * 1.8	数据元素
 * 1.8.1	页面查询条件（输入）
 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	
 * 备注
 * 
 * 代收货款状态	代收货款状态	枚举	代收货款退款状态	10	是	无
 *   单号	运单号	文本	请输入正确单号：8-10位数字	10	否	无
 * 
 * 1.8.2	代收货款信息（输出）
 * 字段名称 	说明 	输出限制	输入项提示文本	长度	是否必填	备注
 * 运单号	运单号	无	无	10	无	来源于运单
 * 开单金额	代收货款金额	无	无	10	无	运单开单确认的代收货款
 * 冲应收金额	冲销的金额	无	无	10	无	运单应收冲应付的金额
 * 应退金额	应付代收货款金额。取应付单未核销金额	无	无	10	无	开单金额=冲应收金额+应退金额
 * 退款类型	代收货款退款类型	无	无	20	无	无
 * 退款状态	代收货款退款状态	无	无	20	无	无
 * 客户名称	发货客户名称	无	无	20	无	来源于运单
 * 收款人	代收货款收款人姓名	无	无	20	无	来源于crm
 * 账号	收款账号	无	无	40	无	来源于crm
 * 开户行	收款账号所属银行	无	无	20	无	来源于crm
 * 客户收款人关系	发货客户与收款人关系	无	无	50	无	来源于crm
 * 对公对私标志	账号类型	无	无	20	无	隐藏
 * 省份	账号所属银行所在省份	无	无	20	无	隐藏
 * 城市	账号所属银行所在城市	无	无	20	无	隐藏
 * 银行地址（支行）	账号所属银行具体地址	无	无	50	无	隐藏
 * 收款人手机号	账号开户人的手机号	无	无	11	无	隐藏
 * 银行行号	银行行号	无	无	50	无	无
 * 开单日期	运单开单日期	无	无	参照统一标准	无	无
 * 部门	代收货款所属部门	无	无	一标准	无	等于当前登录部门
 * 备注	备注信息	无	无	255	无	显示代收货款备注信息：如资金部汇款失败，会填写失败原因到备注中
 * 1.8.3	代收货款更新的字段（输出）
 * 字段名称 	说明 	输出限制	输出项提示文本	长度	是否必填	备注
 * 代收货款状态	代收货款的状态	枚举	无	50	是	无
 * 收款人	代收货款银行账号对应的收款人	文本	无	50	是	无
 * 账号	代收货款银行账号	文本	无	50	是	无
 * 开户行	银行账号所属开户行	文本	无	20	是	无
 * 银行地址（支行）	开户银行详细地址	文本	无	100	是	无
 * 客户收款人关系	客户收款人关系	文本	无	100	是	无
 * 省份	开户银行所属省份	文本	无	20	是	无
 * 城市	开户银行所属城市	文本	无	20	是	无
 * 收款人手机号	收款人手机号	文本	无	11	是	无
 * 对公对私标志	银行账号属性表明是对公还是对私	文本	无	20	是	无
 * 
 * 1.9	非功能性需求
 * 使用量	每天收银员审核约为700票
 * 2012年全网估计用户数	收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
 * 响应要求（如果与全系统要求 不一致的话）	响应时间2-5s以内 
 * 使用时间段	营业部正常上班时间
 * 高峰使用时间段	8:00-17:30
 * 
 * 1.10	接口描述：
 * 接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 * 获取客户账号信息接口	综合管理系统	通过客户获取客户账号信息
 *
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.dao.ISettlementInfoQueryDao;
import com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISettlementInfoQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.DestFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.InvoiceFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrgSettlementInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OrigFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherFeeInfo;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillFinanceInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.WaybillSettlementInfoDto;
import com.deppon.foss.util.NumberUtils;

/**
 * 查询运单的代收货款、装卸费、发票信息Service [提供给综合管理使用]
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-21 上午9:22:24
 * @since
 * @version
 */
public class SettlementInfoQueryService implements ISettlementInfoQueryService {

	/**
	 * 查询Dao
	 */
	private ISettlementInfoQueryDao settlementInfoQueryDao;
	
	/**
	 * 小票service
	 */
	private IOtherRevenueService otherRevenueService;
	

	/**
	 * 根据运单号和开单日期查询运单的代收货款信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:52:30
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public WaybillSettlementInfoDto queryCodFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate) {
		if (StringUtils.isEmpty(waybillNo) || startDate == null || endDate == null) {
			throw new SettlementException("查询运单的代收货款信息参数不能为空！");
		}

		// 日期格式化到秒
		startDate = DateUtils.addDays(startDate, -1);
		Date stDate = DateUtils.truncate(startDate, Calendar.SECOND);
		Date eDate = DateUtils.truncate(endDate, Calendar.SECOND);

		List<WaybillSettlementInfoDto> list = settlementInfoQueryDao.queryCodFeeByWaybillNO(
				waybillNo, stDate, eDate);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		WaybillSettlementInfoDto resultDto = new WaybillSettlementInfoDto();
		for (WaybillSettlementInfoDto dto : list) {
			if (SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__COD_RECEIVABLE.equals(dto
					.getBillType())
					|| SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__AIR_AGENCY_COD
							.equals(dto.getBillType())) {
				// 金额
				resultDto.setCodFee(dto.getAmount());
			} else if (SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD
					.equals(dto.getBillType())) {
				// 代收货款应付金额
				resultDto.setPayableAmount(dto.getAmount());

				// 代收货款已核销金额
				resultDto.setWriteoffAmount(dto.getVerifyAmount());

				// 代收货款未核销金额
				resultDto.setRetreatAmount(dto.getUnverifyAmount());

				// 退款状态
				resultDto.setCodRefundStatus(dto.getStatus());

				// 备注
				resultDto.setCodRefundNotes(dto.getNotes());
			}
		}
		return resultDto;
	}

	/**
	 * 根据运单号和开单日期查询运单的装卸费信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:52:50
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public WaybillSettlementInfoDto queryServiceFeeByWaybillNO(String waybillNo, Date startDate,
			Date endDate) {
		if (StringUtils.isEmpty(waybillNo) || startDate == null || endDate == null) {
			throw new SettlementException("查询运单的装卸费信息参数不能为空！");
		}

		// 日期格式化到秒
		startDate = DateUtils.addDays(startDate, -1);
		Date stDate = DateUtils.truncate(startDate, Calendar.SECOND);
		Date eDate = DateUtils.truncate(endDate, Calendar.SECOND);
		return settlementInfoQueryDao.queryServiceFeeByWaybillNO(waybillNo, stDate, eDate);
	}

	/**
	 * 根据运单号和开单日期查询运单的发票信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-21 上午8:53:21
	 * @param waybillNo
	 * @param startDate
	 * @param endDate
	 * @param origOrgCode
	 *            始发部门编码
	 * @param destOrgCode
	 *            到达部门编码
	 * @return
	 */
	@Override
	public WaybillSettlementInfoDto queryInvoiceByWaybillNO(String waybillNo, String origOrgCode,
			String destOrgCode, Date startDate, Date endDate) {
		if (StringUtils.isEmpty(waybillNo) || startDate == null || endDate == null) {
			throw new SettlementException("查询运单的发票信息参数不能为空！");
		}
		if (StringUtils.isEmpty(origOrgCode)) {
			throw new SettlementException("运单出发部门编码不能为空");
		}
		if (StringUtils.isEmpty(destOrgCode)) {
			throw new SettlementException("运单最终配载部门编码不能为空");
		}

		// 日期格式化到秒
		startDate = DateUtils.addDays(startDate, -1);
		Date stDate = DateUtils.truncate(startDate, Calendar.SECOND);
		Date eDate = DateUtils.truncate(endDate, Calendar.SECOND);
		List<WaybillSettlementInfoDto> list = settlementInfoQueryDao.queryInvoiceByWaybillNO(
				waybillNo, stDate, eDate);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		WaybillSettlementInfoDto resultDto = new WaybillSettlementInfoDto();
		BigDecimal origInvoiceAmount = BigDecimal.ZERO;
		BigDecimal destInvoiceAmount = BigDecimal.ZERO;
		for (WaybillSettlementInfoDto dto : list) {

			// 和运单的始发部门编码相同
			if (origOrgCode.equals(dto.getOrgCode())) {
				origInvoiceAmount = NumberUtils.add(origInvoiceAmount, dto.getAmount());
			} else if (destOrgCode.equals(dto.getOrgCode())) {
				// 和运单的最终配载部门编码相同
				destInvoiceAmount = NumberUtils.add(destInvoiceAmount, dto.getAmount());
			}
		}

		// 始发开票金额
		resultDto.setOrigInvoiceAmount(origInvoiceAmount);

		// 到达开票金额
		resultDto.setDestInvoiceAmount(destInvoiceAmount);
		return resultDto;
	}

	/**
	 * 根据部门编码和部门创建日期查找该部门的应收、应付、预收、预付未核销的金额信息
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-26 下午8:54:50
	 * @param orgCode
	 *            部门编码
	 * @param orgCreateTime
	 *            部门创建时间
	 * @return
	 */
	@Override
	public OrgSettlementInfoDto queryOrgSettlementInfo(String orgCode, Date orgCreateTime) {
		if (StringUtils.isEmpty(orgCode) || orgCreateTime == null) {
			throw new SettlementException("查询部门财务信息参数不能为空！");
		}

		// 日期格式化到秒
		Date orgCrTime = DateUtils.truncate(orgCreateTime, Calendar.SECOND);
		List<OrgSettlementInfoDto> list = this.settlementInfoQueryDao.queryOrgSettlementInfo(
				orgCode, orgCrTime);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		OrgSettlementInfoDto resultDto = new OrgSettlementInfoDto();
		for (OrgSettlementInfoDto dto : list) {
			if (SettlementConstants.SETTLEMENT_BILL_TYPE_RECEIVABLE.equals(dto.getType())) {
				// 类型为：应收单
				resultDto.setReceivableAmount(dto.getAmount());
			} else if (SettlementConstants.SETTLEMENT_BILL_TYPE_PAYABLE.equals(dto.getType())) {
				// 类型为：应付单
				resultDto.setPayableAmount(dto.getAmount());
			} else if (SettlementConstants.SETTLEMENT_BILL_TYPE_DEPOSIT_RECEIVED.equals(dto
					.getType())) {
				// 类型为：预收单
				resultDto.setDepositReceivedAmount(dto.getAmount());
			} else if (SettlementConstants.SETTLEMENT_BILL_TYPE_ADVANCED_PAYMENT.equals(dto
					.getType())) {
				// 类型为：预付单
				resultDto.setAdvancedPaymentAmount(dto.getAmount());
			}
		}
		return resultDto;
	}

	/**
	 * 1、始发运费：
	 * 
	 * 包括始发付款方式（现金、银行卡、临时欠款、月结、网上支付）、实收始发运费（指实际收取的始发运费金额，临欠和月结还款时，
	 * 实收始发运费显示已还款金额）、收款日期（收取始发运费的日期，如果是临欠或月结，取应收单核销日期）。
	 * 
	 * @param waybillNo
	 */
	private OrigFeeInfo queryOrigFee(String waybillNo) {

		return settlementInfoQueryDao.queryOrigFee(waybillNo);
	}

	/**
	 * 2.到付费用：
	 * 
	 * 包括实收到付总额（代收货款金额+到付款）、实收到付运费（仅指到付款）、到达付款方式（包括现金、银行卡、网上支付、月结、临欠， 到付转临欠
	 * /月结时，实收到付运费不显示<等还款完成时再显示>，但到达付款方式要显示。如果到付款的结款方式有多种时，要显示多种付款方式）、到达付款日期
	 * （指到付款收回日期，如果是到付转月结/临欠，则指应收单核销日期）
	 * 
	 * @param waybillNo
	 * @return
	 */
	private DestFeeInfo queryDestFee(String waybillNo) {

		return settlementInfoQueryDao.queryDestFee(waybillNo);

	}

	/**
	 * 3.代收货款：
	 * 
	 * 包括实收代收货款、收款方式（实收代收货款的收款方式）、冲应收金额（代收货款冲应收的金额）、应退金额（剩余应退金额）、退款状态（指代收货款付款状态）
	 * 
	 * @param waybillNo
	 * @return
	 */
	private CODFeeInfo queryCODFee(String waybillNo) {

		CODFeeInfo info = settlementInfoQueryDao.queryCODFee(waybillNo);
		
		if (info != null && info.getVerifyRcvAmount() == null) {
			info.setVerifyRcvAmount(BigDecimal.ZERO);
			info.setReturnableAmount(info.getCodAmount().subtract(info.getVerifyRcvAmount()));
		}
		
		return info;

	}

	/**
	 * 4.其他费用：
	 * 
	 * （1）装卸费：包括装卸费金额（开单装卸费的金额）、付款状态（包括未支付、付款中、已支付三个状态，未支付指应付单未生成付款单时的状态；
	 * 付款中指应付单已生成付款单，但还未核销；已支付指应付单已核销的状态）、付款方式（包括现金和电汇）。
	 * 
	 * （2）服务补救：包括减免金额（服务补救应付单的应付金额
	 * ）、付款状态（包括未支付、付款中、已支付三个状态，未支付指应付单未生成付款单时的状态；付款中指应付单已生成付款单
	 * ，但还未核销；已支付指应付单已核销的状态）、付款方式（包括现金和电汇）。
	 * 
	 * （3）退运费：包括退运费金额（退运费应付单应付金额）、冲应收金额（应收冲应付金额
	 * ）、应退金额（冲应收后剩余金额）、付款状态（包括未支付、付款中、已支付三个状态
	 * ，未支付指应付单未生成付款单时的状态；付款中指应付单已生成付款单，但还未核销；已支付指应付单已核销的状态）、付款方式（包括现金和电汇）。
	 * 
	 * @param waybillNo
	 * @return
	 */
	private List<OtherFeeInfo> queryOtherFee(String waybillNo) {

		return settlementInfoQueryDao.queryOtherFee(waybillNo);

	}

	/**
	 * 5.发票：
	 * 
	 * 包括始发已开票金额和到达已开票金额，金额取数与现有系统规则保持不变，只是增加"已"字以明确开票状态。
	 * 
	 * @param waybillNo
	 * @return
	 */
	private List<InvoiceFeeInfo> queryInvoiceFee(String waybillNo) {
		// 存放开票来源单据
		
		List<String> sourceBills = new ArrayList<String>();
		
		sourceBills.add(waybillNo);
		
		// 根据运单号查询是否存在小票记录
		List<OtherRevenueEntity> otherRevenueEntities = otherRevenueService.queryOtherRevenueByWayBillNO(waybillNo);
		
		if(otherRevenueEntities != null) {
			for(OtherRevenueEntity otherRevenueEntity : otherRevenueEntities) {
				// 取出小票的单号放到集合里
				sourceBills.add(otherRevenueEntity.getOtherRevenueNo());
			}
		}
		
		
		
		return settlementInfoQueryDao.queryInvoiceFee(sourceBills);

	}

	/**
	 * 查询运单的财务情况
	 * 
	 * @param waybill
	 * @return
	 */
	public WaybillFinanceInfoDto queryWaybillFinanceInfo(String waybillNo) {

		WaybillFinanceInfoDto dto = new WaybillFinanceInfoDto();
		dto.setWaybillNo(waybillNo);
		dto.setOrigFeeInfo(queryOrigFee(waybillNo));
		dto.setDestFeeInfo(queryDestFee(waybillNo));
		dto.setCodFeeInfo(queryCODFee(waybillNo));
		dto.setOtherFeeInfos(queryOtherFee(waybillNo));
		dto.setInvoiceFeeInfos(queryInvoiceFee(waybillNo));

		return dto;
	}

	/**
	 * @param settlementInfoQueryDao
	 *            the settlementInfoQueryDao to set
	 */
	public void setSettlementInfoQueryDao(ISettlementInfoQueryDao settlementInfoQueryDao) {
		this.settlementInfoQueryDao = settlementInfoQueryDao;
	}

	/**
	 * @param otherRevenueService the otherRevenueService to set
	 */
	public void setOtherRevenueService(IOtherRevenueService otherRevenueService) {
		this.otherRevenueService = otherRevenueService;
	}

	
}
