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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILineItemService.java
 * 
 * FILE NAME        	: ILineItemService.java
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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;


/**
 * 线段服务类
 * @author foss-zhujunyong
 * @date Oct 26, 2012 3:16:22 PM
 * @version 1.0
 */
public interface ILineItemService extends IService{
    /**
     * 
     * <p>添加线段</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:21:03 PM
     * @param lineItem
     * @return
     * @see
     */
    LineItemEntity addLineItem(LineItemEntity lineItem);

    /**
     * 
     * <p>作废线段</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:21:15 PM
     * @param lineItem
     * @return
     * @see
     */
    LineItemEntity deleteLineItem(LineItemEntity lineItem);

    /**
     * 
     * <p>更新线段</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:21:27 PM
     * @param lineItem
     * @return
     * @see
     */
    LineItemEntity updateLineItem(LineItemEntity lineItem);
    
    /**
     * 
     * <p>查询线段详情</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:21:40 PM
     * @param id
     * @return
     * @see
     */
    //LineItemEntity queryLineItemById(String id);
    
    /**
     * 
     * <p>查询特定线路下的线段详情</p> 
     * @author foss-zhujunyong
     * @date Oct 26, 2012 3:21:58 PM
     * @param lineVirtualCode
     * @return
     * @see
     */
    List<LineItemEntity> queryLineItemListByLineVirtualCode(String lineVirtualCode);

    /**
     * 
     * <p>查询特定线路下的线段详情,不包括冗余属性</p> 
     * @author foss-zhujunyong
     * @date Mar 25, 2013 11:03:31 AM
     * @param lineVirtualCode
     * @return
     * @see
     */
    List<LineItemEntity> querySimpleLineItemListByLineVirtualCode(String lineVirtualCode);
    
    /**
     * 
     * <p>通过虚拟编码查询线段实体</p> 
     * @author foss-zhujunyong
     * @date Mar 14, 2013 1:38:40 PM
     * @param virtualCode
     * @return
     * @see
     */
    LineItemEntity querySimpleLineItemByVirtualCode(String virtualCode);
    
    /**
     * 
     * <p>查询符合条件的线段</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 3:13:56 PM
     * @param orginalOrganizationCode
     * @return
     * @see
     */
    List<LineItemEntity> queryLineItemListByCondition(LineItemEntity lineItem);
    
    /**
     * 
     * <p>查询符合条件的线段, 不包括冗余信息</p> 
     * @author foss-zhujunyong
     * @date Mar 25, 2013 3:23:57 PM
     * @param lineItem
     * @return
     * @see
     */
    List<LineItemEntity> querySimpleLineItemListByCondition (LineItemEntity lineItem);
    /**
     * 
     * <p>根据出发部门编码，到达部门编码查询相关线路的虚拟编码列表</p> 
     * @author foss-zhujunyong
     * @date Nov 9, 2012 3:47:14 PM
     * @param sourceCode
     * @param targetCode
     * @return
     * @see
     */
    List<String> queryLineVirtualCodeListBySourceTarget(String sourceCode, String targetCode);
    
    /**
     * 
     * <p>根据线路作废线段</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 3:44:18 PM
     * @param lineVirtualCode
     * @param modifyUser
     * @return
     * @see
     */
    int deleteLineItemByLine(String lineVirtualCode, String modifyUser);

    /**
     * 
     * <p>找出所有符合条件的线段所对应线路的虚拟编码列表</p> 
     * @author foss-zhujunyong
     * @date Apr 10, 2013 10:22:56 AM
     * @param entity
     * @return
     * @see
     */
    List<String> queryLineVirtualCodeListByCondition(LineItemEntity entity);
    
    
    /**
     * 
     * <p>刷新缓存</p> 
     * @author foss-zhujunyong
     * @date Jan 8, 2013 9:38:01 AM
     * @return
     * @see
     */
    void invalidList(String key);
    

}
