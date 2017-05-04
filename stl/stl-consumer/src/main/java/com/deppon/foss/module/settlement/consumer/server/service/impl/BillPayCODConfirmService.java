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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/settlement/consumer/server/service/impl/BillPayCODConfirmService.java
 * 
 * FILE NAME        	: BillPayCODConfirmService.java
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
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * $$$$--------BEGIN--------$$$$
 * 
 * 
 * 
 * @修订记录 
 *		日期   							修订内容   									修订人员   								版本号 
 *		2012-05-11   				创建版   										毛建强  									V0.1
 *		2012-07-16   				修订版   										毛建强  									V0.5
 *		2012-07-25   				版本升级  									毛建强  									V0.9
 *        
 *
 *	@参考 
 *		SUC-139-确认代收货款退款申请
 *		
 *		
 *    @相关业务用例
 *		BUC_FOSS_4.7.50.30_020  出纳审核代收货款
 *		
 *		
 *	@用例描述
 *		营业部收银员确认代收货款信息后，
 *		进行审核代收货款操作,
 *		将代收货款更细状态为“收银员审核”状态。 
 *	@用例条件
 *		条件类型  						描述  																				
 *						引用系统用例
 *		前置条件  
 *											1、  进行营业部冻结或修改过账号的有效的代收货款单据
 *											2、  审核退进行应收冲应付后还有未核销金额的代收货款单据
 *											3、  代收货款状态为退款失败  
 *		后置条件  
 *											更新代收货款状态为“收银员审核”  
 *											
 *	@操作用户角色
 *		操作用户  						描述
 *		收银员  							收银员可以进行营业部冻结、修改账号和审核等操作
 *		
 *	@界面要求
 *		无
 *		
 *	@表现方式
 *		Web
 *		
 *		
 *	@界面原型
 * 
 * 
 *	@界面描述
 *		页面初始化组件描述：
 *			1、  代收货款状态默认显示“待审核或退款失败”
 *			2、  单号文本域默认为空
 *		页面输入组件描述
 *			1、  代收货款状态：代收货款的操作状态
 *  					待审核或退款失败
 *  					未退款
 *			2、  单号查询：输入多个单号，以逗号隔开，最多不能超过10个。
 *		页面表格组件描述：
 *			1、  默认为分页显示，每页显示数据可选择20,30,50,100
 *		页面提供的按钮控件描述：
 *			1、  查询：查询代收货款列表功能
 *			2、  重置：恢复查询条件到界面初始化状态
 *			3、  审核：进行审核操作
 *			4、  营业部冻结：将代收货款单据进行冻结，
 *					使其不能在资金部退款。
 *					参见系统用例《DP-FOSS-结算系统用例-锁定代收货款》
 *			5、修改账号：营业部收银员点击该按钮，
 *				弹出修改账号界面，
 *				从而修改代收货款的退款账号信息。
 *				参见系统用例《DP-FOSS-结算系统用例-锁定代收货款》
 *				
 *				
 *	@操作步骤
 *		查询
 *		序号  												基本步骤  								相关数据  					
 *		补充步骤
 *		1  												页面初始化    														
 *			1、  代收汇款状态下拉框默显示“待审核或退款失败”
 *																														
 *				2、  页面表格不显示
 *		2  												如果选择了代收货款状态，
 *															但是没有填写单号，
 *															点击“查询”按钮  代收货款状态  									1、查询
 *															出该部门符合所选退款条件的代收货款单据
 *																														
 *				2、规则-业务规则参见SR1
 *																														
 *				3、扩展事件参考2b、2c
 *		3  												如果输入单号，
 *															点击“查询”按钮  输入单号  										1、  判
 *															断单号是否超过10个，
 *																														
 *						如果超过10个，则弹出提示
 *																														
 *						扩展事件参见2a
 *																														
 *				2、  查询该单号的代收货款信息
 *																														
 *				3、  规则-业务规则参见SR2
 *																														
 *				4、  扩展事件参考2b、2c
 *
 *		审核
 *		序号  												基本步骤  								相关数据  					
 *		补充步骤
 *		4  												点击查询按钮，查询到数据后。
 *															勾选查询出来的代收货款数据
 *															（可多选）  							代收货款信息  
 *		5  点击“审核”按钮  																		代收货款信息  				1、  判
 *		断是否选中数据，
 *																														
 *						如果没选中数据，
 *																														
 *						则弹出提示
 *																														
 *					扩展事件参见5a
 *																														
 *				2、  调用综合管理接口，
 *																														
 *						判断所选账号是否存在对应的账号信息，
 *																														
 *						如果不存在则抛出异常提示
 *																														
 *						扩展事件参见5b
 *																														
 *				3、  更新代收货款状态为“收银员审核”。
 *																														
 *						业务规则请参加SR3
 *																														
 *				4、  添加日志，
 *																														
 *						用户可以通过统一的日志查询界面进行查询
 *
 *	@扩展事件
 *		序号  					扩展事件  														相关数据  						
 *					备注
 *		2a  					如果输入单号超过10个，
 *								则在输入框旁边提示 																			
 *												 提示信息  输入单号不能超过10个
 *		2b  					当点击“查询”按钮，
 *								没有查询到数据时  																			
 *												提示信息  弹出框提示“没有查询到数据！”
 *		2c  					重置  																						
 *								提示信息  恢复界面控件到初始化状态
 *		5a  					如果没选中数据，																				
 *					则弹出提示  提示信息  
 *																														
 *							请至少选中一条数据进行审核！
 *		5b  					如果代收货款账号不存在于
 *								crm中的客户银行账号信息，
 *								则弹出提示信息  																				
 *												提示信息  该账号不存在对应的账号信息，
 *																														
 *							请选择重新修改账号
 *
 *	@业务规则
 *		序号  					描述
 *		SR1  				1、  代收货款对应的应付单的应付部门为当前登录部门
 *								2、  若代收货款状态为“待审核或退款失败”，
 *										则代收货款状态等于“退款失败”或“待审核”或“营业部冻结”
 *								3、  若代收货款状态为“未退款”，
 *										则查询出代收货款状态等于“未退款”
 *		SR2  				1、  代收货款的运单号等于所输入运单号
 *								2、  代收货款状态为“退款失败”或“待审核”或“未退款”或“营业部冻结”
 *		SR3  				更新选中数据的代收货款状态为“收银员审核”状态。
 *								1、  代收货款状态等于“退款失败”或“待审核”或“未退款”或“营业部冻结”
 *								2、  代收货款对应的应付单的挂账部门为当前登录部门
 *
 *	@数据元素
 *		页面查询条件（输入）
 *		字段名称   				说明   					输入限制  				输入项提示文本  			长度  				是否必填  	
 *				备注
 *
 *		代收货款状态  			代收货款状态  			枚举  						代收货款退款状态  		10  				是  		
 *					无
 *  		单号  						运单号  					文本  						无  							30  	
 *					否  					无
 *
 *	@代收货款列表信息（输出）
 *		字段名称   				说明  						 输出限制  				输入项提示文本  			长度  				是否必
 *		填  			备注
 *		运单号 					 运单号  					无  						无  							10  			
 *			无  					来源于运单
 *		应付单编号  			应付单对应的编号  	无  						无  							30  				无  		
 *					来源于应付单
 *		开单金额  				代收货款金额  			无  						无  							10  				无  	
 *						运单开单确认的代收货款
 *		开单金额				代收货款金额			无							无								10			
 *			无						运单开单确认的代收货款
 *		冲应收金额				冲销的金额				无							无								10			
 *			无						运单应收冲应付的金额
 *		应退金额				应付代收货款金额。
 *									取应付单未核销金额	无							无								10		
 *											无						开单金额=冲应收金额+应退金额
 *		退款类型				代收货款退款类型	无						无							20				无		
 *					无
 *		代收货款状态			代收货款状态			无						无							20				无	
 *						无
 *		客户名称				发货客户名称			无						无							20				无	
 *						来源于运单
 *		收款人					代收货款收款人姓名	无						无							20				无	
 *						
 *		账号						收款账号				无						无							40				
 *		无					
 *		开户行					收款账号所属银行	无						无							20				无	
 *						
 *		客户收款人关系		发货客户与收
 *									人关系					无						无							50		
 *											无					
 *		对公对私标志			账号类型				无						无							20				无	
 *						隐藏
 *		省份						账号所属银行所在省份					无						无							20	
 *					无					隐藏
 *		城市						账号所属银行所在城市					无						无							20	
 *					无					隐藏
 *		银行地址（支行）	账号所属银行具体地址					无						无							50			
 *			无					隐藏
 *		收款人手机号			账号开户人的手机号					11						隐藏							
 *		业务日期				应付单的业务日期					无						无							参照统一标准	
 *					无					无
 *		部门						应付单的应付部门					无						无							一标准	
 *					无					等于当前登录部门
 *		备注						备注信息								无						无							
 *		255				无					显示代收货款备注信息：如经理退回或资金部退回等填写退回原因
 * 
 *
 *	@更新代收货款状态（输出）
 *                     字段名称                       说明                     输出限制                  输入项提示文本                       长度                     是否必填                       备注
 *                   代收货款状态                代收货款的操作状态                        无                        无                       30                        是                  “收银员审核”
 *
 *
 *	@非功能性需求
 *		使用量  											每天审核数据约为700票
 *		2012年全网估计用户数  					收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
 *		响应要求
 *		（如果与全系统要求 不一致的话）  		响应时间2-5s以内 
 *		使用时间段  									全天
 *		高峰使用时间段  								8:00-17:30
 *
 *	@接口描述：
 *		接口名称   										对方系统（外部系统或内部其他模块）  					接口描述
 *		调用查询客户账号信息接口  				综合管理系统   													获取对应客户的开户行信息
 *
 * 
 * 
 * @修订记录 
 *		日期   							修订内容   									修订人员   								版本号 
 *		2012-05-11   				创建版   										毛建强  									V0.1
 *		2012-07-16   				修订版   										毛建强  									V0.5
 *		2012-07-25   				版本升级  									毛建强  									V0.9
 *        
 *
 *	@参考 
 *		SUC-139-确认代收货款退款申请
 *		
 *		
 *    @相关业务用例
 *		BUC_FOSS_4.7.50.30_020  出纳审核代收货款
 *		
 *		
 *	@用例描述
 *		营业部收银员确认代收货款信息后，
 *		进行审核代收货款操作,
 *		将代收货款更细状态为“收银员审核”状态。 
 *	@用例条件
 *		条件类型  						描述  																				
 *						引用系统用例
 *		前置条件  
 *											1、  进行营业部冻结或修改过账号的有效的代收货款单据
 *											2、  审核退进行应收冲应付后还有未核销金额的代收货款单据
 *											3、  代收货款状态为退款失败  
 *		后置条件  
 *											更新代收货款状态为“收银员审核”  
 *											
 *	@操作用户角色
 *		操作用户  						描述
 *		收银员  							收银员可以进行营业部冻结、修改账号和审核等操作
 *		
 *	@界面要求
 *		无
 *		
 *	@表现方式
 *		Web
 *		
 *		
 *	@界面原型
 * 
 * 
 *	@界面描述
 *		页面初始化组件描述：
 *			1、  代收货款状态默认显示“待审核或退款失败”
 *			2、  单号文本域默认为空
 *		页面输入组件描述
 *			1、  代收货款状态：代收货款的操作状态
 *  					待审核或退款失败
 *  					未退款
 *			2、  单号查询：输入多个单号，以逗号隔开，最多不能超过10个。
 *		页面表格组件描述：
 *			1、  默认为分页显示，每页显示数据可选择20,30,50,100
 *		页面提供的按钮控件描述：
 *			1、  查询：查询代收货款列表功能
 *			2、  重置：恢复查询条件到界面初始化状态
 *			3、  审核：进行审核操作
 *			4、  营业部冻结：将代收货款单据进行冻结，
 *					使其不能在资金部退款。
 *					参见系统用例《DP-FOSS-结算系统用例-锁定代收货款》
 *			5、修改账号：营业部收银员点击该按钮，
 *				弹出修改账号界面，
 *				从而修改代收货款的退款账号信息。
 *				参见系统用例《DP-FOSS-结算系统用例-锁定代收货款》
 *				
 *				
 *	@操作步骤
 *		查询
 *		序号  												基本步骤  								相关数据  					
 *		补充步骤
 *		1  												页面初始化    														
 *			1、  代收汇款状态下拉框默显示“待审核或退款失败”
 *																														
 *				2、  页面表格不显示
 *		2  												如果选择了代收货款状态，
 *															但是没有填写单号，
 *															点击“查询”按钮  代收货款状态  									1、查询
 *															出该部门符合所选退款条件的代收货款单据
 *																														
 *				2、规则-业务规则参见SR1
 *																														
 *				3、扩展事件参考2b、2c
 *		3  												如果输入单号，
 *															点击“查询”按钮  输入单号  										1、  判
 *															断单号是否超过10个，
 *																														
 *						如果超过10个，则弹出提示
 *																														
 *						扩展事件参见2a
 *																														
 *				2、  查询该单号的代收货款信息
 *																														
 *				3、  规则-业务规则参见SR2
 *																														
 *				4、  扩展事件参考2b、2c
 *
 *		审核
 *		序号  												基本步骤  								相关数据  					
 *		补充步骤
 *		4  												点击查询按钮，查询到数据后。
 *															勾选查询出来的代收货款数据
 *															（可多选）  							代收货款信息  
 *		5  点击“审核”按钮  																		代收货款信息  				1、  判
 *		断是否选中数据，
 *																														
 *						如果没选中数据，
 *																														
 *						则弹出提示
 *																														
 *					扩展事件参见5a
 *																														
 *				2、  调用综合管理接口，
 *																														
 *						判断所选账号是否存在对应的账号信息，
 *																														
 *						如果不存在则抛出异常提示
 *																														
 *						扩展事件参见5b
 *																														
 *				3、  更新代收货款状态为“收银员审核”。
 *																														
 *						业务规则请参加SR3
 *																														
 *				4、  添加日志，
 *																														
 *						用户可以通过统一的日志查询界面进行查询
 *
 *	@扩展事件
 *		序号  					扩展事件  														相关数据  						
 *					备注
 *		2a  					如果输入单号超过10个，
 *								则在输入框旁边提示 																			
 *												 提示信息  输入单号不能超过10个
 *		2b  					当点击“查询”按钮，
 *								没有查询到数据时  																			
 *												提示信息  弹出框提示“没有查询到数据！”
 *		2c  					重置  																						
 *								提示信息  恢复界面控件到初始化状态
 *		5a  					如果没选中数据，																				
 *					则弹出提示  提示信息  
 *																														
 *							请至少选中一条数据进行审核！
 *		5b  					如果代收货款账号不存在于
 *								crm中的客户银行账号信息，
 *								则弹出提示信息  																				
 *												提示信息  该账号不存在对应的账号信息，
 *																														
 *							请选择重新修改账号
 *
 *	@业务规则
 *		序号  					描述
 *		SR1  				1、  代收货款对应的应付单的应付部门为当前登录部门
 *								2、  若代收货款状态为“待审核或退款失败”，
 *										则代收货款状态等于“退款失败”或“待审核”或“营业部冻结”
 *								3、  若代收货款状态为“未退款”，
 *										则查询出代收货款状态等于“未退款”
 *		SR2  				1、  代收货款的运单号等于所输入运单号
 *								2、  代收货款状态为“退款失败”或“待审核”或“未退款”或“营业部冻结”
 *		SR3  				更新选中数据的代收货款状态为“收银员审核”状态。
 *								1、  代收货款状态等于“退款失败”或“待审核”或“未退款”或“营业部冻结”
 *								2、  代收货款对应的应付单的挂账部门为当前登录部门
 *
 *	@数据元素
 *		页面查询条件（输入）
 *		字段名称   				说明   					输入限制  				输入项提示文本  			长度  				是否必填  	
 *				备注
 *
 *		代收货款状态  			代收货款状态  			枚举  						代收货款退款状态  		10  				是  		
 *					无
 *  		单号  						运单号  					文本  						无  							30  	
 *					否  					无
 *
 *	@代收货款列表信息（输出）
 *		字段名称   				说明  						 输出限制  				输入项提示文本  			长度  				是否必
 *		填  			备注
 *		运单号 					 运单号  					无  						无  							10  			
 *			无  					来源于运单
 *		应付单编号  			应付单对应的编号  	无  						无  							30  				无  		
 *					来源于应付单
 *		开单金额  				代收货款金额  			无  						无  							10  				无  	
 *						运单开单确认的代收货款
 *		开单金额				代收货款金额			无							无								10			
 *			无						运单开单确认的代收货款
 *		冲应收金额				冲销的金额				无							无								10			
 *			无						运单应收冲应付的金额
 *		应退金额				应付代收货款金额。
 *									取应付单未核销金额	无							无								10		
 *											无						开单金额=冲应收金额+应退金额
 *		退款类型				代收货款退款类型	无						无							20				无		
 *					无
 *		代收货款状态			代收货款状态			无						无							20				无	
 *						无
 *		客户名称				发货客户名称			无						无							20				无	
 *						来源于运单
 *		收款人					代收货款收款人姓名	无						无							20				无	
 *						
 *		账号						收款账号				无						无							40				
 *		无					
 *		开户行					收款账号所属银行	无						无							20				无	
 *						
 *		客户收款人关系		发货客户与收
 *									人关系					无						无							50		
 *											无					
 *		对公对私标志			账号类型				无						无							20				无	
 *						隐藏
 *		省份						账号所属银行所在省份					无						无							20	
 *					无					隐藏
 *		城市						账号所属银行所在城市					无						无							20	
 *					无					隐藏
 *		银行地址（支行）	账号所属银行具体地址					无						无							50			
 *			无					隐藏
 *		收款人手机号			账号开户人的手机号					11						隐藏							
 *		业务日期				应付单的业务日期					无						无							参照统一标准	
 *					无					无
 *		部门						应付单的应付部门					无						无							一标准	
 *					无					等于当前登录部门
 *		备注						备注信息								无						无							
 *		255				无					显示代收货款备注信息：如经理退回或资金部退回等填写退回原因
 * 
 *
 *	@更新代收货款状态（输出）
 *                     字段名称                       说明                     输出限制                  输入项提示文本                       长度                     是否必填                       备注
 *                   代收货款状态                代收货款的操作状态                        无                        无                       30                        是                  “收银员审核”
 *
 *
 *	@非功能性需求
 *		使用量  											每天审核数据约为700票
 *		2012年全网估计用户数  					收银员数量约2236名(截止2012.4.12，其增长速度与网点增长速度成正比)
 *		响应要求
 *		（如果与全系统要求 不一致的话）  		响应时间2-5s以内 
 *		使用时间段  									全天
 *		高峰使用时间段  								8:00-17:30
 *
 *	@接口描述：
 *		接口名称   										对方系统（外部系统或内部其他模块）  					接口描述
 *		调用查询客户账号信息接口  				综合管理系统   													获取对应客户的开户行信息
 *
 *
 * 
 * 
 * 
 *	@操作步骤
 *		查询
 *		序号  												基本步骤  								相关数据  					
 *		补充步骤
 *		1  												页面初始化    														
 *			1、  代收汇款状态下拉框默显示“待审核或退款失败”
 *																														
 *				2、  页面表格不显示
 *		2  												如果选择了代收货款状态，
 *															但是没有填写单号，
 *															点击“查询”按钮  代收货款状态  									1、查询
 *															出该部门符合所选退款条件的代收货款单据
 *																														
 *				2、规则-业务规则参见SR1
 *																														
 *				3、扩展事件参考2b、2c
 *		3  												如果输入单号，
 *															点击“查询”按钮  输入单号  										1、  判
 *															断单号是否超过10个，
 *																														
 *						如果超过10个，则弹出提示
 *																														
 *						扩展事件参见2a
 *																														
 *				2、  查询该单号的代收货款信息
 *																														
 *				3、  规则-业务规则参见SR2
 *																														
 *				4、  扩展事件参考2b、2c
 *
 *		审核
 *		序号  												基本步骤  								相关数据  					
 *		补充步骤
 *		4  												点击查询按钮，查询到数据后。
 *															勾选查询出来的代收货款数据
 *															（可多选）  							代收货款信息  
 *		5  点击“审核”按钮  																		代收货款信息  				1、  判
 *		断是否选中数据，
 *																														
 *						如果没选中数据，
 *																														
 *						则弹出提示
 *																														
 *					扩展事件参见5a
 *																														
 *				2、  调用综合管理接口，
 *																														
 *						判断所选账号是否存在对应的账号信息，
 *																														
 *						如果不存在则抛出异常提示
 *																														
 *						扩展事件参见5b
 *																														
 *				3、  更新代收货款状态为“收银员审核”。
 *																														
 *						业务规则请参加SR3
 *																														
 *				4、  添加日志，
 *																														
 *						用户可以通过统一的日志查询界面进行查询
 *
 *	@扩展事件
 *		序号  					扩展事件  														相关数据  						
 *					备注
 *		2a  					如果输入单号超过10个，
 *								则在输入框旁边提示 																			
 *												 提示信息  输入单号不能超过10个
 *		2b  					当点击“查询”按钮，
 *								没有查询到数据时  																			
 *												提示信息  弹出框提示“没有查询到数据！”
 *		2c  					重置  																						
 *								提示信息  恢复界面控件到初始化状态
 *		5a  					如果没选中数据，																				
 *					则弹出提示  提示信息  
 *																														
 *							请至少选中一条数据进行审核！
 *		5b  					如果代收货款账号不存在于
 *								crm中的客户银行账号信息，
 *								则弹出提示信息  																				
 *												提示信息  该账号不存在对应的账号信息，
 *																														
 *							请选择重新修改账号
 *
 * $$$$--------END--------$$$$
 * 
 ******************************************************************************/

