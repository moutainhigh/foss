package com.deppon.foss.module.transfer.common.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.common.api.shared.domain.WaybillFromMachineEntity;

public interface IWaybillFromMachineDao {
	/**
	 * 添加一条数据
	 * @author 268084
	 * @param wfmEntity
	 */
	void addData(WaybillFromMachineEntity wfmEntity);
	/**
	 * 删除一天数据
	 * @author 268084
	 * @param waybillNo
	 */
	void deleteByWaybillNo(String waybillNo);
	/**
	 * 修改一条数据
	 * @author 268084
	 * @param map
	 */
	void updateByWayBillNo(WaybillFromMachineEntity wfmEntity);
	/**
	 *根据天剑查询数据
	 * @author 268084
	 * @param map
	 * @return
	 */
	List<WaybillFromMachineEntity> findByCondition(Map<String, String> map);
}
