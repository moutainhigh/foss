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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/api/server/service/IProductService.java
 * 
 * FILE NAME        	: IProductService.java
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

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.*;

/**
 * 
 * @Description: 行政区域与时效、汽运、空运价格区域关系表Service
 * IDistrictRegionService.java Create on 2013-4-17 下午4:48:49
 * Company:IBM
 * @author FOSSDP-Administrator
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public interface IDistrictRegionService extends IService {
	/**
	 * 
	 * @Description: 添加记录
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午5:01:41
	 * @param districtCode
	 * @version V1.0
	 */
    void addDistrictRegion(String districtCode);
    /**
     * 
     * @Description: 查询所有行政区域与时效、汽运、空运价格区域关系
     * @author FOSSDP-sz
     * @date 2013-4-19 下午3:29:18
     * @return
     * @version V1.0
     */
    List<DistrictRegionEntity> searchDistrictRegion();
	/**
	 * 
	 * @Description: 部门Code寻找价格到达区域ID 
	 * 查询规则： 
	 * 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-yangkang
	 * @date 2014-7-7 下午6:37:32
	 * @param deptNo
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
    List<PriceRegionBigGoodsArriveEntity> findRegionBigArriveOrgByDeptNo(String orgCode, Date billDate, String productCode, String regionNature);
	/**
	 * 
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author FOSSDP-yangkang
	 * @date 2014-7-7 下午6:37:32
	 * @param deptNo
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
    PriceRegionBigGoodsEntity findRegionBigOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature);



	/**
	 *
	 * @Description: 部门Code寻找价格到达区域ID
	 * 查询规则：
	 * 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author 311417 wangfeng
	 * @date 2016.07.14
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	PriceRegionEcGoodsArriveEntity findRegionEcGoodsArriveOrgByDeptNo(String orgCode, Date billDate, String productCode, String regionNature);
	/**
	 *
	 * @Description: 部门Code寻找价格始发区域ID 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * @author 311417 wangfeng
	 * @date 2016.07.14
	 * @param orgCode
	 * @param billDate
	 * @param productCode
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	PriceRegionEcGoodsEntity findRegionEcGoodsOrgByDeptCode(String orgCode, Date billDate, String productCode, String regionNature);
}