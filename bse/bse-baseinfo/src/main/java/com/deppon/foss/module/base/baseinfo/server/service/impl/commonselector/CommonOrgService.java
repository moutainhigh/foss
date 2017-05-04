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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/commonselector/CommonOrgService.java
 * 
 * FILE NAME        	: CommonOrgService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: bse-baseinfo
 * PACKAGE NAME: com.deppon.foss.module.base.baseinfo.server.service.impl
 * FILE    NAME: CommonOrgService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAreaAddressService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CommonOrgVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 公共组件查询--组织查询 service.
 *
 * @author panGuangJun
 * @date 2012-11-28 上午10:41:47
 */
public class CommonOrgService implements ICommonOrgService {
	// DAO实现
	/** The common org dao. */
	private ICommonOrgDao commonOrgDao;
	// addressService
	/** The area address service. */
	private IAreaAddressService areaAddressService;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	private ISaleDepartmentService saleDepartmentService;


	// set方法--用于spring注入
	/**
	 * Sets the common org dao.
	 *
	 * @param commonOrgDao the new common org dao
	 */
	public void setCommonOrgDao(ICommonOrgDao commonOrgDao) {
		this.commonOrgDao = commonOrgDao;
	}
	

	

	/**
	 * description 通过条件查询组织信息总数--返回组织的编码和名称.
	 *
	 * @param orgSerchCondtion the org serch condtion
	 * @return the list
	 * @author panGuangJun
	 * @date 2012-11-28 上午11:19:43
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService#countOrgByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition)
	 */
	@Override
	public List<CommonOrgEntity> searchOrgByCondition(CommonOrgVo orgSerchCondtion) {
		//填充查询条件
		OrgSearchCondition condtion = getSearchCondtion(orgSerchCondtion);
		List<CommonOrgEntity> list = commonOrgDao.queryOrgByCondition(condtion);
		completeRegionInfo(list);
		return list;
	}
	/**
	 * 构造查询条件.
	 *
	 * @param orgSerchCondtion the org serch condtion
	 * @return OrgSearchCondition
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-17 下午1:38:51
	 */
	private OrgSearchCondition getSearchCondtion(CommonOrgVo orgSerchCondtion) {
		OrgSearchCondition condtion = new OrgSearchCondition();
		BeanUtils.copyProperties(orgSerchCondtion, condtion);
		// 如果部门属性不为空，这设置部门属性是'Y'
		if (StringUtil.isEmpty(orgSerchCondtion.getDivision())
				&& StringUtil.isEmpty(orgSerchCondtion.getAirDispatch())
				&& StringUtil.isEmpty(orgSerchCondtion.getBigRegion())
				&& StringUtil.isEmpty(orgSerchCondtion.getBillingGroup())
				&& StringUtil.isEmpty(orgSerchCondtion.getDispatchTeam())
				&& StringUtil.isEmpty(orgSerchCondtion.getDoAirDispatch())
				&& StringUtil.isEmpty(orgSerchCondtion.getIsArrangeGoods())
				&& StringUtil.isEmpty(orgSerchCondtion.getIsDeliverSchedule())
				&& StringUtil.isEmpty(orgSerchCondtion.getIsEntityFinance())
				&& StringUtil.isEmpty(orgSerchCondtion.getSalesDepartment())
				&& StringUtil.isEmpty(orgSerchCondtion.getSmallRegion())
				&& StringUtil.isEmpty(orgSerchCondtion.getTransDepartment())
				&& StringUtil.isEmpty(orgSerchCondtion.getTransferCenter())
				&& StringUtil.isEmpty(orgSerchCondtion.getTransTeam())
				&& StringUtil.isEmpty(orgSerchCondtion.getExpressSalesDepartment())
				&& StringUtil.isEmpty(orgSerchCondtion.getExpressPart())
				&& StringUtil.isEmpty(orgSerchCondtion.getExpressBigRegion())) {
			condtion.setNature(FossConstants.YES);
		} else {
			condtion.setNature(null);
		}
		//313353 sonar
		this.sonarSplitOne(condtion);
		
		if(StringUtils.isNotEmpty(orgSerchCondtion.getDeptCode())){
			condtion.setCode(orgSerchCondtion.getDeptCode());
		}
		return condtion;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private void sonarSplitOne(OrgSearchCondition condtion) {
		if(CollectionUtils.isNotEmpty(condtion.getTypes())){
			List<String> queryTypes=new ArrayList<String>();
			for(String tp : condtion.getTypes()){
				if(StringUtils.equals(DictionaryValueConstants.DEPPON_OWN_ORG,tp)){
					condtion.setType(tp);
				}else{
					queryTypes.add(tp); 
				}
			}
			condtion.setQueryTypes(queryTypes);
		}
	}

	/**
	 * description 通过条件查询组织信息总数--返回组织的编码和名称.
	 *
	 * @param orgSearchCondtion the org search condtion
	 * @return the long
	 * @author panGuangJun
	 * @date 2012-11-28 上午11:19:43
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService#countOrgByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgSearchCondition)
	 */
	@Override
	public Long countOrgByCondition(CommonOrgVo orgSearchCondtion) {
		OrgSearchCondition condtion = getSearchCondtion(orgSearchCondtion);
		return commonOrgDao.countOrgByCondition(condtion);
	}

	/**
	 * 根据前端输入框的值查询组织(值可能传入的是名称，也可能是拼音，也可能是编码).
	 *
	 * @param orgSerchCondtion the org serch condtion
	 * @return the list
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:08:17
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService#searchOrgByName(java.lang.String)
	 */
	@Override
	public List<CommonOrgEntity> searchOrgByParam(CommonOrgVo orgSerchCondtion) {
		OrgSearchCondition condtion = getSearchCondtion(orgSerchCondtion);
		if (StringUtils.equals(FossConstants.YES, orgSerchCondtion.getTransDepartment())){
			if (StringUtil.isNotBlank(orgSerchCondtion.getDeptCode())) {			
				// 获取接收方组织及所有下级组织
				List<String> orgList = orgAdministrativeInfoComplexService.queryDeptCodeListByCode(orgSerchCondtion.getDeptCode(),null);
				condtion.setOrgList(orgList);
			}
		}
		if (StringUtils.equals(FossConstants.YES, orgSerchCondtion.getTransTeam())){
			if (StringUtil.isNotBlank(orgSerchCondtion.getDeptCode())) {			
				// 获取接收方组织及所有下级组织				
				List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgSerchCondtion.getDeptCode());
				List<String> orgList = new ArrayList<String>();
				if(CollectionUtils.isNotEmpty(orgs)){
					for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgs){
						orgList.add(orgAdministrativeInfoEntity.getCode());
					}
					condtion.setOrgList(orgList);
				}else{
					condtion.setCode(orgSerchCondtion.getDeptCode());
				}
				
			}
		}

