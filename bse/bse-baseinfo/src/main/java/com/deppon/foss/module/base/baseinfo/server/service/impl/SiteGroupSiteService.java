/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SiteGroupSiteService.java
 * 
 * FILE NAME        	: SiteGroupSiteService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
dp-foss-综合管理系统用例-新增_修改_作废_查询站点组-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-6-21	新增  	谢艳涛	V0.1
2012-7-4	根据ITA王偕旭要求修改：站点组更名为站点组；把线路分组更改为到达站点分组、出发站点分组	谢艳涛	V0.2
2012-7-11	根据ITA评审意见修改：站点组信息列表中去掉“所属部门”，增加“站点序号”、“站点”	谢艳涛	V0.3
2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
2013-2-21	根据ISSUE-1354优化SUC	张继恒	V1.2

1.	SUC-756-新增_修改_作废_查询站点组
1.1	相关业务用例
BUC_FOSS_5.10.20_010 预测货量
1.2	用例描述
站点组是对经过某个中转站点的到达站点和出发站点进行分组，排序；用于货量预测的聚合展示。本系统用例用于对站点组基础资料的维护，包括新增、修改、作废、查询等操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	行政组织基础资料完备
2、	始发线路基础资料完备
3、	运作到运作线路信息基础资料完备
4、	到达线路基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
SUC-284  新增_修改_作废_查询始发线路
SUC-218  新增_修改_作废_查询运作到运作线路信息
SUC-740  新增_修改_作废_查询到达线路
后置条件	1、	为“SUC-62 预测货量”系统用例提供站点组基础资料查询	SUC-62 预测货量
1.4	操作用户角色
操作用户	描述
外场管理员	外场管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
空运总调管理员	空运总调管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
                               图一：站点组管理主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	新增出发组按钮：点击新增到达组按钮进入新增界面，参见【图三：站点组新增/修改界面（二）】。
2)	新增到达组按钮：点击新增到达组按钮进入新增界面，参见【图二：站点组新增/修改界面（一）】
3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
4)	重置按钮：点击重置按钮，清空查询条件。
5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，作废时同时把该站点组包含的站点移除；或点击各行的作废按钮，弹出确认提示框。作废成功后会弹出作废成功的提示框，作废时同时把该站点组包含的站点移除；若作废失败，弹出作废失败的提示框，并提示失败原因。
6)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：站点组新增/修改界面（一）】或者【图三：站点组新增/修改界面（二）】。
8)	分页按钮：实现分页功能。
2.	列表区域
1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
2)	列表中显示：站点组名称、站点组类型、站点序号、站点。
3.	字段输入区域
1)	查询条件包括站点组名称、站点组类型。
1.1	站点组名称：文本，支持模糊查询；
1.2	站点组类型：下拉框，包括：全部、到达、出发，默认为全部。

1.5.4	界面原型-新增/修改界面
 
图二：站点组新增/修改界面（一）
 
图三：站点组新增/修改界面（二）

 
                                  图四：选择站点界面
1.5.5	界面描述-新增/修改界面
1.	字段输入区域
1)	站点组名称： 必填，文本
2)	所属部门：只读，自动带出，为外场管理员所在的外场或者空运总调管理员所在的空运总调；
3)	站点组类型：自动带出，点击新增出发组按钮，带出站点组类型为“出发”；点击新增到达组按钮，带出站点组类型为“到达”
4)	站点组站点：必填，列表，通过选择站点进行添加，站点可以通过输入站点序号进行排序，详见【站点组站点列表信息】。
5)	备注：选填，文本
2.	功能按钮区域
1)	选择站点按钮：点击选择站点按钮，先判断站点组信息是否已经保存，如果信息还未保存，弹出提示框，提示需要保存站点组信息才能选择站点；如果信息已经保存成功，则弹出站点选择框（见图【图四：选择站点界面】），进行站点选择操作。
2)	移除站点按钮：选择要移除的站点，点击移除站点按钮，先判断站点组信息是否已做修改，如果站点组信息已做修改，弹出提醒框，提示先保存站点组信息才能移除选择的站点。
3)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面提示成功信息，并继续停留在新增界面，供用户继续添加；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面
4)	重置按钮：点击重置按钮，回到当前界面的初始状态。
5)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
1.6	操作步骤
1.6.1	添加站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击新增按钮，进入新增/修改界面		
3	输入站点组详细信息，并选择站点组站点，点击保存。
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	点击选择站点按钮，弹出站点选择框，选择站点。
4	初始化新增界面返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

1.6.2	修改站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击修改按钮，进入新增/修改界面		
3	修改站点组详细信息，点击保存
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	
4	返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

1.6.3	作废站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	选择一行或者多行记录，点击作废按钮。		作废时同时把该站点组下面的站点一起移除。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.6.4	查询站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	输入查询条件，点击查询按钮。参见业务规则SR-3	【站点组查询条件】	系统返回查询结果
1.6.5	选择站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	点击选择站点按钮，弹出选择站点页面（见图【图四：选择站点界面】） 		
3	选择站点，点击确定按钮	【站点列表信息】	关闭弹出窗口，并把选中的站点名称添加到【图二：站点组新增/修改界面】站点组站点列表中
4	返回【图二：站点组新增/修改界面（一）】		

序号	扩展事件	相关数据	备注
3a	输入查询条件，点击查询按钮		把查询结果显示到站点列表中
3b	点击重置按钮，清空查询条件		
3c	点击取消按钮，关闭弹出窗口，返回到【图二：站点组新增/修改界面（一）】		

1.6.6	移除站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	选择一行或者多行记录，点击移除站点按钮。		弹出提示确认框。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.7	业务规则
序号	描述
SR-1	新增/修改界面，选择站点时，根据“所属部门“和”站点组类型“进行查询所有站点，遵循以下规则：
1、	若“站点组类型”为到达，站点列表显示的站点是从到达“所属部门”的始发线路、运作到运作线路中位于“所属部门”之前的站点中获取
2、	若“站点组类型”为出发，站点列表显示的站点是从“所属部门”出发的到达线路、运作到运作线路中位于“所属部门”之后的站点中获取
3、	站点组站点只能从站点列表进行选择；所选的站点默认生成一个序号，可以自行设定数字进行站点排序。
SR-2	站点，所属部门和站点组类型的组合必须唯一；
SR-3	查询支持模糊查询， “站点组类型”默认为全部，包含：到达、出发、全部

