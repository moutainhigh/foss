package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * TODO(描述类的职责)
 * @author 187862-杜军辉
 * @date 2014-5-8 下午5:02:48
 * @since
 * @version
 */
public class PackagingSupplierException extends BusinessException{
	
	/**
     * 异常类
     */
    private static final long serialVersionUID = -6525548077272997158L;

    public PackagingSupplierException(String errCode) {
	    
    	super();
	    super.errCode = errCode;
    }

    public PackagingSupplierException(String code, String msg) {

	    super(code, msg);
    }

}
