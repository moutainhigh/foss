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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/api/server/service/IPdaScanToFossService.java
 * 
 * FILE NAME        	: IPdaScanToFossService.java
 * 
 * AUTHOR			: 菜鸟项目--电子运单
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: pkp-waybill-api
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.api.server.service
 * FILE    NAME: IPdaScanToFossService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PdaScanEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PdaScanQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;



/**
 * PDA扫描信息处理相关服务接口
 * @author 200972  lanhuilan
 * @date 2015-01=30 下午8:58:38
 */
public interface IPdaScanService extends IService {
	/**
	 * 保存PDA扫描任务反馈的信息
	 * @author 200972 lanhuilan 
	 * @date 2015-01-30  下午10:02:53
	 */
	void savePdaScanInfo(PdaScanQueryDto pdaScanInfo);
	
	/**
	 * 完成接货并激活运单
	 * @author 200972 lanhuilan 
	 * @date 2015-02-03  下午10:02:53
	 */
	void overPickup(String taskId);

	/**
	 * 随机获取一个收货部门
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-5-23 11:01:35
	 * @param expressPartOrgCode
	 * @return
	 */
	String getRandomReceiveOrgCodeByExpressPartOrgCode(String expressPartOrgCode);

	/**
	 * 根据扫描记录插入对应的货签信息数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-7-18 11:28:33
	 */
	int addLabelGoodsInfo(WaybillEntity waybillEntity, WaybillSystemDto systemDto);
	
	/**
	 * 查询是否存在已经卸车的记录,如果存在，需要进行入库
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-7-12 10:22:58
	 * @param waybillNo
	 */
	void waybillInStockByWaybillNo(String waybillNo);
	
	/**
	 * 添加PDA货签信息表
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-7-25 10:07:46
	 * @param waybillEntity
	 * @return
	 */
	int addLabelGoodPda(WaybillEntity waybillEntity);

	/**
	 * 获取到对应的开单部门
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-10 11:56:46
	 * @param driverCode
	 * @return
	 */
	String getRadomCreateOrgCodeByDriverCode(String driverCode);
	
	/**
	 * 查询有效的PDA扫描信息
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-27 09:52:44
	 */
	List<PdaScanEntity> queryScanInforBySecondCondition(PdaScanQueryDto pdaScanQueryDto);

	/**
	 * 查询数据是否数据在子母件数据里面
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-22 14:03:16
	 * @param pdaScanQueryDto
	 * @return
	 */
	List<PdaScanEntity> queryPdaScanInfoWaybillRelateByCondition(PdaScanQueryDto pdaScanQueryDto);
}