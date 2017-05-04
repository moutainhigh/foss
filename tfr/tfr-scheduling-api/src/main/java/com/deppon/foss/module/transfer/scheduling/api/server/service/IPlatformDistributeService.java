package com.deppon.foss.module.transfer.scheduling.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformDistributeEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.domain.PlatformPreAssignEntity;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDistributeQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformQcDto;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.PlatformVehicleInfoDto;

public interface IPlatformDistributeService {

	
	Map<String, String> queryParentTfrCtrCode(String code);
	
	/**
	 * 查询未到达公司车(带交接编号,交接单号或配载单号)
	 * @param qcDto
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	PlatformVehicleInfoDto queryCompanyOnTheWayWithNum(
			PlatformDistributeQcDto qcDto);

	/**
	 * 查询未到达外请车(带交接编号,交接单号或配载单号)
	 * @param qcDto
	 * @return
	 * @date 2014-4-10
	 * @author Ouyang
	 */
	PlatformVehicleInfoDto queryLeasedOnTheWayWithNum(
			PlatformDistributeQcDto qcDto);

	/**
	 * 查询到达未分配(带交接编号,交接单号或配载单号)
	 * @param qcDto
	 * @return
	 * @date 2014-4-18
	 * @author Ouyang
	 */
	PlatformVehicleInfoDto queryArrivedWithNum(PlatformDistributeQcDto qcDto);

	/**
	 * 查询未到达公司车
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	List<PlatformVehicleInfoDto> queryCompanyOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit);

	/**
	 * 查询未到达外请车
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	List<PlatformVehicleInfoDto> queryLeasedOnTheWay(
			PlatformDistributeQcDto qcDto, int start, int limit);

	/**
	 * 查询已到达未分配
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	List<PlatformVehicleInfoDto> queryArrived(PlatformDistributeQcDto qcDto,
			int start, int limit);

	/**
	 * 查询未到达公司车数量
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	Long queryCntCompanyOnTheWay(PlatformDistributeQcDto qcDto);

	/**
	 * 查询未到达外请车数量
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	Long queryCntLeasedOnTheWay(PlatformDistributeQcDto qcDto);

	/**
	 * 查询已到达未分配数量
	 * @param qcDto
	 * @return
	 * @date 2014-4-5
	 * @author Ouyang
	 */
	Long queryCntArrived(PlatformDistributeQcDto qcDto);

	/**
	 * 获取最优月台
	 * @param truckTaskDetailId
	 * @param vehicleNo
	 * @param deptOrgCode
	 * @return
	 * @date 2014-4-14
	 * @author Ouyang
	 */
	List<PlatformDto> queryOptimalPlatform(PlatformQcDto qcDto);

	/**
	 * 月台预分配
	 * @param entity
	 * @date 2014-4-16
	 * @author Ouyang
	 */
	void predistribute(PlatformDistributeEntity entity);

	/**月台预分配
	 * @desc 
	 * @param info
	 * @date 2015年4月21日 下午5:58:41
	 * @author Ouyang
	 */
	void preAssign(PlatformPreAssignEntity info);

}
