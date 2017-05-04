/**
 *  initial comments.
 *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目
 *  
 *  的地信息组成：
 *  
 *  出发信息包括：
 *  
 *  a)	生效日期: 设定生效日期。
 *  
 *  b)	始发区域: 区域信息来自产品基础数据维护下
 *  
 *  	的区域来填充该下拉表请先熟悉 DP-FOSS-接送货系统用例-
 *  
 *  	产品价格-城市列表-录入区域SUC-587-V0.1用例。          
 *  
 *  C)  未被激活的版本信息部能被正常使用。D) 方案描述：  
 *  
 *  	对建立新方案的一些描述信息。
 *  
 *      目的地信息：  
 *      
 *      由于可以设置至少一个到N个元素信息.故使用明细列表的方式来暂存数据一起提交。
 *      
 *  SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
 *  
 *  SR3	添加目的地明细信息时可一选择基础产品设置运营时效和取货时间，是否长短途， 
 *  
 *  	在提交目的地设置相关信息时点击 “提交”按钮需要校验，目的地、基础产品、长短途在后台
 *  
 *  	不能有相同的数据已存在， 提示如“广州-精准卡航-短途时效信息已经存在不能重复添加!”
 *  
 *  SR7	图3中产品下拉列表中数据需要过滤规则：  只能显示该始发区域与目的地区域在区域与产品基础数据中所维护的产品信息。
 *  
 *  SR9	新增时，所选择的区域信息（始发区域/目的地区域）都需要过滤，只能取时效相关的区域信息作为数据源。
 *  
 *  	如：图2中界面元素下拉列标的始发区域，图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为时效的区域,”。
 *  
 *  SR11新增时效方案信息时， 必须先保存出发地信息，然后目的地信息中的“新增”、“删除”、“修改”、
 *  
 *  	功能才可以被使用。 否则为禁用状态。
 *  
 *  SR12所有新建方案都以草稿数据存储。针对草稿数据，我们可以随时进行任意操作。
 *  
 *      一旦在查询列表中做过激活的方案，就不能再做删除与修改了。
 *      
 *      只能做方案的复制对该方案进行延续不同时间段内体现不同时效信息。
 *      
 *  SR13 在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的目的地明细是否在指定的生效日期是不是已
 *  
 *  	经存在相同的重复数据检查。重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品（三级），
 *  
 *  	如果存在则提示“该方案下xxx目的地xxx产品xxx已经在另一个xxx方案下存在，请确认是否以该方案为准,请将xxx方案中止。
 *  
 * SR14	立即中止功能： 在时效查询列表中，只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 。
 *  
 * SR15	立即激活功能： 在时效查询列表中，只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” 。
 *  
 *  
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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/EffectivePlanService.java
 * 
 * FILE NAME        	: EffectivePlanService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectivePlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.EffectivePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * 时效方案信息实现类,对时效版本的管理服务
 * 提供后续公布价中需要查询到的时效版本信息
 * @author 岳洪杰
 * @date 2012-10-12 上午10:55:12
 * @since
 * @version
 */
public class EffectivePlanService implements IEffectivePlanService{


	private static final int THREE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;



	private static final int NUMBER_11 =11;
	private static final int NUMBER_20 =20;
	private static final int NUMBER_31 =31;
	private static final int NUMBER_2999 =2999;
	/**
	 * 
	 */
	Logger log = Logger.getLogger(getClass());
	/**
	 * 时效方案DAO`
	 */
    @Inject
    IEffectivePlanDao effectivePlanDao;
    /**
	 * 时效方案明细DAO
	 */
    @Inject
    IEffectivePlanDetailDao effectivePlanDetailDao;
    /**
	 * 产品SERVICE
	 */
    @Inject
    IProductService productService;
    /**
	 * 区域SERVICE
	 */
    @Inject
    IRegionService regionService;
    /**
	 * 员工SERVICE
	 */
    @Inject
    IEmployeeService employeeService;
    /**
     * 设置 员工SERVICE.
     *
     * @param employeeService the new 员工SERVICE
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 设置 时效方案明细DAO.
     *
     * @param effectivePlanDetailDao the new 时效方案明细DAO
     */
    public void setEffectivePlanDetailDao(
    	IEffectivePlanDetailDao effectivePlanDetailDao) {
        this.effectivePlanDetailDao = effectivePlanDetailDao;
    }
    /**
     * 设置 时效方案DAO.
     *
     * @param effectivePlanDao the new 时效方案DAO
     */
    public void setEffectivePlanDao(IEffectivePlanDao effectivePlanDao) {
        this.effectivePlanDao = effectivePlanDao;
    }
    /**
     * 设置 产品SERVICE.
     *
     * @param productService the new 产品SERVICE
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    /**
     * 设置 区域SERVICE.
     *
     * @param regionService the new 区域SERVICE
     */
    public void setRegionService(IRegionService regionService) {
        this.regionService = regionService;
    }

    /**
     * 
     * <p>(按照具体始发区域返回最新时效信息)</p> 
     * 
     * @author 岳洪杰
     * 
     * @date 2012-10-13 上午8:53:02
     * 
     * @param cuurentTime
     * 
     * @param regionId
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService#findEffectivePlanByRegionId(java.util.Date, java.lang.String)
     */
    public EffectivePlanEntity findEffectivePlanByRegionId(Date cuurentTime, String active,String regionId) throws EffectivePlanException{
	return effectivePlanDao.findEffectivePlanByRegionId(cuurentTime,active,regionId);
    }

