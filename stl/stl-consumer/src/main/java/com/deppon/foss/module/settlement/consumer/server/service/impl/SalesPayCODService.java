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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/SalesPayCODService.java
 * 
 * FILE NAME        	: SalesPayCODService.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 * 
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
 * 
 * 
 * 
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
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBankService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.INonfixedCusAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IUserService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.NonfixedCusAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BankException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.server.service.ICUBCGrayService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCCodAuditResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayRequestDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CUBCGrayResultDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayRequestEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.CubcGrayResponseEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.VestBatchResult;
import com.deppon.foss.module.settlement.common.api.shared.exception.CUBCGrayException;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.HttpClientUtils;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodAuditService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ISalesPayCODService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CodAuditEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODStartApplyQueryDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CodAuditDto;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.CODVo;
import com.deppon.foss.util.define.FossConstants;

/**
 * 营业部代收货款客户账号状态控制.
 *
 * @author dp-zengbinwen
 * @date 2012-10-15 上午11:30:22
 */
public class SalesPayCODService implements ISalesPayCODService {
	
	private String queryCodAuditListUrl;
	public void setQueryCodAuditListUrl(String queryCodAuditListUrl) {
		this.queryCodAuditListUrl = queryCodAuditListUrl;
	}
	
	private String grayByWaybillNoUrl;
	public void setGrayByWaybillNoUrl(String grayByWaybillNoUrl) {
		this.grayByWaybillNoUrl = grayByWaybillNoUrl;
	}
	
	private static final String SERVICE_CODE = "com.deppon.foss.module.settlement.consumer.server.service.impl.SalesPayCODService";

	private ICUBCGrayService cUBCGrayService;
	
	public void setcUBCGrayService(ICUBCGrayService cUBCGrayService) {
		this.cUBCGrayService = cUBCGrayService;
	}

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(SalesPayCODService.class);

	/** 代收货款服务. */
	private ICodCommonService codCommonService;

	/** 综合:客户开户银行Service接口. */
	private ICusAccountService cusAccountService;
	
	/** 综合：临欠散客开户银行账户Service接口. */
	private INonfixedCusAccountService nonfixedCusAccountService;

	/** 综合：用户服务. */
	private IUserService userService;
	
	/** 银行Service接口实现. */
	private IBankService bankService;
	
	/** 应收单服务. */
	private IBillReceivableService billReceivableService;
	
    /**
     * 代收货款审核
     */
    private  ICodAuditService codAuditService;

	/**
	 * 出发申请查询代收货款【应付部门、代收货款状态、分页号都不允许为空】.
	 *
	 * @param currentInfo the current info
	 * @param statuses the statuses
	 * @param offset the offset
	 * @param limit the limit
	 * @return the list
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:38:42
	 */
	public List<CODDto> queryStartApplyBillCOD(CurrentInfo currentInfo,
			List<String> statuses, int offset, int limit)
			throws SettlementException {

		LOGGER.debug("Start service,currentInfo.getEmpCode():" + currentInfo.getEmpCode());

		// 设置查询参数
		BillCODStartApplyQueryDto queryDto = validateAndSetQueryParam(
				currentInfo, statuses);
		queryDto.setLimit(limit);
		queryDto.setOffset(offset);

		LOGGER.debug("End service.");

		// 查询并返回结果
		return codCommonService.queryStartApplyBillCOD(queryDto);
	}
	
	/**
	 * @author 218392 zhangyongxue 2015-11-09 16:07:20
	 * 代收货款优化需求
	 */
	public List<CODDto> queryStartApplyBillCODZyx(CurrentInfo currentInfo,
			List<String> statuses, int offset, int limit ,CODVo salesPayCODVO)
			throws SettlementException {

		LOGGER.debug("Start service,currentInfo.getEmpCode():" + currentInfo.getEmpCode());

		// 设置查询参数
		BillCODStartApplyQueryDto queryDto = validateAndSetQueryParam(
				currentInfo, statuses);
		
		
		queryDto.setLimit(limit);
		queryDto.setOffset(offset);
		
		/**
		 * @author 218392 zhangyongxue 2015-11-09 15:27:00
		 */
		queryDto.setTimeType(salesPayCODVO.getTimeType());
		queryDto.setTimeBegin(salesPayCODVO.getTimeBegin());
		queryDto.setTimeEnd(salesPayCODVO.getTimeEnd());
		queryDto.setCusAccountName(salesPayCODVO.getCusAccountName());

		LOGGER.debug("End service.");

		// 查询并返回结果
		return codCommonService.queryStartApplyBillCOD(queryDto);
	}

