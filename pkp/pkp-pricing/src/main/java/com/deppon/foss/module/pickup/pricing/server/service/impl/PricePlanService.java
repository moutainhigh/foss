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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopPriceDetailSectionDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PopPriceDetailSectionEntity;
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
 * 
 * 价格管理信息
 * 
 * @author 岳洪杰
 * 
 * @date 2012-10-12 上午10:55:12
 * 
 * @since
 * 
 * @version
 */
public class PricePlanService implements IPricePlanService {
	private static final Logger log = Logger.getLogger(PricePlanService.class);
	/**
	 * 价格方案主信息
	 */
	@Inject
	IPricePlanDao pricePlanDao;

	/**
	 * 计费规则 
	 */
	@Inject
	IPriceValuationDao priceValuationDao;
	/**
	 * 计费明细
	 */
	@Inject
	IPriceCriteriaDetailDao priceCriteriaDetailDao;
	/**
	 * 产品条目
	 */
	@Inject
	IProductItemDao productItemDao;
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
	IRegionService regionService;
	/**
	 * 员工信息Service
	 */
	@Inject
	IEmployeeService employeeService;
	/**
	 * 魔法数字1
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int ONE=1;
	/**
	 * 魔法数字2
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int TWO=2;
	/**
	 * 魔法数字3
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int THREE=3;
	/**
	 * 魔法数字4
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int FOUR=4;
	/**
	 * 魔法数字5
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int FIVE=5;
	/**
	 * 魔法数字6
	 * @author Pop-Team-LuoMengxiang
	 * @category 2014.10.30
	 */
	public static final int SIX=6;

	@Inject
	/**
	 * 汽运到达区域 
	 */
	private IRegionArriveService regionArriveService;

	@Inject
	/***
	 * 计费明细分段dao
	 * @author POP-Team-LuoMengXiang
	 * @date 2014/10/24
	 */
	private IPopPriceDetailSectionDao popPriceDetailSectionDao;

	/**
	 * 设置计费明细分段dao
	 * 
	 * @author POP-Team-LuoMengXiang
	 * @date 2014/10/24
	 */
	public void setPopPriceDetailSectionDao(
			IPopPriceDetailSectionDao popPriceDetailSectionDao) {
		this.popPriceDetailSectionDao = popPriceDetailSectionDao;
	}

	/**
	 * 汽运到达区域
	 */
	public void setRegionArriveService(IRegionArriveService regionArriveService) {
		this.regionArriveService = regionArriveService;
	}

	/**
	 * 设置 区域Service.
	 * 
	 * @param regionService
	 *            the new 区域Service
	 */
	public void setRegionService(IRegionService regionService) {
		this.regionService = regionService;
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
	public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}

