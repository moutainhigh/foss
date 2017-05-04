package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ICarloadLinePricePlanDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.ICarloadLinePricePlanService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanDetailEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.CarloadLinePricePlanEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CarloadLinePricePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.define.WaybillConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.google.inject.Inject;
/**
 * 整车线路价格方案服务
 * @author hehaisu
 * @date 2015-1-23 下午1:59:35
 */
public class CarloadLinePricePlanService implements ICarloadLinePricePlanService  {
	
    @Inject
    ICarloadLinePricePlanDao carloadLinePricePlanDao;
    


	public ICarloadLinePricePlanDao getCarloadLinePricePlanDao() {
		return carloadLinePricePlanDao;
	}


	public void setCarloadLinePricePlanDao(
			ICarloadLinePricePlanDao carloadLinePricePlanDao) {
		this.carloadLinePricePlanDao = carloadLinePricePlanDao;
	}


	/**
	 * 分页查询方案
	 * 
	 * @param carloadPricePlanDto
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<CarloadLinePricePlanEntity> queryCarloadLinePricePlanByEntity(
			CarloadLinePricePlanEntity carloadLinePriceEntity, int start,
			int limit) {
		if (carloadLinePriceEntity == null) {
			return null;
		}
		return carloadLinePricePlanDao
				.queryCarloadLinePricePlanByEntity(carloadLinePriceEntity, start, limit);
	}


	/**
	 * 查询方案总条数
	 * @param carloadLinePricePlanDto
	 * @return
	 */
	public Long queryCarloadLinePricePlanCountByEntity(
			CarloadLinePricePlanEntity carloadLinePriceEntity) {
		if (carloadLinePriceEntity == null) {
			return 0l;
		}
		return carloadLinePricePlanDao.queryCarloadLinePricePlanCountByEntity(carloadLinePriceEntity);
	}


	/**
	 * 查询方案明细
	 * @param carloadLinePriceDetailEntity
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<CarloadLinePricePlanDetailEntity> queryCarloadLinePricePlanDetailByEntity(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity,
			int start, int limit) {
		if (carloadLinePriceDetailEntity == null) {
			return null;
		}
		return carloadLinePricePlanDao
				.queryCarloadLinePricePlanDetailByEntity(carloadLinePriceDetailEntity, start, limit);
	}


	/**
	 * 查询方案明细总条数
	 * @param carloadLinePriceDetailEntity
	 * @return
	 */
	public Long queryCarloadLinePricePlanDetailCountByEntity(
			CarloadLinePricePlanDetailEntity carloadLinePriceDetailEntity) {
		if (carloadLinePriceDetailEntity == null) {
			return 0l;
		}
		return carloadLinePricePlanDao
				.queryCarloadLinePricePlanDetailCountByEntity(carloadLinePriceDetailEntity);
	}


	/**
	 * 批量添加整车线路价格方案及明细
	 * @param carloadLinePricePlanDtos
	 * @return
	 */
	@Transactional
	public int batchInsert(
			List<CarloadLinePricePlanDto> carloadLinePricePlanDtos) {
		
		int count = 0;
		if (CollectionUtils.isEmpty(carloadLinePricePlanDtos)) {
			return 0;
		}
		
		for (CarloadLinePricePlanDto dto : carloadLinePricePlanDtos) {
			CarloadLinePricePlanEntity carloadLinePriceEntity = null;
			
			CarloadLinePricePlanEntity queryCarloadLinePriceEntity = new CarloadLinePricePlanEntity();
			queryCarloadLinePriceEntity.setDepartureCityCode(dto.getDepartureCityCode());
			queryCarloadLinePriceEntity.setArrivalCityCode(dto.getArrivalCityCode());
			queryCarloadLinePriceEntity.setActive(WaybillConstants.YES);
			List<CarloadLinePricePlanEntity> carloadLinePriceEntites = carloadLinePricePlanDao
					.queryCarloadLinePricePlanByEntity(queryCarloadLinePriceEntity, 0, 1);
			
			//如果数据存在，则插入，反之则不插入
			if (CollectionUtils.isEmpty(carloadLinePriceEntites)) {
				carloadLinePriceEntity = getCarloadLinePricePlanEntity(dto);
				carloadLinePricePlanDao.insertCarloadLinePricePlan(carloadLinePriceEntity);
			} else {
				carloadLinePriceEntity = carloadLinePriceEntites.get(0);
			}
			
			//设置dto的id为carloadLinePriceEntity的主键,获取明细实体时setCarloadLinePriceId
			dto.setId(carloadLinePriceEntity.getId());
			
			CarloadLinePricePlanDetailEntity queryCarloadLinePricePlanDetailEntity = new CarloadLinePricePlanDetailEntity();
			queryCarloadLinePricePlanDetailEntity.setActive(WaybillConstants.YES);
			queryCarloadLinePricePlanDetailEntity.setCarloadLinePriceId(dto.getId());
			queryCarloadLinePricePlanDetailEntity.setInvoiceType(dto.getInvoiceType());
			queryCarloadLinePricePlanDetailEntity.setUpLimit(dto.getUpLimit());
			queryCarloadLinePricePlanDetailEntity.setDownLimit(dto.getDownLimit());
			queryCarloadLinePricePlanDetailEntity.setChargeStandard(dto.getChargeStandard());
			queryCarloadLinePricePlanDetailEntity.setType(dto.getType());
			
			
			long l = carloadLinePricePlanDao.queryCarloadLinePricePlanDetailCountByEntity(queryCarloadLinePricePlanDetailEntity);
			//如果不存在则插入
			if (l <= 0l) {
				CarloadLinePricePlanDetailEntity carloadLinePricePlanDetailEntity = getCarloadLinePricePlanDetailEntity(dto);
				carloadLinePricePlanDao.insertCarloadLinePricePlanDetail(carloadLinePricePlanDetailEntity);
				count++;
			} else {
				throw new BusinessException("第"+(count+2)+"行数据已存在,请检查"); 
			}
			
		}
		
		return count;
	}
	
