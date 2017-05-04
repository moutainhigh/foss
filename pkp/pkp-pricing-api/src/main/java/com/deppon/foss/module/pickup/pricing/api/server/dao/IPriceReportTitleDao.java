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
package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceReportTitleEntity;


/**
 * 汽运价格报表表头信息DAO接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-10 上午10:05:36 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-10 上午10:05:36
 * @since
 * @version
 */
public interface IPriceReportTitleDao {
    
    /**
     * 新增汽运价格报表表头信息
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 上午8:49:48
     * @param entity 汽运价格报表表头信息实体
     * @return 1：成功；-1：失败
     * @see
     */
    int addInfo(PriceReportTitleEntity entity);
    
    /**
     * 根据code作废汽运价格报表表头信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param codes ID字符串集合
     * @param modifyUser
     * @return 1：成功；-1：失败 
     * @see
     */
    int deleteInfo(List<String> codes,String modifyUser);
    
    /**
     * 根据传入对象查询符合条件所有汽运价格报表表头信息信息 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<PriceReportTitleEntity> queryInfos(PriceReportTitleEntity entity,int limit,int start);
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午2:55:11
     * @param entity 汽运价格报表表头信息实体
     * @return
     * @see
     */
    Long queryRecordCount(PriceReportTitleEntity entity);
    
    /**
     * <p>根据ID查询汽运价格报表表头信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2014-1-10 下午5:07:43
     * @param id
     * @return
     * @see
     */
    PriceReportTitleEntity queryInfoById(String id);

}
