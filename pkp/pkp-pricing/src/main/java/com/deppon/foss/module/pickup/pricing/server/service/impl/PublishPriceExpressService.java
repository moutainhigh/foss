/**
 *  查询界面主要分为3个部分查询条件区域、查询结果列。
 *  
*1.	查询条件区域：
*
* 出发城市、目的地
* 
* a)	出发城市：
* 
*出发城市默认是当前人员所在城市，
*
*也可以进行选择。
*
*b)	目的地：
*
*目的地是指货物运输的目的地。
*
*可以选择选择国家、省、市、区县以及填写地址来查询目的城市的网点。
*
*2.	功能按钮：  
*
*查询按钮、退出按钮。
*
*a)	查询：
*
*查询条件符合的列表数据。
*
*b)	退出：
*
*点击此按钮，退出当前页面回到主界面。
*
*3.	查询结果列表：
*
*目的城市、产品信息、重货价格、轻货价格、运营时效、
*
*取货时间、长短途、取货时间、最低一票、备注信息。
*
*
*通过公布价菜单进入此页面，
*
*默认当前营业部门所在城市，
*
*录入查询条件后，
*
*点击查询时，
*
*
*将符合的查询条件的信息列表展示在下面。
*
*点击城市超链接，
*
*会弹出新的窗口，
*
*该窗口展示网点信息。
*
*该网点信息的展现规则是：
*
*1.当只选择了城市层级，
*
*则根据GIS里城市所辖的全部网点返回网点信息。
*
*2.当选择到区县层级，
*
*则根据GIS里区县所辖的全部网点返回网点信息。
*
*3.当填写地址信息，
*
*则根据选择的信息以及填写的地址信息从GIS里所查询的全部网点返回网点信息。
*
*
*
*SR1	查询支持条件组合查询,
*
*目的地通过选择下拉列表中的类型来支持不同的查询。
*
*如果选择城市则按填写的相应城市所对应的区域进行查询；
*
*如果选择地址，
*
*则按填写地址通过GIS进行查找相对应的网点，
*
*并以这些网点对应的区域进行查询
*
*SR2	公布价后台查询规则描述： 
*
*请参考1.5.4逻辑图，
*
*根据部门信息到区域中去查找且按照异常优先级
*
*（先找区域类型为国标区域与部门所维护的区域，
*
*再找部门所在城市信息，
*
*最后找部门所在省份信息直到找到对应的区域为止）
*
*来确定最终区域信息，
*
*定位到了区域信息就可以分别找区域类型了，
*
*它包括了时效区域与价格区域，
*
*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
*
*如果时效区域信息在时效版本里面没有找到相关的信息，
*
*那么基于该区域的公布价就不用显示了，
*
*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
*
*SR3	
*
*查询列表中重货价格以公斤计算。
*
*SR4
*
*	查询列表中轻货价格以体积计算。
*
*SR5	
*
*查询列表中营运时效对客户承诺的时间。
*
*
*SR6	
*
*公布价信息中的最低一票信息是根据区域和产品条目信息关联
*
*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
*
*与产品-V0.1.doc 中所维护的最低一票而来的。

 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PublishPriceService.java
 * 
 * FILE NAME        	: PublishPriceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SaleDepartmentInfoDto;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPublishPriceExpressDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCriteriaDetailService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.util.GisInterfaceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PublishPriceExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectiveExpressPlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublishPriceExpressDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublishPriceExpressDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PublishPriceException;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 公布价查询Service
 * 
 * 	1、按照始发部门信息查询对应所有目的站的时效,价格信息 - 满足公布价SUC查询服务用例
 * 
 * 	2、按照始发城市信息获取所有目的站时效,价格信息 -   满足网银接口服务方法
 * 
 * 	3、按照始发部门、产品、货物类型查询相关目的站的时效价格-满足GUI开单时候所需要的价格、时效信息
 * 
 * @author 岳洪杰
 * 
 * @date 2012-10-12 上午8:43:02
 * 
 * @since
 * 
 * @version
 */
public class PublishPriceExpressService implements IPublishPriceExpressService {

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(PublishPriceExpressService.class);
	 /**
     * 时效详细信息接口
     */
    @Inject
    private IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService;
    /**
     * 计价方案明细接口
     */
    @Inject
    private IPriceCriteriaDetailService priceCriteriaDetailService;
    /**
     * 组织机构接口
     */
    @Inject
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    /**
     * 产品接口
     */
    @Inject
    private IGoodsTypeService goodsTypeService;
    /**
     * 产品接口
     */
    @Inject
    private IProductService productService;

    /**
     * 区域接口
     */
    @Inject
    private IRegionExpressService regionExpressService;
    /**
     * 公布价DAO
     */
    @Inject
    private IPublishPriceExpressDao publishPriceExpressDao;
    /**
     * 网点综合Dao
     */
    @Inject
    private IPricingOrgDao pricingOrgDao;
    
    private DistrictRegionCacheDeal districtRegionCacheDeal;
    
    @Inject
    private IDistrictRegionService districtRegionService;
    /**
     * 营业部DAO
     */
    @Inject
    private ISaleDepartmentDao saleDepartmentDao;
    /**
     * 代理DAO
     */
    @Inject
    private IVehicleAgencyDeptDao vehicleAgencyDeptDao;
    /**
     * 行政区域接口
     */
    @Inject
    private IAdministrativeRegionsService  administrativeRegionsService;
    
