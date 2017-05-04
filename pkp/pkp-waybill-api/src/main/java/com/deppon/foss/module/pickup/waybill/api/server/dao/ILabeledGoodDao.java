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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/ILabeledGoodDao.java
 * 
 * FILE NAME        	: ILabeledGoodDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.LabeledGoodEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabeledGoodDto;

/**
 * 
 * 货签信息数据持久层接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-30 下午3:10:53, </p>
 * @author foss-sunrui
 * @date 2012-10-30 下午3:10:53
 * @since
 * @version
 */
public interface ILabeledGoodDao {

    /**
     * 
     * <p>按需插入一条数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:29
     * @param record
     * @return
     * @see
     */
    int insertSelective(LabeledGoodEntity record);
    
    /**
     * 
     * <p>批量插入</p> 
     * @author foss-sunrui
     * @date 2012-11-6 下午4:53:14
     * @param record
     * @return
     * @see
     */
    int insertBatch(List<LabeledGoodEntity> list);

    /**
     * 
     * <p>通过主键查询</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:32
     * @param id
     * @return
     * @see
     */
    LabeledGoodEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过运单号和流水号查询</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:32
     * @param labeledGoodDto
     * @param isActive 查询有效数据还是无效数据
     * @return
     * @see
     */
    LabeledGoodEntity queryByWaybillNoAndSerialNo(LabeledGoodDto labeledGoodDto, boolean isActive);
    
    /**
     * 
     * <p>通过运单号查询最大的序列号</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    LabeledGoodEntity queryLastSerialByWaybillNo(String waybillNo);

    /**
     * 
     * <p>按主键更新数据</p> 
     * @author foss-sunrui
     * @date 2012-10-30 下午3:12:36
     * @param record
     * @return
     * @see
     */
    int updateByPrimaryKeySelective(LabeledGoodEntity record);
    
    /**
     * 
     * <p>
     * 通过运单号查询所有流水号
     * </p>
     * 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    List<LabeledGoodEntity> queryAllSerialByWaybillNo(String waybillNo) ;
    
    
    /**
     * 
     * <p>
     * 通过运单号查询所有激活流水号
     * </p>
     * 
     * @author foss-jiangfei
     * @date 2012-10-30 下午4:55:34
     * @param waybillNo
     * @return
     * @see
     */
    List<LabeledGoodEntity> queryActiveSerialNoBywaybillNo(String waybillNo);
    
    /**
     * 删除货签
     * 不是真正的删除 是设置 active 为N
     * @param waybillNo
     * @param serialNo
     * @return
     */
    int deleteGoodEntity(LabeledGoodEntity record); 
    
    /**
     * 删除货签
     * 不是真正的删除 是设置 active 为N
     * @param waybillNo
     * @param serialNo
     * @return
     */
    int deleteGoodEntityBatch(List<LabeledGoodEntity> records);

    /**
     * 
     * 更新流水号中的运单号
     * @author 102246-foss-shaohongliang
     * @date 2013-3-18 下午8:12:59
     */
	void modifyWaybillNo(String oldWaybillNo, String newWaybillNo); 
	
	/**
	 * 通过运单号和流水号查询(为中转提供)
	 * @author WangQianJin
	 * @date 2013-6-20 下午3:50:41
	 */
	List<LabeledGoodEntity> queryLabeledGoodByWaybillNoAndSerialNo(String waybillNo, String serialNo);

	/**
	 * @function 根据单号查询所有已经作废的流水号信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2013-11-6 10:05:16
	 */
	List<LabeledGoodEntity> queryLabelGoodStatusisNByWaybillNo(
			String waybillNo, String status);
	
	/**
	 * 根据运单号删除流水号信息
	 * @remark 
	 * @author WangQianJin
	 * @date 2014-1-21 上午9:23:50
	 */
	int deleteLabeledGoodByWaybillNo(String waybillNo);

	/**
	 * 根据运单号，流水号集合，流水号状态查询详情
	 * @param waybillNo
	 * @param serialNoList
	 * @param yes
	 * @return
	 */
	List<LabeledGoodEntity> queryLabeledGoodByWaybillNoWithSerial(
			String waybillNo, List<String> serialNoList, String active);

	int insertBatchLabeledGoods(List<LabeledGoodEntity> list);

	void deleteLabByWaybillNos(List<String> waybillNos);

	void deleteLabByWaybillNo(String waybillNo);
}