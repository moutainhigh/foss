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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AirlinesAgentService.java
 * 
 * FILE NAME        	: AirlinesAgentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 
 * 该用例可对航空公司代理人基础资料进行新增、修改、作废、查询操作等，该基础资料主要记录航空公司代理人的基础信息。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	航空公司基础资料齐备
        2、	行政组织基础资料齐备	SUC-42 新增_修改_删除_查询航空公司
        SUC-85 修改_查询行政组织业务属性
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供航空公司代理人基础资料查询	SUC-247-录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司代理人”进行新增，修改，作废，查询操作。
        1.5	界面要求
        1.5.1	表现方式
        Web 页面
        1.5.2	界面原型-主界面
          图一：航空公司代理人主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮弹出新增界面，参见【图二：航空公司代理人新增和
        修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，重置查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框。
        5)	查看详细信息：鼠标移动到列表中某一条记录，双击该行记录，弹出一个窗口，可以查看该记录的详细信息。
        6)	修改按钮：点击各行的修改按钮，弹出修改界面，参见【图二：航空公司代理人新增和修改界面】
        7)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：配载部门、航空公司代码、航空公司名称、始发城市、代理人编码、代理人名称。
        3.	字段输入区域
        1)	录入查询条件，点击查询按钮后在列表区按代理人代码顺序显示满足条件的查询结果。
        1.5.4	界面原型-新增和修改界面
         
        
        图二：航空公司代理人新增和修改界面
        1.5.5	界面描述-新增和修改界面
        1.	字段输入区域
        1)	配载部门： 必填
        2)	始发城市：与“配载部门”联动，只读
        3)	航空公司代码：必填
        4)          航空公司名称：与“航空公司代码”联动，只读
        5)          代理人编码：必填
        6)          代理人名称：必填
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，需要提示用户是否保存成功，若保存成功，关闭当前界面，返回主界面；若保存失败，提示用户保存失败以及失败原因，不关闭当前界面。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
        1.6	操作步骤
        1.6.1	添加航空公司代理人操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	点击新增按钮，弹出新增和修改界面		
        3	输入航空公司代理人详细信息，点击保存。
        参见业务规则SR-1、SR-2、SR-3	【航空公司代理人新增/修改信息】	
        4	返回航空公司代理人管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改航空公司代理人操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	点击修改按钮，弹出新增和修改界面		
        3	修改航空公司代理人详细信息,点击保存
        参见业务规则SR-1、SR-2、SR-3	【航空公司代理人新增/修改信息】	
        4	返回航空公司代理人管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		
        
        1.6.3	作废航空公司代理人操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	点击作废图标，可以作废当前记录；选择一行记录或多行记录，点击作废按钮，可以作废多条记录。。		弹出确认对话框。
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		
        
        1.6.4	查询航空公司代理人操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-4	【航空公司代理人查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
        
        1.8	数据元素
        1.8.1	航空公司代理人新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        配载部门	配载部门	选择框		50	是	
        航空公司代码	航空公司二字代码	选择框		2	是	
        代理人编码	代理人编码	文本		20	是	
        代理人名称	代理航空公司空运运输业务的公司	文本		50	是	
        1.8.2	航空公司代理人列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        配载部门	配载部门	N/A	50	N/A	
        航空公司代码	航空公司二字代码	N/A	2	N/A	
        航空公司名称	航空公司的名称	N/A	50	N/A	
        始发城市	配载部门所在城市名称	N/A	20	N/A	
        代理人编码	代理人编码	N/A	10	N/A	
        代理人名称	代理人名称	N/A	50	N/A	
        1.8.3	航空公司代理人查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        代理人名称	代理航空公司空运运输业务的公司	文本	50	否	
        代理人编码	代理人编码	文本	10	否	
        航空公司名称	航空公司名称	文本	50	否	
        配载部门	配载部门，支持手动输入，也支持从行政组织（空运总调）基础资料中选取	文本	50	否	
        
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