	private CarloadLinePricePlanEntity getCarloadLinePricePlanEntity(CarloadLinePricePlanDto dto) {
		CarloadLinePricePlanEntity entity = new CarloadLinePricePlanEntity();
		if (StringUtil.isBlank(dto.getDepartureCityName())) {
			throw new BusinessException("始发城市不能为空");
		} else {
			entity.setDepartureCityName(dto.getDepartureCityName());
		}
		
		if (StringUtil.isBlank(dto.getDepartureCityCode())) {
			throw new BusinessException("始发城市不能为空");
		} else {
			entity.setDepartureCityCode(dto.getDepartureCityCode());
		}
		
		if (StringUtil.isBlank(dto.getArrivalCityName())) {
			throw new BusinessException("到达城市不能为空");
		} else {
			entity.setArrivalCityName(dto.getArrivalCityName());
		}
		
		if (StringUtil.isBlank(dto.getArrivalCityCode())) {
			throw new BusinessException("到达城市不能为空");
		} else {
			entity.setArrivalCityCode(dto.getArrivalCityCode());
		}
		
		UserEntity currentUser = FossUserContext.getCurrentUser();
		entity.setCreateUser(currentUser.getEmployee().getEmpCode());
		entity.setCreateUserName(currentUser.getEmployee().getEmpName());
		entity.setCreateDate(new Date());
		entity.setActive(FossConstants.YES);
		entity.setRemark(dto.getRemark());
		entity.setId(UUIDUtils.getUUID());
		
		return entity;
	}
	
	private CarloadLinePricePlanDetailEntity getCarloadLinePricePlanDetailEntity(CarloadLinePricePlanDto dto) {
		CarloadLinePricePlanDetailEntity entity = new CarloadLinePricePlanDetailEntity();
		if (StringUtil.isBlank(dto.getInvoiceType())) {
			throw new BusinessException("发票标记不能为空");
		} else {
			entity.setInvoiceType(dto.getInvoiceType());
		}
		
		if (StringUtil.isBlank(dto.getType())) {
			throw new BusinessException("类型不能为空");
		} else if (!"WEIGHT".equals(dto.getType()) && !"VOLUME".equals(dto.getType())) {
			throw new BusinessException("类型值不符合");
		} else {
			entity.setType(dto.getType());
		}
		
		if (dto.getUpLimit().compareTo(BigDecimal.ZERO) == -1) {
			throw new BusinessException("上限不能为负值");
		} else {
			entity.setUpLimit(dto.getUpLimit());
		}
		
		if (dto.getDownLimit().compareTo(BigDecimal.ZERO) == -1) {
			throw new BusinessException("下限不能为负值");
		} else {
			entity.setDownLimit(dto.getDownLimit());
		}
		
		if (dto.getChargeStandard().compareTo(BigDecimal.ZERO) <= 0 ) {
			throw new BusinessException("收费标准不能为负值");
		} else {
			entity.setChargeStandard(dto.getChargeStandard());
		}
		
		UserEntity currentUser = FossUserContext.getCurrentUser();
		entity.setCreateUser(currentUser.getEmployee().getEmpCode());
		entity.setCreateDate(new Date());
		entity.setActive(FossConstants.YES);
		entity.setRemark(dto.getRemark());
		entity.setId(UUIDUtils.getUUID());
		entity.setCarloadLinePriceId(dto.getId());
		
		return entity;
	}



}
