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
 * PROJECT NAME	: stl-writeoff
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/writeoff/server/action/PayStatementManagerAction.java.java
 * 
 * FILE NAME        	: PayStatementManagerAction.java
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
 	1.1	相关业务用例
			BUC_FOSS_4.7.20.10_100 
			 制作对账单
*
*
*
*
*
*
*
*
*
*
*
*			 
	1.2	用例描述
    		用户可以通过
    			对账单类型、
    			客户信息、
    			代理信息、
    			对账单制作日期、
    			对账单确认状态、
    			对账单结算状态、
    			对账单号、
    			明细来源单号
    		查询出对账单及对账单明细数据，
    		可以选择需要确认的对账单
    		进行确认对账单的操作，
    		并可以对已确认的对账单进行
    			反确认、
    			还款、
    			付款、
    			核销操作，
    		还可以对所有对账单进行
    			打印回执、
    			查看明细的操作，
    		对明细数据进行
    			新增、
    			删除、
    			标记、
    			反标记的操作，
    		也可以打印或导出对账单及明细数据。
 *
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   
 *   		
	1.3	用例条件
		条件类型	
		描述	
		引用
		系统用例
		*
		*
		*
		前置条件
			1、	与客户对账前，
				成功生成对账单	
			2、	制作对账单系统用例
			*
			*
			*
		后置条件	
			1、	打印对账单确认回执
			2、	客户还款
			3、	给客户付款
			4、	应收单和应付单核销
			5、	预收单和应收单核销
			6、	预付单和应付单核销
			7、	对账单明细被标记为有争议单据
			8、	对账单明细被删除	
*
*
*
*
*
*
*
*
*
*
*
*
*
	1.4	操作用户角色
			操作用户	
				描述
			*
			收银员	
				能够查询、
				修改客户、
				代理对账单信息
			呼叫中心职员	
				能够查询、
				修改客户、
				代理对账单信息
			空运对账员	
				能够查询、
				修改代理对账单信息
			偏线会计
				能够查询、
				修改代理货款对账单信息 
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*				
1.5	界面要求
		1.5.1	表现方式
				Web
				*
				*
				*
		1.5.2	界面原型
				按客户查询
				对账单明细
				未完全核销明细
				单据核销明细
 				*
 				*
 				*
		1.5.3	界面描述
		按客户查询界面
			页面初始化组件描述：
				1、对账单的制作日期
					始日期默认为前一天日期，
					结束时间默认为当前日期
			页面输入组件描述：
				1、用户在选择客户信息或代理信息时，
					能够自动弹出客户
					或代理查询对话框，
					能够让用户选择；
					支持用户同时选择
					多个客户或代理信息
 				2、用户可以通过单据子类型的
 					下拉列表选择对账单类型
 					*
 					*
 					*
		按对账单号查询界面
			页面输入组件描述：
				1、	用户可以输入
					一个或者多个对账单号
					进行查询
					*
					*
					*
		按明细单号查询界面
			页面输入组件描述：
				1、	用户可以输入
					一个或者多个对账单明细单号
					进行查询
			页面表格组件功能描述：
				查询页面表格
				1、用户可以自定义显示
					结果集中的数据列
				2、用户能够多选对账单信息进行
					确认、
					还款、
					核销操作
				3、对账单信息结果集有操作列，
					操作列显示按钮内容参见
					业务规则SR1 
				4、结果集以竖向滚动条显示
				5、页面初始化时，
					页面结果集表格组件部分不显示
					明细页面表格 
					1、对账单明细信息结果集提供多选框，
						能够让用户进行
						批量删除、
						标记操作
					2、用户能够自定义显示
						应收单、
						应付单、
						预收单、
						预付单
					     信息结果集的显示列
					3、结果集以竖向滚动条显示
						未完全核销明细信息
						1、用户可以批量选择明细信息进行付款操作
						*
						*
						*
		单据子类型以下拉列表形式显示，
		内容为：
			客户对账单
			代理对账单
		单据子类型的内容在数据字典中维护；
		对账单状态以下拉列表形式显示，
		内容为：
			全部
			已确认
			未确认
		对账单结账状态以下拉列表形式显示，内容为：
			全部
			已结清
			未结清
		修改对账单界面提供以下按钮：
			查询
			重置
			确认对账单
			还款
			付款
			导出
			打印
