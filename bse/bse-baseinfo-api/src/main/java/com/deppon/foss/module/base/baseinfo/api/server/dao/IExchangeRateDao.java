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
package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;


/**
 * 汇率信息维护Dao接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-15 下午3:20:17 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-15 下午3:20:17
 * @since
 * @version
 */
public interface IExchangeRateDao {
    
    /**
     * <p>新增汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:09:21
     * @param entity
     * @return
     * @see
     */
    int addExchangeRate(ExchangeRateEntity entity);
    
    /**
     * <p>修改汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:09:53
     * @param entity
     * @return
     * @see
     */
    int updateExchangeRate(ExchangeRateEntity entity);
    
    /**
     * <p>根据币种和id查询，生效时间段汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-27 上午11:30:39
     * @param currency 币种
     * @param id id
     * @return
     * @see
     */
    ExchangeRateEntity queryRateEntityByCurrency(String currency,String id);
    
    /**
     * <p>根据币种查询失效时间最大的汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-6-25 下午5:19:50
     * @param currency 币种
     * @return
     * @see
     */
    ExchangeRateEntity queryRateEntityByMaxEndTimeCurrency(String currency,String id);
    
    /**
     * <p>作废汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param virtualCodeList 虚拟编码集合
     * @param modifyUser 修改人编码
     * @return
     * @see
     */
    int deleteExchangeRateByVirtualCode(List<String> virtualCodeList,
	    String modifyUser);

    /**
     * 根据传入对象查询符合条件所有汇率信息
     * 
     * @author dp-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param entity
     *            汇率信息实体
     * @param limit
     *            每页最大显示记录数
     * @param start
     *            开始记录数
     * @return 符合条件的实体列表
     * @see
     */
    List<ExchangeRateEntity> queryAllExchangeRate(ExchangeRateEntity entity,
	    int limit, int start);

    /**
     * 统计总记录数
     * 
     * @author dp-xieyantao
     * @date 2013-4-15 下午3:10:32
     * @param entity
     *            汇率信息实体
     * @return
     * @see
     */
    Long queryRecordCount(ExchangeRateEntity entity);
    
    /**
     * <p>根据币种编码、开单日期查询汇率信息</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-12 上午11:38:44
     * @param currencyCode 币种编码
     * @param billTime 开单日期
     * @return
     * @see
     */
    List<ExchangeRateEntity> queryExchangeRate(String currencyCode,Date billTime);
    /**
     * 
     *<p>根据有效时间段，币种和id查询回来吧信息集合</p>
     *@author 130566-zengJunfan
     *@date   2013-9-17下午1:44:07
     * @param currencyCode
     * @param billTime
     * @return
     */
    List<ExchangeRateEntity> queryExchangeRateBytimeAndCurrency(ExchangeRateEntity entity);
    /**
     * 
     *<p>根据id查询汇率信息</p>
     *@author 130566-zengJunfan
     *@date   2013-9-17下午3:26:56
     * @param id
     * @return
     */
    ExchangeRateEntity queryExchangeRateById(String id);

}
