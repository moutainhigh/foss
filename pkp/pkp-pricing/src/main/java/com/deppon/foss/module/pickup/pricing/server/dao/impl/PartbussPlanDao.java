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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理理公司运价方案Dao接口实现 
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 下午3:28:34 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 下午3:28:34
 * @since
 * @version
 */
public class PartbussPlanDao extends SqlSessionDaoSupport implements IPartbussPlanDao {
    
    private static final String NAMESPACE = "foss.pkp.pkp-pricing.partbussPlanEntity.";

    /** 
     * 新快递代理公司运价方案 
     * @author 094463-foss-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param entit快递代理递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#addInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public int addInfo(PartbussPlanEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }

    /** 
     * 根据cod快递代理快递代理公司运价方案 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param codes ID字符串数组
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#deleteInfo(java.lang.String[], java.lang.String)
     */
    @Override
    public int deleteInfo(List<String> codes, String modifyUser) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
    }

    /** 
     快递代理改快递代理公司运价方案 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param en快递代理y 快递代理公司运价方案实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#updateInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public int updateInfo(PartbussPlanEntity entity) {
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }

    /** 
     * 根据传入对象查询快递代理件所有快递代理公司运价方案信息 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#queryInfos(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PartbussPlanEntity> queryInfos(PartbussPlanEntity entity,
	    int limit, int start) {
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /** 
     * 统计总记录数 
     * @author dp-xieyantao
     * @date 2013-7-18 下午2:55:11
     * @param 快递代理ity 快递代理公司运价方案实体
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#queryRecordCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity)
     */
    @Override
    public Long queryRecordCount(PartbussPlanEntity entity) {
	
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",
		entity);
    }
    
    /**
     * <快递代理据ID查询快递代理公司运价方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-26 下午4:16:35
     * @param id 
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#queryInfoById(java.lang.String)
     */
    @Override
    public PartbussPlanEntity queryInfoById(String id) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("id", id);
	
	return (PartbussPlanEntity)this.getSqlSession().selectOne(NAMESPACE + "queryInfoById", map);
    }
    
    /**
   快递代理 <p>查询快递代理运价方案最大运价编号</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-10-21 上午8:55:11
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.dao.IPartbussPlanDao#queryMaxPriceNo()
     */
    @Override
    public String queryMaxPriceNo() {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	return (String)this.getSqlSession().selectOne(NAMESPACE + "queryMaxPriceNo",map);
    }

}
