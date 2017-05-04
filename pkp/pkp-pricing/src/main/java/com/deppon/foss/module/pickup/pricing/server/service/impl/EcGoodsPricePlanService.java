package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEcGoodsPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEcGoodsPricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IEcGoodsPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceRuleDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IProductItemDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IEcGoodsPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsArriveService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionEcGoodsService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PricingColumnConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsArriveEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEcGoodsEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PricePlanDetailDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryExistPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultPricePlanDetailBean;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.EcGoodsPriceManageMentVo;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.NumberUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @Description	首续重价格方案管理service接口实现
 * @author		348757-cc
 * @date		2016-07-04
 * @version		1.0
 */
public class EcGoodsPricePlanService implements IEcGoodsPricePlanService{

	/**日志对象*/
	private static final Logger LOGGER = Logger.getLogger(EcGoodsPricePlanService.class);
	/**计费规则*/
	private static final String VALUATION_RULE ="VALUATIONRULE";
	/**计费明细*/
	private static final String PRICING_CRITERIA="PRICINGCRITERIA";
	/**价格方案主信息*/
	IEcGoodsPricePlanDao ecGoodsPricePlanDao;
	/**计费规则*/
	IEcGoodsPriceValuationDao ecGoodsPriceValuationDao;
	/**计费明细*/
	IEcGoodsPriceCriteriaDetailDao ecGoodsPriceCriteriaDetailDao;
	/**产品条目*/
	IProductItemDao productItemDao;
	/**计价条目*/
	IPriceEntryDao priceEntryDao;
	/**价格计算表达式*/
	IPriceRuleDao priceRuleDao;
	/**产品Service*/
	IProductService productService;
	/** 货物Service*/
	IGoodsTypeService goodsTypeService;
	/**员工信息Service*/
	IEmployeeService employeeService;
	/**出发区域Service*/
	IRegionEcGoodsService regionEcGoodsService;
	/** 到达区域Service*/
	private IRegionEcGoodsArriveService regionEcGoodsArriveService;
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public void setRegionEcGoodsService(IRegionEcGoodsService regionEcGoodsService) {
		this.regionEcGoodsService = regionEcGoodsService;
	}
	public void setRegionEcGoodsArriveService(IRegionEcGoodsArriveService regionEcGoodsArriveService) {
		this.regionEcGoodsArriveService = regionEcGoodsArriveService;
	}
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}
	public void setPriceRuleDao(IPriceRuleDao priceRuleDao) {
		this.priceRuleDao = priceRuleDao;
	}
	public void setPriceEntryDao(IPriceEntryDao priceEntryDao) {
		this.priceEntryDao = priceEntryDao;
	}
	public void setProductItemDao(IProductItemDao productItemDao) {
		this.productItemDao = productItemDao;
	}
	public void setEcGoodsPriceValuationDao(IEcGoodsPriceValuationDao ecGoodsPriceValuationDao) {
		this.ecGoodsPriceValuationDao = ecGoodsPriceValuationDao;
	}
	public void setEcGoodsPriceCriteriaDetailDao(IEcGoodsPriceCriteriaDetailDao ecGoodsPriceCriteriaDetailDao) {
		this.ecGoodsPriceCriteriaDetailDao = ecGoodsPriceCriteriaDetailDao;
	}
	public void setEcGoodsPricePlanDao(IEcGoodsPricePlanDao ecGoodsPricePlanDao) {
		this.ecGoodsPricePlanDao = ecGoodsPricePlanDao;
	}
