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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AirlinesAccountService.java
 * 
 * FILE NAME        	: AirlinesAccountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * BUC_FOSS_5.70.30_060制作航空正单
 * 
 * 
        1.2	用例描述
        航空公司账户系统用例用于管理航空公司账户基础资料。
        该用例可对航空公司账户基础资料进行新增、修改、作废、查询。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	航空公司基础资料完备
        2、	行政组织基础资料完备	SUC-42 新增_修改_作废_查询航空公司
        SUC-85 修改_查询行政组织业务属性
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供航空公司账户基础资料查询	SUC-247 录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司账户”进行新增，修改，作废，查询操作。
        1.5	界面要求
        1.5.1	表现方式
        Web页面
        1.5.2	界面原型-主界面
          
        图一：航空公司账户管理主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮，弹出新增界面，参见【图二：航空公司账户新增和修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，重置查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框。
        5)	修改按钮：点击各行的修改按钮，弹出修改界面，参见【图二：航空公司账户新增和修改界面】。
        6)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：航空公司、使用部门、接入账号、航空公司账号。
        3.	字段输入区域
        1)	录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。
        
        1.5.4	界面原型-新增和修改界面
         
        
        图二：航空公司账户新增和修改界面
        1.5.5	界面描述-新增和修改界面
        1.	字段输入区域
        1)	航空公司： 必填
        2)	使用部门：必填
        3)	接入账户：必填
        4)          接入密码：必填
        5)          确认接入密码：必填
        6)          航空公司账号：必填
        7)          航空公司校验码：必填
        8)          确认航空公司校验码：必填
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭弹出窗口，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭弹出窗口。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，关闭弹出窗口，返回主界面。
        1.6	操作步骤
        1.6.1	添加航空公司账户操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司账户管理主界面	【航空公司账户列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入航空公司账户详细信息，点击保存。
        参见业务规则SR-1、SR-2、SR-3	【航空公司账户信息】	
        4	返回航空公司账户管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改航空公司账户操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司账户管理主界面	【航空公司账户列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改航空公司账户详细信息,点击保存
        参见业务规则SR-1、SR-2、SR-3	【航空公司账户信息】	
        4	返回航空公司账户管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		
        
        1.6.3	作废航空公司账户操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司账户管理主界面	【航空公司账户列表信息】	
        2	选择一行或者多行记录，点击作废按钮		弹出确认对话框。
        3	点击确定按钮。		成功保存至系统。
        
        序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		
        
        1.6.4	查询航空公司账户操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司账户管理主界面	【航空公司账户列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-4	【航空公司账户查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	“接入密码”、“确认接入密码”和“航空公司校验码”、“确认航空公司校验码”以密码框形式存在，输入数字后以星号显示。
        SR-2	新增与编辑页面中，“航空公司”不支持手动输入，只支持从航空公司基础中手动选取；“使用部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取。
        SR-3	“航空公司”，“使用部门”组合必须唯一
        SR-4	查询模糊查询，若查询条件为空，默认查询所有的记录。
        	
        
        1.8	数据元素
        1.8.1	航空公司账户信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        航空公司	航空公司	选择框		20	是	
        使用部门	使用部门	选择框		20	是	
        接入账号	接入账号	文本		20	是	
        接入密码	接入密码	文本		20	是	
        确认接入密码	确认接入密码	文本		20	是	
        航空公司账号	航空公司账号	文本		20	是	
        航空公司校验码	航空公司校验码	文本		20	是	
        确认航空公司校验码	航空公司校验码	文本		20	是	
        						
        1.8.2	航空公司列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        航空公司	航空公司	N/A	20	N/A	
        使用部门	使用部门	N/A	20	N/A	
        接入账号	接入账号	N/A	20	N/A	
        航空公司账号	航空公司账号	N/A	20	N/A	
        1.8.3	航空公司账户查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        航空公司账号	航空公司账号	文本	20	否	
        航空公司	航空公司	文本	20	否	
        使用部门	使用部门	文本	20	否	
        
        1.9	非功能性需求
        使用量	
        2012年全网估计用户数	
        响应要求（如果与全系统要求 不一致的话）	
        使用时间段	
        高峰使用时间段	
        
        1.10	接口描述
        接口名称 	对方系统（外部系统或内部其他模块）	接口描述
		


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
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAccountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AirlinesAccountDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirlinesAccountException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirlinesAgentException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedDriverException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“航空公司账户”的数据库对应数据访问Service接口实现类：SUC-43 
 * 
 *  SR-1	“接入密码”、“确认接入密码”和“航空公司校验码”、“确认航空公司校验码”以密码框形式存在，输入数字后以星号显示。
    SR-2	新增与编辑页面中，“航空公司”不支持手动输入，只支持从航空公司基础中手动选取；“使用部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取。
    SR-3	“航空公司”，“使用部门”组合必须唯一
    SR-4	查询模糊查询，若查询条件为空，默认查询所有的记录。

 * 航空公司账户系统用例用于管理航空公司账户基础资料。
        该用例可对航空公司账户基础资料进行新增、修改、作废、查询。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	航空公司基础资料完备
        2、	行政组织基础资料完备	SUC-42 新增_修改_作废_查询航空公司
        SUC-85 修改_查询行政组织业务属性
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供航空公司账户基础资料查询	SUC-247 录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司账户”进行新增，修改，作废，查询操作。
 * 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-11-3 上午10:04:46</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-11-3 上午10:04:46
 * @since
 * @version
 */
