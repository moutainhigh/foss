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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/server/dao/ICertificateBagDao.java
 *  
 *  FILE NAME          :ICertificateBagDao.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagQueryEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagReturnEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.CertificatebagTakeEntity;
import com.deppon.foss.module.transfer.management.api.shared.domain.TruckTaskEntity;
import com.deppon.foss.module.transfer.management.api.shared.dto.CertificatebagDto;

/**
 *  证件包Dao接口
 * 
 * @author dp-liming
 * @date 2012-11-2 上午10:50:47
 */
public interface ICertificateBagDao {

	/**
	 * 新增证件包ACTION
	 * 
	 * @author dp-liming
	 * @date 2012-11-5 上午11:00:26
	 */
	int addCertificateBagAction(CertificatebagEntity certificatebagEntity);
	
	/**
	 * 修改证件包ACTION
	 * 
	 * @author dp-liming
	 * @date 2012-11-5 上午11:00:26
	 */
	int updateCertificateBagAction(CertificatebagEntity certificatebagEntity);


	/**
	 * 分页查询证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:00:26
	 */
	List<CertificatebagEntity> queryCertificateBagList(
			CertificatebagDto certificatebagDto, int start, int limit);

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
	List<CertificatebagEntity> displayCertificateBagDetail(CertificatebagDto certificatebagEntity);

	/**
	 * 归还证件包
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:05:15
	 */
	int updateCertificateBagStatus(List<CertificatebagReturnEntity> certificatebagReturnList);

	/**
	 * 检查是否有长途发车计划
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:23:26
	 */
	List<TruckTaskEntity> verifyTruckDepartPlan(String vehicleNo);

	/**
	 * 绑定车辆
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:23:26
	 */
	int addTruckTask(TruckTaskEntity truckTaskEntity);

	/**
	 * 领取证件包
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	int takeCertificateBag(List<CertificatebagTakeEntity> certificatebagTakeList);

	/**
	 * 根据车牌号找到领取证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	CertificatebagEntity queryCertificateBagById(String id);

	/**
	 * 根据关联refid找到领取证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	List<CertificatebagQueryEntity> queryTakeRefId(CertificatebagDto certificatebagDto);

	/**
	 * 根据关联refid找到归还证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	List<CertificatebagQueryEntity> queryReturnByRefId(CertificatebagDto certificatebagDto);
	
	/**
	 * 根据 任务车辆ID找到证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	CertificatebagEntity queryByTruckTaskId(String truckTaskId);
	
	
	/**
	 * 查询已被领取的证件包信息
	 * 
	 * @author dp-liming
	 * @date 2012-11-2 上午11:36:10
	 */
	CertificatebagEntity queryCertificatebag(CertificatebagDto certificatebagDto);

	/** 
	* @Title: isOwnTruck 
	* @Description:查询是否是公司车
	* @param newVehicleNo
	* @return  设定文件 
	* @return int    返回类型 
	* @see isOwnTruck
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午1:50:48   
	* @throws 
	*/ 
	int isOwnTruck(String newVehicleNo);

	/** 
	* @Title: isLeasedTruck 
	* @Description: 查看是否是外请车
	* @param newVehicleNo
	* @return  设定文件 
	* @return int    返回类型 
	* @see isLeasedTruck
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午1:51:10   
	* @throws 
	*/ 
	int isLeasedTruck(String newVehicleNo);

	/** 
	* @Title: isOwnTruckWithoutActive 
	* @Description: 查询公司内部车牌号是否使用过
	* @param newVehicleNo
	* @return  设定文件 
	* @return int    返回类型 
	* @see isOwnTruckWithoutActive
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午3:07:20   
	* @throws 
	*/ 
	int isOwnTruckWithoutActive(String newVehicleNo);

	/** 
	* @Title: isLeasedTruckWithoutActive 
	* @Description: 查询外请车是否使用过
	* @param newVehicleNo
	* @return  设定文件 
	* @return int    返回类型 
	* @see isLeasedTruckWithoutActive
	* @author: liangfuxiang liangfux@cn.ibm.com  
	* @version: 2013-5-17 下午3:07:46   
	* @throws 
	*/ 
	int isLeasedTruckWithoutActive(String newVehicleNo);
	
}