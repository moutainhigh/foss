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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/VehicleAgencyDeptService.java
 * 
 * FILE NAME        	: VehicleAgencyDeptService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 偏线代理维护员或系统管理员使用该用例新增、修改、作废、查询偏线代理网点信息
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	行政区域基础资料完备；
        2、	偏线代理公司基础资料完备；	SUC-33新增_修改_作废_查询行政区域
        SUC-754新增_修改_作废_查询偏线代理公司
        后置条件	1、	录入运单中的提货网点时能正常获取所需的偏线代理网点；	SUC-496录入运输信息
        1.4	操作用户角色
        操作用户	描述
        系统管理员	系统管理员负责对偏线代理网点基础资料进行新增、修改、作废、查询操作
        偏线代理维护员	偏线代理维护员负责对偏线代理网点基础资料进行新增、修改、作废、查询操作
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-偏线代理网点查询界面
         
        图 1：偏线代理网点查询界面
        1.5.3	界面描述-偏线代理网点查询界面
        该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
        1.	查询条件区域：查询条件包括偏线代理网点编码、偏线代理网点、代理公司、省份、增值业务、管理部门；
        1.1	所属偏线代理公司：共用选择框，内容读取偏线代理公司基础资料；
        1.2	城市：共用选择框，读取行政区域基础资料中的城市信息；
        1.3	增值服务：下拉框，包括全部、返回签单、货到付款、代收货款；
        1.4	承运业务：下拉框，包括全部、出发、到达、中转；
        1.5	管理部门：新增偏线代理网点时的管理部门；共用选择框，读取行政组织；
        2.	查询结果列表：数据元素参见【偏线代理网点查询结果列表】，“偏线代理网点编码”字段添加超链接，点击超链可打开偏线代理网点详情界面；
        3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
        	新增：此按钮位于系统悬浮工具条中，点击可打开偏线代理网点新增界面(图2)；
        	修改：图标按钮，位于【偏线代理网点查询结果列表】最前面的操作列，点击可打开偏线代理网点修改界面(图2)；
        	作废：【偏线代理网点查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的偏线代理网点信息；另外存在“作废”的图标按钮，位于【偏线代理网点查询结果列表】最前面的操作列，点击可作废该行偏线代理网点；
        	查询：点击此按钮查询符合条件的偏线代理网点；
        	重置：重新初始化【偏线代理网点查询条件】；
        4.	提供的相关用例链接或提示信息：作废偏线代理网点成功后，提示操作成功，同时刷新【偏线代理网点查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。
        1.5.4	界面原型-偏线代理网点新增、修改界面
         
        图 2：偏线代理网点新增、修改界面
        1.5.5	界面描述-偏线代理网点新增、修改界面
        该界面分为两个部分：偏线代理网点信息输入区域，功能按钮区；
        1.	偏线代理网点信息输入区域：字段包括偏线代理网点编码、偏线代理网点名称、代理公司、省份、城市、区/县、偏线代理网点地址、联系电话、备注、标准服务、增值服务、到达限制、自提区域描述、派送区域描述；
        1.1	偏线代理网点编码：可作为偏线代理网点的唯一标识，不可重复，参见业务规则SR-1；
        1.2	偏线代理网点名称：不可重复，参见业务规则SR-1；
        1.3	管理部门：共用选择框，读取行政组织，参见业务规则SR-2；
        1.4	代理公司：该偏线代理网点所属的代理公司，共用选择框；
        1.5	省份、城市、区/县：共用选择框，读取行政区域基础资料，参见业务规则SR-2；
        1.6	标准服务：包括自提、送货上门复选框，参见业务规则SR-3；
        1.7	增值服务：包括返回签单、代收货款、货到付款复选框，参见业务规则SR-3；
        1.8	承运业务：包括出发、到达、中转复选框，参见业务规则SR-3；
        2.	功能按钮区：按钮包括保存、取消、重置；
        	保存：点击此按钮保存偏线代理网点信息；
        	取消：退出当前界面；
        	重置：点击此按钮重新初始化输入区域各控件；
        3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。
        1.6	操作步骤
        1.6.1	查询偏线代理网点
        序号	基本步骤	相关数据	补充步骤
        1	进入偏线代理网点查询界面		
        2	输入查询条件，点击“查询”按钮	【偏线代理网点查询条件】
        【偏线代理网点查询结果列表】	后台执行查询，并把查询结果返回到前台
        
        1.6.2	新增偏线代理网点
        序号	基本步骤	相关数据	补充步骤
        1	点击悬浮工具条中的“新增”按钮		打开新增界面(图2)
        2	输入偏线代理网点信息，点击“保存”按钮		校验通过后，保存偏线代理网点信息
        
        序号	扩展事件	相关数据	备注
        2a	步骤2中，点击“保存”按钮之前，点击“重置”按钮		重新初始化各控件的值
        2b	步骤2中，点击“取消”按钮		退出当前界面
        
        1.6.3	修改偏线代理网点
        序号	基本步骤	相关数据	补充步骤
        1	点击查询结果列表中的操作列中的“修改”按钮		打开偏线代理网点修改界面
        2	输入【偏线代理网点信息】，点击修改界面的“保存”按钮		校验通过后，更新偏线代理网点信息
        
        序号	扩展事件	相关数据	备注
        2a	步骤2中，点击“保存”按钮之前，点击“重置”按钮		重新填充各控件的值为修改前的值
        2b	步骤2中，点击“取消”按钮		退出当前界面
        
        1.6.4	作废偏线代理网点
        序号	基本步骤	相关数据	补充步骤
        1	点击查询结果列表中的操作列中的“作废”按钮		校验是否可作废，若可作废则弹出“确认/取消”的对话框，内容为“您确定要作废该偏线代理网点信息吗？”
        2	在弹出的“确认/取消”对话框中，点击“确认”按钮		更新该偏线代理网点信息状态为“已作废”，同时刷新【偏线代理网点查询结果列表】
        
        序号	扩展事件	相关数据	备注
        1a	除步骤1的操作方式外，还可以勾选列表中的记录，点击列表顶部或下部的“作废”按钮	【偏线代理网点查询结果列表】	
        2a	步骤2中，点击“取消”按钮，则回到查询界面		
        
        1.7	业务规则
        序号	描述
        SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
        
        1.8	数据元素
        1.8.1	偏线代理网点查询条件
        字段名称 	说明	输入限制	输入提示文本	长度	是否必填	备注
        偏线代理网点编码	系统自动生成的偏线代理网点编码	数字		7	否	
        偏线代理网点	偏线代理网点的名称	文本		40	否	
        所属偏线代理公司	偏线代理网点所属的代理公司	共用选择框		40	否	读取代理公司基础资料
        城市	偏线代理网点所在城市	共用选择框		20	否	读取行政区域中的城市信息
        增值服务	偏线代理网点所能提供的增值服务	下拉框		1	否	包括全部、返回签单、代收货款、货到付款
        承运业务	偏线代理网点承运的业务类型	下拉框				包括全部、出发、到达、中转
        管理部门	新增偏线代理网点时的管理部门	共用选择框				读取行政组织
        1.8.1	偏线代理网点查询结果列表
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        偏线代理网点编码	系统自动生成的偏线代理网点编码	N/A	N/A	N/A	N/A	
        偏线代理网点	偏线代理网点的名称	N/A	N/A	N/A	N/A	
        所属偏线代理公司	偏线代理网点所属的代理公司	N/A	N/A	N/A	N/A	
        管理部门	新增偏线代理网点时的管理部门	N/A	N/A	N/A	N/A	
        省份	偏线代理网点所在省份	N/A	N/A	N/A	N/A	
        城市	偏线代理网点所在城市	N/A	N/A	N/A	N/A	
        区/县	偏线代理网点所在区县	N/A	N/A	N/A	N/A	
        
        1.8.2	偏线代理网点信息
        字段名称 	说明 	输入限制	输入提示文本	长度	是否必填	备注
        偏线代理网点编码	系统自动生成的偏线代理网点编码	N/A		N/A	N/A	
        偏线代理网点名称	偏线代理网点的名称	文本		40	是	
        偏线代理公司	偏线代理网点所属的偏线代理公司	共用选择框		N/A	是	读取偏线代理
        管理部门	负责管理此代理的部门	共用选择框		N/A	N/A	读取行政组织
        省份	偏线代理网点所在省份	共用选择框		N/A	是	读取行政区域中的省份信息
        城市	偏线代理网点所在城市	共用选择框		N/A	是	参见业务规则SR-2
        区/县	偏线代理网点所在区县	共用选择框		N/A	是	参见业务规则SR-2
        联系电话	偏线代理网点的联系电话	文本		80	是	
        备注	备注信息	文本		250	否	
        偏线代理网点地址	偏线代理网点地址	文本		150	是	
        标准服务	自提、送货上门	复选框		N/A	是	
        增值服务	返回签单、代收货款、货到付款	复选框		N/A	是	
        承运业务	出发、到达、中转	复选框		N/A	是	
        自提区域描述	对自提区域的描述	文本		800	是	
        派送区域描述	对派送区域的描述	文本		800	是	


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
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncPartialLineNetworkToCCService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendOutBranchInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.VehicleAgencyBranchException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 偏线代理网点service 接口实现：对偏线代理网点信息的增删改查的基本操作
 * 
 *  SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
    SR-2	新增或修改偏线代理网点时：
    1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
    2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
    3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
    SR-3	新增或修改偏线代理网点时：
    1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
    2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
    	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
    SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；

 * <p style="display:none">
 * 
 * modifyRecord
 * 
 * </p>
 * 
 * <p style="display:none">
 * 
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-15
 * 
 * 下午2:22:04
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * 
 * @date 2012-10-15 下午2:22:04
 * 
 * @since
 * @version
 */
