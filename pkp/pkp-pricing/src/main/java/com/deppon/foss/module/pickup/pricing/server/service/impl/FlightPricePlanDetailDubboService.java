/**
 *  initial comments.
 *  
 *  
 *  	空运底价方案信息：
 *  
 *  	1、	运价编号-航空公司下发的运价编号
 *  
 *  
 *  	2、	配载部门-空运总调信息通过点击右边的“选择”按钮弹出配载部门界面
 *  
 *  
 *  	3、	出发机场-根据航班号显示的某个出发城市机场，自动带出不可编辑。
 *  
 *  
 *  	4、	航空公司-包含所有与德班业务往来的航空公司,根据录入的航班号默认自动带出不可修改
 *  
 *  
 *  	5、	起飞时间 - 根据航班号显示，不可修改
 *  
 *  
 *  	6、	到达时间-  根据航班号显示，不可修改
 *  
 *  
 *  	7、	生效时间-当前配置信息开始时间
 *  
 *  
 *  	8、	截止时间-当前配置信息结束时间
 *  
 *  	9、	最低费用-当重量 * X费率 = X运费价格费率<最低费用时就用此处最低费用来代替目的为了控制成本价格
 *  
 *  
 *  	10、	是否激活-选择是否激活可让管理员抉择是否立即激活本次维护的配置信息，默认为“否”	
 *  
 *  
 *  	11、	航班运费-默认显示按照重量标准费率表格支持增、删、改、导入、导出操作来管理运价费率。
 *  
 *  
 *  	12、	备注描述： 备注信息
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
 *	4.	最低一票-航空公司给出的最低一票	
 *
 *
 *	5.	45-3000kg在45-3000公斤固定范围内给予不同费率点设置（其规则一样不一一列举）

 *  
 *  
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/FlightPricePlanDetailService.java
 * 
 * FILE NAME        	: FlightPricePlanDetailService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IFlightPricePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.FlightPricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.FlightPricePlanDto;
import com.deppon.foss.module.pickup.pricing.dubbo.api.server.service.IFlightPricePlanDetailService;
import com.deppon.foss.module.pickup.pricing.dubbo.api.shared.exception.FlightPricePlanDetailException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 代理航空公司运价方案明细接口,支持提供运价信息报价
 * 
 * @author DP-Foss-YueHongJie
 * @date 2012-11-1 下午2:03:37
 */
public class FlightPricePlanDetailDubboService implements IFlightPricePlanDetailService {

    /**
     * 批次服务
     */
    IFlightPricePlanDao flightPricePlanDao;

    /**
     * 明细服务
     */
    IFlightPricePlanDetailDao flightPricePlanDetailDao;
    
    
    /**
     * 行政区域
     */
    IAdministrativeRegionsService administrativeRegionsService;
    /**
     * 
     * 45
     * 
     */
    private static final int FORTY_FIVE = 45;
    
    /**
     * 
     * 100
     * 
     */
    private static final int ONE_HUNDRED = 100;
    
    /**
     * 
     * 300
     * 
     */
    private static final int THREE_HUNDRED = 300;
    
    /**
     * 
     * 500
     * 
     */
    private static final int FIVE_HUNDRED = 500;
    
    /**
     * 
     * 1000
     * 
     */
    private static final int ONE_THOUSAND = 1000;
    
    /**
     * 
     * 2000
     * 
     */
    private static final int TWO_THOUSAND = 2000;
    
    /**
     * 
     * 3000
     * 
     */
    private static final int THREE_THOUSAND = 3000;

	/**
	 * 
	 * 设置 行政区域.
	 *
	 * @param administrativeRegionsService the new 行政区域
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 设置 批次服务.
	 *
	 * @param flightPricePlanDao the new 批次服务
	 */
	public void setFlightPricePlanDao(IFlightPricePlanDao flightPricePlanDao) throws FlightPricePlanDetailException {
		this.flightPricePlanDao = flightPricePlanDao;
	}

