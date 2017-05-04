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
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/service/ICertificateBagService.java
 *  
 *  FILE NAME          :ICertificateBagService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagQueryEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.ConfigOrgRelationEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagReturnDto;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagTakeDto;

public interface ICertificateBagService extends IService {
	/**
	 * 分页查询证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:00:26
	 */
	List<CertificatebagEntity> queryCertificateBagList(	CertificatebagDto certificatebagDto, int start, int limit);

	/**
	 * 证件包总数
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:00:26
	 */
	Long queryCount(CertificatebagDto certificatebagDto);

	/**
	 * 显示证件包明细
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:10:24
	 */
	List<CertificatebagEntity> displayCertificateBagDetail(
			CertificatebagDto certificatebagEntity);

	/**
	 * 归还证件包
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:05:15
	 */
	int updateCertificateBagStatus(CertificatebagReturnDto returnDto);

	/**
	 * 领取证件包
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	int takeCertificateBag(CertificatebagTakeDto certificatebagTakeDto);

	/**
	 * 根据关联refid找到领取证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	List<CertificatebagQueryEntity> queryTakeRefId(
			CertificatebagDto certificatebagDto);

	/**
	 * 根据关联refid找到归还证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	List<CertificatebagQueryEntity> queryReturnByRefId(
			CertificatebagDto certificatebagDto);

	/** 
	* @Title: convertVehicleCode2Name 
	* @Description: 将英文车牌号转换为中文(例:YUE-X-20000------->粤X20000)
	* @param vehicleNo
	* @return  设定文件 
	* @return String    返回类型 
	* @see convertVehicleCode2Name
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-9 下午2:40:10   
	* @throws 
	*/ 
	String convertVehicleCode2Name(String vehicleNo);

	/** 
	* @Title: getContainerTakenInfo 
	* @Description: 根据货柜号获取被领取的货柜信息
	* @param certificatebagDto
	* @return  设定文件 
	* @return CertificatebagDto    返回类型 
	* @see getContainerTakenInfo
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-9 下午6:39:16   
	* @throws 
	*/ 
	CertificatebagDto getContainerTakenInfo(CertificatebagDto certificatebagDto);

	/**
	 * 
	* @Title: queryContainerCardItemsByOrgCode 
	* @Description: 查询当前部门的车头柜信息
	* @param confType
	* @return  设定文件 
	* @return List<ConfigOrgRelationEntity>    返回类型 
	* @see queryContainerCardItemsByOrgCode
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-4-13 上午11:37:30   
	* @throws
	 */
	List<ConfigOrgRelationEntity> queryConfigItemsByConfType(String confType) throws TfrBusinessException;

	/** 
	* @Title: convertVehicleCode2NameForQuery 
	* @Description: 转换证件包车牌号
	* @param vehicleNo
	* @return  设定文件 
	* @return String    返回类型 
	* @see convertVehicleCode2NameForQuery
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午2:54:39   
	* @throws 
	*/ 
	String convertVehicleCode2NameForQuery(String vehicleNo);
}