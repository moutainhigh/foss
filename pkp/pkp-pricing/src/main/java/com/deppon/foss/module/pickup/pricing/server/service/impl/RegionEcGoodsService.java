package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

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
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionEcGoodsDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDistrictRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegioOrgnEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.RegionException;
import com.deppon.foss.module.pickup.pricing.server.cache.DistrictRegionCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * ClassName: RegionEcGoodsService <br/>
 * Function: 精准电商价格区域服务. <br/>
 * date: 2016.6.30<br/>
 *
 * @author 311417 wangfeng
 * @since JDK 1.6
 */
@Transactional
public class RegionEcGoodsService implements IRegionEcGoodsService {


    private static  String endTimeBeforeBeginTimeError = "终止时间不能早于开始时间！";
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
    private IRegionEcGoodsDao regionEcGoodsDao;

    /**
     * 网点SERVICe
     */
    @Inject
    IVehicleAgencyDeptService vehicleAgencyDeptService;

    /**
     * 行政区域与区域关系缓存处理
     */
    @Inject
    private DistrictRegionCacheDeal districtRegionCacheDeal;

    @Inject
    private IDistrictRegionService districtRegionService;


    /**
     *
     * 根据条件查询区域
     *
     * @author 311417 wangfeng
     *
     * @date 2016.07.05
     *
     * @param regionEntity
     *
     * @param start
     *
     * @param limit
     *
     * @return
     */
    @Override
    public List<PriceRegionEcGoodsEntity> searchRegionByCondition(
            PriceRegionEcGoodsEntity regionEntity, int start, int limit) {
        //设置条件
        if (PricingConstants.ALL.equals(regionEntity.getActive())) {
            regionEntity.setActive(null);
        }
        List<PriceRegionEcGoodsEntity> priceRegionEcEntityList = regionEcGoodsDao
                .searchRegionByCondition(regionEntity, start, limit);
        if (CollectionUtils.isNotEmpty(priceRegionEcEntityList)) {
            for (PriceRegionEcGoodsEntity model : priceRegionEcEntityList) {
                model.setRegionNature(regionEntity.getRegionNature());
                if (StringUtil.isNotBlank(model.getModifyUser())) {
                    //查询修改人信息
                    EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(model.getModifyUser());
                    if (employeeEntity != null) {
                        //设置修改人姓名
                        model.setModifyUserName(employeeEntity.getEmpName());
                    }
                }
            }
        }
        //返回结果
        return priceRegionEcEntityList;
    }

    /**
     *
     * 计算条数
     *
     * @author 311417
     *
     * @date 2016.07.06
     *
     * @param regionEntity
     *
     * @return
     */
    @Override
    public Long countRegionByCondition(PriceRegionEcGoodsEntity regionEntity) {
        if (PricingConstants.ALL.equals(regionEntity.getActive())) {
            regionEntity.setActive(null);
        }
        return regionEcGoodsDao.countRegionByCondition(regionEntity);
    }

