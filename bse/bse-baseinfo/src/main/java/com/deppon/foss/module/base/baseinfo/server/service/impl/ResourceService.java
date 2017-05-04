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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/ResourceService.java
 * 
 * FILE NAME        	: ResourceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
		 * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		*
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		*
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		*
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		*
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		*
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		*
		1.5.2	界面原型-主界面
		 *
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		*
		1.5.4	界面原型-权限新增和修改界面
		 * 
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		 * 
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		*
		1.5.8	界面原型-权限添加角色界面
		 *
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		*
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		*
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		*
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		*
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		*
		1.7	业务规则
		 *
		图四：权限类型所属关系图
		*
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		*
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		*
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		*
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		*************************************************
		* * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		*
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		*
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		*
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		*
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		*
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		*
		1.5.2	界面原型-主界面
		 *
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		*
		1.5.4	界面原型-权限新增和修改界面
		 * 
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		*  
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		*
		1.5.8	界面原型-权限添加角色界面
		 *
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		*
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		*
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		*
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		*
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		*
		1.7	业务规则
		 *
		图四：权限类型所属关系图
		*
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		*
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		*
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		*
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		*************************************************
		* * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		*
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		*
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		*
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		*
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		*
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		*
		1.5.2	界面原型-主界面
		 *
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		*
		1.5.4	界面原型-权限新增和修改界面
		 * 
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		*  
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		*
		1.5.8	界面原型-权限添加角色界面
		* 
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		*
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		*
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		*
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		*
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		*
		1.7	业务规则
		 *
		图四：权限类型所属关系图
		*
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		*
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		*
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		*
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		*************************************************
		* * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		*
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		*
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		*
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		*
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		*
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		*
		1.5.2	界面原型-主界面
		* 
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		*
		1.5.4	界面原型-权限新增和修改界面
		*  
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		 * 
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		*
		1.5.8	界面原型-权限添加角色界面
		* 
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		*
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		
		1.7	业务规则
		 
		图四：权限类型所属关系图
		
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		*************************************************
		* * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		
		1.5.2	界面原型-主界面
		 
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		
		1.5.4	界面原型-权限新增和修改界面
		  
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		  
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		
		1.5.8	界面原型-权限添加角色界面
		 
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		
		1.7	业务规则
		 
		图四：权限类型所属关系图
		
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		*************************************************
		* * 修订记录 
		日期 	修订内容 	修订人员 	版本号 
		2012-04-27 	新增 	李俊	V0.1
		2012-07-05 	修改了权限的页面布局及描述。	李俊	V0.2
		2012-07-09	按照朱俊勇老师的评审意见，去掉了“权限检查”，添加了权限新增	李俊	V0.3
		2012-07-10	按照王偕旭老师的评审意见，去掉“有效”，添加“权限检查”；在详情页面上增加“添加下级权限”和“作废”按钮；“上级权限”可编辑；列出权限对应的角色且可编辑。	李俊	V0.4
		2012-12-04	去掉了根据权限选角色	李俊	V1.1
		2012-12-25	优化了权限的新增界面	李俊	V1.2
		
		1.	SUC-39-新增_修改_作废_查询权限
		1.1	相关业务用例
		无
		
		1.2	用例描述
		该用例可对权限进行新增、修改、作废、查询维护。
		
		1.3	用例条件
		条件类型	描述	引用系统用例
		前置条件		
		后置条件	1、	为系统用例SUC-38-修改_查询角色权限提供查询功能。	修改_查询角色权限
		
		1.4	操作用户角色
		操作用户	描述
		系统管理员	系统管理员对权限进行新增，修改，作废，查询操作。
		
		1.5	界面要求
		1.5.1	表现方式
		Web页面
		
		1.5.2	界面原型-主界面
		 
		图一：权限主界面
		1.5.3	界面描述-主界面
		1.	功能按钮区域
		1)	新增按钮：点击新增按钮进入新增界面，参见【图二：权限新增和修改界面】。
		2)	查询按钮：输入查询条件，点击查询按钮，查询结果突出显示。
		3)	查看按钮：点击树形结构中的权限名称，在树型结构的右边显示这个权限的详情，参见【图三：权限查看界面】。
		2.	树形结构区域
		1)	树型结构展示了权限的名称，点击树型结构中的权限名称，在右边显示这个权限的详情。
		2)	点击树形结构的“+”，在权限树中展开该权限下一级的所有权限。点击树形结构的“-”，则收起该权限下一级的所有权限。
		3.	字段输入区域
		1)	参见数据元素【权限查询条件】。
		
		1.5.4	界面原型-权限新增和修改界面
		  
		图二：权限新增和修改界面
		1.5.5	界面描述-权限新增和修改界面
		1.	功能按钮区域
		1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
		2)	重置按钮：点击重置按钮，恢复当前权限信息。
		3)	取消按钮：点击取消按钮，返回上一级界面。
		4)3)	添加角色按钮：点击添加角色按钮，进入权限添加角色界面。参见【图四：权限添加角色界面】。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		1.5.6	界面原型-权限查看界面
		  
		图三：权限查看界面
		1.5.7	界面描述-权限查看界面
		1.	功能按钮区域
		1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：权限新增和修改界面】。
		2)	作废按钮：点击作废按钮，弹出作废确认对话框，点击对话框中的确定按钮，当前权限被作废。
		3)	添加下级权限按钮：点击添加下级权限按钮，进入权限新增界面，在新增界面中，上级权限的值默认为当前权限。
		2.	字段输入区域
		1)	参见数据元素【权限信息】。
		
		1.5.8	界面原型-权限添加角色界面
		 
		图三：权限添加角色界面
		1.5.9	界面描述-权限添加角色界面
		1.	功能按钮区域
		1)	右全移按钮：点击右全移按钮，左框的数据全部被移到右框。
		2)	右移按钮：从左框中选中一条数据，点击右移按钮，数据被移到右框。
		3)	左移按钮：从右框中选中一条数据，点击左移按钮，数据被移到左框。
		4)	左全移按钮：点击左全移按钮，右框的数据全部被移到左框。
		5)	保存按钮：点击保存按钮，修改角色对应的权限并保存到数据库。
		6)	重置按钮：点击重置按钮，恢复权限的可分配角色，已分配角色。
		7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
		
		1.6	操作步骤
		1.6.1	权限新增操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	点击新增按钮。		进入权限新增界面。
		3	输入权限详情，添加权限对应的角色。	【权限信息】	
		4	点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限新增操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		4a	点击取消按钮，退出当前界面，返回权限主界面。		
		4b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面。		
		
		1.6.2	权限作废操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要作废的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击作废按钮。		弹出作废确认对话框。
		5	点击确定按钮。		成功保存至数据库，返回权限主界面。
		权限作废操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		5a	点击取消按钮。退出当前界面，返回权限主界面。		
		5b	若作废失败，弹出对话框，提示用户作废失败以及失败原因。		
		
		1.6.3	权限修改操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，查出需要修改的权限并点击。	【权限信息】	进入权限详情界面。
		3	点击修改按钮。		进入权限修改界面。
		4	修改权限信息，点击保存按钮。		成功保存至数据库，返回权限主界面。
		权限修改操作步骤-异常操作
		序号	扩展事件	相关数据	备注
		3a	点击取消按钮。退出当前界面，返回上一级界面。		
		3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面。		
		
		1.6.4	权限查询操作步骤
		序号	基本步骤	相关数据	补充步骤
		1	进入权限主界面。		
		2	输入查询条件，点击查询按钮。	【权限查询条件】	系统返回查询结果，在权限树中被查询的权限突出显示。
		3	点击树型结构中的目标权限。		进入权限详情界面。在权限树的右边展示权限详情。
		
		1.7	业务规则
		 
		图四：权限类型所属关系图
		
		序号	描述
		SR-1SR-1	作废时，如果被作废的权限结点包含子结点，一起作废当前结点下的所有子结点。新增或者修改时，权限编码，权限入口URI不能重复。
		SR-2	权限类型为应用系统时，下级的权限类型只能为应用子系统。权限类型为应用子系统时，下级的权限类型只能为子系统模块。权限类型为子系统模块时，下级的权限类型只能为子系统模块或者模块功能。权限类型为模块功能时，下级的权限类型只能为页面元素。
		SR-3	在系统初始化时根结点“应用系统”被添加到系统中。
		SR-4	在权限的新增和修改界面，当去掉了是否权限检查，隐藏角色列表信息。当勾选了是否权限检查，显示角色列表信息。
		
		1.8	数据元素
		1.8.1	权限信息
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限编码	权限的编码，对权限的唯一标识。	文本	50	是	
		权限名称	权限的名称。	文本	20	是	
		权限入口URI	权限对应的URI的后面的一部分。	文本	2551000	是	
		功能层次	应用系统为1，应用系统下面的权限依次加1。	文本	50	是	
		上级权限	上级的权限。	文本	2050	否是	
		权限元素名	权限对应的元素名称。	文本	20	否	
		显示顺序	权限与同级权限的显示顺序，当两个权限的显示顺序相同时，随机前后显示。	文本	50	是	
		权限类型	权限对应的类型（应用系统、应用子系统、子系统模块、模块功能、页面元素）。	文本数字	5020	是	
		 图标的CSS样式权限编码	 图标的CSS样式权限的编码，对权限的唯一标识。	文本文本	20050	是是	
		节点的CSS样式	 图标的CSS样式	文本	200	是	
		权限描述	权限的描述。	文本	255	否	
		是否权限检查	是否做权限检查，如果不检查，则所有登录用户均可使用。	文本数字	201	是	
		是否子结点角色列表	是否子结点角色列表	文本列表框	2551	是	
		权限描述	权限的描述。	文本	255	否	
		1.8.2	权限查询条件
		字段名称 	说明 	输入限制	长度	是否必填	备注
		权限名称	权限的名称。	文本	20	否	
		
		1.9	非功能性需求
		使用量	
		2012年全网估计用户数	
		响应要求（如果与全系统要求 不一致的话）	
		使用时间段	
		高峰使用时间段	
		
		1.10	接口描述
		接口名称 	对方系统（外部系统或内部其他模块）	接口描述
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
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.FlagConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonResourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceConflictService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncResourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ResourceException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.common.FossTTLCache;

