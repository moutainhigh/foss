package com.deppon.foss.module.base.dict.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class DataDegreePostionDao extends SqlSessionDaoSupport implements IDataDegreePostionDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataDegreePostionDao.class);
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_DICT_DEPO + ".dataDegreePostion.";
    
    
    
    /**查询
     * @author 130134
     * @date 2014-3-10 下午5:36:06
     * @param entity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao#queryDataDictionaryValueExactByEntity(com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity, int, int)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataDegreePostionValueEntity> queryDataDictionaryValueExactByEntity(DataDegreePostionValueEntity entity) {
		DataDegreePostionValueEntity queryEntity;
		if (null == entity) {
		    queryEntity = new DataDegreePostionValueEntity();
		} else {
		    queryEntity = entity;
		}
		queryEntity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryDataDictionaryValueExactByEntity",queryEntity);
	}
 
	
	
   /**删除----------------------------
    * @author 130134
    * @date 2014-3-10 下午5:37:02
    * @param entity
    * @return 
    * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao#deleteDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity)
    */
	@Override
	public DataDegreePostionValueEntity deleteDataDictionaryValue(DataDegreePostionValueEntity entity) {
		// 处理删除时要更新的数据
		Date now = new Date();
		entity.setModifyDate(now);
		// entity应包含modifyUser,因此不用处理
		entity.setActive(FossConstants.INACTIVE);
		entity.setVersionNo(now.getTime());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entity", entity);
		// 只删除active为有效的：
		map.put("conditionActive", FossConstants.ACTIVE);
		int result = getSqlSession().update(NAMESPACE + "deleteDataDictionaryValue", map);
		return result > 0 ? entity : null;
	}
	
	
	
    /**更新
     * @author 130134
     * @date 2014-3-10 下午5:37:22
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao#updateDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity)
     */
	@Override
	public DataDegreePostionValueEntity updateDataDictionaryValue(DataDegreePostionValueEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
		    return entity;
		}
		// 更新要先删除旧的数据：
		DataDegreePostionValueEntity result = this.deleteDataDictionaryValue(entity);
		if (result == null) {
		    String msg = "更新时，作废失败";
		    LOGGER.error(msg);
		    return null;
		}
		
		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		// CreateUser为传入的用户编码，CreateDate为当前时间
		entity.setCreateDate(new Date());
		entity.setVersionNo(System.currentTimeMillis());
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(entity.getModifyUser());

		entity.setActive(FossConstants.ACTIVE);
		int resultNum = getSqlSession().insert(NAMESPACE + "addDataDictionaryValue", entity);
		return resultNum > 0 ? entity : null;
	}

	
	
	/**查询通过编码-------------------------------
	 * @author 130134
	 * @date 2014-3-10 下午5:37:40
	 * @param termsCode
	 * @param valueCode
	 * @return 
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao#queryDataDictionaryValueByTermsCodeValueCode(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DataDegreePostionValueEntity queryDataDictionaryValueByTermsCodeValueCode(String termsCode, String valueCode) {
		if (StringUtils.isBlank(termsCode) || StringUtils.isBlank(valueCode)) {
		    return null;
		}

		// 构造查询条件：
		DataDegreePostionValueEntity entity = new DataDegreePostionValueEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setType(termsCode);
		entity.setValueCode(valueCode);

		List<DataDegreePostionValueEntity> entitys = this.getSqlSession().selectList(NAMESPACE+ "queryDataDictionaryValueByTermsCodeValueCode",entity);
		if (CollectionUtils.isNotEmpty(entitys)) {
		    return entitys.get(0);
		}
		return null;
	}

	
	
	/***新增
	 * @author 130134
	 * @date 2014-3-10 下午5:38:08
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.dict.api.server.dao.IDataDegreePostionDao#addDataDictionaryValue(com.deppon.foss.module.base.dict.api.shared.domain.DataDegreePostionValueEntity)
	 */
	@Override
	public DataDegreePostionValueEntity addDataDictionaryValue(DataDegreePostionValueEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
		    return null;
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());
		// CreateUser为传入的用户编码，CreateDate为当前时间
		entity.setCreateDate(now);
		// ModifyDate为2999年，为一个常量
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setModifyUser(entity.getCreateUser());
		entity.setVersionNo(now.getTime());

		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "addDataDictionaryValue", entity);
		return result > 0 ? entity : null;
	}

}