    /**
     * 调用gis链接
     */
    @Inject
    private String gisUrl;
    
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
     * 方法（一）
     * 
     * 
     * <p>(查询公布价信息，根据条件始发部门到目的站所有相关最新的公布价信息,其中包括
     * 
     * 
     * 出发逻辑区域与到达逻辑区域之间承诺时效、取货时间、最低一票、重货、轻货费率)</p> 
     * 
     * @author 岳洪杰
     * 
     * 
     * @date 2012-10-12 上午8:46:51
     * 
     * 
     * @param startDeptNo-始发部门编号
     * 
     * 
     * @param destinationCode-目的地编码
     * 
     * 
     * @return 
     * 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService#queryPublishPriceDetail(java.lang.String, java.lang.String)
     * 
     */
    @Override
    public List<PublishPriceExpressEntity> queryPublishPriceDetail(String startDeptNo, String destinationCode, Date billDate) {
    	LOGGER.info("queryPublishPriceDetail 1 startTime >>"+ new Date());
	    //(1) 时效
    	//根据出发部门Code，到达目的地获取
    	//（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
    	List<EffectiveExpressPlanDto> effectInfoList = effectiveExpressPlanDetailService.queryEffectiveExpressPlanDetailListByOrgCode(startDeptNo,
    			destinationCode, null, billDate);
	    //(2) 价格
		//根据出发部门Code,到达目的地获取
    	//（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
    	List<PublishPriceExpressDto> priceInfoList = this.queryCuurentPublishPriceDetailInfo(startDeptNo, destinationCode, billDate, PricingConstants.VALUATION_TYPE_PRICING);
		List<PublishPriceExpressEntity> voList = new ArrayList<PublishPriceExpressEntity>();
		String arrvName = null;
		if(SqlUtil.loadCache){
        		CommonOrgEntity arrvCommonOrgEntity = pricingOrgDao.queryOrgByCode(destinationCode, FossConstants.ACTIVE);
        		if(arrvCommonOrgEntity != null) {
        			arrvName = arrvCommonOrgEntity.getName();
        		}
		}else{
		  arrvName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(destinationCode);
		}
		//根据startDeptNo获取组织名字
		String startName = null;
		if(SqlUtil.loadCache){
        		CommonOrgEntity startCommonOrgEntity = pricingOrgDao.queryOrgByCode(startDeptNo, FossConstants.ACTIVE);
        		if(startCommonOrgEntity != null) {
        			startName = startCommonOrgEntity.getName();
        		}
		}else{
		    startName = orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(startDeptNo);
		}
		//合并价格和时效信息
		encapulatePublishPrice(effectInfoList, priceInfoList, voList, arrvName, startName);
		LOGGER.info("queryPublishPriceDetail 1 endTime >>"+ new Date());
		//返回结果集合
		return voList;
    }
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * <p>
	 * Description:组装公布价<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-11-2
	 * 
	 * @param effectInfoList
	 * 
	 * @param priceInfoList
	 * 
	 * @param voList
	 * 
	 * @param arrvName
	 * 
	 * @param startName
	 * 
	 * void
	 */
	private void encapulatePublishPrice(List<EffectiveExpressPlanDto> effectInfoList, List<PublishPriceExpressDto> priceInfoList,
			List<PublishPriceExpressEntity> voList, String arrvName, String startName) {
		PublishPriceExpressEntity publishPrice = null;
		if(CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectiveExpressPlanDto> effectivePlanDtos = new ArrayList<EffectiveExpressPlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
					for(int j = 0; j<priceInfoList.size(); j++){
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
						PublishPriceExpressDto priceDto = (PublishPriceExpressDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
				    	if(effParentCode.equals(priceDto.getProductCode())){			
				    		publishPrice = new PublishPriceExpressEntity();
				    		publishPrice.setLongOrShort(effectiveDto.getLongOrShort());
							publishPrice.setAddDay(effectiveDto.getAddDay());
				    		publishPrice.setProductCode(priceDto.getProductCode());
							publishPrice.setProductName(priceDto.getProductName());
							publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
							publishPrice.setStartDept(startName);
							publishPrice.setDeptRegionId(effectiveDto.getDeptRegionId());
							publishPrice.setArrvRegionName(arrvName);
							publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
							publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
							publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
							publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
							
							publishPrice.setMaxTime(effectiveDto.getMaxTime());
							publishPrice.setMaxTimeUnit(effectiveDto.getMaxTimeUnit());
							publishPrice.setMinTime(effectiveDto.getMinTime());
							publishPrice.setMinTimeUnit(effectiveDto.getMinTimeUnit());
							//deliveryTime 派送时间
							publishPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
							/**
							 * 设置取货时间
							 */
							if(effectiveDto.getArriveTime()!=null && !"".equals(effectiveDto.getArriveTime())){
								if(effectiveDto.getArriveTime().length()>2){
									String hours=effectiveDto.getArriveTime().substring(0,2);
									String minutes=effectiveDto.getArriveTime().substring(2,effectiveDto.getArriveTime().length());
									String pickupTime=hours+":"+minutes;
									publishPrice.setPickupTime(pickupTime);
								}
								
							}

							publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + 
									effectiveDto.getMaxTime());	
							publishPrice.setProductItemCode(effectiveDto.getProductCode());
							publishPrice.setProductItemName(effectiveDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
							
							/**
							 * 添加价格信息
							 */
							PublishPriceExpressDetailDto publishPriceExpressDetail;
							for(int k=0; k<priceDto.getPublishPriceExpressDetail().size();k++){
								publishPriceExpressDetail = priceDto.getPublishPriceExpressDetail().get(k);
								if(k==0){//首重
									publishPrice.setFirstWeight(publishPriceExpressDetail.getFirstWeight());
									publishPrice.setWeightLowLimit(publishPriceExpressDetail.getWeightOffline());//首重下线
									publishPrice.setWeightHighLimit(publishPriceExpressDetail.getWeightOnline());//首重上限
								}else if(k==1){
									
									publishPrice.setWeightOffline1(publishPriceExpressDetail.getWeightOffline());
									publishPrice.setWeightOnline1(publishPriceExpressDetail.getWeightOnline());
									publishPrice.setFeeRate1(publishPriceExpressDetail.getFeeRate());
								}else if(k==2){
									publishPrice.setWeightOffline2(publishPriceExpressDetail.getWeightOffline());
									publishPrice.setWeightOnline2(publishPriceExpressDetail.getWeightOnline());
									publishPrice.setFeeRate2(publishPriceExpressDetail.getFeeRate());
								}
							}
							effectivePlanDtos.add(effectiveDto);
							voList.add(publishPrice);
						}
				    }
				}
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {
							continue;
						} 
						publishPrice = new PublishPriceExpressEntity();
						publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
						publishPrice.setStartDept(startName);
						publishPrice.setDeptRegionId(effectiveDto.getDeptRegionId());
						publishPrice.setArrvRegionName(arrvName);
						publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
						publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
						publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
						publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
						publishPrice.setPickupTime(effectiveDto.getArriveTime());
						publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
					    publishPrice.setProductItemName(effectiveDto.getProductName());
						publishPrice.setProductItemCode(effectiveDto.getProductCode());
						//deliveryTime 派送时间
						publishPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
						voList.add(publishPrice);
					}
				}
				//时效区域全部未匹配的情况
			} else {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
					publishPrice = new PublishPriceExpressEntity();
					publishPrice.setCreateDate(new Date(System.currentTimeMillis()));
					publishPrice.setStartDept(startName);
					publishPrice.setDeptRegionId(effectiveDto.getDeptRegionId());
					publishPrice.setArrvRegionName(arrvName);
					publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
					publishPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
					publishPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
					publishPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
					publishPrice.setPickupTime(effectiveDto.getArriveTime());
					publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
					publishPrice.setProductItemName(effectiveDto.getProductName());
					publishPrice.setProductItemCode(effectiveDto.getProductCode());
					//deliveryTime 派送时间
					publishPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
					voList.add(publishPrice);
				}
			}
		}
	}

	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
     * 
     * <p>
     * (根据始发部门编号与目的站区域编号查询时效方案明细信息)
     * </p>
     * 
     * @author sz
     * 
     * @date 2012-12-29
     * 
     * @param deptNo
     *            - 始发站编号
     *            
     * @param arrvieNo
     *            - 目的信息
     *            
     * @billDate 开单日期
     * 
     * @return billDate 开单日期
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanDetailService#
     * 
     * queryCuurentEffectivePlanDetailInfo(java.lang.String,
     *      java.lang.String)
     */
    private List<PublishPriceExpressDto> queryCuurentPublishPriceDetailInfo(
	    String deptNo, String arrvieNo,  Date billDate, String type) {
		//判断是否有业务时间
		//如果没有业务时间NEW新值
		Date currentDateTime = null;
		if (billDate == null) {
		    currentDateTime = new Date();
		} else {
		    currentDateTime = billDate;
		}
    	// 根据出发部门编号定位价格逻辑区域
    	String deptRegionId = regionExpressService.findRegionOrgByDeptNo(deptNo, currentDateTime, null,PricingConstants.PRICING_REGION);
		if(StringUtils.isEmpty(deptRegionId)) {
		    return null;
		}
		// 根据到达部门编号定位价格逻辑区域
    	String arrvieRegionId = regionExpressService.findRegionOrgByDeptNo(arrvieNo, currentDateTime,null, PricingConstants.PRICING_REGION);
		if(StringUtils.isEmpty(arrvieRegionId)) {
		    return null;
		}
		//查询汽运信息
		PublishPriceExpressDto publishPriceExpressDto = new PublishPriceExpressDto(); 
		publishPriceExpressDto.setDeptRegionId(deptRegionId);
		publishPriceExpressDto.setArrvRegionId(arrvieRegionId);
		
//publishPriceExpressDto.setDeptRegionId("5a6f1c2a-60fd-4f87-bf59-379d5a357140");
//publishPriceExpressDto.setArrvRegionId("5118f4b0-e550-4a50-8c0b-af942baeb7e7");
		publishPriceExpressDto.setReceiveDate(currentDateTime);
		publishPriceExpressDto.setValuationType(type);
		publishPriceExpressDto.setActive(FossConstants.ACTIVE);
		// 根据最新时效方案版本查询相关时效方案详细信息
		
		List<PublishPriceExpressDto> publishPriceDtos = publishPriceExpressDao.queryPublishPriceValuationByCalculaCondition(publishPriceExpressDto);
		
		
		if(CollectionUtils.isNotEmpty(publishPriceDtos)) {
			publishPriceDtos = this.boxCatalogName(publishPriceDtos);
		}
		//返回集合
		return publishPriceDtos;
    }

