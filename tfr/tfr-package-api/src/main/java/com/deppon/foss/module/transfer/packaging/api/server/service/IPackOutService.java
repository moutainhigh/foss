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
 *  PROJECT NAME  : tfr-package-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/server/service/IPackOutService.java
 *  
 *  FILE NAME          :IPackOutService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package-api
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.server.service
 * FILE    NAME: IPackOutService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.WaybillNoLogingDateDto;

/**
 * 供外部模块调用
 * @author 046130-foss-xuduowei
 * @date 2012-10-18 上午11:51:37
 */
public interface IPackOutService extends IService {
	/**
	 * 
	 * 保存需要包装运单信息到包装需求表中
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:11:34
	 */
	int addPackagingRequire(PackagingRequireEntity packagingRequireEntity);

	/**
	 * 
	 * 用于接送货发更改时调用，修改需要包装的运单信息
	 * @param packagingRequireEntity 包装需求数据
	 * @param addSerialNo 新增需要包装的流水号集合
	 * @param deleteSerialNo 删除不需要包装的流水号集合
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:11:34
	 */
	int updatePackagingRequire(PackagingRequireEntity packagingRequireEntity);
	/**
	 * 
	 * 用于接送货发更改时调用，修改需要包装的运单信息
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:11:34
	 */
	int disablePackagingRequire(String waybillNo);
	
	/**
	 * 判断waybillno的serialNo是否已包装，已包装为true，否则为false
	 * @param waybillNo 运单号
	 * @param serialNo 流水号
	 * @return
	 * @date 2013-03-04 下午6:00:00
	 */
	boolean queryPackedStatusBySerialNo(String waybillNo,String serialNo);
	/**
	 * 更新运单号
	 * @param origWaybillNo 原始运单号
	 * @param newWaybillNo 新运单号
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	int updateWaybillNo(String origWaybillNo,String newWaybillNo);
	/**
	 * 插入包装货物登入包装货去或登出包装货区的时
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	int insertWaybillNoLogingDate(WaybillNoLogingDateDto waybillNoLogingDateDto);
}