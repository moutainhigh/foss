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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/PickupAndDeliverySmallZoneService.java
 * 
 * FILE NAME        	: PickupAndDeliverySmallZoneService.java
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
 * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、行政组织基础资料完备	SUC-85 DP-FOSS-综合管理系统用例-修改_查询行政组织业务属性
        后置条件	1、	为“新增_修改_作废_查询定人定区”系统用例提供集中接送货小区基础资料查询
        2、	为“新增_修改_作废_查询集中接送货大区”系统用例提供集中接送货小区基础资料查询	SUC-55新增_修改_作废_查询定人定区
        SUC-169 新增_修改_作废_查询集中接送货大区
        1.4	操作用户角色
        操作用户	描述
        接送货车队调度	接送货车队调度对“集中接送货小区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货小区”的“管理部门”默认为调度所在的调度组，不能做选择修改。
        系统管理员	系统管理员对“集中接送货小区基础资料”进行新增，修改，作废，查询操作；新增/修改时，“集中接送货小区”的“管理部门”从行政组织（车队调度组）基础资料中选择
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-主界面
                                        图一：集中接送货小区基础资料管理主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮进入新增界面，参见【图二：集中接送货小区新增/修改界面】。
        2)	导出按钮：点击导出按钮，可以导出所有数据至Excel表中
        3)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        4)	重置按钮：点击重置按钮，清空查询条件。
        5)	作废按钮：选中列表中一行或多行记录，点击作废按钮，弹出确认提示框，同时调用GIS接口作废这些记录；或点击各行的作废按钮，弹出确认提示框，作废各行记录（同时调用GIS接口作废该记录），作废成功后会弹出作废成功的提示框；若作废失败，弹出作废失败的提示框，并提示失败原因。
        6)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
        7)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：集中接送货小区新增/修改界面】。
        8)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：集中接送货小区编码、集中接送货小区名称、管理部门、所属送货大区、所属接货大区。
        3.	字段输入区域
        1)	查询条件包括集中接送货小区编码、集中接送货小区名称。
        1.1 集中接送货小区编码：数字文本，支持模糊查询；
        1.2集中接送货小区名称：文本，支持模糊查询。
        1.3 管理部门：选择框，支持手动输入模糊查询，也支持从行政组织（车队调度组）基础资料中选择
        
        1.5.4	界面原型-新增/修改界面
          
        图二：集中接送货小区新增/修改界面
        1.5.5	界面描述-新增/修改界面
        1.	字段输入区域
        1)	集中接送货小区编码： 只读，允许为空，系统自动生成，生成规则参见业务规则SR-4；
        2)	集中接送货小区名称：必填，文本
        2)3)	区域类型：下拉框，包括：接货区、送货区；
        4)	管理部门：必填，选择框，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取；
        5)	所在省：选择框，必填，从行政区域（省份）基础资料选择
        6)	所在市：选择框，必填，从行政区域（城市）基础资料选择，规则见SR-5
        3)7)	所在区县：选择框，可为空，从行政区域（区县）基础资料选择；
        4)8)	描述：选填，文本
        5)9)	GIS集中接送货小区范围：必填，接送货小区范围是嵌入页面，由GIS系统提供。
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，如果当前界面数据未保存，提示“界面数据不为空，是否退出”，点击“是”退出当前界面，返回主界面；否则，不关闭当前界面。
        1.6	操作步骤
        1.6.1	添加集中接送货小区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货小区管理主界面	【集中接送货小区列表信息】	
        2	点击新增按钮，进入新增/修改界面		
        3	输入集中接送货小区详细信息，在GIS上用鼠标划分该集中接送货小区的范围，点击保存。
        参见业务规则SR-1、SR-3、SR-4	【集中接送货小区新增/修改信息】	调用GIS系统提供接口，把集中接送货小区信息和所辖小区范围传到GIS系统
        4	返回集中接送货小区管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改集中接送货小区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货小区管理主界面	【集中接送货小区列表信息】	
        2	点击修改按钮，进入新增/修改界面		
        3	修改集中接送货小区详细信息, 在GIS上用鼠标修改该集中接送货小区的范围，点击保存
        参见业务规则SR-1、SR-3、SR-4	【集中接送货小区新增/修改信息】	调用GIS系统提供接口，把集中接送货小区信息和所辖小区范围传到GIS系统
        4	返回集中接送货小区管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		
        
        1.6.3	作废集中接送货小区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货小区管理主界面	【集中接送货小区列表信息】	
        2	选择一行或者多行记录，点击作废按钮。		弹出确认对话框，同时调用GIS接口作废这些记录
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		
        
        1.6.4	查询集中接送货小区操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入集中接送货小区管理主界面	【集中接送货小区列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-2	【集中接送货小区查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；在调用GIS接口时，把最小的行政区域传给GIS接口；
        
        1.8	数据元素
        1.8.1	集中接送货小区新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        集中接送货小区编码	集中接送货小区营业区编码	文本		4	是	系统自动生成，参见业务规则SR-4
        集中接送货小区名称	集中接送货小区营业区的名称	文本		50	是	
        管理部门	集中接送货小区所属的管理部门（车队调度组）	选择框		50	是	
        所在省	小区所在省	选择框			是	
        所在市	小区所在市	选择框			是	
        所在区县	小区所在区县	选择框			否	
        描述	描述信息	文本		100	否	
        集中接送货小区范围	集中接送货小区范围，在GIS上用鼠标进行圈定	N/A		-	是	
        1.8.2	集中接送货小区列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        集中接送货小区编码	集中接送货小区编码	N/A	4	N/A	
        集中接送货小区名称	集中接送货小区的名称	N/A	50	N/A	
        管理部门	集中接送货小区所属的管理部门（车队调度组）	N/A	50	N/A	
        所属接货大区	所属接货大区	N/A	50	N/A	
        所属送货大区	所属送货大区	N/A	50	N/A	
        1.8.3	集中接送货小区查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        集中接送货小区编码	集中接送货小区编码	文本	4	否	
        集中接送货小区名称	集中接送货小区的名称	文本	50	否	
        管理部门	集中接送货小区所属的管理部门（车队调度组）	选择框	50	否	

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

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPickupAndDeliverySmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliveryBigZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IRegionalVehicleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BigZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PickupAndDeliverySmallZoneException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 集中接送货小区service接口实现：对定人定区小区提供增删改查的操作
 * 
 * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
 * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
 * 
 *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
    SR-2	查询支持模糊查询
    SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
    SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
    
    SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；在调用GIS接口时，把最小的行政区域传给GIS接口；
 * 
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13 上午9:22:32
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * 
 * @date 2012-10-13 上午9:22:32
 * 
 * @since
 * 
 * @version
 * 
 */
