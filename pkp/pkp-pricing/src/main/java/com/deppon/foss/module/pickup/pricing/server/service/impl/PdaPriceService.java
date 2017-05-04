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
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PdaPriceService.java
 * 
 * FILE NAME        	: PdaPriceService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PreferentialEntity;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPriceService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.CityMarketPlanDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PreferentialDto;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICityMarketPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPdaBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPublishPriceService;
import com.deppon.foss.module.pickup.pricing.api.server.util.PriceUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CityMarketPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;
import com.deppon.foss.util.CollectionUtils;


/**
 * 
 * @Description: PDA 价格相关
 * PdaPriceService.java Create on 2013-3-18 上午10:53:13
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PdaPriceService implements IPdaPriceService{
	private static final Logger log = Logger.getLogger(PdaPriceService.class);
    /**
     * 公布价SERVICE
     */
    IPublishPriceService publishPriceService;
    /**
     * 客户优惠信息Service
     */
    IPreferentialService  preferentialService;
    /**
     * PDA价格计算
     */
    IPdaBillCaculateService pdaBillCaculateService;
    /**
     * 客户合同信息Service
     */
    ICusBargainService cusBargainService;
    
    /**
     * 设置 公布价SERVICE.
     *
     * @param publishPriceService the new 公布价SERVICE
     */
    public void setPublishPriceService(IPublishPriceService publishPriceService) {
        this.publishPriceService = publishPriceService;
    }
    
    /**
     * 设置 客户优惠信息Service.
     *
     * @param preferentialService the new 客户优惠信息Service
     */
    public void setPreferentialService(IPreferentialService preferentialService) {
        this.preferentialService = preferentialService;
    }
    
    /**
     * 设置 pDA价格计算.
     *
     * @param pdaBillCaculateService the new pDA价格计算
     */
    public void setPdaBillCaculateService(
			IPdaBillCaculateService pdaBillCaculateService) {
		this.pdaBillCaculateService = pdaBillCaculateService;
	}

	/**
	 * 设置 客户合同信息Service.
	 *
	 * @param cusBargainService the new 客户合同信息Service
	 */
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}
	/**
	 * 大礼包service
	 */
	private ICityMarketPlanService cityMarketPlanService;
	@Override
	public List<CityMarketPlanDto> searchCityMarketPlanEntityList(
			String deptcode, Date billDate) {
		List<CityMarketPlanEntity> list = cityMarketPlanService.searchCityMarketPlanEntityList(deptcode, billDate);
		if(null==list || list.size()==0){
			return null;
		}
		List<CityMarketPlanDto> rstList = new ArrayList<CityMarketPlanDto>(); 
		for(CityMarketPlanEntity entity : list){
			CityMarketPlanDto  dto = new CityMarketPlanDto();
			dto = changeToCityMarketPlanDto(entity);
			rstList.add(dto);
		}
		return rstList;
	}

	@Override
	public CityMarketPlanDto getCityMarketPlanEntityCode(String code,
			String deptcode, Date billDate) {
		CityMarketPlanEntity  entity = cityMarketPlanService.getCityMarketPlanEntityCode(code, deptcode, billDate);
		CityMarketPlanDto dto = changeToCityMarketPlanDto(entity);
		return dto;
	}

	private CityMarketPlanDto changeToCityMarketPlanDto(
			CityMarketPlanEntity entity) {
		CityMarketPlanDto dto  =  null;
		if(null!=entity){
			dto = new CityMarketPlanDto();
			dto.setActive(entity.getActive());
			dto.setBeginTime(entity.getBeginTime());
			dto.setBfRate(entity.getBfRate());
			dto.setCityCode(entity.getCityCode());
			dto.setCode(entity.getCode());
			dto.setCountyCode(entity.getCountyCode());
			dto.setCreateOrgCode(entity.getCreateOrgCode());
			dto.setCreateTime(dto.getCreateTime());
			dto.setCreateUserCode(dto.getCreateUserCode());
			dto.setDescription(entity.getDescription());
			dto.setEndTime(entity.getEndTime());
			dto.setId(entity.getId());
			dto.setMaxFee(entity.getMaxFee());
			dto.setMinFee(entity.getMinFee());
			dto.setModifyOrgCode(entity.getModifyOrgCode());
			dto.setModifyUserCode(entity.getModifyUserCode());
			dto.setModifyTime(entity.getModifyTime());
			dto.setName(entity.getName());
			dto.setNationCode(entity.getNationCode());
			dto.setProvCode(entity.getProvCode());
			dto.setR1Rate(entity.getR1Rate());
			dto.setR3Rate(entity.getR3Rate());
			dto.setRaRate(entity.getRaRate());
			dto.setShippingdiscount(entity.getShippingdiscount());
			dto.setVersionNo(entity.getVersionNo());
		}
		return dto;
	}

	@Override
	public Long countMarketPlanEntity(String deptcode, Date billDate) {
		// TODO Auto-generated method stub
		return cityMarketPlanService.countMarketPlanEntity(deptcode, billDate);
	}

	public void setCityMarketPlanService(
			ICityMarketPlanService cityMarketPlanService) {
		this.cityMarketPlanService = cityMarketPlanService;
	}

	/**
     * <p>根据客户编码、时间查询客户当前时间内的客户优惠信息</p> 
     * @author zhangdongping
     * @date 2012-12-20 上午10:20:09
     * @param customerCode 客户编码
     * @param date 查询时间
     * @return
     * @see
     */
    @Override
    public PreferentialDto queryPreferentialInfo(String customerCode, Date billDate) {
    	Date currentDate = billDate;
    	if(currentDate == null) {
    		currentDate = new Date();
    	}
		PreferentialEntity source = preferentialService.queryPreferentialInfo(customerCode, currentDate,null);
		if(source==null) {
		    return null;
		}else {
		    PreferentialDto target=new PreferentialDto();
		    CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainInfos(customerCode, billDate);
		    try {
				PropertyUtils.copyProperties(target, source);
			} catch (IllegalAccessException e) {
				log.info(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				log.info(e.getMessage(), e);
			} catch (NoSuchMethodException e) {
				log.info(e.getMessage(), e);
			}
		    if(cusBargainEntity != null) {
		    	target.setChargeType(cusBargainEntity.getChargeType());
		    	target.setArrearsAmount(cusBargainEntity.getArrearsAmount());
		    }
		    return target;
		}
    }
    /**
     * 
     * <p>
     * Description:根据出发、到达城市Code查询公布价<br />
     * </p>
     * 
     * @author  zhangdongping
     * 
     * @version 0.1 2012-10-29
     * 
     * @param startCityCode 出发城市code
     * 
     * @param destinationCityCode 到达城市code
     * 
     * @param billDate 开单日期
     * 
     * @return
     * 
     * List<PublishPriceEntity>
     */
    @Override
    public List<PublicPriceDto> queryPublishPriceDetailByCity(
	    String startCityCode, String destinationCityCode, Date billDate) {
    	Date currentDate = billDate;
    	if(currentDate == null) {
    		currentDate = new Date();
    	}
    	List<PublicPriceDto> publicPriceDtos = publishPriceService.queryPublishPriceDetailByCity(startCityCode, destinationCityCode, currentDate);
    	List<PublicPriceDto> list = new ArrayList<PublicPriceDto>();
    	if(CollectionUtils.isNotEmpty(publicPriceDtos)) {
    		for (int i = 0; i < publicPriceDtos.size(); i++) {
    			PublicPriceDto publicPriceDto = publicPriceDtos.get(i);
    			PublicPriceDto priceDto = new PublicPriceDto();
    			try {
					PropertyUtils.copyProperties(priceDto, publicPriceDto);
				} catch (IllegalAccessException e) {
					log.info(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					log.info(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					log.info(e.getMessage(), e);
				}
    			list.add(priceDto);
			}
    	}
    	return  list;
    }
    /**
     * 
     * @Description: 根据条件查询PDA价格
     * 
     * @Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-14 上午10:28:57
     * 
     * @param billCalculateDto
     * 
     * @return
     * 
     * @version V1.0
     */
	@Override
	public List<PdaResultBillCalculateDto> queryBillCalculate(
			PdaQueryBillCalculateDto billCalculateDto) {
		//数据检验
		PriceUtil.checkPdaQueryBillCalcuateDto(billCalculateDto);
		return pdaBillCaculateService.queryPdaBillPrice(billCalculateDto);
	}
	
	    /**
	     * 
	     * @Description: 根据条件查询快遞PDA价格
	     * 
	     * @Company:IBM
	     * 
	     * @author FOSSDP-sz
	     * 
	     * @date 2013-1-14 上午10:28:57
	     * 
	     * @param billCalculateDto
	     * 
	     * @return
	     * 
	     * @version V1.0
	     */
		@Override
	 public List<PdaResultBillCalculateDto> queryExpressBillCalculate(
				PdaQueryBillCalculateDto billCalculateDto) {
			//数据检验
			PriceUtil.checkExpressPdaQueryBillCalcuateDto(billCalculateDto);
			return pdaBillCaculateService.queryPdaExpressBillPrice(billCalculateDto);
		}
		
	/**
	 * PDA不传产品类型查询物流费用接口
	 * 
	 * @author foss-206860
	 * 
	 * RFOSS2015041729
	 * 
	 * */	
	@Override
	public HashMap<String, List<PdaResultBillCalculateDto>> queryPdaExpressBillPriceNoProduct(PdaQueryBillCalculateDto billCalculateDto){
		//数据检验
		PriceUtil.checkExpressPdaQueryBillCalcuateDtoNoProduct(billCalculateDto);
		return pdaBillCaculateService.queryPdaExpressBillPriceNoProduct(billCalculateDto);
	}
}