    /**
     * 
     * 时效方案新增
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-25 下午6:56:48
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService#insertEffectivePlanEntity(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectivePlanEntity)
     */
    @Override
    public int insertEffectivePlanEntity(EffectivePlanEntity effectivePlanEntity) throws EffectivePlanException{
	int valRt = -1;
	if(null != effectivePlanEntity){
	    //判断输入参数是否符合业务要求
	    if(null == effectivePlanEntity.getDeptRegionCode()){
		    throw new EffectivePlanException("方案信息不能为空!",EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
	    }
	    Date currentTime = new Date();
	    if(effectivePlanEntity.getBeginTime().before(currentTime)){
		throw new EffectivePlanException("开始时间必须大于当前营业日期!", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
	    }
	    //查询当前方案名称是否已经存在了。如果存在则提示重复信息
	    EffectivePlanEntity queryEffectivePlanEntity = new EffectivePlanEntity(); 
	    queryEffectivePlanEntity.setName(effectivePlanEntity.getName());
	    List<EffectivePlanEntity> effectivePlanEntities =  effectivePlanDao.searchEffectivePlanByName(queryEffectivePlanEntity);
	    if(CollectionUtils.isNotEmpty(effectivePlanEntities)){
		throw new EffectivePlanException(effectivePlanEntity.getName()+"方案已经存在，不可录入！",EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
	    }
	    //设置 结束时间， uuid，有效标记，创建时间，修改时间
	    effectivePlanEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	    String effectivePlanId = UUIDUtils.getUUID();
	    effectivePlanEntity.setId(effectivePlanId);
	    effectivePlanEntity.setActive(FossConstants.INACTIVE);
	    effectivePlanEntity.setVersionNo(new Date().getTime());
	    effectivePlanEntity.setCreateDate(currentTime);
	    effectivePlanEntity.setModifyDate(currentTime);
	    //数据库持久化时效方案信息
	    valRt = effectivePlanDao.insertSelective(effectivePlanEntity);
	}
	return valRt;
    }
    
    /**
     * 
     * 激活时效方案
     * 方法名： activeEffectivePlan
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-10 下午2:37:06
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService#activeEffectivePlan(java.lang.String[])
     */
    @Transactional
    @Override
    public int activeEffectivePlan(List<String> effectivePlans)throws EffectivePlanException {
	Date currentDate = new Date();
	int valRt = 0;
	for (String key : effectivePlans) {
	    EffectivePlanEntity effectivePlanEntity = effectivePlanDao.selectByPrimaryKey(key);
	    if(null == effectivePlanEntity){
		throw new PricePlanException("根据方案ID"+key+"查询不到具体的时效方案信息,请联系管理员查看!", null);
	    }
	    Date beginTime = effectivePlanEntity.getBeginTime();
	    if(currentDate.after(beginTime)){
			throw new PricePlanException(effectivePlanEntity.getName()+"方案的生效日期为"+ DateUtils.convert(beginTime, DateUtils.DATE_TIME_FORMAT
			+" 需要大于当前营业日期才能被激活!"), null);
	    }
	    EffectivePlanDetailEntity effectivePlanDetailEntity = new EffectivePlanDetailEntity();
	    effectivePlanDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
	    List<EffectivePlanDetailEntity> effectivePlanDetailEntities = effectivePlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
	    if(CollectionUtils.isNotEmpty(effectivePlanDetailEntities)){
		//用于记录时效明细的集合
		List<String> detailIds = new ArrayList<String>();
		//用于记录异常信息
		StringBuilder errStr = new StringBuilder();
		//迭代时效明细信息，并判断是否可以正常激活
		for (EffectivePlanDetailEntity detail : effectivePlanDetailEntities) {
		    if(null!=detail){
			 //获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
			 List<EffectivePlanDetailEntity> detailList = effectivePlanDetailDao.queryEffectivePlanDetailListByCondition(detail.getDeptRegionId(),detail.getArrvRegionId(),detail.getProductCode(),effectivePlanEntity.getBeginTime());
			 if(CollectionUtils.isNotEmpty(detailList)){
			         EffectivePlanEntity existBean =  effectivePlanDao.selectByPrimaryKey(detail.getEffectivePlanId());
			     	 PriceRegionEntity effectiveDeptRegion = regionService.searchRegionByID(detail.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
			     	 if(null == effectiveDeptRegion){
			     	     throw new EffectivePlanException("缺失始发区域的基础信息， 请联系管理员查看!",null);
			     	 }
			     	 PriceRegionEntity effectiveArrvRegion = regionService.searchRegionByID(detail.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
			     	 if(null == effectiveArrvRegion){
			     	     throw new EffectivePlanException("缺失目的地区域基础信息， 请联系管理员查看!",null);
			     	 }
			     	 ProductEntity productEntity = productService.getProductByCache(detail.getProductCode(), currentDate);
			     	 if(null == productEntity){
			     	     throw new EffectivePlanException("缺失产品的基础信息， 请联系管理员查看!",null);
			     	 }
				 errStr.append("始发区域[");
				 errStr.append(effectiveDeptRegion.getRegionName());
				 errStr.append("],目的地区域[");
				 errStr.append(effectiveArrvRegion.getRegionName());
				 errStr.append("],产品[");
				 errStr.append(productEntity.getName());
				 errStr.append("],在时效方案名称为[");
				 errStr.append(existBean.getName());
				 errStr.append("],下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止"+existBean.getName()+"价格方案!");
				 throw new EffectivePlanException(errStr.toString(), null);
			 }else{
			     	//累计有效的时效明细ID
			     	detailIds.add(detail.getId());
			 }
		    } 
		}
		    //激活明细
		    effectivePlanDetailDao.activeEffectivePlanDetailByIds(detailIds);
		    //设置方案状态
		    effectivePlanEntity.setActive(FossConstants.ACTIVE);
		    effectivePlanEntity.setVersionNo(new Date().getTime());
		    //激活方案
		    valRt = effectivePlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
	    }else{
		throw new PricePlanException(effectivePlanEntity.getName()+"方案明细为空不能激活!", null);
	    }
	}
	return valRt;
    }

    /**
     * 
     * 查询方案主信息分页
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-10 下午7:50:06
     */
    @Override
	public List<EffectivePlanEntity> searchEffectiveByCondition(EffectivePlanEntity effectivePlanEntity, int start,
			int limit) throws EffectivePlanException {
		if (PricingConstants.ALL.equals(effectivePlanEntity.getActive())) {
			effectivePlanEntity.setActive(null);
		}
		//如果选择的是全部则不添加任何过滤条件
		if(PricingConstants.ALL.equals(effectivePlanEntity.getCurrentUsedVersion())){
			effectivePlanEntity.setCurrentUsedVersion(null);
		}else{
			effectivePlanEntity.setBusinessDate(new Date());
		}
		List<EffectivePlanEntity> effectivePlanEntities = effectivePlanDao.searchEffectivePlanByCondition(
				effectivePlanEntity, start, limit);
		List<EffectivePlanEntity> list = new ArrayList<EffectivePlanEntity>();
		for (EffectivePlanEntity temp : effectivePlanEntities) {
			if(StringUtil.isNotEmpty(temp.getModifyUser())){
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(temp.getModifyUser());
				if (null != emp) {
					temp.setModifyUserName(emp.getEmpName());
				}
			}
			PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(temp.getDeptRegionId(),
					PricingConstants.PRESCRIPTION_REGION);			
			if (null != priceRegionEntity) {
				temp.setDeptRegionName(priceRegionEntity.getRegionName());
			}
			list.add(temp);
		}
		return list;
	}

    /**
     * 
     * 查询方案主信息分页总记录数
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-10 下午7:50:06
     */
    @Override
    public Long searchEffectiveByConditionCount(
	    EffectivePlanEntity effectivePlanEntity)throws EffectivePlanException {
	return effectivePlanDao.searchEffectivePlanByConditionCount(effectivePlanEntity);
    }

    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息)
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return 
     * 
     * List<EffectivePlanDetailEntity>
     * 
     * @exception 
     * 
     * @since  1.0.0
     */
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfo(
	    EffectivePlanDetailEntity effectivePlanDetailEntity)throws EffectivePlanException {
	List<EffectivePlanDetailEntity> effectivePlanDetailEntities = effectivePlanDetailDao.queryEffectivePlanDetailInfo(effectivePlanDetailEntity);
	Date currentDateTime =  new Date();
	List<EffectivePlanDetailEntity> effectivePlanDetailList = null;
	//根据时效明细结果集中的产品CODE与区域ID分别查询产品名称、区域名称
	if(CollectionUtils.isNotEmpty(effectivePlanDetailEntities)){
	    effectivePlanDetailList = new ArrayList<EffectivePlanDetailEntity>();
	    for (EffectivePlanDetailEntity effectivePlanDetail : effectivePlanDetailEntities) {
		String productCode = effectivePlanDetail.getProductCode();
		String arrvregionId = effectivePlanDetail.getArrvRegionId();
		ProductEntity productEntity = productService.getProductByCache(productCode,currentDateTime);
		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(arrvregionId, PricingConstants.PRESCRIPTION_REGION);
		effectivePlanDetail.setProductName(productEntity.getName());
		effectivePlanDetail.setArrvRegionName(priceRegionEntity.getRegionName());
		effectivePlanDetailList.add(effectivePlanDetail);
	    }
	}
	return effectivePlanDetailList;
    }

    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息分页)
     * 
     * @param effectivePlanDetailEntity
     * 
     * @param start
     * 
     * @param limit
     * 
     * @return 
     * 
     * List<EffectivePlanDetailEntity>
     * 
     * @exception 
     * 
     * @since  1.0.0
     */
    @Override
    public List<EffectivePlanDetailEntity> queryEffectivePlanDetailInfoPagging(
	    EffectivePlanDetailEntity effectivePlanDetailEntity, int start, int limit) throws EffectivePlanException{
	
	List<EffectivePlanDetailEntity> effectivePlanDetailEntities =  effectivePlanDetailDao.queryEffectivePlanDetailInfoPagging(effectivePlanDetailEntity, start, limit);
	List<EffectivePlanDetailEntity> effectiveArrayList = new ArrayList<EffectivePlanDetailEntity>();
	
	for (EffectivePlanDetailEntity temp : effectivePlanDetailEntities) {
		PriceRegionEntity priceRegionEnntiy = regionService.searchRegionByID(temp.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
		ProductEntity  productEntity = productService.getProductByCache(temp.getProductCode(), new Date());
		if(null != priceRegionEnntiy){
			temp.setArrvRegionName(priceRegionEnntiy.getRegionName());
		}
		if(null != productEntity){
			temp.setProductName(productEntity.getName());
		}
		effectiveArrayList.add(temp);
	}
	return effectiveArrayList;
    }
    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息分页总数)
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return 
     * 
     * Long 
     * 
     * @exception 
     * 
     * @since  1.0.0
     */
    @Override
    public Long queryEffectivePlanDetailInfoPaggingCount(
	    EffectivePlanDetailEntity effectivePlanDetailEntity) {
	return effectivePlanDetailDao.queryEffectivePlanDetailInfoPaggingCount(effectivePlanDetailEntity);
    }
    /**
     * 
     * @Description: 修改时效方案
     * 
     * Company:IBM
     * 
     * @author IBMDP-Administrator
     * 
     * @date 2012-12-22 下午10:01:09
     * 
     * @param effectivePlanEntity
     * 
     * @return
     * 
     * @version V1.0
     */
    @Override
    @Transactional
    public void modifyEffectivePlan(
			EffectivePlanEntity effectivePlanEntity) {
        	effectivePlanEntity.setModifyDate(new Date());
        	Date currentDate = new Date();
        	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
        	UserEntity currentUser = FossUserContext.getCurrentUser();
        	effectivePlanEntity.setModifyOrgCode(currentDept.getCode());
        	effectivePlanEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
        	effectivePlanEntity.setVersionInfo(String.valueOf(currentDate.getTime()));
        	effectivePlanEntity.setVersionNo(currentDate.getTime());
            effectivePlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
            EffectivePlanDetailEntity planDetailEntity = new EffectivePlanDetailEntity();
            planDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
            List<EffectivePlanDetailEntity> planDetailEntities = effectivePlanDetailDao.queryEffectivePlanDetailByCondition(planDetailEntity);
            if(CollectionUtils.isNotEmpty(planDetailEntities)) {
            	for (EffectivePlanDetailEntity effectivePlanDetailEntity : planDetailEntities) {
            		effectivePlanDetailEntity.setDeptRegionId(effectivePlanEntity.getDeptRegionId());
            		effectivePlanDetailEntity.setVersionNo(currentDate.getTime());
            		effectivePlanDetailDao.updateByPrimaryKeySelective(effectivePlanDetailEntity);
				}
            }
    }
    /**
     * 
     * @Description: 复制时效主方案
     * 
     * Company:IBM
     * 
     * @author IBMDP-sz
     * 
     * @date 2012-12-22 下午8:36:39
     * 
     * @param effectivePlanId
     * 
     * @return EffectivePlanEntity
     * 
     * @version V1.0
     */
	@Override
	public EffectivePlanEntity copyEffectivePlan(String effectivePlanId) throws EffectivePlanException{
		EffectivePlanEntity voEffectivePlanEntity = null;
		if(effectivePlanId != null && !"".equals(effectivePlanId)) {
			EffectivePlanEntity effectivePlanEntity = effectivePlanDao.selectByPrimaryKey(effectivePlanId);
			if(effectivePlanEntity != null && effectivePlanEntity.getId() != null && StringUtil.equals(effectivePlanEntity.getId(), effectivePlanId)) {
			    	voEffectivePlanEntity = new EffectivePlanEntity();
			    	try {
						PropertyUtils.copyProperties(voEffectivePlanEntity, effectivePlanEntity);
					} catch (IllegalAccessException e) {
						log.info(e.getMessage(), e);
					} catch (InvocationTargetException e) {
						log.info(e.getMessage(), e);
					} catch (NoSuchMethodException e) {
						log.info(e.getMessage(), e);
					}
				String newUUId = UUIDUtils.getUUID();
				voEffectivePlanEntity.setId(newUUId);
				voEffectivePlanEntity.setActive(FossConstants.INACTIVE);
				voEffectivePlanEntity.setVersionNo(new Date().getTime());
				voEffectivePlanEntity.setRefId(effectivePlanId);
				voEffectivePlanEntity.setBeginTime(getNextBusinessDate());
				voEffectivePlanEntity.setEndTime(new Date(PricingConstants.ENDTIME));
				voEffectivePlanEntity.setCreateDate(new Date());
				voEffectivePlanEntity.setCreateUser(getCurrentUserCode());
				voEffectivePlanEntity.setCreateOrgCode(getCurrentOrgCode());
				voEffectivePlanEntity.setModifyDate(new Date());
				voEffectivePlanEntity.setModifyUser(getCurrentUserCode());
				voEffectivePlanEntity.setModifyOrgCode(getCurrentOrgCode());
				effectivePlanDao.insertSelective(voEffectivePlanEntity);
				
				//批量处理
				effectivePlanDetailDao.insertBatchEffectivePlanDetail(effectivePlanId, newUUId);
				
//				EffectivePlanDetailEntity entity = new EffectivePlanDetailEntity();
//				entity.setEffectivePlanId(effectivePlanId);
//				List<EffectivePlanDetailEntity> planDetailEntities = effectivePlanDetailDao.queryEffectivePlanDetailInfo(entity);
//				if(planDetailEntities != null && planDetailEntities.size() > 0) {
//					for (int i = 0; i < planDetailEntities.size(); i++) {
//						EffectivePlanDetailEntity planDetailEntity = planDetailEntities.get(i);
//						if(planDetailEntity != null) {
//							EffectivePlanDetailEntity planDetailEntity2 = new EffectivePlanDetailEntity();
//							BeanUtils.copyProperties(planDetailEntity, planDetailEntity2, true);
//							String planDetailEntity2UUId = UUIDUtils.getUUID();
//							planDetailEntity2.setId(planDetailEntity2UUId);
//							planDetailEntity2.setEffectivePlanId(effectivePlanEntity2UUId);
//							planDetailEntity2.setVersionNo(new Date().getTime());
//							planDetailEntity2.setCreateDate(new Date());
//							planDetailEntity2.setCreateUser(getCurrentUserCode());
//							planDetailEntity2.setCreateOrgCode(getCurrentOrgCode());
//							planDetailEntity2.setModifyDate(new Date());
//							planDetailEntity2.setModifyUser(getCurrentUserCode());
//							planDetailEntity2.setModifyOrgCode(getCurrentOrgCode());
//							planDetailEntity2.setActive(FossConstants.INACTIVE);
//							effectivePlanDetailDao.insertSelective(planDetailEntity2);
//						}
//					}
//				}
			} else {
				throw new EffectivePlanException("没有获得该时效方案", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
			}
		} 
		return voEffectivePlanEntity;
	}
	/**
	 * 	
	 * @Description: 获取最大业务时间
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-14 下午2:01:22
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */



	private Date getMaxBusinessDate() {
        	Calendar cal = Calendar.getInstance();
        	cal.set(NUMBER_2999, NUMBER_11, NUMBER_31);
        	Date maxDate = cal.getTime();
        	return maxDate;
	}
	/**
	 * 	
	 * @Description: 获取下一天业务时间
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-14 下午2:01:22
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private Date getNextBusinessDate() {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(new Date());
    	cal.add(Calendar.DATE, 1);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	Date maxDate = cal.getTime();
    	return maxDate;
    }
	/**
     * @Description: 导入时效数据
     * 
     * Company:IBM
     * 
     * @author IBMDP-sz
     * 
     * @date 2012-12-22 下午10:45:25
     * 
     * @version V1.0
     */
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public void importEffectivePlanDetail(Workbook book) {
		Integer index = 1;
		Workbook workbook = book;
    	int sheetNum = workbook.getNumberOfSheets();
    	if(sheetNum > NUMBER_20)
    	{
    		throw new EffectivePlanException("一次导入的时效方案数量不允许超过20，请确认后再次导入！");
    	}
    	for (int i = 0; i < sheetNum; i++) {
    		Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
        	Iterator rows = sheet.rowIterator();
        	//解決第一张Excel表格为results的问题
            //if ("results".equals(sheetName)) {
            //	continue;
            //}
			if (sheet.rowIterator().hasNext() == false) {
				continue;
			}
            EffectivePlanEntity effectivePlanEntity = null;
            List<PriceRegionEntity> regionEntities = regionService.searchRegionByName(sheetName, PricingConstants.PRESCRIPTION_REGION);
            if(regionEntities != null && regionEntities.size() > 0) {
            	PriceRegionEntity regionEntity = regionEntities.get(0);
            	effectivePlanEntity = new EffectivePlanEntity();
            	String effectivePlanEntitUUId = UUIDUtils.getUUID();
            	effectivePlanEntity.setId(effectivePlanEntitUUId);
            	effectivePlanEntity.setName(sheetName+"方案");
            	effectivePlanEntity.setDeptRegionId(regionEntity.getId());
            	effectivePlanEntity.setDeptRegionCode(regionEntity.getRegionCode());
            	effectivePlanEntity.setActive(FossConstants.INACTIVE);
            	effectivePlanEntity.setBeginTime(getNextBusinessDate());
            	effectivePlanEntity.setEndTime(getMaxBusinessDate());
            	effectivePlanEntity.setVersionNo(new Date().getTime());
            	effectivePlanEntity.setVersionInfo(new Date().toString());
            	effectivePlanEntity.setCreateDate(new Date());
            	effectivePlanEntity.setCreateUser(getCurrentUserCode());
            	effectivePlanEntity.setCreateOrgCode(getCurrentOrgCode());
            	effectivePlanEntity.setModifyDate(new Date());
            	effectivePlanEntity.setModifyUser(getCurrentUserCode());
            	effectivePlanEntity.setModifyOrgCode(getCurrentOrgCode());
            	effectivePlanDao.insertSelective(effectivePlanEntity);
            } else {
            	throw new EffectivePlanException("没有查询到相应的时效区域", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
            }
            
            int col = 0;
            try {
    			// 加个计数器 count
    			rows.next();// 第一行标题不读
    			if(rows.hasNext()){
    				for (; rows.hasNext();) {
    					Row row = (Row)rows.next();
    					EffectivePlanDetailEntity effectivePlanDetailEntity = new EffectivePlanDetailEntity();
    					String effectivePlanDetailEntitUUId = UUIDUtils.getUUID();
    					effectivePlanDetailEntity.setId(effectivePlanDetailEntitUUId);
    					col = 0;
    					for (Iterator cells = row.cellIterator(); cells.hasNext();) {
    						Cell cell = (Cell)cells.next();
    						switch (cell.getColumnIndex()) {
    							case 0:
    								//获得填写的到达区域
    								col ++;
    								String arrvRegionName = ExcelHandleUtil.obtainStringVal(cell);
    								if(StringUtils.isNotBlank(arrvRegionName)) {
    									List<PriceRegionEntity> arrvRegionEntities = regionService.searchRegionByName(arrvRegionName, PricingConstants.PRESCRIPTION_REGION);
    									if(arrvRegionEntities != null && arrvRegionEntities.size() > 0) {
    										PriceRegionEntity arrvRegionEntity = arrvRegionEntities.get(0);
    										if(arrvRegionEntity != null && arrvRegionEntity.getRegionName().equals(arrvRegionName)) {
    											effectivePlanDetailEntity.setArrvRegionId(arrvRegionEntity.getId());
    											effectivePlanDetailEntity.setArrvRegionCode(arrvRegionEntity.getRegionCode());
    										} else {
            									throw new EffectivePlanException("第"+index+"行，第"+col+"列填写的到达区或名称不存在相应的记录", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    										}
    									} else {
        									throw new EffectivePlanException("第"+index+"行，第"+col+"列填写的到达区或名称不存在相应的记录", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    									}
    								} else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写时效到达区或名称", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    								}
    								break;
    							case 1:
    								//获得填写的产品
    								col ++;
    								String productName = ExcelHandleUtil.obtainStringVal(cell);
    								if(StringUtils.isNotBlank(productName)) {
    									ProductEntity productEntity = new ProductEntity();
    									productEntity.setName(productName);
    									productEntity.setActive(FossConstants.ACTIVE);
    									productEntity.setBeginTime(new Date());
    									ProductEntity productEntity2 = productService.findProductByName(productEntity);
    									if(productEntity2 != null && StringUtils.isNotBlank(productEntity2.getId())) {
    										effectivePlanDetailEntity.setProductId(productEntity2.getId());
    										effectivePlanDetailEntity.setProductCode(productEntity2.getCode());
    									} else {
        									throw new EffectivePlanException("第"+index+"行，第"+col+"列填写的产品名称不存在相应的记录", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    									}
    								} else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写产品名称", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    								}
    								break;
    							case 2:
    								// 承诺最大天数
    								col ++;
    								String promiseMax = ExcelHandleUtil.obtainStringVal(cell);
									log.info("承诺最大天数>>"+promiseMax);
                                    if(StringUtils.isNotBlank(promiseMax)) {
                                    	effectivePlanDetailEntity.setMaxTime(Double.valueOf(promiseMax).intValue());
                                    	effectivePlanDetailEntity.setMaxTimeUnit("DAY");
                                    } else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写承诺最大天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                    }
    								break;
    							case THREE:
    								// 承诺最小天数
    								col ++;
    								String promiseMin = ExcelHandleUtil.obtainStringVal(cell);
    								log.info("承诺最小天数>>"+promiseMin);
                                    if(StringUtils.isNotBlank(promiseMin)) {
                                    	effectivePlanDetailEntity.setMinTime(Double.valueOf(promiseMin).intValue());
                                    	effectivePlanDetailEntity.setMinTimeUnit("DAY");
                                    } else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写承诺最小天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                    }
    								break;
    							case FOUR:
    								// 派送承诺时间
    								col ++;
    								String deleveryTime = ExcelHandleUtil.obtainStringVal(cell);
    								log.info("派送承诺时间>>"+deleveryTime);
                                    if(StringUtils.isNotBlank(deleveryTime)) {
                                    	if(deleveryTime.indexOf(":") > -1) {
                                    		String tempStr = deleveryTime.replaceAll(":", "");
                                    		effectivePlanDetailEntity.setDeliveryTime(tempStr);
                                    	} else {
                                    		effectivePlanDetailEntity.setDeliveryTime(deleveryTime);
                                    	}
                                    }
    								break;
    							case FIVE:
    								// 承诺派送需加天数
    								col ++;
    								String addDay = ExcelHandleUtil.obtainStringVal(cell);
    								log.info("承诺派送需加天数>>"+addDay);
                                    if(StringUtils.isNotBlank(addDay)) {
                                    	effectivePlanDetailEntity.setAddDay(Double.valueOf(addDay).intValue());
                                    } else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写派送承诺需加天数", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                    }
    								break;
    							case SIX:
    								// 是否驻地部门
    								col ++;
    								String encampment = ExcelHandleUtil.obtainStringVal(cell);
    								log.info("是否驻地部门>>"+encampment);
                                    if(StringUtils.isNotBlank(encampment)) {
                                    	effectivePlanDetailEntity.setHasSalesDept(encampment);
                                    } else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写是否驻地部门", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                    }
    								break;
    							case SEVEN:
    								// 到达营业部承诺时间
    								col ++;
    								String arrvDeptTime = ExcelHandleUtil.obtainStringVal(cell);
    								log.info("到达营业部承诺时间>>"+arrvDeptTime);
                                    if(StringUtils.isNotBlank(arrvDeptTime)) {
                                    	if(arrvDeptTime.indexOf(":") > -1) {
                                    		String tempStr = arrvDeptTime.replaceAll(":", "");
                                    		effectivePlanDetailEntity.setArriveTime(tempStr);
                                    	} else {
                                    		effectivePlanDetailEntity.setArriveTime(arrvDeptTime);
                                    	}
                                    }
    								break;
    							case EIGHT:
    								// 长短途
    								col ++;
    								String longOrShort = "";
    								if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
    									longOrShort = cell.getStringCellValue();
    								} else  if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
    									Double d = cell.getNumericCellValue();
    									longOrShort = String.valueOf(d.intValue());
    								}
    								log.info("长短途>>"+longOrShort);
                                    if(StringUtils.isNotBlank(longOrShort)) {
                                    	if(StringUtils.equals(longOrShort, "1")) {
                                    		effectivePlanDetailEntity.setLongOrShort("L");
                                    	} else {
                                    		effectivePlanDetailEntity.setLongOrShort("S");
                                    	}
                                    } else {
    									throw new EffectivePlanException("第"+index+"行，第"+col+"列未填写长短途", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
                                    }
    								break;
    							default :
    								log.info("第"+index+"行，第"+col+"列数据超出预计范围");
    						}
    					}
    					effectivePlanDetailEntity.setActive(FossConstants.INACTIVE);
    					effectivePlanDetailEntity.setDeptRegionId(effectivePlanEntity.getDeptRegionId());
    					effectivePlanDetailEntity.setDeptRegionCode(effectivePlanEntity.getDeptRegionCode());
    					effectivePlanDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
    					effectivePlanDetailDao.insertSelective(effectivePlanDetailEntity);
    					index ++;
    				}
    			}
			} catch(EffectivePlanException ex) {
				throw new EffectivePlanException(ex.getErrorCode(), EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
			} catch (Exception e) {
				throw new EffectivePlanException("第"+index+"行数据出现异常情况", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
			}
		}
	}
	/**
     * @Description: 导出时效数据
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2012-12-25 上午10:44:31
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return
     * 
     * @version V1.0
     */
	public ExportResource  exportEffectivePlanDetailList(EffectivePlanDetailEntity effectivePlanDetailEntity) throws EffectivePlanException{
		if (effectivePlanDetailEntity == null) {
			effectivePlanDetailEntity = new EffectivePlanDetailEntity();
		}
		ExportResource exportResource = new ExportResource();
		List<EffectivePlanDetailEntity> planDetailEntities = effectivePlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
		if (CollectionUtils.isEmpty(planDetailEntities)) {
		    throw new EffectivePlanException("没有数据",null);
		}
		List<List<String>> resultList = new ArrayList<List<String>>();
		for (EffectivePlanDetailEntity entity : planDetailEntities) {
		    List<String> result = exportPlatform(entity);
		    if (CollectionUtils.isEmpty(result)) {
		    	continue;
		    }
		    resultList.add(result);
		}
		
		exportResource.setHeads(PricingColumnConstants.EFFECTIVE_PLAN_DETAIL_TITLE);
		exportResource.setRowList(resultList);
		return exportResource;
	}
	/**
	 * 
	 * @Description: 导出
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-14 下午2:02:32
	 * 
	 * @param effectivePlanDetailEntity
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	 private List<String> exportPlatform(EffectivePlanDetailEntity effectivePlanDetailEntity){
		List<String> result = new ArrayList<String>();
		PriceRegionEntity arrvRegion = regionService.searchRegionByID(effectivePlanDetailEntity.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
		ProductEntity productEntity = productService.findProductById(effectivePlanDetailEntity.getProductId());
		if(arrvRegion != null && StringUtil.equals(effectivePlanDetailEntity.getArrvRegionId(), arrvRegion.getId())) {
			result.add(arrvRegion.getRegionName());
		}
		if(productEntity != null && StringUtil.equals(effectivePlanDetailEntity.getProductId(), productEntity.getId())) {
			result.add(productEntity.getName());
		}
		result.add(effectivePlanDetailEntity.getMinTime().toString());
		result.add(effectivePlanDetailEntity.getMaxTime().toString());
		result.add(effectivePlanDetailEntity.getArriveTime());
		result.add(effectivePlanDetailEntity.getHasSalesDept());
		if(PricingConstants.LONG_OR_SHORT_L.equals(effectivePlanDetailEntity.getLongOrShort())) {
			result.add(PricingConstants.LONG_OR_SHORT_L_NAME);
		} else {
			result.add(PricingConstants.LONG_OR_SHORT_S_NAME);
		}
		result.add(effectivePlanDetailEntity.getDeliveryTime());
		result.add(effectivePlanDetailEntity.getAddDay().toString());
		result.add(effectivePlanDetailEntity.getMark());
		return result;
	}
	/**
	 * 
	 * <p>
	 * 查询单个时效方案对象
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-26 上午9:39:38
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	public EffectivePlanEntity getEffectiveEntityById(String id) {
	    EffectivePlanEntity effectivePlanEntity = effectivePlanDao.selectByPrimaryKey(id);
	    if(null != effectivePlanEntity){
		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(effectivePlanEntity.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
		effectivePlanEntity.setDeptRegionName(priceRegionEntity.getRegionName());
	    }
	    return effectivePlanEntity;
	}
	/**
     * 
     * <p>批量删除</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-1-5 下午8:04:55
     * 
     * @param ids
     * 
     * @see
     */
	@Override
	public void deleteEffectivePlanBatch(List<String> ids) {
	    if(CollectionUtils.isNotEmpty(ids)){
		for(int i = 0; i<ids.size(); i++){
		    String pricePlanId =  ids.get(i);
		    EffectivePlanEntity effectivePlanEntity = effectivePlanDao.selectByPrimaryKey(pricePlanId);
		    if(null!=effectivePlanEntity){
			if(StringUtil.equalsIgnoreCase(effectivePlanEntity.getActive(),FossConstants.ACTIVE)){
			    throw new EffectivePlanException("该时效方案已经激活，不能删除！", EffectivePlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
			}
		    }
		    EffectivePlanDetailEntity effectivePlanDetailEntity = new EffectivePlanDetailEntity();
		    effectivePlanDetailEntity.setEffectivePlanId(pricePlanId);
		    List<EffectivePlanDetailEntity> effectivePlanDetailEntitys = effectivePlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
		    if(CollectionUtils.isNotEmpty(effectivePlanDetailEntitys)){
			 for (EffectivePlanDetailEntity temp : effectivePlanDetailEntitys) {
			     //此处不能使用批量删除，超过1000笔 myBatis 报错
			     effectivePlanDetailDao.deleteByPrimaryKey(temp.getId());
			 }
		    }
		}
		//批量删除时效方案
		effectivePlanDao.deleteEffectivePlanByIds(ids);
	    }
	}
	/**
     * @Description: 中止时效方案
     * 
     * Company:IBM
     * 
     * @author FOSSDP-sz
     * 
     * @date 2013-1-5 下午8:20:58
     * 
     * @param id
     * 
     * @version V1.0
     */
	@Override
	public void terminateEffectivePlan(EffectivePlanEntity voEffectivePlanEntity) {
		EffectivePlanEntity effectivePlanEntity = effectivePlanDao.selectByPrimaryKey(voEffectivePlanEntity.getId());
		Date endDate = voEffectivePlanEntity.getEndTime();
		if(effectivePlanEntity != null) {
    		    	if(endDate == null){
    		    	    throw new EffectivePlanException("截至日期不能为空!",null);
    		    	}
//    		    	if(!voEffectivePlanEntity.getIsPromptly()){
        		    	if(endDate.before(new Date())){
        		    	    throw new EffectivePlanException("方案："+effectivePlanEntity.getName()+"的中止日期必须大于当前营业日期!",null);
        		    	}
//    		    	}
        		if(endDate.after(effectivePlanEntity.getEndTime()))
        		{
        		    throw new PricePlanException("方案："+effectivePlanEntity.getName()+"的中止日期不能延长原方案所制定的活动结束日期!",null);
        		}
			effectivePlanEntity.setEndTime(endDate);
			effectivePlanEntity.setModifyDate(new Date());
			effectivePlanEntity.setModifyOrgCode(getCurrentOrgCode());
			effectivePlanEntity.setModifyUser(getCurrentUserCode());
			effectivePlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
		}
	}
	
	/**
	 * 
	 * @Description: 获得当前部门值
	 * 
	 * Company:IBM
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
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
	 * @author FOSSDP-sz
	 * 
	 * @date 2012-12-25 下午2:50:57
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}
	/**
     * 
     * 立即激活时效方案信息
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-10 下午2:24:05
     */
	@Override
	public void immediatelyActiveEffectivePlan(
		EffectivePlanEntity effectivePlanEntity)throws EffectivePlanException {
	   	Date currentDate = new Date();
		    if(null == effectivePlanEntity){
			throw new PricePlanException("查询不到具体的时效方案信息,请联系管理员查看!", null);
		    }
		    EffectivePlanDetailEntity effectivePlanDetailEntity = new EffectivePlanDetailEntity();
		    effectivePlanDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
		    List<EffectivePlanDetailEntity> effectivePlanDetailEntities = effectivePlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
		    if(CollectionUtils.isNotEmpty(effectivePlanDetailEntities)){
			List<String> detailIds = new ArrayList<String>();
			for (EffectivePlanDetailEntity detail : effectivePlanDetailEntities) {
			    if(null!=detail){
				 //获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
				 List<EffectivePlanDetailEntity> detailList = effectivePlanDetailDao.queryEffectivePlanDetailListByCondition(detail.getDeptRegionId(),detail.getArrvRegionId(),detail.getProductCode(),effectivePlanEntity.getBeginTime());
				 if(CollectionUtils.isNotEmpty(detailList)){
				         EffectivePlanEntity existBean =  effectivePlanDao.selectByPrimaryKey(detail.getEffectivePlanId());
				     	 PriceRegionEntity effectiveDeptRegion = regionService.searchRegionByID(detail.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
				     	 if(null == effectiveDeptRegion){
				     	     throw new EffectivePlanException("缺失始发区域的基础信息， 请联系管理员查看!",null);
				     	 }
				     	 PriceRegionEntity effectiveArrvRegion = regionService.searchRegionByID(detail.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
				     	 if(null == effectiveArrvRegion){
				     	     throw new EffectivePlanException("缺失目的地区域基础信息， 请联系管理员查看!",null);
				     	 }
				     	 ProductEntity productEntity = productService.getProductByCache(detail.getProductCode(), currentDate);
				     	 if(null == productEntity){
				     	     throw new EffectivePlanException("缺失产品的基础信息， 请联系管理员查看!",null);
				     	 }
					 StringBuilder errStr = new StringBuilder();
					 errStr.append("始发区域[");
					 errStr.append(effectiveDeptRegion.getRegionName());
					 errStr.append("],目的地区域[");
					 errStr.append(effectiveArrvRegion.getRegionName());
					 errStr.append("],产品[");
					 errStr.append(productEntity.getName());
					 errStr.append("],在时效方案名称为[");
					 errStr.append(existBean.getName());
					 errStr.append("],下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止"+existBean.getName()+"价格方案!");
					 throw new EffectivePlanException(errStr.toString(), null);
				 }else{
				     	detailIds.add(detail.getId());
				 }
			    } 
			}
			   //激活明细
			    effectivePlanDetailDao.activeEffectivePlanDetailByIds(detailIds);
			    effectivePlanEntity.setActive(FossConstants.ACTIVE);
			  //激活方案
			    effectivePlanEntity.setVersionNo(new Date().getTime());
			    effectivePlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
		    }else{
			throw new PricePlanException(effectivePlanEntity.getName()+"方案明细为空不能激活!", null);
		    }
	}
    
	/**
	 * author lianghaisheng
	 * 功能:批量终止方案
	 * */
	@Override
	@Transactional
	public void bathterminateEffectivePlan(List<EffectivePlanEntity> list) {
		if(CollectionUtils.isNotEmpty(list)){
			for(int i=0;i<list.size();i++){
				this.terminateEffectivePlan(list.get(i));
			}
		}
	}
}












































/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


