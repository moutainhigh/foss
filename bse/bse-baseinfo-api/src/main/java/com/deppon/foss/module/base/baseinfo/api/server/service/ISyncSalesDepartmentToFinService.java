package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.SyncSalesInfoToFinSelfDto;


/**
 * 配合发票项目，同步营业部部分信息的接口
 * @author 130566
 *
 */
public interface ISyncSalesDepartmentToFinService extends IService {
	/**
	 *<p>同步给发票项目的营业部部分信息</p>	
	 * @date 2014-6-18 下午3:48:24
	 * @author 130566-ZengJunfan
	 * @param dto
	 */
	void syncSalesInfoToFinSelf(SyncSalesInfoToFinSelfDto dto);
}
