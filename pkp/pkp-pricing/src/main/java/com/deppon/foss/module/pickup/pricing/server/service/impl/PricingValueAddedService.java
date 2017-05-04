/**
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 输入信息
 * 
 * 服务名称	服务名称	文本框		 50	否
 * 
 * 
 * 输出信息
 * 
 *  服务名称	服务名称	输出信息		80	是
 *  
*状态	启用状态	输出信息		20	是
*
*最后修改人	默认当前操作人	输出信息		20	是
*
*修改时间	最后修改时间	输出信息		20	是
*
*生效日期	生效日期	输出信息		10	是
*
*截止日期	截止日期	输出信息		10	是
*
*操作列	做修改	输出信息		20	是
*
*长短途	长短途定义	输出信息		20	是

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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/PricingValueAddedService.java
 * 
 * FILE NAME        	: PricingValueAddedService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.server.service.impl.CustomerIndustryService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IfibelPaperPackingUnitPriceDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPopValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricingValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ResultProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricingCommonException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 
 * @Description: 增值服务SERVICE
 * 
 * PricingValueAddedService.java Create on 2013-3-18 上午11:00:12
 * 
 * Company:IBM
 * 
 * @author FOSSDP-Administrator
 * 
 * Copyright (c) 2013 Company,Inc. All Rights Reserved
 * 
 * @version V1.0
 */
@Transactional
public class PricingValueAddedService implements IPricingValueAddedService {
	public static final String TABLE_NAME_VALUEADD = "T_SRV_PRICING_VALUATION";

	/**
	 * 增值服务DAO
	 */
    @Inject
	private IPricingValueAddedDao pricingValueAddedDao;
	/**
	 * 设置 增值服务DAO.
	 *
	 * @param pricingValueAddedDao the new 增值服务DAO
	 */
	public void setPricingValueAddedDao(
			IPricingValueAddedDao pricingValueAddedDao) {
		this.pricingValueAddedDao = pricingValueAddedDao;
	}
	@Inject
	private IPopValueAddedService popValueAddedService;
	public void setPopValueAddedService(IPopValueAddedService popValueAddedService){
		this.popValueAddedService = popValueAddedService;
	}
	/**
	 * 计费规则DAO
	 */
	@Inject
	private IPriceValuationDao priceValuationDao;
	/**
	 * 设置 计费规则DAO.
	 *
	 * @param priceValuationDao the new 计费规则DAO
	 */
	public void setPriceValuationDao(IPriceValuationDao priceValuationDao) {
		this.priceValuationDao = priceValuationDao;
	}
	/**
	 * 计价方式明细DAO
	 */
	@Inject
	private IPriceCriteriaDetailDao priceCriteriaDetailDao;
	/**
	 * 设置 计价方式明细DAO.
	 *
	 * @param priceCriteriaDetailDao the new 计价方式明细DAO
	 */
	public void setPriceCriteriaDetailDao(
			IPriceCriteriaDetailDao priceCriteriaDetailDao) {
		this.priceCriteriaDetailDao = priceCriteriaDetailDao;
	}
	/**
	 * 获取基础产品的SERVICE
	 */
	@Inject
	private IProductService productService;

	/**
	 * 设置 获取基础产品的SERVICE.
	 *
	 * @param productService the new 获取基础产品的SERVICE
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	/**
	 * 获取产品条目的SERVICE
	 */
	@Inject
	private IProductItemService productItemService;
	/**
	 * 设置 获取产品条目的SERVICE.
	 *
	 * @param productItemService the new 获取产品条目的SERVICE
	 */
	public void setProductItemService(IProductItemService productItemService) {
		this.productItemService = productItemService;
	}
	/**
	 * 获取货物类型的SERVICE 
	 */
	@Inject
	private IGoodsTypeService goodsTypeService;

	/**
	 * 设置 获取货物类型的SERVICE.
	 *
	 * @param goodsTypeService the new 获取货物类型的SERVICE
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}
    /**
     * 职员service
     */
	@Inject
	private IEmployeeService employeeService;
	/**
	 * 设置 职员service.
	 *
	 * @param employeeService the new 职员service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 * 增值区域
	 */
	@Inject
	IRegionValueAddService regionValueAddService;
	
	/**
	 * 增值区域
	 * @param regionValueAddService
	 */
	public void setRegionValueAddService(
			IRegionValueAddService regionValueAddService) {
		this.regionValueAddService = regionValueAddService;
	}
	
	/**
	 * 向纸纤包装单价表中增添数据
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-15上午10:37
	 */
	@Inject
	private IfibelPaperPackingUnitPriceDao fibelPaperPackingUnitPriceDao;
	
