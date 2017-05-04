/******************************************************************************************************
  	     *	initial comments.
  	     *	SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *  
	     *  
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	     *    
	     *    
	     *    
	     *    
	     *    
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *
             *
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *
             *
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *
             *
             *		     比对生效日期必须大于上一个版本生效日期。
             *
             *
             *
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *
             *
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *
             *
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
             *
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *
	     *
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *
	     *
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *  
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *  
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *
	     *
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *
	     *
	     *	即时间可以在今天和今天以后任意调整，
	     *
	     *	但是不能调整为昨天的时间
	     ************************************************************************************* */

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/AirPricePlanService.java
 * 
 * FILE NAME        	: AirPricePlanService.java
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
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.dict.api.server.service.IDataDictionaryValueService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionAirService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionAirEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
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
 * 空运价格管理信息.
 *
 * @author 岳洪杰
 * @date 2012-10-12 上午10:55:12
 * @since
 * @version
 */
public class AirPricePlanService implements IAirPricePlanService{
	
	private static final Logger log = Logger.getLogger(AirPricePlanService.class);
    /**
     * 价格方案主信息
     */
    @Inject
    IPricePlanDao pricePlanDao;
    /**
     *  计费规则
     */
    @Inject
    IPriceValuationDao priceValuationDao;
    /** 
     * 
     * 计费明细
     * 
     */
    @Inject
    IPriceCriteriaDetailDao priceCriteriaDetailDao;
    /** 
     * 
     * 产品条目
     *
     */
    @Inject
    IProductItemDao productItemDao;
    /**
     * 
     * 计价条目
     * 
     */
    @Inject
    IPriceEntryDao priceEntryDao;
    /**
     * 
     * 价格计算表达式
     * 
     */
    @Inject
    IPriceRuleDao priceRuleDao;
    /** 
     * 
     * 产品 
     * 
     */
    @Inject
    IProductService productService;
    /**
     * 
     * 货物
     * 
     */
    @Inject
    IGoodsTypeService goodsTypeService;
    /**
     * 
     * 空运价格区域
     * 
     */
    IRegionAirService regionAirService;
    /**
     * 
     * 用户信息
     *
     */
    @Inject
    IEmployeeService employeeService;
    /**
     * 
     * 数据字典
     * 
     */
    @Inject
    IDataDictionaryValueService dataDictionaryValueService;
    /**
     * <p>空运价格区域</p>.
     *
     * @param regionAirService the new region air service
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-13 下午3:07:48
     * 
     * @see
     */
    public void setRegionAirService(IRegionAirService regionAirService) {
        this.regionAirService = regionAirService;
    }
    /**
     * Sets the employee service.
     *
     * @param employeeService the new employee service
     */
    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    /**
     * Sets the product service.
     *
     * @param productService the new product service
     */
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
    /**
     * Sets the goods type service.
     *
     * @param goodsTypeService the new goods type service
     */
    public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
        this.goodsTypeService = goodsTypeService;
    }
    /**
     * Sets the price rule dao.
     *
     * @param priceRuleDao the new price rule dao
     */
    public void setPriceRuleDao(IPriceRuleDao priceRuleDao) {
	this.priceRuleDao = priceRuleDao;
    }
    /**
     * Sets the price entry dao.
     *
     * @param priceEntryDao the new price entry dao
     */
    public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
	this.priceEntryDao = priceEntryDao;
    }
    /**
     * Sets the product item dao.
     *
     * @param productItemDao the new product item dao
     */
    public void setProductItemDao(IProductItemDao productItemDao) {
	this.productItemDao = productItemDao;
    }
    /**
     * Sets the price valuation dao.
     *
     * @param priceValuationDao the new price valuation dao
     */
    public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
	this.priceValuationDao = priceValuationDao;
    }
    /**
     * Sets the price criteria detail dao.
     *
     * @param priceCriteriaDetailDao the new price criteria detail dao
     */
    public void setPriceCriteriaDetailDao(IPriceCriteriaDetailDao priceCriteriaDetailDao) {
    	this.priceCriteriaDetailDao = priceCriteriaDetailDao;
    }
    /**
     * Sets the price plan dao.
     *
     * @param pricePlanDao the new price plan dao
     */
    public void setPricePlanDao(IPricePlanDao pricePlanDao) {
        this.pricePlanDao = pricePlanDao;
    }
    /** 
     * <p>(根据传入生效时间与始发区域ID加上数据状态查询所符合的最新价格方案版本) </p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:52:38
     * 
     * @param cuurentTime
     * 
     * @param deptRegionId
     * 
     * @param active
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#findPricePlanByRegionId(java.util.Date, java.lang.String, java.lang.String)
     */
    @Override
    public PricePlanEntity findPricePlanByRegionId(Date cuurentTime, String deptRegionId,String active) {
	return pricePlanDao.findPricePlanByDeptRegionId(cuurentTime, deptRegionId,active);
	/**  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
        *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
        *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
        *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
        *		     比对生效日期必须大于上一个版本生效日期。
        *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
        *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
        *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     **/
    }
    
    /**
     * Sets the data dictionary value service.
     *
     * @param dataDictionaryValueService the new data dictionary value service
     */
    public void setDataDictionaryValueService(
		IDataDictionaryValueService dataDictionaryValueService) {
	this.dataDictionaryValueService = dataDictionaryValueService;
    }
	/** 
	 * 
	 * <p>激活空运方案</p> 
	 * 方法名称： activeAirPricePlan
	 * 
	 *     * 方法实现步骤： 1、 校验输入数据完整性
         * 		
         * 	       	  2、 校验方案下是否存在明细， 没有明细则提示 “空的方案不可激活”
         * 
         * 	          3、 以上验证是否为有效数据通过后、则开始根据方案信息找计费规则
         * 		 
         * 		  4、是否已经有存在的计费规则、
         * 		         如果存在、说明已经发生冲突、
         * 		         抛出“下已经发生冲突的明细,
         * 		         不能被有效激活,要激活当前草稿,
         *  		         请删除当前草稿下与其他方案发生冲突的明细，
         *  		         或者中止”
         * 			
         * 	          5、 对计费规则做数据状态有效标记
         * 
         * 		  6、分别对重量、和体积进行计费方式明细的数据处理激活状态
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanIds
	 * 
	 * @throws PricePlanException 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#activeAirPricePlan(java.util.List)
	 *****************************************************************************************************************************************************/
	@Override
	@Transactional
	public void activeAirPricePlan(List<String> pricePlanIds)throws PricePlanException {
	    Date currentTime = new Date();
	    if(CollectionUtils.isNotEmpty(pricePlanIds)){
		//遍历所有价格方案信息
		for(int i=0; i<pricePlanIds.size(); i++){
		    //价格方案信息
		    PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePlanIds.get(i));
		    if(null == pricePlanEntity){
			    throw new PricePlanException("所选价格方案为空! 请检查",null);  
		    }
		    String pricePlanId = pricePlanEntity.getId();
		    Date beginTime = pricePlanEntity.getBeginTime();
		    Date endTime = pricePlanEntity.getEndTime();
		    String priceregionId = pricePlanEntity.getPriceRegionId();
		    //同一个价格方案下计费规则
		    PriceValuationEntity valEntity = new PriceValuationEntity();
		    valEntity.setPricePlanId(pricePlanId);
		    List<PriceValuationEntity> valList = priceValuationDao.selectByCodition(valEntity);
		    if(CollectionUtils.isEmpty(valList)){
			throw new PricePlanException("空的方案不能激活!", null);
		    }
		    List<String> valuationIds =  new ArrayList<String>();
		    //遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
		    for (PriceValuationEntity priceValuationEntity : valList) {
			//计费规则影响价格的因素属性
			String arrvRegionId =  priceValuationEntity.getArrvRegionId();
			String isCentralizePickup = priceValuationEntity.getCentralizePickup();
			String productCode = priceValuationEntity.getProductCode();
			String goodsTypeCode = priceValuationEntity.getGoodsTypeCode();
			String flightShift = priceValuationEntity.getLightShift();
			String priceValuationId = priceValuationEntity.getId();
			
			PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
			priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
			//同一个计费规则下的所有计价明细 start
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = priceCriteriaDetailDao.findPriceCriteriaDetailByCondition(priceCriteriaDetailEntity);
			
			//获取计价明细的重，轻货物价格
			PriceCriteriaDetailEntity criteriaDetailWeight = new PriceCriteriaDetailEntity();
			PriceCriteriaDetailEntity criteriaDetailVolume = new PriceCriteriaDetailEntity();
			if(CollectionUtils.isNotEmpty(priceCriteriaDetailEntities)){
			    for (PriceCriteriaDetailEntity tempDetailEntity : priceCriteriaDetailEntities) {
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(tempDetailEntity.getCaculateType())){
				    criteriaDetailWeight = tempDetailEntity;
				}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(tempDetailEntity.getCaculateType())){
				    criteriaDetailVolume = tempDetailEntity;
				}
			    }
			}
			BigDecimal weightRate = criteriaDetailWeight.getFeeRate();
			BigDecimal volumeRate = criteriaDetailVolume.getFeeRate();
			Double currentWeightRate = weightRate == null ? new Double(PricingConstants.ZERO):weightRate.doubleValue();
			Double currentVolumeRate = volumeRate == null ? new Double(PricingConstants.ZERO):volumeRate.doubleValue();
			//获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
			QueryExistPricePlanDetailBean queryExistPricePlanBean = new QueryExistPricePlanDetailBean();
			queryExistPricePlanBean.setBeginTime(beginTime);
			queryExistPricePlanBean.setEndTime(endTime);
			queryExistPricePlanBean.setPriceRegionId(priceregionId);
			queryExistPricePlanBean.setProductCode(productCode);
			queryExistPricePlanBean.setGoodsTypeCode(goodsTypeCode);
			queryExistPricePlanBean.setArrvRegionId(arrvRegionId);
			queryExistPricePlanBean.setActive(FossConstants.ACTIVE);
			queryExistPricePlanBean.setIsCentralizePickup(isCentralizePickup);
			queryExistPricePlanBean.setFlightShift(flightShift);
			String centralizeName = isCentralizePickup;
			if(StringUtil.isNotBlank(isCentralizePickup)){
			    if(StringUtil.equals(isCentralizePickup, FossConstants.ACTIVE)){
				    centralizeName = "是";
			    }else{
				    centralizeName = "否";
			    }
			}else{
			    throw new PricePlanException("参数为空, 请联系管理员检查!", null);
			}
			List<ResultPricePlanDetailBean> resultDetailBeans = pricePlanDao.isExistRpeatAirLinePricePlanDetailData(queryExistPricePlanBean);
			if(CollectionUtils.isNotEmpty(resultDetailBeans)){
			    for (ResultPricePlanDetailBean resultPricePlanDetailBean : resultDetailBeans) {
				Double leftFeeRate = resultPricePlanDetailBean.getLightFeeRate().doubleValue();
				Double rightFeeRate = resultPricePlanDetailBean.getHeavyFeeRate().doubleValue();
				String caculateType = resultPricePlanDetailBean.getCaculateType();
				String existPricePlanId = resultPricePlanDetailBean.getPricePlanId();
				
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)){//按重量
				    //只对符合的重量范围才进行计算
				    if(currentWeightRate >= leftFeeRate && currentWeightRate  < rightFeeRate){
					 GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(goodsTypeCode, currentTime);
					 ProductEntity   producTypeEntity = productService.getProductByCache(productCode, currentTime);
					 PricePlanEntity selectByPrimaryBean = pricePlanDao.selectByPrimaryKey(existPricePlanId);
					 PriceRegionAirEntity priceRegion = regionAirService.searchRegionByID(priceregionId, PricingConstants.PRICING_REGION);
					 if(priceRegion == null){
						throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
					 }
					 PriceRegionAirEntity arrvRegion = regionAirService.searchRegionByID(arrvRegionId, PricingConstants.PRICING_REGION);
					 if(arrvRegion == null){
						throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
					 }
					 StringBuilder itemName = new StringBuilder();
					 itemName.append(producTypeEntity.getName());
					 itemName.append("-");
					 itemName.append(goodsTypeEntity.getName());
					 StringBuilder errStr = new StringBuilder();
					 errStr.append("始发区域[");
					 errStr.append(priceRegion.getRegionName());
					 errStr.append("],目的地区域[");
					 errStr.append(arrvRegion.getRegionName());
					 errStr.append("],是否接货[");
					 errStr.append(centralizeName);
					 errStr.append("],条目名称[");
					 errStr.append(itemName);
					 errStr.append("],在价格方案名称为[");
					 errStr.append(selectByPrimaryBean.getName());
					 errStr.append("],下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止"+selectByPrimaryBean.getName()+"价格方案!");
					throw new PricePlanException(errStr.toString(), null);
				    } 
				}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)){//按体积
				    //只对符合的体积范围才进行计算
				    if(currentVolumeRate >= leftFeeRate && currentVolumeRate  < rightFeeRate){
					GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(goodsTypeCode, currentTime);
					ProductEntity   producTypeEntity = productService.getProductByCache(productCode, currentTime);
					PricePlanEntity selectByPrimaryBean = pricePlanDao.selectByPrimaryKey(existPricePlanId);
					PriceRegionAirEntity priceRegion = regionAirService.searchRegionByID(priceregionId, PricingConstants.PRICING_REGION);
					if(priceRegion == null){
						throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
					}
					PriceRegionAirEntity arrvRegion = regionAirService.searchRegionByID(arrvRegionId, PricingConstants.PRICING_REGION);
					if(arrvRegion == null){
						throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
					}
					StringBuilder itemName = new StringBuilder();
					itemName.append(producTypeEntity.getName());
					itemName.append("-");
					itemName.append(goodsTypeEntity.getName());
					StringBuilder errStr = new StringBuilder();
					errStr.append("始发区域[");
					errStr.append(priceRegion.getRegionName());
					errStr.append("],目的地区域[");
					errStr.append(arrvRegion.getRegionName());
					errStr.append("],是否接货[");
					errStr.append(centralizeName);
					errStr.append("],条目名称[");
					errStr.append(itemName);
					errStr.append("],在价格方案名称为[");
					errStr.append(selectByPrimaryBean.getName());
					errStr.append("],下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止"+selectByPrimaryBean.getName()+"价格方案!");
					throw new PricePlanException(errStr.toString(), null);
				    } 
				}
			    }
			}
			if(StringUtil.isNotEmpty(priceValuationId)){
			    valuationIds.add(priceValuationId);
			}
		    }
		    	//以上全部校验通过,依次激活计价明细，计费规则，价格方案
			priceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
			priceValuationDao.activeValueAdded(valuationIds);
		}
		pricePlanDao.activePricePlan(pricePlanIds);
	    }
	    /**  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     **/
	}
	/** 
	 * <p>删除空运方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanIds 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#deleteAirPricePlan(java.util.List)
	 */
	@Override
	@Transactional
	public void deleteAirPricePlan(List<String> pricePlanIds) {
	    if(CollectionUtils.isNotEmpty(pricePlanIds)){
		for(int i = 0; i<pricePlanIds.size(); i++){
		    String pricePlanId =  pricePlanIds.get(i);
		    PriceValuationEntity valEntity = new PriceValuationEntity();
		    valEntity.setPricePlanId(pricePlanId);
		    List<PriceValuationEntity> valList = priceValuationDao.selectByCodition(valEntity);
		    if (CollectionUtils.isNotEmpty(valList)) {
			for (int j = 0; j < valList.size(); j++) {
			    PriceValuationEntity val = (PriceValuationEntity)valList.get(j);
			    String id = val.getId();
			    priceCriteriaDetailDao.deleteCriteriaDetailByValuationId(id);
			}
			priceValuationDao.deltePriceValuationByPricePlanId(pricePlanId);	
		    }
		    pricePlanDao.deleteByPrimaryKey(pricePlanId);
		}
	    }
	    /**  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     **/
	}
	/** 
	 * <p>复制空运方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanId
	 * 
	 * @return
	 * 
	 * @throws PricePlanException 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#copyAirPricePlan(java.lang.String)
	 */
	@Override
	@Transactional
	public String copyAirPricePlan(String pricePlanId)throws PricePlanException {
	    if(null == pricePlanId){
		throw new PricePlanException("复制方案丢失数据,不能发起复制",null);
	    }
	    //复制方案，根据方案ID,查询计费规则，根据计费规则查询计价明细依次复制且新增相应的新记录
	    PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePlanId);
	    //获取被复制对象的计费规则信息
	    PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
	    priceValuationEntity.setPricePlanId(pricePlanId);
	    List<PriceValuationEntity> tempPriceValuationEntitys = priceValuationDao.selectByCodition(priceValuationEntity);
	    String newPricePlanId = UUIDUtils.getUUID();
	    pricePlanEntity.setActive(FossConstants.INACTIVE);//复制草稿
	    pricePlanEntity.setId(newPricePlanId);
	    pricePlanEntity.setName(pricePlanEntity.getName());
	    pricePlanDao.insert(pricePlanEntity);
	    
	    if(CollectionUtils.isNotEmpty(tempPriceValuationEntitys)){
		for(int loop=0; loop <tempPriceValuationEntitys.size(); loop++){
		    PriceValuationEntity tempValuationEntity = tempPriceValuationEntitys.get(loop);
		    List<PriceCriteriaDetailEntity> tempPriceCriteriaDetailEntitys = priceCriteriaDetailDao.selectByValuationId(tempValuationEntity.getId());
		    tempValuationEntity.setId(UUIDUtils.getUUID());
		    tempValuationEntity.setPricePlanId(newPricePlanId);
		    priceValuationDao.insertSelective(tempValuationEntity);
		    
		    if(CollectionUtils.isNotEmpty(tempPriceCriteriaDetailEntitys)){
			for (int i = 0; i < tempPriceCriteriaDetailEntitys.size(); i++) {
			    PriceCriteriaDetailEntity tempPriceCriteriaDetailEntity = tempPriceCriteriaDetailEntitys.get(i);
			    tempPriceCriteriaDetailEntity.setId(UUIDUtils.getUUID());
			    tempPriceCriteriaDetailEntity.setPricingValuationId(tempValuationEntity.getId());
			    priceCriteriaDetailDao.insertSelective(tempPriceCriteriaDetailEntity);
			}
		    }
		}
	    }
	    //返回新增草稿UUID
	    return newPricePlanId;
	    /**  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     **/
	}
	
	/** 
	 * <p>复制前的查询空运方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanId
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#queryCopyAirPricePlanInfo(java.lang.String)
	 */
	@Override
	public PriceManageMentVo queryCopyAirPricePlanInfo(String pricePlanId){
	    if(StringUtil.isEmpty(pricePlanId)){
		throw new PricePlanException("选择的主方案信息缺失!,请联系运维人员查询原因。",null);
	    }
	    PricePlanEntity pricePlan = pricePlanDao.selectByPrimaryKey(pricePlanId);
	    PriceRegionAirEntity priceRegionEntity = regionAirService.searchRegionByID(pricePlan.getPriceRegionId(),PricingConstants.PRICING_REGION);
	    if(priceRegionEntity == null){
		throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
	    }
	    pricePlan.setPriceRegionName(priceRegionEntity.getRegionName());
   	    PriceManageMentVo priceManageMentVo = new PriceManageMentVo();
	    priceManageMentVo.setPricePlanEntity(pricePlan);
 	    return priceManageMentVo;
	}
	/** 
	 * <p>查询空运方案信息/p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanEntity
	 * 
	 * @param start
	 * 
	 * @param limit
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#queryAirPricePlanBatchInfo(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity, int, int)
	 */
	@Override
	public List<PricePlanEntity> queryAirPricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start,int limit) {
	    pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_KY_FLAG);
	    List<PricePlanEntity> list =  pricePlanDao.queryPricePlanBatchInfo(pricePlanEntity, start, limit);
	    return convertToRegionName(list);
	    /**  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     **/
	}

	/**
	 * Convert to region name.
	 *
	 * @param list the list
	 * @return the list
	 */
	private List<PricePlanEntity> convertToRegionName(List<PricePlanEntity> list){
	    List<PricePlanEntity> convertList = new ArrayList<PricePlanEntity>();
		for (PricePlanEntity pricePlanEntity : list) {
			String priceRegionId = pricePlanEntity.getPriceRegionId();
			PriceRegionAirEntity priceRegionEntity = regionAirService.searchRegionByID(priceRegionId, PricingConstants.PRICING_REGION);
//			if(priceRegionEntity == null){
//			    throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
//			}
			if(null != priceRegionEntity){				
				pricePlanEntity.setPriceRegionName(priceRegionEntity.getRegionName());
				if(StringUtil.isNotEmpty(pricePlanEntity.getModifyUser())){
					EmployeeEntity employeeEntity = employeeService.queryEmployeeByEmpCode(pricePlanEntity.getModifyUser());
					if(null != employeeEntity){
					    pricePlanEntity.setModifyUser(employeeEntity.getEmpName());
					}
				}
			}
			convertList.add(pricePlanEntity);
	    }
	    return convertList;
	}
	
	/** 
	 * <p>查询空运方案总数</p> 
	   /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     *
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#queryPricePlanBatchInfoCount(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
	 */
	@Override
	public Long queryPricePlanBatchInfoCount(
		PricePlanEntity pricePlanEntity) {
	    return pricePlanDao.queryPricePlanBatchInfoCount(pricePlanEntity);
	}

	/** 
	 * <p>增加空运方案</p> 
	 * 
	 *
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     *
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanEntity
	 * 
	 * @return
	 * 
	 * @throws PricePlanException 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#addAirPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
	 */
	@Transactional  
	@Override
	public PricePlanEntity addAirPricePlan(PricePlanEntity pricePlanEntity)throws PricePlanException{
		if(null == pricePlanEntity){
		    throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PLANISNULL_ERROR_CODE,null);
		}
		if(pricePlanEntity.getBeginTime().before(new Date())){
		    throw new PricePlanException(pricePlanEntity.getName()+"方案日期必须大于当前营业日期",null);
		}
		pricePlanEntity = getPricePlanValue(pricePlanEntity);
		PricePlanEntity querBean = new PricePlanEntity();
		querBean.setName(pricePlanEntity.getName());
		querBean.setTransportFlag(PricingConstants.TRANSPORT_KY_FLAG);
		List<PricePlanEntity>  pricePlanEntitys = pricePlanDao.queryPricePlanBatchInfo(querBean);
		if(CollectionUtils.isNotEmpty(pricePlanEntitys)){
		    throw new PricePlanException(pricePlanEntity.getName()+"方案已经存在,不能重复添加",null);
		}
		pricePlanDao.insert(pricePlanEntity);
		return pricePlanDao.selectByPrimaryKey(pricePlanEntity.getId());
		  /**
		     *
		     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
		     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
		     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
	             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
	             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
	             *		     比对生效日期必须大于上一个版本生效日期。
	             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
	             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
	             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
		     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
		     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
		     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
		     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
		     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
		     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
		     *	SR6	（原因是区域与产品用例早已经废除）
		     *	SR7	（原因是区域与产品用例早已经废除）
		     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
		     *	譬如有一个汽运价格方案生效日期为2010-05-31 
		     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
		     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
		     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
		     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
		     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
		     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
		     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
		     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
		     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
		     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
		     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
		     *	校验提示、“****到达城市已经存在， 请检查excel模版”
		     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
		     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
		     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
		     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
		     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
		     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
		     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
		     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
		     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
		     *	请确认是否以该方案为准,请将xxx方案中止。”
		     *	SR14	立即中止功能： 在价格查询列表中，
		     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
		     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
		     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
		     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
		     *	SR15	立即激活功能： 在价格查询列表中，
		     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
		     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
		     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
		     *	立即激活操作的生效时间必须大于等于营业日期!” ，
		     *	即时间可以在今天和今天以后任意调整，
		     *	但是不能调整为昨天的时间
		     */
	}
 
	//价格方案批次新增信息
	/**
	 * Gets the price plan value.
	 * @param pricePlanEntity the price plan entity
	 * @return the price plan value
	 */
	private PricePlanEntity getPricePlanValue(PricePlanEntity pricePlanEntity){
	    Date currentTime = new Date();
	    pricePlanEntity.setId(UUIDUtils.getUUID()); 
	    pricePlanEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
	    pricePlanEntity.setActive(FossConstants.INACTIVE);
	    pricePlanEntity.setVersionInfo(String.valueOf(currentTime.getTime())); 
	    pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_KY_FLAG);
	    pricePlanEntity.setEndTime(new Date(PricingConstants.ENDTIME));
	    pricePlanEntity.setModifyDate(currentTime);
	    pricePlanEntity.setVersionNo(currentTime.getTime());
	    pricePlanEntity.setCreateDate(currentTime);
	    return pricePlanEntity;
	    /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}
	
	//计费规则
	/** The Constant VALUATION_RULE. */
	private static final String VALUATION_RULE ="VALUATIONRULE";
	//计费明细
	/** The Constant PRICING_CRITERIA. */
	private static final String PRICING_CRITERIA="PRICINGCRITERIA";
		
	/**
	 * <p>(业务数据场景: 计费规则一条对应计价明细两条，分析数据进行持久化)</p>.
	 *
	 * @param detailList the detail list
	 * 
	 * @param pricePriceId the price price id
	 * 
	 * @return the map
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-14 下午4:57:40
	 * 
	 * @see
	 */
	private Map<String, Object> analysisPricePlanData(List<PricePlanDetailDto> detailList,String pricePriceId){
	    Date currentTime = new Date();
	    
	    //计费规则
	    List<PriceValuationEntity> valuationEntitys = new ArrayList<PriceValuationEntity>();
	    //重货
	    List<PriceCriteriaDetailEntity>  heavyList = new ArrayList<PriceCriteriaDetailEntity>();
	    //整理后的数据容器,主要是对计费规则,计价明细的数据收集
	    Map<String,Object> priceCriteriaDetailMap  = new HashMap<String, Object>();
	    
	    PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePriceId);
	    String productCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004;
	    Date currentDate = new Date();
	    ProductEntity productEntity = null;
	    GoodsTypeEntity goodsTypeEntity = null;
	    for(int i=0; i<detailList.size(); i++){
		PriceValuationEntity valuationEntity = new PriceValuationEntity();
		PricePlanDetailDto pricePlanDetailDto =  detailList.get(i);
		pricePlanDetailDto.setArrvRegionName(pricePlanDetailDto.getArrvRegionName());
		pricePlanDetailDto.setPricePlanId(pricePriceId); 
		//运价方案ID
		valuationEntity.setPricePlanId(pricePlanDetailDto.getPricePlanId());
		//是否集中接货
		valuationEntity.setCentralizePickup(pricePlanDetailDto.getCentralizePickup());
		//类型-价格方案
		valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
		//到达区域ID
		valuationEntity.setArrvRegionId(pricePlanDetailDto.getArrvRegionId());
		//价格方案主信息中的始发区域ID
		valuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
		//价格方案主信息中的始发区域ID
		valuationEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
		
		/*收集计费规则信息**/
		valuationEntity.setId(UUIDUtils.getUUID());
		valuationEntity.setProductCode(productCode);
		valuationEntity.setGoodsTypeCode(pricePlanDetailDto.getGoodsTypeCode());
		productEntity = productService.getProductByCache(productCode, currentDate);
		if(null==productEntity){
		    throw new PricePlanException("产品信息没有找到，请联系配置管理员查看",null);
		}
		goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(pricePlanDetailDto.getGoodsTypeCode(), currentDate);
		valuationEntity.setProductId(productEntity.getId());
		valuationEntity.setGoodsTypeId(goodsTypeEntity.getId());
		valuationEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		valuationEntity.setArrvRegionName(pricePlanDetailDto.getArrvRegionName());
		valuationEntity.setVersionNo(currentTime.getTime());
		valuationEntity.setBeginTime(currentTime);
		valuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		valuationEntity.setActive(pricePlanEntity.getActive());
		//创建时间
		valuationEntity.setCreateDate(currentTime);
		//航班类型CODE
		valuationEntity.setLightShift(pricePlanDetailDto.getFlightTypeCode());
		 //计价条目
		PriceEntity priceEntry = new PriceEntity();
		priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntry.setReceiveDate(currentTime);
		List<PriceEntity>  listPriceEntry = priceEntryDao.searchPriceEntryByConditions(priceEntry);
		if(CollectionUtils.isNotEmpty(listPriceEntry)){
		    priceEntry = listPriceEntry.get(0);
		    //由于数据库中对该值不能为NULL,必须查询一次计费条目ID
		    valuationEntity.setPricingEntryId(priceEntry.getId());
		    //计费条目编码
		    valuationEntity.setPricingEntryCode(priceEntry.getCode());
		    valuationEntity.setCode(priceEntry.getCode());
		}else{
			throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE,null);
		}
		valuationEntitys.add(valuationEntity);
		//重货数据
		PriceCriteriaDetailEntity heavyEntity = new PriceCriteriaDetailEntity();
		//关联计费规则ID
		heavyEntity.setPricingValuationId(valuationEntity.getId());
		//设置明细备注
		heavyEntity.setDescription(pricePlanDetailDto.getRemark());
		//计费类型 按重量
		heavyEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		heavyEntity.setActive(pricePlanEntity.getActive());
		BigDecimal heavyPrice = NumberUtils.multiply(pricePlanDetailDto.getHeavyPrice(), PricingConstants.YUTOFEN);
		heavyEntity.setFeeRate(heavyPrice);//重货价格
		heavyEntity.setMinFee(pricePlanDetailDto.getMinimumOneTicket());
		heavyEntity.setVersionNo(currentTime.getTime());
		heavyEntity.setPricingCriteriaId("");
		heavyEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
		heavyEntity.setId(UUIDUtils.getUUID());
		heavyEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
		heavyEntity.setRightrange(BigDecimal.valueOf(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX));
		heavyEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());//始发区域ID
		//zxy 20140429 MANA-1253 start 新增:合票类型
		heavyEntity.setCombBillTypeCode(pricePlanDetailDto.getCombBillTypeCode());
		//zxy 20140429 MANA-1253 end 新增:合票类型
		heavyList.add(heavyEntity);
	    }
	    priceCriteriaDetailMap.put(AirPricePlanService.VALUATION_RULE,valuationEntitys);
	    priceCriteriaDetailMap.put(AirPricePlanService.PRICING_CRITERIA, heavyList);
	    return priceCriteriaDetailMap;
	    /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}
	/** 
	 * 
	 * <p>查询空运方案明细</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param queryPricePlanDetailBean
	 * 
	 * @param start
	 * 
	 * @param limit
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#queryAirPricePlanDetailInfo(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean, int, int)
	 */
	@Override
	public List<PricePlanDetailDto> queryAirPricePlanDetailInfo(
		QueryPricePlanDetailBean queryPricePlanDetailBean, int start,
		int limit) {
	    String productItemCode = queryPricePlanDetailBean.getProductItemCode();
	    if(StringUtil.isNotBlank(productItemCode)){
		ProductItemEntity productItemEntity = productItemDao.findProductItemByCode(productItemCode, new Date());
		 if(null != productItemEntity){
			queryPricePlanDetailBean.setGoodsTypeCode(productItemEntity.getGoodstypeCode());
			queryPricePlanDetailBean.setProductCode(productItemEntity.getProductCode());
		 } 
	    } 
	    List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList =  pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean, start, limit);
	    if(CollectionUtils.isNotEmpty(resultPricePlanDetailBeanList)){
		  return this.boxHeaveAndLight(resultPricePlanDetailBeanList);
	    } 
	    return null;
	    /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}

	/** 
	 * 
	 * <p>查询空运方案明细总数</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param queryPricePlanDetailBean
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#queryAirPricePlanDetailInfoCount(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean)
	 */
	@Override
	public Long queryAirPricePlanDetailInfoCount(
		QueryPricePlanDetailBean queryPricePlanDetailBean) {
	    return pricePlanDao.queryPricePlanDetailInfoCount(queryPricePlanDetailBean);
	}
    /**
     * <p>
     * (同一计费规则封装重货和轻货价格,价格方案同一计费规则下分别存有两条对重货与轻货的明细)
     * </p>.
     *
     * @param resultPricePlanDetailBeanList the result price plan detail bean list
     * 
     * @return the list
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-6 下午5:23:26
     * 
     * @see
     */
	private List<PricePlanDetailDto> boxHeaveAndLight(List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList) {
	      //返回客户端list
	      List<PricePlanDetailDto> pricePlanDetailList = new ArrayList<PricePlanDetailDto>();
	      List<String> pricingValuationIds = new ArrayList<String>();
	      PricePlanDetailDto pricePlanDetailDto  = null;
	      
	      //遍历数据库list
	      for(Iterator<ResultPricePlanDetailBean> iterator = resultPricePlanDetailBeanList.iterator(); iterator.hasNext();){
		  ResultPricePlanDetailBean r1 = iterator.next();
		  //出现相同计费规则跳过不执行以下操作
		  if(pricingValuationIds.contains(r1.getPricingValuationId())){
		      continue;
		  }
		  pricePlanDetailDto = new PricePlanDetailDto();
		  if(StringUtil.isBlank(r1.getArrvRegionId())){
		      throw new PricePlanException("目的地区域信息为空,请联系管理员检查!",null);
		  }
		  //目的地区域名称
		  PriceRegionAirEntity priceRegionEntity = regionAirService.searchRegionByID(r1.getArrvRegionId(), PricingConstants.PRICING_REGION);
		  if(null == priceRegionEntity){
		      throw new PricePlanException("没有找到目的地区域信息!",null);
		  }
		  pricePlanDetailDto.setArrvRegionName(priceRegionEntity.getRegionName());
		  pricePlanDetailDto.setArrvRegionId(priceRegionEntity.getId());
		  //组装产品显示名称
		  pricePlanDetailDto.setGoodsTypeName(r1.getGoodsTypeName());
		  pricePlanDetailDto.setMinimumOneTicket(r1.getMinFee().longValue()/PricingConstants.YUTOFEN);
		  //是否集中接货
		  pricePlanDetailDto.setCentralizePickup(r1.getCentralizePickup());
		  pricePlanDetailDto.setRemark(r1.getRemark());
		  //价格计费规则ID
		  pricePlanDetailDto.setValuationId(r1.getPricingValuationId());
		  //价格方案ID
		  pricePlanDetailDto.setPricePlanId(r1.getPricePlanId());
		  //货物信息
		  pricePlanDetailDto.setGoodsTypeCode(r1.getGoodsTypeCode());
		  pricePlanDetailDto.setGoodsTypeName(r1.getGoodsTypeName());
		  //航空类别信息
		  pricePlanDetailDto.setFlightTypeCode(r1.getFlightShift());
		  DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.AIR_FLIGHT_TYPE,r1.getFlightShift());
		  if(dataDictionaryValueEntity!=null)
		  {
		      pricePlanDetailDto.setFlightTypeName(dataDictionaryValueEntity.getValueName());
		  }else if("NIGHT_FLIGHT".equals(r1.getFlightShift())){
			  pricePlanDetailDto.setFlightTypeName("晚班");
		  }
		
		  //zxy 20140429 MANA-1253 start 新增:合票类型
		  pricePlanDetailDto.setCombBillTypeCode(r1.getCombBillTypeCode());
		  DataDictionaryValueEntity dicCombBillEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.MAKE_WAYBILL_WAY,r1.getCombBillTypeCode());
		  if(dicCombBillEntity!=null)
		  {
		      pricePlanDetailDto.setCombBillTypeName(dicCombBillEntity.getValueName());
		  }
		  //zxy 20140429 MANA-1253 end 新增:合票类型
		  for(int j = 0; j<resultPricePlanDetailBeanList.size(); j++ ){
		      ResultPricePlanDetailBean r2 = resultPricePlanDetailBeanList.get(j);
		      //找到与当前计费规则一致的明细信息
		      if(r1.getPricingValuationId().equalsIgnoreCase(r2.getPricingValuationId())){
			  if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equalsIgnoreCase(r2.getCaculateType())){
			      BigDecimal heavryPrice =  BigDecimal.valueOf(r2.getFeeRate().doubleValue()/PricingConstants.YUTOFEN);
			      pricePlanDetailDto.setHeavyPrice(heavryPrice);
			      //设置重货价格
			  }else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equalsIgnoreCase(r2.getCaculateType())){
			      //设置轻货价格
			      BigDecimal lightPricePrice =  BigDecimal.valueOf(r2.getFeeRate().doubleValue()/PricingConstants.YUTOFEN);
			      pricePlanDetailDto.setLightPrice(lightPricePrice);
			  }else{
			      //其他计费类别是没有的或者说是错误的数据不处理,价格运费类型目前只有按照重量与体积来计算
			  }
			  pricingValuationIds.add(r2.getPricingValuationId());
		      }
		  }
		  pricePlanDetailList.add(pricePlanDetailDto);
	      }
	      return pricePlanDetailList;
	      /**
		     *
		     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
		     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
		     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
	             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
	             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
	             *		     比对生效日期必须大于上一个版本生效日期。
	             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
	             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
	             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
		     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
		     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
		     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
		     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
		     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
		     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
		     *	SR6	（原因是区域与产品用例早已经废除）
		     *	SR7	（原因是区域与产品用例早已经废除）
		     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
		     *	譬如有一个汽运价格方案生效日期为2010-05-31 
		     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
		     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
		     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
		     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
		     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
		     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
		     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
		     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
		     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
		     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
		     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
		     *	校验提示、“****到达城市已经存在， 请检查excel模版”
		     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
		     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
		     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
		     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
		     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
		     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
		     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
		     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
		     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
		     *	请确认是否以该方案为准,请将xxx方案中止。”
		     *	SR14	立即中止功能： 在价格查询列表中，
		     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
		     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
		     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
		     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
		     *	SR15	立即激活功能： 在价格查询列表中，
		     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
		     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
		     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
		     *	立即激活操作的生效时间必须大于等于营业日期!” ，
		     *	即时间可以在今天和今天以后任意调整，
		     *	但是不能调整为昨天的时间
		     */
	}
	/** 
	 * <p>修改空运方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanEntity
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#modifyAirPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
	 */
	@Override
	@Transactional
	public PricePlanEntity modifyAirPricePlan(PricePlanEntity pricePlanEntity) {
	    
	    if(null == pricePlanEntity){
		throw new PricePlanException("主方案信息缺失,请检查!",null);
	    }
	    String pricePlanId = pricePlanEntity.getId();
	    PricePlanEntity dbPricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePlanId);
	    try {
	    	if(dbPricePlanEntity != null){
	    		PropertyUtils.copyProperties(dbPricePlanEntity, pricePlanEntity);
	    	}
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
	    List<PriceValuationEntity> priceValuationEntities = priceValuationDao.selectByCodition(valuationEntity);
	    if(CollectionUtils.isNotEmpty(priceValuationEntities)) {
	    	for (int i = 0; i < priceValuationEntities.size(); i++) {
	    		PriceValuationEntity priceValuationEntity = priceValuationEntities.get(i);
	    		priceValuationEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
	    		priceValuationDao.updateValuation(priceValuationEntity);
			}
	    }
	    return pricePlanDao.selectByPrimaryKey(pricePlanId);
	}

	/**
	 * Check exist rpeat price plan detail data.
	 *
	 * @param valuationEntity the valuation entity
	 */
	private void checkExistRpeatPricePlanDetailData(PriceValuationEntity valuationEntity){
	    // 处理计费规则
	    QueryExistPricePlanDetailBean queryBean = new QueryExistPricePlanDetailBean();
	    queryBean.setPricePlanId(valuationEntity.getPricePlanId());
	    queryBean.setArrvRegionId(valuationEntity.getArrvRegionId());
	    queryBean.setProductCode(valuationEntity.getProductCode());
	    queryBean.setGoodsTypeCode(valuationEntity.getGoodsTypeCode());
	    queryBean.setIsCentralizePickup(valuationEntity.getCentralizePickup());
	    queryBean.setFlightShift(valuationEntity.getLightShift());
	    queryBean.setValuationId(valuationEntity.getId());
	    //是否已经存在
	    List<ResultPricePlanDetailBean> list = pricePlanDao.isExistRpeatPricePlanDetailForEdit(queryBean);
	    if(CollectionUtils.isNotEmpty(list)){
    	    	String centralizeName = "";
    	    	if(StringUtil.isNotBlank(valuationEntity.getCentralizePickup())){
    	    	    if(StringUtil.equals(valuationEntity.getCentralizePickup(), FossConstants.ACTIVE)){
    	    		centralizeName = "是";
    	    	    }else{
    	    		centralizeName = "否";
    	    	    }
    	    	} 
    	    	GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(valuationEntity.getGoodsTypeCode(), new Date());
    	    	ProductEntity   producTypeEntity = productService.getProductByCache(valuationEntity.getProductCode(), new Date());
    	    	PriceRegionAirEntity priceRegionEntity = regionAirService.searchRegionByID(valuationEntity.getArrvRegionId(), PricingConstants.PRICING_REGION);
    	    	if(priceRegionEntity == null){
    	    	    throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
    	    	}
    	    	StringBuilder buf = new StringBuilder();
    	    	buf.append("目的地区域["+priceRegionEntity.getRegionName()+"],");
    	    	buf.append("条目名称["+producTypeEntity.getName()+"]");
    	    	buf.append("-"+goodsTypeEntity.getName()+"]");
    	    	buf.append("是否接货["+centralizeName+"],");
    	    	buf.append("已经在当前方案下存在,不能有效添加!");
    	    	throw new PricePlanException(buf.toString(),null);
    	    /**
   	     *
   	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
   	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
   	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
                *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
                *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
                *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
                *		     比对生效日期必须大于上一个版本生效日期。
                *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
                *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
                *		    （区域分别有时效与价格，在此只需要获取价格区域）           
   	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
   	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
   	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
   	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
   	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
   	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
   	     *	SR6	（原因是区域与产品用例早已经废除）
   	     *	SR7	（原因是区域与产品用例早已经废除）
   	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
   	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
   	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
   	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
   	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
   	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
   	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
   	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
   	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
   	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
   	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
   	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
   	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
   	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
   	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
   	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
   	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
   	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
   	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
   	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
   	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
   	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
   	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
   	     *	请确认是否以该方案为准,请将xxx方案中止。”
   	     *	SR14	立即中止功能： 在价格查询列表中，
   	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
   	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
   	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
   	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
   	     *	SR15	立即激活功能： 在价格查询列表中，
   	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
   	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
   	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
   	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
   	     *	即时间可以在今天和今天以后任意调整，
   	     *	但是不能调整为昨天的时间
   	     */
	    }
	}
	/** 
	 * 
	 * <p>修改空运方案明细</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param priceManageMentVo
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#modifyAirPricePlanDetail(com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo)
	 */
	@Override
	@Transactional
	public List<PricePlanDetailDto> modifyAirPricePlanDetail(PriceManageMentVo priceManageMentVo) {
	    //修改计费规则
	    PricePlanDetailDto pricePlanDetailDto = priceManageMentVo.getPricePlanDetailDto();
	    if(null == pricePlanDetailDto){
		throw new PricePlanException("所选方案计费规则ID为空!",null);
	    }
	    PriceValuationEntity valuationEntity = priceValuationDao.selectByPrimaryKey(pricePlanDetailDto.getValuationId());
	    //目的地区域id
	    valuationEntity.setArrvRegionId(pricePlanDetailDto.getArrvRegionId());
	    //目的地区域名称
	    valuationEntity.setArrvRegionName(pricePlanDetailDto.getArrvRegionName());
	    //是否接货
	    valuationEntity.setCentralizePickup(pricePlanDetailDto.getCentralizePickup());
	    //货物类型
	    valuationEntity.setGoodsTypeCode(pricePlanDetailDto.getGoodsTypeCode());
	    //航班类别
	    valuationEntity.setLightShift(pricePlanDetailDto.getFlightTypeCode());
	    //计费规则ID
	    valuationEntity.setId(pricePlanDetailDto.getValuationId());
	    //备注信息
	    valuationEntity.setRemarks(pricePlanDetailDto.getRemark());
	    //检查是否有重复数据
	    this.checkExistRpeatPricePlanDetailData(valuationEntity);
	    //更新数据库 计费规则信息
	    priceValuationDao.updateValuation(valuationEntity);
	    //修改计价明细
	    String valuationId = valuationEntity.getId();
	    List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = priceCriteriaDetailDao.selectByValuationId(valuationId);
	    for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : priceCriteriaDetailEntities) {
		//按照重量
		if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(priceCriteriaDetailEntity.getCaculateType())){
		    if(null != pricePlanDetailDto.getHeavyPrice()){
			 BigDecimal heavryPrice =  BigDecimal.valueOf(pricePlanDetailDto.getHeavyPrice().doubleValue()*PricingConstants.YUTOFEN);
			 priceCriteriaDetailEntity.setFeeRate(heavryPrice);
		    }
		//按照体积
		}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equalsIgnoreCase(priceCriteriaDetailEntity.getCaculateType())){
		    if(null != pricePlanDetailDto.getLightPrice()){
			 BigDecimal heavryPrice =  BigDecimal.valueOf(pricePlanDetailDto.getLightPrice().doubleValue()*PricingConstants.YUTOFEN);
			 priceCriteriaDetailEntity.setFeeRate(heavryPrice);
		    }
		}
		//zxy 20140504 MANA-1253 start 新增:合票
		priceCriteriaDetailEntity.setCombBillTypeCode(pricePlanDetailDto.getCombBillTypeCode());
		//zxy 20140504 MANA-1253 end 新增:合票
		//最低一票
		priceCriteriaDetailEntity.setMinFee(pricePlanDetailDto.getMinimumOneTicket().longValue());
		priceCriteriaDetailEntity.setDescription(pricePlanDetailDto.getRemark());
		priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
	    }
	    //查询计费明细信息
	    QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
    	    queryPricePlanDetailBean.setPricePlanId(valuationEntity.getPricePlanId());
    	    List<ResultPricePlanDetailBean> resultPricePlanDetails = pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean);
    	    return this.boxHeaveAndLight(resultPricePlanDetails);
    	  /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}
	/** 
	 * 
	 * <p>中止空运方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param voPricePlanEntity
	 * 
	 * @throws PricePlanException 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#stopAirPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
	 */
	@Override
	@Transactional
	public void stopAirPricePlan(PricePlanEntity voPricePlanEntity) throws PricePlanException{
	    String pricePlanId = voPricePlanEntity.getId();
	    Date stopTime = voPricePlanEntity.getEndTime();
	    if(StringUtil.isBlank(pricePlanId)){
		throw new PricePlanException("所选价格方案ID为空!",null);
	    }
	    if(stopTime == null){
		throw new PricePlanException("截至日期不能为空!",null);
	    } 
	    //如果当前为立即中止
	    if(stopTime.before(new Date())){
			throw new PricePlanException("中止日期必须大于当前营业日期!",null);
		}
	    PricePlanEntity pricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePlanId);
	    if(null==pricePlanEntity)
	    {
		throw new PricePlanException("根据前台参数实体ID,没有找到对应的实体!",null);
	    }
	    if(stopTime.after(pricePlanEntity.getEndTime()))
	    {
		throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
	    }
	    //修改计费规则截止日期
	    PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
	    priceValuationEntity.setPricePlanId(pricePlanId);
	    List<PriceValuationEntity> priceValuationEntitys = priceValuationDao.selectByCodition(priceValuationEntity);
	    if(CollectionUtils.isNotEmpty(priceValuationEntitys)){
			for (PriceValuationEntity tempPriceValuationEntity : priceValuationEntitys) {
			    tempPriceValuationEntity.setEndTime(stopTime);
			    tempPriceValuationEntity.setVersionNo(new Date().getTime());
			    priceValuationDao.updateValuation(tempPriceValuationEntity);
			}
	    }
	    //修改价格方案截止日期
	    pricePlanEntity.setEndTime(stopTime);
	    pricePlanEntity.setVersionNo(new Date().getTime());
	    pricePlanDao.updateByPrimaryKeySelective(pricePlanEntity);
	    /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}

	/** 
	 * 
	 * <p>添加空运方案明细</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param pricePlanDetailDto
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#addAirPricePlanDetail(com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<PricePlanDetailDto> addAirPricePlanDetail(PricePlanDetailDto pricePlanDetailDto) {
		List<PricePlanDetailDto> resultpricePlanDetails = new ArrayList<PricePlanDetailDto>();
		if (null != pricePlanDetailDto) {
			Date currentDate = new Date();
			// 方案id
			String pricePlanId = pricePlanDetailDto.getPricePlanId();
			// 目的地区域
			String arrvRegionId = pricePlanDetailDto.getArrvRegionId();
			// 是否接货
			String centralizePickup = pricePlanDetailDto.getCentralizePickup();
			// 航班类型Code
			String flightShift = pricePlanDetailDto.getFlightTypeCode();
			// 货物类型
			String goodsTypeCode = pricePlanDetailDto.getGoodsTypeCode();
			// 空运固定产品
			String productCode = PricingConstants.ProductEntityConstants.PRICING_PRODUCT_C2_C20004;
			List<PricePlanDetailDto> pricePlanDetailDtos = new ArrayList<PricePlanDetailDto>();
			pricePlanDetailDtos.add(pricePlanDetailDto);
			// 处理计费规则与计价明细(重货轻货)
			Map<String, Object> priceCriteriaDetailMap = analysisPricePlanData(pricePlanDetailDtos, pricePlanId);
			List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap
					.get(AirPricePlanService.VALUATION_RULE);
			List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap
					.get(AirPricePlanService.PRICING_CRITERIA);
			// 处理计费规则
			if (CollectionUtils.isNotEmpty(valuations)) {
				QueryExistPricePlanDetailBean queryBean = new QueryExistPricePlanDetailBean();
				queryBean.setPricePlanId(pricePlanId);
				queryBean.setArrvRegionId(arrvRegionId);
				queryBean.setIsCentralizePickup(centralizePickup);
				queryBean.setProductCode(productCode);
				queryBean.setGoodsTypeCode(goodsTypeCode);
				queryBean.setFlightShift(flightShift);
				queryBean.setCombBillTypeCode(pricePlanDetailDto.getCombBillTypeCode());	//zxy 20140428 MANA-1253 新增
				List<ResultPricePlanDetailBean> list = pricePlanDao.isExistRpeatPricePlanDetailForEdit(queryBean);
				if (CollectionUtils.isNotEmpty(list)) {
					String centralizeName = "";
					if (StringUtil.isNotBlank(centralizePickup)) {
						if (StringUtil.equals(centralizePickup, FossConstants.ACTIVE)) {
							centralizeName = "是";
						} else {
							centralizeName = "否";
						}
					}
					GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(goodsTypeCode,
							currentDate);
					PriceRegionAirEntity priceRegionEntity = regionAirService.searchRegionByID(arrvRegionId,
							PricingConstants.PRICING_REGION);
					if (priceRegionEntity == null) {
						throw new PricePlanException("根据区域ID没有找到对应的空运区域信息", null);
					}
					DataDictionaryValueEntity dataDictionaryValueEntity = dataDictionaryValueService
							.queryDataDictionaryValueByTermsCodeValueCode(DictionaryConstants.AIR_FLIGHT_TYPE,
									flightShift);
					DataDictionaryValueEntity dicCombBillEntity = dataDictionaryValueService.queryDataDictionaryValueByTermsCodeValueCode(
							DictionaryConstants.MAKE_WAYBILL_WAY,pricePlanDetailDto.getCombBillTypeCode());
					StringBuilder buf = new StringBuilder();
					buf.append("目的地区域[" + priceRegionEntity.getRegionName() + "],");
					buf.append("航班类型[" + dataDictionaryValueEntity.getValueName() + "]");
					buf.append("合票类型[" + dicCombBillEntity.getValueName() + "]");		//zxy 20140509 MANA-1253 新增
					buf.append("货物类型[" + goodsTypeEntity.getName() + "]");
					buf.append("是否接货[" + centralizeName + "],");
					buf.append("已经在当前方案下存在,不能有效添加!");
					throw new PricePlanException(buf.toString(), null);
				}
				for (PriceValuationEntity priceValuationEntity : valuations) {
					priceValuationDao.insertSelective(priceValuationEntity);
				}
			}
			// 处理计价明细
			if (CollectionUtils.isNotEmpty(details)) {
				for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
					priceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntitys);
				}
			}
			QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
			queryPricePlanDetailBean.setPricePlanId(pricePlanId);
			List<ResultPricePlanDetailBean> resultPricePlanDetails = pricePlanDao
					.queryPricePlanDetailInfo(queryPricePlanDetailBean);
			resultpricePlanDetails = this.boxHeaveAndLight(resultPricePlanDetails);
		} else {
			throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_DETAILISNULL_ERROR_CODE, null);
		}
		return resultpricePlanDetails;
		  /**
		     *
		     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
		     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
		     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
	             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
	             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
	             *		     比对生效日期必须大于上一个版本生效日期。
	             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
	             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
	             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
		     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
		     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
		     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
		     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
		     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
		     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
		     *	SR6	（原因是区域与产品用例早已经废除）
		     *	SR7	（原因是区域与产品用例早已经废除）
		     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
		     *	譬如有一个汽运价格方案生效日期为2010-05-31 
		     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
		     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
		     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
		     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
		     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
		     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
		     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
		     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
		     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
		     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
		     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
		     *	校验提示、“****到达城市已经存在， 请检查excel模版”
		     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
		     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
		     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
		     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
		     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
		     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
		     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
		     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
		     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
		     *	请确认是否以该方案为准,请将xxx方案中止。”
		     *	SR14	立即中止功能： 在价格查询列表中，
		     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
		     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
		     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
		     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
		     *	SR15	立即激活功能： 在价格查询列表中，
		     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
		     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
		     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
		     *	立即激活操作的生效时间必须大于等于营业日期!” ，
		     *	即时间可以在今天和今天以后任意调整，
		     *	但是不能调整为昨天的时间
		     */
	}
	/** 
	 * 
	 * <p>编辑空运方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param priceManageMentVo 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#modifyAirPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.vo.PriceManageMentVo)
	 */
	@Override
	public void modifyAirPricePlan(PriceManageMentVo priceManageMentVo) {
	    //修改方案信息
	    PricePlanEntity priceEntity = priceManageMentVo.getPricePlanEntity();
	    if(null != priceEntity){
		PricePlanEntity dbEntity =  pricePlanDao.selectByPrimaryKey(priceEntity.getId());
		dbEntity.setName(priceEntity.getName());
		dbEntity.setBeginTime(priceEntity.getBeginTime());
		dbEntity.setPriceRegionId(priceEntity.getPriceRegionId());
		dbEntity.setPriceRegionCode(priceEntity.getPriceRegionCode());
		dbEntity.setPriceRegionName(priceEntity.getPriceRegionName());
		dbEntity.setDescription(priceEntity.getDescription());
		pricePlanDao.updateByPrimaryKeySelective(dbEntity);
	    }
	    /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
            *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
            *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
            *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
            *		     比对生效日期必须大于上一个版本生效日期。
            *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
            *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
            *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}
	/** 
	 * 
	 * <p>删除空运明细方案</p> 
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-3-14 下午2:52:38
	 * 
	 * @param valuationIds
	 * 
	 * @return 
	 * 
	 * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#deleteAirPriceDetailPlan(java.util.List)
	 */
	@Override
	@Transactional
	public List<PricePlanDetailDto> deleteAirPriceDetailPlan(List<String> valuationIds) {
	    List<PricePlanDetailDto> pricePlanDetailDtos = new ArrayList<PricePlanDetailDto>();
	    if(CollectionUtils.isNotEmpty(valuationIds)){
		 //删除前，获得计价方案ID
		    PriceValuationEntity pricevaluationEntity = priceValuationDao.selectByPrimaryKey(valuationIds.get(0));
		    QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
		    queryPricePlanDetailBean.setPricePlanId(pricevaluationEntity.getPricePlanId());
		for(int i = 0; i<valuationIds.size(); i++){
		    String valuationId =  valuationIds.get(i);
		    priceCriteriaDetailDao.deleteCriteriaDetailByValuationId(valuationId);
		    priceValuationDao.deleteByPrimaryKey(valuationId);	
		}
	 	pricePlanDetailDtos = this.boxHeaveAndLight(pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean));
	    }
	    return pricePlanDetailDtos; 
	    /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
	}
    /**
     * 
     * 批量导入价格数据.
     *
     * @param detailMap the detail map
     * 
     * @param priceRegionEntityMap the price region entity map
     * 
     * @param productEntityMap the product entity map
     * 
     * @author zhangdongping
     * 
     * @date 2012-12-24 下午5:52:56
     * 
     * @update 2014-05-09 zxy MANA-1253 新增合票类型字段
     */
	@Transactional
	public void addAirPricePlanBatch(Map<String, List<PricePlanDetailDto>> detailMap,
			Map<String, PriceRegionAirEntity> priceRegionEntityMap, Map<String, ProductEntity> productEntityMap,
			Map<String, GoodsTypeEntity> goodsMap, Map<String, String> flightShiftEntityMap,Map<String, String> combBillTypeEntityMap) {
		// 检查数据
		if (priceRegionEntityMap == null
				|| priceRegionEntityMap.size() < 1 || productEntityMap == null || productEntityMap.size() < 1
				|| goodsMap == null || goodsMap.size() < 1 || flightShiftEntityMap == null || flightShiftEntityMap.size() < 1
				|| combBillTypeEntityMap == null) {
			return;
		}
		// 计价条目查询
		PriceEntity priceEntry = new PriceEntity();
		priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntry.setReceiveDate(new Date());
		List<PriceEntity> listPriceEntry = priceEntryDao.searchPriceEntryByConditions(priceEntry);
		if (CollectionUtils.isNotEmpty(listPriceEntry)) {
			priceEntry = listPriceEntry.get(0);
		} else {
			throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE, null);
		}
		Set<Entry<String, List<PricePlanDetailDto>>> detailSet = detailMap.entrySet();
		for (Entry<String, List<PricePlanDetailDto>> entry : detailSet) {
			String regionName = entry.getKey().trim();
			List<PricePlanDetailDto> detailList = entry.getValue();
			// 添加价格主方案信息
			PriceRegionAirEntity deptRegion = priceRegionEntityMap.get(regionName);
			PricePlanEntity pricePlanEntity = new PricePlanEntity();
			pricePlanEntity.setPriceRegionId(deptRegion.getId());
			pricePlanEntity.setPriceRegionCode(deptRegion.getRegionCode());
			// 设置方案名称
			pricePlanEntity.setName(regionName + "导入价格方案");
			// 方案生效日期默认明天
			pricePlanEntity.setBeginTime(DateUtils.getStartDatetime(new Date(), 1));
			// 准备数据
			pricePlanEntity = getPricePlanValue(pricePlanEntity);
			// 插入方案主信息表
			pricePlanDao.insert(pricePlanEntity);
			String pricePlanId = pricePlanEntity.getId();
			// 价格方案明细插入
			if (CollectionUtils.isNotEmpty(detailList)) {
				Iterator<PricePlanDetailDto> iterator = detailList.iterator();
				while (iterator.hasNext()) {
					PricePlanDetailDto pricePlanDetailDto = iterator.next();
					if (null == pricePlanDetailDto) {
						throw new PricePlanException("没有找到相关计费信息", null);
					}
					pricePlanDetailDto.setPricePlanId(pricePlanId);
					pricePlanDetailDto.setActive(FossConstants.INACTIVE);
					PriceRegionAirEntity areiveRegionEntity = priceRegionEntityMap.get(pricePlanDetailDto
							.getArrvRegionName().trim());
					pricePlanDetailDto.setArrvRegionId(areiveRegionEntity.getId());
					pricePlanDetailDto.setFlightTypeCode(flightShiftEntityMap.get(pricePlanDetailDto
							.getFlightTypeName()));
					pricePlanDetailDto.setCombBillTypeCode(combBillTypeEntityMap.get(pricePlanDetailDto.getCombBillTypeName()));//zxy 20140428 MANA-1253 新增
					GoodsTypeEntity goodsEntity = goodsMap.get(pricePlanDetailDto.getGoodsTypeName());
					ProductEntity procuctEntity = productEntityMap.get(pricePlanDetailDto.getProductItemName());
					addPriceBatchPlanDetail(pricePlanDetailDto, deptRegion, procuctEntity, priceEntry, goodsEntity);
				}
			}
		}
