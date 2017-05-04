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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/VehicleAgencyCompanyService.java
 * 
 * FILE NAME        	: VehicleAgencyCompanyService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 偏线代理维护员或系统管理员使用该用例新增、修改、作废、查询偏线代理公司信息
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	行政区域基础资料完备；	SUC-33新增_修改_作废_查询行政区域
        后置条件	1、	新增、修改、查询偏线代理时能正常获取所需的偏线代理公司信息；	SUC-649新增_修改_作废_查询偏线代理网点
        1.4	操作用户角色
        操作用户	描述
        偏线代理维护员	偏线代理维护员负责对偏线代理公司基础资料进行新增、修改、作废、查询操作
        系统管理员	系统管理员负责对偏线代理公司基础资料进行新增、修改、作废、查询操作
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-偏线代理公司查询界面
         
        图 1：偏线代理公司查询界面
        1.5.3	界面描述-偏线代理公司查询界面
        该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
        1.	查询条件区域：查询条件包括代理名称、代理编码、管理部门；
        1.1	代理名称：代理的名称；
        1.2	代理编码：可作为代理的唯一标识；
        1.3	管理部门：共用选择框，读取行政组织；
        2.	查询结果列表：数据元素参见【偏线代理公司查询结果列表】，“代理编码”字段添加超链接，点击超链可打开偏线代理公司详情界面，该界面字段均不可修改；
        3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
        	新增：此按钮位于系统悬浮工具条中，点击可打开偏线代理公司新增界面(图2)；
        	修改：图标按钮，位于【偏线代理公司查询结果列表】最前面的操作列，点击可打开偏线代理公司修改界面(图2)；
        	作废：【偏线代理公司查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的偏线代理公司信息；另外存在“作废”的图标按钮，位于【偏线代理公司查询结果列表】最前面的操作列，点击可作废该行偏线代理公司信息；
        	查询：点击此按钮查询符合条件的偏线代理公司信息；
        	重置：重新初始化【偏线代理公司查询条件】；
        4.	提供的相关用例链接或提示信息：作废偏线代理公司信息成功后，提示操作成功，同时刷新【偏线代理公司查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。
        1.5.4	界面原型-偏线代理公司新增、修改界面
         
        图 2：偏线代理公司新增、修改界面
        1.5.5	界面描述-偏线代理公司新增、修改界面
        该界面分为两个部分：偏线代理公司信息输入区域，网点信息列表、功能按钮区；
        1.	偏线代理公司信息输入区域：字段包括代理编码、管理部门、代理名称、代理简称、省份、城市、联系地址、联系电话、联系人、联系人电话、备注；
        1.1	代理编码：可作为偏线代理公司的唯一标识，不可重复，参见业务规则SR-1；
        1.2	管理部门：共用选择框，读取行政组织，参见业务规则SR-2；
        1.3	代理名称：偏线代理公司的名称，参见业务规则SR-1；
        1.4	代理简称：偏线代理公司的简称，参见业务规则SR-1；
        1.5	省份、城市：共用选择框，读取省市信息基础资料；
        2.	网点信息列表：显示该偏线代理公司对应的偏线代理网点，最前面的操作列包含修改、作废的图标按钮，点击“修改”按钮打开修改偏线代理界面，点击“作废”作废该偏线代理；
        3.	功能按钮区：按钮包括保存、取消、重置、添加网点；
        	保存：点击此按钮保存偏线代理公司信息；
        	取消：退出当前界面；
        	重置：点击此按钮重新初始化输入区域各控件；
        	添加网点：点击该按钮，打开新增偏线代理界面，参见SUC-649-新增_修改_作废_查询偏线代理网点用例；
        4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。
        1.6	操作步骤
        1.6.1	查询偏线代理公司
        序号	基本步骤	相关数据	补充步骤
        1	进入偏线代理公司查询界面		
        2	输入查询条件，点击“查询”按钮	【偏线代理公司查询条件】
        【偏线代理公司查询结果列表】	后台执行查询，并把查询结果返回到前台
        
        1.6.2	新增偏线代理公司
        序号	基本步骤	相关数据	补充步骤
        1	点击悬浮工具条中的“新增”按钮		打开新增界面(图2)
        2	输入偏线代理公司信息，点击“保存”按钮		校验通过后，保存偏线代理公司信息
        
        序号	扩展事件	相关数据	备注
        2a	步骤2中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        2b	步骤2中，点击“取消”按钮		退出当前界面
        2c	步骤2中，点击“保存”按钮之后，点击“添加网点”按钮		打开新增偏线代理界面，并将本偏线代理公司填充至该界面的“偏线代理公司”；参见SUC-649-新增_修改_作废_查询偏线代理网点用例，参见业务规则SR-3
        
        1.6.3	修改偏线代理公司
        序号	基本步骤	相关数据	补充步骤
        1	点击查询结果列表中的操作列中的“修改”按钮		打开偏线代理公司修改界面(图2)
        2	输入【偏线代理公司信息】点击修改界面的“保存”按钮	【偏线代理公司信息】	校验通过后，更新偏线代理公司信息
        
        序号	扩展事件	相关数据	备注
        2a	步骤2中，点击“保存”按钮之前，点击“重置”按钮		重新填充各控件的值为修改前的值
        2b	步骤2中，点击“取消”按钮		退出当前界面
        2c	步骤2中，点击“保存”按钮之后，点击“添加网点”按钮		打开修改偏线代理界面，参见SUC-649-新增_修改_作废_查询偏线代理网点用例，参见业务规则SR-3
        
        1.6.4	作废偏线代理公司
        序号	基本步骤	相关数据	补充步骤
        1	点击查询结果列表中的操作列中的“作废”按钮		校验是否可作废，若可作废则弹出“确认/取消”的对话框，内容为“您确定要作废该偏线代理公司信息吗？”
        2	在弹出的“确认/取消”对话框中，点击“确认”按钮		更新该偏线代理公司信息状态为“已作废”，同时刷新【偏线代理公司查询结果列表】
        
        序号	扩展事件	相关数据	备注
        1a	除步骤1的操作方式外，还可以勾选列表中的记录，点击列表顶部或下部的“作废”按钮	【偏线代理公司查询结果列表】	
        2a	步骤2中，点击“取消”按钮，则回到查询界面		
        
        1.7	业务规则
        序号	描述
        SR-1	新增、修改偏线代理公司信息时：
        1、	“代理编码”不可重复；
        2、	“代理名称”、“代理简称”均不可重复；
        SR-2	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        SR-3	1、	只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司；
        2、	新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作；
        3、	只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点；
        SR-4	只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
        
        1.8	数据元素
        1.8.1	偏线代理公司查询条件
        字段名称 	说明	输入限制	输入提示文本	长度	是否必填	备注
        代理名称	代理名称	文本		40	否	支持模糊查询
        代理编码	代理编码	文本		40	否	支持模糊查询
        管理部门	负责管理该偏线代理公司的本公司部门	N/A		N/A	否	
        1.8.2	偏线代理公司查询结果列表
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        代理编码	代理名称	N/A	N/A	N/A	N/A	
        代理名称	代理编码	N/A	N/A	N/A	N/A	
        管理部门	负责管理该偏线代理公司的本公司部门	N/A	N/A	N/A	N/A	
        省份	偏线代理公司所在省份	N/A	N/A	N/A	N/A	
        城市	偏线代理公司所在城市	N/A	N/A	N/A	N/A	
        
        1.8.3	偏线代理公司信息
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        代理编码	代理编码	文本		40	是	
        管理部门	管理部门	N/A		N/A	是	
        代理名称	代理名称	N/A		40	N/A	
        代理简称	代理简称			40	是	
        省份	偏线代理公司所在省份	共用选择框		N/A	是	
        城市	偏线代理公司所在城市	共用选择框		N/A	是	
        联系地址	偏线代理公司所在地址	文本		200	是	
        联系电话	偏线代理公司联系电话	文本		40	是	
        联系人	偏线代理公司提供的联系人姓名	文本		40	否	
        联系人电话	偏线代理公司提供的联系人电话	文本		40	否	
        备注	备注信息	文本		200	否	


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
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendAgentCompanyInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleAgencyCompanyException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 偏线代理公司service接口实现：对偏线代理公司信息的增删改查的基本操作
 * 
 * 
 * 
 * 偏线代理维护员或系统管理员使用该用例新增、修改、作废、查询偏线代理公司信息 1.3 用例条件 条件类型 描述 引用系统用例 前置条件 1、
 * 行政区域基础资料完备； SUC-33新增_修改_作废_查询行政区域 后置条件 1、 新增、修改、查询偏线代理时能正常获取所需的偏线代理公司信息；
 * SUC-649新增_修改_作废_查询偏线代理网点 1.4 操作用户角色 操作用户 描述 偏线代理维护员
 * 偏线代理维护员负责对偏线代理公司基础资料进行新增、修改、作废、查询操作 系统管理员 系统管理员负责对偏线代理公司基础资料进行新增、修改、作废、查询操作
 * 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2 界面原型-偏线代理公司查询界面
 * 
 * SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
 * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
 * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
 * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息，添加的偏线代理公司对应网点在SUC
 * -649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点；
 * SR-4 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
 * 
 * <p style="display:none">
 * 
 * modifyRecord
 * 
 * </p>
 * 
 * <p style="display:none">
 * 
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 下午2:05:37
 * 
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * 
 * @date 2012-10-15 下午2:05:37
 * 
 * @since
 * 
 * @version
 */
