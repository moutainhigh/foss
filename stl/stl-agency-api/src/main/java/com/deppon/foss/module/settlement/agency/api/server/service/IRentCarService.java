/**
 * 外租车
 */
package com.deppon.foss.module.settlement.agency.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.RentCarDto;

/**
 * @author 045738
 * @外租车应付单生成和取消
 */
public interface IRentCarService extends IService {
	
	/**
	 * 功能：生成外租车应付单
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-11
	 * @return
	 */
	public void addRentCar(List<RentCarDto> list,CurrentInfo cInfo);
	
	/**
	 * 功能：取消标记
	 * @author 045738-foss-maojianqiang
	 * @date 2014-6-11
	 * @return
	 */
	public void disableRentCar(String rentCarNo);
}
