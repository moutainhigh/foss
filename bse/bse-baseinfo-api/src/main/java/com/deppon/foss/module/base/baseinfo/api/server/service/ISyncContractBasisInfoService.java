package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.esb.inteface.domain.ptp.ContractBasisInfo;
import com.deppon.foss.framework.service.IService;
/**
 * 
 * 同步合同信息service接口
 * @author 308861 
 * @date 2016-8-12 下午3:52:57
 */
public interface ISyncContractBasisInfoService extends IService{
	
	/**
	 * 
	 * 操作 
	 * @author 308861 
	 * @date 2016-8-12 下午3:53:34
	 * @param info
	 * @see
	 */
	public void operation(ContractBasisInfo info);
	
}
