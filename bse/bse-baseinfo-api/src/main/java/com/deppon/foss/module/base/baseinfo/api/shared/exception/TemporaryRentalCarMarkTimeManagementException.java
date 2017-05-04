package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 临时租车标记时间管理 异常类
 * @author 218392  张永雪
 * @date 创建时间：2014-12-18 下午2:00:30
 *
 */
public class TemporaryRentalCarMarkTimeManagementException extends BusinessException{

	/**
	 * serialVersionUID
	 * @author 218392  张永雪
	 * @date 创建时间：2014-12-18 下午2:02:21
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TemporaryRentalCarMarkTimeManagementException(String errCode){
		super();
		super.errCode = errCode;
	}
	public TemporaryRentalCarMarkTimeManagementException(String code,String msg){
		super(code,msg);
	}
}
