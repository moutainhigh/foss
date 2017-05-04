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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/AirlinesService.java
 * 
 * FILE NAME        	: AirlinesService.java
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
 * 
 * 
 * 
 * 
 * 该用例可对航空公司基础资料进行新增、修改、作废、查询操作等，该基础资料主要记录航空公司的基础信息。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件		
        后置条件	1、	为SUC-247 录入航空正单明细系统用例提供航空公司基础资料查询
        2、	为SUC-290打印航空正单标签系统用例提供航空公司基础资料查询
        3、	为SUC-308打印航空正单交接单系统用例提供航空公司基础资料查询	SUC-247 录入航空正单明细
        SUC-309审核代收货款(空运)
        SUC-290打印航空正单标签
        SUC-308打印航空正单交接单
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司”进行新增，修改，作废，查询操作。
        1.5	界面要求
        1.5.1	表现方式
        WEB页面
        1.5.2	界面原型-主界面
         
        图一：航空公司管理主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮进入新增界面，参见【图二：航空公司新增和修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，重置查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框。
        5)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：航空公司新增和修改界面】。
        6)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：航空公司、航空公司代码、航空公司简称、航空公司数字前缀。
        3.	字段输入区域
        1)	录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。
        1.5.4	界面原型-新增和修改界面
         
        图二：航空公司新增和修改界面
        1.5.5	界面描述-新增和修改界面
        1.	字段输入区域
        1)	航空公司： 必填
        2)	航空公司代码：必填
        3)	航空公司简称：必填
        4)          航空公司数字前缀：选填
        5)          航空公司LOGO：选填
        4)          描述：选填
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，若保存成功，弹出保存成功提示框，返回主界面，否则，提示保存失败，返回新增/修改界面。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
        1.6	操作步骤
        1.6.1	添加航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入航空公司详细信息，点击保存。
        参见业务规则SR-1	【航空公司新增/修改信息】	
        4	返回航空公司管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改航空公司详细信息,点击保存
        参见业务规则SR-1	【航空公司新增/修改信息】	
        4	返回航空公司管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面		
        
        1.6.3	作废航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司列表信息】	
        2	点击作废图标，可以作废当前记录；选择一行记录或多行记录，点击作废按钮，可以作废多条记录。		弹出确认对话框。
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	取消按钮，关闭对话框，返回主界面		
        2b	确认作废后，若作废失败，弹出对话框，提示用户作废失败以及失败原因		
        
        1.6.4	查询航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司账户列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-2	【航空公司查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	新增界面中航空公司、航空公司代码、航空公司简称均为唯一的信息，不能重复
        SR-2	航空公司查询支持模糊查询
        
        1.8	数据元素
        1.8.1	航空公司新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        航空公司	以各种航空飞行器为运输工具为乘客和货物提供民用航空服务的企业	文本		50	是	
        航空公司代码	航空公司代码都是在国际组织中注册的代号	文本		2	是	
        航空公司简称	航空公司根据代码生成的简短容易记住的名字	文本		50	是	
        航空公司数字前缀	航空公司的3位数字前缀	数字		3	是	
        航空公司LOGO	航空公司LOGO图标	图片		-	否	
        描述	对航空公司说明的信息	文本		200	否	
        1.8.2	航空公司列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        航空公司	以各种航空飞行器为运输工具为乘客和货物提供民用航空服务的企业	N/A	50	N/A	
        航空公司代码	航空公司代码都是在国际组织中注册的代号	N/A	2	N/A	
        航空公司简称	航空公司根据代码生成的简短容易记住的名字	N/A	50	N/A	
        航空公司数字前缀	航空公司的3位数字前缀	N/A	3		
        1.8.3	航空公司查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        航空公司	航空公司名称	文本	50	否	
        航空公司代码	航空公司	文本	2	否	
        
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IAirlinesDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAirLineService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.AirlinesException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 航空公司service接口实现：提供对航空公司的增删改查的基本操作
 * 
 * 
 * 该用例可对航空公司基础资料进行新增、修改、作废、查询操作等，该基础资料主要记录航空公司的基础信息。
        1.3	用例条件
        条件类型	描述	引用系统用例
        前置条件		
        后置条件	1、	为SUC-247 录入航空正单明细系统用例提供航空公司基础资料查询
        2、	为SUC-290打印航空正单标签系统用例提供航空公司基础资料查询
        3、	为SUC-308打印航空正单交接单系统用例提供航空公司基础资料查询	SUC-247 录入航空正单明细
        SUC-309审核代收货款(空运)
        SUC-290打印航空正单标签
        SUC-308打印航空正单交接单
        1.4	操作用户角色
        操作用户	描述
        空运管理员	空运管理员对“航空公司”进行新增，修改，作废，查询操作。
        1.5	界面要求
        1.5.1	表现方式
        WEB页面
        1.5.2	界面原型-主界面
         
        图一：航空公司管理主界面
        1.5.3	界面描述-主界面
        1.	功能按钮区域
        1)	新增按钮：点击新增按钮进入新增界面，参见【图二：航空公司新增和修改界面】。
        2)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
        3)	重置按钮：点击重置按钮，重置查询条件。
        4)	作废按钮：选中列表中一行或多行记录，点击作废按钮，选中的记录被作废；或点击各行的作废按钮，作废各行记录；需要弹出确认提示框。
        5)	修改按钮：点击各行的修改按钮，进入修改界面，参见【图二：航空公司新增和修改界面】。
        6)	分页按钮：实现分页功能。
        2.	列表区域
        1)	列表区域默认不显示，点击查询按钮，根据查询条件显示列表数据。
        2)	列表中显示：航空公司、航空公司代码、航空公司简称、航空公司数字前缀。
        3.	字段输入区域
        1)	录入查询条件，点击查询按钮后在列表区显示满足条件的查询结果。
        1.5.4	界面原型-新增和修改界面
         
        图二：航空公司新增和修改界面
        1.5.5	界面描述-新增和修改界面
        1.	字段输入区域
        1)	航空公司： 必填
        2)	航空公司代码：必填
        3)	航空公司简称：必填
        4)          航空公司数字前缀：选填
        5)          航空公司LOGO：选填
        4)          描述：选填
        2.	功能按钮区域
        1)	保存按钮：点击保存按钮，若保存成功，弹出保存成功提示框，返回主界面，否则，提示保存失败，返回新增/修改界面。
        2)	重置按钮：点击重置按钮，回到当前界面的初始状态。
        3)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
        1.6	操作步骤
        1.6.1	添加航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司列表信息】	
        2	点击新增按钮，进入新增和修改界面		
        3	输入航空公司详细信息，点击保存。
        参见业务规则SR-1	【航空公司新增/修改信息】	
        4	返回航空公司管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在新增界面		
        
        1.6.2	修改航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司列表信息】	
        2	点击修改按钮，进入新增和修改界面		
        3	修改航空公司详细信息,点击保存
        参见业务规则SR-1	【航空公司新增/修改信息】	
        4	返回航空公司管理主界面		
        
        序号	扩展事件	相关数据	备注
        3a	取消按钮，退出当前界面，返回主界面		
        3b	若保存失败，弹出对话框，提示用户保存失败以及失败原因，继续停留在修改界面		
        
        1.6.3	作废航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司列表信息】	
        2	点击作废图标，可以作废当前记录；选择一行记录或多行记录，点击作废按钮，可以作废多条记录。		弹出确认对话框。
        3	点击确定按钮。		
        
        序号	扩展事件	相关数据	备注
        2a	取消按钮，关闭对话框，返回主界面		
        2b	确认作废后，若作废失败，弹出对话框，提示用户作废失败以及失败原因		
        
        1.6.4	查询航空公司操作步骤
        序号	基本步骤	相关数据	补充步骤
        1	进入航空公司管理主界面	【航空公司账户列表信息】	
        2	输入查询条件，点击查询按钮。参见业务规则SR-2	【航空公司查询条件】	系统返回查询结果
        1.7	业务规则
        序号	描述
        SR-1	新增界面中航空公司、航空公司代码、航空公司简称均为唯一的信息，不能重复
        SR-2	航空公司查询支持模糊查询
        
        1.8	数据元素
        1.8.1	航空公司新增/修改信息
        字段名称 	说明 	输入限制	输入项提示文本	长度	是否必填	备注
        航空公司	以各种航空飞行器为运输工具为乘客和货物提供民用航空服务的企业	文本		50	是	
        航空公司代码	航空公司代码都是在国际组织中注册的代号	文本		2	是	
        航空公司简称	航空公司根据代码生成的简短容易记住的名字	文本		50	是	
        航空公司数字前缀	航空公司的3位数字前缀	数字		3	是	
        航空公司LOGO	航空公司LOGO图标	图片		-	否	
        描述	对航空公司说明的信息	文本		200	否	
        1.8.2	航空公司列表信息
        字段名称 	说明 	输入限制	长度	是否必填	备注
        航空公司	以各种航空飞行器为运输工具为乘客和货物提供民用航空服务的企业	N/A	50	N/A	
        航空公司代码	航空公司代码都是在国际组织中注册的代号	N/A	2	N/A	
        航空公司简称	航空公司根据代码生成的简短容易记住的名字	N/A	50	N/A	
        航空公司数字前缀	航空公司的3位数字前缀	N/A	3		
        1.8.3	航空公司查询条件
        字段名称 	说明 	输入限制	长度	是否必填	备注
        航空公司	航空公司名称	文本	50	否	
        航空公司代码	航空公司	文本	2	否	
        
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
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 下午2:49:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 下午2:49:17
 * @since
 * @version
 */
