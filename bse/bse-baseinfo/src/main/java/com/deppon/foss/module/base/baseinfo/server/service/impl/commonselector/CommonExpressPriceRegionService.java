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
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressPriceRegionDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressPriceRegionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity;

/**
 * 公共组件--快递价格区域Service实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-8-12 下午6:42:51
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-8-12 下午6:42:51
 * @since
 * @version
 */
public class CommonExpressPriceRegionService implements
	ICommonExpressPriceRegionService {
    
    /**
     * 公共查询组件--快递价格区域DAO接口.
     */
    private ICommonExpressPriceRegionDao commonExpressPriceRegionDao;
    
    /**
     * 设置 公共查询组件--快递价格区域DAO接口.
     *
     * @param commonExpressPriceRegionDao the commonExpressPriceRegionDao to set
     */
    public void setCommonExpressPriceRegionDao(
    	ICommonExpressPriceRegionDao commonExpressPriceRegionDao) {
        this.commonExpressPriceRegionDao = commonExpressPriceRegionDao;
    }

    /**
     * <p>
     * 根据条件查询区域信息
     * </p>.
     *
     * @param regionEntity 查询条件
     * @param start 其实查询位置
     * @param limit 每页几条
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-8-12 下午6:42:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressPriceRegionService#searchRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity,
     * int, int)
     */
    @Override
    public List<PriceRegionEntity> searchRegionByCondition(
	    PriceRegionEntity regionEntity, int start, int limit) {
	
	return commonExpressPriceRegionDao.searchRegionByCondition(regionEntity, start, limit);
    }

    /**
     * <p>
     * 查询总记录数
     * </p>.
     *
     * @param regionEntity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-8-12 下午6:42:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressPriceRegionService#countRegionByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceRegionEntity)
     */
    @Override
    public Long countRegionByCondition(PriceRegionEntity regionEntity) {
	
	return commonExpressPriceRegionDao.countRegionByCondition(regionEntity);
    }

}
