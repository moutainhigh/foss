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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IDeliverHandlerDao.java
 * 
 * FILE NAME        	: IDeliverHandlerDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.DeliverbillDetailDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.FinancialDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StayHandoverDto;

/**
 * 派送处理Dao
 * @author foss-meiying
 * @date 2012-10-29 下午2:16:31
 * @since
 * @version
 */
public interface IDeliverHandlerDao {
	/**
	 * 派送处理      根据（派送编号）查询运单编号,到达联编号
	 * @author foss-meiying
	 * @date 2012-10-30 上午11:05:51
	 * @param dto
	 * @return
	 * @see
	 */
	 List<DeliverbillDetailDto> queryDeliverbillWaybillNo(DeliverbillDetailDto dto);
	/**
	 * 根据运单号查询运单里的财务信息
	 * @author foss-meiying
	 * @date 2012-10-31 下午1:32:09
	 * @param waybillNo
	 * @return
	 * @see
	 */
	 FinancialDto queryWaybillByWaybillNo(String waybillNo);
	 /**
	  * 查询当前派送单中所有的运单到达联是否都完成了签收确认
	  * @author foss-meiying
	  * @date 2012-11-12 上午11:42:57
	  * @param dto
	  * @return
	  * @see
	  */
	 List<DeliverbillDetailDto> queryArrivesheetIsSign(DeliverbillDetailDto dto);
	 /**
	  * 送货确认---根据派送单查询货物拉回信息
	  * @author foss-meiying
	  * @date 2012-11-13 上午11:42:38
	  * @param deliverbillNo
	  * @return
	  * @see
	  */
	 List<StayHandoverDto> queryStayHandoverBydeliverbillNo(DeliverbillDetailDto dto);
	 /**
	 * 
	 * 更新派送单
	 * 
	 * @param deliverbill
	 *            派送单
	 * @return 
	 * @author foss-meiying
	 * @date 2013-06-29 下午2:54:18
	 */
	 int updateDeliverbill(DeliverbillDetailDto dto);
	 /**
	 * 
	 * 根据运单号查询部分派送单信息（综合查询提供到达联派送中接口）
	 * 
	 * @return 
	 * @author foss-meiying
	 * @date 2013-08-7 下午2:54:18
	 */
	 List<DeliverbillDetailDto> queryPartDeliverBillByWaybillNo(DeliverbillDetailDto dto);
	 /**
	 * 派送处理      根据（派送编号）查询运单编号,到达联编号  快递相关
	 * @author foss-yuting
	 * @date 2014-10-09 上午11:05:51
	 * @param dto
	 * @return
	 * @see
	 */
	List<DeliverbillDetailDto> queryDeliverbillWaybillNoExp(DeliverbillDetailDto dto);
}