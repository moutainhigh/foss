package com.deppon.foss.module.transfer.platform.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CargoFcstResultDto;

public interface ICargoFcstService {

	/**
	 * @desc 查询部门所属外场
	 * @param currentDeptCode
	 * @return
	 * @date 2015年3月19日 下午4:06:16
	 * @author Ouyang
	 */
	OrgAdministrativeInfoEntity queryTfrCtrBySubCode(String orgCode);

	/**
	 * @desc 货量预测，实际货量和预测货量生成
	 * @date 2015年3月19日 下午3:04:50
	 * @author Ouyang
	 */
	void fcstCargo();

	/**
	 * @desc 界面查询
	 * @param parameter
	 * @return
	 * @date 2015年3月17日 下午5:12:04
	 * @author Ouyang
	 */
	List<CargoFcstResultDto> findFcstResult(CargoFcstDto parameter);

	/**
	 * @desc 查询全国货量预测默认参数
	 * @return
	 * @date 2015年3月19日 上午11:36:57
	 * @author Ouyang
	 */
	String findDefaultConfig();

	/**
	 * @desc 查询外场货量预测特别参数
	 * @param tfrCtrCode
	 * @return
	 * @date 2015年3月19日 上午11:37:15
	 * @author Ouyang
	 */
	String findTfrCtrConfig(String tfrCtrCode);

}