*
*
*
*
*
*
*
*
*
*
*
*
*
	1.6	操作步骤
		1.6.1	查询对账单		
			*
			序号	基本步骤	相关数据	补充步骤
			1	页面初始化		
				1、	单据子类型选择全部、
					对账单状态选择全部、
					结帐状态选择全部
				2、	系统默认选择按客户查询界面
			2	当按客户查询时，
				选择单据子类型	
					对账单据子类型信息	
					1、	当用户选择客户对账单时，
						界面显示客户名称输入框；
						当用户选择代理对账单时，
						界面显示代理名称输入框
			3	选择客户名称或编码、代理名称或编码	
					客户信息、代理信息	
						1、	用户选择客户信息时
							系统自动调用综合管理
							查询客户信息接口
							查询客户信息
							（弹出公共客户信息查询框）
						2、	用户选择信息时时
							系统自动调用综合管理
							查询代理商信息接口
							查询出代理商信息
							（弹出公共代理信息查询框）
			4	选择开始日期、结束日期；
				选择对账单确认状态和结账状态		
					1、业务规则参见
						业务规则SR2
			5	当按对账单号查询时，
				输入一个或多个对账单号		
					1、	校验对账单号个数
						是否超过最大值
					2、	扩展事件请参见
						扩展事件5a
					3、	业务规则请参见
						业务规则SR3
			6	当按明细来源单号查询时，
				输入一个或多个明细来源单号		
					1、	校验对账单号个数
						是否超过最大值
					2、	扩展事件请参见
						扩展事件6a
					3、	业务规则请参见
						业务规则SR3
			7	点击“查询“	对账单信息	
					1、	扩展事件请参见
						扩展事件7a、7b
					2、	业务规则参见
						业务规则SR7
						*
						*
						*
		1.6.2	确认对账单
			*
			序号	基本步骤	相关数据	补充步骤
			8	选择一条或者多条未确认的对账单，
				点击“确认对账单“按钮	
					对账单信息、
					应收单信息、
					应付单信息、
					预收单信息、
					预付单信息	
					1、	根据对账单号检查该对账单所关联的
						对账单明细数据是否有未受理的更改单
						或是否已经发生了更改
					2、	如果没有发生更改且没有未受理的的更改单，
						则修改对账单确认状态为已确认
					3、	根据对账单的客户信息
						获取客户邮箱地址，
						并自动给客户发送一封电子邮件，
						邮件内容从基础资料获取
					4、	保存单据信息、确认人、确认时间
						到操作日志表中
					5、	扩展事件请参见
						扩展事件8a、8b
					6、	业务规则请参见
						业务规则SR4
						*
						*
						*
		1.6.3	反确认对账单
			*
			序号	基本步骤	相关数据	补充步骤
			9	如果需要对已确认过的对账单反确认，
				点击操作按钮“反确认“	对账单信息	
					1、	修改对账单确认状态
					2、	保存单据信息、反确认人、反确认时间
						到操作日志表中
					2、扩展事件请参见
						扩展事件9a
					3、业务规则请参见
						业务规则SR5
						*
						*
						*
		1.6.4	查看对账单明细
			*
			序号	基本步骤	相关数据	补充步骤
			10	如果需要查看对账单明细数据，
				则在对账单列表的操作列中
				 点击“明细“按钮	
				对账单明细信息	
					1、	查询对账单明细信息
					2、	跳转到对账单明细界面显示
					3、	对账单明细界面操作按钮显示规则
						参见SR13
						*
						*
						*
		1.6.5	查看单据核销记录
			*
			序号	基本步骤	相关数据	补充步骤
			11	点击“单据核销明细”按钮	
				核销单信息	
					1、	根据已完全核销的信息集合
						查询出核销单明细信息
					2、	弹出单据核销明细界面显示
					3、	扩展事件
						参见11a
						*
						*
						*
		1.6.6	删除对账单明细
			*
			序号	基本步骤	相关数据	补充步骤
			12	如果需要删除对账单明细信息，
				点击操作按钮“删除“	
					应收单信息、
					应付单信息、
					预收单信息、
					预付单信息、
					对账单信息	
					1、	将对账单明细标记为删除
					2、	更新对账单信息，
						参见业务规则SR9
					3、	将被删除单据的对账单号清空
					4、	被删除明细自动进入到
						往期明细列表中
					5、	扩展事件请参见
						扩展事件12a
					6、	业务规则请参见
						业务规则SR6
						*
						*
						*
						*
		1.6.7	标记\反标记对账单明细
			*
			序号	基本步骤	相关数据	补充步骤
			13	对有争议的单据进行标记操作，
				也可以对被标记的单据
				进行反标记操作	
					对账单信息、
					对账单明细信息	
						1、	将对账单明细标记状态改为是或否
						2、	更新对账单信息，
							参见业务规则SR9
						3、	如果对账单处于已确认状态，
							反标记时，
							将对账单明细标记为删除状态，
							同时将对账单明细对对应的
							原财务单据的对账单号清空
						4、	扩展事件
							参见13a
							*
							*
							*
							*
		1.6.8	还款
			*
			序号	基本步骤	相关数据	补充步骤
			14	选择多条对账单
				点击批量“还款“按钮
				或点击对账单操作列的单条“还款”按钮	
				1、	判断对账单是否被确认
					如果未被确认则
					参见1.6.2的补充步骤
				2、	自动核销对账单明细中的明细单据，
						生成核销单、
						核销单明细信息，
					修改与账单明细信息相关联的
						应收单、
						应付单、
						预收单、
						预付单
					的已核销和未核销金额
					（对账单明细表中的信息不变，修改原始单据信息）
				3、	核销单以及核销单明细的生成规则
					参见SR8
				4、	跳转到新增还款单界面，
					并将该对账单信息传递给新增还款单用例
				5、	扩展事件
					参见14a、14b
				6、	业务规则
					参见SR10
					*
					*
					*
					*
		1.6.9	核销
			*
			序号	基本步骤	相关数据	补充步骤
			15	当本期发生金额为0时，
				用户点击操作按钮“核销”	
				1、判断对账单是否被确认
					如果未被确认则参见1.6.2的补充步骤
				3、	自动核销对账单明细中的明细单据，
					生成核销单、核销单明细信息，
					修改与账单明细信息相关联的
						应收单、
						应付单、
						预收单、
						预付单
					的已核销和未核销金额
					（对账单明细表中的信息不变，修改原始单据信息）
				4、	核销单以及核销单明细的生成规则
					参见SR8
				5、	业务规则
					参见SR10
				6	当本期发生金额小于0时，
					用户点击操作按钮“核销”
					或批量选择对账单进行核销		
						1、	判断对账单是否被确认
							如果未被确认则参见1.6.2的补充步骤
						2、	自动核销对账单明细中的明细单据，
							生成核销单、核销单明细信息，
							修改与账单明细信息相关联的
								应收单、
								应付单、
								预收单、
								预付单
							的已核销和未核销金额
							（对账单明细表中的信息不变，修改原始单据信息）
						3、	核销单以及核销单明细的生成规则
							参见SR8
						4、	弹出未被完全核销的明细信息
						5、	扩展事件
							参见16a、16b
						6、	业务规则
							参见SR10
							*
							*
							*
							*
		1.6.10	付款
			*
			序号	基本步骤	相关数据	补充步骤
			17	用户在弹出框中选择
				需要被付款的明细单据，
				点击“付款”按钮	
				对账单明细信息
				1、	自动跳转到新增付款单页面，
					并将用户选择的对账单明细信息传递过去
					*
					*
					*
		1.6.11	打印回执
			*
			序号	基本步骤	相关数据	补充步骤
			18	如果用户需要打印对账单确认回执，
				点击操作按钮“打印回执“	
				对账单信息
				1、调用打印对账单确认回执用例
				*
				*
				*
		1.6.12	新增对账单明细
			*
			序号	基本步骤	相关数据	补充步骤
			19	点击“新增对账单明细”按钮		
				1、	调用新增对账单明细用例，新增对账单明细信息
				2、	跳转到新增对账单明细界面
			20、在对账单明细界面直接点击操作列“新增”按钮			
				1、	将新增信息新增到本期对账单明细中显示
				2、	在往期明细中去除新增的明细信息
				3、	修改对账单的期初发生金额和本期发生金额信息，
					具体规则参见12
						*
						*
						*
						*
		1.6.13	导出
			*
			序号	基本步骤	相关数据	补充步骤
			21	点击“导出”按钮	
					对账单信息、
					对账单明细信息	
					1、调用导出数据接口，
						导出相对应的对账单、
						对账单明细及核销单明细信息
						（按用户选择的字段导出）,
						导出文件为PDF格式
						*
						*
						*
		1.6.14	打印
			*
			序号	基本步骤	相关数据	补充步骤
			22	点击“打印”按钮	
					对账单信息、
					对账单明细信息	
					1、	调用打印数据接口，
						打印相对应的对账单、
						对账单明细数据及核销单明细信息
						（按用户选择的字段打印）
					2、	打印规则
					参见SR11
					*
					*
					*
					*

		扩展事件	相关数据	备注
			5a	当用户输入的对账单号个数
				超过最大值时，
				点击查询时，
				提示总数超过最大值信息		
				提示信息显示在输入框右下角，
				以红色字体显示，
				提示内容：
				“输入的单号个数超过最大值，请重新输入“
			6a	当用户输入的运单号个数
				超过最大值时，
				点击查询时，
				提示总数超过最大值信息		
				提示信息显示在输入框右下角，
				以红色字体显示，
				提示内容：
				“输入的单号个数超过最大值，请重新输入“
			7a	当筛选不到结果时,
				提示没有符合条件的单据		
				提示内容以弹出框形式提示，
				提示内容：
				“没有符合条件的单据”
			7b	当用户取消查询条件时，
				点击“重置“按钮		
				界面查询条件值恢复页面初始化状态
			8a	如果所选择的对账单所关联的
					应收单、
					应付单、
					预收单、
					预付单
				信息发生了更改，
				则修改对账单信息的所有金额信息，
				并提示对账单已发生更改信息，
				并中断确认对账单操作		
				提示以警告弹出框形式提示，
				提示内容：
				“所选择的对账单信息包含已发生更改记录“，
				发生更改的对账单信息背景显示为红色
			8b	如果所选择的对账单所关联的
					应收单、
					应付单
				存在关联的未受理更改单，
				则提示错误信息，
				并中断确认操作 		
				提示信息以弹出框形式提示，
				提示内容为：
				“所选择的对账单明细关联有未受理的更改单信息，
				不能确认！”
			9a	如果所选择的对账单已经进行过还款或付款或核销处操作，
				则提示错误信息		
				提示格式以弹出格式提示，
				提示内容：
				“该对账单已经进行过还款或付款，
				不能进行反确认操作“
			11a	如果该对账单不包括已完全核销的明细单据，
				则提示信息		
				提示格式以弹出格式提示，
				提示内容：
				“该对账单没有包含已完全核销的单据信息！“
			12a	如果所选择的对账单明细所对应的对账单已处于确认状态，
				则提示删除失败信息		
				提示格式以弹出形式提示，
				提示内容：
				“已经确认的对账单明细不能删除！”
			13a	如果所选择的对账单明细所对应的对账单已处于确认状态，
				则提示标记失败信息		
				提示格式以弹出形式提示，
				提示内容：
				“已经确认的对账单明细不能被标记争议单据！”
			14a	如果所选择的对账单中本期发生金额有小于0的对账单，
				则中止操作，
				并提示错误信息		
				提示信息以弹出形式提示，
				提示内容：
				“所选择的单据包含应付对账单，
				不能批量还款”
			14b	如果所选择的对账单中有已结清的对账单，
				则中止操作，
				并提示错误信息		
				提示信息以弹出形式提示，
				提示内容：
				“所选择的单据包含已结清的对账单不能进行还款操作”
			16a	如果所选择的对账单包含
				本期发生金额大于0的对账单信息，
				则中止操作，
				并提示错误信息		
				提示信息以弹出形式提示，
				提示内容：
				“所选择的单据包含应收对账单，
				不能核销”
			16b	如果所选择的对账单中有已结清的对账单，
				则中止操作，
				并提示错误信息		
				提示信息以弹出形式提示，
				提示内容：
				“所选择的单据包含已结清的对账单不能进行核销操作”
