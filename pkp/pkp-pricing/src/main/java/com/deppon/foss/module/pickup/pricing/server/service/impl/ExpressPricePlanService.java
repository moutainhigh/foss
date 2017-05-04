/**
 * 
 * 
 * SR1	
 * 
 * 弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
 *  
 *出发信息包括：
 *
 * a) 生效日期: 
 * 
 * 设定生效日期在提交的时候判断该新建的版本信息以出发地作为标准来判断是否已经存在，
 * 
 * 没有存在则正常录入且截止日期默认为2999-01-01，
 * 
 * 生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况），
 * 
 *  如果存在则是需要对原有数据作变更的概念，
 *  
 *  需要校验生效日期在上一个最新启用的版本中
 *  
 *  （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
 *  
 *  比对生效日期必须大于上一个版本生效日期。
 *  
 *b) 始发区域: 
 *
 *区域信息来自产品基础数据维护下的区域来填充该下拉表请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
 *
 *（区域分别有时效与价格，在此只需要获取价格区域）        
 *
 *   c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
 *   
 *   D) 方案描述：  对建立新方案的一些描述信息。
 *   
 *目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为。
 *
 *每一笔明细保存都会记录在数据库中以确保数据的完整性。
 *
 *e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
 *
 *SR2	
 *
 *目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
 *
 *SR3	
 *
 *1．	最低一票根据所选择的条目信息带出默认最低一票设置，
 *
 *在此可做修改并将其绑定到区域与价格上
 *
 *SR4	
 *
 * 版本号无意义， 都是根据生效时间来做版本控制。譬如有一个汽运价格方案生效日期为2010-05-31 截止日期为2999-01-01，升级时候生效日期为2012-12-11，那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。这样在两段不同时间轴中出现两个不同价格的版本信息。
 *SR5	
 *
 *新增价格方案时，所选择的区域信息（始发区域/目的地区域）都需要过滤（由于区域管理按照价格时效部门不同做以划分），只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
 *
 *SR6	
 *
 *图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，校验提示、“****到达城市已经存在， 请检查excel模版”
 *
 *SR7	
 *
 *汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,是否立即添加相应目的地价格明细信息?” 选择“确定”，回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
 *
 *SR8	
 *
 *在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，请确认是否以该方案为准,请将xxx方案中止。”
 *
 *SR9	
 *
 *立即中止功能： 在价格查询列表中，只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” ，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
 *
 *SR10	
 *
 *立即激活功能： 在价格查询列表中，只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时间调整为当前设置的生效时间，出现小于当前营业日期系统提示“立即激活操作的生效时间必须大于等于营业日期!” ，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间
 *
 *
 *出发地信息
 *
 *始发区域	区域信息	下拉列表		100	是
 *
 *生效日期	生效日期	文本框	请输入价格方案名称	200	是
 *
 *是否生效	是否生效	单选按钮		50	是
 *
 *方案描述	备注	文本域		500	是
 *
 *
 *
 *
 *目的城市价格设定信息
 *
 *目的地	绑定目的城市	输出	100
 *
 *产品条目	绑定产品条条目设定价格信息	输出	 100
 *
 *重货价格	设置从出发城市到目的地城市的重货价格	输出	20
 *
 *轻货价格	设置从出发城市到目的地城市的轻货价格	输出	20
 *
 *最低一票	最低一票设定	输出	20
 *
 *是否集中接货	用于区别是否上门不上门的价格维护	输出	20
 *
 *备注	其他备注信息	输出	500
 *
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * you may not use this file except in compliance with the License.
 * 
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * 
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PricePlanService.java
 * 
 * FILE NAME        	: PricePlanService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;

import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;

import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressPricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 快递价格管理信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2013-8-5 上午9:29:37
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2013-8-5 上午9:29:37
 * @since
 * @version
 */
public class ExpressPricePlanService implements IExpressPricePlanService {

    private static final Logger log = Logger
	    .getLogger(ExpressPricePlanService.class);

    /**
     * 价格方案主信息
     */
    @Inject
    IPricePlanDao pricePlanDao;

    /**
     * 计费规则
     */
    @Inject
    IExpressPriceValuationDao expressPriceValuationDao;

    /**
     * 计费明细
     */
    @Inject
    IExpressPriceCriteriaDetailDao expressPriceCriteriaDetailDao;

    /**
     * 产品条目
     */
    @Inject
    IProductItemDao productItemDao;
    @Inject
    IProductDao productDao;
    
    /**
     * 计价条目
     */
    @Inject
    IPriceEntryDao priceEntryDao;

    /**
     * 价格计算表达式
     */
    @Inject
    IPriceRuleDao priceRuleDao;

    /**
     * 产品Service
     */
    @Inject
    IProductService productService;

    /**
     * 货物Service
     */
    @Inject
    IGoodsTypeService goodsTypeService;

    /**
     * 区域Service
     */
    @Inject
    IRegionExpressService regionExpressService;

    /**
     * 员工信息Service
     */
    @Inject
    IEmployeeService employeeService;
    /**
     * 快递折扣方案Service
     */
    IExpressDiscountPlanService expressDiscountPlanService;
    