package com.deppon.foss.module.settlement.consumer.server.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.base.common.api.server.service.IMessageService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.dto.BillPaymentDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillPayCODConfirmService;
import com.deppon.foss.module.settlement.consumer.api.server.service.ICodCommonService;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.CODEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.BillCODPayConfirmDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CODDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 代收货款汇款确认服务.
 *
 * @author 046644-foss-zengbinwen
 * @date 2012-11-6 上午8:42:11
 */
public class BillPayCODConfirmService implements IBillPayCODConfirmService {

	/** 日志. */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPayCODConfirmService.class);

	/** 代收货款服务. */
	private ICodCommonService codCommonService;
	
	/** 付款单服务. */
	private IBillPaymentService billPaymentService;
	
	/**在线通知*/
    private IMessageService messageService;

	public IMessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	/**
	 * 查询代收货款汇款确认.
	 *
	 * @param dto the dto
	 * @param start the start
	 * @param offset the offset
	 * @return the list
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:10:54
	 */
	@Override
	public List<CODDto> queryPayConfirm(BillCODPayConfirmDto dto, int start,
			int offset) throws SettlementException {

		LOGGER.trace("Service start,start Time:"
				+ (dto == null ? null : dto.getExportStartTime()));

		if (dto == null) {
			throw new SettlementException("内部错误，待确认的查询条件为空!");
		}

		// 校验并设置查询条件
		validateAndSetConfirmDto(dto);
		
		//核销类型
		dto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);

		List<CODDto> result = codCommonService.queryPayConfirm(dto, start,
				offset);

		LOGGER.trace("Service end.");

		return result;
	}

	/**
	 * 查询代收货款汇款确认大小，合计金额.
	 *
	 * @param dto the dto
	 * @return the int
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:12:13
	 */
	@Override
	public CODDto queryPayConfirmSize(BillCODPayConfirmDto dto)
			throws SettlementException {

		LOGGER.trace("Service start,start Time:"
				+ (dto == null ? null : dto.getExportStartTime()));
		// 判断查询条件是否为空
		if (dto == null) {
			throw new SettlementException("查询代收货款汇款确认条件为空.");
		}
		
		// 校验并设置查询条件
		validateAndSetConfirmDto(dto);
		
		//核销类型
		dto.setWriteoffType(SettlementDictionaryConstants.BILL_WRITEOFF__WRITEOFF_TYPE__RECEIVABLE_PAYABLE);

		CODDto codDto = codCommonService.queryPayConfirmSize(dto);

		LOGGER.trace("Service end.");

		return codDto;
	}

	/**
	 * 校验参数是否合法.
	 *
	 * @param dto the dto
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-6 上午9:47:40
	 */
	private void validateAndSetConfirmDto(BillCODPayConfirmDto dto)
			throws SettlementException {

		// 判断查询条件是否为空
		if (dto == null) {
			throw new SettlementException("查询代收货款汇款确认条件为空.");
		}

		// 允许的代收货款状态
		List<String> acceptStatuses = new ArrayList<String>();
		acceptStatuses
				.add(SettlementDictionaryConstants.COD__STATUS__RETURNING);
		acceptStatuses.add(SettlementDictionaryConstants.COD__STATUS__RETURNED);

		// 如果代收货款状态为空，则默认代收货款状态为退款中、已退款
		List<String> statuses = dto.getStatuses();
		if (CollectionUtils.isEmpty(statuses)) {
			dto.setStatuses(acceptStatuses);
		}

		// 否则需要判断代收货款状态是否为
		else {

			// 循环判断代收货款状态是否为退款中、已退款
			for (String status : statuses) {

				if (!acceptStatuses.contains(status)) {
					throw new SettlementException("代收货款状态不为退款中或已退款.");
				}

			}
		}

		// 校验导出开始时间、结束时间
		Date startTime = dto.getExportStartTime();
		Date endTime = dto.getExportEndTime();

		// 开始时间不能为空
		if (startTime == null) {
			throw new SettlementException("开始时间不能为空.");
		}

		// 结束时间不能为空
		if (endTime == null) {
			throw new SettlementException("结束时间不能为空.");
		}

		// 导出开始时间不能大于导出结束时间
		if (startTime.compareTo(endTime) > 0) {
			throw new SettlementException("开始时间不能大于结束时间");
		}

		// 开始时间与结束时间不能起始7天
		Date addedStartTime = DateUtils.addDays(startTime,
				SettlementConstants.DATE_LIMIT_DAYS_WEEK);
		if (addedStartTime.compareTo(endTime) < 0) {
			throw new SettlementException("开始时间与结束时间相差不能超过"
					+ SettlementConstants.DATE_LIMIT_DAYS_WEEK + "天");
		}

		// 是否有效、应付单是否有效、应付单单据类型、退款路径
		dto.setActive(FossConstants.ACTIVE);
		dto.setPayableActive(FossConstants.ACTIVE);
		dto.setPayableBillType(SettlementDictionaryConstants.BILL_PAYABLE__BILL_TYPE__ACCOUNT_PAYABLE_COD);
		dto.setRefundPath(SettlementDictionaryConstants.COD__COD_REFUND_PATH__OFFLINE);
	}

	/**
	 * 更新代收货款汇款成功状态.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:48:54
	 */
	@Transactional
	public void updatePayCODOfflineSuccess(List<String> entityIds,
			CurrentInfo currentInfo) throws SettlementException {

		LOGGER.info("更新代收货款汇款成功状态Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		CODEntity entity = null;
		String status = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态不为退款中，则抛出异常
			status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__RETURNING
					.equals(status)) {
				throw new SettlementException("代收货款状态不为退款中，不能进行汇款成功操作");
			}

			// 更新代收货款状态为已退款
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURNED);
			Date now = new Date();
			entity.setModifyTime(now);
			entity.setRefundSuccessTime(now);
			entity.setRemittanceFailNotes(null);
			entity.setModifyUserName(currentInfo.getEmpName());
			entity.setModifyUserCode(currentInfo.getEmpCode());
			codCommonService.updatePayCODSuccess(entity, currentInfo);
			
			// 查询应付单
			BillPayableEntity billPayableEntity = codCommonService.getBillPayableEntity(entity);
			// 查询付款单
			List<BillPaymentEntity> billPaymentList = billPaymentService.queryBillPaymentByPaymentNOs(Arrays.asList(billPayableEntity.getPaymentNo()), FossConstants.ACTIVE);
			//付款单状态变为--已汇款
			BillPaymentDto billPaymentDto = new BillPaymentDto();
			billPaymentDto.setBillPayments(billPaymentList);// 设置更新付款单集合
			billPaymentDto.setRemitStatus(SettlementDictionaryConstants.BILL_PAYMENT__REMIT_STATUS__TRANSFERRED);// 设置更新汇款状态为"未汇款"
			// 批量更新付款单为付款中
			billPaymentService.batchReverseRemitStatusBillPayment(billPaymentDto,currentInfo);

		}

		LOGGER.info("更新代收货款汇款成功状态Service end.");
	}

	/**
	 * 更新代收货款汇款失败状态.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @param failNotes the fail notes
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:49:39
	 */
	public void updatePayCODOfflineFailure(List<String> entityIds,
			CurrentInfo currentInfo, String failNotes)
			throws SettlementException {

		LOGGER.info("Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		// 如果代收货款汇款失败原因为空，则抛出异常
		if (StringUtils.isEmpty(failNotes)) {
			throw new SettlementException("代收货款汇款失败原因为空");
		}

		CODEntity entity = null;
		String status = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态不为退款中，则抛出异常
			status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__RETURNING
					.equals(status)) {
				throw new SettlementException("代收货款状态不为退款中，不能进行汇款失败操作");
			}

			// 更新代收货款状态为退款失败申请
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__RETURN_FAILURE_APPLICATION);
			Date now = new Date();
			entity.setModifyTime(now);
			entity.setRefundSuccessTime(null);
			entity.setRemittanceFailNotes(failNotes);
			entity.setModifyUserName(currentInfo.getEmpName());
			entity.setModifyUserCode(currentInfo.getEmpCode());
			codCommonService.updatePayCODFailure(entity, currentInfo);
			
			//如果为即日退、三日退、审核退 则提示应付部门及时处理 提示放在审核时候提示
