package com.deppon.foss.module.transfer.load.server.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.load.api.server.dao.IVehicleEmptyBillDao;
import com.deppon.foss.module.transfer.load.api.shared.dto.VehiclEmptyBillDto;

public class VehicleEmptyBillDao extends iBatis3DaoImpl implements IVehicleEmptyBillDao {
	private static final String NAMESPACE = "tfr-load.vehiclemptybill.";
	
	
	/**
	 * 空驶单查询
	 *  @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VehiclEmptyBillDto> queryVehicleEmptyBill(VehiclEmptyBillDto vehiclEmDto,
			int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		
		return this.getSqlSession().selectList(NAMESPACE+"queryVehicleEmptybill",vehiclEmDto, rowBounds);
	}
	/**
	 * 空驶单查询 获取查询的总记录数
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	@Override
	public Long getVehiclEmptyBillCount(VehiclEmptyBillDto vehiclEmDto) {
	
		return  (Long)this.getSqlSession().selectOne(NAMESPACE+"queryVehiclEmptyBillCount", vehiclEmDto);
	}
	/**
	 * 通过空驶单号查询车辆状态是否在途已到达（删除校验）
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	@Override
	public Long searchStatusByVehiclEmptyBillNo(String vehiclEmptyBillNo) {
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "searchStatusByVehiclEmptyBillNo", vehiclEmptyBillNo);
	}
	/**
	 * 通过车牌号查询车辆状态是否未出发（新增校验）
	 * @author zhangpeng
	 * @date 2015-10-10 上午10:51:04
	 */
	@Override
	public Long searchStatusByVehicleNoIsDepart(String vehicleNo) {
		
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "searchStatusByVehicleNoIsDepart", vehicleNo);
	}
	@Override
	public int updateVehiclEmptyBillState(String vehicleNo) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public VehiclEmptyBillDto queryTruckTaskIdByVehiclEmptyBillNo(
			String vehiclEmptyBillNo) {
		return (VehiclEmptyBillDto) this.getSqlSession().selectOne(NAMESPACE+"queryTruckTaskIdByVehiclEmptyBillNo", vehiclEmptyBillNo);
	}
	@Override
	public int deleteTruckTaskDetail(String truckTaskDetailId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(NAMESPACE+"deleteTruckTaskDetailVehiclEmptyBillNo", truckTaskDetailId);
	}
	@Override
	public int deleteTruckTask(String truckTaskId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(NAMESPACE+"deleteTruckTaskVehiclEmptyBillNo", truckTaskId);
	}
	@Override
	public int deleteTruckTaskBill(String vehiclEmptyBillNo) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(NAMESPACE+"deleteTruckTaskBillVehiclEmptyBillNo", vehiclEmptyBillNo);
	}
	
		
}
