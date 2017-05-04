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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillChargeDtlService.java
 * 
 * FILE NAME        	: IWaybillChargeDtlService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;

/**
 * 
 * 运单费用明细服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 上午10:00:44, </p>
 * @author foss-sunrui
 * @date 2012-10-31 上午10:00:44
 * @since
 * @version
 */
public interface IWaybillChargeDtlService extends IService {
	 /**
     * 新增费用明细实体
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:40:26
     */
    int insert(WaybillChargeDtlEntity record);
    
    /**
     * 批量新增费用明细实体
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:40:26
     */
    int addWaybillChargeDtlEntityBatch(List<WaybillChargeDtlEntity> waybillChargeDtlList,WaybillSystemDto systemDto );
    
    /**
     * 
     * 更改单起草后追加费用明细实体
     * @author 102246-foss-shaohongliang
     * @date 2012-12-3 下午4:08:36
     */
    int appendWaybillChargeDtlEntityBatchAfterChange(List<WaybillChargeDtlEntity> waybillChargeDtlList,WaybillSystemDto systemDto );
    /**
     * 根据id查询
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:40:26
     */
    WaybillChargeDtlEntity queryByPrimaryKey(String id);
    /**
     * 根据运单号查询
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午11:40:26
     */
    List<WaybillChargeDtlEntity> queryChargeDtlEntityByNo(String waybillNo);
    /**
     * 根据id选择性地进行更新数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(WaybillChargeDtlEntity record);

    /**
     * 
     * <p>
     *  查询最新一版本的费用明细<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-11-30
     * @param queryDto
     * @return
     * List<WaybillChargeDtlEntity>
     */
	List<WaybillChargeDtlEntity> queryNewChargeDtlEntityByNO(LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 删除费用明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * void
	 */
	int deleteWaybillChargeDtlEntityById(String id);

	/**
	 * 根据运单号查询费用明细中的其它费用
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:01:04
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
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillChargeDtlService#deleteWaybillChargeDtlEntityById(java.lang.String)
	 */
	int deleteWaybillChargeDtlEntityByWaybillNo(String waybillNo);
    
}