package com.deppon.foss.module.transfer.platform.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.CockpitResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.CockpitVo;

public interface ICockpitService extends IService {

	
	/**
	* @description 驾驶舱查询
	* @param orgCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年9月2日 下午3:24:01
	*/
	CockpitVo queryCockpitByOrgCode(String orgCode);
	
	/**
	 * @desc 驾驶舱查询
	 * @param tfrCtrCode
	 * @return
	 * @date 2015年8月11日下午4:17:05
	 */
	CockpitResultDto findCockpitResult(String tfrCtrCode);
	
}