	public void setFibelPaperPackingUnitPriceDao(IfibelPaperPackingUnitPriceDao fibelPaperPackingUnitPriceDao) {
		this.fibelPaperPackingUnitPriceDao = fibelPaperPackingUnitPriceDao;
	}
	/**
	 * 二级行业service
	 */
	@Inject
	private CustomerIndustryService cusProfessionService;
	/**
	 * 二级行业service
	 * @param cusProfessionService
	 */
	public void setCusProfessionService(CustomerIndustryService cusProfessionService) {
		this.cusProfessionService = cusProfessionService;
	}
	/**
	 * .
	 * <p>
	 * 新增增值服务<br/>
	 * 方法名：addValueAdded
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 *            
	 * @param priceCriteriaDetailEntity
	 *            计价方式明细实体
	 *            
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	@Transactional
	public void addValueAdded(PriceValuationEntity priceValuationEntity,
			List<PriceCriteriaDetailEntity> priceCriteriaDetailEntityList) {
		String operateType = priceValuationEntity.getOperateTypeForAddAndUpdateVersion();
		// 新增需要校验方案名是否已存在，升级版本不需要校验
		if(!StringUtil.equals(operateType, "upgradedVersion")){
			if(isTheSameValueAddName(priceValuationEntity, false)){
				throw new PricingCommonException("增值方案名称已存在!","增值方案名称已存在!");
			}
		}
		long versionNo = new Date().getTime();
		UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 该计费规则是否激活
		String active = priceValuationEntity.getActive();
		String code  = this.createCode(priceValuationEntity.getType());
		priceValuationEntity.setCode(code);
		if (FossConstants.ACTIVE.equals(priceValuationEntity.getActive())) {
			//应该被修改的数据的截止日期
			Date endTime = new Date(priceValuationEntity.getBeginTime().getTime() - PricingConstants.ONE_MILLISECOND);
			// 查询截止时间是2999-12-31的数据
			PriceValuationEntity searchModel = new PriceValuationEntity();
			//出发区域
			searchModel.setDeptRegionId(priceValuationEntity.getDeptRegionId());
			//到达区域
			searchModel.setArrvRegionId(priceValuationEntity.getArrvRegionId());
			// 设置长短途
			searchModel.setLongOrShort(priceValuationEntity.getLongOrShort());
			// 设置类型
			searchModel.setType(priceValuationEntity.getType());
			//有效数据
			searchModel.setActive(FossConstants.ACTIVE);
			//业务类型
			searchModel.setPricingEntryCode(priceValuationEntity.getPricingEntryCode());
			//货物
			searchModel.setGoodsTypeId(priceValuationEntity.getGoodsTypeId());
			//产品
			searchModel.setProductCode(priceValuationEntity.getProductCode());			
			// 设置查询开始日期
			searchModel.setBeginTime(priceValuationEntity.getBeginTime());
			// 设置查询结束时间
			searchModel.setEndTime(priceValuationEntity.getEndTime());
			//业务发生时间
			searchModel.setBusinessBeginTime(priceValuationEntity.getBusinessBeginTime());
			//业务中止时间
			searchModel.setBusinessEndTime(priceValuationEntity.getBusinessEndTime());
			//根据规则类型,开始时间
			List<PriceValuationEntity> searchResultList = priceValuationDao.selectByCoditionSq(searchModel);
			for(PriceValuationEntity priceValuationEntityModel : searchResultList){
				//判断基础产品 和 货物类型否有交集,如果没有交集不算冲突
				if(checkRepartByRecord(priceValuationEntity,priceValuationEntityModel)){
					continue;
				}
//				{定价项目}去掉针对保费的限保物品的特殊判断,我怕后面可能会恢复,所有只是注释,由于限保物品可以为空,可能会报空指针异常,恢复时请注意
//				//针对保费特殊处理
//			    //根据已经重复的计费规则中找出数据库中的保费限保物品与当前所维护的限宝物品是否有冲突,如果限保物品一致,
//			    //则修改上一个计费规则的截止日期为当前日期的前一天
//			    if(StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,
//			    		priceValuationEntityModel.getPricingEntryCode())) {	
//            		List<PriceCriteriaDetailEntity> oldPriceCriteriaDetailEntityList  
//            			= priceCriteriaDetailDao.selectByValuationId(priceValuationEntityModel.getId());
//            		if(CollectionUtils.isNotEmpty(oldPriceCriteriaDetailEntityList)) {
//            		    //如果vo中的限保物品在数据库中已经存在,则修改上一个版本截止日期
//            		    if(StringUtil.isEmpty(priceCriteriaDetailEntityList.get(0).getSubType())
//        		    		||priceCriteriaDetailEntityList.get(0).getSubType().equalsIgnoreCase(
//        		    				oldPriceCriteriaDetailEntityList.get(0).getSubType())) {
//            		    	PriceValuationEntity updateModel = new PriceValuationEntity();
//	            			updateModel.setVersionNo(versionNo);
//	            			updateModel.setId(priceValuationEntityModel.getId());
//	            			updateModel.setEndTime(endTime);
//	            			updateModel.setModifyDate(new Date());
//	            			updateModel.setModifyUser(userCode);
//	            			updateModel.setModifyOrgCode(orgCode);
//	            			priceValuationDao.updateValuation(updateModel);
//            		    }
//            		}
//            		//其他增值服务
//                }else{
                	// 修改对应低版本数据
	            	PriceValuationEntity updateModel = new PriceValuationEntity();
        	   		updateModel.setVersionNo(versionNo);
        	   		updateModel.setId(priceValuationEntityModel.getId());
        	   		updateModel.setEndTime(endTime);
        	   		updateModel.setModifyDate(new Date());
        	   		updateModel.setModifyUser(userCode);
        	   		updateModel.setModifyOrgCode(orgCode);
        	   		priceValuationDao.updateValuation(updateModel);
//                }
			}
		}
		// 计费规则ID
		String priceValuationEntityId = UUIDUtils.getUUID();
		// 设置ID
		priceValuationEntity.setId(priceValuationEntityId);
		//如果endTime
		if (priceValuationEntity.getEndTime() == null) {
			// 先设置默认值
			priceValuationEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		}else{
			//根据MANA-1320修改
			// 设置结束日期为23:59:59
			priceValuationEntity.setEndTime(DateUtils.getEndDatetime(priceValuationEntity.getEndTime()));
		}
		// 版本号
		priceValuationEntity.setVersionNo(versionNo);
		// 创建人
		priceValuationEntity.setCreateUser(userCode);
		// 修改人
		priceValuationEntity.setModifyUser(userCode);
		// 币种
		priceValuationEntity.setCurrencyCode(DictionaryValueConstants.SETTLEMENT__CURRENCY_CODE__RMB);
		// 创建部门
		priceValuationEntity.setCreateOrgCode(orgCode);
		// 修改部门
		priceValuationEntity.setModifyOrgCode(orgCode);
		// 新增一条数计费规则
		priceValuationDao.insertSelective(priceValuationEntity);
		// 插入产品类型
		List<PriceProductEntity> productEntities = priceValuationEntity.getProductList();
		if (CollectionUtils.isNotEmpty(productEntities))
			for (PriceProductEntity product : productEntities) {
				product.setTableId(priceValuationEntity.getId());
				product.setTableName(TABLE_NAME_VALUEADD);
				product.setId(UUIDUtils.getUUID());
				priceValuationDao.insertPriceProductEntity(product);
			}
		// 插入货物类类型
		List<PriceIndustryEntity> industryEntities = transformIndustry(priceValuationEntity.getCustomerIndustryEntityList(), priceValuationEntity.getId());
		if (CollectionUtils.isNotEmpty(industryEntities)){
			for (PriceIndustryEntity industry : industryEntities) {
				industry.setTableId(priceValuationEntity.getId());
				industry.setTableName(TABLE_NAME_VALUEADD);
				industry.setId(UUIDUtils.getUUID());
				priceValuationDao.insertPriceIndustryEntity(industry);
			}
		}
		// 新增计价方式明细
		for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : priceCriteriaDetailEntityList) {
			// 计价方式明细ID
			String priceCriteriaDetailEntityId = UUIDUtils.getUUID();
			priceCriteriaDetailEntity.setActive(active);
			priceCriteriaDetailEntity.setId(priceCriteriaDetailEntityId);
			// 版本号
			priceCriteriaDetailEntity.setVersionNo(versionNo);
			// 价格计算表达式——简单费率规则
			priceCriteriaDetailEntity
					.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
			//当是其他费用和签收回单、超远派送 时采用固定价暂时写死，之后会从缓存中去判断
			if (PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(
					priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT
							.equals(priceValuationEntity.getPricingEntryCode())|| PricingConstants.PriceEntityConstants.PRICING_CODE_CY
							.equals(priceValuationEntity.getPricingEntryCode())) {
				// 价格计算表达式——固定价
				priceCriteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_FIXED_ID);
			}
			
			/**
			 * 修改此处代码，增加大件上楼费
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-12-29上午12:49
			 */
			if (PricingConstants.PriceEntityConstants.PRICING_CODE_SHSL.equals(priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_CCF.equals(priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT.equals(priceValuationEntity.getPricingEntryCode())
					|| PricingConstants.PriceEntityConstants.PRICING_CODE_DJSL.equals(priceValuationEntity.getPricingEntryCode())) {
				// 大件上楼费、送货上楼、送货费、接货费、包装费、仓储费、其它费用  费率乘100
				if(priceCriteriaDetailEntity.getFeeRate()!=null){
					priceCriteriaDetailEntity.setFeeRate(priceCriteriaDetailEntity.getFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
				}
				if(priceCriteriaDetailEntity.getMaxFeeRate()!=null){
					priceCriteriaDetailEntity.setMaxFeeRate(priceCriteriaDetailEntity.getMaxFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
				}
				if(priceCriteriaDetailEntity.getMinFeeRate()!=null){
					priceCriteriaDetailEntity.setMinFeeRate(priceCriteriaDetailEntity.getMinFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
				}
			} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(priceValuationEntity.getPricingEntryCode())) {
				//代收货款左右区间乘以100
				priceCriteriaDetailEntity.setLeftrange(priceCriteriaDetailEntity.getLeftrange().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
				priceCriteriaDetailEntity.setRightrange(priceCriteriaDetailEntity.getRightrange().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
			}
			// 计费规则ID
			priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntityId);
			priceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntity);
			/**
			 * 将创建时间，修改时间，计价规则表ID,计价方式明细表ID包括PriceFibelPaperPackingEntity存入表
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-11-14下午15:55
			 */
			if(priceCriteriaDetailEntity.getPriceFibelPaperPackingEntity()!=null){
				PriceFibelPaperPackingEntity priceFibelPaperPackingEntity=priceCriteriaDetailEntity.getPriceFibelPaperPackingEntity();
				priceFibelPaperPackingEntity.setValuationId(priceValuationEntity.getId());
				priceFibelPaperPackingEntity.setCritcriaDetailId(priceCriteriaDetailEntity.getId());
				priceFibelPaperPackingEntity.setCreateDate(priceValuationEntity.getCreateDate());
				priceFibelPaperPackingEntity.setModifyDate(priceValuationEntity.getModifyDate());
				fibelPaperPackingUnitPriceDao.insertPricingFibelPaper(priceFibelPaperPackingEntity);
			}
		}
	}
	/**
	 *  从生效时间,业务发生时间,所属增值服务,区域,基础产品和货物类型几个维度判断是否重复
	 *  重复返回false,不重复返回true
	 */
	private boolean checkRepartByRecord(PriceValuationEntity priceValuationEntity,PriceValuationEntity other) {
		//传入的基础产品
		List<PriceProductEntity> productList = priceValuationEntity.getProductList();
		//查询出的基础产品
		List<PriceProductEntity> procudtListTemp = priceValuationDao.queryProductListByTableId(other.getId());
		if(CollectionUtils.isNotEmpty(procudtListTemp)
				&&CollectionUtils.isNotEmpty(productList)
				&&!CollectionUtils.containsAny(procudtListTemp, productList)){
			return true;
		}
		//传入的货物类型
		List<CustomerIndustryEntity> industryEntityList = priceValuationEntity.getCustomerIndustryEntityList();
		//查询出的货物类型
		List<CustomerIndustryEntity> industryEntityListTemp = popValueAddedService.queryAllSecProfession(other.getId());
		if(CollectionUtils.isNotEmpty(industryEntityListTemp)
			&&CollectionUtils.isNotEmpty(industryEntityList)
			&&!CollectionUtils.containsAny(industryEntityListTemp, industryEntityList)){
			return true;
		}
		return false;
	}
	/**
	 * .
	 * <p>
	 * 查询增值服务类型<br/>
	 * 方法名：searchValueAddedType
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	public List<PriceEntity> searchValueAddedType(PriceEntity priceEntity) {
		return pricingValueAddedDao.searchValueAddedType(priceEntity);
	}
	/**
	 * .
	 * <p>
	 * 根据ID查询计价方式明细<br/>
	 * 方法名：searchCriteriaDetailById
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-10-20
	 * 
	 * @since JDK1.6
	 */
	@Override
	public List<PriceCriteriaDetailEntity> selectByValuationId(
			String valuationId) {
		return priceCriteriaDetailDao.selectByValuationId(valuationId);
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件查询计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-20 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	public List<PriceValuationEntity> selectByCodition(
			PriceValuationEntity record, int start, int limit) {
		if (PricingConstants.ALL.equals(record.getPricingEntryCode())) {
			record.setPricingEntryCode(null);
		}
		if(record.getBeginTime() != null) {
			Date beginTime = new Date(record.getBeginTime().getTime());
			Date endTime = new Date(record.getBeginTime().getTime());
			record.setBeginTime(beginTime);
			record.setEndTime(endTime);
		}
		List<PriceValuationEntity> priceValuationEntityList = priceValuationDao.selectByCoditionNew(record, start, limit);
		if(CollectionUtils.isNotEmpty(priceValuationEntityList)){
			for(PriceValuationEntity priceValuationEntity:priceValuationEntityList){
				if(StringUtil.isNotBlank(priceValuationEntity.getModifyUser())){
					EmployeeEntity employeeEntity= employeeService.queryEmployeeByEmpCode(priceValuationEntity.getModifyUser());//查询修改人信息
					if(employeeEntity!=null){
						//设置修改人姓名
						priceValuationEntity.setModifyUserName(employeeEntity.getEmpName());
					}
				}
			}
		}
		if(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED.equals(record.getType())){
			for(PriceValuationEntity priceValuationEntity:priceValuationEntityList){
				if(StringUtil.isNotBlank(priceValuationEntity.getArrvRegionId())){
					if(PricingConstants.ALL.equalsIgnoreCase(priceValuationEntity.getArrvRegionId())){
						//设置到达区域名称
						priceValuationEntity.setArrvRegionName(PricingConstants.ALL_REGION_NAME);
					}else{
						PriceRegionValueAddEntity priceRegionEntity = regionValueAddService.searchRegionByID(priceValuationEntity.getArrvRegionId(), PricingConstants.VALUEADD_REGION);
						if(priceRegionEntity!=null){
							//设置到达区域名称
							priceValuationEntity.setArrvRegionName(priceRegionEntity.getRegionName());
						}
					}
				}
				if(StringUtil.isNotBlank(priceValuationEntity.getDeptRegionId())){
					if(PricingConstants.ALL.equalsIgnoreCase(priceValuationEntity.getDeptRegionId())){
						//设置到达区域名称
						priceValuationEntity.setDeptRegionName(PricingConstants.ALL_REGION_NAME);
					}else{
						PriceRegionValueAddEntity priceRegionEntity = regionValueAddService.searchRegionByID(priceValuationEntity.getDeptRegionId(), PricingConstants.VALUEADD_REGION);
						if(priceRegionEntity!=null){
							//设置出发区域名称
							priceValuationEntity.setDeptRegionName(priceRegionEntity.getRegionName());
						}
					}
				}
			}
		}
		return priceValuationEntityList;
	}
	/**
	 * 
	 * <p>
	 * 根据查询条件统计计费规则
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-20 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	public Long countByCodition(PriceValuationEntity record) {
		return priceValuationDao.countByCodition(record);
	}
	/**
	 * 
	 * <p>
	 * (激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-22 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void activeValueAdded(List<String> valuationIds) {
		// 激活之后修改相关的数据
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 查询所有要激活的计费规则ID条件
		PriceValuationEntity searchAllModel = new PriceValuationEntity();
		searchAllModel.setValuationIds(valuationIds);
		// 查询所有要激活的计费规则ID条件
		List<PriceValuationEntity> searchResultAllList = priceValuationDao
				.selectByCodition(searchAllModel);
		for (PriceValuationEntity priceValuationEntity : searchResultAllList) 
		{
			Date endTime = new Date(priceValuationEntity.getBeginTime()
				.getTime() - PricingConstants.ONE_MILLISECOND);
			//当天的0点00分00秒
			Date nowDate = DateUtils.convert(DateUtils.convert(new Date(),DateUtils.DATE_FORMAT),DateUtils.DATE_FORMAT);
			if(priceValuationEntity.getBeginTime().getTime()<(nowDate.getTime()+PricingConstants.ONE_MILLISECOND)){
				throw new PricingCommonException("区域名称为："+priceValuationEntity.getName()+"的生效日期小于明天",null);
			}
			priceValuationEntity.setActive(FossConstants.ACTIVE);
			//设入货物类型
			priceValuationEntity.setCustomerIndustryEntityList(popValueAddedService.queryAllSecProfession(priceValuationEntity.getId()));
			//设入基础产品
			priceValuationEntity.setProductList(priceValuationDao.selectPriceProductEntityByValueAddedId(
					priceValuationEntity.getId(), TABLE_NAME_VALUEADD));
			// 查询原来的计费规则
			PriceValuationEntity searchModel = new PriceValuationEntity();
			// 设置长短途
			searchModel.setLongOrShort(priceValuationEntity.getLongOrShort());
			// 设置类型
			searchModel.setType(priceValuationEntity.getType());
			searchModel.setActive(FossConstants.ACTIVE);
			searchModel.setPricingEntryCode(priceValuationEntity.getPricingEntryCode());
			// 设置查询开始日期
			searchModel.setBeginTime(priceValuationEntity.getBeginTime());
			// 设置业务结束时间
			searchModel.setEndTime(priceValuationEntity.getEndTime());
			searchModel.setDeptRegionId(priceValuationEntity.getDeptRegionId());
			searchModel.setArrvRegionId(priceValuationEntity.getArrvRegionId());
			searchModel.setGoodsTypeId(priceValuationEntity.getGoodsTypeId());
			searchModel.setProductCode(priceValuationEntity.getProductCode());	
			searchModel.setBusinessBeginTime(priceValuationEntity.getBusinessBeginTime());
			searchModel.setBusinessEndTime(priceValuationEntity.getBusinessEndTime());
			// 查询出符合条件的的版本
			List<PriceValuationEntity> searchResultList = priceValuationDao.selectByCoditionSq(searchModel);
			if(!CollectionUtils.isEmpty(searchResultList)){
				for(PriceValuationEntity priceValuationEntityModel:searchResultList){
					//校验区域是否重复
					if(checkRepartByRecord(priceValuationEntity, priceValuationEntityModel)){
						continue;
					}
					PriceValuationEntity updateModel = new PriceValuationEntity();
					// 设置版本号
					updateModel.setVersionNo(versionNo);
					// 设置ID
					updateModel.setId(priceValuationEntityModel.getId());
					updateModel.setEndTime(endTime);
					updateModel.setModifyDate(new Date());
					updateModel.setModifyUser(userCode);
					updateModel.setModifyOrgCode(orgCode);
					// 修改对应低版本数据
					priceValuationDao.updateValuation(updateModel);
				}
			}
    	}
		priceValuationDao.activeValueAdded(valuationIds);
		priceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
	}
	/**
	 * 
	 * <p>
	 * (立即激活激活增值服务)
	 * </p>
	 * 
	 * @author panGuoYang
	 * 
	 * @date 2013-02-25 上午11:15:04
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void activeFast(PriceValuationEntity priceValuationEntity) {
		// 激活之后修改相关的数据
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		if(null==priceValuationEntity || priceValuationEntity.getId() == null){
		    throw new PricingCommonException("激活参数为空!","激活参数为空!");
		}
		if(null == priceValuationEntity.getBeginTime()){
		    throw new PricingCommonException("激活生效时间参数为空!","激活生效时间参数为空!");
		}
		String id = priceValuationEntity.getId();
		List<String> valuationIds = new ArrayList<String>();
		valuationIds.add(id);
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		
		//修改激活日期,组织机构,登录操作用户信息
		PriceValuationEntity tempPriceValuationEntity = priceValuationDao.selectByPrimaryKey(id);
		/**
		 * 判断同出发区域、到达区域的相同时间里是否有有效的增值方案
		 */
		PriceValuationEntity seletpriceValuationEntity =new PriceValuationEntity();
		//开始时间
		seletpriceValuationEntity.setBeginTime(priceValuationEntity.getBeginTime());
		//结束时间
		seletpriceValuationEntity.setEndTime(tempPriceValuationEntity.getEndTime());
		//出发区域
		seletpriceValuationEntity.setDeptRegionId(tempPriceValuationEntity.getDeptRegionId());
		//到付区域
		seletpriceValuationEntity.setArrvRegionId(tempPriceValuationEntity.getArrvRegionId());
		//费用类型
		seletpriceValuationEntity.setPricingEntryCode(tempPriceValuationEntity.getPricingEntryCode());
		//类型
		seletpriceValuationEntity.setType(tempPriceValuationEntity.getType());
		//有效
		seletpriceValuationEntity.setActive(FossConstants.YES);
		List<PriceValuationEntity> valuationEntitys = priceValuationDao.queryByValuation(seletpriceValuationEntity);
		
		if (CollectionUtils.isNotEmpty(valuationEntitys)) {
				
			//基础增值
			if(PricingConstants.VALUATION_TYPE_BASICVALUEADDED.equals(tempPriceValuationEntity.getType())){
				/**
				 * 是否有相同数据
				 */
				Map<String, String> vaddMap = new HashMap<String, String>();
				for (PriceValuationEntity valuationEntity : valuationEntitys) {
					//货物类型
					List<CustomerIndustryEntity> industryEntitys = popValueAddedService.queryAllSecProfession(valuationEntity.getId());
					if (CollectionUtils.isNotEmpty(industryEntitys)) {
						for (CustomerIndustryEntity industryEntity : industryEntitys) {
							vaddMap.put(valuationEntity.getId()+" "+industryEntity.getId(),industryEntity.getProfessionCode());
						}
					}else{
						vaddMap.put(valuationEntity.getId(),"ALL");
					}
				}
				
				/**
				 * 货物类型是否相同
				 */
				List<CustomerIndustryEntity> industryEntitys = popValueAddedService.queryAllSecProfession(tempPriceValuationEntity.getId());
				if(CollectionUtils.isNotEmpty(industryEntitys)){
					for(CustomerIndustryEntity industryEntity:industryEntitys){
						String professionCode =industryEntity.getProfessionCode();
						if(vaddMap.size()>0 ){
							Iterator<String>  iterator = vaddMap.keySet().iterator();
							while(iterator.hasNext()){
								String idKey =iterator.next();
								String fcode =vaddMap.get(idKey);
								if(professionCode.equals(fcode) || "ALL".equals(fcode)){
									String [] ary =idKey.split(" ");
									PriceValuationEntity updateModel = new PriceValuationEntity();
					            	// 设置版本号
									updateModel.setVersionNo(versionNo);
									// 设置ID
									updateModel.setId(ary[0]);
									updateModel.setEndTime(new Date(priceValuationEntity.getBeginTime().getTime()-PricingConstants.ONE_MILLISECOND));
									updateModel.setModifyDate(new Date());
									updateModel.setModifyUser(userCode);
									updateModel.setModifyOrgCode(orgCode);
									// 修改对应低版本数据
									priceValuationDao.updateValuation(updateModel);
								}
							}
						}
					}
			   }
					
			}else{
					
					/**
					 * 是否有相同数据
					 */
					Map<String, String> vaddMap = new HashMap<String, String>();
					for (PriceValuationEntity valuationEntity : valuationEntitys) {
						List<PriceProductEntity> temproducts = priceValuationDao
								.queryProductListByTableId(valuationEntity.getId());
						if (CollectionUtils.isNotEmpty(temproducts)) {
							for (PriceProductEntity temproduct : temproducts) {
								vaddMap.put(valuationEntity.getId()+" "+temproduct.getId(),temproduct.getCode());
							}
						}else{
							vaddMap.put(valuationEntity.getId(),"ALL");
						}
					}
					/**
					 *  产品是否相同
					 */
					Map<String, String> productMap = new HashMap<String, String>();
					List<PriceProductEntity> products = priceValuationDao.queryProductListByTableId(tempPriceValuationEntity.getId());
					if (CollectionUtils.isNotEmpty(products)) {
						for (PriceProductEntity product : products) {
							String productCode = product.getCode();
							if(vaddMap.size() > 0 ){
								Iterator<String>  iterator = vaddMap.keySet().iterator(); 
								while(iterator.hasNext()){
									String idKey= iterator.next();
									String mapProductCode = vaddMap.get(idKey);
									if(productCode.equals(mapProductCode)|| "ALL".equals(mapProductCode)){
										//PriceValuationEntity valuationEntity =(PriceValuationEntity) vaddMap.get(mapProductCode);
										String [] ary =idKey.split(" ");
										List<CustomerIndustryEntity> industryEntitys = popValueAddedService.queryAllSecProfession(ary[0]);
										if(CollectionUtils.isNotEmpty(industryEntitys)){
											for(CustomerIndustryEntity industryEntity:industryEntitys ){
												productMap.put(ary[0]+" "+industryEntity.getId(),industryEntity.getProfessionCode());
											}
										}else{
											productMap.put(ary[0],"ALL");
										}
										
									}
							  }
								
						  }
							
					}
				}
				/**
				 * 货物类型是否相同
				 */
				
				//货物类型
				List<CustomerIndustryEntity> industryEntitys = popValueAddedService.queryAllSecProfession(tempPriceValuationEntity.getId());
				if(CollectionUtils.isNotEmpty(industryEntitys)){
					for(CustomerIndustryEntity industryEntity:industryEntitys){
						String professionCode =industryEntity.getProfessionCode();
						if(productMap.size()>0 ){
							Iterator<String>  iterator = productMap.keySet().iterator();
							while(iterator.hasNext()){
								String idKey =iterator.next();
								String fcode =productMap.get(idKey);
								if(professionCode.equals(fcode) || "ALL".equals(fcode)){
									String [] ary =idKey.split(" ");
									PriceValuationEntity updateModel = new PriceValuationEntity();
					            	// 设置版本号
									updateModel.setVersionNo(versionNo);
									// 设置ID
									updateModel.setId(ary[0]);
									updateModel.setEndTime(new Date(priceValuationEntity.getBeginTime().getTime()-PricingConstants.ONE_MILLISECOND));
									updateModel.setModifyDate(new Date());
									updateModel.setModifyUser(userCode);
									updateModel.setModifyOrgCode(orgCode);
									// 修改对应低版本数据
									priceValuationDao.updateValuation(updateModel);
								}
							}
						}
					}
			   }
				
		  }
				
		}
		
		
		/**
		 * 激活增值服务方案
		 */
		tempPriceValuationEntity.setVersionNo(new Date().getTime());
		tempPriceValuationEntity.setModifyDate(new Date());
		tempPriceValuationEntity.setModifyOrgCode(orgCode);
		tempPriceValuationEntity.setModifyUser(userCode);
		tempPriceValuationEntity.setBeginTime(priceValuationEntity.getBeginTime());
		tempPriceValuationEntity.setActive(FossConstants.YES);
		priceValuationDao.updateValuation(tempPriceValuationEntity);
		
		
		/**
		 * 激活明细
		 */
		priceCriteriaDetailDao.activeCriteriaDetailByValuationIds(valuationIds);
	}
	/**
	 * 
	 * <p>
	 * (删除未激活增值服务)
	 * </p>
	 * 
	 * @author 张斌
	 * 
	 * @date 2012-10-25 上午11:15:04
	 * 
	 * @param record
	 * 
	 * @see
	 */
	@Override
	@Transactional
	public void deleteValueAdded(List<String> valuationIds) {
		// 删除计费规则
		priceValuationDao.deleteValueAdded(valuationIds);
		// 删除计价方式明细
		priceCriteriaDetailDao.deleteCriteriaDetailByValuationIds(valuationIds);
		/**
		 * 将对应的纸纤包装单价表删除
		 * @author:218371-foss-zhaoyanjun
		 * @date:2014-11-24
		 */
		fibelPaperPackingUnitPriceDao.deletePricingFibelPaper(valuationIds);
	}
	/**
	 * .
	 * <p>
	 * 修改增值服务<br/>
	 * 方法名：updateValueAdded
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 *            
	 * @param addPriceCriteriaDetailEntityList
	 *            新增计价方式明细实体
	 *            
	 * @param updatePriceCriteriaDetailEntityList
	 *            修改计价方式明细实体
	 *            
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	@Transactional
	public void updateValueAdded(PriceValuationEntity priceValuationEntity,
			List<PriceCriteriaDetailEntity> addPriceCriteriaDetailEntityList,
			List<PriceCriteriaDetailEntity> updatePriceCriteriaDetailEntityList,
			List<PriceCriteriaDetailEntity> deletePriceCriteriaDetailEntityList) {
		if(isTheSameValueAddName(priceValuationEntity, true)){
			throw new PricingCommonException("增值方案名称已存在!","增值方案名称已存在!");
		}
		// 版本号
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if(user==null){
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN,PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		if (FossConstants.ACTIVE.equals(priceValuationEntity.getActive())) {
			//应该被修改的数据的截止日期
			Date endTime = new Date(priceValuationEntity.getBeginTime().getTime() - PricingConstants.ONE_MILLISECOND);
			PriceValuationEntity searchModel = new PriceValuationEntity();
			//出发区域
			searchModel.setDeptRegionId(priceValuationEntity.getDeptRegionId());
			//到达区域
			searchModel.setArrvRegionId(priceValuationEntity.getArrvRegionId());
			// 设置长短途
			searchModel.setLongOrShort(priceValuationEntity.getLongOrShort());
			// 设置类型
			searchModel.setType(priceValuationEntity.getType());
			//有效数据
			searchModel.setActive(FossConstants.ACTIVE);
			//业务类型
			searchModel.setPricingEntryCode(priceValuationEntity.getPricingEntryCode());
			//货物
			searchModel.setGoodsTypeId(priceValuationEntity.getGoodsTypeId());
			//产品
			searchModel.setProductCode(priceValuationEntity.getProductCode());			
			// 设置查询开始日期
			searchModel.setBeginTime(priceValuationEntity.getBeginTime());
			// 设置查询结束日期
			searchModel.setEndTime(priceValuationEntity.getEndTime());
			//业务发生时间
			searchModel.setBusinessBeginTime(priceValuationEntity.getBusinessBeginTime());
			//业务中止时间
			searchModel.setBusinessEndTime(priceValuationEntity.getBusinessEndTime());
			//根据规则类型,开始时间
			List<PriceValuationEntity> searchResultList = priceValuationDao.selectByCoditionSq(searchModel);
			for(PriceValuationEntity priceValuationEntityModel : searchResultList){
			    //判断货物类型,基础产品是否冲突
				if(checkRepartByRecord(priceValuationEntity, priceValuationEntityModel)){
					continue;
				}
//				{定价项目}去掉针对保费的限保物品的特殊判断,我怕后面可能会恢复,所有只是注释,由于限保物品可以为空,可能会报空指针异常,恢复时请注意
//				//针对保费特殊处理
//			    //根据已经重复的计费规则中找出数据库中的保费限保物品与当前所维护的限宝物品是否有冲突,如果限保物品一致,
//			    //则修改上一个计费规则的截止日期为当前日期的前一天
//			    if(StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,
//			    		priceValuationEntityModel.getPricingEntryCode())) {
//            		List<PriceCriteriaDetailEntity> oldPriceCriteriaDetailEntityList  
//            			= priceCriteriaDetailDao.selectByValuationId(priceValuationEntityModel.getId());
//            		if(CollectionUtils.isNotEmpty(oldPriceCriteriaDetailEntityList)) {
//            		    //如果vo中的限保物品在数据库中已经存在,则修改上一个版本截止日期
//            			PriceCriteriaDetailEntity priceCriteriaDetailEntityList = null; 
//            			if(CollectionUtils.isNotEmpty(addPriceCriteriaDetailEntityList)){
//            				priceCriteriaDetailEntityList = addPriceCriteriaDetailEntityList.get(0);
//            			}else if(CollectionUtils.isNotEmpty(updatePriceCriteriaDetailEntityList)){
//            				priceCriteriaDetailEntityList = updatePriceCriteriaDetailEntityList.get(0);
//            			}else{
//            				List<PriceCriteriaDetailEntity> tempList = priceCriteriaDetailDao.selectByValuationId(priceValuationEntity.getId());
//            				if(CollectionUtils.isEmpty(tempList)){
//            					continue;
//            				}
//            				priceCriteriaDetailEntityList = tempList.get(0);
//            			}
//            		    if(StringUtil.isEmpty(priceCriteriaDetailEntityList.getSubType())
//        		    		||priceCriteriaDetailEntityList.getSubType().equalsIgnoreCase(
//        		    				oldPriceCriteriaDetailEntityList.get(0).getSubType())) {
//            		    	PriceValuationEntity updateModel = new PriceValuationEntity();
//	            			updateModel.setVersionNo(versionNo);
//	            			updateModel.setId(priceValuationEntityModel.getId());
//	            			updateModel.setEndTime(endTime);
//	            			updateModel.setModifyDate(new Date());
//	            			updateModel.setModifyUser(userCode);
//	            			updateModel.setModifyOrgCode(orgCode);
//	            			priceValuationDao.updateValuation(updateModel);
//            		    }
//            		}
//                }else{
                	// 修改对应低版本数据
	            	PriceValuationEntity updateModel = new PriceValuationEntity();
        	   		updateModel.setVersionNo(versionNo);
        	   		updateModel.setId(priceValuationEntityModel.getId());
        	   		updateModel.setEndTime(endTime);
        	   		updateModel.setModifyDate(new Date());
        	   		updateModel.setModifyUser(userCode);
        	   		updateModel.setModifyOrgCode(orgCode);
        	   		priceValuationDao.updateValuation(updateModel);
//                }
			}
		}
		// 该计费规则是否激活
		String active = priceValuationEntity.getActive();
		// 计价方式ID
		String priceCriteriaEntityId = priceValuationEntity.getCriteriaId();
		// 修改部门
		priceValuationEntity.setModifyOrgCode(orgCode);
		// 修改人
		priceValuationEntity.setModifyUser(userCode);
		priceValuationEntity.setVersionNo(new Date().getTime());
		//根据MANA-1320修改
		if (priceValuationEntity.getEndTime() != null) {			
			// 设置截止日期为23:59:59
			priceValuationEntity.setEndTime(DateUtils.getEndDatetime(priceValuationEntity.getEndTime()));
		}
		priceValuationDao.updateValuation(priceValuationEntity);
		// 查询产品
		List<PriceProductEntity> oldProductEntities = priceValuationDao.selectPriceProductEntityByValueAddedId(
				priceValuationEntity.getId(), TABLE_NAME_VALUEADD);
		// 查询产品类型
		List<PriceIndustryEntity> oldIndustryEntities = priceValuationDao.selectPriceIndustryEntityByValueAddedId(
				priceValuationEntity.getId(), TABLE_NAME_VALUEADD);
		List<PriceProductEntity> productEntities = priceValuationEntity.getProductList();
		// 插入货物类类型
//				List<PriceIndustryEntity> industryEntities = priceValueAddedEntity.getIndustryList();
		List<PriceIndustryEntity> industryEntities = transformIndustry(priceValuationEntity.getCustomerIndustryEntityList(), priceValuationEntity.getId());
		// 如果新旧产品列表不相等 先删除再插入
		// 插入产品类型
		if (CollectionUtils.isNotEmpty(oldProductEntities)){
			for (PriceProductEntity product : oldProductEntities) {
				priceValuationDao.deleteProductEntityById(product.getId());
			}
		}
		// 插入产品类型
		if (CollectionUtils.isNotEmpty(productEntities)){
			for (PriceProductEntity product : productEntities) {
				product.setTableId(priceValuationEntity.getId());
				product.setTableName(TABLE_NAME_VALUEADD);
				product.setId(UUIDUtils.getUUID());
				priceValuationDao.insertPriceProductEntity(product);
			}
		}
				// 删除产品类型
		if (CollectionUtils.isNotEmpty(oldIndustryEntities)){
			for (PriceIndustryEntity industry : oldIndustryEntities) {
				priceValuationDao.deleteIndustryEntityById(industry.getId());
			}
		}
		// 插入行业列表
		if (CollectionUtils.isNotEmpty(industryEntities)){
			for (PriceIndustryEntity industry : industryEntities) {
				industry.setTableId(priceValuationEntity.getId());
				industry.setTableName(TABLE_NAME_VALUEADD);
				industry.setId(UUIDUtils.getUUID());
				priceValuationDao.insertPriceIndustryEntity(industry);
			}
		}
		if (addPriceCriteriaDetailEntityList != null && addPriceCriteriaDetailEntityList.size() > PricingConstants.ZERO) {
			// 新增计价方式明细
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : addPriceCriteriaDetailEntityList) {
				String priceCriteriaDetailEntityId = UUIDUtils.getUUID();
				priceCriteriaDetailEntity.setActive(active);
				priceCriteriaDetailEntity.setId(priceCriteriaDetailEntityId);
				priceCriteriaDetailEntity.setVersionNo(versionNo);
				priceCriteriaDetailEntity.setPricingCriteriaId(priceCriteriaEntityId);
				// 价格计算表达式——简单费率规则
				priceCriteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
				//当是其他费用和签收回单、超远派送 时采用固定价暂时写死，之后会从缓存中去判断
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(
						priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT
								.equals(priceValuationEntity.getPricingEntryCode())|| PricingConstants.PriceEntityConstants.PRICING_CODE_CY
								.equals(priceValuationEntity.getPricingEntryCode())) {
					// 价格计算表达式——固定价
					priceCriteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_FIXED_ID);
				}
				
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_SHSL.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_CCF.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_DJSL.equals(priceValuationEntity.getPricingEntryCode())) {
					if(priceCriteriaDetailEntity.getFeeRate()!=null){
						priceCriteriaDetailEntity.setFeeRate(priceCriteriaDetailEntity.getFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					}
					if(priceCriteriaDetailEntity.getMaxFeeRate()!=null){
						priceCriteriaDetailEntity.setMaxFeeRate(priceCriteriaDetailEntity.getMaxFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					}
					if(priceCriteriaDetailEntity.getMinFeeRate()!=null){
						priceCriteriaDetailEntity.setMinFeeRate(priceCriteriaDetailEntity.getMinFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					}
					
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(priceValuationEntity.getPricingEntryCode())) {
					priceCriteriaDetailEntity.setLeftrange(priceCriteriaDetailEntity.getLeftrange().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					priceCriteriaDetailEntity.setRightrange(priceCriteriaDetailEntity.getRightrange().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
				}
				priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
				priceCriteriaDetailDao.insertSelective(priceCriteriaDetailEntity);
			}
		}
		if (updatePriceCriteriaDetailEntityList != null
				&& updatePriceCriteriaDetailEntityList.size() > PricingConstants.ZERO) {
			// 修改的计价方式明细
			for (PriceCriteriaDetailEntity priceCriteriaDetailEntity : updatePriceCriteriaDetailEntityList) {
				if (PricingConstants.PriceEntityConstants.PRICING_CODE_SHSL.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_CCF.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT.equals(priceValuationEntity.getPricingEntryCode())
						|| PricingConstants.PriceEntityConstants.PRICING_CODE_DJSL.equals(priceValuationEntity.getPricingEntryCode())) {
					if(priceCriteriaDetailEntity.getFeeRate()!=null){
						priceCriteriaDetailEntity.setFeeRate(priceCriteriaDetailEntity.getFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					}
					if(priceCriteriaDetailEntity.getMaxFeeRate()!=null){
						priceCriteriaDetailEntity.setMaxFeeRate(priceCriteriaDetailEntity.getMaxFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					}
					if(priceCriteriaDetailEntity.getMinFeeRate()!=null){
						priceCriteriaDetailEntity.setMinFeeRate(priceCriteriaDetailEntity.getMinFeeRate().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					}
				} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(priceValuationEntity.getPricingEntryCode())) {
					priceCriteriaDetailEntity.setLeftrange(priceCriteriaDetailEntity.getLeftrange().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
					priceCriteriaDetailEntity.setRightrange(priceCriteriaDetailEntity.getRightrange().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
				}
				priceCriteriaDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
				/**
				 * 增加判断是否是纸纤包装修改
				 * @author:218371-foss-zhaoyanjun
				 * @date:2014-11-24下午17:19
				 */
				if(priceCriteriaDetailEntity.getPriceFibelPaperPackingEntity()!=null){
					PriceFibelPaperPackingEntity priceFibelPaperPackingEntity=priceCriteriaDetailEntity.getPriceFibelPaperPackingEntity();
					priceFibelPaperPackingEntity.setModifyDate(priceCriteriaDetailEntity.getModifyDate());
					priceFibelPaperPackingEntity.setCritcriaDetailId(priceCriteriaDetailEntity.getId());
					fibelPaperPackingUnitPriceDao.updatePricingFibelPaper(priceFibelPaperPackingEntity);
				}
			}
		} else {
			//只修改限保物品，不修改明细
			if (StringUtils.isNotEmpty(priceValuationEntity.getSubType()) 
					&& StringUtils.isNotEmpty(priceValuationEntity.getId())) {
				PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
				priceCriteriaDetailEntity.setSubType(priceValuationEntity.getSubType());
				priceCriteriaDetailEntity.setPricingValuationId(priceValuationEntity.getId());
				priceCriteriaDetailDao.updateCriteriaDetailByPricingValuationId(priceCriteriaDetailEntity);
			}
		}
		if (deletePriceCriteriaDetailEntityList != null && 
				deletePriceCriteriaDetailEntityList.size() > PricingConstants.ZERO) {
			for (PriceCriteriaDetailEntity dto : deletePriceCriteriaDetailEntityList) {
				priceCriteriaDetailDao.deleteByPrimaryKey(dto.getId());
			}
		}
	}
	/**
	 * 
	 * 查询产品条目信息-
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductItemEntity> findProductItemByCondiction() {
		ProductItemEntity productItem = new ProductItemEntity();
		// 查询激活的
		productItem.setActive(FossConstants.ACTIVE);
		return productItemService.findProductItemByCondiction(productItem);
	}
	/**
	 * 
	 * 查询基础产品信息(三级产品)
	 * 
	 * @author DP-Foss-张斌
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findProductByCondition() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		condtion.setCode("ECONOMIC_EXPRESS");//经济快递
		return productService.findExternalProductByCondition(condtion, null);
	}
	 /**
     * 
     * 查询货物定义信息
     * @author DP-Foss-张斌
     * @date 2012-10-30 下午5:13:38
     */
	@Override
	public List<GoodsTypeEntity> findGoodsTypeByCondiction() {
		GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
		// 查询激活的
		goodsTypeEntity.setActive(FossConstants.ACTIVE);
		return goodsTypeService.findGoodsTypeByCondiction(goodsTypeEntity);
	}
	/**
     * 
     * 根据不同条件查询增值服务计价规则
     * 
     * 各种增值服务计价规则查询： 基础增值服务，产品增值服务，区域增值服务
     * 
     * @author DP-Foss-YueHongJie
     * 
     * @date 2012-11-3 下午2:28:56
     */
	@Override
	public List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
	    List<ResultProductPriceDto> regionEntityList = priceValuationDao.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	    return regionEntityList;
	}
	/**
	 * 
	 * （筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
	 * 提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法
	 * 
	 * ）
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 下午12:52:16
	 * 
	 * @param queryBillCacilateValueAddDto
	 *            计费规则与费用明细的组合查询条件
	 *            
	 */
	@Override
	public List<ResultProductPriceDto> siftValuationBillingRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto){
	    String oldGoodsTypeCode=queryBillCacilateValueAddDto.getGoodsTypeCode();
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	    queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
	  //区域增值
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
	    String longOrShort=queryBillCacilateValueAddDto.getLongOrShort();
	    queryBillCacilateValueAddDto.setLongOrShort(null);	    
	    List<ResultProductPriceDto> entityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	    entityList=filterGoodsType(entityList,oldGoodsTypeCode);
	    entityList=filterLongORshort(entityList,longOrShort);
	    //筛选合适的区域增值服务
	    entityList = filterBestMapEntity(entityList);
	    if(!CollectionUtils.isEmpty(entityList)){
		 queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);		
		return entityList;
	    }
	    queryBillCacilateValueAddDto.setDeptRegionId(null);
	    queryBillCacilateValueAddDto.setArrvRegionId(null);
	    queryBillCacilateValueAddDto.setAllNet(null);
	  //产品区域增值
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED);
	    entityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	    entityList=filterGoodsType(entityList,oldGoodsTypeCode);
	    entityList=filterLongORshort(entityList,longOrShort);
	    if(!CollectionUtils.isEmpty(entityList)){
		 queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
		return entityList;
	    }
	    queryBillCacilateValueAddDto.setProductCode(null);
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	  //基础增值	   
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
	    entityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);    
	    entityList=filterLongORshort(entityList,longOrShort);
	    if(!CollectionUtils.isEmpty(entityList)){
		 queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
		 return entityList;
	    }
	    queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
	    return null;
	}
	/**
	 * 
	 * （筛选计费规则 - 根据条件按照异常优先级找出最合理的计费规则下的计费明细信息,优先原则为【区域增值服务】->【产品增值服务】-【基础增值服务】
	 * 提供给计算增值服务接口中(IBillCaculateService.searchValueAddPriceList) - 用到该放方法
	 * 
	 * ）
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 下午12:52:16
	 * 
	 * @param queryBillCacilateValueAddDto
	 *            计费规则与费用明细的组合查询条件
	 *            
	 */
	@Override
	public Map<String, List<ResultProductPriceDto>> siftValueAddRuleService(QueryBillCacilateValueAddDto queryBillCacilateValueAddDto){
		/**
		 * 查询区域增值服务
		 */
		String oldGoodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
		String oldProductCode = queryBillCacilateValueAddDto.getProductCode();
		String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		String customerCode = queryBillCacilateValueAddDto.getCustomerCode();
	 // 去除相关查询条件
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setProductCode(null);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		queryBillCacilateValueAddDto.setCustomerCode(customerCode);
	    //基础增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_CUSTOMERVALUEADDED); 
	    List<ResultProductPriceDto> customerEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
	 //  根据产品筛选
	    customerEntityList = filterProduct(customerEntityList, oldProductCode);
	 	// 根据货物类型筛选
	    customerEntityList = filterGoodsType(customerEntityList, oldGoodsTypeCode);
	 	// 根据长短途筛选
//	 		regionEntityList = filterLongORshort(regionEntityList, longOrShort);
	 	// 根据区域途筛选
	    customerEntityList = filterBestMapEntity(customerEntityList);
		// 去除相关查询条件
		queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setProductCode(null);
		queryBillCacilateValueAddDto.setCustomerCode(null);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		// 区域增值标识
		queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		// 执行查询操作
		List<ResultProductPriceDto> regionEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		// 筛选合适的区域增值服务
		// 根据产品筛选
		regionEntityList = filterProduct(regionEntityList, oldProductCode);
		// 根据货物类型筛选
		regionEntityList = filterGoodsType(regionEntityList, oldGoodsTypeCode);
		// 根据长短途筛选
//		regionEntityList = filterLongORshort(regionEntityList, longOrShort);
		// 根据区域途筛选
		regionEntityList = filterBestMapEntity(regionEntityList);
		/**
		 * 查询产品增值服务
		 */
		//如果没有合适的区域增值服务，则查询产品增值服务
	    queryBillCacilateValueAddDto.setDeptRegionId(null);
	    queryBillCacilateValueAddDto.setArrvRegionId(null);
	    queryBillCacilateValueAddDto.setAllNet(null);
	    queryBillCacilateValueAddDto.setCustomerCode(null);
	    //产品增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED);
	    //设置产品编号
	    queryBillCacilateValueAddDto.setProductCode(oldProductCode);
	    //此处加入新增查询条件的封装 liyongfei
	    // 执行查询操作
	    List<ResultProductPriceDto> productEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		productEntityList = filterGoodsType(productEntityList, oldGoodsTypeCode);
//		productEntityList = filterLongORshort(productEntityList, longOrShort);
	    /**
		 * 查询基础增值服务
		 */
	    queryBillCacilateValueAddDto.setProductCode(null);
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	    queryBillCacilateValueAddDto.setCustomerCode(null);
	    //基础增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
	    List<ResultProductPriceDto> baseEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);    