1.8	数据元素
1.8.1	站点组新增/修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
站点组名称	站点组名称	文本		50	是	
所属部门	所属部门只读，自动带出，用户所在部门（用户分为外场管理员和空运总调管理员）	N/A		100	N/A	
站点组类型	站点组类型，分为：到达、出发；自动带出	N/A		10	N/A	
站点信息						详见【站点组站点列表信息】
备注	备注	文本		200	否	
1.8.2	站点组列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	N/A	50	N/A	
站点组类型	站点组类型，分为：到达、出发	N/A	10	N/A	
站点序号	站点的序号	N/A	4	N/A	
站点	站点的名称	N/A	50	N/A	
1.8.3	站点组查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	文本	50	否	
所属部门	站点组所属部门，从行政组织（外场、空运总调）基础资料中选择	选择框	100	否	支持手动输入，模糊查询
站点组类型	站点组类型，分为：到达、出发	下拉框	10	否	

1.8.4	站点组站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
序号	线路排序序号，默认从小到大	数字	4	N/A	
站点.	站点的名称	N/A	50	N/A	

1.8.5	站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点名称	站点的名称	N/A	50	N/A	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
dp-foss-综合管理系统用例-新增_修改_作废_查询站点组-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-6-21	新增  	谢艳涛	V0.1
2012-7-4	根据ITA王偕旭要求修改：站点组更名为站点组；把线路分组更改为到达站点分组、出发站点分组	谢艳涛	V0.2
2012-7-11	根据ITA评审意见修改：站点组信息列表中去掉“所属部门”，增加“站点序号”、“站点”	谢艳涛	V0.3
2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
2013-2-21	根据ISSUE-1354优化SUC	张继恒	V1.2

1.	SUC-756-新增_修改_作废_查询站点组
1.1	相关业务用例
BUC_FOSS_5.10.20_010 预测货量
1.2	用例描述
站点组是对经过某个中转站点的到达站点和出发站点进行分组，排序；用于货量预测的聚合展示。本系统用例用于对站点组基础资料的维护，包括新增、修改、作废、查询等操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	行政组织基础资料完备
2、	始发线路基础资料完备
3、	运作到运作线路信息基础资料完备
4、	到达线路基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
SUC-284  新增_修改_作废_查询始发线路
SUC-218  新增_修改_作废_查询运作到运作线路信息
SUC-740  新增_修改_作废_查询到达线路
后置条件	1、	为“SUC-62 预测货量”系统用例提供站点组基础资料查询	SUC-62 预测货量
1.4	操作用户角色
操作用户	描述
外场管理员	外场管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
空运总调管理员	空运总调管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
                               图一：站点组管理主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	新增出发组按钮：点击新增到达组按钮进入新增界面，参见【图三：站点组新增/修改界面（二）】。
2)	新增到达组按钮：点击新增到达组按钮进入新增界面，参见【图二：站点组新增/修改界面（一）】
3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
4)	重置按钮：点击重置按钮，清空查询条件。
5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，作废时同时把该站点组包含的站点移除；或点击各行的作废按钮，弹出确认提示框。作废成功后会弹出作废成功的提示框，作废时同时把该站点组包含的站点移除；若作废失败，弹出作废失败的提示框，并提示失败原因。
6)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：站点组新增/修改界面（一）】或者【图三：站点组新增/修改界面（二）】。
8)	分页按钮：实现分页功能。
2.	列表区域
1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
2)	列表中显示：站点组名称、站点组类型、站点序号、站点。
3.	字段输入区域
1)	查询条件包括站点组名称、站点组类型。
1.1	站点组名称：文本，支持模糊查询；
1.2	站点组类型：下拉框，包括：全部、到达、出发，默认为全部。

1.5.4	界面原型-新增/修改界面
 
图二：站点组新增/修改界面（一）
 
图三：站点组新增/修改界面（二）

 
                                  图四：选择站点界面
1.5.5	界面描述-新增/修改界面
1.	字段输入区域
1)	站点组名称： 必填，文本
2)	所属部门：只读，自动带出，为外场管理员所在的外场或者空运总调管理员所在的空运总调；
3)	站点组类型：自动带出，点击新增出发组按钮，带出站点组类型为“出发”；点击新增到达组按钮，带出站点组类型为“到达”
4)	站点组站点：必填，列表，通过选择站点进行添加，站点可以通过输入站点序号进行排序，详见【站点组站点列表信息】。
5)	备注：选填，文本
2.	功能按钮区域
1)	选择站点按钮：点击选择站点按钮，先判断站点组信息是否已经保存，如果信息还未保存，弹出提示框，提示需要保存站点组信息才能选择站点；如果信息已经保存成功，则弹出站点选择框（见图【图四：选择站点界面】），进行站点选择操作。
2)	移除站点按钮：选择要移除的站点，点击移除站点按钮，先判断站点组信息是否已做修改，如果站点组信息已做修改，弹出提醒框，提示先保存站点组信息才能移除选择的站点。
3)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面提示成功信息，并继续停留在新增界面，供用户继续添加；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面
4)	重置按钮：点击重置按钮，回到当前界面的初始状态。
5)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
1.6	操作步骤
1.6.1	添加站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击新增按钮，进入新增/修改界面		
3	输入站点组详细信息，并选择站点组站点，点击保存。
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	点击选择站点按钮，弹出站点选择框，选择站点。
4	初始化新增界面返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

1.6.2	修改站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击修改按钮，进入新增/修改界面		
3	修改站点组详细信息，点击保存
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	
4	返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

1.6.3	作废站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	选择一行或者多行记录，点击作废按钮。		作废时同时把该站点组下面的站点一起移除。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.6.4	查询站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	输入查询条件，点击查询按钮。参见业务规则SR-3	【站点组查询条件】	系统返回查询结果
1.6.5	选择站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	点击选择站点按钮，弹出选择站点页面（见图【图四：选择站点界面】） 		
3	选择站点，点击确定按钮	【站点列表信息】	关闭弹出窗口，并把选中的站点名称添加到【图二：站点组新增/修改界面】站点组站点列表中
4	返回【图二：站点组新增/修改界面（一）】		

序号	扩展事件	相关数据	备注
3a	输入查询条件，点击查询按钮		把查询结果显示到站点列表中
3b	点击重置按钮，清空查询条件		
3c	点击取消按钮，关闭弹出窗口，返回到【图二：站点组新增/修改界面（一）】		

