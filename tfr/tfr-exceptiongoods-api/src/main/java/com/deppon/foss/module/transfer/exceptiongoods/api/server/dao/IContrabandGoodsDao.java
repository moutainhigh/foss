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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/IContrabandGoodsDao.java
 * 
 *  FILE NAME          :IContrabandGoodsDao.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.ContrabandGoodsEntity;

/**
 * 违禁品的增、删、改、查操作
 * 
 * @author 097457-foss-wangqiang
 * @date 2012-12-5 上午11:02:20
 */
public interface IContrabandGoodsDao {
	/**
	 * 增加违禁品
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 上午11:00:41
	 */
	int addContrabandGoods(ContrabandGoodsEntity contrabandGoodsEntity);

	/**
	 * 更新违禁品
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 上午11:01:06
	 */
	int updateContrabandGoods(ContrabandGoodsEntity contrabandGoodsEntity);

	/**
	 * 分页查询违禁品
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 上午11:01:21
	 */
	List<ContrabandGoodsEntity> queryContrabandGoods(ContrabandGoodsEntity contrabandGoods, int limit, int start);

	/**
	 * 违禁品总数
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-5 下午5:11:42
	 */
	Long queryContrabandGoodsCount(ContrabandGoodsEntity contrabandGoods);

	/**
	 * 查询违禁品
	 * 
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-30 下午4:46:08
	 */
	ContrabandGoodsEntity queryContrabandGoods(String waybillNo);

	/**
	 * 增加从QMS推来的违禁品
	 * 
	 * @author 316759-foss-ruipengwang
	 * @date 2016-05-19 下午16:28:41
	 */
	int addContrabandGoodsFromQms(ContrabandGoodsEntity contrabandGoodsEntity);

	/**
	 * QMS更新违禁品
	 * 
	 * @author 316759-foss-ruipengwang
	 * @date 2016-05-20上午10:45:23
	 */
	int updateContrabandGoodsFromQms(ContrabandGoodsEntity contrabandGoodsEntity);

}