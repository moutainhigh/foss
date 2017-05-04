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
 *  FILE PATH     :src/main/java/com/deppon/foss/module/transfer/partialline/api/server/service/IExternalBillService.java
 * 
 *  FILE NAME     :IExternalBillService.java
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
package com.deppon.foss.module.transfer.partialline.api.server.service;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillDto;
import com.deppon.foss.module.transfer.partialline.api.shared.dto.ExternalBillWayBillInfoDto;
import com.deppon.foss.module.transfer.partialline.api.shared.vo.ExternalBillVo;

/**
 * 偏线service接口
 * 
 * @author 096598-foss-zhongyubing
 * @date 2012-10-12 上午9:18:08
 */
public interface IExternalBillService {

	/**
	 * 查询偏线列表
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午9:03:45
	 */
	List<ExternalBillDto> selectByParams(ExternalBillDto dto, int limit, int start, CurrentInfo currentInfo);

	/**
	 * 获取偏线外发单总条数
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 下午2:32:16
	 */
	Long queryCount(ExternalBillDto dto, CurrentInfo currentInfo);

	/**
	 * 保存偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-12 上午11:46:12
	 */
	int addExternalBill(ExternalBillDto tempDto, CurrentInfo currentInfo);

	/**
	 * 更新偏线外发单
	 * 
	 * @author 096598-foss-zhongyubing
	 * @date 2012-10-15 上午8:38:46
	 */
	int updateExternalBill(ExternalBillDto tempDto, CurrentInfo currentInfo);

	/**
	 * 查询运单信息及代理信息
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-10-20 上午10:20:50
	 */
	ExternalBillWayBillInfoDto queryWaybillInfo(ExternalBillDto tempDto, String validateWaybillNo, CurrentInfo user);

	/**
	 * 审核偏线
	 * 
	 * @author dp-zhongyubing
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-20 上午11:48:12
	 */
	int auditPartialline(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 反审核偏线
	 * 
	 * @author dp-zhongyubing
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-20 下午1:40:15
	 */
	int deAuditPartialline(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException;

	/**
	 * 作废偏线外发单
	 * 
	 * @author dp-zhongyubing
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-10-20 下午1:41:08
	 */
	int invalidePartialline(List<String> auditIds, CurrentInfo currentInfo) throws IllegalAccessException,
			InvocationTargetException;

	

	/**
	 * 根据运单号查询外发单信息
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-12-12 下午3:39:17
	 */
	ExternalBillDto queryExternalBillByWaybillNo(ExternalBillDto tempDto);

	/**
	 * 运单号查询是否已存在有效的非中转外发的外发单(用于更改单的查询)
	 * 
	 * @author dp-zhongyubing
	 * @date 2012-12-14 下午3:56:20
	 */
	ExternalBillDto queryExternalBillByWaybillNoForChange(ExternalBillDto tempDto);

	/**
	 * 
	 * 导出偏线外发单列表
	 * 
	 * 
	 * @author dp-zhongyubing
	 * 
	 * @date 2013-3-22 上午9:24:40
	 */
	InputStream exportExternalBill(List<ExternalBillDto> externalBillList,ExternalBillVo vo);

	/**
	 * 提供给结算的接口，校验是否存在外发单
	 * @author foss-liuxue(for IBM)
	 * @date 2013-7-9 上午11:27:24
	 */
	boolean validateIsExistExternalBill(String waybillNo);
	
	/**
	 * @author niuly
	 * @date 2014-3-3下午4:55:15
	 * @function 根据运单号list查询有效的外发单信息
	 * @param waybillNoList
	 * @return
	 */
	List<ExternalBillDto> queryExternalBillByWaybillNos(List<String> waybillNoList);
	
	
	/**
	 * @author wqh
	 * @date 2014-07-31下午4:55:15
	 * @function 根据运单号计算外发费用和外发送货费用
	 * @param waybillNo
	 * @return 
	 */
	ExternalBillWayBillInfoDto calculateDeliverFeeAndEtdFee(String waybillNo);
	/**
	 * @author zwd 2009687
	 * @date 2015-04-24 
	 * @function 根据运单号查询偏线外发信息
	 * @param dto
	 * @return
	 */
	List<ExternalBillDto>  selectExternalByWayBillNo(ExternalBillDto dto);
	
	/**
	 * @Desc 根据运单号查询有效的(Not "INVALID")外发单号列表
	 * @Author 370210
	 * @Created 2016-12-14 下午2:13:58
	 */
	List<String> getExternalBillNumListByWaybillNo(String waybillNo);
	
}