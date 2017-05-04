/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/action/PrintModifyWaybillAction.java
 * 
 * FILE NAME        	: PrintModifyWaybillAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
/**
 * 修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-05-30	新增	林文升	V0.1
--------------			
-------------- 			
--------------  	  	  	 
--------------
1.	SUC-505 打印运单出发联
1.1	相关业务用例
   BUC_FOSS_5.20.30_530 运单生成
1.2	用例描述
营业员选择合适的打印模板打印运单联
（一式三联：客户联(白)、财务联(红)、存根联(黄)）。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	运单已生成	
FOSS-接送货系统用例-客户上门-确认承运信息-生成运单 SUC-486
--------------
后置条件		
1.4	操作用户角色
操作用户	描述
营业部	打印运单出发联
1.5	界面要求
1.5.1	表现方式
Web页面 
1.5.2	界面原型
--------------
--------------
--------------
 --------------
--------------
--------------
打印运单出发联模板
--------------
 
打印效果
--------------
1.5.3	界面描述
界面标题：运单半信息打印预览界面
1. 界面包括填充完信息且带纸质运单背景的模板
--------------   
界面标题：运单全信息打印预览界面
1. 界面包括填充完信息且带纸质运单背景的模板
--------------
界面标题：打印效果
1.打印效果图片
--------------
1.6	操作步骤
1.6.1	打印运单
序号	基本步骤	相关数据	补充步骤
1	用户点击“打印预览”按钮		
弹出对话框，显示“已打印【次数】，
是否继续打印？” 
3	用户选择“是”		
弹出打印模板选择界面，
默认的模板填充到选择文本框里面
4	选择打印模板：
全信息模板		
填充选择的模板到选择文本框里面,打印规则SR1
5	点击“确定”按钮	
打印信息	弹出系统打印机选择界面
--------------
--------------
扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
2a	弹出时，
默认选择已经在后台设置的模板 		
--------------
1.6.2	打印预览
序号	基本步骤	相关数据	补充步骤
1	用户点击“打印预览”按钮		
弹出对话框，显示“已打印【次数】，是否继续打印？” 
2	用户选择“是”		
弹出打印模板选择界面，默认的模板填充到选择文本框里面
3	选择打印模板：全信息模板		
填充选择的模板到选择文本框里面
4	点击“确定”按钮	
打印信息	弹出系统打印预览界面，把“运单信息”（元数据），按照规则SR1，填充到选择的打印模板中。
5	点击“打印”按钮		
弹出系统打印机选择界面
--------------
--------------
--------------
扩展事件写非典型或异常操作过程
序号	扩展事件	相关数据	备注
2a	用户选择“否”		
终止打印
--------------
1.7	业务规则
序号	描述
SR1	按照打印信息所对应的数据元素
--------------
1、	托运人信息（对应数据元素：托运人信息）
托运人/公司：发货联系人
联系方式：发货人手机和电话号码，如果只有手机号码，只打印手机号码，如果只有电话号码，打印电话号码，如果两者都有，用”/”隔开，例如13916510075/020-6535678
会员卡：发货人编码
地址：发货人地址
--------------
   --------------
2、	收货人信息（对应数据元素：收货人信息）
收货人/公司：收货联系人
联系方式：收货人手机
会员卡：收货人编码
地址：收货人地址
--------------
3、	 增值业务信息（对应数据元素：增值业务信息、收款人信息）
     报价声明价值：￥符号后面显示保险价值
     代收货款：如果没有代收货款，不打印，如果有，打印退款类型+代收货款 
例如：审核退33332 
     户名：户名
     开户行：开户行
     收款账户：收款账号
--------------
4、	  服务（对应数据元素：运输信息、计费付款）
     运输方式：直接打印“整车”
     交货方式：直接打印“送货上门”
     签收单：返单类型
--------------
5、	货物基本信息（对应数据元素：货物基本信息）
货物品名：货物名称
包装：货物包装
储运事项：对内备注
件数：总件数
体积：总体积
重量：总重量
上门接货：直接打印“送货上门”
--------------
--------------
6、	费用（对应数据元素：计费付款信息）
计费重量：不需要打印
费率：费率
运费：总运费
保价费：保价费
包装费：包装费
装卸费：装卸费
--------------
费用合计：现付金额和到付金额四舍五入后取整
   --------------
打印规则为：
“现付：￥“+现付金额
现付金额转为大写+”整””
到付：￥”+到付金额
到付金额转为大写+”整”
--------------
例如：
现付：￥2851.00
贰仟捌佰伍拾元整
到付：￥3333.00
--------------
--------------
7、制单人/时间（对应数据元素：打印其他信息）
打印开单人和时间，格式为：年-月-日 例如：2012-03-05
--------------
8、	发货网点/电话（对应数据元素：部门信息）
打印出发部门名称/出发部门地址/出发部门对外电话号码
（如果没有部门对外电话，
直接把电话替换成400-830-5555） 
例子：广州白云区营业部/广州市白云区新广从路口
（白云心理医院旁）/020-140091 
62140092 62140139 62140096
--------------
9、	提货网点（对应数据元素：部门信息）
--------------
10、	打印到达部门名称/到达部门地址/到达部门对外电话号码
（如果没有部门对外电话，直接把电话替换成400-830-5555）
 例子：广州白云区营业部/广州市白云区新广从路口
 （白云心理医院旁）/020-140091 62140092 
 62140139 62140096
--------------
--------------
11、	其他（对应数据元素：部门信息、运输信息）
运输性质：直接打印整车
始发站：对于出发部门城市，例如：广州
目的站：“到达营业部”打印到达部门名称 例如：
上海派送部
--------------
--------------
1.8	数据元素
1.8.1	打印信息
1.8.1.1	托运人信息
--------------
字段名称 	说明 	数据类型	备注
发货客户	发货联系人所属公司	文本	
发货联系人	发货联系人名称	文本	
发货人手机	发货联系人手机	文本	
发货人电话	发货联系人电话	文本	
发货人地址	发货联系统人的省份城市区县详细 地址	文本	
发货人编码	客户编码	文本	
--------------
1.8.1.2	收货人信息
字段名称 	说明 	数据类型	备注
收货客户	收货联系人所属公司	文本	
收货联系人	收货联系人名称	文本	
收货人手机	收货联系人手机	文本	
收货人电话	收货联系人电话	文本	
收货人地址	收货联系统人的省份城市区县详细 地址	文本	
--------------
1.8.1.3	增值业务信息
字段名称 	说明 	数据类型	备注
保险价值	声明保险价值；	文本	
代收类型	代收货款类型，即日退和三日退；	文本	
代收货款	代收货款金额	文本	
1.8.1.4	收款人信息
字段名称 	说明 	数据类型	备注
户名	收款人户名	文本	
开户行	所属银行	文本	
收款账号	帐号	文本	
--------------
--------------
1.8.1.5	运输信息
--------------
字段名称 	说明 	数据类型	备注
到达类型	到达营业部、到达客户处	文本	
返单类别	同运单界面的签收单返回类型；	文本	
--------------
1.8.1.6	货物基本信息
--------------
字段名称 	说明 	数据类型	备注
货物名称	货物名称	文本	
总重量	总重量	文本	
总体积	总体积	文本	
总件数	总件数	文本	
货物包装	货物包装	文本	
是否上门接货	货物是否上门接货	布尔值	
--------------
1.8.1.7	计费付款
--------------
字段名称 	说明 	数据类型	备注
付款方式	现金、到付、月结；	文本	
计费费率	计费的费率	文本	
总运费	运单运费	文本	
其他费用	综合服务费、燃油附加费等	文本	
预付	预付费用	文本	
到付	到付费用	文本	
--------------
1.8.1.8	部门信息
字段名称 	说明 	数据类型	备注
部门名称	现金、到付、月结；	文本	
部门地址	计费的费率	文本	
部门对外电话	运单运费	文本	
城市	所在城市	文本	
--------------
--------------
1.8.1.9	打印其他信息
字段名称 	说明 	数据类型	备注
制单人	开单人	文本	
开单日期	开单日期	文本	
--------------
--------------
--------------
--------------
使用量	每天处理的运单约为9000单
2012年全网估计用户数	营业员数量约10000名
响应要求（如果与全系统要求 不一致的话）	
一般要求
使用时间段	营业部上班时间
高峰使用时间段	无
--------------
--------------
1.9	接口描述：
接口名称 	对方系统（外部系统或内部其他模块）	
接口描述
--------------
--------------
--------------
--------------
 */
