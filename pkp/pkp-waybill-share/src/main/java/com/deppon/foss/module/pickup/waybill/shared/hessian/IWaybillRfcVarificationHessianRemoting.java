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
 * PROJECT NAME	: pkp-waybill-share
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/shared/hessian/IWaybillRfcVarificationHessianRemoting.java
 * 
 * FILE NAME        	: IWaybillRfcVarificationHessianRemoting.java
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
package com.deppon.foss.module.pickup.waybill.shared.hessian;

import java.util.List;

import com.deppon.foss.framework.service.IHessianService;
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
 * <p>
 * 更改单 审核处理处理 远程服务接口<br />
 * </p>
 * @title IWaybillRfcVarificationHessianRemoting.java
 * @package com.deppon.foss.module.pickup.waybill.shared.hessian 
 * @author suyujun
 * @version 0.1 2012-11-27
 */

public interface IWaybillRfcVarificationHessianRemoting extends IHessianService{
    
	 /**
     * 查询更改单
     * @author 043260-foss-suyujun
     * @date 2012-12-4
     * @param condition 查询条件
     * @param 
     * @return List<WaybillRfcChangeDto> 返回列表
     */
    List<WaybillRfcChangeDto> queryWaybillRfcVarificationDto(WaybillRfcCondition condition);
    
    /**
     * 受理同意操作
     * @author 043260-foss-suyujun
     * @date 2012-12-4
     * @param waybillRfcIdl
     * @param notes
     * @return boolean
     */
    boolean agreeWaybillRfcOpinion(String waybillRfcId,String notes);
    
    /**
     * 受理拒绝操作
     * @author 043260-foss-suyujun
     * @date 2012-12-4
     * @param waybillRfcId
     * @param notes
     * @return
     * @return boolean
     */
    boolean refuseWaybillRfcOpinion(String waybillRfcId,String notes);
    
    /**
     * 根据更改单Id查询附件列表
     * @author 043260-foss-suyujun
     * @date 2012-12-4
     * @param waybillRfcId
     * @return
     * @return List<WaybillRfcProofEntity>
     */
    List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(String waybillRfcId);
    
    /**
     * 根据图片路径下载图片
     * @author 043260-foss-suyujun
     * @date 2012-12-4
     * @param filePath
     * @return
     * @return String
     */
    String queryWaybillRfcProofByFilePath(String filePath);
    
    /**
     * 
     * <p>同意运单更改审核</p> 
     * @author foss-gengzhe
     * @date 2012-12-5 下午2:13:42
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
     * @date 2012-12-5 下午2:14:15
     * @param waybillRfcId
     * @param notes
     * @return
     * @see
     */
    boolean refuseWaybillRfcCheck(String waybillRfcId, String notes);
    
    /**
     * 更改单审核受理查询
     * @author 043260-foss-suyujun
     * @date 2012-12-7
     * @param condition
     * @return
     * @return List<WaybillRfcChangeDto>
     * @see
     */
    List<WaybillRfcChangeDto> queryWaybillRfcChkAndProList(WaybillRfcCondition condition);
    
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
	 * @param waybillId
	 * @return
	 * @see
	 */
	WaybillEntity queryWaybillById(String waybillId);
	
	/**
	 * 
	 * 是否打印过到达联
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-25 上午11:00:51
	 */
	boolean isPrintArrivesheet(String waybillNO);
	
	/**
	 * 根据更改单ID查询变更明细
	 * @author foss-shaohongliang
	 * @date 2013-6-14 下午12:06:25
	 * @param rfcId
	 * @return
	 */
	List<WaybillRfcChangeDetailEntity> queryRfcChangeDetailList(String rfcId);

	List<WaybillRfcChangeDto> queryWaybillRfcCargoVarificationDto(
			WaybillRfcCondition waybillRfcCondition, String cargoPay);

	void addWayBillRfcAgentExp(WaybillRfcAgentEntity bean);

	void updateWaybillRfcAgentExp(WaybillRfcAgentEntity bean);
}