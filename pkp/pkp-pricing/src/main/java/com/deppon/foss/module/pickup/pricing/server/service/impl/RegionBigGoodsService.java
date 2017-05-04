package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionBigGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionBigGoodsService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionBigGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.RegionException;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
//import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionBigGoodsCacheDeal;
//import com.deppon.foss.module.pickup.pricing.server.cache.PriceRegionOrgBigGoodsCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * ClassName: RegionBigGoodsService <br/>
 * Function: 精准大票价格区域服务. <br/>
 * date: 2014-6-13 下午7:15:29 <br/>
 *
 * @author 157229-zxy
 * @version 
 * @since JDK 1.6
 */
public class RegionBigGoodsService implements IRegionBigGoodsService{
	
	/**
	 * 组织机构接口
	 */
	@Inject
	IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
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
	private IRegionBigGoodsDao regionBigGoodsDao;
	
	/**
	 * 网点SERVICe
	 */
	@Inject
	IVehicleAgencyDeptService vehicleAgencyDeptService;
	
	/**
	 * 精准大票价格区域 缓存处理
	 */
//	private PriceRegionBigGoodsCacheDeal priceRegionBigGoodsCacheDeal;

	/**
	 * 精准大票价格区域与部门 缓存处理
	 */
//	private PriceRegionOrgBigGoodsCacheDeal priceRegionOrgBigGoodsCacheDeal;
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
	

