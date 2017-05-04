package com.deppon.foss.module.pickup.order.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.CourierSignDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerBillCountDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressWorkerStatusDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.WorkerbillCountQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.util.define.FossConstants;

public class ExpressWorkerStatusDao extends iBatis3DaoImpl implements IExpressWorkerStatusDao {
	private static final String NAMESPACE ="com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity.";

	@Override
	public int deleteByPrimaryKey(Object id) {
		return 0;
	}
    
    /**
     * 
     * @author:lianghaisheng
     * @Description：插入快递员工作状态
     * @date:2014-4-24 下午2:33:28
     */
	@Override
	public int insert(ExpressWorkerStatusEntity record) {
		 return getSqlSession().insert(NAMESPACE+"insert",record);
	}
	
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：插入快递员工作状态
	 * @date:2014-4-24 下午2:33:56
	 */
	@Override
	public int insertSelective(ExpressWorkerStatusEntity record) {
		return getSqlSession().insert(NAMESPACE+"insertSelective",record);
	}
    
	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：快递员工作状态单条更新
	 * @date:2014-4-24 下午2:34:15
	 */
	@Override
	public int updateByPrimaryKeySelective(ExpressWorkerStatusEntity record) {		
		return getSqlSession().update(NAMESPACE+"updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(ExpressWorkerStatusEntity record) {
		return 0;
	}


	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：根据工号查询快递员工作状态
	 * @date:2014-4-24 下午2:34:56
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerStatusEntity> selectByEmployeeCodes(List<CourierSignDto> list) {			
		return getSqlSession().selectList(NAMESPACE+"selectByEmployeeCodes", list);
		
	}
	/**
	 * 
	 * @author:gaochunling
	 * @Description：根据工号查询快递员工作状态的最新一条数据
	 * @date:2014-7-9 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerStatusEntity> selectUpDateByEmployeeCodes(List<CourierSignDto> list) {			
		return getSqlSession().selectList(NAMESPACE+"selectUpDateByEmployeeCodes", list);
		
	}
	
	/**
	 * 
	 * @author:zxy
	 * @Description：根据工号查询快递员工作状态
	 * @date:2014-7-8 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerStatusEntity> selectByEmployeeCodesByEntity(ExpressWorkerStatusEntity workerStatusEntity,List<CourierSignDto> list) {			
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("empCodeLst", list);
		paramMap.put("active", workerStatusEntity.getActive());
		return getSqlSession().selectList(NAMESPACE+"selectByEmployeeCodesByEntity", paramMap);
		
	}


	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：批量更新快递员工作状态
	 * @date:2014-4-24 下午2:35:32
	 */
	@Override
	public int updateByEmployeeCodes(ExpressWorkerStatusDto dto) {
		dto.setActive(FossConstants.ACTIVE);//14.7.17 gcl AUTO-177开启暂停操作都是在快递员登陆中操作
		return getSqlSession().update(NAMESPACE+"updateByEmployeeCodes", dto);
	}
	/**
	 * 零担自动分配 先 根据车牌号查询车辆工作状态 
	 * 14.7.17 gcl  AUTO-170
	 */
	@Override
	public Long selectOneByVehicleNo(ExpressWorkerStatusEntity worker) {
		return (Long)getSqlSession().selectOne(NAMESPACE+"selectOneByVehicleNo", worker);
	}
	
	/**
	 * 根据车牌号查询车辆工作状态 
	 * @see com.deppon.foss.module.pickup.order.api.server.dao.IExpressWorkerStatusDao#queryOneByVehicleNo(com.deppon.foss.module.pickup.order.api.shared.domain.ExpressWorkerStatusEntity)
	 */
	@Override
	public ExpressWorkerStatusEntity queryOneByVehicleNo(ExpressWorkerStatusEntity queryParam) {
		List<ExpressWorkerStatusEntity> workerStatusEntityLst = (List<ExpressWorkerStatusEntity>)getSqlSession().selectList(NAMESPACE+"queryOneByVehicleNo", queryParam);
		if(workerStatusEntityLst != null && workerStatusEntityLst.size() > 0)
			return workerStatusEntityLst.get(0);
		return null;
	}

	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：运单完成情况查询
	 * @date:2014-4-24 下午2:36:05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerBillCountDto> queryWaybillCount(
			WorkerbillCountQueryDto dto) {
		List<String> status = new ArrayList<String>();
		status.add( DispatchOrderStatusConstants.STATUS_PDA_ACCEPT);
		status.add(DispatchOrderStatusConstants.STATUS_PICKUPING);
		status.add(DispatchOrderStatusConstants.STATUS_WAYBILL);
		dto.setStatus(status);
		return getSqlSession().selectList(NAMESPACE+"queryWaybillCount", dto);
	}

	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：根据司机查询定单的统计结果
	 * @date:2014-4-24 下午2:36:43
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerBillCountDto> queryOrderBillCount(
			WorkerbillCountQueryDto dto) {
		List<String> status = new ArrayList<String>();
		status.add(DeliverbillConstants.STATUS_CONFIRMED);
		status.add(DeliverbillConstants.STATUS_PDA_DOWNLOADED);
		dto.setStatus(status);
		dto.setProductCode(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE+"queryOrderCount", dto);
	}

	/**
	 * 
	 * @author:lianghaisheng
	 * @Description：根据司机查询无订单开单的统计结果
	 * @date:2014-4-24 下午2:36:58
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerBillCountDto> queryNoOrderBillCount(
			WorkerbillCountQueryDto dto) {		
		List<String> status = new ArrayList<String>();
		status.add(WaybillConstants.PDA_ACTIVE);
		dto.setStatus(status);
		dto.setProductCode(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE+"queryNoOrderCount", dto);

	}

	/**
	 * @author:lianghaisheng
	 * @Description：根据工号查询员工状态
	 * @date:2014-4-29 下午2:23:05
	 */
	@Override
	public ExpressWorkerStatusEntity selectOneByEmpcode(String empCode) {
		return (ExpressWorkerStatusEntity) getSqlSession().selectOne(NAMESPACE+"selectOneByEmpcode", empCode);
	}
	/**
	 * @author:gcl
	 * @Description：根据签到id查询员工状态
	 * @date:2014-7-16 下午2:23:05 AUTO-163
	 */
	@Override
	public ExpressWorkerStatusEntity selectOneByPdaSignid(String pdaSignId) {
		return (ExpressWorkerStatusEntity) getSqlSession().selectOne(NAMESPACE+"selectOneByPdaSignid", pdaSignId);
	}
	
	/**
	 * @author:lianghaisheng
	 * @Description：统计无订单开单-运单补录表
	 * @date:2014-6-29 下午2:23:05
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerBillCountDto> queryNoOrderPendingCount(
			WorkerbillCountQueryDto dto) {
		dto.setProductCode(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		return getSqlSession().selectList(NAMESPACE+"queryNoOrderPendingCount", dto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpressWorkerStatusEntity> queryWorkStatusByVehicle(
			List<String> vehicleNoList, ExpressWorkerStatusEntity entity) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("vehicleNoList", vehicleNoList);
		paramMap.put("active", entity.getActive());
		paramMap.put("businessArea", entity.getBusinessArea());
		return getSqlSession().selectList(NAMESPACE+"queryWorkStatusByVehicle", paramMap);
	}
	
	@Override
	public int updateByVehicleNos(ExpressWorkerStatusDto dto){
		return getSqlSession().update(NAMESPACE+"updateByVehicleNos", dto);
	}

}
