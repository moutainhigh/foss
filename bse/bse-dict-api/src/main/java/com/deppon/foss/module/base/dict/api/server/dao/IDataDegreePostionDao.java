package com.deppon.foss.module.base.dict.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;

public interface IDataDegreePostionDao {

	 /**
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     *
     * @author 087584-foss-lijun
     * @date 2012-10-19 下午12:35:36
     */
    List<DataDegreePostionValueEntity> queryDataDictionaryValueExactByEntity(DataDegreePostionValueEntity entity) ;
     	
    /**
     * 根据CODE删除
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDegreePostionValueEntity deleteDataDictionaryValue(DataDegreePostionValueEntity entity);
    /**
     * 更新 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDegreePostionValueEntity updateDataDictionaryValue(DataDegreePostionValueEntity entity);
    
    /**
     * 精确查询
     * 根据 TERMS_CODE,VALUE_CODE 查询值对象：
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:11:20
     */
    DataDegreePostionValueEntity queryDataDictionaryValueByTermsCodeValueCode(String termsCode,String valueCode); 
    /**
     * 插入
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:6:38
     */
    DataDegreePostionValueEntity addDataDictionaryValue(DataDegreePostionValueEntity entity);
}

