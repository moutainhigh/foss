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
 * PROJECT NAME	: bse-querying
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/querying/server/dao/impl/WaybillMarkDao.java
 * 
 * FILE NAME        	: WaybillMarkDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IWaybillMarkDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.QueryingConstant;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.WaybillMarkEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;

/**
 * 运单标记Dao
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:073586-FOSS-LIXUEXING,date:2013-01-21 20:33
 * </p>
 * 
 * @author 073586-FOSS-LIXUEXING
 * @date 2013-01-21 20:33
 * @since
 * @version
 */
public class WaybillMarkDao extends SqlSessionDaoSupport implements
	IWaybillMarkDao {

    private static final String NAMESPACE_WAYBILLMARK = QueryingConstant.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".waybillMarkEntityMapper.";

    @Override
    public int addWaybillMark(WaybillMarkEntity entity) {
	entity.setId(UUIDUtils.getUUID());
	entity.setCreateUser(FossUserContext.getCurrentInfo().getEmpCode());
	return this.getSqlSession().insert(NAMESPACE_WAYBILLMARK + "insert",
		entity);
    }

    @Override
    public int updateWaybillMark(WaybillMarkEntity entity) {
	return this.getSqlSession().update(NAMESPACE_WAYBILLMARK + "update",
		entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WaybillMarkEntity> queryWaybillMarks(WaybillMarkEntity entity) {
	return this.getSqlSession().selectList(
		NAMESPACE_WAYBILLMARK + "getWaybillMark", entity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int addWaybillMarkList(String[] codeStr, String markStatus) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codeStr);
	map.put("mark", markStatus);
	List<WaybillMarkEntity> waybillMarkList = this.getSqlSession().selectList(
		NAMESPACE_WAYBILLMARK + "getWaybillMarkByCods", map);
	int i = this.getSqlSession().delete(NAMESPACE_WAYBILLMARK + "delete",
		map);
	 //获得 已经 进行标记 为 目标 状态的 集合
	List<String> waybillNoList = acquireWaybillMarkWaybill(waybillMarkList,markStatus);
	// 将要 进行标记 为 目标 状态的 的 运单 转化成数组
	List<String> codeList = org.springframework.util.CollectionUtils.arrayToList(codeStr);
	// 获得本来没有进行标记 为 目标 状态的 集合
	// 【待优化 批量新增】本来没有进行标记 为 目标 状态的 集合 循环新增
	int j = 0;
	for (String str : codeList) {
	   if (!waybillNoList.contains(str)) {
		WaybillMarkEntity entity = new WaybillMarkEntity();
		entity.setCreateUserCode(FossUserContext.getCurrentInfo()
			.getEmpCode());
		entity.setCreateUserName(FossUserContext.getCurrentInfo()
			.getEmpName());
		entity.setCreateDate(new Date());
		entity.setMarkStatus(markStatus);
		entity.setWaybillNo(str);
		j += addWaybillMark(entity);
	    }
	}
	return i + j;
    }
    /**
     * 
     * <p>获得 list集合中 指定 标记状态的 运单集合</p> 
     * @author 073586-FOSS-LIXUEXING
     * @date 2013-3-14 下午2:58:15
     * @param entityList
     * @return
     * @see
     */
    private List<String> acquireWaybillMarkWaybill(List<WaybillMarkEntity> entityList,String markStatus){
	List<String> waybillNoList = new ArrayList<String>();
	if(CollectionUtils.isNotEmpty(entityList)){
	    for(WaybillMarkEntity waybill : entityList){
		if(markStatus.equals(waybill.getMarkStatus())){
		    waybillNoList.add(waybill.getWaybillNo());
		}
	    }
	}
	return waybillNoList;
    }
}