//		baseEntityList = filterLongORshort(baseEntityList, longOrShort);
		//根据业务要求组合集合数据
		Map<String, List<ResultProductPriceDto>> resultMap = new HashMap<String, List<ResultProductPriceDto>>();
		if (!CollectionUtils.isEmpty(regionEntityList)) {
			resultMap.put("other", regionEntityList);
		} else if(!CollectionUtils.isEmpty(productEntityList)) {
			resultMap.put("other", productEntityList);
		}
		if(!CollectionUtils.isEmpty(baseEntityList)) {
			resultMap.put("base", baseEntityList);
		}
		if(!CollectionUtils.isEmpty(customerEntityList)) {
			resultMap.put("customer", customerEntityList);
		}
		queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
		queryBillCacilateValueAddDto.setProductCode(oldProductCode);
		queryBillCacilateValueAddDto.setLongOrShort(longOrShort);
		return resultMap;
	}



/**
	 * 
	 * @Description: 过滤产品CODE
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-3-26 下午6:05:22
	 * 
	 * @param entityList
	 * 
	 * @param oldProductCode
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private List<ResultProductPriceDto> filterProduct(List<ResultProductPriceDto> entityList, String oldProductCode) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<ResultProductPriceDto> allList = new ArrayList<ResultProductPriceDto>();
		List<ResultProductPriceDto> singleList = new ArrayList<ResultProductPriceDto>();
		for (int loop = 0; loop < entityList.size(); loop++) {
			ResultProductPriceDto resultObject = entityList.get(loop);
			if (StringUtil.equalsIgnoreCase(resultObject.getProductCode(), oldProductCode)) {
				singleList.add(resultObject);
			}
			if (StringUtil.isEmpty(resultObject.getProductCode())) {
				allList.add(resultObject);
			}
		}
		if (CollectionUtils.isNotEmpty(singleList)) {
			return singleList;
		} else {
			return allList;
		}
	}
	/**
	 * 
	 *	过滤长短途
	 *
	 * @param entityList
	 *  
	 * @param longOrShort 
	 * 
	 * @return 
	 */
	private List<ResultProductPriceDto> filterLongORshort(List<ResultProductPriceDto> entityList, String longOrShort) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<ResultProductPriceDto> allList = new ArrayList<ResultProductPriceDto>();
		List<ResultProductPriceDto> singleList = new ArrayList<ResultProductPriceDto>();
		if (StringUtil.isEmpty(longOrShort)) {
			longOrShort = PricingConstants.ALL;
		}
		for (int loop = 0; loop < entityList.size(); loop++) {
			ResultProductPriceDto resultObject = entityList.get(loop);
			if (StringUtil.equalsIgnoreCase(resultObject.getLongOrShort(), longOrShort)) {
				singleList.add(resultObject);
			}
			if (StringUtil.equalsIgnoreCase(resultObject.getLongOrShort(), PricingConstants.ALL)) {
				allList.add(resultObject);
			}

		}
		if (CollectionUtils.isNotEmpty(singleList)) {
			return singleList;
		} else {
			return allList;
		}
	}
	/**
	 * 
	 * 过滤货物类型
	 * 
	 * @param entityList
	 *  
	 * @param oldGoodsTypeCode 
	 * 
	 * @return 
	 */
	private List<ResultProductPriceDto> filterGoodsType(List<ResultProductPriceDto> entityList, String oldGoodsTypeCode) {
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		List<ResultProductPriceDto> allList = new ArrayList<ResultProductPriceDto>();
		List<ResultProductPriceDto> singleList = new ArrayList<ResultProductPriceDto>();
		if (StringUtil.isEmpty(oldGoodsTypeCode)) {
			oldGoodsTypeCode = GoodsTypeEntityConstants.GOODSTYPE_GENERAL_H00001;
		}
		for (int loop = 0; loop < entityList.size(); loop++) {
			ResultProductPriceDto resultObject = entityList.get(loop);
			if (StringUtil.equalsIgnoreCase(resultObject.getGoodsTypeCode(), oldGoodsTypeCode)) {
				singleList.add(resultObject);
			}
			if (StringUtil.isEmpty(resultObject.getGoodsTypeCode())) {
				allList.add(resultObject);
			}
		}
		if (CollectionUtils.isNotEmpty(singleList)) {
			return singleList;
		} else {
			return allList;
		}
	}
	/**
	 * 
	 * 过滤最优先的区域增值计费规则
	 * 
	 * @author DP-Foss-YueHongJie
	 * 
	 * @date 2012-11-7 下午1:27:13
	 * 
	 */
	private List<ResultProductPriceDto> filterBestMapEntity(List<ResultProductPriceDto> resultProductPriceList) {
		if (CollectionUtils.isEmpty(resultProductPriceList)) {
			return null;
		}
		// 都不全网点数据集合
		List<ResultProductPriceDto> noFullNetWorkList = new ArrayList<ResultProductPriceDto>();
		// 目的地区域为全网点数据集合
		List<ResultProductPriceDto> arrvRegionFullNetworkList = new ArrayList<ResultProductPriceDto>();
		// 始发区域为全网点数据集合
		List<ResultProductPriceDto> deptRegionFullNetWorkList = new ArrayList<ResultProductPriceDto>();
		// 始发区域与目的地区域都为全网点数据集合
		List<ResultProductPriceDto> fullNetWorkList = new ArrayList<ResultProductPriceDto>();
		// 打散数据
		for (ResultProductPriceDto resultProductPriceDto : resultProductPriceList) {
			// 始发区域
			String deptRegionId = resultProductPriceDto.getDeptRegionId();
			// 到达区域
			String arrvRegionId = resultProductPriceDto.getArrvRegionId();
			// 都不全网点
			if (!PricingConstants.ALL.equals(deptRegionId) && !PricingConstants.ALL.equals(arrvRegionId)) {
				noFullNetWorkList.add(resultProductPriceDto);
				// 始发区域为全网点
			} else if (PricingConstants.ALL.equals(deptRegionId) && !PricingConstants.ALL.equals(arrvRegionId)) {
				deptRegionFullNetWorkList.add(resultProductPriceDto);
				// 目的地区域为全网点
			} else if (!PricingConstants.ALL.equals(deptRegionId) && PricingConstants.ALL.equals(arrvRegionId)) {
				arrvRegionFullNetworkList.add(resultProductPriceDto);
				// 始发区域与目的地区域都为全网点
			} else if (PricingConstants.ALL.equals(deptRegionId) && PricingConstants.ALL.equals(arrvRegionId)) {
				fullNetWorkList.add(resultProductPriceDto);
			}
		}
		if (noFullNetWorkList.size() > 0) {
			return noFullNetWorkList;
		} else if (arrvRegionFullNetworkList.size() > PricingConstants.ZERO) {
			return arrvRegionFullNetworkList;
		} else if (deptRegionFullNetWorkList.size() > PricingConstants.ZERO) {
			return deptRegionFullNetWorkList;
		} else {
			return fullNetWorkList;
		}
	}
	/**
	 * .
	 * <p>
	 * 修改计费规则实体<br/>
	 * 方法名：updatePriceValuation
	 * </p>
	 * 
	 * @param priceValuationEntity
	 *            计费规则实体
	 *            
	 * @author 张斌
	 * 
	 * @时间 2012-10-18
	 * 
	 * @since JDK1.6
	 */
	@Override
	@Transactional
	public void updatePriceValuation(PriceValuationEntity priceValuationEntity) {
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if (user == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 修改部门
		priceValuationEntity.setModifyOrgCode(orgCode);
		// 修改人
		priceValuationEntity.setVersionNo(new Date().getTime());
		priceValuationEntity.setModifyUser(userCode);
		priceValuationDao.updateValuation(priceValuationEntity);
	}
	/**
	 * 
	 * 查询基础产品信息(二级产品)
	 * 
	 * @author DP-Foss-张斌
	 * 
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findTwoLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_2);
		return productService.findExternalProductByCondition(condtion, null);
	}
	/**
	 * 
	 * 查询基础产品信息(一级产品)
	 * 
	 * @author DP-Foss-张斌
	 * 
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findOneLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_1);
		return productService.findExternalProductByCondition(condtion, null);
	}
	/**
	 * .
	 * <p>
	 * 自动构建CODE<br/>
	 * 方法名：createCode
	 * </p>
	 * 
	 * @param type
	 *            增值服务类型
	 *            
	 * @return Boolean
	 * 
	 * @author 张斌
	 * 
	 * @时间 2013-1-8
	 * 
	 * @since JDK1.6
	 */
	private String createCode(String type){
		 Date currentTime = new Date();
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	     String dateString = formatter.format(currentTime);
		String code = PricingConstants.EMPTY_STRING;
		if(PricingConstants.VALUATION_TYPE_BASICVALUEADDED.equals(type)){
			code = PricingConstants.PRICING_BASIC_CODE;
		}else if(PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED.equals(type)){
			code = PricingConstants.PRICING_PRODUCT_CODE;
		}else if(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED.equals(type)){
			code = PricingConstants.PRICING_REGION_CODE;
		}
		code = code.concat(dateString);
		return code;
	}
	/**
	 * .
	 * <p>
	 * 检测增值服务名称<br/>
	 * 方法名：isTheSameValueAddName
	 * </p>
	 * 
	 * @param regionName
	 *            区域名称
	 *            
	 * @return Boolean
	 * 
	 * @author 张斌
	 * 
	 * @时间 2012-11-23
	 * 
	 * @since JDK1.6
	 */
	public boolean isTheSameValueAddName(PriceValuationEntity priceValuationEntity, boolean isUpdate) {
		boolean flag = false;
		List<PriceValuationEntity> priceValuationEntityList = priceValuationDao.selectByName(priceValuationEntity);
		if (priceValuationEntityList != null && priceValuationEntityList.size() > 0) {
			// 通过升级操作，存在多条重名的记录
			if(isUpdate){
				flag = true; // 默认为重复，如果有id相同的情况，则通过下面的循环修改flag
				for(PriceValuationEntity temp : priceValuationEntityList){
					// 如果id相同，说明是对当前记录的修改
					if(StringUtils.equals(temp.getId(), priceValuationEntity.getId())){
						flag = false;
						break;
					}
				}
			} else {
				//如果是新增，直接表示有重复
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}
	/**
	 * 
	 * 查询基础产品信息(三级产品)
	 * 
	 * @author DP-Foss-张斌
	 * 
	 * @date 2012-10-29 下午6:47:34
	 */
	@Override
	public List<ProductEntity> findThreeLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		return productService.findExternalProductByCondition(condtion, null);
	}
	
	/**
	 * 查询纸纤包装详细单价讯息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-17下午18:30
	 */
	@Override
	public List<PriceFibelPaperPackingEntity> selectByValuationIdAndCode(
			String valuationId) {
		return priceCriteriaDetailDao.selectByValuationIdAndCode(valuationId);
	}
	
	/**
	 * 查询纸纤包装详细单价讯息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-17下午18:30
	 */
	@Override
	public List<PriceFibelPaperPackingEntity> selectByValuationIdAndCodeTrue(
			String valuationId) {
		return priceCriteriaDetailDao.selectByValuationIdAndCodeTrue(valuationId);
	}
	/**
	 * 查询所有二级行业数据
	 * @return
	 */
	public List<CustomerIndustryEntity> queryAllSecProfession(String valuationId){
	 if(StringUtil.isEmpty(valuationId)){
		 return cusProfessionService.queryAllSecProfession();
	 }else{
		 List<PriceIndustryEntity> priceIndustryEntityList = priceValuationDao.selectPriceIndustryEntityByValueAddedId(valuationId, TABLE_NAME_VALUEADD);
		 if(!CollectionUtils.isEmpty(priceIndustryEntityList)){
			 List<String> codes = new ArrayList<String>();
			 for(PriceIndustryEntity priceIndustryEntity:priceIndustryEntityList){
				 codes.add(priceIndustryEntity.getSencondTradecode());
			 }
			 return cusProfessionService.querySecProfessionByCodes(codes);
		 }
	 }
	 return null;
	}
	/**
	 * 封装二级行业数据
	 */
	private List<PriceIndustryEntity> transformIndustry(
		 List<CustomerIndustryEntity> customerindeCustomerIndustryEntityList,String tableId){
	 if(CollectionUtils.isEmpty(customerindeCustomerIndustryEntityList)){
		 return null;
	 }
	 List<PriceIndustryEntity> list = new ArrayList<PriceIndustryEntity>();
	 
	 for(CustomerIndustryEntity customerIndustryEntity:customerindeCustomerIndustryEntityList){
		 PriceIndustryEntity priceIndustryEntity = new PriceIndustryEntity();
		 priceIndustryEntity.setId(UUIDUtils.getUUID());
		 priceIndustryEntity.setTableId(tableId);
		 priceIndustryEntity.setFirstTradecode(customerIndustryEntity.getParentProfessionCode());
		 priceIndustryEntity.setFirstTradeName(customerIndustryEntity.getParentProfessionName());
		 priceIndustryEntity.setSencondTradecode(customerIndustryEntity.getProfessionCode());
		 priceIndustryEntity.setSencondTradeName(customerIndustryEntity.getProfessionName());
		 list.add(priceIndustryEntity);
	 }
	 return list;
}
	/**
	 * 查询基础产品
	 */
	public List<ProductEntity> queryProductList(String valuationId){
		 if(StringUtil.isEmpty(valuationId)){
			 return this.findThreeLevelProduct();
		 }else{
			 List<PriceProductEntity> priceProductEntityList = priceValuationDao.queryProductListByTableId(valuationId);
			 if(CollectionUtils.isNotEmpty(priceProductEntityList)){
				 List<ProductEntity> list = new ArrayList<ProductEntity>();
				 for(PriceProductEntity priceProductEntity:priceProductEntityList){
					 ProductEntity productEntity = new ProductEntity();
					 productEntity.setId(priceProductEntity.getId());
					 productEntity.setCode(priceProductEntity.getCode());
					 productEntity.setName(priceProductEntity.getName());
					 list.add(productEntity);
				 }
				 return list;
			 }
		 }
		 return null;
	}
}




