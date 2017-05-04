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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillDisDtlService.java
 * 
 * FILE NAME        	: IWaybillDisDtlService.java
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
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;

/**
 * 
 * 运单折扣费用明细服务接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:foss-sunrui,date:2012-10-31 上午10:01:58, </p>
 * @author foss-sunrui
 * @date 2012-10-31 上午10:01:58
 * @since
 * @version
 */
public interface IWaybillDisDtlService extends IService {

	/**
	 * 
	 * 插入运单折扣明细 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-24 下午10:27:16
	 */
    int insert(WaybillDisDtlEntity record);
    
    /**
     * 插入部分对象数据
     * @author WangQianJin
     * @date 2014-05-21
     */
    int insertSelective(WaybillDisDtlEntity record);
    
    /**
     * 批量增加WaybillDisDtlEntity实休数据
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 上午10:57:23
     */
    int addWaybillDisDtlEntityBatch(List<WaybillDisDtlEntity> waybillDisDtlEntity,WaybillSystemDto systemDto);
    
    /**
     * 
     * 追加更改单起草时WaybillDisDtlEntity实休数据
     * @author 102246-foss-shaohongliang
     * @date 2012-12-3 下午3:49:08
     */
    int appendWaybillDisDtlEntityBatchAfterChange(List<WaybillDisDtlEntity> waybillDisDtlEntity, WaybillSystemDto systemDto);

    
    /**
     * 根据主键ID查询运单折扣明细
     * @author 026123-foss-lifengteng
     * @date 2012-12-24 下午10:27:50
     */
    WaybillDisDtlEntity queryByPrimaryKey(String id);
    
    /**
     * 根据运单号查询WaybillDisDtlEntity列表
     * @author 026123-foss-lifengteng
     * @date 2012-11-5 下午4:11:56
     */
    List<WaybillDisDtlEntity> queryDisDtlEntityByNo(String waybillNo);

    
    /**
     * 选择性更新运单折扣明细信息
     * @author 026123-foss-lifengteng
     * @date 2012-12-24 下午10:28:33
     */
    int updateByPrimaryKeySelective(WaybillDisDtlEntity record);

    /**
     * 
     * <p>
     * 运单同一运单号最新折扣明细<br />
     * </p>
     * @author suyujun
     * @version 0.1 2012-11-30
     * @param queryDto
     * @return
     * List<WaybillDisDtlEntity>
     */
	List<WaybillDisDtlEntity> queryNewDisDtlEntityByNo(LastWaybillRfcQueryDto queryDto);

	/**
	 * 
	 * <p>
	 * 删除折扣明细<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-12-3
	 * @param id
	 * void
	 */
	int deleteWaybillDisDtlEntityById(String id);
	
	/**
	 * 根据运单号和类型查询CRM营销活动
	 * @创建时间 2014-4-22 下午8:34:33   
	 * @创建人： WangQianJin
	 */
	WaybillDisDtlEntity queryActiveInfoByNoAndType(String waybillNo);
	
	/**
	 * 根据运单号与类型修改折扣信息状态
	 * @创建时间 2014-5-10 下午6:24:22   
	 * @创建人： WangQianJin
	 */
	int updateByWaybillNoAndType(WaybillDisDtlEntity record);
    
	/**
	 * 激活运单折扣费用明细
	 * @author liyongfei--DMANA-2035
	 * @param waybillNoList
	 * @return
	 */
	int setWaybillDisDtlActive(List<String> waybillNoList);

	int deleteWaybillDisDtlEntityByWaybillNo(String waybillNo);
}