1.6.6	移除站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	选择一行或者多行记录，点击移除站点按钮。		弹出提示确认框。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.7	业务规则
序号	描述
SR-1	新增/修改界面，选择站点时，根据“所属部门“和”站点组类型“进行查询所有站点，遵循以下规则：
1、	若“站点组类型”为到达，站点列表显示的站点是从到达“所属部门”的始发线路、运作到运作线路中位于“所属部门”之前的站点中获取
2、	若“站点组类型”为出发，站点列表显示的站点是从“所属部门”出发的到达线路、运作到运作线路中位于“所属部门”之后的站点中获取
3、	站点组站点只能从站点列表进行选择；所选的站点默认生成一个序号，可以自行设定数字进行站点排序。
SR-2	站点，所属部门和站点组类型的组合必须唯一；
SR-3	查询支持模糊查询， “站点组类型”默认为全部，包含：到达、出发、全部

1.8	数据元素
1.8.1	站点组新增/修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
站点组名称	站点组名称	文本		50	是	
所属部门	所属部门只读，自动带出，用户所在部门（用户分为外场管理员和空运总调管理员）	N/A		100	N/A	
站点组类型	站点组类型，分为：到达、出发；自动带出	N/A		10	N/A	
站点信息						详见【站点组站点列表信息】
备注	备注	文本		200	否	
1.8.2	站点组列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	N/A	50	N/A	
站点组类型	站点组类型，分为：到达、出发	N/A	10	N/A	
站点序号	站点的序号	N/A	4	N/A	
站点	站点的名称	N/A	50	N/A	
1.8.3	站点组查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	文本	50	否	
所属部门	站点组所属部门，从行政组织（外场、空运总调）基础资料中选择	选择框	100	否	支持手动输入，模糊查询
站点组类型	站点组类型，分为：到达、出发	下拉框	10	否	

1.8.4	站点组站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
序号	线路排序序号，默认从小到大	数字	4	N/A	
站点.	站点的名称	N/A	50	N/A	

1.8.5	站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点名称	站点的名称	N/A	50	N/A	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
dp-foss-综合管理系统用例-新增_修改_作废_查询站点组-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-6-21	新增  	谢艳涛	V0.1
2012-7-4	根据ITA王偕旭要求修改：站点组更名为站点组；把线路分组更改为到达站点分组、出发站点分组	谢艳涛	V0.2
2012-7-11	根据ITA评审意见修改：站点组信息列表中去掉“所属部门”，增加“站点序号”、“站点”	谢艳涛	V0.3
2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
2013-2-21	根据ISSUE-1354优化SUC	张继恒	V1.2

1.	SUC-756-新增_修改_作废_查询站点组
1.1	相关业务用例
BUC_FOSS_5.10.20_010 预测货量
1.2	用例描述
站点组是对经过某个中转站点的到达站点和出发站点进行分组，排序；用于货量预测的聚合展示。本系统用例用于对站点组基础资料的维护，包括新增、修改、作废、查询等操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	行政组织基础资料完备
2、	始发线路基础资料完备
3、	运作到运作线路信息基础资料完备
4、	到达线路基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
SUC-284  新增_修改_作废_查询始发线路
SUC-218  新增_修改_作废_查询运作到运作线路信息
SUC-740  新增_修改_作废_查询到达线路
后置条件	1、	为“SUC-62 预测货量”系统用例提供站点组基础资料查询	SUC-62 预测货量
1.4	操作用户角色
操作用户	描述
外场管理员	外场管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
空运总调管理员	空运总调管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
                               图一：站点组管理主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	新增出发组按钮：点击新增到达组按钮进入新增界面，参见【图三：站点组新增/修改界面（二）】。
2)	新增到达组按钮：点击新增到达组按钮进入新增界面，参见【图二：站点组新增/修改界面（一）】
3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
4)	重置按钮：点击重置按钮，清空查询条件。
5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，作废时同时把该站点组包含的站点移除；或点击各行的作废按钮，弹出确认提示框。作废成功后会弹出作废成功的提示框，作废时同时把该站点组包含的站点移除；若作废失败，弹出作废失败的提示框，并提示失败原因。
6)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：站点组新增/修改界面（一）】或者【图三：站点组新增/修改界面（二）】。
8)	分页按钮：实现分页功能。
2.	列表区域
1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
2)	列表中显示：站点组名称、站点组类型、站点序号、站点。
3.	字段输入区域
1)	查询条件包括站点组名称、站点组类型。
1.1	站点组名称：文本，支持模糊查询；
1.2	站点组类型：下拉框，包括：全部、到达、出发，默认为全部。

1.5.4	界面原型-新增/修改界面
 
图二：站点组新增/修改界面（一）
 
图三：站点组新增/修改界面（二）

 
                                  图四：选择站点界面
1.5.5	界面描述-新增/修改界面
1.	字段输入区域
1)	站点组名称： 必填，文本
2)	所属部门：只读，自动带出，为外场管理员所在的外场或者空运总调管理员所在的空运总调；
3)	站点组类型：自动带出，点击新增出发组按钮，带出站点组类型为“出发”；点击新增到达组按钮，带出站点组类型为“到达”
4)	站点组站点：必填，列表，通过选择站点进行添加，站点可以通过输入站点序号进行排序，详见【站点组站点列表信息】。
5)	备注：选填，文本
2.	功能按钮区域
1)	选择站点按钮：点击选择站点按钮，先判断站点组信息是否已经保存，如果信息还未保存，弹出提示框，提示需要保存站点组信息才能选择站点；如果信息已经保存成功，则弹出站点选择框（见图【图四：选择站点界面】），进行站点选择操作。
2)	移除站点按钮：选择要移除的站点，点击移除站点按钮，先判断站点组信息是否已做修改，如果站点组信息已做修改，弹出提醒框，提示先保存站点组信息才能移除选择的站点。
3)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面提示成功信息，并继续停留在新增界面，供用户继续添加；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面
4)	重置按钮：点击重置按钮，回到当前界面的初始状态。
5)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
1.6	操作步骤
1.6.1	添加站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击新增按钮，进入新增/修改界面		
3	输入站点组详细信息，并选择站点组站点，点击保存。
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	点击选择站点按钮，弹出站点选择框，选择站点。
4	初始化新增界面返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

1.6.2	修改站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击修改按钮，进入新增/修改界面		
3	修改站点组详细信息，点击保存
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	
4	返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

1.6.3	作废站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	选择一行或者多行记录，点击作废按钮。		作废时同时把该站点组下面的站点一起移除。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.6.4	查询站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	输入查询条件，点击查询按钮。参见业务规则SR-3	【站点组查询条件】	系统返回查询结果
1.6.5	选择站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	点击选择站点按钮，弹出选择站点页面（见图【图四：选择站点界面】） 		
3	选择站点，点击确定按钮	【站点列表信息】	关闭弹出窗口，并把选中的站点名称添加到【图二：站点组新增/修改界面】站点组站点列表中
4	返回【图二：站点组新增/修改界面（一）】		

