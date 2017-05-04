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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectiveExpressPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEffectiveExpressPlanDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEffectiveExpressPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.server.util.ExcelHandleUtil;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.EffectiveExpressPlanException;
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
public class EffectiveExpressPlanService implements IEffectiveExpressPlanService{
	
	private static final int THREE = 3;
	private static final int FOUR =4;
	private static final int FIVE = 5;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int EIGHT = 8;

	private static final int NUMBER_1000 = 1000;
	private static final int NUMBER_2999 = 2999;
	
	Logger log = Logger.getLogger(getClass());
	/**
	 * 时效方案DAO
	 */
    @Inject
    IEffectiveExpressPlanDao effectiveExpressPlanDao;
    /**
	 * 时效方案明细DAO
	 */
    @Inject
    IEffectiveExpressPlanDetailDao effectiveExpressPlanDetailDao;
    /**
	 * 产品SERVICE
	 */
    @Inject
    IProductService productService;
    /**
	 * 区域SERVICE
	 */
    @Inject
    IRegionExpressService regionExpressService;
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
     * 设置 产品SERVICE.
     *
     * @param productService the new 产品SERVICE
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    
    /**
     * 设置 时效方案DAO.
     *
     * @param effectiveExpressPlanDao the new 时效方案DAO
     */
    public void setEffectiveExpressPlanDao(
			IEffectiveExpressPlanDao effectiveExpressPlanDao) {
		this.effectiveExpressPlanDao = effectiveExpressPlanDao;
	}
    /**
     * 设置 时效方案明细DAO.
     *
     * @param effectiveExpressPlanDetailDao the new 时效方案明细DAO
     */
	public void setEffectiveExpressPlanDetailDao(
			IEffectiveExpressPlanDetailDao effectiveExpressPlanDetailDao) {
		this.effectiveExpressPlanDetailDao = effectiveExpressPlanDetailDao;
	}
    /**
     * 设置 区域SERVICE.
     *
     * @param regionExpressService the new 区域SERVICE
     */
	public void setRegionExpressService(IRegionExpressService regionExpressService) {
		this.regionExpressService = regionExpressService;
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
    public EffectiveExpressPlanEntity findEffectiveExpressPlanByRegionId(Date cuurentTime, String active,String regionId) throws EffectiveExpressPlanException{
	return effectiveExpressPlanDao.findEffectivePlanByRegionId(cuurentTime,active,regionId);
    }

    /**
     * 
     * 时效方案新增
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-10-25 下午6:56:48
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IEffectivePlanService#insertEffectivePlanEntity(com.deppon.foss.module.pickup.pricing.api.shared.domain.EffectiveExpressPlanEntity)
     */
    @Override
    public int insertEffectiveExpressPlanEntity(EffectiveExpressPlanEntity effectivePlanEntity) throws EffectiveExpressPlanException{
	int valRt = -1;
	if(null != effectivePlanEntity){
	    //判断输入参数是否符合业务要求
	    if(null == effectivePlanEntity.getDeptRegionCode()){
		    throw new EffectiveExpressPlanException("方案信息不能为空!",EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
	    }
	    Date currentTime = new Date();
	    if(effectivePlanEntity.getBeginTime().before(currentTime)){
		throw new EffectiveExpressPlanException("开始时间必须大于当前营业日期!", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
	    }
	    //查询当前方案名称是否已经存在了。如果存在则提示重复信息
	    EffectiveExpressPlanEntity queryEffectivePlanEntity = new EffectiveExpressPlanEntity(); 
	    queryEffectivePlanEntity.setName(effectivePlanEntity.getName());
	    List<EffectiveExpressPlanEntity> effectivePlanEntities =  effectiveExpressPlanDao.searchEffectivePlanByName(queryEffectivePlanEntity);
	    if(CollectionUtils.isNotEmpty(effectivePlanEntities)){
		throw new EffectiveExpressPlanException(effectivePlanEntity.getName()+"方案已经存在，不可录入！",EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
	    }
	    //设置 结束时间， uuid，有效标记，创建时间，修改时间
	    effectivePlanEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	    String effectivePlanId = UUIDUtils.getUUID();
	    effectivePlanEntity.setId(effectivePlanId);
	    effectivePlanEntity.setActive(FossConstants.INACTIVE);
	    effectivePlanEntity.setVersionNo(new Date().getTime());
	    effectivePlanEntity.setCreateDate(currentTime);
	    effectivePlanEntity.setModifyDate(currentTime);
	    effectivePlanEntity.setDescription(effectivePlanEntity.getUpdateDesTemp());
	    //数据库持久化时效方案信息
	    valRt = effectiveExpressPlanDao.insertSelective(effectivePlanEntity);
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
    public int activeEffectiveExpressPlan(List<String> effectivePlans)throws EffectiveExpressPlanException {
	Date currentDate = new Date();
	int valRt = 0;
	for (String key : effectivePlans) {
	    EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanDao.selectByPrimaryKey(key);
	    if(null == effectivePlanEntity){
		throw new EffectiveExpressPlanException("根据方案ID"+key+"查询不到具体的时效方案信息,请联系管理员查看!", null);
	    }
	    Date beginTime = effectivePlanEntity.getBeginTime();
	    if(currentDate.after(beginTime)){
			throw new EffectiveExpressPlanException(effectivePlanEntity.getName()+"方案的生效日期为"+ DateUtils.convert(beginTime, DateUtils.DATE_TIME_FORMAT
			+" 需要大于当前营业日期才能被激活!"), null);
	    }
	    EffectiveExpressPlanDetailEntity effectivePlanDetailEntity = new EffectiveExpressPlanDetailEntity();
	    effectivePlanDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
	    List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntities = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
	    if(CollectionUtils.isNotEmpty(effectivePlanDetailEntities)){
		//用于记录时效明细的集合
		List<String> detailIds = new ArrayList<String>();
		//用于记录异常信息
		StringBuilder errStr = new StringBuilder();
		//迭代时效明细信息，并判断是否可以正常激活
		for (EffectiveExpressPlanDetailEntity detail : effectivePlanDetailEntities) {
		    if(null!=detail){
			 //获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
			 List<EffectiveExpressPlanDetailEntity> detailList = effectiveExpressPlanDetailDao.queryEffectivePlanDetailListByCondition(detail.getDeptRegionId(),detail.getArrvRegionId(),detail.getProductCode(),effectivePlanEntity.getBeginTime());
			 if(CollectionUtils.isNotEmpty(detailList)){
			         EffectiveExpressPlanEntity existBean =  effectiveExpressPlanDao.selectByPrimaryKey(detail.getEffectivePlanId());
			     	 PriceRegionExpressEntity effectiveDeptRegion = regionExpressService.searchRegionByID(detail.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
			     	 if(null == effectiveDeptRegion){
			     	     throw new EffectiveExpressPlanException("缺失始发区域的基础信息， 请联系管理员查看!",null);
			     	 }
			     	 PriceRegionExpressEntity effectiveArrvRegion = regionExpressService.searchRegionByID(detail.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
			     	 if(null == effectiveArrvRegion){
			     	     throw new EffectiveExpressPlanException("缺失目的地区域基础信息， 请联系管理员查看!",null);
			     	 }
			     	 ProductEntity productEntity = productService.getProductByCache(detail.getProductCode(), currentDate);
			     	 if(null == productEntity){
			     	     throw new EffectiveExpressPlanException("缺失产品的基础信息， 请联系管理员查看!",null);
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
				 throw new EffectiveExpressPlanException(errStr.toString(), null);
			 }else{
			     	//累计有效的时效明细ID
			     	detailIds.add(detail.getId());
			 }
		    } 
		}
		//由于oracle10g中  in条件的参数中个数不能大于1000  所以需要每次激活1000分批激活明细
		if(CollectionUtils.isNotEmpty(detailIds)){
			 int size= detailIds.size();
			 int sizeSub = NUMBER_1000;
			 //总循环次数
	         int num = size% sizeSub == 0 ? (size / sizeSub) : (size / sizeSub + 1);
	         int start=0;
	         int end  =0;
	         List<String> detailIdSub = new ArrayList<String>();
	         
	         for(int i=1;i<=num;i++){
	          start=(i-1)*sizeSub;
	          end=i*sizeSub> size ? size:i*sizeSub;
	          detailIdSub = detailIds.subList(start, end);
	          effectiveExpressPlanDetailDao.activeEffectivePlanDetailByIds(detailIdSub);
	         }
		}
		    //设置方案状态
		    effectivePlanEntity.setActive(FossConstants.ACTIVE);
		    effectivePlanEntity.setVersionNo(new Date().getTime());
		    //激活方案
		    valRt = effectiveExpressPlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
	    }else{
		throw new EffectiveExpressPlanException(effectivePlanEntity.getName()+"方案明细为空不能激活!", null);
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
	public List<EffectiveExpressPlanEntity> searchEffectiveExpressByCondition(EffectiveExpressPlanEntity effectivePlanEntity, int start,
			int limit) throws EffectiveExpressPlanException {
		if (PricingConstants.ALL.equals(effectivePlanEntity.getActive())) {
			effectivePlanEntity.setActive(null);
		}
		List<EffectiveExpressPlanEntity> effectivePlanEntities = effectiveExpressPlanDao.searchEffectivePlanByCondition(
				effectivePlanEntity, start, limit);
		List<EffectiveExpressPlanEntity> list = new ArrayList<EffectiveExpressPlanEntity>();
		for (EffectiveExpressPlanEntity temp : effectivePlanEntities) {
			if(StringUtil.isNotEmpty(temp.getModifyUser())){
				EmployeeEntity emp = employeeService.queryEmployeeByEmpCode(temp.getModifyUser());			
				if (null != emp) {
					temp.setModifyUserName(emp.getEmpName());
				}
			}
			PriceRegionExpressEntity priceRegionEntity = regionExpressService.searchRegionByID(temp.getDeptRegionId(),
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
    public Long searchEffectiveExpressByConditionCount(
	    EffectiveExpressPlanEntity effectivePlanEntity)throws EffectiveExpressPlanException {
	return effectiveExpressPlanDao.searchEffectivePlanByConditionCount(effectivePlanEntity);
    }

    /**
     * 
     * queryEffectivePlanDetailInfo(查询时效明细信息)
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return 
     * 
     * List<EffectiveExpressPlanDetailEntity>
     * 
     * @exception 
     * 
     * @since  1.0.0
     */
    @Override
    public List<EffectiveExpressPlanDetailEntity> queryEffectiveExpressPlanDetailInfo(
	    EffectiveExpressPlanDetailEntity effectivePlanDetailEntity)throws EffectiveExpressPlanException {
	List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntities = effectiveExpressPlanDetailDao.queryEffectivePlanDetailInfo(effectivePlanDetailEntity);
	Date currentDateTime =  new Date();
	List<EffectiveExpressPlanDetailEntity> effectivePlanDetailList = null;
	//根据时效明细结果集中的产品CODE与区域ID分别查询产品名称、区域名称
	if(CollectionUtils.isNotEmpty(effectivePlanDetailEntities)){
	    effectivePlanDetailList = new ArrayList<EffectiveExpressPlanDetailEntity>();
	    for (EffectiveExpressPlanDetailEntity effectivePlanDetail : effectivePlanDetailEntities) {
		String productCode = effectivePlanDetail.getProductCode();
		String arrvregionId = effectivePlanDetail.getArrvRegionId();
		ProductEntity productEntity = productService.getProductByCache(productCode,currentDateTime);
		PriceRegionExpressEntity priceRegionEntity = regionExpressService.searchRegionByID(arrvregionId, PricingConstants.PRESCRIPTION_REGION);
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
     * List<EffectiveExpressPlanDetailEntity>
     * 
     * @exception 
     * 
     * @since  1.0.0
     */
    @Override
    public List<EffectiveExpressPlanDetailEntity> queryEffectiveExpressPlanDetailInfoPagging(
	    EffectiveExpressPlanDetailEntity effectivePlanDetailEntity, int start,
	    int limit) throws EffectiveExpressPlanException{
	
	List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntities =  effectiveExpressPlanDetailDao.queryEffectivePlanDetailInfoPagging(effectivePlanDetailEntity, start, limit);
	List<EffectiveExpressPlanDetailEntity> effectiveArrayList = new ArrayList<EffectiveExpressPlanDetailEntity>();
	
	for (EffectiveExpressPlanDetailEntity temp : effectivePlanDetailEntities) {
		PriceRegionExpressEntity priceRegionEnntiy = regionExpressService.searchRegionByID(temp.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
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
    public Long queryEffectiveExpressPlanDetailInfoPaggingCount(
	    EffectiveExpressPlanDetailEntity effectivePlanDetailEntity) {
	return effectiveExpressPlanDetailDao.queryEffectivePlanDetailInfoPaggingCount(effectivePlanDetailEntity);
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
    public void modifyEffectiveExpressPlan(
			EffectiveExpressPlanEntity effectivePlanEntity) {
        	effectivePlanEntity.setModifyDate(new Date());
        	Date currentDate = new Date();
        	OrgAdministrativeInfoEntity currentDept  = FossUserContext.getCurrentDept();
        	UserEntity currentUser = FossUserContext.getCurrentUser();
        	effectivePlanEntity.setModifyOrgCode(currentDept.getCode());
        	effectivePlanEntity.setModifyUser(currentUser.getEmployee().getEmpCode());
        	effectivePlanEntity.setVersionInfo(String.valueOf(currentDate.getTime()));
        	effectivePlanEntity.setVersionNo(currentDate.getTime());
        	effectivePlanEntity.setDescription(effectivePlanEntity.getUpdateDesTemp());
            effectiveExpressPlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
            EffectiveExpressPlanDetailEntity planDetailEntity = new EffectiveExpressPlanDetailEntity();
            planDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
            List<EffectiveExpressPlanDetailEntity> planDetailEntities = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(planDetailEntity);
            if(CollectionUtils.isNotEmpty(planDetailEntities)) {
            	for (EffectiveExpressPlanDetailEntity effectivePlanDetailEntity : planDetailEntities) {
            		effectivePlanDetailEntity.setDeptRegionId(effectivePlanEntity.getDeptRegionId());
            		effectivePlanDetailEntity.setVersionNo(currentDate.getTime());
            		effectiveExpressPlanDetailDao.updateByPrimaryKeySelective(effectivePlanDetailEntity);
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
     * @return EffectiveExpressPlanEntity
     * 
     * @version V1.0
     */
	@Override
	public EffectiveExpressPlanEntity copyEffectiveExpressPlan(String effectivePlanId) throws EffectiveExpressPlanException{
		EffectiveExpressPlanEntity voEffectivePlanEntity = null;
		if(effectivePlanId != null && !"".equals(effectivePlanId)) {
			EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanDao.selectByPrimaryKey(effectivePlanId);
			if(effectivePlanEntity != null && effectivePlanEntity.getId() != null && StringUtil.equals(effectivePlanEntity.getId(), effectivePlanId)) {
			    	voEffectivePlanEntity = new EffectiveExpressPlanEntity();
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
				effectiveExpressPlanDao.insertSelective(voEffectivePlanEntity);
				
				//批量处理
				effectiveExpressPlanDetailDao.insertBatchEffectivePlanDetail(effectivePlanId, newUUId);
				
//				EffectiveExpressPlanDetailEntity entity = new EffectiveExpressPlanDetailEntity();
//				entity.setEffectivePlanId(effectivePlanId);
//				List<EffectiveExpressPlanDetailEntity> planDetailEntities = effectiveExpressPlanDetailDao.queryEffectivePlanDetailInfo(entity);
//				if(planDetailEntities != null && planDetailEntities.size() > 0) {
//					for (int i = 0; i < planDetailEntities.size(); i++) {
//						EffectiveExpressPlanDetailEntity planDetailEntity = planDetailEntities.get(i);
//						if(planDetailEntity != null) {
//							EffectiveExpressPlanDetailEntity planDetailEntity2 = new EffectiveExpressPlanDetailEntity();
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
//							effectiveExpressPlanDetailDao.insertSelective(planDetailEntity2);
//						}
//					}
//				}
			} else {
				throw new EffectiveExpressPlanException("没有获得该时效方案", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
        	cal.set(NUMBER_2999, NumberConstants.NUMBER_11, NumberConstants.NUMBER_31);
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
	public void importEffectiveExpressPlanDetail(Workbook book) {
		Integer index = 1;
		Workbook workbook = book;
    	int sheetNum = workbook.getNumberOfSheets();
//    	checkRepeat(workbook,sheetNum);
    	for (int i = 0; i < sheetNum; i++) {
    		Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            EffectiveExpressPlanEntity effectivePlanEntity = null;
            List<PriceRegionExpressEntity> regionEntities = regionExpressService.searchRegionByName(sheetName, PricingConstants.PRESCRIPTION_REGION);
            if(regionEntities != null && regionEntities.size() > 0) {
            	PriceRegionExpressEntity regionEntity = regionEntities.get(0);
            	effectivePlanEntity = new EffectiveExpressPlanEntity();
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
            	effectiveExpressPlanDao.insertSelective(effectivePlanEntity);
            } else {
            	throw new EffectiveExpressPlanException("没有查询到相应的时效区域", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
            }
            
            int col = 0;
            try {
            	Iterator rows = sheet.rowIterator();
    			// 加个计数器 count
    			rows.next();// 第一行标题不读
    			Map<String, String> checkdestRegionNameMap = new HashMap<String, String>();
    			if(rows.hasNext()){
    				for (; rows.hasNext();) {
    					Row row = (Row)rows.next();
    					EffectiveExpressPlanDetailEntity effectivePlanDetailEntity = new EffectiveExpressPlanDetailEntity();
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
        								if (checkdestRegionNameMap.get(arrvRegionName) == null) {
        								    checkdestRegionNameMap.put(arrvRegionName, arrvRegionName);// 把目的地放入map，作为目的地只能有一条的判断数据源
        								} else {
        								    throw new EffectiveExpressPlanException("目的地:" + arrvRegionName + " 重复，请检查数据", null);
    
        								}
    									List<PriceRegionExpressEntity> arrvRegionEntities = regionExpressService.searchRegionByName(arrvRegionName, PricingConstants.PRESCRIPTION_REGION);
    									if(arrvRegionEntities != null && arrvRegionEntities.size() > 0) {
    										PriceRegionExpressEntity arrvRegionEntity = arrvRegionEntities.get(0);
    										if(arrvRegionEntity != null && arrvRegionEntity.getRegionName().equals(arrvRegionName)) {
    											effectivePlanDetailEntity.setArrvRegionId(arrvRegionEntity.getId());
    											effectivePlanDetailEntity.setArrvRegionCode(arrvRegionEntity.getRegionCode());
    										} else {
            									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列填写的到达区或名称不存在相应的记录", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    										}
    									} else {
        									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列填写的到达区或名称不存在相应的记录", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    									}
    								} else {
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写时效到达区或名称", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    								}
    								break;
    							case 1:
    								//获得填写的产品
    								col ++;
    								String productName = ExcelHandleUtil.obtainStringVal(cell);
    								
    								if(StringUtils.isNotBlank(productName)) {
//    									if(!StringUtils.equals(FossConstants.PACKAGE_NAME, productName)){
//    										throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列，产品名称:"+productName+",应为:"+FossConstants.PACKAGE_NAME, EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
//    									}
    									ProductEntity productEntity = new ProductEntity();
    									productEntity.setName(productName);
    									productEntity.setActive(FossConstants.ACTIVE);
    									productEntity.setBeginTime(new Date());
    									ProductEntity productEntity2 = productService.findProductByName(productEntity);
    									if(productEntity2 != null && StringUtils.isNotBlank(productEntity2.getId())) {
    										effectivePlanDetailEntity.setProductId(productEntity2.getId());
    										effectivePlanDetailEntity.setProductCode(productEntity2.getCode());
    									} else {
        									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列填写的产品名称不存在相应的记录", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
    									}
    								} else {
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写产品名称", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写承诺最大天数", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写承诺最小天数", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写派送承诺需加天数", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写是否驻地部门", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
    									throw new EffectiveExpressPlanException("第"+index+"行，第"+col+"列未填写长短途", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
    					effectiveExpressPlanDetailDao.insertSelective(effectivePlanDetailEntity);
    					index ++;
    				}
    			}
			} catch(EffectiveExpressPlanException ex) {
				throw new EffectiveExpressPlanException(ex.getErrorCode(), EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
			} catch (Exception e) {
				throw new EffectiveExpressPlanException("第"+index+"行数据出现异常情况", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
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
	public ExportResource  exportEffectiveExpressPlanDetailList(EffectiveExpressPlanDetailEntity effectivePlanDetailEntity) throws EffectiveExpressPlanException{
		if (effectivePlanDetailEntity == null) {
			effectivePlanDetailEntity = new EffectiveExpressPlanDetailEntity();
		}
		ExportResource exportResource = new ExportResource();
		List<List<String>> resultList = new ArrayList<List<String>>();
		List<EffectiveExpressPlanDetailEntity> planDetailEntities = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
		if (CollectionUtils.isEmpty(planDetailEntities)) {
			//    DEAP-40项目环境，快递时效管理中，任意选择一条价格方案，点击导出，系统报错
//		    throw new EffectiveExpressPlanException("没有数据",null);
			exportResource.setHeads(PricingColumnConstants.EFFECTIVE_PLAN_DETAIL_TITLE);
			exportResource.setRowList(resultList);
			return exportResource;
		}
		for (EffectiveExpressPlanDetailEntity entity : planDetailEntities) {
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
	 private List<String> exportPlatform(EffectiveExpressPlanDetailEntity effectivePlanDetailEntity){
		List<String> result = new ArrayList<String>();
		PriceRegionExpressEntity arrvRegion = regionExpressService.searchRegionByID(effectivePlanDetailEntity.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
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
		result.add(effectivePlanDetailEntity.getDescription());
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
	public EffectiveExpressPlanEntity getEffectiveExpressEntityById(String id) {
	    EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanDao.selectByPrimaryKey(id);
	    if(null != effectivePlanEntity){
		PriceRegionExpressEntity priceRegionEntity = regionExpressService.searchRegionByID(effectivePlanEntity.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
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
	public void deleteEffectiveExpressPlanBatch(List<String> ids) {
	    if(CollectionUtils.isNotEmpty(ids)){
		for(int i = 0; i<ids.size(); i++){
		    String pricePlanId =  ids.get(i);
		    EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanDao.selectByPrimaryKey(pricePlanId);
		    if(null!=effectivePlanEntity){
			if(StringUtil.equalsIgnoreCase(effectivePlanEntity.getActive(),FossConstants.ACTIVE)){
			    throw new EffectiveExpressPlanException("该时效方案已经激活，不能删除！", EffectiveExpressPlanException.EFFECTIVE_PLAN_CHECK_PARAMETER_ERROR_CODE);
			}
		    }
		    EffectiveExpressPlanDetailEntity effectivePlanDetailEntity = new EffectiveExpressPlanDetailEntity();
		    effectivePlanDetailEntity.setEffectivePlanId(pricePlanId);
		    List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntitys = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
		    if(CollectionUtils.isNotEmpty(effectivePlanDetailEntitys)){
			 for (EffectiveExpressPlanDetailEntity temp : effectivePlanDetailEntitys) {
			     //此处不能使用批量删除，超过1000笔 myBatis 报错
			     effectiveExpressPlanDetailDao.deleteByPrimaryKey(temp.getId());
			 }
		    }
		}
		//批量删除时效方案
		effectiveExpressPlanDao.deleteEffectivePlanByIds(ids);
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
	public void terminateEffectiveExpressPlan(EffectiveExpressPlanEntity voEffectivePlanEntity) {
		EffectiveExpressPlanEntity effectivePlanEntity = effectiveExpressPlanDao.selectByPrimaryKey(voEffectivePlanEntity.getId());
		Date endDate = voEffectivePlanEntity.getEndTime();
		if(effectivePlanEntity != null) {
    		    	if(endDate == null){
    		    	    throw new EffectiveExpressPlanException("截至日期不能为空!",null);
    		    	}
//    		    	if(!voEffectivePlanEntity.getIsPromptly()){
        		    	if(endDate.before(new Date())){
        		    	    throw new EffectiveExpressPlanException("中止日期必须大于当前营业日期!",null);
        		    	}
//    		    	}
        		if(endDate.after(effectivePlanEntity.getEndTime()))
        		{
        		    throw new EffectiveExpressPlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
        		}
			effectivePlanEntity.setEndTime(endDate);
			effectivePlanEntity.setModifyDate(new Date());
			effectivePlanEntity.setModifyOrgCode(getCurrentOrgCode());
			effectivePlanEntity.setModifyUser(getCurrentUserCode());
			effectiveExpressPlanDao.updateByPrimaryKeySelective(effectivePlanEntity);
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
	public void immediatelyActiveEffectiveExpressPlan(
			List<String> activeEffectiveExpressPlanIds,Date beginTime)throws EffectiveExpressPlanException {
		   	Date currentDate = new Date();
		    if(activeEffectiveExpressPlanIds==null||activeEffectiveExpressPlanIds.size()<0){
			throw new EffectiveExpressPlanException("查询不到具体的时效方案信息,请联系管理员查看!", null);
		    }
	    	if(beginTime == null){
	    	    throw new EffectiveExpressPlanException("生效日期不能为空!",null);
	    	}
	    	if(currentDate.after(beginTime)){
	    	    throw new EffectiveExpressPlanException("立即激活时间必须大于当前日期!",null);
	    	}
	    	//采用循环的方式批量激活方案  
	    	for(String effectivePlanId:activeEffectiveExpressPlanIds){
	    		EffectiveExpressPlanEntity activeEffectiveEntity  = new EffectiveExpressPlanEntity();
	    		activeEffectiveEntity.setId(effectivePlanId);
	    		activeEffectiveEntity.setBeginTime(beginTime);
	    		//判断是否存在与当前方案重复方案
			    checkSamePlan(activeEffectiveEntity);

			    EffectiveExpressPlanDetailEntity effectivePlanDetailEntity = new EffectiveExpressPlanDetailEntity();
			    effectivePlanDetailEntity.setEffectivePlanId(activeEffectiveEntity.getId());
			    List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntities = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntity);
			    if(CollectionUtils.isNotEmpty(effectivePlanDetailEntities)){
				List<String> detailIds = new ArrayList<String>();
				for (EffectiveExpressPlanDetailEntity detail : effectivePlanDetailEntities) {
				    if(null!=detail){
					 //获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
					 List<EffectiveExpressPlanDetailEntity> detailList = effectiveExpressPlanDetailDao.queryEffectivePlanDetailListByCondition(detail.getDeptRegionId(),detail.getArrvRegionId(),detail.getProductCode(),activeEffectiveEntity.getBeginTime());
					 if(CollectionUtils.isNotEmpty(detailList)){
					         EffectiveExpressPlanEntity existBean =  effectiveExpressPlanDao.selectByPrimaryKey(detail.getEffectivePlanId());
					     	 PriceRegionExpressEntity effectiveDeptRegion = regionExpressService.searchRegionByID(detail.getDeptRegionId(), PricingConstants.PRESCRIPTION_REGION);
					     	 if(null == effectiveDeptRegion){
					     	     throw new EffectiveExpressPlanException("缺失始发区域的基础信息， 请联系管理员查看!",null);
					     	 }
					     	 PriceRegionExpressEntity effectiveArrvRegion = regionExpressService.searchRegionByID(detail.getArrvRegionId(), PricingConstants.PRESCRIPTION_REGION);
					     	 if(null == effectiveArrvRegion){
					     	     throw new EffectiveExpressPlanException("缺失目的地区域基础信息， 请联系管理员查看!",null);
					     	 }
					     	 ProductEntity productEntity = productService.getProductByCache(detail.getProductCode(), currentDate);
					     	 if(null == productEntity){
					     	     throw new EffectiveExpressPlanException("缺失产品的基础信息， 请联系管理员查看!",null);
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
						 throw new EffectiveExpressPlanException(errStr.toString(), null);
					 }else{
					     	detailIds.add(detail.getId());
					 }
				    } 
				}
				//由于oracle10g中  in条件的参数中个数不能大于1000  所以需要每次激活1000分批激活明细
				if(CollectionUtils.isNotEmpty(detailIds)){
					 int size= detailIds.size();
					 int sizeSub = NUMBER_1000;
					 //总循环次数
			         int num = size% sizeSub == 0 ? (size / sizeSub) : (size / sizeSub + 1);
			         int start=0;
			         int end  =0;
			         List<String> detailIdSub = new ArrayList<String>();
			         
			         for(int i=1;i<=num;i++){
				          start=(i-1)*sizeSub;
				          end=i*sizeSub> size ? size:i*sizeSub;
				          detailIdSub = detailIds.subList(start, end);
				          effectiveExpressPlanDetailDao.activeEffectivePlanDetailByIds(detailIdSub);
			         }
				}
				
				activeEffectiveEntity.setActive(FossConstants.ACTIVE);
				//激活方案
				activeEffectiveEntity.setVersionNo(new Date().getTime());
				effectiveExpressPlanDao.updateByPrimaryKeySelective(activeEffectiveEntity);
			    }else{
				throw new EffectiveExpressPlanException(activeEffectiveEntity.getName()+"方案明细为空不能激活!", null);
			    }
	    		}
	}

	/**
	 * 激活条件校验
	 * 不能存在相同方案 始发区域，到达区域，产品 时间 不能有交集
	 * @param effectivePlanEntity
	 */
	private void checkSamePlan(EffectiveExpressPlanEntity effectivePlanEntity) {
	    /**
	     * 查看已经存在的相同始发区域id
	     */
		effectivePlanEntity = effectiveExpressPlanDao.selectByPrimaryKey(effectivePlanEntity.getId());
	    EffectiveExpressPlanDetailEntity effectivePlanDetailEntityOld = new EffectiveExpressPlanDetailEntity();
	    EffectiveExpressPlanEntity effectiveExpressPlanEntity = new EffectiveExpressPlanEntity();
	    effectiveExpressPlanEntity.setDeptRegionCode(effectivePlanEntity.getDeptRegionCode());
	    effectiveExpressPlanEntity.setDeptRegionId(effectivePlanEntity.getDeptRegionId());
	    effectiveExpressPlanEntity.setActive(FossConstants.ACTIVE);
	    effectiveExpressPlanEntity.setBeginTime(effectivePlanEntity.getBeginTime());//结束时间大于激活的生效时间
	    
	    List<EffectiveExpressPlanEntity> effectivePlanEntityOldList = effectiveExpressPlanDao.searchEffectivePlanOldList(effectiveExpressPlanEntity);
	    List<String> oldArrAreaCode = new ArrayList<String>();
	    List<String> activeArrAreaCode = new ArrayList<String>();
	    if(null!=effectivePlanEntityOldList&&effectivePlanEntityOldList.size()>0){
	    	List<EffectiveExpressPlanDetailEntity> allOldEffectiveExpressPlanDetailEntity = new ArrayList<EffectiveExpressPlanDetailEntity>();
	    	for(EffectiveExpressPlanEntity  effectivePlanEntityOld : effectivePlanEntityOldList ){
	        	effectivePlanDetailEntityOld.setActive(FossConstants.ACTIVE);
		    	effectivePlanDetailEntityOld.setEffectivePlanId(effectivePlanEntityOld.getId());
		    	List<EffectiveExpressPlanDetailEntity> effectivePlanDetailEntityOldList = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(effectivePlanDetailEntityOld);
		    	allOldEffectiveExpressPlanDetailEntity.addAll(effectivePlanDetailEntityOldList);
	    	}
	    	//获取全部到达该方案的到达区域code
	    	for(EffectiveExpressPlanDetailEntity entity:allOldEffectiveExpressPlanDetailEntity){
	    		//将到达区域和产品ID一起作为判断是否重复的标准
	    		oldArrAreaCode.add(entity.getArrvRegionCode()+entity.getProductId());
	    	}
	    	if(oldArrAreaCode.size()<1){
	    		return;
	    	}else{
	    		EffectiveExpressPlanDetailEntity activeEffectivePlanDetailEntity = new EffectiveExpressPlanDetailEntity();
	    		if(null==effectivePlanEntity.getId()){
	    			return;
	    		}
	    		activeEffectivePlanDetailEntity.setEffectivePlanId(effectivePlanEntity.getId());
	    		List<EffectiveExpressPlanDetailEntity> activeEffectivePlanDetailEntityList = effectiveExpressPlanDetailDao.queryEffectivePlanDetailByCondition(activeEffectivePlanDetailEntity);
	    		
		    	for(EffectiveExpressPlanDetailEntity entity:activeEffectivePlanDetailEntityList){
		    		//将到达区域和产品ID一起作为判断是否重复的标准
		    		activeArrAreaCode.add(entity.getArrvRegionCode()+entity.getProductId());
		    	}
	    	}
	    	for(String oldEntity :oldArrAreaCode){
	    		for(String activeEntity :activeArrAreaCode){
	    			if(oldEntity.equals(activeEntity)){
	    				throw new EffectiveExpressPlanException(effectivePlanEntity.getName()+"存在相同的记录,不能立即激活!",null);
	    			}
	    		}
	    	}
	    }
	}
	
	/**
	 * 用于验证重复数据 
	 *
	 */
	class RepeatEntity{
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((longOrShort == null) ? 0 : longOrShort.hashCode());
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result
					+ ((productName == null) ? 0 : productName.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RepeatEntity other = (RepeatEntity) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (longOrShort == null) {
				if (other.longOrShort != null)
					return false;
			} else if (!longOrShort.equals(other.longOrShort))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (productName == null) {
				if (other.productName != null)
					return false;
			} else if (!productName.equals(other.productName))
				return false;
			return true;
		}
		String name;
		String productName;
		String longOrShort;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getProductName() {
			return productName;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public String getLongOrShort() {
			return longOrShort;
		}
		public void setLongOrShort(String longOrShort) {
			this.longOrShort = longOrShort;
		}
		private EffectiveExpressPlanService getOuterType() {
			return EffectiveExpressPlanService.this;
		}
	}
}