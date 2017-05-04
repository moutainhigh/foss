/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-pda-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pda/api/server/service/IPdaPriceService.java
 * 
 * FILE NAME        	: IPdaPriceService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pda.api.server.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.CityMarketPlanDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PreferentialDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PdaResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PublicPriceDto;


/**
 * 
 * @author zhangdongping
 * @date 2012-12-25 下午2:03:23
 * @since
 * @version
 */
public interface IPdaPriceService extends IService {
    /**
     * <p>根据客户编码、时间查询客户当前时间内的客户优惠信息</p> 
     * @author zhangdongping
     * @date 2012-12-20 上午10:20:09
     * @param customerCode 客户编码
     * @param date 查询时间
     * @return
     * @see
     */
    PreferentialDto queryPreferentialInfo(String customerCode,Date date);
    /**
     * 
     * <p>
     * Description:根据出发、到达城市Code查询公布价<br />
     * </p>
     * @author  zhangdongping
     * @version 0.1 2012-10-29
     * @param startCityCode 出发城市code
     * @param destinationCityCode 到达城市code
     * @param billDate 开单日期
     * @return
     * List<PublishPriceEntity>
     */
    List<PublicPriceDto> queryPublishPriceDetailByCity(
		String startCityCode, String destinationCityCode, Date billDate) ;
    /**
     * 
     * @Description: 根据条件查询PDA价格
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-14 上午10:28:57
     * @param billCalculateDto
     * @return
     * @version V1.0
     */
    List<PdaResultBillCalculateDto> queryBillCalculate(PdaQueryBillCalculateDto billCalculateDto) ;
    
	
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
    
    public List<PdaResultBillCalculateDto> queryExpressBillCalculate(
		PdaQueryBillCalculateDto billCalculateDto);
    /**
     * 通过部门编码\业务时间,查询大礼包名称(List<entity>) 
     * @return
     */
    	public List<CityMarketPlanDto> searchCityMarketPlanEntityList(String deptcode,Date billDate);
    /**
     * 通过大礼包编码、业务时间、查询大礼包信息entity
     * @param cityMarketPlanCode
     * @param billDate
     * @return
     */
    	public CityMarketPlanDto getCityMarketPlanEntityCode(String code,String deptcode,Date billDate);
    /**
     * 查询数量(笔记本，电脑等活动)
     * @param deptcode
     * @param billDate
     * @return
     */
    	public Long countMarketPlanEntity(String deptcode,Date billDate);
    	
	/**
	 * PDA不传产品类型查询物流费用接口
	 * 
	 * @author foss-206860	
	 * @date 2015-5-6 下午2:30:32
	 */
	public HashMap<String, List<PdaResultBillCalculateDto>> queryPdaExpressBillPriceNoProduct(
		PdaQueryBillCalculateDto billCalculateDto);
}