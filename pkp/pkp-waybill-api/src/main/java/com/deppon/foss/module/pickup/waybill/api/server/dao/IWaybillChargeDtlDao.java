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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/dao/IWaybillChargeDtlDao.java
 * 
 * FILE NAME        	: IWaybillChargeDtlDao.java
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

import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;

/**
 * 
 * 运单费用明细数据持久层
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Bobby,date:2012-10-17 下午6:03:33, </p>
 * @author Bobby
 * @date 2012-10-17 下午6:03:33
 * @since
 * @version
 */
public interface IWaybillChargeDtlDao {

    /**
     * 新增其它费用分录
     * @author 026123-foss-lifengteng
     * @date 2012-12-18 下午7:48:58
     */
    int insert(WaybillChargeDtlEntity record); 
    /**
     * 批量插入实体数据
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:42:45
     */
    int addBatch(List<WaybillChargeDtlEntity> list);

    /**
     * 根据主键查询实体对象
     * @author 026123-foss-lifengteng
     * @date 2012-12-18 下午7:49:13
     */
    WaybillChargeDtlEntity queryByPrimaryKey(String id);
    
    /**
     * 根据运单号查询对应费用明细实体List
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 下午2:52:07
     */
    List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo);

    /**
     * 根据主键更新费用信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-18 下午7:49:32
     */
    int updateByPrimaryKeySelective(WaybillChargeDtlEntity record);

    /**
     * 
     * <p>
     * 查询最新版费用明细<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-11-30
     * @param queryDto
     * @return
     * List<WaybillChargeDtlEntity>
     */
	List<WaybillChargeDtlEntity> queryNewChargeDtlEntityByNo(LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 删除费用明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * @return
	 * int
	 */
	int deleteWaybillChargeDtlEntityById(String id);
	
	/**
	 * 通过运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午2:41:46
	 */
	List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo);
	
	
	/**
	 * 激活运单明细---电子运单
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	int setWaybillChargeDtlActive(List<String> waybillNoList);
	/**
	 * 根据运单号删除费用实体
	 * 
	 */
	int deleteWaybillChargeDtlEntityByWaybillNo(String waybillNo);
}