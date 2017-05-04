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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/PickupAndDeliveryBigZoneService.java
 * 
 * FILE NAME        	: PickupAndDeliveryBigZoneService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 
 * 
 * 修订记录 
        日期 	修订内容 	修订人员 	版本号 
        2012-5-15 	新增 	谢艳涛	V0.1
        2012-6-12	根据王偕旭点评修改	谢艳涛	V0.2
        2012-6-18	根据最新需求进行修改：增加“区域类型”属性	谢艳涛	V0.3
        2012-6-21	更名“集中接送货大区”，界面根据需求也做调整	谢艳涛	V0.31
        2012-6-25	小组内部通过审核	谢艳涛	V0.5
        2012-7-4	根据评审修改：增加集中接送货小区的管理部门	谢艳涛	V0.6
        2012-7-17	增加：“归属车队组”；“集中接送货大区编码”修改成自动生成，并添加编码生成规则	谢艳涛	V0.6
        2012-7-28	修改“集中接送货大区”编码规则	谢艳涛	V0.6
        2012-8-2	通过业务部门审核签字版本升级到V0.9	谢艳涛	V0.9
        2012-12-1	根据业务变更需求给接送货小区添加“区域类型”：接货、送货两种类型，同时需要对集中接送货大区业务规则做相应修改	谢艳涛	V1.11
        2012-12-11	根据业务变更需求修改业务规则SR-2、SR-4，增加“所在省”、“所在市”、“所在区县”	谢艳涛	V1.12
        
        1.	SUC-169-新增_修改_作废_查询集中接送货大区
        1.1	相关业务用例
        BUC_FOSS_5.10.10_500 DP-FOSS-接送货业务用例-接送货调度-集中接货调度-处理订单
        BUC_FOSS_5.50.10_500  DP-FOSS-接送货业务用例-集中送货-查询货量
        1.2	用例描述
        该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
        后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
        1.4	操作用户角色
        操作用户	描述
        接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
        系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-主界面
                                        图一：集中接送货大区基础资料管理主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮进入新增界面，参见【图二：集中接送货大区新增/修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，清空查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；作废成功后会弹出作废成功的提示框，若作废失败，弹出作废失败的提示框，并提示失败的原因。
        5)	作废（小区）按钮：点击作废（小区）按钮，将该小区从集中接送货大区中移除，需要弹出确认提示框。
        6)5)	查看详细信息：双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
        7)6)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：集中接送货大区新增/修改界面】。
        8)7)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：集中接送货大区编码、集中接送货大区名称、区域类型、管理部门、归属车队组、集中接送货小区编码、集中接送货小区名称。
        3.	字段输入区域
        1)	查询条件包括：集中接送货大区编码、集中接送货大区名称、区域类型、管理部门、归属车队组。
        1.1	集中接送货大区编码：只能输入数字文本，支持模糊查询。
        1.2	集中接送货大区名称：文本，支持模糊查询。
        1.3	区域类型：下拉框，全部、送货区、接货区，默认全部。
        1.4	管理部门：选择框，支持手动输入模糊查询，也支持从行政组织（车队调度组）基础资料中选择
        1.5	归属车队组：选择框，支持手动输入模糊查询，也支持从行政组织（车队）基础资料中选择
        1.5.4	界面原型-新增和修改界面
          
        图二：集中接送货大区新增/修改界面
        1.5.5	界面描述-新增/修改界面
        1.	字段输入区域
        1)	集中接送货大区编码： 系统自动生成，生成规则参见规则SR-6；
        2)	集中接送货大区名称：必填，文本
        3)	区域类型：必填，选择框，包括：送货区、接货区。
        4)	管理部门：必填，选择框，从行政组织（车队调度组）基础资料中选取
        5)	归属车队组：必填，选择框，从行政组织（车队）基础资料中选取
        6)	所在省: 选择框，必填，从行政区域（省份）基础资料选择
        7)	所在市：选择框，必填，从行政区域（城市）基础资料选择，参见业务规则SR-7
        8)	所在区县：选择框，可为空，从行政区域（区县）基础资料选择,参见业务规则SR-7
        5)9)	
        6)10)	所辖集中接送货小区：必选
        7)11)	描述：选填
        8)12)	GIS上集中接送货区域显示：接送货区域显示是嵌入页面，由GIS系统提供，已选的集中接送货小区会在GIS上显示。
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
        2)	右移按钮：选择未选区域的“集中接送货小区”，点击右移按钮，该选中的“集中接送货小区”会移动到已选区域。
        3)	全右移按钮：点击全右移按钮，可以把未选区域所有“集中接送货小区”全部移动到右边已选区域
        4)	左移按钮：选择已选区域的“集中接送货小区”，点击左移按钮，该选中的“集中接送货小区”会移动到未选区域。
        5)	全左移按钮：点击全左移按钮，可以把已选区域所有“集中接送货小区”全部移动到左边未选区域
        6)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
        1.6	操作步骤
        1.6.1	添加集中接送货大区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货大区管理主界面	【集中接送货大区列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入集中接送货大区详细信息，选择所辖集中接送货小区，点击保存。
        参见业务规则SR-1，SR-2、SR-4、SR-6	【集中接送货大区新增/修改信息】	选择的小区会在GIS地图上显示
        4	返回集中接送货大区管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改集中接送货大区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货大区管理主界面	【集中接送货大区列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改集中接送货大区详细信息, 选择所辖集中接送货小区，点击保存
        参见业务规则SR-1，SR-2、SR-4	【集中接送货大区新增/修改信息】	选择的小区会在GIS地图上显示
        4	返回集中接送货大区管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		
        
        1.6.3	作废集中接送货大区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货大区管理主界面	【集中接送货大区列表信息】	
        2	选择一行或者多行记录，点击作废按钮。		弹出确认对话框。
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		
        
        1.6.4	查询集中接送货大区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货大区管理主界面	【集中接送货大区列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-3	【集中接送货大区查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的与“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致组合必须唯一，即且一个“集中接送货小区” 只能唯一属于“管理部门”相同的不能隶属于两个或两个以上的“集中接货大区”；一个“集中接送货小区”只能唯一属于“管理部门”相同的 不能隶属于两个或两个以上的“集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列表列出所有“管理部门”、“区域类型”“管理部门”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何“接/送货类型”相同的集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
        
        1.8	数据元素
        1.8.1	集中接送货大区新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        集中接送货大区编码	集中接送货大区编码	N/A		10	N/A	系统自动生成,参见业务规则SR-6
        集中接送货大区名称	集中接送货大区的名称	文本		50	是	
        区域类型	区域类型：接货区、送货区	下拉框		10	是	
        管理部门	集中接送货大区所属的管理部门（车队调度组），从行政组织（车队调度组）基础资料中选取	选择框		50	是	
        归属车队组	集中接送货大区所属的车队组，从行政组织（车队）基础资料中选取	选择框		50	是	
        所在省	大区所在省	选择框			是	
        所在市	大区所在市	选择框			是	
        所在区县	大区所在区县	选择框			否	
        所辖集中接送货小区	所辖集中接送货小区	选择框		50	是	
        描述	描述信息	文本		100	否	
        1.8.2	集中接送货大区列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        集中接送货大区编码	集中接送货大区编码	N/A	10	N/A	
        集中接送货大区名称	集中接送货大区的名称	N/A	50	N/A	
        区域类型	区域类型，分为：接货区、送货区	N/A	10	N/A	
        管理部门	集中接送货大区所属的管理部门（车队调度组）	N/A	50	N/A	
        归属车队组	集中接送货大区所属的车队组，从行政组织（车队）基础资料中选取	N/A	50	N/A	
        集中接送货小区编码	集中接送货小区编码	N/A	10	N/A	
        集中接送货小区名称	集中接送货小区名称	N/A	50	N/A	
        1.8.3	集中接送货大区查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        集中接送货大区编码	集中接送货大区编码	文本	10	否	
        集中接送货大区名称	集中接送货大区的名称	文本	50	否	
        区域类型	区域类型：接货区、送货区、全部	下拉框	10	默认为全部	
        管理部门	集中接送货大区所属的管理部门（车队调度组），从行政组织（车队调度组）基础资料中选取	选择框	50	否	
        归属车队组	集中接送货大区所属的车队组，从行政组织（车队）基础资料中选取	选择框	50	否	
        
        1.9	非功能性需求
        使用量	
        2012年全网估计用户数	
        响应要求（如果与全系统要求 不一致的话）	
        使用时间段	
        高峰使用时间段	
        
        1.10	接口描述
        接口名称 	对方系统（外部系统或内部其他模块）	接口描述
        获取集中接送货小区电子地图信息	GIS系统	显示集中接送货小区电子地图信息


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
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliveryBigZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PickupAndDeliveryBiglZoneException;
import com.deppon.foss.module.base.baseinfo.server.service.impl.complex.OrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 集中接送货大区service接口实现:对定人定区大区提供增删改查的操作
 * 
 * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
        后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
        1.4	操作用户角色
        操作用户	描述
        接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
        系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
        1.5	界面要求
        1.5.1	表现方式
 * 
 * 
 *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
    SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
    SR-3	查询支持模糊查询
    SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
    SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
    SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
    
    SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;

 * 
 * <p style="display:none">
 * 
 * modifyRecord
 * 
 * </p>
 * 
 * <p style="display:none">
 * 
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13 上午9:38:46
 * 
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * 
 * @date 2012-10-13 上午9:38:46
 * 
 * @since
 * 
 * @version
 * 
 */
