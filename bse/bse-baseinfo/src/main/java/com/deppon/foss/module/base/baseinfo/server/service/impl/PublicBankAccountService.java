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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/PublicBankAccountService.java
 * 
 * FILE NAME        	: PublicBankAccountService.java
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
 * 接口名称	同步对公银行帐号信息
        背 景	FOSS需要各营业部的对公银行帐号信息
        接口提供者	FOSS系统	接口调用者	财务自助系统
        交互场景	前置条件：
        1、财务自助系统正常运行
        
        接口场景
        1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
        后置动作：
        
        接口异常处理
        接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统
        接口内容	传入参数信息：
        字段	是否必填	是否列表	备注
        银行帐号信息	是	是	参见下表
        
        字段	是否必填	是否列表	备注
        部门标杆编码	是	否	
        银行编码	否	否	
        银行名称	是	否	
        支行编码	是	否	
        支行名称	是	否	
        银行开户名	是	否	
        省份编码	是	否	
        省份名称	是	否	
        城市编码	是	否	
        城市名称	是	否	
        银行账号	是	否	
        账号ID	是	否	
        操作类型	是	否	操作类型：1-新增，2-修改，3-删除；注意可能没有删除
        账号状态	是	否	正常/销户
        最后更新时间	是	否	当前数据的最后更新时间
        
        返回参数：
        字段	是否必填	是否列表	备注
        成功总数	是	否	
        失败总数	是	否	
        处理明细	是	是	参见【处理明细】
        
        处理明细
        字段	是否必填	是否列表	备注
        部门标杆编码	是	否	
        成功或失败的标识	是	否	
        失败原因	否	否	如果处理失败，此字段为必填
        
        非功能需求	调用时效：立即
        交互模式：请求/回调
        是否需要顺序执行：否
        是否批量处理：是
        是否允许重复调用：是
        调用时段和调用量： 
        消息大小 ： 
        时间响应要求：N/A
        安全性要求：无
        业务规则	

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

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;

/**
 * 对公银行账号 Service实现
 * 
 * 
 * 接口名称	同步对公银行帐号信息
 * 背 景	FOSS需要各营业部的对公银行帐号信息
 * 接口提供者	FOSS系统	接口调用者	财务自助系统
 * 交互场景	前置条件：
 * 1、财务自助系统正常运行
 * 
 * 接口场景
 * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
 * 后置动作：
 * 
 * 接口异常处理
 * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
 *
 * @author 087584-foss-lijun
 * @date 2012-12-10 下午8:4:34
 */
