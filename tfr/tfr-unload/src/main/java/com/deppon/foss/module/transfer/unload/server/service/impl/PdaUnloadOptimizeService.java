package com.deppon.foss.module.transfer.unload.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.MessageConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.transfer.unload.api.server.dao.IPdaUnloadOptimizeDao;
import com.deppon.foss.module.transfer.unload.api.server.service.IPdaUnloadOptimizeService;
import com.deppon.foss.module.transfer.unload.api.shared.domain.VehicleSyncPdaEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadSerialDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.PdaUnloadTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadBillDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadSerialDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.UnloadWaybillDto;
import com.deppon.foss.util.define.FossConstants;

public class PdaUnloadOptimizeService implements IPdaUnloadOptimizeService {
	
	private static final String UNLOAD_TYPE_LONG_DISTANCE = "LONG_DISTANCE";
	private static final String UNLOAD_TYPE_SHORT_DISTANCE = "SHORT_DISTANCE";
	private static final String BILL_TYPE_VEHICLEASSEMBLE = "VEHICLEASSEMBLE";
	private static final String BILL_TYPE_HANDOVER = "HANDOVER";

	private IPdaUnloadOptimizeDao pdaUnloadOptimizeDao;

	private IAdministrativeRegionsService administrativeRegionsService;

	private ISaleDepartmentService saleDepartmentService;

	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setPdaUnloadOptimizeDao(IPdaUnloadOptimizeDao pdaUnloadOptimizeDao) {
		this.pdaUnloadOptimizeDao = pdaUnloadOptimizeDao;
	}

