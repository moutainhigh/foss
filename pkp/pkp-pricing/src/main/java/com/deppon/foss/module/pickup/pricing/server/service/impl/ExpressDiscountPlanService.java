package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.shared.util.classes.BeanUtils;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.EmployeeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExpressDiscountPlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IMarketingEventChannelDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressDiscountPlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IExpressPricePlanService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGoodsTypeService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IRegionExpressService;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExpressDiscountEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.MarketingEventChannelEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceRegionExpressEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceValuationEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ExpressDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.ExpressDiscountPlanException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.foundation.common.lang.DateUtil;
import com.google.inject.Inject;
/**
 * 
 * Copyright (C) 2015 Asiainfo-Linkage
 *
 *
 * @className:com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService
 * @description:快递折扣方案service实现类
 *
 * @version:v1.0.0
 * @author:DP-FOSS-YANGKANG
 *
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2015-1-9     DP-FOSS-YANGKANG       v1.0.0        create
 *
 *
 */
public class ExpressDiscountPlanService implements IExpressDiscountPlanService {
	/**
	 * 快递折扣方案主信息Dao
	 */
	@Inject
	private IExpressDiscountPlanDao expressDiscountPlanDao; 
	/**
	 *渠道信息Dao
	 */
	@Inject
	private IMarketingEventChannelDao  marketingEventChannelDao;
	
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
     * 快递区域信息service.
     */
    @Inject
    IRegionExpressService regionExpressService;
    
    
	/**
	 * 员工信息Service
	 */
	@Inject
	IEmployeeService employeeService;
	/**
	 * 快递价格方案Service
	 */
	@Inject
	IExpressPricePlanService  expressPricePlanService;
	/**
	 * 客户合同信息
	 */
	@Inject
	ICusBargainService cusBargainService;
	/**
	 * 设置快递折扣方案信息dao
	 */
	public void setExpressDiscountPlanDao(
			IExpressDiscountPlanDao expressDiscountPlanDao) {
		this.expressDiscountPlanDao = expressDiscountPlanDao;
	}
	/**
	 * 设置渠道信息dao
	 */
	public void setMarketingEventChannelDao(
			IMarketingEventChannelDao marketingEventChannelDao) {
		this.marketingEventChannelDao = marketingEventChannelDao;
	}
	
	
	/**
	 * 设置产品信息service
	 */
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	/**
	 * 设置货物信息service
	 */
	public void setGoodsTypeService(IGoodsTypeService goodsTypeService) {
		this.goodsTypeService = goodsTypeService;
	}
	/**
	 * 设置快递区域信息service
	 */
	public void setRegionExpressService(IRegionExpressService regionExpressService) {
		this.regionExpressService = regionExpressService;
	}
	
	
	/**
	 * 设置员工信息service
	 */
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	/**
	 *设置快递价格方案Service
	 */
	public void setExpressPricePlanService(
			IExpressPricePlanService expressPricePlanService) {
		this.expressPricePlanService = expressPricePlanService;
	}
	
	
	/**
	 *设置客户合同Service
	 */
	public void setCusBargainService(ICusBargainService cusBargainService) {
		this.cusBargainService = cusBargainService;
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.insertSelective
	 * @Description:新增快递折扣方案   根据传入实体属性值是否为空进行选择性的插入
	 *
	 * @param record
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-8 下午4:29:00
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	@Transactional
	public void insertSelective(ExpressDiscountEntity discountPlanEntity) {
		/**
		 * 1.是否满足新增的条件
		 * 2.插入快递折扣方案主信息
		 * 3.插入方案适用渠道信息 一个方案可以同时对应多个渠道
		 */
		//验证是否满足条件
		addDiscountPlanValidation(discountPlanEntity);
		
		MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
		//封装方案主信息和渠道公用信息
		packagingExpressDiscountPlan(discountPlanEntity,channelEntity);
		//添加快递折扣主方案
		this.expressDiscountPlanDao.insertSelective(discountPlanEntity);
		//插入折扣方案对应的渠道信息
		if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes())){
			
			for(int i=0;i<discountPlanEntity.getChannelCodes().size();i++){
				String channelCode = discountPlanEntity.getChannelCodes().get(i);
				channelEntity.setSalesChannelCode(channelCode);
				channelEntity.setSalesChannelId(channelCode);
				channelEntity.setId(UUIDUtils.getUUID());
				//添加快递折扣方案适用渠道信息
				this.marketingEventChannelDao.insertSelective(channelEntity);
			}
			
		}
		
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.deleteByPrimaryKey
	 * @Description:根据快递折扣方案的ID删除方案信息 支持批量删除操作
	 *
	 * @param id
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-8 下午4:31:57
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public int deleteByIds(List<String> discountPlanIds) {
		if(CollectionUtils.isEmpty(discountPlanIds)){
			throw new ExpressDiscountPlanException("请确认是否已经选择需要删除的快递折扣方案！", null);
		}
		
		return this.expressDiscountPlanDao.deleteByIds(discountPlanIds);
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.updateByIdSelective
	 * @Description: 修改快递折扣方案信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-8 下午4:34:19
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	@Transactional
	public int updateByIdSelective(ExpressDiscountEntity entity,List<String> oldChannelCodes) {
		/**
		 * 1.是否满足修改的条件
		 * 2.修改快递折扣方案主信息
		 * 3.修改方案适用渠道信息
		 */
		//判断是否满足修改条件
		updateDiscountPlanValidation(entity);
		Date updateDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		//修改主方案信息
		entity.setModifyTime(updateDate);
		entity.setModifyUserCode(userCode);
		entity.setModifyOrgCode(deptCode);
		//更新快递折扣方案渠道信息
		updateExpresssDiscountChannel(entity,oldChannelCodes);
		
		return this.expressDiscountPlanDao.updateByIdSelective(entity);
	}
	/**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.selectById
     * @Description:分页查询快递折扣方案 根据传入的实体属性进行查询   其中方案名称进行模糊查询
     *
     * @param id
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-8 下午4:36:01
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@Override
	public List<ExpressDiscountEntity> queryExpressDiscountPlanList(
			ExpressDiscountEntity entity, int start, int limit) {
		//处理查询条件
		if(PricingConstants.ALL.equals(entity.getActive())){
			entity.setActive(null);
		}
		//方案状态
		if(PricingConstants.ALL.equals(entity.getPlanType())){
			entity.setPlanType(null);
		}
		List<ExpressDiscountEntity> discountEntityList = this.expressDiscountPlanDao.queryExpressDiscountPlanList(entity, start, limit);
		//处理封装查询出的数据
		if(CollectionUtils.isNotEmpty(discountEntityList)){
			for(ExpressDiscountEntity discountEntity:discountEntityList){
				
				EmployeeEntity employee = employeeService.queryEmployeeByEmpCode(discountEntity.getModifyUserCode());
				if(null!=employee){
					discountEntity.setModifyUserName(employee.getEmpName());
				}
			}
		}
		return discountEntityList;
	}
	  /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.queryExpressDiscountPlanListCount
     * @Description:查询满足当前查询条件的快递折扣方案的数量
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-8 下午4:53:37
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@Override
	public Long queryExpressDiscountPlanListCount(ExpressDiscountEntity entity) {
		return this.expressDiscountPlanDao.queryExpressDiscountPlanListCount(entity);
	}
	/**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.activeExpressDiscountPlan
     * @Description:根据方案ID激活方案 
     *
     * @param entity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-8 下午5:02:48
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-8    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@Override
	@Transactional
	public void activeExpressDiscountPlan(ExpressDiscountEntity discountEntity) {
		/**
		 * 1.判断当前折扣方案是否满足激活的条件
		 * 2.如果满足激活条件，激活折扣方案对应的渠道信息
		 * 3.如果满足激活条件，激活主方案信息
		 */
		if(null==discountEntity){
			throw new ExpressDiscountPlanException("请选择需要激活的方案！",null);
		}
		Date beginTime = discountEntity.getBeginTime();
		ExpressDiscountEntity queryEntity = new ExpressDiscountEntity();
		queryEntity.setId(discountEntity.getId());
		//查询当前方案信息
		discountEntity = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(queryEntity).get(0);
		//设置开始时间
		discountEntity.setBeginTime(beginTime);
		//验证是否满足激活的条件
		activeDiscountPlanValidation(discountEntity);
		Date nowDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		
		discountEntity.setActive(FossConstants.YES);
		discountEntity.setModifyTime(nowDate);
		discountEntity.setModifyOrgCode(deptCode);
		discountEntity.setModifyUserCode(userCode);
		discountEntity.setVersionNo(nowDate.getTime());
		
		MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
		channelEntity.setMarketingEventId(discountEntity.getId());
		channelEntity.setActive(FossConstants.YES);
		channelEntity.setModifyDate(nowDate);
		channelEntity.setModifyOrgCode(discountEntity.getModifyOrgCode());
		channelEntity.setModifyUser(discountEntity.getModifyUserCode());
		//更新渠道信息
		this.marketingEventChannelDao.updateByMarketEventIdSelective(channelEntity);
		//更新折扣方案主信息
		this.expressDiscountPlanDao.activeExpressDiscountPlan(discountEntity);
		
	}
  /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.stopExpressDiscountPlan
     * @Description:中止快递折扣方案
     *
     * @param entity
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-9 下午4:43:05
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-9    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@Override
	@Transactional
	public void stopExpressDiscountPlan(ExpressDiscountEntity entity) {
		Date nowDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		
		entity.setModifyTime(nowDate);
		entity.setModifyOrgCode(deptCode);
		entity.setModifyUserCode(userCode);
		entity.setVersionNo(nowDate.getTime());
		
		MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
		channelEntity.setMarketingEventId(entity.getId());
		channelEntity.setModifyDate(nowDate);
		channelEntity.setModifyOrgCode(entity.getModifyOrgCode());
		channelEntity.setModifyUser(entity.getModifyUserCode());
		channelEntity.setEndTime(entity.getEndTime());
		//更新渠道信息
		this.marketingEventChannelDao.updateByMarketEventIdSelective(channelEntity);
		this.expressDiscountPlanDao.stopExpressDiscountPlan(entity);
	}
	
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.insertDiscountDetailSelective
	 * @Description:新增快递折扣方案明细信息
	 *
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 下午2:16:12
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public void insertDiscountDetailSelective(ExpressDiscountDto detailEntity) {
		
		 if(null ==detailEntity){
			 throw new ExpressDiscountPlanException("新增的明细信息为空，请确认数据是否正确！",null);
		 }
		 /**
		  * 判断是否满足明细信息新增的条件
		  * 1·出发区域-到达区域，折扣规则、基础产品、货物类型、开始-结束范围,唯一确定一条明细数据
		  * 2.确认当前折扣方案中明细信息是否满足唯一条件
		  */
		 List<ExpressDiscountDto> resultDetailList = new ArrayList<ExpressDiscountDto>();
		 
		 resultDetailList = this.expressDiscountPlanDao.queryExpressDiscountDetailByCondition(detailEntity);
		 if(CollectionUtils.isNotEmpty(resultDetailList)){		 
			 throw new ExpressDiscountPlanException("新增的明细信息存在冲突，请确认数据是否正确！",null);
		 }
		 Date nowDate = new Date();
		 //获取当前登录人信息
		 UserEntity userEntity = (UserEntity) UserContext.getCurrentUser();
		 String userCode = userEntity.getEmployee().getEmpCode();
		 String deptCode = userEntity.getEmployee().getDepartment().getCode();
		 //封装数据
		 detailEntity.setId(UUIDUtils.getUUID());
		 detailEntity.setCreateTime(nowDate);
		 detailEntity.setCreateUserCode(userCode);
		 detailEntity.setCreateOrgCode(deptCode);
		 //货币类型
		 detailEntity.setCurrencyCode(PricingConstants.CURRENCY_CODE);
		 detailEntity.setVersionNo(nowDate.getTime());
		 //费用类型
		 detailEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
		 this.expressDiscountPlanDao.insertDiscountDetailSelective(detailEntity);
	}
	
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.queryExpressDiscountPlanDetailList
	 * @Description:查询快递折扣方案明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-9 下午6:39:27
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-9    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public List<ExpressDiscountDto> queryExpressDiscountPlanDetailList(
			ExpressDiscountDto entity, int start, int limit) {
		//处理查询条件
		if(PricingConstants.ALL.equals(entity.getGoodsTypeCode())){
			entity.setGoodsTypeCode(null);
		}
		if(PricingConstants.ALL.equals(entity.getProductCode())){
			entity.setProductCode(null);
		}
		List<ExpressDiscountDto>  detailList = this.expressDiscountPlanDao.queryExpressDiscountPlanDetailList(entity, start, limit);
		//封装产品名，出发区域，到达区域等属性
		packingDiscountDetailList(detailList);
		
		return detailList;
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.updateDiscountDetailSelective
	 * @Description:修改快递折扣明细信息
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:58:33
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	@Transactional
	public void updateDiscountDetailSelective(ExpressDiscountDto detailEntity) {
		
		 /**
		  * 判断是否满足明细信息新增的条件
		  * 1·出发区域-到达区域，折扣规则、基础产品、货物类型、开始-结束范围,唯一确定一条明细数据
		  * 2.确认当前折扣方案中明细信息是否满足唯一条件
		  */
		 List<ExpressDiscountDto> resultDetailList = new ArrayList<ExpressDiscountDto>();
		 ExpressDiscountDto queryEntity  = new ExpressDiscountDto();
		 //复制属性到查询实体中
		 BeanUtils.copyProperties(detailEntity, queryEntity);
		 queryEntity.setId(null);
		 resultDetailList = this.expressDiscountPlanDao.queryExpressDiscountDetailByCondition(queryEntity);
		 if(CollectionUtils.isNotEmpty(resultDetailList)){
			 for(ExpressDiscountDto entity:resultDetailList){
				 if(!StringUtils.equals(detailEntity.getId(), entity.getId())){
					 throw new ExpressDiscountPlanException("当前修改折扣明细在方案中存在冲突的明细，请确认数据是否正确！",null);
				 }
			 }
		 }
		Date nowDate = new Date();
		UserEntity userEntity = (UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		detailEntity.setModifyTime(nowDate);
		detailEntity.setModifyUserCode(userCode);
		detailEntity.setModifyOrgCode(deptCode);
		detailEntity.setVersionNo(nowDate.getTime());
		
		this.expressDiscountPlanDao.updateDiscountDetailSelective(detailEntity);
		//更新主方案信息
		ExpressDiscountEntity planEntity = new ExpressDiscountEntity();
		planEntity.setModifyTime(nowDate);
		planEntity.setModifyUserCode(userCode);
		planEntity.setModifyOrgCode(deptCode);
		planEntity.setVersionNo(nowDate.getTime());
		planEntity.setId(detailEntity.getExpressDiscountPlanId());
		this.expressDiscountPlanDao.updateByIdSelective(planEntity);
		
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.deleteDiscountDetailById
	 * @Description:根据明细ID删除折扣明细信息
	 *
	 * @param detailEntity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午11:00:26
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	@Transactional
	public void deleteDiscountDetailById(ExpressDiscountEntity discountEntity,List<String> discountDetailIds) {
		
		Date nowDate = new Date();
		UserEntity userEntity = (UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		this.expressDiscountPlanDao.deleteDiscountDetailByIds(discountDetailIds);
		
		//更新主方案信息
		discountEntity.setModifyTime(nowDate);
		discountEntity.setModifyUserCode(userCode);
		discountEntity.setModifyOrgCode(deptCode);
		discountEntity.setVersionNo(nowDate.getTime());
		this.expressDiscountPlanDao.updateByIdSelective(discountEntity);
		
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.queryExpressDiscountPlanDetailListCount
	 * @Description:查询快递折扣方案明细信息记录总数
	 *
	 * @param entity
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-10 上午10:42:15
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-10    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public Long queryExpressDiscountPlanDetailListCount(
			ExpressDiscountDto detailEntity) {
		//处理查询条件
		if(PricingConstants.ALL.equals(detailEntity.getGoodsTypeCode())){
			detailEntity.setGoodsTypeCode(null);
		}
		if(PricingConstants.ALL.equals(detailEntity.getProductCode())){
			detailEntity.setProductCode(null);
		}
		return this.expressDiscountPlanDao.queryExpressDiscountPlanDetailListCount(detailEntity);
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.queryExpressDiscountPlanById
	 * @Description:根据折扣方案ID查询折扣方案
	 *
	 * @return
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午4:54:46
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	@Override
	public ExpressDiscountEntity queryExpressDiscountPlanById(ExpressDiscountEntity discountEntity){
		
		ExpressDiscountEntity discountPlanEntity = new ExpressDiscountEntity();
		//查询方案主信息
		discountPlanEntity = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(discountEntity).get(0);
		//查询方案适用的渠道信息
		List<String> channelCodes = new ArrayList<String>();

		List<MarketingEventChannelEntity> channelList=this.marketingEventChannelDao.queryMarketingEventChannelListByEventId(discountEntity.getId());
		//封装渠道code到集合
		if(CollectionUtils.isNotEmpty(channelList)){
			for(MarketingEventChannelEntity entity:channelList){
				channelCodes.add(entity.getSalesChannelCode());
			}
		}
		
		discountPlanEntity.setChannelCodes(channelCodes);
		return discountPlanEntity;
	}
	
    /**
     * 
     *
     * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.queryExpressDiscountDetailByCondition
     * @Description:根据查询条件查询快递折扣方案明细信息（不分页）
     *
     * @param detailEntity
     * @return
     *
     * @version:v1.0
     * @author:DP-FOSS-YANGKANG
     * @date:2015-1-14 上午9:50:10
     *
     * Modification History:
     * Date         Author      Version     Description
     * -----------------------------------------------------------------
     * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
     */
	@Override
	public List<ExpressDiscountDto> queryExpressDiscountPlanDetailByCondition(
			ExpressDiscountDto detailEntity) {
		List<ExpressDiscountDto>  detailList = this.expressDiscountPlanDao.queryExpressDiscountDetailByCondition(detailEntity);
		//封装产品名，出发区域，到达区域等属性
		packingDiscountDetailList(detailList);
		
		return detailList;
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.getExpressDiscountPlan
	 * @Description:封装快递折扣主方案信息和渠道信息
	 *
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 上午9:05:08
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void packagingExpressDiscountPlan(ExpressDiscountEntity discountPlanEntity,MarketingEventChannelEntity channelEntity){
		
		
		Date nowDate = new Date();
		//获取当前登录人信息
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		//封装折扣方案主信息
		discountPlanEntity.setId(UUIDUtils.getUUID());
		discountPlanEntity.setCreateUserCode(userCode);
		discountPlanEntity.setCreateOrgCode(deptCode);
		discountPlanEntity.setCreateTime(nowDate);
		discountPlanEntity.setModifyUserCode(userCode);
		discountPlanEntity.setModifyOrgCode(deptCode);
		discountPlanEntity.setModifyTime(nowDate);
		discountPlanEntity.setVersionNo(nowDate.getTime());
		discountPlanEntity.setActive(FossConstants.NO);
		
		//如果方案对应的渠道不为空，则封装渠道信息，否则不进行封装
		if(CollectionUtils.isNotEmpty(discountPlanEntity.getChannelCodes())){
			//封装渠道公用信息  不包括渠道code
			channelEntity.setMarketingEventId(discountPlanEntity.getId());
			channelEntity.setBeginTime(discountPlanEntity.getBeginTime());
			channelEntity.setEndTime(discountPlanEntity.getEndTime());
			channelEntity.setActive(FossConstants.NO);
			channelEntity.setCreateDate(nowDate);
			channelEntity.setCreateUser(discountPlanEntity.getCreateUserCode());
			channelEntity.setCreateOrgCode(discountPlanEntity.getCreateOrgCode());
			channelEntity.setVersionNo(nowDate.getTime());
		}
		
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.updateExpresssDiscountChannel
	 * @Description:更新折扣方案渠道信息  用于更新方案的方法中
	 *
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-12 下午1:53:36
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-12    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void  updateExpresssDiscountChannel(ExpressDiscountEntity entity,List<String> oldChannelCodes){
		//获取方案修改后最新的渠道信息
		List<String> newChannelCodes = entity.getChannelCodes();
		MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
		
		/**
		 *情况一：当方案原折扣渠道为空
		 */
		if(CollectionUtils.isEmpty(oldChannelCodes)){
			
			if(CollectionUtils.isNotEmpty(newChannelCodes)){
				//封装渠道公用信息  不包括渠道code
				channelEntity.setMarketingEventId(entity.getId());
				channelEntity.setBeginTime(entity.getBeginTime());
				channelEntity.setEndTime(entity.getEndTime());
				channelEntity.setCreateDate(entity.getModifyTime());
				channelEntity.setCreateUser(entity.getModifyUserCode());
				channelEntity.setCreateOrgCode(entity.getModifyOrgCode());
				channelEntity.setVersionNo(entity.getModifyTime().getTime());
				channelEntity.setActive(FossConstants.NO);
				
				for(int i=0;i<newChannelCodes.size();i++){
					channelEntity.setId(UUIDUtils.getUUID());
					channelEntity.setSalesChannelCode(newChannelCodes.get(i));
					channelEntity.setSalesChannelId(newChannelCodes.get(i));
					this.marketingEventChannelDao.insertSelective(channelEntity);
				}
			}
		}
		/**
		 *情况二：方案原折扣渠道不为空，方案新折扣渠道为空，则需要删除掉原方案所有的折扣渠道信息
		 */
		if(CollectionUtils.isNotEmpty(oldChannelCodes)&&CollectionUtils.isEmpty(newChannelCodes)){
			this.marketingEventChannelDao.deleteByMarketEventId(entity.getId());
		}
		/**
		 * 情况三：方案原折扣渠道不为空，且方案新折扣渠道不为空
		 * 1.方案原折扣渠道中存在，且方案新折扣渠道中不存在的渠道，则需要删除
		 * 2.方案新折扣渠道中存在，且方案原折扣渠道中不存在的渠道，则需要新增
		 * 3.方案原折扣渠道中存在，且方案新折扣渠道中同时存在的渠道，则需要更新
		 */
		if(CollectionUtils.isNotEmpty(oldChannelCodes)&&CollectionUtils.isNotEmpty(newChannelCodes)){
			//需要删除的渠道
			List<String> deleteChannelCodes = new ArrayList<String>();
			//需要新增的渠道
			List<String> addChannelCodes = new ArrayList<String>();
			//需要更新的渠道
			List<String> updateChannelCodes = new ArrayList<String>();
			
			for(String channelCode:oldChannelCodes){
				if(newChannelCodes.contains(channelCode)){
					updateChannelCodes.add(channelCode);
				}else{
					deleteChannelCodes.add(channelCode);
				}
			}
			
			for(String channelCode:newChannelCodes){
				if(!oldChannelCodes.contains(channelCode)){
					addChannelCodes.add(channelCode);
				}
			}
			//根据渠道code和折扣方案ID删除相应的渠道信息
			if(CollectionUtils.isNotEmpty(deleteChannelCodes)){
				channelEntity.setMarketingEventId(entity.getId());
				this.marketingEventChannelDao.deleteByMarketEventIdAndChannelCode(channelEntity, deleteChannelCodes);
			}
			//根据折扣方案ID更新渠道信息
			if(CollectionUtils.isNotEmpty(updateChannelCodes)){
				channelEntity.setMarketingEventId(entity.getId());
				channelEntity.setBeginTime(entity.getBeginTime());
				channelEntity.setEndTime(entity.getEndTime());
				channelEntity.setModifyDate(entity.getModifyTime());
				channelEntity.setModifyUser(entity.getModifyUserCode());
				channelEntity.setModifyOrgCode(entity.getModifyOrgCode());
				channelEntity.setVersionNo(entity.getModifyTime().getTime());
				this.marketingEventChannelDao.updateByMarketEventIdSelective(channelEntity);
			}
			
			//新增折扣方案的渠道信息
			if(CollectionUtils.isNotEmpty(addChannelCodes)){
				
				MarketingEventChannelEntity addChannelEntity = new MarketingEventChannelEntity();
				
				addChannelEntity.setMarketingEventId(entity.getId());
				addChannelEntity.setBeginTime(entity.getBeginTime());
				addChannelEntity.setEndTime(entity.getEndTime());
				addChannelEntity.setCreateDate(entity.getModifyTime());
				addChannelEntity.setCreateUser(entity.getModifyUserCode());
				addChannelEntity.setCreateOrgCode(entity.getModifyOrgCode());
				addChannelEntity.setVersionNo(entity.getModifyTime().getTime());
				addChannelEntity.setActive(FossConstants.NO);
				
				for(int i=0;i<addChannelCodes.size();i++){
					addChannelEntity.setId(UUIDUtils.getUUID());
					addChannelEntity.setSalesChannelCode(addChannelCodes.get(i));
					addChannelEntity.setSalesChannelId(addChannelCodes.get(i));
					this.marketingEventChannelDao.insertSelective(addChannelEntity);
				}
			}
		}
		
	}
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.packingDiscountDetailList
	 * @Description:封装查询出的快递折扣明细数据  
	 *
	 * @param detailList
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-13 上午11:15:14
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-13    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void packingDiscountDetailList(List<ExpressDiscountDto>  detailList){
		
		//封装产品名，出发区域，到达区域等属性
		if(CollectionUtils.isNotEmpty(detailList)){
			
			for(ExpressDiscountDto detailEntity:detailList){
				PriceRegionExpressEntity regionEntity = new PriceRegionExpressEntity();
				regionEntity.setRegionNature(PricingConstants.PRICING_REGION);
				regionEntity.setId(detailEntity.getStartRegionCode());
				regionEntity.setActive(FossConstants.YES);
				//出发区域名称
				List<PriceRegionExpressEntity> startReginList = regionExpressService.findRegionByRegion(regionEntity);
				if(CollectionUtils.isNotEmpty(startReginList)){
					detailEntity.setStartRegionName(startReginList.get(0).getRegionName());
				}
				//到达区域名称
				regionEntity.setId(detailEntity.getArriveRegionCode());
				List<PriceRegionExpressEntity> arriveReginList = regionExpressService.findRegionByRegion(regionEntity);
				if(CollectionUtils.isNotEmpty(arriveReginList)){
					detailEntity.setArriveRegionName(arriveReginList.get(0).getRegionName());
				}
				//产品名称
				ProductEntity productEntity = new ProductEntity();
				productEntity.setActive(FossConstants.YES);
				productEntity.setCode(detailEntity.getProductCode());
				List<ProductEntity> productEntityList =	productService.findProductByCondition(productEntity);
				if(CollectionUtils.isNotEmpty(productEntityList)){
					detailEntity.setProductName(productEntityList.get(0).getName());
				}
				GoodsTypeEntity goodsTypeEntity =goodsTypeService.queryGoodsTypeByGoodTypeCode(detailEntity.getGoodsTypeCode(), new Date());
				if(null!=goodsTypeEntity){
					detailEntity.setGoodsTypeId(goodsTypeEntity.getId());
					detailEntity.setGoodsTypeName(goodsTypeEntity.getName());
				}
			}
		}
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.addDiscountPlanValidation
	 * @Description:验证方案是否满足新增或者修改的条件
	 *
	 * @param discountEntity
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 上午8:20:54
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void addDiscountPlanValidation(ExpressDiscountEntity discountEntity){
		/**
		 * 1.验证方案名称是否重复
		 * 2.当所选客户CRM合同属性是否单独定价为“否”时，不允许提交
		 * 3.当所选折扣客户在所选方案期限内已存在折扣方案时，不允许提交，即一个客户编码只允许存在一个有效的出发折扣方案
		 */
		List<ExpressDiscountEntity> resultList = new ArrayList<ExpressDiscountEntity>();
		//查询是否存在同名的方案
		ExpressDiscountEntity queryEntity = new ExpressDiscountEntity();
		queryEntity.setName(discountEntity.getName());
		
		resultList = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(queryEntity);
		if(CollectionUtils.isNotEmpty(resultList)){
			throw new ExpressDiscountPlanException("该方案名称已存在，不允许提交!",null);
		}
		//查询客户在所选方案期限内是否已存在折扣方案,不管该方案是否激活 
		queryEntity.setName(null);
		queryEntity.setCustomerCode(discountEntity.getCustomerCode());
		queryEntity.setPlanType(discountEntity.getPlanType());
		queryEntity.setBeginTime(discountEntity.getBeginTime());
		queryEntity.setEndTime(discountEntity.getEndTime());
		boolean flag = false;
		String weekCodes = discountEntity.getWeekCodes();
		flag = validateWeeks(flag,weekCodes, queryEntity);
		if(flag) {
			throw new ExpressDiscountPlanException("该客户所选方案期限内已经存在方案类型相同的快递折扣方案，不允许新增方案!",null);
		}
		/*//查询当前客户合同属性是否满足条件
		CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainInfos(discountEntity.getCustomerCode(), null);
		//当客户合同中是否单独定价为否时，不允许新增方案
		if(cusBargainEntity != null){
			//由于没有初始化客户合同中单独定价的字段  如果该字段为空，则当做单独定价为“否”处理
			if(StringUtil.isNotBlank(cusBargainEntity.getIsAloneQuotation())){
				if(StringUtils.equals(FossConstants.NO,cusBargainEntity.getIsAloneQuotation())){
					throw new ExpressDiscountPlanException("当前方案所选客户合同是否单独定价不满足条件，请确认！",null);
				}
			}else{
				throw new ExpressDiscountPlanException("当前方案所选客户合同是否单独定价不满足条件，请确认！",null);
			}
		}else{
			throw new ExpressDiscountPlanException("当前方案所选客户不是合同客户，请确认！",null);
		}*/
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.updateDiscountPlanValidation
	 * @Description:验证修改时方案是否满足条件
	 *
	 * @param discountEntity
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-14 下午4:00:53
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void updateDiscountPlanValidation(ExpressDiscountEntity discountEntity){
		/**
		 * 1.验证方案名称是否重复
		 * 2.当所选客户CRM合同属性是否单独定价为“否”时，不允许提交
		 * 3.当所选折扣客户在所选方案期限内已存在折扣方案时，不允许提交，即一个客户编码只允许存在一个有效的出发折扣方案
		 */
		List<ExpressDiscountEntity> resultList = new ArrayList<ExpressDiscountEntity>();
		//查询是否存在同名的方案
		ExpressDiscountEntity queryEntity = new ExpressDiscountEntity();
		queryEntity.setName(discountEntity.getName());
		
		resultList = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(queryEntity);
		
		if(CollectionUtils.isNotEmpty(resultList)){
			for(ExpressDiscountEntity entity:resultList){
				if(!StringUtils.equals(discountEntity.getId(), entity.getId())){
					throw new ExpressDiscountPlanException("该方案名称已存在，不允许提交!",null);
				}
			}
		}
		//查询客户在所选方案期限内是否已存在折扣方案,不管该方案是否激活 
		queryEntity.setName(null);
		queryEntity.setCustomerCode(discountEntity.getCustomerCode());
		queryEntity.setBeginTime(discountEntity.getBeginTime());
		queryEntity.setEndTime(discountEntity.getEndTime());
		queryEntity.setPlanType(discountEntity.getPlanType());
		boolean flag = false;
		String weekCodes = discountEntity.getWeekCodes();
		resultList = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(queryEntity);
		if(CollectionUtils.isNotEmpty(resultList)){
			for(ExpressDiscountEntity entity:resultList){
				if(!StringUtils.equals(discountEntity.getId(), entity.getId())){
					flag=validateWeeks(flag,weekCodes, queryEntity);
				}
			}
		}
		if(flag) {
			throw new ExpressDiscountPlanException("该客户所选方案期限内已经存在方案类型相同的快递折扣方案，修改失败!",null);
		}		
		/*//查询当前客户合同属性是否满足条件
				CusBargainEntity cusBargainEntity = cusBargainService.queryCusBargainInfos(discountEntity.getCustomerCode(), null);
				//当客户合同中是否单独定价为否时，不允许新增方案
				if(cusBargainEntity != null){
					//由于没有初始化客户合同中单独定价的字段  如果该字段为空，则当做单独定价为“否”处理
					if(StringUtil.isNotBlank(cusBargainEntity.getIsAloneQuotation())){
						if(StringUtils.equals(FossConstants.NO,cusBargainEntity.getIsAloneQuotation())){
							throw new ExpressDiscountPlanException("当前方案所选客户合同是否单独定价不满足条件，请确认！",null);
						}
					}else{
						throw new ExpressDiscountPlanException("当前方案所选客户合同是否单独定价不满足条件，请确认！",null);
					}
				}else{
					throw new ExpressDiscountPlanException("当前方案所选客户不是合同客户，请确认！",null);
				}*/
	}
	
	/**
	 * 
	 *
	 * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.activeDiscountPlanDetailValidation
	 * @Description:查询是否存在冲突的客户快递价格方案
	 *
	 * @param detailEntity
	 *
	 * @version:v1.0
	 * @author:DP-FOSS-YANGKANG
	 * @date:2015-1-19 上午9:46:36
	 *
	 * Modification History:
	 * Date         Author      Version     Description
	 * -----------------------------------------------------------------
	 * 2015-1-19    DP-FOSS-YANGKANG      v1.0.0         create
	 */
	public void activeDiscountPlanDetailValidation(ExpressDiscountDto detailEntity){
		/**
		 * 快递价格方案中存储的是二级产品的产品编码
		 * 快递折扣方案中存储的是三级产品的编码
		 * 需要通过三级产品获取其父类产品，然后进行查询判断
		 */
		//获取产品信息
		ProductEntity productEntity =productService.getProductByCache(detailEntity.getProductCode(), new Date());
		List<PriceValuationEntity>  resultList = new ArrayList<PriceValuationEntity>();
		PriceValuationEntity queryEntity = new PriceValuationEntity();
		queryEntity.setDeptRegionId(detailEntity.getStartRegionCode());
		queryEntity.setArrvRegionId(detailEntity.getArriveRegionCode());
		//传递二级产品的code
		queryEntity.setProductCode(productEntity.getParentCode());
	    queryEntity.setBeginTime(detailEntity.getBeginTime());
	    queryEntity.setEndTime(detailEntity.getEndTime());
	    queryEntity.setCustomerCode(detailEntity.getCustomerCode());
	    resultList = expressPricePlanService.queryExpressPricePlanByCondition(queryEntity);
    
	    if(CollectionUtils.isNotEmpty(resultList)){
	    	throw new ExpressDiscountPlanException("激活失败，与客户快递价格方案["+resultList.get(0).getPricePlanName()+"]存在冲突，请确认！",null);
	    }
  }
  
  
  /**
   * 
   *
   * @Function: com.deppon.foss.module.pickup.pricing.server.service.impl.ExpressDiscountPlanService.activeDiscountPlanValidation
   * @Description:判断当前方案是否满足激活的条件
   *
   * @param discountEntity
   *
   * @version:v1.0
   * @author:DP-FOSS-YANGKANG
   * @date:2015-1-14 下午4:08:35
   *
   * Modification History:
   * Date         Author      Version     Description
   * -----------------------------------------------------------------
   * 2015-1-14    DP-FOSS-YANGKANG      v1.0.0         create
   */
  public void activeDiscountPlanValidation(ExpressDiscountEntity discountEntity){
    /**
     * 1.如果当前客户在选择的时间期间内已经存在激活的有效的折扣方案，则需要将原方案中止，然后激活方案
     * 2.判断折扣方案明细是否与客户快递价格方案存在冲突
     */
    //查询是否存在冲突的方案
    ExpressDiscountEntity queryEntity = new ExpressDiscountEntity();
    queryEntity.setCustomerCode(discountEntity.getCustomerCode());
    queryEntity.setBeginTime(discountEntity.getBeginTime());
	queryEntity.setEndTime(discountEntity.getEndTime());
	queryEntity.setPlanType(discountEntity.getPlanType());
	queryEntity.setActive(FossConstants.ACTIVE);
	boolean flag = false;
	String weekCodes = discountEntity.getWeekCodes();
	flag = validateWeeks(flag, weekCodes, queryEntity);
	if(flag) {
		throw new ExpressDiscountPlanException("存在与当前激活方案期间冲突的有效折扣方案,请中止后进行激活操作！",null);
	}
	//判断当前折扣方案明细是否冲突
	List<ExpressDiscountDto>  detailList = new ArrayList<ExpressDiscountDto>();
	ExpressDiscountDto queryDetailEntity = new ExpressDiscountDto();
	queryDetailEntity.setExpressDiscountPlanId(discountEntity.getId());
	queryDetailEntity.setCustomerCode(discountEntity.getCustomerCode());
	
	detailList = this.expressDiscountPlanDao.queryExpressDiscountDetailByCondition(queryDetailEntity);
	
	if(CollectionUtils.isEmpty(detailList)){
		throw new ExpressDiscountPlanException("当前方案不存在折扣明细，不允许激活方案！",null);
	}
	//只要快递折扣方案中存在一条明细与客户快递价格方案冲突，则不允许激活方案
	for(ExpressDiscountDto detailEntity:detailList){
		detailEntity.setCustomerCode(discountEntity.getCustomerCode());
		detailEntity.setEndTime(discountEntity.getEndTime());
		detailEntity.setBeginTime(discountEntity.getBeginTime());
		activeDiscountPlanDetailValidation(detailEntity);
	}
		
	}
  
	/***
	 * 
	* @author: 200945 吴涛
	* @Title: upgradeExpressDiscountPlan 
	* @Description: (
	*    #用于升级主方案:业务逻辑
	*    1.复制主方案信息到对应的数据库中
	*    2.复制主方案折扣信息的价格明细到数据库中
	*    3.复制渠道信息
	*    
	* ) 
	* @param @param discountEntity
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public void upgradeExpressDiscountPlan(ExpressDiscountEntity discountEntity){
		String newExpressDiscountPlanId = UUIDUtils.getUUID();
		ExpressDiscountEntity queryEntity = new ExpressDiscountEntity();
		queryEntity.setId(discountEntity.getId());
		Date nowDate = new Date();
		//1.主方案
		ExpressDiscountEntity copyExpressDiscountEntity = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(queryEntity).get(0);
		copyExpressDiscountEntity.setId(newExpressDiscountPlanId);
		copyExpressDiscountEntity.setActive(FossConstants.NO);
		UserEntity userEntity=(UserEntity) UserContext.getCurrentUser();
		String userCode = userEntity.getEmployee().getEmpCode();
		String deptCode = userEntity.getEmployee().getDepartment().getCode();
		copyExpressDiscountEntity.setModifyUserCode(userCode); //修改员工编码
		copyExpressDiscountEntity.setModifyOrgCode(deptCode); //修改部门编码
		copyExpressDiscountEntity.setModifyDate(nowDate);
		copyExpressDiscountEntity.setVersionNo(nowDate.getTime());
		copyExpressDiscountEntity.setEndTime(new Date(NumberConstants.ENDTIME));//将结束时间设置为最大的时间
		this.expressDiscountPlanDao.insertSelective(copyExpressDiscountEntity);
		//2.明细
		ExpressDiscountDto queryDiscountDto = new ExpressDiscountDto();
		queryDiscountDto.setExpressDiscountPlanId(discountEntity.getId());
		queryDiscountDto.setCustomerCode(discountEntity.getCustomerCode());
		List<ExpressDiscountDto> copyExpressDiscountDetailEntityList = this.expressDiscountPlanDao.queryExpressDiscountDetailByCondition(queryDiscountDto);
		for(ExpressDiscountDto copyDetailEntity:copyExpressDiscountDetailEntityList){
			copyDetailEntity.setId(UUIDUtils.getUUID());
			copyDetailEntity.setExpressDiscountPlanId(newExpressDiscountPlanId);
			copyDetailEntity.setModifyOrgCode(deptCode);
			copyDetailEntity.setModifyUserCode(userCode);
			copyDetailEntity.setVersionNo(nowDate.getTime());
			copyDetailEntity.setModifyTime(nowDate);
			this.expressDiscountPlanDao.insertDiscountDetailSelective(copyDetailEntity);
		}
		//3.渠道
		List<MarketingEventChannelEntity> channelList = this.marketingEventChannelDao.queryMarketingEventChannelListByEventId(discountEntity.getId());
		for(MarketingEventChannelEntity channelEntity : channelList){
			channelEntity.setMarketingEventId(newExpressDiscountPlanId);
			channelEntity.setId(UUIDUtils.getUUID());
			channelEntity.setModifyOrgCode(deptCode);
			channelEntity.setModifyUser(userCode);
			channelEntity.setModifyDate(nowDate);
			channelEntity.setVersionNo(nowDate.getTime());
			this.marketingEventChannelDao.insertSelective(channelEntity);
		}		
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 校验周特惠方案是否重复
	 */
	public boolean validateWeeks(boolean flag,String weekCodes,ExpressDiscountEntity queryEntity) {		
		String[] codes =weekCodes.split(",");
		List<ExpressDiscountEntity> resultList = this.expressDiscountPlanDao.queryExpressDiscountPlanByCondition(queryEntity);		
		if(CollectionUtils.isNotEmpty(resultList)){
			if("ALL".equals(weekCodes)) {
				return true;
			}
			String[] searchCodes = null;
			
			for (ExpressDiscountEntity expressDiscountEntity : resultList) {
				if(StringUtil.isNotBlank(expressDiscountEntity.getWeekCodes())) {
					if("ALL".equals(expressDiscountEntity.getWeekCodes())) {
						flag = true;
						break;
					} else {
						searchCodes = expressDiscountEntity.getWeekCodes().split(",");
						String[] unition = intersect(codes,searchCodes);
						if(unition.length>0) {
							flag = true;
							break;
						}
					}
				}
			}
			
			
		}
		return flag;
	}
	/**
	 * dp-foss-dongjialing
	 * 225131
	 * 求两个数组集合的交集
	 */
	public  String[] intersect(String[] arr1, String[] arr2) {   
        Map<String, Boolean> map = new HashMap<String, Boolean>();   
        LinkedList<String> list = new LinkedList<String>();   
        for (String str : arr1) {   
            if (!map.containsKey(str)) {   
                map.put(str, Boolean.FALSE);   
            }   
        }   
        for (String str : arr2) {   
            if (map.containsKey(str)) {   
                map.put(str, Boolean.TRUE);   
            }   
        }   
  
        for (Entry<String, Boolean> e : map.entrySet()) {   
            if (e.getValue().equals(Boolean.TRUE)) {   
                list.add(e.getKey());   
            }   
        }   
  
        String[] result = {};   
        return list.toArray(result);   
    }   
	/***
	 * 
	* @author: 王增明
	* @Title: addDiscountBatch 
	* @Description:
	* #用于导入折扣方案:业务逻辑
	*    1.批量添加折扣主方案
	*    2.验证明细方案是否属于主方案
	*    3.批量添加明细方案
	* @param @param discountMap，detailMap，
	* 					priceRegionEntityMap，productEntityMap，customerEntityMap
	* @param @return    设定文件 
	* @return 返回类型 
	* @throws
	 */
	@Override
	public void addDiscountBatch(Map<String, List<String>> discountMap,
			Map<String, List<String>> detailMap,
			Map<String, GoodsTypeEntity> googsTypeEntityMap,
			Map<String, PriceRegionExpressEntity> priceRegionEntityMap,
			Map<String, ProductEntity> productEntityMap,
			Map<String, CustomerEntity> customerEntityMap,Map<String,String> weeksEntityMap,Map<String,List<DataDictionaryValueEntity>> channelEntityMap) {
		// 检查数据
		if (discountMap == null || discountMap.size() < 1 || detailMap == null || detailMap.size() < 1
			|| priceRegionEntityMap == null
			|| priceRegionEntityMap.size() < 1 || productEntityMap == null
			|| productEntityMap.size() < 1 || weeksEntityMap == null || weeksEntityMap.size() < 1) {
		    return;
		}
		//在插入数据之前验证日期，避免出现错误的日期格式
		validateDate(discountMap);
		//获取当前用户资料
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		Set<Entry<String, List<String>>> discountSet = discountMap.entrySet();
		//验证明细方案是否属于主方案的map
		Map<String,String> isMainPlan = new HashMap<String,String>();
		for (Entry<String, List<String>> entry : discountSet) {
		    String key = entry.getKey();
		  
		    String[] splitRegionName = key.split("#");
			//获取主方案信息
		    List<String> discountList = entry.getValue();
		    //获取方案详细信息
		    List<String> detailList = detailMap.get(key);
		    //获取区域信息
		    
		    PriceRegionExpressEntity startingRegion = priceRegionEntityMap
			    .get(splitRegionName[PricingConstants.TWO]);
		    PriceRegionExpressEntity arriveRegion = priceRegionEntityMap
				    .get(splitRegionName[PricingConstants.THREE]);
		    //获取周特惠名称
		    String weeksName = splitRegionName[PricingConstants.SIX];
		    //获取客户信息
		    CustomerEntity customerEntity = customerEntityMap.get(splitRegionName[PricingConstants.ONE]);
		    
		    //获取产品信息
		    ProductEntity productEntity = productEntityMap.get(splitRegionName[PricingConstants.FOUR]);
		    
		    //获取货物类型信息
		    GoodsTypeEntity goodsTypeEntity = googsTypeEntityMap.get(splitRegionName[PricingConstants.FIVE]);
		    
		    //获取渠道信息
		    List<DataDictionaryValueEntity> channelList = null;
		    if(channelEntityMap != null && channelEntityMap.size() > 0 && splitRegionName.length > PricingConstants.SEVEN){
		    	channelList = channelEntityMap.get(splitRegionName[PricingConstants.SEVEN]);
		    }
		    // 添加价格主方案信息
		    if(discountList != null && discountList.size() > 1){
		    	//封装周特惠编码
		    	StringBuilder isMainPlanKeyTemp = new StringBuilder();
		    	for(int i = PricingConstants.ZERO ; i < discountList.size()- PricingConstants.ONE; i ++){
		    		isMainPlanKeyTemp.append(discountList.get(i));
		    		isMainPlanKeyTemp.append("#");
				}
		    	String isMainPlanKey = isMainPlanKeyTemp.substring(PricingConstants.ZERO, isMainPlanKeyTemp.length()-1);
		    	//明细方案不属于主方案
		    	if(isMainPlan.get(isMainPlanKey) == null){
		    		ExpressDiscountEntity discountEntity = packingDiscountEntity(discountList,customerEntity,currentUser,currentDept,weeksEntityMap,weeksName,channelList);
				    isMainPlan.put(isMainPlanKey, discountEntity.getId());
				    ExpressDiscountDto expressDiscountDetail = new ExpressDiscountDto();
				    expressDiscountDetail.setExpressDiscountPlanId(discountEntity.getId());
				    packingDiscountDetail(expressDiscountDetail,detailList,startingRegion,arriveRegion,customerEntity,productEntity,currentUser,currentDept,goodsTypeEntity);
				    expressDiscountPlanDao.insertSelective(discountEntity);
				    expressDiscountPlanDao.insertDiscountDetailSelective(expressDiscountDetail);
				//明细方案属于主方案
		    	}else{
		    		ExpressDiscountDto expressDiscountDetail = new ExpressDiscountDto();
		    		String expressDiscountPlanId = isMainPlan.get(isMainPlanKey);
		    		expressDiscountDetail.setExpressDiscountPlanId(expressDiscountPlanId);
		    		packingDiscountDetail(expressDiscountDetail,detailList,startingRegion,arriveRegion,customerEntity,productEntity,currentUser,currentDept,goodsTypeEntity);
		    		expressDiscountPlanDao.insertDiscountDetailSelective(expressDiscountDetail);
		    	}
		    }
		}
	}
	
	private void validateDate(Map<String, List<String>> discountMap) {
		Set<Entry<String, List<String>>> discountSet = discountMap.entrySet();
		for (Entry<String, List<String>> entry : discountSet) {
			//获取主方案信息
		    List<String> discountList = entry.getValue();
		    String row = discountList.get(PricingConstants.EIGHT);
		    try {
				DateUtil.stringToDate(discountList.get(PricingConstants.THREE),PricingConstants.PATTEN);
				DateUtil.stringToDate(discountList.get(PricingConstants.FOUR),PricingConstants.PATTEN);
			} catch (ParseException e) {
				e.printStackTrace();
				throw new FileException("折扣方案导入时发生错误 ：第" + row + "行开始时间或截止时间格式错误", "折扣方案导入时发生错误 ：第" + row + "行开始时间或截止时间格式错误");
			}
		    
		}
	}
	/***
	 * 
	* @author: 王增明
	* @Title: packingDiscountDetail 
	* @Description: 
	*    #:封装折扣方案明细
	* @param expressDiscountDetail，detailList，
	* 					startingRegion，arriveRegion
	* @param @return    设定文件 
	* @return 返回类型 
	* @throws
	 */
	private void packingDiscountDetail(
			ExpressDiscountDto expressDiscountDetail, List<String> detailList,
			PriceRegionExpressEntity startingRegion,
			PriceRegionExpressEntity arriveRegion,
			CustomerEntity customerEntity, ProductEntity productEntity, UserEntity currentUser,
			OrgAdministrativeInfoEntity currentDept,GoodsTypeEntity goodsTypeEntity) {
		Date nowDate = new Date();
		expressDiscountDetail.setId(UUIDUtils.getUUID());
		//区域添加
		expressDiscountDetail.setArriveRegionCode(arriveRegion.getId());
		expressDiscountDetail.setArriveRegionName(arriveRegion.getRegionName());
		expressDiscountDetail.setStartRegionCode(startingRegion.getId());
		expressDiscountDetail.setStartRegionName(startingRegion.getRegionName());
		//货物类型添加 
		expressDiscountDetail.setDiscountRule(PricingConstants.DISCOUNTRULE);
		expressDiscountDetail.setDiscountRuleName(detailList.get(PricingConstants.TWO));
		expressDiscountDetail.setGoodsTypeId(goodsTypeEntity.getId());
		expressDiscountDetail.setGoodsTypeCode(goodsTypeEntity.getCode());
		expressDiscountDetail.setGoodsTypeName(goodsTypeEntity.getName());
		//产品添加
		expressDiscountDetail.setProductName(productEntity.getName());
		expressDiscountDetail.setProductCode(productEntity.getCode());
		//范围添加
		expressDiscountDetail.setLeftRange(new BigDecimal(Integer.parseInt(detailList.get(PricingConstants.FIVE))));
		expressDiscountDetail.setRightRange(new BigDecimal(Integer.parseInt(detailList.get(PricingConstants.SIX))));
		//首重续重添加
		expressDiscountDetail.setFirstDiscountRate(new BigDecimal(Integer.parseInt(detailList.get(PricingConstants.SEVEN))));
		expressDiscountDetail.setRenewalDiscountRate(new BigDecimal(Integer.parseInt(detailList.get(PricingConstants.EIGHT))));
		//货币类型
		expressDiscountDetail.setCurrencyCode(PricingConstants.CURRENCY_CODE);
		expressDiscountDetail.setVersionNo(nowDate.getTime());
		//费用类型
		expressDiscountDetail.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
		//续重最低费率
		expressDiscountDetail.setContinueHeavyLowestRate(new BigDecimal(Integer.parseInt(detailList.get(PricingConstants.NINE))));
		
		//创建时间、创建人、创建人所在部门
		expressDiscountDetail.setCreateUserCode(currentUser.getEmployee().getEmpCode());
		expressDiscountDetail.setCreateOrgCode(currentDept.getCode());
	    expressDiscountDetail.setCreateTime(nowDate);
	}
	
	/***
	 * 
	* @author: 王增明
	* @Title: packingDiscountEntity 
	* @Description: 
	*    #:封装折扣主方案
	* @param @return    设定文件 
	* @return 返回类型 
	* @throws
	 */
	private ExpressDiscountEntity packingDiscountEntity(
			List<String> discountList,CustomerEntity customerEntity,UserEntity currentUser,OrgAdministrativeInfoEntity currentDept,
			Map<String, String> weeksEntityMap, String weeksName,List<DataDictionaryValueEntity> channelList) {
		String uuId = UUIDUtils.getUUID();
		ExpressDiscountEntity discountEntity = new ExpressDiscountEntity();
    	discountEntity.setId(uuId);
    	//方案名称
	    discountEntity.setName(discountList.get(PricingConstants.ZERO));
	    //客户信息
	    discountEntity.setCustomerName(customerEntity.getName());
	    discountEntity.setCustomerCode(customerEntity.getCusCode());
	    //周特惠
	    discountEntity.setWeekNames(weeksName);
	    discountEntity.setWeekCodes(weeksEntityMap.get(weeksName));
	    //方案类型
	    discountEntity.setPlanType(PricingConstants.DEFAULTPLANTYPE);
	    //创建时间、创建人、创建人所在部门
	    Date nowDate = new Date();
	    discountEntity.setCreateUserCode(currentUser.getEmployee().getEmpCode());
	    discountEntity.setModifyUserCode(currentUser.getEmployee().getEmpCode());
	    discountEntity.setCreateOrgCode(currentDept.getCode());
	    discountEntity.setModifyOrgCode(currentDept.getCode());
	    discountEntity.setCreateTime(nowDate);
	    discountEntity.setVersionNo(nowDate.getTime());
	    try {
			discountEntity.setBeginTime(DateUtil.stringToDate(discountList.get(PricingConstants.THREE),PricingConstants.PATTEN));
			discountEntity.setEndTime(DateUtil.stringToDate(discountList.get(PricingConstants.FOUR),PricingConstants.PATTEN));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new FileException("折扣方案导入时发生错误 ：开始时间或截止时间格式错误", "折扣方案导入时发生错误 ：开始时间或截止时间格式错误");
		}
	    discountEntity.setRemark(discountList.get(PricingConstants.SEVEN));
	    if(CollectionUtils.isNotEmpty(channelList)){
	    	for(DataDictionaryValueEntity dictionary : channelList){
	    		MarketingEventChannelEntity channelEntity = new MarketingEventChannelEntity();
		    	String channelCode = dictionary.getValueCode();
				channelEntity.setSalesChannelCode(channelCode);
				channelEntity.setSalesChannelId(channelCode);
				channelEntity.setId(UUIDUtils.getUUID());
				channelEntity.setMarketingEventId(uuId);
				try {
					channelEntity.setBeginTime(DateUtil.stringToDate(discountList.get(PricingConstants.THREE),PricingConstants.PATTEN));
					channelEntity.setEndTime(DateUtil.stringToDate(discountList.get(PricingConstants.FOUR),PricingConstants.PATTEN));
				} catch (ParseException e) {
					e.printStackTrace();
					throw new FileException("折扣方案导入时发生错误 ：开始时间或截止时间格式错误", "折扣方案导入时发生错误 ：开始时间或截止时间格式错误");
				}
				channelEntity.setCreateUser(currentUser.getEmployee().getEmpCode());
				channelEntity.setCreateOrgCode(currentDept.getCode());
				channelEntity.setModifyOrgCode(currentDept.getCode());
				channelEntity.setCreateDate(nowDate);
				channelEntity.setVersionNo(nowDate.getTime());
				//添加快递折扣方案适用渠道信息
				this.marketingEventChannelDao.insertSelective(channelEntity);
	    	}
	    }
		return discountEntity;
	}
	
	/***
	 * 
	* @author: 王增明
	* @Title: findExpressDiscountByPlanNames 
	* @Description: (根据多个方案名称查出返回类型map) 
	* @param @param names
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, ExpressDiscountEntity> findExpressDiscountByPlanNames(
			List<String> names) {
		Map map = new HashMap();
		map.put("planNames", names);
		List<ExpressDiscountEntity> discountEntityList = this.expressDiscountPlanDao.findExpressDiscountByPlanNames(map);
		if (CollectionUtils.isNotEmpty(discountEntityList)) {
			Map<String, ExpressDiscountEntity> returnMap = new HashMap<String, ExpressDiscountEntity>();
			for (int loop = 0; loop < discountEntityList.size(); loop++) {
				returnMap.put(discountEntityList.get(loop).getName(), discountEntityList.get(loop));
			}
			return returnMap;
		}
		return null;
	}
}
