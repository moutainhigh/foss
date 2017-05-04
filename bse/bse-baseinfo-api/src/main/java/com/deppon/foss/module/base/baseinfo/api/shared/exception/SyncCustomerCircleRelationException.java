package com.deppon.foss.module.base.baseinfo.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 用来处理“客户圈”业务操作异常类类
 * @author 308861
 *
 */
public class SyncCustomerCircleRelationException extends BusinessException{

	/**
	 *序列化
	 */
	private static final long serialVersionUID = 8107250822486742725L;
	
	public SyncCustomerCircleRelationException(String crmId, String msg, Throwable cause) {
	super(crmId, msg, cause);
    }

    public SyncCustomerCircleRelationException(String crmId,String operation, String msg) {
	super(crmId,operation, msg);
    }
    public SyncCustomerCircleRelationException(String msg) {
	super(msg);
    }

}
