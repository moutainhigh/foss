package com.deppon.foss.module.transfer.common.server.service.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.server.dao.IWaybillFromMachineDao;
import com.deppon.foss.module.transfer.common.api.server.service.IWaybillFromMachineService;
import com.deppon.foss.module.transfer.common.api.shared.domain.WaybillFromMachineEntity;

public class WaybillFromMachineService implements IWaybillFromMachineService {
	
	private IWaybillFromMachineDao waybillFromMachineDao;
	
	public void setWaybillFromMachineDao(
			IWaybillFromMachineDao waybillFromMachineDao) {
		this.waybillFromMachineDao = waybillFromMachineDao;
	}
	/**
	 * 添加一条数据
	 * @author 268084
	 * @param wfmEntity
	 */
	@Override
	public void addData(WaybillFromMachineEntity wfmEntity) {
		waybillFromMachineDao.addData(wfmEntity);

	}
	/**
	 * 删除一天数据
	 * @author 268084
	 * @param waybillNo
	 */
	@Override
	public void deleteByWaybillNo(String waybillNo) {
		waybillFromMachineDao.deleteByWaybillNo(waybillNo);

	}
	/**
	 * 修改一条数据
	 * @author 268084
	 * @param map
	 */
	@Override
	public void updateByWayBillNo(WaybillFromMachineEntity wfmEntity) {
		waybillFromMachineDao.updateByWayBillNo(wfmEntity);

	}
	/**
	 *根据天剑查询数据
	 * @author 268084
	 * @param map
	 * @return
	 */
	@Override
	public List<WaybillFromMachineEntity> findByCondition(
			Map<String, String> map) {
		
		return waybillFromMachineDao.findByCondition(map);
	}

}
