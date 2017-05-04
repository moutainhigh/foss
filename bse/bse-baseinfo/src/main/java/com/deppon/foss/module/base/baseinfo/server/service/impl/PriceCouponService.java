package com.deppon.foss.module.base.baseinfo.server.service.impl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDiscountOrgDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarketingSchemeDAO;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceCouponDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceCriteriaDetailDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceEntryDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IPriceValuationDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPriceCouponService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IProductService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerDegreeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerProfessionService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrderSourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerDegreeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerProfessionEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarketingSchemeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceCriteriaDetailEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ProductItemEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DetailExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PriceCouponDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.ProductDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.PriceCouponException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PriceCouponVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IRegionDao;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.FlightPricePlanException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.PricePlanException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 折扣方案操作类
 * @author dujunhui-187862
 * @date 2014-10-10 下午3:22:09
 * @since
 * @version
 */
public class PriceCouponService implements IPriceCouponService {
	/**
     * 加载日志文件
     */
	private static final Logger log = Logger.getLogger(PriceCouponService.class);
    /**
     * 价格优惠券操作DAO
     */
    private IPriceCouponDao priceCouponDao;
    /**
   	 * 降价发券方案操作DAO
   	 */
    private IMarketingSchemeDAO marketingSchemeDao;

    /**
   	 * 三级产品类型Service
   	 */
    private IProductService bseProductService;
    /**
   	 * 订单来源Service
   	 */
    private ICommonOrderSourceService commonOrderSourceService;
    /**
   	 * 客户等级Service
   	 */
    private ICommonCustomerDegreeService commonCustomerDegreeService;
    /**
   	 * 客户行业Service
   	 */
    private ICommonCustomerProfessionService commonCustomerProfessionService;
    
    /**
	 * 折扣适用网点操作DAO
	 */ 
    private IDiscountOrgDao bseDiscountOrgDao;
    /**
	 * 计费规则操作DAO
	 */ 
    private IPriceValuationDao bsePriceValuationDao;
    /**
   	 * 计价明细操作DAO
   	 */
    private IPriceCriteriaDetailDao priceRuleDetailDao;
    /**
   	 * 计价条目操作DAO
   	 */ 
    private IPriceEntryDao bsePriceEntryDao;

    /**
     * 价格区域DAO
     */
	private IRegionDao regionDao;
	/*
	 * 人员service
	 */
	private IEmployeeService employeeService;
    
    public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	/**
     * priceCouponDao注入
     */
    public void setPriceCouponDao(IPriceCouponDao priceCouponDao) {
		this.priceCouponDao = priceCouponDao;
	}
    /**
     * marketingSchemeDao注入
     */
    public void setMarketingSchemeDao(IMarketingSchemeDAO marketingSchemeDao) {
		this.marketingSchemeDao = marketingSchemeDao;
	}
	/**
     * commonOrderSourceService注入
     */
	public void setCommonOrderSourceService(
			ICommonOrderSourceService commonOrderSourceService) {
		this.commonOrderSourceService = commonOrderSourceService;
	}
	/**
     * commonCustomerDegreeService注入
     */
	public void setCommonCustomerDegreeService(
			ICommonCustomerDegreeService commonCustomerDegreeService) {
		this.commonCustomerDegreeService = commonCustomerDegreeService;
	}
	/**
     * commonCustomerProfessionService注入
     */
	public void setCommonCustomerProfessionService(
			ICommonCustomerProfessionService commonCustomerProfessionService) {
		this.commonCustomerProfessionService = commonCustomerProfessionService;
	}
	
	/**
	 * @return  the bseDiscountOrgDao
	 */
	public IDiscountOrgDao getBseDiscountOrgDao() {
		return bseDiscountOrgDao;
	}
	/**
	 * @param bseDiscountOrgDao the bseDiscountOrgDao to set
	 */
	public void setBseDiscountOrgDao(IDiscountOrgDao bseDiscountOrgDao) {
		this.bseDiscountOrgDao = bseDiscountOrgDao;
	}
	
	/**
	 * @param bsePriceEntryDao the bsePriceEntryDao to set
	 */
	public void setBsePriceEntryDao(IPriceEntryDao bsePriceEntryDao) {
		this.bsePriceEntryDao = bsePriceEntryDao;
	}
	/**
	 * @param bseProductService the bseProductService to set
	 */
	public void setBseProductService(IProductService bseProductService) {
		this.bseProductService = bseProductService;
	}

	/**
	 * @return  the bsePriceValuationDao
	 */
	public IPriceValuationDao getBsePriceValuationDao() {
		return bsePriceValuationDao;
	}
	/**
	 * @param bsePriceValuationDao the bsePriceValuationDao to set
	 */
	public void setBsePriceValuationDao(IPriceValuationDao bsePriceValuationDao) {
		this.bsePriceValuationDao = bsePriceValuationDao;
	}
	/**
	 * 获取 计价明细操作DAO.
	 * @return the 计价明细操作DAO
	 */
	public IPriceCriteriaDetailDao getPriceRuleDetailDao() {
		return priceRuleDetailDao;
	}
	/**
	 * 设置 计价明细操作DAO.
	 * @param priceCriteriaDetailDao the new 计价明细操作DAO
	 */
	public void setPriceRuleDetailDao(IPriceCriteriaDetailDao priceRuleDetailDao) {
		this.priceRuleDetailDao = priceRuleDetailDao;
	}

	/**
	 * 设置 价格区域DAO.
	 *
	 * @param regionDao the new 价格区域DAO
	 */
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	/**
	 * <p>根据条件查询计价条目折</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-9 下午4:44:26
	 * @return
	 * @see
	 */
	@Override
	public List<PriceEntity> selectPriceEntityByCodition() {
		PriceEntity  priceEntity = new PriceEntity();
		priceEntity.setId(null);
		priceEntity.setActive(FossConstants.ACTIVE);
		priceEntity.setReceiveDate(new Date());
		priceEntity.setCode(PriceEntityConstants.PRICING_CODE_FRT);
		return bsePriceEntryDao.searchPriceEntryByConditions(priceEntity);
	}
	/**
	 * <p>根据条件查询降价发券方案总数</p> 
	 * @author dujunhui-187862
	 * @date 2014-9-24 上午8:21:26
	 * @return
	 * @see
	 */
	@Override
	public Long countPriceProgramByCodition(MarketingSchemeEntity entity) {
		if (DictionaryValueConstants.ALL.equals(entity.getActive())) {
			entity.setActive(null);
		}
		return marketingSchemeDao.countMarketingScheme(entity);
	}
	
