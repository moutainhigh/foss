package com.deppon.foss.module.pickup.predeliver.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.tools.ant.util.DateUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IRegionalVehicleDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.RegionVehicleEntity;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IPartitionViewInfoDao;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionViewInfoService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IWaybilldetailNewService;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PartitionViewInfoDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo;
import com.deppon.foss.module.transfer.scheduling.api.server.service.ITruckSchedulingTaskService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.SimpleTruckScheduleDto;

/** 
 * @ClassName: PartitionViewInfoService 
 * @Description: 分区查看Service 实现 
 * @author 237982-foss-fangwenjun 
 * @date 2015-6-19 上午10:00:38 
 *  
 */
public class PartitionViewInfoService implements IPartitionViewInfoService {
	
	private IPartitionViewInfoDao partitionViewInfoDao;
	
	/**
	 * 定人定区Dao
	 */
	private IRegionalVehicleDao regionalVehicleDao;
	
	/**
	 * 排班任务Service
	 */
	private ITruckSchedulingTaskService truckSchedulingTaskService;
	
	/**
	 * 创建派送单(新) ---运单明细
	 */
	private IWaybilldetailNewService waybilldetailNewService;

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionViewInfoService#selectPartitionByBigZone(com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo, int, int)
	 */
	@Override
	public List<PartitionViewInfoDto> selectPartitionByBigZone(PartitionViewInfoVo partitionViewInfoVo, int start,
			int limit) {
		if (partitionViewInfoVo == null) {
			throw new BusinessException("传入参数对象不能为空");
		}
		// 判断设置库区 ,最后库存code,最终配载部门是否成功
		if (!setOrgRole(partitionViewInfoVo)) {
			return null;
		}
		List<PartitionViewInfoDto> partitionViewInfos = partitionViewInfoDao.selectPartitionByBigZone(partitionViewInfoVo, start, limit);
		
		for (int i = 0, len = partitionViewInfos.size(); i < len; i ++ ) {
			List<String> list = new ArrayList<String>();
			list.add(partitionViewInfos.get(i).getRegionalVirtaulCode());
			List<RegionVehicleEntity> regionVehicles = regionalVehicleDao.queryRegionVehiclesByRegionCodes(list);
			if (regionVehicles != null && regionVehicles.size() > 0) {
				partitionViewInfos.get(i).setVehicleNo(regionVehicles.get(0).getVehicleNo());
				partitionViewInfos.get(i).setDeliveryDate(partitionViewInfoVo.getDeliveryDate());
				SimpleTruckScheduleDto queryTruckByVehicle = truckSchedulingTaskService.queryTruckByVehicle(regionVehicles.get(0).getVehicleNo(),DateUtils.format(partitionViewInfoVo.getDeliveryDate(), DateUtils.ISO8601_DATE_PATTERN));
				if (queryTruckByVehicle != null) {
					partitionViewInfos.get(i).setTruckModelValue(queryTruckByVehicle.getTruckModelValue());
					partitionViewInfos.get(i).setFrequencyNo(queryTruckByVehicle.getFrequencyNo());
					partitionViewInfos.get(i).setExpectedBringVolume(queryTruckByVehicle.getExpectedBringVolume());
				}
				
				
			}
		}
		return partitionViewInfos;
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionViewInfoService#selectPartitionByBigZoneCount(com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo)
	 */
	@Override
	public Long selectPartitionByBigZoneCount(PartitionViewInfoVo partitionViewInfoVo) {
		if (partitionViewInfoVo == null) {
			throw new BusinessException("传入参数对象不能为空");
		}
		// 判断设置库区 ,最后库存code,最终配载部门是否成功
		if (!setOrgRole(partitionViewInfoVo)) {
			return null;
		}
		return partitionViewInfoDao.selectPartitionByBigZoneCount(partitionViewInfoVo);
	}

	/* (non-Javadoc)
	 * @see com.deppon.foss.module.pickup.predeliver.api.server.service.IPartitionViewInfoService#selectPartitionBySmallZone(com.deppon.foss.module.pickup.predeliver.api.shared.vo.PartitionViewInfoVo)
	 */
	@Override
	public List<PartitionViewInfoDto> selectPartitionBySmallZone(PartitionViewInfoVo partitionViewInfoVo) {
		if (partitionViewInfoVo == null) {
			throw new BusinessException("传入参数对象不能为空");
		}
		// 判断设置库区 ,最后库存code,最终配载部门是否成功
		if (!setOrgRole(partitionViewInfoVo)) {
			return null;
		}
		List<PartitionViewInfoDto> partitionViewInfos = partitionViewInfoDao.selectPartitionBySmallZone(partitionViewInfoVo);
		for (int i = 0, len = partitionViewInfos.size(); i < len; i ++ ) {
			List<RegionVehicleEntity> regionVehicles =regionalVehicleDao.getRegionVehicleNoBySmallZoneCode(partitionViewInfos.get(i).getRegionalCode());
			if (regionVehicles != null && regionVehicles.size() > 0) {
				partitionViewInfos.get(i).setRegionalName(regionVehicles.get(0).getRegionName());
				partitionViewInfos.get(i).setVehicleNo(regionVehicles.get(0).getVehicleNo());
				partitionViewInfos.get(i).setDeliveryDate(partitionViewInfoVo.getDeliveryDate());
				SimpleTruckScheduleDto queryTruckByVehicle = truckSchedulingTaskService.queryTruckByVehicle(regionVehicles.get(0).getVehicleNo(),DateUtils.format(partitionViewInfoVo.getDeliveryDate(), DateUtils.ISO8601_DATE_PATTERN));
				if (queryTruckByVehicle != null) {
					partitionViewInfos.get(i).setTruckModelValue(queryTruckByVehicle.getTruckModelValue());
					partitionViewInfos.get(i).setFrequencyNo(queryTruckByVehicle.getFrequencyNo());
					partitionViewInfos.get(i).setExpectedBringVolume(queryTruckByVehicle.getExpectedBringVolume());
				}
				
				
			}
		}
		return partitionViewInfos;
	}
	
	/**
	 * @Title: setOrgRole
	 * @Description: 设置库区 ,最后库存code,最终配载部门
	 * @param partitionViewInfoVo
	 * @return true：设置成功  false:设置失败
	 */
	private boolean setOrgRole(PartitionViewInfoVo partitionViewInfoVo) {
		PermissionControlDto pcd = waybilldetailNewService.initOrgRole();
		if (!pcd.isNext()) {
			return false;
		}
		partitionViewInfoVo.setLastLoadOrgCode(pcd.getLastLoadOrgCode());
		partitionViewInfoVo.setEndStockOrgCode(pcd.getEndStockOrgCode());
		partitionViewInfoVo.setGoodsAreaCode(pcd.getGoodsAreaCode());
		return true;
	}

	/**
	 * 获取partitionViewInfoDao
	 * @return the partitionViewInfoDao
	 */
	public IPartitionViewInfoDao getPartitionViewInfoDao() {
		return partitionViewInfoDao;
	}

	/**
	 * 设置partitionViewInfoDao
	 * @param partitionViewInfoDao 要设置的partitionViewInfoDao
	 */
	@Resource
	public void setPartitionViewInfoDao(IPartitionViewInfoDao partitionViewInfoDao) {
		this.partitionViewInfoDao = partitionViewInfoDao;
	}

	/**
	 * 获取regionalVehicleDao
	 * @return the regionalVehicleDao
	 */
	public IRegionalVehicleDao getRegionalVehicleDao() {
		return regionalVehicleDao;
	}

	/**
	 * 设置regionalVehicleDao
	 * @param regionalVehicleDao 要设置的regionalVehicleDao
	 */
	@Resource
	public void setRegionalVehicleDao(IRegionalVehicleDao regionalVehicleDao) {
		this.regionalVehicleDao = regionalVehicleDao;
	}

	/**
	 * 获取truckSchedulingTaskService
	 * @return the truckSchedulingTaskService
	 */
	public ITruckSchedulingTaskService getTruckSchedulingTaskService() {
		return truckSchedulingTaskService;
	}

	/**
	 * 设置truckSchedulingTaskService
	 * @param truckSchedulingTaskService 要设置的truckSchedulingTaskService
	 */
	@Resource
	public void setTruckSchedulingTaskService(ITruckSchedulingTaskService truckSchedulingTaskService) {
		this.truckSchedulingTaskService = truckSchedulingTaskService;
	}

	/**
	 * 设置waybilldetailNewService
	 * @param waybilldetailNewService 要设置的waybilldetailNewService
	 */
	@Resource
	public void setWaybilldetailNewService(IWaybilldetailNewService waybilldetailNewService) {
		this.waybilldetailNewService = waybilldetailNewService;
	}
	
	

}
