/**
 *  initial comments.
 */
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleAgencyDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.AdministrativeRegionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.OuterBranchParamsDto;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.RegionException;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgValueAddCacheDeal;
import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionValueAddCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * @Description: 增值区域新增修改查询service 
 * RegionAirService.java Create on 2013-3-13 下午4:04:42
 * Company:IBM
 * @author 043258-foss-zhaobin
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * @version V1.0
 */
@Transactional
public class RegionValueAddService implements IRegionValueAddService {
	
	private static final Logger log = Logger.getLogger(RegionValueAddService.class);
	
	/**
	 * 组织机构接口
	 */
	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 * 网点SERVICe
	 */
	@Inject
	private IVehicleAgencyDeptService vehicleAgencyDeptService;
	/**
	 * 行政区域SERVICE
	 */
	@Inject
	private IAdministrativeRegionsService administrativeRegionsService;

	/**
	 * 职员service
	 */
	@Inject
	private IEmployeeService employeeService;
	/**
	 * 区域 DAO 
	 */
	@Inject
	private IRegionValueAddDao regionValueAddDao;

	/**
	 * 增值区域 缓存处理
	 */
	private PriceRegionValueAddCacheDeal priceRegionValueAddCacheDeal;

	/**
	 * 增值区域与部门 缓存处理
	 */
	private PriceRegionOrgValueAddCacheDeal priceRegionOrgValueAddCacheDeal;
	/**
	 * 行政区域与区域关系缓存处理
	 */
	@Inject
	private DistrictRegionCacheDeal districtRegionCacheDeal;
	/**
	 * 
	 */
	@Inject
	private IDistrictRegionService districtRegionService;

	/** 
	 * 
	 * 根据条件查询区域 
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:19:48
	 * 
	 * @param regionEntity
	 * 
	 * @param start
	 * 
	 * @param limit
	 * 
	 * @return 
	 *  
	 */
	@Override
	public List<PriceRegionValueAddEntity> searchRegionByCondition(
			PriceRegionValueAddEntity regionEntity, int start, int limit) {
	      //设置条件
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		List<PriceRegionValueAddEntity> priceRegionAirEntityList = regionValueAddDao.searchRegionByCondition(regionEntity, start, limit);
		if(CollectionUtils.isNotEmpty(priceRegionAirEntityList)){
			for (PriceRegionValueAddEntity model : priceRegionAirEntityList) {
				model.setRegionNature(regionEntity.getRegionNature());
				if(StringUtil.isNotBlank(model.getModifyUser())){
					//查询修改人信息
					EmployeeEntity employeeEntity= employeeService.queryEmployeeByEmpCode(model.getModifyUser());
					if(employeeEntity!=null){
						//设置修改人姓名
						model.setModifyUserName(employeeEntity.getEmpName());
					}
				}
			}
		}
		//返回结果
		return priceRegionAirEntityList;
	}

	/** 
	 * 
	 *  查询时效
	 *  
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:22:06
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 */
	@Override
	public List<PriceRegionValueAddEntity> findRegionByCondition(
			PriceRegionValueAddEntity regionEntity) {
	    //设置条件
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		List<PriceRegionValueAddEntity> priceRegionAirEntityList = regionValueAddDao.findRegionByCondition(regionEntity);
		for (PriceRegionValueAddEntity model : priceRegionAirEntityList) {
			model.setRegionNature(regionEntity.getRegionNature());
		}
		//返回结果
		return priceRegionAirEntityList;
	}

	/** 
	 * 
	 * 计算条数 
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:23:51
	 * 
	 * @param regionEntity
	 * 
	 * @return  
	 */
	@Override
	public Long countRegionByCondition(PriceRegionValueAddEntity regionEntity) {
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		return regionValueAddDao.countRegionByCondition(regionEntity);
	}