		List<CommonOrgEntity> commonOrgs = commonOrgDao.queryOrgByParam(
				condtion, condtion.getStart(), condtion.getLimit());
		//判断营业部时候可出发，可到达
		if(StringUtil.isNotBlank(orgSerchCondtion.getArrive())){
			String arrive = orgSerchCondtion.getArrive();
		//	SaleDepartmentEntity saleDepartmentEntity = new SaleDepartmentEntity();
			List<CommonOrgEntity> commonOrgEntitys = new ArrayList<CommonOrgEntity>();
				for(CommonOrgEntity commonOrg:commonOrgs){
					//如果是营业部则判断是否可以出发到达
					if(StringUtils.equals(FossConstants.YES, commonOrg.getSalesDepartment())){
						SaleDepartmentEntity saleDepartmentEntity = saleDepartmentService.querySaleDepartmentByCode(commonOrg.getCode());
						if(!StringUtils.equals(arrive, saleDepartmentEntity.getArrive())){
							commonOrgEntitys.add(commonOrg);
						}
					}
				}
				//将不符合要求的营业部删掉
				commonOrgs.removeAll(commonOrgEntitys);
		}
		completeRegionInfo(commonOrgs);
		return commonOrgs;
	}
 
	/**
	 * 根据网点编码查询网点信息
	 * @author 101911-foss-zhouChunlai
	 * @param 
	 * @date 2013-1-22 下午3:02:41
	 * @return 
	 */
	public CommonOrgEntity searchOrgInfoByCode(String code) {
		OrgSearchCondition condtion=new OrgSearchCondition();
		if(StringUtil.isBlank(code)){
			return null;
		}
		//设置网点编码
		condtion.setCode(code);
		//设置有效状诚
		condtion.setActive(FossConstants.ACTIVE);
		//设置查询判断标识
		condtion.setNature(FossConstants.YES);
		List<CommonOrgEntity> list = commonOrgDao.queryOrgByCondition(condtion);
		completeRegionInfo(list);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 填充省市县的名称.
	 *
	 * @param commonOrgs the common orgs
	 * @return void
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-17 下午2:38:06
	 */
	private void completeRegionInfo(List<CommonOrgEntity> commonOrgs) {
		if (CollectionUtils.isNotEmpty(commonOrgs)) {
			for (int i = 0; i < commonOrgs.size(); i++) {
				if (!StringUtil.isEmpty(commonOrgs.get(i).getProvinceCode())) {
					//根据省份编码获取省份对象
					AdministrativeRegionsEntity prov = areaAddressService
							.queryRegionByCode(commonOrgs.get(i)
									.getProvinceCode());
					if (null != prov) {
						//设置省份名称
						commonOrgs.get(i).setProvinceName(prov.getName());
					}
				}
				if (!StringUtil.isEmpty(commonOrgs.get(i).getCityCode())) {
					//根据省份编码获取市对象
					AdministrativeRegionsEntity city = areaAddressService
							.queryRegionByCode(commonOrgs.get(i)
									.getCityCode());
					if (null != city) {
						//设置市名称
						commonOrgs.get(i).setCityName(city.getName());
					}
				}
				if (!StringUtil.isEmpty(commonOrgs.get(i).getCountyCode())) {
					//根据省份编码获取县市对象
					AdministrativeRegionsEntity county = areaAddressService
							.queryRegionByCode(commonOrgs.get(i)
									.getCountyCode());
					if (null != county) {
						//设置县名称
						commonOrgs.get(i).setCountyName(county.getName());
					}
				}
			}
		}
	}

	/**
	 * 返回查询的总条数.
	 *
	 * @param orgSerchCondtion the org serch condtion
	 * @return the long
	 * @author 078823-foss-panGuangJun
	 * @date 2012-12-6 下午3:08:17
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService#countOrgByName(java.lang.String)
	 */
	@Override
	public Long countOrgByParam(CommonOrgVo orgSerchCondtion) {
		//填充查询条件
		OrgSearchCondition condtion = getSearchCondtion(orgSerchCondtion);
		if (StringUtils.equals(FossConstants.YES, orgSerchCondtion.getTransDepartment())){
			if (StringUtil.isNotBlank(orgSerchCondtion.getDeptCode())) {			
				// 获取接收方组织及所有下级组织
				List<String> orgList = orgAdministrativeInfoComplexService.queryDeptCodeListByCode(orgSerchCondtion.getDeptCode(),null);
				condtion.setOrgList(orgList);
			}
		}
		if (StringUtils.equals(FossConstants.YES, orgSerchCondtion.getTransTeam())){
			if (StringUtil.isNotBlank(orgSerchCondtion.getDeptCode())) {			
				// 获取接收方组织及所有下级组织				
				List<OrgAdministrativeInfoEntity> orgs = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllSubByCode(orgSerchCondtion.getDeptCode());
				List<String> orgList = new ArrayList<String>();
				if(CollectionUtils.isNotEmpty(orgs)){
					for(OrgAdministrativeInfoEntity orgAdministrativeInfoEntity : orgs){
						orgList.add(orgAdministrativeInfoEntity.getCode());
					}
					condtion.setOrgList(orgList);
				}else{
					condtion.setCode(orgSerchCondtion.getDeptCode());
				}
				
			}
		}
		return commonOrgDao.countOrgByParam(condtion);
	}

	/** 
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:45:16
	 * @param dto
	 * @param start
	 * @param limit
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService#queryOrgByParam(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto, int, int)
	 */
	public List<CommonOrgEntity> queryOrgByParam(CommonOrgDto dto, int start, int limit){
		//条件的非空判断
		if (null == dto) {
		//抛出异常
			throw new BusinessException();
		}
		//调用Dao层的方法，通过所有条件进行分页查询
		return commonOrgDao.queryOrgByParamNew(dto, start, limit);
	}
	/** 
	 * <p>org组织表选择器</p> 
	 * @author 232607 
	 * @date 2015-6-2 上午10:45:54
	 * @param dto
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrgService#queryOrgByParamCount(com.deppon.foss.module.base.baseinfo.api.shared.dto.CommonOrgDto)
	 */
	public Long queryOrgByParamCount(CommonOrgDto dto){
		return commonOrgDao.queryOrgByParamNewCount(dto);
	}
	/**
	 * setter.
	 *
	 * @param areaAddressService the new area address service
	 */
	public void setAreaAddressService(IAreaAddressService areaAddressService) {
		this.areaAddressService = areaAddressService;
	}
	
	public IOrgAdministrativeInfoComplexService getOrgAdministrativeInfoComplexService() {
		return orgAdministrativeInfoComplexService;
	}
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}
	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}
	
	
	
}
