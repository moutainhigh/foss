package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;

/**     
 *      
 * 装车任务不存在异常
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class LoadTaskNotExistException extends PdaBusiException{

	private static final long serialVersionUID = 1L;
	
	public LoadTaskNotExistException(){
		super();
	}
	
	public LoadTaskNotExistException(Throwable cause){
		super(cause);
	}
	
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_LOADTASKNOEXIST_ERR;
	}

	@Override
	public String getDefaultMessage() {
		return "装车任务未创建或者已撤销!";
	}

}
