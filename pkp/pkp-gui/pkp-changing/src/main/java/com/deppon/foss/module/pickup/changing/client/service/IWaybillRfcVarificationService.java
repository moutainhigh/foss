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
 * PROJECT NAME	: pkp-changing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changing/client/service/IWaybillRfcVarificationService.java
 * 
 * FILE NAME        	: IWaybillRfcVarificationService.java
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
package com.deppon.foss.module.pickup.changing.client.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcAgentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcChangeDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcCondition;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRfcProofEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;

/**
 * 

 * 更改审核受理服务
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:kevin,date:2012-11-27 下午5:34:19</p>
 * @author foss-gengzhe
 * @date 2012-11-27 下午5:34:19
 * @since
 * @version
 */
public interface IWaybillRfcVarificationService {
   
    /**
     * 
     * <p>更改单查询</p> 
     * @author foss-gengzhe
     * @date 2012-11-27 下午9:19:32
     * @param condition
     * @return
     * @see
     */
    List<WaybillRfcChangeDto> queryWaybillRfcVarificationDto(WaybillRfcCondition condition);
    
    /**
     * 
     * <p>同意运单更改审核</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:22:25
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
    boolean agreeWaybillRfcCheck(String waybillRfcId, String notes);
    
    /**
     * 
     * <p>拒绝运单更改审核</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:23:07
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
    boolean refuseWaybillRfcCheck(String waybillRfcId, String notes);
    
    /**
     * 
     * <p>同意运单更改受理</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:24:44
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
    boolean agreeWaybillRfcOpinion(String waybillRfcId,String notes);
    
    /**
     * 
     * <p>拒绝运单更改受理</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:25:34
     * @param waybillRfcId
     * @param notes
     */
    boolean refuseWaybillRfcOpinion(String waybillRfcId,String notes);
    
    /**
     * 
     * <p>根据更改单Id查询附件列表</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:27:07
     * @param waybillRfcId
     */
    List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(String waybillRfcId);
    
    /**
     * 
     * <p>根据图片路径下载图片</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:27:45
     * @param filePath
     */
    String queryWaybillRfcProofByFilePath(String filePath);
    
	/**
	 * 新增更改单代理审核
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @return void
	 */
	void addWayBillRfcAgent(WaybillRfcAgentEntity entity);
	
	/**
	 * 根据查询条件查询审核代理信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param condition
	 * @return List<WaybillRfcAgentEntity>
	 */
	List<WaybillRfcAgentEntity> queryWaybillRfcAgent(WaybillRfcQueryAgentConDto condition);
	
	/**
	 * 修改审核代理权限
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param condition
	 */
	void updateWaybillRfcAgent(WaybillRfcAgentEntity entity);
	
	/**
	 * 
	 * <p>根据部门标杆编码查找部门人员</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午3:19:19
	 * @return
	 * @see
	 */
	List<EmployeeEntity> queryEmployeeByEntity(String unifieldCode);
	
	/**
	 * 
	 * 删除审核代理数据
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午5:15:38
	 * @param entity
	 * @see
	 */
	void deleteWayBillRfcAgent(WaybillRfcAgentEntity entity);
	
	/**
	 * 
	 * 中止审核代理
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午8:01:28
	 * @param entity
	 * @see
	 */
	void stopWayBillRfcAgent(WaybillRfcAgentEntity entity);
	
	/**
	 * 
	 * 审核和受理状态查询
	 * @author foss-gengzhe
	 * @date 2012-12-27 上午11:14:56
	 * @param condition
	 * @return
	 * @see
	 */
	List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(WaybillRfcCondition condition);
	
	/**
	 * 
	 * 根据运单ID查询运单详细信息
	 * @author foss-gengzhe
	 * @date 2013-1-6 下午12:06:25
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public WaybillEntity queryWaybillById(String waybillId);

	/**
	 * 根据更改单ID查询变更明细
	 * @author foss-shaohongliang
	 * @date 2013-6-14 下午12:06:25
	 * @param rfcId
	 * @return
	 */
	List<WaybillRfcChangeDetailEntity> getRfcChangeDetailList(String rfcId);

	/**
	 * 查询零担的更改情况
	 * @param waybillRfcCondition
	 * @param cargoPay 
	 * @return
	 */
	List<WaybillRfcChangeDto> queryWaybillRfcCargoVarificationDto(
			WaybillRfcCondition waybillRfcCondition, String cargoPay);
}