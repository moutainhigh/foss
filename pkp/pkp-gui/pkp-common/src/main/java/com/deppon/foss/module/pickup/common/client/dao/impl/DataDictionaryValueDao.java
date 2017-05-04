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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/dao/impl/DataDictionaryValueDao.java
 * 
 * FILE NAME        	: DataDictionaryValueDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.common.client.dao.IDataDictionaryValueDao;
import com.deppon.foss.util.define.FossConstants;


/**
 * 数据字典值
 * @author 105089-foss-yangtong
 * @date 2012-10-31 下午2:45:39
 */
public class DataDictionaryValueDao implements IDataDictionaryValueDao {
	/**
	 * 名称空间
	 */
	private static final String NAMESPACE = "foss.pkp.DataDictionaryValueEntityMapper.";
	/**
	 * 数据库连接
	 */
	private SqlSession sqlSession;
	/**
	 * 数据库连接
	 * @return void
	 * @since:1.6
	 */@Inject
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	/**
     * 
     * <p>
     * 通过主键获取
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:33:04
     * @param id
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.dao.IDataDictionaryDao#queryByPrimaryKey(java.lang.String)
     */
    public DataDictionaryValueEntity queryByPrimaryKey(String id) {
    	return sqlSession.selectOne(NAMESPACE + "selectDataDictionaryByPrimaryKey", id);
    }

    /**
     * 
     * <p>
     * 通过词条代码查询
     * </p>
     * 
     * @author foss-sunrui
     * @date 2012-10-16 下午5:33:08
     * @param termsCode
     * @return
     * @see com.deppon.foss.module.pickup.creating.client.dao.IDataDictionaryDao#queryByTermsCode(java.lang.String)
     */
    public List<DataDictionaryValueEntity> queryByTermsCode(String termsCode) {
    	DataDictionaryValueEntity dataDic = new DataDictionaryValueEntity();
    	dataDic.setActive(FossConstants.ACTIVE);
    	dataDic.setTermsCode(termsCode);
    	return sqlSession.selectList(NAMESPACE + "selectDataDictValueByTermsCode", dataDic);
    }
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	public boolean addDataDictionaryValue(
			DataDictionaryValueEntity dataDictionaryValue) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", dataDictionaryValue.getId());
		String id = sqlSession.selectOne(NAMESPACE + "selectById", map);
		if(ObjectUtils.NULL.equals(ObjectUtils.defaultIfNull(
				id, ObjectUtils.NULL))){
			sqlSession.insert(NAMESPACE + "insertDataDictionaryValue", dataDictionaryValue);
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	public void updateDataDictionaryValue(
			DataDictionaryValueEntity dataDictionaryValue) {
		sqlSession.update(NAMESPACE + "updateDataDictionaryValue", dataDictionaryValue);

	}

	/**
	 * 
	 * 根据Code查找数据字典
	 * @author 102246-foss-shaohongliang
	 * @date 2012-12-4 上午11:46:28
	 */
	public DataDictionaryValueEntity queryDataDictoryValueByCode(String termsCode, String valueCode) {
		DataDictionaryValueEntity dataDic = new DataDictionaryValueEntity();			
		dataDic.setActive(FossConstants.ACTIVE);
    	dataDic.setTermsCode(termsCode);
    	dataDic.setValueCode(valueCode);
    	return sqlSession.selectOne(NAMESPACE + "selectDataDictValueByValueCode", dataDic);
	}
	
	/**
	 * @param termsCode
	 * @param valueCode
	 * 兼容航班 晚班类型
	 * @author 218459-foss-dongsiwei
	 * @return
	 */
	public DataDictionaryValueEntity queryDataDictoryValueByValueCode(String termsCode, String valueCode) {
		DataDictionaryValueEntity dataDic = new DataDictionaryValueEntity();
    	dataDic.setTermsCode(termsCode);
    	dataDic.setValueCode(valueCode);
    	List<DataDictionaryValueEntity> list= sqlSession.selectList(NAMESPACE + "selectDataDictValueByValueCode", dataDic);
    	if(list.size()>0){    		
    		return list.get(0);
    	}else{
    		return null;
    	}
	}

	/**
	 * 删除
	 * @param dataDictionaryValue
	 */
	public void delete(DataDictionaryValueEntity dataDictionaryValue) {
		sqlSession.delete(NAMESPACE + "delete", dataDictionaryValue);
	}

}