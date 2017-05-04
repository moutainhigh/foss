package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * CRM行业、客户等级、订单来源信息异常类
 * @author dujunhui-187862
 * @date 2014-9-25 下午3:30:01
 * @since
 * @version
 */
public class CusOrderSourceException extends BusinessException{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 6181761802510106770L;

	public CusOrderSourceException(String errCode) {
		super();
		super.errCode = errCode;
	    }

	    public CusOrderSourceException(String errCode, Object... para) {
		super(errCode, para);
		super.errCode = errCode;
	    }

}
