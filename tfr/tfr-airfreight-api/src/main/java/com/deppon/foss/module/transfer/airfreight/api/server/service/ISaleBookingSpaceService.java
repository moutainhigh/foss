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
 *  PROJECT NAME  : tfr-airfreight-api
 *  
 *  package_name  : 
 *  
 *  FILE PATH          :/ISaleBookingSpaceService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.airfreight.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.airfreight.api.shared.dto.SaleBookingSpaceDto;
/**
 * 营业部订舱service接口
 * @author 038300-foss-pengzhen
 * @date 2012-11-12 上午11:28:10
 */
public interface ISaleBookingSpaceService extends IService {
	/**
	 * 查询营业部订舱信息, 参数1：entity 封装的查询对象
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 上午9:33:42
	 */
	List<SaleBookingSpaceDto> queryBookingSpace(SaleBookingSpaceDto dto, int limit,int start);
	
	/**
	 * 新增订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-3 下午4:41:16
	 */
	void addBookingSpace(SaleBookingSpaceDto dto);

	/**
	 * 受理营业部订舱， 参数1：受理类型 | 参数2：受理备注 | 参数3：受理列表id
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-5 下午3:05:30
	 */
	void acceptBookingSpace(boolean acceptType,String acceptNotes,List<String> acceptIds);
	
	/**
	 * 初始化界面参数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-9 下午8:43:12
	 */
	SaleBookingSpaceDto initBookingSpace();
	
	/**
	 * 通过id查询营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午10:30:07
	 */
	SaleBookingSpaceDto queryBookingSpaceById(String id);
	
	/**
	 * 更新营业部订舱信息
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 上午11:27:07
	 */
	void updateBookingSpace(SaleBookingSpaceDto dto);
	
	/**
	 * 获取未受理记录总数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-12 下午5:28:35
	 */
	Long queryNoAcceptCount(SaleBookingSpaceDto dto);
	
	/**
	 * 获取订舱列表总记录数
	 * @author 038300-foss-pengzhen
	 * @date 2012-11-13 下午5:12:06
	 */
	Long queryCount(SaleBookingSpaceDto dto);

	/**
	 * 根据空运总调调出始发站
	 * @author foss-liuxue(for IBM)
	 * @date 2013-1-24 下午8:45:25
	 */
	SaleBookingSpaceDto queryDeptRegion(String acceptOrgCode);

	/**
	 * 根据运单号查询需要订舱的信息
	 * @author foss-liuxue(for IBM)
	 * @date 2013-5-21 下午4:35:38
	 */
	SaleBookingSpaceDto queryWaybillInfo(String waybillNo);

	/**
	 * 获取上级空运总调组织
	 */
	OrgAdministrativeInfoEntity getOrgAdministrative();
	
}