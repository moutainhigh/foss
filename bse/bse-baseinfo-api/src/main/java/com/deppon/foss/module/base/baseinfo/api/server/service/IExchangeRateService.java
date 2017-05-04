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
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExchangeRateEntity;

/**
 * 汇率信息维护Service接口
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-4-15 下午2:53:33
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-4-15 下午2:53:33
 * @since
 * @version
 */
public interface IExchangeRateService extends IService {
    
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
     * 
     * <p>根据传入的人民币金额、外币代码、开单日期获取转汇后的外币金额</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-7-12 上午10:34:59
     * @param rmbFee 人民币金额
     * @param currencyCode 外币代码
     * @param billTime 开单日期
     * @return 转汇后的外币金额 
     * @see
     */
    BigDecimal getExchangedFee(BigDecimal rmbFee, String currencyCode, Date billTime);
    
    /**
     * 
     * 根据传入的外币代码、开单日期获得汇率
     * @param currencyCode 外币代码
     * @param billTime 开单日期
     * @return 汇率
     * @author 025000-FOSS-helong
     * @date 2013-7-15
     */
    BigDecimal getExchangedFeeRateByCurrencyCode(String currencyCode, Date billTime);
    /**
     * 
     *<p>查询根据币种的失效时间进行排序，查询失效时间最大的币种</p>
     *@author 130566-zengJunfan
     *@date   2013-9-16下午7:38:15
     * @param currency
     * @param id
     * @return
     */
    ExchangeRateEntity queryRateEntityByMaxEndTimeCurrency(String currency);

}