    /**
	 * 客户合同信息
	 */
	@Inject
	ICusBargainService cusBargainService;
	/**
	 *设置客户合同Service
	 */
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}
    

	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public void setExpressDiscountPlanService(
			IExpressDiscountPlanService expressDiscountPlanService) {
		this.expressDiscountPlanService = expressDiscountPlanService;
	}

	/**
     * 设置 区域Service.
     * 
     * @param regionService
     *            the new 区域Service
     */
    public void setRegionExpressService(
	    IRegionExpressService regionExpressService) {
	this.regionExpressService = regionExpressService;
    }

    /**
     * 设置 产品Service.
     * 
     * @param productService
     *            the new 产品Service
     */
    public void setProductService(IProductService productService) {
	this.productService = productService;
    }

    /**
     * 设置 货物Service.
     * 
     * @param goodsTypeService
     *            the new 货物Service
     */
    public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
	this.goodsTypeService = goodsTypeService;
    }

    /**
     * 设置 价格计算表达式.
     * 
     * @param priceRuleDao
     *            the new 价格计算表达式
     */
    public void setPriceRuleDao(IPriceRuleDao priceRuleDao) {
	this.priceRuleDao = priceRuleDao;
    }

    /**
     * 设置 计价条目.
     * 
     * @param priceEntryDao
     *            the new 计价条目
     */
    public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
	this.priceEntryDao = priceEntryDao;
    }

    /**
     * 设置 产品条目.
     * 
     * @param productItemDao
     *            the new 产品条目
     */
    public void setProductItemDao(IProductItemDao productItemDao) {
	this.productItemDao = productItemDao;
    }

    /**
     * 设置 计费规则.
     * 
     * @param priceValuationDao
     *            the new 计费规则
     */
    public void setExpressPriceValuationDao(
			IExpressPriceValuationDao expressPriceValuationDao) {
		this.expressPriceValuationDao = expressPriceValuationDao;
	}


    /**
     * 设置 计费明细.
     * 
     * @param priceCriteriaDetailDao
     *            the new 计费明细
     */
   public void setExpressPriceCriteriaDetailDao(
			IExpressPriceCriteriaDetailDao expressPriceCriteriaDetailDao) {
		this.expressPriceCriteriaDetailDao = expressPriceCriteriaDetailDao;
	}

	/**
     * 设置 价格方案主信息.
     * 
     * @param pricePlanDao
     *            the new 价格方案主信息
     */
    public void setPricePlanDao(IPricePlanDao pricePlanDao) {
	this.pricePlanDao = pricePlanDao;
    }

    /**
     * 
     * <p>
     * (根据传入生效时间与始发区域ID加上数据状态查询所符合的最新价格方案版本)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 下午3:12:01
     * 
     * @param beginTime
     *            生效时间
     * 
     * @param deptRegionId
     *            始发区域
     * 
     * @param active
     *            是否激活
     * 
     * @return
     * 
     * @see
     */
    @Override
    public PricePlanEntity findPricePlanByRegionId(Date cuurentTime,
	    String deptRegionId, String active) {
	return pricePlanDao.findPricePlanByDeptRegionId(cuurentTime,
		deptRegionId, active);
    }

    /**
     * <p>
     * 将状态为未激活的记录更新为激活状态，并且要新激活的记录需同已有激活记录进行失效截止日期的正确衔接
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-19 下午2:39:20
     * @param pricePlanIds
     * @throws PricePlanException
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#activePricePlan(java.util.List)
     */
    @Override
    @Transactional
    public void activePricePlan(List<String> pricePlanIds)
	    throws PricePlanException {
	Date currentTime = new Date();
	if (CollectionUtils.isNotEmpty(pricePlanIds)) {
	    // 遍历所有价格方案信息
	    for (int i = 0; i < pricePlanIds.size(); i++) {
		PricePlanEntity pricePlanEntity = pricePlanDao
			.selectByPrimaryKey(pricePlanIds.get(i));
		if (null == pricePlanEntity) {
		    throw new PricePlanException("所选价格方案为空! 请检查", null);
		}
		// 激活日期需要大于当前营业日期
		Date beginTime = pricePlanEntity.getBeginTime();
		if (currentTime.after(beginTime)) {
		    throw new PricePlanException(pricePlanEntity.getName()
			    + "方案的生效日期为"
			    + DateUtils.convert(beginTime,
				    DateUtils.DATE_TIME_FORMAT
					    + " 需要大于当前营业日期才能被激活!"), null);
		}
		PriceValuationEntity valEntity = new PriceValuationEntity();
		valEntity.setPricePlanId(pricePlanEntity.getId());
		//获得客户编码
		String customerCode =  pricePlanEntity.getCustomerCode();
		// 得到某个价格放下所有的规则,并校验这些规则是否出现重复存在
		List<PriceValuationEntity> valList = expressPriceValuationDao
			.selectByCodition(valEntity);
		// 有效的规则信息集合
		List<String> valuationIds = new ArrayList<String>();
		
		if (CollectionUtils.isNotEmpty(valList)) {
			// 遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
	    	//折扣方案明细集合
	    	List<ExpressDiscountDto> expressDiscountDtoList = null;
	    	//价格方案集合
			List<PriceValuationEntity> entityList = null;
			//3级产品集合
			List<ProductEntity> productList = null;
			//折扣方案查询实体
			ExpressDiscountDto expressDiscountDto = new ExpressDiscountDto();
			
			String productCodeL3 = null;
		    // 遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
		    for (PriceValuationEntity entity : valList) {
			// 设置方案激活时间
			entity.setBeginTime(beginTime);
			//设置查询条件客户编码
		    entity.setCustomerCode(customerCode);
			// 根据条件查询符合条件的计费规则
			 entityList = queryInfosByCondition(entity);
			 //封装折扣方案查询条件
			 expressDiscountDto.setCustomerCode(customerCode);
			 productList = productDao.getAllChildsFromParentCode(entity.getProductCode());
			 if(CollectionUtils.isNotEmpty(productList)) {
				 StringBuffer bf = new StringBuffer();
				 bf.append("('");
				 for (ProductEntity product : productList) {
					bf.append(product.getCode()+"','");
				}
				if(bf.length()>=2) {
					productCodeL3=bf.toString().substring(0, bf.length()-2)+")";
				}
				
			 }
			 if(StringUtils.isNotBlank(entity.getCustomerCode())) {
				 if(productCodeL3 != null && !"".equals(productCodeL3)) {
					 expressDiscountDto.setProductCodeL3(productCodeL3);
					 expressDiscountDto.setArriveRegionCode(entity.getArrvRegionId());
					 expressDiscountDto.setStartRegionCode(entity.getDeptRegionId());
					 expressDiscountDto.setBeginTime(beginTime);
					 expressDiscountDto.setEndTime(pricePlanEntity.getEndTime());
					 expressDiscountDto.setActive(FossConstants.ACTIVE);
					 //根据条件查询符合条件的折扣明细
					 expressDiscountDtoList = expressDiscountPlanService.queryExpressDiscountPlanDetailByCondition(expressDiscountDto);
				 }
			 }
			if (CollectionUtils.isNotEmpty(entityList) || CollectionUtils.isNotEmpty(expressDiscountDtoList)) {
			    throw new PricePlanException(
				    getErrorMessage(entity,entityList,expressDiscountDtoList), null);
			} else {
			    valuationIds.add(entity.getId());
			}
		    }
		} else {
		    throw new PricePlanException("空的方案不能激活!", null);
		}
		// 以上全部校验通过,依次激活计价明细，计费规则，价格方案
		expressPriceCriteriaDetailDao
			.activeCriteriaDetailByValuationIds(valuationIds);
		expressPriceValuationDao.activeValueAdded(valuationIds);
	    }
	    pricePlanDao.activePricePlan(pricePlanIds);
	}
    }

    /**
     * <p>
     * 立即激活
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-19 下午3:59:13
     * @param pricePlanEntity
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#immediatelyActivePricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
     */
    @Override
    @Transactional
    public void immediatelyActivePricePlan(PricePlanEntity planEntity) {
	Date currentTime = new Date();
	if (null != planEntity) {
	    List<String> pricePlanIds = new ArrayList<String>();
	    //查询价格方案主信息
	    PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(planEntity.getId());
	    if (null == pricePlanEntity) {
		throw new PricePlanException("所选价格方案为空! 请检查", null);
	    }
	    //设置 生效日期
	    pricePlanEntity.setBeginTime(planEntity.getBeginTime());
	    pricePlanEntity.setModifyDate(currentTime);
	    String pricePlanId = pricePlanEntity.getId();
	    // 激活日期需要大于当前营业日期
	    Date beginTime = pricePlanEntity.getBeginTime();
	    if (currentTime.after(beginTime)) {
		throw new PricePlanException(pricePlanEntity.getName()
			+ "方案的生效日期为"
			+ DateUtils.convert(beginTime,
				DateUtils.DATE_TIME_FORMAT
					+ " 需要大于当前营业日期才能被激活!"), null);
	    }
	    PriceValuationEntity valEntity = new PriceValuationEntity();
	    valEntity.setPricePlanId(pricePlanEntity.getId());
	    //获得客户编码
	    String customerCode =  pricePlanEntity.getCustomerCode();
	    // 得到某个价格放下所有的规则,并校验这些规则是否出现重复存在
	    List<PriceValuationEntity> valList = expressPriceValuationDao
		    .selectByCodition(valEntity);
	    // 有效的规则信息集合
	    List<String> valuationIds = new ArrayList<String>();
	    if (CollectionUtils.isNotEmpty(valList)) {
		// 遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
	    	//折扣方案明细集合
	    	List<ExpressDiscountDto> expressDiscountDtoList = null;
	    	//价格方案集合
			List<PriceValuationEntity> entityList = null;
			//3及产品集合
			List<ProductEntity> productList = null;
			//折扣方案查询实体
			ExpressDiscountDto expressDiscountDto = new ExpressDiscountDto();
			
			String productCodeL3 = null;
		    // 遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
		    for (PriceValuationEntity entity : valList) {
			// 设置方案激活时间
			entity.setBeginTime(beginTime);
			//设置查询条件客户编码
		    entity.setCustomerCode(customerCode);
			// 根据条件查询符合条件的计费规则
			 entityList = queryInfosByCondition(entity);
			 //封装折扣方案查询条件
			 expressDiscountDto.setCustomerCode(customerCode);
			 productList = productDao.getAllChildsFromParentCode(entity.getProductCode());
			 if(CollectionUtils.isNotEmpty(productList)) {
				 StringBuffer bf = new StringBuffer();
				 bf.append("('");
				 for (ProductEntity product : productList) {
					bf.append(product.getCode()+"','");
				}
				if(bf.length()>=2) {
					productCodeL3=bf.toString().substring(0, bf.length()-2)+")";
				}
				
			 }
			 if(StringUtils.isNotBlank(entity.getCustomerCode())) {
				 if(productCodeL3 != null && !"".equals(productCodeL3)) {
					 expressDiscountDto.setProductCodeL3(productCodeL3);
					 expressDiscountDto.setArriveRegionCode(entity.getArrvRegionId());
					 expressDiscountDto.setStartRegionCode(entity.getDeptRegionId());
					 expressDiscountDto.setBeginTime(beginTime);
					 expressDiscountDto.setEndTime(pricePlanEntity.getEndTime());
					 expressDiscountDto.setActive(FossConstants.ACTIVE);
					 //根据条件查询符合条件的折扣明细
					 expressDiscountDtoList = expressDiscountPlanService.queryExpressDiscountPlanDetailByCondition(expressDiscountDto);
				 }
			 }
			 
			if (CollectionUtils.isNotEmpty(entityList) || CollectionUtils.isNotEmpty(expressDiscountDtoList)) {
			    throw new PricePlanException(
				    getErrorMessage(entity,entityList,expressDiscountDtoList), null);
			} else {
			    valuationIds.add(entity.getId());
			}
		    }
	    } else {
		throw new PricePlanException("空的方案不能激活!", null);
	    }
	    // 以上全部校验通过,依次激活计价明细，计费规则，价格方案
	    expressPriceCriteriaDetailDao
		    .activeCriteriaDetailByValuationIds(valuationIds);
	    expressPriceValuationDao.activeValueAdded(valuationIds);
	    pricePlanIds.add(pricePlanId);
	    pricePlanDao.updateByPrimaryKeySelective(pricePlanEntity);
	    pricePlanDao.activePricePlan(pricePlanIds);
	}
    }

    /**
     * <p>
     * 根据条件查询符合条件的计费规则
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-19 下午2:32:57
     * @param entity
     * @return
     * @see
     */
    public List<PriceValuationEntity> queryInfosByCondition(
	    PriceValuationEntity entity) {
	if (entity == null) {
	    return null;
	} else {
	    // 查询激活状态下的数据
	    entity.setActive(FossConstants.ACTIVE);
	    return expressPriceValuationDao.queryInfoByCondition(entity);
	}
    }

    /**
     * <p>
     * 激活方案检查明细数据出现错误时给出的提示信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-19 下午3:53:26
     * @param priceValuationEntity
     * @return
     * @see
     */
    private String getErrorMessage(PriceValuationEntity priceValuationEntity,
    		List<PriceValuationEntity> entityList,
    		List<ExpressDiscountDto> expressDiscountDtoList
    		) {
	// 依次寻找，产品,货物,区域
	ProductEntity producTypeEntity = productService.getProductByCache(
		priceValuationEntity.getProductCode(), new Date());
	GoodsTypeEntity goodsTypeEntity = goodsTypeService
		.queryGoodsTypeByGoodTypeCode(
			priceValuationEntity.getGoodsTypeCode(), new Date());
	// 始发区域
	PriceRegionExpressEntity priceRegion = regionExpressService
		.searchRegionByID(priceValuationEntity.getDeptRegionId(),
			PricingConstants.PRICING_REGION);
	// 到达区域
	PriceRegionExpressEntity arrvRegion = regionExpressService
		.searchRegionByID(priceValuationEntity.getArrvRegionId(),
			PricingConstants.PRICING_REGION);
	StringBuilder itemName = new StringBuilder();
	itemName.append(producTypeEntity.getName());
	itemName.append("-");
	itemName.append(goodsTypeEntity.getName());
	StringBuilder errStr = new StringBuilder();
	if(priceValuationEntity.getCustomerCode()!=null && !"".equals(priceValuationEntity.getCustomerCode())) {
		errStr.append("客户编码[");
		errStr.append(priceValuationEntity.getCustomerCode());
		errStr.append("],");
	}
	errStr.append("始发区域[");
	errStr.append(priceRegion.getRegionName());
	errStr.append("],目的地区域[");
	errStr.append(arrvRegion.getRegionName());
	errStr.append("],条目名称[");
	errStr.append(itemName);
	errStr.append("]");
	if(CollectionUtils.isNotEmpty(entityList)) {
		errStr.append("的计费规则存在已激活的价格方案，不允许激活！");
	} else {
		if(CollectionUtils.isNotEmpty(expressDiscountDtoList)) {
			errStr.append("的计费规则存在已激活的折扣方案，不允许激活！");
		}
	}
	
	return errStr.toString();
    }

    /**
     * 
     * <p>
     * (删除价格主方案-草稿状态 删除步骤,按照顺序删除计费明细,计费规则,价格方案)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 上午11:36:03
     * 
     * @param pricePlanIds
     * 
     * @return
     * 
     * @see
     */
    @Override
    @Transactional
    public void deletePricePlan(List<String> pricePlanIds) {
	if (CollectionUtils.isNotEmpty(pricePlanIds)) {
	    for (int i = 0; i < pricePlanIds.size(); i++) {
		String pricePlanId = pricePlanIds.get(i);
		PriceValuationEntity valEntity = new PriceValuationEntity();
		valEntity.setPricePlanId(pricePlanId);
		List<PriceValuationEntity> valList = expressPriceValuationDao
			.selectByCodition(valEntity);
		if (CollectionUtils.isNotEmpty(valList)) {
		    for (int j = 0; j < valList.size(); j++) {
			PriceValuationEntity val = (PriceValuationEntity) valList
				.get(j);
			String id = val.getId();
			expressPriceCriteriaDetailDao
				.deleteCriteriaDetailByValuationId(id);
		    }
		    expressPriceValuationDao
			    .deltePriceValuationByPricePlanId(pricePlanId);
		}
		pricePlanDao.deleteByPrimaryKey(pricePlanId);
	    }
	}
    }

    /**
     * <p>复制方案</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-26 下午2:55:01
     * @param pricePlanId
     * @return
     * @throws PricePlanException 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#copyPricePlan(java.lang.String)
     */
    @Override
    @Transactional
    public String copyPricePlan(String pricePlanId) throws PricePlanException {
	if (null == pricePlanId) {
	    throw new PricePlanException("复制方案丢失数据,不能发起复制", null);
	}
	// 复制方案，根据方案ID,查询计费规则，根据计费规则查询计价明细依次复制且新增相应的新记录
	PricePlanEntity pricePlanEntity = pricePlanDao
		.selectByPrimaryKey(pricePlanId);
	// 获取被复制对象的计费规则信息
	PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
	priceValuationEntity.setPricePlanId(pricePlanId);
	List<PriceValuationEntity> tempPriceValuationEntitys = expressPriceValuationDao
		.selectByCodition(priceValuationEntity);
	String newPricePlanId = UUIDUtils.getUUID();
	// 复制草稿
	pricePlanEntity.setActive(FossConstants.INACTIVE);
	pricePlanEntity.setId(newPricePlanId);
	//设置为生效时间为当前时间
	pricePlanEntity.setBeginTime(new Date());
	//设置截止时间为2999年
	pricePlanEntity.setEndTime(new Date(NumberConstants.ENDTIME));
	pricePlanDao.insert(pricePlanEntity);

	if (CollectionUtils.isNotEmpty(tempPriceValuationEntitys)) {
	    for (int loop = 0; loop < tempPriceValuationEntitys.size(); loop++) {
		PriceValuationEntity tempValuationEntity = tempPriceValuationEntitys
			.get(loop);
		//author:wangshuai 2016年1月14日  价格方案明细查询 begin
		List<PriceCriteriaDetailEntity> tempPriceCriteriaDetailEntitys = expressPriceCriteriaDetailDao
			.selectByValuationIdForExpressPriceCopy(tempValuationEntity.getId());
		//author:wangshuai 2016年1月14日  价格方案明细查询 end
		tempValuationEntity.setId(UUIDUtils.getUUID());
		tempValuationEntity.setPricePlanId(newPricePlanId);
		expressPriceValuationDao.insertSelective(tempValuationEntity);
		if (CollectionUtils.isNotEmpty(tempPriceCriteriaDetailEntitys)) {
		    for (int i = 0; i < tempPriceCriteriaDetailEntitys.size(); i++) {
			PriceCriteriaDetailEntity tempPriceCriteriaDetailEntity = tempPriceCriteriaDetailEntitys
				.get(i);
			tempPriceCriteriaDetailEntity
				.setId(UUIDUtils.getUUID());
			tempPriceCriteriaDetailEntity
				.setPricingValuationId(tempValuationEntity
					.getId());
			
			expressPriceCriteriaDetailDao
				.insertSelective(tempPriceCriteriaDetailEntity);
		    }
		}
	    }
	}
	// 返回新增草稿UUID
	return newPricePlanId;
    }

    /**
     * 
     * <p>
     * (查询复制方案信息以及明细信息)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 下午5:20:23
     * 
     * @param pricePlanId
     * 
     * @return
     * 
     * @see
     */
    @Override
    public PriceManageMentVo queryCopyPricePlanInfo(String pricePlanId) {
	if (StringUtil.isEmpty(pricePlanId)) {
	    throw new PricePlanException("选择的主方案信息缺失!,请联系运维人员查询原因。", null);
	}
	PricePlanEntity pricePlan = pricePlanDao
		.selectByPrimaryKey(pricePlanId);
	PriceRegionExpressEntity priceRegionEntity = regionExpressService
		.searchRegionByID(pricePlan.getPriceRegionId(),
			PricingConstants.PRICING_REGION);
	pricePlan.setPriceRegionName(priceRegionEntity.getRegionName());
	PriceManageMentVo priceManageMentVo = new PriceManageMentVo();
	priceManageMentVo.setPricePlanEntity(pricePlan);
	return priceManageMentVo;
    }

    /**
     * 
     * queryPricePlanBatchInfo(分页查询价格方案)
     * 
     * @param pricePlanEntity
     * 
     * @param start
     * 
     * @param limit
     * 
     * @return
     * 
     *         List<PricePlanEntity>
     * 
     * @exception
     * 
     * @since 1.0.0
     */
    @Override
    public List<PricePlanEntity> queryPricePlanBatchInfo(
	    PricePlanEntity pricePlanEntity, int start, int limit) {
	// 设置条件
	if (PricingConstants.ALL.equals(pricePlanEntity.getActive())) {
	    pricePlanEntity.setActive(null);
	}
	pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_KD_FLAG);
	List<PricePlanEntity> list = pricePlanDao.queryPricePlanBatchInfo(
		pricePlanEntity, start, limit);
	return convertToRegionName(list);
    }

    /**
     * 
     * 获取区域名称
     * 
     * @param list
     * 
     * @return
     */
    private List<PricePlanEntity> convertToRegionName(List<PricePlanEntity> list) {
	List<PricePlanEntity> convertList = new ArrayList<PricePlanEntity>();
	for (PricePlanEntity pricePlanEntity : list) {
	    String priceRegionId = pricePlanEntity.getPriceRegionId();
	    PriceRegionExpressEntity priceRegionEntity = regionExpressService
		    .searchRegionByID(priceRegionId,
			    PricingConstants.PRICING_REGION);
	    if(StringUtil.isNotEmpty(pricePlanEntity.getModifyUser())){
		    EmployeeEntity employee = employeeService
			    .queryEmployeeByEmpCode(pricePlanEntity.getModifyUser());
		    if (null != employee) {
			pricePlanEntity.setModifyUserName(employee.getEmpName());
		    }
	    }
	    if (null != priceRegionEntity) {
		pricePlanEntity.setPriceRegionName(priceRegionEntity
			.getRegionName());
	    }
	    convertList.add(pricePlanEntity);
	}
	return convertList;
    }

    /**
     * 设置 员工信息Service.
     * 
     * @param employeeService
     *            the new 员工信息Service
     */
    public void setEmployeeService(IEmployeeService employeeService) {
	this.employeeService = employeeService;
    }

    /**
     * 
     * queryPricePlanBatchInfoCount(价格方案查询总数)
     * 
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param pricePlanEntity
     * 
     * @return
     * 
     *         Long 总记录数
     * 
     * @exception
     * 
     * @since 1.0.0
     */
    @Override
    public Long queryPricePlanBatchInfoCount(PricePlanEntity pricePlanEntity) {
	return pricePlanDao.queryPricePlanBatchInfoCount(pricePlanEntity);
    }

    /**
     * 批量导入价格方案和明细
     * 
     * @author zhangdongping
     * @date 2012-12-23 上午12:35:09
     * @param detailMap
     * @param priceRegionEntityMap
     * @param productEntityMap
     * @see
     */
    @Transactional
    @Override
    public PricePlanEntity addPricePlan(PricePlanEntity pricePlanEntity)
	    throws PricePlanException {
	if (null == pricePlanEntity) {
	    throw new PricePlanException(
		    PricePlanException.PRICE_PLAN_ADD_PLANISNULL_ERROR_CODE,
		    null);
	}
	if (pricePlanEntity.getBeginTime().before(new Date())) {
	    throw new PricePlanException("方案生效日期:"
		    + DateUtils.convert(pricePlanEntity.getBeginTime())
		    + "必须大于当前营业日期", null);
	}
	pricePlanEntity = getPricePlanValue(pricePlanEntity);
	String planName = pricePlanEntity.getName();
	PricePlanEntity queryBean = new PricePlanEntity();
	queryBean.setName(planName);
	queryBean.setTransportFlag(PricingConstants.TRANSPORT_KD_FLAG);
	List<PricePlanEntity> pricePlanEntitys = pricePlanDao
		.queryPricePlanBatchInfo(queryBean);
	if (CollectionUtils.isNotEmpty(pricePlanEntitys)) {
	    throw new PricePlanException("方案名称为:" + planName + "已经存在,不可重复添加",
		    null);
	}
	if(isAloneQuotation(pricePlanEntity.getCustomerCode())) {
		pricePlanDao.insert(pricePlanEntity);
	}else {
		return null;
	}
	return pricePlanDao.selectByPrimaryKey(pricePlanEntity.getId());
    }

    /**
     * 价格方案批次新增信息
     * 
     * @param pricePlanEntity
     * @return
     */
    private PricePlanEntity getPricePlanValue(PricePlanEntity pricePlanEntity) {
	Date currentTime = new Date();
	pricePlanEntity.setId(UUIDUtils.getUUID());
	pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_KD_FLAG);
	pricePlanEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	pricePlanEntity.setActive(FossConstants.INACTIVE);
	pricePlanEntity.setVersionInfo(String.valueOf(currentTime.getTime()));
	pricePlanEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	pricePlanEntity.setModifyDate(currentTime);
	pricePlanEntity.setVersionNo(currentTime.getTime());
	pricePlanEntity.setCreateDate(currentTime);
	return pricePlanEntity;
    }

    /**
     * 计费规则
     */
    private static final String VALUATION_RULE = "VALUATIONRULE";

    /**
     * 计费明细
     */
    private static final String PRICING_CRITERIA = "PRICINGCRITERIA";

    /**
     * <p>
     * 一条计费规则一条对应计价明细3条，分析数据进行持久化)
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-6 下午4:05:56
     * @param detailList
     * @param pricePriceId
     * @return
     * @see
     */
    private Map<String, Object> analysisPricePlanData(
	    List<PricePlanDetailDto> detailList, String pricePriceId) {
	Date currentTime = new Date();
	// 计费规则
	List<PriceValuationEntity> valuationEntitys = new ArrayList<PriceValuationEntity>();
	// 重货
	List<PriceCriteriaDetailEntity> heavyList = new ArrayList<PriceCriteriaDetailEntity>();
	// 轻货
	List<PriceCriteriaDetailEntity> lightList = new ArrayList<PriceCriteriaDetailEntity>();
	// 整理后的数据容器,主要是对计费规则,计价明细的数据收集
	Map<String, Object> priceCriteriaDetailMap = new HashMap<String, Object>();
	PricePlanEntity pricePlanEntity = pricePlanDao
		.selectByPrimaryKey(pricePriceId);
	for (int i = 0; i < detailList.size(); i++) {
	    PriceValuationEntity valuationEntity = new PriceValuationEntity();
	    PricePlanDetailDto pricePlanDetailDto = detailList.get(i);
	    pricePlanDetailDto.setArrvRegionName(pricePlanDetailDto
		    .getArrvRegionName());
	    pricePlanDetailDto.setPricePlanId(pricePriceId);
	    // 根据条目编码与时间查询条目信息
	    ProductItemEntity productItemEntity = productItemDao
		    .findProductItemByCode(
			    pricePlanDetailDto.getProductItemCode(),
			    currentTime);
	    if (null == productItemEntity) {
		throw new PricePlanException(
			PricePlanException.PRICE_PLAN_ADD_CHECKPRODUCTITEM_ERROR_CODE,
			null);
	    } else {
		valuationEntity.setProductCode(productItemEntity
			.getProductCode());
		valuationEntity.setGoodsTypeCode(productItemEntity
			.getGoodstypeCode());
	    }
	    // 运价方案ID
	    valuationEntity.setPricePlanId(pricePlanDetailDto.getPricePlanId());
	    // 是否集中接货
	    valuationEntity.setCentralizePickup(pricePlanDetailDto
		    .getCentralizePickup());
	    // 类型-价格方案
	    valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
	    // 到达区域ID
	    valuationEntity.setArrvRegionId(pricePlanDetailDto
		    .getArrvRegionId());
	    // 价格方案主信息中的始发区域ID
	    valuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	    // 价格方案主信息中的始发区域ID
	    valuationEntity
		    .setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
	    /* 收集计费规则信息* */
	    valuationEntity.setId(UUIDUtils.getUUID());
	    valuationEntity.setProductId(productItemEntity.getProductId());
	    valuationEntity.setGoodsTypeId(productItemEntity.getGoodstypeId());
	    valuationEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    valuationEntity.setArrvRegionName(pricePlanDetailDto
		    .getArrvRegionName());
	    valuationEntity.setVersionNo(currentTime.getTime());
	    valuationEntity.setBeginTime(currentTime);
	    valuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	    valuationEntity.setActive(pricePlanEntity.getActive());
	    // 创建时间
	    valuationEntity.setCreateDate(currentTime);
	    // 计价条目
	    PriceEntity priceEntry = new PriceEntity();
	    priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
	    priceEntry.setReceiveDate(currentTime);
	    List<PriceEntity> listPriceEntry = priceEntryDao
		    .searchPriceEntryByConditions(priceEntry);
	    if (CollectionUtils.isNotEmpty(listPriceEntry)) {
		priceEntry = listPriceEntry.get(0);
		// 由于数据库中对该值不能为NULL,必须查询一次计费条目ID
		valuationEntity.setPricingEntryId(priceEntry.getId());
		// 计费条目编码
		valuationEntity.setPricingEntryCode(priceEntry.getCode());
		valuationEntity.setCode(priceEntry.getCode());
	    } else {
		throw new PricePlanException(
			PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE,
			null);
	    }
	    valuationEntitys.add(valuationEntity);
	    // 重货数据
	    PriceCriteriaDetailEntity heavyEntity = new PriceCriteriaDetailEntity();
	    PriceCriteriaDetailEntity lightEntity = new PriceCriteriaDetailEntity();
	    // 关联计费规则ID
	    heavyEntity.setPricingValuationId(valuationEntity.getId());
	    // 设置明细备注
	    heavyEntity.setDescription(pricePlanDetailDto.getRemark());
	    // 计费类型 按重量
	    heavyEntity
		    .setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	    heavyEntity.setActive(pricePlanEntity.getActive());
	    BigDecimal heavyPrice = NumberUtils.multiply(
		    pricePlanDetailDto.getHeavyPrice(),
		    PricingConstants.YUTOFEN);
	    // 重货价格
	    heavyEntity.setFeeRate(heavyPrice);
	    heavyEntity.setMinFee(pricePlanDetailDto.getMinimumOneTicket());
	    heavyEntity.setVersionNo(currentTime.getTime());
	    heavyEntity.setPricingCriteriaId("");
	    heavyEntity
		    .settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	    heavyEntity.setId(UUIDUtils.getUUID());
	    heavyEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
	    heavyEntity.setRightrange(BigDecimal
		    .valueOf(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX));
	    // 始发区域ID
	    heavyEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	    heavyList.add(heavyEntity);
	    // 轻货数据
	    // 关联计费规则ID
	    lightEntity.setPricingValuationId(valuationEntity.getId());
	    // 设置明细备注
	    lightEntity.setDescription(pricePlanDetailDto.getRemark());
	    // 计费类型 按体积
	    lightEntity
		    .setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
	    lightEntity.setVersionNo(currentTime.getTime());
	    lightEntity.setActive(pricePlanEntity.getActive());
	    BigDecimal lightPrice = NumberUtils.multiply(
		    pricePlanDetailDto.getLightPrice(),
		    PricingConstants.YUTOFEN);
	    // 轻货价格
	    lightEntity.setFeeRate(lightPrice);
	    lightEntity.setMinFee(pricePlanDetailDto.getMinimumOneTicket());
	    // 该属性后续会被删除
	    lightEntity.setPricingCriteriaId("");
	    lightEntity
		    .settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	    lightEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
	    lightEntity.setRightrange(BigDecimal
		    .valueOf(PricingConstants.CRITERIA_DETAIL_VOLUME_MAX));
	    lightEntity.setId(UUIDUtils.getUUID());
	    // 始发区域ID
	    lightEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	    lightList.add(lightEntity);
	}
	heavyList.addAll(lightList);
	priceCriteriaDetailMap.put(ExpressPricePlanService.VALUATION_RULE,
		valuationEntitys);
	priceCriteriaDetailMap.put(ExpressPricePlanService.PRICING_CRITERIA,
		heavyList);
	return priceCriteriaDetailMap;
    }

    /**
     * <p>
     * 查询价格方案明细分页
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-8 下午3:26:58
     * @param queryPricePlanDetailBean
     * @param start
     * @param limit
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#queryPricePlanDetailInfo(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean,
     *      int, int)
     */
    @Override
    public List<ExpressPricePlanDetailDto> queryPricePlanDetailInfo(
	    QueryPricePlanDetailBean queryBean, int start, int limit) {
	/*
	 * if (null != queryPricePlanDetailBean) { String productItemCode =
	 * queryPricePlanDetailBean .getProductItemCode(); if
	 * (StringUtil.isNotBlank(productItemCode)) { ProductItemEntity
	 * productItemEntity = productItemDao
	 * .findProductItemByCode(productItemCode, new Date()); if (null !=
	 * productItemEntity) {
	 * queryPricePlanDetailBean.setGoodsTypeCode(productItemEntity
	 * .getGoodstypeCode());
	 * queryPricePlanDetailBean.setProductCode(productItemEntity
	 * .getProductCode()); } } }
	 */

	PriceValuationEntity entity = new PriceValuationEntity();
	if (null != queryBean) {
	    if (StringUtils.isBlank(queryBean.getPricePlanId())) {
		throw new PricePlanException("价格方案ID不允许为空！");
	    }
	    entity.setPricePlanId(queryBean.getPricePlanId());
	    if (StringUtils.isNotBlank(queryBean.getArrvRegionId())) {
		entity.setArrvRegionId(queryBean.getArrvRegionId());
	    }
	    if (StringUtils.isNotBlank(queryBean.getProductItemCode())) {
		ProductItemEntity productItemEntity = productItemDao
			.findProductItemByCode(queryBean.getProductItemCode(),
				new Date());
		if (null != productItemEntity) {
		    entity.setGoodsTypeCode(productItemEntity
			    .getGoodstypeCode());
		    entity.setProductCode(productItemEntity.getProductCode());
		}
	    }
	}

	// 根据查询条件查询计费规则(分页)
	List<PriceValuationEntity> list = expressPriceValuationDao.selectByCodition(
		entity, start, limit);

	return convertInfoList(list);
    }

    /**
     * <p>
     * 根据传入的计价规则ID查询计价规则、计价明细信息
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-9 上午11:44:04
     * @param queryPricePlanDetailBean
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#queryDetailDto(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean)
     */
    @Override
    public ExpressPricePlanDetailDto queryDetailDto(
	    QueryPricePlanDetailBean queryBean) {

	PriceValuationEntity entity = new PriceValuationEntity();
	if (null != queryBean) {
	    if (StringUtils.isBlank(queryBean.getValuationId())) {
		throw new PricePlanException("计价规则ID不允许为空！");
	    }
	    List<String> idList = new ArrayList<String>();
	    idList.add(queryBean.getValuationId());
	    entity.setValuationIds(idList);
	}

	// 根据查询条件查询计费规则(分页)
	List<PriceValuationEntity> list = expressPriceValuationDao.selectByCodition(
		entity, NumberConstants.ZERO, Integer.MAX_VALUE);
	if (CollectionUtils.isNotEmpty(list)) {
	    return convertInfo(list.get(0));
	}
	return null;
    }

    /**
     * <p>
     * 批量封装计价明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-8 下午5:24:38
     * @param list
     * @return
     * @see
     */
    private List<ExpressPricePlanDetailDto> convertInfoList(
	    List<PriceValuationEntity> list) {
	if (CollectionUtils.isNotEmpty(list)) {
	    List<ExpressPricePlanDetailDto> dtoList = new ArrayList<ExpressPricePlanDetailDto>();
	    for (PriceValuationEntity entity : list) {
		ExpressPricePlanDetailDto dto = convertInfo(entity);
		dtoList.add(dto);
	    }
	    return dtoList;
	}
	return null;
    }

    /**
     * <p>
     * 封装计价明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-8 下午3:54:31
     * @param entity
     * @return
     * @see
     */
    private ExpressPricePlanDetailDto convertInfo(PriceValuationEntity entity) {
	// 快递价格方案计价明细DTO
	ExpressPricePlanDetailDto dto = new ExpressPricePlanDetailDto();
	// 根据条件查询区域信息
	PriceRegionExpressEntity priceRegionEntity = regionExpressService
		.searchRegionByID(entity.getArrvRegionId(),
			PricingConstants.PRICING_REGION);
	// 设置 目的区域NAME.
	dto.setArrvRegionName(priceRegionEntity.getRegionName());
	// 设置 目的区域ID.
	dto.setArrvRegionId(priceRegionEntity.getId());
	// 设置 目的区域ID
	dto.setValuationId(entity.getId());
	// 设置 是否自提（Y/N）.
	dto.setSelfPickUp(entity.getSelfPickUp());
	// 设置 价格方案ID.
	dto.setPricePlanId(entity.getPricePlanId());

	// 按照产品编码与货物编码查询条目信息
	ProductItemEntity productItemEntity = productItemDao
		.findProductItemByGoodCodeAndProductCode(
			entity.getProductCode(), entity.getGoodsTypeCode(),
			new Date());
	// 设置 产品条目ID
	dto.setProductItemId(productItemEntity.getId());
	// 设置 产品条目名称.
	dto.setProductItemName(productItemEntity.getName());
	// 设置 产品条目CODE.
	dto.setProductItemCode(productItemEntity.getCode());

	// 根据计费规则ID查询计价方式明细列表 (按计价左区间升序查询)
	List<PriceCriteriaDetailEntity> detailList = expressPriceCriteriaDetailDao
		.queryInfosByParentId(entity.getId());
	if (CollectionUtils.isNotEmpty(detailList)) {
	    // 首重
	    PriceCriteriaDetailEntity firstEntity = detailList
		    .get(NumberConstants.ZERO);
	    // 设置 计价明细ID(首重).
	    dto.setPriceDetailId(firstEntity.getId());
	    dto.setRemark(firstEntity.getDescription());
	    // 设置 重量上限(首重).
	    dto.setWeightOnline(firstEntity.getRightrange());
	    // 设置 重量下限（首重）.
	    dto.setWeightDownline(firstEntity.getLeftrange());
	    //DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
		//2015/12/3 liding mod
		// 设置 价格（首重）.
	    // 显示时设置一位小数
		dto.setFirstFee(BigDecimal.valueOf(firstEntity.getFee())
					.divide(new BigDecimal(NumberConstants.NUMBER_100), 1, RoundingMode.HALF_UP)
					.setScale(1));
//	    dto.setFirstFee(BigDecimal.valueOf(firstEntity.getFee()
//			    .doubleValue() / 100));

	    // 续重1
	    PriceCriteriaDetailEntity entityOne = detailList
		    .get(NumberConstants.ONE);
	    // 设置 计价明细ID(续重1).
	    dto.setOneDetailId(entityOne.getId());
	    // 设置 重量上限(续重1).
	    dto.setWeightOnlineOne(entityOne.getRightrange());
	    // 设置 重量下限(续重1).
	    dto.setWeightDownlineOne(entityOne.getLeftrange());
	    // 设置 费率(续重1).
	    dto.setFeeRateOne(BigDecimal.valueOf(entityOne.getFeeRate()
		    .doubleValue() / NumberConstants.NUMBER_100));

	    // 续重2
	    PriceCriteriaDetailEntity entityTwo = detailList
		    .get(NumberConstants.TWO);
	    // 设置 计价明细ID(续重2).
	    dto.setTwoDetailId(entityTwo.getId());
	    // 设置 重量上限(续重2).
	    dto.setWeightOnlineTwo(entityTwo.getRightrange());
	    // 设置 重量下限(续重2).
	    dto.setWeightDownlineTwo(entityTwo.getLeftrange());
	    // 设置 费率(续重2).
	    dto.setFeeRateTwo(BigDecimal.valueOf(entityTwo.getFeeRate()
		    .doubleValue() / NumberConstants.NUMBER_100));

	}

	return dto;
    }

    /**
     * 
     * <p>
     * 查询汽运价格方案不分页
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * @date 2013-4-10 下午5:39:29
     * @param queryPricePlanDetailBean
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService#queryPricePlanDetailInfo(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean)
     */
    @Override
    public List<PricePlanDetailDto> queryPricePlanDetailInfo(
	    QueryPricePlanDetailBean queryPricePlanDetailBean) {
	String productItemCode = queryPricePlanDetailBean.getProductItemCode();
	if (StringUtil.isNotBlank(productItemCode)) {
	    ProductItemEntity productItemEntity = productItemDao
		    .findProductItemByCode(productItemCode, new Date());
	    if (null != productItemEntity) {
		queryPricePlanDetailBean.setGoodsTypeCode(productItemEntity
			.getGoodstypeCode());
		queryPricePlanDetailBean.setProductCode(productItemEntity
			.getProductCode());
	    }
	}
	List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList = pricePlanDao
		.queryPricePlanDetailInfo(queryPricePlanDetailBean);
	if (CollectionUtils.isNotEmpty(resultPricePlanDetailBeanList)) {
	    return this.boxHeaveAndLight(resultPricePlanDetailBeanList);
	}
	return null;
    }

    /**
     * <p>
     * 查询价格方案明细总记录数
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-8 下午5:38:51
     * @param queryPricePlanDetailBean
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#queryPricePlanDetailInfoCount(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean)
     */
    @Override
    public Long queryPricePlanDetailInfoCount(QueryPricePlanDetailBean queryBean) {

	PriceValuationEntity entity = new PriceValuationEntity();
	if (null != queryBean) {
	    if (StringUtils.isBlank(queryBean.getPricePlanId())) {
		throw new PricePlanException("价格方案ID不允许为空！");
	    }
	    entity.setPricePlanId(queryBean.getPricePlanId());
	    entity.setPricePlanId(queryBean.getPricePlanId());
	    if (StringUtils.isNotBlank(queryBean.getArrvRegionId())) {
		entity.setArrvRegionId(queryBean.getArrvRegionId());
	    }
	    if (StringUtils.isNotBlank(queryBean.getProductItemCode())) {
		ProductItemEntity productItemEntity = productItemDao
			.findProductItemByCode(queryBean.getProductItemCode(),
				new Date());
		if (null != productItemEntity) {
		    entity.setGoodsTypeCode(productItemEntity
			    .getGoodstypeCode());
		    entity.setProductCode(productItemEntity.getProductCode());
		}
	    }
	}

	return expressPriceValuationDao.countByCodition(entity);
    }

    /**
     * 
     * <p>
     * (同一计费规则封装重货和轻货价格,价格方案同一计费规则下分别存有两条对重货与轻货的明细)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-6 下午5:23:26
     * 
     * @param resultPricePlanDetailBeanList
     * 
     * @return
     * 
     * @see
     */
    private List<PricePlanDetailDto> boxHeaveAndLight(
	    List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList) {
	// 返回客户端list
	List<PricePlanDetailDto> pricePlanDetailList = new ArrayList<PricePlanDetailDto>();
	List<String> pricingValuationIds = new ArrayList<String>();
	PricePlanDetailDto pricePlanDetailDto = null;
	// 遍历数据库list
	for (Iterator<ResultPricePlanDetailBean> iterator = resultPricePlanDetailBeanList
		.iterator(); iterator.hasNext();) {
	    ResultPricePlanDetailBean r1 = iterator.next();
	    // 出现相同计费规则跳过不执行以下操作
	    if (pricingValuationIds.contains(r1.getPricingValuationId())) {
		continue;
	    }
	    pricePlanDetailDto = new PricePlanDetailDto();
	    if (StringUtil.isBlank(r1.getArrvRegionId())) {
		throw new PricePlanException("目的地区域信息为空,请联系管理员检查!", null);
	    }
	    // 目的地区域名称
	    PriceRegionExpressEntity priceRegionEntity = regionExpressService
		    .searchRegionByID(r1.getArrvRegionId(),
			    PricingConstants.PRICING_REGION);
	    pricePlanDetailDto.setArrvRegionName(priceRegionEntity
		    .getRegionName());
	    pricePlanDetailDto.setArrvRegionId(priceRegionEntity.getId());
	    // 组装产品显示名称
	    /*
	     * pricePlanDetailDto.setProductItemName(r1.getProductName()+"-"+r1.
	     * getGoodsTypeName());
	     */
	    pricePlanDetailDto
		    .setMinimumOneTicket(r1.getMinFee().longValue() / NumberConstants.NUMBER_100);
	    // 是否集中接货
	    pricePlanDetailDto.setCentralizePickup(r1.getCentralizePickup());
	    pricePlanDetailDto.setRemark(r1.getRemark());
	    // 设置方案ID
	    pricePlanDetailDto.setPricePlanId(r1.getPricePlanId());
	    ProductItemEntity productItemEntity = productItemDao
		    .findProductItemByGoodCodeAndProductCode(
			    r1.getProductCode(), r1.getGoodsTypeCode(),
			    new Date());
	    // 产品条目信息
	    pricePlanDetailDto.setProductItemId(productItemEntity.getId());
	    pricePlanDetailDto.setProductItemName(productItemEntity.getName());
	    pricePlanDetailDto.setProductItemCode(productItemEntity.getCode());
	    // 价格计费规则ID
	    pricePlanDetailDto.setValuationId(r1.getPricingValuationId());
	    for (int j = 0; j < resultPricePlanDetailBeanList.size(); j++) {
		ResultPricePlanDetailBean r2 = resultPricePlanDetailBeanList
			.get(j);
		// 找到与当前计费规则一致的明细信息
		if (r1.getPricingValuationId().equalsIgnoreCase(
			r2.getPricingValuationId())) {
		    if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
			    .equalsIgnoreCase(r2.getCaculateType())) {
		    	if(r2.getFeeRate()!=null){
		    		BigDecimal heavryPrice = BigDecimal.valueOf(r2
		    				.getFeeRate().doubleValue() / NumberConstants.NUMBER_100);
		    		pricePlanDetailDto.setHeavyPrice(heavryPrice);
		    	}
			// 设置重货价格
		    } else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
			    .equalsIgnoreCase(r2.getCaculateType())) {
			// 设置轻货价格
		    	if(r2.getFeeRate()!=null){
		    		BigDecimal lightPricePrice = BigDecimal.valueOf(r2
		    				.getFeeRate().doubleValue() / NumberConstants.NUMBER_100);
		    		pricePlanDetailDto.setLightPrice(lightPricePrice);
		    	}
		    } else {
			// 其他计费类别是没有的或者说是错误的数据不处理,价格运费类型目前只有按照重量与体积来计算
		    }
		    pricingValuationIds.add(r2.getPricingValuationId());
		}
	    }
	    pricePlanDetailList.add(pricePlanDetailDto);
	}
	return pricePlanDetailList;
    }

    /**
     * 
     * <p>
     * (修改价格方案信息)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 下午7:14:52
     * 
     * @param priceEntity
     * 
     * @see
     */
    @Override
    @Transactional
    public PricePlanEntity modifyPricePlan(PricePlanEntity pricePlanEntity)
	    throws PricePlanException {
	if (null == pricePlanEntity) {
	    throw new PricePlanException("主方案信息缺失,请检查!", null);
	}
	if (pricePlanEntity.getBeginTime().before(new Date())) {
	    throw new PricePlanException("方案生效日期:"
		    + DateUtils.convert(pricePlanEntity.getBeginTime())
		    + "必须大于当前营业日期", null);
	}
	String pricePlanId = pricePlanEntity.getId();
	PricePlanEntity dbPricePlanEntity = pricePlanDao
		.selectByPrimaryKey(pricePlanId);
	try {
	    PropertyUtils.copyProperties(dbPricePlanEntity, pricePlanEntity);
	} catch (IllegalAccessException e) {
	    log.info(e.getMessage(), e);
	} catch (InvocationTargetException e) {
	    log.info(e.getMessage(), e);
	} catch (NoSuchMethodException e) {
	    log.info(e.getMessage(), e);
	}
	pricePlanDao.updateByPrimaryKeySelective(dbPricePlanEntity);
	PriceValuationEntity valuationEntity = new PriceValuationEntity();
	valuationEntity.setPricePlanId(pricePlanId);
	List<PriceValuationEntity> priceValuationEntities = expressPriceValuationDao
		.selectByCodition(valuationEntity);
	if (CollectionUtils.isNotEmpty(priceValuationEntities)) {
	    for (int i = 0; i < priceValuationEntities.size(); i++) {
		PriceValuationEntity priceValuationEntity = priceValuationEntities
			.get(i);
		priceValuationEntity.setDeptRegionId(pricePlanEntity
			.getPriceRegionId());
		expressPriceValuationDao.updateValuation(priceValuationEntity);
	    }
	}
	return pricePlanDao.selectByPrimaryKey(pricePlanId);
    }

    /**
     * 
     * <p>
     * (修改方案明细信息)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 下午7:17:38
     * 
     * @param priceManageMentVo
     * 
     * @see
     */
    @Override
    @Transactional
    public List<PricePlanDetailDto> modifyPricePlanDetail(
	    PriceManageMentVo priceManageMentVo) {
	// 修改计费规则
	PricePlanDetailDto pricePlanDetailDto = priceManageMentVo
		.getPricePlanDetailDto();
	PriceValuationEntity valuationEntity = expressPriceValuationDao
		.selectByPrimaryKey(pricePlanDetailDto.getValuationId());
	// 通过计价条目找到产品与货物信息
	String productItemId = priceManageMentVo.getPricePlanDetailDto()
		.getProductItemId();
	ProductItemEntity productItemEntity = productItemDao
		.selectByPrimaryKey(productItemId);
	// 设置计费规则中产品信息
	ProductEntity productEntity = productService.getProductByCache(
		productItemEntity.getProductCode(), new Date());
	// 设置计费规则中货物信息
	GoodsTypeEntity goodsEntiy = goodsTypeService.getGoodsTypeByCache(
		productItemEntity.getGoodstypeCode(), new Date());
	// 产品ID
	valuationEntity.setProductId(productEntity.getId());
	// 产品编码
	valuationEntity.setProductCode(productItemEntity.getProductCode());
	// 货物ID
	valuationEntity.setGoodsTypeId(goodsEntiy.getId());
	// 货物编码
	valuationEntity.setGoodsTypeCode(productItemEntity.getGoodstypeCode());
	// 计费规则描述
	valuationEntity.setRemarks(pricePlanDetailDto.getRemark());
	// 目的站ID
	valuationEntity.setArrvRegionId(pricePlanDetailDto.getArrvRegionId());
	// 目的站名称
	valuationEntity.setArrvRegionName(pricePlanDetailDto
		.getArrvRegionName());
	// 是否接货
	valuationEntity.setCentralizePickup(pricePlanDetailDto
		.getCentralizePickup());
	// 处理计费规则
	QueryExistPricePlanDetailBean queryBean = new QueryExistPricePlanDetailBean();
	queryBean.setPricePlanId(valuationEntity.getPricePlanId());
	queryBean.setArrvRegionId(valuationEntity.getArrvRegionId());
	queryBean.setProductCode(valuationEntity.getProductCode());
	queryBean.setGoodsTypeCode(valuationEntity.getGoodsTypeCode());
	queryBean.setIsCentralizePickup(valuationEntity.getCentralizePickup());
	queryBean.setValuationId(valuationEntity.getId());
	// 是否已经存在
	List<ResultPricePlanDetailBean> list = pricePlanDao
		.isExistRpeatPricePlanDetailForEdit(queryBean);
	if (CollectionUtils.isNotEmpty(list)) {
	    String centralizeName = "";
	    if (StringUtil.isNotBlank(valuationEntity.getCentralizePickup())) {
		if (StringUtil.equals(valuationEntity.getCentralizePickup(),
			FossConstants.ACTIVE)) {
		    centralizeName = "是";
		} else {
		    centralizeName = "否";
		}
	    }
	    GoodsTypeEntity goodsTypeEntity = goodsTypeService
		    .queryGoodsTypeByGoodTypeCode(
			    valuationEntity.getGoodsTypeCode(), new Date());
	    ProductEntity producTypeEntity = productService.getProductByCache(
		    valuationEntity.getProductCode(), new Date());
	    PriceRegionExpressEntity priceRegionEntity = regionExpressService
		    .searchRegionByID(valuationEntity.getArrvRegionId(),
			    PricingConstants.PRICING_REGION);
	    StringBuilder buf = new StringBuilder();
	    buf.append("目的地区域[" + priceRegionEntity.getRegionName() + "],");
	    buf.append("条目名称[" + producTypeEntity.getName() + "]");
	    buf.append("-" + goodsTypeEntity.getName() + "]");
	    buf.append("是否接货[" + centralizeName + "],");
	    buf.append("已经在当前方案下存在,不能有效添加!");
	    throw new PricePlanException(buf.toString(), null);
	}
	expressPriceValuationDao.updateValuation(valuationEntity);
	// 修改计价明细
	String valuationId = valuationEntity.getId();
	List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = expressPriceCriteriaDetailDao
		.selectByValuationId(valuationId);
	for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : priceCriteriaDetailEntities) {
	    // 最低一票
	    priceCriteriaDetailEntity.setMinFee(pricePlanDetailDto
		    .getMinimumOneTicket());
	    // 按照重量
	    if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
		    .equals(priceCriteriaDetailEntity.getCaculateType())) {
		priceCriteriaDetailEntity.setFeeRate(pricePlanDetailDto
			.getHeavyPrice());
		// 按照体积
	    } else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
		    .equalsIgnoreCase(priceCriteriaDetailEntity
			    .getCaculateType())) {
		priceCriteriaDetailEntity.setFeeRate(pricePlanDetailDto
			.getLightPrice());
	    }
	    // 备注信息
	    priceCriteriaDetailEntity.setDescription(pricePlanDetailDto
		    .getRemark());
	    expressPriceCriteriaDetailDao
		    .updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
	}
	QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
	queryPricePlanDetailBean.setPricePlanId(valuationEntity
		.getPricePlanId());
	List<ResultPricePlanDetailBean> resultPricePlanDetails = pricePlanDao
		.queryPricePlanDetailInfo(queryPricePlanDetailBean);
	return this.boxHeaveAndLight(resultPricePlanDetails);
    }

    /**
     * 
     * <p>
     * (中止方案)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-19 上午10:31:23
     * 
     * @param pricePlanEntity
     * @see
     */
    @Override
    @Transactional
    public void stopPricePlan(PricePlanEntity voPricePlanEntity)
	    throws PricePlanException {
	String pricePlanId = voPricePlanEntity.getId();
	Date stopTime = voPricePlanEntity.getEndTime();
	if (StringUtil.isBlank(pricePlanId)) {
	    throw new PricePlanException("所选价格方案ID为空!", null);
	}
	if (stopTime == null) {
	    throw new PricePlanException("截至日期不能为空!", null);
	}
	// //如果当前为立即中止
	// if(!voPricePlanEntity.getIsPromptly())
	// {
	if (stopTime.before(new Date())) {
	    throw new PricePlanException("中止日期必须大于当前营业日期!", null);
	}
	// }
	PricePlanEntity pricePlanEntity = pricePlanDao
		.selectByPrimaryKey(pricePlanId);
	if (null == pricePlanEntity) {
	    throw new PricePlanException("根据前台参数实体ID,没有找到对应的实体!", null);
	}
	if (stopTime.after(pricePlanEntity.getEndTime())) {
	    throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!", null);
	}
	// 修改计费规则截止日期
	PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
	priceValuationEntity.setPricePlanId(pricePlanId);
	List<PriceValuationEntity> priceValuationEntitys = expressPriceValuationDao
		.selectByCodition(priceValuationEntity);
	if (CollectionUtils.isNotEmpty(priceValuationEntitys)) {
	    for (PriceValuationEntity tempPriceValuationEntity : priceValuationEntitys) {
		tempPriceValuationEntity.setEndTime(stopTime);
		expressPriceValuationDao.updateValuation(tempPriceValuationEntity);
	    }
	}
	// 修改价格方案截止日期
	pricePlanEntity.setEndTime(stopTime);
	pricePlanDao.updateByPrimaryKeySelective(pricePlanEntity);
    }

    /**
     * <p>
     * 新增快递价格方案明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-6 下午3:30:40
     * @param pricePlanDetailDto
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#addPricePlanDetail(com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressPricePlanDetailDto)
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<PricePlanDetailDto> addPricePlanDetail(
	    PricePlanDetailDto pricePlanDetailDto) {
	List<PricePlanDetailDto> resultpricePlanDetails = new ArrayList<PricePlanDetailDto>();
	if (null != pricePlanDetailDto) {
	    Date currentDate = new Date();
	    // 方案id
	    String pricePlanId = pricePlanDetailDto.getPricePlanId();
	    // 目的地区域
	    String arrvRegionId = pricePlanDetailDto.getArrvRegionId();
	    // 是否自提
	    // String selfPickUp = pricePlanDetailDto.getSelfPickUp();
	    // 产品条目
	    String productItemCode = pricePlanDetailDto.getProductItemCode();
	    ProductItemEntity productItemEntity = productItemDao
		    .findProductItemByCode(productItemCode, new Date());
	    String productCode = productItemEntity.getProductCode();
	    String goodsCode = productItemEntity.getGoodstypeCode();
	    List<PricePlanDetailDto> pricePlanDetailDtos = new ArrayList<PricePlanDetailDto>();
	    pricePlanDetailDtos.add(pricePlanDetailDto);
	    // 处理计费规则与计价明细(重货轻货)
	    Map<String, Object> priceCriteriaDetailMap = analysisPricePlanData(
		    pricePlanDetailDtos, pricePlanId);
	    List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap
		    .get(ExpressPricePlanService.VALUATION_RULE);
	    List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap
		    .get(ExpressPricePlanService.PRICING_CRITERIA);
	    // 处理计费规则
	    if (CollectionUtils.isNotEmpty(valuations)) {
		QueryExistPricePlanDetailBean queryBean = new QueryExistPricePlanDetailBean();
		queryBean.setPricePlanId(pricePlanId);
		queryBean.setArrvRegionId(arrvRegionId);
		// queryBean.setIsCentralizePickup(centralizePickup);
		queryBean.setProductCode(productCode);
		queryBean.setGoodsTypeCode(goodsCode);
		List<ResultPricePlanDetailBean> list = pricePlanDao
			.isExistRpeatPricePlanDetailForEdit(queryBean);
		if (CollectionUtils.isNotEmpty(list)) {
		    String centralizeName = "";
		    // if (StringUtil.isNotBlank(centralizePickup)) {
		    // if (StringUtil.equals(centralizePickup,
		    // FossConstants.ACTIVE)) {
		    // centralizeName = "是";
		    // } else {
		    // centralizeName = "否";
		    // }
		    // }
		    GoodsTypeEntity goodsTypeEntity = goodsTypeService
			    .queryGoodsTypeByGoodTypeCode(goodsCode,
				    currentDate);
		    ProductEntity producTypeEntity = productService
			    .getProductByCache(productCode, currentDate);
		    PriceRegionExpressEntity priceRegionEntity = regionExpressService
			    .searchRegionByID(arrvRegionId,
				    PricingConstants.PRICING_REGION);
		    StringBuilder buf = new StringBuilder();
		    buf.append("目的地区域[" + priceRegionEntity.getRegionName()
			    + "],");
		    buf.append("条目名称[" + producTypeEntity.getName() + "]");
		    buf.append("-" + goodsTypeEntity.getName() + "]");
		    buf.append("是否接货[" + centralizeName + "],");
		    buf.append("已经在当前方案下存在,不能有效添加!");
		    throw new PricePlanException(buf.toString(), null);
		}
		for (PriceValuationEntity priceValuationEntity : valuations) {
			expressPriceValuationDao.insertSelective(priceValuationEntity);
		}
	    }
	    // 处理计价明细
	    if (CollectionUtils.isNotEmpty(details)) {
		for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
			expressPriceCriteriaDetailDao
			    .insertSelective(priceCriteriaDetailEntitys);
		}
	    }
	    QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
	    queryPricePlanDetailBean.setPricePlanId(pricePlanId);
	    List<ResultPricePlanDetailBean> resultPricePlanDetails = pricePlanDao
		    .queryPricePlanDetailInfo(queryPricePlanDetailBean);
	    resultpricePlanDetails = this
		    .boxHeaveAndLight(resultPricePlanDetails);
	} else {
	    throw new PricePlanException(
		    PricePlanException.PRICE_PLAN_ADD_DETAILISNULL_ERROR_CODE,
		    null);
	}
	return resultpricePlanDetails;
    }

    /**
     * <p>
     * 新增快递价格明细（1条计费+3条计费明细）
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-7 上午11:50:23
     * @param dto
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#addDetailInfo(com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressPricePlanDetailDto)
     */
    @Transactional
    public ExpressPricePlanDetailDto addDetailInfo(ExpressPricePlanDetailDto dto) {
	if (null == dto) {
	    throw new PricePlanException("快递价格明细不允许为空！", null);
	}
	if (dto.getWeightDownline().compareTo(dto.getWeightOnline()) == NumberConstants.ONE
		|| dto.getWeightDownline().compareTo(dto.getWeightOnline()) == NumberConstants.ZERO) {
	    throw new PricePlanException("首重重量下限必须小于重量上限！", null);
	}
	if (dto.getWeightDownlineOne().compareTo(dto.getWeightOnlineOne()) == NumberConstants.ONE
		|| dto.getWeightDownlineOne().compareTo(
			dto.getWeightOnlineOne()) == NumberConstants.ZERO) {
	    throw new PricePlanException("续重1重量下限必须小于重量上限！", null);
	}
	if (dto.getWeightDownlineTwo().compareTo(dto.getWeightOnlineTwo()) == NumberConstants.ONE
		|| dto.getWeightOnline().compareTo(dto.getWeightDownline()) == NumberConstants.ZERO) {
	    throw new PricePlanException("续重2重量下限必须小于重量上限！", null);
	}
	// 保存计费规则
	ExpressPricePlanDetailDto dto2 = addValuationInfo(dto);

	return addCriteriaInfo(dto2);
    }

    /**
     * <p>
     * 修改快递价格方案明细（1条计费+3条计费明细）
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-9 下午2:36:19
     * @return
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#updateDetailInfo(com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressPricePlanDetailDto)
     */
    @Transactional
    @Override
    public ExpressPricePlanDetailDto updateDetailInfo(
	    ExpressPricePlanDetailDto dto) {
	if (null == dto) {
	    throw new PricePlanException("快递价格明细不允许为空！", null);
	}
	if (dto.getWeightDownline().compareTo(dto.getWeightOnline()) == NumberConstants.ONE
		|| dto.getWeightDownline().compareTo(dto.getWeightOnline()) == NumberConstants.ZERO) {
	    throw new PricePlanException("首重重量下限必须小于重量上限！", null);
	}
	if (dto.getWeightDownlineOne().compareTo(dto.getWeightOnlineOne()) == NumberConstants.ONE
		|| dto.getWeightDownlineOne().compareTo(
			dto.getWeightOnlineOne()) == NumberConstants.ZERO) {
	    throw new PricePlanException("续重1重量下限必须小于重量上限！", null);
	}
	if (dto.getWeightDownlineTwo().compareTo(dto.getWeightOnlineTwo()) == NumberConstants.ONE
		|| dto.getWeightOnline().compareTo(dto.getWeightDownline()) == NumberConstants.ZERO) {
	    throw new PricePlanException("续重2重量下限必须小于重量上限！", null);
	}

	// 保存计费规则
	ExpressPricePlanDetailDto dto2 = updateValuationInfo(dto);

	return updateCriteriaInfo(dto2);
    }

    /**
     * <p>
     * 保存计费规则
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-6 下午4:35:57
     * @param pricePlanDetailDto
     * @return
     * @see
     */
    public ExpressPricePlanDetailDto addValuationInfo(
	    ExpressPricePlanDetailDto pricePlanDetailDto) {
	// 方案id
	String pricePlanId = pricePlanDetailDto.getPricePlanId();
	// 目的地区域
	String arrvRegionId = pricePlanDetailDto.getArrvRegionId();
	// 是否自提
	String selfPickUp = pricePlanDetailDto.getSelfPickUp();
	// 产品条目
	String productItemCode = pricePlanDetailDto.getProductItemCode();
	// 根据条目编码与时间查询条目信息
	ProductItemEntity productItemEntity = productItemDao
		.findProductItemByCode(productItemCode, new Date());
	if(null==productItemEntity){
		 throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_CHECKPRODUCTITEM_ERROR_CODE,"具体产品条目不存在");	
	}
	// 获取 产品CODE.
	String productCode = productItemEntity.getProductCode();
	// 获取 货物CODE.
	String goodsCode = productItemEntity.getGoodstypeCode();

	// 价格方案计费规则实体类
	PriceValuationEntity valuationEntity = new PriceValuationEntity();
	PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePlanId);
	// 设置 目的区域ID.
	valuationEntity.setArrvRegionId(arrvRegionId);
	// 设置 价格方案主信息ID
	valuationEntity.setPricePlanId(pricePlanId);
	// 设置 产品CODE
	valuationEntity.setProductCode(productCode);
	// 设置 货物类型CODE
	valuationEntity.setGoodsTypeCode(goodsCode);
	// 价格方案主信息中的始发区域ID
	valuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	//根据条件查询符合条件的计费规则
	List<PriceValuationEntity> valuationList = expressPriceValuationDao
		.queryInfoByCon(valuationEntity);
	if (CollectionUtils.isNotEmpty(valuationList)) {
	    throw new PricePlanException("目的区域:"
		    + pricePlanDetailDto.getArrvRegionName() + " 产品目录为："
		    + pricePlanDetailDto.getProductItemName()
		    + " 的计费规则在数据库已经存在！", null);
	}
	// (根据价格方案ID查询价格方案主信息)
	
	Date currentTime = new Date();
	// 运价方案ID
