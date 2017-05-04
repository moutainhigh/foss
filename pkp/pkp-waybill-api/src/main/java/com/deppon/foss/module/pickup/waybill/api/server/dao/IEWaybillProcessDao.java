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
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillProcessEntity;

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
public interface IEWaybillProcessDao {
	
	/**
	 * 
	 * <p>新增待激活运单数据</p> 
	 * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
	 */
   public  int insert(EWaybillProcessEntity entity);

    /**
     * 
     * <p>通过设置的JOBID查询待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
    public List<EWaybillProcessEntity> queryAllByCommon(String jobId);
    
    /**
     * 
     * <p>更新一批待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
    public int updateForJob(Map<String,Object> map);
    
    /**
     * 
     * <p>激活完成后删除待激活运单数据</p> 
     * @author FOSS-YANGBIN
     * @date 2015-01-30 
     * @param waybillNo
     * @return integer
     * @see
     */
    public int deleteByWaybillNo(String waybillNo);

    /**
     * 根据条件查询对应的条件个数
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-17 20:37:34
     * @param maps
     * @return
     */
	public List<EWaybillProcessEntity> queryEWaybillByCondition(Map<String, Object> maps);

    /**
     * 根据运单号更新对应数据
     * @author Foss-105888-Zhangxingwang
     * @date 2015-3-18 10:44:33
     * @param entity
     * @return
     */
	int updateEWaybillProcessByWaybillNo(EWaybillProcessEntity entity);
	/**
	 * <p>激活线程处理，线程池满数据回滚操作，将pkp.t_srv_ewaybil_process jobId 置为N/A,状态置为N待于再执行一次激活后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-14 下午5:10:51
	 * @param jobId
	 * @return
	 * @see
	 */
	int updateProcessJobIdByIdForActive(String jobId);
}