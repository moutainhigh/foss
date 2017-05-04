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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/dao/impl/PriceEntryDao.java
 * 
 * FILE NAME        	: PriceEntryDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDistrictRegionDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DistrictRegionEntity;

/**
 * 
 * @Description: 行政区域与时效、汽运、空运价格区域关系表DAO
 * DistrictRegionDao.java Create on 2013-4-17 下午2:19:27
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class DistrictRegionDao extends SqlSessionDaoSupport implements IDistrictRegionDao{
    /**
     * ibatis mapper
     */
    private static final String NAME_SPACE = "foss.pkp.pkp-pricing.districtRegionMapper.";
    /**
	 * 
	 * @Description: 查询所有行政区域与时效、汽运、空运价格区域关系
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午2:13:39
	 * @param districtCode
	 * @return
	 * @version V1.0
	 */
    @Override
	@SuppressWarnings("unchecked")
    public List<DistrictRegionEntity> selectByDistrictCode() {
    	return getSqlSession().selectList(NAME_SPACE+"selectByDistrictCode", null);
    }
	/**
	 * 
	 * @Description: 根据行政区域CODE查询
	 * @author FOSSDP-sz
	 * @date 2013-4-17 下午2:13:39
	 * @param districtCode
	 * @return
	 * @version V1.0
	 */
	@Override
    public DistrictRegionEntity selectByDistrictCode(String districtCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("districtCode", districtCode);
		return (DistrictRegionEntity)getSqlSession().selectOne(NAME_SPACE+"selectByDistrictCode", map);
	}
    /**
     * 
     * @Description: 添加新记录
     * @author FOSSDP-sz
     * @date 2013-4-17 下午2:16:02
     * @param districtRegionEntity
     * @version V1.0
     */
    @Override
    public void insertDistrictRegion(DistrictRegionEntity districtRegionEntity) {
    	getSqlSession().insert(NAME_SPACE+"insertDistrictCode", districtRegionEntity);
    }
    /**
     * 
     * @Description: 修改记录
     * @author FOSSDP-sz
     * @date 2013-4-17 下午2:16:09
     * @param districtRegionEntity
     * @version V1.0
     */
    @Override
    public void updateDistrictRegion(DistrictRegionEntity districtRegionEntity) {
    	getSqlSession().update(NAME_SPACE+"updateByDistrictCode", districtRegionEntity);
    }
    /**
     * 
     * @Description: 删除记录
     * @author FOSSDP-sz
     * @date 2013-4-17 下午2:16:13
     * @param code
     * @version V1.0
     */
    @Override
    public void deleteDistrictRegion(String districtCode) {
    	getSqlSession().delete(NAME_SPACE+"deleteByDistrictCode", districtCode);
    }
}