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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.FlagConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ISalesBillingGroupDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncInfosToCrmService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SalesBillingGroupEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.MapDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesBillingGroupDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesBillingGroupSyncDto;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;

/**
 * 营业部和集中开单组关系Service类
 * 
 * @author foss-zhujunyong
 * @date Apr 26, 2013 4:20:31 PM
 * @version 1.0
 */
public class SalesBillingGroupService implements ISalesBillingGroupService {

	/**
	 * 日志类
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SalesBillingGroupService.class);

	/**
	 * 同步foss到crm Service
	 */
	private ISyncInfosToCrmService syncInfosToCrmService;
	@Inject
	private ISalesBillingGroupDao salesBillingGroupDao;

	public void setSyncInfosToCrmService(
			ISyncInfosToCrmService syncInfosToCrmService) {
		this.syncInfosToCrmService = syncInfosToCrmService;
	}

	/**
	 * @param salesBillingGroupDao
	 *            the salesBillingGroupDao to set
	 */
	public void setSalesBillingGroupDao(
			ISalesBillingGroupDao salesBillingGroupDao) {
		this.salesBillingGroupDao = salesBillingGroupDao;
	}

	/**
	 * <p>
	 * 新增,更新或作废营业部和集中开单组关系
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date Apr 26, 2013 5:50:51 PM
	 * @param dto
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService#mergeSalesBillingGroupDto(com.deppon.foss.module.base.baseinfo.api.shared.dto.SalesBillingGroupDto)
	 */
	@Override
	public void mergeSalesBillingGroupDto(SalesBillingGroupDto dto) {
		List<SalesBillingGroupSyncDto> syncList = mergeSalesBillingGroupDtoInTX(dto);
		for (SalesBillingGroupSyncDto sync : syncList) {
			syncToCrm(sync.getSaleDeptCode(), sync.getBillingGroupCode(),
					sync.getOperateFlag());
		}
	}

	@Transactional
	private List<SalesBillingGroupSyncDto> mergeSalesBillingGroupDtoInTX(
			SalesBillingGroupDto dto) {
		List<SalesBillingGroupSyncDto> syncList = new ArrayList<SalesBillingGroupSyncDto>();
		// 检查数据
		if (dto == null || BooleanUtils.isFalse(dto.validate())) {
			return syncList;
		}
		// 数据库中的原始集中开单组列表
		List<MapDto> billingGroupList1 = queryBillingGroupListBySalesCode(dto
				.getSalesDeptCode());
		// 界面中新的集中开单组列表
		List<MapDto> billingGroupList2 = dto.getBillingGroupList();

		// 如果在数据库中有而更新的数据中没有，说明要删除
		for (MapDto c : billingGroupList1) {
			if (!billingGroupList2.contains(c)) {
				SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
				entity.setModifyUser(dto.getModifyUserCode());
				entity.setBillingGroupCode(c.getCode());
				entity.setSalesDeptCode(dto.getSalesDeptCode());

				SalesBillingGroupSyncDto syncDto = new SalesBillingGroupSyncDto();
				syncDto.setSaleDeptCode(entity.getSalesDeptCode());
				syncDto.setBillingGroupCode(entity.getBillingGroupCode());
				syncDto.setOperateFlag(FlagConstants.OPERATE_FLAG_DELETE);
				syncList.add(syncDto);

				// syncToCrm(entity.getSalesDeptCode(),
				// entity.getBillingGroupCode(),
				// FlagConstants.OPERATE_FLAG_DELETE);
				salesBillingGroupDao
						.deleteSalesBillingGroupBySalesBillingGroupCode(entity);
			}
		}

		// 如果在更新的数据中有而数据库中没有，说明要新增
		for (MapDto c : billingGroupList2) {
			if (!billingGroupList1.contains(c)) {
				SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
				entity.setCreateUser(dto.getModifyUserCode());
				entity.setBillingGroupCode(c.getCode());
				entity.setBillingGroupName(c.getName());
				entity.setSalesDeptCode(dto.getSalesDeptCode());
				entity.setSalesDeptName(dto.getSalesDeptName());

				SalesBillingGroupSyncDto syncDto = new SalesBillingGroupSyncDto();
				syncDto.setSaleDeptCode(entity.getSalesDeptCode());
				syncDto.setBillingGroupCode(entity.getBillingGroupCode());
				syncDto.setOperateFlag(FlagConstants.OPERATE_FLAG_INSERT);
				syncList.add(syncDto);

				// syncToCrm(entity.getSalesDeptCode(),
				// entity.getBillingGroupCode(),
				// FlagConstants.OPERATE_FLAG_INSERT);
				salesBillingGroupDao.addSalesBillingGroup(entity);
			}
		}
		return syncList;
	}

