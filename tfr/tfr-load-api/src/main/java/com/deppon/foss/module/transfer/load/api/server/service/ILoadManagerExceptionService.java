package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DriverBaseDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.LoadTaskExceptionDto;
import com.deppon.foss.module.transfer.load.api.shared.vo.LoadManagerExceptionVo;

public interface ILoadManagerExceptionService extends IService{
	

	/**
	 * 根据界面输入的条件查询异常装车任务数量
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	Long queryLoadManagerExceptionCount(LoadManagerExceptionVo loadManagerExceptionVo);
	
	/**
	 * 根据界面输入的条件查询异常装车任务
	 * @author 332209-FOSS-ruilibao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	List<LoadTaskExceptionDto> queryLoadManagerException(LoadManagerExceptionVo loadManagerExceptionVo,int limit,int start);
	
	/**
	 * 根据界面输入的条件查询异常装车任务
	 * @author 328768-FOSS-jianfugao
	 * @date 2017年3月29日
	 * @param loadManagerExceptionVo
	 * @return
	 */
	OrgAdministrativeInfoEntity queryOrgCode(String orgCode);
	
	void editLoadTaskVehicleNo(LoadManagerExceptionVo loadManagerExceptionVo);
	
	DriverBaseDto queryDriverInfoByVehicleNo(String vehicleNo);

}