	/**
	 * 设置 计费明细.
	 * 
	 * @param priceCriteriaDetailDao
	 *            the new 计费明细
	 */
	public void setPriceCriteriaDetailDao(
			IPriceCriteriaDetailDao priceCriteriaDetailDao) {
		this.priceCriteriaDetailDao = priceCriteriaDetailDao;
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
	 * 
	 * <p>
	 * 将状态为未激活的记录更新为激活状态，并且要新激活的记录需同已有激活记录进行失效截止日期的正确衔接
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-12 下午2:04:13
	 * 
	 * @param pricePlanIds
	 * 
	 * @see
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
				// 得到某个价格放下所有的规则,并校验这些规则是否出现重复存在
				List<PriceValuationEntity> valList = priceValuationDao
						.selectByCodition(valEntity);
				// 有效的规则信息集合
				List<String> valuationIds = new ArrayList<String>();
				if (CollectionUtils.isNotEmpty(valList)) {
					// 遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
					for (PriceValuationEntity priceValuationEntity : valList) {
						// 如果一个规则下校验不出现错误则往计费规则集合数据中填充该规则
						if (checkPricePlanDetailActiveDate(pricePlanEntity,
								priceValuationEntity)) {
							valuationIds.add(priceValuationEntity.getId());
						}
					}
				} else {
					throw new PricePlanException("空的方案不能激活!", null);
				}
				// 以上全部校验通过,依次激活计价明细，计费规则，价格方案
				priceCriteriaDetailDao
						.activeCriteriaDetailByValuationIds(valuationIds);
				priceValuationDao.activeValueAdded(valuationIds);
			}
			pricePlanDao.activePricePlan(pricePlanIds);
		}
	}

	/**
	 * 
	 * <p>
	 * 检查当前新增、或修改的价格明细数据是否为有效
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-2-28 下午3:44:02
	 * 
	 * @see
	 */
	private boolean checkPricePlanDetailActiveDate(
			PricePlanEntity pricePlanEntity,
			PriceValuationEntity priceValuationEntity) {
		PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
		priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity
				.getId());
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
				} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
						.equals(tempDetailEntity.getCaculateType())) {
					criteriaDetailVolume = tempDetailEntity;
				}
			}
		}
		BigDecimal weightRate = criteriaDetailWeight.getFeeRate();
		BigDecimal volumeRate = criteriaDetailVolume.getFeeRate();
		Double currentWeightRate = weightRate == null ? new Double(
				PricingConstants.ZERO) : weightRate.doubleValue();
		Double currentVolumeRate = volumeRate == null ? new Double(
				PricingConstants.ZERO) : volumeRate.doubleValue();
		// 获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
		QueryExistPricePlanDetailBean queryExistPricePlanBean = new QueryExistPricePlanDetailBean();
		// 生效日期
		queryExistPricePlanBean.setBeginTime(pricePlanEntity.getBeginTime());
		// 始发区域
		queryExistPricePlanBean.setPriceRegionId(pricePlanEntity
				.getPriceRegionId());
		// 产品
		queryExistPricePlanBean.setProductCode(priceValuationEntity
				.getProductCode());
		// 货物
		queryExistPricePlanBean.setGoodsTypeCode(priceValuationEntity
				.getGoodsTypeCode());
		// 到达区域
		queryExistPricePlanBean.setArrvRegionId(priceValuationEntity
				.getArrvRegionId());
		// 是否接货
		queryExistPricePlanBean.setIsCentralizePickup(priceValuationEntity
				.getCentralizePickup());
		// 有效
		queryExistPricePlanBean.setActive(FossConstants.ACTIVE);
		// 是否接货中文显示特殊处理
		String centralizeName = null;
		if (StringUtil.isNotBlank(priceValuationEntity.getCentralizePickup())) {
			if (StringUtil.equals(priceValuationEntity.getCentralizePickup(),
					FossConstants.ACTIVE)) {
				centralizeName = "是";
			} else {
				centralizeName = "否";
			}
		} else {
			throw new PricePlanException("参数为空, 请联系管理员检查!", null);
		}
		List<ResultPricePlanDetailBean> resultDetailBeans = pricePlanDao
				.isExistRpeatPricePlanDetailData(queryExistPricePlanBean);
		if (CollectionUtils.isNotEmpty(resultDetailBeans)) {
			for (ResultPricePlanDetailBean resultPricePlanDetailBean : resultDetailBeans) {
				Double leftFeeRate = resultPricePlanDetailBean
						.getLightFeeRate().doubleValue();
				Double rightFeeRate = resultPricePlanDetailBean
						.getHeavyFeeRate().doubleValue();
				String caculateType = resultPricePlanDetailBean
						.getCaculateType();
				String existPricePlanId = resultPricePlanDetailBean
						.getPricePlanId();
				// 按重量
				if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
						.equals(caculateType)) {
					// 只对符合的重量范围才进行计算
					if (currentWeightRate >= leftFeeRate
							&& currentWeightRate < rightFeeRate) {
						throw new PricePlanException(checkDetailErrorMessage(
								pricePlanEntity, priceValuationEntity,
								existPricePlanId, centralizeName), null);
					}
					// 按体积
				} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
						.equals(caculateType)) {
					// 只对符合的体积范围才进行计算
					if (currentVolumeRate >= leftFeeRate
							&& currentVolumeRate < rightFeeRate) {
						throw new PricePlanException(checkDetailErrorMessage(
								pricePlanEntity, priceValuationEntity,
								existPricePlanId, centralizeName), null);
					}
				}
			}
		}
		// 以上校验没有出现错误返回true
		return true;
	}

	/**
	 * 
	 * <p>
	 * 激活方案检查明细数据出现错误时给出的提示信息
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-2-28 下午4:52:08
	 * 
	 * @param pricePlanEntity
	 *            价格主信息
	 * 
	 * @param priceValuationEntity
	 *            规则信息
	 * 
	 * @param existPricePlanId
	 *            已激活且存在的主信息
	 * 
	 * @param centralizeName
	 *            是否上门
	 * 
	 * @return
	 * 
	 * @see
	 */
	private String checkDetailErrorMessage(PricePlanEntity pricePlanEntity,
			PriceValuationEntity priceValuationEntity, String existPricePlanId,
			String centralizeName) {
		// 依次寻找，产品,货物,区域
		ProductEntity producTypeEntity = productService.getProductByCache(
				priceValuationEntity.getProductCode(), new Date());
		GoodsTypeEntity goodsTypeEntity = goodsTypeService
				.queryGoodsTypeByGoodTypeCode(
						priceValuationEntity.getGoodsTypeCode(), new Date());
		PricePlanEntity selectByPrimaryBean = pricePlanDao
				.selectByPrimaryKey(existPricePlanId);
		// 始发区域
		PriceRegionEntity priceRegion = regionService.searchRegionByID(
				pricePlanEntity.getPriceRegionId(),
				PricingConstants.PRICING_REGION);
		// 到达区域
		PriceRegionArriveEntity arrvRegion = regionArriveService
				.searchRegionByID(priceValuationEntity.getArrvRegionId(),
						PricingConstants.PRICING_REGION);
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
	public void deletePricePlan(List<String> pricePlanIds) {
		if (CollectionUtils.isNotEmpty(pricePlanIds)) {
			
			List<String> priceValuationIdsList = new ArrayList<String>();
			List<String> pricePlanIdsList = new ArrayList<String>();

			for (int i = 0; i < pricePlanIds.size(); i++) {
				String pricePlanId = pricePlanIds.get(i);
				PriceValuationEntity valEntity = new PriceValuationEntity();
				valEntity.setPricePlanId(pricePlanId);
				List<PriceValuationEntity> valList = priceValuationDao
						.selectByCodition(valEntity);
				if (CollectionUtils.isNotEmpty(valList)) {
					for (int j = 0; j < valList.size(); j++) {
						PriceValuationEntity val = (PriceValuationEntity) valList
								.get(j);
						String id = val.getId();
						/**
						 * 根据规则id删除计价分段明细的信息
						 * @author Pop-Team-LuoMengxiang
						 * @date 2014.11.1
						 */
						priceValuationIdsList.add(id);//批量删除
						
//						popPriceDetailSectionDao.deleteByValuationId(id);
//						priceCriteriaDetailDao
//								.deleteCriteriaDetailByValuationId(id);
					}
//					priceValuationDao
//							.deltePriceValuationByPricePlanId(pricePlanId);
				}
				pricePlanIdsList.add(pricePlanId);//批量删除
//				pricePlanDao.deleteByPrimaryKey(pricePlanId);
			}
			pricePlanDao.deletePricePlanBatch(priceValuationIdsList, pricePlanIdsList);
		}
	}

	/**
	 * 
	 * <p>
	 * (复制方案)
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-12 下午5:24:19
	 * 
	 * @param pricePlanId
	 * 
	 * @return string pricePlanId
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
		List<PriceValuationEntity> tempPriceValuationEntitys = priceValuationDao
				.selectByCodition(priceValuationEntity);
		String newPricePlanId = UUIDUtils.getUUID();
		// 复制草稿
		pricePlanEntity.setActive(FossConstants.INACTIVE);
		pricePlanEntity.setId(newPricePlanId);
		// 重新设置复制方案的默认截止日期
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		try {
			pricePlanEntity.setEndTime(dateFormat.parse("2999-12-31 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		pricePlanDao.insert(pricePlanEntity);

		if (CollectionUtils.isNotEmpty(tempPriceValuationEntitys)) {
			for (int loop = 0; loop < tempPriceValuationEntitys.size(); loop++) {
				PriceValuationEntity tempValuationEntity = tempPriceValuationEntitys
						.get(loop);
				//获取原方案的规则id  219413-Luomengxiang  2015.1.19
				String valuationId = tempValuationEntity.getId();
				List<PriceCriteriaDetailEntity> tempPriceCriteriaDetailEntitys = priceCriteriaDetailDao
						.selectByValuationId(valuationId);
				String newValuationId = UUIDUtils.getUUID();
				tempValuationEntity.setId(newValuationId);
				tempValuationEntity.setPricePlanId(newPricePlanId);
				priceValuationDao.insertSelective(tempValuationEntity);
				if (CollectionUtils.isNotEmpty(tempPriceCriteriaDetailEntitys)) {
					for (int i = 0; i < tempPriceCriteriaDetailEntitys.size(); i++) {
						PriceCriteriaDetailEntity tempPriceCriteriaDetailEntity = tempPriceCriteriaDetailEntitys
								.get(i);
						//获取原方案的明细id
						String criteriaId = tempPriceCriteriaDetailEntity.getId();
						String newCriteriaId = UUIDUtils.getUUID();
						tempPriceCriteriaDetailEntity
								.setId(newCriteriaId);
						tempPriceCriteriaDetailEntity
								.setPricingValuationId(newValuationId);
						priceCriteriaDetailDao
								.insertSelective(tempPriceCriteriaDetailEntity);
						//根据明细id查询分段信息
						List<PopPriceDetailSectionEntity> popPriceDetailSectionEntities = 
								popPriceDetailSectionDao.selectByCriteriaId(criteriaId);
						PopPriceDetailSectionEntity sectionEntity = null;
						for(PopPriceDetailSectionEntity entity : popPriceDetailSectionEntities){
							//若为重货
							if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(
									tempPriceCriteriaDetailEntity.getCaculateType())){
								sectionEntity = new PopPriceDetailSectionEntity();
								//重货第一段
								if(String.valueOf(ONE).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(ONE));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//重货第二段
								if(String.valueOf(TWO).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(TWO));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//重货第三段
								if(String.valueOf(THREE).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(THREE));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//重货第四段
								if(String.valueOf(FOUR).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(FOUR));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//重货第五段
								if(String.valueOf(FIVE).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(FIVE));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//重货第六段
								if(String.valueOf(SIX).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(SIX));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
							}
							//若为轻货
							if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(
									tempPriceCriteriaDetailEntity.getCaculateType())){
								sectionEntity = new PopPriceDetailSectionEntity();
								//轻货第一段
								if(String.valueOf(ONE).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(ONE));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//轻货第二段
								if(String.valueOf(TWO).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(TWO));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//轻货第三段
								if(String.valueOf(THREE).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(THREE));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//轻货第四段
								if(String.valueOf(FOUR).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(FOUR));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//轻货第五段
								if(String.valueOf(FIVE).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(FIVE));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
								//轻货第六段
								if(String.valueOf(SIX).equals(entity.getSectionID())){
									sectionEntity.setId(UUIDUtils.getUUID());
									sectionEntity.setSectionID(String.valueOf(SIX));
									sectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
								    sectionEntity.setCriteriaDetailID(newCriteriaId);
								    sectionEntity.setValuationId(newValuationId);
								    sectionEntity.setFee(entity.getFee());
								    sectionEntity.setCriticalValue(entity.getCriticalValue());
								    popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
								}
							}
						}
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
		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(
				pricePlan.getPriceRegionId(), PricingConstants.PRICING_REGION);
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
		pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_QY_FLAG);
		List<PricePlanEntity> list = pricePlanDao.queryPricePlanBatchInfo(
				pricePlanEntity, start, limit);
		return convertToRegionName(list);
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.
	 *            PricePlanService.queryComparePricePlanBatchInfo
	 * @Description:分页查询比对价格方案
	 * 
	 * @param pricePlanEntity
	 * @param start
	 * @param limit
	 * @return
	 * 
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 下午3:52:50
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2014-5-20 130376-YANGKANG v1.0.0 create
	 */
	@Override
	public List<PricePlanEntity> queryComparePricePlanBatchInfo(
			PricePlanEntity pricePlanEntity, int start, int limit) {
		// 设置条件
		if (PricingConstants.ALL.equals(pricePlanEntity.getActive())) {
			pricePlanEntity.setActive(null);
		}
		pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_QY_FLAG);
		List<PricePlanEntity> list = pricePlanDao
				.queryComparePricePlanBatchInfo(pricePlanEntity, start, limit);
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
			PriceRegionEntity priceRegionEntity = regionService
					.searchRegionByID(priceRegionId,
							PricingConstants.PRICING_REGION);
			if (StringUtil.isNotEmpty(pricePlanEntity.getModifyUser())) {
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
		queryBean.setTransportFlag(PricingConstants.TRANSPORT_QY_FLAG);
		List<PricePlanEntity> pricePlanEntitys = pricePlanDao
				.queryPricePlanBatchInfo(queryBean);
		if (CollectionUtils.isNotEmpty(pricePlanEntitys)) {
			throw new PricePlanException("方案名称为:" + planName + "已经存在,不可重复添加",
					null);
		}
		pricePlanDao.insert(pricePlanEntity);
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
		pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_QY_FLAG);
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
	 * 计费明细分段
	 * 
	 * @author POP-Team-LuoMengxiang
	 * @category /10/24
	 */
	private static final String PRICING_DETAIL_SECTION = "PRICINGDETAILSECTION";

	/**
	 * 
	 * <p>
	 * (业务数据场景: 计费规则一条对应计价明细两条，分析数据进行持久化)
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * @date 2012-12-14 下午4:57:40
	 * @param detailList
	 * @param pricePriceId
	 * @return
	 * @see
	 */
	private Map<String, Object> analysisPricePlanData(
			List<PricePlanDetailDto> detailList, String pricePlanId) {
		Date currentTime = new Date();
		// 计费规则
		List<PriceValuationEntity> valuationEntitys = new ArrayList<PriceValuationEntity>();
		// 重货
		List<PriceCriteriaDetailEntity> heavyList = new ArrayList<PriceCriteriaDetailEntity>();
		// 轻货
		List<PriceCriteriaDetailEntity> lightList = new ArrayList<PriceCriteriaDetailEntity>();
		//重货和轻货分段明细   219413-Luomengxiang
		PopPriceDetailSectionEntity heavySectionEntity=null;
		PopPriceDetailSectionEntity lightSectionEntity=null;
		//明细分段   219413-	Pop-Team-Luomengxiang
		List<PopPriceDetailSectionEntity> sections = new ArrayList<PopPriceDetailSectionEntity>();
		// 整理后的数据容器,主要是对计费规则,计价明细的数据收集
		Map<String, Object> priceCriteriaDetailMap = new HashMap<String, Object>();
		PricePlanEntity pricePlanEntity = pricePlanDao
				.selectByPrimaryKey(pricePlanId);
		PriceValuationEntity valuationEntity = new PriceValuationEntity();
		for (int i = 0; i < detailList.size(); i++) {
			 heavySectionEntity = new PopPriceDetailSectionEntity();
			 lightSectionEntity = new PopPriceDetailSectionEntity();
			 PricePlanDetailDto pricePlanDetailDto = detailList.get(i);
			/**
			 * 如果是第一段 ,使用第一段数据封装计费规则和原来的计费明细表的 重货价格明细和轻货价格明细
			 * @author POP-Team-LuoMengXiang
			 * @date 2014/10/24
			 */
			if (String.valueOf(ONE).equals(pricePlanDetailDto.getSectionID())) {
				pricePlanDetailDto.setArrvRegionName(pricePlanDetailDto
						.getArrvRegionName());
				pricePlanDetailDto.setPricePlanId(pricePlanId);
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
				valuationEntity.setPricePlanId(pricePlanDetailDto
						.getPricePlanId());
				// 是否集中接货
				valuationEntity.setCentralizePickup(pricePlanDetailDto
						.getCentralizePickup());
				// 类型-价格方案
				valuationEntity
						.setType(PricingConstants.VALUATION_TYPE_PRICING);
				// 到达区域ID
				valuationEntity.setArrvRegionId(pricePlanDetailDto
						.getArrvRegionId());
				// 价格方案主信息中的始发区域ID
				valuationEntity.setDeptRegionId(pricePlanEntity
						.getPriceRegionId());
				// 价格方案主信息中的始发区域ID
				valuationEntity
						.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
				/* 收集计费规则信息* */
				valuationEntity.setId(UUIDUtils.getUUID());
				valuationEntity.setProductId(productItemEntity.getProductId());
				valuationEntity.setGoodsTypeId(productItemEntity
						.getGoodstypeId());
				valuationEntity
						.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
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
				//设置计费规则id    219413-Luomengxiang
				pricePlanDetailDto.setValuationId(valuationEntity.getId());
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
				heavyEntity.setLeftrange(BigDecimal
						.valueOf(PricingConstants.ZERO));
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
				lightEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
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
				lightEntity.setLeftrange(BigDecimal
						.valueOf(PricingConstants.ZERO));
				lightEntity.setRightrange(BigDecimal
						.valueOf(PricingConstants.CRITERIA_DETAIL_VOLUME_MAX));
				lightEntity.setId(UUIDUtils.getUUID());
				// 始发区域ID
				lightEntity.setDeptRegionId(pricePlanEntity.getPriceRegionId());
				lightList.add(lightEntity);
				heavyList.addAll(lightList);
				buildSectionList(sections, heavySectionEntity,
						lightSectionEntity, pricePlanDetailDto);
			} else {
				//设置计费规则id    219413-Luomengxiang
				pricePlanDetailDto.setValuationId(valuationEntity.getId());
				buildSectionList(sections, heavySectionEntity,
						lightSectionEntity, pricePlanDetailDto);
			}
		}
		priceCriteriaDetailMap.put(PricePlanService.VALUATION_RULE,
				valuationEntitys);
		priceCriteriaDetailMap
				.put(PricePlanService.PRICING_CRITERIA, heavyList);
		priceCriteriaDetailMap.put(PricePlanService.PRICING_DETAIL_SECTION,
				sections);
		return priceCriteriaDetailMap;
	}
  /**
   * 对重、轻货明细分段的处理
   * @author Pop-Team-Luomengxiang
   * @param sections
   * @param heavySectionEntity
   * @param lightSectionEntity
   * @param pricePlanDetailDto
   * @date 2014.11.4
   */
	private void buildSectionList(List<PopPriceDetailSectionEntity> sections,
			PopPriceDetailSectionEntity heavySectionEntity,
			PopPriceDetailSectionEntity lightSectionEntity,
			PricePlanDetailDto pricePlanDetailDto) {
		//重货明细分段
		heavySectionEntity.setId(UUIDUtils.getUUID());
		heavySectionEntity.setSectionID(pricePlanDetailDto.getSectionID());
		heavySectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		heavySectionEntity.setFee(NumberUtils.multiply(pricePlanDetailDto.getHeavyPrice(),PricingConstants.YUTOFEN));
		heavySectionEntity.setCriticalValue(NumberUtils.multiply(pricePlanDetailDto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
		heavySectionEntity.setDescription(pricePlanDetailDto.getRemark());
		heavySectionEntity.setValuationId(pricePlanDetailDto.getValuationId());
		sections.add(heavySectionEntity);
		//轻货明细分段
		lightSectionEntity.setId(UUIDUtils.getUUID());
		lightSectionEntity.setSectionID(pricePlanDetailDto.getSectionID());
		lightSectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
		lightSectionEntity.setFee(NumberUtils.multiply(pricePlanDetailDto.getLightPrice(),PricingConstants.YUTOFEN));
		lightSectionEntity.setCriticalValue(NumberUtils.multiply(pricePlanDetailDto.getLightCriticalVal(),PricingConstants.YUTOFEN));
		lightSectionEntity.setDescription(pricePlanDetailDto.getRemark());
		lightSectionEntity.setValuationId(pricePlanDetailDto.getValuationId());
		sections.add(lightSectionEntity);
	}

	/**
	 * 
	 * queryPricePlanDetailInfo(查询价格方案明细分页)
	 * 
	 * @param queryPricePlanDetailBean
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @return
	 * 
	 *         List<ResultPricePlanDetailBean>
	 * 
	 * @exception
	 * 
	 * @since 1.0.0
	 */
	@Override
	public List<PricePlanDetailDto> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean,
			int start,int limit) {
			if (null != queryPricePlanDetailBean) {
				String productItemCode = queryPricePlanDetailBean.getProductItemCode();
			if (StringUtil.isNotBlank(productItemCode)) {
				ProductItemEntity productItemEntity = productItemDao.findProductItemByCode(productItemCode, new Date());
				if (null != productItemEntity) {
					queryPricePlanDetailBean.setGoodsTypeCode(productItemEntity.getGoodstypeCode());
					queryPricePlanDetailBean.setProductCode(productItemEntity.getProductCode());
				}
			}
		}
			List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList = pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean, 
					start,limit);
			if (CollectionUtils.isNotEmpty(resultPricePlanDetailBeanList)) {
				return appendSections(this.boxHeaveAndLight(resultPricePlanDetailBeanList));
			}
		   return null;
	}

	/**
	 * 同一计费规则封装重货和轻货价格,同一计费规则下分别存有至少两条对重货与轻货的明细， 或者至多12条对重货与轻货的明细
	 * 
	 * @author Pop-Team-Luomengxiang
	 * 
	 * @param resultPopPriceDetailSectionList
	 * 
	 * @return pricePlanDetailDtos
	 * 
	 * @category 2014.10.30
	 */
	@Override
	public List<PricePlanDetailDto> appendSections(
			List<PricePlanDetailDto> pricePlanDetailDtoList) {
				List<PricePlanDetailDto> pricePlanDetailDtos = new ArrayList<PricePlanDetailDto>();
				if (null != pricePlanDetailDtoList) {
		             for(PricePlanDetailDto pricePlanDetailDto : pricePlanDetailDtoList){
		                	 //获得规则id
		            	     String valuationId=pricePlanDetailDto.getValuationId();
		                	 //根据规则id查询到计价分段明细的信息，并返回到popPriceDetailSectionEntityList中
		            	     List<PopPriceDetailSectionEntity> popPriceDetailSectionEntityList=popPriceDetailSectionDao.selectByValuationId(valuationId);
		                     if(null !=popPriceDetailSectionEntityList){
		                          for(PopPriceDetailSectionEntity entities : popPriceDetailSectionEntityList){
		                        	  //根据分段Id,设置相应段数的轻、重货信息
		                        	if(String.valueOf(ONE).equals(entities.getSectionID())){
		                                  if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
		                                	   pricePlanDetailDto.setHeavyPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                                	   pricePlanDetailDto.setRemark(entities.getDescription());
		                                  }else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
		                                	  pricePlanDetailDto.setLightPrice(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                                	  pricePlanDetailDto.setRemark(entities.getDescription());
		                                  }          
		                        }else if(String.valueOf(TWO).equals(entities.getSectionID())){
		                        	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
		                            	  pricePlanDetailDto.setHeavyPrice2(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        		  pricePlanDetailDto.setHeavyCriticalVal2(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
			                              	pricePlanDetailDto.setLightPrice2(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100))); 
			                        		pricePlanDetailDto.setLightCriticalVal2(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                            }          
		                        }else if(String.valueOf(THREE).equals(entities.getSectionID())){
		                        	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
		                        		  pricePlanDetailDto.setHeavyPrice3(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        		  pricePlanDetailDto.setHeavyCriticalVal3(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
		                              	  pricePlanDetailDto.setLightPrice3(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              	  pricePlanDetailDto.setLightCriticalVal3(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              }          
			                  }else if(String.valueOf(FOUR).equals(entities.getSectionID())){
		                        	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
		                        		  pricePlanDetailDto.setHeavyPrice4(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        		  pricePlanDetailDto.setHeavyCriticalVal4(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
		                              	  pricePlanDetailDto.setLightPrice4(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              	  pricePlanDetailDto.setLightCriticalVal4(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              }          
			                  }else if(String.valueOf(FIVE).equals(entities.getSectionID())){
		                        	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
		                        		  pricePlanDetailDto.setHeavyPrice5(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        		  pricePlanDetailDto.setHeavyCriticalVal5(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
		                              	  pricePlanDetailDto.setLightPrice5(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              	  pricePlanDetailDto.setLightCriticalVal5(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              }          
			                  }else if(String.valueOf(SIX).equals(entities.getSectionID())){
		                        	if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entities.getCaculateType())){
		                        		  pricePlanDetailDto.setHeavyPrice6(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        		  pricePlanDetailDto.setHeavyCriticalVal6(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                        	}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entities.getCaculateType())){
		                              	  pricePlanDetailDto.setLightPrice6(entities.getFee().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              	  pricePlanDetailDto.setLightCriticalVal6(entities.getCriticalValue().divide(BigDecimal.valueOf(NumberConstants.NUMBER_100)));
		                              }          
			                    }
		                  }
		             }
		                     pricePlanDetailDtos.add(pricePlanDetailDto);	     
		     }
		}
				            return pricePlanDetailDtos;
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
			/**
			 * 加入查询分段明细
			 * @author Pop-Team-LuoMengXiang
			 * @date 2014.11.3
			 */
			return appendSections(this.boxHeaveAndLight(resultPricePlanDetailBeanList));
		}
		return null;
	}

	/**
	 * 
	 * queryPricePlanDetailInfoCount(查询价格方案明细总记录数)
	 * 
	 * @param queryPricePlanDetailInfoCount
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @return
	 * 
	 *         Long
	 * 
	 * @exception
	 * 
	 * @since 1.0.0
	 */
	@Override
	public Long queryPricePlanDetailInfoCount(
			QueryPricePlanDetailBean queryPricePlanDetailBean) {
		return pricePlanDao
				.queryPricePlanDetailInfoCount(queryPricePlanDetailBean);
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
			PriceRegionArriveEntity priceRegionEntity = regionArriveService
					.searchRegionByID(r1.getArrvRegionId(),
							PricingConstants.PRICING_REGION);
			if (null != priceRegionEntity) {
				pricePlanDetailDto.setArrvRegionName(priceRegionEntity
						.getRegionName());
				pricePlanDetailDto.setArrvRegionId(priceRegionEntity.getId());
			}
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
						// 设置重货价格
						BigDecimal heavryPrice = BigDecimal.valueOf(r2
								.getFeeRate().doubleValue() / NumberConstants.NUMBER_100);
						pricePlanDetailDto.setHeavyPrice(heavryPrice);
					} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
							.equalsIgnoreCase(r2.getCaculateType())) {
						// 设置轻货价格
						BigDecimal lightPricePrice = BigDecimal.valueOf(r2
								.getFeeRate().doubleValue() / NumberConstants.NUMBER_100);
						pricePlanDetailDto.setLightPrice(lightPricePrice);
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
		List<PriceValuationEntity> priceValuationEntities = priceValuationDao
				.selectByCodition(valuationEntity);
		if (CollectionUtils.isNotEmpty(priceValuationEntities)) {
			for (int i = 0; i < priceValuationEntities.size(); i++) {
				PriceValuationEntity priceValuationEntity = priceValuationEntities
						.get(i);
				priceValuationEntity.setDeptRegionId(pricePlanEntity
						.getPriceRegionId());
				priceValuationDao.updateValuation(priceValuationEntity);
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
		//修改后的结果集   219413-Luomengxiang  2014.11.10
		List<PricePlanDetailDto> resultPricePlanDetailDtoList=new ArrayList<PricePlanDetailDto>();
		// 获得第一段的dto明细  219413-Luomengxiang
		PricePlanDetailDto pricePlanDetailDto= getFirstPricePlanDetailDto(priceManageMentVo.getPricePlanDetailDtoList());
		PriceValuationEntity valuationEntity = priceValuationDao
				.selectByPrimaryKey(pricePlanDetailDto.getValuationId());
		// 通过计价条目找到产品与货物信息  219413-Luomengxiang
		String productItemId = pricePlanDetailDto.getProductItemId();
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
			PriceRegionArriveEntity priceRegionEntity = regionArriveService
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
		priceValuationDao.updateValuation(valuationEntity);
		// 修改计价明细
		String valuationId = valuationEntity.getId();
		List<PriceCriteriaDetailEntity> priceCriteriaDetailEntities = priceCriteriaDetailDao
				.selectByValuationId(valuationId);
		for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : priceCriteriaDetailEntities) {
			// 最低一票
			priceCriteriaDetailEntity.setMinFee(pricePlanDetailDto
					.getMinimumOneTicket());
			// 按照重量
			if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
					.equals(priceCriteriaDetailEntity.getCaculateType())) {
				priceCriteriaDetailEntity.setFeeRate(
						NumberUtils.multiply(
								pricePlanDetailDto.getHeavyPrice(), PricingConstants.YUTOFEN));
				// 按照体积
			} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
					.equalsIgnoreCase(priceCriteriaDetailEntity
							.getCaculateType())) {
				priceCriteriaDetailEntity.setFeeRate(
						NumberUtils.multiply(
						        pricePlanDetailDto.getLightPrice(), PricingConstants.YUTOFEN));
			}
			// 备注信息
			priceCriteriaDetailEntity.setDescription(pricePlanDetailDto
					.getRemark());
			priceCriteriaDetailDao
					.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
		}
		//修改计价分段明细   219413-Luomengxiang    2014.11.7
		List<PopPriceDetailSectionEntity> popPriceDetailSectionEntities=
				popPriceDetailSectionDao.selectByValuationId(valuationId);
		//对历史数据的修改   219413-Luomengxiang   2015.1.19
		if(CollectionUtils.isEmpty(popPriceDetailSectionEntities)){
			//将历史的明细数据插入到分段表中   219413-Luomengxiang   2015.1.19
			//没有增加分段数的情况
			PopPriceDetailSectionEntity sectionEntity = null;
			//对修改后的结果集查询   219413-Luomengxiang  2014.11.10
			QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
			queryPricePlanDetailBean.setPricePlanId(valuationEntity
					.getPricePlanId());
			if(priceCriteriaDetailEntities.size() == 2){
				for(PriceCriteriaDetailEntity detailEntity : priceCriteriaDetailEntities){
					sectionEntity = new PopPriceDetailSectionEntity();
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(detailEntity.getCaculateType())
							||PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(detailEntity.getCaculateType())){
						//设置分段表轻货的主键
						sectionEntity.setId(UUIDUtils.getUUID());
						//设置轻货的明细id
						sectionEntity.setCriteriaDetailID(detailEntity.getId());
						//设置分段数
						sectionEntity.setSectionID(String.valueOf(ONE));
						//设置规则id
						sectionEntity.setValuationId(valuationId);
						//设置轻货价格
						sectionEntity.setFee(detailEntity.getFeeRate());
						//设置轻货类型
						sectionEntity.setCaculateType(detailEntity.getCaculateType());
					}
					popPriceDetailSectionDao.insertPriceDetailSection(sectionEntity);
				}
			}
			//再次查询分段表里的记录   219413-Luomengxiang   2015.1.20
			List<PopPriceDetailSectionEntity> popPriceDetailSectionEntitiesList = 
					popPriceDetailSectionDao.selectByValuationId(valuationId);
			if(popPriceDetailSectionEntitiesList.size() != 0){
				//获取修改的分段信息
				List<PricePlanDetailDto> pricePlanDetailDtos = 
						priceManageMentVo.getPricePlanDetailDtoList();
				//重货实体
				PopPriceDetailSectionEntity heavyEntity = null;
				//轻货实体
				PopPriceDetailSectionEntity lightEntity = null;
				//重货明细id
				String heavyCriticalId = null;
				//轻货明细id
				String lightCriticalId = null;
				for(PriceCriteriaDetailEntity criteriaEntity : priceCriteriaDetailEntities){
					if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(
							criteriaEntity.getCaculateType())){
						heavyCriticalId = criteriaEntity.getId();
					}else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(
							criteriaEntity.getCaculateType())){
						lightCriticalId = criteriaEntity.getId();
					}
				}
				for(PricePlanDetailDto dto : pricePlanDetailDtos){
					if(String.valueOf(TWO).equals(dto.getSectionID())){
						heavyEntity = new PopPriceDetailSectionEntity();
						lightEntity = new PopPriceDetailSectionEntity();
						//设置重货信息
						heavyEntity.setId(UUIDUtils.getUUID());
						heavyEntity.setSectionID(String.valueOf(TWO));
						heavyEntity.setCaculateType(
								PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
					    heavyEntity.setValuationId(valuationId);
					    heavyEntity.setCriteriaDetailID(heavyCriticalId);
					    heavyEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getHeavyPrice(),PricingConstants.YUTOFEN));
					    heavyEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
					    //设置轻货信息
					    lightEntity.setId(UUIDUtils.getUUID());
					    lightEntity.setSectionID(String.valueOf(TWO));
					    lightEntity.setCaculateType(
					    		PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
					    lightEntity.setValuationId(valuationId);
					    lightEntity.setCriteriaDetailID(lightCriticalId);
					    lightEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getLightPrice(),PricingConstants.YUTOFEN));
					    lightEntity.setCriticalValue(
					    		NumberUtils.multiply(
		  			    				dto.getLightCriticalVal(),PricingConstants.YUTOFEN));
					    popPriceDetailSectionDao.insertPriceDetailSection(heavyEntity);
					    popPriceDetailSectionDao.insertPriceDetailSection(lightEntity);
					}  
					if(String.valueOf(THREE).equals(dto.getSectionID())){
						heavyEntity = new PopPriceDetailSectionEntity();
						lightEntity = new PopPriceDetailSectionEntity();
						//设置重货信息
						heavyEntity.setId(UUIDUtils.getUUID());
						heavyEntity.setSectionID(String.valueOf(THREE));
						heavyEntity.setCaculateType(
								PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
					    heavyEntity.setValuationId(valuationId);
					    heavyEntity.setCriteriaDetailID(heavyCriticalId);
					    heavyEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getHeavyPrice(),PricingConstants.YUTOFEN));
					    heavyEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
					    //设置轻货信息
					    lightEntity.setId(UUIDUtils.getUUID());
					    lightEntity.setSectionID(String.valueOf(THREE));
					    lightEntity.setCaculateType(
					    		PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
					    lightEntity.setValuationId(valuationId);
					    lightEntity.setCriteriaDetailID(lightCriticalId);
					    lightEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getLightPrice(),PricingConstants.YUTOFEN));
					    lightEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getLightCriticalVal(),PricingConstants.YUTOFEN));
					    popPriceDetailSectionDao.insertPriceDetailSection(heavyEntity);
					    popPriceDetailSectionDao.insertPriceDetailSection(lightEntity);
					}
					if(String.valueOf(FOUR).equals(dto.getSectionID())){
						heavyEntity = new PopPriceDetailSectionEntity();
						lightEntity = new PopPriceDetailSectionEntity();
						//设置重货信息
						heavyEntity.setId(UUIDUtils.getUUID());
						heavyEntity.setSectionID(String.valueOf(FOUR));
						heavyEntity.setCaculateType(
								PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
					    heavyEntity.setValuationId(valuationId);
					    heavyEntity.setCriteriaDetailID(heavyCriticalId);
					    heavyEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getHeavyPrice(),PricingConstants.YUTOFEN));
					    heavyEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
					    //设置轻货信息
					    lightEntity.setId(UUIDUtils.getUUID());
					    lightEntity.setSectionID(String.valueOf(FOUR));
					    lightEntity.setCaculateType(
					    		PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
					    lightEntity.setValuationId(valuationId);
					    lightEntity.setCriteriaDetailID(lightCriticalId);
					    lightEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getLightPrice(),PricingConstants.YUTOFEN));
					    lightEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getLightCriticalVal(),PricingConstants.YUTOFEN));
					    popPriceDetailSectionDao.insertPriceDetailSection(heavyEntity);
					    popPriceDetailSectionDao.insertPriceDetailSection(lightEntity);
					}
					if(String.valueOf(FIVE).equals(dto.getSectionID())){
						heavyEntity = new PopPriceDetailSectionEntity();
						lightEntity = new PopPriceDetailSectionEntity();
						//设置重货信息
						heavyEntity.setId(UUIDUtils.getUUID());
						heavyEntity.setSectionID(String.valueOf(FIVE));
						heavyEntity.setCaculateType(
								PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
					    heavyEntity.setValuationId(valuationId);
					    heavyEntity.setCriteriaDetailID(heavyCriticalId);
					    heavyEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getHeavyPrice(),PricingConstants.YUTOFEN));
					    heavyEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
					    //设置轻货信息
					    lightEntity.setId(UUIDUtils.getUUID());
					    lightEntity.setSectionID(String.valueOf(FIVE));
					    lightEntity.setCaculateType(
					    		PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
					    lightEntity.setValuationId(valuationId);
					    lightEntity.setCriteriaDetailID(lightCriticalId);
					    lightEntity.setFee(
					    		NumberUtils.multiply(
			    				dto.getLightPrice(),PricingConstants.YUTOFEN));
					    lightEntity.setCriticalValue(
					    		NumberUtils.multiply(
			    				dto.getLightCriticalVal(),PricingConstants.YUTOFEN));
					    popPriceDetailSectionDao.insertPriceDetailSection(heavyEntity);
					    popPriceDetailSectionDao.insertPriceDetailSection(lightEntity);
					}
					if(String.valueOf(SIX).equals(dto.getSectionID())){
						heavyEntity = new PopPriceDetailSectionEntity();
						lightEntity = new PopPriceDetailSectionEntity();
						//设置重货信息
						heavyEntity.setId(UUIDUtils.getUUID());
						heavyEntity.setSectionID(String.valueOf(SIX));
						heavyEntity.setCaculateType(
								PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
					    heavyEntity.setValuationId(valuationId);
					    heavyEntity.setCriteriaDetailID(heavyCriticalId);
					    heavyEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getHeavyPrice(),PricingConstants.YUTOFEN));
					    heavyEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
					    //设置轻货信息
					    lightEntity.setId(UUIDUtils.getUUID());
					    lightEntity.setSectionID(String.valueOf(SIX));
					    lightEntity.setCaculateType(
					    		PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
					    lightEntity.setValuationId(valuationId);
					    lightEntity.setCriteriaDetailID(lightCriticalId);
					    lightEntity.setFee(
					    		NumberUtils.multiply(
					    				dto.getLightPrice(),PricingConstants.YUTOFEN));
					    lightEntity.setCriticalValue(
					    		NumberUtils.multiply(
					    				dto.getLightCriticalVal(),PricingConstants.YUTOFEN));
					    popPriceDetailSectionDao.insertPriceDetailSection(heavyEntity);
					    popPriceDetailSectionDao.insertPriceDetailSection(lightEntity);
					}
				}
			}
			resultPricePlanDetailDtoList=
					appendSections(this.boxHeaveAndLight(pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean)));
			return resultPricePlanDetailDtoList;
		}
	    //重货明细id  219413-Luomengxiang    2014.11.12
		String heavyId=null;
		//轻货明细id   219413-Luomengxiang    2014.11.12
		String lightId=null;
		//处理轻重货明细   219413-Luomengxiang    2014.11.12
		if(CollectionUtils.isNotEmpty(popPriceDetailSectionEntities)){
		for(PopPriceDetailSectionEntity entity : popPriceDetailSectionEntities){
			   if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entity.getCaculateType())){
				        heavyId=entity.getCriteriaDetailID();
			    }else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entity.getCaculateType())){
				        lightId=entity.getCriteriaDetailID();
			      } 
	         }
		}
		//获得修改前的分段数   219413-Luomengxiang   2014.11.10
		int sectionCountBefore=popPriceDetailSectionEntities.size()/2;
		 List<PricePlanDetailDto>  pricePlanDetailDtoList = priceManageMentVo.getPricePlanDetailDtoList();
		//获得修改后的分段数   219413-Luomengxiang   2014.11.10
		 int sectionCountAfter=pricePlanDetailDtoList.size();
		//对分段明细修改数据的操作  219413-Luomengxiang  2014.11.10
		if(sectionCountBefore==sectionCountAfter){
			 modifySectionDetail( pricePlanDetailDtoList);
		}else if(sectionCountBefore>sectionCountAfter){
			deleteSectionDetail(pricePlanDetailDtoList,sectionCountBefore);
		}else if(sectionCountBefore<sectionCountAfter){
			addSectionDetail(pricePlanDetailDtoList,sectionCountBefore,valuationId,heavyId,lightId);
		}
		//对修改后的结果集查询   219413-Luomengxiang  2014.11.10
		QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
		queryPricePlanDetailBean.setPricePlanId(valuationEntity
				.getPricePlanId());
		resultPricePlanDetailDtoList=
				appendSections(this.boxHeaveAndLight(pricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean)));
		return resultPricePlanDetailDtoList;
	}
	/**
	 * 修改新增的分段明细信息
	 * @author 219413-Pop-Team-Luomengxiang  
	 * @param popPriceDetailSectionEntities
	 * @param priceManageMentVo 
	 * @return popPriceDetailSectionEntities
	 * @category 2014.11.10
	 */
	private void addSectionDetail(
			List<PricePlanDetailDto> pricePlanDetailDtoList,
			int sectionCountBefore,String valuationId,
			String heavyId,String lightId) {
		    int sectionCountAfter=pricePlanDetailDtoList.size();
		  List<PopPriceDetailSectionEntity> popPriceDetailSectionEntities = new ArrayList<PopPriceDetailSectionEntity>(); 
		  //重货分段实体
		  PopPriceDetailSectionEntity heavySectionEntity=null;
		  //轻货分段实体
		  PopPriceDetailSectionEntity lightSectionEntity=null;
		  for(;sectionCountBefore<sectionCountAfter;sectionCountBefore++){
			    //重货分段明细
			    heavySectionEntity=new PopPriceDetailSectionEntity();
			    heavySectionEntity.setId(UUIDUtils.getUUID());
				heavySectionEntity.setSectionID(pricePlanDetailDtoList.get(sectionCountBefore).getSectionID());
				heavySectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
				heavySectionEntity.setFee(NumberUtils.multiply(pricePlanDetailDtoList.get(sectionCountBefore).getHeavyPrice(),PricingConstants.YUTOFEN));
				heavySectionEntity.setCriticalValue(NumberUtils.multiply(pricePlanDetailDtoList.get(sectionCountBefore).getHeavyCriticalVal(),PricingConstants.YUTOFEN));
				heavySectionEntity.setCriteriaDetailID(heavyId);
				heavySectionEntity.setDescription(pricePlanDetailDtoList.get(sectionCountBefore).getRemark());
				heavySectionEntity.setValuationId(valuationId);
				popPriceDetailSectionEntities.add(heavySectionEntity);
				//轻货分段明细
				lightSectionEntity=new PopPriceDetailSectionEntity();
				lightSectionEntity.setId(UUIDUtils.getUUID());
				lightSectionEntity.setSectionID(pricePlanDetailDtoList.get(sectionCountBefore).getSectionID());
				lightSectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
				lightSectionEntity.setFee(NumberUtils.multiply(pricePlanDetailDtoList.get(sectionCountBefore).getLightPrice(),PricingConstants.YUTOFEN));
				lightSectionEntity.setCriticalValue(NumberUtils.multiply(pricePlanDetailDtoList.get(sectionCountBefore).getLightCriticalVal(),PricingConstants.YUTOFEN));
				lightSectionEntity.setCriteriaDetailID(lightId);
				lightSectionEntity.setDescription(pricePlanDetailDtoList.get(sectionCountBefore).getRemark());
				lightSectionEntity.setValuationId(valuationId);
				popPriceDetailSectionEntities.add(lightSectionEntity);
		  }
		  if(CollectionUtils.isNotEmpty(popPriceDetailSectionEntities)){
			  for(PopPriceDetailSectionEntity entity : popPriceDetailSectionEntities){
				  if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(entity.getCaculateType())){
					  entity.setCriteriaDetailID(heavyId);
				  }else if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME.equals(entity.getCaculateType())){
					  entity.setCriteriaDetailID(lightId);
				  }
				  popPriceDetailSectionDao.insertPriceDetailSection(entity);
			  }
		  }
