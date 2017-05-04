/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-waybill-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IPrintInfoDao.java
 * 
 * FILE NAME        	: IPrintInfoDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
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
package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.domain.PrintInfoEntity;

/**
 * 
 * 运单打印信息数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-12-5 下午4:52:57,= </p>
 * @author foss-sunrui
 * @date 2012-12-5 下午4:52:57
 * @since
 * @version
 */
public interface IPrintInfoDao {
	
	/**
	 * 
	 * <p>创建新纪录</p> 
	 * @author foss-sunrui
	 * @date 2012-12-5 下午4:53:26
	 * @param record
	 * @return
	 * @see
	 */
    int insertSelective(PrintInfoEntity record);

    /**
     * 
     * <p>通过运单编号查询</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午4:53:42
     * @param waybillId
     * @return
     * @see
     */
    List<PrintInfoEntity> queryByWaybillId(String waybillId,String waybillNo);
    
    /**
     * 
     * <p>通过更改单编号查询</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午4:53:42
     * @param waybillId
     * @return
     * @see
     */
    List<PrintInfoEntity> queryByWaybillRFCId(String waybillRFCId,String waybillNo);
    
    /**
     * 
     * <p>通过运单编号获取该运单纪录的打印次数</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午5:11:20
     * @param waybillId
     * @return
     * @see
     */
    int queryPrintTimesByWaybillId(String waybillId,String waybillNo);
    
    /**
     * 
     * <p>通过更改单编号获取该更改单纪录的打印次数</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午5:11:55
     * @param waybillRFCId
     * @return
     * @see
     */
    int queryPrintTimesByWaybillRFCId(String waybillRFCId,String waybillNo);

    /**
     * 
     * <p>更新记录</p> 
     * @author foss-sunrui
     * @date 2012-12-5 下午4:54:16
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(PrintInfoEntity record);
    /**
	 * 查询在线运单打印次数
	 * 
	 * @author foss-zhuxue
	 * @date 2016-10-13 
	 * @param waybillNo
	 * @return
	 * @see
	 */
    int queryPrintTimesByWaybill(String waybillNo);
    /**
     * 更新运单打印
     * zhuxue
     * @param record
     * @return
     */
	int updatePrintJudgeSelective(PrintInfoEntity record);
    
}