	/** 
	 * 添加区域
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:24:14
	 * 
	 * @param regionEntity
	 * 
	 * @param addPriceRegionOrgValueAddEntityList 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#addRegion(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity, java.util.List)
	 */
	@Override
	public void addRegion(PriceRegionValueAddEntity regionEntity, List<PriceRegionOrgValueAddEntity> addPriceRegionOrgValueAddEntityList) {
		String regionId = UUIDUtils.getUUID();
		// 设置ID
		regionEntity.setId(regionId);
		//转换时间(时间得失明天开始)
//		Date begintime=DateUtils.convert(DateUtils.convert(new Date(new Date().getTime()+PricingConstants.ONE_DAY_MILLISECOND),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
		Date begintime = new Date();
		String regionNature = regionEntity.getRegionNature();
		Long versionNo = new Date().getTime();
		regionEntity.setVersionNo(versionNo);
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		String userCode = user.getEmployee().getEmpCode();
		String orgCode = user.getEmployee().getDepartment().getCode();
		regionEntity.setCreateOrgCode(orgCode);
		regionEntity.setCreateUser(userCode);
		regionEntity.setModifyOrgCode(orgCode);
		regionEntity.setModifyUser(userCode);
		regionEntity.setBeginTime(begintime);
		regionEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		// 判断名称是否重复
		/*boolean isTheSameRegionName = this.isTheSameRegionName(regionEntity,
				false);
		if (isTheSameRegionName) {
			throw new RegionException(RegionException.REGION_NAME_SAME,
					RegionException.REGION_NAME_SAME);
		}else{
			//did nothing
		}*/
		PriceRegionValueAddEntity checkEntity = new PriceRegionValueAddEntity();
		checkEntity.setRegionCode(regionEntity.getRegionCode());
		checkEntity.setRegionNature(regionEntity.getRegionNature());
		// 判断CODE是否重复
		boolean isTheSameRegionCode = this.isTheSameRegionCode(checkEntity);
		if (isTheSameRegionCode) {
			throw new RegionException(RegionException.REGION_CODE_SAME,
					RegionException.REGION_CODE_SAME);
		}else{
			//did nothing
		}
		if(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())){
			//校验行政区域
			checkRegionOrganizationType(regionEntity);
		}
		//添加数据
		log.info("增值区域-新增 REGION_ORG");
		regionValueAddDao.addRegion(regionEntity);
		//处理该区域包含的组织
		if (addPriceRegionOrgValueAddEntityList != null) {
			log.info("增值区域-新增 REGION_ORG" + addPriceRegionOrgValueAddEntityList.size());
			for (PriceRegionOrgValueAddEntity model : addPriceRegionOrgValueAddEntityList) {
			    //准备数据
				String regionOrgId = UUIDUtils.getUUID();
				model.setId(regionOrgId);// 设置ID
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setCreateOrgCode(orgCode);
				model.setCreateUser(userCode);
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				model.setPriceRegionCode(regionEntity.getRegionCode());
				model.setPriceRegionId(regionId);
				model.setRegionNature(regionEntity.getRegionNature());
				//跟父表的状态一样
				model.setActive(regionEntity.getActive());
				model.setBeginTime(begintime);
				model.setEndTime(new Date(PricingConstants.ENDTIME));
				//判断改组织在区域组织表中是否存在
				PriceRegionOrgValueAddEntity priceRegionOrgAirEntity = new PriceRegionOrgValueAddEntity();
				priceRegionOrgAirEntity.setIncludeOrgCode(model.getIncludeOrgCode());	
				priceRegionOrgAirEntity.setRegionNature(regionNature);
				List<PriceRegionOrgValueAddEntity> priceRegionOrgAirEntityList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
				if(CollectionUtils.isNotEmpty(priceRegionOrgAirEntityList)){
					 for(int loop=0;loop<priceRegionOrgAirEntityList.size();loop++){
						 PriceRegionOrgValueAddEntity object = priceRegionOrgAirEntityList.get(loop);
						 if(begintime.getTime() <= object.getEndTime().getTime()){
							 PriceRegionValueAddEntity priceRegionAirEntity = regionValueAddDao.searchRegionByID(object.getPriceRegionId(),regionNature);
							 String message = "在"+priceRegionAirEntity.getRegionName()+"区域下已经存在部门"+object.getIncludeOrgCode();
							 log.info("增值区域：" + message);
							 throw new RegionException(message,message);
						 }		 
					 }
					 //没有抛异常则新增
					 log.info("增值区域-新增 REGION_ORG");
					 regionValueAddDao.addRegionOrg(model);
				}else{
					 log.info("增值区域-新增 REGION_ORG");
					 regionValueAddDao.addRegionOrg(model);
				}
			}
		} else {
			log.info("增值区域-新增 REGION_ORG addPriceRegionOrgValueAddEntityList is Null ");
		}
		/**
		 * 行政区域与区域关系
		 */
		if(StringUtils.equals(FossConstants.ACTIVE, regionEntity.getActive())) {
			if(StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, regionEntity.getRegionType())){
				if(StringUtils.isNotEmpty(regionEntity.getCountyCode())) {
					districtRegionService.addDistrictRegion(regionEntity.getCountyCode());
					districtRegionCacheDeal.getDistrictRegionCache().invalid(regionEntity.getCountyCode());
				} 
				if(StringUtils.isNotEmpty(regionEntity.getCityCode())) {
					districtRegionService.addDistrictRegion(regionEntity.getCityCode());
					districtRegionCacheDeal.getDistrictRegionCache().invalid(regionEntity.getCityCode());
				}
			} else if(PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(regionEntity.getRegionType())) {
				if(CollectionUtils.isNotEmpty(addPriceRegionOrgValueAddEntityList)) {
					List<String> cityList = new ArrayList<String>();
					List<String> countyList = new ArrayList<String>();
					for (PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntity : addPriceRegionOrgValueAddEntityList) {
						String deptCode = priceRegionOrgValueAddEntity.getIncludeOrgCode();
						OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode, new Date());
						if (orginfo != null) {
							String countyCode = orginfo.getCountyCode(); 
							String cityCode = orginfo.getCityCode();
							if (StringUtil.isNotEmpty(countyCode) && !cityList.contains(countyCode)) {
								countyList.add(countyCode);
							}
							if (StringUtil.isNotEmpty(cityCode) && !cityList.contains(cityCode)) {
								cityList.add(cityCode);
							}
						}
					}
					if(CollectionUtils.isNotEmpty(countyList)) {
						for (String countyCode : countyList) {
							districtRegionService.addDistrictRegion(countyCode);
							districtRegionCacheDeal.getDistrictRegionCache().invalid(countyCode);
						}
					}
					if(CollectionUtils.isNotEmpty(cityList)) {
						for (String cityCode : cityList) {
							districtRegionService.addDistrictRegion(cityCode);
							districtRegionCacheDeal.getDistrictRegionCache().invalid(cityCode);
						}
					}
				}
			}
		}
	}
	/** 
	 * 
	 * 检测行政组织区域是否重复
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2013-1-25 下午2:26:03
	 * 
	 * @param regionEntity
	 * 
	 * @return 
	 */
	private void checkRegionOrganizationType(PriceRegionValueAddEntity regionEntity){
		String administrativeRegionCodes = regionEntity.getNationCode()+regionEntity.getProCode()+regionEntity.getCityCode()+regionEntity.getCountyCode();
		String regionNature = regionEntity.getRegionNature();
		List<PriceRegionValueAddEntity> priceRegionAirEntityList=regionValueAddDao.checkRegionOrganizationType(administrativeRegionCodes, regionNature, new Date());
		if(CollectionUtils.isNotEmpty(priceRegionAirEntityList)){
			 throw new RegionException("该行政组织下已经存在区域！",null);
		}
	}
	/** 
	 * 
	 * 根据条件查询
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:26:03
	 * 
	 * @param PriceRegionOrgAirEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#searchRegionOrgByCondition(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionOrgValueAddEntity)
	 */
	@Override
	public List<PriceRegionOrgValueAddEntity> searchRegionOrgByCondition(PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntity) {
		List<PriceRegionOrgValueAddEntity> priceRegionOrgAirEntityList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgValueAddEntity);
		
		List<PriceRegionOrgValueAddEntity> resultlist=new ArrayList<PriceRegionOrgValueAddEntity>();
		for(PriceRegionOrgValueAddEntity model:priceRegionOrgAirEntityList){
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(model.getIncludeOrgCode());
			if(orgAdministrativeInfo!=null&&StringUtils.equalsIgnoreCase(orgAdministrativeInfo.getActive(), FossConstants.ACTIVE))
			{
        			model.setIncludeOrgName(orgAdministrativeInfo.getName());
        			model.setCityCode(orgAdministrativeInfo.getCityCode());
        			model.setCityName(orgAdministrativeInfo.getCityName());
        			model.setNationCode(orgAdministrativeInfo.getCountryRegion());
        			model.setNationName(orgAdministrativeInfo.getCountryRegionName());
        			model.setProCode(orgAdministrativeInfo.getProvCode());
        			model.setProName(orgAdministrativeInfo.getProvName());
        			model.setCountyCode(orgAdministrativeInfo.getCountyCode());
        			model.setCountyName(orgAdministrativeInfo.getCountyName());
        			resultlist.add(model);
			}else
			{
			    OuterBranchParamsDto dto = new OuterBranchParamsDto(); 
				dto.setAgentDeptCode(model.getIncludeOrgCode());
				List<OuterBranchEntity> entity = vehicleAgencyDeptService
						.queryOuterBranchs(dto);
				if (CollectionUtils.isNotEmpty(entity)) {
				    OuterBranchEntity object = entity.get(0);
				      if(object!=null&&StringUtils.equalsIgnoreCase(object.getActive(), FossConstants.ACTIVE))
					{
        					model.setIncludeOrgName(object.getAgentDeptName());
        		    			model.setCityCode(object.getCityCode());
        		    			model.setCityName(object.getCityName());
        		    			model.setNationCode(object.getCountryRegion());
        		    			model.setNationName(object.getCountryRegionName()==null?"中国":object.getCountryRegionName());
        		    			model.setProCode(object.getProvCode());
        		    			model.setProName(object.getProvName());
        		    			model.setCountyCode(object.getCountyCode());
        		    			model.setCountyName(object.getCountyName());
        		    			resultlist.add(model);
					}
				}
			}
		}
		return resultlist;
	}

	/** 
	 * 
	 * 激活
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:26:20
	 * 
	 * @param regionIds
	 * 
	 * @param regionNature 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#activeRegion(java.util.List, java.lang.String)
	 */
	@Override
	public void activeRegion(List<String> regionIds, String regionNature,Date beginTime) {
		for(String id:regionIds){
			PriceRegionValueAddEntity model = regionValueAddDao.searchRegionByID(id, regionNature);
			// 获取当前登录用户
			UserEntity user = (UserEntity) UserContext.getCurrentUser();
			if(user==null){
				throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
			}
			String userCode = user.getEmployee().getEmpCode();
			String orgCode = user.getEmployee().getDepartment().getCode();
			Long versionNoOrg = new Date().getTime();
			model.setBeginTime(beginTime);
			model.setVersionNo(versionNoOrg);
			model.setModifyOrgCode(orgCode);
			model.setModifyUser(userCode);
			model.setActive(FossConstants.ACTIVE);
			model.setRegionNature(regionNature);
			regionValueAddDao.updateRegion(model);
			PriceRegionOrgValueAddEntity priceRegionOrgAirEntity = new PriceRegionOrgValueAddEntity();
			priceRegionOrgAirEntity.setPriceRegionId(model.getId());
			priceRegionOrgAirEntity.setRegionNature(regionNature);
			List<PriceRegionOrgValueAddEntity>  priceRegionOrgAirEntityList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
			if(CollectionUtils.isNotEmpty(priceRegionOrgAirEntityList)){
				for(PriceRegionOrgValueAddEntity priceRegioOrgnModel:priceRegionOrgAirEntityList){
					priceRegioOrgnModel.setVersionNo(versionNoOrg);
					//修改状态
					priceRegioOrgnModel.setActive(FossConstants.ACTIVE);
					priceRegioOrgnModel.setRegionNature(regionNature);
					priceRegioOrgnModel.setBeginTime(beginTime);
					priceRegioOrgnModel.setModifyOrgCode(orgCode);
					priceRegioOrgnModel.setModifyUser(userCode);
					regionValueAddDao.updateRegionOrg(priceRegioOrgnModel);
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description: 删除区域
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-12 上午10:46:31
	 * 
	 * @param regionIds
	 * 
	 * @param regionNature
	 * 
	 * @version V1.0
	 */
	@Override
	public void deleteRegion(List<String> regionIds, String regionNature) {
		regionValueAddDao.deleteRegion(regionIds, regionNature);
		regionValueAddDao.deleteRegionOrg(regionIds, regionNature);
	}

	/** 
	 * 更新
	 * @author zhangdongping
	 * @date 2012-12-25 下午2:26:29
	 * @param regionEntity
	 * @param addPriceRegionOrgAirEntityList
	 * @param updatePriceRegionOrgAirEntityList 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#updateRegion(com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity, java.util.List, java.util.List)
	 */
	@Override
	public void updateRegion(PriceRegionValueAddEntity regionEntity,
			List<PriceRegionOrgValueAddEntity> addPriceRegionOrgAirEntityList,
			List<PriceRegionOrgValueAddEntity> updatePriceRegionOrgAirEntityList) {
		Long versionNo = new Date().getTime();
		regionEntity.setVersionNo(versionNo);
		String regionNature = regionEntity.getRegionNature();
//		Date begintime=DateUtils.convert(DateUtils.convert(new Date(new Date().getTime()+PricingConstants.ONE_DAY_MILLISECOND),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
		Date begintime = new Date(); 
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		String userCode = user.getEmployee().getEmpCode();
		String orgCode = user.getEmployee().getDepartment().getCode();
		regionEntity.setModifyOrgCode(orgCode);
		regionEntity.setModifyUser(userCode);
		/*// 判断名称是否重复
		boolean isTheSameRegionName = this.isTheSameRegionName(regionEntity,
				true);
		if (isTheSameRegionName) {
			throw new RegionException(RegionException.REGION_NAME_SAME,
					RegionException.REGION_NAME_SAME);
		}else{
			
		}*/
		//更新数据
		regionValueAddDao.updateRegion(regionEntity);
		//处理包含组织
		if (addPriceRegionOrgAirEntityList != null) {
			for (PriceRegionOrgValueAddEntity model : addPriceRegionOrgAirEntityList) {
				String regionOrgId = UUIDUtils.getUUID();
				// 设置ID
				model.setId(regionOrgId);
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setCreateOrgCode(orgCode);
				model.setCreateUser(userCode);
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				model.setPriceRegionCode(regionEntity.getRegionCode());
				model.setRegionNature(regionEntity.getRegionNature());
				model.setActive(regionEntity.getActive());
				model.setBeginTime(begintime);
				model.setEndTime(new Date(PricingConstants.ENDTIME));
				//判断改组织在区域组织表中是否存在
				PriceRegionOrgValueAddEntity priceRegionOrgAirEntity = new PriceRegionOrgValueAddEntity();
				priceRegionOrgAirEntity.setIncludeOrgCode(model.getIncludeOrgCode());	
				priceRegionOrgAirEntity.setRegionNature(regionNature);
				List<PriceRegionOrgValueAddEntity> priceRegionOrgAirEntityList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
				if(CollectionUtils.isNotEmpty(priceRegionOrgAirEntityList)){
					 for(int loop=0;loop<priceRegionOrgAirEntityList.size();loop++){
						 PriceRegionOrgValueAddEntity object = priceRegionOrgAirEntityList.get(loop);
						 if(begintime.getTime() <= object.getEndTime().getTime()){
							 PriceRegionValueAddEntity priceRegionAirEntity = regionValueAddDao.searchRegionByID(object.getPriceRegionId(), regionEntity.getRegionNature());
							 String message = "在"+priceRegionAirEntity.getRegionName()+"区域下已经存在部门"+object.getIncludeOrgCode();//在哪个区域下已经存在该部门
							 throw new RegionException(message,message);
						 }		 
					 }
					 //没有抛异常则新增
					 regionValueAddDao.addRegionOrg(model);
				}
				else{
					regionValueAddDao.addRegionOrg(model);
				}
				
			}
		}
		//处理其他数据
		if (updatePriceRegionOrgAirEntityList != null) {
			for (PriceRegionOrgValueAddEntity model : updatePriceRegionOrgAirEntityList) {
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setRegionNature(regionEntity.getRegionNature());
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				regionValueAddDao.updateRegionOrg(model);
			}
		}
	}

	/**
	 * .
	 * <p>
	 * 检测区域名称<br/>
	 * 方法名：isTheSameRegionName
	 * </p>
	 * 
	 * @param PriceRegionAirEntity
	 *            查询条件
	 @param isUpdate
	 *            是否是修改
	 * @return Boolean
	 * @author 张斌
	 * @时间 2012-11-23
	 * @since JDK1.6
	 */
	public boolean isTheSameRegionName(PriceRegionValueAddEntity priceRegionAirEntity, boolean isUpdate) {
		boolean flag = false;
		List<PriceRegionValueAddEntity> priceRegionAirEntityList = regionValueAddDao.searchRegionByName(priceRegionAirEntity.getRegionName(),
				priceRegionAirEntity.getRegionNature());
		if (priceRegionAirEntityList != null && priceRegionAirEntityList.size() > 1) {
			flag = true;
		} else if (priceRegionAirEntityList != null && priceRegionAirEntityList.size() == 1) {
			// 如果修改还得判断是否ID相同
			if (isUpdate) {
				if (StringUtils.equals(priceRegionAirEntityList.get(0).getId(), priceRegionAirEntity.getId())) {
					flag = false;
				} else {
					flag = true;
				}
				// 如果是新增，直接表示有重复
			} else {
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
	/**
	 * .
	 * <p>
	 * 检测区域CODE<br/>
	 * 方法名：isTheSameRegionCode
	 * </p>
	 * 
	 * @param PriceRegionAirEntity
	 *            查询条件
	 * @return Boolean
	 * @author 张斌
	 * @时间 2013-1-9
	 * @since JDK1.6
	 */
	 
	public boolean isTheSameRegionCode(PriceRegionValueAddEntity priceRegionAirEntity) {
		List<PriceRegionValueAddEntity> priceRegionAirEntityList = regionValueAddDao.searchRegionByCondition(priceRegionAirEntity,NumberConstants.ZERO,Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(priceRegionAirEntityList)) {
			return false;
		} 
		return true;
	}
	/**
	 * 
	 * <p>
	 * (根据始发部门寻找对应的时效区域 查询规则： 1.查询区域与部门表中是否存在假如不存在请看2
	 * 2.查询基础数据行政区域与部门关系表获得具体省份
	 * 、市、区县，再依次按照区县->市->省份向上的方式到时效区域表里面寻找逻辑区域ID,没有找到返回Null)
	 * </p>
	 * 
	 * @author 岳洪杰
	 * @date 2012-10-12 下午7:27:25
	 * @param deptNo
	 *            组织机构code
	 * @param billDate
	 *            开单日期
	 * @param productCode
	 *            产品code
	 * @param regionNature
	 *            区域性质
	 * @return
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveRegionOrgService#findRegionOrgByDeptNo(java.lang.String)
	 */
	@Override
	public String findRegionOrgByDeptNo(String orgCode, Date billDate,
			String productCode, String regionNature) {

		if (StringUtil.isEmpty(orgCode)) {
			return null;
		}
		//   1.查询区域与部门表中是否存在，根据部门编码，时间，查询唯一的区域信息。
		PriceRegionOrgValueAddEntity priceRegionOrgAirEntity = null;
		
		if(SqlUtil.loadCache){//客户端不读缓存
			try {
				priceRegionOrgAirEntity = priceRegionOrgValueAddCacheDeal.getPriceRegionOrgAirByCache(orgCode, billDate);
			} catch (Exception e) {
				log.info("无法获取空运区域与部门关系缓存", e);
			}
		}
		
		if(priceRegionOrgAirEntity != null) {
			return priceRegionOrgAirEntity.getPriceRegionId();
		}else {
			priceRegionOrgAirEntity = new PriceRegionOrgValueAddEntity();
			priceRegionOrgAirEntity.setIncludeOrgCode(orgCode);
			priceRegionOrgAirEntity.setActive(FossConstants.ACTIVE);
			priceRegionOrgAirEntity.setBillDate(billDate);
			List<PriceRegionOrgValueAddEntity> resultList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
			if (CollectionUtils.isNotEmpty(resultList)) {
				PriceRegionOrgValueAddEntity object = resultList.get(0);
				return object.getPriceRegionId();
			}
		}

		// 2 ,没找到，按组织所在行政区域匹配     
		String countyCode = null;
		String cityCode = null;
		String provCode = null;
		OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService
				.queryOrgInfoByCodeAndTimeNoAttach(orgCode, billDate);
		
		if (orginfo != null) {
			countyCode = orginfo.getCountyCode();
			cityCode = orginfo.getCityCode();
			provCode = orginfo.getProvCode();
			if(StringUtil.isEmpty(provCode))
			{
			    return null;
			}

		} else {
		    OuterBranchParamsDto dto = new OuterBranchParamsDto();
			dto.setDate(billDate);
			dto.setAgentDeptCode(orgCode);
			List<OuterBranchEntity> entity = vehicleAgencyDeptService
					.queryOuterBranchsSimpleInfo(dto);
			if (CollectionUtils.isNotEmpty(entity)) {
				OuterBranchEntity obkect = entity.get(0);
				countyCode = obkect.getCountyCode();
				cityCode = obkect.getCityCode();
				provCode = obkect.getProvCode();
				if(StringUtil.isEmpty(provCode))
				{
				    return null;
				}
			}else
			{
			    return null;
			}
		}

		//根据返回的行政区域信息，查询逻辑区域
		//foss 343617 赵一清 20160919 sonar问题修复
		String key = "key";

		if(StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		
		if(StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		
		log.info("key str>>"+key);
		List<PriceRegionValueAddEntity> regionlist = null;
		if(SqlUtil.loadCache){//客户端不读缓存 服务器端才读取
			try {
				regionlist = priceRegionValueAddCacheDeal.getPriceRegionAirByCache(key, billDate);
			} catch (Exception e) {
				log.info("无法获取空运区域缓存", e);
			}
		}
		if(CollectionUtils.isEmpty(regionlist)) {
			PriceRegionValueAddEntity regionEntity = new PriceRegionValueAddEntity();
			regionEntity.setProCode(provCode);
			regionEntity.setCityCode(cityCode);
			regionEntity.setCountyCode(countyCode);
			regionEntity.setBillDate(billDate);
			regionEntity.setActive(FossConstants.ACTIVE);
			regionlist = regionValueAddDao
					.searchRegionByDistrictNew(regionEntity);
		}
		
		//过滤最符合条件的数据
		PriceRegionValueAddEntity entity = filterBestMapEntity(regionlist,provCode,cityCode,countyCode);
		if (entity != null) {
			return entity.getId();
		}
		return null;
	}
	/**
	 * <p>
	 * 根据行政区域查找逻辑区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-2 下午10:52:58
	 * 
	 * @param code
	 * 
	 * @param billDate
	 * 
	 * @param regionNature
	 * 
	 * @return
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#findRegionIdByDistrictCode(java.lang.String,
	 *      java.util.Date, java.lang.String)
	 */
	public String findRegionIdByDistrictCode(String code, Date billDate,
			String regionNature) {

		if (StringUtil.isEmpty(code)) {
			return null;
		}
		// step 1 : 查询逻辑区域上，符合条件的数据，返回
		PriceRegionValueAddEntity regionEntity = new PriceRegionValueAddEntity();
		regionEntity.setProCode(code);
		regionEntity.setCityCode(code);
		regionEntity.setCountyCode(code);
		regionEntity.setBillDate(billDate);
		regionEntity.setRegionNature(regionNature);
		regionEntity.setActive(FossConstants.ACTIVE);
		List<PriceRegionValueAddEntity> regionlist = regionValueAddDao
				.searchRegionByDistrict(regionEntity);
		PriceRegionValueAddEntity entity = filterBestMapEntity(regionlist,code,code,code);
		if (entity != null) {
			return entity.getId();
		}
		// step 2
		// ：如果第一步没找到，查询组织机构，查找该行政区域下所有的组织。
		//然后循环组织信息，查询该组织属于的区域，直到找到逻辑区域后返回。如果最后都没找到，则返回空。
		List<OrgAdministrativeInfoEntity> orglist = orgAdministrativeInfoService.queryOrgAdministrativeInfoByDistrictCode(code, billDate);
		if (CollectionUtils.isNotEmpty(orglist)) {
			for (int loop = 0; loop < orglist.size(); loop++) {
				OrgAdministrativeInfoEntity orgentity = orglist.get(loop);
				PriceRegionOrgValueAddEntity priceRegionOrgAirEntity = new PriceRegionOrgValueAddEntity();
				priceRegionOrgAirEntity.setIncludeOrgCode(orgentity.getCode());
				priceRegionOrgAirEntity.setRegionNature(regionNature);
				priceRegionOrgAirEntity.setActive(FossConstants.ACTIVE);
				priceRegionOrgAirEntity.setBillDate(billDate);
				List<PriceRegionOrgValueAddEntity> resultList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
				if (resultList != null && resultList.size() > 0) {
					//找到后返回
					PriceRegionOrgValueAddEntity object = resultList.get(0);
					return object.getPriceRegionId();
				}

			}
		}
		// step 2
		// ：如 查询外部代理网点，查找该行政区域下所有的外部网点。
		//然后外部网点信息，查询该外部网点属于的区域，直到找到逻辑区域后返回。如果最后都没找到，则返回空。
		List<OuterBranchEntity> outerorglist = vehicleAgencyDeptService.queryOuterBranchsByDistrictCode(code, billDate);
		if (CollectionUtils.isNotEmpty(outerorglist)) {
			for (int loop = 0; loop < outerorglist.size(); loop++) {
				OuterBranchEntity outorgentity = outerorglist.get(loop);
				PriceRegionOrgValueAddEntity priceRegionOrgAirEntity = new PriceRegionOrgValueAddEntity();
				priceRegionOrgAirEntity.setIncludeOrgCode(outorgentity.getAgentDeptCode());
				priceRegionOrgAirEntity.setRegionNature(regionNature);
				priceRegionOrgAirEntity.setActive(FossConstants.ACTIVE);
				priceRegionOrgAirEntity.setBillDate(billDate);
				List<PriceRegionOrgValueAddEntity> resultList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgAirEntity);
				if (resultList != null && resultList.size() > 0) {
					//找到后返回
					PriceRegionOrgValueAddEntity object = resultList.get(0);
					return object.getPriceRegionId();
				} 
			}
		} 
		return null;
	}

	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-4 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionValueAddEntity filterBestMapEntity(
			List<PriceRegionValueAddEntity> regionlist,String provCode,String cityCode,String countryCode) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionValueAddEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		List<PriceRegionValueAddEntity> countryRegionlist=new ArrayList<PriceRegionValueAddEntity>();
		List<PriceRegionValueAddEntity> cityRegionlist=new ArrayList<PriceRegionValueAddEntity>();
		List<PriceRegionValueAddEntity> provRegionlist=new ArrayList<PriceRegionValueAddEntity>();
		
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 0; j < regionlist.size(); j++) { 
			PriceRegionValueAddEntity temp = regionlist.get(j);
			if(StringUtils.equalsIgnoreCase(temp.getCountyCode(), countryCode))
			{
			    countryRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getCityCode(), cityCode))
			{
			    cityRegionlist.add(temp);
			}
			if(StringUtils.equalsIgnoreCase(temp.getProCode(), provCode))
			{
			    provRegionlist.add(temp);
			}
		}
		if (CollectionUtils.isNotEmpty(countryRegionlist)){
		    return filterBestMapEntity(countryRegionlist);
		}
		if (CollectionUtils.isNotEmpty(cityRegionlist)){
		    return filterBestMapEntity(cityRegionlist);
		}
		
//		if (CollectionUtils.isNotEmpty(provRegionlist)){
//		    return filterBestMapEntity(provRegionlist);
//		}
//		return filterBestMapEntity(regionlist);
		return null;
	}

	/**
	 * <p>
	 * 过滤最匹配的区域
	 * </p>
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-11-4 下午2:09:09
	 * 
	 * @param regionlist
	 * 
	 * @return
	 * 
	 * @see
	 */
	private PriceRegionValueAddEntity filterBestMapEntity(
			List<PriceRegionValueAddEntity> regionlist) {
		if (CollectionUtils.isEmpty(regionlist)) {
			return null;
		}
		PriceRegionValueAddEntity result = regionlist.get(0);
		if (regionlist.size() == 1) {
			return result;
		}
		//根据逻辑区域上的行政区域信息，返回优先级高的数据。如果逻辑区域映射的越小的地方，级别越高
		for (int j = 1; j < regionlist.size(); j++) {
			PriceRegionValueAddEntity temp = regionlist.get(j);
			if (temp.getPriority() < result.getPriority()) {
			    //找到后替换
				result = temp;
			}

		}
		return result;
	}
	/** 
	 * 
	 * 根据ID，查询区域 信息
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-25 下午2:31:35
	 * 
	 * @param id
	 * 
	 * @param regionNature
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService#searchRegionByID(java.lang.String, java.lang.String)
	 */
	@Override
	public PriceRegionValueAddEntity searchRegionByID(String id,
			String regionNature) {
		if(StringUtils.isEmpty(id)){
			return new PriceRegionValueAddEntity();
		}
		PriceRegionValueAddEntity model = regionValueAddDao.searchRegionByID(id, regionNature);
		if(PricingConstants.PRESCRIPTION_REGION.equals(regionNature)){
		    model.setRegionNature(PricingConstants.PRESCRIPTION_REGION_NAME);
		}else if(PricingConstants.PRICING_REGION.equals(regionNature)){
		    model.setRegionNature(PricingConstants.PRICING_REGION_NAME);
		}
		if(model != null) {
			//判断国家CODE不为空
			if(StringUtil.isNotBlank(model.getNationCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getNationCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setNationName(administrativeRegionsEntity.getName());
			}
			//判断省份CODE不为空
			if(StringUtil.isNotBlank(model.getProCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getProCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setProName(administrativeRegionsEntity.getName());
			}
			//判断城市CODE不为空
			if(StringUtil.isNotBlank(model.getCityCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getCityCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setCityName(administrativeRegionsEntity.getName());
			}
			//判断区县CODE不为空
			if(StringUtil.isNotBlank(model.getCountyCode())){
				AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getCountyCode());
				if(null == administrativeRegionsEntity){
				    return model;
				}
				model.setCountyName(administrativeRegionsEntity.getName());
			}
		}
		return model;
	}

	/**
	 * 
	 * @Description: 根据时效区域名称查询时效区域
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-12 上午10:48:50
	 * 
	 * @param regionName
	 * 
	 * @param regionNature
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public List<PriceRegionValueAddEntity> searchRegionByName(String regionName,
			String regionNature) {
		return regionValueAddDao.searchRegionByName(regionName, regionNature);
	}
	/**
	 * 
	 * 根据区域名称批量查找区域信息
	 * 
	 * @author zhangdongping
	 * 
	 * @date 2012-12-22 下午8:26:15
	 * 
	 * @param names 区域名称List
	 * 
	 * @param regionNature 区域性质
	 * 
	 * @param billDate 开单日期
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public Map<String, PriceRegionValueAddEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		return regionValueAddDao.findRegionByNames(names, regionNature, billDate);
	}
	/**
	 * 
	 * @Description: 刷新价格区域缓存
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-2-22 下午1:59:04
	 * 
	 * @param provinceCode
	 * 
	 * @param cityCode
	 * 
	 * @param countyCode
	 * 
	 * @version V1.0
	 */
	@Override
	public void refreshPriceRegionValueAddCache(String provinceCode, String cityCode, String countyCode) {
		String key = "";
		if(StringUtils.isEmpty(provinceCode)) {
			key = "key";
		} else {
			key = provinceCode;
		}
		if(StringUtils.isEmpty(cityCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + cityCode;
		}
		if(StringUtils.isEmpty(countyCode)) {
			key = key + "#key";
		} else {
			key = key + "#" + countyCode;
		}
		try {
			priceRegionValueAddCacheDeal.getPriceRegionValueAddCache().invalid(key);
		} catch (Exception e) {
			log.info("无法刷新空运价格区域缓存数据",e);
		}
	}
	/**
	 * 
	 * @Description: 刷新价格区域与组织缓存
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-2-22 下午1:59:46
	 * 
	 * @param deptNo
	 * 
	 * @version V1.0
	 */
	@Override
	public void refreshPriceRegionOrgValueAddCache(String deptNo) {
		if(StringUtil.isNotBlank(deptNo)) {
			try {
				priceRegionOrgValueAddCacheDeal.getPriceRegionOrgValueAddCache().invalid(deptNo);
			} catch (Exception e) {
				log.info("无法刷新空运价格区域与部门缓存数据",e);
			}
		}
	}
	/**
	 * 
	 * @Description: 立即中止
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-21 下午2:09:39
	 * 
	 * @param regionId
	 * 
	 * @param regionNature
	 * 
	 * @param date
	 * 
	 * @version V1.0
	 */
	@Override
	@Transactional
	public void immedietelyStopRegionValueAdd(String regionId, String regionNature, Date endTime) {
		//查询需要更新的记录
		PriceRegionValueAddEntity model = regionValueAddDao.searchRegionByID(regionId, regionNature);
		// 获取当前登录用户
		String userCode = getCurrentUserCode();
		// 获取当前登录用户所在部门
		String orgCode = getCurrentOrgCode();
		//获取版本号
		Long versionNoOrg = new Date().getTime();
		//设置终止时间
		model.setEndTime(endTime);
		//补充更新信息
		model.setVersionNo(versionNoOrg);
		model.setModifyOrgCode(orgCode);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
		model.setModifyUser(userCode);
		model.setRegionNature(regionNature);
		//执行更新操作
		regionValueAddDao.updateRegion(model);
		//查询区域与部门关系相关记录
		PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntity = new PriceRegionOrgValueAddEntity();
		priceRegionOrgValueAddEntity.setPriceRegionId(model.getId());
		priceRegionOrgValueAddEntity.setRegionNature(regionNature);
		List<PriceRegionOrgValueAddEntity> priceRegionOrgValueAddEntities = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgValueAddEntity);
		if (CollectionUtils.isNotEmpty(priceRegionOrgValueAddEntities)) {
			for (PriceRegionOrgValueAddEntity priceRegionOrgValueAddModel : priceRegionOrgValueAddEntities) {
				priceRegionOrgValueAddModel.setVersionNo(versionNoOrg);
				priceRegionOrgValueAddModel.setRegionNature(regionNature);
				//如果原结束时间晚于此次更新时间，则执行更新
				//如果原结束时间早于此次更新时间，则不执行更新
				if(endTime.getTime() < priceRegionOrgValueAddModel.getEndTime().getTime()) {
					priceRegionOrgValueAddModel.setEndTime(endTime);
				} else {
					continue;
				}
				priceRegionOrgValueAddModel.setModifyOrgCode(orgCode);
				priceRegionOrgValueAddModel.setModifyUser(userCode);
				regionValueAddDao.updateRegionOrg(priceRegionOrgValueAddModel);
			}
		}
		/**
		 * 行政区域与区域关系
		 */
		if(StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, model.getRegionType())){
			if(StringUtils.isNotEmpty(model.getCountyCode())) {
				districtRegionService.addDistrictRegion(model.getCountyCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCountyCode());
			} 
			if(StringUtils.isNotEmpty(model.getCityCode())) {
				districtRegionService.addDistrictRegion(model.getCityCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCityCode());
			}
		} else if(PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(model.getRegionType())) {
			if(CollectionUtils.isNotEmpty(priceRegionOrgValueAddEntities)) {
				List<String> cityList = new ArrayList<String>();
				List<String> countyList = new ArrayList<String>();
				for (PriceRegionOrgValueAddEntity orgValueAddEntity : priceRegionOrgValueAddEntities) {
					String deptCode = orgValueAddEntity.getIncludeOrgCode();
					OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode, new Date());
					if (orginfo != null) {
						String countyCode = orginfo.getCountyCode();
						String cityCode = orginfo.getCityCode();
						if (StringUtil.isNotEmpty(countyCode) && !cityList.contains(countyCode)) {
							countyList.add(countyCode);
						}
						if (StringUtil.isNotEmpty(cityCode) && !cityList.contains(cityCode)) {
							cityList.add(cityCode);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(countyList)) {
					for (String countyCode : countyList) {
						districtRegionService.addDistrictRegion(countyCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(countyCode);
					}
				}
				if(CollectionUtils.isNotEmpty(cityList)) {
					for (String cityCode : cityList) {
						districtRegionService.addDistrictRegion(cityCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(cityCode);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @Description: 
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2013-3-25 上午10:56:07
	 * 
	 * @param regionId
	 * 
	 * @param regionNature
	 * 
	 * @param endTime
	 * 
	 * @version V1.0
	 * 
	 */
	@Override
	@Transactional
	public void immedietelyActiveRegionValueAdd(String regionId, String regionNature, Date beginTime) {
		PriceRegionValueAddEntity model = regionValueAddDao.searchRegionByID(regionId, regionNature);
		// 获取当前登录用户
		String userCode = getCurrentUserCode();
		// 获取当前登录用户所在部门
		String orgCode = getCurrentOrgCode();
		//获取版本号
		Long versionNoOrg = new Date().getTime();
		//设置起始时间
		model.setBeginTime(beginTime);
		//补充更新信息
		model.setVersionNo(versionNoOrg);
		model.setModifyOrgCode(orgCode);
		model.setModifyUser(userCode);
		model.setActive(FossConstants.ACTIVE);
		model.setRegionNature(regionNature);
		regionValueAddDao.updateRegion(model);
		
		PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntity = new PriceRegionOrgValueAddEntity();
		priceRegionOrgValueAddEntity.setPriceRegionId(model.getId());
		priceRegionOrgValueAddEntity.setRegionNature(regionNature);
		List<PriceRegionOrgValueAddEntity>  priceRegionOrgValueAddEntityList = regionValueAddDao.searchRegionOrgByCondition(priceRegionOrgValueAddEntity);
		if(CollectionUtils.isNotEmpty(priceRegionOrgValueAddEntityList)){
			for(PriceRegionOrgValueAddEntity priceRegioOrgnModel:priceRegionOrgValueAddEntityList){
				priceRegioOrgnModel.setVersionNo(versionNoOrg);
				//修改状态
				priceRegioOrgnModel.setActive(FossConstants.ACTIVE);
				priceRegioOrgnModel.setRegionNature(regionNature);
				priceRegioOrgnModel.setBeginTime(beginTime);
				priceRegioOrgnModel.setModifyOrgCode(orgCode);
				priceRegioOrgnModel.setModifyUser(userCode);
				regionValueAddDao.updateRegionOrg(priceRegioOrgnModel);
			}
		}
		/**
		 * 行政区域与区域关系
		 */
		if(StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, model.getRegionType())){
			if(StringUtils.isNotEmpty(model.getCountyCode())) {
				districtRegionService.addDistrictRegion(model.getCountyCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCountyCode());
			} 
			if(StringUtils.isNotEmpty(model.getCityCode())) {
				districtRegionService.addDistrictRegion(model.getCityCode());
				districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCityCode());
			}
		} else if(PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(model.getRegionType())) {
			if(CollectionUtils.isNotEmpty(priceRegionOrgValueAddEntityList)) {
				List<String> cityList = new ArrayList<String>();
				List<String> countyList = new ArrayList<String>();
				for (PriceRegionOrgValueAddEntity orgValueAddEntity : priceRegionOrgValueAddEntityList) {
					String deptCode = orgValueAddEntity.getIncludeOrgCode();
					OrgAdministrativeInfoEntity orginfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(deptCode, new Date());
					if (orginfo != null) {
						String countyCode = orginfo.getCountyCode();
						String cityCode = orginfo.getCityCode();
						if (StringUtil.isNotEmpty(countyCode) && !cityList.contains(countyCode)) {
							countyList.add(countyCode);
						}
						if (StringUtil.isNotEmpty(cityCode) && !cityList.contains(cityCode)) {
							cityList.add(cityCode);
						}
					}
				}
				if(CollectionUtils.isNotEmpty(countyList)) {
					for (String countyCode : countyList) {
						districtRegionService.addDistrictRegion(countyCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(countyCode);
					}
				}
				if(CollectionUtils.isNotEmpty(cityList)) {
					for (String cityCode : cityList) {
						districtRegionService.addDistrictRegion(cityCode);
						districtRegionCacheDeal.getDistrictRegionCache().invalid(cityCode);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 获得编号序列
	 * @author 043258-foss-zhaobin
	 * @date 2013-8-15 上午8:19:13
	 */
	@Override
	public String querySequence() {
		return regionValueAddDao.querySequence();
	}
	
	/**
	 * 
	 * @Description: 根据区域ID获取其下所有的营业部
	 * @author 043258-foss-zhaobin
	 * @date 2013-4-22 下午2:16:14
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	public List<String> searchRegionOrgCodeByRegionId(String deptRegionId) {
		List<String> list = null;
		List<PriceRegionOrgValueAddEntity> priceRegionOrgValueAddEntities = regionValueAddDao.searchRegionOrgByRegionId(deptRegionId);
		if(CollectionUtils.isNotEmpty(priceRegionOrgValueAddEntities)) {
			list = new ArrayList<String>();
			for (PriceRegionOrgValueAddEntity priceRegionOrgValueAddEntity : priceRegionOrgValueAddEntities) {
				list.add(priceRegionOrgValueAddEntity.getIncludeOrgCode());
			}
		}
		return list;
	}
	/**
	 * 
	 * @Description: 获得当前部门值
	 * 
	 * Company:IBM
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	/**
	 * 
	 * @Description: 获得当前人
	 * 
	 * Company:IBM
	 * 
	 * @author 043258-foss-zhaobin
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 * 
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		if(currentUser == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		return currentUser.getEmployee().getEmpCode();
	}
	/**
	 * 设置 组织机构接口.
	 *
	 * @param orgAdministrativeInfoService the new 组织机构接口
	 */
	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 网点SERVICe.
	 *
	 * @param vehicleAgencyDeptService the new 网点SERVICe
	 */
	public void setVehicleAgencyDeptService(IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}

	/**
	 * 设置 行政区域SERVICE.
	 *
	 * @param administrativeRegionsService the new 行政区域SERVICE
	 */
	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	/**
	 * 设置 职员service.
	 *
	 * @param employeeService the new 职员service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setRegionValueAddDao(IRegionValueAddDao regionValueAddDao) {
		this.regionValueAddDao = regionValueAddDao;
	}

	public void setPriceRegionValueAddCacheDeal(
			PriceRegionValueAddCacheDeal priceRegionValueAddCacheDeal) {
		this.priceRegionValueAddCacheDeal = priceRegionValueAddCacheDeal;
	}

	public void setPriceRegionOrgValueAddCacheDeal(
			PriceRegionOrgValueAddCacheDeal priceRegionOrgValueAddCacheDeal) {
		this.priceRegionOrgValueAddCacheDeal = priceRegionOrgValueAddCacheDeal;
	}

	public void setDistrictRegionCacheDeal(DistrictRegionCacheDeal districtRegionCacheDeal) {
		this.districtRegionCacheDeal = districtRegionCacheDeal;
	}
	public void setDistrictRegionService(IDistrictRegionService districtRegionService) {
		this.districtRegionService = districtRegionService;
	}

}