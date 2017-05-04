package com.deppon.pda.bdm.module.ocb.shared.exception;

import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;


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
public class UploadException extends PdaBusiException{
	
	private static final long serialVersionUID = 1L;
	
	public UploadException(String clientTime){
		super();
	}
	
	
	public UploadException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return null;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "图片上传异常";
	}
	@Override
	public Object[] getErrorArguments(){
		Object[] arg = new Object[]{
		};
		return arg;
	}
}
