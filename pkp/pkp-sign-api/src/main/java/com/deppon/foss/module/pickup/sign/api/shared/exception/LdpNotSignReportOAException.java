package com.deppon.foss.module.pickup.sign.api.shared.exception;

import com.deppon.foss.framework.exception.BusinessException;

/**
 * 快递代理理外发XX天未签收自动上报OA丢货异常类
 * 
 * @ClassName: LdpNotSignReportOAException
 * @author 200664-yangjinheng
 * @date 2014年9月3日 上午10:45:00
 */
public class LdpNotSignReportOAException extends BusinessException {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 有参构造函数
	 * 
	 * @Title: WaybillQueryForBseException
	 * @author 200664-yangjinheng
	 * @date 2014年9月3日 上午10:46:09
	 * @throws
	 */
	public LdpNotSignReportOAException(String msg) {
		super(msg);
	}

}