//		  for(int i=sectionCountAfter;i>sectionCountBefore;i--){
//			  pricePlanDetailDtoList.remove(i-1);
//		  }
		  modifySectionDetail(pricePlanDetailDtoList);
	}
	/**
	 * 修改删除的分段明细信息
	 * @author 219413-Pop-Team-Luomengxiang  
	 * @param popPriceDetailSectionEntities
	 * @return popPriceDetailSectionEntities
	 * @category 2014.11.10
	 */
	private void deleteSectionDetail(
			List<PricePlanDetailDto>  pricePlanDetailDtoList,
			int sectionCountBefore) {
		  int sectionCountAfter=pricePlanDetailDtoList.size();
		  for(;sectionCountAfter<sectionCountBefore;sectionCountBefore--){
			  popPriceDetailSectionDao.deleteBySectionId(String.valueOf(sectionCountBefore));
		  }
//		  for(int i=sectionCountBefore;i>sectionCountAfter;i--){
//			  pricePlanDetailDtoList.remove(i-1);
//		  }
		  modifySectionDetail(pricePlanDetailDtoList);
	}

	/**
	 * 修改分段明细信息
	 * @author 219413-Pop-Team-Luomengxiang  
	 * @param popPriceDetailSectionEntities
	 * @return popPriceDetailSectionEntities
	 * @category 2014.11.10
	 */
	private void modifySectionDetail(List<PricePlanDetailDto>  pricePlanDetailDtoList) {
		 //重货分段实体
		 PopPriceDetailSectionEntity heavySectionEntity=null;
		  //轻货分段实体
		 PopPriceDetailSectionEntity lightSectionEntity=null;
		 String valuationId=pricePlanDetailDtoList.get(0).getValuationId();
		for(PricePlanDetailDto dto : pricePlanDetailDtoList){
				heavySectionEntity = new PopPriceDetailSectionEntity();
				lightSectionEntity = new PopPriceDetailSectionEntity();
				heavySectionEntity.setSectionID(dto.getSectionID());
				lightSectionEntity.setSectionID(dto.getSectionID());
				heavySectionEntity.setValuationId(valuationId);
				lightSectionEntity.setValuationId(valuationId);
				//修改第一段的备注    219413-Luomengxiang  2014.11.15
				heavySectionEntity.setDescription(dto.getRemark());
				//修改第一段的备注    219413-Luomengxiang  2014.11.15
				lightSectionEntity.setDescription(dto.getRemark());
				heavySectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
				lightSectionEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
				heavySectionEntity.setFee(NumberUtils.multiply(dto.getHeavyPrice(),PricingConstants.YUTOFEN));
				lightSectionEntity.setFee(NumberUtils.multiply(dto.getLightPrice(),PricingConstants.YUTOFEN));
				heavySectionEntity.setCriticalValue(NumberUtils.multiply(dto.getHeavyCriticalVal(),PricingConstants.YUTOFEN));
				lightSectionEntity.setCriticalValue(NumberUtils.multiply(dto.getLightCriticalVal(),PricingConstants.YUTOFEN));
				popPriceDetailSectionDao.updateByValuationId(heavySectionEntity);
				popPriceDetailSectionDao.updateByValuationId(lightSectionEntity);
		}
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
	public void stopPricePlan(PricePlanEntity voPricePlanEntity, List<PricePlanEntity> pricePlanEntityList, List<PriceValuationEntity> priceValuationEntityList)
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
		List<PriceValuationEntity> priceValuationEntitys = priceValuationDao
				.selectByCodition(priceValuationEntity);
		if (CollectionUtils.isNotEmpty(priceValuationEntitys)) {
			for (PriceValuationEntity tempPriceValuationEntity : priceValuationEntitys) {
				tempPriceValuationEntity.setEndTime(stopTime);
				tempPriceValuationEntity.setVersionNo(new Date().getTime());
				priceValuationEntityList.add(tempPriceValuationEntity);
//				priceValuationDao.updateValuation(tempPriceValuationEntity);
			}
		}
		// 修改价格方案截止日期
		pricePlanEntity.setEndTime(stopTime);
		pricePlanEntity.setVersionNo(new Date().getTime());
		pricePlanEntityList.add(pricePlanEntity);
//		pricePlanDao.updateByPrimaryKeySelective(pricePlanEntity);
	}

	/**
	 *获得第一段分段明細的dto
	 * 
	 * @author POP-Team-LuoMengxiang
	 * @category 2014/10/27
	 * @param pricePlanDetailDtoList
	 * @return
	 */
	private PricePlanDetailDto getFirstPricePlanDetailDto(
			List<PricePlanDetailDto> pricePlanDetailDtoList) {
		if (CollectionUtils.isNotEmpty(pricePlanDetailDtoList)) {
			for (PricePlanDetailDto pricePlanDetailDto : pricePlanDetailDtoList) {
				if (String.valueOf(ONE).equals(pricePlanDetailDto.getSectionID())) {
					return pricePlanDetailDto;
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * <p>
	 * (新增价格方案明细)
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-14 下午7:17:01
	 * 
	 * @param dto
	 * 
	 * @return
	 * 
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	/**
	 * 由于计价分段，故将原有的改为pricePlanDetailDtoList
	 * 
	 * @author POP-Team-LuoMengXiang
	 * 
	 * @date 2014/10/27
	 */
	public List<PricePlanDetailDto> addPricePlanDetail(
			List<PricePlanDetailDto> pricePlanDetailDtoList) {
		List<PricePlanDetailDto> resultpricePlanDetails = new ArrayList<PricePlanDetailDto>();
		if (null != pricePlanDetailDtoList) {
			PricePlanDetailDto pricePlanDetailDto = getFirstPricePlanDetailDto(pricePlanDetailDtoList);
			Date currentDate = new Date();
			// 方案id
			String pricePlanId = pricePlanDetailDto.getPricePlanId();
			// 目的地区域
			String arrvRegionId = pricePlanDetailDto.getArrvRegionId();
			// 是否接货
			String centralizePickUp = pricePlanDetailDto.getCentralizePickup();
			// 产品条目
			String productItemCode = pricePlanDetailDto.getProductItemCode();
			ProductItemEntity productItemEntity = productItemDao
					.findProductItemByCode(productItemCode, new Date());
			String productCode = productItemEntity.getProductCode();
			String goodsCode = productItemEntity.getGoodstypeCode();
			List<PricePlanDetailDto> pricePlanDetailDtos = new ArrayList<PricePlanDetailDto>();
			pricePlanDetailDtos.addAll(pricePlanDetailDtoList);
			Map<String, Object> priceCriteriaDetailMap = analysisPricePlanData(
					pricePlanDetailDtos, pricePlanId);
			List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap
					.get(PricePlanService.VALUATION_RULE);
			List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap
					.get(PricePlanService.PRICING_CRITERIA);
			/**
			 * 计费明细分段
			 * @author POP-Team-LuoMengXiang
			 * @date 2014/10/24
			 */
			List<PopPriceDetailSectionEntity> sections = (List<PopPriceDetailSectionEntity>) priceCriteriaDetailMap
					.get(PricePlanService.PRICING_DETAIL_SECTION);
			// 处理计费规则
			if (CollectionUtils.isNotEmpty(valuations)) {
				QueryExistPricePlanDetailBean queryBean = new QueryExistPricePlanDetailBean();
				queryBean.setPricePlanId(pricePlanId);
				queryBean.setArrvRegionId(arrvRegionId);
				queryBean.setIsCentralizePickup(centralizePickUp);
				queryBean.setProductCode(productCode);
				queryBean.setGoodsTypeCode(goodsCode);
				 List<ResultPricePlanDetailBean> list =pricePlanDao.isExistRpeatPricePlanDetailForEdit(queryBean);
				if (CollectionUtils.isNotEmpty(list)) {
					String centralizeName = "";
					if (StringUtil.isNotBlank(centralizePickUp)) {
						if (StringUtil.equals(centralizePickUp,
								FossConstants.ACTIVE)) {
							centralizeName = "是";
						} else {
							centralizeName = "否";
						}
					}
					GoodsTypeEntity goodsTypeEntity = goodsTypeService
							.queryGoodsTypeByGoodTypeCode(goodsCode,
									currentDate);
					ProductEntity producTypeEntity = productService
							.getProductByCache(productCode, currentDate);
					PriceRegionArriveEntity priceRegionEntity = regionArriveService
							.searchRegionByID(
									pricePlanDetailDto.getArrvRegionId(),
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
					priceValuationDao.insertSelective(priceValuationEntity);
				}
			}
			/**
			 * 计费明细分段表引用的明细表id
			 * 
			 * @author POP-Team-LuoMengxiang
			 * @date 2014/10/24
			 */
			String lightId = null;
			String heavyId = null;
			//处理计价明细
			if (CollectionUtils.isNotEmpty(details)) {
				for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
					if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
							.equals(priceCriteriaDetailEntitys
									.getCaculateType())) {
						heavyId = priceCriteriaDetailEntitys.getId();
					} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
							.equals(priceCriteriaDetailEntitys
									.getCaculateType())) {
						lightId = priceCriteriaDetailEntitys.getId();
					}
					priceCriteriaDetailDao
							.insertSelective(priceCriteriaDetailEntitys);
					priceCriteriaDetailEntitys.getActive();
				}
			}
			// 处理计费明细分段
			if (CollectionUtils.isNotEmpty(sections)) {
				for (PopPriceDetailSectionEntity sectionEntity : sections) {
					if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
							.equals(sectionEntity.getCaculateType())) {
						sectionEntity.setCriteriaDetailID(heavyId);
					} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
							.equals(sectionEntity.getCaculateType())) {
						sectionEntity.setCriteriaDetailID(lightId);
					}
					popPriceDetailSectionDao
							.insertPriceDetailSection(sectionEntity);
				}
			}
			QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
			queryPricePlanDetailBean.setPricePlanId(pricePlanId);
			List<ResultPricePlanDetailBean> resultPricePlanDetails = pricePlanDao
					.queryPricePlanDetailInfo(queryPricePlanDetailBean);
			/**
			 * 加入查询分段明细
			 * @author Pop-Team-LuoMengxiang
			 * @date 2014.11.3
			 */
			resultpricePlanDetails=appendSections(this.boxHeaveAndLight(resultPricePlanDetails));
		}else {
			throw new PricePlanException(
					PricePlanException.PRICE_PLAN_ADD_DETAILISNULL_ERROR_CODE,
					null);
		}
		return resultpricePlanDetails;
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
			pricePlanDao.updateByPrimaryKeySelective(dbEntity);
		}
	}

	/**
	 * 
	 * <p>
	 * (删除价格明细-草稿状态 删除步骤,按照顺序删除计费明细,计费规则)
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-12-12 上午11:36:03
	 * 
	 * @param valuationIds
	 * 
	 * @return
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public List<PricePlanDetailDto> deletePriceDetailPlan(
			List<String> valuationIds) {
		List<PricePlanDetailDto> pricePlanDetailDtos = new ArrayList<PricePlanDetailDto>();
		if (CollectionUtils.isNotEmpty(valuationIds)) {
			// 删除前，获得计价方案ID
			PriceValuationEntity pricevaluationEntity = priceValuationDao
					.selectByPrimaryKey(valuationIds.get(0));
			QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();
			queryPricePlanDetailBean.setPricePlanId(pricevaluationEntity
					.getPricePlanId());
			for (int i = 0; i < valuationIds.size(); i++) {
				String valuationId = valuationIds.get(i);
				/**
				 * 根据规则id删除计价方式分段明细的记录
				 * @author Pop-Team-LuoMengxiang
				 * @date 2014.11.1
				 */
				popPriceDetailSectionDao.deleteByValuationId(valuationId);
				
				priceCriteriaDetailDao
						.deleteCriteriaDetailByValuationId(valuationId);
				priceValuationDao.deleteByPrimaryKey(valuationId);
			}
			pricePlanDetailDtos = appendSections(this.boxHeaveAndLight(pricePlanDao
					.queryPricePlanDetailInfo(queryPricePlanDetailBean)));
		}
		return pricePlanDetailDtos;
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
	public void addPricePlanBatch(Map<String, List<PricePlanDetailDto>> detailMap,
			Map<String, PriceRegionEntity> priceRegionEntityMap
			,Map<String, PriceRegionArriveEntity> priceArrvRegionEntityMap
			, Map<String, ProductEntity> productEntityMap
			,Map<String, PricePlanDetailDto> detailSectionMap) {
		// 检查数据
		if (detailMap == null || detailMap.size() < 1 || priceRegionEntityMap == null
				|| priceRegionEntityMap.size() < 1 || productEntityMap == null || productEntityMap.size() < 1) {
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
		// 查询货物类型，固定的常量
		GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(
				GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001, new Date());
		Set<Entry<String, List<PricePlanDetailDto>>> detailSet = detailMap.entrySet();
		
		
		List<PricePlanEntity> pricePlanBatch = new ArrayList<PricePlanEntity>();//方案
		List<PriceValuationEntity> priceValuationBatch = new ArrayList<PriceValuationEntity>();//价格规则
		List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch = new ArrayList<PriceCriteriaDetailEntity>();//价格明细
		List<PopPriceDetailSectionEntity> popPriceDetailSectionBatch = new ArrayList<PopPriceDetailSectionEntity>();//分段价格

		
		for (Entry<String, List<PricePlanDetailDto>> entry : detailSet) {
			String regionName = entry.getKey();
			List<PricePlanDetailDto> detailList = entry.getValue();
			// 添加价格主方案信息
			PriceRegionEntity deptRegion = priceRegionEntityMap.get(regionName);
			if (deptRegion == null) {
				continue;
			}
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
			
//			pricePlanDao.insert(pricePlanEntity);
			pricePlanBatch.add(pricePlanEntity);//批量插入，减少锁表问题
			
			
			String pricePlanId = pricePlanEntity.getId();
			// 价格方案明细插入
			for (int loop = 0; loop < detailList.size(); loop++) {
				PricePlanDetailDto pricePlanDetailDto = detailList.get(loop);
				if("1".equals(pricePlanDetailDto.getSectionID())){
					pricePlanDetailDto.setPricePlanId(pricePlanId);
					// 设置状态
					pricePlanDetailDto.setActive(FossConstants.INACTIVE);
					PriceRegionArriveEntity arriveRegionEntity = priceArrvRegionEntityMap.get(pricePlanDetailDto.getArrvRegionName());
					if (arriveRegionEntity == null) {
						log.error("到达区域ID不存在");
						throw new BusinessException("到达区域ID不存在");
					}
					// 设置到达区域ID
					pricePlanDetailDto.setArrvRegionId(arriveRegionEntity.getId());
					ProductEntity procuctEntity = productEntityMap.get(pricePlanDetailDto.getProductItemName());
					addPriceBatchPlanDetail(pricePlanDetailDto, deptRegion, procuctEntity, priceEntry, goodsTypeEntity,detailSectionMap, priceValuationBatch, priceCriteriaDetailEntityBatch, popPriceDetailSectionBatch);
				}
			}
		}
		
		pricePlanDao.insertPricePlanAllBatch(pricePlanBatch, priceValuationBatch, priceCriteriaDetailEntityBatch, popPriceDetailSectionBatch);
		
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
	private void addPriceBatchPlanDetail(PricePlanDetailDto pricePlanDetailDto, PriceRegionEntity deptRegion, ProductEntity procuctEntity,
			PriceEntity priceEntry, GoodsTypeEntity goodsTypeEntity,Map<String, PricePlanDetailDto> detailSectionMap, 
			List<PriceValuationEntity> priceValuationBatch, List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch, List<PopPriceDetailSectionEntity> popPriceDetailSectionBatch) {
		// 处理计费规则与计价明细(重货轻货)
		Map<String, Object> priceCriteriaDetailMap = analysisBatchPricePlanData(pricePlanDetailDto, deptRegion, procuctEntity, priceEntry, goodsTypeEntity);
		@SuppressWarnings("unchecked")
		// 取出计费规则数据
		List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap.get(PricePlanService.VALUATION_RULE);
		@SuppressWarnings("unchecked")
		// 取出价格详细
		List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap.get(PricePlanService.PRICING_CRITERIA);

		// 处理计费规则数据
		if(valuations != null && valuations.size() > 0){
			priceValuationBatch.addAll(valuations);//批量导入价格规则
		}
		
//		for (PriceValuationEntity priceValuationEntity : valuations) {
//			priceValuationDao.insertSelective(priceValuationEntity);
//		}
		
		
		// 批量处理计价明细
		if (CollectionUtils.isNotEmpty(details)) {
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
				
//				priceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntitys);
				priceCriteriaDetailEntityBatch.add(priceCriteriaDetailEntitys);//批量导入价格明细
				
				insertDetailSection(pricePlanDetailDto,priceCriteriaDetailEntitys,detailSectionMap,popPriceDetailSectionBatch);//批量插入价格段
			}
		}
		
	}
	/**
	 * 插入明细分段
	 */
	private void insertDetailSection(PricePlanDetailDto pricePlanDetailDto,PriceCriteriaDetailEntity priceCriteriaDetailEntity,Map<String, PricePlanDetailDto> detailSectionMap,List<PopPriceDetailSectionEntity> popPriceDetailSectionBatch){
//		List<PopPriceDetailSectionEntity> list = new ArrayList<PopPriceDetailSectionEntity>();
		for(int i=1;i<=NumberConstants.NUMBER_6;i++){
			//key 为出发城市name+价格段+产品类型名字+到达城市name
			String key = pricePlanDetailDto.getDeptRegionName()+("Y".equals(pricePlanDetailDto.getCentralizePickup()) ? "是":"否") + i
					+pricePlanDetailDto.getProductItemName()
					+pricePlanDetailDto.getArrvRegionName();
			PricePlanDetailDto dto = detailSectionMap.get(key);
			if(detailSectionMap.get(key)!=null){
				PopPriceDetailSectionEntity detailSectionEntity = new PopPriceDetailSectionEntity();
				//计费类型
				detailSectionEntity.setCaculateType(priceCriteriaDetailEntity.getCaculateType());
				//设置规则明细ID
				detailSectionEntity.setCriteriaDetailID(priceCriteriaDetailEntity.getId());
				//如果为重货
				if(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT.equals(priceCriteriaDetailEntity.getCaculateType())){
					//设置临界值
					detailSectionEntity.setCriticalValue(dto.getHeavyCriticalVal());
					//设置价格
					detailSectionEntity.setFee(dto.getHeavyPrice());
				}else{
					//设置临界值
					detailSectionEntity.setCriticalValue(dto.getLightCriticalVal());
					//设置价格
					detailSectionEntity.setFee(dto.getLightPrice());
				}
				detailSectionEntity.setId(UUIDUtils.getUUID());
				//设置分段
				detailSectionEntity.setSectionID(""+i);
				//设置计费规则ID
				detailSectionEntity.setValuationId(priceCriteriaDetailEntity.getPricingValuationId());
				popPriceDetailSectionBatch.add(detailSectionEntity);
			}
		}
//		if(CollectionUtils.isNotEmpty(list)){
//			priceCriteriaDetailDao.insertDetailSectionList(list);
//		}
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
			PricePlanDetailDto detail, PriceRegionEntity deptRegion,
			ProductEntity procuctEntity, PriceEntity priceEntry,
			GoodsTypeEntity goodsTypeEntity) {
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
		// 设置产品code
		valuationEntity.setProductCode(procuctEntity.getCode());
		// 设置货物类型code
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
		PriceCriteriaDetailEntity heavyEntity = new PriceCriteriaDetailEntity();
		PriceCriteriaDetailEntity lightEntity = new PriceCriteriaDetailEntity();
		// 关联计费规则ID
		heavyEntity.setPricingValuationId(valuationEntity.getId());
		// 设置明细备注
		heavyEntity.setDescription(detail.getRemark());
		// 计费类型
		// 按重量
		heavyEntity
				.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		// 重货价格 换算成分
		heavyEntity.setFeeRate(NumberUtils.multiply(detail.getHeavyPrice(),
				PricingConstants.YUTOFEN));
		// 最低一票
		heavyEntity.setMinFee(detail.getMinimumOneTicket());
		heavyEntity.setVersionNo(currentTime.getTime());
		// 为空，原因该属性关联关系的实体已经不存在。后续删除该字段
		heavyEntity.setPricingCriteriaId("");
		// 计费规则
		heavyEntity
				.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
		heavyEntity.setId(UUIDUtils.getUUID());
		// 左区间
		heavyEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
		// 右区间
		heavyEntity.setRightrange(BigDecimal
				.valueOf(PricingConstants.CRITERIA_DETAIL_WEIGHT_MAX));
		// 始发区域ID
		heavyEntity.setDeptRegionId(deptRegion.getId());
		heavyEntity.setActive(detail.getActive());
		heavyList.add(heavyEntity);
		// 轻货数据
		// 关联计费规则ID
		lightEntity.setPricingValuationId(valuationEntity.getId());
		// 设置明细备注
		lightEntity.setDescription(detail.getRemark());
		// 计费类型 按体积
		lightEntity
				.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
		lightEntity.setVersionNo(currentTime.getTime());
		lightEntity.setActive(detail.getActive());
		// 轻货价格 换算造成分
		lightEntity.setFeeRate(NumberUtils.multiply(detail.getLightPrice(),
				PricingConstants.YUTOFEN));
		// 最低一票
		lightEntity.setMinFee(detail.getMinimumOneTicket());
		lightEntity.setPricingCriteriaId("");
		lightEntity
				.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
		lightEntity.setLeftrange(BigDecimal.valueOf(PricingConstants.ZERO));
		lightEntity.setRightrange(BigDecimal
				.valueOf(PricingConstants.CRITERIA_DETAIL_VOLUME_MAX));
		lightEntity.setId(UUIDUtils.getUUID());
		// 始发区域ID
		lightEntity.setDeptRegionId(deptRegion.getId());
		lightEntity.setActive(detail.getActive());
		// 组装数据
		lightList.add(lightEntity);
		heavyList.addAll(lightList);
		priceCriteriaDetailMap.put(PricePlanService.VALUATION_RULE,
				valuationEntitys);
		priceCriteriaDetailMap
				.put(PricePlanService.PRICING_CRITERIA, heavyList);
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
		PriceRegionEntity priceRegionEntity = regionService.searchRegionByID(
				pricePlanEntity.getPriceRegionId(),
				PricingConstants.PRICING_REGION);
		pricePlanEntity.setPriceRegionName(priceRegionEntity.getRegionName());
		return pricePlanEntity;
	}

	/**
	 * 
	 * <p>
	 * 价格方案明细导出
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
	public ExportResource exportPricePlanDetail(
			QueryPricePlanDetailBean queryPricePlanDetailBean) {
		ExportResource exportResource = new ExportResource();
		//[POP] modify foss-148246-sunjianbo 2014-11-13 start
		//原代码 start
		/*List<ResultPricePlanDetailBean> resultPricePlanDetailBeans = pricePlanDao
				.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0,
						Integer.MAX_VALUE);
		List<PricePlanDetailDto> pricePlanDetailDtos = this
				.boxHeaveAndLight(resultPricePlanDetailBeans);
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(pricePlanDetailDtos)) {
			for (PricePlanDetailDto pricePlanDetail : pricePlanDetailDtos) {*/
		//原代码 end
		List<PricePlanDetailDto> resultPricePlanDetailDtoList = this.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0,
						Integer.MAX_VALUE);
		List<List<String>> rowList = new ArrayList<List<String>>();
		if (CollectionUtils.isNotEmpty(resultPricePlanDetailDtoList)) {
			for (PricePlanDetailDto pricePlanDetail : resultPricePlanDetailDtoList) {
		//[POP] modify foss-148246-sunjianbo 2014-11-13 end
				List<String> row = exportPlatform(pricePlanDetail);
				rowList.add(row);
			}
		}
		exportResource.setHeads(PricingColumnConstants.PRICE_PLAN_DETAIL_TITLE);
		exportResource.setRowList(rowList);
		return exportResource;
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.
	 *            PricePlanService.queryPricePlanCompareResult
	 * @Description:查询价格方案比对结果集
	 * 
	 * @return
	 * 
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-9 上午11:14:13
	 * 
	 *                Modification History: Date Author Version Description
	 *                ------
	 *                ------------------------------------------------------
	 *                ----- 2014-5-9 130376-YANGKANG v1.0.0 create
	 */
	@Override
	public List<PricePlanDetailDto> queryPricePlanCompareResult(
			PricePlanEntity pricePlanEntityOld, PricePlanEntity pricePlanEntity) {
		QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();

		queryPricePlanDetailBean.setPricePlanId(pricePlanEntityOld.getId());
		// 查询原价格方案明细信息
		List<PricePlanDetailDto> pricePlanDetailDtoListOld = this
				.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0,
						Integer.MAX_VALUE);

		queryPricePlanDetailBean.setPricePlanId(pricePlanEntity.getId());
		// 查询需要比对的价格方案明细
		List<PricePlanDetailDto> pricePlanDetailDtoList = this
				.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0,
						Integer.MAX_VALUE);
		// 价格方案比对结果集
		List<PricePlanDetailDto> pricePlanCompareResultList = new ArrayList<PricePlanDetailDto>();

		for (int i = 0; i < pricePlanDetailDtoListOld.size(); i++) {

			for (int j = 0; j < pricePlanDetailDtoList.size(); j++) {
				// 如果比对的价格方案中目的站、产品条目及是否接货与原价格方案相同，则进行价格比对
				if (pricePlanDetailDtoList
						.get(j)
						.getArrvRegionId()
						.equals(pricePlanDetailDtoListOld.get(i)
								.getArrvRegionId())
						&& pricePlanDetailDtoList
								.get(j)
								.getProductItemId()
								.equals(pricePlanDetailDtoListOld.get(i)
										.getProductItemId())
						&& pricePlanDetailDtoList
								.get(j)
								.getCentralizePickup()
								.equals(pricePlanDetailDtoListOld.get(i)
										.getCentralizePickup())) {

					PricePlanDetailDto pricePlanCompareResult = new PricePlanDetailDto();

					pricePlanCompareResult
							.setHeavyPrice(pricePlanDetailDtoListOld
									.get(i)
									.getHeavyPrice()
									.subtract(
											pricePlanDetailDtoList.get(j)
													.getHeavyPrice()));
					pricePlanCompareResult
							.setLightPrice(pricePlanDetailDtoListOld
									.get(i)
									.getLightPrice()
									.subtract(
											pricePlanDetailDtoList.get(j)
													.getLightPrice()));
					pricePlanCompareResult
							.setMinimumOneTicket(pricePlanDetailDtoListOld.get(
									i).getMinimumOneTicket()
									- pricePlanDetailDtoList.get(j)
											.getMinimumOneTicket());
					pricePlanCompareResult
							.setArrvRegionId(pricePlanDetailDtoListOld.get(i)
									.getArrvRegionId());
					pricePlanCompareResult
							.setArrvRegionName(pricePlanDetailDtoListOld.get(i)
									.getArrvRegionName());
					pricePlanCompareResult
							.setProductItemId(pricePlanDetailDtoListOld.get(i)
									.getProductItemId());
					pricePlanCompareResult
							.setProductItemName(pricePlanDetailDtoListOld
									.get(i).getProductItemName());
					pricePlanCompareResult
							.setCentralizePickup(pricePlanDetailDtoListOld.get(
									i).getCentralizePickup());

					// 由于每条明细信息是可以唯一确定的 比较完之后可以移除已经比较过得明细信息 提高比较的效率
					pricePlanDetailDtoListOld.remove(i);
					pricePlanDetailDtoList.remove(j);
					i = i - 1;
					pricePlanCompareResultList.add(pricePlanCompareResult);
					break;
				}

			}

		}
		return pricePlanCompareResultList;
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.
	 *            PricePlanService.exportPricePlanCompareResult
	 * @Description:价格方案比对结果导出
	 * 
	 * @param pricePlanDetailDtos
	 * @return
	 * 
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-9 上午10:29:40
	 * 
	 *                Modification History: Date Author Version Description
	 *                ------
	 *                ------------------------------------------------------
	 *                ----- 2014-5-9 130376-YANGKANG v1.0.0 create
	 */
	@Override
	public ExportResource exportPricePlanCompareResult(
			PricePlanEntity pricePlanEntityOld, PricePlanEntity pricePlanEntity) {

		ExportResource exportResource = new ExportResource();

		List<List<String>> rowList = new ArrayList<List<String>>();

		QueryPricePlanDetailBean queryPricePlanDetailBean = new QueryPricePlanDetailBean();

		queryPricePlanDetailBean.setPricePlanId(pricePlanEntityOld.getId());
		// 查询原价格方案明细信息
		List<PricePlanDetailDto> pricePlanDetailDtoListOld = this
				.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0,
						Integer.MAX_VALUE);

		queryPricePlanDetailBean.setPricePlanId(pricePlanEntity.getId());
		// 查询需要比对的价格方案明细
		List<PricePlanDetailDto> pricePlanDetailDtoList = this
				.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0,
						Integer.MAX_VALUE);
		// 价格方案比对结果集
		List<PricePlanDetailDto> pricePlanCompareResultList = new ArrayList<PricePlanDetailDto>();

		for (int i = 0; i < pricePlanDetailDtoListOld.size(); i++) {

			for (int j = 0; j < pricePlanDetailDtoList.size(); j++) {
				// 如果比对的价格方案中目的站、产品条目及是否接货与原价格方案相同，则进行价格比对
				if (pricePlanDetailDtoList
						.get(j)
						.getArrvRegionId()
						.equals(pricePlanDetailDtoListOld.get(i)
								.getArrvRegionId())
						&& pricePlanDetailDtoList
								.get(j)
								.getProductItemId()
								.equals(pricePlanDetailDtoListOld.get(i)
										.getProductItemId())
						&& pricePlanDetailDtoList
								.get(j)
								.getCentralizePickup()
								.equals(pricePlanDetailDtoListOld.get(i)
										.getCentralizePickup())) {

					PricePlanDetailDto pricePlanCompareResult = new PricePlanDetailDto();

					pricePlanCompareResult
							.setHeavyPrice(pricePlanDetailDtoListOld
									.get(i)
									.getHeavyPrice()
									.subtract(
											pricePlanDetailDtoList.get(j)
													.getHeavyPrice()));
					pricePlanCompareResult
							.setLightPrice(pricePlanDetailDtoListOld
									.get(i)
									.getLightPrice()
									.subtract(
											pricePlanDetailDtoList.get(j)
													.getLightPrice()));
					pricePlanCompareResult
							.setMinimumOneTicket(pricePlanDetailDtoListOld.get(
									i).getMinimumOneTicket()
									- pricePlanDetailDtoList.get(j)
											.getMinimumOneTicket());
					pricePlanCompareResult
							.setArrvRegionId(pricePlanDetailDtoListOld.get(i)
									.getArrvRegionId());
					pricePlanCompareResult
							.setArrvRegionName(pricePlanDetailDtoListOld.get(i)
									.getArrvRegionName());
					pricePlanCompareResult
							.setProductItemId(pricePlanDetailDtoListOld.get(i)
									.getProductItemId());
					pricePlanCompareResult
							.setProductItemName(pricePlanDetailDtoListOld
									.get(i).getProductItemName());
					pricePlanCompareResult
							.setCentralizePickup(pricePlanDetailDtoListOld.get(
									i).getCentralizePickup());

					// 由于每条明细信息是可以唯一确定的 比较完之后可以移除已经比较过得明细信息 提高比较的效率
					pricePlanDetailDtoListOld.remove(i);
					pricePlanDetailDtoList.remove(j);
					i = i - 1;
					// 将比对结果明细添加到导出的数据中
					pricePlanCompareResultList.add(pricePlanCompareResult);
					List<String> row = exportComparePlatform(pricePlanCompareResult);
					rowList.add(row);
					break;
				}

			}

		}

		exportResource
				.setHeads(PricingColumnConstants.EXPORT_PRICE_PLAN_COMPARE_RESULT_TITLE);
		exportResource.setRowList(rowList);
		return exportResource;
	}

	/**
	 * 
	 * <p>
	 * 填充方案明细 sheet row
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
	private List<String> exportPlatform(PricePlanDetailDto pricePlanDetailDto) {
		List<String> result = new ArrayList<String>();
		result.add(pricePlanDetailDto.getArrvRegionName());
		result.add(pricePlanDetailDto.getProductItemName());
		result.add(pricePlanDetailDto.getMinimumOneTicket().toString());
		result.add(pricePlanDetailDto.getHeavyPrice().toString());
		result.add(pricePlanDetailDto.getLightPrice().toString());
		//[POP] add 148246-sunjianbo 2014-11-13 start
		result.add((null == pricePlanDetailDto.getHeavyCriticalVal2() ? "" :  pricePlanDetailDto.getHeavyCriticalVal2()).toString());
		result.add((null == pricePlanDetailDto.getLightCriticalVal2() ? "" :  pricePlanDetailDto.getLightCriticalVal2()).toString());
		result.add((null == pricePlanDetailDto.getHeavyPrice2() ? "" :  pricePlanDetailDto.getHeavyPrice2()).toString());
		result.add((null == pricePlanDetailDto.getLightPrice2() ? "" :  pricePlanDetailDto.getLightPrice2()).toString());
		result.add((null == pricePlanDetailDto.getHeavyCriticalVal3() ? "" :  pricePlanDetailDto.getHeavyCriticalVal3()).toString());
		result.add((null == pricePlanDetailDto.getLightCriticalVal3() ? "" :  pricePlanDetailDto.getLightCriticalVal3()).toString());
		result.add((null == pricePlanDetailDto.getHeavyPrice3() ? "" :  pricePlanDetailDto.getHeavyPrice3()).toString());
		result.add((null == pricePlanDetailDto.getLightPrice3() ? "" :  pricePlanDetailDto.getLightPrice3()).toString());
		result.add((null == pricePlanDetailDto.getHeavyCriticalVal4() ? "" :  pricePlanDetailDto.getHeavyCriticalVal4()).toString());
		result.add((null == pricePlanDetailDto.getLightCriticalVal4() ? "" :  pricePlanDetailDto.getLightCriticalVal4()).toString());
		result.add((null == pricePlanDetailDto.getHeavyPrice4() ? "" :  pricePlanDetailDto.getHeavyPrice4()).toString());
		result.add((null == pricePlanDetailDto.getLightPrice4() ? "" :  pricePlanDetailDto.getLightPrice4()).toString());
		result.add((null == pricePlanDetailDto.getHeavyCriticalVal5() ? "" :  pricePlanDetailDto.getHeavyCriticalVal5()).toString());
		result.add((null == pricePlanDetailDto.getLightCriticalVal5() ? "" :  pricePlanDetailDto.getLightCriticalVal5()).toString());
		result.add((null == pricePlanDetailDto.getHeavyPrice5() ? "" :  pricePlanDetailDto.getHeavyPrice5()).toString());
		result.add((null == pricePlanDetailDto.getLightPrice5() ? "" :  pricePlanDetailDto.getLightPrice5()).toString());
		result.add((null == pricePlanDetailDto.getHeavyCriticalVal6() ? "" :  pricePlanDetailDto.getHeavyCriticalVal6()).toString());
		result.add((null == pricePlanDetailDto.getLightCriticalVal6() ? "" :  pricePlanDetailDto.getLightCriticalVal6()).toString());
		result.add((null == pricePlanDetailDto.getHeavyPrice6() ? "" :  pricePlanDetailDto.getHeavyPrice6()).toString());
		result.add((null == pricePlanDetailDto.getLightPrice6() ? "" :  pricePlanDetailDto.getLightPrice6()).toString());
		//[POP] add 148246-sunjianbo 2014-11-13 end
		result.add(pricePlanDetailDto.getCentralizePickup());
		result.add(pricePlanDetailDto.getRemark());
		return result;
	}

	/**
	 * 
	 * 
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.
	 *            PricePlanService.exportComparePlatform
	 * @Description:填充价格方案比对sheet row
	 * 
	 * @param pricePlanDetailDto
	 * @return
	 * 
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-12 下午3:53:04
	 * 
	 *                 Modification History: Date Author Version Description
	 *                 ----
	 *                 ------------------------------------------------------
	 *                 ------- 2014-5-12 130376-YANGKANG v1.0.0 create
	 */
	private List<String> exportComparePlatform(
			PricePlanDetailDto pricePlanDetailDto) {
		List<String> result = new ArrayList<String>();
		result.add(pricePlanDetailDto.getArrvRegionName());
		result.add(pricePlanDetailDto.getProductItemName());
		result.add(pricePlanDetailDto.getHeavyPrice().toString());
		result.add(pricePlanDetailDto.getLightPrice().toString());
		result.add(pricePlanDetailDto.getMinimumOneTicket().toString());
		result.add(pricePlanDetailDto.getCentralizePickup());
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
			result.add("激活");
		} else {
			result.add("未激活");
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
		log.info("pop-开始查询价格方案");
		List<PricePlanEntity> pricePlanEntitys = queryPricePlanBatchInfo(
				pricePlanEntity, PricingConstants.ZERO, Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(pricePlanEntitys)) {
			return null;
		}
		log.info("pop-查询结果总条数："+pricePlanEntitys.size());
		log.info("pop-开始获取区域名称");
		List<PricePlanEntity> pricePlanList = convertToRegionName(pricePlanEntitys);
		log.info("pop-获取区域名称后总条数："+pricePlanList.size());
		List<List<String>> rowList = new ArrayList<List<String>>();
		log.info("pop-开始将方案转换为list");
		int i = 0;
		for (PricePlanEntity tempPricePlan : pricePlanList) {
			i++;
			log.info("pop-正在转换第"+i+"条,改条方案名称为："+tempPricePlan.getName());
			List<String> row = exportPlatform(tempPricePlan);
			rowList.add(row);
		}
		log.info("pop-转换完毕，总处理条数为："+i+"||"+rowList.size());
		log.info("pop-开始设置表头");
		exportResource.setHeads(PricingColumnConstants.PRICE_PLAN_TITLE);
		log.info("pop-开始添加数据");
		exportResource.setRowList(rowList);
		log.info("pop-返回结果");
		return exportResource;
	}

	/**
	 * 
	 * <p>
	 * 立即激活
	 * </p>
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2013-2-21 上午10:26:02
	 * 
	 * @param pricePlanEntity
	 * 
	 * @see
	 */
	@Override
	public void immediatelyActivePricePlan(PricePlanEntity pricePlanEntity, List<PricePlanEntity> pricePlanEntityList, List<String> priceValuationIdsList) {
		Date currentTime = new Date();
		if (null != pricePlanEntity) {
//			List<String> pricePlanIds = new ArrayList<String>();
			PricePlanEntity dbPricePlanEntity = pricePlanDao
					.selectByPrimaryKey(pricePlanEntity.getId());
			if (null == dbPricePlanEntity) {
				throw new PricePlanException("所选价格方案为空! 请检查", null);
			}
			dbPricePlanEntity.setBeginTime(pricePlanEntity.getBeginTime());
			dbPricePlanEntity.setModifyDate(currentTime);
			String pricePlanId = dbPricePlanEntity.getId();
			Date beginTime = dbPricePlanEntity.getBeginTime();
			String priceregionId = dbPricePlanEntity.getPriceRegionId();
			// 同一个价格方案下计费规则
			PriceValuationEntity valEntity = new PriceValuationEntity();
			valEntity.setPricePlanId(pricePlanId);
			List<PriceValuationEntity> valList = priceValuationDao
					.selectByCodition(valEntity);
			if (CollectionUtils.isEmpty(valList)) {
				throw new PricePlanException("空的方案不能激活!", null);
			}

			// List<String> valuationIds = new ArrayList<String>();
			// 遍历所有计费规则，只要有一条明细与DB中存在重复，则报出异常提示信息，整个方案将不可激活。
			for (PriceValuationEntity priceValuationEntity : valList) {
				// 计费规则影响价格的因素属性
				String arrvRegionId = priceValuationEntity.getArrvRegionId();
				String isCentralizePickup = priceValuationEntity
						.getCentralizePickup();
				String productCode = priceValuationEntity.getProductCode();
				String goodsTypeCode = priceValuationEntity.getGoodsTypeCode();
				String priceValuationId = priceValuationEntity.getId();
				PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
				priceCriteriaDetailEntity
						.setPricingValuationId(priceValuationEntity.getId());
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
						} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
								.equals(tempDetailEntity.getCaculateType())) {
							criteriaDetailVolume = tempDetailEntity;
						}
					}
				}
				BigDecimal weightRate = criteriaDetailWeight.getFeeRate();
				BigDecimal volumeRate = criteriaDetailVolume.getFeeRate();
				Double currentWeightRate = weightRate == null ? new Double(
						PricingConstants.ZERO) : weightRate.doubleValue();
				Double currentVolumeRate = volumeRate == null ? new Double(
						PricingConstants.ZERO) : volumeRate.doubleValue();
				// 获取同一个始发区域的方案下明细重复的记录,如果存在重复,不能激活当前方案，给予客户端提示
				QueryExistPricePlanDetailBean queryExistPricePlanBean = new QueryExistPricePlanDetailBean();
				queryExistPricePlanBean.setBeginTime(beginTime);
				queryExistPricePlanBean.setPriceRegionId(priceregionId);
				queryExistPricePlanBean.setProductCode(productCode);
				queryExistPricePlanBean.setGoodsTypeCode(goodsTypeCode);
				queryExistPricePlanBean.setArrvRegionId(arrvRegionId);
				queryExistPricePlanBean.setActive(FossConstants.ACTIVE);
				queryExistPricePlanBean
						.setIsCentralizePickup(isCentralizePickup);
				String centralizeName = isCentralizePickup;
				if (StringUtil.isNotBlank(isCentralizePickup)) {
					if (StringUtil.equals(isCentralizePickup,
							FossConstants.ACTIVE)) {
						centralizeName = "是";
					} else {
						centralizeName = "否";
					}
				} else {
					throw new PricePlanException("参数为空, 请联系管理员检查!", null);
				}
				List<ResultPricePlanDetailBean> resultDetailBeans = pricePlanDao
						.isExistRpeatPricePlanDetailData(queryExistPricePlanBean);
				if (CollectionUtils.isNotEmpty(resultDetailBeans)) {
					for (ResultPricePlanDetailBean resultPricePlanDetailBean : resultDetailBeans) {
						Double leftFeeRate = resultPricePlanDetailBean
								.getLightFeeRate().doubleValue();
						Double rightFeeRate = resultPricePlanDetailBean
								.getHeavyFeeRate().doubleValue();
						String caculateType = resultPricePlanDetailBean
								.getCaculateType();
						String existPricePlanId = resultPricePlanDetailBean
								.getPricePlanId();
						// 按重量
						if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT
								.equals(caculateType)) {
							// 只对符合的重量范围才进行计算
							if (currentWeightRate >= leftFeeRate
									&& currentWeightRate < rightFeeRate) {
								GoodsTypeEntity goodsTypeEntity = goodsTypeService
										.queryGoodsTypeByGoodTypeCode(
												goodsTypeCode, currentTime);
								ProductEntity producTypeEntity = productService
										.getProductByCache(productCode,
												currentTime);
								PricePlanEntity selectByPrimaryBean = pricePlanDao
										.selectByPrimaryKey(existPricePlanId);
								PriceRegionEntity priceRegion = regionService
										.searchRegionByID(priceregionId,
												PricingConstants.PRICING_REGION);
								PriceRegionArriveEntity arrvRegion = regionArriveService
										.searchRegionByID(arrvRegionId,
												PricingConstants.PRICING_REGION);
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
										+ selectByPrimaryBean.getName()
										+ "价格方案!");
								throw new PricePlanException(errStr.toString(),
										null);
							}
						} else if (PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME
								.equals(caculateType)) {// 按体积
							// 只对符合的体积范围才进行计算
							if (currentVolumeRate >= leftFeeRate
									&& currentVolumeRate < rightFeeRate) {
								GoodsTypeEntity goodsTypeEntity = goodsTypeService
										.queryGoodsTypeByGoodTypeCode(
												goodsTypeCode, currentTime);
								ProductEntity producTypeEntity = productService
										.getProductByCache(productCode,
												currentTime);
								PricePlanEntity selectByPrimaryBean = pricePlanDao
										.selectByPrimaryKey(existPricePlanId);
								PriceRegionEntity priceRegion = regionService
										.searchRegionByID(priceregionId,
												PricingConstants.PRICING_REGION);
								PriceRegionArriveEntity arrvRegion = regionArriveService
										.searchRegionByID(arrvRegionId,
												PricingConstants.PRICING_REGION);

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
										+ selectByPrimaryBean.getName()
										+ "价格方案!");
								throw new PricePlanException(errStr.toString(),
										null);
							}
						}
					}
				}
				if (StringUtil.isNotEmpty(priceValuationId)) {
					
					priceValuationIdsList.add(priceValuationId);
					
					
//					List<String> valuationIds = new ArrayList<String>();
//					valuationIds.add(priceValuationId);
					// //计费规则 放在循环中执行 避免数据量太大 超过in的范围
//					priceValuationDao.activeValueAdded(valuationIds);
//					priceCriteriaDetailDao
//							.activeCriteriaDetailByValuationIds(valuationIds);
					
					
				}
			}
			// //计费规则
			// priceValuationDao.activeValueAdded(valuationIds);
			// priceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
			
			dbPricePlanEntity.setVersionNo(currentTime.getTime());
			dbPricePlanEntity.setActive(FossConstants.ACTIVE);
			
			pricePlanEntityList.add(dbPricePlanEntity);

//			pricePlanIds.add(pricePlanId);
//			pricePlanDao.updateByPrimaryKeySelective(dbPricePlanEntity);
//			pricePlanDao.activePricePlan(pricePlanIds);
		}
	}

	@Override
	public void immediatelyActivePricePlanBatch(List<PricePlanEntity> dbPricePlanEntityList,
			List<String> priceValuationIdsList) {
		pricePlanDao.activePricePlanBatch(dbPricePlanEntityList, priceValuationIdsList);
	}

	@Override
	public void stopPlanBatch(List<PricePlanEntity> pricePlanEntityList,
			List<PriceValuationEntity> priceValuationEntityList) {
		pricePlanDao.stopPricePlanBatch(pricePlanEntityList, priceValuationEntityList);
	}
}