//	valuationEntity.setPricePlanId(pricePlanDetailDto.getPricePlanId());
	// 快递默认都为接货
	valuationEntity.setCentralizePickup(FossConstants.YES);
	// 类型-价格方案
	valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
	// 到达区域ID
	valuationEntity.setArrvRegionId(pricePlanDetailDto.getArrvRegionId());
	// 价格方案主信息中的始发区域ID
	valuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	// 价格方案主信息中的始发区域ID
	valuationEntity
		.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
	/* 收集计费规则信息* */
	valuationEntity.setId(UUIDUtils.getUUID());
	valuationEntity.setProductId(productItemEntity.getProductId());
	valuationEntity.setGoodsTypeId(productItemEntity.getGoodstypeId());
	// 设置 币种.
	valuationEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	valuationEntity.setArrvRegionName(pricePlanDetailDto
		.getArrvRegionName());
	valuationEntity.setVersionNo(currentTime.getTime());
	valuationEntity.setBeginTime(currentTime);
	valuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	valuationEntity.setActive(pricePlanEntity.getActive());
	// 创建时间
	valuationEntity.setCreateDate(currentTime);
	// 设置 是否自提(Y/N).
	valuationEntity.setSelfPickUp(selfPickUp);
	// 计价条目
	PriceEntity priceEntry = new PriceEntity();
	priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
	priceEntry.setReceiveDate(currentTime);

	// 根据不同条件查询计价条目
	List<PriceEntity> listPriceEntry = priceEntryDao
		.searchPriceEntryByConditions(priceEntry);
	if (CollectionUtils.isNotEmpty(listPriceEntry)) {
	    priceEntry = listPriceEntry.get(0);
	    // 由于数据库中对该值不能为NULL,必须查询一次计费条目ID
	    valuationEntity.setPricingEntryId(priceEntry.getId());
	    // 计费条目编码
	    valuationEntity.setPricingEntryCode(priceEntry.getCode());
	    valuationEntity.setCode(priceEntry.getCode());
	} else {
	    throw new PricePlanException(
		    PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE,
		    null);
	}
	// 设置 计费规则ID.
	pricePlanDetailDto.setValuationId(valuationEntity.getId());
	// 设置 始发区域ID
	pricePlanDetailDto.setOrigRegionId(valuationEntity.getDeptRegionId());

	// (插入一条计费规则)
	expressPriceValuationDao.insertSelective(valuationEntity);

	return pricePlanDetailDto;
    }

    /**
     * <p>
     * 保存计费明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-6 下午4:35:57
     * @param pricePlanDetailDto
     * @return
     * @see
     */
    @Transactional
    public ExpressPricePlanDetailDto addCriteriaInfo(
	    ExpressPricePlanDetailDto dto) {

	Date currentTime = new Date();
	// 定义首重明细类
	PriceCriteriaDetailEntity entity = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	entity.setPricingValuationId(dto.getValuationId());
	// 设置明细备注
	entity.setDescription(dto.getRemark());
	// 计费类型 按重量
	entity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	entity.setActive(FossConstants.INACTIVE);
	// 设置 数据版本.
	entity.setVersionNo(currentTime.getTime());
	entity.setPricingCriteriaId("");
	entity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	entity.setId(UUIDUtils.getUUID());
	// 设置 最低费用.
	entity.setMinFee(0L);
	// entity.setMaxFee(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX);
	// 获取 价格（首重）.
	//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
	//2015/12/3 liding mod
	entity.setFee(dto.getFirstFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)).longValue());
