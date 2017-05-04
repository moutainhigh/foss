package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.Date;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.IsCustomerCircleDto;
/**
 * 
 * 客户圈信息service接口
 * @author 308861 
 * @date 2017-1-10 下午5:47:54
 * @since
 * @version
 */
public interface ICustomerCircleRelationNewService extends IService{
	
	/**
	 * 
	 * 根据 客户编码  开单时间  开单更改单状态 查询客户圈信息  
	 * @author 308861 
	 * @date 2017-1-4 下午3:56:41
	 * @param custCode
	 * @param billDate
	 * @param isChange
	 * @return
	 * @see
	 */
	public CustomerCircleNewDto getByCustCode(String custCode, Date billDate,String isChange);
	/**
	 * 
	 * 根据客户编码判断是否在客户圈内以及主客户编码  
	 * @author 308861 
	 * @date 2017-2-7 下午5:33:22
	 * @param custCode
	 * @param billDate
	 * @return
	 * @see
	 */
	public IsCustomerCircleDto isCustomerCircle(String custCode,Date billDate);
	
}