public class ResourceService implements IResourceService {
	
	
	/**
	 * 权限Dao
	 */
	private IResourceDao resourceDao;
	
	/**
	 * 互斥权限信息Service
	 */
	private IResourceConflictService resourceConflictService;
	
	private ISyncResourceService syncResourceService;

	public void setSyncResourceService(ISyncResourceService syncResourceService) {
		this.syncResourceService = syncResourceService;
	}
	
	private void syncResourceToOther(List<ResourceEntity> entitys){
		syncResourceService.syncResource(entitys);
	}
	
	/**
	 * 通过资源的URL,得到资源对象,先从缓存中拿，如果拿不到，在从数据库中获得 queryResourceByUrl
	 * 
	 * @param string
	 * @return
	 * @return ResourceEntity
	 * @since:
	 */
	public ResourceEntity queryResourceByUri(String uri)
			throws ResourceException {
		if (uri == null || "".equals(uri.trim())) {
			throw new ResourceException(ResourceException.RESOURCE_URI_NULL);
		}
		return resourceDao.getResourceByUri(uri);
	}
	/**
	 * 权限 新增
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-2 下午5:21:30
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService#addResource(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	@Transactional
	public ResourceEntity addResource(ResourceEntity entity)
			throws ResourceException {
		// 检查参数的合法性
		if (null == entity) {
			return null;
		}
		if (StringUtils.isBlank(entity.getCode())) {
			throw new ResourceException("权限编码不能为空");
		}
		if (StringUtils.isBlank(entity.getEntryUri())) {
			throw new ResourceException("权限入口URI不能为空");
		}
		ResourceEntity entityCondition = new ResourceEntity();
		entityCondition.setCode(entity.getCode());
		List<ResourceEntity> entitys = resourceDao.queryResourceExactByEntity(
				entityCondition, NumberConstants.NUMERAL_ZORE,
				NumberConstants.NUMERAL_ONE);
		if (!CollectionUtils.isEmpty(entitys)) {
			throw new ResourceException("权限编码已存在");
		}
		if(!StringUtils.equals(entity.getBelongSystemType(),FlagConstants.MENU_FLAG_GUI)){
			entityCondition.setEntryUri(entity.getEntryUri());
			entityCondition.setCode(null);
			entitys = resourceDao.queryResourceExactByEntity(entityCondition,
					NumberConstants.NUMERAL_ZORE, NumberConstants.NUMERAL_ONE);
			if (!CollectionUtils.isEmpty(entitys)) {
				throw new ResourceException("权限入口URI已存在");
			}
		}
		ResourceEntity entityUpdate = resourceDao.addResource(entity);
		
		if (entityUpdate != null) {
			List<ResourceEntity> entityss = new ArrayList<ResourceEntity>();
			entityss.add(entityUpdate);
			syncResourceToOther(entityss);
			
			// 如果更新成功，刷新缓存
			this.refreshCache(entity);
		}
		return entityUpdate;
	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-2 下午5:21:30
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#deleteResource(java.lang.String)
	 */
	@Override
	@Transactional
	public ResourceEntity deleteResource(ResourceEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		ResourceEntity entityUpdate = resourceDao.deleteResource(entity);
		if (entityUpdate != null) {
			
			// 如果更新成功，刷新缓存
			this.refreshCache(entity);
		}
		return entityUpdate;
	}
	/**
	 * 通过code标识来批量删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#deleteResourceMore(java.lang.String[])
	 */
	@Override
	@Transactional
	public ResourceEntity deleteResourceMore(String[] codes, String deleteUser) {
		ResourceEntity entityUpdate = resourceDao.deleteResourceMore(codes,
				deleteUser);
		if (entityUpdate != null) {
			// 如果更新成功，刷新缓存
			this.refreshCache(entityUpdate);
		}
		return entityUpdate;
	}
	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceDao#updateResource(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	@Transactional
	public ResourceEntity updateResource(ResourceEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		ResourceEntity entityUpdate = resourceDao.updateResource(entity);
		if (entityUpdate != null) {
			//   同步权限到其他系统
			List<ResourceEntity> entityss = new ArrayList<ResourceEntity>();
			entityss.add(entityUpdate);
			syncResourceToOther(entityss);
			
			// 如果更新成功，刷新缓存
			this.refreshCache(entity);
		}
		return entityUpdate;
	}
	/**
	 * 以下全为查询
	 */
	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-2 下午5:21:30
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceService#queryResourceByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResourceEntity queryResourceByCode(String code) {
		if (StringUtils.isBlank(code)) {
			throw new ResourceException(ResourceException.RESOURCE_CODE_NULL);
		}
		ICache<String, ResourceEntity> resCache = CacheManager.getInstance()
				.getCache(FossTTLCache.RESOURCE_CODE_CACHE_UUID);
		ResourceEntity res = null;
		if (resCache != null) {
			res = resCache.get(code);
		}
		return res;
	}
	/**
	 * 根据父菜单编码，得到菜单下的子节点列表 queryResourcesByParentCode
	 * 
	 * @param parentCode
	 * @return void
	 * @since:
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourcesByParentCode(String parentCode)
			throws ResourceException {
		if (StringUtil.isEmpty(parentCode)) {
			throw new ResourceException(ResourceException.RESOURCE_CODE_NULL);
		}
		ICache<String, List<ResourceEntity>> resMenuCache = CacheManager
				.getInstance().getCache(FossTTLCache.MENU_CACHE_UUID);
		List<ResourceEntity> resMenus = resMenuCache.get(parentCode);
		if (resMenus == null) {
			throw new ResourceException(ResourceException.RESOURCE_URI_NULL);
		}
		return resMenus;
	}
	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceService#queryResourceByCode(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceEntity> queryResourceBatchByCode(String[] codes) {
		if (ArrayUtils.isEmpty(codes)) {
			return null;
		}
		List<ResourceEntity> entityResults = new ArrayList<ResourceEntity>();
		ICache<String, ResourceEntity> resCache = CacheManager.getInstance()
				.getCache(FossTTLCache.RESOURCE_CODE_CACHE_UUID);
		for (String code : codes) {
			ResourceEntity res = resCache.get(code);
			if(res!=null){
				entityResults.add(res);				
			}
		}
		return entityResults;
	}
	private ICommonResourceDao commonResourceDao;
	
	public void setCommonResourceDao(ICommonResourceDao commonResourceDao) {
		this.commonResourceDao = commonResourceDao;
	}
	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceService#queryResourceExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity,
	 *      int, int)
	 */
	@Override
	public List<ResourceEntity> queryResourceExactByEntity(
			ResourceEntity entity, int start, int limit) {
		List<ResourceEntity> entityResults = commonResourceDao
				.queryResourceExactByEntity(entity, start, limit);
		entityResults = this.attachParentName(entityResults);
		return entityResults;
	}
	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceService#queryResourceExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	public long queryResourceExactByEntityCount(ResourceEntity entity) {
		return commonResourceDao.queryResourceExactByEntityCount(entity);
	}
	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IResourceService#deleteResourceMore(java.lang.String[])
	 */
	@Override
	public List<ResourceEntity> queryResourceByEntity(ResourceEntity entity,
			int start, int limit) {
		List<ResourceEntity> entityResults = resourceDao.queryResourceByEntity(
				entity, start, limit);
		entityResults = this.attachParentName(entityResults);
		return entityResults;
	}
	/**
	 * 
	 * <p>模糊查询 动态的查询条件。</p> 
	 * @author ztjie
	 * @date 2013-3-20 上午9:37:30
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService#queryResourceByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.ResourceEntity)
	 */
	@Override
	public List<ResourceEntity> queryResourceByEntity(ResourceEntity entity) {
		List<ResourceEntity> entityResults = resourceDao.queryResourceByEntity(entity);
		return entityResults;
	}
	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @see com.deppon.foss.module.baseinfo.server.service.IResourceService#queryResourceCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.ResourceEntity)
	 */
	@Override
	public long queryResourceByEntityCount(ResourceEntity entity) {
		return resourceDao.queryResourceByEntityCount(entity);
	}
	/**
	 * 下面为特殊查询
	 */
	/**
	 * 模糊查询
	 * 
	 * 模糊查询 根据权限名称name,角色编码 查询角色包含的指定‘名称’的权限
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 * @param resourceName
	 *            权限名称
	 * @param roleCode
	 *            角色编码
	 */
	@Override
	public List<ResourceEntity> queryResourceByNameRole(String resourceName,
			String roleCode, int start, int limit) {
		List<ResourceEntity> entityResults = resourceDao
				.queryResourceByNameRole(resourceName, roleCode, start, limit);

		entityResults = this.attachParentName(entityResults);
		return entityResults;
	}
	/**
	 * 模糊查询 根据权限名称查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 */
	@Override
	public List<ResourceEntity> queryResourceUpByName(String name) {
		List<ResourceEntity> entityResults = resourceDao
				.queryResourceUpByName(name);
		entityResults = this.attachParentName(entityResults);
		return entityResults;
	}
	/**
	 * 精确查询
	 * 
	 * 根据权限编码查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-23 下午4:52:39
	 */
	@Override
	public List<ResourceEntity> queryResourceUpByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		List<ResourceEntity> entityResults = resourceDao
				.queryResourceUpByCode(code);
		entityResults = this.attachParentName(entityResults);
		return entityResults;
	}
	/**
	 * 精确查询 查询权限的根结点 根据权限名称查询权限的所有上级 ，上下级通过CODE,PARENT_RES来关联
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-12 上午9:17:33
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService#queryResourceRoot()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResourceEntity queryResourceRoot(String belongSystemType) {
		ICache<String, ResourceEntity> resUriCache = CacheManager.getInstance()
				.getCache(FossTTLCache.RESOURCE_URI_CACHE_UUID);
		ResourceEntity entityResults = resUriCache.get(belongSystemType);
		return entityResults;
	}
	/**
	 * 精确查询
	 * 
	 * 返回符合记录的条数
	 * 
	 * 根据上级权限，角色查询‘角色所包含的这个权限的下级权限’
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	@Override
	public long queryResourceCountByRoleResource(String resourceCode,
			String roleCode) throws BusinessException {
		if (StringUtils.isBlank(resourceCode) || StringUtils.isBlank(roleCode)) {
			throw new ResourceException("权限编码或者角色编码不能为空");
		}
		long result = resourceDao.queryResourceCountByRoleResource(
				resourceCode, roleCode);
		return result;
	}
	/**
	 * 下面为特殊作废
	 */
	/**
	 * 作废角色权限
	 * 
	 * 返回作废的记录数
	 * 
	 * 根据上级权限，角色查询‘角色所包含的这个权限的下级权限’,然后作废
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 */
	@Override
	public long deleteResourceByRoleResource(String resourceCode,
			String roleCode) throws BusinessException {
		long result = resourceDao.deleteResourceByRoleResource(resourceCode,
				roleCode);
		return result;
	}
	/**
	 * 通过CODE 标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午3:44:19
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService#deleteResourceRecursion(java.lang.String)
	 */
	@Transactional
	public ResourceEntity deleteResourceRecursion(ResourceEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			return null;
		}
		resourceConflictService.deleteResourceConflictByCode(entity.getCode(), entity.getModifyUser());
		ResourceEntity deleteEntity = resourceDao.deleteResourceRecursion(entity);
		if(null != deleteEntity){
			//  同步权限到其他系统
			List<ResourceEntity> entityss = new ArrayList<ResourceEntity>();
			entityss.add(deleteEntity);
			syncResourceToOther(entityss);
		}
		return deleteEntity;
	}
	/**
	 * 精确查询 (不走缓存) 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @param code权限编码
	 */
	@Override
	public ResourceEntity queryResourceByCodeNoCache(String code) {
		if (StringUtils.isBlank(code)) {
			throw new ResourceException(ResourceException.RESOURCE_CODE_NULL);
		}
		ResourceEntity res = resourceDao.queryResourceByCode(code);
		res = this.attachParentName(res);
		return res;
	}
	/**
	 * 下面是包装后的业务方法
	 */

	/**
	 * 精确查询 通过 CODE 查询 全路径
	 * 
	 * // 全路径示例：“.JT00001.GS00002.DQ00003.XQ00003.YYB00008”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:21:30
	 * @param code权限编码
	 * @return 权限的全路径，父结点在前面
	 * 
	 */
	@Override
	public String gainFullPathByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		StringBuilder fullPath = new StringBuilder();

		List<ResourceEntity> entitys = this.queryResourceUpByCode(code);
		Map<String, ResourceEntity> map = new HashMap<String, ResourceEntity>();
		for (ResourceEntity entity : entitys) {
			if (entity != null) {
				map.put(entity.getCode(), entity);
			}
		}
		String currCode = code;
		ResourceEntity entity;
		fullPath.insert(NumberConstants.ZERO, currCode).insert(
				NumberConstants.ZERO, SymbolConstants.EN_PERIOD);
		// 拼装全路径
		for (int i = 0, l = entitys.size(); i < l; i++) {
			// 获得上级权限
			entity = map.get(currCode);
			if (entity == null || entity.getParentRes() == null
					|| StringUtils.isBlank(entity.getParentRes().getCode())) {
				return fullPath.toString();
			}
			currCode = entity.getParentRes().getCode();

			fullPath.insert(NumberConstants.ZERO, currCode).insert(
					NumberConstants.ZERO, SymbolConstants.EN_PERIOD);

		}
		return fullPath.toString();
	}
	/**
	 * 下面是工具方法
	 */
	/**
	 * 刷新权限信息的缓存
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-24 下午6:08:33
	 */
	@SuppressWarnings("unchecked")
	public void refreshCache(ResourceEntity entity) {
		ICache<String, ResourceEntity> resUriCache = CacheManager.getInstance()
				.getCache(FossTTLCache.RESOURCE_URI_CACHE_UUID);
		ICache<String, ResourceEntity> resCodeCache = CacheManager
				.getInstance().getCache(FossTTLCache.RESOURCE_CODE_CACHE_UUID);
		ICache<String, List<ResourceEntity>> resMenuCache = CacheManager
				.getInstance().getCache(FossTTLCache.MENU_CACHE_UUID);
		//删除刷新用户缓存
		//ICache<String, IUser> userCache = CacheManager.getInstance().getCache(
		//		FossTTLCache.USER_CACHE_UUID);
		if (entity != null) {
			String uri = entity.createAccessUri();
			resUriCache.invalid(uri);
			resUriCache.invalid(entity.getUri());
			resCodeCache.invalid(entity.getCode());
			resMenuCache.invalid(entity.getParentRes().getCode());
			//删除刷新用户缓存
			/*List<String> userIds = userService
					.queryUserIdsByResourceCode(entity.getCode());
			if (userCache != null && !CollectionUtils.isEmpty(userIds)) {
				for (String userId : userIds) {
					userCache.invalid(userId);
				}
			}*/
		}
	}
	/**
	 * 给部门加上部门名称
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	@Override
	public ResourceEntity attachParentName(ResourceEntity entity) {
		if (entity == null || entity.getParentRes() == null
				|| StringUtils.isBlank(entity.getParentRes().getCode())) {
			return entity;
		}
		ResourceEntity resource = this.queryResourceByCode(entity
				.getParentRes().getCode());
		if (resource != null) {
			entity.getParentRes().setName(resource.getName());
		}
		return entity;
	}
	/**
	 * 给部门加上部门名称
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	@Override
	public List<ResourceEntity> attachParentName(List<ResourceEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}
		for (ResourceEntity entity : entitys) {
			this.attachParentName(entity);
		}
		return entitys;
	}
	/**
	 * 下面是dao对象的声明及get,set方法：
	 */
	public void setResourceDao(IResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	

	public IResourceDao getResourceDao() {
		return resourceDao;
	}
	/*	private IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}*/
	/**
	 * 
	 * <p>
	 * 得到所有的权限信息
	 * </p>
	 * 
	 * @author ztjie
	 * @date 2013-2-18 下午3:46:04
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IResourceService#queryAllResource()
	 */
	@Override
	public List<ResourceEntity> queryAllResource() {
		return resourceDao.getAllResource();
	}
	/**
	 * 
	 * <p>
	 * 根据节点查询所有子节点
	 * </p>
	 * 
	 * @author 何波
	 * @date 2013-3-1 上午8:14:54
	 * @return
	 * @see
	 */
	@Override
	public List<ResourceEntity> queryResourceByCodeAllChildNode(String code) {
		if (StringUtil.isEmpty(code)) {
			return null;
		}
		List<ResourceEntity> entityResults = resourceDao
				.queryResourceByCodeAllChildNode(code);

		return entityResults;
	}
	/**
	 * 
	 * 根据员工编码和部门编码查询出所有的GUI权限信息
	 * 
	 * @author 088933-foss-zhangjiheng
	 * @date 2013-3-7 上午10:20:49
	 */
	@Override
	public List<ResourceEntity> queryGUIResourceByUserCode(String empCode,
			String orgCode) {
		if (StringUtil.isEmpty(empCode) || StringUtil.isEmpty(orgCode)) {
			throw new BusinessException("", "员工编号或组织编码为空！");
		}
		// 查询该用户的GUI权限信息
		List<ResourceEntity> guiResourceEntityList = resourceDao
				.queryGUIResourceByCode(
						empCode,
						orgCode,
						DictionaryValueConstants.BSE_RESOURCE_BELONG_SYSTEM_TYPE_GUI);
		return guiResourceEntityList;
	}
	
	public IResourceConflictService getResourceConflictService() {
		return resourceConflictService;
	}
	
	public void setResourceConflictService(
			IResourceConflictService resourceConflictService) {
		this.resourceConflictService = resourceConflictService;
	} 
	
	
	/**
	 * 通过运单号判断是否为悟空快递单
	 * @param waybillNo 
	 * @return
	 * @author 310854-liuzhenhua
	 */
	@Override
	public String queryIsECSByWayBillNo(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			return null ;
		}
		return resourceDao.queryIsECSByWayBillNo(waybillNo);
	}
}