package com.deppon.foss.module.pickup.predeliver.server.job;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.pickup.order.api.shared.util.SettlementReportNumber;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IEditVechileSchedulingService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.IHandoverBillVoService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.EditVechileSchedulingEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.tools.DeliverTheadPool;

/**
 * @Description:车辆排班发生变更更新交单车辆-线程自动加载
 * @author 269044
 * @date 2015-10-26
 */
public class AutoVehicleSchedulingHandleProcess extends DeliverTheadPool {
	private static final Logger LOGGER = LoggerFactory.
			getLogger(AutoVehicleSchedulingHandleProcess.class);
	/**
	 * 车辆排班发生更改表Service 
	 */
	private IEditVechileSchedulingService editVechileSchedulingService;
	
	/**
	 * 已交单VoService 
	 */
	private IHandoverBillVoService handoverBillVoService;

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
	 * 设置handoverBillVoService
	 * @param handoverBillVoService 要设置的handoverBillVoService
	 */
	@Resource
	public void setHandoverBillVoService(
			IHandoverBillVoService handoverBillVoService) {
		this.handoverBillVoService = handoverBillVoService;
	}

	/**
	 * @title businessExecutor
	 * @param object 每一个即将要处理的对象
	 */
	@Override
	public void businessExecutor(Object obj) {
		LOGGER.info("AutoVehicleSchedulingHandleProcess 车辆排班发生变更更新交单车辆线程池的业务执行者方法执行开始......");
		EditVechileSchedulingEntity editVechile =(EditVechileSchedulingEntity)obj;
		// 修改交单表里的车辆
		int i = handoverBillVoService.updateHandoverBillVehicle(editVechile.getSmallZoneCode(),editVechile.getVechileNo(),
				editVechile.getEditDate(), editVechile.getSchedulingStatus());
		LOGGER.info("修改交单表里的车辆后返回的结果 " + i);
	    LOGGER.info("AutoVehicleSchedulingHandleProcess 车辆排班发生变更更新交单车辆线程池的业务执行者方法执行结束......");
	}
	
	/**
	 * @Title: addIntoThreadPool
	 * @Description: 添加至线程池
	 * @param 
	 */
	public void addIntoThreadPool(List<EditVechileSchedulingEntity> selectEditInfoList) {
		if (CollectionUtils.isNotEmpty(selectEditInfoList)) {
			for (int i = 0, size = selectEditInfoList.size(); i < size; i++) {
				// 获取集合中的每个信息
				EditVechileSchedulingEntity editVechile = selectEditInfoList.get(i);
				// 修改车辆排班变更信息表里的车辆的使用状态
				int result = editVechileSchedulingService.updateEditInfoIsUsed(editVechile.getId(), "Y");
				if (result == 1){
					//将每一个实体放入线程池中处理
					super.pushThreadsPool(editVechile);
				} else {
					LOGGER.info("车辆排班状态更改失败！");
				}
			}
		} else {
			LOGGER.info("车辆排班没有要变更的信息");
		}
	}

	@Override
	public void outOfOrderPool(Object obj) {
		LOGGER.info("AutoVehicleSchedulingHandleProcess 车辆排班发生变更更新交单车辆线程池满异常处理开始......");
		EditVechileSchedulingEntity editVechile =(EditVechileSchedulingEntity)obj;
		if(editVechile != null ) {
			editVechileSchedulingService.updateEditInfoIsUsed(editVechile.getId(), "N");
		}
		LOGGER.info("AutoVehicleSchedulingHandleProcess 车辆排班发生变更更新交单车辆线程池满异常处理结束......");
	}

	/**
	 * @Title: getActiveThreads
	 * @Description: 添加至线程池最大的个数
	 */
	@Override
	public int getActiveThreads() {
		return SettlementReportNumber.THREE;
	}

}
