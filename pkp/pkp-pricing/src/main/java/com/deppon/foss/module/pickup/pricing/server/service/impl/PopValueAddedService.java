package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerIndustryEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.server.service.impl.CustomerIndustryService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDetailDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPricingValueAddedDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPopValueAddedService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductItemService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.GoodsTypeEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionValueAddEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceIndustryEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedEntity;
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
 * <p>
 * Description:增值服务service<br />
 * </p>
 * 
 * @title PricingValueAddedService.java
 * @package com.deppon.foss.module.pickup.pop.server.service.impl
 * @author xx
 * @version 0.1 2014年10月11日
 */
public class PopValueAddedService implements IPopValueAddedService {
	public static final String TABLE_NAME_VALUEADD = "T_POP_VALUEADDED";
	public static final String TABLE_NAME_SER_VALUATION = "T_SRV_PRICING_VALUATION";

	/**
	 * 增值服务dao
	 */
	@Inject
	private IPopValueAddedDao popValueAddedDao;
	/**
	 * 增值服务明细dao
	 */
	@Inject
	private IPopValueAddedDetailDao popValueAddedDetailDao;
	/**
	 * 员工service
	 */
	@Inject
	private IEmployeeService employeeService;
	/**
	 * 增值区域service
	 */
	@Inject
	private IRegionValueAddService regionValueAddService;
	/**
	 * 产品条目service
	 */
	@Inject
	private IProductItemService productItemService;
	/**
	 * 产品service
	 */
	@Inject
	private IProductService productService;
	/**
	 * 获取货物类型的SERVICE
	 */
	@Inject
	private IGoodsTypeService goodsTypeService;
	/**
	 * 
	 */
	@Inject
	private IPricingValueAddedDao pricingValueAddedDao;
	/**
	 * 二级行业service
	 */
	private CustomerIndustryService cusProfessionService;
	/**
	 * 二级行业service
	 * @param cusProfessionService
	 */
	public void setCusProfessionService(CustomerIndustryService cusProfessionService) {
		this.cusProfessionService = cusProfessionService;
	}
	@Autowired
    private IPreferentialService preferentialService;
	
	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}
	@Autowired
	private IConfigurationParamsService configurationParamsService;
	

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	/**
	 * 
	 * <p>
	 * Description:新增增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param priceValueAddedEntity
	 * @param valueAddedDetailList
	 *
	 */
	@Override
	public void addValueAdded(PriceValueAddedEntity priceValueAddedEntity,
			List<PriceValueAddedDetailEntity> valueAddedDetailList) {
		/**
		 * 校验增值服务名称是否存在
		 */
		this.checkValueAddedName(priceValueAddedEntity,false);
		/**
		 * 设置一些基本信息
		 */
		priceValueAddedEntity = this.makeBaseInfo(priceValueAddedEntity);
		// 增值服务的状态
		String active = priceValueAddedEntity.getActive();
		if (FossConstants.ACTIVE.equals(active)) {
			// 应该被修改的数据的截止日期
			Date endTime = new Date(priceValueAddedEntity.getBeginTime().getTime() - PricingConstants.ONE_MILLISECOND);
			// 查询数据库的增值服务列表
			List<PriceValueAddedEntity> searchResultList = this.seachOldValueAddedList(priceValueAddedEntity);
			for (PriceValueAddedEntity priceValueAddedEntityModel : searchResultList) {
			 	// 针对保费特殊处理
				// 根据已经重复的计费规则中找出数据库中的保费限保物品与当前所维护的限宝物品是否有冲突,如果限保物品一致,
				// 则修改上一个计费规则的截止日期为当前日期的前一天
				if (StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,
						priceValueAddedEntityModel.getPricingEntryCode())) {
					List<PriceValueAddedDetailEntity> oldPriceValueAddedDetailEntityList = popValueAddedDetailDao
							.selectByValueAddedId(priceValueAddedEntityModel.getId());
					if (CollectionUtils.isNotEmpty(oldPriceValueAddedDetailEntityList)) {
						// 如果vo中的限保物品在数据库中已经存在,则修改上一个版本截止日期
						if (valueAddedDetailList.get(0).getValueaddSubType()
								.equalsIgnoreCase(oldPriceValueAddedDetailEntityList.get(0).getValueaddSubType())) {
							// 修改对应低版本数据
							this.updateValueAddBase(priceValueAddedEntityModel.getId(), endTime, priceValueAddedEntity);
						}
					}
				}
				// 其他增值服务
				else {
					// 修改对应低版本数据
					this.updateValueAddBase(priceValueAddedEntityModel.getId(), endTime, priceValueAddedEntity);
				}
			}
		}
		// 计费规则ID
		String priceValueAddedEntityId = UUIDUtils.getUUID();
		// 设置ID
		priceValueAddedEntity.setId(priceValueAddedEntityId);
		// 设置默认结束时间
		priceValueAddedEntity = this.makeDefaultEndTime(priceValueAddedEntity);
		// 新增一条增值服务
		popValueAddedDao.insertSelective(priceValueAddedEntity);
		// 插入产品类型
		List<PriceProductEntity> productEntities = priceValueAddedEntity.getProductList();
		if (CollectionUtils.isNotEmpty(productEntities))
			for (PriceProductEntity product : productEntities) {
				product.setTableId(priceValueAddedEntity.getId());
				product.setTableName(TABLE_NAME_VALUEADD);
				popValueAddedDao.insertPriceProductEntity(product);
			}
		// 插入货物类类型
		List<PriceIndustryEntity> industryEntities = transformIndustry(priceValueAddedEntity.getCustomerIndustryEntityList(), priceValueAddedEntity.getId());
		if (CollectionUtils.isNotEmpty(industryEntities))
			for (PriceIndustryEntity industry : industryEntities) {
				industry.setTableId(priceValueAddedEntity.getId());
				industry.setTableName(TABLE_NAME_VALUEADD);
				popValueAddedDao.insertPriceIndustryEntity(industry);
			}

		// 新增增值服务明细 
		for (PriceValueAddedDetailEntity priceValueAddedDetailEntity : valueAddedDetailList) {
			priceValueAddedDetailEntity = this.makeDetailInfo(priceValueAddedEntity, priceValueAddedDetailEntity);
			// 插入增值服务详情
			popValueAddedDetailDao.insertSelective(priceValueAddedDetailEntity);

		}
	}

	/**
	 * 
	 * <p>
	 * Description:更新增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param priceValueAddedEntity
	 * @param addPriceValueAddedDetailEntityList
	 * @param updatericeValueAddedDetailEntityList
	 * @param deletericeValueAddedDetailEntityList
	 *
	 */
	@Override
	public void updateValueAdded(PriceValueAddedEntity priceValueAddedEntity,
			List<PriceValueAddedDetailEntity> addPriceValueAddedDetailEntityList,
			List<PriceValueAddedDetailEntity> updatericeValueAddedDetailEntityList,
			List<PriceValueAddedDetailEntity> deletericeValueAddedDetailEntityList) {
		/**
		 * 校验增值服务名称是否存在
		 */
		checkValueAddedName(priceValueAddedEntity,true);
		/**
		 * 封装基本信息
		 */
		priceValueAddedEntity = this.makeBaseInfo(priceValueAddedEntity);
		/**
		 * 封装结束时间
		 */
		priceValueAddedEntity = this.makeDefaultEndTime(priceValueAddedEntity);
		/**
		 * 更新增值服务
		 */
		popValueAddedDao.updateValueAdded(priceValueAddedEntity);
		// 查询产品
		List<PriceProductEntity> oldProductEntities = popValueAddedDao.selectPriceProductEntityByValueAddedId(
				priceValueAddedEntity.getId(), TABLE_NAME_VALUEADD);
		// 查询产品类型
		List<PriceIndustryEntity> oldIndustryEntities = popValueAddedDao.selectPriceIndustryEntityByValueAddedId(
				priceValueAddedEntity.getId(), TABLE_NAME_VALUEADD);
		List<PriceProductEntity> productEntities = priceValueAddedEntity.getProductList();
		// 插入货物类类型
//		List<PriceIndustryEntity> industryEntities = priceValueAddedEntity.getIndustryList();
		List<PriceIndustryEntity> industryEntities = transformIndustry(priceValueAddedEntity.getCustomerIndustryEntityList(), priceValueAddedEntity.getId());
		// 如果新旧产品列表不相等 先删除再插入
		if (!CollectionUtils.isEqualCollection(oldProductEntities, productEntities)) {
			// 插入产品类型
			if (CollectionUtils.isNotEmpty(oldProductEntities))
				for (PriceProductEntity product : oldProductEntities) {
					popValueAddedDao.deleteProductEntityById(product.getId());
				}
			// 插入产品类型
			if (CollectionUtils.isNotEmpty(productEntities))
				for (PriceProductEntity product : productEntities) {
					product.setTableId(priceValueAddedEntity.getId());
					product.setTableName("T_POP_VALUEADDED");
					popValueAddedDao.insertPriceProductEntity(product);
				}
		}
		// 如果新旧行业列表不相等 先删除再插入
		if (!CollectionUtils.isEqualCollection(oldIndustryEntities, industryEntities)) {
			// 删除产品类型
			if (CollectionUtils.isNotEmpty(oldIndustryEntities))
				for (PriceIndustryEntity industry : oldIndustryEntities) {
					popValueAddedDao.deleteIndustryEntityById(industry.getId());
				}
			// 插入行业列表
			if (CollectionUtils.isNotEmpty(industryEntities))
				for (PriceIndustryEntity industry : industryEntities) {
					industry.setTableId(priceValueAddedEntity.getId());
					industry.setTableName("T_POP_VALUEADDED");
					popValueAddedDao.insertPriceIndustryEntity(industry);
				}

		}
		if (CollectionUtils.isNotEmpty(addPriceValueAddedDetailEntityList)) {
			// 新增计价方式明细
			for (PriceValueAddedDetailEntity priceValueAddedDetailEntity : addPriceValueAddedDetailEntityList) {
				priceValueAddedDetailEntity = this.makeDetailInfo(priceValueAddedEntity, priceValueAddedDetailEntity);
				// 插入增值服务详情
				popValueAddedDetailDao.insertSelective(priceValueAddedDetailEntity);
			}
		}
			if (CollectionUtils.isNotEmpty(updatericeValueAddedDetailEntityList)) {
				// 修改的计价方式明细
				for (PriceValueAddedDetailEntity priceValueAddedDetailEntity : updatericeValueAddedDetailEntityList) {
					if (PricingConstants.PriceEntityConstants.PRICING_CODE_SHSL.equals(priceValueAddedEntity
							.getPricingEntryCode())
							|| PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(priceValueAddedEntity
									.getPricingEntryCode())
							|| PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(priceValueAddedEntity
									.getPricingEntryCode())
							|| PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(priceValueAddedEntity
									.getPricingEntryCode())
							|| PricingConstants.PriceEntityConstants.PRICING_CODE_CCF.equals(priceValueAddedEntity
									.getPricingEntryCode())
							|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT.equals(priceValueAddedEntity
									.getPricingEntryCode())) {
						priceValueAddedDetailEntity.setDefaultFee(priceValueAddedDetailEntity.getDefaultFee().multiply(
								new BigDecimal(NumberConstants.NUMBER_100)));
					} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(priceValueAddedEntity
							.getPricingEntryCode())) {
						priceValueAddedDetailEntity.setLeftrange(priceValueAddedDetailEntity.getLeftrange().multiply(
								new BigDecimal(NumberConstants.NUMBER_100)));
						priceValueAddedDetailEntity.setRightrange(priceValueAddedDetailEntity.getRightrange().multiply(
								new BigDecimal(NumberConstants.NUMBER_100)));
					}
					popValueAddedDetailDao.updateValueAddedDetailByPrimaryKey(priceValueAddedDetailEntity);
				}
			} else {
				// 只修改限保物品，不修改明细
				if (StringUtils.isNotEmpty(priceValueAddedEntity.getSubType())
						&& StringUtils.isNotEmpty(priceValueAddedEntity.getId())) {
					PriceValueAddedDetailEntity priceValueAddedDetailEntity = new PriceValueAddedDetailEntity();
					priceValueAddedDetailEntity.setValueaddSubType(priceValueAddedEntity.getSubType());
					priceValueAddedDetailEntity.setValueaddId(priceValueAddedEntity.getId());
					popValueAddedDetailDao.updateValueAddedDetailByValueAddedId(priceValueAddedDetailEntity);
				}
			}
			if (CollectionUtils.isNotEmpty(deletericeValueAddedDetailEntityList)) {
				for (PriceValueAddedDetailEntity dto : deletericeValueAddedDetailEntityList) {
					popValueAddedDetailDao.deleteByPrimaryKey(dto.getId());
				}
			}
		
	}

	/**
	 * 
	 * <p>
	 * Description:更新增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param priceValueAddedEntity
	 *
	 */
	@Override
	public void updatePriceValueAdded(PriceValueAddedEntity priceValueAddedEntity) {

		popValueAddedDao.updateValueAdded(priceValueAddedEntity);

	}

	/**
	 * 
	 * <p>
	 * Description:查询增值服务类型<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param priceEntity
	 * @return
	 *
	 */
	@Override
	public List<PriceEntity> searchValueAddedType(PriceEntity priceEntity) {
		return pricingValueAddedDao.searchValueAddedType(priceEntity);
	}

	/**
	 * 
	 * <p>
	 * Description:根据增值服务id查询增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAdddedId
	 * @return
	 *
	 */
	@Override
	public List<PriceValueAddedDetailEntity> selectByValueAddedId(String valueAdddedId) {
		return popValueAddedDetailDao.selectByValueAddedId(valueAdddedId);
	}

	/**
	 * 
	 * <p>
	 * Description:根据条件查询增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param record
	 * @param start
	 * @param limit
	 * @return
	 *
	 */
	@Override
	public List<PriceValueAddedEntity> selectByCodition(PriceValueAddedEntity record, int start, int limit) {
		if (PricingConstants.ALL.equals(record.getPricingEntryCode())) {
			record.setPricingEntryCode(null);
		}
		if (record.getBeginTime() != null) {
			Date beginTime = new Date(record.getBeginTime().getTime());
			Date endTime = new Date(record.getBeginTime().getTime());
			record.setBeginTime(beginTime);
			record.setEndTime(endTime);
		}
		List<PriceValueAddedEntity> priceValueAddedEntityList = popValueAddedDao.selectByCoditionNew(record, start,
				limit);
		if (CollectionUtils.isNotEmpty(priceValueAddedEntityList)) {
			for (PriceValueAddedEntity priceValueAddedEntity : priceValueAddedEntityList) {
				if (StringUtil.isNotBlank(priceValueAddedEntity.getModifyUser())) {
					EmployeeEntity employeeEntity =employeeService.queryEmployeeByEmpCode(
							priceValueAddedEntity.getModifyUser());// 查询修改人信息
					if (employeeEntity != null) {
						// 设置修改人姓名
						priceValueAddedEntity.setModifyUserName(employeeEntity.getEmpName());
					}
				}
			}
		}
		if (PricingConstants.VALUATION_TYPE_REGIONVALUEADDED.equals(record.getType())) {
			for (PriceValueAddedEntity priceValueAddedEntity : priceValueAddedEntityList) {
				if (StringUtil.isNotBlank(priceValueAddedEntity.getArrvRegionId())) {
					if (PricingConstants.ALL.equalsIgnoreCase(priceValueAddedEntity.getArrvRegionId())) {
						// 设置到达区域名称
						priceValueAddedEntity.setArrvRegionName(PricingConstants.ALL_REGION_NAME);
					} else {
						PriceRegionValueAddEntity priceRegionEntity = regionValueAddService.searchRegionByID(
								priceValueAddedEntity.getArrvRegionId(), PricingConstants.VALUEADD_REGION);
						if (priceRegionEntity != null) {
							// 设置到达区域名称
							priceValueAddedEntity.setArrvRegionName(priceRegionEntity.getRegionName());
						}
					}
				}
				if (StringUtil.isNotBlank(priceValueAddedEntity.getDeptRegionId())) {
					if (PricingConstants.ALL.equalsIgnoreCase(priceValueAddedEntity.getDeptRegionId())) {
						// 设置到达区域名称
						priceValueAddedEntity.setDeptRegionName(PricingConstants.ALL_REGION_NAME);
					} else {
						PriceRegionValueAddEntity priceRegionEntity = regionValueAddService.searchRegionByID(
								priceValueAddedEntity.getDeptRegionId(), PricingConstants.VALUEADD_REGION);
						if (priceRegionEntity != null) {
							// 设置出发区域名称
							priceValueAddedEntity.setDeptRegionName(priceRegionEntity.getRegionName());
						}
					}
				}
			}
		}
		return priceValueAddedEntityList;
	}

	/**
	 * 
	 * <p>
	 * Description:统计增值服务条数<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param record
	 * @return
	 *
	 */
	@Override
	public Long countByCodition(PriceValueAddedEntity record) {
		if (PricingConstants.ALL.equals(record.getPricingEntryCode())) {
			record.setPricingEntryCode(null);
		}
		if (record.getBeginTime() != null) {
			Date beginTime = new Date(record.getBeginTime().getTime());
			Date endTime = new Date(record.getBeginTime().getTime());
			record.setBeginTime(beginTime);
			record.setEndTime(endTime);
		}
		return popValueAddedDao.countByCodition(record);
	}

	/**
	 * 
	 * <p>
	 * Description:激活增值服务<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAddedIds
	 *
	 */
	@Override
	public void activeValueAdded(List<String> valueAddedIds) {
		// 激活之后修改相关的数据
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if (user == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 查询所有要激活的计费规则ID条件
		PriceValueAddedEntity searchAllModel = new PriceValueAddedEntity();
		searchAllModel.setValueAddedIds(valueAddedIds);
		// 查询所有要激活的计费规则ID条件
		List<PriceValueAddedEntity> searchResultAllList = popValueAddedDao.selectByCodition(searchAllModel);
		for (PriceValueAddedEntity priceValueAddedEntity : searchResultAllList) {
			// Date endTime = new Date(priceValueAddedEntity.getBeginTime()
			// .getTime() - PricingConstants.ONE_DAY_MILLISECOND);

			Date endTime = new Date(priceValueAddedEntity.getBeginTime().getTime() - PricingConstants.ONE_MILLISECOND);

			// 当天的0点00分00秒
			Date nowDate = DateUtils.convert(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT),
					DateUtils.DATE_FORMAT);
			if (priceValueAddedEntity.getBeginTime().getTime() < (nowDate.getTime() + PricingConstants.ONE_MILLISECOND)) {
				throw new PricingCommonException("区域名称为：" + priceValueAddedEntity.getName() + "的生效日期小于明天", null);
			}
			// 查询原来的计费规则
			PriceValueAddedEntity searchModel = new PriceValueAddedEntity();
			// 设置类型
			searchModel.setType(priceValueAddedEntity.getType());
			searchModel.setActive(FossConstants.ACTIVE);
			searchModel.setCode(priceValueAddedEntity.getCode());
			// 设置查询开始日期
			searchModel.setBeginTime(priceValueAddedEntity.getBeginTime());
			searchModel.setDeptRegionId(priceValueAddedEntity.getDeptRegionId());
			searchModel.setArrvRegionId(priceValueAddedEntity.getArrvRegionId());
			// 产品
			searchModel.setProductList(priceValueAddedEntity.getProductList());
			// 行业
			searchModel.setIndustryList(priceValueAddedEntity.getIndustryList());
			// 查询出符合条件的的版本
			List<PriceValueAddedEntity> searchResultList = popValueAddedDao.selectByCoditionSq(searchModel);
			List<PriceValueAddedDetailEntity> newPriceValueAddedDetailEntityList = popValueAddedDetailDao
					.selectByValueAddedId(priceValueAddedEntity.getId());
			for (PriceValueAddedEntity priceValueAddedEntityModel : searchResultList) {
				if (StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,
						priceValueAddedEntityModel.getCode())) {// 判断限保物品是否重复
					List<PriceValueAddedDetailEntity> oldPriceValueAddedDetailEntityList = popValueAddedDetailDao
							.selectByValueAddedId(priceValueAddedEntityModel.getId());
					if (CollectionUtils.isNotEmpty(oldPriceValueAddedDetailEntityList)
							&& CollectionUtils.isNotEmpty(newPriceValueAddedDetailEntityList)) {
						if (!StringUtil.equalsIgnoreCase(oldPriceValueAddedDetailEntityList.get(0).getSubType(),
								newPriceValueAddedDetailEntityList.get(0).getSubType())) {
							continue;
						}
					}
					PriceValueAddedEntity updateModel = new PriceValueAddedEntity();
					// 设置版本号
					updateModel.setVersionNo(versionNo);
					// 设置ID
					updateModel.setId(priceValueAddedEntityModel.getId());
					updateModel.setEndTime(endTime);
					updateModel.setModifyDate(new Date());
					updateModel.setModifyUser(userCode);
					updateModel.setModifyOrgCode(orgCode);
					// 修改对应低版本数据
					popValueAddedDao.updateValueAdded(updateModel);
				} else {
					PriceValueAddedEntity updateModel = new PriceValueAddedEntity();
					// 设置版本号
					updateModel.setVersionNo(versionNo);
					// 设置ID
					updateModel.setId(priceValueAddedEntityModel.getId());
					updateModel.setEndTime(endTime);
					updateModel.setModifyDate(new Date());
					updateModel.setModifyUser(userCode);
					updateModel.setModifyOrgCode(orgCode);
					// 修改对应低版本数据
					popValueAddedDao.updateValueAdded(updateModel);
				}
			}
		}
		popValueAddedDao.activeValueAdded(valueAddedIds);
		popValueAddedDetailDao.activeValueAddedDetailByValueAddedIds(valueAddedIds);

	}

	/**
	 * 
	 * <p>
	 * Description:这里写描述<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @param valueAddedIds
	 *
	 */
	@Override
	public void deleteValueAdded(List<String> valueAddedIds) {
		// 删除增值服务
		popValueAddedDao.deleteValueAdded(valueAddedIds);
		// 删除增值服务明细
		popValueAddedDetailDao.deleteValueAddedDetailByValueAddedIds(valueAddedIds);
	}

	/**
	 * 
	 * <p>
	 * Description:查询产品条目<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @return
	 *
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
	 * <p>
	 * Description:查询三级产品<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @return
	 *
	 */
	@Override
	public List<ProductEntity> findProductByCondition() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		condtion.setCode("ECONOMIC_EXPRESS");// 经济快递
		return productService.findExternalProductByCondition(condtion, null);
	}

	/**
	 * 
	 * <p>
	 * Description:查询货物类型<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @return
	 *
	 */
	@Override
	public List<GoodsTypeEntity> findGoodsTypeByCondiction() {
		// 查询激活的
		GoodsTypeEntity goodsTypeEntity = new GoodsTypeEntity();
		goodsTypeEntity.setActive(FossConstants.ACTIVE);
		return goodsTypeService.findGoodsTypeByCondiction(goodsTypeEntity);
	}

	@Override
	public List<ResultProductPriceDto> searchValueAddedPricingValuationByCondition(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		   List<ResultProductPriceDto> regionEntityList = popValueAddedDao.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		    return regionEntityList;
	}

	@Override
	public Map<String, List<ResultProductPriceDto>> siftValuationBillingRuleService(
			QueryBillCacilateValueAddDto queryBillCacilateValueAddDto) {
		
		/**
		 * 查询区域增值服务
		 */
		String oldGoodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
		String oldProductCode = queryBillCacilateValueAddDto.getProductCode();
		String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		//根据发货客户编码查询发货客户所属行业
		String customerCode = queryBillCacilateValueAddDto.getCustomerCode();
		CustomerIndustryEntity entry = cusProfessionService.querySecProfessionByCusCode(customerCode);
		if(entry!=null){
			String industryCode =entry.getProfessionCode();
			queryBillCacilateValueAddDto.setIndustryCode(industryCode);
		}
		//获取制单时间
		Date billTime = queryBillCacilateValueAddDto.getBillTime();
		//获取制单时间对应的 hh24：mi:ss格式,业务发生时间
		if(billTime!=null){
			String businessTime = DateUtils.convert(billTime).split(" ")[1];
			queryBillCacilateValueAddDto.setBusinessTime(businessTime.replaceAll(":", ""));
		}
		if(queryBillCacilateValueAddDto.getWeight()==null
				&& queryBillCacilateValueAddDto.getVolume()==null && queryBillCacilateValueAddDto.getIsPicWayBill()!=null && queryBillCacilateValueAddDto.getIsPicWayBill()){
			 /**
			 * 查询基础增值服务
			 */
		    queryBillCacilateValueAddDto.setProductCode(null);
		    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		    queryBillCacilateValueAddDto.setCustomerCode(null);
		    //基础增值标识
		    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
		    List<ResultProductPriceDto> baseEntityList = popValueAddedDao.searchValueAddedPricingValuationByConditionPic(queryBillCacilateValueAddDto);    
			
			//根据业务要求组合集合数据
			Map<String, List<ResultProductPriceDto>> resultMap = new HashMap<String, List<ResultProductPriceDto>>();
			if(!CollectionUtils.isEmpty(baseEntityList)) {
				resultMap.put("base", baseEntityList);
			}
			queryBillCacilateValueAddDto.setGoodsTypeCode(oldGoodsTypeCode);
			queryBillCacilateValueAddDto.setProductCode(oldProductCode);
			queryBillCacilateValueAddDto.setLongOrShort(longOrShort);
			return resultMap;
		}
		/**
	     * dp-foss-dongjialing
	     * 225131
		 * 查询客户增值服务
		 */
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setCustomerCode(customerCode);
		queryBillCacilateValueAddDto.setProductCode(oldProductCode);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		// 客户增值标识
		queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_CUSTOMERVALUEADDED);
		// 执行查询操作
		List<ResultProductPriceDto>  customerEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		// 根据产品筛选
		customerEntityList = filterProduct(customerEntityList, oldProductCode);
		// 根据货物类型筛选
		customerEntityList = filterGoodsType(customerEntityList, oldGoodsTypeCode);
		// 根据区域途筛选
		customerEntityList = filterBestMapEntity(customerEntityList);
		
		//获取开单时间
		Date receiveDate=queryBillCacilateValueAddDto.getReceiveDate();
		if("SH".equals(queryBillCacilateValueAddDto.getPricingEntryCode())&&StringUtils.isNotEmpty(customerCode)){
			ConfigurationParamsEntity configurationEntity=configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,"SH_FIXED_TIME", "DIP");
			if(configurationEntity!=null&&StringUtils.isNotBlank(configurationEntity.getConfValue())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date fixedDate=null;
				try {
					fixedDate = sdf.parse(configurationEntity.getConfValue());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (receiveDate.after(fixedDate)) {
					List<PreferentialInfoDto> queryCusBargainByCodeAndTime = preferentialService.queryCusBargainByCodeAndTime(customerCode, fixedDate);
					if(CollectionUtils.isNotEmpty(queryCusBargainByCodeAndTime)){
						queryBillCacilateValueAddDto.setReceiveDate(fixedDate);
					}
				}
			}
		}
		
		// 去除相关查询条件
		queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setCustomerCode(null);
		queryBillCacilateValueAddDto.setProductCode(oldProductCode);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		// 区域增值标识
		queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		// 执行查询操作
		List<ResultProductPriceDto>  regionEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		
		// 筛选合适的区域增值服务
		// 根据产品筛选
		regionEntityList = filterProduct(regionEntityList, oldProductCode);
		// 根据货物类型筛选
		regionEntityList = filterGoodsType(regionEntityList, oldGoodsTypeCode);
		// 根据长短途筛选
		//regionEntityList = filterLongORshort(regionEntityList, longOrShort);
		// 根据区域途筛选
		regionEntityList = filterBestMapEntity(regionEntityList);
		//Online-定价体系优化项目DJTWO-257 没有配置相关产品的区域增值，但是取到了区域增值