//		public void addAirPricePlanBatch(Map<String, List<PricePlanDetailDto>> detailMap,
//				Map<String, PriceRegionAirEntity> priceRegionEntityMap, Map<String, ProductEntity> productEntityMap,
//				Map<String, GoodsTypeEntity> goodsMap, Map<String, String> flightShiftEntityMap) {
//			// 检查数据
//			if (priceRegionEntityMap == null
//					|| priceRegionEntityMap.size() < 1 || productEntityMap == null || productEntityMap.size() < 1
//					|| goodsMap.size() < 1 || goodsMap == null || flightShiftEntityMap.size() < 1
//					|| flightShiftEntityMap == null) {
//				return;
//			}
//			Iterator<String> it = detailMap.keySet().iterator();
//			// 计价条目查询
//			PriceEntity priceEntry = new PriceEntity();
//			priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
//			priceEntry.setReceiveDate(new Date());
//			List<PriceEntity> listPriceEntry = priceEntryDao.searchPriceEntryByConditions(priceEntry);
//			if (CollectionUtils.isNotEmpty(listPriceEntry)) {
//				priceEntry = listPriceEntry.get(0);
//			} else {
//				throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE, null);
//			}
//			while (it.hasNext()) {
//				String regionName = it.next();
//				// 添加价格主方案信息
//				PriceRegionAirEntity deptRegion = priceRegionEntityMap.get(regionName);
//				PricePlanEntity pricePlanEntity = new PricePlanEntity();
//				pricePlanEntity.setPriceRegionId(deptRegion.getId());
//				pricePlanEntity.setPriceRegionCode(deptRegion.getRegionCode());
//				// 设置方案名称
//				pricePlanEntity.setName(regionName + "导入价格方案");
//				// 方案生效日期默认明天
//				pricePlanEntity.setBeginTime(DateUtils.getStartDatetime(new Date(), 1));
//				// 准备数据
//				pricePlanEntity = getPricePlanValue(pricePlanEntity);
//				// 插入方案主信息表
//				pricePlanDao.insert(pricePlanEntity);
//				String pricePlanId = pricePlanEntity.getId();
//				// 价格方案明细插入
//
//				List<PricePlanDetailDto> detailList = detailMap.get(regionName);
//				if (CollectionUtils.isNotEmpty(detailList)) {
//					Iterator<PricePlanDetailDto> iterator = detailList.iterator();
//					while (iterator.hasNext()) {
//						PricePlanDetailDto pricePlanDetailDto = iterator.next();
//						if (null == pricePlanDetailDto) {
//							throw new PricePlanException("没有找到相关计费信息", null);
//						}
//						pricePlanDetailDto.setPricePlanId(pricePlanId);
//						pricePlanDetailDto.setActive(FossConstants.INACTIVE);
//						PriceRegionAirEntity areiveRegionEntity = priceRegionEntityMap.get(pricePlanDetailDto
//								.getArrvRegionName());
//						pricePlanDetailDto.setArrvRegionId(areiveRegionEntity.getId());
//						pricePlanDetailDto.setFlightTypeCode(flightShiftEntityMap.get(pricePlanDetailDto
//								.getFlightTypeName()));
//						GoodsTypeEntity goodsEntity = goodsMap.get(pricePlanDetailDto.getGoodsTypeName());
//						ProductEntity procuctEntity = productEntityMap.get(pricePlanDetailDto.getProductItemName());
//						addPriceBatchPlanDetail(pricePlanDetailDto, deptRegion, procuctEntity, priceEntry, goodsEntity);
//					}
//				}
//			}
		 /**
		     *
		     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
		     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
		     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
	             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
	             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
	             *		     比对生效日期必须大于上一个版本生效日期。
	             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
	             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
	             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
		     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
		     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
		     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
		     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
		     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
		     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
		     *	SR6	（原因是区域与产品用例早已经废除）
		     *	SR7	（原因是区域与产品用例早已经废除）
		     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
		     *	譬如有一个汽运价格方案生效日期为2010-05-31 
		     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
		     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
		     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
		     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
		     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
		     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
		     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
		     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
		     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
		     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
		     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
		     *	校验提示、“****到达城市已经存在， 请检查excel模版”
		     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
		     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
		     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
		     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
		     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
		     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
		     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
		     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
		     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
		     *	请确认是否以该方案为准,请将xxx方案中止。”
		     *	SR14	立即中止功能： 在价格查询列表中，
		     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
		     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
		     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
		     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
		     *	SR15	立即激活功能： 在价格查询列表中，
		     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
		     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
		     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
		     *	立即激活操作的生效时间必须大于等于营业日期!” ，
		     *	即时间可以在今天和今天以后任意调整，
		     *	但是不能调整为昨天的时间
		     */
	}
    /**
     * 添加价格数据.
     *
     * @param pricePlanDetailDto the price plan detail dto
     * 
     * @param deptRegion the dept region
     * 
     * @param procuctEntity the procuct entity
     * 
     * @param priceEntry the price entry
     * 
     * @param goodsTypeEntity the goods type entity
     * 
     * @author zhangdongping
     * 
     * @date 2012-12-24 下午5:51:49
     * 
     * @see
     */
	@Transactional
	private void addPriceBatchPlanDetail(PricePlanDetailDto pricePlanDetailDto, PriceRegionAirEntity deptRegion,
			ProductEntity procuctEntity, PriceEntity priceEntry, GoodsTypeEntity goodsTypeEntity) {
		// 处理计费规则与计价明细(重货轻货)
		Map<String, Object> priceCriteriaDetailMap = analysisBatchPricePlanData(pricePlanDetailDto, deptRegion,
				procuctEntity, priceEntry, goodsTypeEntity);
		@SuppressWarnings("unchecked")
		// 取出计费规则数据
		List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap
				.get(AirPricePlanService.VALUATION_RULE);
		@SuppressWarnings("unchecked")
		// 取出价格详细
		List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap
				.get(AirPricePlanService.PRICING_CRITERIA);
		// 处理计费规则数据
		for (PriceValuationEntity priceValuationEntity : valuations) {
			priceValuationDao.insertSelective(priceValuationEntity);

		}
		// 批量处理计价明细
		if (CollectionUtils.isNotEmpty(details)) {
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
				priceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntitys);
			}
		}
		   /**
		     *
		     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
		     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
		     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
	             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
	             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
	             *		     比对生效日期必须大于上一个版本生效日期。
	             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
	             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
	             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
		     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
		     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
		     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
		     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
		     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
		     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
		     *	SR6	（原因是区域与产品用例早已经废除）
		     *	SR7	（原因是区域与产品用例早已经废除）
		     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
		     *	譬如有一个汽运价格方案生效日期为2010-05-31 
		     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
		     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
		     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
		     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
		     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
		     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
		     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
		     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
		     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
		     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
		     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
		     *	校验提示、“****到达城市已经存在， 请检查excel模版”
		     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
		     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
		     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
		     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
		     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
		     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
		     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
		     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
		     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
		     *	请确认是否以该方案为准,请将xxx方案中止。”
		     *	SR14	立即中止功能： 在价格查询列表中，
		     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
		     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
		     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
		     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
		     *	SR15	立即激活功能： 在价格查询列表中，
		     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
		     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
		     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
		     *	立即激活操作的生效时间必须大于等于营业日期!” ，
		     *	即时间可以在今天和今天以后任意调整，
		     *	但是不能调整为昨天的时间
		     */
	}
    /**
     * 准备批量数据.
     *
     * @param detail the detail
     * 
     * @param deptRegion the dept region
     * 
     * @param procuctEntity the procuct entity
     * 
     * @param priceEntry the price entry
     * 
     * @param goodsTypeEntity the goods type entity
     * 
     * @return the map
     * 
     * @author zhangdongping
     * 
     * @date 2012-12-24 下午5:51:34
     * 
     * @see
     */
    private Map<String, Object> analysisBatchPricePlanData(
	    PricePlanDetailDto detail, PriceRegionAirEntity deptRegion,
	    ProductEntity procuctEntity, PriceEntity priceEntry,
	    GoodsTypeEntity goodsTypeEntity ) {
	Date currentTime = DateUtils.getStartDatetime(new Date(), 1);
	// 计费规则
	List<PriceValuationEntity> valuationEntitys = new ArrayList<PriceValuationEntity>();
	// 重货
	List<PriceCriteriaDetailEntity> heavyList = new ArrayList<PriceCriteriaDetailEntity>();
	// 轻货
	List<PriceCriteriaDetailEntity> lightList = new ArrayList<PriceCriteriaDetailEntity>();
	// 整理后的数据容器,主要是对计费规则,计价明细的数据收集
	Map<String, Object> priceCriteriaDetailMap = new HashMap<String, Object>();

	PriceValuationEntity valuationEntity = new PriceValuationEntity();

	detail.setArrvRegionName(detail.getArrvRegionName());
	//设置产品code
	valuationEntity.setProductCode(procuctEntity.getCode());
	//设置航班类型（早-中-晚-中转）
	valuationEntity.setLightShift(detail.getFlightTypeCode());
	//设置货物类型code
	valuationEntity.setGoodsTypeCode(goodsTypeEntity.getCode());
	// 运价方案ID
	valuationEntity.setPricePlanId(detail.getPricePlanId());
	// 是否集中接货
	valuationEntity.setCentralizePickup(detail.getCentralizePickup());
	// 类型-价格方案
	valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
	// 到达区域ID
	valuationEntity.setArrvRegionId(detail.getArrvRegionId());
	valuationEntity.setDeptRegionId(deptRegion.getId());
	valuationEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT); 
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
	PriceCriteriaDetailEntity heavyEntity = new PriceCriteriaDetailEntity();
	PriceCriteriaDetailEntity lightEntity = new PriceCriteriaDetailEntity();
	// 关联计费规则ID
	heavyEntity.setPricingValuationId(valuationEntity.getId());
	// 设置明细备注
	heavyEntity.setDescription(detail.getRemark());
	// 计费类型
	// 按重量
	heavyEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
	// 重货价格 换算成分								
	heavyEntity.setFeeRate(NumberUtils.multiply(detail.getHeavyPrice(),PricingConstants.YUTOFEN));
	//最低一票
	heavyEntity.setMinFee(detail.getMinimumOneTicket());
	heavyEntity.setVersionNo(currentTime.getTime());
	//为空，原因该属性关联关系的实体已经不存在。后续删除该字段
	heavyEntity.setPricingCriteriaId("");
	//计费规则
	heavyEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	heavyEntity.setId(UUIDUtils.getUUID());
	//左区间
	heavyEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
	//右区间
	heavyEntity.setRightrange(BigDecimal.valueOf(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX));
	// 始发区域ID 
	heavyEntity.setDeptRegionId(deptRegion.getId());
	heavyEntity.setActive(detail.getActive());
	heavyEntity.setCombBillTypeCode(detail.getCombBillTypeCode());			//zxy 20140509 MANA-1253 新增:重量计费  导入excel 增加合票类型
	heavyList.add(heavyEntity);

	// 轻货数据
	// 关联计费规则ID
	lightEntity.setPricingValuationId(valuationEntity.getId());
	// 设置明细备注
	lightEntity.setDescription(detail.getRemark());
	// 计费类型 按体积
	lightEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
	lightEntity.setVersionNo(currentTime.getTime());
	lightEntity.setActive(detail.getActive());
	// 轻货价格 换算造成分
	lightEntity.setFeeRate(NumberUtils.multiply(detail.getLightPrice(),PricingConstants.YUTOFEN));
	//最低一票
	lightEntity.setMinFee(detail.getMinimumOneTicket());
	lightEntity.setPricingCriteriaId("");
	lightEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
	lightEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
	lightEntity.setRightrange(BigDecimal.valueOf(PricingConstants.CRITERIA_DETAIL_VOLUME_MAX));
	lightEntity.setId(UUIDUtils.getUUID());
	// 始发区域ID
	lightEntity.setDeptRegionId(deptRegion.getId());
	lightEntity.setActive(detail.getActive()); 
	lightEntity.setCombBillTypeCode(detail.getCombBillTypeCode());		//zxy 20140509 MANA-1253 新增：体积计费 导入excel 增加合票类型
	// 组装数据
	lightList.add(lightEntity);
	heavyList.addAll(lightList);
	priceCriteriaDetailMap.put(AirPricePlanService.VALUATION_RULE,valuationEntitys);
	priceCriteriaDetailMap.put(AirPricePlanService.PRICING_CRITERIA, heavyList);
	return priceCriteriaDetailMap;
	   /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
    }
    /** 
     * 
     * <p>根据ID获得空运方案</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:52:38
     * 
     * @param id
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#getAirPricePlanEntity(java.lang.String)
     */
    @Override
    public PricePlanEntity getAirPricePlanEntity(String id) {
	PricePlanEntity pricePlanEntity =  pricePlanDao.selectByPrimaryKey(id);
	PriceRegionAirEntity priceRegionEntity = regionAirService.searchRegionByID(pricePlanEntity.getPriceRegionId(), PricingConstants.PRICING_REGION);
	if(priceRegionEntity == null){
		throw new PricePlanException("根据区域ID没有找到对应的空运区域信息",null);
	}
	pricePlanEntity.setPriceRegionName(priceRegionEntity.getRegionName());
	return pricePlanEntity;
    }
    /** 
     * 
     * <p>导出空运价格明细</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:52:38
     * 
     * @param queryPricePlanDetailBean
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#exportAirPricePlanDetail(com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean)
     */
    @Override
    public ExportResource exportAirPricePlanDetail(QueryPricePlanDetailBean queryPricePlanDetailBean) {
	List<ResultPricePlanDetailBean>  resultPricePlanDetailBeans = pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0, Integer.MAX_VALUE);
	List<PricePlanDetailDto> pricePlanDetailDtos = this.boxHeaveAndLight(resultPricePlanDetailBeans);
	List<List<String>> rowList = new ArrayList<List<String>>();
	if(CollectionUtils.isNotEmpty(pricePlanDetailDtos)){
	    for (PricePlanDetailDto pricePlanDetail : pricePlanDetailDtos) {
		List<String> row = exportPlatform(pricePlanDetail);
		rowList.add(row);
	    }
	}
	//创建导出资源对象
	ExportResource exportResource = new ExportResource();
	//设置导出头信息
	exportResource.setHeads(PricingColumnConstants.AIR_PRICE_PLAN_DETAIL_TITLE);
	//设置导出行
	exportResource.setRowList(rowList);
	return exportResource;
	 /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
    }
    
    /**
     * <p>填充方案明细 sheet row </p>.
     *
     * @param pricePlanDetailDto the price plan detail dto
     * 
     * @return the list
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-27 上午9:59:41
     * 
     * @see
     */
    private List<String> exportPlatform(PricePlanDetailDto pricePlanDetailDto){
	List<String> result = new ArrayList<String>();
	//目的地区域名称
	result.add(pricePlanDetailDto.getArrvRegionName());
	//航空类型
	result.add(pricePlanDetailDto.getFlightTypeName());
	//合票类型				
	result.add(pricePlanDetailDto.getCombBillTypeName()); //zxy 20140428 MANA-1253 新增
	//货物类型
	result.add(pricePlanDetailDto.getGoodsTypeName());
	//重货价格
	result.add(pricePlanDetailDto.getHeavyPrice().toString());
	//轻货价格		空运无轻货价格
	//result.add(pricePlanDetailDto.getLightPrice() == null ? "" : pricePlanDetailDto.getLightPrice().toString());
	//最低一票
	result.add(pricePlanDetailDto.getMinimumOneTicket().toString());
	//是否接货
	result.add(pricePlanDetailDto.getCentralizePickup());
	result.add(pricePlanDetailDto.getRemark());
	return result;
	   /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
    }
    
    /**
     * <p>填充方案 sheet row </p>.
     *
     * @param pricePlanEntity the price plan entity
     * 
     * @return the list
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-12-27 上午9:59:41
     * 
     * @see
     */
    private List<String> exportPlatform(PricePlanEntity pricePlanEntity){
	List<String> result = new ArrayList<String>();
	result.add(pricePlanEntity.getName());
	result.add(pricePlanEntity.getPriceRegionName());
	//修改时间
	String modifyDate = DateUtils.convert(pricePlanEntity.getModifyDate(), DateUtils.DATE_FORMAT);
	//方案开始时间
	String beginDate = DateUtils.convert(pricePlanEntity.getBeginTime(), DateUtils.DATE_FORMAT);
	//方案结束时间
	String endDate = DateUtils.convert(pricePlanEntity.getEndTime(), DateUtils.DATE_FORMAT);
	result.add(modifyDate);
	result.add(pricePlanEntity.getModifyUser());
	result.add(beginDate);
	result.add(endDate);
	if(StringUtil.equalsIgnoreCase(FossConstants.ACTIVE, pricePlanEntity.getActive())){
	    result.add("已激活");
	}else{
	    result.add("未激活");
	}
	return result;
	   /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
    }

    /** 
     * <p>导出空运价格方案</p> 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:52:38
     * 
     * @param pricePlanEntity
     * 
     * @return 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#exportAirPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
     */
    @Override
    public ExportResource exportAirPricePlan(PricePlanEntity pricePlanEntity) {
	List<PricePlanEntity> pricePlanEntitys  = queryAirPricePlanBatchInfo(pricePlanEntity, PricingConstants.ZERO, Integer.MAX_VALUE);
	if(CollectionUtils.isEmpty(pricePlanEntitys)){
	    return null;
	}
	ExportResource exportResource = new ExportResource();
	
	List<PricePlanEntity> pricePlanList = convertToRegionName(pricePlanEntitys);
	List<List<String>> rowList = new ArrayList<List<String>>();
	for (PricePlanEntity tempPricePlan : pricePlanList) {
	     List<String> row = exportPlatform(tempPricePlan);
	     rowList.add(row);
	}
	exportResource.setHeads(PricingColumnConstants.PRICE_PLAN_TITLE);
	exportResource.setRowList(rowList);
	return exportResource;
	  /**
	     *
	     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
	     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
	     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
             *		     比对生效日期必须大于上一个版本生效日期。
             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
	     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
	     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
	     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
	     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
	     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
	     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
	     *	SR6	（原因是区域与产品用例早已经废除）
	     *	SR7	（原因是区域与产品用例早已经废除）
	     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
	     *	譬如有一个汽运价格方案生效日期为2010-05-31 
	     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
	     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
	     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
	     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
	     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
	     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
	     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
	     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
	     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
	     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
	     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
	     *	校验提示、“****到达城市已经存在， 请检查excel模版”
	     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
	     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
	     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
	     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
	     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
	     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
	     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
	     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
	     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
	     *	请确认是否以该方案为准,请将xxx方案中止。”
	     *	SR14	立即中止功能： 在价格查询列表中，
	     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
	     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
	     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
	     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
	     *	SR15	立即激活功能： 在价格查询列表中，
	     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
	     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
	     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
	     *	立即激活操作的生效时间必须大于等于营业日期!” ，
	     *	即时间可以在今天和今天以后任意调整，
	     *	但是不能调整为昨天的时间
	     */
    }

    /** 
     * 
     * <p>立即激活</p> 
     * 
     * 方法适用场景： 由于实际业务有当天可能发生2次以上的调价操作，
     * 
     * 	  	    故增加立即中止与立即激活应急功能建立权限隔离机制，
     * 
     * 		    根据配置支持不同角色去操作立即失效与立即激活操作。 
     * 
     * 方法名称：immediatelyActiveAirPricePlan
     *  
     *  
     * 方法目的： 提供对空运价格方案的立即激活，可以当天就激活。不必判定是否大于今天
     * 
     * 方法实现步骤： 1、 校验输入数据完整性
     * 		
     * 	       	  2、 校验方案下是否存在明细， 没有明细则提示 “空的方案不可激活”
     * 
     * 	          3、 以上验证是否为有效数据通过后、则开始根据方案信息找计费规则
     * 		 
     * 		  4、是否已经有存在的计费规则、如果存在、说明已经发生冲突、抛出“下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止”
     * 			
     * 	          5、 对计费规则做数据状态有效标记
     * 
     * 		  6、分别对重量、和体积进行计费方式明细的数据处理激活状态
     * 
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2013-3-14 下午2:52:38
     * 
     * @param pricePlanEntity 
     * 
     * @see com.deppon.foss.module.pickup.pricing.api.server.service.IAirPricePlanService#immediatelyActiveAirPricePlan(com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity)
     */
    @Override
    @Transactional
	public void immediatelyActiveAirPricePlan(PricePlanEntity pricePlanEntity) {
		Date currentTime = new Date();
		// 价格方案信息
		if (null == pricePlanEntity) {
			throw new PricePlanException("所选价格方案为空! 请检查", null);
		}
		String pricePlanId = pricePlanEntity.getId();
		Date beginTime = pricePlanEntity.getBeginTime();
		String priceregionId = pricePlanEntity.getPriceRegionId();
		
		PricePlanEntity dbPricePlanEntity = pricePlanDao.selectByPrimaryKey(pricePlanEntity.getId());
		if(null == dbPricePlanEntity){
		    throw new PricePlanException("所选价格方案为空! 请检查",null);  
		}
		dbPricePlanEntity.setModifyDate(currentTime);
		dbPricePlanEntity.setBeginTime(pricePlanEntity.getBeginTime());


		// 同一个价格方案下计费规则
		PriceValuationEntity valEntity = new PriceValuationEntity();
		valEntity.setPricePlanId(pricePlanId);
		List<PriceValuationEntity> valList = priceValuationDao.selectByCodition(valEntity);
		if (CollectionUtils.isEmpty(valList)) {
			throw new PricePlanException("空的方案不能激活!", null);
		}
		// 遍历所有计费规则，
		// 只要有一条明细与DB中存在重复，
		// 则报出异常提示信息，整个方案将不可激活。
		for (PriceValuationEntity priceValuationEntity : valList) {
			// 计费规则影响价格的因素属性
			String arrvRegionId = priceValuationEntity.getArrvRegionId();
			String isCentralizePickup = priceValuationEntity.getCentralizePickup();
			String productCode = priceValuationEntity.getProductCode();
			String goodsTypeCode = priceValuationEntity.getGoodsTypeCode();
			String priceValuationId = priceValuationEntity.getId();
			String flightShift = priceValuationEntity.getLightShift();// 航班

			PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
			priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
			// 同一个计费规则下的所有计价明细 start
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = priceCriteriaDetailDao
					.findPriceCriteriaDetailByCondition(priceCriteriaDetailEntity);
			// 获取计价明细的重，轻货物价格
			PriceCriteriaDetailEntity criteriaDetailWeight = new PriceCriteriaDetailEntity();
			PriceCriteriaDetailEntity criteriaDetailVolume = new PriceCriteriaDetailEntity();
			if (CollectionUtils.isNotEmpty(priceCriteriaDetailEntities)) {
				for (PriceCriteriaDetailEntity tempDetailEntity : priceCriteriaDetailEntities) {
					if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
							.equals(tempDetailEntity.getCaculateType())) {
						criteriaDetailWeight = tempDetailEntity;
					} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(tempDetailEntity
							.getCaculateType())) {
						criteriaDetailVolume = tempDetailEntity;
					}
				}
			}
			BigDecimal weightRate = criteriaDetailWeight.getFeeRate();
			BigDecimal volumeRate = criteriaDetailVolume.getFeeRate();
			// 获取重货价格
			Double currentWeightRate = weightRate == null ? new Double(PricingConstants.ZERO) : weightRate
					.doubleValue();
			// 获取轻货价格
			Double currentVolumeRate = volumeRate == null ? new Double(PricingConstants.ZERO) : volumeRate
					.doubleValue();
			// 获取同一个始发区域的方案下明细重复的记录,
			// 如果存在重复,不能激活当前方案，给予客户端提示
			QueryExistPricePlanDetailBean queryExistPricePlanBean = new QueryExistPricePlanDetailBean();
			// 开始时间
			queryExistPricePlanBean.setBeginTime(beginTime);
			queryExistPricePlanBean.setEndTime(dbPricePlanEntity.getEndTime());
			// 始发价格区域ID
			queryExistPricePlanBean.setPriceRegionId(priceregionId);
			// 产品
			queryExistPricePlanBean.setProductCode(productCode);
			// 货物
			queryExistPricePlanBean.setGoodsTypeCode(goodsTypeCode);
			// 到达区域ID
			queryExistPricePlanBean.setArrvRegionId(arrvRegionId);
			queryExistPricePlanBean.setActive(FossConstants.ACTIVE);
			queryExistPricePlanBean.setIsCentralizePickup(isCentralizePickup);
			queryExistPricePlanBean.setFlightShift(flightShift);// 航班
			String centralizeName = isCentralizePickup;
			if (StringUtil.isNotBlank(isCentralizePickup)) {
				if (StringUtil.equals(isCentralizePickup, FossConstants.ACTIVE)) {
					centralizeName = "是";
				} else {
					centralizeName = "否";
				}
			} else {
				throw new PricePlanException("参数为空, 请联系管理员检查!", null);
			}
			List<ResultPricePlanDetailBean> resultDetailBeans = pricePlanDao
					.isExistRpeatAirLinePricePlanDetailData(queryExistPricePlanBean);
			if (CollectionUtils.isNotEmpty(resultDetailBeans)) {
				for (ResultPricePlanDetailBean resultPricePlanDetailBean : resultDetailBeans) {
					// 价格左区间
					Double leftFeeRate = resultPricePlanDetailBean.getLightFeeRate().doubleValue();
					// 价格右区间
					Double rightFeeRate = resultPricePlanDetailBean.getHeavyFeeRate().doubleValue();
					// 计费类别
					String caculateType = resultPricePlanDetailBean.getCaculateType();
					// 已经存在的价格方案ID
					String existPricePlanId = resultPricePlanDetailBean.getPricePlanId();
					if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(caculateType)) {// 按重量
						// 只对符合的重量范围才进行计算
						if (currentWeightRate >= leftFeeRate && currentWeightRate < rightFeeRate) {
							GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(
									goodsTypeCode, currentTime);
							ProductEntity producTypeEntity = productService.getProductByCache(productCode, currentTime);
							PricePlanEntity selectByPrimaryBean = pricePlanDao.selectByPrimaryKey(existPricePlanId);
							PriceRegionAirEntity priceRegion = regionAirService.searchRegionByID(priceregionId,
									PricingConstants.PRICING_REGION);
							if (priceRegion == null) {
								throw new PricePlanException("根据区域ID没有找到对应的空运区域信息", null);
							}
							PriceRegionAirEntity arrvRegion = regionAirService.searchRegionByID(arrvRegionId,
									PricingConstants.PRICING_REGION);
							if (arrvRegion == null) {
								throw new PricePlanException("根据区域ID没有找到对应的空运区域信息", null);
							}
							StringBuilder itemName = new StringBuilder();
							itemName.append(producTypeEntity.getName());
							itemName.append("-");
							itemName.append(goodsTypeEntity.getName());
							StringBuilder errStr = new StringBuilder();
							errStr.append("始发区域[");
							errStr.append(priceRegion.getRegionName());
							errStr.append("],目的地区域[");
							errStr.append(arrvRegion.getRegionName());
							errStr.append("],是否接货[");
							errStr.append(centralizeName);
							errStr.append("],条目名称[");
							errStr.append(itemName);
							errStr.append("],在价格方案名称为[");
							errStr.append(selectByPrimaryBean.getName());
							errStr.append("],下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止"
									+ selectByPrimaryBean.getName() + "价格方案!");
							throw new PricePlanException(errStr.toString(), null);
						}
					} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(caculateType)) {// 按体积
						// 只对符合的体积范围才进行计算
						if (currentVolumeRate >= leftFeeRate && currentVolumeRate < rightFeeRate) {
							GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(
									goodsTypeCode, currentTime);
							ProductEntity producTypeEntity = productService.getProductByCache(productCode, currentTime);
							PricePlanEntity selectByPrimaryBean = pricePlanDao.selectByPrimaryKey(existPricePlanId);
							PriceRegionAirEntity priceRegion = regionAirService.searchRegionByID(priceregionId,
									PricingConstants.PRICING_REGION);
							if (priceRegion == null) {
								throw new PricePlanException("根据区域ID没有找到对应的空运区域信息", null);
							}
							PriceRegionAirEntity arrvRegion = regionAirService.searchRegionByID(arrvRegionId,
									PricingConstants.PRICING_REGION);
							if (arrvRegion == null) {
								throw new PricePlanException("根据区域ID没有找到对应的空运区域信息", null);
							}
							StringBuilder itemName = new StringBuilder();
							itemName.append(producTypeEntity.getName());
							itemName.append("-");
							itemName.append(goodsTypeEntity.getName());
							StringBuilder errStr = new StringBuilder();
							errStr.append("始发区域[");
							errStr.append(priceRegion.getRegionName());
							errStr.append("],目的地区域[");
							errStr.append(arrvRegion.getRegionName());
							errStr.append("],是否接货[");
							errStr.append(centralizeName);
							errStr.append("],条目名称[");
							errStr.append(itemName);
							errStr.append("],在价格方案名称为[");
							errStr.append(selectByPrimaryBean.getName());
							errStr.append("],下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止"
									+ selectByPrimaryBean.getName() + "价格方案!");
							throw new PricePlanException(errStr.toString(), null);
						}
					}
				}
			}
			if (StringUtil.isNotEmpty(priceValuationId)) {
				List<String> valuationIds = new ArrayList<String>();
				valuationIds.add(priceValuationId);
				priceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
				pricePlanDao.updateByPrimaryKeySelective(dbPricePlanEntity);				
				priceValuationDao.activeValueAdded(valuationIds);
			}
		}
		List<String> priceIdList = new ArrayList<String>();
		priceIdList.add(pricePlanId);
		// 以上全部校验通过,依次激活计价明细，计费规则，价格方案
		
		pricePlanDao.activePricePlan(priceIdList);
		    /**
		     *
		     *  SR1	弹出新增方案界面展示界面录入信息由出发信息与目的地信息组成：
		     *  出发信息包括： 	  a) 生效日期: 设定生效日期在提交的时候判断该新建的版本信息 
		     *    		     以出发地作为标准来判断是否已经存在，没有存在则正常录入且截止日期默认为2999-01-01
	             *		     生效日期必须大于今天（避免防止上午开过的单，而下午创建新的价格版本情况）， 
	             *		     如果存在则是需要对原有数据作变更的概念，需要校验生效日期在上一个最新启用的版本中
	             *		     （依靠生效日期去比对最新版本，原因生效日期归属价格版本中不会出现重复版本信息）
	             *		     比对生效日期必须大于上一个版本生效日期。
	             *		  b) 始发区域: 区域信息来自产品基础数据维护下的区域来填充该下拉表
	             *		    请先熟悉 DP-FOSS-接送货系统用例-产品价格-区域列表-录入区域SUC-587-V0.1用例。
	             *		    （区域分别有时效与价格，在此只需要获取价格区域）           
		     *	  	  c)（所有激活操作统一到查询列表中去操作） 未被激活的版本信息，营业部门开单时不能被正常使用。
		     *	  	  d) 方案描述：  对建立新方案的一些描述信息。
		     *	  	  e)新增目的地明细信息时， 可支持对明细的增加，删除，修改。
		     *	目的地信息：  由于可以设置至少一个到N个元素信息（防止数据不完整性）改为每一笔明细保存都会记录在数据库中以确保数据的完整性。
		     *	SR2	目的地信息也是来自产品基础数据中的区域信息，和以上说的始发区域是同一数据源
		     *	SR3	1．最低一票根据所选择的条目信息带出默认最低一票设置，在此可做修改并将其绑定到区域与价格上
		     *	SR6	（原因是区域与产品用例早已经废除）
		     *	SR7	（原因是区域与产品用例早已经废除）
		     *	SR8	 版本号无意义， 都是根据生效时间来做版本控制。
		     *	譬如有一个汽运价格方案生效日期为2010-05-31 
		     *	截止日期为2999-01-01，升级时候生效日期为2012-12-11，
		     *	那么会修改之前版本截止日期为当前生效日期的前一天为2012-12-10。	
		     *	这样在两段不同时间轴中出现两个不同价格的版本信息。
		     *	SR9	新增价格方案时，所选择的区域信息（始发区域/目的地区域）
		     *	都需要过滤（由于区域管理按照价格时效部门不同做以划分），
		     *	只能取价格相关的区域信息作为数据源。如：图2中界面元素下拉列标的始发区域，
		     *  图3中目的地城市（实际目的地城市也是区域信息）这些数据源都需要过滤为价格的区域,
		     *	excel所提供的区域数据信息也需要过滤掉时效区域信息。若发现excel中所填写的区域信息不存在或存在
		     *	但区域性质不是价格区域则提示 “excel文件导入第N行区域信息找不到”。
		     *	SR10	图1是价格方案主信息查询界面，界面中的 “导入”按钮是批量导入所有价格信息。 
		     *  导入模版-  ，导入的规则：同一个出发城市的到达城市不能出现重复列，
		     *	校验提示、“****到达城市已经存在， 请检查excel模版”
		     *	SR12	汽运价格方案新增时，出发地信息保存后，提示“汽运价格方案保存成功,
		     *	是否立即添加相应目的地价格明细信息?” 选择“确定”，
		     *	回到该界面出发地所有信息变为不可编辑，然后对目的地明细信息的数据操作， 
		     *	如果选择“取消”，只是建立了一个空的价格草稿方案待后续继续添加。
		     *	只有在查询界面中做激活时才会检查。空的方案不能被激活使用。
		     *	SR13	在草稿状态的方案被建立之后做激活时，会去检查具体草稿方案信息下的
		     *	目的地明细是否在指定的生效日期是不是已经存在相同的重复数据检查。
		     *	重复数据被检查的标准定义是： 生效日期+方案始发区域+目的地区域+产品+是否接货，
		     *	如果存在则提示“该方案下xxx目的地xxx产品xxx是否接货已经在另一个xxx方案下存在，
		     *	请确认是否以该方案为准,请将xxx方案中止。”
		     *	SR14	立即中止功能： 在价格查询列表中，
		     *	只能选择一条激活的数据点击列表中“立即中止”按钮弹出 选择中止时间，
		     *	点击中止，,选择“提交”系统将选中的方案截止时间调整为当前设置的中止的时间，
		     *	出现小于当前营业日期系统提示“立即中止操作的截止时间必须大于等于营业日期!” 
		     *	，即时间可以在今天和今天以后任意调整，但是不能调整为昨天的时间 。
		     *	SR15	立即激活功能： 在价格查询列表中，
		     *	只能选择一条未激活的数据点击列表中“立即激活”按钮弹出 
		     *	选择生效时间，点击激活按钮，选择“是”系统将选中的方案生效时
		     *	间调整为当前设置的生效时间，出现小于当前营业日期系统提示“
		     *	立即激活操作的生效时间必须大于等于营业日期!” ，
		     *	即时间可以在今天和今天以后任意调整，
		     *	但是不能调整为昨天的时间
		     */
	}
    @Override
    public List<PricePlanDetailDto> queryAirPricePlanDetailInfo(
	    QueryPricePlanDetailBean queryPricePlanDetailBean) {
	   String productItemCode = queryPricePlanDetailBean.getProductItemCode();
	    if(StringUtil.isNotBlank(productItemCode)){
		ProductItemEntity productItemEntity = productItemDao.findProductItemByCode(productItemCode, new Date());
		 if(null != productItemEntity){
			queryPricePlanDetailBean.setGoodsTypeCode(productItemEntity.getGoodstypeCode());
			queryPricePlanDetailBean.setProductCode(productItemEntity.getProductCode());
		 } 
	    } 
	    List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList =  pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean);
	    if(CollectionUtils.isNotEmpty(resultPricePlanDetailBeanList)){
		  return this.boxHeaveAndLight(resultPricePlanDetailBeanList);
	    } 
	    return null;
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
     */
}