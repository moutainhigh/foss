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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/AdministrativeRegionsDao.java
 * 
 * FILE NAME        	: AdministrativeRegionsDao.java
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
 **相关SUC
			 *修订记录 	
			日期 	修订内容 	修订人员 	版本号 
			2012-04-24 	新增 	谢艳涛	V0.1
			2012-4-26	修改查询界面	谢艳涛	V0.2
			2012-4-26	 修改相关业务用例	谢艳涛	V0.3 
			2012-5-21	根据系统用例模板及陈兴波GIS行政区域基础数据接口要求进行修改	谢艳涛	V0.4
			2012-7-9	根据ITA评审意见修改：“虚拟城市”改为“虚拟行政区域”；增加“可用名称”属性；界面行政区域树位置移到左边。	谢艳涛	V0.41
			2012-8-6	通过业务部门审核，版本升至0.9	石巍	V0.9
			
			1.	SUC-33-新增_修改_作废_查询行政区域
			1.1	相关业务用例
			BUS_FOSS_5.20.30_510 确认承运信息
			1.2	用例描述
			本系统用例统一维护所有业务用例中涉及的行政区域的基础信息，可对行政区域基础资料进行新增、修改、作废、查询操作。
			1.3	用例条件
			条件类型	描述	引用系统用例
			前置条件		
			后置条件	1、	为始发线路、到达线路、运作到运作线路等其他系统用例提供行政区域基础资料查询	SUC-284  新增_修改_作废_查询始发线路
			SUC-218  新增_修改_作废_查询运作到运作线路信息
			SUC-740  新增_修改_作废_查询到达线路
			1.4	操作用户角色
			操作用户	描述
			系统维护人员	系统维护人员负责对行政区域基础资料进行新增，修改，作废，查询操作。
			1.5	界面要求
			1.5.1	表现方式
			Web页面
			1.5.2	界面原型-主界面
			                                        图一：行政区域主界面
			 
			                                   图二：查看行政区域信息（一）
			 
			                                  图三：查看行政区域信息（二）
			1.5.3	界面描述-主界面
			1.	功能按钮区域
			1)	新增按钮：点击新增按钮进入新增界面，参见【图四：新增/修改行政区域】。
			2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
			3)	重置按钮：点击重置按钮，清空查询条件。
			4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废图标，作废各行记录，作废时连同作废该行政区域所包含的子行政区域，需要弹出确认提示框。
			5)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息，参见【图三：查看行政区域信息（二）】；点击左侧行政区域树的某一行政区域也可以查看该行政区域的详细信息，参见【图二：查看行政区域信息（一）】。
			6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：新增/修改行政区域】。
			7)	分页按钮：实现分页功能。
			2.	列表区域
			1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
			2)	列表中显示：编码、区域全称、简称、可用名称、上级区域名称、虚拟行政区域。
			3.	字段输入区域
			1)	查询条件包括区域全称、简称、虚拟行政区域。
			1.1	区域全称：文本，行政区域名称，支持模糊查询
			1.2	简称：文本，行政区域简称，支持模糊查询
			1.3	虚拟行政区域：下拉框，默认全部，包含全部、是、否；表示行政区域是否为虚拟的；
			1.5.4	界面原型-新增/修改行政区域
			 
			图四：新增/修改行政区域
			1.5.5	界面描述-新增和修改始发线路
			1.	字段输入区域
			1)	编码： 必填，文本，编码必须唯一
			2)	区域全称：必填，文本，行政区域全称，单位从下拉框选择，包含省、自治州、市、区、县 
			3)	简称：必填，文本，行政区域简称
			4)	可用名称：必填，文本，行政区域有哪些可用的名称
			5)	上级行政区域：必填，选择框，从行政区域树基础资料中选取
			6)	虚拟行政区域：必填，单选框，默认为否，表示该行政区域是否是虚拟的行政区域；
			7)	行政区域级别：必填，单选框，包括：国家、省、市、区县
			7)8)	界面新增加一个是否热门城市单选框
			2.	功能按钮区域
			1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
			2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
			3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
			1.6	操作步骤
			1.6.1	添加行政区域操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	点击新增按钮，进入【图四：新增/修改行政区域】界面		
			3	输入行政区域详细信息，点击保存。
			参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
			4	返回行政区域管理主界面		
			
			序号	扩展事件	相关数据	备注
			3a	点击取消按钮，退出当前界面，返回主界面		
			3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
			
			1.6.2	修改行政区域操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	点击修改按钮，进入【图四：新增/修改行政区域】界面		
			3	修改行政区域详细信息,点击保存
			参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
			4	返回行政区域管理主界面		
			
			序号	扩展事件	相关数据	备注
			3a	点击取消按钮，退出当前界面，返回主界面		
			3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		
			
			1.6.3	作废始发线路操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	选择一行或者多行记录，点击作废按钮。		作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
			3	点击确定按钮。		
			
			序号	扩展事件	相关数据	备注
			2a	点击取消按钮，退出当前界面，返回主界面		
			2b	若作废失败，需提示用户作废失败以及失败原因		
			
			1.6.4	查询行政区域操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	输入查询条件，点击查询按钮。参见业务规则SR-4	【行政区域查询条件】	系统返回查询结果
			1.7	业务规则
			序号	描述
			SR-1	新增/修改页面，行政区域“编码”不能重复； 
			SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
			SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
			SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
			
			1.8	数据元素
			1.8.1	行政区域新增/修改信息
			字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
			编码	行政区域编码	文本		10	是	
			区域全称	行政区域名称	文本		50	是	区域全称后面还有下拉框显示行政区域单位，包含：省、自治州、市、区、县
			简称	行政区域名称简称	文本		50	是	
			可用名称	行政区域曾经用过和现在用过的名称	文本		100	是	
			上级行政区域	行政区域上级区域名称	选择框		50	是	从行政区域树基础资料中选择
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	单选框		10	是	默认为否
			行政区域级别	行政区域所属级别，包括：国家、省、市、区县	单选框		10	是	
			1.8.2	行政区域列表信息
			字段名称 	说明 	输入限制	长度	是否必填	备注
			编码	行政区域编码	N/A	10	N/A	
			区域全称	行政区域名称	N/A	50	N/A	
			简称	行政区域名称简称	N/A	50	N/A	
			可用名称	行政区域曾经用过和现在用过的名称	N/A	100	N/A	
			上级行政区域	行政区域上级区域名称	N/A	50	N/A	
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	N/A	10	N/A	
			1.8.3	行政区域查询条件
			字段名称 	说明 	输入限制	长度	是否必填	备注
			区域全称	行政区域名称，支持模糊查询	文本	50	否	
			简称	行政区域名称简称，支持模糊查询	文本	50	否	
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在，默认为全部，包含：全部、是、否	文本	10	否	
			1.9	非功能性需求
			使用量	
			2012年全网估计用户数	
			响应要求（如果与全系统要求 不一致的话）	
			使用时间段	
			高峰使用时间段	
			
			1.10	接口描述
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
						 *修订记录 	
			日期 	修订内容 	修订人员 	版本号 
			2012-04-24 	新增 	谢艳涛	V0.1
			2012-4-26	修改查询界面	谢艳涛	V0.2
			2012-4-26	 修改相关业务用例	谢艳涛	V0.3 
			2012-5-21	根据系统用例模板及陈兴波GIS行政区域基础数据接口要求进行修改	谢艳涛	V0.4
			2012-7-9	根据ITA评审意见修改：“虚拟城市”改为“虚拟行政区域”；增加“可用名称”属性；界面行政区域树位置移到左边。	谢艳涛	V0.41
			2012-8-6	通过业务部门审核，版本升至0.9	石巍	V0.9
			
			1.	SUC-33-新增_修改_作废_查询行政区域
			1.1	相关业务用例
			BUS_FOSS_5.20.30_510 确认承运信息
			1.2	用例描述
			本系统用例统一维护所有业务用例中涉及的行政区域的基础信息，可对行政区域基础资料进行新增、修改、作废、查询操作。
			1.3	用例条件
			条件类型	描述	引用系统用例
			前置条件		
			后置条件	1、	为始发线路、到达线路、运作到运作线路等其他系统用例提供行政区域基础资料查询	SUC-284  新增_修改_作废_查询始发线路
			SUC-218  新增_修改_作废_查询运作到运作线路信息
			SUC-740  新增_修改_作废_查询到达线路
			1.4	操作用户角色
			操作用户	描述
			系统维护人员	系统维护人员负责对行政区域基础资料进行新增，修改，作废，查询操作。
			1.5	界面要求
			1.5.1	表现方式
			Web页面
			1.5.2	界面原型-主界面
			                                        图一：行政区域主界面
			 
			                                   图二：查看行政区域信息（一）
			 
			                                  图三：查看行政区域信息（二）
			1.5.3	界面描述-主界面
			1.	功能按钮区域
			1)	新增按钮：点击新增按钮进入新增界面，参见【图四：新增/修改行政区域】。
			2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
			3)	重置按钮：点击重置按钮，清空查询条件。
			4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废图标，作废各行记录，作废时连同作废该行政区域所包含的子行政区域，需要弹出确认提示框。
			5)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息，参见【图三：查看行政区域信息（二）】；点击左侧行政区域树的某一行政区域也可以查看该行政区域的详细信息，参见【图二：查看行政区域信息（一）】。
			6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：新增/修改行政区域】。
			7)	分页按钮：实现分页功能。
			2.	列表区域
			1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
			2)	列表中显示：编码、区域全称、简称、可用名称、上级区域名称、虚拟行政区域。
			3.	字段输入区域
			1)	查询条件包括区域全称、简称、虚拟行政区域。
			1.1	区域全称：文本，行政区域名称，支持模糊查询
			1.2	简称：文本，行政区域简称，支持模糊查询
			1.3	虚拟行政区域：下拉框，默认全部，包含全部、是、否；表示行政区域是否为虚拟的；
			1.5.4	界面原型-新增/修改行政区域
			1.8	数据元素
			1.8.1	行政区域新增/修改信息
			字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
			编码	行政区域编码	文本		10	是	
			区域全称	行政区域名称	文本		50	是	区域全称后面还有下拉框显示行政区域单位，包含：省、自治州、市、区、县
			简称	行政区域名称简称	文本		50	是	
			可用名称	行政区域曾经用过和现在用过的名称	文本		100	是	
			上级行政区域	行政区域上级区域名称	选择框		50	是	从行政区域树基础资料中选择
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	单选框		10	是	默认为否
			行政区域级别	行政区域所属级别，包括：国家、省、市、区县	单选框		10	是	
			1.8.2	行政区域列表信息
			字段名称 	说明 	输入限制	长度	是否必填	备注
			编码	行政区域编码	N/A	10	N/A	
			区域全称	行政区域名称	N/A	50	N/A	
			简称	行政区域名称简称	N/A	50	N/A	
			可用名称	行政区域曾经用过和现在用过的名称	N/A	100	N/A	
			上级行政区域	行政区域上级区域名称	N/A	50	N/A	
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	N/A	10	N/A	
			1.8.3	行政区域查询条件
			字段名称 	说明 	输入限制	长度	是否必填	备注
			区域全称	行政区域名称，支持模糊查询	文本	50	否	
			简称	行政区域名称简称，支持模糊查询	文本	50	否	
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在，默认为全部，包含：全部、是、否	文本	10	否	
			1.9	非功能性需求
			使用量	
			2012年全网估计用户数	
			响应要求（如果与全系统要求 不一致的话）	
			使用时间段	
			高峰使用时间段	
			
			1.10	接口描述
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
						 *修订记录 	
			日期 	修订内容 	修订人员 	版本号 
			2012-04-24 	新增 	谢艳涛	V0.1
			2012-4-26	修改查询界面	谢艳涛	V0.2
			2012-4-26	 修改相关业务用例	谢艳涛	V0.3 
			2012-5-21	根据系统用例模板及陈兴波GIS行政区域基础数据接口要求进行修改	谢艳涛	V0.4
			2012-7-9	根据ITA评审意见修改：“虚拟城市”改为“虚拟行政区域”；增加“可用名称”属性；界面行政区域树位置移到左边。	谢艳涛	V0.41
			2012-8-6	通过业务部门审核，版本升至0.9	石巍	V0.9
			
			1.	SUC-33-新增_修改_作废_查询行政区域
			1.1	相关业务用例
			BUS_FOSS_5.20.30_510 确认承运信息
			1.2	用例描述
			本系统用例统一维护所有业务用例中涉及的行政区域的基础信息，可对行政区域基础资料进行新增、修改、作废、查询操作。
			1.3	用例条件
			条件类型	描述	引用系统用例
			前置条件		
			后置条件	1、	为始发线路、到达线路、运作到运作线路等其他系统用例提供行政区域基础资料查询	SUC-284  新增_修改_作废_查询始发线路
			SUC-218  新增_修改_作废_查询运作到运作线路信息
			SUC-740  新增_修改_作废_查询到达线路
			1.4	操作用户角色
			操作用户	描述
			系统维护人员	系统维护人员负责对行政区域基础资料进行新增，修改，作废，查询操作。
			1.5	界面要求
			1.5.1	表现方式
			Web页面
			1.5.2	界面原型-主界面
			                                        图一：行政区域主界面
			 
			                                   图二：查看行政区域信息（一）
			 
			                                  图三：查看行政区域信息（二）
			1.5.3	界面描述-主界面
			1.	功能按钮区域
			1)	新增按钮：点击新增按钮进入新增界面，参见【图四：新增/修改行政区域】。
			2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
			3)	重置按钮：点击重置按钮，清空查询条件。
			4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废图标，作废各行记录，作废时连同作废该行政区域所包含的子行政区域，需要弹出确认提示框。
			5)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息，参见【图三：查看行政区域信息（二）】；点击左侧行政区域树的某一行政区域也可以查看该行政区域的详细信息，参见【图二：查看行政区域信息（一）】。
			6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：新增/修改行政区域】。
			7)	分页按钮：实现分页功能。
			2.	列表区域
			1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
			2)	列表中显示：编码、区域全称、简称、可用名称、上级区域名称、虚拟行政区域。
			3.	字段输入区域
			1)	查询条件包括区域全称、简称、虚拟行政区域。
			1.1	区域全称：文本，行政区域名称，支持模糊查询
			1.2	简称：文本，行政区域简称，支持模糊查询
			1.3	虚拟行政区域：下拉框，默认全部，包含全部、是、否；表示行政区域是否为虚拟的；
			1.5.4	界面原型-新增/修改行政区域
			 
			图四：新增/修改行政区域
			1.5.5	界面描述-新增和修改始发线路
			1.	字段输入区域
			1)	编码： 必填，文本，编码必须唯一
			2)	区域全称：必填，文本，行政区域全称，单位从下拉框选择，包含省、自治州、市、区、县 
			3)	简称：必填，文本，行政区域简称
			4)	可用名称：必填，文本，行政区域有哪些可用的名称
			5)	上级行政区域：必填，选择框，从行政区域树基础资料中选取
			6)	虚拟行政区域：必填，单选框，默认为否，表示该行政区域是否是虚拟的行政区域；
			7)	行政区域级别：必填，单选框，包括：国家、省、市、区县
			7)8)	界面新增加一个是否热门城市单选框
			2.	功能按钮区域
			1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
			2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
			3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
			1.6	操作步骤
			1.6.1	添加行政区域操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	点击新增按钮，进入【图四：新增/修改行政区域】界面		
			3	输入行政区域详细信息，点击保存。
			参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
			4	返回行政区域管理主界面		
			
			序号	扩展事件	相关数据	备注
			3a	点击取消按钮，退出当前界面，返回主界面		
			3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
			
			1.6.2	修改行政区域操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	点击修改按钮，进入【图四：新增/修改行政区域】界面		
			3	修改行政区域详细信息,点击保存
			参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
			4	返回行政区域管理主界面		
			
			序号	扩展事件	相关数据	备注
			3a	点击取消按钮，退出当前界面，返回主界面		
			3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		
			
			1.6.3	作废始发线路操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	选择一行或者多行记录，点击作废按钮。		作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
			3	点击确定按钮。		
			
			序号	扩展事件	相关数据	备注
			2a	点击取消按钮，退出当前界面，返回主界面		
			2b	若作废失败，需提示用户作废失败以及失败原因		
			
			1.6.4	查询行政区域操作步骤
			序号	基本步骤	相关数据	补充步骤
			1	进入行政区域管理主界面	【行政区域列表信息】	
			2	输入查询条件，点击查询按钮。参见业务规则SR-4	【行政区域查询条件】	系统返回查询结果
			1.7	业务规则
			序号	描述
			SR-1	新增/修改页面，行政区域“编码”不能重复； 
			SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
			SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
			SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
			
			1.8	数据元素
			1.8.1	行政区域新增/修改信息
			字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
			编码	行政区域编码	文本		10	是	
			区域全称	行政区域名称	文本		50	是	区域全称后面还有下拉框显示行政区域单位，包含：省、自治州、市、区、县
			简称	行政区域名称简称	文本		50	是	
			可用名称	行政区域曾经用过和现在用过的名称	文本		100	是	
			上级行政区域	行政区域上级区域名称	选择框		50	是	从行政区域树基础资料中选择
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	单选框		10	是	默认为否
			行政区域级别	行政区域所属级别，包括：国家、省、市、区县	单选框		10	是	
			1.8.2	行政区域列表信息
			字段名称 	说明 	输入限制	长度	是否必填	备注
			编码	行政区域编码	N/A	10	N/A	
			区域全称	行政区域名称	N/A	50	N/A	
			简称	行政区域名称简称	N/A	50	N/A	
			可用名称	行政区域曾经用过和现在用过的名称	N/A	100	N/A	
			上级行政区域	行政区域上级区域名称	N/A	50	N/A	
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在	N/A	10	N/A	
			1.8.3	行政区域查询条件
			字段名称 	说明 	输入限制	长度	是否必填	备注
			区域全称	行政区域名称，支持模糊查询	文本	50	否	
			简称	行政区域名称简称，支持模糊查询	文本	50	否	
			虚拟行政区域	行政区域是否为虚拟的，在实际行政区域中不存在，默认为全部，包含：全部、是、否	文本	10	否	
			1.9	非功能性需求
			使用量	
			2012年全网估计用户数	
			响应要求（如果与全系统要求 不一致的话）	
			使用时间段	
			高峰使用时间段	
			
			1.10	接口描述
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 */ 
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AdministrativeRegionsDto;