//		 //DJTWO-256 大客户特殊报价区域短途燃油附加费读取4元，收费标准应是2元
//		//如果根据产品匹配不到对应的产品价格，则取消产品匹配，对查询出来的结果根据产品过滤
//	    if(regionEntityList==null || regionEntityList.size()==0){
//	    	queryBillCacilateValueAddDto.setProductCode(null);
//	    	regionEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
//		    // 根据产品筛选
//	    	regionEntityList = filterProduct(regionEntityList, oldProductCode);
//	    	// 根据货物类型筛选
//			regionEntityList = filterGoodsType(regionEntityList, oldGoodsTypeCode);
//	    	// 根据区域途筛选
//			regionEntityList = filterBestMapEntity(regionEntityList);
//	    }
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
	    //如果根据产品匹配不到对应的产品价格，则取消产品匹配，对查询出来的结果根据产品过滤
//	    if(productEntityList==null || productEntityList.size()==0){
//	    	queryBillCacilateValueAddDto.setProductCode(null);
//		   productEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
//		  // 根据产品筛选
//		    productEntityList = filterProduct(productEntityList, oldProductCode);
//		    productEntityList = filterGoodsType(productEntityList, oldGoodsTypeCode);
//	    }
	    /**
		 * 查询基础增值服务
		 */
	    queryBillCacilateValueAddDto.setProductCode(null);
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	    queryBillCacilateValueAddDto.setCustomerCode(null);
	    //基础增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
	    List<ResultProductPriceDto> baseEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);    
		
