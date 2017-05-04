package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 定义一个存放导出excel信息的异常
 * 
 * @author WangPeng
 * @date 2013-06-20 3:11PM
 * @see
 * 
 */
public class RecordImportExcelFileInfoException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public RecordImportExcelFileInfoException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public RecordImportExcelFileInfoException(String code, String msg) {

		super(code, msg);
	}

}