	@Transactional
	@Override
	public int deleteSalesBillingGroupDtoBySalesCode(String salesCode,
			String modifyUser) {
		if (StringUtils.isBlank(salesCode)) {
			return FossConstants.FAILURE;
		}

		SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
		entity.setSalesDeptCode(salesCode);
		entity.setModifyUser(modifyUser);
		return salesBillingGroupDao.deleteSalesBillingGroupBySalesCode(entity);
	}

	@Transactional
	@Override
	public int deleteSalesBillingGroupDtoByBillingGroupCode(
			String billingGroupCode, String modifyUser) {
		if (StringUtils.isBlank(billingGroupCode)) {
			return FossConstants.FAILURE;
		}

		SalesBillingGroupEntity entity = new SalesBillingGroupEntity();
		entity.setBillingGroupCode(billingGroupCode);
		entity.setModifyUser(modifyUser);
		return salesBillingGroupDao
				.deleteSalesBillingGroupByBillingGroupCode(entity);
	}

	@Override
	public List<MapDto> querySalesListByBillingGroupCode(String billingGroupCode) {
		List<MapDto> result = new ArrayList<MapDto>();
		if (StringUtils.isBlank(billingGroupCode)) {
			return result;
		}

		List<SalesBillingGroupEntity> list = salesBillingGroupDao
				.querySalesListByBillingGroupCode(billingGroupCode);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		for (SalesBillingGroupEntity entity : list) {
			if (entity == null
					|| StringUtils.isBlank(entity.getSalesDeptCode())) {
				continue;
			}
			MapDto dto = new MapDto();
			dto.setCode(entity.getSalesDeptCode());
			dto.setName(entity.getSalesDeptName());
			result.add(dto);
		}
		return result;
	}

