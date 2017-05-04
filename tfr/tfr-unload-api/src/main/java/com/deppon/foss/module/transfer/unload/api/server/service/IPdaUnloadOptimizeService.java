package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.VehicleSyncPdaEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadSerialDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDto;

public interface IPdaUnloadOptimizeService {

	/**
	 * @desc 新增待同步给pda的车辆信息
	 * @param info
	 * @date 2016年9月20日 上午8:27:41
	 */
	void addVehicleSyncPda(VehicleSyncPdaEntity info);

	/**
	 * @desc 更新待同步给pda的车辆信息
	 * @param info
	 * @date 2016年9月20日 上午8:28:52
	 */
	int updateVehicleSyncPda(VehicleSyncPdaEntity info);

	int deleteVehicleSyncPda(VehicleSyncPdaEntity info);

	List<VehicleSyncPdaEntity> queryVehicleSyncPda();

	/**
	 * @desc 根据车辆任务明细id查询车辆任务单据
	 * @param truckTaskDetailId
	 * @return
	 * @date 2016年9月19日 下午8:56:22
	 */
	List<UnloadBillDto> queryUnloadBillByTruckTaskDetailId(String truckTaskDetailId);

	/**
	 * @desc 根据卸车任务号，查询对应的卸车单据
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月21日 下午3:25:12
	 */
	PdaUnloadTaskDto queryPdaUnloadTaskByTaskNo(String unloadTaskNo);

	/**
	 * @desc 根据卸车任务号查询多货信息
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月20日 上午8:28:05
	 */
	List<PdaUnloadSerialDto> queryUnloadMoreGoods(String unloadTaskNo);

	/**
	 * @desc 根据卸车单据，查询对应的卸车流水
	 * @param bill
	 * @return
	 * @date 2016年9月21日 下午3:26:32
	 */
	List<PdaUnloadSerialDto> queryPdaUnloadSerialDto(UnloadBillDto bill);

}