/**--------------------------------------
 * 1.	纸包装件数默认显示数值等于货物总件数，
 * 营业员可以修改各包装的件数；
 * 系统自动在录入下一个包装前计算显示剩余未录入的件数，
 * 例：某票货物为50件，则自动显示纸包装50，
 * 在营业员修改纸包装为20时，木包装自动显示为30，
 * 在营业员修改木包装为10时，
 * 纤包装自动显示为20，该过程中未录入包装数值的默认显示为0；
2.	货物包装总件数小于等于货物总件数；
 */
/**--------------------------------------
 * 1.	离线开单时，不录入代打木架信息；
 * 当木包装件数大于等于1时，系统不提示任何信息，
 * 也不弹出代打木架录入界面；
2.	离线开单的代打木架信息在离线开单提交时，
系统校验提醒；
 */
/**
 * 1.	运输类型为汽运时，货物类型为唯一选择项；即，
 * 非A即B；默认不可勾选，
 * 只有当走货路由经过特定的城市时需要录入货物类型,
 * 特定城市可在系统中进行配置；
2.	运输类型为空
默认显示为普货，目前只有这一个分类，该类型可做配置；
--------------------------------------
 */
/**--------------------------------------
 * 1.	系统根据货物名称匹配生成的勾选贵重物品，
 * 营业员不可去掉勾选；
2.	营业员也可以主动勾选贵重物品；
3.	件数等于1、体积小于等于0.05个方、
报价声明价值大于等于10000元，为贵重物品；
系统自动勾选贵重物品，
营业员可修改是否勾选；
4.	件数大于等于2时，平均体积（体积/件数）
小于等于0.5方，平均声明价值（保价声明价值/件数）
大于等于10000元，为贵重物品；
系统自动勾选贵重物品，
营业员可修改是否勾选；
1.	营业部开单时按打完木架后的包装开，
即包装中含“木架/木箱”，
开单件数为货物打木架/木箱前的实际件数（防止丢货），
尺寸和重量按照以下公式计算：
1.1	整票货物代打时：
开单体积=代打货物体积*1.4；
开单重量=所有货物重量+代打货物体积*42；
1.2	部分货物代打时，
开单体积=代打货物体积*1.4+未打木架货物体积；
开单重量=所有货物重量+代打货物体积*42。
即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，
重量另加，单票中未打木架的货物的体积和重量不变；
1.3	例如：一票货物需全部代打，
货物体积为1个方，重量为100KG，
则开单体积为1.4个方，
开单重量为100+1*42=142KG，
收取客户包装费为150*1.4=210元；
1.4	需要加托时，仍按照50元/个另外收取费用，
托的重量和体积不再另加；
营业部不需要再更改由于打木架引起重量和体积的变化；
2.	开单件数为代打木架前货物实际件数，
包装为打木架后的包装，
打木架后件数发生变化后，
需及时更改件数；
--------------------------------------
 */

/**--------------------------------------
 * 每天处理的运单约为1000000单
营业员数量约10000名
系统一般要求
营业部、集中开单小组上班时间
营业部：16：00-20：00
集中开单小组：21：00-次日4：00
 */

/**--------------------------------------
 * 本界面为录入增值服务信息。
界面主要分为三个部分：
录入增值服务、
录入包装费、
查询其它费用。
1.	录入增值服务
录入增值服务分为两个部分：
录入增值服务信息界面、
录入其它费用列表；
1.1	录入增值服务信息界面
录入增值服务信息界面包括：
1.1.1	保价声明价值；
1.1.2	保价费率：
保价费率可由基础资料配置，
可按出发城市区域，
出发营业部等；
1.1.3	保价费；
1.1.4	代收货款；
1.1.5	代收费率；
1.1.6	代收手续费；
1.1.7	退款类型：
包括三日退、
退日退、
审核退，
默认三日退；
1.1.8	包装费；
1.1.9	装卸费；
1.1.10	送货费；
1.1.11	其它费用合计；
1.1.12	返单类别：
包括无需返单、
原件签收单返回、
传真件签收单返回、
扫描件返回，
默认无需返单；
1.1.13	预付费保密；
1.2	录入其它费用列表
录入其它费用列表包括：
1.2.1	费用名称；
1.2.2	费用金额；
1.2.3	新增其它费用：
功能按钮；
1.2.4	删除其它费用：
功能按钮；
2.	录入包装费
其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“录入包装费”；
3.	查询其它费用
其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“查询其它费用”；
--------------------------------------
 */
/**
 * /**--------------------------------------
 * 1.	若货物为违禁品，
 * 	则系统自动提示“货物为违禁品，不可开单！”；
 2.	若货物为拒收品，
 则系统自动提示“货物为拒收品，不可开单！”；
 3.	若货物为贵重物品，
 则系统自动勾选“贵重物品”，
 且不可修改；
 4.	若货物为限保物品，
 则系统自动限定保价金额，且不可修改，
 并提示“货物为限保物品”；
 5.	违禁品、拒收品、
 贵重物品、限保物品（含保价金额上限）
 具体类型可在系统中进行配置；
 1.	货物重量单位为千克
 */
/**--------------------------------------
 * 1.	货物尺寸为计算器输入，
 * 显示为输入文本；
2.	尺寸计算单位为厘米，
尺寸计算出数据后转换单位为立方米后，
在货物体积中显示数据；例如：
尺寸录入为：50*50*20，
则体积显示数据为：0.05；
 */
/**--------------------------------------
 * 1.	货物体积单位为立方米；
2.	营业员可以修改通过尺寸计算器
计算得出的体积数据；
3.	系统设置货物重量体积比区间值（该值由基础资料配置），
在运单提交时，
系统自动对重量体积比进行校验：
即重量体积比X=重量/体积；
3.1	当X不在设置的区间中，
弹出提示“请确认录入的重量体积是否准确！”；
（该弹窗有两个按钮：确定、取消）点击确定时，
弹出离线开单确认运单信息界面；点击取消，
点返回运单录入界面；
3.2	当X在区间中，无提示；
直接进入确认运单信息界面；
--------------------------------------
 */
/**--------------------------------------
 * 1.	纸包装件数默认显示数值等于货物总件数，
 * 营业员可以修改各包装的件数；
 * 系统自动在录入下一个包装前计算显示剩余未录入的件数，
 * 例：某票货物为50件，则自动显示纸包装50，
 * 在营业员修改纸包装为20时，木包装自动显示为30，
 * 在营业员修改木包装为10时，
 * 纤包装自动显示为20，该过程中未录入包装数值的默认显示为0；
2.	货物包装总件数小于等于货物总件数；
 */
/**--------------------------------------
 * 1.	离线开单时，不录入代打木架信息；
 * 当木包装件数大于等于1时，系统不提示任何信息，
 * 也不弹出代打木架录入界面；
2.	离线开单的代打木架信息在离线开单提交时，
系统校验提醒；
 */
/**
 * 1.	运输类型为汽运时，货物类型为唯一选择项；即，
 * 非A即B；默认不可勾选，
 * 只有当走货路由经过特定的城市时需要录入货物类型,
 * 特定城市可在系统中进行配置；
2.	运输类型为空
默认显示为普货，目前只有这一个分类，该类型可做配置；
--------------------------------------
 */