public class AirlinesAccountService implements IAirlinesAccountService {
    
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesAccountService.class);

    /**
     * "航空公司账户"DAO.
     */
    private IAirlinesAccountDao airlinesAccountDao;
    
    /**
     * "组织结构"Service.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * "航空公司"Service.
     */
    private IAirlinesService airlinesService;

    /**
     * <p>新增一个“航空公司账户”实体入库（忽略实体中是否存在空值）</p>.
     *
     * @param airlinesAccount “航空公司账户”实体
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws AirlinesAccountException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:16
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService#addAirlinesAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addAirlinesAccount(AirlinesAccountEntity airlinesAccount,
	    String createUser, boolean ignoreNull) throws AirlinesAccountException {
	//航空公司账户验证
	if (null == airlinesAccount) {
	    throw new AirlinesAccountException("", "航空公司账户为空");
	}
	//航空公司验证
	if (StringUtils.isBlank(airlinesAccount.getAirlines())) {
	    throw new AirlinesAccountException("", "航空公司为空");
	}
	//使用部门验证
	if (StringUtils.isBlank(airlinesAccount.getOrgId())) {
	    throw new AirlinesAccountException("", "使用部门为空");
	}
	//创建人信息验证
	if (StringUtils.isBlank(createUser)) {
	    throw new AirlinesAccountException("", "创建人信息为空");
	}
	
	//验证数据
	AirlinesAccountEntity olaAirlinessAccount, tempAirlinessAccount = new AirlinesAccountEntity();
	//设置航空公司
	tempAirlinessAccount.setAirlines(airlinesAccount.getAirlines());
	//设置部门ＩＤ
	tempAirlinessAccount.setOrgId(airlinesAccount.getOrgId());
	//根据条件有选择的检索出符合条件的“航空公司账户”唯一的一个实体（条件做自动判断，只选择实体中非空值）
	olaAirlinessAccount = airlinesAccountDao.queryAirlinesAccountBySelective(tempAirlinessAccount);
	//航空公司账户验证
	if (null != olaAirlinessAccount) {
	    throw new AirlinesAccountException("", "航空公司账户已经存在");
	}else{
	    //设置创建人
	    airlinesAccount.setCreateUser(createUser);
	    if (ignoreNull) {
		//新增一个“航空公司账户”实体入库 （只选择实体中非空值）
		airlinesAccountDao.addAirlinesAccountBySelective(airlinesAccount);
	    }else{
		//新增一个“航空公司账户”实体入库（忽略实体中是否存在空值）
		airlinesAccountDao.addAirlinesAccount(airlinesAccount);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“航空公司账户”记录唯一标识作废（物理删除）一条“航空公司账户”记录</p>.
     *
     * @param ids 记录唯一标识集合
     * @param modifyUser 修改人
     * @return 1：成功；0：失败
     * @throws AirlinesAccountException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:10
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService#deleteAirlinesAccount(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public int deleteAirlinesAccount(List<String> ids, String modifyUser)
	    throws AirlinesAccountException {
	//修改人验证
	if (StringUtils.isBlank(modifyUser)) {
	    throw new AirlinesAccountException("", "修改人信息为空");
	}
	for (String id : ids) {
	    //ID验证
	    if (StringUtils.isBlank(id)) {
		throw new AirlinesAccountException("", "ID为空");
	    }else{
		if(LOGGER.isDebugEnabled()){
		    LOGGER.debug(" *******  ******* 调试：作废航空账户的ID检测通过 *******  ******* ");
		}
	    }
	    //验证数据
	    AirlinesAccountEntity olaAirlinessAccount, tempAirlinessAccount = new AirlinesAccountEntity();
	    //设置ＩＤ
	    tempAirlinessAccount.setId(id);
	    //根据条件有选择的检索出符合条件的“航空公司账户”唯一的一个实体（条件做自动判断，只选择实体中非空值）
	    olaAirlinessAccount = airlinesAccountDao.queryAirlinesAccountBySelective(tempAirlinessAccount);
	    //航空公司账户验证
	    if (null == olaAirlinessAccount) {
		throw new AirlinesAccountException("", "航空公司账户不存在");
	    }
	    //设置修改人
	    olaAirlinessAccount.setModifyUser(modifyUser);
	    //设置状态：无效
	    olaAirlinessAccount.setActive(FossConstants.INACTIVE);
	    //修改一个“航空公司账户”实体入库 （只选择实体中非空值）
	    airlinesAccountDao.updateAirlinesAccountBySelective(olaAirlinessAccount);
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>修改一个“航空公司账户”实体入库（忽略实体中是否存在空值）</p>.
     *
     * @param airlinesAccount “航空公司账户”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；0：失败
     * @throws AirlinesAccountException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService#updateAirlinesAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateAirlinesAccount(AirlinesAccountEntity airlinesAccount,
	    String modifyUser, boolean ignoreNull) throws AirlinesAccountException {
	//航空公司账户验证
	if (null == airlinesAccount) {
	    throw new AirlinesAccountException("", "航空公司账户为空");
	}
	//ＩＤ验证
	if (StringUtils.isBlank(airlinesAccount.getId())) {
	    throw new AirlinesAccountException("", "ID 为空");
	}
	//航空公司验证
	if (StringUtils.isBlank(airlinesAccount.getAirlines())) {
	    throw new AirlinesAccountException("", "航空公司为空");
	}
	//使用部门验证
	if (StringUtils.isBlank(airlinesAccount.getOrgId())) {
	    throw new AirlinesAccountException("", "使用部门为空");
	}
	//修改人信息验证
	if (StringUtils.isBlank(modifyUser)) {
	    throw new AirlinesAccountException("", "修改人信息为空");
	}
	
	//验证数据
	AirlinesAccountEntity oldAirlinessAccount, tempAirlinessAccount = new AirlinesAccountEntity();
	//设置航空公司
	tempAirlinessAccount.setAirlines(airlinesAccount.getAirlines());
	//设置使用部门
	tempAirlinessAccount.setOrgId(airlinesAccount.getOrgId());
	//根据条件有选择的检索出符合条件的“航空公司账户”唯一的一个实体（条件做自动判断，只选择实体中非空值）
	oldAirlinessAccount  = airlinesAccountDao.queryAirlinesAccountBySelective(tempAirlinessAccount);
	//航空公司账户验证
	if (null == oldAirlinessAccount) {
	    throw new AirlinesAccountException("", "航空公司账户不存在");
	}else{
	    //设置修改人
	    airlinesAccount.setModifyUser(modifyUser);
	    if (ignoreNull) {
		//修改一个“航空公司账户”实体入库 （只选择实体中非空值）
		airlinesAccountDao.updateAirlinesAccountBySelective(airlinesAccount);
	    }else{
		//修改一个“航空公司账户”实体入库（忽略实体中是否存在空值）
		airlinesAccountDao.updateAirlinesAccount(airlinesAccount);
	    }
	}
	return FossConstants.SUCCESS;
    }

    /**
     * <p>根据“航空公司账户”记录唯一标识查询出一条“航空公司账户”记录</p>.
     *
     * @param id 记录唯一标识
     * @return “航空公司账户”实体
     * @throws AirlinesAccountException 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-12 上午8:49:24
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService#queryAirlinesAccount(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public AirlinesAccountEntity queryAirlinesAccount(String id)
	    throws AirlinesAccountException {
	//ＩＤ验证
	if (StringUtils.isBlank(id)) {
	    throw new LeasedDriverException("", "ID 为空");
	}
	//创建一个航空公司账户对象
	AirlinesAccountEntity airlinesAccount = new AirlinesAccountEntity();
	//设置ＩＤ
	airlinesAccount.setId(id);
	//根据条件有选择的检索出符合条件的“航空公司账户”唯一的一个实体（条件做自动判断，只选择实体中非空值）
	return airlinesAccountDao.queryAirlinesAccountBySelective(airlinesAccount);
    }

    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司账户”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     *
     * @param airlinesAccount 
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 分页的Action和Service通讯封装对象
     * @throws AirlinesAccountException 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-2 下午5:36:39
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAccountService#queryAirlinesAccountListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAccountEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryAirlinesAccountListBySelectiveCondition(
	    AirlinesAccountEntity airlinesAccount, int offset, int limit)
	    throws AirlinesAccountException {
	//创建一个分页的Action和Service通讯封装对象
	PaginationDto paginationDto = new PaginationDto();
	//防御参数验证
	if (offset < 0 || limit < 0) {
	    return paginationDto;
	}
	if(null == airlinesAccount){
	    //创建航空公司账户对象
	    airlinesAccount = new AirlinesAccountEntity();
	}
	//声明一个存放航空公司账户实体的集合
	List<AirlinesAccountEntity> airlinesAccountList = null;
	//根据条件（分页模糊）有选择的检索出符合条件的“航空公司账户”实体列表（条件做自动判断，只选择实体中非空值）
	airlinesAccountList = airlinesAccountDao.queryAirlinesAccountListBySelectiveCondition(airlinesAccount, offset, limit);
	//集合不为空判断
	if (CollectionUtils.isNotEmpty(airlinesAccountList)) {
	    //定义一个集合
	    List<AirlinesAccountDto> airlinesAccountDtos = new ArrayList<AirlinesAccountDto>();
	    //循环迭代
	    for (AirlinesAccountEntity airlinesAccountEntity : airlinesAccountList) {
		//定义一个航空公司账户实体DTO
		AirlinesAccountDto airlinesAgentDto = new AirlinesAccountDto();
		try {
		    //复制数据
		    BeanUtils.copyProperties(airlinesAccountEntity,
			    airlinesAgentDto.getAirlinesAccount(), new String[] {"createDate", "modifyDate" });
		    //设置创建日期
		    airlinesAgentDto.getAirlinesAccount().setCreateDate(airlinesAccountEntity.getCreateDate());
		    //设置修改日期
		    airlinesAgentDto.getAirlinesAccount().setModifyDate(airlinesAccountEntity.getModifyDate());
		} catch (Exception e) {
		    throw new AirlinesAgentException("", "复制航空公司账户信息失败");
		}
		//使用部门走缓存
		OrgAdministrativeInfoEntity organization = null;
		//根据编码查询组织信息
		organization = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(airlinesAccountEntity.getOrgId());
		//查询结果验证
		if (null != organization) {
		    //设置使用部门名称
		    airlinesAgentDto.setUseDepartment(organization.getName());
		}
		//航空公司名称走缓存
		AirlinesEntity airlines = airlinesService.queryAirlineByCode(airlinesAccountEntity.getAirlines());
		if (null != airlines) {
		    //设置航空公司名称
		    airlinesAgentDto.setAirlinesName(airlines.getName());
		}
		//添加信息
		airlinesAccountDtos.add(airlinesAgentDto);
	    }
	    
	    //查询总记录数
	    Long totalCount = airlinesAccountDao.queryAirlinesAccountRecordCountBySelectiveCondition(airlinesAccount);
	    //设置查询结果集合
	    paginationDto.setPaginationDtos(airlinesAccountDtos);
	    //设置总记录数
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto; 
    }
    
    /**
     * 设置 "航空公司账户"DAO.
     *
     * @param airlinesAccountDao the airlinesAccountDao to set
     */
    public void setAirlinesAccountDao(IAirlinesAccountDao airlinesAccountDao) {
        this.airlinesAccountDao = airlinesAccountDao;
    }

    /**
     * 设置 "组织结构"Service.
     *
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    
    /**
     * 设置 "航空公司"Service.
     *
     * @param airlinesService the airlinesService to set
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
        this.airlinesService = airlinesService;
    }
}
