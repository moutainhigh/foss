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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/VehicleAgencyDeptDao.java
 * 
 * FILE NAME        	: VehicleAgencyDeptDao.java
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.result.DefaultMapResultHandler;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.AgencyBranchOrCompanyDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.frameworkimpl.server.util.AutoMapResultHandler;
import com.deppon.foss.util.define.FossConstants;
/**
 * 偏线代理网点Dao接口实现：对偏线代理网点信息的增删改查的基本操作
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2012-10-15 下午2:14:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2012-10-15 下午2:14:19
 * @since
 * @version
 */
public class VehicleAgencyDeptDao extends SqlSessionDaoSupport implements
	IVehicleAgencyDeptDao {
    
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.outerBranch.";
    
    /**
     * 新增偏线代理网点 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:17:22
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#addVehicleAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public int addVehicleAgencyDept(OuterBranchEntity entity) {
	
	return this.getSqlSession().insert(NAMESPACE + "insert", entity);
    }
    
    /**
     * 根据code作废偏线代理网点 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:17:28
     * @param codes code字符串数组
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#deleteVehicleAgencyDeptByCode(java.lang.String[])
     */
    @Override
    public int deleteVehicleAgencyDeptByCode(String[] codes,String modifyUser) {
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
     * <p>根据虚拟编码查询偏线网点 DMANA-1630</p> 
     * @author 195406 gcl 
     * @date 2014.7.21
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchsByComCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryOuterBranchsByVirtualCode(String[] codes) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("codes", codes);
    	map.put("active0", FossConstants.ACTIVE);
	
    	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchsByVirtualCode",map);
    }
    /**
     * 修改偏线代理网点 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:17:34
     * @param entity 空运/偏线代理网点实体
     * @return 1：成功；-1：失败
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#updateVehicleAgencyDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public int updateVehicleAgencyDept(OuterBranchEntity entity) {
	
	return this.getSqlSession().update(NAMESPACE + "update", entity);
    }
    
    /**
     * 根据传入对象查询符合条件所有偏线代理网点信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:17:40
     * @param limit 每页最大显示记录数
     * @param start 开始记录数
     * @return 符合条件的实体列表
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryVehicleAgencyDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryVehicleAgencyDepts(
	    OuterBranchEntity entity, int limit, int start) {
	
	RowBounds rowBounds = new RowBounds(start, limit);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryVehicleInfos", entity,rowBounds);
    }
    
    /**
     * 统计总记录数 
     * @author 094463-foss-xieyantao
     * @date 2012-10-15 下午2:17:49
     * @param entity 空运/偏线代理网点实体
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @Override
    public Long queryRecordCount(OuterBranchEntity entity) {
	
	return  (Long)this.getSqlSession().selectOne(NAMESPACE + "queryVehicleCount", entity);
    }
    
    /**
     * 根据代理网点编码查询外发代理网点和合作伙伴（代理公司）信息 
     * @author 094463-foss-xieyantao
     * @date 2012-10-24 上午9:21:53
     * @param agencyBranchCode 代理网点编码
     * @return AgencyBranchOrCompanyDto
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryAgencyBranchCompanyInfo(java.lang.String)
     */
    @Override
    public AgencyBranchOrCompanyDto queryAgencyBranchCompanyInfo(
	    String agencyBranchCode) {
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("agencyBranchCode", agencyBranchCode);
	map.put("active", FossConstants.ACTIVE);
	
	return (AgencyBranchOrCompanyDto)this.getSqlSession().selectOne(NAMESPACE + "queryAgencyBranchCompanyInfo", map);
    }
    
    /**
     * 根据传入参数查询代理网点（空运代理网点和偏线代理网点） 
     * @author 094463-foss-xieyantao
     * @date 2012-11-2 上午9:00:56
     * @param dto 参数封装DTO（包括：目的站、代理网点名称、代理网点类型、用于版本控制时间）
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchs(com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryOuterBranchs(OuterBranchParamsDto dto) {
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("dto", dto);
	map.put("active", FossConstants.ACTIVE);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchs",map);
    }
    
    /**
     * <p>下载外部网点信息 数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-6 下午1:47:06
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchsForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryOuterBranchsForDownload(
	    OuterBranchEntity entity) {
	
	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchsForDownload",entity);
    }
    
    
    /**
     * <p>分页下载外部网点信息 数据</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-6 下午1:47:06
     * @param entity
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchsForDownload(com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryOuterBranchsForDownloadByPage(OuterBranchEntity entity, 
			int start, int limited) {
    	RowBounds rowBounds = new RowBounds(start, limited);
    	return this.getSqlSession().
    			selectList(NAMESPACE + "queryOuterBranchsForDownload",entity, rowBounds);
    }
    
    /**
     * <p>根据代理公司虚拟编码查询该公司的所有代理网点</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-11-30 下午2:01:44
     * @param comVirtualCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchsByComCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryOuterBranchsByComCode(
	    String comVirtualCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("comVirtualCode", comVirtualCode);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchsByComCode",map);
    }
    
    /**
     * <p>
     * 根据代理公司编码查询该代理公司的所有代理网点
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-5-13 下午4:10:27
     * @param comCode
     *            代理公司编码
     * @return
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBrangchsByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryOuterBrangchsByCode(String comCode,String branchType) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("active", FossConstants.ACTIVE);
	map.put("comCode", comCode);
	map.put("branchType", branchType);
	
	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBrangchsByCode",map);
    } 
    
    /**
     * <p>根据外部网点、网点类别查询外部网点是否存在</p> 
     * @author 094463-foss-xieyantao
     * @date 2012-12-20 下午5:52:06
     * @param branchCode 外部网点编码
     * @param branchType 网点类型 :DictionaryValueConstants.OUTERBRANCH_TYPE_KY(空运)
     *                          DictionaryValueConstants.OUTERBRANCH_TYPE_PX(偏线)
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleAgencyDeptDao#queryOuterBranchByBranchCode(java.lang.String, java.lang.String)
     */
    @Override
    public OuterBranchEntity queryOuterBranchByBranchCode(String branchCode,
	    String branchType) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("branchCode", branchCode);
	map.put("branchType", branchType);
	map.put("active", FossConstants.ACTIVE);
	
	return (OuterBranchEntity)this.getSqlSession().selectOne(NAMESPACE + "queryOuterBranchByBranchCode", map);
    }
    
    /**
     * <p>根据行政区域code查找该行政区域下面所有的代理网点信息</p> 
     * @author zhangdongping
     * @date 2013-1-13 下午2:36:41
     * @param code 行政区域code
     * @param billDate 开单日期
     * @return
     * @see
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<OuterBranchEntity> queryOuterBranchsByDistrictCode(String districtCode,
	    Date billDate) { 
	Map  map = new HashMap ();
	map.put("districtCode", districtCode);
	map.put("billDate", billDate);  
	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchsByDistrictCode",map);
    }
    
    /**
     * 
     * @Description: 根据代理网点编码查询代理网点信息
     * Company:IBM
     * @author FOSSDP-sz
     * @date 2013-1-23 上午9:43:09
     * @param branchCode
     * @param branchType
     * @return
     * @version V1.0
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<OuterBranchEntity> queryOuterBranchByCodes(List<String> branchCodes,String branchType) {
    	Map map = new HashMap();
    	map.put("codes", branchCodes);
    	map.put("branchType", branchType);
    	map.put("active", FossConstants.ACTIVE);
    	return this.getSqlSession().selectList(NAMESPACE + "queryOuterBranchByCodes", map);
    }
    
    /**
	 * 根据历史时间和代理网点编码查询代理网点信息（查询历史代理网点信息）
	 * 
	 * 若时间为空，则只根据代理网点编码查询代理网点信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的代理网点
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OuterBranchEntity queryOuterBranchByCode(String code, Date billTime) {
		//定义对象
		OuterBranchEntity agentDept = null;
		//判断时间是否为空
		if(null != billTime){
			// 构造查询条件
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			map.put("billTime", billTime);
			
			//返回集合信息
			List<OuterBranchEntity> outerDeptList = getSqlSession().selectList(NAMESPACE + "queryAgencyBranchInfoByCodeAndTime", map);
			//集合非空判断
			if(CollectionUtils.isNotEmpty(outerDeptList)){
				//返回第一条数据
				return outerDeptList.get(0);
			}else{
				//否则返回空
				return agentDept;
			}
		}else{
			//若时间为空则直接返回
			return agentDept;
		}
	}
	
	/**
     * <p>根据区县代码查询出落地配网点坐标和服务范围坐标</p> 
     * @author foss-WeiXing
     * @date 2014-08-28 下午03:56:35
     * @param countyCode 区县代码
     * @return
     * @see
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OuterBranchEntity> queryServerCoordinatesByCountyCode(String countyCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("active", FossConstants.ACTIVE);
		map.put("countyCode", countyCode);
		return this.getSqlSession().selectList(NAMESPACE + "queryServerCoordinatesByCountyCode",map);
    }
    
    @Override
    public Map<String, String> queryAgentNameMapsByAgentCode(List<String> orgs) {
    	if(CollectionUtils.isEmpty(orgs)){
			return new HashMap<String, String>();
		}
		Map<String, String> retMap = new HashMap<String, String>();
		String[] keyValCol ={"agentDeptCode", "agentDeptName"};
		DefaultMapResultHandler resultHandle = new AutoMapResultHandler(keyValCol,retMap);
		this.getSqlSession().select(NAMESPACE + "queryAgentNameMapsByAgentCode", orgs, resultHandle);
		
    	return retMap;
    }
    
}
