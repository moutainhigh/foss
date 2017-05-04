/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/shared/vo/FlightPriceVo.java
 * 
 * FILE NAME        	: FlightPriceVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.FlightEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OubrPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.OutbranchPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightDto;

/**
 * 快递代理价格方案VO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-7-26 下午2:43:07
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-7-26 下午2:43:07
 * @since
 * @version
 */
public class AgencyPricePlanVo implements Serializable {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -3034490915579301266L;

    /**
     * 快递代理公司运价方案实体类.
     */
    private PartbussPlanEntity partbussPlan;

    /**
     * 快递代理公司运价方案实体类.
     */
    private List<PartbussPlanEntity> partbussPlanList;

    /**
     * 快递代理网点运价方案实体类.
     */
    private OutbranchPlanEntity outbranchPlan;

    /**
     * 快递代理网点运价方案实体类集合.
     */
    private List<OutbranchPlanEntity> outbranchPlanList;
    
    /**
     * 快递代理网点运价方案明细实体类.
     */
    private OubrPlanDetailEntity oubrPlanDetail;
    
    /**
     * 快递代理网点运价方案明细实体类集合.
     */
    private List<OubrPlanDetailEntity> oubrPlanDetailList;

    /**
     * idList.
     */
    private List<String> idList;

    /**
     * 航班信息.
     */
    private FlightEntity flightEntity;

    /**
     * 航班信息DTO.
     */
    private List<FlightDto> flightDtoList;

    /**
     * 获取 航班信息DTO.
     * 
     * @return the 航班信息DTO
     */
    public List<FlightDto> getFlightDtoList() {
	return flightDtoList;
    }

    /**
     * 设置 航班信息DTO.
     * 
     * @param flightDtoList
     *            the new 航班信息DTO
     */
    public void setFlightDtoList(List<FlightDto> flightDtoList) {
	this.flightDtoList = flightDtoList;
    }

    /**
     * 获取 航班信息.
     * 
     * @return the 航班信息
     */
    public FlightEntity getFlightEntity() {
	return flightEntity;
    }

    /**
     * 设置 航班信息.
     * 
     * @param flightEntity
     *            the new 航班信息
     */
    public void setFlightEntity(FlightEntity flightEntity) {
	this.flightEntity = flightEntity;
    }

    /**
     * 获取 idList.
     * 
     * @return the idList
     */
    public List<String> getIdList() {
	return idList;
    }

    /**
     * 设置 idList.
     * 
     * @param idList
     *            the new idList
     */
    public void setIdList(List<String> idList) {
	this.idList = idList;
    }

    
    /**
     * 获取 快递代理公司运价方案实体类.
     *
     * @return  the partbussPlan
     */
    public PartbussPlanEntity getPartbussPlan() {
        return partbussPlan;
    }

    
    /**
     * 设置 快递代理公司运价方案实体类.
     *
     * @param partbussPlan the partbussPlan to set
     */
    public void setPartbussPlan(PartbussPlanEntity partbussPlan) {
        this.partbussPlan = partbussPlan;
    }

    
    /**
     * 获取 快递代理公司运价方案实体类.
     *
     * @return  the partbussPlanList
     */
    public List<PartbussPlanEntity> getPartbussPlanList() {
        return partbussPlanList;
    }

    
    /**
     * 设置 快递代理公司运价方案实体类.
     *
     * @param partbussPlanList the partbussPlanList to set
     */
    public void setPartbussPlanList(List<PartbussPlanEntity> partbussPlanList) {
        this.partbussPlanList = partbussPlanList;
    }

    
    /**
     * 获取 快递代理网点运价方案实体类.
     *
     * @return  the outbranchPlan
     */
    public OutbranchPlanEntity getOutbranchPlan() {
        return outbranchPlan;
    }

    
    /**
     * 设置 快递代理网点运价方案实体类.
     *
     * @param outbranchPlan the outbranchPlan to set
     */
    public void setOutbranchPlan(OutbranchPlanEntity outbranchPlan) {
        this.outbranchPlan = outbranchPlan;
    }

    
    /**
     * 获取 快递代理网点运价方案实体类集合.
     *
     * @return  the outbranchPlanList
     */
    public List<OutbranchPlanEntity> getOutbranchPlanList() {
        return outbranchPlanList;
    }

    
    /**
     * 设置 快递代理网点运价方案实体类集合.
     *
     * @param outbranchPlanList the outbranchPlanList to set
     */
    public void setOutbranchPlanList(List<OutbranchPlanEntity> outbranchPlanList) {
        this.outbranchPlanList = outbranchPlanList;
    }

    
    /**
     * 获取 快递代理网点运价方案明细实体类.
     *
     * @return  the oubrPlanDetail
     */
    public OubrPlanDetailEntity getOubrPlanDetail() {
        return oubrPlanDetail;
    }

    
    /**
     * 设置 快递代理网点运价方案明细实体类.
     *
     * @param oubrPlanDetail the oubrPlanDetail to set
     */
    public void setOubrPlanDetail(OubrPlanDetailEntity oubrPlanDetail) {
        this.oubrPlanDetail = oubrPlanDetail;
    }

    
    /**
     * 获取 快递代理网点运价方案明细实体类集合.
     *
     * @return  the oubrPlanDetailList
     */
    public List<OubrPlanDetailEntity> getOubrPlanDetailList() {
        return oubrPlanDetailList;
    }

    
    /**
     * 设置 快递代理网点运价方案明细实体类集合.
     *
     * @param oubrPlanDetailList the oubrPlanDetailList to set
     */
    public void setOubrPlanDetailList(List<OubrPlanDetailEntity> oubrPlanDetailList) {
        this.oubrPlanDetailList = oubrPlanDetailList;
    }
    

}