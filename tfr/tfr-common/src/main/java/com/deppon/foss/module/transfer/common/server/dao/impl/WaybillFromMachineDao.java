package com.deppon.foss.module.transfer.common.server.dao.impl;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.common.api.server.dao.IWaybillFromMachineDao;
import com.deppon.foss.module.transfer.common.api.shared.domain.WaybillFromMachineEntity;

public class WaybillFromMachineDao extends iBatis3DaoImpl implements IWaybillFromMachineDao {
	private static final String nameSpace="foss.tfr.waybillfromMachine.";
	/**
	 * 添加一条数据
	 * @author 268084
	 * @param wfmEntity
	 */
	@Override
	public void addData(WaybillFromMachineEntity wfmEntity) {
		this.getSqlSession().insert(nameSpace+"adddNewData", wfmEntity);

	}
	/**
	 * 删除一天数据
	 * @author 268084
	 * @param waybillNo
	 */
	@Override
	public void deleteByWaybillNo(String waybillNo) {
		this.getSqlSession().delete(nameSpace+"deleteByWaybill", waybillNo);

	}
	/**
	 * 修改一条数据
	 * @author 268084
	 * @param map
	 */
	@Override	
	public void updateByWayBillNo(WaybillFromMachineEntity wfmEntity) {
		this.getSqlSession().update(nameSpace+"updateData", wfmEntity);

	}
	/**
	 *根据条件查询数据
	 * @author 268084
	 * @param map
	 * @return
	 */
	@Override
	public List<WaybillFromMachineEntity> findByCondition(
			Map<String, String> map) {
		
		return this.getSqlSession().selectList(nameSpace+"findByCondition", map);
	}

}