public class PublicBankAccountService implements IPublicBankAccountService {
    /**
     * 下面是dao对象的声明及get,set方法：.
     */
    private IPublicBankAccountDao publicBankAccountDao;
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 设置 下面是dao对象的声明及get,set方法：.
     *
     * @param publicBankAccountDao the new 下面是dao对象的声明及get,set方法：
     */
    public void setPublicBankAccountDao(IPublicBankAccountDao publicBankAccountDao) {
	this.publicBankAccountDao = publicBankAccountDao;
    }
    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the new 组织信息 Service接口
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }
    /**
     * 对公银行账号 新增 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IPublicBankAccountService#addPublicBankAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    @Transactional
    public PublicBankAccountEntity addPublicBankAccount(PublicBankAccountEntity entity) {
	//检查参数的合法性
	if (null == entity) {
	    return null;
	}
	
	return publicBankAccountDao.addPublicBankAccount(entity);
    }
    /**
     * 通过FID标识来删除 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#deletePublicBankAccount(java.lang.String)
     */
    @Override
    @Transactional
    public PublicBankAccountEntity deletePublicBankAccount(PublicBankAccountEntity entity) {
	// 请求合法性判断：
	if (null == entity || StringUtils.isBlank(entity.getBankAcc())) {
	    return null;
	}
	return publicBankAccountDao.deletePublicBankAccount(entity);
    }
    /**
     * 通过FID标识来批量删除 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param codes 
     * @param deleteUser 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#deletePublicBankAccountMore(java.lang.String[])
     */
    @Override
    @Transactional
    public PublicBankAccountEntity deletePublicBankAccountMore(String[] codes , String deleteUser) {
	return publicBankAccountDao.deletePublicBankAccountMore(codes, deleteUser);
    }
    /**
     * 更新 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#updatePublicBankAccount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    @Transactional
    public PublicBankAccountEntity updatePublicBankAccount(PublicBankAccountEntity entity) {
	//检查参数的合法性
	if (null == entity || StringUtils.isBlank(entity.getBankAcc())) {
	    return null;
	}
	
	return publicBankAccountDao.updatePublicBankAccount(entity);
    }
    /**
     * 以下全为查询.
     *
     * @param code 
     * @return 
     */
	
    /**
     * 精确查询 
     * 
     * 通过 FID 查询 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
                    背 景	FOSS需要各营业部的对公银行帐号信息
                    接口提供者	FOSS系统	接口调用者	财务自助系统
                    交互场景	前置条件：
                    1、财务自助系统正常运行
                    
                    接口场景
                    1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
                    后置动作：
                    
                    接口异常处理
                    接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountService#queryPublicBankAccountByCode(java.lang.String)
     */
    @Override
    public PublicBankAccountEntity queryPublicBankAccountByFid(String code) {
	if (null == code) {
	    return null;
	}
	return publicBankAccountDao.queryPublicBankAccountByFid(code);
    }
    /**
     * 精确查询
     * 
     * 根据多个编码批量查询 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param codes 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountService#queryPublicBankAccountByCode(java.lang.String)
     */
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountBatchByFid(
	    String[] codes) {
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	return publicBankAccountDao.queryPublicBankAccountBatchByFid(codes);
    }
    /**
     * 精确查询 对公银行账号
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @param start 
     * @param limit 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountService#queryPublicBankAccountExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity, int, int)
     */
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountExactByEntity(PublicBankAccountEntity entity, int start, int limit) {
	return publicBankAccountDao.queryPublicBankAccountExactByEntity(entity, start, limit);
    }
    /**
     * 精确查询 对公银行账号
     * 查询总条数，用于分页
     * 
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountService#queryPublicBankAccountExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity)
     */
    @Override
    public long queryPublicBankAccountExactByEntityCount(PublicBankAccountEntity entity) {
	return publicBankAccountDao.queryPublicBankAccountExactByEntityCount(entity);
    }
    /**
     * 精确查询 对公银行账号,返回所有的结果，
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     */
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountExactByEntityAll(PublicBankAccountEntity entity) {
	return this.queryPublicBankAccountExactByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
    }
    /**
     * 模糊查询 对公银行账号
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @param start 
     * @param limit 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountService#deletePublicBankAccountMore(java.lang.String[])
     */
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountByEntity(PublicBankAccountEntity entity, int start, int limit) {
	return publicBankAccountDao.queryPublicBankAccountByEntity(entity, start, limit);
    }
    /**
     * 模糊查询 对公银行账号
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param entity 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.baseinfo.server.service.IPublicBankAccountService#queryPublicBankAccountCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.PublicBankAccountEntity)
     */
    @Override
    public long queryPublicBankAccountByEntityCount(PublicBankAccountEntity entity) {
	return publicBankAccountDao.queryPublicBankAccountByEntityCount(entity);
    }
    /**
     * 下面是特殊方法.
     *
     * @param entity 
     * @return 
     */
    /**
     * 根据entity精确查询 对公银行账号,返回所有的结果，
     * 
     * 
     * 接口名称	同步对公银行帐号信息
                    背 景	FOSS需要各营业部的对公银行帐号信息
                    接口提供者	FOSS系统	接口调用者	财务自助系统
                    交互场景	前置条件：
                    1、财务自助系统正常运行
                    
                    接口场景
                    1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
                    后置动作：
                    
                    接口异常处理
                    接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统
                    
     * @author 087584-foss-lijun
     * 
     * @date 2012-12-10 下午8:4:34
     */
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountByEntityAll(PublicBankAccountEntity entity) {
	return this.queryPublicBankAccountByEntity(entity, NumberConstants.ZERO, Integer.MAX_VALUE);
    }
    /**
     * 根据部门标杆编码deptCd 精确查询 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param deptCd 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     */
    @Override
    public List<PublicBankAccountEntity> queryPublicBankAccountByDeptCd(String deptCd) {
	PublicBankAccountEntity entityCodition = new PublicBankAccountEntity();
	entityCodition.setDeptCd(deptCd);
	return this.queryPublicBankAccountByEntity(entityCodition, NumberConstants.ZERO, Integer.MAX_VALUE);
    }
    /**
     * 根据部门编码code 精确查询 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
     * 背 景	FOSS需要各营业部的对公银行帐号信息
     * 接口提供者	FOSS系统	接口调用者	财务自助系统
     * 交互场景	前置条件：
     * 1、财务自助系统正常运行
     * 
     * 接口场景
     * 1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
     * 后置动作：
     * 
     * 接口异常处理
     * 接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统.
     *
     * @param code 
     * @return 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     */
    @Override
    public PublicBankAccountEntity queryPublicBankAccountOneByDeptCode(String code) {
	OrgAdministrativeInfoEntity org = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(code);
	if(org == null){
	    return null;
	}
	List<PublicBankAccountEntity> entityResults = this.queryPublicBankAccountByDeptCd(org.getUnifiedCode());
	// 如果查询的结果集中有数据，则返回第一条
	if(CollectionUtils.isNotEmpty(entityResults)){
	    return entityResults.get(NumberConstants.ZERO);
	}
	return null;
    }
    /**
     * 下面全部为查询.
     *
     * @param code 
     * @return 
     */
    /**
     * 以下全为查询 
     */
    /**
     * 精确查询 
     * 
     * 通过 FID 查询 对公银行账号
     * 
     * 接口名称	同步对公银行帐号信息
                    背 景	FOSS需要各营业部的对公银行帐号信息
                    接口提供者	FOSS系统	接口调用者	财务自助系统
                    交互场景	前置条件：
                    1、财务自助系统正常运行
                    
                    接口场景
                    1、财务自助系统中任何对公银行帐号信息有任何变更，把变更信息发送到FOSS系统
                    后置动作：
                    
                    接口异常处理
                    接口调用发生异常需要抛出， FOSS系统将异常信息返回给财务自助系统
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountService#queryPublicBankAccountByCode(java.lang.String)
     */
    @Override
    public PublicBankAccountEntity queryPublicBankAccountOfActiveByFid(String code) {
	if (null == code) {
	    return null;
	}
	return publicBankAccountDao.queryPublicBankAccountOfActiveByFid(code);
    }
}