*
*
*
*
*
*
*
*
*
*
*
*
*
*
	1.7	业务规则
			序号	描述
			SR1	1、	当对账单的状态已确认、
					未还金额大于0、
					结账次数等于0时，
					操作列按钮为：
					打印回执、反确认、还款、明细
				2、	当对账单的状态已确认、
					未还金额大于0、
					结账次数大于0时，
					操作列按钮为：
					打印回执、还款、明细
				3、	当对账单的状态已确认、
					未还金额等于0、
					结账次数等于0时，
					操作列按钮为：
					打印回执、反确认、核销、明细
				4、	当对账单的状态已确认、
					未还金额等于0、
					结账次数大于0时，
					操作列按钮为：
					打印回执、明细
				5、	当对账单的状态已确认、
					未还金额小于0、
					结账次数等于0时，
					操作列按钮为：
					打印回执、反确认、核销、明细
				6、	当对账单的状态已确认、
					未还金额小于0、
					结账次数大于0时，
					操作列按钮为：
					打印回执、明细、核销
				7、	当对账单的状态未确认、
					未还金额大于0、
					结账次数等于0时，
					操作列按钮为：
					打印回执、确认、还款、明细
				8、	当对账单的状态未确认、
					未还金额等于0、
					结账次数等于0时，
					操作列按钮为：
					打印回执、确认、核销、明细
				9、	当对账单的状态未确认、
					未还金额小于0、
					结账次数等于0时，
					操作列按钮为：
					打印回执、确认、核销、明细
			SR2	1、开始时间和结束时间相差
					不能大于一个月	
				2、对账单制作的开始和结束日期
					不能为空
			SR3	1、	单号的个数不能超过10个
				2、	单号之间用半角状态逗号分隔
				3、	单号不能为空
			SR4	1、确认对账单时，
					选择的条数必须大于0
				2、所选择的对账单的状态
					必须为未确认状态
				3、对账单明细所关联的运单存在未受理更改单时
					不能确认对账单
				4、	对账单明细中的应收单
					如果被网厅锁定，
					则不允许确认
			SR5	1、	未确认的对账单
					不能进行反确认操作
				2、	已还款或已付款的对账单
					不能进行反确认操作
			SR6	1、已确认的对账单
					不能删除对账单明细
			SR7	1、	当前登录用户
					只能查询所属部门制作的对账单信息。
				2、	对账单的锁定时间
					不能小于当前时间
			SR8	1、	核销单明细信息的核销单号
					系统自动生成，
					     公司信息、
					     部门信息、
					     客户信息
					从对账单信息中获取，
					核销金额为两条单据相互核销时
					金额小的一方的金额信息，
					默认为有效且非红单单据，
					核销日期、会计日期为
					系统当前日期
				2、	对账单明细的核销顺序为
					预收冲应收、
					应收冲应付、
					还款冲应收、
					预付冲应付
				3、核销顺序按照业务发生时间的
					先后顺序进行核销
				4、两个单据核销时，
					按照金额小的一方核销，
					未完全核销的单据
					自动与下一条单据核销
				5、对生成的核销单明细信息
					统计生成核销单信息，
					核销单单号、
					公司信息、
					部门信息、
					客户信息
					从核销单明细信息中获取；
					默认为有效且非红单单据，
					核销金额为核销单明细金额的总和，
					核销日期、会计日期
					为系统当前日期
			SR9	1、	如果被标记或删除的是
					应收单明细，
					则对账单的期初应收金额增加，
					期初发生金额增加，
					本期应收金额减少，
					本期发生金额减少
				2、	如果被标记或删除的是
					应付单明细，
					则对账单的期初应付金额增加，
					期初发生金额减少，
					本期应付金额减少，
					本期发生金额增加
				3、	如果被标记或删除的是
					预收单明细，
					则对账单的期初预收金额增加，
					期初发生金额减少，
					本期预收金额减少，
					本期发生金额增加
				4、	如果被标记或删除的是
					预付单明细，
					则对账单的期初预付金额增加，
					期初发生金额增加，
					本期预付金额减少，
					本期发生金额减少
				5、	已确认的对账单
					不能进行标记、
					删除操作
				6、	如果对账单为未确认状态，
				          则反标记的规则和标记的规则相反，
				7、	如果对账单处于已确认状态，
					反标记时，
					对账单的所有金额不发生改变,
					并将该明细从对账单中剔除
			SR10 1、	还款状态为已结清的对账单
					不能进行还款和核销操作
				2、	对账单明细所关联的运单存在未受理更改单时
					不能还款
			SR11	1、	先打印对账单信息，
					字段和界面显示完全一样，
					显示在打印纸张的开头部分
				2、	然后打印对账单明细单据，
					字段显示顺序及数据排序和界面查询的保持一致
				3、	有争议的单据明细放在最后打印，
					在打印纸张的末尾处
			SR12	1、	如果新增的是应收单明细，
					则对账单的期初应收金额减少，
					期初发生金额减少，
					本期应收金额增加，
					本期发生金额增加
				2、	如果新增的是应付单明细，
					则对账单的期初应付金额减少，
					期初发生金额增加，
					本期应付金额增加，
					本期发生金额减少
				3、	如果新增的是预收单明细，
					则对账单的期初预收金额减少，
					期初发生金额增加，
					本期预收金额增加，
					本期发生金额减少
				4、	如果新增的是预付单明细，
					则对账单的期初预付金额减少，
					期初发生金额减少，
					本期预付金额增加，
					本期发生金额增加
				5、	已进入对账单的明细信息
					不能进行新增操作
			SR13	1、	当对账单明细期次为往期时，
					且对账单单号不为空，
					则无任何操作按钮显示
				2、	当对账单明细期次为往期时，
					且对账单单号为空时，
					操作按钮显示“添加”
				3、	当对账单明细期次为本期时，
					且不为有争议单据时，
					操作按钮显示“删除”和“标记”
				4、	当对账单明细期次为本期时，
					且为有争议单据时，
					操作按钮显示“删除”和“反标记”
			Sr14	1、应付单、预收单、预付单
					这三种单据进入对账单，
					若对账单未进行还款/核销操作，
					则其不能进入下期对账单；
					还款/核销后，
					这三种单据就会被释放，
					既可以进入下期对账单；
*
*
*
*
*
*
*
*
*
*
*
*
	1.8	数据元素
		1.8.1	查询信息（输入）
字段名称 	
说明 	
输入限制	输入项提示文本	
长度	
是否必填	
备注

单据子类型	
对账单类型	
以下列表形式显示	
无	
20	
是	
包括：全部、客户对账单和代理对账单

客户信息	
客户名称或编码	
文本	
无	
参照统一标准	
是	
无

代理信息	
代理名称或编码	
文本	
无	
参照统一标准	
是	
无

开始日期	
对账单生成日期	
日期	
无	
参照统一标准	
否	
无

结束日期	
对账单生成日期	
日期	
无	
参照统一标准	
否	
无

确认状态	对账单确认状态	
以下列表形式显示	
无	
10	
是	
包括：全部和已确认、未确认

结账状态	对账单的还款或付款状态	
以下列表形式显示	
无	
10	
是	
包括：全部，已结清、未结清

对账单单号	
对账单单号	
文本	请输入一个或多个对账单单号	
100	
是	
无

明细来源单号	
明细来源单号	
文本	请输入一个或多个单号	
100	
是	
无

	1.8.2	对账单信息（输出）
