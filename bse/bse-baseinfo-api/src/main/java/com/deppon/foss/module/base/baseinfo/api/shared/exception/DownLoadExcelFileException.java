package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 定义下载excel文件信息的异常
 * 
 * @author WangPeng
 * @date 2013-06-21 2:22PM
 * @see
 * 
 */
public class DownLoadExcelFileException extends BusinessException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	public DownLoadExcelFileException(String errCode) {
		super();
		super.errCode = errCode;
	}

	public DownLoadExcelFileException(String code, String msg) {

		super(code, msg);
	}

}
