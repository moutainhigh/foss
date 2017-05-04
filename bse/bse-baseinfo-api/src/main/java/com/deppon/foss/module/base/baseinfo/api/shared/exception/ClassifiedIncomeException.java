package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;
/**
 * 重分类收入基础信息
 * @author 307196
 *
 */
public class ClassifiedIncomeException extends BusinessException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

    public static final String ClassifiedIncome_ADD_SUCCESS = "新增成功";
    
    public static final String ClassifiedIncome_ADD_FAILURE = "新增失败";
    
    public static final String ClassifiedIncome_DEL_SUCCESS = "作废成功";
    
    public static final String ClassifiedIncome_DEL_FAILURE = "作废失败";
    
    public static final String ClassifiedIncome_UPD_SUCCESS = "修改成功";
    
    public static final String ClassifiedIncome_UPD_FAILURE = "修改失败";

    public ClassifiedIncomeException(String code, String msg) {
	super(code, msg);
    }

    public ClassifiedIncomeException(String msg, Throwable cause) {
	super(msg, cause);
    }

    public ClassifiedIncomeException(String msg) {
	super(msg);
    }
}