/**
 * 封装货物类型名字    
 * @param publishPriceDtos
 * @return
 */
	private List<PublishPriceExpressDto> boxCatalogName(
			List<PublishPriceExpressDto> publishPriceDtos) {
		List<PublishPriceExpressDto> list = null;
		if(CollectionUtils.isNotEmpty(publishPriceDtos)) {
			list = new ArrayList<PublishPriceExpressDto>();
			Map<String, String> goodsTypeNames = getUtilGoodsTypeNames(publishPriceDtos);
			for (int i = 0; i < publishPriceDtos.size(); i++) {
				PublishPriceExpressDto productPriceDto = publishPriceDtos.get(i);
				//封装货物类型NAME
				if(StringUtil.isNotBlank(productPriceDto.getGoodsTypeCode())) {
					String goodsTypeName = goodsTypeNames.get(productPriceDto.getGoodsTypeCode());
					if(StringUtil.isNotBlank(goodsTypeName)) {
						productPriceDto.setGoodsTypeName(goodsTypeName);
						list.add(productPriceDto);
					}
				}
			}
		}
		return list;
	}
	/**
	 * @Description: 根据集合中的货物类型CODE查询货物类型NAME
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-28 上午9:48:26
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Map<String, String> getUtilGoodsTypeNames(List<PublishPriceExpressDto> list) {
		Map<String, String> goodsTypeMap = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			String goodsTypeCode = list.get(i).getGoodsTypeCode();
			if(StringUtil.isNotBlank(goodsTypeCode)) {
				GoodsTypeEntity goodsTypeEntity = goodsTypeService.getGoodsTypeByCache(goodsTypeCode, new Date());
				if(goodsTypeEntity != null && goodsTypeEntity.getName() != null) {
					goodsTypeMap.put(goodsTypeEntity.getCode(), goodsTypeEntity.getName());
				}
			}
		}
		return goodsTypeMap;
	}
	/**
	 * 设置 计价方案明细接口.
	 *
	 * @param priceCriteriaDetailService the new 计价方案明细接口
	 */
	public void setPriceCriteriaDetailService(IPriceCriteriaDetailService priceCriteriaDetailService) {
		this.priceCriteriaDetailService = priceCriteriaDetailService;
	}
	
	/**
	 * 设置 时效详细信息接口.
	 *
	 * @param effectiveExpressPlanDetailService the new 时效详细信息接口
	 */
	public void setEffectiveExpressPlanDetailService(
			IEffectiveExpressPlanDetailService effectiveExpressPlanDetailService) {
		this.effectiveExpressPlanDetailService = effectiveExpressPlanDetailService;
	}

	/**
	 * 设置 区域接口.
	 *
	 * @param regionExpressService the new 区域接口
	 */
	public void setRegionExpressService(IRegionExpressService regionExpressService) {
		this.regionExpressService = regionExpressService;
	}

	/**
	 * 设置 公布价DAO.
	 *
	 * @param publishPriceDao the new 公布价DAO
	 */
	public void setPublishPriceExpressDao(
			IPublishPriceExpressDao publishPriceExpressDao) {
		this.publishPriceExpressDao = publishPriceExpressDao;
	}
	
	/**
	 * 设置 组织机构接口.
	 *
	 * @param orgAdministrativeInfoService the new 组织机构接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	@Override
	public List<PublishPriceExpressEntity> queryPublishPriceExpressDetailByCondition(
			String startCityCode, String destinationCountryCode,
			String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress,
			Date billDate) {
		List<PublishPriceExpressEntity> voList = null;
		// 根据始发网点查询和封装区域数据
		Map<String, Map> startAreaMap = null;
		// 根据目的网点查询和封装区域数据
		Map<String, Map> arrvAreaMap = null;
		try {
			this.validateAreaInfo(startCityCode, destinationCountryCode, destinationProvinceCode, destinationCityCode);
			if (billDate == null) {
				billDate = new Date();
			}
			startAreaMap = buildUpDistrictRegion(startCityCode);
			if(startAreaMap.size() < 1) {
				districtRegionService.addDistrictRegion(startCityCode);
				startAreaMap = buildUpDistrictRegion(startCityCode);
				if(startAreaMap.size() < 1) {
					AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
					if(administrativeRegionsEntity != null) {
						if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
							if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
								startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
								if(startAreaMap.size() < 1) {
									districtRegionService.addDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
									startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
								}
							}
						}
					}
				}
			}
			if(startAreaMap.size() < 1) {
				return null;
			}
			
			List<String> unifiedDeptList = null;
			String provinceName = null;
			String cityName = null;
			String countyName = null;
			// 根据省CODe查询省名称
			provinceName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationProvinceCode);
			// 根据市CODe查询市名称
			cityName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationCityCode);
			// 根据区县CODe查询区县名称
			countyName = administrativeRegionsService.queryAdministrativeRegionsNameByCode(destinationCountyCode);
			// 根据出发城市CODe查询其下的网点信息
			List<String> arrvDeptList = null;
			if (StringUtil.isNotBlank(destinationAddress)) {
				// 从GIS获取营业网点
				try {
					unifiedDeptList = this.getCompositeStationCodes(provinceName, cityName, countyName, destinationAddress, gisUrl);
				} catch (Exception e) {
					LOGGER.error("输入参数为:" + provinceName + "|" + cityName + "|" + countyName + "|" + destinationAddress + "|" + gisUrl);
					LOGGER.error("调用GIS接口出现错误:" + e);
				}
				if (CollectionUtils.isNotEmpty(unifiedDeptList)) {
					arrvDeptList = new ArrayList<String>();
					List<CommonOrgEntity> orgList = pricingOrgDao.queryOrgByBatchCodes(null, unifiedDeptList);
					List<CommonOrgEntity> otherOrgList = pricingOrgDao.queryOrgByBatchCodes(unifiedDeptList, null);
					if (CollectionUtils.isNotEmpty(orgList)) {
						for (CommonOrgEntity commonOrgEntity : orgList) {
							arrvDeptList.add(commonOrgEntity.getCode());
						}
					}
					if (CollectionUtils.isNotEmpty(otherOrgList)) {
						for (CommonOrgEntity commonOrgEntity : otherOrgList) {
							arrvDeptList.add(commonOrgEntity.getCode());
						}
					}
				}
				if(arrvDeptList != null){
					arrvAreaMap = buildUpDeptRegion(arrvDeptList);
				}
			} else {
				// 城市或区县查询营业网点
				if (StringUtil.isNotBlank(destinationCountyCode)) {
					arrvAreaMap = buildUpDistrictRegion(destinationCountyCode);
					if(arrvAreaMap.size() < 1) {
						districtRegionService.addDistrictRegion(destinationCountyCode);
						arrvAreaMap = buildUpDistrictRegion(destinationCountyCode);
						if(arrvAreaMap.size() < 1) {
							arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
							if(arrvAreaMap.size() < 1) {
								districtRegionService.addDistrictRegion(destinationCityCode);
								arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
							}
						}
					}
				} else {
					arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
					if(arrvAreaMap.size() < 1) {
						districtRegionService.addDistrictRegion(destinationCityCode);
						arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
					}
				}
			}
			if(arrvAreaMap.size() < 1) {
				return null;
			}
			// 起始时效区域
			Map<String, List<String>> effectiveStartRegionMap = startAreaMap.get("expressEffectiveRegionMap");
			// 目的时效区域
			Map<String, List<String>> effectiveArrvRegionMap = arrvAreaMap.get("expressEffectiveRegionMap");
			// 起始时效区域名称
			Map<String, String> effectiveStartRegionNameMap = startAreaMap.get("expresseffectiveRegionNameMap");
			// 目的时效区域名称
			Map<String, String> effectiveArrvRegionNameMap = arrvAreaMap.get("expresseffectiveRegionNameMap");
			// 起始价格区域
			Map<String, List<String>> priceStartRegionMap = startAreaMap.get("expressPriceRegionMap");
			// 目的价格区域
			Map<String, List<String>> priceArrvRegionMap = arrvAreaMap.get("expressPriceRegionMap");
			// 起始价格区域名称
			Map<String, String> priceStartRegionNameMap = startAreaMap.get("expressPriceRegionNameMap");
			// 目的价格区域名称
			Map<String, String> priceArrvRegionNameMap = arrvAreaMap.get("expressPriceRegionNameMap");
			
			//定义始发价格区域合并SET,用于合并汽运价格区域和空运价格区域
			Set priceStartRegion = new HashSet();
			//定义目的价格区域合并SET,用于合并汽运价格区域和空运价格区域
			Set priceArrvRegion = new HashSet();
			// 合并始发汽运价格区域
			if(priceStartRegionMap != null && priceStartRegionMap.size() > 0) {
				priceStartRegion.addAll(priceStartRegionMap.keySet());
			}
			// 合并目的汽运价格区域
			if(priceArrvRegionMap != null && priceArrvRegionMap.size() > 0) {
				priceArrvRegion.addAll(priceArrvRegionMap.keySet());
			}
			// (1) 时效
			// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
			List<EffectiveExpressPlanDto> effectInfoList = null;
			if(effectiveStartRegionMap != null && effectiveArrvRegionMap != null) {
				effectInfoList = effectiveExpressPlanDetailService.queryEffectiveExpressPlanDetailListByCondition(effectiveStartRegionMap.keySet(),
						effectiveArrvRegionMap.keySet(), null, billDate);
			}
			// (2) 价格
			// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
			List<PublishPriceExpressDto> priceInfoList = null;
 			if(CollectionUtils.isNotEmpty(priceStartRegion) && CollectionUtils.isNotEmpty(priceArrvRegion)) {
				priceInfoList = this.queryCurrentPublishPriceByRegionIds(priceStartRegion, priceArrvRegion,
						PricingConstants.VALUATION_TYPE_PRICING, billDate);
			}
			//组装时效和价格公布价
			if(CollectionUtils.isNotEmpty(effectInfoList) || CollectionUtils.isNotEmpty(priceInfoList)) {
				voList = new ArrayList<PublishPriceExpressEntity>();
				// 根据destinationCode获取组织名字
				assemblePublicPrice(effectInfoList, priceInfoList, voList);
			}
			//封装相关信息
			if (CollectionUtils.isNotEmpty(voList)) {
				for (int i = 0; i < voList.size(); i++) {
					PublishPriceExpressEntity publicPriceDto = voList.get(i);
					publicPriceDto.setId(UUIDUtils.getUUID());
						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
						//始发价格区域name
						publicPriceDto.setDeptPriceRegionName(priceStartRegionNameMap.get(publicPriceDto.getDeptPriceRegionId()));
						//到达价格区域name
						publicPriceDto.setArrvPriceRegionName(priceArrvRegionNameMap.get(publicPriceDto.getArrvPriceRegionId()));
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("queryPublishPriceExpressDetailByCondition  >>" + e);
		}
		return voList;
	}
	
	
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 组装本地时效价格
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-24 下午1:59:14
	 * 
	 * @param effectInfoList
	 * 
	 * @param priceInfoList
	 * 
	 * @param voList
	 * 
	 * @version V1.0
	 */
	private void assemblePublicPrice(List<EffectiveExpressPlanDto> effectInfoList,
			List<PublishPriceExpressDto> priceInfoList, List<PublishPriceExpressEntity> voList) {
		PublishPriceExpressEntity publishPrice = null;
		if (CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectiveExpressPlanDto> effectivePlanDtos = new ArrayList<EffectiveExpressPlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
				    for(int j = 0; j<priceInfoList.size(); j++){
				    	PublishPriceExpressDto priceDto = (PublishPriceExpressDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
						if(effParentCode.equals(priceDto.getProductCode())){	
							publishPrice = new PublishPriceExpressEntity();
							//deliveryTime 派送时间
							publishPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
							//pickTime取货时间 自提时间
							publishPrice.setPickupTime(getStandardTime(effectiveDto.getArriveTime()));
							//arriveTime 营运时效 承诺到达营业部时间
							publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
							//productItemName产品name
							publishPrice.setProductItemName(effectiveDto.getProductName());
							//货物类型CODE
							publishPrice.setGoodsTypeCode(priceDto.getGoodsTypeCode());
							//货物类型name
							publishPrice.setGoodsTypeName(priceDto.getGoodsTypeName());
							publishPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());//到达区域code
							publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());//到达区域id
							publishPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());//始发区域code
							publishPrice.setDeptRegionId(effectiveDto.getDeptRegionId());	//始发区域id
					
							publishPrice.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
							/**
							 * 添加价格信息
							 */
							PublishPriceExpressDetailDto publishPriceExpressDetail;
							if(null!=priceDto.getPublishPriceExpressDetail()){
								publishPrice.setArrvPriceRegionCode(priceDto.getArrvRegionCode());//到达价格区域code
								publishPrice.setArrvPriceRegionId(priceDto.getArrvRegionId());//到达价格区域id
								publishPrice.setDeptPriceRegionCode(priceDto.getDeptRegionCode());//始发价格区域code
								publishPrice.setDeptPriceRegionId(priceDto.getDeptRegionId());//始发价格区域id
								for(int k=0; k<priceDto.getPublishPriceExpressDetail().size();k++){
									publishPriceExpressDetail = priceDto.getPublishPriceExpressDetail().get(k);
									if(k==0){//首重
										publishPrice.setFirstWeight(publishPriceExpressDetail.getFirstWeight());
										publishPrice.setWeightLowLimit(publishPriceExpressDetail.getWeightOffline());//首重下线
										publishPrice.setWeightHighLimit(publishPriceExpressDetail.getWeightOnline());//首重上限
									}else if(k==1){
										
										publishPrice.setWeightOffline1(publishPriceExpressDetail.getWeightOffline());
										publishPrice.setWeightOnline1(publishPriceExpressDetail.getWeightOnline());
										publishPrice.setFeeRate1(publishPriceExpressDetail.getFeeRate());
									}else if(k==2){
										publishPrice.setWeightOffline2(publishPriceExpressDetail.getWeightOffline());
										publishPrice.setWeightOnline2(publishPriceExpressDetail.getWeightOnline());
										publishPrice.setFeeRate2(publishPriceExpressDetail.getFeeRate());
									}
								}
							}
							effectivePlanDtos.add(effectiveDto);
							voList.add(publishPrice);
						} 
				    }
				}
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {//没有对应价格的时效
							continue;
						} 
						publishPrice = new PublishPriceExpressEntity();
						//deliveryTime 派送时间
						publishPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
						//pickTime取货时间 自提时间
						publishPrice.setPickupTime(getStandardTime(effectiveDto.getArriveTime()));
						//arriveTime 营运时效 承诺到达营业部时间
						publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
						//productItemName产品name
						publishPrice.setProductItemName(effectiveDto.getProductName());
						publishPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());//到达区域code
						publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());//到达区域id
						publishPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());//始发区域code
						publishPrice.setDeptRegionId(effectiveDto.getDeptRegionId());	//始发区域id
						
						publishPrice.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
						voList.add(publishPrice);
					}
				}
			} else {
				//时效区域全部未匹配的情况
				for(int i=0; i<effectInfoList.size(); i++){
					EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
					publishPrice = new PublishPriceExpressEntity();
					//deliveryTime 派送时间
					publishPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
					//pickTime取货时间 自提时间
					publishPrice.setPickupTime(getStandardTime(effectiveDto.getArriveTime()));
					//arriveTime 营运时效 承诺到达营业部时间
					publishPrice.setArriveTime(effectiveDto.getMinTime() + "-" + effectiveDto.getMaxTime());
					//productItemName产品name
					publishPrice.setProductItemName(effectiveDto.getProductName());
					publishPrice.setArrvRegionCode(effectiveDto.getArrvRegionCode());//到达区域code
					publishPrice.setArrvRegionId(effectiveDto.getArrvRegionId());//到达区域id
					publishPrice.setDeptRegionCode(effectiveDto.getDeptRegionCode());//始发区域code
					publishPrice.setDeptRegionId(effectiveDto.getDeptRegionId());	//始发区域id
					
					publishPrice.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
					voList.add(publishPrice);
				}
			}
		}
	}

    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-25 下午4:41:02
     * 
     * @param startCodeList
     * 
     * @param arrvCodeList
     * 
     * @param type
     * 
     * @param active
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
	@SuppressWarnings("rawtypes")
	private List<PublishPriceExpressDto> queryCurrentPublishPriceByRegionIds(Set<String> startRegionIdSet,
			Set<String> arrvRegionIdSet, String type, Date billDate) {
//		List<PublishPriceExpressDto> list = new ArrayList<PublishPriceExpressDto>();
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}

		if (CollectionUtils.isEmpty(startRegionIdSet) || CollectionUtils.isEmpty(arrvRegionIdSet)) {
			return null;
		}
		
		List<String> startRegionIdList = new ArrayList<String>();
		for (Iterator iterator = startRegionIdSet.iterator(); iterator.hasNext();) {
			String startRegionId = (String) iterator.next();
			startRegionIdList.add(startRegionId);
		}
		
		List<String> arrvRegionIdList = new ArrayList<String>();
		for (Iterator iterator = arrvRegionIdSet.iterator(); iterator.hasNext();) {
			String arrvRegionId = (String) iterator.next();
			arrvRegionIdList.add(arrvRegionId);
		}

		List<PublishPriceExpressDto> publishPriceDtos = publishPriceExpressDao.queryPublishPriceByRegionIds(startRegionIdList,
				arrvRegionIdList, type, FossConstants.ACTIVE, currentDateTime);
