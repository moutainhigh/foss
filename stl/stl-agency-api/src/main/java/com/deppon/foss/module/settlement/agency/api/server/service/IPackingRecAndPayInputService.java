package com.deppon.foss.module.settlement.agency.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.agency.api.shared.dto.PackingRecAndPayInputDto;

/**
 * 包装其它应收应付录入Service
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-6-6 上午8:58:31,content:TODO </p>
 * @author 105762
 * @date 2014-6-6 上午8:58:31
 * @since 1.6
 * @version 1.0
 */
public interface IPackingRecAndPayInputService extends IService{
	
	/**
	 * <p>包装其它应收应付录入</p> 
	 * @author 105762
	 * @date 2014-5-16 下午5:22:53
	 * @return
	 * @see
	 */
	void input(PackingRecAndPayInputDto dto, CurrentInfo currentInfo);
}