序号	扩展事件	相关数据	备注
3a	输入查询条件，点击查询按钮		把查询结果显示到站点列表中
3b	点击重置按钮，清空查询条件		
3c	点击取消按钮，关闭弹出窗口，返回到【图二：站点组新增/修改界面（一）】		

1.6.6	移除站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	选择一行或者多行记录，点击移除站点按钮。		弹出提示确认框。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.7	业务规则
序号	描述
SR-1	新增/修改界面，选择站点时，根据“所属部门“和”站点组类型“进行查询所有站点，遵循以下规则：
1、	若“站点组类型”为到达，站点列表显示的站点是从到达“所属部门”的始发线路、运作到运作线路中位于“所属部门”之前的站点中获取
2、	若“站点组类型”为出发，站点列表显示的站点是从“所属部门”出发的到达线路、运作到运作线路中位于“所属部门”之后的站点中获取
3、	站点组站点只能从站点列表进行选择；所选的站点默认生成一个序号，可以自行设定数字进行站点排序。
SR-2	站点，所属部门和站点组类型的组合必须唯一；
SR-3	查询支持模糊查询， “站点组类型”默认为全部，包含：到达、出发、全部

1.8	数据元素
1.8.1	站点组新增/修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
站点组名称	站点组名称	文本		50	是	
所属部门	所属部门只读，自动带出，用户所在部门（用户分为外场管理员和空运总调管理员）	N/A		100	N/A	
站点组类型	站点组类型，分为：到达、出发；自动带出	N/A		10	N/A	
站点信息						详见【站点组站点列表信息】
备注	备注	文本		200	否	
1.8.2	站点组列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	N/A	50	N/A	
站点组类型	站点组类型，分为：到达、出发	N/A	10	N/A	
站点序号	站点的序号	N/A	4	N/A	
站点	站点的名称	N/A	50	N/A	
1.8.3	站点组查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	文本	50	否	
所属部门	站点组所属部门，从行政组织（外场、空运总调）基础资料中选择	选择框	100	否	支持手动输入，模糊查询
站点组类型	站点组类型，分为：到达、出发	下拉框	10	否	

1.8.4	站点组站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
序号	线路排序序号，默认从小到大	数字	4	N/A	
站点.	站点的名称	N/A	50	N/A	

1.8.5	站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点名称	站点的名称	N/A	50	N/A	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
dp-foss-综合管理系统用例-新增_修改_作废_查询站点组-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-6-21	新增  	谢艳涛	V0.1
2012-7-4	根据ITA王偕旭要求修改：站点组更名为站点组；把线路分组更改为到达站点分组、出发站点分组	谢艳涛	V0.2
2012-7-11	根据ITA评审意见修改：站点组信息列表中去掉“所属部门”，增加“站点序号”、“站点”	谢艳涛	V0.3
2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
2013-2-21	根据ISSUE-1354优化SUC	张继恒	V1.2

1.	SUC-756-新增_修改_作废_查询站点组
1.1	相关业务用例
BUC_FOSS_5.10.20_010 预测货量
1.2	用例描述
站点组是对经过某个中转站点的到达站点和出发站点进行分组，排序；用于货量预测的聚合展示。本系统用例用于对站点组基础资料的维护，包括新增、修改、作废、查询等操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	行政组织基础资料完备
2、	始发线路基础资料完备
3、	运作到运作线路信息基础资料完备
4、	到达线路基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
SUC-284  新增_修改_作废_查询始发线路
SUC-218  新增_修改_作废_查询运作到运作线路信息
SUC-740  新增_修改_作废_查询到达线路
后置条件	1、	为“SUC-62 预测货量”系统用例提供站点组基础资料查询	SUC-62 预测货量
1.4	操作用户角色
操作用户	描述
外场管理员	外场管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
空运总调管理员	空运总调管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
                               图一：站点组管理主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	新增出发组按钮：点击新增到达组按钮进入新增界面，参见【图三：站点组新增/修改界面（二）】。
2)	新增到达组按钮：点击新增到达组按钮进入新增界面，参见【图二：站点组新增/修改界面（一）】
3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
4)	重置按钮：点击重置按钮，清空查询条件。
5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，作废时同时把该站点组包含的站点移除；或点击各行的作废按钮，弹出确认提示框。作废成功后会弹出作废成功的提示框，作废时同时把该站点组包含的站点移除；若作废失败，弹出作废失败的提示框，并提示失败原因。
6)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：站点组新增/修改界面（一）】或者【图三：站点组新增/修改界面（二）】。
8)	分页按钮：实现分页功能。
2.	列表区域
1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
2)	列表中显示：站点组名称、站点组类型、站点序号、站点。
3.	字段输入区域
1)	查询条件包括站点组名称、站点组类型。
1.1	站点组名称：文本，支持模糊查询；
1.2	站点组类型：下拉框，包括：全部、到达、出发，默认为全部。

1.5.4	界面原型-新增/修改界面
 
图二：站点组新增/修改界面（一）
 
图三：站点组新增/修改界面（二）

 
                                  图四：选择站点界面
1.5.5	界面描述-新增/修改界面
1.	字段输入区域
1)	站点组名称： 必填，文本
2)	所属部门：只读，自动带出，为外场管理员所在的外场或者空运总调管理员所在的空运总调；
3)	站点组类型：自动带出，点击新增出发组按钮，带出站点组类型为“出发”；点击新增到达组按钮，带出站点组类型为“到达”
4)	站点组站点：必填，列表，通过选择站点进行添加，站点可以通过输入站点序号进行排序，详见【站点组站点列表信息】。
5)	备注：选填，文本
2.	功能按钮区域
1)	选择站点按钮：点击选择站点按钮，先判断站点组信息是否已经保存，如果信息还未保存，弹出提示框，提示需要保存站点组信息才能选择站点；如果信息已经保存成功，则弹出站点选择框（见图【图四：选择站点界面】），进行站点选择操作。
2)	移除站点按钮：选择要移除的站点，点击移除站点按钮，先判断站点组信息是否已做修改，如果站点组信息已做修改，弹出提醒框，提示先保存站点组信息才能移除选择的站点。
3)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面提示成功信息，并继续停留在新增界面，供用户继续添加；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面
4)	重置按钮：点击重置按钮，回到当前界面的初始状态。
5)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
1.6	操作步骤
1.6.1	添加站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击新增按钮，进入新增/修改界面		
3	输入站点组详细信息，并选择站点组站点，点击保存。
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	点击选择站点按钮，弹出站点选择框，选择站点。
4	初始化新增界面返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

