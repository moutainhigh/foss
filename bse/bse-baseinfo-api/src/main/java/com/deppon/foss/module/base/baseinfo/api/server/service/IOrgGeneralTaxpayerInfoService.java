package com.deppon.foss.module.base.baseinfo.api.server.service;

import com.deppon.esb.inteface.domain.crm.FailTaxpayerInfo;
import com.deppon.esb.inteface.domain.crm.GeneralTaxpayerInfo;
import com.deppon.foss.framework.service.IService;
/**
 * 
 * 同步一般纳税人信息service接口
 * @author 308861 
 * @date 2016-2-28 下午2:49:39
 * @since
 * @version
 */
public interface IOrgGeneralTaxpayerInfoService extends IService{
	
	/**
	 * 
	 * 操作 
	 * @author 308861 
	 * @date 2016-2-25 上午10:57:52
	 * @param list
	 * @return
	 * @see
	 */
	public FailTaxpayerInfo operation(GeneralTaxpayerInfo info);
	
}
