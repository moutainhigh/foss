package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IVehicleDrivingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IVehicleDrivingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleDrivingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleDrivingQueryDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;


public class VehicleDrivingService implements IVehicleDrivingService  {
	/**
	 * 日志打印对象申明
	 */
	private static final Logger log = Logger.getLogger(VehicleDrivingService.class);
	private IVehicleDrivingDao vehicleDrivingDao;
	public void setVehicleDrivingDao(IVehicleDrivingDao vehicleDrivingDao) {
		this.vehicleDrivingDao = vehicleDrivingDao;
	}
	/**
	 * 业务锁SERVICE
	 */
	private IBusinessLockService businessLockService;
	
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}
	/**
	 * 新增长途车队信息
	 */
	@Override
	public void addVehicleDriving(VehicleDrivingEntity entity,
			CurrentInfo currentInfo) {
		if(entity == null){
			throw new BusinessException("新增长途车队信息失败，参数异常");
		}
		log.info("begin addVehicleDriving()");
		// 业务锁
		MutexElement mutex = new MutexElement(entity.getLongHaulFleetCode()
				+ entity.getDepartmentCode(), "LONGHAUL_FLEET_CODE",
				MutexElementType.LONGHAUL_FLEET_CODE);
		log.info("开始加锁：" + mutex.getBusinessNo());
		boolean result = businessLockService.lock(mutex, NumberConstants.ZERO);
		if (!result) {
			throw new BusinessException("出现并发！");
		}
		
		log.info("成功加锁：" + mutex.getBusinessNo());
		/*新增长途车队信息时校验表中是否有重复数据
		 * 1.根据长途车编码和下辖部门编码校验
		 * 2.根据长途车编码和行车编码校验
		 * */
		List<VehicleDrivingEntity> vehicleDrivingEntities 
									=  vehicleDrivingDao.queryVehicleDrivingByFleetDep(entity.getLongHaulFleetCode(), entity.getDepartmentCode());
		
		if(CollectionUtils.isNotEmpty(vehicleDrivingEntities)){
			throw new BusinessException("该新增的长途车队信息已经存在！");
		}
		List<VehicleDrivingEntity> vehicleDrivingEntitiys 
									= vehicleDrivingDao.queryVehicleDrivingByFleetTra(entity.getLongHaulFleetCode(), entity.getTrafficCode());
		if(CollectionUtils.isNotEmpty(vehicleDrivingEntitiys)){
			throw new BusinessException("新增长途车队行车编码已存在");
		}
		
		vehicleDrivingDao.addVehicleDriving(entity, currentInfo);
		log.info("开始解锁：" + mutex.getBusinessNo());
		// 解业务锁
		businessLockService.unlock(mutex);
		log.info("完成解锁：" + mutex.getBusinessNo());
		
	}
	/**
	 * 删除长途车队信息
	 */
	@Override
	public void deleteVehicleDriving(VehicleDrivingQueryDto queryDto,
			CurrentInfo currentInfo) {
		toDoSelectedIds(queryDto);
		vehicleDrivingDao.updateVehicleDriving(queryDto, currentInfo);
	}
	
	//选择单条或多条记录
	private void toDoSelectedIds(VehicleDrivingQueryDto queryDto)
	{
			 
		// 如果选择的ID为空，提示输入参数错误
		if (StringUtils.isEmpty(queryDto.getSelectedIds())) {
			throw new BusinessException("没有选中条目");
		}

		// 字符串切割,变为字符串数组
		String[] idArray = queryDto.getSelectedIds().split(",");
				
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < idArray.length; i++) {
			list.add(idArray[i].trim());
		}
		queryDto.setSelectedIdList(list);

	}
	/**
	 * 表单回显总条数
	 */
	@Override
	public long queryTotalByCondition(VehicleDrivingQueryDto queryDto) {		
		return vehicleDrivingDao.queryTotalByCondition(queryDto);
	}
	
	/**
	 * 表单回显所有长途车队信息
	 */
	@Override
	public List<VehicleDrivingEntity> queryVehicleDrivingByCondition(
			VehicleDrivingQueryDto queryDto, int start, int limit) {
		
		return vehicleDrivingDao.queryVehicleDrivingByCondition(queryDto, start, limit);
	}
	
	
	
	/**
	 * 供中转调用
	 */
	@Override
	public List<VehicleDrivingEntity> queryVehicleDrivingByLHF(
			String longHaulFleetCode) {
		
		return vehicleDrivingDao.queryVehicleDrivingByFleetDep(longHaulFleetCode, null);
	}
	
	/**
	 * 通过下辖部门编码查询返回车队信息供中转调用
	 */
	@Override
	public List<VehicleDrivingEntity> queryVehicleDrivingByDEP(
			String departmentCode) {
		
		return vehicleDrivingDao.queryVehicleDrivingByFleetDep(null, departmentCode);
	}

}
