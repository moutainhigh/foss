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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AirportService.java
 * 
 * FILE NAME        	: AirportService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 修订记录 
        日期 	修订内容 	修订人员 	版本号 
        2012-04-12	始创	石巍	V0.1
        2012-04-19	17号开会确定模板问题后，进行了修改，主要是用例四合一，界面描述遵循FOSS-Ⅰ的写法	石巍	V0.2
        2012-04-26	根据王超点评，对格式进行修改、对内容进行补充	石巍	V0.3
        2012-6-7	操作步骤、业务规则进行完善	谢艳涛	V0.4
        2012-6-26	根据王偕旭点评修改：去掉“机场简称”，增加“机场名称”查询条件	谢艳涛	V0.41
        2012-08-02	通过业务部门审核签字版本升级到V0.9	周春来	V0.9
        
        1.	SUC-52-新增_修改_作废_查询机场信息
        1.1	相关业务用例
        BUC_FOSS_5.70.30_060制作航空正单。
        1.2	用例描述
        通过本用例可对机场的信息基础资料进行新增、修改、作废、查询操作。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	城市基础资料完备。	SUC-33 新增_修改_作废_查询行政区域
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供机场的基础资料查询	SUC-247 录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        系统管理员	系统管理员对机场信息进行新增，修改，作废，查询操作。
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-主界面
         
        图一：机场信息主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮进入新增界面，参见【图二：机场信息新增/
        修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，清空查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框，作废成功后会弹出作废成功的提示框；若作废失败，弹出作废失败的提示框，并提示失败原因。
        5)	查看详细信息：双击一行记录，弹出一个窗口，可以查看该记录的详细信息
        6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：机场信息新增/修改界面】。
        7)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：机场名称、机场三字代码、机场所在城市、提货地址。
        3.	字段输入区域
        1)	查询条件包括机场所在城市、机场三字代码、机场名称。
        1.1	机场所在城市：选择框，支持手动输入模糊查询，也支持从行政区域（城市）机场资料中选取；
        1.2	机场三字代码：文本，支持模糊查询；
        1.3	机场名称：文本，支持模糊查询。
        
        1.5.4	界面原型-新增和修改界面
         
        图二：机场信息新增/修改界面
        1.5.5	界面描述-新增和修改界面
        1.	字段输入区域
        1)	机场名称： 必填，文本
        2)	机场三字代码：必填，文本，大写英文字母表示
        3)	机场所在城市：必填，选择框，从行政区域（城市）机场资料中选择
        4)	所属区县：必填，与“机场所在城市”联动，下拉框显示“机场所在城市”下辖的区县
        5)	联系人：选填，文本
        6)	联系电话：选填，数字
        7)	提货地址：必填，文本
        8)	机场描述信息：选填，文本
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前页面。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
        1.6	操作步骤
        1.6.1	添加机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入机场详细信息，点击保存。
        参见业务规则SR-1、SR-2。	【机场信息新增/修改信息】	
        4	返回航班信息管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改机场信息详细信息，点击保存
        参见业务规则SR-1、SR-2	【机场信息新增/修改信息】	成功保存至数据库
        4	返回机场信息管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.3	作废机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	选择一行或者多行记录，点击作废按钮。		弹出确认对话框。
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		
        
        1.6.4	查询机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-3	【机场信息查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
        
        1.8	数据元素
        1.8.1	机场信息新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        机场名称	机场名称	文本	机场的名称	50	是	
        机场三字代码	由国际航空运输协会分配给每个机场的三字英文字母简称	文本	大写英文字母，输入长度必须为3	 3	是	
        机场所在城市	机场所在城市，从行政区域（城市）机场资料选择	选择框	机场所在城市	30	是	
        所属区县	所属区县，与“机场所在城市”联动	文本		30	是	
        联系人	联系人	文本		10	否	
        联系电话	联系电话	数字		15	否	
        提货地址	提货地址	文本		100	是	
        机场描述信息	机场描述信息	文本		200	否	
        1.8.2	机场信息列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        机场名称	机场的名称	N/A	50	N/A	
        机场三字代码	由国际航空运输协会分配给每个机场的三字英文字母简称	N/A	  3	N/A	
        机场所在城市	机场所在城市	N/A	30	N/A	
        提货地址	提货地址	N/A	100	N/A	
        1.8.3	机场信息查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        机场所在城市	机场所在城市，从行政区域（城市）基础资料选择	选择框	50	否	
        机场三字代码	机场的三字代码	文本	3	否	
        机场名称	机场名称	文本	50	否	
        
        1.9	非功能性需求
        使用量	
        2012年全网估计用户数	
        响应要求（如果与全系统要求 不一致的话）	
        使用时间段	
        高峰使用时间段	
        
        1.10	接口描述
        接口名称 	对方系统（外部系统或内部其他模块）	接口描述



 * 
 * 
 * 修订记录 
        日期 	修订内容 	修订人员 	版本号 
        2012-04-12	始创	石巍	V0.1
        2012-04-19	17号开会确定模板问题后，进行了修改，主要是用例四合一，界面描述遵循FOSS-Ⅰ的写法	石巍	V0.2
        2012-04-26	根据王超点评，对格式进行修改、对内容进行补充	石巍	V0.3
        2012-6-7	操作步骤、业务规则进行完善	谢艳涛	V0.4
        2012-6-26	根据王偕旭点评修改：去掉“机场简称”，增加“机场名称”查询条件	谢艳涛	V0.41
        2012-08-02	通过业务部门审核签字版本升级到V0.9	周春来	V0.9
        
        1.	SUC-52-新增_修改_作废_查询机场信息
        1.1	相关业务用例
        BUC_FOSS_5.70.30_060制作航空正单。
        1.2	用例描述
        通过本用例可对机场的信息基础资料进行新增、修改、作废、查询操作。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	城市基础资料完备。	SUC-33 新增_修改_作废_查询行政区域
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供机场的基础资料查询	SUC-247 录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        系统管理员	系统管理员对机场信息进行新增，修改，作废，查询操作。
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-主界面
         
        图一：机场信息主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮进入新增界面，参见【图二：机场信息新增/
        修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，清空查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框，作废成功后会弹出作废成功的提示框；若作废失败，弹出作废失败的提示框，并提示失败原因。
        5)	查看详细信息：双击一行记录，弹出一个窗口，可以查看该记录的详细信息
        6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：机场信息新增/修改界面】。
        7)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：机场名称、机场三字代码、机场所在城市、提货地址。
        3.	字段输入区域
        1)	查询条件包括机场所在城市、机场三字代码、机场名称。
        1.1	机场所在城市：选择框，支持手动输入模糊查询，也支持从行政区域（城市）机场资料中选取；
        1.2	机场三字代码：文本，支持模糊查询；
        1.3	机场名称：文本，支持模糊查询。
        
        1.5.4	界面原型-新增和修改界面
         
        图二：机场信息新增/修改界面
        1.5.5	界面描述-新增和修改界面
        1.	字段输入区域
        1)	机场名称： 必填，文本
        2)	机场三字代码：必填，文本，大写英文字母表示
        3)	机场所在城市：必填，选择框，从行政区域（城市）机场资料中选择
        4)	所属区县：必填，与“机场所在城市”联动，下拉框显示“机场所在城市”下辖的区县
        5)	联系人：选填，文本
        6)	联系电话：选填，数字
        7)	提货地址：必填，文本
        8)	机场描述信息：选填，文本
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前页面。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
        1.6	操作步骤
        1.6.1	添加机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入机场详细信息，点击保存。
        参见业务规则SR-1、SR-2。	【机场信息新增/修改信息】	
        4	返回航班信息管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改机场信息详细信息，点击保存
        参见业务规则SR-1、SR-2	【机场信息新增/修改信息】	成功保存至数据库
        4	返回机场信息管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.3	作废机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	选择一行或者多行记录，点击作废按钮。		弹出确认对话框。
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		
        
        1.6.4	查询机场信息操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入机场信息管理主界面	【机场信息列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-3	【机场信息查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
        
        1.8	数据元素
        1.8.1	机场信息新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        机场名称	机场名称	文本	机场的名称	50	是	
        机场三字代码	由国际航空运输协会分配给每个机场的三字英文字母简称	文本	大写英文字母，输入长度必须为3	 3	是	
        机场所在城市	机场所在城市，从行政区域（城市）机场资料选择	选择框	机场所在城市	30	是	
        所属区县	所属区县，与“机场所在城市”联动	文本		30	是	
        联系人	联系人	文本		10	否	
        联系电话	联系电话	数字		15	否	
        提货地址	提货地址	文本		100	是	
        机场描述信息	机场描述信息	文本		200	否	
        1.8.2	机场信息列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        机场名称	机场的名称	N/A	50	N/A	
        机场三字代码	由国际航空运输协会分配给每个机场的三字英文字母简称	N/A	  3	N/A	
        机场所在城市	机场所在城市	N/A	30	N/A	
        提货地址	提货地址	N/A	100	N/A	
        1.8.3	机场信息查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        机场所在城市	机场所在城市，从行政区域（城市）基础资料选择	选择框	50	否	
        机场三字代码	机场的三字代码	文本	3	否	
        机场名称	机场名称	文本	50	否	
        
        1.9	非功能性需求
        使用量	
        2012年全网估计用户数	
        响应要求（如果与全系统要求 不一致的话）	
        使用时间段	
        高峰使用时间段	
        
        1.10	接口描述
        接口名称 	对方系统（外部系统或内部其他模块）	接口描述
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirportDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFlightService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirPortService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AirportDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirportException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“机场信息”的数据库对应数据访问Service接口实现类：SUC-52
 * 
 *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
    SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
    SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取

 * 
 * 
 * 
 * 
 * <p style="display:none">modifyRecord</p>
 * 
 * 
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午10:12:24</p>
 * 
 * 
 * @author 100847-foss-GaoPeng
 * 
 * 
 * @date 2012-11-3 上午10:12:24
 * 
 * @since
 * 
 * 
 * @version
 */
