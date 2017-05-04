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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/service/IWaybillQueryForBseService.java
 * 
 * FILE NAME        	: IWaybillQueryForBseService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverySituationDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WayBillOtherForBseDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WayBillStatusForBseDto;
import com.deppon.foss.module.pickup.sign.api.shared.exception.WaybillQueryForBseException;
import com.deppon.foss.module.pickup.waybill.api.shared.dto.SimpleWaybillDto;

/**
 * 提供给综合查询的接口
 * 
 * @author ibm-wangfei
 * @date Dec 24, 2012 5:01:53 PM
 */
public interface IWaybillQueryForBseService extends IService {

	/**
	 * 根据运单号查询运单其他信息
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 5:03:41 PM
	 */
	WayBillOtherForBseDto queryOtherInfoByWaybillNo(String waybillNo) throws WaybillQueryForBseException;

	/**
	 * 
	 * 根据运单号查询运单状态，返回一个对象的集合
	 * 
	 * @param waybillNo
	 * @return
	 * @author ibm-wangfei
	 * @date Dec 24, 2012 5:04:06 PM
	 */
	List<WayBillStatusForBseDto> queryWaybillStatusByWaybillNoForPkp(String waybillNo) throws WaybillQueryForBseException;
	
	/**
	 * 自定义查询
	 * 
	 * @param where where語句，count 总数
	 * @author 038590-foss-wanghui
	 * @date 2013-3-1 上午9:12:32
	 */
	List<SimpleWaybillDto> queryWayBillListByUserDefinedQuery(String where, int count);
	/**
	 * 综合查询增加派送情况查询
	 * 
	 * @author foss-meiying
	 * @date 2013-7-2 上午9:12:32
	 */
	DeliverySituationDto queryDeliverySituationByWaybill(String waybillNo);

}