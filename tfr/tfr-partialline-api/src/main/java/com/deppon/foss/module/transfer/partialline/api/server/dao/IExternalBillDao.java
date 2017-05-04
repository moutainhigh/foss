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
 *  
 *  PROJECT NAME  : tfr-partialline-api
 *  
 *  PACKAGE NAME  : 
 * 
 *  DESCRIPTION   : 偏线外发管理
 *  
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/dao/IExternalBillDao.java
 * 
 *  FILE NAME     :IExternalBillDao.java
 *  
 *  AUTHOR        : FOSS中转开发组
 *  
 *  TIME          : 
 *  
 *  HOME PAGE     :  http://www.deppon.com
 *  
 *  COPYRIGHT     : Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 *  VERSION       :0.1
 * 
 *  LAST MODIFY TIME:
 ******************************************************************************/
package com.deppon.foss.module.transfer.partialline.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.partialline.api.shared.domain.ExternalBillEntity;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillWayBillInfoDto;

/**
 * 偏线Dao接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:17:45
 */
public interface IExternalBillDao {

	/**
	 * 插入新的纪录
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:04:53
	 */
	int insert(ExternalBillDto record);

	/**
	 * 根据主键查询记录
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:05:18
	 */
	ExternalBillEntity queryByPrimaryKey(String id);

	/**
	 * 更新记录
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:05:51
	 */
	int updateByPrimaryKey(ExternalBillDto record);

	/**
	 * 查询偏线列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:03:45
	 */
	List<ExternalBillDto> selectByParams(ExternalBillDto dto, int limit, int start);

	/**
	 * 根据运单号查询，被录入的外发单列表
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-23 上午11:36:12
	 */
	List<ExternalBillDto> queryByWaybillNo(ExternalBillDto dto);

	/**
	 * 根据运单号查询,特定时间之后的被录入的外发单列表
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-24 上午10:25:49
	 */
	Long queryByWaybillNoAndRegisterTime(ExternalBillDto dto);

	/**
	 * 根据外发单号及偏线代理编号 查询,非作废 的外发单列表
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-23 下午1:47:31
	 */
	List<ExternalBillDto> queryByExternalBillNo(ExternalBillDto dto);

	/**
	 * 获取总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 下午2:31:28
	 */
	Long queryCount(ExternalBillDto dto);

	/**
	 * 查询运单号是否存在未录入的交接单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-16 上午11:29:29
	 */
	Long queryHandedUninputWaybill(ExternalBillDto dto);

	/**
	 * 根据查询条件及sql查询相应的条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 下午2:31:28
	 */
	Long queryCount(ExternalBillDto dto, String mapperSqlId);

	/**
	 * 根据ID 更新审核状态
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-20 下午12:17:08
	 */
	void updateAuditStatusByPrimaryKey(List<String> auditIds, String auditStatus);

	/**
	 * 根据主键列表查询偏线外发单列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-25 下午3:08:15
	 */
	List<ExternalBillDto> selectExternalBillByPrimaryKeys(List<String> externalBillIds);

	/**
	 * 根据运单号查询运单信息
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-25 下午5:23:23
	 */
	ExternalBillWayBillInfoDto selectWayBillByWaybillNo(ExternalBillDto dto);

	/**
	 * 根据运单号查询偏线外发单
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-12-12 下午3:42:17
	 */
	ExternalBillDto queryExternalBillByWaybillNo(ExternalBillDto tempDto);

	/**
	 * 运单号查询是否已存在有效的非中转外发的外发单
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-12-14 下午3:56:20
	 */
	ExternalBillDto queryExternalBillByWaybillNoForChange(ExternalBillDto tempDto);

	/**
	 * 提供给结算的接口，校验是否存在外发单
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-9 上午11:27:24
	 */
	List<ExternalBillDto> validateIsExistExternalBill(String waybillNo);

	/**
	 * @author niuly
	 * @date 2014-3-3下午3:59:01
	 * @function 根据运单号list查询有效的外发单信息
	 * @param waybillNoList
	 * @return
	 */
	List<ExternalBillDto> queryExternalBillByWaybillNos(List<String> waybillNoList);

	/**
	 * @author zwd 200968
	 * @date 2015-04-24 
	 * @function 根据运单号查询偏线外发信息
	 * @param dto
	 * @return
	 */
	public List<ExternalBillDto> selectExternalByWayBillNo(ExternalBillDto dto);
}