public class VehicleAgencyCompanyService implements
	IVehicleAgencyCompanyService {
	
	/**
     * 同步代理公司给wdgh 系统接口service
     */
     
    private ISendAgentCompanyInfoToWDGHService sendAgentCompanyInfoToWDGHService;
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(VehicleAgencyCompanyService.class);

    /**
     * 偏线代理公司DAO接口.
     */
    
    private IVehicleAgencyCompanyDao vehicleAgencyCompanyDao;

    /**
     * 偏线代理网点service 接口.
     */
     
    private IVehicleAgencyDeptService vehicleAgencyDeptService;

    /**
     * 组织信息 Service接口.
     */
   
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 行政区域接口.
     */
    
    private IAdministrativeRegionsService administrativeRegionsService;

    
    private IAirAgencyCompanyDao airAgencyCompanyDao;

    /**
     * @param airAgencyCompanyDao
     *            the airAgencyCompanyDao to set
     */
    public void setAirAgencyCompanyDao(IAirAgencyCompanyDao airAgencyCompanyDao) {
	this.airAgencyCompanyDao = airAgencyCompanyDao;
    }

    /**
     * 设置 偏线代理公司DAO接口.
     * 
     * @param vehicleAgencyCompanyDao
     *            the new 偏线代理公司DAO接口
     */
    public void setVehicleAgencyCompanyDao(
	    IVehicleAgencyCompanyDao vehicleAgencyCompanyDao) {
	this.vehicleAgencyCompanyDao = vehicleAgencyCompanyDao;
    }

    /**
     * 设置 组织信息 Service接口.
     * 
     * @param orgAdministrativeInfoService
     *            the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(
	    IOrgAdministrativeInfoService orgAdministrativeInfoService) {
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
     * 设置 偏线代理网点service 接口.
     * 
     * @param vehicleAgencyDeptService
     *            the new 偏线代理网点service 接口
     */
    public void setVehicleAgencyDeptService(
	    IVehicleAgencyDeptService vehicleAgencyDeptService) {
	this.vehicleAgencyDeptService = vehicleAgencyDeptService;
    }

    /**
     * 设置 同步代理公司接口.
     * 
     * @param sendAgentCompanyInfoToWDGHService
     *            the new 同步代理公司接口
     */
    public void setSendAgentCompanyInfoToWDGHService(
			ISendAgentCompanyInfoToWDGHService sendAgentCompanyInfoToWDGHService) {
		this.sendAgentCompanyInfoToWDGHService = sendAgentCompanyInfoToWDGHService;
	}
    /**
     * 新增偏线代理公司. 1 点击悬浮工具条中的“新增”按钮 打开新增界面(图2) 2 输入偏线代理公司信息，点击“保存”按钮
     * 校验通过后，保存偏线代理公司信息
     * 
     * 序号 扩展事件 相关数据 备注 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新初始化各控件的值 2b 步骤2中，点击“取消”按钮
     * 退出当前界面 2c 步骤2中，点击“保存”按钮之后，点击“添加网点”按钮
     * 打开新增偏线代理界面，并将本偏线代理公司填充至该界面的“偏线代理公司”；参见SUC
     * -649-新增_修改_作废_查询偏线代理网点用例，参见业务规则SR-3
     * 
     * 
     * SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param entity
     *            偏线/空运代理公司实体
     * @return 1：成功；-1：失败
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:07:38
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#addVehicleAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    //@Transactional
    @Override
    public int addVehicleAgencyCompany(BusinessPartnerEntity entity)
	    throws VehicleAgencyCompanyException {
	// 创建日期
	Date date = new Date();
	// 参数判断
	if (null == entity) {
	    // 返回失败
	    return FossConstants.FAILURE;
	}
	// 设置代理公司类别为：偏线
	entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	// 设置新增记录默认生效
	entity.setActive(FossConstants.ACTIVE);
	// 记录ID有工具类生成ＵＵＩＤ
	entity.setId(UUIDUtils.getUUID());
	// 新增时虚拟编码与第一次新增记录的ID相同
	entity.setVirtualCode(entity.getId());
	// 设置创建日期
	entity.setCreateDate(date);
	// 设置版本号
	entity.setVersionNo(date.getTime());
	// 设置修改日期
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));

	// 验证代理公司名称是否重复
	BusinessPartnerEntity businessPartner = queryEntityByName(entity
		.getAgentCompanyName());
	// 结果验证
	if (null != businessPartner) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理公司名称不允许重复！");
	}
	// 验证代理公司简称是否重复
	businessPartner = queryEntityBySimplename(entity.getSimplename());
	// 结果验证
	if (null != businessPartner) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理公司简称不允许重复！");
	}

	// 验证代理公司编码是否以KY开头
	if (StringUtil.isNotEmpty(entity.getAgentCompanyCode())) {
	    // 判断
	    if (entity.getAgentCompanyCode().length() < NumberConstants.NUMERAL_TWO) {
		// 异常信息处理
		throw new VehicleAgencyCompanyException("偏线代理公司代理编码长度必须大于2！");
	    }
	    // if
	    // (!StringUtil.equals(DictionaryValueConstants.OUTERBRANCH_TYPE_PX,entity.getAgentCompanyCode().substring(NumberConstants.NUMERAL_ZORE,NumberConstants.NUMERAL_TWO)))
	    // {
	    // //异常信息处理
	    // throw new VehicleAgencyCompanyException("偏线代理公司代理编码必须以PX开头！");
	    // }
	}
	// 验证代理公司编码是否重复
	businessPartner = queryEntityByCode(entity.getAgentCompanyCode());
	// 结果验证
	if (null != businessPartner) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理公司编码不允许重复！");
	}
	int result = vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
	
	// 新增偏线代理公司
	sendAgentCompanyInfoToWDGHService.syncAgentCompanyInfo(entity, (NumberConstants.ONE).toString());
	
	return result;
    }

    /**
     * 根据code作废偏线代理公司. 偏线代理维护员或系统管理员使用该用例新增、修改、作废、查询偏线代理公司信息 1.3 用例条件 条件类型 描述
     * 引用系统用例 前置条件 1、 行政区域基础资料完备； SUC-33新增_修改_作废_查询行政区域 后置条件 1、
     * 新增、修改、查询偏线代理时能正常获取所需的偏线代理公司信息； SUC-649新增_修改_作废_查询偏线代理网点 1.4 操作用户角色 操作用户
     * 描述 偏线代理维护员 偏线代理维护员负责对偏线代理公司基础资料进行新增、修改、作废、查询操作 系统管理员
     * 系统管理员负责对偏线代理公司基础资料进行新增、修改、作废、查询操作 1.5 界面要求 1.5.1 表现方式 Web页面 1.5.2
     * 界面原型-偏线代理公司查询界面
     * 
     * SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param codeStr
     *            d
     * @param modifyUser
     * 
     * @return
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:07:50
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#deleteVehicleAgencyCompanyByCode(java.lang.String[])
     */
    //@Transactional
    @Override
    public int deleteVehicleAgencyCompanyByCode(String codeStr,
	    String modifyUser) throws VehicleAgencyCompanyException {
	// 参数验证
	if (StringUtil.isBlank(codeStr)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("偏线代理公司虚拟编码不允许为空！");
	} else {
	    LOGGER.debug("codeStr: " + codeStr);
	    String[] codes = StringUtil
		    .split(codeStr, SymbolConstants.EN_COMMA);
	    for (int i = 0; i < codes.length; i++) {
		// 根据代理公司虚拟编码查询所属代理网点
		List<OuterBranchEntity> entityList = vehicleAgencyDeptService
			.queryOuterBranchsByComCode(codes[i]);
		// 集合判断
		if (CollectionUtils.isNotEmpty(entityList)) {
		    // 异常信息处理
		    throw new VehicleAgencyCompanyException("存在代理网点代理公司不允许作废！");
		} else {
		    // 根据虚拟编码作废空运代理公司,同步作废代理公司到WDGH
		    vehicleAgencyCompanyDao.deleteAgencyCompanyByVirtualCode(
			    codes[i], modifyUser);
		}
	    }
	    // 返回成功
	    return FossConstants.SUCCESS;
	}
    }

    /**
     * 修改偏线代理公司 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     * 
     * 
     * 1 点击查询结果列表中的操作列中的“修改”按钮 打开偏线代理公司修改界面(图2) 2 输入【偏线代理公司信息】点击修改界面的“保存”按钮
     * 【偏线代理公司信息】 校验通过后，更新偏线代理公司信息
     * 
     * 序号 扩展事件 相关数据 备注 2a 步骤2中，点击“保存”按钮之前，点击“重置”按钮 重新填充各控件的值为修改前的值 2b
     * 步骤2中，点击“取消”按钮 退出当前界面 2c 步骤2中，点击“保存”按钮之后，点击“添加网点”按钮
     * 打开修改偏线代理界面，参见SUC-649-新增_修改_作废_查询偏线代理网点用例，参见业务规则SR-3
     * 
     * SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param entity
     * 
     * @return
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:07:56
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#updateVehicleAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    @Transactional
    @Override
    public int updateVehicleAgencyCompany(BusinessPartnerEntity entity)
	    throws VehicleAgencyCompanyException {
	// 参数判断
	if (null == entity) {
	    // 返回失败
	    return FossConstants.FAILURE;
	    // 参数判断
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("偏线代理公司虚拟编码不允许为空！");
	}
	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };
	// 作废历史记录
	int flag = vehicleAgencyCompanyDao.deleteVehicleAgencyCompanyByCode(
		codes, entity.getModifyUser());
	if (flag > 0) {
	    // 设置代理公司类别为：偏线
	    entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	    // 设置新增记录默认生效
	    entity.setActive(FossConstants.ACTIVE);
	    // 记录ID有工具类生成ＵＵＩＤ
	    entity.setId(UUIDUtils.getUUID());
	    // 设置创建日期
	    entity.setCreateDate(new Date());
	    // 设置版本号
	    entity.setVersionNo(entity.getCreateDate().getTime());
	    // 设置修改人
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    // 验证代理公司名称是否重复
	    BusinessPartnerEntity businessPartner = queryEntityByName(entity
		    .getAgentCompanyName());
	    if (null != businessPartner) {
		// 异常信息处理
		throw new VehicleAgencyCompanyException("代理公司名称不允许重复！");
	    }
	    // 验证代理公司简称是否重复
	    businessPartner = queryEntityBySimplename(entity.getSimplename());
	    // 结果验证
	    if (null != businessPartner) {
		// 异常信息处理
		throw new VehicleAgencyCompanyException("代理公司简称不允许重复！");
	    }
	    // 验证代理公司编码是否以PX开头
	    if (StringUtil.isNotEmpty(entity.getAgentCompanyCode())) {
		// 参数验证
		if (entity.getAgentCompanyCode().length() < NumberConstants.NUMERAL_TWO) {
		    // 异常信息处理
		    throw new VehicleAgencyCompanyException(
			    "偏线代理公司代理编码长度必须大于2！");
		}
		// 参数验证
		// if
		// (!StringUtil.equals(DictionaryValueConstants.OUTERBRANCH_TYPE_PX,entity.getAgentCompanyCode().substring(NumberConstants.NUMERAL_ZORE,NumberConstants.NUMERAL_TWO)))
		// {
		// //异常信息处理
		// throw new
		// VehicleAgencyCompanyException("偏线代理公司代理编码必须以KY开头！");
		// }
	    }
	    // 验证代理公司编码是否重复
	    businessPartner = queryEntityByCode(entity.getAgentCompanyCode());
	    // 结果验证
	    if (null != businessPartner) {
		// 异常信息处理
		throw new VehicleAgencyCompanyException("代理公司编码不允许重复！");
	    }
	    // 新增偏线代理公司
	    int result = vehicleAgencyCompanyDao.addVehicleAgencyCompany(entity);
	    
	    //同步更新代理公司到WDGH
	    sendAgentCompanyInfoToWDGHService.syncAgentCompanyInfo(entity, (NumberConstants.TWO).toString());
	    
	    return result;
	}
	return FossConstants.FAILURE;
    }

    /**
     * 根据传入对象查询符合条件所有偏线代理公司信息.
     * 
     * 
     * 
     * SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param entity
     * 
     * @param limit
     * 
     * @param start
     * 
     * @return
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:08:02
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryVehicleAgencyCompanys(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity,
     *      int, int)
     */
    @Transactional
    @Override
    public List<BusinessPartnerEntity> queryVehicleAgencyCompanys(
	    BusinessPartnerEntity entity, int limit, int start)
	    throws VehicleAgencyCompanyException {
	// 设置状态
	entity.setActive(FossConstants.ACTIVE);
	// 设置 代理公司类别.
	entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	// 批量填充行政区域名称、管理部门名称
	return convertOuterBranchList(vehicleAgencyCompanyDao
		.queryVehicleAgencyCompanys(entity, limit, start));
    }

    /**
     * 统计总记录数. SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param entity
     * 
     * @return
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:08:08
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    @Override
    public Long queryRecordCount(BusinessPartnerEntity entity)
	    throws VehicleAgencyCompanyException {
	// 设置状态
	entity.setActive(FossConstants.ACTIVE);
	// 设置 代理公司类别.
	entity.setAgentCompanySort(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	// 统计总记录数
	return vehicleAgencyCompanyDao.queryRecordCount(entity);
    }

    /**
     * 根据偏线代理公司名称查询代理公司信息 (验证该代理公司是否存在). SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、
     * “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param agentCompanyName
     *            偏线代理公司名称
     * @return null:不存在 BusinessPartnerEntity:存在
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-23 上午11:05:59
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryEntityByName(java.lang.String)
     */
    @Transactional
    @Override
    public BusinessPartnerEntity queryEntityByName(String agentCompanyName)
	    throws VehicleAgencyCompanyException {
	// 参数验证
	if (StringUtil.isBlank(agentCompanyName)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("偏线代理公司名称不允许为空！");
	}
	// 填充行政区域名称、管理部门名称
	return convertBusinessPartner(vehicleAgencyCompanyDao
		.queryEntityByName(agentCompanyName));
    }

    /**
     * 根据偏线代理公司简称查询代理公司信息 (验证该代理公司是否存在). SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、
     * “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param simplename
     *            代理公司简称
     * @return null:不存在 BusinessPartnerEntity:存在
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-23 上午11:10:56
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryEntityBySimplename(java.lang.String)
     */
    @Transactional
    @Override
    public BusinessPartnerEntity queryEntityBySimplename(String simplename)
	    throws VehicleAgencyCompanyException {
	// 参数验证
	if (StringUtil.isBlank(simplename)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("偏线代理公司简称不允许为空！");
	}
	// 填充行政区域名称、管理部门名称
	return convertBusinessPartner(vehicleAgencyCompanyDao
		.queryEntityBySimplename(simplename));
    }

    /**
     * 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在). SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、
     * “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param agentCompanyCode
     *            代理公司编码
     * @return null:不存在 BusinessPartnerEntity:存在
     * 
     * 
     * 
     * 
     * @throws VehicleAgencyCompanyException
     * 
     * 
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-23 上午11:13:16
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryEntityByCode(java.lang.String)
     */
    @Transactional
    @Override
    public BusinessPartnerEntity queryEntityByCode(String agentCompanyCode)
	    throws VehicleAgencyCompanyException {
	// 参数验证
	if (StringUtil.isBlank(agentCompanyCode)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理公司编码不允许为空！");
	}
	// 填充行政区域名称、管理部门名称
	return convertBusinessPartner(vehicleAgencyCompanyDao
		.queryEntityByCode(agentCompanyCode,
			DictionaryValueConstants.OUTERBRANCH_TYPE_PX));
    }

    /**
     * 
     * <p>
     * 通过代理公司编码查询代理公司名称（不区分偏线或空运）
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 28, 2013 2:45:19 PM
     * @param code
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryBusinessPartnerNameByCode(java.lang.String)
     */
    @Override
    public String queryBusinessPartnerNameByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	BusinessPartnerEntity entity = vehicleAgencyCompanyDao
		.queryEntityByCode(code, null);
	return entity == null ? null : entity.getAgentCompanyName();
    }

    /**
     * <p>
     * 填充行政区域名称、管理部门名称
     * </p>
     * . SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param entity
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * @return
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-12 上午9:12:38
     * 
     * @see
     */
    private BusinessPartnerEntity convertBusinessPartner(
	    BusinessPartnerEntity entity) {
	// 参数验证
	if (null == entity) {
	    // 返回空
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getManagement());
	    // 结果验证
	    if (null != org) {
		// 设置 管理部门名称（扩展）.
		entity.setManagementName(org.getName());
	    }
	    // 设置省份名称
	    entity.setProvName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity.getProvCode()));
	    // 设置城市名称
	    entity.setCityName(administrativeRegionsService
		    .queryAdministrativeRegionsNameByCode(entity.getCityCode()));
	    // 返回信息
	    return entity;
	}
    }

    /**
     * <p>
     * 批量填充行政区域名称、管理部门名称
     * </p>
     * . SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
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
    private List<BusinessPartnerEntity> convertOuterBranchList(
	    List<BusinessPartnerEntity> list) {
	// 定义集合
	List<BusinessPartnerEntity> entities = new ArrayList<BusinessPartnerEntity>();
	// 集合不为空验证
	if (CollectionUtils.isNotEmpty(list)) {
	    // 迭代循环
	    for (BusinessPartnerEntity entity : list) {
		// 填充行政区域名称、管理部门名称
		entity = convertBusinessPartner(entity);
		// 存放集合
		entities.add(entity);
	    }
	    // 返回集合
	    return entities;
	} else {
	    // 返回空
	    return null;
	}
    }

    /**
     * 根据代理公司虚拟编码查询代理公司信息 (验证该代理公司是否存在). SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、
     * “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param virtualCode
     *            代理公司虚拟编码
     * 
     * @return null:不存在 BusinessPartnerEntity:存在
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-12 下午3:18:28
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryEntityByVirtualCode(java.lang.String)
     */
    @Override
    public BusinessPartnerEntity queryEntityByVirtualCode(String virtualCode) {
	// 参数验证
	if (StringUtil.isBlank(virtualCode)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理公司虚拟编码不允许为空！");
	}
	// 根据代理公司虚拟编码查询代理公司信息
	return vehicleAgencyCompanyDao.queryEntityByVirtualCode(virtualCode);
    }

    /**
     * <p>
     * 根据传入的代理编码和代理类型判断代理信息是否存在
     * </p>
     * . SR-1 新增、修改偏线代理公司信息时： 1、 “代理编码”不可重复； 2、 “代理名称”、“代理简称”均不可重复； SR-2
     * 新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”； SR-3 1、
     * 只有新增偏线代理公司时的管理部门或者系统管理员才有权限修改、作废偏线代理公司； 2、
     * 新增、修改偏线代理公司时，点击“保存”按钮，只保存偏线代理公司信息
     * ，添加的偏线代理公司对应网点在SUC-649-新增_修改_作废_查询偏线代理网点用例中执行保存操作； 3、
     * 只有保存偏线代理公司信息后，才能点击“添加网点”按钮为该公司添加偏线代理网点； SR-4
     * 只有当偏线代理公司下无偏线代理网点时，才允许作废该偏线代理公司；
     * 
     * @param agencyCode
     *            代理编码
     * @param agencyType
     *            代理类别：DictionaryValueConstants.AGENCY_TYPE_COM(代理公司)
     *            DictionaryValueConstants.AGENCY_TYPE_BRANCH（代理网点）
     * @return
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-20 下午4:28:43
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#queryAgencyIsExistByCodeAndType(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public boolean queryAgencyIsExistByCodeAndType(String agencyCode,
	    String agencyType) {
	// 参数验证
	if (StringUtil.isBlank(agencyCode)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理编码不允许为空！");
	    // 参数验证
	} else if (StringUtil.isBlank(agencyCode)) {
	    // 异常信息处理
	    throw new VehicleAgencyCompanyException("代理类型不允许为空！");
	} else {
	    // 参数验证
	    if (StringUtil.equals(DictionaryValueConstants.AGENCY_TYPE_COM,
		    agencyType)) {
		// 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在)
		BusinessPartnerEntity entity = vehicleAgencyCompanyDao
			.queryEntityByCode(agencyCode, null);
		// 返回值
		return null != entity;
		// 参数验证
	    } else if (StringUtil.equals(
		    DictionaryValueConstants.AGENCY_TYPE_BRANCH, agencyType)) {
		// 根据外部网点、网点类别查询外部网点是否存在
		OuterBranchEntity entity = vehicleAgencyDeptService
			.queryOuterBranchByBranchCode(agencyCode, null);
		// 返回值
		return null != entity;
	    } else {
		return false;
	    }
	}
    }

    /**
     * 
     * <p>
     * 通过代理公司网点编码查询代理公司编码
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 28, 2013 3:05:38 PM
     * @param siteCode
     * @return
     * @see
     */
    @Override
    public String queryCompanyCodeBySiteCode(String siteCode) {
	// 检查参数
	if (StringUtils.isBlank(siteCode)) {
	    return null;
	}

	BusinessPartnerEntity company = airAgencyCompanyDao
		.queryBusinessPartnerByAgencyDeptCode(siteCode);

	// // 查询网点
	// OuterBranchEntity branch =
	// vehicleAgencyDeptService.queryOuterBranchByBranchCode(siteCode,
	// null);
	// if (branch == null) {
	// return null;
	// }
	// // 查询所属公司
	// BusinessPartnerEntity company =
	// queryEntityByVirtualCode(branch.getAgentCompany());
	return company == null ? null : company.getAgentCompanyCode();
    }

    /**
     * <p>
     * 根据传入条件 导出查询结果
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-10 下午4:43:38
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService#exportVehicleAgencyComtList(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)
     */
    @Override
    public ExportResource exportVehicleAgencyComtList(
	    BusinessPartnerEntity entity) {
	// 参数验证
	if (null == entity) {
	    // 返回空值
	    return null;
	}
	// 设置有效
	entity.setActive(FossConstants.ACTIVE);
	// 根据传入对象查询符合条件所有偏线代理公司信息.
	List<BusinessPartnerEntity> list = queryVehicleAgencyCompanys(entity,
		Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
	// 集合验证
	if (null == list) {
	    // 定义一个集合
	    list = new ArrayList<BusinessPartnerEntity>();
	}
	// 定义集合
	List<List<String>> resultList = new ArrayList<List<String>>();
	// 迭代循环
	for (BusinessPartnerEntity outerBranch : list) {
	    // 实体信息封装到集合中
	    List<String> result = exportInfoList(outerBranch);
	    // 存放到集合
	    resultList.add(result);
	}
	// 导出对象创建
	ExportResource sheet = new ExportResource();
	// 设置Excel表头
	sheet.setHeads(ComnConst.VEHICLE_AGENCY_COM_TITLE);
	// 设置导出数据
	sheet.setRowList(resultList);
	return sheet;
    }

    /**
     * <p>
     * 导出信息封装
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-7-5 下午5:11:29
     * @param entity
     * @return
     * @see
     */
    private List<String> exportInfoList(BusinessPartnerEntity entity) {
	// 定义一个集合存放信息
	List<String> result = new ArrayList<String>();
	// 代理公司编码.
	result.add(entity.getAgentCompanyCode());
	// 管理部门编码
	result.add(entity.getManagement());
	// 管理部门名称.
	result.add(entity.getManagementName());
	// 代理公司名称.
	result.add(entity.getAgentCompanyName());
	// 代理简称.
	result.add(entity.getSimplename());
	// 省份编码.
	result.add(entity.getProvCode());
	// 省份名称.
	result.add(entity.getProvName());
	// 城市编码.
	result.add(entity.getCityCode());
	// 城市名称.
	result.add(entity.getCityName());
	// 联系地址.
	result.add(entity.getContactAddress());
	// 联系电话.
	result.add(entity.getContactPhone());
	// 联系人.
	result.add(entity.getContact());
	// 联系人电话.
	result.add(entity.getMobilePhone());
	// 备注.
	result.add(entity.getNotes());
	
	// 返回集合
	return result;
    }

}