/**--------------------------------------
 * 1.	系统根据货物名称匹配生成的勾选贵重物品，
 * 营业员不可去掉勾选；
2.	营业员也可以主动勾选贵重物品；
3.	件数等于1、体积小于等于0.05个方、
报价声明价值大于等于10000元，为贵重物品；
系统自动勾选贵重物品，
营业员可修改是否勾选；
4.	件数大于等于2时，平均体积（体积/件数）
小于等于0.5方，平均声明价值（保价声明价值/件数）
大于等于10000元，为贵重物品；
系统自动勾选贵重物品，
营业员可修改是否勾选；
1.	营业部开单时按打完木架后的包装开，
即包装中含“木架/木箱”，
开单件数为货物打木架/木箱前的实际件数（防止丢货），
尺寸和重量按照以下公式计算：
1.1	整票货物代打时：
开单体积=代打货物体积*1.4；
开单重量=所有货物重量+代打货物体积*42；
1.2	部分货物代打时，
开单体积=代打货物体积*1.4+未打木架货物体积；
开单重量=所有货物重量+代打货物体积*42。
即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，
重量另加，单票中未打木架的货物的体积和重量不变；
1.3	例如：一票货物需全部代打，
货物体积为1个方，重量为100KG，
则开单体积为1.4个方，
开单重量为100+1*42=142KG，
收取客户包装费为150*1.4=210元；
1.4	需要加托时，仍按照50元/个另外收取费用，
托的重量和体积不再另加；
营业部不需要再更改由于打木架引起重量和体积的变化；
2.	开单件数为代打木架前货物实际件数，
包装为打木架后的包装，
打木架后件数发生变化后，
需及时更改件数；
--------------------------------------
 */

/**--------------------------------------
 * 每天处理的运单约为1000000单
营业员数量约10000名
系统一般要求
营业部、集中开单小组上班时间
营业部：16：00-20：00
集中开单小组：21：00-次日4：00
 */

/**--------------------------------------
 * 本界面为录入增值服务信息。
界面主要分为三个部分：
录入增值服务、
录入包装费、
查询其它费用。
1.	录入增值服务
录入增值服务分为两个部分：
录入增值服务信息界面、
录入其它费用列表；
1.1	录入增值服务信息界面
录入增值服务信息界面包括：
1.1.1	保价声明价值；
1.1.2	保价费率：
保价费率可由基础资料配置，
可按出发城市区域，
出发营业部等；
1.1.3	保价费；
1.1.4	代收货款；
1.1.5	代收费率；
1.1.6	代收手续费；
1.1.7	退款类型：
包括三日退、
退日退、
审核退，
默认三日退；
1.1.8	包装费；
1.1.9	装卸费；
1.1.10	送货费；
1.1.11	其它费用合计；
1.1.12	返单类别：
包括无需返单、
原件签收单返回、
传真件签收单返回、
扫描件返回，
默认无需返单；
1.1.13	预付费保密；
1.2	录入其它费用列表
录入其它费用列表包括：
1.2.1	费用名称；
1.2.2	费用金额；
1.2.3	新增其它费用：
功能按钮；
1.2.4	删除其它费用：
功能按钮；
2.	录入包装费
其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“录入包装费”；
3.	查询其它费用
其界面和界面和需求详见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中“查询其它费用”；
--------------------------------------
 */