/**
 * 行政区域 DAO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:54:55
 */
public class AdministrativeRegionsDao extends SqlSessionDaoSupport implements
	IAdministrativeRegionsDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".administrativeRegions.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午2:38:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#addAdministrativeRegions(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
    	 *  1 进入行政区域管理主界面 【行政区域列表信息】
    	 *  
	 *  2点击新增按钮，进入【图四：新增/修改行政区域】界面 
	 *  
	 *  3 输入行政区域详细信息， 点击保存。 参见业务规则SR-1、SR-2、SR-3【行政区域新增/修改信息】
	 *  
	 *  4 返回行政区域管理主界面
	 *  
	 *  序号 扩展事件 相关数据 备注 
	 *  
	 *  3a 点击取消按钮，退出当前界面，返回主界面 
	 *  
	 *  3b若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面
     */
    @Override
    @Transactional
    public AdministrativeRegionsEntity addAdministrativeRegions(
	    AdministrativeRegionsEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
	entity.setActive(FossConstants.ACTIVE);
	
	int result = getSqlSession().insert(
		NAMESPACE + "addAdministrativeRegions", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 通过CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午2:38:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#deleteAdministrativeRegions(java.lang.String)
     * * 1 进入行政区域管理主界面 【行政区域列表信息】 
     * 
	 * 2 选择一行或者多行记录，点击作废按钮。
	 * 
	 *  作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
	 *  
	 * 3 点击确定按钮。
	 * 
	 * 序号 扩展事件 相关数据 备注 
	 * 
	 * 2a 点击取消按钮，退出当前界面，返回主界面 
	 * 
	 * 2b 若作废失败，需提示用户作废失败以及失败原因
     */
    @Override
    @Transactional
    public AdministrativeRegionsEntity deleteAdministrativeRegions(
	    AdministrativeRegionsEntity entity) {
	// 请求参数合法性验证
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	// entity应包含modifyUser,因此不用处理
	entity.setActive(FossConstants.INACTIVE);

	Map<String, Object> map = new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(
		NAMESPACE + "deleteAdministrativeRegions", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午2:38:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#deleteAdministrativeRegionsMore(java.lang.String[],
     *      java.lang.String)
     *  * 1 进入行政区域管理主界面 【行政区域列表信息】 
     *  
	 * 2 选择一行或者多行记录，点击作废按钮。
	 * 
	 *  作废时连同作废该行政区域所包含的子行政区域，弹出确认对话框。
	 *  
	 * 3 点击确定按钮。
	 * 
	 * 
	 * 序号 扩展事件 相关数据 备注 
	 * 
	 * 2a 点击取消按钮，退出当前界面，返回主界面 
	 * 
	 * 2b 若作废失败，需提示用户作废失败以及失败原因
     */
    @Override
    @Transactional
    public AdministrativeRegionsEntity deleteAdministrativeRegionsMore(
	    String[] codes, String deleteUser) {
	// 请求合法性判断：
	if (ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	// 处理删除时要更新的数据
	Date now = new Date();
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	entity.setModifyUser(deleteUser);
	entity.setActive(FossConstants.INACTIVE);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(
		NAMESPACE + "deleteAdministrativeRegionsMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 通过CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-31 下午2:38:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#updateAdministrativeRegions(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
     *  1	进入行政区域管理主界面	【行政区域列表信息】	
     *  
     *  2	点击修改按钮，进入【图四：新增/修改行政区域】界面	
     *  	
     *  3	修改行政区域详细信息,点击保存
     *  
     *  
               *                 参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
     *                           
      * 4	返回行政区域管理主界面		
    *
    *
             *                  序号	扩展事件	相关数据	备注
             *                  
    *   3a	点击取消按钮，退出当前界面，返回主界面		
    *   
    *   3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面	
     */
    @Override
    @Transactional
    public AdministrativeRegionsEntity updateAdministrativeRegions(
	    AdministrativeRegionsEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return entity;
	}

	// 更新要先删除旧的数据：
	AdministrativeRegionsEntity result = this
		.deleteAdministrativeRegions(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}
	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	entity.setVersionNo(System.currentTimeMillis());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());

	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(
		NAMESPACE + "addAdministrativeRegions", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 以下全为查询：
     */

    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-31 下午3:10:19
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByCode(java.lang.String)
     * 1.6.4	查询行政区域操作步骤
               *                          序号	基本步骤	相关数据	补充步骤
               *                          
               *                          
      *        1	进入行政区域管理主界面	【行政区域列表信息】	
      *        
      *        
      *       2	输入查询条件，点击查询按钮。参见业务规则SR-4	
      *       
      *                   【行政区域查询条件】	系统返回查询结果
      *       1.7	业务规则
      *       
      *      序号	描述
      *       SR-1	新增/修改页面，行政区域“编码”不能重复； 
      *       
      *       SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
      *       
      *      SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
      *      
      *      SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
      *      
     */
    @SuppressWarnings("unchecked")
    @Override
    public AdministrativeRegionsEntity queryAdministrativeRegionsByCode(
	    String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}

	// 构造查询条件：
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);

	List<AdministrativeRegionsEntity> entitys = this.getSqlSession()
		.selectList(NAMESPACE + "queryAdministrativeRegionsByCode",
			entity);
	if (entitys == null || entitys.isEmpty()) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public String queryRegionNameByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	// 构造查询条件：
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	List<String> list = getSqlSession().selectList(
		NAMESPACE + "queryRegionNameByCode", entity);
	if (CollectionUtils.isEmpty(list)) {
	    return null;
	}
	return list.get(NumberConstants.ZERO);
    }

    /**
     * 通过 标识编码CODE,是否有效ACTIVE精确查询
     * 
     * 两个参数都可传空，当传空时，不做为查询条件，查询时，取更新时间最近的一条
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-11-2 下午2:39:55
     * 
     * @param code
     *            标识编码（组织编码）
     * @param active
     *            FossConstants.YES:FossConstants.NO
     *            
     *            1.6.4	查询行政区域操作步骤
     *            
     *                           序号	基本步骤	相关数据	补充步骤
     *                           
      *       1	进入行政区域管理主界面	【行政区域列表信息】	
      *       
      *       2	输入查询条件，点击查询按钮。参见业务规则SR-4	
      *       
                *                 【行政区域查询条件】	系统返回查询结果
                *                 
       *      1.7	业务规则
                  *  序号	描述
                  *  
       *      SR-1	新增/修改页面，行政区域“编码”不能重复； 
       *      
       *      SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
       *      
        *     SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
        *     
        *     SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsByCodeActive(
	    List<String> codes, String active) {
	// 请求合法性判断
	if (CollectionUtils.isEmpty(codes)) {
	    return null;
	}
	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("active", active);

	List<AdministrativeRegionsEntity> entitys = this.getSqlSession()
		.selectList(
			NAMESPACE + "queryAdministrativeRegionsByCodeActive",
			map);
	return entitys;
    }
    /**
     * 精确查询 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-18 下午4:1:47
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByCode(java.lang.String)
     * 1.6.4	查询行政区域操作步骤
     * 
             *                            序号	基本步骤	相关数据	补充步骤
             *                            
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
              *                   【行政区域查询条件】	系统返回查询结果
              *                   
     *        1.7	业务规则
              *      序号	描述
              *      
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
      **       SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
      *
      *       SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
      *       
      *       SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
      *       
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsBatchByCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (codes == null || codes.length == 0) {
	    return null;
	}
	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);

	List<AdministrativeRegionsEntity> entitys = this.getSqlSession()
		.selectList(
			NAMESPACE + "queryAdministrativeRegionsBatchByCode",
			map);
	return entitys;
    }
    /**
     * 根据AdministrativeRegionsEntity实体属性动态分页查询行政区域信息
     * 
     * 只查询有效信息
     * 
     * @author 087584-foss-lijun
     * 
     * @date 2012-10-19 上午11:11:15
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity,
     *      int, int)
     *      1.6.4	查询行政区域操作步骤
     *      
             *                            序号	基本步骤	相关数据	补充步骤
             *                            
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
              *                   【行政区域查询条件】	系统返回查询结果
              *                   
     *        1.7	业务规则
     *        
      *        *      序号	描述
      *        
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
      *       SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
      *       
       *      SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
       *      
       *      SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
       *      
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsExactByEntity(
	    AdministrativeRegionsEntity entity, int start, int limit) {
	AdministrativeRegionsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new AdministrativeRegionsEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(
		NAMESPACE + "queryAdministrativeRegionsExactByEntity",
		queryEntity, rowBounds);
    }
    /**
     * 精确查询-查询总条数，用于分页 动态的查询条件。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * 
     * IAdministrativeRegionsDao#queryAdministrativeRegionsExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
     * 1.6.4	查询行政区域操作步骤
             *                            序号	基本步骤	相关数据	补充步骤
             *                            
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
              *                   【行政区域查询条件】	系统返回查询结果
              *                   
     *        1.7	业务规则
              *      序号	描述
              *      
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
     *        SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
     *        
     *        SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
     *        
     *        SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     */
    @Override
    public long queryAdministrativeRegionsExactByEntityCount(
	    AdministrativeRegionsEntity entity) {
	AdministrativeRegionsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new AdministrativeRegionsEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long) getSqlSession().selectOne(
		NAMESPACE + "queryAdministrativeRegionsExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午12:59:33
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity,
     *      int, int)
     *      1.6.4	查询行政区域操作步骤
     *      
             *                            序号	基本步骤	相关数据	补充步骤
             *                            
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
             *                    【行政区域查询条件】	系统返回查询结
             *                    果
     *        1.7	业务规则
     *        
             *       序号	描
             *       述
             *       
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
      *       SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
      *       
      *       SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
      *       
      *       SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsByEntity(
	    AdministrativeRegionsEntity entity, int start, int limit) {
	AdministrativeRegionsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new AdministrativeRegionsEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(
		NAMESPACE + "queryAdministrativeRegionsByEntity", queryEntity,
		rowBounds);
    }

    /**
     * 模糊查询 动态的查询条件-查询总条数。
     * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 上午12:59:33
     * @see com.deppon.foss.module.base.baseinfo.server.dao.
     * IAdministrativeRegionsDao#queryDynimicCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity)
     * 1.6.4	查询行政区域操作步骤
     *                            序号	基本步骤	相关数据	补充步骤
     *                            
     *       1	进入行政区域管理主界面	【行政区域列表信息】	
     *       
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
     *                   【行政区域查询条件】	系统返回查询结果
     *                   
     *        1.7	业务规则
     *        序号	描述
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
     *        SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
     *        
     *        SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
     *        
     *        SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     */
    @Override
    public long queryAdministrativeRegionsByEntityCount(
	    AdministrativeRegionsEntity entity) {
	AdministrativeRegionsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new AdministrativeRegionsEntity();
	} else {
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long) getSqlSession().selectOne(
		NAMESPACE + "queryAdministrativeRegionsByEntityCount",
		queryEntity);
    }

    /**
     * 根据entity精确查询 entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午4:49:15
     * 1.6.4	查询行政区域操作步骤
     *                            序号	基本步骤	相关数据	补充步骤
     *                            
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
             *                   【行政区域查询条件】	系统返回查询结果
             *                   
     *        1.7	业务规则
            *        序号	描述
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
     *        SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
     *        
     *        SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
     *        
     *        SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsForDownload(
	    AdministrativeRegionsEntity entity) {
	AdministrativeRegionsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new AdministrativeRegionsEntity();
	} else {
	    queryEntity = entity;
	}
	return (List<AdministrativeRegionsEntity>) getSqlSession().selectList(
		NAMESPACE + "queryAdministrativeRegionsForDownload",
		queryEntity);
    }
    
    /**
     * 下载分页查询
     * 查询行政区域
     * @author 157229-foss-zxy
     * @date 2014-04-17 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsForDownloadByPage(
	    AdministrativeRegionsEntity entity,int start, int limited) {
	AdministrativeRegionsEntity queryEntity;
	RowBounds rowBounds = new RowBounds(start, limited);
	if (null == entity) {
	    queryEntity = new AdministrativeRegionsEntity();
	} else {
	    queryEntity = entity; 
	}
	return (List<AdministrativeRegionsEntity>) getSqlSession().selectList(
		NAMESPACE + "queryAdministrativeRegionsForDownload",
		queryEntity,rowBounds);
    }
    /**
     * 下面是特殊查询
     */
    /**
     * 通过上级编码查询
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午4:57:12
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.
     * IAdministrativeRegionsDao#queryAdministrativeRegionsByParentDistrictCode(java.lang.String)
     * 1.6.4	查询行政区域操作步骤
             *                            序号	基本步骤	相关数据	补充步骤
             *                            
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
             *                    【行政区域查询条件】	系统返回查询结果
             *                    
     *        1.7	业务规则
              *      序号	描述
              *      
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
     *        SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
     *        
     *        SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
     *        
     *        SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryAdministrativeRegionsByParentDistrictCode(
	    String parentDistrictCode) {
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	entity.setParentDistrictCode(parentDistrictCode);
	entity.setActive(FossConstants.ACTIVE);
	List<AdministrativeRegionsEntity> entitys = getSqlSession().selectList(
		NAMESPACE + "queryAdministrativeRegionsByParentDistrictCode",
		entity);
	return entitys;
    }
    /**
     * 查询顶级行政区域
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-13 下午4:57:39
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryRoot()
     * 1.6.4	查询行政区域操作步骤
     * 
            *                             序号	基本步骤	相关数据	补充步骤
            *                             
     *        1	进入行政区域管理主界面	【行政区域列表信息】	
     *        
     *        2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *        
     *        *                    【行政区域查询条件】	系统返回查询结果
     *        
     *        1.7	业务规则
     *        
             *       序号	描述
             *       
     *        SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *        
     *        SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
     *        
     *        SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
     *        
     *        SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
     *        
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> queryRoot() {
	AdministrativeRegionsEntity entity = new AdministrativeRegionsEntity();
	entity.setActive(FossConstants.ACTIVE);
	List<AdministrativeRegionsEntity> entitys = getSqlSession().selectList(
		NAMESPACE + "queryRoot", entity);
	return entitys;
    }
    /**
     * 获取最后跟新时间
     * 
     * @author dp-yangtong
     * 
     * @date 2012-10-30 下午4:13:48
     * 
     * @param
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @Override
    public Date getLastModifyTime() {
	long versionNo = (Long) getSqlSession().selectOne(
		NAMESPACE + "getLastUpdateTime");

	return new Date(versionNo);
    }
    /**
     * 数据更新
     * 
     * @author dp-yangtong
     * 
     * @date 2012-10-30 下午4:13:48
     * 
     * @param entity
     * 
     *            行政区域实体
     *            
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsEntity> getSyncData(Date fromDate,
	    String userID) {
	Map<String, Object> parms = new HashMap<String, Object>();
	parms.put("fromDate", fromDate);
	return this.getSqlSession().selectList(
		NAMESPACE + "getAdministrativeRegions", parms);
    }
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AdministrativeRegionsDao.class);

    /**
     * 
     * <p>根据城市名称查询城市实体</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-8 上午11:52:43
     * @param cityName
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAdministrativeRegionsDao#queryAdministrativeRegionsByName(java.lang.String)
     */
    @Override
    public AdministrativeRegionsEntity queryAdministrativeRegionsByName(
	    String cityName) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", FossConstants.ACTIVE);
	map.put("name", cityName);
	return (AdministrativeRegionsEntity) this.getSqlSession().selectOne(NAMESPACE + "queryAdministrativeRegionsByName", map);
    }
    
    /**
     * 根据行政区域代码查询快递试点网点的点坐标和范围坐标
     * 
     * @author WeiXing
     * @date 2014-08-27 下午4:57:12
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsDto> queryServerCoordinatesByCode(String code) {
	AdministrativeRegionsDto dto = new AdministrativeRegionsDto();
	dto.setCountyCode(code);
	dto.setActive(FossConstants.ACTIVE);
	List<AdministrativeRegionsDto> dtos = getSqlSession().selectList(
		NAMESPACE + "queryServerCoordinates",
		dto);
	return dtos;
    }
    
    /**
     * 根据行政区域代码查询非试点快递网点的点坐标和范围坐标
     * 
     * @author WeiXing
     * @date 2014-08-27 下午4:57:12
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<AdministrativeRegionsDto> queryServerCoordinatesNotByCode(String code) {
	AdministrativeRegionsDto dto = new AdministrativeRegionsDto();
	dto.setCountyCode(code);
	dto.setActive(FossConstants.ACTIVE);
	List<AdministrativeRegionsDto> dtos = getSqlSession().selectList(
		NAMESPACE + "queryServerCoordinatesNot",
		dto);
	return dtos;
    }
}
