package com.deppon.pda.bdm.module.foss.unload.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;


/**     
 *      
 * 存在未完成卸车扫描异常
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class UnldHasNoFnshScanUserException extends PdaBusiException{

	private static final long serialVersionUID = 7894737599654838807L;
	
	public UnldHasNoFnshScanUserException(){
		super();
	}
	
	public UnldHasNoFnshScanUserException(Throwable cause){
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_UNLOAD_UNLDNOFNSHEXIST_ERR;
	}

	@Override
	public String getDefaultMessage() {
		return "存在未完成卸车任务用户!";
	}
}