1.6.2	修改站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击修改按钮，进入新增/修改界面		
3	修改站点组详细信息，点击保存
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	
4	返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

1.6.3	作废站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	选择一行或者多行记录，点击作废按钮。		作废时同时把该站点组下面的站点一起移除。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.6.4	查询站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	输入查询条件，点击查询按钮。参见业务规则SR-3	【站点组查询条件】	系统返回查询结果
1.6.5	选择站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	点击选择站点按钮，弹出选择站点页面（见图【图四：选择站点界面】） 		
3	选择站点，点击确定按钮	【站点列表信息】	关闭弹出窗口，并把选中的站点名称添加到【图二：站点组新增/修改界面】站点组站点列表中
4	返回【图二：站点组新增/修改界面（一）】		

序号	扩展事件	相关数据	备注
3a	输入查询条件，点击查询按钮		把查询结果显示到站点列表中
3b	点击重置按钮，清空查询条件		
3c	点击取消按钮，关闭弹出窗口，返回到【图二：站点组新增/修改界面（一）】		

1.6.6	移除站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	选择一行或者多行记录，点击移除站点按钮。		弹出提示确认框。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.7	业务规则
序号	描述
SR-1	新增/修改界面，选择站点时，根据“所属部门“和”站点组类型“进行查询所有站点，遵循以下规则：
1、	若“站点组类型”为到达，站点列表显示的站点是从到达“所属部门”的始发线路、运作到运作线路中位于“所属部门”之前的站点中获取
2、	若“站点组类型”为出发，站点列表显示的站点是从“所属部门”出发的到达线路、运作到运作线路中位于“所属部门”之后的站点中获取
3、	站点组站点只能从站点列表进行选择；所选的站点默认生成一个序号，可以自行设定数字进行站点排序。
SR-2	站点，所属部门和站点组类型的组合必须唯一；
SR-3	查询支持模糊查询， “站点组类型”默认为全部，包含：到达、出发、全部

1.8	数据元素
1.8.1	站点组新增/修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
站点组名称	站点组名称	文本		50	是	
所属部门	所属部门只读，自动带出，用户所在部门（用户分为外场管理员和空运总调管理员）	N/A		100	N/A	
站点组类型	站点组类型，分为：到达、出发；自动带出	N/A		10	N/A	
站点信息						详见【站点组站点列表信息】
备注	备注	文本		200	否	
1.8.2	站点组列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	N/A	50	N/A	
站点组类型	站点组类型，分为：到达、出发	N/A	10	N/A	
站点序号	站点的序号	N/A	4	N/A	
站点	站点的名称	N/A	50	N/A	
1.8.3	站点组查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	文本	50	否	
所属部门	站点组所属部门，从行政组织（外场、空运总调）基础资料中选择	选择框	100	否	支持手动输入，模糊查询
站点组类型	站点组类型，分为：到达、出发	下拉框	10	否	

1.8.4	站点组站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
序号	线路排序序号，默认从小到大	数字	4	N/A	
站点.	站点的名称	N/A	50	N/A	

1.8.5	站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点名称	站点的名称	N/A	50	N/A	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
dp-foss-综合管理系统用例-新增_修改_作废_查询站点组-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-6-21	新增  	谢艳涛	V0.1
2012-7-4	根据ITA王偕旭要求修改：站点组更名为站点组；把线路分组更改为到达站点分组、出发站点分组	谢艳涛	V0.2
2012-7-11	根据ITA评审意见修改：站点组信息列表中去掉“所属部门”，增加“站点序号”、“站点”	谢艳涛	V0.3
2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
2013-2-21	根据ISSUE-1354优化SUC	张继恒	V1.2

1.	SUC-756-新增_修改_作废_查询站点组
1.1	相关业务用例
BUC_FOSS_5.10.20_010 预测货量
1.2	用例描述
站点组是对经过某个中转站点的到达站点和出发站点进行分组，排序；用于货量预测的聚合展示。本系统用例用于对站点组基础资料的维护，包括新增、修改、作废、查询等操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	行政组织基础资料完备
2、	始发线路基础资料完备
3、	运作到运作线路信息基础资料完备
4、	到达线路基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
SUC-284  新增_修改_作废_查询始发线路
SUC-218  新增_修改_作废_查询运作到运作线路信息
SUC-740  新增_修改_作废_查询到达线路
后置条件	1、	为“SUC-62 预测货量”系统用例提供站点组基础资料查询	SUC-62 预测货量
1.4	操作用户角色
操作用户	描述
外场管理员	外场管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
空运总调管理员	空运总调管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
                               图一：站点组管理主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	新增出发组按钮：点击新增到达组按钮进入新增界面，参见【图三：站点组新增/修改界面（二）】。
2)	新增到达组按钮：点击新增到达组按钮进入新增界面，参见【图二：站点组新增/修改界面（一）】
3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
4)	重置按钮：点击重置按钮，清空查询条件。
5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，作废时同时把该站点组包含的站点移除；或点击各行的作废按钮，弹出确认提示框。作废成功后会弹出作废成功的提示框，作废时同时把该站点组包含的站点移除；若作废失败，弹出作废失败的提示框，并提示失败原因。
6)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：站点组新增/修改界面（一）】或者【图三：站点组新增/修改界面（二）】。
8)	分页按钮：实现分页功能。
2.	列表区域
1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
2)	列表中显示：站点组名称、站点组类型、站点序号、站点。
3.	字段输入区域
1)	查询条件包括站点组名称、站点组类型。
1.1	站点组名称：文本，支持模糊查询；
1.2	站点组类型：下拉框，包括：全部、到达、出发，默认为全部。

1.5.4	界面原型-新增/修改界面
 
图二：站点组新增/修改界面（一）
 
图三：站点组新增/修改界面（二）

 
                                  图四：选择站点界面
