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
 * PROJECT NAME	: bse-dict-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/dict/api/util/DataDictUtil.java
 * 
 * FILE NAME        	: DataDictUtil.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.api.util;

import java.lang.reflect.Field;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.FlagConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;

public class DataDictUtil {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(DataDictUtil.class);
	
    // 工具类或者对象
    final static Collator myCollator = Collator.getInstance(Locale.CHINESE);

    // 词代码-词对象）映射
    private static Map<String, DataDictionaryEntity> dataDictMap = null;

    // 词对象）列表
    private static List<DataDictionaryEntity> propList = null;

    public static Map<String, DataDictionaryEntity> getDataDictMap() {
	if (dataDictMap == null) {
	    DataDictUtil.initDict();
	    if (dataDictMap == null) {
		dataDictMap = new HashMap<String, DataDictionaryEntity>();
	    }
	}

	return dataDictMap;
    }

    public static List<DataDictionaryEntity> getPropList() {
	if (propList == null) {
	    DataDictUtil.initDict();
	    if (propList == null) {
		propList= new ArrayList<DataDictionaryEntity>();
	    }
	}

	return propList;
    }

    /**
     * 初始化dataDictMap，propList(按拼音排序）
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-27 下午5:42:55
     */
    public static void initDict() {
	Map<String, String> propMap = new HashMap<String, String>();
	Comp com = new Comp();
	Set<DataDictionaryEntity> propSet = new TreeSet<DataDictionaryEntity>(
		com);
	Field[] fields = DictionaryConstants.class.getFields();
	for (Field field : fields) {
	    String propertyName = field.getName();
	    String propertyValue = null;
	    try {
		propertyValue = (String) field.get(propertyName);
	    } catch (Exception e) {
	    	LOGGER.error("按拼音排序有误：  "+e.getMessage());
	    }
	    propMap.put(propertyName, propertyValue);

	}
	
	if(dataDictMap == null){
	    dataDictMap = new HashMap<String, DataDictionaryEntity>();
	}
	for (Field field : fields) {
	    String propertyName = field.getName();
	    if(propertyName!=null && !propertyName.endsWith(FlagConstants.DATA_DICT_DESC_PROP_SUFFIX)){
		DataDictionaryEntity entity = new DataDictionaryEntity();
		String termsCode = propMap.get(propertyName);
		entity.setTermsCode(termsCode);
		entity.setTermsName(propMap.get(propertyName + FlagConstants.DATA_DICT_DESC_PROP_SUFFIX));
		propSet.add(entity);
		dataDictMap.put(termsCode,entity);
	    }

	}
	
	if(propList == null){
	    propList = new ArrayList<DataDictionaryEntity>();
	}

	if(!CollectionUtils.isEmpty(propSet)){
	    propList.addAll(propSet);
	}
    }

    public static class Comp implements Comparator<DataDictionaryEntity> {

	public int compare(DataDictionaryEntity o1, DataDictionaryEntity o2) {
	    DataDictionaryEntity e1 = (DataDictionaryEntity) o1;
	    DataDictionaryEntity e2 = (DataDictionaryEntity) o2;
	    if (e1 == null || e1.getTermsName() == null) {
		return -1;
	    }
	    if (e2 == null || e2.getTermsName() == null) {
		return 1;
	    }
	    if(e1.getTermsName().equals(e2.getTermsName())){
		return 0;
	    }
	    return myCollator.compare(e1.getTermsName(), e2.getTermsName());
	}

    }
    

    static {
	DataDictUtil.initDict();
    }
    
}
