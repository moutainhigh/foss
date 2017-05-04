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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IEffectivePlanDetailService.java
 * 
 * FILE NAME        	: IEffectivePlanDetailService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;

/**
 * 
 * TODO(时效方案详细信息)
 * @author 岳洪杰
 * @date 2012-10-12 上午10:47:06
 * @since
 * @version
 */
public interface IEffectivePlanDetailService extends IService {
    
    
    /**
     * 
     * <p>新增时效明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:38:31
     * @param effectivePlanDetail
     * @see
     */
     void insertEffectivePlanDetail(EffectivePlanDetailEntity effectivePlanDetail);
    
    /**
     * 
     * <p>批量删除时效明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:40:17
     * @param ids
     * @see
     */
     void deleteEffectivePlanDetail(List<String> ids);
    
    /**
     * 
     * <p>批量激活时效明细信息</p> 
     * @author DP-Foss-YueHongJie
     * @date 2012-12-25 下午2:40:48
     * @param ids
     * @see
     */
     void activeEffectivePlanDetail(List<String> ids);
    
    
    /**
     * 
     * <p>(修改时效明细信息)</p> 
     * @author DP-Foss-YueHongJie
     * @date 2013-1-10 上午9:35:16
     * @param effectivePlanDetail
     * @see
     */
     void updateEffectivePlanDetail(EffectivePlanDetailEntity effectivePlanDetail);
    
    
    /**
     * 
     * <p>
     * Description:查询产品时效
     *             根据originalCityCode,destinationCityCode关联T_SRV_EFFECTIVE_REGION、T_SRV_EFFECTIVE_PLAN、
     *             T_SRV_EFFECTIVE_PLAN_DETAIL查询获取EFFECTIVE_PLAN_DETAIL明细信息和产品名称信息列表
     * <br />
     * </p>
     * @author DP-Foss-YueHongJie
     * @version 0.1 2012-10-26
     * @param originalCityCode  出发城市code
     * @param destinationCityCode  到达城市code
     * @param productCode 产品code
     * @return
     * List<EffectivePlanDto>
     */
     List<EffectivePlanDto> queryEffectivePlanDetailListByCityCode(String originalCityCode, String destinationCityCode,
    		String productCode, Date billDate);

	/**
	 * 
	 * <p>
	 * Description: 查询产品时效 <br />
	 * </p>
	 * @author DP-Foss-YueHongJie
	 * @version 0.1 2012-10-25
	 * @param originalOrgCode 出发部门
	 * @param destinationOrgCode 到达部门
	 * @param productCode 产品code
	 * @parm  billDate 开单日期 可空 ，默认为当前时间
	 * @return
	 * List<EffectivePlanDto>
	 */
     List<EffectivePlanDto> queryEffectivePlanDetailListByOrgCode(
	    String originalOrgCode, String destinationOrgCode,
	    String productCode, Date billDate);
    
    
    /**
     * @Description: 根据条件查询时效方案明细
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2012-12-25 下午2:05:11
     * @param effectivePlanDetailEntity
     * @return
     * @version V1.0
     */
     List<EffectivePlanDetailEntity> queryEffectivePlanDetailByConditions(EffectivePlanDetailEntity effectivePlanDetailEntity);
    
    /**
     * 
     * 查询方案明细信息分页
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午7:50:06
     */
     List<EffectivePlanDetailEntity> searchEffectivePlanDetailEntityByCondition(
	    EffectivePlanDetailEntity effectivePlanDetailEntity, int start, int limit);
    
    /**
     * 
     * 查询方案没明细信息分页总记录数
     * @author DP-Foss-YueHongJie
     * @date 2012-11-10 下午7:50:06
     */
     Long searchEffectivePlanDetailEntityCount(EffectivePlanDetailEntity effectivePlanDetailEntity);
    
    /**
     * 
     * @Description: 根据条件查询时效
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-9 下午5:16:30
     * @param startCityCode
     * @param deptCodeList
     * @param productCode
     * @param billDate
     * @return
     * @version V1.0
     */
     List<EffectivePlanDto> queryEffectivePlanDetailListByCondition(
			String startCityCode, List<String> deptCodeList,
			String productCode, Date billDate);
    
    /**
     * 
     * @Description: 根据条件查询时效方法
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-25 上午11:37:12
     * @param startCodeList
     * @param arrvCodeList
     * @param productCode
     * @param billDate
     * @return
     * @version V1.0
     */
     List<EffectivePlanDto> queryEffectivePlanDetailListByCondition(
    		List<String> startCodeList, List<String> arrvCodeList,
			String productCode, Date billDate);
    
    /**
     * 
     * @Description: 根据条件查询时效方法
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-25 上午11:37:12
     * @param startCodeList
     * @param arrvCodeList
     * @param productCode
     * @param billDate
     * @return
     * @version V1.0
     */
     List<EffectivePlanDto> queryEffectivePlanDetailListByCondition(
    		Set<String> startCodeList, Set<String> arrvCodeList,
			String productCode, Date billDate);
 
   /**
    * 
    * <p>根据ID查询明细实体信息</p> 
    * @author DP-Foss-YueHongJie
    * @date 2013-1-10 上午9:08:50
    * @param id
    * @return
    * @see
    */
    EffectivePlanDetailEntity getEffectiveDetailEntityById(String id);

}