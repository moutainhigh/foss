package com.deppon.foss.module.base.dict.api.server.service;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;

public interface IDataDegreePostionService {

	/**
	 * @author 130134
	 * @date 2014-3-10 下午4:35:49
	 * @param termsCode
	 * @param valueCode
	 * @return
	 * @see
	 */
    DataDegreePostionValueEntity queryDegreePostionValueByTermsCodeValueCodeNoCache(String termsCode,String valueCode);
 
    /**
     * 更新 
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
     */
    DataDegreePostionValueEntity updateDegreePostionValue( DataDegreePostionValueEntity entity)throws DataDictionaryValueException;
    
    /**
     * 插入
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:17:33
     */
    DataDegreePostionValueEntity addDegreePostionValue(DataDegreePostionValueEntity entity)throws DataDictionaryValueException;
    
    /**
     * 
     * @param entity
     * @return
     */
    public DataDegreePostionValueEntity deleteDegreePostionValue(DataDegreePostionValueEntity entity);
}
