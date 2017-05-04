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
 *  PROJECT NAME  : tfr-pda-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/server/service/IPdaVehicleSealService.java
 *  
 *  FILE NAME          :IPdaVehicleSealService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.pda.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SealDestDetailEntity;
import com.deppon.foss.module.transfer.pda.api.shared.domain.SealOrigDetailEntity;

/**
 * PDA封签接口
 * @author ibm-zhangyixin
 * @date 2012-11-5 下午2:02:19
 */
public interface IPdaVehicleSealService extends IService {
	/**
	 * 录入装车封签
	 * @author ibm-zhangyixin
	 * @date 2012-11-5 下午2:17:48
	 * @param vehicleNo 	车牌号
	 * @param sealdOrgCode 	绑定部门
	 * @param backSealNos 	后门封签
	 * @param sideSealNos 	侧门封签号码 
	 * @param sealerCode 	封车人编号
	 * @param pdaDeviceNo 	PDA设备号 
	 * @param currentDept 	当前部门
	 * @param nextDept 		下一部门
	 */
	int insertSealOrig (String vehicleNo, String sealdOrgCode,
			List<String> backSealNos, List<String> sideSealNos,
			String sealerCode, String pdaDeviceNo, String currentDept, String nextDept);

	/**
	 * 录入到达封签
	 * @author ibm-zhangyixin
	 * @date 2012-11-5 下午2:17:48
	 * @param sealState 	封签状态
	 * @param vehicleNo 	车牌号
	 * @param backSealNos 	后门封签号码
	 * @param sideSealNos 	侧门封签号码 
	 * @param checkOrgMemo 	封签破损异常备注
	 * @param checkerUser 	检查人编号
	 * @param pdaDeviceNo 	PDA设备号 
	 * @param billNos 		交接单编号<集合> 
	 */
	int insertSealDest (String sealState, String vehicleNo, String checkOrgCode,
			List<String> backSealNos, List<String> sideSealNos,
			String checkOrgMemo, String checkerUser, String pdaDeviceNo, List<String> billNos);
	
	
	//封签新需求2013-10-28
	//begin
	/**
	 * 录入装车封签
	 * @author ibm-zhangyixin
	 * @date 2012-11-5 下午2:17:48
	 * @param vehicleNo 	车牌号
	 * @param sealdOrgCode 	绑定部门
	 * @param sealOrigDetails 	封签明细
	 * @param sealerCode 	封车人编号
	 * @param pdaDeviceNo 	PDA设备号 
	 * @param currentDept 	当前部门
	 * @param nextDept 		下一部门
	 * @param sealType 		绑定封签方式(SCANED, BY_HAND) @see SealConstant
	 */
	int insertSealOrig (String vehicleNo, String sealdOrgCode,
			List<SealOrigDetailEntity> sealOrigDetails,
			String sealerCode, String pdaDeviceNo, String currentDept, String nextDept, String sealType);

	/**
	 * 录入到达封签
	 * @author ibm-zhangyixin
	 * @date 2012-11-5 下午2:17:48
	 * @param sealState 	封签状态
	 * @param vehicleNo 	车牌号
	 * @param checkOrgCode 	校验部门
	 * @param sealDestDetails 	封签明细
	 * @param checkOrgMemo 	封签破损异常备注
	 * @param checkerUser 	检查人编号
	 * @param pdaDeviceNo 	PDA设备号 
	 * @param sealType 		校验封签方式(SCANED, BY_HAND) @see SealConstant
	 */
	int insertSealDest (String sealState, String vehicleNo, String checkOrgCode,
			List<SealDestDetailEntity> sealDestDetails,
			String checkOrgMemo, String checkerUser, String pdaDeviceNo, String sealType);
	//end
	
	/**
	 * 录入到达封签获取预分配月台号
	 * @author wqh
	 * @date 2015-06-01 上午09:17:48
	 * @param sealState 	封签状态
	 * @param vehicleNo 	车牌号
	 * @param checkOrgCode 	校验部门
	 * @param sealDestDetails 	封签明细
	 * @param checkOrgMemo 	封签破损异常备注
	 * @param checkerUser 	检查人编号
	 * @param pdaDeviceNo 	PDA设备号 
	 * @param sealType 		校验封签方式(SCANED, BY_HAND) @see SealConstant
	 */
	String insertSealDestNew(String sealState, String vehicleNo, String checkOrgCode,
			List<SealDestDetailEntity> sealDestDetails,
			String checkOrgMemo, String checkerUser, String pdaDeviceNo, String sealType);
}