package com.deppon.foss.module.transfer.scheduling.api.server.dao;

import java.math.BigDecimal;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformPreAssignEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformVehicleInfoDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformWaybillDto;

public interface IPlatformDistributeDao {

	/**
	 * 查询未到达车辆任务明细id(带交接编号)
	 * 
	 * @param handoverNo
	 * @return
	 * @date 2014-4-11
	 * @author Ouyang
	 */
	String queryTaskDetailIdOnTheWayWithNum(PlatformDistributeQcDto qcDto);

	/**
	 * 查询到达未分配车辆任务明细id(带交接编号)
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	String queryTaskDetailIdArrivedByNum(PlatformDistributeQcDto qcDto);

	/**
	 * 根据车辆任务明细id查询车辆相关信息
	 * 
	 * @param taskDetailId
	 * @return
	 * @date 2014-4-11
	 * @author Ouyang
	 */
	PlatformVehicleInfoDto queryVehicleInfoByTaskDetailId(String taskDetailId);

	/**
	 * 查询未到达公司车
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	List<PlatformVehicleInfoDto> queryCompanyOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit);

	/**
	 * 查询未到达外请车
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	List<PlatformVehicleInfoDto> queryLeasedOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit);

	/**
	 * 查询已到达未分配
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	List<PlatformVehicleInfoDto> queryArrived(PlatformDistributeQcDto qcDto,
			int start, int limit);

	/**
	 * 查询未到达公司车数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	Long queryCntCompanyOnTheWay(PlatformDistributeQcDto qcDto);

	/**
	 * 查询未到达外请车数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	Long queryCntLeasedOnTheWay(PlatformDistributeQcDto qcDto);

	/**
	 * 查询已到达未分配数量
	 * 
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	Long queryCntArrived(PlatformDistributeQcDto qcDto);

	/**
	 * 根据车辆任务明细id查询此明细的货物信息
	 * 
	 * @param taskDetailId
	 * @return
	 * @date 2014-4-8
	 * @author Ouyang
	 */
	List<PlatformWaybillDto> queryWaybillInfosByTaskDetailId(String taskDetailId);

	/**
	 * 根据运单号查询走货路径，获取运单的下一部门
	 * 
	 * @param waybill
	 * @return
	 * @date 2014-4-8
	 * @author Ouyang
	 */
	String queryNextDeptCode(PlatformWaybillDto waybill);

	/**
	 * 查询月台是否有对应状态的分配记录
	 * 
	 * @param entity
	 * @return
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	Long queryCntPlatformDistribute(PlatformDistributeEntity entity);

	/**
	 * 查询车辆预分配的月台
	 * 
	 * @param entity
	 * @return
	 * @date 2014年7月28日
	 * @author 042770
	 */
	List<String> queryVehiclePrePlatform(PlatformDistributeEntity entity);

	/**
	 * 月台预分配
	 * 
	 * @param entity
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	void predistribute(PlatformDistributeEntity entity);

	/**
	 * 月台预分配
	 * 
	 * @param entity
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	void preAssign(PlatformPreAssignEntity entity);

	int updatePreAssign(PlatformPreAssignEntity entity);

	/**
	 * @desc 查询月台到库区的距离
	 * @param platformVirtualCode
	 * @param goodsAreaVirtualCode
	 * @return
	 * @date 2015年6月8日 下午3:58:54
	 * @author Ouyang
	 */
	List<BigDecimal> findPlatformGoodsAreaDistance(String platformVirtualCode,
			String goodsAreaVirtualCode);

}
