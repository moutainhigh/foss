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

 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/VehicleAgencyCompanyDao.java

 * 

 * FILE NAME        	: VehicleAgencyCompanyDao.java

 * 

 * AUTHOR			: FOSS综合管理开发组

 * 

 * HOME PAGE		:  http://www.deppon.com

 * 

 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.

 ******************************************************************************/

/*

 * Copyright by Deppon and the original author or authors.

 * 

 * This document only allow internal use ,Any of your behaviors using the file

 * not internal will pay legal responsibility.

 *

 * You may learn more information about Deppon from

 *

 *      http://www.deppon.com

 *

 */ 

package com.deppon.foss.module.base.baseinfo.server.dao.impl;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISendAgentCompanyInfoToWDGHService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;



/**

 * 偏线代理公司Dao接口实现：对偏线代理公司信息的增删改查的基本操作

 * <p style="display:none">modifyRecord</p>

 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 下午1:56:59 </p>

 * @author 094463-foss-xieyantao

 * @date 2012-10-15 下午1:56:59

 * @since

 * @version

 */

public class VehicleAgencyCompanyDao extends SqlSessionDaoSupport implements

	IVehicleAgencyCompanyDao {

    

    private static final String NAMESPACE = "foss.bse.bse-baseinfo.businessPartner.";

	

    /**

     * 同步代理公司给wdgh 系统接口service

     */

    private ISendAgentCompanyInfoToWDGHService sendAgentCompanyInfoToWDGHService;

    

    public void setSendAgentCompanyInfoToWDGHService(

			ISendAgentCompanyInfoToWDGHService sendAgentCompanyInfoToWDGHService) {

		this.sendAgentCompanyInfoToWDGHService = sendAgentCompanyInfoToWDGHService;

	}



    /**

     * 新增偏线代理公司 

     * @author 094463-foss-xieyantao

     * @date 2012-10-15 下午1:58:28

     * @param entity 偏线/空运代理公司实体

     * @return 1：成功；-1：失败

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#addVehicleAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)

     */

    @Override

    public int addVehicleAgencyCompany(BusinessPartnerEntity entity) {

	

	return this.getSqlSession().insert(NAMESPACE + "insert", entity);

    }

    

    /**

     * 根据code作废偏线代理公司 

     * @author 094463-foss-xieyantao

     * @date 2012-10-15 下午1:58:34

     * @param codes code字符串数组

     * @param modifyUser 修改人

     * @return 1：成功；-1：失败

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#deleteVehicleAgencyCompanyByCode(java.lang.String[])

     */

    @Override

    public int deleteVehicleAgencyCompanyByCode(String[] codes,String modifyUser) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	Date date = new Date();

	map.put("codes", codes);

	map.put("modifyUser", modifyUser);

	map.put("modifyDate", date);

	map.put("versionNo", date.getTime());

	map.put("active", FossConstants.INACTIVE);

	map.put("active0", FossConstants.ACTIVE);

	

	return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);

    }

    

    /**

     * 根据id作废偏线代理公司 

    */

    @Override

    public int deleteVehicleAgencyCompanyById(String id) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("active", FossConstants.INACTIVE);

	map.put("id", id);

	return this.getSqlSession().update(NAMESPACE + "deleteById", map);

    }

    

    

    /**

     * <p>根据虚拟编码作废偏线代理公司</p> 

     */

    public int deleteAgencyCompanyByVirtualCode(String virtualCode,String modifyUser) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	Date date = new Date();

	map.put("virtualCode", virtualCode);

	map.put("modifyUser", modifyUser);

	map.put("modifyDate", date);

	map.put("versionNo", date.getTime());

	map.put("active", FossConstants.INACTIVE);

	map.put("active0", FossConstants.ACTIVE);

	

	BusinessPartnerEntity entity = new BusinessPartnerEntity();

	entity.setVirtualCode(virtualCode);

	entity.setActive(FossConstants.INACTIVE);

	List<BusinessPartnerEntity> list = queryVehicleAgencyCompanys(entity, 1, 0);

	if(CollectionUtils.isNotEmpty(list))

		entity = list.get(0);

	//同步删除代理公司到WDGH	

	sendAgentCompanyInfoToWDGHService.syncAgentCompanyInfo(entity,(NumberConstants.THREE).toString());	

	

	return this.getSqlSession().update(NAMESPACE + "deleteByVirtualCode", map);

    }



    /**

     * <p>根据虚拟编码作废偏线代理公司</p> 

     * @author 094463-foss-xieyantao

     * @date 2012-12-29 下午3:48:46

     * @param virtualCode

     * @param modifyUser

     * @return

     * @see

     */

    @Override

    public int deleteAgencyCompanyByCompanyCode(String agentCompanyCode) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("agentCompanyCode", agentCompanyCode);

	map.put("active", FossConstants.INACTIVE);

	return this.getSqlSession().update(NAMESPACE + "deleteByAgentCompanyCode", map);

    }

    

    /**

     * 修改偏线代理公司 

     * @author 094463-foss-xieyantao

     * @date 2012-10-15 下午1:58:41

     * @param entity

     * @return 1：成功；-1：失败 

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#updateVehicleAgencyCompany(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)

     */

    @Override

    public int updateVehicleAgencyCompany(BusinessPartnerEntity entity) {

	

	return this.getSqlSession().update(NAMESPACE + "update", entity);

    }

    

    /**

     * 根据id更新偏线代理公司 

     */

    @Override

    public int updateVehicleAgencyCompanyById(BusinessPartnerEntity entity) {

	

	return this.getSqlSession().update(NAMESPACE + "updateById", entity);

    }



    /**

     * 根据传入对象查询符合条件所有偏线代理公司信息 

     * @author 094463-foss-xieyantao

     * @date 2012-10-15 下午1:58:50

     * @param entity 偏线/空运代理公司实体

     * @param limit 每页最大显示记录数

     * @param start 开始记录数

     * @return 符合条件的实体列表

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#queryVehicleAgencyCompanys(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity, int, int)

     */

    @SuppressWarnings("unchecked")

    @Override

    public List<BusinessPartnerEntity> queryVehicleAgencyCompanys(

	    BusinessPartnerEntity entity, int limit, int start) {

	

	RowBounds rowBounds = new RowBounds(start, limit);

	

	return this.getSqlSession().selectList(NAMESPACE + "queryAllInfos", entity,rowBounds);

    }

    

    /**

     * 统计总记录数 

     * @author 094463-foss-xieyantao

     * @date 2012-10-15 下午1:58:56

     * @param entity 偏线/空运代理公司实体

     * @return 符合条件的总记录数

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)

     */

    @Override

    public Long queryRecordCount(BusinessPartnerEntity entity) {

	

	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);

    }

    

    /**

     * 根据偏线代理公司名称查询代理公司信息 (验证该代理公司是否存在)

     * @author 094463-foss-xieyantao

     * @date 2012-10-23 上午11:05:59

     * @param agentCompanyName 空运代理公司名称

     * @return null:不存在  BusinessPartnerEntity:存在

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao#queryEntityByName(java.lang.String)

     */

    @Override

    public BusinessPartnerEntity queryEntityByName(String agentCompanyName) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("agentCompanyName", agentCompanyName);

	map.put("active", FossConstants.ACTIVE);

	map.put("agentCompanySort", DictionaryValueConstants.OUTERBRANCH_TYPE_PX);

	

	return (BusinessPartnerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryEntityByName",map);

    }

    

    /**

     * 根据偏线代理公司简称查询代理公司信息 (验证该代理公司是否存在) 

     * @author 094463-foss-xieyantao

     * @date 2012-10-23 上午11:10:56

     * @param simplename 代理公司简称

     * @return null:不存在  BusinessPartnerEntity:存在

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao#queryEntityBySimplename(java.lang.String)

     */

    @Override

    public BusinessPartnerEntity queryEntityBySimplename(String simplename) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("simplename", simplename);

	map.put("active", FossConstants.ACTIVE);

	map.put("agentCompanySort", DictionaryValueConstants.OUTERBRANCH_TYPE_PX);

	

	return (BusinessPartnerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryEntityBySimplename",map);

    }

    

    /**

     * 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在) 

     * @author 094463-foss-xieyantao

     * @date 2012-10-23 上午11:13:16

     * @param agentCompanyCode 代理公司编码

     * @param agencySort 代理公司类型：偏线(PX)、空运（KY）

     * @return null:不存在  BusinessPartnerEntity:存在

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IAirAgencyCompanyDao#queryEntityByCode(java.lang.String)

     */

    @Override

    public BusinessPartnerEntity queryEntityByCode(String agentCompanyCode,String agencySort) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("agentCompanyCode", agentCompanyCode);

	map.put("active", FossConstants.ACTIVE);

	if (StringUtils.isNotBlank(agencySort)) {

	    map.put("agentCompanySort",agencySort);

	}

	

	return (BusinessPartnerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryEntityByCode",map);

    }

    

    /**

     * 根据偏线代理公司编码查询代理公司信息 (验证该代理公司是否存在) 

     */

    @Override

    public BusinessPartnerEntity queryEntityByCodeAndActive(String agentCompanyCode,String active) {

	

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("agentCompanyCode", agentCompanyCode);

	if (StringUtils.isNotBlank(active)) {

	    map.put("active",active);

	}

	

	return (BusinessPartnerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryEntityByCode",map);

    }

    

    

    /**

     * 根据代理公司虚拟编码查询代理公司信息 

     * @author 094463-foss-xieyantao

     * @date 2012-12-12 下午3:18:28

     * @param virtualCode 代理公司虚拟编码

     * @return null:不存在  BusinessPartnerEntity:存在 

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#queryEntityByVirtualCode(java.lang.String)

     */

    @Override

    public BusinessPartnerEntity queryEntityByVirtualCode(String virtualCode) {

	Map<String, String> map = new HashMap<String, String>();

	map.put("virtualCode", virtualCode);

	map.put("active", FossConstants.ACTIVE);

	

	return (BusinessPartnerEntity)this.getSqlSession().selectOne(NAMESPACE + "queryEntityByVirtualCode",map);

    }

    

    /**

     * <p>下载合作伙伴(代理公司信息) 数据</p>

     * @author 094463-foss-xieyantao

     * @date 2012-12-15 下午3:20:30

     * @param entity

     * @return 

     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyCompanyDao#queryBusinessPartnersForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity)

     */

    @SuppressWarnings("unchecked")

    @Override

    public List<BusinessPartnerEntity> queryBusinessPartnersForDownload(

	    BusinessPartnerEntity entity) {

	

	return this.getSqlSession().selectList(NAMESPACE + "queryBusinessPartnersForDownload",entity);

    }



}