	@Override
	public List<MapDto> queryBillingGroupListBySalesCode(String salesCode) {
		List<MapDto> result = new ArrayList<MapDto>();
		if (StringUtils.isBlank(salesCode)) {
			return result;
		}

		List<SalesBillingGroupEntity> list = salesBillingGroupDao
				.queryBillingGroupListBySalesCode(salesCode);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		for (SalesBillingGroupEntity entity : list) {
			if (entity == null
					|| StringUtils.isBlank(entity.getBillingGroupCode())) {
				continue;
			}
			MapDto dto = new MapDto();
			dto.setCode(entity.getBillingGroupCode());
			dto.setName(entity.getBillingGroupName());
			result.add(dto);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * 向CRM同步营业部和集中开单组关系信息
	 * </p>
	 * 
	 * @author foss-zhujunyong
	 * @date May 3, 2013 11:29:22 AM
	 * @param saleCode
	 * @param billingGroupCode
	 * @param operateType
	 *            操作类型：只有新增（insert）、删除（delete）两种状态
	 * @return
	 * @see
	 */
	private boolean syncToCrm(String saleCode, String billingGroupCode,
			String operateType) {
		if (StringUtils.isBlank(saleCode)
				|| StringUtils.isBlank(billingGroupCode)
				|| StringUtils.isBlank(operateType)) {
			return false;
		}

		boolean result = true;
		try {
			syncInfosToCrmService.sendBillingPermtInfos(saleCode,
					billingGroupCode, operateType);
		} catch (Exception e) {
			LOGGER.error("发送 营业部信息 到CRM出错", e);
			result = false;
		}
		return result;
	}

	/**
	 * 
	 * 根据营业部部门编码查询相关的集中开单组列表
	 * 
	 * @author foss-zhangxiaohui
	 * 
	 * @date May 16, 2013 11:19:01 AM
	 * @param salesCode
	 *            营业部部门编码
	 * @return
	 * @see
	 */
	public List<SalesBillingGroupEntity> queryBillingGroupListBySaleDepCode(
			String salesCode) {
		// 打印日志
		LOGGER.info("Enter Service queryBillingGroupListBySaleDepCode...");
		// 非空验证
		if (StringUtils.isEmpty(salesCode)) {
			// 打印Log
			LOGGER.info("queryBillingGroupListBySaleDepCode传入的参数为空");
			// 返回空值
			return null;
		}
		// 执行查询
		List<SalesBillingGroupEntity> list = salesBillingGroupDao
				.queryBillingGroupListBySalesCode(salesCode);
		// 打印日志
		LOGGER.info("Exit Service queryBillingGroupListBySaleDepCode...");
		// 非空验证
		if (CollectionUtils.isNotEmpty(list)) {
			// 返回查询结果
			return list;
		}
		// 默认返回空值
		return null;
	}

	/**
	 * 
	 * <p>
	 * 根据集中开单组部门编码查询相关的集中开单组列表
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-7-24下午2:53:52
	 * @param billingGroupCode
	 * @return
	 */
	@Override
	public List<SalesBillingGroupEntity> queryBillingGroupListByBillingGroupCode(
			String billingGroupCode) {
		// 非空判断
		if (StringUtils.isEmpty(billingGroupCode)) {
			// 打印Log
			LOGGER.info("queryBillingGroupListByBillingGroupCode传入的参数为空");
			return null;
		}
		// 执行查询
		List<SalesBillingGroupEntity> entities = salesBillingGroupDao
				.querySalesListByBillingGroupCode(billingGroupCode);
		// 非空判断
		if (CollectionUtils.isNotEmpty(entities)) {
			return entities;
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 批量插入营业部集中开单组
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-7-25下午6:04:44
	 * @param entities
	 * @return
	 */
	@Override
	public List<SalesBillingGroupEntity> addSalesBillingGroupEntityMore(
			List<SalesBillingGroupEntity> entities) {
		// 非空判断
		if (CollectionUtils.isEmpty(entities)) {
			LOGGER.info("addSalesBillingGroupEntityMore传入的参数为空");
			return null;
		}
		for (SalesBillingGroupEntity salesBillingGroupEntity : entities) {
			// 判断数据是否已经存在
			SalesBillingGroupEntity entity = salesBillingGroupDao
					.querySalesListByBillingGroupCodeAndSalesCode(
							salesBillingGroupEntity.getBillingGroupCode(),
							salesBillingGroupEntity.getSalesDeptCode());
			if (entity == null) {
				// 插入数据
				salesBillingGroupDao
						.addSalesBillingGroup(salesBillingGroupEntity);
				// 同步给crm 营业部和集中开单组的关系
				syncToCrm(salesBillingGroupEntity.getSalesDeptCode(),
						salesBillingGroupEntity.getBillingGroupCode(),
						FlagConstants.OPERATE_FLAG_INSERT);
			}
		}
		return entities;
	}

	/**
	 * 
	 * <p>
	 * 批量作废营业部集中开单组
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-7-25下午6:19:34
	 * @param entities
	 * @return
	 */
	@Override
	public List<SalesBillingGroupEntity> deleteSalesBillingGroupEntities(
			List<SalesBillingGroupEntity> entities) {
		// 非空判断
		if (CollectionUtils.isEmpty(entities)) {
			LOGGER.info("addSalesBillingGroupEntityMore传入的参数为空");
			return null;
		}
		for (SalesBillingGroupEntity salesBillingGroupEntity : entities) {
			// 废除数据
			salesBillingGroupDao
					.deleteSalesBillingGroupByVirtualCode(salesBillingGroupEntity);
			// 同步给crm 营业部和集中开单组的关系
			syncToCrm(salesBillingGroupEntity.getSalesDeptCode(),
					salesBillingGroupEntity.getBillingGroupCode(),
					FlagConstants.OPERATE_FLAG_DELETE);
		}
		return entities;
	}

	/**
	 * 
	 * <p>
	 * 根据billingGroupCode来作废
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-7-26上午9:33:08
	 * @param billingGroupEntity
	 * @return
	 */
	@Override
	public SalesBillingGroupEntity deleteSalesBillingGroupByBillingGroupCode(
			SalesBillingGroupEntity billingGroupEntity) {
		// 非空校验
		if (null == billingGroupEntity
				|| StringUtils
						.isBlank(billingGroupEntity.getBillingGroupCode())) {
			LOGGER.info("传入的参数billingGroupCode为空");
			return null;
		}
		int result = salesBillingGroupDao
				.deleteSalesBillingGroupByBillingGroupCode(billingGroupEntity);
		return result > NumberConstants.ZERO ? billingGroupEntity : null;
	}

	/**
	 * 
	 * <p>
	 * 根据salesCode来作废
	 * </p>
	 * 
	 * @author 130566-zengJunfan
	 * @date 2013-7-26上午9:33:52
	 * @param billingGroupEntity
	 * @return
	 */
	@Override
	public SalesBillingGroupEntity deleteSalesBillingGroupBySalesCode(
			SalesBillingGroupEntity billingGroupEntity) {
		// 非空校验
		if (null == billingGroupEntity
				|| StringUtils.isBlank(billingGroupEntity.getSalesDeptCode())) {
			LOGGER.info("传入的参数billingGroupCode为空");
			return null;
		}
		int result = salesBillingGroupDao
				.deleteSalesBillingGroupByBillingGroupCode(billingGroupEntity);
		return result > NumberConstants.ZERO ? billingGroupEntity : null;
	}

}