//	entity.setFee(dto.getFirstFee());
	// 获取 重量下限（首重）.
	entity.setLeftrange(dto.getWeightDownline());
	// 获取 重量上限(首重).
	entity.setRightrange(dto.getWeightOnline());
	// 始发区域ID
	entity.setDeptRegionId(dto.getOrigRegionId());
	// 设置 步进量纲.
	entity.setDimension(BigDecimal.ONE);
	// 设置 计价明细ID(首重).
	dto.setPriceDetailId(entity.getId());

	// (插入一条计价方式明细)
	expressPriceCriteriaDetailDao.insertSelective(entity);

	PriceCriteriaDetailEntity entity1 = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	entity1.setPricingValuationId(dto.getValuationId());
	// 设置明细备注
	entity1.setDescription(dto.getRemark());
	// 计费类型 按重量
	entity1.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	entity1.setActive(FossConstants.INACTIVE);
	// 设置 数据版本.
	entity1.setVersionNo(currentTime.getTime());
	entity1.setPricingCriteriaId("");
	entity1.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	entity1.setId(UUIDUtils.getUUID());
	// 设置 最低费用.
	entity1.setMinFee(0L);
	// entity1.setMaxFee(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX);
	BigDecimal heavyPrice1 = NumberUtils.multiply(dto.getFeeRateOne(),
		PricingConstants.YUTOFEN);
	// 设置 费率或者单价.
	entity1.setFeeRate(heavyPrice1);
	// 获取 重量下限(续重1).
	entity1.setLeftrange(dto.getWeightDownlineOne());
	// 获取 重量上限(续重1).
	entity1.setRightrange(dto.getWeightOnlineOne());
	// 始发区域ID
	entity1.setDeptRegionId(dto.getOrigRegionId());
	// 设置 步进量纲.
	entity1.setDimension(BigDecimal.ONE);
	// 设置 计价明细ID(续重1).
	dto.setOneDetailId(entity1.getId());

	// (插入一条计价方式明细)
	expressPriceCriteriaDetailDao.insertSelective(entity1);

	PriceCriteriaDetailEntity entity2 = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	entity2.setPricingValuationId(dto.getValuationId());
	// 设置明细备注
	entity2.setDescription(dto.getRemark());
	// 计费类型 按重量
	entity2.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	entity2.setActive(FossConstants.INACTIVE);
	// 设置 数据版本.
	entity2.setVersionNo(currentTime.getTime());
	entity2.setPricingCriteriaId("");
	entity2.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	entity2.setId(UUIDUtils.getUUID());
	// 设置 最低费用.
	entity2.setMinFee(0L);
	// entity2.setMaxFee(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX);
	BigDecimal heavyPrice2 = NumberUtils.multiply(dto.getFeeRateTwo(),
		PricingConstants.YUTOFEN);
	// 设置 费率或者单价.
	entity2.setFeeRate(heavyPrice2);
	// 获取 重量下限(续重2).
	entity2.setLeftrange(dto.getWeightDownlineTwo());
	// 获取 重量上限(续重2).
	entity2.setRightrange(dto.getWeightOnlineTwo());
	// 始发区域ID
	entity2.setDeptRegionId(dto.getOrigRegionId());
	// 设置 步进量纲.
	entity2.setDimension(BigDecimal.ONE);
	// 设置 计价明细ID(续重2).
	dto.setTwoDetailId(entity2.getId());

	// (插入一条计价方式明细)
	expressPriceCriteriaDetailDao.insertSelective(entity2);

	return dto;
    }

    /**
     * <p>
     * 修改计费规则
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-6 下午4:35:57
     * @param pricePlanDetailDto
     * @return
     * @see
     */
    public ExpressPricePlanDetailDto updateValuationInfo(
	    ExpressPricePlanDetailDto pricePlanDetailDto) {
	// 方案id
	String pricePlanId = pricePlanDetailDto.getPricePlanId();
	// 目的地区域
	String arrvRegionId = pricePlanDetailDto.getArrvRegionId();
	// 是否自提
	String selfPickUp = pricePlanDetailDto.getSelfPickUp();
	// 产品条目
	String productItemCode = pricePlanDetailDto.getProductItemCode();
	// 根据条目编码与时间查询条目信息
	ProductItemEntity productItemEntity = productItemDao
		.findProductItemByCode(productItemCode, new Date());
	// 获取 产品CODE.
	String productCode = productItemEntity.getProductCode();
	// 获取 货物CODE.
	String goodsCode = productItemEntity.getGoodstypeCode();

	// 价格方案计费规则实体类
	PriceValuationEntity valuationEntity = new PriceValuationEntity();
	// (根据价格方案ID查询价格方案主信息)
	PricePlanEntity pricePlanEntity = pricePlanDao
			.selectByPrimaryKey(pricePlanId);
	// 设置 目的区域ID.
	valuationEntity.setArrvRegionId(arrvRegionId);
	// 设置 价格方案主信息ID
	valuationEntity.setPricePlanId(pricePlanId);
	// 设置 产品CODE
	valuationEntity.setProductCode(productCode);
	// 设置 货物类型CODE
	valuationEntity.setGoodsTypeCode(goodsCode);
	// 价格方案主信息中的始发区域ID
	valuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	valuationEntity.setId(pricePlanDetailDto.getValuationId());
	// 根据条件查询符合条件的计费规则
	List<PriceValuationEntity> valuationList = expressPriceValuationDao
		.queryInfoByCon(valuationEntity);

	if (CollectionUtils.isNotEmpty(valuationList)) {
	    throw new PricePlanException("目的区域:"
		    + pricePlanDetailDto.getArrvRegionName() + " 产品目录为："
		    + pricePlanDetailDto.getProductItemName()
		    + " 的计费规则在数据库已经存在！",null);
	}
	
	Date currentTime = new Date();
	// 运价方案ID
	valuationEntity.setPricePlanId(pricePlanDetailDto.getPricePlanId());
	// 快递默认都为接货
	valuationEntity.setCentralizePickup(FossConstants.YES);
	// 类型-价格方案
	valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
	// 到达区域ID
	valuationEntity.setArrvRegionId(pricePlanDetailDto.getArrvRegionId());
	// 价格方案主信息中的始发区域ID
	valuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	// 价格方案主信息中的始发区域ID
	valuationEntity
		.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
	/* 收集计费规则信息* */
	// 设置计价规则ID
	valuationEntity.setId(pricePlanDetailDto.getValuationId());
	valuationEntity.setProductId(productItemEntity.getProductId());
	valuationEntity.setGoodsTypeId(productItemEntity.getGoodstypeId());
	// 设置 币种.
	valuationEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	valuationEntity.setArrvRegionName(pricePlanDetailDto
		.getArrvRegionName());
	valuationEntity.setVersionNo(currentTime.getTime());
	valuationEntity.setBeginTime(currentTime);
	valuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	valuationEntity.setActive(FossConstants.INACTIVE);
	// 创建时间
	valuationEntity.setCreateDate(currentTime);
	// 设置 是否自提(Y/N).
	valuationEntity.setSelfPickUp(selfPickUp);
	// 计价条目
	PriceEntity priceEntry = new PriceEntity();
	priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
	priceEntry.setReceiveDate(currentTime);

	// 根据不同条件查询计价条目
	List<PriceEntity> listPriceEntry = priceEntryDao
		.searchPriceEntryByConditions(priceEntry);
	if (CollectionUtils.isNotEmpty(listPriceEntry)) {
	    priceEntry = listPriceEntry.get(0);
	    // 由于数据库中对该值不能为NULL,必须查询一次计费条目ID
	    valuationEntity.setPricingEntryId(priceEntry.getId());
	    // 计费条目编码
	    valuationEntity.setPricingEntryCode(priceEntry.getCode());
	    valuationEntity.setCode(priceEntry.getCode());
	} else {
	    throw new PricePlanException(
		    PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE);
	}
	// 设置 计费规则ID.
	pricePlanDetailDto.setValuationId(valuationEntity.getId());
	// 设置 始发区域ID
	pricePlanDetailDto.setOrigRegionId(valuationEntity.getDeptRegionId());

	// (插入一条计费规则)
	expressPriceValuationDao.updateValuation(valuationEntity);

	return pricePlanDetailDto;
    }

    /**
     * <p>
     * 修改计费明细
     * </p>
     * 
     * @author 094463-foss-xieyantao
     * @date 2013-8-6 下午4:35:57
     * @param pricePlanDetailDto
     * @return
     * @see
     */
    @Transactional
    public ExpressPricePlanDetailDto updateCriteriaInfo(
	    ExpressPricePlanDetailDto dto) {

	Date currentTime = new Date();
	// 定义首重明细类
	PriceCriteriaDetailEntity entity = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	entity.setPricingValuationId(dto.getValuationId());
	// 设置明细备注
	entity.setDescription(dto.getRemark());
	// 计费类型 按重量
	entity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	entity.setActive(FossConstants.INACTIVE);
	// 设置 数据版本.
	entity.setVersionNo(currentTime.getTime());
	entity.setPricingCriteriaId("");
	entity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	// 设置ID
	entity.setId(dto.getPriceDetailId());
	// 设置 最低费用.
	entity.setMinFee(0L);
	// entity.setMaxFee(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX);
	// 获取 价格（首重）.
	//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
	//2015/12/3 liding mod
	entity.setFee(dto.getFirstFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)).longValue());
