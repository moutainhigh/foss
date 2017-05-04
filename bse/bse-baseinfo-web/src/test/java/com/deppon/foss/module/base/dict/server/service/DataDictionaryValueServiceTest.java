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
 * PROJECT NAME	: bse-baseinfo-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/base/dict/server/service/DataDictionaryValueServiceTest.java
 * 
 * FILE NAME        	: DataDictionaryValueServiceTest.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.dict.server.service;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;


import com.deppon.foss.module.base.baseinfo.server.util.SpringTestHelper;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryEntity;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
import com.deppon.foss.module.base.dict.server.service.impl.DataDictionaryValueService;

import com.deppon.foss.module.base.dict.api.util.DataDictUtil;

/**
 * 数据字典测试类
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-10 上午11:05:41
 */
@Ignore
public class DataDictionaryValueServiceTest {

    /**
     * 数据字典-值
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryValueData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @Test
    public void addDataDictionaryValue() {
	String termsCode = DictionaryConstants.PKP_PRICE_CHANNAL;
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String d=a;
	String user="087584-toDelete";
	String seq = a+(++count);
	if(seq.length()>8){
	    seq = seq.substring(seq.length()-7,seq.length());
	}
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY_VALUE(id, VIRTUAL_CODE, TERMS_CODE, VALUE_NAME, VALUE_CODE,VALUE_SEQ, CREATE_USER_CODE, ACTIVE) " +
		"values('"+b+"', '"+b+"', '"+termsCode+"','"+b+"', '"+b+"', '"+seq+"', '"+user+"', 'Y')");
	seq+=(++count);
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY_VALUE(id, VIRTUAL_CODE, TERMS_CODE, VALUE_NAME, VALUE_CODE,VALUE_SEQ, CREATE_USER_CODE, ACTIVE) " +
		"values('"+c+"', '"+c+"', '"+termsCode+"','"+c+"', '"+c+"', '"+seq+"', '"+user+"', 'Y')");
	
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	entity.setTermsCode(termsCode);
	// 测试valueCode重复异常
	d=d+(++count);
	entity.setId(d);
	entity.setValueCode(b);
	entity.setValueCode(b);
	entity.setValueName(a+(++count));
	entity.setValueSeq(this.getSeq());
	DataDictionaryValueEntity reusult =null;
	try{
	    reusult = dataDictionaryValue.addDataDictionaryValue(entity);
	}catch(Exception e){
	    Assert.assertEquals(e.getClass().getName(),
		    DataDictionaryValueException.class.getName());
	}
	deleteDataDictionaryValueValueById(d);
	
	// 测试valueName重复异常
	entity.setValueCode(a+(++count));
	entity.setValueName(b);
	entity.setValueSeq(this.getSeq());
	try{
	    reusult = dataDictionaryValue.addDataDictionaryValue(entity);
	}catch(Exception e){
	    Assert.assertEquals(e.getClass().getName(),
		    DataDictionaryValueException.class.getName());
	}
	
	// 测试valueSeq重复异常
	d=d+(++count);
	entity.setId(d);
	entity.setValueCode(a+(++count));
	entity.setValueName(a+(++count));
	entity.setValueSeq(seq);
	try{
	    reusult = dataDictionaryValue.addDataDictionaryValue(entity);
	}catch(Exception e){
	    Assert.assertEquals(e.getClass().getName(),
		    DataDictionaryValueException.class.getName());
	}
	deleteDataDictionaryValueValueById(d);
	
	// 正常
	d=d+(++count);
	entity.setId(d);
	entity.setValueCode(a);
	entity.setValueName(a);
	entity.setValueSeq(this.getSeq());
	reusult = dataDictionaryValue.addDataDictionaryValue(entity);
	Assert.assertTrue(reusult != null);
	deleteDataDictionaryValueValueById(d);
	
	deleteDataDictionaryValueValueById(b);
	deleteDataDictionaryValueValueById(c);
    }
    /**
     * 数据字典-值
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryValueData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    @Test
    public void queryDataDictionaryValueByEntity() {
	String termsCode = DictionaryConstants.PKP_PRICE_CHANNAL;
	String a=System.currentTimeMillis()+"";
	String b=a+(++count);
	String c=a+(++count);
	String user="087584-toDelete";
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY_VALUE(id, VIRTUAL_CODE, TERMS_CODE, VALUE_NAME, VALUE_CODE, CREATE_USER_CODE, ACTIVE) " +
		"values('"+b+"', '"+b+"', '"+termsCode+"','"+b+"', '"+b+"', '"+user+"', 'Y')");
	jdbc.execute("insert into T_BAS_DATA_DICTIONARY_VALUE(id, VIRTUAL_CODE, TERMS_CODE, VALUE_NAME, VALUE_CODE, CREATE_USER_CODE, ACTIVE) " +
		"values('"+c+"', '"+c+"', '"+termsCode+"','"+c+"', '"+c+"', '"+user+"', 'Y')");
	
	DataDictionaryValueEntity entity = new DataDictionaryValueEntity();
	entity.setTermsCode(termsCode);
	entity.setValueCode(b);
	List<DataDictionaryValueEntity> reusult = dataDictionaryValue.queryDataDictionaryValueByEntity(entity, 0, 1);
	Assert.assertTrue(!CollectionUtils.isEmpty(reusult) && !StringUtils.isBlank(reusult.get(0).getValueCode()));
	
	deleteDataDictionaryValueValueById(b);
	deleteDataDictionaryValueValueById(c);
    }


    /**
     * 数据字典-词 跟值关联查询
     * Test method for {@link com.deppon.foss.module.base.dict.server.service.impl.DictDownloadService#downloadDataDictionaryValueData(com.deppon.foss.base.util.ClientUpdateDataPack)}.
     */
    
    @Test
    public void queryDataDictionaryValueList() {
	List<DataDictionaryEntity> entitys = DataDictUtil.getPropList();
	Assert.assertTrue(!CollectionUtils.isEmpty(entitys));
    }
    
    /**
     * 下面是测试使用的工具方法
     */
    static int count=0;
    private JdbcTemplate jdbc;
    private IDataDictionaryValueService dataDictionaryValue;
    
    public String getSeq(){
	String seq = System.currentTimeMillis()+(++count)+"";
	seq = seq.substring(seq.length()-7,seq.length());
	return seq;
    }
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteDataDictionaryValueById(String id){
	jdbc.execute("delete from BSE.T_BAS_DATA_DICTIONARY where id = '" + id + "'");
	
    }    
    /**
     * 获得需要测试的实体
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-14 下午2:02:00
     */
    public void deleteDataDictionaryValueValueById(String id){
	jdbc.execute("delete from BSE.T_BAS_DATA_DICTIONARY_VALUE where id = '" + id + "'");
	
    }
    
    @Before
    public void setUp() throws Exception {
	jdbc = (JdbcTemplate) SpringTestHelper.get().getBeanByClass(JdbcTemplate.class);
	dataDictionaryValue = (IDataDictionaryValueService) SpringTestHelper.get().getBeanByClass(DataDictionaryValueService.class);
    }
    

    @After
    public void tearDown() throws Exception {
    }
    
}