/**
 * 录入保价声明价值
 * 1.	系统根据录入的保价声明价值和系统自动获得的保价费率计算保价费；
 * 2.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR1、SR3；
 * 录入代货货款
 * 1.	系统根据录入的代收货款和系统自动获得的代收费率计算代收手续费；
2.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR2、SR3；
录入退款类型
1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR2、SR4；
2.	规则-请参见系统规则SR1；
录入包装费：焦点移至包装费时，
自动弹出包装费录入界面
1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR5；
录入装卸费
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则SR6；
 * 录入送货费
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-
 * 确认承运信息-录入增值服务信息”中系统规则SR7；
 * 录入其它费用：点击新增其它费用，增加新的其它费用列后，点击放大镜，弹出其它费用查询列表；
 * 运单生成-确认承运信息-录入增值服务信息”中系统规则SR8；
 * 录入返单类别
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例
 * -运单生成-确认承运信息-录入增值服务信息”中系统规则SR9；
 * 录入预付费保密
 * 1.	规则-请参见系统用例“DP-FOSS-接送货系统用例-运单生成-确认承运信息-录入增值服务信息”中系统规则
 * SR10,SR11,SR12；
 * 1.	离线开单中不生成代收货款退款收银人信息，
 * 且不录入；
 * 其代收货款的退款收银人信息在有线后提交运单时录入；
 * 字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
 * 保价声明价值		数字		8	是	
保价费率		数字、符号		8		
保价费		数字		8		系统自动生成
代收货款		数字		8	是	
代收费率		数字、符号		8		
代收手续费		数字		8		系统自动生成
退款类型		下拉框		3	是	
包装费		数字		8	是	
装卸费		数字		8	是	
送货费		数字		8	是	
其它费用合计		数字		8	是	
返单类别		下拉框		10	是	
预付费保密		复选框		1	否	
--------------------------------------
使用量	每天处理的运单约为1000000单
2012年全网估计用户数	营业员数量约10000名
响应要求（如果与全系统要求 不一致的话）	系统一般要求
使用时间段	营业部、集中开单小组上班时间
高峰使用时间段	营业部：16：00-20：00
集中开单小组：21：00-次日4：00
--------------------------------------
1.10	接口描述
输入的尺寸可以进行加减例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5*
体积为空时，录入尺寸后，
填充体积。
当尺寸修改时，
体积随之变处。
当修改体积时，
尺寸不变化。 

货物包装总件数小于等于货物总件数，
如果大于总件数，
提示：“包装件数不能大于总件数”
--------------------------------------
贵重物品规则变更： 
1.修改现有系统对"贵重物品"的判断条件如下： 
a. 单票单件货物，
体积≤0.05立方且保价声明价值≥1万元； 
b. 单票多件货物，
平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
（注：平均单件体积=开单总体积÷开单件数，
平均单件保价=开单保价声明价值÷开单件数） 
c. 高保价货物，
单票货物保价≥10万元； 
满足以上任意一个条件时，
系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，
且为灰色，不可编辑；不满足以上条件时，
该复选框为可编辑状态，
用户可根据实际情况自行选择是否勾选； 
2.若"贵重物品"复选框被勾选，
则在【储运注意事项】中自动加入提示记录"贵重物品"字段，
若该货物为贵重物品时，
"储运注意事项"中信息显示优先级为：贵重物品＞其他； 
--------------------------------------
尺寸录入框可以不录入件数，
如件数为1时。
因此录入框即可以输入10*10*10，
又可以输入10*10*10*1
进入该页面时，木架和木箱都赋初始值0，
仍保持必填，操作员根据实际情况改，
选择木架或木箱或两者都选择后则该值不能为0
--------------------------------------
	ISSUE-1105
1.运单开单时，货物的件数、
重量及体积文本框是必填项，
默认值为空； 
2.件数只能是大于等于1的整数，
重量及体积只能大于0。
体积、重量这种过程数据保留两位小数
--------------------------------------
前置条件		1.	贵重物品基础资料
2.	违禁物品基础资料
3.	分拣目的站基础资料
后置条件		1.	打木架录入
2.	运单收银
--------------------------------------
营业员点击运单开单，进入运单开单界面。
本界面为录入货物信息。
界面主要分为两个部分：录入货物信息、代收木架信息录入。
1.	录入货物信息：
录入信息包括：货物名称、总件数、
总重量、货物尺寸、
总体积、货物包装、
货物类型、是否贵重物品；
1.1	货物名称：
货物的名称；
1.2	总件数：
收货时货物的总件数；
1.3	总重量：
收货时货物的总重量；
1.4	货物尺寸：
收货时货物的尺寸；
1.5	总体积：
收货时货物的总体积；
1.6	货物包装：
货物的包装，
其包装总件数等于打木架和打木箱后的总件数；
1.7	货物类型：
1.7.1	运输类型为汽运时，
货物类型为公司规则的分拣条件的类型， 
为唯一选择框，包括：A、B；
1.7.2	运输类型为空运时，
货物类型为下拉选择框，
默认显示为普货，
目前只这一个分类，
该类型可做配置；
1.8	是否贵重物品：
货物是否为公司规则的贵重物品，
为选择框；
2.	代打木架信息录入；
当包装木的件数大于等于1时，
自动弹出对话框：“是否收入代打木架？”，是，
则进入场代打木架信息录入界面；
录入信息包括：代打部门、打木架货物件数、
代打木架要求、打木架货物尺寸、打木架货物体积、
打木箱货物件数、代打木箱要求、打木箱货物尺寸、打木架货物体积；
2.1	代打部门：
代打木箱或木架的部门名称；
2.2	打木架货物件数：
需要代打木架的货物件数；
2.3	代打木架要求：
代打木架的要求，
如“1和2合打，3、4分开打”；
2.4	打木架货物尺寸：
需要代打木架的货物尺寸；
2.5	打木架货物体积：
需要代打木架的货物体积；
2.6	打木箱货物件数：
需要代打木架的货物件数；
2.7	代打木箱要求：
代打木箱的要求，如“5和6合打，加一个木托”；
2.8	打木箱货物尺寸：
需要代打木箱的货物尺寸；
2.9	打木箱货物体积：
需要代打木箱的货物体积；
2.10	功能按钮：
确定、关闭、重置；k
--------------------------------------------------
1.6.1	录入货物信息
序号	基本步骤	相关数据	补充步骤
1	录入货物名称		
	1.	系统自动匹配违禁品、贵重物品、限保物品基础资料，生成规则；
2.	规则-请参见系统规则SR1；
2	录入总件数		
	规则-请参见系统规则SR2
3	录入总重量		
	1.	规则-请参见系统规则SR2、SR11；
4	录入货物尺寸		
	1.	规则-请参见系统规则SR3、SR4；
5	录入总体积		
	1.	规则-请参见系统规则SR2、SR3、SR4、SR10；
6	录入货物包装		
	1.	当木包装大于等于1时，
		系统校验出发城市和对应目的站的走货路由中是否有有打木架功能的部门，
		若有，则提醒营业员“是否收入代打木架？”，是，
		则进入场代打木架信息录入界面”，
		并显示第一个有打木架功能的部门名称；
		若走货路由中无有打木架功能的部门，
		则不提示“是否收入代打木架？”；
2.	规则-请参见系统规则SR5、SR6、SR10；
7	录入货物类型		
	1.	规则-请参见系统规则SR7；
8	录入是否贵重货物		
	1.	规则-请参见系统规则SR8；
9       录入打木箱货物体积		
	1.	规则-请参见系统规则SR3、SR4、SR10；
	-----------------------------------
	1.	若货物为违禁品，
	则系统自动提示“货物为违禁品，不可开单！”；
2.	若货物为贵重物品，
则系统自动勾选“贵重物品”，
且不可修改；
3.	若货物为限保物品，
则系统自动限定保价金额，
且不可修改，
并提示“货物为限保物品”；
4.	违禁品、
拒收品、
贵重物品、
限保物品（含保价金额上限）
具体类型可在系统中进行配置；
1.	货物重量单位为千克；
2.	运单开单时，
货物的件数、
重量及体积文本框是必填项，
默认值为空； 
3.	件数只能是大于等于1的整数，
重量及体积只能大于0。
4.	体积、
重量这种过程数据保留两位小数
1.	尺寸录入文本框，
支持'长*宽*高*件数+长*宽*高*（即多个尺寸相加）的计算；
2.	如果件数是1，
则不强制在录入时要“*1”；
3.	货物尺寸为计算器输入，
输入的尺寸可以进行加减，
例如：1*1*1*5+2*2*2*3-0.5*0.5*0.5，
显示为输入文本；
4.	尺寸计算单位为厘米，
尺寸计算出数据后转换单位为立方米后，
在货物体积中显示数据；
例如：尺寸录入为：50*50*20（其中20为件数），
则体积显示数据为：0.05； 
5.	体积为空时，
录入尺寸后，填充体积。
当尺寸修改时，体积随之变处。
当修改体积时，尺寸不变化。
6.	体积初始值为“0”，
仍保持必填，
操作员根据实际情况改，
选择木架或木箱或两者都选择后则该值不能为0

1.	货物体积单位为立方米；
2.	营业员可以修改通过尺寸计算器计算得出的体积数据；
3.	系统设置货物重量体积比区间值（该值由基础资料配置），
在运单提交时，
系统自动对重量体积比进行校验：即重量体积比X=重量/体积； 
3.1	当X不在设置的区间中，
弹出提示“请确认录入的重量体积是否准确！”；
（该弹窗有两个按钮：确定、取消）点击确定时，
弹出确认运单信息界面；点击取消，点返回运单录入界面；
3.2	当X在区间中，无提示；
直接进入确认运单信息界面；
4.	录入重量体积后，
系统校验单票的重量体积及单件（平均单件）
的重量体积是否满足“修改-查询行政组织业务属性”
基础资料中的单票和单件重量体积限制；
只要该四项中有一项不满足，
则提示“XX超出提货网点限制，
请重新选择提货网点！”； 
1.	货物包装总件数小于等于货物总件数，
如果大于总件数，
提示：“包装件数不能大于总件数”；
1.	当木包装件数大于等于1时，
系统校验出发城市和对应目的站的走货路由中是否有有打木架功能的部门，
若有，则提醒营业员“是否收入代打木架？”，是，
则进入场代打木架信息录入界面”，
并显示第一个有打木架功能的部门名称；
若走货路由中无有打木架功能的部门，
则不提示“是否收入代打木架？”；
2.	当走货路由中有有打木架功能的部门为多个时，
只显示系统路由中第一有有打木架功能的部门，
且不可修改；
1.	运输类型为汽运时，
货物类型为唯一选择项；
即，非A即B；默认不可勾选，
只有当走货路由经过特定的城市时需要录入货物类型,
特定城市可在系统中进行配置；
2.	运输类型为空运时，
货物类型为下拉选择框，
默认显示为普货，
目前只有这一个分类，
该类型可做配置；
1.	贵重物品判断规则： 
a. 单票单件货物，
体积≤0.05立方且保价声明价值≥1万元； 
b. 单票多件货物，
平均单件体积≤0.2立方且平均单件保价声明价值≥1万元；
（注：平均单件体积=开单总体积÷开单件数，
平均单件保价=开单保价声明价值÷开单件数） 
c. 高保价货物，
单票货物保价≥10万元； 
满足以上任意一个条件时，
系统将判定该票货物为贵重物品并自动勾选"贵重物品"复选框，
且为灰色，
不可编辑；
不满足以上条件时，
该复选框为可编辑状态，
用户可根据实际情况自行选择是否勾选； 
2.	若"贵重物品"复选框被勾选，
则在【储运注意事项】中自动加入提示记录"贵重物品"字段
，若该货物为贵重物品时，
"储运注意事项"中信息显示优先级为：
贵重物品＞其他；
======================================
1.	录入的打木架货物件数和打木箱件数之和必须大于等于录入的木包装件数；
2.	系统默认标签流水号前X的货物为需要代打木架货物，
X等于录入的“打木架货物件数”和“打木箱货物件数”之和；
3.	营业员在打印标签时，
按流水号先贴要打木架或打木箱的货物；
1.	录入的打木架货物体积和打木箱货物体积之和乘以1.4必须小于等于货物总体积；
该1.4为打木架体积计算系统，可配置；
2.	营业部开单时按打完木架后的包装开，
即包装中含“木架/木箱”，
开单件数为货物打木架/木箱前的实际件数（防止丢货），
尺寸和重量按照以下公式计算：
1.1	整票货物代打时：开单体积=代打货物体积*1.4；
开单重量=所有货物重量+代打货物体积*42；
1.2	部分货物代打时，
开单体积=代打货物体积*1.4+未打木架货物体积；
开单重量=所有货物重量+代打货物体积*42。
即：营业部按照代打货物未打木架之前体积的1.4倍来开单收费，
重量另加，
单票中未打木架的货物的体积和重量不变；
1.3	例如：一票货物需全部代打，货物体积为1个方，
重量为100KG，则开单体积为1.4个方，
开单重量为100+1*42=142KG，
收取客户包装费为150*1.4=210元；
1.4	需要加托时，仍按照50元/个另外收取费用，
托的重量和体积不再另加；
营业部不需要再更改由于打木架引起重量和体积的变化；
3.	打木架要在“对内备注”中备注“代打木架/木箱”，
特殊要求（特别是合打情况）必须在对内备注和A4纸上都注明，
例如：货物1、2、3合打成一件等；
4.	开单件数为代打木架前货物实际件数，
包装为打木架后的包装，打木架后件数发生变化后，
需及时更改件数；
=======================================
1.8.1	录入货物信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
货物名称		文本		100	是	
货物总件数		数字		4	是	
货物总重量		数字		8	是	
货物尺寸		数字、符号		500	否	
货物总体积		数字		8	是	
货物包装		数字、文本		4	是	
货物类型		选择框		10	否	
贵重物品		选择框		1	否	
=======================================
1.8.2	代打木架信息录入
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
代打部门		文本		20	是	
打木架货物件数		数字		4	是	
代打木架要求		文本		100	是	
打木架货物尺寸		数字、符号		500	否	
打木架货物体积		数字		8	是	
打木箱货物件数		数字		4	是	
代打木箱要求		文本		100	是	
打木箱货物尺寸		数字、符号		500	否	
打木箱货物体积		数字		8	是	
==========================================
使用量	每天处理的运单约为1000000单
2012年全网估计用户数	营业员数量约10000名
响应要求（如果与全系统要求 不一致的话）	系统一般要求
使用时间段	营业部、集中开单小组上班时间
高峰使用时间段	营业部：16：00-20：00
集中开单小组：21：00-次日4：00
============================================
根据电话号码带出客户信息，
仅在客户名称为空的前提下才会有效。
即意味着由其他方式带出客户信息后，
修改电话号码并不会触发重新的检索。
==========================================
1.	使用电话号码和手机进行匹配，
如果是唯一匹配一条CRM客户信息时，
填充客户编码、客户名称、联系人、地址，
如果有多条需弹出选择框进行选择， 
如果查询不到CRM客户信息时，
使用FOSS三个月运单历史记录中的收货信息查询，
唯一匹配一条进行填充收货客户信息，多条进行弹窗选择，
如果都查询不到，不做其他操作
2.	但是对于电话号码匹配，只有当发货人手机、
客户名称为空时，才会用电话号码检索并弹窗显示使用手机号码、
电话号码、客户名称弹出选择框选择记录后覆盖原先记录
3.	修改联系人时，需要清空客户名称，
当清空客户名称时，会同时删除客户编码
4.	使用手机号码、
电话号码弹出选择框选择记录后覆盖原先记录
==========================================
身份证号、客户编码、客户名称、
联系人编码可以查询到全公司的客户
==========================================
本界面分为两个界面：录入收货客户信息、选择收货客户。
1.	录入收货客户信息：
界面为信息录入界面：包括：手机、电话、
发货收货联系人（发货收货部门）、发货收货人地址；
1.1	手机：发货收货人手机号码；
1.2	电话：发货收货人电话号码；
1.3	收货联系人（收货部门）：
收货客户的客户姓名，
当“运单开单”中的“开单提货方式”
为“内部带货自提”时，
“收货联系人”字段更改为“收货部门”；
1.4	收货人地址：收货客户的详细联系地址，
支持国家行政区域自动过滤；
1.5	客户名称
1.6	客户编码
2.	选择收货客户界面：
界面为选择客户信息界面：包括两部分：
客户信息列表区域、功能按钮区域；
2.1.	客户信息列表区域：
包括：联系人、手机、电话、
地址（规范化地址和详细地址）；
2.2.	功能按钮区域：
包括：确定、取消；
3.	    选择热门城市界面
   界面信息包含人热门城市
4.	选择省份界面
   界面信息包含省份
5.	选择城市界面
           界面信息保护城市
6.	选择区县界面
           界面信息保护区县
==============================================
1.6.1	录入
序号	基本步骤	相关数据	补充步骤
1	录入手机号码		
1.	系统自动查询CRM系统中对应手机号码绑定的客户信息，
如果有弹窗，
弹窗操作见扩展1a，和如果没有，
弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
并弹窗，弹窗操作见扩展1b；
2.	规则-请参见系统规则SR1、SR5、SR6；
2	录入电话号码		
1.	如果手机号码没有填写，
系统自动查询CRM系统中对应电话号码绑定的客户信息，
如果有弹窗，
弹窗操作见扩展2a，
如果没有，
弹出FOSS历史开单记录中对应手机号码绑定的客户信息，
并弹窗，弹窗操作见扩展2b；
1.	1、系统自动查询CRM系统中对应电话号码绑定的
客户信息和FOSS历史开单记录中对应手机号码绑定的客户信息，
并弹窗，弹窗操作参见扩展2a；
2.	规则-请参见系统规则
SR1、SR5 、SR6；
------------------------------------
3	录入收货联系人（收货部门）		
1.	规则-请参见系统规则SR2、SR3；
4	录入收货人地址		
1.	提供下拉框选择输入，
系统自动过滤输入的行政区下一级行政级的字段；
2.	地址在系统后台通过GIS系统进行匹配，
如果是禁行区域，地址颜色为红色，
如果是进仓区域，地址颜色为黄色
3.	规则-请参见系统规则SR4；
===================================
 */
