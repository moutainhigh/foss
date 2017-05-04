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
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 * 汇率维护信息实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-4-15 下午2:38:04 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-4-15 下午2:38:04
 * @since
 * @version
 */
public class ExchangeRateEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 7936219680927899630L;
    
    /**
     * 虚拟编码.
     */
    private String virtualCode;
    
    /**
     * 币种.
     */
    private String currency;
    
    /**
     * 汇率.
     */
    private BigDecimal exchange;
    
    /**
     * 生效时间，精确到天.
     */
    private Date beginTime;
    
    /**
     * 失效时间，精确到天.
     */
    private Date endTime;
    
    /**
     * 是否有效.
     */
    private String active;
    
    /**
     * 获取 虚拟编码.
     *
     * @return  the virtualCode
     */
    public String getVirtualCode() {
        return virtualCode;
    }
    
    /**
     * 设置 虚拟编码.
     *
     * @param virtualCode the virtualCode to set
     */
    public void setVirtualCode(String virtualCode) {
        this.virtualCode = virtualCode;
    }
    
    /**
     * 获取 币种.
     *
     * @return  the currency
     */
    public String getCurrency() {
        return currency;
    }
    
    /**
     * 设置 币种.
     *
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    /**
     * 获取 汇率.
     *
     * @return  the exchange
     */
    public BigDecimal getExchange() {
        return exchange;
    }
    
    /**
     * 设置 汇率.
     *
     * @param exchange the exchange to set
     */
    public void setExchange(BigDecimal exchange) {
        this.exchange = exchange;
    }
    
    /**
     * 获取 生效时间，精确到天.
     *
     * @return  the beginTime
     */
    public Date getBeginTime() {
        return beginTime;
    }
    
    /**
     * 设置 生效时间，精确到天.
     *
     * @param beginTime the beginTime to set
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    
    /**
     * 获取 失效时间，精确到天.
     *
     * @return  the endTime
     */
    public Date getEndTime() {
        return endTime;
    }
    
    /**
     * 设置 失效时间，精确到天.
     *
     * @param endTime the endTime to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    /**
     * 获取 是否有效.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否有效.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    

}