	/**
	 * 出发申请查询代收货款大小【应付部门、代收货款状态都不允许为空】.
	 *
	 * @param currentInfo the current info
	 * @param statuses the statuses
	 * @return the int
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午4:38:42
	 */
	public int queryStartApplyBillCODSize(CurrentInfo currentInfo,
			List<String> statuses) throws SettlementException {

		LOGGER.debug("Start service,currentInfo.getEmpCode:" + currentInfo.getEmpCode());
		
		// 设置查询参数
		BillCODStartApplyQueryDto queryDto = validateAndSetQueryParam(
				currentInfo, statuses);

		LOGGER.debug("End service.");

		// 查询并返回结果
		return codCommonService.queryStartApplyBillCODSize(queryDto);
	}
	
	/**
	 * @author 218392 zhangyongxue
	 */
	public int queryStartApplyBillCODSizeZyx(CurrentInfo currentInfo,
			List<String> statuses,CODVo salesPayCODVO) throws SettlementException {

		LOGGER.debug("Start service,currentInfo.getEmpCode:" + currentInfo.getEmpCode());
		
		// 设置查询参数
//		BillCODStartApplyQueryDto queryDto = validateAndSetQueryParam(
//				currentInfo, statuses);
		// 判断代收货款状态不能为空
			if (CollectionUtils.isEmpty(statuses)) {
				throw new SettlementException("代收货款状态为空，不能进行出发申请查询");
			}

			BillCODStartApplyQueryDto queryDto = new BillCODStartApplyQueryDto();

			// 设置参数,是否有效、代收货款状态、应付部门、应付单是否有效、应付单单据类型
			queryDto.setActive(FossConstants.ACTIVE);
			queryDto.setStatuses(statuses);
			// 设置用户数据查询权限
			queryDto.setUserCode(currentInfo.getEmpCode());
			/**
			 * @author 218392 张永雪 2015-11-12 09:52:00
			 * 设置当前登陆部门code
			 */
			queryDto.setCurrentOrgCode(currentInfo.getCurrentDeptCode());
			queryDto.setPayableActive(FossConstants.ACTIVE);
			queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

			/**
			 * @author 218392 zhangyongxue 2015-11-09 15:27:00
			 */
			queryDto.setTimeType(salesPayCODVO.getTimeType());
			queryDto.setTimeBegin(salesPayCODVO.getTimeBegin());
			queryDto.setTimeEnd(salesPayCODVO.getTimeEnd());
			queryDto.setCusAccountName(salesPayCODVO.getCusAccountName());
			
		LOGGER.debug("End service.");

		// 查询并返回结果
		return codCommonService.queryStartApplyBillCODSize(queryDto);
	}

	/**
	 * 校验并设置出发申请查询参数.
	 *
	 * @param currentInfo the current info
	 * @param statuses the statuses
	 * @return the bill cod start apply query dto
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-30 上午9:33:52
	 */
	private BillCODStartApplyQueryDto validateAndSetQueryParam(
			CurrentInfo currentInfo, List<String> statuses)
			throws SettlementException {

		// 判断代收货款状态不能为空
		if (CollectionUtils.isEmpty(statuses)) {
			throw new SettlementException("代收货款状态为空，不能进行出发申请查询");
		}

		BillCODStartApplyQueryDto queryDto = new BillCODStartApplyQueryDto();

		// 设置参数,是否有效、代收货款状态、应付部门、应付单是否有效、应付单单据类型
		queryDto.setActive(FossConstants.ACTIVE);
		queryDto.setStatuses(statuses);
		/**
		 * @author 218392 张永雪 2015-11-12 09:58:00
		 * 设置当前登陆部门
		 */
		queryDto.setCurrentOrgCode(currentInfo.getCurrentDeptCode());
		// 设置用户数据查询权限
		queryDto.setUserCode(currentInfo.getEmpCode());
		queryDto.setPayableActive(FossConstants.ACTIVE);
		queryDto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);

