package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.VehicleSyncPdaEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDto;

public interface IPdaUnloadOptimizeDao {

	/**
	 * @desc 根据卸车任务号，查询卸车任务
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月21日 下午8:30:45
	 */
	PdaUnloadTaskDto queryUnloadTaskByTaskNo(String unloadTaskNo);
	
	/**
	 * @desc 根据卸车任务id，查询对应的卸车交接单
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月21日 下午3:28:24
	 */
	List<UnloadBillDto> queryUnloadHandOverBill(String unloadTaskId);
	
	/**
	 * @desc 根据卸车任务id，查询对应的卸车配载单
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月21日 下午3:28:24
	 */
	List<UnloadBillDto> queryUnloadAssembleBill(String unloadTaskId);


	/**
	 * @desc 根据卸车任务id，查询已扫描的运单
	 * @param billNo
	 * @return
	 * @date 2016年9月18日 下午9:07:02
	 */
	List<UnloadWaybillDto> queryUnloadWaybillByTaskId(String unloadTaskId);

	/**
	 * @desc 根据卸车运单明细id,运单号，查询卸车多货流水
	 * @param waybill
	 * @return
	 * @date 2016年9月21日 下午7:22:17
	 */
	List<UnloadSerialDto> queryUnloadMoreSerial(String unloadWaybillDetailId);

	/**
	 * @desc 新增待同步给pda的车辆信息
	 * @param info
	 * @date 2016年9月19日 下午8:56:13
	 */
	int addVehicleSyncPda(VehicleSyncPdaEntity info);

	/**
	 * @desc 更新待同步给pda的车辆信息
	 * @param info
	 * @date 2016年9月20日 上午8:28:52
	 */
	int updateVehicleSyncPda(VehicleSyncPdaEntity updateVehicleSyncPda);

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
	 * @desc 根据交接单号，查询卸车运单信息
	 * @param billNo
	 * @return
	 * @date 2016年9月18日 下午9:06:30
	 */
	List<UnloadWaybillDto> queryHandOverWaybill(String billNo);

	/**
	 * @desc 根据配载单号，查询卸车运单信息
	 * @param billNo
	 * @return
	 * @date 2016年9月18日 下午9:07:02
	 */
	List<UnloadWaybillDto> queryAssembleWaybill(String billNo);

	/**
	 * @desc 根据交接单，运单号查询交接单流水
	 * @param waybill
	 * @return
	 * @date 2016年9月20日 下午3:00:03
	 */
	List<UnloadSerialDto> queryUnloadSerial(UnloadWaybillDto waybill);
	
	int queryContraband(String waybillNo);

	String queryOrgStationNum(String customerPickupOrgCode);

	String queryOuterBranchStationNum(String customerPickupOrgCode);

	int queryNeedPack(UnloadSerialDto serial);

	int queryTodo(UnloadSerialDto serial);
	
	String queryWaybillType(String waybillNo);
	
	List<ReturnGoodsRequestEntity> queryReturnGoods(String waybillNo);
}