	@Override
	public List<PriceRegionBigGoodsEntity> searchRegionByCondition(
			PriceRegionBigGoodsEntity regionEntity, int start, int limit) {
		   //设置条件
				if (PricingConstants.ALL.equals(regionEntity.getActive())) {
					regionEntity.setActive(null);
				}
				List<PriceRegionBigGoodsEntity> priceRegionBgEntityList = regionBigGoodsDao
						.searchRegionByCondition(regionEntity, start, limit);
				if(CollectionUtils.isNotEmpty(priceRegionBgEntityList)){
					for (PriceRegionBigGoodsEntity model : priceRegionBgEntityList) {
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
				return priceRegionBgEntityList;
	}

	@Override
	public Long countRegionByCondition(PriceRegionBigGoodsEntity regionEntity) {
		if (PricingConstants.ALL.equals(regionEntity.getActive())) {
			regionEntity.setActive(null);
		}
		return regionBigGoodsDao.countRegionByCondition(regionEntity);
	}

	@Override
	public void immedietelyActiveRegion(String regionId, String regionNature,
			Date beginTime) {
		//查询需要更新的记录
		PriceRegionBigGoodsEntity model = regionBigGoodsDao.searchRegionByID(regionId, regionNature);
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
		//执行更新操作
		regionBigGoodsDao.updateRegion(model);
		//查询区域与部门关系相关记录
		PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnBigGoodsEntity();
		priceRegioOrgnEntity.setPriceRegionId(model.getId());
		priceRegioOrgnEntity.setRegionNature(regionNature);
		List<PriceRegioOrgnBigGoodsEntity>  priceRegioOrgnEntityList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
			for(PriceRegioOrgnBigGoodsEntity priceRegioOrgnModel:priceRegioOrgnEntityList){
				priceRegioOrgnModel.setVersionNo(versionNoOrg);
				//修改状态
				priceRegioOrgnModel.setActive(FossConstants.ACTIVE);
				//补充更新信息
				priceRegioOrgnModel.setRegionNature(regionNature);
				priceRegioOrgnModel.setBeginTime(beginTime);
				priceRegioOrgnModel.setModifyOrgCode(orgCode);
				priceRegioOrgnModel.setModifyUser(userCode);
				regionBigGoodsDao.updateRegionOrg(priceRegioOrgnModel);
			}
		}
		/**
		 * 维护行政区域与区域关系
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
			if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
				List<String> cityList = new ArrayList<String>();
				List<String> countyList = new ArrayList<String>();
				for (PriceRegioOrgnBigGoodsEntity regioOrgnEntity : priceRegioOrgnEntityList) {
					String deptCode = regioOrgnEntity.getIncludeOrgCode();
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

	@Override
	public void deleteRegion(List<String> regionIds, String regionNature) {
		regionBigGoodsDao.deleteRegion(regionIds, regionNature);
		regionBigGoodsDao.deleteRegionOrg(regionIds, regionNature);
	}

	@Override
	public void addRegion(PriceRegionBigGoodsEntity regionEntity,
			List<PriceRegioOrgnBigGoodsEntity> addPriceRegioOrgnEntityList) {
		String regionId = UUIDUtils.getUUID();
		regionEntity.setId(regionId);// 设置ID
		//转换时间(时间得失明天开始)
//		Date begintime=DateUtils.convert(DateUtils.convert(new Date(new Date().getTime()+PricingConstants.ONE_DAY_MILLISECOND),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
		Date begintime = new Date();
		String regionNature = regionEntity.getRegionNature();
		//是否激活
		String active =regionEntity.getActive();
		//查看是否存在名称重复的区域
		if(FossConstants.YES.equals(active)){
			List<PriceRegionBigGoodsEntity> bigGoodsRegions = regionBigGoodsDao.findRegionByName(regionEntity);
			if(CollectionUtils.isNotEmpty(bigGoodsRegions)){
				throw new PricingCommonException("已存在相同激活的区域名称:"+regionEntity.getRegionName(),"");
			}
		}
		
		Long versionNo = new Date().getTime();
		regionEntity.setVersionNo(versionNo);
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
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
		boolean isTheSameRegionName = this.isTheSameRegionName(regionEntity,
				false);
		if (isTheSameRegionName) {
			throw new RegionException(RegionException.REGION_NAME_SAME,
					RegionException.REGION_NAME_SAME);
		}else{
			//did nothing
		}
		PriceRegionBigGoodsEntity checkEntity = new PriceRegionBigGoodsEntity();
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
		if(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())
		  && FossConstants.YES.equals(active)){
			//校验行政区域
			checkRegionOrganizationType(regionEntity);
		}
		//添加数据
		regionBigGoodsDao.addRegion(regionEntity);
		//处理该区域包含的组织
		if (addPriceRegioOrgnEntityList != null) {
			for (PriceRegioOrgnBigGoodsEntity model : addPriceRegioOrgnEntityList) {
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
				PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnBigGoodsEntity();
				priceRegioOrgnEntity.setIncludeOrgCode(model.getIncludeOrgCode());	
				priceRegioOrgnEntity.setRegionNature(regionNature);
				List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntityList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
				if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
					 for(int loop=0;loop<priceRegioOrgnEntityList.size();loop++){
						 PriceRegioOrgnBigGoodsEntity object = priceRegioOrgnEntityList.get(loop);
						 if(begintime.getTime() <= object.getEndTime().getTime()){
							 PriceRegionBigGoodsEntity priceRegionEntity = regionBigGoodsDao.searchRegionByID(object.getPriceRegionId(),regionNature);
							 String message = "在"+priceRegionEntity.getRegionName()+"区域下已经存在部门"+object.getIncludeOrgCode();
							 throw new RegionException(message,message);
						 }		 
					 }
					//没有抛异常则新增
					 regionBigGoodsDao.addRegionOrg(model);
				}else{
					 regionBigGoodsDao.addRegionOrg(model);
				}
			}
		}
		
	}

	@Override
	public void updateRegion(PriceRegionBigGoodsEntity regionEntity,
			List<PriceRegioOrgnBigGoodsEntity> addPriceRegioOrgnEntityList,
			List<PriceRegioOrgnBigGoodsEntity> updatePriceRegioOrgnEntityList) {
		Long versionNo = new Date().getTime();
		regionEntity.setVersionNo(versionNo);
		String regionNature = regionEntity.getRegionNature();
		Date begintime = new Date();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		String userCode = user.getEmployee().getEmpCode();
		String orgCode = user.getEmployee().getDepartment().getCode();
		regionEntity.setModifyOrgCode(orgCode);
		regionEntity.setModifyUser(userCode);
		// 判断名称是否重复
		boolean isTheSameRegionName = this.isTheSameRegionName(regionEntity,
				true);
		if (isTheSameRegionName) {
			throw new RegionException(RegionException.REGION_NAME_SAME,
					RegionException.REGION_NAME_SAME);
		}
		if(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())){
			//校验行政区域
			if(FossConstants.YES.equals(regionEntity.getActive())){
				checkRegionOrganizationType(regionEntity);
			}
		}
		//更新数据
		regionBigGoodsDao.updateRegion(regionEntity);
		//处理包含组织
		if (addPriceRegioOrgnEntityList != null) {
			for (PriceRegioOrgnBigGoodsEntity model : addPriceRegioOrgnEntityList) {
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
				PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnBigGoodsEntity();
				priceRegioOrgnEntity.setIncludeOrgCode(model.getIncludeOrgCode());	
				priceRegioOrgnEntity.setRegionNature(regionNature);
				List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntityList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
				if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)){
					 for(int loop=0;loop<priceRegioOrgnEntityList.size();loop++){
						 PriceRegioOrgnBigGoodsEntity object = priceRegioOrgnEntityList.get(loop);
						 if(begintime.getTime() <= object.getEndTime().getTime()){
							 PriceRegionBigGoodsEntity priceRegionEntity = regionBigGoodsDao.searchRegionByID(object.getPriceRegionId(), regionEntity.getRegionNature());
							//在哪个区域下已经存在该部门
							 String message = "在"+priceRegionEntity.getRegionName()+"区域下已经存在部门"+object.getIncludeOrgCode();
							 throw new RegionException(message,message);
						 }		 
					 }
					//没有抛异常则新增
					 regionBigGoodsDao.addRegionOrg(model);
				}
				else{
					regionBigGoodsDao.addRegionOrg(model);
				}
				
			}
		}
		//处理其他数据
		if (updatePriceRegioOrgnEntityList != null) {
			for (PriceRegioOrgnBigGoodsEntity model : updatePriceRegioOrgnEntityList) {
				Long versionNoOrg = new Date().getTime();
				model.setVersionNo(versionNoOrg);
				model.setRegionNature(regionEntity.getRegionNature());
				model.setModifyOrgCode(orgCode);
				model.setModifyUser(userCode);
				regionBigGoodsDao.updateRegionOrg(model);
			}
		}
	}

	@Override
	public PriceRegionBigGoodsEntity searchRegionByID(String id,
			String regionNature) {
		if(StringUtils.isEmpty(id)){
			return new PriceRegionBigGoodsEntity();
		}
		PriceRegionBigGoodsEntity model = regionBigGoodsDao.searchRegionByID(id, regionNature);
		if(model != null) {
			if(PricingConstants.PRESCRIPTION_REGION.equals(regionNature)){
			    model.setRegionNature(PricingConstants.PRESCRIPTION_REGION_NAME);
			}else if(PricingConstants.PRICING_REGION.equals(regionNature)){
			    model.setRegionNature(PricingConstants.PRICING_REGION_NAME);
			}
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

	@Override
	public void immedietelyStopRegion(String regionId, String regionNature,
			Date endTime) {
		//查询需要更新的记录
		PriceRegionBigGoodsEntity model = regionBigGoodsDao.searchRegionByID(regionId, regionNature);
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
		regionBigGoodsDao.updateRegion(model);
		//查询区域与部门关系相关记录
		PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnBigGoodsEntity();
		priceRegioOrgnEntity.setPriceRegionId(model.getId());
		priceRegioOrgnEntity.setRegionNature(regionNature);
		List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntityList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
		if (CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
			for (PriceRegioOrgnBigGoodsEntity priceRegioOrgnModel : priceRegioOrgnEntityList) {
				priceRegioOrgnModel.setVersionNo(versionNoOrg);
				priceRegioOrgnModel.setRegionNature(regionNature);
				//如果原结束时间晚于此次更新时间，则执行更新
				//如果原结束时间早于此次更新时间，则不执行更新
				if(endTime.getTime() < priceRegioOrgnModel.getEndTime().getTime()) {
					priceRegioOrgnModel.setEndTime(endTime);
				} else {
					continue;
				}
				priceRegioOrgnModel.setModifyOrgCode(orgCode);
				priceRegioOrgnModel.setModifyUser(userCode);
				regionBigGoodsDao.updateRegionOrg(priceRegioOrgnModel);
			}
		}
		/**
		 * 维护行政区域与区域关系
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
			if(CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
				List<String> cityList = new ArrayList<String>();
				List<String> countyList = new ArrayList<String>();
				for (PriceRegioOrgnBigGoodsEntity regioOrgnEntity : priceRegioOrgnEntityList) {
					String deptCode = regioOrgnEntity.getIncludeOrgCode();
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
	 * getCurrentUserCode:获取当前用户信息. <br/>
	 * 
	 * Date:2014-6-13下午7:47:36
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		if(currentUser == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		return currentUser.getEmployee().getEmpCode();
	}
	
	/**
	 * 
	 * getCurrentOrgCode:获取当前机构信息. <br/>
	 * 
	 * Date:2014-6-13下午7:48:11
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	
	/**
	 * 
	 * isTheSameRegionName:检测区域名称. <br/>
	 * 
	 * Date:2014-6-13下午7:53:50
	 * @author 157229-zxy
	 * @param priceRegionEntity
	 * @param isUpdate 是否是修改
	 * @return
	 * @since JDK 1.6
	 */
	public boolean isTheSameRegionName(PriceRegionBigGoodsEntity priceRegionEntity, boolean isUpdate) {
		return false;
	}
	
	/**
	 * 
	 * isTheSameRegionCode:检测区域code. <br/>
	 * 
	 * Date:2014-6-13下午7:55:04
	 * @author 157229-zxy
	 * @param priceRegionEntity
	 * @return
	 * @since JDK 1.6
	 */
	public boolean isTheSameRegionCode(PriceRegionBigGoodsEntity priceRegionEntity) {
		List<PriceRegionBigGoodsEntity> priceRegionEntityList = regionBigGoodsDao
				.searchRegionByCondition(priceRegionEntity,NumberConstants.ZERO,Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(priceRegionEntityList)) {
			return false;
		} 
		return true;
	}
	
	/**
	 * 
	 * checkRegionOrganizationType:检测行政组织区域是否重复. <br/>
	 * Date:2014-6-13下午7:56:21
	 * @author 157229-zxy
	 * @param regionEntity
	 * @since JDK 1.6
	 */
	private void checkRegionOrganizationType(PriceRegionBigGoodsEntity regionEntity){
		String administrativeRegionCodes = regionEntity.getNationCode()+regionEntity.getProCode()+regionEntity.getCityCode()+regionEntity.getCountyCode();
		String regionNature = regionEntity.getRegionNature();
		String id = regionEntity.getId();
		String active = regionEntity.getActive();
		Date billDate= regionEntity.getBeginTime();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("administrativeRegionCodes", administrativeRegionCodes);
		map.put("id", id);
		map.put("billDate", billDate);
		map.put("active", active);
		List<PriceRegionBigGoodsEntity> priceRegionEntityList=regionBigGoodsDao.checkRegionOrganizationType(map, regionNature);
		if(CollectionUtils.isNotEmpty(priceRegionEntityList)){
			 throw new RegionException("该行政组织下已经存在区域！",null);
		}
	}
	
	@Override
	public List<PriceRegioOrgnBigGoodsEntity> searchRegionOrgByCondition(
			PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity) {
		List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntityList = regionBigGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
		List<PriceRegioOrgnBigGoodsEntity> resultlist = new ArrayList<PriceRegioOrgnBigGoodsEntity>();
		for (PriceRegioOrgnBigGoodsEntity model : priceRegioOrgnEntityList) {
			OrgAdministrativeInfoEntity orgAdministrativeInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(model.getIncludeOrgCode());
			if (orgAdministrativeInfo != null && StringUtils.equalsIgnoreCase(orgAdministrativeInfo.getActive(), FossConstants.ACTIVE)) {
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
			} else {
				OuterBranchParamsDto dto = new OuterBranchParamsDto();
				dto.setAgentDeptCode(model.getIncludeOrgCode());
				List<OuterBranchEntity> entity = vehicleAgencyDeptService.queryOuterBranchs(dto);
				if (CollectionUtils.isNotEmpty(entity)) {
					OuterBranchEntity object = entity.get(0);
					if (object != null && StringUtils.equalsIgnoreCase(object.getActive(), FossConstants.ACTIVE)) {
						model.setIncludeOrgName(object.getAgentDeptName());
						model.setCityCode(object.getCityCode());
						model.setCityName(object.getCityName());
						model.setNationCode(object.getCountryRegion());
						model.setNationName(object.getCountryRegionName() == null ? "中国" : object.getCountryRegionName());
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

	public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public IAdministrativeRegionsService getAdministrativeRegionsService() {
		return administrativeRegionsService;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public IRegionBigGoodsDao getRegionBigGoodsDao() {
		return regionBigGoodsDao;
	}

	public void setRegionBigGoodsDao(IRegionBigGoodsDao regionBigGoodsDao) {
		this.regionBigGoodsDao = regionBigGoodsDao;
	}

	public DistrictRegionCacheDeal getDistrictRegionCacheDeal() {
		return districtRegionCacheDeal;
	}

	public void setDistrictRegionCacheDeal(
			DistrictRegionCacheDeal districtRegionCacheDeal) {
		this.districtRegionCacheDeal = districtRegionCacheDeal;
	}

	public IDistrictRegionService getDistrictRegionService() {
		return districtRegionService;
	}

	public void setDistrictRegionService(
			IDistrictRegionService districtRegionService) {
		this.districtRegionService = districtRegionService;
	}

	public IVehicleAgencyDeptService getVehicleAgencyDeptService() {
		return vehicleAgencyDeptService;
	}

	public void setVehicleAgencyDeptService(
			IVehicleAgencyDeptService vehicleAgencyDeptService) {
		this.vehicleAgencyDeptService = vehicleAgencyDeptService;
	}
	/**
	 * 根据区域名称批量查找区域信息
	 * 
	 * @author yangkang
	 * 
	 * @date 2014-6-28 下午8:26:15
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
	public Map<String, PriceRegionBigGoodsEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
		return regionBigGoodsDao.findRegionByNames(names, regionNature, billDate);
	}

	/**
	 * 
	 * @Description: 根据区域ID获取其下所有的营业部
	 * @author FOSSDP-yangkang	
	 * @date 2014-7-11 下午2:15:29
	 * @param deptRegionId
	 * @param regionNature
	 * @return
	 * @version V1.0
	 */
	@Override
	public List<String> searchRegionOrgCodeByRegionId(String deptRegionId, String regionNature) {
		List<String> list = null;
		List<PriceRegioOrgnBigGoodsEntity> priceRegioOrgnEntities = regionBigGoodsDao.searchRegionOrgByRegionId(deptRegionId, regionNature);
		if(CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
			list = new ArrayList<String>();
			for (PriceRegioOrgnBigGoodsEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
				list.add(priceRegioOrgnEntity.getIncludeOrgCode());
			}
		}
		return list;
	}
}