	/**
	 * <p>根据条件查询降价发券方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-9-24 上午8:21:26
	 * @return
	 * @see
	 */
	@Override
	public List<MarketingSchemeEntity> selectPriceProgramByCodition(MarketingSchemeEntity marketingEvent, int start, int limit) {
		if (DictionaryValueConstants.ALL.equals(marketingEvent.getActive())) {
			marketingEvent.setActive(null);
		}
		List<MarketingSchemeEntity> list = marketingSchemeDao.queryMarketingSchemeList(marketingEvent, start, limit);
		for(MarketingSchemeEntity marketingEventEntity:list){
			//6、根据创建人工号封装创建人姓名
			if(StringUtils.isNotEmpty(marketingEventEntity.getCreateUser())){
				String createUserName=employeeService.
						queryEmpNameByEmpCode(marketingEventEntity.getCreateUser());
				//封装创建人姓名
				marketingEventEntity.setCreateUserName(createUserName);
			}
		}
		return list;
	}
	/**
	 * <p>根据条件查询价格折扣明细总数</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午10:55:26
	 * @param dto
	 * @return
	 * @see
	 */
	@Override
	public Long countPriceCouponByCodition(PriceCouponDto dto) {
		if (DictionaryValueConstants.ALL.equals(dto.getActive())) {
			dto.setActive(null);
		}
		return priceCouponDao.countPriceCouponByCodition(dto);
	}
	/**
	 * <p>根据条件查询价格折扣明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 上午10:55:26
	 * @param dto
	 * @return
	 * @see
	 */
	@Override
	public List<PriceCouponDto> selectPriceCouponByCodition(PriceCouponDto dto, int start, int limit) {
		if (DictionaryValueConstants.ALL.equals(dto.getActive())) {
			dto.setActive(null);
		}
		if (DictionaryValueConstants.ALL.equals(dto.getProductId())){
			dto.setProductId(null);
		}
		//根据区域name模糊查询区域code
		PriceRegionEntity priceRegionNameEntity=new PriceRegionEntity();
		priceRegionNameEntity.setActive(FossConstants.ACTIVE);
		priceRegionNameEntity.setRegionNature(PricingConstants.PRICING_REGION);
		//根据出发区域
		if(StringUtil.isNotBlank(dto.getDeptOrgName())){//出发部门名不为空
			priceRegionNameEntity.setRegionName(dto.getDeptOrgName());
			List<PriceRegionEntity> regionDeptNameEntity = regionDao.
					searchRegionByCondition(priceRegionNameEntity, 0, 1);
			if(CollectionUtils.isNotEmpty(regionDeptNameEntity)){
				dto.setDeptOrgCode(regionDeptNameEntity.get(0).getRegionCode());
			}
			//根据出发部门名称查询实体为空，则设置出发部门编码为时间，用以达到明细查询结果为空
			dto.setDeptOrgCode(new Date().toString());
		}
		//根据到达区域
		if(StringUtil.isNotBlank(dto.getArrvOrgName())==true){//到达部门名不为空
			
			priceRegionNameEntity.setRegionName(dto.getArrvOrgName());
			List<PriceRegionEntity> regionArrvNameEntity = regionDao.
					searchRegionByCondition(priceRegionNameEntity, 0, 1);
			if(CollectionUtils.isNotEmpty(regionArrvNameEntity)){
				dto.setArrvOrgCode(regionArrvNameEntity.get(0).getRegionCode());
			}
			//根据到达部门名称查询实体为空，则设置到达部门编码为时间，用以达到明细查询结果为空
			dto.setArrvOrgCode(new Date().toString());
		}
		//解决多选公共选择器传至后台的产品类型编码中存在空格问题（如FLF, FSF）
		if(StringUtils.isNotEmpty(dto.getProductCode())){
			dto.setProductCode(dto.getProductCode().replaceAll(" ",""));
		}
		List<PriceCouponDto> list = priceCouponDao.
					selectPriceCouponByCodition(dto, start, limit);
		
		//根据code封装name
		for(PriceCouponDto listDto:list){
			//根据区域code封装区域name
			PriceRegionEntity priceRegionEntity=new PriceRegionEntity();
			priceRegionEntity.setActive(FossConstants.ACTIVE);
			priceRegionEntity.setRegionNature(PricingConstants.PRICING_REGION);
			//1.封装出发区域名字
			priceRegionEntity.setRegionCode(listDto.getDeptOrgCode());
			List<PriceRegionEntity> regionDeptEntity = regionDao.
					searchRegionByCondition(priceRegionEntity, 0, 1);
			if(CollectionUtils.isNotEmpty(regionDeptEntity)){
				listDto.setDeptOrgName(regionDeptEntity.get(0).getRegionName());
			}
			//2.封装到达区域名字
			priceRegionEntity.setRegionCode(listDto.getArrvOrgCode());
			List<PriceRegionEntity> regionArrivEntity = regionDao.
					searchRegionByCondition(priceRegionEntity, 0, 1);
			if(CollectionUtils.isNotEmpty(regionDeptEntity)){
				listDto.setArrvOrgName(regionArrivEntity.get(0).getRegionName());
			}
			
			//根据产品code封装产品name
			//String strName1="";
			StringBuffer strName1 = new StringBuffer();
			String strProductItem=listDto.getProductCode();
			ProductDto productEntity=new ProductDto();
			productEntity.setActive(FossConstants.ACTIVE);
			if(StringUtil.isNotEmpty(strProductItem)){//产品类型一个或多个
				String[] productList=strProductItem.split(",");
				for(int i=0;i<productList.length;i++){
					productEntity.setCode(productList[i]);
					List<ProductEntity> productEntityList = bseProductService.
							findExternalProductByCondition(productEntity, null);
					if(CollectionUtils.isNotEmpty(productEntityList)){
						if(productList.length>0){
							//strName1+=productEntityList.get(0).getName()+",";
							strName1.append(productEntityList.get(0).getName());
							strName1.append(",");
						}else if(productList.length==0){
							strName1.append(productEntityList.get(0).getName());
							//strName1=productEntityList.get(0).getName();
						}
					}
				}
			}else{//产品类型为空
				strName1.append("无");
			//	strName1="无";	
			}
			if(strName1.toString().endsWith(",")){//去掉多产品类型编码拼接后出现的以逗号结尾的情况
			//	strName1=strName1.substring(0, strName1.length()-1);
				 String str = strName1.substring(0, strName1.length()-1);
				 strName1.setLength(0);
				 strName1.append(str);
			}
			listDto.setProductName(strName1.toString());
		}
		
		return list;
	}
	

