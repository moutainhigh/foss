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
 * PROJECT NAME	: bse-dict
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/server/service/impl/DataDictionaryService.java
 * 
 * FILE NAME        	: DataDictionaryService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.util.DictUtil;
import com.deppon.foss.module.base.dict.server.dao.impl.DataDictionaryDao;

public class DataDictionaryService implements IDataDictionaryService {

	 /**
     * 声明日志对象
     */
    private static final Log LOGGER = LogFactory.getLog(DataDictionaryDao.class);
	
    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private IDataDictionaryDao dataDictionaryDao;

    /**
     * 下面是dao对象的声明及get,set方法：
     */
    private IDataDictionaryValueDao dataDictionaryValueDao;
	
    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:15:34
     * @see com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryService#addDataDictionary(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity)
     */
    @Override
    public DataDictionaryEntity addDataDictionary(DataDictionaryEntity entity) {
		//检查参数的合法性
		if (null == entity) {
		    return null;
		}
		//返回查询结果
		return dataDictionaryDao.addDataDictionary(entity);
    }

    /**
     * 通过code标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:15:34
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionary(java.lang.String)
     */
    @Override
    public DataDictionaryEntity deleteDataDictionary(DataDictionaryEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getTermsCode())) {
			//返回null
		    return null;
		}
		//返回查询结果
		return dataDictionaryDao.deleteDataDictionary(entity);
    }

    /**
     * 通过code标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:15:34
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionaryMore(java.lang.String[])
     */
    @Override
    public DataDictionaryEntity deleteDataDictionaryMore(DataDictionaryEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getTermsCode())) {
			//返回null
		    return null;
		}
		//返回查询结果
		return dataDictionaryDao.deleteDataDictionaryMore(entity);
    }

    /**
     * 更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-30 下午2:15:34
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#updateDataDictionary(com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity)
     */
    @Override
    @Transactional
    public DataDictionaryEntity updateDataDictionary(DataDictionaryEntity entity) {
		//检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getTermsCode())) {
			//返回null
		    return null;
		}
		//返回查询结果
		return dataDictionaryDao.updateDataDictionary(entity);
    }
    
    /**
     * 以下全为查询
     */
    
    /**
     * 
     * <p>批量查询所有数据字典</p> 
     * @author foss-zhujunyong
     * @date Nov 6, 2012 2:19:09 PM
     * @param code
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
	@Override
    public List<DataDictionaryEntity> queryDataDictionarys() {
	List<DataDictionaryEntity> dicList = new ArrayList<DataDictionaryEntity>();
	ICache<String, DataDictionaryEntity> dicCache = CacheManager
			.getInstance().getCache(DataDictionaryEntity.class.getName());
		Map<String, DataDictionaryEntity> dicMap = dicCache.get();

		if (dicMap != null && dicMap.values() != null) {
		    dicList.addAll(dicMap.values());
		}

		if ( dicList.size() == 0 ) {
			LOGGER.warn("从缓存中获取的数据字典为空");
		}
		StringBuilder sb = new StringBuilder();

		for (DataDictionaryEntity entity : dicList) {
			LOGGER.debug("数据字典词代码: " + entity.getTermsCode() + "; 加载数据字典词名称: " + entity.getTermsName());
		    sb.append("加载数据字典词代码:").append(entity.getTermsCode())
			    .append(",加载数据字典词名称:").append(entity.getTermsName());
		    //LOGGER.info(sb.toString());
		}

	if (dicList.size() == 0) {
	    dicList = dataDictionaryValueDao.queryDataForCache();
	}
	return dicList;
    }
    
    
    /**
     * 根据词条集合查询数据字典对象：
     * 
     * @author 053990-foss-zhongtingjie
     * @date 2012-11-14 上午10:53:20
     */
	@Override
	public List<DataDictionaryEntity> queryDataDictionaryByTermsCodes(
			List<String> termsCodes) {
		if (null == termsCodes) {
			return null;
		}
		if(termsCodes.size()==0){
			return null;
		}
		List<DataDictionaryEntity> entityList = DictUtil.getDataByTermsCodes(termsCodes);
		if(entityList.size()==0){
			String[] codes = new String[termsCodes.size()];
			entityList = dataDictionaryValueDao.queryDataForCacheByTermsCodes(termsCodes.toArray(codes));
		}
		return entityList;
	}

    /**
     * 通过词条编码TERM_CODE查询 数据字典-词
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-17 上午11:21:16
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#queryDataDictionaryByCode(java.lang.String)
     */
	@Override
	public DataDictionaryEntity queryDataDictionaryByTermsCode(String code) {
		if (null == code) {
			return null;
		}
		DataDictionaryEntity entity = DictUtil.getDataByTermsCode(code);
		if(entity==null){
			entity = dataDictionaryDao.queryDataDictionaryByTermsCode(code);
		}
		return entity;
	}

    /**
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-17 上午11:21:16
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#deleteDataDictionaryMore(java.lang.String[])
     */
    @Override
    public List<DataDictionaryEntity> queryDataDictionaryByEntity(
	    DataDictionaryEntity entity, int start, int limit) {
	if (null == entity) {
	    entity = new DataDictionaryEntity();
	}
	List<DataDictionaryEntity> entitys = dataDictionaryDao.queryDataDictionaryByEntity(entity, limit,
		start);
	return entitys;
    }

    /**
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-17 上午11:21:16
     * @see com.deppon.foss.module.district.server.dao.IDataDictionaryDao#queryDynimicCondition(com.deppon.foss.module.base.dict.api.shared.domain.district.shared.domain.DataDictionaryEntity)
     */
    @Override
    public long queryDataDictionaryByEntityCount(DataDictionaryEntity entity) {
    	//赋一个非空值
    	DataDictionaryEntity dateDictEntity = entity == null? new DataDictionaryEntity():entity;
    	//执行查询
		long result = (Long) dataDictionaryDao.queryDataDictionaryByEntityCount(dateDictEntity);
		return result;
    }

    /**
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-17 上午11:21:16
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#queryCount(java.lang.String,
     *      java.lang.String)
     */
    private long queryDataDictionaryCount(String columnName,
	    String columnValue) {
    	//执行查询
    	return (Long) dataDictionaryDao.queryDataDictionaryCount(columnName,columnValue);
    }

    /**
     * 查询columnName列的columnValue值有多少个，可用于去重
     * 
     * @author 087584-dp-lijun
     * @date 2012-10-17 上午11:21:16
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryDao#queryCount(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public long queryDataDictionaryCountByTermsCode(String columnValue) {
		String columnName = "TERMS_CODE";
		return this.queryDataDictionaryCount(columnName, columnValue);
    }   
    
    /**
     * 下面是特殊查询
     */
   
    /**
     * 根据传入数据字典词编码，名称，数据字典值编码，名称查询数据字典词，并带出值：
     * 动态的查询条件。 如果传入的为空，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-9 下午11:18:56
     */
    @Override
    public List<DataDictionaryEntity> queryDataDictionaryByCond(
	    String termsCode,String termsName,String valueCode,String valueName, 
	    int start, int limit){
	List<DataDictionaryEntity> entitys=null;
	if(StringUtils.isEmpty(valueCode) && StringUtils.isEmpty(valueName)){
	    DataDictionaryEntity entityCondition = new DataDictionaryEntity();
	    entityCondition.setTermsCode(termsCode);
	    entityCondition.setTermsName(termsName);
	    entitys = dataDictionaryDao.queryDataDictionaryByEntity(entityCondition, start, limit);
	}else{
	    entitys = dataDictionaryDao.queryDataDictionaryByCond(termsCode, termsName, valueCode, valueName, start, limit);
	}
	if(CollectionUtils.isEmpty(entitys)){
	    return entitys;
	}
	

	// 将数据字典-值 设置进来：
	Map<String, DataDictionaryEntity> map = new HashMap<String, DataDictionaryEntity>();
	List<String> list = new ArrayList<String>();
	for(DataDictionaryEntity entity: entitys){
	    String termsCodeInner = entity.getTermsCode();
	    if(termsCodeInner !=null){
		map.put(termsCodeInner, entity);
		list.add(termsCodeInner);
	    }
	}
	String[] codes = new String[list.size()];
	for(int i=0,l = codes.length;i<l; i++){
	    codes[i]=list.get(i);
	}
	
	
	List<DataDictionaryValueEntity> valueEntitys = dataDictionaryValueDao.queryDataDictionaryValueMoreByTermsCode(codes);
	// 分拣数据字典值
	for(DataDictionaryValueEntity valueEntity : valueEntitys){
	    if (valueEntity != null) {
		DataDictionaryEntity termsEntity = map.get(valueEntity.getTermsCode());
		if(termsEntity!=null){
		    // 取出数据字典值的容器
		    List<DataDictionaryValueEntity> dataDictionaryValueEntityList = termsEntity.getDataDictionaryValueEntityList();
		    if(dataDictionaryValueEntityList == null){
			dataDictionaryValueEntityList = new ArrayList<DataDictionaryValueEntity>();
		    }
		    dataDictionaryValueEntityList.add(valueEntity);
		    termsEntity.setDataDictionaryValueEntityList(dataDictionaryValueEntityList);
		}
	    }
	}
	
	return entitys;
    }
    
    /**
     * 查询总条数      
     * 根据传入数据字典词编码，名称，数据字典值编码，名称查询数据字典词，并带出值：
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-9 下午11:19:52
     */ 
    @Override
    public long queryDataDictionaryByCondCount(String termsCode,String termsName,String valueCode,String valueName){
		long result = 0;
		if (StringUtils.isEmpty(valueCode) && StringUtils.isEmpty(valueName)) {
			DataDictionaryEntity entityCondition = new DataDictionaryEntity();
			entityCondition.setTermsCode(termsCode);
			entityCondition.setTermsName(termsName);
			return dataDictionaryDao
					.queryDataDictionaryByEntityCount(entityCondition);
		} else {
			result = dataDictionaryDao.queryDataDictionaryByCondCount(
					termsCode, termsName, valueCode, valueName);
		}
		//返回结果
		return result;
	}
    

    public IDataDictionaryDao getDataDictionaryDao() {
        return dataDictionaryDao;
    }
  
    public void setDataDictionaryDao(IDataDictionaryDao dataDictionaryDao) {
        this.dataDictionaryDao = dataDictionaryDao;
    }

    public IDataDictionaryValueDao getDataDictionaryValueDao() {
        return dataDictionaryValueDao;
    }
    
    public void setDataDictionaryValueDao(
    	IDataDictionaryValueDao dataDictionaryValueDao) {
        this.dataDictionaryValueDao = dataDictionaryValueDao;
    }

}
