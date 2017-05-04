package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ILclDeliveryToCashManagementUnloadingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LoadAndUnloadEfficiencyVehicleException;

public class LclDeliveryToCashManagementUnloadingService implements
		ILclDeliveryToCashManagementUnloadingService {
	/**
	 * 
	 * <p>规定卸出时间管理   新增</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:13:04
	 * @param entity
	 * @return
	 * @throws LoadAndUnloadEfficiencyVehicleException 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#addlclDeliveryToCashManagementUnloading(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
	 */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity addlclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity)
			throws LoadAndUnloadEfficiencyVehicleException {
		if (entity == null) {
			return null;
		}
		entity = this.attachOrg(entity);
		List<LclDeliveryToCashManagementUnloadingEntity> entityCountion = this
				.queryLclDeliveryToCashManagementUnloadingEntitytByCode(
						entity.getStartOrgCode(), entity.getReachOrgCode());
		if (entityCountion != null) {
			for (LclDeliveryToCashManagementUnloadingEntity entity1 : entityCountion) {
				if (entity.getVehicleNumber()
						.equals(entity1.getVehicleNumber())) {
					throw new LoadAndUnloadEfficiencyVehicleException("",
							"同出发部门到达部门班次重复");
				}
			}
		}
		return lclDeliveryToCashManagementUnloadingDao
				.addlclDeliveryToCashManagementUnloading(entity);
	}
   /**
    * 
    * <p>规定卸出时间管理   删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午4:14:05
    * @param entity
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#deletelclDeliveryToCashManagementUnloading(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
    */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity deletelclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity) {
		// 验证合法性
		if (entity == null || StringUtils.isBlank(entity.getId())) {
			return null;
		}
		return lclDeliveryToCashManagementUnloadingDao
				.deletelclDeliveryToCashManagementUnloading(entity);
	}
   /**
    * 
    * <p>规定卸出时间管理   批量删除</p> 
    * @author 273311 
    * @date 2016-8-23 下午4:14:25
    * @param ids
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#deletelclDeliveryToCashManagementUnloadingMore(java.lang.String[])
    */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity deletelclDeliveryToCashManagementUnloadingMore(
			String[] ids) {
		if (ids == null || ids.length == 0) {
			return null;
		}
		return lclDeliveryToCashManagementUnloadingDao
				.deletelclDeliveryToCashManagementUnloadingMore(ids);
	}
   /**
    * 
    * <p>规定卸出时间管理  更新</p> 
    * @author 273311 
    * @date 2016-8-23 下午4:14:45
    * @param entity
    * @return 
    * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#updatelclDeliveryToCashManagementUnloading(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
    */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity updatelclDeliveryToCashManagementUnloading(
			LclDeliveryToCashManagementUnloadingEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getStartOrgCode())
				|| StringUtils.isBlank(entity.getReachOrgCode())) {
			return null;
		}
		// 验证数据是否存在
		LclDeliveryToCashManagementUnloadingEntity updataEntity = this
				.queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(entity
						.getId());
		if (updataEntity != null) {
			lclDeliveryToCashManagementUnloadingDao
					.deletelclDeliveryToCashManagementUnloading(updataEntity);
		}
		return this.addlclDeliveryToCashManagementUnloading(entity);
		// return
		// lclDeliveryToCashManagementUnloadingDao.updatelclDeliveryToCashManagementUnloading(entity);
	}
    /**
     * 
     * <p>startOrgCode, reachOrgCode 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:15:34
     * @param startOrgCode
     * @param reachOrgCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#queryLclDeliveryToCashManagementUnloadingEntitytByCode(java.lang.String, java.lang.String)
     */
	@Override
	public List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntitytByCode(
			String startOrgCode, String reachOrgCode) {
		if (StringUtils.isBlank(startOrgCode)
				|| StringUtils.isBlank(reachOrgCode)) {
			return null;
		}
		List<LclDeliveryToCashManagementUnloadingEntity> entityResult = lclDeliveryToCashManagementUnloadingDao
				.queryLclDeliveryToCashManagementUnloadingEntitytByCode(
						startOrgCode, reachOrgCode);
		return entityResult;
	}
    /**
     * 
     * <p>ID 精确查询</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:16:35
     * @param id
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(java.lang.String)
     */
	@Override
	public LclDeliveryToCashManagementUnloadingEntity queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(
			String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		LclDeliveryToCashManagementUnloadingEntity entityResult = lclDeliveryToCashManagementUnloadingDao
				.queryLclDeliveryToCashManagementUnloadingEntitytByIdCode(id);
		return entityResult;
	}
    /**
     * 
     * <p>规定卸出时间管理  查询 分页</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:17:06
     * @param entity
     * @param start
     * @param limit
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity, int, int)
     */
	@Override
	public List<LclDeliveryToCashManagementUnloadingEntity> queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(
			LclDeliveryToCashManagementUnloadingEntity entity, int start,
			int limit) {
		List<LclDeliveryToCashManagementUnloadingEntity> entityResults = lclDeliveryToCashManagementUnloadingDao
				.queryLclDeliveryToCashManagementUnloadingEntityExactByEntity(
						entity, start, limit);
		entityResults = this.attachOrg(entityResults);
		return entityResults;
	}
    /**
     * 
     * <p>规定卸出时间管理  查询条数</p> 
     * @author 273311 
     * @date 2016-8-23 下午4:17:55
     * @param entity
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.ILclDeliveryToCashManagementUnloadingService#queryLclDeliveryToCashManagementUnloadingExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LclDeliveryToCashManagementUnloadingEntity)
     */
	@Override
	public long queryLclDeliveryToCashManagementUnloadingExactByEntityCount(
			LclDeliveryToCashManagementUnloadingEntity entity) {
		return lclDeliveryToCashManagementUnloadingDao
				.queryLclDeliveryToCashManagementUnloadingExactByEntityCount(entity);
	}

	/**
	 * 
	 * <p>给部门加上名称</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:18:27
	 * @param entity
	 * @return
	 * @see
	 */
	private LclDeliveryToCashManagementUnloadingEntity attachOrg(
			LclDeliveryToCashManagementUnloadingEntity entity) {
		if (entity == null || StringUtils.isBlank(entity.getStartOrgCode())
				|| StringUtils.isBlank(entity.getReachOrgCode())) {
			return entity;
		}
		entity.setStartOrgName(orgAdministrativeInfoService
				.queryOrgAdministrativeInfoNameByCode(entity.getStartOrgCode()));
		entity.setReachOrgName(orgAdministrativeInfoService
				.queryOrgAdministrativeInfoNameByCode(entity.getReachOrgCode()));
		return entity;
	}

	/**
	 * 
	 * <p> 批量给部门加上名称</p> 
	 * @author 273311 
	 * @date 2016-8-23 下午4:18:45
	 * @param entitys
	 * @return
	 * @see
	 */
	private List<LclDeliveryToCashManagementUnloadingEntity> attachOrg(
			List<LclDeliveryToCashManagementUnloadingEntity> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return entitys;
		}
		for (LclDeliveryToCashManagementUnloadingEntity entity : entitys) {
			this.attachOrg(entity);
		}
		return entitys;
	}
    /**
     * get set 方法
     */
	private ILclDeliveryToCashManagementUnloadingDao lclDeliveryToCashManagementUnloadingDao;
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setLclDeliveryToCashManagementUnloadingDao(
			ILclDeliveryToCashManagementUnloadingDao lclDeliveryToCashManagementUnloadingDao) {
		this.lclDeliveryToCashManagementUnloadingDao = lclDeliveryToCashManagementUnloadingDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

}
