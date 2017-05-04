package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**     
 *      
 * 装车任务需要任务号异常
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class LoadTaskNeedTaskCodeException extends PdaBusiException{
	private static final long serialVersionUID = 1L;
	
	public LoadTaskNeedTaskCodeException(){
		super();
	}
	
	public LoadTaskNeedTaskCodeException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_TASKNEEDCODE_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "该任务需要填写任务号!";
	}

}
