/**
 *  initial comments.
 *  
 *  
 *  
 *  
 *  维护折扣优先级定义管理
 *  
 *  
 *  
 *  
 *  
 *  增值优惠与价格折扣都以该优先级
 *  
 *  
 *  
 *  
 *  
 *  来判定处理
 *  
 *  
 *  
 *  
 *  
 */
/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * 
 * you may not use this file except in compliance with the License.
 * 
 * 
 * You may obtain a copy of the License at
 * 
 * 
 * 
 * 
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * 
 * 
 * distributed under the License is distributed on an "AS IS" BASIS,
 * 
 * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * 
 * See the License for the specific language governing permissions and
 * 
 * 
 * limitations under the License.
 * 
 * 
 * 
 * PROJECT NAME	: pkp-pricing
 * 
 * 
 * 
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/pricing/server/service/impl/DiscountPriorityService.java
 * 
 * 
 * 
 * 
 * FILE NAME        	: DiscountPriorityService.java
 * 
 * 
 * 
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * 
 * 
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * 
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 * 
 * 
 * 
 ******************************************************************************/
package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IDiscountPriorityDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IDiscountPriorityService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.DiscountPriorityEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.DiscountPriorityException;
import com.deppon.foss.module.pickup.pricing.server.cache.DiscountPriorityCacheDeal;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.google.inject.Inject;


/**
 * 
 * 折扣优先级操作类
 * 
 * 
 * @author sz
 * 
 * 
 * @date 2012-12-5 下午3:16:09
 * 
 * 
 * @since
 * 
 * 
 * @version
 */
public class DiscountPriorityService implements IDiscountPriorityService {
	/**
	 * 日志
	 */
	private static final Logger log = Logger.getLogger(DiscountPriorityService.class);
	/**
	 * 
	 * 优先级DAO
	 * 
	 */
    @Inject
    private IDiscountPriorityDao discountPriorityDao;
    
    	/**
    	 * 
    	 * 
	 * 优先级缓存
	 * 
	 * 
	 */
    @Inject
    private DiscountPriorityCacheDeal discountPriorityCacheDeal;

