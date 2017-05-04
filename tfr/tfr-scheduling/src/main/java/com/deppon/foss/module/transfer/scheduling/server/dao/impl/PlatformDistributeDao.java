package com.deppon.foss.module.transfer.scheduling.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.transfer.scheduling.api.server.dao.IPlatformDistributeDao;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformPreAssignEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformVehicleInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformWaybillDto;

public class PlatformDistributeDao extends iBatis3DaoImpl implements
		IPlatformDistributeDao {

	private static final String NAMESPACE = "com.deppon.foss.module.transfer.scheduling.api.server.dao.IPlatformDistributeDao.";

	/**
	 * 查询未到达公司车
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformVehicleInfoDto> queryCompanyOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryCompanyOnTheWay", qcDto,
				new RowBounds(start, limit));
	}

	/**
	 * 查询未到达外请车
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformVehicleInfoDto> queryLeasedOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryLeasedOnTheWay", qcDto,
				new RowBounds(start, limit));
	}

	/**
	 * 查询已到达未分配
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformVehicleInfoDto> queryArrived(
			PlatformDistributeQcDto qcDto, int start, int limit) {
		return super.getSqlSession().selectList(NAMESPACE + "queryArrived",
				qcDto, new RowBounds(start, limit));
	}

	/**
	 * 查询未到达公司车数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public Long queryCntCompanyOnTheWay(PlatformDistributeQcDto qcDto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "queryCntCompanyOnTheWay", qcDto);
	}

	/**
	 * 查询未到达外请车数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public Long queryCntLeasedOnTheWay(PlatformDistributeQcDto qcDto) {

		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "queryCntLeasedOnTheWay", qcDto);
	}

	/**
	 * 查询已到达未分配数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	@Override
	public Long queryCntArrived(PlatformDistributeQcDto qcDto) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "queryCntArrived", qcDto);
	}

	/**
	 * 根据车辆任务明细id查询此明细的货物信息
	 * 
	 * @param taskDetailId
	 * @return
	 * @date 2014-4-8
	 * @author Ouyang
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformWaybillDto> queryWaybillInfosByTaskDetailId(
			String taskDetailId) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryWaybillInfosByTaskDetailId", taskDetailId);
	}

	/**
	 * 查询运单从当前外场出发，走货路径的下一部门
	 * 
	 * @param waybill
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	@Override
	public String queryNextDeptCode(PlatformWaybillDto waybill) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "queryNextDeptCode", waybill);
	}

	/**
	 * 查询未到达车辆任务明细id(带交接编号)
	 * 
	 * @param handoverNo
	 * @return
	 * @date 2014-4-11
	 * @author Ouyang
	 */
	@Override
	public String queryTaskDetailIdOnTheWayWithNum(PlatformDistributeQcDto qcDto) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "queryTaskDetailIdOnTheWayByNum", qcDto);
	}

	/**
	 * 查询到达未分配车辆任务明细id(带交接编号)
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	@Override
	public String queryTaskDetailIdArrivedByNum(PlatformDistributeQcDto qcDto) {
		return (String) super.getSqlSession().selectOne(
				NAMESPACE + "queryTaskDetailIdArrivedByNum", qcDto);
	}

	/**
	 * 根据车辆任务明细id查询车辆相关信息
	 * 
	 * @param taskDetailId
	 * @return
	 * @date 2014-4-11
	 * @author Ouyang
	 */
	@Override
	public PlatformVehicleInfoDto queryVehicleInfoByTaskDetailId(
			String taskDetailId) {
		return (PlatformVehicleInfoDto) super.getSqlSession().selectOne(
				NAMESPACE + "queryVehicleInfoByTaskDetailId", taskDetailId);
	}

	/**
	 * 查询月台是否有对应状态的分配记录
	 * 
	 * @param entity
	 * @return
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	@Override
	public Long queryCntPlatformDistribute(PlatformDistributeEntity entity) {
		return (Long) super.getSqlSession().selectOne(
				NAMESPACE + "queryCntPlatformDistribute", entity);
	}

	/**
	 * 查询车辆预分配的月台
	 * 
	 * @param entity
	 * @return
	 * @date 2014年7月28日
	 * @author 042770
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryVehiclePrePlatform(PlatformDistributeEntity entity) {
		return super.getSqlSession().selectList(
				NAMESPACE + "queryVehiclePrePlatform", entity);
	}

	/**
	 * 月台预分配
	 * 
	 * @param entity
	 * @return
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	@Override
	public void predistribute(PlatformDistributeEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "predistribute", entity);
	}

	@Override
	public void preAssign(PlatformPreAssignEntity entity) {
		super.getSqlSession().insert(NAMESPACE + "preAssign", entity);
	}

	@Override
	public int updatePreAssign(PlatformPreAssignEntity entity) {
		return super.getSqlSession().update(NAMESPACE + "updatePreAssign",
				entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> findPlatformGoodsAreaDistance(
			String platformVirtualCode, String goodsAreaVirtualCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("platformVirtualCode", platformVirtualCode);
		map.put("goodsAreaVirtualCode", goodsAreaVirtualCode);
		return super.getSqlSession().selectList(
				NAMESPACE + "findPlatformGoodsAreaDistance", map);
	}

}