	public void setAdministrativeRegionsService(IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setSaleDepartmentService(ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setOrgAdministrativeInfoService(IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * @desc 根据卸车任务号，查询多货信息; 刷新卸车任务明细时，若pda从pda端的缓存取到明细，则需要加上多货信息
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月19日 下午10:00:22
	 */
	@Override
	public List<PdaUnloadSerialDto> queryUnloadMoreGoods(String unloadTaskNo) {
		List<PdaUnloadSerialDto> result = new ArrayList<PdaUnloadSerialDto>();

		PdaUnloadTaskDto unloadTask = pdaUnloadOptimizeDao.queryUnloadTaskByTaskNo(unloadTaskNo);
		if (unloadTask == null) {
			return result;
		}

		List<UnloadWaybillDto> waybillList = pdaUnloadOptimizeDao
				.queryUnloadWaybillByTaskId(unloadTask.getUnloadTaskId());

		String unloadOrgTfrCtrCode = queryUnloadTfrCtrCode(unloadTask.getUnloadOrgCode());

		for (UnloadWaybillDto waybill : waybillList) {
			// 根据卸车运单明细id查询卸车多货流水，若查不到，则表示无卸车多货
			List<UnloadSerialDto> serialList = pdaUnloadOptimizeDao
					.queryUnloadMoreSerial(waybill.getUnloadWaybillDetailId());

			if (CollectionUtils.isEmpty(serialList)) {
				continue;
			}

			attachUnloadWaybill(waybill, unloadOrgTfrCtrCode);

			for (UnloadSerialDto serial : serialList) {
				// pdaUnloadOptimizeDao.queryUnloadMoreSerial没有查询运单号，这里设值
				serial.setWaybillNo(waybill.getWaybillNo());
				// 完善流水号信息
				attachUnloadSerial(serial);
				// 构造一个pda需要的卸车明细对象
				PdaUnloadSerialDto element = constructPdaUnloadSerial(null, waybill, serial);

				result.add(element);
			}
		}

		return result;
	}

	/**
	 * @desc 根据卸车任务号，查询对应的卸车任务单据； 刷新卸车任务明细时，若pda不知道卸车任务对应卸车单据，则此方法提供；只针对长短途
	 * @param unloadTaskNo
	 * @return
	 * @date 2016年9月21日 下午3:49:43
	 */
	@Override
	public PdaUnloadTaskDto queryPdaUnloadTaskByTaskNo(String unloadTaskNo) {
		PdaUnloadTaskDto task = pdaUnloadOptimizeDao.queryUnloadTaskByTaskNo(unloadTaskNo);
		if (task != null) {
			String unloadType = task.getUnloadType();
			List<UnloadBillDto> billList = null;
			if(UNLOAD_TYPE_LONG_DISTANCE.equals(unloadType)){
				 billList = pdaUnloadOptimizeDao.queryUnloadAssembleBill(task.getUnloadTaskId());
			}else if(UNLOAD_TYPE_SHORT_DISTANCE.equals(unloadType)){
				 billList = pdaUnloadOptimizeDao.queryUnloadHandOverBill(task.getUnloadTaskId());
			}
			task.setUnloadBillList(billList);
		}
		return task;
	}

	/**
	 * @desc 根据卸车单据，查询pda卸车流水； 刷新卸车任务明细时，若pda没有在pda端的缓存中取到对应的明细，则此方法可以提供；只针对长短途
	 * @param bill
	 * @return
	 * @date 2016年9月21日 下午3:50:07
	 */
	@Override
	public List<PdaUnloadSerialDto> queryPdaUnloadSerialDto(UnloadBillDto bill) {
		List<PdaUnloadSerialDto> result = new ArrayList<PdaUnloadSerialDto>();

		// 根据车辆任务单据查询对应运单
		List<UnloadWaybillDto> waybillList = queryUnloadWaybill(bill);

		if (CollectionUtils.isEmpty(waybillList)) {
			return result;
		}

		// 查询每个单据的卸车外场
		String unloadOrgTfrCtrCode = queryUnloadTfrCtrCode(bill.getDestOrgCode());

		for (UnloadWaybillDto waybill : waybillList) {
			// 完善运单信息
			attachUnloadWaybill(waybill, unloadOrgTfrCtrCode);
			// 查询车辆任务单据对应的运单流水
			List<UnloadSerialDto> serialList = pdaUnloadOptimizeDao.queryUnloadSerial(waybill);

			for (UnloadSerialDto serial : serialList) {
				// 完善流水号信息
				attachUnloadSerial(serial);
				// 构造一个pda需要的卸车明细对象
				PdaUnloadSerialDto element = constructPdaUnloadSerial(bill, waybill, serial);

				result.add(element);
			}
		}

		return result;
	}

	/**
	 * @desc 根据单据编号，单据类型，查询对应的运单,配载单中需过滤掉包交接单，sql中已处理
	 * @param unloadBill
	 * @return
	 * @date 2016年9月21日 下午3:55:26
	 */
	private List<UnloadWaybillDto> queryUnloadWaybill(UnloadBillDto unloadBill) {
		List<UnloadWaybillDto> unloadWaybillList = null;

		String billType = unloadBill.getBillType();
		String billNo = unloadBill.getBillNo();
		if (BILL_TYPE_HANDOVER.equals(billType)) {
			unloadWaybillList = pdaUnloadOptimizeDao.queryHandOverWaybill(billNo);
		} else if (BILL_TYPE_VEHICLEASSEMBLE.equals(billType)) {
			unloadWaybillList = pdaUnloadOptimizeDao.queryAssembleWaybill(billNo);
		}

		return unloadWaybillList;
	}

	/**
	 * @desc 查询卸车外场；1如果是外场卸车，自然就是外场编码；2如果是驻地部门卸车，则为驻地部门对应外场编码
	 * @param destOrgCode
	 * @return
	 * @date 2016年9月20日 下午5:30:40
	 */
	private String queryUnloadTfrCtrCode(String destOrgCode) {
		OrgAdministrativeInfoEntity unloadOrg = orgAdministrativeInfoService
				.queryOrgAdministrativeInfoByCodeClean(destOrgCode);
		// 默认是外场建立的卸车任务
		String unloadOrgTfrCtrCode = unloadOrg.getCode();
		if (!FossConstants.YES.equals(unloadOrg.getTransferCenter())) {
			SaleDepartmentEntity salesDept = saleDepartmentService.querySaleDepartmentNoAttach(destOrgCode);
			// 是否驻地部门
			if (salesDept != null && FossConstants.YES.equals(salesDept.getStation())) {
				unloadOrgTfrCtrCode = salesDept.getTransferCenter();
			} else {
				unloadOrgTfrCtrCode = null;
			}
		}
		return unloadOrgTfrCtrCode;
	}

	/**
	 * @desc 完善运单信息
	 * @param waybill
	 * @date 2016年9月20日 下午4:19:15
	 */
	private void attachUnloadWaybill(UnloadWaybillDto waybill, String unloadOrgTfrCtrCode) {
		// 违禁品
		String beContraband = pdaUnloadOptimizeDao.queryContraband(waybill.getWaybillNo()) > 0 ? FossConstants.YES
				: FossConstants.NO;
		waybill.setBeContraband(beContraband);

		// 提货网点对应的接货网店编码
		String stationNumber = pdaUnloadOptimizeDao.queryOrgStationNum(waybill.getCustomerPickupOrgCode());
		if (stationNumber == null) {
			stationNumber = pdaUnloadOptimizeDao.queryOuterBranchStationNum(waybill.getCustomerPickupOrgCode());
		}
		waybill.setStationNumber(stationNumber);

		// 是否电子面单
		String waybillType = pdaUnloadOptimizeDao.queryWaybillType(waybill.getWaybillNo());
		String beEWaybill = FossConstants.NO;
		if ("EWAYBILL".equals(waybillType)) {
			beEWaybill = FossConstants.YES;
		}
		waybill.setBeEWaybill(beEWaybill);

		// 转寄退回、7天返货
		String isHandle = FossConstants.NO;
		String sevenDayReturn = FossConstants.NO;
		List<ReturnGoodsRequestEntity> returnGoods = pdaUnloadOptimizeDao.queryReturnGoods(waybill.getWaybillNo());
		if (CollectionUtils.isNotEmpty(returnGoods)) {
			isHandle = FossConstants.YES;
			sevenDayReturn = isSevenDayReturn(returnGoods) ? FossConstants.YES : FossConstants.NO;
		}

		waybill.setIsHandle(isHandle);
		waybill.setIsSevenDaysReturn(sevenDayReturn);

		if (unloadOrgTfrCtrCode == null || FossConstants.YES.equals(waybill.getIsExpress())) {
			return;
		}

		String pkpOrgTfrCtrCode = queryTransferCenterCodeByStationCode(waybill.getCustomerPickupOrgCode());

		if (unloadOrgTfrCtrCode.equals(pkpOrgTfrCtrCode)) {
			if (StringUtils.equals(waybill.getReceiveMethod(), "SELF_PICKUP")
					|| StringUtils.equals(waybill.getReceiveMethod(), "INNER_PICKUP")
					|| StringUtils.equals(waybill.getReceiveMethod(), "SELF_PICKUP_FREE_AIR")
					|| StringUtils.equals(waybill.getReceiveMethod(), "SELF_PICKUP_AIR")
					|| StringUtils.equals(waybill.getReceiveMethod(), "AIRPORT_PICKUP")) {
				// 行政区域名称
				waybill.setAdminiRegion("自提区");
			} else if (StringUtils.isNotEmpty(waybill.getReceiveCustomerDistCode())) {
				waybill.setAdminiRegion(
						administrativeRegionsService.queryRegionName(waybill.getReceiveCustomerDistCode()));
			}
		}
	}

	/**
	 * @desc 实际上就是returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo
	 * @param returnGoods
	 * @return
	 * @date 2016年9月22日 上午10:17:57
	 */
	private boolean isSevenDayReturn(List<ReturnGoodsRequestEntity> returnGoods) {
		boolean flag = false;
		int index = 0;
		for (int i = 1; i < returnGoods.size(); i++) {
			if (returnGoods.get(index).getTimeReport().getTime() < returnGoods.get(i).getTimeReport().getTime()) {
				index = i;
			}
		}
		// 只返回已受理的返单
		if (returnGoods.get(index) != null
				&& MessageConstants.ACCEPTSTATUS_HANDLED.equals(returnGoods.get(index).getReturnStatus())) {
			ReturnGoodsRequestEntity info = returnGoods.get(index);
			flag = "SEVEN_DAYS_RETURN".equals(info.getReturnType());
		}
		return flag;
	}

	/**
	 * @desc 完善流水信息
	 * @param serial
	 * @date 2016年9月20日 下午4:19:23
	 */
	private void attachUnloadSerial(UnloadSerialDto serial) {
		String needPacked = pdaUnloadOptimizeDao.queryNeedPack(serial) > 0 ? FossConstants.YES : FossConstants.NO;
		serial.setNeedPacked(needPacked);

		String hasTodo = pdaUnloadOptimizeDao.queryTodo(serial) > 0 ? FossConstants.YES : FossConstants.NO;
		serial.setHasTodo(hasTodo);
	}

	/**
	 * @desc 构造一个pda需要的对象
	 * @param element
	 * @param bill
	 * @param waybill
	 * @param serial
	 * @date 2016年9月20日 下午4:20:06
	 */
	private PdaUnloadSerialDto constructPdaUnloadSerial(UnloadBillDto bill, UnloadWaybillDto waybill,
			UnloadSerialDto serial) {
		PdaUnloadSerialDto element = new PdaUnloadSerialDto();

		if (bill != null) {
			element.setBillNo(bill.getBillNo());
			element.setBillType(bill.getBillType());
			element.setOrigOrgCode(bill.getOrigOrgCode());
			element.setDestOrgCode(bill.getDestOrgCode());
		}

		element.setWaybillNo(waybill.getWaybillNo());
		element.setIsValue(waybill.getIsValue());
		element.setReceiveOrgCode(waybill.getReceiveOrgCode());
		element.setReceiveOrgName(waybill.getReceiveOrgName());
		element.setCustomerPickupOrgCode(waybill.getCustomerPickupOrgCode());
		element.setCustomerPickupOrgName(waybill.getCustomerPickupOrgName());
		element.setWeight(waybill.getWeight());
		element.setVolume(waybill.getVolume());
		element.setWaybillQty(waybill.getWaybillQty());
		element.setGoodsName(waybill.getGoodsName());
		element.setProductName(waybill.getProductName());
		element.setPacking(waybill.getPacking());
		element.setStationNumber(waybill.getStationNumber());
		element.setBeContraband(waybill.getBeContraband());
		element.setAdminiRegion(waybill.getAdminiRegion());
		element.setIsSevenDaysReturn(waybill.getIsSevenDaysReturn());
		element.setBeEWaybill(waybill.getBeEWaybill());
		element.setIsHandle(waybill.getIsHandle());

		element.setSerialNo(serial.getSerialNo());
		element.setNeedPacked(serial.getNeedPacked());
		element.setHasTodo(serial.getHasTodo());

		StringBuilder sb = new StringBuilder();
		if (FossConstants.YES.equals(serial.getHasTodo())) {
			sb.append("有更改");
		}
		if (FossConstants.YES.equals(serial.getNeedPacked())) {
			sb.append(" 代打包装");
		}
		if (FossConstants.YES.equals(waybill.getIsValue())) {
			sb.append(" 贵重物品");
		}
		element.setNote(sb.toString().trim());
		return element;
	}

	/**
	 * @desc 实际上就是saleDepartmentService.queryTransferCenterCodeByStationCode这个方法
	 * @param customerPickupOrgCode
	 * @return
	 * @date 2016年9月20日 下午5:08:47
	 */
	private String queryTransferCenterCodeByStationCode(String customerPickupOrgCode) {
		SaleDepartmentEntity sale = saleDepartmentService.querySaleDepartmentNoAttach(customerPickupOrgCode);
		if (null != sale && StringUtils.equals(FossConstants.YES, sale.getStation())
				&& StringUtils.equals(FossConstants.YES, sale.getTruckArrive())) {
			return sale.getTransferCenter();
		}
		return null;
	}
	
	/**
	 * @desc 车辆出发时，记录车辆任务明细
	 * @param info
	 * @date 2016年9月20日 上午8:39:57
	 */
	@Override
	public void addVehicleSyncPda(VehicleSyncPdaEntity info) {
		pdaUnloadOptimizeDao.addVehicleSyncPda(info);
	}

	@Override
	public int updateVehicleSyncPda(VehicleSyncPdaEntity info) {
		return pdaUnloadOptimizeDao.updateVehicleSyncPda(info);
	}

	@Override
	public int deleteVehicleSyncPda(VehicleSyncPdaEntity info) {
		return pdaUnloadOptimizeDao.deleteVehicleSyncPda(info);
	}

	/**
	 * @desc 查询待处理的车辆信息，一次500,sql中有处理
	 * @return
	 * @date 2016年9月21日 下午3:58:25
	 */
	@Override
	public List<VehicleSyncPdaEntity> queryVehicleSyncPda() {
		return pdaUnloadOptimizeDao.queryVehicleSyncPda();
	}

	@Override
	public List<UnloadBillDto> queryUnloadBillByTruckTaskDetailId(String truckTaskDetailId) {
		return pdaUnloadOptimizeDao.queryUnloadBillByTruckTaskDetailId(truckTaskDetailId);
	}
}
