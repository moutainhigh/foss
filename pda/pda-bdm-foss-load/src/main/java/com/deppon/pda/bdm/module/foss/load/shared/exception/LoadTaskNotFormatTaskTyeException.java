package com.deppon.pda.bdm.module.foss.load.shared.exception;

import com.deppon.pda.bdm.module.core.shared.constants.ExceptionConstant;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
/**     
 *      
 * 任务号与装车类型不对应异常
 *     
 * @author gaojia       
 * @version 1.0     
 * @created 2012-9-12 上午11:08:23    
 */
public class LoadTaskNotFormatTaskTyeException extends PdaBusiException{
	private static final long serialVersionUID = 1L;
	
	public LoadTaskNotFormatTaskTyeException(){
		super();
	}
	
	public LoadTaskNotFormatTaskTyeException(Throwable cause){
		super(cause);
	}
	
	@Override
	public String getErrCode() {
		return ExceptionConstant.ERRCODE_BUSI_LOAD_TASKCODENOTMACTHTASKTYPE_ERR;
	}
	
	protected String getNativeMessageKey(){
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return "任务号与任务类型不对应!";
	}
}