//		if (CollectionUtils.isNotEmpty(publishPriceDtos)) {
//			List<PublishPriceDto> priceDtos = this.boxHeaveAndLight(publishPriceDtos);
//			if (CollectionUtils.isNotEmpty(priceDtos)) {
//				list = this.boxProductPriceDto(priceDtos);
//			}
//		}
//
//		if (CollectionUtils.isNotEmpty(list)) {
//			list = this.boxCatalogName(list);
//		}
		//返回结果集合
		return publishPriceDtos ;
		
		
	}
	
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 从GIS获取网点Code
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-9 下午3:24:33
	 * 
	 * @param destinationProvinceCode
	 * 
	 * @param destinationCityCode
	 * 
	 * @param destinationCountyCode
	 * 
	 * @param destinationAddress
	 * 
	 * @param interfaceType
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "unchecked"})
	public List<String> getCompositeStationCodes(String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress, String interfaceType) {
		List<String> deptList = null;
		Map<String, Object> map = formatToMap(destinationProvinceCode, destinationCityCode, destinationCountyCode, destinationAddress);
		Map<String, Object> result = null;
		try {
			result = GisInterfaceUtil.callGisInterface(interfaceType, map);
			LOGGER.info("result.get(depts)" + result.get("depts").toString());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		if(result  != null && (Boolean)result.get("success")){
			List<Map<String, String>> gisDeptList = (List<Map<String, String>>)result.get("depts");
			if(CollectionUtils.isNotEmpty(gisDeptList)) {
				deptList = new ArrayList<String>();
				for (int i = 0; i < gisDeptList.size(); i++) {
					Map<String, String> deptMap = gisDeptList.get(i);
					deptList.add(deptMap.get("deptNo"));
				}
			}
		}
		return deptList;
	}
	
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 封装MAP
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-1-7 下午5:36:59
	 * 
	 * @param dispatchOrderEntity
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private static Map<String, Object> formatToMap(String destinationProvinceCode, String destinationCityCode,
			String destinationCountyCode, String destinationAddress){
		Map<String, Object> map = new HashMap<String, Object>();
		// 省份
		map.put("province", destinationProvinceCode);
		// 城市
		map.put("city", destinationCityCode);
		// 区县
		map.put("county", destinationCountyCode);
		// 其他详细地址
		map.put("otherAddress", destinationAddress);
		// 网点类型
		List<String> matchtypes = new ArrayList<String>();
		//自提
		matchtypes.add("pickup");
		//派送
		matchtypes.add("deliver");
		//收货
		matchtypes.add("leave");
		map.put("matchtypes", matchtypes); 
		// 运输类型
		List<String> transportways = new ArrayList<String>();
		//汽运专线
		transportways.add("motor_self");   
		//汽运偏线	
		transportways.add("motor_agency"); 
		 //空运
		transportways.add("air");         
		map.put("transportways", transportways);	 
		return map;
	}	
	
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 	
     * @Description: 校验方法
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-3-14 上午10:13:07
     * 
     * @param startCityCode
     * 
     * @param destinationCountryCode
     * 
     * @param destinationProvinceCode
     * 
     * @param destinationCityCode
     * 
     * @version V1.0
     */
    private void validateAreaInfo(String startCityCode, String destinationCountryCode,
			String destinationProvinceCode, String destinationCityCode) {
    	if(StringUtil.isEmpty(destinationCountryCode)) {
    		throw new PublishPriceException("请选择国家", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	} else if(StringUtil.isEmpty(destinationProvinceCode)) {
    		throw new PublishPriceException("请选择省份", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	} else if(StringUtil.isEmpty(destinationCityCode)) {
    		throw new PublishPriceException("请选择目的城市", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	} else  if(StringUtil.isEmpty(startCityCode)) {
    		throw new PublishPriceException("请选择始发城市", PublishPriceException.PUBLISH_PRICE_CHECK_PARAMETER_ERROR_CODE);
    	}
    }
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * 
	 * @Description: 将字符串化为标准时间
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-24 下午2:07:35
	 * 
	 * @param str
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private String getStandardTime(String str) {
		String time = str;
		if(StringUtil.isNotBlank(str) && str.length() == NumberConstants.NUMBER_4) {
			String timeHead = time.substring(0, 2);
			String timeTail = time.substring(2, NumberConstants.NUMBER_4);
			time = timeHead +":"+ timeTail;
		} 
		return time;
	}    
    /**
     * 
     * @Description: 
     * @author FOSSDP-Administrator
     * @date 2013-4-16 上午9:39:19
     * @param startDeptList
     * @param arrvDeptList
     * @return
     * @version V1.0
     */
    @SuppressWarnings({"rawtypes" })
    private Map<String, Map> buildUpDeptRegion(List<String> arrvDeptList) {
    	Map<String, Map> areaMap = new HashMap<String, Map>();
    	//查询新集合时效区域
    	Map<String, String> expressEffectiveRegionMap = new HashMap<String, String>();
    	for (int i = 0; i < arrvDeptList.size(); i++) {
			String deptCode = arrvDeptList.get(i);
			String regionId = regionExpressService.findRegionOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRESCRIPTION_REGION);
			expressEffectiveRegionMap.put(deptCode, regionId);
		}
    	//查询新集合价格区域
    	Map<String, String> expressPriceRegionMap = new HashMap<String, String>();
    	for (int i = 0; i < arrvDeptList.size(); i++) {
			String deptCode = arrvDeptList.get(i);
			String regionId = regionExpressService.findRegionOrgByDeptNo(deptCode, new Date(), null, PricingConstants.PRICING_REGION);
			expressPriceRegionMap.put(deptCode, regionId);
		}
    	/**
		 * 时效区域
		 */
		//根据时效目的区域组装网点信息
		Map<String, List<String>> arrvRegionMap = new HashMap<String, List<String>>();
		Map<String, String> arrvRegionNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(arrvDeptList)) {
			for (int i = 0; i < arrvDeptList.size(); i++) {
				String deptNo = arrvDeptList.get(i);
				String arrvRegionId = expressEffectiveRegionMap.get(deptNo);
				if(StringUtil.isNotBlank(arrvRegionId)) {
					List<String> regionDeptList = null;
					if(arrvRegionMap.containsKey(arrvRegionId)) {
						regionDeptList = arrvRegionMap.get(arrvRegionId);
					} else {
						regionDeptList = new ArrayList<String>();
					}
					regionDeptList.add(deptNo);
					arrvRegionMap.put(arrvRegionId, regionDeptList);
				}
			}
			if(arrvRegionMap.size() > 0) {
				for (Iterator<String> iterator = arrvRegionMap.keySet().iterator(); iterator.hasNext();) {
					String arrvRegionId = iterator.next();
					PriceRegionExpressEntity regionEntity = (PriceRegionExpressEntity) regionExpressService.searchRegionByID(arrvRegionId,
							PricingConstants.PRESCRIPTION_REGION);
					if(regionEntity != null && regionEntity.getRegionName() != null) {
						arrvRegionNameMap.put(arrvRegionId, regionEntity.getRegionName());
					}
				}
			}
		}
		/**
		 * 价格区域
		 */
		//根据价格目的区域组装网点信息
		Map<String, List<String>> priceArrvRegionMap = new HashMap<String, List<String>>();
//		Map<String, String> priceArrvRegionNameMap = new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(arrvDeptList)) {
			for (int i = 0; i < arrvDeptList.size(); i++) {
				String deptNo = arrvDeptList.get(i);
				String arrvRegionId = expressPriceRegionMap.get(deptNo);
				if(StringUtil.isNotBlank(arrvRegionId)) {
					List<String> regionDeptList = null;
					if(priceArrvRegionMap.containsKey(arrvRegionId)) {
						regionDeptList = priceArrvRegionMap.get(arrvRegionId);
					} else {
						regionDeptList = new ArrayList<String>();
					}
					regionDeptList.add(deptNo);
					priceArrvRegionMap.put(arrvRegionId, regionDeptList);
				}
			}
//			if(priceArrvRegionMap.size() > 0) {
//				for (Iterator<String> iterator = priceArrvRegionMap.keySet().iterator(); iterator.hasNext();) {
//					String arrvRegionId = iterator.next();
//					PriceRegionExpressEntity regionEntity = (PriceRegionExpressEntity) regionExpressService.searchRegionByID(arrvRegionId,
//							PricingConstants.PRICING_REGION);
//					if(regionEntity != null && regionEntity.getRegionName() != null) {
//						priceArrvRegionNameMap.put(arrvRegionId, regionEntity.getRegionName());
//					}
//				}
//			}
		}

		//目的时效区域
		areaMap.put("expressEffectiveRegionMap", arrvRegionMap);
		//目的时效区域名称
		areaMap.put("expresseffectiveRegionNameMap", arrvRegionNameMap);
		//目的价格区域
		areaMap.put("expressPriceRegionMap", priceArrvRegionMap);
//		//目的价格区域名称
//		areaMap.put("expressPriceRegionNameMap", priceArrvRegionNameMap);
		//返回结果MAP
		return areaMap;
    }
    
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 封装网点的区域信息
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-25 下午2:17:29
     * 
     * @param startDeptList
     * 
     * @param arrvDeptList
     * 
     * @return
     * 
     * @version V1.0
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Map> buildUpDistrictRegion(String districtCode) {
    	if(StringUtils.isEmpty(districtCode)) {
    		return null;
    	}
    	DistrictRegionEntity districtRegionEntity = districtRegionCacheDeal.getDistrictRegionByCache(districtCode);
    	//	    快递时效区域ID
		Map<String, List<String>> expressEffectiveRegionMap = null;
		//	    快递时效区域Name
		Map<String, String> expresseffectiveRegionNameMap = null;	
		//	    快递价格区域ID
		Map<String, List<String>> expressPriceRegionMap = null;
		//	    快递价格区域Name
		Map<String, String> expressPriceRegionNameMap = null;
    	Map<String, Map> areaMap = new HashMap<String, Map>();
    	if(districtRegionEntity != null) {
    		//快递时效区域ID
    		String expressEffectiveRegionIds = districtRegionEntity.getExpressEffectiveRegionIds();
    		//快递价格区域ID
    		String expressPriceRegionIds = districtRegionEntity.getExpressPriceRegionIds();
    		//快递时效区域Name
    		String expressEffectiveRegionNames = districtRegionEntity.getExpressEffectiveRegionNames();
    		//快递价格区域Name
    		String expressPriceRegionNames = districtRegionEntity.getExpressPriceRegionNames();
    		if(StringUtils.isNotEmpty(expressEffectiveRegionIds)) {
    			expressEffectiveRegionMap = new HashMap<String, List<String>>();
    			expresseffectiveRegionNameMap = new HashMap<String, String>();
    			String[] regionIds = expressEffectiveRegionIds.split(",");
    			String[] regionNames = expressEffectiveRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					expressEffectiveRegionMap.put(regionIds[i], null);
            			expresseffectiveRegionNameMap.put(regionIds[i], regionNames[i]);
					}
    			}
    		}
    		
    		if(StringUtils.isNotEmpty(expressPriceRegionIds)) {
    			expressPriceRegionMap = new HashMap<String, List<String>>();
    			expressPriceRegionNameMap = new HashMap<String, String>();
    			String[] regionIds = expressPriceRegionIds.split(",");
    			String[] regionNames = expressPriceRegionNames.split(",");
    			if(regionIds != null && regionIds.length > 0) {
    				for (int i = 0; i < regionIds.length; i++) {
    					expressPriceRegionMap.put(regionIds[i], null);
    					expressPriceRegionNameMap.put(regionIds[i], regionNames[i]);
					} 
    			}
    		}
    		
			//存在缓存
//			areaMap.put("districtRegionEntityIsNotNull",null);
    	}
		//起始快递时效区域
    	if(expressEffectiveRegionMap != null && expressEffectiveRegionMap.size() > 0) {
    		areaMap.put("expressEffectiveRegionMap", expressEffectiveRegionMap);
    	}
    	//起始快递时效区域名称
    	if(expresseffectiveRegionNameMap != null && expresseffectiveRegionNameMap.size() > 0) {
    		areaMap.put("expresseffectiveRegionNameMap", expresseffectiveRegionNameMap);
    	}
		//起始快递价格区域
		if(expressPriceRegionMap != null && expressPriceRegionMap.size() > 0) {
			areaMap.put("expressPriceRegionMap", expressPriceRegionMap);
    	}
		//起始快递价格区域名称
		if(expressPriceRegionNameMap != null && expressPriceRegionNameMap.size() > 0) {
			areaMap.put("expressPriceRegionNameMap", expressPriceRegionNameMap);
    	}
		//返回结果MAP
		return areaMap;
    }
    
    /**
     * 
     * @Description: 封装网点的区域信息
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-25 下午2:17:29
     * 
     * @param startDeptList
     * 
     * @param arrvDeptList
     * @param regionType
     * @return
     * 
     * @version V1.0
     * @param areaMap 
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Map> getRegionMapByDistrictCode(String code, String codeType, Map<String, Map> areaMap,String regionType ) {
    	//
    	PriceRegionExpressEntity regionExpress = new PriceRegionExpressEntity();
    	if(DictionaryValueConstants.DISTRICT_COUNTY.equals(codeType)){
    		regionExpress.setCountyCode(code);
    	}else{
    		regionExpress.setCityCode(code);
    	}
    	regionExpress.setActive(FossConstants.YES);
    	regionExpress.setBillDate(new Date());
    	
    	//时效区域
    	if(PricingConstants.PRESCRIPTION_REGION.equals(regionType)){
        	//	    快递时效区域ID
    		Map<String, List<String>> expressEffectiveRegionMap = new HashMap<String, List<String>>();
    		//	    快递时效区域Name
    		Map<String, String> expresseffectiveRegionNameMap = new HashMap<String, String>();
    		regionExpress.setRegionNature(PricingConstants.PRESCRIPTION_REGION);
    		//获取时效区域ID,NAME
    		PriceRegionExpressEntity districtRegionEntity = null;
    		PriceRegionExpressEntity districtRegionEntityTemp = null;
    		List<PriceRegionExpressEntity> regionExpressList= regionExpressService.findRegionByRegion(regionExpress);
    		if(null!=regionExpressList&&regionExpressList.size()>0){
    			for(PriceRegionExpressEntity region:regionExpressList){
    				//县级
    				if(StringUtils.isNotEmpty(region.getCountyCode())&&region.getCountyCode().equals(regionExpress.getCountyCode())){
    					districtRegionEntity= region;
    					break;
    					//市级
    				}else if(StringUtils.isEmpty(region.getCountyCode())&&StringUtils.isNotEmpty(region.getCityCode())){
    					districtRegionEntityTemp= region;
    				}
    			}
    		}
    		if(districtRegionEntity==null){
    			districtRegionEntity=districtRegionEntityTemp;
    		}
    		
        	if(districtRegionEntity != null&&StringUtils.isNotEmpty(districtRegionEntity.getId())) {
    			expressEffectiveRegionMap.put(districtRegionEntity.getId(), null);
    			expresseffectiveRegionNameMap.put(districtRegionEntity.getId(), districtRegionEntity.getRegionName());
        	}  
    		//起始快递时效区域
        	if(expressEffectiveRegionMap != null && expressEffectiveRegionMap.size() > 0) {
        		areaMap.put("expressEffectiveRegionMap", expressEffectiveRegionMap);
        	}
        	//起始快递时效区域名称
        	if(expresseffectiveRegionNameMap != null && expresseffectiveRegionNameMap.size() > 0) {
        		areaMap.put("expresseffectiveRegionNameMap", expresseffectiveRegionNameMap);
        	}
    	}
    	//价格区域
    	else if (PricingConstants.PRICING_REGION.equals(regionType)){
        	//获取价格区域ID,NAME
        	regionExpress.setRegionNature(PricingConstants.PRICING_REGION);
        	PriceRegionExpressEntity districtPriceRegionEntity = null;
        	PriceRegionExpressEntity districtPriceRegionEntityTemp = null;
        	List<PriceRegionExpressEntity> regionPriceExpressList= regionExpressService.findRegionByRegion(regionExpress);
        	if(null!=regionPriceExpressList&&regionPriceExpressList.size()>0){
        		for(PriceRegionExpressEntity region:regionPriceExpressList){
        			//县级
        			if(StringUtils.isNotEmpty(region.getCountyCode())&&region.getCountyCode().equals(regionExpress.getCountyCode())){
        				districtPriceRegionEntity= region;
        				break;
        			//市级
        			}else if(StringUtils.isEmpty(region.getCountyCode())&&StringUtils.isNotEmpty(region.getCityCode())){
        				districtPriceRegionEntityTemp= region;
        			}
        		}
        	}
        	if(districtPriceRegionEntity==null){
        		districtPriceRegionEntity=districtPriceRegionEntityTemp;
        	}
        	
    		//	    快递价格区域ID
    		Map<String, List<String>> expressPriceRegionMap = new HashMap<String,List<String>>();
    		//	    快递价格区域Name
    		Map<String, String> expressPriceRegionNameMap = new HashMap<String, String>();
        	if(districtPriceRegionEntity != null&&StringUtils.isNotEmpty(districtPriceRegionEntity.getId())) {	
        		expressPriceRegionMap.put(districtPriceRegionEntity.getId(), null);
        		expressPriceRegionNameMap.put(districtPriceRegionEntity.getId(), districtPriceRegionEntity.getRegionName());
        	}

    		//起始快递价格区域
    		if(expressPriceRegionMap != null && expressPriceRegionMap.size() > 0) {
    			areaMap.put("expressPriceRegionMap", expressPriceRegionMap);
        	}
    		//起始快递价格区域名称
    		if(expressPriceRegionNameMap != null && expressPriceRegionNameMap.size() > 0) {
    			areaMap.put("expressPriceRegionNameMap", expressPriceRegionNameMap);
        	}
    	}
		//返回结果MAP
		return areaMap;
    }
    
	/**
	 * 获取 产品接口.
	 *
	 * @return the 产品接口
	 */
	public IProductService getProductService() {
		return productService;
	}

	/**
	 * 设置 产品接口.
	 *
	 * @param productService the new 产品接口
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * 获取 计价方案明细接口.
	 *
	 * @return the 计价方案明细接口
	 */
	public IPriceCriteriaDetailService getPriceCriteriaDetailService() {
		return priceCriteriaDetailService;
	}

	/**
	 * 设置 网点综合Dao.
	 *
	 * @param pricingOrgDao the new 网点综合Dao
	 */
	public void setPricingOrgDao(IPricingOrgDao pricingOrgDao) {
		this.pricingOrgDao = pricingOrgDao;
	}
	
	public void setDistrictRegionCacheDeal(DistrictRegionCacheDeal districtRegionCacheDeal) {
		this.districtRegionCacheDeal = districtRegionCacheDeal;
	}
	
	public void setDistrictRegionService(IDistrictRegionService districtRegionService) {
		this.districtRegionService = districtRegionService;
	}
	
	/**
	 * 设置 行政区域接口.
	 *
	 * @param administrativeRegionsService the new 行政区域接口
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}
	/**
	 * 设置 调用gis链接.
	 *
	 * @param gisUrl the new 调用gis链接
	 */
	public void setGisUrl(String gisUrl) {
		this.gisUrl = gisUrl;
	}
	
    /**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 方法（二）
     * 
     * @Description: 根据出发、到达城市Code查询公布价,PDA专用
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-25 下午2:06:45
     * 
     * @param startCityCode
     * 
     * @param destinationCityCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<PublishPriceExpressEntity> queryPublishPriceDetailByCity(
			String startCityCode, String destinationCityCode, Date billDate) {

    	//检验数据
    	if(StringUtils.isEmpty(startCityCode)) {
    		return null;
    	}
    	if(StringUtils.isEmpty(destinationCityCode)) {
    		return null;
    	}
    	//根据始发行政区域查询和封装区域数据
    	Map<String, Map> startAreaMap = buildUpDistrictRegion(startCityCode);
		if(startAreaMap.size() < 1) {
			districtRegionService.addDistrictRegion(startCityCode);
			startAreaMap = buildUpDistrictRegion(startCityCode);
			if(startAreaMap.size() < 1) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
				if(administrativeRegionsEntity != null) {
					if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
						if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
							startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							if(startAreaMap.size() < 1) {
								districtRegionService.addDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
								startAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							}
						}
					}
				}
			}
		}
		if(startAreaMap.size() < 1) {
			return null;
		}
    	//根据目的行政区域查询和封装区域数据
    	Map<String, Map> arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
    	if(arrvAreaMap.size() < 1) {
			districtRegionService.addDistrictRegion(destinationCityCode);
			arrvAreaMap = buildUpDistrictRegion(destinationCityCode);
			if(arrvAreaMap.size() < 1) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(destinationCityCode);
				if(administrativeRegionsEntity != null) {
					if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
						if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
							arrvAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							if(arrvAreaMap.size() < 1) {
								districtRegionService.addDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
								arrvAreaMap = buildUpDistrictRegion(administrativeRegionsEntity.getParentDistrictCode());
							}
						}
					}
				}
			}
		}
		if(arrvAreaMap.size() < 1) {
			return null;
		}
		
    	// 起始时效区域
		Map<String, List<String>> effectiveStartRegionMap = startAreaMap.get("expressEffectiveRegionMap");
		// 目的时效区域
		Map<String, List<String>> effectiveArrvRegionMap = arrvAreaMap.get("expressEffectiveRegionMap");
		// 起始时效区域名称
		Map<String, String> effectiveStartRegionNameMap = startAreaMap.get("expresseffectiveRegionNameMap");
		// 目的时效区域名称
		Map<String, String> effectiveArrvRegionNameMap = arrvAreaMap.get("expresseffectiveRegionNameMap");
		// 起始价格区域
		Map<String, List<String>> priceStartRegionMap = startAreaMap.get("expressPriceRegionMap");
		// 目的价格区域
		Map<String, List<String>> priceArrvRegionMap = arrvAreaMap.get("expressPriceRegionMap");
		 //起始价格区域名称
//		Map<String, String> priceStartRegionNameMap = startAreaMap.get("priceRegionNameMap");
//		 //目的价格区域名称
//		Map<String, String> priceArrvRegionNameMap = arrvAreaMap.get("priceRegionNameMap");

		//定义始发价格区域合并SET,用于合并汽运价格区域和空运价格区域
		Set priceStartRegion = new HashSet();
		//定义目的价格区域合并SET,用于合并汽运价格区域和空运价格区域
		Set priceArrvRegion = new HashSet();
		// 合并始发汽运价格区域
		if(priceStartRegionMap != null && priceStartRegionMap.size() > 0) {
			priceStartRegion.addAll(priceStartRegionMap.keySet());
		}
		// 合并目的汽运价格区域
		if(priceArrvRegionMap != null && priceArrvRegionMap.size() > 0) {
			priceArrvRegion.addAll(priceArrvRegionMap.keySet());
		}
		// (1) 时效
		// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
		List<EffectiveExpressPlanDto> effectInfoList= null;
		if(effectiveStartRegionMap != null && effectiveArrvRegionMap != null) {
			effectInfoList = effectiveExpressPlanDetailService.queryEffectiveExpressPlanDetailListByCondition(
					effectiveStartRegionMap.keySet(), effectiveArrvRegionMap.keySet(), null, billDate);
		}
		// (2) 价格
		// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
		List<PublishPriceExpressDto> priceInfoList = null;
		if(CollectionUtils.isNotEmpty(priceStartRegion) && CollectionUtils.isNotEmpty(priceArrvRegion)) {
			priceInfoList = this.queryCurrentPublishPriceByRegionIdsForPickUp(
					priceStartRegion, priceArrvRegion, PricingConstants.VALUATION_TYPE_PRICING, billDate);
		}
		// 根据destinationCode获取组织名字
		List<PublishPriceExpressEntity> voList = null;
		if(CollectionUtils.isNotEmpty(effectInfoList) && CollectionUtils.isNotEmpty(priceInfoList)) {
			voList = new ArrayList<PublishPriceExpressEntity>();
			encapulatePublicPrice(effectInfoList, priceInfoList, voList);
		}
		if(CollectionUtils.isNotEmpty(voList)) {
			for (int i = 0; i < voList.size(); i++) {
				PublishPriceExpressEntity publicPriceDto = voList.get(i);
				publicPriceDto.setId(UUIDUtils.getUUID());
				if(StringUtils.isNotEmpty(publicPriceDto.getDeptRegionId())) {
					publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
					if(StringUtils.isEmpty(publicPriceDto.getDeptRegionName())) {
						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
					}
				}
				if(StringUtils.isNotEmpty(publicPriceDto.getArrvRegionId())) {
					publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
					if(StringUtils.isEmpty(publicPriceDto.getArrvRegionName())) {
						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
					}
				}
//				publicPriceDto.setStartDeptCodes(effectiveStartRegionMap.get(publicPriceDto.getDeptRegionId()));
//				publicPriceDto.setDeptCodes(effectiveArrvRegionMap.get(publicPriceDto.getArrvRegionId()));
			//产品为空运的情况
			} 
		}
		return voList;
    }
    
    /**
     * 方法（二）
     * 
     * @Description: 仅供官网和呼叫中心调用，只查询行政区域 
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-2-25 下午2:06:45
     * 
     * @param startCityCode
     * 
     * @param destinationCityCode
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<PublishPriceExpressEntity> queryPublishPriceByCityCode(
			String startCityCode, String destinationCityCode, Date billDate) {

    	//检验数据
    	if(StringUtils.isEmpty(startCityCode)) {
    		return null;
    	}
    	if(StringUtils.isEmpty(destinationCityCode)) {
    		return null;
    	}
    	
    	//根据始发行政区域查询和封装区域数据
    	//时效区域始发
    	Map<String, Map> startAreaMap = new HashMap<String, Map> ();
    	startAreaMap = getRegionMapByDistrictCode(startCityCode,DictionaryValueConstants.DISTRICT_COUNTY,startAreaMap,PricingConstants.PRESCRIPTION_REGION);
		if(startAreaMap.size() < 1) {//时效
			startAreaMap = getRegionMapByDistrictCode(startCityCode,null,startAreaMap,PricingConstants.PRESCRIPTION_REGION);
			if(startAreaMap.size() < 1) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
				if(administrativeRegionsEntity != null) {
					if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
						if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
							startAreaMap = getRegionMapByDistrictCode(administrativeRegionsEntity.getParentDistrictCode(),null,startAreaMap,PricingConstants.PRESCRIPTION_REGION);
						}
					}
				}
			}
		}
		if(startAreaMap.size() < 1) {
			return null;
		}else{//价格始发
			startAreaMap = getRegionMapByDistrictCode(startCityCode,DictionaryValueConstants.DISTRICT_COUNTY,startAreaMap,PricingConstants.PRICING_REGION);
			if(null==startAreaMap.get("expressPriceRegionMap")) {
				startAreaMap = getRegionMapByDistrictCode(startCityCode,null,startAreaMap,PricingConstants.PRICING_REGION);
				if(null==startAreaMap.get("expressPriceRegionMap")) {
					AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(startCityCode);
					if(administrativeRegionsEntity != null) {
						if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
							if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
								startAreaMap = getRegionMapByDistrictCode(administrativeRegionsEntity.getParentDistrictCode(),null,startAreaMap,PricingConstants.PRICING_REGION);
							}
						}
					}
				}
			}			
		}
    	//根据目的行政区域查询和封装区域数据
		//时效到达
		Map<String, Map> arrvAreaMap = new HashMap<String, Map> ();
    	arrvAreaMap = getRegionMapByDistrictCode(destinationCityCode,DictionaryValueConstants.DISTRICT_COUNTY,arrvAreaMap,PricingConstants.PRESCRIPTION_REGION);
		if(arrvAreaMap.size() < 1) {
			arrvAreaMap = getRegionMapByDistrictCode(destinationCityCode,null,arrvAreaMap,PricingConstants.PRESCRIPTION_REGION);
			if(arrvAreaMap.size() < 1) {
			AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(destinationCityCode);
				if(administrativeRegionsEntity != null) {
					if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
						if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
							arrvAreaMap = getRegionMapByDistrictCode(administrativeRegionsEntity.getParentDistrictCode(),null,arrvAreaMap,PricingConstants.PRESCRIPTION_REGION);
						}
					}
				}
			}
		}
		if(arrvAreaMap.size() < 1) {
			return null;
		}else{//价格到达
	    	arrvAreaMap = getRegionMapByDistrictCode(destinationCityCode,DictionaryValueConstants.DISTRICT_COUNTY,arrvAreaMap,PricingConstants.PRICING_REGION);
	    	if(null==arrvAreaMap.get("expressPriceRegionMap")) {
				arrvAreaMap = getRegionMapByDistrictCode(destinationCityCode,null,arrvAreaMap,PricingConstants.PRICING_REGION);
				if(null==arrvAreaMap.get("expressPriceRegionMap")) {
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(destinationCityCode);
					if(administrativeRegionsEntity != null) {
						if(StringUtil.equals(administrativeRegionsEntity.getDegree(), DictionaryValueConstants.DISTRICT_COUNTY)) {
							if(StringUtils.isNotEmpty(administrativeRegionsEntity.getParentDistrictCode())) {
								arrvAreaMap = getRegionMapByDistrictCode(administrativeRegionsEntity.getParentDistrictCode(),null,arrvAreaMap,PricingConstants.PRICING_REGION);
							}
						}
					}
				}
			}
		}
    	// 起始时效区域
		Map<String, List<String>> effectiveStartRegionMap = startAreaMap.get("expressEffectiveRegionMap");
		// 目的时效区域
		Map<String, List<String>> effectiveArrvRegionMap = arrvAreaMap.get("expressEffectiveRegionMap");
		// 起始时效区域名称
		Map<String, String> effectiveStartRegionNameMap = startAreaMap.get("expresseffectiveRegionNameMap");
		// 目的时效区域名称
		Map<String, String> effectiveArrvRegionNameMap = arrvAreaMap.get("expresseffectiveRegionNameMap");
		// 起始价格区域
		Map<String, List<String>> priceStartRegionMap = startAreaMap.get("expressPriceRegionMap");
		// 目的价格区域
		Map<String, List<String>> priceArrvRegionMap = arrvAreaMap.get("expressPriceRegionMap");
		//定义始发价格区域合并SET,用于合并汽运价格区域和空运价格区域
		Set priceStartRegion = new HashSet();
		//定义目的价格区域合并SET,用于合并汽运价格区域和空运价格区域
		Set priceArrvRegion = new HashSet();
		// 合并始发汽运价格区域
		if(priceStartRegionMap != null && priceStartRegionMap.size() > 0) {
			priceStartRegion.addAll(priceStartRegionMap.keySet());
		}
		// 合并目的汽运价格区域
		if(priceArrvRegionMap != null && priceArrvRegionMap.size() > 0) {
			priceArrvRegion.addAll(priceArrvRegionMap.keySet());
		}
		// (1) 时效
		// 根据出发部门Code，到达目的地获取（获取顺序：时效逻辑区域，最新时效方案信息-需要结合产品信息）时效方案详细信息
		List<EffectiveExpressPlanDto> effectInfoList= null;
		if(effectiveStartRegionMap != null && effectiveArrvRegionMap != null) {
			effectInfoList = effectiveExpressPlanDetailService.queryEffectiveExpressPlanDetailListByCondition(
					effectiveStartRegionMap.keySet(), effectiveArrvRegionMap.keySet(), null, billDate);
		}
		// (2) 价格
		// 根据出发部门Code,到达目的地获取（获取顺序：价格逻辑区域，最新价格方案信息-需要结合产品、货物信息）价格方案详细信息
		List<PublishPriceExpressDto> priceInfoList = null;
		if(CollectionUtils.isNotEmpty(priceStartRegion) && CollectionUtils.isNotEmpty(priceArrvRegion)) {
			priceInfoList = this.queryCurrentPublishPriceByRegionIdsForPickUp(
					priceStartRegion, priceArrvRegion, PricingConstants.VALUATION_TYPE_PRICING, billDate);
		}
		// 根据destinationCode获取组织名字
		List<PublishPriceExpressEntity> voList = null;
		if(CollectionUtils.isNotEmpty(effectInfoList) && CollectionUtils.isNotEmpty(priceInfoList)) {
			voList = new ArrayList<PublishPriceExpressEntity>();
			encapulatePublicPrice(effectInfoList, priceInfoList, voList);
		}
		if(CollectionUtils.isNotEmpty(voList)) {
			for (int i = 0; i < voList.size(); i++) {
				PublishPriceExpressEntity publicPriceDto = voList.get(i);
				publicPriceDto.setId(UUIDUtils.getUUID());
				if(StringUtils.isNotEmpty(publicPriceDto.getDeptRegionId())) {
					publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
					if(StringUtils.isEmpty(publicPriceDto.getDeptRegionName())) {
						publicPriceDto.setDeptRegionName(effectiveStartRegionNameMap.get(publicPriceDto.getDeptRegionId()));
					}
				}
				if(StringUtils.isNotEmpty(publicPriceDto.getArrvRegionId())) {
					publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
					if(StringUtils.isEmpty(publicPriceDto.getArrvRegionName())) {
						publicPriceDto.setArrvRegionName(effectiveArrvRegionNameMap.get(publicPriceDto.getArrvRegionId()));
					}
				}
			//产品为空运的情况
			} 
		}
		return voList;
    }
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
	/**
	 * <p>
	 * Description:组装官网的时效价格<br />
	 * </p>
	 * 
	 * @author admin
	 * 
	 * @version 0.1 2012-11-5
	 * 
	 * @param effectInfoList
	 * 
	 * @param priceInfoList
	 * 
	 * @param voList
	 * 
	 * void
	 */
	private void encapulatePublicPrice(List<EffectiveExpressPlanDto> effectInfoList,
			List<PublishPriceExpressDto> priceInfoList, List<PublishPriceExpressEntity> voList) {
		PublishPriceExpressEntity publicPrice = null;
		if (CollectionUtils.isNotEmpty(effectInfoList)) {
			List<EffectiveExpressPlanDto> effectivePlanDtos = new ArrayList<EffectiveExpressPlanDto>();
			if(CollectionUtils.isNotEmpty(priceInfoList)) {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
				    for(int j = 0; j<priceInfoList.size(); j++){			    	
						//时效产品与价格信息产品相同时将价格信息中的条目作为产品信息显示， 同时添加重货，轻货价格;空运不根据时效，而是取决于价格
					    //价格明细是组装的一个新的Dto对象包含计费规则、计价方式和计价方式明细几个表的综合数据
				    	PublishPriceExpressDto priceDto = (PublishPriceExpressDto)priceInfoList.get(j);	
				    	//比较时效的父产品code和价格产品code是否相等
				    	ProductEntity product = productService.getProductByCache(effectiveDto.getProductCode(), null);
				    	String effParentCode = product.getParentCode();
						if(effParentCode.equals(priceDto.getProductCode())){	
							publicPrice= new PublishPriceExpressEntity();
							publicPrice.setLongOrShort(effectiveDto.getLongOrShort());
							publicPrice.setAddDay(effectiveDto.getAddDay());
							
							//调整为时效产品三级名称编码
							publicPrice.setProductCode(effectiveDto.getProductCode());
							publicPrice.setProductName(effectiveDto.getProductName());
//publicPrice.setStartDept(startName);
							publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());
//publicPrice.setArrvRegionName(arrvName);
							publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
							publicPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
							publicPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
							publicPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
							
							publicPrice.setMaxTime(effectiveDto.getMaxTime());
							publicPrice.setMaxTimeUnit(effectiveDto.getMaxTimeUnit());
							publicPrice.setMinTime(effectiveDto.getMinTime());
							publicPrice.setMinTimeUnit(effectiveDto.getMinTimeUnit());
							//deliveryTime 派送时间
							publicPrice.setDeliveryTime(effectiveDto.getDeliveryTime());
							//publicPrice.setDeliveryTime(getStandardTime(effectiveDto.getDeliveryTime()));
							/**
							 * 设置取货时间
							 */
//							if(effectiveDto.getArriveTime()!=null && !"".equals(effectiveDto.getArriveTime())){
//								if(effectiveDto.getArriveTime().length()>2){
//									String hours=effectiveDto.getArriveTime().substring(0,2);
//									String minutes=effectiveDto.getArriveTime().substring(2,effectiveDto.getArriveTime().length());
//									String pickupTime=hours+":"+minutes;
//									publicPrice.setPickupTime(pickupTime);
//								}
//								
//							}
							publicPrice.setPickupTime(effectiveDto.getArriveTime());

							publicPrice.setArriveTime(effectiveDto.getArriveTime());	
							publicPrice.setProductItemCode(effectiveDto.getProductCode());
							publicPrice.setProductItemName(effectiveDto.getProductName()+"("+priceDto.getGoodsTypeName()+")");
							
							/**
							 * 添加价格信息
							 */
							PublishPriceExpressDetailDto publishPriceExpressDetail;
							for(int k=0; k<priceDto.getPublishPriceExpressDetail().size();k++){
								publishPriceExpressDetail = priceDto.getPublishPriceExpressDetail().get(k);
								if(k==0){//首重
									publicPrice.setFirstWeight(publishPriceExpressDetail.getFirstWeight());
									publicPrice.setWeightLowLimit(publishPriceExpressDetail.getWeightOffline());//首重下线
									publicPrice.setWeightHighLimit(publishPriceExpressDetail.getWeightOnline());//首重上限
									
								}else if(k==1){//续重1
									
									publicPrice.setWeightOffline1(publishPriceExpressDetail.getWeightOffline());
									publicPrice.setWeightOnline1(publishPriceExpressDetail.getWeightOnline());
									publicPrice.setFeeRate1(publishPriceExpressDetail.getFeeRate());
								}else if(k==2){//续重2
									publicPrice.setWeightOffline2(publishPriceExpressDetail.getWeightOffline());
									publicPrice.setWeightOnline2(publishPriceExpressDetail.getWeightOnline());
									publicPrice.setFeeRate2(publishPriceExpressDetail.getFeeRate());
								}
							}
							effectivePlanDtos.add(effectiveDto);
							voList.add(publicPrice);
						} 
				    }
				}
				
				
				//时效产品信息作为产品名显示，价格不显示空白展出
				//时效区域部分未匹配的情况
				if(effectivePlanDtos.size() != effectInfoList.size()) {
					for(int i=0; i<effectInfoList.size(); i++){
						EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
						if(effectivePlanDtos.contains(effectiveDto)) {
							continue;
						} 
						publicPrice = new PublishPriceExpressEntity();
						publicPrice.setCreateDate(new Date(System.currentTimeMillis()));
						publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());
						publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
						publicPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
						publicPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
						publicPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
						publicPrice.setPickupTime(effectiveDto.getArriveTime());
						publicPrice.setArriveTime(effectiveDto.getArriveTime());
					    publicPrice.setProductItemName(effectiveDto.getProductName());
						publicPrice.setProductItemCode(effectiveDto.getProductCode());
						voList.add(publicPrice);
					}
				}
				//时效区域全部未匹配的情况
			} else {
				for(int i=0; i<effectInfoList.size(); i++){
					EffectiveExpressPlanDto effectiveDto = (EffectiveExpressPlanDto)effectInfoList.get(i);
					publicPrice = new PublishPriceExpressEntity();
					publicPrice.setCreateDate(new Date(System.currentTimeMillis()));
					publicPrice.setDeptRegionId(effectiveDto.getDeptRegionId());
					publicPrice.setArrvRegionId(effectiveDto.getArrvRegionId());
					publicPrice.setEffectiveUnit(effectiveDto.getMaxTimeUnit());
					publicPrice.setMinEffectiveValue(String.valueOf(effectiveDto.getMinTime()));
					publicPrice.setMaxEffectiveValue(String.valueOf(effectiveDto.getMaxTime()));
					publicPrice.setPickupTime(effectiveDto.getArriveTime());
					publicPrice.setArriveTime(effectiveDto.getArriveTime());
					publicPrice.setProductItemName(effectiveDto.getProductName());
					publicPrice.setProductItemCode(effectiveDto.getProductCode());
					voList.add(publicPrice);
				}
			}
		}
	}    
	
	
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
     * 
     * @Description: 
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-25 下午4:41:02
     * 
     * @param startCodeList
     * 
     * @param arrvCodeList
     * 
     * @param type
     * 
     * @param active
     * 
     * @param billDate
     * 
     * @return
     * 
     * @version V1.0
     */
	@SuppressWarnings("rawtypes")
	private List<PublishPriceExpressDto> queryCurrentPublishPriceByRegionIdsForPickUp(Set<String> startRegionIdSet,
			Set<String> arrvRegionIdSet, String type, Date billDate) {
		Date currentDateTime = null;
		if (billDate == null) {
			currentDateTime = new Date();
		} else {
			currentDateTime = billDate;
		}
		if (CollectionUtils.isEmpty(startRegionIdSet) || CollectionUtils.isEmpty(arrvRegionIdSet)) {
			return null;
		}
		List<String> startRegionIdList = new ArrayList<String>();
		for (Iterator iterator = startRegionIdSet.iterator(); iterator.hasNext();) {
			String startRegionId = (String) iterator.next();
			startRegionIdList.add(startRegionId);
		}
		List<String> arrvRegionIdList = new ArrayList<String>();
		for (Iterator iterator = arrvRegionIdSet.iterator(); iterator.hasNext();) {
			String arrvRegionId = (String) iterator.next();
			arrvRegionIdList.add(arrvRegionId);
		}
		List<PublishPriceExpressDto> publishPriceDtos = publishPriceExpressDao.queryPublishPriceByRegionIds(startRegionIdList,
				arrvRegionIdList, type, FossConstants.ACTIVE, currentDateTime);
		return publishPriceDtos;
	}


    /**
	 * 
	 * @Description: 获取网点信息
	 * @author FOSSDP-sz
	 * @date 2013-4-22 上午11:45:02
	 * @param codes
	 * @param regionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
    @Override
    public List<SaleDepartmentInfoDto> getOuterAndDepartment(List<String> codes, String regionId, String regionNature) {
    	if(CollectionUtils.isNotEmpty(codes)) {
    		return this.getOuterAndDepartment(codes);
    	} else {
    		if(StringUtils.isNotEmpty(regionId) && StringUtils.isNotEmpty(regionNature)) {
				List<String> list = regionExpressService.searchRegionOrgCodeByRegionId(regionId, regionNature);
				if(CollectionUtils.isNotEmpty(list)) {
					return this.getOuterAndDepartment(list);
				} else {
					list = getAdministrativeDept(regionId, regionNature);
					if(CollectionUtils.isNotEmpty(list)) {
						return this.getOuterAndDepartment(list); 
					}
				}
    		}
    		return null;
    	}
    }
    
	/**
     *SR1	查询支持条件组合查询,
	*
	*目的地通过选择下拉列表中的类型来支持不同的查询。
	*
	*如果选择城市则按填写的相应城市所对应的区域进行查询；
	*
	*如果选择地址，
	*
	*则按填写地址通过GIS进行查找相对应的网点，
	*
	*并以这些网点对应的区域进行查询
	*
	*SR2	公布价后台查询规则描述： 
	*
	*请参考1.5.4逻辑图，
	*
	*根据部门信息到区域中去查找且按照异常优先级
	*
	*（先找区域类型为国标区域与部门所维护的区域，
	*
	*再找部门所在城市信息，
	*
	*最后找部门所在省份信息直到找到对应的区域为止）
	*
	*来确定最终区域信息，
	*
	*定位到了区域信息就可以分别找区域类型了，
	*
	*它包括了时效区域与价格区域，
	*
	*接着可按照价格区域找价格的最新发布方案版本而时效区域找最新的时效发布方案版本再进行组装就成了最终的公布价了。
	*
	*如果时效区域信息在时效版本里面没有找到相关的信息，
	*
	*那么基于该区域的公布价就不用显示了，
	*
	*如果找到时效相关版本信息而没有找到价格版本信息那么公布价只会显示时效相关的信息而已。
	*
	*SR3	
	*
	*查询列表中重货价格以公斤计算。
	*
	*SR4
	*
	*	查询列表中轻货价格以体积计算。
	*
	*SR5	
	*
	*查询列表中营运时效对客户承诺的时间。
	*
	*
	*SR6	
	*
	*公布价信息中的最低一票信息是根据区域和产品条目信息关联
	*
	*引用 DP-FOSS-接送货系统用例-产品价格-城市与产品列表-录入区域
	*
	*与产品-V0.1.doc 中所维护的最低一票而来的。
     */
    /**
	 * 
	 * @Description: 获取网点信息
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-1-23 上午10:25:21
	 * 
	 * @param codes
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
    @Override
    public List<SaleDepartmentInfoDto> getOuterAndDepartment(List<String> codes) {
    	List<SaleDepartmentInfoDto> deptList = saleDepartmentDao.querySaleDepartmentInfoByCodes(codes);
    	List<OuterBranchEntity> outerList = vehicleAgencyDeptDao.queryOuterBranchByCodes(codes, null); 
    	if(CollectionUtils.isEmpty(deptList)) {
    		deptList = new ArrayList<SaleDepartmentInfoDto>();
    	}
    	if(CollectionUtils.isNotEmpty(outerList)) {
    		for (int i = 0; i < outerList.size(); i++) {
    			OuterBranchEntity outerBranchEntity = outerList.get(i);
    			SaleDepartmentInfoDto infoDto = new SaleDepartmentInfoDto();
    			infoDto.setCode(outerBranchEntity.getAgentDeptCode());
    			infoDto.setName(outerBranchEntity.getAgentDeptName());
    			infoDto.setSimpleName(outerBranchEntity.getSimplename());
    			infoDto.setDelivery(outerBranchEntity.getPickupToDoor());
    			infoDto.setPickupSelf(outerBranchEntity.getPickupSelf());
    			infoDto.setTelephone(outerBranchEntity.getMobilePhone());
    			infoDto.setOrgType(outerBranchEntity.getBranchtype());
    			infoDto.setAddress(outerBranchEntity.getAddress());
    			deptList.add(infoDto);
			}
    	}
    	return deptList;
    }    
    
    /**
     * 
     * @Description: 根据区域查找行政区域下的网点信息
     * @author FOSSDP-sz
     * @date 2013-4-28 上午11:06:56
     * @param regionId
     * @param regionNature
     * @return
     * @version V1.0
     */
    private List<String> getAdministrativeDept(String regionId, String regionNature) {
    	List<String> list = null;
    	String areaCode = null;
		PriceRegionExpressEntity priceRegionEntity = regionExpressService.searchRegionByID(regionId, regionNature);
		if(priceRegionEntity != null && StringUtils.equals(priceRegionEntity.getRegionType(), PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE)) {
			String countyCode = priceRegionEntity.getCountyCode();
			String cityCode = priceRegionEntity.getCityCode();
			String provinceCode = priceRegionEntity.getProCode();
			if(StringUtil.isNotEmpty(countyCode)) {
				areaCode = countyCode;
			} else if(StringUtil.isNotEmpty(cityCode)) {
				areaCode = cityCode;
			} else if(StringUtil.isNotEmpty(provinceCode)) {
				areaCode = provinceCode;
			} else {
				return null;
			}
		}
    	List<CommonOrgEntity> commonOrgEntities = pricingOrgDao.queryArrvOrgByDistrict(areaCode, FossConstants.ACTIVE);
		if(CollectionUtils.isNotEmpty(commonOrgEntities)) {
			list = new ArrayList<String>();
			for (CommonOrgEntity commonOrgEntity : commonOrgEntities) {
				list.add(commonOrgEntity.getCode());
			}
		}
    	return list;
    }
	public void setSaleDepartmentDao(ISaleDepartmentDao saleDepartmentDao) {
		this.saleDepartmentDao = saleDepartmentDao;
	}
	public void setVehicleAgencyDeptDao(IVehicleAgencyDeptDao vehicleAgencyDeptDao) {
		this.vehicleAgencyDeptDao = vehicleAgencyDeptDao;
	}
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}


	
}




































































/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 *  
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