package com.deppon.foss.module.pickup.changingexp.client.action;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.pickup.changingexp.client.service.WaybillRfcServiceFactory;
import com.deppon.foss.module.pickup.changingexp.client.ui.dialog.RFCPrintChoiceDiaglogNew;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillInfoVo;
import com.deppon.foss.module.pickup.changingexp.client.vo.WaybillRfcPrintVo;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillRfcConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDetailDto;
import com.deppon.foss.print.jasper.JasperContext;
import com.deppon.foss.print.jasper.JasperPrintManager;

/**
 * 更改单打印触发操作
 * @author foss-jiangfei
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-jiangfei,date:2012-10-23 下午3:39:06,content:
 * </p>
 * 
 * 用途：
 * 用户提交变更运单申请后，打印变更单供客户签字。
 * 前置条件：
 * 1．	修改运单信息，提交运单变更申请
 * 操作：
 * 运单变更申请员 打印运单变更信息
 * =================================
 * 变更单打印模板分为：
 * 更改单、
 * 转运单、
 * 返货单三个不同模板。 
 * 1. 更改单打印模板：该模板适用于非转货、
 * 非返货的运单变更。
 * 界面主要包含六大区域，
 * 主要为变更原因信息区域、
 * 货物基本信息区域、
 * 变更信息区域、 
 * 费用明细区域、
 * 费用合计区域、
 * 签字区域。
 * ====================================
 * 1)	变更原因信息区域：
 * 单号：变更运单对应的单号，
 * 数据来自变更运单界面“单号”对应的信息。
 * 更改原因：运单信息变更的原因，
 * 数据来自变更运单界面“变更原因”对应的信息。
 * 申请部门：运单变更申请部门。
 * 申请人：运单变更申请操作用户。
 * 申请时间：提交运单变更申请的时间。
 * 2)	货物基本信息区域：
 * 货物名称：更改后的货物名称。
 * 包装：更改后的包装名称。
 * 件数：更改后的件数。
 * 重量：更改后的重量。
 * 体积：更改后的体积。
 * 尺寸：更改后的尺寸。
 * 3)	变更信息区域：
 * 变更项：变更的项目。
 * 变更前信息：
 * 变更项在变更之前对应的运单信息。
 * 
 * 变更后信息：
 * 变更项在变更之后对应的运单信息。
 * 总费用差额：
 * 
 * 总费用变更之后与变更之前的差额。
 * 4)	签字区域：
 * 经理签字、
 * 客户签字、
 * 日期。
 * =========================================
 * 2. 转运单打印模板：该模板适用于转运运单变更。
 * 界面主要包含六大区域，
 * 主要为转货原因信息区域、
 * 货物基本信息区域、
 * 变更信息区域、
 * 费用明细区域、
 * 费用合计区域、
 * 签字区域。
 * 1)	转运信息区域：
 * 单号：变更运单对应的单号，
 * 数据来自变更运单界面“单号”对应的信息。
 * 转运原因：客户转运货物的原因，
 * 数据来自变更运单界面“变更原因”对应的信息。
 * 转运目的站：
 * 数据来自起草转运单时录入的转运目的站。
 * 
 * 转运提货网点：
 * 数据来自起草转运单时录入的转运提货网点。
 * 
 * 转运运输性质：
 * 数据来自起草转运单时录入的转运运输性质。
 * 
 * 申请部门：运单变更申请部门。
 * 申请人：运单变更申请操作用户。
 * 申请时间：提交转运单申请的时间。
 * 2）其它信息区域与更改单打印模板一致。
 * =================================
 * 3. 返货单打印模板：该模板适用于返货运单变更。
 * 界面主要包含六大区域，
 * 主要为返货原因信息区域、
 * 货物基本信息区域、
 * 变更信息区域、
 * 费用明细区域、 
 * 费用合计区域、
 * 签字区域。
 * =======================
 * 1)	返货信息区域：
 * 单号：
 * 变更运单对应的单号，
 * 数据来自变更运单界面“单号”对应的信息。
 * 
 * 返货原因：
 * 客户返回货物的原因，
 * 数据来自变更运单界面“变更原因”对应的信息。
 * 
 * 返货目的站：
 * 数据来自起草转运单时录入的返货目的站。
 * 
 * 返货提货网点：
 * 数据来自起草转运单时录入的返货提货网点。
 * 
 * 返货运输性质：
 * 数据来自起草转运单时录入的返货运输性质。
 * 
 * 申请部门：
 * 运单变更申请部门。
 * 
 * 申请人：
 * 运单变更申请操作用户。
 * 
 * 申请时间：提交返货单申请的时间。
 * 2）其它信息区域与更改单打印模板一致。
 * ===============================
 * 其中变更信息区域明细列表包含：
 * “变更项”列、“变更前信息”列、“变更后信息”列、总费用差额。
 * 变更项：
 * 运单变更界面信息发生变化的数据元素名称。
 * 
	变更前信息：
	变更项在变更之前对应的运单信息。
	
	变更后信息：
	变更项在变更之后对应的运单信息。
	
	总费用差额：
	总费用变更之后与变更之前的差额。
	
	变更项业务规则：
	1.	 “变更项”列显示为变更的运单项名称，
	“变更前信息”列显示变更项在变更前对应的运单信息，
	“变更后信息”列显示变更项在变更后对应的运单信息。
	如是转运或返货变更：
	“目的站”变更项对应的变更前信息为原目的站，
	变更后信息为转运/返货目的站；
	“提货网点”变更项对应的变更前信息为原提货网点，变更后信息为转运/返货提货网点。
	2.	对于“上门接货”、“预付费保密”为复选框的变更项，
	变更前后对应的信息根据复选框勾选与否显示为是或否；
	3.	若变更项变更前信息或变更后信息为空，
	则“变更前信息”或“变更后信息”列显示为:—；
	4.	以下运单项不列入变更项：计费类型、计费重量、费率、
	保价费率、代收费率、装卸费、其他费用（总金额）、转运运输性质、
	转运配载类型、转运预配航班、转运计费类型、转运费率、
	返货运输性质、返货计费类型；
	5.	变更项行显示顺序为：
	运单基础信息变更项→发货客户信息变更项→收货客户信息变更项→运输信息变更项→
	货物信息变更项→增值业务信息变更项→详细计价信息变更项→计费付款信息变更项；
	6.	“变更前信息”与“变更后信息”列显示内容不能相同。
	7.	总费用差额=变更后(到付金额+现付金额)-变更前（到付金额+现付金额），
	显示格式为：总费用差额：￥  元。
 * 
 * @date 2012-10-23 下午3:39:06
 * @since
 * @version
 */