public class PickupAndDeliveryBigZoneService implements
	IPickupAndDeliveryBigZoneService {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PickupAndDeliveryBigZoneService.class);
    /**
     * 大区编码前五位
     */
    private static final String REGION_CODE = "02019";
    /**
     * 集中接送货大区接口.
     */
    private IPickupAndDeliveryBigZoneDao bigZoneDao;
    /**
     * 集中接送货小区service接口.
     */
    private IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    
    /**
     * 定人定区接口 wp_078816_20140609
     */
    private IRegionalVehicleService regionalVehicleService;
    
    /**
     * 接送货大区同步接口
     */
    private ISyncBigZoneService syncBigZoneService;
    
    /**
     * 接送货大区同步接口
     * @author 310854
     * @date 2016-4-6
     */
	public void setSyncBigZoneService(ISyncBigZoneService syncBigZoneService) {
		this.syncBigZoneService = syncBigZoneService;
	}
	
	/**
	 * 同步
	 * @author 310854
	 * @date 2016-4-6
	 */
	private void syncBigZoneToOther(List<BigZoneEntity> entitys){
    	this.syncBigZoneService.syncBigZone(entitys);
    }
	
	/**
     * 
     * 
     * 设置 集中接送货大区接口.
     * 
     * 
     * 
     * @param bigZoneDao
     *            the new 集中接送货大区接口
     *            
     *            
     */
    public void setBigZoneDao(IPickupAndDeliveryBigZoneDao bigZoneDao) {
	this.bigZoneDao = bigZoneDao;
    }
    /**
     * 
     * 
     * 设置 集中接送货小区service接口.
     * 
     * @param pickupAndDeliverySmallZoneService
     *            the pickupAndDeliverySmallZoneService to set
     *            
     *            
     */
    public void setPickupAndDeliverySmallZoneService(IPickupAndDeliverySmallZoneService pickupAndDeliverySmallZoneService) {
	this.pickupAndDeliverySmallZoneService = pickupAndDeliverySmallZoneService;
    }
    /**
     * 
     * 
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoComplexService
     *            the orgAdministrativeInfoComplexService to set
     *            
     *            
     */
    public void setOrgAdministrativeInfoComplexService(OrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
	this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
    /**
     * 
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the new 组织信息 Service接口
     *            
     *            
     */
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    /**
     * 设置 行政区域接口.
     * 
     * @param administrativeRegionsService
     *            the new 行政区域接口
     */
    public void setAdministrativeRegionsService(
	    IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }
    /**
	 * @return the regionalVehicleService
	 */
	public IRegionalVehicleService getRegionalVehicleService() {
		return regionalVehicleService;
	}
	/**
	 * @param regionalVehicleService the regionalVehicleService to set
	 */
	public void setRegionalVehicleService(IRegionalVehicleService regionalVehicleService) {
		this.regionalVehicleService = regionalVehicleService;
	}
	/**
     * 新增集中接送货大区.
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
    
     * 
     * 
     * 
     * @param entity
     *            集中接送货大区实体
     *            
     * @param addList
     * 
     * @param deleteList
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws PickupAndDeliveryBiglZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:41:06
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#addPickupAndDeliveryBigZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
     */
    @Transactional
    @Override
    public int addPickupAndDeliveryBigZone(BigZoneEntity entity,List<String> addList, List<String> deleteList)throws PickupAndDeliveryBiglZoneException {
	if (null == entity) {
	    return FossConstants.FAILURE;
	}
	Date date = new Date();
	entity.setActive(FossConstants.ACTIVE);
	// 记录ID有工具类生成ＵＵＩＤ
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(date);
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 新增时虚拟编码与第一次新增记录的ID相同
	entity.setVirtualCode(entity.getId());
	//校验编码是否存在重复
	BigZoneEntity resultEntity  =bigZoneDao.queryBigzoneByCode(entity.getRegionCode());
	if(null != resultEntity){
		throw new PickupAndDeliveryBiglZoneException("不能新增两个重复的大区");
	}
	int result = bigZoneDao.addPickupAndDeliveryBigZone(entity);
	if (result > 0) {
		//  接送货大区信息同步周围系统OMS
		List<BigZoneEntity> entitys = new ArrayList<BigZoneEntity>();
		entitys.add(entity);
		syncBigZoneToOther(entitys);
		
	    if (CollectionUtils.isNotEmpty(addList)) {// 添加小区虚拟编码集合不为空
		for (String virtualCode : addList) {
		    // 根据大区编码生成小区编码
		    String smallRegionCode = pickupAndDeliverySmallZoneService
			    .generateRegionCode(entity.getRegionCode());
		    SmallZoneEntity smallZone = new SmallZoneEntity();
		    // 设置小区区域编码
		    smallZone.setRegionCode(smallRegionCode);
		    // 设置所属大区虚拟编码
		    smallZone.setBigzonecode(entity.getVirtualCode());
		    // 设置修改日期
		    smallZone.setModifyDate(date);
		    smallZone.setModifyUser("system");
		    smallZone.setVirtualCode(virtualCode);
		    pickupAndDeliverySmallZoneService
			    .updateSmallZoneByVirtualCode(smallZone);
		}
	    }
	    if (CollectionUtils.isNotEmpty(deleteList)) {// 删除小区虚拟编码集合不为空
		for (String virtualCode : deleteList) {
		    SmallZoneEntity smallZone = new SmallZoneEntity();
		    // 设置小区区域编码
		    smallZone.setRegionCode("");
		    // 设置所属大区虚拟编码
		    smallZone.setBigzonecode("");
		    // 设置修改日期
		    smallZone.setModifyDate(date);
		    smallZone.setModifyUser("system");
		    smallZone.setVirtualCode(virtualCode);
		    pickupAndDeliverySmallZoneService
			    .updateSmallZoneByVirtualCode(smallZone);
		}
	    }
	}
	return FossConstants.SUCCESS;
    }
    /**
     * 根据code作废集中接送货大区信息.
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * 
     * @param codeStr
     *            虚拟编码拼接字符串
     *            
     * @param modifyUser
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws PickupAndDeliveryBiglZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:44:11
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#deletePickupAndDeliveryBigZoneByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deletePickupAndDeliveryBigZoneByCode(String codeStr,String modifyUser) throws PickupAndDeliveryBiglZoneException {
	if (StringUtil.isBlank(codeStr)) {
	    throw new PickupAndDeliveryBiglZoneException("集中接送货大区虚拟编码不允许为空！");
	} else {
		 //wp_078816_20140609 作废集中接货小区之前先作废定人定区的数据
	    if(StringUtils.isEmpty(modifyUser)){
	    	modifyUser = FossUserContext.getCurrentUser().getUserName();
	    }
	    regionalVehicleService.deleteRegionalVehicleByRegionCode(codeStr, modifyUser);
	  
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    LOGGER.debug("codeStr: " + codeStr);
	    int result = bigZoneDao.deletePickupAndDeliveryBigZoneByCode(codes,modifyUser);
	    
	    if(result > 0){
	    	List<BigZoneEntity> entitys = new ArrayList<BigZoneEntity>();
			for(String bigvirtualCode : codes){
				
				BigZoneEntity entity = new BigZoneEntity();
				entity.setVirtualCode(bigvirtualCode);
				entity.setModifyDate(new Date());
				entity.setModifyUser(modifyUser);
				entity.setActive(FossConstants.INACTIVE);
				entitys.add(entity);
				
			    SmallZoneEntity smallZone = new SmallZoneEntity();
			    // 设置小区区域编码
			    smallZone.setRegionCode("");
			    // 设置所属大区虚拟编码
			    //smallZone.setBigzonecode("");
			    // 设置修改日期
			    smallZone.setModifyDate(new Date());
			    //wp_078816_20140609 将修改人由system 改为当前操作人
			    if(StringUtils.isEmpty(modifyUser)){
			    	modifyUser = FossUserContext.getCurrentUser().getUserName();
			    }
				if(!StringUtils.isEmpty(modifyUser)){
					smallZone.setModifyUser(modifyUser);
				}else{
					smallZone.setModifyUser("system");
				}
			    //设置小区所属大区虚拟编码
			    smallZone.setBigzonecode(bigvirtualCode);
			    //设置小区所属大区为空
			    smallZone.setBigzonecode1("");
			    
			    pickupAndDeliverySmallZoneService.updateSmallZoneByBigCode(smallZone);
			}
		
			//   接送货大区信息同步周围信息OMS
			syncBigZoneToOther(entitys);
			return FossConstants.SUCCESS;
	    }else {
		return FossConstants.FAILURE;
	    }
	    
	}
    }
    /**
     * 修改集中接送货大区信息 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * 
     * @param entity
     *            集中接送货大区实体
     *            
     * @param addList
     *            新增的小区虚拟编码集合
     *            
     * @param deleteList
     *            删除的小区虚拟编码集合
     *            
     * @return
     * 
     * @throws PickupAndDeliveryBiglZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:44:17
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#updatePickupAndDeliveryBigZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
     */
    @Transactional
    @Override
    public int updatePickupAndDeliveryBigZone(BigZoneEntity entity,List<String> addList, List<String> deleteList) throws PickupAndDeliveryBiglZoneException {
	if (null == entity) {
	    return FossConstants.FAILURE;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    throw new PickupAndDeliveryBiglZoneException("集中接送货大区虚拟编码不允许为空！");
	}
	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };
	// 作废历史记录
	int flag = bigZoneDao.deletePickupAndDeliveryBigZoneByCode(codes,entity.getModifyUser());
	List<BigZoneEntity> entitys = new ArrayList<BigZoneEntity>();
	for(String code : codes){
		BigZoneEntity deleteEntity = new BigZoneEntity();
		deleteEntity.setVirtualCode(code);
		deleteEntity.setModifyDate(new Date());
		deleteEntity.setModifyUser(FossUserContext.getCurrentUser().getUserName());
		deleteEntity.setActive(FossConstants.INACTIVE);
		entitys.add(deleteEntity);
	}
	if (flag > 0) {
	    Date date = new Date();
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(date);
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    entity.setModifyUser(FossUserContext.getCurrentUser().getUserName());
	    int result = bigZoneDao.addPickupAndDeliveryBigZone(entity);
	    if (result > 0) {
	    	
			if (CollectionUtils.isNotEmpty(addList)) {// 添加小区虚拟编码集合不为空
			    for (String virtualCode : addList) {
				// 根据大区编码生成小区编码
				String smallRegionCode = pickupAndDeliverySmallZoneService
					.generateRegionCode(entity.getRegionCode());
				SmallZoneEntity smallZone = new SmallZoneEntity();
				// 设置小区区域编码
				smallZone.setRegionCode(smallRegionCode);
				// 设置所属大区虚拟编码
				smallZone.setBigzonecode(entity.getVirtualCode());
				// 设置修改日期
				smallZone.setModifyDate(date);
				smallZone.setModifyUser("system");
				smallZone.setVirtualCode(virtualCode);
				pickupAndDeliverySmallZoneService.updateSmallZoneByVirtualCode(smallZone);
			    }
			}
			if (CollectionUtils.isNotEmpty(deleteList)) {// 删除小区虚拟编码集合不为空
			    for (String virtualCode : deleteList) {
				SmallZoneEntity smallZone = new SmallZoneEntity();
				// 设置小区区域编码
				smallZone.setRegionCode("");
				// 设置所属大区虚拟编码
				smallZone.setBigzonecode("");
				// 设置修改日期
				smallZone.setModifyDate(date);
				smallZone.setModifyUser("system");
				smallZone.setVirtualCode(virtualCode);
				pickupAndDeliverySmallZoneService.updateSmallZoneByVirtualCode(smallZone);
			    }
			}
			
			//  接送货大区信息同步周围信息OMS
			entitys.add(entity);
			syncBigZoneToOther(entitys);
	    }
	    return FossConstants.SUCCESS;
	}
	return FossConstants.FAILURE;
    }
    /**
     * 根据传入对象查询符合条件所有集中接送货大区信息.
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * 
     * @param entity
     *            集中接送货大区实体
     *            
     * @param limit
     *            每页最大显示记录数
     *            
     * @param start
     *            开始记录数
     *            
     * @return 符合条件的实体列表
     * 
     * @throws PickupAndDeliveryBiglZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:44:23
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#queryPickupAndDeliveryBigZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<BigZoneEntity> queryPickupAndDeliveryBigZones(BigZoneEntity entity, int limit, int start) throws PickupAndDeliveryBiglZoneException {
	entity.setActive(FossConstants.ACTIVE);
	return convertInfoList(bigZoneDao.queryPickupAndDeliveryBigZones(entity, limit, start));
    }
    
    /**
     * 
     * <p>根据条件查询集中接送货大区信息</p> 
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-3-20 上午8:59:43
     * 
     * @param entity
     * 
     * @return
     * 
     * @throws PickupAndDeliveryBiglZoneException 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#queryBigZonesByNameOrCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
     */
    @Override
    public List<BigZoneEntity> queryBigZonesByNameOrCode(BigZoneEntity entity)throws PickupAndDeliveryBiglZoneException {
	entity.setActive(FossConstants.ACTIVE);
	return convertInfoList(bigZoneDao.queryBigZonesByNameOrCode(entity));
    }
    /**
     * 统计总记录数.
     * 
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * 
     * @param entity
     *            集中接送货大区实体
     *            
     * @return
     * 
     * @throws PickupAndDeliveryBiglZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:44:31
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(BigZoneEntity entity) throws PickupAndDeliveryBiglZoneException {
	entity.setActive(FossConstants.ACTIVE);
	return bigZoneDao.queryRecordCount(entity);
    }
    /**
     * 根据区域编码查询集中接送货大区详细信息.
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * 
     * @param regionCode
     *            区域编码
     * @return
     * 
     * @throws PickupAndDeliveryBiglZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-29 下午1:39:44
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#queryBigzoneByCode(java.lang.String)
     */
    @Override
    public BigZoneEntity queryBigzoneByCode(String regionCode) throws PickupAndDeliveryBiglZoneException {
	if (StringUtil.isBlank(regionCode)) {
	    throw new PickupAndDeliveryBiglZoneException("区域编码不允许为空！");
	}
	return convertInfo(bigZoneDao.queryBigzoneByCode(regionCode));
    }
    /**
     * <p>
     * 根据大区虚拟编码查询集中接送货大区详细信息
     * </p>
     * .
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @param virtualCode
     *            虚拟编码
     *            
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-11 下午7:34:29
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#queryBigzoneByVirtualCode(java.lang.String)
     */
    @Override
    public BigZoneEntity queryBigzoneByVirtualCode(String virtualCode) {
	if (StringUtil.isBlank(virtualCode)) {
	    throw new PickupAndDeliveryBiglZoneException("集中接送货大区虚拟编码不允许为空！");
	} else {
	    LOGGER.debug("virtualCode: " + virtualCode);
	    return convertInfo(bigZoneDao.queryBigzoneByVirtualCode(virtualCode));
	}
    }
    /**
     * <p>
     * 填充行政区域名称、管理部门名称、所属车队组名称
     * </p>
     * .
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @param entity
     * 
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-12 上午9:12:38
     * 
     * @see
     */
    private BigZoneEntity convertInfo(BigZoneEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getManagement());
	    // 通过车队编码查询车体的实体
	    OrgAdministrativeInfoEntity transDept = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getTransDepartmentCode());
	    if (null != org) {
		// 设置管理部门名称
		entity.setManagementName(org.getName());
	    } else {
		entity.setManagementName(null);
	    }
	    if (null != transDept) {
		// 车队名称
		entity.setTransDeptName(transDept.getName());
	    } else {
		entity.setTransDeptName(null);
	    }
	    // 设置省份名称
	    entity.setProvName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getProvCode()));
	    // 设置城市名称
	    entity.setCityName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCityCode()));
	    // 设置区县名称
	    entity.setCountyName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCountyCode()));

	    return entity;
	}
    }
    /**
     * <p>
     * 批量填充行政区域名称、管理部门名称
     * </p>
     * .
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @param list
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:13:00
     * @see
     */
    private List<BigZoneEntity> convertInfoList(List<BigZoneEntity> list) {
	List<BigZoneEntity> entities = new ArrayList<BigZoneEntity>();
	if (CollectionUtils.isNotEmpty(list)) {
	    for (BigZoneEntity entity : list) {
		entity = convertInfo(entity);
		entities.add(entity);
	    }
	    return entities;
	} else {
	    return null;
	}
    }
    /**
     * <p>
     * 根据传入的车队调度组编码、区域类型生成集中接送货大区编码
     * </p>
     * .
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @param orgCode
     *            车队调度组编码
     *            
     * @param regionType
     *            区域类型：接货区：PK 送货区：DE
     *            
     * @return 集中接送货大区编码
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-11 上午9:13:14
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService#generateCode(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public String generateCode(String orgCode, String regionType) {
	// 上海事业部-上海车队
	StringBuffer basicCode = new StringBuffer();
	String preCode = generatePreCode(orgCode);
	if(preCode.length() < NumberConstants.NUMBER_5){
	    preCode = REGION_CODE;
	}
	basicCode.append(preCode);
	if (StringUtil.isBlank(regionType)) {
	    throw new PickupAndDeliveryBiglZoneException("区域类型编码不允许为空！");
	} else {
	    if (StringUtil.equals(DictionaryValueConstants.REGION_TYPE_PK,
		    regionType)) {// 接货区
		basicCode.append("J");
	    } else if (StringUtil.equals(
		    DictionaryValueConstants.REGION_TYPE_DE, regionType)) {// 送货区
		basicCode.append("S");
	    } else {
		throw new PickupAndDeliveryBiglZoneException("传入的区域类型编码不正确！");
	    }
	    String code = basicCode.toString();
	    // 按业务规则生成后三位代码
	    String afterCode = generateAfterCode(code);
	    // 生成符合规则的编码
	    basicCode.append(afterCode);
	    return basicCode.toString();
	}
    }
    /**
     * <p>
     * 根据部门编码查询上面的车队、事业部并生成大区前缀编码
     * </p>
     * 
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-26 上午9:12:52
     * 
     * @param orgCode
     *            车队调度组部门编码
     *            
     * @return
     * 
     * @see
     */
    private String generatePreCode(String orgCode) {
	StringBuffer buffer = new StringBuffer();
	List<String> list = new ArrayList<String>();
	// 设置查询部门上面的事业部
	list.add(BizTypeConstants.ORG_DIVISION);
	// 获取部门上面的事业部
	OrgAdministrativeInfoEntity entity = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoByCode(orgCode, list);
	if (null != entity) {
	    // 获取事业部部门编码
	    String divisionOrgCode = entity.getCode();
	    if(divisionOrgCode.length() > NumberConstants.NUMERAL_TWO){
		//截取事业部后两位字符
		buffer.append(divisionOrgCode.substring(divisionOrgCode.length()-NumberConstants.NUMERAL_TWO, divisionOrgCode.length()));
	    }
	}
	// 通过部门编码获得顶级车队组织
	entity = orgAdministrativeInfoComplexService.getTopFleetByCode(orgCode);
	if (null != entity) {
	    // 获取上面顶级车队编码
	    String vehicleOrgCode = entity.getCode();
	    if(vehicleOrgCode.length() > NumberConstants.NUMERAL_THREE){
		//截取车队后面三位字符
		buffer.append(vehicleOrgCode.substring(vehicleOrgCode.length()-NumberConstants.NUMERAL_THREE, vehicleOrgCode.length()));
	    }
	}
	return buffer.toString();
    }
    /**
     * <p>
     * 通过集中接送货大区前五位编码查询数据库然后生成大区后三位编码
     * </p>
     * .
     * 
     * 
     * 该系统用例用于对集中接送货大区基础资料管理，可以对集中接送货大区基础资料进行新增、修改、作废、查询。
            1.3	用例条件
                    条件类型	描述	引用系统用例
                    前置条件	1、集中接送货小区基础资料完备	SUC-755新增_修改_作废_查询集中接送货小区
                    后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货大区基础资料查询	SUC-55新增_修改_作废_查询定人定区
                    1.4	操作用户角色
                    操作用户	描述
                    接送货车队调度	接送货车队调度对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
                    系统管理员	系统管理员对“集中接送货大区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货大区”的“管理部门”从行政组织（车队调度组）基础资料中选择
            1.5	界面要求
            1.5.1	表现方式
     * 
     * 
     *  SR-1	集中接送货大区编码、集中接送货大区名称必须唯一
        SR-2	新增和修改页面中，“所辖的集中接送货小区”的“区域类型”、“管理部门”必须与所属大区的“区域类型”、“管理部门”一致，且一个“集中接货小区” 只能唯一属于“管理部门”相同的“集中接货大区”；一个“集中送货小区”只能唯一属于“管理部门”相同的 “集中送货大区”
        SR-3	查询支持模糊查询
        SR-4	新增和修改页面中，“未选”下拉框列出所有“管理部门”、“区域类型”与集中接送货大区的“管理部门”、“区域类型”相同并且尚未隶属于任何集中接送货大区的小区。
        SR-5	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-6	新增/修改页面，“集中接送货大区编码”系统自动生成，生成规则：事业部编码2位+车队编码2位+接送货类型1位（J 表示接货；S 表示送货）+3位序号（001），如：0207J001，“02”表示事业部编码，“07”表示车队编码，“J”表示接货，“001”表示大区序号。详情参照文档 
        
        SR-7	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”;
     * 
     * @param code
     * 
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-11 上午10:21:01
     * 
     * @see
     */
    private String generateAfterCode(String code) {
	// 根据集中接送货大区生成的前五位编码模糊查询集中接送货大区 按区域编码降序排序
	List<BigZoneEntity> list = bigZoneDao.queryBigZonesByGenerateCode(code);
	// 默认生成编码从"001"开始
	String afterCode = "001";
	if (CollectionUtils.isNotEmpty(list)) {
	    // 取区域编码最大的集中接送货大区信息
	    BigZoneEntity entity = list.get(0);
	    if (null != entity) {
		// 获取区域编码
		String regionCode = entity.getRegionCode();
		if (StringUtil.isBlank(regionCode)) {
		    return afterCode;
		} else {
		    // 获取编码的后三位编码
		    afterCode = regionCode.substring(regionCode.length() - NumberConstants.NUMBER_3,
			    regionCode.length());
		    // 把字符串转化成数字
		    int intCode = Integer.parseInt(afterCode);
		    // 数字加1
		    intCode = intCode + 1;
		    String strCode = String.valueOf(intCode);
		    if (strCode.length() == NumberConstants.NUMERAL_ONE) {
			afterCode = "00" + strCode;
		    } else if (strCode.length() == NumberConstants.NUMERAL_TWO) {
			afterCode = "0" + strCode;
		    } else {
			afterCode = strCode;
		    }
		    return afterCode;
		}
	    } else {
		return afterCode;
	    }
	} else {
	    return afterCode;
	}
    }
}