参见制作对账单输出元素
	1.8.3	对账单明细信息（输出）
参见制作对账单输出元素
	1.8.4	核销单信息（输出）
字段名称 	
说明 	
输入限制	输入项提示文本	
长度	
是否必填	
备注

核销单编号	
核销单号	
无	
无	
参照统一标准	
是	
无

部门编号	
客户业务发生部门编码	
无	
无	
参照统一标准	
是	
无

部门名称	
客户业务发生部门名称	
无	
无	
参照统一标准	
是	
无

客户名称	
客户名称	
无	
无	
参照统一标准	
是	
无

客户编号	
客户编号	
无	
无	
参照统一标准	
是	
无

金额
核销金额	
无	
无	
参照统一标准	
是	
无

是否有效	
有效单据标示	
无	
无	
2	
是
无

是否红单
红冲单据标示	
无	
无	
2	
是	
无

核销人编号	
核销人编号	
无	
无	
参照统一标准	
是	
无

核销人名称	
核销人名称	
无	
无	
参照统一标准	
是	
无

核销时间	
创建日期	
无	
无	
参照统一标准	
是	
无

会计日期	
创建日期	
无	
无	
参照统一标准	
是	
无

	1.8.5	核销单明细信息（输出）
字段名称 	
说明 	
输入限制	输入项提示文本	
长度
是否必填	
备注

核销单编号	
核销单编号	
无	
无	
参照统一标准	
是	
无

开始单号
参与核销的单号	
无	
无	
参照统一标准	
是	
无

目的单号	
参与核销的单号	
无	
无	
参照统一标准	
是	
无

核销类型	
生成核销单的类型	
无	
无	
参照统一标准	
是	
无

部门编号	
客户业务发生部门编码	
无	
无	
参照统一标准	
是	
无

部门名称	
客户业务发生部门名称	
无	
无	
参照统一标准	
是	
无

客户名称	
客户名称	
无	
无	
参照统一标准	
是	
无

客户编号	
客户编号	
无	
无	
参照统一标准	
是	
无

核销金额	核销金额	
无	
无	
参照统一标准	
是	
无

是否有效	
有效单据标示	
无	
无	
2	
是	
无

是否红单	
红冲单据标示	
无	
无	
2	
是	
无

核销人编号	
核销人编号	
无	
无	
参照统一标准	
是	
无

核销人名称	
核销人名称	
无	
无	
参照统一标准	
是	
无

业务日期	
核销日期	
无	
无	
参照统一标准	
是	
无

记账日期	
财务做账日期	
无	
无	
参照统一标准	
是	
无
*
*
*
*
*
*
*
*
*
*
1.9	非功能性需求
		使用量	
			2012.4，全网每月平均产生的有效应收单为
				150万，
				共6万名客户，
				预估普通客户一个月生成一次，
				所以未来每月产生的对账单数量
				为6万
		2012年全网估计用户数	
			截止2012.4.1，
			收银员总量2324名，
			呼叫中心职员总量174名，
			空运对账员总量10名，
			会计698名，
			所有用户合计3206名
		响应要求
			（如果与全系统要求 不一致的话）	
				查询、
				删除、
				标记、
				还款、
				付款
				3秒完成；
				核销操作
				10秒完成
		使用时间段	
			正常白天上班时间
			（8:00-17：30）
		高峰使用时间段	
			每月的下旬
*
*
*
*
*
*
*
*
*
	1.10	接口描述：
	接口名称 	对方系统（外部系统或内部其他模块）	接口描述
	查询代理商信息接口	FOSS-综合管理子系统	查询代理商信息
	查询客户信息接口	FOSS-综合管理子系统	查询客户信息
*
*
*
*
*
*

 ******************************************************************************/
package com.deppon.foss.module.settlement.writeoff.server.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.common.server.util.SettlementUtil;
import com.deppon.foss.module.settlement.pay.api.shared.vo.BillPaymentVo;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IPartnerPayStatementManagerService;
import com.deppon.foss.module.settlement.writeoff.api.server.service.IStatementModifyService;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementDEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.domain.PartnerPayStatementEntity;
import com.deppon.foss.module.settlement.writeoff.api.shared.dto.PartnerPayStatementDto;
import com.deppon.foss.module.settlement.writeoff.api.shared.vo.PartnerPayStatementVo;
import com.deppon.foss.util.DateUtils;

/**
 * 付款对账单管理
 * 
 * @author 311396-foss-wangwenbo
 * @date 2016-02-22 上午 10:05:16
 */
public class PartnerPayStatementManagerAction extends AbstractAction {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -4106866789248575610L;
	
	/**
	 * 声明日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(PartnerPayStatementManagerAction.class);
	/**
	 * 声明导出excel的名称
	 */
	private static final String EXCELXMLNAME = "付款对账单本期明细";
	private static final String EXCELNAME = "合伙人付款对账单";
	/** 
	 * excel名称 
	 **/
	private String excelName;

	/** 
	 * excel导出流
	 **/
	private InputStream inputStream;
	
	/**
	 * 查询及结果参数
	 */
	private PartnerPayStatementVo partnerPayStatementVo;
	
	/**
	 * 注入付款单vo
	 */
	private BillPaymentVo vo;

	/**
	 * 注入statementModifyService
	 */
	private IPartnerPayStatementManagerService partnerPayStatementManagerService;
	
	
	/**
	 * 根据产品CODE找对应的名称
	 */
	IProductService  productService;
	
	/**
	 * 注入对账单接口
	 */
	IStatementModifyService  statementModifyService;
	

	/**
	 * 导出pdf的输入流
	 */
	//private InputStream pdfStream;

	/**
	 * 导出pdf名称
	 */
	//private String fileName;

