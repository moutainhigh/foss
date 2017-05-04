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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/api/server/service/IWayBillNoLocusService.java
 * 
 * FILE NAME        	: IWayBillNoLocusService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillLocusDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;

/**
 * 
 * 运单轨迹接口
 * 
 * @author ibm-wangfei
 * @date Jan 11, 2013 3:40:24 PM
 */
public interface IWayBillNoLocusService extends IService {
	/**
	 * 
	 * 运单轨迹接口（供官网调用）
	 * 
	 * @param waybillNo
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:59:07
	 */
	public List<WayBillNoLocusDTO> getWayBillNoLocus(String waybillNo);
	
	/**
	 * 
	 * <p>运单轨迹接口（供CC调用）</p> 
	 * @author alfred
	 * @date 2014-7-31 下午3:39:43
	 * @param waybillNo
	 * @return
	 * @see
	 */
	public WayBillNoLocusDTO getWayBillNoLocusForCC(String waybillNo);
	
	/**
	 * 
	 * 运单轨迹接口（供综合查询用）
	 * 
	 * @param waybillNo
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:59:07
	 */
	public List<WayBillNoLocusDTO> getWayBillNoLocusForBse(String waybillNo);
	
	/**
	 * 
	 * 运单轨迹接口
	 * 
	 * @param waybillNo
	 * @param invokingSource 调用来源
	 * 
	 * @author foss-liubinbin(for IBM)
	 * @date 2013-1-8 上午9:59:07
	 */
	public List<WayBillNoLocusDTO> getWayBillNoLocus(String waybillNo, String invokingSource);

	/**
	 * 
	 * PDA查询货物接口
	 * 
	 * @param waybillNo
	 * @return List<WayBillLocusDto>
	 * @author ibm-wangfei
	 * @date Jan 11, 2013 4:15:26 PM
	 */
	public WayBillLocusDto getWayBillLocusForPda(String waybillNo);

	/**
	 * 
	 * 按件查询运单轨迹
	 * 
	 * @param waybillNo
	 * @return List<WayBillLocusDto>
	 * @author ibm-wangfei
	 * @date Jan 11, 2013 4:15:26 PM
	 */
	public List<WayBillNoLocusDTO> getWayBillNoLocusBySerialNo(
			String waybillNo, String serialNo);
	
	/**
	 * 查询补录后的PDA开单信息
	 * @author liyongfei
	 * @param waybillEntity
	 * @return
	 */
	public WayBillNoLocusDTO getWayBillHisPDA(String waybillNo);
}