package com.deppon.pda.bdm.module.foss.delivery.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.foss.delivery.shared.constants.DeliveryExceptionConstant;

/**
 * 
 * TODO(客户端时间与服务器时间不一致异常)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2014-2-21 上午9:53:31,content:TODO </p>
 * @author Administrator
 * @date 2014-2-21 上午9:53:31
 * @since
 * @version
 */
public class ClientTimeNotMatchServerException extends PdaBusiException{
	
	private static final long serialVersionUID = 1L;
	
	private String clientTime;
	public ClientTimeNotMatchServerException(String clientTime){
		super();
		this.clientTime = clientTime;
	}
	
	
	public ClientTimeNotMatchServerException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return DeliveryExceptionConstant.ERRCODE_BUSI_DELIVERY_TIME_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "PDA当前时间为%s与服务器不一致，请退出系统，关闭同步网络时间功能!";
	}
	@Override
	public Object[] getErrorArguments(){
		Object[] arg = new Object[]{
			this.getClientTime()
		};
		return arg;
	}
	private String getClientTime(){
		return clientTime;
	}
}
