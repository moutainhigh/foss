package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 营业部与外请车费用承担部门映射信息
 * @author 307196
 *
 */
public class SalesExpenseMappingException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

    public static final String SalesExpenseMapping_ADD_SUCCESS = "新增成功";
    
    public static final String SalesExpenseMapping_ADD_FAILURE = "新增失败";
    
    public static final String SalesExpenseMapping_DEL_SUCCESS = "作废成功";
    
    public static final String SalesExpenseMapping_DEL_FAILURE = "作废失败";
    
    public static final String SalesExpenseMapping_UPD_SUCCESS = "修改成功";
    
    public static final String SalesExpenseMapping_UPD_FAILURE = "修改失败";

    public SalesExpenseMappingException(String code, String msg) {
	super(code, msg);
    }

    public SalesExpenseMappingException(String msg, Throwable cause) {
	super(msg, cause);
    }

    public SalesExpenseMappingException(String msg) {
	super(msg);
    }
}
