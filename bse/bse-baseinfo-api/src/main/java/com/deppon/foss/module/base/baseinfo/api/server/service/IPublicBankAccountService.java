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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/IPublicBankAccountService.java
 * 
 * FILE NAME        	: IPublicBankAccountService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PublicBankAccountEntity;
/**
 * 对公银行账号 Service接口
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-10 下午8:4:19
 */
public interface IPublicBankAccountService extends IService {
    /**
     * 插入 对公银行账号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    PublicBankAccountEntity addPublicBankAccount(PublicBankAccountEntity entity);
	
    /**
     * 根据FID删除 对公银行账号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    PublicBankAccountEntity deletePublicBankAccount(PublicBankAccountEntity entity);
	
    /**
     * 根据FID批量删除 对公银行账号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    PublicBankAccountEntity deletePublicBankAccountMore(String[] codes , String deleteUser);
	
    /**
     * 更新  对公银行账号,
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    PublicBankAccountEntity updatePublicBankAccount(PublicBankAccountEntity entity);
	
	
	
    /**
     * 以下全为查询 
     */
	
    /**
     * 根据编码查询 对公银行账号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    PublicBankAccountEntity queryPublicBankAccountByFid(String code);	
	
	
    /**
     * 精确查询 对公银行账号,
    
     * 根据多个编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountByFid(java.lang.String)
     */
    List<PublicBankAccountEntity> queryPublicBankAccountBatchByFid(String[] codes);
    
    /**
     * 精确查询 对公银行账号,
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    List<PublicBankAccountEntity> queryPublicBankAccountExactByEntity(
	    PublicBankAccountEntity entity, int start, int limit) ;
    
    /**
     * 精确查询-查询总条数，用于分页
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    long queryPublicBankAccountExactByEntityCount(PublicBankAccountEntity entity);
	
    /**
     * 精确查询 对公银行账号,返回所有的结果，
    
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    List<PublicBankAccountEntity> queryPublicBankAccountExactByEntityAll(
	    PublicBankAccountEntity entity) ;
    
    /**
     * 根据entity模糊查询 对公银行账号
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    List<PublicBankAccountEntity> queryPublicBankAccountByEntity(
	    PublicBankAccountEntity entity,int start, int limit);
	
    /**
     * 查询queryPublicBankAccountByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    long queryPublicBankAccountByEntityCount(PublicBankAccountEntity entity);
    
    /**
     * 根据entity模糊查询 对公银行账号,返回所有的结果，
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:19
     */
    List<PublicBankAccountEntity> queryPublicBankAccountByEntityAll(
	    PublicBankAccountEntity entity);
    
    /**
     * 下面是特殊方法
     */
    

    /**
     * 根据部门标杆编码deptCd 精确查询 对公银行账号
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     */
    List<PublicBankAccountEntity> queryPublicBankAccountByDeptCd(String deptCd);


    /**
     * 根据部门编码code 精确查询 对公银行账号
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:34
     */
    PublicBankAccountEntity queryPublicBankAccountOneByDeptCode(String code);
		
    
    /**
     * 下面是特殊查询
     */
    
    /**
     * 通过 标识编码FID查询所有ACTIVE有效的（正常和销户的）
     * 
     * @author 087584-foss-lijun
     * @date 2012-12-10 下午8:4:6
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IPublicBankAccountDao#queryPublicBankAccountActiveByFid(java.lang.String)
     */
    PublicBankAccountEntity queryPublicBankAccountOfActiveByFid(String code) ;
	
}
