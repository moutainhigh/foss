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
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 汇率信息维护Dao接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-15 下午3:28:44 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-15 下午3:28:44
 * @since
 * @version
 */
public class ExchangeRateDao extends SqlSessionDaoSupport implements
	IExchangeRateDao {
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.exchangeRate.";

    /** 
     * <p>新增汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:28:44
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#addExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)
     */
    @Override
    public int addExchangeRate(ExchangeRateEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * <p>修改汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:28:44
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#updateExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)
     */
    @Override
    public int updateExchangeRate(ExchangeRateEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /** 
     * <p>作废汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:28:46
     * @param virtualCodeList
     * @param modifyUser
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#deleteExchangeRateByVirtualCode(java.util.List, java.lang.String)
     */
    @Override
    public int deleteExchangeRateByVirtualCode(List<String> virtualCodeList,
	    String modifyUser) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("virtualCodeList", virtualCodeList);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", new Date());
	map.put("active", FossConstants.INACTIVE);
	map.put("active0", FossConstants.ACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * * 根据传入对象查询符合条件所有汇率信息
     * 
     * @author dp-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param entity
     *            汇率信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#queryAllExchangeRate(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExchangeRateEntity> queryAllExchangeRate(
	    ExchangeRateEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllExchangeRate",
		entity, rowBounds);
    }

    /** 
     * <p>统计总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:28:46
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity)
     */
    @Override
    public Long queryRecordCount(ExchangeRateEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }
    
    /**
     * <p>根据币种和id查询汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-27 上午11:30:39
     * @param currency 币种
     * @param id id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#queryRateEntityByCurrency(java.lang.String, java.lang.String)
     */
    @Override
    public ExchangeRateEntity queryRateEntityByCurrency(String currency,
	    String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("currency", currency);
	map.put("id", id);
	map.put("active", FossConstants.ACTIVE);
	
	return (ExchangeRateEntity)this.getSqlSession().selectOne(NAMESPACE + "queryRateEntityByCurrency", map);
    }
    
    /**
     * <p>根据币种查询失效时间最大的汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-25 下午5:19:50
     * @param currency 币种
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#queryRateEntityByMaxEndTimeCurrency(java.lang.String)
     */
    @Override
    public ExchangeRateEntity queryRateEntityByMaxEndTimeCurrency(
	    String currency,String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("currency", currency);
	map.put("id", id);
	
	return (ExchangeRateEntity)this.getSqlSession().selectOne(NAMESPACE + "queryRateEntityByMaxEndTimeCurrency", map);
    }
    
    /**
     * <p>根据币种编码、开单日期查询汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-12 上午11:38:44
     * @param currencyCode 币种编码
     * @param billTime 开单日期
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IExchangeRateDao#queryExchangeRate(java.lang.String, java.util.Date)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ExchangeRateEntity> queryExchangeRate(String currencyCode,
	    Date billTime) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("currency", currencyCode);
	map.put("billTime", billTime);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryExchangeRate", map);
    }
    /**
     * 
     *<p>根据币种和有效时间段 以及id查询信息</p>
     *@author 130566-zengJunfan
     *@date   2013-9-17下午1:45:37
     * @param entity
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExchangeRateEntity> queryExchangeRateBytimeAndCurrency(
			ExchangeRateEntity entity) {
		if(null ==entity){
			return null;
		}
		return this.getSqlSession().selectList(NAMESPACE+"queryExchangeRateBytimeAndCurrency",entity);
	}
	/**
	 * 
	 *<p>根据id查询信息实体</p>
	 *@author 130566-zengJunfan
	 *@date   2013-9-17下午3:27:39
	 * @param id
	 * @return
	 */
	@Override
	public ExchangeRateEntity queryExchangeRateById(String id) {
		if(StringUtils.isBlank(id)){
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("active", FossConstants.ACTIVE);
		return (ExchangeRateEntity) this.getSqlSession().selectOne(NAMESPACE +"queryExchangeRateById",map);
	}

}
