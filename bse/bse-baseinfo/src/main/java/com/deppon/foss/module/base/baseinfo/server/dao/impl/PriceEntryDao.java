/**
 *  initial comments.
 */
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PriceEntryDao.java
 * 
 * FILE NAME        	: PriceEntryDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 计价条目DAO(配合定价项目降价发券方案引用PKP)
 * @author DP-Foss-YueHongJie
 * @date 2012-11-12 下午4:33:08
 */
public class PriceEntryDao extends SqlSessionDaoSupport implements IPriceEntryDao{
    /**
     * ibatis mapper
     */
    private static final String NAME_SPACE = "foss.bse.bse-baseinfo.priceEntityMapper.";
    /**
     * 
     *  根据不同条件查询计价条目
     * @author DP-Foss-YueHongJie
     * @param entity 实体条件
     * @date 2012-11-12 下午3:23:08
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PriceEntity> searchPriceEntryByConditions(PriceEntity entity) {
	return getSqlSession().selectList(NAME_SPACE + "queryPriceEntryDetail", entity); 
    }
    /**
      * 
      * <p>(根据计价条目CODE,查询计价条目名称)</p> 
      * @author DP-Foss-YueHongJie
      * @date 2012-12-7 下午2:29:13
      * @param entryCode
      * @return
      * @see
      */
    @Override
    public String queryPriceEntryNameByCode(String code) {
	PriceEntity p = new PriceEntity();
	p.setCode(code);
	p.setActive(FossConstants.ACTIVE);
	p.setReceiveDate(new Date());
	return (String)getSqlSession().selectOne(NAME_SPACE + "queryPriceEntryNameByCode", p);
    }
    /**
     * 
     * <p>(批量查询计价条目名称)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-7 下午5:44:29
     * @param entryCodes
     * @return List<PriceEntity>
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<PriceEntity> queryPriceEntryNameByEntryCodes(
	    List<String> entryCodes) {
	Map parameterMap = new HashMap();
	parameterMap.put("entryCodes", entryCodes);
	parameterMap.put("active", FossConstants.ACTIVE);
	parameterMap.put("receiveDate", new Date());
	return getSqlSession().selectList(NAME_SPACE + "queryPriceEntryNameByEntryCodes", parameterMap);
    }
    
    /**
	 * @Description: 根据计价条目CODE,查询计价条目
	 * @author FOSSDP-sz
	 * @date 2013-1-21 下午4:53:01
	 * @param entryCode
	 * @return
	 * @version V1.0
	 */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public PriceEntity queryPriceEntryByCode(String entryCode) {
	Map parameterMap = new HashMap();
	parameterMap.put("code", entryCode);
	parameterMap.put("active", FossConstants.ACTIVE);
	parameterMap.put("receiveDate", new Date());
	return (PriceEntity)getSqlSession().selectOne(NAME_SPACE + "queryPriceEntryByCode", parameterMap);
    }
    /**
     * @Description: 根据计价条目CODE,查询计价条目集合
     * @author FOSSDP-sz
     * @date 2013-2-1 下午5:05:41
     * @param entryCode
     * @return
     * @version V1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<PriceEntity> getPriceEntryByCode(String entryCode, Date billDate) {
		Map parameterMap = new HashMap();
		parameterMap.put("code", entryCode);
		parameterMap.put("receiveDate", billDate);
		parameterMap.put("active", FossConstants.ACTIVE);
		return getSqlSession().selectList(NAME_SPACE + "queryPriceEntryByCode", parameterMap);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<PriceEntity> findPriceEntityPagingByCondition(
	   PriceEntity entity, int start, int limit) {
	   RowBounds rowBounds = new RowBounds(start,limit);
	   return getSqlSession().selectList(NAME_SPACE + "findPriceEntryByCondiction", entity, rowBounds);
    }
    @Override
    public Long countPriceEntityPagingByCondition(PriceEntity entity) {
	return (Long) getSqlSession().selectOne(NAME_SPACE + "countPriceEntityByCondition", entity);
    }
    @Override
    public PriceEntity findPriceEntityById(String id) {
	return (PriceEntity)getSqlSession().selectOne(NAME_SPACE + "selectByPrimaryKey", id);
    }
    @Override
    public void modifyPriceEntity(PriceEntity priceEntity) {
	getSqlSession().update(NAME_SPACE + "updateByPrimaryKey", priceEntity);
    }
    @Override
    public void addPriceEntry(PriceEntity priceEntity) {
	getSqlSession().insert(NAME_SPACE+"insert", priceEntity);
    }
    @Override
    public PriceEntity modifyBeforeCheckPriceEntryCode(PriceEntity priceEntity) {
	return (PriceEntity) getSqlSession().selectOne(NAME_SPACE+"modifyBeforeCheckPriceEntryCode", priceEntity);
    }
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceEntity> findParentPriceEntitys() {
		return getSqlSession().selectList(NAME_SPACE+"findParentPriceEntryByCondiction");
	}
}