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
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/api/server/dao/IPackOutDao.java
 *  
 *  FILE NAME          :IPackOutDao.java
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
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.api.server.dao
 * FILE    NAME: IPackOutDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.WaybillNoLogingDateDto;

/**
 * 对外的DAO
 * @author 046130-foss-xuduowei
 * @date 2012-10-26 上午11:14:55
 */
public interface IPackOutDao {
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
	 * 插入包装需求前查看是否已存在该运单
	 * @param waybillNo 运单号
	 * @return 符合该运单号的包装需求个数
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-13 上午11:11:20
	 */
	int queryPackagingRequire(String waybillNo);
	/**
	 * 
	 * 保存包装需求明细
	 * @param packagingRequireDetails 包装需求明细
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-5 下午3:18:30
	 */
	int addPackagingRequireDetails(List<PackagingRequireDetailsEntity> packagingRequireDetails);
	/**
	 * 
	 * 删除包装需求明细
	 * @author 046130-foss-xuduowei
	 * @date 2012-12-12 下午7:53:11
	 */
	int deletePackagingRequireDetails(List<PackagingRequireDetailsEntity> packagingRequireDetails);
	/**
	 * 
	 * 保存需要修改包装运单信息到包装需求表中
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:11:34
	 */
	int updatePackagingRequire(PackagingRequireEntity packagingRequireEntity);
	
	/**
	 * 
	 * 用于接送货发更改时调用，修改需要包装的运单信息
	 * @param waybillNo 运单号
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:11:34
	 */
	int disablePackagingRequire(String waybillNo);
	/**
	 * 更新运单号
	 * @param origWaybillNo
	 * @param newWaybillNo
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	int updateWaybillNo(Map<String,Object> map);
	/**
	 * 修改包装需求中需要包装件数
	 * @author 046130-foss-xuduowei
	 * @param waybillNo
	 * @return
	 * @date 2013-04-26 下午6:00:00
	 */
	int updateNeedPackNum(String waybillNo);
	/**
	 * 插入包装货物登入包装货去或登出包装货区的时
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	int insertWaybillNoLogingDate(WaybillNoLogingDateDto waybillNoLogingDateDto);
	/**
	 * 返回包装需求主表信息
	 * @return
	 * @date 2013年11月30日 11:52:43
	 */
	 PackagingRequireEntity queryPackagingRequireWithId(String waybillNo);
}