//			if(SettlementDictionaryConstants.COD__COD_TYPE__RETURN_1_DAY.equals(entity.getCodType())
//					||SettlementDictionaryConstants.COD__COD_TYPE__RETURN_3_DAY.equals(entity.getCodType())
//					||SettlementDictionaryConstants.COD__COD_TYPE__RETURN_APPROVE.equals(entity.getCodType())){
//				InstationJobMsgEntity entiy = new InstationJobMsgEntity();
//				//发送人和发送部门信息
//				entiy.setSenderCode(currentInfo.getEmpCode());
//				entiy.setSenderName(currentInfo.getEmpName());
//				entiy.setSenderOrgCode(currentInfo.getCurrentDeptCode());
//				entiy.setSenderOrgName(currentInfo.getCurrentDeptName());
//				//设置为代收货款消息
//				entiy.setMsgType(MessageConstants.MSG_TYPE__COLLECTION);						
//				//接受方式为组织
//				entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
//				//设置接收部门信息
//				entiy.setReceiveOrgCode(entity.getPayableOrgCode());
//				//发送方式为
//				entiy.setReceiveType(MessageConstants.MSG__RECEIVE_TYPE__ORG);
//				entiy.setReceiveOrgName(entity.getPayableOrgName());
//				//设置
//				entiy.setContext("你部门含有代收货款的单据："+entity.getWaybillNo()+"汇款失败，请及时处理！");
//				messageService.createBatchInstationMsg(entiy);	
//				}

		}

		LOGGER.info("Service end.");
	}

	/**
	 * 更新代收货款反汇款成功状态.
	 *
	 * @param entityIds the entity ids
	 * @param currentInfo the current info
	 * @param failNotes the fail notes
	 * @throws SettlementException the settlement exception
	 * @author 046644-foss-zengbinwen
	 * @date 2012-11-7 上午9:51:08
	 */
	public void updatePayCODOfflineAntiRemittance(List<String> entityIds,
			CurrentInfo currentInfo, String failNotes)
			throws SettlementException {

		LOGGER.info("Service start,entityIds:"
				+ (entityIds == null ? null : entityIds.toString()));

		// 如果代收货款实体ID为空，则抛出异常
		if (CollectionUtils.isEmpty(entityIds)) {
			throw new SettlementException("代收货款实体ID为空");
		}

		// 如果代收货款汇款失败原因为空，则抛出异常
		if (StringUtils.isEmpty(failNotes)) {
			throw new SettlementException("代收货款汇款失败原因为空");
		}

		CODEntity entity = null;
		String status = null;

		// 循环对代收货款数据处理
		for (String entityId : entityIds) {

			entity = codCommonService.queryById(entityId);

			// 如果代收货款实体为空，抛出异常
			if (entity == null) {
				throw new SettlementException("代收货款实体为空");
			}

			// 如果代收货款状态不为退款中，则抛出异常
			status = entity.getStatus();
			if (!SettlementDictionaryConstants.COD__STATUS__RETURNED
					.equals(status)) {
				throw new SettlementException("代收货款状态不为已退款，不能进行反汇款成功操作");
			}

			// 更新代收货款状态为已退款
			entity.setStatus(SettlementDictionaryConstants.COD__STATUS__NEGATIVE_RETURN_SUCCESS);
			Date now = new Date();
			entity.setModifyTime(now);
			entity.setRefundSuccessTime(null);
			entity.setRemittanceFailNotes(failNotes);
			entity.setModifyUserName(currentInfo.getEmpName());
			entity.setModifyUserCode(currentInfo.getEmpCode());
			codCommonService.updatePayCODAntiRemittance(entity, currentInfo);
		}
		LOGGER.info("Service end.");
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
	 * Sets the bill payment service.
	 *
	 * @param billPaymentService the new bill payment service
	 */
	public void setBillPaymentService(IBillPaymentService billPaymentService) {
		this.billPaymentService = billPaymentService;
	}
}
