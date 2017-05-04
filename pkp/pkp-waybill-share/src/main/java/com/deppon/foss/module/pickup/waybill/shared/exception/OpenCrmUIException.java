/*
 * PROJECT NAME: pkp-waybill-share
 * PACKAGE NAME: com.deppon.foss.module.pickup.waybill.shared.exception
 * FILE    NAME: OpenCrmUIException.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.pickup.waybill.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;


/**
 * 打开CRM相关界面异常
 * @author 026123-foss-lifengteng
 * @date 2013-3-18 上午11:31:17
 */
public class OpenCrmUIException extends BusinessException {

	/**
	 * 序列化号
	 * 用于对象在网络中进行传输
	 */
	private static final long serialVersionUID = -3006545691079124362L;

	/**
	 * 创建一个新的实例OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException() {
		super();
	}

	/**
	 * 创建一个新的实例OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String msg) {
		super(msg);
		this.errCode = msg;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String msg, Throwable cause) {
		super(msg, cause);
		this.errCode = msg;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String code, String msg) {
		super(code, msg);
		this.errCode = code;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String code, Object... args) {
		super(code, args);
		this.errCode = code;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String code, String msg, Throwable cause) {
		super(code, msg, cause);
		this.errCode = code;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String code, String msg, String natvieMsg) {
		super(code, msg, natvieMsg);
		this.errCode = code;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String code, String msg, Object... args) {
		super(code, msg, args);
		this.errCode = code;
	}

	/**
	 * 创建一个新的实例 OpenCrmUIException
	 * @author 026123-foss-lifengteng
	 * @date 2013-3-18 上午11:31:17
	 */
	public OpenCrmUIException(String code, String msg, String natvieMsg, Throwable cause) {
		super(code, msg, natvieMsg, cause);
		this.errCode = code;
	}

}