//	    baseEntityList = filterLongORshort(baseEntityList, longOrShort);
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
		queryBillCacilateValueAddDto.setCustomerCode(customerCode);
		if("SH".equals(queryBillCacilateValueAddDto.getPricingEntryCode())){
			queryBillCacilateValueAddDto.setReceiveDate(receiveDate);
		}
		return resultMap;

	}
	
	@Override
	public Map<String, List<ResultProductPriceDto>> siftValuationBillingRuleServiceNoIndustry(
			QueryBillCacilateValueAddDto condition) {

		/**
		 * 查询区域增值服务
		 */
		QueryBillCacilateValueAddDto queryBillCacilateValueAddDto = condition;
		String oldGoodsTypeCode = queryBillCacilateValueAddDto.getGoodsTypeCode();
		String oldProductCode = queryBillCacilateValueAddDto.getProductCode();
		String longOrShort = queryBillCacilateValueAddDto.getLongOrShort();
		String customerCode = queryBillCacilateValueAddDto.getCustomerCode();
		queryBillCacilateValueAddDto.setIndustryCode(null);
		//获取制单时间
		Date billTime = queryBillCacilateValueAddDto.getBillTime();
		//获取制单时间对应的 hh24：mi:ss格式,业务发生时间
		if(billTime!=null){
			String businessTime = DateUtils.convert(billTime).split(" ")[1];
			queryBillCacilateValueAddDto.setBusinessTime(businessTime.replaceAll(":", ""));
		}
		/**
	     * dp-foss-dongjialing
	     * 225131
		 * 查询客户增值服务
		 */
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setCustomerCode(customerCode);
		queryBillCacilateValueAddDto.setProductCode(oldProductCode);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		// 客户增值标识
		queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_CUSTOMERVALUEADDED);
		// 执行查询操作
		List<ResultProductPriceDto>  customerEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		// 根据产品筛选
		customerEntityList = filterProduct(customerEntityList, oldProductCode);
		// 根据货物类型筛选
		customerEntityList = filterGoodsType(customerEntityList, oldGoodsTypeCode);
		// 根据区域途筛选
		customerEntityList = filterBestMapEntity(customerEntityList);
		// 去除相关查询条件
		queryBillCacilateValueAddDto.setGoodsTypeCode(null);
		queryBillCacilateValueAddDto.setLongOrShort(null);
		queryBillCacilateValueAddDto.setCustomerCode(null);
		queryBillCacilateValueAddDto.setProductCode(oldProductCode);
		// 设置相关查询条件
		queryBillCacilateValueAddDto.setAllNet(PricingConstants.ALL);
		// 区域增值标识
		queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_REGIONVALUEADDED);
		// 执行查询操作
		List<ResultProductPriceDto>  regionEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
		
		// 筛选合适的区域增值服务
		// 根据产品筛选
		regionEntityList = filterProduct(regionEntityList, oldProductCode);
		// 根据货物类型筛选
		regionEntityList = filterGoodsType(regionEntityList, oldGoodsTypeCode);
		// 根据长短途筛选
		//regionEntityList = filterLongORshort(regionEntityList, longOrShort);
		// 根据区域途筛选
		regionEntityList = filterBestMapEntity(regionEntityList);
		//Online-定价体系优化项目DJTWO-257 没有配置相关产品的区域增值，但是取到了区域增值