1.5.5	界面描述-新增/修改界面
1.	字段输入区域
1)	站点组名称： 必填，文本
2)	所属部门：只读，自动带出，为外场管理员所在的外场或者空运总调管理员所在的空运总调；
3)	站点组类型：自动带出，点击新增出发组按钮，带出站点组类型为“出发”；点击新增到达组按钮，带出站点组类型为“到达”
4)	站点组站点：必填，列表，通过选择站点进行添加，站点可以通过输入站点序号进行排序，详见【站点组站点列表信息】。
5)	备注：选填，文本
2.	功能按钮区域
1)	选择站点按钮：点击选择站点按钮，先判断站点组信息是否已经保存，如果信息还未保存，弹出提示框，提示需要保存站点组信息才能选择站点；如果信息已经保存成功，则弹出站点选择框（见图【图四：选择站点界面】），进行站点选择操作。
2)	移除站点按钮：选择要移除的站点，点击移除站点按钮，先判断站点组信息是否已做修改，如果站点组信息已做修改，弹出提醒框，提示先保存站点组信息才能移除选择的站点。
3)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面提示成功信息，并继续停留在新增界面，供用户继续添加；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面
4)	重置按钮：点击重置按钮，回到当前界面的初始状态。
5)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
1.6	操作步骤
1.6.1	添加站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击新增按钮，进入新增/修改界面		
3	输入站点组详细信息，并选择站点组站点，点击保存。
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	点击选择站点按钮，弹出站点选择框，选择站点。
4	初始化新增界面返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

1.6.2	修改站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击修改按钮，进入新增/修改界面		
3	修改站点组详细信息，点击保存
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	
4	返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

1.6.3	作废站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	选择一行或者多行记录，点击作废按钮。		作废时同时把该站点组下面的站点一起移除。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.6.4	查询站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	输入查询条件，点击查询按钮。参见业务规则SR-3	【站点组查询条件】	系统返回查询结果
1.6.5	选择站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	点击选择站点按钮，弹出选择站点页面（见图【图四：选择站点界面】） 		
3	选择站点，点击确定按钮	【站点列表信息】	关闭弹出窗口，并把选中的站点名称添加到【图二：站点组新增/修改界面】站点组站点列表中
4	返回【图二：站点组新增/修改界面（一）】		

序号	扩展事件	相关数据	备注
3a	输入查询条件，点击查询按钮		把查询结果显示到站点列表中
3b	点击重置按钮，清空查询条件		
3c	点击取消按钮，关闭弹出窗口，返回到【图二：站点组新增/修改界面（一）】		

1.6.6	移除站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	选择一行或者多行记录，点击移除站点按钮。		弹出提示确认框。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.7	业务规则
序号	描述
SR-1	新增/修改界面，选择站点时，根据“所属部门“和”站点组类型“进行查询所有站点，遵循以下规则：
1、	若“站点组类型”为到达，站点列表显示的站点是从到达“所属部门”的始发线路、运作到运作线路中位于“所属部门”之前的站点中获取
2、	若“站点组类型”为出发，站点列表显示的站点是从“所属部门”出发的到达线路、运作到运作线路中位于“所属部门”之后的站点中获取
3、	站点组站点只能从站点列表进行选择；所选的站点默认生成一个序号，可以自行设定数字进行站点排序。
SR-2	站点，所属部门和站点组类型的组合必须唯一；
SR-3	查询支持模糊查询， “站点组类型”默认为全部，包含：到达、出发、全部

1.8	数据元素
1.8.1	站点组新增/修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
站点组名称	站点组名称	文本		50	是	
所属部门	所属部门只读，自动带出，用户所在部门（用户分为外场管理员和空运总调管理员）	N/A		100	N/A	
站点组类型	站点组类型，分为：到达、出发；自动带出	N/A		10	N/A	
站点信息						详见【站点组站点列表信息】
备注	备注	文本		200	否	
1.8.2	站点组列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	N/A	50	N/A	
站点组类型	站点组类型，分为：到达、出发	N/A	10	N/A	
站点序号	站点的序号	N/A	4	N/A	
站点	站点的名称	N/A	50	N/A	
1.8.3	站点组查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	文本	50	否	
所属部门	站点组所属部门，从行政组织（外场、空运总调）基础资料中选择	选择框	100	否	支持手动输入，模糊查询
站点组类型	站点组类型，分为：到达、出发	下拉框	10	否	

1.8.4	站点组站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
序号	线路排序序号，默认从小到大	数字	4	N/A	
站点.	站点的名称	N/A	50	N/A	

1.8.5	站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点名称	站点的名称	N/A	50	N/A	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述
dp-foss-综合管理系统用例-新增_修改_作废_查询站点组-v1.2

修订记录 
日期 	修订内容 	修订人员 	版本号 
2012-6-21	新增  	谢艳涛	V0.1
2012-7-4	根据ITA王偕旭要求修改：站点组更名为站点组；把线路分组更改为到达站点分组、出发站点分组	谢艳涛	V0.2
2012-7-11	根据ITA评审意见修改：站点组信息列表中去掉“所属部门”，增加“站点序号”、“站点”	谢艳涛	V0.3
2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
2013-2-21	根据ISSUE-1354优化SUC	张继恒	V1.2

1.	SUC-756-新增_修改_作废_查询站点组
1.1	相关业务用例
BUC_FOSS_5.10.20_010 预测货量
1.2	用例描述
站点组是对经过某个中转站点的到达站点和出发站点进行分组，排序；用于货量预测的聚合展示。本系统用例用于对站点组基础资料的维护，包括新增、修改、作废、查询等操作。
1.3	用例条件
条件类型	描述	引用系统用例
前置条件	1、	行政组织基础资料完备
2、	始发线路基础资料完备
3、	运作到运作线路信息基础资料完备
4、	到达线路基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
SUC-284  新增_修改_作废_查询始发线路
SUC-218  新增_修改_作废_查询运作到运作线路信息
SUC-740  新增_修改_作废_查询到达线路
后置条件	1、	为“SUC-62 预测货量”系统用例提供站点组基础资料查询	SUC-62 预测货量
1.4	操作用户角色
操作用户	描述
外场管理员	外场管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
空运总调管理员	空运总调管理员只能维护本部门的站点组基础资料，可以新增、修改、作废、查询操作。
1.5	界面要求
1.5.1	表现方式
Web页面
1.5.2	界面原型-主界面
                               图一：站点组管理主界面
1.5.3	界面描述-主界面
1.	功能按钮区域
1)	新增出发组按钮：点击新增到达组按钮进入新增界面，参见【图三：站点组新增/修改界面（二）】。
2)	新增到达组按钮：点击新增到达组按钮进入新增界面，参见【图二：站点组新增/修改界面（一）】
3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
4)	重置按钮：点击重置按钮，清空查询条件。
5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，作废时同时把该站点组包含的站点移除；或点击各行的作废按钮，弹出确认提示框。作废成功后会弹出作废成功的提示框，作废时同时把该站点组包含的站点移除；若作废失败，弹出作废失败的提示框，并提示失败原因。
6)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：站点组新增/修改界面（一）】或者【图三：站点组新增/修改界面（二）】。
8)	分页按钮：实现分页功能。
2.	列表区域
1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
2)	列表中显示：站点组名称、站点组类型、站点序号、站点。
3.	字段输入区域
1)	查询条件包括站点组名称、站点组类型。
1.1	站点组名称：文本，支持模糊查询；
1.2	站点组类型：下拉框，包括：全部、到达、出发，默认为全部。

