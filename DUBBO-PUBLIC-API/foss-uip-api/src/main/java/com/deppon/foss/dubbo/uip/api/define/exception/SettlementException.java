package com.deppon.foss.dubbo.uip.api.define.exception;

public class SettlementException extends BusinessException {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 959484631147426908L;

	/**
	 * 空的构造方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-17 下午8:21:17
	 */
	public SettlementException() {
	}

	/**
	 * 空的构造方法
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-17 下午8:21:00
	 * @param errCode
	 */
	public SettlementException(String errCode) {
		super(errCode);
		super.errCode = errCode;
	}

	/**
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-11-5 上午9:02:44
	 * @param errCode
	 * @param msg
	 *            //动态异常信息
	 */
	public SettlementException(String errCode, String msg) {
		super(errCode, msg);
		super.errCode = errCode;
	}

	/**
	 * 调用其他模块的Exception 可以传入一个BusinessException异常
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-17 下午8:20:34
	 * @param e
	 */
	public SettlementException(BusinessException e) {
		super();
		super.errCode = e.getErrorCode();
	}
	
	/**
	 * 有两个参数的构造函数
	 * @author foss-jiangxun
	 * @date 2016-3-2 下午3:42:00
	 * @param code
	 * @param cause
	 */
	public SettlementException(String code, Throwable cause) {
		super(code,cause);
		this.errCode = code;
	}

}