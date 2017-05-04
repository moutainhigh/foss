package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递派送地图管理异常类
 * @author 187862-dujunhui
 * @date 2014-12-29 上午11:23:48
 * @since
 * @version
 */
public class ExpressDeliveryMapManageException extends BusinessException{
	/**
     * 异常类序列化
     */
    private static final long serialVersionUID = -6525548077272997158L;

    public ExpressDeliveryMapManageException(String errCode) {
    	super();
	    super.errCode = errCode;
    }

    public ExpressDeliveryMapManageException(String code, String msg) {
	    super(code, msg);
    }

}
