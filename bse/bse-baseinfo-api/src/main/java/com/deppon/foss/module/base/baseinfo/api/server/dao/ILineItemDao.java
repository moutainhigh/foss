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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ILineItemDao.java
 * 
 * FILE NAME        	: ILineItemDao.java
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

import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LineItemEntity;


/**
 * 线段Dao
 * @author foss-zhujunyong
 * @date Oct 25, 2012 3:59:46 PM
 * @version 1.0
 */
public interface ILineItemDao {

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
    LineItemEntity queryLineItemById(String id);
    
    /**
     * 
     * <p>根据虚拟编码查询线段</p> 
     * @author foss-zhujunyong
     * @date Mar 14, 2013 1:32:16 PM
     * @param virtualCode
     * @return
     * @see
     */
    LineItemEntity queryLineItemByVirtualCode(String virtualCode);    
    
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
     * <p>查询符合条件的线段</p> 
     * @author foss-zhujunyong
     * @date Nov 5, 2012 3:13:56 PM
     * @param orginalOrganizationCode
     * @return
     * @see
     */
    List<LineItemEntity> queryLineItemListByCondition (LineItemEntity lineItem);
    
    /**
     * 
     * <p>根据线路作废线段</p> 
     * @author foss-zhujunyong
     * @date Nov 12, 2012 3:40:52 PM
     * @param lineItem
     * @return
     * @see
     */
    int deleteLineItemByLine(String lineVirtualCode, String modifyUser);
 
    /**
     * 
     * <p>下载线段</p> 
     * @author foss-zhujunyong
     * @date Jan 15, 2013 2:43:15 PM
     * @param lineItem
     * @return
     * @see
     */
    List<LineItemEntity> queryLineItemListForDownload(LineItemEntity lineItem);

    /**
     * 
     * <p>取最后修改时间</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:37:04 PM
     * @return
     * @see
     */
    Date queryLastModifyTime();    
    
    /**
     * 
     * <p>取所有激活的线段进入缓存</p> 
     * @author foss-zhujunyong
     * @date Dec 17, 2012 7:44:09 PM
     * @return
     * @see
     */
    List<LineItemEntity> queryLineItemListForCache();
    
    /**
     * 
     * <p>更新线路中的冗余字段：普车时效和卡车时效</p> 
     * @author foss-qirongsheng
     * @return 
     * @date Mar 23, 2016 4:02:57 PM
     * @see
     */
    LineEntity updateLineAging(LineItemEntity lineItem);
}