////////////////////////////////////////////////////////【导入】/////////////////////////////////////////////////////////////////////////
	/**
	 * Excel批量导入价格方案0
	 */
	@Transactional
	public void addPricePlanBatch(
			Map<String, List<PricePlanDetailDto>> planDetailMap,
			Map<String, PriceRegionEcGoodsEntity> regionEntityMap,
			Map<String, PriceRegionEcGoodsArriveEntity> arriveRegionEntityMap,
			Map<String, ProductEntity> productEntityMap) {
		// 检查入参集合数据完整性,不全gameover！
		if (MapUtils.isEmpty(planDetailMap)
				|| MapUtils.isEmpty(regionEntityMap)
				|| MapUtils.isEmpty(arriveRegionEntityMap)
				|| MapUtils.isEmpty(productEntityMap)) {
			return;
		}
		// 数据库运费的计价条目查询--查到取第一条
		PriceEntity priceEntry = new PriceEntity();
		priceEntry.setCode(PriceEntityConstants.PRICING_CODE_FRT);
		priceEntry.setReceiveDate(new Date());
		List<PriceEntity> priceEntryList = priceEntryDao.searchPriceEntryByConditions(priceEntry);
		if (CollectionUtils.isEmpty(priceEntryList)) {
			//检查计价条目为空
			throw new PricePlanException(PricePlanException.PRICE_PLAN_ADD_PRICINGENTRYISNULL_ERROR_CODE, null);
		} else {
			priceEntry = priceEntryList.get(0);
		}
		// 查询货物类型，固定的常量【所有H00001】
		GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001, new Date());
		List<PricePlanEntity> pricePlanBatch = new ArrayList<PricePlanEntity>();//方案
		List<PriceValuationEntity> priceValuationBatch = new ArrayList<PriceValuationEntity>();//价格规则
		List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch = new ArrayList<PriceCriteriaDetailEntity>();//价格明细
		// 遍历价格方案明细集合
		Set<Entry<String, List<PricePlanDetailDto>>> detailSet = planDetailMap.entrySet();
		for (Entry<String, List<PricePlanDetailDto>> entry : detailSet) {
			//出发区域名称
			String regionName = entry.getKey();
			//出发区域对应的价格方案
			List<PricePlanDetailDto> detailList = entry.getValue();
			//出发区域信息
			PriceRegionEcGoodsEntity startRegion = regionEntityMap.get(regionName);
			if (null == startRegion) {
				//没有继续下一个出发地查找
				continue;
			}
			// 创建一个价格方案主体
			PricePlanEntity pricePlanEntity = new PricePlanEntity();
			// 出发区域id
			pricePlanEntity.setPriceRegionId(startRegion.getId());
			// 出发区域编码
			pricePlanEntity.setPriceRegionCode(startRegion.getRegionCode());
			// 设置方案名称
			pricePlanEntity.setName(regionName + "导入价格方案");
			// 方案生效日期【默认明天】
			pricePlanEntity.setBeginTime(DateUtils.getStartDatetime(new Date(), 1));
			// 准备数据
			pricePlanEntity = this.getPricePlanValue(pricePlanEntity);
			pricePlanBatch.add(pricePlanEntity);
			String pricePlanId = pricePlanEntity.getId();
			// 插入价格主方案下对应的明细表（规则表和明细表【重量、体积】）
			for (int loop = 0; loop < detailList.size(); loop++) {
				PricePlanDetailDto pricePlanDetailDto = detailList.get(loop);
				pricePlanDetailDto.setPricePlanId(pricePlanId);
				// 设置状态--未生效N
				pricePlanDetailDto.setActive(FossConstants.INACTIVE);
				PriceRegionEcGoodsArriveEntity arriveRegionEntity = arriveRegionEntityMap.get(pricePlanDetailDto.getArrvRegionName());
				if ( null == arriveRegionEntity) {
					LOGGER.error("到达区域ID不存在！");
					throw new BusinessException("到达区域ID不存在！");
				}
				// 设置到达区域ID
				pricePlanDetailDto.setArrvRegionId(arriveRegionEntity.getId());
				// 取出相印的产品信息对象
				ProductEntity procuctEntity = productEntityMap.get(pricePlanDetailDto.getProductItemName());
				this.addPriceBatchPlanDetail(pricePlanDetailDto, startRegion, procuctEntity, priceEntry, goodsTypeEntity,priceValuationBatch, priceCriteriaDetailEntityBatch);
			}
		}
		ecGoodsPricePlanDao.insertPricePlanAllBatch(pricePlanBatch, priceValuationBatch, priceCriteriaDetailEntityBatch);
	}

	// 批量添加首续重价格明细数据：为导入方法使用。优化为批处理
	@SuppressWarnings("unchecked")
	private void addPriceBatchPlanDetail(PricePlanDetailDto pricePlanDetailDto,
										 PriceRegionEcGoodsEntity deptRegion, ProductEntity procuctEntity,
										 PriceEntity priceEntry, GoodsTypeEntity goodsTypeEntity, List<PriceValuationEntity> priceValuationBatch, List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityBatch) {
		// 处理计费规则与计价明细【重量/体积】
		Map<String, Object> priceCriteriaDetailMap = this.analysisBatchPricePlanData(pricePlanDetailDto, deptRegion, procuctEntity, priceEntry,goodsTypeEntity);
		// 取出计费规则数据
		List<PriceValuationEntity> valuations = (List<PriceValuationEntity>) priceCriteriaDetailMap.get(EcGoodsPricePlanService.VALUATION_RULE);
		// 取出价格明细数据
		List<PriceCriteriaDetailEntity> details = (List<PriceCriteriaDetailEntity>) priceCriteriaDetailMap.get(EcGoodsPricePlanService.PRICING_CRITERIA);
		// 批处理计费规则数据
		if (CollectionUtils.isNotEmpty(valuations)) {
			for (PriceValuationEntity priceValuationEntity : valuations) {
				priceValuationBatch.add(priceValuationEntity);
			}
		}
		// 批处理计价明细数据
		if (CollectionUtils.isNotEmpty(details)) {
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntitys : details) {
				priceCriteriaDetailEntityBatch.add(priceCriteriaDetailEntitys);
			}
		}
	}

	// 准备计价规则和计价明细的批量数据：为导入方案用
	private Map<String, Object> analysisBatchPricePlanData(PricePlanDetailDto detail, PriceRegionEcGoodsEntity deptRegion, ProductEntity procuctEntity,
														   PriceEntity priceEntry, GoodsTypeEntity goodsTypeEntity) {
		// 返回数据处理结果容器-主要是对计费规则,计价明细的数据收集
		Map<String, Object> priceCriteriaDetailMap = new HashMap<String, Object>();
		// 明天的零点
		Date currentTime = DateUtils.getStartDatetime(new Date(), 1);
		//************************************规则************************************************
		// 批量设置计费规则【重量/体积】
		List<PriceValuationEntity> valuationEntitys = new ArrayList<PriceValuationEntity>();
		PriceValuationEntity heavyValuationEntity = new PriceValuationEntity();
		PriceValuationEntity lightValuationEntity = new PriceValuationEntity();
		// 计价规则--按重量
		this.setPriceRuleEntity(detail, deptRegion, procuctEntity, priceEntry,goodsTypeEntity, currentTime, heavyValuationEntity);
		valuationEntitys.add(heavyValuationEntity);
		// 计价规则--按体积
		this.setPriceRuleEntity(detail, deptRegion, procuctEntity, priceEntry,goodsTypeEntity, currentTime, lightValuationEntity);
		valuationEntitys.add(lightValuationEntity);
		//*************************************明细************************************************
		// 计价方式明细【重量/体积】
		List<PriceCriteriaDetailEntity> criteriaList = new ArrayList<PriceCriteriaDetailEntity>();
		List<PriceCriteriaDetailEntity> heavyList = new ArrayList<PriceCriteriaDetailEntity>();
		List<PriceCriteriaDetailEntity> lightList = new ArrayList<PriceCriteriaDetailEntity>();
		PriceCriteriaDetailEntity heavyEntity = new PriceCriteriaDetailEntity();
		PriceCriteriaDetailEntity lightEntity = new PriceCriteriaDetailEntity();
		// 计价方式明细--按重量
		this.setEntity(detail, deptRegion, currentTime, heavyValuationEntity,heavyEntity);
		heavyEntity.setLeftrange(detail.getWeightLeftRange());
		heavyEntity.setRightrange(detail.getWeightRightRange());
		heavyEntity.setFeeRate(NumberUtils.multiply(detail.getHeavyPrice(), PricingConstants.YUTOFEN));//单位:分
		heavyEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT);
		heavyList.add(heavyEntity);
		criteriaList.addAll(heavyList);
		// 计价方式明细--按体积
		this.setEntity(detail, deptRegion, currentTime, lightValuationEntity,lightEntity);
		lightEntity.setFeeRate(NumberUtils.multiply(detail.getLightPrice(), PricingConstants.YUTOFEN));//单位:分
		lightEntity.setCaculateType(PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_VOLUME);
		lightEntity.setLeftrange(detail.getVolumeLeftRange());
		lightEntity.setRightrange(detail.getVolumeRightRange());
		lightList.add(lightEntity);
		criteriaList.addAll(lightList);
		priceCriteriaDetailMap.put(EcGoodsPricePlanService.VALUATION_RULE, valuationEntitys);
		priceCriteriaDetailMap.put(EcGoodsPricePlanService.PRICING_CRITERIA, criteriaList);
		return priceCriteriaDetailMap;
	}

	// 设置计价规则属性值
	private void setPriceRuleEntity(PricePlanDetailDto detail,
									PriceRegionEcGoodsEntity deptRegion, ProductEntity procuctEntity,
									PriceEntity priceEntry, GoodsTypeEntity goodsTypeEntity,
									Date currentTime, PriceValuationEntity valuationEntity) {
		// 设置产品code
		valuationEntity.setProductCode(procuctEntity.getCode());
		// 设置货物类型code
		valuationEntity.setGoodsTypeCode(goodsTypeEntity.getCode());
		// 运价方案ID
		valuationEntity.setPricePlanId(detail.getPricePlanId());
		// 是否集中接货
		valuationEntity.setCentralizePickup(detail.getCentralizePickup());
		// 是否集中送货【PS：这是首续重价格方案新增字段】
		valuationEntity.setCentralizeDelivery(detail.getCentralizeDelivery());
		// 规则类型-价格方案
		valuationEntity.setType(PricingConstants.VALUATION_TYPE_PRICING);
		// 到达区域ID
		valuationEntity.setArrvRegionId(detail.getArrvRegionId());
		// 出发区域ID
		valuationEntity.setDeptRegionId(deptRegion.getId());
		// 计价条目代码
		valuationEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
		// 收集计费规则信息
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
	}

	// 设置计价方式明细属性值
	private void setEntity(PricePlanDetailDto detail,
						   PriceRegionEcGoodsEntity deptRegion, Date currentTime,
						   PriceValuationEntity valuationEntity,
						   PriceCriteriaDetailEntity entity) {
		// 关联计费规则ID
		entity.setPricingValuationId(valuationEntity.getId());
		// 设置明细备注
		entity.setDescription(detail.getRemark());
		// 数据版本
		entity.setVersionNo(currentTime.getTime());
		// 数据状态
		entity.setActive(detail.getActive());
		// 固定费用
		entity.setFee(detail.getFixedCosts().longValue());
		// 条件id设为空，原因该属性关联关系的实体已经不存在。后续删除该字段
		entity.setPricingCriteriaId("");
		// 价格计算表达式Id="PR008"
		entity.settSrvPriceRuleId(PricingConstants.EC_PRICE_RULE_RATERULES_ID);
		// 实例ID
		entity.setId(UUIDUtils.getUUID());
		// 始发区域ID
		entity.setDeptRegionId(deptRegion.getId());
		entity.setActive(detail.getActive());
	}
	
	// 价格方案批次新增信息
	private PricePlanEntity getPricePlanValue(PricePlanEntity pricePlanEntity){
		Date currentTime = new Date();
		pricePlanEntity.setId(UUIDUtils.getUUID());
		pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_EC_FLAG);//4
		pricePlanEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
		pricePlanEntity.setActive(FossConstants.INACTIVE);//N
		pricePlanEntity.setVersionInfo(String.valueOf(currentTime.getTime())); //当前时间的字符串形式
		pricePlanEntity.setEndTime(new Date(PricingConstants.ENDTIME));//2999年-12-31 23:59:59
		pricePlanEntity.setModifyDate(currentTime);//当前时间date型
		pricePlanEntity.setVersionNo(currentTime.getTime());//当前时间的数值型
		pricePlanEntity.setCreateDate(currentTime);//当前时间date型
		return pricePlanEntity;
	}

