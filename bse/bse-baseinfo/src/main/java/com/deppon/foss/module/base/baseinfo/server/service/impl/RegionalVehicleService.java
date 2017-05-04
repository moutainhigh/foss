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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/RegionalVehicleService.java
 * 
 * FILE NAME        	: RegionalVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
        1.4	操作用户角色
        操作用户	描述
        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-定人定区查询主界面
         
        图 1：定人定区查询主界面
        1.5.3	界面描述-定人定区查询主界面
        该界面分为四个部分：集中接送货大小区树形展示区，查询条件区域、查询结果列表、功能按钮区；
        1.	集中接送货大小区树形展示区：该区域的树形结构数据来源于集中接送货大区基础资料和集中接送货小区基础资料；
        2.	查询条件区域：查询条件包括车队、车牌号、车辆职责类别；
        1.1	车牌号：共用选择框，读取公司厢式车基础资料中车辆所属部门为该界面“车队”的接送货车；
        1.2	车队：读取当前登录部门对应的车队，不可输入；
        1.3	车辆职责类别：下拉框，包括全部、定区车、机动车，默认为全部；
        3.	查询结果列表：数据元素参见【定人定区查询结果列表】，双击列表中某行记录可打开定人定区详情界面；
        4.	功能按钮区：按钮包括查找、新增、修改、作废、查询、重置；
        ?	查找：该按钮位于集中接送货大小区树形展示区的顶部，输入区域名称或者编号后，点击该按钮，可在树形区域中定位输入的区域；
        ?	新增：此按钮位于系统悬浮工具条中，点击可打开定人定区新增界面(图2)；
        ?	修改：图标按钮，位于【定人定区查询结果列表】最前面的操作列，点击可打开定人定区修改界面(图2)；
        ?	作废：【定人定区查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的定人定区信息；另外存在“作废”的图标按钮，位于【定人定区查询结果列表】最前面的操作列，点击可作废该行定人定区信息；
        ?	查询：点击此按钮查询符合条件的定人定区信息；
        ?	重置：重新初始化【定人定区查询条件】；
        5.	提供的相关用例链接或提示信息：作废定人定区信息成功后，提示操作成功，同时刷新【定人定区查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。
        1.5.4	界面原型-定人定区新增、修改界面
         
        图 2：定人定区新增、修改界面
        1.5.5	界面描述-定人定区新增、修改界面
        在图1界面中，点击悬浮工具条中的“新增”按钮，弹出该界面；该界面分为两个部分：定人定区信息输入区域，功能按钮区；
        1.	定人定区信息输入区域：字段包括区域、区域编码、车牌号、车辆所属车队、车辆职责类别；
        1.1	区域：共用选择框，读取集中接送货大区、小区基础资料；
        1.2	区域编码：根据所选择的大区或者小区自动读取；
        1.3	接送货类型：如果选择的“区域”为大区，则读取该大区的区域类型；如果选择的“区域”为小区，则读取该小区所属大区的区域类型；
        1.4	车牌号：共用选择框，读取公司厢式车基础资料中的接送货车；
        1.5	车辆所属车队：根据车牌号自动读取；
        1.6	车辆职责类型：如果选择的区域为大区，此处默认为机动车；如果选择的区域为小区，此处默认为定区车；
        2.	功能按钮区：按钮包括保存、取消、重置；
        ?	保存：点击此按钮保存定人定区信息；
        ?	取消：退出当前界面；
        ?	重置：点击此按钮重新初始化输入区域各控件；
        3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。
        1.5.6	界面原型-查看大区对应车辆信息界面
         
        图 3：查看大区对应车辆信息界面
         
        图 4：为大区添加机动车界面
        1.5.7	界面描述-查看大区对应车辆信息界面
        点击左边大小区树形结构的某大区后，主界面的右下方区域(图3红框区域)显示点击的大区的基本信息和该大区所对应的机动车信息；该部分界面分为三部分：大区基本信息、大区机动车列表、功能按钮；
        1.	大区基本信息：包括大区名称、大区编码、大区类型、管理部门；
        1.1	“查看电子地图”超链接：点击该链接，打开该大区的电子地图界面；
        2.	大区机动车列表：列表显示大区对应的机动车，包括车牌号和车辆所属车队，第一列为操作列，包括“作废”和“修改”的图标按钮；
        3.	功能按钮：按钮包括添加机动车、作废、修改；
        3.1	添加机动车：点击该按钮弹出图4界面，该界面的“区域”、“区域编码”、“接送货类型”默认为树形结构中选中的大区、大区编码及大区接送货类型，“车辆所属车队”根据车牌号自动读取，“车辆职责类型”默认为机动车；
        3.2	作废：机动车列表中的操作列存在“作废”的图标按钮，点击该按钮，将该车辆从当前大区中移除；机动车列表左下方存在“作废”按钮，点击该按钮，将列表中选中的车辆从当前大区中移除；
        3.3	修改：该图标按钮位于机动车列表的操作列中，点击该按钮弹出图4界面；
        1.5.8	界面原型-查看小区对应车辆信息界面
         
        图5：查看小区对应车辆信息界面
         
        图 6：为小区添加定区车界面
        1.5.9	界面描述-查看小区对应车辆信息界面
        点击左边大小区树形结构的某小区后，主界面的右下方(图5红框区域)区域显示点击的小区的基本信息和该小区所对应的定区车信息；该部分界面分为三部分：小区基本信息、小区定区车列表、功能按钮；
        1.	小区基本信息：包括小区名称、小区编码、管理部门；
        1.1	“查看电子地图”超链接：点击该链接，打开该小区的电子地图界面；
        2.	小区定区车列表：列表显示小区对应的定区车，包括车牌号和车辆所属车队，第一列为操作列，包括“作废”和“修改”的图标按钮；
        3.	功能按钮：按钮包括添加定区车、作废、修改；
        3.1	添加定区车：点击该按钮弹出图6界面，该界面的“区域”、“区域编码”读取树形结构中选中的小区及小区编码，“接送货类型”读取该小区所属大区的区域类型，“车辆所属车队”根据车牌号自动读取，“车辆职责类型”默认为定区车；
        3.2	作废：定区车列表中的操作列存在“作废”的图标按钮，点击该按钮，将该车辆从当前小区中移除；定区车列表左下方存在“作废”按钮，点击该按钮，将列表中选中的车辆从当前小区中移除；
        3.3	修改：该图标按钮位于定区车列表的操作列中，点击该按钮弹出图6界面；
        1.6	操作步骤
        1.6.1	查询定人定区信息
        序号	基本步骤	相关数据	补充步骤
        1	进入定人定区查询界面		
        2	输入查询条件，点击“查询”按钮	【定人定区查询条件】
        【定人定区查询结果列表】
        【车辆统计信息】	后台执行查询，并把查询结果返回到前台，并填充【车辆统计信息】
        
        1.6.2	新增定人定区信息
        序号	基本步骤	相关数据	补充步骤
        1	点击悬浮工具条中的“新增”按钮		打开新增界面(图2)
        2	输入定人定区信息，点击“保存”按钮		校验通过后，保存定人定区信息
        
        序号	扩展事件	相关数据	备注
        2a	步骤2中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        2b	步骤2中，点击“取消”按钮		关闭新增界面
        
        1.6.3	修改定人定区信息
        序号	基本步骤	相关数据	补充步骤
        1	点击查询结果列表中的操作列中的“修改”按钮		
        2	点击修改界面的“保存”按钮		校验通过后，更新定人定区信息
        
        序号	扩展事件	相关数据	备注
        2a	步骤2中，点击“保存”按钮之前，点击“重置”按钮		重新填充各控件的值为修改前的值
        2b	步骤2中，点击“取消”按钮		关闭修改界面
        
        1.6.4	作废定人定区信息
        序号	基本步骤	相关数据	补充步骤
        1	点击查询结果列表中的操作列中的“作废”按钮		校验是否可作废，若可作废则弹出“确认/取消”的对话框，内容为“您确定要作废该定人定区信息吗？”
        2	在弹出的“确认/取消”对话框中，点击“确认”按钮		更新该定人定区信息状态为“已作废”，同时刷新【定人定区查询结果列表】
        
        序号	扩展事件	相关数据	备注
        1a	除步骤1的操作方式外，还可以勾选列表中的记录，点击列表顶部或下部的“作废”按钮	【定人定区查询结果列表】	
        2a	步骤2中，点击“取消”按钮，则回到查询界面		
        1.6.5	添加大区机动车
        序号	基本步骤	相关数据	补充步骤
        1	点击查询主界面大小区树形结构中的某大区节点		读取大区基本信息及对应机动车信息，填充至主界面右下方区域
        2	点击机动车列表上方的“添加机动车”按钮		弹出图4界面
        3	输入【定人定区信息】，点击“保存”按钮	【定人定区信息】	校验通过后，保存车辆和大区的对应关系
        
        序号	扩展事件	相关数据	备注
        3a	步骤3中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        3b	步骤3中，点击“取消”按钮		关闭新增机动车界面
        
        1.6.6	修改大区机动车
        序号	基本步骤	相关数据	补充步骤
        1	点击查询主界面大小区树形结构中的某大区节点		读取大区基本信息及对应机动车信息，填充至主界面右下方区域
        2	点击机动车列表操作列中的“修改”图标按钮		弹出图4界面
        3	输入【定人定区信息】，点击“保存”按钮	【定人定区信息】	校验通过后，更新车辆和大区的对应关系
        
        序号	扩展事件	相关数据	备注
        3a	步骤3中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        3b	步骤3中，点击“取消”按钮		关闭修改机动车界面
        1.6.7	作废大区机动车
        序号	基本步骤	相关数据	补充步骤
        1	点击查询主界面大小区树形结构中的某大区节点		读取大区基本信息及对应机动车信息，填充至主界面右下方区域
        2	点击机动车列表操作列中的“作废”图标按钮		弹出“确认/取消”的对话框，内容为“您确定要移除该车辆吗？”
        3	在弹出的“确认/取消”对话框中，点击“确认”按钮		更新车辆和大区的对应关系，同时刷新机动车列表
        
        序号	扩展事件	相关数据	备注
        2a	除步骤2的操作方式外，还可以勾选列表中的记录，点击列表下方的“作废”按钮		
        3a	步骤3中，点击“取消”按钮，则回到查询界面		
        
        1.6.8	添加小区定区车
        序号	基本步骤	相关数据	补充步骤
        1	点击查询主界面大小区树形结构中的某小区节点		读取小区基本信息及对应定区车信息，填充至主界面右下方区域
        2	点击定区车列表上方的“添加定区车”按钮		弹出图6界面
        3	输入【定人定区信息】，点击“保存”按钮	【定人定区信息】	校验通过后，保存小区和车辆的对应关系
        
        序号	扩展事件	相关数据	备注
        3a	步骤3中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        3b	步骤3中，点击“取消”按钮		关闭新增定区车界面
        1.6.9	修改小区定区车
        序号	基本步骤	相关数据	补充步骤
        1	点击查询主界面大小区树形结构中的某小区节点		读取小区基本信息及对应定区车信息，填充至主界面右下方区域
        2	点击定区车列表操作列中的“修改”图标按钮		弹出图6界面
        3	输入【定人定区信息】，点击“保存”按钮	【定人定区信息】	校验通过后，更新小区和车辆的对应关系
        
        序号	扩展事件	相关数据	备注
        3a	步骤3中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        3b	步骤3中，点击“取消”按钮		关闭修改定区车界面
        1.6.10	作废小区定区车
        序号	基本步骤	相关数据	补充步骤
        1	点击查询主界面大小区树形结构中的某小区节点		读取小区基本信息及对应定区车信息，填充至主界面右下方区域
        2	点击定区车列表操作列中的“作废”图标按钮		弹出“确认/取消”的对话框，内容为“您确定要移除该车辆吗？”
        3	在弹出的“确认/取消”对话框中，点击“确认”按钮		更新车辆和小区的对应关系，同时刷新定区车列表
        
        序号	扩展事件	相关数据	备注
        2a	除步骤2的操作方式外，还可以勾选列表中的记录，点击列表下方的“作废”按钮		
        3a	步骤3中，点击“取消”按钮，则回到查询界面		
        
        1.7	业务规则
        序号	描述
        SR-1	“车牌号”和“区域”的组合不可重复；
        一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
        
        1.8	数据元素
        1.8.1	定人定区查询条件
        字段名称 	说明	输入限制	输入提示文本	长度	是否必填	备注
        车牌号	定人定区的车辆	共用选择框		N/A	否	共用选择框，读取公司厢式车基础资料
        车辆职责类型	车辆的职责类型，定区车或者机动车	下拉框		N/A	否	包括全部、定区车、机动车
        车队	定人定区中的车辆所属的车队	共用选择框		N/A	否	共用选择框，读取行政组织中的车队
        1.8.2	定人定区查询结果列表
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        车牌号	定人定区的车辆	N/A	N/A	N/A	N/A	
        车辆职责类别	车辆的职责类别	N/A	N/A	N/A	N/A	
        车辆所属车队	车辆所属的车队	N/A	N/A	N/A	N/A	
        大区编码	定人定区信息中大区的编码	N/A	N/A	N/A	N/A	
        大区名称	定人定区信息中大区的名称	N/A	N/A	N/A	N/A	
        大区类型	定人定区信息中大区的类型，接货区或者送货区	N/A	N/A	N/A	N/A	
        小区编码	定人定区信息中小区的编码	N/A	N/A	N/A	N/A	
        小区名称	定人定区信息中小区的编码	N/A	N/A	N/A	N/A	
        1.8.3	车辆统计信息
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        车辆个数	查询结果列表中的车辆个数	N/A	N/A	N/A	N/A	
        机动车个数	查询结果列表中的机动车个数	N/A	N/A	N/A	N/A	
        定区车个数	查询结果列表中的定区车个数	N/A	N/A	N/A	N/A	
        
        1.8.4	定人定区信息
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        区域	车辆所负责的区域	共用选择框		N/A	N/A	共用选择框，读取自集中接送货大区、小区基础资料
        区域编码	车辆负责的大区或者小区的编码	N/A		N/A	N/A	
        接送货类型	大区或者小区所属大区的接送货类型	N/A		N/A	N/A	
        车牌号	定人定区的车辆	共用选择框		40	是	共用选择框，读取自有厢式车基础资料
        车辆所属车队	车辆所属的车队	N/A		N/A	N/A	
        车辆职责类型	车辆的职责类别，定区车或者机动车	下拉框		N/A	N/A	
        
        1.9	非功能性需求（可选）
        使用量	
        2012年全网估计用户数	
        响应要求（如果与全系统要求 不一致的话）	查询响应与全系统要求一致
        使用时间段	全天
        高峰使用时段	
        
        1.10	接口描述：
        接口名称 	对方系统	接口描述
        获取电子地图信息	GIS	传入集中接送货区域编码，返回该区域的电子地图页面

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
 * You may learn more information about Deepen from
 *
 *      http://www.deppon.com
 *
 */ 
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.RegionVehicleInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.RegionalVehicleException;
import com.deppon.foss.module.base.baseinfo.server.util.DeepCloneUtil;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 定人定区service接口实现：对定人定区提供增删改查的操作
 * 
 * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
        1.4	操作用户角色
        操作用户	描述
        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
 * 
 *  SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
    SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
    SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
    SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
    SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
    SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。

 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * <p style="display:none">
 * 
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13 上午9:51:04
 * 
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-13 上午9:51:04
 * @since
 * @version
 */
