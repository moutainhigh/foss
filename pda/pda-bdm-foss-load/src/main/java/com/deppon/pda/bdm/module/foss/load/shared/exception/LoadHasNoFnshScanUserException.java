package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**     
 *      
 * 装车任务存在未完成扫描用户异常
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class LoadHasNoFnshScanUserException extends PdaBusiException{
	private static final long serialVersionUID = 1L;
	
	public LoadHasNoFnshScanUserException(){
		super();
	}
	
	public LoadHasNoFnshScanUserException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_LOADNOFNSHEXIST_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "存在未完成扫描的用户!";
	}
}
