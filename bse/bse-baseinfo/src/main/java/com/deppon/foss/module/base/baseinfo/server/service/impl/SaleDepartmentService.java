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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/SaleDepartmentService.java
 * 
 * FILE NAME        	: SaleDepartmentService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.SqlUtil;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.cache.CacheManager;
import com.deppon.foss.framework.cache.ICache;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryMapManageService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesDescExpandService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISatellitePartSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncAllSalesDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PickAreaAndDeliveryAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesDescExpandEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SatellitePartSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SyncSalesInfoToFinSelfDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OrgAdministrativeInfoException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SalesDepartmentException;
import com.deppon.foss.module.base.baseinfo.server.service.impl.esb.SyncSalesDepartmentService;
import com.deppon.foss.module.base.baseinfo.server.service.impl.esb.SyncSalesDepartmentToFinService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.common.FossTTLCache;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 营业部 Service实现
 * 
 * @author 087584-foss-lijun
 * @date 2012-11-2 下午5:31:57
 */
public class SaleDepartmentService implements ISaleDepartmentService {

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SaleDepartmentService.class);

	/**
	 * 同步信息到OWS
	 */
	private SyncSalesDepartmentService syncSalesDepartmentService;
	/**
	 * 同步给发票项目
	 */
	private SyncSalesDepartmentToFinService syncSalesDepartmentToFinService;
	/**
	 * 同步给周年系统，订单，整车，快递等
	 */
	private ISyncAllSalesDepartmentService syncAllSalesDepartmentService;
	/**
	 * 营业部Dao声明
	 */
	@Inject
	private ISaleDepartmentDao saleDepartmentDao;

	@Inject
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	@Inject
	private ISalesBillingGroupService salesBillingGroupService;
	@Inject
	private ISalesDescExpandService salesDescExpandService;
	@Inject
	private ISatellitePartSalesDeptService satellitePartSalesDeptService;

	private IExpressDeliveryMapManageService expressDeliveryMapManageService;

	public void setExpressDeliveryMapManageService(
			IExpressDeliveryMapManageService expressDeliveryMapManageService) {
		this.expressDeliveryMapManageService = expressDeliveryMapManageService;
	}

	public void setSatellitePartSalesDeptService(
			ISatellitePartSalesDeptService satellitePartSalesDeptService) {
		this.satellitePartSalesDeptService = satellitePartSalesDeptService;
	}

	public void setSyncSalesDepartmentToFinService(
			SyncSalesDepartmentToFinService syncSalesDepartmentToFinService) {
		this.syncSalesDepartmentToFinService = syncSalesDepartmentToFinService;
	}

	public void setSyncAllSalesDepartmentService(
			ISyncAllSalesDepartmentService syncAllSalesDepartmentService) {
		this.syncAllSalesDepartmentService = syncAllSalesDepartmentService;
	}

	/**
	 * @param salesBillingGroupService
	 *            the salesBillingGroupService to set
	 */
	public void setSalesBillingGroupService(
			ISalesBillingGroupService salesBillingGroupService) {
		this.salesBillingGroupService = salesBillingGroupService;
	}

	public void setSalesDescExpandService(
			ISalesDescExpandService salesDescExpandService) {
		this.salesDescExpandService = salesDescExpandService;
	}

	/**
	 * 营业部 新增
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService#addSaleDepartment(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	@Transactional
	public SaleDepartmentEntity addSaleDepartment(SaleDepartmentEntity entity) {
		// 检查参数的合法性
		if (null == entity) {
			// 如果参数为null则返回空
			return null;
		}
		// 在网点管理模块勾选营业部"可派送"属性时，限制用户必须画完网点派送范围，这样能从根本上解决"派送网点范围可能为空"的问题。
		if (StringUtils.equals(entity.getDelivery(), FossConstants.YES)
				&& StringUtils.isBlank(entity.getDeliveryCoordinate())) {
			// 抛出异常
			throw new OrgAdministrativeInfoException(
					OrgAdministrativeInfoException.ORGADMINISTRATIVEINFO_SALE_DELIVERY_INVALID);
		}
		// 新增时，把部门名字设置进来
		OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(entity.getCode());
		// 判断查询结果是否为空
		if (org != null) {
			// 如果不为空则设置营业部名字
			entity.setName(org.getName());
		}
		// 执行新增操作
		SaleDepartmentEntity entityResult = saleDepartmentDao
				.addSaleDepartment(entity);
		// 查询结果非空判断
		if (entityResult != null) {
			try {
				// 声明营业部List
				List<SaleDepartmentEntity> saleDepartmentList = new ArrayList<SaleDepartmentEntity>();
				// 往营业部List里面添加数据
				saleDepartmentList.add(entityResult);
				// 同步给周边系统
				this.syncAllSaleDepartmentAround(entityResult, "1");
				// 进行同步操作
				syncSaleDepartmentDataToOws(saleDepartmentList);
				// 同步给发票项目
				syncSalesInfoToFinSelf(entityResult, "ADD");
			}
			// 捕获异常
			catch (Exception e) {
				// 打印日志信息
				LOGGER.error("营业部新增 addSaleDepartment()" + e);
			}
		}
		// 返回新增后的返回值
		return entityResult;
	}

	/**
	 * @author 273311
	 * @date 2016-3-24 上午10:31:57
	 * @param saleDepartmentList
	 * @param operateType
	 */
	private void syncAllSaleDepartmentAround(SaleDepartmentEntity entityResult,
			String operateType) {
		List<SaleDepartmentEntity> saleDepartmentList = new ArrayList<SaleDepartmentEntity>();
		saleDepartmentList.add(entityResult);
		syncAllSalesDepartmentService.syncAllSalesDepartmentDataToAll(
				saleDepartmentList, operateType);

	}

	/**
	 * 通过code标识来删除
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao#deleteSaleDepartment(java.lang.String)
	 */
	@Override
	@Transactional
	public SaleDepartmentEntity deleteSaleDepartment(SaleDepartmentEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			// 返回为空
			return null;
		}
		/**
		 * 作废营业部卫星点关系映射
		 */
		SatellitePartSalesDeptEntity satellitePartSalesDeptEntity = new SatellitePartSalesDeptEntity();
		satellitePartSalesDeptEntity.setModifyUser(entity.getModifyUser());
		// 若作废的营业部 本身是个卫星点部
		if (StringUtils.isNotBlank(entity.getSatelliteDept())
				&& entity.getSatelliteDept().equals(FossConstants.YES)) {
			// 设置卫星点编码
			satellitePartSalesDeptEntity.setSatelliteDeptCode(entity.getCode());
			satellitePartSalesDeptService
					.deleteSatelliteBySatelliteCode(satellitePartSalesDeptEntity); // 根据卫星点部编码作废营业部映射卫星点关系
		} else {
			// 设置营业部编码
			satellitePartSalesDeptEntity.setSalesDeptCode(entity.getCode());
			satellitePartSalesDeptService
					.deleteSatelliteBySalesCode(satellitePartSalesDeptEntity);// 根据营业部编码作废营业部映射卫星点关系
		}

		// 删除营业部信息
		SaleDepartmentEntity entityResult = saleDepartmentDao
				.deleteSaleDepartment(entity);
		// 判断返回结果是否为空
		if (entityResult != null) {
			try {
				// 清除掉缓存里面的数据
				invalidEntity(entity.getCode());
				List<SaleDepartmentEntity> saleDepartmentLst = new ArrayList<SaleDepartmentEntity>();
				saleDepartmentLst.add(entityResult);
				// 同步给周边系统
				this.syncAllSaleDepartmentAround(entityResult, "3");
				// 进行同步操作
				syncSaleDepartmentDataToOws(saleDepartmentLst);
				// 进行同步FIN_SELF发票项目操作
				syncSalesInfoToFinSelf(entityResult, "GIVEUP");
			} catch (Exception e) {
				LOGGER.info("删除营业部信息 deleteSaleDepartment()" + e);
			}
		}
		// 返回查询结果
		return entityResult;
	}

	/**
	 * 更新
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentDao#updateSaleDepartment(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	@Transactional
	public SaleDepartmentEntity updateSaleDepartment(SaleDepartmentEntity entity) {
		// 检查参数的合法性
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			// 返回null
			return null;
		}
		// 在网点管理模块勾选营业部"可派送"属性时，限制用户必须画完网点派送范围，这样能从根本上解决"派送网点范围可能为空"的问题。(由于页面的重构，该代码职能在)
		// if (StringUtils.equals(entity.getDelivery(), FossConstants.YES)
		// && StringUtils.isBlank(entity.getDeliveryCoordinate())) {
		// // 抛出异常
		// throw new OrgAdministrativeInfoException(
		// OrgAdministrativeInfoException.ORGADMINISTRATIVEINFO_SALE_DELIVERY_INVALID);
		// }
		// 判断是否作废一个卫星点
		boolean flag = this.judgeDeleteSatellitePart(entity);

		if (StringUtils.equals(entity.getCanExpressDelivery(),
				FossConstants.ACTIVE)) {
			// 判断快递派送区域中有无生效的快点点部坐标信息--
			ExpressDeliveryMapManageEntity mapEntity = expressDeliveryMapManageService
					.queryExpressDeliveryMapManageEntityByCode(entity.getCode());
			if (null != mapEntity
					&& StringUtils
							.equals(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_ACTIVED,
									mapEntity.getVerifyState())) {
				entity.setExpressDeliveryCoordinate(mapEntity
						.getExpressDeliveryCoordinate());
				entity.setDepartServiceArea(mapEntity.getDepartServiceArea());
			}
		}

		// 执行更新操作
		SaleDepartmentEntity entityResult = saleDepartmentDao
				.updateSaleDepartment(entity);
		// 若作废
		if (flag) {
			SatellitePartSalesDeptEntity satellitePartSalesDeptEntity = new SatellitePartSalesDeptEntity();
			satellitePartSalesDeptEntity.setSatelliteDeptCode(entity.getCode());
			satellitePartSalesDeptEntity.setModifyUser(entity.getModifyUser());
			// 作废卫星点营业部映射关系
			satellitePartSalesDeptService
					.deleteSatelliteBySatelliteCode(satellitePartSalesDeptEntity);
		}
		// 判断查询结果是否为空
		if (entityResult != null) {
			try {
				// 使缓存的数据失效
				invalidEntity(entity.getCode());
				// 执行营业部信息查询
				List<SaleDepartmentEntity> saleDepartmentLst = saleDepartmentDao
						.querySaleDepartmentByCodeActive(
								Arrays.asList(entity.getCode()),
								FossConstants.INACTIVE);
				// 如果查询结果为空
				if (CollectionUtils.isNotEmpty(saleDepartmentLst)) {
					// 进行OWS同步操作
					// 把查询结果放在List里面
					saleDepartmentLst.add(entityResult);
					// 同步给周边系统
					this.syncAllSaleDepartmentAround(entityResult, "2");
					// 把结果发送出去
					syncSaleDepartmentDataToOws(saleDepartmentLst);

					// 进行同步FIN_SELF发票项目操作
					syncSalesInfoToFinSelf(entityResult, "MODIFY");
				}
			}
			// 捕获异常
			catch (Exception e) {
				// 打印Log日志
				LOGGER.error("更新营业部 updateSaleDepartment()", e);
			}
		}
		// 返回更新结果
		return entityResult;
	}

	/**
	 * 
	 * <p>
	 * 同步营业部信息给发票项目
	 * </p>
	 * 
	 * @date 2014-6-18 下午4:00:03
	 * @author 130566-ZengJunfan
	 * @param entityResult
	 */
	private void syncSalesInfoToFinSelf(SaleDepartmentEntity entityResult,
			String operatStatus) {
		SyncSalesInfoToFinSelfDto dto = new SyncSalesInfoToFinSelfDto();
		// 非空校验,封装dto
		if (entityResult != null && StringUtils.isNotBlank(operatStatus)) {
			dto.setOrgName(entityResult.getName());
			// dto.setOrgCode(entityResult.getCode());
			// 获取地址
			OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoByCodeNoCache(entityResult
							.getCode());
			if (org != null && StringUtils.isNotBlank(org.getAddress())
					&& StringUtils.isNotBlank(org.getUnifiedCode())) {
				// 组织标杆编码
				dto.setOrgCode(org.getUnifiedCode());
				// 地址
				dto.setOrgAddress(org.getAddress());
			}
			// 发票项目要求 传递ADD MODIFY GIVEUP
			// 判断
			if (StringUtils.equals(operatStatus, "ADD")) {
				dto.setActive("ADD");
			} else if (StringUtils.equals(operatStatus, "MODIFY")) {
				dto.setActive("MODIFY");
			} else {
				dto.setActive("GIVEUP");
			}
			// 同步
			syncSalesDepartmentToFinService.syncSalesInfoToFinSelf(dto);
		}

	}

	/**
	 * 判断是否是要作废‘卫星点’
	 * 
	 * @author 130566-foss-zengjunFan
	 * @date 2014-04-08 下午15:31:57
	 * @param entity
	 * @return
	 */
	private boolean judgeDeleteSatellitePart(SaleDepartmentEntity entity) {
		// 查询更新前的记录
		SaleDepartmentEntity result = saleDepartmentDao
				.querySaleDepartmentByCode(entity.getCode());
		// 若更新前记录‘是否卫星点部’是‘Y’
		if (null != result && StringUtils.isNotBlank(result.getSatelliteDept())
				&& result.getSatelliteDept().equals(FossConstants.YES)) {
			// 而要更新后的‘是否卫星点’为‘N’
			if (entity.getSatelliteDept().equals(FossConstants.NO)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#querySaleDepartmentByCode(java.lang.String)
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code) {
		// 对code进行非空判断
		if (StringUtils.isBlank(code)) {
			// 如果为空则返回null
			return null;
		}
		// 从缓存中查找
		SaleDepartmentEntity entityResult = null;

		if (SqlUtil.loadCache) {// 客户端不读缓存
			entityResult = queryEntityCache(code);
		} else {
			// 执行查询
			entityResult = saleDepartmentDao.querySaleDepartmentByCode(code);
		}
		// 设置该对象其他的属性值
		entityResult = this.attachOrgName(entityResult);
		// 返回查询结果
		return entityResult;
	}
	
	/** 
	 * <p>查询营业部信息，不关联组织名称等只查询基础信息</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-5-12 下午8:38:57
	 * @param code
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService#querySaleDepartmentNoAttach(java.lang.String)
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentNoAttach(String code) {
		// 对code进行非空判断
		if (StringUtils.isBlank(code)) {
			// 如果为空则返回null
			return null;
		}
		// 从缓存中查找
		SaleDepartmentEntity entityResult = null;
		if (SqlUtil.loadCache) {// 客户端不读缓存
			entityResult = queryEntityCache(code);
		} else {
			// 执行查询
			entityResult = saleDepartmentDao.querySaleDepartmentByCode(code);
		}
		// 返回查询结果
		return entityResult;
	}
	/**
	 * 通过code从缓存中查询营业部简单信息，若缓存中没有找到再走一次数据库查询
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 268984 
	 * @date 2016-2-24 下午2:17:01
	 * @param code
	 * @return
	 * @see
	 */
	@Override
    public SaleDepartmentEntity querySimpleSaleDepartmentByCodeCache(String code){
		// 对code进行非空判断
				if (StringUtils.isBlank(code)) {
					// 如果为空则返回null
					return null;
				}
				// 从缓存中查找
				SaleDepartmentEntity entityResult = null;

					entityResult = queryEntityCache(code);
				//如果缓存中没有找到，再走一次数据库查询
				if(entityResult==null){
					// 执行查询
					entityResult = saleDepartmentDao.querySaleDepartmentByCode(code);
				}
				// 设置该对象其他的属性值
				entityResult = this.attachOrgName(entityResult);
				// 返回查询结果
				return entityResult;
    }
	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author foss-zhangxiaohui
	 * @date 2013-04-25 下午5:29:19
	 * 
	 * */

	@Override
	public SaleDepartmentEntity querySaleDepartmentByCodeNoCache(String code) {
		// 对code进行非空判断
		if (StringUtils.isBlank(code)) {
			// 如果为空则返回null
			return null;
		}
		// 从缓存中查找
		SaleDepartmentEntity entityResult = saleDepartmentDao
				.querySaleDepartmentByCode(code);
		// 设置该对象其他的属性值
		entityResult = this.attachOrgName(entityResult);
		// 根据CODE查询部门快递员数添加到营业部实体-187862-dujunhui
		if (entityResult != null) {
			entityResult.setExpressManNum((int) saleDepartmentDao
					.queryExpressManNumBySaleDepartmentCode(code));
		}
		// 返回查询结果
		return entityResult;
	}

	/**
	 * 精确查询 通过 CODE 查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午6:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#querySaleDepartmentByCode(java.lang.String)
	 */
	@Override
	public SaleDepartmentEntity querySimpleSaleDepartmentByCode(String code) {
		// 通过code精准查询
		if (StringUtils.isBlank(code)) {
			// 返回null
			return null;
		}
		// 从缓存中查找
		SaleDepartmentEntity entityResult = null;
		// 查询缓存
		if (SqlUtil.loadCache) {// 客户端不读缓存 晓伟添加 如不清楚 可以问晓伟
			entityResult = queryEntityCache(code);
		} else {
			// 通过code做精准查询
			entityResult = saleDepartmentDao.querySaleDepartmentByCode(code);
		}
		// 返回查询结果
		return entityResult;
	}

	/**
	 * 精确查询 根据多个编码批量查询
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-18 下午4:1:47
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#querySaleDepartmentByCode(java.lang.String)
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentBatchByCode(
			String[] codes) {
		// 对参数进行非空判断
		if (ArrayUtils.isEmpty(codes)) {
			// 返回null
			return null;
		}
		// 以数组为参数执行查询
		List<SaleDepartmentEntity> entityResuts = saleDepartmentDao
				.querySaleDepartmentBatchByCode(codes);
		// 设置该对象其他的属性值
		entityResuts = this.attachOrgName(entityResuts);
		// 返回查询结果
		return entityResuts;
	}

	/**
	 * 精确查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:11:15
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#querySaleDepartmentExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity,
	 *      int, int)
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentExactByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		// 执行分页查询
		List<SaleDepartmentEntity> entityResuts = saleDepartmentDao
				.querySaleDepartmentExactByEntity(entity, start, limit);
		// 设置该对象的其他属性
		entityResuts = this.attachOrgName(entityResuts);
		// 返回结果
		return entityResuts;
	}

	@Override
	public List<SaleDepartmentEntity> querySimpleSaleDepartmentExactByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		// 执行分页查询
		return saleDepartmentDao.querySaleDepartmentExactByEntity(entity,
				start, limit);
	}

	/**
	 * 
	 * 根据外场的部门编码查询其驻地营业部的部门编码 如果有多个驻地部门，随便找一个
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 7, 2012 2:24:13 PM
	 * @param transferCenterCode
	 * @return
	 * @see
	 */
	@Override
	public String queryStationSaleCodeByTransferCenterCode(
			String transferCenterCode) {
		// 声明一个对象做查询条件
		SaleDepartmentEntity condition = new SaleDepartmentEntity();
		// 设置外场部门编码
		condition.setTransferCenter(transferCenterCode);
		// 设置驻地营业部
		condition.setStation(FossConstants.YES);
		List<SaleDepartmentEntity> list = querySimpleSaleDepartmentExactByEntity(
				condition, 0, BigDecimal.ONE.intValue());
		if (CollectionUtils.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return list.get(0).getCode();
	}

	/**
	 * 
	 * 根据外场的部门编码查询其可到达驻地营业部的部门编码
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 7, 2012 2:24:13 PM
	 * @param transferCenterCode
	 * @return
	 * @see
	 */
	@Override
	public String queryArriveStationSaleCodeByTransferCenterCode(
			String transferCenterCode) {
		// 声明一个对象做查询条件
		SaleDepartmentEntity condition = new SaleDepartmentEntity();
		// 设置外场部门编码
		condition.setTransferCenter(transferCenterCode);
		condition.setArrive(FossConstants.YES);
		// 设置驻地营业部
		condition.setStation(FossConstants.YES);
		List<SaleDepartmentEntity> list = querySimpleSaleDepartmentExactByEntity(
				condition, 0, BigDecimal.ONE.intValue());
		if (CollectionUtils.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return list.get(0).getCode();
	}

	/**
	 * 
	 * 根据外场的部门编码查询其可出发驻地营业部的部门编码
	 * 
	 * @author foss-zhujunyong
	 * @date Nov 7, 2012 2:24:13 PM
	 * @param transferCenterCode
	 * @return
	 * @see
	 */
	@Override
	public String queryLeaveStationSaleCodeByTransferCenterCode(
			String transferCenterCode) {
		// 声明一个对象做查询条件
		SaleDepartmentEntity condition = new SaleDepartmentEntity();
		// 设置外场部门编码
		condition.setTransferCenter(transferCenterCode);
		condition.setLeave(FossConstants.YES);
		// 设置驻地营业部
		condition.setStation(FossConstants.YES);
		List<SaleDepartmentEntity> list = querySimpleSaleDepartmentExactByEntity(
				condition, 0, BigDecimal.ONE.intValue());
		if (CollectionUtils.isEmpty(list)) {
			return StringUtils.EMPTY;
		}
		return list.get(0).getCode();
	}

	/**
	 * 精确查询-查询总条数，用于分页 动态的查询条件。
	 * 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-10-19 上午11:09:53
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#querySaleDepartmentExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	public long querySaleDepartmentExactByEntityCount(
			SaleDepartmentEntity entity) {
		return saleDepartmentDao.querySaleDepartmentExactByEntityCount(entity);
	}

	/**
	 * 模糊查询 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ISaleDepartmentService#deleteSaleDepartmentMore(java.lang.String[])
	 */
	@Override
	public List<SaleDepartmentEntity> querySaleDepartmentByEntity(
			SaleDepartmentEntity entity, int start, int limit) {
		List<SaleDepartmentEntity> entityResuts = saleDepartmentDao
				.querySaleDepartmentByEntity(entity, start, limit);

		entityResuts = this.attachOrgName(entityResuts);
		return entityResuts;
	}

	/**
	 * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @see com.deppon.foss.module.baseinfo.server.service.ISaleDepartmentService#querySaleDepartmentCountByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.district.shared.domain.SaleDepartmentEntity)
	 */
	@Override
	public long querySaleDepartmentByEntityCount(SaleDepartmentEntity entity) {
		return saleDepartmentDao.querySaleDepartmentByEntityCount(entity);
	}

	/**
	 * 下面是工具方法
	 */

	/**
	 * 提供给结算
	 * 
	 * 根据部门编码设置 最大临欠额度
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @param code
	 *            部门编码
	 * @param maxTempArrears
	 *            最大临欠额度
	 */
	@Override
	public boolean changeMaxTempArrears(String code, BigDecimal maxTempArrears) {
		SaleDepartmentEntity entityResuts = this
				.querySaleDepartmentByCode(code);

		entityResuts.setMaxTempArrears(maxTempArrears);
		this.updateSaleDepartment(entityResuts);

		return true;
	}

	/**
	 * 提供给结算
	 * 
	 * 根据部门编码设置 已用临欠额度
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-11-2 下午5:31:57
	 * @param code
	 *            部门编码
	 * @param maxTempArrears
	 *            已用临欠额度
	 */
	@Override
	public boolean changeUsedTempArrears(String code, BigDecimal usedTempArrears) {
		SaleDepartmentEntity entityResuts = this
				.querySaleDepartmentByCode(code);

		entityResuts.setUsedTempArrears(usedTempArrears);
		this.updateSaleDepartment(entityResuts);

		return true;
	}

	/**
	 * 给部门加上“名称”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	private SaleDepartmentEntity attachOrgName(SaleDepartmentEntity entity) {
		if (entity == null) {
			return entity;
		}
		// 设置集中开单组编码和名称列表
		if (CollectionUtils.isEmpty(entity.getBillingGroupList())) {
			entity.setBillingGroupList(salesBillingGroupService
					.queryBillingGroupListBySalesCode(entity.getCode()));
		}
		// // 添加集中开单组的名称
		// if (StringUtils.isEmpty(entity.getBillingGroupName())) {
		// String name =
		// orgAdministrativeInfoService.queryOrgAdministrativeInfoNameByCode(entity.getBillingGroup());
		// entity.setBillingGroupName(name);
		// }
		// 添加“驻地营业部所属外场”的名称
		if (StringUtils.isEmpty(entity.getTransferCenterName())) {
			String name = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoNameByCode(entity
							.getTransferCenter());
			entity.setTransferCenterName(name);
		}
		// 添加“转货部门”的名称
		if (StringUtils.isEmpty(entity.getTransferGoodDeptName())) {
			String name = orgAdministrativeInfoService
					.queryOrgAdministrativeInfoNameByCode(entity
							.getTransferGoodDept());
			entity.setTransferGoodDeptName(name);
		}
		// 将自提区域描述的扩展内容取出来（自提）
		if (FossConstants.YES.equals(entity.getPickAreaIsExpand())) {
			StringBuffer pickStringBuffer = new StringBuffer();
			pickStringBuffer.append(entity.getPickupAreaDesc());
			List<SalesDescExpandEntity> pickList = salesDescExpandService
					.querySalesDescExpandByCode(entity.getCode(),
							ComnConst.SALES_DEPARTMENT_DESC_EXPAND_TYPE_PICKUP);
			for (SalesDescExpandEntity salesDescExpandEntity : pickList) {
				pickStringBuffer.append(salesDescExpandEntity.getDescContent());
			}
			entity.setPickupAreaDesc(pickStringBuffer.toString());
		}
		// 将派送区域描述的扩展内容取出来(派送)
		if (FossConstants.YES.equals(entity.getDeliveryAreaIsExpand())) {
			StringBuffer deliveryStringBuffer = new StringBuffer();
			deliveryStringBuffer.append(entity.getDeliveryAreaDesc());
			List<SalesDescExpandEntity> deliveryList = salesDescExpandService
					.querySalesDescExpandByCode(
							entity.getCode(),
							ComnConst.SALES_DEPARTMENT_DESC_EXPAND_TYPE_DELIVERY);
			for (SalesDescExpandEntity salesDescExpandEntity : deliveryList) {
				deliveryStringBuffer.append(salesDescExpandEntity
						.getDescContent());
			}
			entity.setDeliveryAreaDesc(deliveryStringBuffer.toString());
		}
		// 将快递自提区域描述的扩展内容取出来
		if (FossConstants.YES.equals(entity.getExpressPickupAreaIsExp())) {
			StringBuffer expressPickupBuffer = new StringBuffer();
			expressPickupBuffer.append(entity.getExpressPickupAreaDesc());
			// 查询该扩展表中的快递自提的该营业部的扩展内容
			List<SalesDescExpandEntity> expressPickList = salesDescExpandService
					.querySalesDescExpandByCode(
							entity.getCode(),
							ComnConst.SALES_DEPARTMENT_DESC_EXPAND_TYPE_EXPRESS_PICKUP);
			if (CollectionUtils.isNotEmpty(expressPickList)) {
				for (SalesDescExpandEntity salesDescExpandEntity : expressPickList) {
					expressPickupBuffer.append(salesDescExpandEntity
							.getDescContent());
				}
			}
			entity.setExpressPickupAreaDesc(expressPickupBuffer.toString());
		}
		// 将快递派送区域描述的扩展内容取出来
		if (FossConstants.YES.equals(entity.getExpressDeliveryAreaIsExp())) {
			StringBuffer expressDeliveryBuffer = new StringBuffer();
			// 添加表中一部分描述内容到buffer中
			expressDeliveryBuffer.append(entity.getExpressDeliveryAreaDesc());
			// 查询该扩展表中的快递派送的该营业部的扩展内容
			List<SalesDescExpandEntity> expressDeliveryList = salesDescExpandService
					.querySalesDescExpandByCode(
							entity.getCode(),
							ComnConst.SALES_DEPARTMENT_DESC_EXPAND_TYPE_EXPRESS_DELIVERY);
			for (SalesDescExpandEntity salesDescExpandEntity : expressDeliveryList) {
				expressDeliveryBuffer.append(salesDescExpandEntity
						.getDescContent());
			}
			entity.setExpressDeliveryAreaDesc(expressDeliveryBuffer.toString());
		}
		return entity;
	}

	/**
	 * 给部门加上“名称”
	 * 
	 * @author 087584-foss-lijun
	 * @date 2012-12-3 下午4:55:43
	 */
	private List<SaleDepartmentEntity> attachOrgName(
			List<SaleDepartmentEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}

		for (SaleDepartmentEntity entity : entitys) {
			this.attachOrgName(entity);
		}

		return entitys;
	}

	/**
	 * 同步营业部信息到OWS
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @param
	 * @date 2013-1-15 下午5:22:39
	 * @return
	 */
	private void syncSaleDepartmentDataToOws(
			List<SaleDepartmentEntity> entityLst) {
		syncSalesDepartmentService.syncSalesDepartmentDataToOws(entityLst);
	}

	@SuppressWarnings("unchecked")
	private void invalidEntity(String key) {
		((ICache<String, SaleDepartmentEntity>) CacheManager.getInstance()
				.getCache(FossTTLCache.SALEDEPARTMENT_ENTITY_CACHE_UUID))
				.invalid(key);
	}

	// 取缓存中的数据
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SaleDepartmentEntity queryEntityCache(String key) {
		SaleDepartmentEntity result = null;
		try {
			CacheManager cacheManager = CacheManager.getInstance();
			if (cacheManager == null) {
				return null;
			}
			ICache cache = cacheManager
					.getCache(FossTTLCache.SALEDEPARTMENT_ENTITY_CACHE_UUID);
			if (cache == null) {
				return null;
			}
			result = (SaleDepartmentEntity) cache.get(key);
		} catch (Exception t) {
			LOGGER.error("缓存中找不到数据", t);
		}
		return result;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	public void setSaleDepartmentDao(ISaleDepartmentDao saleDepartmentDao) {
		this.saleDepartmentDao = saleDepartmentDao;
	}

	public void setSyncSalesDepartmentService(
			SyncSalesDepartmentService syncSalesDepartmentService) {
		this.syncSalesDepartmentService = syncSalesDepartmentService;
	}

	/**
	 * 根据历史时间和营业部编码查询营业部信息（查询历史营业部信息）
	 * 
	 * 若时间为空，则只根据营业部编码查询营业部信息 否则将根据时间，查询在creatTime和modifyTime时间段的营业部
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentByCode(String code,
			Date billTime) {
		return saleDepartmentDao.querySaleDepartmentByCode(code, billTime);
	}

	/**** 用于同步 ****/
	/**
	 * 
	 * <p>
	 * 用于同步WDGH的派送自提的更新方法
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-6下午2:06:42
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public int updateSaleDepartmentBySync(PickAreaAndDeliveryAreaEntity entity) {
		// 非空验证
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			throw new SalesDepartmentException("转换的派送自提对象为空，或者编码为空");
		}
		// 查询出库中的记录(不走缓存)
		SaleDepartmentEntity salesEntity = saleDepartmentDao
				.querySaleDepartmentByCode(entity.getCode());
		if (null == salesEntity) {
			LOGGER.info("*****要同步的营业部库中不存在 ******");
			throw new SalesDepartmentException("要同步的营业部库中不存在");
		}
		// 若部门不是可达到的部门
		if ((FossConstants.NO.equals(salesEntity.getArrive()))
				|| (StringUtils.isBlank(salesEntity.getArrive()))) {
			LOGGER.info("*****要同步的营业部不是可到达营业部 ******");
			throw new SalesDepartmentException("要同步的营业部不是可到达营业部");
		}
		// 将派送自提对象转换成营业部对象
		salesEntity = convertPickAreaAndDeliveryToSales(salesEntity, entity);
		// 更新营业部
		SaleDepartmentEntity entityResult = this
				.updateSaleDepartment(salesEntity);
		return entityResult == null ? 0 : 1;
	}

	/**
	 * 以下是同步的工具方法
	 */
	/**
	 * <p>
	 * 将派送自提对象转换成营业部对象
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-6下午2:19:43
	 * @param saleDepartmentEntity
	 * @param entity
	 * @return
	 */
	private SaleDepartmentEntity convertPickAreaAndDeliveryToSales(
			SaleDepartmentEntity saleDepartmentEntity,
			PickAreaAndDeliveryAreaEntity entity) {
		// 非空校验，若派送对象为空，
		if (null == entity || StringUtils.isBlank(entity.getCode())) {
			LOGGER.info("*******转换的派送对象为空*******");
			throw new SalesDepartmentException("转换的派送自提对象为空");
		}
		// 部门编码
		saleDepartmentEntity.setCode(entity.getCode());
		if (StringUtils.isNotBlank(entity.getPickupSelf())) {
			// 可自提
			saleDepartmentEntity.setPickupSelf(entity.getPickupSelf());
		}
		if (StringUtils.isNotBlank(entity.getDelivery())) {
			// 可派送
			saleDepartmentEntity.setDelivery(entity.getDelivery());
		}
		if (null != entity.getSingleBillLimitkg()
				&& entity.getSingleBillLimitkg().doubleValue() > 0) {
			// 单件重量上限
			saleDepartmentEntity.setSingleBillLimitkg(entity
					.getSingleBillLimitkg());
		}

		if (null != entity.getSinglePieceLimitkg()
				&& entity.getSinglePieceLimitkg().doubleValue() > 0) {
			// 单票重量上限
			saleDepartmentEntity.setSinglePieceLimitkg(entity
					.getSinglePieceLimitkg());
		}

		if (null != entity.getSingleBillLimitvol()
				&& entity.getSingleBillLimitvol().doubleValue() > 0) {
			// 单件体积上限
			saleDepartmentEntity.setSingleBillLimitvol(entity
					.getSingleBillLimitvol());
		}

		if (null != entity.getSinglePieceLimitvol()
				&& entity.getSinglePieceLimitvol().doubleValue() > 0) {
			// 单票体积上限
			saleDepartmentEntity.setSinglePieceLimitvol(entity
					.getSinglePieceLimitvol());
		}
		if (StringUtils.isNotBlank(entity.getPickupAreaDesc())) {
			// 自提范围描述
			String pickDesc = entity.getPickupAreaDesc();
			// 判断自提
			try {
				if (pickDesc.getBytes(ComnConst.STRING_TYPE_UTF8).length > NumberConstants.PROP_LENG_4000) {
					saleDepartmentEntity.setPickAreaIsExpand(FossConstants.YES);
					// 将前1333字插入派送字段
					saleDepartmentEntity.setPickupAreaDesc(pickDesc.substring(
							NumberConstants.ZERO, NumberConstants.NUMBER_1333));
					// 将超过内容插入扩展表
					String[] pickDescList = changeString(pickDesc);
					SalesDescExpandEntity pickUpEntity = new SalesDescExpandEntity();
					pickUpEntity.setCode(entity.getCode());
					pickUpEntity.setCreateUser(entity.getCreateUser());
					pickUpEntity.setModifyUser(entity.getModifyUser());
					pickUpEntity
							.setDescType(ComnConst.SALES_DEPARTMENT_DESC_EXPAND_TYPE_PICKUP);
					// 添加都与的字 到拓展表中
					salesDescExpandService.addSalesDescExpandByType(
							pickDescList, pickUpEntity);
				} else {
					saleDepartmentEntity.setPickAreaIsExpand(FossConstants.NO);
					saleDepartmentEntity.setPickupAreaDesc(pickDesc);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (StringUtils.isNotBlank(entity.getDeliveryAreaDesc())) {
			// 派送范围描述
			String deliveryDesc = entity.getDeliveryAreaDesc();
			// 判断派送
			try {
				if (deliveryDesc.getBytes(ComnConst.STRING_TYPE_UTF8).length > NumberConstants.PROP_LENG_4000) {
					saleDepartmentEntity
							.setDeliveryAreaIsExpand(FossConstants.YES);
					saleDepartmentEntity.setDeliveryAreaDesc(deliveryDesc
							.substring(NumberConstants.ZERO,
									NumberConstants.NUMBER_1333));
					// 将超过内容插入扩展表
					String[] deliveryDescList = changeString(deliveryDesc);
					SalesDescExpandEntity deliveryEntity = new SalesDescExpandEntity();
					deliveryEntity.setCode(entity.getCode());
					deliveryEntity.setCreateUser(entity.getCreateUser());
					deliveryEntity.setModifyUser(entity.getModifyUser());
					deliveryEntity
							.setDescType(ComnConst.SALES_DEPARTMENT_DESC_EXPAND_TYPE_DELIVERY);
					// 添加多余的字 到拓展表中
					salesDescExpandService.addSalesDescExpandByType(
							deliveryDescList, deliveryEntity);
				} else {
					saleDepartmentEntity
							.setDeliveryAreaIsExpand(FossConstants.NO);
					saleDepartmentEntity.setDeliveryAreaDesc(deliveryDesc);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// 创建用户
		saleDepartmentEntity.setCreateUser(entity.getCreateUser());
		// 修改用户
		saleDepartmentEntity.setModifyUser(entity.getModifyUser());

		return saleDepartmentEntity;
	}

	/**
	 * 
	 * <p>
	 * 将传入的字符串按一定长度转换成数组
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-9-6下午3:32:56
	 * @param pickDesc
	 * @return
	 */
	private String[] changeString(String str) {
		int beginIndex = NumberConstants.NUMBER_1333;
		int avgLen = NumberConstants.NUMBER_1333;
		// 获取字符串的字节长度
		int len = str.length() - beginIndex;
		// 需要分割次数，向下取整
		int count = (int) Math.ceil(Double.valueOf(len)
				/ Double.valueOf(avgLen));
		String[] strList = new String[count];
		for (int n = 0; n < count; n++) {
			int endIndex = beginIndex + (n + 1) * avgLen;
			if (endIndex > str.length()) {
				endIndex = str.length();
			}
			strList[n] = str.substring(beginIndex + n * avgLen, endIndex);

		}
		return strList;
	}

	/**
	 * 根据营业部编码查询营业部信息，自提和派送区域范围不拼接扩展表的
	 * 
	 * auther:wangpeng_078816 date:2014-4-19
	 * 
	 */
	@Override
	public SaleDepartmentEntity querySaleDepartmentInfoByCode(String code) {
		return saleDepartmentDao.querySaleDepartmentByCode(code);
	}

	/**
	 * 
	 * 根据外场的部门编码查询其驻地营业部的List
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-6-5 上午9:59:23
	 * @param transferCenterCode
	 * @return
	 * @see
	 */
	@Override
	public List<SaleDepartmentEntity> queryStationListByTransferCenterCode(
			String transferCenterCode) {
		// 声明一个对象做查询条件
		SaleDepartmentEntity condition = new SaleDepartmentEntity();
		// 设置外场部门编码
		condition.setTransferCenter(transferCenterCode);
		// 设置驻地营业部
		condition.setStation(FossConstants.YES);
		// 设置active='Y'
		condition.setActive(FossConstants.ACTIVE);
		List<SaleDepartmentEntity> list = querySimpleSaleDepartmentExactByEntity(
				condition, 0, Integer.MAX_VALUE);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list;
	}

	/**
	 * 提供营业部Code 查询时汽运到达的驻地外场
	 * 
	 * @author 130566-ZengJunFan
	 * @param code
	 * @return
	 */
	@Override
	public String queryTransferCenterCodeByStationCode(
			String salesDepartmentCode) {
		if (StringUtils.isEmpty(salesDepartmentCode)) {
			throw new SalesDepartmentException("code 为空");
		}
		// 查询营业部实体
		SaleDepartmentEntity sale = querySaleDepartmentByCode(salesDepartmentCode);
		// 非空校验
		if (null != sale) {
			// 是驻地部门 切是汽运到达的
			if (StringUtils.equals(FossConstants.YES, sale.getStation())
					&& StringUtils.equals(FossConstants.YES,
							sale.getTruckArrive())) {
				return sale.getTransferCenter();
			}
		}
		return null;
	}

}