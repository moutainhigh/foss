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
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;
import com.deppon.foss.util.define.FossConstants;


/**
 * 汽运价格报表表头信息DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-10 上午10:09:15 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-10 上午10:09:15
 * @since
 * @version
 */
public class PriceReportTitleDao extends SqlSessionDaoSupport implements
	IPriceReportTitleDao {
    
    private static final String NAMESPACE = "foss.pkp.pkp-pricing.priceReportTitle.";

    /** 
     * 新增汽运价格报表表头信息
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 上午8:49:48
     * @param entity 汽运价格报表表头信息实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity)
     */
    @Override
    public int addInfo(PriceReportTitleEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据code作废汽运价格报表表头信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao#deleteInfo(java.util.List, java.lang.String)
     */
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	Date date = new Date();
	map.put("codes", codes);
	map.put("modifyUser", modifyUser);
	map.put("modifyDate", date);
	// 设置版本号
	map.put("versionNo", date.getTime());
	//设置启用状态为否
	map.put("active", FossConstants.INACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     * 根据传入对象查询符合条件所有汽运价格报表表头信息信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PriceReportTitleEntity> queryInfos(
	    PriceReportTitleEntity entity, int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",entity, rowBounds);
    }

    /** 
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param entity 汽运价格报表表头信息实体
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity)
     */
    @Override
    public Long queryRecordCount(PriceReportTitleEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",entity);
    }

    /** 
     * <p>根据ID查询汽运价格报表表头信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午5:07:43
     * @param id
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceReportTitleDao#queryInfoById(java.lang.String)
     */
    @Override
    public PriceReportTitleEntity queryInfoById(String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("id", id);
	map.put("active", FossConstants.ACTIVE);
	
	return (PriceReportTitleEntity)this.getSqlSession().selectOne(NAMESPACE + "queryInfoById", map);
    }

}