public class RegionalVehicleService implements IRegionalVehicleService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegionalVehicleService.class);
    /**
     * 定车定区接口.
     */
    private IRegionalVehicleDao regionalVehicleDao;
    /**
     * 集中接送大区DAO接口.
     */
    private IPickupAndDeliveryBigZoneDao bigZoneDao;
    /**
     * 集中接送小区DAO接口.
     */
    private IPickupAndDeliverySmallZoneDao smallZoneDao;
    /**
     * 行政组织接口.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 数据字典Service接口
     */
   // private IDataDictionaryValueService dataDictionaryValueService;
    
    /**
     * 同步数据接口
     */
    private ISyncRegionalVehicleService syncRegionalVehicleService;
    
	/** 
	 * 常量20000.
	 */
	private static final int NUMBER = 20000;
	
    /**
     * 设置 定车定区接口.
     * 
     * @param regionalVehicleDao
     *            the new 定车定区接口
     *            
     */
    public void setRegionalVehicleDao(IRegionalVehicleDao regionalVehicleDao) {
	this.regionalVehicleDao = regionalVehicleDao;
    }
    /**
     * 
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the orgAdministrativeInfoService to set
     *            
     */
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    /**
     * 
     * 设置 集中接送大区DAO接口.
     * 
     * @param bigZoneDao
     *            the new 集中接送大区DAO接口
     *            
     */
    public void setBigZoneDao(IPickupAndDeliveryBigZoneDao bigZoneDao) {
	this.bigZoneDao = bigZoneDao;
    }
    /**
     * 
     * 设置 集中接送小区DAO接口.
     * 
     * @param smallZoneDao
     *            the new 集中接送小区DAO接口
     *            
     */
    public void setSmallZoneDao(IPickupAndDeliverySmallZoneDao smallZoneDao) {
	this.smallZoneDao = smallZoneDao;
    }
    /**
     * 
     * 设置 行政组织接口.
     * 
     * @param orgAdministrativeInfoComplexService
     *            the new 行政组织接口
     *            
     */
    public void setOrgAdministrativeInfoComplexService(
	    IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
	this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
    
    /**
     * 设置 数据字典接口。
     * @param dataDictionaryValueService
     */
    /*public void setDataDictionaryValueService(
			IDataDictionaryValueService dataDictionaryValueService) {
		this.dataDictionaryValueService = dataDictionaryValueService;
	}*/

    /**
     * 设置 同步接口
     * @author 310854
     * @date 2016-3-23上午8:55:40
     */
    public void setSyncRegionalVehicleService(
			ISyncRegionalVehicleService syncRegionalVehicleService) {
		this.syncRegionalVehicleService = syncRegionalVehicleService;
	}
    
    /**
     * 同步接口
     * @author 310854
     * @date 2016-3-23上午9:13:42
     */
    private void syncRegionalVehicleToOMS(List<RegionVehicleEntity> entitys){
    	this.syncRegionalVehicleService.syncRegionalVehicle(entitys);
    }
    		
    		
	/**
     * 新增定人定区.
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * 	SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。

     * 
     * 
     * @param entity
     *            车辆定区实体
     * @return 1：成功；-1：失败
     * @throws RegionalVehicleException
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午9:53:33
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#addRegionalVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity)
     */
    //update by 092020-lipengfei 定区车区分为一、二级定区车时修改。
    @Transactional
    @Override
    public int addRegionalVehicle(RegionVehicleInfoDto entity)
	    throws RegionalVehicleException {
	if (null == entity) {
	    return FossConstants.FAILURE;
	} else {
		/**
		 * zjf_130566 校验，一二级车 和区域类型的唯一性
		 */
		 checkingVehicleUnique(entity);
		
		// 设置新增记录默认生效
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentUser().getUserName());
		RegionVehicleEntity secondEntity=splitRegionVehicleEntity(entity);
		/**
		 * 李鹏飞 校验业务不完整，不采用  130566
		 */
		//entityAddOrUpdateCheck(entity,"add");
		// 记录ID有工具类生成ＵＵＩＤ
		entity.setId(UUIDUtils.getUUID());
		// 新增时虚拟编码与第一次新增记录的ID相同
		entity.setVirtualCode(entity.getId());
		int result1 = regionalVehicleDao.addRegionalVehicle(entity);
		int result2=0;
		if( 0 < result1 ){
			//   FOSS定人定区信息同步周围系统OMS
			List<RegionVehicleEntity> entitys = new ArrayList<RegionVehicleEntity>();
			entitys.add(entity);
			syncRegionalVehicleToOMS(entitys);
		}
		if(secondEntity!=null){
			entityAddOrUpdateCheck(secondEntity,"add");
			secondEntity.setId(UUIDUtils.getUUID());
			secondEntity.setVirtualCode(secondEntity.getId());
			result2=regionalVehicleDao.addRegionalVehicle(secondEntity);
			
			if(0 < result2){
				//   FOSS定人定区信息同步周围系统OMS
				List<RegionVehicleEntity> secondEntitys = new ArrayList<RegionVehicleEntity>();
				secondEntitys.add(secondEntity);
				syncRegionalVehicleToOMS(secondEntitys);
			}
		}
		
		
	    return result1+result2;
	}
    }
    /**
     *<p>Title: checkingVehicleUnique</p>
     *<p>校验定区车辆的唯一性</p>
     *@author 130566-ZengJunfan
     *@date 2014-7-2上午9:05:54
     * @param entity
     */
    private void checkingVehicleUnique(RegionVehicleInfoDto entity) {
		if(null ==entity){
			 throw new RegionalVehicleException("车辆不能为空");
		}
//		List<RegionVehicleInfoDto> dList = queryRegionalVehicles(entity, 0, 0);	
		//ASP:一个送货大区只能添加一台机动车-187862-dujunhui
		List<RegionVehicleEntity> queryEntityList=regionalVehicleDao.queryRegionVehicleByVehicleAndRegionCode(null, entity.getRegionCode());
		//接送小区
		if(StringUtils.isNotBlank(entity.getRegionNature())&&StringUtils.equals(entity.getRegionNature(), "SQ")){
			//不管是 接货 还是送货区域，一级车都是必填的
			String fristVehicle =entity.getFristRegionVehicleNo();
			//二级车
			String secondVehicle =entity.getSecondRegionVehicleNo();
			//非空校验
			if(StringUtils.isBlank(fristVehicle)){
				throw new RegionalVehicleException("一级定区车,车牌号不能为空");
			}
			//校验一级车 的唯一性
			List<RegionVehicleEntity> list = regionalVehicleDao.queryInfoByVehicleNoAndRegionType(fristVehicle, entity.getRegionType(),null);
			
			//校验 若是送货区域，只能有一级车，不能有二级车
			if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "DE")){
				/**
				 * 自动排单项目 一个送货小区只能添加一个定区车 leo-zeng 优化
				 */
//				if (CollectionUtils.isNotEmpty(dList)) {
//					if(StringUtil.isNotEmpty(dList.get(0).getVehicleNo())){
//						throw new RegionalVehicleException("该送货小区已经存在定区车");
//					}
//				}
				if(CollectionUtils.isNotEmpty(queryEntityList)){
					for(RegionVehicleEntity myEntity:queryEntityList){//ASP-187862
						if(StringUtil.equals(myEntity.getVehicleType(), DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR)
								||StringUtil.equals(myEntity.getVehicleType(), DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR)){
							throw new RegionalVehicleException("一个送货小区只能添加一台定区车!");
						}
					}
				}
				if(StringUtils.isNotBlank(secondVehicle)){
					throw new RegionalVehicleException("送货区域,不能存在二级定区车");
				}
				if(CollectionUtils.isNotEmpty(list)){
					throw new RegionalVehicleException("车牌号:"+fristVehicle+"和区域类型:送货区域的"+list.get(0).getRegionName()+"组合已经存在于系统中，不能新增！");
				}
			//若是接货区域，一级必有，二级可有，一二级不可一样	
			}else if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "PK")){
				//校验一级的唯一性
				if(CollectionUtils.isNotEmpty(list)){
					throw new RegionalVehicleException("一级定区车牌号:"+fristVehicle+"和区域类型:接货区的"+list.get(0).getRegionName()+"组合已经存在于系统中，不能新增！");
				}
				//若是二级车非空
				if(StringUtils.isNotBlank(secondVehicle)){
					//保证 一二级车不一样，唯一性
					if(StringUtils.equals(fristVehicle, secondVehicle)){
						throw new RegionalVehicleException("一级定区车和二级定区车一样，不能新增！");
					}
					//校验一级的唯一性
					List<RegionVehicleEntity> secondlist = regionalVehicleDao.queryInfoByVehicleNoAndRegionType(secondVehicle, entity.getRegionType(),null);
					if(CollectionUtils.isNotEmpty(secondlist)){
						throw new RegionalVehicleException("二级定区车牌号:"+secondVehicle+"和区域类型:接货区的"+secondlist.get(0).getRegionName()+"组合已经存在于系统中，不能新增！");
					}
				}
			}
		//接送货大区	
		}else if(StringUtils.isNotBlank(entity.getRegionNature())&&StringUtils.equals(entity.getRegionNature(), "BQ")){
			//机动车车辆号
			if(StringUtils.isBlank(entity.getMotorVehicleNo())){
				throw new RegionalVehicleException("机动车牌号不能为空");
			}
			List<RegionVehicleEntity> bigList = regionalVehicleDao.queryInfoByVehicleNoAndRegionType(entity.getMotorVehicleNo(), entity.getRegionType(),null);			
			//接货区域
			if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "PK")){
				//校验机动的唯一性
				if(CollectionUtils.isNotEmpty(bigList)){
					throw new RegionalVehicleException("机动车牌号:"+entity.getMotorVehicleNo()+"和区域类型:接货区的"+bigList.get(0).getRegionName()+"组合已经存在于系统中，不能新增！");
				}
			}
			//TODO送货小区也需要验证唯一信息
			else if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "DE")){
				/**
				 * 自动排单项目 一个送货大区只能添加一个机动车 leo-zeng 优化
				 */
//				if(CollectionUtils.isNotEmpty(dList)){
//					if(StringUtil.isNotEmpty(dList.get(0).getVehicleNo())){
//						throw new RegionalVehicleException("该大区已经添加上机动车了");
//					}
//				}
				if(CollectionUtils.isNotEmpty(queryEntityList)){
					for(RegionVehicleEntity myEntity: queryEntityList){//ASP-187862
						if(StringUtil.equals(myEntity.getVehicleType(), DictionaryValueConstants.MOTOR_VEHICLE)){
							throw new RegionalVehicleException("一个送货大区只能添加一台机动车!");
						}
					}
				}
				if(CollectionUtils.isNotEmpty(bigList)){
					throw new RegionalVehicleException("机动车牌号:"+entity.getMotorVehicleNo()+"和区域类型:送货区的"+bigList.get(0).getRegionName()+"组合已经存在于系统中，不能新增！");
				}
				
			}
		}
	}
	/**
     * 根据code作废定人定区信息.
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * 
     *  SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * 
     * @param codeStr
     *            虚拟编码拼接字符串
     * @param modifyUser
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws RegionalVehicleException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:54:02
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#deleteRegionalVehicleByCode(java.lang.String[])
     */
    //update by 092020-lipengfei 数组长度固定，不适合实际业务场景，改为list
    @Transactional
    @Override
    public int deleteRegionalVehicleByCode(String codeStr, String modifyUser)
	    throws RegionalVehicleException {
	if (StringUtil.isBlank(codeStr)) {
	    return FossConstants.FAILURE;
	} else {
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    List<String> codesList=new ArrayList<String>();
	    List<RegionVehicleEntity> entitys = new ArrayList<RegionVehicleEntity>();
	    for(String code:codes){
	    	codesList.add(code);
	    	RegionVehicleEntity entity = new RegionVehicleEntity();
		    entity.setVirtualCode(code);
		    entity.setModifyUser(modifyUser);
		    entity.setModifyDate(new Date());
		    entity.setActive(FossConstants.INACTIVE);
		    entitys.add(entity);
	    }
	    LOGGER.debug("codeStr: " + codeStr);
	    int result = regionalVehicleDao.deleteRegionalVehicleByCode(codesList,modifyUser);
	  
	    //   FOSS定人定区信息同步周围系统OMS
	    if(result > 0){
	    	syncRegionalVehicleToOMS(entitys);
	    }
	    
	    return result;
	}
    }
    /**
     * 修改定人定区信息 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                    1.4	操作用户角色
                    操作用户	描述
                    车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * @param entity
     *            车辆定区实体
     * @return 1：成功；-1：失败
     * 
     * @throws RegionalVehicleException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:54:13
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#updateRegionalVehicle(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity)
     */
    //update by 092020-lipengfei 定区车区分为一、二级定区车时修改。将校验逻辑提取出来
    @Transactional
    @Override
    public int updateRegionalVehicle(RegionVehicleInfoDto entity)
			throws RegionalVehicleException {
		if (null == entity||(StringUtil.isBlank(entity.getMotorVirtualCode())
				&& StringUtil.isBlank(entity.getFristRegionVehicleCode()))) {
			return FossConstants.FAILURE;
		} /*else if (StringUtil.isBlank(entity.getMotorVirtualCode())
				&& StringUtil.isBlank(entity.getFristRegionVehicleCode())) {
			return FossConstants.FAILURE;
		}*/
		//验证唯一性
		checkingVehicleUniqueForUpdate(entity);

		// 定义一个虚拟编码code
		List<String> codes = new ArrayList<String>();
		if (StringUtil.isNotBlank(entity.getMotorVirtualCode())) {
			codes.add(entity.getMotorVirtualCode());
		}
		if (StringUtil.isNotBlank(entity.getFristRegionVehicleCode())) {
			codes.add(entity.getFristRegionVehicleCode());
		}
		if (StringUtil.isNotBlank(entity.getSecondRegionVehicleCode())) {
			codes.add(entity.getSecondRegionVehicleCode());
		}
		// 作废历史记录
		int flag = regionalVehicleDao.deleteRegionalVehicleByCode(codes,
				entity.getModifyUser());// 删除时 通过reginCode删除 不通过vihicleCode
		
		List<RegionVehicleEntity> list = new ArrayList<RegionVehicleEntity>();
		RegionVehicleEntity deleteEntity;
		for(String code : codes){
			deleteEntity = new RegionVehicleEntity();
			deleteEntity.setVirtualCode(code);
			deleteEntity.setModifyUser(FossUserContext.getCurrentUser().getUserName());
			deleteEntity.setModifyDate(new Date());
			deleteEntity.setActive(FossConstants.INACTIVE);
			list.add(deleteEntity);
		}
		
		if (flag > 0) {
			entity.setCreateDate(new Date());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setActive(FossConstants.ACTIVE);
			entity.setCreateUser(FossUserContext.getCurrentUser().getUserName());
			RegionVehicleEntity secondEntity = splitRegionVehicleEntity(entity);
			//李鹏飞校验不完整，不采用
			//entityAddOrUpdateCheck(entity, "update");
			// 记录ID有工具类生成ＵＵＩＤ
			entity.setId(UUIDUtils.getUUID());
			entity.setVirtualCode(entity.getId());
			int result1 = regionalVehicleDao.addRegionalVehicle(entity);
			int result2 = 0;
			
			if (secondEntity != null) {
			//	entityAddOrUpdateCheck(secondEntity, "update");
				secondEntity.setId(UUIDUtils.getUUID());
				secondEntity.setVirtualCode(secondEntity.getId());
				result2 = regionalVehicleDao.addRegionalVehicle(secondEntity);
			}
			
			//   FOSS定人定区信息同步周围系统OMS
			
			list.add(entity);
			list.add(secondEntity);
			
		    syncRegionalVehicleToOMS(list);
		    
			return result1 + result2;
		}
		return FossConstants.FAILURE;
	}
    /**
     * 
     *<p>Title: checkingVehicleUnique</p>
     *<p>校验修改定人定区车的唯一性</p>
     *@author 130566-ZengJunfan
     *@date 2014-7-4上午11:33:58
     * @param entity
     * @param type
     */
    private void checkingVehicleUniqueForUpdate(RegionVehicleInfoDto entity) {
    	if(null ==entity){
			 throw new RegionalVehicleException("车辆不能为空");
		}
    	//接送小区
    	if(StringUtils.isNotBlank(entity.getRegionNature())&&StringUtils.equals(entity.getRegionNature(), "SQ")){
    		//一级车都是必填的
			String fristVehicle =entity.getFristRegionVehicleNo();
			//二级车
			String secondVehicle =entity.getSecondRegionVehicleNo();
			
			if(StringUtils.isBlank(fristVehicle)){
				throw new RegionalVehicleException("一级定区车,车牌号不能为空");
			}			
			//校验 若是送货区域，只能有一级车，不能有二级车
			//313353 sonar
			this.sonarSplitOne(entity, fristVehicle, secondVehicle);
			
		//接送货大区	
		}else if(StringUtils.isNotBlank(entity.getRegionNature())&&StringUtils.equals(entity.getRegionNature(), "BQ")){
			//机动车车辆号
			if(StringUtils.isBlank(entity.getMotorVehicleNo())){
				throw new RegionalVehicleException("机动车牌号不能为空");
			}
			//接货区域
			if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "PK")){
				//校验机动车的唯一性
				checkAllKindsVehicleUnique(entity, DictionaryValueConstants.MOTOR_VEHICLE, "PK");
				
			}else if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "DE")){
				
				checkAllKindsVehicleUnique(entity, DictionaryValueConstants.MOTOR_VEHICLE, "DE");
			}
		}		
	}
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(RegionVehicleInfoDto entity, String fristVehicle, 
			String secondVehicle) {
		if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "DE")){
			if(StringUtils.isNotBlank(secondVehicle)){
				throw new RegionalVehicleException("送货区域,不能存在二级定区车");
			}
			//校验一级车 的唯一性
			checkAllKindsVehicleUnique(entity,DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR,"DE");			
		//若是接货区域，一级必有，二级可有，一二级不可一样	
		}else if(StringUtils.isNotBlank(entity.getRegionType())&&StringUtils.equals(entity.getRegionType(), "PK")){
			//校验一级的唯一性
			checkAllKindsVehicleUnique(entity,DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR,"PK");
			//若二级车非空
			if(StringUtils.isNotBlank(secondVehicle)){
				//保证 一二级车唯一性
				if(StringUtils.equals(fristVehicle, secondVehicle)){
					throw new RegionalVehicleException("一级定区车和二级定区车一样，不能修改！");
				}
				//校验二级的唯一性
				checkAllKindsVehicleUnique(entity, DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, "DE");
			}		
		}
	}
    
    /**
     *<p>Title: checkFristVehicleUnique</p>
     *<p>校验修改时，不同区域、不同类型车唯一性</p>
     *@author 130566-ZengJunfan
     *@date 2014-7-4下午1:38:55
     * @param entity
     * @param type
     */
	private void checkAllKindsVehicleUnique(RegionVehicleInfoDto entity, String vehicleType,
			String type) {
		if(null ==entity){
			throw new RegionalVehicleException("参数不能为空");
		}
		String regionTypeName =null;
		String vehicleTypeName =null;
		String vehicleNo =null;
		//判断区域类型
		if(StringUtils.equals(type, "DE")){
			regionTypeName = "送货";
		}else if(StringUtils.equals(type, "PK")){
			regionTypeName = "接货";
		}
		//判断车辆类型
		if(StringUtils.equals(vehicleType, DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR)){
			vehicleNo =entity.getFristRegionVehicleNo();
			vehicleTypeName ="一级定区车";
		}else if(StringUtils.equals(vehicleType, DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR)){
			vehicleNo =entity.getSecondRegionVehicleNo();
			vehicleTypeName ="二级定区车";
		}else{
			vehicleNo =entity.getMotorVehicleNo();
			vehicleTypeName ="机动车";
		}
		//唯一性查询
		List<RegionVehicleEntity> list = regionalVehicleDao.queryInfoByVehicleNoAndRegionType(vehicleNo, entity.getRegionType(),null);
			//非空校验
		if(CollectionUtils.isNotEmpty(list)){
			String virtualCode ="";
			if(StringUtils.equals(vehicleTypeName, "一级定区车")){
				virtualCode = entity.getFristRegionVehicleCode();
			}else if(StringUtils.equals(vehicleTypeName, "二级定区车")){
				virtualCode = entity.getSecondRegionVehicleCode();
			}else{
				virtualCode = entity.getMotorVirtualCode();
			}
			//标记为
			boolean flag = false;
			String regionName ="";
			for (RegionVehicleEntity regionVehicleEntity : list) {
				String newVirtualCode = regionVehicleEntity.getVirtualCode();
				if(StringUtils.isEmpty(newVirtualCode)){
					continue;
				}
				if(!StringUtils.equals(virtualCode, newVirtualCode)){
					flag = true;
					regionName =regionVehicleEntity.getRegionName();
					break;
				}
			}
			if(flag){
				throw new RegionalVehicleException(vehicleTypeName+"牌号:" + vehicleNo + "和区域类型: "+regionTypeName+"的"+regionName+"组合已经存在于系统中，不能修改！");
			}
		}
		
	}
	/**
     * 根据传入对象查询符合条件所有定人定区信息.
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * 1@param entity
     *            车辆定区实体
     *            
     * @param limit
     *            每页最大显示记录数
     *            
     * @param start
     *            开始记录数
     *            
     * @return 符合条件的实体列表
     * 
     * @throws RegionalVehicleException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:54:24
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryRegionalVehicles(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<RegionVehicleInfoDto> queryRegionalVehicles(
	    RegionVehicleEntity entity, int limit, int start)
	    throws RegionalVehicleException {
	entity.setActive(FossConstants.ACTIVE);
	//填充区域编码，名称
	return attahCode(regionalVehicleDao.queryRegionalVehicles(entity,limit, start));
    }
    /**
     *<p>Title: attahCode</p>
     *<p>填充名称</p>
     *@author 130566-ZengJunfan
     *@date 2014-7-2下午4:52:31
     * @param queryRegionalVehicles
     * @return
     */
    private List<RegionVehicleInfoDto> attahCode(
			List<RegionVehicleInfoDto> queryRegionalVehicles) {
		if(CollectionUtils.isEmpty(queryRegionalVehicles)){
			return null;
		}
		List<RegionVehicleInfoDto> dtos =new ArrayList<RegionVehicleInfoDto>();
		for (RegionVehicleInfoDto dto : queryRegionalVehicles) {
			dto =attahCode(dto);
			dtos.add(dto);
		}
		return dtos;
	}
    /**
     *<p>Title: attahCode</p>
     *<p>填充虚拟信息</p>
     *@author 130566-ZengJunfan
     *@date 2014-7-2下午4:54:50
     * @param dto
     */
	private RegionVehicleInfoDto attahCode(RegionVehicleInfoDto dto) {
		//参数验证
		if (null == dto) {
		    //异常新处理
		    throw new RegionalVehicleException("传入的参数不允许为空！");
		} else {
		    // 通过部门编码获得该部门的实体
		    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(dto.getVehicleDept());
		    //结果验证
		    if (null != org) {
			// 设置车队名称
			dto.setVehicleDeptName(org.getName());
		    }
		    //参数验证
		    if (StringUtil.isNotBlank(dto.getRegionNature())) {
				if (StringUtil.equals(DictionaryValueConstants.REGION_NATURE_BQ,dto.getRegionNature())) {
				    //根据大区虚拟编码查询集中接送货大区详细信息 
				    BigZoneEntity bigZone = null;
				    // 通过小区所属大区虚拟编码查询大区信息
				    if(StringUtil.isNotBlank(dto.getRegionCode())){
					bigZone = bigZoneDao.queryBigzoneByVirtualCode(dto.getRegionCode());
				    }
	//			    BigZoneEntity bigZone = bigZoneDao.queryBigzoneByVirtualCode(entity.getRegionCode());
				    //结果验证
				    if (null != bigZone) {
					// 设置区域编码
					dto.setCode(bigZone.getRegionCode());
					//设置区域名称
					dto.setRegionName(bigZone.getRegionName());
				    }
				} else if (StringUtil.equals(DictionaryValueConstants.REGION_NATURE_SQ,dto.getRegionNature())) {
				    //根据虚拟编码查询集中接送货小区详细信息 
				    SmallZoneEntity smallZone = smallZoneDao.querySmallZoneByVirtualCode(dto.getRegionCode());
				    //结果验证
				    if (null != smallZone) {
					// 设置区域编码
					dto.setCode(smallZone.getRegionCode());
					//设置区域名称
					dto.setRegionName(smallZone.getRegionName());
				    }
				    //封装dto小区 一二级定区车
				    if(StringUtils.isBlank(dto.getFristRegionVehicleNo())){
				    	//根据区域编码  接送货小区类型 一级车性质，查询唯一一条记录
				    	RegionVehicleEntity fristEntity=regionalVehicleDao.queryInfosByRegionCodeAndVihicleTypeAndRegionType(dto.getRegionCode(), dto.getRegionType(), DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR);
				    	if(null !=fristEntity){
				    		dto.setFristRegionVehicleNo(fristEntity.getVehicleNo());
				    		dto.setFristRegionVehicleCode(fristEntity.getVirtualCode());
				    	}
				    }
				    if(StringUtils.isBlank(dto.getSecondRegionVehicleNo())&&StringUtils.equals(dto.getRegionType(), "PK")){
				    	//根据区域编码  接送货小区类型二级车性质，查询唯一一条记录
				    	RegionVehicleEntity secodeEntity=regionalVehicleDao.queryInfosByRegionCodeAndVihicleTypeAndRegionType(dto.getRegionCode(), dto.getRegionType(), DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR);
				    	if(null !=secodeEntity){
				    		dto.setSecondRegionVehicleNo(secodeEntity.getVehicleNo());
				    		dto.setSecondRegionVehicleCode(secodeEntity.getVirtualCode());
				    	}
				    }
				}
		    }
		}
		return dto;
	}
	/**
     * <p>
     * 根据传入对象查询符合条件所有定人定区封装信息
     * </p>
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-1-17 上午9:23:13
     * @param entity
     *            车辆定区实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryRegionalVehicleInfoDtos(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity,
     *      int, int)
     */
    @Override
    public List<RegionVehicleInfoDto> queryRegionalVehicleInfoDtos(
    		RegionVehicleInfoDto infoDto, int limit, int start) {
    	RegionVehicleEntity entity =infoDto;
	//根据传入对象查询符合条件所有定人定区信息.
	List<RegionVehicleInfoDto> infoDtoList = queryRegionalVehicles(entity, limit, start);
	return convertInfoDtoList(infoDtoList);
    }
    /**
     * <p>
     * 信息封装转换
     * </p>
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-17 上午9:44:45
     * 
     * @param entity
     * 
     * @return
     * 
     * @see
     */
    private RegionVehicleInfoDto convertInfoDto(RegionVehicleInfoDto entity) {
	RegionVehicleInfoDto dto = null;
	if (null == entity) {
	    return null;
	} else {
	    dto = new RegionVehicleInfoDto();
	    dto.setVehicleDept(entity.getVehicleDept());
	    dto.setVehicleDeptName(entity.getVehicleDeptName());
	    dto.setVehicleNo(entity.getVehicleNo());
	    dto.setVehicleType(entity.getVehicleType());
	    dto.setFristRegionVehicleNo(entity.getFristRegionVehicleNo());
	    dto.setFristRegionVehicleCode(entity.getFristRegionVehicleCode());
	    dto.setSecondRegionVehicleNo(entity.getSecondRegionVehicleNo());
	    dto.setSecondRegionVehicleCode(entity.getSecondRegionVehicleCode());
	    dto.setMotorVehicleNo(entity.getMotorVehicleNo());
	    dto.setMotorVirtualCode(entity.getMotorVirtualCode());
	    dto.setVirtualCode(entity.getVirtualCode());
	    dto.setRegionNature(entity.getRegionNature());
	    dto.setRegionType(entity.getRegionType());
	    dto.setRegionCode(entity.getRegionCode());
	    dto.setCreateDate(entity.getCreateDate());
	    dto.setActive(entity.getActive());
	    dto.setCreateUser(entity.getCreateUser());
	    dto.setModifyDate(entity.getModifyDate());
	    dto.setModifyUser(entity.getModifyUser());
	    dto.setId(entity.getId());
	    if (StringUtils.equals(DictionaryValueConstants.REGION_NATURE_SQ,
		    entity.getRegionNature())) {
		// 如果定车定区绑定的是小区，则把小区所属大区编码、及名称查询出来
		SmallZoneEntity smallZone = smallZoneDao
			.querySmallZoneByVirtualCode(entity.getRegionCode());
		if (null != smallZone) {
		    //设置小区编码
		    dto.setSmallZoneCode(smallZone.getRegionCode());
		    //设置小区名称
		    dto.setSmallZoneName(smallZone.getRegionName());
		    
		    BigZoneEntity bigZone = null;
		    // 通过小区所属大区虚拟编码查询大区信息
		    if(StringUtils.isNotBlank(smallZone.getBigzonecode())){
			bigZone = bigZoneDao.queryBigzoneByVirtualCode(smallZone.getBigzonecode());
		    }
		   
		    if(null != bigZone){
			//设置大区名称
			dto.setRegionName(bigZone.getRegionName());
			//设置大区编码
			dto.setCode(bigZone.getRegionCode());
		    }
		}
	    }else {
		//设置大区名称
		dto.setRegionName(entity.getRegionName());
		//设置大区编码
		dto.setCode(entity.getCode());
	    }
	}
	return dto;
    }
    
    /**
     * <p>定车定区信息集合封装转换</p> 
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-17 上午11:04:19
     * 
     * @param list
     * 
     * @return
     * 
     * @see
     */
    private List<RegionVehicleInfoDto> convertInfoDtoList(List<RegionVehicleInfoDto> list) {
	if (CollectionUtils.isNotEmpty(list)) {
	    List<RegionVehicleInfoDto> dtoList = new ArrayList<RegionVehicleInfoDto>();
	    for (RegionVehicleInfoDto infoDto : list) {
		dtoList.add(convertInfoDto(infoDto));
	    }
	    return dtoList;
	} else {
	    return null;
	}
    }
    /**
     * 统计总记录数.
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * @param entity
     *            车辆定区实体
     * @return
     * 
     * @throws RegionalVehicleException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:54:35
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity)
     */
    //update by 092020-lipengfei 将RegionVehicleEntity替换为更为详细的RegionVehicleInfoDto，页面统一使用RegionVehicleInfoDto
    @Transactional
    @Override
    public Long queryRecordCount(RegionVehicleInfoDto infoDto)
	    throws RegionalVehicleException {
    	RegionVehicleEntity entity=infoDto;
		entity.setActive(FossConstants.ACTIVE);
		return regionalVehicleDao.queryRecordCount(entity);
    }
    /**
     * <p>
     * 根据区域的虚拟编码、区域性质查询定车定区详细信息
     * </p>
     * .
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * @param regionVirtualCode
     *            区域的虚拟编码
     *            
     * @param regionNature
     *            区域性质：大区、小区 大区：DictionaryValueConstants.REGION_NATURE_BQ
     *            小区:DictionaryValueConstants.REGION_NATURE_SQ
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-24 下午7:43:44
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryInfoByCode(java.lang.String)
     */
    //update by 092020-lipengfei 将RegionVehicleDto中List<RegionVehicleEntity> 替换为 List<RegionVehicleInfoDto>
    //后者为前者子类，信息更完整
    @Transactional
    @Override
    public RegionVehicleDto queryInfoByCode(String regionVirtualCode,
	    String regionNature) {
	if (StringUtil.isBlank(regionVirtualCode)) {
	    throw new RegionalVehicleException("区域编码不允许为空！");
	} else if (StringUtil.isBlank(regionNature)) {
	    throw new RegionalVehicleException("区域性质不允许为空！");
	} else {
	    RegionVehicleDto dto = null;
	    if (StringUtil.equals(DictionaryValueConstants.REGION_NATURE_BQ,
		    regionNature)) {
		// 根据虚拟编码查询集中接送货大区信息
		BigZoneEntity bigZoneEntity = bigZoneDao.queryBigzoneByVirtualCode(regionVirtualCode);
		dto = new RegionVehicleDto();
		dto.setBigZoneEntity(bigZoneEntity);
		List<String> list = new ArrayList<String>();
		list.add(regionVirtualCode);
		// 根据多个区域编码查询定车定区信息
		List<RegionVehicleInfoDto> dtoInfoList = convertInfoList(regionalVehicleDao
			.queryRegionVehiclesByRegionCodes(list));
		dto.setRegionVehicleInfoDtoList(dtoInfoList);

		return dto;
	    } else if (StringUtil.equals(
		    DictionaryValueConstants.REGION_NATURE_SQ, regionNature)) {
		// 根据虚拟编码查询集中接送货小区信息
		SmallZoneEntity smallZoneEntity = smallZoneDao
			.querySmallZoneByVirtualCode(regionVirtualCode);
		dto = new RegionVehicleDto();
		dto.setSmallZoneEntity(smallZoneEntity);
		// 根据区域编码查询定车定区绑定的车辆信息
		List<RegionVehicleEntity> regionVehicleList = regionalVehicleDao
			.queryRegionVehiclesByCode(regionVirtualCode);
		dto.setRegionVehicleInfoDtoList(convertInfoList(regionVehicleList));
		return dto;
	    } else {
		throw new RegionalVehicleException("区域性质不正确！");
	    }
	}
    }

    /**
     * <p>
     * 根据传入的参数查询车辆定区信息
     * </p>
     * .
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                    1.4	操作用户角色
                    操作用户	描述
                    车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * @param vehicleNoList
     *            车牌号列表
     *            
     * @param regionType
     * 
     *            接送货类型：接货区、送货区
     *            
     * @param regionNature
     * 
     *            区域类型:大区、小区
     *            
     * @return null 或 车辆定区信息列表
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-7 下午2:08:20
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryRegionVehiclesByParam(java.util.List,
     *      java.lang.String, java.lang.String)
     */
    //update by 092020-lipengfei 将RegionVehicleDto中List<RegionVehicleEntity> 替换为 List<RegionVehicleInfoDto>
    //后者为前者子类，信息更完整
    @Override
    public List<RegionVehicleEntity> queryRegionVehiclesByParam(
	    List<String> vehicleNoList, String regionType, String regionNature) {
	if (CollectionUtils.isEmpty(vehicleNoList)) {
	    throw new RegionalVehicleException("车牌号不允许为空！");
	}
	return convertInfoListForPKPquery(regionalVehicleDao.queryRegionVehiclesByParam(vehicleNoList, regionType, regionNature));
    }

    /**
     * <p>
     * 根据gis的区域id匹配接货小区id及车辆车牌号
     * </p>
     * .
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
                        
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * @param gisId
     *            GIS系统小区范围ID
     *            
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-15 下午6:16:29
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#querySmallZoneInfoByParam(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public RegionVehicleEntity querySmallZoneInfoByGisId(String gisId) {
	if (StringUtil.isBlank(gisId)) {
	    return null;
	}
	List<RegionVehicleEntity> entityList=regionalVehicleDao.querySmallZoneInfoByGisId(gisId);
	for(RegionVehicleEntity entity:entityList){
		if(entity!=null&&StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, entity.getVehicleType())){
			return convertInfo(entity);
		}
	}
	List<SmallZoneEntity> smalls=smallZoneDao.querySmallZoneByGisId(gisId);
	if(smalls!=null&&smalls.size()>0){
		SmallZoneEntity entity=smalls.get(0);
		RegionVehicleEntity regionVehicleEntity =new RegionVehicleEntity();
		regionVehicleEntity.setRegionCode(entity.getVirtualCode());
		regionVehicleEntity.setRegionName(entity.getRegionName());
		return regionVehicleEntity;
	}
	return null;
    }
    /**
     * @author 092020-lipengfie
     * @Description 本方法提供给接送货使用，返回小区一级、二级定区车和机动车。
     * @Time 2014-5-4
     * @param gisId
     * @return
     */
    @Override
    public RegionVehicleInfoDto querySmallZoneInfoByGisIdForNew(String gisId) {
    	if (StringUtil.isBlank(gisId)) {
    		return null;
    	}
    	List<RegionVehicleEntity> entities=regionalVehicleDao.querySmallZoneInfoByGisId(gisId);
    	if(entities.size()==0||null==entities.get(0)){
    		RegionVehicleInfoDto infoDto=new RegionVehicleInfoDto();
    		List<SmallZoneEntity> smalls=smallZoneDao.querySmallZoneByGisId(gisId);
    		if(smalls.size()==0){
    			return null;
    		}
    		 
    		SmallZoneEntity entity=smalls.get(0);
    		infoDto.setSmallZoneCode(entity.getRegionCode());
    		infoDto.setSmallZoneName(entity.getRegionName());
    		BigZoneEntity bigZoneEntity=bigZoneDao.queryBigzoneByVirtualCode(entity.getBigzonecode());
    		if(null==bigZoneEntity){
    			return null;  
    		}
    		
    		List<RegionVehicleEntity> entitys=regionalVehicleDao.getRegionVehicleEntityByBigZoonCode(bigZoneEntity.getVirtualCode());
    		if(entitys!=null&&entitys.size()>0){
    			infoDto.setMotorVehicleNo(entitys.get(0).getVehicleNo());//设置机动车车牌号
    			return infoDto;
    		}else{
    			return null;
    		}
    	}
    	RegionVehicleInfoDto infoDto=convertInfoList(entities).get(0);
    	SmallZoneEntity smallZone = smallZoneDao.querySmallZoneByVirtualCode(infoDto.getRegionCode());
    	BigZoneEntity bigZone = null;
    	if (null != smallZone) {
    		infoDto.setSmallZoneCode(smallZone.getRegionCode());
    		infoDto.setSmallZoneName(smallZone.getRegionName());
    		// 通过小区所属大区虚拟编码查询大区信息
    		if(StringUtils.isNotBlank(smallZone.getBigzonecode())){
    			bigZone = bigZoneDao.queryBigzoneByVirtualCode(smallZone.getBigzonecode());
    		}
    	}
    	if(bigZone!=null){
    		List<RegionVehicleEntity> entitys=regionalVehicleDao.getRegionVehicleEntityByBigZoonCode(bigZone.getVirtualCode());
    		if(entitys!=null&&entitys.size()>0){
    			infoDto.setMotorVehicleNo(entitys.get(0).getVehicleNo());//设置机动车车牌号
    		}
    	}
    	return infoDto;
    }
    
    
    /**
     * <p>
     * 通过传入一个车队或车队下调度组的code，查询出车队下的所有接货小区
     * </p>
     * .
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                    1.4	操作用户角色
                    操作用户	描述
                    车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * @param vehicleOrgCode
     *            车队code或车队下调度组的code
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-16 上午11:58:23
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryRegionVehiclesByOrgCode(java.lang.String)
     */
    @Transactional
    @Override
    //update by 092020-lipengfei 将RegionVehicleDto中List<RegionVehicleEntity> 替换为 List<RegionVehicleInfoDto>
    //后者为前者子类，信息更完整
    public List<RegionVehicleInfoDto> queryRegionVehiclesByOrgCode( String vehicleOrgCode) {
	//参数验证
	if (StringUtil.isBlank(vehicleOrgCode)) {
	    return null;
	}
	// 通过传入一个车队或车队下调度组的code，查询出车队下的所有接送货组
	List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService.querySubOrgByCode(vehicleOrgCode);
	//定义集合
	List<String> list = new ArrayList<String>();
	//存放集合
	list.add(vehicleOrgCode);
	//集合验证
	if (CollectionUtils.isNotEmpty(orgs)) {
	    //迭代循环
	    for (OrgAdministrativeInfoEntity entity : orgs) {
		// 添加车队接送货组编码
		list.add(entity.getCode());
	    }
	}
	//填充定车定区区域编码集合
	return convertInfoList(regionalVehicleDao.queryRegionVehiclesByOrgCode(list));
    }

    /**
     * <p>
     * 填充定车定区区域编码、车队名称
     * </p>
     * .
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                    1.4	操作用户角色
                    操作用户	描述
                    车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * @param entity
     * 
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-28 下午5:08:45
     * 
     * @see
     */
    private RegionVehicleEntity convertInfo(RegionVehicleEntity entity) {
	//参数验证
	if (null == entity) {
	    //异常新处理
	    throw new RegionalVehicleException("传入的参数不允许为空！");
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getVehicleDept());
	    //结果验证
	    if (null != org) {
		// 设置车队名称
		entity.setVehicleDeptName(org.getName());
	    }
	    //参数验证
	    if (StringUtil.isNotBlank(entity.getRegionNature())) {
		if (StringUtil.equals(DictionaryValueConstants.REGION_NATURE_BQ,entity.getRegionNature())) {
		    //根据大区虚拟编码查询集中接送货大区详细信息 
		    BigZoneEntity bigZone = null;
		    // 通过小区所属大区虚拟编码查询大区信息
		    if(StringUtil.isNotBlank(entity.getRegionCode())){
			bigZone = bigZoneDao.queryBigzoneByVirtualCode(entity.getRegionCode());
		    }
//		    BigZoneEntity bigZone = bigZoneDao.queryBigzoneByVirtualCode(entity.getRegionCode());
		    //结果验证
		    if (null != bigZone) {
			// 设置区域编码
			entity.setCode(bigZone.getRegionCode());
			//设置区域名称
			entity.setRegionName(bigZone.getRegionName());
		    }
		} else if (StringUtil.equals(DictionaryValueConstants.REGION_NATURE_SQ,entity.getRegionNature())) {
		    //根据虚拟编码查询集中接送货小区详细信息 
		    SmallZoneEntity smallZone = smallZoneDao.querySmallZoneByVirtualCode(entity.getRegionCode());
		    //结果验证
		    if (null != smallZone) {
			// 设置区域编码
			entity.setCode(smallZone.getRegionCode());
			//设置区域名称
			entity.setRegionName(smallZone.getRegionName());
		    }
		}
	    }
	}
	return entity;
    }

    /**
     * <p>
     * 填充定车定区区域编码集合
     * </p>
     * .
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * 
     * @param list
     * 
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-28 下午5:27:51
     * 
     * @see
     */
    //update by 092020-lipengfei 在此处对查询的结果进行遍历，将多条记录合并为一条记录
    private List<RegionVehicleInfoDto> convertInfoList(List<RegionVehicleEntity> list) {
	//定义一个集合
	List<RegionVehicleInfoDto> infoDtoList = new ArrayList<RegionVehicleInfoDto>();
	//集合验证
	if (CollectionUtils.isNotEmpty(list)) {
	    RegionVehicleInfoDto beforeInfoEntityDto=new RegionVehicleInfoDto();
	    //迭代循环
	    //每次循环，若本条与上一条不是同一个区域，则将上一条放入list
	    //若本条与上一条是同一区域，则将本条中需要的值set仅上一条，并将本次的上一条作为下一次的上一条。
	    for (RegionVehicleEntity entity : list) {
	    	if(StringUtil.equals(beforeInfoEntityDto.getRegionCode(), entity.getRegionCode())){
	    		//若上一条一级定区车为空，且当前一级定区车不为空，则set
	    		//313353 sonar
	    		this.sonarSplitTwo(beforeInfoEntityDto, entity, infoDtoList);
	    		//设置名称
	    		beforeInfoEntityDto =attahCode(beforeInfoEntityDto);
		    	beforeInfoEntityDto.setVehicleNo("");
		    	beforeInfoEntityDto.setVirtualCode("");
		    	beforeInfoEntityDto.setVehicleType("");
	    	}else{
		    	entity=convertInfo(entity);
	    		if(StringUtil.isNotBlank(beforeInfoEntityDto.getRegionCode())){
	    			//存放到集合
	    			infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
	    			beforeInfoEntityDto=new RegionVehicleInfoDto();
	    		}
	    		DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
		    	//设置一级定区车的值
		    	if(StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, beforeInfoEntityDto.getVehicleType())){
		    		beforeInfoEntityDto.setFristRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
		    		beforeInfoEntityDto.setFristRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
		    	}else if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, beforeInfoEntityDto.getVehicleType())){
		    		//设置二级定区车的值
		    		beforeInfoEntityDto.setSecondRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
		    		beforeInfoEntityDto.setSecondRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
		    	}else if(StringUtil.equals(DictionaryValueConstants.MOTOR_VEHICLE, beforeInfoEntityDto.getVehicleType())){
		    		//设置机动车的值
		    		beforeInfoEntityDto.setMotorVehicleNo(beforeInfoEntityDto.getVehicleNo());
		    		beforeInfoEntityDto.setMotorVirtualCode(beforeInfoEntityDto.getVirtualCode());
		    	}
		    	//定人定区区分一二级定区车后以下三个值 不需要
		    	beforeInfoEntityDto.setVehicleNo("");
		    	beforeInfoEntityDto.setVirtualCode("");
		    	beforeInfoEntityDto.setVehicleType("");
	    	}
	    }
	    infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));//最后一条
	    }
		return infoDtoList;
    }
    
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitTwo(RegionVehicleInfoDto beforeInfoEntityDto, RegionVehicleEntity entity,
			List<RegionVehicleInfoDto> infoDtoList) {
		if(StringUtil.isBlank(beforeInfoEntityDto.getFristRegionVehicleNo())&&
				StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, entity.getVehicleType())){
			beforeInfoEntityDto.setFristRegionVehicleNo(entity.getVehicleNo());
			beforeInfoEntityDto.setFristRegionVehicleCode(entity.getVirtualCode());
		}else if(StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())&&
				StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, entity.getVehicleType())){
			////若上一条二级定区车为空，且当前二级定区车不为空，则set 不考虑机动车，因为一个区域只对应一辆机动车，若出现多条，取最前面一条即可
			beforeInfoEntityDto.setSecondRegionVehicleNo(entity.getVehicleNo());
			beforeInfoEntityDto.setSecondRegionVehicleCode(entity.getVirtualCode());
		}else if(StringUtil.equals(DictionaryValueConstants.MOTOR_VEHICLE, entity.getVehicleType())){
			infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
			DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
    		beforeInfoEntityDto.setMotorVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		beforeInfoEntityDto.setMotorVirtualCode(beforeInfoEntityDto.getVirtualCode());
		}else if(StringUtil.isNotBlank(beforeInfoEntityDto.getFristRegionVehicleNo())&&
				StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, entity.getVehicleType())){
			infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
			DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
			beforeInfoEntityDto.setFristRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		beforeInfoEntityDto.setFristRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
		}else if(StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())&&
				StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, entity.getVehicleType())){
			infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
			DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
			beforeInfoEntityDto.setSecondRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		beforeInfoEntityDto.setSecondRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
		}
	}
    
    private List<RegionVehicleEntity> convertInfoListForPKPquery(List<RegionVehicleEntity> list) {
    	//集合验证
    	if (CollectionUtils.isNotEmpty(list)) {
    	    //定义一个集合
    	    List<RegionVehicleEntity> entityList = new ArrayList<RegionVehicleEntity>();
    	    //迭代循环
    	    for (RegionVehicleEntity entity : list) {
    		//存放到集合
    		entityList.add(convertInfo(entity));
    	    }
    	    //返回集合
    	    return entityList;
    	} else {
    	    return list;
    	}
        }
    
    /**
     * @author 092020-lipengfei
     * @Description 重载convertInfoList，在查询一级定区车和二级定区车时，根据查询的车辆职责类型进行判断，组装数据
     * @Time 2014-4-22
     * @param list
     * @param vehicleType
     * @return
     */
    @SuppressWarnings("unused")
	private List<RegionVehicleInfoDto> convertInfoList(List<RegionVehicleEntity> list,String vehicleType) {
    	//定义一个集合
    	List<RegionVehicleInfoDto> infoDtoList = new ArrayList<RegionVehicleInfoDto>();
    	//集合验证
    	if (CollectionUtils.isNotEmpty(list)) {
    	    RegionVehicleInfoDto beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    //迭代循环
    	    //每次循环，若本条与上一条不是同一个区域，则将上一条放入list
    	    //若本条与上一条是同一区域，则将本条中需要的值set仅上一条，并将本次的上一条作为下一次的上一条。
    	    for (RegionVehicleEntity entity : list) {
    	    	if(StringUtil.equals(beforeInfoEntityDto.getRegionCode(), entity.getRegionCode())){
    	    		//若上一条一级定区车为空，且当前一级定区车不为空，则set
    	    		if(StringUtil.isBlank(beforeInfoEntityDto.getFristRegionVehicleNo())&&
    	    				StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, entity.getVehicleType())){
    	    			beforeInfoEntityDto.setFristRegionVehicleNo(entity.getVehicleNo());
    	    			beforeInfoEntityDto.setFristRegionVehicleCode(entity.getVirtualCode());
    	    		}else if(StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())&&
    	    				StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, entity.getVehicleType())){
    	    			////若上一条二级定区车为空，且当前二级定区车不为空，则set 不考虑机动车，因为一个区域只对应一辆机动车，若出现多条，取最前面一条即可
    	    			beforeInfoEntityDto.setSecondRegionVehicleNo(entity.getVehicleNo());
    	    			beforeInfoEntityDto.setSecondRegionVehicleCode(entity.getVirtualCode());
    	    		}else if(StringUtil.equals(DictionaryValueConstants.MOTOR_VEHICLE, entity.getVehicleType())){
    	    			if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)&&StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())){//当查询二级定区车时，二级定区车为空，不添加到显示
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}else{
    	    				infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}
    	    			DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
    		    		beforeInfoEntityDto.setMotorVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		    		beforeInfoEntityDto.setMotorVirtualCode(beforeInfoEntityDto.getVirtualCode());
    	    		}else if(StringUtil.isNotBlank(beforeInfoEntityDto.getFristRegionVehicleNo())&&
    	    				StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, entity.getVehicleType())){
    	    			if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)&&StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())){//当查询二级定区车时，二级定区车为空，不添加到显示
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}else{
    	    				infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}
    	    			DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
    	    			beforeInfoEntityDto.setFristRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		    		beforeInfoEntityDto.setFristRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
    	    		}else if(StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())&&
    	    				StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, entity.getVehicleType())){
    	    			if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)&&StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())){//当查询二级定区车时，二级定区车为空，不添加到显示
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}else{
    	    				infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}
    	    			DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
    	    			beforeInfoEntityDto.setSecondRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		    		beforeInfoEntityDto.setSecondRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
    	    		}
    		    	beforeInfoEntityDto.setVehicleNo("");
    		    	beforeInfoEntityDto.setVirtualCode("");
    		    	beforeInfoEntityDto.setVehicleType("");
    	    	}else{
    		    	entity=convertInfo(entity);
    	    		if(StringUtil.isNotBlank(beforeInfoEntityDto.getRegionCode())){
    	    			//存放到集合
    	    			if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)&&StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())){//当查询二级定区车时，二级定区车为空，不添加到显示
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}else{
    	    				infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));
    	    				beforeInfoEntityDto=new RegionVehicleInfoDto();
    	    			}
    	    		}
    	    		DeepCloneUtil.parentToChild(entity, beforeInfoEntityDto);
    		    	//设置一级定区车的值
    		    	if(StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, beforeInfoEntityDto.getVehicleType())){
    		    		beforeInfoEntityDto.setFristRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		    		beforeInfoEntityDto.setFristRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
    		    	}else if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, beforeInfoEntityDto.getVehicleType())){
    		    		//设置二级定区车的值
    		    		beforeInfoEntityDto.setSecondRegionVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		    		beforeInfoEntityDto.setSecondRegionVehicleCode(beforeInfoEntityDto.getVirtualCode());
    		    	}else if(StringUtil.equals(DictionaryValueConstants.MOTOR_VEHICLE, beforeInfoEntityDto.getVehicleType())){
    		    		//设置机动车的值
    		    		beforeInfoEntityDto.setMotorVehicleNo(beforeInfoEntityDto.getVehicleNo());
    		    		beforeInfoEntityDto.setMotorVirtualCode(beforeInfoEntityDto.getVirtualCode());
    		    	}
    		    	//定人定区区分一二级定区车后以下三个值 不需要
    		    	beforeInfoEntityDto.setVehicleNo("");
    		    	beforeInfoEntityDto.setVirtualCode("");
    		    	beforeInfoEntityDto.setVehicleType("");
    	    	}
    	    }
			if(StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)&&StringUtil.isBlank(beforeInfoEntityDto.getSecondRegionVehicleNo())){//当查询二级定区车时，二级定区车为空，不添加到显示
				//nothing to do
			}else{
				infoDtoList.add(DeepCloneUtil.of(beforeInfoEntityDto));//最后一条
			}
    	    }
    		return infoDtoList;
        }

    /**
     * <p>
     * 验证车牌号与区域的唯一性
     * </p>
     * .
     * 
     * 前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                    1.4	操作用户角色
                    操作用户	描述
                    车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * 
     * SR-1	“车牌号”和“区域”的组合不可重复；
    	一辆车只能对应一种“车辆职责类型”；
        SR-2	一个集中接送货小区是否允许有两个及两个以上的“接送货类型”相同的定区车可配置。当配置为“不允许”时，用户在“定人定区新增界面”点击“保存”时，若所选小区已有“接送货类型”相同的定区车时，则不允许保存。在“查看小区对应车辆信息界面”中，若定区车列表不为空时，则隐藏“添加定区车”按钮。
        SR-3	当为某小区或大区添加车辆时，只能选择负责管理该小区或大区的管理部门对应的车队所拥有的接送货车；
        SR-4	查询定人定区信息时，界面左方的大区小区树形结构中只显示当前登录部门负责管理的大、小区；查询条件中的“车牌号”只可选择当前登录部门所对应车队所属的车辆；
        SR-5	用户登录后默认显示对应车队的定人定区信息，且不可编辑。
        SR-6	当车队有多级时，用户可以查看和修改其所在级别及所在级别管辖的其它所有车队的定人定区信息。用户可通过选择或筛选获得其可查看的其它车队的定人定区信息。
     * 
     * @param vehicleNo
     *            车牌号
     *            
     * @param regionCode
     *            区域虚拟编码
     *            
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-14 上午9:19:39
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryRegionVehicleByVehicleAndRegionCode(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public RegionVehicleEntity queryRegionVehicleByVehicleAndRegionCode(String vehicleNo, String regionCode) {
	//参数验证
	if (StringUtil.isBlank(vehicleNo) || StringUtil.isBlank(regionCode)) {
	    //异常信息处理
	    throw new RegionalVehicleException("传入的参数不允许为空！");
	} else {
	    
	    //验证车牌号与车辆职责类型的唯一性
	    List<RegionVehicleEntity> list = regionalVehicleDao.queryRegionVehicleByVehicleAndRegionCode(vehicleNo, regionCode);
	    
	    if(CollectionUtils.isNotEmpty(list)){
		return list.get(0);
	    }else {
		return null;
	    }
	}
    }
    
    /**
     * <p>根据车牌号、接送货类型、车辆职责类型验证定车定区是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-5-15 下午5:02:17
     * @param vehicleNo 车牌号
     * @param regionType 接送货类型：接货区、送货区
     * @return
     * @see
     */
    public RegionVehicleEntity queryInfoByVehicleNoAndRegionType(String vehicleNo, String regionType,String vehicleType){
	//update by 092020-lipengfei. 定区车分为一级定区车和二级定区车，SQL查询时通过like来统一判断
	if(StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, vehicleType)||
			StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)){
		vehicleType=DictionaryValueConstants.GENERAL_CONSTANT_AREA_CAR;
	}
	List<RegionVehicleEntity> list = regionalVehicleDao.queryInfoByVehicleNoAndRegionType(vehicleNo, regionType, vehicleType);
	if(CollectionUtils.isNotEmpty(list)){
	    return list.get(0);
	}
	return null;
    }
    
    /**
     * <p>验证车牌号与车辆职责类型的唯一性</p> 
     * 
     * 	前置条件	1、	厢式车基础资料完备；
        2、	集中接送货大区基础资料完备；
        3、	集中接送货小区基础资料完备。	SUC-44新增_修改_作废_查询厢式车信息
        SUC-169新增_修改_作废_查询集中接送货大区
        SUC-755新增_修改_作废_查询集中接送货小区
                        后置条件	1、	接送货子系统各用例能正常使用本基础资料，实现各自功能；	SUC-362 查询/处理订单任务
                        1.4	操作用户角色
                        操作用户	描述
                        车队调度	车队调度负责对定人定区基础资料进行新增、修改、作废、查询操作
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-23 下午2:31:22
     * 
     * @param vehicleNo 车牌号
     * 
     * @param vehicleType 车辆职责类型：定区车、机动车
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService#queryRegionVehicleByVehicleAndVehicleType(java.lang.String, java.lang.String)
     */
    @Override
    public RegionVehicleEntity queryRegionVehicleByVehicleAndVehicleType(
	    String vehicleNo) {
	//参数验证
	if (StringUtil.isBlank(vehicleNo)){
	    //异常信息处理
	    throw new RegionalVehicleException("传入的参数不允许为空！");
	} else {
	    //验证车牌号与区域的唯一性
	    List<RegionVehicleEntity> list = regionalVehicleDao.queryRegionVehicleByVehicleAndVehicleType( vehicleNo);
	    
	    if(CollectionUtils.isNotEmpty(list)){
		return list.get(0);
	    }else {
		return null;
	    }
	}
    }
    
    /**
     * <p>验证车牌号与车辆职责类型的唯一性</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-16 下午3:39:15
     * @param vehicleNo 车牌号
     * @param vehicleType vehicleType 车辆职责类型：定区车、机动车
     * @return
     * @see
     */
    public RegionVehicleEntity queryInfosVehicleAndVehicleType(
	    String vehicleNo,String vehicleType) {
	//参数验证
	if (StringUtil.isBlank(vehicleNo)|| StringUtil.isBlank(vehicleType)){
	    //异常信息处理
	    throw new RegionalVehicleException("传入的参数不允许为空！");
	} else {
	    //验证车牌号与区域的唯一性
		//update by 092020-lipengfei. 定区车分为一级定区车和二级定区车，SQL查询时通过like来统一判断
		if(StringUtil.equals(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, vehicleType)||
				StringUtil.equals(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, vehicleType)){
			vehicleType=DictionaryValueConstants.GENERAL_CONSTANT_AREA_CAR;
		}
	    List<RegionVehicleEntity> list = regionalVehicleDao.queryInfosVehicleAndVehicleType( vehicleNo,vehicleType);
	    
	    if(CollectionUtils.isNotEmpty(list)){
		return list.get(0);
	    }else {
		return null;
	    }
	}
    }
    
    /**
     * <p>根据查询条件导出定人定区数据</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-8-23 上午8:49:15
     * @param RegionVehicleEntity entity
     * @return
     * @see
     */
    //update by 092020-lipengfei 将RegionVehicleDto中List<RegionVehicleEntity> 替换为 List<RegionVehicleInfoDto>
    //后者为前者子类，信息更完整
	@Override
	public InputStream queryExportRegionVehicles(RegionVehicleInfoDto entity) {
		entity.setActive(FossConstants.ACTIVE);
		List<RegionVehicleInfoDto> dtos  =queryRegionalVehicleInfoDtos(entity,Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
		
		List<List<String>> rowList = new ArrayList<List<String>>();
		for(RegionVehicleInfoDto row : dtos){
			List<String> columnList = new ArrayList<String>();
			// 根据上级编码及值代码 查询值名称    区域类型
			//DataDictionaryValueEntity  dataDictionaryEntity= dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCodeNoCache("BSE_REGION_TYPE",row.getRegionType());
			// 大区编码
			columnList.add(row.getCode());
			// 大区名称
			columnList.add(row.getRegionName());
			// 大区类型
			if(StringUtils.equals(row.getRegionType(), "PK")){
				columnList.add("接货区");
			}else if(StringUtils.equals(row.getRegionType(), "DE")){
				columnList.add("送货区");
			}
			//机动车车牌号
			columnList.add(row.getMotorVehicleNo());
			// 小区编码
			columnList.add(row.getSmallZoneCode());
			// 小区名称
			columnList.add(row.getSmallZoneName());
			//一级定区车车牌号
			columnList.add(row.getFristRegionVehicleNo());
			//二级定区车车牌号
			columnList.add(row.getSecondRegionVehicleNo());
			rowList.add(columnList);
		}
		// 列头
//		String[] rowHeads = {"车牌号","车辆职责类型","车辆所属车队","大区编码","大区名称","大区类型","小区编码","小区名称"};
		String[] rowHeads = {"大区编码","大区名称","大区类型","机动车","小区编码","小区名称","一级定区车","二级定区车"};
		
		ExportResource exportResource = new ExportResource();
		exportResource.setHeads(rowHeads);
		exportResource.setRowList(rowList);
		ExportSetting exportSetting = new ExportSetting();
		exportSetting.setSheetName(QueryingConstant.REGION_VEHICLE_NAME);
		exportSetting.setSize(NUMBER);
		ExporterExecutor objExporterExecutor = new ExporterExecutor();
		return objExporterExecutor.exportSync(exportResource, exportSetting);
	}
	
	/**
	 * @author 092020-lipengfei
	 * @Description 定区车新增、修改时，若有一、二级定区车时，将对象拆分为两个
	 * 				若二级定区车存在，则本方法返回的为二级定区车对象，若二级定区车不存在，返回为空。
	 * @Time 2014-4-16
	 * @param entity
	 * @return RegionVehicleEntity
	 */
	private RegionVehicleEntity splitRegionVehicleEntity(RegionVehicleInfoDto entity){
		RegionVehicleEntity secondRegionVehicleEntity=null;
		//130566-zjf 重构
		/**
		 * 若为机动车，原来的设置为机动车,说明是大区
		 */
		if(StringUtil.equals("BQ", entity.getRegionNature())){
			entity.setVehicleNo(entity.getMotorVehicleNo());//将车牌号设置为机动车车牌号。
			//虚拟编码不为空
			if(StringUtils.isNotBlank(entity.getMotorVirtualCode())){
				entity.setVirtualCode(entity.getMotorVirtualCode());//将虚拟编码设置为机动车所在记录的虚拟编码。以下代码类似
			}else{
				entity.setVirtualCode(UUIDUtils.getUUID());//新增时虚拟编码是重新生成
			}
			entity.setVehicleType(DictionaryValueConstants.MOTOR_VEHICLE);
			entity.setMotorVehicleNo("");
			entity.setMotorVirtualCode("");
		}else{
			//查询小区
			SmallZoneEntity smallZone  =new SmallZoneEntity();
			//非空校验
			if(StringUtils.isNotBlank(entity.getSmallZoneCode())){
				 smallZone =smallZoneDao.querySmallZoneByCode(entity.getSmallZoneCode());
			}
			/**
			 * 否则就是一级车实体
			 */
			//TODO
			entity.setVehicleNo(entity.getFristRegionVehicleNo());
			//修改是虚拟编码不为空，新增时 虚拟编码为空（重新由UUID生成）
			if(StringUtils.isNotBlank(entity.getFristRegionVehicleCode())){
				entity.setVirtualCode(entity.getFristRegionVehicleCode());
			}else{
				entity.setVirtualCode(UUIDUtils.getUUID());
			}
			entity.setVehicleType(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR);
			//设置小区编码，小区名称
			if(null !=smallZone && StringUtils.isNotBlank(entity.getSmallZoneCode())){
				entity.setRegionCode(smallZone.getVirtualCode());
			}
			if(StringUtils.isNotBlank(entity.getSmallZoneName())){
				entity.setRegionName(entity.getSmallZoneName());
			}
			entity.setFristRegionVehicleNo("");
			entity.setFristRegionVehicleCode("");
			/**
			 * 若二级定区车编码不为空
			 */
			if(StringUtil.isNotBlank(entity.getSecondRegionVehicleNo())){
				//深层复制二级车的实体
				secondRegionVehicleEntity=DeepCloneUtil.of(entity);
				secondRegionVehicleEntity.setVehicleNo(entity.getSecondRegionVehicleNo());
				//修改是虚拟编码不为空，新增时 虚拟编码为空（重新由UUID生成）
				if(StringUtils.isNotBlank(entity.getSecondRegionVehicleCode())){
					secondRegionVehicleEntity.setVirtualCode(entity.getSecondRegionVehicleCode());
				}else{
					secondRegionVehicleEntity.setVirtualCode(UUIDUtils.getUUID());
				}
				secondRegionVehicleEntity.setVehicleType(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR);
				//设置二级小区编码，小区名称
				if(null !=smallZone && StringUtils.isNotBlank(entity.getSmallZoneCode())){
					secondRegionVehicleEntity.setRegionCode(smallZone.getVirtualCode());
				}
				if(StringUtils.isNotBlank(entity.getSmallZoneName())){
					secondRegionVehicleEntity.setRegionName(entity.getSmallZoneName());
				}
			}
		}
		return secondRegionVehicleEntity;
	}
	
	/**
	 * @author 李鹏飞
	 * @Description 新增、修改定人定区时校验。
	 * @Time 2014-4-16
	 */
	private void entityAddOrUpdateCheck(RegionVehicleEntity entity,String type){
		RegionVehicleEntity entity2 = queryRegionVehicleByVehicleAndRegionCode(entity.getVehicleNo(), entity.getRegionCode());
	    if (null != entity2) {
		    entity2=convertInfo(entity2);
			throw new RegionalVehicleException("车辆【"+entity.getVehicleNo()+"】已被【"+entity2.getRegionName()+"】绑定为"+map.get(entity2.getVehicleType())+"！");
	    }
	    //根据车牌号、接送货类型、车辆职责类型验证定车定区是否存在
	    entity2 = queryInfoByVehicleNoAndRegionType(entity.getVehicleNo(),entity.getRegionType(),entity.getVehicleType());
	    if(null != entity2){
	    entity2=convertInfo(entity2);
		throw new RegionalVehicleException("车辆【"+entity.getVehicleNo()+"】已被【"+entity2.getRegionName()+"】绑定为"+map.get(entity2.getVehicleType())+"！");
	    }
		if(StringUtil.equals("add", type)){
			//根据车辆职责类型、区域判断区域是否存在对应职责类型车辆
			int regionVehicleEntityNum=regionalVehicleDao.queryNumByRegionCodeAndVehicleType(entity.getRegionCode(),entity.getVehicleType());
			if(regionVehicleEntityNum>0){
				throw new RegionalVehicleException("【"+entity.getRegionName()+"】已绑定"+map.get(entity.getVehicleType())+"！");
			}
			String vehicleType;
		    if(StringUtil.equals(DictionaryValueConstants.REGION_NATURE_SQ, entity.getRegionNature())){//定区车
				//设置为机动车
				vehicleType = DictionaryValueConstants.MOTOR_VEHICLE;
		    }else{
				//设置为定区车
				vehicleType = DictionaryValueConstants.GENERAL_CONSTANT_AREA_CAR;
		    }
			    entity2 = queryInfosVehicleAndVehicleType(entity.getVehicleNo(),vehicleType);
			if(null != entity2){
				throw new RegionalVehicleException("一辆车【"+entity.getVehicleNo()+"】只能对应一种职责类型，不允许重复！");
		    }
		}else{
			String vehicleType;
		    if(StringUtil.equals(DictionaryValueConstants.REGION_NATURE_SQ, entity.getRegionNature())){//定区车
				//设置为机动车
				vehicleType = DictionaryValueConstants.MOTOR_VEHICLE;
		    }else{
				//设置为定区车
				vehicleType = DictionaryValueConstants.GENERAL_CONSTANT_AREA_CAR;
		    }
		    entity2 = queryInfosVehicleAndVehicleType(entity.getVehicleNo(),vehicleType);
//		    entity2 = queryRegionVehicleByVehicleAndVehicleType(entity.getVehicleNo());
		    if(null != entity2){
//			throw new RegionalVehicleException("存在相同车辆职责类型的车【"+entity.getVehicleNo()+"】,请核实！");
		    	throw new RegionalVehicleException("一辆车【"+entity.getVehicleNo()+"】只能对应一种职责类型，不允许重复！");
		    }
		}
	}
	private static Map<String,String> map=new HashMap<String, String>();
	static{
		map.put(DictionaryValueConstants.MOTOR_VEHICLE, "机动车");
		map.put(DictionaryValueConstants.FIRST_CONSTANT_AREA_CAR, "一级定区车");
		map.put(DictionaryValueConstants.SECOND_CONSTANT_AREA_CAR, "二级定区车");
	}
	
	/**
	 * 根据传入的大区或者小区的的虚拟编码作废定人定区记录
	 *
	 * auther:wangpeng_078816
	 * date:2014-6-9
	 *
	 */
	@Override
	public int deleteRegionalVehicleByRegionCode(String codeStr,
			String modifyUser) {
		if (StringUtil.isBlank(codeStr)) {
		    return FossConstants.FAILURE;
		} else {
		    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
		    List<String> codesList=new ArrayList<String>();
		    List<RegionVehicleEntity> entitys = new ArrayList<RegionVehicleEntity>();
		    for(String code:codes){
		    	codesList.add(code);
		    	RegionVehicleEntity entity = new RegionVehicleEntity();
			    entity.setRegionCode(code);
			    entity.setModifyUser(modifyUser);
			    entity.setModifyDate(new Date());
			    entity.setActive(FossConstants.INACTIVE);
			    entitys.add(entity);
		    }
		    LOGGER.debug("codeStr: " + codeStr);
		    
		    int result = regionalVehicleDao.deleteRegionalVehicleByRegionCode(codesList, modifyUser);
		    
		    if(0 < result){
		    	syncRegionalVehicleToOMS(entitys);
		    }
		    
		    return result;
		}
	}
	
	/**
     * @author dujunhui-187862
     * @Description 自动排单项目：根据小区编码查询对应车牌号
     * @Time 2015-5-8 下午5:05：14
     * @param codes
     * @return
     */
	@Override
	public String getRegionVehicleNoBySmallZoneCode(
			String smallRegionCode) {
		if(StringUtil.isEmpty(smallRegionCode)){
			return null;
		}
		List<RegionVehicleEntity> list=regionalVehicleDao.getRegionVehicleNoBySmallZoneCode(smallRegionCode);
		return (list==null || list.size()<=0) ?null:list.get(0).getVehicleNo();
		
	}
}