//		 //DJTWO-256 大客户特殊报价区域短途燃油附加费读取4元，收费标准应是2元
//		//如果根据产品匹配不到对应的产品价格，则取消产品匹配，对查询出来的结果根据产品过滤
//	    if(regionEntityList==null || regionEntityList.size()==0){
//	    	queryBillCacilateValueAddDto.setProductCode(null);
//	    	regionEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
//		    // 根据产品筛选
//	    	regionEntityList = filterProduct(regionEntityList, oldProductCode);
//	    	// 根据货物类型筛选
//			regionEntityList = filterGoodsType(regionEntityList, oldGoodsTypeCode);
//	    	// 根据区域途筛选
//			regionEntityList = filterBestMapEntity(regionEntityList);
//	    }
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
	    //如果根据产品匹配不到对应的产品价格，则取消产品匹配，对查询出来的结果根据产品过滤
//	    if(productEntityList==null || productEntityList.size()==0){
//	    	queryBillCacilateValueAddDto.setProductCode(null);
//		   productEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);
//		  // 根据产品筛选
//		    productEntityList = filterProduct(productEntityList, oldProductCode);
//		    productEntityList = filterGoodsType(productEntityList, oldGoodsTypeCode);
//	    }
	    /**
		 * 查询基础增值服务
		 */
	    queryBillCacilateValueAddDto.setProductCode(null);
	    queryBillCacilateValueAddDto.setGoodsTypeCode(null);
	    queryBillCacilateValueAddDto.setCustomerCode(null);
	    //基础增值标识
	    queryBillCacilateValueAddDto.setType(PricingConstants.VALUATION_TYPE_BASICVALUEADDED); 
	    List<ResultProductPriceDto> baseEntityList = this.searchValueAddedPricingValuationByCondition(queryBillCacilateValueAddDto);    
		
