/**
 *  initial comments.
 *  
 *  	空运底价方案信息：
 *  
 *  1、	运价编号-航空公司下发的运价编号
 *  
 *  
 *  2、	配载部门-空运总调信息通过点击右边的“选择”按钮弹出配载部门界面
 *  
 *  
 *  3、	出发机场-根据航班号显示的某个出发城市机场，自动带出不可编辑。
 *  
 *  
 *  4、	航空公司-包含所有与德班业务往来的航空公司,根据录入的航班号默认自动带出不可修改
 *  
 *  
 *  5、	起飞时间 - 根据航班号显示，不可修改
 *  
 *  
 *  6、	到达时间-  根据航班号显示，不可修改
 *  
 *  
 *  7、	生效时间-当前配置信息开始时间
 *  
 *  
 *  8、	截止时间-当前配置信息结束时间
 *  
 *  
 *  9、	最低费用-当重量 * X费率 = X运费价格费率<最低费用时就用此处最低费用来代替目的为了控制成本价格
 *  
 *  
 *  10、	是否激活-选择是否激活可让管理员抉择是否立即激活本次维护的配置信息，默认为“否”
 *  
 *  
 *  11、	航班运费-默认显示按照重量标准费率表格支持增、删、改、导入、导出操作来管理运价费率。
 *  
 *  
 *  12、	备注描述： 备注信息
 *  
 *  
 *  	空运底价方案明细信息：
 *  
 *  
 *  	1.	目的地-到达机场所在城市
 *  
 *  
 *  	2.	货物类别-根据不同的航空公司显示不同货物类别提供选择录入
 *  
 *  
 *  	3.	航班号-航空公司规划的航班号
 *  
 *  
 *  	4.	最低一票-航空公司给出的最低一票
 *  
 *  
 *  	5.	45-3000kg在45-3000公斤固定范围内给予不同费率点设置（其规则一样不一一列举）
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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/FlightPricePlanService.java
 * 
 * FILE NAME        	: FlightPricePlanService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IFlightPricePlanService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.FlightPricePlanException;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 航空公司运价方案主信息维护
 * @author DP-Foss-YueHongJie
 * @date 2012-10-31 下午3:32:27
 */
public class FlightPricePlanDubboService implements IFlightPricePlanService {
    /**
     * 航空代理方案运价dao
     */
	@Inject
    IFlightPricePlanDao  flightPricePlanDao;

    /**
     * 设置 航空代理方案运价dao.
     *
     * @param flightPricePlanDao the new 航空代理方案运价dao
     */
    public void setFlightPricePlanDao(IFlightPricePlanDao flightPricePlanDao) throws FlightPricePlanException{
        this.flightPricePlanDao = flightPricePlanDao;
    }
    
    /**
     * 
     * <p>查询运价方案信息</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-25 下午4:03:51
     * 
     * @param airlinesCode
     * 
     * @param loadOrgCode
     * 
     * @param active
     * 
     * @param billDate
     * 
     * @return
     * 
     * @throws FlightPricePlanException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanService#findFlightPricePlanByCondition(java.lang.String, java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
    public com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanEntity findFlightPricePlanByCondition(String airlinesCode,
	    String loadOrgCode, String active, Date billDate)throws FlightPricePlanException{
	//航空运价实体bean 设置有效数据、 设置配载部门、设置营业部时间、
	FlightPricePlanDto dto = new FlightPricePlanDto();
	dto.setActive(FossConstants.ACTIVE);
	dto.setLoadOrgCode(loadOrgCode);
	dto.setBillDate(billDate);
	dto.setAirlinesCode(airlinesCode);
	//获取航空运价明细
	List<FlightPricePlanEntity> list = flightPricePlanDao.queryFlightPricePlans(dto);
	FlightPricePlanEntity flightPricePlanEntity = null;
	//避免调用时查询没有数据的情况出现报错
	if(CollectionUtils.isEmpty(list)){
	    flightPricePlanEntity = new FlightPricePlanEntity();
	}else{
	    flightPricePlanEntity =  list.get(0);
	}
	return this.convertorEntity(flightPricePlanEntity);
    }
    
    private com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanEntity convertorEntity(FlightPricePlanEntity flightPricePlanEntity){
    	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanEntity dubboEntity = 
    			new com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanEntity();
    	if(flightPricePlanEntity==null){
    		return null;
    	}
    	dubboEntity.setActive(flightPricePlanEntity.getActive());
    	dubboEntity.setAirlinesCode(flightPricePlanEntity.getAirlinesCode());
    	dubboEntity.setAirlinesName(flightPricePlanEntity.getAirlinesName());
    	dubboEntity.setAirport(flightPricePlanEntity.getAirport());
    	dubboEntity.setBeginTime(flightPricePlanEntity.getBeginTime());
    	dubboEntity.setBillDate(flightPricePlanEntity.getBillDate());
    	dubboEntity.setCreateDate(flightPricePlanEntity.getCreateDate());
    	dubboEntity.setCreateOrgCode(flightPricePlanEntity.getCreateOrgCode());
    	dubboEntity.setCreateUser(flightPricePlanEntity.getCreateUser());
    	dubboEntity.setCreateUserName(flightPricePlanEntity.getCreateUserName());
    	dubboEntity.setCurrencyCode(flightPricePlanEntity.getCurrencyCode());
    	dubboEntity.setCurrentUsedVersion(flightPricePlanEntity.getCurrentUsedVersion());
    	dubboEntity.setDescription(flightPricePlanEntity.getDescription());
    	dubboEntity.setEndTime(flightPricePlanEntity.getEndTime());
    	dubboEntity.setId(flightPricePlanEntity.getId());
    	dubboEntity.setLoadOrgCode(flightPricePlanEntity.getLoadOrgCode());
    	dubboEntity.setLoadOrgName(flightPricePlanEntity.getLoadOrgName());
    	dubboEntity.setModifyDate(flightPricePlanEntity.getModifyDate());
    	dubboEntity.setModifyOrgCode(flightPricePlanEntity.getModifyOrgCode());
    	dubboEntity.setModifyUser(flightPricePlanEntity.getModifyUser());
    	dubboEntity.setModifyUserName(flightPricePlanEntity.getModifyUserName());
    	dubboEntity.setPriceNo(flightPricePlanEntity.getPriceNo());
    	dubboEntity.setVersionNo(flightPricePlanEntity.getVersionNo());
    	return dubboEntity;
    }
}
