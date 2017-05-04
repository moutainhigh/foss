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
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressPriceRegionDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;


/**
 * 公共查询组件--快递价格区域DAO接口实现
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-8-12 下午7:01:06 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-8-12 下午7:01:06
 * @since
 * @version
 */
public class CommonExpressPriceRegionDao extends SqlSessionDaoSupport implements
	ICommonExpressPriceRegionDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.expressPricingRegion.";
    /** 
     * <p>分页查询快递价格区域</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-12 下午7:01:06
     * @param regionEntity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressPriceRegionDao#searchRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<PriceRegionEntity> searchRegionByCondition(
	    PriceRegionEntity entity, int start, int limit) {
	RowBounds rowBounds = new RowBounds(start, limit);

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos",
		entity, rowBounds);
    }

    /** 
     * <p>查询总记录数</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-12 下午7:01:06
     * @param regionEntity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressPriceRegionDao#countRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
     */
    @Override
    public Long countRegionByCondition(PriceRegionEntity entity) {
	return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCount",entity);
    }

}
