/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/AdministrativeRegionsAction.java
 * 
 * FILE NAME        	: AdministrativeRegionsAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************
 *
 *
 *
 *
 *相关SUC
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
 ******************************************************************************/

package com.deppon.foss.module.base.baseinfo.server.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.AdministrativeRegionsVo;
import com.deppon.foss.module.base.baseinfo.server.service.impl.AdministrativeRegionsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;

/**
 * 行政区域 action
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 上午9:47:55
 */
public class AdministrativeRegionsAction extends AbstractAction {

	/**
	 * 新增 行政区域
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-19 上午11:47:23 
	 * 
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 *  
	 */
	@JSON
	public String addAdministrativeRegions() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				// 校验前台传入行政区域CODE是否为空
				|| StringUtils.isBlank(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getCode())
				// 校验前台传入行政区域名称是否为空
				|| StringUtils.isBlank(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getName())) {
			// 校验不通过，返回错误信息
			return returnError("行政区域的编码和全称不能为空");
		}
		// 获取行政区域明细信息
		AdministrativeRegionsEntity entityView = administrativeRegionsVo
				.getAdministrativeRegionsDetail();

		// 设置操作用户的用户编码
		UserEntity userOfCache = FossUserContext.getCurrentUser();
		String operUserCode = null;
		// 校验CACH只的用户信息是否为空
		if (userOfCache != null
				&& userOfCache.getEmployee() != null
				&& StringUtils.isNotBlank(userOfCache.getEmployee()
						.getEmpCode())) {
			// 如果不为空，则获取行政区域编码赋值给操作员工编码
			operUserCode = userOfCache.getEmployee().getEmpCode();
		}
		entityView.setCreateUser(operUserCode);

		try {
			// 如果有行政区域信息，则新增
			administrativeRegionsService.addAdministrativeRegions(entityView);
		} catch (BusinessException e) {
			// 如果出错，则打印logger日志信息
			LOGGER.error("新增行政区域失败", e);
			return returnError("新增行政区域失败");
		}
		return returnSuccess();
	}

	/**
	 * 删除 行政区域
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-19 上午11:47:23 
	 * 
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 * 
	 */
	@JSON
	public String deleteAdministrativeRegions() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
				// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isBlank(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getCode())) {
			// 校验不通过返回错误信息
			return returnError("参数为空，不能执行删除操作！");
		}
		// 从数据库中重新查询出要删除的行政区域信息
		AdministrativeRegionsEntity entityView = administrativeRegionsService
				.queryAdministrativeRegionsByCode(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getCode());
		// 判断实体对象是否为空
		if (entityView == null) {
			return returnError("改行政区域信息已经变为无效，不能执行删除操作！");
		}
		// 设置操作用户的用户编码
		CurrentInfo emp =FossUserContext.getCurrentInfo();
		String operUserCode = null;
		// 校验行政区域信息是否为空
		if (emp != null && StringUtils.isNotBlank(emp.getEmpCode())) {
			operUserCode = emp.getEmpCode();
		}
		// 设置创建人、修改人编码信息
		entityView.setCreateUser(operUserCode);
		entityView.setModifyUser(operUserCode);
		// 删除行政区域信息
		administrativeRegionsService.deleteAdministrativeRegions(entityView);
		return returnSuccess();
	}

	/**
	 * 批量删除 行政区域
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-19 上午11:47:23
	 * 
	 * *  1 进入行政区域管理主界面 【行政区域列表信息】
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 * 
	 * 1 进入行政区域管理主界面 【行政区域列表信息】 
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
	@JSON
	public String deleteAdministrativeRegionsMore() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
				// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isBlank(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getCode())) {
			// 校验不通过返回错误信息
			return returnError("参数为空，不能执行删除操作！");
		}

		// 设置操作用户的用户编码
		CurrentInfo emp = FossUserContext.getCurrentInfo();
		String operUserCode = null;
		// 判断行政区域信息是否为空
		if (emp != null && StringUtils.isNotBlank(emp.getEmpCode())) {
			operUserCode = emp.getEmpCode();
		}
		// 获取行政区域编码集合信息
		String codes = administrativeRegionsVo.getAdministrativeRegionsDetail()
				.getCode();
		// 将行政区域编码转换成数组信息
		String[] codeArray = codes.split(SymbolConstants.EN_COMMA);
		// 执行批量删除行政区域信息
		administrativeRegionsService.deleteAdministrativeRegionsMore(codeArray,
				operUserCode);

		return returnSuccess();
	}

	/**
	 * 更新 行政区域
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-19 上午11:47:23
	 * 
	 * *  1 进入行政区域管理主界面 【行政区域列表信息】
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 * 
	 * 1	进入行政区域管理主界面	【行政区域列表信息】	
	 * 
       2	点击修改按钮，进入【图四：新增/修改行政区域】界面	
       	
       3	修改行政区域详细信息,点击保存
       
                                参见业务规则SR-1、SR-2、SR-3	【行政区域新增/修改信息】	
                                
       4	返回行政区域管理主界面		
       

                               序号	扩展事件	相关数据	备注
                               
       3a	点击取消按钮，退出当前界面，返回主界面		
       
       3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

	 */
	@JSON
	public String updateAdministrativeRegions() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
				// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isBlank(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getCode())) {
			// 校验不通过返回错误信息
			return returnError("行政区域的编码不能为空");
		}
		// 获取
		AdministrativeRegionsEntity entityView = administrativeRegionsVo
				.getAdministrativeRegionsDetail();
		AdministrativeRegionsEntity entityQuery = administrativeRegionsService
				.queryAdministrativeRegionsByCode(entityView.getCode());
		entityQuery = this.transEntity(entityView, entityQuery);

		// 设置操作用户的用户编码
		UserEntity emp = FossUserContext.getCurrentUser();
		entityQuery.setCreateUser(emp.getUserName());
		entityQuery.setModifyUser(emp.getUserName());
		try {
			// 执行修改行政区域信息
			entityQuery = administrativeRegionsService
					.updateAdministrativeRegions(entityQuery);
			// 如果修改结果为空，则提示异常信息
			if (entityQuery == null) {
				return returnError("修改失败");
			}
			// 出现异常则，打印异常日志信息
		} catch (Exception e) {
			LOGGER.error("修改失败", e);
			return returnError("修改失败", e);
		}
		return returnSuccess();
	}

	/**
	 * 下面全部是查询
	 * 1.6.4	查询行政区域操作步骤
	 * 
	 * 
                                         序号	基本步骤	相关数据	补充步骤
             1	进入行政区域管理主界面	【行政区域列表信息】	
             2	输入查询条件，点击查询按钮。参见业务规则SR-4	
                                 【行政区域查询条件】	系统返回查询结果
             1.7	业务规则
                    序号	描述
             SR-1	新增/修改页面，行政区域“编码”不能重复； 
             SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
             SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
             SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；
             

	 */

	/**
	 * 根据编码code查询行政区域
	 * 
	 * @author 087584-foss-lijun
	 * 
	 * @date 2012-11-17 下午6:15:25
	 */
	@JSON
	public String queryAdministrativeRegionsByCode() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
				// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isEmpty(administrativeRegionsVo
						.getAdministrativeRegionsDetail().getCode())) {
			// 校验不通过返回错误信息
			return returnSuccess();
		}
		// 为VO设置查询结果
		administrativeRegionsVo
				.setAdministrativeRegionsDetail(administrativeRegionsService
						.queryAdministrativeRegionsByCode(administrativeRegionsVo
								.getAdministrativeRegionsDetail().getCode()));

		return returnSuccess();
	}

	/**
	 * 根据父结点编码，查询直接下级的所有子结点（不包括间接下级）
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-16 下午2:50:28
	 * 
	 * *  1 进入行政区域管理主界面 【行政区域列表信息】
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 * 
	 * * 1.6.4	查询行政区域操作步骤
     *                         序号	基本步骤	相关数据	补充步骤
     *                         
     *       1	进入行政区域管理主界面	【行政区域列表信息】	
     *       
     *      2	输入查询条件，点击查询按钮。参见业务规则SR-4	
     *      
     *              【行政区域查询条件】	系统返回查询结果
     *              
             1.7	业务规则
             
                  * 序号	描述
     *      SR-1	新增/修改页面，行政区域“编码”不能重复； 
     *      
     *      SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
     *      
     *      SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
     *      
     *      SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；

	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryByParentDistrictCode() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isEmpty(administrativeRegionsVo
						.getAdministrativeRegionsDetail()
						.getParentDistrictCode())) {
			// 如果VO为空，则new一个对象
			if (administrativeRegionsVo == null) {
				administrativeRegionsVo = new AdministrativeRegionsVo();
			}
			// 如上级编码为空，返回根结点
			administrativeRegionsVo
					.setAdministrativeRegionsList(administrativeRegionsService
							.queryRoot());
			return returnSuccess();
		}
		// 获取父节点行政区域编码
		String parentCode = administrativeRegionsVo
				.getAdministrativeRegionsDetail().getParentDistrictCode();
		// 根据行政区域编码查询行政区域集合信息
		List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService
				.queryAdministrativeRegionsByParentDistrictCode(parentCode);
		nodes = new ArrayList<TreeNode>();
		// 循环获取行政区域信息
		for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
			TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
			treeNode.setId(pojo.getCode());
			treeNode.setText(pojo.getName());
			// 如果行政区域级别是TOWN_STREET_AGENCY（乡镇/街道），则为叶子结点--187862-dujunhui
			treeNode.setLeaf(StringUtils.equals(
					DictionaryValueConstants.TOWN_STREET_AGENCY, pojo.getDegree()));
			// 如果上级行政区域编码不为空
			if (pojo.getParentDistrictCode() != null
					&& !"".equals(pojo.getParentDistrictCode())) {
				treeNode.setParentId(pojo.getParentDistrictCode());
			} else {
				treeNode.setParentId(null);
			}
			// 将获取的行政区域信息添加到节点中
			treeNode.setEntity(pojo);
			nodes.add(treeNode);
		}
		return returnSuccess();
	}

	/**
	 * 动态条件，模糊查询
	 * 
	 * @author 087584
	 * @date 2012-9-8 上午11:16:53
	 * @return
	 * @see
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 * * 1.6.4	查询行政区域操作步骤
              *                          序号	基本步骤	相关数据	补充步骤
              *                          
      *       1	进入行政区域管理主界面	【行政区域列表信息】	
      *       
       *      2	输入查询条件，点击查询按钮。参见业务规则SR-4	
        *          *               【行政区域查询条件】	系统返回查询结果
        *          
       *      1.7	业务规则
                    序号	描述
       *      SR-1	新增/修改页面，行政区域“编码”不能重复； 
       *      
       *      SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
       *      
        *     SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
        *     
        *     SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；

	 */
	@JSON
	public String queryAdministrativeRegionsByEntity() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null) {
			administrativeRegionsVo = new AdministrativeRegionsVo();
		}
		// 获取查询条件
		AdministrativeRegionsEntity entityCondition = administrativeRegionsVo
				.getAdministrativeRegionsDetail();

		// 返回的结果显示在表格中：
		administrativeRegionsVo
				.setAdministrativeRegionsList(administrativeRegionsService
						.queryAdministrativeRegionsByEntity(entityCondition,
								start, limit));
		// 获取合计信息
		totalCount = administrativeRegionsService
				.queryAdministrativeRegionsByEntityCount(entityCondition);
		return returnSuccess();
	}

	/**
	 * 下面是一些工具方法
	 */

	/**
	 * 将从界面获取的数据与数据库中的源数据整合一下
	 * 
	 * 在更新的时候使用，更新的时候，先从数据库查出原数据，再设入界面上能修改的数据
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-21 上午10:40:15
	 * 
	 * @param entityView
	 *            从界面获取的源数据
	 * @param entityQuery
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
	 *  
	 *  1 进入行政区域管理主界面 【行政区域列表信息】 
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
	 *            从数据库查出来的，要存入数据库的目标数据
	 *            * 1.6.4	查询行政区域操作步骤
              *                           序号	基本步骤	相关数据	补充步骤
              *                           
      *       1	进入行政区域管理主界面	【行政区域列表信息】	
      *       
      *       2	输入查询条件，点击查询按钮。参见业务规则SR-4	
               *                  【行政区域查询条件】	系统返回查询结果
               *                  
       *      1.7	业务规则
                  *  序号	描述
       *      SR-1	新增/修改页面，行政区域“编码”不能重复； 
       *      SR-2	除了“国家”级行政区域，其它行政区域的“上级行政区域”不能为空
       *      
        *     SR-3	下级行政区域的行政区域级别不能高于上级行政区域的行政区域级别；
        *     
        *     SR-4	查询支持模糊查询，“虚拟行政区域”包含全部、是、否；

	 */
	public AdministrativeRegionsEntity transEntity(
			AdministrativeRegionsEntity entityView,
			AdministrativeRegionsEntity entityQuery) {
		if (entityQuery == null) {
			entityQuery = new AdministrativeRegionsEntity();
		}

		// 区域全称
		entityQuery.setName(entityView.getName());
		// 简称
		entityQuery.setSimpleName(entityView.getSimpleName());
		// 可用名称
		entityQuery.setAvailableName(entityView.getAvailableName());
		// 上级行政区域编码
		entityQuery.setParentDistrictCode(entityView.getParentDistrictCode());
		// 上级行政区域名称TODO
		entityQuery.setParentDistrictName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entityView.getParentDistrictCode()));
		// 虚拟行政区域
		entityQuery.setVirtualDistrictId(entityView.getVirtualDistrictId());
		// 行政区域级别
		entityQuery.setDegree(entityView.getDegree());
		// 热门城市
		entityQuery.setIsHotCity(entityView.getIsHotCity());
		// 后缀
		entityQuery.setRegionsuffix(entityView.getRegionsuffix());

		return entityQuery;
	}

	/**
	 * 获取行政区域树，到city节点
	 * @author foss-qiaolifeng
	 * @date 2013-7-23 下午7:23:50
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryCityByParentDistrictCode() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isEmpty(administrativeRegionsVo
						.getAdministrativeRegionsDetail()
						.getParentDistrictCode())) {
			// 如果VO为空，则new一个对象
			if (administrativeRegionsVo == null) {
				administrativeRegionsVo = new AdministrativeRegionsVo();
			}
			// 如上级编码为空，返回根结点
			administrativeRegionsVo
					.setAdministrativeRegionsList(administrativeRegionsService
							.queryRoot());
			return returnSuccess();
		}
		// 获取父节点行政区域编码
		String parentCode = administrativeRegionsVo
				.getAdministrativeRegionsDetail().getParentDistrictCode();
		// 根据行政区域编码查询行政区域集合信息
		List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService
				.queryAdministrativeRegionsByParentDistrictCode(parentCode);
		nodes = new ArrayList<TreeNode>();
		// 循环获取行政区域信息
		for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
			TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
			treeNode.setId(pojo.getCode());
			treeNode.setText(pojo.getName());
			// 如果行政区域级别是TOWN_STREET_AGENCY（乡镇/街道），则为叶子结点--187862-dujunhui
			treeNode.setLeaf(StringUtils.equals(
					DictionaryValueConstants.TOWN_STREET_AGENCY, pojo.getDegree()));
			// 如果上级行政区域编码不为空
			if (pojo.getParentDistrictCode() != null
					&& !"".equals(pojo.getParentDistrictCode())) {
				treeNode.setParentId(pojo.getParentDistrictCode());
			} else {
				treeNode.setParentId(null);
			}
			// 将获取的行政区域信息添加到节点中
			treeNode.setEntity(pojo);
			treeNode.setChecked(false);
			nodes.add(treeNode);
		}
		return returnSuccess();
	}
	/**
	 * <p>获取行政区域树，到区县节点</p> 
	 * @author 094463-foss-xieyantao
	 * @date 2013-8-14 下午3:26:32
	 * @return
	 * @see
	 */
	@SuppressWarnings("rawtypes")
	@JSON
	public String queryDistrictCode() {
		// 校验前台传入的VO是否为空
		if (administrativeRegionsVo == null
		// 校验前台传入的VO的行政区域明细信息是否为空
				|| administrativeRegionsVo.getAdministrativeRegionsDetail() == null
				|| StringUtils.isEmpty(administrativeRegionsVo
						.getAdministrativeRegionsDetail()
						.getParentDistrictCode())) {
			// 如果VO为空，则new一个对象
			if (administrativeRegionsVo == null) {
				administrativeRegionsVo = new AdministrativeRegionsVo();
			}
			// 如上级编码为空，返回根结点
			administrativeRegionsVo
					.setAdministrativeRegionsList(administrativeRegionsService
							.queryRoot());
			return returnSuccess();
		}
		// 获取父节点行政区域编码
		String parentCode = administrativeRegionsVo
				.getAdministrativeRegionsDetail().getParentDistrictCode();
		// 根据行政区域编码查询行政区域集合信息
		List<AdministrativeRegionsEntity> administrativeRegionsList = administrativeRegionsService
				.queryAdministrativeRegionsByParentDistrictCode(parentCode);
		nodes = new ArrayList<TreeNode>();
		// 循环获取行政区域信息
		for (AdministrativeRegionsEntity pojo : administrativeRegionsList) {
			TreeNode<AdministrativeRegionsEntity, TreeNode> treeNode = new TreeNode<AdministrativeRegionsEntity, TreeNode>();
			treeNode.setId(pojo.getCode());
			treeNode.setText(pojo.getName());
			// 如果行政区域级别是TOWN_STREET_AGENCY（乡镇/街道），则为叶子结点--187862-dujunhui
			treeNode.setLeaf(StringUtils.equals(
					DictionaryValueConstants.TOWN_STREET_AGENCY, pojo.getDegree()));
			// 如果上级行政区域编码不为空
			if (pojo.getParentDistrictCode() != null
					&& !"".equals(pojo.getParentDistrictCode())) {
				treeNode.setParentId(pojo.getParentDistrictCode());
			} else {
				treeNode.setParentId(null);
			}
			// 将获取的行政区域信息添加到节点中
			treeNode.setEntity(pojo);
			treeNode.setChecked(false);
			nodes.add(treeNode);
		}
		return returnSuccess();
	}
	
	/**
	 * 下面是声明的变更
	 */
	private static final long serialVersionUID = -4387687988772020011L;

	// 用于注入行政区域业务服务实现类
	private IAdministrativeRegionsService administrativeRegionsService;

	private AdministrativeRegionsVo administrativeRegionsVo;

	@SuppressWarnings({ "rawtypes" })
	private List<TreeNode> nodes;
	// 日志信息
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AdministrativeRegionsService.class);

	/*
	 * =================================================================
	 * 下面是get,set方法：
	 */

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public AdministrativeRegionsVo getAdministrativeRegionsVo() {
		return administrativeRegionsVo;
	}

	public void setAdministrativeRegionsVo(
			AdministrativeRegionsVo administrativeRegionsVo) {
		this.administrativeRegionsVo = administrativeRegionsVo;
	}

}