//	entity.setFee(dto.getFirstFee());
	// 获取 重量下限（首重）.
	entity.setLeftrange(dto.getWeightDownline());
	// 获取 重量上限(首重).
	entity.setRightrange(dto.getWeightOnline());
	// 始发区域ID
	entity.setDeptRegionId(dto.getOrigRegionId());
	// 设置 步进量纲.
	entity.setDimension(BigDecimal.ONE);
	// 设置 计价明细ID(首重).
	dto.setPriceDetailId(entity.getId());

	// (插入一条计价方式明细)
	expressPriceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(entity);

	PriceCriteriaDetailEntity entity1 = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	entity1.setPricingValuationId(dto.getValuationId());
	// 设置明细备注
	entity1.setDescription(dto.getRemark());
	// 计费类型 按重量
	entity1.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	entity1.setActive(FossConstants.INACTIVE);
	// 设置 数据版本.
	entity1.setVersionNo(currentTime.getTime());
	entity1.setPricingCriteriaId("");
	entity1.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	// 设置ＩＤ
	entity1.setId(dto.getOneDetailId());
	// 设置 最低费用.
	entity1.setMinFee(0L);
	// entity1.setMaxFee(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX);
	BigDecimal heavyPrice1 = NumberUtils.multiply(dto.getFeeRateOne(),
		PricingConstants.YUTOFEN);
	// 设置 费率或者单价.
	entity1.setFeeRate(heavyPrice1);
	// 获取 重量下限(续重1).
	entity1.setLeftrange(dto.getWeightDownlineOne());
	// 获取 重量上限(续重1).
	entity1.setRightrange(dto.getWeightOnlineOne());
	// 始发区域ID
	entity1.setDeptRegionId(dto.getOrigRegionId());
	// 设置 步进量纲.
	entity1.setDimension(BigDecimal.ONE);
	// 设置 计价明细ID(续重1).
	dto.setOneDetailId(entity1.getId());

	// (插入一条计价方式明细)
	expressPriceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(entity1);

	PriceCriteriaDetailEntity entity2 = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	entity2.setPricingValuationId(dto.getValuationId());
	// 设置明细备注
	entity2.setDescription(dto.getRemark());
	// 计费类型 按重量
	entity2.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	entity2.setActive(FossConstants.INACTIVE);
	// 设置 数据版本.
	entity2.setVersionNo(currentTime.getTime());
	entity2.setPricingCriteriaId("");
	entity2.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	// 设置ＩＤ
	entity2.setId(dto.getTwoDetailId());
	// 设置 最低费用.
	entity2.setMinFee(0L);
	// entity2.setMaxFee(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX);
	BigDecimal heavyPrice2 = NumberUtils.multiply(dto.getFeeRateTwo(),
		PricingConstants.YUTOFEN);
	// 设置 费率或者单价.
	entity2.setFeeRate(heavyPrice2);
	// 获取 重量下限(续重2).
	entity2.setLeftrange(dto.getWeightDownlineTwo());
	// 获取 重量上限(续重2).
	entity2.setRightrange(dto.getWeightOnlineTwo());
	// 始发区域ID
	entity2.setDeptRegionId(dto.getOrigRegionId());
	// 设置 步进量纲.
	entity2.setDimension(BigDecimal.ONE);
	// 设置 计价明细ID(续重2).
	dto.setTwoDetailId(entity2.getId());

	// (插入一条计价方式明细)
	expressPriceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(entity2);

	return dto;
    }

    /**
     * 
     * <p>
     * (修改价格方案信息)
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-12 下午7:14:52
     * 
     * @param priceEntity
     * 
     * @see
     */
    @Override
    @Transactional
    public void modifyPricePlan(PriceManageMentVo priceManageMentVo) {
	// 修改方案信息
	PricePlanEntity priceEntity = priceManageMentVo.getPricePlanEntity();
	if (null != priceEntity) {
	    PricePlanEntity dbEntity = pricePlanDao
		    .selectByPrimaryKey(priceEntity.getId());
	    dbEntity.setName(priceEntity.getName());
	    dbEntity.setBeginTime(priceEntity.getBeginTime());
	    dbEntity.setPriceRegionId(priceEntity.getPriceRegionId());
	    dbEntity.setPriceRegionCode(priceEntity.getPriceRegionCode());
	    dbEntity.setPriceRegionName(priceEntity.getPriceRegionName());
	    dbEntity.setDescription(priceEntity.getDescription());
	    if(isAloneQuotation(dbEntity.getCustomerCode())) {
	    	pricePlanDao.updateByPrimaryKeySelective(dbEntity);
	    }
	    
	}
    }

    /**
     * <p>(删除价格明细-草稿状态 删除步骤,按照顺序删除计费明细,计费规则)</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-9-17 下午4:42:06
     * @param valuationIds
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#deletePriceDetailPlan(java.util.List)
     */
    @Override
    @Transactional
    public int deletePriceDetailPlan(
	    List<String> valuationIds) {
	if (CollectionUtils.isNotEmpty(valuationIds)) {
	    // 删除前，获得计价方案ID
	    PriceValuationEntity pricevaluationEntity = expressPriceValuationDao
		    .selectByPrimaryKey(valuationIds.get(0));
	    QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
	    queryPricePlanDetailBean.setPricePlanId(pricevaluationEntity
		    .getPricePlanId());
	    for (int i = 0; i < valuationIds.size(); i++) {
		String valuationId = valuationIds.get(i);
		expressPriceCriteriaDetailDao
			.deleteCriteriaDetailByValuationId(valuationId);
		expressPriceValuationDao.deleteByPrimaryKey(valuationId);
	    }
//	    pricePlanDetailDtos = this.boxHeaveAndLight(pricePlanDao
//		    .queryPricePlanDetailInfo(queryPricePlanDetailBean));
	}
	return FossConstants.SUCCESS;
    }

    /**
     * 批量导入价格数据
     * 
     * @author zhangdongping
     * 
     * @date 2012-12-24 下午5:52:56
     * 
     * @param detailMap
     * 
     * @param priceRegionEntityMap
     * 
     * @param productEntityMap
     * 
     */
    @Transactional
    public void addPricePlanBatch(
	    Map<String, List<PricePlanDetailDto>> detailMap,
	    Map<String, PriceRegionExpressEntity> priceRegionEntityMap,
	    Map<String, ProductEntity> productEntityMap,Map<String,CustomerEntity> customerMap) {
	// 检查数据
	if (detailMap == null || detailMap.size() < 1
		|| priceRegionEntityMap == null
		|| priceRegionEntityMap.size() < 1 || productEntityMap == null
		|| productEntityMap.size() < 1) {
	    return;
	}
	// 计价条目查询
	PriceEntity priceEntry = new PriceEntity();
	priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
	priceEntry.setReceiveDate(new Date());
	List<PriceEntity> listPriceEntry = priceEntryDao
		.searchPriceEntryByConditions(priceEntry);
	if (CollectionUtils.isNotEmpty(listPriceEntry)) {
	    priceEntry = listPriceEntry.get(0);
	} else {
	    throw new PricePlanException(
		    PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE,
		    null);
	}
	// 查询货物类型，固定的常量
	GoodsTypeEntity goodsTypeEntity = goodsTypeService
		.queryGoodsTypeByGoodTypeCode(
			GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001,
			new Date());
	Set<Entry<String, List<PricePlanDetailDto>>> detailSet = detailMap
		.entrySet();
	for (Entry<String, List<PricePlanDetailDto>> entry : detailSet) {
	    String regionName = entry.getKey();
	    String[] splitRegionName = regionName.split("#");
		if(splitRegionName != null && splitRegionName.length>0) {
			regionName = splitRegionName[splitRegionName.length-1];
		}
	    List<PricePlanDetailDto> detailList = entry.getValue();
	    // 添加价格主方案信息
	    PriceRegionExpressEntity deptRegion = priceRegionEntityMap
		    .get(regionName);
	    if (deptRegion == null) {
		continue;
	    }
	    PricePlanEntity pricePlanEntity = new PricePlanEntity();
	    pricePlanEntity.setPriceRegionId(deptRegion.getId());
	    pricePlanEntity.setPriceRegionCode(deptRegion.getRegionCode());
	    // 设置方案名称
	    pricePlanEntity.setName(regionName + "导入价格方案");
	  //设置客户名称和客户编码
	    if(splitRegionName != null && splitRegionName.length==2) {
	    	pricePlanEntity.setCustomerName(customerMap.get(splitRegionName[0]).getName());
	    	pricePlanEntity.setCustomerCode(customerMap.get(splitRegionName[0]).getCusCode());
	    }
	  // 设置方案名称
	    if(StringUtils.isBlank(pricePlanEntity.getCustomerCode())) {
	     pricePlanEntity.setName(regionName + "导入价格方案");
	    } else {
	     pricePlanEntity.setName(pricePlanEntity.getCustomerName()+regionName + "导入价格方案");
	    }
	    // 方案生效日期默认明天
	    pricePlanEntity.setBeginTime(DateUtils.getStartDatetime(new Date(),
		    1));
	    // 准备数据
	    pricePlanEntity = getPricePlanValue(pricePlanEntity);
	    // 插入方案主信息表
	    if(isAloneQuotation(pricePlanEntity.getCustomerCode())) {
	    	pricePlanDao.insert(pricePlanEntity);
	    }
	    String pricePlanId = pricePlanEntity.getId();
	    // 价格方案明细插入
	    for (int loop = 0; loop < detailList.size(); loop++) {
		PricePlanDetailDto pricePlanDetailDto = detailList.get(loop);
		pricePlanDetailDto.setPricePlanId(pricePlanId);
		// 设置状态
		pricePlanDetailDto.setActive(FossConstants.INACTIVE);
		PriceRegionExpressEntity areiveRegionEntity = priceRegionEntityMap
			.get(pricePlanDetailDto.getArrvRegionName());
		// 设置到达区域ID
		pricePlanDetailDto.setArrvRegionId(areiveRegionEntity.getId());
		ProductEntity procuctEntity = productEntityMap
			.get(pricePlanDetailDto.getProductItemName());
		addPriceBatchPlanDetail(pricePlanDetailDto, deptRegion,
			procuctEntity, priceEntry, goodsTypeEntity);
	    }
	    
	}
	
    }

    /**
     * 添加价格数据
     * 
     * @author zhangdongping
     * 
     * @date 2012-12-24 下午5:51:49
     * 
     * @param pricePlanDetailDto
     * 
     * @param deptRegion
     * 
     * @param procuctEntity
     * 
     * @param priceEntry
     * 
     * @param goodsTypeEntity
     * 
     * @see
     */
    private void addPriceBatchPlanDetail(PricePlanDetailDto pricePlanDetailDto,
	    PriceRegionExpressEntity deptRegion, ProductEntity procuctEntity,
	    PriceEntity priceEntry, GoodsTypeEntity goodsTypeEntity) {
	// 处理计费规则与计价明细(重货轻货)
	Map<String, Object> priceCriteriaDetailMap = analysisBatchPricePlanData(
		pricePlanDetailDto, deptRegion, procuctEntity, priceEntry,
		goodsTypeEntity);
	@SuppressWarnings("unchecked")
	// 取出计费规则数据
	List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap
		.get(ExpressPricePlanService.VALUATION_RULE);
	@SuppressWarnings("unchecked")
	// 取出价格详细
	List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap
		.get(ExpressPricePlanService.PRICING_CRITERIA);

	// 处理计费规则数据
	for (PriceValuationEntity priceValuationEntity : valuations) {
		expressPriceValuationDao.insertSelective(priceValuationEntity);
	}
	// 批量处理计价明细
	if (CollectionUtils.isNotEmpty(details)) {
	    for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
	    expressPriceCriteriaDetailDao
			.insertSelective(priceCriteriaDetailEntitys);
	    }
	}
    }

    /**
     * 准备批量数据
     * 
     * @author zhangdongping
     * 
     * @date 2012-12-24 下午5:51:34
     * 
     * @param detail
     * 
     * @param deptRegion
     * 
     * @param procuctEntity
     * 
     * @param priceEntry
     * 
     * @param goodsTypeEntity
     * 
     * @return
     * 
     * @see
     */
    private Map<String, Object> analysisBatchPricePlanData(
	    PricePlanDetailDto detail, PriceRegionExpressEntity deptRegion,
	    ProductEntity procuctEntity, PriceEntity priceEntry,
	    GoodsTypeEntity goodsTypeEntity) {
	Date currentTime = DateUtils.getStartDatetime(new Date(), 1);
	// 计费规则
	List<PriceValuationEntity> valuationEntitys = new ArrayList<PriceValuationEntity>();
	// 重货
	List<PriceCriteriaDetailEntity> heavyList = new ArrayList<PriceCriteriaDetailEntity>();

	// 整理后的数据容器,主要是对计费规则,计价明细的数据收集
	Map<String, Object> priceCriteriaDetailMap = new HashMap<String, Object>();
	PriceValuationEntity valuationEntity = new PriceValuationEntity();
	detail.setArrvRegionName(detail.getArrvRegionName());
	// 设置产品code
	valuationEntity.setProductCode(procuctEntity.getCode());
	// 设置货物类型code
	valuationEntity.setGoodsTypeCode(goodsTypeEntity.getCode());
	// 运价方案ID
	valuationEntity.setPricePlanId(detail.getPricePlanId());
	// 是否集中接货
	valuationEntity.setCentralizePickup(detail.getCentralizePickup());
	// 是否自提
	valuationEntity.setSelfPickUp(detail.getSelfPickUp());
	// 类型-价格方案
	valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
	// 到达区域ID
	valuationEntity.setArrvRegionId(detail.getArrvRegionId());
	valuationEntity.setDeptRegionId(deptRegion.getId());
	valuationEntity
		.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
	/* 收集计费规则信息* */
	valuationEntity.setId(UUIDUtils.getUUID());
	valuationEntity.setProductId(procuctEntity.getId());
	valuationEntity.setGoodsTypeId(goodsTypeEntity.getId());
	// 货币类型
	valuationEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	valuationEntity.setArrvRegionName(detail.getArrvRegionName());
	valuationEntity.setVersionNo(currentTime.getTime());
	valuationEntity.setBeginTime(currentTime);
	valuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	// 创建时间
	valuationEntity.setCreateDate(currentTime);
	// 计价条目
	valuationEntity.setPricingEntryId(priceEntry.getId());
	// 计费条目编码
	valuationEntity.setPricingEntryCode(priceEntry.getCode());
	valuationEntity.setCode(priceEntry.getCode());
	valuationEntity.setActive(detail.getActive());
	valuationEntitys.add(valuationEntity);
	// 重货数据
	// 首重
	PriceCriteriaDetailEntity firstEntity = new PriceCriteriaDetailEntity();
	// 续重1
	PriceCriteriaDetailEntity addOneEntity = new PriceCriteriaDetailEntity();
	// 续重2
	PriceCriteriaDetailEntity addTwoEntity = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	firstEntity.setPricingValuationId(valuationEntity.getId());
	// 设置明细备注
	firstEntity.setDescription(detail.getRemark());
	// 计费类型
	// 按重量
	firstEntity
		.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	//DN201510290016 DP-FOSS-快递价格方案首重价格输入优化需求
	//2015/12/3 liding mod
	// 重货价格 换算成分
	firstEntity.setFee(detail.getFirstFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)).longValue());