	/**
	 * 查询付款对账单
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-22 上午 10:05:16
	 */
	@JSON
	public String queryStatement() {
		logger.info("PartnerPayStatementManagerAction.queryStatement 查询付款对账单开始 {}", new Date());
		//获取对账单查询dto
		PartnerPayStatementDto queryDto = this.partnerPayStatementVo.getPartnerPayStatementDto();
		try {
			// 制作开始日期
			if (queryDto.getPeriodBeginDate() != null) {
				queryDto.setPeriodBeginDate(DateUtils.getStartDatetime(queryDto
						.getPeriodBeginDate()));
			}
			// 给制作结束日期加1天
			if (queryDto.getPeriodEndDate() != null) {
				// 将前台结束日期进行+1操作，设置成 第2天的00:00:00
				queryDto.setPeriodEndDate(DateUtils.getStartDatetime(
						queryDto.getPeriodEndDate(), 1));
			}
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 将orgCode设置给dto
			queryDto.setOrgCode(currentInfo.getCurrentDeptCode());
			queryDto.setEmpCode(currentInfo.getEmpCode());
			// 调用service方法进行查询
			PartnerPayStatementDto dto = this.partnerPayStatementManagerService.queryStatement(queryDto,this.getStart(),this.getLimit());
			// 如果没查询到，则new一个防止前台报错
			if (dto == null) {
				// 实例化dto
				dto = new PartnerPayStatementDto();
			}
			// 将dto设置给vo，将vo返回
			this.partnerPayStatementVo.setPartnerPayStatementDto(dto);
			this.setTotalCount(dto.getTotalCount());
			logger.info("queryStatement 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	
	/**
	 * 根据对账单号查询对账单明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-23下午 4:53:19
	 */
	@JSON
	public String queryStatementDetailByNumber() {
		logger.info("PartnerPayStatementManagerAction.queryStatementDetailByNumber 查询付款对账单明细开始 {}", new Date());
		try {
			String statementBillNo = null;
			// 获取对账单号
			if(null != partnerPayStatementVo)
			{
				PartnerPayStatementDto dto = partnerPayStatementVo.getPartnerPayStatementDto();
				if(null != dto)
				{
					PartnerPayStatementEntity ppsEntity = dto.getPartnerPayStatementEntity();
					if(null != ppsEntity)
					{
						statementBillNo = ppsEntity.getStatementBillNo();
					}
					else
					{
						throw new SettlementException("获取statementBillNo对象为空");
					}
				}
				else
				{
					throw new SettlementException("获取partnerPayStatementEntity对象为空");
				}
			}
			else
			{
				throw new SettlementException("获取partnerPayStatementVo对象为空");
			}
			// 如果对账单号为空，则抛出异常
			if (StringUtil.isBlank(statementBillNo)) {
				//提示对账单号不能为空
				throw new SettlementException("对账单号不能为空！");
			}
			List<String> statementBillNos = new ArrayList<String>();
			statementBillNos.add(statementBillNo);
			// 调用service,查询对账单明细
			List<PartnerPayStatementDEntity> partnerPayStatementDList = this.partnerPayStatementManagerService.queryDetailByStatementBillNos(statementBillNos);
			// 如果明细为null
			if (CollectionUtils.isEmpty(partnerPayStatementDList)) {
				//新建明细信息并传递到前台
				partnerPayStatementDList = new ArrayList<PartnerPayStatementDEntity>();
			}
			// 将dto设置给vo，将vo返回
			this.partnerPayStatementVo.getPartnerPayStatementDto().setPartnerPayStatementDList(partnerPayStatementDList);
			logger.info("queryStatementDetailByNumber 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	
	/**
	 * 
	 * 付款
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-26 上午 10:00:11
	 */
	@JSON
	public String toPayment() {
		logger.info("PartnerPayStatementManagerAction.toPayment 合伙人付款对账单付款开始 {}", new Date());
		try{
			//获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//调用录入接口
			String paymentNo = partnerPayStatementManagerService.toPayment(vo.getPaymentEntity(), vo.getBillNos(), currentInfo);
			//设置返回付款单号
			logger.info("返回的付款单号码【{}】", paymentNo);
			this.vo.getPaymentEntity().setPaymentNo(paymentNo);
			logger.info("toPayment 结束{}", new Date());
			return returnSuccess();
		}catch(BusinessException e){
			logger.error(e.getErrorCode(),e);
			return returnError(e);
		}
	}
	
	
	/**
	 * 确认/反确认 对账单
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-26 上午 10:00:11
	 */
	@JSON
	public String confirmStatement() {
		logger.info("PartnerPayStatementManagerAction.confirmStatement 合伙人付款对账单确认反确认开始 {}", new Date());
		if(null == partnerPayStatementVo){
			return returnError("参数不能为空");
		}
		if(null == partnerPayStatementVo.getPartnerPayStatementDto()){
			return returnError("参数不能为空");
		}
		if(null==partnerPayStatementVo.getPartnerPayStatementDto().getConfirmStatus()){
			return returnError("参数不能为空");
		}
		if(null==partnerPayStatementVo.getPartnerPayStatementDto().getStatementBillNo()){
			return returnError("参数不能为空");
		}
		// 获取dto
		PartnerPayStatementDto queryDto = this.partnerPayStatementVo.getPartnerPayStatementDto();
		// 获取当前操作时要进行确认还是反确认动作
		String confirmStatus = queryDto.getConfirmStatus();
		try {
			// 判断进行的确认还是反确认操作
			if (StringUtil.isBlank(confirmStatus)) {
				//提示确认类型不能为空
				throw new SettlementException("确认类型不能为空!");
			}
			
			// 调用接口进行确认/反确认操作
			queryDto = partnerPayStatementManagerService.confirmStatement(queryDto);
			this.partnerPayStatementVo.setPartnerPayStatementDto(queryDto);
			logger.info("confirmStatement 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}

	
	/**
	 * 删除对账单明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-02-26 上午 10:00:11
	 */
	@JSON
	public String deleteStatementEntry() {
		logger.info("PartnerPayStatementManagerAction.deleteStatementEntry 合伙人付款对账单删除对账单明细开始 {}", new Date());
		if(null == partnerPayStatementVo){
			return returnError("参数为空");
		}
		// 获取前台传入dto
		PartnerPayStatementDto queryDto = this.partnerPayStatementVo.getPartnerPayStatementDto();
		try {
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用service进行
			PartnerPayStatementDto dto = this.partnerPayStatementManagerService.deleteStatementDetail(queryDto, currentInfo);
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				//新建对账单制作dto
				dto = new PartnerPayStatementDto();
			}
			// 将dto设置给vo,将vo返回
			this.partnerPayStatementVo.setPartnerPayStatementDto(dto);
			logger.info("deleteStatementEntry 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 批量删除合伙人付款对账单明细
	 * @author gpz
	 * @date 2016年12月6日
	 */
	@JSON
	public String batchDeleteStatementEntry() {
		logger.info("批量删除合伙人付款对账单明细开始batchDeleteStatementEntry");
		if(null == partnerPayStatementVo){
			return returnError("参数为空");
		}
		try {
			List<PartnerPayStatementDEntity> detailList = partnerPayStatementVo
					.getPartnerPayStatementDto().getPartnerPayStatementDList();
			if (CollectionUtils.isEmpty(detailList)) {
				return returnError("参数为空");
			}

			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			//批量删除对账单明细
			PartnerPayStatementDto dto = partnerPayStatementManagerService
					.batchDeleteStatementEntry(detailList, currentInfo);

			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				// 新建对账单制作dto
				dto = new PartnerPayStatementDto();
			}
			// 将dto设置给vo,将vo返回
			partnerPayStatementVo.setPartnerPayStatementDto(dto);
			
			return returnSuccess();
		} catch (BusinessException e) {
			// 记录日志
			logger.error("批量删除合伙人付款对账单明细异常：" + e.getErrorCode(), e);
			// 异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 查询可添加的对账单明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-07 下午 03:20:11
	 */
	@JSON
	public String queryEntryForAdd() {
		logger.info("PartnerPayStatementManagerAction.queryEntryForAdd 合伙人付款对账单查询可添加明细开始 {}", new Date());
		// 获取前台传入dto
		PartnerPayStatementDto queryDto = this.partnerPayStatementVo.getPartnerPayStatementDto();
		try {
			if (queryDto.getPeriodBeginDate() != null) {
				queryDto.setPeriodBeginDate(DateUtils.getStartDatetime(queryDto.getPeriodBeginDate()));
			}
			//如果结束日期不为空，则需要对其进行+1操作
			if(queryDto.getPeriodEndDate()!=null){
				//结束日期加1天
				queryDto.setPeriodEndDate(DateUtils.getStartDatetime(queryDto.getPeriodEndDate(), 1));
			}
			// 获取当前登录用户
			CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
			// 调用service进行
			PartnerPayStatementDto dto = this.partnerPayStatementManagerService.queryEntryForAdd(queryDto, currentInfo, this.getStart(),this.getLimit());
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				//新建对账单制作dto
				dto = new PartnerPayStatementDto();
			}
			// 将dto设置给vo,将vo返回
			this.partnerPayStatementVo.setPartnerPayStatementDto(dto);
			logger.info("queryEntryForAdd 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	/**
	 * 添加对账单明细
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-09上午 10:04:11
	 */
	@JSON
	public String addPartnerPayStatementDetail() {
		logger.info("PartnerPayStatementManagerAction.addPartnerPayStatementDetail 合伙人付款对账单添加对账单明细开始 {}", new Date());
		if(null == partnerPayStatementVo){
			return returnError("参数为空");
		}
		// 获取前台传入dto
		PartnerPayStatementDto queryDto = this.partnerPayStatementVo.getPartnerPayStatementDto();
		try {
			//如果结束日期不为空，则需要对其进行+1操作
			if(queryDto.getPeriodEndDate()!=null){
				//结束日期加1天
				queryDto.setPeriodEndDate(DateUtils.convert(DateUtils.addDay(queryDto.getPeriodEndDate(), 1)));
			}
			// 调用service进行
			PartnerPayStatementDto dto = this.partnerPayStatementManagerService.addPartnerPayStatementDetail(queryDto);
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				//新建对账单制作dto
				dto = new PartnerPayStatementDto();
			}
			// 将dto设置给vo,将vo返回
			this.partnerPayStatementVo.setPartnerPayStatementDto(dto);
			logger.info("addPartnerPayStatementDetail 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
	
	
	/**
     * 导出付款对账单
     * @return
     */
    @JSON
    public String  partnerPayExportExl(){
        if(null==partnerPayStatementVo){
            return returnError("导出参数不能为空!");
        }
        if(null==partnerPayStatementVo.getPartnerPayStatementDto()){
            return returnError("导出参数不能为空!");
        }
        if(null==partnerPayStatementVo.getPartnerPayStatementDto().getStatementBillNos()){
            return returnError("导出参数不能为空!");
        }
        // 获取类头名称
        String[] header = partnerPayStatementVo.getArrayColumns();
        // 获取列头中文名
        String[] headerName = partnerPayStatementVo.getArrayColumnNames();
        try {
            try {
                // 设置导出excel名称
                this.setExcelName(new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return returnError("导出Excel失败");
            }
            //查询对账单信息
            List<PartnerPayStatementEntity> list;
            try{
                list = this.partnerPayStatementManagerService.queryPartnerPayStatementEntitesByBills(partnerPayStatementVo.getPartnerPayStatementDto());
            }catch (SettlementException e){
                logger.error(e.getMessage(), e);
                return returnError(e.getMessage());
            }
            // 导出格式数据
            ExportResource exportResource = packExportResourceXLS(list,header,headerName);
            // 创建导出表头对象
            ExportSetting exportSetting = new ExportSetting();
            // 设置名称
            exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
            // 创建导出工具类
            ExporterExecutor executor = new ExporterExecutor();
            // 输出到导出流中
            inputStream = executor.exportSync(exportResource, exportSetting);
        } catch (SettlementException e) {
            // 记录日志并返回失败
            logger.error(e.getMessage(),e);
            return returnError("导出对账单异常:" + e.getMessage());
        }
        return returnSuccess();
    }

    
    /**
     * 导出付款对账单明细
     * @return
     */
    @JSON
    public String  partnerPayEntryExportExl(){
    	logger.info("--导出合伙人付款对账单明细partnerPayEntryExportExl");
        if(null==partnerPayStatementVo){
            return returnError("导出参数不能为空!");
        }
        if(null==partnerPayStatementVo.getPartnerPayStatementDto()){
            return returnError("导出参数不能为空!");
        }
        if(StringUtils.isBlank(partnerPayStatementVo.getPartnerPayStatementDto().getStatementBillNo())){
            return returnError("导出参数对账单号不能为空!");
        }
        // 获取类头名称
        String[] header = partnerPayStatementVo.getArrayColumns();
        // 获取列头中文名
        String[] headerName = partnerPayStatementVo.getArrayColumnNames();
        try {
            try {
                // 设置导出excel名称
                this.setExcelName(new String((EXCELXMLNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
                return returnError("导出Excel失败");
            }
            //查询对账单信息
            List<PartnerPayStatementDEntity> list;
            try{
                list = this.partnerPayStatementManagerService.queryPartnerPayStatementEntriesByBillNo(partnerPayStatementVo.getPartnerPayStatementDto());
            }catch (SettlementException e){
                logger.error(e.getMessage(), e);
                return returnError(e.getMessage());
            }
            // 导出格式数据
            ExportResource exportResource = packExportResourceDetailXLS(list,header,headerName);
            // 创建导出表头对象
            ExportSetting exportSetting = new ExportSetting();
            // 设置名称
            exportSetting.setSheetName(SettlementConstants.EXCEL_SHEET_NAME);
            // 创建导出工具类
            ExporterExecutor executor = new ExporterExecutor();
            // 输出到导出流中
            inputStream = executor.exportSync(exportResource, exportSetting);
        } catch (SettlementException e) {
            // 记录日志并返回失败
            logger.error(e.getMessage(),e);
            return returnError("导出对账单明细异常:" + e.getMessage());
        }
        return returnSuccess();
    }
    
    //导出对账单的方法
    private ExportResource packExportResourceXLS(List<PartnerPayStatementEntity> list, String[] header, String[] headerName){
        // 导出excel数据
        ExportResource data = new ExportResource();
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();
        // 循环进行封装
        for (PartnerPayStatementEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(PartnerPayStatementEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    // 如果为日期，需要进行格式化
                    if (Date.class.equals(field.getType())&& fieldValue != null) {
                        //日期转化
                        fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
                    }
                    String fieldValueStr = (fieldValue == null ? "" : fieldValue.toString());
                    fieldValue = validaColumnName(entity, columnName,
							fieldValue, fieldValueStr);
                    fieldValue = validaFieldValue(entity, columnName,
							fieldValue, fieldValueStr);
                    if (fieldValue != null) {
                        rowList.add(fieldValue ==  null ? "" : fieldValue.toString());
                    } else {
                        rowList.add(null);
                    }
                }
            }
            sheetList.add(rowList);
        }
        //封装数据
        data.setRowList(sheetList);
        data.setHeads(headerName);
        return data;
    }


	private Object validaColumnName(PartnerPayStatementEntity entity,
			String columnName, Object fieldValue, String fieldValueStr) {
		if(columnName.equals("billType")){
		    //单据子类型
		    if(StringUtils.isNotEmpty(fieldValueStr)){
		        if("PDFP".equals(entity.getBillType())){
		            fieldValue = "到达提成";
		        }else if("PDDF".equals(entity.getBillType())){
		            fieldValue = "委托派费";
		        }else{
		            fieldValue = entity.getBillType();
		        }
		    }
		}
		return fieldValue;
	}


	private Object validaFieldValue(PartnerPayStatementEntity entity,
			String columnName, Object fieldValue, String fieldValueStr) {
		if(columnName.equals("statementStatus")){
		    //单据子类型
		    if(StringUtils.isNotEmpty(fieldValueStr)){
		        if("C".equals(entity.getStatementStatus())){
		            fieldValue = "已确认";
		        }else if("N".equals(entity.getStatementStatus())){
		            fieldValue = "未确认";
		        }else{
		            fieldValue = entity.getStatementStatus();
		        }
		    }
		}
		return fieldValue;
	}
	
    
    //导出对账单明细的方法
    private ExportResource packExportResourceDetailXLS(List<PartnerPayStatementDEntity> list, String[] header, String[] headerName){
        // 导出excel数据
        ExportResource data = new ExportResource();
        // 声明sheetList
        List<List<String>> sheetList = new ArrayList<List<String>>();
		List<String> termCodes = new ArrayList<String>();
		termCodes.add(DictionaryConstants.PICKUP_GOODS);//提货方式
		termCodes.add(DictionaryConstants.PICKUP_GOODS_AIR);//提货方式
        Map<String,Map<String,Object>> termMaps = SettlementUtil.getDataDictionaryByTermsCodes(termCodes);
		//获取全部有效的第三级别产品类型
		List<ProductEntity> productList = productService.queryLevel3ProductInfo();
		// 生成存储产品类型的map
		Map<String, String> productMap = new HashMap<String, String>();
		// 如果产品类型不为空，循环加入到map中
		if (!CollectionUtils.isEmpty(productList)) {
			for (ProductEntity entity : productList) {
				productMap.put(entity.getCode(), entity.getName());
			}
		}
        // 循环进行封装
        for (PartnerPayStatementDEntity entity : list) {
            // 声明一行的rowList
            List<String> rowList = new ArrayList<String>();
            for (String columnName : header) {
                // 通过名称获取field
                Field field = ReflectionUtils.findField(PartnerPayStatementDEntity.class, columnName);
                if (field != null) {
                    // 通过传入字段获取值
                    ReflectionUtils.makeAccessible(field);
                    Object fieldValue = ReflectionUtils.getField(field, entity);
                    // 如果为日期，需要进行格式化
                    if (Date.class.equals(field.getType())&& fieldValue != null) {
                        //日期转化
                        fieldValue = DateUtils.convert((Date) fieldValue,DateUtils.DATE_TIME_FORMAT);
                    }
                    String fieldValueStr = (fieldValue == null ? "" : fieldValue.toString());
                    fieldValue = validaPayStatement(entity, columnName,
							fieldValue, fieldValueStr);
                    if(columnName.equals("auditStatus")){
                        //审核状态
                        if(StringUtils.isNotEmpty(fieldValueStr)){
                            if("AA".equals(entity.getAuditStatus())){
                                fieldValue = "已审核";
                            }else if("NA".equals(entity.getAuditStatus())){
                                fieldValue = "未审核";
                            }else{
                                fieldValue = entity.getAuditStatus();
                            }
                        }
                    }
                    if(columnName.equals("paymentType")){
                        //付款方式
                        if(StringUtils.isNotEmpty(fieldValueStr)){
                            fieldValue = validaPartnerPay(entity);
                        }
                    }
                    if(columnName.equals("productCode")){
						//如果数据产品类型编码不为空
						if(StringUtils.isNotEmpty(fieldValue.toString())){
							//将产品类型转换编码为名称
							fieldValue=productMap.get(fieldValue.toString());
						}
					}
                    if(columnName.equals("receiveMethod") && null!=fieldValue){
						String receiveMethod = fieldValue.toString();
						//先去汽运提货方式词条中拿
						fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.PICKUP_GOODS, receiveMethod);
						//如果汽运提货方式没拿到，则去空运词条中拿
						if(fieldValue==null ||receiveMethod==fieldValue){
							fieldValue = SettlementUtil.getDataDictionaryByTermsName(termMaps,DictionaryConstants.PICKUP_GOODS_AIR,receiveMethod);
						}
					}
                    if (fieldValue != null) {
                        rowList.add(fieldValue ==  null ? "" : fieldValue.toString());
                    } else {
                        rowList.add(null);
                    }
                }
            }
            sheetList.add(rowList);
        }
        //封装数据
        data.setRowList(sheetList);
        data.setHeads(headerName);
        return data;
    }


	private Object validaPayStatement(PartnerPayStatementDEntity entity,
			String columnName, Object fieldValue, String fieldValueStr) {
		if(columnName.equals("billType")){
		    //单据子类型
		    if(StringUtils.isNotEmpty(fieldValueStr)){
		        if("PDFP".equals(entity.getBillType())){
		            fieldValue = "到达提成";
		        }else if("PDDF".equals(entity.getBillType())){
		            fieldValue = "委托派费";
		        }else{
		            fieldValue = entity.getBillType();
		        }
		    }
		}
		return fieldValue;
	}


	private Object validaPartnerPay(PartnerPayStatementDEntity entity) {
		Object fieldValue;
		if("CT".equals(entity.getPaymentType())){
		    fieldValue = "月结";
		}else if("CH".equals(entity.getPaymentType())){
		    fieldValue = "现金";
		}else if("CD".equals(entity.getPaymentType())){
		    fieldValue = "银行卡";
		}else if("TT".equals(entity.getPaymentType())){
		    fieldValue = "电汇";
		}else if("NT".equals(entity.getPaymentType())){
		    fieldValue = "支票";
		}else if("OL".equals(entity.getPaymentType())){
		    fieldValue = "网上支付";
		}else if("DT".equals(entity.getPaymentType())){
		    fieldValue = "临时欠款";
		}else if("FC".equals(entity.getPaymentType())){
		    fieldValue = "到付";
		}else{
		    fieldValue = entity.getPaymentType();
		}
		return fieldValue;
	}
	
	/**
	 * 查询费用承担部门
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-11下午 3:04:11
	 */
	@JSON
	public String queryExpenseCenter() {
		 if(null==partnerPayStatementVo){
            return returnError("参数不能为空!");
        }
        if(null==partnerPayStatementVo.getPartnerPayStatementDto()){
            return returnError("参数不能为空!");
        }
        if(null==partnerPayStatementVo.getPartnerPayStatementDto().getStatementBillNo()){
            return returnError("对账单号不能为空!");
        }
        PartnerPayStatementDto dto = this.partnerPayStatementVo.getPartnerPayStatementDto();
        try {
			// 调用service进行
			dto = this.partnerPayStatementManagerService.queryExpenseCenter(dto);
			// 如果有结果则将其放入到返回的vo中
			if (dto == null) {
				//新建对账单制作dto
				dto = new PartnerPayStatementDto();
			}
			// 将dto设置给vo,将vo返回
			this.partnerPayStatementVo.setPartnerPayStatementDto(dto);
			logger.info("getExpenseCenter 结束{}", new Date());
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (BusinessException e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return returnError(e);
		}
	}
    
	
	/**
	 * 导出对账单pdf格式
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-14 下午 2:04:11
	 */
	/*public String exportStatementPdf() {
		try {//partnerPayStatementVo
			// 获取mapper,进行前台转换
			ObjectMapper mapper = new ObjectMapper();
			// 将前台出入json字符串转化为对象
			PartnerPayStatementEntity entity = mapper.readValue((String) partnerPayStatementVo.getStatementofAccountStr(),PartnerPayStatementEntity.class);
			// 获取类头名称
			String[] header = partnerPayStatementVo.getArrayColumns();
			// 获取列头中文名
			String[] headerName = partnerPayStatementVo.getArrayColumnNames();
			// 放界面对账单信息
			Map<String, Object> map = new HashMap<String, Object>();
			// 获取当前路径
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			// 设置单据类型
			map.put("billType", DictUtil.rendererSubmitToDisplay(entity.getBillType(), DictionaryConstants.STATEMENT_OF_ACCOUNT__BILL_TYPE));
			// 对制单时间进行转化
			if (entity.getBillBeginTime() != null) {
				// 对日期进行格式化
				map.put("createTime", DateUtils.convert(entity.getBillBeginTime()));
			}
			// 合伙人编码
			map.put("customerCode", entity.getCustomerCode());
			// 确认状态进行转化
			map.put("statementStatus", DictUtil.rendererSubmitToDisplay(entity.getStatementStatus(),DictionaryConstants.STATEMENT_OF_ACCOUNT__CONFIRM_STATUS));
			// 判断日期是否为空
			if (entity.getPeriodBeginDate() != null) {
				// 对日期进行格式化
				map.put("periodBeginDate",DateUtils.convert(entity.getPeriodBeginDate()));
			}
			// 设置公司名称
			map.put("companyName", entity.getCompanyName());
			// 设置客户名称
			map.put("customerName", entity.getCustomerName());
			// 判断确认时间
			if (entity.getConfirmTime() != null) {
				// 对日期进行格式化
				map.put("confirmTime",DateUtils.convert(entity.getConfirmTime()));
			}
			// 判断结束日期
			if (entity.getPeriodEndDate() != null) {
				// 对日期进行格式化
				map.put("periodEndDate",DateUtils.convert(entity.getPeriodEndDate()));
			}
			// 判断对账单号是否为null
			if (StringUtil.isBlank(entity.getStatementBillNo())) {
				//提示对账单号不能为空
				throw new Exception("对账单号不能为空");
			}
			// 设置部门名称
			map.put("createOrgName", entity.getCreateOrgName());
			// 设置对账单号
			map.put("statementBillNo", entity.getStatementBillNo());
			// 设置部门标杆编码
			map.put("unifiedCode", entity.getUnifiedCode());
			// 设置银行账号
			map.put("companyAccountBankNo", entity.getCompanyAccountBankNo());
			// 设置开户行
			map.put("accountUserName", entity.getAccountUserName());
			// 设置开户行支行名称
			map.put("bankBranchName", entity.getBankBranchName());
			// 设置本期剩余未还金额
			map.put("periodPayAmount", entity.getPeriodPayAmount());
			// 设置结账次数
			map.put("settleNum", entity.getSettleNum());
			// 设置本期金额
			map.put("periodAmount", entity.getPeriodAmount());
			// 判断本期金额是否为0
			if (entity.getPeriodAmount() != null&& entity.getPeriodAmount().compareTo(BigDecimal.ZERO) != 0) {
				// 获取文件读取类
				PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
				// 声明图片位置
//				Resource resource = resolver.getResource("com/deppon/foss/module/settlement/writeoff/server/META-INF/images/sumDetail.png");
				// 按公司编号查询对应对账单章
				String chapterUrl = statementModifyService.queryStatementChapter(entity.getCompanyCode());
				// ddw
				Resource companyNameImg = resolver.getResource(chapterUrl);
				// 将图片放到map中
//				if (resource != null) {
//					map.put("subDetailImg", resource.getInputStream());
//				}
				// 将图片放到map中
				if (companyNameImg != null) {
					map.put("companyNameImg", companyNameImg.getInputStream());
				}
			}
			// 声明列标
			int flag = 0;
			// 拼接列头
			for (int i = 0; i < headerName.length; i++) {
				flag += 1;
				// 设置列标
				map.put("columnName" + flag, headerName[i]);
			}
			List<String> statementBillNos = new ArrayList<String>();
			statementBillNos.add(entity.getStatementBillNo());
			// 根据对账单号，查询对账单明细
			List<PartnerPayStatementDEntity> targetList = this.partnerPayStatementManagerService.queryDetailByStatementBillNos(statementBillNos);
			// 判断对账单明细是否为空
			if (CollectionUtils.isNotEmpty(targetList)) {
				// 循环对账单明细
				for (final PartnerPayStatementDEntity dEntity : targetList) {
					// 声明打印模板对应表格的一行数据
					final Map<String, Object> listMap = new HashMap<String, Object>();
					// 列标
					int index = 0;
					// 声明日期列表
					List<String> dateList = new ArrayList<String>();
					// 声明数据字典字段列表
					List<String> dictList = new ArrayList<String>();
					dictList.add("billType");// 单据子类型
					dictList.add("paymentType");// 付款方式
					dictList.add("receiveMethod");//提货方式
					dictList.add("productCode");// 运输性质
					dateList.add("accountDate");// 记账日期
					dateList.add("signDate");// 签收日期
					// 根据前台传入列来获取对应数据
					for (String columnName : header) {
						// 根据传入对账单明细的属性名称，根据反射，获取其属性对应的值
						Field field = ReflectionUtils.findField(PartnerPayStatementDEntity.class,columnName);
						// 判断值是否为空
						if (field != null) {
							index += 1;
							// 设置field允许获取值
							ReflectionUtils.makeAccessible(field);
							// 根据属性名获取当前循环的实体的对应值
							Object fieldValue = ReflectionUtils.getField(field, dEntity);
							// 如果为日期，需要转化
							if (fieldValue != null) {
								// 如果当前字段为日期，则进行格式化操作
								if (dateList.contains(columnName)) {
									// 转化日期为y-m-d形式
									String dateToString = DateUtils.convert((Date) fieldValue,"yyyy-MM-dd");
									// 重置fieldValue值
									fieldValue = dateToString;
								}
								// 如果为数据字典类型，需要转化
								if (dictList.contains(columnName)) {
									// 进行数据转化
									fieldValue = exprotPDFConvert(fieldValue.toString(),columnName);
								}
							}
							// 将当前字段放置到行map中
							listMap.put("columnName" + index, fieldValue);
						}
					}
					// 将行map放置到表map中
					list.add(listMap);
				}
			} else {
				// 声明行listMap，没有数据
				Map<String, Object> listMap = new HashMap<String, Object>();
				//加入到list
				list.add(listMap);
			}
			// 声明文件名称
			fileName = new String((EXCELNAME).getBytes(SettlementConstants.UNICODE_GBK),SettlementConstants.UNICODE_ISO);
			// 进行流导出操作
			pdfStream = PDFExport.exportPDF("settlement/writeoff","statementbill", map, list);
			//正常返回
			return returnSuccess();
		//异常处理	
		} catch (Exception e) {
			//记录日志
			logger.error(e.getMessage(), e);
			//异常返回
			return ERROR;
		}
	}*/

	/**
	 * 导出pdf数据转化
	 * 
	 * @author 311396-foss-wangwenbo
	 * @date 2016-03-14 下午 2:04:11
	 */
	/*private String exprotPDFConvert(String fieldValue, String columnName) {
		// 付款方式进行转化
		if (columnName.equals("paymentType")) {
			//数据字典转换
			fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(), DictionaryConstants.SETTLEMENT__PAYMENT_TYPE);
		}
		// 单据子类型进行转化
		if (columnName.equals("billType")) {
				//数据字典转换
				fieldValue = DictUtil.rendererSubmitToDisplay(fieldValue.toString(), DictionaryConstants.BILL_PAYABLE__BILL_TYPE);
		}
		//产品类型
		if(columnName.equals("productCode")){
			//通过产品code 获取产品名称,从缓存里面取
			ProductEntity productEntity=null;
			if(StringUtils.isNotEmpty(fieldValue.toString())){
				try{
					productEntity=productService.getProductByCache(fieldValue.toString(),new Date());
				}catch(Exception e){
					logger.error("查询产品类型报错，报错信息为    ");
				}
			}
			if(null!=productEntity){
				//产品名称				
				fieldValue = productEntity.getName();
			}
		}
		//提货方式
		if(columnName.equals("receiveMethod")){
			String receiveMethod = fieldValue.toString();
			//先去汽运提货方式词条中拿
			fieldValue = DictUtil.rendererSubmitToDisplay(receiveMethod, DictionaryConstants.PICKUP_GOODS);
			//如果汽运提货方式没拿到，则去空运词条中拿
			if(fieldValue==null ||receiveMethod==fieldValue){
				fieldValue = DictUtil.rendererSubmitToDisplay(receiveMethod, DictionaryConstants.PICKUP_GOODS_AIR);
			}
		}
		//返回值
		return fieldValue;
	}*/
    
	
	/**
	 * @return statementOfAccountMakeQueryResultVo
	 */
	public PartnerPayStatementVo getPartnerPayStatementVo() {
	return partnerPayStatementVo;
	}

	/**
	 * @param statementOfAccountMakeQueryResultVo
	 */
	public void setPartnerPayStatementVo(PartnerPayStatementVo partnerPayStatementVo) {
		this.partnerPayStatementVo = partnerPayStatementVo;
	}
	
	

	/**
	 * @param partnerPayStatementManagerService
	 */
	public void setPartnerPayStatementManagerService(
			IPartnerPayStatementManagerService partnerPayStatementManagerService) {
		this.partnerPayStatementManagerService = partnerPayStatementManagerService;
	}
	
	/**
	 * 对账单管理的接口
	 * @param statementModifyService
	 */
	public void setStatementModifyService(
			IStatementModifyService statementModifyService) {
		this.statementModifyService = statementModifyService;
	}


	/**
	 * @return pdfStream
	 */
/*	public InputStream getPdfStream() {
		return pdfStream;
	}*/

	/**
	 * @param pdfStream
	 */
/*	public void setPdfStream(InputStream pdfStream) {
		this.pdfStream = pdfStream;
	}*/

	/**
	 * @return fileName
	 */
	/*public String getFileName() {
		return fileName;
	}*/

	/**
	 * @param fileName
	 */
	/*public void setFileName(String fileName) {
		this.fileName = fileName;
	}*/
	
	/**
	 * @GET
	 * @return excelName
	 */
	public String getExcelName() {
		/*
		 *@get
		 *@ return excelName
		 */
		return excelName;
	}
//
	/**
	 * @SET
	 * @param excelName
	 */
	public void setExcelName(String excelName) {
		/*
		 *@set
		 *@this.excelName = excelName
		 */
		this.excelName = excelName;
	}

	/**
	 * @GET
	 * @return inputStream
	 */
	public InputStream getInputStream() {
		/*
		 *@get
		 *@ return inputStream
		 */
		return inputStream;
	}

	/**
	 * @SET
	 * @param inputStream
	 */
	public void setInputStream(InputStream inputStream) {
		/*
		 *@set
		 *@this.inputStream = inputStream
		 */
		this.inputStream = inputStream;
	}


	public void setVo(BillPaymentVo vo) {
		this.vo = vo;
	}
	public BillPaymentVo getVo() {
		return vo;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	
}