public class PrintModifyWaybillAction implements IButtonActionListener<RFCPrintChoiceDiaglogNew> {

	// 日志
	private static final Log LOG = LogFactory.getLog(PrintModifyWaybillAction.class);
	//默认半打行数
	public static final int DEFAULT_HALF_NUM = 12;

	/**
	 * RFCPrintChoiceDiaglogNew ui object
	 */
	private RFCPrintChoiceDiaglogNew ui;

	/**
	 * 国际化
	 */
	private II18n i18n = I18nManager.getI18n(PrintModifyWaybillAction.class);

	/**
	 * 打印触发方法
	 * 	 * <p>
	 * </p>
	 * ===================================
	 * 变更单打印模板分为：
	 * 更改单、
	 * 转运单、
	 * 返货单三种类型。
	 * 
	 * 变更类型为“更改”的运单变更对应的打印模板为更改单；
	 * 变更类型为“转运”的运单变更对应的打印模板为转运单；
	 * 变更类型为“返货”的运单变更对应的打印模板为返货单。
	 * ===============================
	 * 打印业务规则： 1. 单号：运单变更对应的单号； 
	 * 2. 更改原因：提交运单变更时录入的变更原因； 
	 * 3. 申请部门：起草运单变更的部门； 4.
	 * 申请人：
	 * 起草运单变更的用户；
	 *  
	 * 5. 申请时间：
	 * 提交运单变更申请的时间； 
	 * 
	 * 6. 转运/返货目的站：
	 * 起草转运单/返货单时录入的转运/返货目的站； 
	 * 
	 * 7.转运/返货提货网点：
	 * 起草转运单/返货单时录入的转运/返货提货网点；
	 * 
	 * 8. 转运/返货运输性质：
	 * 起草转运单/返货单时录入的转运/返货运输性质； 
	 * 
	 * 9.货物基本信息：
	 * 运单变更提交时对应的数据； 
	 * 
	 * 10. 变更信息：
	 * 业务规则及其显示模式参见系统用例SUC-490显示运单变更明细。
	 * =====================================
	 * @author foss-jiangfei
	 * @date 2012-10-23 下午3:41:06
	 * @param e
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			// 获得打印上下文
			JasperContext jctx = new JasperContext();
			jctx.setClassLoader(this.getClass().getClassLoader());
			//获得打印变更bean
			WaybillRfcPrintVo resourceBean = getWaybillRfcPrintVo();
			//得到打印变更bean 里的运单信息
			WaybillInfoVo waybillInfo = resourceBean.getWaybillInfoVo();
			// 数据填充
			dataInsert(jctx, resourceBean);
			// 获得打印管理类
			JasperPrintManager jasperPrintManager = new JasperPrintManager(jctx);
			if (ui.list.getSelectedIndex() == 0) {
				if ((WaybillRfcConstants.CUSTOMER_CHANGE.equals(waybillInfo.getRfcType().getValueCode())) || (WaybillRfcConstants.INSIDE_CHANGE.equals(waybillInfo.getRfcType().getValueCode()))) {
					if(resourceBean.getWaybillRfcChangeDetailDtos().size()<=DEFAULT_HALF_NUM){
						// 直接打印 更改单打印。
						jctx.setPrtkey("halfModifyWaybill");
					}else{
						// 直接打印 更改单打印。
						jctx.setPrtkey("modifyWaybill");
					}
				} else if ((WaybillRfcConstants.TRANSFER.equals(waybillInfo.getRfcType().getValueCode()))) {
					// 打印转运单
					jctx.put("changedeststation", (waybillInfo.getTfrTargetOrgCode() != null) ? waybillInfo.getTfrTargetOrgCode() : "-");// 目的站
					jctx.put("newpickuporg", (waybillInfo.getTfrCustomerPickupOrgCode() != null) ? waybillInfo.getTfrCustomerPickupOrgCode().getName() : "-");// 提货网点
					jctx.put("changetype", (waybillInfo.getTfrProductCode().getName() != null) ? waybillInfo.getTfrProductCode().getName() : "-");// 运输性质
					if(resourceBean.getWaybillRfcChangeDetailDtos().size()>DEFAULT_HALF_NUM){
						jctx.setPrtkey("changetransportWaybill");
					}else{
						jctx.setPrtkey("halfChangetransportWaybill");
					}
				} else if ((WaybillRfcConstants.RETURN.equals(waybillInfo.getRfcType().getValueCode()))) {
					// 打印返货单
					jctx.setPrtkey("returngoodsWaybill");
					jctx.put("changedeststation", (waybillInfo.getRtnTargetOrgCode() != null) ? waybillInfo.getRtnTargetOrgCode() : "-");// 返货网点
					jctx.put("newpickuporg", (waybillInfo.getRtnCustomerPickupOrgCode() != null) ? waybillInfo.getRtnCustomerPickupOrgCode().getName() : "-");// 返货提货网点
					jctx.put("changetype", (waybillInfo.getRtnProductCode() != null) ? waybillInfo.getRtnProductCode().getName() : "-");// 返货运输性质
					if(resourceBean.getWaybillRfcChangeDetailDtos().size()>DEFAULT_HALF_NUM){
						jctx.setPrtkey("returngoodsWaybill");
					}else{
						jctx.setPrtkey("halfReturngoodsWaybill");
					}
				}

				jasperPrintManager.processModifyPrintResultInPreviewer(jctx);
				ui.setVisible(false);
			} else if (ui.list.getSelectedIndex() == 1) {
				// 套打

				if ((waybillInfo.getRfcType().getValueCode().equals(WaybillRfcConstants.CUSTOMER_CHANGE)) || (waybillInfo.getRfcType().getValueCode().equals(WaybillRfcConstants.INSIDE_CHANGE))) {
					if(resourceBean.getWaybillRfcChangeDetailDtos().size()<=DEFAULT_HALF_NUM){
						// 直接打印 更改单打印。
						jctx.setPrtkey("halfModifyWaybill");
					}else{
						// 直接打印 更改单打印。
						jctx.setPrtkey("modifyWaybill");
					}
				} else if ((waybillInfo.getRfcType().getValueCode().equals(WaybillRfcConstants.TRANSFER))) {
					if(resourceBean.getWaybillRfcChangeDetailDtos().size()<=DEFAULT_HALF_NUM){
						// 直接打印 更改单打印。
						jctx.setPrtkey("halfChangetransportWaybill");
					}else{
						// 直接打印 更改单打印。
						jctx.setPrtkey("changetransportWaybill");
					}
					jctx.put("changedeststation", (waybillInfo.getTfrTargetOrgCode() != null) ? waybillInfo.getTfrTargetOrgCode() : "-");// 目的站
					jctx.put("newpickuporg", (waybillInfo.getTfrCustomerPickupOrgCode() != null) ? waybillInfo.getTfrCustomerPickupOrgCode().getName() : "-");// 提货网点
					jctx.put("changetype", (waybillInfo.getTfrProductCode() != null) ? waybillInfo.getTfrProductCode().getName() : "-");// 运输性质
				} else if ((waybillInfo.getRfcType().getValueCode().equals(WaybillRfcConstants.RETURN))) {
					jctx.put("changedeststation", (waybillInfo.getRtnTargetOrgCode() != null) ? waybillInfo.getRtnTargetOrgCode() : "-");// 返货网点
					jctx.put("newpickuporg", (waybillInfo.getRtnCustomerPickupOrgCode() != null) ? waybillInfo.getRtnCustomerPickupOrgCode().getName() : "-");// 返货提货网点
					jctx.put("changetype", (waybillInfo.getRtnProductCode() != null) ? waybillInfo.getRtnProductCode().getName() : "-");// 返货运输性质
					if(resourceBean.getWaybillRfcChangeDetailDtos().size()<=DEFAULT_HALF_NUM){
						// 直接打印 更改单打印。
						jctx.setPrtkey("halfReturngoodsWaybill");
					}else{
						// 直接打印 更改单打印。
						jctx.setPrtkey("returngoodsWaybill");
					}
				}

				jasperPrintManager.processModifyPrintResultInPreviewer(jctx);
				ui.setVisible(false);
			} else {
				// 提示请选择打印模版
				JOptionPane.showMessageDialog(null, i18n.get("foss.gui.changingexp.printModifyWaybillAction.choosePrintTemplates.exception"), i18n.get("foss.gui.changingexp.printModifyWaybillAction.choosePrintTemplates.inputError"),
						JOptionPane.WARNING_MESSAGE);
			}
		} catch (Exception exp) {
			LOG.error("打印更改单异常", exp);
		}
	}

	/**
	 * <p>
	 * 数据填充
	 * </p>
	 * <p>
	 * 1、更改单打印信息
	 * 单号:
	 * 货物运单号
	 * 
	 * 更改原因:
	 * 运单变更原因
	 * 
	 * 申请部门:
	 * 运单变更起草部门
	 * 
	 * 申请人:	
	 * 运单变更起草人
	 * 
	 * 申请时间:
	 * 运单变更起草时间
	 * 
	 * 货物名称:
	 * 更改之后的货物名称
	 * 
	 * 包装:
	 * 更改之后的货物包装
	 * 
	 * 件数:	
	 * 更改之后的货物件数
	 * 
	 * 重量:
	 * 更改之后的货物重量
	 * 
	 * 体积:	
	 * 更改之后的货物体积
	 * 
	 * 尺寸:	
	 * 更改之后的货物尺寸
	 * 
	 * 变更项:	
	 * 变更的运单信息项目
	 * 
	 * 变更前信息:	
	 * 变更项变更前对应的信息
	 * 
	 * 变更后信息:
	 * 变更项变更后对应的信息
	 * 
	 * </p>
	 * 1.8.2	转运单打印信息
	 * 单号:	
	 * 货物运单号
	 * 
	 * 转运原因:	
	 * 客户提出转货的原因
	 * 
	 * 转运目的站:	
	 * 货物转运的目的站
	 * 
	 * 转运提货网点:	
	 * 货物转运的提货网点
	 * 
	 * 转运运输性质:	
	 * 货物转运的运输性质
	 * 
	 * 申请部门:	
	 * 运单变更起草部门
	 * 
	 * 申请人:	
	 * 运单变更起草人
	 * 
	 * 申请时间:	
	 * 运单变更起草时间
	 * 
	 * 货物名称:
	 * 更改后的货物名称
	 * 
	 * 包装:
	 * 更改后的货物包装
	 * 
	 * 件数:	
	 * 更改后的货物件数
	 * 
	 * 重量:	
	 * 更改后的货物重量
	 * 
	 * 体积:
	 * 更改后的货物体积
	 * 
	 * 尺寸:	
	 * 更改后的货物尺寸
	 * 
	 * 变更项:	
	 * 更改的项目
	 * 
	 * 变更前信息:
	 * 变更项变更前对应的信息
	 * 
	 * 变更后信息:	
	 * 变更项变更后对应的信息
	 * 
	 * </p>
	 * 单号:	
	 * 货物运单号
	 * 
	 * 返货原因:
	 * 客户提出返货的原因
	 * 
	 * 返货目的站:	
	 * 货物返回的目的站
	 * 
	 * 返货提货网点:	
	 * 货物返回的提货网点
	 * 
	 * 返货运输性质:	
	 * 货物返回的运输性质
	 * 
	 * 申请部门:	
	 * 运单变更起草部门
	 * 
	 * 申请人:	
	 * 运单变更起草人
	 * 
	 * 申请时间:
	 * 运单变更起草时间
	 * 
	 * 货物名称:
	 * 更改后的货物名称
	 * 
	 * 包装:	
	 * 更改后的货物包装
	 * 
	 * 件数:	
	 * 更改后的货物件数
	 * 
	 * 重量:	
	 * 更改后的货物重量
	 * 
	 * 体积:	
	 * 更改后的货物体积
	 * 
	 * 尺寸:	
	 * 更改后的货物尺寸
	 * 
	 * 变更项:	
	 * 更改的运单项目
	 * 
	 * 变更前信息:	
	 * 变更项变更前对应的信息
	 * 
	 * 变更后信息：	
	 * 变更项变更后对应的信息
	 * @author foss-jiangfei
	 * @date 2012-10-28 下午3:21:42
	 * @param jctx
	 * @param resourceBean
	 * @see
	 */
	private void dataInsert(JasperContext jctx, WaybillRfcPrintVo resourceBean) {
		WaybillInfoVo waybillInfo = resourceBean.getWaybillInfoVo();
		jctx.put("waybillNo", waybillInfo.getWaybillNo());// 单号
		jctx.put("applicant", (waybillInfo.getDarfter() != null) ? waybillInfo.getDarfter() : "-");// 申请人
		jctx.put("applydepartment", (waybillInfo.getDarftOrgName() != null) ? waybillInfo.getDarftOrgName() : "-");// 申请部门
		jctx.put("changereason", (waybillInfo.getRfcReason() != null) ? waybillInfo.getRfcReason() : "-");// 更改原因
		jctx.put("applytime", (!ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(getDate(waybillInfo.getDarftTime()), ObjectUtils.NULL))) ? getDate(waybillInfo.getDarftTime()) : "-");// 申请时间