1.5.4	界面原型-新增/修改界面
 
图二：站点组新增/修改界面（一）
 
图三：站点组新增/修改界面（二）

 
                                  图四：选择站点界面
1.5.5	界面描述-新增/修改界面
1.	字段输入区域
1)	站点组名称： 必填，文本
2)	所属部门：只读，自动带出，为外场管理员所在的外场或者空运总调管理员所在的空运总调；
3)	站点组类型：自动带出，点击新增出发组按钮，带出站点组类型为“出发”；点击新增到达组按钮，带出站点组类型为“到达”
4)	站点组站点：必填，列表，通过选择站点进行添加，站点可以通过输入站点序号进行排序，详见【站点组站点列表信息】。
5)	备注：选填，文本
2.	功能按钮区域
1)	选择站点按钮：点击选择站点按钮，先判断站点组信息是否已经保存，如果信息还未保存，弹出提示框，提示需要保存站点组信息才能选择站点；如果信息已经保存成功，则弹出站点选择框（见图【图四：选择站点界面】），进行站点选择操作。
2)	移除站点按钮：选择要移除的站点，点击移除站点按钮，先判断站点组信息是否已做修改，如果站点组信息已做修改，弹出提醒框，提示先保存站点组信息才能移除选择的站点。
3)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面提示成功信息，并继续停留在新增界面，供用户继续添加；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面
4)	重置按钮：点击重置按钮，回到当前界面的初始状态。
5)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
1.6	操作步骤
1.6.1	添加站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击新增按钮，进入新增/修改界面		
3	输入站点组详细信息，并选择站点组站点，点击保存。
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	点击选择站点按钮，弹出站点选择框，选择站点。
4	初始化新增界面返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

1.6.2	修改站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	点击修改按钮，进入新增/修改界面		
3	修改站点组详细信息，点击保存
参见业务规则SR-1、SR-2	【站点组新增/修改信息】【站点组站点列表信息】	
4	返回站点组管理主界面		

序号	扩展事件	相关数据	备注
3a	点击取消按钮，退出当前界面，返回主界面		
3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

1.6.3	作废站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	选择一行或者多行记录，点击作废按钮。		作废时同时把该站点组下面的站点一起移除。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.6.4	查询站点组操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入站点组管理主界面	【站点组列表信息】	
2	输入查询条件，点击查询按钮。参见业务规则SR-3	【站点组查询条件】	系统返回查询结果
1.6.5	选择站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	点击选择站点按钮，弹出选择站点页面（见图【图四：选择站点界面】） 		
3	选择站点，点击确定按钮	【站点列表信息】	关闭弹出窗口，并把选中的站点名称添加到【图二：站点组新增/修改界面】站点组站点列表中
4	返回【图二：站点组新增/修改界面（一）】		

序号	扩展事件	相关数据	备注
3a	输入查询条件，点击查询按钮		把查询结果显示到站点列表中
3b	点击重置按钮，清空查询条件		
3c	点击取消按钮，关闭弹出窗口，返回到【图二：站点组新增/修改界面（一）】		

1.6.6	移除站点操作步骤
序号	基本步骤	相关数据	补充步骤
1	进入【图二：站点组新增/修改界面（一）】	【站点组新增/修改信息】【站点组站点列表信息】	
2	选择一行或者多行记录，点击移除站点按钮。		弹出提示确认框。
3	点击确定按钮。		

序号	扩展事件	相关数据	备注
2a	点击取消按钮，退出当前界面，返回主界面		
2b	若作废失败，需提示用户作废失败以及失败原因		

1.7	业务规则
序号	描述
SR-1	新增/修改界面，选择站点时，根据“所属部门“和”站点组类型“进行查询所有站点，遵循以下规则：
1、	若“站点组类型”为到达，站点列表显示的站点是从到达“所属部门”的始发线路、运作到运作线路中位于“所属部门”之前的站点中获取
2、	若“站点组类型”为出发，站点列表显示的站点是从“所属部门”出发的到达线路、运作到运作线路中位于“所属部门”之后的站点中获取
3、	站点组站点只能从站点列表进行选择；所选的站点默认生成一个序号，可以自行设定数字进行站点排序。
SR-2	站点，所属部门和站点组类型的组合必须唯一；
SR-3	查询支持模糊查询， “站点组类型”默认为全部，包含：到达、出发、全部

1.8	数据元素
1.8.1	站点组新增/修改信息
字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
站点组名称	站点组名称	文本		50	是	
所属部门	所属部门只读，自动带出，用户所在部门（用户分为外场管理员和空运总调管理员）	N/A		100	N/A	
站点组类型	站点组类型，分为：到达、出发；自动带出	N/A		10	N/A	
站点信息						详见【站点组站点列表信息】
备注	备注	文本		200	否	
1.8.2	站点组列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	N/A	50	N/A	
站点组类型	站点组类型，分为：到达、出发	N/A	10	N/A	
站点序号	站点的序号	N/A	4	N/A	
站点	站点的名称	N/A	50	N/A	
1.8.3	站点组查询条件
字段名称 	说明 	输入限制	长度	是否必填	备注
站点组名称	站点组名称	文本	50	否	
所属部门	站点组所属部门，从行政组织（外场、空运总调）基础资料中选择	选择框	100	否	支持手动输入，模糊查询
站点组类型	站点组类型，分为：到达、出发	下拉框	10	否	

1.8.4	站点组站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
序号	线路排序序号，默认从小到大	数字	4	N/A	
站点.	站点的名称	N/A	50	N/A	

1.8.5	站点列表信息
字段名称 	说明 	输入限制	长度	是否必填	备注
站点名称	站点的名称	N/A	50	N/A	

1.9	非功能性需求
使用量	
2012年全网估计用户数	
响应要求（如果与全系统要求 不一致的话）	
使用时间段	
高峰使用时间段	

1.10	接口描述
接口名称 	对方系统（外部系统或内部其他模块）	接口描述

*		
*/		
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISiteGroupSiteDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SiteGroupSiteException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 站点组站点service接口实现：对站点组站点增删改查
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13
 * 上午10:58:36
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-13 上午10:58:36
 * @since
 * @version
 */
