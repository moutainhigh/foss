package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.List;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPdaUnloadOptimizeDao;
import com.deppon.foss.module.transfer.unload.api.shared.domain.VehicleSyncPdaEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDto;

@SuppressWarnings("unchecked")
public class PdaUnloadOptimizeDao extends iBatis3DaoImpl implements IPdaUnloadOptimizeDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.unload.api.server.dao.IPdaUnloadOptimizeDao.";

	@Override
	public PdaUnloadTaskDto queryUnloadTaskByTaskNo(String unloadTaskNo) {
		return (PdaUnloadTaskDto) super.getSqlSession().selectOne(NAMESPACE + "queryUnloadTaskByTaskNo", unloadTaskNo);
	}

	@Override
	public List<UnloadBillDto> queryUnloadHandOverBill(String unloadTaskId) {
		return super.getSqlSession().selectList(NAMESPACE + "queryUnloadHandOverBill", unloadTaskId);
	}

	@Override
	public List<UnloadBillDto> queryUnloadAssembleBill(String unloadTaskId) {
		return super.getSqlSession().selectList(NAMESPACE + "queryUnloadAssembleBill", unloadTaskId);
	}

	@Override
	public List<UnloadWaybillDto> queryUnloadWaybillByTaskId(String unloadTaskId) {
		return super.getSqlSession().selectList(NAMESPACE + "queryUnloadWaybillByTaskId", unloadTaskId);
	}

	@Override
	public List<UnloadSerialDto> queryUnloadMoreSerial(String unloadWaybillDetailId) {
		return super.getSqlSession().selectList(NAMESPACE + "queryUnloadMoreSerial", unloadWaybillDetailId);
	}

	@Override
	public int addVehicleSyncPda(VehicleSyncPdaEntity info) {
		return super.getSqlSession().insert(NAMESPACE + "addVehicleSyncPda", info);
	}

	@Override
	public int updateVehicleSyncPda(VehicleSyncPdaEntity info) {
		return super.getSqlSession().update(NAMESPACE + "updateVehicleSyncPda", info);
	}

	@Override
	public int deleteVehicleSyncPda(VehicleSyncPdaEntity info) {
		return super.getSqlSession().delete(NAMESPACE + "deleteVehicleSyncPda", info);
	}

	@Override
	public List<VehicleSyncPdaEntity> queryVehicleSyncPda() {
		return super.getSqlSession().selectList(NAMESPACE + "queryVehicleSyncPda");
	}

	@Override
	public List<UnloadBillDto> queryUnloadBillByTruckTaskDetailId(String truckTaskDetailId) {
		return super.getSqlSession().selectList(NAMESPACE + "queryUnloadBillByTruckTaskDetailId", truckTaskDetailId);
	}

	@Override
	public List<UnloadWaybillDto> queryHandOverWaybill(String billNo) {
		return super.getSqlSession().selectList(NAMESPACE + "queryHandOverWaybill", billNo);
	}

	@Override
	public List<UnloadWaybillDto> queryAssembleWaybill(String billNo) {
		return super.getSqlSession().selectList(NAMESPACE + "queryAssembleWaybill", billNo);
	}

	@Override
	public List<UnloadSerialDto> queryUnloadSerial(UnloadWaybillDto waybill) {
		return super.getSqlSession().selectList(NAMESPACE + "queryUnloadSerial", waybill);
	}

	@Override
	public int queryContraband(String waybillNo) {
		return (Integer) super.getSqlSession().selectOne(NAMESPACE + "queryContraband", waybillNo);
	}

	@Override
	public String queryOrgStationNum(String customerPickupOrgCode) {
		return (String) super.getSqlSession().selectOne(NAMESPACE + "queryOrgStationNum", customerPickupOrgCode);
	}

	@Override
	public String queryOuterBranchStationNum(String customerPickupOrgCode) {
		return (String) super.getSqlSession().selectOne(NAMESPACE + "queryOuterBranchStationNum",
				customerPickupOrgCode);
	}

	@Override
	public int queryNeedPack(UnloadSerialDto serial) {
		return (Integer) super.getSqlSession().selectOne(NAMESPACE + "queryNeedPack", serial);
	}

	@Override
	public int queryTodo(UnloadSerialDto serial) {
		return (Integer) super.getSqlSession().selectOne(NAMESPACE + "queryTodo", serial);
	}

	@Override
	public String queryWaybillType(String waybillNo) {
		return (String) super.getSqlSession().selectOne(NAMESPACE + "queryWaybillType", waybillNo);
	}

	@Override
	public List<ReturnGoodsRequestEntity> queryReturnGoods(String waybillNo) {
		return super.getSqlSession().selectList(NAMESPACE + "queryReturnGoods", waybillNo);
	}
}
