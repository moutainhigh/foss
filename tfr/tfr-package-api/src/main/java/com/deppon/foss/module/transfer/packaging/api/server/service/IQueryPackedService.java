/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/server/service/IQueryPackedService.java
 *  
 *  FILE NAME          :IQueryPackedService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.server.service
 * FILE    NAME: IQueryPackedService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.server.service;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackedPersonEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedConditionEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.QueryPackedResultEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.SerialRelationEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.CurrentDeptDto;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.QueryPackedDto;

/**
 * 包装录入的service
 * @author 046130-foss-xuduowei
 * @date 2012-10-17 上午8:13:50
 */
public interface IQueryPackedService extends IService {
	/**
	 * 
	 * 根据查询条件查询本部门包装的货物信息，并做分页处理
	 * @param queryPackedConditionEntity 查询条件
	 * @param limit 每页限制个数
	 * @param start 每页开始数
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-17 上午8:22:47
	 */
	List<QueryPackedResultEntity> queryPackedAll(QueryPackedConditionEntity queryPackedConditionEntity,int limit,int start);
	
	/**
	 * 分页总数
	 * @param queryPackedConditionEntity 查询条件
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-12 下午5:17:54
	 */
	Long queryTotalCount(QueryPackedConditionEntity queryPackedConditionEntity);

	/**
	 * 
	 * 获取最大流水号
	 * @param serialNo 流水号
	 * @param interval 递增公差
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-17 上午8:23:02
	 */
	String obtainMaxSerialNo(String serialNo,int interval);
	/**
	 * 
	 * 根据运单号查询包装录入信息
	 * @param 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-17 上午8:23:10
	 */
	QueryPackedDto queryWaybillPack(String waybillno,String packageType);
	/**
	 * 
	 * 新增或修改包装录入信息
	 * @param waybillPackEntity 包装录入主信息列表
	 * @param serialRelationList 新旧流水号关系列表
	 * @param newSerialList 新流水号列表
	 * @param packedPersonList 包装人列表
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-17 上午8:23:34
	 */
	int addPackageInfo(WaybillPackEntity waybillPackEntity,List<SerialRelationEntity> serialRelationList,
			List<SerialRelationEntity> newSerialList,List<PackedPersonEntity> packedPersonList);
	/**
	 * 
	 * 根据运单号查询新流水号
	 * @param waybillNo 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-17 上午8:23:43
	 */
	QueryPackedDto queryNewSerialNo(String waybillNo);
	/**
	 * 
	 * 打印新流水号
	 * @param waybillNo 运单号
	 * @param serialRelationList 需要打印的流水号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-7 上午8:47:05
	 */
	List<BarcodePrintLabelDto> printNewSerialNo(String waybillNo,List<SerialRelationEntity> serialRelationList);
	/**
	 * 
	 * 包装的PC端库存处理
	 * @param inStockSerialRelationSet 入库集合
	 * @param outStockSerialRelationSet 出库集合
	 * @param waybillNo 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-7 上午8:40:00
	 */
	int handleSerialStock(Set<String> inStockSerialRelationSet,
			Set<String> outStockSerialRelationSet, String waybillNo);
	
	/**
	 * 
	 * 获取当前登录人组织信息
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-26 下午8:01:34
	 */
	CurrentDeptDto queryCurrentDept();
	/**
	 * 
	 * 输出代包装信息导出流
	 * @author 046130-foss-xuduowei
	 * @date 2013-03-22 下午8:01:34
	 */
	InputStream exportExcelStream(QueryPackedConditionEntity queryPackedConditionEntity);
	/**
	 * 
	 * 将成功打印的流水号记录到数据库中
	 * @author 046130-foss-xuduowei
	 * @date 2013-06-29 下午8:01:34
	 */
	 int insertLabelRecord(String waybillNo,String serialNo);
	/**
	 * 还原
	 * 删除实际包装记录
	 * 
	 * @param waybillno
	 * @param packageType
	 */
	int deleteWaybillPack(String waybillno,String packageType);
	/**
	 * @author niuly
	 * @date 2014-1-20上午8:48:12
	 * @function 根据查询条件查询本部门包装的货物信息（为接送货提供的接口）
	 * @param queryPackedConditionEntity
	 * @return
	 */
	List<QueryPackedResultEntity> queryPackedAllInfo(QueryPackedConditionEntity queryPackedConditionEntity);
	
}