		return queryDto;
	}

	/**
	 * 营业部冻结代收货款.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 上午10:17:54
	 */
	@Transactional
	public void freezeBillPayCOD(String[] entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("Service start,entity ids:" + Arrays.toString(entityIds));

		// 如果实体为空，则抛出异常
		if (entityIds == null || entityIds.length == 0) {
			throw new SettlementException("代收货款实体为空，不能进行冻结代收货款操作");
		}

		List<CODEntity> codList = codCommonService.queryByIds(Arrays.asList(entityIds));
		String status = null;

		// 定义允许冻结的代收货款状态:未退款、待审核、退款失败
		List<String> acceptStatusList = new ArrayList<String>();
		acceptStatusList
				.add(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
		acceptStatusList
				.add(SettlementDictionaryConstants.COD__STATUS__APPROVING);
		acceptStatusList
				.add(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE);

		// 循环处理
		for (CODEntity entity : codList) {

			// 根据ID查询代收货款
			//entity = codCommonService.queryById(entityId);
			if (entity == null) {
				throw new SettlementException("找不到代收货款实体");
			}

			// 判断代收货款状态
			status = entity.getStatus();
			if (!acceptStatusList.contains(status)) {
				throw new SettlementException("代收货款状态不为未退款、待审核、退款失败,不能冻结代收货款");
			}
			/**
			 * @author 218392 zhangyongxue 
			 * @date 2015-12-30 10:47:30
			 * 1、对于资金安全控制组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁”
			 * 2、对于资金复核小组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
			 */
			String waybillNo = entity.getWaybillNo();
			if(StringUtils.isNotEmpty(waybillNo)){
				CodAuditDto dto = new CodAuditDto();
				List<String> waybillNoList = new ArrayList<String>();
				waybillNoList.add(waybillNo);
				dto.setWaybillNos(waybillNoList);
				//代收货款审核灰度   353654 ------------------------ start
	            String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
                	arrayList.add(waybillNo);
                	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
                			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".freezeBillPayCOD",
                			SettlementConstants.TYPE_FOSS);
                	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();		
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
	            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	            	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
					if(codAuditEntityList != null && codAuditEntityList.size() > 0){
						CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
						if(codAuditEntity != null){
							//如果为资金部锁定
							if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
							}
							if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
							}
						}
					}else{
						LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
					}
	            }
	            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
	    				CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
	    				requestDto.setWaybillNo(waybillNoList);
	    				CUBCCodAuditResultDto resultDto = null;
						try {
							resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
						} catch (Exception e) {
							LOGGER.error("调用CUBC代收货款审核接口连接异常...");
		    				throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
						}
	    				if(resultDto != null){
	    					if(StringUtils.isNotBlank(resultDto.getMeg())){
	    						LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
	    						throw new SettlementException(resultDto.getMeg());
	    					}
	    					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
	    					if(CollectionUtils.isNotEmpty(auditList)){
	    						com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
	    						if(codAuditDto1 != null){
	    							//如果为资金部锁定
	    							if(StringUtils.equalsIgnoreCase("FL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
	    							}
	    							if(StringUtils.equals("RL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
	    							}
	    						}
	    					}else{
	    						LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
	    					}
	    				}
	            }
	            //代收货款审核灰度   353654 ------------------------ end
			}

			// 设置为营业部冻结
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE);
			entity.setOrgFreezeTime(new Date());
			entity.setOrgFreezeUserCode(currentInfo.getEmpCode());
			entity.setOrgFreezeUserName(currentInfo.getEmpName());

			// 更新代收货款冻结状态
			codCommonService.updateOrgFreezeStatus(entity, currentInfo);

		}

		LOGGER.info("End service.");
	}

	/**
	 * 获取客户的银行账号,目前接口未确定.
	 *
	 * @param customerCode the customer code
	 * @return the bill pay cod bank accounts
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 上午11:54:35
	 */
	public List<CusAccountEntity> getBillPayCODBankAccounts(String customerCode)
			throws SettlementException {

		// 判断客户编码不能为空
		if (StringUtils.isEmpty(customerCode)) {
			throw new SettlementException("客户编码为空");
		}

		// 调用综合接口获取银行账号信息
		List<CusAccountEntity> accountList = cusAccountService.queryAccountInfoByCustCode(customerCode);
		
		return accountList;
	}

	/**
	 * 修改代收货款账号.
	 *
	 * @param entity the entity
	 * @param currentInfo the current info
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-29 下午2:00:10
	 */
	@Transactional
	public void changeBillPayCODBankAccounts(CODEntity entity,
			CurrentInfo currentInfo) throws SettlementException {

		LOGGER.info("Start service,entityId:"
				+ (entity == null ? null : entity.getId()));

		// 判断实体及账号关键信息是否为空
		if (entity == null) {
			throw new SettlementException("代收货款实体不能为空");
		}
		assumeObjectNotEmpty(entity.getAccountNo(), "代收货款账号不能为空");
		assumeObjectNotEmpty(entity.getBankHQCode(), "开户行编码不能为空");
		assumeObjectNotEmpty(entity.getBankHQName(), "开户行名称不能为空");
		assumeObjectNotEmpty(entity.getPublicPrivateFlag(), "对公对私标志不能为空");
		assumeObjectNotEmpty(entity.getProvinceCode(), "省份编码不能为空");
		assumeObjectNotEmpty(entity.getCityCode(), "城市编码不能为空");
		assumeObjectNotEmpty(entity.getProvinceName(), "省份不能为空");
		assumeObjectNotEmpty(entity.getCityName(), "城市不能为空");
		assumeObjectNotEmpty(entity.getBankBranchCode(), "开户行支行编码不能为空");
		assumeObjectNotEmpty(entity.getBankBranchName(), "开户行支行不能为空");
		assumeObjectNotEmpty(entity.getPayeeName(), "收款人不能为空");
		/*assumeObjectNotEmpty(entity.getPayeePhone(), "手机号码不能为空");*/
		assumeObjectNotEmpty(entity.getPayeeRelationship(), "客户收款人关系不能为空");
		
		/**
		 * @author 218392 zhangyongxue 
		 * @date 2015-12-30 11:34:30
		 * 1、对于资金安全控制组锁定后的单据：
		 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
		 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁”
		 * 2、对于资金复核小组锁定后的单据：
		 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
		 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
		 */
		String waybillNo = entity.getWaybillNo();
		if(StringUtils.isNotEmpty(waybillNo)){
			CodAuditDto dto = new CodAuditDto();
			List<String> waybillNoList = new ArrayList<String>();
			waybillNoList.add(waybillNo);
			dto.setWaybillNos(waybillNoList);
			//代收货款审核灰度   353654 ------------------------ start
            String vestSystemCode = null;
            try {
            	ArrayList<String> arrayList = new ArrayList<String>();
            	arrayList.add(waybillNo);
            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".changeBillPayCODBankAccounts",
            			SettlementConstants.TYPE_FOSS);
            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
            	List<VestBatchResult> list = response.getVestBatchResult();
            	vestSystemCode = list.get(0).getVestSystemCode();	
			} catch (Exception e) {
				LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
				throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
			}
            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
            	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
    			if(codAuditEntityList != null && codAuditEntityList.size() > 0){
    				CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
    				if(codAuditEntity != null){
    					//如果为资金部锁定
    					if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
    						throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
    					}
    					if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
    						throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
    					}
    				}
    			}else{
    				LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
    			}
            }
            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
    				CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
    				requestDto.setWaybillNo(waybillNoList);
    				CUBCCodAuditResultDto resultDto = null;
					try {
						resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
					} catch (Exception e) {
						LOGGER.error("调用CUBC代收货款审核接口连接异常...");
	    				throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
					}
    				if(resultDto != null){
    					if(StringUtils.isNotBlank(resultDto.getMeg())){
    						LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
    						throw new SettlementException(resultDto.getMeg());
    					}
    					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
    					if(CollectionUtils.isNotEmpty(auditList)){
    						com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
    						if(codAuditDto1 != null){
    							//如果为资金部锁定
    	    					if(StringUtils.equalsIgnoreCase("FL", codAuditDto1.getLockStatus())){
    	    						throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
    	    					}
    	    					if(StringUtils.equals("RL", codAuditDto1.getLockStatus())){
    	    						throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
    	    					}
    						}
    					}else{
    						LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
    					}
    				}
            }
            //代收货款审核灰度   353654 ------------------------ start
		}

		codCommonService.updateBankAccounts(entity, currentInfo);

		LOGGER.info("End service.");
	}

	/**
	 * 判断对象不能为空，如果为空，则抛出异常.
	 *
	 * @param obj the obj
	 * @param msg the msg
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-30 下午3:49:49
	 */
	@SuppressWarnings("rawtypes")
	private void assumeObjectNotEmpty(Object obj, String msg)
			throws SettlementException {

		// 如果对象字符串，且为空，则抛异常
		if (obj instanceof String) {
			if (StringUtils.isEmpty((String) obj)) {
				throw new SettlementException(msg);
			}
		}

		// 如果对象为集合，且为空，则抛异常
		else if (obj instanceof Collection) {
			if (CollectionUtils.isEmpty((Collection) obj)) {
				throw new SettlementException(msg);
			}
		}

		// 其它，判断对象是否为空
		else {
			if (obj == null) {
				throw new SettlementException(msg);
			}
		}

	}

	/**
	 * 判断银行账号是否存在.
	 *
	 * @param account the account
	 * @param customerCode the customer code
	 * @return true, if is account exist
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-9 下午3:44:18
	 */
	private boolean isAccountExist(String account, String customerCode) {

		// 如果银行账号、客户编码为空，返回FALSE
		if (StringUtils.isEmpty(account) || StringUtils.isEmpty(customerCode)) {
			return false;
		}

		// 查询客户银行账号，如果匹配，则返回TRUE
		List<CusAccountEntity> accountList = getBillPayCODBankAccounts(customerCode);
		if (CollectionUtils.isNotEmpty(accountList)) {
			for (CusAccountEntity entity : accountList) {
				if (account.equalsIgnoreCase(entity.getAccountNo())) {
					return true;
				}
			}
		}
		
		// 查询散客银行信息，如果匹配，则返回TRUE 
		List<NonfixedCusAccountEntity> nonfixedCusAccountList = nonfixedCusAccountService.queryAccountInfoByCustCode(customerCode);
		if (CollectionUtils.isNotEmpty(nonfixedCusAccountList)) {
			for (NonfixedCusAccountEntity entity : nonfixedCusAccountList) {
				if (account.equalsIgnoreCase(entity.getAccountNo())) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/**
	 * 获得客户存在应收账款的代收款客户信息--审核退类型的代收货款
	 * @author guxinhua
	 * @date 2013-4-9 下午5:53:15
	 * @param codDto
	 * @return
	 * @throws SettlementException
	 */
	public List<CODDto> queryCODDtoCheckReceivableUnAmount(List<CODDto> codDtoList)
			throws SettlementException{
		LOGGER.info("获得客户存在应收账款的代收款客户信息 开始.");
		if(CollectionUtils.isEmpty(codDtoList)){
			throw new SettlementException("参数为空");
		}
		// list<CODDto> 转 list<Id>
		@SuppressWarnings("unchecked")
		List<String> codIds = (List<String>) CollectionUtils.collect(codDtoList,new Transformer(){
	        public java.lang.Object transform(final java.lang.Object input){
	            return ((CODDto)input).getId();
	          }
	        });
		
		List<CODEntity> codList = codCommonService.queryByIds(codIds);
		
		List<CODDto> resultDtoList = new ArrayList<CODDto>();//存代收货款存在应收账款cod
		
		// 定义不需要验证的代收货款类型
		List<String> codTypeList = new ArrayList<String>();
		codTypeList.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY);
		codTypeList.add(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY);
		
		out: for (final CODDto dto : codDtoList) {
			// 根据ID查询代收货款
			String codId = dto.getId();
			if (StringUtils.isBlank(codId)) {
				throw new SettlementException("代收货款ID参数无效");
			}
			
			// list<CODEntity> 查找 dto 的 CODEntity
			CODEntity entity = (CODEntity) CollectionUtils.find(codList, new Predicate() {
				@Override
				public boolean evaluate(Object object) {
					if (StringUtils.equals(((CODEntity)object).getId(), dto.getId()))
					return true;
					return false;
				}
			});
			
			if (entity == null) {
				throw new SettlementException("找不到运单[" + dto.getWaybillNo() + "]代收货款实体");
			} else {
				if(codTypeList.contains(entity.getCodType())){
					// 代收货款是即日退，三日退的，不需要判断处理。
					continue out;
				}
			}

			// 判断代收货款状态是否能审核代收货款
			String status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE.equals(status)) {
				throw new SettlementException("运单[" + dto.getWaybillNo() + "]代收货款非营业部冻结，不能进行审核代收货款操作");
			}
			
			String customerCode = dto.getPayableCustomerCode();
			if (StringUtils.isBlank(customerCode)) {
				throw new SettlementException("运单[" + dto.getWaybillNo() + "]代收货款的客户编码为空，不能进行审核代收货款操作");
			}
			
			if(resultDtoList.size() > 0 ){
				for (CODDto codDto2 : resultDtoList) { // 先判断客户代收款编码已存在结果集中
					if (StringUtils.equals(codDto2.getPayableCustomerCode(),customerCode)) {
						resultDtoList.add(dto); // 直接添加
						continue out;
					}
				}
			}
			
			// 根据客户编码，获取客户是否存在应收未核销金额
			if (billReceivableService.queryIsExistsReceivableAmountByCustomerCode(customerCode)) {
				resultDtoList.add(dto); // 添加
			}
		}
		LOGGER.info("获得客户存在应收账款的代收款客户信息 结束.");
		return resultDtoList;
	}

	/**
	 * 营业部审核代收货款.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @throws SettlementException the settlement exception
	 * @author dp-zengbinwen
	 * @date 2012-10-30 下午4:24:45
	 */
	@Transactional
	public void auditBillPayCOD(List<String> entityIds, CurrentInfo currentInfo)
			throws SettlementException {

		LOGGER.info("营业部审核代收货款Start service,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果实体为空，则抛出异常
		if (entityIds == null || entityIds.size() == 0) {
			throw new SettlementException("代收货款实体为空，不能进行审核代收货款操作");
		}

		List<CODEntity> codList = codCommonService.queryByIds(entityIds);
		String status = null;

		// 循环处理
		for (CODEntity entity : codList) {

			// 根据ID查询代收货款
			//entity = codCommonService.queryById(entityId);
			if (entity == null) {
				throw new SettlementException("找不到代收货款实体");
			}
			
			/**
			 * @author 218392 zhangyongxue 
			 * @date 2015-12-30 10:47:30
			 * 1、对于资金安全控制组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁”
			 * 2、对于资金复核小组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
			 */
			String waybillNo = entity.getWaybillNo();
			if(StringUtils.isNotEmpty(waybillNo)){
				CodAuditDto dto = new CodAuditDto();
				List<String> waybillNoList = new ArrayList<String>();
				waybillNoList.add(waybillNo);
				dto.setWaybillNos(waybillNoList);
				//代收货款审核灰度   353654 ------------------------ start
	            String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
	            	arrayList.add(waybillNo);
	            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".auditBillPayCOD",
	            			SettlementConstants.TYPE_FOSS);
	            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();	
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
	            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	            	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
					if(codAuditEntityList != null && codAuditEntityList.size() > 0){
						CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
						if(codAuditEntity != null){
							//如果为资金部锁定
							if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
							}
							if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
							}
						}
					}else{
						LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
					}
	            }
	            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
	    				CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
	    				requestDto.setWaybillNo(waybillNoList);
	    				CUBCCodAuditResultDto resultDto = null;
						try {
							resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
						} catch (Exception e) {
							LOGGER.error("调用CUBC代收货款审核接口连接异常...");
		    				throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
						}
	    				if(resultDto != null){
	    					if(StringUtils.isNotBlank(resultDto.getMeg())){
	    						LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
	    						throw new SettlementException(resultDto.getMeg());
	    					}
	    					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
	    					if(CollectionUtils.isNotEmpty(auditList)){
	    						com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
	    						if(codAuditDto1 != null){
	    							//如果为资金部锁定
	    							if(StringUtils.equalsIgnoreCase("FL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
	    							}
	    							if(StringUtils.equals("RL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
	    							}
	    						}
	    					}else{
	    						LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
	    					}
	    				}
	            }
	            //代收货款审核灰度   353654 ------------------------ start
			}
			
			// 判断代收货款状态是否能审核代收货款
			status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__SHIPPER_FREEZE.equals(status)) {
				throw new SettlementException("代收货款非营业部冻结，不能进行审核代收货款操作");
			}
			
			// 判断银行账号不存在 ，抛出异常
			String account = entity.getAccountNo();

			String customerCode = entity.getCustomerCode();
			if (!isAccountExist(account, customerCode)) {
				throw new SettlementException("客户对应的银行账号不存在");
			}

			// 设置代收货款状态、审核时间、审核人
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__CASHIER_APPROVE);
			entity.setOrgAuditTime(new Date());
			entity.setOrgAuditUserName(currentInfo.getEmpName());
			entity.setOrgAuditUserCode(currentInfo.getEmpCode());

			// 更新代收货款状态
			codCommonService.updateOrgAuditStatus(entity, currentInfo);

		}

		LOGGER.info("营业部审核代收货款End service.");
	}

	/**
	 * 代收货款经理审核同意.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @param password the password
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-10-30 下午5:01:45
	 */
	@Transactional
	public void agreeChangeBankAccounts(List<String> entityIds,
			CurrentInfo currentInfo, String password)
			throws SettlementException {

		LOGGER.info("Start service,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果实体为空，则抛出异常
		if (entityIds == null || entityIds.size() == 0) {
			throw new SettlementException("代收货款实体为空，不能进行经理审核代收货款操作");
		}

		// 判断用户名、密码是否正确
		boolean valid = userService.queryUserForValidationCorrectness(
				currentInfo.getEmpCode(), password);
		if (!valid) {
			throw new SettlementException("用户名密码不正确");
		}

		List<CODEntity> codList = codCommonService.queryByIds(entityIds);
		String status = null;

		// 循环处理
		for (CODEntity entity : codList) {

			// 根据ID查询代收货款
			//entity = codCommonService.queryById(entityId);
			if (entity == null) {
				throw new SettlementException("找不到代收货款实体");
			}

			/**
			 * @author 218392 zhangyongxue 
			 * @date 2015-12-30 11:34:30
			 * 1、对于资金安全控制组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁”
			 * 2、对于资金复核小组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
			 */
			String waybillNo = entity.getWaybillNo();
			if(StringUtils.isNotEmpty(waybillNo)){
				CodAuditDto dto = new CodAuditDto();
				List<String> waybillNoList = new ArrayList<String>();
				waybillNoList.add(waybillNo);
				dto.setWaybillNos(waybillNoList);
				//代收货款审核灰度   353654 ------------------------ start
	            String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
	            	arrayList.add(waybillNo);
	            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".agreeChangeBankAccounts",
	            			SettlementConstants.TYPE_FOSS);
	            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();	
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
	            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	            	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
					if(codAuditEntityList != null && codAuditEntityList.size() > 0){
						CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
						if(codAuditEntity != null){
							//如果为资金部锁定
							if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
							}
							if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
							}
						}
					}else{
						LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
					}
	            }
	            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
	    				CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
	    				requestDto.setWaybillNo(waybillNoList);
	    				CUBCCodAuditResultDto resultDto = null;
						try {
							resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
						} catch (Exception e) {
							LOGGER.error("调用CUBC代收货款审核接口连接异常...");
		    				throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
						}
	    				if(resultDto != null){
	    					if(StringUtils.isNotBlank(resultDto.getMeg())){
	    						LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
	    						throw new SettlementException(resultDto.getMeg());
	    					}
	    					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
	    					if(CollectionUtils.isNotEmpty(auditList)){
	    						com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
	    						if(codAuditDto1 != null){
	    							//如果为资金部锁定
	    							if(StringUtils.equalsIgnoreCase("FL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
	    							}
	    							if(StringUtils.equals("RL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
	    							}
	    						}
	    					}else{
	    						LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
	    					}
	    				}
	            }
	            //调用灰度接口根据返回值判断调用FOSS还是CUBC 353654-----------end
			}
			
			
			// 判断代收货款状态
			status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__CASHIER_APPROVE
					.equals(status)) {
				throw new SettlementException("代收货款状态不为收银员审核,不能审核代收货款");
			}

			//判断运单是否签收
			BillPayableEntity billPayable = codCommonService
					.getBillPayableEntity(entity);
			boolean isSigned = billPayable.getSignDate() != null;

			// 如果已签收 ，更新代收货款状态为未退款
			if (isSigned) {
				entity.setStatus(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
			}

			// 未签收 ，根据代收货款类型确定代收货款状态
			else {
				// 得到代收货款类型
				String codType = entity.getCodType();

				// 如果为即日退、三日退，更新为未退款
				if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY
						.equals(codType)
						|| SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY
								.equals(codType)) {
					entity.setStatus(SettlementDictionaryConstants.COD__STATUS__NOT_RETURN);
				}

				// 如果为审核退，更新为待审核
				else if (SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE
						.equals(codType)) {
					entity.setStatus(SettlementDictionaryConstants.COD__STATUS__APPROVING);
				}

				else {
					throw new SettlementException("未识别的代收货款类型");
				}
			}

			// 审核人、审核时间
			entity.setOrgManagerAuditName(currentInfo.getEmpName());
			entity.setOrgManagerAuditCode(currentInfo.getEmpCode());
			entity.setOrgManagerAuditTime(new Date());

			// 更新代收货款状态
			codCommonService.updateManagerAuditStatus(entity, currentInfo);

		}

		LOGGER.info("End service.");
	}

	/**
	 * 代收货款经理审核拒绝.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @param denyDesc the deny desc
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-10-30 下午5:04:50
	 */
	@Transactional
	public void denyChangeBankAccounts(List<String> entityIds,
			CurrentInfo currentInfo, String denyDesc)
			throws SettlementException {

		LOGGER.info("Start service,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果实体为空，则抛出异常
		if (entityIds == null || entityIds.size() == 0) {
			throw new SettlementException("代收货款实体为空，不能进行经理审核代收货款操作");
		}

		List<CODEntity> codList = codCommonService.queryByIds(entityIds);
		String status = null;

		// 循环处理
		for (CODEntity entity : codList) {

			// 根据ID查询代收货款
			//entity = codCommonService.queryById(entityId);
			if (entity == null) {
				throw new SettlementException("找不到代收货款实体");
			}

			/**
			 * @author 218392 zhangyongxue 
			 * @date 2015-12-30 10:47:30
			 * 1、对于资金安全控制组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁”
			 * 2、对于资金复核小组锁定后的单据：
			 * 一线出发部门收银员无法对此单据进行冻结、修改账号、审核操作。
			 * 当进行以上操作时则提示“此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁”
			 */
			String waybillNo = entity.getWaybillNo();
			if(StringUtils.isNotEmpty(waybillNo)){
				CodAuditDto dto = new CodAuditDto();
				List<String> waybillNoList = new ArrayList<String>();
				waybillNoList.add(waybillNo);
				dto.setWaybillNos(waybillNoList);
				//代收货款审核灰度   353654 ------------------------ start
	            String vestSystemCode = null;
	            try {
	            	ArrayList<String> arrayList = new ArrayList<String>();
	            	arrayList.add(waybillNo);
	            	CubcGrayRequestEntity requestDto = new CubcGrayRequestEntity(arrayList,
	            			SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,SERVICE_CODE+".denyChangeBankAccounts",
	            			SettlementConstants.TYPE_FOSS);
	            	CubcGrayResponseEntity response = (CubcGrayResponseEntity)HttpClientUtils.postMethod(requestDto, new CubcGrayResponseEntity(),grayByWaybillNoUrl);
	            	List<VestBatchResult> list = response.getVestBatchResult();
	            	vestSystemCode = list.get(0).getVestSystemCode();	
				} catch (Exception e) {
					LOGGER.info("灰度分流失败,"+"运单号："+waybillNo);
					throw new CUBCGrayException("系统繁忙,灰度分流失败,请稍后重试！");
				}
	            if(SettlementConstants.TYPE_FOSS.equals(vestSystemCode)){
	            	List<CodAuditEntity> codAuditEntityList = codAuditService.queryCodAuditByCondition(dto);//根据单号查询
					if(codAuditEntityList != null && codAuditEntityList.size() > 0){
						CodAuditEntity codAuditEntity = codAuditEntityList.get(0);//获取第一条
						if(codAuditEntity != null){
							//如果为资金部锁定
							if(StringUtils.equalsIgnoreCase("FL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
							}
							if(StringUtils.equals("RL", codAuditEntity.getLockStatus())){
								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
							}
						}
					}else{
						LOGGER.info("单号："+waybillNo+"没有进入代收货款支付审核！");
					}
	            }
	            if(SettlementConstants.TYPE_CUBC.equals(vestSystemCode)){
	    				CUBCCodAuditRequestDto requestDto = new CUBCCodAuditRequestDto();
	    				requestDto.setWaybillNo(waybillNoList);
	    				CUBCCodAuditResultDto resultDto = null;
						try {
							resultDto = (CUBCCodAuditResultDto)HttpClientUtils.postMethod(requestDto,new CUBCCodAuditResultDto(),queryCodAuditListUrl);
						} catch (Exception e) {
							LOGGER.error("调用CUBC代收货款审核接口连接异常...");
		    				throw new SettlementException("服务器正忙,CUBC代收货款审核失败,请稍后重试");
						}
	    				if(resultDto != null){
	    					if(StringUtils.isNotBlank(resultDto.getMeg())){
	    						LOGGER.error("调用CUBC代收货款审核接口失败，异常信息：" + resultDto.getMeg());
	    						throw new SettlementException(resultDto.getMeg());
	    					}
	    					List<com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto> auditList = resultDto.getCodAuditList();
	    					if(CollectionUtils.isNotEmpty(auditList)){
	    						com.deppon.foss.module.settlement.common.api.shared.dto.CodAuditDto codAuditDto1 = auditList.get(0);
	    						if(codAuditDto1 != null){
	    							//如果为资金部锁定
	    							if(StringUtils.equalsIgnoreCase("FL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金部锁定，如需操作，请联系资金结算组进行解锁");
	    							}
	    							if(StringUtils.equals("RL", codAuditDto1.getLockStatus())){
	    								throw new SettlementException("此单据已被资金复核组锁定，如需操作，请联系资金复核组进行解锁");
	    							}
	    						}
	    					}else{
	    						LOGGER.info("CUBC,单号："+waybillNo+"没有进入代收货款支付审核！");
	    					}
	    				}
	            }
	            //代收货款审核灰度   353654 ------------------------ start
			}
			
			
			// 判断代收货款状态
			status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__CASHIER_APPROVE
					.equals(status)) {
				throw new SettlementException("代收货款状态不为收银员审核,不能审核代收货款");
			}

			// 代收货款状态、审核人、审核时间、退回原因
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__APPROVING);
			entity.setOrgManagerAuditName(currentInfo.getEmpName());
			entity.setOrgManagerAuditCode(currentInfo.getEmpCode());
			entity.setOrgManagerAuditTime(new Date());
			entity.setRefundNotes(denyDesc);

			// 更新代收货款状态
			codCommonService.updateManagerBackStatus(entity, currentInfo);

		}

		LOGGER.info("End service.");
	}
	
	/**
	 * 根据银行编码查询是否支持即日退.
	 *
	 * @param entity the entity
	 * @return true, if successful
	 * @author guxinhua
	 * @date 2013-1-30 上午10:47:56
	 */
	public boolean checkBankIntraDayTypeByBankEntity(BankEntity entity){
		
		if(null == entity){
			throw new SettlementException("参数银行实体为空");
		}
		if(StringUtil.isBlank(entity.getCode())){
		    throw new BankException("参数银行编码不允许为空！");
		}
		
		BankEntity bank = null;
		try {
			bank = bankService.queryBankInfoByCode(entity.getCode());
		} catch (Exception e) {
			throw new SettlementException("综合提供根据银行编码查询银行信息异常");
		}
		
		if(null == bank){
			throw new SettlementException("查询银行实体为空");
		}
		// 银行实体
		if (StringUtils.equals(bank.getIntraDayType(),FossConstants.YES)){
			// 支持即日退
			return true;
		} else if (StringUtils.equals(bank.getIntraDayType(),FossConstants.NO)){
			// 不支持即日退
			return false;
		} else {
			throw new SettlementException("查询银行的是否支持即日退为空");
		}
	}
	
	/**
	 * Sets the cod common service.
	 *
	 * @param codCommonService the new cod common service
	 */
	public void setCodCommonService(ICodCommonService codCommonService) {
		this.codCommonService = codCommonService;
	}

	/**
	 * Sets the user service.
	 *
	 * @param userService the new user service
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * Sets the bank service.
	 *
	 * @param bankService the new bank service
	 */
	public void setBankService(IBankService bankService) {
		this.bankService = bankService;
	}

	/**
	 * Sets the nonfixed cus account service.
	 *
	 * @param nonfixedCusAccountService the new nonfixed cus account service
	 */
	public void setNonfixedCusAccountService(
			INonfixedCusAccountService nonfixedCusAccountService) {
		this.nonfixedCusAccountService = nonfixedCusAccountService;
	}

	/**
	 * @param billReceivableService
	 */
	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	/**
	 * @param cusAccountService
	 */
	public void setCusAccountService(ICusAccountService cusAccountService) {
		this.cusAccountService = cusAccountService;
	}

	public void setCodAuditService(ICodAuditService codAuditService) {
		this.codAuditService = codAuditService;
	}
	
}