public class VehicleAgencyDeptService implements IVehicleAgencyDeptService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleAgencyDeptService.class);

    /**
     * 偏线代理网点DAO接口.
     */
    @Inject
    private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
    
    /**
     * 偏线代理公司service接口.
     */
    @Inject
    private IVehicleAgencyCompanyService vehicleAgencyCompanyService;
    
    /**
     * 同步代理网点信息service接口.
     */
    private ISendOutBranchInfoService sendOutBranchInfoService;
    
    /**
     * 组织信息 Service接口.
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;

    /**
     * 行政区域接口.
     */
    @Inject
    private IAdministrativeRegionsService administrativeRegionsService;
    /**
	 * 向CC同步信息Service
	 */
	private ISyncPartialLineNetworkToCCService syncPartialLineToCCService;

    public void setSyncPartialLineToCCService(
			ISyncPartialLineNetworkToCCService syncPartialLineToCCService) {
		this.syncPartialLineToCCService = syncPartialLineToCCService;
	}

	/**
     * 设置 偏线代理网点DAO接口.
     *
     * @param vehicleAgencyDeptDao the new 偏线代理网点DAO接口
     */
    public void setVehicleAgencyDeptDao(
	    IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
	this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
    }

    /**
     * 设置 行政区域接口.
     *
     * @param administrativeRegionsService the new 行政区域接口
     */
    public void setAdministrativeRegionsService(
	    IAdministrativeRegionsService administrativeRegionsService) {
	this.administrativeRegionsService = administrativeRegionsService;
    }

    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
	this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    
    /**
     * 设置 偏线代理公司service接口.
     *
     * @param vehicleAgencyCompanyService the new 偏线代理公司service接口
     */
    public void setVehicleAgencyCompanyService(IVehicleAgencyCompanyService vehicleAgencyCompanyService) {
        this.vehicleAgencyCompanyService = vehicleAgencyCompanyService;
    }

    /**
     * 设置 同步代理网点service接口.
     *
     * @param sendOutBranchInfoToWDGHService the new 同步代理网点service接口
     */
	public void setSendOutBranchInfoService(
			ISendOutBranchInfoService sendOutBranchInfoService) {
		this.sendOutBranchInfoService = sendOutBranchInfoService;
	}
	
    /**
     * 新增偏线代理网点.
     *
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
        
     * @param entity 空运/偏线代理网点实体
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws VehicleAgencyBranchException 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:23:49
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#addVehicleAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    //@Transactional
    @Override
    public int addVehicleAgencyDept(OuterBranchEntity entity)
	    throws VehicleAgencyBranchException {
	//创建日期
        Date date = new Date();
        //参数验证
	if (null == entity) {
	    //返回值
	    return FossConstants.FAILURE;
	}

	// 记录ID有工具类生成ＵＵＩＤ
	entity.setId(UUIDUtils.getUUID());
	//设置创建日期
	entity.setCreateDate(date);
	//设置版本号
	entity.setVersionNo(date.getTime());
	//设置修改日期
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	// 第一次记录新增时，虚拟编码为记录的id
	entity.setVirtualCode(entity.getId());
	// 设置新增记录默认生效
	entity.setActive(FossConstants.ACTIVE);
	// 设置类型为偏线代理
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	
	//新增偏线代理网点
	int ret=vehicleAgencyDeptDao.addVehicleAgencyDept(entity);
	
	//同步偏线代理网点到WDGH及OMS
	syncOuterBranchInfoToWdgh(entity,NumberConstants.ONE);
	
	//////////2014.6.27 新增偏线代理网点成功后同步到CC
	if (ret>0){
		List<OuterBranchEntity> list=new ArrayList<OuterBranchEntity>();
		list.add(entity);
		syncToCC(list);
	}
		return ret;
    }
    
    /**
     *<p>同步给网点规划</p>
     *@author 269231 -qirongsheng
     *@date 2016-3-30 上午10:21:32
     *@param outerBranchEntity
     *@param type
     */
    private void syncOuterBranchInfoToWdgh(OuterBranchEntity outerBranchEntity,
			Integer type) {	
    	
    	if(null !=outerBranchEntity){
        	//同步接口
        	sendOutBranchInfoService.syncOutBranchInfoToWDGH(outerBranchEntity, type.toString());
    	}
	}
    
    /**
     * 同步偏线网点失败后 重新发送请求
     * @param list
     * @return
     */
    public int syncToCCFail(List<String> list){
    	if(list!=null&&list.size()>0){
    		List<OuterBranchEntity> olist=new ArrayList<OuterBranchEntity>();
    		for(String code:list){
    			OuterBranchEntity entity=queryOuterBranchByBranchCode(code, "PX");
    			if(entity!=null){
    				olist.add(entity);
    			}
    		}
    		if(olist.size()>0){
    			syncToCC(olist);
    		}
    	}
    	return 1;
    }
    /**
     * 根据code作废偏线代理网点.
     *  SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param codeStr 偏线代理网点虚拟编码拼接字符串
     * 
     * @param modifyUser 
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws VehicleAgencyBranchException 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:23:54
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#deleteVehicleAgencyDeptByCode(java.lang.String[])
     */
    //@Transactional
    @Override
    public int deleteVehicleAgencyDeptByCode(String codeStr, String modifyUser)
	    throws VehicleAgencyBranchException {
	//参数验证
	if (StringUtil.isBlank(codeStr)) {
	    //返回失败
	    return FossConstants.FAILURE;
	} 
	//日志记录
	LOGGER.debug("codeStr: " + codeStr);
	//字符串拆分
	String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
//	return vehicleAgencyDeptDao.deleteVehicleAgencyDeptByCode(codes,modifyUser);
	List<OuterBranchEntity> outlist=vehicleAgencyDeptDao.queryOuterBranchsByVirtualCode(codes);
	//根据code作废偏线代理网点
		int issuc=vehicleAgencyDeptDao.deleteVehicleAgencyDeptByCode(codes,modifyUser);
		Date date = new Date();
		if(issuc>0){
			List<OuterBranchEntity> outlist2=new ArrayList<OuterBranchEntity>();
			for(OuterBranchEntity entity:outlist){
				entity.setActive(FossConstants.INACTIVE);
				outlist2.add(entity);
				
				entity.setModifyUser(modifyUser);
				entity.setVersionNo(date.getTime());
				entity.setModifyDate(date);
				
				//同步作废代理网点到WDGH
				syncOuterBranchInfoToWdgh(entity,NumberConstants.THREE);
			}
			syncToCC(outlist2);
		}
		return issuc;
    }

    /**
     * 修改偏线代理网点 对于有版本控制的编辑，先逻辑删除该记录，再新增一条记录.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param entity 空运/偏线代理网点实体
     * 
     * @return 1：成功；-1：失败
     * 
     * @throws VehicleAgencyBranchException 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:24:00
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#updateVehicleAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Transactional
    @Override
    public int updateVehicleAgencyDept(OuterBranchEntity entity)
	    throws VehicleAgencyBranchException {
	//参数判断
	if (null == entity||StringUtil.isBlank(entity.getVirtualCode())) {
	    //返回失败
	    return FossConstants.FAILURE;
	    //参数验证
	} /*else if (StringUtil.isBlank(entity.getVirtualCode())) {
	  //返回失败
	    return FossConstants.FAILURE;
	}*/
	// 定义一个虚拟编码code
	String[] codes = { entity.getVirtualCode() };
	// 作废历史记录
	int flag = vehicleAgencyDeptDao.deleteVehicleAgencyDeptByCode(codes,entity.getModifyUser());
	//判断作废是否成功
	if (flag > 0) {
	   // 设置 是否启用.
	    entity.setActive(FossConstants.ACTIVE);
	    //设置ID
	    entity.setId(UUIDUtils.getUUID());
	    //设置创建日期
	    entity.setCreateDate(new Date());
	    //设置版本号
	    entity.setVersionNo(entity.getCreateDate().getTime());
	    //设置修改日期
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    // 设置类型为偏线代理
	    entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	    //新增偏线代理网点
//	    return vehicleAgencyDeptDao.addVehicleAgencyDept(entity);
	  //新增偏线代理网点
	    int issuc=vehicleAgencyDeptDao.addVehicleAgencyDept(entity);
	    
	    //同步更新代理网点到WDGH及OMS
	    syncOuterBranchInfoToWdgh(entity, NumberConstants.TWO);
	    
	    if(issuc>0){
	    	List<OuterBranchEntity> list=new ArrayList<OuterBranchEntity>();
			list.add(entity);
			syncToCC(list);
	    }
	    return issuc;
	}
	return FossConstants.FAILURE;
    }
    /**
	 *<p>Title: syncToCC</p>
	 *<p>向CC同步数据</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-14上午10:06:04
	 * @param dto
	 */
	private boolean syncToCC(List<OuterBranchEntity> list) {
		//校验非空
		if(null ==list||list.size()<=0){
			return false;
		}
		boolean result =false;
		try {
			result =syncPartialLineToCCService.syncPartialLine(list);
		} catch(VehicleAgencyBranchException e){
			result =false;
			throw new VehicleAgencyBranchException("同步偏线网点失败！"+e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			result =false;
			 throw new VehicleAgencyBranchException("同步偏线网点失败！"+e.getMessage());
		}
		return result;
	}
    /**
     * 根据传入对象查询符合条件所有偏线代理网点信息.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param entity 空运/偏线代理网点实体
     * 
     * @param limit 每页最大显示记录数
     * 
     * @param start 开始记录数
     * 
     * @return 符合条件的实体列表
     * 
     * @throws VehicleAgencyBranchException 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:24:06
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryVehicleAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity,
     * int, int)
     */
    @Transactional
    @Override
    public List<OuterBranchEntity> queryVehicleAgencyDepts(
	    OuterBranchEntity entity, int limit, int start)
	    throws VehicleAgencyBranchException {
	//设置 是否启用.
	entity.setActive(FossConstants.ACTIVE);
	//设置 网点类型：空运代理：KY 偏线代理：PX.
	entity.setBranchtype(DictionaryValueConstants.OUTERBRANCH_TYPE_PX);
	//批量填充行政区域名称、管理部门名称、代理公司名称 
	return convertOuterBranchList(vehicleAgencyDeptDao.queryVehicleAgencyDepts(entity, limit, start));
    }

    /**
     * <p>
     * 根据代理公司虚拟编码查询该公司的所有代理网点
     * </p>.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param comVirtualCode 代理公司的虚拟编码
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-30 下午1:56:35
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryOuterBranchsByComCode(java.lang.String)
     */
    @Transactional
    @Override
    public List<OuterBranchEntity> queryOuterBranchsByComCode(
	    String comVirtualCode) {
	//参数验证
	if (StringUtil.isBlank(comVirtualCode)) {
	    //异常信息处理
	    throw new VehicleAgencyBranchException("代理公司虚拟编码不允许为空！");
	}
	//批量填充行政区域名称、管理部门名称、代理公司名称 
	return convertOuterBranchList(vehicleAgencyDeptDao.queryOuterBranchsByComCode(comVirtualCode));
    }
    

    /**
     * <p>
     * 根据代理公司编码查询该代理公司的所有代理网点
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-5-13 下午4:10:27
     * @param comCode
     *            代理公司编码
     * @return
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryOuterBrangchsByCode(java.lang.String)
     */
    @Override
    public List<OuterBranchEntity> queryOuterBrangchsByCode(String comCode) {
	//参数验证
	if (StringUtil.isBlank(comCode)) {
	    // 异常信息处理
	    throw new VehicleAgencyBranchException("代理公司编码不允许为空！");
	}

	return convertOuterBranchList(vehicleAgencyDeptDao.queryOuterBrangchsByCode(comCode,DictionaryValueConstants.OUTERBRANCH_TYPE_PX));
    }


    /**
     * 统计总记录数.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param entity 空运/偏线代理网点实体
     * 
     * @return 
     * 
     * @throws VehicleAgencyBranchException 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:24:13
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public Long queryRecordCount(OuterBranchEntity entity)
	    throws VehicleAgencyBranchException {
	//设置状态
	entity.setActive(FossConstants.ACTIVE);
	//统计总记录数 
	return vehicleAgencyDeptDao.queryRecordCount(entity);
    }

    /**
     * 对外接口实现 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param agencyBranchCode 代理网点编码
     * 
     * @return AgencyBranchOrCompanyDto
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-24 上午9:21:53
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryAgencyBranchCompanyInfos(java.lang.String)
     */
    @Override
    public AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(
	    String agencyBranchCode) {
	//参数验证
	if (StringUtil.isBlank(agencyBranchCode)) {
	    //异常信息处理
	    throw new VehicleAgencyBranchException(VehicleAgencyBranchException.AGENCYBRANCHCODE_NULL_ERROR_CODE);
	}
	//根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息
	return vehicleAgencyDeptDao.queryAgencyBranchCompanyInfo(agencyBranchCode);
    }

    /**
     * 对外接口实现 根据传入参数查询代理网点（空运代理网点和偏线代理网点）.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、是否可自提、是否送货上门、用于版本控制时间）
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-11-1 下午3:43:18
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryOuterBranchs(com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto)
     */
    @Override
    public List<OuterBranchEntity> queryOuterBranchs(OuterBranchParamsDto dto) {
	//参数判断
	if (null == dto) {
	    //返回空值
	    return null;
	}
	//根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
	List<OuterBranchEntity> list = vehicleAgencyDeptDao.queryOuterBranchs(dto);
	//批量填充行政区域名称、管理部门名称、代理公司名称 
	return convertOuterBranchList(list);
    }

    /**
     * <p>
     * 填充行政区域名称、管理部门名称、代理公司名称
     * </p>.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
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
    private OuterBranchEntity convertOuterBranch(OuterBranchEntity entity) {
	//参数验证
	if (null == entity) {
	    //返回空值
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(entity.getManagement());
	    //偏线代理公司service接口.
	    BusinessPartnerEntity busiPartner = vehicleAgencyCompanyService.queryEntityByVirtualCode(entity.getAgentCompany());
	    //结果验证
	    if(null != org){
		//设置管理部门名称
		entity.setManagementName(org.getName());
	    }
	    //结果验证
	    if(null != busiPartner){
		//设置代理公司名称
		entity.setAgentCompanyName(busiPartner.getAgentCompanyName());
	    }
	    // 设置国家名称
	    entity.setCountryRegionName(administrativeRegionsService.queryAdministrativeRegionsNameByCode(entity.getCountryRegion()));
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
     * 批量填充行政区域名称、管理部门名称、代理公司名称
     * </p>.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
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
    private List<OuterBranchEntity> convertOuterBranchList(List<OuterBranchEntity> list) {
	//定义一个集合
	List<OuterBranchEntity> entities = new ArrayList<OuterBranchEntity>();
	//不为空验证
	if (CollectionUtils.isNotEmpty(list)) {
	    //迭代循环
	    for (OuterBranchEntity entity : list) {
		//填充行政区域名称、管理部门名称、代理公司名称 
		entity = convertOuterBranch(entity);
		//放入集合
		entities.add(entity);
	    }
	    //返回集合
	    return entities;
	} else {
	    return null;
	}
    }
    
    /**
     * <p>根据外部网点、网点类别查询外部网点是否存在</p>.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param branchCode 外部网点编码
     * 
     * @param branchType 网点类型 :DictionaryValueConstants.OUTERBRANCH_TYPE_KY(空运)
     * 
     * DictionaryValueConstants.OUTERBRANCH_TYPE_PX(偏线)
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-12-20 下午5:52:06
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryOuterBranchByBranchCode(java.lang.String, java.lang.String)
     */
    @Override
    public OuterBranchEntity queryOuterBranchByBranchCode(String branchCode,
	    String branchType) {
	if(StringUtil.isBlank(branchCode)){
	    throw new VehicleAgencyBranchException("代理网点编码不允许为空！");
	}else {
	    return vehicleAgencyDeptDao.queryOuterBranchByBranchCode(branchCode, branchType);
	}
    }
    
    /**
     * <p>根据行政区域code查找该行政区域下面所有的代理网点信息</p>.
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * @param districtCode 
     * 
     * @param billDate 开单日期
     * 
     * @return 
     * 
     * @author zhangdongping
     * 
     * @date 2013-1-13 下午2:36:41
     * 
     * @see
     */
    @Override
    public List<OuterBranchEntity> queryOuterBranchsByDistrictCode(String districtCode,
	    Date billDate) {
	if(StringUtil.isEmpty(districtCode)){
	    return null;
	}
	if(null==billDate){
	    //根据行政区域code查找该行政区域下面所有的代理网点信息
	    return vehicleAgencyDeptDao.queryOuterBranchsByDistrictCode(districtCode,new Date());
	}else {
	    //根据行政区域code查找该行政区域下面所有的代理网点信息
	    return vehicleAgencyDeptDao.queryOuterBranchsByDistrictCode(districtCode,billDate);
	}
    }
    
    /**
     * 对外接口实现 根据传入参数查询代理网点（空运代理网点和偏线代理网点）. 的简要信息，不做任何包装。
     *	SR-1	“偏线代理网点编码”不可重复；“偏线代理网点名称”不可重复；
        SR-2	新增或修改偏线代理网点时：
        1、	新增、修改时，偏线代理维护员登录后，界面初始化时“管理部门”默认为当前用户所属部门，不可更改；系统管理员可修改“管理部门”；
        2、	必须先输入“省份”才可输入“城市”，并且输入“省份”后，“城市”自动过滤为该省份下辖城市；
        3、	必须先输入“城市”才可输入“区/县”，并且输入“城市”后，“区/县”自动过滤为该城市下辖区县；
        SR-3	新增或修改偏线代理网点时：
        1、	只有勾选“标准服务”中的“自提”后，“自提区域描述”方可输入；
        2、	只有勾选“标准服务”中的“送货上门”后，“派送区域描述”方可输入；
        	只有勾选“承运业务”中的“到达”后，方可勾选“标准服务”和“增值服务”；
        SR-4	只有新增偏线代理网点时的管理部门或者系统管理员才有权限修改、作废偏线代理网点；
     * 
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、是否可自提、是否送货上门、用于版本控制时间）
     * 
     * @return 
     * 
     * @author zhangdongping
     * 
     * @date 2012-11-1 下午3:43:18
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#queryOuterBranchs(com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto)
     */
    @Override
    public List<OuterBranchEntity> queryOuterBranchsSimpleInfo(OuterBranchParamsDto dto) {
	if (null == dto) {
	    return null;
	}
	//根据传入参数查询代理网点（空运代理网点和偏线代理网点）
	return vehicleAgencyDeptDao.queryOuterBranchs(dto);
    }
    
    /**
 	 * 根据历史时间和代理网点编码查询代理网点信息（查询历史代理网点信息）
 	 * 
 	 * 若时间为空，则只根据代理网点编码查询代理网点信息
 	 * 否则将根据时间，查询在creatTime和modifyTime时间段的代理网点
 	 * 不根据Active='Y'来查询
 	 * 
 	 * @author 026123-foss-lifengteng
 	 * @date 2013-4-17 下午6:02:26
 	 */
    @Override
     public OuterBranchEntity queryOuterBranchByCode(String code,Date billTime){
    	 return vehicleAgencyDeptDao.queryOuterBranchByCode(code, billTime);
     }
    
    /**
     * <p>根据传入条件 导出查询结果</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-5 下午4:58:57
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService#exportVehicleAgencyDeptList(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public ExportResource exportVehicleAgencyDeptList(OuterBranchEntity entity) {
	// 参数验证
	if (null == entity) {
	    // 返回空值
	    return null;
	}
	// 设置有效
	entity.setActive(FossConstants.ACTIVE);
	// 根据传入对象查询符合条件所有偏线代理网点信息.
	List<OuterBranchEntity> list = queryVehicleAgencyDepts(entity,
		Integer.MAX_VALUE, NumberConstants.NUMERAL_ZORE);
	// 集合验证
	if (null == list) {
	    // 定义一个集合
	    list = new ArrayList<OuterBranchEntity>();
	}
	// 定义集合
	List<List<String>> resultList = new ArrayList<List<String>>();
	// 迭代循环
	for (OuterBranchEntity outerBranch : list) {
	    // 实体信息封装到集合中
	    List<String> result = exportInfoList(outerBranch);
	    // 存放到集合
	    resultList.add(result);
	}
	// 导出对象创建
	ExportResource sheet = new ExportResource();
	// 设置Excel表头
	sheet.setHeads(ComnConst.VEHICLE_AGENCY_DEPT_TITLE);
	// 设置导出数据
	sheet.setRowList(resultList);
	return sheet;
    }
    
    /**
     * <p>导出信息封装</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-5 下午5:11:29
     * @param entity
     * @return
     * @see
     */
    private List<String> exportInfoList(OuterBranchEntity entity){
	//定义一个集合存放信息
	List<String> result = new ArrayList<String>();
	//代理网点编码.
	result.add(entity.getAgentDeptCode());
	//代理网点名称.
	result.add(entity.getAgentDeptName());
	//代理公司虚拟编码.
	result.add(entity.getAgentCompany());
	//代理公司名称.
	result.add(entity.getAgentCompanyName());
	// 管理部门编码.
	result.add(entity.getManagement());
	// 管理部门名称.
	result.add(entity.getManagementName());
	// 代理网点简称.
	result.add(entity.getSimplename());
	// 区县编码.
	result.add(entity.getCountyCode());
	// 区县名称.
	result.add(entity.getCountyName());
	// 提货网点编码.
	result.add(entity.getStationNumber());
	// 省份编码.
	result.add(entity.getProvCode());
	// 省份名称.
	result.add(entity.getProvName());
	// 城市编码.
	result.add(entity.getCityCode());
	// 城市名称.
	result.add(entity.getCityName());
	// 网点地址.
	result.add(entity.getAddress());
	// 是否可自提.
	result.add(entity.getPickupSelf());
	
	// 是否送货上门.
	result.add(entity.getPickupToDoor());
	// 是否支持返回签单.
	result.add(entity.getReturnBill());
	// 是否支持货到付款.
	result.add(entity.getArriveCharge());
	// 是否支持代收货款.
	result.add(entity.getHelpCharge());
	// 自提区域.
	result.add(entity.getPickupArea());
	// 派送区域.
	result.add(entity.getDeliverArea());
	// 可出发.
	result.add(entity.getLeave());
	// 可中转.
	result.add(entity.getTransfer());
	// 可到达.
	result.add(entity.getArrive());
	// 联系电话.
	result.add(entity.getContactPhone());
	// 联系人手机.
	result.add(entity.getMobilePhone());
	// 备注.
	result.add(entity.getNotes());
	// 拼音.
	result.add(entity.getPinyin());
	// 是否机场.
	result.add(entity.getIsAirport());
	
	// 返回集合
	return result;
    }
    
    /**
     * <p>根据区县代码查询出落地配网点坐标和服务范围坐标</p> 
     * @author foss-WeiXing
     * @date 2014-08-28 下午03:56:35
     * @param countyCode 区县代码
     * @return
     * @see
     */
    @Transactional
    @Override
    public List<OuterBranchEntity> queryServerCoordinatesByCountyCode(String countyCode) {
	//参数验证
	if (StringUtil.isBlank(countyCode)) {
	    //异常信息处理
	    throw new VehicleAgencyBranchException("区县编码不能为空！");
	}
	//批量填充行政区域名称、管理部门名称、代理公司名称 
	return convertOuterBranchList(vehicleAgencyDeptDao.queryServerCoordinatesByCountyCode(countyCode));
    }
    
    @Override
    public Map<String, String>  queryAgentNameMapsByAgentCode(List<String> orgs){
    	//根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息
    	return vehicleAgencyDeptDao.queryAgentNameMapsByAgentCode(orgs);
    }

}
