package com.deppon.foss.module.pickup.predeliver.server.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.tools.EditVehicleSchedulingThreadPoolCaller;

/**
 * @Description:车辆排班信息修改-线程自动加载
 * @author 269044
 * @date 2015-10-26
 */
public class AutoVehicleSchedulingThread extends EditVehicleSchedulingThreadPoolCaller{
	public static final Logger LOGGER = LoggerFactory
			.getLogger(AutoVehicleSchedulingThread.class);
	/**
	 * 车辆排班发生更改表Service 
	 */
	private IEditVechileSchedulingService editVechileSchedulingService;

	/**
	 * 设置editVechileSchedulingService
	 * @param editVechileSchedulingService 要设置的editVechileSchedulingService
	 */
	@Resource
	public void setEditVechileSchedulingService(
			IEditVechileSchedulingService editVechileSchedulingService) {
		this.editVechileSchedulingService = editVechileSchedulingService;
	}
	
	/**
	 * 线程池处理process
	 */
	private AutoVehicleSchedulingHandleProcess autoVehicleSchedulingHandleProcess;
	
	/**
	 * 设置autoVehicleSchedulingHandleProcess
	 * @param autoVehicleSchedulingHandleProcess 要设置的autoVehicleSchedulingHandleProcess
	 */
	public void setAutoVehicleSchedulingHandleProcess(
			AutoVehicleSchedulingHandleProcess autoVehicleSchedulingHandleProcess) {
		this.autoVehicleSchedulingHandleProcess = autoVehicleSchedulingHandleProcess;
	}


	@Override
	public int serviceCaller() {
		// 获取车辆排班变更信息集合
		List<EditVechileSchedulingEntity> selectEditInfoList = editVechileSchedulingService.selectEditInfoList();
		LOGGER.info("AutoVehicleSchedulingThread 车辆排班发生变更更新交单车辆线程自动处理开启......");
		try {	
			if(CollectionUtils.isNotEmpty(selectEditInfoList)) {
				//将其添加到线程池中
				autoVehicleSchedulingHandleProcess.addIntoThreadPool(selectEditInfoList);
			}
			editVechileSchedulingService.deleteEditInfo();
		} catch (Exception e ) {
			LOGGER.info("AutoVehicleSchedulingThread 车辆排班发生变更更新交单车辆线程自动处理异常！");
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("AutoVehicleSchedulingThread 车辆排班发生变更更新交单车辆线程自动处理结束......");
		return selectEditInfoList.size();
	}


}
