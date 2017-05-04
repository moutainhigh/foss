package com.deppon.foss.module.transfer.load.dubbo.api.exception;

/**
 * Created by 335284 on 2017/3/31.
 */
public class TfrLoadException extends Exception {
    /**
	 * 序列化ID
	 */
	private static final long serialVersionUID = -787064228612260804L;
	
	public TfrLoadException(){}
    public TfrLoadException(String message) {
        super(message);
    }
}