import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesAgentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirLinesAgentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntityTransEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AirlinesAgentDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirlinesAgentException;
import com.deppon.foss.util.define.FossConstants;
/**
 * 用来操作交互“航空公司代理人”的数据库对应数据访问Dao调用的Service接口实现类：SUC-61 
 * 
 * 
 * 
 * 
 * 该用例可对航空公司代理人基础资料进行新增、修改、作废、查询操作等，该基础资料主要记录航空公司代理人的基础信息。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件	1、	航空公司基础资料齐备
        2、	行政组织基础资料齐备	SUC-42 新增_修改_删除_查询航空公司
        SUC-85 修改_查询行政组织业务属性
        后置条件	1、	为SUC-247-录入航空正单明细系统用例提供航空公司代理人基础资料查询	SUC-247-录入航空正单明细
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司代理人”进行新增，修改，作废，查询操作。
 * 
 *  SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
    SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
    SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
    SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取

 * 
 * 
 * <p style="display:none">modifyRecord</p>
 * 
 * 
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-16 上午8:38:20</p>
 * 
 * 
 * @author 100847-foss-GaoPeng
 * 
 * 
 * @date 2012-10-16 上午8:38:20
 * 
 * @since
 * 
 * 
 * @version
 */
public class AirlinesAgentService implements IAirlinesAgentService {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesAgentService.class);
    /**
     * 
     * 
     * "航空公司代理人"Dao.
     * 
     * 
     */
    private IAirlinesAgentDao airlinesAgentDao;
    /**
     * 
     * 
     * "组织结构"Service.
     * 
     * 
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 
     * 
     * "航空公司"Service.
     * 
     * 
     */
    private IAirlinesService airlinesService;
    /**
     * 航空公司代理人接口同步
     */
    private ISyncAirLinesAgentService syncAirLinesAgentService;
    /**
     * <p>新增一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p>.
     * 
     * 	SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
     *
     *
     *
     *
     *	1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	点击新增按钮，弹出新增和修改界面		
        3	输入航空公司代理人详细信息，点击保存。
        	参见业务规则SR-1、SR-2、SR-3	【航空公司代理人新增/修改信息】	
        4	返回航空公司代理人管理主界面		
        
        	序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在新增界面		

     * @param airlinesAgent “航空公司代理人”实体
     * 
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
     * @throws AirlinesAgentException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-10-15 下午4:40:48
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService#addAirlinesAgent(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int addAirlinesAgent(AirlinesAgentEntity airlinesAgent,String createUser, boolean ignoreNull) throws AirlinesAgentException {
	//航空公司代理人验证
	if (null == airlinesAgent) {
	    throw new AirlinesAgentException("", "航空公司代理人为空");
	}
	//配载部门验证
	if (StringUtils.isBlank(airlinesAgent.getAssemblyDeptId())) {
	    throw new AirlinesAgentException("", "配载部门为空");
	}
	//航空公司代码
	if (StringUtils.isBlank(airlinesAgent.getAirlinesCode())) {
	    throw new AirlinesAgentException("", "航空公司代码为空");
	}
	//代理人编码
	if (StringUtils.isBlank(airlinesAgent.getAgentCode())) {
	    throw new AirlinesAgentException("", "代理人编码为空");
	}
	//创建人信息
	if (StringUtils.isBlank(createUser)) {
	    throw new AirlinesAgentException("", "创建人信息为空");
	}
	//验证数据
	AirlinesAgentEntity airlinesAgentParameter = new AirlinesAgentEntity();
	//配载部门
	airlinesAgentParameter.setAssemblyDeptId(airlinesAgent.getAssemblyDeptId());
	//航空公司代码
	airlinesAgentParameter.setAirlinesCode(airlinesAgent.getAirlinesCode());
	//代理人编码
	airlinesAgentParameter.setAgentCode(airlinesAgent.getAgentCode());
	//根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）
	AirlinesAgentEntity tempAirlinesAgent = airlinesAgentDao.queryAirlinesAgentBySelective(airlinesAgentParameter);
	if(null != tempAirlinesAgent){
	    throw new AirlinesAgentException("", "航空公司代理人已经存在");
	}else{
	    //设置创建人
	    airlinesAgent.setCreateUser(createUser);
	    int result1=0;
	    int result2=0;
	    if (ignoreNull) {
		//新增一个“航空公司代理人”实体入库 （只选择实体中非空值）
	     result1=airlinesAgentDao.addAirlinesAgentBySelective(airlinesAgent);
	    }else{
		//新增一个“航空公司代理人”实体入库（忽略实体中是否存在空值）
	    result2=airlinesAgentDao.addAirlinesAgent(airlinesAgent);
	    }
     if (result2>0||result1>0) {
    	 syncAirLinesAgentToCUBC(airlinesAgent,"1");
	}
	}
	return FossConstants.SUCCESS;
    }
    private void syncAirLinesAgentToCUBC(AirlinesAgentEntity airlinesAgent,
			String operateType) {
    	List<AirlinesAgentEntity> entitys = new ArrayList<AirlinesAgentEntity>();
    	entitys.add(airlinesAgent);
    	syncAirLinesAgentService.SyncAirLinesAgent(entitys, operateType);	
		
	}
	/**
     * <p>根据“航空公司代理人”记录唯一标识作废一条“航空公司代理人”记录</p>.
     * 
     * 	SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
     *	
     *	1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
	2	输入查询条件，点击查询按钮。参见业务规则SR-4	【航空公司代理人查询条件】	系统返回查询结果

     * 
     * 	1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	点击作废图标，可以作废当前记录；选择一行记录或多行记录，点击作废按钮，可以作废多条记录。。		弹出确认对话框。
        3	点击确定按钮。		
        
        	序号	扩展事件	相关数据	备注
        2a	点击取消按钮，退出当前界面，返回主界面		
        2b	若作废失败，需提示用户作废失败以及失败原因		

     * 
     * @param ids 
     * 
     * @param modifyUser 修改人
     * 
     * @return 1：成功；0：失败
     * 
     * 
     * @throws AirlinesAgentException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * @date 2012-10-16 上午8:38:36
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService#deleteAirlinesAgent(java.lang.String)
     */
    @Override
    @Transactional
    public int deleteAirlinesAgent(List<String> ids, String modifyUser) throws AirlinesAgentException{
	//修改人信息验证
	if (StringUtils.isBlank(modifyUser)) {
	    throw new AirlinesAgentException("", "修改人信息为空");
	}
	//循环迭代
	for (String id : ids) {
	    //id验证
	    if (StringUtils.isBlank(id)) {
		throw new AirlinesAgentException("", "ID为空");
	    }else{
		if(LOGGER.isDebugEnabled()){
		    LOGGER.debug(" *******  ******* 调试：作废航空代理人的ID检测通过 *******  ******* ");
		}
	    }
	    //验证数据
	    AirlinesAgentEntity oldAirlinesAgent, tempAirlinesAgent = new AirlinesAgentEntity();
	    //设置ＩＤ
	    tempAirlinesAgent.setId(id);
	    //根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）
	    oldAirlinesAgent = airlinesAgentDao.queryAirlinesAgentBySelective(tempAirlinesAgent);
	    //航空公司代理人验证
	    if(null == oldAirlinesAgent){
		throw new AirlinesAgentException("", "航空公司代理人不存在");
	    }
	    
	    //设置ID
	    oldAirlinesAgent.setId(id);
	    //设置修改人
	    oldAirlinesAgent.setModifyUser(modifyUser);
	    //设置状态：无效
	    oldAirlinesAgent.setActive(FossConstants.INACTIVE);
	    //修改一个“航空公司代理人”实体入库 （只选择实体中非空值）
	   int result= airlinesAgentDao.updateAirlinesAgentBySelective(oldAirlinesAgent);
	   if(result>0){
		   syncAirLinesAgentToCUBC(oldAirlinesAgent,"3");    
	   }  
	}
	return FossConstants.SUCCESS;
    }
    /**
     * <p>修改一个“航空公司代理人”实体入库（忽略实体中是否存在空值）</p>.
     * 
     * 	SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
     *
     *
     *
     *	1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
        2	点击修改按钮，弹出新增和修改界面		
        3	修改航空公司代理人详细信息,点击保存
        	参见业务规则SR-1、SR-2、SR-3	【航空公司代理人新增/修改信息】	
        4	返回航空公司代理人管理主界面		
        
        	序号	扩展事件	相关数据	备注
        3a	点击取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，需提示用户保存失败以及失败原因，继续停留在修改界面		

     *
     *
     * @param airlinesAgent “航空公司代理人”实体
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
     * @throws AirlinesAgentException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-10-15 下午4:41:34
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService#updateAirlinesAgent(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity, java.lang.String, boolean)
     */
    @Override
    @Transactional
    public int updateAirlinesAgent(AirlinesAgentEntity airlinesAgent,String modifyUser, boolean ignoreNull) throws AirlinesAgentException {
	//航空公司代理人验证
	if (null == airlinesAgent) {
	    throw new AirlinesAgentException("航空公司代理人为空", "航空公司代理人为空");
	}
	//ＩＤ验证
	if (StringUtils.isBlank(airlinesAgent.getId())) {
	    throw new AirlinesAgentException("ID 为空", "ID 为空");
	}
	//配载部门验证
	if (StringUtils.isBlank(airlinesAgent.getAssemblyDeptId())) {
	    throw new AirlinesAgentException("配载部门为空", "配载部门为空");
	}
	//航空公司代码验证
	if (StringUtils.isBlank(airlinesAgent.getAirlinesCode())) {
	    throw new AirlinesAgentException("航空公司代码为空", "航空公司代码为空");
	}
	//代理人编码验证
	if (StringUtils.isBlank(airlinesAgent.getAgentCode())) {
	    throw new AirlinesAgentException("代理人编码为空", "代理人编码为空");
	}
	//修改人信息验证
	if (StringUtils.isBlank(modifyUser)) {
	    throw new AirlinesAgentException("修改人信息为空", "修改人信息为空");
	}
	//验证数据
//	AirlinesAgentEntity oldAirlinesAgent, tempAirlinesAgent = new AirlinesAgentEntity();
//	//配载部门
//	tempAirlinesAgent.setAssemblyDeptId(airlinesAgent.getAssemblyDeptId());
//	//航空公司代码
//	tempAirlinesAgent.setAirlinesCode(airlinesAgent.getAirlinesCode());
//	//设置 航空公司代码.
//	tempAirlinesAgent.setAgentCode(airlinesAgent.getAgentCode());
	//根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）
	/*oldAirlinesAgent = airlinesAgentDao.queryAirlinesAgentBySelective(tempAirlinesAgent);
	if(null == oldAirlinesAgent){
	    throw new AirlinesAgentException("航空公司代理人不存在", "航空公司代理人不存在");
	}else{
	    //设置修改人
	    airlinesAgent.setModifyUser(modifyUser);
	    if (ignoreNull) {
		//修改一个“航空公司代理人”实体入库 （只选择实体中非空值）
		airlinesAgentDao.updateAirlinesAgentBySelective(airlinesAgent);
	    }else{
		//修改一个“航空公司代理人”实体入库（忽略实体中是否存在空值）
		airlinesAgentDao.updateAirlinesAgent(airlinesAgent);
	    }
	}*/
	 int result1=0;
	 int result2=0;
	//设置修改人
	airlinesAgent.setModifyUser(modifyUser);
	if (ignoreNull) {
	    //修改一个“航空公司代理人”实体入库 （只选择实体中非空值）
		result1=  airlinesAgentDao.updateAirlinesAgentBySelective(airlinesAgent);
	}else{
	    //修改一个“航空公司代理人”实体入库（忽略实体中是否存在空值）
		result2= airlinesAgentDao.updateAirlinesAgent(airlinesAgent);
	}
	if (result1>0||result2>0) {
		syncAirLinesAgentToCUBC(airlinesAgent,"2");	
	}
	return FossConstants.SUCCESS;
    }
    
    /**
     * <p>根据“航空公司代理人”记录唯一标识查询出一条“航空公司代理人”记录</p>.
     * 
     * 	SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
     *
     *
     *	1	进入航空公司代理人管理主界面	【航空公司代理人列表信息】	
	2	输入查询条件，点击查询按钮。参见业务规则SR-4	【航空公司代理人查询条件】	系统返回查询结果
     *
     * @param id 记录唯一标识
     * 
     * 
     * @return “航空公司代理人”实体
     * 
     * 
     * @throws AirlinesAgentException 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * @date 2012-10-16 上午8:39:00
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService#queryAirlinesAgent(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public AirlinesAgentEntity queryAirlinesAgent(String id) throws AirlinesAgentException{
	//参数验证
	if(StringUtils.isBlank(id)){
	    //异常信息处理
	    throw new AirlinesAgentException("", "ID 为空");
	}
	//定义对象
	AirlinesAgentEntity airlinesAgent = new AirlinesAgentEntity();
	//设置ＩＤ
	airlinesAgent.setId(id);
	//根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）
	return airlinesAgentDao.queryAirlinesAgentBySelective(airlinesAgent);
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）</p>.
     * 
     * 	SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
     *
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * 
     * 
     * 
     * 
     * @return 符合条件的“航空公司代理人”实体
     * 
     * 
     * 
     * @throws AirlinesAgentException 
     * 
     * 
     * 
     * @author 100847-foss-GaoPeng
     * 
     * 
     * 
     * @date 2012-12-3 上午10:36:42
     * 
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService#queryAirlinesAgentBySelective(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity)
     */
    @Override
    public AirlinesAgentEntity queryAirlinesAgentBySelective(AirlinesAgentEntity airlinesAgent) throws AirlinesAgentException {
	//参数验证
	if(null == airlinesAgent){
	    //异常信息处理
	    throw new AirlinesAgentException("", "航空公司代理人为空");
	}
	//根据条件有选择的检索出符合条件的“航空公司代理人”唯一记录（条件做自动判断，只选择实体中非空值）
        return airlinesAgentDao.queryAirlinesAgentBySelective(airlinesAgent);
    }
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     * 
     * 
     * SR-1	“航空公司代码”，“代理人编码”组合为唯一的信息，不允许重复
        SR-2	新增与编辑页面中，“配载部门”不支持手动输入，只支持从行政组织（空运总调）基础资料中选取；“始发城市”与“配载部门”联动，只读；“航空公司代码”不支持手动输入，只支持从航空公司基础资料中选取；“航空公司”与“航空公司代码”联动，只读；
        SR-3	“配载部门”与“航空公司”形成组合主键，不允许重复
        SR-4	查询支持模糊查询；“航空公司名称”支持手动输入，也支持从航空公司基础资料中选取；“配载部门”支持手动输入，也支持从行政组织（空运总调）基础资料中选取
     *
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * 
     * 
     * @param offset 开始记录数
     * 
     * 
     * 
     * @param limit 限制记录数
     * 
     * 
     * @return 分页的Action和Service通讯封装对象
     * 
     * 
     * @throws AirlinesAgentException
     * 
     * 
     *  
     * @author 100847-foss-GaoPeng
     * 
     * 
     * 
     * @date 2012-10-16 上午8:39:04
     * 
     * 
     * 根据ISSUE-3779航空公司代理人基础数据中增加是否为外部代理的标识2013-8-13 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesAgentService#queryAirlinesAgentListBySelectiveCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntity, int, int)
     */
    @Override
    @Transactional(readOnly = true)
    public PaginationDto queryAirlinesAgentListBySelectiveCondition(AirlinesAgentEntity airlinesAgent, int offset, int limit) throws AirlinesAgentException{
	//定义分页对象
	PaginationDto paginationDto = new PaginationDto();
	//参数验证
	if (limit < 0 || offset < 0) {
	    //返回空值
	    return paginationDto;
	}
	//参数验证
	if(null == airlinesAgent){
	  //定义对象
	    airlinesAgent = new AirlinesAgentEntity();
	}
	//定义集合
	List<AirlinesAgentEntity> airlinesAgentList = null;
	//据条件（分页模糊）有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，
	//只选择实体中非空值）
	airlinesAgentList = airlinesAgentDao.queryAirlinesAgentListBySelectiveCondition(airlinesAgent, offset, limit);
	//对实体进行转换封装
	if (CollectionUtils.isNotEmpty(airlinesAgentList)) {
	    //定义集合
	    List<AirlinesAgentDto> airlinesAgentDtos = new ArrayList<AirlinesAgentDto>();
	    //迭代循环
	    for (AirlinesAgentEntity airlinesAgentEntity : airlinesAgentList) {
		//创建对象
		AirlinesAgentDto airlinesAgentDto = new AirlinesAgentDto();
		if(StringUtils.isNotEmpty(airlinesAgentEntity.getIsOutAgent())){
			airlinesAgentDto.setIsOutAgent(airlinesAgentEntity.getIsOutAgent());
		}
		try {
		    //复制信息
		    BeanUtils.copyProperties(airlinesAgentEntity,airlinesAgentDto.getAirlinesAgent(), new String[] {"createDate", "modifyDate" });
		    //设置创建时间
		    airlinesAgentDto.getAirlinesAgent().setCreateDate(airlinesAgentEntity.getCreateDate());
		    //设置修改日期
		    airlinesAgentDto.getAirlinesAgent().setModifyDate(airlinesAgentEntity.getModifyDate());
		} catch (Exception e) {
		    //异常信息处理
		    throw new AirlinesAgentException("", "复制空运代理人数据信息失败");
		}
		//配置部门走缓存
		OrgAdministrativeInfoEntity organization = null;
		//根据编码查询
		organization = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(airlinesAgentEntity.getAssemblyDeptId());
		//结果验证
		if (null != organization) {
		    //设置部门
		    airlinesAgentDto.setDispatchDepartment(organization.getName());
		    //始发城市
		    airlinesAgentDto.setOriginatingCity(organization.getCityName());
		}
		//航空公司名称走缓存
		AirlinesEntity airlines = airlinesService.queryAirlineByCode(airlinesAgentEntity.getAirlinesCode());
		if (null != airlines) {
		    //设置名称
		    airlinesAgentDto.setAirlinesName(airlines.getName());
		}
		//添加信息
		airlinesAgentDtos.add(airlinesAgentDto);
	    }
	    //查询总记录数
	    Long totalCount = airlinesAgentDao.queryAirlinesAgentRecordCountBySelectiveCondition(airlinesAgent);
	    //设置分页对象
	    paginationDto.setPaginationDtos(airlinesAgentDtos);
	    //设置总记录数
	    paginationDto.setTotalCount(totalCount);
	}
	return paginationDto;
    }
    
    /**
     * <p>根据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，只选择实体中非空值）</p>.
     * 
     * @param airlinesAgent 以“航空公司代理人”实体承载的条件参数实体
     * @return 分页的Action和Service通讯封装对象
     * @throws AirlinesAgentException
     * @author 313353-foss-Qiupeng
     * @date 2016-05-28 上午8:39:04
     */
	@Override
    @Transactional(readOnly = true)
	public List<AirlinesAgentEntityTransEntity> queryAirlinesAgentTransListBySelectiveCondition(
			AirlinesAgentEntity airlinesAgent)
			throws AirlinesAgentException {
		List<AirlinesAgentEntityTransEntity> airlineAgentTransEntityList = new ArrayList<AirlinesAgentEntityTransEntity>();
		// 参数验证
		if (null == airlinesAgent) {
			// 定义对象
			airlinesAgent = new AirlinesAgentEntity();
		}
		// 定义集合
		List<AirlinesAgentEntity> airlinesAgentList = null;
		// 据条件有选择的检索出符合条件的“航空公司代理人”实体列表（条件做自动判断，
		// 只选择实体中非空值）
		airlinesAgentList = airlinesAgentDao
				.queryAirlinesAgentListByAgentCode(airlinesAgent);
		// 对实体进行转换封装
		if (CollectionUtils.isNotEmpty(airlinesAgentList)) {
			// 迭代循环
			for (AirlinesAgentEntity airlinesAgentEntity : airlinesAgentList) {
				// 创建对象
				AirlinesAgentEntityTransEntity airlinesAgentEntityTransEntity = new AirlinesAgentEntityTransEntity();
				if (StringUtils.isNotEmpty(airlinesAgentEntity.getIsOutAgent())) {
					airlinesAgentEntityTransEntity
							.setIsOutAgent(airlinesAgentEntity.getIsOutAgent());
				}
				try {
					// 复制信息
					airlinesAgentEntityTransEntity
							.setAgentCode(airlinesAgentEntity.getAgentCode());
					airlinesAgentEntityTransEntity
							.setAgentName(airlinesAgentEntity.getAgentName());
				} catch (Exception e) {
					// 异常信息处理
					throw new AirlinesAgentException("", "复制空运代理人数据信息失败");
				}
				// 配置部门走缓存
				OrgAdministrativeInfoEntity organization = null;
				// 根据编码查询
				organization = orgAdministrativeInfoService
						.queryOrgAdministrativeInfoByCode(airlinesAgentEntity
								.getAssemblyDeptId());
				// 结果验证
				if (null != organization) {
					airlinesAgentEntityTransEntity
							.setDispatchDepartCode(organization.getCode());
					airlinesAgentEntityTransEntity
							.setDispatchDepartment(organization.getName());
					airlinesAgentEntityTransEntity
							.setOriginatingCity(organization.getCityName());
					airlinesAgentEntityTransEntity
							.setOriginatingCityCode(organization.getCityCode());
				}
				airlineAgentTransEntityList.add(airlinesAgentEntityTransEntity);
			}
		}
		return airlineAgentTransEntityList;
	}
    
    
    
    /**
     * 
     * 
     * 设置 "航空公司代理人"Dao.
     * 
     * 
     *
     * @param airlinesAgentDao the airlinesAgentDao to set
     */
    public void setAirlinesAgentDao(IAirlinesAgentDao airlinesAgentDao) {
        this.airlinesAgentDao = airlinesAgentDao;
    }
    /**
     * 
     * 
     * 设置 "组织结构"Service.
     * 
     * 
     *
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    /**
     * 
     * 
     * 设置 "航空公司"Service.
     * 
     * 
     *
     * @param airlinesService the airlinesService to set
     */
    public void setAirlinesService(IAirlinesService airlinesService) {
        this.airlinesService = airlinesService;
    }
	public void setSyncAirLinesAgentService(
			ISyncAirLinesAgentService syncAirLinesAgentService) {
		this.syncAirLinesAgentService = syncAirLinesAgentService;
	}
    
}