		jctx.put("goodsname", (waybillInfo.getGoodsName() != null) ? waybillInfo.getGoodsName() : "-");// 货物名称
		// 包装的值
		String packageInfo = "";
		//获得多少纸包装
		if (waybillInfo.getPaper() != null && waybillInfo.getPaper() > 0) {
			packageInfo += waybillInfo.getPaper() + i18n.get("foss.gui.changingexp.printModifyWaybillAction.packageInfo.papper");
		}
		//获得多少木包装
		if (waybillInfo.getWood() != null && waybillInfo.getWood() > 0) {
			packageInfo += waybillInfo.getWood() + i18n.get("foss.gui.changingexp.printModifyWaybillAction.packageInfo.wood");
		}
		//获得多少托包装
		if (waybillInfo.getSalver() != null && waybillInfo.getSalver() > 0) {
			packageInfo += waybillInfo.getSalver() + i18n.get("foss.gui.changingexp.printModifyWaybillAction.packageInfo.salver");
		}
		//获得多少 纤包装
		if(waybillInfo.getFibre() != null && waybillInfo.getFibre() > 0){
			packageInfo += waybillInfo.getFibre() + i18n.get("foss.gui.creating.common.packageLaber.fibre");
		}
		//获得多少膜包装
		if (waybillInfo.getMembrane() != null && waybillInfo.getMembrane() > 0) {
			packageInfo += waybillInfo.getMembrane() + i18n.get("foss.gui.changingexp.printModifyWaybillAction.packageInfo.membrane");
		}
		//其他
		if (waybillInfo.getOtherPackage() != null) {
			packageInfo += waybillInfo.getOtherPackage() + i18n.get("foss.gui.changingexp.printModifyWaybillAction.packageInfo.otherPackage");
		}
		
