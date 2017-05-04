/*******************************************************************************
 * Copyright 2013 BSE TEAM
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
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/dao/ISpecialAddressDao.java
 * 
 * FILE NAME        	: ISpecialAddressDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.SpecialAddressEntity;
/**
 * 
 * 特殊地址DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:何波,date:2013-2-20 下午2:36:43,content: </p>
 * @author 何波
 * @date 2013-2-20 下午2:36:43
 * @since
 * @version
 */
public interface ISpecialAddressDao {

	/**
	 * 
	 * <p>新增特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 上午10:19:50
	 * @param entity
	 * @return
	 * @see
	 */
	 int addSpecialAddress(SpecialAddressEntity specialAddress);

	/**
	 * 
	 * <p>作废特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 上午10:36:14
	 * @param specialAddress
	 * @return
	 * @see
	 */
	int deleteSpecialAddress(SpecialAddressEntity specialAddress);
	
	/**
	 * 
	 * <p>修改特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 上午10:32:37
	 * @param specialAddress
	 * @return
	 * @see
	 */
	int updateSpecialAddress(SpecialAddressEntity specialAddress);
	
	/**
	 * 
	 * <p>查询特殊地址</p> 
	 * @author 何波
	 * @date 2013-1-31 下午3:05:14
	 * @param specialAddress
	 * @return
	 * @see
	 */
	List<SpecialAddressEntity> querySpecialAddress(SpecialAddressEntity specialAddress);
	
	/**
	 * 
	 * <p>查询一条有效的特殊地址</p> 
	 * @author 何波
	 * @date 2013-2-18 下午5:34:46
	 * @param id
	 * @return
	 * @see
	 */
	SpecialAddressEntity querySpecialAddressBySelective(String id);
	
	/**
	 * 
	 * <p>分页查询</p> 
	 * @author 何波
	 * @date 2013-2-19 下午4:53:20
	 * @param specialAddress
	 * @param offset
	 * @param limit
	 * @return
	 * @see
	 */
	List<SpecialAddressEntity> querySpecialAddressListBySelectiveCondition(SpecialAddressEntity specialAddress, int offset, int limit);
	
	/**
	 * 
	 * <p>查询满足条件的条数</p> 
	 * @author 何波
	 * @date 2013-2-19 下午5:01:14
	 * @param specialAddress
	 * @return
	 * @see
	 */
	Long querySpecialAddressCountBySelectiveCondition(SpecialAddressEntity specialAddress);
	 
}
