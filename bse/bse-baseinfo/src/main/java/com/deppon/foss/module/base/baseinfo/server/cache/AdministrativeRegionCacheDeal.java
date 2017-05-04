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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/cache/AdministrativeRegionCacheDeal.java
 * 
 * FILE NAME        	: AdministrativeRegionCacheDeal.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * *相关SUC
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
			*
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
			*
			1.10	接口描述
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
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
			*
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
			*
			1.10	接口描述
			接口名称 	对方系统（外部系统或内部其他模块）	接口描述
 ******************************************************************************/

package com.deppon.foss.module.base.baseinfo.server.cache;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;

public class AdministrativeRegionCacheDeal {
	@SuppressWarnings("rawtypes")
	private CacheManager cacheManager;
	@SuppressWarnings("unchecked")
	private ICache<String, Object> getCache() {
		ICache<String, Object> cityEntityCache = getInstanceCache().getCache(
				AdministrativeRegionCacheProvider.class.getName());
		return cityEntityCache;
	}
	/**
	 * Description:获取所有省份<br />
	 * 
	 * @author 078823-foss-panGuangjun
	 * 
	 * @version 0.1 2012-3-16
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getAllProvinceList() {
		List<AdministrativeRegionsEntity> provinceList = (List<AdministrativeRegionsEntity>) getCache()
				.get("province");
		return provinceList;
	}
	/**
	 * Description:获取对应省份的城市<br />
	 * 
	 * @author 078823-foss-panGuangjun
	 * @version 0.1 2012-3-16
	 * @param 省份ID
	 * @return 城市列表
	 */
	public List<AdministrativeRegionsEntity> getAdministrativeRegionsEntityByProvince(
			String provinceId) {
		List<AdministrativeRegionsEntity> administrativeRegionsEntityList = this
				.getAllProvinceList();
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		// 遍历城市列表，获取传进来省份的城市列表
		for (AdministrativeRegionsEntity c : administrativeRegionsEntityList) {
			if (provinceId.equals(c.getParentDistrictCode())) {
				targetList.add(c);
			}
		}
		return targetList;
	}
	/**
	 * Description:获取缓存<br />
	 * 
	 * @author 078823-foss-panGuangjun
	 * @version 0.1 2012-3-16
	 * @param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public CacheManager getInstanceCache() {
		if (cacheManager == null) {
			cacheManager = CacheManager.getInstance();
		}
		return cacheManager;
	}
	/**
	 * 
	 * Description:根据id查询对应城市<br />
	 * 
	 * @author 078823-foss-panGuangjun
	 * @version 0.1 2012-3-27
	 * @param 城市id
	 * @return
	 */
	public List<AdministrativeRegionsEntity> searchCityById(String id) {
		List<AdministrativeRegionsEntity> cityList = getAllCityList();
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		for (AdministrativeRegionsEntity c : cityList) {
			if (c.getId() != null && c.getId().equals(id)) {
				targetList.add(c);
				break;
			}
		}
		return targetList;
	}
	/**
	 * 从缓存拿到所有城市
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-15 下午3:00:45
	 * @return List<AdministrativeRegionsEntity>
	 * @param
	 */
	@SuppressWarnings("unchecked")
	private List<AdministrativeRegionsEntity> getAllCityList() {
		List<AdministrativeRegionsEntity> regionList = (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		List<AdministrativeRegionsEntity> cityList = this.getRegionByType(
				regionList, DictionaryValueConstants.DISTRICT_PROVINCE);
		return cityList;
	}
	/**
	 * 
	 * Description:根据id查询对应省份<br />
	 * 
	 * @author 078823-foss-panGuangjun
	 * @version 0.1 2012-3-27
	 * @param id
	 * @return
	 */
	public List<AdministrativeRegionsEntity> searchProvinceById(String id) {
		List<AdministrativeRegionsEntity> provinceList = getProvinceList();
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		for (AdministrativeRegionsEntity p : provinceList) {
			if (p.getId() != null && p.getId().equals(id)) {
				targetList.add(p);
				break;
			}
		}
		return targetList;
	}
	/**
	 * 从缓存拿到所有省份
	 * 
	 * @author 078823-foss-panGuangJun
	 * 
	 * @date 2012-12-15 下午2:59:35
	 * 
	 * @return List<AdministrativeRegionsEntity>
	 * 
	 * @param
	 */
	private List<AdministrativeRegionsEntity> getProvinceList() {
		return getAllCityList();
	}
	/**
	 * 根据省份编码获得城市信息
	 * 
	 * @author panGuangJun
	 * @date 2012-11-29 下午4:09:28
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getCityByProvince(
			String provinceCode) {
		List<AdministrativeRegionsEntity> regionList = (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		List<AdministrativeRegionsEntity> cityList = this.getRegionByType(
				regionList, DictionaryValueConstants.DISTRICT_CITY);
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		if (CollectionUtils.isEmpty(cityList)) {
			return null;
		}
		for (AdministrativeRegionsEntity c : cityList) {
			if (null != c
					&& StringUtil.equals(c.getParentDistrictCode(),
							provinceCode)) {
				targetList.add(c);
			}
		}
		return targetList;
	}
	/**
	 * 根据名称获得城市信息
	 * 
	 * @author panGuangJun
	 * 
	 * @date 2012-11-29 下午4:09:38
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getCityByName(String name,
			int start, int limit) {
		List<AdministrativeRegionsEntity> regionList = (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		List<AdministrativeRegionsEntity> cityList = this.getRegionByType(
				regionList, DictionaryValueConstants.DISTRICT_CITY);
		List<AdministrativeRegionsEntity> tagertCityList = getPagingAdministativeReginByName(
				name, start, limit, cityList);
		return tagertCityList;
	}
	/**
	 * 返回合同条件的行政区域
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 下午4:19:59
	 * @return List<AdministrativeRegionsEntity>
	 * @param
	 */
	private List<AdministrativeRegionsEntity> getPagingAdministativeReginByName(
			String name, int start, int limit,
			List<AdministrativeRegionsEntity> cityList) {
		List<AdministrativeRegionsEntity> targetList = getAdministativeReginByName(
				name, cityList);
		int length = 0;
		List<AdministrativeRegionsEntity> tagertReginList = new ArrayList<AdministrativeRegionsEntity>();
		// 如果targetList 没有数据，或者开始条数大于targetList的总数，反回空
		if (0 >= targetList.size() || start > targetList.size()) {
			return null;
			// 否则取符合条件的数据
		} else {
			length = start + limit < targetList.size() ? start + limit
					: targetList.size();
			for (int i = start; i < length; i++) {
				tagertReginList.add(targetList.get(i));
			}
		}
		return tagertReginList;
	}
	/**
	 * 获取所有符合条件的行政区域
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 下午4:23:55
	 * @return List<AdministrativeRegionsEntity>
	 * @param
	 */
	private List<AdministrativeRegionsEntity> getAdministativeReginByName(
			String name, List<AdministrativeRegionsEntity> cityList) {
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		// 缓存中没有数据，返回空
		if (StringUtil.isEmpty(name)) {
			targetList = cityList;
			// 名称不为空,则用名称比较，符合条件就假如targetList
		} else if (CollectionUtils.isNotEmpty(cityList)) {
			for (AdministrativeRegionsEntity adminRegin : cityList) {
				if (StringUtil.isEmpty(adminRegin.getName())) {
					continue;
				}
				if (adminRegin.getName().indexOf(name) > -1) {
					targetList.add(adminRegin);
				}
			}
		}
		return targetList;
	}

	/**
	 * 根据省份名称获得省份信息
	 * 
	 * @author panGuangJun
	 * @date 2012-11-29 下午4:45:20
	 */
	public List<AdministrativeRegionsEntity> getProvinceByName(String name,
			int start, int limit) {
		List<AdministrativeRegionsEntity> cityList = getProvinceList();
		List<AdministrativeRegionsEntity> targetList = getPagingAdministativeReginByName(
				name, start, limit, cityList);
		return targetList;
	}
	/**
	 * 查询所有国家
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-9 下午3:22:32
	 * @return List<AdministrativeRegionsEntity>
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getAllNationList() {
		List<AdministrativeRegionsEntity> regionList = (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		return this.getRegionByType(regionList,
				DictionaryValueConstants.DISTRICT_NATION);
	}
	/**
	 * 通过省份code查询对应省份
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 上午9:05:27
	 * @return List<AdministrativeRegionsEntity>
	 * @param
	 */
	public List<AdministrativeRegionsEntity> getProvinceByNation(String parentId) {
		List<AdministrativeRegionsEntity> provinceList = getProvinceList();
		List<AdministrativeRegionsEntity> targetList = new ArrayList<AdministrativeRegionsEntity>();
		for (AdministrativeRegionsEntity c : provinceList) {
			if (c.getParentDistrictCode() != null
					&& c.getParentDistrictCode().equals(parentId)) {
				targetList.add(c);
			}
		}
		return targetList;
	}
	/**
	 * 统计符合查询条件的省份总数
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 下午4:00:16
	 * @return Long
	 * @param
	 */
	public Long countProvinceByName(String name) {
		List<AdministrativeRegionsEntity> cityList = getProvinceList();
		List<AdministrativeRegionsEntity> targetList = getAdministativeReginByName(
				name, cityList);
		return (long) targetList.size();
	}
	/**
	 * 统计符合查询条件的市总数
	 * 
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-10 下午4:00:21
	 * @return Long
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public Long countCityByName(String name) {
		List<AdministrativeRegionsEntity> regionList = (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		List<AdministrativeRegionsEntity> cityList = this.getRegionByType(
				regionList, DictionaryValueConstants.DISTRICT_CITY);
		List<AdministrativeRegionsEntity> targetList = getAdministativeReginByName(
				name, cityList);
		return (long) targetList.size();
	}
	/**
	 * 根据编码查询行政区域
	 * 
	 * @author 078823-foss-panGuangJun
	 * 
	 * @date 2012-12-15 下午2:37:37
	 * 
	 * @return void
	 * @param
	 */
	@SuppressWarnings("unchecked")
	public AdministrativeRegionsEntity searchRegionByCode(String code) {
		if (StringUtil.isEmpty(code)) {
			return null;
		}
		List<AdministrativeRegionsEntity> regionList = (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		if (CollectionUtils.isNotEmpty(regionList)) {
			for (int i = 0; i < regionList.size(); i++) {
				if (code.equals(regionList.get(i).getCode())) {
					return regionList.get(i);
				}
			}
		}
		return null;
	}
	/**
	 * 根据类型查询行政区域
	 * 
	 * @author 078823-foss-panGuangJun
	 * 
	 * @date 2012-12-15 下午2:37:37
	 * 
	 * @return void
	 * @param
	 */
	private List<AdministrativeRegionsEntity> getRegionByType(List<AdministrativeRegionsEntity> regionList, String type) {
		List<AdministrativeRegionsEntity> administrativeRegionsEntities = new ArrayList<AdministrativeRegionsEntity>();
		if (CollectionUtils.isNotEmpty(regionList)) {
			for (int i = 0; i < regionList.size(); i++) {
				if (type.equals(regionList.get(i).getDegree())) {
					administrativeRegionsEntities.add(regionList.get(i));
				}
			}
		}
		return administrativeRegionsEntities;
	}
	
	@SuppressWarnings("unchecked")
	public List<AdministrativeRegionsEntity> getAllData() {
	    return   (List<AdministrativeRegionsEntity>) getCache()
				.get("region");
		  
	}
}
