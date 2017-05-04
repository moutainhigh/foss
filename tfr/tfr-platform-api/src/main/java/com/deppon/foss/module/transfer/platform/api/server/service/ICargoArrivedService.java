package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDetailDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoArrivedQcDto;

public interface ICargoArrivedService extends IService {

	/**
	 * @desc 根据子部门编码查询所属上级外场
	 * @param currentDeptCode
	 * @return
	 * @date 2015年1月19日 上午9:51:44
	 * @author Ouyang
	 */
	OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String code);

	/**
	 * @desc 到达本外场货量统计
	 * @param parameter
	 * @return
	 * @date 2015年1月19日 上午9:54:34
	 * @author Ouyang
	 */
	List<CargoArrivedDto> findCargoArrived(CargoArrivedQcDto parameter);

	/**
	 * @desc 长途在途明细
	 * @param parameter
	 * @return
	 * @date 2015年1月19日 上午9:54:59
	 * @author Ouyang
	 */
	List<CargoArrivedDetailDto> findDetails(CargoArrivedQcDto parameter);

}