	/**
	 * <p>增加折扣价格方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-9 下午4:28:26
	 * @see
	 */
	@Override
	@Transactional
	public int addCouponProgramItem(List<DiscountOrgEntity> startDepts,
			List<DiscountOrgEntity> endDepts, PriceCouponDto dto) {
		if(CollectionUtils.isNotEmpty(startDepts)&& CollectionUtils.isNotEmpty(endDepts)) {
			String currentOrgCode = getCurrentOrgCode();
			String currentUserCode = getCurrentUserCode();
			List<PriceEntity> priceEntityList = this.selectPriceEntityByCodition();
			if(priceEntityList != null && priceEntityList.size() > 0) {
				PriceEntity priceEntity = priceEntityList.get(0);
				String priceEntryId = priceEntity.getId();
				for (int i = 0; i < startDepts.size(); i++) {
					DiscountOrgEntity startOrg = (DiscountOrgEntity)startDepts.get(i);
					for (int j = 0; j < endDepts.size(); j++) {
						DiscountOrgEntity arrvOrg = (DiscountOrgEntity)endDepts.get(j);
						//做一次查询，确保同一方案明细中不存在相同数据
						if(this.checkSameItemsInOneScheme(startOrg,arrvOrg,dto,null)){
							//返回true，则说明存在重复,不允许添加、保存
							throw new PriceCouponException("同一方案中已选降价发券信息结果不允许重复或交叉", 
									"同一方案中已选降价发券信息结果不允许重复或交叉");
						}
						
						//封装计费规则实体PriceValuationEntity
						//市场活动信息、优惠券期限、产品信息等，来自前台
						PriceValuationEntity valuationEntity = new PriceValuationEntity();
						String valuationEntityId = UUIDUtils.getUUID();
						valuationEntity.setId(valuationEntityId);
						valuationEntity.setPricingEntryId(priceEntryId);
						valuationEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
						valuationEntity.setMarketingEventId(dto.getMarketId());
						valuationEntity.setMarketingEventCode(dto.getMarketCode());
						valuationEntity.setBeginTime(dto.getBeginDate());
						valuationEntity.setEndTime(dto.getEndDate());
						valuationEntity.setGoodsTypeId(dto.getGoodsTypeId());
						valuationEntity.setGoodsTypeCode(dto.getGoodsTypeCode());
						valuationEntity.setProductId(dto.getProductId());
						valuationEntity.setProductCode(dto.getProductCode());
						valuationEntity.setVersionNo(new Date().getTime());
						valuationEntity.setActive(FossConstants.INACTIVE);
						valuationEntity.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
						valuationEntity.setCurrencyCode("RMB");
						valuationEntity.setCode(PriceEntityConstants.PRICING_CODE_FRT);
						valuationEntity.setCreateUser(currentUserCode);
						valuationEntity.setCreateOrgCode(currentOrgCode);
						valuationEntity.setCreateDate(new Date());
						bsePriceValuationDao.insertSelective(valuationEntity);
						//封装起始目的组织网点实体类DiscountOrgEntity
						//始发和目的地点信息
						DiscountOrgEntity orgEntity = new DiscountOrgEntity();
						String orgEntityUUId = UUIDUtils.getUUID();
						orgEntity.setId(orgEntityUUId);
						orgEntity.setDeptOrgId(startOrg.getDeptOrgId());
						orgEntity.setDeptOrgCode(startOrg.getDeptOrgCode());
						orgEntity.setDeptOrgTypeCode(startOrg.getDeptOrgTypeCode());
						orgEntity.setArrvOrgId(arrvOrg.getArrvOrgId());
						orgEntity.setArrvOrgCode(arrvOrg.getArrvOrgCode());
						orgEntity.setArrvOrgTypeCode(arrvOrg.getArrvOrgTypeCode());
						orgEntity.setBeginTime(dto.getBeginDate());
						orgEntity.setEndTime(dto.getEndDate());
						orgEntity.setCreateUserCode(currentUserCode);
						orgEntity.setCreateOrgCode(currentOrgCode);
						orgEntity.setCreateTime(new Date());
						orgEntity.settSrvPricingValuationId(valuationEntityId);
						orgEntity.setActive(FossConstants.INACTIVE);
						orgEntity.setVersionNo(new Date().getTime());
						bseDiscountOrgDao.insertSelective(orgEntity);
						//封装计价方式明细PriceCriteriaDetailEntity
						//重量、体积的开始结束范围、返券系数、返券时间等， 来自前台
						PriceCriteriaDetailEntity criteriaDetailEntity = new PriceCriteriaDetailEntity();
						String criteriaDetailEntityUUId = UUIDUtils.getUUID();
						criteriaDetailEntity.setId(criteriaDetailEntityUUId);
						criteriaDetailEntity.setCaculateType(dto.getCaculateType());
						criteriaDetailEntity.setLeftrange(dto.getLeftRange());
						criteriaDetailEntity.setRightrange(dto.getRightRange());
						criteriaDetailEntity.settSrvPriceRuleId(PricingConstants.PRICE_RULE_DISCOUNTRATE_ID);
						criteriaDetailEntity.setCouponRate(dto.getCouponRate());
						criteriaDetailEntity.setWeightLeftRange(dto.getWeightLeftRange());
						criteriaDetailEntity.setWeightRightRange(dto.getWeightRightRange());
						criteriaDetailEntity.setVolumeLeftRange(dto.getVolumeLeftRange());
						criteriaDetailEntity.setVolumeRightRange(dto.getVolumeRightRange());
						criteriaDetailEntity.setIsPickUp(dto.getIsPickUp());
						criteriaDetailEntity.setMinFee(dto.getMinFee());
						criteriaDetailEntity.setActive(FossConstants.INACTIVE);
						criteriaDetailEntity.setVersionNo(new Date().getTime());
						criteriaDetailEntity.setPricingValuationId(valuationEntityId);
						priceRuleDetailDao.insertSelective(criteriaDetailEntity);
					}
				}
			}else{
				throw new PriceCouponException("计价条目为空！", "计价条目为空！");
			}
		} else {
			throw new PriceCouponException("没有选择始发或目的地点 !", "没有选择始发或目的地点 !");
		}
		return NumberConstants.NUMBER_1;
	}
	/*
	 * 检查同一方案中已选降价发券信息结果是否存在重复
	 * 返回false则说明不存在重复；返回true则说明存在重复
	 */
	public boolean checkSameItemsInOneScheme(DiscountOrgEntity startOrg,
			DiscountOrgEntity arrvOrg, PriceCouponDto dto,String isAddOrUpdate){
		
		boolean checkResult=false;//标识符字段
		PriceCouponDto priceDiscountDto=new PriceCouponDto();
		priceDiscountDto.setActive(FossConstants.INACTIVE);//可以新增明细则该方案未激活
		priceDiscountDto.setDeptOrgCode(startOrg.getDeptOrgCode());
		priceDiscountDto.setDeptOrgId(startOrg.getDeptOrgId());
		priceDiscountDto.setDeptOrgTypeCode(startOrg.getDeptOrgTypeCode());
		priceDiscountDto.setDeptOrgTypeName(startOrg.getDeptOrgTypeName());
		priceDiscountDto.setArrvOrgCode(arrvOrg.getArrvOrgCode());
		priceDiscountDto.setArrvOrgId(arrvOrg.getArrvOrgId());
		priceDiscountDto.setArrvOrgTypeCode(arrvOrg.getArrvOrgTypeCode());
		priceDiscountDto.setArrvOrgTypeName(arrvOrg.getArrvOrgTypeName());
		priceDiscountDto.setProductCode(dto.getProductCode());
		priceDiscountDto.setIsPickUp(dto.getIsPickUp());
		
		//不加重量、体积查询同一方案中是否存在重量、体积交叉明细
		List<PriceCouponDto> queryList = priceCouponDao.selectPriceCouponAllByCodition(priceDiscountDto);
		if(CollectionUtils.isNotEmpty(queryList)){
			checkResult = isCheckWeightAndVolume(dto, isAddOrUpdate,
					checkResult, priceDiscountDto, queryList);
		}
		//加重量、体积查询同一方案中是否存在重复明细
		if(checkResult!=true){//
			priceDiscountDto.setWeightLeftRange(dto.getWeightLeftRange());
			priceDiscountDto.setWeightRightRange(dto.getWeightRightRange());
			priceDiscountDto.setVolumeLeftRange(dto.getVolumeLeftRange());
			priceDiscountDto.setVolumeRightRange(dto.getVolumeRightRange());
			List<PriceCouponDto> list = priceCouponDao.selectPriceCouponAllByCodition(priceDiscountDto);
			if(CollectionUtils.isNotEmpty(list)){
				checkResult = isCheckMarket(dto, isAddOrUpdate, checkResult, list);
			}
		}
		
		return checkResult;
	}
	private boolean isCheckMarket(PriceCouponDto dto, String isAddOrUpdate,
			boolean checkResult, List<PriceCouponDto> list) {
		//查询存在,判断是否属于同一方案
		for(PriceCouponDto checkdto:list){
			if("UPDATE".equals(isAddOrUpdate)){//修改时dto的MarketCode为空，故写死为相等
				dto.setMarketCode(checkdto.getMarketCode());
			}
			if(StringUtils.equals(dto.getMarketCode(), checkdto.getMarketCode())){
				if("UPDATE".equals(isAddOrUpdate)){//如果是修改，需要将查询出来的本条正在修改的数据剔除，不考虑重复或交叉
					if(StringUtils.equals(checkdto.getPriceValuationId(), dto.getPriceValuationId())){
						//单纯判断ID不够，比如重量体积存在交叉而其ID肯定相同，所以字段全相等才continue
						continue;
					}
					checkResult=true;
					break;//只要当前操作的Dto所属方案与查询所得的任意一条属于同一方案，则返回true
				}else{
					checkResult=true;
					break;//只要当前操作的Dto所属方案与查询所得的任意一条属于同一方案，则返回true
				}
			}
		}
		return checkResult;
	}
	private boolean isCheckWeightAndVolume(PriceCouponDto dto,
			String isAddOrUpdate, boolean checkResult,
			PriceCouponDto priceDiscountDto, List<PriceCouponDto> queryList) {
		//查询存在,判断是否属于同一方案
		for(PriceCouponDto checkdto:queryList){
			priceDiscountDto.setWeightLeftRange(dto.getWeightLeftRange());
			priceDiscountDto.setWeightRightRange(dto.getWeightRightRange());
			priceDiscountDto.setVolumeLeftRange(dto.getVolumeLeftRange());
			priceDiscountDto.setVolumeRightRange(dto.getVolumeRightRange());
			if("UPDATE".equals(isAddOrUpdate)){//修改时dto的MarketCode为空，故写死为相等
				dto.setMarketCode(checkdto.getMarketCode());
			}
			if(StringUtils.equals(dto.getMarketCode(), checkdto.getMarketCode())
					&& this.checkRangeInteval(priceDiscountDto, checkdto)){//重量、体积不可交叉
				if("UPDATE".equals(isAddOrUpdate)){//如果是修改，需要将查询出来的本条正在修改的数据剔除，不考虑重复或交叉
					if(StringUtils.equals(checkdto.getPriceValuationId(), dto.getPriceValuationId())){
						//单纯判断ID不够，比如重量体积存在交叉而其ID肯定相同，所以字段全相等才continue
						continue;
					}
					checkResult=true;
					break;//只要当前操作的Dto所属方案与查询所得的任意一条属于同一方案，则返回true
				}else{
					checkResult=true;
					break;//只要当前操作的Dto所属方案与查询所得的任意一条属于同一方案，则返回true
				}
			}
		}
		return checkResult;
	}
	/**
	 * <p>查询降价发券主方案信息</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午3:37:49
	 * @param marketEventId
	 * @see
	 */
	@Override
	public PriceCouponVo selectCouponProgram(String marketEventId) {
		PriceCouponVo vo = new PriceCouponVo();
		MarketingSchemeEntity marketingEventEntity = marketingSchemeDao.selectByPrimaryKey(marketEventId);
		if(marketingEventEntity != null) {
			//根据code封装name（产品类型、订单来源、 客户等级、 客户行业、  线路区域要求）
			this.transformBox(marketingEventEntity);
			//封装降价发券方案实体MarketingSchemeEntity
			vo.setMarketingSchemeEntity(marketingEventEntity);
		}
		return vo;
	}
	/**
	 * <p>根据code封装name（产品类型、订单来源、 客户等级、 客户行业、  线路区域要求）</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午3:37:49
	 * @param marketingEventEntity
	 * @see
	 */
	public void transformBox(MarketingSchemeEntity marketingEventEntity){
		List<String> strName1=new ArrayList<String>(),strName2=new ArrayList<String>(),
				strName3=new ArrayList<String>(),strName4=new ArrayList<String>();
		//1、封装产品类型
		//封装产品类型多选公共选择器
		List<ProductItemEntity> proItemEntityList=new 
				ArrayList<ProductItemEntity>();//封装Store
		List<String> proItemValue=new ArrayList<String>();//封装Value
		String strProductItem=marketingEventEntity.getProductItem();
		ProductDto productEntity=new ProductDto();
		productEntity.setActive(FossConstants.ACTIVE);
		if(StringUtil.isNotEmpty(strProductItem)){//产品类型一个或多个
			String[] list=strProductItem.split(",");
			for(int i=0;i<list.length;i++){
				productEntity.setCode(list[i]);
				List<ProductEntity> entityList=bseProductService.
						findExternalProductByCondition(productEntity,null);
				if(CollectionUtils.isNotEmpty(entityList)){
					strName1.add(entityList.get(0).getName());
					//封装产品类型多选公共选择器
					ProductItemEntity proItemEntity=new ProductItemEntity();
					proItemEntity.setId(entityList.get(0).getId());
					proItemEntity.setName(entityList.get(0).getName());
					proItemEntity.setCode(entityList.get(0).getCode());
					proItemEntityList.add(proItemEntity);
					proItemValue.add(entityList.get(0).getCode());
				}
			}
		}
		//2、封装订单来源
		//封装订单来源多选公共选择器
		List<CommonOrderSourceEntity> ordSouEntityList=new 
				ArrayList<CommonOrderSourceEntity>();//封装Store
		List<String> ordSouValue=new ArrayList<String>();//封装Value
		String strOrdersource=marketingEventEntity.getOrderSource();
		CommonOrderSourceEntity orderSourceEntity=new CommonOrderSourceEntity();
		if(StringUtil.isNotEmpty(strOrdersource)){//订单来源一个或多个
			String[] list=strOrdersource.split(",");
			for(int i=0;i<list.length;i++){
				orderSourceEntity.setSourceCode(list[i]);
				List<CommonOrderSourceEntity> entityList=commonOrderSourceService.
						searchOrderSourceByCondition(orderSourceEntity, 1, 0);
				if(CollectionUtils.isNotEmpty(entityList)){
					strName2.add(entityList.get(0).getSourceName());
					//封装订单来源多选公共选择器
					CommonOrderSourceEntity ordSouEntity=new CommonOrderSourceEntity();
					ordSouEntity.setId(entityList.get(0).getId());
					ordSouEntity.setSourceName(entityList.get(0).getSourceName());
					ordSouEntity.setSourceCode(entityList.get(0).getSourceCode());
					ordSouEntityList.add(ordSouEntity);
					ordSouValue.add(entityList.get(0).getSourceCode());
				}
			}
		}
		//3、封装客户等级
		//封装客户等级多选公共选择器
		List<CommonCustomerDegreeEntity> cusDgrEntityList=new 
										ArrayList<CommonCustomerDegreeEntity>();//封装Store
		List<String> cusDgrValue=new ArrayList<String>();//封装Value
		String strCustomerDegree=marketingEventEntity.getCustomerDegree();
		CommonCustomerDegreeEntity customerDegreeEntity=new CommonCustomerDegreeEntity();
		if(StringUtil.isNotEmpty(strCustomerDegree)){//客户等级一个或多个
			String[] list=strCustomerDegree.split(",");
			for(int i=0;i<list.length;i++){
				customerDegreeEntity.setDegreeCode(list[i]);
				List<CommonCustomerDegreeEntity> entityList=commonCustomerDegreeService.
						searchCustomerDegreeByCondition(customerDegreeEntity, 1, 0);
				if(CollectionUtils.isNotEmpty(entityList)){
					strName3.add(entityList.get(0).getDegreeName());
					//封装客户行业多选公共选择器
					CommonCustomerDegreeEntity cusDgrEntity=new CommonCustomerDegreeEntity();
					cusDgrEntity.setId(entityList.get(0).getId());
					cusDgrEntity.setDegreeName(entityList.get(0).getDegreeName());
					cusDgrEntity.setDegreeCode(entityList.get(0).getDegreeCode());
					cusDgrEntityList.add(cusDgrEntity);
					cusDgrValue.add(entityList.get(0).getDegreeCode());
				}
			}
		}
		//4、封装客户行业
		String strCustomerProfession=marketingEventEntity.getCustomerProfession();
		CommonCustomerProfessionEntity customerProfessionEntity=new CommonCustomerProfessionEntity();
		//封装客户行业多选公共选择器
		List<CommonCustomerProfessionEntity> cusProEntityList=new 
								ArrayList<CommonCustomerProfessionEntity>();//封装Store
		List<String> cusProValue=new ArrayList<String>();//封装Value
		if(StringUtil.isNotEmpty(strCustomerProfession)){//客户行业一个或多个
			String[] list=strCustomerProfession.split(",");
			for(int i=0;i<list.length;i++){
				customerProfessionEntity.setProfessionCode(list[i]);
				List<CommonCustomerProfessionEntity> entityList=commonCustomerProfessionService.
						searchCustomerProfessionByCondition(customerProfessionEntity, 1, 0);
				if(CollectionUtils.isNotEmpty(entityList)){
					strName4.add(entityList.get(0).getProfessionName());
					//封装客户行业多选公共选择器
					CommonCustomerProfessionEntity cusProEntity=new CommonCustomerProfessionEntity();
					cusProEntity.setId(entityList.get(0).getId());
					cusProEntity.setProfessionName(entityList.get(0).getProfessionName());
					cusProEntity.setProfessionCode(entityList.get(0).getProfessionCode());
					cusProEntityList.add(cusProEntity);
					cusProValue.add(entityList.get(0).getProfessionCode());
				}
			}
		}
		//5、封装线路区域要求
		String strLineRegion=marketingEventEntity.getLineRegion();
		if(strLineRegion!=null && strLineRegion.
				equals(DictionaryValueConstants.IS_LINE)){
			strLineRegion="线路";
		}else if(strLineRegion!=null && strLineRegion.
				equals(DictionaryValueConstants.IS_DEPARTURE_CITY)){
			strLineRegion="出发城市";
		}else{
			strLineRegion="无";
		}
		
		//填值
		marketingEventEntity.setProductItemCode(strProductItem);//产品类型编码封装
		marketingEventEntity.setOrderSourceCode(strOrdersource);//订单来源编码封装
		marketingEventEntity.setCustomerDegreeCode(strCustomerDegree);//客户等级编码封装
		marketingEventEntity.setCustomerProfessionCode(strCustomerProfession);//客户行业编码封装
		//封装产品类型多选公共选择器
		marketingEventEntity.setProItemEntityList(proItemEntityList);
		marketingEventEntity.setProItemValue(proItemValue);
		//封装订单来源多选公共选择器
		marketingEventEntity.setOrdSouEntityList(ordSouEntityList);
		marketingEventEntity.setOrdSouValue(ordSouValue);
		//封装客户等级多选公共选择器
		marketingEventEntity.setCusDgrEntityList(cusDgrEntityList);
		marketingEventEntity.setCusDgrValue(cusDgrValue);
		//封装客户行业多选公共选择器
		marketingEventEntity.setCusProEntityList(cusProEntityList);
		marketingEventEntity.setCusProValue(cusProValue);
		
		marketingEventEntity.setProductItem(strName1.toString());
		marketingEventEntity.setOrderSource(strName2.toString());
		marketingEventEntity.setCustomerDegree(strName3.toString());
		marketingEventEntity.setCustomerProfession(strName4.toString());
		marketingEventEntity.setLineRegion(strLineRegion);
		//返券时间为空或为0，设置为0
		if(marketingEventEntity.getCouponTimeToSend()==null
				||marketingEventEntity.getCouponTimeToSend().equals(new BigDecimal(0)) ){
			marketingEventEntity.setCouponTimeToSend(new BigDecimal(0));
			}
	}
	/**
	 * <p>新增折扣价格方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-8 下午6:56:25
	 * @param dto
	 * @see
	 */
	@Override
	@Transactional
	public PriceCouponVo addCouponProgram(MarketingSchemeEntity marketingEventEntity) {
		if(marketingEventEntity != null && StringUtil.isNotBlank(marketingEventEntity.getName())) {
			List<MarketingSchemeEntity> marketingEventEntities = marketingSchemeDao.queryMarketingEventByName(marketingEventEntity.getName());
			if(CollectionUtils.isNotEmpty(marketingEventEntities)) {
				throw new PriceCouponException("该方案名称已经存在,不能再次使用 !", "该方案名称已经存在,不能再次使用 !");
			}
			String currentOrgCode = getCurrentOrgCode();
			String currentUserCode = getCurrentUserCode();
			//折扣方案NAME、有效期等来自前端
			marketingEventEntity.setId(UUIDUtils.getUUID());
			String marketCode = generateProgramCode();
			if(StringUtil.isNotBlank(marketCode)) {
				marketingEventEntity.setCode(marketCode);
			} else {
				throw new PriceCouponException("方案编号无法生成 !", "方案编号无法生成 !");
			}
			marketingEventEntity.setCreateUser(currentUserCode);
			marketingEventEntity.setCreateOrgCode(currentOrgCode);
			marketingEventEntity.setCreateDate(new Date());
			marketingEventEntity.setVersionNo(new Date().getTime());
			marketingEventEntity.setPriceRegionCode("");
			marketingEventEntity.setPriceRegionId("");
			marketingEventEntity.setActive(FossConstants.INACTIVE);
			marketingEventEntity.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
			if(marketingEventEntity.getCouponTimeToSend()==null
					||marketingEventEntity.getCouponTimeToSend().equals(new BigDecimal(0))){
				marketingEventEntity.setCouponTimeToSend(new BigDecimal(0));
				}
			marketingSchemeDao.insertSelective(marketingEventEntity);
		} else {
			throw new PriceCouponException("折扣方案的CODE或NAME为空 !", "折扣方案的CODE或NAME为空 !");
		}
		PriceCouponVo vo = new PriceCouponVo();
		//根据code封装name（产品类型、订单来源、 客户等级、 客户行业、  线路区域要求）
		this.transformBox(marketingEventEntity);
		vo.setMarketingSchemeEntity(marketingEventEntity);
		return vo;
	}
	
