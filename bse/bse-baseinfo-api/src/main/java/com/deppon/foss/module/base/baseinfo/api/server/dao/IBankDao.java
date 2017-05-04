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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IBankDao.java
 * 
 * FILE NAME        	: IBankDao.java
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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity;


/**
 * 银行Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-30 下午3:55:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-30 下午3:55:19
 * @since
 * @version
 */
public interface IBankDao {
    
    /**
     * 新增银行 
     * @author 094463-foss-xieyantao
     * @date 2012-10-30 下午4:01:52
     * @param entity 银行实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addBank(BankEntity entity);
    
    /**
     * 根据code作废银行 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:02:06
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteBank(String[] codes,String modifyUser);
    
    /**
     * 修改银行 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:02:06
     * @param entity 银行实体
     * @return 1：成功；-1：失败
     * @see
     */
    int updateBank(BankEntity entity);
    
    /**
     * 根据传入对象查询符合条件所有银行信息 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:02:06
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<BankEntity> queryBanks(BankEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2012-10-30 下午4:02:06
     * @param entity 银行实体
     * @return
     * @see
     */
    Long queryRecordCount(BankEntity entity);
    
    /**
     * <p>下载银行数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-6 下午2:19:28
     * @param entity
     * @return
     * @see
     */
    List<BankEntity> queryBanksForDownLoad(BankEntity entity);
    
    /** 
     * 获取最后跟新时间
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity 银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    Date getLastModifyTime();
    
    /** 
     * 数据更新
     * @author dp-yangtong
     * @date 2012-10-30 下午4:13:48
     * @param entity 银行实体
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IBankDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BankEntity)
     */
    List<BankEntity> getSyncData(Date fromDate, String userID);
    
    /**
     * <p>根据银行编码查询银行信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-2-2 上午10:34:13
     * @param bankCode 银行编码
     * @return
     * @see
     */
    BankEntity queryBankInfoByCode(String bankCode);
    /**
     * 根据名称精确查询
     * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
     * @author 268984 
     * @date 2016-4-14 下午3:54:02
     * @param bankName
     * @return
     * @see
     */
	BankEntity queryBankInfoByName(String bankName);
}