public class SiteGroupSiteService implements ISiteGroupSiteService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteGroupSiteService.class);

    /**
     * 站点组站点Dao接口.
     */
    private ISiteGroupSiteDao siteGroupSiteDao;

    /**
     * 设置 站点组站点Dao接口.
     *
     * @param siteGroupSiteDao the new 站点组站点Dao接口
     */
    public void setSiteGroupSiteDao(ISiteGroupSiteDao siteGroupSiteDao) {
	this.siteGroupSiteDao = siteGroupSiteDao;
    }

    /**
     * 新增站点组站点.
     *
     * @param entitys 
     * @return 成功：list 失败：null;
     * @throws SiteGroupSiteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:01:52
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#addSiteGroupSite(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Transactional
    @Override
    public List<SiteGroupSiteEntity> addSiteGroupSite(List<SiteGroupSiteEntity> entitys) throws SiteGroupSiteException {
	// 检查参数
	if (null == entitys) {
	    return null;
	}
	
	List<SiteGroupSiteEntity> list = new ArrayList<SiteGroupSiteEntity>();
	for (SiteGroupSiteEntity entity : entitys) {
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    // 第一次记录新增时，虚拟编码为记录的id
	    entity.setVirtualCode(entity.getId());
	    entity.setActive(FossConstants.ACTIVE);
	    //验证站点是否重复
	    SiteGroupSiteEntity siteEntity = queryGroupSiteBySiteCode(entity);
	    if(null == siteEntity){
		int result = siteGroupSiteDao.addSiteGroupSite(entity);
		if(result > 0){
			list.add(entity);
		}
	    }else {
		throw new SiteGroupSiteException("站点已重复！");
	    }
	}
	
	return list.size() > 0 ? list : null;
    }

    /**
     * 根据code作废站点组站点信息.
     *
     * @param codeStr 
     * @param modifyUser 
     * @return 1：成功；-1：失败
     * @throws SiteGroupSiteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:02:02
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#deleteSiteGroupSiteByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deleteSiteGroupSiteByCode(String codeStr, String modifyUser) throws SiteGroupSiteException {
	// 检查参数
	if (StringUtil.isBlank(codeStr)) {
	    throw new SiteGroupSiteException("站点组站点虚拟编码不允许为空！");
	} else {// 拆分字符串
	    LOGGER.debug("codeStr: "+codeStr);
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);

	    return siteGroupSiteDao.deleteSiteGroupSiteByCode(codes,modifyUser);
	}
    }

    /**
     * 修改站点组站点信息.
     *
     * @param entity 站点组站点实体
     * @return 1：成功；-1：失败
     * @throws SiteGroupSiteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:02:13
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#updateSiteGroupSite(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Transactional
    @Override
    public SiteGroupSiteEntity updateSiteGroupSite(SiteGroupSiteEntity entity) throws SiteGroupSiteException {
	// 检查参数
	if (null == entity) {
	    return null;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    throw new SiteGroupSiteException("虚拟编码不允许为空！");
	}

	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };

	// 作废历史记录
	int flag = siteGroupSiteDao.deleteSiteGroupSiteByCode(codes, entity.getModifyUser());

	if (flag > 0) {// 作废成功，新增一条记录

	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    //验证站点序号是否唯一
	    SiteGroupSiteEntity siteEntity = queryGroupSiteBySeqVirtualCode(entity);
	    if(null == siteEntity){
		int result = siteGroupSiteDao.addSiteGroupSite(entity);
		return result > 0 ? entity : null;
	    }else {
		throw new SiteGroupSiteException("站点序号已重复！");
	    }
	}

	return null;
    }

    /**
     * 根据传入对象查询符合条件所有站点组站点信息.
     *
     * @param entity 站点组站点实体
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @throws SiteGroupSiteException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:02:19
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#querySiteGroupSites(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity,
     * int, int)
     */
    @Transactional
    @Override
    public List<SiteGroupSiteEntity> querySiteGroupSites(SiteGroupSiteEntity entity, int limit, int start) throws SiteGroupSiteException {
	entity.setActive(FossConstants.ACTIVE);
	return siteGroupSiteDao.querySiteGroupSites(entity, limit, start);
    }

    /**
     * 统计总记录数.
     *
     * @param entity 站点组站点实体
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午11:02:33
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(SiteGroupSiteEntity entity) {

	entity.setActive(FossConstants.ACTIVE);
	return siteGroupSiteDao.queryRecordCount(entity);
    }
    
    /**
     * <p>根据站点组虚拟编码查询所有站点</p>.
     *
     * @param siteGroupCode 站点组虚拟编码
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-8 下午2:32:13
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#queryAllSites(java.lang.String)
     */
    @Override
    public List<SiteGroupSiteEntity> queryAllSitesByCode(String siteGroupCode) {
	// 检查参数
	if(StringUtil.isBlank(siteGroupCode)){
	    throw new SiteGroupSiteException("虚拟编码不允许为空！");
	}
	LOGGER.debug("siteGroupCode: "+siteGroupCode);
	return siteGroupSiteDao.queryAllSitesByCode(siteGroupCode);
    }
    
    /**
     * <p>验证所属站点组虚拟编码、站点序号是否唯一</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-28 上午11:05:08
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#queryGroupSiteBySeqVirtualCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Override
    public SiteGroupSiteEntity queryGroupSiteBySeqVirtualCode(SiteGroupSiteEntity entity) {
	if(null == entity){
	    throw new SiteGroupSiteException("传入的参数不允许为空！");
	}else {
	    if(null == entity.getParentOrgCode()){
		throw new SiteGroupSiteException("所属站点组虚拟编码不允许为空！");
	    }else {
		return siteGroupSiteDao.queryGroupSiteBySeqVirtualCode(entity);
	    }
	}
    }
    
    /**
     * <p>验证所属站点组站点是否重复</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-06-04 上午11:05:08
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISiteGroupSiteService#queryGroupSiteBySeqVirtualCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SiteGroupSiteEntity)
     */
    @Override
    public SiteGroupSiteEntity queryGroupSiteBySiteCode(SiteGroupSiteEntity entity) {
	if(null == entity){
	    throw new SiteGroupSiteException("传入的参数不允许为空！");
	}else {
	    if(null == entity.getParentOrgCode()){
		throw new SiteGroupSiteException("所属站点组虚拟编码不允许为空！");
	    }else {
		return siteGroupSiteDao.queryGroupSiteBySiteCode(entity);
	    }
	}
    }

}