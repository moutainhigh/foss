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
 * PROJECT NAME	: pkp-predeliver-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IDeliverbillService.java
 * 
 * FILE NAME        	: IDeliverbillService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillDetailEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DriverDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadGaprepWaybillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoadTaskDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.LoaderDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.ScanDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliveryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;

/**
 * 
 * 派送排单Service接口
 * 
 */ 
public interface IDeliverbillNewService {
	
	
	/**
	 * 
	 * 根据输入条件，查询符合条件的派送单数量
	 * 
	 * @param deliverbillNewDto
	 *            查询条件
	 * 
	 * @return 符合条件的派送单数量
	 * @author 
	 * @date 
	 */
	Long queryDeliverbillCountByCondition(DeliverbillNewDto deliverbillNewDto);
	
	/**
	 * 
	 * 根据输入条件，查询派送单
	 * 
	 * @param deliverbillNewDto
	 *            查询条件
	 * @return 派送单列表
	 * @author 
	 * @date 
	 */
	List<DeliverbillNewDto> queryDeliverbillList(DeliverbillNewDto deliverbillNewDto,
			int start, int limit);

	

	/**
	 * 
	 * 查询派送单序列
	 * @author 
	 * @date 
	 */
	String querySequence();
	
	
	/**
	 * 
	 * 取消预派送单
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @param deliverbillNo
	 *            派送单编号
	 * @return 若成功，返回大于0；否则返回0
	 * @author 
	 * @date 
	 */
	int cancelDeliverbill(String deliverbillId, String deliverbillNo);
	
	/**
	 * 
	 * 取消已保存的派送单
	 * @author 
	 * @date
	 */
	int cancelDeliverbillForSaved(String deliverbillId, String deliverbillNo);
	
	/**
	 * 
	 * 根据派送单ID查找派送单信息
	 * 
	 * @author 
	 * @date 
	 */
	DeliverbillEntity queryDeliverbill(String id);
	
	
	
	/**
	 * 
	 * 根据派送单ID查找派送单明细列表大小
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @return 派送单明细列表大小
	 * 
	 * @return 派送单明细列表大小
	 * @author 
	 * @date 
	 */
	Long queryDeliverbillDetailCountByDeliverbillId(String deliverbillId);
	
	/**
	 * 
	 * 根据条件查询派送单明细
	 * @author 
	 * @date 
	 * @param deliverbill
	 * @param waybill
	 * @return
	 * @see
	 */
    InputStream queryDeliverbillDetailLists(DeliverbillNewDto deliverbillNewDto);
	
	/**
	 * 
	 * 根据派送单ID查找派送单明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @author 
	 * @date 
	 */
	List<DeliverbillDetailEntity> queryDeliverbillDetailList(String deliverbillId, int start, int limit);
	
	
	/**
	 * 
	 * 根据派送单ID查找已生成到达联的派送明细列表
	 * 
	 * @param deliverbillId
	 *            派送单ID
	 * @author 
	 * @date 
	 */
	List<DeliverbillDetailEntity> queryDeliverbillArrivesheetList(String deliverbillId,String status);
	
	
	
	
	
	/**
	 * 
	 * 根据接送货车辆车牌号查询接送货司机(SUC-447 创建派送单 SR9 1.
	 * 若排班（和处理订单中排班是同一个排班）中存在该车牌号，则用户在选择司机时自动带出对应的司机 2.
	 * 若PDA绑定中存在该车牌号，则系统自动带出对应的司机且不可修改 3. 当排班和PDA绑定同时存在时，以PDA绑定为准)
	 * 
	 * @param vehicleNo
	 *            车牌号
	 * @return 接送货司机
	 * @author 
	 * @date
	 */
	DriverDto queryDriverByVehicleNo(String vehicleNo);
	
	
	/**
	 * 
	 * 根据查询条件(工号/姓名/电话号码)查询公司司机
	 * 
	 * @param driverDto
	 *            查询条件
	 * @return 符合条件的公司司机列表
	 * @author 
	 * @date 
	 */
	List<DriverDto> queryDriverListByDriverDto(DriverDto driverDto);
	
	
	/**
	 * 
	 * 分配派送任务
	 * 
	 * @param deliverbillId
	 *            派送单id
	 * @param driver
	 *            分配的司机
	 * @return 分配成功标志。若成功，则返回1；否则不成功
	 * @author 
	 * @date 
	 */
	int assignDriver(String deliverbillId, DriverDto driver);
	
	/**
	 * 
	 * 根据条件查询派送单
	 * @author 
	 * @date 
	 */
	InputStream queryDeliverbillList(DeliverbillNewDto deliverbillNewDto);
	
	
	
    /**
     * 更新派送单
     * @param deliverbill
     * @return
     */
	int updateDeliverBill(DeliverbillEntity deliverbill);
	
	/**
	 * 短信通知司机
	 * @param deliverbill
	 * @return
	 */
	void isSendSMSToDriver(String deliverbillId);

	/**
	 * 短信通知客户
	 * @author 306548
	 */
	void isSendSMSToConsumers(String deliverbillId,String taskNo);

	
	
}
	
	