public class PickupAndDeliverySmallZoneService implements
	IPickupAndDeliverySmallZoneService {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PickupAndDeliverySmallZoneService.class);
    /**
     * 集中接送货小区Dao接口.
     */
    private IPickupAndDeliverySmallZoneDao smallZoneDao;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 行政区域接口.
     */
    private IAdministrativeRegionsService administrativeRegionsService;
    /**
     * 集中接送货大区service接口.
     */
    private IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService;
    
    /**
     * 定人定区接口 wp_078816_20140609
     */
    private IRegionalVehicleService regionalVehicleService;
    
    /**
     * 集中接送货小区同步接口
     */
    private ISyncSmallZoneService syncSmallZoneService;
    
    /**
     * 设置 集中接送货小区Dao接口.
     * 
     * 
     * @param smallZoneDao
     *            the new 集中接送货小区Dao接口
     *            
     */
    public void setSmallZoneDao(IPickupAndDeliverySmallZoneDao smallZoneDao) {
	this.smallZoneDao = smallZoneDao;
    }
    /**
     * 设置 组织信息 Service接口.
     * 
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
     * 
     * @param administrativeRegionsService
     *            the new 行政区域接口
     *            
     */
    public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }
    /**
     * 设置 集中接送货大区service接口.
     * 
     * 
     * @param pickupAndDeliveryBigZoneService
     *            the new 集中接送货大区service接口
     *            
     */
    public void setPickupAndDeliveryBigZoneService(IPickupAndDeliveryBigZoneService pickupAndDeliveryBigZoneService) {
	this.pickupAndDeliveryBigZoneService = pickupAndDeliveryBigZoneService;
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
	 * 集中接送货小区同步接口
	 * @author 310854
	 * @date 2016-4-6
	 */
	public void setSyncSmallZoneService(ISyncSmallZoneService syncSmallZoneService) {
		this.syncSmallZoneService = syncSmallZoneService;
	}
	
	/**
	 * 同步接口
	 * @author 310854
	 * @date 2016-4-6
	 */
    private void syncSmallZoneToOther(List<SmallZoneEntity> entitys){
    	this.syncSmallZoneService.syncSmallZone(entitys);
    }
	
	/**
     * 新增集中接送货小区.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param entity
     *            集中接送货小区实体
     *            
     * @return 1：成功；-1：失败
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:32:35
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#addPickupAndDeliverySmallZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Transactional
    @Override
    public int addPickupAndDeliverySmallZone(SmallZoneEntity entity) {
	if (null == entity) {
	    throw new PickupAndDeliverySmallZoneException(PickupAndDeliverySmallZoneException.SMALLZONE_NULL_ERROR_CODE);
	}
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 第一次记录新增时，虚拟编码为记录的id
	entity.setVirtualCode(entity.getId());
	entity.setActive(FossConstants.ACTIVE);
	// 验证小区名称是否唯一
	SmallZoneEntity smallZone = querySmallZoneByName(entity.getRegionName());
	if (null != smallZone) {
	    throw new PickupAndDeliverySmallZoneException("集中接送货小区名称不允许重复！");
	}
	//处理GIS导航距离返回值中的“公里”-187862-dujunhui
	String naviDis=entity.getNavigationDistance();
	if(StringUtils.isNotEmpty(naviDis)){//去掉最后两个字符
		if(naviDis.contains("公里")){
			entity.setNavigationDistance(naviDis.replace("公里", ""));
		}
	}
	int result = smallZoneDao.addPickupAndDeliverySmallZone(entity);
	if (result > 0) {
		List<SmallZoneEntity> entitys = new ArrayList<SmallZoneEntity>();
		entitys.add(entity);
		syncSmallZoneToOther(entitys);
	}
	
	return result;
    }
    /**
     * 根据code作废集中接送货小区信息.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param codeStr
     *            虚拟编码拼接字符串
     *            
     * @param modifyUser
     * 
     * @return 1：成功；-1：失败
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:32:03
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#deletePickupAndDeliverySmallZoneByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deletePickupAndDeliverySmallZoneByCode(String codeStr,String modifyUser) {
	if (StringUtil.isBlank(codeStr)) {
	    throw new PickupAndDeliverySmallZoneException("集中接送货小区虚拟编码不允许为空！");
	} else {
	    LOGGER.debug("codeStr: " + codeStr);
	    //wp_078816_20140609 作废集中接货小区之前先作废定人定区的数据
	    if(StringUtils.isEmpty(modifyUser)){
	    	modifyUser = FossUserContext.getCurrentUser().getUserName();
	    }
	    regionalVehicleService.deleteRegionalVehicleByRegionCode(codeStr, modifyUser);
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    int result = smallZoneDao.deletePickupAndDeliverySmallZoneByCode(codes, modifyUser);
	    
	    if (result > 0) {
			List<SmallZoneEntity> entitys = new ArrayList<SmallZoneEntity>();
			for(String code : codes){
				SmallZoneEntity entity = new SmallZoneEntity();
				entity.setVirtualCode(code);
				entity.setModifyDate(new Date());
				entity.setModifyUser(modifyUser);
				entity.setActive(FossConstants.INACTIVE);
				entitys.add(entity);
			}
			syncSmallZoneToOther(entitys);
		}
	    return result;
	}
    }
    /**
     * 修改集中接送货小区信息 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param entity
     *            集中接送货小区实体
     *            
     * @return 1：成功；-1：失败
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:33:00
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#updatePickupAndDeliverySmallZone(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Transactional
    @Override
    public int updatePickupAndDeliverySmallZone(SmallZoneEntity entity) {
	//参数验证
	if (null == entity) {
	    //返回失败信息
	    return FossConstants.FAILURE;
	    //虚拟编码验证
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("集中接送货小区虚拟编码不允许为空！");
	}
	//处理GIS导航距离返回值中的“公里”-187862-dujunhui
	String naviDis=entity.getNavigationDistance();
	if(StringUtils.isNotEmpty(naviDis)){//去掉最后两个字符
		if(naviDis.contains("公里")){
			entity.setNavigationDistance(naviDis.replace("公里", ""));
		}
	}
	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };
	// 作废历史记录
	int flag = smallZoneDao.deletePickupAndDeliverySmallZoneByCode(codes,entity.getModifyUser());
	List<SmallZoneEntity> entitys = new ArrayList<SmallZoneEntity>();
	for(String code : codes){
		SmallZoneEntity deleteEntity = new SmallZoneEntity();
		deleteEntity.setVirtualCode(code);
		deleteEntity.setModifyDate(new Date());
		deleteEntity.setModifyUser(FossUserContext.getCurrentUser().getUserName());
		deleteEntity.setActive(FossConstants.INACTIVE);
		entitys.add(deleteEntity);
	}
	//处理结果验证
	if (flag > 0) {
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    entity.setCreateUser(FossUserContext.getCurrentUser().getUserName());
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    // 验证小区名称是否唯一
	    SmallZoneEntity smallZone = querySmallZoneByName(entity.getRegionName());
	    if (null != smallZone) {
		throw new PickupAndDeliverySmallZoneException("集中接送货小区名称不允许重复！");
	    }
	    int result = smallZoneDao.addPickupAndDeliverySmallZone(entity);
	    if (result > 0) {
			
			entitys.add(entity);
			syncSmallZoneToOther(entitys);
		}
	    return result;
	}
	return FossConstants.FAILURE;
    }
    /**
     * 根据传入对象查询符合条件所有集中接送货小区信息.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param entity
     *            集中接送货小区实体
     *            
     * @param limit
     *            每页最大显示记录数
     *            
     * @param start
     *            开始记录数
     *            
     * @return 符合条件的实体列表
     * 
     * @throws PickupAndDeliverySmallZoneException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-13 上午9:35:32
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#queryPickupAndDeliverySmallZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<SmallZoneEntity> queryPickupAndDeliverySmallZones(SmallZoneEntity entity, int limit, int start)throws PickupAndDeliverySmallZoneException {
	//设置状态
	entity.setActive(FossConstants.ACTIVE);
	//批量填充行政区域名称、管理部门名称 
	return convertInfoList(smallZoneDao.queryPickupAndDeliverySmallZones(entity, limit, start));
    }
    
    /**
     * 根据传入对象查询符合条件所有集中接送货小区信息.（供导出用）
     * 
     * @param entity
     *            集中接送货小区实体
     *            
     * @param limit
     *            每页最大显示记录数
     *            
     * @param start
     *            开始记录数
     *            
     * @return 符合条件的实体列表
     * 
     * @throws PickupAndDeliverySmallZoneException
     * 
     * @author 132599-foss-shenweihua
     * 
     * @date 2013-11-15 上午8:35:32
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#queryPickupAndDeliverySmallZones(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<SmallZoneEntity> queryPickupAndDeliverySmallZonesExport(SmallZoneEntity entity, int limit, int start)throws PickupAndDeliverySmallZoneException {
	//设置状态
	entity.setActive(FossConstants.ACTIVE);
	//批量填充行政区域名称、管理部门名称 
	return convertInfoListExport(smallZoneDao.queryPickupAndDeliverySmallZones(entity, limit, start));
    }
    /** 
     * <p>根据条件查询接送货小区信息</p> 
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
                      
                      
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-3-19 下午5:03:28
     * 
     * @param entity
     * 
     * @return
     * 
     * @throws PickupAndDeliverySmallZoneException 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#querySmallZonesByNameOrCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Override
    public List<SmallZoneEntity> querySmallZonesByNameOrCode(SmallZoneEntity entity) throws PickupAndDeliverySmallZoneException {
	//设置状态
	entity.setActive(FossConstants.ACTIVE);
	//批量填充行政区域名称、管理部门名称 
	return convertInfoList(smallZoneDao.querySmallZonesByNameOrCode(entity));
    }
    /**
     * 统计总记录数.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param entity
     *            集中接送货小区实体
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午9:35:57
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(SmallZoneEntity entity) {
	//设置状态
	entity.setActive(FossConstants.ACTIVE);
	//统计总记录数
	return smallZoneDao.queryRecordCount(entity);
    }
    /**
     * 根据传入的管理部门Code、集中接送货大区的区域类型（接货区、送货区）查询符合条件 的集中接送货小区.
     * 
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * @param deptCode
     *            管理部门Code 必填
     *            
     * @param type
     *            区域类型 必填
     *            
     * @param bigZoneVirtualCode
     *            所属大区虚拟编码 选填 若为：null 查询所有没有所属大区的小区 否则:查询属于该大区的小区
     *            
     * @return 集中接送货小区列表
     * 
     * @throws PickupAndDeliverySmallZoneException
     * 
     * @author 094463-foss-senate
     * 
     * @date 2012-10-25 下午3:04:56
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#querySmallZonesByDeptCode(java.lang.String,
     *      java.lang.String)
     */
    @Transactional
    @Override
    public List<SmallZoneEntity> querySmallZonesByDeptCode(String deptCode,String type, String bigZoneVirtualCode)throws PickupAndDeliverySmallZoneException {
	//参数验证
	if (StringUtil.isBlank(deptCode)) {
	    //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("管理部门编码不允许为空!");
	  //参数验证
	} else if (StringUtil.isBlank(type)) {
	  //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("集中接送货大区区域类型不允许为空！");
	} else {
	    //日志记录
	    LOGGER.debug("deptCode: " + deptCode);
	    //批量填充行政区域名称、管理部门名称 
	    return convertInfoList(smallZoneDao.querySmallZonesByDeptCode(deptCode, type, bigZoneVirtualCode));
	}
    }
    /**
     * 根据区域编码查询集中接送货小区详细信息.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param regionCode
     *            区域编码
     * @return
     * @throws PickupAndDeliverySmallZoneException
     * @author 094463-foss-xieyantao
     * @date 2012-10-29 下午2:27:08
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#querySmallZoneByCode(java.lang.String)
     */
    @Override
    public SmallZoneEntity querySmallZoneByCode(String regionCode)throws PickupAndDeliverySmallZoneException {
	//参数验证
	if (StringUtil.isBlank(regionCode)) {
	    //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("集中接送货区域编码不允许为空！");
	}
	//日志记录
	LOGGER.debug("regionCode: " + regionCode);
	//填充行政区域名称、管理部门名称、所属大区名称 
	return convertInfo(smallZoneDao.querySmallZoneByCode(regionCode));
    }
    /**
     * <p>
     * 填充行政区域名称、管理部门名称、所属大区名称
     * </p>
     * .
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
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
    private SmallZoneEntity convertInfo(SmallZoneEntity entity) {
	//参数验证
	if (null == entity) {
	    //返回空值
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getManagement());
	    //参数验证
	    if (StringUtil.isNotBlank(entity.getBigzonecode())) {
		//根据大区虚拟编码查询集中接送货大区详细信息
		BigZoneEntity bigZone = pickupAndDeliveryBigZoneService.queryBigzoneByVirtualCode(entity.getBigzonecode());
		//结果验证
		if (null != bigZone) {
		    // 设置所属大区名称
		    entity.setBigzoneName(bigZone.getRegionName());
		} else {
		  //设置空值
		    entity.setBigzoneName(null);
		}
	    }
	    if (null != org) {
		// 设置管理部门名称
		entity.setManagementName(org.getName());
	    } else {
		//设置空值
		entity.setManagementName(null);
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
     * 填充行政区域名称、管理部门名称、所属大区名称（供导出用）
     * </p>
     * .
     * 
     * @param entity
     * 
     * @return
     * 
     * @author 132599-foss-shenweihua
     * 
     * @date 2013-11-15 上午8:12:38
     * 
     * @see
     */
    private SmallZoneEntity convertInfoExport(SmallZoneEntity entity) {
	//参数验证
	if (null == entity) {
	    //返回空值
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCodeClean(entity.getManagement());
	    //参数验证
	    if (StringUtil.isNotBlank(entity.getBigzonecode())) {
		//根据大区虚拟编码查询集中接送货大区详细信息
		BigZoneEntity bigZone = pickupAndDeliveryBigZoneService.queryBigzoneByVirtualCode(entity.getBigzonecode());
		//结果验证
		if (null != bigZone) {
		    // 设置所属大区名称
		    entity.setBigzoneName(bigZone.getRegionName());
		} else {
		  //设置空值
		    entity.setBigzoneName(null);
		}
	    }
	    if (null != org) {
		// 设置管理部门名称
		entity.setManagementName(org.getName());
	    } else {
		//设置空值
		entity.setManagementName(null);
	    }
	    return entity;
	}
    }
    /**
     * <p>
     * 批量填充行政区域名称、管理部门名称
     * </p>
     * .
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param list
     * 
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-12 上午9:13:00
     * 
     * @see
     */
    private List<SmallZoneEntity> convertInfoList(List<SmallZoneEntity> list) {
	//定义集合
	List<SmallZoneEntity> entities = new ArrayList<SmallZoneEntity>();
	//集合验证
	if (CollectionUtils.isNotEmpty(list)) {
	    //迭代循环
	    for (SmallZoneEntity entity : list) {
		//填充行政区域名称、管理部门名称、所属大区名称 
		entity = convertInfo(entity);
		//存放到集合
		entities.add(entity);
	    }
	    //返回信息
	    return entities;
	} else {
	    return null;
	}
    }
    
    /**
     * <p>
     * 批量填充行政区域名称、管理部门名称（供导出用）
     * </p>
     * .
     * 
     * @param list
     * 
     * @return
     * 
     * @author 132599-foss-shenweihua
     * 
     * @date 2013-11-15 上午8:13:00
     * 
     * @see
     */
    private List<SmallZoneEntity> convertInfoListExport(List<SmallZoneEntity> list) {
	//定义集合
	List<SmallZoneEntity> entities = new ArrayList<SmallZoneEntity>();
	//集合验证
	if (CollectionUtils.isNotEmpty(list)) {
	    //迭代循环
	    for (SmallZoneEntity entity : list) {
		//填充行政区域名称、管理部门名称、所属大区名称 
		entity = convertInfoExport(entity);
		//存放到集合
		entities.add(entity);
	    }
	    //返回信息
	    return entities;
	} else {
	    return null;
	}
    }
    /**
     * <p>根据传入的条件导出数据</p>.
     *	
     *	集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param entity 
     * 
     * @return 
     * 
     * @throws PickupAndDeliverySmallZoneException 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-17 下午7:42:42
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#exportSmallZoneList(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Override
    public ExportResource exportSmallZoneList(SmallZoneEntity entity)throws PickupAndDeliverySmallZoneException {
	//参数验证
	if(null == entity){
	    //返回空值
	    return null;
	}
	//设置有效
	entity.setActive(FossConstants.ACTIVE);
	//根据传入对象查询符合条件所有集中接送货小区信息.
	List<SmallZoneEntity> list = queryPickupAndDeliverySmallZonesExport(entity, Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
	//集合验证
	if (null == list) {
	    //定义一个集合
	    list = new ArrayList<SmallZoneEntity>();
	}
	//定义集合
	List<List<String>> resultList = new ArrayList<List<String>>();
	//迭代循环
	for(SmallZoneEntity smallZone : list){
	    //实体信息封装到集合中
	    List<String> result = exportInfoList(smallZone);
	    //存放到集合
	    resultList.add(result);
	}
	//导出对象创建
	ExportResource sheet = new ExportResource();
	//设置Excel表头
	sheet.setHeads(ComnConst.PICKUP_SMALLZONE_TITLE);
	//设置导出数据
	sheet.setRowList(resultList);
	return sheet;
    }
    /**
     * <p>导出信息封装</p>.
     * 
     *	集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     * @param entity 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-17 下午7:48:28
     * 
     * @see
     */
    private List<String> exportInfoList(SmallZoneEntity entity){
	//定义一个机会
	List<String> result = new ArrayList<String>();
	//添加小区编码.
	result.add(entity.getRegionCode());
	//小区名称.
	result.add(entity.getRegionName());
	//管理部门名称.
	result.add(entity.getManagementName());
	//所属大区名称(扩展).
	result.add(entity.getBigzoneName());
	
	String regionType  = entity.getRegionType();
	if ("DE".equals(regionType)){
		result.add("送货区");	
	}else if ("PK".equals(regionType)){
		result.add("接货区");
	}
	
//	String area  = entity.getGisArea();
//	if(area!=null&&!area.equals("")){
//		String areas[] = area.split("\\.");
//		String decimal = areas[1].length()<=3?areas[1]:areas[1].substring(0,3);
//		String gisarea = areas[0]+"."+decimal;
//		// 小区面积（单位平方千米）
//		result.add(gisarea);
//	}else{
		result.add(entity.getGisArea());
//	}

		
	result.add(entity.getNavigationDistance());	
		
	//返回集合
	return result;
    }
    /**
     * <p>
     * 验证小区名称是否唯一
     * </p>.
     *	集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     *
     *
     * @param regionName 集中接送货小区名称
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-7 上午9:20:06
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#querySmallZoneByName(java.lang.String)
     */
    @Override
    public SmallZoneEntity querySmallZoneByName(String regionName) {
	//参数验证
	if (StringUtil.isBlank(regionName)) {
	    //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("集中接送货小区名称");
	} else {
	    //验证小区名称是否唯一
	    return smallZoneDao.querySmallZoneByName(regionName);
	}
    }

    /**
     * <p>
     * 根据大区的区域编码动态生成小区区域编码
     * </p>.
     *	
     *	集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     *
     *	
     * @param bigZoneRegionCode 大区的区域编码
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-11 下午4:14:42
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#generateRegionCode(java.lang.String)
     */
    @Override
    public String generateRegionCode(String bigZoneRegionCode) {
	//参数验证
	if (StringUtil.isBlank(bigZoneRegionCode)) {
	    //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("集中接送货大区编码不允许为空！");
	} else {
	    //定义一个StringBuffer
	    StringBuffer regionCode = new StringBuffer();
	    //拼接字符串
	    regionCode.append(bigZoneRegionCode);
	    //根据大区区域编码生成小区后四位编码
	    String afterCode = generateAfterCode(bigZoneRegionCode);
	    //返回符合条件的字符串
	    return regionCode.append(afterCode).toString();
	}
    }
    /**
     * <p>
     * 通过集中接送货大区编码查询数据库然后生成小区后四位编码
     * </p>.
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
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
	// 根据大区的区域编码模糊查询集中接送货小区 按区域编码降序排序
	List<SmallZoneEntity> list = smallZoneDao.querySmallZonesByBigZoneRegionCode(code);
	//默认生成编码从"0001"开始
	String afterCode = "0001";
	if (CollectionUtils.isNotEmpty(list)) {
	    // 取区域编码最大的集中接送货小区信息
	    SmallZoneEntity entity = list.get(0);
	    //结果验证
	    if (null != entity) {
		// 获取区域编码
		String regionCode = entity.getRegionCode();
		//参数验证
		if (StringUtil.isBlank(regionCode)) {
		    //返回编码
		    return afterCode;
		} else {
		    // 获取编码的后三位编码
		    afterCode = regionCode.substring(regionCode.length() - NumberConstants.NUMBER_4,regionCode.length());
		    // 把字符串转化成数字
		    int intCode = Integer.parseInt(afterCode);
		    // 数字加1
		    intCode = intCode + 1;
		    //字符串转换
		    String strCode = String.valueOf(intCode);
		    if (strCode.length() == NumberConstants.NUMERAL_ONE) {
			afterCode = "000" + strCode;
		    } else if (strCode.length() == NumberConstants.NUMERAL_TWO) {
			afterCode = "00" + strCode;
		    }else if(strCode.length() == NumberConstants.NUMERAL_THREE){
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
    /**
     * <p>根据小区虚拟编码修改小区的区域编码、所属大区</p>.
     * 
     * 
     * 集中接送货小区是集中接送货划分的最小单元区，不可再划分，集中接送货小区归属各自的车队调度组进行管理。
     * 本用例提供对集中接送货小区基础资料进行新增、修改、作废、查询、导出等操作。
     * 
     *  SR-1	集中接送货小区编码、集中接送货小区名称必须唯一
        SR-2	查询支持模糊查询
        SR-3	新增/修改页面，如果用户角色为“接送货车队调度”，“管理部门”默认为当前用户所在的部门，不可修改选择；如果用户角色为“系统管理员”，“管理部门”只能从行政组织（车队调度组）基础资料中选取
        SR-4	新增/修改页面，“集中接送货小区编码”允许为空，只读，在分配到所属的大区后，系统自动生成小区编码，生成规则：有4位数字组成，在同一“管理部门”下面顺序编码 ，如“0001”，详情请参照文档 
        
        SR-5	新增/修改页面，“所在省”从行政区域（省份）基础资料中选择，“所在市”从行政区域（城市）基础资料选择，与“所在省”联动，“所在市”隶属于“所在省”；“所在区县”从行政区域（区县）基础资料选择，可为空，与“所在市”联动，“所在区县”必须隶属于“所在市”；
                      在调用GIS接口时，把最小的行政区域传给GIS接口
     * 
     *
     * @param entity 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2013-1-12 上午8:12:26
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#updateSmallZoneByVirtualCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SmallZoneEntity)
     */
    @Override
    public int updateSmallZoneByVirtualCode(SmallZoneEntity entity) {
	//参数严重
	if(null == entity){
	    //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("传入的参数不允许为空！");
	}else {
		//参数严重
	    if(StringUtil.isBlank(entity.getVirtualCode())){
	    	//异常信息处理
	    	throw new PickupAndDeliverySmallZoneException("集中接送货小区虚拟编码不允许为空！");
	    }else {
	    	//根据小区虚拟编码修改小区的区域编码、所属大区
	    	int result = smallZoneDao.updateSmallZoneByVirtualCode(entity);
	    	
	    	//  同步定人定区修改信息到OMS系统
	    	if(0 < result){
	    		List<SmallZoneEntity> entitys = new ArrayList<SmallZoneEntity>();
		    	entity.setActive(FossConstants.ACTIVE);
		    	entitys.add(entity);
		    	syncSmallZoneToOther(entitys);
	    	}
	    	
	    	return result;
	    }
	}
    }
    
    /**
     * <p>
     * 根据所属大区编码修改小区编码、所属大区编码为空
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-5-18 上午9:31:36
     * @param entity
     *            小区信息
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#updateSmallZoneByBigCode(java.lang.String)
     */
    @Override
    public int updateSmallZoneByBigCode(SmallZoneEntity entity) {
	if(null == entity||StringUtils.isBlank(entity.getBigzonecode())){
	    return FossConstants.FAILURE;
	}/*else if(StringUtils.isBlank(entity.getBigzonecode())){
	     return FossConstants.FAILURE;
	}*/
	int result = smallZoneDao.updateSmallZoneByBigCode(entity);
	
	//  同步定人定区修改信息到OMS系统
	if(0 < result){
		List<SmallZoneEntity> entitys = new ArrayList<SmallZoneEntity>();
		entity.setActive(FossConstants.ACTIVE);
		entitys.add(entity);
		syncSmallZoneToOther(entitys);
	}
	
	
	return result;
    }
    /**
     * <p>
     * 根据所属大区编码修改小区编码、所属大区编码为空
     * </p>
     * 
     * @author 130346-foss-lifanghong
     * @date 2014-03-05 上午9:31:36
     * @param entity
     *            小区信息
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPickupAndDeliverySmallZoneService#updateSmallZoneByBigCode(java.lang.String)
     */
    @Override
    public SmallZoneEntity querySmallZoneByVirtualCode(String virtualCode) {
	if(StringUtil.isBlank(virtualCode)){
	      //异常信息处理
	    throw new PickupAndDeliverySmallZoneException("虚拟编码不能为空！");
	}else{
		return smallZoneDao.querySmallZoneByVirtualCode(virtualCode);
	}
    }
}
