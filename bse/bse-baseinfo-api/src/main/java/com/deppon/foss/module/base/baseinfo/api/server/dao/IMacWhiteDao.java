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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/IMacWhiteDao.java
 * 
 * FILE NAME        	: IMacWhiteDao.java
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

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.MacWhiteEntity;


/**
 * MAC地址白名单DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-3-12 上午9:38:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-3-12 上午9:38:17
 * @since
 * @version
 * 新增：增，删，改，查 。 author:132599-foss-shenweihua,date:2013-4-25 上午10:31:13
 */
public interface IMacWhiteDao {
    /**
     * <p>验证MAC地址是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-3-12 上午9:36:03
     * @param mac mac地址
     * @return
     * @see
     */
    boolean queryMacAddressByMac(String mac);
    
    /**
     * <p>新增MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param entity
     * @return
     * @see
     */
    int addMacWhite(MacWhiteEntity entity);
    
    /**
     * <p>修改MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param entity
     * @return
     * @see
     */
    int updateMacWhite(MacWhiteEntity entity);
    
    /**
     * <p>作废MAC地址白名单信息</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:40:21
     * @param idList MAC地址白名单信息ID集合
     * @return
     * @see
     */
    int deleteMacWhiteById(List<String> idList);
	    

    /**
     * 根据传入对象查询符合条件所有MAC地址白名单信息
     * 
     * @author 132599-foss-shenweihua
     * @date 2013-4-25 上午10:50:21
     * @param entity
     *            MAC地址白名单信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<MacWhiteEntity> queryAllMacWhite(MacWhiteEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param entity
     *             MAC地址白名单信息实体
     * @return
     * @see
     */
    Long queryRecordCount(MacWhiteEntity entity);

}