	/**
	 * 设置 明细服务.
	 *
	 * @param flightPricePlanDetailDao the new 明细服务
	 */
	public void setFlightPricePlanDetailDao(IFlightPricePlanDetailDao flightPricePlanDetailDao)
			throws FlightPricePlanDetailException {
		this.flightPricePlanDetailDao = flightPricePlanDetailDao;
	}
    /**
     * 
     * 根据以下条件确定唯一航空代理公司运价方案明细信息，
     * 包括运价区间范围的实际费率信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @param flightCode 航班公司编码
     * 
     * @param loadOrgCode 配载部门编码
     * 
     * @param destDistictCode 到达站编码
     * 
     * @param goodsTypeCode 货物类型
     * 
     * @param billDate 当前日期 
     * 
     * @throws FlightPricePlanDetailException 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IFlightPricePlanDetailService#findFlightPricePlanDetail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal, java.util.Date)
     */
    @Override
    public com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity findFlightPricePlanDetail(String flightNo,String airlinesCode,String loadOrgCode, String destDistictCode, String goodsTypeCode,BigDecimal weight, Date billDate)
	    throws FlightPricePlanDetailException {
	if (!StringUtil.isNotEmpty(flightNo)) {
	    throw new FlightPricePlanDetailException("","输入的航班号不能为空!");
	}
	if (!StringUtil.isNotEmpty(airlinesCode)) {
	    throw new FlightPricePlanDetailException("","输入的航空公司编码不能为空!");
	}
	if (!StringUtil.isNotEmpty(loadOrgCode)) {
	    throw new FlightPricePlanDetailException("","输入配载部门编码不能为空!");
	}
	if(null == weight){
	    throw new FlightPricePlanDetailException("","输入货物重量不能为空!");
	}
	/* 第一步: 先根据航空公司与配载部门以及有效标记,录单时间获取该时间所符合的运价方案主信息 */
	FlightPricePlanDto flightPricePlanDto = new FlightPricePlanDto();
	flightPricePlanDto.setActive(FossConstants.ACTIVE);
	flightPricePlanDto.setAirlinesCode(airlinesCode);
	flightPricePlanDto.setLoadOrgCode(loadOrgCode);
	flightPricePlanDto.setBillDate(billDate);
	List<FlightPricePlanEntity> list = flightPricePlanDao.queryFlightPricePlans(flightPricePlanDto);
	if(CollectionUtils.isEmpty(list)){
	    throw new FlightPricePlanDetailException("","根据航空公司编码没有找到对应的费用数据");
	}
	//得到主方案信息
	FlightPricePlanEntity pricePlanEntity = list.get(0);
	String flightPricePlanId = pricePlanEntity.getId();
	/* 第二步: 根据主方案信息查询相关运价明细 */
	FlightPricePlanDetailEntity flightPricePlanDetailEntity = new FlightPricePlanDetailEntity();
	flightPricePlanDetailEntity = flightPricePlanDetailDao.queryUniquenessFlightPricePlanDetail(flightPricePlanId, destDistictCode, goodsTypeCode, flightNo, FossConstants.ACTIVE, FossConstants.CURRENCY_CODE_RMB);
	/**
	 * 以下根据调用者所传入重量weight判断落在对应价格区间段的实际运费
	 */
	if(null != flightPricePlanDetailEntity)
	{
	    double weightValue = weight.doubleValue();
	    /** 例如重量范围<=45KG,获取45公斤以下对应的运费...  */
	    if(weightValue < FORTY_FIVE)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getDown45Kg());
	    }
	    if(weightValue >= FORTY_FIVE && weightValue < ONE_HUNDRED)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp45Kg());
	    }
	    if(weightValue >= ONE_HUNDRED && weightValue < THREE_HUNDRED)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp100Kg());
	    }
	    if(weightValue >= THREE_HUNDRED && weightValue < FIVE_HUNDRED)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp300Kg());
	    }
	    if(weightValue >= FIVE_HUNDRED && weightValue < ONE_THOUSAND)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp500Kg());
	    }
	    if(weightValue >= ONE_THOUSAND && weightValue < TWO_THOUSAND)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp1000Kg());
	    }
	    if(weightValue >= TWO_THOUSAND && weightValue < THREE_THOUSAND)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp2000Kg());
	    }
	    if(weightValue >= THREE_THOUSAND)
	    {
		flightPricePlanDetailEntity.setCalculateFee(flightPricePlanDetailEntity.getUp3000Kg());
	    }
	}
	return this.convertorEntity(flightPricePlanDetailEntity);
    }
    
    private com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity convertorEntity(FlightPricePlanDetailEntity flightPricePlanDetailEntity){
    	com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity dubboEntity = 
    			new com.deppon.foss.module.pickup.pricing.dubbo.api.shared.domain.FlightPricePlanDetailEntity();
    	if(flightPricePlanDetailEntity==null){
    		return null;
    	}
    	dubboEntity.setActive(flightPricePlanDetailEntity.getActive());
    	dubboEntity.setAirlinesName(flightPricePlanDetailEntity.getAirlinesName());
    	dubboEntity.setArriveAirPort(flightPricePlanDetailEntity.getArriveAirPort());
    	dubboEntity.setArriveTime(flightPricePlanDetailEntity.getArriveTime());
    	dubboEntity.setBillDate(flightPricePlanDetailEntity.getBillDate());
    	dubboEntity.setCalculateFee(flightPricePlanDetailEntity.getCalculateFee());
    	dubboEntity.setCreateDate(flightPricePlanDetailEntity.getCreateDate());
    	dubboEntity.setCreateOrgCode(flightPricePlanDetailEntity.getCreateOrgCode());
    	dubboEntity.setCreateUser(flightPricePlanDetailEntity.getCreateUser());
    	dubboEntity.setCurrencyCode(flightPricePlanDetailEntity.getCurrencyCode());
    	dubboEntity.setDepartAirPort(flightPricePlanDetailEntity.getDepartAirPort());
    	dubboEntity.setDestDistrictCode(flightPricePlanDetailEntity.getDestDistrictCode());
    	dubboEntity.setDestDistrictName(flightPricePlanDetailEntity.getDestDistrictName());
    	dubboEntity.setDown45Kg(flightPricePlanDetailEntity.getDown45Kg());
    	dubboEntity.setFlightNo(flightPricePlanDetailEntity.getFlightNo());
    	dubboEntity.setFlightPricePlanId(flightPricePlanDetailEntity.getFlightPricePlanId());
    	dubboEntity.setGoodsTypeCode(flightPricePlanDetailEntity.getGoodsTypeCode());
    	dubboEntity.setId(flightPricePlanDetailEntity.getId());
    	dubboEntity.setLaunchTime(flightPricePlanDetailEntity.getLaunchTime());
    	dubboEntity.setMinFee(flightPricePlanDetailEntity.getMinFee());
    	dubboEntity.setModifyDate(flightPricePlanDetailEntity.getModifyDate());
    	dubboEntity.setModifyOrgCode(flightPricePlanDetailEntity.getModifyOrgCode());
    	dubboEntity.setModifyUser(flightPricePlanDetailEntity.getModifyUser());
    	dubboEntity.setUp1000Kg(flightPricePlanDetailEntity.getUp1000Kg());
    	dubboEntity.setUp100Kg(flightPricePlanDetailEntity.getUp100Kg());
    	dubboEntity.setUp2000Kg(flightPricePlanDetailEntity.getUp2000Kg());
    	dubboEntity.setUp3000Kg(flightPricePlanDetailEntity.getUp3000Kg());
    	dubboEntity.setUp300Kg(flightPricePlanDetailEntity.getUp300Kg());
    	dubboEntity.setUp45Kg(flightPricePlanDetailEntity.getUp45Kg());
    	dubboEntity.setUp500Kg(flightPricePlanDetailEntity.getUp500Kg());
    	dubboEntity.setVersionNo(flightPricePlanDetailEntity.getVersionNo());
    	return dubboEntity;
    }
}