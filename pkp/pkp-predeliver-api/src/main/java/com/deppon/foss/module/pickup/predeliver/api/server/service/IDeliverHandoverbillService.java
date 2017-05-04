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

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.AutoDeliverHandoverEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverHandoverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.*;



/**
 * 派送交单service接口
 * 
 * @author 159231 meiying
 * 2015-4-20  上午11:23:14
 */
public interface IDeliverHandoverbillService {
	/**
	 * 查询待交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	 Long queryPreDeliverHandoverCount(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
	 /**
	  * 查询待交单运单List
	  * @author 159231 meiying
	  * 2015-4-21  上午8:58:52
	  * @param preDeliverHandoverbillQueryDto
	  * @return
	  */
	 List<PreDeliverHandoverbillDto> queryPreDeliverHandoverList(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto,int start, int limit);
	 /**
	 * 查询已交单运单总数
	 * @author 159231 meiying
	 * 2015-4-21  上午8:47:36
	 * @param preDeliverHandoverbillQueryDto
	 * @return
	 */
	 Long queryDeliverHandoverCount(DeliverHandoverbillQueryDto deliverHandoverbillQueryDto);
	 /**
	  * 查询已交单运单List
	  * @author 159231 meiying
	  * 2015-4-21  上午8:58:52
	  * @param preDeliverHandoverbillQueryDto
	  * @return
	  */
	 List<DeliverHandoverbillDto> queryDeliverHandoverList(DeliverHandoverbillQueryDto deliverHandoverbillQueryDto, int start, int limit);
	 /**
	  * 修改预计送货日期
	  * @author 159231 meiying
	  * 2015-4-21  上午9:02:36
	  * @param preDeliverHandoverbillQueryDto
	  * @return
	  */
	 int updatePreDeliverDateByWaybillNo(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto);
	
	/**
	  * 导出派送交单-待交单信息
	  * @author 159231 meiying
	  * 2015-4-28  下午6:11:17
	  * @param preDeliverHandoverbillQueryDto
	  * @return
	  */
	 InputStream exportPreDeliverHandover(PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto, int start, int limit);
	 /**
	  * 根据运单号查询最后一次通知的信息
	  * @author 159231 meiying
	  * 2015-4-30  下午5:17:40
	  * @return
	  */
	 NotificationEntity  queryLastNotifyByWaybillNo(PreDeliverHandoverbillDto dto);
	 /**
	  * 改最后一次通知信息
	  * @author 159231 meiying
	  * 2015-4-30  下午3:04:40
	  * @param notify
	  * @return
	  */
	 NotificationEntity updateLastNotifyByWaybillNo(NotificationEntity notify);
	 /**
	  * 派送交单
	  * @author 159231 meiying
	  * 2015-4-30  下午3:13:05
	  * @param dtos
	  */
	 void executePreDeliverbill(List<PreDeliverHandoverbillDto> dtos);
	 /**
	  * 撤销派送交单
	  * @author 159231 meiying
	  * 2015-4-30  下午3:13:34
	  */
	 void revokedPreDeliverbill(PreDeliverHandoverbillQueryDto dto);
	 /**
	  * 导出派送交单-交单信息
	  * @author 159231 meiying
	  * 2015-5-6  上午11:11:11
	  * @param dto
	  * @param start
	  * @param limit
	  * @return
	  */
	 InputStream exportDeliverHandover(DeliverHandoverbillQueryDto dto, int start, int limit);
	 /**
	  * 可视化排单，分区排单，创建派送单（新）里运单退回调用接口
	  * @author 159231 meiying
	  * 2015-6-17  下午4:51:08
	  * @param dto
	  */
	 List<String> returnPreDeliverbill( DeliverHandoverbillReturnDto dto);
    /**
     *  
     * @author 159231 meiying
     * 2015-6-17  下午4:50:41
     * @param dto
     * @return
     */
   void goodsBackReturnPreDeliverbill( DeliverHandoverbillReturnDto dto);
    void LastLoadOrgCodeChangeDeliverbill( DeliverHandoverbillReturnDto dto);
    /**
     * 根据运单号集合查询待交单信息
     * @author 159231 meiying
     * 2015-5-19  下午6:17:56
     * @param preDeliverHandoverbillQueryDto
     * @param start
     * @param limit
     * @return
     */
    List<PreDeliverHandoverbillDto> queryPreDeliverHandoverByWaybillNosList(
	PreDeliverHandoverbillQueryDto preDeliverHandoverbillQueryDto, int start, int limit);
    /**
     * 进行自动交单
     * @author 159231 meiying
     * 2015-6-1  上午8:40:20
     * @param pre
     */
    void executeAutoPreDeliverbill(AutoDeliverHandoverEntity pre);
    /**
     * 根据传入的运单号集合＋active = 'Y'查询满足条件的运单
     * @author 159231 meiying
     * 2015-6-1  下午5:13:40
     * @param pre
     * @return
     */
    List<DeliverHandoverbillEntity> queryDeliverHandoverbillByWaybillNos(
			DeliverHandoverbillOtherDto pre);
    /**
     * 根据运单号集合＋active = 'Y'修改满足条件的信息
     * @author 159231 meiying
     * 2015-6-1  下午5:14:51
     * @param pre
     * @return
     */
    int updatePartByWaybillNos(DeliverHandoverbillOtherDto pre);
    /**
     * 根据运单号修改派送交单信息
     * * @author 159231 meiying
     * 2015-6-5  下午3:50:33
     * @param entity
     * @return
     */
    int updateByWaybillNoSelective(DeliverHandoverbillEntity entity);
}