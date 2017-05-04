package com.deppon.foss.module.settlement.pay.api.server.dao;

import com.deppon.foss.module.settlement.pay.api.shared.dto.SubmitRefundDto;

/**
 * 转报销接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:105762,date:2014-7-10 下午4:31:31,content:TODO </p>
 * @author 105762
 * @date 2014-7-10 下午4:31:31
 * @since 1.6
 * @version 1.0
 */
public interface ISubmitRefundDao {

	/**
	 * <p>转报销接口</p> 
	 * @author 105762
	 * @param dto 
	 * @return 
	 * @date 2014-7-10 下午4:31:39
	 * @see
	 */
	int handleSubmitRefund(SubmitRefundDto dto);

}
