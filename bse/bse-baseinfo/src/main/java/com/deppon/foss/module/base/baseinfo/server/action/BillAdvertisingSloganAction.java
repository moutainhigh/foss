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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/BillAdvertisingSloganAction.java
 * 
 * FILE NAME        	: BillAdvertisingSloganAction.java
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
 */
/**
 * 
 * 该界面分为三个部分：
 * 
 *  查询条件区域、
 *  
 *  查询结果列表、
 *  
 *  功能按钮区； 1
 *  
 *  . 查询条件区域：
 *  
 *   查询条件包括广告语代码
 *   
 * 、广告语名称、
 * 
 * 所属子系统、
 * 
 * 子系统功能模块； 
 * 
 * 1.1 所属子系统
 * 
 * ：广 告语所属的子系统，
 * 
 * 下拉框，读取自数据字典；
 * 
 *  1.2 子系统功能模
 *  
 * 块：读取自数据字典，
 * 
 * “所 属子系统”下的子系统功能模块，
 * 
 * 选择“所属子系统”后，
 * 
 * 该控件自动过滤为所选子系统下的功能模块；
 * 
 *  2.1. 查询结果列表：
 *  
 * 数据元素参见【单据广告语查询结果列表】，
 * 
 * “广告语代码”字段添加超链接，
 * 
 * 点击超链可打开单据广告语详情界面；
 * 
 *  3.2. 功能按钮区：按
 *  
 * 钮包括新增、修改、作废 、查询、重置； 
 * 
 *  新增：此按钮位 于系统悬浮工具条中，
 * 
 * 点击可打开单据广告语新增界面(图2)； 
 * 
 *  修改：图标按钮
 * 
 * ，位于【单据广告语查询结果列表】
 * 
 * 最前面的操作列，点击可打开单据广告语修改界面(图2)；
 * 
 *   作废：【单据广 告语查询结果列表】顶部
 *  
 *  
 * 和下部各有一个“
 * 
 * 
 * 作废”按钮，点击可作废选中的单据广告语信息；
 * 
 * 另外存在“作废”的图标按钮，
 * 
 * 位于【单据广告语查询结果列表】最前面的操作列，
 * 
 * 点击可作废该行单据广告语；
 * 
 * 
 *  查询：点击此按钮 查询符合条件的单据广告 语； 
 * 
 *  重置：重新初始 化【单据广告语查询条件】；
 *  
 *  
 *  
 *   4.3. 提供的相关用例
 *   
 * 链接或提示信息：作废单据广告语成功后
 * 
 * ，提示操作成功，
 * 
 * 同时刷新【单据广告语查询结果列表】，
 * 
 * 停留在查询页面，可继续执行本用例；
 * 
 * 作废失败后，提示失败原因。 
 * 
 * 1.5.5
 * 界面描述-单据广告语新增、
 * 
 * 修改界面 该界面分为两个部分：
 * 
 * 单据广告语信息输入区域、
 * 
 * 部门广告语列表、功能按钮区； 
 * 
 * 1.
 * 单据广告语信息输入区域：
 * 
 * 字段包括所属子系统、
 * 
 * 子系统功能模块、
 * 
 * 广告语代码、广告语名称、
 * 
 * 广告语内容； 1.1
 * 
 * 所属子系统：广告语所属的子系统，
 * 
 * 下拉框，读取自数据字典； 1.2
 * 
 * 
 * 子系统功能模块：读取自数据字典，
 * 
 * “所属子系统”下的子系统功能模块，
 * 
 * 选择“所属子系统”后，
 * 
 * 该控件自动过滤为所选子系统下的功能模块；
 * 
 *  1.31.1
 * 广告语代码、广告语名称：
 * 
 * 参见业务规则SR-1； 2.
 * 
 * 
 * 部门广告语列表：列表形式，
 * 
 * 字段包括“适用部门”、“广告语内容”，
 * 
 * 最前面的操作列包含有“修改”、
 * 
 * 
 * “作废”的图标按钮，
 * 
 * 点击分别打开部门广告语修改界面(图3)、
 * 
 * 作废该行部门广告语； 3.
 * 
 * 
 * 功能按钮区：按钮包括保存、取消、
 * 
 * 重置、添加部门广告语、作废；  
 * 
 * 保存：点击此按钮保存单据广告语信息； 
 * 
 *  取消：退出当前界面； 
 *  
 * 重置：点击此按钮重新初始化输入区域各控件；
 * 
 *   添加部门广告语：点击此按钮，
 *  
 *  弹出新增部门广告语界面(图3)； 
 *  
 * 作废：点击该按钮，作废【部门广告语列表】
 * 
 * 中被勾选的列； 4.
 * 
 * 
 * 提供的相关用例链接或提示信息：
 * 
 * 保存成功后，提示操作成功，停留在当前界面
 * 
 * ，界面中各控件不可输入；
 * 
 * 保存失败后，提示失
 * 
 * 败原因。 1
 * 
 * .5.7
 * 界面描述-部门广告
 * 
 * 语新增、
 * 
 * 修改界面 本界面用于为某部门定制单据广告语，
 * 
 * 分为两个区域，字段输入区域，功能按钮区； 1、
 * 
 * 
 * 字段输入区域：输入字段包括适用部门、
 * 
 * 广告语内容：
 * 
 *  1.1 适用部门：共用选择框，
 *  
 *  读取行政组织，参见业务规则SR-2； 1.2
 *  
 *  
 * 广告语内容：部门对应的广告语内容；
 * 
 *  2、 功能按钮区：包括取消、重置、确定；
 *  
 *   2.1 取消：点击该按钮退出当前界面； 2.2
 *   
 *   
 * 重置：点击该按钮重新初始化界面字段； 2.3
 * 
 * 
 * 确定：点击该按钮将输入的【部门广告语信息】
 * 
 * 添加至单据广告语新增、修改界面(图2)的
 * 
 * 【部门广告语列表】中。 SR-1
 * 
 * “广告语代码”不可重复；“广告语名称”
 * 
 * 不可重复； SR-2 对于同一条单据广告语，
 * 
 * 每个部门在【部门广告语列表】中最多
 * 
 * 只能存在一条记录；
 * 
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BillAdvertisingSloganException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BillAdvertisingSloganForDepException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.BillSloganAndDeptVo;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;

/**
 * 单据广告action
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2012-12-14 21:10:10
 * @since
 * @version 0.01
 */
