package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressSortStationMappingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressSortStationMappingService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressSortStationMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressSortStationMappingException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
/**
 * 快递分拣目的站映射接口实现类
 * @author 130566
 *
 */
public class ExpressSortStationMappingService implements
		IExpressSortStationMappingService {
	//外场service
	private IOutfieldService outfieldService;
	
	private IExpressSortStationMappingDao expressSortStationMappingDao;
	
	public void setExpressSortStationMappingDao(
			IExpressSortStationMappingDao expressSortStationMappingDao) {
		this.expressSortStationMappingDao = expressSortStationMappingDao;
	}
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}
	/**
	 * 新增映射关系
	 * @param entity
	 * @return
	 */
	@Override
	public int addExpSortStationMappingEntity(
			ExpressSortStationMappingEntity entity) {
		if(null ==entity){
			throw new ExpressSortStationMappingException("添加的实体数据为空");
		}
		//查询库中是否存在这条数据
		List<ExpressSortStationMappingEntity> resultList =queryListByCondition(entity);
		if(CollectionUtils.isNotEmpty(resultList)){
			throw new ExpressSortStationMappingException("新增的信息，系统已经存在");
		}
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(UUIDUtils.getUUID());
		return expressSortStationMappingDao.addExpressSortStationMappingEntity(entity);
	}
	/**
	 * 作废映射关系
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteExpSortStationMappingEntity(
			ExpressSortStationMappingEntity entity) {
		if(null==entity || StringUtils.isEmpty(entity.getVirtualCode())){
			throw new ExpressSortStationMappingException("传进来的参数为空");
		}
		
		return expressSortStationMappingDao.deleteExpSortStationMappingByVirtualCode(entity);
	}
	/**
	 * 修改映射关系
	 */
	@Override
	public int updateExpSortStationMappingEntity(
			ExpressSortStationMappingEntity entity) {
		if(null==entity || StringUtils.isEmpty(entity.getVirtualCode())){
			throw new ExpressSortStationMappingException("传进来的参数为空");
		}
		//查询库中是否存在这条数据
		List<ExpressSortStationMappingEntity> resultList =queryListByCondition(entity);
		if(CollectionUtils.isNotEmpty(resultList)){
			for (ExpressSortStationMappingEntity result : resultList) {
				if(!StringUtils.equals(result.getVirtualCode(), entity.getVirtualCode())){
					throw new ExpressSortStationMappingException("修改的信息，系统已经存在");
				}
			}
		}
		return expressSortStationMappingDao.updateExpSortStationMapping(entity);
	}
	/**
	 * 分页查询映射关系
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<ExpressSortStationMappingEntity> queryExpressSortStationMappingEntities(
			ExpressSortStationMappingEntity entity, int start, int limit) {
		if(null ==entity){
			entity =new ExpressSortStationMappingEntity();
		}
		return expressSortStationMappingDao.queryExpSortStationMappingList(entity, start, limit);
	}
	/**
	 * 查询总数
	 */
	@Override
	public long queryMappingCount(ExpressSortStationMappingEntity entity) {
		if(null ==entity){
			entity =new ExpressSortStationMappingEntity();
		}
		return expressSortStationMappingDao.queryCount(entity);
	}
	/**
	 * 多条件查询信息
	 * @param entity
	 * @return
	 */
	@Override
	public List<ExpressSortStationMappingEntity> queryListByCondition(
			ExpressSortStationMappingEntity entity) {
		//非空校验
		if(entity ==null){
			return null;
		}
		return expressSortStationMappingDao.queryMappingListByCondition(entity);
	}
	/**
	 * 删除多条信息
	 * @param entityList
	 * @return
	 */
	@Override
	public int deleteMoreMappingByvirtualCode(
			List<ExpressSortStationMappingEntity> entityList) {
		if(CollectionUtils.isEmpty(entityList)){
			throw new ExpressSortStationMappingException("删除参数集合为空");
		}
		int count=0;
		for (ExpressSortStationMappingEntity expressSortStationMappingEntity : entityList) {
			count =+ this.deleteExpSortStationMappingEntity(expressSortStationMappingEntity);
		}
		return count ==entityList.size()?NumberConstants.NUMBER_1:NumberConstants.ZERO;
	}
	/**
	 * 根据条件查询库区名称
	 * @param destOrgCode
	 * @param TransferCenterCode
	 * @return
	 */
	@Override
	public String queryGoodSAreaByCondition(String destOrgCode,
			String transferCenterCode) {
		//非空校验
		if(StringUtils.isEmpty(destOrgCode)||StringUtils.isEmpty(transferCenterCode)){
			throw new ExpressSortStationMappingException("查询参数不能为空！");
		}
		OutfieldEntity outfieldEntity = outfieldService.queryOutfieldByOrgCodeNoCache(transferCenterCode);
		//外场信息不能为空
		if(null!= outfieldEntity && StringUtils.isNotBlank(outfieldEntity.getCode())){
			ExpressSortStationMappingEntity expressSortStationMappingEntity = new ExpressSortStationMappingEntity();
			expressSortStationMappingEntity.setOutfieldCode(outfieldEntity.getCode());
			expressSortStationMappingEntity.setDestinationCode(destOrgCode);
			//查询映射信息
			List<ExpressSortStationMappingEntity> entities =this.queryListByCondition(expressSortStationMappingEntity);
			if(CollectionUtils.isNotEmpty(entities)){
				return entities.get(0).getWarehouseAreaName();
			}
		}
		return null;
	}

}