    /**
     * 
	 * @Description: 根据条件查询优先级集合(分页)
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 上午11:33:37
	 * 
	 * @param @param priorityEntity
	 * 
	 * @param @param start
	 * 
	 * @param @param limit
	 * 
	 * @param @return
	 * 
	 * @version V1.0
	 */
	@Override
	public List<DiscountPriorityEntity> queryPagingByCodition(
			DiscountPriorityEntity priorityEntity, int start, int limit) {
		return discountPriorityDao.selectPagingByCondition(priorityEntity, start, limit);
	}
	/**
	 * 
	 * @Description: 根据条件查询优先级总数（分页）
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 上午11:37:28
	 * 
	 * @param priorityEntity
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public Integer countPagingByCodition(DiscountPriorityEntity priorityEntity) {
		return discountPriorityDao.countPagingByCondition(priorityEntity);
	}
	/**
	 * 
	 * @Description: 根据条件查询优先级集合
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 上午11:33:37
	 * 
	 * @param @param priorityEntity
	 * 
	 * @param @param start
	 * 
	 * @param @param limit
	 * 
	 * @param @return
	 * 
	 * @version V1.0
	 */
	@Override
	public List<DiscountPriorityEntity> queryByCodition(DiscountPriorityEntity priorityEntity) {
		return discountPriorityDao.selectByCondition(priorityEntity);
	}
	/**
	 * 
	 * @Description: 根据主键ID查询优先级记录
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * @date 2012-12-17 上午11:40:29
	 * 
	 * @param priorityId
	 * 
	 * @version V1.0
	 * 
	 */
	@Override
	public DiscountPriorityEntity selectByPrimaryKey(String priorityId) {
		return discountPriorityDao.selectByPrimaryKey(priorityId);
	}
	/**
	 * 
	 * @Description: 增加优先级记录
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 上午11:36:18
	 * 
	 * @param priorityEntity
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public void addDiscountPriority(DiscountPriorityEntity priorityEntity) {
	        /***
	         * 设置折扣优先级UUID
	         * ****/
		String priorityUUId = UUIDUtils.getUUID();
		/***
		 * 设置折扣优先级UUID
		 * ****/
		priorityEntity.setId(priorityUUId);
		/***
		 * 设置折扣优先级级别 1.2.3种
		 *  ****/
		priorityEntity.setLevel(getMaxLevel());
		/***
		 * 创建用户
		 *  ****/
		priorityEntity.setCreateUser(getCurrentUserCode());
		/***
		 * 创建部门 
		 * ****/
		priorityEntity.setCreateOrgCode(getCurrentOrgCode());
		/***
		 * 创建时间 
		 * ****/
		priorityEntity.setCreateDate(new Date());
		/***
		 * 创建版本
		 * ****/
		priorityEntity.setVersionNo(new Date().getTime());
		/***
		 * 添加折扣优先级
		 ***/
		discountPriorityDao.insertSelective(priorityEntity);
	}
	/**
	 * 
	 * @Description: 修改优先级记录
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 上午11:39:03
	 * 
	 * @param priorityEntity
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@Override
	public void modifyDiscountPriority(DiscountPriorityEntity priorityEntity) {
	    	/***
	    	 * 修改时间
	    	 *  ****/
		priorityEntity.setModifyDate(new Date());
		/***
		 * 修改用户 
		 * ***
		 * */
		priorityEntity.setModifyUser(getCurrentUserCode());
		/***
		 * 修改部门code
		 ****/
		priorityEntity.setModifyOrgCode(getCurrentOrgCode());
		/***
		 * 创建版本 
		 * ****/
		priorityEntity.setVersionNo(new Date().getTime());
		/***
		 * 修改
		 * ****/
		discountPriorityDao.updateByPrimaryKeySelective(priorityEntity);
	}
	/**
	 * 
	 * @Description: 根据主键ID删除优先级记录
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 上午11:40:29
	 * 
	 * @param priorityId
	 * 
	 * @version V1.0
	 */
	@Override
	public void deleteDiscountPriority(String priorityId) {
		discountPriorityDao.deleteByPrimaryKey(priorityId);
	}
	/**
	 * 
	 * @Description: 提升一级优先级
	 * 
	 * Company:IBM
	 * 
	 * @author IBMDP-sz
	 * 
	 * @date 2012-12-17 下午7:27:48
	 * 
	 * @param priorityId
	 * 
	 * @version V1.0
	 */
	public void upPriority(String priorityId) throws DiscountPriorityException {
	    	/***
	    	 * 获得折扣优先级 
	    	 * ****/
		DiscountPriorityEntity priorityEntity = discountPriorityDao.selectByPrimaryKey(priorityId);
		/***
		 * 判断是否有记录 
		 * 
		 * ****/
		if(priorityEntity != null && priorityEntity.getLevel() > 0) {
		    	/***
		    	 * 
		    	 * 获得优先级级别
		    	 * 
		    	 ****/
			int currentLevel = priorityEntity.getLevel();
			/***
			 * 
			 * 如果级别>0 
			 * 
			 * ****/
			if(currentLevel > 0) {
			    	 /***
			    	  * 
			    	  * 
			    	  * 是否为最高级别，则抛出提示 
			    	  * 
			    	  * 
			    	  * ****/
				if(currentLevel == 1) {
					throw new DiscountPriorityException("已经是最高级别，不能再升高级别", DiscountPriorityException.DISCOUNT_PRIORITY_CHECK_PARAMETER_ERROR_CODE);
				} else {
				 /***
				  * 
				  * 
				  * 升级级别 
				  * 
				  * 
				  * ****/
					DiscountPriorityEntity entity = new DiscountPriorityEntity();
					entity.setLevel(currentLevel-1);
					List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityDao.selectByCondition(entity);
					if(discountPriorityEntities != null && discountPriorityEntities.size() > 0) {
						if(discountPriorityEntities.size() > 1) {
							throw new DiscountPriorityException("已有级别设置异常，请联系管理员", DiscountPriorityException.DISCOUNT_PRIORITY_CHECK_PARAMETER_ERROR_CODE);
						} else {
						    	/***
						    	 * 
						    	 * 
						    	 * 安全过滤错误提示后，处理升级
						    	 * 
						    	 * 
						    	 *  ****/
							DiscountPriorityEntity priorityEntity2 = discountPriorityEntities.get(0);
							priorityEntity2.setLevel(currentLevel);
							priorityEntity.setLevel(currentLevel-1);
							discountPriorityDao.updateByPrimaryKeySelective(priorityEntity);
							discountPriorityDao.updateByPrimaryKeySelective(priorityEntity2);
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * 
	 * @Description: 降低一级优先级
	 * 
	 * 
	 * Company:IBM
	 * 
	 * 
	 * @author IBMDP-sz
	 * 
	 * 
	 * 
	 * @date 2012-12-17 下午7:28:29
	 * 
	 * 
	 * @param priorityId
	 * 
	 * 
	 * @version V1.0
	 */
	public void downPriority(String priorityId) throws DiscountPriorityException {
		DiscountPriorityEntity priorityEntity = discountPriorityDao.selectByPrimaryKey(priorityId);
		if(priorityEntity != null && priorityEntity.getLevel() > 0) {
			int maxLevel = getMaxLevel();
			int currentLevel = priorityEntity.getLevel();
			if(currentLevel > 0) {
				if(currentLevel == maxLevel) {
					throw new DiscountPriorityException("已经是最低级别，不能再降低级别", DiscountPriorityException.DISCOUNT_PRIORITY_CHECK_PARAMETER_ERROR_CODE);
				} else {
					DiscountPriorityEntity entity = new DiscountPriorityEntity();
					entity.setLevel(currentLevel+1);
					List<DiscountPriorityEntity> discountPriorityEntities = discountPriorityDao.selectByCondition(entity);
					if(discountPriorityEntities != null && discountPriorityEntities.size() > 0) {
						if(discountPriorityEntities.size() > 1) {
							throw new DiscountPriorityException("已有级别设置异常，请联系管理员", DiscountPriorityException.DISCOUNT_PRIORITY_CHECK_PARAMETER_ERROR_CODE);
						} else {
							DiscountPriorityEntity priorityEntity2 = discountPriorityEntities.get(0);
							priorityEntity2.setLevel(currentLevel);
							priorityEntity.setLevel(currentLevel+1);
							discountPriorityDao.updateByPrimaryKeySelective(priorityEntity);
							discountPriorityDao.updateByPrimaryKeySelective(priorityEntity2);
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 * @Description: 从缓存中获取优先级
	 * 
	 * @author FOSSDP-sz
	 * 
	 * @date 2013-2-22 上午10:29:52
	 * 
	 * @param dpcode
	 * 
	 * @param billDate
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	@SuppressWarnings("static-access")
	@Override
	public List<DiscountPriorityEntity> getDiscountPriorityByCache() {
		List<DiscountPriorityEntity> discountPriorityEntities = null;
		try {
			discountPriorityEntities = discountPriorityCacheDeal.getDiscountPriorityByCache(discountPriorityCacheDeal.DISCOUNT_PRIORITY_CACHE);
		} catch (Exception e) {
			log.info("无法获取折扣优先级缓存数据",e);
		}
		if(CollectionUtils.isEmpty(discountPriorityEntities)) {
			DiscountPriorityEntity entity = new DiscountPriorityEntity();
			entity.setBeginTime(new Date());
			discountPriorityEntities = this.queryByCodition(entity);
		}
		return discountPriorityEntities;
	}
	/**
	 * 
	 * @Description: 刷新缓存
	 * 
	 * 
	 * @author FOSSDP-sz
	 * 
	 * 
	 * @date 2013-2-22 上午10:30:51
	 * 
	 * 
	 * @param dpcode
	 * 
	 * 
	 * @version V1.0
	 * 
	 */
	@SuppressWarnings("static-access")
	@Override
	public void refreshDiscountPriorityCache() {
		try {
			discountPriorityCacheDeal.getDiscountPriorityCache().invalid(discountPriorityCacheDeal.DISCOUNT_PRIORITY_CACHE);
		} catch (Exception e) {
			log.info("无法刷新折扣优先级缓存数据",e);
		}
	}
	/**
	 * 
	 * 
	 * @Description: 获取最大级别
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * @date 2013-3-14 下午2:28:48
	 * 
	 * @return
	 * 
	 * @version V1.0
	 */
	private int getMaxLevel() {
		Integer num = discountPriorityDao.selectMaxLevel();
		if(num == null) {
			num = 0;
		}
		return num + 1;
	}
	/**
	 * 
	 * 
	 * @Description: 获取当前用户所有部门
	 * 
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * 
	 * @date 2013-3-14 下午2:28:48
	 * 
	 * 
	 * @return
	 * 
	 * 
	 * @version V1.0
	 */
	private String getCurrentOrgCode() {
		OrgAdministrativeInfoEntity currentDept = FossUserContext.getCurrentDept();
		return currentDept.getCode();
	}
	/**
	 * 
	 * @Description: 获取当前用户
	 * 
	 * 
	 * @author FOSSDP-Administrator
	 * 
	 * 
	 * 
	 * @date 2013-3-14 下午2:28:48
	 * 
	 * 
	 * @return
	 * 
	 * 
	 * 
	 * @version V1.0
	 */
	private String getCurrentUserCode() {
		UserEntity currentUser = (UserEntity) UserContext.getCurrentUser();
		return currentUser.getEmployee().getEmpCode();
	}
	/**
	 * 设置 优先级DAO.
	 *
	 *
	 *
	 * @param discountPriorityDao the new 优先级DAO
	 * 
	 * 
	 * 
	 */
	public void setDiscountPriorityDao(IDiscountPriorityDao discountPriorityDao) {
		this.discountPriorityDao = discountPriorityDao;
	}
	/**
	 * 
	 * 
	 * 
	 * 设置 优先级缓存.
	 *
	 *
	 *
	 * @param discountPriorityCacheDeal the new 优先级缓存
	 * 
	 * 
	 * 
	 */
	public void setDiscountPriorityCacheDeal(DiscountPriorityCacheDeal discountPriorityCacheDeal) {
		this.discountPriorityCacheDeal = discountPriorityCacheDeal;
	}
}