//	firstEntity.setFee(detail.getFirstFee());
	// 最低一票
	firstEntity.setMinFee(detail.getMinimumOneTicket());
	firstEntity.setVersionNo(currentTime.getTime());
	// 为空，原因该属性关联关系的实体已经不存在。后续删除该字段
	firstEntity.setPricingCriteriaId("");
	// 计费规则
	firstEntity
		.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	firstEntity.setId(UUIDUtils.getUUID());
	// 左区间
	firstEntity.setLeftrange(detail.getWeightDownline());
	// 右区间
	firstEntity.setRightrange(detail.getWeightOnline());
	// 始发区域ID
	firstEntity.setDeptRegionId(deptRegion.getId());
	firstEntity.setActive(detail.getActive());
	heavyList.add(firstEntity);
	// 续重1
	// 关联计费规则ID
	addOneEntity.setPricingValuationId(valuationEntity.getId());
	// 设置明细备注
	addOneEntity.setDescription(detail.getRemark());
	// 计费类型 按体积
	addOneEntity
		.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	addOneEntity.setVersionNo(currentTime.getTime());
	addOneEntity.setActive(detail.getActive());
	// 轻货价格 换算造成分
	addOneEntity.setFeeRate(NumberUtils.multiply(detail.getFeeRateOne(),
		PricingConstants.YUTOFEN));
	// 最低一票
	addOneEntity.setMinFee(detail.getMinimumOneTicket());
	addOneEntity.setPricingCriteriaId("");
	addOneEntity
		.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	addOneEntity.setLeftrange(detail.getWeightDownlineOne());
	addOneEntity.setRightrange(detail.getWeightOnlineOne());
	addOneEntity.setId(UUIDUtils.getUUID());
	// 始发区域ID
	addOneEntity.setDeptRegionId(deptRegion.getId());
	addOneEntity.setActive(detail.getActive());
	// 组装数据
	heavyList.add(addOneEntity);

	// 续重2
	// 关联计费规则ID
	addTwoEntity.setPricingValuationId(valuationEntity.getId());
	// 设置明细备注
	addTwoEntity.setDescription(detail.getRemark());
	// 计费类型 按体积
	addTwoEntity
		.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	addTwoEntity.setVersionNo(currentTime.getTime());
	addTwoEntity.setActive(detail.getActive());
	// 轻货价格 换算造成分
	addTwoEntity.setFeeRate(NumberUtils.multiply(detail.getFeeRateTwo(),
		PricingConstants.YUTOFEN));
	// 最低一票
	addTwoEntity.setMinFee(detail.getMinimumOneTicket());
	addTwoEntity.setPricingCriteriaId("");
	addTwoEntity
		.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	addTwoEntity.setLeftrange(detail.getWeightDownlineTwo());
	addTwoEntity.setRightrange(detail.getWeightOnlineTwo());
	addTwoEntity.setId(UUIDUtils.getUUID());
	// 始发区域ID
	addTwoEntity.setDeptRegionId(deptRegion.getId());
	addTwoEntity.setActive(detail.getActive());
	// 组装数据
	heavyList.add(addTwoEntity);
	priceCriteriaDetailMap.put(ExpressPricePlanService.VALUATION_RULE,
		valuationEntitys);
	priceCriteriaDetailMap.put(ExpressPricePlanService.PRICING_CRITERIA,
		heavyList);
	return priceCriteriaDetailMap;
    }

    /**
     * 
     * <p>
     * 查询单个方案主信息
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-23 下午3:13:02
     * 
     * @param id
     * 
     * @return
     * 
     * @see
     */
    @Override
    public PricePlanEntity getPricePlanEntity(String id) {
	PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(id);
	PriceRegionExpressEntity priceRegionEntity = regionExpressService
		.searchRegionByID(pricePlanEntity.getPriceRegionId(),
			PricingConstants.PRICING_REGION);
	pricePlanEntity.setPriceRegionName(priceRegionEntity.getRegionName());
	return pricePlanEntity;
    }

    /**
     * <p>价格方案明细导出</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-22 下午5:30:06
     * @param queryPricePlanDetailBean
     * @return 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService#exportPricePlanDetail(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean)
     */
    @Override
    public ExportResource exportPricePlanDetail(QueryPricePlanDetailBean queryBean) {
	//查询价格方案明细分页
	List<ExpressPricePlanDetailDto> list =  queryPricePlanDetailInfo(queryBean, NumberConstants.ZERO, Integer.MAX_VALUE);
	
	//集合验证
	if (null == list) {
	    // 定义一个集合
	    list = new ArrayList<ExpressPricePlanDetailDto>();
	}
	// 定义集合
	List<List<String>> resultList = new ArrayList<List<String>>();
	
	for(ExpressPricePlanDetailDto dto : list){
	    List<String> result = exportInfoList(dto);
	    resultList.add(result);
	}
	
	ExportResource sheet = new ExportResource();
	
	//设置Excel表头
	sheet.setHeads(PricingColumnConstants.EXPRESS_PRICE_PLAN_DETAIL_TITLE);
	//设置导出数据
	sheet.setRowList(resultList);
	
	return sheet;
    }
    
    /**
     * <p>导出信息封装</p> 
     * @author 094463-foss-xieyantao
     * @date 2013-8-23 下午2:15:10
     * @param entity
     * @return
     * @see
     */
    private List<String> exportInfoList(ExpressPricePlanDetailDto entity){
	//定义一个机会
	List<String> result = new ArrayList<String>();
	result.add(entity.getArrvRegionName());
	result.add(entity.getProductItemName());
	result.add(entity.getFirstFee().toString());
	result.add(entity.getWeightDownlineOne().toString());
	result.add(entity.getWeightOnlineOne().toString());
	result.add(entity.getFeeRateOne().toString());
	result.add(entity.getWeightDownlineTwo().toString());
	result.add(entity.getWeightOnlineTwo().toString());
	result.add(entity.getFeeRateTwo().toString());
	if(StringUtils.equals(FossConstants.YES, entity.getSelfPickUp())){
	    result.add("是");
	}else {
	    result.add("否");
	}
	
	//返回集合
	return result;
    }

    /**
     * 
     * <p>
     * 填充方案 sheet row
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-27 上午9:59:41
     * 
     * @param effectivePlanDetailEntity
     * 
     * @return
     * 
     * @see
     */
    private List<String> exportPlatform(PricePlanEntity pricePlanEntity) {
	List<String> result = new ArrayList<String>();
	result.add(pricePlanEntity.getCustomerName());
	result.add(pricePlanEntity.getCustomerCode());
	result.add(pricePlanEntity.getName());
	result.add(pricePlanEntity.getPriceRegionName());
	String modifyDate = DateUtils.convert(pricePlanEntity.getModifyDate(),
		DateUtils.DATE_FORMAT);
	String beginDate = DateUtils.convert(pricePlanEntity.getBeginTime(),
		DateUtils.DATE_FORMAT);
	String endDate = DateUtils.convert(pricePlanEntity.getEndTime(),
		DateUtils.DATE_FORMAT);
	result.add(modifyDate);
	result.add(pricePlanEntity.getModifyUser());
	result.add(beginDate);
	result.add(endDate);
	if (StringUtil.equalsIgnoreCase(FossConstants.ACTIVE,
		pricePlanEntity.getActive())) {
	    result.add("是");
	} else {
	    result.add("否");
	}
	return result;
    }

    /**
     * 
     * <p>
     * 价格方案导出
     * </p>
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-27 上午10:06:22
     * 
     * @param queryPricePlanDetailBean
     * 
     * @return
     * 
     * @see
     */
    @Override
    public ExportResource exportPricePlan(PricePlanEntity pricePlanEntity) {
	ExportResource exportResource = new ExportResource();
	List<PricePlanEntity> pricePlanEntitys = queryPricePlanBatchInfo(
		pricePlanEntity, PricingConstants.ZERO, Integer.MAX_VALUE);
	if (CollectionUtils.isEmpty(pricePlanEntitys)) {
	    return null;
	}
	List<PricePlanEntity> pricePlanList = convertToRegionName(pricePlanEntitys);
	List<List<String>> rowList = new ArrayList<List<String>>();
	for (PricePlanEntity tempPricePlan : pricePlanList) {
	    List<String> row = exportPlatform(tempPricePlan);
	    rowList.add(row);
	}
	if(pricePlanEntity.getTransportFlag()!=null && "2".equals(pricePlanEntity.getTransportFlag())) {
		exportResource.setHeads(PricingColumnConstants.EXP_PRICE_PLAN_TITLE);
	} else {
		exportResource.setHeads(PricingColumnConstants.PRICE_PLAN_TITLE);
	}
	
	exportResource.setRowList(rowList);
	return exportResource;
    }
    
    
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService.queryExpressPricePlanByCondition
     * @Description:查询满足条件的客户快递价格方案   供外部接口调用
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-14 下午4:58:56
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
     */
    @Override
    public List<PriceValuationEntity> queryExpressPricePlanByCondition(
    	    PriceValuationEntity entity) {
    	if (entity == null) {
    	    return null;
    	} else {
    	    // 查询激活状态下的数据
    	    entity.setActive(FossConstants.ACTIVE);
    	    return expressPriceValuationDao.queryPriceValuationsByCondition(entity);
    	}
        }
    /**
     * 判断客户是否单独定价
     *
     */
    /**
     * 判断客户是否单独定价
     *
     */
    public boolean isAloneQuotation(String customerCode) {
    	CusBargainEntity cusBargainEntity = null;
    	if(customerCode!=null && !"".equals(customerCode)) {
    		 cusBargainEntity = cusBargainService.queryCusBargainInfos(customerCode, null);
    	}else {
    		return true;
    	}
    	if(cusBargainEntity==null) {
    		throw new PricePlanException("当前客户不是合同客户",null);
    	}
    	if(StringUtil.isBlank(cusBargainEntity.getIsAloneQuotation())) {
    		throw new PricePlanException("当前方案所选客户合同是否单独定价不满足条件，请确认！",null);

    	} else {
    		if(StringUtils.equals(FossConstants.NO,cusBargainEntity.getIsAloneQuotation())){
    			throw new PricePlanException("当前方案所选客户合同是否单独定价不满足条件，请确认！",null);
    		}
    	}
		return true;
    	
    }
}