public class AirlinesService implements IAirlinesService {
    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AirlinesService.class);
    /**
     * 航空公司DAO接口.
     */
    private IAirlinesDao airlinesDao;
    /**
     * 设置 航空公司DAO接口.
     *
     * @param airlinesDao the new 航空公司DAO接口
     */
    public void setAirlinesDao(IAirlinesDao airlinesDao) {
        this.airlinesDao = airlinesDao;
    }
    /**
     * 航空公司同步cubc接口
     */
    private ISyncAirLineService syncAirLineService;
    public void setSyncAirLineService(ISyncAirLineService syncAirLineService) {
		this.syncAirLineService = syncAirLineService;
	}
	/**
     * 新增航空公司.
     *
     * @param entity 航空公司实体
     * 
     * 
     * @return 1：成功；-1：失败
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-10-15 下午2:52:02
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#addAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
     */
    @Transactional
    @Override
    public int addAirlines(AirlinesEntity entity) {
	if(null == entity){
	    
	    return FossConstants.FAILURE;
	}
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateDate(new Date());
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setActive(FossConstants.ACTIVE);
	int result = airlinesDao.addAirlines(entity);
	if (result>0) {
		syncAirLineToCUBC(entity,"1");
	}
	return result;
	//return airlinesDao.addAirlines(entity);
    }
    private void syncAirLineToCUBC(AirlinesEntity entity, String operateType) {
    	List<AirlinesEntity> entitys = new ArrayList<AirlinesEntity>();
    	entitys.add(entity);
    	syncAirLineService.SyncAirLine(entitys, operateType);	
		
	}
	/**
     * 根据code作废航空公司.
     *	
     *
     *
     * @param codeStr Id拼接字符串
     * 
     * 
     * @param modifyUser 
     * 
     * 
     * @return 1：成功；-1：失败
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-15 下午2:51:57
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#deleteAirlinesByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deleteAirlinesByCode(String codeStr,String modifyUser) {
	if(StringUtil.isBlank(codeStr)){
	    throw new AirlinesException("航空公司ID不允许为空！");
	}else {
	    LOGGER.debug("codeStr:"+ codeStr +"modifyUser: "+modifyUser);
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);
	    int result = airlinesDao.deleteAirlinesByCode(codes,modifyUser);
	    //return airlinesDao.deleteAirlinesByCode(codes,modifyUser);
	    List<AirlinesEntity> deleteEntitys = airlinesDao.queryAirlinesById(codes);
	    if (result>0) {
	    	syncAirLineService.SyncAirLine(deleteEntitys, "3");
		}
	    return result;
	}
    }
    
    /**
     * 修改航空公司.
     *
     *
     *
     *
     * @param entity 航空公司实体
     * 
     * @return 1：成功；-1：失败
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-10-15 下午2:52:11
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#updateAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
     */
    @Transactional
    @Override
    public int updateAirlines(AirlinesEntity entity) {
	if(null == entity){
	    return FossConstants.FAILURE;
	}else {
	    if(StringUtil.isNotBlank(entity.getId())){
	    int result = airlinesDao.updateAirlines(entity);
	    if (result>0) {
	    	syncAirLineToCUBC(entity, "2");	
		}
		//return airlinesDao.updateAirlines(entity);
	    return result;
	    }else {
		throw new AirlinesException("航空公司ID不允许为空！");
	    }
	}
    }
    /**
     * 根据传入对象查询符合条件所有航空公司信息.
     *
     * @param entity 航空公司实体
     * 
     * 
     * @param limit 每页最大显示记录数
     * 
     * 
     * @param start 开始记录数
     * 
     * 
     * @return 符合条件的实体列表
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-10-15 下午2:52:20
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#queryAirlines(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity, int, int)
     */
    @Transactional
    @Override
    public List<AirlinesEntity> queryAirlines(AirlinesEntity entity, int limit,int start) {
	entity.setActive(FossConstants.ACTIVE);
	return airlinesDao.queryAirlines(entity, limit, start);
    }
    
    /**
     * 统计总记录数.
     *
     * @param entity 航空公司实体
     * 
     * 
     * @return 
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-10-15 下午2:52:28
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(AirlinesEntity entity) {
	entity.setActive(FossConstants.ACTIVE);
	return airlinesDao.queryRecordCount(entity);
    }
    /**
     * 根据航空公司编码查询公司是否存在.
     *
     * @param airlineCode 航空公司编码
     * 
     * 
     * @return 
     * 
     * @author 094463-foss-xieyantao
     * 
     * @date 2012-10-30 上午11:35:49
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#queryAirlineByCode(java.lang.String)
     */
    @Override
    public AirlinesEntity queryAirlineByCode(String airlineCode) {
	if(StringUtil.isBlank(airlineCode)){
	    throw new AirlinesException("航空公司编码不允许为空！");
	}else {
	    LOGGER.debug("airlineCode:" + airlineCode);
	    return airlinesDao.queryAirlineByCode(airlineCode);
	}
    }
    /**
     * 根据航空公司名称查询公司是否存在.
     *
     *
     *
     *
     * @param airlineName 
     * 
     * 
     * @return 
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-10-30 上午11:35:49
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#queryAirlineByName(java.lang.String)
     */
    @Override
    public AirlinesEntity queryAirlineByName(String airlineName) {
	if(StringUtil.isBlank(airlineName)){
	    throw new AirlinesException("航空公司名称不允许为空！");
	}else {
	    LOGGER.debug("airlineName:" + airlineName);
	    return airlinesDao.queryAirlineByName(airlineName);
	}
    }
    /**
     * 根据航空公司简称查询公司是否存在.
     *
     * @param simpleName 
     * 
     * 
     * @return 
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-10-30 上午11:35:49
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#queryAirlineBySimpleName(java.lang.String)
     */
    @Override
    public AirlinesEntity queryAirlineBySimpleName(String simpleName) {
	//参数验证
	if(StringUtil.isBlank(simpleName)){
	    //异常信息处理
	    throw new AirlinesException("航空公司简称不允许为空！");
	}else {
	    //日志记录
	    LOGGER.debug("simpleName:" + simpleName);
	    //根据航空公司简称查询公司是否存在
	    return airlinesDao.queryAirlineBySimpleName(simpleName);
	}
    }
    /**
     * <p>查询所有有效航空公司信息</p>.
     *
     *
     *
     * @return 
     * 
     * 
     * @author 094463-foss-xieyantao
     * 
     * 
     * @date 2012-11-28 下午4:17:49
     * 
     * 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IAirlinesService#queryAllAirlines()
     */
    @Override
    public List<AirlinesEntity> queryAllAirlines() {
	//查询所有有效航空公司信息
	return airlinesDao.queryAllAirlines();
    }
}
