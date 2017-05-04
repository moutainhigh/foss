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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/PricingOrgDao.java
 * 
 * FILE NAME        	: PricingOrgDao.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.dao.impl
 * FILE    NAME: CommonOrgDao.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPricingOrgDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * @Description: 查询物化视图 bse.mv_dest_net
 * PricingOrgDao.java Create on 2013-1-9 下午6:56:22
 * Company:IBM
 * @author FOSSDP-sz
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
public class PricingOrgDao extends SqlSessionDaoSupport implements IPricingOrgDao {
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.pricingOrg.";
	
	/**
	 * 
	 * @Description: 根据条件批量查询组织
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-9 上午10:08:21
	 * @param map
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CommonOrgEntity> queryBatchOrgByCondition(List<String> codes,
			List<String> unifiedCodes, String active, String type) {
		Map map = new HashMap();
		if(CollectionUtils.isNotEmpty(codes)) {
			map.put("codes", codes);
		}
		if(CollectionUtils.isNotEmpty(unifiedCodes)) {
			map.put("unifiedCodes", unifiedCodes);
		}
		if (StringUtils.isNotBlank(type)) {
		    map.put("type", type);
		}
		map.put("active", active);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryBatchOrgByCondition", map);
	}
	/**
	 * 
	 * @Description: 根据批量CODE查询组织
	 * @author FOSSDP-sz
	 * @date 2013-4-10 上午11:06:34
	 * @param codes
	 * @param unifiedCodes
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CommonOrgEntity> queryOrgByBatchCodes(List<String> codes, List<String> unifiedCodes) {
		Map map = new HashMap();
		if(CollectionUtils.isNotEmpty(codes)) {
			map.put("codes", codes);
		}
		if(CollectionUtils.isNotEmpty(unifiedCodes)) {
			map.put("unifiedCodes", unifiedCodes);
		}
		return this.getSqlSession().selectList(
				NAMESPACE + "queryOrgByBatchCodes", map);
	}
	/**
	 * 
	 * @Description: 根据行政区域查询网点
	 * Company:IBM
	 * @author FOSSDP-sz
	 * @date 2013-1-25 上午10:00:16
	 * @param districtCode
	 * @param active
	 * @param type
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CommonOrgEntity> queryStartOrgByDistrict(String districtCode, String active) {
		Map map = new HashMap();
		map.put("active", active);
		map.put("districtCode", districtCode);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryStartOrgByDistrict", map);
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CommonOrgEntity> queryArrvOrgByDistrict(String districtCode, String active) {
		Map map = new HashMap();
		map.put("active", active);
		map.put("districtCode", districtCode);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryArrvOrgByDistrict", map);
	}
	/**
	 * 
	 * @Description: 根据网点CODE查询视图
	 * @author FOSSDP-sz
	 * @date 2013-4-1 上午11:16:08
	 * @param code
	 * @param active
	 * @param types
	 * @return
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CommonOrgEntity queryOrgByCode(String code, String active) {
		Map map = new HashMap();
		map.put("active", active);
		map.put("code", code);
		return (CommonOrgEntity)this.getSqlSession().selectOne(NAMESPACE + "queryOrgByCode", map);
	}
	
	/**
	 * 
	 * @Description: 根据行政区域查询营业部
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-4-8 上午11:07:45
	 * 
	 * @param districtCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CommonOrgEntity> queryStartDeptByDistrict(String districtCode) {
		Map map = new HashMap();
		map.put("active", FossConstants.ACTIVE);
		map.put("leave", FossConstants.ACTIVE);
		map.put("beginTime", new Date());
		map.put("endTime", new Date());
		map.put("districtCode", districtCode);
		return this.getSqlSession().selectList(
				NAMESPACE + "queryStartDeptByDistrict", map);
	}
}
