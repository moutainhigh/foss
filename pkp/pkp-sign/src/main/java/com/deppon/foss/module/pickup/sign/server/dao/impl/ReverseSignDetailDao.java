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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/dao/impl/ReverseSignDetailDao.java
 * 
 * FILE NAME        	: ReverseSignDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.sign.api.server.dao.IReverseSignDetailDao;
import com.deppon.foss.module.pickup.sign.api.shared.domain.ReverseSignDetailEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * 反签收明细操作
 * @author ibm-lizhiguo
 * @date 2012-11-20 上午9:39:38
 */
public class ReverseSignDetailDao extends iBatis3DaoImpl implements IReverseSignDetailDao{
	//反签收功能模块命名空间
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.sign.api.shared.domain.ReverseSignDetailEntity.";
	//获得反签收LIST
	private static final String REVERSESIGNDETAILLIST = "queryReverseSignDetailListBySignRfcId";
	//添加反签收明细
	private static final String INSERTSELECTIVE = "insertSelective";
	
	/**
	 * 
	 * 获得反签收明细List
	 * @author ibm-lizhiguo
	 * @date 2012-11-20 上午9:41:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseSignDetailEntity> searchReverseSignDetailList(String signRfcId) {
		return getSqlSession().selectList(NAMESPACE + REVERSESIGNDETAILLIST, signRfcId);
	}
	
	/**
     * 
     * 保存反签收明细
     * @author ibm-lizhiguo
     * @date 2012-11-20 上午9:42:17
     */
	@Override
	public int insertSelective(ReverseSignDetailEntity record) {
		record.setId(UUIDUtils.getUUID());
		//创建时间
		record.setCreateDate(new Date());
		//修改时间
		record.setModifyDate(record.getCreateDate());
		return getSqlSession().insert(NAMESPACE+INSERTSELECTIVE, record);
	}
	/**
	 * 可选的修改
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月14日 15:58:17
	 */
	@Override
	public int updateByPrimaryKeySelective(ReverseSignDetailEntity record) {
		//修改时间
		record.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
	}
	/**
	 * 修改
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年3月14日 15:58:17
	 */
	@Override
	public int updateByPrimaryKey(ReverseSignDetailEntity record) {
		//修改时间
		record.setModifyDate(new Date());
		return this.getSqlSession().update(NAMESPACE+"updateByPrimaryKey", record);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReverseSignDetailEntity> selectReverseSignDetailByIds(List<String> reverseIds){
		Map<Object, Object> maps = new HashMap<Object, Object>();
		maps.put("reverseIds", reverseIds);
		return this.getSqlSession().selectList(NAMESPACE+"selectReverseSignDetailByIds", maps);		
	}
}