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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IWaybillRfcVarificationService.java
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
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.domain.*;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcChangeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcPrintDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillRfcQueryAgentConDto;


/**
 * 
 * (更改单的审核和受理服务接口)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:kevin,date:2012-11-27 下午4:04:07</p>
 * @author foss-gengzhe
 * @date 2012-11-27 下午4:04:07
 * @since
 * @version
 */
public interface IWaybillRfcVarificationService {
	/**
	 * 
	 * <p>
	 * 查询更改单<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-27
	 * @param condition 封装查询条件
	 * @return WaybillRfcChangeDto 查询结果对象
	 * 
	 */
    List<WaybillRfcChangeDto> queryWaybillRfcVarifyDto(WaybillRfcCondition condition);
	
	/**
	 * 
	 * <p>
	 * 依据更改单Id查询对应的凭证信息<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcId
	 * @return List<WaybillRfcProofEntity>
	 * 
	 */
    List<WaybillRfcProofEntity> queryWayBillRfcProofByRfcId(String waybillRfcId);
	
	/**
	 * 
	 * <p>
	 * 根据文件路径下载附件（图片）<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param filePath
	 * @return
	 * String
	 */
    String queryWaybillRfcProofByFilePath(String filePath);
	
	/**
	 * 
	 * <p>同意运单更改审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午8:21:04
	 * @param waybillRfcId
	 * @param notes
	 * @return boolean
	 */
    boolean agreeWaybillRfcCheck(WaybillRfcEntity waybillRfcEntity, String notes, CurrentInfo currentInfo);
	
	/**
	 * <p>
	 * 同意运单更改受理<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcId 更改单id
	 * @param notes 备注
	 * @return boolean 操作结果
	 */
    boolean agreeWaybillRfcOpinion(WaybillRfcEntity waybillRfcEntity,String notes, CurrentInfo currentInfo);
	
	/**
	 * 
	 * <p>
	 * 拒绝运单更改受理<br />
	 * </p>
	 * @author suyujun
	 * @version 0.1 2012-11-28
	 * @param waybillRfcId
	 * @param notes 备注信息
	 * @return boolean 操作结果
	 */
    boolean refuseWaybillRfcOpinion(WaybillRfcEntity waybillRfcEntity,String notes, CurrentInfo currentInfo);
	
	/**
	 * 
	 * <p>拒绝运单更改审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午8:22:02
	 * @param waybillRfcId
	 * @param notes
	 * @return boolean
	 */
    boolean refuseWaybillRfcCheck(WaybillRfcEntity waybillRfcEntity,String notes, CurrentInfo currentInfo);
	
    
	/**
	 * 
	 * 根据更改单生成操作记录实体<br />
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-6 上午11:25:52
	 */
	void addWaybillRfcActionHistory(WaybillRfcEntity rfcEntity, Date currentDate);

	/**
	 * 审核受理状态查询
	 * @author 043260-foss-suyujun
	 * @date 2012-12-7
	 * @param conditon
	 * @return void
	 * @see
	 */
	List<WaybillRfcChangeDto> queryWaybillRfcChkAndPro(WaybillRfcCondition conditon);
	
	/**
	 * 
	 * @author 043260-foss-suyujun
	 * @date 2012-12-10
	 * @return WaybillRfcPrintDto
	 * @see
	 */
	WaybillRfcPrintDto queryWaybillRfcPrintDto(String waybillNo);
	
	/**
	 * 新增更改单代理审核
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @return void
	 */
	void addWayBillRfcAgent(WaybillRfcAgentEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 新增快递更改单代理审核
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param entity
	 * @return void
	 */
	void addWayBillRfcAgentExpress(WaybillRfcAgentEntity entity, CurrentInfo currentInfo) ;
	/**
	 * 根据查询条件查询审核代理信息
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param condition
	 * @return List<WaybillRfcAgentEntity>
	 */
	List<WaybillRfcAgentEntity> queryWaybillRfcAgent(WaybillRfcQueryAgentConDto condition, CurrentInfo currentInfo);
	
	/**
	 * 修改审核代理权限
	 * @author 043260-foss-suyujun
	 * @date 2012-12-24
	 * @param condition
	 */
	void updateWaybillRfcAgent(WaybillRfcAgentEntity entity, CurrentInfo currentInfo);
	
	/**
	 * 验证是否保留历史记录
	 * @author 043260-foss-suyujun
	 * @date 2012-12-26
	 * @param entity
	 * @return boolean
	 */
	boolean isSaveAuthorizationRecords(WaybillRfcAgentEntity entity);

	/**
	 * 
	 * <p>根据部门标杆编码查找部门人员</p> 
	 * @author foss-gengzhe
	 * @date 2012-12-26 下午3:19:19
	 * @return
	 * @see
	 */
	List<EmployeeEntity> queryEmployeeByEntity(String unifieldCode,CurrentInfo currentInfo);
	
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
	 * 是否打印过到达联
	 * @return
	 * @author 026113-foss-linwensheng
	 * @date 2013-3-25 上午11:00:51
	 */
	boolean isPrintArrivesheet(String waybillNO);
	
	/**
	 * 
	 * <p>更改单自动审核</p> 
	 * @author foss-gengzhe
	 * @date 2012-11-29 下午8:29:15
	 * @param waybillRfcId
	 * @param notes
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcVarificationService#agreeWaybillRfcCheck(java.lang.String, java.lang.String)
	 */
	public boolean agreeWaybillRfcCheckAuto(WaybillRfcEntity rfcEntity, CurrentInfo currentInfo, Date currentDate);
	
	/**
	 * 
	 * 根据id查询更改单
	 * 
	 * @author wangqianjin
	 * @version 2013-04-02
	 * @param id
	 * @return WaybillRfcEntity
	 */
	public WaybillRfcEntity selectByPrimaryKey(String id);

	//DP-FOSS zhaoyiqing 343617 2016-11-07 更改单付款方式为临欠或月结，提交校验信用额度
	void getBeBebtForCUBC(WaybillEntity oldWaybill, WaybillEntity newWaybill);

	
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

	void updateWaybillRfcAgentExpress(WaybillRfcAgentEntity entity,
			CurrentInfo currentInfo);

	/**
	 * 配合快递100foss进行的轨迹推送
	 * @author 220125 yangxiaolong
	 * */
	public void addOneWaybillTrack(WaybillRfcEntity rfcEntity) ;
}