public class AirportService implements IAirportService {
    /**
     * 
     * 
     * "机场信息"DAO.
     * 
     * 
     */
    private IAirportDao airportDao;
    /**
     * 
     * 
     * "行政区域"Service.
     * 
     * 
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    /**
     * 
     * 
     * "航空公司"Service.
     * 
     * 
     */
    private IAirlinesService airlinesService;
    /**
     * 
     * 
     * "组织机构"Service.
     * 
     * 
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 
     * "航班信息"Service.
     * 
     * 
     */
    private IFlightService flightService;
    /**
     * 同步机场信息给cubc
     */
    private ISyncAirPortService syncAirPortService;
    /**
     * <p>新增一个“机场信息”实体入库（忽略实体中是否存在空值）</p>.
     * 
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
        
     *
     *  1	进入机场信息管理主界面	【机场信息列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入机场详细信息，点击保存。
          	参见业务规则SR-1、SR-2。	【机场信息新增/修改信息】	
        4	返回航班信息管理主界面		
        
        	序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

     *
     * @param airport “机场信息”实体
     * 
     * @param createUser 创建人
     * 
     * 
     * @param ignoreNull true：忽略空值，false：包含空值
     * 
     * 
     * @return 1：成功；0：失败
     * 
     * 
     * @throws AirportException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午8:53:10
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#addAirport(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addAirport(AirportEntity airport, String createUser,boolean ignoreNull) throws AirportException {
	//参数验证
	if (null == airport) {
	    //异常信息处理
	    throw new AirportException("", "机场信息为空");
	}
	//参数验证
	if (StringUtils.isBlank(createUser)) {
	  //异常信息处理
	    throw new AirportException("", "创建人信息为空");
	}
	//验证数据
	AirportEntity oldAirport, tempAirport = new AirportEntity();
	//	设置 机场名称.
	tempAirport.setAirportName(airport.getAirportName());
	//设置 机场名称.
	tempAirport.setAirportCode(airport.getAirportCode());
	//根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）
	oldAirport = airportDao.queryAirportBySelective(tempAirport);
	//结果验证
	if (null != oldAirport) {
	    //异常信息处理
	    throw new AirportException("", "机场信息已经存在");
	}else{
	    //设置创建人
	    airport.setCreateUser(createUser);
	    int result1=0;
	    int result2=0;
	    if (ignoreNull) {
		//新增一个“机场信息”实体入库 （只选择实体中非空值）
	    	result1=airportDao.addAirportBySelective(airport);
	    }else{
		//新增一个“机场信息”实体入库（忽略实体中是否存在空值）
	    	result2=airportDao.addAirport(airport);
	    }
	    if (result1>0||result2>0) {
	    	syncAirPortToCUBC(airport,"1");
		}
	}
	return FossConstants.SUCCESS;
    }
    private void syncAirPortToCUBC(AirportEntity airport, String operateType) {
	List<AirportEntity>	entitys=new ArrayList<AirportEntity>();
	entitys.add(airport);
	syncAirPortService.SyncAirPort(entitys, operateType);
	}
	/**
     * <p>根据“机场信息”记录唯一标识作废（物理删除）一条“机场信息”记录</p>.
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     *
     * 	1	进入机场信息管理主界面	【机场信息列表信息】	
        2	选择一行或者多行记录，点击作废按钮。		弹出确认对话框。
        3	点击确定按钮。		
        
        	序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		

     *
     *  @param ids 记录唯一标识集合
     *  
     *  
     * @param modifyUser 修改人
     * 
     * 
     * @return 1：成功；0：失败
     * 
     * @throws AirportException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午8:53:08
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#deleteAirport(java.util.List, java.lang.String)
     */
    @Override
    @Transactional
    public int deleteAirport(List<String> ids, String modifyUser) throws AirportException {
	//参数验证
	if (CollectionUtils.isEmpty(ids)) {
	    //异常信息处理
	    throw new AirportException("", "ID 为空");
	}
	//参数验证
	if (StringUtils.isBlank(modifyUser)) {
	  //异常信息处理
	    throw new AirportException("", "修改人信息为空");
	}
	for (String id : ids) {
	    //验证数据
	    AirportEntity oldAirport, tempAirport = new AirportEntity();
	    //设置ＩＤ
	    tempAirport.setId(id);
	    //根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）
	    oldAirport = airportDao.queryAirportBySelective(tempAirport);
	    //结果验证
	    if (null == oldAirport) {
		//异常信息处理
		throw new AirportException("", "机场信息不存在");
	    }else{
		//设置修改人
		oldAirport.setModifyUser(modifyUser);
		//设置状态
		oldAirport.setActive(FossConstants.INACTIVE);
		//修改一个“机场信息”实体入库 （只选择实体中非空值）
		int result =airportDao.updateAirportBySelective(oldAirport);
		if (result>0) {
			syncAirPortToCUBC(oldAirport,"3");
		}
	    } 
	}
	return FossConstants.SUCCESS;
    }
    /**
     * <p>修改一个“机场信息”实体入库（忽略实体中是否存在空值）</p>.
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     *
     *
     *	1	进入机场信息管理主界面	【机场信息列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改机场信息详细信息，点击保存
        	参见业务规则SR-1、SR-2	【机场信息新增/修改信息】	成功保存至数据库
        4	返回机场信息管理主界面		
        
        	序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

     *
     * @param airport “机场信息”实体
     * 
     * 
     * @param modifyUser 修改人
     * 
     * 
     * @param ignoreNull true：忽略空值，false：包含空值
     * 
     * 
     * @return 1：成功；0：失败
     * 
     * 
     * @throws AirportException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-10-12 上午8:53:19
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#updateAirport(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateAirport(AirportEntity airport, String modifyUser,boolean ignoreNull) throws AirportException {
	//验证数据
	if (null == airport) {
	    //异常信息处理
	    throw new AirportException("", "机场信息为空");
	}
	//验证数据
	if (StringUtils.isBlank(airport.getId())) {
	  //异常信息处理
	    throw new AirportException("", "ID 为空");
	}
	//验证数据
	if (StringUtils.isBlank(modifyUser)) {
	  //异常信息处理
	    throw new AirportException("", "修改人信息为空");
	}
	//验证数据
	AirportEntity oldAirport, tempAirport = new AirportEntity();
	//验证ＩＤ
	tempAirport.setId(airport.getId());
	//根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）
	oldAirport = airportDao.queryAirportBySelective(tempAirport);
	if (null == oldAirport) {
	  //异常信息处理
	    throw new AirportException("", "机场信息不存在");
	}else{
	    //设置修改人
	    airport.setModifyUser(modifyUser);
	    int result1=0;
	    int result2=0;
	    if (ignoreNull) {
		//修改一个“机场信息”实体入库 （只选择实体中非空值）
	    	result1=airportDao.updateAirportBySelective(airport);
	    }else{
		//修改一个“机场信息”实体入库（忽略实体中是否存在空值）
	    	result2=airportDao.updateAirport(airport);
	    }
	    if (result1>0||result2>0) {
	    	  syncAirPortToCUBC(airport, "2");	
		}
	  
	}
	return FossConstants.SUCCESS;
    }
    /**
     * <p>根据“机场信息”记录唯一标识查询出一条“机场信息”记录</p>.
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     *
     * @param id 记录唯一标识
     * 
     * 
     * @return “机场信息”实体
     * 
     * 
     * @throws AirportException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-12 上午8:53:14
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#queryAirportBySelective(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public AirportEntity queryAirportBySelective(String id) throws AirportException {
	//参数验证
	if (StringUtils.isBlank(id)) {
	    //异常信息处理
	    throw new AirportException("", "ID 为空");
	}
	//创建对象
	AirportEntity airport = new AirportEntity();
	//设置ＩＤ
	airport.setId(id);
	//根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）
	return airportDao.queryAirportBySelective(airport);
    }
    /**
     * <p>根据条件有选择的检索出符合条件一个的“机场信息”实体（条件做自动判断，只选择实体中非空值）</p>.
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     *	
     *	1	进入机场信息管理主界面	【机场信息列表信息】	
	2	输入查询条件，点击查询按钮。参见业务规则SR-3	【机场信息查询条件】	系统返回查询结果

     * @param airport 以“机场信息”实体承载的条件参数实体
     * 
     * 
     * @return 符合条件的“机场信息”实体
     * 
     * @throws AirportException 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-11-14 上午11:23:53
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#queryAirportBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public AirportEntity queryAirportBySelective(AirportEntity airport)throws AirportException {
        //验证数据
	if (null == airport) {
	    //异常信息处理
            throw new AirportException("", "机场信息为空");
	}
	//根据条件有选择的检索出符合条件的“机场信息”唯一实体（条件做自动判断，只选择实体中非空值）
        return airportDao.queryAirportBySelective(airport);
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     * 
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     *		
     *
     *	1	进入机场信息管理主界面	【机场信息列表信息】	
	2	输入查询条件，点击查询按钮。参见业务规则SR-3	【机场信息查询条件】	系统返回查询结果

     * @param airport 以“机场信息”实体承载的条件参数实体
     * 
     * 
     * @return 符合条件的“机场信息”实体列表
     * 
     * 
     * @throws AirportException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-11-2 下午5:36:39
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#queryAirportListBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity)
     */
    @Override
    public List<AirportEntity> queryAirportListBySelective(AirportEntity airport)throws AirportException {
	//参数验证
	if (null == airport) {
	    //创建对象
	    airport = new AirportEntity();
	}
	//根据条件有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）
        return airportDao.queryAirportListBySelective(airport);
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     * 
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     * 
     *	1	进入机场信息管理主界面	【机场信息列表信息】	
	2	输入查询条件，点击查询按钮。参见业务规则SR-3	【机场信息查询条件】	系统返回查询结果

     * @param airport 
     * 
     * 
     * @param offset 开始记录数
     * 
     * 
     * @param limit 限制记录数
     * 
     * 
     * @return 符合条件的“机场信息”实体列表
     * 
     * 
     * @throws AirportException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-11-2 下午5:36:39
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#queryAirportListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirportEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryAirportListBySelectiveCondition(AirportEntity airport, int offset, int limit)throws AirportException {
	//创建分页对象
	PaginationDto paginationDto = new PaginationDto();
	//参数验证
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	//参数验证
	if(null == airport){
	  //创建对象
	    airport = new AirportEntity();
	}
	//根据条件（分页模糊）有选择的检索出符合条件的“机场信息”实体列表（条件做自动判断，只选择实体中非空值）
	List<AirportEntity> airportList = airportDao.queryAirportListBySelectiveCondition(airport, offset, limit);
	//参数验证
	if (CollectionUtils.isNotEmpty(airportList)) {
	    //初始化数据
	    List<AirportDto> airportDtoList = new ArrayList<AirportDto>(airportList.size());
	    AdministrativeRegionsEntity administrativeRegionsEntity = null;
	    AirportDto airportDto = null;
	    for (AirportEntity tempArport : airportList) {
		try {
		    airportDto = new AirportDto(tempArport);
		} catch (AirportException e) {
		    throw new AirportException("", "机场信息Entity数据复制到Dto失败", e);
		}
		administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(tempArport.getProvCode());
		if(null != administrativeRegionsEntity){
		    airportDto.setAirportIntoProvinceName(administrativeRegionsEntity.getName());
		}
		administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(tempArport.getCityCode());
		if(null != administrativeRegionsEntity){
		    airportDto.setAirportIntoCityName(administrativeRegionsEntity.getName());
		}
		administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(tempArport.getCountyCode());
		if(null != administrativeRegionsEntity){
		    airportDto.setAirportIntoAreaName(administrativeRegionsEntity.getName());
		}
		airportDtoList.add(airportDto);
	    }
	    Long totalCount = airportDao.queryAirportCountBySelectiveCondition(airport);
	    paginationDto.setPaginationDtos(airportDtoList);
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto; 
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“机场信息”实体列表（条件必须有一个）</p>.
     * 
     * 
     *  SR-1	新增和修改页面，“机场所在城市”不支持手动输入，支持从行政区域（城市）基础资料
        SR-2	新增/修改界面中机场的名称、机场的三字代码均为唯一的信息，不能重复
        SR-3	查询支持模糊查询，“机场所在城市”也支持从行政区域（城市）基础资料中选取
     *
     * @param airlinesCode 航空公司二字码
     * 
     * 
     * @param orgCode 部门编码
     * 
     * 
     * @return 符合条件的“机场信息”实体列表
     * 
     * 
     * @throws AirportException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-12-27 下午5:31:16
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirportService#queryAirportListBySelectiveCondition(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public List<AirportEntity> queryAirportListBySelectiveCondition(String airlinesCode, String orgCode) throws AirportException {
        if(StringUtils.isBlank(airlinesCode) && StringUtils.isBlank(orgCode)){
            throw new AirportException("", "参数不能全部为空");
        }
        List<AirportEntity> airportList = new ArrayList<AirportEntity>();
        List<String> startStations = null;
        //航空公司处理
        if (StringUtils.isNotBlank(airlinesCode)) {
            AirlinesEntity airlines = airlinesService.queryAirlineByCode(airlinesCode);
            if(null != airlines){
        	//获取所有航班
                FlightEntity flight = new FlightEntity();
                flight.setAirlines(airlines.getCode());
                List<FlightEntity> flightList = flightService.queryFlightListBySelective(flight);
                if(CollectionUtils.isNotEmpty(flightList)){
                    startStations = new ArrayList<String>(flightList.size());
                    for (FlightEntity flightEntity : flightList) {
                	//临时存储航班的“始发站”代码
                	startStations.add(flightEntity.getDepartureAirport());
                    }
                }
            }
	}
        //部门处理
        if (StringUtils.isNotBlank(orgCode)) {
            OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(orgCode);
            if(null != orgAdministrativeInfo){
        	String cityCode = orgAdministrativeInfo.getCityCode();
        	if(null != startStations){
        		if(CollectionUtils.isEmpty(startStations)){
            	    startStations = new ArrayList<String>(NumberConstants.NUMERAL_ONE);
                }
            	startStations.add(cityCode);
        	}
            }
	}
        if(null != startStations){
        	//去除重复“城市”代码
            Set<String> cityCodes =new HashSet<String>(startStations.size());
            cityCodes.addAll(startStations);
            //处理根据“城市”获取对应“始发机场”集合
            AirportEntity airport, airportParameter = new AirportEntity();
            for (String cityCode : cityCodes) {
                if(StringUtils.isNotBlank(cityCode)){
                    airportParameter.setCityCode(cityCode);
                    airport = airportDao.queryAirportBySelective(airportParameter);
                    if(null != airport){
                        airportList.add(airport);
                    }
                }
            }
        }
        
        return airportList;
    }
    /**
     * 设置 "机场信息"DAO.
     *
     * @param airportDao the airportDao to set
     */
    public void setAirportDao(IAirportDao airportDao) {
        this.airportDao = airportDao;
    }
    /**
     * 设置 "行政区域"Service.
     *
     * @param administrativeRegionsService the administrativeRegionsService to set
     */
    public void setAdministrativeRegionsService(
    	IAdministrativeRegionsService administrativeRegionsService) {
        this.administrativeRegionsService = administrativeRegionsService;
    }
    /**
     * 设置 "航空公司"Service.
     *
     * @param airlinesService the airlinesService to set
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
        this.airlinesService = airlinesService;
    }
    /**
     * 设置 "组织机构"Service.
     *
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    /**
     * 设置 "航班信息"Service.
     *
     * @param flightService the flightService to set
     */
    public void setFlightService(IFlightService flightService) {
        this.flightService = flightService;
    }
	public void setSyncAirPortService(ISyncAirPortService syncAirPortService) {
		this.syncAirPortService = syncAirPortService;
	}
    
}