public class BillAdvertisingSloganAction extends AbstractAction {

    /**
     * 下面是声明的属性
     */
    private static final long serialVersionUID = -802246567875971335L;

    // 单据广告service接口
    private IBillAdvertisingSloganService billAdvertisingSloganService;

    // 单据广告部门service接口
    private IBillAdvertisingSloganForDepService billAdvertisingSloganForDepService;

    // 单据广告 使用VO
    private BillSloganAndDeptVo objectVo = new BillSloganAndDeptVo();

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(BillAdvertisingSloganAction.class);

    /**
     * <p>
     * 查询单据广告
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     */
    public String queryBillSloganEntityByEntity() {
	BillSloganEntity entityCondition = objectVo.getBillSloganEntity();
	// 返回的结果显示在表格中：
	objectVo.setBillSloganEntityList(billAdvertisingSloganService
		.queryBillAdvertisingSlogans(entityCondition, limit, start));
	totalCount = billAdvertisingSloganService
		.queryRecordCount(entityCondition);
	return returnSuccess();
    }

    /**
     * 作废单据广告
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     * @see
     */
    public String deleteBillSloganEntity() {
	try {
	    objectVo.setReturnInt(billAdvertisingSloganService
		    .deleteBillAdvertisingSloganByCode(objectVo.getCodeStr(),
			    FossUserContext.getCurrentInfo().getEmpCode()));
	    return returnSuccess();
	} catch (BillAdvertisingSloganException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改单据广告
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     * @see
     */
    public String updateBillSloganEntity() {
	try {
	    objectVo.setReturnInt(billAdvertisingSloganService
		    .updateBillAdvertisingSlogan(objectVo.getBillSloganEntity()));
	    return returnSuccess();
	} catch (BillAdvertisingSloganException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增单据广告
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     * @see
     */
    public String addBillSloganEntity() {
	try {
	    objectVo.setReturnInt(billAdvertisingSloganService
		    .addBillAdvertisingSlogan(objectVo.getBillSloganEntity()));
	    return returnSuccess();
	} catch (BillAdvertisingSloganException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 单据广告 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     */
    public String billSloganEntityExist() {
	// 单据广告 是否重复
	return returnSuccess();
    }

    /**
     * <p>
     * 查询部门单据广告
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     */
    public String queryBillAdvertisingSloganForDepts() {
	BillSloganAppOrgEntity entityCondition = objectVo
		.getBillSloganAppOrgEntity();
	// 返回的结果显示在表格中：
	objectVo.setBillSloganAppOrgEntityList(billAdvertisingSloganForDepService
		.queryBillAdvertisingSloganForDepts(entityCondition,
			Integer.MAX_VALUE, start));
	return returnSuccess();
    }

    /**
     * 作废部门单据广告
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     * @see
     */
    public String deleteBillSloganAppOrgEntity() {
	try {
	    objectVo.setReturnInt(billAdvertisingSloganForDepService
		    .deleteBillAdvertisingSloganForDeptByCode(objectVo
			    .getCodeStr(), FossUserContext.getCurrentInfo()
			    .getEmpCode()));
	    return returnSuccess();
	} catch (BillAdvertisingSloganForDepException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 修改部门单据广告
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     * @see
     */
    public String updateBillSloganAppOrgEntity() {
	try {
	    objectVo.setReturnInt(billAdvertisingSloganForDepService
		    .updateBillAdvertisingSloganForDept(objectVo
			    .getBillSloganAppOrgEntity()));
	    return returnSuccess();
	} catch (BillAdvertisingSloganForDepException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * 新增部门单据广告
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     * @see
     */
    public String addBillSloganAppOrgEntity() {
	try {
	    objectVo.setReturnInt(billAdvertisingSloganForDepService
		    .addBillAdvertisingSloganForDept(objectVo
			    .getBillSloganAppOrgEntity()));
	    return returnSuccess();
	} catch (BillAdvertisingSloganForDepException e) {
	    LOGGER.debug(e.getMessage(), e);
	    return returnError(e);
	}
    }

    /**
     * <p>
     * 部门单据广告 是否重复
     * </p>
     * 
     * @author 073586-FOSS-LIXUEXING
     * @date 2012-12-14 21:10:10
     * @return String
     */
    public String billSloganAppOrgEntityIsExist() {
	// 部门单据广告 是否重复
	return returnSuccess();
    }

    /*
     * =================================================================
     * 下面是get,set方法：
     */
    public BillSloganAndDeptVo getObjectVo() {
	return objectVo;
    }

    public void setObjectVo(BillSloganAndDeptVo objectVo) {
	this.objectVo = objectVo;
    }

    public void setBillAdvertisingSloganService(
	    IBillAdvertisingSloganService billAdvertisingSloganService) {
	this.billAdvertisingSloganService = billAdvertisingSloganService;
    }

    public void setBillAdvertisingSloganForDepService(
	    IBillAdvertisingSloganForDepService billAdvertisingSloganForDepService) {
	this.billAdvertisingSloganForDepService = billAdvertisingSloganForDepService;
    }

}