		//set 包装值 给参数
		jctx.put("package", packageInfo);// 包装

		jctx.put("goodsnum", (waybillInfo.getGoodsQtyTotal() != null) ? waybillInfo.getGoodsQtyTotal().toString() : "-");// 货物数量

		jctx.put("weight", (waybillInfo.getGoodsWeightTotal() != null) ? waybillInfo.getGoodsWeightTotal().toPlainString() : "-");// 货物重量
		jctx.put("volume", (waybillInfo.getGoodsVolumeTotal() != null) ? waybillInfo.getGoodsVolumeTotal().toPlainString() : "-");// 货物体积
		jctx.put("size", (waybillInfo.getGoodsSize() != null) ? waybillInfo.getGoodsSize() : "-");// 货物尺寸

		jctx.put("waybillRfcChangeDetailList", resourceBean.getWaybillRfcChangeDetailDtos());
		BigDecimal resultFee = BigDecimal.ZERO;
		// 处理更改项 before after
		for (int i = 0; i < resourceBean.getWaybillRfcChangeDetailDtos().size(); i++) {
			WaybillRfcChangeDetailDto tmp = resourceBean.getWaybillRfcChangeDetailDtos().get(i);
			jctx.put("WaybillRfcChangeDetailDto" + i, tmp.getRfcItems() + "&" + (tmp.getBeforeRfcInfo() != null ? tmp.getBeforeRfcInfo() : "-") + "~" + tmp.getAfterRfcInfo());// 组合起来到那边PreWayBillPrtDataSource拆分
			if ("totalFee".equals(tmp.getPropertyName())) {
				resultFee = new BigDecimal(tmp.getAfterRfcInfo().toString()).subtract(new BigDecimal(tmp.getBeforeRfcInfo()));
			}
		}
		// 费用差额
		jctx.put("costvariance", resultFee.toString());
		// 更改项有多少个
		jctx.put("WaybillRfcChangeDetailDtoSize", resourceBean.getWaybillRfcChangeDetailDtos().size());
		// 插入打印更改单记录 和 查询打印次数

		if ("ONLINE_LOGIN".equals(SessionContext.get("user_loginType").toString())) {
			String waybillNo = resourceBean.getWaybillInfoVo().getWaybillNo();
			PrintInfoEntity record = new PrintInfoEntity();
			//打印信息  更改单打印
			record.setPrintType(WaybillConstants.PRINT_INFO_WAYBILLRFC);
			//set 运单号
			record.setWaybillNo(waybillNo);
			//set 更改单ID
			record.setWaybillId(waybillInfo.getWaybillRfcId());
			//获得当前登录对象
			UserEntity user = (UserEntity) SessionContext.getCurrentUser();
			//set 打印人工号
			record.setPrintUserCode(user.getEmployee().getEmpCode());
			//set 打印人名称
			record.setPrintUser(user.getEmployee().getEmpName());
			// set 打印人部门编号
			record.setPrintOrgCode(user.getEmployee().getDepartment().getCode());
			//set 打印人组织名称
			record.setPrintOrg(user.getEmployee().getDepartment().getName());
			//set 打印时间
			record.setPrintTime(new Date());
			//插入一条更改单打印记录
			WaybillRfcServiceFactory.getWaybillRfcService().insertSelective(record);
			//在线查询更改单打印次数
			int printTimes = WaybillRfcServiceFactory.getWaybillRfcService().queryPrintTimesByWaybillId(waybillInfo.getWaybillRfcId(), waybillNo);
			// set 打印次数
			jctx.put("printtimes", String.valueOf(printTimes));// 更改次数

		}
	}

	/**
	 * <p>
	 * ui注入
	 * </p>
	 * ========
	 * 注入打印类型选择对话框
	 * @author foss-jiangfei
	 * @date 2012-10-23 下午3:41:06
	 * @param ui
	 * @see com.deppon.foss.framework.client.component.buttonaction.IButtonActionListener#setInjectUI(java.awt.Container)
	 */
	@Override
	public void setInjectUI(RFCPrintChoiceDiaglogNew ui) {
		this.ui = ui;
	}

	/**
	 * <p>
	 * Date 转成 yy-MM-dd HH:mm:ss格式
	 * </p>
	 * 格式化日期  
	 * 返回格式化后string类型
	 * 传入参数  date 日期类型
	 * @author foss-jiangfei
	 * @date 2012-10-28 下午4:49:39
	 * @param date
	 * @return
	 * @see
	 */
	@SuppressWarnings("finally")
	public static String getDate(Date date) {
		String str = "";
		SimpleDateFormat sf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		try {
			str = sf.format(date);

		} catch (Exception e) {
			LOG.error("日期转换异常", e);
		} finally {
			return str;
		}
	}

	/**
	 * <p>
	 * 获得更改单打印需要的BEAN
	 * </p>
	 * 
	 * @author foss-jiangfei
	 * @date 2012-10-28 下午4:49:39
	 * @param
	 * @return WaybillRfcPrintVo
	 * @see
	 */
	private WaybillRfcPrintVo getWaybillRfcPrintVo() {
		//获得更改单打印vo
		WaybillRfcPrintVo resourceBean = new WaybillRfcPrintVo();
		//获得运单VO
		WaybillInfoVo tp = ui.getWaybillRFCUI().getBinderWaybill();
		resourceBean.setWaybillInfoVo(tp);
		//获得变更信息面板数据
		List<WaybillRfcChangeDetailDto> top = ui.getWaybillRFCUI().getMessagePanel().getChangedInfoPanel().getTableData();
		//set 变更明细集合
		resourceBean.setWaybillRfcChangeDetailDtos(top);
		//返回 WaybillRfcPrintVo
		return resourceBean;
	}
}