////////////////////////////////////////////////////////【查询】/////////////////////////////////////////////////////////////////////////
	/**
	 * 分页查询价格方案1
	 */
	@Override
	public List<PricePlanEntity> queryPricePlanBatchInfo(PricePlanEntity pricePlanEntity, int start,int limit) {
		//设置条件
		if (PricingConstants.ALL.equals(pricePlanEntity.getActive())) {
			pricePlanEntity.setActive(null);
		}
		pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_EC_FLAG);
		List<PricePlanEntity> list =  ecGoodsPricePlanDao.queryPricePlanBatchInfo(pricePlanEntity, start, limit);
		return convertToRegionName(list);
	}

	/**
	 * 价格方案查询总数1
	 */
	@Override
	public Long queryPricePlanBatchInfoCount(PricePlanEntity pricePlanEntity) {
		pricePlanEntity.setTransportFlag(PricingConstants.TRANSPORT_EC_FLAG);
		return ecGoodsPricePlanDao.queryPricePlanBatchInfoCount(pricePlanEntity);
	}

	/**
	 * 分页查询价格方案明细2
	 */
	@Override
	public List<PricePlanDetailDto> queryPricePlanDetailInfo(QueryPricePlanDetailBean queryPricePlanDetailBean,int start, int limit) {
		if(null != queryPricePlanDetailBean){
			String productItemCode = queryPricePlanDetailBean.getProductItemCode();
			if (StringUtil.isNotBlank(productItemCode)) {
				ProductItemEntity productItemEntity = productItemDao.findProductItemByCode(productItemCode, new Date());
				if (null != productItemEntity) {
					queryPricePlanDetailBean.setGoodsTypeCode(productItemEntity.getGoodstypeCode());
					queryPricePlanDetailBean.setProductCode(productItemEntity.getProductCode());
				}
			}
		}
		List<ResultPricePlanDetailBean> resultPricePlanDetailBeanList = ecGoodsPricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean, start, limit);
		if (CollectionUtils.isNotEmpty(resultPricePlanDetailBeanList)) {
			List<PricePlanDetailDto> list = this.boxHeaveAndLight(resultPricePlanDetailBeanList);
			this.sortList(list);
			return list;
		}
		return null;
	}

	/**
	 * 查询价格方案明细总记录数2
	 */
	@Override
	public Long queryPricePlanDetailInfoCount(QueryPricePlanDetailBean queryPricePlanDetailBean) {
		return ecGoodsPricePlanDao.queryPricePlanDetailInfoCount(queryPricePlanDetailBean);
	}

	/**
	 * 查询需要修改和复制的价格方案3、9
	 */
	@Override
	public EcGoodsPriceManageMentVo queryCopyPricePlanInfo(String pricePlanId){
		if(StringUtil.isEmpty(pricePlanId)){
			throw new PricePlanException("选择的主方案信息缺失!,请联系运维人员查询原因。",null);
		}
		PricePlanEntity pricePlan = ecGoodsPricePlanDao.selectByPrimaryKey(pricePlanId);
		PriceRegionEcGoodsEntity priceRegionEntity = regionEcGoodsService.searchRegionByID(pricePlan.getPriceRegionId());
		pricePlan.setPriceRegionName(priceRegionEntity.getRegionName());
		EcGoodsPriceManageMentVo ecPriceManagementVo = new EcGoodsPriceManageMentVo();
		ecPriceManagementVo.setPricePlanEntity(pricePlan);
		return ecPriceManagementVo;
	}


	/**
	 * 修改价格方案信息4
	 */
	@Override
	@Transactional
	public PricePlanEntity modifyPricePlan(PricePlanEntity pricePlanEntity)throws PricePlanException {
		if(null == pricePlanEntity){
			throw new PricePlanException("主方案信息缺失,请检查!",null);
		}
		if(pricePlanEntity.getBeginTime().before(new Date())){
			throw new PricePlanException("方案生效日期:"+DateUtils.convert(pricePlanEntity.getBeginTime())+"必须大于当前营业日期",null);
		}
		String pricePlanId = pricePlanEntity.getId();
		PricePlanEntity dbPricePlanEntity = ecGoodsPricePlanDao.selectByPrimaryKey(pricePlanId);
		try {
			PropertyUtils.copyProperties(dbPricePlanEntity, pricePlanEntity);
		} catch (IllegalAccessException e) {
			LOGGER.info(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			LOGGER.info(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			LOGGER.info(e.getMessage(), e);
		}
		//更新方案操作
		ecGoodsPricePlanDao.updateByPrimaryKeySelective(dbPricePlanEntity);
		//查询修改好的方案
		return ecGoodsPricePlanDao.selectByPrimaryKey(pricePlanId);
	}

////////////////////////////////////////////////////////【删除】/////////////////////////////////////////////////////////////////////////
	/**
	 * 删除方案5
	 */
	@Override
	@Transactional
	public void deletePricePlan(List<String> pricePlanIds) {
		if(CollectionUtils.isNotEmpty(pricePlanIds)){
			List<String> valuationIds = new ArrayList<String>();
			for(int i = 0; i<pricePlanIds.size(); i++){
				String pricePlanId =  pricePlanIds.get(i);
				PriceValuationEntity valEntity = new PriceValuationEntity();
				valEntity.setPricePlanId(pricePlanId);
				List<PriceValuationEntity> valList = ecGoodsPriceValuationDao.selectByCodition(valEntity);
				if (CollectionUtils.isNotEmpty(valList)) {
					for (PriceValuationEntity priceValuationEntity : valList) {
						if (null != priceValuationEntity) {
							valuationIds.add(priceValuationEntity.getId());
						}
					}
				}
			}
			// 批量删除--压力有点大！
			ecGoodsPricePlanDao.batchDeletePlan(pricePlanIds);
			ecGoodsPriceValuationDao.batchDleleteValuation(pricePlanIds);
			ecGoodsPriceCriteriaDetailDao.batchDeleteCriteria(valuationIds);
		}
	}

////////////////////////////////////////////////////////【激活】/////////////////////////////////////////////////////////////////////////
	/**
	 * 立即激活价格方案6
	 */
	@Override
	@Transactional
	public void immediatelyActivePricePlan(PricePlanEntity pricePlanEntity) {
		Date currentTime = new Date();
		if(null!=pricePlanEntity){
			PricePlanEntity dbPricePlanEntity = ecGoodsPricePlanDao.selectByPrimaryKey(pricePlanEntity.getId());
			if(null == dbPricePlanEntity){
				throw new PricePlanException("所选价格方案为空! 请检查",null);
			}
			String pricePlanId = dbPricePlanEntity.getId();
			Date beginTime = pricePlanEntity.getBeginTime();
			// 一条方案只对应一个出发地--查出来备用！
			String priceregionId = dbPricePlanEntity.getPriceRegionId();
			PriceRegionEcGoodsEntity startRegion = regionEcGoodsService.searchRegionByID(priceregionId);
			//查询出需要被激活的价格方案下所有的计费规则
			PriceValuationEntity valEntity = new PriceValuationEntity();
			valEntity.setPricePlanId(pricePlanId);
			List<PriceValuationEntity> valList = ecGoodsPriceValuationDao.selectByCodition(valEntity);
			if(CollectionUtils.isEmpty(valList)){
				throw new PricePlanException("空的方案不能激活!", null);
			}
			List<String> valuationIds =  new ArrayList<String>();
			//遍历所有计费规则，只要有一条明细存在重复，则报出异常提示信息，整个方案将不可激活。
			for (PriceValuationEntity priceValuationEntity : valList) {
				if (null!=priceValuationEntity) {
					String arrvRegionId =  priceValuationEntity.getArrvRegionId();
					String productCode = priceValuationEntity.getProductCode();
					String goodsTypeCode = priceValuationEntity.getGoodsTypeCode();
					QueryExistPricePlanDetailBean queryExistPricePlanBean = new QueryExistPricePlanDetailBean();
					queryExistPricePlanBean.setBeginTime(beginTime);
					queryExistPricePlanBean.setPriceRegionId(priceregionId);
					queryExistPricePlanBean.setProductCode(productCode);
					queryExistPricePlanBean.setGoodsTypeCode(goodsTypeCode);
					queryExistPricePlanBean.setArrvRegionId(arrvRegionId);
					queryExistPricePlanBean.setActive(FossConstants.ACTIVE);
					List<ResultPricePlanDetailBean> resultDetailBeans = ecGoodsPricePlanDao.isExistRpeatPricePlanDetailData(queryExistPricePlanBean);
					if(CollectionUtils.isNotEmpty(resultDetailBeans)){
						ProductEntity   producTypeEntity = productService.getProductByCache(productCode, currentTime);
						GoodsTypeEntity goodsTypeEntity = goodsTypeService.queryGoodsTypeByGoodTypeCode(goodsTypeCode, currentTime);
						PriceRegionEcGoodsArriveEntity arriveRegion = regionEcGoodsArriveService.searchRegionByID(arrvRegionId);
						StringBuilder itemName = new StringBuilder();
						itemName.append(producTypeEntity.getName());
						itemName.append("-");
						itemName.append(goodsTypeEntity != null ? goodsTypeEntity.getName():"");
						StringBuilder errStr = new StringBuilder();
						errStr.append("始发区域[");
						errStr.append(startRegion.getRegionName());
						errStr.append("],目的地区域[");
						errStr.append(arriveRegion.getRegionName());
						errStr.append("],条目名称[");
						errStr.append(itemName);
						errStr.append("],在价格方案名称为[");
						errStr.append(dbPricePlanEntity.getName());
						errStr.append("]下已经发生冲突的明细,不能被有效激活,要激活当前草稿,请删除当前草稿下与其他方案发生冲突的明细，或者中止已存在的["+dbPricePlanEntity.getName()+"]!");
						throw new PricePlanException(errStr.toString(), null);
					}
					valuationIds.add(priceValuationEntity.getId());
				}
			}
			valList = null;
			// 如果没有重复方案【出发地-目的地-已激活】存在，那么就激活该方案以及该方案下的规则明细！
			ecGoodsPriceCriteriaDetailDao.activeCriteriaDetails(valuationIds);
			ecGoodsPriceValuationDao.activeValuations(pricePlanId,beginTime);
			ecGoodsPricePlanDao.activePricePlan(pricePlanId,beginTime);
		}
	}

////////////////////////////////////////////////////////【中止】/////////////////////////////////////////////////////////////////////////
	/**
	 * 批量中止方案7、8
	 */
	@Override
	@Transactional
	public void stopPricePlan(PricePlanEntity entity) throws PricePlanException{
		String pricePlanId = entity.getId();
		Date stopTime = entity.getEndTime();
		if(StringUtil.isBlank(pricePlanId)){
			throw new PricePlanException("所选价格方案ID为空!",null);
		}
		if(stopTime == null){
			throw new PricePlanException("截至日期不能为空!",null);
		}
		if(stopTime.before(new Date())){
			throw new PricePlanException("中止日期必须大于当前营业日期!",null);
		}
		PricePlanEntity pricePlanEntity = ecGoodsPricePlanDao.selectByPrimaryKey(pricePlanId);
		if(null==pricePlanEntity)
		{
			throw new PricePlanException("根据前台参数实体ID,没有找到对应的实体!",null);
		}
		if (stopTime.before(pricePlanEntity.getBeginTime())) {
			throw new PricePlanException("中止日期不能早于原方案所制定的生效日期!",null);
		}
		if(stopTime.after(pricePlanEntity.getEndTime()))
		{
			throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
		}
		ecGoodsPriceValuationDao.updateValuation(pricePlanId , stopTime);
		ecGoodsPricePlanDao.stopPricePlan(pricePlanId , stopTime);
	}

////////////////////////////////////////////////////////【导出】/////////////////////////////////////////////////////////////////////////
	/**
	 * 导出价格方案10
	 */
	@Override
	public ExportResource exportPricePlan(PricePlanEntity pricePlanEntity) {
		ExportResource exportResource = new ExportResource();

		//解决乱码问题
		String name = pricePlanEntity.getName();
		if(StringUtil.isNotEmpty(name)){
			try {
				pricePlanEntity.setName( URLDecoder.decode(name, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		List<PricePlanEntity> pricePlanEntitys  = this.queryPricePlanBatchInfo(pricePlanEntity, 0, Integer.MAX_VALUE);
		if(CollectionUtils.isEmpty(pricePlanEntitys)){
			return null;
		}
		List<PricePlanEntity> pricePlanList = this.convertToRegionName(pricePlanEntitys);
		List<List<String>> rowList = new ArrayList<List<String>>();
		for (PricePlanEntity tempPricePlan : pricePlanList) {
			List<String> row = this.exportPlatform(tempPricePlan);
			rowList.add(row);
		}
		//{"方案名称","始发区域","修改时间","修改人","生效日期","截止日期","数据状态","是否当前版本"}
		exportResource.setHeads(PricingColumnConstants.EC_PRICE_PLAN_TITLE);
		exportResource.setRowList(rowList);
		return exportResource;
	}

	// 填充方案
	private List<String> exportPlatform(PricePlanEntity pricePlanEntity){
		List<String> result = new ArrayList<String>();
		result.add(pricePlanEntity.getName());
		result.add(pricePlanEntity.getPriceRegionName());
		String modifyDate = DateUtils.convert(pricePlanEntity.getModifyDate(), DateUtils.DATE_TIME_FORMAT);
		String beginDate = DateUtils.convert(pricePlanEntity.getBeginTime(), DateUtils.DATE_TIME_FORMAT);
		String endDate = DateUtils.convert(pricePlanEntity.getEndTime(), DateUtils.DATE_TIME_FORMAT);
		result.add(modifyDate);
		result.add(pricePlanEntity.getModifyUser());
		result.add(beginDate);
		result.add(endDate);
		if(StringUtil.equalsIgnoreCase(FossConstants.ACTIVE, pricePlanEntity.getActive())){
			result.add("激活");
		}else{
			result.add("未激活");
		}
		result.add(pricePlanEntity.getCurrentUsedVersion());
		return result;
	}

	/**
	 * 价格方案明细导出11
	 */
	@Override
	public ExportResource exportPricePlanDetail(QueryPricePlanDetailBean queryPricePlanDetailBean) {
		ExportResource exportResource = new ExportResource();
		List<ResultPricePlanDetailBean>  resultPricePlanDetailBeans = ecGoodsPricePlanDao.queryPricePlanDetailInfo(queryPricePlanDetailBean, 0, Integer.MAX_VALUE);
		List<PricePlanDetailDto> pricePlanDetailDtos = this.boxHeaveAndLight(resultPricePlanDetailBeans);
		this.sortList(pricePlanDetailDtos);
		List<List<String>> rowList = new ArrayList<List<String>>();
		if(CollectionUtils.isNotEmpty(pricePlanDetailDtos)){
			for (PricePlanDetailDto pricePlanDetail : pricePlanDetailDtos) {
				List<String> row = this.exportPlatform(pricePlanDetail);
				rowList.add(row);
			}
		}
		//{"目的站","产品条目","计费类型","下限","上限","费率","首重","是否包含接货","是否包含送货","备注"}
		exportResource.setHeads(PricingColumnConstants.PRICE_EC_PLAN_DETAIL_TITLE);
		exportResource.setRowList(rowList);
		return exportResource;
	}

	// 填充方案明细
	private List<String> exportPlatform(PricePlanDetailDto pricePlanDetailDto){
		List<String> result = new ArrayList<String>();
		result.add(pricePlanDetailDto.getArrvRegionName());
		result.add(pricePlanDetailDto.getProductItemName());
		result.add((StringUtils.equals(pricePlanDetailDto.getCaculateType(), PricingConstants.CRITERIA_DETAIL_CACULATE_TYPE_WEIGHT))?"重量":"体积");
		result.add(pricePlanDetailDto.getLeftRange().toString());
		result.add(pricePlanDetailDto.getRightRange().toString());
		result.add(pricePlanDetailDto.getPrices().toString());
		result.add(pricePlanDetailDto.getFixedCosts().toString());
		result.add(pricePlanDetailDto.getCentralizePickup());
		result.add(pricePlanDetailDto.getCentralizeDelivery());
		result.add(pricePlanDetailDto.getRemark());
		return result;
	}

////////////////////////////////////////////////////////【公用】//////////////////////////////////////////////////////////////////////////
	// 获取区域名称
	private List<PricePlanEntity> convertToRegionName(List<PricePlanEntity> list){
		List<PricePlanEntity> convertList = new ArrayList<PricePlanEntity>();
		for (PricePlanEntity pricePlanEntity : list) {
			String priceRegionId = pricePlanEntity.getPriceRegionId();
			PriceRegionEcGoodsEntity priceRegionEntity = regionEcGoodsService.searchRegionByID(priceRegionId);
			EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(pricePlanEntity.getModifyUser());
			if (null != employee) {
				pricePlanEntity.setModifyUserName(employee.getEmpName());
			}
			if (null != priceRegionEntity) {
				pricePlanEntity.setPriceRegionName(priceRegionEntity.getRegionName());
			}
			convertList.add(pricePlanEntity);
		}
		return convertList;
	}

	// 对查询结果的排序
	private void sortList(List< PricePlanDetailDto> list) {
		if (CollectionUtils.isEmpty(list)){
			return;
		}
		Collections.sort(list, new Comparator<PricePlanDetailDto>() {
			@Override
			public int compare(PricePlanDetailDto p1, PricePlanDetailDto p2) {
				if (p1.getArrvRegionName().compareTo(p2.getArrvRegionName()) == 0) {
					if (p1.getProductItemName().compareTo(p2.getProductItemName()) == 0) {
						if (p1.getCaculateType().compareTo(p2.getCaculateType()) == 0) {
							if (p1.getLeftRange().compareTo(p2.getLeftRange()) == 0) {
								return 0;
							} else {
								return p1.getLeftRange().compareTo(p2.getLeftRange()) > 0 ? 1 : -1;
							}
						} else {
							return p1.getCaculateType().compareTo(p2.getCaculateType()) >0 ? -1 : 1;
						}
					} else {
						return p1.getProductItemName().compareTo(p2.getProductItemName());
					}
				}
				return p1.getArrvRegionName().compareTo(p2.getArrvRegionName());
			}
		});
	}

	// 同一计费规则封装重货和轻货价格明细
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
			}else{
				pricingValuationIds.add(r1.getPricingValuationId());
			}
			pricePlanDetailDto = new PricePlanDetailDto();
			if(StringUtil.isBlank(r1.getArrvRegionId())){
				throw new PricePlanException("目的地区域信息为空,请联系管理员检查!",null);
			}
			//目的地区域名称---这里传入一个常量：价格区域
			PriceRegionEcGoodsArriveEntity priceRegionEntity = regionEcGoodsArriveService.searchRegionByID(r1.getArrvRegionId());
			if(null != priceRegionEntity){
				pricePlanDetailDto.setArrvRegionName(priceRegionEntity.getRegionName());
				pricePlanDetailDto.setArrvRegionId(priceRegionEntity.getId());
			}
			//是否集中接货
			pricePlanDetailDto.setCentralizePickup(r1.getCentralizePickup());
			//是否集中送货
			pricePlanDetailDto.setCentralizeDelivery(r1.getCentralizeDelivery());
			pricePlanDetailDto.setRemark(r1.getRemark());
			//设置方案ID
			pricePlanDetailDto.setPricePlanId(r1.getPricePlanId());
			ProductItemEntity productItemEntity = productItemDao.findProductItemByGoodCodeAndProductCode(r1.getProductCode(), r1.getGoodsTypeCode(), new Date());
			//产品条目信息
			pricePlanDetailDto.setProductItemId(productItemEntity.getId());
			pricePlanDetailDto.setProductItemName(productItemEntity.getName());
			pricePlanDetailDto.setProductItemCode(productItemEntity.getCode());
			//价格计费规则ID
			pricePlanDetailDto.setValuationId(r1.getPricingValuationId());
			//计费规则
			pricePlanDetailDto.setCaculateType(r1.getCaculateType());
			//开始范围
			pricePlanDetailDto.setLeftRange(r1.getLeftRange());
			//结束范围
			pricePlanDetailDto.setRightRange(r1.getRightRange());
			//固定费用--单位：元
			pricePlanDetailDto.setFixedCosts(r1.getFixedCosts().divide(new BigDecimal("100")));
			//价格--单位：元
			pricePlanDetailDto.setPrices(r1.getPrices().divide(new BigDecimal("100")));
			pricePlanDetailList.add(pricePlanDetailDto);
		}
		return pricePlanDetailList;
	}
}