	/**
	 * <p>修改价格折扣方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-20 下午4:25:15
	 * @param marketingEventEntity
	 * @see
	 */
	@Override
	@Transactional
	public PriceCouponVo updateCouponProgram(
			MarketingSchemeEntity marketingEventEntity) {
		if(marketingEventEntity != null && marketingEventEntity.getId() != null) {
			List<MarketingSchemeEntity> marketingEventEntities = marketingSchemeDao.queryMarketingEventByName(marketingEventEntity.getName());
			if(CollectionUtils.isNotEmpty(marketingEventEntities)) {
				if (marketingEventEntities.size() > 1) {
					throw new PriceCouponException("该方案名称：" + marketingEventEntity.getName() + "已经存在，不能再次使用 !", 
							"该方案名称：" + marketingEventEntity.getName() + "已经存在，不能再次使用 !");
				} else if (marketingEventEntities.size() == 1) {
					if (marketingEventEntity.getCode() != null) {
						//根据编码判断，不能将方案名称修改成系统中其他已存在的方案名称
						if (!marketingEventEntity.getCode().equals(marketingEventEntities.get(0).getCode())) {
							throw new PriceCouponException("您修改的方案名称：" + marketingEventEntity.getName() + 
									"在系统中已经存在，不能再次使用 !", "您修改的方案名称：" + marketingEventEntity.getName() + 
									"在系统中已经存在，不能再次使用 !");
						}
					}
				}
			}
			MarketingSchemeEntity eventEntity = marketingSchemeDao.selectByPrimaryKey(marketingEventEntity.getId());
			if(eventEntity != null && eventEntity.getId().equals(marketingEventEntity.getId())) {
				String currentOrgCode = getCurrentOrgCode();
				String currentUserCode = getCurrentUserCode();
				PriceCouponDto priceDiscountDto = new PriceCouponDto();
				priceDiscountDto.setMarketId(marketingEventEntity.getId());
				List<PriceCouponDto> priceDiscountDtos = priceCouponDao.selectPriceCouponByCodition(priceDiscountDto);
				if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
					for (int i = 0; i < priceDiscountDtos.size(); i++) {
						PriceCouponDto dto = priceDiscountDtos.get(i);
						PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
						priceValuationEntity.setId(dto.getPriceValuationId());
						priceValuationEntity.setBeginTime(marketingEventEntity.getBeginTime());
						priceValuationEntity.setEndTime(marketingEventEntity.getEndTime());
						priceValuationEntity.setModifyUser(currentUserCode);
						priceValuationEntity.setModifyDate(new Date());
						priceValuationEntity.setModifyOrgCode(currentOrgCode);
						priceValuationEntity.setVersionNo(new Date().getTime());
						bsePriceValuationDao.updateValuation(priceValuationEntity);
						DiscountOrgEntity discountOrgEntity = new DiscountOrgEntity();
						discountOrgEntity.setId(dto.getDiscountOrgId());
						discountOrgEntity.setBeginTime(marketingEventEntity.getBeginTime());
						discountOrgEntity.setEndTime(marketingEventEntity.getEndTime());
						discountOrgEntity.setModifyUser(currentUserCode);
						discountOrgEntity.setModifyTime(new Date());
						discountOrgEntity.setModifyOrgCode(currentOrgCode);
						discountOrgEntity.setVersionNo(new Date().getTime());
						bseDiscountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
					}
				}
				eventEntity.setCode(marketingEventEntity.getCode());
				eventEntity.setName(marketingEventEntity.getName());
				eventEntity.setBeginTime(marketingEventEntity.getBeginTime());
				eventEntity.setEndTime(marketingEventEntity.getEndTime());
				eventEntity.setProductItem(marketingEventEntity.getProductItem());
				eventEntity.setOrderSource(marketingEventEntity.getOrderSource());
				eventEntity.setCustomerDegree(marketingEventEntity.getCustomerDegree());
				eventEntity.setCustomerProfession(marketingEventEntity.getCustomerProfession());
				eventEntity.setLineRegion(marketingEventEntity.getLineRegion());
				eventEntity.setAvailablePeriod(marketingEventEntity.getAvailablePeriod());
				eventEntity.setCouponTimeToSend(marketingEventEntity.getCouponTimeToSend());
				eventEntity.setRemark(marketingEventEntity.getRemark());
				eventEntity.setModifyUser(currentUserCode);
				eventEntity.setModifyDate(new Date());
				eventEntity.setModifyOrgCode(currentOrgCode);
				eventEntity.setVersionNo(new Date().getTime());
				if(eventEntity.getCouponTimeToSend()==null
						||eventEntity.getCouponTimeToSend().equals(new BigDecimal(0))){
					eventEntity.setCouponTimeToSend(new BigDecimal(0));
					}
				marketingSchemeDao.updateByPrimaryKeySelective(eventEntity);
			} else {
				throw new PriceCouponException("没有查询到相应的市场活动实体记录 !", "没有查询到相应的市场活动实体记录 !");
			}
		} else {
			throw new PriceCouponException("没有查询到相应的市场活动实体记录 !", "没有查询到相应的市场活动实体记录 !");
		}
		//返回给前台主方案的封装信息
		PriceCouponVo vo = new PriceCouponVo();
		//根据code封装name（产品类型、订单来源、 客户等级、 客户行业、  线路区域要求）
		this.transformBox(marketingEventEntity);
		vo.setMarketingSchemeEntity(marketingEventEntity);
		return vo;
	}
	
	/**
	 * @Description: 立即中止折扣方案
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午1:56:31
	 * @param 
	 * @version V1.0
	 */
	@Override
	@Transactional
	public void terminateImmediatelyCouponProgram(String marketEventId, Date endTime) {
		if(marketEventId != null && !"".equals(marketEventId)) {
			MarketingSchemeEntity eventEntity = marketingSchemeDao.selectByPrimaryKey(marketEventId);
			if(eventEntity != null && marketEventId.equals(eventEntity.getId())) {
				if(StringUtil.equals(eventEntity.getActive(), FossConstants.INACTIVE)) {
					throw new PriceCouponException("该方案尚未激活,不能中止 !", "该方案尚未激活,不能中止 !");
				}
				if(endTime.after(eventEntity.getEndTime())){
        		    throw new PricePlanException("中止日期不能延长原方案所制定的活动结束日期!",null);
        		}
				if(endTime.before(new Date())){
		    	    throw new FlightPricePlanException("中止日期必须大于当前营业日期!",null);
		    	}
				PriceCouponDto priceDiscountDto = new PriceCouponDto();
				priceDiscountDto.setMarketId(marketEventId);
				//方案
				eventEntity.setEndTime(endTime);
				eventEntity.setModifyDate(new Date());
				eventEntity.setModifyUser(getCurrentUserCode());
				eventEntity.setModifyOrgCode(getCurrentOrgCode());
				marketingSchemeDao.updateByPrimaryKeySelective(eventEntity);
				
				//明细
				List<PriceCouponDto> list2 = priceCouponDao.selectPriceCouponByCodition(priceDiscountDto);
				if(list2 != null && list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						PriceCouponDto dto = list2.get(i);
						//复制计费规则
						PriceValuationEntity priceValuationEntity = bsePriceValuationDao.selectByPrimaryKey(dto.getPriceValuationId());
						priceValuationEntity.setEndTime(endTime);
						priceValuationEntity.setModifyDate(new Date());
						priceValuationEntity.setModifyUser(getCurrentUserCode());
						priceValuationEntity.setModifyOrgCode(getCurrentOrgCode());
						bsePriceValuationDao.updateValuation(priceValuationEntity);
						//复制市场活动适用渠道
						DiscountOrgEntity discountOrgEntity = bseDiscountOrgDao.selectByPrimaryKey(dto.getDiscountOrgId());
						discountOrgEntity.setEndTime(endTime);
						discountOrgEntity.setModifyDate(new Date());
						discountOrgEntity.setModifyUser(getCurrentUserCode());
						discountOrgEntity.setModifyOrgCode(getCurrentOrgCode());
						bseDiscountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
					}
				}
			}
		}
	}
	
	/**
	 * <p>作废降价发券方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午2:20:48
	 * @param marketingEventId
	 * @see
	 */
	@Override
	@Transactional
	public void deleteCouponProgram(List<String> marketingEventIds) {
		if(marketingEventIds != null && marketingEventIds.size() > 0) {
			for (int k = 0; k < marketingEventIds.size(); k++) {
				String marketEventId = marketingEventIds.get(k);
				marketingSchemeDao.deleteByPrimaryKey(marketEventId);
			}
		}
	}

	/**
	 * <p>立即修改价格折扣方案状态</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午3:59:57
	 * @param marketingEventEntity
	 * @see
	 */
	@Override
	@Transactional
	public void activateImmediatelyCouponProgram(String marketEventId, Date beginTime) {
		MarketingSchemeEntity eventEntity = marketingSchemeDao.selectByPrimaryKey(marketEventId);
		if(eventEntity != null && eventEntity.getId().equals(marketEventId)) {
			if(FossConstants.ACTIVE.equals(eventEntity.getActive())) {
				throw new PriceCouponException("该方案已经激活,不能再次激活","该方案已经激活,不能再次激活");
			}
			List<MarketingSchemeEntity> marketingEventEntities = marketingSchemeDao.selectByMarketCode(eventEntity.getCode(), beginTime);
			if(CollectionUtils.isNotEmpty(marketingEventEntities)) {
				for (MarketingSchemeEntity marketingEventEntity : marketingEventEntities) {
					terminateImmediatelyCouponProgram(marketingEventEntity.getId(), beginTime);
				}
			} 
			PriceCouponDto priceDiscountDto = new PriceCouponDto();
			priceDiscountDto.setMarketId(marketEventId);
			List<PriceCouponDto> alllist = priceCouponDao.selectPriceCouponAllByCodition(priceDiscountDto);
			if(CollectionUtils.isNotEmpty(alllist)) {
				for (int i = 0; i < alllist.size(); i++) {
					PriceCouponDto dto = alllist.get(i);
					checkProgramIntersection(dto);
				}
				
				List<PriceCouponDto> list = priceCouponDao.selectPriceCouponByCodition(priceDiscountDto);
				for (int i = 0; i < list.size(); i++) {
					PriceCouponDto dto = list.get(i);
					PriceValuationEntity priceValuationEntity = new PriceValuationEntity();
					priceValuationEntity.setId(dto.getPriceValuationId());
					priceValuationEntity.setActive(FossConstants.ACTIVE);
					bsePriceValuationDao.updateValuation(priceValuationEntity);
					
					PriceCriteriaDetailEntity priceCriteriaDetailEntity = new PriceCriteriaDetailEntity();
					priceCriteriaDetailEntity.setId(dto.getPriceCriteriaId());
					priceCriteriaDetailEntity.setActive(FossConstants.ACTIVE);
					priceRuleDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
					
					DiscountOrgEntity discountOrgEntity = new DiscountOrgEntity();
					discountOrgEntity.setId(dto.getDiscountOrgId());
					discountOrgEntity.setActive(FossConstants.ACTIVE);
					bseDiscountOrgDao.updateByPrimaryKeySelective(discountOrgEntity);
				}
			} else {
				throw new PriceCouponException("该方案没有具体明细,不能激活", "该方案没有具体明细,不能激活");
			}
			
			eventEntity.setBeginTime(beginTime);
			eventEntity.setActive(FossConstants.ACTIVE);
			marketingSchemeDao.updateByPrimaryKeySelective(eventEntity);
			
		}
	}
	
	/**
	 * <p>拷贝降价发券方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午3:04:15
	 * @param
	 * @see
	 */
	@Override
	@Transactional
	public PriceCouponVo copyCouponProgram(String marketEventId) {
		MarketingSchemeEntity eventEntity2 = null;
		if(marketEventId != null && !"".equals(marketEventId)) {
			MarketingSchemeEntity eventEntity = marketingSchemeDao.selectByPrimaryKey(marketEventId);
			if(eventEntity != null && marketEventId.equals(eventEntity.getId())) {
				PriceCouponDto priceDiscountDto = new PriceCouponDto();
				priceDiscountDto.setMarketId(marketEventId);
				//方案
				eventEntity2 = new MarketingSchemeEntity();
				try {
					PropertyUtils.copyProperties(eventEntity2, eventEntity);
				} catch (IllegalAccessException e) {
					log.info(e.getMessage(), e);
				} catch (InvocationTargetException e) {
					log.info(e.getMessage(), e);
				} catch (NoSuchMethodException e) {
					log.info(e.getMessage(), e);
				}
				String eventEntityUUId = UUIDUtils.getUUID();
				eventEntity2.setId(eventEntityUUId);
				//封装marketingSchemeEntity
				//进行数据库插入操作
				eventEntity2.setActive(FossConstants.INACTIVE);
				eventEntity2.setVersionNo(new Date().getTime());
				eventEntity2.setBeginTime(getNextBusinessDate());
				eventEntity2.setEndTime(getMaxBusinessDate());
				eventEntity2.setCreateDate(new Date());
				eventEntity2.setCreateUser(getCurrentUserCode());
				eventEntity2.setCreateOrgCode(getCurrentOrgCode());
				eventEntity2.setModifyDate(new Date());
				eventEntity2.setModifyUser(getCurrentUserCode());
				eventEntity2.setModifyOrgCode(getCurrentOrgCode());
				marketingSchemeDao.insertSelective(eventEntity2);
				
				//明细
				List<PriceCouponDto> list2 = priceCouponDao.selectPriceCouponByCodition(priceDiscountDto);
				if(list2 != null && list2.size() > 0) {
					for (int i = 0; i < list2.size(); i++) {
						copyProperties(eventEntityUUId, list2, i);
					}
				}
			}
		}
		PriceCouponVo vo = new PriceCouponVo();
		vo.setMarketingSchemeEntity(eventEntity2);
		return vo;
	}
	private void copyProperties(String eventEntityUUId,
			List<PriceCouponDto> list2, int i) {
		PriceCouponDto dto = list2.get(i);
		//复制计费规则
		PriceValuationEntity priceValuationEntity = bsePriceValuationDao.selectByPrimaryKey(dto.getPriceValuationId());
		PriceValuationEntity priceValuationEntity2 = new PriceValuationEntity();
		try {
			if(priceValuationEntity != null){
				PropertyUtils.copyProperties(priceValuationEntity2, priceValuationEntity);
			}
		} catch (IllegalAccessException e) {
			log.info(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.info(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.info(e.getMessage(), e);
		}
		String valuationEntityUUId = UUIDUtils.getUUID();
		priceValuationEntity2.setId(valuationEntityUUId);
		priceValuationEntity2.setMarketingEventId(eventEntityUUId);
		priceValuationEntity2.setVersionNo(new Date().getTime());
		priceValuationEntity2.setActive(FossConstants.INACTIVE);
		priceValuationEntity2.setBeginTime(getNextBusinessDate());
		priceValuationEntity2.setEndTime(getMaxBusinessDate());
		priceValuationEntity2.setCreateDate(new Date());
		priceValuationEntity2.setCreateUser(getCurrentUserCode());
		priceValuationEntity2.setCreateOrgCode(getCurrentOrgCode());
		priceValuationEntity2.setModifyDate(new Date());
		priceValuationEntity2.setModifyUser(getCurrentUserCode());
		priceValuationEntity2.setModifyOrgCode(getCurrentOrgCode());
		bsePriceValuationDao.insertSelective(priceValuationEntity2);
		//复制市场活动适用渠道
		DiscountOrgEntity discountOrgEntity = bseDiscountOrgDao.selectByPrimaryKey(dto.getDiscountOrgId());
		DiscountOrgEntity discountOrgEntity2 = new DiscountOrgEntity();
		try {
			if(discountOrgEntity != null){
				PropertyUtils.copyProperties(discountOrgEntity2, discountOrgEntity);
			}
		} catch (IllegalAccessException e) {
			log.info(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.info(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.info(e.getMessage(), e);
		}
		String discountOrgEntityUUId = UUIDUtils.getUUID();
		discountOrgEntity2.setId(discountOrgEntityUUId);
		discountOrgEntity2.settSrvPricingValuationId(valuationEntityUUId);
		discountOrgEntity2.setVersionNo(new Date().getTime());
		discountOrgEntity2.setActive(FossConstants.INACTIVE);
		discountOrgEntity2.setBeginTime(getNextBusinessDate());
		discountOrgEntity2.setEndTime(getMaxBusinessDate());
		discountOrgEntity2.setCreateDate(new Date());
		discountOrgEntity2.setCreateUser(getCurrentUserCode());
		discountOrgEntity2.setCreateOrgCode(getCurrentOrgCode());
		discountOrgEntity2.setModifyDate(new Date());
		discountOrgEntity2.setModifyUser(getCurrentUserCode());
		discountOrgEntity2.setModifyOrgCode(getCurrentOrgCode());
		bseDiscountOrgDao.insertSelective(discountOrgEntity2);
		//复制计价方式明细
		PriceCriteriaDetailEntity priceCriteriaDetailEntity = priceRuleDetailDao.selectByPrimaryKey(dto.getPriceCriteriaId());
		PriceCriteriaDetailEntity priceCriteriaDetailEntity2 = new PriceCriteriaDetailEntity();
		try {
			if(priceCriteriaDetailEntity!=null){
				PropertyUtils.copyProperties(priceCriteriaDetailEntity2, priceCriteriaDetailEntity);
			}
		} catch (IllegalAccessException e) {
			log.info(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			log.info(e.getMessage(), e);
		} catch (NoSuchMethodException e) {
			log.info(e.getMessage(), e);
		}
		String criteriaDetailEntityUUId = UUIDUtils.getUUID();
		priceCriteriaDetailEntity2.setId(criteriaDetailEntityUUId);
		priceCriteriaDetailEntity2.setPricingValuationId(valuationEntityUUId);
		priceCriteriaDetailEntity2.setVersionNo(new Date().getTime());
		priceCriteriaDetailEntity2.setActive(FossConstants.INACTIVE);
		priceRuleDetailDao.copyOriginalSelective(priceCriteriaDetailEntity2);
	}
	
	/**
	 * <p>删除价格折扣方案明细</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午9:31:58
	 * @param priceValuationId
	 * @see
	 */
	@Override
	@Transactional
	public void deleteCouponProgramItem(String priceValuationId) {
		if(StringUtil.isNotBlank(priceValuationId)) {
			PriceValuationEntity priceValuationEntity = bsePriceValuationDao.selectByPrimaryKey(priceValuationId);
			if(priceValuationEntity != null && priceValuationId.equals(priceValuationEntity.getId())) {
				bseDiscountOrgDao.deleteByPriceValuationId(priceValuationEntity.getId());
				priceRuleDetailDao.deleteCriteriaDetailByValuationId(priceValuationEntity.getId());
				bsePriceValuationDao.deleteByPrimaryKey(priceValuationId);
			}
		}
	}
	/**
	 * <p>单条修改价格折扣方案</p> 
	 * @author dujunhui-187862
	 * @date 2014-10-30 上午8:48:15
	 * @param 
	 * @see
	 */
	@Override
	@Transactional
	public void updateCouponProgramItem(PriceCouponDto discountDto) {
		if(discountDto==null){
			throw new PriceCouponException("价格发券方案明细为空");
		}
		if(StringUtil.isNotBlank(discountDto.getPriceValuationId())) {
			//做一次查询，确保同一方案明细中不存在相同数据
			//封装数据类型
			DiscountOrgEntity startOrg=new DiscountOrgEntity();
			DiscountOrgEntity arrvOrg=new DiscountOrgEntity();
			PriceCouponDto updateDto=new PriceCouponDto();
			if(discountDto!=null){
//				updateDto.setDeptOrgCode(discountDto.getDeptOrgCode());
//				updateDto.setDeptOrgTypeCode(discountDto.getDeptOrgTypeCode());
//				updateDto.setDeptOrgId(discountDto.getDeptOrgId());
//				updateDto.setDeptOrgTypeName(discountDto.getDeptOrgTypeName());
//				updateDto.setArrvOrgCode(discountDto.getArrvOrgCode());
//				updateDto.setArrvOrgTypeCode(discountDto.getArrvOrgTypeCode());
//				updateDto.setArrvOrgId(discountDto.getArrvOrgId());
//				updateDto.setArrvOrgTypeName(discountDto.getArrvOrgTypeName());
//				updateDto.setProductCode(discountDto.getProductCode());
//				updateDto.setIsPickUp(discountDto.getIsPickUp());
				try {
					PropertyUtils.copyProperties(updateDto, discountDto);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				
				startOrg.setDeptOrgCode(discountDto.getDeptOrgCode());
				startOrg.setDeptOrgId(discountDto.getDeptOrgId());
				startOrg.setDeptOrgTypeCode(discountDto.getDeptOrgTypeCode());
				startOrg.setDeptOrgTypeName(discountDto.getDeptOrgTypeName());
				
				arrvOrg.setArrvOrgCode(discountDto.getArrvOrgCode());
				arrvOrg.setArrvOrgId(discountDto.getArrvOrgId());
				arrvOrg.setArrvOrgTypeCode(discountDto.getArrvOrgTypeCode());
				arrvOrg.setArrvOrgTypeName(discountDto.getArrvOrgTypeName());
			}
			
			if(this.checkSameItemsInOneScheme(startOrg,arrvOrg,updateDto,"UPDATE")){
				//返回true，则说明存在重复,不允许添加、保存
				throw new PriceCouponException("同一方案中已选降价发券信息结果不允许重复或交叉", 
						"同一方案中已选降价发券信息结果不允许重复或交叉");
			}

			PriceValuationEntity priceValuationEntity = bsePriceValuationDao.selectByPrimaryKey(discountDto.getPriceValuationId());
			if(priceValuationEntity != null && StringUtil.equals(discountDto.getPriceValuationId(), priceValuationEntity.getId())) {
				PriceCouponDto priceDiscountDto = new PriceCouponDto();
				priceDiscountDto.setPriceValuationId(discountDto.getPriceValuationId());
				List<PriceCouponDto> list = priceCouponDao.selectPriceCouponByCodition(priceDiscountDto);
				if(CollectionUtils.isNotEmpty(list)) {
					for (int i = 0; i < list.size(); i++) {
						PriceCouponDto dto = list.get(i);
						PriceCriteriaDetailEntity priceCriteriaDetailEntity = priceRuleDetailDao.selectByPrimaryKey(dto.getPriceCriteriaId());
						if(priceCriteriaDetailEntity != null) {
							priceCriteriaDetailEntity.setCaculateType(discountDto.getCaculateType());
							priceCriteriaDetailEntity.setLeftrange(discountDto.getLeftRange());
							priceCriteriaDetailEntity.setRightrange(discountDto.getRightRange());
							priceCriteriaDetailEntity.setWeightLeftRange(discountDto.getWeightLeftRange());
							priceCriteriaDetailEntity.setWeightRightRange(discountDto.getWeightRightRange());
							priceCriteriaDetailEntity.setVolumeLeftRange(discountDto.getVolumeLeftRange());
							priceCriteriaDetailEntity.setVolumeRightRange(discountDto.getVolumeRightRange());
							priceCriteriaDetailEntity.setCouponRate(discountDto.getCouponRate());
							priceCriteriaDetailEntity.setIsPickUp(discountDto.getIsPickUp());
							priceCriteriaDetailEntity.setMinFee(discountDto.getMinFee());
							priceCriteriaDetailEntity.setModifyDate(new Date());
							priceCriteriaDetailEntity.setModifyUser(getCurrentUserCode());
							priceRuleDetailDao.updateCriteriaDetailByPrimaryKey(priceCriteriaDetailEntity);
						}
					}
					priceValuationEntity.setGoodsTypeId(discountDto.getGoodsTypeId());
					priceValuationEntity.setGoodsTypeCode(discountDto.getGoodsTypeCode());
					priceValuationEntity.setProductId(discountDto.getProductId());
					priceValuationEntity.setProductCode(discountDto.getProductCode());
					priceValuationEntity.setModifyUser(getCurrentUserCode());
					priceValuationEntity.setModifyDate(new Date());
					priceValuationEntity.setModifyOrgCode(getCurrentOrgCode());
					bsePriceValuationDao.updateValuation(priceValuationEntity);
				}
			}
		}
	}
	/**
	 * @Description: 检查条件交集
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:22:29
	 * @param priceDiscountDto
	 * @return
	 * @version V1.0
	 */
	private void checkProgramIntersection (PriceCouponDto priceDiscountDto) {
		PriceCouponDto selectDto = new PriceCouponDto();
		selectDto.setActive(FossConstants.ACTIVE);
		selectDto.setDeptOrgId(priceDiscountDto.getDeptOrgId());
		selectDto.setDeptOrgTypeCode(priceDiscountDto.getDeptOrgTypeCode());
		selectDto.setArrvOrgId(priceDiscountDto.getArrvOrgId());
		selectDto.setArrvOrgTypeCode(priceDiscountDto.getArrvOrgTypeCode());
		selectDto.setCaculateType(priceDiscountDto.getCaculateType());
		selectDto.setProductId(priceDiscountDto.getProductId());
		selectDto.setProductCode(priceDiscountDto.getProductCode());
		selectDto.setGoodsTypeId(priceDiscountDto.getGoodsTypeId());
		List<PriceCouponDto> priceDiscountDtos = priceCouponDao.selectPriceCouponAllByCodition(selectDto);
		if(priceDiscountDtos != null && priceDiscountDtos.size() > 0) {
			 for (int i = 0; i < priceDiscountDtos.size(); i++) {
				 PriceCouponDto checkDto = priceDiscountDtos.get(i);
				 if(checkDto.getPriceValuationId() == null || priceDiscountDto.getPriceValuationId() == null || (priceDiscountDto.getPriceValuationId().equals(checkDto.getPriceValuationId()))
						 ||checkDto.getMarketCode().equals(priceDiscountDto.getMarketCode())) {
					 continue;
				 } /*else  if(checkDto.getMarketCode().equals(priceDiscountDto.getMarketCode())) {
					 continue;
				 }*/ else {
					 boolean flag1 = this.checkDateInteval(priceDiscountDto, checkDto);
					 boolean flag2 = this.checkRangeInteval(priceDiscountDto, checkDto);
					 if(flag1 && flag2) {
						throw new PriceCouponException("该方案不能激活,与"+filterStr(checkDto.getMarketName())+"存在重合的信息", "该方案不能激活,与"+filterStr(checkDto.getMarketName())+"存在重合的信息");
					 } 
				 }
			}
		} 
	}
	private String filterStr(String str) {
		String result = str;
		result = result.replace("\"", "");
		return result;
	}
	/**
	 * @Description: 检查适用范围交集（返回false则不交叉，返回true则交叉）
	 * @author dujunhui-187862
	 * @date 2014-10-28 上午9:05:29
	 * @param 
	 * @return
	 * @version 
	 */
	private boolean checkRangeInteval(PriceCouponDto dto, PriceCouponDto checkDto) {
		boolean flag= true, //默认重量、体积存在交叉，标识位为true
				flagWeight1= false, 
				flagWeight2= false, 
				flagVolume1= false, 
				flagVolume2 = false;//
		//检查重量范围是否交叉
		if(checkDto.getWeightRightRange().doubleValue() <= dto.getWeightLeftRange().doubleValue()) {
			flagWeight1 = true;//重量不交叉情况1
		}
		if(checkDto.getWeightLeftRange().doubleValue() >= dto.getWeightRightRange().doubleValue()) {
			flagWeight2 = true;//重量不交叉情况2
		}
		
		//检查体积范围是否交叉
		if(checkDto.getVolumeRightRange().doubleValue() <= dto.getVolumeLeftRange().doubleValue()) {
			flagVolume1 = true;//体积不交叉情况1
		}
		if(checkDto.getVolumeLeftRange().doubleValue() >= dto.getVolumeRightRange().doubleValue()) {
			flagVolume2 = true;//体积不交叉情况2
		}
		//设置标识符
		if((flagWeight1==true||flagWeight2==true)&&(flagVolume1==true||flagVolume2==true)){
			//重量、体积均不存在交叉，标识符为false
			flag=false;
		}
		return flag;
	}
	/**
	 * @Description: 检查有效期范围交集（返回false则不交叉，返回true则交叉）
	 * @author dujunhui-187862
	 * @date 2014-10-25 下午4:27:29
	 * @param priceCouponDto
	 * @return
	 * @version V1.0
	 */
	private boolean checkDateInteval(PriceCouponDto dto, PriceCouponDto checkDto) {
		boolean flag = false;//默认时间不存在交叉，标识位为false
		boolean flag1=true,flag2=true;
		if(checkDto.getEndDate().getTime() < dto.getBeginDate().getTime()) {
			//当前方案dto开始时间在对比方案checkDto的结束时间之前，则时间不交叉
			flag1 = false;
		}
		if(dto.getEndDate().getTime() < checkDto.getBeginDate().getTime()) {
			//对比方案dto开始时间在当前方案checkDto的结束时间之前，则时间不交叉
			flag2 = false;
		}
		if(flag1 && flag2){//flag1、flag2都不为false则时间交叉
			flag=true;
		}
		return flag;
	}
	/**
	 * @Description: 根据计费规则主键查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-10-29 下午4:25:06
	 * @param priceValuationId
	 * @return
	 * @version
	 */
	@Override
	public PriceCouponDto selectPriceCouponItemByValuationId(
			String priceValuationId) {
		
		PriceCouponDto priceCouponDto=priceCouponDao.
				selectPriceCouponItemByValuationId(priceValuationId);
		
		//封装明细中的产品编码和产品名称
		List<String> strName1=new ArrayList<String>();
		//封装产品类型多选公共选择器
		List<ProductItemEntity> proItemEntityList=new 
				ArrayList<ProductItemEntity>();//封装Store
		List<String> proItemValue=new ArrayList<String>();//封装Value
		String strProductItem=priceCouponDto.getProductCode();
		
		ProductDto productEntity=new ProductDto();
		productEntity.setActive(FossConstants.ACTIVE);
		
		if(StringUtil.isNotEmpty(strProductItem)){//产品类型一个或多个
			String[] list=strProductItem.split(",");
			for(int i=0;i<list.length;i++){
				productEntity.setCode(list[i]);
				List<ProductEntity> entityList=bseProductService.
						findExternalProductByCondition(productEntity,null);
				if(CollectionUtils.isNotEmpty(entityList)){
					strName1.add(entityList.get(0).getName());
					//封装产品类型多选公共选择器
					ProductItemEntity proItemEntity=new ProductItemEntity();
					proItemEntity.setId(entityList.get(0).getId());
					proItemEntity.setName(entityList.get(0).getName());
					proItemEntity.setCode(entityList.get(0).getCode());
					proItemEntityList.add(proItemEntity);
					proItemValue.add(entityList.get(0).getCode());
				}
			}
		}
		
		//产品类型填值
		priceCouponDto.setProductName(strName1.toString());//产品类型名称封装
		//封装产品类型多选公共选择器
		priceCouponDto.setProductEntityList(proItemEntityList);
		priceCouponDto.setProductValue(proItemValue);
		
		return priceCouponDto;
	}

	/**
	 * 生成方案编号
	 * @author dujunhui-187862
	 * @date 2014-10-2 上午9:16:33
	 * @return 
	 */
	private String generateProgramCode() {
		String marketCode = marketingSchemeDao.
				getMarketingEventMaxCode(DictionaryValueConstants.TYPE_PRICE_COUPON);//活动类型
		String code = null;
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		int currentMonth = cal.get(Calendar.MONTH)+1;
		int currentDay = cal.get(Calendar.DAY_OF_MONTH);
		String currentYearStr =  String.valueOf(currentYear);
		String currentMonthStr = String.format("%02d", currentMonth);
		String currentDayStr = String.format("%02d", currentDay);
		if(StringUtil.isNotBlank(marketCode)) {
			String year = marketCode.substring(NumberConstants.NUMBER_4, NumberConstants.NUMBER_8);
			String month = marketCode.substring(NumberConstants.NUMBER_8, NumberConstants.NUMBER_10);
			String day = marketCode.substring(NumberConstants.NUMBER_10, NumberConstants.NUMBER_12);
			String seq = marketCode.substring(NumberConstants.NUMBER_12, NumberConstants.NUMBER_15);
			if(StringUtil.equals(year, currentYearStr) && StringUtil.equals(month, currentMonthStr) && StringUtil.equals(day, currentDayStr)) {
				code = "FQFA" + year + month + day;
				int num = Integer.parseInt(seq);
				num = num + 1;
				code = code + String.format("%03d", num);
			} else {
				code = "FQFA" + currentYearStr + currentMonthStr + currentDayStr + "001" ;
			}
		} else {
			code = "FQFA" + currentYearStr + currentMonthStr + currentDayStr + "001" ;
		}
		return code;
	}
	/**
	 * @Description: 获取有效期最大值
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午9:05:27
	 * @return
	 * @version
	 */
	private Date getMaxBusinessDate() {
    	Calendar cal = Calendar.getInstance();
    	cal.set(NumberConstants.NUMBER_2999, NumberConstants.NUMBER_11, NumberConstants.NUMBER_31);
    	Date maxDate = cal.getTime();
    	return maxDate;
    }
	/**
	 * @Description: 获取有效期起启值
	 * @author dujunhui-187862
	 * @date 2014-10-29 上午9:04:32
	 * @return
	 * @version
	 */
	private Date getNextBusinessDate() {
    	return new Date();
    }
	
	/**
	 * <p>获得当前部门值</p>
	 * @author dujunhui-187862
	 * @date 2014-10-1 下午6:25:57
	 * @return
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	/**
	 * @Description: 获得当前人
	 * @author dujunhui-187862
	 * @date 2014-10-1 下午6:26:33
	 * @return
	 * 
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}
	/**
	 *<p>批量插入</p>
	 *@author dujunhui-187862
	 *@date 2014-10-31 上午8:38:52
	 *@param excelDtos
	 *@param empCode
	*/
//	@Transactional
	@Override
	public int addDetailMore(List<DetailExcelDto> excelDtos,
			String empCode) {
		if(CollectionUtils.isEmpty(excelDtos)||StringUtils.isBlank(empCode)){
			throw new  PriceCouponException("数据存在重叠,不能做修改操作");
		}
		int count =0;
		//循环
		for (DetailExcelDto detailExcelDto : excelDtos) {
			int rowNum =detailExcelDto.getRowNum();
			PriceCriteriaDetailEntity entity =detailExcelDto.getPriceCriteriaDetailEntity();
			//添加创建人
			entity.setCreateUser(empCode);
			//执行新增
			int addReturn=addDetail(entity, rowNum);
			count+=addReturn;
			if(addReturn==0){
				break;
			}
		}
		return count;
	}
	/**
	 *<p>插入方法（用于批量导入排班使用）</p>
	 *@author dujunhui-187862
	 *@date 2014-10-31 上午8:25:24
	 *@param entity
	 *@param rowNum
	 *@return
	 */
	
	@Override
	public int addDetail(PriceCriteriaDetailEntity entity,int rowNum){
		//非空校验
		if(null ==entity||rowNum<1){
			throw new PriceCouponException("对象为空");
		}
		//返回值
		int result=1;
		
		//根据区域NAME模糊查询区域CODE、ID等
		PriceRegionEntity priceRegionNameEntity=new PriceRegionEntity();
		priceRegionNameEntity.setActive(FossConstants.ACTIVE);
		priceRegionNameEntity.setRegionNature(PricingConstants.PRICING_REGION);
		//封装startDepts
		List<DiscountOrgEntity> startDepts=new ArrayList<DiscountOrgEntity>();
		if(StringUtil.isNotBlank(entity.getDeptOrgName())){//出发部门名不为空
			priceRegionNameEntity.setRegionName(entity.getDeptOrgName());
			List<PriceRegionEntity> regionDeptNameEntity = regionDao.
					searchRegionByCondition(priceRegionNameEntity, 0, 1);
			if(CollectionUtils.isNotEmpty(regionDeptNameEntity)){
				DiscountOrgEntity orgEntity=new DiscountOrgEntity();
				orgEntity.setDeptOrgCode(regionDeptNameEntity.get(0).getRegionCode());
				orgEntity.setDeptOrgId(regionDeptNameEntity.get(0).getId());
				orgEntity.setDeptOrgTypeCode("Coupon_REGION");
				startDepts.add(orgEntity);
			}
		}
		//封装endDepts
		List<DiscountOrgEntity> endDepts=new ArrayList<DiscountOrgEntity>(); 
		if(StringUtil.isNotBlank(entity.getAvvrOrgName())){//到达部门名不为空
			priceRegionNameEntity.setRegionName(entity.getAvvrOrgName());
			List<PriceRegionEntity> regionDeptNameEntity = regionDao.
					searchRegionByCondition(priceRegionNameEntity, 0, 1);
			if(CollectionUtils.isNotEmpty(regionDeptNameEntity)){
				DiscountOrgEntity orgEntity=new DiscountOrgEntity();
				orgEntity.setArrvOrgCode(regionDeptNameEntity.get(0).getRegionCode());
				orgEntity.setArrvOrgId(regionDeptNameEntity.get(0).getId());
				orgEntity.setArrvOrgTypeCode("Coupon_REGION");
				endDepts.add(orgEntity);
			}
		}
		PriceCouponDto dto=new PriceCouponDto();
		//根据产品NAME查询CODE
		//String strCode="";
		StringBuffer strCode = new StringBuffer();
		String strProductItem=entity.getProductName();
		ProductDto productEntity=new ProductDto();
		productEntity.setActive(FossConstants.ACTIVE);
		if(StringUtil.isNotEmpty(strProductItem)){//产品类型一个或多个
			String[] productList=strProductItem.split(",");//EXCEL中的英文逗号
			for(int i=0;i<productList.length;i++){
				productEntity.setName(productList[i]);
				List<ProductEntity> productEntityList = bseProductService.
						findExternalProductByCondition(productEntity, null);
				if(CollectionUtils.isNotEmpty(productEntityList)){
					if(productList.length>0){//产品类型多个
						//strCode+=productEntityList.get(0).getCode()+",";
						strCode.append(productEntityList.get(0).getCode());
						strCode.append(",");
					}else if(productList.length==0){//产品类型一个
						//strCode=productEntityList.get(0).getCode();
						strCode.append(productEntityList.get(0).getCode());
					}
				}
			}
		}
		
		if(strCode.toString().endsWith(",")){//去掉多产品类型编码拼接后出现的以逗号结尾情况
			//strCode=strCode.substring(0, strCode.length()-1);
			String str = strCode.substring(0, strCode.length()-1);
			strCode.setLength(0);
			strCode.append(str);
		}
		//封装dto
		dto.setProductCode(strCode.toString());
		dto.setWeightLeftRange(entity.getWeightLeftRange());
		dto.setWeightRightRange(entity.getWeightRightRange());
		dto.setVolumeLeftRange(entity.getVolumeLeftRange());
		dto.setVolumeRightRange(entity.getVolumeRightRange());
		dto.setCouponRate(entity.getCouponRate());
		dto.setIsPickUp(entity.getIsPickUp());
		dto.setMarketCode(entity.getSchemeCode());
		
		//根据CODE查询MarketingScheme的ID、beginDate、endDate
		MarketingSchemeEntity marketintEvent=new  MarketingSchemeEntity();
		marketintEvent.setCode(entity.getSchemeCode());
		marketintEvent.setActive(FossConstants.INACTIVE);
		marketintEvent.setType(DictionaryValueConstants.TYPE_PRICE_COUPON);
		List<MarketingSchemeEntity> marketintEventList=marketingSchemeDao.queryMarketingSchemeList(marketintEvent, 0, 1);
		if(CollectionUtils.isNotEmpty(marketintEventList)){
			dto.setMarketId(marketintEventList.get(0).getId());
			dto.setBeginDate(marketintEventList.get(0).getBeginTime());
			dto.setEndDate(marketintEventList.get(0).getEndTime());
		}
		try{
			result=this.addCouponProgramItem(startDepts, endDepts, dto);
		}catch(Exception e){
			result=0;
		}
		return result;//每成功添加一条数据则返回1
	}
	
	/**
	 * @Description: 根据接送货参数查询折扣明细
	 * @author dujunhui-187862
	 * @date 2014-11-10 上午9:32:16
	 * @param 
	 * @return
	 * @version V1.0
	 */
	@Override
	public PriceCouponDto selectPriceCouponByConditionToQuery(String deptOrgId,
			String arrvOrgId,String productCode,BigDecimal weight,BigDecimal volume,
			String isPickUp,String calType,Date billTime) {
		return priceCouponDao.selectPriceCouponByConditionToQuery(deptOrgId, 
				arrvOrgId, productCode, weight, volume, isPickUp, 
				calType, billTime);
	}
	
}