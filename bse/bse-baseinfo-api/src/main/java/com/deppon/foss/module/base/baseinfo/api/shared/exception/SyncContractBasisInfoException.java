package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

public class SyncContractBasisInfoException extends BusinessException{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
	public SyncContractBasisInfoException(String id, String msg, Throwable cause) {
		super(id, msg, cause);
	    }

	    public SyncContractBasisInfoException(String id,String operation, String msg) {
		super(id,operation, msg);
	    }
	    public SyncContractBasisInfoException(String msg) {
		super(msg);
	    }
}