    /**
     *
     * @Description: 立即激活
     *
     * @author 311417   wangfeng
     *
     * @date 2016.07.005
     *
     * @param regionId
     *
     * @param regionNature
     *
     * @param beginTime
     *
     * @version V1.0
     */
    @Override
    public void immedietelyActiveRegion(String regionId, String regionNature,
                                        Date beginTime) {
        //查询需要更新的记录
        PriceRegionEcGoodsEntity model = regionEcGoodsDao.searchRegionByID(regionId, regionNature);
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
        regionEcGoodsDao.updateRegion(model);
        //查询区域与部门关系相关记录
        PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnEcGoodsEntity();
        priceRegioOrgnEntity.setPriceRegionId(model.getId());
        priceRegioOrgnEntity.setRegionNature(regionNature);
        List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
        if (CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
            for (PriceRegioOrgnEcGoodsEntity priceRegioOrgnModel : priceRegioOrgnEntityList) {
                priceRegioOrgnModel.setVersionNo(versionNoOrg);
                //修改状态
                priceRegioOrgnModel.setActive(FossConstants.ACTIVE);
                //补充更新信息
                priceRegioOrgnModel.setRegionNature(regionNature);
                priceRegioOrgnModel.setBeginTime(beginTime);
                priceRegioOrgnModel.setModifyOrgCode(orgCode);
                priceRegioOrgnModel.setModifyUser(userCode);
                regionEcGoodsDao.updateRegionOrg(priceRegioOrgnModel);
            }
        }
        /**
         * 维护行政区域与区域关系
         */
        if (StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, model.getRegionType())) {
            if (StringUtils.isNotEmpty(model.getCountyCode())) {
                districtRegionService.addDistrictRegion(model.getCountyCode());
                districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCountyCode());
            }
            if (StringUtils.isNotEmpty(model.getCityCode())) {
                districtRegionService.addDistrictRegion(model.getCityCode());
                districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCityCode());
            }
        } else if (PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(model.getRegionType())) {
            addDistrictRegionOrg(priceRegioOrgnEntityList);
        }

    }

    /**
     * 删除区域<br/>
     * 方法名：deleteRegion
     * @param regionIds
     *            要删除的区域的ID
     * @param regionNature
     *            区域性质
     * @author wangfeng 311417
     *
     * @Date 2016.07.05
     */
    @Override
    public void deleteRegion(List<String> regionIds, String regionNature) {
        regionEcGoodsDao.deleteRegion(regionIds, regionNature);
        regionEcGoodsDao.deleteRegionOrg(regionIds, regionNature);
    }

    /**
     *
     * 添加区域
     *
     * @author 311417   wangfeng
     *
     * @date 2016.07.05
     *
     * @param regionEntity
     *
     * @param addPriceRegioOrgnEntityList
     */
     @Override
    public void addRegion(PriceRegionEcGoodsEntity regionEntity,
                          List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList) {
    	 String regionId = UUIDUtils.getUUID();
 		// 设置ID
 		regionEntity.setId(regionId);
 		//转换时间(时间得失明天开始)
 		Date begintime = new Date();
 		Long versionNo = new Date().getTime();
 		regionEntity.setVersionNo(versionNo);
 		// 获取当前登录用户
 		UserEntity user = (UserEntity) UserContext.getCurrentUser();
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
 		}
 		PriceRegionEcGoodsEntity checkEntity = new PriceRegionEcGoodsEntity();
 		checkEntity.setRegionCode(regionEntity.getRegionCode());
 		checkEntity.setRegionNature(regionEntity.getRegionNature());
 		// 判断CODE是否重复
 		boolean isTheSameRegionCode = this.isTheSameRegionCode(checkEntity);
 		if (isTheSameRegionCode) {
 			throw new RegionException(RegionException.REGION_CODE_SAME,
 					RegionException.REGION_CODE_SAME);
 		}
 		if(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())){
 			//校验行政区域
 			checkRegionOrganizationType(regionEntity);
 		}
        //添加数据
        regionEcGoodsDao.addRegion(regionEntity);
        String regionNature = regionEntity.getRegionNature();
        //处理该区域包含的组织
        addRegionOrg(regionEntity, addPriceRegioOrgnEntityList, regionId,
				begintime, regionNature, userCode, orgCode);

        /**
         * 添加行政区域与区域关系
         */
        processDistrictRegion(regionEntity, addPriceRegioOrgnEntityList);
    }
    
     /**
      * 处理该区域包含的组织
      * @param regionEntity
      * @param addPriceRegioOrgnEntityList
      * @param regionId
      * @param begintime
      * @param regionNature
      * @param userCode
      * @param orgCode
      */
	private void addRegionOrg(PriceRegionEcGoodsEntity regionEntity,
			List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList,
			String regionId, Date begintime, String regionNature,
			String userCode, String orgCode) {
		if (addPriceRegioOrgnEntityList != null) {
            for (PriceRegioOrgnEcGoodsEntity model : addPriceRegioOrgnEntityList) {
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
                PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnEcGoodsEntity();
                priceRegioOrgnEntity.setIncludeOrgCode(model.getIncludeOrgCode());
                priceRegioOrgnEntity.setRegionNature(regionNature);
                List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
                if (CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
                    for (int loop = 0; loop < priceRegioOrgnEntityList.size(); loop++) {
                        PriceRegioOrgnEcGoodsEntity object = priceRegioOrgnEntityList.get(loop);
                        if (begintime.getTime() <= object.getEndTime().getTime()) {
                            PriceRegionEcGoodsEntity priceRegionEntity = regionEcGoodsDao.searchRegionByID(object.getPriceRegionId(), regionNature);
                            String message = "在" + priceRegionEntity.getRegionName() + "区域下已经存在部门" + object.getIncludeOrgCode();
                            throw new RegionException(message, message);
                        }
                    }
                    //没有抛异常则新增
                    regionEcGoodsDao.addRegionOrg(model);
                } else {
                    regionEcGoodsDao.addRegionOrg(model);
                }
            }
        }
	}
    
    /*
     * 添加区域与行政组织关系
     * @param regionEntity
     * @param addPriceRegioOrgnEntityList
     */
	private void processDistrictRegion(PriceRegionEcGoodsEntity regionEntity,
			List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList) {
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
        		if(CollectionUtils.isNotEmpty(addPriceRegioOrgnEntityList)) {
                //把部门添加到区域行政关系中
            	addDistrictRegionOrg(addPriceRegioOrgnEntityList);
        		}
            }
        }
	}
    
    /**
     * 把部门添加到区域行政关系中
     * @param addPriceRegioOrgnEntityList
     */
	private void addDistrictRegionOrg(
		List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList) {
	    List<String> cityList = new ArrayList<String>();
	    List<String> countyList = new ArrayList<String>();
	    for (PriceRegioOrgnEcGoodsEntity priceRegioOrgnEcEntity : addPriceRegioOrgnEntityList) {
	        String deptCode = priceRegioOrgnEcEntity.getIncludeOrgCode();
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

    /**
     * 更新区域
     * @author 311417 wangfeng
     *
     * @date 2016.07.05
     *
     * @param regionEntity
     *
     * @param addPriceRegioOrgnEntityList
     *
     * @param updatePriceRegioOrgnEntityList
     */
     @Override
    public void updateRegion(PriceRegionEcGoodsEntity regionEntity,
                             List<PriceRegioOrgnEcGoodsEntity> addPriceRegioOrgnEntityList,
                             List<PriceRegioOrgnEcGoodsEntity> updatePriceRegioOrgnEntityList) {
        Long versionNo = new Date().getTime();
        regionEntity.setVersionNo(versionNo);
        String regionNature = regionEntity.getRegionNature();
        Date begintime = new Date();
        // 获取当前登录用户
        UserEntity user = (UserEntity) UserContext.getCurrentUser();
        if (user == null) {
            throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
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
        if (PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE.equalsIgnoreCase(regionEntity.getRegionType())) {
            //校验行政区域
        	checkRegionOrganizationTypeForUpdate(regionEntity);

        }
        //更新数据
        regionEcGoodsDao.updateRegion(regionEntity);
        //处理包含组织
        if (addPriceRegioOrgnEntityList != null) {
            for (PriceRegioOrgnEcGoodsEntity model : addPriceRegioOrgnEntityList) {
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
                PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnEcGoodsEntity();
                priceRegioOrgnEntity.setIncludeOrgCode(model.getIncludeOrgCode());
                priceRegioOrgnEntity.setRegionNature(regionNature);
                List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
                if (CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
                    for (int loop = 0; loop < priceRegioOrgnEntityList.size(); loop++) {
                        PriceRegioOrgnEcGoodsEntity object = priceRegioOrgnEntityList.get(loop);
                        if (begintime.getTime() <= object.getEndTime().getTime()) {
                            PriceRegionEcGoodsEntity priceRegionEntity = regionEcGoodsDao.searchRegionByID(object.getPriceRegionId(), regionEntity.getRegionNature());
                            //在哪个区域下已经存在该部门
                            String message = "在" + priceRegionEntity.getRegionName() + "区域下已经存在部门" + object.getIncludeOrgCode();
                            throw new RegionException(message, message);
                        }
                    }
                    //没有抛异常则新增
                    regionEcGoodsDao.addRegionOrg(model);
                } else {
                    regionEcGoodsDao.addRegionOrg(model);
                }

            }
        }
        //处理其他数据
        if (updatePriceRegioOrgnEntityList != null) {
            for (PriceRegioOrgnEcGoodsEntity model : updatePriceRegioOrgnEntityList) {
                Long versionNoOrg = new Date().getTime();
                model.setVersionNo(versionNoOrg);
                model.setRegionNature(regionEntity.getRegionNature());
                model.setModifyOrgCode(orgCode);
                model.setModifyUser(userCode);
                regionEcGoodsDao.updateRegionOrg(model);
            }
        }
    }

    /**
     *
     * 根据ID，查询区域 信息
     * @author 311417 wangfeng
     *
     * @date 2016.07.05
     *
     * @param id
     *
     * @param regionNature
     *
     * @return
     */
     @Override
    public PriceRegionEcGoodsEntity searchRegionByID(String id,
                                                     String regionNature) {
        if (StringUtils.isEmpty(id)) {
            return new PriceRegionEcGoodsEntity();
        }
        PriceRegionEcGoodsEntity model = regionEcGoodsDao.searchRegionByID(id, regionNature);
        if (model != null) {
            if (PricingConstants.PRESCRIPTION_REGION.equals(regionNature)) {
                model.setRegionNature(PricingConstants.PRESCRIPTION_REGION_NAME);
            } else if (PricingConstants.PRICING_REGION.equals(regionNature)) {
                model.setRegionNature(PricingConstants.PRICING_REGION_NAME);
            }
            //判断国家CODE不为空
            if (StringUtil.isNotBlank(model.getNationCode())) {
                AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getNationCode());
                if (null == administrativeRegionsEntity) {
                    return model;
                }
                model.setNationName(administrativeRegionsEntity.getName());
            }
            //判断省份CODE不为空
            if (StringUtil.isNotBlank(model.getProCode())) {
                AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getProCode());
                if (null == administrativeRegionsEntity) {
                    return model;
                }
                model.setProName(administrativeRegionsEntity.getName());
            }
            //判断城市CODE不为空
            if (StringUtil.isNotBlank(model.getCityCode())) {
                AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getCityCode());
                if (null == administrativeRegionsEntity) {
                    return model;
                }
                model.setCityName(administrativeRegionsEntity.getName());
            }
            //判断区县CODE不为空
            if (StringUtil.isNotBlank(model.getCountyCode())) {
                AdministrativeRegionsEntity administrativeRegionsEntity = administrativeRegionsService.queryAdministrativeRegionsByCode(model.getCountyCode());
                if (null == administrativeRegionsEntity) {
                    return model;
                }
                model.setCountyName(administrativeRegionsEntity.getName());
            }
        }
        return model;
    }

     /**
      * 根据区域id查询区域
      * @param id
      * @return
      */
     public PriceRegionEcGoodsEntity searchRegionByID(String id) {
         if (StringUtils.isEmpty(id)) {
             return new PriceRegionEcGoodsEntity();
         }
         PriceRegionEcGoodsEntity model = regionEcGoodsDao.searchRegionByID(id);
         if(model ==null){
        	 return new PriceRegionEcGoodsEntity();
         }
         return model;
     }
     
    /**
     *
     * @Description: 立即中止
     *
     * @author wangfeng 311417
     *
     * @date 2016.07.05
     *
     * @param regionId
     *
     * @param regionNature
     *
     * @param date
     *
     */
    @Override
    public void immedietelyStopRegion(String regionId, String regionNature,
                                      Date endTime) {
        //查询需要更新的记录
        PriceRegionEcGoodsEntity model = regionEcGoodsDao.searchRegionByID(regionId, regionNature);
        //DP-foss 343617 zhaoyiqing 20160818 校验终止时间不能早于激活时间
        Date beginTime = model.getBeginTime();
        if(endTime!=null && beginTime!=null && beginTime.after(endTime)){
            throw new RegionException(endTimeBeforeBeginTimeError,null);
        }
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
        regionEcGoodsDao.updateRegion(model);
        //查询区域与部门关系相关记录
        PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity = new PriceRegioOrgnEcGoodsEntity();
        priceRegioOrgnEntity.setPriceRegionId(model.getId());
        priceRegioOrgnEntity.setRegionNature(regionNature);
        List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
        if (CollectionUtils.isNotEmpty(priceRegioOrgnEntityList)) {
            for (PriceRegioOrgnEcGoodsEntity priceRegioOrgnModel : priceRegioOrgnEntityList) {
                priceRegioOrgnModel.setVersionNo(versionNoOrg);
                priceRegioOrgnModel.setRegionNature(regionNature);
                //如果原结束时间晚于此次更新时间，则执行更新
                //如果原结束时间早于此次更新时间，则不执行更新
                if (null!=endTime && endTime.getTime() < priceRegioOrgnModel.getEndTime().getTime()) {
                    priceRegioOrgnModel.setEndTime(endTime);
                } else {
                    continue;
                }
                priceRegioOrgnModel.setModifyOrgCode(orgCode);
                priceRegioOrgnModel.setModifyUser(userCode);
                regionEcGoodsDao.updateRegionOrg(priceRegioOrgnModel);
            }
        }
        /**
         * 维护行政区域与区域关系
         */
        if (StringUtils.equals(PricingConstants.REGION_ORGANIZATION_TYPE_ADMINISTRATIVE, model.getRegionType())) {
            if (StringUtils.isNotEmpty(model.getCountyCode())) {
                districtRegionService.addDistrictRegion(model.getCountyCode());
                districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCountyCode());
            }
            if (StringUtils.isNotEmpty(model.getCityCode())) {
                districtRegionService.addDistrictRegion(model.getCityCode());
                districtRegionCacheDeal.getDistrictRegionCache().invalid(model.getCityCode());
            }
        } else if (PricingConstants.REGION_ORGANIZATION_TYPE_DEPT.equalsIgnoreCase(model.getRegionType())) {
            addDistrictRegionOrg(priceRegioOrgnEntityList);
        }
    }

    /**
     * getCurrentUserCode:获取当前用户信息. <br/>
     */
    private String getCurrentUserCode() {
        UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
        if (currentUser == null) {
            throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
        }
        return currentUser.getEmployee().getEmpCode();
    }

    /**
     * getCurrentOrgCode:获取当前机构信息. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @return
     * @author 311417
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
    public boolean isTheSameRegionName(PriceRegionEcGoodsEntity priceRegionEntity, boolean isUpdate) {
    	boolean flag = false;
		List<PriceRegionEcGoodsEntity> priceRegionEcEntityList = regionEcGoodsDao.searchRegionByName(priceRegionEntity.getRegionName(),
				priceRegionEntity.getRegionNature());
		if (priceRegionEcEntityList != null && priceRegionEcEntityList.size() > 1) {
			flag = true;
		} else if (CollectionUtils.isEmpty(priceRegionEcEntityList)) {
			flag = false;
		} else if (priceRegionEcEntityList != null && priceRegionEcEntityList.size() == 1) {
			// 如果修改还得判断是否ID相同
			if (isUpdate) {
				if (StringUtils.equals(priceRegionEcEntityList.get(0).getId(), priceRegionEntity.getId())) {
					flag = false;
				} else {
					flag = true;
				}
				// 如果是新增，直接表示有重复
			} else {
				flag = true;
			}
		}
		return flag;
    }

    /**
     * isTheSameRegionCode:检测区域code. <br/>
     * <p>
     * Date:2016.06.29
     *
     * @param priceRegionEntity
     * @return
     * @author 311417 wangfeng
     * @since JDK 1.6
     */
    public boolean isTheSameRegionCode(PriceRegionEcGoodsEntity priceRegionEntity) {
        List<PriceRegionEcGoodsEntity> priceRegionEntityList = regionEcGoodsDao
                .searchRegionByCondition(priceRegionEntity, NumberConstants.ZERO, Integer.MAX_VALUE);
        if (CollectionUtils.isEmpty(priceRegionEntityList)) {
            return false;
        }
        return true;
    }

    /**
     * checkRegionOrganizationType:检测行政组织区域是否重复. <br/>
     * Date:2016.06.29
     *
     * @param regionEntity
     * @author 311417 wangfeng
     * @since JDK 1.6
     */
    private void checkRegionOrganizationType(PriceRegionEcGoodsEntity regionEntity) {
        String administrativeRegionCodes = regionEntity.getNationCode()+regionEntity.getProCode()+regionEntity.getCityCode()+regionEntity.getCountyCode();
		String regionNature = regionEntity.getRegionNature();
		List<PriceRegionEcGoodsEntity> priceRegionEntityList=regionEcGoodsDao.checkRegionOrganizationType(administrativeRegionCodes, regionNature, new Date());
		if(CollectionUtils.isNotEmpty(priceRegionEntityList)){
			 throw new RegionException("该行政组织下已经存在区域！",null);
		}
    }
    
    /**
     * 更新时校验行政组织关系
     * @param regionEntity
     */
    private void checkRegionOrganizationTypeForUpdate(PriceRegionEcGoodsEntity regionEntity) {
        String administrativeRegionCodes = regionEntity.getNationCode()+regionEntity.getProCode()+regionEntity.getCityCode()+regionEntity.getCountyCode();
        String regionNature = regionEntity.getRegionNature();
        List<PriceRegionEcGoodsEntity> priceRegionEntityList=regionEcGoodsDao.checkRegionOrganizationType(administrativeRegionCodes, regionNature, new Date());
        if(CollectionUtils.isNotEmpty(priceRegionEntityList)
                && !(priceRegionEntityList.size()==1 &&
                StringUtil.equals(priceRegionEntityList.get(0).getRegionCode(),regionEntity.getRegionCode()))){
            throw new RegionException("该行政组织下已经存在区域！",null);
        }
    }
    
    /**
     *
     * 根据条件查询
     *
     * @author 311417 wangfeng
     *
     * @date 2016.07.06
     *
     * @param priceRegioOrgnEntity
     *
     * @return
     */
     @Override
    public List<PriceRegioOrgnEcGoodsEntity> searchRegionOrgByCondition(
            PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity) {
        List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntityList = regionEcGoodsDao.searchRegionOrgByCondition(priceRegioOrgnEntity);
        List<PriceRegioOrgnEcGoodsEntity> resultlist = new ArrayList<PriceRegioOrgnEcGoodsEntity>();
        for (PriceRegioOrgnEcGoodsEntity model : priceRegioOrgnEntityList) {
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

    public IRegionEcGoodsDao getRegionEcGoodsDao() {
        return regionEcGoodsDao;
    }

    public void setRegionEcGoodsDao(IRegionEcGoodsDao regionEcGoodsDao) {
        this.regionEcGoodsDao = regionEcGoodsDao;
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
     * @param names        区域名称List
     *
     * @param regionNature 区域性质
     *
     * @param billDate     开单日期
     *
     * @return
     *
     * @author wangfeng
     *
     * @date 2016.06.29
     */
    @Override
    public Map<String, PriceRegionEcGoodsEntity> findRegionByNames(List<String> names, String regionNature, Date billDate) {
        return regionEcGoodsDao.findRegionByNames(names, regionNature, billDate);
    }

    /**
     * @param deptRegionId
     *
     * @param regionNature
     *
     * @return
     *
     * @Description: 根据区域ID获取其下所有的营业部
     *
     * @author 311417 wangfeng
     *
     * @date 2016.06.29
     */
    @Override
    public List<String> searchRegionOrgCodeByRegionId(String deptRegionId, String regionNature) {
        List<String> list = null;
        List<PriceRegioOrgnEcGoodsEntity> priceRegioOrgnEntities = regionEcGoodsDao.searchRegionOrgByRegionId(deptRegionId, regionNature);
        if (CollectionUtils.isNotEmpty(priceRegioOrgnEntities)) {
            list = new ArrayList<String>();
            for (PriceRegioOrgnEcGoodsEntity priceRegioOrgnEntity : priceRegioOrgnEntities) {
                list.add(priceRegioOrgnEntity.getIncludeOrgCode());
            }
        }
        return list;
    }
}
