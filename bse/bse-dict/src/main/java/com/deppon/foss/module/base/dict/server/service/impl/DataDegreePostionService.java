package com.deppon.foss.module.base.dict.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao;
import com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;
import com.deppon.foss.module.base.dict.api.shared.exception.DataDictionaryValueException;
import com.deppon.foss.util.CollectionUtils;

public class DataDegreePostionService implements IDataDegreePostionService {

	
	private IDataDegreePostionDao dataDegreePostionDao;
	
	public void setDataDegreePostionDao(
			IDataDegreePostionDao dataDegreePostionDao) {
		this.dataDegreePostionDao = dataDegreePostionDao;
	}
	
	/**
	 * 
	 * @author 130134---------------------
	 * @date 2014-3-10 下午4:40:27
	 * @param termsCode
	 * @param valueCode
	 * @return 
	 * @see com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService#queryDataDictionaryValueByTermsCodeValueCodeNoCache(java.lang.String, java.lang.String)
	 */
	@Override
	public DataDegreePostionValueEntity queryDegreePostionValueByTermsCodeValueCodeNoCache(String termsCode, String valueCode) {
			// 非空校验
				if (StringUtils.isBlank(termsCode) || StringUtils.isBlank(valueCode)) {
					// 返回为空
					return null;
				}
				// 返回查询结果
				DataDegreePostionValueEntity entity = null;
				entity = dataDegreePostionDao.queryDataDictionaryValueByTermsCodeValueCode(termsCode,valueCode);
				return entity;
	}

	/**
	 * @author 130134-------------------
	 * @date 2014-3-10 下午4:40:38
	 * @param entity
	 * @return
	 * @throws DataDictionaryValueException 
	 * @see com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService#updateDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity)
	 */
	@Override
	public DataDegreePostionValueEntity updateDegreePostionValue(DataDegreePostionValueEntity entity)
			throws DataDictionaryValueException {
		// 检查参数的合法性
				if (null == entity || StringUtils.isBlank(entity.getValueCode())|| StringUtils.isBlank(entity.getType())|| StringUtils.isBlank(entity.getValueName())) {
					return null;
				}
				// 请求合法性验证
				// 处理去重的问题
				DataDegreePostionValueEntity entityCondition = new DataDegreePostionValueEntity();
				
				entityCondition.setType(entity.getType());
				entityCondition.setValueCode(entity.getValueCode());
				List<DataDegreePostionValueEntity> entitys = dataDegreePostionDao.queryDataDictionaryValueExactByEntity(entityCondition);
				if(entitys.isEmpty()){
					throw new DataDictionaryValueException("职位职等表中找不到所要更新的记录！");
				}
				DataDegreePostionValueEntity result = dataDegreePostionDao.updateDataDictionaryValue(entity);
				return result;
	}
	
	/**
	 * @author 130134--------
	 * @date 2014-3-10 下午4:40:42
	 * @param entity
	 * @return
	 * @throws DataDictionaryValueException 
	 * @see com.deppon.foss.module.base.dict.api.server.service.IDataDegreePostionService#addDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity)
	 */
	@Override
	public DataDegreePostionValueEntity addDegreePostionValue(DataDegreePostionValueEntity entity)throws DataDictionaryValueException {
		// 检查参数的合法性
				if (null == entity) {
					throw new DataDictionaryValueException("职位职等表-值对象为空");
				}
				if (StringUtils.isEmpty(entity.getValueCode())) {
					throw new DataDictionaryValueException("职位职等表-值代码为空");
				}
				if (StringUtils.isEmpty(entity.getValueName())) {
					throw new DataDictionaryValueException("职位职等表-名称为空");
				}
				
				DataDegreePostionValueEntity entityCondition = new DataDegreePostionValueEntity();
				entityCondition.setType(entity.getType());
				entityCondition.setValueCode(entity.getValueCode());
				List<DataDegreePostionValueEntity> entitys = dataDegreePostionDao.queryDataDictionaryValueExactByEntity(entityCondition);
				
				if(!CollectionUtils.isEmpty(entitys)){
					throw new DataDictionaryValueException("职位职等表中已存该记录！");
				}
				DataDegreePostionValueEntity result = dataDegreePostionDao.addDataDictionaryValue(entity);
				return result;
	}
	
	/**
	 * 通过code标识来删除---------------------------------------------------------------------
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-30 下午2:26:0
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDictionaryValueDao#deleteDataDictionaryValue(java.lang.String)
	 */
	@Override
	public DataDegreePostionValueEntity deleteDegreePostionValue(DataDegreePostionValueEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getValueCode())) {
			return null;
		}
		// 为了删除缓存需要先根据虚拟编码查询出实体信息

		DataDegreePostionValueEntity result = dataDegreePostionDao.deleteDataDictionaryValue(entity);
		return result;
	}
	
}