//	    baseEntityList = filterLongORshort(baseEntityList, longOrShort);
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
		queryBillCacilateValueAddDto.setCustomerCode(customerCode);
		return resultMap;
	}
	/**
	 * 
	 * <p>
	 * Description:过滤产品CODE<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年12月4日
	 * @param entityList
	 * @param oldProductCode
	 * @return
	 * List<ResultProductPriceDto>
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
	 * <p>
	 * Description:过滤货物类型<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年12月4日
	 * @param entityList
	 * @param oldGoodsTypeCode
	 * @return
	 * List<ResultProductPriceDto>
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
	 * <p>
	 * Description:过滤最优先的区域增值计费规则<br />
	 * </p>
	 * @author xx
	 * @version 0.1 2014年12月4日
	 * @param resultProductPriceList
	 * @return
	 * List<ResultProductPriceDto>
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
	 * 
	 * <p>
	 * Description:查询基础产品信息(二级产品)<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @return
	 *
	 */
	@Override
	public List<ProductEntity> findTwoLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_2);
		return productService.findExternalProductByCondition(condtion, null);
	}

	/**
	 * 
	 * <p>
	 * Description:查询基础产品信息(一级产品)<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @return
	 *
	 */
	@Override
	public List<ProductEntity> findOneLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_1);
		return productService.findExternalProductByCondition(condtion, null);
	}

	/**
	 * 
	 * <p>
	 * Description:查询三级产品<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 * @return
	 *
	 */
	@Override
	public List<ProductEntity> findThreeLevelProduct() {
		ProductDto condtion = new ProductDto();
		condtion.setLevels(ProductEntityConstants.PRICING_PRODUCT_LEVEL_3);
		return productService.findExternalProductByCondition(condtion, null);
	}

	@Override
	public void activeFast(PriceValueAddedEntity priceValueAddedEntity) {
		// 激活之后修改相关的数据
		long versionNo = new Date().getTime();
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if (user == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		if (null == priceValueAddedEntity || priceValueAddedEntity.getId() == null) {
			throw new PricingCommonException("激活参数为空!", "激活参数为空!");
		}
		if (null == priceValueAddedEntity.getBeginTime()) {
			throw new PricingCommonException("激活生效时间参数为空!", "激活生效时间参数为空!");
		}
		String id = priceValueAddedEntity.getId();
		List<String> valueAddedIds = new ArrayList<String>();
		valueAddedIds.add(id);
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();

		// 修改激活日期,组织机构,登录操作用户信息
		PriceValueAddedEntity temppriceValueAddedEntity = popValueAddedDao.selectByPrimaryKey(id);
		temppriceValueAddedEntity.setVersionNo(versionNo);
		temppriceValueAddedEntity.setModifyDate(new Date());
		temppriceValueAddedEntity.setModifyOrgCode(orgCode);
		temppriceValueAddedEntity.setModifyUser(userCode);
		temppriceValueAddedEntity.setBeginTime(priceValueAddedEntity.getBeginTime());
		popValueAddedDao.updateValueAdded(temppriceValueAddedEntity);

		// 查询所有要激活的计费规则ID条件
		PriceValueAddedEntity searchAllModel = new PriceValueAddedEntity();
		searchAllModel.setValueAddedIds(valueAddedIds);
		// 查询所有要激活的计费规则ID条件
		List<PriceValueAddedEntity> searchResultAllList = popValueAddedDao.selectByCodition(searchAllModel);
		for (PriceValueAddedEntity priceValueAdded : searchResultAllList) {
			Date endTime = new Date(priceValueAdded.getBeginTime().getTime() - PricingConstants.ONE_MILLISECOND);
			// 查询出符合条件的的版本
			List<PriceValueAddedEntity> searchResultList = this.seachOldValueAddedList(priceValueAddedEntity);
			List<PriceValueAddedDetailEntity> newPriceValueAddedDetailEntityList = popValueAddedDetailDao
					.selectByValueAddedId(priceValueAdded.getId());
			for (PriceValueAddedEntity priceValueAddedEntityModel : searchResultList) {
				if (StringUtil.equalsIgnoreCase(PriceEntityConstants.PRICING_CODE_BF,
						priceValueAddedEntityModel.getCode())) {// 判断限保物品是否重复
					List<PriceValueAddedDetailEntity> oldpriceValueAddedDetailEntityList = popValueAddedDetailDao
							.selectByValueAddedId(priceValueAddedEntityModel.getId());
					if (CollectionUtils.isNotEmpty(oldpriceValueAddedDetailEntityList)
							&& CollectionUtils.isNotEmpty(newPriceValueAddedDetailEntityList)) {
						if (!StringUtil.equalsIgnoreCase(
								oldpriceValueAddedDetailEntityList.get(0).getValueaddSubType(),
								newPriceValueAddedDetailEntityList.get(0).getValueaddSubType())) {
							continue;
						}
					}
					this.updateValueAddBase(priceValueAddedEntityModel.getId(), endTime, temppriceValueAddedEntity);
				} else {
					this.updateValueAddBase(priceValueAddedEntityModel.getId(), endTime, temppriceValueAddedEntity);

				}
			}
		}
		popValueAddedDao.activeValueAdded(valueAddedIds);
		popValueAddedDetailDao.activeValueAddedDetailByValueAddedIds(valueAddedIds);

	}


	/**
	 * <p>
	 * Description:employeeService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public IEmployeeService getEmployeeService() {
		return employeeService;
	}

	/**
	 * <p>
	 * Description:employeeService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * <p>
	 * Description:regionValueAddService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public IRegionValueAddService getRegionValueAddService() {
		return regionValueAddService;
	}

	/**
	 * <p>
	 * Description:regionValueAddService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setRegionValueAddService(IRegionValueAddService regionValueAddService) {
		this.regionValueAddService = regionValueAddService;
	}

	/**
	 * <p>
	 * Description:productItemService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public IProductItemService getProductItemService() {
		return productItemService;
	}

	/**
	 * <p>
	 * Description:productItemService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setProductItemService(IProductItemService productItemService) {
		this.productItemService = productItemService;
	}

	/**
	 * <p>
	 * Description:productService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public IProductService getProductService() {
		return productService;
	}

	/**
	 * <p>
	 * Description:productService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	/**
	 * <p>
	 * Description:pricingValueAddedDetailDao<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public IPopValueAddedDetailDao getPopValueAddedDetailDao() {
		return popValueAddedDetailDao;
	}

	/**
	 * <p>
	 * Description:pricingValueAddedDetailDao<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPopValueAddedDetailDao(IPopValueAddedDetailDao popValueAddedDetailDao) {
		this.popValueAddedDetailDao = popValueAddedDetailDao;
	}

	/**
	 * <p>
	 * Description:priceValueAddedDao<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public IPopValueAddedDao getPopValueAddedDao() {
		return popValueAddedDao;
	}

	/**
	 * <p>
	 * Description:priceValueAddedDao<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月13日
	 */
	public void setPopValueAddedDao(IPopValueAddedDao popValueAddedDao) {
		this.popValueAddedDao = popValueAddedDao;
	}

	/**
	 * <p>
	 * Description:goodsTypeService<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月14日
	 */
	public IGoodsTypeService getGoodsTypeService() {
		return goodsTypeService;
	}

	/**
	 * 设置 获取货物类型的SERVICE.
	 *
	 * @param goodsTypeService
	 *            the new 获取货物类型的SERVICE
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}

	/**
	 * <p>
	 * Description:pricingValueAddedDao<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月15日
	 */
	public IPricingValueAddedDao getPricingValueAddedDao() {
		return pricingValueAddedDao;
	}

	/**
	 * <p>
	 * Description:pricingValueAddedDao<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月15日
	 */
	public void setPricingValueAddedDao(IPricingValueAddedDao pricingValueAddedDao) {
		this.pricingValueAddedDao = pricingValueAddedDao;
	}

	/**
	 * 
	 * <p>
	 * Description:校验增值服务方案是否存在<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @param priceValueAddedEntity
	 *            void
	 */
	public void checkValueAddedName(PriceValueAddedEntity priceValueAddedEntity,boolean isUpdate) {
		String operateType = priceValueAddedEntity.getOperateTypeForAddAndUpdateVersion();
		// 新增需要校验方案名是否已存在，升级版本不需要校验
		if (!StringUtil.equals(operateType, "upgradedVersion")) {
			if (isTheSameValueAddName(priceValueAddedEntity, isUpdate)) {
				throw new PricingCommonException("增值方案名称已存在!", "增值方案名称已存在!");
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Description:封装基本信息<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @param priceValueAddedEntity
	 * @return PriceValueAddedEntity
	 */

	public PriceValueAddedEntity makeBaseInfo(PriceValueAddedEntity priceValueAddedEntity) {
		// 获取当前登录用户
		UserEntity user = (UserEntity) UserContext.getCurrentUser();
		if (user == null) {
			throw new PricingCommonException(PricingCommonException.NOT_LOGIN, PricingCommonException.NOT_LOGIN);
		}
		// 当前登录用户empcode
		String userCode = user.getEmployee().getEmpCode();
		// 当前登录用户所在部门code
		String orgCode = user.getEmployee().getDepartment().getCode();
		// 版本号
		priceValueAddedEntity.setVersionNo(new Date().getTime());
		// 创建人
		priceValueAddedEntity.setCreateUser(userCode);
		// 修改人
		priceValueAddedEntity.setModifyUser(userCode);
		// 币种
		priceValueAddedEntity.setCurrencyCode(DictionaryValueConstants.SETTLEMENT__CURRENCY_CODE__RMB);
		// 创建部门
		priceValueAddedEntity.setCreateOrgCode(orgCode);
		// 修改部门
		priceValueAddedEntity.setModifyOrgCode(orgCode);
		String code = this.createCode(priceValueAddedEntity.getType());
		priceValueAddedEntity.setCode(code);
		return priceValueAddedEntity;
	}

	/**
	 * 
	 * <p>
	 * Description:查询旧的增值服务列表<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @param priceValueAddedEntity
	 * @return List<PriceValueAddedEntity>
	 */
	public List<PriceValueAddedEntity> seachOldValueAddedList(PriceValueAddedEntity priceValueAddedEntity) {
		// 查询截止时间是2999-12-31的数据
		PriceValueAddedEntity searchModel = new PriceValueAddedEntity();
		// 出发区域
		searchModel.setDeptRegionId(priceValueAddedEntity.getDeptRegionId());
		// 到达区域
		searchModel.setArrvRegionId(priceValueAddedEntity.getArrvRegionId());
		// 设置类型
		searchModel.setType(priceValueAddedEntity.getType());
		// 有效数据
		searchModel.setActive(FossConstants.ACTIVE);
		// 设置查询开始日期
		searchModel.setBeginTime(priceValueAddedEntity.getBeginTime());
		// 根据规则类型,开始时间
		List<PriceValueAddedEntity> searchResultList = popValueAddedDao.selectByCoditionSq(searchModel);
		return searchResultList;
	}

	/**
	 * 
	 * <p>
	 * Description:根据增值服务基本信息<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @param id
	 * @param endTime
	 * @param priceValueAddedEntity
	 *            void
	 */
	public void updateValueAddBase(String id, Date endTime, PriceValueAddedEntity priceValueAddedEntity) {
		PriceValueAddedEntity updateModel = new PriceValueAddedEntity();
		updateModel.setVersionNo(priceValueAddedEntity.getVersionNo());
		updateModel.setId(id);
		updateModel.setEndTime(endTime);
		updateModel.setModifyDate(new Date());
		updateModel.setModifyUser(priceValueAddedEntity.getModifyUser());
		updateModel.setModifyOrgCode(priceValueAddedEntity.getModifyOrgCode());
		popValueAddedDao.updateValueAdded(updateModel);

	}

	/**
	 * 
	 * <p>
	 * Description:设置结束时间<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月23日
	 * @param priceValueAddedEntity
	 * @return PriceValueAddedEntity
	 */
	public PriceValueAddedEntity makeDefaultEndTime(PriceValueAddedEntity priceValueAddedEntity) {
		// 如果endTime
		if (priceValueAddedEntity.getEndTime() == null) {
			// 先设置默认值
			priceValueAddedEntity.setEndTime(new Date(PricingConstants.ENDTIME));
		} else {
			// 根据MANA-1320修改
			// 设置结束日期为23:59:59
			priceValueAddedEntity.setEndTime(DateUtils.getEndDatetime(priceValueAddedEntity.getEndTime()));
		}
		return priceValueAddedEntity;
	}

	public PriceValueAddedDetailEntity makeDetailInfo(PriceValueAddedEntity priceValueAddedEntity,
			PriceValueAddedDetailEntity priceValueAddedDetailEntity) {
		// 计价方式明细ID
		String priceValueAddedDetailEntityId = UUIDUtils.getUUID();
		priceValueAddedDetailEntity.setActive(priceValueAddedEntity.getActive());
		priceValueAddedDetailEntity.setId(priceValueAddedDetailEntityId);
		// 版本号
		priceValueAddedDetailEntity.setVersionNo(priceValueAddedEntity.getVersionNo());
		// 价格计算表达式——简单费率规则
		priceValueAddedDetailEntity.setPriceRuleId(PricingConstants.PRICE_RULE_RATERULES_ID);
		// 当是其他费用和签收回单、超远派送 时采用固定价暂时写死，之后会从缓存中去判断
		if (PricingConstants.PriceEntityConstants.PRICING_CODE_QS.equals(priceValueAddedEntity.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT.equals(priceValueAddedEntity
						.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_CY.equals(priceValueAddedEntity
						.getPricingEntryCode())) {
			// 价格计算表达式——固定价
			priceValueAddedDetailEntity.setPriceRuleId(PricingConstants.PRICE_RULE_FIXED_ID);
		}

		if (PricingConstants.PriceEntityConstants.PRICING_CODE_SHSL.equals(priceValueAddedEntity.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_SH.equals(priceValueAddedEntity
						.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_JH.equals(priceValueAddedEntity
						.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_BZ.equals(priceValueAddedEntity
						.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_CCF.equals(priceValueAddedEntity
						.getPricingEntryCode())
				|| PricingConstants.PriceEntityConstants.PRICING_CODE_QT.equals(priceValueAddedEntity
						.getPricingEntryCode())) {
			// 送货上楼、送货费、接货费、包装费、仓储费、其它费用 费率乘100
			priceValueAddedDetailEntity.setDefaultFee(priceValueAddedDetailEntity.getDefaultFee().multiply(
					new BigDecimal(NumberConstants.NUMBER_100)));
		} else if (PricingConstants.PriceEntityConstants.PRICING_CODE_HK.equals(priceValueAddedEntity
				.getPricingEntryCode())) {
			// 代收货款左右区间乘以100
			priceValueAddedDetailEntity.setLeftrange(priceValueAddedDetailEntity.getLeftrange().multiply(
					new BigDecimal(NumberConstants.NUMBER_100)));
			priceValueAddedDetailEntity.setRightrange(priceValueAddedDetailEntity.getRightrange().multiply(
					new BigDecimal(NumberConstants.NUMBER_100)));
		}
		priceValueAddedDetailEntity.setValueaddId(priceValueAddedEntity.getId());

		return priceValueAddedDetailEntity;
	}

	/**
	 * 
	 * <p>
	 * Description:检测增值服务名称<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param priceValueAddedEntity
	 * @param isUpdate
	 * @return boolean
	 */
	public boolean isTheSameValueAddName(PriceValueAddedEntity priceValueAddedEntity, boolean isUpdate) {
		boolean flag = false;
		List<PriceValueAddedEntity> priceValueAddedEntityList = popValueAddedDao.selectByName(priceValueAddedEntity);
		if (CollectionUtils.isNotEmpty(priceValueAddedEntityList)) {
			// 通过升级操作，存在多条重名的记录
			if (isUpdate) {
				flag = true; // 默认为重复，如果有id相同的情况，则通过下面的循环修改flag
				for (PriceValueAddedEntity temp : priceValueAddedEntityList) {
					// 如果id相同，说明是对当前记录的修改
					if (StringUtils.equals(temp.getId(), priceValueAddedEntity.getId())) {
						flag = false;
						break;
					}
				}
			} else {
				// 如果是新增，直接表示有重复
				flag = true;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * <p>
	 * Description:自动构建CODE<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月11日
	 * @param type
	 * @return String
	 */
	private String createCode(String type) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String dateString = formatter.format(currentTime);
		String code = PricingConstants.EMPTY_STRING;
		if (PricingConstants.VALUATION_TYPE_BASICVALUEADDED.equals(type)) {
			code = PricingConstants.PRICING_BASIC_CODE;
		} else if (PricingConstants.VALUATION_TYPE_PRODUCTVALUEADDED.equals(type)) {
			code = PricingConstants.PRICING_PRODUCT_CODE;
		} else if (PricingConstants.VALUATION_TYPE_REGIONVALUEADDED.equals(type)) {
			code = PricingConstants.PRICING_REGION_CODE;
		}
		code = code.concat(dateString);
		return code;
	}
	/**
	  * 查询所有二级行业数据
	  * @return
	  */
	 public List<CustomerIndustryEntity> queryAllSecProfession(String valuationId){
		 if(StringUtil.isEmpty(valuationId)){
			 return cusProfessionService.queryAllSecProfession();
		 }else{
			 List<PriceIndustryEntity> priceIndustryEntityList = popValueAddedDao.selectPriceIndustryEntityByValueAddedId(valuationId, TABLE_NAME_SER_VALUATION);
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
			 List<PriceProductEntity> priceProductEntityList = popValueAddedDao.queryProductListByTableId(valuationId);
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
